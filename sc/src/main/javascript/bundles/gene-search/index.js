import React from 'react'
import ReactDOM from 'react-dom'

import GeneSearchRouter from './src/GeneSearchRouter'

const render = (options, mountNodeId) => {
  ReactDOM.render(<GeneSearchRouter {...options} />,  document.getElementById(mountNodeId))
}

export { render }
