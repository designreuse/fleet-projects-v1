var Basic = {
    crudImplClass: {},
    page_elements: {

        btn_Create: 'btn_Create',
        btn_Delete: 'btn_Delete',
        btn_Update: 'btn_Update',
        btn_Search: 'btn_Search',
        btn_Search_Reset: 'btn_Search_Reset',

        modal_Search_Form: 'modal_Search_Form',
        modal_Search_Form_Button_Reset: 'modal_Search_Form_Button_Reset',

        modal_Create: 'modal_Create',
        modal_Create_Form: 'modal_Create_Form',
        modal_Create_Form_InfoContainer: 'modal_Create_Form_InfoContainer',
        modal_Create_Form_Button_Cancel: 'modal_Create_Form_Button_Cancel',
        modal_Create_Form_Button_Reset: 'modal_Create_Form_Button_Reset',
        modal_Create_Form_Button_Submit: 'modal_Create_Form_Button_Submit',

        modal_Delete: 'modal_Delete',
        modal_Delete_Form: 'modal_Delete_Form',
        modal_Delete_Form_InfoContainer: 'modal_Delete_Form_InfoContainer',
        modal_Delete_Form_Button_Cancel: 'modal_Delete_Form_Button_Cancel',
        modal_Delete_Form_Button_Reset: 'modal_Delete_Form_Button_Reset',
        modal_Delete_Form_Button_Submit: 'modal_Delete_Form_Button_Submit',

        modal_Update: 'modal_Update',
        modal_Update_Form: 'modal_Update_Form',
        modal_Update_Form_InfoContainer: 'modal_Update_Form_InfoContainer',
        modal_Update_Form_Button_Cancel: 'modal_Update_Form_Button_Cancel',
        modal_Update_Form_Button_Reset: 'modal_Update_Form_Button_Reset',
        modal_Update_Form_Button_Submit: 'modal_Update_Form_Button_Submit'

    },
    fun_Basic_Init: function (moduleCRUD) {

        Basic.crudImplClass = moduleCRUD;
        Controls_DataTables._server_ListUrl = moduleCRUD._server_ListUrl;
        Controls_DataTables._server_ListTypePost = moduleCRUD._server_ListTypePost;
        Controls_DataTables._htmlTableObj = $('#' + moduleCRUD._tableId);
        Controls_DataTables.fun_DataTables_Columns = moduleCRUD.fun_DataTables_Columns();
        Controls_DataTables.fun_DataTables_Init();

        CRUD.crudImplClass = moduleCRUD;
        CRUD.ajaxServerRequestOptions._fieldTags_Id = moduleCRUD._fieldTags_Id;
        CRUD.ajaxServerRequestOptions._fieldTags_Name = moduleCRUD._fieldTags_Name;
        CRUD.ajaxServerRequestOptions._server_CreateUrl = moduleCRUD._server_CreateUrl;
        CRUD.ajaxServerRequestOptions._server_CreateTypePost = moduleCRUD._server_CreateTypePost;
        CRUD.ajaxServerRequestOptions._server_DeleteUrl = moduleCRUD._server_DeleteUrl;
        CRUD.ajaxServerRequestOptions._server_DeleteTypePost = moduleCRUD._server_DeleteTypePost;
        CRUD.ajaxServerRequestOptions._server_UpdateUrl = moduleCRUD._server_UpdateUrl;
        CRUD.ajaxServerRequestOptions._server_UpdateTypePost = moduleCRUD._server_UpdateTypePost;
        CRUD.ajaxServerRequestOptions._server_UpdateTypeGet = moduleCRUD._server_UpdateTypeGet;
        CRUD.fun_CRUE_Init();

    },
    fun_setHeader: function (XMLHttpRequest) {
        XMLHttpRequest.setRequestHeader('token', $.cookie(ConstantsFinalValue.SERVER_TOKEN_MARK));
    }
};


