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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"></c:set>
</c:if>

<c:if test="${isWidget && not empty param.accessKey}">
    <c:set var="additionalQueryOptionsString"
           value="&geneQuery=${preferences.geneQuery}&serializedFilterFactors=${preferences.serializedFilterFactors}"></c:set>
</c:if>

<c:if test="${isWidget && empty param.accessKey}">
    <c:set var="additionalQueryOptionsString"
           value="?geneQuery=${preferences.geneQuery}&serializedFilterFactors=${preferences.serializedFilterFactors}"></c:set>
</c:if>

<c:set var="experimentURL" value="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${experimentAccession}${accessKeyQueryString}${additionalQueryOptionsString}"></c:set>

<td width="100%">
    <div id="experimentDescription">
        <a id="goto-experiment" class="thick-link" title="Experiment Page"
           href="${experimentURL}">${experimentDescription}</a>
        <c:if test="${hasExtraInfo}">
            <a id="extra-info"
               href="${applicationProperties.buildServerURL(pageContext.request)}/external-resources/${experimentAccession}/extra-info.png">
                <img alt="more information"
                     src="${applicationProperties.buildServerURL(pageContext.request)}/resources/images/balloon-ellipsis-icon-left.png">
            </a>
        </c:if>
    </div>
    <div id="experimentOrganisms">Organism(s): <span style="font-style:italic">${allSpecies}</span></div>
    <c:if test="${allArrayDesigns!=null}">
        <div id="experimentArrayDesigns">Array Design(s):
            <c:forEach items="${allArrayDesigns}" var="arrayDesign">
                <a class="array-design" id="${arrayDesign}" title="View array design in ArrayExpress"
                   href="${applicationProperties.getArrayExpressArrayURL(arrayDesign)}"
                   target='_blank'>${arrayDesign}</a>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty pubMedIds and not empty pubMedIds.get(0)}">
        <div id="experimentReferences">Reference(s):
            <c:forEach var="pubMedId" items="${pubMedIds}">
                        <span><a class="pubmed-id" href="${applicationProperties.getPubMedURL(pubMedId)}"
                                 title="View publication in PubMed" target='_blank'>${pubMedId}</a></span>
            </c:forEach>
        </div>
    </c:if>
</td>

