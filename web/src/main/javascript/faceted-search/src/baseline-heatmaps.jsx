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
         [{"geneQuery":"GO:0001234","species":"Homo sapiens","factor":"CELL_LINE"},
          {"geneQuery":"GO:0001234","species":"Mus musculus","factor":"ORGANISM_PART"}]
         */
        showAnatomograms: React.PropTypes.bool.isRequired,
        heatmaps: React.PropTypes.arrayOf(React.PropTypes.shape({
            geneQuery: React.PropTypes.string.isRequired,
            species: React.PropTypes.string.isRequired,
            factor: React.PropTypes.string.isRequired
        })).isRequired
    },

    render: function () {
        return (
            <div>
                {this.props.heatmaps.map(function (heatmap) {
                    return <BaselineHeatmapWidget key={heatmap.species + "_" + heatmap.factor}
                                                  showAnatomogram={this.props.showAnatomograms} showHeatmapLabel={this._hasMoreThanOneSpecies()}
                                                  species={heatmap.species} factor={heatmap.factor}
                                                  atlasHost={this.props.atlasHost} geneQuery={this.props.geneQuery} />;
                }.bind(this))}
            </div>
        );
    },

    _hasMoreThanOneSpecies: function() {
        var species = [];
        for (var i = 0 ; i < this.props.heatmaps.length ; i++) {
            if (species.indexOf(this.props.heatmaps[i].species) === -1) {
                species.push(this.props.heatmaps[i].species);
            }
        }
        return species.length > 1;
    }
});

//*------------------------------------------------------------------*

module.exports = Heatmaps;


