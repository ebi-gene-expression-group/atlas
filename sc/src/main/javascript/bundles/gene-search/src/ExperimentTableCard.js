import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'
import SpeciesIconCard from './SpeciesIconCard'

const CardContainerDiv = styled.div`
  height: 100%;
  width: 100%;
  display: flex !important;
  flex-wrap: nowrap;
  align-items: center;
  border: #e6e6e6 solid 1px;
  margin-bottom: 0.5rem;
  padding: 1rem;
  &:hover {background-color: #eaeaea;}
`

const IconDiv = styled.span`
  width: 15%;
  text-align: center;
`

const MarkerDiv = styled.div`
  width: 15%;
  text-align: center;
`

const TitleDiv = styled.p`
  width: 40%;
  text-align: center;
  margin-bottom: 0;
`

const VariableDiv = styled.div`
  width: 20%;
  text-align: center;
`

const CountDiv = styled.div`
  width: 10%;
  text-align: center;
`

class ExperimentTableCard extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    const {url, species, experimentDescription, markerGenes, numberOfAssays, factors} = this.props

    const markerGeneLinks = markerGenes && markerGenes.map((markerGene) => {
      return <li key={`marker-gene-${markerGene.k}`}><a href={markerGene.url}>See cluster {markerGene.clusterIds.sort().join(`, `)} for k = {markerGene.k}</a></li>
    })

    return (
      <a href={url}>
        <CardContainerDiv>
          <IconDiv>
            <SpeciesIconCard iconSrc={species} description={species}/>
          </IconDiv>
          {   markerGenes ?
            <MarkerDiv>
              <ul style={{marginBottom: 0}}>
                {markerGeneLinks}
              </ul>
            </MarkerDiv> :
            <MarkerDiv>
              <span data-tooltip title="Not a marker gene" className="icon icon-functional" data-icon="x"></span>
            </MarkerDiv>
          }

          <TitleDiv> {experimentDescription} </TitleDiv>
          <VariableDiv>
            <ul style={{marginBottom: 0}}>
              {factors.map(factor => <li key={`factor-${factor}`}> {factor} </li>)}
            </ul>
          </VariableDiv>
          <CountDiv> {numberOfAssays} </CountDiv>
        </CardContainerDiv>
      </a>

    )
  }
}

ExperimentTableCard.propTypes = {
  url: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  experimentDescription: PropTypes.string.isRequired,
  markerGenes: PropTypes.arrayOf(PropTypes.shape({
    k: PropTypes.number.isRequired,
    clusterIds: PropTypes.array.isRequired,
    url: PropTypes.string.isRequired
  })),
  numberOfAssays: PropTypes.number.isRequired,
  factors: PropTypes.array.isRequired
}

export default ExperimentTableCard
