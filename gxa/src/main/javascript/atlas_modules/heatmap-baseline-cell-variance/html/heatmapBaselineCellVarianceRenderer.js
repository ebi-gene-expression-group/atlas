var React = require('react');
var ReactDOM = require('react-dom');

var HeatmapBaselineCellVariance = require('../src/HeatmapBaselineCellVariance.jsx');

module.exports = function(quartiles, mountNode) {

    ReactDOM.render(React.createElement(HeatmapBaselineCellVariance, {quartiles: quartiles}), mountNode);
};
