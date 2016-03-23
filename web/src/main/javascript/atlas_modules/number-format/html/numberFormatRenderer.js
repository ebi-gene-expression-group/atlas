"use strict";

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var NumberFormat = require('../src/NumberFormat.jsx');

//*------------------------------------------------------------------*

exports.baselineExpression = function(expressionLevel, mountNode) {
    ReactDOM.render(NumberFormat.baselineExpression(expressionLevel), mountNode);
};

exports.scientificNotation = function(numberAsString, mountNode) {
    ReactDOM.render(NumberFormat.scientificNotation(numberAsString), mountNode);
}