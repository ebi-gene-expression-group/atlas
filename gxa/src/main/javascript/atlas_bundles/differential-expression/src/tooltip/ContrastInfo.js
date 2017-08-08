import React from 'react'
import PropTypes from 'prop-types'

const ContrastInfoPropertyRow = ({testValue, referenceValue, contrastPropertyType, propertyName}) => {
  if (!testValue && !referenceValue) {
    return null
  }

  const style = {
    whiteSpace: `normal`,
    fontWeight: contrastPropertyType === `FACTOR` ? `bold` : ``,
    color: contrastPropertyType === `FACTOR` ? `` : `grey`
  }

  return (
    <tr key={`${contrastPropertyType}_${propertyName}`}>
      <td style={style}>{propertyName}</td>
      <td style={style}>{testValue}</td>
      <td style={style}>{referenceValue}</td>
    </tr>
  )
}

ContrastInfoPropertyRow.propTypes = {
  contrastPropertyType: React.PropTypes.string,
  propertyName: React.PropTypes.string.isRequired,
  referenceValue: React.PropTypes.string.isRequired,
  testValue: React.PropTypes.string.isRequired
}

const ContrastInfo = ({experimentDescription, contrastDescription, testReplicates, referenceReplicates, properties}) =>
  <div>
    <div style={{fontWeight: `bold`, color: `blue`, textAlign: `center`}}>{experimentDescription}</div>
    <div style={{textAlign: `center`}}>{contrastDescription}</div>
    <table>
      <thead>
      <tr>
        <th>Property</th>
        <th>Test value (N={testReplicates})</th>
        <th>Reference value (N={referenceReplicates})</th>
      </tr>
      </thead>
      <tbody>
        {properties.map((property) => <ContrastInfoPropertyRow key={property.propertyName} {...property} />)}
      </tbody>
    </table>
  </div>

ContrastInfo.proptypes = {
  experimentDescription: PropTypes.string,
  contrastDescription: PropTypes.string,
  testReplicates: PropTypes.number,
  referenceReplicates: PropTypes.number,
  properties: PropTypes.arrayOf(React.PropTypes.shape(
    ContrastInfoPropertyRow.propTypes
  ))
}

export default ContrastInfo
