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
                                    <label for='display-levels'>Display levels</label>
                                </button>
                                </div>"
                            class="header-cell">

                        <c:set var="geneId" value="${geneProfile.geneId}"/>

                        <fmt:message bundle="${configuration}" key="gene.url.template" var="genePageURL">
                            <fmt:param value="${geneId}"/>
                        </fmt:message>

                        <a class="genename" id="${geneId}" href='${genePageURL}' target='_blank'
                           title="">${geneNamesProvider.getGeneName(geneId)}</a>
                    </display:column>

                    <c:if test="${type eq 'MICROARRAY'}">
                        <display:column title="" class="design-element">
                            ${geneProfile.designElementName}
                        </display:column>
                    </c:if>

                    <c:forEach var="queryFactor" items="${allQueryFactors}">

                        <c:set var="expressionLevel"
                               value="${geneProfile.getExpressionLevel(queryFactor)}"/>

                        <c:choose>
                            <c:when test="${expressionLevel != 0}">
                                <c:choose>
                                    <c:when test="${type eq 'BASELINE'}">
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
                            </c:when>
                            <c:otherwise>
                                <c:set var="style" value=""/>
                            </c:otherwise>
                        </c:choose>

                        <c:set var="columnHeader"
                               value="${type eq 'BASELINE' ? queryFactor.value : queryFactor.displayName}"/>

                        <display:column
                                title="<div data-organism-part=\"${columnHeader}\" class=\"factor-header rotate_text\" title=\"${columnHeader}\"></div>"
                                headerClass='rotated_cell'
                                style="${style}">

                            <c:if test="${expressionLevel != 0}">
                                <c:choose>
                                    <c:when test="${type eq 'BASELINE'}">
                                        <fmt:formatNumber type="number"
                                                          maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                          value="${expressionLevel}" groupingUsed="false"
                                                          var="expressionLevel"/>
                                    </c:when>
                                    <c:when test="${type != 'BASELINE'}">
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
                                                <c:if test="${type == 'MICROARRAY'}">
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

                                <div class="hide_cell" ${type == 'MICROARRAY' ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                                                       ${type != 'BASELINE' ? 'data-fold-change="'.concat(foldChange).concat('"'):""}
                                                        data-organism-part="${columnHeader}" data-color="${cellColour}">
                                                       ${type != 'BASELINE' ? numberUtils.htmlFormatDouble(expressionLevel) : expressionLevel}
                                </div>

                            </c:if>

                        </display:column>

                    </c:forEach>

                </display:table>
            </td>
            <td style="vertical-align: top">
                <div style="float:left">
                    <!--
                     <button id="download-profiles" class="button-image" value="D"></button>
                    -->
                    <a id="download-profiles-link"
                       title="Top 50 genes displayed on page. Download results to see the rest." href="${downloadUrl}"
                       class="button-image" target="_blank">
                        <img id="download-profiles" alt="Download query results" style="width:20px"
                             src="resources/images/download_blue_small.png">
                    </a>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

</div>

<div id="genenametooltip-content" style="display: none"/>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/highlight.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/genePropertiesTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/heatmap.js"></script>

<script type="text/javascript">

    initHeatmapDisplayValueToggle();

    initHeatmapCustomHeaders(${type == 'MICROARRAY'});

    initMAPlotButtons(${preferences.cutoff == '0.05' && empty preferences.geneQuery});

    genePropertiesTooltipModule.init('${preferences.geneQuery}');

</script>
