<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://ebi.ac.uk/atlas3/templates" prefix="grad" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:useBean id="transcriptExpressions" type="java.util.List" scope="request"/>
<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
</head>

<body>

${grad:getColor('2', '0', '17')}

<display:table name="${heatmapOrganismParts}" htmlId="heatmapTable" id="organismPart">

    <c:forEach var="transcriptId" items="${heatmapTranscripts}">

        <display:column title="${transcriptId}">

            <c:forEach items="${transcriptExpressions}" var="transcriptExpression">
                <c:if test="${transcriptExpression.organismPart eq organismPart}">
                    <c:if test="${transcriptExpression.transcriptId eq transcriptId}">
                        <div style="background-color:${grad:getColor(transcriptExpression.rpkm, '0', '17')}">

                            <c:out value="${transcriptExpression.rpkm}"/>
                        </div>

                    </c:if>
                </c:if>
            </c:forEach>

        </display:column>

    </c:forEach>
    <display:column title="organism part" value="${organismPart}"/>

</display:table>


<display:table name="${transcriptExpressions}" htmlId="expressionsTable" id="transcriptExpression">

    <display:column title="Transcript id" property="transcriptId"/>

    <display:column title="Organism part">
        <c:out value="${transcriptExpression.organismPart}"/><br/>
    </display:column>

    <display:column title="RPKM" property="rpkm"/>
    <display:column title="Specificity" property="specificity"/>

</display:table>

</body>
</html>