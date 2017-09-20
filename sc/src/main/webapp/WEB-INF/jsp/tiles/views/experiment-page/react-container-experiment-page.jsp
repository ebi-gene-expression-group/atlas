<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="https://www.ebi.ac.uk/gxa/resources/css/foundation/atlas.css" type="text/css" media="all">

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/experimentPage.bundle.js"></script>

<div class="row">
    <div id="container"></div>
</div>

<script>
    experimentPage.render({
      atlasUrl: "${pageContext.request.contextPath}/",
      experimentAccession: "${experimentAccession}",
      suggesterEndpoint:"json/suggestions",
      availableClusters: ['2','3','4','5','6','7','8','9','10'],
      resourcesUrl: "${pageContext.request.contextPath}/resources/js-bundles/"
    }, 'container');
</script>