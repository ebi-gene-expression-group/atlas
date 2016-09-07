"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var Snap = require('imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js');

var $ = require('jquery');

var HeatmapData= require('./DataForHighcharts.js');

//*------------------------------------------------------------------*

var HighchartsHeatmap = require('./HighchartsHeatmap.jsx');
require('./HighchartsHeatmapContainer.css');

var Anatomogram = require('anatomogram');

var genomeBrowserTemplate = require('./genomeBrowserLink.js');


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

var Container = React.createClass({

  getDefaultProps: function(){
    return {
      referenceToAnatomogramContainer: "anatomogramContainer"
    };
  },
  render: function(){
    var heatmapProps = {
      heatmapConfig:this.props.heatmapConfig,
      googleAnalyticsCallback:this.props.googleAnalyticsCallback,
      heatmapData:this.props.heatmapData,
      onHeatmapRedrawn:this._attachListenersToLabels,
    };//overriden: ontologyIdsToHighlight, onOntologyIdIsUnderFocus
    var anatomogramConfig = {
      pathToFolderWithBundledResources:this.props.pathToFolderWithBundledResources,
      anatomogramData: this.props.anatomogramData,
      expressedTissueColour: this.props.heatmapConfig.isExperimentPage? "gray":"red",
      hoveredTissueColour: this.props.heatmapConfig.isExperimentPage? "red" :"purple",
      atlasBaseURL: this.props.atlasBaseURL,
      idsExpressedInExperiment:this._ontologyIdsForTissuesExpressedInAllRows()
    };
    var Wrapped = Anatomogram.wrapComponent(anatomogramConfig, HighchartsHeatmap, heatmapProps);
    return (
      this._showAnatomogram()
      ? <Wrapped ref={this.props.referenceToAnatomogramContainer}/>
      :<HighchartsHeatmap {...heatmapProps} ontologyIdsToHighlight={[]} onOntologyIdIsUnderFocus={function(){}}/>
    );
  },
  _showAnatomogram: function(){
    return (
      this.props.showAnatomogram
      && this.props.anatomogramData
      && Object.keys(this.props.anatomogramData).length
    );
  },

  componentDidMount: function() {
    this._attachListenersToLabels();
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

  _attachListenersToLabels: function() {
    if(!this._showAnatomogram()){
      return;
    }
    /*
    I am a hack and I attach event listeners to the labels.
    There seems to be no way to do it in the HighchartsHeatmap component -
    the labels that are selected when HighchartsHeatmap.componentDidUpdate is called are redrawn when both components appear on the screen
    */
    Snap.selectAll('.highcharts-yaxis-labels > *')
    .forEach(function (v) {
      //careful - if the label doesn't fit, the element will have two children: displayed and full title
      //here we assume the longest text is the correct title of the experiment
      var title =
        v.selectAll('*').items
        .map(function(c){return c.node.textContent})
        .reduce(function(l,r){return l.length > r.length? l : r}, "");
      if (title) {
        v.hover(
          function onMouseEnterSendTitle() {
            this.refs[this.props.referenceToAnatomogramContainer].setState({ontologyIdsForComponentContentUnderFocus: this._ontologyIdsForTissuesExpressedInRow(title)});
          }
            ,
            function onMouseLeaveSendNull() {
            this.refs[this.props.referenceToAnatomogramContainer].setState({ontologyIdsForComponentContentUnderFocus: []});
          } , this, this);
      }
    }, this);
  }

})

var ContainerLoader = React.createClass({
    propTypes: {
        pathToFolderWithBundledResources: React.PropTypes.string.isRequired,
        sourceURL: React.PropTypes.string.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        showAnatomogram:React.PropTypes.bool.isRequired,
        isDifferential: React.PropTypes.bool.isRequired,
        isMultiExperiment: React.PropTypes.bool.isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
        fail: React.PropTypes.func,
        googleAnalyticsCallback: React.PropTypes.func,
        anatomogramDataEventEmitter: React.PropTypes.object
    },

    render: function () {

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&conditionQuery=" + this.state.heatmapConfig.conditionQuery +
            "&organism=" + this.state.heatmapConfig.species;


        return (
          <div ref="this">
              { this._isReferenceExperiment() && this.state.experimentData ?
                  <ExperimentDescription experiment={this.state.experimentData} linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                  : null
              }
              { this.state.ajaxCompleted ?
                  this.state.error ?
                    <div ref="gxaError">
                        {this.state.error}
                    </div>
                  :
                    <Container {...this.props} {...this.state} />
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

    componentDidUpdate: function() {
        if (this.props.anatomogramDataEventEmitter) {
            if (this.state.anatomogramData && Object.keys(this.state.anatomogramData).length !== 0) {
                this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', true);
            } else {
                this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', false);
            }
        }
    },

    getInitialState: function() {
        return {
            ajaxCompleted: false,
            error: false,
            heatmapConfig: {},
            profiles: {
                rows: [],
                minExpressionLevel: 0,
                maxExpressionLevel: 0
            },
            jsonCoexpressions : [],
            geneSetProfiles: {},
            anatomogramData: {},
            googleAnalyticsCallback: function (){},
            heatmapData: HeatmapData.EMPTY
        }
    },

    _handleAjaxFailure: function (jqXHR, textStatus, errorThrown) {
      if (this.props.fail) {
        this.props.fail(jqXHR, textStatus, errorThrown);
      } else {
        this.setState({
          ajaxCompleted: true,
          error:
            textStatus === "parsererror"
            ? "Could not parse JSON response"
            : errorThrown
        });
      }
    },

    _onAjaxDone: function (data, textStatus, jqXHR){
      if(! this.isMounted()){
        this._handleAjaxFailure(jqXHR, textStatus, "DOM element not mounted!");
      } else if (data.hasOwnProperty('error')) {
        this._handleAjaxFailure(jqXHR, textStatus, data.error);
      } else {
        this.onAjaxSuccessful(data);
      }
    },

    _isExperimentPage: function(){
      return this.props.sourceURL.indexOf("/json/experiments/") >-1;
    },

    _isReferenceExperiment: function(){
      return !this.props.isMultiExperiment && !this._isExperimentPage();
    },

    _introductoryMessage: function(profiles) {
        var shownRows = profiles.rows.length,
            totalRows = profiles.searchResultTotal;

        var what =
            (this.props.isMultiExperiment ? 'experiment' : 'gene') +
            (totalRows > 1 ? 's' : '');

        return 'Showing ' + shownRows + ' ' +
            (totalRows === shownRows ? what + ':' : 'of ' + totalRows + ' ' + what + ' found:');
    },

    onAjaxSuccessful: function(data){
      var capitalizeFirstLetter = function(str){
        return !str? str: str.charAt(0).toUpperCase() + str.substr(1);
      }
      var config = {
        geneQuery: data.config.geneQuery,
        atlasBaseURL: this.props.atlasBaseURL,
        isExperimentPage: this._isExperimentPage(),
        isMultiExperiment: this.props.isMultiExperiment,
        isReferenceExperiment: this._isReferenceExperiment(),
        isDifferential: this.props.isDifferential,
        introductoryMessage: this._introductoryMessage(data.profiles),
        xAxisLegendName: capitalizeFirstLetter(data.config.columnType) || "Experimental condition",
        yAxisLegendName: this._isExperimentPage() ? "Gene name": "Experiment",
      };
      //See in heatmap-data.jsp which thirteen properties this config is populated with.
      Object.assign(config, data.config);
      Object.assign(config,{genomeBrowserTemplate: this._isExperimentPage()? genomeBrowserTemplate(config):""});


      this.setState({
          ajaxCompleted: true,
          heatmapConfig: Object.freeze(config),
          columnHeaders: data.columnHeaders,
          profiles: data.profiles,
          jsonCoexpressions : data.jsonCoexpressions,
          anatomogramData: data.anatomogram,
          experimentData: data.experiment,
          heatmapData: HeatmapData.get(data, config)
      });
    },

    componentDidMount: function() {
        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET"
        };

        $.ajax(httpRequest).done(this._onAjaxDone).fail(this._handleAjaxFailure);

        if (!this.props.disableGoogleAnalytics) {
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-37676851-1', 'auto');
            ga('send', 'pageview');
            this.setState({googleAnalyticsCallback: ga});
        }
    }
});

//*------------------------------------------------------------------*

module.exports = ContainerLoader;
