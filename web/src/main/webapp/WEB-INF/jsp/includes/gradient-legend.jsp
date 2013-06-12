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

<div style="float:right; padding-left: 100px">
    <%--<div style="float:left">--%>
    <c:choose>
        <c:when test="${type eq 'BASELINE'}">
            <div style="float:left">
                <table style="font-size:10px;" id="baseline-heatmap-legend">

                    <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('lightGray')}"
                                          highValueColour="${colourGradient.getHexByColourName('blue')}"
                                          lowValueColorExpressionLevel="${geneProfiles.getMinExpressionLevel()}"
                                          highValueColorExpressionLevel="${geneProfiles.getMaxExpressionLevel()}"/>

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
                                              highValueColorExpressionLevel="${numberUtils.htmlFormatDouble(geneProfiles.getMinDownRegulatedExpressionLevel())}"
                                              lowValueColorExpressionLevel="${numberUtils.htmlFormatDouble(geneProfiles.getMaxDownRegulatedExpressionLevel())}"/>

                    </c:if>
                    <c:if test="${((preferences.regulation eq 'UP') or (preferences.regulation eq 'UP_DOWN'))
                                    and geneProfiles.getMinUpRegulatedExpressionLevel() != 'NaN'}">

                        <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('pink')}"
                                              highValueColour="${colourGradient.getHexByColourName('red')}"
                                              highValueColorExpressionLevel="${numberUtils.htmlFormatDouble(geneProfiles.getMinUpRegulatedExpressionLevel())}"
                                              lowValueColorExpressionLevel="${numberUtils.htmlFormatDouble(geneProfiles.getMaxUpRegulatedExpressionLevel())}"/>

                    </c:if>
                </table>

            </div>
            <div id="gradient-help-diff" data-help-loc="#gradient-diff" style="float:left;"/>
        </c:otherwise>
    </c:choose>

    <%--</div>--%>

    <%--<div data-help-loc="#gradient" style="float:left;"/>--%>
</div>
