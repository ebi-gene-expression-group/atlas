/** @jsx React.DOM */

/*global React */
var Heatmaps = (function (React) {

    return React.createClass({

        render: function () {
            // this.props.geneQuery
            return (
                React.DOM.div(null, 
                    JSON.stringify(this.props.heatmaps)
                )
            );
        }
    });

})(React);
