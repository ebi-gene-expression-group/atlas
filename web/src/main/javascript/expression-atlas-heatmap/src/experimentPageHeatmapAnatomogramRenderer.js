"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./ExperimentPageHeatmapAnatomogramContainer.jsx');

var ExperimentTypes = require('./experimentTypes.js');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {Object} options.heatmapData
 * @param {boolean} options.isMultiExperiment
 * @param {boolean} options.isDifferential
 * @param {boolean} options.isProteomicsBaseline
 */
exports.render = function(options) {

    var heatmapConfig = options.heatmapData.config,
        columnHeaders = options.heatmapData.columnHeaders,
        nonExpressedColumnHeaders = options.heatmapData.nonExpressedColumnHeaders,
        multipleColumnHeaders = options.heatmapData.multipleColumnHeaders,
        profiles = options.heatmapData.profiles,
        jsonCoexpressions = options.heatmapData.jsonCoexpressions,
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
                profiles: profiles, jsonCoexpressions: jsonCoexpressions, geneSetProfiles: geneSetProfiles,
                atlasBaseURL: heatmapConfig.atlasHost + heatmapConfig.contextRoot, linksAtlasBaseURL: heatmapConfig.atlasHost + heatmapConfig.contextRoot
            }
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

};
