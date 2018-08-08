import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

const ReadMoreButton = styled.button`
  cursor: pointer;
  color: #222;
  border-bottom-width: 1px;
  border-bottom-style: dotted;
  border-bottom-color: inherit;
`

// border: #777 solid 1px; Darker grey border
const ExperimentCardDiv = styled.div`
  border: #e6e6e6 solid 1px;
  margin-bottom: 0.5rem;
  padding: 1rem;

  &:hover {
    background-color: #eaeaea;
  }
`

// Naive multi-line string clamping
const truncate = (string) => {
  if (isDescriptionTooLong(string)) {
    return `${string.substring(0, 500)}... `
  }
  else {
    return string
  }
}

const isDescriptionTooLong = (description) => {
  return description.length > 500
}

class ExperimentCard extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      expanded: false,
      descriptionShown: truncate(this.props.longDescription)
    }

    this.setExpanded = this._setExpanded.bind(this);
  }

  _setExpanded() {
    this.setState(previousState => ({
      descriptionShown: !previousState.expanded ? this.props.longDescription : truncate(this.props.longDescription),
      expanded: !previousState.expanded
    }))
  }

  render() {
    const {experimentAccession, url, species, experimentDescription, longDescription, lastUpdated, markerGenes} = this.props
    const {expanded, descriptionShown} = this.state

    const markerGeneLinks = markerGenes && markerGenes.map((markerGene) => {
      return <li><a href={markerGene.url}>View marker gene in clusters {markerGene.clusterIds.sort().join(', ')} for k = {markerGene.k}</a></li>
    })
    return (
      <ExperimentCardDiv>
      <span className={"label"}>
          {lastUpdated} | <i>{species}</i>
        </span>
        <h5>
          <a href={url}>{experimentAccession}: {experimentDescription}</a>
        </h5>

        {
          markerGenes &&
          <div>
            <ul>
              {markerGeneLinks}
            </ul>
          </div>
        }

        {
          longDescription &&
          <p style={{marginBottom: 0}}>
            <i>About the experiment: </i>
            {descriptionShown}
            {
              isDescriptionTooLong(longDescription) &&
              <ReadMoreButton onClick={this.setExpanded}>
                {!expanded ? `(Read more)`  : `(Read less)`}
              </ReadMoreButton>
            }
          </p>
        }

      </ExperimentCardDiv>
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
  type: PropTypes.string.isRequired,
  markerGenes: PropTypes.arrayOf(PropTypes.shape({
    k: PropTypes.number.isRequired,
    clusterIds: PropTypes.array.isRequired,
    url: PropTypes.string.isRequired
  }))
}

export default ExperimentCard
