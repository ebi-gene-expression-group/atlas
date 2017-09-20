import React from 'react'
import ReactDOM from 'react-dom'

import ExperimentPageRouter from './ExperimentPageRouter'

const render = (options, mountNodeId) => {
  ReactDOM.render(<ExperimentPageRouter {...options} />, document.getElementById(mountNodeId))
}

export {ExperimentPageRouter as default, render}
