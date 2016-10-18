"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var CellDifferential = require('../src/CellDifferential.jsx');

//*------------------------------------------------------------------*

module.exports = function(fontSize, colour, foldChange, pValue, tStat, displayLevels, mountNode) {
    ReactDOM.render(
        React.createElement(
            CellDifferential, {fontSize: fontSize, colour: colour, foldChange: foldChange, pValue: pValue, tStat: tStat, displayLevels: displayLevels}
        ),
        mountNode
    );
};
