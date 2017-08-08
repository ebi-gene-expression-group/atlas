import React from 'react'
import PropTypes from 'prop-types'

import BaselineHeatmapWidget from './BaselineHeatmapWidget.jsx'

import AtlasFeedback from 'expression-atlas-feedback'

class BaselineHeatmaps extends React.Component {

    render() {
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
                        geneQuery = {this.props.geneQuery}
                        conditionQuery = {this.props.conditionQuery} />
                )}
                <AtlasFeedback
                  collectionCallback = {
                    typeof window.ga === `function` ?
                      (score, comment) => { window.ga('send','event','BaselineHeatmaps', 'feedback', comment, score) } :
                      () => {}
                  }
                />
            </div>
        );
    }

    _hasMoreThanOneSpecies () {
        const uniqueSpecies = new Set()
        this.props.heatmaps.forEach(heatmap => { uniqueSpecies.add(heatmap.species) })
        return uniqueSpecies.size > 1
    }
}

BaselineHeatmaps.propTypes = {
    atlasUrl: PropTypes.string.isRequired,
    geneQuery: PropTypes.string.isRequired,
    conditionQuery: PropTypes.string,
    /*
     [{"species":"Homo sapiens", "factor":"CELL_LINE"}, {"species":"Mus musculus", "factor":"ORGANISM_PART"}]
     */
    showAnatomograms: PropTypes.bool.isRequired,
    heatmaps: PropTypes.arrayOf(React.PropTypes.shape({
        species: PropTypes.string.isRequired,
        factor: PropTypes.shape({
            name: PropTypes.string.isRequired,
            value: PropTypes.string.isRequired
        })
    })).isRequired
};

export default BaselineHeatmaps;
