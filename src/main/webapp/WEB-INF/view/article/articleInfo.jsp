<%--
  Created by IntelliJ IDEA.
  User: cqs
  Date: 2017/8/6
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <title>文章查看</title>
    <script type="application/javascript">
        $(function () {
            console.log('---------------')
            var articleId = ${id};
             articleId = '901074896795279362';
             console.log(articleId)
            var _url = basePath + "/article/find/" + articleId;
            console.log("_URL:"+_url);
            $.get(_url, function (data) {
                console.log(data.title);
                $('#title').html(data.title);
                $('#author').html(data.author);
                $('#content').html(data.content);
            });
            var _cm_url = 'http://localhost:8080/jianshu/comment/list/' + articleId;
            console.log(_cm_url);
            $.get(_cm_url, function (data) {
                console.log(data);
            })
        })
    </script>
</head>
<body>
<form>

</form>
<div>
    标题：<span class="label label-primary" id="title"></span>
</div>
<div>
    作者： <span id="author"></span>  <button type="button" class="btn btn-info" onclick="follow()">+关注</button>
</div>
<div>
    正文：<span id="content"></span>
</div>


<%--发表评论--%>
<form role="form">
    <div class="form-group">
        <%--评论人--%>
        <input type="text" class="form-control" value="901107958614630401">
    </div>
    <div class="form-group">
        <label for="comment">评论</label>
        <textarea class="form-control" id="comment" rows="3"></textarea>
    </div>
    <input class="btn btn-default" value="Submit" onclick="comment()">
</form>


<%--展示评论--%>

</body>
</html>
