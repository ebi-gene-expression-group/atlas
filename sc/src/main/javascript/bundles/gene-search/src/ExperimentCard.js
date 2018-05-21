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

const ExperimentCard = ({experimentAccession, url, species, experimentDescription, type, longDescription}) => {
  return (
    <div className={"experiment-card"}>
      <a href={url}>
        <h5>
            <p>{experimentAccession}: {experimentDescription}</p>
        </h5>
        <p><i>Species:</i> {species}</p>
        {
          longDescription &&
          <p><i>Description:</i> {truncate(longDescription)}</p>
        }
      </a>

    </div>
  )
}

ExperimentCard.propTypes = {
  experimentAccession: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  experimentDescription: PropTypes.string.isRequired,
  longDescription: PropTypes.string,
  type: PropTypes.string.isRequired
}

export default ExperimentCard
