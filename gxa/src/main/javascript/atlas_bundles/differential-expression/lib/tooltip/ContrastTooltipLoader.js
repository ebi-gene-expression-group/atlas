'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactRefetch = require('react-refetch');

var _reactTooltip = require('react-tooltip');

var _reactTooltip2 = _interopRequireDefault(_reactTooltip);

var _urijs = require('urijs');

var _urijs2 = _interopRequireDefault(_urijs);

var _ContrastInfo = require('./ContrastInfo');

var _ContrastInfo2 = _interopRequireDefault(_ContrastInfo);

require('./DifferentialResultsTooltip.css');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TooltipLoader = function (_React$Component) {
  _inherits(TooltipLoader, _React$Component);

  function TooltipLoader(props) {
    _classCallCheck(this, TooltipLoader);

    return _possibleConstructorReturn(this, (TooltipLoader.__proto__ || Object.getPrototypeOf(TooltipLoader)).call(this, props));
  }

  _createClass(TooltipLoader, [{
    key: 'render',
    value: function render() {
      var _props = this.props,
          tooltipFetch = _props.tooltipFetch,
          id = _props.id;


      if (tooltipFetch.pending) {
        return _react2.default.createElement(
          _reactTooltip2.default,
          { id: id, type: 'light', className: 'gxaDifferentialResultsTooltip' },
          _react2.default.createElement(
            'span',
            null,
            'Loading...'
          )
        );
      } else if (tooltipFetch.rejected) {
        return _react2.default.createElement(
          _reactTooltip2.default,
          { id: id, type: 'light', className: 'gxaDifferentialResultsTooltip' },
          _react2.default.createElement(
            'span',
            null,
            'Error retrieving tooltip data: ',
            tooltipFetch.reason
          )
        );
      } else if (tooltipFetch.fulfilled) {
        return _react2.default.createElement(
          _reactTooltip2.default,
          { id: id, type: 'light', className: 'gxaDifferentialResultsTooltip' },
          _react2.default.createElement(_ContrastInfo2.default, tooltipFetch.value)
        );
      }
    }
  }]);

  return TooltipLoader;
}(_react2.default.Component);

TooltipLoader.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  tooltipUrl: _propTypes2.default.string.isRequired,
  tooltipUrlParams: _propTypes2.default.objectOf(_propTypes2.default.string).isRequired,
  id: _propTypes2.default.string.isRequired
};

exports.default = (0, _reactRefetch.connect)(function (props) {
  return {
    tooltipFetch: (0, _urijs2.default)(props.tooltipUrl, props.atlasUrl).search(props.tooltipUrlParams).toString()
  };
})(TooltipLoader);