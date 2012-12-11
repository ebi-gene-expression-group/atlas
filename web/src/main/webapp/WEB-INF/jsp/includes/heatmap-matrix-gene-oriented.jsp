<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<div id="geneCount" style="">Showing ${geneProfiles.size()} of ${totalResultCount} genes found:</div>
<div class="block">
    <table>
        <tbody>
            <tr>
                <td>
                    <display:table name="${geneProfiles}" id="geneProfile"
                                   htmlId="heatmap-table" class="table-grid">
                        <display:column title="<button id='display-levels' /><label for='display-levels'>Display levels</label>"
                                        class="header-cell">
                            <fmt:message bundle="${configuration}" key="gene.url.template" var="genePageURL">
                                <fmt:param value="${geneProfile.geneId}"/>
                            </fmt:message>
                            <a href='${genePageURL}' target='_blank'>${geneProfile.geneName}</a>
                        </display:column>

                        <c:forEach var="organismPart" items="${heatmapOrganismParts}">

                            <c:set var="expressionLevel"
                                   value="${geneProfile.getExpressionLevel(organismPart)}"/>

                            <c:if test="${expressionLevel != 0}">

                                <c:set var="cellColour"
                                       value="${colourGradient.getGradientColour(expressionLevel, minExpressionLevel, maxExpressionLevel)}"/>

                                <c:set var="style" value="background-color:${cellColour}"/>

                            </c:if>

                            <display:column title="<div data-organism-part='${organismPart}' class='rotate_text'>${organismPart}</div>"
                                            headerClass='rotated_cell'
                                            style="${expressionLevel !=0 ? style : ''}">

                                <c:if test="${expressionLevel != 0}">

                                    <div style="font-size:1px;color:${cellColour}" data-organism-part="${organismPart}" data-color="${cellColour}">
                                        <fmt:formatNumber type="number" maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                          value="${expressionLevel}" groupingUsed="false"/>
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
                        <a id="download-profiles-link" title="Download query results" href="${downloadUrl}" class="button-image" target="_blank">
                            <img id="download-profiles" alt="Download query results" style="width:20px" src="resources/images/download_blue_small.png">
                        </a>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</div>
