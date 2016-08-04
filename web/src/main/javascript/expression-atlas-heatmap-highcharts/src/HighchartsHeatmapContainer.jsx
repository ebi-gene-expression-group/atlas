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
        anatomogramEventEmitter: React.PropTypes.object.isRequired,
        anatomogramDataEventEmitter: React.PropTypes.object
    },

    render: function () {

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&conditionQuery=" + this.state.heatmapConfig.conditionQuery +
            "&organism=" + this.state.heatmapConfig.species;

            var display;
            var marginLeft;

            if (this.state.anatomogramData) {
                display = this.props.showAnatomogram ? "block" : "none";
                marginLeft = this.props.showAnatomogram ? "270px" : "0";
            } else {
                display = "none";
                marginLeft = "0";
            }

        return (
          <div ref="this">
              { this.state.experimentData ?
                  <ExperimentDescription experiment={this.state.experimentData} linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                  : null
              }

              { this.state.ajaxCompleted ?
                  this.state.error ?
                    <div ref="gxaError">
                        {this.state.error}
                    </div>
                  :
                    <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                        <div ref="anatomogramEnsembl" className="gxaAside" style={{display: display}}>
                            { this.props.showAnatomogram && this.state.anatomogramData && Object.keys(this.state.anatomogramData).length
                              ? <Anatomogram
                                  pathToFolderWithBundledResources={this.props.pathToFolderWithBundledResources}
                                  anatomogramData={this.state.anatomogramData}
                                   expressedTissueColour={this._isExperimentPage()? "gray":"red"} hoveredTissueColour={this._isExperimentPage()? "red" :"purple"}
                                   profileRows={this.state.profiles.rows} eventEmitter={this.props.anatomogramEventEmitter} atlasBaseURL={this.props.atlasBaseURL}/>
                              : null
                            }
                        </div>

                        <div id="heatmap-react" className="gxaInnerHeatmap" style={{marginLeft: marginLeft, display:"block"}}>
                            <HighchartsHeatmap
                                profiles={this.state.profiles}
                                heatmapConfig={this.state.heatmapConfig}
                                anatomogramEventEmitter={this.props.anatomogramEventEmitter}
                                googleAnalyticsCallback={this.state.googleAnalyticsCallback}
                                heatmapData={this.state.heatmapData}
                                afterHeatmapRedrawn={this._attachListenersToLabels}
                            />
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

    _attachListenersToLabels: function() {
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
              this.props.anatomogramEventEmitter.emit('gxaHeatmapRowHoverChange', title);
            }
              ,
              function onMouseLeaveSendNull() {
              this.props.anatomogramEventEmitter.emit('gxaHeatmapRowHoverChange', null);
            } , this, this);
        }
      }, this);
    },

    componentDidUpdate: function() {
        this._attachListenersToLabels();

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

    handleAjaxFailure: function (jqXHR, textStatus, errorThrown) {
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

    onAjaxDone: function (data, textStatus, jqXHR){
      if(! this.isMounted()){
        this.handleAjaxFailure(jqXHR, textStatus, "DOM element not mounted!");
      } else if (data.hasOwnProperty('error')) {
        this.handleAjaxFailure(jqXHR, textStatus, data.error);
      } else {
        this.onAjaxSuccessful(data);
      }
    },

    _isExperimentPage: function(){
      return this.props.sourceURL.indexOf("/json/experiments/") >-1;
    },

    onAjaxSuccessful: function(data){
      var config = {
        geneQuery: data.config.geneQuery,
        atlasBaseURL: this.props.atlasBaseURL,
        isExperimentPage: this._isExperimentPage(),
        isMultiExperiment: this.props.isMultiExperiment,
        isReferenceExperiment: !this.props.isMultiExperiment && this.props.sourceURL.indexOf("/json/experiments/") === -1,
        isDifferential: this.props.isDifferential
      };
      //See in heatmap-data.jsp which thirteen properties this config is populated with.
      for(var key in data.config){
        if(data.config.hasOwnProperty(key)){
          config[key]=data.config[key];
        }
      }

      this.setState({
          ajaxCompleted: true,
          heatmapConfig: config,
          columnHeaders: data.columnHeaders,
          profiles: data.profiles,
          jsonCoexpressions : data.jsonCoexpressions,
          geneSetProfiles: data.geneSetProfiles,
          anatomogramData: data.anatomogram,
          experimentData: data.experiment,
          heatmapData: HeatmapData.get(data.profiles.rows, data.columnHeaders, config)
      });
    },

    componentDidMount: function() {
        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET"
        };

        $.ajax(httpRequest).done(this.onAjaxDone).fail(this.handleAjaxFailure);

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

module.exports = Container;
