"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var HelpTooltips = require('expression-atlas-help-tooltips');

//*------------------------------------------------------------------*

require('./gxaLegend.css');

//*------------------------------------------------------------------*

var LegendDifferential = React.createClass({

    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        minDownLevel: React.PropTypes.number.isRequired,
        maxDownLevel: React.PropTypes.number.isRequired,
        minUpLevel: React.PropTypes.number.isRequired,
        maxUpLevel: React.PropTypes.number.isRequired
    },

    render: function () {
        return (
            <div className="gxaLegend">
                <div style={{display: "inline-table"}}>
                    {isNaN(this.props.minDownLevel) && isNaN(this.props.maxDownLevel) ?
                        null :
                        <LegendRow lowExpressionLevel={<span>{this.props.minDownLevel}</span>}
                                   highExpressionLevel={<span>{this.props.maxDownLevel}</span>}
                                   lowValueColour="#C0C0C0"
                                   highValueColour="#0000FF"/>
                    }
                    {isNaN(this.props.minUpLevel) && isNaN(this.props.maxUpLevel) ?
                        null :
                        <LegendRow lowExpressionLevel={<span>{this.props.minUpLevel}</span>}
                                   highExpressionLevel={<span>{this.props.maxUpLevel}</span>}
                                   lowValueColour="#FFAFAF"
                                   highValueColour="#FF0000"/>
                    }
                </div>
                <div ref="legendHelp" data-help-loc="#gradient-differential" className="gxaLegendHelp"></div>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
    }
});

//*------------------------------------------------------------------*

module.exports = LegendDifferential;
