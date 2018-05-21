<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<form action="search" id="home-search-atlas-form">
    <!-- No need to enclose in row as the component already uses Foundation classes -->
    <div id="gene-search-results-autocomplete"></div>

    <div class="margin-top-large">
        <div>
            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>
                <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
        </div>
    </div>
</form>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/atlasAutocomplete.bundle.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", function(event) {
        atlasAutocomplete.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            suggesterEndpoint: 'json/suggestions',
            initialValue: '${geneId}'
      }, 'gene-search-results-autocomplete')
  });

</script>