var expressionAtlasBaselineExpression =
webpackJsonp_name_([2],{

/***/ 100:
/*!****************************************************************!*\
  !*** ./node_modules/react-transition-group/utils/PropTypes.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.nameShape = undefined;
exports.transitionTimeout = transitionTimeout;

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function transitionTimeout(transitionType) {
  var timeoutPropName = 'transition' + transitionType + 'Timeout';
  var enabledPropName = 'transition' + transitionType;

  return function (props) {
    // If the transition is enabled
    if (props[enabledPropName]) {
      // If no timeout duration is provided
      if (props[timeoutPropName] == null) {
        return new Error(timeoutPropName + ' wasn\'t supplied to CSSTransitionGroup: ' + 'this can cause unreliable animations and won\'t be supported in ' + 'a future version of React. See ' + 'https://fb.me/react-animation-transition-group-timeout for more ' + 'information.');

        // If the duration isn't a number
      } else if (typeof props[timeoutPropName] !== 'number') {
        return new Error(timeoutPropName + ' must be a number (in milliseconds)');
      }
    }

    return null;
  };
}

var nameShape = exports.nameShape = _propTypes2.default.oneOfType([_propTypes2.default.string, _propTypes2.default.shape({
  enter: _propTypes2.default.string,
  leave: _propTypes2.default.string,
  active: _propTypes2.default.string
}), _propTypes2.default.shape({
  enter: _propTypes2.default.string,
  enterActive: _propTypes2.default.string,
  leave: _propTypes2.default.string,
  leaveActive: _propTypes2.default.string,
  appear: _propTypes2.default.string,
  appearActive: _propTypes2.default.string
})]);

/***/ }),

/***/ 1102:
/*!****************************************************!*\
  !*** ./atlas_bundles/baseline-expression/index.js ***!
  \****************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _baselineRenderer = __webpack_require__(/*! ./src/baselineRenderer.jsx */ 1103);

var _baselineRenderer2 = _interopRequireDefault(_baselineRenderer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.render = _baselineRenderer2.default;

/***/ }),

/***/ 1103:
/*!********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/baselineRenderer.jsx ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

exports.default = function (_ref) {
    var _ref$atlasUrl = _ref.atlasUrl,
        atlasUrl = _ref$atlasUrl === undefined ? 'https://www.ebi.ac.uk/gxa' : _ref$atlasUrl,
        _ref$target = _ref.target,
        target = _ref$target === undefined ? 'gxaBaselineTab' : _ref$target,
        facetsTreeData = _ref.facetsTreeData,
        geneQuery = _ref.geneQuery,
        conditionQuery = _ref.conditionQuery,
        species = _ref.species;


    _reactDom2.default.render(_react2.default.createElement(_BaselineRouter2.default, { atlasUrl: atlasUrl,
        facetsTreeData: facetsTreeData,
        geneQuery: geneQuery,
        conditionQuery: conditionQuery,
        species: species
    }), document.getElementById(target));
};

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 11);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _BaselineRouter = __webpack_require__(/*! ./BaselineRouter.jsx */ 1104);

var _BaselineRouter2 = _interopRequireDefault(_BaselineRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/***/ }),

/***/ 1104:
/*!******************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineRouter.jsx ***!
  \******************************************************************/
/*! no static exports found */
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

var _BaselineFacetsTree = __webpack_require__(/*! ./facets-tree/BaselineFacetsTree.jsx */ 1105);

var _BaselineFacetsTree2 = _interopRequireDefault(_BaselineFacetsTree);

var _BaselineHeatmaps = __webpack_require__(/*! ./BaselineHeatmaps.jsx */ 1108);

var _BaselineHeatmaps2 = _interopRequireDefault(_BaselineHeatmaps);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var UrlManager = __webpack_require__(/*! ./urlManager.js */ 1110);

var BaselineRouter = function (_React$Component) {
    _inherits(BaselineRouter, _React$Component);

    function BaselineRouter(props) {
        _classCallCheck(this, BaselineRouter);

        var _this = _possibleConstructorReturn(this, (BaselineRouter.__proto__ || Object.getPrototypeOf(BaselineRouter)).call(this, props));

        var newQuerySelect = UrlManager.parseBaselineUrlParameter();
        var newShowAnatomograms = false;

        if (Object.keys(newQuerySelect).length === 0) {
            Object.keys(_this.props.facetsTreeData).forEach(function (species) {
                var factorToPreselect = _this.props.facetsTreeData[species].find(function (factor) {
                    return factor.name.toLowerCase() === 'organism_part';
                });
                if (factorToPreselect) {
                    _this._addElementToObjectOfArrays(newQuerySelect, species, factorToPreselect.name);
                    newShowAnatomograms = true;
                } else if (_this.props.facetsTreeData[species].length) {
                    _this._addElementToObjectOfArrays(newQuerySelect, species, _this.props.facetsTreeData[species][0].name);
                }
            });
        }

        UrlManager.baselinePush(newQuerySelect, true);

        _this.state = {
            facetsTreeData: _this._transformPropsFacetsObjectToArray(newQuerySelect),
            querySelect: newQuerySelect,
            showAnatomograms: newShowAnatomograms
        };

        _this.setChecked = _this._setChecked.bind(_this);
        _this.toggleAnatomograms = _this._toggleAnatomograms.bind(_this);
        return _this;
    }

    _createClass(BaselineRouter, [{
        key: 'componentDidMount',
        value: function componentDidMount() {
            var _this2 = this;

            // TODO Consider using https://github.com/reactjs/react-router
            window.addEventListener('popstate', function () {
                var newQuerySelect = UrlManager.parseBaselineUrlParameter();
                _this2.setState({
                    querySelect: newQuerySelect,
                    facetsTreeData: _this2._transformPropsFacetsObjectToArray(newQuerySelect)
                });
            }, false);
        }
    }, {
        key: 'render',
        value: function render() {
            var organismPartInQuerySelect = this._organismPartInQuerySelect();
            var heatmaps = this._querySelectToHeatmaps();

            return _react2.default.createElement(
                'div',
                { className: 'row expanded' },
                _react2.default.createElement(
                    'div',
                    { className: 'small-3 large-2 columns' },
                    _react2.default.createElement(_BaselineFacetsTree2.default, {
                        facets: this.state.facetsTreeData,
                        setChecked: this.setChecked,
                        showAnatomograms: this.state.showAnatomograms,
                        toggleAnatomograms: this.toggleAnatomograms,
                        disableAnatomogramsCheckbox: !organismPartInQuerySelect })
                ),
                _react2.default.createElement(
                    'div',
                    { className: 'small-9 large-10 columns' },
                    _react2.default.createElement(_BaselineHeatmaps2.default, {
                        atlasUrl: this.props.atlasUrl,
                        geneQuery: this.props.geneQuery,
                        conditionQuery: this.props.conditionQuery,
                        heatmaps: heatmaps,
                        showAnatomograms: this.state.showAnatomograms })
                )
            );
        }
    }, {
        key: '_setChecked',
        value: function _setChecked(species, factorName, checked) {
            var newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect));
            var newFacetsTreeData = JSON.parse(JSON.stringify(this.state.facetsTreeData));

            if (checked) {
                this._addElementToObjectOfArrays(newQuerySelect, species, factorName);
                newFacetsTreeData.find(function (facet) {
                    return facet.facetName === species;
                }).facetItems.find(function (factor) {
                    return factor.name === factorName;
                }).checked = true;
            } else {
                this._removeElementFromObjectOfArrays(newQuerySelect, species, factorName);
                newFacetsTreeData.find(function (facet) {
                    return facet.facetName === species;
                }).facetItems.find(function (factor) {
                    return factor.name === factorName;
                }).checked = false;
            }

            UrlManager.baselinePush(newQuerySelect, false);
            this.setState({
                facetsTreeData: newFacetsTreeData,
                querySelect: newQuerySelect
            });
        }
    }, {
        key: '_addElementToObjectOfArrays',
        value: function _addElementToObjectOfArrays(obj, arrayName, element) {
            if (!obj[arrayName]) {
                obj[arrayName] = [];
            }
            obj[arrayName].push(element);
        }
    }, {
        key: '_removeElementFromObjectOfArrays',
        value: function _removeElementFromObjectOfArrays(obj, arrayName, element) {
            delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1);
            if (obj[arrayName].length === 0) {
                delete obj[arrayName];
            }
        }
    }, {
        key: '_toggleAnatomograms',
        value: function _toggleAnatomograms() {
            var newShowAnatomograms = !this.state.showAnatomograms;

            this.setState({
                showAnatomograms: newShowAnatomograms
            });
        }
    }, {
        key: '_organismPartInQuerySelect',
        value: function _organismPartInQuerySelect() {
            var querySelect = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : this.state.querySelect;

            return Object.keys(querySelect).some(function (species) {
                return querySelect[species].some(function (facetItem) {
                    return facetItem.toLowerCase() === 'organism_part';
                });
            });
        }

        // Also syncs this.state.facetsTreeData with querySelect

    }, {
        key: '_transformPropsFacetsObjectToArray',
        value: function _transformPropsFacetsObjectToArray(querySelect) {
            var _this3 = this;

            return Object.keys(this.props.facetsTreeData).map(function (facetName) {
                return {
                    facetName: facetName,
                    facetItems: _this3.props.facetsTreeData[facetName].map(function (facetItem) {
                        return {
                            name: facetItem.name,
                            value: facetItem.value,
                            checked: querySelect[facetName] ? querySelect[facetName].includes(facetItem.name) : false
                        };
                    })
                };
            });
        }
    }, {
        key: '_querySelectToHeatmaps',
        value: function _querySelectToHeatmaps() {
            var _this4 = this;

            /*
             querySelect={ "Homo sapiens": [ "CELL_LINE", "ORGANISM_PART" ] }
             ->
             [ { "species": "Homo sapiens",
                 "factor": { "name": "ORGANISM_PART", "value": "Organism part"}
               },
               { "species": "Homo sapiens",
                 "factor": { "name": "CELL_LINE", "value": "Cell line" }
             ]
             */
            var heatmaps = [];

            // We iterate over facetsTreeData instead of over querySelect to get heatmaps in the same order as the facets
            // tree
            this.state.facetsTreeData.forEach(function (facet) {
                facet.facetItems.forEach(function (facetItem) {
                    if (_this4.state.querySelect[facet.facetName] && _this4.state.querySelect[facet.facetName].includes(facetItem.name)) {
                        heatmaps.push({
                            species: facet.facetName,
                            factor: facetItem
                        });
                    }
                });
            });

            return heatmaps;
        }
    }]);

    return BaselineRouter;
}(_react2.default.Component);

BaselineRouter.propTypes = {
    atlasUrl: _propTypes2.default.string.isRequired,
    /*
    {
        "homo sapiens": [{ "name": "ORGANISM_PART", "value": "Organism part"},
        { "name": "CELL_LINE", "value": "Cell line"}],
        "macaca mulatta": [{ "name": "ORGANISM_PART", "value": "Organism part"}]
    }
     */
    facetsTreeData: _propTypes2.default.object.isRequired,
    geneQuery: _propTypes2.default.string.isRequired,
    conditionQuery: _propTypes2.default.string.isRequired,
    species: _propTypes2.default.string.isRequired
};

exports.default = BaselineRouter;

/***/ }),

/***/ 1105:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/BaselineFacetsTree.jsx ***!
  \**********************************************************************************/
/*! no static exports found */
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

var _Facet = __webpack_require__(/*! ./Facet.jsx */ 1106);

var _Facet2 = _interopRequireDefault(_Facet);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var BaselineFacetsTree = function BaselineFacetsTree(props) {
    var facets = props.facets.map(function (facet) {
        return _react2.default.createElement(_Facet2.default, {
            key: facet.facetName,
            facetName: facet.facetName,
            facetItems: facet.facetItems,
            setChecked: props.setChecked
        });
    });

    return _react2.default.createElement(
        'div',
        null,
        _react2.default.createElement('input', { type: 'checkbox', checked: props.showAnatomograms, onChange: props.toggleAnatomograms, disabled: props.disableAnatomogramsCheckbox }),
        _react2.default.createElement(
            'label',
            { className: props.disableAnatomogramsCheckbox ? "gxaDisabledCheckbox" : "" },
            'Show anatomograms'
        ),
        _react2.default.createElement(
            'h4',
            null,
            'Filter your results'
        ),
        facets
    );
};

BaselineFacetsTree.propTypes = {
    /*
     [
         {
             facetName : "homo sapiens",
             facetItems: [
                 {"name": "CELL_LINE", "value": "Cell line", "checked": true},
                 {"name": "ORGANISM_PART", "value": "Tissue", "checked: true}
             ]
         },
         {
             facetName : "mus musculus",
             facetItems": [
                 {"name": "CELL_LINE", "value": "Cell line", "checked": false},
                 {"name": "ORGANISM_PART", "value": "Tissue", "checked": true}
             ]
         }
     ]
     */
    facets: _propTypes2.default.arrayOf(_react2.default.PropTypes.shape({
        facetName: _propTypes2.default.string.isRequired,
        facetItems: _propTypes2.default.arrayOf(_react2.default.PropTypes.shape({
            name: _propTypes2.default.string.isRequired,
            value: _propTypes2.default.string.isRequired,
            checked: _propTypes2.default.bool.isRequired
        })).isRequired
    })).isRequired,
    setChecked: _propTypes2.default.func.isRequired,
    showAnatomograms: _propTypes2.default.bool.isRequired,
    toggleAnatomograms: _propTypes2.default.func.isRequired,
    disableAnatomogramsCheckbox: _propTypes2.default.bool.isRequired
};

exports.default = BaselineFacetsTree;

/***/ }),

/***/ 1106:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/Facet.jsx ***!
  \*********************************************************************/
/*! no static exports found */
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

var _FacetItem = __webpack_require__(/*! ./FacetItem.jsx */ 1107);

var _FacetItem2 = _interopRequireDefault(_FacetItem);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var capitalizeFirstLetter = function capitalizeFirstLetter(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
};

var Facet = function Facet(props) {
    var facetItems = props.facetItems.map(function (facetItem) {
        return _react2.default.createElement(_FacetItem2.default, {
            key: props.facetName + '_' + facetItem.name,
            name: facetItem.name,
            value: facetItem.value,
            checked: facetItem.checked,
            setChecked: function setChecked(facetItemName, facetItemChecked) {
                props.setChecked(props.facetName, facetItemName, facetItemChecked);
            }
        });
    });

    return _react2.default.createElement(
        'div',
        { className: 'margin-top-large' },
        _react2.default.createElement(
            'h5',
            null,
            capitalizeFirstLetter(props.facetName)
        ),
        facetItems
    );
};

Facet.propTypes = {
    facetName: _propTypes2.default.string.isRequired,
    facetItems: _propTypes2.default.arrayOf(_react2.default.PropTypes.shape({
        name: _propTypes2.default.string.isRequired,
        value: _propTypes2.default.string.isRequired,
        checked: _propTypes2.default.bool.isRequired
    })).isRequired,
    setChecked: _propTypes2.default.func.isRequired
};

exports.default = Facet;

/***/ }),

/***/ 1107:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/FacetItem.jsx ***!
  \*************************************************************************/
/*! no static exports found */
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

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var FacetItem = function FacetItem(props) {
    return _react2.default.createElement(
        'div',
        null,
        _react2.default.createElement('input', { type: 'checkbox', checked: props.checked, onChange: function onChange() {
                return props.setChecked(props.name, !props.checked);
            } }),
        _react2.default.createElement(
            'label',
            null,
            props.value
        )
    );
};

FacetItem.propTypes = {
    name: _propTypes2.default.string.isRequired,
    value: _propTypes2.default.string.isRequired,
    checked: _propTypes2.default.bool.isRequired,
    setChecked: _propTypes2.default.func.isRequired
};

exports.default = FacetItem;

/***/ }),

/***/ 1108:
/*!********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineHeatmaps.jsx ***!
  \********************************************************************/
/*! no static exports found */
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

var _BaselineHeatmapWidget = __webpack_require__(/*! ./BaselineHeatmapWidget.jsx */ 1109);

var _BaselineHeatmapWidget2 = _interopRequireDefault(_BaselineHeatmapWidget);

var _expressionAtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 232);

var _expressionAtlasFeedback2 = _interopRequireDefault(_expressionAtlasFeedback);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var BaselineHeatmaps = function (_React$Component) {
    _inherits(BaselineHeatmaps, _React$Component);

    function BaselineHeatmaps() {
        _classCallCheck(this, BaselineHeatmaps);

        return _possibleConstructorReturn(this, (BaselineHeatmaps.__proto__ || Object.getPrototypeOf(BaselineHeatmaps)).apply(this, arguments));
    }

    _createClass(BaselineHeatmaps, [{
        key: 'render',
        value: function render() {
            var _this2 = this;

            return _react2.default.createElement(
                'div',
                null,
                this.props.heatmaps.map(function (heatmap) {
                    return _react2.default.createElement(_BaselineHeatmapWidget2.default, {
                        key: heatmap.species + '_' + heatmap.factor.name,
                        showAnatomogram: _this2.props.showAnatomograms,
                        showHeatmapLabel: _this2._hasMoreThanOneSpecies(),
                        species: heatmap.species,
                        factor: heatmap.factor,
                        atlasUrl: _this2.props.atlasUrl,
                        geneQuery: _this2.props.geneQuery,
                        conditionQuery: _this2.props.conditionQuery });
                }),
                _react2.default.createElement(_expressionAtlasFeedback2.default, {
                    collectionCallback: typeof window.ga === 'function' ? function (score, comment) {
                        window.ga('send', 'event', 'BaselineHeatmaps', 'feedback', comment, score);
                    } : function () {}
                })
            );
        }
    }, {
        key: '_hasMoreThanOneSpecies',
        value: function _hasMoreThanOneSpecies() {
            var uniqueSpecies = new Set();
            this.props.heatmaps.forEach(function (heatmap) {
                uniqueSpecies.add(heatmap.species);
            });
            return uniqueSpecies.size > 1;
        }
    }]);

    return BaselineHeatmaps;
}(_react2.default.Component);

BaselineHeatmaps.propTypes = {
    atlasUrl: _propTypes2.default.string.isRequired,
    geneQuery: _propTypes2.default.string.isRequired,
    conditionQuery: _propTypes2.default.string,
    /*
     [{"species":"Homo sapiens", "factor":"CELL_LINE"}, {"species":"Mus musculus", "factor":"ORGANISM_PART"}]
     */
    showAnatomograms: _propTypes2.default.bool.isRequired,
    heatmaps: _propTypes2.default.arrayOf(_react2.default.PropTypes.shape({
        species: _propTypes2.default.string.isRequired,
        factor: _propTypes2.default.shape({
            name: _propTypes2.default.string.isRequired,
            value: _propTypes2.default.string.isRequired
        })
    })).isRequired
};

exports.default = BaselineHeatmaps;

/***/ }),

/***/ 1109:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineHeatmapWidget.jsx ***!
  \*************************************************************************/
/*! no static exports found */
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

var _expressionAtlasHeatmapHighcharts = __webpack_require__(/*! expression-atlas-heatmap-highcharts */ 137);

var _expressionAtlasHeatmapHighcharts2 = _interopRequireDefault(_expressionAtlasHeatmapHighcharts);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var capitalizeFirstLetter = function capitalizeFirstLetter(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
};

var BaselineHeatmapWidget = function BaselineHeatmapWidget(props) {
    return _react2.default.createElement(
        'div',
        { className: 'row column margin-top-large' },
        _react2.default.createElement(
            'h5',
            null,
            (props.showHeatmapLabel ? capitalizeFirstLetter(props.species) + ' \u2014 ' : '') + props.factor.value
        ),
        _react2.default.createElement(_expressionAtlasHeatmapHighcharts2.default, { atlasUrl: props.atlasUrl,
            query: {
                gene: props.geneQuery,
                condition: props.conditionQuery,
                species: props.species,
                source: props.factor.name
            },
            isWidget: false,
            showAnatomogram: props.showAnatomogram
        })
    );
};

BaselineHeatmapWidget.propTypes = {
    atlasUrl: _propTypes2.default.string.isRequired,
    geneQuery: _propTypes2.default.string.isRequired,
    conditionQuery: _propTypes2.default.string.isRequired,
    species: _propTypes2.default.string.isRequired,
    factor: _propTypes2.default.shape({
        name: _propTypes2.default.string.isRequired,
        value: _propTypes2.default.string.isRequired
    }).isRequired,
    showAnatomogram: _propTypes2.default.bool.isRequired,
    showHeatmapLabel: _propTypes2.default.bool.isRequired
};

exports.default = BaselineHeatmapWidget;

/***/ }),

/***/ 1110:
/*!*************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/urlManager.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Url = __webpack_require__(/*! url */ 223);
var QueryString = __webpack_require__(/*! querystring */ 224);

/**
 * Stringify the `query` object, assign it to the `bs` search field in the URL and store it in the History
 * @param {object} querySelect
 * @param {boolean} replace - use `replaceState` instead of `pushState`
 */
exports.baselinePush = function pushQueryIntoBrowserHistory(querySelect, replace) {
    var currentUrlObject = Url.parse(window.location.toString());

    var newUrlQueryParams = QueryString.parse(currentUrlObject.query);
    newUrlQueryParams.bs = JSON.stringify(querySelect);

    var newUrlObject = {
        protocol: currentUrlObject.protocol,
        host: currentUrlObject.host,
        hash: currentUrlObject.hash,
        pathname: currentUrlObject.pathname,
        query: newUrlQueryParams
    };

    if (replace) {
        history.replaceState(null, '', Url.format(newUrlObject));
    } else {
        history.pushState(null, '', Url.format(newUrlObject));
    }
};

exports.parseBaselineUrlParameter = function getQuerySelectFromLocation() {
    var location = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : window.location;

    var currentURL = Url.parse(location.toString());
    var differentialSelectParam = QueryString.parse(currentURL.query).bs;
    return differentialSelectParam ? JSON.parse(differentialSelectParam) : {};
};

/***/ }),

/***/ 232:
/*!*************************************************************!*\
  !*** ./node_modules/expression-atlas-feedback/lib/index.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Feedback = __webpack_require__(/*! ./Feedback */ 233);

var _Feedback2 = _interopRequireDefault(_Feedback);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Feedback2.default;

/***/ }),

/***/ 233:
/*!****************************************************************!*\
  !*** ./node_modules/expression-atlas-feedback/lib/Feedback.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactLocalstorage = __webpack_require__(/*! react-localstorage */ 234);

var _reactLocalstorage2 = _interopRequireDefault(_reactLocalstorage);

var _reactTimerMixin = __webpack_require__(/*! react-timer-mixin */ 236);

var _reactTimerMixin2 = _interopRequireDefault(_reactTimerMixin);

var _reactAddonsCssTransitionGroup = __webpack_require__(/*! react-addons-css-transition-group */ 237);

var _reactAddonsCssTransitionGroup2 = _interopRequireDefault(_reactAddonsCssTransitionGroup);

var _Button = __webpack_require__(/*! react-bootstrap/lib/Button */ 48);

var _Button2 = _interopRequireDefault(_Button);

var _FormGroup = __webpack_require__(/*! react-bootstrap/lib/FormGroup */ 204);

var _FormGroup2 = _interopRequireDefault(_FormGroup);

var _FormControl = __webpack_require__(/*! react-bootstrap/lib/FormControl */ 203);

var _FormControl2 = _interopRequireDefault(_FormControl);

var _emojioneSprites = __webpack_require__(/*! ./assets/emojione.sprites.png */ 244);

var _emojioneSprites2 = _interopRequireDefault(_emojioneSprites);

var _reactEmojione = __webpack_require__(/*! react-emojione */ 245);

__webpack_require__(/*! ./gxaFeedback.css */ 254);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var FeedbackPersistence = function createFeedbackComponent(FeedbackUIComponent) {
  return _react2.default.createClass({
    displayName: 'ExpressionAtlasFeedbackForm',
    mixins: [_reactLocalstorage2.default],

    propTypes: {
      collectionCallback: _react2.default.PropTypes.func.isRequired
    },

    getInitialState: function getInitialState() {
      return {
        created: new Date().toISOString(),
        shownTimes: 0,
        show: true
      };
    },

    _shouldShow: function _shouldShow() {
      var timeDiff = Math.abs(new Date().getTime() - new Date(this.state.created).getTime());
      var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

      return this.state.show && diffDays > 0 && this.state.shownTimes < 50;
    },

    _hide: function _hide() {
      this.setState({ show: false });
    },

    _complete: function _complete(userResponse, optionalUserComment) {
      this.setState({ show: false });
      this.props.collectionCallback(userResponse, new Date().toISOString() + (optionalUserComment || ""));
    },

    render: function render() {
      var it = this._shouldShow() ? _react2.default.createElement(FeedbackUIComponent, { key: "box", onComplete: this._complete, onRequestHide: this._hide }) : _react2.default.createElement('div', { key: 'nullKey' });
      return _react2.default.createElement(
        _reactAddonsCssTransitionGroup2.default,
        { transitionName: 'feedbackBoxTransitionWrapper', transitionEnterTimeout: 500, transitionLeaveTimeout: 1000 },
        it
      );
    },

    componentDidMount: function componentDidMount() {
      if (this._shouldShow()) {
        this.setState(function (previousState) {
          return { shownTimes: previousState.shownTimes + 1 };
        });
      }
    }
  });
};

var FeedbackBox = _react2.default.createClass({
  displayName: 'FeedbackBox',

  propTypes: {
    onComplete: _react2.default.PropTypes.func.isRequired,
    onRequestHide: _react2.default.PropTypes.func.isRequired
  },

  mixins: [_reactTimerMixin2.default],

  getInitialState: function getInitialState() {
    return {
      askingWhyTheResultsWereNotUseful: false,
      feedbackMessage: ""
    };
  },

  componentDidUpdate: function componentDidUpdate() {
    if (this.state.askingWhyTheResultsWereNotUseful && this.state.feedbackMessage.length === 0) {
      this.setTimeout(function () {
        if (this.state.feedbackMessage.length === 0) {
          this._submitNegativeAnswer();
        }
      }.bind(this), 5000);
    }
  },

  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
    this.setState({ feedbackMessage: e.target.value });
  },

  _submitNegativeAnswer: function _submitNegativeAnswer() {
    this._submitAnswer(0, this.state.feedbackMessage);
  },

  _submitPositiveAnswer: function _submitPositiveAnswer() {
    this._submitAnswer(10);
  },

  _submitAnswer: function _submitAnswer(score, optionalMessage) {
    this.props.onComplete.apply(this, arguments);
  },

  render: function render() {
    return _react2.default.createElement(
      'div',
      { className: 'gxaFeedbackQuestionBox' },
      _react2.default.createElement('div', { id: 'feedbackBoxCross', className: 'icon icon-functional', 'data-icon': 'x', onClick: this.props.onRequestHide }),
      _react2.default.createElement(
        'p',
        null,
        'Did you find these results useful?'
      ),
      _react2.default.createElement(
        'div',
        { className: 'gxaFeedbackQuestionBoxAnswer' },
        this.state.askingWhyTheResultsWereNotUseful ? _react2.default.createElement(
          'form',
          null,
          _react2.default.createElement(
            _FormGroup2.default,
            {
              controlId: 'optionalFeedback'
            },
            _react2.default.createElement(_FormControl2.default, {
              componentClass: 'textarea',
              type: 'text',
              value: this.state.feedbackMessage,
              placeholder: 'Why not? (optional)',
              onChange: this._updateStateWithFormAnswer
            }),
            _react2.default.createElement(_FormControl2.default.Feedback, null),
            _react2.default.createElement(
              _Button2.default,
              { style: { float: "right" }, onClick: this._submitNegativeAnswer },
              'Submit'
            )
          )
        ) : _react2.default.createElement(
          'div',
          null,
          _react2.default.createElement(
            _Button2.default,
            { bsStyle: 'default', onClick: this._submitPositiveAnswer },
            'Yes'
          ),
          _react2.default.createElement(
            _Button2.default,
            { onClick: function () {
                this.setState({ askingWhyTheResultsWereNotUseful: true });
              }.bind(this), bsStyle: 'default' },
            'No'
          ),
          _react2.default.createElement(
            'a',
            { onClick: this.props.onRequestHide },
            'Do not show this again'
          )
        )
      )
    );
  }
});

var Smiley = _react2.default.createClass({
  displayName: 'Smiley',

  propTypes: {
    emoji: _react2.default.PropTypes.string.isRequired,
    value: _react2.default.PropTypes.number.isRequired,
    onClickCallback: _react2.default.PropTypes.func.isRequired,
    selected: _react2.default.PropTypes.bool.isRequired
  },

  _onClick: function _onClick() {
    this.props.onClickCallback(this.props.value);
  },

  _emojifyOptions: {
    convertShortnames: true,
    convertUnicode: false,
    convertAscii: true,
    styles: {
      backgroundImage: 'url(' + _emojioneSprites2.default + ')',
      width: '32px',
      height: '32px',
      margin: '4px'
    }
  },

  render: function render() {
    return _react2.default.createElement(
      'span',
      { style: { padding: '6px' } },
      _react2.default.createElement(
        'span',
        { className: this.props.selected ? "gxaSmiley gxaSmileyClicked" : "gxaSmiley", onClick: this._onClick },
        (0, _reactEmojione.emojify)(this.props.emoji, this._emojifyOptions)
      )
    );
  }
});

var FeedbackSmileys = _react2.default.createClass({
  displayName: 'FeedbackSmileys',

  propTypes: {
    onComplete: _react2.default.PropTypes.func.isRequired,
    onRequestHide: _react2.default.PropTypes.func.isRequired
  },

  mixins: [_reactTimerMixin2.default],

  getInitialState: function getInitialState() {
    return {
      score: -1,
      feedbackMessage: ""
    };
  },

  _interactionHappened: function _interactionHappened() {
    return this.state.score !== this.getInitialState().score;
  },

  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
    this.setState({ feedbackMessage: e.target.value });
  },

  _smileyClicked: function _smileyClicked(newScore) {
    this.setState({ score: newScore });
  },

  _submit: function _submit() {
    this.props.onComplete(this.state.score, this.state.feedbackMessage);
  },

  componentDidUpdate: function componentDidUpdate() {
    if (this._interactionHappened() && this.state.feedbackMessage.length === 0) {
      this.setTimeout(function () {
        if (this.state.feedbackMessage.length === 0) {
          this._submit();
        }
      }.bind(this), 5000);
    }
  },

  render: function render() {
    /* identifiers from http://emoji.codes/ */
    return _react2.default.createElement(
      'div',
      { className: 'gxaSmileyFeedbackBox' },
      _react2.default.createElement(
        'p',
        null,
        ' Did you find these results useful?'
      ),
      _react2.default.createElement(
        'div',
        { className: 'gxaSmileyRow' },
        [[":frowning:", 0], [":slight_frown:", 2], [":neutral_face:", 5], [":slight_smile:", 8], [":smiley:", 10]].map(function (ar) {
          return _react2.default.createElement(Smiley, {
            key: ar[0] + (this.state.score === ar[1]),
            emoji: ar[0],
            value: ar[1],
            onClickCallback: this._smileyClicked,
            selected: this.state.score === ar[1]
          });
        }.bind(this))
      ),
      _react2.default.createElement(
        'form',
        { style: { display: this._interactionHappened() ? "block" : "none" } },
        _react2.default.createElement(
          _FormGroup2.default,
          {
            controlId: 'optionalFeedback'
          },
          _react2.default.createElement(_FormControl2.default, {
            componentClass: 'textarea',
            type: 'text',
            value: this.state.feedbackMessage,
            placeholder: 'Feedback (optional)',
            onChange: this._updateStateWithFormAnswer
          }),
          _react2.default.createElement(_FormControl2.default.Feedback, null),
          _react2.default.createElement(
            'div',
            null,
            _react2.default.createElement(
              _Button2.default,
              { onClick: this._submit },
              'Submit'
            )
          )
        )
      )
    );
  }

});

exports.default = FeedbackPersistence(FeedbackSmileys);

/***/ }),

/***/ 234:
/*!***************************************************************!*\
  !*** ./node_modules/react-localstorage/react-localstorage.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global, process) {
var warn = __webpack_require__(/*! ./lib/warning */ 235);
var hasLocalStorage = 'localStorage' in global;
var ls, testKey;

if (hasLocalStorage) {
  testKey = 'react-localstorage.mixin.test-key';
  try {
    // Access to global `localStorage` property must be guarded as it
    // fails under iOS private session mode.
    ls = global.localStorage;
    ls.setItem(testKey, 'foo');
    ls.removeItem(testKey);
  } catch (e) {
    hasLocalStorage = false;
  }
}

// Warn if localStorage cannot be found or accessed.
if (process.browser) {
  warn(
    hasLocalStorage,
    'localStorage not found. Component state will not be stored to localStorage.'
  );
}

module.exports = {
  /**
   * Error checking. On update, ensure that the last state stored in localStorage is equal
   * to the state on the component. We skip the check the first time around as state is left
   * alone until mount to keep server rendering working.
   *
   * If it is not consistent, we know that someone else is modifying localStorage out from under us, so we throw
   * an error.
   *
   * There are a lot of ways this can happen, so it is worth throwing the error.
   */
  componentWillUpdate: function(nextProps, nextState) {
    if (!hasLocalStorage || !this.__stateLoadedFromLS) return;
    var key = getLocalStorageKey(this);
    if (key === false) return;
    var prevStoredState = ls.getItem(key);
    if (prevStoredState && "development" !== "production") {
      warn(
        prevStoredState === JSON.stringify(getSyncState(this, this.state)),
        'While component ' + getDisplayName(this) + ' was saving state to localStorage, ' +
        'the localStorage entry was modified by another actor. This can happen when multiple ' +
        'components are using the same localStorage key. Set the property `localStorageKey` ' +
        'on ' + getDisplayName(this) + '.'
      );
    }
    // Since setState() can't be called in CWU, it's a fine time to save the state.
    ls.setItem(key, JSON.stringify(getSyncState(this, nextState)));
  },

  /**
   * Load data.
   * This seems odd to do this on componentDidMount, but it prevents server checksum errors.
   * This is because the server has no way to know what is in your localStorage. So instead
   * of breaking the checksum and causing a full rerender, we instead change the component after mount
   * for an efficient diff.
   */
  componentDidMount: function () {
    if (!hasLocalStorage) return;
    var me = this;
    loadStateFromLocalStorage(this, function() {
      // After setting state, mirror back to localstorage.
      // This prevents invariants if the developer has changed the initial state of the component.
      ls.setItem(getLocalStorageKey(me), JSON.stringify(getSyncState(me, me.state)));
    });
  }
};

