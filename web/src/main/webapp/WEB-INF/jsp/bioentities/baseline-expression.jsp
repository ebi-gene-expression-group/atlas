<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="queryType" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="grid_4 alpha" id="gxaBaselineFacetsContainerDiv"></div>
<div class="grid_20 omega" id="gxaBaselineResultsContainerDiv"></div>

<script>
    <c:if test="${hasBaselineResults}">

    var baselineFacetsData = ${jsonFacets};

    facetedSearch.baseline({
        facetsContainer: "gxaBaselineFacetsContainerDiv",
        resultsContainer: "gxaBaselineResultsContainerDiv",
        showAnatomogramsInput: "showAnatomogramsCheckbox",
        species: "${species}",
        facetsTreeData: baselineFacetsData,
        identifier: "${identifier}"
    });
    </c:if>
</script>


