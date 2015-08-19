"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('../lib/jquery.hc-sticky.js');

var URI = require('URIjs');

var EventEmitter = require('wolfy87-eventemitter');

//*------------------------------------------------------------------*

require('../css/heatmap-and-anatomogram.css');

var Anatomogram = require('./anatomogram.jsx');
var EnsemblLauncher = require('./ensembl-launcher.jsx');

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
        isWidget: React.PropTypes.bool.isRequired
    },

    render: function () {
        var eventEmitter = new EventEmitter();

        var Heatmap = this.props.Heatmap;

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">
                    { this.props.anatomogram ?
                        <Anatomogram anatomogram={this.props.anatomogram} heatmapConfig={this.props.heatmapConfig} />
                        : null}
                    { this.props.heatmapConfig.enableEnsemblLauncher ?
                        <EnsemblLauncher isBaseline={this.props.isBaseline} atlasHost={this.props.heatmapConfig.atlasHost} contextRoot={this.props.heatmapConfig.contextRoot} experimentAccession={this.props.heatmapConfig.experimentAccession} species={this.props.heatmapConfig.species} ensemblDB={this.props.heatmapConfig.ensemblDB} columnType={this.props.heatmapConfig.columnType}
                                         eventEmitter={eventEmitter} />
                        : null }
                </div>

                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} isWidget={false}
                             eventEmitter={eventEmitter}/>
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