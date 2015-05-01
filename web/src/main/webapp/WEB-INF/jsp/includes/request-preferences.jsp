<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.SearchRequest"--%>
<div id="preferencesFormBlock" class="gxaExperimentRequestPreferencesBlockMaxWidth">
    <form:form method="get" commandName="preferences" id="prefForm">
        <input type="hidden" name="accessKey" value="${param.accessKey}"/>
        <form:hidden path="serializedFilterFactors"/>
        <form:hidden path="queryFactorType"/>
        <form:hidden path="rootContext"/>
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden id="displayLevels" path="displayLevels"/>
        <form:hidden id="displayGeneDistribution" path="displayGeneDistribution"/>
        <c:if test="${type.isMicroarray()}">
            <form:hidden path="arrayDesignAccession"/>
        </c:if>

        <form:errors path="*" cssClass="gxaError"/>
        <table class="gxaFormGrid">
            <tr>
                <td class="gxaExperimentRequestPreferencesFluidWideTableColumn">
                    <form:label path="geneQuery">Gene query</form:label>
                    <span data-help-loc="#geneSearch"></span>
                </td>

                <c:if test="${!type.isBaseline()}">
                    <td class=""> <!-- empty placeholder above Contrasts --> </td>
                </c:if>

                <c:if test="${selectedFilterFactorNamesAndValues.size() > 0}">
                    <td class="gxaExperimentRequestPreferencesFluidTableColumn">
                        <label>Filtered by</label>
                        <span data-help-loc="#filterBy"></span>
                    </td>
                </c:if>

                <td class="gxaExperimentRequestPreferencesFluidWideTableColumn">
                    <form:label path="queryFactorValues">${queryFactorName}</form:label>
                    <span data-help-loc="#factorSearch${type.isBaseline() ? '' : '-differential'}"></span>
                </td>
                <c:choose>
                    <c:when test="${type.isBaseline()}">
                        <td class="gxaExperimentRequestPreferencesFluidTableColumn">
                            <form:label path="cutoff">Expression level cutoff</form:label>
                            <span data-help-loc="#cutoff${type.isProteomicsBaseline() ? '-proteomics' : ''}"></span>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class="gxaExperimentRequestPreferencesFluidNarrowTableColumn">
                            <form:label path="cutoff">Adjusted <i>p</i>-value cutoff</form:label>
                            <span data-help-loc="#cutoff-differential"></span>
                        </td>
                        <td class="gxaExperimentRequestPreferencesFluidNarrowTableColumn">
                            <form:label path="foldChangeCutOff">Log<sub>2</sub>-fold change cutoff</form:label>
                            <span data-help-loc="#foldChangeCutOff"></span>
                        </td>
                    </c:otherwise>
                </c:choose>
                <td rowspan="2" class="gxaExperimentRequestPreferencesFixedSizeTableColumn">
                    <div class="gxaExperimentRequestPreferencesActionButtons">
                        <div>
                            <input id="submit-button" type="submit" value="Apply"/>
                        </div>
                        <div>
                            <input id="reset-button" type="button" value="Reset"/>
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td class="gxaExperimentRequestPreferencesFluidWideTableColumn">
                    <textarea id="geneQuery" name="geneQuery" rows="2" cols="36" >${preferences.geneQuery.asTags()}</textarea>
                    <form:checkbox style="vertical-align: middle" id="exactMatch" path="exactMatch" label="Exact match"/>
                </td>

                <c:if test="${selectedFilterFactorNamesAndValues.size() > 0}">
                    <td class="gxaExperimentRequestPreferencesFluidTableColumn">
                        <c:import url="includes/filterby-menu.jsp"/>
                    </td>
                </c:if>
                <c:if test="${!type.isBaseline()}">
                    <td class="">
                        <c:import url="includes/contrast-up-down-menu.jsp"/>
                    </td>
                </c:if>

                <td class="gxaExperimentRequestPreferencesFluidWideTableColumn">
                    <div>
                        <c:set var="isSingleContrast" value="${(!type.isBaseline()) && allQueryFactors.size() == 1}"/>
                        <c:set var="itemLabel" value="${type.isBaseline() ? 'value' : 'displayName'}"/>
                        <c:set var="itemValue" value="${type.isBaseline() ? 'value' : 'id'}"/>
                        <%--@elvariable id="stringUtil" type="uk.ac.ebi.atlas.utils.StringUtil"--%>
                        <form:select path="queryFactorValues" data-placeholder="(any ${stringUtil.lowerCaseIfNotAllUpperCase(queryFactorName)}s)"
                                     tabindex="-1"
                                     items="${allQueryFactors}" itemValue="${itemValue}" itemLabel="${itemLabel}"
                                     cssStyle="width:100%;"
                                     disabled="${isSingleContrast ? true : false}"/>
                    </div>
                    <form:checkbox style="vertical-align: middle" id="specific" path="specific" label="Specific" disabled="${isSingleContrast ? true : false}"/>
                    <span data-help-loc="#specific${type.isBaseline() ? '' : '-differential'}"
                          style="display:inline-block"></span>
                </td>

                <td class="gxaExperimentRequestPreferencesFluidTableColumn">
                    <div>
                        <c:choose>
                            <c:when test="${fn:endsWith('' + preferences.cutoff, '.0')}">
                                <fmt:formatNumber value="${preferences.cutoff}" groupingUsed="false"
                                                  type="number"
                                                  maxFractionDigits="0"
                                                  var="formattedCutoff"/>
                                <form:input size="10" path="cutoff" value="${formattedCutoff}" id="cutoff"/>
                            </c:when>
                            <c:otherwise>
                                <form:input size="10" path="cutoff" id="cutoff"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>

                <c:if test="${!type.isBaseline()}">
                    <td class="gxaExperimentRequestPreferencesFluidTableColumn">
                        <form:input size="10" path="foldChangeCutOff" id="foldChangeCutOff"/>
                    </td>
                </c:if>
            </tr>
        </table>
        <br/>
    </form:form>
    <div style="min-width: 955px;position:relative" id="gene-distribution-panel">
        <div class="gxaExperimentRequestPreferencesBarchartTooltip" id="barChartTooltip"></div>
        <div id="gene-distribution" style="height:100px;width:940px;display:inline-block;">
        </div>
        <span data-help-loc="#gene-distribution" style="vertical-align: top"></span>
    </div>

    <div style="min-width: 955px;display:none;" id="sliderAndChart">
        <div style="display:inline-block">
            <div id="gene-distribution-button" style="float:left">
                <a id="display-chart" title="Display gene distribution" class="gxaButtonImage" href="#">
                    <img alt="Display gene distribution" src="resources/images/yellow-chart-icon-16.png"/>
                </a>
            </div>
            <div id="slider-range-max"
                 style="font-size:65%;width:910px;margin-left:27px;margin-right:0px; margin-top:10px"></div>
        </div>
        <span id="slider-help" data-help-loc="#slider"></span>
    </div>
</div>


