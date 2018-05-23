import React from 'react'
import ReactDOM from 'react-dom'

import FetchLoader from 'react-faceted-search'
import ExperimentCard from './ExperimentCard'

const render = (options, mountNodeId) => {
  ReactDOM.render(
    <FetchLoader
      host={options.atlasUrl}
      resource={options.resource}
      ResultElementComponent={ExperimentCard}
      noResultsMessage={`The gene you searched for is not expressed in any experiments. Try searching for a different gene.`}
    />,
    document.getElementById(mountNodeId)
  )
}

export {render}