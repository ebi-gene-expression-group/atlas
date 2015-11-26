"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var MultipleFactorHeader = require('./MultipleFactorHeader.jsx');

//*------------------------------------------------------------------*

var MultipleHeatmapHeader = React.createClass({

    renderContrastFactorHeaders: function () {
        var hoverColumnCallback = this.props.hoverColumnCallback;
        var heatmapConfig = this.props.heatmapConfig;
        var type = this.props.type;

        if (this.props.type.isBaseline) {
            var factorHeadersArray = [];

            var multipleColumnHeaders = this.props.multipleColumnHeaders.children;
            for (var children in multipleColumnHeaders) {
                var mainHeaderName = multipleColumnHeaders[children].name;
                var childs = multipleColumnHeaders[children].children;
                for (var child in childs) {
                    var subHeaderName = childs[child].name;
                    var cellLines = childs[child].children[0].cellLines;

                    var cellLinesObject = {
                        mHNames: mainHeaderName + subHeaderName,
                        cellLines: cellLines
                    };
                    factorHeadersArray.push(cellLinesObject);
                }
            }

            var factorHeaders = factorHeadersArray.map(function (factorHeader) {
                return renderFactorHeaders(heatmapConfig, factorHeader.mHNames, type, factorHeader.cellLines,
                    heatmapConfig.experimentAccession, null, null, hoverColumnCallback, null);
            });

            return factorHeaders;
        }
    },

    renderHeaders: function () {
        var multipleHeaders = (this.props.multipleColumnHeaders.children).map(function (children) {
            return (<MultipleFactorHeader key={children.name}
                                          name={children.name}
                                          colspan={children.colSpan} />);
        });

        return multipleHeaders;
    },

    renderSubHeaders: function () {
        var subHeaders = (this.props.multipleColumnHeaders.children).map(function (children) {
            return (children.children).map(function (child) {
                return (<MultipleFactorHeader key={child.name}
                                              name={child.name}
                                              colspan={child.colSpan} />);
            });
        });

        return subHeaders;
    },

    render: function () {
        var showGeneProfile = this.props.showGeneSetProfiles ? 'Gene set' : 'Gene';
        var showExperimentProfile = this.props.type.isMultiExperiment ? 'Experiment' : showGeneProfile;

        return (
            <thead>
            <tr>
                <th className="gxaHorizontalHeaderCell" rowSpan="3">
                    <TopLeftCorner type={this.props.type}
                                   hasQuartiles={this.props.hasQuartiles}
                                   radioId={this.props.radioId}
                                   isSingleGeneResult={this.props.isSingleGeneResult}
                                   heatmapConfig={this.props.heatmapConfig}
                                   displayLevels={this.props.displayLevels}
                                   toggleDisplayLevels={this.props.toggleDisplayLevels}
                                   selectedRadioButton={this.props.selectedRadioButton}
                                   toggleRadioButton={this.props.toggleRadioButton}/>
                </th>
                { this.renderHeaders() }
            </tr>
            <tr>
                { this.renderSubHeaders() }
            </tr>
            <tr>
                { this.props.renderContrastFactorHeaders ? this.renderContrastFactorHeaders() : null }
            </tr>
            <tr>
                <th className="gxaHorizontalHeaderCell gxaHeatmapTableIntersect" style={ this.props.isMicroarray ? {width:"166px"} : undefined}>
                    <div>{ showExperimentProfile }</div>
                </th>
                { this.props.isMicroarray ? <th className="gxaHorizontalHeaderCell gxaHeatmapTableIntersect"><div>Design Element</div></th> : null}
            </tr>
            </thead>
        );
    }

});


function renderFactorHeaders(heatmapConfig, mainHeaderNames, type, assayGroupFactors, experimentAccession, selectColumn,
                             selectedColumnId, hoverColumnCallback, anatomogramEventEmitter) {

    var factorHeaders = assayGroupFactors.map(function (assayGroupFactor) {
        return <FactorHeader key={mainHeaderNames + assayGroupFactor.factorValue}
                             type={type}
                             heatmapConfig={heatmapConfig}
                             factorName={assayGroupFactor.factorValue}
                             svgPathId={assayGroupFactor.factorValueOntologyTermId}
                             assayGroupId={assayGroupFactor.assayGroupId}
                             experimentAccession={experimentAccession}
                             selectColumn={selectColumn}
                             selected={assayGroupFactor.assayGroupId === selectedColumnId}
                             hoverColumnCallback={hoverColumnCallback}
                             anatomogramEventEmitter={anatomogramEventEmitter}/>;
    }.bind(this));

    return factorHeaders;
}

//*------------------------------------------------------------------*

module.exports = MultipleHeatmapHeader;