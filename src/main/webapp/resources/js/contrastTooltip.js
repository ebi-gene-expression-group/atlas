/*global React */
var ContrastTooltip = (function(React) {

    return React.createClass({

        propertyRow: function (property) {
            if (!property.testValue && !property.referenceValue) {
                return null;
            }

            function isFactor(property) {
                return property.contrastPropertyType === 'FACTOR';
            }

            var style = {whiteSpace: 'normal'};

            if (isFactor(property)) {
                style['fontWeight'] = 'bold';
            } else {
                style['color'] = 'gray';
            }

            return (
                React.DOM.tr({key: property.propertyName},
                    React.DOM.td({style: style}, property.propertyName), 
                    React.DOM.td({style: style}, property.testValue), 
                    React.DOM.td({style: style}, property.referenceValue)
                )
            );
        },

        render: function () {
            return (

                React.DOM.div(null, 
                    React.DOM.div({id: "contrastExperimentDescription", style: {fontWeight: 'bold', color: 'blue', textAlign: 'center'}}, this.props.experimentDescription),
                    React.DOM.div({id: "contrastDescription", style: {textAlign: 'center'}}, this.props.contrastDescription),
                    React.DOM.table({className: "gxaTableGrid", style: {padding: '0px', margin: '0px', width: '100%'}}, 
                        React.DOM.thead(null, 
                            React.DOM.tr(null, 
                                React.DOM.th({className: "gxaHeaderCell"}, 
                                "Property"
                                ), 
                                React.DOM.th({className: "gxaHeaderCell"}, 
                                "Test value (N=", this.props.testReplicates, ")"
                                ), 
                                React.DOM.th({className: "gxaHeaderCell"}, 
                                "Reference value (N=", this.props.referenceReplicates, ")"
                                )
                            )
                        ), 
                        React.DOM.tbody(null, 
                            this.props.properties.map(this.propertyRow)
                        )
                    )
                )
            );
        }
    });
})(React);
