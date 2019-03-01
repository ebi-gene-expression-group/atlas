import React from 'react'
import PropTypes from 'prop-types'

const DisplayLevelsButton = ({displayLevels, onClick}) =>
  <a className={`button`} onClick={onClick}>
    {displayLevels ? <span>Hide log<sub>2</sub>-fold change</span> : <span>Display log<sub>2</sub>-fold change</span>}
  </a>

DisplayLevelsButton.propTypes = {
  displayLevels: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired
}

export default DisplayLevelsButton