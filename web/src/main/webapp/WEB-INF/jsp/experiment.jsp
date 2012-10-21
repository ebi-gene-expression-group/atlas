<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>

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
            src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.9.0.custom.min.js"></script>

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/anatomogram.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/searchForm.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/slider.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/heatmap.js"></script>

    <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.9.0.custom.min.css">



    <script>


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
                    initAnatomogram(organismParts);
                    initSlider(${preferences.cutoff});
                    initSearchForm('experiment');
                    initHeatmapDisplayValueToggle();
                }

                $(".gradient-level").attr('style','color:white');

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

    <p>
        <div style="font-weight:bold">Found <c:out value="${totalResultCount}"/> genes.</div>
    </p>

    <c:if test="${not empty heatmapGenes}">


        <div id="heatmap" class="block">

            <div id="anatomogram" style="float:left;position:fixed" class="double-click-noselection">

                <table style="font-size:10px" id="heatmap-legenda" >
                    <tr>
                        <td>
                            <div style="text-align:right;width: 30px" class="gradient-level">
                                <fmt:formatNumber type="number" maxFractionDigits="${roundedMaxExpressionLevel >= 1 ? 0 : 1}" value="${roundedMaxExpressionLevel}" groupingUsed="false" />
                            </div>
                        </td>
                        <td width="220px">
                            <div style="background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.maxColour}), color-stop(1, ${colourGradient.minColour}));

                                    background-image: -moz-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    background-image: -o-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${colourGradient.maxColour},endColorstr=${colourGradient.minColour});">
                                &nbsp;
                            </div>
                        </td>
                        <td>
                            <div style="text-align:left;width: 30px" class="gradient-level">
                                <fmt:formatNumber type="number" maxFractionDigits="${roundedMinExpressionLevel >= 1 ? 0 : 1}" value="${roundedMinExpressionLevel}" groupingUsed="false" />
                            </div>
                        </td>

                    </tr>
                </table>


                <table>
                    <tr>
                        <td style="padding-top: 20px; vertical-align:top">
                            <div id="sexToggle" class="male"></div>
                        </td>
                        <td>
                            <div id="anatomogramBody" style="width: 250px; height: 400px">
                            </div>
                        </td>
                    </tr>
                </table>

            </div>

            <div style="margin-left:300px">
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




    </c:if>


</div>

    <!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>