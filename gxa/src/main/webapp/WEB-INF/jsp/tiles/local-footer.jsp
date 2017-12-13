<%@ page contentType="text/html;charset=UTF-8" %>

<footer id="local-footer" class="local-footer row expanded">
    <div id="elixir-banner"
         data-color="grey"
         data-name="This service"
         data-description="Gene Expression Atlas is an Elixir Core Data Resource"
         data-more-information-link="https://www.elixir-europe.org/about-us/who-we-are/nodes/embl-ebi"
         data-use-basic-styles="true"></div>
    <script defer="defer" src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/elixirBanner.js"></script>

    <div class="small-12 small-centered medium-8 large-6 columns">
        <div class="row small-up-1 medium-up-3">
            <div class="column column-block margin-top-large margin-bottom-large text-center">
                <h4 style="color:white">Experiments</h4>
                <a href="${pageContext.request.contextPath}/experiments?experimentType=baseline" target="_blank">Baseline experiments</a><br/>
                <a href="${pageContext.request.contextPath}/experiments?experimentType=differential" target="_blank">Differential experiments</a>
            </div>

            <div class="column column-block margin-top-large margin-bottom-large text-center">
                <h4 style="color:white">Atlas</h4>
                <a href="${pageContext.request.contextPath}/release-notes.html" target="_blank">Release notes</a><br/>
                <a href="${pageContext.request.contextPath}/download.html" target="_blank">Download</a><br/>
                <a href="${pageContext.request.contextPath}/help/index.html" target="_blank">Help</a><br/>
                <a href="${pageContext.request.contextPath}/FAQ.html" target="_blank">FAQ</a><br/>
                <a href="${pageContext.request.contextPath}/about.html" target="_blank">About Atlas</a>
            </div>

            <div class="column column-block margin-top-large margin-bottom-large text-center">
                <h4 style="color:white">Follow us</h4>
                <a href="https://twitter.com/ExpressionAtlas" target="_blank">Twitter</a><br/>
                <a href="https://www.ebi.ac.uk/support/gxa" target="_blank">Feedback</a><br/>
                <a href="mailto:arrayexpress-atlas@ebi.ac.uk" target="_blank">Contact</a>
            </div>
        </div>
    </div>
</footer>
