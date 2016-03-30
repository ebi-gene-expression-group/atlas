"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var HighchartsHeatmapContainer = require('./highcharts-heatmap-container.jsx');

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
 */
module.exports = function(options) {

    var protocol = window.location.protocol + "//",
        atlasHost = options.atlasHost === undefined ? "www.ebi.ac.uk" : options.atlasHost,
        atlasPath = "/gxa";

    var linksAtlasBaseURL = protocol + atlasHost + atlasPath,
        atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;

    var endpointPath =  options.isMultiExperiment ? "/widgets/heatmap/baselineAnalytics" : "/widgets/heatmap/referenceExperiment";

    var sourceURL = atlasBaseURL + endpointPath + "?" + options.params;

    ReactDOM.render(
        React.createElement(
            HighchartsHeatmapContainer,
            {
                sourceURL: sourceURL,
                atlasBaseURL: atlasBaseURL,
                linksAtlasBaseURL: linksAtlasBaseURL,
                isWidget: options.isWidget === undefined ? true : options.isWidget,
                disableGoogleAnalytics: options.disableGoogleAnalytics === undefined ? false : options.disableGoogleAnalytics,
                fail: options.fail
            }
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
