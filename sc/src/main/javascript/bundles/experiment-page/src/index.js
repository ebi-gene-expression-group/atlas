import React from 'react'
import ReactDOM from 'react-dom'

import ExperimentPage from './experimentPage.js'

const render = function (options, mountNodeId) {
  ReactDOM.render(<ExperimentPage {...options} />, document.getElementById(mountNodeId))
}

export {ExperimentPage as default, render}
