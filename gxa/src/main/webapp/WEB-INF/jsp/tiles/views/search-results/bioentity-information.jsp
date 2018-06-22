<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row expanded">
    <div class="small-12 columns">
        <div id="bioentityInformationTab"></div>
    </div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/expressionAtlasBioentityInformation.bundle.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        expressionAtlasBioentityInformation.render({
            atlasUrl: "${pageContext.request.contextPath}/",
            geneId: "${identifier}"
        }, 'bioentityInformationTab')
    })
</script>
