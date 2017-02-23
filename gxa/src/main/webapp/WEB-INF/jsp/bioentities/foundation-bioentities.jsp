<%--@elvariable id="searchDescription" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasDifferentialResults" type="boolean"--%>
<%--@elvariable id="applicationProperties" type="atlas.web.ApplicationProperties"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-migrate-1.4.0.min.js"></script>
<script language="JavaScript" type="text/javascript" src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<%@ include file="includes/bootstrap.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bioentities/bioentities.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/foundation/bioentities-box.css"/>

<%--<link type="text/css" rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css">--%>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasBaselineExpression.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasDifferentialExpression.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasBioentityInformation.bundle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<c:if test="${not empty searchDescription}">
    <section>
        <h2 class="strapline">
            Results for <span class="searchterm">${searchDescription}</span>
        </h2>
    </section>
</c:if>

<!-- Simple page header -->
<section class="gxaSection">
    <div class="gxaBioentityHeader">
        <p class="gxaBioentityName">${entityFullName}</p>
        <p class="gxaBioentitySpecies">${species}</p>
        <p class="gxaBioentityDescription">${bioEntityDescription}</p>
    </div>
</section>

<section class="gxaSection">
    <ul class="tabs" data-tabs role="tablist" id="experiments-tabs">
        <c:if test="${hasBaselineResults}">
            <li title="Baseline experiments" class="tabs-title is-active" role="presentation">
                <a href="${requestScope['javax.servlet.forward.request_uri']}&foundation#base" role="tab" id="baselineTabLink">Baseline expression</a>
            </li>
        </c:if>
        <c:if test="${!hasBaselineResults}">
            <li title="Baseline experiments" class="tabs-title" role="presentation">Baseline expression</li>
        </c:if>

        <c:if test="${hasDifferentialResults}">
            <li title="Differential experiments" class="tabs-title" role="presentation">
                <a href="${requestScope['javax.servlet.forward.request_uri']}&foundation#diff" role="tab" id="differentialTabLink">Differential expression</a>
            </li>
        </c:if>
        <c:if test="${!hasDifferentialResults}">
            <li title="Differential experiments" class="tabs-title" role="presentation">
                <a href="">Differential expression</a>
            </li>

        </c:if>

        <li title="Bioentity information" class="tabs-title" role="presentation" >
            <a href="${requestScope['javax.servlet.forward.request_uri']}&foundation#info" role="tab" id="informationTabLink">${entityBriefName} information</a>
        </li>
    </ul>

    <!-- Tab panes -->
    <div class="tabs-content" data-tabs-content="experiments-tabs">
        <div role="tabpanel" class="tabs-panel is-active" id="base"><%@ include file="baseline-expression.jsp" %></div>
        <div role="tabpanel" class="tabs-panel" id="diff"><%@ include file="differential-expression.jsp" %></div>
        <div role="tabpanel" class="tabs-panel" id="info"><%@ include file="bioentity-information.jsp" %></div>
    </div>
</section>


<script>

    let hasBaselineResults = ${hasBaselineResults};
    let hasDifferentialResults = ${hasDifferentialResults};

    let $informationTabLink = $("#informationTabLink"),
        $baselineTabLink = $("#baselineTabLink"),
        $differentialTabLink = $("#differentialTabLink");

    $informationTabLink.click(function() {
        $(".gxaContrastTooltip").add(".gxaWebpackHelpTooltip").remove();
        window.location.hash = "#information";
    });
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
        } else if (window.location.hash === "#differential") {
            $differentialTabLink.tab("show");
        } else {
            $informationTabLink.tab("show");
        }
    }

    function setInitialHash() {
        if (window.location.hash != "#baseline" && window.location.hash != "#differential" && window.location.hash != "#information") {
            let hash = "#information";

            if (hasBaselineResults) {
                hash = "#baseline";
            }
            else if (hasDifferentialResults) {
                hash = "#differential";
            }

            let newURL = new URI(window.location).hash(hash);
            history.replaceState(null, "", newURL);
        }
    }

</script>
