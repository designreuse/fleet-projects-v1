<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Create start-->
<div class="modal fade" id="modal_Create" tabindex="-1" role="dialog" aria-labelledby="modal_Create" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add ClientBranch</h4>
            </div>
            <%--模态框 页头header end--%>

            <form class="form-horizontal" id="modal_Create_Form">
                <div id="modal_Create_Form_InfoContainer"></div>

                <%--模态框 modal-body start--%>
                <div class="modal-body">
                    <div class="container-fluid">

                        <%--用户基本信息 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">Client Branch Base Information</div>
                                <div class="panel-body">

                                    <input type="hidden" id="modal_Create_Form_Element_id" name="id">
                                    <div class="form-group has-feedback">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_clientName"><span style="color:red">*</span>ClientName</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="clientName" placeholder="Please enter 2 or more characters to find ClientName...."
                                                   id="modal_Create_Form_Element_clientName">
                                            <input type="text" class="form-control" name="clientId" id="modal_Create_Form_Element_clientId" style="display: none;">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_name"><span style="color:red">*</span>Branch Name</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="name" placeholder="Branch Name" id="modal_Create_Form_Element_name">
                                        </div>
                                    </div>


                                    <div class="form-group ">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_type"><span style="color:red">*</span>Type</label>
                                        <div class="col-sm-8">
                                            <select class="select form-control" name="type" id="modal_Create_Form_Element_type"></select>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_contact"><span style="color:red">*</span>Contact</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="contact" placeholder="Contact" id="modal_Create_Form_Element_contact">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_phone"><span style="color:red">*</span>Phone</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="phone" placeholder="Phone" id="modal_Create_Form_Element_phone">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_address"><span style="color:red">*</span>Address</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="address" placeholder="Address" id="modal_Create_Form_Element_address">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_latitude"><span style="color:red">*</span>Latitude</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="latitude" placeholder="Latitude" id="modal_Create_Form_Element_latitude">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_longitude"><span style="color:red">*</span>Longitude</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="longitude" placeholder="Longitude" id="modal_Create_Form_Element_longitude">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_lockOpener"><span style="color:red">*</span>Lock Opener</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="lockOpener" placeholder="Lock Opener" id="modal_Create_Form_Element_lockOpener">
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <%--用户基本信息 start--%>

                        <%--用户权限设置 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">Client Branch Permission</div>
                                <div class="panel-body">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3"><span style="color:red">*</span>Status</label>
                                        <div class="col-sm-8">
                                            <div class="radio" id="modal_Create_Form_Element_status"></div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3"><span style="color:red">*</span>Week</label>
                                        <div class="col-sm-8">
                                            <div class="checkbox" id="modal_Create_Form_Element_cronExpression"></div>
                                        </div>
                                    </div>


                                    <div class="form-group has-feedback">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_windowStartTime"><span style="color:red">*</span>Window Time</label>
                                        <div class="col-sm-3">
                                            <span class="glyphicon glyphicon-time form-control-feedback"></span>
                                            <input type="text" class="form-control" name="windowStartTime" placeholder="Window Start Time" id="modal_Create_Form_Element_windowStartTime">
                                        </div>
                                        <div class="col-sm-3">
                                            <span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
                                            <input type="text" class="form-control" name="windowEndTime" placeholder="Window End Time" id="modal_Create_Form_Element_windowEndTime">
                                        </div>
                                        <div class="col-sm-3">
                                            <span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
                                            <input type="text" class="form-control" name="windowDuration" placeholder="Window Duration" id="modal_Create_Form_Element_windowDuration">
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
                    <button type="button" class="btn btn-default" id="modal_Create_Form_Button_Cancel" data-dismiss="modal">Cancel</button>
                    <button type="reset" class="btn btn-warning" id="modal_Create_Form_Button_Reset">Reset</button>
                    <button type="button" class="btn btn-primary" id="modal_Create_Form_Button_Submit">Add</button>
                </div>
                <%--模态框 页脚modal-footer end--%>
            </form>


        </div>
    </div>
</div>
<!--modal_Create end-->