"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

var Highcharts = require('react-highcharts');
require('highcharts-heatmap')(Highcharts.Highcharts);

//*------------------------------------------------------------------*

require('../css/heatmap-highcharts.css');

//*------------------------------------------------------------------*

var HighchartsHeatmap = React.createClass({

    propTypes: {
        profiles: React.PropTypes.object.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired

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
                    to: 0.25,
                    seriesData: []
                }, {
                    label: "Medium",
                    from: 0.25,
                    to: 0.75,
                    seriesData: []
                }, {
                    label: "High",
                    from: 0.75,
                    to: 1.0,
                    seriesData: []
                }
            ]
        })
    },

    handleClick: function (index) {
        var heatmap = this.refs.chart.getChart();

        //$(".highcharts-legend-item").eq($(this)).trigger("click");
        $(this).toggleClass("legend-item-off");

        if (heatmap.series[index].visible) {
            heatmap.series[index].hide();
        } else {
            heatmap.series[index].show();
        }

    },

    componentDidMount: function () {
        var heatmap = this.refs.chart.getChart();
        heatmap.series[0].hide();
    },

    render: function () {
        var atlasBaseURL = this.props.atlasBaseURL;
        var yAxisCategoriesLinks = this.props.yAxisCategoriesLinks;
        var yAxisCategories = this.props.yAxisCategories;

        var highchartsOptions = {
            plotOptions: {
                series: {
                    color: '#3dc3b8' //color cell on mouse over
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
                //height: this.props.profiles.rows.length * 4+200,
                height: yAxisCategories.length * 12 + 200,
                zoomType: 'xy'
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
                    autoRotation: [-45, -90]
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
                        return '<a class="project_link"  href="' + atlasBaseURL +'/experiments/' + yAxisCategoriesLinks[this.value] + '">' + this.value + '</a>';
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
                    return 'Sample name: <b>' + this.series.yAxis.categories[this.point.y] + '</b>  <br> Tissue: <b>' + this.series.xAxis.categories[this.point.x] + '</b><br><b>' +
                        '</b>' +
                        '<span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:' + this.point.color + ';">' +
                        '</span> Expression level: <b></span>' +
                        'Expression level: <b>' + this.point.value + '</b>';
                }
            },
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

        var barcharts_legend = (
            <div id ="barcharts_legend_list_items" ref="barcharts_legend_items">
                <span>Click to interact:</span>

                <div id="legend_1" className="legend-item" onClick={this.handleClick.bind(this,1)}>
                    <div className="legend-rectangle col_below"></div><span>Below cutoff</span>
                </div>
                <div id="legend_2" className="legend-item" onClick={this.handleClick.bind(this,2)}>
                    <div className="legend-rectangle col_low"></div><span>Low</span>
                </div>
                <div id="legend_3" className="legend-item" onClick={this.handleClick.bind(this,3)}>
                    <div className="legend-rectangle col_med"></div><span>Medium</span>
                </div>

                <div id="legend_4" className="legend-item" onClick={this.handleClick.bind(this,4)}>
                    <div className="legend-rectangle col_high"></div><span>High</span>
                </div>

                <div id="legend_5" className="legend-item special" onClick={this.handleClick.bind(this,0)}>
                    <div className="legend-rectangle col_nd"></div><span>No data available</span>
                </div>
            </div>
        );

        return (
            <div>
                <div id="highcharts_container">
                    <Highcharts config={highchartsOptions} ref="chart"/>
                    {barcharts_legend}
                </div>

            </div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = HighchartsHeatmap;
