<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home_request-preferences.css" media="screen">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/request-preferences.css" media="screen">

<div>
    <form:form method="get" commandName="preferences" id="prefForm">
        <input type="hidden" name="accessKey" value="${param.accessKey}"/>
        <form:hidden path="serializedFilterFactors"/>
        <form:hidden path="queryFactorType"/>
        <form:hidden path="rootContext"/>
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden id="displayLevels" path="displayLevels"/>
        <form:hidden id="displayGeneDistribution" path="displayGeneDistribution"/>
        <c:if test="${type.microarray}">
            <form:hidden path="arrayDesignAccession"/>
        </c:if>

        <form:errors path="*" cssClass="gxaError"/>
        <table class="gxaFormGrid">
            <tr>
                <td class="gxaTableColumn35">
                    <form:label path="geneQuery">Gene query</form:label>
                    <span data-help-loc="#geneSearch"></span>
                </td>

                <c:if test="${!type.baseline}">
                    <td class="gxaTableColumn10"> <!-- empty placeholder above contrast-up-down-menu.jsp --> </td>
                </c:if>

                <c:if test="${selectedFilterFactorNamesAndValues.size() > 0}">
                    <td class="gxaTableColumn20">
                        <label>Filtered by</label>
                        <span data-help-loc="#filterBy"></span>
                    </td>
                </c:if>

                <td class="gxaTableColumn17">
                    <form:label path="queryFactorValues">${queryFactorName}</form:label>
                    <span data-help-loc="#factorSearch${type.baseline ? '' : '-differential'}"></span>
                </td>
                <c:choose>
                    <c:when test="${type.baseline}">
                        <td class="gxaTableColumn17">
                            <form:label path="cutoff">Expression level cutoff</form:label>
                            <span data-help-loc="#cutoff${type.proteomicsBaseline ? '-proteomics' : ''}"></span>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class="gxaTableColumn17">
                            <form:label path="cutoff">Adjusted <i>p</i>-value cutoff</form:label>
                            <span data-help-loc="#cutoff-differential"></span>
                        </td>
                        <td class="gxaTableColumn17">
                            <form:label path="foldChangeCutOff">Log<sub>2</sub>-fold change cutoff</form:label>
                            <span data-help-loc="#foldChangeCutOff"></span>
                        </td>
                    </c:otherwise>
                </c:choose>
                <td rowspan="2" class="gxaTableColumn10" style="vertical-align: middle;">
                    <div class="gxaExperimentRequestPreferencesActionButtons">
                        <div style="text-align: right;">
                            <input id="submit-button" type="submit" value="Apply"/>
                        </div>
                        <div style="text-align: right;">
                            <input id="reset-button" type="button" value="Reset"/>
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <textarea id="geneQuery" name="geneQuery" rows="2" cols="36" >${preferences.geneQuery.toJson()}</textarea>
                </td>

                <c:if test="${selectedFilterFactorNamesAndValues.size() > 0}">
                    <td>
                        <c:import url="includes/filterby-menu.jsp"/>
                    </td>
                </c:if>
                <c:if test="${!type.baseline}">
                    <td>
                        <c:import url="includes/contrast-up-down-menu.jsp"/>
                    </td>
                </c:if>

                <td>
                    <div>
                        <c:set var="isSingleContrast" value="${(!type.baseline) && allQueryFactors.size() == 1}"/>
                        <c:set var="itemLabel" value="${type.baseline ? 'value' : 'displayName'}"/>
                        <c:set var="itemValue" value="${type.baseline ? 'value' : 'id'}"/>
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

                <td>
                    <c:choose>
                        <c:when test="${fn:endsWith('' + preferences.cutoff, '.0')}">
                            <fmt:formatNumber value="${preferences.cutoff}" groupingUsed="false"
                                              type="number"
                                              maxFractionDigits="0"
                                              var="formattedCutoff"/>
                            <%--height value set after Chosen input box to the left--%>
                            <form:input style="height:27px; border: 1px solid #AAA; padding: 0px 5px;" size="10" path="cutoff" value="${formattedCutoff}" id="cutoff"/>
                        </c:when>
                        <c:otherwise>
                            <form:input style="height:27px; border: 1px solid #AAA; padding: 0px 5px;" size="10" path="cutoff" id="cutoff"/>
                        </c:otherwise>
                    </c:choose>
                </td>

                <c:if test="${!type.baseline}">
                    <td>
                        <form:input style="height:27px; border: 1px solid #AAA; padding: 0px 5px;" size="10" path="foldChangeCutOff" id="foldChangeCutOff"/>
                    </td>
                </c:if>
            </tr>
        </table>
    </form:form>


    <div class="grid_24" style="padding-top: 15px; padding-bottom: 30px">
        <div id="gxaGeneDistributionPanel">
            <div id="gene-distribution" style="height: 100px;">
            </div>
            <span data-help-loc="#gene-distribution" style="vertical-align: top"></span>
        </div>

        <div id="gxaSliderAndChart">
            <div>
                <div id="gxaGeneDistributionButton" style="float: left;">
                    <a id="gxaDisplayChart" title="Display gene distribution" href="#">
                        <img alt="Display gene distribution" src="resources/images/yellow-chart-icon-16.png"/>
                    </a>
                </div>
                <div id="slider-range-max" style="float: left; font-size: 65%; width: 94%; margin: 8px 12px"></div>
            </div>
            <span id="slider-help" data-help-loc="#slider"></span>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        initForm();
        onResetButtonRemoveAllTagsAndSelectHomoSapiens();

        function initForm() {
            $('#prefForm').submit(function() {
                var $geneQuery = $('#geneQuery'),
                    geneQueryTags = $geneQuery.jsonTagEditor('getTags')[0].tags;
                $geneQuery.val(JSON.stringify(geneQueryTags));
            });
        }

        function onResetButtonRemoveAllTagsAndSelectHomoSapiens() {
            $('#reset-button').on('click' , function () {
                // Remove all tags
                var $geneQuery = $('#geneQuery'),
                    geneQueryTags = $geneQuery.jsonTagEditor('getTags')[0].tags;
                geneQueryTags.forEach(function(geneQueryTag){
                    $geneQuery.jsonTagEditor('removeTag', geneQueryTag.value);
                });

                $('#regulation3').prop('checked', true);

                $('#queryFactorValues').val('').trigger('chosen:updated');
                $('#specific').prop('checked', true)

                $('#cutoff').val(${type.baseline ? '0.5' : '0.05'});
                $('#foldChangeCutOff').val(1.0);
            });
        }
    });
</script>


