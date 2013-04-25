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

<section>


<c:import url="includes/request-preferences.jsp"/>

</section>

<section style="overflow: auto;" >

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

            <div id="heatmap" class="row stickem-container">

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

                <div id="heatmap-div" class="heatmap-position" style="display:none">

                    <table>
                        <tr>
                            <td>
                                <span id="geneCount">Showing ${geneProfiles.size()}
                                    of ${geneProfiles.getTotalResultCount()} genes found:
                                </span>
                            </td>
                            <td>
                                <c:import url="includes/gradient-legend.jsp"/>
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

    <br/>

    <div id="help-placeholder" style="display: none"></div>

</section>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.pie.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery-watermark/jquery.watermark.min.js"></script>
<!--[if lte IE 8]>
<script language="JavaScript" type="text/javascript"
src="${pageContext.request.contextPath}/resources/js/flot/excanvas.min.js"></script>
<![endif]-->

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.stickem.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/anatomogram.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/sliderAndBarChart.js"></script>

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });
</script>


<script type="text/javascript">
<!--[if IE]>
    //disable vertical header in IE
    $("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
    $("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell)');
    <![endif]-->
</script>

<script type="text/javascript">
    <!--[if lte IE 8]>
        $("#anatomogram").remove();
        $("#heatmap-div").removeClass();
        $("#gene-distribution-button").hide();//hide the bar chart button
        $("#gene-distribution-panel").hide();//hide the bar chart
        $("#slider-range-max").hide();//hide the cutoff slider
        $("#slider-help").hide();//hide the slider help
    <![endif]-->
</script>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            var anyAnatomogramFile = "${maleAnatomogramFile}" + "${femaleAnatomogramFile}"

            if ('${type}'!=='BASELINE' ) {

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
                allQueryFactorValues[${i.index}] = "${type == 'BASELINE' ? queryFactor.value : queryFactor.displayName}";
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

            searchFormModule.init("(any ${queryFactorName}s)", '${species}', '${preferences.defaultCutoff}');

            helpTooltipsModule.init('experiment');

            $('.container').stickem();
        });

    })(jQuery);

</script>