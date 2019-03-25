<%@ page contentType="text/html;charset=UTF-8" %>

<div data-sticky-container>
    <header id="masthead" class="masthead" data-sticky data-sticky-on="large" data-top-anchor="content:top" data-btm-anchor="content:bottom">
        <div class="masthead-inner row expanded">
            <!-- local-title -->
            <div class="medium-12 large-8 columns">
                <a href="${pageContext.request.contextPath}/home" title="Back to Expression Atlas homepage">
                    <div class="media-object" id="local-title">
                        <div class="media-object-section hide-for-small-only">
                            <img src="${pageContext.request.contextPath}/resources/images/expression-atlas.png" alt="Expression Atlas logo" style="height:7em">
                        </div>
                        <div class="media-object-section">
                            <h1>Expression&nbsp;Atlas</h1>
                            <h4 class="show-for-large">Gene expression across species and biological conditions</h4>
                        </div>
                    </div>
                </a>
            </div>

            <div class="medium-12 large-4 columns text-right">
                <h4 class="show-for-large">Query single cell expression</h4>
                <a href="/gxa/sc" title="To Single Cell Expression Atlas" class="button" style="box-shadow: 2px 2px 2px 2px rgba(0,0,0,0.5)">To Single Cell Expression Atlas <i class="icon icon-functional" data-icon=">"></i></a>
            </div>

            <nav>
                <ul id="local-nav" class="dropdown menu float-left" data-description="navigational">
                    <li id="local-nav-home"><a href="${pageContext.request.contextPath}/home"><i class="icon icon-generic padding-right-medium" data-icon="H"></i>Home</a></li>
                    <li id="local-nav-experiments"><a href="${pageContext.request.contextPath}/experiments"><i class="icon icon-functional padding-right-medium" data-icon="C"></i>Browse experiments</a></li>
                    <li id="local-nav-download"><a href="${pageContext.request.contextPath}/download.html"><i class="icon icon-functional padding-right-medium" data-icon="="></i>Download</a></li>
                    <li id="local-nav-release-notes"><a href="${pageContext.request.contextPath}/release-notes.html"><i class="icon icon-generic padding-right-medium" data-icon=";"></i>Release notes</a></li>
                    <li id="local-nav-faq"><a href="${pageContext.request.contextPath}/FAQ.html"><i class="icon icon-generic padding-right-medium" data-icon="G"></i>FAQ</a></li>
                    <li id="local-nav-help"><a href="${pageContext.request.contextPath}/help/index.html"><i class="icon icon-generic padding-right-medium" data-icon="?"></i>Help</a></li>
                    <li id="local-nav-licence"><a href="${pageContext.request.contextPath}/licence.html"><i class="icon icon-generic padding-right-medium" data-icon="'"></i>Licence</a></li>
                    <li id="local-nav-about"><a href="${pageContext.request.contextPath}/about.html"><i class="icon icon-generic padding-right-medium" data-icon="i"></i>About</a></li>
                    <li id="local-nav-feedback"><a href="https://www.ebi.ac.uk/support/gxa" target="_blank" data-icon="X"><i class="icon icon-generic padding-right-medium" data-icon="s"></i>Support</a></li>
                </ul>
            </nav>
            <!-- /local-nav -->
        </div>
    </header>
</div>
