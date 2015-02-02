/** @jsx React.DOM */

/*global React */
var Heatmaps = (function (React) {

    return React.createClass({

        render: function () {
            // this.props.geneQuery
            var geneQuery = this.props.geneQuery;
            return (
                <div>
                    {this.props.heatmaps.map(function (heatmapParameters) {
                        return <BioJSAtlasHeatmap gxaBaseUrl={"/gxa"}
                            geneQuery={geneQuery} species={heatmapParameters.species} factor={heatmapParameters.factor} />;
                    })}
                </div>
            );
        }
    });

})(React);
