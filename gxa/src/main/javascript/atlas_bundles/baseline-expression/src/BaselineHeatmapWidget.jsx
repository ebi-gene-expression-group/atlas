import React from 'react';

import EventEmitter from 'events';
import {ExpressionAtlasHeatmap} from 'expression-atlas-heatmap-highcharts';

class BaselineHeatmapWidget extends React.Component {

    render () {
        const speciesLabel = this._capitalize(this.props.species);
        const header = <h5>{(this.props.showHeatmapLabel ? `${speciesLabel} — ` : '') + this.props.factor.value}</h5>;

        return(
            <div>
                {header}
                <ExpressionAtlasHeatmap atlasUrl={this.props.atlasUrl}
                                        query={{
                                            gene: this.props.geneQuery,
                                            condition: this.props.conditionQuery,
                                            species: this.props.species,
                                            source: this.props.factor.name
                                        }}
                                        isWidget={false}
                                        showAnatomogram={this.props.showAnatomogram}
                                        anatomogramDataEventEmitter={this.props.anatomogramDataEventEmitter}
                />
            </div>
        );
    }

    _capitalize (str) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    }

    _removeUnderScore (str) {
        return str.replace(/[-_.]/g, ` `);
    }

}

const RequiredString = React.PropTypes.string.isRequired;
const RequiredBool = React.PropTypes.bool.isRequired;

BaselineHeatmapWidget.propTypes = {
    atlasUrl: RequiredString,
    geneQuery: RequiredString,
    conditionQuery: RequiredString,
    species: RequiredString,
    factor: React.PropTypes.shape({
        name: RequiredString,
        value: RequiredString
    }).isRequired,
    showAnatomogram: RequiredBool,
    showHeatmapLabel: RequiredBool,
    anatomogramDataEventEmitter: React.PropTypes.instanceOf(EventEmitter).isRequired
};

export default BaselineHeatmapWidget;
