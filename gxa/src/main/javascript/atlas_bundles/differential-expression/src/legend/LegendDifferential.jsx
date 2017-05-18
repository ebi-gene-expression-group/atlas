const $ = require('jquery');
require('jquery-ui-bundle');

const React = require('react');

//*------------------------------------------------------------------*

const LegendRow = require('./LegendRow.jsx');

//*------------------------------------------------------------------*

require('./gxaGradient.css');
require('./gxaHelpTooltip.css');

//*------------------------------------------------------------------*

const LegendDifferential = React.createClass({

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

                <a href="#" id="legendHelp" className="help-icon" title="Greater colour saturation means higher absolute log<sub>2</sub>-fold change value. Blue indicates the gene is down–regulated and red means up–regulated.">?</a>
            </div>
        );
    },

    componentDidMount: function () {
        $('#legendHelp')
          .click(function (e) {e.preventDefault();})
          .tooltip({ tooltipClass: 'gxaHelpTooltip' })
    }
});

//*------------------------------------------------------------------*

module.exports = LegendDifferential;
