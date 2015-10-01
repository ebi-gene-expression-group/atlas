"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var CellDifferential = require('../src/CellDifferential.jsx');

//*------------------------------------------------------------------*

exports.render = function(colour, foldChange, pValue, tStat, displayLevels, mountNode) {
    React.render(
        React.createElement(
            CellDifferential, {colour: colour, foldChange: foldChange, pValue: pValue, tStat: tStat, displayLevels: displayLevels}
        ),
        mountNode
    );
};
