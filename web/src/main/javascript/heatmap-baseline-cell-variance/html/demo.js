"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var HeatmapBaselineCellVariance = require('../src/HeatmapBaselineCellVariance.jsx');

//*------------------------------------------------------------------*

exports.render = function(quartiles, mountNode) {

    React.render(
        React.createElement(HeatmapBaselineCellVariance, {quartiles: quartiles}), mountNode
    );
};
