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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:import url="includes/request-preferences.jsp"/>

<c:if test="${not empty geneProfiles}">

    <div class="container">

        <div id="heatmap" style="overflow: auto; padding:15px;" class="row stickem-container">

            <div id="anatomogram" class="aside stickem double-click-noselection">

                <table style="font-size:10px" id="heatmap-legenda">
                    <tr>
                        <td>
                            <div style="color:white" class="gradient-level">
                                <fmt:formatNumber type="number" value="${minExpressionLevel}" groupingUsed="false"/>
                            </div>
                        </td>
                        <td width="200px">
                            <div data-help-loc="#gradient" style="background-image:
                                    -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.minColour}), color-stop(1, ${colourGradient.maxColour}));

                                    background-image: -moz-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                    background-image: -o-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1,
                                    startColorstr=${colourGradient.minColour},endColorstr=${colourGradient.maxColour});">
                                &nbsp;
                            </div>
                        </td>
                        <td>
                            <div style="color:white" class="gradient-level">
                                <fmt:formatNumber type="number" value="${maxExpressionLevel}" groupingUsed="false"/>
                            </div>
                        </td>

                    </tr>
                </table>

                <table>
                    <tr>
                        <td style="width:25px;padding-top: 15px; vertical-align:top">
                            <div id="sex-toggle">
                                <img id="sex-toggle-image" title="Switch anatomogram" class="button-image"
                                     style="width:20px;height:38px;padding:2px"
                                     src="resources/images/male_selected.png"/>
                            </div>
                        </td>
                        <td>
                            <div id="anatomogramBody" data-help-loc="#anatomogram" style="width: 230px; height:
                             360px">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="heatmap-div" class="content">

                <c:import url="includes/heatmap-matrix-gene-oriented.jsp"/>

            </div>
        </div>

    </div>

    <br/>

</c:if>

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
        src="${pageContext.request.contextPath}/resources/js/searchForm.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/sliderAndBarChart.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/heatmap.js"></script>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            var experimentalFactors = [${allExperimentalFactors.size()}];

            <c:forEach varStatus="i" var="organismPart" items="${allExperimentalFactors}">
            experimentalFactors[${i.index}] = '${organismPart}';
            </c:forEach>

            var isIE8 = false;

            //disable vertical header and anatomogram in IE
            if ($.browser.msie) {

                //configuration required for any IE browser

                $("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
                $("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell)');

            }

            var experimentalFactor = "${experimentalFactor}";

            if ($.browser.msie && $.browser.version <= 8.0) {

                //configurations required for any IE 8 or lower browser

                $("#anatomogram").remove();
                $("#heatmap-div").removeClass();
                $("#gene-distribution-button").hide();//hide the bar chart button
                $("#gene-distribution").hide();//hide the bar chart
                $("#slider-range-max").hide();//hide the cutoff slider
                isIE8 = true;
            } else {

                //configurations required for any browser excepted IE version 8 or lower
                initBarChartButton();
                if (experimentalFactor == "Organism part") {
                    initAnatomogram(experimentalFactors, '${maleAnatomogramFile}', '${femaleAnatomogramFile}');
                }
            }

            //configurations required for any browser...

            if (experimentalFactor != "Organism part") {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }

            initSearchForm('${requestURI}', ${preferences.cutoff}, '${experimentAccession}', isIE8, "(all ${experimentalFactor}s)");

            $('.container').stickem();
        });

    })(jQuery);

</script>
