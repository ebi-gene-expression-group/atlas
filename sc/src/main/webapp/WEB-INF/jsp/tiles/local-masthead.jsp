<%@ page contentType="text/html;charset=UTF-8" %>

<div data-sticky-container>
    <header id="masthead" class="masthead" data-sticky data-sticky-on="large" data-top-anchor="content:top" data-btm-anchor="content:bottom">
        <div class="masthead-inner row">
            <!-- local-title -->
            <a href="${pageContext.request.contextPath}/home" title="Back to Single Cell Expression Atlas homepage">
            <div class="media-object columns small-8" id="local-title">
                <div class="media-object-section hide-for-small-only">
                    <img src="${pageContext.request.contextPath}/resources/images/logos/sc_atlas_logo.png" alt="Single Cell Expression Atlas logo" style="height:7em">
                </div>
                <div class="media-object-section">
                    <h1>Single Cell Expression&nbsp;Atlas</h1>
                    <h4 class="show-for-large">Single cell gene expression across species</h4>
                </div>
            </div>
            </a>

            <!-- local-search -->
            <!--
            <div class="columns medium-6 text-right">
                <div class="">
                    <form id="ebi_search" action="../search">
                        <fieldset>
                            <div class="input-group margin-bottom-none margin-top-large">
                                <input type="text" name="q" id="tipue_search_input" class="input-group-field" pattern=".{3,}" tabindex="1" title="At least 3 characters" placeholder="Search the Style Lab" required>
                                <div class="input-group-button">
                                    <input id="search_submit" class="button icon icon-functional" tabindex="2" type="submit" name="submit1" value="1">
                                </div>
                            </div>
                        </fieldset>
                        <p id="example" class="small">
                          Examples: <a href="/ebisearch/search.ebi?db=allebi&amp;requestFrom=ebi_index&amp;query=blast">blast</a>, <a href="/ebisearch/search.ebi?db=allebi&amp;query=keratin&amp;requestFrom=ebi_index">keratin</a>, <a href="/ebisearch/search.ebi?db=allebi&amp;query=bfl1&amp;requestFrom=ebi_index">bfl1</a>
                          <a class="float-right" href="#"><span class="icon icon-generic" data-icon="("></span> advanced search</a>
                        </p>
                    </form>
                </div>
            </div>
            -->
            <!-- /local-search -->
            <!-- /local-title -->

            <!-- local-nav -->
            <nav>
                <ul id="local-nav" class="dropdown menu float-left" data-description="navigational">
                    <li id="local-nav-home"><a href="${pageContext.request.contextPath}/home"><i class="icon icon-generic" data-icon="H"></i> Home</a></li>
                    <li id="local-nav-experiments"><a href="${pageContext.request.contextPath}/experiments"><i class="icon icon-common" data-icon="&#x41;"></i> Browse experiments</a></li>
                    <li id="local-nav-download"><a href="#"><i class="icon icon-common" data-icon="&#xc2;"></i> Download</a></li>
                    <li id="local-nav-help"><a href="#"><i class="icon icon-common" data-icon="&#x3f;"></i> Help</a></li>
                    <li id="local-nav-about"><a href="../websites/patterns/"><i class="icon icon-common" data-icon="&#x2139;"></i> About</a></li>
                    <li id="local-nav-feedback"><a href="" target="_blank" data-icon="X"><i class="icon icon-common" data-icon="&#x6e;"></i> Feedback</a></li>
                </ul>
            </nav>
            <!-- /local-nav -->
        </div>
    </header>
</div>

