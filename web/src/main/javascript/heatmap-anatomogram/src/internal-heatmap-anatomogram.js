"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./internal-heatmap-anatomogram-container.jsx');

var ExperimentTypes = require('./experiment-types.js');

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
        nonExpressedColumnHeaders = options.heatmapData.nonExpressedColumnHeaders,
        multipleColumnHeaders = options.heatmapData.multipleColumnHeaders,
        profiles = options.heatmapData.profiles,
        geneSetProfiles = options.heatmapData.geneSetProfiles,
        anatomogramData = options.heatmapData.anatomogram;

    var type =
        options.isMultiExperiment ? ExperimentTypes.MULTIEXPERIMENT :
            options.isDifferential ? ExperimentTypes.DIFFERENTIAL :
                options.isProteomicsBaseline ? ExperimentTypes.PROTEOMICS_BASELINE :
                    ExperimentTypes.BASELINE;

    ReactDOM.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {
                type: type, heatmapConfig: heatmapConfig, isWidget: false,
                anatomogram: anatomogramData, columnHeaders: columnHeaders, nonExpressedColumnHeaders: nonExpressedColumnHeaders,
                multipleColumnHeaders: multipleColumnHeaders,
                profiles: profiles, geneSetProfiles: geneSetProfiles,
                atlasBaseURL: "/gxa", linksAtlasBaseURL: "/gxa"
            }
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

}

//*------------------------------------------------------------------*

module.exports = drawHeatmap;