<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--modal_Operate start-->
<div class="modal fade" id="modal_Operate" tabindex="-1" role="dialog" aria-labelledby="modal_Operate" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <%--模态框 页头header start--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Operate Task</h4>
            </div>
            <%--模态框 页头header end--%>

            <form class="form-horizontal" id="modal_Operate_Form">

                <div id="modal_Operate_Form_InfoContainer"></div>

                <%--模态框 modal-body start--%>
                <div class="modal-body">
                    <div class="container-fluid">

                        <%--用户基本信息 start--%>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">Task Base Information</div>
                                <div class="panel-body">

                                    <div class="col-md-7">
                                        <select multiple="multiple" size="10" name="duallistbox_demo1" class="demo1">
                                            <option value="1">Option 1</option>
                                            <option value="2">Option 2</option>
                                            <option value="3" selected="selected">Option 3</option>
                                            <option value="4">Option 4</option>
                                            <option value="5">Option 5</option>
                                            <option value="6" selected="selected">Option 6</option>
                                            <option value="7">Option 7</option>
                                            <option value="8">Option 8</option>
                                            <option value="9">Option 9</option>
                                            <option value="10">Option 10</option>
                                        </select>
                                        <br />
                                        <input id="showValue" type="button" value="show selected data" />
                                    </div>

                                </div>
                            </div>
                        </div>
                        <%--模态框 modal-body end--%>

                        <%--模态框 页脚modal-footer start--%>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" id="modal_Operate_Form_Button_Cancel" data-dismiss="modal">Cancel</button>
                            <button type="reset" class="btn btn-warning" id="modal_Operate_Form_Button_Reset">Reset</button>
                            <button type="button" class="btn btn-primary" id="modal_Operate_Form_Button_Submit">Operate</button>
                        </div>
                        <%--模态框 页脚modal-footer end--%>
            </form>

        </div>
    </div>
</div>
<!--modal_Operate end-->