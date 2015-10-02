"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
require('jquery-ui');
require('../css/jquery-ui.min.css');

//*------------------------------------------------------------------*

var NumberFormat = require('number-format');

//*------------------------------------------------------------------*

require('../css/cell-differential.css');
require('../css/cell-differential-tooltip.css');

//*------------------------------------------------------------------*

var CellDifferential = React.createClass({

    propTypes: {
        fontSize: React.PropTypes.number,
        colour: React.PropTypes.string.isRequired,
        foldChange: React.PropTypes.string.isRequired,
        pValue: React.PropTypes.number,
        tStat: React.PropTypes.number,
        displayLevels: React.PropTypes.bool.isRequired
    },

    _hasValue: function () {
        return (this.props.foldChange !== undefined);
    },

    _fontSize: function() {
        if (this.props.fontSize) {
            return this.props.fontSize + "px";
        } else {
            return "9px";
        }
    },

    render: function () {
        if (!this._hasValue()) {
            return (<td></td>);
        }

        return (
            <td style={{backgroundColor: this.props.colour, verticalAlign: "middle"}}>
                <div style={{fontSize: this._fontSize()}} className={this.props.displayLevels ? "gxaShowCell" : "gxaHideCell"}>
                    {this.props.foldChange}
                </div>
            </td>
        );
    },

    componentDidMount: function () {
        if (this._hasValue()) {
            this._initTooltip(this.getDOMNode());
        }
    },

    _initTooltip: function(element) {

        //TODO - build this from a React component, like we do for FactorTooltip
        function buildHeatmapCellTooltip (pValue, tstatistic, foldChange) {

            return "<table class='gxaTableGrid' style='margin: 0; padding: 0;'>" +
                       "<thead>" +
                           (pValue !== undefined ?
                               "<th class='gxaHeaderCell'>Adjusted <i>p</i>-value</th>" : "") +
                           (tstatistic !== undefined ?
                               "<th class='gxaHeaderCell'><i>t</i>-statistic</th>" : "") +
                           "<th class='gxaHeaderCell'>Log<sub>2</sub>-fold change</th>" +
                       "</thead>" +
                       "<tbody>" +
                           "<tr>" +
                               (pValue !== undefined ?
                                   "<td style='padding:6px'>" + React.renderToStaticMarkup(NumberFormat.scientificNotation(pValue.toString())) + "</td>" : "") +
                               (tstatistic !== undefined ?
                                   "<td style='padding:6px'>" + tstatistic + "</td>" : "") +
                               "<td style='padding:6px'>" + foldChange + "</td>" +
                           "</tr>" +
                       "</tbody>" +
                   "</table>";
        }

        var props = this.props;

        $(element).attr('title', '').tooltip({
            open: function (event, ui) {
                ui.tooltip.css('background', props.colour);
            },

            tooltipClass:"gxaHelpTooltip gxaPvalueTooltipStyling",

            content:function () {
                return buildHeatmapCellTooltip(props.pValue, props.tStat, props.foldChange);
            }
        });
    }
});

//*------------------------------------------------------------------*

module.exports = CellDifferential;