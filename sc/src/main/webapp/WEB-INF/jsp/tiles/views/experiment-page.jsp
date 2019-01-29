<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="experiment-page/experiment-description.jsp" %>

<div class="row column margin-top-large expanded">
    <div id="experiment-page"></div>
</div>

<div id="feedback-button"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/experimentPage.bundle.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js-bundles/feedbackForm.bundle.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        experimentPage.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            resourcesUrl: '${pageContext.request.contextPath}/resources/js-bundles/',
            content: ${content},
        }, 'experiment-page');
        feedbackForm.render({}, 'feedback-button');
    });
</script>
