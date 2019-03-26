<%@ page contentType="text/html;charset=UTF-8" %>
<div id="species-summary-panel"></div>

<div class="row expanded column text-center margin-top-medium">
  <a class="button primary" href="${pageContext.request.contextPath}/experiments">Show experiments of all species</a>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/homepageSpeciesSummaryPanel.bundle.js"></script>
<!-- Set to http://localhost:8080/gxa/ or http://localhost:8080/gxa_sc/ -- Remember the trailing slash! -->
<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    homepageSpeciesSummaryPanel.render(
      {
        host: '${pageContext.request.contextPath}/',
        resource: 'json/species-summary',
        responsiveCardsRowProps: {
          className: 'row expanded small-up-2 medium-up-3 large-up-6',
          cardContainerClassName: 'column'
        },
        onComponentDidMount: function() {
          $('#species-summary-panel').foundation();
          $('#species-summary-panel').foundationExtendEBI();
        }
      },
      'species-summary-panel');
  });
</script>

