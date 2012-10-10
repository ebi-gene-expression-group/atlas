<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setBundle basename="bundles.labels" var="i18n"/>

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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-ebi.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-grid.css">
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/heatmap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/anatomogram.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/chosen/chosen.css">

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.8.2.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/anatomogram.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.js"></script>


    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.8.24.custom.css">


    <script src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.8.24.custom.min.js"></script>

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
                }


            });

        })(jQuery);

    </script>

</head>

<body>


<!-- old style start -->

<%@ include file="layout/old/header.jsp" %>


<div id="centeredMain">
    <div class="ae_pagecontainer">

        <!-- old style end -->

        <div id="contents" class="page-contents">


            <jsp:include page="includes/cutoff-slider.jsp" />


            <c:if test="${not empty heatmapGenes}">

                <div id="anatomogramAndMatrix" class="block">

                    <div id="heatmap" class="block">

                        <div id="anatomogram" style="float:left">

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

                        <display:table name="${heatmapOrganismParts}" id="organismPart"
                                       htmlId="heatmap-table" class="heatmap">
                            <display:column title="" value="${organismPart}" style="font-weight: bold;"
                                            class="heatmaprow"/>

                            <c:forEach var="geneId" items="${heatmapGenes}">

                                <c:set var="expressionLevel"
                                       value="${geneExpressions.getExpressionLevel(geneId, organismPart)}"/>
                                <c:set var="cellColour"
                                       value="${colourGradient.getGradientColour(expressionLevel,minExpressionLevel, maxExpressionLevel)}"/>

                                <display:column title="<div class='rotate_text'>${geneId}</div>"
                                                headerClass='rotated_cell'
                                                style="background-color:${cellColour};color:${cellColour};font-size:1px"
                                                class="heatmaprow">
                                    <c:out value="${expressionLevel}"/>
                                </display:column>

                            </c:forEach>

                        </display:table>

                        </br>

                        <table id="heatmap-legenda" class="atlas-grid" width="300px">
                            <tr>
                                <td>
                                    <div style="color:white;background-color:${colourGradient.minColour}; float:left;">
                                        <c:out value="${minExpressionLevel}"/>
                                    </div>
                                </td>
                                <td width="100%">
                                    <div style="background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.minColour}), color-stop(1, ${colourGradient.maxColour}));

                                                background-image: -moz-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                                background-image: -o-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                                filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${colourGradient.minColour},endColorstr=${colourGradient.maxColour});">
                                        &nbsp;</div>
                                </td>
                                <td>
                                    <div
                                            style="color:white;background-color:${colourGradient.maxColour};
                                                    float:right; ">
                                        <c:out value="${maxExpressionLevel}"/>
                                    </div>
                                </td>

                            </tr>
                        </table>
                    </div>

                </div>
            </c:if>


            <c:if test="${not empty geneExpressions}">

                <div id="expressions" class="block" style="width:600px;clear:both">

                    <div>

                        <display:table style="width:100%" name="${geneExpressions}" htmlId="expressions-table"
                                       id="geneExpressions"
                                       class="atlas-grid">

                            <fmt:message key="gene.id" bundle="${i18n}" var="geneIdLabel"/>
                            <display:column title="${geneIdLabel}" property="geneId"/>

                            <fmt:message key="factor.name.ORGANISMPART" bundle="${i18n}" var="organismpart"/>
                            <display:column title="${organismpart}" property="organismPart"/>

                            <fmt:message key="expression.level.metric" bundle="${i18n}" var="measurement"/>
                            <display:column title="${measurement}" property="level"/>

                            <display:column title="Specificity" property="specificity"/>

                        </display:table>

                    </div>
                </div>

            </c:if>

        </div>

        <!-- old style start -->

    </div>
</div>

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>