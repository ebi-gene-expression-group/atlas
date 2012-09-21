<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://ebi.ac.uk/atlas3/templates" prefix="grad" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
</head>

<body>

<div style="background-color:${grad:getColor(maxRpkm, minRpkm, maxRpkm)}">
    Heatmap values max = <c:out value="${maxRpkm}"/>
</div>
<br>

<div style="background-color:${grad:getColor(minRpkm, minRpkm, maxRpkm)}">
    Heatmap values min = <c:out value="${minRpkm}"/>
</div>

<display:table name="${heatmapOrganismParts}" htmlId="heatmapTable" id="organismPart" style="heatmapTable">
    <c:forEach var="transcriptId" items="${heatmapTranscripts}">

        <display:column title="${transcriptId}">

            <div id="heatmapCell"
                 style="background-color:${grad:getColor(
                        transcriptExpressions.getRpkmValue(transcriptId, organismPart),
                                minRpkm, maxRpkm)}">

                <c:out value="${transcriptExpressions.getRpkmValue(transcriptId, organismPart)}"/>
            </div>

        </display:column>

    </c:forEach>
    <display:column title="" value="${organismPart}"/>

</display:table>


<display:table name="${transcriptExpressions}" htmlId="expressionsTable" id="transcriptExpression">

    <display:column title="Transcript id" property="transcriptId"/>

    <display:column title="Organism part" property="organismPart"/>

    <display:column title="RPKM" property="rpkm"/>
    <display:column title="Specificity" property="specificity"/>

</display:table>

</body>
</html>