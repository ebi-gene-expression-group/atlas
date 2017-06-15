<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="masthead row">
    <!-- local-title -->
    <div class="columns small-8 medium-10 anim" id="local-title">
        <h1>
            <a href="${applicationProperties.buildServerURL(pageContext.request)}" title="Back to Expression Atlas homepage">
                <img src="${pageContext.request.contextPath}/resources/images/logos/logo_atlas_transparent.png" class="" alt="atlas logo" >
            </a>
            Single cell Expression Atlas
        </h1>
        <h4 class="hide-for-small-only">Single-cell gene expression across species</h4>
    </div>

    <div class="columns small-4 medium-2" id="slide-menu">
        <!-- Search form-->
        <div class="local-buttons">
            <div class="local-site-search">
                <form class="tool-search" id="local-search" name="local-search" action="${pageContext.request.contextPath}/search" method="GET">
                    <input type="text" maxlength="255" value="" class="search-box">
                    <a data-tooltip href="${pageContext.request.contextPath}/search" data-icon="1" class="button icon icon-functional" title="Search -
                    do a free text search by keyword"></a>
                </form>
            </div>

        </div>
        <!-- END Search form -->
    </div>

    <div id="search-local-dropdown" class="dropdown-pane left" data-dropdown data-options="closeOnClick:true;" data-animate="fade-in fade-out" >
        <form id="local-search-dropdown" name="local-search" action="${pageContext.request.contextPath}/search" method="GET">
            <fieldset>
                <div class="input-group">
                    <input type="text" name="query" id="local-searchbox" class="input-group-field" placeholder="Search atlas">
                    <div class="input-group-button">
                        <input type="submit" name="submit" value="Search" class="button">
                        <input type="hidden" name="db" value="allatlas" checked="checked">
                        <input type="hidden" name="requestFrom" value="local-masthead" checked="checked">
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <!-- /local-title -->

    <nav id="menu">
        <!-- Use the menu on click and not on mouse over not create confusion for small screens to much menu open without wanting it -->
        <ul class="show-for-small-only dropdown menu float-left" data-options="disableHover:true;clickOpen:true" data-dropdown-menu data-description="navigational" data-magellan>
            <!-- Alternative menu for small screens -->
            <li class="show-for-small-only has-submenu is-down-arrow" role="menuitem" aria-haspopup="true" aria-selected="false" aria-expanded="false" aria-label="Also in this section" ><a href="#">Atlas menu</a>
                <ul class="menu submenu is-dropdown-submenu first-sub vertical" data-submenu="" aria-hidden="true" role="menu">
                    <li class="first active-trail active menu-mlid-3080 menu-overview active active-trail menu-3080 is-submenu-item is-dropdown-submenu-item" role="menuitem"><a href="index_atlas_irene.html" class="active">Home</a></li>
                    <li class="menu-mlid-3422 menu-research-groups menu-3422 is-submenu-item is-dropdown-submenu-item" role="menuitem"><a href="atlas_browse.html">Browse experiments</a></li>
                    <li class="has-children menu-mlid-3407 menu-postdocs menu-3407 is-submenu-item is-dropdown-submenu-item" role="menuitem"><a href="atlas_download.html">Download</a></li>
                    <li class="last has-children menu-mlid-3125 menu-3125 is-submenu-item is-dropdown-submenu-item" role="menuitem"><a href="atlas_help.html">Help</a></li>
                    <li class="last has-children menu-mlid-3125  is-submenu-item is-dropdown-submenu-item" role="menuitem"><a href="atlas_about.html">About</a></li>
                </ul>
            </li>
        </ul>

        <ul class="hide-for-small-only horizontal menu" data-magellan>
            <li><a href="" class="active">Home</a></li>
            <li><a href="">Browse experiments</a></li>
            <li><a href="">Download</a></li>
            <li><a href="">Help</a></li>
            <li><a href="">About</a></li>
        </ul>

        <!-- Adding class hide-for-small-only as the right hand side takes a lot of space for small + remove text for medium-->
    </nav>
    <!-- /local-nav -->
</div>