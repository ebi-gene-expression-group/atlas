import React from 'react'
import ReactDOM from 'react-dom'

import HomePagePanel from 'atlas-homepage-panels'
import {SceaHomepageSpeciesContainer} from 'atlas-homepage-cards'
import {SceaHomePageLatestExperimentContainer} from 'atlas-latest-experiments-cards'
const render = (options, target) => {
  ReactDOM.render(<HomePagePanel {...options} SpeciesCardType={SceaHomepageSpeciesContainer}
                                 LatestExperimentCardType={SceaHomePageLatestExperimentContainer} />, document.getElementById(target))
}

export {render}
