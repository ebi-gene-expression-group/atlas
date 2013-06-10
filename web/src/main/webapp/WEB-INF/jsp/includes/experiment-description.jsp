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

<td style="width:140px;padding-right:20px">
    <div class="experiment-accession">
        <a id="goto-ae" class="thick-link"
           href="${applicationProperties.getArrayExpressURL(experimentAccession)}"
           title="View experiment in ArrayExpress"
           target="_blank">${experimentAccession}</a>
    </div>
</td>
<td width="100%">
    <div id="experimentDescription">
        <a id="goto-experiment" class="thick-link" title="Experiment Page"
           href="experiments/${experimentAccession}">${experimentDescription}</a>
        <c:if test="${hasExtraInfo}">
            <a id="extra-info" href="external-resources/${experimentAccession}/extra-info.png">
                <img alt="more information" src="resources/images/balloon-ellipsis-icon-left.png">
            </a>
        </c:if>
    </div>
    <div id="experimentOrganisms">Organism(s): <span style="font-style:italic">${allSpecies}</span></div>
    <c:if test="${allArrayDesigns!=null}">
        <div>Array Design(s):
            <c:forEach items="${allArrayDesigns}" var="arrayDesign">
                <a class="array-design" id="${arrayDesign}" title="View array design in ArrayExpress"
                   href="http://www.ebi.ac.uk/arrayexpress/arrays/${arrayDesign}"
                   target='_blank'>${arrayDesign}</a>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty pubMedIds and not empty pubMedIds.get(0)}">
        <div id="experimentReferences">Reference(s):
            <c:forEach var="pubMedId" items="${pubMedIds}">
                        <span><a href="http://www.ncbi.nlm.nih.gov/pubmed/${pubMedId}"
                                 title="View publication in PubMed">${pubMedId}</a></span>
            </c:forEach>
        </div>
    </c:if>
</td>

