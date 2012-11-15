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

<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <base href="${pageContext.request.contextPath}/" />
    <!-- old style start -->

    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta content="en-GB" http-equiv="Content-Language">
    <meta content="_top" http-equiv="Window-target">
    <%--<meta content="IE=7" http-equiv="X-UA-Compatible">--%>
    <meta content="http://www.unspam.com/noemailcollection/" name="no-email-collection">
    <meta content="IE=9" http-equiv="X-UA-Compatible"/>

    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-ebi.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/old/atlas-searchform.css">
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico"/>

    <style type="text/css">
        @media print {
            body, .contents, .header, .contentsarea, .head {
                position: relative;
            }
        }
    </style>

    <!-- old style end -->

    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table-grid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/anatomogram.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/chosen/chosen.css">

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.8.2.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.9.1.custom.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/flot-v07/jquery.flot.js"></script>
    <!--[if lte IE 8]>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/flot-v07/excanvas.min.js"></script>
    <![endif]-->

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/anatomogram.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/searchForm.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/slider.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/heatmap.js"></script>

    <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.9.1.custom.min.css">


    <script>

        var x;
        (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

            $(document).ready(function () {

                var organismParts = [${heatmapOrganismParts.size()}];

                <c:forEach varStatus="i" var="organismPart" items="${heatmapOrganismParts}">
                organismParts[${i.index}] = '${organismPart}';
                </c:forEach>

                //disable vertical header and anatomogram in IE
                if ($.browser.msie) {
                    if ($.browser.version <= 8.0) {
                        $("#anatomogram").hide();
                    }
                    $("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
                    $("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell)');

                } else {
                    initAnatomogram(organismParts, '${maleAnatomogramFile}', '${femaleAnatomogramFile}');
                    initSlider(${preferences.cutoff}, '${experimentAccession}');
                    initSearchForm('${requestURI}');
                    initHeatmapDisplayValueToggle();
                }

                //$(".gradient-level").attr('style','color:white');

            });

        })(jQuery);

    </script>

</head>

<body>


<!-- old style start -->

<%@ include file="layout/old/header.jsp" %>


    <!-- old style end -->

<div id="contents" class="page-contents">

    <c:import url="includes/request-preferences.jsp" />

    <c:if test="${not empty geneProfiles}">


        <div id="heatmap" class="block">

            <div id="anatomogram" style="float:left;position:fixed" class="double-click-noselection">

                <table style="font-size:10px" id="heatmap-legenda" >
                    <tr>
                        <td>
                            <div style="color:white" class="gradient-level">
                                <fmt:formatNumber type="number" value="${maxExpressionLevel}" groupingUsed="false" />
                            </div>
                        </td>
                        <td width="200px">
                            <div style="background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.maxColour}), color-stop(1, ${colourGradient.minColour}));

                                    background-image: -moz-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    background-image: -o-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${colourGradient.maxColour},endColorstr=${colourGradient.minColour});">
                                &nbsp;
                            </div>
                        </td>
                        <td>
                            <div style="color:white" class="gradient-level">
                                <fmt:formatNumber type="number" value="${minExpressionLevel}" groupingUsed="false" />
                            </div>
                        </td>

                    </tr>
                </table>

                    <table>
                        <tr>
                            <td style="padding-top: 20px; vertical-align:top">
                                <div id="sexToggle" class="male" ${maleAnatomogramFile == femaleAnatomogramFile ? 'style="background-size:0%"':''}></div>
                            </td>
                            <td>
                                <div id="anatomogramBody" style="width: 250px; height: 400px">
                                </div>
                            </td>
                        </tr>
                    </table>
            </div>
            <div style="margin-left:310px">

                <div id="geneCount" style="font-weight:bold">Found <c:out value="${totalResultCount}"/> genes.</div>
                <div>
                    <a id="download-profiles" href="<c:out value='${downloadUrl}'/>" target="_blank"> Download Gene Expression Profiles </a>
                </div>
                <br/>

                <c:choose>
                    <c:when test="${param.organismOriented!=null}">
                        <c:import url="includes/heatmap-matrix-organism-oriented.jsp" />
                    </c:when>
                    <c:otherwise>
                        <c:import url="includes/heatmap-matrix-gene-oriented.jsp" />
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <br/>

    </c:if>


</div>

    <!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>