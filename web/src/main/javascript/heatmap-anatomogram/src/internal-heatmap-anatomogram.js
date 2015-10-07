"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var heatmapModule = require('./heatmap.jsx');
var HeatmapAnatomogramContainer = require('./internal-heatmap-anatomogram-container.jsx');

//*------------------------------------------------------------------*

function drawHeatmap (heatmapData, isMultiExperiment, isDifferential, isProteomicsBaseline) {

    var heatmapConfig = heatmapData.config,
        columnHeaders = heatmapData.columnHeaders,
        multipleColumnHeaders = heatmapData.multipleColumnHeaders,
        profiles = heatmapData.profiles,
        geneSetProfiles = heatmapData.geneSetProfiles,
        anatomogramData = heatmapData.anatomogram;

    var isBaseline = (!isMultiExperiment && !isDifferential && !isProteomicsBaseline);

    var proxyPrefix = "";
    var atlasHost = "";
    var gxaBaseUrl = "/gxa";

    heatmapConfig.proxyPrefix = proxyPrefix;
    heatmapConfig.atlasHost = atlasHost;
    heatmapConfig.gxaBaseUrl = gxaBaseUrl;
    if (anatomogramData) {
        anatomogramData.proxyPrefix = proxyPrefix;
        anatomogramData.atlasHost = atlasHost;
        anatomogramData.gxaBaseUrl = gxaBaseUrl;
    }

    var heatmapBuilder =
        isMultiExperiment ? heatmapModule.buildMultiExperiment :
            (isDifferential ? heatmapModule.buildDifferential :
                (isProteomicsBaseline ? heatmapModule.buildProteomicsBaseline :
                    heatmapModule.buildBaseline));
    var Heatmap = heatmapBuilder(heatmapConfig, $('#displayLevels')).Heatmap;

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {Heatmap: Heatmap,
             anatomogram: anatomogramData, columnHeaders: columnHeaders, multipleColumnHeaders: multipleColumnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles, heatmapConfig: heatmapConfig,
             isBaseline: isBaseline, isMultiExperiment: isMultiExperiment, isWidget: false}
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

}

//*------------------------------------------------------------------*

module.exports = drawHeatmap;