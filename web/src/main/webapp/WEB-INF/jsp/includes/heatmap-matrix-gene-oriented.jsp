<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<display:table name="${heatmapGenes}" id="geneId"
               htmlId="heatmap-table" class="table-grid">
    <display:column title="" value="${geneId}"
                    class="header-cell"/>

    <c:forEach var="organismPart" items="${heatmapOrganismParts}">

        <c:set var="expressionLevel"
               value="${geneExpressions.getExpressionLevel(geneId, organismPart)}"/>
        <c:set var="cellColour"
               value="${colourGradient.getGradientColour(expressionLevel,minExpressionLevel, maxExpressionLevel)}"/>

        <display:column title="<div data-organism-part='${organismPart}' class='rotate_text'>${organismPart}</div>"
                        headerClass='rotated_cell'
                        style="background-color:${cellColour};color:${cellColour};font-size:1px">
            <div data-organism-part="${organismPart}">
                <c:out value="${expressionLevel}"/>
            </div>
        </display:column>

    </c:forEach>

</display:table>
