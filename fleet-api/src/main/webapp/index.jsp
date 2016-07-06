<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <!--[if lt IE 9]> -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.6/js/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.6/js/respond/1.4.2/respond.min.js"></script>
    <!--[endif]-->
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.11.3.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.6/js/bootstrap.min.js"></script>


    <script type="text/javascript">

        $(function () {

            <%--$("#userLogin").click(function () {--%>

            <%--var url = '${pageContext.request.contextPath}/user/login';--%>
            <%--var loginAccount = $("#loginAccount").val();--%>
            <%--var loginPassword = $("#loginPassword").val();--%>
            <%--var args = "{\"loginAccount\":\"" + loginAccount + "\", \"loginPassword\":\"" + loginPassword + "\"}";--%>
            <%--alert("URL：" + url + "\n参数：" + args);--%>

            <%--$.ajax({--%>
            <%--type: 'post',--%>
            <%--url: url,--%>
            <%--contentType: 'application/json;charset=utf-8',--%>
            <%--//数据格式是json串--%>
            <%--data: args,--%>
            <%--dataType: "json",--%>
            <%--success: function (data) {//返回json结果--%>
            <%--alert(data.access_token);--%>
            <%--}--%>
            <%--})--%>
            <%--return false;--%>
            <%--});--%>

        });

    </script>

    <%--内部样式来定义CSS样式--%>
    <style type="text/css">
        .row {
            /*margin-bottom: 20px;*/
        }

        .row.row {
            /*margin-top: 10px;
            margin-bottom: 0;
            */
        }

        [class*="col-"] { /*通配的方式*/
            /*padding-top: 15px;*/
            /*padding-bottom: 15px;*/
            background-color: #eeeeee;
            /*border: 1px solid #000000;*/
        }

    </style>
</head>


<body style="background-color: #cccccc">
<%--
    .container 类用于固定宽度并支持响应式布局的容器。
    <div class="container">
    .container-fluid 类用于 100% 宽度，占据全部视口（viewport）的容器。
    <div class="container-fluid">
