"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var LegendBaseline = require('../src/LegendBaseline.jsx');
var LegendDifferential = require('../src/LegendDifferential.jsx');

//*------------------------------------------------------------------*

exports.renderBaseline = function(minExpressionLevel, maxExpressionLevel, mountNode) {
    ReactDOM.render(
        React.createElement(
            LegendBaseline, {atlasBaseURL: "https://wwwdev.ebi.ac.uk/gxa",
                             minExpressionLevel: minExpressionLevel, maxExpressionLevel: maxExpressionLevel,
                             isMultiExperiment: false
            }
        ),
        mountNode
    );
};

exports.renderDifferential = function(minDownLevel, maxDownLevel, minUpLevel, maxUpLevel, mountNode) {
    ReactDOM.render(
        React.createElement(
            LegendDifferential, {atlasBaseURL: "https://wwwdev.ebi.ac.uk/gxa",
                                 minDownLevel: minDownLevel, maxDownLevel: maxDownLevel, minUpLevel: minUpLevel, maxUpLevel: maxUpLevel
            }
        ),
        mountNode
    );
};
