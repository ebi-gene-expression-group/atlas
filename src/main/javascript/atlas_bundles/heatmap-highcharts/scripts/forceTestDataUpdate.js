#!/usr/bin/env node

var fs = require('fs');
var request = require('request');
var path = require('path');

var subject = require("../src/load/main.js");

var dataDir="../test/data/"
dataFiles = [
  { name: "experimentPageBaselineOneGeneWithCoexpressions.json",
    url: "http://localhost:8080/gxa/json/experiments/E-MTAB-3028?geneQuery=%5B%7B%22value%22%3A%22TUBA3%22%7D%5D",
    config:{
      "isExperimentPage": true,
      "isMultiExperiment": false,
      "isReferenceExperiment": false,
      "isDifferential": false,
      "atlasBaseUrl": "test-invalid"
    }
  },
  { name: "experimentPageProteomicsBaseline.json",
    url: "http://localhost:8080/gxa/json/experiments/E-PROT-1",
    config:{
      "isExperimentPage": true,
      "isMultiExperiment": false,
      "isReferenceExperiment": false,
      "isDifferential": false,
      "atlasBaseUrl": "test-invalid"
    }
  },
  { name: "experimentPageBaselineNonSpecific.json",
    url: "http://localhost:8080/gxa/json/experiments/E-MTAB-3871?specific=false",
    config:{
      "isExperimentPage": true,
      "isMultiExperiment": false,
      "isReferenceExperiment": false,
      "isDifferential": false,
      "atlasBaseUrl": "test-invalid"
    }
  },
  {
    name: "genesetPageOneRow.json",
    url:"http://localhost:8080/gxa/widgets/heatmap/baselineAnalytics?geneQuery=%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D&conditionQuery=%5B%5D&species=bos%20taurus&source=ORGANISM_PART",
    config:{
      "isExperimentPage": false,
      "isMultiExperiment": true,
      "isReferenceExperiment": false,
      "isDifferential": false,
      "atlasBaseUrl": "test-invalid"
    }
  },
  {
    name: "experimentPageDifferentialSpecificShort.json",
    url:"http://localhost:8080/gxa/json/experiments/E-GEOD-54705?heatmapMatrixSize=5",
    config:{
      "isExperimentPage": true,
      "isMultiExperiment": false,
      "isReferenceExperiment": false,
      "isDifferential": true,
      "atlasBaseUrl": "test-invalid"
    }
  },
  {
    name: "experimentPageDifferentialSpecific.json",
    url:"http://localhost:8080/gxa/json/experiments/E-GEOD-54705",
    config:{
      "isExperimentPage": true,
      "isMultiExperiment": false,
      "isReferenceExperiment": false,
      "isDifferential": true,
      "atlasBaseUrl": "test-invalid"
    }
  }
]

dataFiles.forEach(function(dataFile){
  request(dataFile.url, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var data = JSON.parse(body);
      result = {actual:data, expected:subject(dataFile.config, data).heatmapData};
      console.log("Updating: "+path.resolve(__dirname, dataDir+ dataFile.name));
      fs.writeFileSync(path.resolve(__dirname, dataDir+ dataFile.name), JSON.stringify(result, null, 4), 'utf8');
    } else {
      console.log("Error updating "+dataFile.name+" :"+error );
    }
  })
})
