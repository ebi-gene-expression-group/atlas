"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var SimpleComponent = React.createClass({

    propTypes: {
        message: React.PropTypes.string.isRequired,
        aString: React.PropTypes.string,
        aNumber: React.PropTypes.number,
        aBoolean: React.PropTypes.bool,
        anObject: React.PropTypes.object,
        anArray: React.PropTypes.object
    },

    render: function () {
        return (
            <div>{this.props.message}</div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = SimpleComponent;