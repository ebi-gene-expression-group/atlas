<%@ page contentType="text/html;charset=UTF-8" %>

<div>
    <header id="masthead" class="masthead">
        <div class="masthead-inner row">
            <!-- local-title -->
            <div class="small-8 medium-10 columns anim" id="local-title">
                <h1>
                    <a href="${pageContext.request.contextPath}/home" title="Back to Single Cell Expression Atlas homepage">
                        <img src="${pageContext.request.contextPath}/resources/images/logos/logo_atlas_transparent.png" alt="Single Cell Expression Atlas logo" >
                        Single Cell Expression Atlas
                    </a>
                </h1>
                <h4 class="hide-for-small-only">Single-cell gene expression across species</h4>
            </div>
            <!-- /local-title -->

            <!-- local-nav -->
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
    </header>
</div>
