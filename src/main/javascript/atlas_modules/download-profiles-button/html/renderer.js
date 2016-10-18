"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var DownloadProfilesButton = require('../src/DownloadProfilesButton.jsx');

//*------------------------------------------------------------------*

module.exports = function(mountNode,atlasBaseURL,downloadProfilesURL, disclaimer,googleAnalyticsCallback) {
    ReactDOM.render(
        React.createElement(
            DownloadProfilesButton,
            {
                atlasBaseURL:atlasBaseURL,
                downloadProfilesURL: downloadProfilesURL,
                disclaimer: disclaimer,
                onDownloadCallbackForAnalytics: typeof googleAnalyticsCallback ==='undefined' ? console.log || function(){} : googleAnalyticsCallback
            }
        ),
        mountNode
    );
};
