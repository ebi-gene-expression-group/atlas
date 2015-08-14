webpackJsonp([2],{

/***/ 0:
/*!*****************************************!*\
  !*** ./heatmap/internal-atlas-index.js ***!
  \*****************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["exposed"] = __webpack_require__(/*! -!./heatmap/internal-atlas-index.js */ 542);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },

/***/ 542:
/*!*****************************************!*\
  !*** ./heatmap/internal-atlas-index.js ***!
  \*****************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 4);
	var $ = __webpack_require__(/*! jquery */ 153);
	var jQuery = $;
	
	__webpack_require__(/*! ./lib/jquery.hc-sticky.js */ 156);
	
	//*------------------------------------------------------------------*
	
	var heatmapBuild = __webpack_require__(/*! ./src/heatmap.jsx */ 3);
	var anatomogramModule = __webpack_require__(/*! ./src/anatomogram-module.js */ 372);
	
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
	            var $anatomogram = $("#anatomogram");
	            var $ensemblLauncher = $('#ensembl-launcher');
	
	            if (anatomogramData) {
	                anatomogramModule(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
	                    anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene);
	            } else {
	                $anatomogram.remove();
	            }
	
	            $anatomogram.hcSticky({responsive: true});
	            $ensemblLauncher.hcSticky({responsive: true});
	        });
	
	    })($, React, isMultiExperiment, isDifferential, isProteomicsBaseline, heatmapData.config, heatmapData.columnHeaders, heatmapData.profiles, heatmapData.geneSetProfiles, heatmapData.anatomogram);
	}
	
	module.exports = drawHeatmapContainer;


/***/ }

});
//# sourceMappingURL=internal-atlas-heatmap.bundle.js.map