const $ = require('jquery');
require('jquery.browser');

const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const AtlasFeedback = require('expression-atlas-feedback');
const EbiSpeciesIcon = require('react-ebi-species').Icon;

import URI from 'urijs'

//*------------------------------------------------------------------*

const DisplayLevelsButton = require('./DisplayLevelsButton.jsx');
const DifferentialDownloadButton = require('./DifferentialDownloadButton.jsx');
const Legend = require('./legend/LegendDifferential.jsx');
const CellDifferential = require('./cell-differential/CellDifferential.jsx');

const ContrastTooltips = require('./contrast-tooltip/contrastTooltipModule.js');

require('./DifferentialResults.css');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const OptionalString = React.PropTypes.string;
const DoubleWithDefault = React.PropTypes.number;

const ResultType = {
    species: RequiredString,
    kingdom: RequiredString,
    experimentType: RequiredString,
    numReplicates: RequiredString,    // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
    regulation: RequiredString,
    factors: React.PropTypes.arrayOf(OptionalString).isRequired,
    bioentityIdentifier: RequiredString,
    bioentityName: RequiredString,
    experimentAccession: RequiredString,
    experimentName: RequiredString,
    contrastId: RequiredString,
    comparison: RequiredString,
    foldChange: React.PropTypes.number.isRequired,
    colour: RequiredString,
    id: RequiredString,
    uri: RequiredString
}

const DifferentialResults = React.createClass({
    /*
    results: [
     {
       "bioentityIdentifier":"ENSMUSG00000072476",
       "species":"mus musculus",
       "kingdom":"animals",
       "experimentAccession":"E-MTAB-698",
       "experimentType":"rnaseq_mrna_differential",
       "contrastId":"g1_g2",
       "numReplicates":"3",
       "foldChange":"2.4",
       "regulation":"DOWN"
       "colour": some_hex_value
     },
     {
       "bioentityIdentifier":"ENSMUSG00000071341",
       "species":"mus musculus",
       "kingdom":"animals",
       "experimentAccession":"E-MTAB-698",
       "experimentType":"rnaseq_mrna_differential",
       "contrastId":"g1_g2",
       "numReplicates":"3",
       "foldChange":"-∞",
       "regulation":"DOWN",
       "colour": some_hex_value

     }
    ],
    maxDownLevel: "-∞" ,
    minDownLevel: "0",
    minUpLevel: "0",
    maxUpLevel: "2.4"
    */
    propTypes: {
        results: React.PropTypes.arrayOf(React.PropTypes.shape(ResultType)).isRequired,
        maxDownLevel: DoubleWithDefault,
        minDownLevel: DoubleWithDefault,
        minUpLevel: DoubleWithDefault,
        maxUpLevel: DoubleWithDefault,
        atlasUrl: RequiredString
    },

    getDefaultProps () {
      return {
        maxDownLevel: Number.NEGATIVE_INFINITY,
        minDownLevel: 0,
        minUpLevel: 0,
        maxUpLevel: Number.POSITIVE_INFINITY,
      }
    },

    getInitialState () {
        return {
            displayLevels: false,
            googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : () => {}
        };
    },

    _toggleDisplayLevels () {
        let newDisplayLevels = !this.state.displayLevels;
        this.setState({displayLevels: newDisplayLevels});
    },

    render () {
        let differentialResultRows = this.props.results.map(diffResult =>
              <DifferentialResultRow
                key = {diffResult.id}
                displayLevels = {this.state.displayLevels}
                atlasUrl = {this.props.atlasUrl}
                {...diffResult} />
        );

        let feedbackSmileys = $.browser.msie ? null
            :
            <div style = {{marginTop:'50px'}}>
                <AtlasFeedback
                    collectionCallback = { (score, comment) => {
                        this.state.googleAnalyticsCallback('send','event','DifferentialHeatmaps', 'feedback', comment, score);
                    }}/>
            </div>;

        return (
            <div>
                <div style={{display: 'inline-block', verticalAlign: 'middle'}}>
                    <DisplayLevelsButton hideText='Hide log<sub>2</sub>-fold change' showText='Display log<sub>2</sub>-fold change' onClickCallback={this._toggleDisplayLevels} displayLevels={this.state.displayLevels} fontSize='14px' width='200px'/>
                </div>

                <div style={{display: 'inline-block', verticalAlign: 'middle'}} className="margin-left-large">
                    <Legend
                        atlasBaseURL={this.props.atlasUrl} minDownLevel={this.props.minDownLevel} maxDownLevel={this.props.maxDownLevel} minUpLevel={this.props.minUpLevel} maxUpLevel={this.props.maxUpLevel}
                    />
                </div>

                <div style={{display: 'inline-block'}} className="margin-left-large">
                    <DifferentialDownloadButton ref="downloadProfilesButton"
                                                results={this.props.results}
                    />
                 </div>

                <table className="table-striped gxaDifferentialFacetedSearchResults">
                    <thead>
                        <tr>
                            <th style={{width: '10%'}}>Log<sub>2</sub>-fold change</th>
                            <th style={{width: '5%'}}>Species</th>
                            <th style={{width: '5%'}}>Gene name</th>
                            <th style={{width: '30%'}}>Comparison</th>
                            <th style={{width: '15%'}}>Experimental variables</th>
                            <th style={{width: '35%'}}>Experiment name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {differentialResultRows}
                    </tbody>
                </table>
                {feedbackSmileys}
            </div>
        );
    }
});


const DifferentialResultRow = React.createClass({
    propTypes: Object.assign({}, ResultType, {
      atlasUrl: RequiredString
    }),

    _linkToComparisonPage () {
      return (
        'experiments/' + this.props.experimentAccession
        + '?geneQuery=' + this.props.bioentityIdentifier
        + '&queryFactorValues=' + this.props.contrastId
        + '&specific=false'
      );
    },

    render () {
        let factors = this.props.factors ? this.props.factors.toString().replace(/,/g, ', ') : '';
        const uriBase = URI(this.props.atlasUrl).path()
        return (
            <tr>
                <CellDifferential
                    colour={this.props.colour}
                    infinity={this.props.infinity}
                    foldChange={this.props.foldChange}
                    pValue={this.props.pValue}
                    tStat={this.props.tStatistics}
                    displayLevels={this.props.displayLevels}/>
                <td className="col_species">
                    <EbiSpeciesIcon species={this.props.species}/>
                </td>
                <td>
                    <a href={URI(`genes/${this.props.bioentityIdentifier}`, uriBase).toString()}>
                    {
                      this.props.bioentityName || this.props.bioentityIdentifier
                    }
                    </a>
                </td>
                <td ref="comparison">
                    <a href={URI(this.props.uri, uriBase).toString()}>
                        {this.props.comparison}
                    </a>
                </td>
                <td className="gxaExperimentalVariable">
                    {factors}
                </td>
                <td>
                    <a href={URI(`experiments/${this.props.experimentAccession}`, uriBase).toString()}>
                        {this.props.experimentName}
                    </a>
                </td>
            </tr>
        );
    },

    componentDidMount () {
        ContrastTooltips(this.props.atlasUrl, '', ReactDOM.findDOMNode(this.refs.comparison), this.props.experimentAccession, this.props.contrastId);
        $(document).ready(() => {
          this.setState(
              {googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : () => {}}
            );
        });
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;
