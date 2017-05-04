<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customized-bootstrap-3.3.5.css">

<br/>
<%@ include file="includes/experiment-description.jsp" %>
<br/>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/experimentPage.bundle.js"></script>

<div id="experiment-page" ></div>

<script type="text/javascript">
    experimentPage.render({
        atlasUrl: '${pageContext.request.contextPath}/',
        pathToFolderWithBundledResources: '${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/',
        content: ${content},
        target: 'experiment-page'
    });
</script>
