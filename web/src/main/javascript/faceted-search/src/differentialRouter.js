"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var DifferentialTab = require('./DifferentialTab.jsx');
//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.target - id of the container
 * @param {string} options.atlasHost
 * @param {string} options.identifier
 * @param {string} options.geneQuery
 * @param {string} options.conditionQuery
 * @param {string} options.species
 */
module.exports = function (options) {
  options.host = options.atlasHost ? options.atlasHost : window.location.host;
  ReactDOM.render(
    React.createElement(
      DifferentialTab, options
    ), document.getElementById(options.target || "gxaDifferentialTab")
  );
};
