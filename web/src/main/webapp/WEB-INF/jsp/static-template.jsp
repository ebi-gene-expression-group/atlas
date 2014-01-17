<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set value="/resources/html/${path}/${pageName}.html" var="filePath"/>

<%@ page import="org.springframework.web.context.support.ServletContextResourceLoader, org.springframework.core.io.Resource" %>
<%@ page import="com.google.common.io.Files, java.nio.charset.Charset" %>
<%@ page import="java.io.File" %>

<%
    // manually import file instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
    // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
    String filePath = (String)pageContext.findAttribute("filePath");
    ServletContextResourceLoader loader = new ServletContextResourceLoader(getServletConfig().getServletContext());
    Resource resource = loader.getResource(filePath);
    File file = resource.getFile();

    String res = Files.toString(file, Charset.forName("UTF-8"));
    out.write(res);
 %>

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#local-nav-${nav}').addClass("active");
    });
</script>
