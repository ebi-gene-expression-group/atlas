"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var BioentityInformation = require('./BioentityInformation.jsx');

//*------------------------------------------------------------------*

exports.render = function(options) {

    ReactDOM.render(
        React.createElement(
            BioentityInformation,
            {bioentityProperties: options.payload}
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
