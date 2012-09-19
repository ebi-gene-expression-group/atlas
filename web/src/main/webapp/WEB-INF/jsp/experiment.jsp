<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:useBean id="expressions" type="java.util.List" scope="request"/>
<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
</head>

<body>


<display:table name="${heatmapExpressions}" htmlId="expressionTable" id="transcriptExpression">

        <c:forEach var="transcriptId" items="${heatmapTranscripts}">

            <display:column title="${transcriptId}">

                    <c:choose>
                        <c:when test="${transcriptExpression.transcriptId eq transcriptId}">
                            <c:out value="${transcriptExpression.rpkm}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="☺"/>
                        </c:otherwise>
                    </c:choose>

            </display:column>

        </c:forEach>
        <display:column title="organism part" property="organismPart"/>

    </display:table>




    <display:table name="${expressions}" htmlId="expressionTable" id="expression">

        <display:column title="Transcript id" property="transcriptId"/>

        <display:column title="Organism part">
                <c:out value="${expression.organismPart}"/><br/>
        </display:column>

        <display:column title="RPKM" property="rpkm"/>
        <display:column title="Specificity" property="specificity"/>

    </display:table>

</body>
</html>