"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactHighcharts = require('react-highcharts');
var Highcharts = ReactHighcharts.Highcharts;
require('highcharts-heatmap')(Highcharts);

//*------------------------------------------------------------------*

require('./HighchartsHeatmap.css');
var Button = require('react-bootstrap/lib/Button');
var DownloadProfilesButton = require('download-profiles-button');

var FormattersFactory = require('./Formatters.jsx');
var PropTypes = require('./PropTypes.js');
var createColorAxis = require('./ColoursForHighcharts.js');
var hash = require('object-hash');

//*------------------------------------------------------------------*

var HeatmapContainer = React.createClass({
    propTypes: {
        profiles: React.PropTypes.object.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        heatmapData: PropTypes.HeatmapData,
        onHeatmapRedrawn: React.PropTypes.func.isRequired,
        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        onOntologyIdIsUnderFocus : React.PropTypes.func.isRequired
    },

    getInitialState: function() {
        return {
            ordering: "Default"
        };
    },

    _introductoryMessage: function() {
        var shownRows = this.props.profiles.rows.length,
            totalRows = this.props.profiles.searchResultTotal;

        var what =
            (this.props.heatmapConfig.isMultiExperiment ? 'experiment' : 'gene') +
            (totalRows > 1 ? 's' : '');

        return 'Showing ' + shownRows + ' ' +
            (totalRows === shownRows ? what + ':' : 'of ' + totalRows + ' ' + what + ' found:');
    },

    _data: function() {
        var permuteX = function(x){
            return this.props.heatmapData.orderings[this.state.ordering].columns.indexOf(x);
        }.bind(this);

        var permuteY = function(y){
            return this.props.heatmapData.orderings[this.state.ordering].rows.indexOf(y);
        }.bind(this);

        var permutePoint = function(point){
            return {
              x: permuteX(point.x),
              y: permuteY(point.y),
              value: point.value,
              info: point.info
            };
        };

        var permuteArray = function(arr, permute){
            return (
                arr
                  .map(
                      function(el, ix){
                        return [el, permute(ix)];
                      })
                  .sort(
                      function(l,r){
                        return l[1]-r[1];
                      })
                  .map(
                      function(el){
                        return el[0];
                      }
                )
            );
        };

        return {
            dataSeries: this.props.heatmapData.dataSeries.map(
                    function(series){
                        return series.data.map(permutePoint);
                    }),
            xAxisCategories: permuteArray(this.props.heatmapData.xAxisCategories, permuteX),
            yAxisCategories: permuteArray(this.props.heatmapData.yAxisCategories, permuteY)
        };
    },

    _labels: function(){
        return this.props.heatmapData.dataSeries.map(
            function (e){
                return {
                    colour: e.colour,
                    name: e.name
                }
            }
        )
    },

    render: function () {
        var marginRight = 60;
        return (
            <div>
                <HeatmapOptions
                    marginRight={marginRight}
                    introductoryMessage={this._introductoryMessage()}
                    downloadOptions={{
                        downloadProfilesURL: this.props.heatmapConfig.downloadProfilesURL,
                        atlasBaseURL: this.props.heatmapConfig.atlasBaseURL,
                        disclaimer: this.props.heatmapConfig.disclaimer
                    }}
                    orderings={{
                        available: Object.keys(this.props.heatmapData.orderings),
                        current: this.state.ordering,
                        onSelect: function(orderingChosen){
                          this.setState({ordering: orderingChosen})
                        }.bind(this)
                    }}
                    googleAnalyticsCallback={this.props.googleAnalyticsCallback}
                    showUsageMessage={this.props.heatmapData.xAxisCategories.length > 100} />

                <HeatmapCanvas
                    marginRight={marginRight}
                    ontologyIdsToHighlight={this.props.ontologyIdsToHighlight}
                    data={this._data()}
                    labels={this._labels()}
                    colorAxis={this.props.heatmapConfig.isExperimentPage ? createColorAxis(this.props.heatmapData.dataSeries) : undefined}
                    onHeatmapRedrawn={this.props.onHeatmapRedrawn}
                    formatters={FormattersFactory(this.props.heatmapConfig)}
                    onUserSelectsColumn={this.props.onOntologyIdIsUnderFocus}/>
            </div>
        );
    }

});

