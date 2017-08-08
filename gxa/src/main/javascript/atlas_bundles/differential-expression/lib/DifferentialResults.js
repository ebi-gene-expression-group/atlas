'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = require('urijs');

var _urijs2 = _interopRequireDefault(_urijs);

var _expressionAtlasFeedback = require('expression-atlas-feedback');

var _expressionAtlasFeedback2 = _interopRequireDefault(_expressionAtlasFeedback);

var _reactEbiSpecies = require('react-ebi-species');

var _reactEbiSpecies2 = _interopRequireDefault(_reactEbiSpecies);

var _DisplayLevelsButton = require('./DisplayLevelsButton');

var _DisplayLevelsButton2 = _interopRequireDefault(_DisplayLevelsButton);

var _DifferentialDownloadButton = require('./DifferentialDownloadButton');

var _DifferentialDownloadButton2 = _interopRequireDefault(_DifferentialDownloadButton);

var _DifferentialFoldChangeCell = require('./DifferentialFoldChangeCell');

var _DifferentialFoldChangeCell2 = _interopRequireDefault(_DifferentialFoldChangeCell);

var _LegendDifferential = require('./legend/LegendDifferential');

var _LegendDifferential2 = _interopRequireDefault(_LegendDifferential);

var _ContrastTooltipLoader = require('./tooltip/ContrastTooltipLoader');

var _ContrastTooltipLoader2 = _interopRequireDefault(_ContrastTooltipLoader);

require('./DifferentialResults.css');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var differentialResultRowDataPropTypes = {
  species: _propTypes2.default.string.isRequired,
  kingdom: _propTypes2.default.string.isRequired,
  experimentType: _propTypes2.default.string.isRequired,
  numReplicates: _propTypes2.default.number,
  regulation: _propTypes2.default.string.isRequired,
  factors: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
  bioentityIdentifier: _propTypes2.default.string.isRequired,
  bioentityName: _propTypes2.default.string.isRequired,
  experimentAccession: _propTypes2.default.string.isRequired,
  experimentName: _propTypes2.default.string.isRequired,
  contrastId: _propTypes2.default.string.isRequired,
  comparison: _propTypes2.default.string.isRequired,
  foldChange: _propTypes2.default.number.isRequired,
  colour: _propTypes2.default.string.isRequired,
  id: _propTypes2.default.string.isRequired,
  uri: _propTypes2.default.string.isRequired
};

var DifferentialResultsRow = function DifferentialResultsRow(props) {
  return _react2.default.createElement(
    'tr',
    null,
    _react2.default.createElement(_DifferentialFoldChangeCell2.default, { foldChange: props.foldChange,
      pValue: props.pValue,
      tStat: props.tStatistics,
      displayLevels: props.displayLevels,
      colour: props.colour,
      id: props.id }),
    _react2.default.createElement(
      'td',
      null,
      _react2.default.createElement(_reactEbiSpecies2.default, { species: props.species })
    ),
    _react2.default.createElement(
      'td',
      null,
      _react2.default.createElement(
        'a',
        { href: (0, _urijs2.default)('genes/' + props.bioentityIdentifier, props.atlasUrl).toString() },
        props.bioentityName || props.bioentityIdentifier
      )
    ),
    _react2.default.createElement(
      'td',
      { 'data-tip': true,
        'data-for': props.id + '_contrast' },
      _react2.default.createElement(
        'a',
        { href: (0, _urijs2.default)(props.uri, props.atlasUrl) },
        props.comparison
      ),
      _react2.default.createElement(_ContrastTooltipLoader2.default, { id: props.id + '_contrast',
        atlasUrl: props.atlasUrl,
        tooltipUrl: 'rest/contrast-summary',
        tooltipUrlParams: {
          experimentAccession: props.experimentAccession,
          contrastId: props.contrastId,
          accessKey: props.accessKey
        } })
    ),
    _react2.default.createElement(
      'td',
      { className: 'gxaExperimentalVariable' },
      props.factors ? props.factors.toString().replace(/,/g, ', ') : ''
    ),
    _react2.default.createElement(
      'td',
      null,
      _react2.default.createElement(
        'a',
        { href: (0, _urijs2.default)('experiments/' + props.experimentAccession, props.atlasUrl).toString() },
        props.experimentName
      )
    )
  );
};

DifferentialResultsRow.propTypes = _extends({}, differentialResultRowDataPropTypes, {
  atlasUrl: _react2.default.PropTypes.string.isRequired
});

