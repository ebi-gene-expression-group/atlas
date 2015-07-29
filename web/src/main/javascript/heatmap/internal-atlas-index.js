"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;

require('./lib/jquery.hc-sticky.js');

//*------------------------------------------------------------------*

var heatmapBuild = require('./src/heatmap.jsx');

//*------------------------------------------------------------------*

function drawHeatmapContainer (heatmapData, isMultiExperiment, isDifferential, isProteomicsBaseline) {

    (function ($, React, isMultiExperiment, isDifferential, isProteomicsBaseline, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData) {

        $(document).ready(function () {
            // Call this inside ready() so all scripts load first in IE8

            var heatmapModuleBuild = isMultiExperiment ? heatmapBuild.buildMultiExperiment : (isDifferential ? heatmapBuild.buildDifferential
                : (isProteomicsBaseline ? heatmapBuild.buildProteomicsBaseline : heatmapBuild.buildBaseline));

            var heatmap = heatmapModuleBuild(heatmapConfig, $('#displayLevels'));

            React.render(
                React.createElement(
                    heatmap.Heatmap, {columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles}
                ),
                document.getElementById('heatmap-react')
            );

            if (heatmap.EnsemblLauncher) {
                React.render(
                    React.createElement(
                        heatmap.EnsemblLauncher
                    ),
                    document.getElementById(anatomogramData ? "anatomogram-ensembl-launcher" : "ensembl-launcher"));
            }

            // Load anatomogram after heatmap is rendered so wiring works
            if (anatomogramData) {
                anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
                    anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene);
            } else {
                $("#anatomogram").remove();
            }

            $('#anatomogram').hcSticky({responsive: true});
            $('#ensembl-launcher').hcSticky({responsive: true});
        });

    })($, React, isMultiExperiment, isDifferential, isProteomicsBaseline, heatmapData.config, heatmapData.columnHeaders, heatmapData.profiles, heatmapData.geneSetProfiles, heatmapData.anatomogram);
}

module.exports = drawHeatmapContainer;
