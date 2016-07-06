basic = {};

basic.JSON = {
    isJson: function (obj) {
        return typeof(obj) == 'object' && Object.prototype.toString.call(obj).toLowerCase() == '[object object]' && !obj.length;
    },
    isJsonString: function (jsonStr) {
        return jsonStr.match("^\{(.+:.+,*){1,}\}$");
    },
    convertStrToJson: function (strJson) {
        return JSON.parse(strJson);
    },
    convertJsonToStr: function (ObjJson) {
        return JSON.stringify(ObjJson);
    }
};

basic.FORM = {
    serializeJSON: function (formObj) {
        var serializeObj = {};
        var array = formObj.serializeArray();
        var str = formObj.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    }
};

basic.DataFormatConversion = {
    DateType_Time: function (data) {
        return moment(data).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES);
    },
    DateType_Date: function (data) {
        return moment(data).format('YYYY-MM-DD');
    },
    DateType_DateTime: function (data) {
        return moment(data).format('YYYY-MM-DD HH:mm:ss');
    }
};

basic.HandleServerReturnResult = {

    successReturnResult: function (resultJsonData, remind, divInfoContainerObj) {
        var strJSONObj = {};
        if (basic.JSON.isJson(resultJsonData) == false) {
            strJSONObj = basic.JSON.convertStrToJson(resultJsonData);
            console.info('server return data type:  not json Object')
        } else {
            strJSONObj = resultJsonData;
            console.info('server return data type:  is json Object')
        }
        var resultCode = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_CODE_MARK);
        var resultMsgStr = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);

        try {
            console.info(strJSONObj);
            console.info(basic.JSON.convertStrToJson(resultMsgStr));
        } catch (err) {
            console.info(resultMsgStr);
        }

        //根据 , 业务逻辑 , resultCode -1,代表服务器系统异常；】且将错误提示信息
        if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_SYSTEM) {
            basic.HandleServerReturnResult.serverException(resultMsgStr);
            return false;
        }
        //根据 , 业务逻辑 , resultCode 0,代表处理成功；】根据调用者，显示对话框给予提示
        if (resultCode == ConstantsFinalValue.RESULTS_CODE_SUCCESS) {
            if (remind) {
                basic.Controls.Dialog.dialog_Success(resultMsgStr);
            }
            return true;
        }
        //根据 , 业务逻辑 , resultCode 1,登陆时间过长，跳到登陆页面；】
        if (resultCode == ConstantsFinalValue.RESULTS_CODE_TOKEN_FAILURE) {
            var resultMsgJSON = basic.JSON.convertStrToJson(resultMsgStr);
            var redirectURL = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_URL_MARK);
            var redirectTips = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_TIPS_MARK);
            basic.Controls.Dialog.dialog_Success(redirectTips);
            window.location.href = ConstantsFinalValue.WEBSITE_ROOT_PATH + redirectURL;
            return false;
        }
        //根据 , 业务逻辑 , resultCode 2,代表 服务器校验数据不合法 , 且将错误提示信息 , 写入 form表单顶部的 , 信息容器中div】
        if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_PARAMETER) {
            if (divInfoContainerObj) {
                var backstageCheckDataErrors = '';
                if (basic.JSON.isJson(resultMsgStr) == false) {
                    backstageCheckDataErrors = resultMsgStr;
                } else {
                    var errMsgJsonMap = basic.JSON.convertStrToJson(resultMsgStr);
                    for (var key in errMsgJsonMap) {
                        console.info('errMsgJsonMap[' + key + ']' + errMsgJsonMap[key]);
                        backstageCheckDataErrors += '<span style="color:red">' + errMsgJsonMap[key] + '</span><br/>';
                    }
                }
                var varDeleteHtml = '<div class="alert alert-warning" role="alert">' + backstageCheckDataErrors + '</div>';
                $('#' + divInfoContainerObj).html(varDeleteHtml);
            } else {
                basic.Controls.Dialog.dialog_Success(resultMsgStr);
            }
            return false;
        }
        //根据 , 业务逻辑 , resultCode 3,业务逻辑异常，直接提示信息】
        if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_BUSINESS) {
            basic.HandleServerReturnResult.businessLogicException(resultMsgStr);
            return false;
        }
    },

    serverException: function (resultCode, resultMsg) {
        var msg = resultMsg + '\n' + resultCode;
        basic.Controls.Dialog.dialog_Warning(msg);
    },
    businessLogicException: function (resultMsg) {
        basic.Controls.Dialog.dialog_Warning(resultMsg);
    },
    serverError: function (resultJsonData) {
        basic.Controls.Dialog.dialog_Danger('System Error');
    }
};

