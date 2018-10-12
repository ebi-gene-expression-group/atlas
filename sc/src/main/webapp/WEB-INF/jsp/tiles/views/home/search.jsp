<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<ul class="tabs" data-tabs id="search-tabs">
    <li class="tabs-title is-active"><a href="#search-atlas" aria-selected="true">Search</a></li>
</ul>

<div class="tabs-content" data-tabs-content="search-tabs">
    <div class="tabs-panel is-active " id="search-atlas" style="background-color: #e6e6e6;">
        <div id="search-form"></div>
    </div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearchForm.bundle.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
      geneSearchForm.render({
        atlasUrl: '${pageContext.request.contextPath}/',
        wrapperClassName: 'row expanded',
        actionEndpoint: 'search',

        autocompleteClassName: 'small-12 medium-8 columns',
        suggesterEndpoint: 'json/suggestions/gene_ids',

        enableSpeciesSelect: true,
        speciesEndpoint: 'json/suggestions/species',
        speciesSelectClassName: 'small-12 medium-4 columns',
      }, 'search-form')
    });
</script>