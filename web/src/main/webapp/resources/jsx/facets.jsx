/** @jsx React.DOM */

/*global React */
var facetsModule = (function($, React) {

    var build = function build(facetsConfig, eventEmitter) {

        var Facets = React.createClass({

             render: function () {
                 var speciesRows = Object.keys(this.props.facets).map( function (species) {
                     return <Species species={species} sources={this.props.facets[species]} />;
                }.bind(this));

                return (
                    <span>
                        {speciesRows}
                    </span>
                );
            }
        });

        var Species = React.createClass({

            render: function () {
                var sourcesRows = Object.keys(this.props.sources).map(function( sources ){
                    var sourcesObject = this.props.sources[sources];
                    return <Source factor={sourcesObject.factor} name={sourcesObject.source} />;

                }.bind(this));

                return (
                    <span>
                        <div>
                            <span>{this.props.species}</span>
                        </div>
                    {sourcesRows}
                    </span>
                );
            }
        });

        var Source = React.createClass({

            render: function() {
                return (
                    <span>
                        <input type="checkbox" /> {this.props.name}
                    </span>
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