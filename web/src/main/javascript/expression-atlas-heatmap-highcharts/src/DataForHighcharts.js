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

var combineComparators =
    _.curry(
      function(comparators,e1,e2){
        return (
          comparators
          .map(
            function(comparator){
              return (comparator(e1,e2));
            })
          .filter(
            function(res){
              return res;
            })
          .concat([0])
          [0]
          );
      },3);


var _createOrdering = _.curry(
  function(comparator, arr){
    return (
      arr
      .map(function(e,ix){
        return [e,ix];
      })
      .sort(function(e_ixLeft,e_ixRight){
        return comparator(e_ixLeft[0],e_ixRight[0]);
      })
      .map(function(e_ix){
        return e_ix[1];
      })
    );
  },2);

//apply rank first, if the ranks are the same use comparator value
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

var _orderingByRank= _.curry(
  function(rank, arr){
    return _createOrdering(
      function(ixL,ixR){
        return rank[ixR]-rank[ixL]; //higher ranks go to the beginning of series
      }
    )(_.range(0,arr.length));
  },2);

var orderingByRank = function(rank){
  return (
    _.range(0,rank.length)
    .sort(
      function(ixL,ixR){
        return rank[ixR]-rank[ixL]; //higher ranks go to the beginning of series
      }
    )
  );
};

//LOL this will not work, this can only sort indices
var comparatorByRank = function(rank){
  return (
    function(ixL,ixR){
      return rank[ixR]-rank[ixL]; //higher ranks go to the beginning of series
    }
  );
};

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

var rankRowsByThreshold2 = function(threshold, expressions){
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
        );
      })
    .map(
      function(row){
        return (
          _.sum(row) > row.length * threshold
          ? 10e6
          : 0
        );
      })
  );
}

var comparatorByProperty = _.curry(
  function (property,e1,e2){
    return e1[property].localeCompare(e2[property]);
  }
);

var _orderingByPropertyName = function(property){
  return _createOrdering(comparatorByProperty);
};

var _orderingByPropertyNameReversedForDebugging = function(property){
  return _createOrdering(
    function comparator(e1,e2){
      return - e1[property].localeCompare(e2[property]);
    });
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
    PROTEOMICS_BASELINE : [0,10,1000], //TODO decide on the thresholds for proteomics baseline experiments
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
        columns: orderingByRank(columnRank),
        rows: orderingByRank(rowRank)
      },
      "Gene expression - new" : {
        columns: createOrdering(columnRank,comparatorByProperty("factorValue"),columnHeaders),
        rows: createOrdering(rowRank,comparatorByProperty("name"),rows)
      },
      "Alphabetical" : {
        columns: _orderingByPropertyName("factorValue")(columnHeaders),
        rows: _orderingByPropertyName("name")(rows)
      },
      "Debug- reversed" :{
        columns: _orderingByPropertyNameReversedForDebugging("factorValue")(columnHeaders),
        rows: _orderingByPropertyNameReversedForDebugging("name")(rows)
      }
    },
    dataSeries: getDataSeries(rows)
  };
};


//*------------------------------------------------------------------*
exports.EMPTY = EMPTY;
exports.get = getTheWholeDataObject;
