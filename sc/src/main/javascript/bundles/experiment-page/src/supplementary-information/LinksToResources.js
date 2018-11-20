import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

const LinksToResources = ({data, atlasUrl}) => {
  const links =  data.map((service, index) =>
    <div  key={index} style={{paddingBottom: `10px`}}>
      <span>
        <Icon type={service.type} />
        {
          service.isDownload && atlasUrl ?
            <a href={URI(service.url, atlasUrl)}>{service.description}</a> :
            <a href={service.url}>{service.description}</a>
        }
      </span>
    </div>
  )

  return (
    <div>
      {links}
    </div>
  )
}

const Icon = ({type}) => {
  const iconSrcMap = {
    'icon-ae': require(`./icons/ae-logo-64.png`),
    'icon-gsea-reactome': require(`./icons/gsea_reactome-icon.png`),
    'icon-gsea-interpro': require(`./icons/gsea_interpro-icon.png`),
    'icon-gsea-go': require(`./icons/gsea_go-icon.png`),
    'icon-ma': require(`./icons/ma-plot-icon.png`),
    'icon-experiment-design': require(`./icons/experiment_design_icon.png`),
    'icon-tsv': require(`./icons/download_blue_small.png`),
    'icon-Rdata': require(`./icons/r-button.png`)
  }

  return (
    <img style={{paddingRight: `5px`}} src={iconSrcMap[type]} />
  )
}

Icon.propTypes = {
  type: PropTypes.string.isRequired
}

LinksToResources.propTypes = {
  data: PropTypes.arrayOf(PropTypes.shape({
    group: PropTypes.string,
    type: PropTypes.string,
    description: PropTypes.string,
    url: PropTypes.string.isRequired,
    isDownload: PropTypes.bool
  })).isRequired,
  atlasUrl: PropTypes.string
}

export default LinksToResources

