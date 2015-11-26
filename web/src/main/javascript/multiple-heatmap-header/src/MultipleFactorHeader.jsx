"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var MultipleFactorHeader = React.createClass({

    propTypes: {
        colspan: React.PropTypes.number.isRequired,
        name: React.PropTypes.string.isRequired,
    },

    render: function() {
        return (
            <th className="gxaHorizontalHeaderCell" colSpan={this.props.colspan}>
                <div>{this.props.name}</div>
            </th>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = MultipleFactorHeader;