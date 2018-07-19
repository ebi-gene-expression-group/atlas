<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="endpoint" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="row column expanded">
    <div class="row column expanded">
        <h3>Search results</h3>
    </div>

    <div class="row column expanded margin-bottom-large">
        <tiles:insertTemplate template="./gene-search-results/search.jsp"/>
    </div>

    <div id="search-results-list"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        geneSearch.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            resource: '${endpoint}',
            noResultsMessage: '${geneId} is not expressed in any experiment. Try searching for a different gene.',
            resultsMessage: '${geneId} is expressed in:'
        }, 'search-results-list')
    });
</script>