--%>
<div class="container-fluid">

    <div class="row">
        <div class="col-md-12 text-center">
            <h1>
                <a href="${pageContext.request.contextPath}/taskList.php">任务列表</a>
            </h1>
        </div>
    </div>


    <br/>
    <div class="row">

        <div class="col-md-6">

            <div>
                <h2>司机 所对应的Task 列表 API</h2>

                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            接口调用请求说明：
                        </div>
                        <div class="col-md-9">
                            http请求方式: GET<br/>
                            <a href="http://192.168.1.10:8080/fleet-api/taskListByDriverId.php">http://192.168.1.10:8080/fleet-api/taskListByDriverId.php</a>
                        </div>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            参数说明：
                        </div>
                        <div class="col-md-9">
                            <table class="table table-bordered table-striped table-hover text-left">
                                <tr>
                                    <td>参数</td>
                                    <td>是否必须</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>ACCESS_TOKEN</td>
                                    <td>是</td>
                                    <td>司机登陆之后的Token 请求头中</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>


                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            返回说明：
                        </div>
                        <div class="col-md-9">
                            <div>
                                正常情况下，会返回下述JSON数据包给客户端：
                                <br/>
                                <br/>
                                <div class="container-fluid">
                                    id: 1,<br/>
                                    name: "任务1",<br/>
                                    operator: 1,<br/>
                                    operatorName: "测试人员",<br/>
                                    taskType: 0,<br/>
                                    createDateTime: 1459891833000,<br/>
                                    startDateTime: null,<br/>
                                    endDateTime: null,<br/>
                                    coustomerId: 1,<br/>
                                    address: "天河区广州市天河区石东小学",<br/>
                                    addressLatitudeX: "23.1138305417",<br/>
                                    addressLatitudeY: "113.3639196018",<br/>
                                    routeId: 1,<br/>
                                    driverId: 1<br/>
                                </div>
                            </div>
                            <br/>
                            <div>
                                错误时会返回错误码等信息，JSON数据包示例如下（该示例为 户未登陆 无效错误）:
                                <br/>
                                <br/>
                                {"errcode":1,"errmsg":"用户未登陆！！！"}<br/>
                                {"errcode":1,"errmsg":"司机不存在！！！"}<br/>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <div class="col-md-6">

            <div>
                <h2>Task 详细明细 API</h2>

                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            接口调用请求说明：
                        </div>
                        <div class="col-md-9">
                            http请求方式: GET<br/>
                            <a href="http://192.168.1.10:8080/fleet-api/taskById.php?id=ID">http://192.168.1.10:8080/fleet-api/taskById.php?id=ID</a>
                        </div>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            参数说明：
                        </div>
                        <div class="col-md-9">
                            <table class="table table-bordered table-striped table-hover text-left">
                                <tr>
                                    <td>参数</td>
                                    <td>是否必须</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>ACCESS_TOKEN</td>
                                    <td>是</td>
                                    <td>司机登陆之后的Token 请求头中</td>
                                </tr>
                                <tr>
                                    <td>ID</td>
                                    <td>是</td>
                                    <td>Task ID</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>


                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            返回说明：
                        </div>
                        <div class="col-md-9">
                            <div>
                                正常情况下，会返回下述JSON数据包给客户端：
                                <br/>
                                <br/>
                                <div class="container-fluid">
                                    id: 1,<br/>
                                    name: "任务1",<br/>
                                    operator: 1,<br/>
                                    operatorName: "测试人员",<br/>
                                    taskType: 0,<br/>
                                    createDateTime: 1459891833000,<br/>
                                    startDateTime: null,<br/>
                                    endDateTime: null,<br/>
                                    coustomerId: 1,<br/>
                                    address: "天河区广州市天河区石东小学",<br/>
                                    addressLatitudeX: "23.1138305417",<br/>
                                    addressLatitudeY: "113.3639196018",<br/>
                                    routeId: 1,<br/>
                                    driverId: 1<br/>
                                </div>
                            </div>
                            <br/>
                            <div>
                                错误时会返回错误码等信息，JSON数据包示例如下（该示例为 户未登陆 无效错误）:
                                <br/>
                                <br/>
                                {"errcode":1,"errmsg":"用户未登陆！！！"}<br/>
                                {"errcode":1,"errmsg":"司机不存在！！！"}<br/>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>


    <div class="row">
        <br/>
        <div class="row">
            <div class="col-md-12 text-center">
                <h1>
                    <a href="${pageContext.request.contextPath}/userList.php">用户列表</a>
                </h1>
            </div>
        </div>


        <br/>
        <div class="row">

            <div class="col-md-6">

                <div>
                    <h2>用户登陆API</h2>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3">
                                接口调用请求说明
                            </div>
                            <div class="col-md-9">
                                http请求方式: GET<br/>
                                <a href="http://192.168.1.10:8080/fleet-api/userLogin.php?username=admin&password=admin">http://192.168.1.10:8080/fleet-api/userLogin.php?username=admin&password=admin</a>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3">
                                参数说明：
                            </div>
                            <div class="col-md-9">
                                <table class="table table-bordered table-striped table-hover text-left">
                                    <tr>
                                        <td>参数</td>
                                        <td>是否必须</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>username</td>
                                        <td>是</td>
                                        <td>用户名</td>
                                    </tr>
                                    <tr>
                                        <td>password</td>
                                        <td>是</td>
                                        <td>用户密码</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>


                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3">
                                返回说明：
                            </div>
                            <div class="col-md-9">
                                <div>
                                    正常情况下，会返回下述JSON数据包给客户端：
                                    <br/>
                                    <br/>
                                    {"access_token":"ACCESS_TOKEN","expires_in":1800}
                                    <br/>
                                    <br/>
                                    <table class="table table-bordered table-striped table-hover text-left">
                                        <tr>
                                            <td>参数</td>
                                            <td>说明</td>
                                        </tr>
                                        <tr>
                                            <td>access_token</td>
                                            <td>获取到的凭证</td>
                                        </tr>
                                        <tr>
                                            <td>expires_in</td>
                                            <td>凭证有效时间，单位：秒</td>
                                        </tr>
                                    </table>
                                </div>
                                <br/>
                                <div>
                                    错误时会返回错误码等信息，JSON数据包示例如下（该示例为 password 无效错误）:
                                    <br/>
                                    <br/>
                                    {"errcode":40013,"errmsg":"用户密码不正确！！！"}
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>


            <div class="col-md-6">
                <div>
                    <form action="${pageContext.request.contextPath}/userLogin.php" method="get">
                        <div class="form-group">

                            <label for="username" class="col-sm-3 control-label">账 号：</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="username" id="username"
                                       placeholder="username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">密 码：</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="password">
                                <button type="submit" class="btn btn-default">登陆</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>

    </div>

</div>
<br/>
<br/>
<br/>
</body>
</html>
