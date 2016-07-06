<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--model_Delete start-->
<div class="modal fade" id="modal_Delete" tabindex="-1" role="dialog" aria-labelledby="modal_Delete" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">


            <form class="form-horizontal" id="modal_Delete_Form">
                <%--模态框 页头header start--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Delete User Account</h4>
                </div>
                <%--模态框 页头header end--%>

                <%--模态框 modal-body start--%>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div id="modal_Delete_Form_InfoContainer"></div>
                    </div>
                </div>
                <%--模态框 modal-body end--%>

                <%--模态框 页脚modal-footer start--%>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="modal_Delete_Form_Button_Cancel" data-dismiss="modal">Cancel</button>
                    <button type="reset" class="btn btn-warning" id="modal_Delete_Form_Button_Reset" style="display: none">Reset</button>
                    <button type="button" class="btn btn-primary" id="modal_Delete_Form_Button_Submit">Delete</button>
                </div>
                <%--模态框 页脚modal-footer end--%>
            </form>
        </div>
    </div>
</div>
<!--model_Delete end-->