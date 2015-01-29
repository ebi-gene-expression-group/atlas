/** @jsx React.DOM */

/*global React */
var facetsModule = (function($, React) {

    var build = function build(facetsConfig, eventEmitter) {

        var Facets = React.createClass({displayName: 'Facets',

             render: function () {
                 var speciesRows = Object.keys(this.props.facets).map( function (species) {
                     return Species( {species:species, sources:this.props.facets[species]} );
                }.bind(this));

                return (
                    React.DOM.span(null, 
                        speciesRows
                    )
                );
            }
        });

        var Species = React.createClass({displayName: 'Species',

            render: function () {
                var sourcesRows = Object.keys(this.props.sources).map(function( sources ){
                    var sourcesObject = this.props.sources[sources];
                    return Source( {factor:sourcesObject.factor, name:sourcesObject.source} );

                }.bind(this));

                return (
                    React.DOM.span(null, 
                        React.DOM.div(null, 
                            React.DOM.span(null, this.props.species)
                        ),
                    sourcesRows
                    )
                );
            }
        });

        var Source = React.createClass({displayName: 'Source',

            render: function() {
                return (
                    React.DOM.div(null, 
                        React.DOM.input( {type:"checkbox"} ), " ", this.props.name
                    )
                );
            }
        });

        return {
                Facets: Facets
            };

    };

    return {
        build: function (facetsConfig) {
            return build(facetsConfig, new EventEmitter());
        }
    };

})(jQuery, React, EventEmitter);