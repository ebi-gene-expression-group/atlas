"use strict";
/*
This module returns functions that take in labels of rows/columns,
and return content as it should appear in tooltip.
*/
//*------------------------------------------------------------------*
var React = require('react');

require('./Tooltips.css');

//*------------------------------------------------------------------*

var createColumnLabelTooltipRenderer = function(heatmapConfig, xAxisCategories){
  if(!heatmapConfig.isExperimentPage){
    return ()=>null;
  }

  var tooltipDataPerHeader = {};
  for(var i = 0; i< xAxisCategories.length; i++){
    if(xAxisCategories[i].info.tooltip){
      tooltipDataPerHeader[xAxisCategories[i].label]=xAxisCategories[i].info.tooltip;
    }
  }
  Object.freeze(tooltipDataPerHeader);

  var Tooltip =
    heatmapConfig.isDifferential
    ? require('./ContrastTooltip.jsx')
    : require('./FactorTooltip.jsx');

  return function(columnLabel){
    return (
      tooltipDataPerHeader.hasOwnProperty(columnLabel)
      ? <Tooltip {...tooltipDataPerHeader[columnLabel]}/>
      : null
    );
  }
}

//TODO consider what the interface of this function should be.
module.exports = function(heatmapConfig, xAxisCategories){
  return {
    row: (rowLabel)=>(<div>{"Row:" + rowLabel}</div>),
    column: createColumnLabelTooltipRenderer(heatmapConfig, xAxisCategories),
    point: ()=>{}
  }
}
