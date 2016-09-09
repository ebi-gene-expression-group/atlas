var assert = require('assert');
var assertPropTypes = require('./assert.js');

var subject = require("../src/load/main.js");



describe('Experiment page baseline one gene with coexpressions', function() {
  var data = require("./data/experimentPageBaselineOneGeneWithCoexpressions.js");

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
        .sort()
        ,[0,1,2]
      );
    })
  });
});

describe('Gene page baseline one row', function() {
  var data = require("./data/genesetPageOneRow.js");

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
