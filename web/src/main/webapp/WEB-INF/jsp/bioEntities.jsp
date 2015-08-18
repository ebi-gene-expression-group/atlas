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

<c:choose>
<c:when test="${not empty exceptionMessage}">
    <div id="error-content" class="gxaBlock">
        <div class="gxaError">
                ${exceptionMessage}
        </div>
    </div>
    <div id="content" class="gxaBlock">
        <a href="/gxa">Go to Expression Atlas home page</a>
    </div>
</c:when>
<c:otherwise>
<section class="grid_17 alpha gxaExtraPadding">
    <h2 class="strapline">
        Expression Atlas results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>
<h:ebiGlobalSearch ebiSearchTerm="${not empty globalSearchTerm ? applicationProperties.urlParamEncode(globalSearchTerm) : geneQuery.asString()}"/>

<section class="grid_23 gxaExtraPadding">
    <div id="accordion">
        <c:if test="${showBioentityPropertiesPane}">
            <ul id="infoHeader" class="gxaBioEntityCardHeader">
                <img id="bioentity-info-image" title="Bio-Entity information" style="position: absolute; left: 0.5em; "
                     src="/gxa/resources/images/bioentity_info_transparent_bkg.png"/>
                        <span class="gxaBioEntityCardBioentityName">
                            <c:forEach var="entityName" varStatus="loopStatus"
                                       items="${bioEntityPropertyService.entityNames}">
                                ${entityName}<c:if test="${not loopStatus.last}">, </c:if>
                                <c:set var="entityNamesList" value="${entityNamesList} ${entityName}"/>
                            </c:forEach>
                        </span>
                <c:set var="species" value="${bioEntityPropertyService.getSpecies()}"/>
                <span class="gxaBioEntityCardSpecies">${fn:toUpperCase(fn:substring(species, 0, 1))}${fn:substring(species, 1,fn:length(species))}</span>
                <span class="gxaBioEntityCardDescription">${bioEntityPropertyService.getBioEntityDescription()}</span>
            </ul>

            <div id="infoBody" class="gxaBioEntityCard">
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
                                        <td class="gxaBioEntityCardPropertyType">${propertyNames.get(propertyType)}</td>
                                        <td class="gxaBioEntityCardPropertyValue">
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
                                        <td class="gxaBioEntityCardPropertyType">${propertyNames.get(propertyType)}</td>
                                        <td class="gxaBioEntityCardPropertyValue">
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

        <ul id="baselineProfileHeader" class="gxaBioEntityCardHeader">
            <img id="baseline-info-image" title="Baseline Expression"
                 style="position: absolute; left: 0.5em; "
                 src="/gxa/resources/images/allup2_transparent_bkg.png"/>
            <span class="gxaBioEntityCardBioentityName">Baseline Expression</span>
            <c:choose>
                <c:when test="${showWidget}">
                     <span style="margin-left: 10px; margin-top:10px">
                        <c:choose>
                            <c:when test="${not empty firstBaselineCounts}">
                                Results found
                            </c:when>
                            <c:otherwise>
                                Results in tissues
                            </c:otherwise>
                        </c:choose>
                    </span>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty firstBaselineCounts}">
                            <c:set var="resultsCount" value="${firstBaselineCounts.size() + remainingBaselineCounts.size()}"/>
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

        <div id="baselineProfileBody" class="gxaBioEntityCard">
            <c:choose>
                <c:when test="${showWidget}">
                    <div class="ui-corner-all gxaBioEntityCardDifferentialSummary">
                        <span style="visibility:hidden">c</span><%--this is to have a border around text bellow--%>
                        <span style="float: right">Within Sample Abundance (Proteomics) > 0</span>
                        <span style="float: left">FPKM/TPM (Transcriptomics) > 0.5</span>
                    </div>
                    <div id="widgetBody"></div>


                    <c:if test="${not empty firstBaselineCounts}">
                        <section class="grid_17 alpha gxaExtraPadding">
                            <h5 style="padding: 0px">Other baseline experiments</h5>
                            <h:baseline-search-results exactMatch="${exactMatch}" firstBaselineCounts="${firstBaselineCounts}" remainingBaselineCounts="${remainingBaselineCounts}" geneQuery="${geneQuery}" hideSpecies="true"/>
                        </section>
                    </c:if>

                </c:when>

                <c:otherwise>
                    <c:if test="${not empty firstBaselineCounts}">
                        <h:baseline-search-results exactMatch="${exactMatch}" firstBaselineCounts="${firstBaselineCounts}" remainingBaselineCounts="${remainingBaselineCounts}" geneQuery="${geneQuery}"/>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>

        <ul id="diffProfileHeader" class="gxaBioEntityCardHeader">
            <img id="differential-info-image" title="Differential Expression"
                 style="position: absolute; left: 0.5em; "
                 src="/gxa/resources/images/updown_transparent_bkg.png"/>
            <span class="gxaBioEntityCardBioentityName">Differential Expression</span>
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

                <div id="diffProfileBody" class="gxaBioEntityCard">
                    <div class="ui-corner-all gxaBioEntityCardDifferentialSummary">
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
                        <div style="padding: 10px">
                            <div style="display: inline-block; vertical-align: top; text-align: center">
                                    <button id='diffresults-display-levels' data-pressedtext="hide"
                                            data-unpressedText="show" style="margin-top: 5px; margin-bottom: 5px; width: 186px;">
                                    </button>
                                    <span id='buttonText' pressedtext='Hide log<sub>2</sub>-fold change'
                                          unpressedText='Display log<sub>2</sub>-fold change'/>
                            </div>
                            <div class="gxaHeatmapLegendGradient" style="display: inline-block; padding-left: 20px">
                                <h:heatmap-differential-legend geneProfiles="${bioentities}"/>
                            </div>
                            <div style="display: inline-block; vertical-align: top; padding-left: 10px">
                                <a id="download-profiles-link"
                                   title="Download all results"
                                   href="${pageContext.request.contextPath}${applicationProperties.buildDownloadURL(pageContext.request)}"
                                   class="gxaButtonImage" target="_blank">
                                    <img id="download-profiles" alt="Download query results" style="width:20px"
                                         src="${pageContext.request.contextPath}/resources/images/download_blue_small.png">
                                </a>
                            </div>
                        </div>
                        <div style="padding: 10px">
                                <c:import url="includes/heatmap-matrix-searchresults-diffanalytics.jsp"/>
                        </div>
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

    <script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js-bundles/expression-atlas-heatmap.bundle.js"></script>