var ItemBaseData = {

    basicData: {
        departmentList: {},
        roleList: {},
        ajaxReturnJSONDataList: {}
    },
    ajax_FuzzyMatchingListClientByClientName: function (clientName, formElementClientIdObj, callback) {
        var varParamData = 'clientName=' + clientName;
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxFuzzyMatchingListClientByClientName.php';
        var type = 'GET';
        console.info('ajax_FuzzyMatchingListClientByClientName , Ajax server ' + type + '  url:\t\t' + url);
        console.info('ajax_FuzzyMatchingListClientByClientName , Ajax server param data:\t' + JSON.stringify(varParamData));

        $.ajax({
            type: type,
            url: url,
            async: false,
            data: varParamData,
            //发送请求   beforeSend:beforeSend(XMLHttpRequest)
            beforeSend: function (XMLHttpRequest) {
                console.info('ajax_FuzzyMatchingListClientByClientName , Ajax server beforeSend');
                // Basic.fun_setHeader(XMLHttpRequest);
                // $('#modal_Create_Form_Element_clientName_ajaxImg').css('display', 'block');
            },
            //请求成功  success:callback(data, status, xmlHttpRequest)
            success: function (resultJsonData) {
                console.info('ajax_FuzzyMatchingListClientByClientName, Ajax server success: ' + resultJsonData);

                if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, null)) {
                    var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                    var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);

                    if (resultMsgJSON.length < 1) {
                        formElementClientIdObj.val('');
                        console.info('AutoComplete，get clientName not exist: clean formElementClientIdObj value' + formElementClientIdObj.val());
                        return;
                    }
                    ItemBaseData.ajaxReturnJSONDataList = resultMsgJSON;

                    var results = _.map(resultMsgJSON, function (item) {
                        return item.name;
                    });
                    console.info(assupg.JSON.convertJsonToStr(results));
                    callback(results);
                }

            },
            //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
            error: function (resultJsonData) {
                console.info('ajax_FuzzyMatchingListClientByClientName, Ajax server error: ' + resultJsonData);
                HandleServerReturnResult.fun_ServerError(resultJsonData);
            },
            //请求完成  complete:complete(XMLHttpRequest, textStatus)
            complete: function (resultJsonData) {
                console.info('ajax_FuzzyMatchingListClientByClientName, Ajax server complete: ' + resultJsonData);
            }
        });
    },
    ajax_FuzzyMatchingListClientBranchByClientAndClientName: function (clientId, clientBranchName, formElementClientBranchIdObj, callback) {
        var varParamData = 'clientId=' + clientId + '&clientBranchName=' + clientBranchName;
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxFuzzyMatchingListClientBranchByClientAndClientName.php';
        var type = 'GET';
        console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName , Ajax server ' + type + '  url:\t\t' + url);
        console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName , Ajax server param data:\t' + JSON.stringify(varParamData));

        $.ajax({
            type: type,
            url: url,
            async: false,
            data: varParamData,
            //发送请求   beforeSend:beforeSend(XMLHttpRequest)
            beforeSend: function (XMLHttpRequest) {
                console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName , Ajax server beforeSend');
                // Basic.fun_setHeader(XMLHttpRequest);
                // $('#modal_Create_Form_Element_clientName_ajaxImg').css('display','block');
            },
            //请求成功  success:callback(data, status, xmlHttpRequest)
            success: function (resultJsonData) {
                console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName, Ajax server success: ' + resultJsonData);

                if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, null)) {
                    var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                    var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);

                    if (resultMsgJSON.length < 1) {
                        formElementClientBranchIdObj.val('');
                        console.info('AutoComplete，get clientName not exist: clean formElementClientIdObj value' + formElementClientBranchIdObj.val());
                        return;
                    }
                    ItemBaseData.ajaxReturnJSONDataList = resultMsgJSON;
                    //process(resultMsgJSON);

                    var results = _.map(resultMsgJSON, function (item) {
                        return item.name;
                    });
                    console.info(assupg.JSON.convertJsonToStr(results));
                    callback(results);
                }

            },
            //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
            error: function (resultJsonData) {
                console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName, Ajax server error: ' + resultJsonData);
                HandleServerReturnResult.fun_ServerError(resultJsonData);
            },
            //请求完成  complete:complete(XMLHttpRequest, textStatus)
            complete: function (resultJsonData) {
                console.info('ajaxFuzzyMatchingListClientBranchByClientAndClientName, Ajax server complete: ' + resultJsonData);
            }
        });
    },

    fun_selectDepartmentAndRoleAll: function () {
        var varParamData = '';
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/getDepartmentAndRoleALLByStatusIsActivated.php';
        var type = 'GET';
        console.info('ItemBaseData , Ajax server ' + type + '  url:\t\t' + url);
        console.info('ItemBaseData , Ajax server param data:\t' + JSON.stringify(varParamData));

        $.ajax({
            type: type,
            url: url,
            async: false,
            data: varParamData,
            cache: false,  //禁用缓存
            dataType: 'json',
            //发送请求   beforeSend:beforeSend(XMLHttpRequest)
            beforeSend: function (XMLHttpRequest) {
                console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server beforeSend');
                // Basic.fun_setHeader(XMLHttpRequest);
            },
            //请求成功  success:callback(data, status, xmlHttpRequest)
            success: function (resultJsonData) {
                console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server success: ' + resultJsonData);

                if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, null)) {
                    var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                    var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);

                    ItemBaseData.basicData.departmentList = resultMsgJSON.departmentList;
                    ItemBaseData.basicData.roleList = resultMsgJSON.roleList;
                }

            },
            //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
            error: function (resultJsonData) {
                console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server error: ' + resultJsonData);
                HandleServerReturnResult.fun_ServerError(resultJsonData);
            },
            //请求完成  complete:complete(XMLHttpRequest, textStatus)
            complete: function (resultJsonData) {
                console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server complete: ' + resultJsonData);
            }
        });
    },

    InitializationControlsData: {

        //通用模块
        fun_SelectionBox_Status: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select Status...</option>');
            }
            selectControlsObj.append('<option value="Enable">Enable</option>');
            selectControlsObj.append('<option value="Disable">Disable</option>');
        },
        fun_RadioBox_Status: function (radioBoxDivId) {
            var radioBoxDivObj = $('#' + radioBoxDivId);
            radioBoxDivObj.html('');
            var insertHTML = '' +
                '<label><input type="radio" name="status" value="Enable">Enable&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label><input type="radio" name="status" value="Disable">Disable</label>';
            radioBoxDivObj.html(insertHTML);
        },
        fun_Daterangepicker_SingleCalendar: function (htmlDateBoxId, htmlFormId, initializationDate) {
            var htmlDateBoxObj = $('#' + htmlDateBoxId);
            this.initializationDate = (initializationDate != null) ? assupg.DataFormatConversion.DateType_Date(initializationDate) : '';
            //时间插件
            htmlDateBoxObj.daterangepicker(controls.DateRangePicker.option_single_calendar,
                function (start, end, label) {//格式化日期显示框
                    console.info('New date range selected: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
                    htmlDateBoxObj.val(start.format(ConstantsFinalValue.FORMAT_DATE));
                    if (htmlFormId) {
                        $('#' + htmlFormId).bootstrapValidator('revalidateField', htmlDateBoxObj.attr('name'));
                    }
                }).val(this.initializationDate);
        },
        fun_Daterangepicker_DualCalendar: function (htmlDateBoxId, htmlStartTimeId, htmlEndTimeId) {
            var htmlDateBoxObj = $('#' + htmlDateBoxId);
            var htmlStartTimeObj = $('#' + htmlStartTimeId);
            var htmlEndTimeObj = $('#' + htmlEndTimeId);
            //时间插件
            htmlDateBoxObj.daterangepicker(controls.DateRangePicker.option_dual_calendar,
                function (start, end, label) {//格式化日期显示框
                    console.info('New date range selected: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
                    htmlStartTimeObj.val(start.format(ConstantsFinalValue.FORMAT_DATE_TIME));
                    console.info('htmlStartTimeId: ' + htmlStartTimeId + ' value: ' + htmlStartTimeObj.val());
                    htmlEndTimeObj.val(end.format(ConstantsFinalValue.FORMAT_DATE_TIME));
                    console.info('htmlEndTimeId: ' + htmlEndTimeId + ' value: ' + htmlEndTimeObj.val());
                }).val('');
            controls.DateRangePicker.daterangepicker_input_readonly();
        },


        fun_SelectionBox_DepartmentList: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select Department...</option>');
            }
            $.each(ItemBaseData.basicData.departmentList, function (i, department) {
                selectControlsObj.append('<option value="' + department.id + '">' + department.name + '</option>');
            });
        },

        fun_SelectionBox_RoleLis: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select Roles...</option>');
            }
            $.each(ItemBaseData.basicData.roleList, function (i, role) {
                selectControlsObj.append('<option value="' + role.id + '">' + role.name + '</option>');
            });
        },
        //User 模块
        fun_SelectionBox_Gender: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select Gender...</option>');
            }
            selectControlsObj.append('<option value="Male">Male</option>');
            selectControlsObj.append('<option value="Female">Female</option>');
        },
        fun_RadioBox_UserType: function (radioBoxDivId) {
            var radioBoxDivObj = $('#' + radioBoxDivId);
            radioBoxDivObj.html('');
            var insertHTML = '' +
                // '<div class="radio">' +
                '<label><input type="radio" name="type" value="Employee">Employee&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label><input type="radio" name="type" value="Client">Client</label>';
            // '</div>';
            radioBoxDivObj.html(insertHTML);
        },

        // Task 模块
        fun_Daterangepicker_SingleCalendar_TaskAdd: function (htmlDateBoxId, htmlFormId) {
            var htmlDateBoxObj = $('#' + htmlDateBoxId);
            //时间插件
            htmlDateBoxObj.daterangepicker(controls.DateRangePicker.option_single_calendar_TaskAdd,
                function (start, end, label) {//格式化日期显示框
                    console.info('New date range selected: ' + start.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' to ' + end.format(ConstantsFinalValue.FORMAT_DATE_TIME) + ' (predefined range: ' + label + ')');
                    htmlDateBoxObj.val(start.format(ConstantsFinalValue.FORMAT_DATE));
                    if (htmlFormId) {
                        $('#' + htmlFormId).bootstrapValidator('revalidateField', htmlDateBoxObj.attr('name'));
                    }
                }).val('');
        },
        fun_SelectionBox_TaskType: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select TaskType...</option>');
            }
            selectControlsObj.append('<option value="Counter">Counter</option>');
            selectControlsObj.append('<option value="Receive_Money">Receive_Money</option>');
            selectControlsObj.append('<option value="Give_Money">Give_Money</option>');
            selectControlsObj.append('<option value="Maintain_ATM">Maintain_ATM</option>');
        },
        fun_SelectionBox_TaskStatus: function (selectControlsId, flag) {
            var selectControlsObj = $('#' + selectControlsId);
            selectControlsObj.empty();
            if (flag == true) {
                selectControlsObj.append('<option value="">Select Status...</option>');
            }
            selectControlsObj.append('<option value="Wait">Wait</option>');
            selectControlsObj.append('<option value="Assignment">Assignment</option>');
            selectControlsObj.append('<option value="Receive">Receive</option>');
            selectControlsObj.append('<option value="Warehousing">Warehousing</option>');
            selectControlsObj.append('<option value="Settlement">Settlement</option>');
            selectControlsObj.append('<option value="Fail">Fail</option>');
            selectControlsObj.append('<option value="Success">Success</option>');
        },
        //客户机构，cronExpression
        fun_ClientBranch_cronExpression_CheckBox: function (checkBoxDivId, checkboxName) {
            var checkBoxDivObj = $('#' + checkBoxDivId);
            checkBoxDivObj.html('');
            var insertHTML = '' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="SUN">SUN&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="MON">MON&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="TUE">TUE&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="WED">WED&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="THU">THU&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="FRI">FRI&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                '<label> <input type="checkbox" name="' + checkboxName + '" value="SAT">SAT&nbsp;&nbsp;&nbsp;&nbsp;</label>';
            checkBoxDivObj.html(insertHTML);
        }
    }
};
ItemBaseData.ajaxGetClientBranchListByKeychainId = {};
ItemBaseData.ajaxGetClientBranchListByKeychainId = function (keychainId, callback) {
    var varParamData = 'keychainId=' + keychainId;
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxGetClientBranchListByKeychainId.php';
    var type = 'GET';
    console.info('ajaxGetClientBranchListByKeychainId , Ajax server ' + type + '  url:\t\t' + url);
    console.info('ajaxGetClientBranchListByKeychainId , Ajax server param data:\t' + JSON.stringify(varParamData));

    $.ajax({
        type: type,
        url: url,
        async: false,
        data: varParamData,
        //发送请求   beforeSend:beforeSend(XMLHttpRequest)
        beforeSend: function (XMLHttpRequest) {
            console.info('ajax_FuzzyMatchingListClientByClientName , Ajax server beforeSend');
        },
        //请求成功  success:callback(data, status, xmlHttpRequest)
        success: function (resultJsonData) {
            console.info('ajaxGetClientBranchListByKeychainId, Ajax server success: ' + resultJsonData);
            if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, null)) {

                var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);
                if (resultMsgJSON.length < 1) {
                    console.info('ajaxGetClientBranchListByKeychainId，Is empty');
                    callback(null);
                } else {
                    callback(resultMsgJSON);
                }
            }
        },
        //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
        error: function (resultJsonData) {
            console.info('ajaxGetClientBranchListByKeychainId, Ajax server error: ' + resultJsonData);
            HandleServerReturnResult.fun_ServerError(resultJsonData);
        },
        //请求完成  complete:complete(XMLHttpRequest, textStatus)
        complete: function (resultJsonData) {
            console.info('ajaxGetClientBranchListByKeychainId, Ajax server complete: ' + resultJsonData);
        }
    });

};

