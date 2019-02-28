<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="https://www.ebi.ac.uk/gxa/resources/css/foundation/atlas.css" type="text/css" media="all">
<div id="panels"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/homePagePanel.bundle.js"></script>
<!-- Set to http://localhost:8080/gxa/ or http://localhost:8080/gxa_sc/ -- Remember the trailing slash! -->
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
      homePagePanel.render({
        host: '${pageContext.request.contextPath}/',
        experimentResources: {
          'Latest experiments': 'json/experiments/latestExperiments'
        },
        speciesResources: {
          Species: 'json/experiments/popular-species',
          Animals: 'json/experiments/popular-species?kingdom=animals',
          Protists: 'json/experiments/popular-species?kingdom=protists'
        }
      }, 'panels')
    });
</script>
