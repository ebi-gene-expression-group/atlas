import React from 'react'
import ReactDOM from 'react-dom'
import ReactDOMServer from 'react-dom/server'
import $ from 'jquery'
import 'jquery-ui-bundle'

import ScientificNotationNumber from 'expression-atlas-number-format'

require('./gxaShowHideCell.css');
require('./gxaDifferentialCellTooltip.css');

const CellDifferential = React.createClass({

    propTypes: {
        fontSize: React.PropTypes.number,
        colour: React.PropTypes.string,
        foldChange: React.PropTypes.number,
        pValue: React.PropTypes.number,
        tStat: React.PropTypes.number,
        displayLevels: React.PropTypes.bool.isRequired
    },

    _hasValue() {
        return !!this.props.foldChange;
    },

    _getStyle() {
        return this.props.fontSize ? { fontSize: `${this.props.fontSize}px` } : {};
    },

    render() {
        if (!this._hasValue()) {
            return <td/>;
        }

        return (
            <td style={{backgroundColor: this.props.colour, verticalAlign: `middle`}}>
                <div style={this._getStyle()} className={this.props.displayLevels ? `gxaShowCell` : `gxaHideCell`}>
                    {this.props.foldChange}
                </div>
            </td>
        );
    },

    componentDidMount() {
        if (this._hasValue()) {
            this._initTooltip(ReactDOM.findDOMNode(this));
        }
    },

    _initTooltip(element) {

        function buildHeatmapCellTooltip (pValue, tStatistic, foldChange) {
            return (
                    <table>
                        <thead>
                            <tr>
                                {pValue ? <th>Adjusted <em>p</em>-value</th> : null}
                                {tStatistic ? <th><em>t</em>-statistic</th> : null}
                                <th>Log<sub>2</sub>-fold change</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                {pValue ? <td><ScientificNotationNumber value={pValue} /></td> : null}
                                {tStatistic ? <td>{Math.floor(tStatistic * 1e4) / 1e4}</td> : null}
                                <td>{foldChange}</td>
                            </tr>
                        </tbody>
                    </table>
            );
        }

        $(element).attr(`title`, ``).tooltip({
            open: (event, ui) => {
                ui.tooltip.css(`background`, this.props.colour);
            },

            tooltipClass: `gxaDifferentialCellTooltip`,

            content: () => {
                return ReactDOMServer.renderToStaticMarkup(
                    buildHeatmapCellTooltip(this.props.pValue, this.props.tStat, this.props.foldChange));
            }
        });
    }
});

module.exports = CellDifferential;
