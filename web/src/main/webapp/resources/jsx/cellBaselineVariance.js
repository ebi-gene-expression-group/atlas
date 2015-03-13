/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var baselineVarianceModule  = (function ($, React) {

    var build = function build(expression) {

        var CellBaselineExpression = React.createClass({displayName: 'CellBaselineExpression',

            render: function () {
                var boxPlotStyle = {"height": "400px", "margin": "auto", "min-width": "400px", "max-width": "600px"};

                return (

                    React.DOM.td(null, 
                        React.DOM.div( {id:"container", style:boxPlotStyle})
                    )



                );
            },

            componentDidMount: function () {
                if(this.props.quartiles) {
                    $('#container').highcharts({

                        chart: {
                            type: 'boxplot'
                        },

                        title: {
                            text: 'Highcharts box plot styling'
                        },

                        legend: {
                            enabled: false
                        },

                        xAxis: {
                            //categories: ['1', '2', '3', '4', '5'],
                            title: {
                                text: 'Variance'
                            }
                        },

                        yAxis: {
                            title: {
                                text: 'Observations'
                            }

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
                        }]

                    });
                }

            }


        });

        return {
            CellBaselineExpression: CellBaselineExpression
        };

    };

    return {
        build: function (expression) {
            return build(expression);
        }
    };


})(jQuery, React);
