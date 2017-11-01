<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>simditor</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/simditor/simditor.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/boostrape/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/boostrape/css/bootstrap-theme.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/simditor/module.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/simditor/uploader.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/simditor/simditor.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrape/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {
            var editor = new Simditor({
                textarea: $('#editor'),
                upload : {
                    url : '${ctx}/image/upload', //文件上传的接口地址
                    params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                    fileKey: 'fileDataFileName', //服务器端获取文件数据的参数名
                    connectionCount: 2,
                    leaveConfirm: '正在上传文件'
                }
            });
        })
    </script>

</head>
<body>
<div class="container">
    <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/article/publish">
        <%--<input  type="text" name="author" value="cqs" />--%>
        <div class="form-group">
            <input type="text" class="form-control" id="title" name="title" placeholder="请输入文章标题">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" id="author" name="author" placeholder="请输入作者">
        </div>
        <div class="form-group">
            <textarea class="form-control" id="editor" name="content" autofocus placeholder="请输入文章内容"></textarea>
        </div>
        <button type="submit" class="btn btn-default">发布文章</button>
    </form>



    <div></div>
    <div></div>

    <address>
        <strong>Full Name</strong><br>
        <a href="mailto:#">first.last@example.com</a>
    </address>
</div>

</body>
</html>
