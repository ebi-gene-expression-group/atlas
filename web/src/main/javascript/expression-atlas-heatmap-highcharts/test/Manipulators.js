var subject = require('../src/Manipulators.js');
var assert = require('assert');

describe('Manipulators', function() {
  var data = require('./data/genesetPageOneRow').expected;
  describe('order', function() {
    describe('by default ordering', function(){
      var result = subject.order(data.orderings["Default"], data);
      it('result should have the data series format', function() {
        assert.equal(require('../src/PropTypes.js').validateHeatmapData(result),undefined);
      });
      it('result should not actually change', function() {
        assert.deepEqual(data.xAxisCategories,result.xAxisCategories);
        assert.deepEqual(data.yAxisCategories,result.yAxisCategories);
        assert.deepEqual(data.dataSeries,result.dataSeries);
      });
    });
    describe('by GE ordering', function(){
      var result = subject.order(data.orderings["Gene expression rank"], data);
      it('result should have the data series format', function() {
        assert.equal(require('../src/PropTypes.js').validateHeatmapData(result),undefined);
      });
      it('the highest column should come first', function() {
        var highest = data.xAxisCategories[7];
        assert.deepEqual(highest,result.xAxisCategories[0]);
      });
    });
  });
});
