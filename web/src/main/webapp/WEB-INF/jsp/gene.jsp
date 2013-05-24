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
    <ul class="geneCardHeader">
        <img id="bioentity-info-image" title="Gene information" style="position: absolute; left: 0.5em; "
             src="resources/images/bioentity_info_transparent_bkg.png"/>
        <span class="geneCardSymbol">${symbol}</span>
        <span class="geneCardSpecies">${bioentityPropertyService.getSpecies()}</span>
        <span class="geneCardDescription">${bioentityPropertyService.getBioEntityDescription()}</span>
    </ul>

    <div class="geneCard">
        <table id="geneCardTable">
            <c:forEach var="propertyType" items="${propertyNames.keySet()}">
                <c:set var="propertyLinks" value="${bioentityPropertyService.getPropertyLinks(propertyType)}"/>
                <c:if test="${propertyLinks.size() > 0}">
                    <tr>
                        <td class="geneCardPropertyType">${propertyNames.get(propertyType)}</td>
                        <td class="geneCardPropertyValue">
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
                                           value="<a class=\"geneCardLink\" href=\"${propertyLink.getUrl()}\" target=\"_blank\">"/>
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

    <ul class="geneCardHeader">
        <img id="differential-info-image" title="Differential Expression" style="position: absolute; left: 0.5em; "
             src="resources/images/updown_transparent_bkg.png"/>
        <span class="geneCardSymbol">Differential Expression</span>
    </ul>


    <div class="geneCard">
        <div class="ui-corner-all geneCardDifferentialSummary">
            <span>${differentialGeneProfileProperties.totalNumberOfProfiles} search results found</span>
            <span style="float: right">False Discovery Rate cutoff: ${differentialGeneProfileProperties.fdrCutoff}</span>
        </div>
        <table>
            <c:forEach items="${differentialGeneProfileProperties.differentialGeneProfileLinks}"
                       var="differentialGeneProfileLink">
                <tr>
                    <td><a class="geneCardLink"
                           href="experiments/${differentialGeneProfileLink.url}">${differentialGeneProfileLink.contrast}</a>
                    </td>
                    <td>${differentialGeneProfileLink.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<script>
    $(function () {
        $("#bioentity-info-image").tooltip();
        $("#differential-info-image").tooltip();

        $("#accordion").accordion({
            collapsible:true,
            heightStyle:"content",
            icons:{ "header":"geneCardIconPlus", "activeHeader":"geneCardIconMinus" },
            header:"ul"
        });
    });
</script>


