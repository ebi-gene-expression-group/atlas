import React from 'react'
import PropTypes from 'prop-types'

import ScientificNotationNumber from 'expression-atlas-number-format'

import './DifferentialResultsTooltip.css'

const DifferentialFoldChangeCellInfo = ({pValue, tStat, foldChange}) =>
  <table>
    <thead>
      <tr>
        {pValue && <th>Adjusted <em>p</em>-value</th>}
        {tStat && <th><em>t</em>-statistic</th>}
        <th>Log<sub>2</sub>-fold change</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        {pValue && <td><ScientificNotationNumber value={pValue}/></td>}
        {tStat && <td>{Math.floor(tStat * 1e4) / 1e4}</td>}
        <td>{foldChange}</td>
      </tr>
    </tbody>
  </table>

DifferentialFoldChangeCellInfo.propTypes = {
  foldChange: PropTypes.number.isRequired,
  pValue: PropTypes.number,
  tStat: PropTypes.number
}

export default DifferentialFoldChangeCellInfo
