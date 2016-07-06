var moduleCRUD;
moduleCRUD = {
    _tableId: 'example',
    _fieldTags_Id: 'id',
    _fieldTags_Name: 'username',
    _server_ListUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/userList.php?now:' + new Date().getTime(),
    _server_ListTypePost: 'POST',
    _server_CreateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/userAdd.php',
    _server_CreateTypePost: 'POST',
    _server_DeleteUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/userDelete.php',
    _server_DeleteTypePost: 'POST',
    _server_UpdateUrl: ConstantsFinalValue.WEBSITE_ROOT_PATH + '/userUpdate.php',
    _server_UpdateTypePost: 'POST',
    _server_UpdateTypeGet: 'GET',

    fun_DataTables_Columns: function () {
        return [
            Controls_DataTables.column.rowDetails,
            {data: 'id', title: 'id', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'username', title: 'username', sDefaultContent: '', class: 'text-center', visible: true},

            {data: 'type', title: 'type', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'sn', title: 'sn', sDefaultContent: '', class: 'text-center', visible: false},

            {data: null, title: 'name', sDefaultContent: '', class: 'text-left', render: Controls_DataTables.column_render.FulName_LastName_FirstName, visible: true},
            {data: 'lastName', title: 'LastName', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'firstName', title: 'FirstName', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'phone', title: 'phoneNumber', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'landline', title: 'landline', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'email', title: 'email', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'gender', title: 'gender', sDefaultContent: '', class: 'text-center', visible: true},
            {data: 'birthday', title: 'birthday', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.DateType_Date, visible: false},
            {data: 'curp', title: 'curp', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'address', title: 'address', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'personalPhoto', title: 'personalPhoto', sDefaultContent: '', class: 'text-center', visible: false},
            {data: 'personalProfile', title: 'PersonalProfile', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'lastLoginTime', title: 'lastLoginTime', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.DateType_DateTime, visible: true},
            {data: 'lastLoginIp', title: 'lastLoginIp', sDefaultContent: '', class: 'text-center', visible: false},

            {data: 'departmentId', title: 'department', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.Department, visible: true},
            {data: 'roles', title: 'roles', sDefaultContent: '', class: 'text-center', render: Controls_DataTables.column_render.Role, visible: false},

            {data: "status", title: "Status", sDefaultContent: "", class: "text-center", visible: true},
            {data: "createDateTime", title: "createDateTime", sDefaultContent: "", class: "text-center", render: Controls_DataTables.column_render.DateType_DateTime, visible: true},

            {data: "deleteFlag", title: "deleteFlag", sDefaultContent: "", class: "text-center", visible: false}

        ]
    },
    fun_DataTables_RowDetailsFormat: function format(rowData) {
        // `d` is the original data object for the row
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
        ItemBaseData.InitializationControlsData.fun_SelectionBox_DepartmentList('modal_Search_Form_Element_departmentId', true);
        ItemBaseData.InitializationControlsData.fun_SelectionBox_RoleLis('modal_Search_Form_Element_roles', true);
        ItemBaseData.InitializationControlsData.fun_SelectionBox_Status('modal_Search_Form_Element_status', true);

        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_createDateTime', 'modal_Search_Form_Element_createDataTimeStartTime', 'modal_Search_Form_Element_createDataTimeEndTime');
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_DualCalendar('modal_Search_Form_Element_loginDateTime', 'modal_Search_Form_Element_lastLoginTimeStartTime', 'modal_Search_Form_Element_lastLoginTimeEndTime');
    },
    fun_createModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_SelectionBox_Gender('modal_Create_Form_Element_gender', true);
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_SingleCalendar('modal_Create_Form_Element_birthday', 'modal_Create_Form');

        ItemBaseData.InitializationControlsData.fun_RadioBox_UserType('modal_Create_Form_Element_type');
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Create_Form_Element_status');
        ItemBaseData.InitializationControlsData.fun_SelectionBox_DepartmentList('modal_Create_Form_Element_departmentId', true);
        ItemBaseData.InitializationControlsData.fun_SelectionBox_RoleLis('modal_Create_Form_Element_roles', false);
    },
    fun_updateModule_Init: function () {
        ItemBaseData.InitializationControlsData.fun_SelectionBox_Gender('modal_Update_Form_Element_gender', true);
        ItemBaseData.InitializationControlsData.fun_Daterangepicker_SingleCalendar('modal_Update_Form_Element_birthday', 'modal_Update_Form');

        ItemBaseData.InitializationControlsData.fun_RadioBox_UserType('modal_Update_Form_Element_type');
        ItemBaseData.InitializationControlsData.fun_RadioBox_Status('modal_Update_Form_Element_status');
        ItemBaseData.InitializationControlsData.fun_SelectionBox_DepartmentList('modal_Update_Form_Element_departmentId', true);
        ItemBaseData.InitializationControlsData.fun_SelectionBox_RoleLis('modal_Update_Form_Element_roles', false);

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
            username: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 4,
                        max: 20,
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
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The password must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The confirmPassword must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as userName'
                    }
                }
            },
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
            curp: {
                validators: {
                    notEmpty: {
                        message: 'The Curp is required and cannot be empty'
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
            gender: {
                validators: {
                    notEmpty: {
                        message: 'The gender is required'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: 'The birthday is required and cannot be empty'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: 'The User Type is required and cannot be empty'
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
    modal_Update_Form_BootstrapValidator_Options: {
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 4,
                        max: 20,
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
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The password must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The confirmPassword must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as userName'
                    }
                }
            },
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
            curp: {
                validators: {
                    notEmpty: {
                        message: 'The Curp is required and cannot be empty'
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
            gender: {
                validators: {
                    notEmpty: {
                        message: 'The gender is required'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: 'The birthday is required and cannot be empty'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: 'The User Type is required and cannot be empty'
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
    }
};

$(function () {
    ItemBaseData.fun_selectDepartmentAndRoleAll();
    Basic.fun_Basic_Init(moduleCRUD);
});
