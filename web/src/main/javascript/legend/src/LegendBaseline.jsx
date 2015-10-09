"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var NumberFormat = require('number-format');
var HelpTooltips = require('help-tooltips');

//*------------------------------------------------------------------*

require('../css/legend.css');

//*------------------------------------------------------------------*

var LegendBaseline = React.createClass({

    propTypes: {
        minExpressionLevel: React.PropTypes.number.isRequired,
        maxExpressionLevel: React.PropTypes.number.isRequired,
        helpTooltipLocation: React.PropTypes.string.isRequired,
        displayLevels: React.PropTypes.bool.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired
    },

    render: function () {
        return (
            <div className="gxaHeatmapLegendGradient">
                <div style={{display: "inline-table"}}>
                    <LegendRow displayLevels={this.props.displayLevels}
                               lowExpressionLevel={NumberFormat.baselineExpression(this.props.minExpressionLevel)}
                               highExpressionLevel={NumberFormat.baselineExpression(this.props.maxExpressionLevel)}
                               lowValueColour="#C0C0C0"
                               highValueColour="#0000FF"/>
                </div>
                <div ref="legendHelp" data-help-loc={this.props.helpTooltipLocation} className="gxaLegendHelp"/>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips.init(this.props.atlasBaseURL, "experiment", this.refs.legendHelp.getDOMNode());
    }
});

//*------------------------------------------------------------------*

module.exports = LegendBaseline;