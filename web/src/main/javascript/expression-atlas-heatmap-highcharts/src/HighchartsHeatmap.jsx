"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactHighcharts = require('react-highcharts');
var Highcharts = ReactHighcharts.Highcharts;
require('highcharts-heatmap')(Highcharts);

//*------------------------------------------------------------------*

require('./HighchartsHeatmap.css');

var EventEmitter = require('events');

//*------------------------------------------------------------------*

var HighchartsHeatmap = React.createClass({

    propTypes: {
        profiles: React.PropTypes.object.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        anatomogramEventEmitter : React.PropTypes.instanceOf(EventEmitter).isRequired,
        ensemblEventEmitter : React.PropTypes.instanceOf(EventEmitter)
    },

    getInitialState: function () {
        return ({
            xAxisCategories:{},
            yAxisCategories:{},
            yAxisCategoriesLinks: {},
            seriesDataNA: [],
            seriesDataNAString: "NA",
            seriesDataBelowCutoff: [],
            seriesDataBelowCutoffString: "Below cutoff",
            seriesDataRanges: [
                {
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
                }
            ],

            legend_1: false,
            legend_2: false,
            legend_3: false,
            legend_4: false

        })
    },

    handleClick: function (index) {
        if (index == 1) {
            this.setState({legend_1: !this.state.legend_1});
        }
        else if (index == 2) {
            this.setState({legend_2: !this.state.legend_2});
        }
        else if (index == 3) {
            this.setState({legend_3: !this.state.legend_3});
        }
        else if (index == 4) {
            this.setState({legend_4: !this.state.legend_4});
        }
    },

    _anatomogramTissueMouseEnter: function(svgPathId) {
      Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseEnter', {svgPathId: svgPathId});
    },

    _anatomogramTissueMouseLeave: function(svgPathId) {
      Highcharts.fireEvent(this.refs.chart.getChart(), 'handleGxaAnatomogramTissueMouseLeave', {svgPathId: svgPathId});
    },

    _registerListeners: function() {
      if (this.props.anatomogramEventEmitter) {
          this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
          this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
      }
      //ensemblEventEmitter only emits events, it doesn't receive them
    },

    componentDidMount: function () {
        this._registerListeners();
        var heatmap = this.refs.chart.getChart();
        heatmap.series[0].hide();
    },

    componentDidUpdate: function () {
        var heatmap = this.refs.chart.getChart();
        heatmap.series[0].hide();

        this.state.legend_1 ? heatmap.series[1].hide() : heatmap.series[1].show();
        this.state.legend_2 ? heatmap.series[2].hide() : heatmap.series[2].show();
        this.state.legend_3 ? heatmap.series[3].hide() : heatmap.series[3].show();
        this.state.legend_4 ? heatmap.series[4].hide() : heatmap.series[4].show();

    },

    render: function () {
        var atlasBaseURL = this.props.atlasBaseURL;
        var yAxisCategoriesLinks = this.props.yAxisCategoriesLinks;
        var yAxisCategories = this.props.yAxisCategories;

        var highchartsOptions = {
            plotOptions: {
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
                        },
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
                enabled: false //remove highchart text in the bottom right corner
            },
            chart: {
                type: 'heatmap',
                marginTop: 82,//labels
                marginRight: 36,//leave space for the export button to appear
                plotBorderWidth: 1,
                height: yAxisCategories.length * 12 + 200,
                zoomType: 'xy',
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
                useHTML:true,
                enabled: false,
                itemDistance: 18,
                symbolWidth: 12,
                symbolHeight:12,
                x: 150,
                align: 'left',
                verticalAlign: 'bottom',
                layout: 'horizontal',
                itemStyle: {
                    lineHeight:"10px",
                    fontSize: "10px",
                    color: '#606060',
                    fontWeight: 'normal'
                }
            },
            title: null,
            xAxis: { //tissues
                tickLength: 5,
                tickColor: "rgb(192, 192, 192)",
                lineColor: "rgb(192, 192, 192)",
                labels: {
                    y: -6,
                    style: {
                        fontSize: "9px"
                    },
                    autoRotation: [-45, -90],
                    formatter: function() {
                        return this.value.label
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
                        fontSize: "10px",
                        color: "#148ff3"
                    },
                    formatter: function() {
                        return '<a href="' + atlasBaseURL +'/experiments/' + yAxisCategoriesLinks[this.value] + '">' + this.value + '</a>';
                    }
                },
                categories: this.props.yAxisCategories,
                title: null,
                gridLineWidth: 0,
                minorGridLineWidth: 0
            },
            tooltip: {
                useHTML: true,
                formatter: function() {
                    return 'Sample name: <b>' + this.series.yAxis.categories[this.point.y] + '</b>  <br> Tissue: <b>' + this.series.xAxis.categories[this.point.x].label + '</b><br><b>' +
                        '</b>' +
                        '<span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:' + this.point.color + ';">' +
                        '</span> Expression level: <b></span>' +
                        'Expression level: <b>' + this.point.value + '</b>';
                }
            },
            anatomogramEventEmitter: this.props.anatomogramEventEmitter,
            ensemblEventEmitter: this.props.ensemblEventEmitter,
            series: [{
                name: this.props.seriesDataNAString,
                color: "#f7f7f7",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataNA
            }, {
                name: this.props.seriesDataBelowCutoffString,
                color: "#eaeaea",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataBelowCutoff
            }, {
                name: this.props.seriesDataRanges[0].label,
                color: "#dff8ff",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataRanges[0].seriesData
            }, {
                name: this.props.seriesDataRanges[1].label,
                color: "#9fd2fa",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataRanges[1].seriesData
            }, {
                name: this.props.seriesDataRanges[2].label,
                color: "#45affd",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataRanges[2].seriesData
            }]
        };

        var clsName_1 = this.state.legend_1 ? 'legend-item legend-item-off' : 'legend-item';
        var clsName_2 = this.state.legend_2 ? 'legend-item legend-item-off' : 'legend-item';
        var clsName_3 = this.state.legend_3 ? 'legend-item legend-item-off' : 'legend-item';
        var clsName_4 = this.state.legend_4 ? 'legend-item legend-item-off' : 'legend-item';

        var barcharts_legend = (
            <div id ="barcharts_legend_list_items" ref="barcharts_legend_items">
                <div className="legend-item">Click to interact:</div>

                <div id="legend_1" ref="legend_1" className={clsName_1} onClick={this.handleClick.bind(this,1)} >
                    <div className="legend-rectangle col_below"></div>
                    <span>Below cutoff</span>
                </div>
                <div id="legend_2" className={clsName_2} onClick={this.handleClick.bind(this,2)}>
                    <div className="legend-rectangle col_low"></div>
                    <span>Low</span>
                </div>
                <div id="legend_3" className={clsName_3} onClick={this.handleClick.bind(this,3)}>
                    <div className="legend-rectangle col_med"></div>
                    <span>Medium</span>
                </div>
                <div id="legend_4" className={clsName_4} onClick={this.handleClick.bind(this,4)}>
                    <div className="legend-rectangle col_high"></div>
                    <span>High</span>
                </div>

                <div className="legend-item">
                    <span className="icon icon-generic" data-icon="i" data-toggle="tooltip" data-placement="bottom"
                          title="This range of value indicates gene expression level accross different tissues. It is calculated in different a different way between DNA and RNA experiments">
                    </span>
                </div>

                <div id="legend_5" className="legend-item special">
                    <div className="legend-rectangle col_nd"></div>
                    <span>No data available</span>
                </div>
            </div>
        );

        return (
            <div>
                <div id="highcharts_container">
                    <ReactHighcharts config={highchartsOptions} ref="chart"/>
                    {barcharts_legend}
                </div>

            </div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = HighchartsHeatmap;
