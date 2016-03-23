"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var NumberFormat = require('number-format');
var HelpTooltips = require('help-tooltips');

//*------------------------------------------------------------------*

require('./gxaLegend.css');

//*------------------------------------------------------------------*

var LegendBaseline = React.createClass({

    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        minExpressionLevel: React.PropTypes.string.isRequired,
        maxExpressionLevel: React.PropTypes.string.isRequired,
        isMultiExperiment: React.PropTypes.bool.isRequired
    },

    render: function () {
        var dataHelpLoc = this.props.isMultiExperiment ? "#gradient-base-crossexp" : "#gradient-base";

        // The class gxaHeatmapLegendGradient is used for Selenium tests but isn’t styled
        return (
            <div className="gxaHeatmapLegendGradient">
                <div style={{display: "inline-table"}}>
                    <LegendRow lowExpressionLevel={NumberFormat.baselineExpression(this.props.minExpressionLevel)}
                               highExpressionLevel={NumberFormat.baselineExpression(this.props.maxExpressionLevel)}
                               lowValueColour="#C0C0C0"
                               highValueColour="#0000FF"/>
                </div>
                <div ref="legendHelp" data-help-loc={dataHelpLoc} className="gxaLegendHelp"></div>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
    }
});

//*------------------------------------------------------------------*

module.exports = LegendBaseline;