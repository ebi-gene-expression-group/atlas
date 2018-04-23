import React from 'react'
import PropTypes from 'prop-types'
import LinksToResources from './supplementary-information/LinksToResources'

const DownloadsRoute = (props) => {
  const downloadLinks = props.data.map((download) => [
    <h3 key="title">{download.title}</h3>,
    <LinksToResources key={"links"} data={download.files} atlasUrl={props.atlasUrl}/>
    ]
  )

  return (
    <div className={"margin-top-large"}>
      {downloadLinks}
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
    title: PropTypes.string,
    files: PropTypes.arrayOf(PropTypes.shape({
      description: PropTypes.string,
      type: PropTypes.string,
      url: PropTypes.string
    }))
  }))
}

export default DownloadsRoute