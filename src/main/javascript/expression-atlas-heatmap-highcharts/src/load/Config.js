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

var _coexpressions = function(jsonCoexpressions){
  /*
  The backend code and the feature in the old heatmap were written to support coexpressions of multiple genes.
  It doesn't seem necessary, so this assumes zero or one coexpressions.
  */
  return (
    jsonCoexpressions[0]
    ? {
      coexpressedGene: jsonCoexpressions[0].geneName,
      numCoexpressionsAvailable: jsonCoexpressions[0].jsonProfiles ? jsonCoexpressions[0].jsonProfiles.rows.length : 0
    }
    : ""
  )
}
var coexpressions = function(setupConfig, data){
  return (
    setupConfig.isExperimentPage
      && data.jsonCoexpressions
      && Array.isArray(data.jsonCoexpressions)
      ? _coexpressions(data.jsonCoexpressions) : ""
  )
}

var getConfig=function(setupConfig,data){
  var config = {
    geneQuery: data.config.geneQuery,
    atlasBaseURL: setupConfig.atlasBaseURL,
    pathToFolderWithBundledResources: setupConfig.pathToFolderWithBundledResources,
    isExperimentPage: setupConfig.isExperimentPage,
    isMultiExperiment: setupConfig.isMultiExperiment,
    isReferenceExperiment: setupConfig.isReferenceExperiment,
    isDifferential: setupConfig.isDifferential,
    introductoryMessage: _introductoryMessage(setupConfig.isMultiExperiment,data.profiles),
    description: setupConfig.isExperimentPage && data.experiment && data.experiment.description ? data.experiment.description : "",
    xAxisLegendName: capitalizeFirstLetter(data.config.columnType) || "Experimental condition",
    yAxisLegendName: setupConfig.isExperimentPage ? "Gene name": "Experiment",
    coexpressions : coexpressions(setupConfig,data)
  };
  //See in heatmap-data.jsp which thirteen properties this config is populated with.
  Object.assign(config, data.config);
  Object.assign(config, {geneURL: geneURL(config)});
  Object.assign(config,{genomeBrowserTemplate: setupConfig.isExperimentPage? genomeBrowserTemplate(config):""});

  return Object.freeze(config);
};


module.exports = getConfig;
