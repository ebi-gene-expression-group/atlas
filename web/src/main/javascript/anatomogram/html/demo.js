"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var Anatomogram = require('../src/Anatomogram.jsx');

//*------------------------------------------------------------------*

exports.render = function(mountNode) {
    ReactDOM.render(
        React.createElement(Anatomogram, {message: "So sexy!"}), mountNode
    );
};