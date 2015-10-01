"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var HelpTooltipsInit = require('help-tooltips');

//*------------------------------------------------------------------*

require('../css/legend.css');

//*------------------------------------------------------------------*

var LegendDifferential = React.createClass({

    propTypes: {
        proxyPrefix: React.PropTypes.string.isRequired,
        contextRoot: React.PropTypes.string.isRequired,
        minDownLevel: React.PropTypes.number.isRequired,
        maxDownLevel: React.PropTypes.number.isRequired,
        minUpLevel: React.PropTypes.number.isRequired,
        maxUpLevel: React.PropTypes.number.isRequired,
        displayLevels: React.PropTypes.bool.isRequired
    },

    render: function () {
        return (
            <div className="gxaLegend">
                <div style={{display: "inline-table"}}>
                    {!isNaN(this.props.minDownLevel) && !isNaN(this.props.maxDownLevel) ?
                        <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minDownLevel} highExpressionLevel={this.props.maxDownLevel} lowValueColour="#C0C0C0" highValueColour="#0000FF"/>
                        : null }
                    {!isNaN(this.props.minUpLevel) && !isNaN(this.props.maxUpLevel) ?
                        <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minUpLevel} highExpressionLevel={this.props.maxUpLevel} lowValueColour="#FFAFAF" highValueColour="#FF0000"/>
                        : null }
                </div>
                <div ref="legendHelp" data-help-loc="#gradient-differential" className="gxaLegendHelp"/>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltipsInit(this.props.proxyPrefix + this.props.contextRoot, "experiment", this.refs.legendHelp.getDOMNode());
    }
});

//*------------------------------------------------------------------*

module.exports = LegendDifferential;
