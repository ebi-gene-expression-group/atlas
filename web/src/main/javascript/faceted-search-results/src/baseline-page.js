"use strict";

//*------------------------------------------------------------------*

var HeatmapsRouter = require('./heatmapsRouter.jsx');

//*------------------------------------------------------------------*

module.exports = function(facetsData, facetContainerId, resultsContainerId) {
    HeatmapsRouter(
        document.getElementById(facetContainerId),
        document.getElementById(resultsContainerId),
        facetsData);
};
