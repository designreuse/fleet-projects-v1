/**
 * Created by supeng on 2016/06/15.
 */
var assupg = {};
assupg.extend = {};
/*
 * MAP对象，实现MAP功能
 *
 * 接口：
 * size()     获取MAP元素个数
 * isEmpty()    判断MAP是否为空
 * clear()     删除MAP所有元素
 * put(key, value)   向MAP中增加元素（key, value)
 * remove(key)    删除指定KEY的元素，成功返回True，失败返回False
 * get(key)    获取指定KEY的元素值VALUE，失败返回NULL
 * element(index)   获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
 * containsKey(key)  判断MAP中是否含有指定KEY的元素
 * containsValue(value) 判断MAP中是否含有指定VALUE的元素
 * values()    获取MAP中所有VALUE的数组（ARRAY）
 * keys()     获取MAP中所有KEY的数组（ARRAY）
 *
 * 例子：
 * var map = new Map();
 *
 * map.put("key", "value");
 * var val = map.get("key")
 * ……
 *
 */
assupg.extend.Map = function () {
    this.elements = new Array();

    //获取MAP元素个数
    this.size = function () {
        return this.elements.length;
    };

    //判断MAP是否为空
    this.isEmpty = function () {
        return (this.elements.length < 1);
    };

    //删除MAP所有元素
    this.clear = function () {
        this.elements = new Array();
    };

    //向MAP中增加元素（key, value)
    this.put = function (_key, _value) {
        this.elements.push({
            key: _key,
            value: _value
        });
    };

    //删除指定KEY的元素，成功返回True，失败返回False
    this.remove = function (_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //获取指定KEY的元素值VALUE，失败返回NULL
    this.get = function (_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    };

    //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
    this.element = function (_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    };

    //判断MAP中是否含有指定KEY的元素
    this.containsKey = function (_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //判断MAP中是否含有指定VALUE的元素
    this.containsValue = function (_value) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //获取MAP中所有VALUE的数组（ARRAY）
    this.values = function () {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    }

    //获取MAP中所有KEY的数组（ARRAY）
    this.keys = function () {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    }
};
assupg.extend.String = {

    trim: function (str) {
        return str.replace(/(^\s+) | (\s+$)/g, "");
    }
};


assupg.JSON = {};
/***** 判断是否为json对象 *******
 * @param obj: 对象（可以是jq取到对象）
 * @return boolean 是否是json对象 true/false
 */
assupg.JSON.isJson = function (obj) {
    return typeof(obj) == 'object' && Object.prototype.toString.call(obj).toLowerCase() == '[object object]' && !obj.length;
};
assupg.JSON.isJsonString = function (jsonStr) {
    return !jsonStr.match("^\{(.+:.+,*){1,}\}$") ? false : true;
};
/**
 *  json字符串，转换成JSON对象
 * @param strJson   json格式的字符串
 * @return JSON对象
 */
assupg.JSON.convertStrToJson = function (strJson) {
    return JSON.parse(strJson);
};
assupg.JSON.convertJsonToStr = function (ObjJson) {
    return JSON.stringify(ObjJson);
};

assupg.FORM = {};
assupg.FORM.serializeJSON = function (formObj) {
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
};


assupg.DataFormatConversion = {};
assupg.DataFormatConversion.DateType_Time = function (data) {
    return moment(data).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES);
};
assupg.DataFormatConversion.DateType_Date = function (data) {
    return moment(data).format('YYYY-MM-DD');
};
assupg.DataFormatConversion.DateType_DateTime = function (data) {
    return moment(data).format('YYYY-MM-DD HH:mm:ss');
};


assupg.HandleServerReturnResult = {};
assupg.HandleServerReturnResult.successReturnResult = function (resultJsonData, remind, divInfoContainerObj) {
    var strJSONObj = {};
    if (assupg.JSON.isJson(resultJsonData) == false) {
        strJSONObj = assupg.JSON.convertStrToJson(resultJsonData);
        console.info('server return data type:  not json Object')
    } else {
        strJSONObj = resultJsonData;
        console.info('server return data type:  is json Object')
    }
    var resultCode = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_CODE_MARK);
    var resultMsgStr = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);

    try {
        console.info(strJSONObj);
        //console.info(assupg.JSON.convertStrToJson(resultMsgStr));
    } catch (err) {
        //console.info(resultMsgStr);
    }

    //根据 , 业务逻辑 , resultCode -1,代表服务器系统异常；】且将错误提示信息
    if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_SYSTEM) {
        assupg.HandleServerReturnResult.serverException(resultMsgStr);
        return false;
    }
    //根据 , 业务逻辑 , resultCode 0,代表处理成功；】根据调用者，显示对话框给予提示
    if (resultCode == ConstantsFinalValue.RESULTS_CODE_SUCCESS) {
        if (remind) {
            controls.Dialog.dialog_Success(resultMsgStr);
        }
        return true;
    }
    //根据 , 业务逻辑 , resultCode 1,登陆时间过长，跳到登陆页面；】
    if (resultCode == ConstantsFinalValue.RESULTS_CODE_TOKEN_FAILURE) {
        var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);
        var redirectURL = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_URL_MARK);
        var redirectTips = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_TIPS_MARK);
        controls.Dialog.dialog_Success(redirectTips);
        window.location.href = ConstantsFinalValue.WEBSITE_ROOT_PATH + redirectURL;
        return false;
    }
    //根据 , 业务逻辑 , resultCode 2,代表 服务器校验数据不合法 , 且将错误提示信息 , 写入 form表单顶部的 , 信息容器中div】
    if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_PARAMETER) {
        if (divInfoContainerObj) {
            var backstageCheckDataErrors = '';
            if (assupg.JSON.isJson(resultMsgStr) == false) {
                backstageCheckDataErrors = resultMsgStr;
            } else {
                var errMsgJsonMap = assupg.JSON.convertStrToJson(resultMsgStr);
                for (var key in errMsgJsonMap) {
                    console.info('errMsgJsonMap[' + key + ']' + errMsgJsonMap[key]);
                    backstageCheckDataErrors += '<span style="color:red">' + errMsgJsonMap[key] + '</span><br/>';
                }
            }
            var varDeleteHtml = '<div class="alert alert-warning" role="alert">' + backstageCheckDataErrors + '</div>';
            $('#' + divInfoContainerObj).html(varDeleteHtml);
        } else {
            controls.Dialog.dialog_Success(resultMsgStr);
        }
        return false;
    }
    //根据 , 业务逻辑 , resultCode 3,业务逻辑异常，直接提示信息】
    if (resultCode == ConstantsFinalValue.RESULTS_CODE_ERROR_BUSINESS) {
        assupg.HandleServerReturnResult.businessLogicException(resultMsgStr);
        return false;
    }

};
assupg.HandleServerReturnResult.serverException = function (resultCode, resultMsg) {
    var msg = resultMsg + '\n' + resultCode;
    controls.Dialog.dialog_Warning(msg);
};
assupg.HandleServerReturnResult.businessLogicException = function (resultMsg) {
    controls.Dialog.dialog_Warning(resultMsg);
};
assupg.HandleServerReturnResult.serverError = function (resultJsonData) {
    controls.Dialog.dialog_Danger('System Error');
};


