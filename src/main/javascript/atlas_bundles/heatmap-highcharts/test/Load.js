var assert = require('assert');
var assertPropTypes = require('./assert.js');

var subject = require("../src/load/main.js");



describe('Experiment page baseline one gene with coexpressions', function() {
  var data = require("json!./data/experimentPageBaselineOneGeneWithCoexpressions.json");

  var config = {
    "isExperimentPage": true,
    "isMultiExperiment": false,
    "isReferenceExperiment": false,
    "isDifferential": false,
    "atlasBaseUrl": "test-invalid"
  };
  describe('Returned object for data with coexpressions', function() {
    it('should have the data series format', function() {
      var result = subject(config, data.actual)
      assertPropTypes.validateLoadResult(result);
    });
    it('coexpressions should end up with the rest of the data', function() {
      var result = subject(config, data.actual)

      assert.ok([].concat.apply([],result.heatmapData.dataSeries.map((series)=>series.data)).length > 1*result.heatmapData.xAxisCategories.length);
    });
    it('should be the same as the JSON dump of the result', function(){
      var result = subject(config, data.actual)
      assert.deepEqual(data.expected,result.heatmapData);
    })

    it('should have the indices that are not only zero', function(){
      var result = subject(config, data.actual)
      assert.deepEqual(
        [].concat.apply([],result.heatmapData.dataSeries.map((series)=>series.data))
        .map((point)=>point.info.index)
        .filter((el,ix,self)=>self.indexOf(el)==ix)
        .length
        ,50
      );
    })
  });
});

describe('Gene page baseline one row', function() {
  var data = require("json!./data/genesetPageOneRow.json");

  var config = {
    "isExperimentPage": false,
    "isMultiExperiment": true,
    "isReferenceExperiment": false,
    "isDifferential": false,
    "atlasBaseUrl": "test-invalid"
  };
  describe('Returned object', function() {
    var result = subject(config, data.actual);
    it('should have the data series format', function() {
      assertPropTypes.validateLoadResult(result);
    });
    it('should have one row', function() {
      assert.equal([].concat.apply([],result.heatmapData.dataSeries.map((series)=>series.data)).length,result.heatmapData.xAxisCategories.length);
    });
    it('should be the same as the JSON dump of the result', function(){
      var result = subject(config, data.actual);
      assert.deepEqual(data.expected,result.heatmapData);
    })
  });
});
describe('Proteomics experiment page', function() {
  var data = require("json!./data/experimentPageProteomicsBaseline.json");

  var config = {
    "isExperimentPage": true,
    "isMultiExperiment": false,
    "isReferenceExperiment": false,
    "isDifferential": false,
    "atlasBaseUrl": "test-invalid"
  };
  describe('Returned object for data with coexpressions', function() {
    it('should have the data series format', function() {
      var result = subject(config, data.actual)
      assertPropTypes.validateLoadResult(result);
    });
    it('should have no coexpressions', function() {
      var result = subject(config, data.actual)
      assert.equal([].concat.apply([],result.heatmapData.dataSeries.map((series)=>series.data)).length, 1*result.heatmapData.yAxisCategories.length);
    });
    it('should be the same as the JSON dump of the result', function(){
      var result = subject(config, data.actual)
      assert.deepEqual(data.expected,result.heatmapData);
    })

    it('should have more than five column rows', function(){
      var result = subject(config, data.actual)
      assert.ok(result.heatmapData.yAxisCategories.length >5);
    })
  });
});

describe('Differential experiment page', function() {
  var dataShort = require("json!./data/experimentPageDifferentialSpecificShort.json");
  var data = require("json!./data/experimentPageDifferentialSpecific.json");

  var config = {
    "isExperimentPage": true,
    "isMultiExperiment": false,
    "isReferenceExperiment": false,
    "isDifferential": true,
    "atlasBaseUrl": "test-invalid"
  };
  describe('Returned object', function() {
    var resultShort = subject(config, dataShort.actual);
    var result = subject(config, data.actual);

    it('should have the data series format', function() {
      assertPropTypes.validateLoadResult(resultShort);
      assertPropTypes.validateLoadResult(result);
    });
    it('should be the same as the JSON dump of the result', function(){
      assert.deepEqual(dataShort.expected,resultShort.heatmapData);
      assert.deepEqual(data.expected,result.heatmapData);
    })
    it('should have different result sizes', function(){
      assert.ok(resultShort.heatmapData.yAxisCategories.length <10);
      assert.ok(result.heatmapData.yAxisCategories.length >10);
    })
    it('should have the same columns', function(){
      assert.deepEqual(result.heatmapData.xAxisCategories.length, resultShort.heatmapData.xAxisCategories.length)
      assert.deepEqual(result.heatmapData.xAxisCategories, resultShort.heatmapData.xAxisCategories)
    })
  });
});
