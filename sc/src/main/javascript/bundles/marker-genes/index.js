import React from 'react'
import ReactDOM from 'react-dom'

import MarkerGenesSearchResults from 'atlas-marker-genes-search-results'

const render = function (options, target) {
  ReactDOM.render(<MarkerGenesSearchResults {...options} />, document.getElementById(target))
}

export {render}