<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<form action="search" id="home-search-atlas-form">
    <h3>Gene search</h3>

    <!-- No need to enclose in row as the component already uses Foundation classes -->
    <div id="target"></div>

    <div class="row column small padding-bottom-medium">
        Examples: <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,
        <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,
        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"lung"}]'>lung</a>,
        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"leaf"}]'>leaf</a>,
        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"valproic acid"}]'>valproic acid</a>,
        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"cancer"}]'>cancer</a></label>
    </div>

    <div class="row column button-group">
        <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>
    </div>
</form>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/atlasAutocomplete.bundle.js"></script>

<!-- Run when all Webpack bundles have been loaded (notice the defer attributes) -->
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        atlasAutocomplete.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            suggesterEndpoint: 'json/suggestions',
            initialValue: '${geneId}',
            enableSpeciesFilter: true,
            wrapperClassName: 'row',
            autocompleteClassName: 'small-12 medium-8 columns',
            speciesFilterClassName: 'small-12 medium-4 columns'
        }, 'target')
    });
</script>