import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'
import ReactTooltip from 'react-tooltip'

import DifferentialFoldChangeCellInfo from './tooltip/DifferentialFoldChangeCellInfo'

const DifferentialCellTd = styled.td`
  background-color: white;
  white-space: nowrap;
  font-size: x-large;
  border: ${props => props.displayLevels ? `3px solid` : `none`} ${props => props.colour};
  background: ${props => props.displayLevels ? `none` : props.colour}
`

const DifferentialCell = ({colour, foldChange, pValue, tStat, displayLevels, id}) => {
  const tooltipId = `${id}_foldchange`

  return (
    <DifferentialCellTd colour={colour} displayLevels={displayLevels} data-tip data-for={tooltipId}>
      <div className={displayLevels ? `` : `hidden`} style={{textAlign: `center`}}>
        {foldChange}
      </div>
      <ReactTooltip id={tooltipId} type={`light`} className={`foobar`}>
        <DifferentialFoldChangeCellInfo
          pValue={pValue}
          tStatistic={tStat}
          foldChange={foldChange} />
      </ReactTooltip>
    </DifferentialCellTd>
  )
}

DifferentialCell.propTypes = {
  ...DifferentialFoldChangeCellInfo.propTypes,
  colour: PropTypes.string,
  displayLevels: PropTypes.bool.isRequired,
  id: PropTypes.string.isRequired
}

export default DifferentialCell

