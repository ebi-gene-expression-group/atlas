import React from 'react'
import PropTypes from 'prop-types'

import styles from './link-to-resource.css'

const LinkToResource = ({data}) => {
  const links =  data.map((service, index) => (
    <div  key={index} className={styles.resource}>
      <span>
        <Icon type={service.type} />
        <a  href={service.url}>{service.description}</a>
      </span>
    </div>
  ))

  return (
    <div>
      {links}
    </div>
  )
}

const Icon = ({type}) => {
  const iconSrcMap = {
    'icon-ae': require('./icons/ae-logo-64.png'),
    'icon-gsea-reactome': require('./icons/gsea_reactome-icon.png'),
    'icon-gsea-interpro': require('./icons/gsea_interpro-icon.png'),
    'icon-gsea-go': require('./icons/gsea_go-icon.png'),
    'icon-ma': require('./icons/ma-plot-icon.png'),
    'icon-experiment-design': require('./icons/experiment_design_icon.png'),
    'icon-tsv': require('./icons/download_blue_small.png'),
    'icon-Rdata': require('./icons/r-button.png')
  }

  return (
    <img className={styles.icon} src={iconSrcMap[type]} />
  )
}


LinkToResource.propTypes = {
  data: PropTypes.arrayOf(PropTypes.shape({
    group: PropTypes.string,
    type: PropTypes.string,
    description: PropTypes.string,
    url: PropTypes.string.isRequired
  })).isRequired
}

export default LinkToResource

