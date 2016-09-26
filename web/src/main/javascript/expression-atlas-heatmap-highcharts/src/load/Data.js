"use strict";

//*------------------------------------------------------------------*
var _ = require('lodash');

//*------------------------------------------------------------------*

var _columnGroupings = function(columnGroupings, id){
  return (
    columnGroupings.map(function(grouping){
      return {
        name: grouping.name,
        memberName: grouping.memberName,
        values:
          grouping
          .groups
          .filter(function(group){
            return group.values.indexOf(id)>-1
          })
          .map(function(group){
            return {
              label: group.name,
              id: group.id
            }
          })
        }
      }
    )
  )
}

var getXAxisCategories = function (columnHeaders,columnGroupings, config) {
  return columnHeaders.map(
    config.isExperimentPage
    ? config.isDifferential
      ? function (columnHeader) {
          return {"label": columnHeader.displayName,
                  "id" : columnHeader.id,
                  "info":{
                    trackId:columnHeader.id,
                    tooltip:columnHeader.contrastSummary,
                    groupings:[]
                  }};
        }
      : function (columnHeader) {
          return {"label": columnHeader.factorValue,
                  "id" : columnHeader.factorValueOntologyTermId || "",
                  "info":{
                    trackId:columnHeader.assayGroupId,
                    tooltip:columnHeader.assayGroupSummary,
                    groupings: _columnGroupings(columnGroupings, columnHeader.factorValueOntologyTermId || "")
                  }};
        }
    : function (columnHeader) {
        return {"label": columnHeader.factorValue,
                "id" : columnHeader.factorValueOntologyTermId || "",
                "info":{
                  trackId:"",
                  tooltip:{},
                  groupings: _columnGroupings(columnGroupings, columnHeader.factorValueOntologyTermId || "")
                }};
      }
      );
};

var getYAxisCategories = function (rows, config) {
  return rows.map(
    config.isExperimentPage
    ? function (profile) {
        return {"label": profile.name,
                "id": profile.id,
                "info":{
                  trackId:profile.id
                }};
      }
    : function (profile) {
        return {"label": profile.name,
                "id" : profile.id + "?geneQuery=" + config.geneQuery +
                    (profile.serializedFilterFactors?"&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors):""),
                "info":{
                  trackId:""
                }};
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
      ? {
        x: rowNumber,
        y: columnNumber,
        value: +expression.foldChange,
        info:Object.assign(
          {
          pValue: expression.pValue,
          foldChange: expression.foldChange,
          tStat: expression.tStat||""}
          ,infoCommonForTheRow)
        }
      : null
    )
  );
};

var _commonPropertiesForRow = function(row, config){
  return (
    Object.assign({},
      {unit: unitForThisRowOfData(row,config)},
      {index: row.coexpressionOfGene? row.coexpressionOfGene.index+1: 0}
    )
  )
}

var _dataPointsFromRow = _.curry(function(config,row, columnNumber){
  return (
    row.expressions
    .map(_.curry(__dataPointFromExpression,4)(_commonPropertiesForRow(row,config), columnNumber))
    .filter(function(el) {
      return el;
    })
  );
},3);

var unitForThisRowOfData = function(row,config){
    return (
      config.isDifferential
      ? "Log2Fold change" //this is what we use for point.value, but we don't actually use this unit for display. See Formatters.jsx.
      : config.isMultiExperiment
        ? row.experimentType === "RNASEQ_MRNA_BASELINE"
          ? row.name.indexOf("FANTOM") > -1 ? "TPM": "FPKM"
          : ""
        : config.experimentAccession.indexOf("PROT")>-1
          ? ""
          : config.description.toUpperCase().indexOf("FANTOM") > -1
            ? "TPM"
            : "FPKM"
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
  var _fns = [_.lt, _.eq,_.gt].map(function(f){return function(point){return f(point.value,0);};});
  var _belowCutoff = _fns[1];
  return (
    config.isMultiExperiment
    ? _dataSplitByThresholds(
      {
        RNASEQ_MRNA_BASELINE : [0,10,1000],
        PROTEOMICS_BASELINE : [0,0.001,8],
        DEFAULT : [0,10,1000]
      },
      ["Below cutoff", "Low", "Medium", "High"],
      ["#eaeaea", "#45affd", "#1E74CA", "#024990"],
      profilesRows, config)
    : config.isDifferential
      ? _dataProportionallyInEachSeries(profilesRows, config,
          _fns,
          [["High down", "Down"], ["Below cutoff"], ["Up", "High up"]],
          [["#0000ff", "#8cc6ff"], ["#808080"], ["#e9967a","#b22222"]])
      : _dataProportionallyInEachSeries(profilesRows, config,
          [_belowCutoff,_.negate(_belowCutoff)],
          [["Below cutoff"],["Low", "Medium", "High"]],
          [["#808080"],["#8cc6ff","#0000ff","#0000b3"]])

  )
};
var _splitDataSetByProportion = function(data,names,colours){
  var sortedValues = data.map(function(point){return point.value}).sort(function(l,r){return l-r;});
  var howManyPointsInTotal = data.length;
  var howManyDataSetsToSplitIn = names.length;
  return (
    _bucketsIntoSeries(names,colours)(
      _.chain(data)
      .map(function(point){
        return [
          Math.floor(_.sortedIndex(sortedValues, point.value)/howManyPointsInTotal *howManyDataSetsToSplitIn)
         , point]
      })
    ).value()
  );
}

var _dataProportionallyInEachSeries = function(profilesRows, config, filters, names, colors){
  var points = _.flatten(profilesRows.map(_dataPointsFromRow(config)));
  return _.flatten(_.range(filters.length).map(function(i){
    return _splitDataSetByProportion(points.filter(filters[i]), names[i], colors[i]);
  }));
}

var _bucketsIntoSeries = _.curry(function(names,colours,chain){
  return (
    chain
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
          info: {
            name: names[i],
            colour: colours[i]
          },
          data: []
        };
      })
    )
  );
},3);

var _dataSplitByThresholds = function (thresholds, names, colours, profilesRows, config) {
  return (
    _bucketsIntoSeries(names,colours)(
      _groupByExperimentType(_.chain(profilesRows),config)
      .map(_.spread(_experimentsIntoDataSeriesByThresholds(thresholds)))
      .flatten()
    ).value()
  );
};

var getTheWholeDataObject = function(config,rows, columnHeaders, columnGroupings){
  return {
    xAxisCategories: getXAxisCategories(columnHeaders,columnGroupings || [], config),
    yAxisCategories: getYAxisCategories(rows, config),
    dataSeries: getDataSeries(rows, config)
  };
};

//*------------------------------------------------------------------*
module.exports = getTheWholeDataObject;
