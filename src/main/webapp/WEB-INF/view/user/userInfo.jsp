<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <title>基本资料</title>
    <script type="application/javascript">
        $(function () {

            <%--var _url = basePath + "/user/info/" + '${id}';--%>
            var _url = basePath + "/user/info/901107958614630401" ;
            console.log(_url);
            $.get(_url, function (data) {
                $('#sex').html(data.sex)
                $('#nickname').html(data.nickname)
                $('#qrcode').html(data.qrcode)
                $('#introduction').html(data.introduction)
            })
        })
    </script>
</head>
<body>
<form>

</form>
<div>
    性别：<span class="label label-primary" id="sex"></span>
</div>
<div>
    昵称： <span id="nickname"></span>
</div>
<div>
    个人简介：<span id="introduction"></span>
</div>
<div>
    微信二维码：<span id="qrcode"><img href="http://cdn2.jianshu.io/assets/default_avatar/4-3397163ecdb3855a0a4139c34a695885.jpg"/></span>
</div>
</body>
</html>
