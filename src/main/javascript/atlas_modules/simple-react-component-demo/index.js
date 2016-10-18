"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var SimpleComponent = require('./src/SimpleComponent.jsx');

//*------------------------------------------------------------------*

exports.render = function(mountNode) {
    ReactDOM.render(
        React.createElement(SimpleComponent), mountNode
    );
};