basic.CommonModule = {
    ajaxRequest: function (url, type, data, options, callbackBeforeSend, callbackComplete, callbackSuccess) {
        console.log('ajaxRequest , Ajax server type: ' + type + '\t\t\turl: ' + url + '\t\t\tdata: ' + JSON.stringify(data));

        $.ajax($.extend(true, {}, options, {
            url: url,
            type: type,
            data: data,
            beforeSend: function (XMLHttpRequest) {//发送请求   beforeSend:beforeSend(XMLHttpRequest)
                //console.log("ajaxRequest , Ajax server beforeSend : The corresponding button settings, failed state, and change the button text messages.");
                null != callbackBeforeSend ? callbackBeforeSend(XMLHttpRequest) : null;
            },
            success: function (resultJsonData) {//请求成功  success:callback(data, status, xmlHttpRequest)
                console.log('ajaxRequest , Ajax server success result: ');
                callbackSuccess(resultJsonData);
            },
            error: function (resultJsonData) {//请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
                console.log('ajaxRequest , Ajax server error result: ');
                basic.HandleServerReturnResult.serverError(resultJsonData);
            },
            complete: function (resultJsonData) {//请求完成  complete:complete(XMLHttpRequest, textStatus)
                // console.log('ajaxRequest , Ajax server complete result: ');
                null != callbackComplete ? callbackComplete(resultJsonData) : null;
            }
        }));
    },
    ajaxRequestHandleSuccess: function (url, type, data, options, callbackBeforeSend, callbackComplete, callbackSuccess) {
        basic.CommonModule.ajaxRequest(url, type, data, options, callbackBeforeSend, callbackComplete,
            function (resultJsonData) {
                if (basic.HandleServerReturnResult.successReturnResult(resultJsonData, false, null) == true) {
                    var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                    callbackSuccess(resultMsgStr);
                } else {
                    null != callbackComplete ? callbackComplete(resultJsonData) : null;
                }
            }
        );
    }
};

basic.Module = {
    loginModule: function (url, type, FormId, but_submitId) {

        var varParamData = $('#' + FormId).serialize();
        var but_submitObj = $('#' + but_submitId);

        basic.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, null,
            function () {
                but_submitObj.text("Loading...").attr("disabled", "disabled");
            },
            function () {
                but_submitObj.text("Login").removeAttr("disabled");
            },
            function (data) {
                var token = eval('data.' + ConstantsFinalValue.TOKEN);
                var redirect_URL = eval('data.' + ConstantsFinalValue.SERVER_REDIRECT_URL_MARK);
                $.cookie(ConstantsFinalValue.TOKEN, token);
                window.location.href = ConstantsFinalValue.WEBSITE_ROOT_PATH + redirect_URL;
            }
        );
    },
    searchModule: {
        attributes: {
            subModule: null,
            formId: null,
            formObj: null
        },
        fun_Init: function (subModule, formId) {
            //调用各子模块，自身业务的逻辑
            basic.Module.searchModule.attributes.subModule = subModule;
            basic.Module.searchModule.attributes.subModule.fun_searchModule_Init();

        },
        fun_searchButtonClick: function () {
            basic.Controls.DataTables._dataTablesObj.ajax.reload();
        },
        fun_searchResetButtonClick: function () {
            $('#' + item.page_elements.modal_Search_Form_Button_Reset).click();
        }
    },
    createModule: {
        attributes: {
            subModule: null
        },
        fun_Init: function (subModule) {
            //调用各子模块，自身业务的逻辑
            basic.Module.createModule.attributes.subModule = subModule;
            basic.Module.createModule.attributes.subModule.fun_searchModule_Init();
        }
    },
    updateModule: {
        attributes: {
            subModule: null
        },
        fun_Init: function (subModule) {
            //调用各子模块，自身业务的逻辑
            basic.Module.updateModule.attributes.subModule = subModule;
            basic.Module.updateModule.attributes.subModule.fun_searchModule_Init();
        }
    },
    deleteModule: {
        attributes: {
            subModule: null
        },
        fun_Init: function (subModule) {
            //调用各子模块，自身业务的逻辑
            basic.Module.deleteModule.attributes.subModule = subModule;
            basic.Module.deleteModule.attributes.subModule.fun_searchModule_Init();
        }
    }
};

