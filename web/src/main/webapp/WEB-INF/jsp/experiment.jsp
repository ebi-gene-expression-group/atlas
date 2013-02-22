<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<c:import url="includes/request-preferences.jsp"/>


<div class="container">

<spring:hasBindErrors name="preferences">
    <c:set var="isPreferenceError" value="true"/>
</spring:hasBindErrors>


<c:choose>
    <c:when test="${empty geneProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found above the expression level cutoff for the query.
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

        <div id="heatmap" style="overflow: auto; padding:15px;" class="row stickem-container">

            <div id="anatomogram" class="aside stickem double-click-noselection">
                <table>
                    <tr>
                        <td style="padding-top: 15px; vertical-align:top">
                        <span id="sex-toggle">
                            <img id="sex-toggle-image" title="Switch anatomogram" class="button-image"
                                 style="width:20px;height:38px;padding:2px"
                                 src="resources/images/male_selected.png"/>
                        </span>
                            <!--
                            <span data-help-loc="#anatomogram"/>
                            -->
                        </td>
                        <td>
                            <div id="anatomogramBody" style="display:inline-block;width: 230px; height:360px">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="heatmap-div" class="content" style="display:none;">

                <table>
                    <tr>
                        <td>
                        <span id="geneCount">Showing ${geneProfiles.size()}
                            of ${geneProfiles.getTotalResultCount()} genes found:
                        </span>
                            <!--
                                <span data-help-loc="#resultInfo"/>
                            -->
                        </td>

                        <td>
                            <div style="float:right">
                                <table style="font-size:10px; float: right" id="heatmap-legenda">
                                    <tr>
                                        <td>
                                        <span style="display:none" class="gradient-level-min">
                                            <fmt:formatNumber type="number"
                                                              value="${geneProfiles.getMinExpressionLevel()}"
                                                              groupingUsed="false"/>
                                        </span>
                                        </td>
                                        <td width="200px">
                                            <div style="
                                                    overflow:auto;
                                                    background-image:
                                                    -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.minColour}), color-stop(1, ${colourGradient.maxColour}));

                                                    background-image: -moz-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                                    background-image: -o-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1,
                                                    startColorstr=${colourGradient.minColour},endColorstr=${colourGradient.maxColour});">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td>
                                        <span style="display:none" class="gradient-level-max">
                                            <fmt:formatNumber type="number"
                                                              value="${geneProfiles.getMaxExpressionLevel()}"
                                                              groupingUsed="false"/>
                                        </span>
                                            <span data-help-loc="#gradient"/>
                                        </td>

                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <c:import url="includes/heatmap-matrix-gene-oriented.jsp"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </c:otherwise>
</c:choose>

</div>

<br/>

<div id="help-placeholder" style="display: none"></div>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot-v07/jquery.flot.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery-watermark/jquery.watermark.min.js"></script>
<!--[if lte IE 8]>
<script language="JavaScript" type="text/javascript"
src="${pageContext.request.contextPath}/resources/js/flot-v07/excanvas.min.js"></script>
<![endif]-->

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.stickem.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/anatomogram.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/sliderAndBarChart.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/heatmap.js"></script>

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });
</script>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            //disable vertical header and anatomogram in IE
            if ($.browser.msie) {

                //configuration required for any IE browser

                $("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
                $("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell)');

            }

            var formattedQueryFactorType = "${formattedQueryFactorType}";

            var anyAnatomogramFile = "${maleAnatomogramFile}"+"${femaleAnatomogramFile}"

            if ($.browser.msie && $.browser.version <= 8.0) {

                //configurations required for any IE 8 or lower browser

                $("#anatomogram").remove();
                $("#heatmap-div").removeClass();
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution-panel").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                $("#slider-help").hide();//hide the slider help
            } else {

                loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}');

                $("#queryFactorValues").change(function () {
                    loadSliderAndPlot(${preferences.cutoff}, '${experimentAccession}');
                });

                //configurations required for any browser excepted IE version 8 or lower
                initBarChartButton();

                //ToDo: this should be replaced with a JSON array directly sent from backend layer
                var allQueryFactorValues = [${allQueryFactors.size()}];
            <c:forEach varStatus="i" var="firstFactor" items="${allQueryFactors}">
                allQueryFactorValues[${i.index}] = '${firstFactor.value}';
            </c:forEach>

                if (anyAnatomogramFile && 0 < anyAnatomogramFile.length)  {
                    initAnatomogram(allQueryFactorValues, '${maleAnatomogramFile}', '${femaleAnatomogramFile}');
                }
            }

            //configurations required for any browser...

            if (!anyAnatomogramFile || 0 === anyAnatomogramFile.length) {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }

            searchFormModule.init(${preferences.cutoff}, '${experimentAccession}', "(any ${formattedQueryFactorType}s)");

            helpTooltipsModule.init('experiment');

            initHeatmapDisplayValueToggle();

            $('.container').stickem();
        });

    })(jQuery);

</script>