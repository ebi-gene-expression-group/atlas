<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<form action="search" id="home-search-atlas-form">
    <h3>Marker genes search</h3>

    <!-- No need to enclose in row as the component already uses Foundation classes -->
    <div id="target"></div>

    <div class="row">
        <div class="small-12 columns small">
            Examples: <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,
            <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,
            <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"lung"}]'>lung</a>,
            <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"leaf"}]'>leaf</a>,
            <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"valproic acid"}]'>valproic acid</a>,
            <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"cancer"}]'>cancer</a></label>
        </div>
    </div>

    <div class="row margin-top-large">
        <div class="small-12 columns">
            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>
                <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
        </div>
    </div>
</form>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/atlasAutocomplete.bundle.js"></script>

<script>
  atlasAutocomplete.render({
    atlasUrl: '${pageContext.request.contextPath}/',
    suggesterEndpoint: 'json/suggestions',
    initialValue: '${geneId}'
  }, 'target')
</script>