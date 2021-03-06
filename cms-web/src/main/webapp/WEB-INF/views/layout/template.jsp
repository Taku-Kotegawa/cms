<!DOCTYPE html>
<html class="no-js" lang="ja">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width" />

  <link rel="icon" href="${f:h(pageContext.request.contextPath)}/favicon.ico">
  <%-- <link rel="icon" type="image/png" href="${f:h(pageContext.request.contextPath)}/favicon.png"> --%>

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

      <div class="ml-3">
        このページの閲覧回数 : ${accessCount}
      </div>

      <%--
      <%@ include file="/WEB-INF/views/common/includes/include-debug.jsp" %>
      --%>

    </div>
  </div>

<style>
.content-header {
    padding-left: 0;
    padding-right: 0;
}

.content-header:after {
    position: relative;
    z-index: 10;
    /* right: 0; */
    bottom: -8px;
    /* left: 0; */
    display: block;
    height: 1px;
    content: "";
    background-color: #a6a6a6;
}

.nav-tabs-wrapper {
  bottom: -9px;
  z-index: 15;
}

.nav-tabs {
  border: 0;
}

.nav-tabs .nav-link.active {
  border: solid 1px #a6a6a6;
  border-bottom: 0;

}

</style>



</body>
</html>
