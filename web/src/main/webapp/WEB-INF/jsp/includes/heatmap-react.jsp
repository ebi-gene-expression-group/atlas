<script src="//cdnjs.cloudflare.com/ajax/libs/react/0.10.0/react.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/transcriptPopupModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/highlight.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/genePropertiesTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/heatmapModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/contrastInfoTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/factorInfoTooltipModule.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/EventEmitter-4.2.7.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/jsx/heatmap.js" type="text/javascript"></script>

<div id="genenametooltip-content" style="display: none"></div>

<section id="contrastInfo" style="display:none">
    <div id="contrastExperimentDescription" style="font-weight: bold; color:blue; text-align: center"></div>
    <div id="contrastDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Test value
            </th>
            <th class='header-cell'>
                Reference value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<section id="factorInfo" style="display:none">
    <div id="factorDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>

<script type="text/javascript">
    var heatmapData = (function (genePropertiesTooltipModule) {

        var config = (function (genePropertiesTooltipModule) {
            return {
                atlasHost: '${pageContext.request.serverName}${serverPort}',
                contextRoot: '${pageContext.request.contextPath}',
                experimentAccession: '${experimentAccession}',
                accessKey: '${param.accessKey}',
                species: '${species}',
                ensemblDB: '${ensemblDB}',
                queryFactorType: '${preferences.queryFactorType}',
                isExactMatch: ${preferences.exactMatch},
                enableGeneLinks: true,
                selectedFilterFactorsJson: ${selectedFilterFactorsJson != null ? selectedFilterFactorsJson : "''"},
                toolTipHighlightedWords: genePropertiesTooltipModule.splitIntoWords('${preferences.geneQuery}'),
                downloadProfilesURL: '${applicationProperties.buildDownloadURL(pageContext.request)}'
            };
        })(genePropertiesTooltipModule);

        var assayGroupFactors = ${jsonAssayGroupFactors};

        var geneSetProfiles = ${jsonGeneSetProfiles};

        var profiles = ${jsonProfiles};

        return {
            config: config,
            assayGroupFactors: assayGroupFactors,
            geneSetProfiles: geneSetProfiles,
            profiles: profiles
        }
    })(genePropertiesTooltipModule);
</script>

<script type="text/javascript">
    (function ($, React, heatmapModule, heatmapConfig, assayGroupFactors, profiles, geneSetProfiles) {
        var heatmap = heatmapModule.build(heatmapConfig, new EventEmitter(), $('#displayLevels'));

        React.renderComponent(heatmap.Baseline( {assayGroupFactors:assayGroupFactors, profiles:profiles, geneSetProfiles: geneSetProfiles} ),
            document.getElementById('heatmap-react')
        );

        React.renderComponent(heatmap.EnsemblBrowser(),
                document.getElementById('${hasAnatomogram ? "anatomogram-ensembl-browser" : "ensembl-browser"}')
        );

    })(jQuery, React, heatmapModule, heatmapData.config,
            heatmapData.assayGroupFactors, heatmapData.profiles, heatmapData.geneSetProfiles);
</script>

