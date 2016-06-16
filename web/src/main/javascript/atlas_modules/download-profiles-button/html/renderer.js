"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var DownloadProfilesButton = require('../src/DownloadProfilesButton.jsx');

//*------------------------------------------------------------------*

module.exports = function(mountNode,atlasBaseURL,downloadProfilesURL, isFortLauderdale,googleAnalyticsCallback) {
    ReactDOM.render(
        React.createElement(
            DownloadProfilesButton,
            {
                atlasBaseURL:atlasBaseURL,
                downloadProfilesURL: downloadProfilesURL,
                isFortLauderdale: isFortLauderdale,
                onDownloadCallbackForAnalytics: typeof googleAnalyticsCallback ==='undefined' ? console.log : googleAnalyticsCallback
            }
        ),
        mountNode
    );
};
