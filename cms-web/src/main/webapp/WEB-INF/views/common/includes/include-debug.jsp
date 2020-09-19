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