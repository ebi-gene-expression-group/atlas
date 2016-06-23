"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var Snap = require('imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js');

var $ = require('jquery');
var _ = require('lodash');
require('jQuery-ajaxTransport-XDomainRequest');

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

var _createOrdering = _.curry(
  function(comparator, arr){
    return (
      _
      .zip(arr.map(function(e, ix){return ix}),arr)
      .sort(function(l,r){
        return comparator(l[1],r[1]);
      })
      .map(_.spread(function(originalIndex, element){
        return originalIndex;
      }))
    );
  },2);
var _orderingByPropertyName = function(property){
  return _createOrdering(
    function comparator(e1,e2){
      return e1[property].localeCompare(e2[property]);
    });
};

var _orderingByPropertyNameReversedForDebugging = function(property){
  return _createOrdering(
    function comparator(e1,e2){
      return -e1[property].localeCompare(e2[property]);
    });
};

var Container = React.createClass({
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
                          atlasBaseURL={this.props.atlasBaseURL}
                          googleAnalyticsCallback={this.state.googleAnalyticsCallback}
                          heatmapData={this.state.heatmapData}
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
            heatmapData: {
              xAxisCategories: {},
              columnOrderings: {},
              yAxisCategories: {},
              rowOrderings:{},
              dataSeries: [[],[],[],[]],
            }
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

                    this.setState({
                        heatmapConfig: data.config,
                        columnHeaders: data.columnHeaders,
                        nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
                        profiles: data.profiles,
                        jsonCoexpressions : data.jsonCoexpressions,
                        geneSetProfiles: data.geneSetProfiles,
                        anatomogramData: data.anatomogram,
                        experimentData: data.experiment,
                        heatmapData:{
                          xAxisCategories: Container.getXAxisCategories(data.columnHeaders),
                          yAxisCategories: Container.getYAxisCategories(data.profiles.rows, data.config),
                          orderings: {
                            "Default" : {
                              columns: Container.xAxisAlphabeticalOrdering(data.columnHeaders),
                              rows: Container.yAxisAlphabeticalOrdering(data.profiles.rows)
                            },
                            "Gene expression" : { //TODO
                              columns: Container.noOrdering(data.columnHeaders),
                              rows: Container.noOrdering(data.profiles.rows)
                            },
                            "Debug- no ordering" :{
                              columns: Container.noOrdering(data.columnHeaders),
                              rows: Container.noOrdering(data.profiles.rows)
                            },
                            "Debug- reversed" :{
                              columns: _orderingByPropertyNameReversedForDebugging("factorValue")(data.columnHeaders),
                              rows: _orderingByPropertyNameReversedForDebugging("name")(data.profiles.rows)
                            }
                          },
                          dataSeries: Container.getDataSeries(data.profiles.rows)
                        }
                    });
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
    },

    statics: {
      getXAxisCategories: function (columnHeaders) {
          return columnHeaders.map(function (columnHeader) {
              return {"label": columnHeader.factorValue,
                      "id" : columnHeader.factorValueOntologyTermId};
          });
      },
      getYAxisCategories: function (rows, heatmapConfig) {
          return rows.map(function (profile) {
              return {"label": profile.name,
                      "id" : profile.id + "?geneQuery=" + heatmapConfig.geneQuery +
                          "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) };
          });
      },
      noOrdering: function(arr){
        return arr.map(function(el,ix){return ix;})
      },
      xAxisAlphabeticalOrdering: _orderingByPropertyName("factorValue"),
      yAxisAlphabeticalOrdering: _orderingByPropertyName("name"),
      getDataSeries: function (profilesRows) {
        var thresholds = {
          RNASEQ_MRNA_BASELINE : [0,10,1000],
          PROTEOMICS_BASELINE : [0,10,1000], //TODO decide on the thresholds for proteomics baseline experiments
          DEFAULT : [0,10,1000]
        };
        return (_
          .chain(profilesRows)
          .map(
              function(row, columnNumber) {
                  return [row.experimentType,
                      row.expressions
                      .map(function(expression, rowNumber) {
                          return (
                              expression.hasOwnProperty("value") && expression.value !== "NT"
                              ? [rowNumber, columnNumber, expression.value || "Below cutoff"]
                              : null)
                      })
                      .filter(function(el) {
                          return el;
                      })
                  ]
              })
          .groupBy(function(experimentTypeAndRow) {
              return experimentTypeAndRow[0]
          })
          .mapValues(function(rows) {
              return rows.map(function(experimentTypeAndRow) {
                  return experimentTypeAndRow[1];
              })
          })
          .mapValues(_.flatten)
          .toPairs()
          .map(_.spread(
              function(experimentType, dataPoints) {
                  return dataPoints.map(
                      _.spread(function(xPosition, yPosition, value) {
                          return [
                              _.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, value),
                              [xPosition, yPosition, value]
                          ];
                      }.bind(this))
                  )
              }.bind(this)))
          .flatten()
          .groupBy(_.spread(function(dataSeriesAssigned, point) {
              return dataSeriesAssigned;
          }))
          .mapValues(function(bucket) {
              return bucket.map(_.spread(function(dataSeriesAssigned, point) {
                  return point;
              }))
          })
          .transform(function(result, bucket, bucketNumber) {
              result[bucketNumber] = bucket;
          }, _.fill(Array(4), []))
          .value()
        );
      }
    }
});

//*------------------------------------------------------------------*

module.exports = Container;
