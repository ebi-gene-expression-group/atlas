"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

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

  var protocol = window.location.protocol + "//",
      atlasHost = options.atlasHost === undefined ? "www.ebi.ac.uk" : options.atlasHost,
      atlasPath = "/gxa";

  var linksAtlasBaseURL =
      (atlasHost.indexOf("http://") === 0 || atlasHost.indexOf("https://") === 0) ? atlasHost + atlasPath :
      protocol + atlasHost + atlasPath;

  var atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;

  var endpointPath =
      options.analyticsSearch ? "/widgets/heatmap/baselineAnalytics" :
          options.isMultiExperiment ? "/widgets/heatmap/multiExperiment" : "/widgets/heatmap/referenceExperiment";

  var sourceURL = atlasBaseURL + endpointPath + "?" + options.params;

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
                linksAtlasBaseURL: linksAtlasBaseURL
            }
        ),
        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
    );

};
