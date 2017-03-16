<%--@elvariable id="qcArrayDesigns" type="java.util.Set<String>"--%>
<%@ page import="uk.ac.ebi.atlas.experimentpage.qc.QCReportUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
    h1 {
        color:cadetblue;
    }
</style>
<div id="qc-content">
    <%
        // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
        // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
        QCReportUtil.printContent(request, pageContext.getOut());
    %>
</div>

