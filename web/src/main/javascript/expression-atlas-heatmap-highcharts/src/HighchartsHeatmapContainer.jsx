"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var Snap = require('imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js');

var $ = require('jquery');
require('jQuery-ajaxTransport-XDomainRequest');

//*------------------------------------------------------------------*

var HighchartsHeatmap = require('./HighchartsHeatmap.jsx');
var HighchartsUtils = require('./highchartsUtils.js');
require('./HighchartsHeatmapContainer.css');

var Anatomogram = require('anatomogram');


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

var HighchartsHeatmapContainer = React.createClass({
    propTypes: {
        sourceURL: React.PropTypes.string.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        showAnatomogram:React.PropTypes.bool.isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        isMultiExperiment: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
        fail: React.PropTypes.func,
        googleAnalyticsCallback: React.PropTypes.func,
        ensemblEventEmitter : React.PropTypes.object.isRequired,
        anatomogramEventEmitter: React.PropTypes.object.isRequired,
        anatomogramDataEventEmitter: React.PropTypes.object
    },

    render: function () {

    var geneURL =
        this.props.linksAtlasBaseURL + "/query" +
        "?geneQuery=" + this.state.heatmapConfig.geneQuery +
        "&exactMatch=" + this.state.heatmapConfig.isExactMatch +
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

          { this.state.heatmapConfig ?
              <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                  <div ref="anatomogramEnsembl" className="gxaAside" style={{display: display}}>
                      { this.props.showAnatomogram && this.state.anatomogramData && Object.keys(this.state.anatomogramData).length
                        ? <Anatomogram anatomogramData={this.state.anatomogramData}
                                     expressedTissueColour={"gray"} hoveredTissueColour={"red"}
                                     profileRows={this.state.profiles.rows} eventEmitter={this.props.anatomogramEventEmitter} atlasBaseURL={this.props.atlasBaseURL}/>
                        : null
                      }
                  </div>

                  <div id="heatmap-react" className="gxaInnerHeatmap" style={{marginLeft: marginLeft, display:"block"}}>
                      <HighchartsHeatmap
                          isMultiExperiment={this.props.isMultiExperiment}
                          profiles={this.state.profiles}
                          heatmapConfig={this.state.heatmapConfig}
                          anatomogramEventEmitter={this.props.anatomogramEventEmitter}
                          ensemblEventEmitter={this.props.ensemblEventEmitter}
                          atlasBaseURL={this.props.atlasBaseURL}
                          googleAnalyticsCallback={this.state.googleAnalyticsCallback}
                          xAxisCategories={this.state.xAxisCategories}
                          yAxisCategories={this.state.yAxisCategories}
                          yAxisCategoriesLinks={this.state.yAxisCategoriesLinks}
                          seriesDataNA={this.state.seriesDataNA}
                          seriesDataNAString={this.state.seriesDataNAString}
                          seriesDataBelowCutoff={this.state.seriesDataBelowCutoff}
                          seriesDataBelowCutoffString={this.state.seriesDataBelowCutoffString}
                          seriesDataRanges={this.state.seriesDataRanges}
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

    componentDidUpdate: function() {
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
            heatmapConfig: '',
            profiles: {
                rows: [],
                minExpressionLevel: 0,
                maxExpressionLevel: 0
            },
            jsonCoexpressions : [],
            geneSetProfiles: {},
            anatomogramData: {},
            googleAnalyticsCallback: function (){},

            xAxisCategories: {},
            yAxisCategories: {},
            yAxisCategoriesLinks: {},

            seriesDataNA: [],
            seriesDataNAString: "NA",
            seriesDataBelowCutoff: [],
            seriesDataBelowCutoffString: "Below cutoff",

            seriesDataRanges: []
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

                    // var orderedData = HighchartsUtils.rankColumns(data.profiles, data.columnHeaders);
                    // var filteredDataByThreshold = HighchartsUtils.applyThresholdToColumns(orderedData.profiles, orderedData.columnHeaders, 40);
                    // var rankedExperiments = HighchartsUtils.rankExperiments(filteredDataByThreshold.rows, filteredDataByThreshold.columnHeaders.length);
                    // if (this.props.isMultiExperiment) {
                    //     data.profiles.rows = HighchartsUtils.applyThresholdToRows(rankedExperiments, filteredDataByThreshold.columnHeaders, 40);
                    // } else { //We don't apply threshold for reference experiments
                    //     data.profiles.rows = rankedExperiments;
                    // }

                    // var xAxisCategories = HighchartsUtils.getXAxisCategories(filteredDataByThreshold.columnHeaders);
                    var xAxisCategories = HighchartsUtils.getXAxisCategories(data.columnHeaders);
                    var yAxisCategories = HighchartsUtils.getYAxisCategories(data.profiles, data.config);
                    var yAxisCategoriesLinks = HighchartsUtils.getYAxisCategoriesLinks();

                    var seriesDataNA = [],
                        seriesDataNAString = "NA";

                    var seriesDataBelowCutoff = [],
                        seriesDataBelowCutoffString = "Below cutoff";

                    var seriesDataRanges = [{
                        label: "Low",
                        from: 0,
                        to: 10,
                        seriesData: []
                    }, {
                        label: "Medium",
                        from: 10,
                        to: 1000,
                        seriesData: []
                    }, {
                        label: "High",
                        from: 1000,
                        to: 100000,
                        seriesData: []
                    }];

                    var experimentTypes = [];

                    for (var i = 0; i < data.profiles.rows.length; i++) {
                        if (experimentTypes.indexOf(data.profiles.rows[i].experimentType) === -1) {
                            experimentTypes.push(data.profiles.rows[i].experimentType);
                        }
                    }

                    for (var i = 0; i < experimentTypes.length; i++) {
                        var experimentTypeSeriesData = [];

                        for (var j = 0; j < yAxisCategories.length; j++) {
                            if (data.profiles.rows[j].experimentType !== experimentTypes[i]) {
                                continue;
                            }

                            for (var k = 0; k < xAxisCategories.length; k++) {
                                var expression = data.profiles.rows[j].expressions[k];
                                //we switched from strings to doubles in April 2016, after a release you can assume we serve doubles that are optionally absent to mean "NT"
                                if (!expression.hasOwnProperty("value") || expression.value === "NT") {
                                    //seriesDataNA.push([k, j, seriesDataNAString]);
                                    continue;
                                } else if (!expression.value) {
                                    seriesDataBelowCutoff.push([k, j, seriesDataBelowCutoffString]);
                                } else {
                                    experimentTypeSeriesData.push([k, j, +expression.value]);
                                }
                            }
                        }

                        experimentTypeSeriesData.sort(function(a, b) {
                            return a[2] - b[2];
                        });

                        for (var k = 0; k < seriesDataRanges.length; k++) {
                            experimentTypeSeriesData.filter(
                                function(datum) {
                                    return datum[2] > seriesDataRanges[k].from && datum[2] <= seriesDataRanges[k].to;
                                }).forEach(
                                function(filteredDatum) {
                                    seriesDataRanges[k].seriesData.push(filteredDatum);
                                });
                        }
                    }


                    this.setState({
                        heatmapConfig: data.config,
                        columnHeaders: data.columnHeaders,
                        nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
                        profiles: data.profiles,
                        jsonCoexpressions : data.jsonCoexpressions,
                        geneSetProfiles: data.geneSetProfiles,
                        anatomogramData: data.anatomogram,
                        experimentData: data.experiment,

                        xAxisCategories: xAxisCategories,
                        yAxisCategories: yAxisCategories,
                        yAxisCategoriesLinks: yAxisCategoriesLinks,

                        seriesDataBelowCutoff: seriesDataBelowCutoff,
                        seriesDataBelowCutoffString: seriesDataBelowCutoffString,
                        seriesDataNA: seriesDataNA,
                        seriesDataNAString: seriesDataNAString,
                        seriesDataRanges: seriesDataRanges

                    });

                    if(this.props.anatomogramDataEventEmitter) {
                        if (this.state.anatomogramData) {
                            this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', true);
                        } else {
                            this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', false);
                        }
                    }
                }
            }.bind(this)
        ).fail(
            function (jqXHR, textStatus, errorThrown) {
                if (this.props.fail) {
                    this.props.fail(jqXHR, textStatus, errorThrown);
                } else if (textStatus === "parsererror") {
                    $(this.refs.this.getDOMNode()).html("<div class='gxaError'>Could not parse JSON response</div>");
                } else {
                    $(this.refs.this.getDOMNode()).html(jqXHR.responseText);
                }
            }.bind(this)
        );

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

module.exports = HighchartsHeatmapContainer;
