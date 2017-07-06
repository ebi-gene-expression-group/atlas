'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.render = undefined;

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactDom = require('react-dom');

var _reactDom2 = _interopRequireDefault(_reactDom);

var _experimentPage = require('./experimentPage.jsx');

var _experimentPage2 = _interopRequireDefault(_experimentPage);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, target) {
    _reactDom2.default.render(_react2.default.createElement(_experimentPage2.default, options), document.getElementById(target));
};

exports.render = render;
// export default function ({atlasUrl, experimentAccession, container}) {
//     ReactDOM.render(
//         <ExperimentPage atlasUrl={atlasUrl}
//                         experimentAccession={experimentAccession}/>,
//         typeof container === `string` ? document.getElementById(container) : container)
// };