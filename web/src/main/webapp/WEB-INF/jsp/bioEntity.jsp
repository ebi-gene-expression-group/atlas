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

<div id="accordion">
    <ul class="bioEntityCardHeader">
        <img id="bioentity-info-image" title="Bio-Entity information" style="position: absolute; left: 0.5em; "
             src="resources/images/bioentity_info_transparent_bkg.png"/>
        <span class="bioEntityCardSymbol">${symbol}</span>
        <span class="bioEntityCardSpecies">${bioEntityPropertyService.getSpecies()}</span>
        <span class="bioEntityCardDescription">${bioEntityPropertyService.getBioEntityDescription()}</span>
    </ul>

    <div class="bioEntityCard">
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

    <ul class="bioEntityCardHeader">
        <img id="differential-info-image" title="Differential Expression" style="position: absolute; left: 0.5em; "
             src="resources/images/updown_transparent_bkg.png"/>
        <span class="bioEntityCardSymbol">Differential Expression</span>
    </ul>


    <div class="bioEntityCard">
        <div class="ui-corner-all bioEntityCardDifferentialSummary">
            <span>${differentialGeneProfileProperties.totalNumberOfResults} search results found</span>
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

        <br/>

        <div id="help-placeholder" style="display: none"></div>

    </div>
</div>

<script>
    $(function () {
        $("#bioentity-info-image").tooltip();
        $("#differential-info-image").tooltip();

        $("#accordion").accordion({
            collapsible:true,
            heightStyle:"content",
            icons:{ "header":"bioEntityCardIconPlus", "activeHeader":"bioEntityCardIconMinus" },
            header:"ul"
        });

        helpTooltipsModule.init('experiment');
    });
</script>


