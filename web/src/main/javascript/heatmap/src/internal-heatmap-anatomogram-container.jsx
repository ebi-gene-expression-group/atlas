"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('../lib/jquery.hc-sticky.js');

var URI = require('URIjs');

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

var heatmapModule = require('./heatmap.jsx');
var Anatomogram = require('./anatomogram.jsx');

//*------------------------------------------------------------------*

var InternalHeatmapAnatomogramContainer = React.createClass({

    render: function () {

        var Heatmap = this.props.Heatmap;
        var EnsemblLauncher = this.props.EnsemblLauncher;

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">

                    { this.props.anatomogram ? <Anatomogram anatomogram={this.props.anatomogram} /> : null}
                    { EnsemblLauncher ? <EnsemblLauncher/> : null }

                </div>

                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} isWidget={false}/>
                </div>

            </div>
        );
    },

    componentDidMount: function() {
        var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
        $anatomogram.hcSticky({responsive: true});
    }
});

//*------------------------------------------------------------------*

module.exports = InternalHeatmapAnatomogramContainer;