const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const DifferentialRouter = require('./DifferentialRouter.jsx');

//*------------------------------------------------------------------*

module.exports = function ({atlasHostUrl : hostUrl = "https://www.ebi.ac.uk", geneQuery, conditionQuery, species, target = 'gxaDifferentialTab', }) {
    ReactDOM.render(
        React.createElement(DifferentialRouter, {hostUrl: hostUrl, geneQuery: geneQuery, conditionQuery: conditionQuery, species: species}),
        document.getElementById(target)
    );
};
