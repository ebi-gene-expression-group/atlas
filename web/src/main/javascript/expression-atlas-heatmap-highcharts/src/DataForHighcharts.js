"use strict";

//*------------------------------------------------------------------*
var _ = require('lodash');

//*------------------------------------------------------------------*
var Orderings = require('./OrderingsForHighcharts.js');

//*------------------------------------------------------------------*

var EMPTY = {
    xAxisCategories: {},
    columnOrderings: {},
    yAxisCategories: {},
    rowOrderings:{},
    dataSeries: [[],[],[],[]]
};

var getXAxisCategories = function (columnHeaders, isDifferential) {
  return columnHeaders.map(
    isDifferential
    ? function (columnHeader) {
        return {"label": columnHeader.displayName,
                "id" : columnHeader.id};
      }
    : function (columnHeader) {
        return {"label": columnHeader.factorValue,
                "id" : columnHeader.factorValueOntologyTermId};
      }
    );
};

var getYAxisCategories = function (rows, config) {
  return rows.map(
    config.isDifferential
    ? function (profile) {
        return {"label": profile.name,
                "id": profile.id };
      }
    : function (profile) {
        return {"label": profile.name,
                "id" : profile.id + "?geneQuery=" + config.geneQuery +
                    "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) };
      }
    );
};

var __dataPointFromExpression = function(infoCommonForTheRow, columnNumber, expression, rowNumber){
  //TODO make this function more complicated and determine the info to pass about each point here.
  return (
        expression.hasOwnProperty("value")
    ? {x: rowNumber, y:columnNumber, value:expression.value ,info:infoCommonForTheRow}
    : (
        expression.hasOwnProperty("foldChange")
      ? {x: rowNumber, y: columnNumber, value: expression.foldChange,info:{pValue: expression.pValue, foldChange: expression.foldChange}}
      : null
    )
  );
};

var _dataPointsFromRow = _.curry(function(config,row, columnNumber){
  return (
    row.expressions
    .map(_.curry(__dataPointFromExpression,4)({unit: unitForThisRowOfData(row,config)}, columnNumber))
    .filter(function(el) {
      return el;
    })
  );
},3);

var unitForThisRowOfData = function(row,config){
    return (
      config.isDifferential
      ? "Log2Fold change" //this is what we use for point.value, but we don't actually use this unit for display. See Formatters.jsx.
      : (config.isMultiExperiment
        ? row.experimentType === "RNASEQ_MRNA_BASELINE"
          ? row.name.indexOf("FANTOM") > -1 ? "TPM": "FPKM"
          : ""
        : "") //TODO determine the units on the experiment page as well
    );
};

var _groupByExperimentType = function(chain, config){
  return (
    chain
    .map(function(row, columnNumber) {
      return [
        row.experimentType,
        _dataPointsFromRow(config,row, columnNumber)
      ];
    })
    .groupBy(function(experimentTypeAndRow) {
      return experimentTypeAndRow[0]
    })
    .mapValues(function(rows) {
      return rows.map(function(experimentTypeAndRow) {
        return experimentTypeAndRow[1];
      })
    })
    .mapValues(_.flatten)
    .toPairs()
  );
};

var _groupSingleExperiment = function(chain, config){
  var _inferExperimentType = function(config){
    return (
      config.isDifferential
        ? "DIFFERENTIAL"
        : config.isProteomics
          ? "PROTEOMICS_BASELINE"
          : "RNASEQ_MRNA_BASELINE"
    );
  };
  return (
    chain
    .map(_dataPointsFromRow(config))
    .flatten()
    .thru(function(value){
      return [
        [_inferExperimentType(config), value]
      ];
    })
  );
};

var _experimentsIntoDataSeriesByThresholds = function(thresholds){
  return function(experimentType, dataPoints) {
    return dataPoints.map(
      function(dataPoint) {
        return [
          _.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, dataPoint.value),
          dataPoint
        ];
      }.bind(this)
    )
  };
};

var getDataSeries = function(profilesRows, config) {
  return (
    config.isDifferential
    ? _getDataSeries(
      {
        DIFFERENTIAL : [-1,-0.01,0.01, 1]
      },
      ["High down", "Down", "Below cutoff", "Up", "High up"],
      ["blue", "lightblue", "grey", "darkSalmon","fireBrick"],
      profilesRows, config)
    : _getDataSeries(
      {
        RNASEQ_MRNA_BASELINE : [0,10,1000],
        PROTEOMICS_BASELINE : [0,0.001,8],
        DEFAULT : [0,10,1000]
      },
      ["Below cutoff", "Low", "Medium", "High"],
      ["#eaeaea", "#45affd", "#1E74CA", "#024990"],
      profilesRows, config)
  );
};

var _getDataSeries = function (thresholds, names, colours, profilesRows, config) {
  return (
    (config.isMultiExperiment
      ? _.curryRight(_groupByExperimentType,2)(config)
      : _.curryRight(_groupSingleExperiment,2)(config)
    )(_.chain(profilesRows))
    .map(_.spread(_experimentsIntoDataSeriesByThresholds(thresholds)))
    .flatten()
    .groupBy(_.spread(function(dataSeriesAssigned, point) {
      return dataSeriesAssigned;
    }))
    .mapValues(function(bucket) {
      return bucket.map(_.spread(function(dataSeriesAssigned, point) {
        return point;
      }))
    })
    .transform(function(result, bucket, bucketNumber) {
        result[bucketNumber].data=bucket;
    }, _.range(names.length).map(
      function(i){
        return {
          name: names[i],
          colour: colours[i],
          data: []
        };
      })
    )
    .value()
  );
};

var extractExpressionValues = function(rows, isDifferential){
  var extractor = function(valueField){
    return (
      function(expression){
        return (
          expression.hasOwnProperty(valueField)
          ? {value : expression[valueField]}
          : {}
        );
      });
  }
  return rows.map(
    function(row){
      return row.expressions.map(
        extractor(isDifferential ? "foldChange": "value")
      );
    }
  );
};

var getTheWholeDataObject = function(rows, columnHeaders, config){
  var expressions = extractExpressionValues(rows,config.isDifferential);

  return {
    xAxisCategories: getXAxisCategories(columnHeaders, config.isDifferential),
    yAxisCategories: getYAxisCategories(rows, config),
    orderings: Orderings.create(expressions,columnHeaders, rows, config),
    dataSeries: getDataSeries(rows, config)
  };
};

//*------------------------------------------------------------------*
exports.EMPTY = EMPTY;
exports.get = getTheWholeDataObject;
