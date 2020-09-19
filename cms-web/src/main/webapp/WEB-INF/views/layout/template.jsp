<!DOCTYPE html>
<html class="no-js" lang="ja">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width" />

  <%@ include file="/WEB-INF/views/common/includes/include-common.jsp" %>

  <c:set var="titleKey">
    <tiles:insertAttribute name="title" ignore="true" />
  </c:set>
  <title>
    <spring:message code="${titleKey}" text="myscaffold" />
  </title>

</head>

<body class="layout-top-nav layout-navbar-fixed layout-footer-fixed">
  <div class="wrapper">
    <tiles:insertAttribute name="header" />
    <div class="content-wrapper">
      <tiles:insertAttribute name="body" />
    </div>
  </div>
</body>
</html>
