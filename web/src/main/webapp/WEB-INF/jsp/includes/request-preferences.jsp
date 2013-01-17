<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<div id="preferencesFormBlock" class="block-max-width">
    <form:form method="get" commandName="preferences" id="prefForm">
        <form:hidden path="filterFactorValues"/>
        <form:hidden path="queryFactorType"/>
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden id="displayLevels" path="displayLevels"/>
        <form:hidden id="displayGeneDistribution" path="displayGeneDistribution"/>

        <form:errors title="HeatmapMatrixSize" path="*" cssClass="error"/>
        <table class="form-grid">
            <tr>
                <td>
                    <div class="tooltip-div" data-help-loc="#geneSearch">
                        <span>
                            <form:label path="geneQuery">Gene Query</form:label>
                        </span>
                        <span>
                            <form:textarea path="geneQuery" maxlength="900" rows="3" cols="35"></form:textarea>
                        </span>
                    </div>
                </td>
                <td>
                    <ul id="filterBy">
                        <c:forEach items="${filterByMenu}" var="level1">
                            <li>
                                <a href="#">${level1.key}</a>
                                <ul>
                                    <c:forEach items="${level1.value}" var="level2">
                                        <li>
                                            <a href="#">${level2.key}</a>
                                            <ul>
                                                <c:forEach items="${level2.value}" var="level3">
                                                    <li>
                                                        <a href="#">${level3.key}</a>
                                                        <ul>
                                                            <c:forEach items="${level3.value}" var="link">
                                                                <li><a href="#">${link}</a></li>
                                                            </c:forEach>
                                                        </ul>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </td>
                <td>
                    <div id="factor-values-div" class="tooltip-div" data-help-loc="#factorSearch">
                        <span>
                            <form:label path="queryFactorValues">${formattedQueryFactorType}</form:label>
                        </span>
                        <span>
                            <form:select path="queryFactorValues" data-placeholder="(all ${formattedQueryFactorType}s)"
                                         tabindex="-1"
                                         items="${heatmapFactorValues}" cssClass="chzn-select"
                                         cssStyle="width:350px;display:none"/>
                        </span>
                    </div>
                    <span>
                        <form:checkbox id="specific"
                                       path="specific"
                                       label="Specific"></form:checkbox>
                    </span>
                </td>
                <td>
                    <div class="tooltip-div" data-help-loc="#cutoff">
                        <span>
                            <form:label path="cutoff">Expression level cutoff</form:label>
                        </span>
                        <span>
                            <c:choose>
                                <c:when test="${fn:endsWith('' + preferences.cutoff, '.0')}">
                                    <fmt:formatNumber value="${preferences.cutoff}" groupingUsed="false"
                                                      type="number"
                                                      maxFractionDigits="0"
                                                      var="formattedCutoff"/>
                                    <form:input size="10" path="cutoff" value="${formattedCutoff}" id="cutoff"
                                                style="border:1; font-weight:bold;"/>
                                </c:when>
                                <c:otherwise>
                                    <form:input size="10" path="cutoff" id="cutoff"
                                                style="border:1; font-weight:bold;"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </td>
                <td rowspan="4">
                    <span>
                        <input id="submit-button" type="submit" value="Search"/>
                    </span>
                    <span>
                        <input id="reset-button" type="button" value="Reset"/>
                    </span>
                </td>
            </tr>
        </table>
        <br/>

    </form:form>

    <div id="gene-distribution-panel">
        <div id="gene-distribution"
             data-help-loc="#gene-distribution" style="margin-bottom:0px;height:100px">
        </div>
    </div>

    <div id="gene-distribution-button" style="float:left">
        <a id="display-chart" title="Display gene distribution" class="button-image" href="#">
            <img alt="Display gene distribution" src="resources/images/yellow-chart-icon-16.png"/>
        </a>
    </div>
    <div id="slider-range-max" data-help-loc="#slider"
         style="font-size:65%;width:920px;margin-left:27px;margin-right:17px; margin-top:10px"></div>

</div>

<script type="text/javascript">
    $(function () {
        $("#filterBy").menu();
    });
</script>
