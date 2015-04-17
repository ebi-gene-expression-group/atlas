/* global require */

var DifferentialRouter = require('./differential-router.js');

module.exports = function(facetsData, diffResultsData, facetContainerId, resultsContainerId) {
    DifferentialRouter(
        document.getElementById(facetContainerId),
        document.getElementById(resultsContainerId),
        facetsData, diffResultsData);
};

