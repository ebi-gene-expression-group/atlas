<%--@elvariable id="foldChangeRounder" type="uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder"--%>
<%--@elvariable id="colourGradient" type="uk.ac.ebi.atlas.utils.ColourGradient"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ attribute name="geneProfiles" required="true" type="uk.ac.ebi.atlas.model.differential.DifferentialExpressionLimits"%>

<div style="display: inline-table">
    <c:if test="${((preferences.regulation eq 'DOWN') or (preferences.regulation eq 'UP_DOWN'))
                    and geneProfiles.getMinDownRegulatedExpressionLevel() != '0.0'}">

        <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('lightGray')}"
                              highValueColour="${colourGradient.getHexByColourName('blue')}"
                              highValueColorExpressionLevel="${foldChangeRounder.round(geneProfiles.getMaxDownRegulatedExpressionLevel())}"
                              lowValueColorExpressionLevel="${foldChangeRounder.round(geneProfiles.getMinDownRegulatedExpressionLevel())}"/>

    </c:if>
    <c:if test="${((preferences.regulation eq 'UP') or (preferences.regulation eq 'UP_DOWN'))
                    and geneProfiles.getMinUpRegulatedExpressionLevel() != '0.0'}">

        <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('pink')}"
                              highValueColour="${colourGradient.getHexByColourName('red')}"
                              highValueColorExpressionLevel="${foldChangeRounder.round(geneProfiles.getMaxUpRegulatedExpressionLevel())}"
                              lowValueColorExpressionLevel="${foldChangeRounder.round(geneProfiles.getMinUpRegulatedExpressionLevel())}"/>

    </c:if>
</div>
<div id="gradient-help-diff" data-help-loc="#gradient-differential-crossexp" style="display: inline-block; vertical-align: top; padding-left: 2px"></div>
