<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>SQL Error!</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>

<body>
    <div id="wrapper">
        <h1>SQL Error!!</h1>
        <div class="error">
            <c:if test="${!empty exceptionCode}">[${f:h(exceptionCode)}]</c:if>
            <spring:message code="e.sl.fw.7003" />
        </div>

<br>
<br>
<br>
<br>
<%@ include file="/WEB-INF/views/common/includes/include-debug.jsp" %>

    </div>
</body>

</html>
