<%@ page contentType="text/html;charset=UTF-8" %>
<div id="experiments-summary-panel"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/homepageExperimentsSummaryPanel.bundle.js"></script>
<!-- Set to http://localhost:8080/gxa/ or http://localhost:8080/gxa_sc/ -- Remember the trailing slash! -->
<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    homepageExperimentsSummaryPanel.render(
      {
        host: '${pageContext.request.contextPath}/',
        resource: 'json/experiments-summary',
        responsiveCardsRowProps: {
          className: 'row expanded small-up-2 medium-up-3 large-up-6',
          cardContainerClassName: 'column',
          imageIconHeight: '3rem'
        },
        onComponentDidMount: function() {
          $('#experiments-summary-panel').foundation();
          $('#experiments-summary-panel').foundationExtendEBI();
        }
      },
      'experiments-summary-panel');
  });
</script>

