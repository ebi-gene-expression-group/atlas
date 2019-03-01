import React from 'react'
import ReactDOM from 'react-dom'

import HomePagePanel from 'atlas-homepage-panels'
import {SceaHomepageSpeciesContainer} from 'atlas-homepage-cards'
const render = (options, target) => {
  ReactDOM.render(<HomePagePanel {...options} SpeciesCardType={SceaHomepageSpeciesContainer}/>, document.getElementById(target))
}

export {render}
