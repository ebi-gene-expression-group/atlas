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
    dataSeries: [[],[],[],[]]
};

//apply rank first,use comparator to resolve ties
var createOrdering = function(rank, comparator, arr){
  return (
    arr.map(function(e,ix){
      return [e,ix];
    })
    .sort(function(e_ixLeft,e_ixRight){
      return ( //lower ranks go to the beginning of series
        rank[e_ixLeft[1]] - rank[e_ixRight[1]] || comparator(e_ixLeft[0],e_ixRight[0])
      );
    }).map(function(e_ix){
      return e_ix[1];
    })
  );
};

var createAlphabeticalOrdering= function(property, arr){
  return (
    createOrdering(arr.map(_.constant(0)), comparatorByProperty(property),arr)
  );
}

var comparatorByProperty = _.curry(
  function (property,e1,e2){
    return e1[property].localeCompare(e2[property]);
  }
);

var rankColumnsByWhereTheyAppearFirst = function(expressions){
  return (
    _.chain(expressions)
    .map(function(row){
      return (
        row.map(function(e){
          return (
            +e.hasOwnProperty("value")
          );
        })
      );
    })
    .thru(_.spread(_.zip))
    .map(function(column){
      return (
        column
        .map(function(e,ix){
          return e*(ix+1);
        })
        .filter(_.identity)
      );
    })
    .map(_.min)
    .value()
  );
};

var highestColumnRankPossible = function(expressions){
  return expressions.length? expressions[0].length : Number.MAX_VALUE;
}

var thresholdColumnsByExpressionAboveCutoff = function(expressions){
  return (
    rankColumnsByExpression(expressions, 0)
    .map(function(e){
      //check if the function assigned the rank value corresponding to everything filtered off
      return e === highestColumnRankPossible(expressions) ? 1 : 0;
    })
  );
}

var rankColumnsByExpression = function(expressions, minimalExpression){
  var includeInRanking =
    typeof minimalExpression=== 'number'
    ? function(e) {
      return e.hasOwnProperty("value") && !isNaN(e.value) && Math.abs(e.value)> minimalExpression;
    }
    : function(e) {
      return e.hasOwnProperty("value") && !isNaN(e.value);
    };
  return (
    _.chain(expressions)
    .map(function(row){
      var valuesInRow =
        row
        .filter(includeInRanking)
        .map(function(e) {
        	return e.value;
        })
        .sort(function(l, r) {
        	return r - l;
        })
        .filter(function(e, ix, self) {
        	return self.indexOf(e) === ix;
        });
      return (
        row.map(function(e){
          return includeInRanking(e) ? valuesInRow.indexOf(e.value) : "missing";
        })
      );
    })
    .thru(_.spread(_.zip))
    .map(function(ranks){
      return (
        ranks
        .filter(_.negate(isNaN))
      );
    })
    .map(function(ranks){
      return (
        ranks.length ? _.sum(ranks) / ranks.length : highestColumnRankPossible(expressions)
      );
    })
    .value()
  );
};


var rankColumnsByThreshold = function(threshold, expressions){
  return (
    expressions
    .map(
      function(row){
        return (
          row.map(
            function(point){
              return +(point.hasOwnProperty("value") && point.value!==0);
            }
          )
        );
      })
    .reduce(function(r1,r2){
      return r1.map(
        function(el,ix){
          return el + r2[ix];
        },_.fill(Array(expressions.length? expressions[0].length :0), 0));
      })
    .map(function(countOfExperimentsWhereTissueExpressedAboveCutoff){
      return (
          countOfExperimentsWhereTissueExpressedAboveCutoff > expressions.length * threshold ? 0 : 1
      );
    })
  );
};

var rankColumnsByCountOfExperimentsExpressed = function(expressions){
  return (
    expressions
    .map(
      function(row){
        return (
          row.map(
            function(point){
              return +(point.hasOwnProperty("value") && point.value!==0);
            }
          )
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

var noOrdering = function(arr){
  return arr.map(function(el,ix){return ix;})
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

var combineRanks = function(ranksAndTheirWeighings){
  return (
    _
    .chain(ranksAndTheirWeighings)
    .map(_.spread(function(ranks, weighing){
      return (
        ranks.map(_.curry(_.multiply, 2)(weighing))
      );
    }))
    .thru(_.spread(_.zip))
    .map(_.sum)
    .value()
  );
};

var createOrderings = function (expressions, columnHeaders, rows, config){
  var transposed = _.zip.apply(_,expressions);
  console.log("width: "+transposed.length+", height: "+expressions.length);
  return (
    config.isMultiExperiment || config.isReferenceExperiment
    ?
      {
        "Default" : {
          columns: createAlphabeticalOrdering("factorValue", columnHeaders),
          rows: noOrdering(rows)
        },
        "Alphabetical" : {
          columns: createAlphabeticalOrdering("factorValue", columnHeaders),
          rows: createAlphabeticalOrdering("name", rows)
        },
        "Gene Expression" : {
          columns: createOrdering(
            combineRanks([
              [rankColumnsByWhereTheyAppearFirst(expressions), 1],
              [rankColumnsByExpression(expressions), 1e3],
              [rankColumnsByThreshold(0.05 + 0.4/Math.pow(1+transposed.length/8,0.4),expressions), 1e6],
              [thresholdColumnsByExpressionAboveCutoff(expressions),1e7],

            ]),comparatorByProperty("factorValue"),columnHeaders),
          rows: createOrdering(
              combineRanks([
              [rankColumnsByExpression(transposed), 1e3],
              [rankColumnsByThreshold(0.05 + 0.4/(1+expressions.length/5),transposed), 1e6]
            ]),comparatorByProperty("name"),rows)
        },
        "By fraction of expression" : {
          columns: createOrdering(combineRanks([[rankColumnsByCountOfExperimentsExpressed(expressions),-1]]),_.constant(0),columnHeaders),
          rows: createOrdering(combineRanks([[rankColumnsByCountOfExperimentsExpressed(_.zip.apply(_,expressions)),-1]]),_.constant(0),rows)
        }
      }
    :
      {
        "Default" : {
          columns: noOrdering(columnHeaders),
          rows: noOrdering(rows)
        }
      }
  );
};

var getTheWholeDataObject = function(rows, columnHeaders, config){
  var expressions = extractExpressionValues(rows,config.isDifferential);

  return {
    xAxisCategories: getXAxisCategories(columnHeaders, config.isDifferential),
    yAxisCategories: getYAxisCategories(rows, config),
    orderings: createOrderings(expressions,columnHeaders, rows, config),
    dataSeries: getDataSeries(rows, config)
  };
};

//*------------------------------------------------------------------*
exports.EMPTY = EMPTY;
exports.get = getTheWholeDataObject;
