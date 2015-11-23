"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./internal-heatmap-anatomogram-container.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {Object} options.heatmapData
 * @param {boolean} options.isMultiExperiment
 * @param {boolean} options.isDifferential
 * @param {boolean} options.isProteomicsBaseline
 */
function drawHeatmap (options) {

    var heatmapConfig = options.heatmapData.config,
        columnHeaders = options.heatmapData.columnHeaders,
        multipleColumnHeaders = options.heatmapData.multipleColumnHeaders,
        profiles = options.heatmapData.profiles,
        geneSetProfiles = options.heatmapData.geneSetProfiles,
        anatomogramData = options.heatmapData.anatomogram;

    var isBaseline = (!options.isMultiExperiment && !options.isDifferential && !options.isProteomicsBaseline);

    heatmapConfig.atlasBaseURL = "/gxa";
    heatmapConfig.linksAtlasBaseURL = "/gxa";

    var type =
        options.isMultiExperiment ? "isMultiExperiment" :
            options.isDifferential ? "isDifferential" :
                options.isProteomicsBaseline ? "isProteomics" : "isBaseline";

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