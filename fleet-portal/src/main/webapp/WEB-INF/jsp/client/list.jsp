<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <title>Client List</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/template/pageHead.jsp" %>
</head>

<body>

<div id="wrapper">

    <%--引入模版：导航--%>
    <%@include file="/WEB-INF/jsp/template/pageNavigation.jsp" %>

    <%--右侧区域--%>
    <div id="page-wrapper">

        <%--右侧区域：头部标题--%>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Client List</h1>
            </div>
        </div>
        <%--右侧区域：头部标题--%>


        <%--右侧区域：整个表格区域--%>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">

                    <%--右侧区域：整个表格，按钮操作栏--%>
                    <div class="panel-heading">
                        <jsp:include page="search.jsp"/>
                    </div><!-- /.panel-heading -->

                    <%--右侧区域：整个表格，表格列表--%>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <div class="container-fluid">
                                <table id="example" class="table table-responsive table-striped table-bordered table-hover text-center">
                                </table>
                            </div>
                        </div>

                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>


    </div><!-- /#page-wrapper -->
    <%--右侧区域--%>

</div><!-- /#wrapper -->
<jsp:include page="add.jsp"/>
<jsp:include page="update.jsp"/>
<jsp:include page="delete.jsp"/>

<%@include file="/WEB-INF/jsp/template/pageTail.jsp" %>
<script type="text/javascript" charset="UTF-8" src="<%=staticResourcesPath%>/resources/js/item/client/ClientManage.js"></script>
</body>
</html>
