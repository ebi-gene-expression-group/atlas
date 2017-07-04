'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactRouterDom = require('react-router-dom');

var _queryString = require('query-string');

var _queryString2 = _interopRequireDefault(_queryString);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var ExperimentPage = function (_Component) {
    _inherits(ExperimentPage, _Component);

    function ExperimentPage() {
        _classCallCheck(this, ExperimentPage);

        return _possibleConstructorReturn(this, (ExperimentPage.__proto__ || Object.getPrototypeOf(ExperimentPage)).apply(this, arguments));
    }

    _createClass(ExperimentPage, [{
        key: 'render',
        value: function render() {
            return _react2.default.createElement(
                _reactRouterDom.BrowserRouter,
                null,
                _react2.default.createElement(
                    'div',
                    null,
                    _react2.default.createElement(_reactRouterDom.Route, { path: '/experiment', render: function render(props) {
                            return _react2.default.createElement(Experiment, props);
                        } })
                )
            );
        }
    }]);

    return ExperimentPage;
}(_react.Component);

ExperimentPage.propTypes = {
    atlasUrl: _propTypes2.default.string.isRequired
};

var Experiment = function (_Component2) {
    _inherits(Experiment, _Component2);

    function Experiment(props) {
        _classCallCheck(this, Experiment);

        var _this2 = _possibleConstructorReturn(this, (Experiment.__proto__ || Object.getPrototypeOf(Experiment)).call(this, props));

        _this2.state = {
            params: _queryString2.default.parse(props.location.search),
            p1: "",
            p2: ""
        };
        return _this2;
    }

    _createClass(Experiment, [{
        key: 'handleChange',
        value: function handleChange(param, item) {
            var _newparam = {};
            _newparam[param] = item.target.value;
            this.setState(_newparam);

            this.props.history.push("/experiment?" + _queryString2.default.stringify({ p1: param === "p1" ? item.target.value : this.state.p1,
                p2: param === "p2" ? item.target.value : this.state.p2 }));
        }
    }, {
        key: 'componentDidMount',
        value: function componentDidMount() {
            this.setState({
                p1: this.state.params.p1,
                p2: this.state.params.p2
            });
        }
    }, {
        key: 'render',
        value: function render() {

            return _react2.default.createElement(
                'div',
                null,
                _react2.default.createElement(
                    'h3',
                    null,
                    'Welcome to the Experiment Page'
                ),
                _react2.default.createElement(
                    'div',
                    { className: 'small-2' },
                    _react2.default.createElement(
                        'label',
                        null,
                        'Param p1 is:'
                    ),
                    _react2.default.createElement('input', { type: 'text', value: this.state.p1, onChange: this.handleChange.bind(this, "p1") }),
                    _react2.default.createElement(
                        'label',
                        null,
                        'Param p2 is:'
                    ),
                    _react2.default.createElement('input', { type: 'text', value: this.state.p2, onChange: this.handleChange.bind(this, "p2") })
                )
            );
        }
    }]);

    return Experiment;
}(_react.Component);

Experiment.propTypes = {
    props: _propTypes2.default.object
};

exports.default = ExperimentPage;