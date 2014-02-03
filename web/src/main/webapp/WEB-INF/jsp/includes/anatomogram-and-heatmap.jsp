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

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:choose>
    <c:when test="${empty geneProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found above the expression level cutoff for the query.
            </div>
        </c:if>
    </c:when>
    <c:otherwise><%--@elvariable id="geneProfiles" type="uk.ac.ebi.atlas.model.GeneProfilesList"--%>

        <div id="heatmap" class="row stickem-container">

            <div id="anatomogram" class="aside stickem double-click-noselection" style="display:inline">
                <table>
                    <tr>
                        <td style="padding-top: 15px; vertical-align:top">
                <span id="sex-toggle">
                    <img id="sex-toggle-image" title="Switch anatomogram" class="button-image"
                         style="width:20px;height:38px;padding:2px"
                         src="${base}/resources/images/male_selected.png"/>
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

            <div id="heatmap-div" class="heatmap-position" style="display:none" style="display:inline">

                <table>
                    <tr>
                        <td>
                        <span id="geneCount">Showing ${geneProfiles.size()}
                            of ${geneProfiles.getTotalResultCount()} ${preferences.geneSetMatch?'gene sets':'genes'} found:
                        </span>
                        </td>
                        <td>
                            <c:import url="includes/gradient-legend.jsp"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <c:import url="includes/heatmap-matrix-gene-oriented.jsp"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        </div>
    </c:otherwise>
</c:choose>