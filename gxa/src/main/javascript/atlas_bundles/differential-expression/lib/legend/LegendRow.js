'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

require('./gxaGradient.css');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var LegendRow = function LegendRow(_ref) {
  var lowValueColour = _ref.lowValueColour,
      highValueColour = _ref.highValueColour,
      lowExpressionLevel = _ref.lowExpressionLevel,
      highExpressionLevel = _ref.highExpressionLevel;

  var spanStyle = {
    backgroundImage: 'linear-gradient(to right, ' + lowValueColour + ', ' + highValueColour + ')'
  };

  return _react2.default.createElement(
    'div',
    { style: { display: 'table-row' } },
    _react2.default.createElement(
      'div',
      { className: 'gxaDiffLegendLevelCell' },
      lowExpressionLevel
    ),
    _react2.default.createElement(
      'div',
      { className: 'gxaDiffLegendGradientCell' },
      _react2.default.createElement('span', { className: 'gxaDiffLegendGradient', style: spanStyle })
    ),
    _react2.default.createElement(
      'div',
      { className: 'gxaDiffLegendLevelCell' },
      highExpressionLevel
    )
  );
};

LegendRow.propTypes = {
  lowValueColour: _propTypes2.default.string.isRequired,
  highValueColour: _propTypes2.default.string.isRequired,
  lowExpressionLevel: _propTypes2.default.element.isRequired,
  highExpressionLevel: _propTypes2.default.element.isRequired
};

exports.default = LegendRow;