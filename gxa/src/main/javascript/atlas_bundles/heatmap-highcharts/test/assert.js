var assert = require('assert');

var validateType = function(Type){
  return function(data){return Type({t: data}, "t", "Test")}
}

exports.validateHeatmapData = (result)=>assert.ifError(validateType(require('../src/PropTypes.js').HeatmapData)(result));
exports.validateLoadResult = (result)=>assert.ifError(validateType(require('../src/PropTypes.js').LoadResult)(result));
