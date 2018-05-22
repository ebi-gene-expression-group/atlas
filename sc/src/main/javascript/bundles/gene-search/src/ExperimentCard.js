import React from 'react'
import PropTypes from 'prop-types'

import '!style-loader!css-loader!./ExperimentCard.css'

// Naive multi-line string clamping
const truncate = (string) => {
  if (string.length > 300) {
    return `${string.substring(0, 300)}...`
  }
  else {
    return string
  }
}

const ExperimentCard = ({experimentAccession, url, species, experimentDescription, type, longDescription, lastUpdated}) => {
  return (
    <div className={"experiment-card"}>
      <span className={"label"}>
          {lastUpdated} | <i>{species}</i>
        </span>
      <h5>
        <a href={url}>{experimentAccession}: {experimentDescription}</a>
      </h5>

      {
        longDescription &&
        <p><i>About the experiment:</i> {truncate(longDescription)}</p>
      }

    </div>
  )
}

ExperimentCard.propTypes = {
  experimentAccession: PropTypes.string.isRequired,
  lastUpdated: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  experimentDescription: PropTypes.string.isRequired,
  longDescription: PropTypes.string,
  type: PropTypes.string.isRequired
}

export default ExperimentCard
