<c:choose>
    <c:when test="${empty jsonProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

    <%@ include file="react.jsp" %>
    <%@ include file="heatmap-js.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/latest/js/lib/jquery.toolbar.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/latest/css/jquery.toolbars.css" />

    <div id="genenametooltip-content" style="display: none"></div>

        <script type="text/javascript">
            var heatmapData = <%@ include file="heatmap-data.jsp" %>;
            var heatmapModuleBuild = ${isMultiExperiment ? 'heatmapModule.buildMultiExperiment': (type.differential ? 'heatmapModule.buildDifferential' : (type.proteomicsBaseline ? 'heatmapModule.buildProteomicsBaseline' : 'heatmapModule.buildBaseline'))};

            (function ($, React, build, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData) {

                $(document).ready(function () {
                    // Call this inside ready() so all scripts load first in IE8
                    var heatmap = build(heatmapConfig, $('#displayLevels'));

                    React.renderComponent(heatmap.Heatmap({columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles}),
                            document.getElementById('heatmap-react')
                    );

                    if (heatmap.EnsemblLauncher) {
                        React.renderComponent(heatmap.EnsemblLauncher(),
                                document.getElementById(anatomogramData ? "anatomogram-ensembl-launcher" : "ensembl-launcher"));
                    }

                    // Load anatomogram after heatmap is rendered so wiring works
                    if (anatomogramData) {
                        anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
                                                anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene);
                    } else {
                        $("#anatomogram").remove();
                    }
                });

            })(jQuery, React, heatmapModuleBuild, heatmapData.config, heatmapData.columnHeaders, heatmapData.profiles, heatmapData.geneSetProfiles, heatmapData.anatomogram);
        </script>
    </c:otherwise>
</c:choose>
