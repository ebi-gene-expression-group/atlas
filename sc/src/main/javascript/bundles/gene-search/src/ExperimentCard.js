import React from 'react'
import PropTypes from 'prop-types'

import '!style-loader!css-loader!./ExperimentCard.css'

// Naive multi-line string clamping
const truncate = (string) => {
  if (isDescriptionTooLong(string)) {
    return `${string.substring(0, 300)}... `
  }
  else {
    return string
  }
}

const isDescriptionTooLong = (description) => {
  return description.length > 300
}

class ExperimentCard extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      expanded: false,
      descriptionShown: truncate(this.props.longDescription)
    }

    this._setExpanded = this._setExpanded.bind(this);
  }

  _setExpanded() {
    this.setState(previousState => ({
      descriptionShown: !previousState.expanded ? this.props.longDescription : truncate(this.props.longDescription),
      expanded: !previousState.expanded
    }))
  }

  render() {
    const {experimentAccession, url, species, experimentDescription, longDescription, lastUpdated} = this.props
    const {expanded, descriptionShown} = this.state

    return (
      <div className={"experiment-card"}>
      <span className={"label"}>
          {lastUpdated} | <i>{species}</i>
        </span>
        <h5>
          <a href={url}>{experimentAccession}: {experimentDescription}</a>
        </h5>

        {
          longDescription &&
          <p>
            <i>About the experiment: </i>
            {descriptionShown}
            {
              isDescriptionTooLong(longDescription) &&
              <button className={'read-more'}  onClick={this._setExpanded}>
                {!expanded ? `(Read more)`  : `(Read less)`}
              </button>
            }


          </p>
        }

      </div>
    )
  }
}

ExperimentCard.propTypes = {
  experimentAccession: PropTypes.string.isRequired,
  lastUpdated: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  experimentDescription: PropTypes.string.isRequired,
  longDescription: PropTypes.string,
  type: PropTypes.string.isRequired
}

export default ExperimentCard