</c:if>

<c:set var="hasBaselineResults" value="${showWidget || not empty firstBaselineCounts}"/>

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
<%-- Assemble the full query information for SEO --%>
<c:set var="fullQueryDescription" value="${fn:replace(searchDescription,'&quot;','')} ${entityNamesList} ${species}"/>
<script>

    window.onload = function (fullSearchDescription) {

        var openPanelIndex = ${param.openPanelIndex != null ? param.openPanelIndex : defaultPanelIndex};

        $('head').append('<meta name="description" content="Baseline and differential expression for ${he.encode(fullQueryDescription)}" />');

        $("#bioentity-info-image").tooltip();
        $("#differential-info-image").tooltip();

        $("#accordion").accordion({
            collapsible: true,
            active: openPanelIndex,
            heightStyle: "content",
            icons: ${hideIcons ? "{ 'header': 'ui-icon-blank'}" : "{ 'header': 'gxaBioEntityCardIconPlus', 'activeHeader': 'gxaBioEntityCardIconMinus' }"},
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

        <c:if test="${showWidget}"><%--@elvariable id="geneQuery" type="uk.ac.ebi.atlas.web.GeneQuery"--%>

        var widgetParameters = "${isGeneSet ? "" : "&propertyType=bioentity_identifier" }" + "${not empty species ? "&species=".concat(species) : ""}";


        var heatmapBuilder = window.exposed;
        heatmapBuilder({
            gxaBaseUrl: '${pageContext.request.contextPath}/',
            params: 'geneQuery=${geneQuery.asUrlQueryParameter()}' + widgetParameters,
            isMultiExperiment: true,
            target: "widgetBody",
            isWidget: false
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


