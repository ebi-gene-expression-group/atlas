"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var EventEmitter = require('events');

var HighchartsHeatmapContainer = require('./HighchartsHeatmapContainer.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object}      options
 * @param {string}          options.proxyPrefix - optionally set as "http(s?)://" or to proxy URL
 * @param {string}          options.selfHosted - Set this as true if you want to host our content yourself, but have outwards links pointing to our original site
 * @param {boolean=}        options.disableGoogleAnalytics - Disable Google Analytics
 * @param {string=}         options.atlasHost - Atlas host with protocol and port
 * @param {string}          options.sourceURL - Where to source the data from
 * *                        e.g. /json/experiments/E-PROT-1, /json/genes/ENSG00000005801, /json/genesets/GO:0000001 or a widget endpoint
 * @param {string}          options.params - Alternate way of sourcing data if you do not provide the sourceURL
 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
 * @param {Object}          options.anatomogramDataEventEmitter - emits events to the facets tree to signal the existence of anatomogram
 * @param {boolean=}        options.showAnatomogram - optionally hide the anatomogram
 * @param {boolean}         options.isDifferential
 * @param {boolean}         options.isMultiExperiment
 * @param {boolean=}        options.isWidget
 * @param {string}          options.pathToFolderWithBundledResources - use if you're serving the /svg's from an unusual location. Development only.
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

    //If using this renderer for a standalone widget, see uk.ac.ebi.atlas.widget.HeatmapWidgetController.java for the source URL/params required
    var sourceURL = options.sourceURL ||
                      atlasBaseURL + "/widgets/heatmap"
                      + (options.isMultiExperiment? "/baselineAnalytics" : "/referenceExperiment")
                      + "?" + options.params;

    var anatomogramEventEmitter = new EventEmitter();
    anatomogramEventEmitter.setMaxListeners(0);

    ReactDOM.render(
        React.createElement(
            HighchartsHeatmapContainer,
            {
                sourceURL: sourceURL,
                atlasBaseURL: atlasBaseURL,
                linksAtlasBaseURL: linksAtlasBaseURL,
                pathToFolderWithBundledResources: options.pathToFolderWithBundledResources || linksAtlasBaseURL + "/resources/js-bundles/",
                showAnatomogram: options.showAnatomogram === undefined || options.showAnatomogram,
                isDifferential: !!options.isDifferential,
                isMultiExperiment: !!options.isMultiExperiment,
                isWidget: options.isWidget === undefined || options.isWidget,
                disableGoogleAnalytics: !!options.disableGoogleAnalytics,
                fail: options.fail,
                anatomogramEventEmitter:anatomogramEventEmitter,
                anatomogramDataEventEmitter: options.anatomogramDataEventEmitter
            }
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
