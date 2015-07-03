/** @jsx React.DOM */

/*global React */
var BioJSAtlasHeatmap = (function(React) {

    return React.createClass({
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
            new Biojs.AtlasHeatmap({
                gxaBaseUrl: this.props.gxaBaseUrl,
                params: 'geneQuery=' + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
                isMultiExperiment: true,
                target: this.refs.widgetBody.getDOMNode(),
                heatmapClass: "gxaHeatmapPosition",
                heatmapUrl: "/widgets/heatmap/baselineAnalytics"
            });
        },

        render: function() {
            // render a heatmap per each element in the array heatmapsParams
            return(
                React.DOM.div({ref: "widgetBody"}

                )
            );
        }

    });

})(React);