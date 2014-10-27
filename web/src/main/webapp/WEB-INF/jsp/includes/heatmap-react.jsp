<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:choose>
    <c:when test="${empty jsonProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise><%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>

        <%@ include file="react.jsp" %>

        <script src="${pageContext.request.contextPath}/resources/js/transcriptPopupModule.js"></script>
        <script language="JavaScript" type="text/javascript"
                src="${pageContext.request.contextPath}/resources/js/highlight.js"></script>
        <script language="JavaScript" type="text/javascript"
                src="${pageContext.request.contextPath}/resources/js/genePropertiesTooltipModule.js"></script>

        <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/jsx/contrastTooltip.js"></script>
        <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contrastTooltipModule.js"></script>

        <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/jsx/factorTooltip.js"></script>
        <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/factorTooltipModule.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/EventEmitter-4.2.7.min.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/resources/jsx/heatmap.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/resources/js/jquery.toolbar.js"></script>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.toolbars.css" />

        <script src="${pageContext.request.contextPath}/resources/js/jquery.stickytableheaders.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.sticky.js"></script>


        <div id="genenametooltip-content" style="display: none"></div>

        <%-- used by helpTooltipsModule --%>
        <div id="help-placeholder" style="display: none"></div>

        <%@ include file="transcript-breakdown-popup.jsp" %>

        <c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
        <c:set var="atlasHost" value="${pageContext.request.serverName == 'localhost' ? 'wwwdev' : pageContext.request.serverName.concat(serverPort)}"/>

        <script type="text/javascript">
            var heatmapData = (function (genePropertiesTooltipModule) {

                <%--
                //TODO: extract ensemlb genome launcher config parameters (ensemblDB, columnType etc.) out into separate object
                //TODO: remove enableGeneLinks parameter
                //TODO: investigate why showMaPlotButton is always true
                --%>
                var config = (function (genePropertiesTooltipModule) {
                    return {
                        atlasHost: '${atlasHost}',
                        contextRoot: '${pageContext.request.contextPath}',
                        experimentAccession: '${experimentAccession}',
                        geneQuery: '${geneQuery}',
                        accessKey: '${param.accessKey}',
                        species: '${species}',
                        ensemblDB: '${ensemblDB}',
                        columnType: '${fn:toLowerCase(queryFactorName)}',
                        isExactMatch: ${not empty preferences ? preferences.exactMatch : 'true'},
                        enableGeneLinks: true,
                        enableEnsemblLauncher: ${isMultiExperiment ? false : (empty enableEnsemblLauncher ? true : enableEnsemblLauncher)},
                        showMaPlotButton: true,
                        gseaPlots: ${empty gseaPlots ? 'undefined' : gseaPlots},
                        <c:if test="${empty disableTranscriptPopups || !disableTranscriptPopups}">
                        transcripts: {
                            serializedFilterFactors: '${serializedFilterFactors != null ? serializedFilterFactors : ""}',
                            queryFactorType: '${isMultiExperiment ? 'ORGANISM_PART' : preferences.queryFactorType}'
                        },
                        </c:if>
                        toolTipHighlightedWords: genePropertiesTooltipModule.splitIntoWords('${preferences.geneQuery}'),
                        downloadProfilesURL: '${applicationProperties.buildDownloadURL(pageContext.request)}'
                    };
                })(genePropertiesTooltipModule);

                var columnHeaders = ${jsonColumnHeaders};

                var geneSetProfiles = ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : 'undefined'};

                var profiles = ${jsonProfiles};

                return {
                    config: config,
                    columnHeaders: columnHeaders,
                    geneSetProfiles: geneSetProfiles,
                    profiles: profiles
                }
            })(genePropertiesTooltipModule);

            <c:choose>
                <c:when test="${hasAnatomogram}">
                    var anatomogramData = {
                        maleAnatomogramFile: '${maleAnatomogramFile}',
                        femaleAnatomogramFile:  '${femaleAnatomogramFile}',
                        allSvgPathIds: ${empty allSvgPathIds ? 'undefined' : allSvgPathIds},
                        contextRoot: '${pageContext.request.contextPath}'
                    };
                </c:when>
            <c:otherwise>
                var anatomogramData = undefined;
            </c:otherwise>
            </c:choose>

            var heatmapModuleBuild = ${isMultiExperiment ? 'heatmapModule.buildMultiExperiment': (isDifferential ? 'heatmapModule.buildDifferential' : 'heatmapModule.buildBaseline')};

            (function ($, React, build, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData) {

                $(document).ready(function () {
                    // call this inside ready() so all scripts load first in IE8
                    var heatmap = build(heatmapConfig, $('#displayLevels'));

                    React.renderComponent(heatmap.Heatmap({columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles}),
                            document.getElementById('heatmap-react')
                    );

                    if (heatmap.EnsemblLauncher) {
                        React.renderComponent(heatmap.EnsemblLauncher(),
                                document.getElementById(anatomogramData ? "anatomogram-ensembl-launcher" : "ensembl-launcher"));
                    }

                    // load anatomogram after heatmap is rendered so wiring works
                    if (anatomogramData) {
                        anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile, anatomogramData.contextRoot);
                    } else {
                        $("#anatomogram").remove();
                    }
                });

            })(jQuery, React, heatmapModuleBuild, heatmapData.config,
                    heatmapData.columnHeaders, heatmapData.profiles, heatmapData.geneSetProfiles, anatomogramData);
        </script>
    </c:otherwise>
</c:choose>
