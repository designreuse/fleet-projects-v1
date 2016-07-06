<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Update start-->
<div class="modal fade" id="modal_Update" tabindex="-1" role="dialog" aria-labelledby="modal_Update" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Edit Task</h4>
            </div>
            <%--模态框 页头header end--%>

            <form class="form-horizontal" id="modal_Update_Form">
                <div id="modal_Update_Form_InfoContainer"></div>

                <%--模态框 modal-body start--%>
                <div class="modal-body">
                    <div class="container-fluid">

                        <%--用户基本信息 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">User Base Information</div>
                                <div class="panel-body">

                                    <input type="hidden" id="modal_Update_Form_Element_id" name="id">
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_username"><span style="color:red">*</span>UserName</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="username" placeholder="UserName" id="modal_Update_Form_Element_username">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_password"><span style="color:red">*</span>Password</label>
                                        <div class="col-sm-8">
                                            <input type="password" class="form-control" name="password" placeholder="Password" id="modal_Update_Form_Element_password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_confirmPassword"><span style="color:red">*</span>Confirm Password
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password" id="modal_Update_Form_Element_confirmPassword">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3"><span style="color:red">*</span>Full name</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" name="lastName" placeholder="Last Name" id="modal_Update_Form_Element_lastName">
                                        </div>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" name="firstName" placeholder="First Name" id="modal_Update_Form_Element_firstName">
                                        </div>
                                    </div>
                                    <div class="form-group sr-only">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_personalPhoto"><span style="color:red">*</span>Personal Photo</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="personalPhoto" placeholder="Personal Photo" id="modal_Update_Form_Element_personalPhoto">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_curp"><span style="color:red">*</span>Curp</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="curp" placeholder="Curp" id="modal_Update_Form_Element_curp">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_phone"><span style="color:red">*</span>Phone</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="phone" placeholder="Phone" id="modal_Update_Form_Element_phone">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_email"><span style="color:red">*</span>Email</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="email" placeholder="Email" id="modal_Update_Form_Element_email">
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_gender"><span style="color:red">*</span>Gender</label>
                                        <div class="col-sm-8">
                                            <select class="select form-control" name="gender" id="modal_Update_Form_Element_gender"></select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_birthday"><span style="color:red">*</span>Birthday</label>
                                        <div class="col-sm-8">
                                            <span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
                                            <input type="text" class="form-control" name="birthday" placeholder="Birthday" id="modal_Update_Form_Element_birthday" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_landline">Landline</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="landline" placeholder="Landline" id="modal_Update_Form_Element_landline">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_address">Address</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="address" placeholder="Address" id="modal_Update_Form_Element_address">
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_personalProfile">Personal Profile</label>
                                        <div class="col-sm-8">
                                            <textarea class="form-control" rows="3" cols="40" name="personalProfile" placeholder="Personal Profile"
                                                      id="modal_Update_Form_Element_personalProfile"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--用户基本信息 start--%>

                        <%--用户权限设置 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">User Permission</div>
                                <div class="panel-body">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3"><span style="color:red">*</span>Status</label>
                                        <div class="col-sm-8">
                                            <div class="radio" id="modal_Update_Form_Element_status">
                                                <%--<label><input type="radio" name="status" value="1">Active</label>--%>
                                                <%--<label><input type="radio" name="status" value="0">Deactivated</label>--%>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_departmentId"><span style="color:red">*</span>Department</label>
                                        <div class="col-sm-8">
                                            <select class="select form-control" name="departmentId" id="modal_Update_Form_Element_departmentId"></select>
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_roles"><span style="color:red">*</span>Roles:</label>
                                        <div class="col-sm-8">
                                            <select multiple size="6" class="form-control" name="roles" id="modal_Update_Form_Element_roles"></select>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <%--用户权限设置 end--%>
                    </div>

                </div>
                <%--模态框 modal-body end--%>

                <%--模态框 页脚modal-footer start--%>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="modal_Update_Form_Button_Cancel" data-dismiss="modal">Cancel</button>
                    <button type="reset" class="btn btn-warning" id="modal_Update_Form_Button_Reset">Reset</button>
                    <button type="button" class="btn btn-primary" id="modal_Update_Form_Button_Submit">Edit</button>
                </div>
                <%--模态框 页脚modal-footer end--%>
            </form>


        </div>
    </div>
</div>
<!--modal_Update end-->