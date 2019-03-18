import React from 'react'
import ReactDOM from 'react-dom'

import ExperimentsSummaryPanel from 'experiments-summary-panel'
import withFetchLoader from 'atlas-react-fetch-loader'

const FetchLoadExperimentsSummaryPanel = withFetchLoader(ExperimentsSummaryPanel)

const render = (options, target) => {
  ReactDOM.render(<FetchLoadExperimentsSummaryPanel {...options} />, document.getElementById(target))
}

export { render }
