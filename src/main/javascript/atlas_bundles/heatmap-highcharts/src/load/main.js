"use strict";

//*------------------------------------------------------------------*

var Config = require('./Config.js');
var Orderings = require('./Orderings.js');
var ColorAxis = require('./ColorAxis.js');
var Data = require('./Data.js');
//*------------------------------------------------------------------*

var _allRows = function(data){
  return (
    [].concat.apply(
      data.profiles.rows,
      (data.jsonCoexpressions || [])
      .map(function(coex) {
        return (coex.jsonProfiles&&coex.jsonProfiles.rows? coex.jsonProfiles.rows:[]).map(function(row, ix) {
          return Object.assign(row, {
            coexpressionOfGene: {
              id: coex.geneId,
              name: coex.geneName,
              index: ix
            }
          })
        })
      }))
    );
}
var get = function(setupConfig,payload){
  var config = Config(setupConfig, payload);
  var rows = _allRows(payload);
  var columnHeaders = payload.columnHeaders;

  var data = Data(config,rows,columnHeaders, payload.columnGroupings);

  return {
    heatmapConfig: config,
    colorAxis : ColorAxis(config,data.dataSeries),
    orderings: Orderings(config,rows,columnHeaders),
    heatmapData : data
  }
};

module.exports = get;
