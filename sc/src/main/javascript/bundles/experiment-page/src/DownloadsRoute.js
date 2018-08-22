import React from 'react'
import PropTypes from 'prop-types'
import LinksToResources from './supplementary-information/LinksToResources'

const DownloadsRoute = ({atlasUrl, data}) => {
  const downloadLinks = data.map((download) =>
    <div key={`download`} className={`small-12 columns margin-bottom-xlarge`}>
      <h3 key={`title`}>{download.title}</h3>
      <LinksToResources key={`links`} data={download.files} atlasUrl={atlasUrl}/>
    </div>
  )

  return (
    <div className={`row expanded margin-top-large`}>
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