"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

require('../css/facets.css');
var BaselineHeatmapWidget = require('./baseline-heatmap-widget.jsx');

//*------------------------------------------------------------------*

var Heatmaps = React.createClass({

    render: function () {
        var geneQuery = this.props.geneQuery;
        return (
            <div>
                {this.props.heatmaps.map(function (heatmapParameters) {
                    return <BaselineHeatmapWidget key={heatmapParameters.species + heatmapParameters.factor} gxaBaseUrl={"/gxa"}
                        geneQuery={geneQuery} species={heatmapParameters.species} factor={heatmapParameters.factor} />;
                })}
            </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = Heatmaps;


