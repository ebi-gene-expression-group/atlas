<%--@elvariable id="accessKey" type="java.lang.String"--%>
<%--@elvariable id="type" type="atlas.model.ExperimentType"--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="protocol" value="${pageContext.request.scheme}://"/>
<c:set var="atlasHost" value="${pageContext.request.serverName}"/>
<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customized-bootstrap-3.3.5.css">

<div class="row column">
<%@ include file="includes/experiment-description.jsp" %>
</div>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/experimentPage.bundle.js"></script>

<div id="experiment-page" ></div>

<script type="text/javascript">
    experimentPage.render({
        atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
        pathToFolderWithBundledResources: '${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/',
        content: ${content},
        target: 'experiment-page'
    });
</script>
