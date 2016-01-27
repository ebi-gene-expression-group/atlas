"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var DownloadDifferentialButton = React.createClass({

    propTypes: {
        host: React.PropTypes.string.isRequired,
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
        })).isRequired
    },

    convertJSONtoTSV: function (results) {
        var headers = ["Gene", "Organism", "Experiment Accession", "Comparison", "log2foldchange"];
        var arrayResults = typeof results != 'object' ? JSON.parse(results) : results;

        var headerFields = headers.join('\t');
        var bodyFields = arrayResults.map(function(diffResults) {
            var line = "";

            line = line + diffResults.bioentityIdentifier + "\t" + diffResults.species + "\t" +
                    diffResults.experimentAccession + "\t" +
                    diffResults.comparison + "\t" + diffResults.foldChange + "\n";

            return line;
        });

        bodyFields.unshift(headerFields);
        var str = bodyFields.join('\n');
        return str;
    },

    _downloadDifferentialProfiles: function () {
        $(this.refs.downloadProfilesLink.getDOMNode()).click();
    },

    render: function () {
        var downloadImgSrcURL = "http://" + this.props.host + "/gxa/resources/images/download_blue_small.png";

        var tsvString = this.convertJSONtoTSV(this.props.results);
        var uri = "data:text/tsv;charset=utf-8," + escape(tsvString);
        var fileName = "differentialResults.tsv";

        return (
            <div style={{display: "inline-block", verticalAlign: "top", paddingLeft: "10px"}}>
                <a id="download-profiles-link" ref="downloadProfilesLink"
                   href={uri} download={fileName} className="gxaButtonImage" target="_blank"
                   onClick={this._downloadDifferentialProfiles.bind(this)}>
                    <img id="download-profiles" alt="Download query results" style={{width: "20px"}}
                         src={downloadImgSrcURL} />
                </a>
            </div>
        );
    },

    componentDidMount: function () {
        var $downloadProfilesLink = $(this.refs.downloadProfilesLink.getDOMNode());
        $downloadProfilesLink.tooltip();
        $downloadProfilesLink.button();
    }

});

//*------------------------------------------------------------------*

module.exports = DownloadDifferentialButton;