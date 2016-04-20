<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>

<div id="fastQcReports">
    <c:set var="hasFastQcReport" value="${fastQReportUtil.hasFastQC(experimentAccession,species)}"/>
    <c:if test="${hasFastQcReport}" >
        <form:form commandName="preferences" method="get" id="prefForm" >
            <c:if test="${not empty param.accessKey}">
                <input id="accessKey" name="accessKey" type="hidden" value="${param.accessKey}"/>
            </c:if>

            <span>Choose view report for:</span>
            <div style="display: inline">
                <form:select path="selectedReport">
                    <form:options items="${fastQCReports}" itemLabel="qcReport" />
                </form:select>
            </div>

            <c:set var="hasMultipleOrganism" value="${fastQReportUtil.hasMultiOrganism(experimentAccession,param.accessKey)}" />
            <c:if test="${hasMultipleOrganism}">
                <span style="margin-left: 20px;">Selected organism:</span>
                <div style="display: inline">
                    <form:select path="selectedSpecies">
                        <form:options items="${allSpecies}"/>
                    </form:select>
                </div>
            </c:if>
        </form:form>
    </c:if>
</div>

<%--<div style="padding-top: 11px; padding-left: 2%; padding-right: 2%; font-family: helvetica,arial,sans-serif; font-size: 10pt; background-color: #FFF; text-align: justify;">--%>
<div>
    <hr/>
</div>


<div id="fast-qc-content">
    <%
        // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
        // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
        Path filePath = (Path)request.getAttribute("contentPath");
        IOUtils.copy(Files.newInputStream(filePath), pageContext.getOut());
    %>
</div>

<script src="${pageContext.request.contextPath}/resources/js/lib/query-string.js"></script>

<script>
    (function ($) {

        $(document).ready(function () {

            $('#selectedReport').change(function () {
                $('#prefForm').submit();
            });

            $('#selectedSpecies').change(function () {
                $('#prefForm').submit();
            });


        });

    })(jQuery);

    (function ($, queryString) {

        $(document).ready(function () {

            function startsWith(str, test) {
                return str.lastIndexOf(test, 0) === 0;
            }

            var addAccessKeyToUrl = function (e) {
                var url = e.target.href;
                if (!startsWith(url, "javascript")) {
                    e.preventDefault();


                    var query = queryString.parse(window.location.search.slice(1));

                    if (query.accessKey) {
                        url = url + "?accessKey=" + query.accessKey;
                    }

                    window.location = url;
                }
            };

            $('#fast-qc-content').on('click', 'a', addAccessKeyToUrl);

        });

    })($, queryString);
</script>