var HeatmapCanvas = React.createClass({
    propTypes: {
        marginRight: React.PropTypes.number.isRequired,
        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        data: React.PropTypes.shape({
            dataSeries: PropTypes.PointsInDataSeries,
            xAxisCategories: PropTypes.AxisCategories,
            yAxisCategories: PropTypes.AxisCategories
        }),
        labels: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string,
            colour: React.PropTypes.string
        })).isRequired,
        colorAxis: React.PropTypes.object,
        onHeatmapRedrawn: React.PropTypes.func.isRequired,
        formatters : React.PropTypes.shape({
          xAxis: PropTypes.Formatter,
          yAxis: PropTypes.Formatter,
          tooltip: PropTypes.Formatter
        }).isRequired,
        onUserSelectsColumn:React.PropTypes.func.isRequired
    },

    getInitialState: function () {
        return ({
            dataSeriesToShow: this.props.labels.map(function(e){return true;})
        })
    },

    _dataToShow: function () {
        var all_s = function(propertyToPickFromEachPoint){
            return (
                this.props.data.dataSeries
                .filter(function(e, ix){
                    return (
                        this.state.dataSeriesToShow[ix]
                    );
                }.bind(this))
                .reduce(function(l,r){
                    return l.concat(r);
                },[])
                .map(function(e){
                    return e[propertyToPickFromEachPoint];
                })
                .filter(function(e,ix,self){
                    return self.indexOf(e) ===ix ;
                })
                .sort(function(l,r){
                    return l-r;
                })
            );
        }.bind(this);

        var allXs = all_s("x");
        var allYs = all_s("y");

        var ds = this.props.data.dataSeries
        .map(function(e, ix){
            return (
                this.state.dataSeriesToShow[ix] ? e : []
            );
        }.bind(this))
        .map(function(series){
            return (
                series
                    .map(function(point){
                        return {
                            x: allXs.indexOf(point.x),
                            y: allYs.indexOf(point.y),
                            value: point.value,
                            info: point.info
                        };
                    })
                    .filter(function(point){
                        return point.x>-1 && point.y>-1
                    })
                );
        });

        return {
            dataSeries: this.props.labels.map(function(e, ix){
                return {
                    name: e.name,
                    color: e.colour,
                    /*For smaller experiments, separate the cells so that they look easier to distinguish
                      For large experiments this would make it show up as one big white block so don't do it.
                      Change the magic number 200 if you feel like it.
                      */
                    borderWidth: this.props.data.xAxisCategories.length > 200 ? 0 :1 ,
                    borderColor: "white",
                    data: ds[ix]
                  }
            }.bind(this)),
            xAxisCategories: this.props.data.xAxisCategories.filter(function(e,ix){
                return allXs.indexOf(ix)>-1
            }),
            yAxisCategories: this.props.data.yAxisCategories.filter(function(e,ix){
                return allYs.indexOf(ix)>-1
            })

        };
    },

    componentDidUpdate: function () {
        this.props.onHeatmapRedrawn();
    },

    _makeLabelToggle: function(ix){
        return function(){
            this.setState(function(previousState){
                return {
                    dataSeriesToShow: previousState.dataSeriesToShow.map(function(e,jx){
                        return ix===jx ? !e : e;
                    })
                }
            });
        }.bind(this);
    },

    renderLegend: function(){
        return (
        <div id ="barcharts_legend_list_items" ref="barcharts_legend_items">
            { this.props.labels.map(
                function(e, ix){
                    return (
                        <HeatmapLegendBox key={e.name}
                                          name={e.name}
                                          colour={e.colour}
                                          on={this.state.dataSeriesToShow[ix]}
                                          onClickCallback={this._makeLabelToggle(ix)} />
                    );
                }.bind(this))
            }

            <div className="legend-item special">
                <span className="icon icon-generic" data-icon="i" data-toggle="tooltip" data-placement="bottom"
                    title="Baseline expression levels in RNA-seq experiments are in FPKM or TPM. Low: 0-10, Medium: 11-1000,  High: >1000. Proteomics expression levels are mapped to low, medium, high on per experiment basis.">
                </span>
            </div>
            <HeatmapLegendBox key={"No data available"}
                              name={"No data available"}
                              colour={"white"}
                              on={ true}
                              onClickCallback={function(){}}/>
             </div>
        );
    },

    render: function () {

        var data = this._dataToShow();
        return (
              <div id="highcharts_container">
                  {data.dataSeries
                    .map(function(e){return e.data;})
                    .reduce(function(l,r){return l.concat(r);}, [])
                    .length
                    ? <HeatmapDrawing dataForTheChart={data} {...this.props} />
                    : <p> No data in the series currently selected. </p>}
                  {this.renderLegend()}
              </div>
        );
    }

});

