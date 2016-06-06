"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var DisplayLevelsButton = require('display-levels-button');
var Legend = require('legend').LegendDifferential;
var CellDifferential = require('cell-differential');
var DifferentialDownloadButton = require('./DifferentialDownloadButton.jsx');
var FeedbackSmileys = require('atlas-feedback');

//*------------------------------------------------------------------*

var ContrastTooltips = require('contrast-tooltips');

//*------------------------------------------------------------------*

require('./DifferentialResults.css');

//*------------------------------------------------------------------*

var DifferentialResults = React.createClass({
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
            species: React.PropTypes.string.isRequired,
            kingdom: React.PropTypes.string.isRequired,
            experimentType: React.PropTypes.string.isRequired,
            numReplicates: React.PropTypes.string.isRequired,  // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
            regulation: React.PropTypes.string.isRequired,
            factors: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,

            bioentityIdentifier: React.PropTypes.string.isRequired,
            experimentAccession: React.PropTypes.string.isRequired,
            experimentName: React.PropTypes.string.isRequired,
            contrastId: React.PropTypes.string.isRequired,
            comparison: React.PropTypes.string.isRequired,
            foldChange: React.PropTypes.string.isRequired,     // a string, a formatted value, to be able to work with Infinity values and rounding
            colour: React.PropTypes.string.isRequired,
            id: React.PropTypes.string.isRequired
        })).isRequired,
        maxDownLevel: React.PropTypes.string.isRequired,
        minDownLevel: React.PropTypes.string.isRequired,
        minUpLevel: React.PropTypes.string.isRequired,
        maxUpLevel: React.PropTypes.string.isRequired,
        host: React.PropTypes.string.isRequired
    },

    getInitialState: function () {
        return {
            displayLevels: false,
            googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function (){}
        };
    },

    _toggleDisplayLevels: function () {
        var newDisplayLevels = !this.state.displayLevels;
        this.setState({displayLevels: newDisplayLevels});
    },

    render: function () {
        var differentialResultRows = this.props.results.map(function (diffResult) {
            return <DifferentialResultRow
                key={diffResult.id}
                bioentityIdentifier={diffResult.bioentityIdentifier} colour={diffResult.colour} foldChange={diffResult.foldChange} species={diffResult.species} comparison={diffResult.comparison} experimentName={diffResult.experimentName}
                factors={diffResult.factors} contrastId={diffResult.contrastId} experimentAccession={diffResult.experimentAccession} displayLevels={this.state.displayLevels} atlasBaseURL={this.props.host + "/gxa"}
            />;
        }.bind(this));

        return (
            <div>
                <div style={{display: "inline-block", verticalAlign: "middle"}}>
                    <DisplayLevelsButton hideText="Hide log<sub>2</sub>-fold change" showText="Display log<sub>2</sub>-fold change" onClickCallback={this._toggleDisplayLevels} displayLevels={this.state.displayLevels} fontSize="14px" width="200px"/>
                </div>

                <div style={{display: "inline-block", verticalAlign: "middle"}}>
                    <Legend
                        atlasBaseURL={window.location.protocol + "//" + this.props.host + "/gxa"} minDownLevel={this.props.minDownLevel} maxDownLevel={this.props.maxDownLevel} minUpLevel={this.props.minUpLevel} maxUpLevel={this.props.maxUpLevel}
                    />
                </div>
                <div style={{display: "inline-block", paddingLeft: "10px", verticalAlign: "top"}}>
                    <DifferentialDownloadButton ref="downloadProfilesButton"
                                                host={this.props.host}
                                                results={this.props.results} />
                </div>

                <table className="table-striped gxaDifferentialFacetedSearchResults">
                    <thead>
                        <tr>
                            <th style={{width: "10%"}}>Log<sub>2</sub>-fold change</th>
                            <th style={{width: "5%"}}>Species</th>
                            <th style={{width: "30%"}}>Comparison</th>
                            <th style={{width: "15%"}}>Experimental variables</th>
                            <th style={{width: "40%"}}>Experiment name</th>
                        </tr>
                    </thead>
                    <tbody>
                            {differentialResultRows}
                    </tbody>
                </table>
                <div style={{"margin-top":"50px"}}>
                <FeedbackSmileys collectionCallback= {
                  function(score,comment){
                    this.state.googleAnalyticsCallback(
                      'send','event','DifferentialHeatmaps', 'feedback',
                      comment,score);
                  }.bind(this)} />
                  </div>
            </div>
        );
    }
});


