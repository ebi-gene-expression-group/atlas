"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var Highcharts = require('react-highcharts');
require('highcharts-more')(Highcharts.Highcharts);

//*------------------------------------------------------------------*

var HeatmapBaselineCellVariance = React.createClass({

    propTypes: {
        quartiles: React.PropTypes.shape({
            min: React.PropTypes.number,
            lower: React.PropTypes.number,
            median: React.PropTypes.number,
            upper: React.PropTypes.number,
            max: React.PropTypes.number
        }).isRequired
    },

    render: function () {

        var chartWidth = 115;
        var chartHeight = 105;
        var chartMargin = 0;

        var highchartsOptions = {
            credits: { enabled: false },
            chart:   { type: "boxplot", width: chartWidth, height: chartHeight, margin: chartMargin},
            title:   { text: "" },
            legend:  { enabled: false },
            xAxis:   { title: { text: "Variance" } },
            yAxis:   {
                title: { text: "Expression level" },
                labels: {
                    align: "left",
                    x: 0,
                    y: -2
                }
            },
            plotOptions: {
                boxplot: {
                    fillColor: "#F0F0E0",
                    lineWidth: 2,
                    medianColor: "#0C5DA5",
                    medianWidth: 3,
                    stemColor: "#A63400",
                    stemDashStyle: "dot",
                    stemWidth: 1,
                    whiskerColor: "#3D9200",
                    whiskerLength: "20%",
                    whiskerWidth: 3
                }
            },
            series: [{
                name: "Expression",
                data: [
                    [this.props.quartiles.min, this.props.quartiles.lower, this.props.quartiles.median, this.props.quartiles.upper, this.props.quartiles.max]
                ]
            }],
            tooltip: {
                headerFormat: "",
                style:{
                    fontSize: "10px",
                    padding: 5
                }
            }
        };

        var boxPlotStyle = {width: chartWidth, height: chartHeight, margin: chartMargin};
        return (
            <td>
                <div id="container" ref="container" style={boxPlotStyle}>
                    <Highcharts config={highchartsOptions}/>
                </div>
            </td>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = HeatmapBaselineCellVariance;