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

<div class="grid_4 alpha" id="gxaBaselineFacetsContainerDiv"></div>

<div class="grid_20 omega">
    <c:if test="${!hasBaselineResults}"><p>No baseline results<p></c:if>
    <c:if test="${hasBaselineResults}">
    <div id="gxaBaselineResultsContainerDiv"></div>
    </c:if>
</div>

<script>
    var selectedSpecies = "${hasSelectedSpecies ? selectedSpecies : ''}";

    <c:if test="${hasBaselineResults}">
    var baselineSearcher = window.exposed.baseline,
        baselineFacetsData = JSON.parse('${jsonFacets}');

    baselineSearcher({
        facetsContainer: "gxaBaselineFacetsContainerDiv",
        resultsContainer: "gxaBaselineResultsContainerDiv",
        selectedSpecies: selectedSpecies,
        facetsTreeData: baselineFacetsData,
        identifier: "${identifier}",
        queryType: "${queryType}"
    });
    </c:if>
</script>


