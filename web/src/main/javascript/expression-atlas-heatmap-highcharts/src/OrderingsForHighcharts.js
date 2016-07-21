"use strict";

//*------------------------------------------------------------------*
var _ = require('lodash');

//*------------------------------------------------------------------*


//*------------------------------------------------------------------*

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

var noOrdering = function(arr){
  return arr.map(function(el,ix){return ix;})
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
        "Alphabetical order" : {
          columns: createAlphabeticalOrdering("factorValue", columnHeaders),
          rows: createAlphabeticalOrdering("name", rows)
        },
        "Gene expression" : {
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

//*------------------------------------------------------------------*

exports.create = createOrderings;
