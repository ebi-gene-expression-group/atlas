"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var DisplayLevelsButton = require('../src/DisplayLevelsButton.jsx');

//*------------------------------------------------------------------*

module.exports = function(hideText, showText, onClickCallback, displayLevels, mountNode) {
    React.render(
        React.createElement(
            DisplayLevelsButton, {hideText: hideText, showText: showText, onClickCallback: onClickCallback, displayLevels: displayLevels}
        ),
        mountNode
    );
};
