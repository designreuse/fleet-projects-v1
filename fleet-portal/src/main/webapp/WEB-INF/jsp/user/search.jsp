<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form class="form-horizontal" id="modal_Search_Form">

            <input type="reset" id="modal_Search_Form_Button_Reset" style="display: none;"/>

            <div class="form-group has-feedback">
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_username">UserName</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_username" name="username" placeholder="UserName" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
                </div>
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_curp">Curp</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_curp" name="curp" placeholder="Curp" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
                </div>
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_phoneNumber">Phone</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_phoneNumber" name="phoneNumber" placeholder="Phone" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
                </div>
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_loginDateTime">LoginTime</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="loginDateTime" placeholder="Login Time" id="modal_Search_Form_Element_loginDateTime" readonly>
                    <span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
                    <input type="text" class="form-control" style="display:none" name="lastLoginTimeStartTime" placeholder="Login Start Time" id="modal_Search_Form_Element_lastLoginTimeStartTime">
                    <input type="text" class="form-control" style="display:none" name="lastLoginTimeEndTime" placeholder="Login End Time" id="modal_Search_Form_Element_lastLoginTimeEndTime">
                </div>
            </div>

            <div class="form-group has-feedback">
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_departmentId">Department</label>
                <div class="col-sm-2">
                    <select id="modal_Search_Form_Element_departmentId" name="departmentId" type="text" class="form-control"></select>
                </div>

                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_roles">Role</label>
                <div class="col-sm-2">
                    <select id="modal_Search_Form_Element_roles" name="roles" type="text" class="form-control"></select>
                </div>

                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_status">Status</label>
                <div class="col-sm-2">
                    <select id="modal_Search_Form_Element_status" name="status" type="text" class="form-control"></select>
                </div>
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_createDateTime">CreateTime</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="createDateTime" placeholder="Create Time" id="modal_Search_Form_Element_createDateTime" readonly>
                    <span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
                    <input type="text" class="form-control" style="display:none" name="createDataTimeStartTime" placeholder="Create Start Time" id="modal_Search_Form_Element_createDataTimeStartTime">
                    <input type="text" class="form-control" style="display:none" name="createDataTimeEndTime" placeholder="Create End Time" id="modal_Search_Form_Element_createDataTimeEndTime">
                </div>

            </div>
        </form>
    </div>
</div>