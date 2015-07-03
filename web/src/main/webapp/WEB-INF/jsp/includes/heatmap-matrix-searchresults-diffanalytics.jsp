<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>

<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:set var="showMultiGeneColumns" value="${!singleGeneDiffHeatmap}" />

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
    <c:forEach items="${bioentities}"
               var="diffAnalytics">
        <tr>
         <c:if test="${showMultiGeneColumns}">
            <td class="gxaHorizontalHeaderCell">
                    <a href="genes/${diffAnalytics.bioentityId}">${diffAnalytics.bioentityName}</a>
            </td>
         </c:if>
          <c:if test="${showMultiGeneColumns}">
            <td class="gxaHorizontalHeaderCell">
                    ${diffAnalytics.species}
            </td>
          </c:if>

            <td class="gxaHorizontalHeaderCell contrastNameCell"
                data-experiment-accession="${diffAnalytics.experimentAccession}"
                data-contrast-id="${diffAnalytics.contrastId}">
                <a href="experiments/${diffAnalytics.experimentPageUrl}">${diffAnalytics.contrastDisplayName}</a>
            </td>

            <c:set var="expression" value="${diffAnalytics.expression}"/>

            <c:set var="expressionLevel"
                   value="${expression.level}"/>

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


<%@ include file="react.jsp" %>

<script language="JavaScript" type="text/javascript" src="${base}/resources/js/heatmapModuleDeprecated.js"></script>
<script src="${pageContext.request.contextPath}/resources/widget/latest/jsx/contrastTooltip.js"></script>
<script src="${pageContext.request.contextPath}/resources/widget/latest/js/contrastTooltipModule.js"></script>

<script type="text/javascript">
    (function ($, heatmapModuleDeprecated, contrastTooltipModule) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            heatmapModuleDeprecated.initRnaSeqHeatmap(undefined, ${preferences.cutoff}, undefined, 'heatmap-div');

            contrastTooltipModule.init('${pageContext.request.contextPath}', '${param.accessKey}');

            $("#injected-header").remove();
            $("#heatmap-table th").attr("rowspan", "1");
        });
    })(jQuery, heatmapModuleDeprecated, contrastTooltipModule);
</script>