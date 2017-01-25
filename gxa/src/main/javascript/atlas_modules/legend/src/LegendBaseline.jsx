"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var NumberFormat = require('expression-atlas-number-format').default;
var HelpTooltips = require('expression-atlas-help-tooltips');

//*------------------------------------------------------------------*

require('./gxaLegend.css');

//*------------------------------------------------------------------*

var LegendBaseline = React.createClass({

    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        minExpressionLevel: React.PropTypes.number.isRequired,
        maxExpressionLevel: React.PropTypes.number.isRequired,
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
                <div ref={legendHelpDiv => { this.legendHelpDiv = legendHelpDiv; }} data-help-loc={dataHelpLoc} className="gxaLegendHelp"></div>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.legendHelpDiv));
    }
});

//*------------------------------------------------------------------*

module.exports = LegendBaseline;