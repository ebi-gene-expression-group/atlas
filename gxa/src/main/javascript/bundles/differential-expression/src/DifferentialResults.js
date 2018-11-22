import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

import URI from 'urijs'

import EbiSpeciesIcon  from 'react-ebi-species'
import DisplayLevelsButton from './DisplayLevelsButton'
import DifferentialDownloadButton from './DifferentialDownloadButton'
import DifferentialFoldChangeCell from './DifferentialFoldChangeCell'
import Legend from './legend/LegendDifferential'

import ContrastTooltipLoader from './tooltip/ContrastTooltipLoader'

const Header = styled.th`
  text-align: center;
`

const Data = styled.td`
  text-align: center;
`

const differentialResultRowDataPropTypes = {
  species: PropTypes.string.isRequired,
  kingdom: PropTypes.string.isRequired,
  experimentType: PropTypes.string.isRequired,
  numReplicates: PropTypes.number,
  regulation: PropTypes.string.isRequired,
  factors: PropTypes.arrayOf(PropTypes.string).isRequired,
  bioentityIdentifier: PropTypes.string.isRequired,
  bioentityName: PropTypes.string.isRequired,
  experimentAccession: PropTypes.string.isRequired,
  experimentName: PropTypes.string.isRequired,
  contrastId: PropTypes.string.isRequired,
  comparison: PropTypes.string.isRequired,
  foldChange: PropTypes.number.isRequired,
  colour: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  uri: PropTypes.string.isRequired
}

const DifferentialResultsRow = (props) =>
  <tr>
    <DifferentialFoldChangeCell
      foldChange={props.foldChange}
      pValue={props.pValue}
      tStat={props.tStatistics}
      displayLevels={props.displayLevels}
      colour={props.colour}
      id={props.id}/>
    <Data style={{fontSize: `3rem`}}>
      <EbiSpeciesIcon species={props.species}/>
    </Data>
    <Data>
      <a href={URI(`genes/${props.bioentityIdentifier}`, props.atlasUrl).toString()}>
        {props.bioentityName || props.bioentityIdentifier}
      </a>
    </Data>
    <Data data-tip
        data-for={`${props.id}_contrast`}>
      <a href={URI(props.uri, props.atlasUrl)}>
        {props.comparison}
      </a>
      <ContrastTooltipLoader
        id={`${props.id}_contrast`}
        atlasUrl={props.atlasUrl}
        tooltipUrl={`rest/contrast-summary`}
        tooltipUrlParams={{
          experimentAccession: props.experimentAccession,
          contrastId: props.contrastId,
          accessKey: props.accessKey
        }} />
    </Data>
    <Data>
      {props.factors ? props.factors.toString().replace(/,/g, `, `) : ``}
    </Data>
    <Data>
      <a href={URI(`experiments/${props.experimentAccession}`, props.atlasUrl).toString()}>
        {props.experimentName}
      </a>
    </Data>
  </tr>

DifferentialResultsRow.propTypes = {
  ...differentialResultRowDataPropTypes,
  atlasUrl: PropTypes.string.isRequired
}


class DifferentialResults extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      displayLevels: false,
    }

    this._toggleDisplayLevels = this._toggleDisplayLevels.bind(this)
  }

  _toggleDisplayLevels() {
    const newDisplayLevels = !this.state.displayLevels
    this.setState({
      displayLevels: newDisplayLevels
    })
  }

  render() {
    return (
      <div className={`row column expanded`}>
        <div className={`row column expanded`}>
          <div className={`small-2 columns padding-left-none padding-right-none center`}>
            <Legend minDownLevel={this.props.minDownLevel}
                    maxDownLevel={this.props.maxDownLevel}
                    minUpLevel={this.props.minUpLevel}
                    maxUpLevel={this.props.maxUpLevel} />
          </div>

          <div className={`small-2 columns padding-left-none padding-right-none margin-left-large text-center`}>
            <DisplayLevelsButton onClick={this._toggleDisplayLevels}
                                 displayLevels={this.state.displayLevels} />
          </div>

          <div className={`small-2 columns padding-left-none padding-right-none margin-left-large text-right`}>
            <DifferentialDownloadButton results={this.props.results}
            />
          </div>
        </div>

        <div className={`row column expanded`}>
          <table className={`gxaDifferentialResultsTable`}>
            <thead>
            <tr>
              <Header style={{width: `10%`}}>Log<sub>2</sub>-fold change</Header>
              <Header style={{width: `5%`}}>Species</Header>
              <Header style={{width: `5%`}}>Gene name</Header>
              <Header style={{width: `30%`}}>Comparison</Header>
              <Header style={{width: `15%`}}>Experimental variables</Header>
              <Header style={{width: `35%`}}>Experiment name</Header>
            </tr>
            </thead>
            <tbody>
            { this.props.results.map(diffResult =>
              <DifferentialResultsRow key = {diffResult.id}
                                      displayLevels = {this.state.displayLevels}
                                      atlasUrl = {this.props.atlasUrl}
                                      {...diffResult} />) }
            </tbody>
          </table>
        </div>
      </div>
    )
  }
}

DifferentialResults.propTypes = {
  results: PropTypes.arrayOf(PropTypes.shape(
    differentialResultRowDataPropTypes
  )).isRequired,
  maxDownLevel: PropTypes.number,
  minDownLevel: PropTypes.number,
  minUpLevel: PropTypes.number,
  maxUpLevel: PropTypes.number,
  atlasUrl: PropTypes.string.isRequired
}

DifferentialResults.defaultProps = {
  maxDownLevel: Number.NEGATIVE_INFINITY,
  minDownLevel: 0,
  minUpLevel: 0,
  maxUpLevel: Number.POSITIVE_INFINITY,
}

export default DifferentialResults
