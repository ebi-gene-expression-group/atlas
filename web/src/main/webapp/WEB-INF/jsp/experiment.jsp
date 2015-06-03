<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="gxaExtraPadding">
    <c:import url="includes/request-preferences.jsp"/>
</section>

<section style="overflow: auto;" class="gxaExtraPadding">
    <spring:hasBindErrors name="preferences">
        <c:set var="isPreferenceError" value="true"/>
    </spring:hasBindErrors>

    <%@ include file="includes/anatomogram-and-heatmap-react.jsp" %>
</section>

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/chosen/chosen.jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/chosen/chosen.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.flot-override.css">

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-hcsticky/jquery.hc-sticky.min.js"></script>

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/geneDistribution.js"></script>


<%@ include file="includes/flot.jsp" %>

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
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution-panel").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                $("#slider-help").hide();//hide the slider help
            }


            if (${!type.isBaseline()}) {
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution-panel").hide();//hide the bar chart
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

            $('#anatomogram').hcSticky({responsive: true});
            $('#ensembl-launcher').hcSticky({responsive: true});

            window.onload = function() {
                var slices = window.location.search.split('&');
                var geneQueryStr = slices[7];
                if(geneQueryStr != undefined) {
                    var gene = geneQueryStr.substring(geneQueryStr.lastIndexOf("=") + 1, geneQueryStr.length);
                    var geneTerms = gene.split("%09");
                    geneTerms.forEach(function(geneTerm) {
                        $('#geneQuery').tagEditor('addTag', decodeURIComponent(geneTerm.replace("+", "%20")));
                    });
                }
            };

        });
    })(jQuery);

</script>