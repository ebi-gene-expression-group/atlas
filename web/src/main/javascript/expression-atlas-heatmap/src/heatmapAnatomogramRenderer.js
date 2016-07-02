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
 * @param {string}          options.proxyPrefix - Proxy URL with protocol: required by CTTV
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

    var protocol = window.location.protocol + "//",
        atlasHost = options.atlasHost === undefined ? "www.ebi.ac.uk" : options.atlasHost,
        atlasPath = "/gxa";

    var linksAtlasBaseURL =
        (atlasHost.indexOf("http://") === 0 || atlasHost.indexOf("https://") === 0) ? atlasHost + atlasPath :
        protocol + atlasHost + atlasPath;

    var atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;

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
