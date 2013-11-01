<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://atlas.ebi.ac.uk/util" prefix="util" %>

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

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:choose>
    <c:when test="${not empty baselineCounts}">
    <c:set var="resultsCount" value="${baselineCounts.size()}"/>

        <table id="baselineCountsTable">
            <thead>
                <tr>
                    <th>
                        <div style="margin-top:10px">
                            ${resultsCount} search results (showing ${resultsCount} of ${resultsCount})
                        </div>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="baselineCount" items="${baselineCounts}">
                    <tr>
                        <td>
                            <a class="bioEntityCardLink"
                                   href="${base}/experiments/${baselineCount.experimentAccession}?queryFactorType=ORGANISM_PART&queryFactorValues=${param.condition}&geneQuery=${util:urlParamEncode(param.geneQuery)}"
                               title="experiment">
                                    ${baselineCount.species} - ${baselineCount.experimentName}
                            </a>
                        </td>
                        <%-- geneQuery searchs don't have proper counts yet --%>
                        <c:if test="${empty param.geneQuery}">
                            <td class="count">
                                (${baselineCount.count})
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </c:when>
    <c:otherwise>
        <div>No baseline experiments were found.</div>
    </c:otherwise>
</c:choose>
