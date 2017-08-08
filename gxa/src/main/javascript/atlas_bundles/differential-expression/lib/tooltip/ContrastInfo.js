'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var ContrastInfoPropertyRow = function ContrastInfoPropertyRow(_ref) {
  var testValue = _ref.testValue,
      referenceValue = _ref.referenceValue,
      contrastPropertyType = _ref.contrastPropertyType,
      propertyName = _ref.propertyName;

  if (!testValue && !referenceValue) {
    return null;
  }

  var style = {
    whiteSpace: 'normal',
    fontWeight: contrastPropertyType === 'FACTOR' ? 'bold' : '',
    color: contrastPropertyType === 'FACTOR' ? '' : 'grey'
  };

  return _react2.default.createElement(
    'tr',
    { key: contrastPropertyType + '_' + propertyName },
    _react2.default.createElement(
      'td',
      { style: style },
      propertyName
    ),
    _react2.default.createElement(
      'td',
      { style: style },
      testValue
    ),
    _react2.default.createElement(
      'td',
      { style: style },
      referenceValue
    )
  );
};

ContrastInfoPropertyRow.propTypes = {
  contrastPropertyType: _react2.default.PropTypes.string,
  propertyName: _react2.default.PropTypes.string.isRequired,
  referenceValue: _react2.default.PropTypes.string.isRequired,
  testValue: _react2.default.PropTypes.string.isRequired
};

var ContrastInfo = function ContrastInfo(_ref2) {
  var experimentDescription = _ref2.experimentDescription,
      contrastDescription = _ref2.contrastDescription,
      testReplicates = _ref2.testReplicates,
      referenceReplicates = _ref2.referenceReplicates,
      properties = _ref2.properties;
  return _react2.default.createElement(
    'div',
    null,
    _react2.default.createElement(
      'div',
      { style: { fontWeight: 'bold', color: 'blue', textAlign: 'center' } },
      experimentDescription
    ),
    _react2.default.createElement(
      'div',
      { style: { textAlign: 'center' } },
      contrastDescription
    ),
    _react2.default.createElement(
      'table',
      null,
      _react2.default.createElement(
        'thead',
        null,
        _react2.default.createElement(
          'tr',
          null,
          _react2.default.createElement(
            'th',
            null,
            'Property'
          ),
          _react2.default.createElement(
            'th',
            null,
            'Test value (N=',
            testReplicates,
            ')'
          ),
          _react2.default.createElement(
            'th',
            null,
            'Reference value (N=',
            referenceReplicates,
            ')'
          )
        )
      ),
      _react2.default.createElement(
        'tbody',
        null,
        properties.map(function (property) {
          return _react2.default.createElement(ContrastInfoPropertyRow, _extends({ key: property.propertyName }, property));
        })
      )
    )
  );
};

ContrastInfo.proptypes = {
  experimentDescription: _propTypes2.default.string,
  contrastDescription: _propTypes2.default.string,
  testReplicates: _propTypes2.default.number,
  referenceReplicates: _propTypes2.default.number,
  properties: _propTypes2.default.arrayOf(_react2.default.PropTypes.shape(ContrastInfoPropertyRow.propTypes))
};

exports.default = ContrastInfo;