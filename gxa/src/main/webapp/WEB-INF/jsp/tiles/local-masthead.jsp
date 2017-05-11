<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="masthead row">

    <div class="small-8 columns media-object">
        <div class="show-for-medium media-object-section middle">
            <a href="${pageContext.request.contextPath}/home/" title="Back to Expression Atlas homepage" class="clear">
                <img src="${pageContext.request.contextPath}/resources/images/foundation/logos/logo_atlas_transparent.png" alt="Expression Atlas logo">
            </a>
        </div>
        <div class="media-object-section middle">
            <h1><a href="${pageContext.request.contextPath}/home/" title="Back to Expression Atlas homepage" class="clear">Expression Atlas</a></h1>
            <h4 class="show-for-large"><a href="${pageContext.request.contextPath}/home/" title="Back to Expression Atlas homepage" class="clear">Gene expression across species and biological conditions</a></h4>
        </div>
    </div>

    <!-- local-search -->
    <div class="hide-for-small-only small-4 columns">
        <!-- EBI Framework standard search -->
        <form id="local-search" name="local-search" method="get" action="${pageContext.request.contextPath}/search">
            <h4>Search this project</h4>
            <fieldset>
                <div class="input-group" style="margin-bottom: 0">
                    <input type="text" name="geneQuery" id="local-searchbox">
                    <div class="input-group-button">
                        <input id="submit-searchbox" type="submit" value="1" class="button icon icon-functional">
                    </div>
                </div>
                <small class="show-for-large">
                    <!-- Include some example searchterms - keep them short and few. -->
                    <span>Examples: <a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22ASPM%22%7D%5D">ASPM</a>,
                            <a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22apoptosis%22%7D%5D">Apoptosis</a>,
                            <a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22ENSMUSG00000021789%22%7D%5D">ENSMUSG00000021789</a>,
                            <a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22zinc%20finger%22%7D%5D">zinc finger</a></span>
                </small>

            </fieldset>
        </form>
    </div>
    <!-- /local-search -->


    <!-- local-nav -->
    <nav>
        <ul class="dropdown menu" data-description="navigational">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/download.html">Download</a></li>
            <li><a href="${pageContext.request.contextPath}/release-notes.html">Release notes</a></li>
            <li><a href="${pageContext.request.contextPath}/FAQ.html">FAQ</a></li>
            <li><a href="${pageContext.request.contextPath}/help/index.html">Help</a></li>
            <li><a href="${pageContext.request.contextPath}/licence.html">Licence</a></li>
            <li><a href="${pageContext.request.contextPath}/about.html">About</a></li>
        </ul>
    </nav>
    <!-- /local-nav -->

</div>