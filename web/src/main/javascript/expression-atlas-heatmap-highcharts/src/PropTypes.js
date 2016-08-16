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
  colour: React.PropTypes.string.isRequired,
  name: React.PropTypes.string.isRequired,
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
      label: React.PropTypes.string.isRequired
    })
  ).isRequired;


var HeatmapDataPropType = React.PropTypes.objectOf(
    function(heatmapData){
    validateDataSeries(heatmapData.dataSeries);

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

    if(!heatmapData.orderings.hasOwnProperty("Default")){
        return new Error("Default ordering missing!");
    }

    for(var orderingName in heatmapData.orderings){
        if(heatmapData.orderings.hasOwnProperty(orderingName)){
            var ordering = heatmapData.orderings[orderingName];

            if(ordering.columns.length!== width || !isPermutation(ordering.columns)){
                return new Error("Column ordering invalid in "+orderingName);
            }
            if(ordering.rows.length!==height || ! isPermutation(ordering.rows)){
                return new Error("Row ordering invalid in "+orderingName);
            }
        }
    }
  }
);

var FormatterPropType = function(props,propName){
  var f = props[propName];
  if(typeof f === 'undefined'){
    return new Error(propName+" formatter missing");
  } else if (typeof f !== 'function' || f.name !== 'Formatter'){
    return new Error(propName+" formatter not correctly created. See the main method of TooltipFormatter.jsx .");
  }
}

exports.validateDataSeries = validateDataSeries;
exports.PointsInDataSeries = PointsInDataSeriesPropType;
exports.HeatmapData = HeatmapDataPropType;
exports.AxisCategories = AxisCategoriesPropType;
exports.Formatter = FormatterPropType;
