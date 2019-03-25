<%@ page contentType="text/html;charset=UTF-8" %>

<div data-sticky-container>
    <header id="masthead" class="masthead" data-sticky data-sticky-on="large" data-top-anchor="content:top" data-btm-anchor="content:bottom">
        <div class="masthead-inner row expanded">
            <div class="medium-12 large-8 columns">
                <!-- local-title -->
                <a href="${pageContext.request.contextPath}/home" title="Back to Single Cell Expression Atlas homepage">
                <div class="media-object" id="local-title">
                    <div class="media-object-section hide-for-small-only">
                        <img src="${pageContext.request.contextPath}/resources/images/logos/sc_atlas_logo.png" alt="Single Cell Expression Atlas logo" style="height:7em">
                    </div>
                    <div class="media-object-section">
                        <h1>Single&nbsp;Cell Expression&nbsp;Atlas</h1>
                        <h4 class="show-for-large">Single cell gene expression across species</h4>
                    </div>
                </div>
                </a>
            </div>

            <div class="medium-12 large-4 columns text-right">
                <h4 class="show-for-large">Query bulk expression</h4>
                <a href="/gxa" title="To Expression Atlas" class="button" style="box-shadow: 2px 2px 2px 2px rgba(0,0,0,0.5)"><i class="icon icon-functional" data-icon="<"></i> Back to Expression Atlas</a>
            </div>

            <nav>
                <ul id="local-nav" class="dropdown menu float-left" data-description="navigational">
                    <li id="local-nav-home"><a href="${pageContext.request.contextPath}/home"><i class="icon icon-generic padding-right-medium" data-icon="H"></i>Home</a></li>
                    <li id="local-nav-search"><a href="${pageContext.request.contextPath}/search"><i class="icon icon-functional padding-right-medium" data-icon="1"></i>Gene search</a></li>
                    <li id="local-nav-experiments"><a href="${pageContext.request.contextPath}/experiments"><i class="icon icon-functional padding-right-medium" data-icon="C"></i>Browse experiments</a></li>
                    <%--<li id="local-nav-download"><a href="#"><i class="icon icon-common padding-right-medium" data-icon="&#xc2;"></i>Download</a></li>--%>
                    <li id="local-nav-release-notes"><a href="${pageContext.request.contextPath}/release-notes.html"><i class="icon icon-generic padding-right-medium" data-icon=";"></i>Release notes</a></li>
                    <li id="local-nav-help"><a href="${pageContext.request.contextPath}/help.html"><i class="icon icon-generic padding-right-medium" data-icon="?"></i>Help</a></li>
                    <%--<li id="local-nav-about"><a href="../websites/patterns/"><i class="icon icon-common padding-right-medium" data-icon="&#x2139;"></i>About</a></li>--%>
                    <li id="local-nav-feedback"><a href="https://www.ebi.ac.uk/support/gxasc" target="_blank" data-icon="X"><i class="icon icon-generic padding-right-medium" data-icon="s"></i>Support</a></li>
                </ul>
            </nav>
            <!-- /local-nav -->
        </div>
    </header>
</div>

