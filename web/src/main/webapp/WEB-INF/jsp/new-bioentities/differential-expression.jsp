<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="grid_6 alpha" id="atlasDifferentialFacetedSearchFacetsContainer"></div>

<div class="grid_18 omega">
    <c:if test="${!hasDifferentialResults}"><p>No differential results<p></c:if>
    <c:if test="${hasDifferentialResults}">
    <div id="atlasDifferentialFacetedSearchResultsContainer"></div>
    </c:if>
</div>

<script>
    <c:if test="${hasDifferentialResults}">
    var differentialSearcher = window.exposed.differential,
        differentialFacetsData = ${empty jsonDifferentialGeneQueryFacets ? 'null' : jsonDifferentialGeneQueryFacets};

    differentialSearcher("atlasDifferentialFacetedSearchFacetsContainer", "atlasDifferentialFacetedSearchResultsContainer", differentialFacetsData);
    </c:if>
</script>


