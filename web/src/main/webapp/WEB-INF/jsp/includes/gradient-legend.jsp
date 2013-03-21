<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <div style="float:left">
        <c:choose>
            <c:when test="${type eq 'BASELINE'}">
                <c:set var="minExpressionLevel" value="${geneProfiles.getMinExpressionLevel()}"/>
                <c:set var="maxExpressionLevel" value="${geneProfiles.getMaxExpressionLevel()}"/>
                <table style="font-size:10px;" id="heatmap-legenda">
                    <tr>
                        <td>
                        <span style="display:none" class="gradient-level-min">
                            <fmt:formatNumber type="number"
                                              value="${minExpressionLevel}"
                                              groupingUsed="false"/>
                        </span>
                        </td>
                        <td width="200px">
                            <div class="color-gradient" style="
                                    overflow:auto;
                                    background-image:
                                    -webkit-gradient(linear, left top, right top,color-stop(0, ${colourGradient.minColour}), color-stop(1, ${colourGradient.maxColour}));

                                    background-image: -moz-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                    background-image: -o-linear-gradient(left, ${colourGradient.minColour}, ${colourGradient.maxColour});

                                    filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1,
                                    startColorstr=${colourGradient.minColour},endColorstr=${colourGradient.maxColour});">
                                &nbsp;
                            </div>
                        </td>
                        <td>
                        <span style="display:none" class="gradient-level-max">
                            <fmt:formatNumber type="number"
                                              value="${maxExpressionLevel}"
                                              groupingUsed="false"/>
                        </span>
                        </td>
                    </tr>
                </table>
            </c:when>
            <c:otherwise>
                <c:if test="${((preferences.regulation eq 'DOWN') or (preferences.regulation eq 'UP_DOWN'))
                                and geneProfiles.getMinDownRegulatedExpressionLevel() != 'NaN'}">
                    <c:set var="minExpressionLevel"
                           value="${numberUtils.htmlFormatDouble(geneProfiles.getMinDownRegulatedExpressionLevel())}"/>
                    <c:set var="maxExpressionLevel"
                           value="${numberUtils.htmlFormatDouble(geneProfiles.getMaxDownRegulatedExpressionLevel())}"/>
                    <c:set var="lowValueColour" value="${colourGradient.getHexByColourName('lightGray')}"/>
                    <c:set var="highValueColour" value="${colourGradient.getHexByColourName('blue')}"/>
                    <table style="font-size:10px;" id="heatmap-legenda">
                        <tr>
                            <td>
                                <span style="display:none" class="gradient-level-min">
                                        ${maxExpressionLevel}
                                </span>
                            </td>
                            <td width="200px">
                                <div class="color-gradient" style="
                                        overflow:auto;
                                        background-image:
                                        -webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));

                                        background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                                        background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                                        filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1,
                                        startColorstr=${lowValueColour},endColorstr=${highValueColour});">
                                    &nbsp;
                                </div>
                            </td>
                            <td>
                                <span style="display:none" class="gradient-level-max">
                                        ${minExpressionLevel}
                                </span>
                            </td>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${((preferences.regulation eq 'UP') or (preferences.regulation eq 'UP_DOWN'))
                                and geneProfiles.getMinUpRegulatedExpressionLevel() != 'NaN'}">
                    <c:set var="minExpressionLevel"
                           value="${numberUtils.htmlFormatDouble(geneProfiles.getMinUpRegulatedExpressionLevel())}"/>
                    <c:set var="maxExpressionLevel"
                           value="${numberUtils.htmlFormatDouble(geneProfiles.getMaxUpRegulatedExpressionLevel())}"/>
                    <c:set var="lowValueColour" value="${colourGradient.getHexByColourName('pink')}"/>
                    <c:set var="highValueColour" value="${colourGradient.getHexByColourName('red')}"/>
                    <table style="font-size:10px;" id="heatmap-legenda">
                        <tr>
                            <td>
                            <span style="display:none" class="gradient-level-min">
                                    ${maxExpressionLevel}
                            </span>
                            </td>
                            <td width="200px">
                                <div class="color-gradient" style="
                                        overflow:auto;
                                        background-image:
                                        -webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));

                                        background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                                        background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                                        filter:progid:DXImageTransform.Microsoft.Gradient(GradientType=1,
                                        startColorstr=${lowValueColour},endColorstr=${highValueColour});">
                                    &nbsp;
                                </div>
                            </td>
                            <td>
                            <span style="display:none" class="gradient-level-max">
                                    ${minExpressionLevel}
                            </span>
                            </td>
                        </tr>
                    </table>
                </c:if>
            </c:otherwise>
        </c:choose>

    </div>

    <div data-help-loc="#gradient" style="float:left;"/>
</div>
