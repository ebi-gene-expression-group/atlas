"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var ContrastTooltip = React.createClass({
    propTypes: {
        experimentDescription: React.PropTypes.string.isRequired,
        contrastDescription: React.PropTypes.string.isRequired,
        testReplicates: React.PropTypes.number.isRequired,
        referenceReplicates: React.PropTypes.number.isRequired,
        properties: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                contrastPropertyType: React.PropTypes.string,
                propertyName: React.PropTypes.string.isRequired,
                referenceValue: React.PropTypes.string.isRequired,
                testValue: React.PropTypes.string.isRequired
            })
        )
    },


    propertyRow: function (property) {
        if (!property.testValue && !property.referenceValue) {
            return null;
        }

        function isFactor(property) {
            return property.contrastPropertyType === "FACTOR";
        }

        var style = {whiteSpace: "normal"};

        if (isFactor(property)) {
            style.fontWeight = "bold";
        } else {
            style.color = "gray";
        }

        return (
            <tr key={property.contrastPropertyType + "-" + property.propertyName}>
                <td style={style}>{property.propertyName}</td>
                <td style={style}>{property.testValue}</td>
                <td style={style}>{property.referenceValue}</td>
            </tr>
        );
    },

    render: function () {
        return (
            <div className="gxaContrastTooltip">
                <div id="contrastExperimentDescription" style={{fontWeight: "bold", color: "blue", textAlign: "center"}}>{this.props.experimentDescription}</div>
                <div id="contrastDescription" style={{textAlign: "center"}}>{this.props.contrastDescription}</div>
                <table style={{padding: "0px", margin: "0px", width: "100%"}}>
                    <thead>
                        <tr>
                            <th>Property</th>
                            <th>Test value (N={this.props.testReplicates})</th>
                            <th>Reference value (N={this.props.referenceReplicates})</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.properties.map(this.propertyRow)}
                    </tbody>
                </table>
            </div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = ContrastTooltip;
