$(function () {
    ItemBaseData.fun_selectDepartmentAndRoleAll();
    Basic.fun_Basic_Init(moduleCRUD);
});

var moduleCRUD;
moduleCRUD = {
    _tableId: 'example',
    _fieldTags_Id: 'id',
    _fieldTags_Name: 'name',
    _server_ListUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/clientBranchList.php?now:' + new Date().getTime(),
    _server_ListTypePost: 'POST',
    _server_CreateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/clientBranchAdd.php',
    _server_CreateTypePost: 'POST',
    _server_DeleteUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/clientBranchDelete.php',
    _server_DeleteTypePost: 'POST',
    _server_UpdateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/clientBranchUpdate.php',
    _server_UpdateTypePost: 'POST',
    _server_UpdateTypeGet: 'GET',

    fun_DataTables_Columns: function () {
        return [
            Controls_DataTables.column.rowDetails,
            {data: 'id', title: 'id', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'name', title: 'name', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'type', title: 'type', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'clientId', title: 'type', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'clientName', title: 'clientName', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'contact', title: 'contact', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'phone', title: 'phone', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'windowStartTime', title: 'windowStartTime', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.ClientBranch_WindowTime, visible: true},
            //{data: 'windowEndTime', title: 'windowEndTime', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.DateType_Time, visible: true},
            //{data: 'windowDuration', title: 'windowDuration', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.DateType_Time, visible: true},

            {data: 'cronExpression', title: 'cronExpression', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'keyCode', title: 'keyCode', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'keychainId', title: 'keychainId', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'keychainName', title: 'keychainName', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'address', title: 'address', sDefaultContent: '', class: 'text-center', visible: true},
            //{data: 'latitude', title: 'Coordinates', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.ClientBranch_Latitude_Longitude, visible: true},
            //{data: 'latitude', title: 'latitude', sDefaultContent: '', class: 'text-center', visible: true},
            //{data: 'longitude', title: 'longitude', sDefaultContent: '', class: 'text-center', visible: true},


            {data: "status", title: "status", sDefaultContent: "", class: "text-center", visible: true},
            {data: "createDateTime", title: "createDateTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_DateTime, visible: false},

            {data: "deleteFlag", title: "deleteFlag", sDefaultContent: "", class: "text-center", visible: false}
        ]
    },
    fun_DataTables_RowDetailsFormat: function format(rowData) {
        // `d` is the original data object for the row
        return '' +
            '<table class="table table-bordered">' +
            '   <tr class="info">' +
            '       <td class="text-right">contact:</td>' +
            '       <td class="text-left">' + rowData.contact + '</td>' +
            '       <td class="text-right">phone:</td>' +
            '       <td colspan="3" class="text-left">' + rowData.phone + '</td>' +
            '   </tr>' +
            '   <tr class="info">' +
            '       <td class="text-right">windowStartTime:</td>' +
            '       <td class="text-left">' + moment(rowData.windowStartTime).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES) + '</td>' +
            '       <td class="text-right">windowEndTime:</td>' +
            '       <td class="text-left">' + moment(rowData.windowEndTime).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES) + '</td>' +
            '       <td class="text-right">windowDuration:</td>' +
            '       <td class="text-left">' + moment(rowData.windowDuration).format(ConstantsFinalValue.FORMAT_TIME_HOURS_MINUTES) + '</td>' +
            '   </tr>' +
            '   <tr class="info">' +
            '       <td class="text-right">address:</td>' +
            '       <td class="text-left">' + rowData.address + '</td>' +
            '       <td class="text-right">latitude:</td>' +
            '       <td class="text-left">' + rowData.latitude + '</td>' +
            '       <td class="text-right">longitude:</td>' +
            '       <td class="text-left">' + rowData.longitude + '</td>' +
            '   </tr>' +
            '   <tr class="info">' +
            '       <td class="text-right">cronExpression:</td>' +
            '       <td colspan="5" class="text-left">' + rowData.cronExpression + '</td>' +
            '   </tr>' +
            '</table>';
    },

    fun_searchModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_SelectionBox_Status('modal_Search_Form_Element_status', true);
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_createDateTime', 'modal_Search_Form_Element_createDataTimeStartTime', 'modal_Search_Form_Element_createDataTimeEndTime');
    },
    fun_createModule_Init: function () {

        controls.Typeahead.init('modal_Create_Form_Element_clientName', 'modal_Create_Form_Element_clientId', ItemBaseData.ajax_FuzzyMatchingListClientByClientName);

        ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskType('modal_Create_Form_Element_type', true);
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Create_Form_Element_status');
        ItemBaseData.InitializationControlsData.fun_ClientBranch_cronExpression_CheckBox('modal_Create_Form_Element_cronExpression', 'cronExpression[]:cronExpressionArray');

        controls.ClockPicker.fun_ClockPicker('modal_Create_Form_Element_windowStartTime', 'modal_Create_Form');
        controls.ClockPicker.fun_ClockPicker('modal_Create_Form_Element_windowEndTime', 'modal_Create_Form');
        controls.ClockPicker.fun_ClockPicker('modal_Create_Form_Element_windowDuration', 'modal_Create_Form');
    },
    fun_updateModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_SelectionBox_TaskType('modal_Update_Form_Element_type', true);
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Update_Form_Element_status');
        ItemBaseData.InitializationControlsData.fun_ClientBranch_cronExpression_CheckBox('modal_Update_Form_Element_cronExpression', 'cronExpression[]:cronExpressionArray');

        controls.ClockPicker.fun_ClockPicker('modal_Update_Form_Element_windowStartTime', 'modal_Update_Form');
        controls.ClockPicker.fun_ClockPicker('modal_Update_Form_Element_windowEndTime', 'modal_Update_Form');
        controls.ClockPicker.fun_ClockPicker('modal_Update_Form_Element_windowDuration', 'modal_Update_Form');
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
            name: {
                validators: {
                    notEmpty: {
                        message: 'The Branch Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'The Branch Name must be more than 3 and less than 20 characters long'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: 'The Type is required and cannot be empty'
                    }
                }
            },
            contact: {
                validators: {
                    notEmpty: {
                        message: 'The Contact is required and cannot be empty'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'The Phone is required and cannot be empty'
                    },
                    digits: {
                        message: 'Phone number only digits'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: 'The Address is required and cannot be empty'
                    }
                }
            },
            latitude: {
                validators: {
                    notEmpty: {
                        message: 'The Latitude is required and cannot be empty'
                    }
                }
            },
            longitude: {
                validators: {
                    notEmpty: {
                        message: 'The Longitude is required and cannot be empty'
                    }
                }
            },
            lockOpener: {
                validators: {
                    notEmpty: {
                        message: 'The Longitude is required and cannot be empty'
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
            'cronExpression[]:cronExpressionArray': {
                validators: {
                    notEmpty: {
                        message: 'The Expression is required and cannot be empty'
                    }
                }
            },
            windowStartTime: {
                validators: {
                    notEmpty: {
                        message: 'The Window Start Time is required and cannot be empty'
                    }
                }
            },
            windowEndTime: {
                validators: {
                    notEmpty: {
                        message: 'The Window End Time is required and cannot be empty'
                    }
                }
            },
            windowDuration: {
                validators: {
                    notEmpty: {
                        message: 'The Window Duration is required and cannot be empty'
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
            name: {
                validators: {
                    notEmpty: {
                        message: 'The Branch Name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: 'The Branch Name must be more than 3 and less than 20 characters long'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: 'The Type is required and cannot be empty'
                    }
                }
            },
            contact: {
                validators: {
                    notEmpty: {
                        message: 'The Contact is required and cannot be empty'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'The Phone is required and cannot be empty'
                    },
                    digits: {
                        message: 'Phone number only digits'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: 'The Address is required and cannot be empty'
                    }
                }
            },
            latitude: {
                validators: {
                    notEmpty: {
                        message: 'The Latitude is required and cannot be empty'
                    }
                }
            },
            longitude: {
                validators: {
                    notEmpty: {
                        message: 'The Longitude is required and cannot be empty'
                    }
                }
            },
            lockOpener: {
                validators: {
                    notEmpty: {
                        message: 'The Longitude is required and cannot be empty'
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
            'cronExpression[]:cronExpressionArray': {
                validators: {
                    notEmpty: {
                        message: 'The Expression is required and cannot be empty'
                    }
                }
            },
            windowStartTime: {
                validators: {
                    notEmpty: {
                        message: 'The Window Start Time is required and cannot be empty'
                    }
                }
            },
            windowEndTime: {
                validators: {
                    notEmpty: {
                        message: 'The Window End Time is required and cannot be empty'
                    }
                }
            },
            windowDuration: {
                validators: {
                    notEmpty: {
                        message: 'The Window Duration is required and cannot be empty'
                    }
                }
            }
        }
    }
};