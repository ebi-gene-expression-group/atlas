"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var AtlasHeatmapBuilder = require('heatmap-anatomogram');

//*------------------------------------------------------------------*

var BaselineHeatmapWidget = React.createClass({
    propTypes: {
        atlasHost: React.PropTypes.string.isRequired,
        geneQuery: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        factor: React.PropTypes.string.isRequired,
        showAnatomogram: React.PropTypes.bool.isRequired,
        showHeatmapLabel: React.PropTypes.bool.isRequired
    },

    componentDidMount: function() {
        AtlasHeatmapBuilder({
            atlasHost: this.props.atlasHost,
            params: 'geneQuery=' + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
            isMultiExperiment: true,
            target: this.refs.widgetBody.getDOMNode(),
            heatmapUrl: "/widgets/heatmap/baselineAnalytics",
            heatmapKey: this.props.species + "-" + this.props.factor,
            isWidget: false,
            showAnatomogram: this.props.showAnatomogram
        });
    },

    render: function() {
        return(
            <div className="gxaBaselineHeatmap">
                {this.props.showHeatmapLabel ? <h5>{this._capitalize(this.props.species)}</h5> : null }
                <div ref="widgetBody" style={{paddingBottom: "30px"}}></div>
            </div>
        );
    },

    _capitalize: function capitalizeFirstLetter(str) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    }

});

//*------------------------------------------------------------------*

module.exports = BaselineHeatmapWidget;
