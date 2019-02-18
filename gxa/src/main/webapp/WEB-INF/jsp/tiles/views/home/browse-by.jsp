<%@ page contentType="text/html;charset=UTF-8" %>
<div id="popular-species"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/homePagePanel.bundle.js"></script>
<!-- Set to http://localhost:8080/gxa/ or http://localhost:8080/gxa_sc/ -- Remember the trailing slash! -->
<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    homePagePanel.render({
      host: '${pageContext.request.contextPath}/',
      speciesResources: {
        Species: 'json/experiments/popular-species',
        Animals: 'json/experiments/popular-species?kingdom=animals',
        Plants: 'json/experiments/popular-species?kingdom=plants',
        Fungi: 'json/experiments/popular-species?kingdom=fungi',
        Protists: 'json/experiments/popular-species?kingdom=protists'
      }
    }, 'popular-species')

  });
</script>
