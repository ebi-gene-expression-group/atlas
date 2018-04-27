<%@ page contentType="text/html;charset=UTF-8" %>

<div data-sticky-container>
    <header id="masthead" class="masthead" data-sticky data-sticky-on="large" data-top-anchor="main-content-area:top" data-btm-anchor="main-content-area:bottom">
        <div class="masthead-inner row">
            <div class="small-8 columns media-object" id="local-title">
                <div class="show-for-medium media-object-section middle">
                    <a href="${pageContext.request.contextPath}/home/" title="Back to Single Cell Expression Atlas homepage" class="clear">
                        <img src="${pageContext.request.contextPath}/resources/images/logos/sc_atlas_logo.png" alt="Single Cell Expression Atlas logo">
                    </a>
                </div>
                <div class="media-object-section middle">
                    <h1><a href="${pageContext.request.contextPath}/home/" title="Back to Single Cell Expression Atlas homepage" class="clear">Single Cell Expression Atlas</a></h1>
                    <h4 class="show-for-large"><a href="${pageContext.request.contextPath}/home/" title="Back to Single Cell Expression Atlas homepage" class="clear">Single cell gene expression across species</a></h4>
                </div>
            </div>

            <%--<div class="hide-for-small-only small-4 columns">--%>
                <%--<!-- EBI Framework standard search -->--%>
                <%--<form id="local-search" name="local-search" method="get" action="${pageContext.request.contextPath}/search">--%>
                    <%--<h4>Search this project</h4>--%>
                    <%--<fieldset>--%>
                        <%--<div class="input-group" style="margin-bottom: 0">--%>
                            <%--<input type="text" name="geneQuery" id="local-searchbox">--%>
                            <%--<div class="input-group-button">--%>
                                <%--<input id="submit-searchbox" type="submit" value="1" class="button icon icon-functional">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<small class="show-for-large">--%>
                            <%--<!-- Include some example searchterms - keep them short and few. -->--%>
                            <%--<span>Examples: <a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22ASPM%22%7D%5D">ASPM</a>,--%>
                            <%--<a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22apoptosis%22%7D%5D">Apoptosis</a>,--%>
                            <%--<a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22ENSMUSG00000021789%22%7D%5D">ENSMUSG00000021789</a>,--%>
                            <%--<a href="${pageContext.request.contextPath}/search?geneQuery=%5B%7B%22value%22%3A%22zinc%20finger%22%7D%5D">zinc finger</a></span>--%>
                        <%--</small>--%>

                    <%--</fieldset>--%>
                <%--</form>--%>
            <%--</div>--%>

            <nav>
                <ul id="local-nav" class="dropdown menu float-left" data-description="navigational">
                    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/experiments">Browse experiments</a></li>
                    <li><a href="">Download</a></li>
                    <li><a href="">Help</a></li>
                    <li><a href="">About</a></li>
                    <li><a href="" target="_blank">Feedback</a></li>
                </ul>
            </nav>
        </div>
    </header>
</div>

