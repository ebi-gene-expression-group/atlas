<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Files" %>

    <div id="arrayDesignsQc" style="width: 50%">

        <c:if test="${fn:length(qcArrayDesigns) > 1}">

            <c:if test= "${not empty qcArrayDesigns}" />

            <form:form commandName="preferences" method="get" id="prefForm" >
                <c:if test="${not empty param.accessKey}">
                    <input id="accessKey" name="accessKey" type="hidden" value="${param.accessKey}"
                </c:if>

                <table cellpadding="0" cellspacing="0" border="0" style="margin-left: 10px" >
                    <tr>
                        <td style="margin-left: 20px;">Choose array design to view report for : </td>

                        <td>
                            <form:select path="arrayDesignAccession">
                                <form:options items="${qcArrayDesigns}" />
                             </form:select>
                        </td>

                    </tr>
                </table>

            </form:form>

        </c:if>

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