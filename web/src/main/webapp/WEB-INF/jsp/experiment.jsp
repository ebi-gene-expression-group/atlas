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
    <display:table name="${expressions}" htmlId="expressionTable" id="expression">

        <display:column title="Transcript id" property="transcriptId"/>
        <display:column title="Conditions">
            <c:forEach var="factor" items="${expression.factorValues}">
                <c:out value="${factor.displayString}"/><br/>
            </c:forEach>
        </display:column>

        <display:column title="RPKM" property="rpkm"/>
    </display:table>
</body>
</html>