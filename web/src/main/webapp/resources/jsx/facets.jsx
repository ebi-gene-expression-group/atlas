
var facetsModule = (function($, React) {

    var build = function build(facetsConfig, eventEmitter) {

        var Facets = React.createClass({

            getInitialState: function () {
                return {
                    facets: this.props.facets
                };
            },

            render: function () {
                var speciesRows = Object.keys(this.props.facets).map( function (species) {
                     return <Species species={species} sources={this.props.facets[species]} />;
                }.bind(this));

                return (
                    <div>
                        {speciesRows}
                    </div>
                );
            }
        });

        var Species = React.createClass({

            getInitialState: function() {

            },

            render: function () {
                var sourcesRows = this.props.sources.map(function( sources ){
                    return <Source factor={sources.factor} name={sources.source} />;
                }.bind(this));

                return (
                    <div>
                    <span>{this.props.species}</span>
                    {sourcesRows}
                    </div>
                );
            }
        });

        var Source = React.createClass({

            render: function() {

                return (
                    <div>
                        <input type="checkbox" /> {this.props.source}
                    </div>
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