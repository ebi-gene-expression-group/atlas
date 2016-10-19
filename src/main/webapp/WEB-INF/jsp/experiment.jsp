<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.min.css">

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/flot/0.8.3/jquery.flot.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/geneDistribution.js"></script>

<section>
    <div class="grid_20">
        <c:import url="includes/request-preferences.jsp"/>
    </div>
</section>

<section class="gxaSection">
    <div class="grid_24" id="gxaExperimentPageHeatmapAnatomogram">
        <spring:hasBindErrors name="preferences">
            <c:set var="isPreferenceError" value="true"/>
        </spring:hasBindErrors>
    </div>
</section>

<c:choose>
    <c:when test="${not empty pageContext.request.getParameter(\"highcharts\")}" >
    <%@ include file="includes/heatmap-highcharts.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="includes/heatmap-react.jsp" %>
    </c:otherwise>
</c:choose>


<%-- used by helpTooltipsModule and the prefForm --%>
<div id="help-placeholder" style="display: none"></div>

<script type="text/javascript">

    $(document).ready(function () {

        clearLocalNav();
        $('#gxaLocalNavHome').addClass("active");

        if ($.browser.msie && $.browser.version <= 8) {
            $("#anatomogram").remove();
            $("#heatmap-div").removeClass();
            $("#heatmap-profilesAsGeneSets").removeClass();
            $("#gxaGeneDistributionButton").hide();//hide the bar chart button
            $("#gxaGeneDistributionPanel").hide();//hide the bar chart
            $("#slider-range-max").hide();//hide the cutoff slider
            $("#slider-help").hide();//hide the slider help
        }


        if (${!type.baseline}) {
            $("#gxaGeneDistributionButton").hide();//hide the bar chart button
            $("#gxaGeneDistributionPanel").hide();//hide the bar chart
            $("#slider-range-max").hide();//hide the cutoff slider
            $("#slider-help").hide();//hide the slider help
        } else {

            <c:choose>
            <c:when test="${type.proteomicsBaseline}">
                var loadSliderAndPlot = geneDistribution.loadProteomicsSliderAndPlot;
            </c:when>
            <c:otherwise>
                var loadSliderAndPlot = geneDistribution.loadSliderAndPlot;
            </c:otherwise>
            </c:choose>

            var $queryFactorValues = $("#queryFactorValues");

            loadSliderAndPlot(
                ${preferences.cutoff},
                '${experimentAccession}',
                $queryFactorValues.val(),
                '<spring:eval expression="T(org.apache.commons.lang3.StringEscapeUtils).escapeEcmaScript(preferences.queryFactorType)"/>',
                '<spring:eval expression="T(org.apache.commons.lang3.StringEscapeUtils).escapeEcmaScript(preferences.serializedFilterFactors)"/>',
                '${accessKey}'
            );

            $queryFactorValues.change(function () {
                loadSliderAndPlot(
                    ${preferences.cutoff},
                    '${experimentAccession}',
                    $queryFactorValues.val(),
                    '<spring:eval expression="T(org.apache.commons.lang3.StringEscapeUtils).escapeEcmaScript(preferences.queryFactorType)"/>',
                    '<spring:eval expression="T(org.apache.commons.lang3.StringEscapeUtils).escapeEcmaScript(preferences.serializedFilterFactors)"/>',
                    '${accessKey}'
                );
            });

            //configurations required for any browser excepted IE version 8 or lower
            geneDistribution.initBarChartButton();

        }

        //configurations required for any browser...
        searchFormModule.init();
        geneQueryTagEditorModule.init('#geneQuery', '${species}');
        helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', $('[data-help-loc]').not('#heatmap-react [data-help-loc]'));

        // Populate gene query tag-editor
        var $geneQuery = $('#geneQuery'),
            geneQueryTags = $geneQuery.jsonTagEditor('getTags')[0].tags;
        geneQueryTags.forEach(function(geneQueryTag){
            $geneQuery.jsonTagEditor('removeTag', geneQueryTag.value);
        });

        var geneQueryStr = new URI(window.location).search(true)["geneQuery"];
        if (geneQueryStr) {
            try {
                geneQueryTags = JSON.parse(geneQueryStr);
            } catch (SyntaxError){
                geneQueryTags = [{value:geneQueryStr}];
            }
            geneQueryTags.forEach(function(geneQueryTag) {
                $geneQuery.jsonTagEditor('addTag', JSON.stringify(geneQueryTag));
            });
        }
    });

</script>