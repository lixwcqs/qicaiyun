<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>

<div class="container">

    <form class="form-signin" METHOD="post">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="邮箱" required autofocus>
        <div> </div> <br/>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="密码" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住
            </label>
        </div>
        <button class="btn btn-lg btn-primary " type="submit">登录</button>
    </form>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<%--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>--%>
</body>
</html>
