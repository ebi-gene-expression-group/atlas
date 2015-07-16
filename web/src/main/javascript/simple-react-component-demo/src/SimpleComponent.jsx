"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var SimpleComponent = React.createClass({

    render: function () {
        var message = "Hello world, I’m super super super hot!";
        return (
            <div>{message}</div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = SimpleComponent;