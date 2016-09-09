"use strict";

var React = require("react");
var Slider = require("rc-slider");
var DisplayLevelsButton = require('display-levels-button');
require('./CoexpressionOption.less');
require('rc-slider/assets/index.css');


var CoexpressionOption = React.createClass({
    propTypes: {
        geneName: React.PropTypes.string.isRequired,
        numCoexpressionsVisible: React.PropTypes.number.isRequired,
        numCoexpressionsAvailable: React.PropTypes.number.isRequired,
        showCoexpressionsCallback: React.PropTypes.func.isRequired
    },
    getInitialState: function () {
        return {visible: 0};
    },
    _chooseValue: function (amount) {
        this.setState({visible: amount});
        this.props.showCoexpressionsCallback(amount);
    },

    _turnOnWithDefaultValue: function () {
        this._chooseValue(10);
    },

    _showOfferToDisplay: function () {
        return <DisplayLevelsButton hideText=""
                                    showText="Add similarly expressed genes"
                                    onClickCallback={this._turnOnWithDefaultValue}
                                    displayLevels={false}
                                    width="250px"
                                    fontSize="14px"/>
    },

    _showSlider: function () {
        var marks = {
            0: "off",
            10: "10"
        };
        marks[this.props.numCoexpressionsAvailable] = this.props.numCoexpressionsAvailable;
        return <div>
            <p>{"Display genes with similar expressions to " + this.props.geneName + ":"}</p>
            <div className="gxaSlider">
                <Slider min={0} max={this.props.numCoexpressionsAvailable} onAfterChange={this._chooseValue}
                        marks={marks} included={false} defaultValue={10}/>
            </div>
        </div>
    },

    render: function () {
        return <div className="gxaDisplayCoexpressionOffer">
            {this.props.numCoexpressionsAvailable
              ? this.props.numCoexpressionsVisible
                  ? this._showSlider()
                  : this._showOfferToDisplay()
              : <span>{"No genes with similar expressions to "+this.props.geneName+" available for display"}</span>
            }
        </div>
    }
});
module.exports = CoexpressionOption;
