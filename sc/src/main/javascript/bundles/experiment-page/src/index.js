import React from 'react'
import ReactDOM from 'react-dom'

import ExperimentPageRouter from './ExperimentPageRouter'

const render = (options, mountNodeId) => {
  ReactDOM.render(
    <ExperimentPageRouter atlasUrl={options.atlasUrl}
                          resourcesUrl={options.resourcesUrl}
                          experimentAccession={options.content.experimentAccession}
                          accessKey={options.content.accessKey}
                          species={options.content.species}
                          tabs={options.content.tabs}
    />,
    document.getElementById(mountNodeId)
  )
}

export {ExperimentPageRouter as default, render}
