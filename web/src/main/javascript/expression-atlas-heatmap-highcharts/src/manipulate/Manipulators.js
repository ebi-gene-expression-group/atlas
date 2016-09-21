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

var groupValuesByProvidedColumnGrouping = function(grouping, data){
  var indexesPerGroup =
    [].concat.apply([],
      data
      .xAxisCategories
      .map(function(e,ix){
        var groups =
          [].concat.apply([],
            e.info.groupings
            .filter((g)=>g.name ==grouping)
            .map((g)=>g.values.map((value)=>value.label))
            .concat([[]])
            [0]
          )
          .map((group=>[group,ix]));
        return (
          groups.length ? groups : [[e.label,ix]]
        )
      })
    );

  var _hasSmallerNonunique = function(xs,ys){
    //xs, ys sorted
    var _xs = xs.filter((x)=>ys.indexOf(x)==-1);
    var _ys = ys.filter((y)=>xs.indexOf(y)==-1);
    return (
      !_xs.length || !_ys.length
      ? -_xs.length*xs.length + _ys.length*ys.length
      :_xs[0]-_ys[0]
    )
  };
  var groupsAndTheirIndices =
    indexesPerGroup
    .map((e)=>e[0])
    .filter((e,ix,self)=>self.indexOf(e)==ix)
    .map(function(group){
      return [
        group,
        indexesPerGroup
        .filter((e)=>e[0]==group)
        .map((e)=>e[1])
        .sort((l,r)=>l-r)
      ]
    })
    .sort(function(l,r){
      return _hasSmallerNonunique(l[1],r[1]);
    });
  var dataSeriesPointsPerNewXAndY=
    [].concat.apply([],
      [].concat.apply([],
        data.dataSeries
        .map((dataSeries,dataSeriesIndex)=>dataSeries.data.map(function(point){
          return (
            groupsAndTheirIndices
            .map((e,ix)=>[e,ix])
            .filter((e)=>e[0][1].indexOf(point.x)>-1)
            .map(function(e){
              return {
                point: point,
                newX: e[1],
                dataSeriesIndex: dataSeriesIndex
              }
            })
          );
        }))
      )
    )
    .reduce(function(acc,e){
      var key = e.newX+" "+e.point.y;
      (acc[key]= acc[key]||[]).push(e)
      return acc;
    }, {});

  var newDataSeries=
    Object.keys(dataSeriesPointsPerNewXAndY)
    .map((k)=>dataSeriesPointsPerNewXAndY[k])
    .filter((e)=>e.length) //should not happen?
    .map(function(points){
      points.sort(function(l,r){
        return l.point.value - r.point.value;
      });
      return [
        points[0].dataSeriesIndex,
        Object.assign({},
          points[0].point,
          {
            x:points[0].newX,
            info:
              Object.assign({},
                points[0].point.info,
                points.length >1
                ? {aggregated: points.map((e)=>e.point)}
                : {}
              )
          }
        )
      ]
    })
    .reduce(function(dataSeriesAcc,x){
      dataSeriesAcc[x[0]].data.push(x[1]);
      return dataSeriesAcc;
    },data.dataSeries.map((ds)=>{return {info:ds.info,data:[]}}))

  return {
    xAxisCategories :
      groupsAndTheirIndices
      .map(function(groupAndIndices){
        var xAxisCategoriesForThisGroup =
          data.xAxisCategories
          .filter((e,ix)=>groupAndIndices[1].indexOf(ix)>-1);
        return (
          xAxisCategoriesForThisGroup.length==1
          ? xAxisCategoriesForThisGroup[0]
          : {
              label: groupAndIndices[0],
              id: xAxisCategoriesForThisGroup.map((e)=>e.id),
              info: {
                trackId:
                  xAxisCategoriesForThisGroup
                  .map((e)=>e.info.trackId)
                  .filter((e,ix,self)=>self.indexOf(e)==ix),
                tooltip: {},
                groupings: []
                //if needed - add the values you need here
              }
            }
        );
      }),
    yAxisCategories: data.yAxisCategories,
    dataSeries:newDataSeries
  };
}


exports.group = groupValuesByProvidedColumnGrouping;
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
