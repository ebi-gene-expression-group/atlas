import React from 'react';
import ReactDOM from 'react-dom'

import DifferentialRouterLoader from './DifferentialRouterLoader'

const render = (props, target) => {
  ReactDOM.render(
    <DifferentialRouterLoader {...props} />, document.getElementById(target)
  )
}

export {render}
