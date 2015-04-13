<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<link type="text/css" rel="stylesheet" href="/gxa/resources/css/facets.css" />

<%@ include file="../includes/react.jsp" %>

<section class="grid_23 extra-padding">
    <%@ include file="includes/search-form.jsp" %>
</section>

<c:if test="${not empty searchDescription}" >
    <section class="grid_23 extra-padding">
        <section class="grid_12 alpha extra-padding">
            <c:if test="${hasBaselineResults}">
                <a href="${pageContext.request.contextPath}/search?geneQuery=${pageContext.request.getParameter("geneQuery")}">Baseline</a>
            </c:if>
            <c:if test="${!hasBaselineResults}">
                Baseline (no results)
            </c:if>
        </section>

        <section class="grid_11 extra-padding">
            <c:if test="${hasDifferentialResults}">
                <div style="font-weight: bold">
                    Differential
                </div>
            </c:if>
            <c:if test="${!hasDifferentialResults}">
                Differential (no results)
            </c:if>
        </section>
    </section>

    <section class="grid_17 alpha extra-padding">
        <h5 class="strapline">
            Differential results for <span class="searchterm">${searchDescription}</span>
        </h5>

    </section>
    <h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>
</c:if>


<section class="grid_23 extra-padding">
    <c:if test="${!hasDifferentialResults}">
        No differential results
    </c:if>

    <div id="atlasAnalyticsSearchFacetContainer"></div>
    <div id="atlasAnalyticsSearchDiffResultsContainer"></div>
</section>


<script src="${pageContext.request.contextPath}/resources/js/lib/query-string.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/facets.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/differentialResults.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/differentialRouter.js"></script>

<c:if test="${hasDifferentialResults}">
    <script>

        var facetsData = ${empty jsonDifferentialGeneQueryFacets ? 'null' : jsonDifferentialGeneQueryFacets};
        var diffResultsData = ${empty jsonDifferentialGeneQueryResults ? 'null': jsonDifferentialGeneQueryResults};

        (function (DifferentialRouter, facetsData, diffResultsData) {

            if (facetsData) {
                DifferentialRouter(
                        document.getElementById('atlasAnalyticsSearchFacetContainer'),
                        document.getElementById('atlasAnalyticsSearchDiffResultsContainer'),
                        facetsData, diffResultsData);
            }

        })(DifferentialRouter, facetsData, diffResultsData);

    </script>
</c:if>
