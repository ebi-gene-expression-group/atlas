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

<section style="overflow: auto;" class="extra-padding">
    <spring:hasBindErrors name="preferences">
        <c:set var="isPreferenceError" value="true"/>
    </spring:hasBindErrors>

    <%@ include file="includes/anatomogram-and-heatmap-react.jsp" %>
</section>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery-watermark/jquery.watermark.min.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery-hcsticky/jquery.hc-sticky.js"></script>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.ba-throttle-debounce.js"></script>


<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/geneDistribution.js"></script>


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

            searchFormModule.init($('#queryFactorValues').attr('data-placeholder'), '${preferences.defaultCutoff}', '${type.isBaseline() ? '' : preferences.defaultFoldChangeCutOff}');

            geneQueryTagEditorModule.init('#geneQuery', '${species}');

            searchFormModule.onLoadSetFocus();

            helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', $('[data-help-loc]').not('#heatmap-react [data-help-loc]'));

            $('#anatomogram').hcSticky({responsive: true});

            if ($('#gxaExperimentPageHeatmapTableStickyWrapperStickyHead').length) {
                var $w	   = $(window),
                    $t	   = $('#heatmap-table'),
                    $stickyHead = $('#gxaExperimentPageHeatmapTableStickyWrapperStickyHead'),
                    $stickyWrap  = $t.parent('#gxaExperimentPageHeatmapTableStickyWrapper');

                // Resize sticky header width to match actual headers
                var setWidths = function () {
                        $stickyHead.find('thead th').each(function (i) {
                            var $originalHeader = $t.find('thead th').eq(i),
                                widthDiff = $originalHeader.width() - $(this).width();

                            if (widthDiff !== 0) {
                                // Changing the width for elements that have an inner div has no effect (and no, outerWidth and outerHeight don’t work either)
                                if ($(this).find('div').length) {
                                    var $thisDiv = $(this).find('div');
                                    $thisDiv.width($thisDiv.width() + widthDiff);
                                } else {
                                    $(this).width($(this).width() + widthDiff);
                                }
                            }
                        });

                        //$stickyHead.width($t.width());
                    },
                    repositionStickyHead = function () {
                        // Return value of calculated allowance
                        var allowance = calcAllowance();

                        // Check if wrapper parent is overflowing along the y-axis
                        if($t.height() > $stickyWrap.height()) {
                            // If it is overflowing (advanced layout)
                            // Position sticky header based on wrapper scrollTop()
                            if($stickyWrap.scrollTop() + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() > 0) {
                                // When top of wrapping parent is out of view
                                $stickyHead.add($stickyInsct).css({
                                    opacity: 1,
                                    top: $stickyWrap.scrollTop() + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() + parseInt($stickyWrap.css("padding-top").replace("px", ""))
                                });
                            } else {
                                // When top of wrapping parent is in view
                                $stickyHead.css({
                                    opacity: 0,
                                    top: 0
                                });
                            }
                        } else {
                            // If it is not overflowing (basic layout)
                            // Position sticky header based on viewport scrollTop
                            if($w.scrollTop() + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() > $t.offset().top && $w.scrollTop() + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() < $t.offset().top + $t.outerHeight() - allowance) {
                                // When top of viewport is in the table itself
                                $stickyHead.css({
                                    opacity: 1,
                                    top: $w.scrollTop() - $t.offset().top + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() + parseInt($stickyWrap.css("padding-top").replace("px", ""))
                                });
                            } else if ($t.offset().top && $w.scrollTop() + $('#gxaExperimentPageHeatmapCountAndLegend').outerHeight() > $t.offset().top + $t.outerHeight() - allowance) {
                                // Sticky header past allowance. Keep calm and continue scrolling.
                            } else {
                                // When top of viewport is above or below table
                                $stickyHead.css({
                                    opacity: 0,
                                    top: 0
                                });
                            }
                        }
                    },
                    calcAllowance = function () {
                        var a = 0;
                        // Calculate allowance: number of bottom rows from which the sticky head disappears
                        $t.find('tbody tr:lt(3)').each(function () {
                            a += $(this).height();
                        });

                        // Set fail safe limit (last three row might be too tall)
                        // Set arbitrary limit at 0.25 of viewport height, or you can use an arbitrary pixel value
                        if(a > $w.height()*0.25) {
                            a = $w.height()*0.25;
                        }

                        // Add the height of sticky header
                        a += $stickyHead.height();
                        return a;
                    };

                setWidths();
                $t.parent('#gxaExperimentPageHeatmapTableStickyWrapper').scroll(function() {
                    repositionStickyHead();
                });

                $w
                .load(setWidths)
                .resize(function () {
                    setWidths();
                    repositionStickyHead();
                })
                .scroll(repositionStickyHead);
            }
        });
    })(jQuery);

</script>