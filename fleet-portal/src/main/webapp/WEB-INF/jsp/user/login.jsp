<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign In</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/template/pageHead.jsp" %>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form role="form" id="userLogin_Form">
                        <%--<fieldset>--%>
                        <div class="form-group">
                            <input class="form-control" placeholder="UserName" name="username" type="text" autofocus
                                   onkeydown="if(event.keyCode==13){$('#btn_Index_Form_Button_Submit_Commit').click();return false;}">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Password" name="password" type="password" value=""
                                   onkeydown="if(event.keyCode==13){$('#btn_Index_Form_Button_Submit_Commit').click();return false;}">
                        </div>
                        <%--<div class="checkbox">--%>
                        <%--<label>--%>
                        <%--<input name="remember" type="checkbox" value="Remember Me">Remember Me--%>
                        <%--</label>--%>
                        <%--</div>--%>
                        <!-- Change this to a button or input when using this as a form -->
                        <button type="button" class="btn btn-lg btn-success btn-block" id="btn_Index_Form_Button_Submit_Commit"
                                onclick="assupg.CommonModule.login(ConstantsFinalValue.WEBSITE_ROOT_PATH + '/userLogin.php', 'POST', 'userLogin_Form', 'btn_Index_Form_Button_Submit_Commit');">Login
                        </button>
                        <%--</fieldset>--%>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/template/pageTail.jsp" %>
</body>
</html>