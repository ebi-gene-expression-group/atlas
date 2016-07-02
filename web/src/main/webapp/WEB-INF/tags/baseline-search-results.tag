<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences"--%>
<%--@elvariable id="requestParameters" type="uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="exactMatch" required="true" type="java.lang.Boolean"%>
<%@ attribute name="geneQuery" required="true" type="uk.ac.ebi.atlas.web.GeneQuery"%>
<%@ attribute name="firstBaselineCounts" required="true" type="java.lang.Iterable"%>
<%@ attribute name="remainingBaselineCounts" required="true" type="java.lang.Iterable"%>
<%@ attribute name="hideSpecies" type="java.lang.Boolean"%>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<table id="baselineCountsTable">
    <tbody>
        <c:forEach var="baselineResult" items="${firstBaselineCounts}">
            <tr>
                <td>
                    <a class="gxaBioEntityCardLink"
                       href="${base}/experiments/${baselineResult.experimentAccession}?_specific=on&queryFactorType=${baselineResult.defaultQueryFactorType}&queryFactorValues=${applicationProperties.encodeMultiValues(baselineResult.defaultFactorValuesForSpecificAssayGroupsWithCondition)}&geneQuery=${geneQuery.asUrlQueryParameter()}&exactMatch=${exactMatch}${baselineResult.filterFactors.isEmpty() ? "" : "&serializedFilterFactors=".concat(baselineResult.serializedFilterFactors)}">
                        <c:choose>
                            <c:when test="${hideSpecies}">
                                ${baselineResult.description()}
                            </c:when>
                            <c:otherwise>
                                ${baselineResult.descriptionWithSpecies()}
                            </c:otherwise>
                        </c:choose>

                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${not empty remainingBaselineCounts}">
            <tr id="gxaMoreResultsRow" >
                <td><a id="gxaMoreBaselineResultsLink" href="">${remainingBaselineCounts.size()} more results...</a></td>
            </tr>

            <c:forEach var="baselineResult" items="${remainingBaselineCounts}">
                <tr class="gxaAdditionalResultRow" style="display:none">
                    <td>
                        <a class="gxaBioEntityCardLink"
                           href="${base}/experiments/${baselineResult.experimentAccession}?_specific=on&queryFactorType=${baselineResult.defaultQueryFactorType}&queryFactorValues=${applicationProperties.encodeMultiValues(baselineResult.defaultFactorValuesForSpecificAssayGroupsWithCondition)}&geneQuery=${geneQuery.asUrlQueryParameter()}&exactMatch=${exactMatch}${baselineResult.filterFactors.isEmpty() ? "" : "&serializedFilterFactors=".concat(baselineResult.serializedFilterFactors)}"
                           title="experiment">
                            <c:choose>
                                <c:when test="${hideSpecies}">
                                    ${baselineResult.description()}
                                </c:when>
                                <c:otherwise>
                                    ${baselineResult.descriptionWithSpecies()}
                                </c:otherwise>
                            </c:choose>

                        </a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>

<script>
    $("#gxaMoreBaselineResultsLink").click(function() {
        $("#gxaMoreResultsRow").hide();
        $(".gxaAdditionalResultRow").show();
        return false;
    });
</script>

