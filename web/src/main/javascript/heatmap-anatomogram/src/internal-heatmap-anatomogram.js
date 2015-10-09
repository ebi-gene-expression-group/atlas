"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

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

    heatmapConfig.atlasBaseURL = "/gxa";
    heatmapConfig.linksAtlasBaseURL = "/gxa";

    var type =
        isMultiExperiment ? "isMultiExperiment" :
            isDifferential ? "isDifferential" :
                isProteomicsBaseline ? "isProteomics" : "isBaseline";

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {   type: type, heatmapConfig: heatmapConfig, isWidget: false,
                anatomogram: anatomogramData, columnHeaders: columnHeaders, multipleColumnHeaders: multipleColumnHeaders,
                profiles: profiles, geneSetProfiles: geneSetProfiles}
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

}

//*------------------------------------------------------------------*

module.exports = drawHeatmap;