/** @jsx React.DOM */

/*global React */
var Heatmaps = (function (React) {

    return React.createClass({

        render: function () {
            return (
                <div>
                    {JSON.stringify(this.props.heatmaps)}
                </div>
            );
        }
    });

})(React);
