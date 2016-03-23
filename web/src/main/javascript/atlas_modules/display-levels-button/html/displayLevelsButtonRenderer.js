"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var DisplayLevelsButton = require('../src/DisplayLevelsButton.jsx');

//*------------------------------------------------------------------*

module.exports = function(hideText, showText, onClickCallback, displayLevels, width, fontSize, mountNode) {
    ReactDOM.render(
        React.createElement(
            DisplayLevelsButton, {hideText: hideText, showText: showText, onClickCallback: onClickCallback, displayLevels: displayLevels, width: width, fontSize: fontSize}
        ),
        mountNode
    );
};
