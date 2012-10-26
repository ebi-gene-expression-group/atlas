<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="configuration" var="configuration"/>

<display:table name="${geneProfiles}" id="geneProfile"
               htmlId="heatmap-table" class="table-grid">
    <display:column title="<button id='display-levels' /><label for='display-levels'>Display levels</label>"
                    class="header-cell">
        <fmt:message bundle="${configuration}" key="gxa.gene.url.template" var="genePageURL">
            <fmt:param value="${geneProfile.geneId}"/>
        </fmt:message>
        <a href='${genePageURL}' target='_blank'>${geneNamesProvider.getGeneName(geneProfile.geneId)}</a>
    </display:column>

    <c:forEach var="organismPart" items="${heatmapOrganismParts}">

        <c:set var="expressionLevel"
               value="${geneProfile.getExpressionLevel(organismPart)}"/>

        <c:if test="${expressionLevel != 0}">

            <c:set var="cellColour"
                   value="${colourGradient.getGradientColour(expressionLevel, minExpressionLevel, maxExpressionLevel)}"/>

            <c:set var="style" value="background-color:${cellColour};color:${cellColour};font-size:1px"/>

        </c:if>

        <display:column title="<div data-organism-part='${organismPart}' class='rotate_text'>${organismPart}</div>"
                        headerClass='rotated_cell'
                        style="${expressionLevel !=0 ? style : ''}">

            <c:if test="${expressionLevel != 0}">

                <div style="font-size:1px" data-organism-part="${organismPart}" data-color="${cellColour}">
                    <fmt:formatNumber type="number" maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                      value="${expressionLevel}" groupingUsed="false"/>
                </div>

            </c:if>

        </display:column>

    </c:forEach>

</display:table>

