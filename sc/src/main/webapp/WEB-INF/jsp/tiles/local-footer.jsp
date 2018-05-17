<%@ page contentType="text/html;charset=UTF-8" %>

<footer id="local-footer" class="local-footer" role="local-footer">
    <div id="relationships" class="row">
        <!-- https://www.ebi.ac.uk/style-lab/websites/patterns/banner-elixir.html -->
        <div class="small-10 columns">
            <div id="elixir-banner"
                 data-color="none"
                 data-use-cdr-logo="false"
                 data-name="This service"
                 data-description="Expression Atlas is an ELIXIR database service"
                 data-more-information-link="https://www.elixir-europe.org/about-us/who-we-are/nodes/embl-ebi"
                 data-use-basic-styles="false">
            </div>
        </div>
        <div class="small-2 columns text-right padding-top-medium">
            <a href="https://wellcome.ac.uk/" class="clear">
                <img src="${pageContext.request.contextPath}/resources/images/logos/wellcome_trust_logo_black.png"
                     alt="Wellcome Trust logo" style="height: 4em">
            </a>
        </div>
    </div>
</footer>

<script defer="defer" src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/elixirBanner.js"></script>
