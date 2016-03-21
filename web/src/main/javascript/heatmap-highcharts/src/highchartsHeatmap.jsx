"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

var Highcharts = require('react-highcharts');
require('highcharts-heatmap')(Highcharts.Highcharts);

//*------------------------------------------------------------------*


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

    render: function () {
        var atlasBaseURL = this.props.atlasBaseURL;
        var yAxisCategoriesLinks = this.props.yAxisCategoriesLinks;

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
                marginTop: 90,
                marginRight: 80,
                plotBorderWidth: 1,
                height: this.props.profiles.rows.length * 4+200,
//          height: yAxisCategories.length * 46,
                zoomType: 'xy'
            },
            legend: {
                x: 160,
                align: 'left',
                verticalAlign: 'bottom',
                layout: 'horizontal',
                itemStyle: {
                    fontSize: "10px",
                    color: '#606060',
                    fontWeight: 'normal'
                }
            },
            title: null,
            xAxis: {
                tickLength: 5,
                tickColor: "rgb(192, 192, 192)",
                lineColor: "rgb(192, 192, 192)",
                labels: {
                    y: -8,
                    style: {
                        fontSize: "10px"
                    },
                    autoRotation: [-45, -90]
                },
                opposite: 'true',
                categories: this.props.xAxisCategories
            },
            yAxis: {
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
                name: this.props.seriesDataBelowCutoffString,
                color: "#d7d7d7",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataBelowCutoff
            }, {
                name: this.props.seriesDataNAString,
                color: "#f7f7f7",
                borderWidth: 1,
                borderColor: "#fff",
                data: this.props.seriesDataNA
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

        return (
            <div id="highcharts_container" ref="container">
                <Highcharts config={highchartsOptions}/>
            </div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = HighchartsHeatmap;
