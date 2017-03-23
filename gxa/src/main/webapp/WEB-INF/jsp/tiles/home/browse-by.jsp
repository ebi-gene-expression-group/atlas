<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasBrowseBySpecies.bundle.js"></script>

<div class="callout browse-by" data-equalizer-watch>
    <ul class="tabs" data-tabs id="browse-by-tabs">
        <li class="tabs-title is-active"><a href="#by-species" aria-selected="true">By species</a></li>
        <li class="tabs-title"><a href="#by-animals">By animals</a></li>
        <li class="tabs-title"><a href="#by-plants">By plants</a></li>
        <li class="tabs-title"><a href="#by-fungi">By fungi</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="browse-by-tabs">

        <div class="tabs-panel is-active" id="by-species"></div>
        <div class="tabs-panel" id="by-animals"></div>
        <div class="tabs-panel" id="by-plants"></div>
        <div class="tabs-panel" id="by-fungi"></div>

    </div>
</div>


<script>
    expressionAtlasBrowseBySpecies.render({
        speciesInfoList : ${speciesList},
        mountNode: document.getElementById('by-species')
    });
    expressionAtlasBrowseBySpecies.render({
        speciesInfoList : ${animalsList},
        mountNode: document.getElementById('by-animals')
    });
    expressionAtlasBrowseBySpecies.render({
        speciesInfoList : ${plantsList},
        mountNode: document.getElementById('by-plants')
    });
    expressionAtlasBrowseBySpecies.render({
        speciesInfoList : ${fungiList},
        mountNode: document.getElementById('by-fungi')
    });
</script>