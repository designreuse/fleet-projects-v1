<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form class="form-horizontal" id="modal_Search_Form">

            <input type="reset" id="modal_Search_Form_Button_Reset" style="display: none;"/>

            <div class="form-group has-feedback">


                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_name">Name</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_name" name="name" placeholder="Name" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
                </div>
                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_phone">Phone</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_phone" name="phone" placeholder="Phone" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
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