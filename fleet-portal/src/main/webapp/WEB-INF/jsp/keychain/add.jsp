<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Create start-->
<div class="modal fade" id="modal_Create" tabindex="-1" role="dialog" aria-labelledby="modal_Create" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Keychain</h4>
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
                                <div class="panel-heading">Keychain Base Information</div>
                                <div class="panel-body">

                                    <input type="hidden" id="modal_Create_Form_Element_id" name="id">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_name"><span style="color:red">*</span>Keychain Name</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="name" placeholder="Keychain Name" id="modal_Create_Form_Element_name">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

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