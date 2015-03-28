/* global require */

var DifferentialRouter = require('./differential-router.js');

if (facetsData) {
    DifferentialRouter(document.getElementById('facets'),
        document.getElementById('results'), facetsData);
}
