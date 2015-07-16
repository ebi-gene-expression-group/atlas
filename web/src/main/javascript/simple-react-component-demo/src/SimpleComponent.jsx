"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var SimpleComponent = React.createClass({

    render: function () {
        var message = "Hello world, I’m not hot!";
        return (
            <div>{message}</div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = SimpleComponent;