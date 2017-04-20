import React from 'react';
import $ from 'jquery';
import 'jquery.browser';

import EventEmitter from 'events';

import BaselineHeatmapWidget from './BaselineHeatmapWidget.jsx';

const AtlasFeedback = require('expression-atlas-feedback');

class BaselineHeatmaps extends React.Component {

    componentDidMount() {
        if (window.ga === `undefined`) {
            window.ga = () => {};
        }
    }

    render() {
        const atlasFeedback = $.browser.msie ? null
            : <AtlasFeedback
                  collectionCallback = {(score, comment) => {
                    window.ga('send','event','BaselineHeatmaps', 'feedback', comment, score);
                  }}
              />;

        return (
            <div>
                {this.props.heatmaps.map(heatmap =>
                    <BaselineHeatmapWidget
                        key = {`${heatmap.species}_${heatmap.factor.name}`}
                        showAnatomogram = {this.props.showAnatomograms}
                        showHeatmapLabel = {this._hasMoreThanOneSpecies()}
                        species = {heatmap.species}
                        factor = {heatmap.factor}
                        atlasUrl = {this.props.atlasUrl}
                        query = {this.props.query}
                        geneQuery = {this.props.geneQuery}
                        conditionQuery = {this.props.conditionQuery}
                        anatomogramDataEventEmitter = {this.props.anatomogramDataEventEmitter}
                    />
                )}
                {atlasFeedback}
            </div>
        );
    }

    _hasMoreThanOneSpecies () {
        const uniqueSpecies = new Set();
        this.props.heatmaps.forEach(el => { uniqueSpecies.add(el.species) });
        return uniqueSpecies.size > 1;
    }
}

BaselineHeatmaps.propTypes = {
    atlasUrl: React.PropTypes.string.isRequired,
    geneQuery: React.PropTypes.string.isRequired,
    conditionQuery: React.PropTypes.string,
    /*
     [{"species":"Homo sapiens", "factor":"CELL_LINE"},
     {"species":"Mus musculus", "factor":"ORGANISM_PART"}]
     */
    showAnatomograms: React.PropTypes.bool.isRequired,
    heatmaps: React.PropTypes.arrayOf(React.PropTypes.shape({
        species: React.PropTypes.string.isRequired,
        factor: React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            value: React.PropTypes.string.isRequired
        })
    })).isRequired,
    anatomogramDataEventEmitter: React.PropTypes.instanceOf(EventEmitter).isRequired
};

export default BaselineHeatmaps;
