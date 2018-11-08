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
  font-size: 15px
`


const TitleDiv = styled.p`
  width: 40%;
  text-align: center;
  font-size: 15px
`

const VariableDiv = styled.div`
  width: 20%;
  text-align: center;
`

const CountDiv = styled.div`
  width: 10%;
  text-align: center;
  font-size: 20px;
`

class ExperimentTableCard extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        const {url, species, experimentDescription, markerGenes, specificExperimentInfo} = this.props

        const markerGeneLinks = markerGenes && markerGenes.map((markerGene) => {
            return <li><a href={markerGene.url}>See cluster {markerGene.clusterIds.sort().join(', ')} for k = {markerGene.k}</a></li>
        })

        return (
            <a href={url}>
                <CardContainerDiv>
                    <IconDiv>
                      <SpeciesIconCard iconSrc={species} description={species}/>
                    </IconDiv>
                    {   markerGenes ?
                        <MarkerDiv>
                            <ul>
                                {markerGeneLinks}
                            </ul>
                        </MarkerDiv> :
                        <MarkerDiv>
                            <span style={{fontSize: `30px`}} data-tooltip title="No expressed marker genes">&#10006;</span>
                        </MarkerDiv>
                    }

                    <TitleDiv> {experimentDescription} </TitleDiv>
                    <VariableDiv>
                        <ul>
                            {specificExperimentInfo.experimentalFactors.map(factor => <li> {factor} </li>)}
                        </ul>
                    </VariableDiv>
                    <CountDiv> {specificExperimentInfo.numberOfAssays} </CountDiv>
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
    specificExperimentInfo: PropTypes.shape({
        numberOfAssays: PropTypes.number.isRequired,
        experimentalFactors: PropTypes.array.isRequired
    })
}

export default ExperimentTableCard
