"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactHighcharts = require('react-highcharts');
var Highcharts = ReactHighcharts.Highcharts;
require('highcharts-heatmap')(Highcharts);

//*------------------------------------------------------------------*

require('./HighchartsHeatmap.css');
var DropdownButton = require('react-bootstrap/lib/DropdownButton');
var MenuItem = require('react-bootstrap/lib/MenuItem');
var DownloadProfilesButton = require('download-profiles-button');

var EventEmitter = require('events');
var FormattersFactory = require('./Formatters.jsx');

//*------------------------------------------------------------------*

var PointPropType = React.PropTypes.shape({
  x: React.PropTypes.number.isRequired,
  y: React.PropTypes.number.isRequired,
  value: React.PropTypes.number.isRequired,
  info: React.PropTypes.object.isRequired
});

var DataSeriesPropType = React.PropTypes.arrayOf(
      React.PropTypes.arrayOf(PointPropType)
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

        for(var i = 0; i < heatmapData.dataSeries.length; i++){
            for(var j = 0; j < heatmapData.dataSeries[i].data.length; j++){
                var point = heatmapData.dataSeries[i].data[j];
                var x = point.x;
                var y = point.y;
                if(x < 0 || y < 0 || x >= width || y >= height){
                    return new Error("Point with coordinates outside range:" + x+","+y);
                }
            }
        }

    var isPermutation = function(arr){
        return (
            [].concat(arr)
            .sort(function(a,b){
                return a-b;
            })
            .map(function(el,ix){
                return el===ix;
            })
            .reduce(function(l,r){
                return l&&r;
            },true)
        );
    };

    if(!heatmapData.orderings.hasOwnProperty("Default")){
        return new Error("Default ordering missing!");
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
        profiles: React.PropTypes.object.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired,
        anatomogramEventEmitter : React.PropTypes.instanceOf(EventEmitter).isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        heatmapData: HeatmapDataPropType,
        afterHeatmapRedrawn: React.PropTypes.func.isRequired
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
            <div style={{"minHeight":"400px"}}>
                <HeatmapOptions
                    marginRight={marginRight}
                    introductoryMessage={this._introductoryMessage()}
                    downloadOptions={{
                        downloadProfilesURL: this.props.heatmapConfig.downloadProfilesURL,
                        atlasBaseURL: this.props.heatmapConfig.atlasBaseURL,
                        isFortLauderdale: this.props.heatmapConfig.isFortLauderdale
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
                <HighchartsHeatmap
                    marginRight={marginRight}
                    anatomogramEventEmitter={this.props.anatomogramEventEmitter}
                    data={this._data()}
                    labels={this._labels()}
                    afterHeatmapRedrawn={this.props.afterHeatmapRedrawn}
                    formatters={FormattersFactory(this.props.heatmapConfig)}
                />
            </div>
        );
    }

});

var FormatterPropType = function(props,propName){
  var f = props[propName];
  if(typeof f === 'undefined'){
    return new Error(propName+" formatter missing");
  } else if (typeof f !== 'function' || f.name !== 'Formatter'){
    return new Error(propName+" formatter not correctly created. See the main method of TooltipFormatter.jsx .");
  }
}

var HighchartsHeatmap = React.createClass({

    propTypes: {
        marginRight: React.PropTypes.number.isRequired,
        anatomogramEventEmitter : React.PropTypes.instanceOf(EventEmitter).isRequired,
        data: React.PropTypes.shape({
            dataSeries: DataSeriesPropType,
            xAxisCategories: AxisCategoriesPropType,
            yAxisCategories: AxisCategoriesPropType
        }),
        labels: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string,
            colour: React.PropTypes.string
        })).isRequired,
        afterHeatmapRedrawn: React.PropTypes.func.isRequired,
        formatters : React.PropTypes.shape({
          xAxis: FormatterPropType,
          yAxis: FormatterPropType,
          tooltip: FormatterPropType
        }).isRequired
    },

    getInitialState: function () {
        return ({
            dataSeriesToShow: this.props.labels.map(function(e){return true;})
        })
    },


    _anatomogramTissueMouseEnter: function(svgPathId) {
        Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseEnter', {svgPathId: svgPathId});
    },

    _anatomogramTissueMouseLeave: function(svgPathId) {
        Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseLeave', {svgPathId: svgPathId});
    },

    _registerListenerIfNecessary: function(name, fn) {
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
                    borderWidth: 1,
                    borderColor: "#fff",
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
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
        this._registerListenerIfNecessary('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
        this.props.afterHeatmapRedrawn();
    },

    _countColumnsToShow: function() {
        return (
            this.props.data.dataSeries
                .filter(function(e,ix){
                    return this.state.dataSeriesToShow[ix];
                }.bind(this))
                .reduce(function(l,r){
                    return l.concat(r);
                },[])
                .map(function(e){
                    return e.x;
                })
                .filter(function(e,ix,self){
                    return self.indexOf(e) ===ix;
                })
                .length
        );
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
                    title="This range of values indicates gene expression level across different experimental conditions (e.g. tissues). It is calculated differently between RNA and proteomics experiments.">
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
            anatomogramEventEmitter: this.props.anatomogramEventEmitter,
            series: data.dataSeries
        }
      );
    },

    _boxedHeatmap: function(dataForTheChart){
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
                <ReactHighcharts config={this._highchartsOptions(marginTop, marginRight, dataForTheChart)} ref="chart"/>
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
                    ? this._boxedHeatmap(data)
                    : <p> No data in the series currently selected. </p>}
                  {this.renderLegend()}
              </div>
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

    render: function () {
        return (
            <div className="btn-group">
                <DropdownButton bsStyle={"primary"}
                                bsSize={"xs"}
                                title={"Order by"}
                                onSelect={this.props.onSelect}
                                id={"ordering-dropdown"}>
                    {this.props.available.map(
                        function(option){
                            return (
                                  <MenuItem key={option}
                                            eventKey={option}
                                            active={option === this.props.current}>
                                            {option}
                                 </MenuItem>);
                    }.bind(this))}
                </DropdownButton>
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
                <div style={{display: "inline-block", verticalAlign: "top", marginRight: this.props.marginRight}}>
                <div className="btn-group">
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
                                            downloadProfilesURL={this.props.downloadOptions.downloadProfilesURL}
                                            atlasBaseURL={this.props.downloadOptions.atlasBaseURL}
                                            isFortLauderdale={this.props.downloadOptions.isFortLauderdale}
                                            onDownloadCallbackForAnalytics={
                                                function() {
                                                    this.props.googleAnalyticsCallback('send', 'event', 'HeatmapHighcharts', 'downloadData')
                                                }.bind(this)}/>
                </div>
                </div>
                    {this.props.showUsageMessage
                      ?
                        <div style={{fontSize: 'small', color: 'grey'}}>
                            To zoom in, click and drag left/right, or tap with two fingers and pinch
                        </div>
                      : null}
                </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapContainer;
