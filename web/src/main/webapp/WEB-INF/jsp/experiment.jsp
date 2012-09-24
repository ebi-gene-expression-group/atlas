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
    </head>

    <body>

        <div id="reload">
            <form:form method="get"  commandName="preferences">
                <table id="reload-form-table">
                    <tr>
                        <td><form:label path="cutoff">Expression Level Cutoff</form:label></td>
                        <td><form:input path="cutoff" type="integer"  id="cutoff"/></td>
                        <td><form:errors path="cutoff" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="rankingSize">Ranking Size</form:label></td>
                        <td><form:input path="rankingSize" type="text" id="rankingSize"/></td>
                        <td><form:errors path="rankingSize" /></td>
                    </tr>
                    <tr>
                        <td colspan="3"><input type="submit" value="Reload Page"/></td>
                    </tr>
                </table>
            </form:form>
        </div>


        <div id="gradientLegenda">
            <table id="heatmap-legenda">
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

        <div id="heatmap">
            <display:table name="${heatmapOrganismParts}" id="organismPart" htmlId="heatmap-table">
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
                <display:column title="" value="${organismPart}"/>

            </display:table>
        </div>


        <div id="expressions">
            <display:table name="${transcriptExpressions}" htmlId="expressions-table" id="transcriptExpression">
        
                <display:column title="Transcript id" property="transcriptId"/>
        
                <fmt:message key="factor.name.ORGANISMPART" bundle="${i18n}" var="organismpart"/>
                <display:column title="${organismpart}" property="organismPart"/>
        
                <fmt:message key="expression.level.metric" bundle="${i18n}" var="measurement"/>
                <display:column title="${measurement}" property="level"/>
        
                <display:column title="Specificity" property="specificity"/>
        
            </display:table>
        </div>


    </body>

</html>