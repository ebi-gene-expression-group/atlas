"use strict";

//*------------------------------------------------------------------*

var URI = require('urijs');

//*------------------------------------------------------------------*

function drawHeatmap (data, targetElement, heatmapBuilder, isWidget, isMultiExperiment, heatmapKey) {

    var React = require('react');

    var HeatmapAnatomogramContainer = require('./heatmap-anatomogram-container.jsx');

    var heatmapConfig = data.config,
        columnHeaders = data.columnHeaders,
        profiles = data.profiles,
        geneSetProfiles = data.geneSetProfiles,
        anatomogramData = data.anatomogram,
        experimentData = data.experiment;

    var Heatmap = heatmapBuilder(heatmapConfig).Heatmap;

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {Heatmap: Heatmap, isWidget: isWidget, isMultiExperiment: isMultiExperiment, experiment: experimentData,
             anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles,
             geneSetProfiles: geneSetProfiles, heatmapKey: heatmapKey, heatmapConfig: heatmapConfig
            }
        ),
        targetElement
    );
}

//*------------------------------------------------------------------*

module.exports = function(opt) {

    var heatmapModule = require('./heatmap.jsx');
    var $ = require('jquery');
    require('../lib/jquery.xdomainrequest.js');

    // Use proxy if set (required by CTTV), and used by AJAX calls (JSON endpoint, anatomogram and tooltips), not used
    // for URLs and redirections (e.g. footer and gene/experiment links)
    var proxyPrefix = opt.hasOwnProperty("proxyPrefix") ? opt.proxyPrefix : "";
    var atlasHost = opt.hasOwnProperty("atlasHost") ? opt.atlasHost : "";
    if (atlasHost.startsWith("http://") || atlasHost.startsWith("https://")) {
        atlasHost = URI(atlasHost).host();
    }
    var gxaBaseUrl = "/gxa";
    var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';

    // Legacy parameter
    if (opt.hasOwnProperty("gxaBaseUrl")) {
        atlasHost = URI(opt.gxaBaseUrl).host();
    }

    var url = "";
    if (proxyPrefix) {
        url = proxyPrefix + "/" + atlasHost + gxaBaseUrl + endpoint + "?" + opt.params;
    } else {
        url = "http://" + atlasHost + gxaBaseUrl + endpoint + "?" + opt.params;
    }

    var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
    var $targetElement = $(targetElement);

    var httpRequest = {
        url: url,
        dataType: "json",
        method:"GET",
        beforeSend:function () {
            // TODO: nasty workaround for http://youtrack.jetbrains.com/issue/IDEA-25934 (still not fixed)
            var resource_host = ("${resources.host}" === "\${resources.host}") ? "wwwdev.ebi.ac.uk" : "${resources.host}";
            $targetElement.html("<img src='http://" + resource_host + "/gxa/resources/images/loading.gif' />");
        }
    };

    $.ajax(httpRequest).done(function (data) {

        var isWidget = opt.hasOwnProperty("isWidget") ? opt.isWidget : true;

        data.config.proxyPrefix = proxyPrefix;
        data.config.atlasHost = atlasHost;
        data.config.gxaBaseUrl = gxaBaseUrl;
        if (data.anatomogram) {
            data.anatomogram.proxyPrefix = proxyPrefix;
            data.anatomogram.atlasHost = atlasHost;
            data.anatomogram.gxaBaseUrl = gxaBaseUrl;
        }
        if (data.experiment) {
            data.experiment.proxyPrefix = proxyPrefix;
            data.experiment.atlasHost = atlasHost;
            data.experiment.gxaBaseUrl = gxaBaseUrl;
        }

        if (opt.isMultiExperiment) {
            drawHeatmap(data, targetElement, heatmapModule.buildMultiExperiment, isWidget, opt.isMultiExperiment, opt.heatmapKey);
        } else {
            drawHeatmap(data, targetElement, heatmapModule.buildBaseline, isWidget, opt.isMultiExperiment);
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
