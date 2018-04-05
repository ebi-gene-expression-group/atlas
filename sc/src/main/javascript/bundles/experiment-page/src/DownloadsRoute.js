import React from 'react'
import PropTypes from 'prop-types'
import LinksToResources from './supplementary-information/LinksToResources'

const DownloadsRoute = (props) => {
  return (
    <div className={"margin-top-large"}>
      <LinksToResources data={props.data} atlasUrl={props.atlasUrl}/>
    </div>
  )
}

DownloadsRoute.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  data: PropTypes.arrayOf(PropTypes.shape({
    description: PropTypes.string,
    type: PropTypes.string,
    url: PropTypes.object
  }))
}

export default DownloadsRoute