/** @jsx React.DOM */

/*global React */
var BioJSAtlasHeatmap = (function(React) {

    return React.createClass({
    //    <Heatmaps gxaBaseUrl='/gxa/' heatmapsParams={[
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
    //    <BioJsAtlasHeatmap ….. />

        //TODO The string this.props.widgetParameters must be built from this.props.species and this.props.factor
        buildHeatmap: function() {
            new Biojs.AtlasHeatmap({
                gxaBaseUrl: this.props.gxaBaseUrl,
                params: 'geneQuery=' + this.props.geneQuery + this.props.widgetParameters,
                isMultiExperiment: true,
                target: "widgetBody",
                heatmapClass: "heatmap-position",
                heatmapUrl: "/widgets/heatmap/baselineAnalytics"
            });
        },

        render: function() {
            // render a heatmap per each element in the array heatmapsParams
            return(
                <div id="widgetBody">
                    {this.buildHeatmap()}
                </div>
            );
        }

    });

})(React);