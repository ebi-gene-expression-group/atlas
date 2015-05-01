/** @jsx React.DOM */

/*global React */
var FactorTooltip = (function(React) {

    return React.createClass({

        propertyRow: function (property) {
            if (!property.testValue) {
                return null;
            }

            function isFactor(property) {
                return property.contrastPropertyType === 'FACTOR';
            }

            var style = {'white-space': 'normal'};

            if (isFactor(property)) {
                style['font-weight'] = 'bold';
            } else {
                style['color'] = 'gray';
            }

            return (
                React.DOM.tr(null, 
                    React.DOM.td({style: style}, property.propertyName), 
                    React.DOM.td({style: style}, property.testValue)
                )
            );
        },

        render: function () {
            return (
                React.DOM.div(null, 
                    React.DOM.table({className: "gxaTableGrid", style: {padding: '0px', margin: '0px'}}, 
                        React.DOM.thead(null, 
                            React.DOM.tr(null, 
                                React.DOM.th({className: "gxaHeaderCell"}, "Property"), 
                                React.DOM.th({className: "gxaHeaderCell"}, "Value (N=", this.props.replicates, ")")
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
