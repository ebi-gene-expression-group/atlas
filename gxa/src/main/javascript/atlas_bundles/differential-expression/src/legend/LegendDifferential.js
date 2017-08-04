import React from 'react'
import PropTypes from 'prop-types'

import LegendRow from './LegendRow'

const LegendDifferential = ({minDownLevel, maxDownLevel, minUpLevel, maxUpLevel}) =>
  <div className={`gxaLegend`}>
    <div style={{display: `inline-table`}}>
      {isNaN(minDownLevel) && isNaN(maxDownLevel) ?
        null :
        <LegendRow lowExpressionLevel={<span>{minDownLevel}</span>}
                   highExpressionLevel={<span>{maxDownLevel}</span>}
                   lowValueColour={`#C0C0C0`}
                   highValueColour={`#0000FF`}/>
      }
      {isNaN(minUpLevel) && isNaN(maxUpLevel) ?
        null :
        <LegendRow lowExpressionLevel={<span>{minUpLevel}</span>}
                   highExpressionLevel={<span>{maxUpLevel}</span>}
                   lowValueColour={`#FFAFAF`}
                   highValueColour={`#FF0000`}/>
      }
    </div>
  </div>

LegendDifferential.propTypes = {
  minDownLevel: PropTypes.number.isRequired,
  maxDownLevel: PropTypes.number.isRequired,
  minUpLevel: PropTypes.number.isRequired,
  maxUpLevel: PropTypes.number.isRequired
}

export default LegendDifferential
