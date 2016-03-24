<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasSelectedSpecies" type="boolean"--%>
<%--@elvariable id="selectedSpecies" type="java.lang.String"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="queryType" type="java.lang.String"--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="grid_5 alpha" id="gxaBaselineFacetsContainerDiv"></div>
<div class="grid_19 omega" id="gxaBaselineResultsContainerDiv"></div>

<script>
    <c:if test="${hasBaselineResults}">
    var selectedSpecies = "${hasSelectedSpecies ? selectedSpecies : ''}";

    var baselineFacetsData = ${jsonFacets};

    facetedSearch.baseline({
        facetsContainer: "gxaBaselineFacetsContainerDiv",
        resultsContainer: "gxaBaselineResultsContainerDiv",
        showAnatomogramsInput: "showAnatomogramsCheckbox",
        selectedSpecies: selectedSpecies,
        facetsTreeData: baselineFacetsData,
        identifier: "${identifier}",
        queryType: "${queryType}"
    });
    </c:if>
</script>


