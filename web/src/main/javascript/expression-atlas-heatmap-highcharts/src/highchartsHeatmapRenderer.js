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
 * @param {string}          options.sourceURL - Where to source the data from
 * *                        e.g. /json/experiments/E-PROT-1, /json/genes/ENSG00000005801, /json/genesets/GO:0000001 or a widget endpoint
 * @param {string}          options.params - Alternate way of sourcing data if you do not provide the sourceURL
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

    //If using this renderer for a standalone widget, see uk.ac.ebi.atlas.widget.HeatmapWidgetController.java for the source URL/params required
    var sourceURL = options.sourceURL ||
                      atlasBaseURL + "/widgets/heatmap"
                      + (options.isMultiExperiment? "/baselineAnalytics" : "/referenceExperiment")
                      + endpointPath + "?" + options.params;

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
                anatomogramEventEmitter:anatomogramEventEmitter,
                anatomogramDataEventEmitter: options.anatomogramDataEventEmitter
            }
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
