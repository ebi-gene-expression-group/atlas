<%--@elvariable id="hcaExperimentsCount" type="int"--%>
<%--@elvariable id="humanExperimentsCount" type="int"--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row column margin-top-large expanded">
    <div class="media-object">
        <div class="media-object-section top hide-for-small-only">
            <div class="thumbnail">
                <img  alt="Human Cell Atlas" style="max-width: 275px"
                      src="${pageContext.request.contextPath}/resources/images/logos/human_cell_atlas.png"/>
            </div>
        </div>
        <div class="media-object-section top">
            <p>
                Single cell Expression Atlas provides access to the analysis results of
                <fmt:formatNumber pattern="#,##0" value="${hcaExperimentsCount}"/> data sets from the Human Cell Atlas
                and <fmt:formatNumber pattern="#,##0" value="${humanExperimentsCount}"/> data sets in <i>Homo
                sapiens</i> from other studies.
            </p>
            <p>
                All studies have been re-analysed using our standardised pipeline
                (<a href="https://github.com/nunofonseca/irap">iRAP</a>). Pipelines and web services are
                being developed by the Gene Expression Team at EMBL-EBI, funded by the
                <a href="https://wellcome.ac.uk/">Wellcome Trust</a> and the
                <a href="https://www.chanzuckerberg.com/">Chan Zuckerberg Initiative</a>.
            </p>
        </div>
    </div>
</div>


<div class="row column margin-top-large expanded">
    <div id="hca-card-container"></div>
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

