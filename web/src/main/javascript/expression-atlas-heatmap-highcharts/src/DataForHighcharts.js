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

var rankColumnsByExpression = function(expressions){
  return (
    _.chain(expressions)
    .map(function(row){
      var valuesInRow =
        row
        .filter(function(e) {
        	return e.hasOwnProperty("value") && !isNaN(e.value);
        })
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
          return e.value === undefined ? "missing" : valuesInRow.indexOf(e.value);
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
        _.sum(ranks) / ranks.length
      );
    })
    .value()
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
        );
      })
    .reduce(function(r1,r2){
      return r1.map(
        function(el,ix){
          return el + r2[ix];
        });
      })
    .map(function(countOfExperimentsWhereTissueExpressed){
      return (
        countOfExperimentsWhereTissueExpressed > expressions.length * threshold ? 0 : 1
      );
    })
  );
};

var rankRowsByThreshold = function(threshold, expressions){
  return rankColumnsByThreshold(threshold, _.zip.apply(_,expressions));
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

var __dataPointFromExpression = function(columnNumber, expression, rowNumber){
  return (
    expression.hasOwnProperty("value") && expression.value !== "NT"
    ? [rowNumber, columnNumber, expression.value || "Below cutoff"]
    : (
        expression.hasOwnProperty("foldChange")
      ? [rowNumber, columnNumber, expression.foldChange || "Below cutoff"]
      : null
    )
  );
}

var _dataPointsFromRow = function(row, columnNumber){
  return (
    row.expressions
    .map(_.curry(__dataPointFromExpression,3)(columnNumber))
    .filter(function(el) {
      return el;
    })
  );
}

var _groupByExperimentType = function(chain){
  return (
    chain
    .map(function(row, columnNumber) {
      return [
        row.experimentType,
        _dataPointsFromRow(row, columnNumber)
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
}

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
    .map(_dataPointsFromRow)
    .flatten()
    .thru(function(value){
      return [
        [_inferExperimentType(config), value]
      ];
    })
  );
}

var _experimentsIntoDataSeriesByThresholds = function(thresholds){
  return function(experimentType, dataPoints) {
    return dataPoints.map(
      _.spread(function(xPosition, yPosition, value) {
        return [
          _.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, value),
          [xPosition, yPosition, value]
        ];
      }.bind(this))
    )
  };
}

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
}

var _getDataSeries = function (thresholds, names, colours, profilesRows, config) {
  return (
    (config.isMultiExperiment
      ? _groupByExperimentType
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
}

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
}

var calculateColumnRank = function(expressions){
  return (
    combineRanks([
      [rankColumnsByWhereTheyAppearFirst(expressions), 1],
      [rankColumnsByExpression(expressions), 1e3],
      [rankColumnsByThreshold(0.4,expressions), 1e6]
    ])
  );
}

var calculateRowRank = function (expressions, config){
  return (
    config.isMultiExperiment
    ?   combineRanks([
          [rankRowsByExpression(expressions), 1e3],
          [rankRowsByThreshold(0.4,expressions), 1e6]
        ])
    : rankRowsByExpression(expressions)
  );
}

var createOrderings = function (columnRank, rowRank, columnHeaders, rows, config){
  return (
    config.isMultiExperiment || config.isReferenceExperiment
    ?
      {
        "Default" : {
          columns: createAlphabeticalOrdering("factorValue", columnHeaders),
          rows: noOrdering(rows)
        },
        "Gene expression" : {
          columns: createOrdering(columnRank,comparatorByProperty("factorValue"),columnHeaders),
          rows: createOrdering(rowRank,comparatorByProperty("name"),rows)
        },
        "Alphabetical" : {
          columns: createAlphabeticalOrdering("factorValue", columnHeaders),
          rows: createAlphabeticalOrdering("name", rows)
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
}

var getTheWholeDataObject = function(rows, columnHeaders, config){
  var expressions = extractExpressionValues(rows,config.isDifferential);

  return {
    xAxisCategories: getXAxisCategories(columnHeaders, config.isDifferential),
    yAxisCategories: getYAxisCategories(rows, config),
    orderings: createOrderings(calculateColumnRank(expressions),calculateRowRank(expressions,config),columnHeaders, rows, config),
    dataSeries: getDataSeries(rows, config)
  };
};

//*------------------------------------------------------------------*
exports.EMPTY = EMPTY;
exports.get = getTheWholeDataObject;
