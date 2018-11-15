<%--
  Created by IntelliJ IDEA.
  User: lingyun
  Date: 25/10/2018
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="media-object">
    <div class="media-object-section top hide-for-small-only">
        <div class="thumbnail">
            <img  alt="Human Cell Atlas" style="max-width: 275px"
                  src="${pageContext.request.contextPath}/resources/images/logos/human_cell_atlas.png"/>
        </div>
    </div>
    <div class="media-object-section top">
        <p>
            "Single cell Expression Atlas provides access to the analysis results of X data sets
            from the Human Cell Atlas and Y data sets in Homo Sapiens from other studies.
        </p>
        <p>
            All studies have been re-analysed using our standardised pipeline (iRAP).
            Pipelines and web-service are being developed by the Gene Expression Team at EMBL-EBI,
            funded by the Wellcome Trust and the Chan Zuckerberg Initiative. "
        </p>
    </div>
</div>


<div class="row column margin-top-large expanded">
    <div id="hca-card-container"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/atlasHomepageCard.bundle.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        atlasHomepageCard.renderHcaLandingPageContainer({
            host: 'http://localhost:8080/gxa/sc/',
            resource: 'json/experiments/landingpage/E-EHCA'
        }, 'hca-card-container');
    });
</script>

