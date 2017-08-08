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

var _singleCellGeneTsnePlot = require('single-cell-gene-tsne-plot');

var _singleCellGeneTsnePlot2 = _interopRequireDefault(_singleCellGeneTsnePlot);

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
                            return _react2.default.createElement(Experiment, _extends({}, props, {
                                atlasUrl: _this2.props.atlasUrl,
                                suggesterEndpoint: _this2.props.suggesterEndpoint,
                                experimentAccession: _this2.props.experimentAccession,
                                referenceDataSourceUrlTemplate: _this2.props.referenceDataSourceUrlTemplate,
                                clustersData: _this2.props.clustersData
                            }));
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
    clustersData: _propTypes2.default.object.isRequired,
    suggesterEndpoint: _propTypes2.default.string,
    referenceDataSourceUrlTemplate: _propTypes2.default.string
};

var Experiment = function (_Component2) {
    _inherits(Experiment, _Component2);

    function Experiment(props) {
        _classCallCheck(this, Experiment);

        var _this3 = _possibleConstructorReturn(this, (Experiment.__proto__ || Object.getPrototypeOf(Experiment)).call(this, props));

        _this3.state = {
            params: _queryString2.default.parse(props.location.search),
            geneId: "",
            k: 0,
            clusterId: []
        };
        return _this3;
    }

    _createClass(Experiment, [{
        key: 'handleChange',
        value: function handleChange(param, item) {
            var _newparam = {};
            _newparam[param] = param === "k" ? item.target.value : item;
            this.setState(_newparam);

            this.props.history.push("?" + _queryString2.default.stringify({
                geneId: param === "geneId" ? item : this.state.geneId,
                k: param === "k" ? item.target.value : this.state.k }) + "&clusterId=[" + this.state.clusterId + "]");
        }
    }, {
        key: 'componentDidMount',
        value: function componentDidMount() {
            this.setState({
                geneId: this.state.params.geneId,
                k: this.state.params.k,
                clusterId: JSON.parse(this.state.params.clusterId || '[]')
            });
        }
    }, {
        key: 'render',
        value: function render() {

            return _react2.default.createElement(
                'div',
                { className: 'row' },
                _react2.default.createElement(
                    'div',
                    { className: 'small-6 columns' },
                    _react2.default.createElement(_singleCellTsnePlot2.default, { clustersData: this.props.clustersData,
                        k: this.state.k,
                        clusterId: this.state.clusterId,
                        handleOptionsChange: this.handleChange.bind(this, "k") })
                ),
                _react2.default.createElement(
                    'div',
                    { className: 'small-6 columns' },
                    _react2.default.createElement(_singleCellGeneTsnePlot2.default, { atlasUrl: this.props.atlasUrl,
                        clustersData: this.props.clustersData,
                        k: this.state.k,
                        suggesterEndpoint: this.props.suggesterEndpoint,
                        referenceDataSourceUrlTemplate: this.props.referenceDataSourceUrlTemplate,
                        geneId: this.state.geneId,
                        onSelect: this.handleChange.bind(this, "geneId")
                    })
                )
            );
        }
    }]);

    return Experiment;
}(_react.Component);

Experiment.propTypes = {
    props: _propTypes2.default.object,
    atlasUrl: _propTypes2.default.string,
    suggesterEndpoint: _propTypes2.default.string,
    referenceDataSourceUrlTemplate: _propTypes2.default.string
};

exports.default = ExperimentPage;