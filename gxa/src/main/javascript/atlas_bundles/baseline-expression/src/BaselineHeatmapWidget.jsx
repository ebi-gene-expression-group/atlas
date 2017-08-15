import React from 'react'
import PropTypes from 'prop-types'

import ExpressionAtlasHeatmap from 'expression-atlas-heatmap-highcharts'

const capitalizeFirstLetter = (str) => str.charAt(0).toUpperCase() + str.slice(1).toLowerCase()

const BaselineHeatmapWidget = (props) =>
  <div className="row column margin-top-large margin-bottom-xlarge">
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
      />
  </div>


BaselineHeatmapWidget.propTypes = {
    atlasUrl: PropTypes.string.isRequired,
    geneQuery: PropTypes.string.isRequired,
    conditionQuery: PropTypes.string.isRequired,
    species: PropTypes.string.isRequired,
    factor: PropTypes.shape({
        name: PropTypes.string.isRequired,
        value: PropTypes.string.isRequired
    }).isRequired,
    showAnatomogram: PropTypes.bool.isRequired,
    showHeatmapLabel: PropTypes.bool.isRequired
}

export default BaselineHeatmapWidget
