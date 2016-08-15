"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var validate = require('react-prop-types-check');

//*------------------------------------------------------------------*

//*------------------------------------------------------------------*

var colourStopsPropType = React.PropTypes.arrayOf(function(colourStops){
  return colourStops.map(function(stop){
    if(! (typeof stop[0] === 'number' && stop[0]>=0 && stop[0]<=1)){
      return new Error("Stop values should be floats between 0 and 1: http://api.highcharts.com/highmaps#colorAxis.stops");
    }
    if (! (typeof stop[1] === "string")){
      return new Error("Stop names should be colours,not: "+stop[1]);
    }
    return null;
  })
  .filter(function(e){return e})
  .concat([null])
  [0];
});


var _colourStops = function(){
  var sortfn = function(l,r){return l-r};
  var values = this.props.heatmapData.dataSeries.map(
    function(series){
      return series.data.map(function(point){
        return point.value;
      })
    }
  );
  var sorted = [].concat.apply([], values);
  sorted.sort(sortfn);
  var min = sorted[0];
  var range = sorted[sorted.length -1]-min;
  var medians = values.map(
    function(seriesValues){
      return seriesValues.sort(sortfn)[Math.floor(seriesValues.length/2)];
    }
  );
  values = undefined;
  return (
    this.props.heatmapData.dataSeries
    .map(function(e,ix,self){
        return [(medians[ix]-min)/range || (ix==0? 0 :ix ==self.length-1 ? 1: (medians[ix-1]+medians[ix+1] - 2*min)/(2*range)), e.colour]
      })
    .map(function(e,ix,self){
      return (
        ix===self.length -1 && ! e[0]? [1,e[1]] : e
      )
    })
  );
};

var getColorAxisFromDataSeries = function(dataSeries){
  return {
            stops: [
                [0, '#3060cf'],
                [0.5, '#fffbbc'],
                [0.99, '#c4463a']
            ]
        };
}

module.exports = getColorAxisFromDataSeries;
