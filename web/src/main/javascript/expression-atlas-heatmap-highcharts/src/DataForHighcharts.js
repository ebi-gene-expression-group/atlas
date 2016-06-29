"use strict";

//*------------------------------------------------------------------*
var _ = require('lodash');

//*------------------------------------------------------------------*


//*------------------------------------------------------------------*

var EMPTY = {
  xAxisCategories: {},
  columnOrderings: {},
  yAxisCategories: {},
  rowOrderings:{},
  dataSeries: [[],[],[],[]],
};

//apply rank first,use comparator to resolve ties
var createOrdering = function(rank, comparator, arr){
  return (
    arr
    .map(function(e,ix){
      return [e,ix];
    })
    .sort(function(e_ixLeft,e_ixRight){
      return ( //higher ranks go to the beginning of series
        rank[e_ixRight[1]] - rank[e_ixLeft[1]] || comparator(e_ixLeft[0],e_ixRight[0])
      );
    })
    .map(function(e_ix){
      return e_ix[1];
    })
  );
};

var comparatorByProperty = _.curry(
  function (property,e1,e2){
    return e1[property].localeCompare(e2[property]);
  }
);

var rankColumnsByExpression = function(expressions){
  return (
    expressions
    .map(
      function(row){
        var rowIndexed = row.map(
          function(point, ix){
            return [point,ix];
          }
        );
        var indicesSortedByExpression =
          rowIndexed
          .filter(
            function(e){
              return e[0].hasOwnProperty("value")
            })
          .sort(
              function(l,r){
                return l[0].value - r[0].value;
            })
          .map(
            function(p){
              return p[1];
            });
        return (
          rowIndexed.map(
            function(pointAndIndex){
              return 1+ indicesSortedByExpression.indexOf(pointAndIndex[1]); //rank value zero means no expression
          })
        );
      })
  .reduce(function(r1,r2){
    return r1.map(
      function(el,ix){
        return el + r2[ix];
      });
    })
  );
};


var rankRowsByExpression = function(expressions){
  return rankColumnsByExpression(_.zip.apply(_,expressions));
};

var rankColumnsByThreshold = function(threshold, expressions){
  return (
    expressions
    .map(
      function(row){
        return (
          row.map(
            function(point){
              return +point.hasOwnProperty("value");
            }
          )
        )
      })
    .reduce(function(r1,r2){
      return r1.map(
        function(el,ix){
          return el + r2[ix];
        });
      })
    .map(function(countOfExperimentsWhereTissueExpressed){
      return (
        countOfExperimentsWhereTissueExpressed > expressions.length * threshold
        ? 10e6
        : 0
      )
    })
  );
};

var rankRowsByThreshold = function(threshold, expressions){
  return rankColumnsByThreshold(threshold, _.zip.apply(_,expressions));
};

var getXAxisCategories = function (columnHeaders) {
    return columnHeaders.map(function (columnHeader) {
        return {"label": columnHeader.factorValue,
                "id" : columnHeader.factorValueOntologyTermId};
    });
};

var getYAxisCategories = function (rows, heatmapConfig) {
    return rows.map(function (profile) {
        return {"label": profile.name,
                "id" : profile.id + "?geneQuery=" + heatmapConfig.geneQuery +
                    "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) };
    });
};

var noOrdering = function(arr){
  return arr.map(function(el,ix){return ix;})
};

var getDataSeries = function (profilesRows) {
  var thresholds = {
    RNASEQ_MRNA_BASELINE : [0,10,1000],
    PROTEOMICS_BASELINE : [0,0.001,8],
    DEFAULT : [0,10,1000]
  };
  return (_
    .chain(profilesRows)
    .map(
        function(row, columnNumber) {
            return [row.experimentType,
                row.expressions
                .map(function(expression, rowNumber) {
                    return (
                        expression.hasOwnProperty("value") && expression.value !== "NT"
                        ? [rowNumber, columnNumber, expression.value || "Below cutoff"]
                        : null)
                })
                .filter(function(el) {
                    return el;
                })
            ]
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
    .map(_.spread(
        function(experimentType, dataPoints) {
            return dataPoints.map(
                _.spread(function(xPosition, yPosition, value) {
                    return [
                        _.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, value),
                        [xPosition, yPosition, value]
                    ];
                }.bind(this))
            )
        }.bind(this)))
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
        result[bucketNumber] = bucket;
    }, _.fill(Array(4), []))
    .value()
  );
};


var getTheWholeDataObject = function(rows, columnHeaders, config, isMultiExperiment){
  var expressions = rows.map(
    function(row){
      return row.expressions;
    }
  );

  var columnRank =
    _.zip(
      rankColumnsByExpression(expressions),
      rankColumnsByThreshold(0.4,expressions)
    ).map(_.sum);

  var rowRank =
    isMultiExperiment
    ? _.zip(
      rankRowsByExpression(expressions),
      rankRowsByThreshold(0.4,expressions)
      ).map(_.sum)
    : rankRowsByExpression(expressions);

  return {
    xAxisCategories: getXAxisCategories(columnHeaders),
    yAxisCategories: getYAxisCategories(rows, config),
    orderings: {
      "Default" : {
        columns: noOrdering(columnHeaders),
        rows: noOrdering(rows)
      },
      "Gene expression" : {
        columns: createOrdering(columnRank,comparatorByProperty("factorValue"),columnHeaders),
        rows: createOrdering(rowRank,comparatorByProperty("name"),rows)
      },
      "Alphabetical" : {
        columns: createOrdering(columnHeaders.map(_.constant(0)), comparatorByProperty("factorValue"),columnHeaders),
        rows: createOrdering(rows.map(_.constant(0)), comparatorByProperty("name"), rows)
      }
    },
    dataSeries: getDataSeries(rows)
  };
};


//*------------------------------------------------------------------*
exports.EMPTY = EMPTY;
exports.get = getTheWholeDataObject;
