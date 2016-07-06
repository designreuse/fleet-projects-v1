$(function () {
    ItemBaseData.fun_selectDepartmentAndRoleAll();
    Basic.fun_Basic_Init(moduleCRUD);
});

var moduleCRUD;
moduleCRUD = {
    _tableId: 'example',
    _fieldTags_Id: 'id',
    _fieldTags_Name: 'name',
    _server_ListUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/keychainList.php?now:' + new Date().getTime(),
    _server_ListTypePost: 'POST',
    _server_CreateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/keychainAdd.php',
    _server_CreateTypePost: 'POST',
    _server_DeleteUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/keychainDelete.php',
    _server_DeleteTypePost: 'POST',
    _server_UpdateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/keychainUpdate.php',
    _server_UpdateTypePost: 'POST',
    _server_UpdateTypeGet: 'GET',

    fun_DataTables_Columns: function () {
        return [
            Controls_DataTables.column.rowDetails,
            {data: 'id', title: 'id', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'name', title: 'name', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'status', title: 'status', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'assignDriverId', title: 'assignDriverId', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'assignDriverName', title: 'assignDriverName', sDefaultContent: '', class: 'text-center', visible: true},

            {data: "createDateTime", title: "createDateTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_DateTime, visible: true},
            {data: null, title: "Operate", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.ClientBranch_Operate, visible: true},
            {data: "deleteFlag", title: "deleteFlag", sDefaultContent: "", class: "text-center", visible: false}
        ]
    },
    fun_DataTables_RowDetailsFormat: function format(rowData) {
        var htmlInfo = '';

        ItemBaseData.ajaxGetClientBranchListByKeychainId(eval('rowData.id'), function (data) {
            if (data == null) {
                htmlInfo = 'No data available in table';
                return;
            }

            var strTableTbodyTr = '';
            $.each(data, function (i, clientBranch) {
                var classStr = (i % 2 == 0) ? '' : 'info';
                strTableTbodyTr += '' +
                    '<tr class="' + classStr + '">' +
                    '   <td>'+(i+1)+'</td>' +
                    '   <td>' + clientBranch.lockOpener + '</td>' +
                    '   <td>' + clientBranch.clientName + '</td>' +
                    '   <td>' + clientBranch.name + '</td>' +
                    '   <td>' + clientBranch.address + '</td>' +
                    '</tr>';
            });

            var strTable = '<div class="container-fluid"><div class="row"><div class="col-md-12">' +
                '<table class="table table-hover">' + strTableTbodyTr + '</table></div></div></div>';

            htmlInfo = strTable;
        });


        return htmlInfo;
    },

    fun_searchModule_Init: function () {

        ItemBaseData.InitializationControlsData.fun_SelectionBox_Status('modal_Search_Form_Element_status', true);
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_createDateTime', 'modal_Search_Form_Element_createDataTimeStartTime', 'modal_Search_Form_Element_createDataTimeEndTime');
    },
    fun_createModule_Init: function () {

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
            name: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The Keychain Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The Keychain Name must be more than 6 and less than 30 characters long'
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
            name: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The Keychain Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The Keychain Name must be more than 6 and less than 30 characters long'
                    }
                }
            }
        }
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
            var demo2 = $('.demo1').bootstrapDualListbox({
                nonSelectedListLabel: 'Non-selected',
                selectedListLabel: 'Selected',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedFilter: 'ion ([7-9]|[1][0-2])'
            });

            $("#showValue").click(function () {
                alert($('[name="duallistbox_demo1"]').val());
            });

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
    }
};