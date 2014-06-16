<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>

<div id="fastQcReports" style="width: 100%">

    <%--@elvariable id="fastQReportUtil" type="uk.ac.ebi.atlas.utils.FastQCReportUtil"--%>
    <c:set var="hasFastQcReport" value="${fastQReportUtil.hasFastQC(experimentAccession,species)}"/>
    <c:if test="${hasFastQcReport}" >

        <form:form commandName="preferences" method="get" id="prefForm" >
            <c:if test="${not empty param.accessKey}">
                <input id="accessKey" name="accessKey" type="hidden" value="${param.accessKey}"
            </c:if>

            <table cellpadding="0" cellspacing="0" border="0" style="margin-left: 10px" >
                <tr>
                    <td style="margin-left: 20px;">Choose view report for : </td>

                    <td>
                        <form:select path="selectedReport">
                            <form:options items="${fastQCReports}" itemLabel="qcReport" />
                        </form:select>
                    </td>

                </tr>
            </table>

        </form:form>

    </c:if>

    <br/>
    <hr>
</div>


<div id="fast-qc-content">

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

            $('#selectedReport').change(function () {
                $('#prefForm').submit();
            });

        });

    });

</script>