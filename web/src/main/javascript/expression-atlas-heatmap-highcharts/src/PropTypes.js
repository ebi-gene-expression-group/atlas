"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var validate = require('react-prop-types-check');

//*------------------------------------------------------------------*

//*------------------------------------------------------------------*


var PointPropType = React.PropTypes.shape({
  x: React.PropTypes.number.isRequired,
  y: React.PropTypes.number.isRequired,
  value: React.PropTypes.number.isRequired,
  info: React.PropTypes.object.isRequired
});

var PointsInDataSeriesPropType = React.PropTypes.arrayOf(React.PropTypes.arrayOf(PointPropType));

var DataSeriesProps = {
  info: React.PropTypes.shape({
    colour: React.PropTypes.string.isRequired,
    name: React.PropTypes.string.isRequired
  }),
  data: React.PropTypes.arrayOf(PointPropType).isRequired
};

var validateDataSeries = function(dataSeries){
  dataSeries.forEach(function(series){
    validate(series, DataSeriesProps);
  });
};

var AxisCategoriesPropType = React.PropTypes.arrayOf(
    React.PropTypes.shape({
      id: React.PropTypes.string, // ontology ID can be missing for x axis
      label: React.PropTypes.string.isRequired,
      info: React.PropTypes.shape({
        trackId:React.PropTypes.string,
        tooltip: React.PropTypes.object
      }).isRequired
    })
  ).isRequired;


var HeatmapDataPropType = function(props, propName, componentName){
  var heatmapData = props[propName];
  var possiblyError = validateDataSeries(heatmapData.dataSeries);
  if(possiblyError!== undefined){
    return possiblyError;
  }

  var width = heatmapData.xAxisCategories.length;
  var height = heatmapData.yAxisCategories.length;

  for(var i = 0; i < heatmapData.dataSeries.length; i++){
      for(var j = 0; j < heatmapData.dataSeries[i].data.length; j++){
          var point = heatmapData.dataSeries[i].data[j];
          var x = point.x;
          var y = point.y;
          if(x < 0 || y < 0 || x >= width || y >= height){
              return new Error("Point with coordinates outside range:" + x+","+y);
          }
      }
  }


};

var OrderingsPropType = function(props, propName, componentName){
  var orderings = props[propName];

  var isPermutation = function(arr){
      return (
          [].concat(arr)
          .sort(function(a,b){
              return a-b;
          })
          .map(function(el,ix){
              return el===ix;
          })
          .reduce(function(l,r){
              return l&&r;
          },true)
      );
  };

  if(!orderings.hasOwnProperty("Default")){
      return new Error("Default ordering missing!");
  }

  for(var orderingName in orderings){
      if(orderings.hasOwnProperty(orderingName)){
          var ordering = orderings[orderingName];

          if(!isPermutation(ordering.columns)){
              return new Error("Column ordering invalid in "+orderingName);
          }
          if(!isPermutation(ordering.rows)){
              return new Error("Row ordering invalid in "+orderingName);
          }
      }
  }
};

var LoadResultPropType = React.PropTypes.shape({
  heatmapConfig: React.PropTypes.object.isRequired,
  colorAxis : React.PropTypes.object,
  orderings: OrderingsPropType,
  heatmapData : HeatmapDataPropType
})

var FormatterPropType = function(props,propName){
  var f = props[propName];
  if(typeof f === 'undefined'){
    return new Error(propName+" formatter missing");
  } else if (typeof f !== 'function' || f.name !== 'Formatter'){
    return new Error(propName+" formatter not correctly created. See the main method of TooltipFormatter.jsx .");
  }
}

var propsForSelectionDropdown = {
    available: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
    current: React.PropTypes.string.isRequired,
    onSelect: React.PropTypes.func.isRequired
};

module.exports = {
validateDataSeries : validateDataSeries,
PointsInDataSeries : PointsInDataSeriesPropType,
HeatmapData : HeatmapDataPropType,
LoadResult: LoadResultPropType,
AxisCategories : AxisCategoriesPropType,
Formatter : FormatterPropType,
SelectionDropdown: propsForSelectionDropdown
};
