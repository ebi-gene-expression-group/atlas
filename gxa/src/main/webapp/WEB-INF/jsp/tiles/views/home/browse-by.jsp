<%@ page contentType="text/html;charset=UTF-8" %>

    <ul class="tabs" data-tabs id="browse-by-tabs">
        <li class="tabs-title is-active"><a href="#by-species" aria-selected="true">By species</a></li>
        <li class="tabs-title"><a href="#by-animals">Animals</a></li>
        <li class="tabs-title"><a href="#by-plants">Plants</a></li>
        <li class="tabs-title"><a href="#by-fungi">Fungi</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="browse-by-tabs">
        <div class="tabs-panel is-active" id="by-species"></div>
        <div class="tabs-panel" id="by-animals"></div>
        <div class="tabs-panel" id="by-plants"></div>
        <div class="tabs-panel" id="by-fungi"></div>
    </div>



<script defer src="${pageContext.request.contextPath}/resources/js-bundles/expressionAtlasBrowseBySpecies.bundle.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function(event) {
        expressionAtlasBrowseBySpecies.render(
          {
            atlasUrl: '${pageContext.request.contextPath}/',
            speciesInfoList: ${speciesList}
          },
          'by-species'
        );
        expressionAtlasBrowseBySpecies.render(
          {
            atlasUrl: '${pageContext.request.contextPath}/',
            speciesInfoList : ${animalsList}
          }, 'by-animals'
        );
        expressionAtlasBrowseBySpecies.render(
          {
            atlasUrl: '${pageContext.request.contextPath}/',
            speciesInfoList : ${plantsList}
          },
          'by-plants'
        );
        expressionAtlasBrowseBySpecies.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            speciesInfoList : ${fungiList}
          },
          'by-fungi'
        );
    });
</script>