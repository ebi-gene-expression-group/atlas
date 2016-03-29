<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.min.css">

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/flot/0.8.3/jquery.flot.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/geneDistribution.js"></script>


<div class="grid_18 alpha gxaNewSection gxaExtraPadding">
    <c:import url="includes/request-preferences.jsp"/>
</div>

<div class="grid_24 alpha gxaNewSection gxaExtraPadding" id="gxaExperimentPageHeatmapAnatomogram">
    <spring:hasBindErrors name="preferences">
        <c:set var="isPreferenceError" value="true"/>
    </spring:hasBindErrors>
</div>


<%@ include file="includes/heatmap-react.jsp" %>

<%-- used by helpTooltipsModule and the prefForm --%>
<div id="help-placeholder" style="display: none"></div>

<script type="text/javascript">

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

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


            if (${!type.isBaseline()}) {
                $("#gxaGeneDistributionButton").hide();//hide the bar chart button
                $("#gxaGeneDistributionPanel").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                $("#slider-help").hide();//hide the slider help
            } else {

                <c:choose>
                <c:when test="${type.isProteomicsBaseline()}">
                    var loadSliderAndPlot = geneDistribution.loadProteomicsSliderAndPlot;
                </c:when>
                <c:otherwise>
                    var loadSliderAndPlot = geneDistribution.loadSliderAndPlot;
                </c:otherwise>
                </c:choose>

                loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}', $("#queryFactorValues").val(), '${preferences.queryFactorType}', '${preferences.serializedFilterFactors}', '${accessKey}');

                $("#queryFactorValues").change(function () {
                    loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}', $("#queryFactorValues").val(), '${preferences.queryFactorType}', '${preferences.serializedFilterFactors}', '${accessKey}');
                });

                //configurations required for any browser excepted IE version 8 or lower
                geneDistribution.initBarChartButton();

            }

            //configurations required for any browser...
            searchFormModule.init();
            geneQueryTagEditorModule.init('#geneQuery', '${species}');
            helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', $('[data-help-loc]').not('#heatmap-react [data-help-loc]'));

            window.onload = function() {
                var geneQueryStr = $.url('?geneQuery');
                if(geneQueryStr) {
                    var geneTerms = geneQueryStr.split("%09");
                    geneTerms.forEach(function(geneTerm) {
                        $('#geneQuery').tagEditor('addTag', decodeURIComponent(geneTerm.replace(/\+/g, "%20")));
                    });
                }
            };
        });
    })(jQuery);

</script>