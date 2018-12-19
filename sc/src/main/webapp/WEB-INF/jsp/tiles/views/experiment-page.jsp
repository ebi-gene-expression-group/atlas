<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="experiment-page/experiment-description.jsp" %>

<link rel="stylesheet" href="http://localhost:8080/gxa/sc/resources/css/popup-feedback.css" type="text/css" media="all">
<link rel="stylesheet" href="http://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.3/fonts.css" type="text/css" media="all">

<style>
    #experiment-page, #feedback-button {
        display: inline;
    }
    #feedback-button h1 {
        color: black;
    }
</style>

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
