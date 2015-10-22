"use strict";

//*------------------------------------------------------------------*

var HeatmapAnatomogramContainer = require('./heatmap-anatomogram-container.jsx');

//*------------------------------------------------------------------*

function drawHeatmap (data, targetElement, isWidget, isMultiExperiment, heatmapKey) {
    var React = require('react');

    var heatmapConfig = data.config,
        columnHeaders = data.columnHeaders,
        profiles = data.profiles,
        geneSetProfiles = data.geneSetProfiles,
        anatomogramData = data.anatomogram,
        experimentData = data.experiment;

    var type = isMultiExperiment ? "isMultiExperiment" : "isBaseline";

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {   type: type, heatmapConfig: heatmapConfig,
                experiment: experimentData, isWidget: isWidget,
                anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles,
                geneSetProfiles: geneSetProfiles, heatmapKey: heatmapKey
            }
        ),
        targetElement
    );
}

//*------------------------------------------------------------------*

module.exports = function(opt) {
    var URI = require('urijs');

    var $ = require('jquery');
    var jQuery = $;
    require('../lib/jquery.xdomainrequest.js');

    // Proxy prefix required by CTTV
    var proxyPrefix = opt.hasOwnProperty("proxyPrefix") ? opt.proxyPrefix : "";
    var atlasHost = opt.hasOwnProperty("atlasHost") ? opt.atlasHost : "";
    if (atlasHost.indexOf("http://") != -1 || atlasHost.indexOf("https://") != -1) {
        atlasHost = URI(atlasHost).host();
    }
    var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';

    // Legacy parameter
    if (opt.hasOwnProperty("gxaBaseUrl")) {
            atlasHost = URI(opt.gxaBaseUrl).host();
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
        atlasBaseURL = "http://" + atlasHost + defaultAtlasPath;
    } else {
        atlasBaseURL = "http://" + defaultAtlasHost + defaultAtlasPath;
    }

    // linksContextRoot -> for links that take you to a new URL
    var linksAtlasBaseURL = "";
    if (atlasHost) {
        linksAtlasBaseURL = "http://" + atlasHost + defaultAtlasPath;
    } else {
        linksAtlasBaseURL = "http://" + defaultAtlasHost + defaultAtlasPath;
    }

    var url = atlasBaseURL + endpoint + "?" + opt.params;

    var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
    var $targetElement = $(targetElement);

    var httpRequest = {
        url: url,
        dataType: "json",
        method:"GET",
        beforeSend:function () {
            $targetElement.html("<img src='" + atlasBaseURL + "/resources/images/loading.gif' />");
        }
    };



    //$.ajax(httpRequest).done(function (data) {

    $.ajax(httpRequest).done(function (data) {
        var isWidget = opt.hasOwnProperty("isWidget") ? opt.isWidget : true;

        data.config.atlasBaseURL = atlasBaseURL;
        data.config.linksAtlasBaseURL = linksAtlasBaseURL;

        if (opt.isMultiExperiment) {
            drawHeatmap(data, targetElement, isWidget, opt.isMultiExperiment, opt.heatmapKey);
        } else {
            drawHeatmap(data, targetElement, isWidget, opt.isMultiExperiment);
        }

    }).fail(function (jqXHR, textStatus, errorThrown) {
        //containerDiv.html("An error occurred while retrieving the data: " + jqXHR.status + " - " + jqXHR.statusText);
        if (textStatus === "parsererror") {
            $targetElement.html("<div class='error'>Could not parse JSON response</div>");
        } else {
            $targetElement.html(jqXHR.responseText);
        }
    });
};
