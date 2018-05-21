import React from 'react'
import PropTypes from 'prop-types'

const ExperimentCard = ({experimentAccession, species, experimentDescription, type}) => {
  return (
    <div style={{ border: `2px solid grey`, marginBottom: `0.5rem`, borderRadius: `4px`, padding: `0.25rem` }}>
      <a href={`http://localhost:8080/scxa/experiments/${experimentAccession}`}>
        <p>{experimentAccession}: {experimentDescription}</p>
      </a>

      <p>Species : {species}</p>
    </div>
  )
}

ExperimentCard.propTypes = {
  experimentAccession: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  experimentDescription: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired
}

export default ExperimentCard
