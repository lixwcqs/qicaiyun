<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cqs
  Date: 2017/6/2
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${ctx}/resources/scripts/jquery.min.js"></script>
<%--<script type="text/javascript" src="${ctx}/resources/scripts/jquery.rest.js"></script>--%>
<script src="http://jpillora.com/jquery.rest/dist/1/jquery.rest.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jquery.serializejson.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/commons.js"></script>
<!-- The jQuery library is a prerequisite for all jqSuite products -->
<script type="javascript" src="${ctx}/resources/scripts/bootstrape/bootstrap.js"></script>

<script src="${ctx}/resources/scripts/jqgrid/jquery.jqGrid.js"></script>
<script src="${ctx}/resources/scripts/jqgrid/grid.locale-cn.js" type="text/javascript"></script>
<!-- This is the localization file of the grid controlling messages, labels, etc.
<!-- A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<!-- The link to the CSS that the grid needs -->
<link rel="stylesheet" type="text/css" media="screen"
      href="${ctx}/resources/styles/jqgrid/ui.jqgrid-bootstrap.css"/>
<script>
    $.jgrid.defaults.width = 780;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>
<meta charset="utf-8" />