"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactHighcharts = require('react-highcharts');
var Highcharts = ReactHighcharts.Highcharts;
require('highcharts-heatmap')(Highcharts);

//*------------------------------------------------------------------*

require('./HighchartsHeatmap.css');
var DownloadProfilesButton = require('download-profiles-button');

var EventEmitter = require('events');

//*------------------------------------------------------------------*

var DataSeriesPropType = React.PropTypes.arrayOf(
    React.PropTypes.arrayOf(
      React.PropTypes.arrayOf(
        function(series){
          if (! Array.isArray(series)
              || series.length !== 3
              || typeof series[0] !== 'number'
              || typeof series[1] !== 'number'){
            return new Error("Data series should be array of number, number, value to display")
          }
        }
      )
    )
  ).isRequired;

var AxisCategoriesPropType = React.PropTypes.arrayOf(
    React.PropTypes.shape({
      id: React.PropTypes.string, // ontology ID can be missing for x axis
      label: React.PropTypes.string.isRequired
    })
  ).isRequired;

var HeatmapDataPropType = React.PropTypes.objectOf(
  function(heatmapData){
    var width = heatmapData.xAxisCategories.length;
    var height = heatmapData.yAxisCategories.length;
    if(heatmapData.dataSeries.length!== 4){
      return new Error("There should be four data series supplied");
    }
    for(var i = 0; i < 4; i++){
      for(var j = 0; j < heatmapData.dataSeries[i].length; j++){
        var point = heatmapData.dataSeries[i][j];
        if(point.length !==3){
          return new Error("Each point in data series should be [x,y,value]:"+ point.toString());
        }
        var x = point[0];
        var y = point[1];
        if(x < 0 || y < 0 || x >= width || y >= height){
          return new Error("Point with coordinates outside range:" + point.toString());
        }
      }
    }
    var isPermutation = function(arr){
      return arr.sort(function(a,b){return a-b}).map(function(el,ix){return el===ix}).reduce(function(l,r){return l&&r},true);
    }
    for(var orderingName in heatmapData.orderings){
      if(heatmapData.orderings.hasOwnProperty(orderingName)){
        var ordering = heatmapData.orderings[orderingName];
        if(ordering.columns.length!== width || !isPermutation(ordering.columns)){
          return new Error("Column ordering invalid in "+orderingName);
        }
        if(ordering.rows.length!==height || ! isPermutation(ordering.rows)){
          return new Error("Row ordering invalid in "+orderingName);
        }
      }
    }
  });


var HeatmapContainer = React.createClass({
  propTypes: {
      isMultiExperiment: React.PropTypes.bool.isRequired,
      profiles: React.PropTypes.object.isRequired,
      atlasBaseURL: React.PropTypes.string.isRequired,
      anatomogramEventEmitter : React.PropTypes.instanceOf(EventEmitter).isRequired,
      googleAnalyticsCallback: React.PropTypes.func.isRequired,
      heatmapData: HeatmapDataPropType
  },

  _introductoryMessage: function() {
      var shownRows = this.props.profiles.rows.length,
          totalRows = this.props.profiles.searchResultTotal;

      var what =
          (this.props.isMultiExperiment ? 'experiment' : 'gene') +
          (totalRows > 1 ? 's' : '');

      return 'Showing ' + shownRows + ' ' +
       (totalRows === shownRows ? what + ':' : 'of ' + totalRows + ' ' + what + ' found:');
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
                atlasBaseURL: this.props.atlasBaseURL,
                isFortLauderdale: this.props.heatmapConfig.isFortLauderdale}}
              googleAnalyticsCallback={this.props.googleAnalyticsCallback}
              showUsageMessage={this.props.heatmapData.xAxisCategories.length > 100} />

            <HighchartsHeatmap
              marginRight={marginRight}
              atlasBaseURL={this.props.atlasBaseURL}
              anatomogramEventEmitter={this.props.anatomogramEventEmitter}
              dataSeries={this.props.heatmapData.dataSeries}
              xAxisCategories={this.props.heatmapData.xAxisCategories}
              yAxisCategories={this.props.heatmapData.yAxisCategories}
            />
        </div>
    );
  }

});

