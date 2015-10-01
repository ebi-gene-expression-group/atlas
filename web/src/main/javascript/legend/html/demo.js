"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var LegendBaseline = require('../src/LegendBaseline.jsx');
var LegendDifferential = require('../src/LegendDifferential.jsx');

//*------------------------------------------------------------------*

exports.renderBaseline = function(minExpressionLevel, maxExpressionLevel, mountNode) {
    React.render(
        React.createElement(
            LegendBaseline, {proxyPrefix: "", contextRoot: "http://localhost:8080/gxa",
                             minExpressionLevel: minExpressionLevel, maxExpressionLevel: maxExpressionLevel, helpTooltipLocation: "#gradient-base", // or "#gradient-base-crossexp"
                             displayLevels: true}
        ),
        mountNode
    );
};

exports.renderDifferential = function(minDownLevel, maxDownLevel, minUpLevel, maxUpLevel, mountNode) {
    React.render(
        React.createElement(
            LegendDifferential, {proxyPrefix: "", contextRoot: "http://localhost:8080/gxa",
                                 minDownLevel: minDownLevel, maxDownLevel: maxDownLevel, minUpLevel: minUpLevel, maxUpLevel: maxUpLevel,
                                 displayLevels: true}
        ),
        mountNode
    );
};
