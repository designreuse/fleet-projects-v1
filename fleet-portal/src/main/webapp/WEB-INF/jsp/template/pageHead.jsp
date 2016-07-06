<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String staticResourcesPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "";
    //staticResourcesPath = "http://assupg.ngrok.cc" + path;
%>

<!-- ========================== pageHead start ========================== -->
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script type="text/javascript" charset="UTF-8"
src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/bootstrap/dist/js/html5shiv/3.7.2/html5shiv.min.js"></script>
<script type="text/javascript" charset="UTF-8"
src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/bootstrap/dist/js/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- JS Libs start -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/jquery-loadJSON/1.0.0/jquery.loadJSON.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/jquery-serializeJSON/2.7.2/jquery.serializejson.min.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/js-dateformat-moment/2.13.0/moment.min.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/js-underscore/1.8.3/underscore-min.js"></script>
<!-- JS Libs end -->

<!-- Bootstrap Core CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- bootstrap-daterangepicker CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/bootstrap-daterangepicker/2.1.19/daterangepicker.min.css">
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/bootstrap-daterangepicker/2.1.19/daterangepicker.min.css">
<!-- bootstrap-daterangepicker JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-daterangepicker/2.1.19/daterangepicker.min.js"></script>


<!-- bootstrap-clockpicker CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/bootstrap-clockpicker/0.0.7/dist/bootstrap-clockpicker.min.css">
<!-- bootstrap-clockpicker JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-clockpicker/0.0.7/dist/bootstrap-clockpicker.min.js"></script>


<!-- bootstrap-validator CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/bootstrap-validator/0.5.3/dist/css/bootstrapValidator.min.css">
<!-- bootstrap-validator JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-validator/0.5.3/dist/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-validator/0.5.3/dist/js/language/zh_CN.js"></script>

<!-- bootstrap-duallistbox JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-duallistbox/3.0.5/dist/jquery.bootstrap-duallistbox.min.js"></script>
<!-- bootstrap-duallistbox CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/bootstrap-duallistbox/3.0.5/dist/bootstrap-duallistbox.min.css">


<!-- DataTables CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css">
<!-- DataTables JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<!-- DataTables Responsive CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/datatables-responsive/css/responsive.dataTables.scss">
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>


<!-- DataTables-detailed CSS -->
<style type="text/css">
    td.details-control {
        background: url('<%=staticResourcesPath%>/resources/image/datatables-row-detailed/details_open.png') no-repeat center center;
        cursor: pointer;
    }

    tr.shown td.details-control {
        background: url('<%=staticResourcesPath%>/resources/image/datatables-row-detailed/details_close.png') no-repeat center center;
    }
</style>
<!-- DataTables-selectd CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/js/datatables-plugins/datatables-selectd/datatables.selectd.css">


<!-- Custom CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/dist/css/sb-admin-2.css">
<!-- Custom Theme JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/dist/js/sb-admin-2.js"></script>

<!-- Custom Fonts -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/font-awesome/css/font-awesome.min.css">

<!-- MetisMenu CSS -->
<link type="text/css" charset="utf-8" rel="stylesheet"
      href="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/metisMenu/dist/metisMenu.min.css">
<!-- Metis Menu Plugin JavaScript -->
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/startbootstrap-sb-admin-2-1.0.8/bower_components/metisMenu/dist/metisMenu.min.js"></script>


<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/bootstrap-3-typeahead/4.0.0/bootstrap3-typeahead.min.js"></script>

<%--<script type="text/javascript" charset="UTF-8" src="<%=staticResourcesPath%>/resources/js/jquery-gmap3/7.1.0/dist/gmap3.min.js"></script>--%>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/js-marker-clusterer/src/markerclusterer.js"></script>


<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/item/ConstantsFinalValue.js"></script>
<script>
    ConstantsFinalValue.WEBSITE_ROOT_PATH = '${pageContext.request.contextPath}';
    ConstantsFinalValue.WEBSITE_STATIC_RESOURCE_PATH = '<%=staticResourcesPath%>';
</script>
<script type="text/javascript" charset="UTF-8" src="<%=staticResourcesPath%>/resources/js/item/Basic.js"></script>
<script type="text/javascript" charset="UTF-8"
        src="<%=staticResourcesPath%>/resources/js/item/assupg.js"></script>
<!-- ========================== pageHead end ========================== -->
