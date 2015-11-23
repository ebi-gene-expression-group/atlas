"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
require('jquery-ui');

//*------------------------------------------------------------------*

require('../css/jquery-ui.min.css');
require('../css/display-levels-button.css');

//*------------------------------------------------------------------*

var DisplayLevelsButton = React.createClass({

    propTypes: {
        hideText: React.PropTypes.string.isRequired,
        showText: React.PropTypes.string.isRequired,
        onClickCallback: React.PropTypes.func.isRequired,
        displayLevels: React.PropTypes.bool.isRequired,
        autoSize: React.PropTypes.bool
    },

    _buttonText: function () {
        return this.props.displayLevels ? this.props.hideText : this.props.showText;
    },

    _updateButtonText: function () {
        $(this.getDOMNode()).button({ label: this._buttonText() });
    },

    render: function () {
        var className = (this.props.autoSize) ? "" : "gxaWebpackMediumButton";
        return (
            <button className={className} onClick={this.props.onClickCallback}/>
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