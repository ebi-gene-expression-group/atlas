import React from 'react'
import ReactDOM from 'react-dom'

import FetchLoader from 'scxa-gene-search-form'

const render = function (options, target) {
  ReactDOM.render(<FetchLoader {...options} />, document.getElementById(target))
}

export {render}