ItemBaseData.bindingNavAjaxUrl = {};
ItemBaseData.bindingNavAjaxUrl = function (urlPath) {
    var varParamData = '';
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + urlPath;
    var type = 'GET';
    console.info('ItemBaseData , Ajax server ' + type + '  url:\t\t' + url);
    console.info('ItemBaseData , Ajax server param data:\t' + JSON.stringify(varParamData));

    $.ajax({
        type: type,
        url: url,
        async: false,
        data: varParamData,
        //发送请求   beforeSend:beforeSend(XMLHttpRequest)
        beforeSend: function (XMLHttpRequest) {
            console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server beforeSend');
            Basic.fun_setHeader(XMLHttpRequest);
        },
        //请求成功  success:callback(data, status, xmlHttpRequest)
        success: function (resultJsonData) {
            console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server success: ' + resultJsonData);
            $('#main_RightArea').html(resultJsonData);
        },
        //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
        error: function (resultJsonData) {
            console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server error: ' + resultJsonData);
            HandleServerReturnResult.fun_ServerError(resultJsonData);
        },
        //请求完成  complete:complete(XMLHttpRequest, textStatus)
        complete: function (resultJsonData) {
            console.info('ItemBaseData fun_selectDepartmentAndRoleAll, Ajax server complete: ' + resultJsonData);
        }
    });
};

var controls = {};
controls.ClockPicker = {};
controls.ClockPicker.option = {
    placement: 'top',   //放置	“底”	弹出窗口位置
    align: 'left',      //对齐	“左”	弹窗箭头对齐
    autoclose: true,    //autoclose	假	当选择分钟自动关闭
    'default': 'now',   //默认的	”	默认的时间,“现在”或“13:14”。
    init: controls.ClockPicker.afterDone
};
controls.ClockPicker.fun_ClockPicker = function (htmlTimeBoxId, htmlFormId) {
    var htmlTimeBoxObj = $('#' + htmlTimeBoxId);
    var input = htmlTimeBoxObj.clockpicker(controls.ClockPicker.option);

    // Manually toggle to the minutes view
    $('#check-minutes').click(function (e) {
        // Have to stop propagation here
        e.stopPropagation();
        input.clockpicker('show')
            .clockpicker('toggleView', 'minutes');
    });
};
controls.ClockPicker.afterDone = function () {
    alert("da");
};


controls.DateRangePicker = {};
controls.DateRangePicker.option_single_calendar = {
    maxDate: moment(),                                          //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期
    showDropdowns: true,                                        //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
    singleDatePicker: true,                                     //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
    locale: {                                                   //locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
        format: ConstantsFinalValue.FORMAT_DATE
    }
};
controls.DateRangePicker.option_single_calendar_TaskAdd = {
    startDate: moment(),                                        //startDate (日期对象,对象或字符串)时刻开始的最初选择的日期范围
    //endDate: moment(),                                        //endDate (日期对象,时刻对象或字符串)的最初选择的日期范围
    minDate: moment(),                                          //maxDate (日期对象,时刻对象或字符串):用户可以选择最新的日期
    maxDate: moment().subtract(-6, 'days').startOf('day'),      //minDate (日期对象,时刻对象或字符串)最早的用户可以选择日期,最多增加7天之内的任务
    //showDropdowns: true,                                      //showDropdowns (布尔)显示年,月选择框上面日历可以跳转到一个特定的月和年
    singleDatePicker: true,                                     //singleDatePicker (布尔):只显示一个日历选择一个日期,而不是一个范围选择有两个日历;你的回调提供的开始和结束日期将是相同的单一选择日期
    locale: {                                                   //locale (对象)允许您为按钮和标签提供本地化字符串,自定义日期显示格式,改变为日历星期的第一天
        format: ConstantsFinalValue.FORMAT_DATE
    }
};
controls.DateRangePicker.option_dual_calendar = {
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
};
controls.DateRangePicker.daterangepicker_input_readonly = function () {
    $('.input-mini').attr('readonly', 'readonly');//设置定义选择日期是，将日期输入框设置为只读状态
};