function loadStateFromLocalStorage(component, cb) {
  if (!ls) return;
  var key = getLocalStorageKey(component);
  if (key === false) return;
  var settingState = false;
  try {
    var storedState = JSON.parse(ls.getItem(key));
    if (storedState) {
      settingState = true;
      component.setState(storedState, done);
    }
  } catch(e) {
    // eslint-disable-next-line no-console
    if (console) console.warn("Unable to load state for", getDisplayName(component), "from localStorage.");
  }
  // If we didn't set state, run the callback right away.
  if (!settingState) done();

  function done() {
    // Flag this component as loaded.
    component.__stateLoadedFromLS = true;
    cb();
  }
}

function getDisplayName(component) {
  // at least, we cannot get displayname
  // via this.displayname in react 0.12
  return component.displayName || component.constructor.displayName || component.constructor.name;
}

function getLocalStorageKey(component) {
  if (component.getLocalStorageKey) return component.getLocalStorageKey();
  if (component.props.localStorageKey === false) return false;
  if (typeof component.props.localStorageKey === 'function') return component.props.localStorageKey.call(component);
  return component.props.localStorageKey || getDisplayName(component) || 'react-localstorage';
}

function getStateFilterKeys(component) {
  if (component.getStateFilterKeys) {
    return typeof component.getStateFilterKeys() === 'string' ?
      [component.getStateFilterKeys()] : component.getStateFilterKeys();
  }
  return typeof component.props.stateFilterKeys === 'string' ?
    [component.props.stateFilterKeys] : component.props.stateFilterKeys;
}

/**
* Filters state to only save keys defined in stateFilterKeys.
* If stateFilterKeys is not set, returns full state.
*/
function getSyncState(component, state) {
  var stateFilterKeys = getStateFilterKeys(component);
  if (!stateFilterKeys || !state) return state;
  var result = {}, key;
  for (var i = 0; i < stateFilterKeys.length; i++) {
    key = stateFilterKeys[i];
    if (state.hasOwnProperty(key)) result[key] = state[key];
  }
  return result;
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../webpack/buildin/global.js */ 20), __webpack_require__(/*! ./../process/browser.js */ 33)))

/***/ }),

/***/ 235:
/*!********************************************************!*\
  !*** ./node_modules/react-localstorage/lib/warning.js ***!
  \********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2014 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @providesModule warning
 */



/**
 * Similar to invariant but only logs a warning if the condition is not met.
 * This can be used to log issues in development environments in critical
 * paths. Removing the logging code for production environments will keep the
 * same logic and follow the same code paths.
 */

var warning = function() {};

if (true) {
  warning = function(condition, format ) {var args=Array.prototype.slice.call(arguments,2);
    if (format === undefined) {
      throw new Error(
        '`warning(condition, format, ...args)` requires a warning ' +
        'message argument'
      );
    }

    if (!condition) {
      var argIndex = 0;
      // eslint-disable-next-line no-console
      console.warn('Warning: ' + format.replace(/%s/g, function()  {return args[argIndex++];}));
    }
  };
}

module.exports = warning;


/***/ }),

/***/ 236:
/*!******************************************************!*\
  !*** ./node_modules/react-timer-mixin/TimerMixin.js ***!
  \******************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {/*
 *  Copyright (c) 2015-present, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */


var GLOBAL = typeof window === 'undefined' ? global : window;

var setter = function(_setter, _clearer, array) {
  return function(callback, delta) {
    var id = _setter(function() {
      _clearer.call(this, id);
      callback.apply(this, arguments);
    }.bind(this), delta);

    if (!this[array]) {
      this[array] = [id];
    } else {
      this[array].push(id);
    }
    return id;
  };
};

var clearer = function(_clearer, array) {
  return function(id) {
    if (this[array]) {
      var index = this[array].indexOf(id);
      if (index !== -1) {
        this[array].splice(index, 1);
      }
    }
    _clearer(id);
  };
};

var _timeouts = 'TimerMixin_timeouts';
var _clearTimeout = clearer(GLOBAL.clearTimeout, _timeouts);
var _setTimeout = setter(GLOBAL.setTimeout, _clearTimeout, _timeouts);

var _intervals = 'TimerMixin_intervals';
var _clearInterval = clearer(GLOBAL.clearInterval, _intervals);
var _setInterval = setter(GLOBAL.setInterval, function() {/* noop */}, _intervals);

var _immediates = 'TimerMixin_immediates';
var _clearImmediate = clearer(GLOBAL.clearImmediate, _immediates);
var _setImmediate = setter(GLOBAL.setImmediate, _clearImmediate, _immediates);

var _rafs = 'TimerMixin_rafs';
var _cancelAnimationFrame = clearer(GLOBAL.cancelAnimationFrame, _rafs);
var _requestAnimationFrame = setter(GLOBAL.requestAnimationFrame, _cancelAnimationFrame, _rafs);

var TimerMixin = {
  componentWillUnmount: function() {
    this[_timeouts] && this[_timeouts].forEach(function(id) {
      GLOBAL.clearTimeout(id);
    });
    this[_timeouts] = null;
    this[_intervals] && this[_intervals].forEach(function(id) {
      GLOBAL.clearInterval(id);
    });
    this[_intervals] = null;
    this[_immediates] && this[_immediates].forEach(function(id) {
      GLOBAL.clearImmediate(id);
    });
    this[_immediates] = null;
    this[_rafs] && this[_rafs].forEach(function(id) {
      GLOBAL.cancelAnimationFrame(id);
    });
    this[_rafs] = null;
  },

  setTimeout: _setTimeout,
  clearTimeout: _clearTimeout,

  setInterval: _setInterval,
  clearInterval: _clearInterval,

  setImmediate: _setImmediate,
  clearImmediate: _clearImmediate,

  requestAnimationFrame: _requestAnimationFrame,
  cancelAnimationFrame: _cancelAnimationFrame,
};

module.exports = TimerMixin;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../webpack/buildin/global.js */ 20)))

/***/ }),

/***/ 237:
/*!*****************************************************************!*\
  !*** ./node_modules/react-addons-css-transition-group/index.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



module.exports = __webpack_require__(/*! react-transition-group/CSSTransitionGroup */ 238);


/***/ }),

/***/ 238:
/*!*******************************************************************!*\
  !*** ./node_modules/react-transition-group/CSSTransitionGroup.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _TransitionGroup = __webpack_require__(/*! ./TransitionGroup */ 239);

var _TransitionGroup2 = _interopRequireDefault(_TransitionGroup);

var _CSSTransitionGroupChild = __webpack_require__(/*! ./CSSTransitionGroupChild */ 242);

var _CSSTransitionGroupChild2 = _interopRequireDefault(_CSSTransitionGroupChild);

var _PropTypes = __webpack_require__(/*! ./utils/PropTypes */ 100);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var propTypes = {
  transitionName: _PropTypes.nameShape.isRequired,

  transitionAppear: _propTypes2.default.bool,
  transitionEnter: _propTypes2.default.bool,
  transitionLeave: _propTypes2.default.bool,
  transitionAppearTimeout: (0, _PropTypes.transitionTimeout)('Appear'),
  transitionEnterTimeout: (0, _PropTypes.transitionTimeout)('Enter'),
  transitionLeaveTimeout: (0, _PropTypes.transitionTimeout)('Leave')
};

var defaultProps = {
  transitionAppear: false,
  transitionEnter: true,
  transitionLeave: true
};

var CSSTransitionGroup = function (_React$Component) {
  _inherits(CSSTransitionGroup, _React$Component);

  function CSSTransitionGroup() {
    var _temp, _this, _ret;

    _classCallCheck(this, CSSTransitionGroup);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this._wrapChild = function (child) {
      return _react2.default.createElement(_CSSTransitionGroupChild2.default, {
        name: _this.props.transitionName,
        appear: _this.props.transitionAppear,
        enter: _this.props.transitionEnter,
        leave: _this.props.transitionLeave,
        appearTimeout: _this.props.transitionAppearTimeout,
        enterTimeout: _this.props.transitionEnterTimeout,
        leaveTimeout: _this.props.transitionLeaveTimeout
      }, child);
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  // We need to provide this childFactory so that
  // ReactCSSTransitionGroupChild can receive updates to name, enter, and
  // leave while it is leaving.


  CSSTransitionGroup.prototype.render = function render() {
    return _react2.default.createElement(_TransitionGroup2.default, _extends({}, this.props, { childFactory: this._wrapChild }));
  };

  return CSSTransitionGroup;
}(_react2.default.Component);

CSSTransitionGroup.displayName = 'CSSTransitionGroup';


CSSTransitionGroup.propTypes =  true ? propTypes : {};
CSSTransitionGroup.defaultProps = defaultProps;

exports.default = CSSTransitionGroup;
module.exports = exports['default'];

/***/ }),

/***/ 239:
/*!****************************************************************!*\
  !*** ./node_modules/react-transition-group/TransitionGroup.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _chainFunction = __webpack_require__(/*! chain-function */ 240);

var _chainFunction2 = _interopRequireDefault(_chainFunction);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _warning = __webpack_require__(/*! warning */ 15);

var _warning2 = _interopRequireDefault(_warning);

var _ChildMapping = __webpack_require__(/*! ./utils/ChildMapping */ 241);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var propTypes = {
  component: _propTypes2.default.any,
  childFactory: _propTypes2.default.func,
  children: _propTypes2.default.node
};

var defaultProps = {
  component: 'span',
  childFactory: function childFactory(child) {
    return child;
  }
};

var TransitionGroup = function (_React$Component) {
  _inherits(TransitionGroup, _React$Component);

  function TransitionGroup(props, context) {
    _classCallCheck(this, TransitionGroup);

    var _this = _possibleConstructorReturn(this, _React$Component.call(this, props, context));

    _this.performAppear = function (key, component) {
      _this.currentlyTransitioningKeys[key] = true;

      if (component.componentWillAppear) {
        component.componentWillAppear(_this._handleDoneAppearing.bind(_this, key, component));
      } else {
        _this._handleDoneAppearing(key, component);
      }
    };

    _this._handleDoneAppearing = function (key, component) {
      if (component.componentDidAppear) {
        component.componentDidAppear();
      }

      delete _this.currentlyTransitioningKeys[key];

      var currentChildMapping = (0, _ChildMapping.getChildMapping)(_this.props.children);

      if (!currentChildMapping || !currentChildMapping.hasOwnProperty(key)) {
        // This was removed before it had fully appeared. Remove it.
        _this.performLeave(key, component);
      }
    };

    _this.performEnter = function (key, component) {
      _this.currentlyTransitioningKeys[key] = true;

      if (component.componentWillEnter) {
        component.componentWillEnter(_this._handleDoneEntering.bind(_this, key, component));
      } else {
        _this._handleDoneEntering(key, component);
      }
    };

    _this._handleDoneEntering = function (key, component) {
      if (component.componentDidEnter) {
        component.componentDidEnter();
      }

      delete _this.currentlyTransitioningKeys[key];

      var currentChildMapping = (0, _ChildMapping.getChildMapping)(_this.props.children);

      if (!currentChildMapping || !currentChildMapping.hasOwnProperty(key)) {
        // This was removed before it had fully entered. Remove it.
        _this.performLeave(key, component);
      }
    };

    _this.performLeave = function (key, component) {
      _this.currentlyTransitioningKeys[key] = true;

      if (component.componentWillLeave) {
        component.componentWillLeave(_this._handleDoneLeaving.bind(_this, key, component));
      } else {
        // Note that this is somewhat dangerous b/c it calls setState()
        // again, effectively mutating the component before all the work
        // is done.
        _this._handleDoneLeaving(key, component);
      }
    };

    _this._handleDoneLeaving = function (key, component) {
      if (component.componentDidLeave) {
        component.componentDidLeave();
      }

      delete _this.currentlyTransitioningKeys[key];

      var currentChildMapping = (0, _ChildMapping.getChildMapping)(_this.props.children);

      if (currentChildMapping && currentChildMapping.hasOwnProperty(key)) {
        // This entered again before it fully left. Add it again.
        _this.keysToEnter.push(key);
      } else {
        _this.setState(function (state) {
          var newChildren = _extends({}, state.children);
          delete newChildren[key];
          return { children: newChildren };
        });
      }
    };

    _this.childRefs = Object.create(null);

    _this.state = {
      children: (0, _ChildMapping.getChildMapping)(props.children)
    };
    return _this;
  }

  TransitionGroup.prototype.componentWillMount = function componentWillMount() {
    this.currentlyTransitioningKeys = {};
    this.keysToEnter = [];
    this.keysToLeave = [];
  };

  TransitionGroup.prototype.componentDidMount = function componentDidMount() {
    var initialChildMapping = this.state.children;
    for (var key in initialChildMapping) {
      if (initialChildMapping[key]) {
        this.performAppear(key, this.childRefs[key]);
      }
    }
  };

  TransitionGroup.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps) {
    var nextChildMapping = (0, _ChildMapping.getChildMapping)(nextProps.children);
    var prevChildMapping = this.state.children;

    this.setState({
      children: (0, _ChildMapping.mergeChildMappings)(prevChildMapping, nextChildMapping)
    });

    for (var key in nextChildMapping) {
      var hasPrev = prevChildMapping && prevChildMapping.hasOwnProperty(key);
      if (nextChildMapping[key] && !hasPrev && !this.currentlyTransitioningKeys[key]) {
        this.keysToEnter.push(key);
      }
    }

    for (var _key in prevChildMapping) {
      var hasNext = nextChildMapping && nextChildMapping.hasOwnProperty(_key);
      if (prevChildMapping[_key] && !hasNext && !this.currentlyTransitioningKeys[_key]) {
        this.keysToLeave.push(_key);
      }
    }

    // If we want to someday check for reordering, we could do it here.
  };

  TransitionGroup.prototype.componentDidUpdate = function componentDidUpdate() {
    var _this2 = this;

    var keysToEnter = this.keysToEnter;
    this.keysToEnter = [];
    keysToEnter.forEach(function (key) {
      return _this2.performEnter(key, _this2.childRefs[key]);
    });

    var keysToLeave = this.keysToLeave;
    this.keysToLeave = [];
    keysToLeave.forEach(function (key) {
      return _this2.performLeave(key, _this2.childRefs[key]);
    });
  };

  TransitionGroup.prototype.render = function render() {
    var _this3 = this;

    // TODO: we could get rid of the need for the wrapper node
    // by cloning a single child
    var childrenToRender = [];

    var _loop = function _loop(key) {
      var child = _this3.state.children[key];
      if (child) {
        var isCallbackRef = typeof child.ref !== 'string';
        var factoryChild = _this3.props.childFactory(child);
        var ref = function ref(r) {
          _this3.childRefs[key] = r;
        };

         true ? (0, _warning2.default)(isCallbackRef, 'string refs are not supported on children of TransitionGroup and will be ignored. ' + 'Please use a callback ref instead: https://facebook.github.io/react/docs/refs-and-the-dom.html#the-ref-callback-attribute') : void 0;

        // Always chaining the refs leads to problems when the childFactory
        // wraps the child. The child ref callback gets called twice with the
        // wrapper and the child. So we only need to chain the ref if the
        // factoryChild is not different from child.
        if (factoryChild === child && isCallbackRef) {
          ref = (0, _chainFunction2.default)(child.ref, ref);
        }

        // You may need to apply reactive updates to a child as it is leaving.
        // The normal React way to do it won't work since the child will have
        // already been removed. In case you need this behavior you can provide
        // a childFactory function to wrap every child, even the ones that are
        // leaving.
        childrenToRender.push(_react2.default.cloneElement(factoryChild, {
          key: key,
          ref: ref
        }));
      }
    };

    for (var key in this.state.children) {
      _loop(key);
    }

    // Do not forward TransitionGroup props to primitive DOM nodes
    var props = _extends({}, this.props);
    delete props.transitionLeave;
    delete props.transitionName;
    delete props.transitionAppear;
    delete props.transitionEnter;
    delete props.childFactory;
    delete props.transitionLeaveTimeout;
    delete props.transitionEnterTimeout;
    delete props.transitionAppearTimeout;
    delete props.component;

    return _react2.default.createElement(this.props.component, props, childrenToRender);
  };

  return TransitionGroup;
}(_react2.default.Component);

TransitionGroup.displayName = 'TransitionGroup';


TransitionGroup.propTypes =  true ? propTypes : {};
TransitionGroup.defaultProps = defaultProps;

exports.default = TransitionGroup;
module.exports = exports['default'];

/***/ }),

/***/ 240:
/*!**********************************************!*\
  !*** ./node_modules/chain-function/index.js ***!
  \**********************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports) {


module.exports = function chain(){
  var len = arguments.length
  var args = [];

  for (var i = 0; i < len; i++)
    args[i] = arguments[i]

  args = args.filter(function(fn){ return fn != null })

  if (args.length === 0) return undefined
  if (args.length === 1) return args[0]

  return args.reduce(function(current, next){
    return function chainedFunction() {
      current.apply(this, arguments);
      next.apply(this, arguments);
    };
  })
}


/***/ }),

/***/ 241:
/*!*******************************************************************!*\
  !*** ./node_modules/react-transition-group/utils/ChildMapping.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.getChildMapping = getChildMapping;
exports.mergeChildMappings = mergeChildMappings;

var _react = __webpack_require__(/*! react */ 0);

/**
 * Given `this.props.children`, return an object mapping key to child.
 *
 * @param {*} children `this.props.children`
 * @return {object} Mapping of key to child
 */
function getChildMapping(children) {
  if (!children) {
    return children;
  }
  var result = {};
  _react.Children.map(children, function (child) {
    return child;
  }).forEach(function (child) {
    result[child.key] = child;
  });
  return result;
}

/**
 * When you're adding or removing children some may be added or removed in the
 * same render pass. We want to show *both* since we want to simultaneously
 * animate elements in and out. This function takes a previous set of keys
 * and a new set of keys and merges them with its best guess of the correct
 * ordering. In the future we may expose some of the utilities in
 * ReactMultiChild to make this easy, but for now React itself does not
 * directly have this concept of the union of prevChildren and nextChildren
 * so we implement it here.
 *
 * @param {object} prev prev children as returned from
 * `ReactTransitionChildMapping.getChildMapping()`.
 * @param {object} next next children as returned from
 * `ReactTransitionChildMapping.getChildMapping()`.
 * @return {object} a key set that contains all keys in `prev` and all keys
 * in `next` in a reasonable order.
 */
function mergeChildMappings(prev, next) {
  prev = prev || {};
  next = next || {};

  function getValueForKey(key) {
    if (next.hasOwnProperty(key)) {
      return next[key];
    }

    return prev[key];
  }

  // For each key of `next`, the list of keys to insert before that key in
  // the combined list
  var nextKeysPending = {};

  var pendingKeys = [];
  for (var prevKey in prev) {
    if (next.hasOwnProperty(prevKey)) {
      if (pendingKeys.length) {
        nextKeysPending[prevKey] = pendingKeys;
        pendingKeys = [];
      }
    } else {
      pendingKeys.push(prevKey);
    }
  }

  var i = void 0;
  var childMapping = {};
  for (var nextKey in next) {
    if (nextKeysPending.hasOwnProperty(nextKey)) {
      for (i = 0; i < nextKeysPending[nextKey].length; i++) {
        var pendingNextKey = nextKeysPending[nextKey][i];
        childMapping[nextKeysPending[nextKey][i]] = getValueForKey(pendingNextKey);
      }
    }
    childMapping[nextKey] = getValueForKey(nextKey);
  }

  // Finally, add the keys which didn't appear before any key in `next`
  for (i = 0; i < pendingKeys.length; i++) {
    childMapping[pendingKeys[i]] = getValueForKey(pendingKeys[i]);
  }

  return childMapping;
}

/***/ }),

/***/ 242:
/*!************************************************************************!*\
  !*** ./node_modules/react-transition-group/CSSTransitionGroupChild.js ***!
  \************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _addClass = __webpack_require__(/*! dom-helpers/class/addClass */ 208);

var _addClass2 = _interopRequireDefault(_addClass);

var _removeClass = __webpack_require__(/*! dom-helpers/class/removeClass */ 209);

var _removeClass2 = _interopRequireDefault(_removeClass);

var _requestAnimationFrame = __webpack_require__(/*! dom-helpers/util/requestAnimationFrame */ 243);

var _requestAnimationFrame2 = _interopRequireDefault(_requestAnimationFrame);

var _properties = __webpack_require__(/*! dom-helpers/transition/properties */ 130);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 1);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactDom = __webpack_require__(/*! react-dom */ 11);

var _PropTypes = __webpack_require__(/*! ./utils/PropTypes */ 100);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var events = [];
if (_properties.transitionEnd) events.push(_properties.transitionEnd);
if (_properties.animationEnd) events.push(_properties.animationEnd);

function addEndListener(node, listener) {
  if (events.length) {
    events.forEach(function (e) {
      return node.addEventListener(e, listener, false);
    });
  } else {
    setTimeout(listener, 0);
  }

  return function () {
    if (!events.length) return;
    events.forEach(function (e) {
      return node.removeEventListener(e, listener, false);
    });
  };
}

var propTypes = {
  children: _propTypes2.default.node,
  name: _PropTypes.nameShape.isRequired,

  // Once we require timeouts to be specified, we can remove the
  // boolean flags (appear etc.) and just accept a number
  // or a bool for the timeout flags (appearTimeout etc.)
  appear: _propTypes2.default.bool,
  enter: _propTypes2.default.bool,
  leave: _propTypes2.default.bool,
  appearTimeout: _propTypes2.default.number,
  enterTimeout: _propTypes2.default.number,
  leaveTimeout: _propTypes2.default.number
};

var CSSTransitionGroupChild = function (_React$Component) {
  _inherits(CSSTransitionGroupChild, _React$Component);

  function CSSTransitionGroupChild() {
    var _temp, _this, _ret;

    _classCallCheck(this, CSSTransitionGroupChild);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.componentWillAppear = function (done) {
      if (_this.props.appear) {
        _this.transition('appear', done, _this.props.appearTimeout);
      } else {
        done();
      }
    }, _this.componentWillEnter = function (done) {
      if (_this.props.enter) {
        _this.transition('enter', done, _this.props.enterTimeout);
      } else {
        done();
      }
    }, _this.componentWillLeave = function (done) {
      if (_this.props.leave) {
        _this.transition('leave', done, _this.props.leaveTimeout);
      } else {
        done();
      }
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  CSSTransitionGroupChild.prototype.componentWillMount = function componentWillMount() {
    this.classNameAndNodeQueue = [];
    this.transitionTimeouts = [];
  };

  CSSTransitionGroupChild.prototype.componentWillUnmount = function componentWillUnmount() {
    this.unmounted = true;

    if (this.timeout) {
      clearTimeout(this.timeout);
    }
    this.transitionTimeouts.forEach(function (timeout) {
      clearTimeout(timeout);
    });

    this.classNameAndNodeQueue.length = 0;
  };

  CSSTransitionGroupChild.prototype.transition = function transition(animationType, finishCallback, timeout) {
    var node = (0, _reactDom.findDOMNode)(this);

    if (!node) {
      if (finishCallback) {
        finishCallback();
      }
      return;
    }

    var className = this.props.name[animationType] || this.props.name + '-' + animationType;
    var activeClassName = this.props.name[animationType + 'Active'] || className + '-active';
    var timer = null;
    var removeListeners = void 0;

    (0, _addClass2.default)(node, className);

    // Need to do this to actually trigger a transition.
    this.queueClassAndNode(activeClassName, node);

    // Clean-up the animation after the specified delay
    var finish = function finish(e) {
      if (e && e.target !== node) {
        return;
      }

      clearTimeout(timer);
      if (removeListeners) removeListeners();

      (0, _removeClass2.default)(node, className);
      (0, _removeClass2.default)(node, activeClassName);

      if (removeListeners) removeListeners();

      // Usually this optional callback is used for informing an owner of
      // a leave animation and telling it to remove the child.
      if (finishCallback) {
        finishCallback();
      }
    };

    if (timeout) {
      timer = setTimeout(finish, timeout);
      this.transitionTimeouts.push(timer);
    } else if (_properties.transitionEnd) {
      removeListeners = addEndListener(node, finish);
    }
  };

  CSSTransitionGroupChild.prototype.queueClassAndNode = function queueClassAndNode(className, node) {
    var _this2 = this;

    this.classNameAndNodeQueue.push({
      className: className,
      node: node
    });

    if (!this.rafHandle) {
      this.rafHandle = (0, _requestAnimationFrame2.default)(function () {
        return _this2.flushClassNameAndNodeQueue();
      });
    }
  };

  CSSTransitionGroupChild.prototype.flushClassNameAndNodeQueue = function flushClassNameAndNodeQueue() {
    if (!this.unmounted) {
      this.classNameAndNodeQueue.forEach(function (obj) {
        // This is for to force a repaint,
        // which is necessary in order to transition styles when adding a class name.
        /* eslint-disable no-unused-expressions */
        obj.node.scrollTop;
        /* eslint-enable no-unused-expressions */
        (0, _addClass2.default)(obj.node, obj.className);
      });
    }
    this.classNameAndNodeQueue.length = 0;
    this.rafHandle = null;
  };

  CSSTransitionGroupChild.prototype.render = function render() {
    var props = _extends({}, this.props);
    delete props.name;
    delete props.appear;
    delete props.enter;
    delete props.leave;
    delete props.appearTimeout;
    delete props.enterTimeout;
    delete props.leaveTimeout;
    delete props.children;
    return _react2.default.cloneElement(_react2.default.Children.only(this.props.children), props);
  };

  return CSSTransitionGroupChild;
}(_react2.default.Component);

CSSTransitionGroupChild.displayName = 'CSSTransitionGroupChild';


CSSTransitionGroupChild.propTypes =  true ? propTypes : {};

exports.default = CSSTransitionGroupChild;
module.exports = exports['default'];

/***/ }),

/***/ 243:
/*!****************************************************************!*\
  !*** ./node_modules/dom-helpers/util/requestAnimationFrame.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _inDOM = __webpack_require__(/*! ./inDOM */ 40);

var _inDOM2 = _interopRequireDefault(_inDOM);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var vendors = ['', 'webkit', 'moz', 'o', 'ms'];
var cancel = 'clearTimeout';
var raf = fallback;
var compatRaf = void 0;

var getKey = function getKey(vendor, k) {
  return vendor + (!vendor ? k : k[0].toUpperCase() + k.substr(1)) + 'AnimationFrame';
};

if (_inDOM2.default) {
  vendors.some(function (vendor) {
    var rafKey = getKey(vendor, 'request');

    if (rafKey in window) {
      cancel = getKey(vendor, 'cancel');
      return raf = function raf(cb) {
        return window[rafKey](cb);
      };
    }
  });
}

/* https://github.com/component/raf */
var prev = new Date().getTime();
function fallback(fn) {
  var curr = new Date().getTime(),
      ms = Math.max(0, 16 - (curr - prev)),
      req = setTimeout(fn, ms);

  prev = curr;
  return req;
}

compatRaf = function compatRaf(cb) {
  return raf(cb);
};
compatRaf.cancel = function (id) {
  window[cancel] && typeof window[cancel] === 'function' && window[cancel](id);
};
exports.default = compatRaf;
module.exports = exports['default'];

/***/ }),

/***/ 244:
/*!********************************************************************************!*\
  !*** ./node_modules/expression-atlas-feedback/lib/assets/emojione.sprites.png ***!
  \********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "72e306f1246f69de2c83c8d3c3141177.png";

/***/ }),

/***/ 245:
/*!**********************************************!*\
  !*** ./node_modules/react-emojione/index.js ***!
  \**********************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ./lib/react-emojione */ 246);


/***/ }),

/***/ 246:
/*!***********************************************************!*\
  !*** ./node_modules/react-emojione/lib/react-emojione.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.emojify = undefined;

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }(); /*!
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          * react-emojione
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          * Copyright(c) 2017 Pedro Ladaria
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          * MIT Licensed
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          *
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          * Emoji provided free by http://emojione.com
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          */


var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _asciiToUnicode = __webpack_require__(/*! ./data/ascii-to-unicode */ 247);

var _asciiToUnicode2 = _interopRequireDefault(_asciiToUnicode);

var _rendererFactory = __webpack_require__(/*! ./renderers/renderer-factory */ 248);

var _rendererFactory2 = _interopRequireDefault(_rendererFactory);

var _emojiFormatConversion = __webpack_require__(/*! ./utils/emoji-format-conversion */ 64);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var DEFAULT_OPTIONS = {
    convertShortnames: true,
    convertUnicode: true,
    convertAscii: true,
    style: {
        backgroundImage: 'url(https://cdnjs.cloudflare.com/ajax/libs/emojione/2.2.7/assets/sprites/emojione.sprites.png)'
    },
    onClick: undefined,
    output: 'emoji' // valid options: 'emoji', 'unicode'
};

var asciiToUnicodeCache = new Map();
var asciiRegExpToUnicode = new Map();

_asciiToUnicode2.default.forEach(function (_ref) {
    var _ref2 = _slicedToArray(_ref, 2),
        reStr = _ref2[0],
        unicode = _ref2[1];

    return asciiRegExpToUnicode.set(RegExp(reStr), unicode);
});

// Escape RegExp code borrowed from lodash
var reRegExpChar = /[\\^$.*+?()[\]{}|]/g;
var reHasRegExpChar = RegExp(reRegExpChar.source);
var escapeRegExp = function escapeRegExp(s) {
    return s && reHasRegExpChar.test(s) ? s.replace(reRegExpChar, '\\$&') : s;
};

var convertAsciiToUnicodeOrNull = function convertAsciiToUnicodeOrNull(text) {
    if (!text) {
        return '';
    }
    var str = String(text);
    if (asciiToUnicodeCache.has(str)) {
        return asciiToUnicodeCache.get(str);
    }
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
        for (var _iterator = asciiRegExpToUnicode.entries()[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
            var _step$value = _slicedToArray(_step.value, 2),
                regExp = _step$value[0],
                unicode = _step$value[1];

            if (str.replace(regExp, unicode) === unicode) {
                asciiToUnicodeCache.set(str, unicode);
                return unicode;
            }
        }
    } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
    } finally {
        try {
            if (!_iteratorNormalCompletion && _iterator.return) {
                _iterator.return();
            }
        } finally {
            if (_didIteratorError) {
                throw _iteratorError;
            }
        }
    }

    return null;
};

var asciiRegexStr = _asciiToUnicode2.default.map(function (_ref3) {
    var _ref4 = _slicedToArray(_ref3, 1),
        reStr = _ref4[0];

    return reStr;
}).join('|');
var unicodesRegexStr = _emojiFormatConversion.unicodes.map(escapeRegExp).join('|');
var shortnamesRegexStr = ':\\w+:';

var REGEX_CACHE = [];

var getRegex = function getRegex(withUnicode, withAscii, withShortnames) {
    var index = (withUnicode ? 1 : 0) + (withAscii ? 2 : 0) + (withShortnames ? 4 : 0);
    if (!REGEX_CACHE[index]) {
        var parts = [withShortnames ? shortnamesRegexStr : '', withUnicode ? unicodesRegexStr : '', withAscii ? asciiRegexStr : ''].filter(Boolean);
        REGEX_CACHE[index] = RegExp('(' + parts.join('|') + ')');
    }
    return REGEX_CACHE[index];
};

var startsWithSpace = function startsWithSpace(str) {
    return (/^\s/.test(str)
    );
};
var endsWithSpace = function endsWithSpace(str) {
    return (/\s$/.test(str)
    );
};

var shouldConvertAscii = function shouldConvertAscii(parts, index) {
    if (parts.length === 1) {
        return true;
    }
    if (index === 0) {
        return startsWithSpace(parts[index + 1]);
    }
    if (index === parts.length - 1) {
        return endsWithSpace(parts[index - 1]);
    }
    return endsWithSpace(parts[index - 1]) && startsWithSpace(parts[index + 1]);
};

var emojify = exports.emojify = function emojify(str) {
    var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};


    var mergedOptions = Object.assign({}, DEFAULT_OPTIONS, options);

    var convertShortnames = mergedOptions.convertShortnames,
        convertUnicode = mergedOptions.convertUnicode,
        convertAscii = mergedOptions.convertAscii;


    var regExp = getRegex(convertUnicode, convertAscii, convertShortnames);

    var renderCodepoint = (0, _rendererFactory2.default)(mergedOptions);

    var convertedParts = str.split(regExp).filter(Boolean).map(function (part, index, parts) {
        if (convertAscii && shouldConvertAscii(parts, index)) {
            var unicode = convertAsciiToUnicodeOrNull(part);
            if (unicode) {
                return renderCodepoint(_emojiFormatConversion.unicodeToCodepoint.get(unicode), 'a-' + index);
            }
        }
        if (convertShortnames && _emojiFormatConversion.shortToCodepoint.has(part)) {
            return renderCodepoint(_emojiFormatConversion.shortToCodepoint.get(part), 's-' + index);
        }
        if (convertUnicode && _emojiFormatConversion.unicodeToCodepoint.has(part)) {
            return renderCodepoint(_emojiFormatConversion.unicodeToCodepoint.get(part), 'u-' + index);
        }
        return part;
    });

    return mergedOptions.output === 'unicode' ? convertedParts.join('') : convertedParts;
};

var Emojify = function (_React$Component) {
    _inherits(Emojify, _React$Component);

    function Emojify() {
        _classCallCheck(this, Emojify);

        return _possibleConstructorReturn(this, (Emojify.__proto__ || Object.getPrototypeOf(Emojify)).apply(this, arguments));
    }

    _createClass(Emojify, [{
        key: 'traverse',
        value: function traverse(children, options) {
            var _this2 = this;

            return _react2.default.Children.map(children, function (child) {
                if (_react2.default.isValidElement(child)) {
                    return _react2.default.cloneElement(child, {}, _this2.traverse(child.props.children, options));
                }
                if (typeof child === 'string') {
                    return emojify(child, options);
                }
                return child;
            });
        }
    }, {
        key: 'render',
        value: function render() {
            var children = this.props.children;
            return _react2.default.Children.count(children) ? _react2.default.createElement('span', {}, this.traverse(children, this.props)) : null;
        }
    }]);

    return Emojify;
}(_react2.default.Component);

