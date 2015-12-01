"use strict";

var React = require('react');

var $ = require('jquery');
var jQuery = $;
require('../lib/jquery.xdomainrequest.js');

var URI = require('urijs');

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./heatmap-anatomogram-container.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object}   options
 * @param {Object}   options.data
 * @param {Object}   options.targetElement
 * @param {boolean}  options.isWidget
 * @param {boolean}  options.isMultiExperiment
 * @param {string}   options.heatmapKey
 * @param {boolean}  options.showAnatomogram
 * @param {boolean=} options.disableGoogleAnalytics
 */
function drawHeatmap (options) {
    var heatmapConfig = options.data.config,
        columnHeaders = options.data.columnHeaders,
        profiles = options.data.profiles,
        geneSetProfiles = options.data.geneSetProfiles,
        anatomogramData = options.data.anatomogram,
        experimentData = options.data.experiment;

    var type = options.isMultiExperiment ? "isMultiExperiment" : "isBaseline";

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {
                type: type, heatmapConfig: heatmapConfig,
                experiment: experimentData, isWidget: options.isWidget,
                anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles,
                geneSetProfiles: geneSetProfiles, heatmapKey: options.heatmapKey,
                showAnatomogram: options.showAnatomogram,
                disableGoogleAnalytics: options.disableGoogleAnalytics
            }
        ),
        options.targetElement
    );
}

//*------------------------------------------------------------------*

/**
 * @param {Object}   options
 * @param {string}   options.params
 * @param {boolean}  options.isMultiExperiment
 * @param {string}   options.heatmapUrl
 * @param {string}   options.heatmapKey
 * @param {boolean}  options.isWidget
 * @param {boolean=} options.showAnatomogram
 * @param {string}   options.proxyPrefix - Proxy URL with protocol: required by CTTV
 * @param {boolean=} options.disableGoogleAnalytics - Disable Google Analytics: required by CTTV
 * @param {string}   options.atlasHost - Atlas host with port (note: don’t include port)
 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
 */
module.exports = function(options) {
    var targetElement = (typeof options.target == 'string') ? document.getElementById(options.target) : options.target,
        $targetElement = $(targetElement),
        showAnatomogram = options.showAnatomogram === undefined ? true : options.showAnatomogram,
        isWidget = options.hasOwnProperty("isWidget") ? options.isWidget : true;


    // Begin parse URL
    var protocol = window.location.protocol + "//",
        atlasHost = options.atlasHost ? options.atlasHost : "www.ebi.ac.uk",
        atlasPath = "/gxa",
        endpointPath = options.heatmapUrl ? options.heatmapUrl : options.isMultiExperiment ? "/widgets/heatmap/multiExperiment" : "/widgets/heatmap/referenceExperiment";

    var linksAtlasBaseURL = protocol + atlasHost + atlasPath, // For links that take you to a new URL
        atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;

    var url = atlasBaseURL + endpointPath + "?" + options.params;
    // End parse URL


    var httpRequest = {
        url: url,
        dataType: "json",
        method:"GET",
        beforeSend:function () {
            $targetElement.html("<img src='" + atlasBaseURL + "/resources/images/loading.gif'/>");
        }
    };

    $.ajax(httpRequest).done(function (data) {
        data.config.atlasBaseURL = atlasBaseURL;
        data.config.linksAtlasBaseURL = linksAtlasBaseURL;

        if (options.isMultiExperiment) {
            drawHeatmap({data: data, targetElement: targetElement, isWidget: isWidget, isMultiExperiment: options.isMultiExperiment, heatmapKey: options.heatmapKey, showAnatomogram: showAnatomogram, disableGoogleAnalytics: options.disableGoogleAnalytics});
        } else {
            drawHeatmap({data: data, targetElement: targetElement, isWidget: isWidget, isMultiExperiment: options.isMultiExperiment, heatmapKey: "", showAnatomogram: showAnatomogram, disableGoogleAnalytics: options.disableGoogleAnalytics});
        }
    }).fail(function (jqXHR, textStatus) {
        if (textStatus === "parsererror") {
            $targetElement.html("<div class='error'>Could not parse JSON response</div>");
        } else {
            $targetElement.html(jqXHR.responseText);
        }
    });
};
