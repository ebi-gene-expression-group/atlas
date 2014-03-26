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

<%@ attribute name="geneProfiles" required="true" type="uk.ac.ebi.atlas.model.GeneProfilesList"%>
<%@ attribute name="elementId" required="true"%>
<%@ attribute name="hidden" required="false" type="java.lang.Boolean"%>

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
                                    <button id='display-levels' class='display-levels-button' />
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

                        <%--@elvariable id="expression" type="uk.ac.ebi.atlas.model.DifferentialExpression"--%>
                        <c:set var="expression" value="${geneProfile.getExpression(queryFactor)}" />

                        <c:set var="hasExpression" value="${not empty expression}" />

                        <c:set var="isKnownLevel" value="${hasExpression && expression.isKnown()}" />

                        <c:set var="expressionLevel" value="${isKnownLevel ? expression.getLevel() : null}"/>

                        <!-- first we preset the style to empty value because this code is being executed within a loop,
                        if we didn't do this the style value would be depending on the results of the previous loop iteration -->
                        <c:set var="style" value=""/>
                        <!-- then we check if expressionLevel is not null we set the style value to the right shade of gradient -->
                        <c:if test="${not empty expressionLevel}">
                            <%--@elvariable id="colourGradient" type="uk.ac.ebi.atlas.utils.ColourGradient"--%>
                            <c:choose>
                                <c:when test="${type.isBaseline()}">
                                    <c:set var="cellColour"
                                           value="${colourGradient.getGradientColour(expressionLevel, geneProfiles.getMinExpressionLevel(), geneProfiles.getMaxExpressionLevel())}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${geneProfile.getExpression(queryFactor).overExpressed}">
                                            <c:set var="cellColour"
                                                   value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - geneProfiles.getMinUpRegulatedExpressionLevel(), 1 - geneProfiles.getMaxUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cellColour"
                                                   value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - geneProfiles.getMinDownRegulatedExpressionLevel(), 1 - geneProfiles.getMaxDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>

                            <c:if test="${isKnownLevel}">
                                <c:set var="style" value="background-color:${cellColour}"/>
                            </c:if>
                        </c:if>


                        <c:set var="columnHeader"
                               value="${type.isBaseline() ? queryFactor.value : queryFactor.displayName}"/>

                        <display:column
                                title="<div data-organism-part=\"${columnHeader}\"
                                    ${type.isBaseline() ? 'data-svg-path-id=\"'.concat(queryFactor.valueOntologyTerm).concat('\"') : ''}
                                    ${type.isMicroarray() ? 'data-array-design=\"'.concat(queryFactor.arrayDesignAccession).concat('\"') : ''}
                                    ${type.isBaseline() ? 'assay-group-id=\"'.concat(factorHolder.assayGroupId).concat('\"') : ''}
                                    ${type.isBaseline() ? 'factor-name=\"'.concat(columnHeader).concat('\"') : ''}
                                    ${!type.isBaseline() ? 'data-contrast-id=\"'.concat(queryFactor.id).concat('\"') : ''}
                                    ${'data-experiment-accession=\"'.concat(experimentAccession).concat('\"')}
                                    class=\"factor-header rotate_text\"></div>"
                                headerClass="rotated_cell vertical-header-cell ${!type.isBaseline() ? 'contrastNameCell' : 'factorNameCell'}"
                                style="${style}">
                            <c:choose>
                            <c:when test="${isKnownLevel}"><%--@elvariable id="numberUtils" type="uk.ac.ebi.atlas.utils.NumberUtils"--%>
                                <c:choose>
                                    <c:when test="${type.isBaseline()}">
                                        <fmt:formatNumber type="number"
                                                          maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                          value="${expressionLevel}" groupingUsed="false"
                                                          var="roundedExpressionLevel"/>
                                    </c:when>
                                    <c:when test="${!type.isBaseline()}">
                                        <c:choose>
                                            <c:when test="${expression.notApplicable}">
                                                <c:set var="foldChange" value="N/A"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="pValue" value="${numberUtils.htmlFormatDoubleEncoded(expression.getPValue())}"/>
                                                <fmt:formatNumber type="number"
                                                                  maxFractionDigits="2"
                                                                  value="${expression.foldChange}"
                                                                  groupingUsed="false"
                                                                  var="foldChange"/>
                                                <c:if test="${type.isMicroarray()}">
                                                    <fmt:formatNumber type="number"
                                                                      maxFractionDigits="2"
                                                                      value="${expression.tstatistic}"
                                                                      groupingUsed="false"
                                                                      var="tstatistic"/>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>

                                <div class="hide_cell" ${type.isMicroarray() ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                                    ${!type.isBaseline() ? 'data-pValue="'.concat(pValue).concat('"'):""}
                                     data-organism-part="${columnHeader}" data-color="${cellColour}"
                                     ${type.isBaseline() ? 'data-svg-path-id=\"'.concat(queryFactor.valueOntologyTerm).concat('\"') : ''}>
                                        ${!type.isBaseline() ? foldChange : roundedExpressionLevel}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${hasExpression}">
                                    <%--Otherwise show question marks tooltip--%>
                                    <div style="text-align: center" ${type.isMicroarray() ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                                        ${!type.isBaseline() ? 'data-fold-change="'.concat(foldChange).concat('"'):""}
                                            data-organism-part="${columnHeader}"
                                        ${type.isBaseline() ? 'data-svg-path-id=\"'.concat(queryFactor.valueOntologyTerm).concat('\"') : ''}>

                                        <span id='unknownCell' data-help-loc='#heatMapTableUnknownCell'></span>
                                    </div>
                                </c:if>
                            </c:otherwise>
                            </c:choose>
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

    <c:choose>
        <c:when test="${type.isBaseline()}">
            <span id='buttonText' pressedtext='Hide levels' unpressedText='Display levels'/>
        </c:when>
        <c:otherwise>
            <span id='buttonText' pressedtext='Hide log<sub>2</sub>-fold change' unpressedText='Display log<sub>2</sub>-fold change'/>
        </c:otherwise>
    </c:choose>

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

<style>
    .fancybox-lock .fancybox-overlay {
        overflow: hidden;
    }

    .fancybox-lock {
        overflow: hidden;
    }
</style>

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

<!--[if lte IE 8]>
<script language="JavaScript" type="text/javascript"
src="${pageContext.request.contextPath}/resources/js/flot/excanvas.min.js"></script>
<![endif]-->
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.pie.js"></script>

<!-- fancybox start -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.css"
      type="text/css" media="screen"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.pack.js"></script>
<!-- fancybox end -->

<script type="text/javascript">
    (function ($, heatmapModule) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            if (${preferences.geneSetMatch == false && !type.isMicroRna()}) {
                genePropertiesTooltipModule.init('${preferences.geneQuery}', '${base}');
            }

            if (${type.isBaseline()}) {

                var selectedFilterFactorsJson = ${selectedFilterFactorsJson != null ? selectedFilterFactorsJson : "''"};

                var isWidget = ${isWidget != null? isWidget : false};

                heatmapModule.initBaselineHeatmap('${experimentAccession}', '${species}', selectedFilterFactorsJson, ${preferences.geneSetMatch}, isWidget, '${base}', '${elementId}', ${hidden != null ? hidden : 'false'});

            } else if (${type.isMicroarray()}) {

                heatmapModule.initMicroarrayHeatmap('${experimentAccession}', ${preferences.cutoff}, '${preferences.geneQuery}', 'heatmap-div');

            } else {
                heatmapModule.initRnaSeqHeatmap('${experimentAccession}', ${preferences.cutoff}, '${preferences.geneQuery}', 'heatmap-div');
            }

            if (!${type.isBaseline()}) {
                contrastInfoTooltipModule.init('${base}', '${param.accessKey}');
            } else {
                factorInfoTooltipModule.init('${base}', '${param.accessKey}');
            }

        });
    })(jQuery, heatmapModule);
</script>
