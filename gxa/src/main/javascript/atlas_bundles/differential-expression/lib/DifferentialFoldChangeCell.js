'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactTooltip = require('react-tooltip');

var _reactTooltip2 = _interopRequireDefault(_reactTooltip);

require('./DifferentialFoldChangeCell.css');

var _DifferentialFoldChangeCellInfo = require('./tooltip/DifferentialFoldChangeCellInfo');

var _DifferentialFoldChangeCellInfo2 = _interopRequireDefault(_DifferentialFoldChangeCellInfo);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var DifferentialCell = function DifferentialCell(_ref) {
  var colour = _ref.colour,
      foldChange = _ref.foldChange,
      pValue = _ref.pValue,
      tStat = _ref.tStat,
      displayLevels = _ref.displayLevels,
      id = _ref.id;

  var tooltipId = id + '_foldchange';
  var tdStyle = {
    border: '4px solid ' + colour,
    background: displayLevels ? 'none' : colour
  };
  return _react2.default.createElement(
    'td',
    { 'data-tip': true, 'data-for': tooltipId, className: 'gxaDifferentialCell', style: tdStyle },
    _react2.default.createElement(
      'div',
      { className: displayLevels ? '' : 'hidden' },
      foldChange
    ),
    _react2.default.createElement(
      _reactTooltip2.default,
      { id: tooltipId, type: 'light', className: 'gxaDifferentialResultsTooltip' },
      _react2.default.createElement(_DifferentialFoldChangeCellInfo2.default, { pValue: pValue,
        tStatistic: tStat,
        foldChange: foldChange })
    )
  );
};

DifferentialCell.propTypes = _extends({}, _DifferentialFoldChangeCellInfo2.default.propTypes, {
  colour: _propTypes2.default.string,
  displayLevels: _propTypes2.default.bool.isRequired,
  id: _propTypes2.default.string.isRequired
});

exports.default = DifferentialCell;