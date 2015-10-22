"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('../lib/jquery.hc-sticky.js');

var URI = require('urijs');
var EventEmitter = require('wolfy87-eventemitter');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var Heatmap = require('./heatmap.jsx');
var EnsemblLauncher = require('./ensembl-launcher.jsx');

//*------------------------------------------------------------------*

require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

var TypeEnum = {
    BASELINE: { isBaseline: true, heatmapTooltip: '#heatMapTableCellInfo' },
    PROTEOMICS_BASELINE: { isBaseline: true, isProteomics: true, heatmapTooltip: '#heatMapTableCellInfo-proteomics' },
    DIFFERENTIAL: { isDifferential: true, heatmapTooltip: '#heatMapTableCellInfo-differential' },
    MULTIEXPERIMENT: { isMultiExperiment: true, heatmapTooltip: '#heatMapTableCellInfo-multiexp' }
};

var InternalHeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        anatomogram: React.PropTypes.object,
        columnHeaders: React.PropTypes.array.isRequired,
        multipleColumnHeaders: React.PropTypes.object,
        profiles: React.PropTypes.object.isRequired,
        geneSetProfiles: React.PropTypes.object,
        heatmapConfig: React.PropTypes.object.isRequired,
        isWidget: React.PropTypes.bool.isRequired
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var type;
        if(this.props.type === "isBaseline") {
            type = TypeEnum.BASELINE;
        } else if(this.props.type === "isMultiExperiment") {
            type = TypeEnum.MULTIEXPERIMENT;
        } else if(this.props.type === "isDifferential") {
            type = TypeEnum.DIFFERENTIAL;
        } else if(this.props.type === "isProteomics") {
            type = TypeEnum.PROTEOMICS_BASELINE;
        }

        var anatomogramExpressedTissueColour = type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = type.isMultiExperiment ? "indigo" : "red";

        var prefFormDisplayLevels = $('#displayLevels');

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">
                    { this.props.anatomogram ?
                        <Anatomogram anatomogramData={this.props.anatomogram}
                                     expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour}
                                     profileRows={this.props.profiles.rows} eventEmitter={anatomogramEventEmitter} atlasBaseURL={this.props.heatmapConfig.atlasBaseURL} />
                        : null}
                    { this.props.heatmapConfig.enableEnsemblLauncher ?
                        <EnsemblLauncher isBaseline={type.isBaseline}
                                         experimentAccession={this.props.heatmapConfig.experimentAccession}
                                         species={this.props.heatmapConfig.species}
                                         ensemblDB={this.props.heatmapConfig.ensemblDB}
                                         columnType={this.props.heatmapConfig.columnType}
                                         eventEmitter={ensemblEventEmitter}
                                         atlasBaseURL={this.props.heatmapConfig.atlasBaseURL}/>
                        : null }
                </div>

                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap type={type}
                             heatmapConfig={this.props.heatmapConfig}
                             columnHeaders={this.props.columnHeaders}
                             multipleColumnHeaders={this.props.multipleColumnHeaders}
                             profiles={this.props.profiles}
                             geneSetProfiles={this.props.geneSetProfiles}
                             isWidget={false}
                             prefFormDisplayLevels={prefFormDisplayLevels}
                             ensemblEventEmitter={ensemblEventEmitter}
                             anatomogramEventEmitter={anatomogramEventEmitter} />
                </div>

            </div>
        );
    },

    componentDidMount: function() {
        var $anatomogramEnsemblAside = $(this.refs.anatomogramEnsembl.getDOMNode());
        $anatomogramEnsemblAside.hcSticky({responsive: true});
    }
});

//*------------------------------------------------------------------*

module.exports = InternalHeatmapAnatomogramContainer;