<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>
<%--@elvariable id="searchDescription" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasDifferentialResults" type="boolean"--%>
<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ include file="includes/bootstrap.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bioentities/bioentities.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bioentities/bioentities-box.css"/>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/expressionAtlasBaselineExpression.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/expressionAtlasDifferentialExpression.bundle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<h:ebiGlobalSearch ebiSearchTerm="${not empty searchDescription ? applicationProperties.urlParamEncode(searchDescription) : searchDescription}"/>

<section>
    <h2 class="strapline">
        Results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>

<section class="gxaSection">
    <ul class="nav nav-tabs" role="tablist">
        <c:if test="${hasBaselineResults}"><li title="Baseline experiments" role="presentation"><a href="${requestScope['javax.servlet.forward.request_uri']}#base" data-toggle="tab" id="baselineTabLink">Baseline expression</a></li></c:if>
        <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" role="presentation" class="disabled noBorderTab">Baseline expression</li></c:if>

        <c:if test="${hasDifferentialResults}"><li title="Differential experiments" role="presentation"><a href="${requestScope['javax.servlet.forward.request_uri']}#diff" data-toggle="tab" id="differentialTabLink">Differential expression</a></li></c:if>
        <c:if test="${!hasDifferentialResults}"><li title="Differential experiments" role="presentation" class="disabled noBorderTab">Differential expression</li></c:if>

    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade" id="base"><%@ include file="baseline-expression.jsp" %></div>
        <div role="tabpanel" class="tab-pane fade" id="diff"><%@ include file="differential-expression.jsp" %></div>
    </div>
</section>


<script>

    var hasBaselineResults = ${hasBaselineResults},
        hasDifferentialResults = ${hasDifferentialResults};

    var $baselineTabLink = $("#baselineTabLink"),
        $differentialTabLink = $("#differentialTabLink");

    $baselineTabLink.click(function() {
        $(".gxaContrastTooltip").add(".gxaWebpackHelpTooltip").remove();
        window.location.hash = "#baseline";
    });
    $differentialTabLink.click(function() {
        $(".gxaContrastTooltip").add(".gxaWebpackHelpTooltip").remove();
        window.location.hash = "#differential";
    });

    $baselineTabLink.on("shown.bs.tab", function() {
        // Hack to resize Highcharts heat maps to container width
        // Use CustomEvent instead of Event to be compatible with IE (we’re using a polyfill)
        window.dispatchEvent(new CustomEvent("resize"));
    });

    setInitialHash();
    showTabOnHash();

    window.addEventListener("popstate", showTabOnHash);

    function showTabOnHash() {
        if (window.location.hash === "#baseline") {
            $baselineTabLink.tab("show");
        } else  {
            $differentialTabLink.tab("show");
        }
    }

    function setInitialHash() {
        if (window.location.hash != "#differential" && window.location.hash != "#information") {
            var hash;

            if (hasBaselineResults) {
                hash = "#baseline";
            }
            else if (hasDifferentialResults) {
                hash = "#differential";
            }

            var newURL = new URI(window.location).hash(hash);
            history.replaceState(null, "", newURL);
        }
    }

</script>