basic.Controls = {

    DataTables: {

        initialization_options: {//DataTables初始化选项
            language: {// 国际化配置
                //  url: staticResourcesPath + '/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/datatables-plugins/i18n/Chinese_New.lang'
            },
            //特性(Features)
            responsive: true,
            serverSide: true,           //bServerSide ：开启服务器模式，使用服务器端处理配置datatable。注意：sAjaxSource参数也必须被给予为了给datatable源代码来获取所需的数据对于每个画。 这个翻译有点别扭。开启此模式后，你对datatables的每个操作 每页显示多少条记录、下一页、上一页、排序（表头）、搜索，这些都会传给服务器相应的值。
            deferRender: true,          //bDeferRender 控制Datatables的延迟渲染，可以提高初始化的速度。当datatable 使用Ajax或者JS源表的数据。这个选项设置为true,将导致datatable推迟创建表元素每个元素,直到他们都创建完成——本参数的目的是节省大量的时间。
            jQueryUI: false,

            info: true,                //bInfo ：启用或禁用表信息显示。这显示了关于数据的信息,目前在网页上,包括信息过滤数据,如果行为被执行。这个参数在bServerSide配置后需要用到。//如果这个参数不穿到后台去，服务器分页会报错，据说这个参数包含了表的所有信息
            filter: false,             //bFilter ：这个很容易明白，启用或禁用过滤数据。在datatable过滤是“智能”,它允许用户 最终输入多个关键字(空格分隔)，和每一行数据匹配,即使不是在被指定的顺序(这允许匹配多个列)。注意,如果您希望使用过滤，在datatable中必须设置为true，如果要删除默认过滤输入框和留住过滤功能,请使用{ @link DataTable.defaults.sDom }。这个最后一句不明白，不知道大家怎么理解。
            lengthChange: true,         //bLengthChange         开启一页显示多少条数据的下拉菜单，允许用户从下拉框(10、25、50和100)，注意需要分页(bPaginate：true)。
            lengthMenu: [[10, 25, 50, 100], [10, 25, 50, 100]],//lengthMenu     自定义长度菜单的选项      使用lengthMenu选项来初始化选项，此选项支持两种参数方式，一种是一维数组，一种是二维数组，此例子演示的是二维数组的用法
            displayLength: 10,                  //displayLength  默认显示的记录数
            paging: true,                      //bPaginate      开启分页功能，如果不开启，将会全部显示
            pagingType: 'simple_numbers',         //pagingType    不同的分页样式【simple - 只有上一页、下一页两个按钮；simple_numbers - 除了上一页、下一页两个按钮还有页数按钮，Datatables默认是这个；full - 有四个按钮首页、上一页、下一页、末页；full_numbers - 除首页、上一页、下一页、末页四个按钮还有页数按钮】
            ordering: false,                   //ordering      标题排序，是否允许Datatables开启排序
            processing: true,                  //bProcessing ：开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
            stateSave: false,                  //stateSave         保存状态 Datatables设置 stateSave选项后，可以保存最后一次分页信息、排序信息，当页面刷新，或者重新进入这个页面，恢复上次的状态。这个状态的保存用了html5的本地储存和session储存，这样更加有效率。如果你的数据是异步获取的，你可以使用stateSaveCallback和 stateLoadCallback选项.需要注意的是，这个特性不支持ie6、ie7 默认情况下，这个状态会保存2小时，如果你希望设置的时间更长，通过设置参数 stateDuration来初始化表格 这个参数值也可以控制是本地储存(0~更大)还是session储存(-1)下面的例子展示了Datatables设置 stateSave选项初始化后，实现的状态保存 * /
            stripeClasses: ['odd', 'even'],     //stripeClasses  为奇偶行加上样式，兼容不支持CSS伪类的场合
            aDataSort: false,

            //X Y轴宽度限制
            autoWidth: true,           //bAutoWidth ：启用或禁用自动列宽度的计算，//关闭后，表格将不会自动计算表格大小，在浏览器大化小化的时候会挤在一坨。
            scrollX: '100%',            //scrollX               设置水平滚动
            scrollXInner: '100%',
            scrollCollapse: true,               //scrollCollapse       是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变
            scrollInfinite: true,               //bScrollInfinite      是否开启不限制长度的滚动条（和sScrollY属性结合使用），不限制长度的滚动条意味着当用户拖动滚动条的时候DataTable会不断加载数据当数据集十分大的时候会有些用处，该选项无法和分页选项同时使用，分页选项会被自动禁止，注意，额外推荐的滚动条会优先与该选项
            //scrollX: 800,                       //sScrollX              是否开启水平滚动，当一个表格过于宽以至于无法放入一个布局的时候，或者表格有太多列的时候，你可以开启该选项从而在一个可横向滚动的视图里面展示表格，该属性可以是css设置，或者一个数字（作为像素量度来使用）
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            searching: false    //禁用原生搜索
        },
        column: {
            rowDetails: {//行详细信息
                className: 'details-control',
                orderable: false,
                width: '5px',
                data: null,
                defaultContent: ''
            }
        },
        column_render: {//常用render可以抽取出来，如日期时间、头像等
            /**
             * @return {string}
             */
            FulName_LastName_FirstName: function (data, type, row) {
                var lastName = row.lastName;
                var firstName = row.firstName;
                return lastName + ' ' + firstName;
            },
            DateType_Time: function (data, type, row) {
                data = data || '';
                return moment(data).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES);
            },
            DateType_Date: function (data, type, row) {
                data = data || '';
                return moment(data).format(ConstantsFinalValue.FORMAT_DATE);
            },
            DateType_DateTime: function (data, type, row) {
                data = data || '';
                return moment(data).format(ConstantsFinalValue.FORMAT_DATE_TIME);
            },
            /**
             * @return {string}
             */
            Department: function (data, type, row) {
                data = data || '';
                var departmentName = '';
                $.each(item.attributes_BaseData.departmentList, function (i, department) {
                    if (data == department.id) {
                        departmentName = department.name;
                    }
                });
                return departmentName;
            },
            /**
             * @return {string}
             */
            Role: function (data, type, row) {
                data = data || '';
                var roles = data.split(ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);//2|3|4
                var roleName = '';
                if (roles instanceof Array) {
                    $.each(item.attributes_BaseData.roleList, function (i, role) {
                        for (var i = 0, j = roles.length; i < j; i++) {
                            if (roles[i] == role.id) {
                                roleName += role.name + '&nbsp;&nbsp;';
                            }
                        }
                    });
                }
                return roleName;
            },
            /**
             * @return {string}
             */
            Task_Create_Mode: function (data, type, row) {
                data = data || '';
                return (data == 0) ? 'System' : 'Manual';
            },
            /**
             * @return {string}
             */
            Task_ExecutionDate: function (data, type, row) {
                var executionDate = basic.DataFormatConversion.DateType_Date(row.executionDate);
                var clientBranchWindowStartTime = basic.DataFormatConversion.DateType_Time(row.clientBranchWindowStartTime);
                var clientBranchWindowEndTime = basic.DataFormatConversion.DateType_Time(row.clientBranchWindowEndTime);
                return executionDate + ' ' + clientBranchWindowStartTime + ' - ' + clientBranchWindowEndTime;
            },
            /**
             * @return {string}
             */
            Task_Operate: function (data, type, row) {
                return '<button type="button" class="btn btn-primary btn-xs" onclick="Basic.crudImplClass.operateModule.fun_btn_Operate_Click(' + row.id + ')">Operate</button>';
            },
            /**
             * @return {string}
             */
            ClientBranch_WindowTime: function (data, type, row) {
                var clientBranchWindowStartTime = basic.DataFormatConversion.DateType_Time(row.clientBranchWindowStartTime);
                var clientBranchWindowEndTime = basic.DataFormatConversion.DateType_Time(row.clientBranchWindowEndTime);
                var clientBranchWindowDuration = basic.DataFormatConversion.DateType_Time(row.clientBranchWindowDuration);
                return clientBranchWindowStartTime + ' - ' + clientBranchWindowEndTime;
            },
            /**
             * @return {string}
             */
            ClientBranch_Operate: function (data, type, row) {
                return '<button type="button" class="btn btn-primary btn-xs" onclick="Basic.crudImplClass.operateModule.fun_btn_Operate_Click(' + row.id + ')">Operate</button>';
            }
        }

    },

    DateRangePicker: {

        option_single_calendar: {
            maxDate: moment(),                                          //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期
            showDropdowns: true,                                        //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
            singleDatePicker: true,                                     //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
            locale: {                                                   //locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
                format: ConstantsFinalValue.FORMAT_DATE
            }
        },

        option_single_calendar_TaskAdd: {
            startDate: moment(),                                        //startDate (日期对象,对象或字符串)时刻开始的最初选择的日期范围
            //endDate: moment(),                                        //endDate (日期对象,时刻对象或字符串)的最初选择的日期范围
            minDate: moment(),                                          //maxDate (日期对象,时刻对象或字符串):用户可以选择最新的日期
            maxDate: moment().subtract(-6, 'days').startOf('day'),      //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期,最多增加7天之内的任务
            //showDropdowns: true,                                      //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
            singleDatePicker: true,                                     //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
            locale: {                                                   //locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
                format: ConstantsFinalValue.FORMAT_DATE
            }
        },

        option_dual_calendar: {
            //startDate: moment().startOf('day'),                       //startDate (日期对象,对象或字符串)时刻开始的最初选择的日期范围
            //endDate: moment(),                                        //endDate (日期对象,时刻对象或字符串)的最初选择的日期范围
            //minDate: '01/01/2012',                                    //maxDate (日期对象,时刻对象或字符串):用户可以选择最新的日期
            maxDate: moment(),                                          //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期
            dateLimit: {months: 1},                                     //dateLimit (对象)之间的最大跨度选择开始和结束日期。 可以有属性可以添加到一个时刻对象(即天,个月)

            showDropdowns: true,                                        //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
            showWeekNumbers: false,                                     //showWeekNumbers (布尔)显示本地化星期数字每周的日历【是否显示第几周】
            showISOWeekNumbers: false,                                  //showWeekNumbers (布尔)ISO星期数字每周的日历

            // timePicker: true,                                        //timePicker (布尔)允许选择日期和时间,不只是日期【是否显示小时和分钟】
            // timePickerIncrement: 1,                                  //timePickerIncrement (数量):增加分钟选择列表的时间(即30只允许选择*以0 - 30)【时间的增量，单位为分钟】
            // timePicker24Hour: true,                                  //timePicker24Hour (布尔)使用24小时,而不是12个小时的时间,消除了AM / PM的选择【是否使用24小时制来显示时间】
            // timePickerSeconds: true,                                 //timePickerSeconds (布尔)显示timePicker秒

            opens: 'left',                                              //日期选择框的弹出位置 left、right、center，(字符串:“左”/“正确的”/“中心”)选择是否出现左对齐,右,或集中在HTML元素上
            drops: 'down',                                              //日期选择框的，显示上面还是下面   up、down，(字符串:“下降”或“了”)选择是否出现以下(默认)或以上的HTML元素
            buttonClasses: 'btn btn-sm',                                //buttonClasses (数组)的CSS类名,将被添加到选择器中的所有按钮
            applyClass: 'btn-success',                                  //applyClass (字符串)CSS类的字符串将被添加到应用按钮
            cancelClass: 'btn-default',                                 //cancelClass (字符串)CSS类的字符串将被添加到取消按钮

            singleDatePicker: false,                                    //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
            //autoApply: true,                                          //autoApply (布尔)隐藏和取消按钮,并自动应用一个新的日期范围只要两个日期或一个预定义的选择范围
            linkedCalendars: false,                                     //linkedCalendars (布尔)启用时,两个日历显示总是会连续两个月(即1月和2月),并且都是先进当点击左边或者右边的箭头上方的日历。 禁用时,两个日历可以单独先进和显示一个月/年。
            //parentEl                                                  //(字符串)jQuery选择器的父元素的日期范围选择器将被添加到,如果不提供,这将是“身体”
            //isInvalidDate                                             //(函数)传递一个函数中的每个日期两个 日历显示之前,可能表明是否返回true或false 日期应该可供选择。
            //autoUpdateInput: true,                                    //autoUpdateInput (布尔)表明日期范围选择器是否应该 自动更新的价值 <输入> 元素附加到 在初始化时,所选日期的变化。
            alwaysShowCalendars: false,                                 //alwaysShowCalendars (布尔):通常情况下,如果您使用 范围 选项指定预定义的日期范围,没有显示日历选择一个自定义的日期范围,直到用户点击“自定义范围”。 这个选项被设置为true时,日历选择一个自定义的日期范围总是显示。

            ranges: {                                                   //ranges: (对象)设置预定义的用户可以选择日期范围。 每个键的标签范围,及其与两个日期代表值数组的边界范围
                //'最近1小时': [moment().subtract(1, 'hours'), moment()],
                'Today': [moment().startOf('days'), moment()],
                'Yesterday': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')]
            },
            locale: {//locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
                // applyLabel: '确定',
                // cancelLabel: '取消',
                // fromLabel: '起始时间',
                // toLabel: '结束时间',
                // customRangeLabel: '自定义',
                // daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                // monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                // firstDay: 1
                format: ConstantsFinalValue.FORMAT_DATE_DualCalendar_Short_format
            }
        },

        daterangepicker_input_readonly: function () {
            $('.input-mini').attr('readonly', 'readonly');//设置定义选择日期是，将日期输入框设置为只读状态
        }
    }

};


