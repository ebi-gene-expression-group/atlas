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

    var heatmapBuilder =
        isMultiExperiment ? heatmapModule.buildMultiExperiment :
            (isDifferential ? heatmapModule.buildDifferential :
                (isProteomicsBaseline ? heatmapModule.buildProteomicsBaseline :
                    heatmapModule.buildBaseline));
    var Heatmap = heatmapBuilder(heatmapConfig, $('#displayLevels')).Heatmap;

    heatmapConfig.proxyPrefix = "";
    anatomogramData.proxyPrefix = "";

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