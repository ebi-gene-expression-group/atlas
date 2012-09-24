<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setBundle basename="bundles.I18n" var="i18n"/>

<jsp:useBean id="colorGenerator" class="uk.ac.ebi.atlas.utils.GradientColorGenerator" scope="page"/>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
</head>

<body>

<%--Gradient color legent--%>
<table>
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

<display:table name="${heatmapOrganismParts}" htmlId="heatmapTable" id="organismPart">
    <c:forEach var="transcriptId" items="${heatmapTranscripts}">

        <display:column title="${transcriptId}">

            <div id="heatmapCell"
                 style="background-color:${colorGenerator.getCellColourString(
                        transcriptExpressions.getExpressionLevel(transcriptId, organismPart),
                                minExpressionLevel, maxExpressionLevel)}">

                <c:out value="${transcriptExpressions.getExpressionLevel(transcriptId, organismPart)}"/>
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
    <display:column title="${measurement}" property="level"/>

    <display:column title="Specificity" property="specificity"/>

</display:table>

</body>

</html>