assupg.CommonModule = {};
/**
 * 通用的Ajax请求，
 * @param url   request server url
 * @param type  request type【post get】
 * @param data  request data
 * @param options
 * @param callbackBeforeSend    用于，架设发送请求之前，回调函数        【一般用于：调用请求头，若将按钮设置不可点击状态】
 * @param callbackSuccess       用于，服务返回成功结果，回调函数
 * @param callbackComplete      用于，请求完成之后，回调函数           【恢复之前设置按钮不可用，还原】
 */
assupg.CommonModule.ajaxRequest = function (url, type, data, options, callbackBeforeSend, callbackComplete, callbackSuccess) {

    console.log('ajaxRequest , Ajax server type: ' + type + '\t\t\turl: ' + url + '\t\t\tdata: ' + JSON.stringify(data));
    // console.log('ajaxRequest , Ajax server url : ' + url);
    // console.log('ajaxRequest , Ajax server data: ' + JSON.stringify(data));

    $.ajax($.extend(true, {}, options, {
        url: url,
        type: type,
        data: data,
        beforeSend: function (XMLHttpRequest) {//发送请求   beforeSend:beforeSend(XMLHttpRequest)
            //console.log("ajaxRequest , Ajax server beforeSend : The corresponding button settings, failed state, and change the button text messages.");
            null != callbackBeforeSend ? callbackBeforeSend(XMLHttpRequest) : null;
        },
        success: function (resultJsonData) {//请求成功  success:callback(data, status, xmlHttpRequest)
            // console.log('ajaxRequest , Ajax server success result: ');
            callbackSuccess(resultJsonData);
        },
        error: function (resultJsonData) {//请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
            console.error('ajaxRequest , Ajax server error result: ');
            assupg.HandleServerReturnResult.serverError(resultJsonData);
        },
        complete: function (resultJsonData) {//请求完成  complete:complete(XMLHttpRequest, textStatus)
            // console.log('ajaxRequest , Ajax server complete result: ');
            null != callbackComplete ? callbackComplete(resultJsonData) : null;
        }
    }));
};
assupg.CommonModule.ajaxRequestHandleSuccess = function (url, type, data, options, callbackBeforeSend, callbackComplete, callbackSuccess) {

    assupg.CommonModule.ajaxRequest(url, type, data, options, callbackBeforeSend, callbackComplete,
        function (resultJsonData) {
            if (assupg.HandleServerReturnResult.successReturnResult(resultJsonData, false, null) == true) {
                var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                callbackSuccess(resultMsgStr);
            } else {
                null != callbackComplete ? callbackComplete(resultJsonData) : null;
            }
        }
    );
};

