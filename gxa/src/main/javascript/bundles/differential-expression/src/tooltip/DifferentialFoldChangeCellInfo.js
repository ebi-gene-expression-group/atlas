import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

import ScientificNotationNumber from 'expression-atlas-number-format'

const BorderLessTable = styled.table`
  border-collapse: collapse;
  margin: 0;
`

const Header = styled.th`
  border-bottom: 1px solid lightgrey !important;
  background-color: floralwhite;
`

const Data = styled.td`
  border: 1px solid lightgrey;
`

const DifferentialFoldChangeCellInfo = ({pValue, tStat, foldChange}) =>
  <BorderLessTable>
    <thead>
      <tr>
        {pValue && <Header>Adjusted <em>p</em>-value</Header>}
        {tStat && <Header><em>t</em>-statistic</Header>}
        <Header>Log<sub>2</sub>-fold change</Header>
      </tr>
    </thead>
    <tbody>
      <tr>
        {pValue && <Data><ScientificNotationNumber value={pValue}/></Data>}
        {tStat && <Data>{Math.floor(tStat * 1e4) / 1e4}</Data>}
        <Data>{foldChange}</Data>
      </tr>
    </tbody>
  </BorderLessTable>

DifferentialFoldChangeCellInfo.propTypes = {
  foldChange: PropTypes.number.isRequired,
  pValue: PropTypes.number,
  tStat: PropTypes.number
}

export default DifferentialFoldChangeCellInfo


