<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<c:choose>
    <c:when test="${type eq 'BASELINE'}">
        <c:set var="minExpressionLevel" value="${geneProfiles.getMinExpressionLevel()}"/>
        <c:set var="maxExpressionLevel" value="${geneProfiles.getMaxExpressionLevel()}"/>
    </c:when>
    <c:otherwise>
        <c:set var="minExpressionLevel" value="0"/>
        <c:set var="maxExpressionLevel" value="1"/>
    </c:otherwise>
</c:choose>

<div style="float:right">
    <table style="font-size:10px; float: right" id="heatmap-legenda">
        <tr>
            <td>
                                        <span style="display:none" class="gradient-level-min">
                                            <fmt:formatNumber type="number"
                                                              value="${minExpressionLevel}"
                                                              groupingUsed="false"/>
                                        </span>
            </td>
            <td width="200px">
                <div style="
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
                <span data-help-loc="#gradient"/>
            </td>

        </tr>
    </table>
</div>