import React from 'react';

import EventEmitter from 'events';
import ExpressionAtlasHeatmap from 'expression-atlas-heatmap-highcharts';

const capitalizeFirstLetter = str => str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();

const BaselineHeatmapWidget = props =>
    <div className="row column margin-top-large">
        <h5>{(props.showHeatmapLabel ? `${capitalizeFirstLetter(props.species)} — ` : '') + props.factor.value}</h5>
        <ExpressionAtlasHeatmap atlasUrl={props.atlasUrl}
                                query={{
                                    gene: props.geneQuery,
                                    condition: props.conditionQuery,
                                    species: props.species,
                                    source: props.factor.name
                                }}
                                isWidget={false}
                                showAnatomogram={props.showAnatomogram}
                                anatomogramDataEventEmitter={props.anatomogramDataEventEmitter}
        />
    </div>;


BaselineHeatmapWidget.propTypes = {
    atlasUrl: React.PropTypes.string.isRequired,
    geneQuery: React.PropTypes.string.isRequired,
    conditionQuery: React.PropTypes.string.isRequired,
    species: React.PropTypes.string.isRequired,
    factor: React.PropTypes.shape({
        name: React.PropTypes.string.isRequired,
        value: React.PropTypes.string.isRequired
    }).isRequired,
    showAnatomogram: React.PropTypes.bool.isRequired,
    showHeatmapLabel: React.PropTypes.bool.isRequired,
    anatomogramDataEventEmitter: React.PropTypes.instanceOf(EventEmitter).isRequired
};

export default BaselineHeatmapWidget;
