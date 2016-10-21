const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const DifferentialRouter = require('./DifferentialRouter.jsx');

//*------------------------------------------------------------------*

module.exports = function ({atlasHostUrl : hostUrl = window.location.protocol + "//" + window.location.host, query, geneQuery, conditionQuery, species, target = 'gxaDifferentialTab', }) {
    ReactDOM.render(
        React.createElement(DifferentialRouter, {hostUrl, query, geneQuery, conditionQuery, species}),
        document.getElementById(target)
    );
};
