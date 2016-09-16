"use strict";
/*
This module returns functions that take in labels of rows/columns,
and return content as it should appear in tooltip.
*/
//*------------------------------------------------------------------*
var React = require('react');

var GeneTooltip = require('./GeneTooltip.jsx');
require('./Tooltips.less');

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
var createRowLabelTooltipRenderer = function(heatmapConfig, yAxisCategories){
  //We have the labels, but we need the indentifiers to do lookups
  var identifierPerLabel = {};
  for(var i =0; i<yAxisCategories.length ; i++){
    identifierPerLabel[yAxisCategories[i].label]=yAxisCategories[i].id;
  }
  Object.freeze(identifierPerLabel);

  var resultCache={};
  return function(rowLabel){
    if(!identifierPerLabel.hasOwnProperty(rowLabel)){
      return null;
    }
    var bioentityIdentifier = identifierPerLabel[rowLabel];
    return (
      resultCache.hasOwnProperty(bioentityIdentifier)
      ? <GeneTooltip
        key={bioentityIdentifier}
        atlasBaseURL={heatmapConfig.atlasBaseURL}
        label={rowLabel}
        id={bioentityIdentifier}
        data={resultCache[bioentityIdentifier]}/>
      : <GeneTooltip
        key={bioentityIdentifier}
        atlasBaseURL={heatmapConfig.atlasBaseURL}
        label={rowLabel}
        id={bioentityIdentifier}
        onAjaxSuccessfulCacheResult={(result)=>{resultCache[bioentityIdentifier]=result}}/>
    )
  }
}

//TODO consider what the interface of this function should be.
module.exports = function(heatmapConfig, xAxisCategories, yAxisCategories){
  return {
    row: createRowLabelTooltipRenderer(heatmapConfig, yAxisCategories),
    column: createColumnLabelTooltipRenderer(heatmapConfig, xAxisCategories),
    point: ()=>{}
  }
}
