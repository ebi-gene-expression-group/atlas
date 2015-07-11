"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

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
            <table>
                <thead>
                    <tr>
                        <th>Gene count</th>
                        <th>Species</th>
                        <th>Comparison</th>
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
        return (
            <tr>
                <td>{this.props.geneCount}</td>
                <td>{this.props.organism}</td>
                <td>{this.props.comparison}</td>
                <td>{this.props.experimentName}</td>
            </tr>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialResults;