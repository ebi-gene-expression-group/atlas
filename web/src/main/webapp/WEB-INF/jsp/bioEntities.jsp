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
<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ page import="org.apache.commons.lang.StringUtils" %>

<c:choose>
<c:when test="${not empty exceptionMessage}">
    <div id="error-content" class="block">
        <div class="error">
                ${exceptionMessage}
        </div>
    </div>
    <div id="content" class="block">
        <a href="/gxa">Go to Expression Atlas home page</a>
    </div>
</c:when>
<c:otherwise>
<section class="grid_17 alpha extra-padding">
    <h2 class="strapline">
        Expression Atlas results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>
<h:ebiGlobalSearch ebiSearchTerm="${not empty globalSearchTerm ? applicationProperties.urlParamEncode(globalSearchTerm) : not empty originalSearchTerm ? originalSearchTerm : entityIdentifier}"/>

<section class="grid_23 extra-padding">
    <div id="accordion">
        <c:if test="${showBioentityPropertiesPane}">
            <ul id="infoHeader" class="bioEntityCardHeader">
                <img id="bioentity-info-image" title="Bio-Entity information" style="position: absolute; left: 0.5em; "
                     src="resources/images/bioentity_info_transparent_bkg.png"/>
                        <span class="bioEntityCardBioentityName">
                            <c:forEach var="entityName" varStatus="loopStatus"
                                       items="${bioEntityPropertyService.entityNames}">
                                ${entityName}<c:if test="${not loopStatus.last}">, </c:if>
                            </c:forEach>
                        </span>
                <c:set var="species" value="${bioEntityPropertyService.getSpecies()}"/>
                <span class="bioEntityCardSpecies">${fn:toUpperCase(fn:substring(species, 0, 1))}${fn:substring(species, 1,fn:length(species))}</span>
                <span class="bioEntityCardDescription">${bioEntityPropertyService.getBioEntityDescription()}</span>
            </ul>

            <div id="infoBody" class="bioEntityCard">
                <table id="bioEntityCardTable">
                    <c:forEach var="propertyType" items="${propertyNames.keySet()}">
                        <c:choose>
                            <c:when test="${propertyType == 'go' || propertyType == 'po'}">

                                <c:set var="relevantGoPoLinks"
                                       value="${bioEntityPropertyService.fetchRelevantGoPoLinks(propertyType, 3)}"/>
                                <c:set var="allGoPoLinks"
                                       value="${bioEntityPropertyService.fetchGoPoLinksOrderedByDepth(propertyType)}"/>

                                <c:if test="${relevantGoPoLinks.size() > 0}">
                                    <tr>
                                        <td class="bioEntityCardPropertyType">${propertyNames.get(propertyType)}</td>
                                        <td class="bioEntityCardPropertyValue">
                                            <div id="${propertyType}RelevantLinks">
                                                <c:set var="count" value="0"/>
                                                <c:forEach var="goLink" items="${relevantGoPoLinks}">

                                                    <c:set var="count" value="${count + 1}"/>
                                                    <c:set var="comma" value=""/>
                                                    <c:if test="${count < relevantGoPoLinks.size()}">
                                                        <c:set var="comma" value=","/>
                                                    </c:if>

                                                    <c:set var="preLinkHTML" value=""/>
                                                    <c:set var="postLinkHTML" value=""/>
                                                    <c:if test="${not goLink.getUrl().isEmpty()}">
                                                        <c:set var="preLinkHTML"
                                                               value="<a class=\"bioEntityCardLink\" href=\"${goLink.getUrl()}\" target=\"_blank\">"/>
                                                        <c:set var="postLinkHTML" value="</a>"/>
                                                    </c:if>
                                                    <span>${preLinkHTML}${goLink.getText()}${postLinkHTML}${comma}</span>
                                                </c:forEach>

                                                <c:if test="${allGoPoLinks.size() > relevantGoPoLinks.size()}">
                                                    <a id="${propertyType}MoreLinks" href="">(... and ${allGoPoLinks.size() - relevantGoPoLinks.size()} more)</a>
                                                </c:if>
                                            </div>

                                            <c:if test="${allGoPoLinks.size() > relevantGoPoLinks.size()}">
                                                <div id="${propertyType}AllLinks" style="display:none">
                                                    <c:set var="count" value="0"/>
                                                    <c:forEach var="goLink" items="${allGoPoLinks}">

                                                        <c:set var="count" value="${count + 1}"/>
                                                        <c:set var="comma" value=""/>
                                                        <c:if test="${count < allGoPoLinks.size()}">
                                                            <c:set var="comma" value=","/>
                                                        </c:if>

                                                        <c:set var="preLinkHTML" value=""/>
                                                        <c:set var="postLinkHTML" value=""/>
                                                        <c:if test="${not goLink.getUrl().isEmpty()}">
                                                            <c:set var="preLinkHTML"
                                                                   value="<a class=\"bioEntityCardLink\" href=\"${goLink.getUrl()}\" target=\"_blank\">"/>
                                                            <c:set var="postLinkHTML" value="</a>"/>
                                                        </c:if>
                                                        <span>${preLinkHTML}${goLink.getText()}${postLinkHTML}${comma}</span>
                                                    </c:forEach>
                                                    <a id="${propertyType}LessLinks" href="">(show less)</a>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:set var="propertyLinks"
                                       value="${bioEntityPropertyService.fetchPropertyLinks(propertyType)}"/>
                                <c:if test="${propertyLinks.size() > 0}">
                                    <tr>
                                        <td class="bioEntityCardPropertyType">${propertyNames.get(propertyType)}</td>
                                        <td class="bioEntityCardPropertyValue">
                                            <c:set var="count" value="0"/>
                                            <c:forEach var="propertyLink" items="${propertyLinks}">

                                                <c:set var="count" value="${count + 1}"/>
                                                <c:set var="comma" value=""/>
                                                <c:if test="${count < propertyLinks.size()}">
                                                    <c:set var="comma" value=","/>
                                                </c:if>

                                                <c:set var="preLinkHTML" value=""/>
                                                <c:set var="postLinkHTML" value=""/>
                                                <c:if test="${not propertyLink.getUrl().isEmpty()}">
                                                    <c:set var="preLinkHTML"
                                                           value="<a class=\"bioEntityCardLink\" href=\"${propertyLink.getUrl()}\" target=\"_blank\">"/>
                                                    <c:set var="postLinkHTML" value="</a>"/>
                                                </c:if>

                                                <span>${preLinkHTML}${propertyLink.getText()}${postLinkHTML}${comma}</span>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </table>
            </div>

        </c:if>

        <c:set var="showWidget" value="${widgetHasBaselineProfiles}"/>

        <ul id="baselineProfileHeader" class="bioEntityCardHeader">
            <img id="baseline-info-image" title="Baseline Expression"
                 style="position: absolute; left: 0.5em; "
                 src="resources/images/allup2_transparent_bkg.png"/>
            <span class="bioEntityCardBioentityName">Baseline Expression</span>
            <c:choose>
                <c:when test="${showWidget}">
                        <span style="margin-left: 10px; margin-top:10px">
                          Results in tissues
                        </span>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty baselineCounts}">
                            <c:set var="resultsCount" value="${baselineCounts.size()}"/>
                            <span style="margin-left: 10px; margin-top:10px">
                                ${resultsCount} ${resultsCount == 1 ? "result" : "results"}
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span style="margin-left: 10px">
                                No results
                            <span>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </ul>

        <div id="baselineProfileBody" class="bioEntityCard">
            <c:choose>
                <c:when test="${showWidget}">
                    <div class="ui-corner-all bioEntityCardDifferentialSummary">
                        <span style="visibility:hidden">c</span><%--this is to have a border around text bellow--%>
                        <span style="float: right">Expression Level cut-off: 0.5</span>
                    </div>
                    <div id="widgetBody"></div>
                </c:when>

                <c:otherwise>
                    <c:import url="baseline-search-results.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>

        <ul id="diffProfileHeader" class="bioEntityCardHeader">
            <img id="differential-info-image" title="Differential Expression"
                 style="position: absolute; left: 0.5em; "
                 src="resources/images/updown_transparent_bkg.png"/>
            <span class="bioEntityCardBioentityName">Differential Expression</span>
            <c:choose>
                <c:when test="${not empty bioentities}">
                    <span style="margin-left: 10px; margin-top:10px">${bioentities.getTotalNumberOfResults()} ${bioentities.getTotalNumberOfResults() == 1 ? "result" : "results"}</span>
                </c:when>
                <c:otherwise>
                    <span style="margin-left: 10px; margin-top:10px">No results</span>
                </c:otherwise>
            </c:choose>
        </ul>

        <c:choose>
            <%--@elvariable id="bioentities" type="uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList"--%>
            <c:when test="${not empty bioentities}">

                <div id="diffProfileBody" class="bioEntityCard">
                    <div class="ui-corner-all bioEntityCardDifferentialSummary">
                        <c:choose>
                            <c:when test="${bioentities.getTotalNumberOfResults() <= 50}">
                                <span> Showing ${bioentities.getTotalNumberOfResults()} ${bioentities.getTotalNumberOfResults() == 1 ? "result" : "results"}</span>
                            </c:when>
                            <c:otherwise>
                                <span> Showing 50 of ${bioentities.getTotalNumberOfResults()} results</span>
                            </c:otherwise>
                        </c:choose>
                        
                        <span style="float: right">cutoffs: adjusted <i>p</i>-value 0.05 &nbsp;&nbsp; log<sub>2</sub>-fold change 1.0</span>
                    </div>

                    <div id="heatmap-div" style="display:none;">
                        <table style="margin-left:auto;margin-right:auto;">
                            <tr>
                                <td>
                                    <button id='diffresults-display-levels' data-pressedtext="hide"
                                            data-unpressedText='show' style="margin-top: 5px; margin-bottom: 5px">
                                            <%--<label for='display-levels'>Display <i>p</i>-values</label>--%>
                                    </button>
                                    <span id='buttonText' pressedtext='Hide log<sub>2</sub>-fold change'
                                          unpressedText='Display log<sub>2</sub>-fold change'/>
                                </td>
                                <td>
                                    <h:heatmap-differential-legend geneProfiles="${bioentities}"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <c:import url="includes/heatmap-matrix-searchresults-diffanalytics.jsp"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<br/>

