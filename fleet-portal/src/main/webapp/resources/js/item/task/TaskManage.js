var moduleCRUD;
moduleCRUD = {
    _tableId: 'example',
    _fieldTags_Id: 'id',
    _fieldTags_Name: 'name',
    _server_ListUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/taskList.php?now:' + new Date().getTime(),
    _server_ListTypePost: 'POST',
    _server_CreateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/taskAdd.php',
    _server_CreateTypePost: 'POST',
    _server_DeleteUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/taskDelete.php',
    _server_DeleteTypePost: 'POST',
    _server_UpdateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/taskUpdate.php',
    _server_UpdateTypePost: 'POST',
    _server_UpdateTypeGet: 'GET',

    fun_DataTables_Columns: function () {
        return [
            Controls_DataTables.column.rowDetails,
            {data: "id", title: "id", sDefaultContent: "", class: "text-center", visible: false},
            {data: "name", title: "Task Name", sDefaultContent: "", class: "text-center", visible: true},
            {data: "type", title: "Task Type", sDefaultContent: "", class: "text-center", visible: true},
            {data: "executionDate", title: "Execution Time", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.Task_ExecutionDate, visible: true},
            {data: "clientBranchWindowStartTime", title: "windowStartTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_Time, visible: false},
            {data: "clientBranchWindowEndTime", title: "windowEndTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_Time, visible: false},
            {data: "clientBranchWindowDuration", title: "windowDuration", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_Time, visible: false},
            {data: "status", title: "Status", sDefaultContent: "", class: "text-center", visible: true},

            {data: "clientId", title: "clientId", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientName", title: "clientName", sDefaultContent: "", class: "text-center", visible: false},

            {data: "clientBranchId", title: "branchId", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientBranchName", title: "branchName", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientBranchContact", title: "branchContact", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientBranchPhone", title: "branchPhone", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientBranchAddress", title: "Address", sDefaultContent: "", class: "text-center", visible: true},
            {data: "clientBranchLatitude", title: "branchLatitude", sDefaultContent: "", class: "text-center", visible: false},
            {data: "clientBranchLongitude", title: "branchLongitude", sDefaultContent: "", class: "text-center", visible: false},

            {data: "operatorId", title: "CreateMode", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.Task_Create_Mode, visible: true},
            {data: "operatorName", title: "OperatorName", sDefaultContent: "", class: "text-center", visible: true},
            {data: "createDateTime", title: "createDateTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_DateTime, visible: false},

            {data: null, title: "Operate", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.Task_Operate, visible: true},

            {data: "deleteFlag", title: "deleteFlag", sDefaultContent: "", class: "text-center", visible: false}
        ]
    },
    fun_DataTables_RowDetailsFormat: function format(rowData) {
        console.info('fun_DataTables_RowDetailsFormat: ' + JSON.stringify(rowData));
        var varParamData = 'taskId=' + eval('rowData.' + moduleCRUD._fieldTags_Id);
        var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/getTaskTransactionListByTaskId.php';
        var type = 'GET';
        console.info('fun_DataTables_RowDetailsFormat , Ajax server ' + type + '  url:\t\t' + url);
        console.info('fun_DataTables_RowDetailsFormat , Ajax server param data:\t' + JSON.stringify(varParamData));

        var strTableTbodyTr = '';

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

                    //alert(resultMsgStr);
                    $.each(resultMsgJSON, function (i, taskTransactionBean) {

                        var classStr = (i % 2 == 0) ? '' : 'info';
                        strTableTbodyTr += '<tr class="' + classStr + '"><td></td><td class="text-left">'
                            + taskTransactionBean.operateType + '&nbsp;Task to '
                            + '<a href="#" class="alert-link">' + taskTransactionBean.taskOwnevName + '</a>' + '&nbsp;by&nbsp;'
                            + '<a href="#" class="alert-link">' + taskTransactionBean.operatorName + '</a>' + '&nbsp;at&nbsp;'
                            + assupg.DataFormatConversion.DateType_DateTime(taskTransactionBean.operateTime)
                            + '</td></tr>';
                    });
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

        var strTableStart = '<div class="container-fluid"><div class="row"><div class="col-md-12">'
            //var strTableStart = ''
            + '<table class="table table-hover">';
        var strTableEnd = '</table></div></div></div>';
        //var strTableEnd = '</table>';
        if (strTableTbodyTr.length <= 0) {
            strTableTbodyTr = '<tr class="warning"><td></td><td class="text-left">No data available in table</td></tr>';
        }
        return strTableStart + strTableTbodyTr + strTableEnd;
    },


    operateModule: {

        operateFrom: null,

        page_elements: {
            modal_Operate: 'modal_Operate',
            modal_Operate_Form: 'modal_Operate_Form',
            modal_Operate_Form_InfoContainer: 'modal_Operate_Form_InfoContainer',
            modal_Operate_Form_Button_Cancel: 'modal_Operate_Form_Button_Cancel',
            modal_Operate_Form_Button_Reset: 'modal_Operate_Form_Button_Reset',
            modal_Operate_Form_Button_Submit: 'modal_Operate_Form_Button_Submit'
        },

        fun_operateModule_Init: function () {
            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Reset).on('click', moduleCRUD.operateModule.fun_modal_Operate_Form_Button_Reset_Click);
            console.info('modal_Operate: Binding click method-------------------------  modal_Operate_Form_Button_Reset');

            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Submit).on('click', moduleCRUD.operateModule.fun_modal_Operate_Form_Button_Submit_Click);
            console.info('modal_Operate: Binding click method-------------------------  modal_Operate_Form_Button_Submit');

            ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskStatus('modal_Operate_Form_Element_operateType', true);


            //为form 增加 bootstrapValidator 校验框架，用于检查数据的有效性
            moduleCRUD.operateModule.operateFrom = $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form);

            moduleCRUD.operateModule.operateFrom.bootstrapValidator(CRUD.crudImplClass.modal_Operate_Form_BootstrapValidator_Options);
            console.info('modal_Create: add bootstrapValidator-------------------------  modal_Create_Form_BootstrapValidator_Options');

        },
        fun_btn_Operate_Click: function (taskId) {
            console.info('click event:  btn_Operate  用于初始化 , Operate Modal Box');
            $('#modal_Operate_Form_Element_taskId').val(taskId);

            moduleCRUD.operateModule.fun_operateModule_Init();
            //show , modal_Operate Modal Box
            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate).modal({
                backdrop: true,         //指定一个静态的背景 , 当用户点击模态框外部时不会关闭模态框。
                keyboard: true,         //当按下 escape 键时关闭模态框 , 设置为 false 时则按键无效。
                show: true              //当初始化时显示模态框。
            });
        },
        fun_modal_Operate_Form_Button_Reset_Click: function () {
            console.info('click event:  modal_Operate_Form_Button_Reset 将form表单各元素值，清空处理');
            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_InfoContainer).html('');
            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Submit).text('Operate').removeAttr('disabled');

            $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form).data('bootstrapValidator').resetForm(true);
        },
        fun_modal_Operate_Form_Button_Submit_Click: function () {
            console.info('click event:  modal_Operate_Form_Button_Submit 用于向服务器 , 提交 Operate 数据');

            //先检查 , JS检验框架 , 是否校验成功
            moduleCRUD.operateModule.operateFrom.bootstrapValidator('validate'); //先调用检验
            var valid = moduleCRUD.operateModule.operateFrom.data('bootstrapValidator').isValid();
            if (valid == false) {
                console.info(' modal_Operate bootstrap Validator fail ,return validator result: ' + valid);
                return;
            }

            var varParamData = moduleCRUD.operateModule.operateFrom.serializeJSON();
            var url = ConstantsFinalValue.WEBSITE_ROOT_PATH + '/taskAddOperate.php';
            var type = 'POST';
            console.info('Operate , Ajax server ' + type + '  url:\t\t' + url);
            console.info('Operate , Ajax server param data:\t' + JSON.stringify(varParamData));

            $.ajax({
                type: type,
                url: url,
                data: varParamData,
                // contentType: 'application/json;charset=utf-8',
                //发送请求   beforeSend:beforeSend(XMLHttpRequest)
                beforeSend: function (XMLHttpRequest) {
                    console.info('CRUD fun_modal_Operate_Form_Button_Submit_Click, Ajax server beforeSend 将相应按钮设置 , 失效状态 , 并更改按钮文字信息.');
                    // Basic.fun_setHeader(XMLHttpRequest);
                    $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Submit).text('Loading...').attr('disabled', 'disabled');
                },
                //请求成功  success:callback(data, status, xmlHttpRequest)
                success: function (resultJsonData) {
                    console.info('CRUD fun_modal_Operate_Form_Button_Submit_Click, Ajax server success: ' + resultJsonData);
                    if (HandleServerReturnResult.fun_SuccessReturnResult(resultJsonData, true, moduleCRUD.operateModule.page_elements.modal_Operate_Form_InfoContainer)) {
                        $('#' + moduleCRUD.operateModule.page_elements.modal_Operate).modal('hide');
                        $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Reset).click();
                        Controls_DataTables._dataTablesObj.ajax.reload();
                    }
                },
                //请求出错  error:error(XMLHttpRequest, textStatus, errorThrown)
                error: function (resultJsonData) {
                    console.info('CRUD fun_modal_Operate_Form_Button_Submit_Click, Ajax server error: ' + resultJsonData);
                    HandleServerReturnResult.fun_ServerError(resultJsonData);
                },
                //请求完成  complete:complete(XMLHttpRequest, textStatus)
                complete: function (resultJsonData) {
                    console.info('CRUD fun_modal_Operate_Form_Button_Submit_Click, Ajax server complete: ' + resultJsonData);
                    $('#' + moduleCRUD.operateModule.page_elements.modal_Operate_Form_Button_Submit).text('Operate').removeAttr('disabled');
                }
            });
        }
    },

    fun_searchModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_createDateTime', 'modal_Search_Form_Element_createDataTimeStartTime', 'modal_Search_Form_Element_createDataTimeEndTime');
        ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskType('modal_Search_Form_Element_taskType', true);
        ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskStatus('modal_Search_Form_Element_taskStatus', true);
    },
    fun_createModule_Init: function () {

        ItemBaseData.InitializationControlsData.fun_Daterangepicker_SingleCalendar_TaskAdd('modal_Create_Form_Element_executionDate', 'modal_Create_Form');
        ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskType('modal_Create_Form_Element_taskType', true);

        var formElementClientNameObj = $('#modal_Create_Form_Element_clientName');
        var formElementClientIdObj = $('#modal_Create_Form_Element_clientId');
        formElementClientNameObj.attr('data-provide', 'typeahead');
        formElementClientNameObj.typeahead.Constructor.prototype.blur = function () {
            var that = this;
            setTimeout(function () {
                that.hide()
            }, 250);
        };
        formElementClientNameObj.typeahead({
            items: 8,//最多显示个数
            delay: 300,//延迟时间
            source: function (query, process) {
                if (query.length < 2) {
                    return;
                }
                ItemBaseData.ajax_FuzzyMatchingListClientByClientName(query, formElementClientIdObj, process);
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

                formElementClientIdObj.val(clientBean.id);
                console.info(formElementClientIdObj.val());
                return clientBean.name;
            }
        });


        var formElementClientBranchNameObj = $('#modal_Create_Form_Element_clientBranchName');
        var formElementClientBranchIdObj = $('#modal_Create_Form_Element_clientBranchId');
        formElementClientBranchNameObj.attr('data-provide', 'typeahead');
        formElementClientBranchNameObj.typeahead.Constructor.prototype.blur = function () {
            var that = this;
            setTimeout(function () {
                that.hide()
            }, 250);
        };
        formElementClientBranchNameObj.typeahead({
            items: 8,//最多显示个数
            delay: 300,//延迟时间
            source: function (query, process) {

                if (formElementClientIdObj.val().length <= 0) {
                    Controls_Dialog.dialog_Warning('You select a client does not exist, please re-enter a valid client.');
                    return;
                }

                if (query.length < 2) {
                    return;
                }
                var clientId = formElementClientIdObj.val();
                ItemBaseData.ajax_FuzzyMatchingListClientBranchByClientAndClientName(clientId, query, formElementClientBranchIdObj, process);
            },
            matcher: function (item) {
                return true;
            },
            highlighter: function (item) {
                var clientBranchBean = _.find(ItemBaseData.ajaxReturnJSONDataList, function (p) {
                    return p.name == item;
                });
                return '' +
                    '<div class="alert alert-info"> '
                    + 'BranchName: <strong>' + clientBranchBean.name + "</strong>&nbsp;&nbsp;&nbsp;&nbsp;Phone: " + clientBranchBean.phone
                    + '<br>Address: ' + clientBranchBean.address
                    + '</div>';
            },
            updater: function (item) {
                console.info('AutoComplete，updater value: ' + item);

                var clientBranchBean = _.find(ItemBaseData.ajaxReturnJSONDataList, function (p) {
                    return p.name == item;
                });

                formElementClientBranchIdObj.val(clientBranchBean.id);
                console.info(formElementClientBranchIdObj.val());
                return clientBranchBean.name;
            }
        });

    },
    fun_updateModule_Init: function () {

    },
    modal_Create_Form_BootstrapValidator_Options: {
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            clientName: {
                validators: {
                    notEmpty: {
                        message: 'The ClientName is required and cannot be empty'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'The ClientName must be more than 2 and less than 30 characters long'
                    }
                }
            },
            clientId: {
                validators: {
                    notEmpty: {
                        message: 'Client you entered does not exist, please re-enter'
                    }
                }
            },
            clientBranchName: {
                validators: {
                    notEmpty: {
                        message: 'The BranchName is required and cannot be empty'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: 'The BranchName must be more than 2 and less than 30 characters long'
                    }
                }
            },
            clientBranchId: {
                validators: {
                    notEmpty: {
                        message: 'Branch you entered does not exist, please re-enter'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: 'The TaskType is required and cannot be empty'
                    }
                }
            },
            executionDate: {
                validators: {
                    notEmpty: {
                        message: 'The Execution Date is required and cannot be empty'
                    }
                }
            }
        }
    },
    modal_Update_Form_BootstrapValidator_Options: {
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                validators: {
                    notEmpty: {
                        message: 'The first name is required and cannot be empty'
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: 'The last name is required and cannot be empty'
                    }
                }
            },
            userName: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The username must be more than 6 and less than 30 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'The username can only consist of alphabetical, number, dot and underscore'
                    },
                    // remote: {
                    //     url: baseUrl + '/checkUserNameExists.php',
                    //     message: 'The username is not available'
                    // },
                    different: {
                        field: 'password',
                        message: 'The username and password cannot be the same as each other'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'userName',
                        message: 'The password cannot be the same as username'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password is required and cannot be empty'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'userName',
                        message: 'The password cannot be the same as userName'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: 'The birthday is required and cannot be empty'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: 'The birthday is not valid'
                    }
                }
            },
            curp: {
                validators: {
                    notEmpty: {
                        message: 'The Curp is required and cannot be empty'
                    },
                }
            },
            gender: {
                validators: {
                    notEmpty: {
                        message: 'The gender is required'
                    }
                }
            },
            phoneNumber: {
                validators: {
                    notEmpty: {
                        message: 'The Phone is required and cannot be empty'
                    },
                    digits: {}
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required and cannot be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: 'The Status is required and cannot be empty'
                    }
                }
            },
            departmentId: {
                validators: {
                    notEmpty: {
                        message: 'The Department is required and cannot be empty'
                    }
                }
            },
            roles: {
                validators: {
                    notEmpty: {
                        message: 'The Roles is required and cannot be empty'
                    }
                }
            }
        }
    },
    modal_Operate_Form_BootstrapValidator_Options: {
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            operatorType: {
                validators: {
                    notEmpty: {
                        message: 'The Client Name is required and cannot be empty'
                    }
                }
            },
            operaterComment: {
                validators: {
                    notEmpty: {
                        message: 'The Client Branch Name is required and cannot be empty'
                    }
                }
            }
        }
    }
};

$(function () {
    Basic.fun_Basic_Init(moduleCRUD);
});