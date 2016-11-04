"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var validateDataSeries = require('../PropTypes.js').validateDataSeries;
var Colour = require("color");

//*------------------------------------------------------------------*

//*------------------------------------------------------------------*

var highlightColour = function(c){
  return (
    c.light()
    ? c.clone().lighten(0.5)
    : c.clone().saturate(0.3).darken(0.5)
  )
}

var dataClassesFromSeries = function(dataSeries){
  validateDataSeries(dataSeries);
  var xs =
    dataSeries
    .map(function(series){
      return (
        series.data.length === 0 && series.info.name === "Below cutoff"
        ? {
          data: [{value: 0.0}],
          colour: series.info.colour
        }
        : {
          data: series.data,
          colour: series.info.colour
        }
      );
    })
    .filter(function(series){
      return series.data.length>0;
    })
    .map(function(series,ix,self){
      var theseSeriesValuesSorted =
        series.data.map(function(point){
          return point.value;
        });
      theseSeriesValuesSorted.sort(function(l,r){return l-r});
      return {
        min: theseSeriesValuesSorted[0],
        minColour: ix ==0? highlightColour(Colour(self[ix].colour)): Colour(self[ix].colour).mix(Colour(self[ix-1].colour)),
        max: theseSeriesValuesSorted[theseSeriesValuesSorted.length-1],
        maxColour: ix ==self.length-1 ? highlightColour(Colour(self[ix].colour)): Colour(self[ix].colour).mix(Colour(self[ix+1].colour)),
        median: theseSeriesValuesSorted[Math.floor(series.data.length/2)],
        medianColour: Colour(self[ix].colour),
        sortedValues: theseSeriesValuesSorted
      }
    }
  );
  var needToSplit = function(x){
    return (
      x.sortedValues.length>3
      && x.sortedValues[0]!=x.sortedValues[x.sortedValues.length-1]
      && x.minColour.rgbString()!==x.maxColour.rgbString()
    );
  };

  var splitInHalf = function(x){
    return [
      {
        min:x.min,
        minColour: x.minColour,
        max:x.median,
        maxColour: x.medianColour,
        median: x.sortedValues[Math.floor(x.sortedValues.length/4)],
        medianColour: x.minColour.clone().mix(x.medianColour),
        sortedValues: x.sortedValues.slice(0, Math.floor(x.sortedValues.length/2))
      },
      {
        min:x.median,
        minColour: x.medianColour,
        max:x.max,
        maxColour: x.maxColour,
        median: x.sortedValues[Math.floor(3* x.sortedValues.length/4)],
        medianColour: x.medianColour.clone().mix(x.maxColour),
        sortedValues: x.sortedValues.slice(Math.floor(x.sortedValues.length/2))
      }
    ];
  };
  var l = Number.MIN_VALUE;
  var L = xs.length;
  while(l<L){
    xs = [].concat.apply([], xs.map(function(x){
      if(needToSplit(x)){
        return splitInHalf(x);
      } else {
        return [x];
      }
    }));
    l = L;
    L = xs.length;
  }

  return (
    xs.map(function(x){
      return {
        from: x.min,
        to: x.max,
        color: x.medianColour.hexString()
      };
    })
  );
};

var getColorAxisFromDataSeries = function(config, dataSeries){
  return (
    config.isExperimentPage
    ? {
      dataClasses: dataClassesFromSeries(dataSeries)
      }
    : null
  )
}

module.exports = getColorAxisFromDataSeries;
