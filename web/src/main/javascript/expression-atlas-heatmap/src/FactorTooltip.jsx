"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var FactorTooltip = React.createClass({

    propertyRow: function (property) {
        if (!property.testValue) {
            return null;
        }

        function isFactor(property) {
            return property.contrastPropertyType === 'FACTOR';
        }

        var style = {'whiteSpace': 'normal'};

        if (isFactor(property)) {
            style['fontWeight'] = 'bold';
        } else {
            style['color'] = 'gray';
        }

        return (
            <tr key={property.propertyName}>
                <td style={style}>{property.propertyName}</td>
                <td style={style}>{property.testValue}</td>
            </tr>
        );
    },

    render: function () {
        return (
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Property</th>
                            <th>Value (N={this.props.replicates})</th>
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

module.exports = FactorTooltip;