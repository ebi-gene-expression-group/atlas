"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var EventEmitter = require('events');

var HeatmapAnatomogramContainer = require('./HeatmapAnatomogramContainer.jsx');

var ExperimentTypes = require('./experimentTypes.js');

//*------------------------------------------------------------------*

/**
 * @param {Object}      options
 * @param {string}          options.proxyPrefix - optionally set as "http(s?)://" or to proxy URL
 * @param {string}          options.selfHosted - Set this as true if you want to host our content yourself, but have outwards links pointing to our original site
 * @param {boolean=}        options.disableGoogleAnalytics - Disable Google Analytics: required by CTTV
 * @param {string=}         options.atlasHost - Atlas host with port (note: don’t include port)
 * @param {string}          options.params
 * @param {boolean=}        options.isMultiExperiment
 * @param {boolean=}        options.showAnatomogram
 * @param {boolean=}        options.isWidget
 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
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

    var endpointPath = options.isMultiExperiment ? "/widgets/heatmap/baselineAnalytics" : "/widgets/heatmap/referenceExperiment";

    var sourceURL = atlasBaseURL + endpointPath + "?" + options.params;

    var anatomogramEventEmitter = new EventEmitter();
    anatomogramEventEmitter.setMaxListeners(0);

    ReactDOM.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {   key: JSON.stringify(options.params),
                sourceURL: sourceURL,
                atlasBaseURL: atlasBaseURL,
                pathToFolderWithBundledResources:linksAtlasBaseURL+"/resources/js-bundles",
                linksAtlasBaseURL: linksAtlasBaseURL,
                type: options.isMultiExperiment ? ExperimentTypes.MULTIEXPERIMENT : ExperimentTypes.BASELINE,
                showAnatomogram: options.showAnatomogram === undefined ? true : options.showAnatomogram,
                isWidget: options.isWidget === undefined ? true : options.isWidget,
                disableGoogleAnalytics: options.disableGoogleAnalytics === undefined ? false : options.disableGoogleAnalytics,
                fail: options.fail,
                anatomogramEventEmitter: anatomogramEventEmitter
            }
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
