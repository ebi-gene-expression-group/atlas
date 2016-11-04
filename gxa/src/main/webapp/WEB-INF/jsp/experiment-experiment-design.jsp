<%--@elvariable id="experimentAccession" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment-experiment-design.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/atlas-data-tables.css">

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"/>
</c:if>

<section>
    <h4>Experiment Design</h4>

    <form:form method="get" commandName="preferences" id="prefForm">
        <div class="grid_23 alpha">
            <span>Show analysed only?</span>
            <input type="checkbox" id="showOnlyAnalysedRuns" name="showOnlyAnalysedRuns" checked="yes"/>
        </div>
        <div class="grid_1 omega" style="text-align:right; padding-bottom: 10px">
            <a id="download-experiment-design-link" class="gxaNoTextButton" title="Download experiment design" href="experiments/${experimentAccession}/experiment-design.tsv${accessKeyQueryString}" target="_blank">
            <img id="download-experiment-design" alt="Download experiment design" src="/gxa/resources/images/download_blue_small.png"></a>
        </div>

        <c:if test="${!type.baseline}">
            <div class="grid_20 omega" style="text-align:right">
                <form:label path="selectedContrast" cssStyle="vertical-align: middle;">Comparison: </form:label>
                <input type="hidden" name="accessKey" value="${param.accessKey}"/>
                <form:select path="selectedContrast" items="${contrasts}" itemValue="id" itemLabel="displayName"/>
            </div>
            <div class="grid_4 omega" style="text-align:right; padding-bottom: 10px">
                <span style="padding-left: 10px">Reference:</span>
                <span style="background-color: #FFC266; width: 20px; display: inline-block;">&nbsp;</span>
                <span style="padding-left: 10px;">Test:</span>
                <span style="background-color: #82CDCD; width: 20px; display: inline-block;">&nbsp;</span>
            </div>
        </c:if>
    </form:form>

    <table id="experiment-design-table"></table>

    <div id="help-placeholder" style="display: none"></div>
</section>

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
                if (jQuery.inArray(accession, ${referenceAssays}) >= 0) {
                    $(this).find('td').css("background-color", "#FFC266");
                }
                if (jQuery.inArray(accession, ${testAssays}) >= 0) {
                    $(this).find('td').css("background-color", "#82CDCD");
                }
            });

            </c:if>

            experimentDesignTableModule.adjustTableSize();
        });
    })(jQuery);

</script>
