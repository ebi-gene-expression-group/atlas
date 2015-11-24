"use strict";

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./heatmap-anatomogram-container.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object}  options
 * @param {Object}  options.data
 * @param {Object}  options.targetElement
 * @param {boolean} options.isWidget
 * @param {boolean} options.isMultiExperiment
 * @param {string}  options.heatmapKey
 * @param {boolean} options.showAnatomogram
 * @param {boolean} options.showAnatomogramLabel
 */
function drawHeatmap (options) {
    var React = require('react');

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
            {   type: type, heatmapConfig: heatmapConfig,
                experiment: experimentData, isWidget: options.isWidget,
                anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles,
                geneSetProfiles: geneSetProfiles, heatmapKey: options.heatmapKey,
                showAnatomogram: options.showAnatomogram, showAnatomogramLabel: options.showAnatomogramLabel
            }
        ),
        options.targetElement
    );
}

//*------------------------------------------------------------------*

/**
 * @param {Object}  options
 * @param {string}  options.gxaBaseUrl - deprecated
 * @param {string}  options.params
 * @param {boolean} options.isMultiExperiment
 * @param {string}  options.target
 * @param {string}  options.heatmapUrl
 * @param {string}  options.heatmapKey
 * @param {boolean} options.isWidget
 * @param {string}  options.proxyPrefix - only used by CTTV
 * @param {string}  options.atlasHost
 * @param {boolean} options.showAnatomogram
 * @param {boolean} options.showAnatomogramLabel
 */
module.exports = function(options) {
    var URI = require('urijs');

    var $ = require('jquery');
    var jQuery = $;
    require('../lib/jquery.xdomainrequest.js');

    var showAnatomogramLabel = options.hasOwnProperty("showAnatomogramLabel") ? options.showAnatomogramLabel : false;

    // Proxy prefix required by CTTV
    var proxyPrefix = options.hasOwnProperty("proxyPrefix") ? options.proxyPrefix : "";

    // Atlas host with protocol
    var atlasHost = options.hasOwnProperty("atlasHost") ? options.atlasHost : "";
    var protocol = "";
    if (atlasHost.indexOf("http://") != -1) {
        protocol = "http://";
        atlasHost = URI(atlasHost).host();
    } else if (atlasHost.indexOf("https://") != -1) {
        protocol = "https://";
        atlasHost = URI(atlasHost).host();
    } else {
        protocol = "http";
    }
    var endpointPath = options.heatmapUrl ? options.heatmapUrl : options.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';

    // Legacy parameter
    if (options.hasOwnProperty("gxaBaseUrl")) {
        atlasHost = URI(options.gxaBaseUrl).host();
    }

    // contextRoot -> for AJAX requests, images, etc.
    var defaultAtlasHost = "www.ebi.ac.uk",
        defaultAtlasPath = "/gxa";

    var atlasBaseURL = "";
    if (proxyPrefix) {
        if (atlasHost) {
            atlasBaseURL = proxyPrefix + "/" + atlasHost + defaultAtlasPath;
        } else {
            atlasBaseURL = proxyPrefix + "/" + defaultAtlasHost + defaultAtlasPath;
        }
    } else if (atlasHost) {
        atlasBaseURL = protocol + atlasHost + defaultAtlasPath;
    } else {
        atlasBaseURL = protocol + defaultAtlasHost + defaultAtlasPath;
    }

    // linksContextRoot -> for links that take you to a new URL
    var linksAtlasBaseURL = "";
    if (atlasHost) {
        linksAtlasBaseURL = "https://" + atlasHost + defaultAtlasPath;
    } else {
        linksAtlasBaseURL = "https://" + defaultAtlasHost + defaultAtlasPath;
    }

    var url = atlasBaseURL + endpointPath + "?" + options.params;

    var targetElement = (typeof options.target == 'string') ? document.getElementById(options.target) : options.target;
    var $targetElement = $(targetElement);

    var httpRequest = {
        url: url,
        dataType: "json",
        method:"GET",
        beforeSend:function () {
            $targetElement.html("<img src='" + atlasBaseURL + "/resources/images/loading.gif'/>");
        }
    };


    $.ajax(httpRequest).done(function (data) {
        var isWidget = options.hasOwnProperty("isWidget") ? options.isWidget : true;

        data.config.atlasBaseURL = atlasBaseURL;
        data.config.linksAtlasBaseURL = linksAtlasBaseURL;

        if (options.isMultiExperiment) {
            drawHeatmap({data: data, targetElement: targetElement, isWidget: isWidget, isMultiExperiment: options.isMultiExperiment, heatmapKey: options.heatmapKey, showAnatomogram: options.showAnatomogram, showAnatomogramLabel: showAnatomogramLabel});
        } else {
            drawHeatmap({data: data, targetElement: targetElement, isWidget: isWidget, isMultiExperiment: options.isMultiExperiment, heatmapKey: "", showAnatomogram: options.showAnatomogram, showAnatomogramLabel: showAnatomogramLabel});
        }

    }).fail(function (jqXHR, textStatus, errorThrown) {
        if (textStatus === "parsererror") {
            $targetElement.html("<div class='error'>Could not parse JSON response</div>");
        } else {
            $targetElement.html(jqXHR.responseText);
        }
    });
};
