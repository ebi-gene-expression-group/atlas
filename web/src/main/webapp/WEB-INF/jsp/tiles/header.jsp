<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="masthead row">

    <div class="row">
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
            <li><a href="/gxa/home">Home</a></li>
            <li><a href="/gxa/release-notes.html">Release notes</a></li>
            <li><a href="/gxa/FAQ.html">Browse experiments</a></li>
            <li><a href="/gxa/download.html">Download</a></li>
            <li><a href="/gxa/help/index.html">Help</a></li>
            <li><a href="/gxa/licence.html">Licence</a></li>
            <li><a href="/gxa/about.html">About</a></li>
        </ul>

        <!-- SP adding class hide-for-small-only as the right hand side takes a lot of space for small + remove text for medium-->
        <ul class="show-for-large dropdown menu float-right" data-dropdown-menu data-description="functional">
            <li class="functional has-submenu is-down-arrow"><a href="#" class="hide-for-small-only"><i class="icon icon-generic" data-icon="d"></i> Share this</a>
                <ul class="menu js">
                    <li >
                        <a href="#">Hope we don't talk of Facebook</a>
                    </li>
                    <li>
                        <a href='#'> Twitter</a>
                        <ul class='menu'>
                            <li><a href='#'>@EMG-metagenomics</a></li>
                            <li><a href='#'>@Rob-FInn</a></li>
                            <li>
                                <a href='#'> Item 1 sub</a>
                                <ul class='menu'>
                                    <li><a href='#'>Item 1 subA</a></li>
                                    <li><a href='#'>Item 1 subB</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href='#'> Item 1 sub</a>
                                <ul class='menu'>
                                    <li><a href='#'>Item 1 subA</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li><a href="#">Reddit</a></li>
                </ul>
            </li><!-- SP Missing code -->
        </ul>
    </nav>
    <!-- /local-nav -->

</div>