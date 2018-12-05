<%--@elvariable id="hcaExperimentsCount" type="int"--%>
<%--@elvariable id="humanExperimentsCount" type="int"--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div id="data" class="callout" data-equalizer-watch>
    <h3><span class="icon icon-functional" data-icon="A"></span> Data</h3>
    <hr>

    <p> Single Cell Expression Atlas provides access to the analysis results of
        <fmt:formatNumber pattern="#,##0" value="${hcaExperimentsCount}"/> data
        ${hcaExperimentsCount > 1 ? "sets" : "set"} from the Human Cell Atlas
        and <fmt:formatNumber pattern="#,##0" value="${humanExperimentsCount}"/> data
        ${humanExperimentsCount > 1 ? "sets" : "set"} in <i>Homo sapiens</i>
        from other studies.
    </p>
    <p>
        All studies have been re-analysed using our standardised pipeline
        (<a href="https://github.com/nunofonseca/irap">iRAP</a>). Pipelines and web services are being developed by the
        Gene Expression Team at EMBL-EBI, funded by <a href="https://wellcome.ac.uk/">Wellcome</a> and the
        <a href="https://www.chanzuckerberg.com/">Chan Zuckerberg Initiative</a>.
    </p>
    <div class="margin-top-xlarge" id="hca-card-container"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/atlasHomepageCard.bundle.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    atlasHomepageCard.renderHcaLandingPageContainer({
      host: '${pageContext.request.contextPath}/',
      resource: 'json/hca'
    }, 'hca-card-container');
  });
</script>
