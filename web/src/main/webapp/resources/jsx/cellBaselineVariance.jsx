/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var baselineVarianceModule  = (function ($, React) {

    var build = function build(expressions) {

        var Variance =  React.createClass({

            render: function () {
                var expressionRow = this.props.expressions.map(function (expression) {
                    return <Expression expression={expression} quartiles={expression.quartiles} />
                }.bind(this));

                return (
                    <div> {expressionRow} </div>

                );
            }

        });

        var Expression = React.createClass({

            render: function () {

                var style = {"height": "400px", "margin": "auto", "min-width": "400px", "max-width": "600px"};

                return (

                    <div id="container" style={style}></div>

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
            Variance: Variance
        };

    };

    return {
        build: function (expressions) {
            return build(expressions);
        }
    };


})(jQuery, React);