exports.default = Emojify;

/***/ }),

/***/ 247:
/*!******************************************************************!*\
  !*** ./node_modules/react-emojione/lib/data/ascii-to-unicode.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
/**
 * Ascii smiley to unicode table
 *
 * The order is important! O:) must be before :)
 */
exports.default = [
// angry
['>?:-?\\[', '😡'], // >:[ >:-[ :[ :-[
['>:-?\\(', '😠'], // >:( >:-(
['\\]:-?[\\/]', '👿'], // ]:\ ]:/ ]:-\ ]:-/
// sweat
['-_+-[uU]', '😓'], // -_-u -__-u ...
["':-?\\|", '😓'], // ':| ':-|
["':-?\\[", '😰'], // ':[ ':-[
["':-?\\(", '😥'], // ':( ':-(
["':-?\\)", '😅'], // ':) ':-)
['\\^_*\\^[uU]', '😅'], // ^_^u ^__^U ...
["'=-?\\)", '😅'], // '=) '=-)
// cool
['B-?\\)', '😎'], // B) B-)
[']:-?\\)', '😈'], [':-?,', '😏'], // :, :-,
['[oO]:-?\\)', '😇'], // o:) O:) o:-) ...
// happy
[':-?\\)', '🙂'], // :) :-)
[':-?D', '😃'], // :D :-D
['=-?\\)', '😊'], // =) =-)
[':-?>', '😁'], // :> :->
['[xX]-?DD+', '😂'], // xDDD x-DDD
['[xX]-?D', '😆'], // xD x-D
['[xX]\'D', '😂'], // x'D
['\\^_*\\^', '😄'], // ^^ ^_^ ^__^ ...
// sad
[':-?\\(', '🙁'], // :( :-(
// wink
[';-?\\)', '😉'], // ;) ;-)
// tonge
[':-?[pPÞþ]', '😛'], // :p :P :Þ :þ :-p :-P :-Þ :-þ
[';-?[pPÞþ]', '😜'], // ;p ;P ;Þ ;þ ;-p ;-P ;-Þ ;-þ
['[:;]-?[d]', '😋'], // :d :-d ;d ;-d
['[xX]-[pPÞþd]', '😝'], // x-p X-P
// love
['<3', '❤'], // <3
['<[\\\\/]3', '💔'], // <\3 </3
['=-?\\*', '😙'], //  =* =-*
[';-?\\*+', '😘'], // ;* ;-* ;** ;-** ...
[':-?\\*\\*+', '😘'], // :* :-*
[':-?\\*+', '😗'], // :* :-*
// cry
['[:;=][\'_]-?\\(', '😢'], ['[xX][\'_]-?\\(', '😭'], ['T_+T', '😭'], // T_T T__T ...
// confused / surprise
[':-?[sS]', '😖'], // :s :-s :S :-S
[':-?[oO]', '😮'], // :o :O :-o :-O
// sleep
['-_+-[zZ]+', '😪'], // -_-zZz ...
['u_+u[zZ]+', '😴'], // u_uzZz ...
// scared
['D-?:', '😦'], // D: D-:
[':-?[cC]', '😦'], // :c :-c :C :-C
['D-X', '😫'], // D-X
['[x]_+[xX]', '😲'], // x_x x__x ...
['[X]_+[xX]', '😵'], // X_X X__X ...
['[oO]_+[oO]', '😱'], // o_o o__O O___O ...
// misc
['-_+-', '😑'], // -_- -__- -___- ...
[':-?\\\\', '😕'], // :\ :-\
[':-?/(?!\\/)', '😕'], // :/ :-/
[':-?\\|', '😐'], // :| :-|
['[uv]_+[uv]', '😔'], // u_u v_v u__u v___v ...
['[xX]-?\\(', '😣'], // x( x-( X( X-(
['¬_*¬', '😒'] // ¬¬ ¬_¬ ¬__¬ ...
];

/***/ }),

/***/ 248:
/*!***********************************************************************!*\
  !*** ./node_modules/react-emojione/lib/renderers/renderer-factory.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _emojiRenderer = __webpack_require__(/*! ./emoji-renderer */ 249);

var _emojiRenderer2 = _interopRequireDefault(_emojiRenderer);

var _unicodeRenderer = __webpack_require__(/*! ./unicode-renderer */ 253);

var _unicodeRenderer2 = _interopRequireDefault(_unicodeRenderer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var rendererForOutputFormat = {
    emoji: _emojiRenderer2.default,
    unicode: _unicodeRenderer2.default
};

var getRenderer = function getRenderer(config) {
    var renderer = rendererForOutputFormat[config.output] || rendererForOutputFormat.emoji;
    return renderer(config);
};

exports.default = getRenderer;

/***/ }),

/***/ 249:
/*!*********************************************************************!*\
  !*** ./node_modules/react-emojione/lib/renderers/emoji-renderer.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _emojioneSprite = __webpack_require__(/*! ../styles/emojione-sprite */ 250);

var _emojiFormatConversion = __webpack_require__(/*! ../utils/emoji-format-conversion */ 64);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var Emoji = function Emoji(_ref) {
    var codepoint = _ref.codepoint,
        _ref$style = _ref.style,
        style = _ref$style === undefined ? {} : _ref$style,
        onClick = _ref.onClick;
    return _react2.default.createElement(
        'span',
        {
            onClick: onClick,
            style: (0, _emojioneSprite.sprite)(codepoint, style),
            title: _emojiFormatConversion.codepointToShort.get(codepoint)
        },
        _emojiFormatConversion.codepointToUnicode.get(codepoint)
    );
};

var getRenderer = function getRenderer(_ref2) {
    var style = _ref2.style,
        onClick = _ref2.onClick;
    return function (codepoint, key) {
        return _react2.default.createElement(Emoji, {
            codepoint: codepoint,
            style: style,
            onClick: onClick,
            key: key
        });
    };
};

exports.default = getRenderer;

/***/ }),

/***/ 250:
/*!*******************************************************************!*\
  !*** ./node_modules/react-emojione/lib/styles/emojione-sprite.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.sprite = undefined;

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _emojioneSpritePositions = __webpack_require__(/*! ./emojione-sprite-positions */ 251);

var _emojioneSpritePositions2 = _interopRequireDefault(_emojioneSpritePositions);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var SPRITE_SIZE = 2794;
var EMOJI_SIZE = 64;

var base = {
    textIndent: '-9999em',
    imageRendering: 'optimizeQuality',
    fontSize: 'inherit',
    height: 32,
    width: 32,
    top: -3,
    position: 'relative',
    display: 'inline-block',
    margin: '0 .15em',
    lineHeight: 'normal',
    verticalAlign: 'middle',
    backgroundImage: 'url("https://cdnjs.cloudflare.com/ajax/libs/emojione/2.2.7/assets/sprites/emojione.sprites.png")',
    backgroundRepeat: 'no-repeat'
};

var sprite = exports.sprite = function sprite(codepoint) {
    var style = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    var result = Object.assign({}, base, style);

    // ensure square size
    var size = parseInt(result.height);
    result.height = size;
    result.width = size;

    var scale = size / EMOJI_SIZE;

    var _positions$codepoint = _slicedToArray(_emojioneSpritePositions2.default[codepoint], 2),
        left = _positions$codepoint[0],
        top = _positions$codepoint[1];

    result.backgroundPosition = left * scale + 'px ' + top * scale + 'px';

    var bgSize = SPRITE_SIZE * scale;
    result.backgroundSize = bgSize + 'px ' + bgSize + 'px';

    return result;
};

/***/ }),

