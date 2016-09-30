"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var contrastTablePropTypes = {
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
};

var ContrastTooltipTable = React.createClass({
    propTypes: contrastTablePropTypes,

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
            <div>
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

var ContrastTooltip = React.createClass({
  propTypes: Object.assign({
    resources: React.PropTypes.arrayOf(React.PropTypes.shape({
      type: React.PropTypes.oneOf(["gsea_go", "gsea_interpro", "gsea_reactome", "ma-plot"]).isRequired,
      uri: React.PropTypes.string.isRequired
    })).isRequired
  },contrastTablePropTypes ),


  render: function(){
    const descriptions = {
      "gsea_go" : "Click to view GO terms enrichment analysis plot",
      "gsea_interpro": "Click to view Interpro domains enrichment analysis plot",
      "gsea_reactome":"Click to view Reactome pathways enrichment analysis plot",
      "ma-plot" :"Click to view MA plot for the contrast across all genes"
    }
    return (
      <div className="gxaContrastTooltip">
        <ContrastTooltipTable {...this.props} />
        <div className="contrastPlots">
          <span>
            {this.props.resources.length && <span> Available plots: </span>}
            {this.props.resources.map(function(resource){
              return (
                <a
                href={resource.uri}
                key={resource.type}
                title={descriptions[resource.type]}
                target={"_blank"}>
                <img src={resource.icon} />
                </a>
              )
            })}
          </span>
          <div className="info"> Click label to interact with tooltip</div>
        </div>
      </div>
    )
  }
})

//*------------------------------------------------------------------*

module.exports = ContrastTooltip;
