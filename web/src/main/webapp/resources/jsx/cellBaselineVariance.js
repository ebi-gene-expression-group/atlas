/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var baselineVarianceModule  = (function ($, React) {

    var build = function build() {
        var CellBaselineVariance = React.createClass({displayName: 'CellBaselineVariance',

            render: function () {
                var boxPlotStyle = {"height": "100px", "margin": "0px", "min-width": "80px", "max-width": "120px"};
                return (

                    React.DOM.td(null, 
                        React.DOM.div( {id:"container", ref:"container", style:boxPlotStyle})
                    )



                );
            },

            componentDidMount: function () {
                if(this.props.quartiles) {
                    $(this.refs.container.getDOMNode()).highcharts({

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

                    });
                }

            }


        });

        return {
            CellBaselineVariance: CellBaselineVariance
        };

    };

    return {
        build: function () {
            return build();
        }
    };


})(jQuery, React);
