'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _DifferentialResults = require('./DifferentialResults');

var _DifferentialResults2 = _interopRequireDefault(_DifferentialResults);

var _DifferentialFacetsTree = require('./facets-tree/DifferentialFacetsTree');

var _DifferentialFacetsTree2 = _interopRequireDefault(_DifferentialFacetsTree);

var _urlManager = require('./urlManager');

var _urlManager2 = _interopRequireDefault(_urlManager);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/*
 TODO if Solr queries get fast enough that we can:
 - split the two requests, so that the facets load first, initial results load second
 - a request to the server is done for every interaction with the facets tree
 - add counts to each facet and disable check boxes if count is 0
*/

var equalsToOrIncludes = function equalsToOrIncludes(obj, value) {
  if (!!obj) {
    if (obj.constructor === Array) {
      return obj.includes(value);
    } else {
      return obj === value;
    }
  } else {
    return false;
  }
};

var addElementToObjectOfArrays = function addElementToObjectOfArrays(obj, arrayName, element) {
  if (!obj[arrayName]) {
    obj[arrayName] = [];
  }
  obj[arrayName].push(element);
};

var removeElementFromObjectOfArrays = function removeElementFromObjectOfArrays(obj, arrayName, element) {
  delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1);
  if (obj[arrayName].length === 0) {
    delete obj[arrayName];
  }
};

var resultMatchesQuery = function resultMatchesQuery(result, query) {
  if (Object.keys(query).length === 0) {
    return false;
  } else {
    return Object.keys(query).every(function (facetName) {
      return query[facetName].some(function (facetItem) {
        return equalsToOrIncludes(result[facetName], facetItem);
      });
    });
  }
};

var DifferentialRouter = function (_React$Component) {
  _inherits(DifferentialRouter, _React$Component);

  function DifferentialRouter(props) {
    _classCallCheck(this, DifferentialRouter);

    var _this = _possibleConstructorReturn(this, (DifferentialRouter.__proto__ || Object.getPrototypeOf(DifferentialRouter)).call(this, props));

    var querySelect = _urlManager2.default.parseDifferentialUrlParameter();
    if (!querySelect.kingdom) {
      querySelect.kingdom = props.facetsTreeData.find(function (facet) {
        return facet.facetName === 'kingdom';
      }).facetItems.map(function (facetItem) {
        return facetItem.name;
      });
    }
    _urlManager2.default.differentialPush(querySelect, true);

    _this.state = {
      querySelect: querySelect
    };

    _this._setChecked = _this._setChecked.bind(_this);
    return _this;
  }

  _createClass(DifferentialRouter, [{
    key: 'componentDidMount',
    value: function componentDidMount() {
      var _this2 = this;

      window.addEventListener('popstate', function () {
        _this2.setState({ querySelect: _urlManager2.default.parseDifferentialUrlParameter() });
      }, false);
    }
  }, {
    key: '_setChecked',
    value: function _setChecked(facetName, facetItemName, checked) {
      // Update URL
      var newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect));
      if (checked) {
        addElementToObjectOfArrays(newQuerySelect, facetName, facetItemName);
      } else {
        removeElementFromObjectOfArrays(newQuerySelect, facetName, facetItemName);
      }

      // TODO Consider using https://github.com/reactjs/react-router
      _urlManager2.default.differentialPush(newQuerySelect, false);
      this.setState({
        querySelect: newQuerySelect
      });
    }
  }, {
    key: '_filteredResults',
    value: function _filteredResults() {
      var query = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : this.state.querySelect;

      return this.props.results.filter(function (result) {
        return resultMatchesQuery(result, query);
      });
    }

    // Syncs tree data with URL (querySelect) and does some other smart things such as check/uncheck or disable facets based on
    // the user results (e.g. check & disable a facet if it’s shared by all results as a side effect of other choice)

  }, {
    key: '_prepareFacetTreeData',
    value: function _prepareFacetTreeData(filteredResults) {
      var _this3 = this;

      return this.props.facetsTreeData.map(function (facet) {
        return {
          facetName: facet.facetName,
          facetItems: facet.facetItems.map(function (facetItem) {
            var querySelectAfterSwitchingThisFacetItem = JSON.parse(JSON.stringify(_this3.state.querySelect));

            if (equalsToOrIncludes(querySelectAfterSwitchingThisFacetItem[facet.facetName], facetItem.name)) {
              removeElementFromObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
            } else {
              addElementToObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
            }

            var resultIdsAfterSwitchingThisFacetItem = _this3._filteredResults(querySelectAfterSwitchingThisFacetItem).map(function (result) {
              return result.id;
            }).sort();
            var currentResultIds = filteredResults.map(function (result) {
              return result.id;
            }).sort();

            var sameResultsAfterSwitchingThisItem = JSON.stringify(resultIdsAfterSwitchingThisFacetItem) === JSON.stringify(currentResultIds);
            var noResultsAfterSwitchingThisItem = resultIdsAfterSwitchingThisFacetItem.length === 0;

            return {
              name: facetItem.name,
              value: facetItem.value,
              checked: equalsToOrIncludes(_this3.state.querySelect[facet.facetName], facetItem.name) || sameResultsAfterSwitchingThisItem,
              disabled: noResultsAfterSwitchingThisItem || sameResultsAfterSwitchingThisItem
            };
          })
        };
      });
    }
  }, {
    key: 'render',
    value: function render() {
      var filteredResults = this._filteredResults();

      return _react2.default.createElement(
        'div',
        { className: 'row column expanded' },
        _react2.default.createElement(
          'div',
          { className: 'show-for-large large-3 columns' },
          Object.keys(this.props.facetsTreeData).length && _react2.default.createElement(_DifferentialFacetsTree2.default, { facets: this._prepareFacetTreeData(filteredResults),
            setChecked: this._setChecked })
        ),
        _react2.default.createElement(
          'div',
          { className: 'small-12 large-9 columns' },
          this.props.results && this.props.results.length && _react2.default.createElement(_DifferentialResults2.default, _extends({ results: filteredResults,
            atlasUrl: this.props.atlasUrl
          }, this.props.legend))
        )
      );
    }
  }]);

  return DifferentialRouter;
}(_react2.default.Component);

DifferentialRouter.propTypes = {
  facetsTreeData: _propTypes2.default.array,
  results: _propTypes2.default.array,
  legend: _propTypes2.default.object,
  atlasUrl: _propTypes2.default.string.isRequired
};

exports.default = DifferentialRouter;