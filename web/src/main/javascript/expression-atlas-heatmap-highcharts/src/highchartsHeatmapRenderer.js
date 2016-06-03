"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var EventEmitter = require('events');

var HighchartsHeatmapContainer = require('./HighchartsHeatmapContainer.jsx');
    
//*------------------------------------------------------------------*

/**
 * @param {Object}      options
 * @param {string}          options.proxyPrefix - Proxy URL with protocol: required by CTTV
 * @param {boolean=}        options.disableGoogleAnalytics - Disable Google Analytics: required by CTTV
 * @param {string=}         options.atlasHost - Atlas host with port (note: don’t include port)
 * @param {string}          options.params
 * @param {boolean}         options.analyticsSearch
 * @param {boolean=}        options.isMultiExperiment
 * @param {boolean=}        options.showAnatomogram
 * @param {boolean=}        options.isWidget
 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
 * @param {function}        options.eventEmitter
 */
exports.render = function(options) {

    var protocol = window.location.protocol + "//",
        atlasHost = options.atlasHost === undefined ? "www.ebi.ac.uk" : options.atlasHost,
        atlasPath = "/gxa";

    var linksAtlasBaseURL = protocol + atlasHost + atlasPath,
        atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;

    var endpointPath =  options.isMultiExperiment ? "/widgets/heatmap/baselineAnalytics" : "/widgets/heatmap/referenceExperiment";

    var sourceURL = atlasBaseURL + endpointPath + "?" + options.params;

    var ensemblEventEmitter = new EventEmitter();
    ensemblEventEmitter.setMaxListeners(0);
    var anatomogramEventEmitter = new EventEmitter();
    anatomogramEventEmitter.setMaxListeners(0);

    ReactDOM.render(
        React.createElement(
            HighchartsHeatmapContainer,
            {
                sourceURL: sourceURL,
                atlasBaseURL: atlasBaseURL,
                linksAtlasBaseURL: linksAtlasBaseURL,
                showAnatomogram: options.showAnatomogram === undefined ? true : options.showAnatomogram,
                isWidget: options.isWidget === undefined ? true : options.isWidget,
                isMultiExperiment: options.isMultiExperiment,
                disableGoogleAnalytics: options.disableGoogleAnalytics === undefined ? false : options.disableGoogleAnalytics,
                fail: options.fail,
                ensemblEventEmitter: ensemblEventEmitter,
                anatomogramEventEmitter:anatomogramEventEmitter,
                eventEmitter: options.eventEmitter
            }
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
