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

var filterHeatmapDataByDataSeries = function(booleanVectorOfDataSeriesToKeep, data){
  var all_s = function(propertyToPickFromEachPoint){
    return (
      data.dataSeries
      .map((e)=>
        e.data)
      .filter((e,ix)=>
        booleanVectorOfDataSeriesToKeep[ix])
      .reduce((l,r)=>
        l.concat(r))
      .map((e)=>
        e[propertyToPickFromEachPoint])
      .filter((e,ix,self)=>
        self.indexOf(e) ===ix)
      .sort((l,r) =>
        l-r)
    );
  }.bind(this);

  var allXs = all_s("x");
  var allYs = all_s("y");

  var ds = data.dataSeries
  .map((e)=>e.data)
  .map(function(e, ix){
      return (
          booleanVectorOfDataSeriesToKeep[ix] ? e : []
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
        data: ds[ix]
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



var filterHeatmapDataByPointProperty = function(property, keep, data){

  return (
    data
  );
}

var filterHeatmapDataByCoexpressionIndex = function(maxIndex, data){
  return filterHeatmapDataByPointProperty("index", (ix)=>(ix<=maxIndex), data);
}


exports.filterByIndex = filterHeatmapDataByCoexpressionIndex;
exports.filterByDataSeries = filterHeatmapDataByDataSeries;
exports.order = orderHeatmapData;

exports.manipulate = function(args, data){
  return (
    filterHeatmapDataByDataSeries(args.dataSeriesToKeep,
      orderHeatmapData(args.ordering,
         data
      )
    )
  );
}
