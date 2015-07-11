"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var SimpleComponent = require('./src/SimpleComponent.jsx');

//*------------------------------------------------------------------*

exports.render = function(mountNode) {
    React.render(
        React.createElement(SimpleComponent), mountNode
    );
};
