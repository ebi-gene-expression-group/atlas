const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const DifferentialTab = require('./DifferentialTab.jsx');

//*------------------------------------------------------------------*

module.exports = function ({atlasHostUrl : hostUrl = "https://www.ebi.ac.uk", geneQuery, conditionQuery, species, target = 'gxaDifferentialTab', }) {
    ReactDOM.render(
        React.createElement(DifferentialTab, {hostUrl: hostUrl, geneQuery: geneQuery, conditionQuery: conditionQuery, species: species}),
        document.getElementById(target)
    );
};
