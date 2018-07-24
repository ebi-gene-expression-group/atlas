<%--@elvariable id="endpoint" type="String"--%>
<%--@elvariable id="geneQueryTerm" type="String"--%>
<%--@elvariable id="geneQueryCategory" type="String"--%>
<%--@elvariable id="species" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="row column expanded">
    <div class="row column expanded">
        <h3>Search results</h3>
    </div>

    <div class="row column expanded margin-bottom-large">
        <div id="search-form"></div>
    </div>

    <div id="search-results-list"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>
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
      defaultSpecies: '${species}',
      defaultValue: {
        term: '${geneQueryTerm}',
        category: '${geneQueryCategory}',
      }
    }, 'search-form');

    geneSearch.render({
      atlasUrl: '${pageContext.request.contextPath}/',
      resource: '${endpoint}',
      noResultsMessage: '${geneQueryTerm} is not expressed in any experiment. Try searching for a different gene.',
      resultsMessage: '${geneQueryTerm} is expressed in:'
    }, 'search-results-list');
  });
</script>
