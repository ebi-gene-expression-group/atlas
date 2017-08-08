'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactDom = require('react-dom');

var _reactDom2 = _interopRequireDefault(_reactDom);

var _DifferentialRouterLoader = require('./DifferentialRouterLoader');

var _DifferentialRouterLoader2 = _interopRequireDefault(_DifferentialRouterLoader);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(props, target) {
  _reactDom2.default.render(_react2.default.createElement(_DifferentialRouterLoader2.default, props), document.getElementById(target));
};

exports.render = render;