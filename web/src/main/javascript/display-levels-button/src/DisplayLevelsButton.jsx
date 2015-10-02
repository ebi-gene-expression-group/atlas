"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
require('jquery-ui');

//*------------------------------------------------------------------*

require('../css/jquery-ui.min.css');

//*------------------------------------------------------------------*

var DisplayLevelsButton = React.createClass({

    propTypes: {
        hideText: React.PropTypes.string.isRequired,
        showText: React.PropTypes.string.isRequired,
        onClickCallback: React.PropTypes.func.isRequired,
        displayLevels: React.PropTypes.bool.isRequired
    },

    _buttonText: function () {
        return this.props.displayLevels ? this.props.hideText : this.props.showText;
    },

    _updateButtonText: function () {
        $(this.getDOMNode()).button({ label: this._buttonText() });
    },

    render: function () {
        return (
            <button id='display-levels' onClick={this.props.onClickCallback}></button>
        );
    },

    componentDidMount: function () {
        this._updateButtonText();
    },

    componentDidUpdate: function () {
        this._updateButtonText();
    }

});

//*------------------------------------------------------------------*

module.exports = DisplayLevelsButton;