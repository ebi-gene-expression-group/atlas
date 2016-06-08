"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

// var heatmapRenderer = require('expression-atlas-heatmap');
var highchartsHeatmapRenderer = require('expression-atlas-heatmap-highcharts');

//*------------------------------------------------------------------*

var BaselineHeatmapWidget = React.createClass({
    propTypes: {
        atlasHost: React.PropTypes.string.isRequired,
        geneQuery: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        factor: React.PropTypes.string.isRequired,
        showAnatomogram: React.PropTypes.bool.isRequired,
        showHeatmapLabel: React.PropTypes.bool.isRequired,
        anatomogramDataEventEmitter: React.PropTypes.object.isRequired
    },

    componentDidMount: function() {
        highchartsHeatmapRenderer.render({
            atlasHost: this.props.atlasHost,
            params: "geneQuery=" + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
            analyticsSearch: true,
            isMultiExperiment: true,
            target: ReactDOM.findDOMNode(this.refs.widgetBody),
            isWidget: false,
            showAnatomogram: this.props.showAnatomogram,
            anatomogramDataEventEmitter: this.props.anatomogramDataEventEmitter
        });
    },

    componentDidUpdate: function() {
        highchartsHeatmapRenderer.render({
            atlasHost: this.props.atlasHost,
            params: "geneQuery=" + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
            analyticsSearch: true,
            isMultiExperiment: true,
            target: ReactDOM.findDOMNode(this.refs.widgetBody),
            isWidget: false,
            showAnatomogram: this.props.showAnatomogram,
            anatomogramDataEventEmitter: this.props.anatomogramDataEventEmitter
        });
    },

    render: function() {
        var speciesLabel = this._capitalize(this.props.species);
        var factorLabel = this._capitalize(this._removeUnderScore(this.props.factor));

        var widgetTitle = <h5>{(this.props.showHeatmapLabel ? speciesLabel + " — " : "") + factorLabel}</h5>;

        return(
            <div className="gxaBaselineHeatmap">
                {widgetTitle}
                <div ref="widgetBody" style={{paddingBottom: "30px"}}></div>
            </div>
        );
    },

    _capitalize: function capitalizeFirstLetter(str) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    },

    _removeUnderScore: function removeUnderScoreForWhiteSpace(str) {
        return str.replace(/[-_.]/g, ' ');
    }

});

//*------------------------------------------------------------------*

module.exports = BaselineHeatmapWidget;
