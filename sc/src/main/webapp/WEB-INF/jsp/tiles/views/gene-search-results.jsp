<%--@elvariable id="endpoint" type="String"--%>
<%--@elvariable id="geneQueryTerm" type="String"--%>
<%--@elvariable id="geneQueryCategory" type="String"--%>
<%--@elvariable id="species" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<style>
    #gene-search, #feedback-button {
        display: inline;
    }
    #feedback-button h1 {
        color: black;
    }
</style>

<div class="row column expanded">
    <div id="gene-search"></div>
</div>

<div id="feedback-button"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js-bundles/feedbackForm.bundle.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById("local-nav-search").className += ' active';
    geneSearch.render({
      atlasUrl: '${pageContext.request.contextPath}/',
      basename: '${pageContext.request.contextPath}',
    }, 'gene-search');
    feedbackForm.render({}, 'feedback-button');
  });
</script>
