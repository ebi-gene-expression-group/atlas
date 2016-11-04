"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
require('jquery.browser');
require('jquery-hc-sticky');

var EventEmitter = require('events');

//*------------------------------------------------------------------*

var Anatomogram = require('anatomogram');
var FeedbackSmileys=require('expression-atlas-feedback');
var Heatmap = require('./Heatmap.jsx');
var EnsemblLauncher = require('./EnsemblLauncher.jsx');

var ExperimentTypes = require('./experimentTypes.js');

//*------------------------------------------------------------------*

require('./ExperimentPageHeatmapAnatomogramContainer.css');

//*------------------------------------------------------------------*

var AsynchronouslyLoadedInternalHeatmapAnatomogramContainer = React.createClass({
  propTypes: {
    sourceURL: React.PropTypes.string.isRequired,
    type: React.PropTypes.oneOf([
        ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE
    ]).isRequired,
    atlasBaseURL: React.PropTypes.string.isRequired,
    linksAtlasBaseURL: React.PropTypes.string.isRequired,
    pathToFolderWithBundledResources: React.PropTypes.string.isRequired
  },
  getInitialState: function() {
    return {
      heatmapData: {}
    };
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
              if (textStatus === "parsererror") {
                  $(ReactDOM.findDOMNode(this.refs.this)).html("<div class='gxaError'>Could not parse JSON response</div>");
              } else {
                  $(ReactDOM.findDOMNode(this.refs.this)).html(jqXHR.responseText);
              }
          }.bind(this)
      );
  },

  _updateStateAsynchronously: function (data) {
      this.setState({heatmapData:data});
  },

  render: function () {
    return Object.keys(this.state.heatmapData).length
      ?
        this.state.heatmapData.error
        ? <p>{this.state.heatmapData.error}</p>
        : <InternalHeatmapAnatomogramContainer
            type={this.props.type}
            heatmapConfig={this.state.heatmapData.config}
            isWidget={false}
            anatomogram={this.state.heatmapData.anatomogram}
            columnHeaders={this.state.heatmapData.columnHeaders}
            multipleColumnHeaders={this.state.heatmapData.multipleColumnHeaders}
            profiles={this.state.heatmapData.profiles}
            jsonCoexpressions={this.state.heatmapData.jsonCoexpressions}
            geneSetProfiles={this.state.heatmapData.geneSetProfiles}
            atlasBaseURL={this.state.heatmapData.config.atlasHost+this.state.heatmapData.config.contextRoot}
            linksAtlasBaseURL={this.props.linksAtlasBaseURL}
            pathToFolderWithBundledResources={this.props.pathToFolderWithBundledResources}/>
      : <div ref="loadingImagePlaceholder">
          <img src={this.props.atlasBaseURL + "/resources/images/loading.gif"}/>
      </div>
  }
});

