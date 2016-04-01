"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
require('jQuery-ajaxTransport-XDomainRequest');
require('jquery-hc-sticky');

var URI = require('urijs');

var EventEmitter = require('events');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var Heatmap = require('./Heatmap.jsx');
var ExperimentsList = require('./ExperimentsList.jsx');
var ExperimentTypes = require('./experimentTypes.js');

//*------------------------------------------------------------------*

require('./HeatmapAnatomogramContainer.css');

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
        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
        fail: React.PropTypes.func
    },

    render: function () {
        var ensemblEventEmitter = new EventEmitter();
        ensemblEventEmitter.setMaxListeners(0);
        var anatomogramEventEmitter = new EventEmitter();
        anatomogramEventEmitter.setMaxListeners(0);

        var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&exactMatch=" + this.state.heatmapConfig.isExactMatch +
            "&organism=" + this.state.heatmapConfig.species;

        var display = this.props.showAnatomogram ? "block" : "none";
        var marginLeft = this.props.showAnatomogram ? "270px" : "0";

        var homoSapiensCellLine = this.state.heatmapConfig.species === "homo sapiens" && new URI(this.props.sourceURL).search(true).source === "CELL_LINE";

        return (
            <div ref="this">

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

                        { !homoSapiensCellLine ?
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
                            </div> :
                            <div style={{marginLeft: marginLeft}}>
                                <ExperimentsList profiles={this.state.profiles}
                                                 atlasBaseURL={this.props.atlasBaseURL}
                                                 linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                                                 geneQuery={this.state.heatmapConfig.geneQuery}/>
                            </div>
                        }

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

    _updateStateAsynchronously: function (data) {
        this.setState({
            heatmapConfig: data.config,
            columnHeaders: data.columnHeaders,
            nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
            profiles: data.profiles,
            geneSetProfiles: data.geneSetProfiles,
            anatomogramData: data.anatomogram,
            experimentData: data.experiment
        })
    },

    componentDidMount: function() {
        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET"
        };

        this.serverRequest = $.ajax(httpRequest).done(
             this._updateStateAsynchronously
        ).fail(
            function (jqXHR, textStatus, errorThrown) {
                if (this.props.fail) {
                    this.props.fail(jqXHR, textStatus, errorThrown);
                } else if (textStatus === "parsererror") {
                    $(ReactDOM.findDOMNode(this.refs.this)).html("<div class='gxaError'>Could not parse JSON response</div>");
                } else {
                    $(ReactDOM.findDOMNode(this.refs.this)).html(jqXHR.responseText);
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
        var $anatomogram = $(ReactDOM.findDOMNode(this.refs.anatomogramEnsembl));

        if (this.props.showAnatomogram) {
            $anatomogram.hcSticky({responsive: true});
        }

        $(window).trigger("gxaResizeHeatmapAnatomogramHeader");
    },
    componentWillUnmount: function() {
        this.serverRequest.abort();
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapAnatomogramContainer;