import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-refetch'
import ReactTooltip from 'react-tooltip'

import URI from 'urijs'

import ContrastInfo from './ContrastInfo'
import './DifferentialResultsTooltip.css'

class TooltipLoader extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    const { tooltipFetch, id } = this.props

    if (tooltipFetch.pending) {
      return <ReactTooltip id={id} type={`light`} className={`gxaDifferentialResultsTooltip`}>
               <span>Loading...</span>
             </ReactTooltip>
    } else if (tooltipFetch.rejected) {
      return <ReactTooltip id={id} type={`light`} className={`gxaDifferentialResultsTooltip`}>
               <span>Error retrieving tooltip data: {tooltipFetch.reason}</span>
             </ReactTooltip>
    } else if (tooltipFetch.fulfilled) {
      return <ReactTooltip id={id} type={`light`} className={`gxaDifferentialResultsTooltip`}>
               <ContrastInfo {...tooltipFetch.value}/>
             </ReactTooltip>
    }
  }
}

TooltipLoader.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  tooltipUrl: PropTypes.string.isRequired,
  tooltipUrlParams: PropTypes.objectOf(PropTypes.string).isRequired,
  id: PropTypes.string.isRequired
}

export default connect((props) => ({
  tooltipFetch: URI(props.tooltipUrl, props.atlasUrl).search(props.tooltipUrlParams).toString()
}))(TooltipLoader)
