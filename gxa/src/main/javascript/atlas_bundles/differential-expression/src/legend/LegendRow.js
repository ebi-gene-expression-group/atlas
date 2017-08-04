import React from 'react'

import './gxaGradient.css'

const LegendRow = ({lowValueColour, highValueColour, lowExpressionLevel, highExpressionLevel}) => {

  const spanStyle = {
    backgroundImage: `linear-gradient(to right, ${lowValueColour}, ${highValueColour})`
  };

  return (
    lowExpressionLevel || highExpressionLevel
      ? <div style={{display: `table-row`}}>
        <div className={`gxaGradientLevel gxaGradientLevelMin`}>{lowExpressionLevel}</div>
        <div style={{display: `table-cell`}}>
          <span className={`gxaGradientColour`} style={spanStyle} />
        </div>
        <div className={`gxaGradientLevel gxaGradientLevelMax`}>{highExpressionLevel}</div>
      </div>
      : null
  )
}

LegendRow.propTypes = {
  lowValueColour: PropTypes.string.isRequired,
  highValueColour: PropTypes.string.isRequired,
  lowExpressionLevel: PropTypes.element.isRequired,
  highExpressionLevel: PropTypes.element.isRequired
}

export default LegendRow
