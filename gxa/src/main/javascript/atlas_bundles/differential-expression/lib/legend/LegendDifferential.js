'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _LegendRow = require('./LegendRow');

var _LegendRow2 = _interopRequireDefault(_LegendRow);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var LegendDifferential = function LegendDifferential(_ref) {
  var minDownLevel = _ref.minDownLevel,
      maxDownLevel = _ref.maxDownLevel,
      minUpLevel = _ref.minUpLevel,
      maxUpLevel = _ref.maxUpLevel;
  return _react2.default.createElement(
    'div',
    { className: 'row column expanded' },
    _react2.default.createElement(
      'div',
      { style: { display: 'table', width: '100%', borderSpacing: '4px' } },
      isNaN(minDownLevel) && isNaN(maxDownLevel) ? null : _react2.default.createElement(_LegendRow2.default, { lowExpressionLevel: _react2.default.createElement(
          'span',
          null,
          minDownLevel
        ),
        highExpressionLevel: _react2.default.createElement(
          'span',
          null,
          maxDownLevel
        ),
        lowValueColour: '#C0C0C0',
        highValueColour: '#0000FF' }),
      isNaN(minUpLevel) && isNaN(maxUpLevel) ? null : _react2.default.createElement(_LegendRow2.default, { lowExpressionLevel: _react2.default.createElement(
          'span',
          null,
          minUpLevel
        ),
        highExpressionLevel: _react2.default.createElement(
          'span',
          null,
          maxUpLevel
        ),
        lowValueColour: '#FFAFAF',
        highValueColour: '#FF0000' })
    )
  );
};

LegendDifferential.propTypes = {
  minDownLevel: _propTypes2.default.number.isRequired,
  maxDownLevel: _propTypes2.default.number.isRequired,
  minUpLevel: _propTypes2.default.number.isRequired,
  maxUpLevel: _propTypes2.default.number.isRequired
};

exports.default = LegendDifferential;