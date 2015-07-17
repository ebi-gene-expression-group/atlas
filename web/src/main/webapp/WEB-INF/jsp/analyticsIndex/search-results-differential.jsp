<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>
<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div id="search_result_pg">

<div id="help-placeholder" style="display: none"></div>

<!-- Search box -->
<section class="grid_24" style="margin-bottom:18px;">
    <%@ include file="includes/search-form.jsp" %>
</section>
<!-- /Search box -->

<c:if test="${not empty searchDescription}" >

    <!-- Simple page header -->
    <div class="container">
        <div class="page-header">
            <h2 class="strapline">Search result for <span class="searchterm">"${searchDescription}"</span></h2>
        </div>
        <!--  <h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>-->
    </div>
    <!-- /Simple page header -->

</c:if>


<section class="grid_24">
        <div class="grid_6 alpha" id="atlasAnalyticsSearchFacetContainer"></div>

        <div class="grid_18 omega">

            <div class="container">

                <ul class="nav nav-tabs">
                    <c:if test="${hasBaselineResults}"><li title="Baseline experiments"><a data-toggle="tab" href="#baseline" >Baseline experiments</a></li></c:if>
                    <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" class="disabled" >Baseline experiments</li></c:if>
                    <c:if test="${hasDifferentialResults}"><li title="Differential experiments" class="active"><a data-toggle="tab" href="#differential">Differential experiments</a></li></c:if>
                    <c:if test="${!hasDifferentialResults}"> <li title="Differential experiments" class="disabled">Differential experiments</li></c:if>
                </ul>

                <div class="tab-content">
                    <div id="baseline" class="tab-pane fade ">
                        <c:if test="${!hasBaselineResults}"><p>No baseline results<p></c:if>
                        <c:if test="${hasBaselineResults}">
                        <h3>List of baseline experiments</h3>
                        <p class="searchterm_det">Showing XX to XX of XX results</p>
                        <div id="atlasAnalyticsSearchHeatmapsContainer"></div>
                        </c:if>

                    </div>
                    <div id="differential" class="tab-pane fade in active">
                        <c:if test="${!hasDifferentialResults}"><p>No differential results<p></c:if>
                        <c:if test="${hasDifferentialResults}">
                        <h3>List of differential experiments</h3>
                            <p class="searchterm_det">Showing XX to XX of XX results</p> <a href="${pageContext.request.contextPath}/search?geneQuery=${geneQuery.asUrlQueryParameter()}">base</a>
                        <div  id="atlasAnalyticsSearchDiffResultsContainer" ></div>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>
</section>

</div><!-- /search_facet -->

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/faceted-search-results.bundle.js"></script>

<c:if test="${hasDifferentialResults}">
    <script>
        var facetsData = ${empty jsonDifferentialGeneQueryFacets ? 'null' : jsonDifferentialGeneQueryFacets};
        <%--var diffResultsData = ${empty jsonDifferentialGeneQueryResults ? 'null': jsonDifferentialGeneQueryResults};--%>

        var differential_page_js = window.exposed;

        differential_page_js(facetsData, 'atlasAnalyticsSearchFacetContainer', 'atlasAnalyticsSearchDiffResultsContainer');
    </script>
</c:if>

<!-- absolute Link to bootstrap script - temp - make it relative-->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<!-- Script to solve conflict between Bootstrap and Jquery https://github.com/twbs/bootstrap/issues/6094 -->
<script>
    var btn = $.fn.button.noConflict() // reverts $.fn.button to jqueryui btn
    $.fn.btn = btn // assigns bootstrap button functionality to $.fn.btn
</script>

