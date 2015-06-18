/* global require */

var DifferentialRouter = require('./differential-router.js');

module.exports = function(facetsData, facetContainerId, resultsContainerId) {
    DifferentialRouter(
        document.getElementById(facetContainerId),
        document.getElementById(resultsContainerId),
        facetsData);
};

