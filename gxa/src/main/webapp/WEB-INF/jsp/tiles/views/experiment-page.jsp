<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="experiment-page/experiment-description.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/alt-customized-bootstrap-3.3.5.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/popup-feedback.css" type="text/css" media="all">

<style>
    #experiment-page, #feedback-button {
        display: inline;
    }
    #feedback-button h1 {
        color: black;
    }
</style>

<div class="row expanded margin-top-large">
    <div class="small-12 columns">
        <div id="experiment-page" ></div>
    </div>
</div>


<div id="feedback-button">
</div>


<script defer src="${pageContext.request.contextPath}/resources/js-bundles/experimentPage.bundle.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js-bundles/feedbackForm.bundle.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    experimentPage.render({
      atlasUrl: '${pageContext.request.contextPath}/',
      content: ${content},
      target: 'experiment-page'
    });
    feedbackForm.render({}, 'feedback-button');
  });
</script>

