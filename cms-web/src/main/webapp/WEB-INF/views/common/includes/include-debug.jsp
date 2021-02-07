<%@ page import="java.util.Enumeration"%>
<%@ page import="org.springframework.context.ApplicationContext"%>

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


        <details>
            <summary>Application</summary>
            <table>
                <%
    Enumeration enum_application = application.getAttributeNames();
    while(enum_application.hasMoreElements()) {
      String key = (String)enum_application.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(application.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
            </table>
        </details>


        <details>
            <summary>Defined Bean(ROOT)</summary>
            <table>
    <%

    ApplicationContext context = (ApplicationContext) application.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");

    for(String beanName : context.getBeanDefinitionNames()) {

        out.println("<tr>");
            out.println("<td>");
            out.println(beanName);
            out.println("</td>");
        out.println("</tr>");
    }
    %>
            </table>
        </details>

        <details>
            <summary>Defined Bean(appServlet)</summary>
            <table>
    <%

    context = (ApplicationContext) application.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.appServlet");

    for(String beanName : context.getBeanDefinitionNames()) {

        out.println("<tr>");
            out.println("<td>");
            out.println(beanName);
            out.println("</td>");
        out.println("</tr>");
    }
    %>
            </table>
        </details>

        <details>
            <summary>Defined Bean(restApiServlet)</summary>
            <table>
    <%

    context = (ApplicationContext) application.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.restApiServlet");

    for(String beanName : context.getBeanDefinitionNames()) {

        out.println("<tr>");
            out.println("<td>");
            out.println(beanName);
            out.println("</td>");
        out.println("</tr>");
    }
    %>
            </table>
        </details>       