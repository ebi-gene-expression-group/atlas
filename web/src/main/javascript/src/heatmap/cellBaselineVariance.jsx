var $ = require('jquery');
var React = require('react');
var Highcharts = require('highcharts-commonjs');

function build() {
    var CellBaselineVariance = React.createClass({

        render: function () {
            var boxPlotStyle = {"height": "100px", "margin": "0px", "min-width": "80px", "max-width": "120px"};
            return (
                <td>
                    <div id="container" ref="container" style={boxPlotStyle}></div>
                </td>
            );
        },

        componentDidMount: function () {
            if(this.props.quartiles) {
                Highcharts.createChart({

                    chart: {
                        type: 'boxplot',
                        margin: 0
                    },

                    credits: {
                        enabled: false
                    },

                    title: {
                        text: ''
                    },

                    legend: {
                        enabled: false
                    },

                    xAxis: {
                        title: {
                            text: 'Variance'
                        }
                    },

                    yAxis: {
                        title: {
                            text: ''
                        },
                        gridLineColor: '#FFFFFF'
                    },

                    exporting: {
                        enabled: false
                    },

                    plotOptions: {
                        boxplot: {
                            fillColor: '#F0F0E0',
                            lineWidth: 2,
                            medianColor: '#0C5DA5',
                            medianWidth: 3,
                            stemColor: '#A63400',
                            stemDashStyle: 'dot',
                            stemWidth: 1,
                            whiskerColor: '#3D9200',
                            whiskerLength: '20%',
                            whiskerWidth: 3
                        }
                    },

                    series: [{
                        name: 'Observations',
                        data: [
                            [this.props.quartiles.min, this.props.quartiles.lower, this.props.quartiles.median, this.props.quartiles.upper, this.props.quartiles.max]
                        ]

                    }],

                    tooltip: {
                        headerFormat: '',
                        style: {
                            padding: 5,
                            fontSize: '9px',
                            width: 80,
                            height: 100
                        }
                    }

                },
                $(this.refs.container.getDOMNode()).h);
            }
        }
    });

    return {
        CellBaselineVariance: CellBaselineVariance
    };

};

exports.build = build;


