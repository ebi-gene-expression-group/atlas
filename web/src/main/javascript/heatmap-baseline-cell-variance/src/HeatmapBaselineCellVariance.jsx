"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var Highcharts = require('react-highcharts/more');

//*------------------------------------------------------------------*

var HeatmapBaselineCellVariance = React.createClass({

    propTypes: {
        quartiles: React.PropTypes.shape({
            min: React.PropTypes.number,
            lower: React.PropTypes.number,
            median: React.PropTypes.number,
            upper: React.PropTypes.number,
            max: React.PropTypes.number
        }).isRequired,
    },

    render: function () {

        //var highchartsOptions =
        //{
        //    "chart": { "type": "boxplot" },
        //    "credits": { "enabled": false },
        //    "title": { "text": "" },
        //    "legend": { "enabled": false },
        //    "xAxis": { "title": { "text": "Variance" } },
        //    "yAxis": { "title": { "text": "" }, "gridLineColor": "#FFFFFF" },
        //    "exporting": { "enabled": false },
        //    "plotOptions": {
        //        "boxplot": {
        //            "fillColor": '#F0F0E0',
        //            "lineWidth": 2,
        //            "medianColor": '#0C5DA5',
        //            "medianWidth": 3,
        //            "stemColor": '#A63400',
        //            "stemDashStyle": 'dot',
        //            "stemWidth": 1,
        //            "whiskerColor": '#3D9200',
        //            "whiskerLength": '20%',
        //            "whiskerWidth": 3
        //        }
        //    },
        //
        //    "tooltip": {
        //        "headerFormat": "",
        //        "style": {
        //            "padding": 5,
        //            "fontSize": '9px',
        //            "width": 80,
        //            "height": 100
        //        }
        //    },
        //
        //    series: [{
        //        "name": "Observations",
        //        "data": [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        //    }]
        //};

        var highchartsOptions = {
            credits: { enabled: false },
            chart:   { type: 'boxplot', margin: 0, width: 100, height: 50 },
            title:   { text: '' },
            legend:  { enabled: false },
            xAxis:   { title: { text: 'Variance' } },
            yAxis:   { title: { text: 'Axpression level' } },
            "plotOptions": {
                "boxplot": {
                    "fillColor": '#F0F0E0',
                    "lineWidth": 2,
                    "medianColor": '#0C5DA5',
                    "medianWidth": 3,
                    "stemColor": '#A63400',
                    "stemDashStyle": 'dot',
                    "stemWidth": 1,
                    "whiskerColor": '#3D9200',
                    "whiskerLength": '20%',
                    "whiskerWidth": 3
                }
            },
            series: [{
                name: 'Expression',
                data: [
                    [this.props.quartiles.min, this.props.quartiles.lower, this.props.quartiles.median, this.props.quartiles.upper, this.props.quartiles.max]
                ],
                "tooltip": {
                    "headerFormat": "",
                    "style": {
                        "padding": 5,
                        "fontSize": '9px',
                        "width": 80,
                        "height": 100
                    }
                },
            }]
        };

        var boxPlotStyle = {"height": "50px", "margin": "0px", "minWidth": "80px", "maxWidth": "150px"};
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