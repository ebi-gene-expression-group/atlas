"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*
require('../css/facets.css');

var DifferentialResults = React.createClass({

    propTypes: {
        /*
         eg: from http://www.ebi.ac.uk/gxa/query?geneQuery=zinc&_exactMatch=on&organism=Any&condition=
         [
         {
         "geneCount": 10,
         "organism": "Mus musculus",
         "contrastId": "g2_g4",
         "comparison": "'LDB1 knock-down with Ldb1 delta 4/5 construct' vs 'control shRNA",
         "experimentAccession": "E-GEOD-54549",
         "experimentName": "Role of LDB1 in the transition from chromatin looping to transcription activation"
         },
         {
         "geneCount": 2,
         "organism": "Homo sapiens",
         "contrastId": "g2_g5",
         "comparison": "'SAP130 knock-down' vs 'mock'",
         "experimentAccession": "E-GEOD-56788",
         "experimentName": "RNA-seq analysis of vorinostat-resistant HCT116 cells following gene knockdown of potential vorinostat-resistance candidate genes"
         }
         ]
         */
        diffResultsData: React.PropTypes.arrayOf(React.PropTypes.shape({
            geneCount: React.PropTypes.number.isRequired,
            organism: React.PropTypes.string.isRequired,
            contrastId: React.PropTypes.string.isRequired,
            comparison: React.PropTypes.string.isRequired,
            experimentAccession: React.PropTypes.string.isRequired,
            experimentName: React.PropTypes.string.isRequired
        })).isRequired
    },

    render: function () {
        var differentialResultRows = this.props.diffResultsData.map(function (diffResult) {
            return <DifferentialResultRow
                key={diffResult.experimentAccession + diffResult.contrastId}
                geneCount={diffResult.geneCount} organism={diffResult.organism} comparison={diffResult.comparison} experimentName={diffResult.experimentName}
                contrastId={diffResult.contrastId} experimentAccession={diffResult.experimentAccession}
            />;
        }.bind(this));

        return (
            <table className="table-striped">
                <thead>
                    <tr>
                        <th>Genes</th>
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
        );
    }
});


var DifferentialResultRow = React.createClass({
    propTypes: {
        geneCount: React.PropTypes.number.isRequired,
        organism: React.PropTypes.string.isRequired,
        comparison: React.PropTypes.string.isRequired,
        experimentName: React.PropTypes.string.isRequired,
        contrastId: React.PropTypes.string.isRequired,
        experimentAccession: React.PropTypes.string.isRequired
    },

    // TODO Use this.props.contrastId and this.props.experimentAccession to add link to the relevant experiment/comparison
    render: function () {
        var classColor="";

        if (this.props.organism === "homo sapiens" || this.props.organism === "gallus gallus" || this.props.organism === "gorilla gorilla" || this.props.organism === "macaca mulatta" || this.props.organism === "monodelphis domestica" || this.props.organism === "mus musculus" || this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" || this.props.organism === "rattus norvegicus") {
            classColor="red";
        } else if (this.props.organism == "arabidopsis thaliana" || this.props.organism === "hordeum vulgare subsp. vulgare" || this.props.organism === "oryza sativa japonica group" ) {
            classColor="green";
        } else if (this.props.organism === "anolis carolinensis" || this.props.organism === "drosophila melanogaster" || this.props.organism === "caenorhabditis elegans" || this.props.organism === "tetraodon nigroviridis" || this.props.organism === "xenopus (silurana) tropicalis") {
            classColor="blue";
        }
//        || this.props.organism === "gallus gallus" || this.props.organism === "gorilla gorilla" || this.props.organism === "macaca mulatta" || this.props.organism === "monodelphis domestica" ||  this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" || this.props.organism === "rattus norvegicus"
//        || this.props.organism === "hordeum vulgare subsp. vulgare" || this.props.organism === "oryza sativa japonica group"
//        || this.props.organism === "drosophila melanogaster" || this.props.organism === "caenorhabditis elegans" || this.props.organism === "tetraodon nigroviridis" || this.props.organism === "xenopus (silurana) tropicalis"
        var classIcon="";

        if (this.props.organism === "homo sapiens") {
            classIcon="H";
        } else if (this.props.organism == "mus musculus") {
            classIcon="M";
        } else if (this.props.organism === "anolis carolinensis") {
            classIcon="7";
        } else if (this.props.organism === "arabidopsis thaliana") {
            classIcon="B";
        } else if (this.props.organism === "bos taurus") {
            classIcon="C";
        } else if (this.props.organism === "caenorhabditis elegans") {
            classIcon="W";
        } else if (this.props.organism === "gallus gallus") {
            classIcon="k";
        } else if (this.props.organism === "gorilla gorilla") {
            classIcon="G";
        } else if (this.props.organism === "hordeum vulgare subsp. vulgare") {
            classIcon="5";
        } else if (this.props.organism === "macaca mulatta") {
            classIcon="r";
        } else if (this.props.organism === "monodelphis domestica") {
            classIcon="9";
        } else if (this.props.organism === "oryctolagus cuniculus") {
            classIcon="t";
        } else if (this.props.organism === "oryza sativa japonica group") {
            classIcon="6";
        } else if (this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" ) {
            classIcon="i";
        } else if (this.props.organism === "papio anubis") {
            classIcon="8";
        } else if (this.props.organism === "rattus norvegicus") {
            classIcon="R";
        } else if (this.props.organism === "tetraodon nigroviridis") {
            classIcon="E";
        } else if (this.props.organism === "zea mays") {
            classIcon="5";
        } else if (this.props.organism === "xenopus (silurana) tropicalis") {
            classIcon="f";
        } else if (this.props.organism === "drosophila melanogaster") {
            classIcon="F";
        } else {classIcon="";}

        return (
            <tr>
                <td className="col_count">{this.props.geneCount}</td>
                <td className="col_species"><span className={"icon icon-species " + classColor} data-icon={classIcon} style={{color: 'red'}} title={this.props.organism}></span></td>
                <td><a href="#">{this.props.comparison}</a></td>
                <td>organism part</td>
                <td>{this.props.experimentName}</td>
            </tr>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;