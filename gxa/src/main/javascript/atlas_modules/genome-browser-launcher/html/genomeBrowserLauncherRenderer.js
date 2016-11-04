var React = require(`react`);
var ReactDOM = require(`react-dom`);
var GenomeBrowserLauncher = require(`../src/GenomeBrowserLauncher.jsx`);

module.exports = (mountNode, atlasBaseURL, downloadProfilesURL, disclaimer, googleAnalyticsCallback) => {
    ReactDOM.render(
        React.createElement(
            GenomeBrowserLauncher,
            {
                atlasBaseURL,
                downloadProfilesURL,
                disclaimer,
                onDownloadCallbackForAnalytics: typeof googleAnalyticsCallback ==='undefined' ? console.log || function(){} : googleAnalyticsCallback
            }
        ),
        mountNode
    );
};
