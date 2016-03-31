"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var HighchartsHeatmap = require('./highchartsHeatmap.jsx');
var HighchartsUtils = require('./highcharts-utils.js');

//*------------------------------------------------------------------*

var ExperimentDescription = React.createClass({
    propTypes: {
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        experiment: React.PropTypes.shape({
            URL: React.PropTypes.string.isRequired,
            description: React.PropTypes.string.isRequired,
            allSpecies: React.PropTypes.string.isRequired
        }).isRequired
    },

    render: function () {

        var experimentURL = this.props.linksAtlasBaseURL + this.props.experiment.URL;

        return (
            <div style={{width: "100%", paddingBottom: "20px"}}>
                <div id="experimentDescription">
                    <a id="goto-experiment" className="gxaThickLink" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                </div>
                <div id="experimentOrganisms">Organism(s): <span style={{"fontStyle":"italic"}}>{this.props.experiment.allSpecies}</span></div>
            </div>
        );
    }

});

var HighchartsHeatmapContainer = React.createClass({
    propTypes: {
        sourceURL: React.PropTypes.string.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        isWidget: React.PropTypes.bool.isRequired,
        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
        fail: React.PropTypes.func
    },

    render: function () {

        var geneURL =
            this.props.linksAtlasBaseURL + "/query" +
            "?geneQuery=" + this.state.heatmapConfig.geneQuery +
            "&exactMatch=" + this.state.heatmapConfig.isExactMatch +
            "&organism=" + this.state.heatmapConfig.species;

         return (
            <div ref="this">

                { this.state.experimentData ?
                    <ExperimentDescription experiment={this.state.experimentData} linksAtlasBaseURL={this.props.linksAtlasBaseURL}/>
                    : null
                }

                { this.state.heatmapConfig ?
                    <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                        <div id="heatmap-react" className="gxaInnerHeatmap">
                            <HighchartsHeatmap
                                     profiles={this.state.profiles}
                                     atlasBaseURL={this.props.atlasBaseURL}
                                     xAxisCategories={this.state.xAxisCategories}
                                     yAxisCategories={this.state.yAxisCategories}
                                     yAxisCategoriesLinks={this.state.yAxisCategoriesLinks}
                                     seriesDataNA={this.state.seriesDataNA}
                                     seriesDataNAString={this.state.seriesDataNAString}
                                     seriesDataBelowCutoff={this.state.seriesDataBelowCutoff}
                                     seriesDataBelowCutoffString={this.state.seriesDataBelowCutoffString}
                                     seriesDataRanges={this.state.seriesDataRanges}
                            />
                        </div>
                    </div>
                    :
                    <div ref="loadingImagePlaceholder">
                        <img src={this.props.atlasBaseURL + "/resources/images/loading.gif"}/>
                    </div>
                }

                { this.props.isWidget ?
                    <div><p><a href={geneURL}>See more expression data at Expression Atlas.</a>
                        <br/>This expression view is provided by <a href={this.props.linksAtlasBaseURL}>Expression Atlas</a>.
                        <br/>Please direct any queries or feedback to <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a></p>
                    </div>
                    :
                    null
                }

            </div>
        );
    },

    getInitialState: function() {
        return {
            heatmapConfig: '',
            profiles: {
                rows: [],
                minExpressionLevel: 0,
                maxExpressionLevel: 0
            },

            xAxisCategories: {},
            yAxisCategories: {},
            yAxisCategoriesLinks: {},

            seriesDataNA: [],
            seriesDataNAString: "NA",
            seriesDataBelowCutoff: [],
            seriesDataBelowCutoffString: "Below cutoff",

            seriesDataRanges: []
        }
    },

    componentDidMount: function() {
        var httpRequest = {
            url: this.props.sourceURL,
            dataType: "json",
            method: "GET"
        };

        $.ajax(httpRequest).done(
            function (data) {
                if (this.isMounted()) {

                    var xAxisCategories = HighchartsUtils.getXAxisCategories(data.columnHeaders);
                    var yAxisCategories = HighchartsUtils.getYAxisCategories(data.profiles, data.config);
                    var yAxisCategoriesLinks = HighchartsUtils.getYAxisCategoriesLinks();

                    var seriesDataNA = [],
                        seriesDataNAString = "NA";

                    var seriesDataBelowCutoff = [],
                        seriesDataBelowCutoffString = "Below cutoff";

                    var seriesDataRanges = [{
                        label: "Low",
                        from: 0,
                        to: 10,
                        seriesData: []
                    }, {
                        label: "Medium",
                        from: 10,
                        to: 1000,
                        seriesData: []
                    }, {
                        label: "High",
                        from: 1000,
                        to: 100000,
                        seriesData: []
                    }];

                    var experimentTypes = [];

                    for (var i = 0; i < data.profiles.rows.length; i++) {
                        if (experimentTypes.indexOf(data.profiles.rows[i].experimentType) === -1) {
                            experimentTypes.push(data.profiles.rows[i].experimentType);
                        }
                    }

                    for (var i = 0; i < experimentTypes.length; i++) {
                        var experimentTypeSeriesData = [];

                        for (var j = 0; j < yAxisCategories.length; j++) {
                            if (data.profiles.rows[j].experimentType !== experimentTypes[i]) {
                                continue;
                            }

                            for (var k = 0; k < xAxisCategories.length; k++) {
                                var literalValue = data.profiles.rows[j].expressions[k].value;

                                if (literalValue === "") {
                                    seriesDataBelowCutoff.push([k, j, seriesDataBelowCutoffString])
                                } else if (literalValue === "NT") {
                                    seriesDataNA.push([k, j, seriesDataNAString])
                                } else {
                                    var value = parseFloat(literalValue);
                                    if (!isNaN(value)) {
                                        experimentTypeSeriesData.push([k, j, value]);
                                    }
                                }
                            }
                        }

                        experimentTypeSeriesData.sort(function(a, b) {
                            return a[2] - b[2];
                        });

                        var experimentTypeMax = experimentTypeSeriesData[experimentTypeSeriesData.length - 1][2];


                        for (var k = 0; k < seriesDataRanges.length; k++) {
                            //seriesDataRanges[k].seriesData.concat(
                            experimentTypeSeriesData.filter(
                                function(datum) {
                                    return datum[2] > seriesDataRanges[k].from * experimentTypeMax && datum[2] <= seriesDataRanges[k].to * experimentTypeMax;
                                }).forEach(
                                function(filteredDatum) {
                                    seriesDataRanges[k].seriesData.push(filteredDatum);
                                });
                        }
                    }


                    this.setState({
                        heatmapConfig: data.config,
                        columnHeaders: data.columnHeaders,
                        nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
                        profiles: data.profiles,
                        geneSetProfiles: data.geneSetProfiles,
                        experimentData: data.experiment,

                        xAxisCategories: xAxisCategories,
                        yAxisCategories: yAxisCategories,
                        yAxisCategoriesLinks: yAxisCategoriesLinks,

                        seriesDataBelowCutoff: seriesDataBelowCutoff,
                        seriesDataBelowCutoffString: seriesDataBelowCutoffString,
                        seriesDataNA: seriesDataNA,
                        seriesDataNAString: seriesDataNAString,
                        seriesDataRanges: seriesDataRanges

                    });
                }
            }.bind(this)
        ).fail(
            function (jqXHR, textStatus, errorThrown) {
                if (this.props.fail) {
                    this.props.fail(jqXHR, textStatus, errorThrown);
                } else if (textStatus === "parsererror") {
                    $(this.refs.this.getDOMNode()).html("<div class='gxaError'>Could not parse JSON response</div>");
                } else {
                    $(this.refs.this.getDOMNode()).html(jqXHR.responseText);
                }
            }.bind(this)
        );

        if (!this.props.disableGoogleAnalytics) {
            var _gaq = _gaq || [];
            _gaq.push(["_setAccount", "UA-37676851-1"]);
            _gaq.push(["_trackPageview"]);
            (function () {
                var ga = document.createElement("script");
                ga.type = "text/javascript";
                ga.async = true;
                ga.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(ga, s);
            })();
        }
    },

    //componentDidUpdate: function() {
    //    // This mounted component is only going to be updated when changing this.props.showAnatomogram, so we only take
    //    // care of the anatomogram, the legend and the sticky header (the last two through an event)
    //    var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
    //
    //    if (this.props.showAnatomogram) {
    //        $anatomogram.hcSticky({responsive: true});
    //    }
    //
    //    $(window).trigger("gxaResizeHeatmapAnatomogramHeader");
    //}
});

//*------------------------------------------------------------------*

module.exports = HighchartsHeatmapContainer;
