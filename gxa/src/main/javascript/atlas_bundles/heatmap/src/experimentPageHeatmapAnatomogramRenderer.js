"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./ExperimentPageHeatmapAnatomogramContainer.jsx');

var ExperimentTypes = require('./experimentTypes.js');
//*------------------------------------------------------------------*

/**
 * @param {Object}  options
 * @param {string}  options.atlasHost
 * @param {string}  options.sourceURL
 * @param {boolean} options.isMultiExperiment
 * @param {boolean} options.isDifferential
 * @param {boolean} options.isProteomicsBaseline
 */
exports.render = function(options) {

  var atlasHost = options.atlasHost === undefined ? "https://www.ebi.ac.uk" : options.atlasHost,
      atlasPath = "/gxa";

  var atlasBaseURL =
      (atlasHost.indexOf("http://") === 0 || atlasHost.indexOf("https://") === 0
        ? "" : "https://")
      + atlasHost
      + atlasPath;

  var linksAtlasBaseURL = atlasBaseURL;
    var type =
        options.isMultiExperiment ? ExperimentTypes.MULTIEXPERIMENT :
            options.isDifferential ? ExperimentTypes.DIFFERENTIAL :
                options.isProteomicsBaseline ? ExperimentTypes.PROTEOMICS_BASELINE :
                    ExperimentTypes.BASELINE;

    ReactDOM.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {
                type: type,
                sourceURL: options.sourceURL,
                atlasBaseURL: atlasBaseURL,
                pathToFolderWithBundledResources:linksAtlasBaseURL+"/resources/js-bundles/",
                linksAtlasBaseURL: linksAtlasBaseURL
            }
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

};
