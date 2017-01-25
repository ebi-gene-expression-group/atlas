<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.springframework.core.io.Resource" %>
<%@ page import="org.apache.commons.io.IOUtils" %>

<%
    // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
    // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
    Resource contentResource = (Resource)request.getAttribute("contentResource");
    IOUtils.copy(contentResource.getInputStream(), pageContext.getOut());
%>
