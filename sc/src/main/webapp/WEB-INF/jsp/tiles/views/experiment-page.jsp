<%@ page contentType="text/html;charset=UTF-8" %>

<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">--%>

<%@ include file="experiment-page/experiment-description.jsp" %>

<div class="row margin-top-large">
    <div class="small-12 columns">
        <div id="experiment-page"></div>
    </div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/experimentPage.bundle.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        experimentPage.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            resourcesUrl: '${pageContext.request.contextPath}/resources/js-bundles/',
            content: ${content},
        }, 'experiment-page');
    });
</script>
