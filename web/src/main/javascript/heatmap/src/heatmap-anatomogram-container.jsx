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

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

var ExperimentDescription = React.createClass({

    render: function () {

        var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;

        return (
            <div style={{width: "100%"}}>
                <div id="experimentDescription">
                    <a id="goto-experiment" className="gxaThickLink" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                </div>
                <div id="experimentOrganisms">Organism(s): <span style={{"fontStyle":"italic"}}>{this.props.experiment.allSpecies}</span></div>
            </div>
        );
    }

});


var HeatmapAnatomogramContainer = React.createClass({

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var anatomogramExpressedTissueColour = this.props.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.isMultiExperiment ? "indigo" : "red";

        var Heatmap = this.props.Heatmap;

        var heatmapConfig = this.props.heatmapConfig;

        var geneURL = heatmapConfig.contextRoot +  'query?geneQuery=' + heatmapConfig.geneQuery + '&exactMatch=' + heatmapConfig.isExactMatch + "&organism=" + heatmapConfig.species;
        var normalizedGeneURL = URI(geneURL).normalize();

        return (
                <div className="gxaBlock">

                    { this.props.experiment ? <ExperimentDescription experiment={this.props.experiment} /> : null }

                    <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                        <div ref="anatomogramEnsembl" className="gxaAside">
                            { this.props.anatomogram ?
                                <Anatomogram anatomogram={this.props.anatomogram} expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour} heatmapConfig={this.props.heatmapConfig} profileRows={this.props.profiles.rows} eventEmitter={anatomogramEventEmitter} />
                                : null}
                        </div>

                        <div id="heatmap-react" className="gxaHeatmapPosition">
                            <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} isWidget={this.props.isWidget}
                                     ensemblEventEmitter={ensemblEventEmitter} anatomogramEventEmitter={anatomogramEventEmitter} />
                        </div>

                        {/* TODO move into help tooltips module */}
                        <div id="help-placeholder" style={{display: "none"}}></div>

                    </div>

                    { this.props.isWidget ?
                            <div><p><a href={normalizedGeneURL}>See more expression data at Expression Atlas.</a>
                                <br/>This expression view is provided by <a href="http://www.ebi.ac.uk/gxa">Expression Atlas</a>.
                                <br/>Please direct any queries or feedback to <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a></p>
                            </div>
                        :
                        null}

                </div>
        );
    },

    componentDidMount: function() {
        var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
        $anatomogram.hcSticky({responsive: true});


        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-37676851-1']);
        _gaq.push(['_trackPageview']);
        (function () {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapAnatomogramContainer;