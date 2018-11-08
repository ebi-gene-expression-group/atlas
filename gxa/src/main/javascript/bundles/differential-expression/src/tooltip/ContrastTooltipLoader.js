import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-refetch'
import ReactTooltip from 'react-tooltip'

import URI from 'urijs'

import ContrastInfo from './ContrastInfo'

const tooltipStyles = {
  padding: `2px !important`,
  boxShadow: `0 0 1em darkgrey`,
  maxWidth: `500px`,
  fontSize: `x-small`,
  opacity: `0.98 !important`
}

const TooltipLoader = ({tooltipFetch, id}) => {
  if (tooltipFetch.pending) {
    return (
      <ReactTooltip id={id} type={`light`} className={`foobar`}>
        <span>Loading...</span>
      </ReactTooltip>
    )
  } else if (tooltipFetch.rejected) {
    return (
      <ReactTooltip id={id} type={`light`} className={`foobar`}>
        <span>Error retrieving tooltip data: {tooltipFetch.reason}</span>
      </ReactTooltip>
    )
  } else if (tooltipFetch.fulfilled) {
    return (
      <ReactTooltip id={id} type={`light`} className={`foobar`}>
        <ContrastInfo {...tooltipFetch.value}/>
      </ReactTooltip>
    )
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
