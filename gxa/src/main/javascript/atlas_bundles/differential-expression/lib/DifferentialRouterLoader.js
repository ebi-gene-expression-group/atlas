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

var _urijs = require('urijs');

var _urijs2 = _interopRequireDefault(_urijs);

var _DifferentialRouter = require('./DifferentialRouter');

var _DifferentialRouter2 = _interopRequireDefault(_DifferentialRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var transformFacetsResponseToArray = function transformFacetsResponseToArray(facetsResponse) {
  return Object.keys(facetsResponse).map(function (facetName) {
    return {
      facetName: facetName,
      facetItems: facetsResponse[facetName].map(function (facetItem) {
        return {
          name: facetItem.name,
          value: facetItem.value,
          disabled: false,
          checked: false
        };
      })
    };
  });
};

var pruneFacetsTreeBasedOnResultsThatCameIn = function pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, results) {
  return facetsTreeData.map(function (facet) {
    return {
      facetName: facet.facetName,
      facetItems: facet.facetItems.filter(function (facetItem) {
        return results.some(function (result) {
          if (Array.isArray(result[facet.facetName])) {
            return result[facet.facetName].includes(facetItem.name);
          } else {
            return result[facet.facetName] === facetItem.name;
          }
        });
      })
    };
  }).filter(function (facet) {
    return facet.facetItems.length > 0;
  });
};

var DifferentialRouterLoader = function (_React$Component) {
  _inherits(DifferentialRouterLoader, _React$Component);

  function DifferentialRouterLoader(props) {
    _classCallCheck(this, DifferentialRouterLoader);

    return _possibleConstructorReturn(this, (DifferentialRouterLoader.__proto__ || Object.getPrototypeOf(DifferentialRouterLoader)).call(this, props));
  }

  _createClass(DifferentialRouterLoader, [{
    key: 'render',
    value: function render() {
      var _props = this.props,
          facetsFetch = _props.facetsFetch,
          resultsFetch = _props.resultsFetch;

      var allFetches = _reactRefetch.PromiseState.all([facetsFetch, resultsFetch]);

      if (allFetches.pending) {
        return _react2.default.createElement(
          'div',
          { className: 'row column' },
          _react2.default.createElement('img', { src: (0, _urijs2.default)('resources/images/loading.gif', this.props.atlasUrl).toString() })
        );
      } else if (allFetches.fulfilled) {
        var resultsResponse = resultsFetch.value;
        var facetsResponse = facetsFetch.value;

        var facetsTreeData = transformFacetsResponseToArray(facetsResponse);
        var prunedFacetsTreeData = pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, resultsResponse.results);
        var results = resultsResponse.results;
        var legend = {
          minDownLevel: resultsResponse.minDownLevel,
          minUpLevel: resultsResponse.minUpLevel,
          maxDownLevel: resultsResponse.maxDownLevel,
          maxUpLevel: resultsResponse.maxUpLevel
        };

        return _react2.default.createElement(_DifferentialRouter2.default, { facetsTreeData: prunedFacetsTreeData,
          results: results,
          legend: legend,
          atlasUrl: this.props.atlasUrl });
      }
    }
  }]);

  return DifferentialRouterLoader;
}(_react2.default.Component);

DifferentialRouterLoader.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  geneQuery: _propTypes2.default.string.isRequired,
  conditionQuery: _propTypes2.default.string.isRequired,
  species: _propTypes2.default.string.isRequired
};

exports.default = (0, _reactRefetch.connect)(function (props) {
  var requestParams = { geneQuery: props.geneQuery, conditionQuery: props.conditionQuery, species: props.species };
  return {
    facetsFetch: (0, _urijs2.default)('json/search/differential_facets', props.atlasUrl).search(requestParams).toString(),
    resultsFetch: (0, _urijs2.default)('json/search/differential_results', props.atlasUrl).search(requestParams).toString()
  };
})(DifferentialRouterLoader);