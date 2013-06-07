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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="text" value="${entityIdentifier}" style="display: none" id="searchterm">

<section class="grid_17 alpha">
    <h2 class="strapline">
        Expression Atlas results for <span class="searchterm">${entityIdentifier}</span>
    </h2>
</section>
<aside id="search-extras" class="grid_6 omega shortcuts expander">
    <div id="ebi_search_results"><h3 data-icon="u" class="slideToggle icon icon-functional">Show more data from
        EMBL-EBI</h3>
    </div>
</aside>

<section class="grid_23">
    <div id="accordion">
        <ul id="infoHeader" class="bioEntityCardHeader">
            <img id="bioentity-info-image" title="Bio-Entity information" style="position: absolute; left: 0.5em; "
                 src="resources/images/bioentity_info_transparent_bkg.png"/>
            <span class="bioEntityCardBioentityName">
                <c:forEach var="entityName" varStatus="loopStatus" items="${bioEntityPropertyService.getEntityNames()}">
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
                    <c:set var="propertyLinks" value="${bioEntityPropertyService.getPropertyLinks(propertyType)}"/>
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
                </c:forEach>
            </table>
        </div>


        <ul id="baselineProfileHeader" class="bioEntityCardHeader">
            <img id="baseline-info-image" title="Baseline Expression"
                 style="position: absolute; left: 0.5em; "
                 src="resources/images/allup2_transparent_bkg.png"/>
            <span class="bioEntityCardBioentityName">Baseline Expression</span>
        </ul>


        <div id="baselineProfileBody" class="bioEntityCard">

            <div class="ui-corner-all bioEntityCardDifferentialSummary">
                <span style="float: right">Expression Level cut-off: 0.5</span>
            </div>

            <div id="widgetBody"></div>
        </div>


        <c:if test="${differentialGeneProfileProperties.totalNumberOfResults > 0}">
            <ul id="diffProfileHeader" class="bioEntityCardHeader">
                <img id="differential-info-image" title="Differential Expression"
                     style="position: absolute; left: 0.5em; "
                     src="resources/images/updown_transparent_bkg.png"/>
                <span class="bioEntityCardBioentityName">Differential Expression</span>
            </ul>


            <div id="diffProfileBody" class="bioEntityCard">
                <div class="ui-corner-all bioEntityCardDifferentialSummary">
                    <span>${differentialGeneProfileProperties.totalNumberOfResults} search result(s) found</span>
                    <span style="float: right">False Discovery Rate cutoff: ${differentialGeneProfileProperties.fdrCutoff}</span>
                </div>

                <div id="heatmap-div" style="display:none;">
                    <table style="margin-left:auto;margin-right:auto;">
                        <tr>
                            <td>
                                <button id='display-levels' style="margin-top: 5px; margin-bottom: 5px">
                                    <label for='display-levels'>Display levels</label>
                                </button>
                            </td>
                            <td>
                                <c:import url="includes/gradient-legend.jsp"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <c:import url="includes/heatmap-matrix-organism-oriented.jsp"/>
                            </td>
                        </tr>
                    </table>
                </div>

            </div>
        </c:if>
    </div>

</section>

<br/>

<div id="help-placeholder" style="display: none"></div>

<script src="${pageContext.request.contextPath}/resources/js/ebi-global-search-run.js"></script>
<script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>


<script language="JavaScript" type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/biojs/Biojs.js"></script>
<script language="JavaScript" type="text/javascript" src="/gxa/resources/biojs/AtlasHeatmap.js"></script>

<script>

    window.onload = function () {

        var openPanelIndex = ${param.openPanelIndex != null ? param.openPanelIndex : 1};

        $("#bioentity-info-image").tooltip();
        $("#differential-info-image").tooltip();

        $("#accordion").accordion({
            collapsible: true,
            active: openPanelIndex,
            heightStyle: "content",
            icons: { "header": "bioEntityCardIconPlus", "activeHeader": "bioEntityCardIconMinus" },
            header: "ul"
        });

        helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}');

        var instance = new Biojs.AtlasHeatmap({
            featuresUrl: '/gxa/widgets/heatmap/protein?geneQuery=${entityIdentifier}&geneSetMatch=true',
            target: "widgetBody"
        });
    };
</script>


