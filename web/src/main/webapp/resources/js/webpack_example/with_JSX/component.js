/** @jsx React.DOM */

/*global React */
var SimpleComponent = (function (React) {

    return React.createClass({

        render: function () {
            var message = "Hola mundo";
            return (
                React.DOM.p(null, message)
            );
        }
    });

})(React);
