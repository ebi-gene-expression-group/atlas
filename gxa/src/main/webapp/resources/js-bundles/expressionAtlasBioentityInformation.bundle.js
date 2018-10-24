var expressionAtlasBioentityInformation =
webpackJsonp_name_([4],{

/***/ 1015:
/*!******************************************************!*\
  !*** ./atlas_bundles/bioentity-information/index.js ***!
  \******************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _scAtlasBioentityInformation = __webpack_require__(/*! sc-atlas-bioentity-information */ 1016);

exports.render = _scAtlasBioentityInformation.render;

/***/ }),

/***/ 1016:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/node_modules/sc-atlas-bioentity-information/lib/index.js ***!
  \******************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.render = exports.default = undefined;

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 9);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _BioentityInformation = __webpack_require__(/*! ./BioentityInformation.js */ 1017);

var _BioentityInformation2 = _interopRequireDefault(_BioentityInformation);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, target) {
    _reactDom2.default.render(_react2.default.createElement(_BioentityInformation2.default, options), document.getElementById(target));
};

exports.default = _BioentityInformation2.default;
exports.render = render;

/***/ }),

/***/ 1017:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/node_modules/sc-atlas-bioentity-information/lib/BioentityInformation.js ***!
  \*********************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 11);

var _urijs2 = _interopRequireDefault(_urijs);

var _BioentityProperty = __webpack_require__(/*! ./BioentityProperty */ 1018);

var _BioentityProperty2 = _interopRequireDefault(_BioentityProperty);

var _Loading = __webpack_require__(/*! ./Loading */ 1019);

var _Loading2 = _interopRequireDefault(_Loading);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

var fetchResponseJson = function () {
  var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee(base, endpoint) {
    var response, responseJson;
    return regeneratorRuntime.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            _context.next = 2;
            return fetch((0, _urijs2.default)(endpoint, base).toString());

          case 2:
            response = _context.sent;
            _context.next = 5;
            return response.json();

          case 5:
            responseJson = _context.sent;
            return _context.abrupt('return', responseJson);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, undefined);
  }));

  return function fetchResponseJson(_x, _x2) {
    return _ref.apply(this, arguments);
  };
}();

var BioentityInformation = function (_React$Component) {
  _inherits(BioentityInformation, _React$Component);

  function BioentityInformation(props) {
    _classCallCheck(this, BioentityInformation);

    var _this = _possibleConstructorReturn(this, (BioentityInformation.__proto__ || Object.getPrototypeOf(BioentityInformation)).call(this, props));

    _this.state = {
      bioentityProperties: [],
      errorMessage: null,
      loading: false
    };
    return _this;
  }

  _createClass(BioentityInformation, [{
    key: '_fetchAndSetState',
    value: function _fetchAndSetState(_ref2) {
      var _this2 = this;

      var atlasUrl = _ref2.atlasUrl,
          geneId = _ref2.geneId;

      var atlasEndpoint = 'json/bioentity_information/' + geneId;

      return fetchResponseJson(atlasUrl, atlasEndpoint).then(function (responseJson) {
        _this2.setState({
          bioentityProperties: responseJson,
          errorMessage: null,
          loading: false
        });
      }).catch(function (reason) {
        _this2.setState({
          errorMessage: reason.name + ': ' + reason.message
        });
      });
    }
  }, {
    key: 'componentDidCatch',
    value: function componentDidCatch(error, errorMessage) {
      this.setState({
        errorMessage: '' + error
      });
    }
  }, {
    key: 'componentDidMount',
    value: function componentDidMount() {
      this.setState({
        loading: true
      });
      return this._fetchAndSetState(this.props);
    }
  }, {
    key: 'componentWillReceiveProps',
    value: function componentWillReceiveProps(nextProps) {
      if (nextProps.atlasUrl !== this.props.atlasUrl || nextProps.geneId !== this.props.geneId) {
        this.setState({
          bioentityProperties: [],
          loading: true
        });
        return this._fetchAndSetState(nextProps);
      }
    }
  }, {
    key: 'render',
    value: function render() {
      var bioentityProperties = this.state.bioentityProperties.map(function (property) {
        return _react2.default.createElement(
          'tr',
          { key: property.type },
          _react2.default.createElement(
            'th',
            { style: { whiteSpace: 'nowrap' } },
            property.name
          ),
          _react2.default.createElement(
            'td',
            null,
            _react2.default.createElement(_BioentityProperty2.default, { type: property.type, values: property.values })
          )
        );
      });

      return this.props.geneId && _react2.default.createElement(
        'div',
        { className: this.props.wrapperClassName },
        _react2.default.createElement(_Loading2.default, { loading: this.state.loading, resourcesUrl: this.props.resourcesUrl }),
        _react2.default.createElement(
          'table',
          { className: 'hover' },
          _react2.default.createElement(
            'tbody',
            null,
            bioentityProperties
          )
        )
      );
    }
  }]);

  return BioentityInformation;
}(_react2.default.Component);

