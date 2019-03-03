<%--@elvariable id="entityBriefName" type="java.lang.String"--%>
<%--@elvariable id="searchDescription" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasDifferentialResults" type="boolean"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/foundation/bioentities-box.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/popup-feedback.css" type="text/css" media="all">

<style>
    #feedback-button {
        display: inline;
    }
    #feedback-button h1 {
        color: black;
    }
</style>

<div class="row expanded">
    <div class="small-12 columns">

        <c:if test="${empty entityBriefName}">
            <div>
                <h3 class="gxaSearchTermDescription">
                    Results for <span class="searchterm">${searchDescription}</span>
                </h3>
            </div>
        </c:if>

        <c:if test="${not empty entityBriefName}">
            <div class="gxaBioentityHeader">
                <p class="gxaBioentityName">${entityFullName}</p>
                <p class="gxaBioentitySpecies">${species}</p>
                <p class="gxaBioentityDescription">${bioEntityDescription}</p>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${hasBaselineResults && hasDifferentialResults}">
                <c:set var="baselineTabClass" value="is-active"/>
                <c:set var="differentialTabClass" value=""/>
            </c:when>
            <c:when test="${hasBaselineResults && !hasDifferentialResults}">
                <c:set var="baselineTabClass" value="is-active"/>
                <c:set var="differentialTabClass" value="tab-disabled"/>
            </c:when>
            <c:when test="${!hasBaselineResults && hasDifferentialResults}">
                <c:set var="baselineTabClass" value="tab-disabled"/>
                <c:set var="differentialTabClass" value="is-active"/>
            </c:when>
        </c:choose>

        <div>
            <ul class="tabs" data-tabs data-deep-link="true" data-update-history="true" id="expression-tabs">
                <li class="tabs-title ${baselineTabClass}"><a href="#baseline">Baseline expression</a></li>
                <li class="tabs-title ${differentialTabClass}"><a href="#differential">Differential expression</a></li>

                <c:if test="${not empty entityBriefName}">
                    <li class="tabs-title"><a href="#information">${entityBriefName} information</a></li>
                </c:if>
            </ul>

            <div class="tabs-content" data-tabs-content="expression-tabs">
                <div class="tabs-panel ${baselineTabClass}" id="baseline"><%@ include file="search-results/baseline-expression.jsp" %></div>
                <div class="tabs-panel ${differentialTabClass}" id="differential"><%@ include file="search-results/differential-expression.jsp" %></div>

                <c:if test="${not empty entityBriefName}">
                    <div class="tabs-panel" id="information"><%@ include file="search-results/bioentity-information.jsp" %></div>
                </c:if>
            </div>
        </div>

    </div>
</div>
<div id="feedback-button"></div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/react-popup.css" type="text/css" media="all">
<script defer src="${pageContext.request.contextPath}/resources/js-bundles/feedbackForm.bundle.js"></script>

<script defer src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>
<script>
  document.addEventListener("DOMContentLoaded", function(event) {
    if (!window.location.hash) {
      var initialHash = ${hasBaselineResults} ? '#baseline' : '#differential';
      var newURL = URI(window.location).hash(initialHash).toString();
      history.replaceState(null, '', newURL);
    }
    feedbackForm.render({
      feedbackFormLink: 'https://www.ebi.ac.uk/support/gxa',
      gaId: 'UA-37676851-1'
    }, 'feedback-button');
  })
</script>