controls.Typeahead = {};
controls.Typeahead.init = function (formElementTextBoxId, formElementHiddenBoxId, AjaxFuncation) {
    var formElementTextBoxObj = $('#' + formElementTextBoxId);
    var formElementHiddenBoxObj = $('#' + formElementHiddenBoxId);

    formElementTextBoxObj.attr('data-provide', 'typeahead');
    formElementTextBoxObj.typeahead.Constructor.prototype.blur = function () {
        var that = this;
        setTimeout(function () {
            that.hide()
        }, 250);
    };

    formElementTextBoxObj.typeahead({
        items: 8,//最多显示个数
        delay: 300,//延迟时间
        source: function (query, process) {

            if (query.length < 2) {
                return;
            }

            AjaxFuncation(query, formElementHiddenBoxObj, process);
        },
        matcher: function (item) {
            return true;
        },
        highlighter: function (item) {
            var clientBean = _.find(ItemBaseData.ajaxReturnJSONDataList, function (p) {
                return p.name == item;
            });
            return '<div class="alert alert-info"> ClientName: <strong>' + clientBean.name + "</strong>&nbsp;&nbsp;&nbsp;&nbsp;Phone: " + clientBean.phone + '</div>';
        },
        updater: function (item) {
            console.info('AutoComplete，updater value: ' + item);

            var clientBean = _.find(ItemBaseData.ajaxReturnJSONDataList, function (p) {
                return p.name == item;
            });

            formElementHiddenBoxObj.val(clientBean.id);
            console.info(formElementHiddenBoxObj.val());
            return clientBean.name;
        }
    });

};


controls.Dialog = {};
controls.Dialog.dialog_Primary = function (msg) {
    alert(msg);
};
controls.Dialog.dialog_Success = function (msg) {
    alert(msg);
};
controls.Dialog.dialog_Info = function (msg) {
    alert(msg);
};
controls.Dialog.dialog_Warning = function (msg) {
    alert(msg);
};
controls.Dialog.dialog_Danger = function (msg) {
    alert(msg);
};


var Controls_DataTables = {

    _htmlTableObj: null,
    _dataTablesObj: null,
    _fuzzySearch: true,
    _server_ListUrl: null,
    _server_ListTypePost: null,
    fun_DataTables_Columns: {},


    fun_DataTables_Init: function () {

        Controls_DataTables._dataTablesObj = Controls_DataTables._htmlTableObj.dataTable($.extend(true, {},
            Controls_DataTables.initialization_options,
            {
                ajax: Controls_DataTables.fun_ServerAjax,
                columns: Controls_DataTables.fun_DataTables_Columns,
                dom: Controls_DataTables.fun_DomOptions(),
                initComplete: Controls_DataTables.fun_InitComplete,
                drawCallback: Controls_DataTables.fun_DrawCallback//表格每次重绘回调函数
            }
        )).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

        Controls_DataTables.fun_RowDetails();
        Controls_DataTables.fun_RowSelect();
    },

    initialization_options: { //DataTables初始化选项
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

    column_render: {   //常用render可以抽取出来，如日期时间、头像等
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
        Department: function (data, type, row) {
            data = data || '';
            var departmentName = '';
            $.each(ItemBaseData.basicData.departmentList, function (i, department) {
                if (data == department.id) {
                    departmentName = department.name;
                }
            });
            return departmentName;
        },
        Role: function (data, type, row) {
            data = data || '';
            var roles = data.split(ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);//2|3|4
            var roleName = '';
            if (roles instanceof Array) {
                $.each(ItemBaseData.basicData.roleList, function (i, role) {
                    for (var i = 0, j = roles.length; i < j; i++) {
                        if (roles[i] == role.id) {
                            roleName += role.name + '&nbsp;&nbsp;';
                        }
                    }
                });
            }
            return roleName;
        },
        Task_Create_Mode: function (data, type, row) {
            data = data || '';
            return (data == 0) ? 'System' : 'Manual';
        },
        Task_ExecutionDate: function (data, type, row) {
            var executionDate = assupg.DataFormatConversion.DateType_Date(row.executionDate);
            var clientBranchWindowStartTime = assupg.DataFormatConversion.DateType_Time(row.clientBranchWindowStartTime);
            var clientBranchWindowEndTime = assupg.DataFormatConversion.DateType_Time(row.clientBranchWindowEndTime);
            return executionDate + ' ' + clientBranchWindowStartTime + ' - ' + clientBranchWindowEndTime;
        },
        Task_Operate: function (data, type, row) {
            return '<button type="button" class="btn btn-primary btn-xs" onclick="Basic.crudImplClass.operateModule.fun_btn_Operate_Click(' + row.id + ')">Operate</button>';
        },
        ClientBranch_WindowTime: function (data, type, row) {
            var clientBranchWindowStartTime = assupg.DataFormatConversion.DateType_Time(row.clientBranchWindowStartTime);
            var clientBranchWindowEndTime = assupg.DataFormatConversion.DateType_Time(row.clientBranchWindowEndTime);
            var clientBranchWindowDuration = assupg.DataFormatConversion.DateType_Time(row.clientBranchWindowDuration);
            return clientBranchWindowStartTime + ' - ' + clientBranchWindowEndTime;
        },
        ClientBranch_Operate: function (data, type, row) {
            return '<button type="button" class="btn btn-primary btn-xs" onclick="Basic.crudImplClass.operateModule.fun_btn_Operate_Click(' + row.id + ')">Operate</button>';
        }
    },

    fun_GetQueryCondition: function (data) {
        var param = {};
        // //组装排序参数
        //组装查询参数
        if (Controls_DataTables._fuzzySearch) {
            //var serialize = $('#model_Search_Form').serialize();//将表单内容序列化成一个字符串。 这样在ajax提交表单数据时 , 就不用一一列举出每一个参数。只需将data参数设置为 $('form').serialize() 即可。
            //console.info('组装查询参数 1查询from 示中所有的元素数据：' + JSON.stringify(formElementsJSON));
            var formElementsJSON = $('#' + Basic.page_elements.modal_Search_Form).serializeArray();//功能：将页面表单序列化成一个JSON结构的对象。注意不是JSON字符串。 比如 , [{'name':'lihui', 'age':'20'},{...}] 获取数据为
            $.each(formElementsJSON, function (i, form) {
                param[form.name] = form.value;
            });
        }
        //组装分页参数
        param.draw = data.draw;
        param.start = data.start;
        param.length = data.length;
        return param;
    },

    fun_ServerAjax: function (data, callback) {
        //封装请求参数
        var varParamData = Controls_DataTables.fun_GetQueryCondition(data);
        var url = Controls_DataTables._server_ListUrl;
        var type = Controls_DataTables._server_ListTypePost;
        assupg.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {
                async: false,
                cache: false,  //禁用缓存
                dataType: 'json'
            },
            null, null,
            function (data) {
                //封装返回数据
                var returnData = {};
                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = data.recordsTotal;
                returnData.recordsFiltered = data.recordsFiltered;//后台不实现过滤功能 , 每次查询均视作全部结果
                returnData.data = data.data;
                //调用DataTables提供的callback方法 , 代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误 , 异常判断应在执行此回调前自行处理完毕
                callback(returnData);
            }
        );
    },

    /**
     * datatables默认会打开部分特性，比如：搜索框，分布显示等等，或许你不喜欢datatables这样云布局，
     * 可能你想把分布按钮放在询问的中间，搜索框放在顶部的左上角，不用担心datatables考虑到这个问题，
     * 使用dom选项就可以灵活配置各个特性的位置
     * t        表格             The只允许出现一次
     * l        每页显示多少条数据选项，
     * f        搜索框
     * i        表格信息
     * p        分页按钮
     * r        pRocessing加载等待显示信息（开启服务器模式后能看到）
     * <and>                    div elements                一个div元素
     * <"#id" and>              div with an id              指定id的div元素
     * <"class" and>            div with a class            指定格式名的div元素
     * <"#.id.class" and>       div with an id and class    指定ID和样式的div元素
     * dom: "" +
     * "<'row'<'#topPlugin.col-xs-6'><'col-xs-6'f>>" +                //<div class="row"><div class="col-xs-6">l</div><div class="col-xs-6">f</div></div>
     * "<'row'<'col-sm-12't>>" +                                      //<div class="row"><div class="tr col-xs-12">>tr</div></div>
     * "<'row'<'col-sm-12'tr>>" +                                      //<div class="row"><div class="tr col-xs-12">>tr</div></div>
     * "<'row.text-center'<'col-xs-2' li><'col-xs-4' ><'col-xs-6'p>>",                      //<div class="row"><div class="col-xs-6">li</div><div class="col-xs-6">p</div></div>
     **/
    fun_DomOptions: function () {
        return '' +
            //"<'row'<'#topPlugin1.col-xs-4 text-left'><'#topPlugin2.col-xs-6 text-right'><'col-xs-2 text-left'l>>" +container
            "<'row'<'#topPlugin1.col-xs-7 text-left'><'col-xs-3 text-left'l>>" +
            "t" +
            "<'row '<'col-xs-1 text-left' i><'col-xs-11 text-right'p>>";

    },

    fun_InitComplete: function () {

        var topPlugin1 = '<div class="btn-toolbar" role="toolbar">' +
            '   <div class="btn-group" role="group">' +
            '       <button type="button" class="btn btn-primary" id="' + Basic.page_elements.btn_Create + '" title="Add">Add</button>   ' +//'<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Add' +
            '       <button type="button" class="btn btn-info"    id="' + Basic.page_elements.btn_Update + '" title="Please select the record to edit" disabled>Edit</button>' +
            '       <button type="button" class="btn btn-danger"  id="' + Basic.page_elements.btn_Delete + '" title="Please select the record to delete" disabled>Delete</button>' +
            '   </div>' +
            '   <div class="btn-group" role="group">' +
            '       <button type="button" class="btn btn-warning" id="' + Basic.page_elements.btn_Search_Reset + '">Reset Search</button>' +
            '       <button type="button" class="btn btn-success" id="' + Basic.page_elements.btn_Search + '">Search</button>' +
            '   </div>' +
            '</div>';
        $('#topPlugin1').append(topPlugin1);
    },

    fun_DrawCallback: function () {
        //渲染完毕后的回调
        $('#' + Basic.page_elements.btn_Update).attr('disabled', 'disabled');
        $('#' + Basic.page_elements.btn_Delete).attr('disabled', 'disabled');
    },

    fun_RowDetails: function () {
        // 行详细信息 start
        // Add event listener for opening and closing details

        $('tbody', Controls_DataTables._htmlTableObj).on('click', 'td.details-control', function () {//$('#example tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = Controls_DataTables._dataTablesObj.row(tr);
            if (row.child.isShown()) {// This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {// Open this row
                var rowData = row.data();
                row.child(Basic.crudImplClass.fun_DataTables_RowDetailsFormat(rowData)).show();
                tr.addClass('shown');
            }
        });
        //行详细信息 end
    },

    fun_RowSelect: function () {//选择器
        $('tbody', Controls_DataTables._htmlTableObj).on('click', 'tr', function () { //$('#example tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            var countRow = Controls_DataTables._dataTablesObj.rows('.selected').data().length;
            if (countRow < 1) {
                $('#' + Basic.page_elements.btn_Update).attr('disabled', 'disabled');
                $('#' + Basic.page_elements.btn_Delete).attr('disabled', 'disabled');
            } else if (countRow > 1) {
                $('#' + Basic.page_elements.btn_Update).attr('disabled', 'disabled');
            } else {
                $('#' + Basic.page_elements.btn_Update).removeAttr('disabled');
                $('#' + Basic.page_elements.btn_Delete).removeAttr('disabled');
            }
        });
    }
};


