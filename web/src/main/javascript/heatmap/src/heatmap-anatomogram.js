"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;
require('../lib/jquery.xdomainrequest.js');

//*------------------------------------------------------------------*

var heatmapModule = require('./heatmap.jsx');
var HeatmapContainer = require('./heatmap-container.jsx');
var anatomogramModule = require('./anatomogram-module.js');

//*------------------------------------------------------------------*

function drawHeatmap (data, targetElement, heatmapClass, heatmapBuilder, heatmapKey) {

    (function ($, React, HeatmapContainer,
               heatmapBuilder, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData, experimentData, heatmapKey) {

        $(document).ready(function () {
            // call this inside ready() so all scripts load first in IE8
            var Heatmap = heatmapBuilder(heatmapConfig).Heatmap;

            React.render(HeatmapContainer({Heatmap: Heatmap, isWidget: true, heatmapClass: heatmapClass, experiment: experimentData, anatomogram: anatomogramData,
                    columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles, heatmapKey: heatmapKey, heatmapConfig:heatmapConfig}),
                targetElement
            );

            // load anatomogram after heatmap is rendered so wiring works
            if (anatomogramData) {
                anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
                    anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene, heatmapKey);
            }
        });

    })($, React, HeatmapContainer,
       heatmapBuilder, data.config, data.columnHeaders, data.profiles, data.geneSetProfiles, data.anatomogram, data.experiment, heatmapKey);

};

//*------------------------------------------------------------------*

module.exports = function(opt) {

    var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
    var $targetElement = $(targetElement);

    var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? 'widgets/heatmap/multiExperiment' : 'widgets/heatmap/referenceExperiment';
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

        overrideContextRoot(data, opt.gxaBaseUrl);

        if (opt.isMultiExperiment) {
            drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildMultiExperiment, opt.heatmapKey);
        } else {
            drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildBaseline);
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
