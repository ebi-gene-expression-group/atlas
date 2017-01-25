const React = require('react');

require('./gxaGradient.css');

const LegendRow = React.createClass({

    propTypes: {
        lowValueColour: React.PropTypes.string.isRequired,
        highValueColour: React.PropTypes.string.isRequired,
        lowExpressionLevel: React.PropTypes.element.isRequired,
        highExpressionLevel: React.PropTypes.element.isRequired
    },

    render: function () {
        const spanStyle = {
            backgroundImage: `linear-gradient(to right, ${this.props.lowValueColour}, ${this.props.highValueColour})`
        };

        return (
          this.props.lowExpressionLevel || this.props.highExpressionLevel
          ? <div style={{display: "table-row"}}>
                <div className="gxaGradientLevel gxaGradientLevelMin">{this.props.lowExpressionLevel}</div>
                <div style={{display: "table-cell"}}>
                    <span className="gxaGradientColour" style={spanStyle} />
                </div>
                <div className="gxaGradientLevel gxaGradientLevelMax">{this.props.highExpressionLevel}</div>
            </div>
          : null  
        );
    }
});

module.exports = LegendRow;
