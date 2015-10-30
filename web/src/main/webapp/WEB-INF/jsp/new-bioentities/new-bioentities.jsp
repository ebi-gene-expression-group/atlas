<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ include file="includes/bootstrap.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/new-bioentities/bioentities.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/faceted-search.css"/>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/faceted-search.bundle.js"></script>

<!-- Simple page header -->
<div class="gxaBioentityHeader">
    <p class="gxaBioentityName">${bioEntityPropertyService.getEntityName()}</p>
    <p class="gxaBioentitySpecies">${bioEntityPropertyService.getSpecies()}</p>
    <p class="gxaBioentityDescription">${bioEntityPropertyService.getBioEntityDescription()}</p>
</div>
<!-- /Simple page header -->

<section class="grid_24">

    <ul class="nav nav-tabs" role="tablist">
        <c:if test="${hasBaselineResults}"><li title="Baseline experiments" role="presentation"><a href="new/genes/${identifier}#baseline" data-toggle="tab" id="baselineTabLink">Baseline expression</a></li></c:if>
        <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" role="presentation" class="disabled noBorderTab">Baseline expression</li></c:if>
            <c:if test="${hasDifferentialResults}"><li title="Differential experiments" role="presentation"><a href="new/genes/${identifier}#differential" data-toggle="tab" id="differentialTabLink">Differential expression</a></li></c:if>
        <c:if test="${!hasDifferentialResults}"><li title="Differential experiments" role="presentation" class="disabled noBorderTab">Differential expression</li></c:if>

        <li role="presentation" title="Bioentity information"><a href="new/genes/${identifier}#information" data-toggle="tab" id="informationTabLink">Bioentity information</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane" id="information"><%@ include file="bioentity-information.jsp" %></div>
        <div role="tabpanel" class="tab-pane" id="baseline"><%@ include file="baseline-expression.jsp" %></div>
        <div role="tabpanel" class="tab-pane" id="differential"><%@ include file="differential-expression.jsp" %></div>
    </div>

</section>

<script>

    var ie9 = $.browser.msie && $.browser.version < 10;
    var $w = $(window);

    var hasBaselineResults = ${hasBaselineResults},
        hasDifferentialResults = ${hasDifferentialResults};

    var $informationTabLink = $("#informationTabLink"),
        $baselineTabLink = $("#baselineTabLink"),
        $differentialTabLink = $("#differentialTabLink");

    $informationTabLink.click(function() {
        window.location.hash = "#information";
    });
    $baselineTabLink.click(function() {
        window.location.hash = "#baseline";
        $w.resize();
    });
    $differentialTabLink.click(function() {
        window.location.hash = "#differential";
    });

    if (ie9) {
        window.onhashchange = route;
    } else {
        window.addEventListener('popstate', route);
    }

    function route() {
        if (window.location.hash === "#information") {
            $informationTabLink.tab("show")
        } else if (window.location.hash === "#baseline") {
            $baselineTabLink.tab("show");
        } else if (window.location.hash === "#differential") {
            $differentialTabLink.tab("show");
        } else {

            var newLocation = window.location;
            if (hasBaselineResults) {
                newLocation.hash = "#baseline";
                if (ie9) {
                    window.location = newLocation;
                } else {
                    history.replaceState(null, null, newLocation);
                }
                $baselineTabLink.tab("show");
            } else if (hasDifferentialResults) {
                newLocation.hash = "#differential";
                if (ie9) {
                    window.location = newLocation;
                } else {
                    history.replaceState(null, null, newLocation);
                }
                $differentialTabLink.tab("show");
            } else {
                newLocation.hash = "#information";
                if (ie9) {
                    window.location = newLocation;
                } else {
                    history.replaceState(null, null, newLocation);
                }
                $informationTabLink.tab("show");
            }

        }
    }

    route();

</script>

