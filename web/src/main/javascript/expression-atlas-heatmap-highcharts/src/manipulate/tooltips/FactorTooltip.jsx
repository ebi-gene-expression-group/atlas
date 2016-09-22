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
      var propertyNames =
        this.props.properties
        .map((e)=>e.propertyName)
        .filter((e,ix,self)=>self.indexOf(e)==ix);

      return (
        <div className="gxaFactorTooltip">
          <table>
            <thead>
              <tr>
                <th>Property</th>
                <th>Value{this.props.replicates? " (N="+this.props.replicates+")":""}</th>
              </tr>
            </thead>
            <tbody>
              {propertyNames.map((propertyName)=>{
                var values =
                  this.props.properties
                  .filter((e)=>e.propertyName==propertyName)
                  .map((e)=>e.testValue)
                  .filter((e,ix,self)=>self.indexOf(e)==ix);
                return {
                  propertyName: propertyName,
                  testValue:
                    values.length
                    ? values.reduce((l,r)=>l+", "+r)
                    : ""
                }
              }).map(this.propertyRow)}
            </tbody>
          </table>
        </div>
      );
    }
});

//*------------------------------------------------------------------*

module.exports = FactorTooltip;
