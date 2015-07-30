"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var AtlasHeatmapBuilder = require('expression-atlas-heatmap');

require('../css/facets.css');

//*------------------------------------------------------------------*

var AtlasHeatmapWidget = React.createClass({

    //    <Heatmaps gxaBaseUrl='/gxa/' geneQuery='blood' heatmaps={[
    //    {
    //        // Data for first heatmap
    //        "geneQuery": "blood",
    //        "species": "Homo sapiens",
    //        "factor": "ORGANISM_PART"
    //    },
    //    {
    //        // Data for second heatmap
    //        "geneQuery": "blood",
    //        "species": "Mus musculus",
    //        "factor": "CELL_LINE"
    //    }
    //    ]} />

    componentDidMount: function() {
        AtlasHeatmapBuilder({
            gxaBaseUrl: this.props.gxaBaseUrl,
            params: 'geneQuery=' + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
            isMultiExperiment: true,
            target: this.refs.widgetBody.getDOMNode(),
            heatmapClass: "gxaHeatmapPosition",
            heatmapUrl: "/widgets/heatmap/baselineAnalytics",
            heatmapKey:this.props.species + this.props.factor
        });
    },

    render: function() {
        // render a heatmap per each element in the array heatmapsParams
        return(
            <div ref="widgetBody">

            </div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = AtlasHeatmapWidget;
