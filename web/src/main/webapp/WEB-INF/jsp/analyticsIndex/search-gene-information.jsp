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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/faceted-search.css"/>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div id="search_result_pg">

<div id="help-placeholder" style="display: none"></div>

<!-- Search box -->
<%--<section class="grid_24" style="margin-bottom:18px;">--%>
  <%--<%@ include file="includes/search-form.jsp" %>--%>
<%--</section>--%>
<!-- /Search box -->



<c:if test="${not empty searchDescription}" >
  <c:if test="${hasGeneInformation}" >
  <!-- Simple page header -->
      <div id="headerBody" class="gxaBioEntityCardHeader" style="margin-bottom: 20px;margin-top: 10px;">
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
      </div>
  </c:if>

  <c:if test="${!hasGeneInformation}" >
    <div class="container">
    <div class="page-header">
    <h2 class="strapline">Search result for <span class="searchterm">${searchDescription}</span></h2>
    </div>
    <%--<h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>--%>
    </div>
  </c:if>

<!-- /Simple page header -->
</c:if>

<section class="grid_24">

  <ul class="nav nav-tabs">
    <c:if test="${hasBaselineResults}"><li title="Baseline experiments"><a href="${pageContext.request.contextPath}/search?geneQuery=${geneQuery.asUrlQueryParameter()}" >Baseline experiments</a></li></c:if>
    <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" class="disabled" >Baseline experiments</li></c:if>
    <c:if test="${hasDifferentialResults}"><li title="Differential experiments"><a href="${pageContext.request.contextPath}/search/differential?geneQuery=${geneQuery.asUrlQueryParameter()}">Differential experiments</a></li></c:if>
    <c:if test="${!hasDifferentialResults}"> <li title="Differential experiments" class="disabled">Differential experiments</li></c:if>
    <c:if test="${hasGeneInformation}"> <li title="Bioentity information" class="active"><a data-toggle="tab" href="${pageContext.request.contextPath}/search/bioentity?geneQuery=${geneQuery.asUrlQueryParameter()}">Bioentity information</a></li> </c:if>
    <c:if test="${!hasGeneInformation}"><li title="Bioentity information" class="disabled">Bioentity information</li> </c:if>
  </ul>

  <div id="geneInfo" class="tab-pane fade in active">

    <div class="grid_18 omega">

        <c:if test="${!hasGeneInformation}"><p>No bioentity information<p></c:if>

        <c:if test="${hasGeneInformation}">

            <div id="infoBody" class="gxaBioEntityCard" style="margin-top: 20px;">
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




    </div>
  </div>

</section><!-- /search_facet -->


</div>