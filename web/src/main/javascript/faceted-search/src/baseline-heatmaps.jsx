"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var URI = require('URIjs');

//*------------------------------------------------------------------*

var BaselineHeatmapWidget = require('./baseline-heatmap-widget.jsx');

//*------------------------------------------------------------------*

var Heatmaps = React.createClass({
    propTypes: {
        geneQuery: React.PropTypes.string.isRequired,
        host: React.PropTypes.string.isRequired,
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
        var geneQuery = this.props.geneQuery;
        var gxaBaseURL = new URI({hostname: this.props.host, path: "/gxa"});

        return (
            <div>
                {this.props.heatmaps.map(function (heatmap) {
                    return <BaselineHeatmapWidget key={heatmap.species + "_" + heatmap.factor}
                                                  gxaBaseUrl={gxaBaseURL.normalize().toString()} geneQuery={geneQuery} species={heatmap.species} factor={heatmap.factor}
                                                  isWidget={false} />;
                })}
            </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = Heatmaps;