assupg.CommonModule.login = function (url, type, FormId, but_submitId) {
    var varParamData = $('#' + FormId).serialize();
    var but_submitObj = $('#' + but_submitId);
    assupg.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, null,
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
};

assupg.Controls = {};
assupg.Controls.DataTables = {

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

};
assupg.Controls.DateRangePicker = {

    option_single_calendar: {
        maxDate: moment(),                                          //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期
        showDropdowns: true,                                        //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
        singleDatePicker: true,                                     //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
        locale: {                                                   //locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
            format: ConstantsFinalValue.FORMAT_DATE
        }
    }
    ,

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
    }
    ,

    option_dual_calendar: {
        //startDate: moment().startOf('day'),                       //startDate (日期对象,对象或字符串)时刻开始的最初选择的日期范围
        //endDate: moment(),                                        //endDate (日期对象,时刻对象或字符串)的最初选择的日期范围
        //minDate: '01/01/2012',                                    //maxDate (日期对象,时刻对象或字符串):用户可以选择最新的日期
        maxDate: moment(),                                          //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期
        dateLimit: {
            months: 1
        }
        ,                                     //dateLimit (对象)之间的最大跨度选择开始和结束日期。 可以有属性可以添加到一个时刻对象(即天,个月)

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
        }
        ,
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
    }
    ,

    daterangepicker_input_readonly: function () {
        $('.input-mini').attr('readonly', 'readonly');//设置定义选择日期是，将日期输入框设置为只读状态
    }
};
/**
 * <pre>
 * 地图对象
 *  map = new google.maps.Map(document.getElementById("map"), {...});
 *      （您可以创建该类的多个实例–每个对象都将定义页面上的一个不同地图。）我们利用 JavaScript new 运算符来新建该类的实例。
 *      当您新建地图实例时，您需要在页面中以地图容器形式指定一个 div HTML 元素。HTML 节点是 JavaScript document 对象的子项，我们通过 document.getElementById() 方法获取对该元素的引用。
 *
 *      Map(mapDiv:Node, opts?:MapOptions )
 *
 *
 *  每个地图都有两个必需选项：center 和 zoom。
 *
 *  disableDefaultUI
 *      默认用户界面
 *          默认情况下，如果地图过小 (200x200px)，所有控件都不会显示。您可以通过将控件显式设置为可见来重写这一行为。请参阅向地图添加控件。
 *          控件的行为和显示方式对于移动设备和桌面设备都一样。
 *          此外，键盘处理功能在所有设备上都默认处于启用状态。
 *
 *      禁用默认用户界面
 *          有时，您可能需要完全关闭 API 的默认用户界面设置。要执行此操作，请将地图的 disableDefaultUI 属性（在 MapOptions 对象中）设置为 true。
 *          此属性可禁用 Google Maps API 中所有自动执行的用户界面行为。
 *
 *  控件
 *      控件选项
 *          有些控件是可以配置的，允许您更改它们的行为或外观。例如，地图类型控件可以水平栏或下拉菜单形式显示。
 *
 *          用于更改地图类型控件的选项位于 mapTypeControlOptions 字段中。地图类型控件可能出现在以下某个 style 选项中：
 *              google.maps.MapTypeControlStyle.HORIZONTAL_BAR：用于在水平栏中将一组控件显示为如 Google 地图中所示的按钮
 *              google.maps.MapTypeControlStyle.DROPDOWN_MENU：用于显示单个按钮控件，以便您通过下拉菜单选择地图类型
 *              google.maps.MapTypeControlStyle.DEFAULT：用于显示默认行为，该行为取决于屏幕大小且可能会在以后的 API 版本中有所变化
 *
 *      控件定位
 *              zoomControlOptions: {
     *                       position: google.maps.ControlPosition.LEFT_CENTER
     *              }
 *
 *           TOP_CENTER：表示控件应沿着地图顶部中心放置
 *           TOP_LEFT：表示控件应沿着地图左上角放置，控件的所有子元素“流”向顶部中心
 *           TOP_RIGHT：表示控件应沿着地图右上角放置，控件的所有子元素“流”向顶部中心
 *           LEFT_TOP：表示控件应沿着地图左上角放置，但应位于任何 TOP_LEFT 元素下方
 *           RIGHT_TOP：表示控件应沿着地图右上角放置，但应位于任何 TOP_RIGHT 元素下方
 *           LEFT_CENTER：表示控件应沿着地图左侧放置在 TOP_LEFT 和 BOTTOM_LEFT 之间的中心位置
 *           RIGHT_CENTER：表示控件应沿着地图右侧放置在 TOP_RIGHT 和 BOTTOM_RIGHT 之间的中心位置
 *           LEFT_BOTTOM：表示控件应沿着地图左下角放置，但应位于任何 BOTTOM_LEFT 元素上方
 *           RIGHT_BOTTOM：表示控件应沿着地图右下角放置，但应位于任何 BOTTOM_RIGHT 元素上方
 *           BOTTOM_CENTER：表示控件应沿着地图底部中心放置
 *           BOTTOM_LEFT：表示控件应沿着地图左下角放置，控件的所有子元素“流”向底部中心
 *           BOTTOM_RIGHT：表示控件应沿着地图右下角放置，控件的所有子元素“流”向底部中心
 *
 * </pre>
 */
