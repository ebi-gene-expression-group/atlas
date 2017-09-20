import React from 'react'
import PropTypes from 'prop-types'
import {BrowserRouter, Route} from 'react-router-dom'

import ExperimentPageView from './ExperimentPageView'

const ExperimentPageRouter = (props) =>
  <BrowserRouter>
    <div>
      <Route path={`/`}
             render={(componentProps) => (
               <ExperimentPageView {...componentProps}
                                   atlasUrl={props.atlasUrl}
                                   resourcesUrl={props.resourcesUrl}
                                   suggesterEndpoint={props.suggesterEndpoint}
                                   experimentAccession={props.experimentAccession}
                                   availableClusters={props.availableClusters} /> )} />
    </div>
  </BrowserRouter>

ExperimentPageRouter.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  availableClusters: PropTypes.array.isRequired,
  suggesterEndpoint: PropTypes.string
}

export default ExperimentPageRouter
