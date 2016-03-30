<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="singleGeneDiffHeatmap" type="boolean"--%>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/heatmap-matrix-searchresults-diffanalytics.css">

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:set var="showMultiGeneColumns" value="${!singleGeneDiffHeatmap}" />

<div id="help-placeholder" style="display: none"></div>

<table id="diff-heatmap-table" class="gxaTableGrid">
    <thead>

    <tr>
    <c:if test="${showMultiGeneColumns}">
        <th class="gxaHorizontalHeaderCell" style="padding: 5px; text-align:center;">
            <div>Gene</div>
        </th>
    </c:if>

    <c:if test="${showMultiGeneColumns}">
        <th class="gxaHorizontalHeaderCell" style="padding: 5px; text-align:center;">
            <div>Organism</div>
        </th>
    </c:if>
        <th class="gxaHorizontalHeaderCell" style="padding: 5px; text-align:center;">
            <div>Comparison</div>
        </th>
        <th class="gxaHorizontalHeaderCell" style="padding: 5px;">
            <div class='factor-header' data-organism-part=''>Log<sub>2</sub>-fold change</div>
        </th>
    </tr>

    </thead>

    <tbody>

    <%--@elvariable id="bioentities" type="uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList"--%>
    <%--@elvariable id="diffAnalytics" type="uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics"--%>
    <c:forEach items="${bioentities}" var="diffAnalytics">
        <tr>
            <c:if test="${showMultiGeneColumns}">
                <td>
                    <a href="genes/${diffAnalytics.bioentityId}">${diffAnalytics.bioentityName}</a>
                </td>
            </c:if>
            <c:if test="${showMultiGeneColumns}">
                <td>${diffAnalytics.species}</td>
            </c:if>
            <td class="contrastNameCell"
                data-experiment-accession="${diffAnalytics.experimentAccession}"
                data-contrast-id="${diffAnalytics.contrastId}">
                <a href="experiments/${diffAnalytics.experimentPageUrl}">${diffAnalytics.contrastDisplayName}</a>
            </td>

            <c:set var="expression" value="${diffAnalytics.expression}"/>
            <c:set var="expressionLevel" value="${expression.level}"/>

            <c:if test="${! empty expressionLevel}">
                <c:choose>
                    <%--@elvariable id="colourGradient" type="uk.ac.ebi.atlas.utils.ColourGradient"--%>
                    <c:when test="${expression.overExpressed}">
                        <c:set var="cellColour"
                               value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - bioentities.getMinUpRegulatedExpressionLevel(), 1 - bioentities.getMaxUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="cellColour"
                               value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - bioentities.getMinDownRegulatedExpressionLevel(), 1 - bioentities.getMaxDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                    </c:otherwise>
                </c:choose>

                <c:set var="style" value="background-color:${cellColour}"/>

            </c:if>

            <td style="${style}">

                <c:if test="${not empty expressionLevel}">
                    <%--@elvariable id="foldChangeRounder" type="uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder"--%>
                    <c:set var="foldChange" value="${foldChangeRounder.format(expression.foldChange)}"/>

                    <div class="gxaHideCell" ${type.isMicroarray() ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                        ${'data-fold-change="'.concat(foldChange).concat('"')}
                         data-organism-part="${firstInRow}" data-color="${cellColour}">
                            ${foldChange}
                    </div>

                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/react/0.14.7/react.min.js"></script>
<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/react/0.14.7/react-dom-server.min.js"></script>

<script language="JavaScript" type="text/javascript" src="${base}/resources/js/heatmapModuleDeprecated.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contrastTooltip.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contrastTooltipModule.js"></script>

<script type="text/javascript">
    (function ($, heatmapModuleDeprecated, contrastTooltipModule) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            heatmapModuleDeprecated.initRnaSeqHeatmap(undefined, ${preferences.cutoff}, undefined, 'heatmap-div');

            contrastTooltipModule.init('${pageContext.request.contextPath}', '${param.accessKey}');

            $("#injected-header").remove();
            $("#heatmap-table th").attr("rowspan", "1");
        });
    })(jQuery, heatmapModuleDeprecated, contrastTooltipModule);

    helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', '');
</script>