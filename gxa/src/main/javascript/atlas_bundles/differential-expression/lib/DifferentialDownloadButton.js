'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var jsonToTsv = function jsonToTsv(results) {
  var arrayResults = typeof results !== 'object' ? JSON.parse(results) : results;

  return [['Gene', 'Organism', 'Experiment Accession', 'Comparison', 'log2foldchange', 'pValue'].concat(arrayResults.some(function (diffResults) {
    return diffResults.tStatistics !== null;
  }) ? ['tStatistics'] : []).join('\t')].concat(arrayResults.map(function (diffResults) {
    return [diffResults.bioentityIdentifier, diffResults.species, diffResults.experimentAccession, diffResults.comparison, diffResults.foldChange, diffResults.pValue, diffResults.tStatistics].filter(function (el) {
      return el !== null;
    });
  } // tStatistics might be missing
  // .join(`\t`)
  )).join('\n');
};

var DownloadDifferentialButton = function DownloadDifferentialButton(_ref) {
  var results = _ref.results;
  return _react2.default.createElement(
    'a',
    { className: 'button',
      download: 'differentialResults.tsv',
      href: 'data:text/tsv;charset=utf-8,' + encodeURI(jsonToTsv(results)),
      target: '_blank' },
    _react2.default.createElement(
      'span',
      { className: 'icon icon-functional', 'data-icon': '=' },
      ' Download results'
    )
  );
};

DownloadDifferentialButton.propTypes = {
  results: _propTypes2.default.arrayOf(_propTypes2.default.shape({
    species: _propTypes2.default.string.isRequired,
    kingdom: _propTypes2.default.string.isRequired,
    experimentType: _propTypes2.default.string.isRequired,
    numReplicates: _propTypes2.default.number.isRequired, // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
    regulation: _propTypes2.default.string.isRequired,
    factors: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
    bioentityIdentifier: _propTypes2.default.string.isRequired,
    experimentAccession: _propTypes2.default.string.isRequired,
    experimentName: _propTypes2.default.string.isRequired,
    contrastId: _propTypes2.default.string.isRequired,
    comparison: _propTypes2.default.string.isRequired,
    foldChange: _propTypes2.default.number.isRequired,
    pValue: _propTypes2.default.number.isRequired,
    tStatistics: _propTypes2.default.number,
    colour: _propTypes2.default.string.isRequired,
    id: _propTypes2.default.string.isRequired
  })).isRequired
};

exports.default = DownloadDifferentialButton;