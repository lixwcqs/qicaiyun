<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="/resources/jsp/res.jsp" %>
<html lang="en">
<head>
    <script type="text/javascript" src="${ctx}/resources/scripts/jquery.min.js"></script>
    <!-- The jQuery library is a prerequisite for all jqSuite products -->
    <script type="javascript" src="${ctx}/resources/scripts/bootstrape/bootstrap.js"></script>

    <script src="${ctx}/resources/scripts/jqgrid/jquery.jqGrid.js"></script>
    <script src="${ctx}/resources/scripts/jqgrid/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${ctx}/resources/scripts/jianshu/article.js" type="text/javascript"></script>
    <!-- This is the localization file of the grid controlling messages, labels, etc.
    <!-- A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.css">
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css" media="screen"
          href="${ctx}/resources/styles/jqgrid/ui.jqgrid-bootstrap.css"/>
    <script>
        $.jgrid.defaults.width = 780;
        $.jgrid.defaults.styleUI = 'Bootstrap';
    </script>
    <meta charset="utf-8"/>
    <title>文章列表</title>
</head>
<body>



<ul class="media-list">
    <li class="media">
        <div class="media-left">
            <a href="#">
                <img class="media-object" style="width: 150px;height: 120px;"
                     src="http://upload-images.jianshu.io/upload_images/1669869-ce21d65d4fd9c71d.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/375/h/300"
                     alt="...">
            </a>
        </div>
        <div class="media-body">
            <h4 class="media-heading"><a href="articleInfo.jsp">原来免费的，最后全是最贵的</a></h4>
            文/怀左同学 01 昨晚睡前，和二胖聊了会天，她讲起了晚上和闺蜜吃饭，后来发现饭店竟是之前同学开的。同学死活不要钱，于是她们后来又给人家孩子买了些小礼物。 我调侃她：“没看出...
        </div>
    </li>
    <li class="media">
        <div class="media-left">
            <a href="#">
                <img class="media-object" style="width: 150px;height: 120px;"
                     src="http://upload-images.jianshu.io/upload_images/1669869-ce21d65d4fd9c71d.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/375/h/300"
                     alt="...">
            </a>
        </div>
        <div class="media-body">
            <h4 class="media-heading">原来免费的，最后全是最贵的</h4>
            文/怀左同学 01 昨晚睡前，和二胖聊了会天，她讲起了晚上和闺蜜吃饭，后来发现饭店竟是之前同学开的。同学死活不要钱，于是她们后来又给人家孩子买了些小礼物。 我调侃她：“没看出...
        </div>
    </li>
</ul>

<a onclick="createArticle()">发布文章</a>

<div style="margin-left:20px">
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        _init();

    });
</script>

</body>
</html>