var HeatmapDrawing = React.createClass({
  propTypes: {
    dataForTheChart: React.PropTypes.object.isRequired,
    ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired
    //And also more props needed for the config
  },

  shouldComponentUpdate: function(nextProps){
    return hash.MD5(nextProps.dataForTheChart)!==hash.MD5(this.props.dataForTheChart);
  },

  componentWillReceiveProps: function(nextProps){
    var chart = this.refs.chart.getChart();
    var forEachXNotInYsEmit = function(xs, ys, eventName){
      xs
      .filter(function(id){
        return ys.indexOf(id)==-1;
      })
      .filter(function uniq(id,ix,self){
        return ix==self.indexOf(id);
      })
      .forEach(function(id){
        Highcharts.fireEvent(chart, eventName, {svgPathId: id});
      }.bind(this));
    };
    forEachXNotInYsEmit(nextProps.ontologyIdsToHighlight, this.props.ontologyIdsToHighlight,'handleGxaAnatomogramTissueMouseEnter');
    forEachXNotInYsEmit(this.props.ontologyIdsToHighlight, nextProps.ontologyIdsToHighlight,'handleGxaAnatomogramTissueMouseLeave');
  },

  render: function(){
    //TODO calculating this based on dataForTheChart would be more correct.
    var xAxisLongestHeaderLength =
        Math.max.apply(null, this.props.data.xAxisCategories.map(function(category) {return category.label.length}));

    var marginTop =
        this.props.data.xAxisCategories.length < 10 ? 30 :   // labels aren’t tilted
            this.props.data.xAxisCategories.length < 50 ? Math.min(150, Math.round(xAxisLongestHeaderLength * 3.75)) : // labels at -45°
                Math.min(250, Math.round(xAxisLongestHeaderLength * 5.5));   // labels at -90°

    var maxWidthFraction = 1-1/Math.pow(0.2*this._countColumnsToShow() +1,4);
    //TODO the marginRight value of props used to be the same here and in top legend.
    //Probably it's time to get rid of this prop.
    var marginRight = this.props.marginRight*(1+10/Math.pow(1+this._countColumnsToShow(),2));

    return (
        <div style={{maxWidth:maxWidthFraction*100+"%"}}>
            <ReactHighcharts config={this._highchartsOptions(marginTop, marginRight, this.props.dataForTheChart)} ref="chart"/>
        </div>
    );
  },

  _countColumnsToShow: function(){
    return this.props.data.xAxisCategories.length;
  },

  _highchartsOptions: function(marginTop, marginRight, data){
    return (
      {
          plotOptions: {
              heatmap: {
                  turboThreshold: 0
              },
              series: {
                  point: {
                      events: {
                          mouseOver: function() {
                              this.series.chart.userOptions.onUserSelectsColumn(this.series.xAxis.categories[this.x].id);
                          }
                      }
                  },
                  events: {
                      mouseOut: function () {
                          this.chart.userOptions.onUserSelectsColumn("");
                      }
                  },

                  states: {
                      hover: {
                          color: '#eeec38' //#edab12 color cell on mouse over
                      },
                      select: {
                          color: '#eeec38'
                      }
                  }
              }
          },
          credits: {
              enabled: false //remove Highcharts text in the bottom right corner
          },
          chart: {
              type: 'heatmap',
              marginTop: marginTop,
              marginRight: marginRight, //leave space for tilted long headers
              spacingTop: 0,
              plotBorderWidth: 1,
              height: Math.max(70, data.yAxisCategories.length * 30 + marginTop),
              zoomType: 'x',
              events: {
                  handleGxaAnatomogramTissueMouseEnter: function(e) {
                      Highcharts.each(this.series, function (series) {
                          Highcharts.each(series.points, function (point) {
                              if (point.series.xAxis.categories[point.x].id === e.svgPathId) {
                                  point.select(true, true);
                              }
                          });
                      });
                  },
                  handleGxaAnatomogramTissueMouseLeave: function(e) {
                      var points = this.getSelectedPoints();
                      if (points.length > 0) {
                          Highcharts.each(points, function (point) {
                              point.select(false);
                          });
                      }
                  }
              }
          },
          legend: {
              enabled: false
          },
          title: null,
          colorAxis: this.props.colorAxis || undefined,
          xAxis: { //assays
              tickLength: 5,
              tickColor: 'rgb(192, 192, 192)',
              lineColor: 'rgb(192, 192, 192)',
              labels: {
                  style: {
                      fontSize: '9px',
                      textOverflow: 'ellipsis'
                  },
                  autoRotation: [-45, -90],
                  formatter: (function() { var f =this.props.formatters.xAxis; return function(){return f(this.value);};}.bind(this))()
              },
              opposite: 'true',
              categories: data.xAxisCategories
          },
          yAxis: { //experiments or bioentities
              useHTML: true,
              reversed: true,
              labels: {
                  style: {
                      fontSize: '10px',
                      color: '#148ff3'
                  },
                  formatter: (function() { var f =this.props.formatters.yAxis; return function(){return f(this.value);};}.bind(this))()
              },
              categories: data.yAxisCategories,
              title: null,
              gridLineWidth: 0,
              minorGridLineWidth: 0,
              endOnTick: false
          },
          tooltip: {
              useHTML: true,
              formatter: (function() { var f =this.props.formatters.tooltip; return function(){return f(this.series,this.point);};}.bind(this))()
          },
          onUserSelectsColumn: this.props.onUserSelectsColumn,
          series: data.dataSeries
      }
    );
  }
});

