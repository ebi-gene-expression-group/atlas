"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');
var ReactDOMServer = require('react-dom/server');
var $ = require('jquery');
require('jquery-ui-bundle');

//*------------------------------------------------------------------*

var NumberFormat = require('number-format');

//*------------------------------------------------------------------*

require('./gxaShowHideCell.css');
require('./gxaDifferentialCellTooltip.css');

//*------------------------------------------------------------------*

var CellDifferential = React.createClass({

    propTypes: {
        fontSize: React.PropTypes.number,
        colour: React.PropTypes.string,
        foldChange: React.PropTypes.number,
        pValue: React.PropTypes.string,
        tStat: React.PropTypes.string,
        displayLevels: React.PropTypes.bool.isRequired
    },

    _hasValue: function () {
        return (this.props.foldChange !== undefined);
    },

    _getStyle: function() {
        var style = {};
        if (this.props.fontSize) {
            style.fontSize = this.props.fontSize + "px";
        }

        return style;
    },

    render: function () {
        if (!this._hasValue()) {
            return (<td/>);
        }

        return (
            <td style={{backgroundColor: this.props.colour, verticalAlign: "middle"}}>
                <div style={this._getStyle()} className={this.props.displayLevels ? "gxaShowCell" : "gxaHideCell"}>
                    {this.props.foldChange}
                </div>
            </td>
        );
    },

    componentDidMount: function () {
        if (this._hasValue()) {
            this._initTooltip(ReactDOM.findDOMNode(this));
        }
    },

    _initTooltip: function(element) {

        //TODO - build this from a React component, like we do for FactorTooltip
        function buildHeatmapCellTooltip (pValue, tStatistic, foldChange) {

            return "<table>" +
                       "<thead>" +
                           (pValue ?
                               "<th>Adjusted <em>p</em>-value</th>" : "") +
                           (tStatistic?
                               "<th><em>t</em>-statistic</th>" : "") +
                           "<th class='gxaHeaderCell'>Log<sub>2</sub>-fold change</th>" +
                       "</thead>" +
                       "<tbody>" +
                           "<tr>" +
                               (pValue?
                                   "<td>" + ReactDOMServer.renderToStaticMarkup(NumberFormat.scientificNotation(pValue)) + "</td>" : "") +
                               (tStatistic ?
                                   "<td>" + (Math.floor(tStatistic * 1e4) / 1e4) + "</td>" : "") +
                               "<td>" + foldChange + "</td>" +
                           "</tr>" +
                       "</tbody>" +
                   "</table>";

        }

        // Don’t use bind, tooltip uses this internally
        var thisProps = this.props;

        $(element).attr("title", "").tooltip({
            open: function (event, ui) {
                ui.tooltip.css("background", thisProps.colour);
            },

            tooltipClass: "gxaDifferentialCellTooltip",

            content:function () {
                return buildHeatmapCellTooltip(thisProps.pValue, thisProps.tStat, thisProps.foldChange);
            }
        });
    }
});

//*------------------------------------------------------------------*

module.exports = CellDifferential;
