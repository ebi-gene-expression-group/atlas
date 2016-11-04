"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
require('jquery-hc-sticky');

var URI = require('urijs');

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
            species: React.PropTypes.string.isRequired
        }).isRequired
    },

    render: function () {

        var experimentURL = this.props.linksAtlasBaseURL + this.props.experiment.URL;

        return (
            <div style={{width: "100%", paddingBottom: "20px"}}>
                <div id="experimentDescription">
                    <a id="goto-experiment" className="gxaThickLink" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                </div>
                <div id="experimentOrganisms">Organism: <span style={{"fontStyle":"italic"}}>{this.props.experiment.species}</span></div>
            </div>
        );
    }

});
var AsynchronouslyLoadedHeatmapAnatomogramContainer = React.createClass({
  getDefaultProps: function(){
    return {
      referenceToAnatomogramContainer: "anatomogramContainer"
    }
  },


  _ontologyIdsForTissuesExpressedInAllRows: function(){
    //TODO be less copypastey
    var _expressedFactors= function(expressedFactorsPerRow){
      var o = expressedFactorsPerRow;
      var vs = Object.keys(o).map(function(e){return o[e];});
      return (
        [].concat.apply([],vs)
        .filter(function uniq(e, ix, self) {
            return self.indexOf(e) === ix;
        })
      );
    };
    var _expressedFactorsPerRow = function(profileRows){
      return (
        profileRows
        .reduce(function(result,row){
          result[row.name] =
            row.expressions.filter(function(expression){
              return expression.value;
            })
            .map(function(expression){
              return expression.svgPathId
            });
          return result;
        },{})
      );
    };
    return _expressedFactors(_expressedFactorsPerRow(this.props.profiles.rows));
  },
  _ontologyIdsForTissuesExpressedInRow: function(rowTitle){
    //TODO be more sane
    var _expressedFactorsPerRow = function(profileRows){
      return (
        profileRows
        .reduce(function(result,row){
          result[row.name] =
            row.expressions.filter(function(expression){
              return expression.value;
            })
            .map(function(expression){
              return expression.svgPathId
            });
          return result;
        },{})
      );
    };
    return (
      _expressedFactorsPerRow(this.props.profiles.rows)[rowTitle]
    )
  },
  render: function (){

    var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
    var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";
    var anatomogramConfig = {
      pathToFolderWithBundledResources:this.props.pathToFolderWithBundledResources,
      anatomogramData: this.props.anatomogramData,
      expressedTissueColour: anatomogramExpressedTissueColour,
      hoveredTissueColour: anatomogramHoveredTissueColour,
      idsExpressedInExperiment:this._ontologyIdsForTissuesExpressedInAllRows(),
      eventEmitter: this.props.anatomogramEventEmitter,
      atlasBaseURL: this.props.atlasBaseURL
    }

    var homoSapiensCellLine = this.props.heatmapConfig.species === "homo sapiens" && new URI(this.props.sourceURL).search(true).source === "CELL_LINE";
    var Component, componentProps;
    if(homoSapiensCellLine){
      Component = ExperimentsList;
      componentProps = {
         profiles:this.props.profiles,
         atlasBaseURL:this.props.atlasBaseURL,
         linksAtlasBaseURL:this.props.linksAtlasBaseURL,
         geneQuery:this.props.heatmapConfig.geneQuery
      };
    } else {
      Component = Heatmap;
      componentProps = {
        type:this.props.type,
                 heatmapConfig:this.props.heatmapConfig,
                 columnHeaders:this.props.columnHeaders,
                 profiles:this.props.profiles,
                 geneSetProfiles:this.props.geneSetProfiles,
                 anatomogramEventEmitter:this.props.anatomogramEventEmitter,
                 atlasBaseURL:this.props.atlasBaseURL,
                 linksAtlasBaseURL:this.props.linksAtlasBaseURL,
                 googleAnalyticsCallback:this.props.googleAnalyticsCallback
      };
    }
    var Wrapped = Anatomogram.wrapComponent(anatomogramConfig, Component, componentProps);
    return (
      this.props.anatomogramData
          ? <Wrapped ref={this.props.referenceToAnatomogramContainer}/>
          :<Component {...componentProps} />
    )
  },
  componentDidMount: function(){
    this.props.anatomogramEventEmitter
    .addListener('gxaHeatmapColumnHoverChange',
      function(columnId){
        this.refs[this.props.referenceToAnatomogramContainer].setState({ontologyIdsForComponentContentUnderFocus:columnId?[columnId]:[]});
      }.bind(this));
    this.props.anatomogramEventEmitter
    .addListener('gxaHeatmapRowHoverChange',
      function(rowId){
        this.refs[this.props.referenceToAnatomogramContainer].setState({ontologyIdsForComponentContentUnderFocus: rowId? this._ontologyIdsForTissuesExpressedInRow(rowId): []});
    }.bind(this));
  }
});
var HeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        pathToFolderWithBundledResources: React.PropTypes.string.isRequired,
        sourceURL: React.PropTypes.string.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        type: React.PropTypes.oneOf([
            ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE
        ]).isRequired,
        showAnatomogram: React.PropTypes.bool.isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
        fail: React.PropTypes.func,
        googleAnalyticsCallback: React.PropTypes.func,
        anatomogramEventEmitter: React.PropTypes.object.isRequired,
        facetsEventEmitter: React.PropTypes.object,
    },

    render: function () {

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&organism=" + this.state.heatmapConfig.species;


        return (
            <div ref="this">

                { this.state.experimentData ?
                    <ExperimentDescription experiment={this.state.experimentData} linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                    : null
                }

                { this.state.heatmapConfig
                  ?   <AsynchronouslyLoadedHeatmapAnatomogramContainer {...this.props} {...this.state} />
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
            profiles: {
                rows: [],
                minExpressionLevel: 0,
                maxExpressionLevel: 0
            },
            jsonCoexpressions:[],
            geneSetProfiles: {},
            anatomogramData: {},
            experimentData: '',
            googleAnalyticsCallback: function (){}
        }
    },

    _updateStateAsynchronously: function (data) {
        this.setState({
            heatmapConfig: data.config,
            columnHeaders: data.columnHeaders,
            profiles: data.profiles,
            jsonCoexpressions : data.jsonCoexpressions,
            geneSetProfiles: data.geneSetProfiles,
            anatomogramData: data.anatomogram,
            experimentData: data.experiment
        });
    },

    componentDidMount: function() {
        var handleError = function(jqXHR, textStatus, errorThrown) {
            if (this.props.fail) {
                this.props.fail(jqXHR, textStatus, errorThrown);
            } else if (textStatus === "parsererror") {
                $(ReactDOM.findDOMNode(this.refs.this)).html('<div class="gxaError">Could not parse JSON response</div>');
            } else {
                $(ReactDOM.findDOMNode(this.refs.this)).html('<div class="gxaError">' + jqXHR.responseText + '</div>');
            }
        }.bind(this);

        var handleSuccess = function(data) {
            if ("error" in data) {
                handleError({responseText: data.error});
            } else {
                this._updateStateAsynchronously(data);
            }
        }.bind(this);

        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET",
            success: handleSuccess,
            error: handleError
        };

        this.serverRequest = $.ajax(httpRequest);

        if (!this.props.disableGoogleAnalytics) {
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-37676851-1', 'auto');
            ga('send', 'pageview');
            this.setState({googleAnalyticsCallback: ga});
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
