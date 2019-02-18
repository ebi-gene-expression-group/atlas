import React from 'react'
import ReactDOM from 'react-dom'

import { SceaHomepageSpeciesContainer, EaHomepageSpeciesContainer, HcaLandingPageContainer } from 'atlas-homepage-cards'

const renderSceaHomepageSpeciesContainer = (options, target) => {
  ReactDOM.render(<SceaHomepageSpeciesContainer {...options} />, document.getElementById(target))
}

const renderEaHomepageSpeciesContainer = (options, target) => {
  ReactDOM.render(<EaHomepageSpeciesContainer {...options} />, document.getElementById(target))
}

const renderHcaLandingPageContainer = (options, target) => {
  ReactDOM.render(<HcaLandingPageContainer {...options} />, document.getElementById(target))
}


export { renderSceaHomepageSpeciesContainer, renderHcaLandingPageContainer, renderEaHomepageSpeciesContainer}