var InternalHeatmapAnatomogramContainer = React.createClass({
    propTypes: {
        pathToFolderWithBundledResources: React.PropTypes.string.isRequired,
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
        multipleColumnHeaders: React.PropTypes.object,
        profiles: React.PropTypes.object.isRequired,
        jsonCoexpressions: React.PropTypes.arrayOf(React.PropTypes.shape({
          geneId: React.PropTypes.string.isRequired,
          geneName: React.PropTypes.string.isRequired,
          jsonProfiles: React.PropTypes.object
        })),
        geneSetProfiles: React.PropTypes.object,
        heatmapConfig: React.PropTypes.object.isRequired,
        type: React.PropTypes.oneOf([
            ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE
        ]).isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        ensemblEventEmitter : React.PropTypes.object.isRequired,
        anatomogramEventEmitter: React.PropTypes.object.isRequired
    },

    getDefaultProps: function (){
      //one ExperimentPageHeatmapAnatomogramContainer per page so this is fine- otherwise the event emitters would be shared
      var ensemblEventEmitter = new EventEmitter();
      ensemblEventEmitter.setMaxListeners(0);
      var anatomogramEventEmitter = new EventEmitter();
      anatomogramEventEmitter.setMaxListeners(0);
      return {ensemblEventEmitter: ensemblEventEmitter, anatomogramEventEmitter:anatomogramEventEmitter };
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

    getInitialState: function () {
      return {googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function (){},idsToBeHighlighted: []}
    },

    render: function () {

        var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
        var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";

        var prefFormDisplayLevels = $('#displayLevels');

        var feedbackSmileys = $.browser.msie ? null
            :
            <div className="gxaHeatmapPosition gxaFeedbackBoxWrapper">
            <FeedbackSmileys collectionCallback= {
                    function(score,comment){
                      this.state.googleAnalyticsCallback(
                        'send','event','HeatmapReact', 'feedback',
                        comment,score);
                    }.bind(this)} />
            </div>;

        return (
            <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                <div ref="anatomogramEnsembl" className="gxaAside">
                    { this.props.anatomogram ?
                        Anatomogram.create({
                          pathToFolderWithBundledResources:this.props.pathToFolderWithBundledResources,
                          anatomogramData: this.props.anatomogram,
                          expressedTissueColour: anatomogramExpressedTissueColour,
                          hoveredTissueColour: anatomogramHoveredTissueColour,
                          profileRows: this.props.profiles.rows,
                          eventEmitter: this.props.anatomogramEventEmitter,
                          atlasBaseURL: this.props.atlasBaseURL,
                          idsToBeHighlighted: this.state.idsToBeHighlighted
                        })
                        : null}
                    { this.props.heatmapConfig.enableEnsemblLauncher ?
                        <EnsemblLauncher isBaseline={this.props.type === ExperimentTypes.BASELINE || this.props.type === ExperimentTypes.PROTEOMICS_BASELINE}
                                         experimentAccession={this.props.heatmapConfig.experimentAccession}
                                         species={this.props.heatmapConfig.species}
                                         ensemblDB={this.props.heatmapConfig.ensemblDB}
                                         columnType={this.props.heatmapConfig.columnType}
                                         eventEmitter={this.props.ensemblEventEmitter}
                                         atlasBaseURL={this.props.atlasBaseURL} />
                        : null }
                </div>
                <div id="heatmap-react" className="gxaHeatmapPosition">
                    <Heatmap type={this.props.type}
                             heatmapConfig={this.props.heatmapConfig}
                             columnHeaders={this.props.columnHeaders}
                             multipleColumnHeaders={this.props.multipleColumnHeaders}
                             profiles={this.props.profiles}
                             jsonCoexpressions={ this.props.jsonCoexpressions}
                             geneSetProfiles={this.props.geneSetProfiles}
                             isWidget={false}
                             prefFormDisplayLevels={prefFormDisplayLevels}
                             ensemblEventEmitter={this.props.ensemblEventEmitter}
                             anatomogramEventEmitter={this.props.anatomogramEventEmitter}
                             atlasBaseURL={this.props.atlasBaseURL}
                             linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                             googleAnalyticsCallback={this.state.googleAnalyticsCallback}/>
                </div>
                {feedbackSmileys}
            </div>
        );
    },

    componentDidMount: function() {
      var $anatomogramEnsemblAside = $(ReactDOM.findDOMNode(this.refs.anatomogramEnsembl));
      $anatomogramEnsemblAside.hcSticky({responsive: true});

      $(document).ready(function () {
        this.setState({googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function (){}})
      }.bind(this)
      )
      this.props.anatomogramEventEmitter
      .addListener('gxaHeatmapColumnHoverChange',
        function(columnId){
          this.setState({idsToBeHighlighted:columnId?[columnId]:[]});
        }.bind(this));
      this.props.anatomogramEventEmitter
      .addListener('gxaHeatmapRowHoverChange',
        function(rowId){
          this.setState({idsToBeHighlighted: rowId? this._ontologyIdsForTissuesExpressedInRow(rowId): []});
      }.bind(this));
    }
});

//*------------------------------------------------------------------*

module.exports = AsynchronouslyLoadedInternalHeatmapAnatomogramContainer;
