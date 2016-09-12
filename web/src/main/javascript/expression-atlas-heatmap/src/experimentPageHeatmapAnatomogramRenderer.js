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
 * @param {string}  options.proxyPrefix - optionally set as "http(s?)://" or to proxy URL
 * @param {string}  options.selfHosted - Set this as true if you want to host our content yourself, but have outwards links pointing to our original site
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
        ? ""
        : options.proxyPrefix || "https://")
      + atlasHost
      + atlasPath;

  var linksAtlasBaseURL = options.selfHosted? (options.proxyPrefix || "https://")+ "www.ebi.ac.uk/gxa": atlasBaseURL;
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
                pathToFolderWithBundledResources:linksAtlasBaseURL+"/resources/js-bundles",
                linksAtlasBaseURL: linksAtlasBaseURL
            }
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

};
