<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customized-bootstrap-3.3.5.css">

<script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>

<%@ include file="includes/experiment-description.jsp" %>

<script src="${pathToFolderWithBundledResources}vendorCommons.bundle.js"></script>
<script src="${pathToFolderWithBundledResources}experimentPage.bundle.js"></script>

<div id="experiment-page" ></div>

<script type="text/javascript">
    experimentPage.render({
        atlasHost: "${not empty atlasHost? atlasHost: "https://www.ebi.ac.uk"}",
        pathToFolderWithBundledResources: "${pathToFolderWithBundledResources}",
        content:${content},
        target: "experiment-page"
    });
</script>
