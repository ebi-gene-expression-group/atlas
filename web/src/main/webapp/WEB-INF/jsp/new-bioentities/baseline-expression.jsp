<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="hasSelectedSpecies" type="boolean"--%>
<%--@elvariable id="selectedSpecies" type="java.lang.String"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="queryType" type="java.lang.String"--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<section class="grid_24 alpha omega">
    <h5 style="padding: 0">
    <input type="checkbox" checked="true" name="anatomogram" value="showAnatomogram" id="showAnatomogramsCheckbox">
        <label for="showAnatomogramsCheckbox">Show anatomograms</label>
    </h5>
</section>

<div class="grid_5 alpha" id="gxaBaselineFacetsContainerDiv"></div>

<div class="grid_19 omega">
    <c:if test="${!hasBaselineResults}"><p>No baseline results<p></c:if>
    <c:if test="${hasBaselineResults}">
    <div id="gxaBaselineResultsContainerDiv"></div>
    </c:if>
</div>

<script>
    <c:if test="${hasBaselineResults}">
    var selectedSpecies = "${hasSelectedSpecies ? selectedSpecies : ''}";

    var baselineSearcher = window.exposed.baseline,
        baselineFacetsData = JSON.parse('${jsonFacets}');

    baselineSearcher({
        facetsContainer: "gxaBaselineFacetsContainerDiv",
        resultsContainer: "gxaBaselineResultsContainerDiv",
        showAnatomogramsInput: "showAnatomogramsCheckbox",
        selectedSpecies: selectedSpecies,
        facetsTreeData: baselineFacetsData,
        identifier: "${identifier}",
        queryType: "${queryType}"
    });

    $showAnatomogramsCheckbox = $("#showAnatomogramsCheckbox");

    $showAnatomogramsCheckbox.change(function() {
        var $gxaAside = $(".gxaAside");

        if ($gxaAside.length > 0) {
            $showAnatomogramsCheckbox.prop("disabled", true);

            if ($showAnatomogramsCheckbox.is(":checked")) {
                $(".gxaHeatmapWithoutAnatomogram").switchClass(
                        "gxaHeatmapWithoutAnatomogram", "gxaHeatmapWithAnatomogram", 200, "easeOutSine",
                        function() {
                            $gxaAside.switchClass("gxaInvisible", "gxaVisible", 200, "easeInSine",
                                    function() {
                                        $showAnatomogramsCheckbox.prop("disabled", false);
                                    }
                            );
                        }
                );
            } else {
                $gxaAside.switchClass("gxaVisible", "gxaInvisible", 200, "easeOutSine",
                        function() {
                            $(".gxaHeatmapWithAnatomogram").switchClass(
                                    "gxaHeatmapWithAnatomogram", "gxaHeatmapWithoutAnatomogram", 200, "easeOutSine",
                                    function() {
                                        $showAnatomogramsCheckbox.prop("disabled", false);
                                    }
                            );
                        }
                );
            }
        }
    });
    </c:if>
</script>


