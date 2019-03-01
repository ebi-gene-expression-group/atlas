import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'
import URI from 'urijs'
import EbiSpeciesIcon  from 'react-ebi-species'

const SpeciesItemContainer = styled.div`
  :hover {
    background-color: #eaeaea;
  }
`

const ClearLink = styled.a`
  border-bottom: none;
  
  :hover {
    border-bottom: none;
  }
`

const SpeciesItem = props => {
  const allExperimentsUrl = URI(props.atlasUrl).segment(`experiments`).addSearch({ species: props.species });
  const differentialExperimentsUrl = URI(allExperimentsUrl).addSearch({ experimentType: `differential` });
  const baselineExperimentsUrl = URI(allExperimentsUrl).addSearch({ experimentType: `baseline` });

  const speciesFirstCapitalLetter = props.species[0].toUpperCase() + props.species.substr(1);

  return (
    <div className={`column column-block text-center`}>
      <SpeciesItemContainer>
        <ClearLink href={allExperimentsUrl}>
          <span className={`large-species-icon`}>
            <EbiSpeciesIcon species={props.species}/>
          </span>

          <h5 className={`species`}>{speciesFirstCapitalLetter}</h5>
        </ClearLink>

        <p className={`experiments`}>{props.totalExperiments} experiments<br/>

          <ClearLink href={baselineExperimentsUrl} className={`baseline`}>
            <span
              data-tooltip
              style={{cursor: `unset`, fontWeight: `bold`}}
              className={`baseline tiny button-rd`} title={`Baseline experiments`}>Baseline</span>
            {props.baselineExperiments}
          </ClearLink>
          <ClearLink
            href={differentialExperimentsUrl}
            className={`differential padding-left-medium`}>
              <span
                data-tooltip
                style={{cursor: `unset`, fontWeight: `bold`}}
                className={`differential tiny button-rd`}
                title={`Differential experiments`}>Differential</span>
            {props.differentialExperiments}
          </ClearLink>
        </p>
      </SpeciesItemContainer>
    </div>
  )
}

SpeciesItem.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  totalExperiments: PropTypes.number.isRequired,
  baselineExperiments: PropTypes.number.isRequired,
  differentialExperiments: PropTypes.number.isRequired,
}

export default SpeciesItem
