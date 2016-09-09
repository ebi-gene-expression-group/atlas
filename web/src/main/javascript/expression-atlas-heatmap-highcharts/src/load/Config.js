"use strict";

var genomeBrowserTemplate = require('./genomeBrowserTemplate.js');
var capitalizeFirstLetter = function(str){
  return !str? str: str.charAt(0).toUpperCase() + str.substr(1);
};

var _introductoryMessage= function(isMultiExperiment, profiles) {
  var shownRows = profiles.rows.length,
      totalRows = profiles.searchResultTotal;

  var what =
      (isMultiExperiment ? 'experiment' : 'gene') +
      (totalRows > 1 ? 's' : '');

  return 'Showing ' + shownRows + ' ' +
      (totalRows === shownRows ? what + ':' : 'of ' + totalRows + ' ' + what + ' found:');
};

var geneURL = function(config){
  return "/query" +
  "?geneQuery=" + config.geneQuery +
  "&conditionQuery=" + config.conditionQuery +
  "&organism=" + config.species;
}

var getConfig=function(setupConfig,data){
  var config = {
    geneQuery: data.config.geneQuery,
    atlasBaseURL: setupConfig.atlasBaseURL,
    isExperimentPage: setupConfig.isExperimentPage,
    isMultiExperiment: setupConfig.isMultiExperiment,
    isReferenceExperiment: setupConfig.isReferenceExperiment,
    isDifferential: setupConfig.isDifferential,
    introductoryMessage: _introductoryMessage(setupConfig.isMultiExperiment,data.profiles),
    xAxisLegendName: capitalizeFirstLetter(data.config.columnType) || "Experimental condition",
    yAxisLegendName: setupConfig.isExperimentPage ? "Gene name": "Experiment",
  };
  //See in heatmap-data.jsp which thirteen properties this config is populated with.
  Object.assign(config, data.config);
  Object.assign(config, {geneURL: geneURL(config)});
  Object.assign(config,{genomeBrowserTemplate: setupConfig.isExperimentPage? genomeBrowserTemplate(config):""});

  return Object.freeze(config);
};


module.exports = getConfig;
