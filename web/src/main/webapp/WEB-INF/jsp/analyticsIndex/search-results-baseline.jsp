<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<link type="text/css" rel="stylesheet" href="/gxa/resources/css/facets.css" />


<section class="grid_23 gxaExtraPadding">
    <%@ include file="includes/search-form.jsp" %>
</section>

<c:if test="${not empty searchDescription}" >
    <section class="grid_23 gxaExtraPadding">
        <section class="grid_12 alpha gxaExtraPadding">
            <div style="font-weight: bold">
            <c:if test="${hasBaselineResults}">
                Baseline
            </c:if>
            <c:if test="${!hasBaselineResults}">
                Baseline (no results)
            </c:if>
            </div>
        </section>

        <section class="grid_11 gxaExtraPadding">
            <c:if test="${hasDifferentialResults}">
                <a href="${pageContext.request.contextPath}/search/differential?geneQuery=${geneQuery.asUrlQueryParameter()}">Differential</a>
            </c:if>
            <c:if test="${!hasDifferentialResults}">
                Differential (no results)
            </c:if>
        </section>
    </section>

    <section class="grid_17 alpha gxaExtraPadding">
        <h5 class="strapline">
            Baseline results for <span class="searchterm">${searchDescription}</span>
        </h5>
    </section>
    <h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>
</c:if>


<section class="grid_23 gxaExtraPadding">
    <c:if test="${!hasBaselineResults}">
        No baseline results
    </c:if>

    <div id="atlasAnalyticsSearchFacetContainer"></div>
    <div id="atlasAnalyticsSearchHeatmapsContainer"></div>
</section>

<!-- /search_facet -->

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/faceted-search-results.bundle.js"></script>

<script>

    var facetsData = ${empty jsonFacets ? 'null' : jsonFacets};

    var baseline_page_js = window.exposed;

    baseline_page_js.baseline(facetsData, 'atlasAnalyticsSearchFacetContainer', 'atlasAnalyticsSearchHeatmapsContainer');

</script>
