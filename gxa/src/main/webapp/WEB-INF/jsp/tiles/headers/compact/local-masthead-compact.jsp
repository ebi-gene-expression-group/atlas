<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="masthead" class="masthead compact-for-data">

    <div class="masthead-inner row">
        <!-- local-title -->
        <div class="columns medium-7 large-3" id="local-title">
            <a href="${pageContext.request.contextPath}/home/" title="Back to Expression Atlas homepage">
                <h1>Expression Atlas</h1>
            </a>
        </div>
        <!-- end local-title -->

        <!-- local-search -->
        <div class="columns medium-5 large-3 large-push-6">
            <form id="local-search" name="local-search" method="get" action="${pageContext.request.contextPath}/search">
                <fieldset>
                    <div class="input-group margin-bottom-none margin-top-large" >
                        <input type="text" class="input-group-field" name="geneQuery" id="local-searchbox">
                        <div class="input-group-button">
                            <input id="submit-searchbox" type="submit" value="1" class="button icon icon-functional">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
        <!-- end local-search -->

        <!-- local-nav -->
        <div class="columns large-6 large-pull-3">
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

        </div>
        <!-- end local-nav -->

    </div>

</div>