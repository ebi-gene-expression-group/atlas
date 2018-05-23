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
      noResultsMessage={options.noResultsMessage}
      resultsMessage={options.resultsMessage}
    />,
    document.getElementById(mountNodeId)
  )
}

export {render}