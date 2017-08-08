'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _expressionAtlasNumberFormat = require('expression-atlas-number-format');

var _expressionAtlasNumberFormat2 = _interopRequireDefault(_expressionAtlasNumberFormat);

require('./DifferentialResultsTooltip.css');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var DifferentialFoldChangeCellInfo = function DifferentialFoldChangeCellInfo(_ref) {
  var pValue = _ref.pValue,
      tStat = _ref.tStat,
      foldChange = _ref.foldChange;
  return _react2.default.createElement(
    'table',
    null,
    _react2.default.createElement(
      'thead',
      null,
      _react2.default.createElement(
        'tr',
        null,
        pValue && _react2.default.createElement(
          'th',
          null,
          'Adjusted ',
          _react2.default.createElement(
            'em',
            null,
            'p'
          ),
          '-value'
        ),
        tStat && _react2.default.createElement(
          'th',
          null,
          _react2.default.createElement(
            'em',
            null,
            't'
          ),
          '-statistic'
        ),
        _react2.default.createElement(
          'th',
          null,
          'Log',
          _react2.default.createElement(
            'sub',
            null,
            '2'
          ),
          '-fold change'
        )
      )
    ),
    _react2.default.createElement(
      'tbody',
      null,
      _react2.default.createElement(
        'tr',
        null,
        pValue && _react2.default.createElement(
          'td',
          null,
          _react2.default.createElement(_expressionAtlasNumberFormat2.default, { value: pValue })
        ),
        tStat && _react2.default.createElement(
          'td',
          null,
          Math.floor(tStat * 1e4) / 1e4
        ),
        _react2.default.createElement(
          'td',
          null,
          foldChange
        )
      )
    )
  );
};

DifferentialFoldChangeCellInfo.propTypes = {
  foldChange: _propTypes2.default.number.isRequired,
  pValue: _propTypes2.default.number,
  tStat: _propTypes2.default.number
};

exports.default = DifferentialFoldChangeCellInfo;