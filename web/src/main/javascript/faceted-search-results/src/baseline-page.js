"use strict";

//*------------------------------------------------------------------*

var HeatmapsRouter = require('./baseline-heatmaps-router.jsx');

//*------------------------------------------------------------------*

module.exports = function(facetsData, facetContainerId, resultsContainerId) {
    HeatmapsRouter(
        document.getElementById(facetContainerId),
        document.getElementById(resultsContainerId),
        facetsData);
};
