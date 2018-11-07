<%--@elvariable id="endpoint" type="String"--%>
<%--@elvariable id="geneQueryTerm" type="String"--%>
<%--@elvariable id="geneQueryCategory" type="String"--%>
<%--@elvariable id="species" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row column expanded">
    <div id="gene-search"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>
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
      basename: '${pageContext.request.contextPath}',
    }, 'gene-search');
  });
</script>
