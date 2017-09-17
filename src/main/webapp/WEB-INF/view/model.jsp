<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>simditor</title>

    <link rel="stylesheet" type="text/css" href="resources/styles/simditor/simditor.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/boostrape/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/boostrape/css/bootstrap-theme.css"/>
    <script type="text/javascript" src="resources/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="resources/scripts/simditor/module.js"></script>
    <script type="text/javascript" src="resources/scripts/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="resources/scripts/simditor/uploader.js"></script>
    <script type="text/javascript" src="resources/scripts/simditor/simditor.js"></script>
    <script src="resources/scripts/bootstrape/bootstrap.js"></script>
    <script type="text/javascript">
        $(function () {
            var editor = new Simditor({textarea: $('#editor')});
        })
    </script>

</head>
<body>
<div class="container">
    <form class="form-horizontal">
        <div class="form-group">
            <input type="text" class="form-control" id="title" placeholder="请输入文章标题">
        </div>
        <div class="form-group">
            <textarea class="form-control" id="editor" name="content" autofocus placeholder="请输入文章内容"></textarea>
        </div>
        <button type="submit" class="btn btn-default">发布文章</button>
    </form>

    <div>
        <dl>
            <dt>Description lists</dt>
            <dd>A description list is perfect for defining terms.</dd>
            <dt>Euismod</dt>
            <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.
                Donec id elit non mi porta gravida at eget metus.
            </dd>
            <dt> Malesuada porta</dt>
            <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
        </dl>
    </div>


    <address>
        <strong>Twitter, Inc.</strong><br>
        1355 Market Street, Suite 900<br>
        San Francisco, CA 94103<br>
        <abbr title="Phone">P:</abbr> (123) 456-7890
    </address>

    <address>
        <strong>Full Name</strong><br>
        <a href="mailto:#">first.last@example.com</a>
    </address>
</div>

</body>
</html>
