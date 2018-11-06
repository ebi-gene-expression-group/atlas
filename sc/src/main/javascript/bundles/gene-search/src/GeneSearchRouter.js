import React from 'react'
import PropTypes from 'prop-types'
import { BrowserRouter, Route } from 'react-router-dom'

import GeneSearch from './GeneSearch'

const GeneSearchRouter = ({atlasUrl, basename}) =>
  <BrowserRouter basename={basename}>
    <Route
      path={`/search`}
      render={
        ({match, location, history}) =>
          <GeneSearch
            atlasUrl={atlasUrl}
            history={history}/>
      } />
  </BrowserRouter>

GeneSearchRouter.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  basename: PropTypes.string.isRequired
}

export default GeneSearchRouter
