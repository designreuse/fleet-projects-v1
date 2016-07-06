<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Create start-->
<div class="modal fade" id="modal_Create" tabindex="-1" role="dialog" aria-labelledby="modal_Create" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Task</h4>
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
                                <div class="panel-heading">Task Base Information</div>
                                <div class="panel-body">


                                    <div class="form-group has-feedback">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_clientName"><span style="color:red">*</span>ClientName</label>
                                        <div class="col-sm-8">
                                            <span class="form-control-feedback">
                                                <img id="modal_Create_Form_Element_clientName_ajaxImg" class="Typeahead-spinner" src="resources/image/spinner.gif" style="display: none;">
                                            </span>
                                            <input type="text" class="form-control" name="clientName" placeholder="Please enter 2 or more characters to find ClientName...."
                                                   id="modal_Create_Form_Element_clientName">
                                            <input type="text" class="form-control" name="clientId" id="modal_Create_Form_Element_clientId" style="display: none;">
                                        </div>
                                    </div>

                                    <div class="form-group has-feedback">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_clientBranchName"><span style="color:red">*</span>BranchName</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="clientBranchName" placeholder="Please enter 2 or more characters to find BranchName"
                                                   id="modal_Create_Form_Element_clientBranchName">
                                            <input type="text" class="form-control" name="clientBranchId" id="modal_Create_Form_Element_clientBranchId" style="display: none;">
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label class="col-sm-3 control-label" for="modal_Create_Form_Element_taskType">TaskType</label>
                                        <div class="col-sm-8">
                                            <select id="modal_Create_Form_Element_taskType" name="type" type="text" class="form-control"></select>
                                        </div>
                                    </div>
                                    <div class="form-group has-feedback">
                                        <label class="control-label col-sm-3" for="modal_Create_Form_Element_executionDate"><span style="color:red">*</span>Execution Date</label>
                                        <div class="col-sm-8">
                                            <span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
                                            <input type="text" class="form-control" name="executionDate" placeholder="Execution Date" id="modal_Create_Form_Element_executionDate" readonly>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <%--用户基本信息 start--%>

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