var HighchartsHeatmap = React.createClass({

    propTypes: {
        marginRight: React.PropTypes.number.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        anatomogramEventEmitter : React.PropTypes.instanceOf(EventEmitter).isRequired,
        dataSeries: DataSeriesPropType,
        xAxisCategories: AxisCategoriesPropType,
        yAxisCategories: AxisCategoriesPropType
    },

    getInitialState: function () {
        return ({
            legend_0: false,
            legend_1: false,
            legend_2: false,
            legend_3: false
        })
    },

    _handleClick: function (index) {
        if (index == 0) {
            this.setState({legend_0: !this.state.legend_0});
        }
        else if (index == 1) {
            this.setState({legend_1: !this.state.legend_1});
        }
        else if (index == 2) {
            this.setState({legend_2: !this.state.legend_2});
        }
        else if (index == 3) {
            this.setState({legend_3: !this.state.legend_3});
        }
    },

    _anatomogramTissueMouseEnter: function(svgPathId) {
        Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseEnter', {svgPathId: svgPathId});
    },

    _anatomogramTissueMouseLeave: function(svgPathId) {
        Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseLeave', {svgPathId: svgPathId});
    },

    _registerListenerIfNecessary(name, fn) {
      if (this.props.anatomogramEventEmitter &&
          this.props.anatomogramEventEmitter._events &&
          !this.props.anatomogramEventEmitter._events.hasOwnProperty(name)) {
              this.props.anatomogramEventEmitter.addListener(name, fn);
          }
    },

    componentDidMount: function () {
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
    },

    componentDidUpdate: function () {
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
        var heatmap = this.refs.chart.getChart();

        this.state.legend_0 ? heatmap.series[0].hide() : heatmap.series[0].show();
        this.state.legend_1 ? heatmap.series[1].hide() : heatmap.series[1].show();
        this.state.legend_2 ? heatmap.series[2].hide() : heatmap.series[2].show();
        this.state.legend_3 ? heatmap.series[3].hide() : heatmap.series[3].show();

    },

    _prepareDataSeries: function () {
      return ([
       ["Below cutoff", "#eaeaea"],
       ["Low", "#45affd"],
       ["Medium", "#1E74CA"],
       ["High", "#024990"]
     ]).map(function (__args__, ix) {
       return {
         name: __args__[0],
         color: __args__[1],
         borderWidth: 1,
         borderColor: "#fff",
         data: this.props.dataSeries[ix]
       }
     }.bind(this));
    },

    render: function () {
        var atlasBaseURL = this.props.atlasBaseURL;

        var xAxisLongestHeaderLength =
            Math.max.apply(null, this.props.xAxisCategories.map(function(category) {return category.label.length}));

        var marginTop =
            this.props.xAxisCategories.length < 10 ? 30 :   // labels aren’t tilted
                this.props.xAxisCategories.length < 50 ? Math.min(150, Math.round(xAxisLongestHeaderLength * 3.75)) : // labels at -45°
                    Math.min(250, Math.round(xAxisLongestHeaderLength * 5.5));   // labels at -90°

        var highchartsOptions = {
            plotOptions: {
                heatmap: {
                    turboThreshold: 0
                },
                series: {
                    point: {
                        events: {
                            mouseOver: function() {
                                this.series.chart.userOptions.anatomogramEventEmitter.emit('gxaHeatmapColumnHoverChange', this.series.xAxis.categories[this.x].id);
                            }
                        }
                    },
                    events: {
                        mouseOut: function () {
                            this.chart.userOptions.anatomogramEventEmitter.emit('gxaHeatmapColumnHoverChange', null);
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
                marginRight: this.props.marginRight, //leave space for tilted long headers
                spacingTop: 0,
                plotBorderWidth: 1,
                height: Math.max(70, this.props.yAxisCategories.length * 30 + marginTop),
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
            xAxis: { //tissues
                tickLength: 5,
                tickColor: 'rgb(192, 192, 192)',
                lineColor: 'rgb(192, 192, 192)',
                labels: {
                    style: {
                        fontSize: '9px',
                        textOverflow: 'ellipsis'
                    },
                    autoRotation: [-45, -90],
                    formatter: function() {
                        return this.value.label;
                    }
                },
                opposite: 'true',
                categories: this.props.xAxisCategories
            },
            yAxis: { //experiments
                useHTML: true,
                reversed: true,
                labels: {
                    style: {
                        fontSize: '10px',
                        color: '#148ff3'
                    },
                    formatter: function() {
                        return '<a href="' + atlasBaseURL +'/experiments/' + this.value.id + '">' + this.value.label + '</a>';
                    }
                },
                categories: this.props.yAxisCategories,
                title: null,
                gridLineWidth: 0,
                minorGridLineWidth: 0,
                endOnTick: false
            },
            tooltip: {
                useHTML: true,
                formatter: function() {
                    return 'Sample name: <b>' + this.series.yAxis.categories[this.point.y].label + '</b><br>' +
                        'Experimental condition: <b>' + this.series.xAxis.categories[this.point.x].label + '</b><br>' +
                        '<span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:' + this.point.color + ';"></span>' +
                        'Expression level: <b>' + this.point.value + '</b>';
                }
            },
            anatomogramEventEmitter: this.props.anatomogramEventEmitter,
            series: this._prepareDataSeries()
        };

        var clsName_0 = this.state.legend_0 ? 'legend-item legend-item-off' : 'legend-item special';
        var clsName_1 = this.state.legend_1 ? 'legend-item legend-item-off' : 'legend-item special';
        var clsName_2 = this.state.legend_2 ? 'legend-item legend-item-off' : 'legend-item special';
        var clsName_3 = this.state.legend_3 ? 'legend-item legend-item-off' : 'legend-item special';

        var barcharts_legend = (
            <div id ="barcharts_legend_list_items" ref="barcharts_legend_items">
                <div id="legend_0" ref="legend_1" className={clsName_0} >
                    <div className="legend-rectangle col_below"></div>
                    <span style={{verticalAlign: "middle"}}>Below cutoff</span>
                </div>
                <div id="legend_1" className={clsName_1} >
                    <div className="legend-rectangle col_low"></div>
                    <span style={{verticalAlign: "middle"}}>Low</span>
                </div>
                <div id="legend_2" className={clsName_2} >
                    <div className="legend-rectangle col_med"></div>
                    <span style={{verticalAlign: "middle"}}>Medium</span>
                </div>
                <div id="legend_3" className={clsName_3} >
                    <div className="legend-rectangle col_high"></div>
                    <span style={{verticalAlign: "middle"}}>High</span>
                </div>

                <div className="legend-item special">
                    <span className="icon icon-generic" data-icon="i" data-toggle="tooltip" data-placement="bottom"
                          title="This range of values indicates gene expression level across different experimental conditions (e.g. tissues). It is calculated differently between RNA and proteomics experiments.">
                    </span>
                </div>

                <div id="legend_4" className="legend-item special">
                    <div className="legend-rectangle col_nd"></div>
                    <span style={{verticalAlign: "middle"}}>No data available</span>
                </div>
            </div>
        );

        return (
              <div id="highcharts_container">
                  <ReactHighcharts config={highchartsOptions} ref="chart"/>
                  {barcharts_legend}
              </div>
        );
    }

});

var HeatmapOptions = React.createClass({
  propTypes: {
    marginRight: React.PropTypes.number.isRequired,
    downloadOptions: React.PropTypes.object.isRequired,
    googleAnalyticsCallback: React.PropTypes.func.isRequired,
    showUsageMessage: React.PropTypes.bool.isRequired
  },

  render: function () {
    return (
      <div ref="countAndLegend" className="gxaHeatmapCountAndLegend" style={{paddingBottom: '15px', position: 'sticky'}}>
          <div style={{display: 'inline-block', verticalAlign: 'top'}}>
              {this.props.introductoryMessage}
          </div>
          <div style={{display: "inline-block", verticalAlign: "top", float: "right", marginRight: this.props.marginRight}}>
              <DownloadProfilesButton ref="downloadProfilesButton"
                                      downloadProfilesURL={this.props.downloadOptions.downloadProfilesURL}
                                      atlasBaseURL={this.props.downloadOptions.atlasBaseURL}
                                      isFortLauderdale={this.props.downloadOptions.isFortLauderdale}
                                      onDownloadCallbackForAnalytics={function() {this.props.googleAnalyticsCallback('send', 'event', 'HeatmapHighcharts', 'downloadData')}.bind(this)}/>
          </div>
          {this.props.showUsageMessage
            ? <div style={{fontSize: 'small', color: 'grey'}}>
                  To zoom in, click and drag left/right, or tap with two fingers and pinch
              </div>
            : null}
      </div>
    );
  }

});

//*------------------------------------------------------------------*

module.exports = HeatmapContainer;
