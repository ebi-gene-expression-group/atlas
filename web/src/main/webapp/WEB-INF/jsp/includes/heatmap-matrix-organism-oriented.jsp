<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="configuration" var="configuration"/>

<display:table name="${heatmapOrganismParts}" id="organismPart"
               htmlId="heatmap-table" class="table-grid">
    <display:column title="<button id='display-levels' /><label for='display-levels'>Display levels</label>" class="header-cell">
        <div data-organism-part="${organismPart}">
            ${organismPart}
        </div>
    </display:column>

    <c:forEach var="geneProfile" items="${geneProfiles}">

        <fmt:message bundle="${configuration}" key="gxa.gene.url.template" var="genePageURL">
            <fmt:param value="${geneProfile}"/>
        </fmt:message>

        <c:set var="roundedExpressionLevel"
               value="${geneProfile.getRoundedExpressionLevel(organismPart)}"/>

        <c:if test="${not empty roundedExpressionLevel}">

            <c:set var="cellColour"
                   value="${colourGradient.getGradientColour(roundedExpressionLevel, roundedMinExpressionLevel, roundedMaxExpressionLevel)}"/>

            <c:set var="style" value="background-color:${cellColour};color:${cellColour};font-size:1px"/>

        </c:if>

        <display:column title="<div class='rotate_text'><a href='${genePageURL}' target='_blank'>${geneNamesProvider.getGeneName(geneProfile)}</a></div>"
                        headerClass='rotated_cell'
                        style="${not empty roundedExpressionLevel? style : ''}">

            <c:if test="${not empty roundedExpressionLevel}">

                <div data-organism-part="${organismPart}" data-color="${cellColour}" >
                    <fmt:formatNumber type="number" maxFractionDigits="${roundedExpressionLevel >= 1 ? 0 : 1}" value="${roundedExpressionLevel}" groupingUsed="false" />
                </div>

            </c:if>
        </display:column>

    </c:forEach>

</display:table>
