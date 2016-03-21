"use strict";

//*------------------------------------------------------------------*

function getXAxisCategories (columnHeaders) {
    return columnHeaders.map(function (columnHeader) {
        return columnHeader.factorValue;
    });
}

var yAxisCategoriesLinks = {};

function getYAxisCategories (profiles, heatmapConfig) {

    return profiles.rows.map(function (profile) {
        yAxisCategoriesLinks[profile.name] = profile.id + "?geneQuery=" + heatmapConfig.geneQuery +
            "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors);
        return profile.name;
    });
}

function getYAxisCategoriesLinks () {
    return yAxisCategoriesLinks;
}

function getExperimentTypes (profiles) {
    var experimentTypes = [];

    for (var i = 0; i < profiles.rows.length; i++) {
        if (experimentTypes.indexOf(profiles.rows[i].experimentType) === -1) {
            experimentTypes.push(profiles.rows[i].experimentType);
        }
    }

    for (var i = 0; i < experimentTypes.length; i++) {
        this.sortExperimentExpressionLevels(experimentTypes[i]);
    }

    return experimentTypes;

}

function sortExperimentExpressionLevels (experimentType) {
    var experimentTypeSeriesData = [];

    for (var i = 0; i < this.yAxisCategories().length; i++) {
        if (this.props.profiles.rows[i].experimentType !== experimentType) {
            continue;
        }

        for (var j = 0; j < this.state.xAxisCategories.length; j++) {
            var literalValue = this.props.profiles.rows[i].expressions[j].value;

            if (literalValue === "") {
                var dataBelowCutoff = this.state.seriesDataBelowCutoff.concat([j, i, this.state.seriesDataBelowCutoffString]);
                this.setState({seriesDataBelowCutoff: dataBelowCutoff});
            } else if (literalValue === "NT") {
                var dataNA = this.state.seriesDataNA.concat([j, i, this.state.seriesDataNAString]);
                this.setState({seriesDataNA: dataNA});
            } else {
                var value = parseFloat(literalValue);
                if (!isNaN(value)) {
                    experimentTypeSeriesData.push([j, i, value]);
                }
            }
        }
    }

    experimentTypeSeriesData.sort(function(a, b) {
        return a[2] - b[2];
    });

    var experimentTypeMax = experimentTypeSeriesData[experimentTypeSeriesData.length - 1][2];

    for (var k = 0; k < this.state.seriesDataRanges.length; k++) {
        //seriesDataRanges[k].seriesData.concat(
        experimentTypeSeriesData.filter(
            function(datum) {
                return datum[2] > this.state.seriesDataRanges[k].from * experimentTypeMax && datum[2] <= this.state.seriesDataRanges[k].to * experimentTypeMax;
            }.bind(this)).forEach(
            function(filteredDatum) {
                var seriesD = this.state.seriesDataRanges[k].seriesData.concat(filteredDatum);
                this.setState({seriesDataRanges: seriesD});
            }.bind(this));
    }

    return experimentTypeSeriesData;

}

//*------------------------------------------------------------------*

exports.getXAxisCategories = getXAxisCategories;
exports.getYAxisCategories = getYAxisCategories;
exports.getYAxisCategoriesLinks = getYAxisCategoriesLinks;