/***/ 251:
/*!*****************************************************************************!*\
  !*** ./node_modules/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \*****************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
/*eslint-disable*/
// Do not edit!
// This file was auto-generated by create-styles.js
exports.default = {
  '0023-20e3': [-65, 0],
  '0023': [-1365, -1820],
  '002a-20e3': [0, -65],
  '002a': [-65, -65],
  '0030-20e3': [-130, 0],
  '0030': [-130, -65],
  '0031-20e3': [0, -130],
  '0031': [-65, -130],
  '0032-20e3': [-130, -130],
  '0032': [-195, 0],
  '0033-20e3': [-195, -65],
  '0033': [-195, -130],
  '0034-20e3': [0, -195],
  '0034': [-65, -195],
  '0035-20e3': [-130, -195],
  '0035': [-195, -195],
  '0036-20e3': [-260, 0],
  '0036': [-260, -65],
  '0037-20e3': [-260, -130],
  '0037': [-260, -195],
  '0038-20e3': [0, -260],
  '0038': [-65, -260],
  '0039-20e3': [-130, -260],
  '0039': [-195, -260],
  '00a9': [-260, -260],
  '00ae': [-325, 0],
  '1f004': [-325, -65],
  '1f0cf': [-325, -130],
  '1f170': [-325, -195],
  '1f171': [-325, -260],
  '1f17e': [0, -325],
  '1f17f': [-65, -325],
  '1f18e': [-130, -325],
  '1f191': [-195, -325],
  '1f192': [-260, -325],
  '1f193': [-325, -325],
  '1f194': [-390, 0],
  '1f195': [-390, -65],
  '1f196': [-390, -130],
  '1f197': [-390, -195],
  '1f198': [-390, -260],
  '1f199': [-390, -325],
  '1f19a': [0, -390],
  '1f1e6-1f1e8': [-65, -390],
  '1f1e6-1f1e9': [-130, -390],
  '1f1e6-1f1ea': [-195, -390],
  '1f1e6-1f1eb': [-260, -390],
  '1f1e6-1f1ec': [-325, -390],
  '1f1e6-1f1ee': [-390, -390],
  '1f1e6-1f1f1': [-455, 0],
  '1f1e6-1f1f2': [-455, -65],
  '1f1e6-1f1f4': [-455, -130],
  '1f1e6-1f1f6': [-455, -195],
  '1f1e6-1f1f7': [-455, -260],
  '1f1e6-1f1f8': [-455, -325],
  '1f1e6-1f1f9': [-455, -390],
  '1f1e6-1f1fa': [0, -455],
  '1f1e6-1f1fc': [-65, -455],
  '1f1e6-1f1fd': [-130, -455],
  '1f1e6-1f1ff': [-195, -455],
  '1f1e6': [-260, -455],
  '1f1e7-1f1e6': [-325, -455],
  '1f1e7-1f1e7': [-390, -455],
  '1f1e7-1f1e9': [-455, -455],
  '1f1e7-1f1ea': [-520, 0],
  '1f1e7-1f1eb': [-520, -65],
  '1f1e7-1f1ec': [-520, -130],
  '1f1e7-1f1ed': [-520, -195],
  '1f1e7-1f1ee': [-520, -260],
  '1f1e7-1f1ef': [-520, -325],
  '1f1e7-1f1f1': [-520, -390],
  '1f1e7-1f1f2': [-520, -455],
  '1f1e7-1f1f3': [0, -520],
  '1f1e7-1f1f4': [-65, -520],
  '1f1e7-1f1f6': [-130, -520],
  '1f1e7-1f1f7': [-195, -520],
  '1f1e7-1f1f8': [-260, -520],
  '1f1e7-1f1f9': [-325, -520],
  '1f1e7-1f1fb': [-390, -520],
  '1f1e7-1f1fc': [-455, -520],
  '1f1e7-1f1fe': [-520, -520],
  '1f1e7-1f1ff': [-585, 0],
  '1f1e7': [-585, -65],
  '1f1e8-1f1e6': [-585, -130],
  '1f1e8-1f1e8': [-585, -195],
  '1f1e8-1f1e9': [-585, -260],
  '1f1e8-1f1eb': [-585, -325],
  '1f1e8-1f1ec': [-585, -390],
  '1f1e8-1f1ed': [-585, -455],
  '1f1e8-1f1ee': [-585, -520],
  '1f1e8-1f1f0': [0, -585],
  '1f1e8-1f1f1': [-65, -585],
  '1f1e8-1f1f2': [-130, -585],
  '1f1e8-1f1f3': [-195, -585],
  '1f1e8-1f1f4': [-260, -585],
  '1f1e8-1f1f5': [-325, -585],
  '1f1e8-1f1f7': [-390, -585],
  '1f1e8-1f1fa': [-455, -585],
  '1f1e8-1f1fb': [-520, -585],
  '1f1e8-1f1fc': [-585, -585],
  '1f1e8-1f1fd': [-650, 0],
  '1f1e8-1f1fe': [-650, -65],
  '1f1e8-1f1ff': [-650, -130],
  '1f1e8': [-650, -195],
  '1f1e9-1f1ea': [-650, -260],
  '1f1e9-1f1ec': [-650, -325],
  '1f1e9-1f1ef': [-650, -390],
  '1f1e9-1f1f0': [-650, -455],
  '1f1e9-1f1f2': [-650, -520],
  '1f1e9-1f1f4': [-650, -585],
  '1f1e9-1f1ff': [0, -650],
  '1f1e9': [-65, -650],
  '1f1ea-1f1e6': [-130, -650],
  '1f1ea-1f1e8': [-195, -650],
  '1f1ea-1f1ea': [-260, -650],
  '1f1ea-1f1ec': [-325, -650],
  '1f1ea-1f1ed': [-390, -650],
  '1f1ea-1f1f7': [-455, -650],
  '1f1ea-1f1f8': [-520, -650],
  '1f1ea-1f1f9': [-585, -650],
  '1f1ea-1f1fa': [-650, -650],
  '1f1ea': [-715, 0],
  '1f1eb-1f1ee': [-715, -65],
  '1f1eb-1f1ef': [-715, -130],
  '1f1eb-1f1f0': [-715, -195],
  '1f1eb-1f1f2': [-715, -260],
  '1f1eb-1f1f4': [-715, -325],
  '1f1eb-1f1f7': [-715, -390],
  '1f1eb': [-715, -455],
  '1f1ec-1f1e6': [-715, -520],
  '1f1ec-1f1e7': [-715, -585],
  '1f1ec-1f1e9': [-715, -650],
  '1f1ec-1f1ea': [0, -715],
  '1f1ec-1f1eb': [-65, -715],
  '1f1ec-1f1ec': [-130, -715],
  '1f1ec-1f1ed': [-195, -715],
  '1f1ec-1f1ee': [-260, -715],
  '1f1ec-1f1f1': [-325, -715],
  '1f1ec-1f1f2': [-390, -715],
  '1f1ec-1f1f3': [-455, -715],
  '1f1ec-1f1f5': [-520, -715],
  '1f1ec-1f1f6': [-585, -715],
  '1f1ec-1f1f7': [-650, -715],
  '1f1ec-1f1f8': [-715, -715],
  '1f1ec-1f1f9': [-780, 0],
  '1f1ec-1f1fa': [-780, -65],
  '1f1ec-1f1fc': [-780, -130],
  '1f1ec-1f1fe': [-780, -195],
  '1f1ec': [-780, -260],
  '1f1ed-1f1f0': [-780, -325],
  '1f1ed-1f1f2': [-780, -390],
  '1f1ed-1f1f3': [-780, -455],
  '1f1ed-1f1f7': [-780, -520],
  '1f1ed-1f1f9': [-780, -585],
  '1f1ed-1f1fa': [-780, -650],
  '1f1ed': [-780, -715],
  '1f1ee-1f1e8': [0, -780],
  '1f1ee-1f1e9': [-65, -780],
  '1f1ee-1f1ea': [-130, -780],
  '1f1ee-1f1f1': [-195, -780],
  '1f1ee-1f1f2': [-260, -780],
  '1f1ee-1f1f3': [-325, -780],
  '1f1ee-1f1f4': [-390, -780],
  '1f1ee-1f1f6': [-455, -780],
  '1f1ee-1f1f7': [-520, -780],
  '1f1ee-1f1f8': [-585, -780],
  '1f1ee-1f1f9': [-650, -780],
  '1f1ee': [-715, -780],
  '1f1ef-1f1ea': [-780, -780],
  '1f1ef-1f1f2': [-845, 0],
  '1f1ef-1f1f4': [-845, -65],
  '1f1ef-1f1f5': [-845, -130],
  '1f1ef': [-845, -195],
  '1f1f0-1f1ea': [-845, -260],
  '1f1f0-1f1ec': [-845, -325],
  '1f1f0-1f1ed': [-845, -390],
  '1f1f0-1f1ee': [-845, -455],
  '1f1f0-1f1f2': [-845, -520],
  '1f1f0-1f1f3': [-845, -585],
  '1f1f0-1f1f5': [-845, -650],
  '1f1f0-1f1f7': [-845, -715],
  '1f1f0-1f1fc': [-845, -780],
  '1f1f0-1f1fe': [0, -845],
  '1f1f0-1f1ff': [-65, -845],
  '1f1f0': [-130, -845],
  '1f1f1-1f1e6': [-195, -845],
  '1f1f1-1f1e7': [-260, -845],
  '1f1f1-1f1e8': [-325, -845],
  '1f1f1-1f1ee': [-390, -845],
  '1f1f1-1f1f0': [-455, -845],
  '1f1f1-1f1f7': [-520, -845],
  '1f1f1-1f1f8': [-585, -845],
  '1f1f1-1f1f9': [-650, -845],
  '1f1f1-1f1fa': [-715, -845],
  '1f1f1-1f1fb': [-780, -845],
  '1f1f1-1f1fe': [-845, -845],
  '1f1f1': [-910, 0],
  '1f1f2-1f1e6': [-910, -65],
  '1f1f2-1f1e8': [-910, -130],
  '1f1f2-1f1e9': [-910, -195],
  '1f1f2-1f1ea': [-910, -260],
  '1f1f2-1f1eb': [-910, -325],
  '1f1f2-1f1ec': [-910, -390],
  '1f1f2-1f1ed': [-910, -455],
  '1f1f2-1f1f0': [-910, -520],
  '1f1f2-1f1f1': [-910, -585],
  '1f1f2-1f1f2': [-910, -650],
  '1f1f2-1f1f3': [-910, -715],
  '1f1f2-1f1f4': [-910, -780],
  '1f1f2-1f1f5': [-910, -845],
  '1f1f2-1f1f6': [0, -910],
  '1f1f2-1f1f7': [-65, -910],
  '1f1f2-1f1f8': [-130, -910],
  '1f1f2-1f1f9': [-195, -910],
  '1f1f2-1f1fa': [-260, -910],
  '1f1f2-1f1fb': [-325, -910],
  '1f1f2-1f1fc': [-390, -910],
  '1f1f2-1f1fd': [-455, -910],
  '1f1f2-1f1fe': [-520, -910],
  '1f1f2-1f1ff': [-585, -910],
  '1f1f2': [-650, -910],
  '1f1f3-1f1e6': [-715, -910],
  '1f1f3-1f1e8': [-780, -910],
  '1f1f3-1f1ea': [-845, -910],
  '1f1f3-1f1eb': [-910, -910],
  '1f1f3-1f1ec': [-975, 0],
  '1f1f3-1f1ee': [-975, -65],
  '1f1f3-1f1f1': [-975, -130],
  '1f1f3-1f1f4': [-975, -195],
  '1f1f3-1f1f5': [-975, -260],
  '1f1f3-1f1f7': [-975, -325],
  '1f1f3-1f1fa': [-975, -390],
  '1f1f3-1f1ff': [-975, -455],
  '1f1f3': [-975, -520],
  '1f1f4-1f1f2': [-975, -585],
  '1f1f4': [-975, -650],
  '1f1f5-1f1e6': [-975, -715],
  '1f1f5-1f1ea': [-975, -780],
  '1f1f5-1f1eb': [-975, -845],
  '1f1f5-1f1ec': [-975, -910],
  '1f1f5-1f1ed': [0, -975],
  '1f1f5-1f1f0': [-65, -975],
  '1f1f5-1f1f1': [-130, -975],
  '1f1f5-1f1f2': [-195, -975],
  '1f1f5-1f1f3': [-260, -975],
  '1f1f5-1f1f7': [-325, -975],
  '1f1f5-1f1f8': [-390, -975],
  '1f1f5-1f1f9': [-455, -975],
  '1f1f5-1f1fc': [-520, -975],
  '1f1f5-1f1fe': [-585, -975],
  '1f1f5': [-650, -975],
  '1f1f6-1f1e6': [-715, -975],
  '1f1f6': [-780, -975],
  '1f1f7-1f1ea': [-845, -975],
  '1f1f7-1f1f4': [-910, -975],
  '1f1f7-1f1f8': [-975, -975],
  '1f1f7-1f1fa': [-1040, 0],
  '1f1f7-1f1fc': [-1040, -65],
  '1f1f7': [-1040, -130],
  '1f1f8-1f1e6': [-1040, -195],
  '1f1f8-1f1e7': [-1040, -260],
  '1f1f8-1f1e8': [-1040, -325],
  '1f1f8-1f1e9': [-1040, -390],
  '1f1f8-1f1ea': [-1040, -455],
  '1f1f8-1f1ec': [-1040, -520],
  '1f1f8-1f1ed': [-1040, -585],
  '1f1f8-1f1ee': [-1040, -650],
  '1f1f8-1f1ef': [-1040, -715],
  '1f1f8-1f1f0': [-1040, -780],
  '1f1f8-1f1f1': [-1040, -845],
  '1f1f8-1f1f2': [-1040, -910],
  '1f1f8-1f1f3': [-1040, -975],
  '1f1f8-1f1f4': [0, -1040],
  '1f1f8-1f1f7': [-65, -1040],
  '1f1f8-1f1f8': [-130, -1040],
  '1f1f8-1f1f9': [-195, -1040],
  '1f1f8-1f1fb': [-260, -1040],
  '1f1f8-1f1fd': [-325, -1040],
  '1f1f8-1f1fe': [-390, -1040],
  '1f1f8-1f1ff': [-455, -1040],
  '1f1f8': [-520, -1040],
  '1f1f9-1f1e6': [-585, -1040],
  '1f1f9-1f1e8': [-650, -1040],
  '1f1f9-1f1e9': [-715, -1040],
  '1f1f9-1f1eb': [-780, -1040],
  '1f1f9-1f1ec': [-845, -1040],
  '1f1f9-1f1ed': [-910, -1040],
  '1f1f9-1f1ef': [-975, -1040],
  '1f1f9-1f1f0': [-1040, -1040],
  '1f1f9-1f1f1': [-1105, 0],
  '1f1f9-1f1f2': [-1105, -65],
  '1f1f9-1f1f3': [-1105, -130],
  '1f1f9-1f1f4': [-1105, -195],
  '1f1f9-1f1f7': [-1105, -260],
  '1f1f9-1f1f9': [-1105, -325],
  '1f1f9-1f1fb': [-1105, -390],
  '1f1f9-1f1fc': [-1105, -455],
  '1f1f9-1f1ff': [-1105, -520],
  '1f1f9': [-1105, -585],
  '1f1fa-1f1e6': [-1105, -650],
  '1f1fa-1f1ec': [-1105, -715],
  '1f1fa-1f1f2': [-1105, -780],
  '1f1fa-1f1f8': [-1105, -845],
  '1f1fa-1f1fe': [-1105, -910],
  '1f1fa-1f1ff': [-1105, -975],
  '1f1fa': [-1105, -1040],
  '1f1fb-1f1e6': [0, -1105],
  '1f1fb-1f1e8': [-65, -1105],
  '1f1fb-1f1ea': [-130, -1105],
  '1f1fb-1f1ec': [-195, -1105],
  '1f1fb-1f1ee': [-260, -1105],
  '1f1fb-1f1f3': [-325, -1105],
  '1f1fb-1f1fa': [-390, -1105],
  '1f1fb': [-455, -1105],
  '1f1fc-1f1eb': [-520, -1105],
  '1f1fc-1f1f8': [-585, -1105],
  '1f1fc': [-650, -1105],
  '1f1fd-1f1f0': [-715, -1105],
  '1f1fd': [-780, -1105],
  '1f1fe-1f1ea': [-845, -1105],
  '1f1fe-1f1f9': [-910, -1105],
  '1f1fe': [-975, -1105],
  '1f1ff-1f1e6': [-1040, -1105],
  '1f1ff-1f1f2': [-1105, -1105],
  '1f1ff-1f1fc': [-1170, 0],
  '1f1ff': [-1170, -65],
  '1f201': [-1170, -130],
  '1f202': [-1170, -195],
  '1f21a': [-1170, -260],
  '1f22f': [-1170, -325],
  '1f232': [-1170, -390],
  '1f233': [-1170, -455],
  '1f234': [-1170, -520],
  '1f235': [-1170, -585],
  '1f236': [-1170, -650],
  '1f237': [-1170, -715],
  '1f238': [-1170, -780],
  '1f239': [-1170, -845],
  '1f23a': [-1170, -910],
  '1f250': [-1170, -975],
  '1f251': [-1170, -1040],
  '1f300': [-1170, -1105],
  '1f301': [0, -1170],
  '1f302': [-65, -1170],
  '1f303': [-130, -1170],
  '1f304': [-195, -1170],
  '1f305': [-260, -1170],
  '1f306': [-325, -1170],
  '1f307': [-390, -1170],
  '1f308': [-455, -1170],
  '1f309': [-520, -1170],
  '1f30a': [-585, -1170],
  '1f30b': [-650, -1170],
  '1f30c': [-715, -1170],
  '1f30d': [-780, -1170],
  '1f30e': [-845, -1170],
  '1f30f': [-910, -1170],
  '1f310': [-975, -1170],
  '1f311': [-1040, -1170],
  '1f312': [-1105, -1170],
  '1f313': [-1170, -1170],
  '1f314': [-1235, 0],
  '1f315': [-1235, -65],
  '1f316': [-1235, -130],
  '1f317': [-1235, -195],
  '1f318': [-1235, -260],
  '1f319': [-1235, -325],
  '1f31a': [-1235, -390],
  '1f31b': [-1235, -455],
  '1f31c': [-1235, -520],
  '1f31d': [-1235, -585],
  '1f31e': [-1235, -650],
  '1f31f': [-1235, -715],
  '1f320': [-1235, -780],
  '1f321': [-1235, -845],
  '1f324': [-1235, -910],
  '1f325': [-1235, -975],
  '1f326': [-1235, -1040],
  '1f327': [-1235, -1105],
  '1f328': [-1235, -1170],
  '1f329': [0, -1235],
  '1f32a': [-65, -1235],
  '1f32b': [-130, -1235],
  '1f32c': [-195, -1235],
  '1f32d': [-260, -1235],
  '1f32e': [-325, -1235],
  '1f32f': [-390, -1235],
  '1f330': [-455, -1235],
  '1f331': [-520, -1235],
  '1f332': [-585, -1235],
  '1f333': [-650, -1235],
  '1f334': [-715, -1235],
  '1f335': [-780, -1235],
  '1f336': [-845, -1235],
  '1f337': [-910, -1235],
  '1f338': [-975, -1235],
  '1f339': [-1040, -1235],
  '1f33a': [-1105, -1235],
  '1f33b': [-1170, -1235],
  '1f33c': [-1235, -1235],
  '1f33d': [-1300, 0],
  '1f33e': [-1300, -65],
  '1f33f': [-1300, -130],
  '1f340': [-1300, -195],
  '1f341': [-1300, -260],
  '1f342': [-1300, -325],
  '1f343': [-1300, -390],
  '1f344': [-1300, -455],
  '1f345': [-1300, -520],
  '1f346': [-1300, -585],
  '1f347': [-1300, -650],
  '1f348': [-1300, -715],
  '1f349': [-1300, -780],
  '1f34a': [-1300, -845],
  '1f34b': [-1300, -910],
  '1f34c': [-1300, -975],
  '1f34d': [-1300, -1040],
  '1f34e': [-1300, -1105],
  '1f34f': [-1300, -1170],
  '1f350': [-1300, -1235],
  '1f351': [0, -1300],
  '1f352': [-65, -1300],
  '1f353': [-130, -1300],
  '1f354': [-195, -1300],
  '1f355': [-260, -1300],
  '1f356': [-325, -1300],
  '1f357': [-390, -1300],
  '1f358': [-455, -1300],
  '1f359': [-520, -1300],
  '1f35a': [-585, -1300],
  '1f35b': [-650, -1300],
  '1f35c': [-715, -1300],
  '1f35d': [-780, -1300],
  '1f35e': [-845, -1300],
  '1f35f': [-910, -1300],
  '1f360': [-975, -1300],
  '1f361': [-1040, -1300],
  '1f362': [-1105, -1300],
  '1f363': [-1170, -1300],
  '1f364': [-1235, -1300],
  '1f365': [-1300, -1300],
  '1f366': [-1365, 0],
  '1f367': [-1365, -65],
  '1f368': [-1365, -130],
  '1f369': [-1365, -195],
  '1f36a': [-1365, -260],
  '1f36b': [-1365, -325],
  '1f36c': [-1365, -390],
  '1f36d': [-1365, -455],
  '1f36e': [-1365, -520],
  '1f36f': [-1365, -585],
  '1f370': [-1365, -650],
  '1f371': [-1365, -715],
  '1f372': [-1365, -780],
  '1f373': [-1365, -845],
  '1f374': [-1365, -910],
  '1f375': [-1365, -975],
  '1f376': [-1365, -1040],
  '1f377': [-1365, -1105],
  '1f378': [-1365, -1170],
  '1f379': [-1365, -1235],
  '1f37a': [-1365, -1300],
  '1f37b': [0, -1365],
  '1f37c': [-65, -1365],
  '1f37d': [-130, -1365],
  '1f37e': [-195, -1365],
  '1f37f': [-260, -1365],
  '1f380': [-325, -1365],
  '1f381': [-390, -1365],
  '1f382': [-455, -1365],
  '1f383': [-520, -1365],
  '1f384': [-585, -1365],
  '1f385-1f3fb': [-650, -1365],
  '1f385-1f3fc': [-715, -1365],
  '1f385-1f3fd': [-780, -1365],
  '1f385-1f3fe': [-845, -1365],
  '1f385-1f3ff': [-910, -1365],
  '1f385': [-975, -1365],
  '1f386': [-1040, -1365],
  '1f387': [-1105, -1365],
  '1f388': [-1170, -1365],
  '1f389': [-1235, -1365],
  '1f38a': [-1300, -1365],
  '1f38b': [-1365, -1365],
  '1f38c': [-1430, 0],
  '1f38d': [-1430, -65],
  '1f38e': [-1430, -130],
  '1f38f': [-1430, -195],
  '1f390': [-1430, -260],
  '1f391': [-1430, -325],
  '1f392': [-1430, -390],
  '1f393': [-1430, -455],
  '1f396': [-1430, -520],
  '1f397': [-1430, -585],
  '1f399': [-1430, -650],
  '1f39a': [-1430, -715],
  '1f39b': [-1430, -780],
  '1f39e': [-1430, -845],
  '1f39f': [-1430, -910],
  '1f3a0': [-1430, -975],
  '1f3a1': [-1430, -1040],
  '1f3a2': [-1430, -1105],
  '1f3a3': [-1430, -1170],
  '1f3a4': [-1430, -1235],
  '1f3a5': [-1430, -1300],
  '1f3a6': [-1430, -1365],
  '1f3a7': [0, -1430],
  '1f3a8': [-65, -1430],
  '1f3a9': [-130, -1430],
  '1f3aa': [-195, -1430],
  '1f3ab': [-260, -1430],
  '1f3ac': [-325, -1430],
  '1f3ad': [-390, -1430],
  '1f3ae': [-455, -1430],
  '1f3af': [-520, -1430],
  '1f3b0': [-585, -1430],
  '1f3b1': [-650, -1430],
  '1f3b2': [-715, -1430],
  '1f3b3': [-780, -1430],
  '1f3b4': [-845, -1430],
  '1f3b5': [-910, -1430],
  '1f3b6': [-975, -1430],
  '1f3b7': [-1040, -1430],
  '1f3b8': [-1105, -1430],
  '1f3b9': [-1170, -1430],
  '1f3ba': [-1235, -1430],
  '1f3bb': [-1300, -1430],
  '1f3bc': [-1365, -1430],
  '1f3bd': [-1430, -1430],
  '1f3be': [-1495, 0],
  '1f3bf': [-1495, -65],
  '1f3c0': [-1495, -130],
  '1f3c1': [-1495, -195],
  '1f3c2': [-1495, -260],
  '1f3c3-1f3fb': [-1495, -325],
  '1f3c3-1f3fc': [-1495, -390],
  '1f3c3-1f3fd': [-1495, -455],
  '1f3c3-1f3fe': [-1495, -520],
  '1f3c3-1f3ff': [-1495, -585],
  '1f3c3': [-1495, -650],
  '1f3c4-1f3fb': [-1495, -715],
  '1f3c4-1f3fc': [-1495, -780],
  '1f3c4-1f3fd': [-1495, -845],
  '1f3c4-1f3fe': [-1495, -910],
  '1f3c4-1f3ff': [-1495, -975],
  '1f3c4': [-1495, -1040],
  '1f3c5': [-1495, -1105],
  '1f3c6': [-1495, -1170],
  '1f3c7-1f3fb': [-1495, -1235],
  '1f3c7-1f3fc': [-1495, -1300],
  '1f3c7-1f3fd': [-1495, -1365],
  '1f3c7-1f3fe': [-1495, -1430],
  '1f3c7-1f3ff': [0, -1495],
  '1f3c7': [-65, -1495],
  '1f3c8': [-130, -1495],
  '1f3c9': [-195, -1495],
  '1f3ca-1f3fb': [-260, -1495],
  '1f3ca-1f3fc': [-325, -1495],
  '1f3ca-1f3fd': [-390, -1495],
  '1f3ca-1f3fe': [-455, -1495],
  '1f3ca-1f3ff': [-520, -1495],
  '1f3ca': [-585, -1495],
  '1f3cb-1f3fb': [-650, -1495],
  '1f3cb-1f3fc': [-715, -1495],
  '1f3cb-1f3fd': [-780, -1495],
  '1f3cb-1f3fe': [-845, -1495],
  '1f3cb-1f3ff': [-910, -1495],
  '1f3cb': [-975, -1495],
  '1f3cc': [-1040, -1495],
  '1f3cd': [-1105, -1495],
  '1f3ce': [-1170, -1495],
  '1f3cf': [-1235, -1495],
  '1f3d0': [-1300, -1495],
  '1f3d1': [-1365, -1495],
  '1f3d2': [-1430, -1495],
  '1f3d3': [-1495, -1495],
  '1f3d4': [-1560, 0],
  '1f3d5': [-1560, -65],
  '1f3d6': [-1560, -130],
  '1f3d7': [-1560, -195],
  '1f3d8': [-1560, -260],
  '1f3d9': [-1560, -325],
  '1f3da': [-1560, -390],
  '1f3db': [-1560, -455],
  '1f3dc': [-1560, -520],
  '1f3dd': [-1560, -585],
  '1f3de': [-1560, -650],
  '1f3df': [-1560, -715],
  '1f3e0': [-1560, -780],
  '1f3e1': [-1560, -845],
  '1f3e2': [-1560, -910],
  '1f3e3': [-1560, -975],
  '1f3e4': [-1560, -1040],
  '1f3e5': [-1560, -1105],
  '1f3e6': [-1560, -1170],
  '1f3e7': [-1560, -1235],
  '1f3e8': [-1560, -1300],
  '1f3e9': [-1560, -1365],
  '1f3ea': [-1560, -1430],
  '1f3eb': [-1560, -1495],
  '1f3ec': [0, -1560],
  '1f3ed': [-65, -1560],
  '1f3ee': [-130, -1560],
  '1f3ef': [-195, -1560],
  '1f3f0': [-260, -1560],
  '1f3f3-1f308': [-325, -1560],
  '1f3f3': [-390, -1560],
  '1f3f4': [-455, -1560],
  '1f3f5': [-520, -1560],
  '1f3f7': [-585, -1560],
  '1f3f8': [-650, -1560],
  '1f3f9': [-715, -1560],
  '1f3fa': [-780, -1560],
  '1f3fb': [-845, -1560],
  '1f3fc': [-910, -1560],
  '1f3fd': [-975, -1560],
  '1f3fe': [-1040, -1560],
  '1f3ff': [-1105, -1560],
  '1f400': [-1170, -1560],
  '1f401': [-1235, -1560],
  '1f402': [-1300, -1560],
  '1f403': [-1365, -1560],
  '1f404': [-1430, -1560],
  '1f405': [-1495, -1560],
  '1f406': [-1560, -1560],
  '1f407': [-1625, 0],
  '1f408': [-1625, -65],
  '1f409': [-1625, -130],
  '1f40a': [-1625, -195],
  '1f40b': [-1625, -260],
  '1f40c': [-1625, -325],
  '1f40d': [-1625, -390],
  '1f40e': [-1625, -455],
  '1f40f': [-1625, -520],
  '1f410': [-1625, -585],
  '1f411': [-1625, -650],
  '1f412': [-1625, -715],
  '1f413': [-1625, -780],
  '1f414': [-1625, -845],
  '1f415': [-1625, -910],
  '1f416': [-1625, -975],
  '1f417': [-1625, -1040],
  '1f418': [-1625, -1105],
  '1f419': [-1625, -1170],
  '1f41a': [-1625, -1235],
  '1f41b': [-1625, -1300],
  '1f41c': [-1625, -1365],
  '1f41d': [-1625, -1430],
  '1f41e': [-1625, -1495],
  '1f41f': [-1625, -1560],
  '1f420': [0, -1625],
  '1f421': [-65, -1625],
  '1f422': [-130, -1625],
  '1f423': [-195, -1625],
  '1f424': [-260, -1625],
  '1f425': [-325, -1625],
  '1f426': [-390, -1625],
  '1f427': [-455, -1625],
  '1f428': [-520, -1625],
  '1f429': [-585, -1625],
  '1f42a': [-650, -1625],
  '1f42b': [-715, -1625],
  '1f42c': [-780, -1625],
  '1f42d': [-845, -1625],
  '1f42e': [-910, -1625],
  '1f42f': [-975, -1625],
  '1f430': [-1040, -1625],
  '1f431': [-1105, -1625],
  '1f432': [-1170, -1625],
  '1f433': [-1235, -1625],
  '1f434': [-1300, -1625],
  '1f435': [-1365, -1625],
  '1f436': [-1430, -1625],
  '1f437': [-1495, -1625],
  '1f438': [-1560, -1625],
  '1f439': [-1625, -1625],
  '1f43a': [-1690, 0],
  '1f43b': [-1690, -65],
  '1f43c': [-1690, -130],
  '1f43d': [-1690, -195],
  '1f43e': [-1690, -260],
  '1f43f': [-1690, -325],
  '1f440': [-1690, -390],
  '1f441-1f5e8': [-1690, -455],
  '1f441': [-1690, -520],
  '1f442-1f3fb': [-1690, -585],
  '1f442-1f3fc': [-1690, -650],
  '1f442-1f3fd': [-1690, -715],
  '1f442-1f3fe': [-1690, -780],
  '1f442-1f3ff': [-1690, -845],
  '1f442': [-1690, -910],
  '1f443-1f3fb': [-1690, -975],
  '1f443-1f3fc': [-1690, -1040],
  '1f443-1f3fd': [-1690, -1105],
  '1f443-1f3fe': [-1690, -1170],
  '1f443-1f3ff': [-1690, -1235],
  '1f443': [-1690, -1300],
  '1f444': [-1690, -1365],
  '1f445': [-1690, -1430],
  '1f446-1f3fb': [-1690, -1495],
  '1f446-1f3fc': [-1690, -1560],
  '1f446-1f3fd': [-1690, -1625],
  '1f446-1f3fe': [0, -1690],
  '1f446-1f3ff': [-65, -1690],
  '1f446': [-130, -1690],
  '1f447-1f3fb': [-195, -1690],
  '1f447-1f3fc': [-260, -1690],
  '1f447-1f3fd': [-325, -1690],
  '1f447-1f3fe': [-390, -1690],
  '1f447-1f3ff': [-455, -1690],
  '1f447': [-520, -1690],
  '1f448-1f3fb': [-585, -1690],
  '1f448-1f3fc': [-650, -1690],
  '1f448-1f3fd': [-715, -1690],
  '1f448-1f3fe': [-780, -1690],
  '1f448-1f3ff': [-845, -1690],
  '1f448': [-910, -1690],
  '1f449-1f3fb': [-975, -1690],
  '1f449-1f3fc': [-1040, -1690],
  '1f449-1f3fd': [-1105, -1690],
  '1f449-1f3fe': [-1170, -1690],
  '1f449-1f3ff': [-1235, -1690],
  '1f449': [-1300, -1690],
  '1f44a-1f3fb': [-1365, -1690],
  '1f44a-1f3fc': [-1430, -1690],
  '1f44a-1f3fd': [-1495, -1690],
  '1f44a-1f3fe': [-1560, -1690],
  '1f44a-1f3ff': [-1625, -1690],
  '1f44a': [-1690, -1690],
  '1f44b-1f3fb': [-1755, 0],
  '1f44b-1f3fc': [-1755, -65],
  '1f44b-1f3fd': [-1755, -130],
  '1f44b-1f3fe': [-1755, -195],
  '1f44b-1f3ff': [-1755, -260],
  '1f44b': [-1755, -325],
  '1f44c-1f3fb': [-1755, -390],
  '1f44c-1f3fc': [-1755, -455],
  '1f44c-1f3fd': [-1755, -520],
  '1f44c-1f3fe': [-1755, -585],
  '1f44c-1f3ff': [-1755, -650],
  '1f44c': [-1755, -715],
  '1f44d-1f3fb': [-1755, -780],
  '1f44d-1f3fc': [-1755, -845],
  '1f44d-1f3fd': [-1755, -910],
  '1f44d-1f3fe': [-1755, -975],
  '1f44d-1f3ff': [-1755, -1040],
  '1f44d': [-1755, -1105],
  '1f44e-1f3fb': [-1755, -1170],
  '1f44e-1f3fc': [-1755, -1235],
  '1f44e-1f3fd': [-1755, -1300],
  '1f44e-1f3fe': [-1755, -1365],
  '1f44e-1f3ff': [-1755, -1430],
  '1f44e': [-1755, -1495],
  '1f44f-1f3fb': [-1755, -1560],
  '1f44f-1f3fc': [-1755, -1625],
  '1f44f-1f3fd': [-1755, -1690],
  '1f44f-1f3fe': [0, -1755],
  '1f44f-1f3ff': [-65, -1755],
  '1f44f': [-130, -1755],
  '1f450-1f3fb': [-195, -1755],
  '1f450-1f3fc': [-260, -1755],
  '1f450-1f3fd': [-325, -1755],
  '1f450-1f3fe': [-390, -1755],
  '1f450-1f3ff': [-455, -1755],
  '1f450': [-520, -1755],
  '1f451': [-585, -1755],
  '1f452': [-650, -1755],
  '1f453': [-715, -1755],
  '1f454': [-780, -1755],
  '1f455': [-845, -1755],
  '1f456': [-910, -1755],
  '1f457': [-975, -1755],
  '1f458': [-1040, -1755],
  '1f459': [-1105, -1755],
  '1f45a': [-1170, -1755],
  '1f45b': [-1235, -1755],
  '1f45c': [-1300, -1755],
  '1f45d': [-1365, -1755],
  '1f45e': [-1430, -1755],
  '1f45f': [-1495, -1755],
  '1f460': [-1560, -1755],
  '1f461': [-1625, -1755],
  '1f462': [-1690, -1755],
  '1f463': [-1755, -1755],
  '1f464': [-1820, 0],
  '1f465': [-1820, -65],
  '1f466-1f3fb': [-1820, -130],
  '1f466-1f3fc': [-1820, -195],
  '1f466-1f3fd': [-1820, -260],
  '1f466-1f3fe': [-1820, -325],
  '1f466-1f3ff': [-1820, -390],
  '1f466': [-1820, -455],
  '1f467-1f3fb': [-1820, -520],
  '1f467-1f3fc': [-1820, -585],
  '1f467-1f3fd': [-1820, -650],
  '1f467-1f3fe': [-1820, -715],
  '1f467-1f3ff': [-1820, -780],
  '1f467': [-1820, -845],
  '1f468-1f3fb': [-1820, -910],
  '1f468-1f3fc': [-1820, -975],
  '1f468-1f3fd': [-1820, -1040],
  '1f468-1f3fe': [-1820, -1105],
  '1f468-1f3ff': [-1820, -1170],
  '1f468-1f468-1f466-1f466': [-1820, -1235],
  '1f468-1f468-1f466': [-1820, -1300],
  '1f468-1f468-1f467-1f466': [-1820, -1365],
  '1f468-1f468-1f467-1f467': [-1820, -1430],
  '1f468-1f468-1f467': [-1820, -1495],
  '1f468-1f469-1f466-1f466': [-1820, -1560],
  '1f468-1f469-1f467-1f466': [-1820, -1625],
  '1f468-1f469-1f467-1f467': [-1820, -1690],
  '1f468-1f469-1f467': [-1820, -1755],
  '1f468-2764-1f468': [0, -1820],
  '1f468-2764-1f48b-1f468': [-65, -1820],
  '1f468': [-130, -1820],
  '1f469-1f3fb': [-195, -1820],
  '1f469-1f3fc': [-260, -1820],
  '1f469-1f3fd': [-325, -1820],
  '1f469-1f3fe': [-390, -1820],
  '1f469-1f3ff': [-455, -1820],
  '1f469-1f469-1f466-1f466': [-520, -1820],
  '1f469-1f469-1f466': [-585, -1820],
  '1f469-1f469-1f467-1f466': [-650, -1820],
  '1f469-1f469-1f467-1f467': [-715, -1820],
  '1f469-1f469-1f467': [-780, -1820],
  '1f469-2764-1f469': [-845, -1820],
  '1f469-2764-1f48b-1f469': [-910, -1820],
  '1f469': [-975, -1820],
  '1f46a': [-1040, -1820],
  '1f46b': [-1105, -1820],
  '1f46c': [-1170, -1820],
  '1f46d': [-1235, -1820],
  '1f46e-1f3fb': [-1300, -1820],
  '1f46e-1f3fc': [0, 0],
  '1f46e-1f3fd': [-1430, -1820],
  '1f46e-1f3fe': [-1495, -1820],
  '1f46e-1f3ff': [-1560, -1820],
  '1f46e': [-1625, -1820],
  '1f46f': [-1690, -1820],
  '1f470-1f3fb': [-1755, -1820],
  '1f470-1f3fc': [-1820, -1820],
  '1f470-1f3fd': [-1885, 0],
  '1f470-1f3fe': [-1885, -65],
  '1f470-1f3ff': [-1885, -130],
  '1f470': [-1885, -195],
  '1f471-1f3fb': [-1885, -260],
  '1f471-1f3fc': [-1885, -325],
  '1f471-1f3fd': [-1885, -390],
  '1f471-1f3fe': [-1885, -455],
  '1f471-1f3ff': [-1885, -520],
  '1f471': [-1885, -585],
  '1f472-1f3fb': [-1885, -650],
  '1f472-1f3fc': [-1885, -715],
  '1f472-1f3fd': [-1885, -780],
  '1f472-1f3fe': [-1885, -845],
  '1f472-1f3ff': [-1885, -910],
  '1f472': [-1885, -975],
  '1f473-1f3fb': [-1885, -1040],
  '1f473-1f3fc': [-1885, -1105],
  '1f473-1f3fd': [-1885, -1170],
  '1f473-1f3fe': [-1885, -1235],
  '1f473-1f3ff': [-1885, -1300],
  '1f473': [-1885, -1365],
  '1f474-1f3fb': [-1885, -1430],
  '1f474-1f3fc': [-1885, -1495],
  '1f474-1f3fd': [-1885, -1560],
  '1f474-1f3fe': [-1885, -1625],
  '1f474-1f3ff': [-1885, -1690],
  '1f474': [-1885, -1755],
  '1f475-1f3fb': [-1885, -1820],
  '1f475-1f3fc': [0, -1885],
  '1f475-1f3fd': [-65, -1885],
  '1f475-1f3fe': [-130, -1885],
  '1f475-1f3ff': [-195, -1885],
  '1f475': [-260, -1885],
  '1f476-1f3fb': [-325, -1885],
  '1f476-1f3fc': [-390, -1885],
  '1f476-1f3fd': [-455, -1885],
  '1f476-1f3fe': [-520, -1885],
  '1f476-1f3ff': [-585, -1885],
  '1f476': [-650, -1885],
  '1f477-1f3fb': [-715, -1885],
  '1f477-1f3fc': [-780, -1885],
  '1f477-1f3fd': [-845, -1885],
  '1f477-1f3fe': [-910, -1885],
  '1f477-1f3ff': [-975, -1885],
  '1f477': [-1040, -1885],
  '1f478-1f3fb': [-1105, -1885],
  '1f478-1f3fc': [-1170, -1885],
  '1f478-1f3fd': [-1235, -1885],
  '1f478-1f3fe': [-1300, -1885],
  '1f478-1f3ff': [-1365, -1885],
  '1f478': [-1430, -1885],
  '1f479': [-1495, -1885],
  '1f47a': [-1560, -1885],
  '1f47b': [-1625, -1885],
  '1f47c-1f3fb': [-1690, -1885],
  '1f47c-1f3fc': [-1755, -1885],
  '1f47c-1f3fd': [-1820, -1885],
  '1f47c-1f3fe': [-1885, -1885],
  '1f47c-1f3ff': [-1950, 0],
  '1f47c': [-1950, -65],
  '1f47d': [-1950, -130],
  '1f47e': [-1950, -195],
  '1f47f': [-1950, -260],
  '1f480': [-1950, -325],
  '1f481-1f3fb': [-1950, -390],
  '1f481-1f3fc': [-1950, -455],
  '1f481-1f3fd': [-1950, -520],
  '1f481-1f3fe': [-1950, -585],
  '1f481-1f3ff': [-1950, -650],
  '1f481': [-1950, -715],
  '1f482-1f3fb': [-1950, -780],
  '1f482-1f3fc': [-1950, -845],
  '1f482-1f3fd': [-1950, -910],
  '1f482-1f3fe': [-1950, -975],
  '1f482-1f3ff': [-1950, -1040],
  '1f482': [-1950, -1105],
  '1f483-1f3fb': [-1950, -1170],
  '1f483-1f3fc': [-1950, -1235],
  '1f483-1f3fd': [-1950, -1300],
  '1f483-1f3fe': [-1950, -1365],
  '1f483-1f3ff': [-1950, -1430],
  '1f483': [-1950, -1495],
  '1f484': [-1950, -1560],
  '1f485-1f3fb': [-1950, -1625],
  '1f485-1f3fc': [-1950, -1690],
  '1f485-1f3fd': [-1950, -1755],
  '1f485-1f3fe': [-1950, -1820],
  '1f485-1f3ff': [-1950, -1885],
  '1f485': [0, -1950],
  '1f486-1f3fb': [-65, -1950],
  '1f486-1f3fc': [-130, -1950],
  '1f486-1f3fd': [-195, -1950],
  '1f486-1f3fe': [-260, -1950],
  '1f486-1f3ff': [-325, -1950],
  '1f486': [-390, -1950],
  '1f487-1f3fb': [-455, -1950],
  '1f487-1f3fc': [-520, -1950],
  '1f487-1f3fd': [-585, -1950],
  '1f487-1f3fe': [-650, -1950],
  '1f487-1f3ff': [-715, -1950],
  '1f487': [-780, -1950],
  '1f488': [-845, -1950],
  '1f489': [-910, -1950],
  '1f48a': [-975, -1950],
  '1f48b': [-1040, -1950],
  '1f48c': [-1105, -1950],
  '1f48d': [-1170, -1950],
  '1f48e': [-1235, -1950],
  '1f48f': [-1300, -1950],
  '1f490': [-1365, -1950],
  '1f491': [-1430, -1950],
  '1f492': [-1495, -1950],
  '1f493': [-1560, -1950],
  '1f494': [-1625, -1950],
  '1f495': [-1690, -1950],
  '1f496': [-1755, -1950],
  '1f497': [-1820, -1950],
  '1f498': [-1885, -1950],
  '1f499': [-1950, -1950],
  '1f49a': [-2015, 0],
  '1f49b': [-2015, -65],
  '1f49c': [-2015, -130],
  '1f49d': [-2015, -195],
  '1f49e': [-2015, -260],
  '1f49f': [-2015, -325],
  '1f4a0': [-2015, -390],
  '1f4a1': [-2015, -455],
  '1f4a2': [-2015, -520],
  '1f4a3': [-2015, -585],
  '1f4a4': [-2015, -650],
  '1f4a5': [-2015, -715],
  '1f4a6': [-2015, -780],
  '1f4a7': [-2015, -845],
  '1f4a8': [-2015, -910],
  '1f4a9': [-2015, -975],
  '1f4aa-1f3fb': [-2015, -1040],
  '1f4aa-1f3fc': [-2015, -1105],
  '1f4aa-1f3fd': [-2015, -1170],
  '1f4aa-1f3fe': [-2015, -1235],
  '1f4aa-1f3ff': [-2015, -1300],
  '1f4aa': [-2015, -1365],
  '1f4ab': [-2015, -1430],
  '1f4ac': [-2015, -1495],
  '1f4ad': [-2015, -1560],
  '1f4ae': [-2015, -1625],
  '1f4af': [-2015, -1690],
  '1f4b0': [-2015, -1755],
  '1f4b1': [-2015, -1820],
  '1f4b2': [-2015, -1885],
  '1f4b3': [-2015, -1950],
  '1f4b4': [0, -2015],
  '1f4b5': [-65, -2015],
  '1f4b6': [-130, -2015],
  '1f4b7': [-195, -2015],
  '1f4b8': [-260, -2015],
  '1f4b9': [-325, -2015],
  '1f4ba': [-390, -2015],
  '1f4bb': [-455, -2015],
  '1f4bc': [-520, -2015],
  '1f4bd': [-585, -2015],
  '1f4be': [-650, -2015],
  '1f4bf': [-715, -2015],
  '1f4c0': [-780, -2015],
  '1f4c1': [-845, -2015],
  '1f4c2': [-910, -2015],
  '1f4c3': [-975, -2015],
  '1f4c4': [-1040, -2015],
  '1f4c5': [-1105, -2015],
  '1f4c6': [-1170, -2015],
  '1f4c7': [-1235, -2015],
  '1f4c8': [-1300, -2015],
  '1f4c9': [-1365, -2015],
  '1f4ca': [-1430, -2015],
  '1f4cb': [-1495, -2015],
  '1f4cc': [-1560, -2015],
  '1f4cd': [-1625, -2015],
  '1f4ce': [-1690, -2015],
  '1f4cf': [-1755, -2015],
  '1f4d0': [-1820, -2015],
  '1f4d1': [-1885, -2015],
  '1f4d2': [-1950, -2015],
  '1f4d3': [-2015, -2015],
  '1f4d4': [-2080, 0],
  '1f4d5': [-2080, -65],
  '1f4d6': [-2080, -130],
  '1f4d7': [-2080, -195],
  '1f4d8': [-2080, -260],
  '1f4d9': [-2080, -325],
  '1f4da': [-2080, -390],
  '1f4db': [-2080, -455],
  '1f4dc': [-2080, -520],
  '1f4dd': [-2080, -585],
  '1f4de': [-2080, -650],
  '1f4df': [-2080, -715],
  '1f4e0': [-2080, -780],
  '1f4e1': [-2080, -845],
  '1f4e2': [-2080, -910],
  '1f4e3': [-2080, -975],
  '1f4e4': [-2080, -1040],
  '1f4e5': [-2080, -1105],
  '1f4e6': [-2080, -1170],
  '1f4e7': [-2080, -1235],
  '1f4e8': [-2080, -1300],
  '1f4e9': [-2080, -1365],
  '1f4ea': [-2080, -1430],
  '1f4eb': [-2080, -1495],
  '1f4ec': [-2080, -1560],
  '1f4ed': [-2080, -1625],
  '1f4ee': [-2080, -1690],
  '1f4ef': [-2080, -1755],
  '1f4f0': [-2080, -1820],
  '1f4f1': [-2080, -1885],
  '1f4f2': [-2080, -1950],
  '1f4f3': [-2080, -2015],
  '1f4f4': [0, -2080],
  '1f4f5': [-65, -2080],
  '1f4f6': [-130, -2080],
  '1f4f7': [-195, -2080],
  '1f4f8': [-260, -2080],
  '1f4f9': [-325, -2080],
  '1f4fa': [-390, -2080],
  '1f4fb': [-455, -2080],
  '1f4fc': [-520, -2080],
  '1f4fd': [-585, -2080],
  '1f4ff': [-650, -2080],
  '1f500': [-715, -2080],
  '1f501': [-780, -2080],
  '1f502': [-845, -2080],
  '1f503': [-910, -2080],
  '1f504': [-975, -2080],
  '1f505': [-1040, -2080],
  '1f506': [-1105, -2080],
  '1f507': [-1170, -2080],
  '1f508': [-1235, -2080],
  '1f509': [-1300, -2080],
  '1f50a': [-1365, -2080],
  '1f50b': [-1430, -2080],
  '1f50c': [-1495, -2080],
  '1f50d': [-1560, -2080],
  '1f50e': [-1625, -2080],
  '1f50f': [-1690, -2080],
  '1f510': [-1755, -2080],
  '1f511': [-1820, -2080],
  '1f512': [-1885, -2080],
  '1f513': [-1950, -2080],
  '1f514': [-2015, -2080],
  '1f515': [-2080, -2080],
  '1f516': [-2145, 0],
  '1f517': [-2145, -65],
  '1f518': [-2145, -130],
  '1f519': [-2145, -195],
  '1f51a': [-2145, -260],
  '1f51b': [-2145, -325],
  '1f51c': [-2145, -390],
  '1f51d': [-2145, -455],
  '1f51e': [-2145, -520],
  '1f51f': [-2145, -585],
  '1f520': [-2145, -650],
  '1f521': [-2145, -715],
  '1f522': [-2145, -780],
  '1f523': [-2145, -845],
  '1f524': [-2145, -910],
  '1f525': [-2145, -975],
  '1f526': [-2145, -1040],
  '1f527': [-2145, -1105],
  '1f528': [-2145, -1170],
  '1f529': [-2145, -1235],
  '1f52a': [-2145, -1300],
  '1f52b': [-2145, -1365],
  '1f52c': [-2145, -1430],
  '1f52d': [-2145, -1495],
  '1f52e': [-2145, -1560],
  '1f52f': [-2145, -1625],
  '1f530': [-2145, -1690],
  '1f531': [-2145, -1755],
  '1f532': [-2145, -1820],
  '1f533': [-2145, -1885],
  '1f534': [-2145, -1950],
  '1f535': [-2145, -2015],
  '1f536': [-2145, -2080],
  '1f537': [0, -2145],
  '1f538': [-65, -2145],
  '1f539': [-130, -2145],
  '1f53a': [-195, -2145],
  '1f53b': [-260, -2145],
  '1f53c': [-325, -2145],
  '1f53d': [-390, -2145],
  '1f549': [-455, -2145],
  '1f54a': [-520, -2145],
  '1f54b': [-585, -2145],
  '1f54c': [-650, -2145],
  '1f54d': [-715, -2145],
  '1f54e': [-780, -2145],
  '1f550': [-845, -2145],
  '1f551': [-910, -2145],
  '1f552': [-975, -2145],
  '1f553': [-1040, -2145],
  '1f554': [-1105, -2145],
  '1f555': [-1170, -2145],
  '1f556': [-1235, -2145],
  '1f557': [-1300, -2145],
  '1f558': [-1365, -2145],
  '1f559': [-1430, -2145],
  '1f55a': [-1495, -2145],
  '1f55b': [-1560, -2145],
  '1f55c': [-1625, -2145],
  '1f55d': [-1690, -2145],
  '1f55e': [-1755, -2145],
  '1f55f': [-1820, -2145],
  '1f560': [-1885, -2145],
  '1f561': [-1950, -2145],
  '1f562': [-2015, -2145],
  '1f563': [-2080, -2145],
  '1f564': [-2145, -2145],
  '1f565': [-2210, 0],
  '1f566': [-2210, -65],
  '1f567': [-2210, -130],
  '1f56f': [-2210, -195],
  '1f570': [-2210, -260],
  '1f573': [-2210, -325],
  '1f574': [-2210, -390],
  '1f575-1f3fb': [-2210, -455],
  '1f575-1f3fc': [-2210, -520],
  '1f575-1f3fd': [-2210, -585],
  '1f575-1f3fe': [-2210, -650],
  '1f575-1f3ff': [-2210, -715],
  '1f575': [-2210, -780],
  '1f576': [-2210, -845],
  '1f577': [-2210, -910],
  '1f578': [-2210, -975],
  '1f579': [-2210, -1040],
  '1f57a-1f3fb': [-2210, -1105],
  '1f57a-1f3fc': [-2210, -1170],
  '1f57a-1f3fd': [-2210, -1235],
  '1f57a-1f3fe': [-2210, -1300],
  '1f57a-1f3ff': [-2210, -1365],
  '1f57a': [-2210, -1430],
  '1f587': [-2210, -1495],
  '1f58a': [-2210, -1560],
  '1f58b': [-2210, -1625],
  '1f58c': [-2210, -1690],
  '1f58d': [-2210, -1755],
  '1f590-1f3fb': [-2210, -1820],
  '1f590-1f3fc': [-2210, -1885],
  '1f590-1f3fd': [-2210, -1950],
  '1f590-1f3fe': [-2210, -2015],
  '1f590-1f3ff': [-2210, -2080],
  '1f590': [-2210, -2145],
  '1f595-1f3fb': [0, -2210],
  '1f595-1f3fc': [-65, -2210],
  '1f595-1f3fd': [-130, -2210],
  '1f595-1f3fe': [-195, -2210],
  '1f595-1f3ff': [-260, -2210],
  '1f595': [-325, -2210],
  '1f596-1f3fb': [-390, -2210],
  '1f596-1f3fc': [-455, -2210],
  '1f596-1f3fd': [-520, -2210],
  '1f596-1f3fe': [-585, -2210],
  '1f596-1f3ff': [-650, -2210],
  '1f596': [-715, -2210],
  '1f5a4': [-780, -2210],
  '1f5a5': [-845, -2210],
  '1f5a8': [-910, -2210],
  '1f5b1': [-975, -2210],
  '1f5b2': [-1040, -2210],
  '1f5bc': [-1105, -2210],
  '1f5c2': [-1170, -2210],
  '1f5c3': [-1235, -2210],
  '1f5c4': [-1300, -2210],
  '1f5d1': [-1365, -2210],
  '1f5d2': [-1430, -2210],
  '1f5d3': [-1495, -2210],
  '1f5dc': [-1560, -2210],
  '1f5dd': [-1625, -2210],
  '1f5de': [-1690, -2210],
  '1f5e1': [-1755, -2210],
  '1f5e3': [-1820, -2210],
  '1f5e8': [-1885, -2210],
  '1f5ef': [-1950, -2210],
  '1f5f3': [-2015, -2210],
  '1f5fa': [-2080, -2210],
  '1f5fb': [-2145, -2210],
  '1f5fc': [-2210, -2210],
  '1f5fd': [-2275, 0],
  '1f5fe': [-2275, -65],
  '1f5ff': [-2275, -130],
  '1f600': [-2275, -195],
  '1f601': [-2275, -260],
  '1f602': [-2275, -325],
  '1f603': [-2275, -390],
  '1f604': [-2275, -455],
  '1f605': [-2275, -520],
  '1f606': [-2275, -585],
  '1f607': [-2275, -650],
  '1f608': [-2275, -715],
  '1f609': [-2275, -780],
  '1f60a': [-2275, -845],
  '1f60b': [-2275, -910],
  '1f60c': [-2275, -975],
  '1f60d': [-2275, -1040],
  '1f60e': [-2275, -1105],
  '1f60f': [-2275, -1170],
  '1f610': [-2275, -1235],
  '1f611': [-2275, -1300],
  '1f612': [-2275, -1365],
  '1f613': [-2275, -1430],
  '1f614': [-2275, -1495],
  '1f615': [-2275, -1560],
  '1f616': [-2275, -1625],
  '1f617': [-2275, -1690],
  '1f618': [-2275, -1755],
  '1f619': [-2275, -1820],
  '1f61a': [-2275, -1885],
  '1f61b': [-2275, -1950],
  '1f61c': [-2275, -2015],
  '1f61d': [-2275, -2080],
  '1f61e': [-2275, -2145],
  '1f61f': [-2275, -2210],
  '1f620': [0, -2275],
  '1f621': [-65, -2275],
  '1f622': [-130, -2275],
  '1f623': [-195, -2275],
  '1f624': [-260, -2275],
  '1f625': [-325, -2275],
  '1f626': [-390, -2275],
  '1f627': [-455, -2275],
  '1f628': [-520, -2275],
  '1f629': [-585, -2275],
  '1f62a': [-650, -2275],
  '1f62b': [-715, -2275],
  '1f62c': [-780, -2275],
  '1f62d': [-845, -2275],
  '1f62e': [-910, -2275],
  '1f62f': [-975, -2275],
  '1f630': [-1040, -2275],
  '1f631': [-1105, -2275],
  '1f632': [-1170, -2275],
  '1f633': [-1235, -2275],
  '1f634': [-1300, -2275],
  '1f635': [-1365, -2275],
  '1f636': [-1430, -2275],
  '1f637': [-1495, -2275],
  '1f638': [-1560, -2275],
  '1f639': [-1625, -2275],
  '1f63a': [-1690, -2275],
  '1f63b': [-1755, -2275],
  '1f63c': [-1820, -2275],
  '1f63d': [-1885, -2275],
  '1f63e': [-1950, -2275],
  '1f63f': [-2015, -2275],
  '1f640': [-2080, -2275],
  '1f641': [-2145, -2275],
  '1f642': [-2210, -2275],
  '1f643': [-2275, -2275],
  '1f644': [-2340, 0],
  '1f645-1f3fb': [-2340, -65],
  '1f645-1f3fc': [-2340, -130],
  '1f645-1f3fd': [-2340, -195],
  '1f645-1f3fe': [-2340, -260],
  '1f645-1f3ff': [-2340, -325],
  '1f645': [-2340, -390],
  '1f646-1f3fb': [-2340, -455],
  '1f646-1f3fc': [-2340, -520],
  '1f646-1f3fd': [-2340, -585],
  '1f646-1f3fe': [-2340, -650],
  '1f646-1f3ff': [-2340, -715],
  '1f646': [-2340, -780],
  '1f647-1f3fb': [-2340, -845],
  '1f647-1f3fc': [-2340, -910],
  '1f647-1f3fd': [-2340, -975],
  '1f647-1f3fe': [-2340, -1040],
  '1f647-1f3ff': [-2340, -1105],
  '1f647': [-2340, -1170],
  '1f648': [-2340, -1235],
  '1f649': [-2340, -1300],
  '1f64a': [-2340, -1365],
  '1f64b-1f3fb': [-2340, -1430],
  '1f64b-1f3fc': [-2340, -1495],
  '1f64b-1f3fd': [-2340, -1560],
  '1f64b-1f3fe': [-2340, -1625],
  '1f64b-1f3ff': [-2340, -1690],
  '1f64b': [-2340, -1755],
  '1f64c-1f3fb': [-2340, -1820],
  '1f64c-1f3fc': [-2340, -1885],
  '1f64c-1f3fd': [-2340, -1950],
  '1f64c-1f3fe': [-2340, -2015],
  '1f64c-1f3ff': [-2340, -2080],
  '1f64c': [-2340, -2145],
  '1f64d-1f3fb': [-2340, -2210],
  '1f64d-1f3fc': [-2340, -2275],
  '1f64d-1f3fd': [0, -2340],
  '1f64d-1f3fe': [-65, -2340],
  '1f64d-1f3ff': [-130, -2340],
  '1f64d': [-195, -2340],
  '1f64e-1f3fb': [-260, -2340],
  '1f64e-1f3fc': [-325, -2340],
  '1f64e-1f3fd': [-390, -2340],
  '1f64e-1f3fe': [-455, -2340],
  '1f64e-1f3ff': [-520, -2340],
  '1f64e': [-585, -2340],
  '1f64f-1f3fb': [-650, -2340],
  '1f64f-1f3fc': [-715, -2340],
  '1f64f-1f3fd': [-780, -2340],
  '1f64f-1f3fe': [-845, -2340],
  '1f64f-1f3ff': [-910, -2340],
  '1f64f': [-975, -2340],
  '1f680': [-1040, -2340],
  '1f681': [-1105, -2340],
  '1f682': [-1170, -2340],
  '1f683': [-1235, -2340],
  '1f684': [-1300, -2340],
  '1f685': [-1365, -2340],
  '1f686': [-1430, -2340],
  '1f687': [-1495, -2340],
  '1f688': [-1560, -2340],
  '1f689': [-1625, -2340],
  '1f68a': [-1690, -2340],
  '1f68b': [-1755, -2340],
  '1f68c': [-1820, -2340],
  '1f68d': [-1885, -2340],
  '1f68e': [-1950, -2340],
  '1f68f': [-2015, -2340],
  '1f690': [-2080, -2340],
  '1f691': [-2145, -2340],
  '1f692': [-2210, -2340],
  '1f693': [-2275, -2340],
  '1f694': [-2340, -2340],
  '1f695': [-2405, 0],
  '1f696': [-2405, -65],
  '1f697': [-2405, -130],
  '1f698': [-2405, -195],
  '1f699': [-2405, -260],
  '1f69a': [-2405, -325],
  '1f69b': [-2405, -390],
  '1f69c': [-2405, -455],
  '1f69d': [-2405, -520],
  '1f69e': [-2405, -585],
  '1f69f': [-2405, -650],
  '1f6a0': [-2405, -715],
  '1f6a1': [-2405, -780],
  '1f6a2': [-2405, -845],
  '1f6a3-1f3fb': [-2405, -910],
  '1f6a3-1f3fc': [-2405, -975],
  '1f6a3-1f3fd': [-2405, -1040],
  '1f6a3-1f3fe': [-2405, -1105],
  '1f6a3-1f3ff': [-2405, -1170],
  '1f6a3': [-2405, -1235],
  '1f6a4': [-2405, -1300],
  '1f6a5': [-2405, -1365],
  '1f6a6': [-2405, -1430],
  '1f6a7': [-2405, -1495],
  '1f6a8': [-2405, -1560],
  '1f6a9': [-2405, -1625],
  '1f6aa': [-2405, -1690],
  '1f6ab': [-2405, -1755],
  '1f6ac': [-2405, -1820],
  '1f6ad': [-2405, -1885],
  '1f6ae': [-2405, -1950],
  '1f6af': [-2405, -2015],
  '1f6b0': [-2405, -2080],
  '1f6b1': [-2405, -2145],
  '1f6b2': [-2405, -2210],
  '1f6b3': [-2405, -2275],
  '1f6b4-1f3fb': [-2405, -2340],
  '1f6b4-1f3fc': [0, -2405],
  '1f6b4-1f3fd': [-65, -2405],
  '1f6b4-1f3fe': [-130, -2405],
  '1f6b4-1f3ff': [-195, -2405],
  '1f6b4': [-260, -2405],
  '1f6b5-1f3fb': [-325, -2405],
  '1f6b5-1f3fc': [-390, -2405],
  '1f6b5-1f3fd': [-455, -2405],
  '1f6b5-1f3fe': [-520, -2405],
  '1f6b5-1f3ff': [-585, -2405],
  '1f6b5': [-650, -2405],
  '1f6b6-1f3fb': [-715, -2405],
  '1f6b6-1f3fc': [-780, -2405],
  '1f6b6-1f3fd': [-845, -2405],
  '1f6b6-1f3fe': [-910, -2405],
  '1f6b6-1f3ff': [-975, -2405],
  '1f6b6': [-1040, -2405],
  '1f6b7': [-1105, -2405],
  '1f6b8': [-1170, -2405],
  '1f6b9': [-1235, -2405],
  '1f6ba': [-1300, -2405],
  '1f6bb': [-1365, -2405],
  '1f6bc': [-1430, -2405],
  '1f6bd': [-1495, -2405],
  '1f6be': [-1560, -2405],
  '1f6bf': [-1625, -2405],
  '1f6c0-1f3fb': [-1690, -2405],
  '1f6c0-1f3fc': [-1755, -2405],
  '1f6c0-1f3fd': [-1820, -2405],
  '1f6c0-1f3fe': [-1885, -2405],
  '1f6c0-1f3ff': [-1950, -2405],
  '1f6c0': [-2015, -2405],
  '1f6c1': [-2080, -2405],
  '1f6c2': [-2145, -2405],
  '1f6c3': [-2210, -2405],
  '1f6c4': [-2275, -2405],
  '1f6c5': [-2340, -2405],
  '1f6cb': [-2405, -2405],
  '1f6cc': [-2470, 0],
  '1f6cd': [-2470, -65],
  '1f6ce': [-2470, -130],
  '1f6cf': [-2470, -195],
  '1f6d0': [-2470, -260],
  '1f6d1': [-2470, -325],
  '1f6d2': [-2470, -390],
  '1f6e0': [-2470, -455],
  '1f6e1': [-2470, -520],
  '1f6e2': [-2470, -585],
  '1f6e3': [-2470, -650],
  '1f6e4': [-2470, -715],
  '1f6e5': [-2470, -780],
  '1f6e9': [-2470, -845],
  '1f6eb': [-2470, -910],
  '1f6ec': [-2470, -975],
  '1f6f0': [-2470, -1040],
  '1f6f3': [-2470, -1105],
  '1f6f4': [-2470, -1170],
  '1f6f5': [-2470, -1235],
  '1f6f6': [-2470, -1300],
  '1f910': [-2470, -1365],
  '1f911': [-2470, -1430],
  '1f912': [-2470, -1495],
  '1f913': [-2470, -1560],
  '1f914': [-2470, -1625],
  '1f915': [-2470, -1690],
  '1f916': [-2470, -1755],
  '1f917': [-2470, -1820],
  '1f918-1f3fb': [-2470, -1885],
  '1f918-1f3fc': [-2470, -1950],
  '1f918-1f3fd': [-2470, -2015],
  '1f918-1f3fe': [-2470, -2080],
  '1f918-1f3ff': [-2470, -2145],
  '1f918': [-2470, -2210],
  '1f919-1f3fb': [-2470, -2275],
  '1f919-1f3fc': [-2470, -2340],
  '1f919-1f3fd': [-2470, -2405],
  '1f919-1f3fe': [0, -2470],
  '1f919-1f3ff': [-65, -2470],
  '1f919': [-130, -2470],
  '1f91a-1f3fb': [-195, -2470],
  '1f91a-1f3fc': [-260, -2470],
  '1f91a-1f3fd': [-325, -2470],
  '1f91a-1f3fe': [-390, -2470],
  '1f91a-1f3ff': [-455, -2470],
  '1f91a': [-520, -2470],
  '1f91b-1f3fb': [-585, -2470],
  '1f91b-1f3fc': [-650, -2470],
  '1f91b-1f3fd': [-715, -2470],
  '1f91b-1f3fe': [-780, -2470],
  '1f91b-1f3ff': [-845, -2470],
  '1f91b': [-910, -2470],
  '1f91c-1f3fb': [-975, -2470],
  '1f91c-1f3fc': [-1040, -2470],
  '1f91c-1f3fd': [-1105, -2470],
  '1f91c-1f3fe': [-1170, -2470],
  '1f91c-1f3ff': [-1235, -2470],
  '1f91c': [-1300, -2470],
  '1f91d-1f3fb': [-1365, -2470],
  '1f91d-1f3fc': [-1430, -2470],
  '1f91d-1f3fd': [-1495, -2470],
  '1f91d-1f3fe': [-1560, -2470],
  '1f91d-1f3ff': [-1625, -2470],
  '1f91d': [-1690, -2470],
  '1f91e-1f3fb': [-1755, -2470],
  '1f91e-1f3fc': [-1820, -2470],
  '1f91e-1f3fd': [-1885, -2470],
  '1f91e-1f3fe': [-1950, -2470],
  '1f91e-1f3ff': [-2015, -2470],
  '1f91e': [-2080, -2470],
  '1f920': [-2145, -2470],
  '1f921': [-2210, -2470],
  '1f922': [-2275, -2470],
  '1f923': [-2340, -2470],
  '1f924': [-2405, -2470],
  '1f925': [-2470, -2470],
  '1f926-1f3fb': [-2535, 0],
  '1f926-1f3fc': [-2535, -65],
  '1f926-1f3fd': [-2535, -130],
  '1f926-1f3fe': [-2535, -195],
  '1f926-1f3ff': [-2535, -260],
  '1f926': [-2535, -325],
  '1f927': [-2535, -390],
  '1f930-1f3fb': [-2535, -455],
  '1f930-1f3fc': [-2535, -520],
  '1f930-1f3fd': [-2535, -585],
  '1f930-1f3fe': [-2535, -650],
  '1f930-1f3ff': [-2535, -715],
  '1f930': [-2535, -780],
  '1f933-1f3fb': [-2535, -845],
  '1f933-1f3fc': [-2535, -910],
  '1f933-1f3fd': [-2535, -975],
  '1f933-1f3fe': [-2535, -1040],
  '1f933-1f3ff': [-2535, -1105],
  '1f933': [-2535, -1170],
  '1f934-1f3fb': [-2535, -1235],
  '1f934-1f3fc': [-2535, -1300],
  '1f934-1f3fd': [-2535, -1365],
  '1f934-1f3fe': [-2535, -1430],
  '1f934-1f3ff': [-2535, -1495],
  '1f934': [-2535, -1560],
  '1f935-1f3fb': [-2535, -1625],
  '1f935-1f3fc': [-2535, -1690],
  '1f935-1f3fd': [-2535, -1755],
  '1f935-1f3fe': [-2535, -1820],
  '1f935-1f3ff': [-2535, -1885],
  '1f935': [-2535, -1950],
  '1f936-1f3fb': [-2535, -2015],
  '1f936-1f3fc': [-2535, -2080],
  '1f936-1f3fd': [-2535, -2145],
  '1f936-1f3fe': [-2535, -2210],
  '1f936-1f3ff': [-2535, -2275],
  '1f936': [-2535, -2340],
  '1f937-1f3fb': [-2535, -2405],
  '1f937-1f3fc': [-2535, -2470],
  '1f937-1f3fd': [0, -2535],
  '1f937-1f3fe': [-65, -2535],
  '1f937-1f3ff': [-130, -2535],
  '1f937': [-195, -2535],
  '1f938-1f3fb': [-260, -2535],
  '1f938-1f3fc': [-325, -2535],
  '1f938-1f3fd': [-390, -2535],
  '1f938-1f3fe': [-455, -2535],
  '1f938-1f3ff': [-520, -2535],
  '1f938': [-585, -2535],
  '1f939-1f3fb': [-650, -2535],
  '1f939-1f3fc': [-715, -2535],
  '1f939-1f3fd': [-780, -2535],
  '1f939-1f3fe': [-845, -2535],
  '1f939-1f3ff': [-910, -2535],
  '1f939': [-975, -2535],
  '1f93a': [-1040, -2535],
  '1f93c-1f3fb': [-1105, -2535],
  '1f93c-1f3fc': [-1170, -2535],
  '1f93c-1f3fd': [-1235, -2535],
  '1f93c-1f3fe': [-1300, -2535],
  '1f93c-1f3ff': [-1365, -2535],
  '1f93c': [-1430, -2535],
  '1f93d-1f3fb': [-1495, -2535],
  '1f93d-1f3fc': [-1560, -2535],
  '1f93d-1f3fd': [-1625, -2535],
  '1f93d-1f3fe': [-1690, -2535],
  '1f93d-1f3ff': [-1755, -2535],
  '1f93d': [-1820, -2535],
  '1f93e-1f3fb': [-1885, -2535],
  '1f93e-1f3fc': [-1950, -2535],
  '1f93e-1f3fd': [-2015, -2535],
  '1f93e-1f3fe': [-2080, -2535],
  '1f93e-1f3ff': [-2145, -2535],
  '1f93e': [-2210, -2535],
  '1f940': [-2275, -2535],
  '1f941': [-2340, -2535],
  '1f942': [-2405, -2535],
  '1f943': [-2470, -2535],
  '1f944': [-2535, -2535],
  '1f945': [-2600, 0],
  '1f947': [-2600, -65],
  '1f948': [-2600, -130],
  '1f949': [-2600, -195],
  '1f94a': [-2600, -260],
  '1f94b': [-2600, -325],
  '1f950': [-2600, -390],
  '1f951': [-2600, -455],
  '1f952': [-2600, -520],
  '1f953': [-2600, -585],
  '1f954': [-2600, -650],
  '1f955': [-2600, -715],
  '1f956': [-2600, -780],
  '1f957': [-2600, -845],
  '1f958': [-2600, -910],
  '1f959': [-2600, -975],
  '1f95a': [-2600, -1040],
  '1f95b': [-2600, -1105],
  '1f95c': [-2600, -1170],
  '1f95d': [-2600, -1235],
  '1f95e': [-2600, -1300],
  '1f980': [-2600, -1365],
  '1f981': [-2600, -1430],
  '1f982': [-2600, -1495],
  '1f983': [-2600, -1560],
  '1f984': [-2600, -1625],
  '1f985': [-2600, -1690],
  '1f986': [-2600, -1755],
  '1f987': [-2600, -1820],
  '1f988': [-2600, -1885],
  '1f989': [-2600, -1950],
  '1f98a': [-2600, -2015],
  '1f98b': [-2600, -2080],
  '1f98c': [-2600, -2145],
  '1f98d': [-2600, -2210],
  '1f98e': [-2600, -2275],
  '1f98f': [-2600, -2340],
  '1f990': [-2600, -2405],
  '1f991': [-2600, -2470],
  '1f9c0': [-2600, -2535],
  '203c': [0, -2600],
  '2049': [-65, -2600],
  '2122': [-130, -2600],
  '2139': [-195, -2600],
  '2194': [-260, -2600],
  '2195': [-325, -2600],
  '2196': [-390, -2600],
  '2197': [-455, -2600],
  '2198': [-520, -2600],
  '2199': [-585, -2600],
  '21a9': [-650, -2600],
  '21aa': [-715, -2600],
  '231a': [-780, -2600],
  '231b': [-845, -2600],
  '2328': [-910, -2600],
  '23cf': [-975, -2600],
  '23e9': [-1040, -2600],
  '23ea': [-1105, -2600],
  '23eb': [-1170, -2600],
  '23ec': [-1235, -2600],
  '23ed': [-1300, -2600],
  '23ee': [-1365, -2600],
  '23ef': [-1430, -2600],
  '23f0': [-1495, -2600],
  '23f1': [-1560, -2600],
  '23f2': [-1625, -2600],
  '23f3': [-1690, -2600],
  '23f8': [-1755, -2600],
  '23f9': [-1820, -2600],
  '23fa': [-1885, -2600],
  '24c2': [-1950, -2600],
  '25aa': [-2015, -2600],
  '25ab': [-2080, -2600],
  '25b6': [-2145, -2600],
  '25c0': [-2210, -2600],
  '25fb': [-2275, -2600],
  '25fc': [-2340, -2600],
  '25fd': [-2405, -2600],
  '25fe': [-2470, -2600],
  '2600': [-2535, -2600],
  '2601': [-2600, -2600],
  '2602': [-2665, 0],
  '2603': [-2665, -65],
  '2604': [-2665, -130],
  '260e': [-2665, -195],
  '2611': [-2665, -260],
  '2614': [-2665, -325],
  '2615': [-2665, -390],
  '2618': [-2665, -455],
  '261d-1f3fb': [-2665, -520],
  '261d-1f3fc': [-2665, -585],
  '261d-1f3fd': [-2665, -650],
  '261d-1f3fe': [-2665, -715],
  '261d-1f3ff': [-2665, -780],
  '261d': [-2665, -845],
  '2620': [-2665, -910],
  '2622': [-2665, -975],
  '2623': [-2665, -1040],
  '2626': [-2665, -1105],
  '262a': [-2665, -1170],
  '262e': [-2665, -1235],
  '262f': [-2665, -1300],
  '2638': [-2665, -1365],
  '2639': [-2665, -1430],
  '263a': [-2665, -1495],
  '2648': [-2665, -1560],
  '2649': [-2665, -1625],
  '264a': [-2665, -1690],
  '264b': [-2665, -1755],
  '264c': [-2665, -1820],
  '264d': [-2665, -1885],
  '264e': [-2665, -1950],
  '264f': [-2665, -2015],
  '2650': [-2665, -2080],
  '2651': [-2665, -2145],
  '2652': [-2665, -2210],
  '2653': [-2665, -2275],
  '2660': [-2665, -2340],
  '2663': [-2665, -2405],
  '2665': [-2665, -2470],
  '2666': [-2665, -2535],
  '2668': [-2665, -2600],
  '267b': [0, -2665],
  '267f': [-65, -2665],
  '2692': [-130, -2665],
  '2693': [-195, -2665],
  '2694': [-260, -2665],
  '2696': [-325, -2665],
  '2697': [-390, -2665],
  '2699': [-455, -2665],
  '269b': [-520, -2665],
  '269c': [-585, -2665],
  '26a0': [-650, -2665],
  '26a1': [-715, -2665],
  '26aa': [-780, -2665],
  '26ab': [-845, -2665],
  '26b0': [-910, -2665],
  '26b1': [-975, -2665],
  '26bd': [-1040, -2665],
  '26be': [-1105, -2665],
  '26c4': [-1170, -2665],
  '26c5': [-1235, -2665],
  '26c8': [-1300, -2665],
  '26ce': [-1365, -2665],
  '26cf': [-1430, -2665],
  '26d1': [-1495, -2665],
  '26d3': [-1560, -2665],
  '26d4': [-1625, -2665],
  '26e9': [-1690, -2665],
  '26ea': [-1755, -2665],
  '26f0': [-1820, -2665],
  '26f1': [-1885, -2665],
  '26f2': [-1950, -2665],
  '26f3': [-2015, -2665],
  '26f4': [-2080, -2665],
  '26f5': [-2145, -2665],
  '26f7': [-2210, -2665],
  '26f8': [-2275, -2665],
  '26f9-1f3fb': [-2340, -2665],
  '26f9-1f3fc': [-2405, -2665],
  '26f9-1f3fd': [-2470, -2665],
  '26f9-1f3fe': [-2535, -2665],
  '26f9-1f3ff': [-2600, -2665],
  '26f9': [-2665, -2665],
  '26fa': [-2730, 0],
  '26fd': [-2730, -65],
  '2702': [-2730, -130],
  '2705': [-2730, -195],
  '2708': [-2730, -260],
  '2709': [-2730, -325],
  '270a-1f3fb': [-2730, -390],
  '270a-1f3fc': [-2730, -455],
  '270a-1f3fd': [-2730, -520],
  '270a-1f3fe': [-2730, -585],
  '270a-1f3ff': [-2730, -650],
  '270a': [-2730, -715],
  '270b-1f3fb': [-2730, -780],
  '270b-1f3fc': [-2730, -845],
  '270b-1f3fd': [-2730, -910],
  '270b-1f3fe': [-2730, -975],
  '270b-1f3ff': [-2730, -1040],
  '270b': [-2730, -1105],
  '270c-1f3fb': [-2730, -1170],
  '270c-1f3fc': [-2730, -1235],
  '270c-1f3fd': [-2730, -1300],
  '270c-1f3fe': [-2730, -1365],
  '270c-1f3ff': [-2730, -1430],
  '270c': [-2730, -1495],
  '270d-1f3fb': [-2730, -1560],
  '270d-1f3fc': [-2730, -1625],
  '270d-1f3fd': [-2730, -1690],
  '270d-1f3fe': [-2730, -1755],
  '270d-1f3ff': [-2730, -1820],
  '270d': [-2730, -1885],
  '270f': [-2730, -1950],
  '2712': [-2730, -2015],
  '2714': [-2730, -2080],
  '2716': [-2730, -2145],
  '271d': [-2730, -2210],
  '2721': [-2730, -2275],
  '2728': [-2730, -2340],
  '2733': [-2730, -2405],
  '2734': [-2730, -2470],
  '2744': [-2730, -2535],
  '2747': [-2730, -2600],
  '274c': [-2730, -2665],
  '274e': [0, -2730],
  '2753': [-65, -2730],
  '2754': [-130, -2730],
  '2755': [-195, -2730],
  '2757': [-260, -2730],
  '2763': [-325, -2730],
  '2764': [-390, -2730],
  '2795': [-455, -2730],
  '2796': [-520, -2730],
  '2797': [-585, -2730],
  '27a1': [-650, -2730],
  '27b0': [-715, -2730],
  '27bf': [-780, -2730],
  '2934': [-845, -2730],
  '2935': [-910, -2730],
  '2b05': [-975, -2730],
  '2b06': [-1040, -2730],
  '2b07': [-1105, -2730],
  '2b1b': [-1170, -2730],
  '2b1c': [-1235, -2730],
  '2b50': [-1300, -2730],
  '2b55': [-1365, -2730],
  '3030': [-1430, -2730],
  '303d': [-1495, -2730],
  '3297': [-1560, -2730],
  '3299': [-1625, -2730] };

