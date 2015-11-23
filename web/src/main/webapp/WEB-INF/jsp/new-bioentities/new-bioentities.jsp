<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>
<%--@elvariable id="isSearch" type="boolean"--%>
<%--@elvariable id="searchDescription" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasDifferentialResults" type="boolean"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ include file="includes/bootstrap.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/new-bioentities/bioentities.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/new-bioentities/bioentities-box.css"/>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/faceted-search.bundle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<c:if test="${isSearch}">
<section id="gxaSearchHeaderSection">
    <h2 class="strapline">
        Results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>
</c:if>

<!-- Simple page header -->
<section class="gxaBioentityHeader" id="gxaBioentityHeaderSection">
    <p class="gxaBioentityName">${bioEntityPropertyService.entityName}</p>
    <p class="gxaBioentitySpecies">${species}</p>
    <p class="gxaBioentityDescription">${bioEntityPropertyService.bioEntityDescription}</p>
</section>

<section id="gxaBioentityTabsSection">

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
        if (!ie9) {
            window.dispatchEvent(new UIEvent("resize"));    // Force repositioning of sticky header. See https://www.pivotaltracker.com/story/show/107035268
        }
    });
    $differentialTabLink.click(function() {
        window.location.hash = "#differential";
    });

    setInitialHash();
    showTabOnHash();

    if (ie9) {
        window.onhashchange = showTabOnHash;
    } else {
        window.addEventListener('popstate', showTabOnHash);
    }



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

            if (ie9) {
                window.location.hash = hash;
            } else {
                var newURL = new URI(window.location).hash(hash);
                history.replaceState(null, "", newURL);
            }
        }
    }

</script>
