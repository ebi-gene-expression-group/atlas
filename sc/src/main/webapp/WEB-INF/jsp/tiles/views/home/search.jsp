<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<ul class="tabs" data-tabs id="search-tabs">
    <li class="tabs-title is-active"><a href="#search-atlas" aria-selected="true">Search</a></li>
</ul>

<div class="tabs-content" data-tabs-content="search-tabs">
    <div class="tabs-panel is-active " id="search-atlas" style="background-color: #e6e6e6;">
        <form method="get" action="${pageContext.request.contextPath}/search" id="home-search-atlas-form">
            <!-- No need to enclose in row as the component already uses Foundation classes -->
            <div id="search"></div>
            <%--<div class="small-12 columns small padding-bottom-medium">--%>
                <%--Examples: <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,--%>
                <%--<a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,--%>
                <%--<a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"O14777", "category":"uniprot"}]'>O14777 (UniProt)</a>,--%>
                <%--<a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"GO:0010468", "category":"go"}]'>GO:0010468 (regulation of gene expression)</a>--%>
            <%--</div>--%>


            <div class="row expanded margin-top-large">
                <div class="small-12 columns">
                    <button id="home-search-atlas-search-button" class="button" type="submit" value="Search">Search</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/atlasAutocomplete.bundle.js"></script>
<!-- Run when all Webpack bundles have been loaded (notice the defer attributes) -->
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        atlasAutocomplete.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            suggesterEndpoint: 'json/suggestions',
            initialValue: '${geneId}',
            enableSpeciesFilter: true,
            wrapperClassName: 'row expanded',
            autocompleteClassName: 'small-12 medium-8 columns',
            speciesFilterClassName: 'small-12 medium-4 columns'
        }, 'search')
    });
</script>