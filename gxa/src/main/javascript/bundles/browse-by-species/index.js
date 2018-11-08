import React from 'react'
import ReactDOM from 'react-dom'

import BrowseBySpecies from './src/BrowseBySpecies'

const render = (options, target) => {
  ReactDOM.render(
    <BrowseBySpecies {...options} />,
    document.getElementById(target)
  )
}

export { render }
