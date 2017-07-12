'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactRouterDom = require('react-router-dom');

var _queryString = require('query-string');

var _queryString2 = _interopRequireDefault(_queryString);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = require('urijs');

var _urijs2 = _interopRequireDefault(_urijs);

var _singleCellTsnePlot = require('single-cell-tsne-plot');

var _singleCellTsnePlot2 = _interopRequireDefault(_singleCellTsnePlot);

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
            var _this2 = this;

            return _react2.default.createElement(
                _reactRouterDom.BrowserRouter,
                null,
                _react2.default.createElement(
                    'div',
                    null,
                    _react2.default.createElement(_reactRouterDom.Route, { path: '/', render: function render(props) {
                            return _react2.default.createElement(Experiment, _extends({}, props, { clustersData: _this2.props.clustersData }));
                        } })
                )
            );
        }
    }]);

    return ExperimentPage;
}(_react.Component);

ExperimentPage.propTypes = {
    atlasUrl: _propTypes2.default.string.isRequired,
    experimentAccession: _propTypes2.default.string.isRequired,
    clustersData: _propTypes2.default.object.isRequired
};

var Experiment = function (_Component2) {
    _inherits(Experiment, _Component2);

    function Experiment(props) {
        _classCallCheck(this, Experiment);

        var _this3 = _possibleConstructorReturn(this, (Experiment.__proto__ || Object.getPrototypeOf(Experiment)).call(this, props));

        _this3.state = {
            params: _queryString2.default.parse(props.location.search),
            geneId: "",
            clusterId: "",
            K: "",
            clustersChosen: ""
        };
        return _this3;
    }

    _createClass(Experiment, [{
        key: 'handleChange',
        value: function handleChange(param, item) {
            var _newparam = {};
            _newparam[param] = item.target.value;
            this.setState(_newparam);

            this.props.history.push("?" + _queryString2.default.stringify({
                geneId: param === "geneId" ? item.target.value : this.state.geneId,
                clusterId: param === "clusterId" ? item.target.value : this.state.clusterId
            }));
        }
    }, {
        key: 'handleOptionsChange',
        value: function handleOptionsChange(e) {
            this.setState({ clusterId: e.target.value });

            this.props.history.push("?" + _queryString2.default.stringify({
                clusterId: e.target.value
            }));
        }
    }, {
        key: 'componentDidMount',
        value: function componentDidMount() {
            this.setState({
                geneId: this.state.params.geneId,
                clusterId: this.state.params.clusterId,
                K: this.state.params.k
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
                    'Experiment Page section'
                ),
                _react2.default.createElement(
                    'div',
                    { className: 'large-6 columns' },
                    _react2.default.createElement(
                        'label',
                        null,
                        'Perplexity:'
                    ),
                    _react2.default.createElement('input', { type: 'text', value: this.state.clusterId, onChange: this.handleChange.bind(this, "clusterId") }),
                    _react2.default.createElement(
                        'h3',
                        null,
                        'Plot'
                    ),
                    _react2.default.createElement(_singleCellTsnePlot2.default, { clustersData: this.props.clustersData,
                        clusterId: this.state.clusterId,
                        handleOptionsChange: this.handleOptionsChange.bind(this) })
                ),
                _react2.default.createElement(
                    'div',
                    { className: 'large-6 columns' },
                    _react2.default.createElement(
                        'label',
                        null,
                        'GeneId:'
                    ),
                    _react2.default.createElement('input', { type: 'text', value: this.state.geneId, onChange: this.handleChange.bind(this, "geneId") })
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