"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var AtlasHeatmapBuilder = require('heatmap-anatomogram');

//*------------------------------------------------------------------*

var BaselineHeatmapWidget = React.createClass({
    propTypes: {
        gxaBaseUrl: React.PropTypes.string.isRequired,
        geneQuery: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        factor: React.PropTypes.string.isRequired,
        showAnatomogramLabel: React.PropTypes.bool.isRequired
    },

    componentDidMount: function() {
        AtlasHeatmapBuilder({
            gxaBaseUrl: this.props.gxaBaseUrl,
            params: 'geneQuery=' + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
            isMultiExperiment: true,
            target: this.refs.widgetBody.getDOMNode(),
            heatmapUrl: "/widgets/heatmap/baselineAnalytics",
            heatmapKey: this.props.species + "-" + this.props.factor,
            isWidget: false,
            showAnatomogramLabel: this.props.showAnatomogramLabel
        });
    },

    render: function() {
        return(
            <div ref="widgetBody" style={{paddingBottom: "30px"}}></div>
        );
    }

});

//*------------------------------------------------------------------*

module.exports = BaselineHeatmapWidget;
