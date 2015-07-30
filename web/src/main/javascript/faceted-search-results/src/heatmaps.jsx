"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

require('../css/facets.css');
var AtlasHeatmapWidget = require('./atlasHeatmapWidget.jsx');

//*------------------------------------------------------------------*

var Heatmaps = React.createClass({

    render: function () {
        var geneQuery = this.props.geneQuery;
        return (
            <div>
                {this.props.heatmaps.map(function (heatmapParameters) {
                    return <AtlasHeatmapWidget key={heatmapParameters.species + heatmapParameters.factor} gxaBaseUrl={"/gxa"}
                        geneQuery={geneQuery} species={heatmapParameters.species} factor={heatmapParameters.factor} />;
                })}
            </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = Heatmaps;


