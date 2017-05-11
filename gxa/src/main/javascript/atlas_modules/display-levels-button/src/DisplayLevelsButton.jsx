var React = require('react');
var ReactDOM = require('react-dom');

var $ = require('jquery');
require('jquery-ui-bundle');

var DisplayLevelsButton = React.createClass({

    propTypes: {
        hideText: React.PropTypes.string.isRequired,
        showText: React.PropTypes.string.isRequired,
        onClickCallback: React.PropTypes.func.isRequired,
        displayLevels: React.PropTypes.bool.isRequired,
        width: React.PropTypes.string,
        fontSize: React.PropTypes.string
    },

    _buttonText: function () {
        return this.props.displayLevels ? this.props.hideText : this.props.showText;
    },

    _updateButtonText: function () {
        $(ReactDOM.findDOMNode(this)).button({ label: this._buttonText() });
    },

    render: function () {
        var style = {
            padding: `12px`
        };
        if (this.props.width) {
            style.width = this.props.width;
        }
        if (this.props.fontSize) {
            style.fontSize = this.props.fontSize;
        }

        return (
            <button style={style} onClick={this.props.onClickCallback}/>
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