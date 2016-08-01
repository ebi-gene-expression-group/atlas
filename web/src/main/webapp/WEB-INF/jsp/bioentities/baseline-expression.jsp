<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>

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
        facetsTreeData: baselineFacetsData,
        identifier: "${geneQuery}", //DEPRECATED and added so our bundles are backwards compatible - see this commit for the change in  baselineRouter.js
        geneQuery: "${geneQuery}",
        conditionQuery: "${conditionQuery}",
        species: "${species}"
    });
    </c:if>
</script>


