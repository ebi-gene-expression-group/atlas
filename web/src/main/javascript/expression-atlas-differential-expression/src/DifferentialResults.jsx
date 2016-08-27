const $ = require('jquery');
require('jquery.browser');

const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

const DisplayLevelsButton = require('display-levels-button');
const Legend = require('legend').LegendDifferential;
const CellDifferential = require('cell-differential');
const DownloadProfilesButton = require('download-profiles-button');
const DifferentialDownloadButton = require('./DifferentialDownloadButton.jsx');
const ContrastTooltips = require('contrast-tooltips');
const AtlasFeedback = require('atlas-feedback');
const EbiSpeciesIcon = require('react-ebi-species').Icon;


//*------------------------------------------------------------------*

require('./DifferentialResults.css');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const OptionalString = React.PropTypes.string;
const RequiredBool = React.PropTypes.bool.isRequired;

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
        results: React.PropTypes.arrayOf(React.PropTypes.shape({
            species: RequiredString,
            kingdom: RequiredString,
            experimentType: RequiredString,
            numReplicates: RequiredString,    // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
            regulation: RequiredString,
            factors: React.PropTypes.arrayOf(OptionalString).isRequired,
            bioentityIdentifier: RequiredString,
            experimentAccession: RequiredString,
            experimentName: RequiredString,
            contrastId: RequiredString,
            comparison: RequiredString,
            foldChange: RequiredString,    // a string, a formatted value, to be able to work with Infinity values and rounding
            colour: RequiredString,
            id: RequiredString
        })).isRequired,
        maxDownLevel: RequiredString,
        minDownLevel: RequiredString,
        minUpLevel: RequiredString,
        maxUpLevel: RequiredString,
        hostUrl: RequiredString
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
        let differentialResultRows = this.props.results.map(diffResult => {
            return <DifferentialResultRow
                        key = {diffResult.id}
                        displayLevels = {this.state.displayLevels}
                        atlasBaseUrl = {this.props.hostUrl + '/gxa'}
                        {...diffResult} />;
        });

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

                <div style={{display: 'inline-block', verticalAlign: 'middle'}}>
                    <Legend
                        atlasBaseURL={this.props.hostUrl + '/gxa'} minDownLevel={this.props.minDownLevel} maxDownLevel={this.props.maxDownLevel} minUpLevel={this.props.minUpLevel} maxUpLevel={this.props.maxUpLevel}
                    />
                </div>
                <div style={{display: 'inline-block', paddingLeft: '10px', verticalAlign: 'top'}}>
                    <DifferentialDownloadButton ref="downloadProfilesButton"
                                                hostUrl={this.props.hostUrl}
                                                results={this.props.results} />
                </div>

                <DownloadProfilesButton ref="downloadProfilesButton"
                                        {...this.props.downloadOptions}
                                        onDownloadCallbackForAnalytics={
                                            function() {
                                                this.props.googleAnalyticsCallback('send', 'event', 'DifferentialExpression', 'downloadData')
                                            }.bind(this)}/>


                <table className="table-striped gxaDifferentialFacetedSearchResults">
                    <thead>
                        <tr>
                            <th style={{width: '10%'}}>Log<sub>2</sub>-fold change</th>
                            <th style={{width: '5%'}}>Species</th>
                            <th style={{width: '30%'}}>Comparison</th>
                            <th style={{width: '15%'}}>Experimental variables</th>
                            <th style={{width: '40%'}}>Experiment name</th>
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
    propTypes: {
        bioentityIdentifier: RequiredString,
        foldChange: RequiredString,
        colour: RequiredString,
        species: RequiredString,
        comparison: RequiredString,
        factors: React.PropTypes.arrayOf(OptionalString).isRequired,
        experimentName: RequiredString,
        contrastId: RequiredString,
        experimentAccession: RequiredString,
        displayLevels: RequiredBool,
        atlasBaseUrl: RequiredString
    },

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

        return (
            <tr>
                <CellDifferential
                    colour={this.props.colour}
                    infinity={this.props.infinity}
                    foldChange={this.props.foldChange}
                    displayLevels={this.props.displayLevels}/>
                <td className="col_species">
                    <EbiSpeciesIcon species={this.props.species}/>
                </td>
                <td ref="comparison">
                    <a href={this._linkToComparisonPage()}>
                        {this.props.comparison}
                    </a>
                </td>
                <td className="gxaExperimentalVariable">
                    {factors}
                </td>
                <td>
                    <a href={'experiments/' + this.props.experimentAccession}>
                        {this.props.experimentName}
                    </a>
                </td>
            </tr>
        );
    },

    componentDidMount () {
        ContrastTooltips(this.props.atlasBaseUrl, '', ReactDOM.findDOMNode(this.refs.comparison), this.props.experimentAccession, this.props.contrastId);
        $(document).ready(() => {
          this.setState(
              {googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : () => {}}
            );
        });
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;