BioentityInformation.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  geneId: _propTypes2.default.string,
  resourcesUrl: _propTypes2.default.string,
  wrapperClassName: _propTypes2.default.string
};

BioentityInformation.defaultProps = {
  geneId: '',
  wrapperClassName: 'row column expanded'
};

exports.default = BioentityInformation;

/***/ }),

/***/ 1018:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/node_modules/sc-atlas-bioentity-information/lib/BioentityProperty.js ***!
  \******************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var PropertyValue = function PropertyValue(_ref) {
  var hasUrl = _ref.hasUrl,
      isLast = _ref.isLast,
      text = _ref.text,
      url = _ref.url;
  return hasUrl ? _react2.default.createElement(
    'span',
    null,
    _react2.default.createElement(
      'a',
      { className: "bioEntityCardLink", href: url, target: '_blank' },
      text
    ),
    !isLast ? ', ' : ''
  ) : _react2.default.createElement(
    'span',
    null,
    text + (!isLast ? ', ' : '')
  );
};

var TOP_RELEVANT_VALUES = 3;

var BioentityProperty = function (_React$Component) {
  _inherits(BioentityProperty, _React$Component);

  function BioentityProperty(props) {
    _classCallCheck(this, BioentityProperty);

    var _this = _possibleConstructorReturn(this, (BioentityProperty.__proto__ || Object.getPrototypeOf(BioentityProperty)).call(this, props));

    _this.state = {
      showAll: false
    };

    _this.handleShowMoreClick = _this.handleClick.bind(_this);
    return _this;
  }

  _createClass(BioentityProperty, [{
    key: 'handleClick',
    value: function handleClick() {
      this.setState(function (previousState) {
        return {
          showAll: !previousState.showAll
        };
      });
    }

    // Return three most relevant links

  }, {
    key: '_getMostRelevant',
    value: function _getMostRelevant(properties) {
      // The properties are sorted in descending order by relevance in the backend
      return properties.slice(0, TOP_RELEVANT_VALUES);
    }
  }, {
    key: '_renderPropertyValues',
    value: function _renderPropertyValues(values) {
      return values.map(function (value, index) {
        return _react2.default.createElement(PropertyValue, { key: value.text,
          isLast: index >= values.length - 1,
          hasUrl: !!value.url,
          text: value.text, url: value.url });
      });
    }
  }, {
    key: 'render',
    value: function render() {
      var numberOfHiddenLinks = this.props.values.length - TOP_RELEVANT_VALUES;
      var hasOptionalLinks = ["go", "po"].indexOf(this.props.type) > -1 && numberOfHiddenLinks > 0;
      var showMoreLessButton = _react2.default.createElement(
        'a',
        { key: 'showButton',
          role: 'button',
          style: { cursor: 'pointer' },
          onClick: this.handleShowMoreClick },
        this.state.showAll ? ' (show less)' : ' \u2026 and ' + numberOfHiddenLinks + ' more'
      );

      var allValuesWithOptionalLinks = this._renderPropertyValues(this.props.values);
      var topThreeValuesWithOptionalLinks = this._renderPropertyValues(this._getMostRelevant(this.props.values));

      return _react2.default.createElement(
        'div',
        null,
        !hasOptionalLinks && allValuesWithOptionalLinks,
        hasOptionalLinks && !this.state.showAll && [topThreeValuesWithOptionalLinks, showMoreLessButton],
        hasOptionalLinks && this.state.showAll && [allValuesWithOptionalLinks, showMoreLessButton]
      );
    }
  }]);

  return BioentityProperty;
}(_react2.default.Component);

BioentityProperty.propTypes = {
  type: _propTypes2.default.string.isRequired,
  values: _propTypes2.default.arrayOf(_propTypes2.default.shape({
    text: _propTypes2.default.string.isRequired,
    url: _propTypes2.default.string.isRequired,
    relevance: _propTypes2.default.number.isRequired
  }))
};

exports.default = BioentityProperty;

/***/ }),

/***/ 1019:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/node_modules/sc-atlas-bioentity-information/lib/Loading.js ***!
  \********************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 11);

var _urijs2 = _interopRequireDefault(_urijs);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var Loading = function Loading(props) {
  if (!props.loading) {
    return null;
  }

  return _react2.default.createElement(
    'div',
    { className: 'text-center' },
    _react2.default.createElement('img', { className: 'small-centered', src: (0, _urijs2.default)(__webpack_require__(/*! ./images/loading.gif */ 1020), props.resourcesUrl).toString() })
  );
};

Loading.propTypes = {
  loading: _propTypes2.default.bool.isRequired,
  resourcesUrl: _propTypes2.default.string
};

Loading.defaultProps = {
  resourcesUrl: ''
};

exports.default = Loading;

/***/ }),

/***/ 1020:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/node_modules/sc-atlas-bioentity-information/lib/images/loading.gif ***!
  \****************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "e5d9a446341dc8fd32fee7401aa8e252.gif";

/***/ })

},[1015]);
//# sourceMappingURL=expressionAtlasBioentityInformation.bundle.js.map