/***/ }),

/***/ 252:
/*!************************************************************!*\
  !*** ./node_modules/react-emojione/lib/data/emoji-data.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
/*eslint-disable*/
// Do not edit!
// This file was auto-generated by create-emoji-data.js
exports.default = [["1f468-1f469-1f466-1f466", "👨👩👦👦", ":family_mwbb:"], ["1f469-1f469-1f467-1f467", "👩👩👧👧", ":family_wwgg:"], ["1f469-1f469-1f466-1f466", "👩👩👦👦", ":family_wwbb:"], ["1f469-1f469-1f467-1f466", "👩👩👧👦", ":family_wwgb:"], ["1f468-1f469-1f467-1f466", "👨👩👧👦", ":family_mwgb:"], ["1f468-1f468-1f466-1f466", "👨👨👦👦", ":family_mmbb:"], ["1f468-1f469-1f467-1f467", "👨👩👧👧", ":family_mwgg:"], ["1f468-1f468-1f467-1f466", "👨👨👧👦", ":family_mmgb:"], ["1f468-1f468-1f467-1f467", "👨👨👧👧", ":family_mmgg:"], ["1f468-2764-1f48b-1f468", "👨❤💋👨", ":kiss_mm:"], ["1f469-2764-1f48b-1f469", "👩❤💋👩", ":kiss_ww:"], ["1f468-1f468-1f466", "👨👨👦", ":family_mmb:"], ["1f468-1f469-1f467", "👨👩👧", ":family_mwg:"], ["1f468-1f468-1f467", "👨👨👧", ":family_mmg:"], ["1f469-1f469-1f466", "👩👩👦", ":family_wwb:"], ["1f469-1f469-1f467", "👩👩👧", ":family_wwg:"], ["1f469-2764-1f469", "👩❤👩", ":couple_ww:"], ["1f468-2764-1f468", "👨❤👨", ":couple_mm:"], ["1f44e-1f3fc", "👎🏼", ":thumbsdown_tone2:"], ["1f469-1f3fe", "👩🏾", ":woman_tone4:"], ["1f469-1f3ff", "👩🏿", ":woman_tone5:"], ["1f474-1f3fb", "👴🏻", ":older_man_tone1:"], ["1f474-1f3fc", "👴🏼", ":older_man_tone2:"], ["1f474-1f3fd", "👴🏽", ":older_man_tone3:"], ["1f474-1f3fe", "👴🏾", ":older_man_tone4:"], ["1f474-1f3ff", "👴🏿", ":older_man_tone5:"], ["1f475-1f3fb", "👵🏻", ":older_woman_tone1:"], ["1f475-1f3fc", "👵🏼", ":older_woman_tone2:"], ["1f475-1f3fd", "👵🏽", ":older_woman_tone3:"], ["1f475-1f3fe", "👵🏾", ":older_woman_tone4:"], ["1f475-1f3ff", "👵🏿", ":older_woman_tone5:"], ["1f476-1f3fb", "👶🏻", ":baby_tone1:"], ["1f476-1f3fc", "👶🏼", ":baby_tone2:"], ["1f476-1f3fd", "👶🏽", ":baby_tone3:"], ["1f476-1f3fe", "👶🏾", ":baby_tone4:"], ["1f476-1f3ff", "👶🏿", ":baby_tone5:"], ["1f47c-1f3fb", "👼🏻", ":angel_tone1:"], ["1f47c-1f3fc", "👼🏼", ":angel_tone2:"], ["1f47c-1f3fd", "👼🏽", ":angel_tone3:"], ["1f47c-1f3fe", "👼🏾", ":angel_tone4:"], ["1f47c-1f3ff", "👼🏿", ":angel_tone5:"], ["1f46e-1f3fb", "👮🏻", ":cop_tone1:"], ["1f46e-1f3fc", "👮🏼", ":cop_tone2:"], ["1f46e-1f3fd", "👮🏽", ":cop_tone3:"], ["1f46e-1f3fe", "👮🏾", ":cop_tone4:"], ["1f46e-1f3ff", "👮🏿", ":cop_tone5:"], ["1f575-1f3fb", "🕵🏻", ":spy_tone1:"], ["1f575-1f3fc", "🕵🏼", ":spy_tone2:"], ["1f575-1f3fd", "🕵🏽", ":spy_tone3:"], ["1f575-1f3fe", "🕵🏾", ":spy_tone4:"], ["1f575-1f3ff", "🕵🏿", ":spy_tone5:"], ["1f482-1f3fb", "💂🏻", ":guardsman_tone1:"], ["1f482-1f3fc", "💂🏼", ":guardsman_tone2:"], ["1f482-1f3fd", "💂🏽", ":guardsman_tone3:"], ["1f482-1f3fe", "💂🏾", ":guardsman_tone4:"], ["1f482-1f3ff", "💂🏿", ":guardsman_tone5:"], ["1f477-1f3fb", "👷🏻", ":construction_worker_tone1:"], ["1f477-1f3fc", "👷🏼", ":construction_worker_tone2:"], ["1f477-1f3fd", "👷🏽", ":construction_worker_tone3:"], ["1f477-1f3fe", "👷🏾", ":construction_worker_tone4:"], ["1f477-1f3ff", "👷🏿", ":construction_worker_tone5:"], ["1f473-1f3fb", "👳🏻", ":man_with_turban_tone1:"], ["1f473-1f3fc", "👳🏼", ":man_with_turban_tone2:"], ["1f473-1f3fd", "👳🏽", ":man_with_turban_tone3:"], ["1f473-1f3fe", "👳🏾", ":man_with_turban_tone4:"], ["1f473-1f3ff", "👳🏿", ":man_with_turban_tone5:"], ["1f471-1f3fb", "👱🏻", ":person_with_blond_hair_tone1:"], ["1f471-1f3fc", "👱🏼", ":person_with_blond_hair_tone2:"], ["1f471-1f3fd", "👱🏽", ":person_with_blond_hair_tone3:"], ["1f471-1f3fe", "👱🏾", ":person_with_blond_hair_tone4:"], ["1f471-1f3ff", "👱🏿", ":person_with_blond_hair_tone5:"], ["1f385-1f3fb", "🎅🏻", ":santa_tone1:"], ["1f385-1f3fc", "🎅🏼", ":santa_tone2:"], ["1f385-1f3fd", "🎅🏽", ":santa_tone3:"], ["1f385-1f3fe", "🎅🏾", ":santa_tone4:"], ["1f385-1f3ff", "🎅🏿", ":santa_tone5:"], ["1f936-1f3fb", "🤶🏻", ":mrs_claus_tone1:"], ["1f936-1f3fc", "🤶🏼", ":mrs_claus_tone2:"], ["1f936-1f3fd", "🤶🏽", ":mrs_claus_tone3:"], ["1f936-1f3fe", "🤶🏾", ":mrs_claus_tone4:"], ["1f936-1f3ff", "🤶🏿", ":mrs_claus_tone5:"], ["1f478-1f3fb", "👸🏻", ":princess_tone1:"], ["1f478-1f3fc", "👸🏼", ":princess_tone2:"], ["1f478-1f3fd", "👸🏽", ":princess_tone3:"], ["1f478-1f3fe", "👸🏾", ":princess_tone4:"], ["1f478-1f3ff", "👸🏿", ":princess_tone5:"], ["1f934-1f3fb", "🤴🏻", ":prince_tone1:"], ["1f934-1f3fc", "🤴🏼", ":prince_tone2:"], ["1f934-1f3fd", "🤴🏽", ":prince_tone3:"], ["1f934-1f3fe", "🤴🏾", ":prince_tone4:"], ["1f934-1f3ff", "🤴🏿", ":prince_tone5:"], ["1f470-1f3fb", "👰🏻", ":bride_with_veil_tone1:"], ["1f470-1f3fc", "👰🏼", ":bride_with_veil_tone2:"], ["1f470-1f3fd", "👰🏽", ":bride_with_veil_tone3:"], ["1f470-1f3fe", "👰🏾", ":bride_with_veil_tone4:"], ["1f470-1f3ff", "👰🏿", ":bride_with_veil_tone5:"], ["1f935-1f3fb", "🤵🏻", ":man_in_tuxedo_tone1:"], ["1f935-1f3fc", "🤵🏼", ":man_in_tuxedo_tone2:"], ["1f935-1f3fd", "🤵🏽", ":man_in_tuxedo_tone3:"], ["1f935-1f3fe", "🤵🏾", ":man_in_tuxedo_tone4:"], ["1f935-1f3ff", "🤵🏿", ":man_in_tuxedo_tone5:"], ["1f930-1f3fb", "🤰🏻", ":pregnant_woman_tone1:"], ["1f930-1f3fc", "🤰🏼", ":pregnant_woman_tone2:"], ["1f930-1f3fd", "🤰🏽", ":pregnant_woman_tone3:"], ["1f930-1f3fe", "🤰🏾", ":pregnant_woman_tone4:"], ["1f930-1f3ff", "🤰🏿", ":pregnant_woman_tone5:"], ["1f472-1f3fb", "👲🏻", ":man_with_gua_pi_mao_tone1:"], ["1f472-1f3fc", "👲🏼", ":man_with_gua_pi_mao_tone2:"], ["1f472-1f3fd", "👲🏽", ":man_with_gua_pi_mao_tone3:"], ["1f472-1f3fe", "👲🏾", ":man_with_gua_pi_mao_tone4:"], ["1f472-1f3ff", "👲🏿", ":man_with_gua_pi_mao_tone5:"], ["1f64d-1f3fb", "🙍🏻", ":person_frowning_tone1:"], ["1f64d-1f3fc", "🙍🏼", ":person_frowning_tone2:"], ["1f64d-1f3fd", "🙍🏽", ":person_frowning_tone3:"], ["1f64d-1f3fe", "🙍🏾", ":person_frowning_tone4:"], ["1f64d-1f3ff", "🙍🏿", ":person_frowning_tone5:"], ["1f64e-1f3fb", "🙎🏻", ":person_with_pouting_face_tone1:"], ["1f64e-1f3fc", "🙎🏼", ":person_with_pouting_face_tone2:"], ["1f64e-1f3fd", "🙎🏽", ":person_with_pouting_face_tone3:"], ["1f64e-1f3fe", "🙎🏾", ":person_with_pouting_face_tone4:"], ["1f64e-1f3ff", "🙎🏿", ":person_with_pouting_face_tone5:"], ["1f645-1f3fb", "🙅🏻", ":no_good_tone1:"], ["1f645-1f3fc", "🙅🏼", ":no_good_tone2:"], ["1f645-1f3fd", "🙅🏽", ":no_good_tone3:"], ["1f645-1f3fe", "🙅🏾", ":no_good_tone4:"], ["1f645-1f3ff", "🙅🏿", ":no_good_tone5:"], ["1f646-1f3fb", "🙆🏻", ":ok_woman_tone1:"], ["1f646-1f3fc", "🙆🏼", ":ok_woman_tone2:"], ["1f646-1f3fd", "🙆🏽", ":ok_woman_tone3:"], ["1f646-1f3fe", "🙆🏾", ":ok_woman_tone4:"], ["1f646-1f3ff", "🙆🏿", ":ok_woman_tone5:"], ["1f481-1f3fb", "💁🏻", ":information_desk_person_tone1:"], ["1f481-1f3fc", "💁🏼", ":information_desk_person_tone2:"], ["1f481-1f3fd", "💁🏽", ":information_desk_person_tone3:"], ["1f481-1f3fe", "💁🏾", ":information_desk_person_tone4:"], ["1f481-1f3ff", "💁🏿", ":information_desk_person_tone5:"], ["1f64b-1f3fb", "🙋🏻", ":raising_hand_tone1:"], ["1f64b-1f3fc", "🙋🏼", ":raising_hand_tone2:"], ["1f64b-1f3fd", "🙋🏽", ":raising_hand_tone3:"], ["1f64b-1f3fe", "🙋🏾", ":raising_hand_tone4:"], ["1f64b-1f3ff", "🙋🏿", ":raising_hand_tone5:"], ["1f647-1f3fb", "🙇🏻", ":bow_tone1:"], ["1f647-1f3fc", "🙇🏼", ":bow_tone2:"], ["1f647-1f3fd", "🙇🏽", ":bow_tone3:"], ["1f647-1f3fe", "🙇🏾", ":bow_tone4:"], ["1f647-1f3ff", "🙇🏿", ":bow_tone5:"], ["1f926-1f3fb", "🤦🏻", ":face_palm_tone1:"], ["1f926-1f3fc", "🤦🏼", ":face_palm_tone2:"], ["1f926-1f3fd", "🤦🏽", ":face_palm_tone3:"], ["1f926-1f3fe", "🤦🏾", ":face_palm_tone4:"], ["1f926-1f3ff", "🤦🏿", ":face_palm_tone5:"], ["1f937-1f3fb", "🤷🏻", ":shrug_tone1:"], ["1f937-1f3fc", "🤷🏼", ":shrug_tone2:"], ["1f937-1f3fd", "🤷🏽", ":shrug_tone3:"], ["1f937-1f3fe", "🤷🏾", ":shrug_tone4:"], ["1f937-1f3ff", "🤷🏿", ":shrug_tone5:"], ["1f486-1f3fb", "💆🏻", ":massage_tone1:"], ["1f486-1f3fc", "💆🏼", ":massage_tone2:"], ["1f486-1f3fd", "💆🏽", ":massage_tone3:"], ["1f486-1f3fe", "💆🏾", ":massage_tone4:"], ["1f486-1f3ff", "💆🏿", ":massage_tone5:"], ["1f487-1f3fb", "💇🏻", ":haircut_tone1:"], ["1f487-1f3fc", "💇🏼", ":haircut_tone2:"], ["1f487-1f3fd", "💇🏽", ":haircut_tone3:"], ["1f487-1f3fe", "💇🏾", ":haircut_tone4:"], ["1f487-1f3ff", "💇🏿", ":haircut_tone5:"], ["1f6b6-1f3fb", "🚶🏻", ":walking_tone1:"], ["1f6b6-1f3fc", "🚶🏼", ":walking_tone2:"], ["1f6b6-1f3fd", "🚶🏽", ":walking_tone3:"], ["1f6b6-1f3fe", "🚶🏾", ":walking_tone4:"], ["1f6b6-1f3ff", "🚶🏿", ":walking_tone5:"], ["1f3c3-1f3fb", "🏃🏻", ":runner_tone1:"], ["1f3c3-1f3fc", "🏃🏼", ":runner_tone2:"], ["1f3c3-1f3fd", "🏃🏽", ":runner_tone3:"], ["1f3c3-1f3fe", "🏃🏾", ":runner_tone4:"], ["1f3c3-1f3ff", "🏃🏿", ":runner_tone5:"], ["1f483-1f3fb", "💃🏻", ":dancer_tone1:"], ["1f483-1f3fc", "💃🏼", ":dancer_tone2:"], ["1f483-1f3fd", "💃🏽", ":dancer_tone3:"], ["1f483-1f3fe", "💃🏾", ":dancer_tone4:"], ["1f483-1f3ff", "💃🏿", ":dancer_tone5:"], ["1f57a-1f3fb", "🕺🏻", ":man_dancing_tone1:"], ["1f57a-1f3fc", "🕺🏼", ":man_dancing_tone2:"], ["1f57a-1f3fd", "🕺🏽", ":man_dancing_tone3:"], ["1f57a-1f3fe", "🕺🏾", ":man_dancing_tone4:"], ["1f57a-1f3ff", "🕺🏿", ":man_dancing_tone5:"], ["1f3c7-1f3fb", "🏇🏻", ":horse_racing_tone1:"], ["1f3c7-1f3fc", "🏇🏼", ":horse_racing_tone2:"], ["1f3c7-1f3fd", "🏇🏽", ":horse_racing_tone3:"], ["1f3c7-1f3fe", "🏇🏾", ":horse_racing_tone4:"], ["1f3c7-1f3ff", "🏇🏿", ":horse_racing_tone5:"], ["1f3c4-1f3fb", "🏄🏻", ":surfer_tone1:"], ["1f3c4-1f3fc", "🏄🏼", ":surfer_tone2:"], ["1f3c4-1f3fd", "🏄🏽", ":surfer_tone3:"], ["1f3c4-1f3fe", "🏄🏾", ":surfer_tone4:"], ["1f3c4-1f3ff", "🏄🏿", ":surfer_tone5:"], ["1f6a3-1f3fb", "🚣🏻", ":rowboat_tone1:"], ["1f6a3-1f3fc", "🚣🏼", ":rowboat_tone2:"], ["1f6a3-1f3fd", "🚣🏽", ":rowboat_tone3:"], ["1f6a3-1f3fe", "🚣🏾", ":rowboat_tone4:"], ["1f6a3-1f3ff", "🚣🏿", ":rowboat_tone5:"], ["1f3ca-1f3fb", "🏊🏻", ":swimmer_tone1:"], ["1f3ca-1f3fc", "🏊🏼", ":swimmer_tone2:"], ["1f3ca-1f3fd", "🏊🏽", ":swimmer_tone3:"], ["1f3ca-1f3fe", "🏊🏾", ":swimmer_tone4:"], ["1f3ca-1f3ff", "🏊🏿", ":swimmer_tone5:"], ["1f1ef-1f1ea", "🇯🇪", ":flag_je:"], ["1f1ee-1f1f9", "🇮🇹", ":flag_it:"], ["1f1ee-1f1f8", "🇮🇸", ":flag_is:"], ["1f1ee-1f1f7", "🇮🇷", ":flag_ir:"], ["1f1ee-1f1f6", "🇮🇶", ":flag_iq:"], ["1f3cb-1f3fb", "🏋🏻", ":lifter_tone1:"], ["1f3cb-1f3fc", "🏋🏼", ":lifter_tone2:"], ["1f3cb-1f3fd", "🏋🏽", ":lifter_tone3:"], ["1f3cb-1f3fe", "🏋🏾", ":lifter_tone4:"], ["1f3cb-1f3ff", "🏋🏿", ":lifter_tone5:"], ["1f6b4-1f3fb", "🚴🏻", ":bicyclist_tone1:"], ["1f6b4-1f3fc", "🚴🏼", ":bicyclist_tone2:"], ["1f6b4-1f3fd", "🚴🏽", ":bicyclist_tone3:"], ["1f6b4-1f3fe", "🚴🏾", ":bicyclist_tone4:"], ["1f6b4-1f3ff", "🚴🏿", ":bicyclist_tone5:"], ["1f6b5-1f3fb", "🚵🏻", ":mountain_bicyclist_tone1:"], ["1f6b5-1f3fc", "🚵🏼", ":mountain_bicyclist_tone2:"], ["1f6b5-1f3fd", "🚵🏽", ":mountain_bicyclist_tone3:"], ["1f6b5-1f3fe", "🚵🏾", ":mountain_bicyclist_tone4:"], ["1f6b5-1f3ff", "🚵🏿", ":mountain_bicyclist_tone5:"], ["1f938-1f3fb", "🤸🏻", ":cartwheel_tone1:"], ["1f938-1f3fc", "🤸🏼", ":cartwheel_tone2:"], ["1f938-1f3fd", "🤸🏽", ":cartwheel_tone3:"], ["1f938-1f3fe", "🤸🏾", ":cartwheel_tone4:"], ["1f938-1f3ff", "🤸🏿", ":cartwheel_tone5:"], ["1f93c-1f3fb", "🤼🏻", ":wrestlers_tone1:"], ["1f93c-1f3fc", "🤼🏼", ":wrestlers_tone2:"], ["1f93c-1f3fd", "🤼🏽", ":wrestlers_tone3:"], ["1f93c-1f3fe", "🤼🏾", ":wrestlers_tone4:"], ["1f93c-1f3ff", "🤼🏿", ":wrestlers_tone5:"], ["1f93d-1f3fb", "🤽🏻", ":water_polo_tone1:"], ["1f93d-1f3fc", "🤽🏼", ":water_polo_tone2:"], ["1f93d-1f3fd", "🤽🏽", ":water_polo_tone3:"], ["1f93d-1f3fe", "🤽🏾", ":water_polo_tone4:"], ["1f93d-1f3ff", "🤽🏿", ":water_polo_tone5:"], ["1f93e-1f3fb", "🤾🏻", ":handball_tone1:"], ["1f93e-1f3fc", "🤾🏼", ":handball_tone2:"], ["1f93e-1f3fd", "🤾🏽", ":handball_tone3:"], ["1f93e-1f3fe", "🤾🏾", ":handball_tone4:"], ["1f93e-1f3ff", "🤾🏿", ":handball_tone5:"], ["1f939-1f3fb", "🤹🏻", ":juggling_tone1:"], ["1f939-1f3fc", "🤹🏼", ":juggling_tone2:"], ["1f939-1f3fd", "🤹🏽", ":juggling_tone3:"], ["1f939-1f3fe", "🤹🏾", ":juggling_tone4:"], ["1f939-1f3ff", "🤹🏿", ":juggling_tone5:"], ["1f1ef-1f1f2", "🇯🇲", ":flag_jm:"], ["1f466-1f3fc", "👦🏼", ":boy_tone2:"], ["1f466-1f3fd", "👦🏽", ":boy_tone3:"], ["1f466-1f3fe", "👦🏾", ":boy_tone4:"], ["1f466-1f3ff", "👦🏿", ":boy_tone5:"], ["1f467-1f3fb", "👧🏻", ":girl_tone1:"], ["1f467-1f3fc", "👧🏼", ":girl_tone2:"], ["1f467-1f3fd", "👧🏽", ":girl_tone3:"], ["1f467-1f3fe", "👧🏾", ":girl_tone4:"], ["1f467-1f3ff", "👧🏿", ":girl_tone5:"], ["1f468-1f3fb", "👨🏻", ":man_tone1:"], ["1f468-1f3fc", "👨🏼", ":man_tone2:"], ["1f468-1f3fd", "👨🏽", ":man_tone3:"], ["1f468-1f3fe", "👨🏾", ":man_tone4:"], ["1f468-1f3ff", "👨🏿", ":man_tone5:"], ["1f469-1f3fb", "👩🏻", ":woman_tone1:"], ["1f469-1f3fc", "👩🏼", ":woman_tone2:"], ["1f469-1f3fd", "👩🏽", ":woman_tone3:"], ["1f4aa-1f3fb", "💪🏻", ":muscle_tone1:"], ["1f4aa-1f3fc", "💪🏼", ":muscle_tone2:"], ["1f4aa-1f3fd", "💪🏽", ":muscle_tone3:"], ["1f4aa-1f3fe", "💪🏾", ":muscle_tone4:"], ["1f4aa-1f3ff", "💪🏿", ":muscle_tone5:"], ["1f933-1f3fb", "🤳🏻", ":selfie_tone1:"], ["1f933-1f3fc", "🤳🏼", ":selfie_tone2:"], ["1f933-1f3fd", "🤳🏽", ":selfie_tone3:"], ["1f933-1f3fe", "🤳🏾", ":selfie_tone4:"], ["1f933-1f3ff", "🤳🏿", ":selfie_tone5:"], ["1f448-1f3fb", "👈🏻", ":point_left_tone1:"], ["1f448-1f3fc", "👈🏼", ":point_left_tone2:"], ["1f448-1f3fd", "👈🏽", ":point_left_tone3:"], ["1f448-1f3fe", "👈🏾", ":point_left_tone4:"], ["1f448-1f3ff", "👈🏿", ":point_left_tone5:"], ["1f449-1f3fb", "👉🏻", ":point_right_tone1:"], ["1f449-1f3fc", "👉🏼", ":point_right_tone2:"], ["1f449-1f3fd", "👉🏽", ":point_right_tone3:"], ["1f449-1f3fe", "👉🏾", ":point_right_tone4:"], ["1f449-1f3ff", "👉🏿", ":point_right_tone5:"], ["1f1ee-1f1f4", "🇮🇴", ":flag_io:"], ["1f1ee-1f1f3", "🇮🇳", ":flag_in:"], ["1f1ee-1f1f2", "🇮🇲", ":flag_im:"], ["1f1ee-1f1f1", "🇮🇱", ":flag_il:"], ["1f1ee-1f1ea", "🇮🇪", ":flag_ie:"], ["1f446-1f3fb", "👆🏻", ":point_up_2_tone1:"], ["1f446-1f3fc", "👆🏼", ":point_up_2_tone2:"], ["1f446-1f3fd", "👆🏽", ":point_up_2_tone3:"], ["1f446-1f3fe", "👆🏾", ":point_up_2_tone4:"], ["1f446-1f3ff", "👆🏿", ":point_up_2_tone5:"], ["1f595-1f3fb", "🖕🏻", ":middle_finger_tone1:"], ["1f595-1f3fc", "🖕🏼", ":middle_finger_tone2:"], ["1f595-1f3fd", "🖕🏽", ":middle_finger_tone3:"], ["1f595-1f3fe", "🖕🏾", ":middle_finger_tone4:"], ["1f595-1f3ff", "🖕🏿", ":middle_finger_tone5:"], ["1f447-1f3fb", "👇🏻", ":point_down_tone1:"], ["1f447-1f3fc", "👇🏼", ":point_down_tone2:"], ["1f447-1f3fd", "👇🏽", ":point_down_tone3:"], ["1f447-1f3fe", "👇🏾", ":point_down_tone4:"], ["1f447-1f3ff", "👇🏿", ":point_down_tone5:"], ["1f1ee-1f1e9", "🇮🇩", ":flag_id:"], ["1f1ee-1f1e8", "🇮🇨", ":flag_ic:"], ["1f1ed-1f1fa", "🇭🇺", ":flag_hu:"], ["1f1ed-1f1f9", "🇭🇹", ":flag_ht:"], ["1f1ed-1f1f7", "🇭🇷", ":flag_hr:"], ["1f91e-1f3fb", "🤞🏻", ":fingers_crossed_tone1:"], ["1f91e-1f3fc", "🤞🏼", ":fingers_crossed_tone2:"], ["1f91e-1f3fd", "🤞🏽", ":fingers_crossed_tone3:"], ["1f91e-1f3fe", "🤞🏾", ":fingers_crossed_tone4:"], ["1f91e-1f3ff", "🤞🏿", ":fingers_crossed_tone5:"], ["1f596-1f3fb", "🖖🏻", ":vulcan_tone1:"], ["1f596-1f3fc", "🖖🏼", ":vulcan_tone2:"], ["1f596-1f3fd", "🖖🏽", ":vulcan_tone3:"], ["1f596-1f3fe", "🖖🏾", ":vulcan_tone4:"], ["1f596-1f3ff", "🖖🏿", ":vulcan_tone5:"], ["1f918-1f3fb", "🤘🏻", ":metal_tone1:"], ["1f918-1f3fc", "🤘🏼", ":metal_tone2:"], ["1f918-1f3fd", "🤘🏽", ":metal_tone3:"], ["1f918-1f3fe", "🤘🏾", ":metal_tone4:"], ["1f918-1f3ff", "🤘🏿", ":metal_tone5:"], ["1f919-1f3fb", "🤙🏻", ":call_me_tone1:"], ["1f919-1f3fc", "🤙🏼", ":call_me_tone2:"], ["1f919-1f3fd", "🤙🏽", ":call_me_tone3:"], ["1f919-1f3fe", "🤙🏾", ":call_me_tone4:"], ["1f919-1f3ff", "🤙🏿", ":call_me_tone5:"], ["1f590-1f3fb", "🖐🏻", ":hand_splayed_tone1:"], ["1f590-1f3fc", "🖐🏼", ":hand_splayed_tone2:"], ["1f590-1f3fd", "🖐🏽", ":hand_splayed_tone3:"], ["1f590-1f3fe", "🖐🏾", ":hand_splayed_tone4:"], ["1f590-1f3ff", "🖐🏿", ":hand_splayed_tone5:"], ["1f1ed-1f1f3", "🇭🇳", ":flag_hn:"], ["1f1ed-1f1f2", "🇭🇲", ":flag_hm:"], ["1f1ed-1f1f0", "🇭🇰", ":flag_hk:"], ["1f1ec-1f1fe", "🇬🇾", ":flag_gy:"], ["1f1ec-1f1fc", "🇬🇼", ":flag_gw:"], ["1f44c-1f3fb", "👌🏻", ":ok_hand_tone1:"], ["1f44c-1f3fc", "👌🏼", ":ok_hand_tone2:"], ["1f44c-1f3fd", "👌🏽", ":ok_hand_tone3:"], ["1f44c-1f3fe", "👌🏾", ":ok_hand_tone4:"], ["1f44c-1f3ff", "👌🏿", ":ok_hand_tone5:"], ["1f44d-1f3fb", "👍🏻", ":thumbsup_tone1:"], ["1f44d-1f3fc", "👍🏼", ":thumbsup_tone2:"], ["1f44d-1f3fd", "👍🏽", ":thumbsup_tone3:"], ["1f44d-1f3fe", "👍🏾", ":thumbsup_tone4:"], ["1f44d-1f3ff", "👍🏿", ":thumbsup_tone5:"], ["1f44e-1f3fb", "👎🏻", ":thumbsdown_tone1:"], ["1f466-1f3fb", "👦🏻", ":boy_tone1:"], ["1f44e-1f3fd", "👎🏽", ":thumbsdown_tone3:"], ["1f44e-1f3fe", "👎🏾", ":thumbsdown_tone4:"], ["1f44e-1f3ff", "👎🏿", ":thumbsdown_tone5:"], ["1f1ec-1f1fa", "🇬🇺", ":flag_gu:"], ["1f1ec-1f1f9", "🇬🇹", ":flag_gt:"], ["1f1ec-1f1f8", "🇬🇸", ":flag_gs:"], ["1f1ec-1f1f7", "🇬🇷", ":flag_gr:"], ["1f1ec-1f1f6", "🇬🇶", ":flag_gq:"], ["1f44a-1f3fb", "👊🏻", ":punch_tone1:"], ["1f44a-1f3fc", "👊🏼", ":punch_tone2:"], ["1f44a-1f3fd", "👊🏽", ":punch_tone3:"], ["1f44a-1f3fe", "👊🏾", ":punch_tone4:"], ["1f44a-1f3ff", "👊🏿", ":punch_tone5:"], ["1f91b-1f3fb", "🤛🏻", ":left_facing_fist_tone1:"], ["1f91b-1f3fc", "🤛🏼", ":left_facing_fist_tone2:"], ["1f91b-1f3fd", "🤛🏽", ":left_facing_fist_tone3:"], ["1f91b-1f3fe", "🤛🏾", ":left_facing_fist_tone4:"], ["1f91b-1f3ff", "🤛🏿", ":left_facing_fist_tone5:"], ["1f91c-1f3fb", "🤜🏻", ":right_facing_fist_tone1:"], ["1f91c-1f3fc", "🤜🏼", ":right_facing_fist_tone2:"], ["1f91c-1f3fd", "🤜🏽", ":right_facing_fist_tone3:"], ["1f91c-1f3fe", "🤜🏾", ":right_facing_fist_tone4:"], ["1f91c-1f3ff", "🤜🏿", ":right_facing_fist_tone5:"], ["1f91a-1f3fb", "🤚🏻", ":raised_back_of_hand_tone1:"], ["1f91a-1f3fc", "🤚🏼", ":raised_back_of_hand_tone2:"], ["1f91a-1f3fd", "🤚🏽", ":raised_back_of_hand_tone3:"], ["1f91a-1f3fe", "🤚🏾", ":raised_back_of_hand_tone4:"], ["1f91a-1f3ff", "🤚🏿", ":raised_back_of_hand_tone5:"], ["1f44b-1f3fb", "👋🏻", ":wave_tone1:"], ["1f44b-1f3fc", "👋🏼", ":wave_tone2:"], ["1f44b-1f3fd", "👋🏽", ":wave_tone3:"], ["1f44b-1f3fe", "👋🏾", ":wave_tone4:"], ["1f44b-1f3ff", "👋🏿", ":wave_tone5:"], ["1f44f-1f3fb", "👏🏻", ":clap_tone1:"], ["1f44f-1f3fc", "👏🏼", ":clap_tone2:"], ["1f44f-1f3fd", "👏🏽", ":clap_tone3:"], ["1f44f-1f3fe", "👏🏾", ":clap_tone4:"], ["1f44f-1f3ff", "👏🏿", ":clap_tone5:"], ["1f1ec-1f1f5", "🇬🇵", ":flag_gp:"], ["1f1ec-1f1f3", "🇬🇳", ":flag_gn:"], ["1f1ec-1f1f2", "🇬🇲", ":flag_gm:"], ["1f1ec-1f1f1", "🇬🇱", ":flag_gl:"], ["1f1ec-1f1ee", "🇬🇮", ":flag_gi:"], ["1f450-1f3fb", "👐🏻", ":open_hands_tone1:"], ["1f450-1f3fc", "👐🏼", ":open_hands_tone2:"], ["1f450-1f3fd", "👐🏽", ":open_hands_tone3:"], ["1f450-1f3fe", "👐🏾", ":open_hands_tone4:"], ["1f450-1f3ff", "👐🏿", ":open_hands_tone5:"], ["1f64c-1f3fb", "🙌🏻", ":raised_hands_tone1:"], ["1f64c-1f3fc", "🙌🏼", ":raised_hands_tone2:"], ["1f64c-1f3fd", "🙌🏽", ":raised_hands_tone3:"], ["1f64c-1f3fe", "🙌🏾", ":raised_hands_tone4:"], ["1f64c-1f3ff", "🙌🏿", ":raised_hands_tone5:"], ["1f64f-1f3fb", "🙏🏻", ":pray_tone1:"], ["1f64f-1f3fc", "🙏🏼", ":pray_tone2:"], ["1f64f-1f3fd", "🙏🏽", ":pray_tone3:"], ["1f64f-1f3fe", "🙏🏾", ":pray_tone4:"], ["1f64f-1f3ff", "🙏🏿", ":pray_tone5:"], ["1f91d-1f3fb", "🤝🏻", ":handshake_tone1:"], ["1f91d-1f3fc", "🤝🏼", ":handshake_tone2:"], ["1f91d-1f3fd", "🤝🏽", ":handshake_tone3:"], ["1f91d-1f3fe", "🤝🏾", ":handshake_tone4:"], ["1f91d-1f3ff", "🤝🏿", ":handshake_tone5:"], ["1f485-1f3fb", "💅🏻", ":nail_care_tone1:"], ["1f485-1f3fc", "💅🏼", ":nail_care_tone2:"], ["1f485-1f3fd", "💅🏽", ":nail_care_tone3:"], ["1f485-1f3fe", "💅🏾", ":nail_care_tone4:"], ["1f485-1f3ff", "💅🏿", ":nail_care_tone5:"], ["1f442-1f3fb", "👂🏻", ":ear_tone1:"], ["1f442-1f3fc", "👂🏼", ":ear_tone2:"], ["1f442-1f3fd", "👂🏽", ":ear_tone3:"], ["1f442-1f3fe", "👂🏾", ":ear_tone4:"], ["1f442-1f3ff", "👂🏿", ":ear_tone5:"], ["1f443-1f3fb", "👃🏻", ":nose_tone1:"], ["1f443-1f3fc", "👃🏼", ":nose_tone2:"], ["1f443-1f3fd", "👃🏽", ":nose_tone3:"], ["1f443-1f3fe", "👃🏾", ":nose_tone4:"], ["1f443-1f3ff", "👃🏿", ":nose_tone5:"], ["1f441-1f5e8", "👁🗨", ":eye_in_speech_bubble:"], ["1f1ff-1f1fc", "🇿🇼", ":flag_zw:"], ["1f6c0-1f3fb", "🛀🏻", ":bath_tone1:"], ["1f6c0-1f3fc", "🛀🏼", ":bath_tone2:"], ["1f6c0-1f3fd", "🛀🏽", ":bath_tone3:"], ["1f6c0-1f3fe", "🛀🏾", ":bath_tone4:"], ["1f6c0-1f3ff", "🛀🏿", ":bath_tone5:"], ["1f1ff-1f1f2", "🇿🇲", ":flag_zm:"], ["1f1ff-1f1e6", "🇿🇦", ":flag_za:"], ["1f1fe-1f1f9", "🇾🇹", ":flag_yt:"], ["1f1fe-1f1ea", "🇾🇪", ":flag_ye:"], ["1f1fd-1f1f0", "🇽🇰", ":flag_xk:"], ["1f1fc-1f1f8", "🇼🇸", ":flag_ws:"], ["1f1fc-1f1eb", "🇼🇫", ":flag_wf:"], ["1f1fb-1f1fa", "🇻🇺", ":flag_vu:"], ["1f1fb-1f1f3", "🇻🇳", ":flag_vn:"], ["1f1fb-1f1ee", "🇻🇮", ":flag_vi:"], ["1f1fb-1f1ec", "🇻🇬", ":flag_vg:"], ["1f1fb-1f1ea", "🇻🇪", ":flag_ve:"], ["1f1fb-1f1e8", "🇻🇨", ":flag_vc:"], ["1f1fb-1f1e6", "🇻🇦", ":flag_va:"], ["1f1fa-1f1ff", "🇺🇿", ":flag_uz:"], ["1f1fa-1f1fe", "🇺🇾", ":flag_uy:"], ["1f1fa-1f1f8", "🇺🇸", ":flag_us:"], ["1f1fa-1f1f2", "🇺🇲", ":flag_um:"], ["1f1fa-1f1ec", "🇺🇬", ":flag_ug:"], ["1f1fa-1f1e6", "🇺🇦", ":flag_ua:"], ["1f1f9-1f1ff", "🇹🇿", ":flag_tz:"], ["1f1f9-1f1fc", "🇹🇼", ":flag_tw:"], ["1f1f9-1f1fb", "🇹🇻", ":flag_tv:"], ["1f1f9-1f1f9", "🇹🇹", ":flag_tt:"], ["1f1f9-1f1f7", "🇹🇷", ":flag_tr:"], ["1f1f9-1f1f4", "🇹🇴", ":flag_to:"], ["1f1f9-1f1f3", "🇹🇳", ":flag_tn:"], ["1f1f9-1f1f2", "🇹🇲", ":flag_tm:"], ["1f1f9-1f1f1", "🇹🇱", ":flag_tl:"], ["1f1f9-1f1f0", "🇹🇰", ":flag_tk:"], ["1f1f9-1f1ef", "🇹🇯", ":flag_tj:"], ["1f1f9-1f1ed", "🇹🇭", ":flag_th:"], ["1f1f9-1f1ec", "🇹🇬", ":flag_tg:"], ["1f1f9-1f1eb", "🇹🇫", ":flag_tf:"], ["1f1f9-1f1e9", "🇹🇩", ":flag_td:"], ["1f1f9-1f1e8", "🇹🇨", ":flag_tc:"], ["1f1f9-1f1e6", "🇹🇦", ":flag_ta:"], ["1f1f8-1f1ff", "🇸🇿", ":flag_sz:"], ["1f1f8-1f1fe", "🇸🇾", ":flag_sy:"], ["1f1f8-1f1fd", "🇸🇽", ":flag_sx:"], ["1f1f8-1f1fb", "🇸🇻", ":flag_sv:"], ["1f1f8-1f1f9", "🇸🇹", ":flag_st:"], ["1f1f8-1f1f8", "🇸🇸", ":flag_ss:"], ["1f1f8-1f1f7", "🇸🇷", ":flag_sr:"], ["1f1f8-1f1f4", "🇸🇴", ":flag_so:"], ["1f1f8-1f1f3", "🇸🇳", ":flag_sn:"], ["1f1f8-1f1f2", "🇸🇲", ":flag_sm:"], ["1f1f8-1f1f1", "🇸🇱", ":flag_sl:"], ["1f1f8-1f1f0", "🇸🇰", ":flag_sk:"], ["1f1f8-1f1ef", "🇸🇯", ":flag_sj:"], ["1f1f8-1f1ee", "🇸🇮", ":flag_si:"], ["1f1f8-1f1ed", "🇸🇭", ":flag_sh:"], ["1f1f8-1f1ec", "🇸🇬", ":flag_sg:"], ["1f1f8-1f1ea", "🇸🇪", ":flag_se:"], ["1f1f8-1f1e9", "🇸🇩", ":flag_sd:"], ["1f1f8-1f1e8", "🇸🇨", ":flag_sc:"], ["1f1f8-1f1e7", "🇸🇧", ":flag_sb:"], ["1f1f8-1f1e6", "🇸🇦", ":flag_sa:"], ["1f1f7-1f1fc", "🇷🇼", ":flag_rw:"], ["1f1f7-1f1fa", "🇷🇺", ":flag_ru:"], ["1f1f7-1f1f8", "🇷🇸", ":flag_rs:"], ["1f1f7-1f1f4", "🇷🇴", ":flag_ro:"], ["1f1f7-1f1ea", "🇷🇪", ":flag_re:"], ["1f1f6-1f1e6", "🇶🇦", ":flag_qa:"], ["1f1f5-1f1fe", "🇵🇾", ":flag_py:"], ["1f1f5-1f1fc", "🇵🇼", ":flag_pw:"], ["1f1f5-1f1f9", "🇵🇹", ":flag_pt:"], ["1f1f5-1f1f8", "🇵🇸", ":flag_ps:"], ["1f1f5-1f1f7", "🇵🇷", ":flag_pr:"], ["1f1f5-1f1f3", "🇵🇳", ":flag_pn:"], ["1f1f5-1f1f2", "🇵🇲", ":flag_pm:"], ["1f1f5-1f1f1", "🇵🇱", ":flag_pl:"], ["1f1f5-1f1f0", "🇵🇰", ":flag_pk:"], ["1f1f5-1f1ed", "🇵🇭", ":flag_ph:"], ["1f1f5-1f1ec", "🇵🇬", ":flag_pg:"], ["1f1f5-1f1eb", "🇵🇫", ":flag_pf:"], ["1f1f5-1f1ea", "🇵🇪", ":flag_pe:"], ["1f1f5-1f1e6", "🇵🇦", ":flag_pa:"], ["1f1f4-1f1f2", "🇴🇲", ":flag_om:"], ["1f1f3-1f1ff", "🇳🇿", ":flag_nz:"], ["1f1f3-1f1fa", "🇳🇺", ":flag_nu:"], ["1f1f3-1f1f7", "🇳🇷", ":flag_nr:"], ["1f1f3-1f1f5", "🇳🇵", ":flag_np:"], ["1f1f3-1f1f4", "🇳🇴", ":flag_no:"], ["1f1f3-1f1f1", "🇳🇱", ":flag_nl:"], ["1f1f3-1f1ee", "🇳🇮", ":flag_ni:"], ["1f1f3-1f1ec", "🇳🇬", ":flag_ng:"], ["1f1f3-1f1eb", "🇳🇫", ":flag_nf:"], ["1f1f3-1f1ea", "🇳🇪", ":flag_ne:"], ["1f1f3-1f1e8", "🇳🇨", ":flag_nc:"], ["1f1f3-1f1e6", "🇳🇦", ":flag_na:"], ["1f1f2-1f1ff", "🇲🇿", ":flag_mz:"], ["1f1f2-1f1fe", "🇲🇾", ":flag_my:"], ["1f1f2-1f1fd", "🇲🇽", ":flag_mx:"], ["1f1f2-1f1fc", "🇲🇼", ":flag_mw:"], ["1f1f2-1f1fb", "🇲🇻", ":flag_mv:"], ["1f1f2-1f1fa", "🇲🇺", ":flag_mu:"], ["1f1f2-1f1f9", "🇲🇹", ":flag_mt:"], ["1f1f2-1f1f8", "🇲🇸", ":flag_ms:"], ["1f1f2-1f1f7", "🇲🇷", ":flag_mr:"], ["1f1f2-1f1f6", "🇲🇶", ":flag_mq:"], ["1f1f2-1f1f5", "🇲🇵", ":flag_mp:"], ["1f1f2-1f1f4", "🇲🇴", ":flag_mo:"], ["1f1f2-1f1f3", "🇲🇳", ":flag_mn:"], ["1f1f2-1f1f2", "🇲🇲", ":flag_mm:"], ["1f1f2-1f1f1", "🇲🇱", ":flag_ml:"], ["1f1f2-1f1f0", "🇲🇰", ":flag_mk:"], ["1f1f2-1f1ed", "🇲🇭", ":flag_mh:"], ["1f1f2-1f1ec", "🇲🇬", ":flag_mg:"], ["1f1f2-1f1eb", "🇲🇫", ":flag_mf:"], ["1f1f2-1f1ea", "🇲🇪", ":flag_me:"], ["1f1f2-1f1e9", "🇲🇩", ":flag_md:"], ["1f1f2-1f1e8", "🇲🇨", ":flag_mc:"], ["1f1f2-1f1e6", "🇲🇦", ":flag_ma:"], ["1f1f1-1f1fe", "🇱🇾", ":flag_ly:"], ["1f1f1-1f1fb", "🇱🇻", ":flag_lv:"], ["1f1f1-1f1fa", "🇱🇺", ":flag_lu:"], ["1f1f1-1f1f9", "🇱🇹", ":flag_lt:"], ["1f1f1-1f1f8", "🇱🇸", ":flag_ls:"], ["1f1f1-1f1f7", "🇱🇷", ":flag_lr:"], ["1f1f1-1f1f0", "🇱🇰", ":flag_lk:"], ["1f1f1-1f1ee", "🇱🇮", ":flag_li:"], ["1f1f1-1f1e8", "🇱🇨", ":flag_lc:"], ["1f1f1-1f1e7", "🇱🇧", ":flag_lb:"], ["1f1ec-1f1ed", "🇬🇭", ":flag_gh:"], ["1f1ec-1f1ec", "🇬🇬", ":flag_gg:"], ["1f1ec-1f1eb", "🇬🇫", ":flag_gf:"], ["1f1ec-1f1ea", "🇬🇪", ":flag_ge:"], ["1f1ec-1f1e9", "🇬🇩", ":flag_gd:"], ["1f1ec-1f1e7", "🇬🇧", ":flag_gb:"], ["1f1ec-1f1e6", "🇬🇦", ":flag_ga:"], ["1f1eb-1f1f7", "🇫🇷", ":flag_fr:"], ["1f1eb-1f1f4", "🇫🇴", ":flag_fo:"], ["1f1eb-1f1f2", "🇫🇲", ":flag_fm:"], ["1f1eb-1f1f0", "🇫🇰", ":flag_fk:"], ["1f1eb-1f1ef", "🇫🇯", ":flag_fj:"], ["1f1f1-1f1e6", "🇱🇦", ":flag_la:"], ["1f1f0-1f1ff", "🇰🇿", ":flag_kz:"], ["1f1f0-1f1fe", "🇰🇾", ":flag_ky:"], ["1f1f0-1f1fc", "🇰🇼", ":flag_kw:"], ["1f1f0-1f1f7", "🇰🇷", ":flag_kr:"], ["1f1f0-1f1f5", "🇰🇵", ":flag_kp:"], ["1f1f0-1f1f3", "🇰🇳", ":flag_kn:"], ["1f1f0-1f1f2", "🇰🇲", ":flag_km:"], ["1f1f0-1f1ee", "🇰🇮", ":flag_ki:"], ["1f1f0-1f1ed", "🇰🇭", ":flag_kh:"], ["1f1f0-1f1ec", "🇰🇬", ":flag_kg:"], ["1f1f0-1f1ea", "🇰🇪", ":flag_ke:"], ["1f1ef-1f1f5", "🇯🇵", ":flag_jp:"], ["1f1ef-1f1f4", "🇯🇴", ":flag_jo:"], ["1f3f3-1f308", "🏳🌈", ":rainbow_flag:"], ["1f1e6-1f1e8", "🇦🇨", ":flag_ac:"], ["1f1e6-1f1e9", "🇦🇩", ":flag_ad:"], ["1f1e6-1f1ea", "🇦🇪", ":flag_ae:"], ["1f1e6-1f1eb", "🇦🇫", ":flag_af:"], ["1f1e6-1f1ec", "🇦🇬", ":flag_ag:"], ["1f1e6-1f1ee", "🇦🇮", ":flag_ai:"], ["1f1e6-1f1f1", "🇦🇱", ":flag_al:"], ["1f1e6-1f1f2", "🇦🇲", ":flag_am:"], ["1f1e6-1f1f4", "🇦🇴", ":flag_ao:"], ["1f1e6-1f1f6", "🇦🇶", ":flag_aq:"], ["1f1e6-1f1f7", "🇦🇷", ":flag_ar:"], ["1f1e6-1f1f8", "🇦🇸", ":flag_as:"], ["1f1e6-1f1f9", "🇦🇹", ":flag_at:"], ["1f1e6-1f1fa", "🇦🇺", ":flag_au:"], ["1f1e6-1f1fc", "🇦🇼", ":flag_aw:"], ["1f1e6-1f1fd", "🇦🇽", ":flag_ax:"], ["1f1e6-1f1ff", "🇦🇿", ":flag_az:"], ["1f1e7-1f1e6", "🇧🇦", ":flag_ba:"], ["1f1e7-1f1e7", "🇧🇧", ":flag_bb:"], ["1f1e7-1f1e9", "🇧🇩", ":flag_bd:"], ["1f1e7-1f1ea", "🇧🇪", ":flag_be:"], ["1f1e7-1f1eb", "🇧🇫", ":flag_bf:"], ["1f1e7-1f1ec", "🇧🇬", ":flag_bg:"], ["1f1e7-1f1ed", "🇧🇭", ":flag_bh:"], ["1f1e7-1f1ee", "🇧🇮", ":flag_bi:"], ["1f1e7-1f1ef", "🇧🇯", ":flag_bj:"], ["1f1e7-1f1f1", "🇧🇱", ":flag_bl:"], ["1f1e7-1f1f2", "🇧🇲", ":flag_bm:"], ["1f1e7-1f1f3", "🇧🇳", ":flag_bn:"], ["1f1e7-1f1f4", "🇧🇴", ":flag_bo:"], ["1f1e7-1f1f6", "🇧🇶", ":flag_bq:"], ["1f1e7-1f1f7", "🇧🇷", ":flag_br:"], ["1f1e7-1f1f8", "🇧🇸", ":flag_bs:"], ["1f1e7-1f1f9", "🇧🇹", ":flag_bt:"], ["1f1e7-1f1fb", "🇧🇻", ":flag_bv:"], ["1f1e7-1f1fc", "🇧🇼", ":flag_bw:"], ["1f1e7-1f1fe", "🇧🇾", ":flag_by:"], ["1f1e7-1f1ff", "🇧🇿", ":flag_bz:"], ["1f1e8-1f1e6", "🇨🇦", ":flag_ca:"], ["1f1e8-1f1e8", "🇨🇨", ":flag_cc:"], ["1f1e8-1f1e9", "🇨🇩", ":flag_cd:"], ["1f1e8-1f1eb", "🇨🇫", ":flag_cf:"], ["1f1e8-1f1ec", "🇨🇬", ":flag_cg:"], ["1f1e8-1f1ed", "🇨🇭", ":flag_ch:"], ["1f1e8-1f1ee", "🇨🇮", ":flag_ci:"], ["1f1e8-1f1f0", "🇨🇰", ":flag_ck:"], ["1f1e8-1f1f1", "🇨🇱", ":flag_cl:"], ["1f1e8-1f1f2", "🇨🇲", ":flag_cm:"], ["1f1e8-1f1f3", "🇨🇳", ":flag_cn:"], ["1f1e8-1f1f4", "🇨🇴", ":flag_co:"], ["1f1e8-1f1f5", "🇨🇵", ":flag_cp:"], ["1f1e8-1f1f7", "🇨🇷", ":flag_cr:"], ["1f1e8-1f1fa", "🇨🇺", ":flag_cu:"], ["1f1e8-1f1fb", "🇨🇻", ":flag_cv:"], ["1f1e8-1f1fc", "🇨🇼", ":flag_cw:"], ["1f1e8-1f1fd", "🇨🇽", ":flag_cx:"], ["1f1e8-1f1fe", "🇨🇾", ":flag_cy:"], ["1f1e8-1f1ff", "🇨🇿", ":flag_cz:"], ["1f1e9-1f1ea", "🇩🇪", ":flag_de:"], ["1f1e9-1f1ec", "🇩🇬", ":flag_dg:"], ["1f1e9-1f1ef", "🇩🇯", ":flag_dj:"], ["1f1e9-1f1f0", "🇩🇰", ":flag_dk:"], ["1f1e9-1f1f2", "🇩🇲", ":flag_dm:"], ["1f1e9-1f1f4", "🇩🇴", ":flag_do:"], ["1f1e9-1f1ff", "🇩🇿", ":flag_dz:"], ["1f1ea-1f1e6", "🇪🇦", ":flag_ea:"], ["1f1ea-1f1e8", "🇪🇨", ":flag_ec:"], ["1f1ea-1f1ea", "🇪🇪", ":flag_ee:"], ["1f1ea-1f1ec", "🇪🇬", ":flag_eg:"], ["1f1ea-1f1ed", "🇪🇭", ":flag_eh:"], ["1f1ea-1f1f7", "🇪🇷", ":flag_er:"], ["1f1ea-1f1f8", "🇪🇸", ":flag_es:"], ["1f1ea-1f1f9", "🇪🇹", ":flag_et:"], ["1f1ea-1f1fa", "🇪🇺", ":flag_eu:"], ["1f1eb-1f1ee", "🇫🇮", ":flag_fi:"], ["270c-1f3ff", "✌🏿", ":v_tone5:"], ["270a-1f3fb", "✊🏻", ":fist_tone1:"], ["270d-1f3fe", "✍🏾", ":writing_hand_tone4:"], ["270d-1f3fd", "✍🏽", ":writing_hand_tone3:"], ["270d-1f3fc", "✍🏼", ":writing_hand_tone2:"], ["270d-1f3fb", "✍🏻", ":writing_hand_tone1:"], ["270a-1f3ff", "✊🏿", ":fist_tone5:"], ["270a-1f3fe", "✊🏾", ":fist_tone4:"], ["270a-1f3fd", "✊🏽", ":fist_tone3:"], ["270a-1f3fc", "✊🏼", ":fist_tone2:"], ["270b-1f3ff", "✋🏿", ":raised_hand_tone5:"], ["270b-1f3fe", "✋🏾", ":raised_hand_tone4:"], ["270b-1f3fd", "✋🏽", ":raised_hand_tone3:"], ["270b-1f3fc", "✋🏼", ":raised_hand_tone2:"], ["270b-1f3fb", "✋🏻", ":raised_hand_tone1:"], ["270d-1f3ff", "✍🏿", ":writing_hand_tone5:"], ["270c-1f3fe", "✌🏾", ":v_tone4:"], ["270c-1f3fd", "✌🏽", ":v_tone3:"], ["270c-1f3fc", "✌🏼", ":v_tone2:"], ["270c-1f3fb", "✌🏻", ":v_tone1:"], ["261d-1f3ff", "☝🏿", ":point_up_tone5:"], ["261d-1f3fe", "☝🏾", ":point_up_tone4:"], ["261d-1f3fd", "☝🏽", ":point_up_tone3:"], ["261d-1f3fc", "☝🏼", ":point_up_tone2:"], ["261d-1f3fb", "☝🏻", ":point_up_tone1:"], ["26f9-1f3ff", "⛹🏿", ":basketball_player_tone5:"], ["26f9-1f3fe", "⛹🏾", ":basketball_player_tone4:"], ["26f9-1f3fd", "⛹🏽", ":basketball_player_tone3:"], ["26f9-1f3fc", "⛹🏼", ":basketball_player_tone2:"], ["26f9-1f3fb", "⛹🏻", ":basketball_player_tone1:"], ["0032-20e3", "2⃣", ":two:"], ["0031-20e3", "1⃣", ":one:"], ["0030-20e3", "0⃣", ":zero:"], ["0038-20e3", "8⃣", ":eight:"], ["002a-20e3", "*⃣", ":asterisk:"], ["0023-20e3", "#⃣", ":hash:"], ["0039-20e3", "9⃣", ":nine:"], ["0037-20e3", "7⃣", ":seven:"], ["0036-20e3", "6⃣", ":six:"], ["0035-20e3", "5⃣", ":five:"], ["0034-20e3", "4⃣", ":four:"], ["0033-20e3", "3⃣", ":three:"], ["1f522", "🔢", ":1234:"], ["1f431", "🐱", ":cat:"], ["1f408", "🐈", ":cat2:"], ["1f981", "🦁", ":lion_face:"], ["1f42f", "🐯", ":tiger:"], ["1f405", "🐅", ":tiger2:"], ["1f406", "🐆", ":leopard:"], ["1f434", "🐴", ":horse:"], ["1f40e", "🐎", ":racehorse:"], ["1f98c", "🦌", ":deer:"], ["1f984", "🦄", ":unicorn:"], ["1f42e", "🐮", ":cow:"], ["1f402", "🐂", ":ox:"], ["1f403", "🐃", ":water_buffalo:"], ["1f404", "🐄", ":cow2:"], ["1f437", "🐷", ":pig:"], ["1f416", "🐖", ":pig2:"], ["1f417", "🐗", ":boar:"], ["1f43d", "🐽", ":pig_nose:"], ["1f40f", "🐏", ":ram:"], ["1f411", "🐑", ":sheep:"], ["1f410", "🐐", ":goat:"], ["1f42a", "🐪", ":dromedary_camel:"], ["1f42b", "🐫", ":camel:"], ["1f418", "🐘", ":elephant:"], ["1f98f", "🦏", ":rhino:"], ["1f42d", "🐭", ":mouse:"], ["1f401", "🐁", ":mouse2:"], ["1f400", "🐀", ":rat:"], ["1f439", "🐹", ":hamster:"], ["1f430", "🐰", ":rabbit:"], ["1f407", "🐇", ":rabbit2:"], ["1f43f", "🐿", ":chipmunk:"], ["1f987", "🦇", ":bat:"], ["1f43b", "🐻", ":bear:"], ["1f428", "🐨", ":koala:"], ["1f43c", "🐼", ":panda_face:"], ["1f43e", "🐾", ":feet:"], ["1f983", "🦃", ":turkey:"], ["1f414", "🐔", ":chicken:"], ["1f413", "🐓", ":rooster:"], ["1f423", "🐣", ":hatching_chick:"], ["1f424", "🐤", ":baby_chick:"], ["1f425", "🐥", ":hatched_chick:"], ["1f426", "🐦", ":bird:"], ["1f427", "🐧", ":penguin:"], ["1f54a", "🕊", ":dove:"], ["1f985", "🦅", ":eagle:"], ["1f986", "🦆", ":duck:"], ["1f989", "🦉", ":owl:"], ["1f438", "🐸", ":frog:"], ["1f40a", "🐊", ":crocodile:"], ["1f422", "🐢", ":turtle:"], ["1f98e", "🦎", ":lizard:"], ["1f40d", "🐍", ":snake:"], ["1f432", "🐲", ":dragon_face:"], ["1f409", "🐉", ":dragon:"], ["1f433", "🐳", ":whale:"], ["1f40b", "🐋", ":whale2:"], ["1f42c", "🐬", ":dolphin:"], ["1f41f", "🐟", ":fish:"], ["1f420", "🐠", ":tropical_fish:"], ["1f421", "🐡", ":blowfish:"], ["1f988", "🦈", ":shark:"], ["1f419", "🐙", ":octopus:"], ["1f41a", "🐚", ":shell:"], ["1f980", "🦀", ":crab:"], ["1f990", "🦐", ":shrimp:"], ["1f991", "🦑", ":squid:"], ["1f98b", "🦋", ":butterfly:"], ["1f40c", "🐌", ":snail:"], ["1f41b", "🐛", ":bug:"], ["1f41c", "🐜", ":ant:"], ["1f41d", "🐝", ":bee:"], ["1f41e", "🐞", ":beetle:"], ["1f577", "🕷", ":spider:"], ["1f578", "🕸", ":spider_web:"], ["1f982", "🦂", ":scorpion:"], ["1f490", "💐", ":bouquet:"], ["1f338", "🌸", ":cherry_blossom:"], ["1f4ae", "💮", ":white_flower:"], ["1f3f5", "🏵", ":rosette:"], ["1f339", "🌹", ":rose:"], ["1f940", "🥀", ":wilted_rose:"], ["1f33a", "🌺", ":hibiscus:"], ["1f33b", "🌻", ":sunflower:"], ["1f33c", "🌼", ":blossom:"], ["1f337", "🌷", ":tulip:"], ["1f331", "🌱", ":seedling:"], ["1f332", "🌲", ":evergreen_tree:"], ["1f333", "🌳", ":deciduous_tree:"], ["1f334", "🌴", ":palm_tree:"], ["1f335", "🌵", ":cactus:"], ["1f33e", "🌾", ":ear_of_rice:"], ["1f33f", "🌿", ":herb:"], ["1f1f4", "🇴", ":regional_indicator_o:"], ["1f340", "🍀", ":four_leaf_clover:"], ["1f341", "🍁", ":maple_leaf:"], ["1f342", "🍂", ":fallen_leaf:"], ["1f343", "🍃", ":leaves:"], ["1f347", "🍇", ":grapes:"], ["1f348", "🍈", ":melon:"], ["1f349", "🍉", ":watermelon:"], ["1f34a", "🍊", ":tangerine:"], ["1f34b", "🍋", ":lemon:"], ["1f34c", "🍌", ":banana:"], ["1f34d", "🍍", ":pineapple:"], ["1f34e", "🍎", ":apple:"], ["1f34f", "🍏", ":green_apple:"], ["1f350", "🍐", ":pear:"], ["1f351", "🍑", ":peach:"], ["1f352", "🍒", ":cherries:"], ["1f353", "🍓", ":strawberry:"], ["1f95d", "🥝", ":kiwi:"], ["1f345", "🍅", ":tomato:"], ["1f951", "🥑", ":avocado:"], ["1f346", "🍆", ":eggplant:"], ["1f954", "🥔", ":potato:"], ["1f955", "🥕", ":carrot:"], ["1f33d", "🌽", ":corn:"], ["1f336", "🌶", ":hot_pepper:"], ["1f952", "🥒", ":cucumber:"], ["1f344", "🍄", ":mushroom:"], ["1f95c", "🥜", ":peanuts:"], ["1f330", "🌰", ":chestnut:"], ["1f35e", "🍞", ":bread:"], ["1f950", "🥐", ":croissant:"], ["1f956", "🥖", ":french_bread:"], ["1f95e", "🥞", ":pancakes:"], ["1f9c0", "🧀", ":cheese:"], ["1f356", "🍖", ":meat_on_bone:"], ["1f357", "🍗", ":poultry_leg:"], ["1f953", "🥓", ":bacon:"], ["1f354", "🍔", ":hamburger:"], ["1f35f", "🍟", ":fries:"], ["1f355", "🍕", ":pizza:"], ["1f32d", "🌭", ":hotdog:"], ["1f32e", "🌮", ":taco:"], ["1f32f", "🌯", ":burrito:"], ["1f959", "🥙", ":stuffed_flatbread:"], ["1f95a", "🥚", ":egg:"], ["1f373", "🍳", ":cooking:"], ["1f958", "🥘", ":shallow_pan_of_food:"], ["1f372", "🍲", ":stew:"], ["1f957", "🥗", ":salad:"], ["1f37f", "🍿", ":popcorn:"], ["1f371", "🍱", ":bento:"], ["1f358", "🍘", ":rice_cracker:"], ["1f359", "🍙", ":rice_ball:"], ["1f35a", "🍚", ":rice:"], ["1f35b", "🍛", ":curry:"], ["1f35c", "🍜", ":ramen:"], ["1f35d", "🍝", ":spaghetti:"], ["1f360", "🍠", ":sweet_potato:"], ["1f362", "🍢", ":oden:"], ["1f363", "🍣", ":sushi:"], ["1f364", "🍤", ":fried_shrimp:"], ["1f365", "🍥", ":fish_cake:"], ["1f361", "🍡", ":dango:"], ["1f366", "🍦", ":icecream:"], ["1f367", "🍧", ":shaved_ice:"], ["1f368", "🍨", ":ice_cream:"], ["1f369", "🍩", ":doughnut:"], ["1f36a", "🍪", ":cookie:"], ["1f382", "🎂", ":birthday:"], ["1f370", "🍰", ":cake:"], ["1f36b", "🍫", ":chocolate_bar:"], ["1f36c", "🍬", ":candy:"], ["1f36d", "🍭", ":lollipop:"], ["1f36e", "🍮", ":custard:"], ["1f36f", "🍯", ":honey_pot:"], ["1f37c", "🍼", ":baby_bottle:"], ["1f95b", "🥛", ":milk:"], ["1f1f5", "🇵", ":regional_indicator_p:"], ["1f375", "🍵", ":tea:"], ["1f376", "🍶", ":sake:"], ["1f37e", "🍾", ":champagne:"], ["1f377", "🍷", ":wine_glass:"], ["1f378", "🍸", ":cocktail:"], ["1f379", "🍹", ":tropical_drink:"], ["1f37a", "🍺", ":beer:"], ["1f37b", "🍻", ":beers:"], ["1f942", "🥂", ":champagne_glass:"], ["1f943", "🥃", ":tumbler_glass:"], ["1f37d", "🍽", ":fork_knife_plate:"], ["1f374", "🍴", ":fork_and_knife:"], ["1f944", "🥄", ":spoon:"], ["1f52a", "🔪", ":knife:"], ["1f3fa", "🏺", ":amphora:"], ["1f30d", "🌍", ":earth_africa:"], ["1f30e", "🌎", ":earth_americas:"], ["1f30f", "🌏", ":earth_asia:"], ["1f310", "🌐", ":globe_with_meridians:"], ["1f5fa", "🗺", ":map:"], ["1f5fe", "🗾", ":japan:"], ["1f3d4", "🏔", ":mountain_snow:"], ["1f1f6", "🇶", ":regional_indicator_q:"], ["1f30b", "🌋", ":volcano:"], ["1f5fb", "🗻", ":mount_fuji:"], ["1f3d5", "🏕", ":camping:"], ["1f3d6", "🏖", ":beach:"], ["1f3dc", "🏜", ":desert:"], ["1f3dd", "🏝", ":island:"], ["1f3de", "🏞", ":park:"], ["1f3df", "🏟", ":stadium:"], ["1f3db", "🏛", ":classical_building:"], ["1f3d7", "🏗", ":construction_site:"], ["1f3d8", "🏘", ":homes:"], ["1f3d9", "🏙", ":cityscape:"], ["1f3da", "🏚", ":house_abandoned:"], ["1f3e0", "🏠", ":house:"], ["1f3e1", "🏡", ":house_with_garden:"], ["1f3e2", "🏢", ":office:"], ["1f3e3", "🏣", ":post_office:"], ["1f3e4", "🏤", ":european_post_office:"], ["1f3e5", "🏥", ":hospital:"], ["1f3e6", "🏦", ":bank:"], ["1f3e8", "🏨", ":hotel:"], ["1f3e9", "🏩", ":love_hotel:"], ["1f3ea", "🏪", ":convenience_store:"], ["1f3eb", "🏫", ":school:"], ["1f3ec", "🏬", ":department_store:"], ["1f3ed", "🏭", ":factory:"], ["1f3ef", "🏯", ":japanese_castle:"], ["1f3f0", "🏰", ":european_castle:"], ["1f492", "💒", ":wedding:"], ["1f5fc", "🗼", ":tokyo_tower:"], ["1f5fd", "🗽", ":statue_of_liberty:"], ["1f1f7", "🇷", ":regional_indicator_r:"], ["1f54c", "🕌", ":mosque:"], ["1f54d", "🕍", ":synagogue:"], ["1f1f8", "🇸", ":regional_indicator_s:"], ["1f54b", "🕋", ":kaaba:"], ["1f1f9", "🇹", ":regional_indicator_t:"], ["1f1fa", "🇺", ":regional_indicator_u:"], ["1f301", "🌁", ":foggy:"], ["1f303", "🌃", ":night_with_stars:"], ["1f304", "🌄", ":sunrise_over_mountains:"], ["1f305", "🌅", ":sunrise:"], ["1f306", "🌆", ":city_dusk:"], ["1f307", "🌇", ":city_sunset:"], ["1f309", "🌉", ":bridge_at_night:"], ["1f1fb", "🇻", ":regional_indicator_v:"], ["1f30c", "🌌", ":milky_way:"], ["1f3a0", "🎠", ":carousel_horse:"], ["1f3a1", "🎡", ":ferris_wheel:"], ["1f3a2", "🎢", ":roller_coaster:"], ["1f488", "💈", ":barber:"], ["1f3aa", "🎪", ":circus_tent:"], ["1f3ad", "🎭", ":performing_arts:"], ["1f5bc", "🖼", ":frame_photo:"], ["1f3a8", "🎨", ":art:"], ["1f3b0", "🎰", ":slot_machine:"], ["1f682", "🚂", ":steam_locomotive:"], ["1f683", "🚃", ":railway_car:"], ["1f684", "🚄", ":bullettrain_side:"], ["1f685", "🚅", ":bullettrain_front:"], ["1f686", "🚆", ":train2:"], ["1f687", "🚇", ":metro:"], ["1f688", "🚈", ":light_rail:"], ["1f689", "🚉", ":station:"], ["1f68a", "🚊", ":tram:"], ["1f69d", "🚝", ":monorail:"], ["1f69e", "🚞", ":mountain_railway:"], ["1f68b", "🚋", ":train:"], ["1f68c", "🚌", ":bus:"], ["1f68d", "🚍", ":oncoming_bus:"], ["1f68e", "🚎", ":trolleybus:"], ["1f690", "🚐", ":minibus:"], ["1f691", "🚑", ":ambulance:"], ["1f692", "🚒", ":fire_engine:"], ["1f693", "🚓", ":police_car:"], ["1f694", "🚔", ":oncoming_police_car:"], ["1f695", "🚕", ":taxi:"], ["1f696", "🚖", ":oncoming_taxi:"], ["1f697", "🚗", ":red_car:"], ["1f698", "🚘", ":oncoming_automobile:"], ["1f699", "🚙", ":blue_car:"], ["1f69a", "🚚", ":truck:"], ["1f69b", "🚛", ":articulated_lorry:"], ["1f69c", "🚜", ":tractor:"], ["1f6b2", "🚲", ":bike:"], ["1f6f4", "🛴", ":scooter:"], ["1f6f5", "🛵", ":motor_scooter:"], ["1f68f", "🚏", ":busstop:"], ["1f6e3", "🛣", ":motorway:"], ["1f6e4", "🛤", ":railway_track:"], ["1f1fc", "🇼", ":regional_indicator_w:"], ["1f6a8", "🚨", ":rotating_light:"], ["1f6a5", "🚥", ":traffic_light:"], ["1f6a6", "🚦", ":vertical_traffic_light:"], ["1f6a7", "🚧", ":construction:"], ["1f6d1", "🛑", ":octagonal_sign:"], ["1f1fd", "🇽", ":regional_indicator_x:"], ["1f1fe", "🇾", ":regional_indicator_y:"], ["1f6f6", "🛶", ":canoe:"], ["1f6a4", "🚤", ":speedboat:"], ["1f6f3", "🛳", ":cruise_ship:"], ["1f1ff", "🇿", ":regional_indicator_z:"], ["1f6e5", "🛥", ":motorboat:"], ["1f6a2", "🚢", ":ship:"], ["1f3fb", "🏻", ":tone1:"], ["1f6e9", "🛩", ":airplane_small:"], ["1f6eb", "🛫", ":airplane_departure:"], ["1f6ec", "🛬", ":airplane_arriving:"], ["1f4ba", "💺", ":seat:"], ["1f681", "🚁", ":helicopter:"], ["1f69f", "🚟", ":suspension_railway:"], ["1f6a0", "🚠", ":mountain_cableway:"], ["1f6a1", "🚡", ":aerial_tramway:"], ["1f680", "🚀", ":rocket:"], ["1f6f0", "🛰", ":satellite_orbital:"], ["1f6ce", "🛎", ":bellhop:"], ["1f6aa", "🚪", ":door:"], ["1f6cc", "🛌", ":sleeping_accommodation:"], ["1f6cf", "🛏", ":bed:"], ["1f6cb", "🛋", ":couch:"], ["1f6bd", "🚽", ":toilet:"], ["1f6bf", "🚿", ":shower:"], ["1f6c0", "🛀", ":bath:"], ["1f3fc", "🏼", ":tone2:"], ["1f3fd", "🏽", ":tone3:"], ["1f3fe", "🏾", ":tone4:"], ["1f3ff", "🏿", ":tone5:"], ["1f4aa", "💪", ":muscle:"], ["1f6c1", "🛁", ":bathtub:"], ["1f634", "😴", ":sleeping:"], ["1f47c", "👼", ":angel:"], ["1f60c", "😌", ":relieved:"], ["1f913", "🤓", ":nerd:"], ["1f61b", "😛", ":stuck_out_tongue:"], ["1f933", "🤳", ":selfie:"], ["1f570", "🕰", ":clock:"], ["1f55b", "🕛", ":clock12:"], ["1f567", "🕧", ":clock1230:"], ["1f550", "🕐", ":clock1:"], ["1f55c", "🕜", ":clock130:"], ["1f551", "🕑", ":clock2:"], ["1f55d", "🕝", ":clock230:"], ["1f552", "🕒", ":clock3:"], ["1f55e", "🕞", ":clock330:"], ["1f553", "🕓", ":clock4:"], ["1f55f", "🕟", ":clock430:"], ["1f554", "🕔", ":clock5:"], ["1f560", "🕠", ":clock530:"], ["1f555", "🕕", ":clock6:"], ["1f561", "🕡", ":clock630:"], ["1f556", "🕖", ":clock7:"], ["1f562", "🕢", ":clock730:"], ["1f557", "🕗", ":clock8:"], ["1f563", "🕣", ":clock830:"], ["1f558", "🕘", ":clock9:"], ["1f564", "🕤", ":clock930:"], ["1f559", "🕙", ":clock10:"], ["1f565", "🕥", ":clock1030:"], ["1f55a", "🕚", ":clock11:"], ["1f566", "🕦", ":clock1130:"], ["1f311", "🌑", ":new_moon:"], ["1f312", "🌒", ":waxing_crescent_moon:"], ["1f313", "🌓", ":first_quarter_moon:"], ["1f314", "🌔", ":waxing_gibbous_moon:"], ["1f315", "🌕", ":full_moon:"], ["1f316", "🌖", ":waning_gibbous_moon:"], ["1f317", "🌗", ":last_quarter_moon:"], ["1f318", "🌘", ":waning_crescent_moon:"], ["1f319", "🌙", ":crescent_moon:"], ["1f31a", "🌚", ":new_moon_with_face:"], ["1f31b", "🌛", ":first_quarter_moon_with_face:"], ["1f31c", "🌜", ":last_quarter_moon_with_face:"], ["1f321", "🌡", ":thermometer:"], ["1f926", "🤦", ":face_palm:"], ["1f31d", "🌝", ":full_moon_with_face:"], ["1f31e", "🌞", ":sun_with_face:"], ["1f61c", "😜", ":stuck_out_tongue_winking_eye:"], ["1f31f", "🌟", ":star2:"], ["1f320", "🌠", ":stars:"], ["1f61d", "😝", ":stuck_out_tongue_closed_eyes:"], ["1f46e", "👮", ":cop:"], ["1f924", "🤤", ":drooling_face:"], ["1f324", "🌤", ":white_sun_small_cloud:"], ["1f325", "🌥", ":white_sun_cloud:"], ["1f326", "🌦", ":white_sun_rain_cloud:"], ["1f327", "🌧", ":cloud_rain:"], ["1f328", "🌨", ":cloud_snow:"], ["1f329", "🌩", ":cloud_lightning:"], ["1f32a", "🌪", ":cloud_tornado:"], ["1f32b", "🌫", ":fog:"], ["1f32c", "🌬", ":wind_blowing_face:"], ["1f300", "🌀", ":cyclone:"], ["1f308", "🌈", ":rainbow:"], ["1f302", "🌂", ":closed_umbrella:"], ["1f448", "👈", ":point_left:"], ["1f612", "😒", ":unamused:"], ["1f937", "🤷", ":shrug:"], ["1f613", "😓", ":sweat:"], ["1f614", "😔", ":pensive:"], ["1f615", "😕", ":confused:"], ["1f449", "👉", ":point_right:"], ["1f575", "🕵", ":spy:"], ["1f525", "🔥", ":fire:"], ["1f4a7", "💧", ":droplet:"], ["1f30a", "🌊", ":ocean:"], ["1f383", "🎃", ":jack_o_lantern:"], ["1f384", "🎄", ":christmas_tree:"], ["1f386", "🎆", ":fireworks:"], ["1f387", "🎇", ":sparkler:"], ["1f643", "🙃", ":upside_down:"], ["1f388", "🎈", ":balloon:"], ["1f389", "🎉", ":tada:"], ["1f38a", "🎊", ":confetti_ball:"], ["1f38b", "🎋", ":tanabata_tree:"], ["1f38d", "🎍", ":bamboo:"], ["1f38e", "🎎", ":dolls:"], ["1f38f", "🎏", ":flags:"], ["1f390", "🎐", ":wind_chime:"], ["1f391", "🎑", ":rice_scene:"], ["1f380", "🎀", ":ribbon:"], ["1f381", "🎁", ":gift:"], ["1f397", "🎗", ":reminder_ribbon:"], ["1f39f", "🎟", ":tickets:"], ["1f3ab", "🎫", ":ticket:"], ["1f396", "🎖", ":military_medal:"], ["1f3c6", "🏆", ":trophy:"], ["1f3c5", "🏅", ":medal:"], ["1f947", "🥇", ":first_place:"], ["1f948", "🥈", ":second_place:"], ["1f949", "🥉", ":third_place:"], ["1f486", "💆", ":massage:"], ["1f911", "🤑", ":money_mouth:"], ["1f3c0", "🏀", ":basketball:"], ["1f3d0", "🏐", ":volleyball:"], ["1f3c8", "🏈", ":football:"], ["1f3c9", "🏉", ":rugby_football:"], ["1f3be", "🎾", ":tennis:"], ["1f3b1", "🎱", ":8ball:"], ["1f3b3", "🎳", ":bowling:"], ["1f3cf", "🏏", ":cricket:"], ["1f3d1", "🏑", ":field_hockey:"], ["1f3d2", "🏒", ":hockey:"], ["1f3d3", "🏓", ":ping_pong:"], ["1f3f8", "🏸", ":badminton:"], ["1f94a", "🥊", ":boxing_glove:"], ["1f94b", "🥋", ":martial_arts_uniform:"], ["1f945", "🥅", ":goal:"], ["1f3af", "🎯", ":dart:"], ["1f632", "😲", ":astonished:"], ["1f1ec", "🇬", ":regional_indicator_g:"], ["1f3a3", "🎣", ":fishing_pole_and_fish:"], ["1f3bd", "🎽", ":running_shirt_with_sash:"], ["1f3bf", "🎿", ":ski:"], ["1f3ae", "🎮", ":video_game:"], ["1f579", "🕹", ":joystick:"], ["1f3b2", "🎲", ":game_die:"], ["1f1e8", "🇨", ":regional_indicator_c:"], ["1f641", "🙁", ":slight_frown:"], ["1f482", "💂", ":guardsman:"], ["1f487", "💇", ":haircut:"], ["1f0cf", "🃏", ":black_joker:"], ["1f004", "🀄", ":mahjong:"], ["1f3b4", "🎴", ":flower_playing_cards:"], ["1f507", "🔇", ":mute:"], ["1f508", "🔈", ":speaker:"], ["1f509", "🔉", ":sound:"], ["1f50a", "🔊", ":loud_sound:"], ["1f4e2", "📢", ":loudspeaker:"], ["1f4e3", "📣", ":mega:"], ["1f4ef", "📯", ":postal_horn:"], ["1f514", "🔔", ":bell:"], ["1f515", "🔕", ":no_bell:"], ["1f3bc", "🎼", ":musical_score:"], ["1f3b5", "🎵", ":musical_note:"], ["1f3b6", "🎶", ":notes:"], ["1f399", "🎙", ":microphone2:"], ["1f39a", "🎚", ":level_slider:"], ["1f39b", "🎛", ":control_knobs:"], ["1f3a4", "🎤", ":microphone:"], ["1f3a7", "🎧", ":headphones:"], ["1f4fb", "📻", ":radio:"], ["1f3b7", "🎷", ":saxophone:"], ["1f3b8", "🎸", ":guitar:"], ["1f3b9", "🎹", ":musical_keyboard:"], ["1f3ba", "🎺", ":trumpet:"], ["1f3bb", "🎻", ":violin:"], ["1f941", "🥁", ":drum:"], ["1f4f1", "📱", ":iphone:"], ["1f4f2", "📲", ":calling:"], ["1f616", "😖", ":confounded:"], ["1f4de", "📞", ":telephone_receiver:"], ["1f4df", "📟", ":pager:"], ["1f4e0", "📠", ":fax:"], ["1f50b", "🔋", ":battery:"], ["1f50c", "🔌", ":electric_plug:"], ["1f4bb", "💻", ":computer:"], ["1f5a5", "🖥", ":desktop:"], ["1f5a8", "🖨", ":printer:"], ["1f446", "👆", ":point_up_2:"], ["1f5b1", "🖱", ":mouse_three_button:"], ["1f5b2", "🖲", ":trackball:"], ["1f4bd", "💽", ":minidisc:"], ["1f4be", "💾", ":floppy_disk:"], ["1f4bf", "💿", ":cd:"], ["1f4c0", "📀", ":dvd:"], ["1f3a5", "🎥", ":movie_camera:"], ["1f39e", "🎞", ":film_frames:"], ["1f4fd", "📽", ":projector:"], ["1f3ac", "🎬", ":clapper:"], ["1f4fa", "📺", ":tv:"], ["1f4f7", "📷", ":camera:"], ["1f4f8", "📸", ":camera_with_flash:"], ["1f4f9", "📹", ":video_camera:"], ["1f4fc", "📼", ":vhs:"], ["1f50d", "🔍", ":mag:"], ["1f50e", "🔎", ":mag_right:"], ["1f52c", "🔬", ":microscope:"], ["1f52d", "🔭", ":telescope:"], ["1f4e1", "📡", ":satellite:"], ["1f56f", "🕯", ":candle:"], ["1f4a1", "💡", ":bulb:"], ["1f526", "🔦", ":flashlight:"], ["1f3ee", "🏮", ":izakaya_lantern:"], ["1f4d4", "📔", ":notebook_with_decorative_cover:"], ["1f4d5", "📕", ":closed_book:"], ["1f4d6", "📖", ":book:"], ["1f4d7", "📗", ":green_book:"], ["1f4d8", "📘", ":blue_book:"], ["1f4d9", "📙", ":orange_book:"], ["1f4da", "📚", ":books:"], ["1f4d3", "📓", ":notebook:"], ["1f4d2", "📒", ":ledger:"], ["1f4c3", "📃", ":page_with_curl:"], ["1f4dc", "📜", ":scroll:"], ["1f4c4", "📄", ":page_facing_up:"], ["1f4f0", "📰", ":newspaper:"], ["1f5de", "🗞", ":newspaper2:"], ["1f4d1", "📑", ":bookmark_tabs:"], ["1f516", "🔖", ":bookmark:"], ["1f3f7", "🏷", ":label:"], ["1f4b0", "💰", ":moneybag:"], ["1f4b4", "💴", ":yen:"], ["1f4b5", "💵", ":dollar:"], ["1f4b6", "💶", ":euro:"], ["1f4b7", "💷", ":pound:"], ["1f4b8", "💸", ":money_with_wings:"], ["1f4b3", "💳", ":credit_card:"], ["1f4b9", "💹", ":chart:"], ["1f4b1", "💱", ":currency_exchange:"], ["1f4b2", "💲", ":heavy_dollar_sign:"], ["1f61e", "😞", ":disappointed:"], ["1f4e7", "📧", ":e-mail:"], ["1f4e8", "📨", ":incoming_envelope:"], ["1f4e9", "📩", ":envelope_with_arrow:"], ["1f4e4", "📤", ":outbox_tray:"], ["1f4e5", "📥", ":inbox_tray:"], ["1f4e6", "📦", ":package:"], ["1f4eb", "📫", ":mailbox:"], ["1f4ea", "📪", ":mailbox_closed:"], ["1f4ec", "📬", ":mailbox_with_mail:"], ["1f4ed", "📭", ":mailbox_with_no_mail:"], ["1f4ee", "📮", ":postbox:"], ["1f5f3", "🗳", ":ballot_box:"], ["1f61f", "😟", ":worried:"], ["1f624", "😤", ":triumph:"], ["1f58b", "🖋", ":pen_fountain:"], ["1f58a", "🖊", ":pen_ballpoint:"], ["1f58c", "🖌", ":paintbrush:"], ["1f58d", "🖍", ":crayon:"], ["1f4dd", "📝", ":pencil:"], ["1f4bc", "💼", ":briefcase:"], ["1f4c1", "📁", ":file_folder:"], ["1f4c2", "📂", ":open_file_folder:"], ["1f5c2", "🗂", ":dividers:"], ["1f4c5", "📅", ":date:"], ["1f4c6", "📆", ":calendar:"], ["1f5d2", "🗒", ":notepad_spiral:"], ["1f5d3", "🗓", ":calendar_spiral:"], ["1f4c7", "📇", ":card_index:"], ["1f4c8", "📈", ":chart_with_upwards_trend:"], ["1f4c9", "📉", ":chart_with_downwards_trend:"], ["1f4ca", "📊", ":bar_chart:"], ["1f4cb", "📋", ":clipboard:"], ["1f4cc", "📌", ":pushpin:"], ["1f4cd", "📍", ":round_pushpin:"], ["1f4ce", "📎", ":paperclip:"], ["1f587", "🖇", ":paperclips:"], ["1f4cf", "📏", ":straight_ruler:"], ["1f4d0", "📐", ":triangular_ruler:"], ["1f622", "😢", ":cry:"], ["1f5c3", "🗃", ":card_box:"], ["1f5c4", "🗄", ":file_cabinet:"], ["1f5d1", "🗑", ":wastebasket:"], ["1f512", "🔒", ":lock:"], ["1f513", "🔓", ":unlock:"], ["1f50f", "🔏", ":lock_with_ink_pen:"], ["1f510", "🔐", ":closed_lock_with_key:"], ["1f511", "🔑", ":key:"], ["1f5dd", "🗝", ":key2:"], ["1f528", "🔨", ":hammer:"], ["1f6b6", "🚶", ":walking:"], ["1f595", "🖕", ":middle_finger:"], ["1f6e0", "🛠", ":tools:"], ["1f5e1", "🗡", ":dagger:"], ["1f477", "👷", ":construction_worker:"], ["1f52b", "🔫", ":gun:"], ["1f3f9", "🏹", ":bow_and_arrow:"], ["1f6e1", "🛡", ":shield:"], ["1f527", "🔧", ":wrench:"], ["1f529", "🔩", ":nut_and_bolt:"], ["1f62d", "😭", ":sob:"], ["1f5dc", "🗜", ":compression:"], ["1f626", "😦", ":frowning:"], ["1f627", "😧", ":anguished:"], ["1f517", "🔗", ":link:"], ["1f628", "😨", ":fearful:"], ["1f489", "💉", ":syringe:"], ["1f48a", "💊", ":pill:"], ["1f6ac", "🚬", ":smoking:"], ["1f447", "👇", ":point_down:"], ["1f3c3", "🏃", ":runner:"], ["1f5ff", "🗿", ":moyai:"], ["1f6e2", "🛢", ":oil:"], ["1f52e", "🔮", ":crystal_ball:"], ["1f6d2", "🛒", ":shopping_cart:"], ["1f3e7", "🏧", ":atm:"], ["1f6ae", "🚮", ":put_litter_in_its_place:"], ["1f6b0", "🚰", ":potable_water:"], ["1f629", "😩", ":weary:"], ["1f6b9", "🚹", ":mens:"], ["1f6ba", "🚺", ":womens:"], ["1f6bb", "🚻", ":restroom:"], ["1f6bc", "🚼", ":baby_symbol:"], ["1f6be", "🚾", ":wc:"], ["1f6c2", "🛂", ":passport_control:"], ["1f6c3", "🛃", ":customs:"], ["1f6c4", "🛄", ":baggage_claim:"], ["1f6c5", "🛅", ":left_luggage:"], ["1f473", "👳", ":man_with_turban:"], ["1f6b8", "🚸", ":children_crossing:"], ["1f62c", "😬", ":grimacing:"], ["1f6ab", "🚫", ":no_entry_sign:"], ["1f6b3", "🚳", ":no_bicycles:"], ["1f6ad", "🚭", ":no_smoking:"], ["1f6af", "🚯", ":do_not_litter:"], ["1f6b1", "🚱", ":non-potable_water:"], ["1f6b7", "🚷", ":no_pedestrians:"], ["1f4f5", "📵", ":no_mobile_phones:"], ["1f51e", "🔞", ":underage:"], ["1f630", "😰", ":cold_sweat:"], ["1f1ed", "🇭", ":regional_indicator_h:"], ["1f631", "😱", ":scream:"], ["1f483", "💃", ":dancer:"], ["1f633", "😳", ":flushed:"], ["1f635", "😵", ":dizzy_face:"], ["1f471", "👱", ":person_with_blond_hair:"], ["1f91e", "🤞", ":fingers_crossed:"], ["1f621", "😡", ":rage:"], ["1f620", "😠", ":angry:"], ["1f57a", "🕺", ":man_dancing:"], ["1f607", "😇", ":innocent:"], ["1f920", "🤠", ":cowboy:"], ["1f596", "🖖", ":vulcan:"], ["1f921", "🤡", ":clown:"], ["1f385", "🎅", ":santa:"], ["1f503", "🔃", ":arrows_clockwise:"], ["1f504", "🔄", ":arrows_counterclockwise:"], ["1f519", "🔙", ":back:"], ["1f51a", "🔚", ":end:"], ["1f51b", "🔛", ":on:"], ["1f51c", "🔜", ":soon:"], ["1f51d", "🔝", ":top:"], ["1f6d0", "🛐", ":place_of_worship:"], ["1f925", "🤥", ":lying_face:"], ["1f549", "🕉", ":om_symbol:"], ["1f46f", "👯", ":dancers:"], ["1f574", "🕴", ":levitate:"], ["1f918", "🤘", ":metal:"], ["1f5e3", "🗣", ":speaking_head:"], ["1f464", "👤", ":bust_in_silhouette:"], ["1f465", "👥", ":busts_in_silhouette:"], ["1f93a", "🤺", ":fencer:"], ["1f54e", "🕎", ":menorah:"], ["1f52f", "🔯", ":six_pointed_star:"], ["1f3c7", "🏇", ":horse_racing:"], ["1f919", "🤙", ":call_me:"], ["1f637", "😷", ":mask:"], ["1f912", "🤒", ":thermometer_face:"], ["1f915", "🤕", ":head_bandage:"], ["1f922", "🤢", ":nauseated_face:"], ["1f936", "🤶", ":mrs_claus:"], ["1f590", "🖐", ":hand_splayed:"], ["1f1ea", "🇪", ":regional_indicator_e:"], ["1f3c2", "🏂", ":snowboarder:"], ["1f3cc", "🏌", ":golfer:"], ["1f3c4", "🏄", ":surfer:"], ["1f927", "🤧", ":sneezing_face:"], ["1f500", "🔀", ":twisted_rightwards_arrows:"], ["1f501", "🔁", ":repeat:"], ["1f502", "🔂", ":repeat_one:"], ["1f1ee", "🇮", ":regional_indicator_i:"], ["1f608", "😈", ":smiling_imp:"], ["1f47f", "👿", ":imp:"], ["1f479", "👹", ":japanese_ogre:"], ["1f47a", "👺", ":japanese_goblin:"], ["1f6a3", "🚣", ":rowboat:"], ["1f44c", "👌", ":ok_hand:"], ["1f53c", "🔼", ":arrow_up_small:"], ["1f478", "👸", ":princess:"], ["1f53d", "🔽", ":arrow_down_small:"], ["1f480", "💀", ":skull:"], ["1f1e9", "🇩", ":regional_indicator_d:"], ["1f47b", "👻", ":ghost:"], ["1f47d", "👽", ":alien:"], ["1f44d", "👍", ":thumbsup:"], ["1f3a6", "🎦", ":cinema:"], ["1f505", "🔅", ":low_brightness:"], ["1f506", "🔆", ":high_brightness:"], ["1f4f6", "📶", ":signal_strength:"], ["1f4f3", "📳", ":vibration_mode:"], ["1f4f4", "📴", ":mobile_phone_off:"], ["1f3ca", "🏊", ":swimmer:"], ["1f4db", "📛", ":name_badge:"], ["1f47e", "👾", ":space_invader:"], ["1f530", "🔰", ":beginner:"], ["1f531", "🔱", ":trident:"], ["1f934", "🤴", ":prince:"], ["1f916", "🤖", ":robot:"], ["1f4a9", "💩", ":poop:"], ["1f44e", "👎", ":thumbsdown:"], ["1f63a", "😺", ":smiley_cat:"], ["1f1eb", "🇫", ":regional_indicator_f:"], ["1f638", "😸", ":smile_cat:"], ["1f639", "😹", ":joy_cat:"], ["1f470", "👰", ":bride_with_veil:"], ["1f1ef", "🇯", ":regional_indicator_j:"], ["1f63b", "😻", ":heart_eyes_cat:"], ["1f63c", "😼", ":smirk_cat:"], ["1f3cb", "🏋", ":lifter:"], ["1f63d", "😽", ":kissing_cat:"], ["1f640", "🙀", ":scream_cat:"], ["1f44a", "👊", ":punch:"], ["1f63f", "😿", ":crying_cat_face:"], ["1f935", "🤵", ":man_in_tuxedo:"], ["1f63e", "😾", ":pouting_cat:"], ["1f6b4", "🚴", ":bicyclist:"], ["1f648", "🙈", ":see_no_evil:"], ["1f91b", "🤛", ":left_facing_fist:"], ["1f649", "🙉", ":hear_no_evil:"], ["1f64a", "🙊", ":speak_no_evil:"], ["1f466", "👦", ":boy:"], ["1f930", "🤰", ":pregnant_woman:"], ["1f6b5", "🚵", ":mountain_bicyclist:"], ["1f91c", "🤜", ":right_facing_fist:"], ["1f4af", "💯", ":100:"], ["1f600", "😀", ":grinning:"], ["1f601", "😁", ":grin:"], ["1f602", "😂", ":joy:"], ["1f923", "🤣", ":rofl:"], ["1f91a", "🤚", ":raised_back_of_hand:"], ["1f3ce", "🏎", ":race_car:"], ["1f3cd", "🏍", ":motorcycle:"], ["1f938", "🤸", ":cartwheel:"], ["1f472", "👲", ":man_with_gua_pi_mao:"], ["1f51f", "🔟", ":keycap_ten:"], ["1f520", "🔠", ":capital_abcd:"], ["1f521", "🔡", ":abcd:"], ["1f523", "🔣", ":symbols:"], ["1f524", "🔤", ":abc:"], ["1f170", "🅰", ":a:"], ["1f18e", "🆎", ":ab:"], ["1f171", "🅱", ":b:"], ["1f191", "🆑", ":cl:"], ["1f192", "🆒", ":cool:"], ["1f193", "🆓", ":free:"], ["1f467", "👧", ":girl:"], ["1f194", "🆔", ":id:"], ["1f44b", "👋", ":wave:"], ["1f195", "🆕", ":new:"], ["1f196", "🆖", ":ng:"], ["1f17e", "🅾", ":o2:"], ["1f197", "🆗", ":ok:"], ["1f17f", "🅿", ":parking:"], ["1f198", "🆘", ":sos:"], ["1f199", "🆙", ":up:"], ["1f19a", "🆚", ":vs:"], ["1f201", "🈁", ":koko:"], ["1f202", "🈂", ":sa:"], ["1f237", "🈷", ":u6708:"], ["1f236", "🈶", ":u6709:"], ["1f22f", "🈯", ":u6307:"], ["1f250", "🉐", ":ideograph_advantage:"], ["1f239", "🈹", ":u5272:"], ["1f21a", "🈚", ":u7121:"], ["1f232", "🈲", ":u7981:"], ["1f251", "🉑", ":accept:"], ["1f238", "🈸", ":u7533:"], ["1f234", "🈴", ":u5408:"], ["1f233", "🈳", ":u7a7a:"], ["1f603", "😃", ":smiley:"], ["1f604", "😄", ":smile:"], ["1f23a", "🈺", ":u55b6:"], ["1f235", "🈵", ":u6e80:"], ["1f605", "😅", ":sweat_smile:"], ["1f93c", "🤼", ":wrestlers:"], ["1f606", "😆", ":laughing:"], ["1f44f", "👏", ":clap:"], ["1f64d", "🙍", ":person_frowning:"], ["1f609", "😉", ":wink:"], ["1f468", "👨", ":man:"], ["1f60a", "😊", ":blush:"], ["1f536", "🔶", ":large_orange_diamond:"], ["1f537", "🔷", ":large_blue_diamond:"], ["1f538", "🔸", ":small_orange_diamond:"], ["1f539", "🔹", ":small_blue_diamond:"], ["1f53a", "🔺", ":small_red_triangle:"], ["1f53b", "🔻", ":small_red_triangle_down:"], ["1f4a0", "💠", ":diamond_shape_with_a_dot_inside:"], ["1f518", "🔘", ":radio_button:"], ["1f532", "🔲", ":black_square_button:"], ["1f533", "🔳", ":white_square_button:"], ["1f93d", "🤽", ":water_polo:"], ["1f1f0", "🇰", ":regional_indicator_k:"], ["1f534", "🔴", ":red_circle:"], ["1f535", "🔵", ":blue_circle:"], ["1f3c1", "🏁", ":checkered_flag:"], ["1f6a9", "🚩", ":triangular_flag_on_post:"], ["1f38c", "🎌", ":crossed_flags:"], ["1f3f4", "🏴", ":flag_black:"], ["1f3f3", "🏳", ":flag_white:"], ["1f60b", "😋", ":yum:"], ["1f60e", "😎", ":sunglasses:"], ["1f64e", "🙎", ":person_with_pouting_face:"], ["1f60d", "😍", ":heart_eyes:"], ["1f618", "😘", ":kissing_heart:"], ["1f450", "👐", ":open_hands:"], ["1f93e", "🤾", ":handball:"], ["1f469", "👩", ":woman:"], ["1f617", "😗", ":kissing:"], ["1f619", "😙", ":kissing_smiling_eyes:"], ["1f645", "🙅", ":no_good:"], ["1f64c", "🙌", ":raised_hands:"], ["1f61a", "😚", ":kissing_closed_eyes:"], ["1f939", "🤹", ":juggling:"], ["1f1e7", "🇧", ":regional_indicator_b:"], ["1f642", "🙂", ":slight_smile:"], ["1f474", "👴", ":older_man:"], ["1f64f", "🙏", ":pray:"], ["1f917", "🤗", ":hugging:"], ["1f646", "🙆", ":ok_woman:"], ["1f46b", "👫", ":couple:"], ["1f46c", "👬", ":two_men_holding_hands:"], ["1f46d", "👭", ":two_women_holding_hands:"], ["1f91d", "🤝", ":handshake:"], ["1f48f", "💏", ":couplekiss:"], ["1f914", "🤔", ":thinking:"], ["1f610", "😐", ":neutral_face:"], ["1f491", "💑", ":couple_with_heart:"], ["1f611", "😑", ":expressionless:"], ["1f485", "💅", ":nail_care:"], ["1f636", "😶", ":no_mouth:"], ["1f46a", "👪", ":family:"], ["1f475", "👵", ":older_woman:"], ["1f481", "💁", ":information_desk_person:"], ["1f644", "🙄", ":rolling_eyes:"], ["1f442", "👂", ":ear:"], ["1f60f", "😏", ":smirk:"], ["1f623", "😣", ":persevere:"], ["1f625", "😥", ":disappointed_relieved:"], ["1f62e", "😮", ":open_mouth:"], ["1f64b", "🙋", ":raising_hand:"], ["1f443", "👃", ":nose:"], ["1f476", "👶", ":baby:"], ["1f910", "🤐", ":zipper_mouth:"], ["1f62f", "😯", ":hushed:"], ["1f62a", "😪", ":sleepy:"], ["1f62b", "😫", ":tired_face:"], ["1f463", "👣", ":footprints:"], ["1f440", "👀", ":eyes:"], ["1f441", "👁", ":eye:"], ["1f647", "🙇", ":bow:"], ["1f445", "👅", ":tongue:"], ["1f444", "👄", ":lips:"], ["1f48b", "💋", ":kiss:"], ["1f498", "💘", ":cupid:"], ["1f1f1", "🇱", ":regional_indicator_l:"], ["1f493", "💓", ":heartbeat:"], ["1f494", "💔", ":broken_heart:"], ["1f495", "💕", ":two_hearts:"], ["1f496", "💖", ":sparkling_heart:"], ["1f497", "💗", ":heartpulse:"], ["1f499", "💙", ":blue_heart:"], ["1f49a", "💚", ":green_heart:"], ["1f49b", "💛", ":yellow_heart:"], ["1f49c", "💜", ":purple_heart:"], ["1f5a4", "🖤", ":black_heart:"], ["1f49d", "💝", ":gift_heart:"], ["1f49e", "💞", ":revolving_hearts:"], ["1f49f", "💟", ":heart_decoration:"], ["1f1f2", "🇲", ":regional_indicator_m:"], ["1f48c", "💌", ":love_letter:"], ["1f4a4", "💤", ":zzz:"], ["1f4a2", "💢", ":anger:"], ["1f4a3", "💣", ":bomb:"], ["1f4a5", "💥", ":boom:"], ["1f4a6", "💦", ":sweat_drops:"], ["1f4a8", "💨", ":dash:"], ["1f4ab", "💫", ":dizzy:"], ["1f4ac", "💬", ":speech_balloon:"], ["1f5e8", "🗨", ":speech_left:"], ["1f5ef", "🗯", ":anger_right:"], ["1f4ad", "💭", ":thought_balloon:"], ["1f573", "🕳", ":hole:"], ["1f453", "👓", ":eyeglasses:"], ["1f576", "🕶", ":dark_sunglasses:"], ["1f454", "👔", ":necktie:"], ["1f455", "👕", ":shirt:"], ["1f456", "👖", ":jeans:"], ["1f457", "👗", ":dress:"], ["1f458", "👘", ":kimono:"], ["1f459", "👙", ":bikini:"], ["1f45a", "👚", ":womans_clothes:"], ["1f45b", "👛", ":purse:"], ["1f45c", "👜", ":handbag:"], ["1f45d", "👝", ":pouch:"], ["1f6cd", "🛍", ":shopping_bags:"], ["1f392", "🎒", ":school_satchel:"], ["1f45e", "👞", ":mans_shoe:"], ["1f45f", "👟", ":athletic_shoe:"], ["1f460", "👠", ":high_heel:"], ["1f461", "👡", ":sandal:"], ["1f462", "👢", ":boot:"], ["1f451", "👑", ":crown:"], ["1f452", "👒", ":womans_hat:"], ["1f3a9", "🎩", ":tophat:"], ["1f393", "🎓", ":mortar_board:"], ["1f1f3", "🇳", ":regional_indicator_n:"], ["1f4ff", "📿", ":prayer_beads:"], ["1f484", "💄", ":lipstick:"], ["1f48d", "💍", ":ring:"], ["1f48e", "💎", ":gem:"], ["1f435", "🐵", ":monkey_face:"], ["1f412", "🐒", ":monkey:"], ["1f98d", "🦍", ":gorilla:"], ["1f436", "🐶", ":dog:"], ["1f415", "🐕", ":dog2:"], ["1f429", "🐩", ":poodle:"], ["1f43a", "🐺", ":wolf:"], ["1f98a", "🦊", ":fox:"], ["1f1e6", "🇦", ":regional_indicator_a:"], ["26ab", "⚫", ":black_circle:"], ["2b1c", "⬜", ":white_large_square:"], ["2b1b", "⬛", ":black_large_square:"], ["25fe", "◾", ":black_medium_small_square:"], ["25fd", "◽", ":white_medium_small_square:"], ["25fc", "◼", ":black_medium_square:"], ["25fb", "◻", ":white_medium_square:"], ["25ab", "▫", ":white_small_square:"], ["25aa", "▪", ":black_small_square:"], ["3299", "㊙", ":secret:"], ["3297", "㊗", ":congratulations:"], ["24c2", "Ⓜ", ":m:"], ["2139", "ℹ", ":information_source:"], ["2122", "™", ":tm:"], ["00ae", "®", ":registered:"], ["00a9", "©", ":copyright:"], ["3030", "〰", ":wavy_dash:"], ["2757", "❗", ":exclamation:"], ["2755", "❕", ":grey_exclamation:"], ["2754", "❔", ":grey_question:"], ["2753", "❓", ":question:"], ["2049", "⁉", ":interrobang:"], ["203c", "‼", ":bangbang:"], ["2747", "❇", ":sparkle:"], ["2734", "✴", ":eight_pointed_black_star:"], ["2733", "✳", ":eight_spoked_asterisk:"], ["303d", "〽", ":part_alternation_mark:"], ["27bf", "➿", ":loop:"], ["27b0", "➰", ":curly_loop:"], ["2797", "➗", ":heavy_division_sign:"], ["2796", "➖", ":heavy_minus_sign:"], ["2795", "➕", ":heavy_plus_sign:"], ["274e", "❎", ":negative_squared_cross_mark:"], ["274c", "❌", ":x:"], ["2716", "✖", ":heavy_multiplication_x:"], ["2714", "✔", ":heavy_check_mark:"], ["2611", "☑", ":ballot_box_with_check:"], ["2705", "✅", ":white_check_mark:"], ["2b55", "⭕", ":o:"], ["269c", "⚜", ":fleur-de-lis:"], ["267b", "♻", ":recycle:"], ["23cf", "⏏", ":eject:"], ["23fa", "⏺", ":record_button:"], ["23f9", "⏹", ":stop_button:"], ["23f8", "⏸", ":pause_button:"], ["23ec", "⏬", ":arrow_double_down:"], ["23eb", "⏫", ":arrow_double_up:"], ["23ee", "⏮", ":track_previous:"], ["23ea", "⏪", ":rewind:"], ["25c0", "◀", ":arrow_backward:"], ["23ef", "⏯", ":play_pause:"], ["23ed", "⏭", ":track_next:"], ["23e9", "⏩", ":fast_forward:"], ["25b6", "▶", ":arrow_forward:"], ["26ce", "⛎", ":ophiuchus:"], ["2653", "♓", ":pisces:"], ["2652", "♒", ":aquarius:"], ["2651", "♑", ":capricorn:"], ["2650", "♐", ":sagittarius:"], ["264f", "♏", ":scorpius:"], ["264e", "♎", ":libra:"], ["264d", "♍", ":virgo:"], ["264c", "♌", ":leo:"], ["264b", "♋", ":cancer:"], ["264a", "♊", ":gemini:"], ["2649", "♉", ":taurus:"], ["2648", "♈", ":aries:"], ["262e", "☮", ":peace:"], ["262a", "☪", ":star_and_crescent:"], ["2626", "☦", ":orthodox_cross:"], ["271d", "✝", ":cross:"], ["262f", "☯", ":yin_yang:"], ["2638", "☸", ":wheel_of_dharma:"], ["2721", "✡", ":star_of_david:"], ["269b", "⚛", ":atom:"], ["2935", "⤵", ":arrow_heading_down:"], ["2934", "⤴", ":arrow_heading_up:"], ["21aa", "↪", ":arrow_right_hook:"], ["21a9", "↩", ":leftwards_arrow_with_hook:"], ["2194", "↔", ":left_right_arrow:"], ["2195", "↕", ":arrow_up_down:"], ["26aa", "⚪", ":white_circle:"], ["2b05", "⬅", ":arrow_left:"], ["2199", "↙", ":arrow_lower_left:"], ["2b07", "⬇", ":arrow_down:"], ["2198", "↘", ":arrow_lower_right:"], ["27a1", "➡", ":arrow_right:"], ["2197", "↗", ":arrow_upper_right:"], ["2b06", "⬆", ":arrow_up:"], ["2623", "☣", ":biohazard:"], ["2622", "☢", ":radioactive:"], ["26d4", "⛔", ":no_entry:"], ["26a0", "⚠", ":warning:"], ["267f", "♿", ":wheelchair:"], ["26b1", "⚱", ":urn:"], ["26b0", "⚰", ":coffin:"], ["26d3", "⛓", ":chains:"], ["2696", "⚖", ":scales:"], ["2697", "⚗", ":alembic:"], ["2699", "⚙", ":gear:"], ["2694", "⚔", ":crossed_swords:"], ["2692", "⚒", ":hammer_pick:"], ["26cf", "⛏", ":pick:"], ["2702", "✂", ":scissors:"], ["2712", "✒", ":black_nib:"], ["270f", "✏", ":pencil2:"], ["2709", "✉", ":envelope:"], ["2328", "⌨", ":keyboard:"], ["260e", "☎", ":telephone:"], ["2663", "♣", ":clubs:"], ["2666", "♦", ":diamonds:"], ["2665", "♥", ":hearts:"], ["2660", "♠", ":spades:"], ["26f8", "⛸", ":ice_skate:"], ["26f3", "⛳", ":golf:"], ["26be", "⚾", ":baseball:"], ["26bd", "⚽", ":soccer:"], ["2728", "✨", ":sparkles:"], ["2604", "☄", ":comet:"], ["26c4", "⛄", ":snowman:"], ["2603", "☃", ":snowman2:"], ["2744", "❄", ":snowflake:"], ["26a1", "⚡", ":zap:"], ["26f1", "⛱", ":beach_umbrella:"], ["2614", "☔", ":umbrella:"], ["2602", "☂", ":umbrella2:"], ["26c8", "⛈", ":thunder_cloud_rain:"], ["26c5", "⛅", ":partly_sunny:"], ["2601", "☁", ":cloud:"], ["2b50", "⭐", ":star:"], ["2600", "☀", ":sunny:"], ["23f2", "⏲", ":timer:"], ["23f1", "⏱", ":stopwatch:"], ["23f0", "⏰", ":alarm_clock:"], ["231a", "⌚", ":watch:"], ["23f3", "⏳", ":hourglass_flowing_sand:"], ["231b", "⌛", ":hourglass:"], ["2708", "✈", ":airplane:"], ["26f4", "⛴", ":ferry:"], ["26f5", "⛵", ":sailboat:"], ["2693", "⚓", ":anchor:"], ["26fd", "⛽", ":fuelpump:"], ["2668", "♨", ":hotsprings:"], ["26fa", "⛺", ":tent:"], ["26f2", "⛲", ":fountain:"], ["26e9", "⛩", ":shinto_shrine:"], ["26ea", "⛪", ":church:"], ["26f0", "⛰", ":mountain:"], ["2615", "☕", ":coffee:"], ["2618", "☘", ":shamrock:"], ["26d1", "⛑", ":helmet_with_cross:"], ["2763", "❣", ":heart_exclamation:"], ["2764", "❤", ":heart:"], ["270d", "✍", ":writing_hand:"], ["270a", "✊", ":fist:"], ["270b", "✋", ":raised_hand:"], ["270c", "✌", ":v:"], ["261d", "☝", ":point_up:"], ["26f9", "⛹", ":basketball_player:"], ["26f7", "⛷", ":skier:"], ["2620", "☠", ":skull_crossbones:"], ["2639", "☹", ":frowning2:"], ["263a", "☺", ":relaxed:"], ["2196", "↖", ":arrow_upper_left:"]];

