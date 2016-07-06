$(function () {
    ItemBaseData.fun_selectDepartmentAndRoleAll();
    Basic.fun_Basic_Init(moduleCRUD);
});

var moduleCRUD;
moduleCRUD = {
    _tableId: 'example',
    _fieldTags_Id: 'id',
    _fieldTags_Name: 'name',
    _server_ListUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/vehicleList.php?now:' + new Date().getTime(),
    _server_ListTypePost: 'POST',
    _server_CreateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/vehicleAdd.php',
    _server_CreateTypePost: 'POST',
    _server_DeleteUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/vehicleDelete.php',
    _server_DeleteTypePost: 'POST',
    _server_UpdateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/vehicleUpdate.php',
    _server_UpdateTypePost: 'POST',
    _server_UpdateTypeGet: 'GET',

    fun_DataTables_Columns: function () {
        return [
            Controls_DataTables.column.rowDetails,
            {data: 'id', title: 'id', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'name', title: 'name', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'licensePlate', title: 'licensePlate', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'locationDeviceId', title: 'locationDeviceId', sDefaultContent: '', class: 'text-center', visible: true},

            {data: "status", title: "Status", sDefaultContent: "", class: "text-center", visible: true},
            {data: "createDateTime", title: "createDateTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_DateTime, visible: true},

            {data: "deleteFlag", title: "deleteFlag", sDefaultContent: "", class: "text-center", visible: false}

        ]
    },
    fun_DataTables_RowDetailsFormat: function format(rowData) {
        // `d` is the original data object for the row
        return '';

        return '' +
            '<table class="table table-bordered">' +
            '   <tr class="info">' +
            '       <td class="text-right">Email:</td>' +
            '       <td class="text-left">' + rowData.email + '</td>' +
            '       <td class="text-right">Roles:</td>' +
            '       <td class="text-left">' + Controls_DataTables.column_render.Role(rowData.roles) + '</td>' +
            '   </tr>' +
            '   <tr class="info">' +
            '       <td class="text-right">CreateDateTime:</td>' +
            '       <td class="text-left">' + moment(rowData.createDateTime).format(ConstantsFinalValue.FORMAT_DATE_TIME) + '</td>' +
            '       <td class="text-right">LastLoginTime:</td>' +
            '       <td class="text-left">' + moment(rowData.lastLoginTime).format(ConstantsFinalValue.FORMAT_DATE_TIME) + '</td>' +
            '   </tr>' +
            '   <tr class="info">' +
            '       <td class="text-right">PersonalProfile:</td>' +
            '       <td colspan="3" class="text-left">' + rowData.personalProfile + '</td>' +
            '   </tr>' +
            '</table>';
    },

    fun_searchModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_SelectionBox_Status('modal_Search_Form_Element_status', true);
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_createDateTime', 'modal_Search_Form_Element_createDataTimeStartTime', 'modal_Search_Form_Element_createDataTimeEndTime');
    },
    fun_createModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Create_Form_Element_status');
    },
    fun_updateModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Update_Form_Element_status');
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
                        message: 'The Vehicle Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'The Vehicle Name must be more than 3 and less than 20 characters long'
                    }
                }
            },
            licensePlate: {
                validators: {
                    notEmpty: {
                        message: 'The LicensePlate is required and cannot be empty'
                    }
                }
            },
            locationDeviceId: {
                validators: {
                    notEmpty: {
                        message: 'The Location DeviceId is required and cannot be empty'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: 'The Status is required and cannot be empty'
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
                        message: 'The Vehicle Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'The Vehicle Name must be more than 3 and less than 20 characters long'
                    }
                }
            },
            licensePlate: {
                validators: {
                    notEmpty: {
                        message: 'The LicensePlate is required and cannot be empty'
                    }
                }
            },
            locationDeviceId: {
                validators: {
                    notEmpty: {
                        message: 'The Location DeviceId is required and cannot be empty'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: 'The Status is required and cannot be empty'
                    }
                }
            }
        }
    }
};