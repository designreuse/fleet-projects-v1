<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form class="form-horizontal" id="modal_Search_Form">

            <input type="reset" id="modal_Search_Form_Button_Reset" style="display: none;"/>


            <div class="form-group has-feedback">

                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_driverUsername">DriverUsername</label>
                <div class="col-sm-2">
                    <input id="modal_Search_Form_Element_driverUsername" name="driverUsername" placeholder="DriverUsername" type="text" class="form-control"
                           onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">
                </div>

                <label class="col-sm-1 control-label" for="modal_Search_Form_Element_phoneUploadTimestampByDate">Date</label>
                <div class="col-sm-2">
                    <span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
                    <input type="text" class="form-control" name="phoneUploadTimestampByDate" placeholder="Date" id="modal_Search_Form_Element_phoneUploadTimestampByDate" readonly>
                </div>

                <div>
                    <button type="button" class="btn btn-success" id="btn_Search">Search</button>
                </div>
                <%--<label class="col-sm-1 control-label" for="modal_Search_Form_Element_name">VehicleName</label>--%>
                <%--<div class="col-sm-2">--%>
                <%--<input id="modal_Search_Form_Element_name" name="name" placeholder="VehicleName" type="text" class="form-control"--%>
                <%--onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">--%>
                <%--</div>--%>


                <%--<label class="col-sm-1 control-label" for="modal_Search_Form_Element_clientName">ClientName</label>--%>
                <%--<div class="col-sm-2">--%>
                <%--<input id="modal_Search_Form_Element_clientName" name="clientName" placeholder="ClientName" type="text" class="form-control"--%>
                <%--onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">--%>
                <%--</div>--%>

                <%--<label class="col-sm-1 control-label" for="modal_Search_Form_Element_clientBranchName">BranchName</label>--%>
                <%--<div class="col-sm-2">--%>
                <%--<input id="modal_Search_Form_Element_clientBranchName" name="clientBranchName" placeholder="BranchName" type="text" class="form-control"--%>
                <%--onkeydown="if(event.keyCode==13){$('#btn_Search').click();return false;}">--%>
                <%--</div>--%>

            </div>

        </form>
    </div>
</div>