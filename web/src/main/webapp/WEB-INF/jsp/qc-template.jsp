<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>

<div class="grid_24 alpha gxaNewSection">
<div id="arrayDesignsQc" class="gxaExtraPadding">
    <c:if test="${fn:length(qcArrayDesigns) > 1}">

        <c:if test= "${not empty qcArrayDesigns}" />

        <form:form commandName="preferences" method="get" id="prefForm" >
            <c:if test="${not empty param.accessKey}">
                <input id="accessKey" name="accessKey" type="hidden" value="${param.accessKey}"/>
            </c:if>

            <span>Choose array design to view report for:</span>
            <div style="display:inline">
            <form:select path="arrayDesignAccession">
                <form:options items="${qcArrayDesigns}" />
             </form:select>
        </form:form>

    </c:if>
</div>
</div>

<div id="qc-content">
    <%
        // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
        // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
        Path filePath = (Path)request.getAttribute("contentPath");
        IOUtils.copy(Files.newInputStream(filePath), pageContext.getOut());
    %>
</div>

<script>
    $(function () {
        $(document).ready(function () {

            $('#arrayDesignAccession').change(function () {
                $('#prefForm').submit();
            });

        });
    });
</script>
