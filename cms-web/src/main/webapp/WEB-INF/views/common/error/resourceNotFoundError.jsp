<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Resource Not Found Error!</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>

<body>
    <div id="wrapper">
        <h1>Resource Not Found Error!</h1>
        <div class="error">
            <c:if test="${!empty exceptionCode}">[${f:h(exceptionCode)}]</c:if>
            <spring:message code="e.sl.fw.5001" />
        </div>
        <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>

        <!------------------------------------------------------------------------->
        <hr>
        <!-- 発生した例外のスタックトレース -->
        <div>
            <details>
                <summary>Exception.stacktrace</summary>
                <pre>${pageContext.out.flush()}${exception.printStackTrace(pageContext.response.writer)}</pre>
            </details>
        </div>

        <!-- 暗黙オブジェクト(request)に格納されている値の項目一覧 -->
        <details>
            <summary>Request</summary>
            <table>
                <%
    Enumeration enum_request = request.getAttributeNames();
    while(enum_request.hasMoreElements()) {
      String key = (String)enum_request.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(request.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
            </table>
        </details>

        <!-- 暗黙オブジェクト(session)に格納されている値の項目一覧 -->
        <details>
            <summary>Session</summary>
            <table>
                <%
    Enumeration enum_session = session.getAttributeNames();
    while(enum_session.hasMoreElements()) {
      String key = (String)enum_session.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(session.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
            </table>
        </details>

    </div>
</body>

</html>
