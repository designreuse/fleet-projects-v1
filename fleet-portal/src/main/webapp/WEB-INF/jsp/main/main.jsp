<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <title>Task List</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/template/pageHead.jsp" %>

</head>

<body style="height: 100%;margin: 0;padding: 0">

<div id="wrapper">

    <%--引入模版：导航--%>
    <%@include file="/WEB-INF/jsp/template/pageNavigation.jsp" %>

    <div id="main_RightArea">

    </div>

</div><!-- /#wrapper -->
<%@include file="/WEB-INF/jsp/template/pageTail.jsp" %>
<script type="text/javascript" charset="UTF-8" src="<%=staticResourcesPath%>/resources/js/item/main/MainManage.js"></script>
</body>
</html>
