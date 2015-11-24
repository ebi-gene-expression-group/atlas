"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var URI = require('urijs');

//*------------------------------------------------------------------*

var BaselineHeatmapWidget = require('./baseline-heatmap-widget.jsx');

//*------------------------------------------------------------------*

var Heatmaps = React.createClass({
    propTypes: {
        geneQuery: React.PropTypes.string.isRequired,
        atlasHost: React.PropTypes.string.isRequired,
        /*
         [{"geneQuery":"zinc finger","species":"Homo sapiens","factor":"CELL_LINE"},
          {"geneQuery":"zinc finger","species":"Homo sapiens","factor":"ORGANISM_PART"}]
         */
        heatmaps: React.PropTypes.arrayOf(React.PropTypes.shape({
            geneQuery: React.PropTypes.string.isRequired,
            species: React.PropTypes.string.isRequired,
            factor: React.PropTypes.string.isRequired
        })).isRequired
    },

    render: function () {
        var moreThanOneSpecies = function() {
            var species = [];
            for (var i = 0 ; i < this.props.heatmaps.length ; i++) {
                if(species.indexOf(this.props.heatmaps[i].species) === -1) {
                    species.push(this.props.heatmaps[i].species);
                }
            }
            return species.length > 1;
        }.bind(this)();

        var geneQuery = this.props.geneQuery;
        var atlasHost = this.props.atlasHost;

        return (
            <div>
                {this.props.heatmaps.map(function (heatmap) {
                    return <BaselineHeatmapWidget key={heatmap.species + "_" + heatmap.factor} showAnatomogramLabel={moreThanOneSpecies}
                                                  atlasHost={atlasHost} geneQuery={geneQuery} species={heatmap.species} factor={heatmap.factor} />;
                })}
            </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = Heatmaps;


