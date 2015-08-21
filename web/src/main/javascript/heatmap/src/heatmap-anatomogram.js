"use strict";

//*------------------------------------------------------------------*

var URI = require('URIjs');

//*------------------------------------------------------------------*

function drawHeatmap (data, targetElement, heatmapBuilder, isWidget, heatmapKey) {

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
            {Heatmap: Heatmap, isWidget: isWidget, experiment: experimentData,
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

    var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
    var $targetElement = $(targetElement);

    var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';
    var url = opt.gxaBaseUrl + endpoint + '?' + opt.params;

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

        function overrideContextRoot(data, gxaBaseUrl) {
            data.config.contextRoot = gxaBaseUrl;

            if (data.anatomogram) {
                data.anatomogram.contextRoot = gxaBaseUrl;
            }
            if (data.experiment) {
                data.experiment.contextRoot = gxaBaseUrl;
            }
        }

        var isWidget = opt.hasOwnProperty("isWidget") ? opt.isWidget : true;

        overrideContextRoot(data, opt.gxaBaseUrl);

        if (opt.isMultiExperiment) {
            drawHeatmap(data, targetElement, heatmapModule.buildMultiExperiment, isWidget, opt.heatmapKey);
        } else {
            drawHeatmap(data, targetElement, heatmapModule.buildBaseline, isWidget);
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