/***/ }),

/***/ 253:
/*!***********************************************************************!*\
  !*** ./node_modules/react-emojione/lib/renderers/unicode-renderer.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _emojiFormatConversion = __webpack_require__(/*! ../utils/emoji-format-conversion */ 64);

var getRenderer = function getRenderer() {
  return function (codepoint) {
    return _emojiFormatConversion.codepointToUnicode.get(codepoint);
  };
};

exports.default = getRenderer;

/***/ }),

/***/ 254:
/*!********************************************************************!*\
  !*** ./node_modules/expression-atlas-feedback/lib/gxaFeedback.css ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(/*! !../../css-loader!./gxaFeedback.css */ 255);
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__(/*! ../../style-loader/lib/addStyles.js */ 17)(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../css-loader/index.js!./gxaFeedback.css", function() {
			var newContent = require("!!../../css-loader/index.js!./gxaFeedback.css");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 255:
/*!**********************************************************************************************!*\
  !*** ./node_modules/css-loader!./node_modules/expression-atlas-feedback/lib/gxaFeedback.css ***!
  \**********************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 16)(undefined);
// imports


// module
exports.push([module.i, "div.gxaFeedbackQuestionBox {\n  margin: 30px;\n  width: 300px;\n  background-color: #b3e0ff;\n  border: 3px solid #008ae6;\n  opacity: 0.6;\n  filter: alpha(opacity=60); /* For IE8 and earlier */\n}\n\n#feedbackBoxCross {\n  margin: 3px;\n  margin-top: 5px;\n  float: right;\n  cursor:pointer;\n}\n\n#feedbackBoxCross:before {\n  color: #BF2222;\n}\n\ndiv.gxaFeedbackQuestionBox p {\n  margin: 2%;\n font-weight: bold;\n text-align: center;\n}\n\ndiv.gxaFeedbackQuestionBox a {\n  float: right;\n  margin-top: 6px;\n  cursor:pointer;\n}\n\ndiv.gxaFeedbackQuestionBoxAnswer {\n  position:relative;\ntext-align: center;\n  margin: 0 auto;\n  margin-bottom: 10px;\n  width: 90%;\n}\n\ndiv.gxaFeedbackQuestionBox button {\n width: auto;\n}\n\n.feedbackBoxTransitionWrapper-leave {\n  opacity: 1;\n}\n\n.feedbackBoxTransitionWrapper-leave.feedbackBoxTransitionWrapper-leave-active {\n  opacity: 0.01;\n  transition: opacity 300ms ease-in;\n}\n\n.gxaSmiley {\n  opacity: 0.6;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmiley:hover {\n  opacity: 0.9;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmileyClicked {\n  opacity: 1;\n}\n\n.gxaSmileyFeedbackBox {\n  text-align: center;\n  clear: both;\n  width: 300px;\n  opacity: 0.8;\n  filter: alpha(opacity=80); /* For IE8 and earlier */\n}\n\n.gxaSmileyRow {\n  text-align: center!important;\n}\n\n.gxaSmileyFeedbackBox p {\n  padding-left: 18px;\n  padding-top: 5px;\n  font-weight: bold;\n  font-size: 14px;\n}\n\n.gxaSmileyFeedbackBox form {\n  padding: 6px;\n  width: 87%;\n}\n\n.gxaSmileyFeedbackBox button {\n  width: 100px;\n  margin-left: 91px;\n}\n\n.form-control {\n  display: block;\n  width: 100%;\n  height: 34px;\n  padding: 6px 12px;\n  font-size: 14px;\n  line-height: 1.42857143;\n  color: #555;\n  background-color: #fff;\n  background-image: none;\n  border: 1px solid #ccc;\n  border-radius: 4px;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;\n       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n}\n.form-control:focus {\n  border-color: #66afe9;\n  outline: 0;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n}\n.form-control::-moz-placeholder {\n  color: #999;\n  opacity: 1;\n}\n.form-control:-ms-input-placeholder {\n  color: #999;\n}\n.form-control::-webkit-input-placeholder {\n  color: #999;\n}\n", ""]);

