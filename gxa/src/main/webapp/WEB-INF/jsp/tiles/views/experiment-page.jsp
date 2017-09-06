<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/alt-customized-bootstrap-3.3.5.css">

<%@ include file="experiment-page/experiment-description.jsp" %>

<div class="row expanded margin-top-large">
    <div class="small-12 columns">
        <div id="experiment-page" ></div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/experimentPage.bundle.js"></script>

<script type="text/javascript">
    experimentPage.render({
        atlasUrl: '${pageContext.request.contextPath}/',
        pathToFolderWithBundledResources: '${pageContext.request.contextPath}/resources/js-bundles/',
        content: ${content},
        target: 'experiment-page'
    });
</script>
