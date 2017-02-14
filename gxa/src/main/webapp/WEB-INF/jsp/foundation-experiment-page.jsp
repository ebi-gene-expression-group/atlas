<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">

<%@ include file="includes/experiment-header.jsp" %>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script
        src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasExperimentPage.bundle.js"></script>

<div id="experiment-page" style="display: none"></div>

<script type="text/javascript">
    expressionAtlasExperimentPage.render({
        atlasHost: "${not empty atlasHost? atlasHost: "https://www.ebi.ac.uk"}",
        content:${content},
        target: "experiment-page"
    });
</script>
