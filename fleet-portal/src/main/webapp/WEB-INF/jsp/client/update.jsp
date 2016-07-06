<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Update start-->
<div class="modal fade" id="modal_Update" tabindex="-1" role="dialog" aria-labelledby="modal_Update" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Edit Client</h4>
            </div>
            <%--模态框 页头header end--%>

            <form class="form-horizontal" id="modal_Update_Form">
                <div id="modal_Update_Form_InfoContainer"></div>

                <input type="hidden" id="modal_Update_Form_Element_id" name="id">
                <%--模态框 modal-body start--%>
                <div class="modal-body">
                    <div class="container-fluid">

                        <%--用户基本信息 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">Client Base Information</div>
                                <div class="panel-body">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_name"><span style="color:red">*</span>Name</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="name" placeholder="Name" id="modal_Update_Form_Element_name">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Update_Form_Element_phone"><span style="color:red">*</span>Phone</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="phone" placeholder="Phone" id="modal_Update_Form_Element_phone">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--用户基本信息 start--%>

                        <%--用户权限设置 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">Client Permission</div>
                                <div class="panel-body">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3"><span style="color:red">*</span>Status</label>
                                        <div class="col-sm-8">
                                            <div class="radio" id="modal_Update_Form_Element_status"></div>
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