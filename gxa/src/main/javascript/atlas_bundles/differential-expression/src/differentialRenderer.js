const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const DifferentialRouter = require('./DifferentialRouter.jsx');

//*------------------------------------------------------------------*

module.exports = function ({atlasHostUrl : atlasUrl = "/gxa/", geneQuery, conditionQuery, species, target = 'gxaDifferentialTab', }) {
    ReactDOM.render(
        React.createElement(DifferentialRouter, {atlasUrl, geneQuery, conditionQuery, species}),
        document.getElementById(target)
    );
};