var CRUD;
CRUD = {

    crudImplClass: null,

    ajaxServerRequestOptions: {
        _fieldTags_Id: null,
        _fieldTags_Name: null,
        _server_ListUrl: null,
        _server_ListTypePost: null,
        _server_CreateUrl: null,
        _server_CreateTypePost: null,
        _server_DeleteUrl: null,
        _server_DeleteTypePost: null,
        _server_UpdateUrl: null,
        _server_UpdateTypePost: null,
        _server_UpdateTypeGet: null
    },

    fun_CRUE_Init: function () {
        $(document).delegate('#' + Basic.page_elements.btn_Search, 'click', CRUD.searchModule.fun_searchButtonClick);
        $(document).delegate('#' + Basic.page_elements.btn_Search_Reset, 'click', CRUD.searchModule.fun_searchResetButtonClick);
        CRUD.searchModule.fun_searchModule_Init();

        $(document).delegate('#' + Basic.page_elements.btn_Create, 'click', CRUD.createModule.fun_btn_Create_Click);
        $(document).delegate('#' + Basic.page_elements.btn_Delete, 'click', CRUD.deleteModule.fun_btn_Delete_Click);
        $(document).delegate('#' + Basic.page_elements.btn_Update, 'click', CRUD.updateModule.fun_btn_Update_Click);

    },

    searchModule: {

        fun_searchModule_Init: function () {
            //调用各子模块，自身业务的逻辑
            CRUD.crudImplClass.fun_searchModule_Init();
        },

        fun_searchButtonClick: function () {
            console.info('click event:  btn_Search');
            Controls_DataTables._dataTablesObj.ajax.reload();
        },

        fun_searchResetButtonClick: function () {
            console.info('click event:  btn_Search_Reset');
            $('#' + Basic.page_elements.modal_Search_Form_Button_Reset).click();
        }
    },

    createModule: {

        createFrom: null,

        fun_createModule_Init: function () {

            $('#' + Basic.page_elements.modal_Create_Form_Button_Reset).on('click', CRUD.createModule.fun_modal_Create_Form_Button_Reset_Click);
            console.info('modal_Create: Binding click method-------------------------  modal_Create_Form_Button_Reset');

            $('#' + Basic.page_elements.modal_Create_Form_Button_Submit).on('click', CRUD.createModule.fun_modal_Create_Form_Button_Submit_Click);
            console.info('modal_Create: Binding click method-------------------------  modal_Create_Form_Button_Submit');

            CRUD.createModule.createFrom = $('#' + Basic.page_elements.modal_Create_Form);

            //调用各子模块，自身业务的逻辑
            CRUD.crudImplClass.fun_createModule_Init();
            console.info('modal_Create: init subClass method-------------------------  fun_createModule_Init()');

            //为form 增加 bootstrapValidator 校验框架，用于检查数据的有效性
            CRUD.createModule.createFrom.bootstrapValidator(CRUD.crudImplClass.modal_Create_Form_BootstrapValidator_Options);
            console.info('modal_Create: add bootstrapValidator-------------------------  modal_Create_Form_BootstrapValidator_Options');
        },
        fun_btn_Create_Click: function () {
            console.info('click event:  btn_Create  用于初始化 , Create Modal Box');
            CRUD.createModule.fun_createModule_Init();
            //显示 , Create 模态框
            $('#' + Basic.page_elements.modal_Create).modal({
                backdrop: true,         //指定一个静态的背景 , 当用户点击模态框外部时不会关闭模态框。
                keyboard: true,         //当按下 escape 键时关闭模态框 , 设置为 false 时则按键无效。
                show: true              //当初始化时显示模态框。
            });
        },
        fun_modal_Create_Form_Button_Reset_Click: function () {
            console.info('click event:  odal_Create_Form_Button_Reset 将form表单各元素值，清空处理');
            $('#' + Basic.page_elements.modal_Create_Form_InfoContainer).html('');
            $('#' + Basic.page_elements.modal_Create_Form_Button_Submit).text('Add').removeAttr('disabled');

            $('#' + Basic.page_elements.modal_Create_Form).data('bootstrapValidator').resetForm(true);
        },
        fun_modal_Create_Form_Button_Submit_Click: function () {
            console.info('click event:  modal_Create_Form_Button_Submit 用于向服务器 , 提交Create 数据');

            //先检查 , JS检验框架 , 是否校验成功
            CRUD.createModule.createFrom.bootstrapValidator('validate'); //先调用检验
            var valid = CRUD.createModule.createFrom.data('bootstrapValidator').isValid();
            if (valid == false) {
                console.info(' modal_Create bootstrap Validator fail ,return validator result: ' + valid);
                return;
            }

            var cronExpressionArray = '';
            var varParamData = CRUD.createModule.createFrom.serializeJSON(
                {
                    customTypes: {
                        cronExpressionArray: function (str) {
                            cronExpressionArray += str + ',';
                        }
                    }
                }
            );
            varParamData.cronExpression = cronExpressionArray;

            var url = CRUD.ajaxServerRequestOptions._server_CreateUrl;
            var type = CRUD.ajaxServerRequestOptions._server_CreateTypePost;
            console.info('Create , Ajax server ' + type + '  url:\t\t' + url);
            console.info('Create , Ajax server param data:\t' + JSON.stringify(varParamData));

            $.ajax({
                type: type,
                url: url,
                data: varParamData,
                //发送请求   beforeSend:beforeSend(XMLHttpRequest)
                beforeSend: function (XMLHttpRequest) {
                    console.info('CRUD fun_modal_Create_Form_Button_Submit_Click, Ajax server beforeSend 将相应按钮设置 , 失效状态 , 并更改按钮文字信息.');
                    // Basic.fun_setHeader(XMLHttpRequest);
                    $('#' + Basic.page_elements.modal_Create_Form_Button_Submit).text('Loading...').attr('disabled', 'disabled');
                },
                //请求成功  success:callback(data, status, xmlHttpRequest)
                success: function (resultJsonData) {
                    console.info('CRUD fun_modal_Create_Form_Button_Submit_Click, Ajax server success: ' + resultJsonData);
                    if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, true, Basic.page_elements.modal_Create_Form_InfoContainer)) {
                        $('#' + Basic.page_elements.modal_Create).modal('hide');
                        $('#' + Basic.page_elements.modal_Create_Form_Button_Reset).click();
                        Controls_DataTables._dataTablesObj.ajax.reload();
                    }
                },
                //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
                error: function (resultJsonData) {
                    console.info('CRUD fun_modal_Create_Form_Button_Submit_Click, Ajax server error: ' + resultJsonData);
                    HandleServerReturnResult.fun_ServerError(resultJsonData);
                },
                //请求完成  complete:complete(XMLHttpRequest, textStatus)
                complete: function (resultJsonData) {
                    console.info('CRUD fun_modal_Create_Form_Button_Submit_Click, Ajax server complete: ' + resultJsonData);
                    $('#' + Basic.page_elements.modal_Create_Form_Button_Submit).text('Add').removeAttr('disabled');
                }
            });
        }
    },

    deleteModule: {

        fun_deleteModule_Init: function () {
            $('#' + Basic.page_elements.modal_Delete_Form_Button_Submit).on('click', CRUD.deleteModule.fun_modal_Delete_Form_Button_Submit_Click);
        },
        fun_btn_Delete_Click: function () {
            console.info('click event:  btn_Delete  用于初始化 , delete Modal Box');
            CRUD.deleteModule.fun_deleteModule_Init();

            var confirmInfo = CRUD.deleteModule.fun_validity_Delete_Confirm();
            $('#' + Basic.page_elements.modal_Delete_Form_InfoContainer).html(confirmInfo);

            //show , Delete Modal Box
            $('#' + Basic.page_elements.modal_Delete).modal({
                backdrop: true,         //指定一个静态的背景 , 当用户点击模态框外部时不会关闭模态框。
                keyboard: true,         //当按下 escape 键时关闭模态框 , 设置为 false 时则按键无效。
                show: true              //当初始化时显示模态框。
            });

        },
        fun_modal_Delete_Form_Button_Submit_Click: function () {
            console.info('click event:  btn_modal_Delete_Form_Button_Submit_Commit');
            try {
                var varParamData = 'ids=' + CRUD.deleteModule.fun_validity_Delete_Submit();
                var url = CRUD.ajaxServerRequestOptions._server_DeleteUrl;
                var type = CRUD.ajaxServerRequestOptions._server_DeleteTypePost;
                console.info('Delete , Ajax server ' + type + '  url:\t\t' + url);
                console.info('Delete , Ajax server param data:\t' + JSON.stringify(varParamData));

                $.ajax({
                    type: type,
                    url: url,
                    data: varParamData,

                    //发送请求   beforeSend:beforeSend(XMLHttpRequest)
                    beforeSend: function (XMLHttpRequest) {
                        console.info('CRUD fun_modal_Delete_Form_Button_Submit_Click, Ajax server beforeSend: 将相应按钮设置 , 失效状态 , 并更改按钮文字信息.');
                        // Basic.fun_setHeader(XMLHttpRequest);
                        $('#' + Basic.page_elements.modal_Delete_Form_Button_Submit).text('Loading...').attr('disabled', 'disabled');
                    },
                    //请求成功  success:callback(data, status, xmlHttpRequest)
                    success: function (resultJsonData) {
                        console.info('CRUD fun_modal_Delete_Form_Button_Submit_Click, Ajax server success: ' + resultJsonData);

                        if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, Basic.page_elements.modal_Delete_Form_InfoContainer)) {
                            $('#' + Basic.page_elements.modal_Delete).modal('hide');
                            Controls_DataTables._dataTablesObj.ajax.reload();
                        }
                    },
                    //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
                    error: function (resultJsonData) {
                        console.info('CRUD fun_modal_Delete_Form_Button_Submit_Click, Ajax server error: ' + resultJsonData);
                        HandleServerReturnResult.fun_ServerError(resultJsonData);
                    },
                    //请求完成  complete:complete(XMLHttpRequest, textStatus)
                    complete: function (resultJsonData) {
                        console.info('CRUD fun_modal_Delete_Form_Button_Submit_Click, Ajax server: ' + resultJsonData);
                        $('#' + Basic.page_elements.modal_Delete_Form_Button_Submit).text('Delete').removeAttr('disabled');
                    }
                });
            } catch (e) {
            }
        },
        fun_validity_Delete_Confirm: function () {
            var selectRowsLength = Controls_DataTables._dataTablesObj.rows('.selected').data().length;
            console.info('Currently selected number of lines: ' + selectRowsLength);
            if (selectRowsLength < 1) {
                Controls_Dialog.dialog_Info('Please select the record to be operated');
                throw new Error('Whoops!');
            }
            var result = '';
            for (var i = 0; i < selectRowsLength; i++) {
                var rowData = Controls_DataTables._dataTablesObj.rows('.selected').data()[i];
                result += '' +
                    '<div class="alert alert-warning" role="alert">' +
                    '       <strong>Warning!</strong> The deletion of' +
                    '       <strong><span>&nbsp;&nbsp;' + eval("rowData." + CRUD.ajaxServerRequestOptions._fieldTags_Name) + '&nbsp;&nbsp;</span></strong> cannot be undone.' +
                    '</div>';

                console.info('To operate the currently selected record id: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Id) + '\t-----name: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Name));
            }
            console.info('fun_validity_Delete_Confirm: ' + result);
            return result;
        },
        fun_validity_Delete_Submit: function () {

            var selectRowsLength = Controls_DataTables._dataTablesObj.rows('.selected').data().length;
            console.info('Currently selected number of lines: ' + selectRowsLength);
            if (selectRowsLength < 1) {
                Controls_Dialog.dialog_Info('Please select the record to be operated');
                throw new Error('Whoops!');
            }

            var result = new Array();
            for (var i = 0; i < selectRowsLength; i++) {
                var rowData = Controls_DataTables._dataTablesObj.rows('.selected').data()[i];
                result.push(eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Id));

                console.info('To operate the currently selected record id: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Id) + '\t-----name: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Name));
            }
            console.info('fun_validity_Delete_Submit: ' + result);
            return result;
        }
    },

    updateModule: {
        updateForm: null,

        fun_updateModule_Init: function () {

            $('#' + Basic.page_elements.modal_Update_Form_Button_Reset).on('click', CRUD.updateModule.fun_modal_Update_Form_Button_Reset_Click);
            console.info('modal_Update: Binding click method-------------------------  modal_Update_Form_Button_Reset');

            $('#' + Basic.page_elements.modal_Update_Form_Button_Submit).on('click', CRUD.updateModule.fun_modal_Update_Form_Button_Submit_Click);
            console.info('modal_Update: Binding click method-------------------------  modal_Update_Form_Button_Submit');

            CRUD.updateModule.updateForm = $('#' + Basic.page_elements.modal_Update_Form);

            //调用各子模块，自身业务的逻辑
            CRUD.crudImplClass.fun_updateModule_Init();
            console.info('modal_Update: init subClass method-------------------------  fun_updateModule_Init()');

            //为form 增加 bootstrapValidator 校验框架，用于检查数据的有效性
            CRUD.updateModule.updateForm.bootstrapValidator(CRUD.crudImplClass.modal_Update_Form_BootstrapValidator_Options);
            console.info('modal_Update: add bootstrapValidator-------------------------  modal_Update_Form_BootstrapValidator_Options');
        },
        fun_btn_Update_Click: function () {
            console.info('click event:  btn_Update  用于初始化 , update Modal Box');
            try {

                var varParamData = 'id=' + CRUD.updateModule.fun_validity_Update_Confirm();
                var url = CRUD.ajaxServerRequestOptions._server_UpdateUrl;
                var type = CRUD.ajaxServerRequestOptions._server_UpdateTypeGet;
                console.info('Edit , Ajax server ' + type + '  url:\t\t' + url);
                console.info('Edit , Ajax server param data:\t' + JSON.stringify(varParamData));

                $.ajax({
                    type: type,
                    url: url,
                    data: varParamData,
                    //发送请求   beforeSend:beforeSend(XMLHttpRequest)
                    beforeSend: function (XMLHttpRequest) {
                        console.info('CRUD fun_btn_Update_Click, Ajax server beforeSend: 将相应按钮设置 , 失效状态 , 并更改按钮文字信息.');
                        // Basic.fun_setHeader(XMLHttpRequest);
                    },
                    success: function (resultJsonData) {
                        console.info('CRUD fun_btn_Update_Click, Ajax server success: ' + resultJsonData);
                        if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, false, null) == true) {

                            CRUD.updateModule.fun_updateModule_Init();
                            var resultMsgStr = eval('resultJsonData.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);
                            var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);

                            CRUD.updateModule.updateForm.loadJSON(resultMsgJSON, ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);//注意loadJSON 1.0.0版本是可以支持 , 1.2.5版本有bug , 只能加载一次

                            var birthday = moment(resultMsgJSON.birthday).format(ConstantsFinalValue.FORMAT_DATE);
                            $('#modal_Update_Form_Element_birthday').val(birthday);

                            $('#' + Basic.page_elements.modal_Update).modal({
                                backdrop: true,         //指定一个静态的背景 , 当用户点击模态框外部时不会关闭模态框。
                                keyboard: true,         //当按下 escape 键时关闭模态框 , 设置为 false 时则按键无效。
                                show: true              //当初始化时显示模态框。
                            });
                        }
                    },
                    error: function (resultJsonData) {
                        console.info('CRUD fun_btn_Update_Click, Ajax server: ' + resultJsonData);
                        HandleServerReturnResult.fun_ServerError(resultJsonData);
                    },
                    //请求完成  complete:complete(XMLHttpRequest, textStatus)
                    complete: function (resultJsonData) {
                        console.info('CRUD fun_btn_Update_Click, Ajax server: ' + resultJsonData);
                    }
                });
            } catch (e) {
            }
        },
        fun_modal_Update_Form_Button_Reset_Click: function () {
            $('#' + Basic.page_elements.modal_Update_Form_InfoContainer).html('');
            $('#' + Basic.page_elements.modal_Update_Form_Button_Submit).text('Edit').removeAttr('disabled');

            $('#' + Basic.page_elements.modal_Update_Form).data('bootstrapValidator').resetForm(true);
        },
        fun_modal_Update_Form_Button_Submit_Click: function () {
            console.info('click event:  btn_modal_Edit_Form_Confirm_Commit_Button');

            //先检查 , JS检验框架 , 是否校验成功
            CRUD.updateModule.updateForm.bootstrapValidator('validate'); //先调用检验
            var valid = CRUD.updateModule.updateForm.data('bootstrapValidator').isValid();
            if (valid == false) {
                console.info(' 根据bootstrap Validator框架 , 返回校验结果：' + valid + '  若为false代表有校验不成功 , 不执行后面Ajax后台服务代码。');
                return;
            }

            var varParamData = CRUD.updateModule.updateForm.serializeJSON();
            var url = CRUD.ajaxServerRequestOptions._server_UpdateUrl;
            var type = CRUD.ajaxServerRequestOptions._server_UpdateTypePost;
            console.info('Create , Ajax server ' + type + '  url:\t\t' + url);
            console.info('Create , Ajax server param data:\t' + JSON.stringify(varParamData));

            $.ajax({
                type: type,
                url: url,
                data: varParamData,
                //发送请求   beforeSend:beforeSend(XMLHttpRequest)
                beforeSend: function (XMLHttpRequest) {
                    console.info('CRUD fun_modal_Update_Form_Button_Submit_Click, Ajax server beforeSend: 将相应按钮设置 , 失效状态 , 并更改按钮文字信息.');
                    // Basic.fun_setHeader(XMLHttpRequest);
                    $('#' + Basic.page_elements.modal_Update_Form_Button_Submit).text('Loading...').attr('disabled', 'disabled');
                },
                //请求成功  success:callback(data, status, xmlHttpRequest)
                success: function (resultJsonData) {
                    console.info('CRUD fun_modal_Update_Form_Button_Submit_Click, Ajax server success: ' + resultJsonData);

                    if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, $('#' + Basic.page_elements.modal_Update_Form_InfoContainer)) == true) {

                        $('#' + Basic.page_elements.modal_Update).modal('hide');
                        $('#' + Basic.page_elements.modal_Update_Form_Button_Reset).click();
                        Controls_DataTables._dataTablesObj.ajax.reload();
                    }
                },
                //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
                error: function (resultJsonData) {
                    console.info('CRUD fun_modal_Update_Form_Button_Submit_Click, Ajax server error: ' + resultJsonData);
                    HandleServerReturnResult.fun_ServerError(resultJsonData);
                },
                //请求完成  complete:complete(XMLHttpRequest, textStatus)
                complete: function (resultJsonData) {
                    console.info('CRUD fun_modal_Update_Form_Button_Submit_Click, Ajax server complete: ' + resultJsonData);

                    $('#' + Basic.page_elements.modal_Update_Form_Button_Submit).text('Edit').removeAttr('disabled');

                }
            });
        },
        fun_validity_Update_Confirm: function () {

            var selectRowsLength = Controls_DataTables._dataTablesObj.rows('.selected').data().length;
            console.info('Currently selected number of lines: ' + selectRowsLength);
            if (selectRowsLength < 1) {
                Controls_Dialog.dialog_Info('Please select the record to be operated');
                throw new Error('Whoops!');
            } else if (selectRowsLength > 1) {
                Controls_Dialog.dialog_Info('Only a single record editing, Currently selected ' + selectRowsLength + 'strip record.');
                throw new Error('Whoops!');
            }

            var result = '';
            for (var i = 0; i < selectRowsLength; i++) {
                var rowData = Controls_DataTables._dataTablesObj.rows('.selected').data()[i];
                console.info('To operate the currently selected record id: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Id) + '\t-----name: ' + eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Name));
                result = eval('rowData.' + CRUD.ajaxServerRequestOptions._fieldTags_Id);
            }
            //console.info(result);
            return result;
        }
    }
};


