import React from 'react'
import ReactDOM from 'react-dom'

import { SceaHomepageSpeciesContainer, HcaLandingPageContainer } from 'atlas-homepage-cards'

const renderSceaHomepageSpeciesContainer = (options, target) => {
  ReactDOM.render(<SceaHomepageSpeciesContainer {...options} />, document.getElementById(target))
}

const renderHcaLandingPageContainer = (options, target) => {
  ReactDOM.render(<HcaLandingPageContainer {...options} />, document.getElementById(target))
}

export { renderSceaHomepageSpeciesContainer, renderHcaLandingPageContainer }
