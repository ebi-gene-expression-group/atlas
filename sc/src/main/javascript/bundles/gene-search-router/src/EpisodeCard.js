import React from 'react'
import PropTypes from 'prop-types'


// using CommonJS modules

const EpisodeCard = ({longDescription}) =>
  <div style={{border: `2px solid grey`, marginBottom: `0.5rem`, borderRadius: `4px`, padding: `0.25rem`}}>
    {longDescription}
  </div>

EpisodeCard.propTypes = {
  longDescription: PropTypes.string.isRequired
}

export default EpisodeCard
