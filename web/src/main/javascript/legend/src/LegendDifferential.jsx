"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var LegendRow = require('./LegendRow.jsx');
var HelpTooltips = require('help-tooltips');

//*------------------------------------------------------------------*

require('../css/legend.css');

//*------------------------------------------------------------------*

var LegendDifferential = React.createClass({

    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        minDownLevel: React.PropTypes.string.isRequired,
        maxDownLevel: React.PropTypes.string.isRequired,
        minUpLevel: React.PropTypes.string.isRequired,
        maxUpLevel: React.PropTypes.string.isRequired
    },

    render: function () {
        return (
            <div className="gxaLegend">
                <div style={{display: "inline-table"}}>
                    {isNaN(this.props.minDownLevel) && isNaN(this.props.maxDownLevel) ?
                        null :
                        <LegendRow lowExpressionLevel={this.props.minDownLevel}
                                   highExpressionLevel={this.props.maxDownLevel}
                                   lowValueColour="#C0C0C0"
                                   highValueColour="#0000FF"/>
                    }
                    {isNaN(this.props.minUpLevel) && isNaN(this.props.maxUpLevel) ?
                        null :
                        <LegendRow lowExpressionLevel={this.props.minUpLevel}
                                   highExpressionLevel={this.props.maxUpLevel}
                                   lowValueColour="#FFAFAF"
                                   highValueColour="#FF0000"/>
                    }
                </div>
                <div ref="legendHelp" data-help-loc="#gradient-differential" className="gxaLegendHelp"></div>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips.init(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
    }
});

//*------------------------------------------------------------------*

module.exports = LegendDifferential;
