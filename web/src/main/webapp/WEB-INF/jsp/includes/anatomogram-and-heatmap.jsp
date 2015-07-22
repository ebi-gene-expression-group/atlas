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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:choose>
    <c:when test="${empty geneProfiles}">
        <c:if test="${not isPreferenceError && widgetHasBaselineProfiles}">
            <div id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise><%--@elvariable id="geneProfiles" type="uk.ac.ebi.atlas.model.GeneProfilesList"--%>

        <div id="heatmap-anatomogram" class="gxaHeatmapAnatomogramRow">

            <div id="anatomogram" class="gxaDoubleClickNoSelection gxaAside" style="display:inline">
                <table>
                    <tr>
                        <td style="padding-top: 15px; vertical-align:top">
                            <span id="sex-toggle">
                                <img id="sex-toggle-image" title="Switch anatomogram" class="gxaButtonImage"
                                     style="width:20px;height:38px;padding:2px" src="${base}/resources/images/male_selected.png"/>
                            </span>
                            <!--
                            <span data-help-loc="#anatomogram"/>
                            -->
                        </td>
                        <td>
                            <c:set var="height" value="250px"/>
                            <c:if test="${species.equalsIgnoreCase(\"Homo sapiens\")}">
                                <c:set var="height" value="360px"/>
                            </c:if>

                            <div id="anatomogramBody" style="display:inline-block;width: 230px; height:${height}">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="heatmap-div" class="gxaHeatmapPosition${isWidget ? '-widget' : ''}">
                <div>
                    <div style="display: inline-block; vertical-align: top;">
                        <span id="geneCount">Showing ${geneProfiles.size()} of ${geneProfiles.getTotalResultCount()} genes found:</span>
                        <c:if test="${not empty profilesAsGeneSets}">
                            <a id="showGeneSetProfiles" href="javascript:void(0)">(show by gene set)</a>
                        </c:if>
                    </div>
                    <div style="display: inline-block; padding-left: 10px; vertical-align: top">
                        <a id="download-profiles-link"
                           title="Download all results"
                           href="${pageContext.request.contextPath}${isWidget ? applicationProperties.buildDownloadURLForWidget(pageContext.request, experimentAccession) : applicationProperties.buildDownloadURL(pageContext.request)}"
                           class="gxaButtonImage" target="_blank">
                            <img id="download-profiles" alt="Download query results" style="width:20px"
                                 src="${base}/resources/images/download_blue_small.png"/>
                        </a>
                    </div>
                    <div style="display: inline-block; padding-left: 20px">
                        <h:heatmap-legend geneProfiles="${geneProfiles}" type="${type.isBaseline() ? 'baseline' : 'differential'}"/>
                    </div>
                </div>
                <div>
                    <h:heatmap geneProfiles="${geneProfiles}" elementId="heatmap-div"/>
                </div>
            </div>
        </div>

        <c:if test="${not empty profilesAsGeneSets}">
            <div id="heatmap-profilesAsGeneSets" class="gxaHeatmapPosition${isWidget ? '-widget' : ''}" style="display:none">
                <div>
                    <div style="display: inline-block; vertical-align: top;">
                        <span id="geneSetsCount">Showing ${profilesAsGeneSets.size()} of ${profilesAsGeneSets.getTotalResultCount()} gene sets found:</span>
                        <c:if test="${not empty profilesAsGeneSets}">
                            <a id="showIndividualGenes" href="javascript:void(0)">(show individual genes)</a>
                        </c:if>
                    </div>
                    <div style="display: inline-block; padding-left: 10px; vertical-align: top">
                        <a id="download-profiles-link"
                           title="Download all results"
                           href="${pageContext.request.contextPath}${isWidget ? applicationProperties.buildDownloadURLForWidget(pageContext.request, experimentAccession) : applicationProperties.buildDownloadURL(pageContext.request)}"
                           class="gxaButtonImage" target="_blank">
                            <img id="download-profiles" alt="Download query results" style="width:20px"
                                 src="${base}/resources/images/download_blue_small.png"/>
                        </a>
                    </div>
                    <div style="display: inline-block; padding-left: 20px">
                        <h:heatmap-legend geneProfiles="${profilesAsGeneSets}" type="${type.isBaseline() ? 'baseline' : 'differential'}"/>
                    </div>
                </div>
                <div>
                    <h:heatmap geneProfiles="${profilesAsGeneSets}" elementId="heatmap-profilesAsGeneSets" geneSet="true" hidden="true"/>
                </div>
            </div>
        </c:if>

        <div id="heatmap-react-baseline" class="gxaHeatmapPosition"></div>

    </c:otherwise>
</c:choose>