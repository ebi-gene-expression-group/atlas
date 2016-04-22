"use strict";

//*------------------------------------------------------------------*

function getXAxisCategories (columnHeaders) {
    return columnHeaders.map(function (columnHeader) {
        return {"label": columnHeader.factorValue, "id" : columnHeader.factorValueOntologyTermId};
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

function compareLevels(a,b) {
    var a1 = parseFloat( a.value );
    var b1 = parseFloat( b.value );

    //Both are numbers
    if( !isNaN(a1) && !isNaN(b1) ) return a1 > b1 ? -1: 1;

    //a IS number
    if( !isNaN(a1) )  return -1;

    //b IS number
    if( !isNaN(b1) ) return 1;

    return a.value.localeCompare(b.value);
    // return a > b ? 1 : -1;
}

function initializeRanking(cols) {
    var rank_array = [];
    for(var i=0; i<cols; i++) {
        rank_array[i] = 0;
    }

    return rank_array;
}

function rankColumns (profiles, columnHeaders) {
    var rank_array = initializeRanking(columnHeaders.length);

    for (var i=0; i < profiles.rows.length; i++) {
        var mapped = profiles.rows[i].expressions.map(function(el, i) {
            return { index: i, value: el.value, factorName: el.factorName, color: el.color };
        });

        //Compare the level values for each expression within each row and sort them
        var sorted_array = mapped.sort(compareLevels);
        // Rank levels
        for (var j=0; j < sorted_array.length; j++) {
            var ind = sorted_array[j].index;
            rank_array[ind] = rank_array[ind] + j + 1;
        }
    }

    //Avg rank_array by rows
    var rank_avg = [];
    for(var i=0; i<rank_array.length; i++) {
        rank_avg[i] = rank_array[i] / profiles.rows.length;
    }

    var avg_mapped = rank_avg.map(function(el, i) {
        return { index: i, value: el}
    });

    //Sort tissues by increasing average rank
    var sort_tissues = avg_mapped.sort(function (a,b) {
        return a.value-b.value;
    });

    //Modify the rows
    for (var i=0; i< profiles.rows.length; i++) {
        var expressions = profiles.rows[i].expressions;
        var new_expressions = [];
        var new_columnHeaders = [];

        for (var j = 0; j < sort_tissues.length; j++) {
            var position = sort_tissues[j].index;
            new_expressions.push(expressions[position]);
            new_columnHeaders.push(columnHeaders[position]);
        }

        profiles.rows[i].expressions = new_expressions;
    }

    return {
        profiles: profiles.rows,
        columnHeaders: new_columnHeaders
    }
}

function applyThresholdtoColumns(rows, columns, threshold) {

    var percentageExpressedBelowThreshold = [];
    var percentageExpressedAboveThreshold = [];
    for (var i=0; i < columns.length; i++) {
        var count = 0;
        for (var j=0; j < rows.length; j++) {
            var value = rows[j].expressions[i].value;
            if(value != "NT" && value != "") {
                count++;
            }
        }

        var totalExpressed = {};
        totalExpressed.total = count;
        totalExpressed.percentage = (count / rows.length) * 100;
        totalExpressed.index = i;

        if(totalExpressed.percentage > threshold) {
            percentageExpressedAboveThreshold.push(totalExpressed);
        } else {
            percentageExpressedBelowThreshold.push(totalExpressed);
        }
    }

    //Sort by percentage
    var percentageExpressedAboveThresholdSorted = percentageExpressedAboveThreshold.sort(function (a,b) {
        return b.percentage-a.percentage;
    });
    var percentageExpressedBelowThresholdSorted = percentageExpressedBelowThreshold.sort(function (a,b) {
        return b.percentage-a.percentage;
    });

    var percentageExpressed = percentageExpressedAboveThresholdSorted.concat(percentageExpressedBelowThresholdSorted);

    //Filter and order the rows with the new percentage
    for (var i=0; i< rows.length; i++) {
        var expressions = rows[i].expressions;
        var new_expressions = [];
        var new_columnHeaders = [];

        for (var j = 0; j < percentageExpressed.length; j++) {
            var position = percentageExpressed[j].index;
            new_expressions.push(expressions[position]);
            new_columnHeaders.push(columns[position]);
        }

        rows[i].expressions = new_expressions;
    }

    return {
        rows: rows,
        columnHeaders: new_columnHeaders
    }
}

function rankExperiments(rows, columns) {

    //Initialize ranking
    var rank_experiments = [];
    for(i=0; i< columns; i++) {
        rank_experiments[i] = new Array(rows.length);
    }

    for(i=0; i< columns; i++) {
        for (j=0; j<rows.length; j++) {
            rank_experiments[i][j] = 0;
        }
    }

    //Sort
    var sorted_cols = [];
    for (var i=0; i < columns; i++) {

        var cols_expressions = [];
        for (j = 0; j < rows.length; j++) {
            var expr = {};
            expr.factorName = rows[j].expressions[i].factorName;
            expr.value = rows[j].expressions[i].value;
            expr.index = j;

            cols_expressions.push(expr)
        }

        //Compare the level values for each expression within each row and sort them
        var sorted_profiles = cols_expressions.sort(compareLevels);
        sorted_cols.push(sorted_profiles);
    }

    // Rank levels
    for (var j=0; j < sorted_cols.length; j++) {
        for (var k=0; k < rows.length; k++) {
            var ind = sorted_cols[j][k].index;
            rank_experiments[j][ind] = rank_experiments[j][ind] + k + 1;
        }
    }

    //Avg rank_experiments by cols
    var exp_rank_avg = [];
    for (var r=0; r<rows.length; r++) {
        var sum = 0;
        for (var cols=0; cols<rank_experiments.length; cols++) {
            sum = sum + rank_experiments[cols][r];
        }
        exp_rank_avg.push(sum/rank_experiments.length);
    }

    var exp_rank_avg_mapped = exp_rank_avg.map(function(el, i) {
        return { index: i, value: el}
    });

    //Sort experiments by increasing average rank
    var sort_experiments = exp_rank_avg_mapped.sort(function (a,b) {
        return a.value-b.value;
    });

    //Replace new rows
    var new_rows = [];

    for(var i=0; i<sort_experiments.length; i++) {
        var position = sort_experiments[i].index;
        new_rows.push(rows[position]);
    }

    return new_rows;
}

//*------------------------------------------------------------------*

exports.getXAxisCategories = getXAxisCategories;
exports.getYAxisCategories = getYAxisCategories;
exports.getYAxisCategoriesLinks = getYAxisCategoriesLinks;
exports.rankColumns = rankColumns;
exports.rankExperiments = rankExperiments;
exports.applyThresholdtoColumns = applyThresholdtoColumns;