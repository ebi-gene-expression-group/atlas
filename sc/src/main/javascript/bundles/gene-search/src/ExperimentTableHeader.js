import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'
import EbiSpeciesIcon from 'react-ebi-species'

const CardContainerDiv = styled.div`
  height: 100%;
  width: 100%;
  display: flex !important;
  flex-wrap: nowrap;
  align-items: center;
`

const IconDiv = styled.div`
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
`

const VariableDiv = styled.div`
  width: 20%;
  text-align: center;
`

const CountDiv = styled.div`
  width: 10%;
  text-align: center;
`

const ExperimentTableHeader = () =>
  <CardContainerDiv>
    <IconDiv>Species</IconDiv>
    <MarkerDiv>Marker genes</MarkerDiv>
    <TitleDiv>Experiment</TitleDiv>
    <VariableDiv>Experimental variables</VariableDiv>
    <CountDiv>Number of assays</CountDiv>
  </CardContainerDiv>

export default ExperimentTableHeader
