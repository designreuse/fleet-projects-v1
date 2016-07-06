<%--右侧区域--%>
<div id="page-wrapper">

    <%--右侧区域：头部标题--%>
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Monitor</h1>
        </div>
    </div>
    <%--右侧区域：头部标题--%>

    <%--右侧区域：整个表格区域--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">

                <%--右侧区域：整个表格，按钮操作栏--%>
                <div class="panel-heading">
                </div><!-- /.panel-heading -->

                <%--右侧区域：整个表格，表格列表--%>
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <div class="container-fluid">

                            <div id="map" class="map"></div>

                        </div>
                    </div>

                </div>
                <!-- /.panel-body -->

            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>

</div>
<!-- /#page-wrapper -->
<%--右侧区域--%>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/item/monitor/MonitorManage.js"></script>
