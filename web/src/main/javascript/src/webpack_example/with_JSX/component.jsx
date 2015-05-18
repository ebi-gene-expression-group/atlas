/** @jsx React.DOM */

var React = require('react');

/*global React */
var SimpleComponent = React.createClass({

    render: function () {
        var message = "Hola mundo";
        return (
            <p>{message}</p>
        );
    }

});

module.exports = SimpleComponent;