item = {
    attributes_BaseData: {
        vehicleCurrentCoordinates: null,
        departmentList: null,
        roleList: null,
        subModule: null
    },
    attributes_PageElements: {
        btn_Create_Id: 'btn_Create',
        btn_Delete_Id: 'btn_Delete',
        btn_Update_Id: 'btn_Update',
        btn_Search_Id: 'btn_Search',
        btn_Search_Reset_Id: 'btn_Search_Reset',

        modal_Search_Form_Id: 'modal_Search_Form',
        modal_Search_Form_Button_Reset_Id: 'modal_Search_Form_Button_Reset',

        modal_Create_Id: 'modal_Create',
        modal_Create_Form_Id: 'modal_Create_Form',
        modal_Create_Form_Div_InfoContainer_Id: 'modal_Create_Form_InfoContainer',
        modal_Create_Form_Button_Cancel_Id: 'modal_Create_Form_Button_Cancel',
        modal_Create_Form_Button_Reset_Id: 'modal_Create_Form_Button_Reset',
        modal_Create_Form_Button_Submit_Id: 'modal_Create_Form_Button_Submit',

        modal_Delete_Id: 'modal_Delete',
        modal_Delete_Form_Id: 'modal_Delete_Form',
        modal_Delete_Form_Div_InfoContainer_Id: 'modal_Delete_Form_InfoContainer',
        modal_Delete_Form_Button_Cancel_Id: 'modal_Delete_Form_Button_Cancel',
        modal_Delete_Form_Button_Reset_Id: 'modal_Delete_Form_Button_Reset',
        modal_Delete_Form_Button_Submit_Id: 'modal_Delete_Form_Button_Submit',

        modal_Update_Id: 'modal_Update',
        modal_Update_Form_Id: 'modal_Update_Form',
        modal_Update_Form_Div_InfoContainer_Id: 'modal_Update_Form_InfoContainer',
        modal_Update_Form_Button_Cancel_Id: 'modal_Update_Form_Button_Cancel',
        modal_Update_Form_Button_Reset_Id: 'modal_Update_Form_Button_Reset',
        modal_Update_Form_Button_Submit_Id: 'modal_Update_Form_Button_Submit'
    }
};
item.bindingNavAjaxUrl = function (urlPath) {
    var varParamData = '';
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + urlPath;
    var type = 'GET';
    basic.CommonModule.ajaxRequest(url, type, varParamData, {async: false}, null, null,
        function (data) {
            var mainRightAreaDivObj = $('#main_RightArea');
            mainRightAreaDivObj.html('');
            mainRightAreaDivObj.html(data);
        }
    );
};
item.fun_Init = function (subModule) {
    item.attributes_BaseData.subModule = subModule;
    basic.Module.searchModule.fun_Init(subModule);
    basic.Module.createModule.fun_Init(subModule);
    basic.Module.updateModule.fun_Init(subModule);
    basic.Module.deleteModule.fun_Init(subModule);
};

