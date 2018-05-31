import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

import ExperimentDesignTable from './experiment-design/ExperimentDesignTable'

const ExperimentDesignRoute = ({atlasUrl, downloadUrl, table}) =>
  <div className={`row expanded margin-top-large`}>
    <div className={`small-12 columns text-right`}>
      <a className={`button`} href={URI(downloadUrl, atlasUrl).toString()}>
        <span className={`icon icon-spacer icon-functional`} data-icon='='/> Download
      </a>
    </div>
    <div className={`small-12 columns`}>
      <ExperimentDesignTable data={table.data} headers={table.headers}/>
    </div>
  </div>

ExperimentDesignRoute.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  downloadUrl: PropTypes.string.isRequired,
  table: PropTypes.shape({
    headers: PropTypes.arrayOf(PropTypes.shape({
      name: PropTypes.string.isRequired,
      values: PropTypes.arrayOf(PropTypes.string.isRequired).isRequired
    }).isRequired).isRequired,
    data: PropTypes.arrayOf(PropTypes.shape({
      properties: PropTypes.oneOfType([
        PropTypes.shape({
          analysed: PropTypes.bool.isRequired
        }).isRequired
      ]),
      values: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.string.isRequired).isRequired).isRequired
    }).isRequired).isRequired
  }).isRequired
}


export default ExperimentDesignRoute