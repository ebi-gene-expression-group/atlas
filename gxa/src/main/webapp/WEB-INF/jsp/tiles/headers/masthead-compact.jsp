<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="masthead" class="masthead  compact-for-data">
    <div class="masthead-inner row expanded">
        <div class="small-12 medium-7 columns" id="local-title">
            <a href="${pageContext.request.contextPath}/home/" title="Back to Expression Atlas homepage">
                <h1>Expression Atlas</h1>
            </a>
        </div>

        <%--<div class="large-6 small-push-12 columns">--%>
        <%--<nav>--%>
        <%--<ul class="dropdown menu" data-description="navigational">--%>
        <%--<li><a href="${pageContext.request.contextPath}/home">Home</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/download.html">Download</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/release-notes.html">Release notes</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/FAQ.html">FAQ</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/help/index.html">Help</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/licence.html">Licence</a></li>--%>
        <%--<li><a href="${pageContext.request.contextPath}/about.html">About</a></li>--%>
        <%--</ul>--%>
        <%--</nav>--%>
        <%--</div>--%>

        <div class="small-12 medium-5 columns">
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

    </div>
</div>
