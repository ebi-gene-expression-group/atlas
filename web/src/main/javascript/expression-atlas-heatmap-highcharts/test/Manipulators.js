var subject = require('../src/manipulate/Manipulators.js');
var assert = require('assert');
var assertPropTypes = require('./assert.js');


describe('Manipulators', function() {
  var data = require('./data/genesetPageOneRow').expected;
  var orderings = {"Default":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Alphabetical order":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Gene expression rank":{"columns":[7,5,8,6,4,3,0,1,2],"rows":[0]}};
  describe('order', function() {
    describe('by default ordering', function(){
      var result = subject.order(orderings["Default"], data);
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
      var result = subject.order(orderings["Gene expression rank"], data);
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
  describe('filterByIndex', function(){
    describe('passing the code through non-experiment page data', function(){
      var geneSetPageData = require('./data/genesetPageOneRow').expected;
      var result = subject.filterByIndex(0,geneSetPageData);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('leaves it as it is with default index value', function (){
        assert.deepEqual(geneSetPageData, result);
      });
      it('leaves it as it is with random non-zero index value', function (){
        assert.deepEqual(geneSetPageData, subject.filterByIndex(5,geneSetPageData));
      });
      it('filters all data out with a negative index value', function (){
        var result = subject.filterByIndex(-1,geneSetPageData);
        assert.deepEqual([], [].concat.apply([], result.dataSeries.map((series)=>series.data)));
      });
    });
  })
});
