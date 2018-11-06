import React from 'react'
import ReactDOM from 'react-dom'

import FetchLoader from 'react-faceted-search'
import ExperimentTableCard from './ExperimentTableCard'

const render = (options, mountNodeId) => {
  ReactDOM.render(
    <FetchLoader
      host={options.atlasUrl}
      resource={options.resource}
      ResultElementClass={ExperimentTableCard}
      noResultsMessage={options.noResultsMessage}
      resultsMessage={options.resultsMessage}
    />,
    document.getElementById(mountNodeId)
  )
}

export {render}