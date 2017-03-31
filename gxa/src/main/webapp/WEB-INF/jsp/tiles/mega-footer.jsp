<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- mega footer-->
<div class="row">

    <%--<div class="small-3 columns">--%>
        <%--<div class="anatomogram" alt="Anatomogram"></div>--%>
    <%--</div>--%>

    <div class="small-3 columns small-offset-2">
        <h5>Experiments</h5>
        <a href="${pageContext.request.contextPath}/experiments?experimentType=baseline" target="_blank">Baseline experiments</a>
        <a href="${pageContext.request.contextPath}/experiments?experimentType=differential" target="_blank">Differential experiments</a><br/>
    </div>

    <div class="small-3 columns">
        <h5>Atlas</h5>
        <a href="${pageContext.request.contextPath}/release-notes.html" target="_blank">Release notes</a><br/>
        <a href="${pageContext.request.contextPath}/download.html" target="_blank">Download</a><br/>
        <a href="${pageContext.request.contextPath}/help/index.html" target="_blank">Help</a><br/>
        <a href="${pageContext.request.contextPath}/FAQ.html" target="_blank">FAQ</a><br/>
        <a href="${pageContext.request.contextPath}/about.html" target="_blank">About Atlas</a>
    </div>

    <div class="small-3 columns end">
        <h5>Follow us</h5>
        <a href="https://twitter.com/ExpressionAtlas" target="_blank">Twitter</a><br/>
        <a href="${pageContext.request.contextPath}/feedback-form" target="_blank">Feedback</a><br/>
        <a href="mailto:arrayexpress-atlas@ebi.ac.uk" target="_blank">Contact</a>
    </div>

</div>
