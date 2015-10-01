"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var Legend = require('legend');
var CellDifferential = require('cell-differential');

//*------------------------------------------------------------------*

require('../css/differential-results.css');

//*------------------------------------------------------------------*

var DifferentialResults = React.createClass({
    /*
    [
     {
       "bioentity_identifier":"ENSMUSG00000072476",
       "species":"mus musculus",
       "kingdom":"animals",
       "experimentAccession":"E-MTAB-698",
       "experimentType":"rnaseq_mrna_differential",
       "contrastId":"g1_g2",
       "numReplicates":"3",
       "foldChange":"-Infinity",
       "regulation":"DOWN"
       "colour": some_hex_value
     },
     {
       "bioentity_identifier":"ENSMUSG00000071341",
       "species":"mus musculus",
       "kingdom":"animals",
       "experimentAccession":"E-MTAB-698",
       "experimentType":"rnaseq_mrna_differential",
       "contrastId":"g1_g2",
       "numReplicates":"3",
       "foldChange":"-Infinity",
       "regulation":"DOWN",
       "colour": some_hex_value

     }
    ]
    */
    propTypes: {
        results: React.PropTypes.arrayOf(React.PropTypes.shape({
            bioentity_identifier: React.PropTypes.string.isRequired,
            species: React.PropTypes.string.isRequired,
            kingdom: React.PropTypes.string.isRequired,
            experimentAccession: React.PropTypes.string.isRequired,
            experimentName: React.PropTypes.string.isRequired,
            experimentType: React.PropTypes.string.isRequired,
            contrastId: React.PropTypes.string.isRequired,
            comparison: React.PropTypes.string.isRequired,
            numReplicates: React.PropTypes.string.isRequired,  // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
            foldChange: React.PropTypes.number.isRequired,
            colour: React.PropTypes.string.isRequired,
            regulation: React.PropTypes.string.isRequired
        })).isRequired,
        maxDownLevel: React.PropTypes.number.isRequired,
        minDownLevel: React.PropTypes.number.isRequired,
        minUpLevel: React.PropTypes.number.isRequired,
        maxUpLevel: React.PropTypes.number.isRequired
    },

    render: function () {
        var differentialResultRows = this.props.results.map(function (diffResult) {
            return <DifferentialResultRow
                key={diffResult.experimentAccession + diffResult.contrastId + diffResult.foldChange}
                colour={diffResult.colour} foldChange={diffResult.foldChange} species={diffResult.species} comparison={diffResult.comparison} experimentName={diffResult.experimentName}
                contrastId={diffResult.contrastId} experimentAccession={diffResult.experimentAccession}
            />;
        }.bind(this));

        return (
            <div>
                <Legend.LegendDifferential
                    proxyPrefix={""} contextRoot={"/gxa"}
                    minDownLevel={this.props.minDownLevel} maxDownLevel={this.props.maxDownLevel} minUpLevel={this.props.minUpLevel} maxUpLevel={this.props.maxUpLevel}
                    displayLevels={true}/>

                <table className="table-striped atlasDifferentialFacetedSearchResults">
                    <thead>
                        <tr>
                            <th>Log<sub>2</sub>-fold change</th>
                            <th>Species</th>
                            <th>Comparison</th>
                            <th>Experimental variables</th>
                            <th>Experiment name</th>
                        </tr>
                    </thead>
                    <tbody>
                            {differentialResultRows}
                    </tbody>
                </table>
            </div>
        );
    }
});


var DifferentialResultRow = React.createClass({
    propTypes: {
        foldChange: React.PropTypes.number.isRequired,
        colour: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        comparison: React.PropTypes.string.isRequired,
        experimentName: React.PropTypes.string.isRequired,
        contrastId: React.PropTypes.string.isRequired,
        experimentAccession: React.PropTypes.string.isRequired
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
        } else if (this.props.species === "drosophila melanogaster") {
            classIcon="F";
        } else {classIcon="";}

        return (
            <tr>
                <CellDifferential colour={this.props.colour} foldChange={this.props.foldChange} displayLevels={true}/>
                <td className="col_species"><span className={"icon icon-species " + classColor} data-icon={classIcon} style={{color: 'red'}} title={this.props.species}></span></td>
                <td><a href="#">{this.props.comparison}</a></td>
                <td>organism part</td>
                <td>{this.props.experimentName}</td>
            </tr>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;