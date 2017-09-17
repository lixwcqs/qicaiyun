<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <title>用户注册</title>
    <script  type="application/javascript" src="${ctx}/resources/scripts/jianshu/user.js"></script>
    <%--<script  type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.rest/1.0.2/jquery.rest.js"></script>--%>
</head>
<body>

<div class="container">

    <form class="form-signin" id="r_user" method="post">
        <label for="inputNickname" class="sr-only">昵称</label>
        <input type="text" id="inputNickname" name="nickname" class="form-control" placeholder="昵称" value="七彩云" required autofocus>
        <div></div> <br/>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" id="inputEmail" name="email" class="form-control" placeholder="邮箱" value="qicaiyun@163.com" required autofocus>
        <div> </div> <br/>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" name="password" class="form-control" value="qcy" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="register()">注册</button>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="deleteUser()">删除</button>
    </form>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<%--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>--%>
</body>
</html>
