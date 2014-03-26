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

<section class="extra-padding">

    <c:import url="includes/request-preferences.jsp"/>

</section>

<section id="stickem-container" style="overflow: auto;" class="extra-padding">

    <spring:hasBindErrors name="preferences">
        <c:set var="isPreferenceError" value="true"/>
    </spring:hasBindErrors>

    <c:set var="isExperimentPage" value="true" scope="request"/>
    <%@ include file="includes/anatomogram-and-heatmap.jsp" %>

    <br/>

    <div id="help-placeholder" style="display: none"></div>

    <%@ include file="includes/transcript-breakdown-popup.jsp" %>

</section>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery-watermark/jquery.watermark.min.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.stickem.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/anatomogramModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/sliderAndBarChart.js"></script>


<script type="text/javascript">

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            clearLocalNav();
            $('#local-nav-home').addClass("active");

            if ($.browser.msie && $.browser.version <= 8) {
                $("#anatomogram").remove();
                $("#heatmap-div").removeClass();
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution-panel").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                $("#slider-help").hide();//hide the slider help
            }

            var anyAnatomogramFile = "${maleAnatomogramFile}" + "${femaleAnatomogramFile}";

            if (${!type.isBaseline()}) {

                $("#anatomogram").remove();
                $("#heatmap-div").removeClass();
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution-panel").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                $("#slider-help").hide();//hide the slider help
            } else {

                loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}', '${preferences.queryFactorType}', '${preferences.serializedFilterFactors}');

                $("#queryFactorValues").change(function () {
                    loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}', '${preferences.queryFactorType}', '${preferences.serializedFilterFactors}');
                });

                //configurations required for any browser excepted IE version 8 or lower
                initBarChartButton();

                //ToDo: this should be replaced with a JSON array directly sent from backend layer
                var allQueryFactorValues = [${allQueryFactors.size()}];
            <c:forEach varStatus="i" var="queryFactor" items="${allQueryFactors}">
                allQueryFactorValues[${i.index}] = "${type.isBaseline() ? queryFactor.valueOntologyTerm : queryFactor.displayName}";
            </c:forEach>

                if (anyAnatomogramFile && 0 < anyAnatomogramFile.length) {
                    anatomogramModule.init(allQueryFactorValues, '${maleAnatomogramFile}', '${femaleAnatomogramFile}', '${pageContext.request.contextPath}');
                }
            }

            //configurations required for any browser...

            if (!anyAnatomogramFile || 0 === anyAnatomogramFile.length) {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }

            searchFormModule.init($('#queryFactorValues').attr('data-placeholder'), '${species}', '${preferences.defaultCutoff}', '${type.isBaseline() ? '' : preferences.defaultFoldChangeCutOff}');

            helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', '');

            $('#stickem-container').stickem();
        });

    })(jQuery);

</script>