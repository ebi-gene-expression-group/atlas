<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="masthead row masthead-atlas">

    <div class="row atlas-row">
        <!-- local-title -->
        <div class="columns small-8" id="local-title">
            <h1><a href="https://www.ebi.ac.uk/gxa/home" title="Back to Expression Atlas homepage"></a>
                <div class="atlas-logo"></div>Expression Atlas
                <%--<img src="${pageContext.request.contextPath}/resources/images/logos/logo_atlas_transparent.png" alt="Expression Atlas logo" >--%>
            </h1>
            <h4 class="show-for-large">Gene expression across species and biological conditions</h4>
        </div>
        <!-- /local-title -->

        <!-- local-search -->
        <div class="hide-for-small-only columns small-4">
            <!-- Dynamic magnifying glass search -->
            <%--<div class="local-site-search">--%>
            <%--<form class="tool-search" id="local-search" name="local-search" action="/search.atlas" method="GET">--%>
            <%--<input type="text" maxlength="255" value="" class="search-box">--%>
            <%--<a data-tooltip href="/search.atlas" data-icon="1" class="button icon icon-functional" title="Search ---%>
            <%--do a free text search by keyword"></a>--%>
            <%--</form>--%>
            <%--</div>--%>

            <!-- EBI Framework standard search -->
            <!-- if you do not have a local-search, delete the following form -->
            <form id="local-search" name="local-search" action="[search-action]" method="post">
                <h4>Search this project</h4>
                <fieldset>
                    <div class="input-group">
                        <input type="text" name="first" id="local-searchbox">
                        <div class="input-group-button">
                            <input type="submit" name="submit" value="1" class="button icon icon-functional">
                        </div>
                    </div>
                    <small class="show-for-large">
                        <!-- If your search is more complex than just a keyword search, you can link to an Advanced Search -->
                        <span class="adv"><a href="../search" id="adv-search" title="Advanced">Advanced</a></span>
                        |
                        <!-- Include some example searchterms - keep them short and few. -->
                        <a href="#" data-toggle="search-example-dropdown">Examples</a>
                    </small>
                    <div class="dropdown-pane" id="search-example-dropdown" data-dropdown data-options="hoverPane:true;" data-hover="true">
                        <ul>
                            <li><a href="[search-url-1]">[search-text-1]</a></li>
                            <li><a href="[search-url-1]">[search-text-2]</a></li>
                            <li><a href="[search-url-1]">[search-text-3]</a></li>
                        </ul>
                    </div>
                    <!-- if you're not using the JS addons
                    Examples:
                    <ul>
                      <li><a href="[search-url-1]">[search-text-1]</a></li>
                      <li><a href="[search-url-1]">[search-text-2]</a></li>
                      <li><a href="[search-url-1]">[search-text-3]</a></li>
                    </ul>
                    -->
                </fieldset>
            </form>
        </div>
        <!-- /local-search -->
    </div>


    <!-- local-nav -->
    <nav>
        <ul class="dropdown menu float-left" data-description="navigational">
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