"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var NumberFormat = require('number-format');
var HelpTooltipsInit = require('help-tooltips');

//*------------------------------------------------------------------*

require('../css/legend.css');

//*------------------------------------------------------------------*

var LegendBaseline = React.createClass({

    propTypes: {
        proxyPrefix: React.PropTypes.string.isRequired,
        contextRoot: React.PropTypes.string.isRequired,
        minExpressionLevel: React.PropTypes.number.isRequired,
        maxExpressionLevel: React.PropTypes.number.isRequired,
        helpTooltipLocation: React.PropTypes.string.isRequired,
        displayLevels: React.PropTypes.bool.isRequired
    },

    render: function () {
        return (
            <div className="gxaHeatmapLegendGradient">
                <div style={{display: "inline-table"}}>
                    <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={NumberFormat.baselineExpression(this.props.minExpressionLevel)} highExpressionLevel={NumberFormat.baselineExpression(this.props.maxExpressionLevel)} lowValueColour="#C0C0C0" highValueColour="#0000FF"/>
                </div>
                <div ref="legendHelp" data-help-loc={this.props.helpTooltipLocation} className="gxaLegendHelp"/>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltipsInit(this.props.proxyPrefix + this.props.contextRoot, "experiment", this.refs.legendHelp.getDOMNode());
    }
});

//*------------------------------------------------------------------*

module.exports = LegendBaseline;