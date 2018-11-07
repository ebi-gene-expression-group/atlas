import React from 'react'
import PropTypes from 'prop-types'
import ReactTooltip from 'react-tooltip'

import './DifferentialFoldChangeCell.css'
import DifferentialFoldChangeCellInfo from './tooltip/DifferentialFoldChangeCellInfo'


const DifferentialCell = ({colour, foldChange, pValue, tStat, displayLevels, id}) => {
  const tooltipId = `${id}_foldchange`
  const tdStyle = {
    border: `4px solid ${colour}`,
    background: displayLevels ? `none` : colour,
  }
  return (
    <td data-tip data-for={tooltipId} className={`gxaDifferentialCell`} style={tdStyle}>
      <div className={displayLevels ? `` : `hidden`}>
        {foldChange}
      </div>
      <ReactTooltip id={tooltipId} type={`light`} className={`gxaDifferentialResultsTooltip`}>
        <DifferentialFoldChangeCellInfo pValue={pValue}
                                        tStatistic={tStat}
                                        foldChange={foldChange} />
      </ReactTooltip>
    </td>
  )
}

DifferentialCell.propTypes = {
  ...DifferentialFoldChangeCellInfo.propTypes,
  colour: PropTypes.string,
  displayLevels: PropTypes.bool.isRequired,
  id: PropTypes.string.isRequired
}

export default DifferentialCell
