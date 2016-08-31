const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const BaselineRouter = require('./BaselineRouter.jsx');

//*------------------------------------------------------------------*

module.exports = function ({atlasHostUrl : hostUrl = window.location.protocol + "//" + window.location.host, target = 'gxaBaselineTab', facetsTreeData, geneQuery, conditionQuery, species}) {
    ReactDOM.render(
        React.createElement(BaselineRouter, {hostUrl: hostUrl, facetsTreeData: facetsTreeData, geneQuery: geneQuery, conditionQuery: conditionQuery, species: species}),
        document.getElementById(target)
    );
};
