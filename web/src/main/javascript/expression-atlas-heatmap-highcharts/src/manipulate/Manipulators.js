"use strict";
//*------------------------------------------------------------------*

/*
All functions in this module accept and return a following format of data:
{
  dataSeries : [info: {...: String}, data: [Point]}]
  xAxisCategories: [X axis label]
  yAxisCategories: [Y axis label]
}
*/

//*------------------------------------------------------------------*

var orderHeatmapData = function(ordering, data){
  var permuteX = function(x){
    return ordering.columns.indexOf(x);
  };

  var permuteY = function(y){
    return ordering.rows.indexOf(y);
  };

  var permutePoint = function(point){
    return {
      x: permuteX(point.x),
      y: permuteY(point.y),
      value: point.value,
      info: point.info
    };
  };

  var permuteArray = function(arr, permute){
    return (
      arr
        .map(
          function(el, ix){
          return [el, permute(ix)];
          })
        .sort(
          function(l,r){
          return l[1]-r[1];
          })
        .map(
          function(el){
          return el[0];
          }
      )
    );
  };

  return {
    dataSeries: data.dataSeries.map(
        function(series){
          return {
            info: series.info,
            data: series.data.map(permutePoint)
          };
        }),
    xAxisCategories: permuteArray(data.xAxisCategories, permuteX),
    yAxisCategories: permuteArray(data.yAxisCategories, permuteY)
  };
}

var _axisElementsForFilteredDataSeries = function(axis, conditionPerSeries,conditionPerPoint,dataSeries){
  return (
    dataSeries
    .filter(conditionPerSeries)
    .map((e)=>
      e.data)
    .reduce((l,r)=>
      l.concat(r))
    .filter(conditionPerPoint)
    .map((e)=>
      e[axis])
    .filter((e,ix,self)=>
      self.indexOf(e) ===ix)
    .sort((l,r) =>
      l-r)
  );
}

var _filterHeatmapData = function(keepSeries, keepPoint, data){
  let allXs = _axisElementsForFilteredDataSeries("x",keepSeries,keepPoint,data.dataSeries);
  let allYs = _axisElementsForFilteredDataSeries("y",keepSeries,keepPoint,data.dataSeries);

  let newDataSeries =
  data.dataSeries
  .map(function(series, ix){
      return (
          keepSeries(series,ix) ? series.data.filter(keepPoint) : []
      );
  })
  .map(function(series){
      return (
          series
              .map(function(point){
                  return {
                      x: allXs.indexOf(point.x),
                      y: allYs.indexOf(point.y),
                      value: point.value,
                      info: point.info
                  };
              })
              .filter(function(point){
                  return point.x>-1 && point.y>-1
              })
          );
  });
  return {
    dataSeries: data.dataSeries.map(function(e, ix){
      return {
        info: e.info,
        data: newDataSeries[ix]
      }
    }),
    xAxisCategories: data.xAxisCategories.filter(function(e,ix){
        return allXs.indexOf(ix)>-1
    }),
    yAxisCategories: data.yAxisCategories.filter(function(e,ix){
        return allYs.indexOf(ix)>-1
    })
  }
}

var filterHeatmapDataByDataSeries = function(booleanVectorOfDataSeriesToKeep, data){
  return _filterHeatmapData(
    (series,ix)=>booleanVectorOfDataSeriesToKeep[ix],
    (point)=>true,
    data
  );
}

var filterHeatmapDataByCoexpressionIndex = function(maxIndex, data){
  return _filterHeatmapData(
    (series,ix)=>true,
    (point)=>{return point.info.index<=maxIndex},
    data
  );
}

exports.group = groupValuesByProvidedColumnGrouping;
var groupValuesByProvidedColumnGrouping = function(grouping, data){
  //new position is the lowest position
  //provide "other" grouping
  //put "aggregated points" into an info object if there was more than one
  //there should be a grouping that doesn't do anything
  return data;
}



exports.filterByIndex = filterHeatmapDataByCoexpressionIndex;
exports.filterByDataSeries = filterHeatmapDataByDataSeries;
exports.order = orderHeatmapData;

exports.manipulate = function(args, data){
  return (
    filterHeatmapDataByCoexpressionIndex(args.maxIndex,
      filterHeatmapDataByDataSeries(args.dataSeriesToKeep,
        orderHeatmapData(args.ordering,
           data
        )
      )
    )
  );
}
