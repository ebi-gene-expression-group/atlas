import React from 'react'
import PropTypes from 'prop-types'

import SpeciesItem from './SpeciesItem'

const BrowseBySpecies = props => {
  const speciesItems = props.speciesInfoList.map(speciesInfo =>
    <SpeciesItem
      key={speciesInfo.species}
      atlasUrl={props.atlasUrl}
      {...speciesInfo}/>)

  return (
    <div className={`row small-up-2 medium-up-6`} style={{"max-width":"none"}}>
      {speciesItems}
    </div>
  )
}

BrowseBySpecies.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  speciesInfoList: PropTypes.arrayOf(PropTypes.shape({
    species: PropTypes.string.isRequired,
    totalExperiments: PropTypes.number.isRequired,
    baselineExperiments: PropTypes.number.isRequired,
    differentialExperiments: PropTypes.number.isRequired
  })).isRequired,
}

export default BrowseBySpecies
