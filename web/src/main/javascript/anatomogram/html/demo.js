"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var Anatomogram = require('../src/Anatomogram.jsx');

//*------------------------------------------------------------------*

exports.render = function(mountNode) {
    React.render(
        React.createElement(Anatomogram, {message: "So sexy!"}), mountNode
    );
};