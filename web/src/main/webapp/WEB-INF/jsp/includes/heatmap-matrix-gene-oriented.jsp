<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<fmt:setBundle basename="configuration" var="configuration"/>

<div class="block">
    <table>
        <tbody>
        <tr>
            <td>
                <display:table name="${geneProfiles}" id="geneProfile"
                               htmlId="heatmap-table" class="table-grid">
                    <display:column
                            title="
                                    <div class='heatmap-matrix-top-left-corner'>
                                    <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo'></span>
                                    <button id='display-levels' />
                                    </button>
                                    </div>"
                            class="horizontal-header-cell">

                        <c:set var="geneId" value="${geneProfile.id}"/>
                        <c:set var="bioEntityURL" value="${preferences.geneSetMatch? \"query?geneQuery=\".concat(geneProfile.getName()).concat(\"&exactMatch=\").concat(preferences.isExactMatch()) : \"genes/\".concat(geneProfile.id)}"/>

                        <c:choose>
                            <c:when test="${isExperimentPage}">
                                <a class="genename" id="${geneId}"
                                   href='${applicationProperties.buildServerURL(pageContext.request)}/${bioEntityURL}'
                                   title="">${geneProfile.getName()}</a>
                            </c:when>
                            <c:otherwise>
                                <div class="genename" id="${geneId}">${geneProfile.getName()}</div>
                            </c:otherwise>
                        </c:choose>

                    </display:column>

                    <c:if test="${type.isMicroarray()}">
                        <display:column title="" class="design-element">
                            ${geneProfile.designElementName}
                        </display:column>
                    </c:if>

                    <c:forEach var="factorHolder" items="${allQueryFactors}">

                        <c:choose>
                            <c:when test="${type.isBaseline()}">
                                <c:set var="queryFactor" value="${factorHolder.factor}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="queryFactor" value="${factorHolder}"/>
                            </c:otherwise>
                        </c:choose>

                        <c:set var="expressionLevel"
                               value="${geneProfile.getExpressionLevel(queryFactor)}"/>
                        <!-- first we preset the style to empty value because this code is being executed within a loop,
                        if we didn't do this the style value would be depending on the results of the previous loop iteration -->
                        <c:set var="style" value=""/>
                        <!-- then we check if expressionLevel is not null we set the style value to the right shade of gradient -->
                        <c:if test="${not empty expressionLevel}">
                            <c:choose>
                                <c:when test="${type.isBaseline()}">
                                    <c:set var="cellColour"
                                           value="${colourGradient.getGradientColour(expressionLevel, geneProfiles.getMinExpressionLevel(), geneProfiles.getMaxExpressionLevel())}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${geneProfile.getExpression(queryFactor).overExpressed}">
                                            <c:set var="cellColour"
                                                   value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - geneProfiles.getMaxUpRegulatedExpressionLevel(), 1 - geneProfiles.getMinUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cellColour"
                                                   value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - geneProfiles.getMaxDownRegulatedExpressionLevel(), 1 - geneProfiles.getMinDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="style" value="background-color:${cellColour}"/>

                        </c:if>

                        <c:set var="columnHeader"
                               value="${type.isBaseline() ? queryFactor.value : queryFactor.displayName}"/>

                        <display:column
                                title="<div data-organism-part=\"${columnHeader}\"
                                    ${type.isMicroarray() ? 'data-array-design=\"'.concat(queryFactor.arrayDesignAccession).concat('\"') : ''}
                                    ${type.isBaseline() ? 'assay-group-id=\"'.concat(factorHolder.assayGroupId).concat('\"') : ''}
                                    ${type.isBaseline() ? 'factor-name=\"'.concat(columnHeader).concat('\"') : ''}
                                    ${!type.isBaseline() ? 'data-contrast-id=\"'.concat(queryFactor.id).concat('\"') : ''}
                                    ${'data-experiment-accession=\"'.concat(experimentAccession).concat('\"')}
                                    class=\"factor-header rotate_text\"></div>"
                                headerClass="rotated_cell vertical-header-cell ${!type.isBaseline() ? 'contrastNameCell' : 'factorNameCell'}"
                                style="${style}">

                            <c:if test="${not empty expressionLevel}">
                                <c:choose>
                                    <c:when test="${type.isBaseline()}">
                                        <fmt:formatNumber type="number"
                                                          maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                          value="${expressionLevel}" groupingUsed="false"
                                                          var="expressionLevel"/>
                                    </c:when>
                                    <c:when test="${!type.isBaseline()}">
                                        <c:choose>
                                            <c:when test="${geneProfile.getExpression(queryFactor).notApplicable}">
                                                <c:set var="foldChange" value="N/A"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber type="number"
                                                                  maxFractionDigits="2"
                                                                  value="${geneProfile.getExpression(queryFactor).foldChange}"
                                                                  groupingUsed="false"
                                                                  var="foldChange"/>
                                                <c:if test="${type.isMicroarray()}">
                                                    <fmt:formatNumber type="number"
                                                                      maxFractionDigits="2"
                                                                      value="${geneProfile.getExpression(queryFactor).tstatistic}"
                                                                      groupingUsed="false"
                                                                      var="tstatistic"/>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>

                                <div class="hide_cell" ${type.isMicroarray() ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                                    ${!type.isBaseline() ? 'data-fold-change="'.concat(foldChange).concat('"'):""}
                                     data-organism-part="${columnHeader}" data-color="${cellColour}">
                                        ${!type.isBaseline() ? numberUtils.htmlFormatDouble(expressionLevel) : expressionLevel}
                                </div>

                            </c:if>

                        </display:column>

                    </c:forEach>

                </display:table>
            </td>
            <td style="vertical-align: top">
                <div style="float:left; ${isWidget ? 'display:none' : ''}">
                    <!--
                     <button id="download-profiles" class="button-image" value="D"></button>
                    -->
                    <a id="download-profiles-link"
                       title="Top 50 genes displayed on page. Download results to see the rest."
                       href="${applicationProperties.buildDownloadURL(pageContext.request)}"
                       class="button-image" target="_blank">
                        <img id="download-profiles" alt="Download query results" style="width:20px"
                             src="${base}/resources/images/download_blue_small.png">
                    </a>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

</div>

<div id="genenametooltip-content" style="display: none"/>

<section id="contrastInfo" style="display:none">
    <div id="contrastExperimentDescription" style="font-weight: bold; color:blue; text-align: center"></div>
    <div id="contrastDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Test value
            </th>
            <th class='header-cell'>
                Reference value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<section id="factorInfo" style="display:none">
    <div id="factorDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<script language="JavaScript" type="text/javascript"
        src="${base}/resources/js/highlight.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${base}/resources/js/genePropertiesTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${base}/resources/js/heatmapModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${base}/resources/js/contrastInfoTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${base}/resources/js/factorInfoTooltipModule.js"></script>

<script type="text/javascript">
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            if (${preferences.geneSetMatch == false && !type.isMicroRna()}) {
                genePropertiesTooltipModule.init('${preferences.geneQuery}', '${base}');
            }

            if (${type.isBaseline()}) {

                var selectedFilterFactorsJson = ${selectedFilterFactorsJson != null ? selectedFilterFactorsJson : "''"};

                var isWidget = ${isWidget != null? isWidget : false};

                heatmapModule.initBaselineHeatmap('${experimentAccession}', '${species}', selectedFilterFactorsJson, ${preferences.geneSetMatch}, isWidget);

            } else if (${type.isMicroarray()}) {

                heatmapModule.initMicroarrayHeatmap('${experimentAccession}', ${preferences.cutoff}, '${preferences.geneQuery}');

            } else {
                heatmapModule.initRnaSeqHeatmap('${experimentAccession}', ${preferences.cutoff}, '${preferences.geneQuery}');
            }

            if (!${type.isBaseline()}) {
                contrastInfoTooltipModule.init('${base}', '${param.accessKey}');
            } else {
                factorInfoTooltipModule.init('${base}', '${param.accessKey}');
            }

        });
    })(jQuery);
</script>
