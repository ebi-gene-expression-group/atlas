var subject = require('../src/manipulate/Manipulators.js');
var assert = require('assert');
var assertPropTypes = require('./assert.js');


describe('Manipulators', function() {
  var data = require('./data/genesetPageOneRow').expected;
  describe('order', function() {
    describe('by default ordering', function(){
      var result = subject.order(data.orderings["Default"], data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
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
        assertPropTypes.validateHeatmapData(result);
      });
      it('the highest column should come first', function() {
        var highest = data.xAxisCategories[7];
        assert.deepEqual(highest,result.xAxisCategories[0]);
      });
    });
  });
  describe('filterByDataSeries', function(){
    describe('keeping all series in', function(){
      var result = subject.filterByDataSeries(data.dataSeries.map((_)=>true), data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('result should not actually change', function() {
        assert.deepEqual(data.xAxisCategories,result.xAxisCategories);
        assert.deepEqual(data.yAxisCategories,result.yAxisCategories);
        assert.deepEqual(data.dataSeries,result.dataSeries);
      });
    });
    describe('dropping one of the series', function(){
      var indexFilteredOut = 1;
      var anotherIndexNotFilteredOut = 2;
      assert.ok(0 < data.dataSeries[indexFilteredOut].data.length);
      assert.ok(0 < data.dataSeries[anotherIndexNotFilteredOut].data.length);


      var result = subject.filterByDataSeries(data.dataSeries.map((_,ix)=>ix!=indexFilteredOut), data);
      it('does not change the info objects', function(){
        assert.equal(data.dataSeries[indexFilteredOut].info, result.dataSeries[indexFilteredOut].info);
        assert.equal(data.dataSeries[anotherIndexNotFilteredOut].info, result.dataSeries[anotherIndexNotFilteredOut].info);
      })
      it('should not change the size of dataset that is not being filtered out', function(){
        assert.deepEqual(data.dataSeries[anotherIndexNotFilteredOut].data.length, result.dataSeries[anotherIndexNotFilteredOut].data.length);
      })
      it('will change the indices in dataset that is not being filtered out', function(){
        assert.notDeepEqual(data.dataSeries[anotherIndexNotFilteredOut].data, result.dataSeries[anotherIndexNotFilteredOut].data);
      })
      it('should put empty data into the index filtered out', function(){
        assert.deepEqual(Object.assign(data.dataSeries[indexFilteredOut], {data:[]}),result.dataSeries[indexFilteredOut]);
      })
      it('will cause us to have less x axis labels here', function(){
        assert.notDeepEqual(data.xAxisCategories, result.xAxisCategories);
        assert.ok(data.xAxisCategories.length > result.xAxisCategories.length);

      })
    })
  });
});
