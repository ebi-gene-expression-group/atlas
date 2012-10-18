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

                </br>

                <table id="heatmap-legenda" width="400px">
                    <tr>
                        <td>
                            <div>
                                <c:out value="${maxExpressionLevel}"/>
                            </div>
                        </td>
                        <td width="100%">
                            <div style="background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.maxColour}), color-stop(1, ${colourGradient.minColour}));

                                    background-image: -moz-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    background-image: -o-linear-gradient(left, ${colourGradient.maxColour}, ${colourGradient.minColour});

                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${colourGradient.maxColour},endColorstr=${colourGradient.minColour});">
                                &nbsp;</div>
                        </td>
                        <td>
                            <div>
                                <c:out value="${minExpressionLevel}"/>
                            </div>
                        </td>

                    </tr>
                </table>

                <br/>

                <div>

                    <display:table style="width:100%" name="${geneExpressions}" htmlId="expressions-table"
                                   id="geneExpressions"
                                   class="table-grid">

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
        </div>




    </c:if>


</div>

    <!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>