<%--@elvariable id="experimentAccession" type="java.lang.String"--%>
<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment-experiment-design.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/atlas-data-tables.css">

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"/>
</c:if>

<div class="grid_24 gxaNewSection">
    <form:form method="get" commandName="preferences" id="prefForm">

        <div class="grid_24" id="table-caption"><b>Experiment Design</b></div>

        <div class="grid_24" id="toolbar">
            <div style="float: left">
                <span>Show analysed only?</span>
                <input type="checkbox" id="showOnlyAnalysedRuns" name="showOnlyAnalysedRuns" checked="yes"/>
            </div>

            <c:if test="${!type.baseline}">
                <div style="float: right">
                    <div style="display: inline-block">
                        <form:label path="selectedContrast" cssStyle="vertical-align: middle;">Comparison: </form:label>
                        <input type="hidden" name="accessKey" value="${param.accessKey}"/>
                        <form:select path="selectedContrast" items="${contrasts}" itemValue="id" itemLabel="displayName"/>
                    </div>
                    <div style="display: inline-block; text-align: right">
                        <span style="vertical-align: middle; padding-left: 10px">Reference:</span>
                        <span style="vertical-align: middle; background-color: #FFC266; width: 20px; display: inline-block;">&nbsp;</span>
                        <span style="vertical-align: middle; padding-left: 10px;">Test:</span>
                        <span style="vertical-align: middle; background-color: #82CDCD; width: 20px; display: inline-block;">&nbsp;</span>
                    </div>
                </div>
            </c:if>
        </div>

        <div id="help-placeholder" style="display: none"></div>

        <div class="grid_24" style="padding-top: 15px;">
            <div id="download-button" style="float:right">
                <a id="download-experiment-design-link" class="gxaNoTextButton" title="Download experiment design" href="experiments/${experimentAccession}/experiment-design.tsv${accessKeyQueryString}" target="_blank">
                <img id="download-experiment-design" alt="Download experiment design" src="resources/images/download_blue_small.png"></a>
            </div>
            <table id="experiment-design-table"></table>
        </div>

    </form:form>
</div>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/experimentDesignModule.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#gxaLocalNavHome').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {
            experimentDesignTableModule.init(${assayHeaders}, ${tableData}, ${runAccessions}, ${sampleHeaders}, ${factorHeaders});

            helpTooltipsModule.init('experiment-design', '${pageContext.request.contextPath}', '');

            <c:if test="${!type.isBaseline()}">
            $('#selectedContrast').change(function () {
                $('#prefForm').submit();
            });

            $('#experiment-design-table').find('tr').each(function (index) {
                var accession = $(this).find('td:first-child').text();
                if (jQuery.inArray(accession, ${referenceAssays}) > -1) {
                    $(this).find('td').css("background-color", "#FFC266");
                }
                if (jQuery.inArray(accession, ${testAssays}) > -1) {
                    $(this).find('td').css("background-color", "#82CDCD");
                }
            });

            </c:if>

            experimentDesignTableModule.adjustTableSize();
        });
    })(jQuery);

</script>
