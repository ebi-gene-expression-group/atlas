'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var DisplayLevelsButton = function DisplayLevelsButton(_ref) {
  var displayLevels = _ref.displayLevels,
      onClick = _ref.onClick;
  return _react2.default.createElement(
    'a',
    { className: 'button', onClick: onClick },
    displayLevels ? _react2.default.createElement(
      'span',
      null,
      'Hide log',
      _react2.default.createElement(
        'sub',
        null,
        '2'
      ),
      '-fold change'
    ) : _react2.default.createElement(
      'span',
      null,
      'Display log',
      _react2.default.createElement(
        'sub',
        null,
        '2'
      ),
      '-fold change'
    )
  );
};

DisplayLevelsButton.propTypes = {
  displayLevels: _propTypes2.default.bool.isRequired,
  onClick: _propTypes2.default.func.isRequired
};

exports.default = DisplayLevelsButton;