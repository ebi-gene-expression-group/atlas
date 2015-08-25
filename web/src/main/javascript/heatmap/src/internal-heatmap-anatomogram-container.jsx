"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('../lib/jquery.hc-sticky.js');

var URI = require('URIjs');
var EventEmitter = require('wolfy87-eventemitter');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var EnsemblLauncher = require('./ensembl-launcher.jsx');

//*------------------------------------------------------------------*

require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*


var InternalHeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        Heatmap: React.PropTypes.func.isRequired,
        anatomogram: React.PropTypes.object.isRequired,
        columnHeaders: React.PropTypes.array.isRequired,
        profiles: React.PropTypes.object.isRequired,
        geneSetProfiles: React.PropTypes.object.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired,
        isBaseline: React.PropTypes.bool.isRequired,
        isMultiExperiment: React.PropTypes.bool.isRequired,
        isWidget: React.PropTypes.bool.isRequired
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var Heatmap = this.props.Heatmap;

        var anatomogramExpressedTissueColour = this.props.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.isMultiExperiment ? "indigo" : "red";

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">
                    { this.props.anatomogram ?
                        <Anatomogram anatomogram={this.props.anatomogram}
                                     expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour}
                                     heatmapConfig={this.props.heatmapConfig} profileRows={this.props.profiles.rows}
                                     eventEmitter={anatomogramEventEmitter} />
                        : null}
                    { this.props.heatmapConfig.enableEnsemblLauncher ?
                        <EnsemblLauncher isBaseline={this.props.isBaseline} atlasHost={this.props.heatmapConfig.atlasHost} contextRoot={this.props.heatmapConfig.contextRoot} experimentAccession={this.props.heatmapConfig.experimentAccession} species={this.props.heatmapConfig.species} ensemblDB={this.props.heatmapConfig.ensemblDB} columnType={this.props.heatmapConfig.columnType}
                                         eventEmitter={ensemblEventEmitter} />
                        : null }
                </div>

                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} isWidget={false}
                             ensemblEventEmitter={ensemblEventEmitter} anatomogramEventEmitter={anatomogramEventEmitter} />
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