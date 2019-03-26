import React from 'react'
import ReactDOM from 'react-dom'

import ResponsiveCardsRow, { ExtendableCard } from 'atlas-homepage-cards'
import withFetchLoader from 'atlas-react-fetch-loader'

const FetchLoadResponsiveCardsRow = withFetchLoader(ResponsiveCardsRow)

const render = (options, target) => {
  ReactDOM.render(
    <FetchLoadResponsiveCardsRow
      CardClass={ExtendableCard}
      {...options}
    />,
    document.getElementById(target))
}

export { render }
