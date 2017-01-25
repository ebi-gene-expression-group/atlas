const React = require('react');
const ReactDOM = require('react-dom');

const CellDifferential = require('../src/CellDifferential.jsx');

module.exports = function(fontSize, colour, foldChange, pValue, tStat, displayLevels, mountNode) {
    ReactDOM.render(
        React.createElement(
            CellDifferential, {fontSize: fontSize, colour: colour, foldChange: foldChange, pValue: pValue, tStat: tStat, displayLevels: displayLevels}
        ),
        mountNode
    );
};
