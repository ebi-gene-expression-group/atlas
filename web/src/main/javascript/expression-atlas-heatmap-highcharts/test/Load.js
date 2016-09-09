var assert = require('assert');
var assertPropTypes = require('./assert.js');

var subject = require("../src/load/main.js");



describe('Experiment page baseline one gene with coexpressions', function() {
  var data = require("./data/experimentPageBaselineOneGeneWithCoexpressions.js");

  var config = {
    "geneQuery": "%5B%7B%22value%22%3A%22%5B%7B%5C%22value%5C%22%3A%5C%22Sb01g006200%5C%22%2C%5C%22category%5C%22%3A%5C%22%5C%22%7D%5D%22%2C%22category%22%3A%22%22%7D%5D",
    "isExperimentPage": true,
    "isMultiExperiment": false,
    "isReferenceExperiment": false,
    "isDifferential": false,
    "xAxisLegendName": "Organism part",
    "yAxisLegendName": "Gene name",
    "species": "Sorghum bicolor",
    "ensemblDB": "plants",
    "columnType": "organism part",
  };
  describe('Returned object for data with coexpressions', function() {
    it('should have the data series format', function() {
      var result = subject(config, data.actual)
      assertPropTypes.validateLoadResult(result);
    });
    it('coexpressions should end up with the rest of the data', function() {
      var result = subject.get(data.actual, config)

      assert.ok([].concat.apply([],result.dataSeries.map((series)=>series.data)).length > 1*result.xAxisCategories.length);
    });
    it('should be the same as the JSON dump of the result', function(){
      var result = subject.get(data.actual, config)
      assert.equal(JSON.stringify(result), JSON.stringify(data.expected));
    })

    it('should have the indices that are not only zero', function(){
      var result = subject.get(data.actual, config)
      assert.deepEqual(
        [].concat.apply([],result.dataSeries.map((series)=>series.data))
        .map((point)=>point.info.index)
        .filter((el,ix,self)=>self.indexOf(el)==ix)
        .sort()
        ,[0,1,2]
      );
    })
  });
});

describe('Gene page baseline one row', function() {
  var data = require("./data/genesetPageOneRow.js");

  //The fact this needs to be included shows we could modularise our code better.
  var config = {
    "geneQuery": "%5B%7B%22value%22%3A%22zinc+finger%22%7D%5D",
    "isExperimentPage": false,
    "isMultiExperiment": true,
    "isReferenceExperiment": false,
    "isDifferential": false
  };
  describe('Returned object', function() {
    it('should have the data series format', function() {
      var result = subject.get(data.actual, config)
      assert.ifError(require('../src/PropTypes.js').validateHeatmapData(result));
    });
    it('should have one row', function() {
      var result = subject.get(data.actual, config)

      assert.equal([].concat.apply([],result.dataSeries.map((series)=>series.data)).length,result.xAxisCategories.length);
    });
    it('should be the same as the JSON dump of the result', function(){
      var result = subject.get(data.actual, config)
      assert.equal(JSON.stringify(result), JSON.stringify(data.expected));
    })
  });
});
