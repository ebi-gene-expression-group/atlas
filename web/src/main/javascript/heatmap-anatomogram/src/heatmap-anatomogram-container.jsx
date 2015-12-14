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
var ExperimentTypes = require('./experiment-types.js');

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

var ExperimentDescription = React.createClass({
    propTypes: {
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        experiment: React.PropTypes.shape({
            URL: React.PropTypes.string.isRequired,
            description: React.PropTypes.string.isRequired,
            allSpecies: React.PropTypes.string.isRequired
        }).isRequired
    },

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

var HeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        sourceURL: React.PropTypes.string.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        type: React.PropTypes.oneOf([
            ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE
        ]).isRequired,
        showAnatomogram: React.PropTypes.bool.isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool.isRequired
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        var anatomogramEventEmitter = new EventEmitter();

        var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&exactMatch=" + this.state.heatmapConfig.isExactMatch +
            "&organism=" + this.state.heatmapConfig.species;

        var display = this.props.showAnatomogram ? "block" : "none";
        var marginLeft = this.props.showAnatomogram ? "270px" : "0";

        return (
            <div ref="this" className="gxaBlock">

                { this.state.experimentData ?
                        <ExperimentDescription experiment={this.state.experimentData} linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                        : null
                }

                { this.state.heatmapConfig ?
                        <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                            <div ref="anatomogramEnsembl" className="gxaAside" style={{display: display}}>
                                { this.state.anatomogramData ?
                                <Anatomogram anatomogramData={this.state.anatomogramData}
                                             expressedTissueColour={anatomogramExpressedTissueColour} hoveredTissueColour={anatomogramHoveredTissueColour}
                                             profileRows={this.state.profiles.rows} eventEmitter={anatomogramEventEmitter} atlasBaseURL={this.props.atlasBaseURL}/>
                                    : null
                                    }
                            </div>

                            <div id="heatmap-react" className="gxaInnerHeatmap" style={{marginLeft: marginLeft}}>
                                <Heatmap type={this.props.type}
                                         heatmapConfig={this.state.heatmapConfig}
                                         columnHeaders={this.state.columnHeaders}
                                         nonExpressedColumnHeaders={this.state.nonExpressedColumnHeaders}
                                         profiles={this.state.profiles}
                                         geneSetProfiles={this.state.geneSetProfiles}
                                         ensemblEventEmitter={ensemblEventEmitter}
                                         anatomogramEventEmitter={anatomogramEventEmitter}
                                         atlasBaseURL={this.props.atlasBaseURL}
                                         linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                            </div>

                        </div>
                        :
                        <div ref="loadingImagePlaceholder">
                            <img src={this.props.atlasBaseURL + "/resources/images/loading.gif"}/>
                        </div>
                }

                { this.props.isWidget ?
                        <div><p><a href={geneURL}>See more expression data at Expression Atlas.</a>
                            <br/>This expression view is provided by <a href={this.props.linksAtlasBaseURL}>Expression Atlas</a>.
                            <br/>Please direct any queries or feedback to <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a></p>
                        </div>
                        :
                        null
                }

            </div>
        );
    },

    getInitialState: function() {
        return {
            heatmapConfig: '',
            columnHeaders: [],
            nonExpressedColumnHeaders: [],
            profiles: {
                rows: [],
                minExpressionLevel: 0,
                maxExpressionLevel: 0
            },
            geneSetProfiles: {},
            anatomogramData: {},
            experimentData: ''
        }
    },

    componentDidMount: function() {
        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET"
        };

        $.ajax(httpRequest).done(
            function (data) {
                if (this.isMounted()) {
                    this.setState({
                        heatmapConfig: data.config,
                        columnHeaders: data.columnHeaders,
                        nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
                        profiles: data.profiles,
                        geneSetProfiles: data.geneSetProfiles,
                        anatomogramData: data.anatomogram,
                        experimentData: data.experiment
                    });
                }
            }.bind(this)
        ).fail(
            function (jqXHR, textStatus) {
                if (textStatus === "parsererror") {
                    $(this.refs.this.getDOMNode()).html("<div class='error'>Could not parse JSON response</div>");
                } else {
                    $(this.refs.this.getDOMNode()).html(jqXHR.responseText);
                }
            }.bind(this)
        );

        if (!this.props.disableGoogleAnalytics) {
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
        // This mounted component is only going to be updated when changing this.props.showAnatomogram, so we only take
        // care of the anatomogram, the legend and the sticky header (the last two through an event)
        var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());

        if (this.props.showAnatomogram) {
            $anatomogram.hcSticky({responsive: true});
        }

        $(window).trigger("gxaResizeHeatmapAnatomogramHeader");
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapAnatomogramContainer;