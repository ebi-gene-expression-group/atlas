import React from 'react'
import PropTypes from 'prop-types'

import './gxaGradient.css'

const LegendRow = ({lowValueColour, highValueColour, lowExpressionLevel, highExpressionLevel}) => {
  const spanStyle = {
    backgroundImage: `linear-gradient(to right, ${lowValueColour}, ${highValueColour})`
  }

  return (
    <div style={{display: `table-row`}}>
      <div className={`gxaDiffLegendLevelCell`}>{lowExpressionLevel}</div>
      <div className={`gxaDiffLegendGradientCell`}><span className={`gxaDiffLegendGradient`} style={spanStyle}/></div>
      <div className={`gxaDiffLegendLevelCell`}>{highExpressionLevel}</div>
    </div>
  )
}

LegendRow.propTypes = {
  lowValueColour: PropTypes.string.isRequired,
  highValueColour: PropTypes.string.isRequired,
  lowExpressionLevel: PropTypes.element.isRequired,
  highExpressionLevel: PropTypes.element.isRequired
}

export default LegendRow