// exports


/***/ }),

/***/ 64:
/*!**************************************************************************!*\
  !*** ./node_modules/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \**************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.unicodeToCodepoint = exports.codepointToUnicode = exports.shortToCodepoint = exports.codepointToShort = exports.unicodes = undefined;

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _emojiData = __webpack_require__(/*! ../data/emoji-data */ 252);

var _emojiData2 = _interopRequireDefault(_emojiData);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var unicodes = exports.unicodes = [];
var codepointToShort = exports.codepointToShort = new Map();
var shortToCodepoint = exports.shortToCodepoint = new Map();
var codepointToUnicode = exports.codepointToUnicode = new Map();
var unicodeToCodepoint = exports.unicodeToCodepoint = new Map();

_emojiData2.default.forEach(function (_ref) {
    var _ref2 = _slicedToArray(_ref, 3),
        codepoint = _ref2[0],
        unicode = _ref2[1],
        shortname = _ref2[2];

    unicodes.push(unicode);
    codepointToShort.set(codepoint, shortname);
    shortToCodepoint.set(shortname, codepoint);
    codepointToUnicode.set(codepoint, unicode);
    unicodeToCodepoint.set(unicode, codepoint);
});

/***/ })

},[1102]);
//# sourceMappingURL=expressionAtlasBaselineExpression.bundle.js.map