<div id="help-placeholder" style="display: none"></div>

<c:if test="${showWidget}">
    <script language="JavaScript" type="text/javascript" src="//www.ebi.ac.uk/Tools/biojs/biojs/Biojs.js"></script>
    <script language="JavaScript" type="text/javascript" src="/gxa/resources/biojs/AtlasHeatmapReact.js"></script>

    <%@ include file="includes/react.jsp" %>
    <%@ include file="includes/heatmap-js.jsp" %>
    <%@ include file="includes/anatomogram.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/jsx/heatmapContainer.js"></script>
</c:if>

<c:set var="hasBaselineResults" value="${showWidget || not empty baselineCounts}"/>

<c:choose>
    <c:when test="${showBioentityPropertiesPane}">
        <c:set var="defaultPanelIndex" value="${hasBaselineResults ? 1 : (not empty bioentities ? 2 : 0)}"/>
    </c:when>
    <c:otherwise>
        <c:set var="defaultPanelIndex" value="${hasBaselineResults ? 0 : (not empty bioentities ? 1 : false)}"/>
    </c:otherwise>
</c:choose>

<%-- hide expand/collapse icons when accordion sections don't have enough results --%>
<c:set var="hideIcons" value="${(showBioentityPropertiesPane && !hasBaselineResults && empty bioentities) || (!showBioentityPropertiesPane && !(hasBaselineResult && not empty bioentities))}"/>