var DifferentialResultRow = React.createClass({
    propTypes: {
        bioentityIdentifier: React.PropTypes.string.isRequired,
        foldChange: React.PropTypes.string.isRequired,
        colour: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        comparison: React.PropTypes.string.isRequired,
        factors: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        experimentName: React.PropTypes.string.isRequired,
        contrastId: React.PropTypes.string.isRequired,
        experimentAccession: React.PropTypes.string.isRequired,
        displayLevels: React.PropTypes.bool.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired
    },

    // TODO Use this.props.contrastId and this.props.experimentAccession to add link to the relevant experiment/comparison
    render: function () {
        var classColor="";

        if (this.props.species === "homo sapiens" || this.props.species === "gallus gallus" || this.props.species === "gorilla gorilla" || this.props.species === "macaca mulatta" || this.props.species === "monodelphis domestica" || this.props.species === "mus musculus" || this.props.species === "pan paniscus" || this.props.species === "pan troglodytes" || this.props.species === "rattus norvegicus") {
            classColor="red";
        } else if (this.props.species == "arabidopsis thaliana" || this.props.species === "hordeum vulgare subsp. vulgare" || this.props.species === "oryza sativa japonica group" ) {
            classColor="green";
        } else if (this.props.species === "anolis carolinensis" || this.props.species === "drosophila melanogaster" || this.props.species === "caenorhabditis elegans" || this.props.species === "tetraodon nigroviridis" || this.props.species === "xenopus (silurana) tropicalis") {
            classColor="blue";
        }
//        || this.props.species === "gallus gallus" || this.props.species === "gorilla gorilla" || this.props.species === "macaca mulatta" || this.props.species === "monodelphis domestica" ||  this.props.species === "pan paniscus" || this.props.species === "pan troglodytes" || this.props.species === "rattus norvegicus"
//        || this.props.species === "hordeum vulgare subsp. vulgare" || this.props.species === "oryza sativa japonica group"
//        || this.props.species === "drosophila melanogaster" || this.props.species === "caenorhabditis elegans" || this.props.species === "tetraodon nigroviridis" || this.props.species === "xenopus (silurana) tropicalis"
        var classIcon="";

        if (this.props.species === "homo sapiens") {
            classIcon="H";
        } else if (this.props.species == "mus musculus") {
            classIcon="M";
        } else if (this.props.species === "anolis carolinensis") {
            classIcon="7";
        } else if (this.props.species === "arabidopsis thaliana") {
            classIcon="B";
        } else if (this.props.species === "bos taurus") {
            classIcon="C";
        } else if (this.props.species === "caenorhabditis elegans") {
            classIcon="W";
        } else if (this.props.species === "gallus gallus") {
            classIcon="k";
        } else if (this.props.species === "gorilla gorilla") {
            classIcon="G";
        } else if (this.props.species === "hordeum vulgare subsp. vulgare") {
            classIcon="5";
        } else if (this.props.species === "macaca mulatta") {
            classIcon="r";
        } else if (this.props.species === "monodelphis domestica") {
            classIcon="9";
        } else if (this.props.species === "oryctolagus cuniculus") {
            classIcon="t";
        } else if (this.props.species === "oryza sativa japonica group") {
            classIcon="6";
        } else if (this.props.species === "pan paniscus" || this.props.species === "pan troglodytes" ) {
            classIcon="i";
        } else if (this.props.species === "papio anubis") {
            classIcon="8";
        } else if (this.props.species === "rattus norvegicus") {
            classIcon="R";
        } else if (this.props.species === "tetraodon nigroviridis") {
            classIcon="E";
        } else if (this.props.species === "zea mays") {
            classIcon="5";
        } else if (this.props.species === "xenopus (silurana) tropicalis") {
            classIcon="f";
        } else if (this.props.species === "xenopus tropicalis") {
            classIcon="f";
        } else if (this.props.species === "drosophila melanogaster") {
            classIcon="F";
        } else {classIcon="";}

        var factors = this.props.factors ? this.props.factors.toString().replace(/,/g, ", ") : "";

        return (
            <tr>
                <CellDifferential colour={this.props.colour} infinity={this.props.infinity} foldChange={this.props.foldChange} displayLevels={this.props.displayLevels}/>
                <td className="col_species"><span className={"icon icon-species " + classColor} data-icon={classIcon} style={{color: 'red'}} title={this.props.species}/></td>
                <td ref="comparison"><a href={"experiments/" + this.props.experimentAccession + "?geneQuery=" + this.props.bioentityIdentifier + "&queryFactorValues=" + this.props.contrastId + "&specific=false"}>{this.props.comparison}</a></td>
                <td className="gxaExperimentalVariable">{factors}</td>
                <td><a href={"experiments/" + this.props.experimentAccession}>{this.props.experimentName}</a></td>
            </tr>
        );
    },

    componentDidMount: function () {
        ContrastTooltips(window.location.protocol + "//" + this.props.atlasBaseURL, "", ReactDOM.findDOMNode(this.refs.comparison), this.props.experimentAccession, this.props.contrastId);
        $(document).ready(function () {
          this.setState(
              {googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function (){}}
            );
          }.bind(this)
        )
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;
