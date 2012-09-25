<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setBundle basename="bundles.I18n" var="i18n"/>

<jsp:useBean id="colorGenerator" class="uk.ac.ebi.atlas.utils.GradientColorGenerator" scope="page"/>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

    <head>
        <title>Experiment</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
        <!-- old style start -->
        <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
        <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-ebi.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-grid.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-searchform.css">
        <!-- old style end -->
    </head>

    <body>

        <!-- old style start -->
        <%@ include file="layout/old/header.jsp" %>

        <div id="centeredMain">
            <div class="ae_pagecontainer">

        <!-- old style end -->

                <div id="contents" class="page-contents">

                        <div id="reload" class="block">
                            <form:form method="get"  commandName="preferences">
                                <form:hidden path="heatmapMatrixSize"/>
                                <form:errors id="heatmapMatrixSize" title="HeatmapMatrixSize" path="heatmapMatrixSize" cssClass="errorMessage" />
                                <table id="reload-form-table" class="atlas-grid">
                                    <tr>
                                        <td width="30%"><form:label path="cutoff">Expression Level Cutoff</form:label></td>
                                        <td>
                                            <form:input size="10" path="cutoff" id="cutoff"/>
                                            <form:errors path="cutoff" cssClass="errorMessage" />
                                        </td>
                                        <td rowspan="2" width="15%">
                                            <input type="submit" value="Reload Page"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><form:label path="rankingSize">Ranking Size</form:label></td>
                                        <td>
                                            <form:input size="10" path="rankingSize" id="rankingSize"/>
                                            <form:errors path="rankingSize" cssClass="errorMessage"/>
                                        </td>
                                    </tr>
                                </table>
                            </form:form>
                        </div>


                        <c:if test="${not empty heatmapTranscripts}">

                            <div id="gradientLegenda" class="block">
                                <table id="heatmap-legenda" class="atlas-grid">
                                    <thead>
                                    <tr>
                                        <th>Max</th>
                                        <th>Min</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="odd">
                                        <td>
                                            <div style="background-color:${colorGenerator.getCellColourString(maxExpressionLevel,
                                                 minExpressionLevel, maxExpressionLevel)}">
                                                <c:out value="${maxExpressionLevel}"/>
                                            </div>
                                        </td>
                                        <td>
                                            <div style="background-color:${colorGenerator.getCellColourString(minExpressionLevel,
                                                             minExpressionLevel, maxExpressionLevel)}">
                                                <c:out value="${minExpressionLevel}"/>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div id="heatmap" class="block">
                                <display:table name="${heatmapOrganismParts}" id="organismPart" htmlId="heatmap-table" class="atlas-grid">
                                    <c:forEach var="transcriptId" items="${heatmapTranscripts}">

                                        <display:column title="${transcriptId}">
                                            <c:set var="expressionLevel" value="${transcriptExpressions.getExpressionLevel(transcriptId, organismPart)}"/>
                                            <div id="heatmapCell"
                                                 style="background-color:${colorGenerator.getCellColourString(expressionLevel,
                                                                             minExpressionLevel, maxExpressionLevel)}">
                                                <c:out value="${expressionLevel}" />
                                            </div>

                                        </display:column>

                                    </c:forEach>
                                    <display:column title="" value="${organismPart}" style="font-weight: bold;"/>

                                </display:table>
                            </div>

                        </c:if>

                        <c:if test="${not empty transcriptExpressions}">

                            <div id="expressions" class="block">
                                <display:table name="${transcriptExpressions}" htmlId="expressions-table" id="transcriptExpression" class="atlas-grid">

                                    <display:column title="Transcript id" property="transcriptId"/>

                                    <fmt:message key="factor.name.ORGANISMPART" bundle="${i18n}" var="organismpart"/>
                                    <display:column title="${organismpart}" property="organismPart"/>

                                    <fmt:message key="expression.level.metric" bundle="${i18n}" var="measurement"/>
                                    <display:column title="${measurement}" property="level"/>

                                    <display:column title="Specificity" property="specificity"/>

                                </display:table>
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