<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>
<%--@elvariable id="isSearch" type="boolean"--%>
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
<script src="${pageContext.request.contextPath}/resources/js-bundles/facetedSearch.bundle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<h:ebiGlobalSearch ebiSearchTerm="${not empty searchDescription ? applicationProperties.urlParamEncode(searchDescription) : searchDescription}"/>

<c:if test="${isSearch}">
<section>
    <h2 class="strapline">
        Results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>
</c:if>

<!-- Simple page header -->
<section class="gxaSection">
    <div class="gxaBioentityHeader">
        <p class="gxaBioentityName">${bioEntityPropertyService.entityName}</p>
        <p class="gxaBioentitySpecies">${species}</p>
        <p class="gxaBioentityDescription">${bioEntityPropertyService.bioEntityDescription}</p>
    </div>
</section>

<section class="gxaSection">
    <ul class="nav nav-tabs" role="tablist">
        <c:if test="${hasBaselineResults}"><li title="Baseline experiments" role="presentation"><a href="genes/${identifier}#base" data-toggle="tab" id="baselineTabLink">Baseline expression</a></li></c:if>
        <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" role="presentation" class="disabled noBorderTab">Baseline expression</li></c:if>

        <c:if test="${hasDifferentialResults}"><li title="Differential experiments" role="presentation"><a href="genes/${identifier}#diff" data-toggle="tab" id="differentialTabLink">Differential expression</a></li></c:if>
        <c:if test="${!hasDifferentialResults}"><li title="Differential experiments" role="presentation" class="disabled noBorderTab">Differential expression</li></c:if>

        <li role="presentation" title="Bioentity information"><a href="genes/${identifier}#info" data-toggle="tab" id="informationTabLink">Bioentity information</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade" id="info"><%@ include file="bioentity-information.jsp" %></div>
        <div role="tabpanel" class="tab-pane fade" id="base"><%@ include file="baseline-expression.jsp" %></div>
        <div role="tabpanel" class="tab-pane fade" id="diff"><%@ include file="differential-expression.jsp" %></div>
    </div>
</section>


<script>

    var hasBaselineResults = ${hasBaselineResults},
        hasDifferentialResults = ${hasDifferentialResults};

    var $informationTabLink = $("#informationTabLink"),
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
            var hash = "#information";

            if (hasBaselineResults) {
                hash = "#baseline";
            }
            else if (hasDifferentialResults) {
                hash = "#differential";
            }
        }
    }

</script>