var DifferentialResults = function (_React$Component) {
  _inherits(DifferentialResults, _React$Component);

  function DifferentialResults(props) {
    _classCallCheck(this, DifferentialResults);

    var _this = _possibleConstructorReturn(this, (DifferentialResults.__proto__ || Object.getPrototypeOf(DifferentialResults)).call(this, props));

    _this.state = {
      displayLevels: false
    };

    _this._toggleDisplayLevels = _this._toggleDisplayLevels.bind(_this);
    return _this;
  }

  _createClass(DifferentialResults, [{
    key: '_toggleDisplayLevels',
    value: function _toggleDisplayLevels() {
      var newDisplayLevels = !this.state.displayLevels;
      this.setState({
        displayLevels: newDisplayLevels
      });
    }
  }, {
    key: 'render',
    value: function render() {
      var _this2 = this;

      return _react2.default.createElement(
        'div',
        { className: 'row column expanded' },
        _react2.default.createElement(
          'div',
          { className: 'row column expanded' },
          _react2.default.createElement(
            'div',
            { className: 'small-2 columns padding-left-none padding-right-none center' },
            _react2.default.createElement(_LegendDifferential2.default, { minDownLevel: this.props.minDownLevel,
              maxDownLevel: this.props.maxDownLevel,
              minUpLevel: this.props.minUpLevel,
              maxUpLevel: this.props.maxUpLevel })
          ),
          _react2.default.createElement(
            'div',
            { className: 'small-2 columns padding-left-none padding-right-none margin-left-large text-center' },
            _react2.default.createElement(_DisplayLevelsButton2.default, { onClick: this._toggleDisplayLevels,
              displayLevels: this.state.displayLevels })
          ),
          _react2.default.createElement(
            'div',
            { className: 'small-2 columns padding-left-none padding-right-none margin-left-large text-right' },
            _react2.default.createElement(_DifferentialDownloadButton2.default, { results: this.props.results
            })
          )
        ),
        _react2.default.createElement(
          'div',
          { className: 'row column expanded' },
          _react2.default.createElement(
            'table',
            { className: 'gxaDifferentialResultsTable' },
            _react2.default.createElement(
              'thead',
              null,
              _react2.default.createElement(
                'tr',
                null,
                _react2.default.createElement(
                  'th',
                  { style: { width: '10%' } },
                  'Log',
                  _react2.default.createElement(
                    'sub',
                    null,
                    '2'
                  ),
                  '-fold change'
                ),
                _react2.default.createElement(
                  'th',
                  { style: { width: '5%' } },
                  'Species'
                ),
                _react2.default.createElement(
                  'th',
                  { style: { width: '5%' } },
                  'Gene name'
                ),
                _react2.default.createElement(
                  'th',
                  { style: { width: '30%' } },
                  'Comparison'
                ),
                _react2.default.createElement(
                  'th',
                  { style: { width: '15%' } },
                  'Experimental variables'
                ),
                _react2.default.createElement(
                  'th',
                  { style: { width: '35%' } },
                  'Experiment name'
                )
              )
            ),
            _react2.default.createElement(
              'tbody',
              null,
              this.props.results.map(function (diffResult) {
                return _react2.default.createElement(DifferentialResultsRow, _extends({ key: diffResult.id,
                  displayLevels: _this2.state.displayLevels,
                  atlasUrl: _this2.props.atlasUrl
                }, diffResult));
              })
            )
          ),
          _react2.default.createElement(
            'div',
            { className: 'margin-top-medium' },
            _react2.default.createElement(_expressionAtlasFeedback2.default, {
              collectionCallback: typeof window.ga === 'function' ? function (score, comment) {
                window.ga('send', 'event', 'DifferentialHeatmaps', 'feedback', comment, score);
              } : function () {} })
          )
        )
      );
    }
  }]);

  return DifferentialResults;
}(_react2.default.Component);

DifferentialResults.propTypes = {
  results: _propTypes2.default.arrayOf(_propTypes2.default.shape(differentialResultRowDataPropTypes)).isRequired,
  maxDownLevel: _propTypes2.default.number,
  minDownLevel: _propTypes2.default.number,
  minUpLevel: _propTypes2.default.number,
  maxUpLevel: _propTypes2.default.number,
  atlasUrl: _propTypes2.default.string.isRequired
};

DifferentialResults.defaultProps = {
  maxDownLevel: Number.NEGATIVE_INFINITY,
  minDownLevel: 0,
  minUpLevel: 0,
  maxUpLevel: Number.POSITIVE_INFINITY
};

exports.default = DifferentialResults;