assupg.Controls.GoogleMap = {
    mapDivElement_Id: null,
    mapDivElement_Obj: null,

    initializationControls: {
        fun_initialization: function (mapDivElement_Id, callbackFunction) {
            var mapDivElement_Obj = document.getElementById(mapDivElement_Id);
            assupg.Controls.GoogleMap.mapDivElement_Id = mapDivElement_Id;
            assupg.Controls.GoogleMap.mapDivElement_Obj = mapDivElement_Obj;
            assupg.Controls.GoogleMap.initializationControls.fun_detectBrowser(mapDivElement_Obj);

            var key = 'AIzaSyBK_zsuIZmWuL0UmumAlk48meB6JVuzy4U';
            var language = 'zh-CN';     //参数：language语言本地化 es 西班牙语  en 英语 zh-CN 简体中文
            var version = 3;            //参数：v版本控制 为确保不因版本轮转而引发问题，我们建议您在引导程序中显式地指定 API 的当前发行版本号。例如，v=3.21。为避免随时间推移降至 API 的冻结版本，必须在 Google Maps JS API v3 通知和公告组中订阅 Google 提供的版本轮转通知。
            var signed_in = true;       //如需在通过 Google Maps JavaScript API 创建的地图上启用登录，请加载 v3.18 或更高版本 API 以及附加的 signed_in=true 参数。
            var callback = callbackFunction || 'assupg.Controls.GoogleMap.initializationControls.fun_defaultInitMap';
            if (typeof(google) == 'undefined') {
                $.getScript('https://maps.googleapis.com/maps/api/js?key=' + key + '&callback=' + callback + '&language=' + language + '&v=' + version + '&signed_in=' + signed_in);
            } else {
                eval(callbackFunction)();
            }
        },
        /**
         * Google Maps JavaScript API 经过精心设计，可以快速加载到并流畅运行于移动设备。特别是，我们的开发一直侧重于高级移动设备，如 Android 和 iOS 手机。
         * 移动设备的屏幕尺寸比常规的桌面浏览器要小。此外，它们通常还具有这些设备专属的特殊行为（例如“捏拉缩放”）。如果您希望自己的应用能够在移动设备上流畅运行，我们建议您执行以下操作：
         *
         * 可以通过在 DOM 中检查 navigator.userAgent 属性，来检测 iPhone 和 Android 设备：
         *
         * 这允许您更改针对特定设备的布局，因为我们已经在此完成针对每款设备更改屏幕实际尺寸。
         *
         * Android 和 iOS 设备遵守以下 <meta> 标记：
         *  <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
         *  此设置指明地图应全屏显示且不能由用户调整大小。请注意，iPhone 的 Safari 浏览器要求在页面的 <head> 元素内包含此 <meta> 标记。
         */
        fun_detectBrowser: function (html_Div_Map_Obj) {
            var useragent = navigator.userAgent;
            if (useragent.indexOf('iPhone') != -1 || useragent.indexOf('Android') != -1) {
                html_Div_Map_Obj.style.width = '100%';
                html_Div_Map_Obj.style.height = '100%';
            } else {
                html_Div_Map_Obj.style.width = '100%';
                html_Div_Map_Obj.style.height = '700px';
            }
        },
        fun_defaultInitMap: function () {
        }
    },
    LatLng: {
        fun_createLatLng: function (lat, lng) {
            return new google.maps.LatLng({lat: parseFloat(lat), lng: parseFloat(lng)});
        }
    },
    Map: {
        fun_mapOptions: function (centerLatLng) {
            return {
                center: centerLatLng,
                zoom: 12,
                disableDefaultUI: false,        //默认用户界面    默认情况下，如果地图过小 (200x200px)，所有控件都不会显示。

                zoomControl: true,              //zoomControl：用于启用/禁用缩放控件。默认情况下，此控件可见并显示在地图右下角。zoomControlOptions 字段用于进一步指定要用于此控件的 ZoomControlOptions
                zoomControlOptions: {
                    //position: google.maps.ControlPosition.LEFT_CENTER
                },
                scaleControl: true,             //scaleControl：用于启用/禁用比例控件，该控件可提供一个简单的地图比例尺。默认情况下，此控件不可见。启用后，它将始终显示在地图右下角。scaleControlOptions 用于进一步指定要用于此控件的 ScaleControlOptions
                streetViewControl: true,        //streetViewControl：用于启用/禁用街景小人控件，该控件可让用户激活 Street View 全景图。默认情况下，此控件可见并显示在地图右下角。streetViewControlOptions 字段用于进一步指定要用于此控件的 StreetViewControlOptions
                rotateControl: true,            //rotateControl：用于显示/取消显示旋转控件，该控件可控制 45° 图像的方向。默认情况下，该控件是否显示取决于给定地图类型在当前的缩放级别和位置上是否存在 45° 图像。您可以通过设置地图的 rotateControlOptions 以指定要使用的 RotateControlOptions，来更改控件的行为。不过，如果当前没有可用的 45° 图像，则无法显示该控件

                mapTypeControl: false,          //mapTypeControl：用于启用/禁用地图类型控件，该控件可让用户在不同的地图类型（例如“地图”和“卫星”）之间进行切换。默认情况下，此控件可见并显示在地图左上角。mapTypeControlOptions 字段用于进一步指定要用于此控件的 MapTypeControlOptions
                mapTypeControlOptions: {        //地图类型控件可以水平栏或下拉菜单形式显示
                    //style: google.maps.MapTypeControlStyle.DROPDOWN_MENU    //用于显示单个按钮控件，以便您通过下拉菜单选择地图类型
                    //style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,   //用于在水平栏中将一组控件显示为如 Google 地图中所示的按钮
                    //style: google.maps.MapTypeControlStyle.DEFAULT          //用于显示默认行为，该行为取决于屏幕大小且可能会在以后的 API 版本中有所变化
                    mapTypeIds: [
                        google.maps.MapTypeId.ROADMAP,      //线路图       用于显示默认的道路地图视图。这是默认地图类型。
                        google.maps.MapTypeId.SATELLITE,    //卫星         用于显示 Google Earth 卫星图像。
                        google.maps.MapTypeId.TERRAIN,      //地形         基于地面信息显示物理地图。
                        google.maps.MapTypeId.HYBRID        //混合         用于同时显示正常视图和卫星视图
                    ]
                }
            }
        }
    },
    Marker: {
        fun_createMarker: function (googleMap, latLng, title, icon) {
            //var latLng = new google.maps.LatLng(coordinatesDriver.lat, coordinatesDriver.lng);
            //var latLng = {lat: parseFloat(coordinatesDriver.lat), lng: parseFloat(coordinatesDriver.lng)};
            return new google.maps.Marker({
                map: googleMap,
                position: latLng,
                title: (title != null) ? title : '',
                icon: (icon != null) ? icon : ''
            });
        }
    },
    MarkerClusterer: {
        fun_createMarkerClusterer: function (googleMap) {
            var imagePath = ConstantsFinalValue.WEBSITE_STATIC_RESOURCE_PATH + '/resources/js/js-marker-clusterer/images/m';
            var mcOptions = {
                gridSize: 50,               //一个集群的网格大小像素。
                maxZoom: 15,                //最大缩放级别标记可以是集群的一部分。
                //minimumClusterSize:40,      //最小数量的标记之前在一个集群中标记隐藏和显示计数。
                zoomOnClick: true,          //点击一个集群的违约行为是否放大它。
                averageCenter: false,        //每个集群的中心是否应该集群中的所有标记的平均值。
                imagePath: imagePath
            };
            return new MarkerClusterer(googleMap, [], mcOptions);
        }
    },
    Polyline: {
        fun_createPolyline: function (googleMap, paths) {
            var lineSymbol = {
                //path: google.maps.SymbolPath.CIRCLE	                        //圆。
                path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW	            //四面封闭的后向箭头。
                // path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW	            //四面封闭的前向箭头。
                // path: google.maps.SymbolPath.BACKWARD_OPEN_ARROW	            //一面开放的后向箭头。
                // path: google.maps.SymbolPath.FORWARD_OPEN_ARROW	                //一面开放的前向箭头。
            };

            //多段线 构造函数带有一组用于指定线的 LatLng 坐标的 PolylineOptions，以及一组用于调整多段线视觉行为的样式。
            var flightPath = new google.maps.Polyline({
                path: paths,
                geodesic: true,
                strokeColor: 'blue',        //strokeColor 指定 "#FFFFFF" 格式的十六进制 HTML 颜色。Polyline 类不支持命名颜色。
                strokeOpacity: 1.0,         //strokeOpacity 指定一个介于 0.0 和 1.0 的数值，用于确定线颜色的不透明度。默认值为 1.0。
                strokeWeight: 2,            //指定线的宽度（单位：像素）。
                //editable: true,             //将形状设置为可编辑 可通过在形状的选项中将 editable 设置为 true，将任何形状（多段线、多边形、圆和矩形）设置为可由用户编辑。
                //draggable: true,              //将形状设置为可拖动 默认情况下，在地图上绘制的形状位置固定。如需允许用户将形状拖动到地图上的其他位置，请在形状的选项中将 draggable 设置为 true。
                icons: [{
                    icon: lineSymbol,
                    offset: '100%'
                }]
            });
            flightPath.setMap(googleMap);

            return flightPath;
        }
    },
    Route: {
        fun_Initialization_options: function (paramOrigin, paramDestination) {
            return {
                origin: paramOrigin,                                     //origin（必填）：用于指定计算路线时所用的起始地点。该值可以指定为 String（例如“伊利诺斯州芝加哥市”）、LatLng 值或 google.maps.Place 对象。如果使用 google.maps.Place 对象，您可以指定一个地点 ID、一个查询字符串或一个 LatLng 地点。您可以从 Google Maps JavaScript API 中的地理编码服务、地点搜索服务和地点自动完成服务检索地点 ID。如需查看使用来自“地点自动完成”的地点 ID 的示例，请参阅地点自动完成和路线。
                destination: paramDestination,                           //destination（必填）：用于指定计算路线时所用的结束地点。其选项与上面所述 origin 字段的选项相同

                /**
                 * //travelMode（必填）：用于指定计算路线时所用的交通方式。有效值见下文出行方式部分所述
                 * 交通方式
                 *
                 * travelMode: google.maps.TravelMode.DRIVING,//（默认）：表示使用道路网络的标准行车路线
                 * travelMode: google.maps.TravelMode.BICYCLING,//：用于请求经过骑行道和优先街道的骑行路线
                 * travelMode: google.maps.TravelMode.TRANSIT,//：用于请求经过公交路线的路线
                 * travelMode: google.maps.TravelMode.WALKING,//：用于请求经过步行街和人行道的步行路线
                 *
                 */
                travelMode: google.maps.TravelMode.DRIVING,         //travelMode（必填）：用于指定计算路线时所用的交通方式。有效值见下文出行方式部分所述

                //transitOptions（可选）：用于指定仅适用于其中 travelMode 为 google.maps.TravelMode.TRANSIT 的请求的值。有效值见下文公交选项部分所述
                //drivingOptions（可选）：用于指定仅适用于其中 travelMode 为 google.maps.TravelMode.DRIVING 的请求的值。有效值见下文行车选项部分所述

                /**
                 *  //unitSystem（可选）：用于指定显示结果时所用的单位制。有效值见下文单位制部分所述
                 *  单位制
                 *  默认情况下，使用起点所在国家或地区的单位制来计算和显示路线。（注：以纬度/经度坐标而不是地址表示的起点始终默认采用公制单位。）例如，从“伊利诺斯州芝加哥市”到“安大略省多伦多市”的路线结果将以英里显示，而反向路线结果以公里显示。您可以使用以下某个 UnitSystem 值在请求中显式设置一个单位制来重写此单位制：
                 *  UnitSystem.METRIC：用于指定使用公制单位。以公里为单位显示距离
                 *  UnitSystem.IMPERIAL：用于指定使用英制单位。以英里为单位显示距离
                 *  注：此单位制设置仅会影响向用户显示的文本。路线结果还包括始终以米为单位表示的距离值，但这些值不向用户显示。
                 */
                //unitSystem: UnitSystem.METRIC,

                /**
                 * waypoints[]（可选）：用于指定 DirectionsWaypoint 数组。路径点通过使路线经过指定位置来改变路线。您可将路径点指定为带有如下所示字段的一个对象字面量：
                 *                      location：用于以 LatLng、google.maps.Place 对象或将进行地理编码的 String 形式指定路径点位置
                 *                      stopover：一个布尔值，表示路径点是路线上的一个停留点，可将路线一分为二
                 *
                 *                      var waypts = [];
                 *                      $.each(base.ItemBaseData.vehicleCurrentCoordinates, function (i, taskMonitorBean) {
                     *
                     *                          var myLatLng = new google.maps.LatLng(parseFloat(taskMonitorBean.vehicleLatitude), parseFloat(taskMonitorBean.vehicleLongitude));
                     *
                     *                          console.info(JSON.stringify(myLatLng));
                     *                          waypts.push({location: myLatLng,stopover: true});
                     *
                     *                          if (i >= 8) {return false;}
                     *                      });
                 *
                 */
                //waypoints: waypts,                                   //waypoints[]（可选）：用于指定 DirectionsWaypoint 数组。路径点通过使路线经过指定位置来改变路线。您可将路径点指定为带有如下所示字段的一个对象字面量：
                //optimizeWaypoints: true,                             //optimizeWaypoints（可选）：用于指明可对使用提供的 waypoints 的路线进行优化，以提供尽可能最短的路线。如果该值设置为 true，那么“路线”服务将在 waypoint_order 字段中返回重新排序的 waypoints。（有关详细信息，请参阅下文在路线中使用路径点部分。）

                provideRouteAlternatives: false,                        //provideRouteAlternatives（可选）：设置为 true 时，指明“路线”服务可在响应中提供多条备用路线。请注意，提供备选路线可能会增加服务器的响应时间。
                //avoidHighways: false,                                //avoidHighways（可选）：设置为 true 时，表示计算的路线应避开主要公路（如果可能）。
                //avoidTolls: false                                    //avoidTolls（可选）：设置为 true 时，表示计算的路线应避开收费道路（如果可能）。
                //region（可选）：用于以 ccTLD（“顶级域”）双字符值形式指定地区代码。（如需了解详细信息，请参阅下面的地区偏向。
            }
        }
    }
};
