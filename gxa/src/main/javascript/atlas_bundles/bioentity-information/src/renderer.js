const React = require('react');
const ReactDOM = require('react-dom');

const BioentityInformation = require('./BioentityInformation.jsx');

exports.render = function(options) {
    ReactDOM.render(
        React.createElement(
            BioentityInformation,
            {bioentityProperties: options.payload}
        ),
        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
    );
};
