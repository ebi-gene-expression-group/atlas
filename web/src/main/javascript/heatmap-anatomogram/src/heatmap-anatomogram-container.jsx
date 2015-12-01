"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('../lib/jquery.hc-sticky.js');

var EventEmitter = require('wolfy87-eventemitter');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var Heatmap = require('./heatmap.jsx');

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

var ExperimentDescription = React.createClass({

    render: function () {

        var experimentURL = this.props.linksAtlasBaseURL + this.props.experiment.URL;

        return (
            <div style={{width: "100%", paddingBottom: "20px"}}>
                <div id="experimentDescription">
                    <a id="goto-experiment" className="gxaThickLink" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                </div>
                <div id="experimentOrganisms">Organism(s): <span style={{"fontStyle":"italic"}}>{this.props.experiment.allSpecies}</span></div>
            </div>
        );
    }

});

var TypeEnum = {
    BASELINE: { isBaseline: true, heatmapTooltip: '#heatMapTableCellInfo' },
    PROTEOMICS_BASELINE: { isBaseline: true, isProteomics: true, heatmapTooltip: '#heatMapTableCellInfo-proteomics' },
    DIFFERENTIAL: { isDifferential: true, heatmapTooltip: '#heatMapTableCellInfo-differential' },
    MULTIEXPERIMENT: { isMultiExperiment: true, heatmapTooltip: '#heatMapTableCellInfo-multiexp' }
};


var HeatmapAnatomogramContainer = React.createClass({
    // TODO Keep populating propTypes until we have everything here
    propTypes: {
        type: React.PropTypes.oneOf(["isBaseline", "isMultiExperiment", "isDifferential", "isProteomics"]).isRequired,
        showAnatomogram: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var type;
        if(this.props.type == "isBaseline") {
            type = TypeEnum.BASELINE;
        } else if(this.props.type == "isMultiExperiment") {
            type = TypeEnum.MULTIEXPERIMENT;
        } else if(this.props.type == "isDifferential") {
            type = TypeEnum.DIFFERENTIAL;
        } else if(this.props.type == "isProteomics") {
            type = TypeEnum.PROTEOMICS_BASELINE;
        }

        var anatomogramExpressedTissueColour = type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = type.isMultiExperiment ? "indigo" : "red";

        var heatmapConfig = this.props.heatmapConfig;

        var geneURL = heatmapConfig.linksAtlasBaseURL + '/query?geneQuery=' + heatmapConfig.geneQuery + '&exactMatch=' + heatmapConfig.isExactMatch + "&organism=" + heatmapConfig.species;

        var display = this.props.showAnatomogram ? "block" : "none";
        var marginLeft = this.props.showAnatomogram ? "270px" : "0";

        return (
            <div className="gxaBlock">

                { this.props.experiment ? <ExperimentDescription experiment={this.props.experiment} linksAtlasBaseURL={this.props.heatmapConfig.linksAtlasBaseURL}/> : null }

                <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                    <div ref="anatomogramEnsembl" className="gxaAside" style={{display: display}}>
                        { this.props.anatomogram ?
                            <Anatomogram anatomogramData={this.props.anatomogram}
                                         expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour}
                                         profileRows={this.props.profiles.rows} eventEmitter={anatomogramEventEmitter} atlasBaseURL={this.props.heatmapConfig.atlasBaseURL}/>
                            : null
                        }
                    </div>

                    <div id="heatmap-react" className="gxaInnerHeatmap" style={{marginLeft: marginLeft}}>
                        <Heatmap type={type}
                                 heatmapConfig={this.props.heatmapConfig}
                                 columnHeaders={this.props.columnHeaders}
                                 profiles={this.props.profiles}
                                 geneSetProfiles={this.props.geneSetProfiles}
                                 isWidget={this.props.isWidget}
                                 ensemblEventEmitter={ensemblEventEmitter}
                                 anatomogramEventEmitter={anatomogramEventEmitter}
                                 showAnatomogram={this.props.showAnatomogram} />
                    </div>

                </div>

                { this.props.isWidget ?
                        <div><p><a href={geneURL}>See more expression data at Expression Atlas.</a>
                            <br/>This expression view is provided by <a href={this.props.heatmapConfig.atlasBaseURL}>Expression Atlas</a>.
                            <br/>Please direct any queries or feedback to <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a></p>
                        </div>
                    :
                    null}

            </div>
        );
    },

    componentDidMount: function() {
        var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
        $anatomogram.on(
            "gxaAnatomogramSticky",
            function() {
                $anatomogram.hcSticky({responsive: true});
            }
        );

        if (this.props.showAnatomogram) {
            $anatomogram.hcSticky({responsive: true});
        }

        var ga = this.props.disableGoogleAnalytics === undefined ? false : this.props.disableGoogleAnalytics;
        if (!ga) {
            var _gaq = _gaq || [];
            _gaq.push(["_setAccount", "UA-37676851-1"]);
            _gaq.push(["_trackPageview"]);
            (function () {
                var ga = document.createElement("script");
                ga.type = "text/javascript";
                ga.async = true;
                ga.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(ga, s);
            })();
        }
    },

    componentDidUpdate: function() {
        if (this.props.showAnatomogram) {
            var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
            $anatomogram.hcSticky({responsive: true});
        }
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapAnatomogramContainer;