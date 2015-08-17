"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var heatmapModule = require('./heatmap.jsx');
var HeatmapAnatomogramContainer = require('./internal-heatmap-anatomogram-container.jsx');
var anatomogramModule = require('./anatomogram-module.js');

//*------------------------------------------------------------------*

function drawHeatmap (heatmapData, isMultiExperiment, isDifferential, isProteomicsBaseline) {

    var heatmapConfig = heatmapData.config,
        columnHeaders = heatmapData.columnHeaders,
        profiles = heatmapData.profiles,
        geneSetProfiles = heatmapData.geneSetProfiles,
        anatomogramData = heatmapData.anatomogram;


    var heatmapBuilder =
        isMultiExperiment ? heatmapModule.buildMultiExperiment :
            (isDifferential ? heatmapModule.buildDifferential :
                (isProteomicsBaseline ? heatmapModule.buildProteomicsBaseline :
                    heatmapModule.buildBaseline));

    var heatmapAndEnsemblLauncher = heatmapBuilder(heatmapConfig, $('#displayLevels'));
    var Heatmap = heatmapAndEnsemblLauncher.Heatmap;
    var EnsemblLauncher = heatmapAndEnsemblLauncher.EnsemblLauncher;

    React.render(
        React.createElement(
            HeatmapAnatomogramContainer,
            {Heatmap: Heatmap, EnsemblLauncher: EnsemblLauncher,
             anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles, heatmapConfig: heatmapConfig,
             isWidget: false}
        ),
        document.getElementById("lolapalooza")
    );

    if (anatomogramData) {
        anatomogramModule(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
            anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene);
    }
}

//*------------------------------------------------------------------*

module.exports = drawHeatmap;