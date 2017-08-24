<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="row">
    <div class="small-12 columns">
        <h3>Search results</h3>
    </div>

    <div class="small-12 columns">
        <tiles:insertTemplate template="./marker-genes-search-results/search.jsp"/>
    </div>

    <div class="small-12 columns">
        <div id="marker-genes-search-results-list"></div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/markerGenesSearchResults.bundle.js"></script>

<script>
  markerGenesSearchResults.render({
    atlasUrl: '${pageContext.request.contextPath}/',
    geneId: '${geneId}',
  }, 'marker-genes-search-results-list')
</script>
