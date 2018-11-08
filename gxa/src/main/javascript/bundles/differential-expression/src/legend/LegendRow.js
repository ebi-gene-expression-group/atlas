import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

const DifferentialLegendLevel = styled.div`
  display: table-cell;
  width: 10%;
  white-space: nowrap;
  font-size: x-small;
  vertical-align: middle;
  text-align: right;
`

const DifferentialGradientContainer = styled.div`
  display: table-cell;
  width: 80%;
`

const DifferentialGradientSpan = styled.span`
  vertical-align: middle;
  height: 15px;
  width: 100%;
  display: block;
  background-image: linear-gradient(to right, ${props => props.lowValueColour}, ${props => props.highValueColour});
`

const LegendRow = ({lowValueColour, highValueColour, lowExpressionLevel, highExpressionLevel}) =>
  <div style={{display: `table-row`}}>
    <DifferentialLegendLevel>{lowExpressionLevel}</DifferentialLegendLevel>
    <DifferentialGradientContainer>
      <DifferentialGradientSpan lowValueColour={lowValueColour} highValueColour={highValueColour}/>
    </DifferentialGradientContainer>
    <DifferentialLegendLevel>{highExpressionLevel}</DifferentialLegendLevel>
  </div>


LegendRow.propTypes = {
  lowValueColour: PropTypes.string.isRequired,
  highValueColour: PropTypes.string.isRequired,
  lowExpressionLevel: PropTypes.element.isRequired,
  highExpressionLevel: PropTypes.element.isRequired
}

export default LegendRow