var HeatmapLegendBox = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        colour: React.PropTypes.string.isRequired,
        on: React.PropTypes.bool.isRequired,
        onClickCallback: React.PropTypes.func.isRequired
    },

    render: function () {
        return (
            <div className={"legend-item "+(this.props.on? "special" : "legend-item-off")} onClick={this.props.onClickCallback}>
                <div style={{background: this.props.colour}} className="legend-rectangle"></div>
                <span style={{verticalAlign: "middle"}}>{this.props.name}</span>
            </div>
        );
    }
});

var propsForOrderingDropdown = {
    available: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
    current: React.PropTypes.string.isRequired,
    onSelect: React.PropTypes.func.isRequired
};

var OrderingDropdown = React.createClass({
    propTypes: propsForOrderingDropdown,

    getInitialState: function () {
        return {selected: this.props.current}
    },

    handleChange: function (e) {
        this.state.selected = e.target.value;
        this.props.onSelect(this.state.selected);
        this.forceUpdate();
    },

    render: function () {

        var createOption = function (option, key) {
            return <option key={key} value={option}>{option}</option>;
        };

        return (
            <div style={{float: "left", marginRight: "10px", marginTop: "1px"}}>
                <span>Sort by: </span>
                <select onChange={this.handleChange} value={this.state.selected}>
                    {this.props.available.map(createOption)}
                </select>
            </div>
        );
    }
});

var HeatmapOptions = React.createClass({
    propTypes: {
        marginRight: React.PropTypes.number.isRequired,
        downloadOptions: React.PropTypes.object.isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        showUsageMessage: React.PropTypes.bool.isRequired,
        orderings: React.PropTypes.shape(propsForOrderingDropdown)
    },

    render: function () {
        return (
            <div ref="countAndLegend" className="gxaHeatmapCountAndLegend" style={{paddingBottom: '15px', position: 'sticky'}}>
                <div style={{display: 'inline-block', verticalAlign: 'top'}}>
                  {this.props.introductoryMessage}
                </div>
                <div style={{display: "inline-block", verticalAlign: "top", float: "right", marginRight: this.props.marginRight}}>

                    { this.props.orderings.available.length > 1
                        ?
                          <OrderingDropdown
                            available={this.props.orderings.available}
                            current={this.props.orderings.current}
                            onSelect={this.props.orderings.onSelect}/>
                        :
                          null
                      }
                    <DownloadProfilesButton ref="downloadProfilesButton"
                      {...this.props.downloadOptions}
                      onDownloadCallbackForAnalytics={
                          function() {
                              this.props.googleAnalyticsCallback('send', 'event', 'HeatmapHighcharts', 'downloadData')
                          }.bind(this)}/>

                </div>
                    {this.props.showUsageMessage
                      ?
                        <div style={{fontSize: 'small', color: 'grey'}}>
                            Select a section of the heatmap to zoom in
                        </div>
                      : null}
                </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapContainer;
