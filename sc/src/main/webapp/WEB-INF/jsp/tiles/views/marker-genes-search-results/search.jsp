<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<form action="search" id="home-search-atlas-form">
    <!-- No need to enclose in row as the component already uses Foundation classes -->
    <div id="marker-genes-search-results-autocomplete"></div>

    <div class="row margin-top-large">
        <div class="small-12 columns">
            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>
                <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
        </div>
    </div>
</form>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/atlasAutocomplete.bundle.js"></script>

<script>
  atlasAutocomplete.render({
    atlasUrl: '${pageContext.request.contextPath}/',
    suggesterEndpoint: 'json/suggestions',
    initialValue: '${geneId}'
  }, 'marker-genes-search-results-autocomplete')
</script>