var HandleServerReturnResult = {
    exceptionInfo: 'System Error ',

    fun_SuccessReturnResult: function (resultJsonData, remind, divInfoContainerObj) {
        var strJSONObj = {};
        if (assupg.JSON.isJson(resultJsonData) == false) {
            strJSONObj = assupg.JSON.convertStrToJson(resultJsonData);
            console.info('ajax server return data type:  not json Object')
        } else {
            strJSONObj = resultJsonData;
            console.info('ajax server return data type:  is json Object')
        }
        var resultCode = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_CODE_MARK);
        var resultMsgStr = eval('strJSONObj.' + ConstantsFinalValue.SERVER_RESULT_MSG_MARK);

        console.info(strJSONObj);
        try {
            console.info(assupg.JSON.convertStrToJson(resultMsgStr));
        } catch (err) {
            console.info(resultMsgStr);
        }

        //根据 , 业务逻辑 , resultCode -1,代表服务器系统异常；】且将错误提示信息
        if (ConstantsFinalValue.RESULTS_CODE_ERROR_SYSTEM == resultCode) {
            HandleServerReturnResult.fun_ServerException(resultMsgStr);
            return false;
        }
        //根据 , 业务逻辑 , resultCode 0,代表处理成功；】根据调用者，显示对话框给予提示
        if (ConstantsFinalValue.RESULTS_CODE_SUCCESS == resultCode) {
            if (remind) {
                controls.Dialog.dialog_Success(resultMsgStr);
            }
            return true;
        }
        //根据 , 业务逻辑 , resultCode 1,登陆时间过长，跳到登陆页面；】
        if (ConstantsFinalValue.RESULTS_CODE_TOKEN_FAILURE == resultCode) {
            var resultMsgJSON = assupg.JSON.convertStrToJson(resultMsgStr);
            var redirectURL = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_URL_MARK);
            var redirectTips = eval('resultMsgJSON.' + ConstantsFinalValue.SERVER_REDIRECT_TIPS_MARK);
            controls.Dialog.dialog_Success(redirectTips);
            window.location.href = ConstantsFinalValue.WEBSITE_ROOT_PATH + redirectURL;
            return false;
        }
        //根据 , 业务逻辑 , resultCode 2,代表 服务器校验数据不合法 , 且将错误提示信息 , 写入 form表单顶部的 , 信息容器中div】
        if (ConstantsFinalValue.RESULTS_CODE_ERROR_PARAMETER == resultCode) {
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
        if (ConstantsFinalValue.RESULTS_CODE_ERROR_BUSINESS == resultCode) {
            HandleServerReturnResult.fun_BusinessLogicException(resultMsgStr);
            return false;
        }

        return false;
    },
    fun_ServerException: function (resultCode, resultMsg) {
        var msg = resultCode + ' ' + resultMsg;
        controls.Dialog.dialog_Warning(msg);
    },
    fun_BusinessLogicException: function (resultMsg) {
        controls.Dialog.dialog_Warning(resultMsg);
    },
    fun_ServerError: function (resultJsonData) {
        console.error(resultJsonData);
        var msg = HandleServerReturnResult.exceptionInfo;
        // msg += ' ' + resultJsonData.responseText;
        controls.Dialog.dialog_Danger(msg);
    }
};


