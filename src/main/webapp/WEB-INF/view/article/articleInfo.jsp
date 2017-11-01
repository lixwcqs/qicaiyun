<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/resources/jsp/res.jsp" %>
<html>
<head>
    <title>文章查看</title>

    <script src="${ctx}/resources/scripts/jianshu/article.js" type="text/javascript"></script>
    <script type="application/javascript">
        $(function () {
            console.log('---------------')
            var articleId = ${id};
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
<div>
    标题：<span class="label label-primary" id="title"></span>
</div>
<div>
    作者： <span id="author"></span>  <button type="button" class="btn btn-info" onclick="follow()">+关注</button>
</div>
<div>
    正文：<span id="content"></span>
</div>

${id}
<%--发表评论--%>
<form role="form" id="comform">
    <input type="text" value="${id}" id="articleId" name="articleId">
    <input type="text" value="2" name="userId">
    <div class="form-group">
        <label for="content">评论</label>
        <textarea class="form-control" name="content" id="content" rows="3"></textarea>
    </div>
    <input class="btn btn-default" value="Submit" onclick="commentArticle()">
</form>


<%--展示评论--%>
</body>
</html>
