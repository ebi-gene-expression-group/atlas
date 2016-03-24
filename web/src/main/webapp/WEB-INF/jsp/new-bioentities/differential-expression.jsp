<%--@elvariable id="hasDifferentialResults" type="boolean"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="queryType" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="grid_6 alpha" id="gxaDifferentialFacetsContainerDiv"></div>
<div class="grid_18 omega" id="gxaDifferentialResultsContainerDiv">
    <img src="/gxa/resources/images/loading.gif"/>
</div>

<script>
    var selectedSpecies = "${hasSelectedSpecies ? selectedSpecies : ''}";

    <c:if test="${hasDifferentialResults}">
    facetedSearch.differential({
        facetsContainer: "gxaDifferentialFacetsContainerDiv",
        resultsContainer: "gxaDifferentialResultsContainerDiv",
        identifier: "${identifier}",
        queryType: "${queryType}",
        species: selectedSpecies
    });
    </c:if>
</script>


