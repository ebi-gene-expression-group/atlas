<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>

<%@ include file="includes/experiment-header.jsp" %>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script
        src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/experimentPage.bundle.js"></script>

<div id="experiment-page" ></div>

<script type="text/javascript">
    experimentPage.render({
        atlasHost: "${not empty atlasHost? atlasHost: "https://www.ebi.ac.uk"}",
        content:${content},
        target: "experiment-page"
    });
</script>
