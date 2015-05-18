var React = require('react');

var SimpleComponent = React.createClass({

    render: function () {
        var message = "Hello world";
        return (
            <p>{message}</p>
        );
    }

});

module.exports = SimpleComponent;