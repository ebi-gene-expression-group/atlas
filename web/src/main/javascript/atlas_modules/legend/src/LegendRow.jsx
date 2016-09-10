"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

require('./gxaGradient.css');

//*------------------------------------------------------------------*

var LegendRow = React.createClass({

    propTypes: {
        lowValueColour: React.PropTypes.string.isRequired,
        highValueColour: React.PropTypes.string.isRequired,
        lowExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.number, React.PropTypes.element]).isRequired,    // Baseline legend rows can be a React <span> element returned by NumberFormat
        highExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.number, React.PropTypes.element]).isRequired
    },

    render: function () {
        var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -ms-linear-gradient(left, ${lowValueColour}, ${highValueColour}); background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
        var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/g, this.props.lowValueColour).replace(/\${highValueColour}/g, this.props.highValueColour);

        // for IE9
        var LT_IE10_FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";
        var lt_ie10_filter = LT_IE10_FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);

        return (
            <div style={{display: "table-row"}}>
                <div className="gxaGradientLevel gxaGradientLevelMin">{this.props.lowExpressionLevel}</div>
                <div style={{display: "table-cell"}}>
                    <span className="gxaGradientColour" style={{backgroundImage: backgroundImage, filter: lt_ie10_filter}} />
                </div>
                <div className="gxaGradientLevel gxaGradientLevelMax">{this.props.highExpressionLevel}</div>
            </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = LegendRow;
