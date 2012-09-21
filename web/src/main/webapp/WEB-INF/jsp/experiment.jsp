<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://ebi.ac.uk/atlas3/templates" prefix="grad" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setBundle basename="bundles.I18n" var="i18n"/>

<jsp:useBean id="colorGenerator" class="uk.ac.ebi.atlas.utils.HeatMapTableColorGenerator" scope="page"/>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
</head>

<body>

<div style="background-color:${grad:getColor(maxFpkm, minFpkm, maxFpkm)}">
    Heatmap values max = <c:out value="${maxFpkm}"/>
</div>
<br>

<div style="background-color:${grad:getColor(minFpkm, minFpkm, maxFpkm)}">
    Heatmap values min = <c:out value="${minFpkm}"/>
</div>

<display:table name="${heatmapOrganismParts}" htmlId="heatmapTable" id="organismPart" style="heatmapTable">
    <c:forEach var="transcriptId" items="${heatmapTranscripts}">

        <display:column title="${transcriptId}">

            <div id="heatmapCell"
                 style="background-color:${grad:getColor(
                        transcriptExpressions.getRpkmValue(transcriptId, organismPart),
                                minFpkm, maxFpkm)}">

                <c:out value="${transcriptExpressions.getRpkmValue(transcriptId, organismPart)}"/>
            </div>

        </display:column>

    </c:forEach>
    <display:column title="" value="${organismPart}"/>

</display:table>


<display:table name="${transcriptExpressions}" htmlId="expressionsTable" id="transcriptExpression">

    <display:column title="Transcript id" property="transcriptId"/>

    <fmt:message key="factor.name.ORGANISMPART" bundle="${i18n}" var="organismpart"/>
    <display:column title="${organismpart}" property="organismPart"/>

    <fmt:message key="expression.level.metric" bundle="${i18n}" var="measurement"/>
    <display:column title="${measurement}" property="rpkm"/>

    <display:column title="Specificity" property="specificity"/>

</display:table>

</body>

</html>