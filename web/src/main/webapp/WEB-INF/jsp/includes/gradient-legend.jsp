<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

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

<%--@elvariable id="numberUtils" type="uk.ac.ebi.atlas.utils.NumberUtils"--%>
<%--@elvariable id="colourGradient" type="uk.ac.ebi.atlas.utils.ColourGradient"--%>


<div style="float:right; padding-left: 100px">
    <%--<div style="float:left">--%>
    <c:choose>
        <c:when test="${type.isBaseline()}">
            <div style="float:left">
                <table style="font-size:10px;" id="baseline-heatmap-legend">

                    <c:set var="minExpressionLevel" value="${geneProfiles.getMinExpressionLevel()}"/>
                    <c:set var="maxExpressionLevel" value="${geneProfiles.getMaxExpressionLevel()}"/>
                    <fmt:formatNumber type="number"
                                      maxFractionDigits="${minExpressionLevel >= 1 ? 0 : 1}"
                                      value="${minExpressionLevel}" groupingUsed="false"
                                      var="minExpressionLevel"/>
                    <fmt:formatNumber type="number"
                                      maxFractionDigits="${maxExpressionLevel >= 1 ? 0 : 1}"
                                      value="${maxExpressionLevel}" groupingUsed="false"
                                      var="maxExpressionLevel"/>

                    <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('lightGray')}"
                                          highValueColour="${colourGradient.getHexByColourName('blue')}"
                                          lowValueColorExpressionLevel="${minExpressionLevel}"
                                          highValueColorExpressionLevel="${maxExpressionLevel}"/>

                </table>

            </div>
            <div id="baseline-help-diff" data-help-loc="#gradient-base" style="float:left;"/>
        </c:when>
        <c:otherwise>
            <div style="float:left">
                <table style="font-size:10px;" id="diff-heatmap-legend">
                    <c:if test="${((preferences.regulation eq 'DOWN') or (preferences.regulation eq 'UP_DOWN'))
                                    and geneProfiles.getMinDownRegulatedExpressionLevel() != 'NaN'}">

                        <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('lightGray')}"
                                              highValueColour="${colourGradient.getHexByColourName('blue')}"
                                              highValueColorExpressionLevel="${numberUtils.round(geneProfiles.getMinDownRegulatedExpressionLevel(), 2)}"
                                              lowValueColorExpressionLevel="${numberUtils.round(geneProfiles.getMaxDownRegulatedExpressionLevel(), 2)}"/>

                    </c:if>
                    <c:if test="${((preferences.regulation eq 'UP') or (preferences.regulation eq 'UP_DOWN'))
                                    and geneProfiles.getMinUpRegulatedExpressionLevel() != 'NaN'}">

                        <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('pink')}"
                                              highValueColour="${colourGradient.getHexByColourName('red')}"
                                              highValueColorExpressionLevel="${numberUtils.round(geneProfiles.getMaxUpRegulatedExpressionLevel(), 2)}"
                                              lowValueColorExpressionLevel="${numberUtils.round(geneProfiles.getMinUpRegulatedExpressionLevel(), 2)}"/>

                    </c:if>
                </table>

            </div>
            <div id="gradient-help-diff" data-help-loc="#gradient-differential" style="float:left;"/>
        </c:otherwise>
    </c:choose>

    <%--</div>--%>

    <%--<div data-help-loc="#gradient" style="float:left;"/>--%>
</div>