<script>

    window.onload = function () {

        var openPanelIndex = ${param.openPanelIndex != null ? param.openPanelIndex : defaultPanelIndex};

        $("#bioentity-info-image").tooltip();
        $("#differential-info-image").tooltip();

        $("#accordion").accordion({
            collapsible: true,
            active: openPanelIndex,
            heightStyle: "content",
            icons: ${hideIcons ? "{ 'header': 'ui-icon-blank'}" : "{ 'header': 'bioEntityCardIconPlus', 'activeHeader': 'bioEntityCardIconMinus' }"},
            header: "ul",
            beforeActivate: function( event, ui ) {
                // prevent empty panel from being opened
                function emptyPanel(panel) {
                    return $.trim($(panel).html()).length == 0;
                }

                function collapsingCurrentlyOpenPanel(ui) {
                    return (ui.newHeader.length == 0);
                }

                if(emptyPanel(ui.newPanel) && !collapsingCurrentlyOpenPanel(ui) ) {
                    event.preventDefault();
                }
            }
        });

        helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', '');

        <c:if test="${showWidget}">

        var widgetParameters = "${isGeneSet ? "" : "&propertyType=bioentity_identifier" }" + "${not empty species ? "&species=".concat(species) : ""}";

        new Biojs.AtlasHeatmap({
            gxaBaseUrl: '${pageContext.request.contextPath}',
            params: 'geneQuery=${entityIdentifier}' + widgetParameters,
            isMultiExperiment: true,
            target: "widgetBody",
            heatmapClass: "heatmap-position"
        });

        </c:if>


        (function addClickEventsToExpandAndCollapseGoAndPoTermNames() {
            $.each(
                    ["go", "po"],
                    function (i, val) {
                        $('#' + val + 'MoreLinks').click(function () {
                            $('#' + val + 'MoreLinks').hide();
                            $('#' + val + 'RelevantLinks').hide();
                            $('#' + val + 'AllLinks').show();
                            $('#' + val + 'LessLinks').show();
                            return false;
                        });
                        $('#' + val + 'LessLinks').click(function () {
                            $('#' + val + 'LessLinks').hide();
                            $('#' + val + 'AllLinks').hide();
                            $('#' + val + 'RelevantLinks').show();
                            $('#' + val + 'MoreLinks').show();
                            return false;
                        });
                    });
        })();
    }
</script>

</c:otherwise>
</c:choose>


