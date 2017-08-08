import React from 'react'
import PropTypes from 'prop-types'

import URI from 'urijs'

import AtlasFeedback from 'expression-atlas-feedback'
import EbiSpeciesIcon  from 'react-ebi-species'
import DisplayLevelsButton from './DisplayLevelsButton'
import DifferentialDownloadButton from './DifferentialDownloadButton'
import DifferentialFoldChangeCell from './DifferentialFoldChangeCell'
import Legend from './legend/LegendDifferential'

import ContrastTooltipLoader from './tooltip/ContrastTooltipLoader'

import './DifferentialResults.css'

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
    <DifferentialFoldChangeCell foldChange={props.foldChange}
                                pValue={props.pValue}
                                tStat={props.tStatistics}
                                displayLevels={props.displayLevels}
                                colour={props.colour}
                                id={props.id}/>
    <td>
      <EbiSpeciesIcon species={props.species}/>
    </td>
    <td>
      <a href={URI(`genes/${props.bioentityIdentifier}`, props.atlasUrl).toString()}>
        {props.bioentityName || props.bioentityIdentifier}
      </a>
    </td>
    <td data-tip
        data-for={`${props.id}_contrast`}>
      <a href={URI(props.uri, props.atlasUrl)}>
        {props.comparison}
      </a>
      <ContrastTooltipLoader id={`${props.id}_contrast`}
                             atlasUrl={props.atlasUrl}
                             tooltipUrl={`rest/contrast-summary`}
                             tooltipUrlParams={{
                               experimentAccession: props.experimentAccession,
                               contrastId: props.contrastId,
                               accessKey: props.accessKey
                             }} />
    </td>
    <td className={`gxaExperimentalVariable`}>
      {props.factors ? props.factors.toString().replace(/,/g, `, `) : ``}
    </td>
    <td>
      <a href={URI(`experiments/${props.experimentAccession}`, props.atlasUrl).toString()}>
        {props.experimentName}
      </a>
    </td>
  </tr>

DifferentialResultsRow.propTypes = {
  ...differentialResultRowDataPropTypes,
  atlasUrl: React.PropTypes.string.isRequired
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
              <th style={{width: `10%`}}>Log<sub>2</sub>-fold change</th>
              <th style={{width: `5%`}}>Species</th>
              <th style={{width: `5%`}}>Gene name</th>
              <th style={{width: `30%`}}>Comparison</th>
              <th style={{width: `15%`}}>Experimental variables</th>
              <th style={{width: `35%`}}>Experiment name</th>
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
          <div className={`margin-top-medium`}>
            <AtlasFeedback
              collectionCallback = {
                typeof window.ga === `function` ?
                  (score, comment) => { window.ga('send','event','DifferentialHeatmaps', 'feedback', comment, score) } :
                  () => {}
              } />
          </div>
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
