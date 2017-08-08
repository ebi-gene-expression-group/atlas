'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.render = exports.default = undefined;

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactDom = require('react-dom');

var _reactDom2 = _interopRequireDefault(_reactDom);

var _experimentPage = require('./experimentPage.js');

var _experimentPage2 = _interopRequireDefault(_experimentPage);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, container) {
    _reactDom2.default.render(_react2.default.createElement(_experimentPage2.default, options), document.getElementById(container));
};

exports.default = _experimentPage2.default;
exports.render = render;