base = {};
base.bindingNavAjaxUrl = function (urlPath) {

    var varParamData = '';
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + urlPath;
    var type = 'GET';

    assupg.CommonModule.ajaxRequest(url, type, varParamData, {async: false}, null, null,
        function (data) {
            $('#main_RightArea').html('');
            $('#main_RightArea').html(data);
        }
    );
};


base.ItemBaseData = {
    coordinatesDriverList: null,
};
base.ItemBaseData.ajax_DepartmentAndRoleAll = function () {
    var varParamData = '';
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/getDepartmentAndRoleALLByStatusIsActivated.php';
    var type = 'GET';
    assupg.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {async: false, cache: false}, null, null,
        function (data) {
            ItemBaseData.basicData.departmentList = data.departmentList;
            ItemBaseData.basicData.roleList = data.roleList;
        }
    );
};
base.ItemBaseData.ajax_LatLngDriver_GetAllDriverCurrentLatLng = function () {
    var varParamData = '';
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxLatLngDriverGetAllDriverCurrentLatLng.php';
    var type = 'GET';

    var driverAllList = null;
    assupg.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {async: false, cache: false}, null, null,
        function (data) {
            driverAllList = data;
        }
    );

    return driverAllList;
};

base.ItemBaseData.ajax_LatLngDriver_ListByDriverUsername = function (driverUsername, phoneUploadTimestampByDate, lastPhoneUploadTimestamp) {
    var varParamData = 'driverUsername=' + driverUsername + '&phoneUploadTimestampByDate=' + phoneUploadTimestampByDate + '&lastPhoneUploadTimestamp=' + lastPhoneUploadTimestamp;
    var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/ajaxLatLngDriverListByDriverUsername.php';
    var type = 'GET';
    var latLngDriverList = null;
    assupg.CommonModule.ajaxRequestHandleSuccess(url, type, varParamData, {async: false, cache: true}, null, null,
        function (data) {
            latLngDriverList = data;
        }
    );
    return latLngDriverList;
};