item.fun_InitializationControls = {
    fun_SelectionBox_Status: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();
        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select Status...</option>');
        }
        html_SelectionBox_Obj.append('<option value="Enable">Enable</option>');
        html_SelectionBox_Obj.append('<option value="Disable">Disable</option>');
    },
    fun_RadioBox_Status: function (radioBoxDiv_Id) {
        var radioBoxDiv_Obj = $('#' + radioBoxDiv_Id);
        radioBoxDiv_Obj.html('');
        var insertHTML = '' +
            '<label><input type="radio" name="status" value="Enable">Enable&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label><input type="radio" name="status" value="Disable">Disable</label>';
        radioBoxDiv_Obj.html(insertHTML);
    },

    fun_SelectionBox_Gender: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();

        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select Gender...</option>');
        }

        html_SelectionBox_Obj.append('<option value="Male">Male</option>');
        html_SelectionBox_Obj.append('<option value="Female">Female</option>');
    },
    fun_RadioBox_Gender: function (radioBoxDiv_Id) {
        var radioBoxDiv_Obj = $('#' + radioBoxDiv_Id);
        radioBoxDiv_Obj.html('');
        var insertHTML = '' +
            '<label><input type="radio" name="gender" value="Male">Male&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label><input type="radio" name="gender" value="Female">Female</label>';
        radioBoxDiv_Obj.html(insertHTML);
    },

    fun_Calendar_DateRangePicker_Single: function (html_TextBox_Id, htmlForm_Id) {
        var html_TextBox_Obj = $('#' + html_TextBox_Id);

        html_TextBox_Obj.daterangepicker(
            basic.Controls.DateRangePicker.option_single_calendar,
            function (start, end, label) {//格式化日期显示框
                console.log('fun_Calendar_DateRangePicker_Single: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
                html_TextBox_Obj.val(start.format(ConstantsFinalValue.FORMAT_DATE));
                if (htmlForm_Id) {
                    $('#' + htmlForm_Id).bootstrapValidator('revalidateField', html_TextBox_Obj.attr('name'));
                }
            }
        ).val('');
    },
    fun_Calendar_DateRangePicker_Dual: function (html_TextBox_Id, html_TextBox_StartTime_Id, html_TextBox_EndTime_Id) {
        var html_TextBox_Obj = $('#' + html_TextBox_Id);
        var html_TextBox_StartTime_Obj = $('#' + html_TextBox_StartTime_Id);
        var html_TextBox_EndTime_Obj = $('#' + html_TextBox_EndTime_Id);
        //时间插件
        html_TextBox_Obj.daterangepicker(basic.Controls.DateRangePicker.option_dual_calendar,
            function (start, end, label) {//格式化日期显示框
                html_TextBox_StartTime_Obj.val(start.format(ConstantsFinalValue.FORMAT_DATE_TIME));
                html_TextBox_EndTime_Obj.val(end.format(ConstantsFinalValue.FORMAT_DATE_TIME));
                console.info('fun_Calendar_DateRangePicker_Dual: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
            }
        ).val('');
        basic.Controls.DateRangePicker.daterangepicker_input_readonly();
    },


    fun_SelectionBox_DepartmentList: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();
        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select Department...</option>');
        }
        $.each(item.attributes_BaseData.departmentList, function (i, department) {
            html_SelectionBox_Obj.append('<option value="' + department.id + '">' + department.name + '</option>');
        });
    },

    fun_SelectionBox_RoleLis: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();
        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select Roles...</option>');
        }
        $.each(item.attributes_BaseData.roleList, function (i, role) {
            html_SelectionBox_Obj.append('<option value="' + role.id + '">' + role.name + '</option>');
        });
    },


    // Task 模块
    fun_Calendar_DateRangePicker_Single_TaskAdd: function (html_TextBox_Id, htmlForm_Id) {
        var html_TextBox_Obj = $('#' + html_TextBox_Id);
        //时间插件
        html_TextBox_Obj.daterangepicker(basic.Controls.DateRangePicker.option_single_calendar_TaskAdd,
            function (start, end, label) {//格式化日期显示框
                console.info('New date range selected: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
                html_TextBox_Obj.val(start.format(ConstantsFinalValue.FORMAT_DATE));
                if (htmlForm_Id) {
                    $('#' + htmlForm_Id).bootstrapValidator('revalidateField', html_TextBox_Obj.attr('name'));
                }
            }
        ).val('');
    },
    fun_SelectionBox_TaskType: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();
        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select TaskType...</option>');
        }
        html_SelectionBox_Obj.append('<option value="Counter">Counter</option>');
        html_SelectionBox_Obj.append('<option value="Receive_Money">Receive_Money</option>');
        html_SelectionBox_Obj.append('<option value="Give_Money">Give_Money</option>');
        html_SelectionBox_Obj.append('<option value="Maintain_ATM">Maintain_ATM</option>');
    },
    fun_SelectionBox_TaskStatus: function (html_SelectionBox_Id, boolean_IncreaseDefault) {
        var html_SelectionBox_Obj = $('#' + html_SelectionBox_Id);
        html_SelectionBox_Obj.empty();
        if (boolean_IncreaseDefault == true) {
            html_SelectionBox_Obj.append('<option value="">Select Status...</option>');
        }
        html_SelectionBox_Obj.append('<option value="Wait">Wait</option>');
        html_SelectionBox_Obj.append('<option value="Assignment">Assignment</option>');
        html_SelectionBox_Obj.append('<option value="Receive">Receive</option>');
        html_SelectionBox_Obj.append('<option value="Warehousing">Warehousing</option>');
        html_SelectionBox_Obj.append('<option value="Settlement">Settlement</option>');
        html_SelectionBox_Obj.append('<option value="Fail">Fail</option>');
        html_SelectionBox_Obj.append('<option value="Success">Success</option>');
    },
    //客户机构，cronExpression
    fun_ClientBranch_cronExpression_CheckBox: function (html_CheckBox_Div_Id, html_CheckBox_Name) {
        var html_CheckBox_Div_Obj = $('#' + html_CheckBox_Div_Id);
        html_CheckBox_Div_Obj.html('');
        var insertHTML = '' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="SUN">SUN&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="MON">MON&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="TUE">TUE&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="WED">WED&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="THU">THU&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="FRI">FRI&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '<label> <input type="checkbox" name="' + html_CheckBox_Name + '" value="SAT">SAT&nbsp;&nbsp;&nbsp;&nbsp;</label>';
        html_CheckBox_Div_Obj.html(insertHTML);
    }
};

item.fun_AjaxServerData = {
    ajax_GetAll_DepartmentAndRole: function () {
        var varParamData = '';
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/getDepartmentAndRoleALLByStatusIsActivated.php';
        var type = 'GET';
        basic.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {async: false}, null, null,
            function (data) {
                item.attributes_BaseData.departmentList = data.departmentList;
                item.attributes_BaseData.roleList = data.roleList;
            }
        );
    },
    ajax_GetAll_VehicleCurrentCoordinates: function () {
        var varParamData = '';
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxGetAllVehicleCurrentCoordinates.php';
        var type = 'GET';
        basic.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {async: false}, null, null,
            function (data) {
                item.attributes_BaseData.vehicleCurrentCoordinates = data;
            }
        );
    }
};