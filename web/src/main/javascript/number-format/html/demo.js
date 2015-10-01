"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var NumberFormat = require('../src/NumberFormat.jsx');

//*------------------------------------------------------------------*

exports.baselineExpression = function(expressionLevel, mountNode) {
    React.render(NumberFormat.baselineExpression(expressionLevel), mountNode);
};

exports.scientificNotation = function(numberAsString, mountNode) {
    React.render(NumberFormat.scientificNotation(numberAsString), mountNode);
}