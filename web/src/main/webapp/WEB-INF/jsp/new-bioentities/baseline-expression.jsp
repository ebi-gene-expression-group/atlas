<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="grid_6 alpha" style="width:15%" id="atlasBaselineFacetedSearchFacetsContainer"></div>

<div class="grid_18 omega">
    <c:if test="${!hasBaselineResults}"><p>No baseline results<p></c:if>
    <c:if test="${hasBaselineResults}">
    <div id="atlasBaselineFacetedSearchResultsContainer"></div>
    </c:if>
</div>

<script>
    <c:if test="${hasBaselineResults}">
    var baselineSearcher = window.exposed.baseline,
        baselineFacetsData = ${empty jsonFacets ? 'null' : jsonFacets};
    baselineSearcher("atlasBaselineFacetedSearchFacetsContainer", "atlasBaselineFacetedSearchResultsContainer", baselineFacetsData);
    </c:if>
</script>


