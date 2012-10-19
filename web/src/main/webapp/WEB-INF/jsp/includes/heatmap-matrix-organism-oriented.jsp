<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="configuration" var="configuration"/>

<display:table name="${heatmapOrganismParts}" id="organismPart"
               htmlId="heatmap-table" class="table-grid">
    <display:column title="<button id='display-levels' /><label for='display-levels'>Display levels</label>"
                    class="header-cell">
        <div data-organism-part="${organismPart}">
                ${organismPart}
        </div>
    </display:column>

    <c:forEach var="geneId" items="${heatmapGenes}">

        <fmt:message bundle="${configuration}" key="gxa.gene.url.template" var="genePageURL">
            <fmt:param value="${geneId}"/>
        </fmt:message>

        <c:set var="expressionLevel"
               value="${geneExpressions.getExpressionLevel(geneId, organismPart)}"/>
        <c:set var="cellColour"
               value="${colourGradient.getGradientColour(expressionLevel,minExpressionLevel, maxExpressionLevel)}"/>

        <display:column
                title="<div class='rotate_text'><a href='${genePageURL}' target='_blank'>${geneService.getGeneName(geneId)}</a></div>"
                headerClass='rotated_cell'
                style="background-color:${cellColour};color:${cellColour};font-size:1px">
            <div data-organism-part="${organismPart}" data-color="${cellColour}">
                <c:out value="${expressionLevel}"/>
            </div>
        </display:column>

    </c:forEach>

</display:table>
