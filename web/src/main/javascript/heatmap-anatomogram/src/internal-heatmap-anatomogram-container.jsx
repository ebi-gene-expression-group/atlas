"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
require('jquery-hc-sticky');

var EventEmitter = require('events');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var Heatmap = require('./heatmap.jsx');
var EnsemblLauncher = require('./ensembl-launcher.jsx');

var ExperimentTypes = require('./experiment-types.js');

//*------------------------------------------------------------------*

require('./internal-heatmap-anatomogram-container.css');

//*------------------------------------------------------------------*

var InternalHeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        anatomogram: React.PropTypes.object,
        columnHeaders: React.PropTypes.oneOfType([
            React.PropTypes.arrayOf(React.PropTypes.shape({
                assayGroupId: React.PropTypes.string.isRequired,
                factorValue: React.PropTypes.string.isRequired,
                factorValueOntologyTermId: React.PropTypes.string
            })),
            React.PropTypes.arrayOf(React.PropTypes.shape({
                id: React.PropTypes.string.isRequired,
                referenceAssayGroup: React.PropTypes.shape({
                    id: React.PropTypes.string.isRequired,
                    assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
                    replicates: React.PropTypes.number.isRequired
                }).isRequired,
                testAssayGroup: React.PropTypes.shape({
                    id: React.PropTypes.string.isRequired,
                    assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
                    replicates: React.PropTypes.number.isRequired
                }).isRequired,
                displayName: React.PropTypes.string.isRequired
            }))
        ]).isRequired,
        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        multipleColumnHeaders: React.PropTypes.object,
        profiles: React.PropTypes.object.isRequired,
        geneSetProfiles: React.PropTypes.object,
        heatmapConfig: React.PropTypes.object.isRequired,
        type: React.PropTypes.oneOf([
            ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE
        ]).isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";

        var prefFormDisplayLevels = $('#displayLevels');

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">
                    { this.props.anatomogram ?
                        <Anatomogram anatomogramData={this.props.anatomogram}
                                     expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour}
                                     profileRows={this.props.profiles.rows} eventEmitter={anatomogramEventEmitter} atlasBaseURL={this.props.atlasBaseURL} />
                        : null}
                    { this.props.heatmapConfig.enableEnsemblLauncher ?
                        <EnsemblLauncher isBaseline={this.props.type === ExperimentTypes.BASELINE || this.props.type === ExperimentTypes.PROTEOMICS_BASELINE}
                                         experimentAccession={this.props.heatmapConfig.experimentAccession}
                                         species={this.props.heatmapConfig.species}
                                         ensemblDB={this.props.heatmapConfig.ensemblDB}
                                         columnType={this.props.heatmapConfig.columnType}
                                         eventEmitter={ensemblEventEmitter}
                                         atlasBaseURL={this.props.atlasBaseURL} />
                        : null }
                </div>

                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap type={this.props.type}
                             heatmapConfig={this.props.heatmapConfig}
                             columnHeaders={this.props.columnHeaders}
                             nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                             multipleColumnHeaders={this.props.multipleColumnHeaders}
                             profiles={this.props.profiles}
                             geneSetProfiles={this.props.geneSetProfiles}
                             isWidget={false}
                             prefFormDisplayLevels={prefFormDisplayLevels}
                             ensemblEventEmitter={ensemblEventEmitter}
                             anatomogramEventEmitter={anatomogramEventEmitter}
                             atlasBaseURL={this.props.atlasBaseURL}
                             linksAtlasBaseURL={this.props.linksAtlasBaseURL} />
                </div>

            </div>
        );
    },

    componentDidMount: function() {
        var $anatomogramEnsemblAside = $(ReactDOM.findDOMNode(this.refs.anatomogramEnsembl));
        $anatomogramEnsemblAside.hcSticky({responsive: true});
    }
});

//*------------------------------------------------------------------*

module.exports = InternalHeatmapAnatomogramContainer;