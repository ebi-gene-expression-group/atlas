'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.facetItemDataPropTypes = exports.facetDataPropTypes = undefined;

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var facetItemDataPropTypes = {
  name: _propTypes2.default.string.isRequired,
  value: _propTypes2.default.string.isRequired,
  checked: _propTypes2.default.bool.isRequired,
  disabled: _propTypes2.default.bool.isRequired
};

var facetDataPropTypes = {
  facetName: _propTypes2.default.string.isRequired,
  facetItems: _propTypes2.default.arrayOf(_propTypes2.default.shape(facetItemDataPropTypes)).isRequired
};

exports.facetDataPropTypes = facetDataPropTypes;
exports.facetItemDataPropTypes = facetItemDataPropTypes;