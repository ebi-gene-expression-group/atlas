var expressionAtlasBaselineExpression =
webpackJsonp_name_([2],{

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;

	var _baselineRenderer = __webpack_require__(2279);

	var _baselineRenderer2 = _interopRequireDefault(_baselineRenderer);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.render = _baselineRenderer2.default;

/***/ },

/***/ 2279:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

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

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _reactDom = __webpack_require__(35);

	var _reactDom2 = _interopRequireDefault(_reactDom);

	var _BaselineRouter = __webpack_require__(2280);

	var _BaselineRouter2 = _interopRequireDefault(_BaselineRouter);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	;

/***/ },

/***/ 2280:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _events = __webpack_require__(727);

	var _events2 = _interopRequireDefault(_events);

	var _BaselineFacetsTree = __webpack_require__(2281);

	var _BaselineFacetsTree2 = _interopRequireDefault(_BaselineFacetsTree);

	var _BaselineHeatmaps = __webpack_require__(2284);

	var _BaselineHeatmaps2 = _interopRequireDefault(_BaselineHeatmaps);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

	var UrlManager = __webpack_require__(2405);

	var BaselineRouter = function (_React$Component) {
	    _inherits(BaselineRouter, _React$Component);

	    function BaselineRouter(props) {
	        _classCallCheck(this, BaselineRouter);

	        var _this = _possibleConstructorReturn(this, (BaselineRouter.__proto__ || Object.getPrototypeOf(BaselineRouter)).call(this, props));

	        var anatomogramDataEventEmitter = new _events2.default();
	        anatomogramDataEventEmitter.setMaxListeners(0);

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
	            anatomogramDataEventEmitter: anatomogramDataEventEmitter,
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
	                    { className: 'small-3 columns' },
	                    _react2.default.createElement(_BaselineFacetsTree2.default, {
	                        facets: this.state.facetsTreeData,
	                        setChecked: this.setChecked,
	                        showAnatomograms: this.state.showAnatomograms,
	                        toggleAnatomograms: this.toggleAnatomograms,
	                        disableAnatomogramsCheckbox: !organismPartInQuerySelect
	                    })
	                ),
	                _react2.default.createElement(
	                    'div',
	                    { className: 'small-9 columns' },
	                    _react2.default.createElement(_BaselineHeatmaps2.default, {
	                        atlasUrl: this.props.atlasUrl,
	                        geneQuery: this.props.geneQuery,
	                        conditionQuery: this.props.conditionQuery,
	                        heatmaps: heatmaps,
	                        showAnatomograms: this.state.showAnatomograms,
	                        anatomogramDataEventEmitter: this.state.anatomogramDataEventEmitter
	                    })
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
	    atlasUrl: _react2.default.PropTypes.string.isRequired,
	    /*
	    {
	        "homo sapiens": [{ "name": "ORGANISM_PART", "value": "Organism part"},
	        { "name": "CELL_LINE", "value": "Cell line"}],
	        "macaca mulatta": [{ "name": "ORGANISM_PART", "value": "Organism part"}]
	    }
	     */
	    facetsTreeData: _react2.default.PropTypes.object.isRequired,
	    geneQuery: _react2.default.PropTypes.string.isRequired,
	    conditionQuery: _react2.default.PropTypes.string.isRequired,
	    species: _react2.default.PropTypes.string.isRequired
	};

	exports.default = BaselineRouter;

/***/ },

/***/ 2281:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _Facet = __webpack_require__(2282);

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
	    facets: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.shape({
	        facetName: _react2.default.PropTypes.string.isRequired,
	        facetItems: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.shape({
	            name: _react2.default.PropTypes.string.isRequired,
	            value: _react2.default.PropTypes.string.isRequired,
	            checked: _react2.default.PropTypes.bool.isRequired
	        })).isRequired
	    })).isRequired,
	    setChecked: _react2.default.PropTypes.func.isRequired,
	    showAnatomograms: _react2.default.PropTypes.bool.isRequired,
	    toggleAnatomograms: _react2.default.PropTypes.func.isRequired,
	    disableAnatomogramsCheckbox: _react2.default.PropTypes.bool.isRequired
	};

	exports.default = BaselineFacetsTree;

/***/ },

/***/ 2282:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _FacetItem = __webpack_require__(2283);

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
	    facetName: _react2.default.PropTypes.string.isRequired,
	    facetItems: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.shape({
	        name: _react2.default.PropTypes.string.isRequired,
	        value: _react2.default.PropTypes.string.isRequired,
	        checked: _react2.default.PropTypes.bool.isRequired
	    })).isRequired,
	    setChecked: _react2.default.PropTypes.func.isRequired
	};

	exports.default = Facet;

/***/ },

/***/ 2283:
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var FacetItem = function FacetItem(props) {
	    return _react2.default.createElement(
	        "div",
	        null,
	        _react2.default.createElement("input", { type: "checkbox", checked: props.checked, onChange: function onChange() {
	                return props.setChecked(props.name, !props.checked);
	            } }),
	        _react2.default.createElement(
	            "label",
	            null,
	            props.value
	        )
	    );
	};

	FacetItem.propTypes = {
	    name: _react2.default.PropTypes.string.isRequired,
	    value: _react2.default.PropTypes.string.isRequired,
	    checked: _react2.default.PropTypes.bool.isRequired,
	    setChecked: _react2.default.PropTypes.func.isRequired
	};

	exports.default = FacetItem;

/***/ },

/***/ 2284:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _jquery = __webpack_require__(2285);

	var _jquery2 = _interopRequireDefault(_jquery);

	__webpack_require__(2286);

	var _events = __webpack_require__(727);

	var _events2 = _interopRequireDefault(_events);

	var _BaselineHeatmapWidget = __webpack_require__(2287);

	var _BaselineHeatmapWidget2 = _interopRequireDefault(_BaselineHeatmapWidget);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

	var AtlasFeedback = __webpack_require__(2404);

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

	            var atlasFeedback = _jquery2.default.browser.msie ? null : _react2.default.createElement(AtlasFeedback, {
	                collectionCallback: typeof window.ga === 'function' ? function (score, comment) {
	                    window.ga('send', 'event', 'BaselineHeatmaps', 'feedback', comment, score);
	                } : function () {}
	            });

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
	                        conditionQuery: _this2.props.conditionQuery,
	                        anatomogramDataEventEmitter: _this2.props.anatomogramDataEventEmitter
	                    });
	                }),
	                atlasFeedback
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
	    atlasUrl: _react2.default.PropTypes.string.isRequired,
	    geneQuery: _react2.default.PropTypes.string.isRequired,
	    conditionQuery: _react2.default.PropTypes.string,
	    /*
	     [{"species":"Homo sapiens", "factor":"CELL_LINE"}, {"species":"Mus musculus", "factor":"ORGANISM_PART"}]
	     */
	    showAnatomograms: _react2.default.PropTypes.bool.isRequired,
	    heatmaps: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.shape({
	        species: _react2.default.PropTypes.string.isRequired,
	        factor: _react2.default.PropTypes.shape({
	            name: _react2.default.PropTypes.string.isRequired,
	            value: _react2.default.PropTypes.string.isRequired
	        })
	    })).isRequired,
	    anatomogramDataEventEmitter: _react2.default.PropTypes.instanceOf(_events2.default).isRequired
	};

	exports.default = BaselineHeatmaps;

/***/ },

/***/ 2285:
1094,

/***/ 2286:
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!
	 * jQuery Browser Plugin 0.1.0
	 * https://github.com/gabceb/jquery-browser-plugin
	 *
	 * Original jquery-browser code Copyright 2005, 2015 jQuery Foundation, Inc. and other contributors
	 * http://jquery.org/license
	 *
	 * Modifications Copyright 2015 Gabriel Cebrian
	 * https://github.com/gabceb
	 *
	 * Released under the MIT license
	 *
	 * Date: 05-07-2015
	 */
	/*global window: false */

	(function (factory) {
	  if (true) {
	    // AMD. Register as an anonymous module.
	    !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(2285)], __WEBPACK_AMD_DEFINE_RESULT__ = function ($) {
	      return factory($);
	    }.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	  } else if (typeof module === 'object' && typeof module.exports === 'object') {
	    // Node-like environment
	    module.exports = factory(require('jquery'));
	  } else {
	    // Browser globals
	    factory(window.jQuery);
	  }
	}(function(jQuery) {
	  "use strict";

	  function uaMatch( ua ) {
	    // If an UA is not provided, default to the current browser UA.
	    if ( ua === undefined ) {
	      ua = window.navigator.userAgent;
	    }
	    ua = ua.toLowerCase();

	    var match = /(edge)\/([\w.]+)/.exec( ua ) ||
	        /(opr)[\/]([\w.]+)/.exec( ua ) ||
	        /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
	        /(iemobile)[\/]([\w.]+)/.exec( ua ) ||
	        /(version)(applewebkit)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec( ua ) ||
	        /(webkit)[ \/]([\w.]+).*(version)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec( ua ) ||
	        /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
	        /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
	        /(msie) ([\w.]+)/.exec( ua ) ||
	        ua.indexOf("trident") >= 0 && /(rv)(?::| )([\w.]+)/.exec( ua ) ||
	        ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
	        [];

	    var platform_match = /(ipad)/.exec( ua ) ||
	        /(ipod)/.exec( ua ) ||
	        /(windows phone)/.exec( ua ) ||
	        /(iphone)/.exec( ua ) ||
	        /(kindle)/.exec( ua ) ||
	        /(silk)/.exec( ua ) ||
	        /(android)/.exec( ua ) ||
	        /(win)/.exec( ua ) ||
	        /(mac)/.exec( ua ) ||
	        /(linux)/.exec( ua ) ||
	        /(cros)/.exec( ua ) ||
	        /(playbook)/.exec( ua ) ||
	        /(bb)/.exec( ua ) ||
	        /(blackberry)/.exec( ua ) ||
	        [];

	    var browser = {},
	        matched = {
	          browser: match[ 5 ] || match[ 3 ] || match[ 1 ] || "",
	          version: match[ 2 ] || match[ 4 ] || "0",
	          versionNumber: match[ 4 ] || match[ 2 ] || "0",
	          platform: platform_match[ 0 ] || ""
	        };

	    if ( matched.browser ) {
	      browser[ matched.browser ] = true;
	      browser.version = matched.version;
	      browser.versionNumber = parseInt(matched.versionNumber, 10);
	    }

	    if ( matched.platform ) {
	      browser[ matched.platform ] = true;
	    }

	    // These are all considered mobile platforms, meaning they run a mobile browser
	    if ( browser.android || browser.bb || browser.blackberry || browser.ipad || browser.iphone ||
	      browser.ipod || browser.kindle || browser.playbook || browser.silk || browser[ "windows phone" ]) {
	      browser.mobile = true;
	    }

	    // These are all considered desktop platforms, meaning they run a desktop browser
	    if ( browser.cros || browser.mac || browser.linux || browser.win ) {
	      browser.desktop = true;
	    }

	    // Chrome, Opera 15+ and Safari are webkit based browsers
	    if ( browser.chrome || browser.opr || browser.safari ) {
	      browser.webkit = true;
	    }

	    // IE11 has a new token so we will assign it msie to avoid breaking changes
	    if ( browser.rv || browser.iemobile) {
	      var ie = "msie";

	      matched.browser = ie;
	      browser[ie] = true;
	    }

	    // Edge is officially known as Microsoft Edge, so rewrite the key to match
	    if ( browser.edge ) {
	      delete browser.edge;
	      var msedge = "msedge";

	      matched.browser = msedge;
	      browser[msedge] = true;
	    }

	    // Blackberry browsers are marked as Safari on BlackBerry
	    if ( browser.safari && browser.blackberry ) {
	      var blackberry = "blackberry";

	      matched.browser = blackberry;
	      browser[blackberry] = true;
	    }

	    // Playbook browsers are marked as Safari on Playbook
	    if ( browser.safari && browser.playbook ) {
	      var playbook = "playbook";

	      matched.browser = playbook;
	      browser[playbook] = true;
	    }

	    // BB10 is a newer OS version of BlackBerry
	    if ( browser.bb ) {
	      var bb = "blackberry";

	      matched.browser = bb;
	      browser[bb] = true;
	    }

	    // Opera 15+ are identified as opr
	    if ( browser.opr ) {
	      var opera = "opera";

	      matched.browser = opera;
	      browser[opera] = true;
	    }

	    // Stock Android browsers are marked as Safari on Android.
	    if ( browser.safari && browser.android ) {
	      var android = "android";

	      matched.browser = android;
	      browser[android] = true;
	    }

	    // Kindle browsers are marked as Safari on Kindle
	    if ( browser.safari && browser.kindle ) {
	      var kindle = "kindle";

	      matched.browser = kindle;
	      browser[kindle] = true;
	    }

	     // Kindle Silk browsers are marked as Safari on Kindle
	    if ( browser.safari && browser.silk ) {
	      var silk = "silk";

	      matched.browser = silk;
	      browser[silk] = true;
	    }

	    // Assign the name and platform variable
	    browser.name = matched.browser;
	    browser.platform = matched.platform;
	    return browser;
	  }

	  // Run the matching process, also assign the function to the returned object
	  // for manual, jQuery-free use if desired
	  window.jQBrowser = uaMatch( window.navigator.userAgent );
	  window.jQBrowser.uaMatch = uaMatch;

	  // Only assign to jQuery.browser if jQuery is loaded
	  if ( jQuery ) {
	    jQuery.browser = window.jQBrowser;
	  }

	  return window.jQBrowser;
	}));


/***/ },

/***/ 2287:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _events = __webpack_require__(727);

	var _events2 = _interopRequireDefault(_events);

	var _expressionAtlasHeatmapHighcharts = __webpack_require__(2288);

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
	            showAnatomogram: props.showAnatomogram,
	            anatomogramDataEventEmitter: props.anatomogramDataEventEmitter
	        })
	    );
	};

	BaselineHeatmapWidget.propTypes = {
	    atlasUrl: _react2.default.PropTypes.string.isRequired,
	    geneQuery: _react2.default.PropTypes.string.isRequired,
	    conditionQuery: _react2.default.PropTypes.string.isRequired,
	    species: _react2.default.PropTypes.string.isRequired,
	    factor: _react2.default.PropTypes.shape({
	        name: _react2.default.PropTypes.string.isRequired,
	        value: _react2.default.PropTypes.string.isRequired
	    }).isRequired,
	    showAnatomogram: _react2.default.PropTypes.bool.isRequired,
	    showHeatmapLabel: _react2.default.PropTypes.bool.isRequired,
	    anatomogramDataEventEmitter: _react2.default.PropTypes.instanceOf(_events2.default).isRequired
	};

	exports.default = BaselineHeatmapWidget;

/***/ },

/***/ 2288:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.render = exports.default = undefined;

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _reactDom = __webpack_require__(35);

	var _reactDom2 = _interopRequireDefault(_reactDom);

	var _reactGa = __webpack_require__(173);

	var _reactGa2 = _interopRequireDefault(_reactGa);

	var _urijs = __webpack_require__(193);

	var _urijs2 = _interopRequireDefault(_urijs);

	var _ContainerLoader = __webpack_require__(2289);

	var _ContainerLoader2 = _interopRequireDefault(_ContainerLoader);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	/**
	 * @param {Object}          options
	 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
	 * @param {boolean}         options.disableGoogleAnalytics - Disable Google Analytics
	 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
	 * @param {function}        options.render - Callback to run after each render
	 * @param {boolean}         options.showAnatomogram - optionally hide the anatomogram
	 * @param {boolean}         options.isWidget
	 * @param {string}          options.atlasUrl - Atlas host and path with protocol and port
	 * @param {string}          options.inProxy - Inbound proxy to pull assets from outside your domain
	 * @param {string}          options.outProxy - Outbound proxy for links that take you outside the current domain
	 * @param {string}          options.experiment
	 * @param {Object|string}   options.query - Query object or relative URL endpoint to source data from:
	 *                              e.g. json/experiments/E-PROT-1, /json/genes/ENSG00000005801, /json/genesets/GO:0000001
	 *                                   json/baseline_refexperiment?geneQuery=…, /json/baseline_experiments?geneQuery=…
	 * @param {string}                              options.query.species
	 * @param {{value: string, category: string}[]} options.query.gene
	 * @param {{value: string, category: string}[]} options.query.condition
	 * @param {string}                              options.query.source
	 */
	var DEFAULT_OPTIONS = {
	    showAnatomogram: true,
	    isWidget: true,
	    disableGoogleAnalytics: false,
	    atlasUrl: 'https://www.ebi.ac.uk/gxa/',
	    inProxy: '',
	    outProxy: '',
	    experiment: ''
	};

	var ExpressionAtlasHeatmap = function ExpressionAtlasHeatmap(options) {
	    var parsedQuery = parseQuery(options.query);
	    var sourceUrl = typeof parsedQuery === 'string' ? parsedQuery : (0, _urijs2.default)(resolveEndpoint(options.experiment)).addSearch(parsedQuery);

	    return _react2.default.createElement(_ContainerLoader2.default, _extends({}, DEFAULT_OPTIONS, options, {
	        sourceUrl: sourceUrl.toString()
	    }));
	};

	var render = function render(options) {
	    var _options$disableGoogl = options.disableGoogleAnalytics,
	        disableGoogleAnalytics = _options$disableGoogl === undefined ? false : _options$disableGoogl,
	        _options$render = options.render,
	        render = _options$render === undefined ? function () {} : _options$render,
	        target = options.target;

	    _reactDom2.default.render(_react2.default.createElement(ExpressionAtlasHeatmap, options), typeof target === 'string' ? document.getElementById(target) : target, render);

	    if (!disableGoogleAnalytics) {
	        _reactGa2.default.initialize('UA-37676851-1', {
	            gaOptions: {
	                name: 'atlas-highcharts-widget'
	            }
	        });
	        _reactGa2.default.pageview(window.location.pathname);
	    }
	};

	function resolveEndpoint(experiment) {
	    return !experiment ? 'json/baseline_experiments' : experiment === 'reference' ? 'json/baseline_refexperiment' : 'json/experiments/' + experiment;
	}

	function parseQuery(query) {
	    if (!query) {
	        return null;
	    }

	    if (typeof query === 'string') {
	        return query;
	    }

	    return {
	        geneQuery: stringifyIfNotString(query.gene),
	        conditionQuery: stringifyIfNotString(query.condition),
	        species: stringifyIfNotString(query.species),
	        source: stringifyIfNotString(query.source)
	    };
	}

	function stringifyIfNotString(o) {
	    return typeof o === 'string' ? o : JSON.stringify(o);
	}

	exports.default = ExpressionAtlasHeatmap;
	exports.render = render;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(DEFAULT_OPTIONS, 'DEFAULT_OPTIONS', 'src/Main.js');

	    __REACT_HOT_LOADER__.register(ExpressionAtlasHeatmap, 'ExpressionAtlasHeatmap', 'src/Main.js');

	    __REACT_HOT_LOADER__.register(render, 'render', 'src/Main.js');

	    __REACT_HOT_LOADER__.register(resolveEndpoint, 'resolveEndpoint', 'src/Main.js');

	    __REACT_HOT_LOADER__.register(parseQuery, 'parseQuery', 'src/Main.js');

	    __REACT_HOT_LOADER__.register(stringifyIfNotString, 'stringifyIfNotString', 'src/Main.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_reactDom2, '_reactDom2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_reactGa2, '_reactGa2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_urijs2, '_urijs2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_ContainerLoader2, '_ContainerLoader2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(DEFAULT_OPTIONS, 'DEFAULT_OPTIONS', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(ExpressionAtlasHeatmap, 'ExpressionAtlasHeatmap', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(render, 'render', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(resolveEndpoint, 'resolveEndpoint', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(parseQuery, 'parseQuery', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(stringifyIfNotString, 'stringifyIfNotString', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/Main.js');
	}();

	;

/***/ },

/***/ 2289:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _extends = Object.assign || function (target) {
	  for (var i = 1; i < arguments.length; i++) {
	    var source = arguments[i];for (var key in source) {
	      if (Object.prototype.hasOwnProperty.call(source, key)) {
	        target[key] = source[key];
	      }
	    }
	  }return target;
	};

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _reactRefetch = __webpack_require__(199);

	var _urijs = __webpack_require__(193);

	var _urijs2 = _interopRequireDefault(_urijs);

	var _Container = __webpack_require__(2290);

	var _Container2 = _interopRequireDefault(_Container);

	function _interopRequireDefault(obj) {
	  return obj && obj.__esModule ? obj : { default: obj };
	}

	var Loading = function Loading(_ref) {
	  var spinnerUrl = _ref.spinnerUrl;
	  return _react2.default.createElement('div', null, _react2.default.createElement('img', { src: spinnerUrl }));
	};

	var failAndShowMessage = function failAndShowMessage(_ref2) {
	  var onFailure = _ref2.onFailure,
	      request = _ref2.request,
	      message = _ref2.message;

	  Boolean(onFailure) && onFailure({
	    url: request.url,
	    method: request.method,
	    message: message
	  });

	  return _react2.default.createElement('div', null, _react2.default.createElement('p', null, message));
	};

	var showMessage = function showMessage(message) {
	  return failAndShowMessage({
	    onFailure: function onFailure() {},
	    request: {},
	    message: message
	  });
	};

	var ContainerLoader = function ContainerLoader(props) {
	  var inProxy = props.inProxy,
	      atlasUrl = props.atlasUrl,
	      fail = props.fail,
	      sourceUrlFetch = props.sourceUrlFetch;

	  if (sourceUrlFetch.pending) {

	    return _react2.default.createElement(Loading, { spinnerUrl: inProxy + (0, _urijs2.default)('resources/images/loading.gif', atlasUrl).toString() });
	  } else if (sourceUrlFetch.rejected) {

	    return failAndShowMessage({
	      onFailure: fail,
	      request: sourceUrlFetch.meta.request,
	      message: 'Error: ' + (sourceUrlFetch.reason.message ? sourceUrlFetch.reason.message : 'Unknown cause, please contact arrayexpress-atlas@ebi.ac.uk')
	    });
	  } else if (sourceUrlFetch.fulfilled) {

	    if (sourceUrlFetch.value.error) {
	      return failAndShowMessage({
	        onFailure: fail,
	        request: sourceUrlFetch.meta.request,
	        message: '' + sourceUrlFetch.value.error
	      });
	    } else if (!sourceUrlFetch.value.profiles) {
	      return showMessage('Sorry, no results could be found matching your query.');
	    } else {
	      return _react2.default.createElement(_Container2.default, _extends({}, props, { data: sourceUrlFetch.value }));
	    }
	  }
	};

	ContainerLoader.propTypes = {
	  inProxy: _propTypes2.default.string.isRequired,
	  atlasUrl: _propTypes2.default.string.isRequired,
	  sourceUrl: _propTypes2.default.string.isRequired,
	  fail: _propTypes2.default.func
	};

	var _default = (0, _reactRefetch.connect)(function (props) {
	  return {
	    sourceUrlFetch: props.inProxy + (0, _urijs2.default)(props.sourceUrl, props.atlasUrl).toString()
	  };
	})(ContainerLoader);

	exports.default = _default;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(Loading, 'Loading', 'src/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(failAndShowMessage, 'failAndShowMessage', 'src/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(showMessage, 'showMessage', 'src/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(ContainerLoader, 'ContainerLoader', 'src/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_default, 'default', 'src/layout/ContainerLoader.js');
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_urijs2, '_urijs2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_Container2, '_Container2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(Loading, 'Loading', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(failAndShowMessage, 'failAndShowMessage', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(showMessage, 'showMessage', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(ContainerLoader, 'ContainerLoader', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');

	  __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ContainerLoader.js');
	}();

	;

/***/ },

/***/ 2290:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _urijs = __webpack_require__(193);

	var _urijs2 = _interopRequireDefault(_urijs);

	var _anatomogram = __webpack_require__(2291);

	var _anatomogram2 = _interopRequireDefault(_anatomogram);

	var _ExperimentDescription = __webpack_require__(2349);

	var _ExperimentDescription2 = _interopRequireDefault(_ExperimentDescription);

	var _Footer = __webpack_require__(2350);

	var _Footer2 = _interopRequireDefault(_Footer);

	var _ChartContainer = __webpack_require__(2351);

	var _ChartContainer2 = _interopRequireDefault(_ChartContainer);

	var _jsonPayloadPropTypes = __webpack_require__(2389);

	var _jsonPayloadPropTypes2 = _interopRequireDefault(_jsonPayloadPropTypes);

	var _main = __webpack_require__(2390);

	var _main2 = _interopRequireDefault(_main);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var ChartWithAnatomogram = function ChartWithAnatomogram(_ref) {
	    var data = _ref.data,
	        inProxy = _ref.inProxy,
	        outProxy = _ref.outProxy,
	        atlasUrl = _ref.atlasUrl,
	        showAnatomogram = _ref.showAnatomogram,
	        isWidget = _ref.isWidget;
	    var experiment = data.experiment,
	        columnHeaders = data.columnHeaders,
	        anatomogram = data.anatomogram;

	    var pathToResources = inProxy + (0, _urijs2.default)('resources/js-bundles/', atlasUrl).toString();

	    var chartData = (0, _main2.default)(data, inProxy, outProxy, atlasUrl, pathToResources, isWidget);

	    if (anatomogram && showAnatomogram) {
	        var Wrapped = _anatomogram2.default.wrapComponent({
	            anatomogramData: anatomogram,
	            pathToResources: inProxy + (0, _urijs2.default)('resources/js-bundles/', atlasUrl).toString(),
	            expressedTissueColour: experiment ? 'gray' : 'red',
	            hoveredTissueColour: experiment ? 'red' : 'purple',
	            idsExpressedInExperiment: columnHeaders.map(function (header) {
	                return header.factorValueOntologyTermId;
	            })
	        }, _ChartContainer2.default, { chartData: chartData });
	        return _react2.default.createElement(Wrapped, null);
	    } else {
	        return _react2.default.createElement(_ChartContainer2.default, _extends({ chartData: chartData }, {
	            ontologyIdsToHighlight: [],
	            onOntologyIdIsUnderFocus: function onOntologyIdIsUnderFocus() {}
	        }));
	    }
	};

	ChartWithAnatomogram.propTypes = {
	    inProxy: _propTypes2.default.string.isRequired,
	    outProxy: _propTypes2.default.string.isRequired,
	    atlasUrl: _propTypes2.default.string.isRequired,
	    sourceUrl: _propTypes2.default.string.isRequired,
	    showAnatomogram: _propTypes2.default.bool.isRequired,
	    isWidget: _propTypes2.default.bool.isRequired,
	    data: _jsonPayloadPropTypes2.default.isRequired
	};

	var Container = function Container(props) {
	    var data = props.data,
	        inProxy = props.inProxy,
	        outProxy = props.outProxy,
	        atlasUrl = props.atlasUrl,
	        showAnatomogram = props.showAnatomogram,
	        isWidget = props.isWidget;
	    var _data$config = data.config,
	        geneQuery = _data$config.geneQuery,
	        conditionQuery = _data$config.conditionQuery,
	        species = _data$config.species;

	    var moreInformationUrl = data.experiment ? // single experiment?
	    (0, _urijs2.default)(data.experiment.relUrl, atlasUrl).search('') : (0, _urijs2.default)(atlasUrl).segment('query').search({ geneQuery: geneQuery, conditionQuery: conditionQuery, species: species });

	    return _react2.default.createElement('div', null, isWidget && data.experiment && _react2.default.createElement(_ExperimentDescription2.default, { outProxy: outProxy,
	        experimentUrl: (0, _urijs2.default)(data.experiment.relUrl, atlasUrl).toString(),
	        description: data.experiment.description }), _react2.default.createElement(ChartWithAnatomogram, props), isWidget && _react2.default.createElement(_Footer2.default, { outProxy: outProxy,
	        atlasUrl: atlasUrl,
	        moreInformationUrl: moreInformationUrl.toString() }));
	};

	Container.propTypes = ChartWithAnatomogram.propTypes;

	var _default = Container;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(ChartWithAnatomogram, 'ChartWithAnatomogram', 'src/layout/Container.js');

	    __REACT_HOT_LOADER__.register(Container, 'Container', 'src/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/layout/Container.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_urijs2, '_urijs2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_anatomogram2, '_anatomogram2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_ExperimentDescription2, '_ExperimentDescription2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_Footer2, '_Footer2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_ChartContainer2, '_ChartContainer2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_jsonPayloadPropTypes2, '_jsonPayloadPropTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_main2, '_main2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(ChartWithAnatomogram, 'ChartWithAnatomogram', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(Container, 'Container', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Container.js');
	}();

	;

/***/ },

/***/ 2291:
[2737, 2292],

/***/ 2292:
[2738, 2293, 2297, 2347],

/***/ 2293:
[2739, 2294, 2296],

/***/ 2294:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _reactDom = __webpack_require__(35);

	var _reactDom2 = _interopRequireDefault(_reactDom);

	var _snapSvg = __webpack_require__(2295);

	var _snapSvg2 = _interopRequireDefault(_snapSvg);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } else { return Array.from(arr); } }

	//http://stackoverflow.com/questions/3115982/how-to-check-if-two-arrays-are-equal-with-javascript
	var ArraysEqual = function ArraysEqual(a, b) {
	    if (a === b) return true;
	    if (a == null || b == null) return false;
	    if (a.length != b.length) return false;
	    for (var i = 0; i < a.length; ++i) {
	        if (a[i] !== b[i]) return false;
	    }
	    return true;
	};

	var AnatomogramImageParts = _react2.default.createClass({
	    displayName: 'AnatomogramImageParts',

	    propTypes: {
	        idsExpressedInExperiment: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        idsHeatmapWantsHighlighted: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        idsMousedOver: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        idsNotHighlighted: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        expressedTissueColour: _react2.default.PropTypes.string.isRequired,
	        hoveredTissueColour: _react2.default.PropTypes.string.isRequired,
	        whenMousedOverIdsChange: _react2.default.PropTypes.func
	    },

	    getDefaultProps: function getDefaultProps() {
	        return { whenMousedOverIdsChange: function whenMousedOverIdsChange(nextIds, oldIds) {} };
	    },
	    getInitialState: function getInitialState() {
	        return {
	            toDraw: [].concat(this._idsThatShouldBeStronglyHighlighted(this.props).map(this._highlightStrongly), this.props.idsExpressedInExperiment.map(this._highlightSlightly), this.props.idsNotHighlighted.map(this._highlightAsBackground)) };
	    },
	    render: function render() {
	        return _react2.default.createElement('span', null);
	    },
	    _highlightStrongly: function _highlightStrongly(svgPathId) {
	        return { id: svgPathId, colour: this.props.hoveredTissueColour, opacity: 0.7 };
	    },
	    _highlightSlightly: function _highlightSlightly(svgPathId) {
	        return { id: svgPathId, colour: this.props.expressedTissueColour, opacity: 0.5 };
	    },
	    _highlightAsBackground: function _highlightAsBackground(svgPathId) {
	        return { id: svgPathId, colour: 'gray', opacity: 0.5 };
	    },
	    componentWillUnmount: function componentWillUnmount() {
	        this.props.whenMousedOverIdsChange([], this.props.idsMousedOver);
	    },
	    componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
	        var _this = this;

	        if (!ArraysEqual(nextProps.idsMousedOver, this.props.idsMousedOver)) {
	            this.props.whenMousedOverIdsChange(nextProps.idsMousedOver, this.props.idsMousedOver);
	        }
	        var oldStrong = this._idsThatShouldBeStronglyHighlighted(this.props);
	        var newStrong = this._idsThatShouldBeStronglyHighlighted(nextProps);
	        var oldWeak = this.props.idsExpressedInExperiment;
	        var newWeak = nextProps.idsExpressedInExperiment;

	        var toDraw = [].concat(
	        //ids that heatmap wants highlighted are the most highlighted
	        newStrong.filter(function (id) {
	            return !oldStrong.includes(id);
	        }).map(this._highlightStrongly),
	        //ids that are expressed in the experiment are highlighted with a weaker colour, often the same as background
	        newWeak.filter(function (id) {
	            return !newStrong.includes(id);
	        }).filter(function (id) {
	            return !oldWeak.includes(id);
	        }).map(this._highlightSlightly), nextProps.idsNotHighlighted.filter(function (id) {
	            return !_this.props.idsNotHighlighted.includes(id);
	        }).map(this._highlightAsBackground));

	        this.setState({ toDraw: toDraw });
	    },
	    _idsThatShouldBeStronglyHighlighted: function _idsThatShouldBeStronglyHighlighted(properties) {
	        return properties.idsHeatmapWantsHighlighted.concat(properties.idsMousedOver);
	    }
	});

	var AnatomogramImage = _react2.default.createClass({
	    displayName: 'AnatomogramImage',

	    propTypes: {
	        file: function file(props, propName, componentName) {
	            if (propName === 'file') {
	                if (typeof props[propName] !== 'string') {
	                    return new Error('Expected string to specify file, got: ' + props[propName]);
	                }
	                if (!props[propName]) {
	                    return new Error('Path to file empty!');
	                }
	            }
	            return '';
	        },
	        height: _react2.default.PropTypes.number.isRequired,
	        allSvgPathIds: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        idsExpressedInExperiment: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        idsToBeHighlighted: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.string).isRequired,
	        expressedTissueColour: _react2.default.PropTypes.string.isRequired,
	        hoveredTissueColour: _react2.default.PropTypes.string.isRequired,
	        whenMousedOverIdsChange: _react2.default.PropTypes.func
	    },

	    getInitialState: function getInitialState() {
	        return { mousedOverSvgIds: [] };
	    },
	    componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
	        if (nextProps.file !== this.props.file) {
	            this._loadAnatomogram(nextProps.file);
	        }
	    },
	    componentDidMount: function componentDidMount() {
	        this._loadAnatomogram(this.props.file);
	        this._draw();
	    },
	    componentDidUpdate: function componentDidUpdate() {
	        this._draw();
	    },
	    _draw: function _draw() {
	        var svg = (0, _snapSvg2.default)(_reactDom2.default.findDOMNode(this._anatomogram)).select('#LAYER_EFO');
	        if (svg !== null) {
	            this._drawOnSvg(svg, this._imageParts.state.toDraw);
	            this._imageParts.setState({ toDraw: [] });
	        }
	    },
	    _drawInitialLayout: function _drawInitialLayout(svg) {
	        if (this._imageParts) {
	            this._drawOnSvg(svg, this._imageParts.getInitialState().toDraw);
	            this._imageParts.setState({ toDraw: [] });
	        }
	    },
	    _drawOnSvg: function _drawOnSvg(svg, instructions) {
	        var _this2 = this;

	        instructions.forEach(function (instruction) {
	            _this2._highlightOrganismParts(svg, instruction.id, instruction.colour, instruction.opacity);
	        });
	    },
	    render: function render() {
	        var _this3 = this;

	        var idsExpressedInExperiment = [],
	            idsHoveredOver = [],
	            idsHeatmapWantsHighlighted = [],
	            idsNotHighlighted = [];

	        this.props.allSvgPathIds.forEach(function (id) {
	            if (_this3.state.mousedOverSvgIds.includes(id)) {
	                idsHoveredOver.push(id);
	            } else if (_this3.props.idsToBeHighlighted.includes(id)) {
	                idsHeatmapWantsHighlighted.push(id);
	            } else if (_this3.props.idsExpressedInExperiment.includes(id)) {
	                idsExpressedInExperiment.push(id);
	            } else {
	                idsNotHighlighted.push(id);
	            }
	        });

	        return _react2.default.createElement(
	            'span',
	            null,
	            _react2.default.createElement('svg', { ref: function ref(c) {
	                    return _this3._anatomogram = c;
	                }, style: { display: "table-cell", width: "230px", height: this.props.height + "px" } }),
	            _react2.default.createElement(AnatomogramImageParts, {
	                ref: function ref(c) {
	                    return _this3._imageParts = c;
	                }, key: this.props.file,
	                idsExpressedInExperiment: idsExpressedInExperiment,
	                idsHeatmapWantsHighlighted: idsHeatmapWantsHighlighted,
	                idsMousedOver: idsHoveredOver,
	                idsNotHighlighted: idsNotHighlighted,
	                expressedTissueColour: this.props.expressedTissueColour,
	                hoveredTissueColour: this.props.hoveredTissueColour,
	                whenMousedOverIdsChange: this.props.whenMousedOverIdsChange
	            })
	        );
	    },
	    _highlightPath: function _highlightPath(svgPathId) {
	        this.setState({ hoveredPathId: svgPathId });
	    },
	    _loadAnatomogram: function _loadAnatomogram(svgFile) {
	        var _this4 = this;

	        var svgCanvas = (0, _snapSvg2.default)(_reactDom2.default.findDOMNode(this._anatomogram)),
	            allElements = svgCanvas.selectAll('*');

	        if (allElements) {
	            allElements.remove();
	        }

	        var displayAllOrganismPartsCallback = this._drawInitialLayout;
	        var registerHoverEventsCallback = this._registerHoverEvents;

	        _snapSvg2.default.load(svgFile, function (fragment) {
	            displayAllOrganismPartsCallback(fragment.select('#LAYER_EFO'));
	            registerHoverEventsCallback(fragment.select('#LAYER_EFO'));
	            fragment.selectAll('svg > g').forEach(function (g) {
	                g.transform('S1.6,0,0');
	                svgCanvas.append(g);
	            });
	            var img = fragment.select('#ccLogo');
	            if (img) {
	                // svgCanvas.node.clientHeight and svgCanvas.node.clientWidth is more “correct” but are 0 in Firefox
	                var heightTranslate = Number.parseInt(_this4._anatomogram.style.height) - 15;
	                var widthTranslate = Number.parseInt(_this4._anatomogram.style.width) / 2 - 40;
	                img.transform('t' + widthTranslate + ',' + heightTranslate);
	                svgCanvas.append(img);
	            }
	        });
	    },
	    _registerHoverEvents: function _registerHoverEvents(svg) {
	        var _this5 = this;

	        if (svg) {
	            (function () {
	                // Sometimes svg is null... why?
	                var MaxOverlappingTissues = 5;
	                var mouseoverCallback = function mouseoverCallback(svgPathId) {
	                    _this5.setState(function (previousState) {
	                        return { mousedOverSvgIds: [].concat(_toConsumableArray(previousState.mousedOverSvgIds), [svgPathId]).slice(-MaxOverlappingTissues) };
	                    });
	                };

	                var mouseoutCallback = function mouseoutCallback(svgPathId) {
	                    _this5.setState(function (previousState) {
	                        return { mousedOverSvgIds: previousState.mousedOverSvgIds.map(function (el) {
	                                return el === svgPathId ? '' : el;
	                            }) };
	                    });
	                };

	                var attachCallbacks = function attachCallbacks(svgElement, svgPathId) {
	                    if (svgElement) {
	                        svgElement.mouseover(function () {
	                            mouseoverCallback(svgPathId);
	                        });
	                        svgElement.mouseout(function () {
	                            mouseoutCallback(svgPathId);
	                        });
	                    }
	                };

	                _this5.props.allSvgPathIds.forEach(function (svgPathId) {
	                    var svgElement = svg.select('#' + svgPathId);
	                    attachCallbacks(svgElement, svgPathId);
	                    if (svgElement && svgElement.type === 'use') {
	                        attachCallbacks(svg.select(svgElement.node.getAttribute('xlink:href')), svgPathId);
	                    }
	                });
	            })();
	        }
	    },
	    _highlightOrganismParts: function _highlightOrganismParts(svg, svgPathId, colour, opacity) {
	        var el = svg.select('#' + svgPathId);
	        if (el && el.type === 'use') {
	            this._recursivelyChangeProperties(svg.select(el.node.getAttribute('xlink:href')), colour, opacity);
	        }
	        this._recursivelyChangeProperties(el, colour, opacity);
	    },
	    _recursivelyChangeProperties: function _recursivelyChangeProperties(svgElement, colour, opacity) {
	        var _this6 = this;

	        if (svgElement) {
	            svgElement.selectAll('*').forEach(function (innerElement) {
	                _this6._recursivelyChangeProperties(innerElement);
	            });
	            svgElement.attr({ "fill": colour, "fill-opacity": opacity });
	        }
	    }
	});

	exports.default = AnatomogramImage;

/***/ },

/***/ 2295:
433,

/***/ 2296:
[2744, 2297, 2343],

/***/ 2297:
[2745, 437, 443, 2298, 2299, 2300, 2311],

/***/ 2298:
444,

/***/ 2299:
445,

/***/ 2300:
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./brain_selected.png": 2301,
		"./brain_unselected.png": 2302,
		"./female_selected.png": 2303,
		"./female_unselected.png": 2304,
		"./flower_parts_selected.png": 2305,
		"./flower_parts_unselected.png": 2306,
		"./male_selected.png": 2307,
		"./male_unselected.png": 2308,
		"./whole_plant_selected.png": 2309,
		"./whole_plant_unselected.png": 2310
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 2300;


/***/ },

/***/ 2301:
447,

/***/ 2302:
448,

/***/ 2303:
449,

/***/ 2304:
450,

/***/ 2305:
451,

/***/ 2306:
452,

/***/ 2307:
453,

/***/ 2308:
454,

/***/ 2309:
455,

/***/ 2310:
456,

/***/ 2311:
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./anolis_carolinensis.svg": 2312,
		"./arabidopsis_thaliana_whole_plant.svg": 2313,
		"./brachypodium_distachyon_flower_parts.svg": 2314,
		"./brachypodium_distachyon_whole_plant.svg": 2315,
		"./chicken.svg": 2316,
		"./cow.svg": 2317,
		"./hordeum_vulgare_flower_parts.svg": 2318,
		"./hordeum_vulgare_whole_plant.svg": 2319,
		"./human_brain.svg": 2320,
		"./human_female.svg": 2321,
		"./human_male.svg": 2322,
		"./macaca_mulatta.svg": 2323,
		"./monodelphis_domestica.svg": 2324,
		"./mouse_brain.svg": 2325,
		"./mouse_female.svg": 2326,
		"./mouse_male.svg": 2327,
		"./oryza_sativa_flower_parts.svg": 2328,
		"./oryza_sativa_whole_plant.svg": 2329,
		"./papio_anubis.svg": 2330,
		"./rat.svg": 2331,
		"./solanum_lycopersicum_flower_parts.svg": 2332,
		"./solanum_lycopersicum_whole_plant.svg": 2333,
		"./solanum_tuberosum_whole_plant.svg": 2334,
		"./sorghum_bicolor_flower_parts.svg": 2335,
		"./sorghum_bicolor_whole_plant.svg": 2336,
		"./tetraodon_nigroviridis.svg": 2337,
		"./triticum_aestivum_flower_parts.svg": 2338,
		"./triticum_aestivum_whole_plant.svg": 2339,
		"./xenopus_tropicalis.svg": 2340,
		"./zea_mays_flower_parts.svg": 2341,
		"./zea_mays_whole_plant.svg": 2342
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 2311;


/***/ },

/***/ 2312:
458,

/***/ 2313:
459,

/***/ 2314:
460,

/***/ 2315:
461,

/***/ 2316:
462,

/***/ 2317:
463,

/***/ 2318:
464,

/***/ 2319:
465,

/***/ 2320:
466,

/***/ 2321:
467,

/***/ 2322:
468,

/***/ 2323:
469,

/***/ 2324:
470,

/***/ 2325:
471,

/***/ 2326:
472,

/***/ 2327:
473,

/***/ 2328:
474,

/***/ 2329:
475,

/***/ 2330:
476,

/***/ 2331:
477,

/***/ 2332:
478,

/***/ 2333:
479,

/***/ 2334:
480,

/***/ 2335:
481,

/***/ 2336:
482,

/***/ 2337:
483,

/***/ 2338:
484,

/***/ 2339:
485,

/***/ 2340:
486,

/***/ 2341:
487,

/***/ 2342:
488,

/***/ 2343:
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(2344);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(2346)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./../../../../../node_modules/less-loader/index.js!./SelectionIcon.less", function() {
				var newContent = require("!!./../../css-loader/index.js!./../../../../../node_modules/less-loader/index.js!./SelectionIcon.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2344:
[2748, 2345],

/***/ 2345:
491,

/***/ 2346:
492,

/***/ 2347:
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(2348);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(2346)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./../../../../../node_modules/less-loader/index.js!./ContainerLayout.less", function() {
				var newContent = require("!!./../../css-loader/index.js!./../../../../../node_modules/less-loader/index.js!./ContainerLayout.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2348:
[2749, 2345],

/***/ 2349:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var ExperimentDescription = function ExperimentDescription(props) {
	    return _react2.default.createElement('div', { style: { width: '100%', paddingBottom: '20px' } }, _react2.default.createElement('div', null, _react2.default.createElement('a', { target: '_blank', href: props.outProxy + props.experimentUrl }, props.description)));
	};

	ExperimentDescription.propTypes = {
	    outProxy: _propTypes2.default.string.isRequired,
	    experimentUrl: _propTypes2.default.string.isRequired,
	    description: _propTypes2.default.string.isRequired
	};

	var _default = ExperimentDescription;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(ExperimentDescription, 'ExperimentDescription', 'src/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/layout/ExperimentDescription.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(ExperimentDescription, 'ExperimentDescription', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/ExperimentDescription.js');
	}();

	;

/***/ },

/***/ 2350:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var Footer = function Footer(props) {
	    return _react2.default.createElement('div', { style: { clear: 'both', paddingTop: '40px' } }, _react2.default.createElement('a', { href: props.outProxy + props.moreInformationUrl }, ' See more expression data at Expression Atlas.'), _react2.default.createElement('br', null), 'This expression view is provided by ', _react2.default.createElement('a', { href: props.outProxy + props.atlasUrl }, 'Expression Atlas'), '.', _react2.default.createElement('br', null), 'Please send any queries or feedback to ', _react2.default.createElement('a', { href: 'mailto:arrayexpress-atlas@ebi.ac.uk' }, 'arrayexpress-atlas@ebi.ac.uk'), '.');
	};

	Footer.propTypes = {
	    outProxy: _propTypes2.default.string.isRequired,
	    atlasUrl: _propTypes2.default.string.isRequired,
	    moreInformationUrl: _propTypes2.default.string.isRequired
	};

	var _default = Footer;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(Footer, 'Footer', 'src/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/layout/Footer.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(Footer, 'Footer', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/Footer.js');
	}();

	;

/***/ },

/***/ 2351:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _uncontrollable = __webpack_require__(498);

	var _uncontrollable2 = _interopRequireDefault(_uncontrollable);

	var _HeatmapWithControls = __webpack_require__(2352);

	var _HeatmapWithControls2 = _interopRequireDefault(_HeatmapWithControls);

	var _BoxplotCanvas = __webpack_require__(2387);

	var _BoxplotCanvas2 = _interopRequireDefault(_BoxplotCanvas);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	var Heatmap = (0, _uncontrollable2.default)(_HeatmapWithControls2.default, {
	    selectedGenomeBrowser: 'onSelectGenomeBrowser',
	    selectedOrderingName: 'onSelectOrdering',
	    selectedFilters: 'onSelectFilters',
	    coexpressionsShown: 'onCoexpressionOptionChange',
	    zoom: 'onZoom'
	});
	//starting values on component creation, managed by uncontrollable later
	var heatmapDefaults = function heatmapDefaults(_ref) {
	    var orderings = _ref.orderings,
	        expressionLevelFilters = _ref.expressionLevelFilters,
	        groupingFilters = _ref.groupingFilters,
	        heatmapConfig = _ref.heatmapConfig;
	    return {
	        defaultSelectedGenomeBrowser: 'none',
	        defaultSelectedOrderingName: orderings.default.name,
	        defaultSelectedFilters: [expressionLevelFilters].concat(_toConsumableArray(groupingFilters)).map(function (filter) {
	            return {
	                name: filter.name,
	                valueNames: filter.values.filter(function (fv) {
	                    return !fv.disabled;
	                }).map(function (fv) {
	                    return fv.name;
	                }) // Deep copy from props
	            };
	        }),
	        defaultCoexpressionsShown: 0,
	        defaultZoom: false
	    };
	};

	var ChartContainer = function (_React$Component) {
	    _inherits(ChartContainer, _React$Component);

	    function ChartContainer(props) {
	        _classCallCheck(this, ChartContainer);

	        var _this = _possibleConstructorReturn(this, (ChartContainer.__proto__ || Object.getPrototypeOf(ChartContainer)).call(this, props));

	        _this.state = {
	            chartType: 'heatmap'
	        };

	        _this.handleClick = _this._handleClick.bind(_this);
	        return _this;
	    }

	    _createClass(ChartContainer, [{
	        key: '_theOtherChartType',
	        value: function _theOtherChartType() {
	            return this.state.chartType === 'heatmap' ? 'boxplot' : 'heatmap';
	        }
	    }, {
	        key: '_handleClick',
	        value: function _handleClick(e) {
	            e.preventDefault();
	            this.setState({ chartType: this._theOtherChartType() });
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            return _react2.default.createElement('div', null, this.props.chartData.boxplotData && _react2.default.createElement('a', { href: '#', onClick: this.handleClick }, 'Switch to ' + this._theOtherChartType() + ' view'), _react2.default.createElement('div', { className: this.state.chartType === 'heatmap' ? '' : 'hidden' }, _react2.default.createElement(Heatmap, _extends({
	                ontologyIdsToHighlight: this.props.ontologyIdsToHighlight,
	                onOntologyIdIsUnderFocus: this.props.onOntologyIdIsUnderFocus
	            }, this.props.chartData, heatmapDefaults(this.props.chartData)))), this.props.chartData.boxplotData && _react2.default.createElement('div', { className: this.state.chartType === 'boxplot' ? '' : 'hidden' }, _react2.default.createElement(_BoxplotCanvas2.default, this.props.chartData.boxplotData)));
	        }
	    }]);

	    return ChartContainer;
	}(_react2.default.Component);

	ChartContainer.propTypes = {
	    chartData: _chartDataPropTypes.chartDataPropTypes.isRequired,
	    ontologyIdsToHighlight: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    onOntologyIdIsUnderFocus: _propTypes2.default.func.isRequired
	};

	var _default = ChartContainer;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(Heatmap, 'Heatmap', 'src/manipulate/ChartContainer.js');

	    __REACT_HOT_LOADER__.register(heatmapDefaults, 'heatmapDefaults', 'src/manipulate/ChartContainer.js');

	    __REACT_HOT_LOADER__.register(ChartContainer, 'ChartContainer', 'src/manipulate/ChartContainer.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/ChartContainer.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, "_extends", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_uncontrollable2, "_uncontrollable2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_HeatmapWithControls2, "_HeatmapWithControls2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_BoxplotCanvas2, "_BoxplotCanvas2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_toConsumableArray, "_toConsumableArray", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(Heatmap, "Heatmap", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(heatmapDefaults, "heatmapDefaults", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(ChartContainer, "ChartContainer", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/ChartContainer.js");
	}();

	;

/***/ },

/***/ 2352:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _GenomeBrowsersDropdown = __webpack_require__(2353);

	var _GenomeBrowsersDropdown2 = _interopRequireDefault(_GenomeBrowsersDropdown);

	var _OrderingsDropdown = __webpack_require__(2354);

	var _OrderingsDropdown2 = _interopRequireDefault(_OrderingsDropdown);

	var _FiltersModal = __webpack_require__(2355);

	var _FiltersModal2 = _interopRequireDefault(_FiltersModal);

	var _DownloadButton = __webpack_require__(2362);

	var _DownloadButton2 = _interopRequireDefault(_DownloadButton);

	var _HeatmapCanvas = __webpack_require__(2365);

	var _HeatmapCanvas2 = _interopRequireDefault(_HeatmapCanvas);

	var _heatmapCellTooltipFormatter = __webpack_require__(2367);

	var _heatmapCellTooltipFormatter2 = _interopRequireDefault(_heatmapCellTooltipFormatter);

	var _axesFormatters2 = __webpack_require__(2371);

	var _axesFormatters3 = _interopRequireDefault(_axesFormatters2);

	var _Main = __webpack_require__(2372);

	var _CoexpressionOption = __webpack_require__(2380);

	var _CoexpressionOption2 = _interopRequireDefault(_CoexpressionOption);

	var _Events = __webpack_require__(2385);

	var _Events2 = _interopRequireDefault(_Events);

	var _Manipulators = __webpack_require__(2386);

	var _lodash = __webpack_require__(434);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var HeatmapWithControls = function (_React$Component) {
	    _inherits(HeatmapWithControls, _React$Component);

	    function HeatmapWithControls(props) {
	        _classCallCheck(this, HeatmapWithControls);

	        return _possibleConstructorReturn(this, (HeatmapWithControls.__proto__ || Object.getPrototypeOf(HeatmapWithControls)).call(this, props));
	    }

	    _createClass(HeatmapWithControls, [{
	        key: '_getSelectedExpressionLevelFilters',
	        value: function _getSelectedExpressionLevelFilters() {
	            var _this2 = this;

	            return this.props.selectedFilters.find(function (selectedFilter) {
	                return selectedFilter.name === _this2.props.expressionLevelFilters.name;
	            }).valueNames;
	        }
	    }, {
	        key: '_getSelectedOrdering',
	        value: function _getSelectedOrdering() {
	            var _this3 = this;

	            var selectedOrderingKey = Object.keys(this.props.orderings).find(function (orderingKey) {
	                return _this3.props.orderings[orderingKey].name === _this3.props.selectedOrderingName;
	            });
	            return this.props.orderings[selectedOrderingKey];
	        }
	    }, {
	        key: '_heatmapDataToPresent',
	        value: function _heatmapDataToPresent() {
	            var _this4 = this;

	            return (0, _Manipulators.manipulate)({
	                //this.state.selectedHeatmapFilters
	                //.find(selectedFilter => selectedFilter.name === this.props.chartData.expressionLevelFilters.name)
	                //.valueNames
	                keepSeries: function keepSeries(series) {
	                    return _this4.props.selectedFilters[0].valueNames.includes(series.info.name);
	                },
	                keepRow: this.props.heatmapConfig.coexpressionsAvailable ? function (rowHeader) {
	                    return _this4._rowHeadersThatCoexpressionSliderSaysWeCanInclude().includes(rowHeader.label);
	                } : function () {
	                    return true;
	                },
	                keepColumn: this.props.groupingFilters.length > 0 ? function (columnHeader) {
	                    return _this4._columnHeadersThatColumnGroupingFiltersSayWeCanInclude().includes(columnHeader.label);
	                } : function () {
	                    return true;
	                },
	                ordering: this._getSelectedOrdering(),
	                allowEmptyColumns: Boolean(this.props.heatmapConfig.experiment)
	            }, this.props.heatmapData);
	        }
	    }, {
	        key: '_rowHeadersThatCoexpressionSliderSaysWeCanInclude',
	        value: function _rowHeadersThatCoexpressionSliderSaysWeCanInclude() {
	            // to keep up with the quirky function names
	            return this.props.heatmapData.yAxisCategories.slice(0, this.props.coexpressionsShown + 1).map(function (yAxisCategory) {
	                return yAxisCategory.label;
	            });
	        }
	    }, {
	        key: '_columnHeadersThatColumnGroupingFiltersSayWeCanInclude',
	        value: function _columnHeadersThatColumnGroupingFiltersSayWeCanInclude() {
	            // In experiment heatmaps no Anatomical Systems filter are available, but they are built nonetheless and every
	            // grouping filter is selected by default, so all columns are included
	            var groupingFilterNames = this.props.groupingFilters.filter(function (filter) {
	                return filter.valueGroupings.length > 0;
	            }).map(function (groupingFilter) {
	                return groupingFilter.name;
	            });

	            return (0, _lodash.spread)(_lodash.intersection)(this.props.selectedFilters.filter(function (selectedFilter) {
	                return groupingFilterNames.includes(selectedFilter.name);
	            }).map(function (selectedFilter) {
	                return selectedFilter.valueNames;
	            }));
	        }
	    }, {
	        key: '_renderOrderings',
	        value: function _renderOrderings(heatmapDataToPresent) {
	            var _this5 = this;

	            return this.props.heatmapConfig.isMultiExperiment ? _react2.default.createElement('div', { style: { display: 'inline-block', padding: '5px' } }, _react2.default.createElement(_OrderingsDropdown2.default, {
	                orderings: Object.keys(this.props.orderings).map(function (orderingKey) {
	                    return _this5.props.orderings[orderingKey].name;
	                }),
	                selected: this.props.selectedOrderingName,
	                onSelect: this.props.onSelectOrdering,
	                zoom: this.props.zoom,
	                hasLessThanTwoRows: heatmapDataToPresent.yAxisCategories.length < 2
	            })) : null;
	        }
	    }, {
	        key: '_renderFilters',
	        value: function _renderFilters() {
	            return this.props.heatmapConfig.isMultiExperiment ? _react2.default.createElement('div', { style: { display: 'inline-block', padding: '5px' } }, _react2.default.createElement(_FiltersModal2.default, { filters: [this.props.expressionLevelFilters].concat(_toConsumableArray(this.props.groupingFilters)),
	                selectedFilters: this.props.selectedFilters,
	                onSelectFilters: this.props.onSelectFilters,
	                disabled: this.props.zoom
	            })) : null;
	        }
	    }, {
	        key: '_renderDownloadButton',
	        value: function _renderDownloadButton(heatmapDataToPresent) {
	            var downloadOptions = {
	                download: {
	                    name: this.props.heatmapConfig.shortDescription || "download",
	                    descriptionLines: [this.props.heatmapConfig.description].concat(_toConsumableArray(this.props.selectedOrderingName ? ['Ordering: ' + this.props.selectedOrderingName] : []), _toConsumableArray(this.props.heatmapConfig.coexpressionsAvailable ? ['Including ' + this.props.coexpressionsShown + ' genes with similar expression pattern'] : [])),
	                    heatmapData: heatmapDataToPresent
	                },
	                disclaimer: this.props.heatmapConfig.disclaimer
	            };

	            return _react2.default.createElement('div', { style: { display: 'inline-block', padding: '5px' } }, _react2.default.createElement(_DownloadButton2.default, downloadOptions));
	        }
	    }, {
	        key: '_renderGenomeBrowserSelect',
	        value: function _renderGenomeBrowserSelect() {
	            return this.props.heatmapConfig.genomeBrowsers.length ? _react2.default.createElement('div', { style: { display: 'inline-block', padding: '5px' } }, _react2.default.createElement(_GenomeBrowsersDropdown2.default, { genomeBrowsers: this.props.heatmapConfig.genomeBrowsers,
	                selected: this.props.selectedGenomeBrowser,
	                onSelect: this.props.onSelectGenomeBrowser })) : null;
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this6 = this;

	            var heatmapDataToPresent = this._heatmapDataToPresent();

	            var _axesFormatters = (0, _axesFormatters3.default)(this.props.heatmapConfig),
	                yAxisStyle = _axesFormatters.yAxisStyle,
	                yAxisFormatter = _axesFormatters.yAxisFormatter,
	                xAxisStyle = _axesFormatters.xAxisStyle,
	                xAxisFormatter = _axesFormatters.xAxisFormatter;

	            var heatmapProps = {
	                heatmapData: heatmapDataToPresent,
	                colourAxis: this.props.colourAxis,
	                cellTooltipFormatter: (0, _heatmapCellTooltipFormatter2.default)(this.props.heatmapConfig),
	                yAxisStyle: yAxisStyle,
	                yAxisFormatter: yAxisFormatter,
	                xAxisStyle: xAxisStyle,
	                xAxisFormatter: xAxisFormatter,
	                onZoom: this.props.onZoom,
	                ontologyIdsToHighlight: this.props.ontologyIdsToHighlight,
	                events: (0, _Events2.default)({
	                    heatmapData: heatmapDataToPresent,
	                    onSelectOntologyIds: this.props.onOntologyIdIsUnderFocus,
	                    genomeBrowser: this.props.selectedGenomeBrowser,
	                    experimentAccession: this.props.heatmapConfig.experiment && this.props.heatmapConfig.experiment.accession,
	                    accessKey: this.props.heatmapConfig.experiment && this.props.heatmapConfig.experiment.accessKey,
	                    atlasUrl: this.props.heatmapConfig.atlasUrl
	                })
	            };

	            var infoMessages = this.props.selectedGenomeBrowser === 'none' ? ['hoo'] : ['Click on a cell to open the selected genome browser with attached tracks if available'];
	            var info = infoMessages.map(function (item) {
	                return _react2.default.createElement('p', { key: item, style: { clear: 'both', float: 'right', fontSize: 'small', margin: '0' } }, item);
	            });

	            return _react2.default.createElement('div', null, _react2.default.createElement('div', null, _react2.default.createElement('div', { style: { float: 'left', lineHeight: '2.5rem', padding: '0.5rem 0' } }, this.props.heatmapConfig.introductoryMessage), _react2.default.createElement('div', { style: { float: 'right', padding: '0.5rem 0' } }, this._renderGenomeBrowserSelect(), this._renderOrderings(heatmapDataToPresent), this._renderFilters(), this._renderDownloadButton(heatmapDataToPresent)), _react2.default.createElement('p', { style: { clear: 'both', float: 'right', fontSize: 'small', margin: '0',
	                    visibility: this.props.selectedGenomeBrowser === 'none' ? 'hidden' : ' visible' } }, 'Click on a cell to open the selected genome browser with attached tracks if available')), _react2.default.createElement('div', { style: { clear: 'both' } }, heatmapProps.heatmapData.yAxisCategories < 1 ? _react2.default.createElement('div', { style: { padding: '50px 0' } }, 'No data match your filtering criteria or your original query. Please, change your query or your filters and try again.') : this.props.heatmapConfig.isMultiExperiment ? _react2.default.createElement('div', null, _react2.default.createElement(_HeatmapCanvas2.default, heatmapProps), _react2.default.createElement(_Main.DataSeriesLegend, {
	                dataSeries: this.props.heatmapData.dataSeries,
	                selectedExpressionLevelFilters: this._getSelectedExpressionLevelFilters()
	            })) : _react2.default.createElement('div', null, _react2.default.createElement(_Main.GradientLegend, {
	                heatmapConfig: this.props.heatmapConfig,
	                colourAxis: this.props.colourAxis
	            }), _react2.default.createElement(_HeatmapCanvas2.default, heatmapProps)), this.props.heatmapConfig.coexpressionsAvailable && !this.props.heatmapConfig.isWidget ? _react2.default.createElement(_CoexpressionOption2.default, { geneName: this.props.heatmapData.yAxisCategories[0].label,
	                numCoexpressionsVisible: this.props.coexpressionsShown,
	                numCoexpressionsAvailable: this.props.heatmapData.yAxisCategories.length - 1,
	                showCoexpressionsCallback: function showCoexpressionsCallback(e) {
	                    return _this6.props.onCoexpressionOptionChange(e);
	                }
	            }) : null));
	        }
	    }]);

	    return HeatmapWithControls;
	}(_react2.default.Component);

	HeatmapWithControls.propTypes = {
	    heatmapConfig: _chartDataPropTypes.heatmapConfigPropTypes.isRequired,
	    heatmapData: _chartDataPropTypes.heatmapDataPropTypes.isRequired,
	    colourAxis: _chartDataPropTypes.colourAxisPropTypes, // Only available in experiment heatmap

	    orderings: _chartDataPropTypes.orderingsPropTypesValidator,
	    selectedOrderingName: _propTypes2.default.string.isRequired,
	    onSelectOrdering: _propTypes2.default.func.isRequired,

	    expressionLevelFilters: _chartDataPropTypes.filterPropTypes.isRequired,
	    groupingFilters: _propTypes2.default.arrayOf(_chartDataPropTypes.filterPropTypes).isRequired,
	    selectedFilters: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        valueNames: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired
	    })).isRequired,
	    onSelectFilters: _propTypes2.default.func.isRequired,

	    selectedGenomeBrowser: _propTypes2.default.string,
	    onSelectGenomeBrowser: _propTypes2.default.func,

	    legendItems: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        key: _propTypes2.default.string.isRequired,
	        name: _propTypes2.default.string.isRequired,
	        colour: _propTypes2.default.string.isRequired,
	        on: _propTypes2.default.bool.isRequired
	    })),

	    dataSeriesLegendProps: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        key: _propTypes2.default.string.isRequired,
	        name: _propTypes2.default.string.isRequired,
	        colour: _propTypes2.default.string.isRequired,
	        on: _propTypes2.default.bool.isRequired
	    })),

	    gradientLegendProps: _chartDataPropTypes.colourAxisPropTypes,

	    coexpressionsShown: _propTypes2.default.number,
	    onCoexpressionOptionChange: _propTypes2.default.func,

	    zoom: _propTypes2.default.bool.isRequired,
	    onZoom: _propTypes2.default.func,
	    ontologyIdsToHighlight: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    onOntologyIdIsUnderFocus: _propTypes2.default.func.isRequired
	};

	var _default = HeatmapWithControls;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(HeatmapWithControls, 'HeatmapWithControls', 'src/manipulate/HeatmapWithControls.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/HeatmapWithControls.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_GenomeBrowsersDropdown2, "_GenomeBrowsersDropdown2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_OrderingsDropdown2, "_OrderingsDropdown2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_FiltersModal2, "_FiltersModal2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_DownloadButton2, "_DownloadButton2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_HeatmapCanvas2, "_HeatmapCanvas2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_heatmapCellTooltipFormatter2, "_heatmapCellTooltipFormatter2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_axesFormatters3, "_axesFormatters3", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_CoexpressionOption2, "_CoexpressionOption2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_Events2, "_Events2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_toConsumableArray, "_toConsumableArray", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(HeatmapWithControls, "HeatmapWithControls", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/HeatmapWithControls.js");
	}();

	;

/***/ },

/***/ 2353:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _Dropdown = __webpack_require__(1230);

	var _Dropdown2 = _interopRequireDefault(_Dropdown);

	var _MenuItem = __webpack_require__(1280);

	var _MenuItem2 = _interopRequireDefault(_MenuItem);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var GenomeBrowsersDropdown = function (_React$Component) {
	    _inherits(GenomeBrowsersDropdown, _React$Component);

	    function GenomeBrowsersDropdown(props) {
	        _classCallCheck(this, GenomeBrowsersDropdown);

	        return _possibleConstructorReturn(this, (GenomeBrowsersDropdown.__proto__ || Object.getPrototypeOf(GenomeBrowsersDropdown)).call(this, props));
	    }

	    _createClass(GenomeBrowsersDropdown, [{
	        key: 'handleChange',
	        value: function handleChange(eventKey, event) {
	            event.preventDefault();
	            this.props.onSelect(eventKey);
	        }
	    }, {
	        key: '_genomeBrowserIcon',
	        value: function _genomeBrowserIcon(genomeBrowser) {
	            switch (genomeBrowser) {
	                case 'none':
	                    return 'eye-close';
	                default:
	                    return 'eye-open';
	            }
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            var genomeBrowsers = [{
	                id: 'none',
	                label: 'Select genome browser to view tracks'
	            }].concat(_toConsumableArray(this.props.genomeBrowsers.map(function (genomeBrowserName) {
	                return {
	                    id: genomeBrowserName.replace(/\s+/g, '').toLowerCase(),
	                    label: genomeBrowserName + ' genome browser'
	                };
	            })));

	            return _react2.default.createElement('div', null, _react2.default.createElement(_Dropdown2.default, { id: 'genome-browsers-dropdown',
	                onSelect: function onSelect(key, e) {
	                    return _this2.handleChange(key, e);
	                },
	                title: 'Choose genome browser' }, _react2.default.createElement(_Dropdown2.default.Toggle, { bsSize: 'small',
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: this._genomeBrowserIcon(this.props.selected)
	            }), ' ', genomeBrowsers.find(function (gb) {
	                return _this2.props.selected === gb.id;
	            }).label), _react2.default.createElement(_Dropdown2.default.Menu, { bsSize: 'small' }, genomeBrowsers.map(function (gb) {
	                return _react2.default.createElement(_MenuItem2.default, { style: { listStyleImage: 'none' }, key: gb.id, eventKey: gb.id, href: '#' }, gb.label);
	            }))));
	        }
	    }]);

	    return GenomeBrowsersDropdown;
	}(_react2.default.Component);

	GenomeBrowsersDropdown.propTypes = {
	    genomeBrowsers: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    selected: _propTypes2.default.string,
	    onSelect: _propTypes2.default.func
	};

	var _default = GenomeBrowsersDropdown;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(GenomeBrowsersDropdown, 'GenomeBrowsersDropdown', 'src/manipulate/controls/GenomeBrowsersDropdown.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/GenomeBrowsersDropdown.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_Dropdown2, "_Dropdown2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_MenuItem2, "_MenuItem2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_toConsumableArray, "_toConsumableArray", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(GenomeBrowsersDropdown, "GenomeBrowsersDropdown", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/GenomeBrowsersDropdown.js");
	}();

	;

/***/ },

/***/ 2354:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _Dropdown = __webpack_require__(1230);

	var _Dropdown2 = _interopRequireDefault(_Dropdown);

	var _MenuItem = __webpack_require__(1280);

	var _MenuItem2 = _interopRequireDefault(_MenuItem);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var OrderingsDropdown = function (_React$Component) {
	    _inherits(OrderingsDropdown, _React$Component);

	    function OrderingsDropdown() {
	        _classCallCheck(this, OrderingsDropdown);

	        return _possibleConstructorReturn(this, (OrderingsDropdown.__proto__ || Object.getPrototypeOf(OrderingsDropdown)).apply(this, arguments));
	    }

	    _createClass(OrderingsDropdown, [{
	        key: 'handleChange',
	        value: function handleChange(eventKey, event) {
	            event.preventDefault();
	            this.props.onSelect(event.target.text);
	        }
	    }, {
	        key: '_orderingIcon',
	        value: function _orderingIcon(ordering) {
	            switch (ordering) {
	                case 'Alphabetical order':
	                    return 'sort-by-alphabet';
	                case 'Gene expression rank':
	                    return 'sort-by-attributes-alt';
	                case 'By experiment type':
	                    return 'sort-by-order';
	                default:
	                    return 'sort-by-order';
	            }
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            return _react2.default.createElement('div', null, _react2.default.createElement(_Dropdown2.default, { id: 'orderings-dropdown',
	                onSelect: function onSelect(key, e) {
	                    return _this2.handleChange(key, e);
	                },
	                title: this.props.zoom ? 'Reset zoom to enable sorting options' : '',
	                disabled: this.props.zoom || this.props.hasLessThanTwoRows }, _react2.default.createElement(_Dropdown2.default.Toggle, { bsSize: 'small',
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: this._orderingIcon(this.props.selected) }), ' ', this.props.selected), _react2.default.createElement(_Dropdown2.default.Menu, { bsSize: 'small' }, this.props.orderings.map(function (orderingName) {
	                return _react2.default.createElement(_MenuItem2.default, { style: { listStyleImage: 'none' }, key: orderingName, href: '#' }, orderingName);
	            }))));
	        }
	    }]);

	    return OrderingsDropdown;
	}(_react2.default.Component);

	OrderingsDropdown.propTypes = {
	    orderings: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    selected: _propTypes2.default.string.isRequired,
	    onSelect: _propTypes2.default.func.isRequired,
	    zoom: _propTypes2.default.bool.isRequired,
	    hasLessThanTwoRows: _propTypes2.default.bool.isRequired
	};

	var _default = OrderingsDropdown;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(OrderingsDropdown, 'OrderingsDropdown', 'src/manipulate/controls/OrderingsDropdown.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/OrderingsDropdown.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_Dropdown2, "_Dropdown2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_MenuItem2, "_MenuItem2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(OrderingsDropdown, "OrderingsDropdown", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/OrderingsDropdown.js");
	}();

	;

/***/ },

/***/ 2355:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _Modal = __webpack_require__(1281);

	var _Modal2 = _interopRequireDefault(_Modal);

	var _Button = __webpack_require__(1202);

	var _Button2 = _interopRequireDefault(_Button);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	var _FlatFilter = __webpack_require__(2356);

	var _FlatFilter2 = _interopRequireDefault(_FlatFilter);

	var _GroupingFilter = __webpack_require__(2359);

	var _GroupingFilter2 = _interopRequireDefault(_GroupingFilter);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var FiltersModal = function (_React$Component) {
	    _inherits(FiltersModal, _React$Component);

	    function FiltersModal(props) {
	        _classCallCheck(this, FiltersModal);

	        var _this = _possibleConstructorReturn(this, (FiltersModal.__proto__ || Object.getPrototypeOf(FiltersModal)).call(this, props));

	        _this.state = {
	            currentTab: _this.props.filters[0].name,
	            showModal: false
	        };

	        _this.open = _this._open.bind(_this);
	        _this.close = _this._close.bind(_this);
	        _this.onSelectFilterValue = _this._onSelectFilterValue.bind(_this);
	        return _this;
	    }

	    _createClass(FiltersModal, [{
	        key: '_close',
	        value: function _close() {
	            this.setState({
	                showModal: false
	            });
	        }
	    }, {
	        key: '_open',
	        value: function _open() {
	            this.setState({
	                showModal: true
	            });
	        }
	    }, {
	        key: '_onSelectFilterValue',
	        value: function _onSelectFilterValue(filterName, newFilterValues) {
	            this.props.onSelectFilters(this.props.selectedFilters.map(function (previousSelectedFilter) {
	                return {
	                    name: previousSelectedFilter.name,
	                    valueNames: previousSelectedFilter.name === filterName ? newFilterValues : previousSelectedFilter.valueNames.map(function (valueName) {
	                        return valueName;
	                    })
	                };
	            }));
	        }
	    }, {
	        key: '_renderFlatFilter',
	        value: function _renderFlatFilter(filter) {
	            return _react2.default.createElement(_FlatFilter2.default, _extends({ key: filter.name,
	                selected: this.props.selectedFilters.find(function (selectedFilter) {
	                    return selectedFilter.name === filter.name;
	                }).valueNames,
	                onSelectFilterValue: this.onSelectFilterValue
	            }, filter));
	        }
	    }, {
	        key: '_renderGroupingFilter',
	        value: function _renderGroupingFilter(filter) {
	            return _react2.default.createElement(_GroupingFilter2.default, _extends({ key: filter.name,
	                selected: this.props.selectedFilters.find(function (selectedFilter) {
	                    return selectedFilter.name === filter.name;
	                }).valueNames,
	                onSelectFilterValue: this.onSelectFilterValue
	            }, filter));
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            return _react2.default.createElement('div', null, _react2.default.createElement(_Button2.default, { bsSize: 'small', onClick: this.open, disabled: this.props.disabled,
	                title: this.props.disabled ? 'Reset zoom to enable filters' : '',
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: 'equalizer' }), _react2.default.createElement('span', { style: { verticalAlign: 'middle' } }, ' Filters')), _react2.default.createElement(_Modal2.default, { show: this.state.showModal, onHide: this.close, bsSize: 'large' }, _react2.default.createElement(_Modal2.default.Header, { closeButton: true }, this.props.filters.length > 1 ? _react2.default.createElement('ul', { className: 'nav nav-tabs' }, this.props.filters.map(function (f) {
	                return _react2.default.createElement('li', { key: f.name,
	                    className: f.name == _this2.state.currentTab ? "active" : "" }, _react2.default.createElement('a', { href: '#', onClick: function onClick() {
	                        _this2.setState({ currentTab: f.name });
	                    } }, f.name));
	            })) : _react2.default.createElement('h4', { className: 'modal-title' }, ' Filters ')), _react2.default.createElement(_Modal2.default.Body, null, this.props.filters.filter(function (filter) {
	                return filter.name == _this2.state.currentTab;
	            }).map(function (filter) {
	                return filter.valueGroupings ? _this2._renderGroupingFilter(filter) : _this2._renderFlatFilter(filter);
	            })), _react2.default.createElement(_Modal2.default.Footer, null, _react2.default.createElement(_Button2.default, { onClick: this.close,
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, 'Close'))));
	        }
	    }]);

	    return FiltersModal;
	}(_react2.default.Component);

	FiltersModal.propTypes = {
	    filters: _propTypes2.default.arrayOf(_chartDataPropTypes.filterPropTypes).isRequired,
	    selectedFilters: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        valueNames: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired
	    })).isRequired,

	    disabled: _propTypes2.default.bool.isRequired,
	    onSelectFilters: _propTypes2.default.func.isRequired
	};

	var _default = FiltersModal;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(FiltersModal, 'FiltersModal', 'src/manipulate/controls/filter/FiltersModal.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/filter/FiltersModal.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, "_extends", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_Modal2, "_Modal2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_Button2, "_Button2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_FlatFilter2, "_FlatFilter2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_GroupingFilter2, "_GroupingFilter2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(FiltersModal, "FiltersModal", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FiltersModal.js");
	}();

	;

/***/ },

/***/ 2356:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _xor = __webpack_require__(673);

	var _xor2 = _interopRequireDefault(_xor);

	__webpack_require__(2357);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var FlatFilter = function (_React$Component) {
	    _inherits(FlatFilter, _React$Component);

	    function FlatFilter() {
	        _classCallCheck(this, FlatFilter);

	        return _possibleConstructorReturn(this, (FlatFilter.__proto__ || Object.getPrototypeOf(FlatFilter)).apply(this, arguments));
	    }

	    _createClass(FlatFilter, [{
	        key: '_toggleFilterValue',
	        value: function _toggleFilterValue(filterValueName) {
	            this.props.onSelectFilterValue(this.props.name, (0, _xor2.default)(this.props.selected, [filterValueName]));
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            return _react2.default.createElement('div', { className: 'gxaFilter' }, _react2.default.createElement('h5', null, this.props.name), _react2.default.createElement('div', { className: 'filterBody' }, _react2.default.createElement('div', null, this.props.values.map(function (value) {
	                return _react2.default.createElement('div', { key: value.name }, _react2.default.createElement('input', { type: 'checkbox',
	                    value: value.name,
	                    onChange: function onChange() {
	                        return _this2._toggleFilterValue(value.name);
	                    },
	                    disabled: value.disabled,
	                    checked: _this2.props.selected.includes(value.name)
	                }), _react2.default.createElement('span', { style: { color: value.disabled ? 'grey' : '' } }, value.name));
	            }))));
	        }
	    }]);

	    return FlatFilter;
	}(_react2.default.Component);

	FlatFilter.propTypes = {
	    name: _propTypes2.default.string.isRequired,
	    values: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        disabled: _propTypes2.default.bool.isRequired
	    })).isRequired,
	    selected: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    onSelectFilterValue: _propTypes2.default.func.isRequired
	};

	var _default = FlatFilter;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(FlatFilter, 'FlatFilter', 'src/manipulate/controls/filter/FlatFilter.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/filter/FlatFilter.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_xor2, "_xor2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(FlatFilter, "FlatFilter", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/FlatFilter.js");
	}();

	;

/***/ },

/***/ 2357:
[2884, 2358, 2346],

/***/ 2358:
[2885, 2345],

/***/ 2359:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _ButtonGroup = __webpack_require__(1203);

	var _ButtonGroup2 = _interopRequireDefault(_ButtonGroup);

	var _Button = __webpack_require__(1202);

	var _Button2 = _interopRequireDefault(_Button);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	var _xor = __webpack_require__(673);

	var _xor2 = _interopRequireDefault(_xor);

	__webpack_require__(2357);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var FilterOption = function (_React$Component) {
	    _inherits(FilterOption, _React$Component);

	    function FilterOption() {
	        _classCallCheck(this, FilterOption);

	        return _possibleConstructorReturn(this, (FilterOption.__proto__ || Object.getPrototypeOf(FilterOption)).apply(this, arguments));
	    }

	    _createClass(FilterOption, [{
	        key: 'toggleAll',
	        value: function toggleAll() {
	            this.props.onNewSelected((0, _xor2.default)(this.props.values, this.props.selected).length ? this.props.values : []);
	        }
	    }, {
	        key: 'toggleOne',
	        value: function toggleOne(valueName) {
	            this.props.onNewSelected((0, _xor2.default)(this.props.selected, [valueName]));
	        }
	    }, {
	        key: 'toggleOpen',
	        value: function toggleOpen() {
	            this.props.onToggleOpen();
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            var allChecked = this.props.values.every(function (v) {
	                return _this2.props.selected.includes(v);
	            });
	            var allUnchecked = this.props.values.every(function (v) {
	                return !_this2.props.selected.includes(v);
	            });

	            var openable = this.props.values.length !== 1 || this.props.values[0] !== this.props.name;
	            // Indeterminate is only a visual state, logically they are unchecked
	            return _react2.default.createElement('div', { className: 'filterBody large-6 columns' }, _react2.default.createElement('input', { type: 'checkbox',
	                value: this.props.name,
	                onChange: function onChange() {
	                    return _this2.toggleAll();
	                },
	                checked: allChecked,
	                ref: function ref(checkbox) {
	                    checkbox ? checkbox.indeterminate = !allChecked && !allUnchecked : null;
	                }
	            }), _react2.default.createElement('div', { className: 'groupName',
	                onClick: function onClick() {
	                    return _this2.toggleOpen();
	                },
	                href: '#' }, this.props.name, ' ', openable && _react2.default.createElement(_Glyphicon2.default, { style: { fontSize: 'x-small', paddingLeft: '5px' }, glyph: this.props.isOpen ? 'menu-up' : 'menu-down' })), openable && this.props.isOpen && _react2.default.createElement('div', { className: 'options' }, this.props.values.map(function (value) {
	                return _react2.default.createElement('div', { className: 'option', key: value }, _react2.default.createElement('input', { type: 'checkbox',
	                    value: value,
	                    onChange: function onChange() {
	                        return _this2.toggleOne(value);
	                    },
	                    checked: _this2.props.selected.includes(value) }), _react2.default.createElement('span', null, ' ', value));
	            })));
	        }
	    }]);

	    return FilterOption;
	}(_react2.default.Component);

	FilterOption.propTypes = {
	    name: _propTypes2.default.string.isRequired,
	    values: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    selected: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    isOpen: _propTypes2.default.bool.isRequired,
	    onToggleOpen: _propTypes2.default.func.isRequired,
	    onNewSelected: _propTypes2.default.func.isRequired
	};

	var GroupingFilter = function (_React$Component2) {
	    _inherits(GroupingFilter, _React$Component2);

	    function GroupingFilter(props) {
	        _classCallCheck(this, GroupingFilter);

	        var _this3 = _possibleConstructorReturn(this, (GroupingFilter.__proto__ || Object.getPrototypeOf(GroupingFilter)).call(this, props));

	        _this3.state = {
	            groupsUserAskedToKeepOpen: []
	        };
	        return _this3;
	    }

	    _createClass(GroupingFilter, [{
	        key: '_renderValueGrouping',
	        value: function _renderValueGrouping(name, values) {
	            var _this4 = this;

	            var userWantedOpen = this.state.groupsUserAskedToKeepOpen.includes(name);
	            var selectedHere = this.props.selected.filter(function (e) {
	                return values.includes(e);
	            });
	            var selectedNotHere = this.props.selected.filter(function (e) {
	                return !values.includes(e);
	            });
	            // isOpen={userWantedOpen || impliedOpen} is a nifty idea, but the user loses focus and there are potentially
	            // too many things going on we could think of using CSS animations (such as a background fading highlight on
	            // the affected tissues to signal the interactions between subsystems)
	            //const impliedOpen = !(selectedHere.length === 0 || values.every(v => selectedHere.includes(v)))
	            return _react2.default.createElement(FilterOption, { key: name,
	                name: name,
	                values: values,
	                selected: selectedHere,
	                isOpen: userWantedOpen,
	                onToggleOpen: function onToggleOpen() {
	                    _this4.setState(function (previousState) {
	                        return {
	                            groupsUserAskedToKeepOpen: (0, _xor2.default)(previousState.groupsUserAskedToKeepOpen, [name])
	                        };
	                    });
	                },
	                onNewSelected: function onNewSelected(selectedInThisOption) {
	                    _this4.props.onSelectFilterValue(_this4.props.name, selectedNotHere.concat(selectedInThisOption));
	                } });
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this5 = this;

	            return _react2.default.createElement('div', { className: 'gxaFilter' }, _react2.default.createElement(_ButtonGroup2.default, null, _react2.default.createElement(_Button2.default, { bsSize: 'xsmall',
	                onClick: function onClick() {
	                    _this5.props.onSelectFilterValue(_this5.props.name, _this5.props.values.map(function (value) {
	                        return value.name;
	                    }));
	                },
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: 'plus' }), _react2.default.createElement('span', { style: { verticalAlign: 'middle' } }, ' Choose all')), _react2.default.createElement(_Button2.default, { bsSize: 'xsmall',
	                onClick: function onClick() {
	                    _this5.props.onSelectFilterValue(_this5.props.name, []);
	                },
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: 'minus' }), _react2.default.createElement('span', { style: { verticalAlign: 'middle' } }, ' Remove all'))), _react2.default.createElement('div', { className: 'row' }, this.props.valueGroupings.map(function (a) {
	                return _this5._renderValueGrouping(a[0], a[1]);
	            })));
	        }
	    }]);

	    return GroupingFilter;
	}(_react2.default.Component);

	GroupingFilter.propTypes = {
	    name: _propTypes2.default.string.isRequired,
	    values: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        disabled: _propTypes2.default.bool.isRequired
	    })).isRequired,
	    valueGroupings: _propTypes2.default.array, // Indirectly validated as [string, array of strings] in FilterOption
	    onSelectFilterValue: _propTypes2.default.func.isRequired
	};

	var _default = GroupingFilter;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(FilterOption, 'FilterOption', 'src/manipulate/controls/filter/GroupingFilter.js');

	    __REACT_HOT_LOADER__.register(GroupingFilter, 'GroupingFilter', 'src/manipulate/controls/filter/GroupingFilter.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/filter/GroupingFilter.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_ButtonGroup2, "_ButtonGroup2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_Button2, "_Button2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_xor2, "_xor2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(FilterOption, "FilterOption", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(GroupingFilter, "GroupingFilter", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/filter/GroupingFilter.js");
	}();

	;

/***/ },

/***/ 2360:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.colourAxisPropTypes = exports.dataSeriesPropTypes = exports.filterPropTypes = exports.orderingsPropTypesValidator = exports.chartDataPropTypes = exports.heatmapDataPropTypes = exports.heatmapConfigPropTypes = undefined;

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	// const heatmapDataPropTypes = (props, propName) => {
	//     const heatmapData = props[propName]
	//     // const possiblyError = ValidateDataSeries(heatmapData.dataSeries)
	//     // if (possiblyError !== undefined) {
	//     //     return possiblyError
	//     // }
	//
	//     const width = heatmapData.xAxisCategories.length
	//     const height = heatmapData.yAxisCategories.length
	//
	//     for (let i = 0 i < heatmapData.dataSeries.length i++) {
	//         for (let j = 0 j < heatmapData.dataSeries[i].data.length j++) {
	//             const point = heatmapData.dataSeries[i].data[j]
	//             const x = point.x
	//             const y = point.y
	//             if (x < 0 || y < 0 || x >= width || y >= height) {
	//                 return new Error(`Point with coordinates outside range: ${x}, ${y}`)
	//             }
	//         }
	//     }
	// }

	var differentialTooltipPropTypes = _propTypes2.default.shape({
	    contrastDescription: _propTypes2.default.string.isRequired,
	    experimentDescription: _propTypes2.default.string.isRequired,
	    properties: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        contrastPropertyType: _propTypes2.default.oneOf(['FACTOR', 'SAMPLE']),
	        propertyName: _propTypes2.default.string.isRequired,
	        referenceValue: _propTypes2.default.string.isRequired,
	        testValue: _propTypes2.default.string.isRequired
	    })).isRequired,
	    referenceReplicates: _propTypes2.default.number.isRequired,
	    resources: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        icon: _propTypes2.default.string.isRequired,
	        type: _propTypes2.default.string.isRequired,
	        url: _propTypes2.default.string.isRequired
	    })).isRequired,
	    testReplicates: _propTypes2.default.number.isRequired
	});

	var baselineTooltipPropTypes = _propTypes2.default.shape({
	    properties: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        contrastPropertyType: _propTypes2.default.oneOf(['FACTOR', 'SAMPLE']),
	        propertyName: _propTypes2.default.string.isRequired,
	        testValue: _propTypes2.default.string.isRequired
	    })).isRequired,
	    replicates: _propTypes2.default.number.isRequired
	});

	var baselineExperimentsTooltipPropTypes = _propTypes2.default.shape({});

	var orderingsPropTypesValidator = function orderingsPropTypesValidator(props, propName, componentName) {
	    var orderings = props[propName];

	    var isPermutation = function isPermutation(arr) {
	        return [].concat(arr).sort(function (a, b) {
	            return a - b;
	        }).map(function (el, ix) {
	            return el === ix;
	        }).reduce(function (l, r) {
	            return l && r;
	        });
	    };

	    if (!orderings.hasOwnProperty('default')) {
	        return new Error('Default ordering missing in \'' + componentName + '\'');
	    }

	    Object.keys(orderings).forEach(function (orderingName) {
	        var ordering = orderings[orderingName];

	        if (!isPermutation(ordering.columns)) {
	            return new Error('Column ordering invalid: \'' + orderingName + '\' in \'' + componentName + '\'');
	        }
	        if (!isPermutation(ordering.rows)) {
	            return new Error('Row ordering invalid: \'' + orderingName + '\' in \'' + componentName + '\'');
	        }
	    });
	};

	var dataSeriesPropTypes = _propTypes2.default.arrayOf(_propTypes2.default.shape({
	    data: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        info: _propTypes2.default.shape({
	            unit: _propTypes2.default.string.isRequired,
	            foldChange: _propTypes2.default.number, // These three only in diff experiments
	            pValue: _propTypes2.default.number,
	            tStat: _propTypes2.default.number
	        }).isRequired,
	        value: _propTypes2.default.number.isRequired,
	        x: _propTypes2.default.number.isRequired,
	        y: _propTypes2.default.number.isRequired
	    })).isRequired,
	    info: _propTypes2.default.shape({
	        colour: _propTypes2.default.string.isRequired,
	        name: _propTypes2.default.string.isRequired
	    }).isRequired
	}));

	var heatmapDataPropTypes = _propTypes2.default.shape({
	    dataSeries: dataSeriesPropTypes.isRequired,
	    xAxisCategories: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        id: _propTypes2.default.string.isRequired,
	        info: _propTypes2.default.shape({
	            groupings: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	                memberName: _propTypes2.default.string.isRequired,
	                name: _propTypes2.default.string.isRequired,
	                values: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	                    id: _propTypes2.default.string.isRequired,
	                    label: _propTypes2.default.string.isRequired
	                })).isRequired
	            })).isRequired,
	            tooltip: _propTypes2.default.oneOfType([differentialTooltipPropTypes, baselineTooltipPropTypes, baselineExperimentsTooltipPropTypes]).isRequired,
	            trackId: _propTypes2.default.string.isRequired
	        }).isRequired,
	        label: _propTypes2.default.string.isRequired
	    })).isRequired,
	    yAxisCategories: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        id: _propTypes2.default.string.isRequired,
	        info: _propTypes2.default.shape({
	            designElement: _propTypes2.default.string.isRequired,
	            trackId: _propTypes2.default.string.isRequired
	        }).isRequired,
	        label: _propTypes2.default.string.isRequired
	    })).isRequired
	});

	var boxplotDataPropTypes = _propTypes2.default.shape({
	    dataSeries: _propTypes2.default.arrayOf(_propTypes2.default.arrayOf(_propTypes2.default.number)).isRequired,
	    xAxisCategories: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    title: _propTypes2.default.string.isRequired,
	    unit: _propTypes2.default.string.isRequired
	});

	var heatmapConfigPropTypes = _propTypes2.default.shape({
	    inProxy: _propTypes2.default.string.isRequired,
	    outProxy: _propTypes2.default.string.isRequired,
	    atlasUrl: _propTypes2.default.string.isRequired,
	    description: _propTypes2.default.string.isRequired,
	    genomeBrowsers: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    introductoryMessage: _propTypes2.default.string.isRequired,
	    shortDescription: _propTypes2.default.string.isRequired,
	    xAxisLegendName: _propTypes2.default.string.isRequired,
	    yAxisLegendName: _propTypes2.default.string.isRequired,

	    experiment: _experimentTypeUtils.experimentPropTypes,

	    disclaimer: _propTypes2.default.string.isRequired,
	    coexpressionsAvailable: _propTypes2.default.bool.isRequired,
	    isMultiExperiment: _propTypes2.default.bool.isRequired,
	    isBaseline: _propTypes2.default.bool.isRequired,
	    isDifferential: _propTypes2.default.bool.isRequired

	});

	var filterPropTypes = _propTypes2.default.shape({
	    name: _propTypes2.default.string.isRequired,
	    values: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        disabled: _propTypes2.default.bool.isRequired
	    })).isRequired,
	    valueGroupings: _propTypes2.default.array // Indirectly validated as [string, array of strings] in FilterOption
	});

	var colourAxisPropTypes = _propTypes2.default.shape({
	    unit: _propTypes2.default.string.isRequired,
	    dataClasses: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        color: _propTypes2.default.string.isRequired,
	        from: _propTypes2.default.number.isRequired,
	        to: _propTypes2.default.number.isRequired
	    })).isRequired
	});

	var chartDataPropTypes = _propTypes2.default.shape({
	    heatmapConfig: heatmapConfigPropTypes.isRequired,
	    colourAxis: colourAxisPropTypes,
	    orderings: orderingsPropTypesValidator,
	    heatmapData: heatmapDataPropTypes.isRequired,
	    boxplotData: boxplotDataPropTypes,
	    expressionLevelFilters: filterPropTypes.isRequired,
	    groupingFilters: _propTypes2.default.arrayOf(filterPropTypes)
	});

	exports.heatmapConfigPropTypes = heatmapConfigPropTypes;
	exports.heatmapDataPropTypes = heatmapDataPropTypes;
	exports.chartDataPropTypes = chartDataPropTypes;
	exports.orderingsPropTypesValidator = orderingsPropTypesValidator;
	exports.filterPropTypes = filterPropTypes;
	exports.dataSeriesPropTypes = dataSeriesPropTypes;
	exports.colourAxisPropTypes = colourAxisPropTypes;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(differentialTooltipPropTypes, 'differentialTooltipPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineTooltipPropTypes, 'baselineTooltipPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineExperimentsTooltipPropTypes, 'baselineExperimentsTooltipPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(orderingsPropTypesValidator, 'orderingsPropTypesValidator', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(dataSeriesPropTypes, 'dataSeriesPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(heatmapDataPropTypes, 'heatmapDataPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(boxplotDataPropTypes, 'boxplotDataPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(heatmapConfigPropTypes, 'heatmapConfigPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(filterPropTypes, 'filterPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(colourAxisPropTypes, 'colourAxisPropTypes', 'src/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(chartDataPropTypes, 'chartDataPropTypes', 'src/manipulate/chartDataPropTypes.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(differentialTooltipPropTypes, 'differentialTooltipPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineTooltipPropTypes, 'baselineTooltipPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineExperimentsTooltipPropTypes, 'baselineExperimentsTooltipPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(orderingsPropTypesValidator, 'orderingsPropTypesValidator', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(dataSeriesPropTypes, 'dataSeriesPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(heatmapDataPropTypes, 'heatmapDataPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(boxplotDataPropTypes, 'boxplotDataPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(heatmapConfigPropTypes, 'heatmapConfigPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(filterPropTypes, 'filterPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(colourAxisPropTypes, 'colourAxisPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(chartDataPropTypes, 'chartDataPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/chartDataPropTypes.js');
	}();

	;

/***/ },

/***/ 2361:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.getUnits = exports.isRnaSeqBaseline = exports.isBaseline = exports.isDifferential = exports.isMultiExperiment = exports.experimentPropTypes = undefined;

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var experimentPropTypes = _propTypes2.default.shape({
	    accession: _propTypes2.default.string.isRequired, // Is it worth to enumerate the experiment types?
	    type: _propTypes2.default.string.isRequired,
	    relUrl: _propTypes2.default.string.isRequired,
	    description: _propTypes2.default.string.isRequired,
	    species: _propTypes2.default.string.isRequired,
	    accessKey: _propTypes2.default.string.isRequired
	});

	var isMultiExperiment = function isMultiExperiment(experiment) {
	    return !Boolean(experiment);
	};

	// From ExperimentType.java:
	// RNASEQ_MRNA_BASELINE("rnaseq_mrna_baseline")
	// RNASEQ_MRNA_DIFFERENTIAL("rnaseq_mrna_differential")
	// MICROARRAY_ANY("microarray parent type")
	// MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_mrna_differential")
	// MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_2colour_mrna_differential")
	// MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL(MICROARRAY_ANY, "microarray_1colour_microrna_differential")
	// PROTEOMICS_BASELINE("proteomics_baseline")
	// SINGLE_CELL_RNASEQ_MRNA_BASELINE("scrnaseq_mrna_baseline")
	var isDifferential = function isDifferential(experiment) {
	    return !isMultiExperiment(experiment) && experiment.type.toUpperCase().endsWith('DIFFERENTIAL');
	};

	var isBaseline = function isBaseline(experiment) {
	    return !isMultiExperiment(experiment) && experiment.type.toUpperCase().endsWith('BASELINE');
	};

	var isRnaSeqBaseline = function isRnaSeqBaseline(experiment) {
	    return !isMultiExperiment(experiment) && experiment.type.toUpperCase() === 'RNASEQ_MRNA_BASELINE';
	};

	var getUnits = function getUnits(experiment) {
	    if (isDifferential(experiment)) {
	        return 'Log2-fold change'; // What we use for point.value, we don't use it for display. See Formatters.jsx.
	    } else if (isRnaSeqBaseline(experiment)) {
	        return experiment.description.toUpperCase().includes('FANTOM') ? 'TPM' : 'FPKM';
	    } else {
	        return '';
	    }
	};

	exports.experimentPropTypes = experimentPropTypes;
	exports.isMultiExperiment = isMultiExperiment;
	exports.isDifferential = isDifferential;
	exports.isBaseline = isBaseline;
	exports.isRnaSeqBaseline = isRnaSeqBaseline;
	exports.getUnits = getUnits;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(experimentPropTypes, 'experimentPropTypes', 'src/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isMultiExperiment, 'isMultiExperiment', 'src/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isDifferential, 'isDifferential', 'src/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isBaseline, 'isBaseline', 'src/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isRnaSeqBaseline, 'isRnaSeqBaseline', 'src/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(getUnits, 'getUnits', 'src/load/experimentTypeUtils.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(experimentPropTypes, 'experimentPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isMultiExperiment, 'isMultiExperiment', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isDifferential, 'isDifferential', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isBaseline, 'isBaseline', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(isRnaSeqBaseline, 'isRnaSeqBaseline', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(getUnits, 'getUnits', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/experimentTypeUtils.js');
	}();

	;

/***/ },

/***/ 2362:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _Modal = __webpack_require__(1281);

	var _Modal2 = _interopRequireDefault(_Modal);

	var _Button = __webpack_require__(1202);

	var _Button2 = _interopRequireDefault(_Button);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	var _Disclaimers = __webpack_require__(2363);

	var _Disclaimers2 = _interopRequireDefault(_Disclaimers);

	var _Download = __webpack_require__(2364);

	var _Download2 = _interopRequireDefault(_Download);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var DownloadButton = function (_React$Component) {
	    _inherits(DownloadButton, _React$Component);

	    function DownloadButton(props) {
	        _classCallCheck(this, DownloadButton);

	        var _this = _possibleConstructorReturn(this, (DownloadButton.__proto__ || Object.getPrototypeOf(DownloadButton)).call(this, props));

	        _this.state = { showModal: false };

	        _this.afterDownloadButtonClicked = _this._afterDownloadButtonClicked.bind(_this);
	        _this.commenceDownloadAndCloseModal = _this._commenceDownloadAndCloseModal.bind(_this);
	        _this.closeModal = _this._closeModal.bind(_this);
	        return _this;
	    }

	    _createClass(DownloadButton, [{
	        key: '_closeModal',
	        value: function _closeModal() {
	            this.setState({ showModal: false });
	        }
	    }, {
	        key: '_disclaimer',
	        value: function _disclaimer() {
	            return this.props.disclaimer && _Disclaimers2.default[this.props.disclaimer] || { title: null, content: null };
	        }
	    }, {
	        key: '_afterDownloadButtonClicked',
	        value: function _afterDownloadButtonClicked() {
	            if (!this._disclaimer().title && !this._disclaimer().content) {
	                this._commenceDownload();
	            } else {
	                this.setState({ showModal: true });
	            }
	        }
	    }, {
	        key: '_commenceDownload',
	        value: function _commenceDownload() {
	            (0, _Download2.default)(this.props.download);
	            typeof window.ga === 'function' && window.ga('atlas-highcharts-widget.send', 'event', 'HeatmapHighcharts', 'downloadData');
	        }
	    }, {
	        key: '_commenceDownloadAndCloseModal',
	        value: function _commenceDownloadAndCloseModal() {
	            this._commenceDownload();
	            this.closeModal();
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            return _react2.default.createElement('a', { onClick: this.afterDownloadButtonClicked }, _react2.default.createElement(_Button2.default, { bsSize: 'small',
	                style: { textTransform: 'unset', letterSpacing: 'unset', height: 'unset' } }, _react2.default.createElement(_Glyphicon2.default, { glyph: 'download-alt' }), ' Download table content'), _react2.default.createElement(_Modal2.default, { show: this.state.showModal, onHide: this.closeModal }, _react2.default.createElement(_Modal2.default.Header, { closeButton: true }, _react2.default.createElement(_Modal2.default.Title, null, this._disclaimer().title)), _react2.default.createElement(_Modal2.default.Body, null, this._disclaimer().content), _react2.default.createElement(_Modal2.default.Footer, null, _react2.default.createElement(_Button2.default, { onClick: this._closeModal }, 'Close'), _react2.default.createElement(_Button2.default, { bsStyle: 'primary', onClick: this.commenceDownloadAndCloseModal }, 'Continue downloading'))));
	        }
	    }]);

	    return DownloadButton;
	}(_react2.default.Component);

	DownloadButton.propTypes = {
	    download: _propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        descriptionLines: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	        heatmapData: _chartDataPropTypes.heatmapDataPropTypes
	    }),
	    disclaimer: _propTypes2.default.string.isRequired
	};

	var _default = DownloadButton;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(DownloadButton, 'DownloadButton', 'src/manipulate/controls/download-button/DownloadButton.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/download-button/DownloadButton.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_Modal2, "_Modal2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_Button2, "_Button2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_Disclaimers2, "_Disclaimers2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_Download2, "_Download2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(DownloadButton, "DownloadButton", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/DownloadButton.js");
	}();

	;

/***/ },

/***/ 2363:
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var BlueprintText = {
	    title: "The Blueprint project Data Reuse statement",
	    content: _react2.default.createElement("div", null, _react2.default.createElement("p", null, "This document refers to the reuse of data generated by the EC funded FP7 High Impact Project, Blueprint."), _react2.default.createElement("p", null, "Blueprint regularly released analysis results via its ftp site and makes the raw sequence data available through the sequence archives at the EMBL-EBI. Much Blueprint data is generated from samples whose data must be released through a managed access process. For these data sets external users must apply for permission to access the data from the European Genome-phenome Archive (EGA) through the Blueprint Data Access Committee."), _react2.default.createElement("p", null, "The Blueprint consortium expects this data to be valuable to other researchers and in keeping with Fort Lauderdale principles data users may use the data for many studies, but are expected to allow the data producers to make the first presentations and to publish the first paper with global analyses of the data."), _react2.default.createElement("h4", null, "Global analyses of Project data"), _react2.default.createElement("p", null, "Blueprint plans to publish global analyses of the sequencing data, epigenetic marks, expression levels and variation both in the context of normal hematopoietic cells and of those neoplastic and non-neoplastic diseases studied within the consortium. Talks, posters, and papers on all such analyses are to be published first by the Blueprint project, by approved presenters on behalf of the Project, with the Project as author. When the first major Project paper on these analyses is published, then researchers inside and outside the Project are free to present and publish using the Project data for these and other analyses."), _react2.default.createElement("h4", null, "Large-scale analyses of Project data"), _react2.default.createElement("p", null, "Groups within the Project may make presentations and publish papers on more extensive analyses of topics to be included in the main analysis presentations and papers, coincident with the main project analysis presentations and papers. The major points would be included in the main Project presentations and papers, but these additional presentations and papers allow more focused discussion of methods and results. The author list would include the Consortium."), _react2.default.createElement("h4", null, "Methods development using Project data"), _react2.default.createElement("p", null, "Researchers who have used small amounts of Project data (&lt= one chromosome) may present methods development posters, talks, and papers that include these data prior to the first major Project paper, without needing Project approval or authorship, although the Project should be acknowledged. Methods presentations or papers on global analyses or analyses using large amounts of Project data, on topics that the Consortium plans to examine, would be similar to large-scale analyses of Project data: researchers within the Project may make presentations or submit papers at the same time as the main Project presentations and papers, and others could do so after the Project publishes the first major analysis paper."), _react2.default.createElement("h4", null, "Disease studies using Project data"), _react2.default.createElement("p", null, "Researchers may present and publish on use of Project data in specific chromosome regions (that are not of general interest) or as summaries (such as number of differentially expressed genes in cell types assayed by Blueprint) for studies on diseases not studied by BLUEPRINT without Project approval, prior to the first major Project paper being published. The Project should not be listed as an author."), _react2.default.createElement("h4", null, "Authors who use data from the project must acknowledge Blueprint using the following wording"), _react2.default.createElement("p", null, "This study makes use of data generated by the Blueprint Consortium. A full list of the investigators who contributed to the generation of the data is available from", _react2.default.createElement("a", { href: "http://www.blueprint-epigenome.eu" }, "www.blueprint-epigenome.eu"), ". Funding for the project was provided by the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement no 282510 \u2013 BLUEPRINT."))
	};

	var ZebrafishText = {
	    title: "Data Reuse statement",
	    content: _react2.default.createElement("div", null, _react2.default.createElement("p", null, "This is a pre-publication release in accordance with ", _react2.default.createElement("a", { href: "http://www.sanger.ac.uk/datasharing/" }, "the Fort Lauderdale Agreement "), ". Feel free to search and download data on your genes of interest."), _react2.default.createElement("p", null, "Equally, you can use the dataset to show developmental expression profiles for specific genes in your publications."), _react2.default.createElement("p", null, "However, we ask that you refrain from publishing larger scale or genome-wide analyses of this dataset for 12 months from the time of deposition in Expression Atlas or until we have published our transcriptional time-course paper, whichever comes first."), _react2.default.createElement("p", null, "For citations in publications before the paper is out please use this link to the Expression Atlas site (", _react2.default.createElement("a", { href: "https://www.ebi.ac.uk/gxa/experiments/E-ERAD-475" }, "http://www.ebi.ac.uk/gxa/experiments/E-ERAD-475"), ") and acknowledge us: \u201CWe would like to thank the Busch-Nentwich lab for providing RNA-seq data.\u201D"))
	};

	var _default = { "fortLauderdale": BlueprintText, "zebrafish": ZebrafishText };
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(BlueprintText, "BlueprintText", "src/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(ZebrafishText, "ZebrafishText", "src/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(_default, "default", "src/manipulate/controls/download-button/Disclaimers.js");
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(BlueprintText, "BlueprintText", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(ZebrafishText, "ZebrafishText", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Disclaimers.js");
	}();

	;

/***/ },

/***/ 2364:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _range2 = __webpack_require__(689);

	var _range3 = _interopRequireDefault(_range2);

	var _downloadjs = __webpack_require__(693);

	var _downloadjs2 = _interopRequireDefault(_downloadjs);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	var heatmapDataIntoLinesOfData = function heatmapDataIntoLinesOfData(heatmapData) {

	    var heatmapDataAsMatrix = (0, _range3.default)(heatmapData.yAxisCategories.length).map(function (y) {
	        return (0, _range3.default)(heatmapData.xAxisCategories.length).map(function (x) {
	            return 'NA';
	        });
	    });

	    heatmapData.dataSeries.forEach(function (series) {
	        series.data.forEach(function (point) {
	            heatmapDataAsMatrix[point.y][point.x] = point.value;
	        });
	    });

	    return [[''].concat(heatmapData.xAxisCategories.map(function (header) {
	        return header.label;
	    }))].concat(heatmapData.yAxisCategories.map(function (rowLabel, ix) {
	        return [].concat.apply([rowLabel.label], heatmapDataAsMatrix[ix]);
	    })).map(function (line) {
	        return line.join('\t');
	    });
	};

	var CommenceDownload = function CommenceDownload(_ref) {
	    var name = _ref.name,
	        descriptionLines = _ref.descriptionLines,
	        heatmapData = _ref.heatmapData;

	    (0, _downloadjs2.default)(new Blob(['# Downloaded from: ' + window.location.href, '# Timestamp: ' + new Date().toISOString()].concat(_toConsumableArray(descriptionLines.map(function (line) {
	        return '# ' + line;
	    })), _toConsumableArray(heatmapDataIntoLinesOfData(heatmapData))).map(function (line) {
	        return line + '\n';
	    })), name.replace(/ +/, '_') + '.tsv', 'text/tsv');
	};

	var _default = CommenceDownload;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(heatmapDataIntoLinesOfData, 'heatmapDataIntoLinesOfData', 'src/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(CommenceDownload, 'CommenceDownload', 'src/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/controls/download-button/Download.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_range3, '_range3', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_downloadjs2, '_downloadjs2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_toConsumableArray, '_toConsumableArray', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(heatmapDataIntoLinesOfData, 'heatmapDataIntoLinesOfData', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(CommenceDownload, 'CommenceDownload', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/controls/download-button/Download.js');
	}();

	;

/***/ },

/***/ 2365:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _reactHighcharts = __webpack_require__(1347);

	var _reactHighcharts2 = _interopRequireDefault(_reactHighcharts);

	var _heatmap = __webpack_require__(2366);

	var _heatmap2 = _interopRequireDefault(_heatmap);

	var _highchartsCustomEvents = __webpack_require__(698);

	var _highchartsCustomEvents2 = _interopRequireDefault(_highchartsCustomEvents);

	var _objectHash = __webpack_require__(699);

	var _objectHash2 = _interopRequireDefault(_objectHash);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var Highcharts = _reactHighcharts2.default.Highcharts;
	(0, _heatmap2.default)(Highcharts);
	(0, _highchartsCustomEvents2.default)(Highcharts);

	// Custom Events default behaviour disables context menu on right-click, we bring it back
	window.oncontextmenu = function () {
	    return true;
	};

	var HeatmapCanvas = function (_React$Component) {
	    _inherits(HeatmapCanvas, _React$Component);

	    function HeatmapCanvas(props) {
	        _classCallCheck(this, HeatmapCanvas);

	        return _possibleConstructorReturn(this, (HeatmapCanvas.__proto__ || Object.getPrototypeOf(HeatmapCanvas)).call(this, props));
	    }

	    _createClass(HeatmapCanvas, [{
	        key: 'shouldComponentUpdate',
	        value: function shouldComponentUpdate(nextProps) {
	            // Callback that does setState fails: https://github.com/kirjs/react-highcharts/issues/245
	            // Don’t call render again after zoom happens
	            return _objectHash2.default.MD5([nextProps.heatmapData, nextProps.events.onClick]) !== _objectHash2.default.MD5([this.props.heatmapData, this.props.events.onClick]);
	        }
	    }, {
	        key: '_countColumns',
	        value: function _countColumns() {
	            return this.props.heatmapData.xAxisCategories.length;
	        }
	    }, {
	        key: '_getAdjustedMarginRight',
	        value: function _getAdjustedMarginRight() {
	            // TODO Should add extra margin if labels are slanted and last ones (3 or so?) are very long. See reference_experiment_single_gene.html
	            var initialMarginRight = 60;
	            return initialMarginRight * (1 + 10 / Math.pow(1 + this._countColumns(), 2));
	        }
	    }, {
	        key: '_getAdjustedMarginTop',
	        value: function _getAdjustedMarginTop() {
	            var longestColumnLabelLength = Math.max.apply(Math, _toConsumableArray(this.props.heatmapData.xAxisCategories.map(function (category) {
	                return category.label.length;
	            })));

	            // Minimum margins when labels aren’t tilted, -45° and -90° respectively see labels.autoRotation below
	            var horizontalLabelsMarginTop = 30,
	                tiltedLabelsMarginTop = 100,
	                verticalLabelsMarginTop = 200;

	            // TODO To know if the labels are actually rotated we must take into account the width of the chart and div

	            if (this._countColumns() < 10) {
	                return Math.max(horizontalLabelsMarginTop, Math.round(longestColumnLabelLength));
	            } else if (this._countColumns() < 80) {
	                return Math.max(tiltedLabelsMarginTop, Math.round(longestColumnLabelLength * 3.85));
	            } else {
	                return Math.max(verticalLabelsMarginTop, Math.round(longestColumnLabelLength * 5.5));
	            }
	        }
	    }, {
	        key: '_getAdjustedHeight',
	        value: function _getAdjustedHeight(marginTop, marginBottom) {
	            var rowsCount = this.props.heatmapData.yAxisCategories.length;
	            var rowHeight = 30;
	            return rowsCount * rowHeight + marginTop + marginBottom;
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            var _this2 = this;

	            // TODO Should the margins be recalculated when the window is resized?
	            var marginBottom = 10;
	            var marginTop = this._getAdjustedMarginTop();
	            var marginRight = this._getAdjustedMarginRight();
	            var height = this._getAdjustedHeight(marginTop, marginBottom);

	            var _props = this.props,
	                cellTooltipFormatter = _props.cellTooltipFormatter,
	                xAxisFormatter = _props.xAxisFormatter,
	                yAxisFormatter = _props.yAxisFormatter,
	                events = _props.events,
	                onZoom = _props.onZoom;

	            var highchartsConfig = {
	                chart: {
	                    marginTop: marginTop,
	                    marginBottom: marginBottom,
	                    marginRight: marginRight,
	                    height: height,
	                    type: 'heatmap',
	                    spacingTop: 0,
	                    plotBorderWidth: 1,
	                    events: {
	                        handleGxaAnatomogramTissueMouseEnter: function handleGxaAnatomogramTissueMouseEnter(e) {
	                            Highcharts.each(this.series, function (series) {
	                                Highcharts.each(series.points, function (point) {
	                                    if (point.series.xAxis.categories[point.x].id === e.svgPathId) {
	                                        point.select(true, true);
	                                    }
	                                });
	                            });
	                        },
	                        handleGxaAnatomogramTissueMouseLeave: function handleGxaAnatomogramTissueMouseLeave(e) {
	                            var points = this.getSelectedPoints();
	                            if (points.length > 0) {
	                                Highcharts.each(points, function (point) {
	                                    point.select(false);
	                                });
	                            }
	                        }
	                    },
	                    zoomType: 'x'
	                },

	                plotOptions: {
	                    heatmap: {
	                        turboThreshold: 0
	                    },

	                    series: {
	                        cursor: events.onClick ? 'pointer' : undefined,
	                        point: {
	                            events: {
	                                click: events.onClick ? function () {
	                                    events.onClick(this.x, this.y);
	                                } : function () {}
	                            }
	                        },

	                        states: {
	                            hover: {
	                                color: '#eeec38' //#edab12 color cell on mouse over
	                            },
	                            select: {
	                                color: '#eeec38'
	                            }
	                        }
	                    }
	                },

	                credits: {
	                    enabled: false
	                },

	                legend: {
	                    enabled: false
	                },

	                title: null,

	                colorAxis: this.props.colourAxis,

	                xAxis: { //assay groups, contrasts, or factors across experiments
	                    tickLength: 5,
	                    tickColor: 'rgb(192, 192, 192)',
	                    lineColor: 'rgb(192, 192, 192)',
	                    labels: {
	                        style: this.props.xAxisStyle,
	                        // Events in labels enabled by 'highcharts-custom-events'
	                        events: {
	                            mouseover: function mouseover() {
	                                events.onHoverColumn(this.value);
	                            },
	                            mouseout: function mouseout() {
	                                events.onHoverOff();
	                            }
	                        },
	                        autoRotation: [-45, -90],
	                        formatter: function formatter() {
	                            return xAxisFormatter(this.value);
	                        }
	                    },

	                    opposite: 'true',
	                    categories: this.props.heatmapData.xAxisCategories,
	                    min: 0,
	                    max: this._countColumns() - 1,

	                    events: {
	                        setExtremes: function setExtremes(event) {
	                            onZoom(event.min !== undefined && event.max !== undefined);
	                        }
	                    }
	                },

	                yAxis: { //experiments or bioentities
	                    useHTML: true,
	                    reversed: true,
	                    labels: {
	                        style: this.props.yAxisStyle,
	                        events: {
	                            mouseover: function mouseover() {
	                                events.onHoverRow(this.value);
	                            },
	                            mouseout: function mouseout() {
	                                events.onHoverOff();
	                            }
	                        },
	                        formatter: function formatter() {
	                            return yAxisFormatter(this.value);
	                        }
	                    },

	                    categories: this.props.heatmapData.yAxisCategories,
	                    title: null,
	                    gridLineWidth: 0,
	                    minorGridLineWidth: 0,
	                    endOnTick: false
	                },

	                tooltip: {
	                    useHTML: true,
	                    shared: false,
	                    borderRadius: 0,
	                    borderWidth: 0,
	                    shadow: false,
	                    enabled: true,
	                    backgroundColor: 'none',
	                    formatter: function formatter() {
	                        return cellTooltipFormatter(this.series, this.point);
	                    }
	                },

	                series: this.props.heatmapData.dataSeries.map(function (e) {
	                    return {
	                        name: e.info.name,
	                        color: e.info.colour,
	                        borderWidth: _this2._countColumns() > 200 ? 0 : 1,
	                        borderColor: 'white',
	                        data: e.data
	                    };
	                })
	            };

	            var maxWidthFraction = this._countColumns() > 6 ? 1 : Math.max(0.5, 1 - Math.exp(-(1 + 0.05 * Math.pow(1 + this._countColumns(), 2))));
	            return _react2.default.createElement('div', { style: { maxWidth: maxWidthFraction * 100 + '%', minWidth: '600px' } }, _react2.default.createElement(_reactHighcharts2.default, { ref: function ref(_ref) {
	                    return _this2.highchartsRef = _ref;
	                }, config: highchartsConfig }));
	        }
	    }, {
	        key: 'componentWillReceiveProps',
	        value: function componentWillReceiveProps(nextProps) {
	            var chart = this.highchartsRef.getChart();
	            var forEachXNotInYsEmit = function forEachXNotInYsEmit(xs, ys, eventName) {
	                xs.filter(function (id) {
	                    return ys.indexOf(id) === -1;
	                }).filter(function (id, ix, self) {
	                    return ix === self.indexOf(id);
	                }).forEach(function (id) {
	                    Highcharts.fireEvent(chart, eventName, { svgPathId: id });
	                });
	            };
	            forEachXNotInYsEmit(nextProps.ontologyIdsToHighlight, this.props.ontologyIdsToHighlight, 'handleGxaAnatomogramTissueMouseEnter');
	            forEachXNotInYsEmit(this.props.ontologyIdsToHighlight, nextProps.ontologyIdsToHighlight, 'handleGxaAnatomogramTissueMouseLeave');
	        }
	    }]);

	    return HeatmapCanvas;
	}(_react2.default.Component);

	HeatmapCanvas.propTypes = {
	    heatmapData: _chartDataPropTypes.heatmapDataPropTypes.isRequired,
	    colourAxis: _chartDataPropTypes.colourAxisPropTypes, // Only for experiment heatmap
	    cellTooltipFormatter: _propTypes2.default.func.isRequired,
	    xAxisFormatter: _propTypes2.default.func.isRequired,
	    xAxisStyle: _propTypes2.default.object.isRequired,
	    yAxisFormatter: _propTypes2.default.func.isRequired,
	    yAxisStyle: _propTypes2.default.object.isRequired,
	    events: _propTypes2.default.shape({
	        onHoverRow: _propTypes2.default.func.isRequired,
	        onHoverColumn: _propTypes2.default.func.isRequired,
	        onHoverOff: _propTypes2.default.func.isRequired,
	        onClick: _propTypes2.default.func
	    }),
	    onZoom: _propTypes2.default.func.isRequired
	};

	var _default = HeatmapCanvas;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(Highcharts, 'Highcharts', 'src/show/HeatmapCanvas.js');

	    __REACT_HOT_LOADER__.register(HeatmapCanvas, 'HeatmapCanvas', 'src/show/HeatmapCanvas.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/show/HeatmapCanvas.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_reactHighcharts2, "_reactHighcharts2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_heatmap2, "_heatmap2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_highchartsCustomEvents2, "_highchartsCustomEvents2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_objectHash2, "_objectHash2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_toConsumableArray, "_toConsumableArray", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(Highcharts, "Highcharts", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(HeatmapCanvas, "HeatmapCanvas", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/HeatmapCanvas.js");
	}();

	;

/***/ },

/***/ 2366:
697,

/***/ 2367:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _server = __webpack_require__(762);

	var _server2 = _interopRequireDefault(_server);

	var _HeatmapCellTooltip = __webpack_require__(2368);

	var _HeatmapCellTooltip2 = _interopRequireDefault(_HeatmapCellTooltip);

	var _he = __webpack_require__(769);

	var _he2 = _interopRequireDefault(_he);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var reactToHtml = function reactToHtml(component) {
	    return _he2.default.decode(_server2.default.renderToStaticMarkup(component));
	};

	var _default = function _default(config) {
	    return function (series, point) {
	        var o = {
	            colour: point.color,
	            xLabel: point.options.info.xLabel || series.xAxis.categories[point.x].label,
	            xProperties: series.xAxis.categories[point.x].info.tooltip.properties,
	            yLabel: series.yAxis.categories[point.y].label,
	            value: point.value,
	            replicates: series.xAxis.categories[point.x].info.tooltip.replicates || undefined
	        };

	        Object.keys(point.options.info).forEach(function (key) {
	            return o[key] = point.options.info[key];
	        });

	        return reactToHtml(_react2.default.createElement(_HeatmapCellTooltip2.default, _extends({}, o, { config: config })));
	    };
	};

	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(reactToHtml, 'reactToHtml', 'src/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/formatters/heatmapCellTooltipFormatter.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_server2, '_server2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_HeatmapCellTooltip2, '_HeatmapCellTooltip2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_he2, '_he2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(reactToHtml, 'reactToHtml', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/heatmapCellTooltipFormatter.js');
	}();

	;

/***/ },

/***/ 2368:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _expressionAtlasNumberFormat = __webpack_require__(2369);

	var _expressionAtlasNumberFormat2 = _interopRequireDefault(_expressionAtlasNumberFormat);

	function _interopRequireDefault(obj) {
	  return obj && obj.__esModule ? obj : { default: obj };
	}

	var scientificNotation = function scientificNotation(value) {
	  return _expressionAtlasNumberFormat2.default.formatScientificNotation(value, 4, { fontWeight: 'bold' });
	};

	var roundTStat = function roundTStat(n) {
	  return n ? +n.toFixed(4) : "";
	};

	var _tinySquare = function _tinySquare(colour) {
	  return _react2.default.createElement('span', { key: 'Tiny ' + colour + ' square',
	    style: {
	      border: '1px rgb(192, 192, 192) solid',
	      marginRight: '0.25rem',
	      width: '0.6rem',
	      height: '0.6rem',
	      display: 'inline-block',
	      backgroundColor: colour
	    }
	  });
	};

	var _info = function _info(text) {
	  return _react2.default.createElement('div', null, _react2.default.createElement('i', null, text));
	};

	var _div = function _div(name, value, format) {
	  return name && value ? _react2.default.createElement('div', { key: name + ' ' + value }, name, ": ", value.length > 50 ? _react2.default.createElement('br', null) : null, (format || _bold)(value)) : null;
	};

	var _span = function _span(name, value) {
	  return _react2.default.createElement('span', { key: name + ' ' + value }, name, ": ", value.length > 50 ? _react2.default.createElement('br', null) : null, _bold(value));
	};

	var _bold = function _bold(value) {
	  return _react2.default.createElement('b', null, value);
	};

	var yInfo = function yInfo(_ref) {
	  var config = _ref.config,
	      yLabel = _ref.yLabel;
	  return _div(config.yAxisLegendName, yLabel);
	};

	var xInfo = function xInfo(_ref2) {
	  var xAxisLegendName = _ref2.xAxisLegendName,
	      config = _ref2.config,
	      xLabel = _ref2.xLabel;
	  return _div(xAxisLegendName || config.xAxisLegendName, xLabel);
	};

	var _comparisonDiv = function _comparisonDiv(name, v1, v2, format) {
	  return name && v1 && v2 ? _react2.default.createElement('div', { key: name + ' ' + v1 + ' ' + v2 }, name + ': ', v1.length + v2.length > 50 ? _react2.default.createElement('br', null) : null, (format || _bold)(v1), _react2.default.createElement('i', { style: { margin: "0.25rem" } }, 'vs'), (format || _bold)(v2)) : null;
	};

	var prettyName = function prettyName(name) {
	  return name.toLowerCase().replace(/\w\S*/, function (txt) {
	    return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
	  });
	};

	var xPropertiesBaselineList = function xPropertiesBaselineList(_ref3) {
	  var xProperties = _ref3.xProperties;
	  return xProperties.filter(function (property) {
	    return property.contrastPropertyType !== "SAMPLE" // would fail with showing too much stuff which isn't catastrophic
	    ;
	  }).map(function (property) {
	    return _div(prettyName(property.propertyName), property.testValue);
	  });
	};

	var xPropertiesDifferentialList = function xPropertiesDifferentialList(_ref4) {
	  var xProperties = _ref4.xProperties;
	  return xProperties.filter(function (property) {
	    return property.testValue !== property.referenceValue;
	  }).map(function (property) {
	    return _comparisonDiv(prettyName(property.propertyName), property.testValue, property.referenceValue);
	  });
	};

	var differentialNumbers = function differentialNumbers(_ref5) {
	  var colour = _ref5.colour,
	      foldChange = _ref5.foldChange,
	      pValue = _ref5.pValue,
	      tStat = _ref5.tStat;
	  return [_react2.default.createElement('div', { key: '' }, _tinySquare(colour), _span(_react2.default.createElement('span', null, 'Log', _react2.default.createElement('sub', null, '2'), '-fold change'), foldChange)), _div('Adjusted p-value', pValue, scientificNotation), _div('T-statistic', roundTStat(tStat))];
	};

	var baselineNumbers = function baselineNumbers(_ref6) {
	  var colour = _ref6.colour,
	      value = _ref6.value,
	      unit = _ref6.unit,
	      replicates = _ref6.replicates;
	  return [_tinySquare(colour), _span('Expression level', value ? value + ' ' + unit : 'Below cutoff')].concat(replicates ? _div('Number of biological replicates', replicates) : []);
	};

	var HeatmapCellTooltip = function HeatmapCellTooltip(props) {
	  return _react2.default.createElement('div', { style: {
	      whiteSpace: 'pre', background: 'rgba(255, 255, 255, .85)',
	      padding: '5px', border: '1px solid darkgray',
	      borderRadius: '3px', boxShadow: '2px 2px 2px darkslategray' } }, yInfo(props), props.config.isMultiExperiment ? xInfo(props) : props.config.isDifferential ? xPropertiesDifferentialList(props) : xPropertiesBaselineList(props), props.config.isDifferential ? differentialNumbers(props) : baselineNumbers(props));
	};

	HeatmapCellTooltip.propTypes = {
	  config: _propTypes2.default.shape({
	    isDifferential: _propTypes2.default.bool.isRequired,
	    isMultiExperiment: _propTypes2.default.bool.isRequired,
	    xAxisLegendName: _propTypes2.default.string.isRequired,
	    yAxisLegendName: _propTypes2.default.string.isRequired
	  }).isRequired,
	  colour: _propTypes2.default.string.isRequired,
	  xLabel: _propTypes2.default.string.isRequired,
	  xProperties: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	    propertyName: _propTypes2.default.string.isRequired,
	    referenceValue: _propTypes2.default.string, // present iff differential
	    testValue: _propTypes2.default.string.isRequired
	  })),
	  yLabel: _propTypes2.default.string.isRequired,
	  value: _propTypes2.default.number.isRequired,
	  unit: _propTypes2.default.string.isRequired,
	  replicates: _propTypes2.default.number,
	  foldChange: _propTypes2.default.number,
	  pValue: _propTypes2.default.number,
	  tStat: _propTypes2.default.number,
	  xAxisLegendName: _propTypes2.default.string
	};

	var _default = HeatmapCellTooltip;
	exports.default = _default;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(scientificNotation, 'scientificNotation', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(roundTStat, 'roundTStat', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_tinySquare, '_tinySquare', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_info, '_info', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_div, '_div', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_span, '_span', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_bold, '_bold', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(yInfo, 'yInfo', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xInfo, 'xInfo', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_comparisonDiv, '_comparisonDiv', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(prettyName, 'prettyName', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xPropertiesBaselineList, 'xPropertiesBaselineList', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xPropertiesDifferentialList, 'xPropertiesDifferentialList', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(differentialNumbers, 'differentialNumbers', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(baselineNumbers, 'baselineNumbers', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(HeatmapCellTooltip, 'HeatmapCellTooltip', 'src/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/formatters/HeatmapCellTooltip.js');
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_expressionAtlasNumberFormat2, '_expressionAtlasNumberFormat2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(scientificNotation, 'scientificNotation', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(roundTStat, 'roundTStat', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_tinySquare, '_tinySquare', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_info, '_info', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_div, '_div', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_span, '_span', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_bold, '_bold', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(yInfo, 'yInfo', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xInfo, 'xInfo', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_comparisonDiv, '_comparisonDiv', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(prettyName, 'prettyName', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xPropertiesBaselineList, 'xPropertiesBaselineList', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(xPropertiesDifferentialList, 'xPropertiesDifferentialList', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(differentialNumbers, 'differentialNumbers', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(baselineNumbers, 'baselineNumbers', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(HeatmapCellTooltip, 'HeatmapCellTooltip', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');

	  __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/HeatmapCellTooltip.js');
	}();

	;

/***/ },

/***/ 2369:
[2891, 2370],

/***/ 2370:
768,

/***/ 2371:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _server = __webpack_require__(762);

	var _server2 = _interopRequireDefault(_server);

	var _he = __webpack_require__(769);

	var _he2 = _interopRequireDefault(_he);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var reactToHtml = function reactToHtml(component) {
	    return _he2.default.decode(_server2.default.renderToStaticMarkup(component));
	};

	var YAxisLabel = function YAxisLabel(props) {
	    var geneNameWithLink = _react2.default.createElement('a', { href: props.config.outProxy + props.url }, props.labelText);

	    return props.extra ? _react2.default.createElement('span', null, geneNameWithLink, _react2.default.createElement('em', { style: { color: "black" } }, '\t' + props.extra)) : _react2.default.createElement('span', null, geneNameWithLink);
	};

	YAxisLabel.propTypes = {
	    config: _propTypes2.default.shape({
	        atlasUrl: _propTypes2.default.string.isRequired,
	        outProxy: _propTypes2.default.string.isRequired,
	        isMultiExperiment: _propTypes2.default.bool.isRequired,
	        isDifferential: _propTypes2.default.bool.isRequired,
	        experiment: _propTypes2.default.shape({
	            accession: _propTypes2.default.string.isRequired,
	            type: _propTypes2.default.string.isRequired,
	            relUrl: _propTypes2.default.string.isRequired,
	            description: _propTypes2.default.string.isRequired,
	            species: _propTypes2.default.string.isRequired
	        })
	    }).isRequired,
	    labelText: _propTypes2.default.string.isRequired,
	    resourceId: _propTypes2.default.string.isRequired,
	    url: _propTypes2.default.string.isRequired,
	    extra: _propTypes2.default.string
	};

	var _default = function _default(config) {
	    return {
	        xAxisFormatter: function xAxisFormatter(value) {
	            return value.label;
	        },
	        xAxisStyle: {
	            fontSize: config.isDifferential ? '9px' : 'smaller',
	            cursor: 'default',
	            textOverflow: config.experiment ? 'none' : 'ellipsis',
	            whiteSpace: config.isDifferential ? 'normal' : 'nowrap'
	        },

	        yAxisFormatter: function yAxisFormatter(value) {
	            return reactToHtml(_react2.default.createElement(YAxisLabel, { config: config,
	                labelText: value.label,
	                resourceId: value.id,
	                url: value.info.url,
	                extra: value.info.designElement || ''
	            }));
	        },
	        yAxisStyle: {
	            fontSize: config.isMultiExperiment ? 'smaller' : 'small',
	            color: '#148ff3'
	        }
	    };
	};

	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(reactToHtml, 'reactToHtml', 'src/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(YAxisLabel, 'YAxisLabel', 'src/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/formatters/axesFormatters.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_server2, '_server2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_he2, '_he2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(reactToHtml, 'reactToHtml', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(YAxisLabel, 'YAxisLabel', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/formatters/axesFormatters.js');
	}();

	;

/***/ },

/***/ 2372:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.GradientLegend = exports.DataSeriesLegend = undefined;

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _DataSeriesHeatmapLegend = __webpack_require__(2373);

	var _DataSeriesHeatmapLegend2 = _interopRequireDefault(_DataSeriesHeatmapLegend);

	var _GradientHeatmapLegend = __webpack_require__(2376);

	var _GradientHeatmapLegend2 = _interopRequireDefault(_GradientHeatmapLegend);

	var _chartDataPropTypes = __webpack_require__(2360);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	var DataSeriesLegend = function DataSeriesLegend(_ref) {
	    var dataSeries = _ref.dataSeries,
	        selectedExpressionLevelFilters = _ref.selectedExpressionLevelFilters;

	    var legendItems = dataSeries.map(function (series) {
	        return {
	            key: series.info.name,
	            name: series.info.name,
	            colour: series.info.colour,
	            on: selectedExpressionLevelFilters.includes(series.info.name)
	        };
	    });

	    return _react2.default.createElement(_DataSeriesHeatmapLegend2.default, { legendItems: legendItems });
	};

	DataSeriesLegend.propTypes = {
	    dataSeries: _chartDataPropTypes.dataSeriesPropTypes.isRequired,
	    selectedExpressionLevelFilters: _propTypes2.default.array
	};

	var GradientLegend = function GradientLegend(_ref2) {
	    var colourAxis = _ref2.colourAxis;

	    var minDownRegulatedValue = Math.min.apply(Math, _toConsumableArray(colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.from <= 0;
	    }).map(function (dataClass) {
	        return dataClass.from;
	    })));
	    var maxDownRegulatedValue = Math.max.apply(Math, _toConsumableArray(colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.to <= 0;
	    }).map(function (dataClass) {
	        return dataClass.to;
	    })));
	    var downRegulatedColours = colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.from <= 0;
	    }).map(function (dataClass) {
	        return dataClass.color;
	    });

	    var minUpRegulatedValue = Math.min.apply(Math, _toConsumableArray(colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.from >= 0;
	    }).map(function (dataClass) {
	        return dataClass.from;
	    })));
	    var maxUpRegulatedValue = Math.max.apply(Math, _toConsumableArray(colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.to >= 0;
	    }).map(function (dataClass) {
	        return dataClass.to;
	    })));
	    var upRegulatedColours = colourAxis.dataClasses.filter(function (dataClass) {
	        return dataClass.from >= 0;
	    }).map(function (dataClass) {
	        return dataClass.color;
	    });

	    return _react2.default.createElement(_GradientHeatmapLegend2.default, {
	        gradients: [{
	            fromValue: minDownRegulatedValue,
	            toValue: maxDownRegulatedValue,
	            colours: downRegulatedColours
	        }, {
	            fromValue: minUpRegulatedValue,
	            toValue: maxUpRegulatedValue,
	            colours: upRegulatedColours
	        }],
	        unit: colourAxis.unit
	    });
	};

	GradientLegend.propTypes = {
	    heatmapConfig: _chartDataPropTypes.heatmapConfigPropTypes.isRequired,
	    colourAxis: _chartDataPropTypes.colourAxisPropTypes.isRequired
	};

	exports.DataSeriesLegend = DataSeriesLegend;
	exports.GradientLegend = GradientLegend;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(DataSeriesLegend, 'DataSeriesLegend', 'src/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(GradientLegend, 'GradientLegend', 'src/manipulate/heatmap-legend/Main.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_DataSeriesHeatmapLegend2, '_DataSeriesHeatmapLegend2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_GradientHeatmapLegend2, '_GradientHeatmapLegend2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_toConsumableArray, '_toConsumableArray', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(DataSeriesLegend, 'DataSeriesLegend', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(GradientLegend, 'GradientLegend', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/Main.js');
	}();

	;

/***/ },

/***/ 2373:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	__webpack_require__(2374);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var DataSeriesHeatmapLegendBox = function DataSeriesHeatmapLegendBox(props) {
	    return _react2.default.createElement('div', { className: 'legend-item' + (props.on ? '' : ' legend-item-off') }, _react2.default.createElement('div', { style: { background: props.colour }, className: 'legend-rectangle' }), _react2.default.createElement('span', { style: { verticalAlign: 'middle' } }, props.name));
	};

	DataSeriesHeatmapLegendBox.propTypes = {
	    name: _propTypes2.default.string.isRequired,
	    colour: _propTypes2.default.string.isRequired,
	    on: _propTypes2.default.bool.isRequired
	};

	var DataSeriesHeatmapLegend = function DataSeriesHeatmapLegend(props) {
	    return _react2.default.createElement('div', { className: 'gxaHeatmapLegend' }, props.legendItems.map(function (legendItemProps) {
	        return _react2.default.createElement(DataSeriesHeatmapLegendBox, legendItemProps);
	    }), _react2.default.createElement('div', { className: 'legend-item' }, _react2.default.createElement('span', { className: 'icon icon-generic gxaInfoIcon',
	        'data-icon': 'i', 'data-toggle': 'tooltip', 'data-placement': 'bottom',
	        title: 'Baseline expression levels in RNA-seq experiments are in FPKM or TPM. Low: 0.5-10, Medium: 11-1,000,  High: >1,000. Proteomics expression levels are mapped to low, medium, high per experiment basis.' })), _react2.default.createElement(DataSeriesHeatmapLegendBox, { key: 'No data available',
	        name: 'No data available',
	        colour: 'white',
	        on: true
	    }));
	};

	DataSeriesHeatmapLegend.propTypes = {
	    legendItems: _propTypes2.default.arrayOf(_propTypes2.default.shape(DataSeriesHeatmapLegendBox.propTypes)).isRequired
	};

	var _default = DataSeriesHeatmapLegend;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(DataSeriesHeatmapLegendBox, 'DataSeriesHeatmapLegendBox', 'src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(DataSeriesHeatmapLegend, 'DataSeriesHeatmapLegend', 'src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(DataSeriesHeatmapLegendBox, 'DataSeriesHeatmapLegendBox', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(DataSeriesHeatmapLegend, 'DataSeriesHeatmapLegend', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/DataSeriesHeatmapLegend.js');
	}();

	;

/***/ },

/***/ 2374:
[2892, 2375, 2346],

/***/ 2375:
[2893, 2345],

/***/ 2376:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _utils = __webpack_require__(2377);

	__webpack_require__(2378);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var renderGradient = function renderGradient(_ref, index) {
	    var fromValue = _ref.fromValue,
	        toValue = _ref.toValue,
	        colours = _ref.colours;

	    var spanStyle = { backgroundImage: 'linear-gradient(to right, ' + colours.join(', ') + ')' };

	    return fromValue < toValue ? _react2.default.createElement('div', { style: { display: 'table-row' }, key: 'gradient_' + index }, _react2.default.createElement('div', { className: 'gxaGradientLevel gxaGradientLevelMin' }, (0, _utils.numberWithCommas)(fromValue)), _react2.default.createElement('div', { style: { display: 'table-cell', verticalAlign: 'middle' } }, _react2.default.createElement('span', { className: 'gxaGradientColour', style: spanStyle })), _react2.default.createElement('div', { className: 'gxaGradientLevel gxaGradientLevelMax' }, (0, _utils.numberWithCommas)(toValue))) : null;
	};

	var GradientHeatmapLegend = function GradientHeatmapLegend(_ref2) {
	    var gradients = _ref2.gradients,
	        unit = _ref2.unit;
	    return _react2.default.createElement('div', { className: 'gxaGradientLegend' }, _react2.default.createElement('div', null, !unit ? _react2.default.createElement('span', null, 'Expression level') : unit.indexOf("fold change") > -1 ? _react2.default.createElement('span', null, 'Log', _react2.default.createElement('sub', null, '2'), '-fold change') : _react2.default.createElement('span', null, 'Expression level in ', unit)), gradients.map(renderGradient));
	};

	GradientHeatmapLegend.propTypes = {
	    gradients: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        fromValue: _propTypes2.default.number,
	        toValue: _propTypes2.default.number,
	        colours: _propTypes2.default.arrayOf(_propTypes2.default.string)
	    })).isRequired,
	    unit: _propTypes2.default.string.isRequired
	};

	var _default = GradientHeatmapLegend;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(renderGradient, 'renderGradient', 'src/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(GradientHeatmapLegend, 'GradientHeatmapLegend', 'src/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/heatmap-legend/GradientHeatmapLegend.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(renderGradient, 'renderGradient', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(GradientHeatmapLegend, 'GradientHeatmapLegend', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/heatmap-legend/GradientHeatmapLegend.js');
	}();

	;

/***/ },

/***/ 2377:
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	var capitalizeFirstLetter = function capitalizeFirstLetter(str) {
	    return str.charAt(0).toUpperCase() + str.substr(1);
	};

	// http://stackoverflow.com/questions/2901102/how-to-print-a-number-with-commas-as-thousands-separators-in-javascript
	var numberWithCommas = function numberWithCommas(x) {
	    var parts = x.toString().split(".");
	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    return parts.join(".");
	};

	exports.capitalizeFirstLetter = capitalizeFirstLetter;
	exports.numberWithCommas = numberWithCommas;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(capitalizeFirstLetter, "capitalizeFirstLetter", "src/utils.js");

	    __REACT_HOT_LOADER__.register(numberWithCommas, "numberWithCommas", "src/utils.js");
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(capitalizeFirstLetter, "capitalizeFirstLetter", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/utils.js");

	    __REACT_HOT_LOADER__.register(numberWithCommas, "numberWithCommas", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/utils.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/utils.js");
	}();

	;

/***/ },

/***/ 2378:
[2894, 2379, 2346],

/***/ 2379:
[2895, 2345],

/***/ 2380:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _createClass = function () {
	    function defineProperties(target, props) {
	        for (var i = 0; i < props.length; i++) {
	            var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
	        }
	    }return function (Constructor, protoProps, staticProps) {
	        if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
	    };
	}();

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _Button = __webpack_require__(1202);

	var _Button2 = _interopRequireDefault(_Button);

	var _Glyphicon = __webpack_require__(1210);

	var _Glyphicon2 = _interopRequireDefault(_Glyphicon);

	var _rcSlider = __webpack_require__(780);

	var _rcSlider2 = _interopRequireDefault(_rcSlider);

	__webpack_require__(2381);

	__webpack_require__(2383);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _classCallCheck(instance, Constructor) {
	    if (!(instance instanceof Constructor)) {
	        throw new TypeError("Cannot call a class as a function");
	    }
	}

	function _possibleConstructorReturn(self, call) {
	    if (!self) {
	        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
	    }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
	    if (typeof superClass !== "function" && superClass !== null) {
	        throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
	    }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var CoexpressionOption = function (_React$Component) {
	    _inherits(CoexpressionOption, _React$Component);

	    function CoexpressionOption() {
	        _classCallCheck(this, CoexpressionOption);

	        return _possibleConstructorReturn(this, (CoexpressionOption.__proto__ || Object.getPrototypeOf(CoexpressionOption)).apply(this, arguments));
	    }

	    _createClass(CoexpressionOption, [{
	        key: '_showOfferToDisplay',
	        value: function _showOfferToDisplay() {
	            var _this2 = this;

	            return _react2.default.createElement(_Button2.default, { bsSize: 'xsmall', onClick: function onClick() {
	                    return _this2.props.showCoexpressionsCallback(10);
	                } }, _react2.default.createElement(_Glyphicon2.default, { glyph: 'th' }), _react2.default.createElement('span', { style: { verticalAlign: 'middle' } }, ' Add similarly expressed genes'));
	        }
	    }, {
	        key: '_showSlider',
	        value: function _showSlider() {
	            var marks = {
	                0: 'off',
	                10: '10'
	            };
	            marks[this.props.numCoexpressionsAvailable] = this.props.numCoexpressionsAvailable;

	            return _react2.default.createElement('div', null, _react2.default.createElement('p', null, 'Display genes with similar expression to ' + this.props.geneName + ':'), _react2.default.createElement('div', { className: 'gxaSlider' }, _react2.default.createElement(_rcSlider2.default, { min: 0,
	                max: this.props.numCoexpressionsAvailable,
	                onAfterChange: this.props.showCoexpressionsCallback,
	                marks: marks, included: false, defaultValue: this.props.numCoexpressionsVisible })));
	        }
	    }, {
	        key: 'render',
	        value: function render() {
	            return _react2.default.createElement('div', { className: 'gxaDisplayCoexpressionOffer' }, this.props.numCoexpressionsAvailable ? this.props.numCoexpressionsVisible ? this._showSlider() : this._showOfferToDisplay() : _react2.default.createElement('span', null, 'No genes with similar expression to ' + this.props.geneName + ' available for display'));
	        }
	    }]);

	    return CoexpressionOption;
	}(_react2.default.Component);

	CoexpressionOption.propTypes = {
	    geneName: _propTypes2.default.string.isRequired,
	    numCoexpressionsVisible: _propTypes2.default.number.isRequired,
	    numCoexpressionsAvailable: _propTypes2.default.number.isRequired,
	    showCoexpressionsCallback: _propTypes2.default.func.isRequired
	};

	var _default = CoexpressionOption;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(CoexpressionOption, 'CoexpressionOption', 'src/manipulate/coexpression/CoexpressionOption.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/coexpression/CoexpressionOption.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_createClass, "_createClass", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_react2, "_react2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_propTypes2, "_propTypes2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_Button2, "_Button2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_Glyphicon2, "_Glyphicon2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_rcSlider2, "_rcSlider2", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, "_interopRequireDefault", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_classCallCheck, "_classCallCheck", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_possibleConstructorReturn, "_possibleConstructorReturn", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_inherits, "_inherits", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(CoexpressionOption, "CoexpressionOption", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/coexpression/CoexpressionOption.js");
	}();

	;

/***/ },

/***/ 2381:
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(2382);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(2346)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../atlas_bundles/baseline-expression/node_modules/css-loader/index.js!./index.css", function() {
				var newContent = require("!!./../../../atlas_bundles/baseline-expression/node_modules/css-loader/index.js!./index.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2382:
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(2345)();
	// imports


	// module
	exports.push([module.id, ".rc-slider {\n  position: relative;\n  height: 4px;\n  width: 100%;\n  border-radius: 6px;\n  background-color: #e9e9e9;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider-track {\n  position: absolute;\n  left: 0;\n  height: 4px;\n  border-radius: 6px;\n  background-color: #abe2fb;\n}\n.rc-slider-handle {\n  position: absolute;\n  margin-left: -7px;\n  margin-top: -5px;\n  width: 14px;\n  height: 14px;\n  cursor: pointer;\n  border-radius: 50%;\n  border: solid 2px #96dbfa;\n  background-color: #fff;\n}\n.rc-slider-handle:hover {\n  border-color: #57c5f7;\n}\n.rc-slider-handle-active:active {\n  border-color: #57c5f7;\n  box-shadow: 0 0 5px #57c5f7;\n}\n.rc-slider-mark {\n  position: absolute;\n  top: 10px;\n  left: 0;\n  width: 100%;\n  font-size: 12px;\n}\n.rc-slider-mark-text {\n  position: absolute;\n  display: inline-block;\n  vertical-align: middle;\n  text-align: center;\n  cursor: pointer;\n  color: #999;\n}\n.rc-slider-mark-text-active {\n  color: #666;\n}\n.rc-slider-step {\n  position: absolute;\n  width: 100%;\n  height: 4px;\n  background: transparent;\n}\n.rc-slider-dot {\n  position: absolute;\n  bottom: -2px;\n  margin-left: -4px;\n  width: 8px;\n  height: 8px;\n  border: 2px solid #e9e9e9;\n  background-color: #fff;\n  cursor: pointer;\n  border-radius: 50%;\n  vertical-align: middle;\n}\n.rc-slider-dot:first-child {\n  margin-left: -4px;\n}\n.rc-slider-dot:last-child {\n  margin-left: -4px;\n}\n.rc-slider-dot-active {\n  border-color: #96dbfa;\n}\n.rc-slider-disabled {\n  background-color: #e9e9e9;\n}\n.rc-slider-disabled .rc-slider-track {\n  background-color: #ccc;\n}\n.rc-slider-disabled .rc-slider-handle,\n.rc-slider-disabled .rc-slider-dot {\n  border-color: #ccc;\n  background-color: #fff;\n  cursor: not-allowed;\n}\n.rc-slider-disabled .rc-slider-mark-text,\n.rc-slider-disabled .rc-slider-dot {\n  cursor: not-allowed !important;\n}\n.rc-slider-vertical {\n  width: 4px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-track {\n  bottom: 0;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-handle {\n  position: absolute;\n  margin-left: -5px;\n  margin-bottom: -7px;\n}\n.rc-slider-vertical .rc-slider-mark {\n  top: 0;\n  left: 10px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-step {\n  height: 100%;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-dot {\n  left: 2px;\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:first-child {\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:last-child {\n  margin-bottom: -4px;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-enter.rc-slider-tooltip-zoom-down-enter-active,\n.rc-slider-tooltip-zoom-down-appear.rc-slider-tooltip-zoom-down-appear-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownIn;\n          animation-name: rcSliderTooltipZoomDownIn;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-leave.rc-slider-tooltip-zoom-down-leave-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownOut;\n          animation-name: rcSliderTooltipZoomDownOut;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-transform: scale(0, 0);\n          transform: scale(0, 0);\n  -webkit-animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n          animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n          animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n}\n@-webkit-keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@-webkit-keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n@keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n.rc-tooltip {\n  position: absolute;\n  left: -9999px;\n  top: -9999px;\n  visibility: visible;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip-hidden {\n  display: none;\n}\n.rc-tooltip-placement-top {\n  padding: 4px 0 8px 0;\n}\n.rc-tooltip-inner {\n  padding: 6px 2px;\n  min-width: 24px;\n  height: 24px;\n  font-size: 12px;\n  line-height: 1;\n  color: #fff;\n  text-align: center;\n  text-decoration: none;\n  background-color: #6c6c6c;\n  border-radius: 6px;\n  box-shadow: 0 0 4px #d9d9d9;\n}\n.rc-tooltip-arrow {\n  position: absolute;\n  width: 0;\n  height: 0;\n  border-color: transparent;\n  border-style: solid;\n}\n.rc-tooltip-placement-top .rc-tooltip-arrow {\n  bottom: 4px;\n  left: 50%;\n  margin-left: -4px;\n  border-width: 4px 4px 0;\n  border-top-color: #6c6c6c;\n}\n", ""]);

	// exports


/***/ },

/***/ 2383:
[2911, 2384, 2346],

/***/ 2384:
[2912, 2345],

/***/ 2385:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _urijs = __webpack_require__(193);

	var _urijs2 = _interopRequireDefault(_urijs);

	function _interopRequireDefault(obj) {
	  return obj && obj.__esModule ? obj : { default: obj };
	}

	var _ontologyIdsForColumn = function _ontologyIdsForColumn(heatmapData, x) {
	  return heatmapData.xAxisCategories[x].id;
	};

	var _ontologyIdsForRow = function _ontologyIdsForRow(heatmapData, y) {
	  return [].concat.apply([], [].concat.apply([], heatmapData.dataSeries.map(function (series) {
	    return series.data;
	  })).filter(function (point) {
	    return point.y === y && !!point.value;
	  }).map(function (point) {
	    return _ontologyIdsForColumn(heatmapData, point.x);
	  }).map(function (e) {
	    return Array.isArray(e) ? e : [e];
	  })).filter(function (e, ix, self) {
	    return self.indexOf(e) === ix;
	  });
	};

	var makeEventCallbacks = function makeEventCallbacks(_ref) {
	  var heatmapData = _ref.heatmapData,
	      onSelectOntologyIds = _ref.onSelectOntologyIds,
	      genomeBrowser = _ref.genomeBrowser,
	      experimentAccession = _ref.experimentAccession,
	      accessKey = _ref.accessKey,
	      atlasUrl = _ref.atlasUrl;

	  return {
	    onHoverRow: function onHoverRow(y) {
	      onSelectOntologyIds(_ontologyIdsForRow(heatmapData, y));
	    },

	    onHoverColumn: function onHoverColumn(x) {
	      onSelectOntologyIds(_ontologyIdsForColumn(heatmapData, x));
	    },

	    onHoverOff: function onHoverOff() {
	      onSelectOntologyIds([]);
	    },

	    /*
	      For this to work genomeBrowser needs to be included in the props that cause a re-render in HeatmapCanvas.jsx
	       TODO we have suffered a bit of a defeat here because it made us include accessKey.
	      If instead of genomeBrowser string we had a {name, uri} here, we could do
	      URI(genomeBrowser.uri, atlasUrl).search({
	        experimentAccession: experimentAccession,
	        geneId: heatmapData.xAxisCategories[x].info.trackId,
	        trackId: heatmapData.yAxisCategories[y].info.trackId
	      })
	    */
	    onClick: genomeBrowser !== 'none' ? function (x, y) {
	      window.open((0, _urijs2.default)('external-services/genome-browser/' + genomeBrowser, atlasUrl).search({
	        experimentAccession: experimentAccession,
	        geneId: heatmapData.yAxisCategories[y].info.trackId,
	        trackId: heatmapData.xAxisCategories[x].info.trackId,
	        accessKey: accessKey
	      }).toString(), '_blank');
	    } : undefined
	  };
	};

	var _default = makeEventCallbacks;
	exports.default = _default;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_ontologyIdsForColumn, '_ontologyIdsForColumn', 'src/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_ontologyIdsForRow, '_ontologyIdsForRow', 'src/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(makeEventCallbacks, 'makeEventCallbacks', 'src/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_default, 'default', 'src/manipulate/Events.js');
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_urijs2, '_urijs2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_ontologyIdsForColumn, '_ontologyIdsForColumn', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_ontologyIdsForRow, '_ontologyIdsForRow', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(makeEventCallbacks, 'makeEventCallbacks', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');

	  __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Events.js');
	}();

	;

/***/ },

/***/ 2386:
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	/*
	 All functions in this module accept and return a following format of data:
	 {
	     dataSeries : [info: {...: String}, data: [Point]}]
	     xAxisCategories: [X axis label]
	     yAxisCategories: [Y axis label]
	 }
	 */

	var orderHeatmapData = function orderHeatmapData(ordering, data) {
	    var permuteX = function permuteX(x) {
	        return ordering.columns.indexOf(x);
	    };
	    var permuteY = function permuteY(y) {
	        return ordering.rows.indexOf(y);
	    };

	    var permutePoint = function permutePoint(point) {
	        return {
	            x: permuteX(point.x),
	            y: permuteY(point.y),
	            value: point.value,
	            info: point.info
	        };
	    };

	    var permuteArray = function permuteArray(arr, permute) {
	        return arr.map(function (el, ix) {
	            return [el, permute(ix)];
	        }).sort(function (l, r) {
	            return l[1] - r[1];
	        }).map(function (el) {
	            return el[0];
	        });
	    };

	    return {
	        dataSeries: data.dataSeries.map(function (series) {
	            return {
	                info: series.info,
	                data: series.data.map(permutePoint) };
	        }),
	        xAxisCategories: permuteArray(data.xAxisCategories, permuteX),
	        yAxisCategories: permuteArray(data.yAxisCategories, permuteY)
	    };
	};

	var _axisElementsForFilteredDataSeries = function _axisElementsForFilteredDataSeries(axis, conditionPerSeries, conditionPerPoint, dataSeries) {
	    return dataSeries.filter(conditionPerSeries).map(function (e) {
	        return e.data;
	    }).reduce(function (l, r) {
	        return l.concat(r);
	    }, []).filter(conditionPerPoint).map(function (e) {
	        return e[axis];
	    }).filter(function (e, ix, self) {
	        return self.indexOf(e) === ix;
	    }) // unique
	    .sort(function (l, r) {
	        return l - r;
	    });
	};

	var _filterHeatmapData = function _filterHeatmapData(keepSeries, keepPoint, data) {
	    var allXs = _axisElementsForFilteredDataSeries("x", keepSeries, keepPoint, data.dataSeries);
	    var allYs = _axisElementsForFilteredDataSeries("y", keepSeries, keepPoint, data.dataSeries);

	    var newDataSeries = data.dataSeries.map(function (series, ix) {
	        return keepSeries(series, ix) ? series.data.filter(keepPoint) : [];
	    }).map(function (series) {
	        return series.map(function (point) {
	            return {
	                x: allXs.indexOf(point.x),
	                y: allYs.indexOf(point.y),
	                value: point.value,
	                info: point.info
	            };
	        }).filter(function (point) {
	            return point.x > -1 && point.y > -1;
	        });
	    });

	    return {
	        dataSeries: data.dataSeries.map(function (e, ix) {
	            return {
	                info: e.info,
	                data: newDataSeries[ix]
	            };
	        }),
	        xAxisCategories: data.xAxisCategories.filter(function (e, ix) {
	            return allXs.includes(ix);
	        }),
	        yAxisCategories: data.yAxisCategories.filter(function (e, ix) {
	            return allYs.includes(ix);
	        })
	    };
	};

	var filterHeatmapData = function filterHeatmapData(keepSeries, keepRow, keepColumn, data) {
	    return _filterHeatmapData(keepSeries, function (point) {
	        return keepRow(data.yAxisCategories[point.y]) && keepColumn(data.xAxisCategories[point.x]);
	    }, data);
	};

	var _calculateInserts = function _calculateInserts(fullColumns, originalColumns) {
	    var result = [];
	    var fullColumnsCursor = 0;
	    var originalColumnsCursor = 0;

	    while (fullColumnsCursor < fullColumns.length && originalColumnsCursor < originalColumns.length) {
	        if (fullColumns.length > fullColumnsCursor && originalColumns.length > originalColumnsCursor && fullColumns[fullColumnsCursor] === originalColumns[originalColumnsCursor]) {
	            result.push("");
	            fullColumnsCursor++;
	            originalColumnsCursor++;
	        } else if (fullColumns.length > fullColumnsCursor) {
	            result.push(fullColumns[fullColumnsCursor]);
	            fullColumnsCursor++;
	        } else if (originalColumns[originalColumnsCursor].length > originalColumnsCursor) {
	            result.push("");
	            originalColumnsCursor++;
	        }
	    }
	    return result;
	};

	var _indicesForInserts = function _indicesForInserts(inserts) {
	    var i = -1;
	    return inserts.map(function (e) {
	        !e && i++;
	        return i;
	    });
	};

	var insertEmptyColumns = function insertEmptyColumns(newColumns, data) {
	    var fullColumns = newColumns.concat(data.xAxisCategories.filter(function (originalColumn) {
	        return newColumns.findIndex(function (e) {
	            return e.label === originalColumn.label;
	        }) === -1;
	    }));

	    var insertIndices = _indicesForInserts(_calculateInserts(fullColumns.map(function (e) {
	        return e.label;
	    }), data.xAxisCategories.map(function (e) {
	        return e.label;
	    })));

	    return {
	        dataSeries: data.dataSeries.map(function (e) {
	            return {
	                info: e.info,
	                data: e.data.map(function (point) {
	                    return _extends({}, point, {
	                        x: insertIndices.indexOf(point.x)
	                    });
	                })
	            };
	        }),
	        xAxisCategories: fullColumns,
	        yAxisCategories: data.yAxisCategories
	    };
	};

	var manipulate = function manipulate(args, data) {
	    var orderedHeatmapData = orderHeatmapData(args.ordering, data);
	    return insertEmptyColumns(args.allowEmptyColumns ? orderedHeatmapData.xAxisCategories : [], filterHeatmapData(args.keepSeries, args.keepRow, args.keepColumn, orderedHeatmapData));
	};

	exports.insertEmptyColumns = insertEmptyColumns;
	exports.filterHeatmapData = filterHeatmapData;
	exports.orderHeatmapData = orderHeatmapData;
	exports.manipulate = manipulate;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(orderHeatmapData, "orderHeatmapData", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_axisElementsForFilteredDataSeries, "_axisElementsForFilteredDataSeries", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_filterHeatmapData, "_filterHeatmapData", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(filterHeatmapData, "filterHeatmapData", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_calculateInserts, "_calculateInserts", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_indicesForInserts, "_indicesForInserts", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(insertEmptyColumns, "insertEmptyColumns", "src/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(manipulate, "manipulate", "src/manipulate/Manipulators.js");
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, "_extends", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(orderHeatmapData, "orderHeatmapData", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_axisElementsForFilteredDataSeries, "_axisElementsForFilteredDataSeries", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_filterHeatmapData, "_filterHeatmapData", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(filterHeatmapData, "filterHeatmapData", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_calculateInserts, "_calculateInserts", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_indicesForInserts, "_indicesForInserts", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(insertEmptyColumns, "insertEmptyColumns", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(manipulate, "manipulate", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");

	    __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/manipulate/Manipulators.js");
	}();

	;

/***/ },

/***/ 2387:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _reactHighcharts = __webpack_require__(1347);

	var _reactHighcharts2 = _interopRequireDefault(_reactHighcharts);

	var _highchartsMore = __webpack_require__(2388);

	var _highchartsMore2 = _interopRequireDefault(_highchartsMore);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	function _toConsumableArray(arr) {
	    if (Array.isArray(arr)) {
	        for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
	            arr2[i] = arr[i];
	        }return arr2;
	    } else {
	        return Array.from(arr);
	    }
	}

	(0, _highchartsMore2.default)(_reactHighcharts2.default.Highcharts);

	var BoxplotCanvas = function BoxplotCanvas(_ref) {
	    var title = _ref.title,
	        xAxisCategories = _ref.xAxisCategories,
	        dataSeries = _ref.dataSeries,
	        unit = _ref.unit;

	    var initialMarginRight = 60;
	    var marginRight = initialMarginRight * (1 + 10 / Math.pow(1 + xAxisCategories.length, 2));

	    // We need to filter because Mat.min(undefined, <any number or anything whatsoever>) returns NaN
	    var min = Math.min.apply(Math, _toConsumableArray(dataSeries.filter(function (quartiles) {
	        return quartiles.length;
	    }).map(function (quartiles) {
	        return quartiles[0];
	    })));
	    var max = Math.max.apply(Math, _toConsumableArray(dataSeries.filter(function (quartiles) {
	        return quartiles.length;
	    }).map(function (quartiles) {
	        return quartiles[4];
	    })));

	    // If no all five points are the same and we want to show the box plot with just points
	    // const scatter = dataSeries.every(quartiles => _.uniq(quartiles).length === 1)

	    var series = {
	        name: 'Observations',
	        data: dataSeries,
	        tooltip: {
	            headerFormat: '<em>Factor: {point.key}</em><br/>'
	        }
	    };

	    var config = {
	        chart: {
	            marginRight: marginRight,
	            type: 'boxplot',
	            spacingRight: xAxisCategories.slice(-1)[0].length > 6 ? 100 : 0
	        },

	        plotOptions: {
	            boxplot: {
	                turboThreshold: 0
	            },
	            column: {
	                dataLabels: {
	                    crop: false
	                }
	            },
	            series: {
	                states: {
	                    hover: {
	                        color: '#eeec38' //#edab12 color cell on mouse over
	                    },
	                    select: {
	                        color: '#eeec38'
	                    }
	                }
	            }
	        },

	        credits: {
	            enabled: false
	        },

	        title: {
	            text: title
	        },

	        legend: {
	            enabled: false
	        },

	        xAxis: {
	            tickLength: 5,
	            tickColor: 'rgb(192, 192, 192)',
	            lineColor: 'rgb(192, 192, 192)',
	            categories: xAxisCategories,
	            labels: {
	                style: {
	                    fontSize: '9px'
	                }
	                // opposite: 'true'
	            } },

	        yAxis: {
	            title: {
	                text: 'Expression' + (unit ? ' (' + unit + ')' : '')
	            },
	            min: min,
	            max: max
	            // reversed: true
	        },

	        series: [series]
	    };

	    return _react2.default.createElement(_reactHighcharts2.default, { config: config });
	};

	BoxplotCanvas.propTypes = {
	    title: _propTypes2.default.string.isRequired,
	    xAxisCategories: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    dataSeries: _propTypes2.default.arrayOf(_propTypes2.default.arrayOf(_propTypes2.default.number)).isRequired,
	    unit: _propTypes2.default.string.isRequired
	};

	var _default = BoxplotCanvas;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(BoxplotCanvas, 'BoxplotCanvas', 'src/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/show/BoxplotCanvas.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_reactHighcharts2, '_reactHighcharts2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_highchartsMore2, '_highchartsMore2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_toConsumableArray, '_toConsumableArray', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(BoxplotCanvas, 'BoxplotCanvas', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/show/BoxplotCanvas.js');
	}();

	;

/***/ },

/***/ 2388:
935,

/***/ 2389:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _propTypes = __webpack_require__(189);

	var _propTypes2 = _interopRequireDefault(_propTypes);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var baselineColumnHeadersPropTypes = _propTypes2.default.shape({
	    assayGroupId: _propTypes2.default.string.isRequired,
	    factorValue: _propTypes2.default.string.isRequired,
	    factorValueOntologyTermId: _propTypes2.default.string, // Some factors don’t have an ontology term... yet
	    assayGroupSummary: _propTypes2.default.shape({ // Present when rows are genes
	        replicates: _propTypes2.default.number.isRequired,
	        properties: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	            propertyName: _propTypes2.default.string.isRequired,
	            testValue: _propTypes2.default.string.isRequired,
	            contrastPropertyType: _propTypes2.default.oneOf(['FACTOR', 'SAMPLE']).isRequired
	        })).isRequired
	    })
	});

	var assayGroupPropTypes = _propTypes2.default.shape({
	    id: _propTypes2.default.string.isRequired,
	    assayAccessions: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	    replicates: _propTypes2.default.number.isRequired
	});

	var differentialColumnHeadersPropTypes = _propTypes2.default.shape({
	    id: _propTypes2.default.string.isRequired,
	    arrayDesignAccession: _propTypes2.default.string, // Present in microarray experiments
	    referenceAssayGroup: assayGroupPropTypes.isRequired,
	    testAssayGroup: assayGroupPropTypes.isRequired,
	    displayName: _propTypes2.default.string.isRequired,
	    contrastSummary: _propTypes2.default.shape({
	        properties: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	            propertyName: _propTypes2.default.string.isRequired,
	            testValue: _propTypes2.default.string.isRequired,
	            contrastPropertyType: _propTypes2.default.oneOf(['FACTOR', 'SAMPLE']).isRequired,
	            referenceValue: _propTypes2.default.string.isRequired
	        })).isRequired,
	        experimentDescription: _propTypes2.default.string.isRequired,
	        contrastDescription: _propTypes2.default.string.isRequired,
	        testReplicates: _propTypes2.default.number.isRequired,
	        referenceReplicates: _propTypes2.default.number.isRequired
	    }).isRequired,
	    resources: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        type: _propTypes2.default.string.isRequired,
	        name: _propTypes2.default.string.isRequired
	    })).isRequired
	});

	var baselineExperimentProfilesRowsPropTypes = _propTypes2.default.shape({
	    id: _propTypes2.default.string.isRequired, // Experiment accession
	    experimentType: _propTypes2.default.string.isRequired,
	    name: _propTypes2.default.string.isRequired, // Human-friendly exp. name with slice
	    expressions: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        value: _propTypes2.default.number
	    })).isRequired,
	    uri: _propTypes2.default.string.isRequired
	});

	var baselineGeneProfilesRowsExpressionsPropTypes = _propTypes2.default.shape({
	    value: _propTypes2.default.number,
	    quartiles: _propTypes2.default.shape({
	        min: _propTypes2.default.number.isRequired,
	        lower: _propTypes2.default.number.isRequired,
	        median: _propTypes2.default.number.isRequired,
	        upper: _propTypes2.default.number.isRequired,
	        max: _propTypes2.default.number.isRequired
	    })
	});

	var baselineGeneProfilesRowsPropTypes = _propTypes2.default.shape({
	    id: _propTypes2.default.string.isRequired, // Gene ID
	    name: _propTypes2.default.string.isRequired, // Gene name
	    expressions: _propTypes2.default.arrayOf(baselineGeneProfilesRowsExpressionsPropTypes).isRequired,
	    uri: _propTypes2.default.string.isRequired
	});

	var baselineProfilesPropTypes = _propTypes2.default.shape({
	    searchResultTotal: _propTypes2.default.string.isRequired,
	    rows: _propTypes2.default.arrayOf(_propTypes2.default.oneOfType([baselineExperimentProfilesRowsPropTypes, baselineGeneProfilesRowsPropTypes]))
	});

	var differentialProfilesPropTypes = _propTypes2.default.shape({
	    maxDownLevel: _propTypes2.default.number,
	    maxUpLevel: _propTypes2.default.number,
	    minDownLevel: _propTypes2.default.number,
	    minUpLevel: _propTypes2.default.number,
	    rows: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        id: _propTypes2.default.string.isRequired,
	        name: _propTypes2.default.string.isRequired,
	        designElement: _propTypes2.default.string.isRequired, // Present but empty in RNA-seq differential exps
	        expressions: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	            contrastName: _propTypes2.default.string.isRequired,
	            color: _propTypes2.default.string,
	            foldChange: _propTypes2.default.number,
	            pValue: _propTypes2.default.number,
	            tStat: _propTypes2.default.number
	        })).isRequired
	    })).isRequired,
	    searchResultTotal: _propTypes2.default.number
	});

	var dataPropTypes = _propTypes2.default.shape({
	    config: _propTypes2.default.shape({
	        geneQuery: _propTypes2.default.string.isRequired,
	        conditionQuery: _propTypes2.default.string.isRequired,
	        species: _propTypes2.default.string.isRequired,
	        genomeBrowsers: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	        columnType: _propTypes2.default.string.isRequired,
	        disclaimer: _propTypes2.default.string.isRequired
	    }).isRequired,

	    columnHeaders: _propTypes2.default.arrayOf(_propTypes2.default.oneOfType([baselineColumnHeadersPropTypes, differentialColumnHeadersPropTypes])).isRequired,

	    columnGroupings: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	        name: _propTypes2.default.string.isRequired,
	        memberName: _propTypes2.default.string.isRequired,
	        groups: _propTypes2.default.arrayOf(_propTypes2.default.shape({
	            id: _propTypes2.default.string.isRequired,
	            name: _propTypes2.default.string.isRequired,
	            values: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired
	        }))
	    })).isRequired,

	    profiles: _propTypes2.default.oneOfType([baselineProfilesPropTypes, differentialProfilesPropTypes]).isRequired,

	    anatomogramData: _propTypes2.default.shape({
	        allSvgPathIds: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
	        species: _propTypes2.default.string.isRequired
	    }),

	    coexpressions: _propTypes2.default.arrayOf(_propTypes2.default.shape({ // Only present in baseline, single result
	        geneName: _propTypes2.default.string.isRequired,
	        geneId: _propTypes2.default.string.isRequired,
	        jsonProfiles: baselineProfilesPropTypes
	    })),

	    experiment: _experimentTypeUtils.experimentPropTypes

	});

	var _default = dataPropTypes;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(baselineColumnHeadersPropTypes, 'baselineColumnHeadersPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(assayGroupPropTypes, 'assayGroupPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(differentialColumnHeadersPropTypes, 'differentialColumnHeadersPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineExperimentProfilesRowsPropTypes, 'baselineExperimentProfilesRowsPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineGeneProfilesRowsExpressionsPropTypes, 'baselineGeneProfilesRowsExpressionsPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineGeneProfilesRowsPropTypes, 'baselineGeneProfilesRowsPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineProfilesPropTypes, 'baselineProfilesPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(differentialProfilesPropTypes, 'differentialProfilesPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(dataPropTypes, 'dataPropTypes', 'src/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/layout/jsonPayloadPropTypes.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_react2, '_react2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(_propTypes2, '_propTypes2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineColumnHeadersPropTypes, 'baselineColumnHeadersPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(assayGroupPropTypes, 'assayGroupPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(differentialColumnHeadersPropTypes, 'differentialColumnHeadersPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineExperimentProfilesRowsPropTypes, 'baselineExperimentProfilesRowsPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineGeneProfilesRowsExpressionsPropTypes, 'baselineGeneProfilesRowsExpressionsPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineGeneProfilesRowsPropTypes, 'baselineGeneProfilesRowsPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(baselineProfilesPropTypes, 'baselineProfilesPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(differentialProfilesPropTypes, 'differentialProfilesPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(dataPropTypes, 'dataPropTypes', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/layout/jsonPayloadPropTypes.js');
	}();

	;

/***/ },

/***/ 2390:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _chartConfiguration = __webpack_require__(2391);

	var _chartConfiguration2 = _interopRequireDefault(_chartConfiguration);

	var _heatmapData = __webpack_require__(2392);

	var _heatmapData2 = _interopRequireDefault(_heatmapData);

	var _boxplotData = __webpack_require__(2400);

	var _boxplotData2 = _interopRequireDefault(_boxplotData);

	var _heatmapOrderings = __webpack_require__(2401);

	var _heatmapOrderings2 = _interopRequireDefault(_heatmapOrderings);

	var _heatmapColourAxis = __webpack_require__(2402);

	var _heatmapColourAxis2 = _interopRequireDefault(_heatmapColourAxis);

	var _heatmapFilters = __webpack_require__(2403);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var _default = function _default(data, inProxy, outProxy, atlasUrl, pathToResources, isWidget) {

	    // This ensures that adding or removing coexpressed genes doesn’t change the colours in the heat map. Colours are
	    // computed upfront and then we just add/remove rows with the coexpression slider.
	    // coexpressions is an array because at first it was envisioned that the JSON payload could carry coexpressions of
	    // more than one gene, but that’s not the case, and ended up being a single item array.
	    var allRows = data.coexpressions ? data.profiles.rows.concat(data.coexpressions[0].jsonProfiles.rows) : data.profiles.rows;

	    var heatmapData = (0, _heatmapData2.default)(allRows, data.config.geneQuery, data.columnHeaders, data.columnGroupings, data.experiment, inProxy, atlasUrl, pathToResources);

	    return {
	        heatmapData: heatmapData,
	        boxplotData: (0, _boxplotData2.default)(data),
	        heatmapConfig: (0, _chartConfiguration2.default)(data, inProxy, outProxy, atlasUrl, isWidget),
	        colourAxis: (0, _heatmapColourAxis2.default)(data.experiment, heatmapData.dataSeries),
	        orderings: (0, _heatmapOrderings2.default)(data.experiment, allRows, data.columnHeaders),
	        expressionLevelFilters: (0, _heatmapFilters.getExpressionLevelFilters)(data.experiment, heatmapData.dataSeries),
	        groupingFilters: (0, _heatmapFilters.getColumnGroupingFilters)(heatmapData.xAxisCategories)
	    };
	};

	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/main.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_chartConfiguration2, '_chartConfiguration2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_heatmapData2, '_heatmapData2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_boxplotData2, '_boxplotData2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_heatmapOrderings2, '_heatmapOrderings2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_heatmapColourAxis2, '_heatmapColourAxis2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/main.js');
	}();

	;

/***/ },

/***/ 2391:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _experimentTypeUtils = __webpack_require__(2361);

	var _utils = __webpack_require__(2377);

	// Message on top of the chart: “Showing 3 experiments:”, “Showing 12 genes of 432 found:”, “Showing 32 genes:”...
	var introductoryMessage = function introductoryMessage(experiment, profiles) {
	    var shownRows = profiles.rows.length;
	    var totalRows = profiles.searchResultTotal;

	    var what = (experiment ? 'gene' : 'experiment') + (totalRows > 1 ? 's' : '');

	    return 'Showing ' + (0, _utils.numberWithCommas)(shownRows) + ' ' + (totalRows === shownRows ? what + ':' : 'of ' + (0, _utils.numberWithCommas)(totalRows) + ' ' + what + ' found:');
	};

	var queryDescription = function queryDescription(geneQuery, conditionQuery, species) {
	    // Since Atlas uses URLEncoder in SemanticQuery.java
	    var plusDecode = function plusDecode(str) {
	        return decodeURIComponent(str.replace(/\+/g, '%20'));
	    };

	    var decodedGeneQuery = plusDecode(geneQuery);
	    var decodedConditionQuery = plusDecode(conditionQuery);
	    var decodedSpecies = plusDecode(species);

	    return 'Query results for: ' + decodedGeneQuery + (decodedConditionQuery ? ', in conditions ' + decodedConditionQuery : '') + (decodedSpecies ? ', in species ' + (decodedSpecies[0].toUpperCase() + decodedSpecies.slice(1).toLowerCase()) : '');
	};

	var getChartConfiguration = function getChartConfiguration(data, inProxy, outProxy, atlasUrl, isWidget) {
	    var experiment = data.experiment,
	        profiles = data.profiles;
	    var _data$config = data.config,
	        geneQuery = _data$config.geneQuery,
	        conditionQuery = _data$config.conditionQuery,
	        species = _data$config.species,
	        disclaimer = _data$config.disclaimer,
	        columnType = _data$config.columnType,
	        resources = _data$config.resources;

	    var chartTextDecorations = {
	        introductoryMessage: introductoryMessage(experiment, profiles),
	        xAxisLegendName: (0, _utils.capitalizeFirstLetter)(columnType) || 'Experimental condition',
	        yAxisLegendName: (0, _experimentTypeUtils.isMultiExperiment)(experiment) ? 'Experiment' : 'Gene name'
	    };

	    var description = (0, _experimentTypeUtils.isMultiExperiment)(experiment) ? queryDescription(geneQuery, conditionQuery, species) :
	    // description can be empty, see ExperimentsCacheLoader.java or BaselineExperimentsBuilder.java
	    experiment.description || experiment.accession;

	    var shortDescription = (0, _experimentTypeUtils.isMultiExperiment)(experiment) ? 'expression_atlas-' + species.replace(/ +/, '_') : experiment.accession;

	    return Object.freeze(_extends({
	        inProxy: inProxy,
	        outProxy: outProxy,
	        atlasUrl: atlasUrl,
	        experiment: experiment,
	        isWidget: isWidget,
	        description: description,
	        shortDescription: shortDescription,
	        disclaimer: disclaimer
	    }, chartTextDecorations, {
	        genomeBrowsers: data.config.genomeBrowsers,
	        coexpressionsAvailable: Boolean(data.coexpressions),
	        isMultiExperiment: (0, _experimentTypeUtils.isMultiExperiment)(experiment),
	        isBaseline: (0, _experimentTypeUtils.isBaseline)(experiment),
	        isDifferential: (0, _experimentTypeUtils.isDifferential)(experiment)
	    }));
	};

	var _default = getChartConfiguration;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(introductoryMessage, 'introductoryMessage', 'src/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(queryDescription, 'queryDescription', 'src/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(getChartConfiguration, 'getChartConfiguration', 'src/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/chartConfiguration.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(introductoryMessage, 'introductoryMessage', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(queryDescription, 'queryDescription', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(getChartConfiguration, 'getChartConfiguration', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/chartConfiguration.js');
	}();

	;

/***/ },

/***/ 2392:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _heatmapDataSeries = __webpack_require__(2393);

	var _heatmapDataSeries2 = _interopRequireDefault(_heatmapDataSeries);

	var _heatmapAxisCategories = __webpack_require__(2394);

	function _interopRequireDefault(obj) {
	  return obj && obj.__esModule ? obj : { default: obj };
	}

	var _default = function _default(allRows, geneQuery, columnHeaders, columnGroupings, experiment, inProxy, atlasUrl, pathToResources) {
	  return {
	    xAxisCategories: (0, _heatmapAxisCategories.getHeatmapXAxisCategories)({ columnHeaders: columnHeaders, columnGroupings: columnGroupings, experiment: experiment, inProxy: inProxy, atlasUrl: atlasUrl, pathToResources: pathToResources }),
	    yAxisCategories: (0, _heatmapAxisCategories.getHeatmapYAxisCategories)({ rows: allRows, geneQuery: geneQuery, experiment: experiment, inProxy: inProxy, atlasUrl: atlasUrl }),
	    dataSeries: (0, _heatmapDataSeries2.default)(allRows, experiment)
	  };
	};

	exports.default = _default;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/heatmapData.js');
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_heatmapDataSeries2, '_heatmapDataSeries2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapData.js');

	  __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapData.js');

	  __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapData.js');

	  __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapData.js');
	}();

	;

/***/ },

/***/ 2393:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) {
	    for (var i = 1; i < arguments.length; i++) {
	        var source = arguments[i];for (var key in source) {
	            if (Object.prototype.hasOwnProperty.call(source, key)) {
	                target[key] = source[key];
	            }
	        }
	    }return target;
	};

	var _lodash = __webpack_require__(434);

	var _lodash2 = _interopRequireDefault(_lodash);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	// Returns an object with coordinates, expression value and other properties for each heat map cell
	var buildHeatMapDataPointFromExpression = function buildHeatMapDataPointFromExpression(_ref) {
	    var rowInfo = _ref.rowInfo,
	        rowIndex = _ref.rowIndex,
	        expression = _ref.expression,
	        expressionIndex = _ref.expressionIndex;
	    return expression.hasOwnProperty('value') ? {
	        x: expressionIndex,
	        y: rowIndex,
	        value: expression.value,
	        info: rowInfo
	    } : expression.hasOwnProperty('foldChange') ? {
	        x: expressionIndex,
	        y: rowIndex,
	        value: expression.foldChange,
	        info: _extends({
	            pValue: expression.pValue,
	            foldChange: expression.foldChange,
	            tStat: expression.tStat
	        }, rowInfo)
	    } : null;
	};

	var buildDataPointsFromRowExpressions = function buildDataPointsFromRowExpressions(_ref2) {
	    var rowInfo = _ref2.rowInfo,
	        expressions = _ref2.row.expressions,
	        rowIndex = _ref2.rowIndex;
	    return expressions.map(function (expression, expressionIndex) {
	        return buildHeatMapDataPointFromExpression({ rowInfo: rowInfo, rowIndex: rowIndex, expression: expression, expressionIndex: expressionIndex });
	    }).filter(function (el) {
	        return el;
	    });
	};

	// Returns lodash wrapper of an array with alternating entries of experiment type and an array of data points of that
	// type
	var _createDataPointsAndGroupThemByExperimentType = function _createDataPointsAndGroupThemByExperimentType(profilesRowsChain) {
	    return profilesRowsChain.map(function (row, rowIndex) {
	        return [row.experimentType, buildDataPointsFromRowExpressions({ rowInfo: { type: row.experimentType, description: row.name, unit: row.expressionUnit || "" /*no need for this safeguard after master from June 2017 is released*/ }, row: row, rowIndex: rowIndex })];
	    }).groupBy(function (experimentTypeAndRow) {
	        return experimentTypeAndRow[0];
	    })
	    // Just leave the data points...
	    .mapValues(function (rows) {
	        return rows.map(function (experimentTypeAndRow) {
	            return experimentTypeAndRow[1];
	        });
	    })
	    // Next, flatten all data point arrays into a single one for each experiment type:
	    // {'rnaseq_mrna_baseline': [[v1],[v2,v3],[v4,v5,v6]]} => {'rnaseq_mrna_baseline': [[v1,v2,v3,v4,v5,v6]]}
	    .mapValues(_lodash2.default.flatten)
	    // Return an array of two-element arrays instead of array of objects:
	    // {'rnaseq_mrna_baeline': [...], 'proteomics_baseline': [...]} =>
	    //    [['rnaseq_mrna_baseline, [...]], ['proteomics_baseline', [...]]]
	    .toPairs();
	};

	// Returns a function that, when passed an experimentType and an array of dataPoints, maps the array to pairs where the
	// first element is the position in the thresholds array for that experiment type (e.g. 0, 1, 2, 3 for “Below cutoff”,
	// “Low”, “Medium” and “High”, respectively) according to its value, and the second is the dataPoint itself
	var dataPointsToThresholdCategoriesMapper = function dataPointsToThresholdCategoriesMapper(thresholds) {
	    return function (experimentType, dataPoints) {
	        return dataPoints.map(function (dataPoint) {
	            return [_lodash2.default.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, dataPoint.value), dataPoint];
	        });
	    };
	};

	// Produces an array with as many thresholds/seriesNames/seriesColours each array entry contains an object with an
	// info field (an object composed of the series/threshold name and colour) and a data array with the data points that
	// correspond to that threshold
	var experimentProfilesRowsAsDataPointsSplitByThresholds = function experimentProfilesRowsAsDataPointsSplitByThresholds(thresholds, seriesNames, seriesColours, profilesRows) {
	    return _bucketsIntoSeries(seriesNames, seriesColours)(
	    // Get lodash wrapper of the experiment type / data points array
	    _createDataPointsAndGroupThemByExperimentType(_lodash2.default.chain(profilesRows))
	    // Map arrays of exp. type and data points to arrays of [threshold group index, data point]
	    .map(_lodash2.default.spread(dataPointsToThresholdCategoriesMapper(thresholds)))
	    // After this flatten we have all the data points categorised by threshold in a single array... hooray!
	    .flatten()).value();
	};

	// Create the array of pairs in a single experiment to be passed to _bucketsIntoSeries
	var _splitDataSetByProportion = function _splitDataSetByProportion(data, names, colours) {
	    var sortedValues = data.map(function (point) {
	        return point.value;
	    }).sort(function (l, r) {
	        return l - r;
	    });
	    var howManyPointsInTotal = data.length;
	    var howManyDataSetsToSplitIn = names.length;
	    return _bucketsIntoSeries(names, colours)(_lodash2.default.chain(data).map(function (point) {
	        return [Math.floor(_lodash2.default.sortedIndex(sortedValues, point.value) / howManyPointsInTotal * howManyDataSetsToSplitIn), point];
	    })).value();
	};

	var splitGeneRowsIntoProportionalSeriesOfDataPoints = function splitGeneRowsIntoProportionalSeriesOfDataPoints(profilesRows, experiment, filters, names, colours) {
	    var dataPoints = _lodash2.default.flatten(profilesRows.map(function (row, rowIndex) {
	        return buildDataPointsFromRowExpressions({ rowInfo: { unit: row.expressionUnit || "" /*no need for this safeguard after master from June 2017 is released*/ }, row: row, rowIndex: rowIndex });
	    }));

	    return _lodash2.default.flatten(_lodash2.default.range(filters.length).map(function (i) {
	        return _splitDataSetByProportion(dataPoints.filter(filters[i]), names[i], colours[i]);
	    }));
	};

	// chain is a lodash wrapper of an array of pairs: [[0, dataPoint1], [0, dataPoint2], ... [3, dataPointN]]
	// The first entry is the number of the category (i.e. “Below cutoff”, ”Low”...) and the second entry is the data point
	// Returns an array
	//    [{info: {...}, data:[data points of category 0]}, {info: {...}, data:[data points of category 1]}], etc.
	var _bucketsIntoSeries = _lodash2.default.curry(function (seriesNames, seriesColours, pairsOfCategoryAndDataPointChain) {
	    return pairsOfCategoryAndDataPointChain.groupBy(function (categoryAndDataPoint) {
	        return categoryAndDataPoint[0];
	    }).mapValues(function (pairs) {
	        return pairs.map(function (categoryAndDataPoint) {
	            return categoryAndDataPoint[1];
	        });
	    }).transform(function (result, bucketValues, bucketNumber) {
	        result[bucketNumber].data = bucketValues;
	    },
	    // The empty with the series info but no data points
	    _lodash2.default.range(seriesNames.length).map(function (i) {
	        return {
	            info: {
	                name: seriesNames[i],
	                colour: seriesColours[i]
	            },
	            data: []
	        };
	    }));
	});

	var getDataSeries = function getDataSeries(profilesRows, experiment) {
	    var _fns = [_lodash2.default.lt, _lodash2.default.eq, _lodash2.default.gt].map(function (f) {
	        return function (point) {
	            return f(point.value, 0);
	        };
	    });
	    var _belowCutoff = _fns[1];

	    if ((0, _experimentTypeUtils.isDifferential)(experiment)) {
	        return splitGeneRowsIntoProportionalSeriesOfDataPoints(profilesRows, experiment, _fns, [['High down', 'Down'], ['Below cutoff'], ['Up', 'High up']], [['#0000ff', '#8cc6ff'], ['gainsboro'], ['#e9967a', '#b22222']]);
	    } else if ((0, _experimentTypeUtils.isBaseline)(experiment)) {
	        return splitGeneRowsIntoProportionalSeriesOfDataPoints(profilesRows, experiment, [_belowCutoff, _lodash2.default.negate(_belowCutoff)], [['Below cutoff'], ['Low', 'Medium', 'High']], [['gainsboro'], ['#8cc6ff', '#0000ff', '#0000b3']]);
	    } else if ((0, _experimentTypeUtils.isMultiExperiment)(experiment)) {
	        return experimentProfilesRowsAsDataPointsSplitByThresholds({
	            RNASEQ_MRNA_BASELINE: [0, 10, 1000],
	            PROTEOMICS_BASELINE: [0, 0.001, 8],
	            DEFAULT: [0, 10, 1000]
	        }, ['Below cutoff', 'Low', 'Medium', 'High'], ['#eaeaea', '#45affd', '#1E74CA', '#024990'], profilesRows);
	    } else {
	        return null;
	    }
	};

	var _default = getDataSeries;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(buildHeatMapDataPointFromExpression, 'buildHeatMapDataPointFromExpression', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(buildDataPointsFromRowExpressions, 'buildDataPointsFromRowExpressions', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_createDataPointsAndGroupThemByExperimentType, '_createDataPointsAndGroupThemByExperimentType', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(dataPointsToThresholdCategoriesMapper, 'dataPointsToThresholdCategoriesMapper', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(experimentProfilesRowsAsDataPointsSplitByThresholds, 'experimentProfilesRowsAsDataPointsSplitByThresholds', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_splitDataSetByProportion, '_splitDataSetByProportion', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(splitGeneRowsIntoProportionalSeriesOfDataPoints, 'splitGeneRowsIntoProportionalSeriesOfDataPoints', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_bucketsIntoSeries, '_bucketsIntoSeries', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(getDataSeries, 'getDataSeries', 'src/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/heatmapDataSeries.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_lodash2, '_lodash2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(buildHeatMapDataPointFromExpression, 'buildHeatMapDataPointFromExpression', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(buildDataPointsFromRowExpressions, 'buildDataPointsFromRowExpressions', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_createDataPointsAndGroupThemByExperimentType, '_createDataPointsAndGroupThemByExperimentType', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(dataPointsToThresholdCategoriesMapper, 'dataPointsToThresholdCategoriesMapper', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(experimentProfilesRowsAsDataPointsSplitByThresholds, 'experimentProfilesRowsAsDataPointsSplitByThresholds', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_splitDataSetByProportion, '_splitDataSetByProportion', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(splitGeneRowsIntoProportionalSeriesOfDataPoints, 'splitGeneRowsIntoProportionalSeriesOfDataPoints', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_bucketsIntoSeries, '_bucketsIntoSeries', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(getDataSeries, 'getDataSeries', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapDataSeries.js');
	}();

	;

/***/ },

/***/ 2394:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.getHeatmapYAxisCategories = exports.getHeatmapXAxisCategories = undefined;

	var _extends = Object.assign || function (target) {
	  for (var i = 1; i < arguments.length; i++) {
	    var source = arguments[i];for (var key in source) {
	      if (Object.prototype.hasOwnProperty.call(source, key)) {
	        target[key] = source[key];
	      }
	    }
	  }return target;
	};

	var _url = __webpack_require__(437);

	var _url2 = _interopRequireDefault(_url);

	var _path = __webpack_require__(443);

	var _path2 = _interopRequireDefault(_path);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	  return obj && obj.__esModule ? obj : { default: obj };
	}

	// For each column grouping get the groups that contain a specific ID, or the group Unmapped if it has no groups
	var getGroupsThatContainId = function getGroupsThatContainId(columnGroupings, id) {
	  return columnGroupings.map(function (grouping) {
	    var values = grouping.groups.filter(function (group) {
	      return group.values.includes(id);
	    }).map(function (group) {
	      return {
	        label: group.name,
	        id: group.id
	      };
	    });

	    return {
	      name: grouping.name,
	      memberName: grouping.memberName,
	      values: values.length ? values : [{ label: 'Unmapped', id: '' }]
	    };
	  });
	};

	var getHeatmapXAxisCategories = function getHeatmapXAxisCategories(_ref) {
	  var columnHeaders = _ref.columnHeaders,
	      columnGroupings = _ref.columnGroupings,
	      experiment = _ref.experiment,
	      inProxy = _ref.inProxy,
	      atlasUrl = _ref.atlasUrl,
	      pathToResources = _ref.pathToResources;

	  if ((0, _experimentTypeUtils.isMultiExperiment)(experiment)) {
	    return columnHeaders.map(function (columnHeader) {
	      return {
	        label: columnHeader.factorValue,
	        id: columnHeader.factorValueOntologyTermId || '',
	        info: {
	          trackId: '',
	          tooltip: {},
	          groupings: getGroupsThatContainId(columnGroupings, columnHeader.factorValueOntologyTermId || '')
	        }
	      };
	    });
	  } else if ((0, _experimentTypeUtils.isDifferential)(experiment)) {
	    return columnHeaders.map(function (columnHeader) {
	      return {
	        label: columnHeader.displayName,
	        id: columnHeader.id,
	        info: {
	          trackId: columnHeader.id,
	          tooltip: _extends({
	            resources: columnHeader.resources.map(function (resource) {
	              return {
	                type: resource.type,
	                url: _url2.default.resolve(inProxy + atlasUrl, resource.uri),
	                icon: _url2.default.resolve(pathToResources, _path2.default.basename(__webpack_require__(2395)("./" + resource.type + '-icon.png')))
	              };
	            })
	          }, columnHeader.contrastSummary),
	          groupings: []
	        }
	      };
	    });
	  } else {
	    return columnHeaders.map(function (columnHeader) {
	      return {
	        label: columnHeader.factorValue,
	        id: columnHeader.factorValueOntologyTermId || '',
	        info: {
	          trackId: columnHeader.assayGroupId,
	          tooltip: {
	            properties: columnHeader.assayGroupSummary.properties,
	            replicates: columnHeader.assayGroupSummary.replicates
	          },
	          groupings: getGroupsThatContainId(columnGroupings, columnHeader.factorValueOntologyTermId || '')
	        }
	      };
	    });
	  }
	};

	var getHeatmapYAxisCategories = function getHeatmapYAxisCategories(_ref2) {
	  var rows = _ref2.rows,
	      geneQuery = _ref2.geneQuery,
	      experiment = _ref2.experiment,
	      inProxy = _ref2.inProxy,
	      atlasUrl = _ref2.atlasUrl;
	  return rows.map(function (profile) {
	    return {
	      label: profile.name,
	      id: profile.id,
	      info: {
	        trackId: profile.id,
	        designElement: profile.designElement || '',
	        url: _url2.default.resolve(inProxy + atlasUrl, profile.uri)
	      }
	    };
	  });
	};

	exports.getHeatmapXAxisCategories = getHeatmapXAxisCategories;
	exports.getHeatmapYAxisCategories = getHeatmapYAxisCategories;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(getGroupsThatContainId, 'getGroupsThatContainId', 'src/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(getHeatmapXAxisCategories, 'getHeatmapXAxisCategories', 'src/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(getHeatmapYAxisCategories, 'getHeatmapYAxisCategories', 'src/load/heatmapAxisCategories.js');
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(_extends, '_extends', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(_url2, '_url2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(_path2, '_path2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(getGroupsThatContainId, 'getGroupsThatContainId', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(getHeatmapXAxisCategories, 'getHeatmapXAxisCategories', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(getHeatmapYAxisCategories, 'getHeatmapYAxisCategories', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');

	  __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapAxisCategories.js');
	}();

	;

/***/ },

/***/ 2395:
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./gsea_go-icon.png": 2396,
		"./gsea_interpro-icon.png": 2397,
		"./gsea_reactome-icon.png": 2398,
		"./ma-plot-icon.png": 2399
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 2395;


/***/ },

/***/ 2396:
943,

/***/ 2397:
944,

/***/ 2398:
945,

/***/ 2399:
946,

/***/ 2400:
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	var quartilesFromExpression = function quartilesFromExpression(expression) {
	  return expression.quartiles ? [expression.quartiles.min, expression.quartiles.lower, expression.quartiles.median, expression.quartiles.upper, expression.quartiles.max] : [];
	};

	var tryCreateBoxplotData = function tryCreateBoxplotData(_ref) {
	  var dataRow = _ref.dataRow,
	      columnHeaders = _ref.columnHeaders;

	  var dataSeries = dataRow.expressions.map(quartilesFromExpression);

	  if (dataSeries.map(function (e) {
	    return e.length;
	  }).reduce(function (l, r) {
	    return l + r;
	  }, 0)) {
	    return {
	      dataSeries: dataSeries,
	      xAxisCategories: columnHeaders.map(function (header) {
	        return header.factorValue;
	      }),
	      title: dataRow.name + " - " + dataRow.id,
	      unit: dataRow.expressionUnit
	    };
	  } else {
	    return null;
	  }
	};

	var _default = function _default(_ref2) {
	  var profiles = _ref2.profiles,
	      columnHeaders = _ref2.columnHeaders;
	  return profiles.rows.length === 1 ? tryCreateBoxplotData({ dataRow: profiles.rows[0], columnHeaders: columnHeaders }) : null;
	};

	exports.default = _default;
	;

	var _temp = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(quartilesFromExpression, "quartilesFromExpression", "src/load/boxplotData.js");

	  __REACT_HOT_LOADER__.register(tryCreateBoxplotData, "tryCreateBoxplotData", "src/load/boxplotData.js");

	  __REACT_HOT_LOADER__.register(_default, "default", "src/load/boxplotData.js");
	}();

	;
	;

	var _temp2 = function () {
	  if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	    return;
	  }

	  __REACT_HOT_LOADER__.register(quartilesFromExpression, "quartilesFromExpression", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/boxplotData.js");

	  __REACT_HOT_LOADER__.register(tryCreateBoxplotData, "tryCreateBoxplotData", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/boxplotData.js");

	  __REACT_HOT_LOADER__.register(_default, "_default", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/boxplotData.js");

	  __REACT_HOT_LOADER__.register(_temp, "_temp", "/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/boxplotData.js");
	}();

	;

/***/ },

/***/ 2401:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _lodash = __webpack_require__(434);

	var _lodash2 = _interopRequireDefault(_lodash);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	//apply rank first, use comparator to resolve ties
	var createOrdering = function createOrdering(rank, comparator, arr) {
	    return arr.map(function (e, ix) {
	        return [e, ix];
	    })
	    //lower ranks go to the beginning of series
	    .sort(function (e_ixLeft, e_ixRight) {
	        return rank[e_ixLeft[1]] - rank[e_ixRight[1]] || comparator(e_ixLeft[0], e_ixRight[0]);
	    }).map(function (e_ix) {
	        return e_ix[1];
	    });
	};

	var createAlphabeticalOrdering = function createAlphabeticalOrdering(property, arr) {
	    return createOrdering(arr.map(_lodash2.default.constant(0)), comparatorByProperty(property), arr);
	};

	var comparatorByProperty = _lodash2.default.curry(function (property, e1, e2) {
	    return e1[property].localeCompare(e2[property]);
	});

	var rankColumnsByWhereTheyAppearFirst = function rankColumnsByWhereTheyAppearFirst(expressions) {
	    return _lodash2.default.chain(expressions).map(function (row) {
	        return row.map(function (e) {
	            return +e.hasOwnProperty('value');
	        });
	    }).thru(_lodash2.default.spread(_lodash2.default.zip)).map(function (column) {
	        return column.map(function (e, ix) {
	            return e * (ix + 1);
	        }).filter(_lodash2.default.identity);
	    }).map(_lodash2.default.min).value();
	};

	var highestColumnRankPossible = function highestColumnRankPossible(expressions) {
	    return expressions.length ? expressions[0].length : Number.MAX_VALUE;
	};

	var thresholdColumnsByExpressionAboveCutoff = function thresholdColumnsByExpressionAboveCutoff(expressions) {
	    return rankColumnsByExpression(expressions, 0)
	    //check if the function assigned the rank value corresponding to everything filtered off
	    .map(function (e) {
	        return e === highestColumnRankPossible(expressions) ? 1 : 0;
	    });
	};

	var rankColumnsByExpression = function rankColumnsByExpression(expressions, minimalExpression) {
	    var includeInRanking = typeof minimalExpression === 'number' ? function (e) {
	        return e.hasOwnProperty('value') && !isNaN(e.value) && Math.abs(e.value) > minimalExpression;
	    } : function (e) {
	        return e.hasOwnProperty('value') && !isNaN(e.value);
	    };

	    return _lodash2.default.chain(expressions).map(function (row) {
	        var valuesInRow = _lodash2.default.uniq(row.filter(includeInRanking).map(function (e) {
	            return e.value;
	        }).sort(function (l, r) {
	            return r - l;
	        }));

	        return row.map(function (e) {
	            return includeInRanking(e) ? valuesInRow.indexOf(e.value) : 'missing';
	        });
	    }).thru(_lodash2.default.spread(_lodash2.default.zip)).map(function (ranks) {
	        return ranks.filter(_lodash2.default.negate(isNaN));
	    }).map(function (ranks) {
	        return ranks.length ? _lodash2.default.sum(ranks) / ranks.length : highestColumnRankPossible(expressions);
	    }).value();
	};

	var rankColumnsByThreshold = function rankColumnsByThreshold(threshold, expressions) {
	    return expressions.map(function (row) {
	        return row.map(function (point) {
	            return +(point.hasOwnProperty('value') && point.value !== 0);
	        });
	    }).reduce(function (r1, r2) {
	        return r1.map(function (el, ix) {
	            return el + r2[ix];
	        }, _lodash2.default.fill(Array(expressions.length ? expressions[0].length : 0), 0));
	    }).map(function (countOfExperimentsWhereTissueExpressedAboveCutoff) {
	        return countOfExperimentsWhereTissueExpressedAboveCutoff > expressions.length * threshold ? 0 : 1;
	    });
	};

	var noOrdering = function noOrdering(arr) {
	    return arr.map(function (el, ix) {
	        return ix;
	    });
	};

	var combineRanks = function combineRanks(ranksAndWeights) {
	    return _lodash2.default.chain(ranksAndWeights).map(function (rankAndWeight) {
	        return rankAndWeight[0].map(function (rank) {
	            return rank * rankAndWeight[1];
	        });
	    }).thru(_lodash2.default.spread(_lodash2.default.zip)).map(_lodash2.default.sum).value();
	};

	var createOrderings = function createOrderings(expressions, columnHeaders, rows, experiment) {
	    var transposed = _lodash2.default.zip.apply(_lodash2.default, expressions);

	    if ((0, _experimentTypeUtils.isMultiExperiment)(experiment)) {
	        return {
	            default: {
	                name: 'By experiment type',
	                columns: createAlphabeticalOrdering('factorValue', columnHeaders),
	                rows: noOrdering(rows)
	            },
	            alphabetical: {
	                name: 'Alphabetical order',
	                columns: createAlphabeticalOrdering('factorValue', columnHeaders),
	                rows: createAlphabeticalOrdering('name', rows)
	            },
	            geneExpression: {
	                name: 'Gene expression rank',
	                columns: createOrdering(combineRanks([[rankColumnsByWhereTheyAppearFirst(expressions), 1], [rankColumnsByExpression(expressions), 1e3], [rankColumnsByThreshold(0.05 + 0.4 / Math.pow(1 + transposed.length / 8, 0.4), expressions), 1e6], [thresholdColumnsByExpressionAboveCutoff(expressions), 1e7]]), comparatorByProperty('factorValue'), columnHeaders),
	                rows: createOrdering(combineRanks([[rankColumnsByExpression(transposed), 1e3], [rankColumnsByThreshold(0.05 + 0.4 / (1 + expressions.length / 5), transposed), 1e6]]), comparatorByProperty('name'), rows)
	            }
	        };
	    } else {
	        return {
	            default: {
	                name: 'Default',
	                columns: noOrdering(columnHeaders),
	                rows: noOrdering(rows)
	            }
	        };
	    }
	};

	var extractExpressionValues = function extractExpressionValues(rows, experiment) {
	    var _valueFieldExtractor = function _valueFieldExtractor(valueField) {
	        return function (expression) {
	            return expression.hasOwnProperty(valueField) ? { value: expression[valueField] } : {};
	        };
	    };

	    return rows.map(function (row) {
	        return row.expressions.map(_valueFieldExtractor((0, _experimentTypeUtils.isDifferential)(experiment) ? 'foldChange' : 'value'));
	    });
	};

	var createOrderingsForData = function createOrderingsForData(experiment, rows, columnHeaders) {
	    return createOrderings(extractExpressionValues(rows, experiment), columnHeaders, rows, experiment);
	};

	var _default = createOrderingsForData;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(createOrdering, 'createOrdering', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createAlphabeticalOrdering, 'createAlphabeticalOrdering', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(comparatorByProperty, 'comparatorByProperty', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByWhereTheyAppearFirst, 'rankColumnsByWhereTheyAppearFirst', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(highestColumnRankPossible, 'highestColumnRankPossible', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(thresholdColumnsByExpressionAboveCutoff, 'thresholdColumnsByExpressionAboveCutoff', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByExpression, 'rankColumnsByExpression', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByThreshold, 'rankColumnsByThreshold', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(noOrdering, 'noOrdering', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(combineRanks, 'combineRanks', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createOrderings, 'createOrderings', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(extractExpressionValues, 'extractExpressionValues', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createOrderingsForData, 'createOrderingsForData', 'src/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/heatmapOrderings.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_lodash2, '_lodash2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createOrdering, 'createOrdering', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createAlphabeticalOrdering, 'createAlphabeticalOrdering', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(comparatorByProperty, 'comparatorByProperty', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByWhereTheyAppearFirst, 'rankColumnsByWhereTheyAppearFirst', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(highestColumnRankPossible, 'highestColumnRankPossible', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(thresholdColumnsByExpressionAboveCutoff, 'thresholdColumnsByExpressionAboveCutoff', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByExpression, 'rankColumnsByExpression', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(rankColumnsByThreshold, 'rankColumnsByThreshold', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(noOrdering, 'noOrdering', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(combineRanks, 'combineRanks', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createOrderings, 'createOrderings', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(extractExpressionValues, 'extractExpressionValues', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(createOrderingsForData, 'createOrderingsForData', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapOrderings.js');
	}();

	;

/***/ },

/***/ 2402:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _color = __webpack_require__(950);

	var _color2 = _interopRequireDefault(_color);

	var _experimentTypeUtils = __webpack_require__(2361);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var highlightColour = function highlightColour(c) {
	    return c.light() ? c.clone().lighten(0.5) : c.clone().saturate(0.3).darken(0.5);
	};

	var dataClassesFromSeries = function dataClassesFromSeries(dataSeries) {
	    // No need to validate here, as it’s already been done in Container.jsx (cf. with previous version of ColorAxis.js)
	    var xs = dataSeries.map(function (series) {
	        return series.data.length === 0 && series.info.name === 'Below cutoff' ? {
	            data: [{ value: 0.0 }],
	            colour: series.info.colour
	        } : {
	            data: series.data,
	            colour: series.info.colour
	        };
	    }).filter(function (series) {
	        return series.data.length > 0;
	    }).map(function (series, ix, self) {
	        var theseSeriesValuesSorted = series.data.map(function (point) {
	            return point.value;
	        });
	        theseSeriesValuesSorted.sort(function (l, r) {
	            return l - r;
	        });

	        return {
	            min: theseSeriesValuesSorted[0],
	            minColour: ix === 0 ? highlightColour((0, _color2.default)(self[ix].colour)) : (0, _color2.default)(self[ix].colour).mix((0, _color2.default)(self[ix - 1].colour)),
	            max: theseSeriesValuesSorted[theseSeriesValuesSorted.length - 1],
	            maxColour: ix === self.length - 1 ? highlightColour((0, _color2.default)(self[ix].colour)) : (0, _color2.default)(self[ix].colour).mix((0, _color2.default)(self[ix + 1].colour)),
	            median: theseSeriesValuesSorted[Math.floor(series.data.length / 2)],
	            medianColour: (0, _color2.default)(self[ix].colour),
	            sortedValues: theseSeriesValuesSorted
	        };
	    });

	    var needToSplit = function needToSplit(x) {
	        return x.sortedValues.length > 3 && x.sortedValues[0] !== x.sortedValues[x.sortedValues.length - 1] && x.minColour.rgbString() !== x.maxColour.rgbString();
	    };

	    var splitInHalf = function splitInHalf(x) {
	        return [{
	            min: x.min,
	            minColour: x.minColour,
	            max: x.median,
	            maxColour: x.medianColour,
	            median: x.sortedValues[Math.floor(x.sortedValues.length / 4)],
	            medianColour: x.minColour.clone().mix(x.medianColour),
	            sortedValues: x.sortedValues.slice(0, Math.floor(x.sortedValues.length / 2))
	        }, {
	            min: x.median,
	            minColour: x.medianColour,
	            max: x.max,
	            maxColour: x.maxColour,
	            median: x.sortedValues[Math.floor(3 * x.sortedValues.length / 4)],
	            medianColour: x.medianColour.clone().mix(x.maxColour),
	            sortedValues: x.sortedValues.slice(Math.floor(x.sortedValues.length / 2))
	        }];
	    };

	    var l = Number.MIN_VALUE;
	    var L = xs.length;
	    while (l < L) {
	        xs = [].concat.apply([], xs.map(function (x) {
	            if (needToSplit(x)) {
	                return splitInHalf(x);
	            } else {
	                return [x];
	            }
	        }));
	        l = L;
	        L = xs.length;
	    }

	    // The format of dataClasses is defined in http://api.highcharts.com/highmaps/colorAxis.dataClasses
	    return xs.map(function (x) {
	        return {
	            from: x.min,
	            to: x.max,
	            color: x.medianColour.hexString()
	        };
	    });
	};

	var unitsUsedInDataSeries = function unitsUsedInDataSeries(dataSeries) {
	    return [].concat.apply([], dataSeries.map(function (series) {
	        return series.data;
	    })).map(function (point) {
	        return point.info.unit;
	    }).filter(function (el) {
	        return el;
	    }).filter(function (el, ix, self) {
	        return self.indexOf(el) === ix;
	    }).join();
	};

	var getColourAxisFromDataSeries = function getColourAxisFromDataSeries(experiment, dataSeries) {
	    return (0, _experimentTypeUtils.isMultiExperiment)(experiment) ? null : {
	        dataClasses: dataClassesFromSeries(dataSeries),
	        unit: unitsUsedInDataSeries(dataSeries)
	    };
	};

	var _default = getColourAxisFromDataSeries;
	exports.default = _default;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(highlightColour, 'highlightColour', 'src/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(dataClassesFromSeries, 'dataClassesFromSeries', 'src/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(unitsUsedInDataSeries, 'unitsUsedInDataSeries', 'src/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(getColourAxisFromDataSeries, 'getColourAxisFromDataSeries', 'src/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(_default, 'default', 'src/load/heatmapColourAxis.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_color2, '_color2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(highlightColour, 'highlightColour', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(dataClassesFromSeries, 'dataClassesFromSeries', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(unitsUsedInDataSeries, 'unitsUsedInDataSeries', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(getColourAxisFromDataSeries, 'getColourAxisFromDataSeries', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(_default, '_default', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapColourAxis.js');
	}();

	;

/***/ },

/***/ 2403:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.getColumnGroupingFilters = exports.getExpressionLevelFilters = undefined;

	var _lodash = __webpack_require__(434);

	var _lodash2 = _interopRequireDefault(_lodash);

	function _interopRequireDefault(obj) {
	    return obj && obj.__esModule ? obj : { default: obj };
	}

	var getExpressionLevelFilters = function getExpressionLevelFilters(experiment, dataSeries) {
	    return {
	        name: 'Expression value' + (experiment ? ' \u2013 relative' : ''),
	        values: dataSeries.map(function (series) {
	            return {
	                name: series.info.name,
	                disabled: series.data.length === 0
	            };
	        })
	    };
	};

	var getColumnGroupingFilters = function getColumnGroupingFilters(xAxisCategories) {
	    var groupingTriplets = _lodash2.default.flattenDeep(xAxisCategories.reduce(function (acc, columnHeader) {
	        var _groupingTriplets = columnHeader.info.groupings.map(function (grouping) {
	            return grouping.values.map(function (groupingValue) {
	                return {
	                    name: grouping.name,
	                    groupingLabel: groupingValue.label,
	                    columnLabel: columnHeader.label
	                };
	            });
	        });
	        acc.push(_groupingTriplets);

	        return acc;
	    }, []));

	    var groupingNames = _lodash2.default.uniq(groupingTriplets.map(function (groupingTriplet) {
	        return groupingTriplet.name;
	    }));

	    return groupingNames.map(function (groupingName) {
	        var columnLabels = _lodash2.default.uniq(groupingTriplets.filter(function (groupingTriplet) {
	            return groupingTriplet.name === groupingName;
	        }).map(function (groupingTriplet) {
	            return groupingTriplet.columnLabel;
	        }));

	        var groupingLabels = _lodash2.default.uniq(groupingTriplets.filter(function (groupingTriplet) {
	            return groupingTriplet.name === groupingName;
	        }).map(function (groupingTriplet) {
	            return groupingTriplet.groupingLabel;
	        })).sort();

	        var groupingLabelsWithUnmappedLast = groupingLabels.filter(function (l) {
	            return l !== 'Unmapped';
	        }).concat(groupingLabels.find(function (l) {
	            return l === 'Unmapped';
	        }) || []);

	        return {
	            name: groupingName,
	            values: columnLabels.map(function (label) {
	                return {
	                    name: label,
	                    disabled: false // Guaranteed because values are extracted from xAxisCategories
	                };
	            }),
	            valueGroupings: groupingLabelsWithUnmappedLast.map(function (groupingLabel) {
	                return [groupingLabel, _lodash2.default.sortedUniq(groupingTriplets.filter(function (groupingTriplet) {
	                    return groupingTriplet.name === groupingName && groupingTriplet.groupingLabel === groupingLabel;
	                }).map(function (groupingTriplet) {
	                    return groupingTriplet.columnLabel;
	                }))];
	            })
	        };
	    });
	};

	exports.getExpressionLevelFilters = getExpressionLevelFilters;
	exports.getColumnGroupingFilters = getColumnGroupingFilters;
	;

	var _temp = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(getExpressionLevelFilters, 'getExpressionLevelFilters', 'src/load/heatmapFilters.js');

	    __REACT_HOT_LOADER__.register(getColumnGroupingFilters, 'getColumnGroupingFilters', 'src/load/heatmapFilters.js');
	}();

	;
	;

	var _temp2 = function () {
	    if (typeof __REACT_HOT_LOADER__ === 'undefined') {
	        return;
	    }

	    __REACT_HOT_LOADER__.register(_lodash2, '_lodash2', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapFilters.js');

	    __REACT_HOT_LOADER__.register(_interopRequireDefault, '_interopRequireDefault', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapFilters.js');

	    __REACT_HOT_LOADER__.register(getExpressionLevelFilters, 'getExpressionLevelFilters', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapFilters.js');

	    __REACT_HOT_LOADER__.register(getColumnGroupingFilters, 'getColumnGroupingFilters', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapFilters.js');

	    __REACT_HOT_LOADER__.register(_temp, '_temp', '/Users/wbazant/dev/atlas/gxa/src/main/javascript/atlas_bundles/baseline-expression/node_modules/expression-atlas-heatmap-highcharts/lib/load/heatmapFilters.js');
	}();

	;

/***/ },

/***/ 2404:
959,

/***/ 2405:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var Url = __webpack_require__(437);
	var QueryString = __webpack_require__(2406);

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

/***/ },

/***/ 2406:
[2747, 2407, 2408],

/***/ 2407:
/***/ function(module, exports) {

	// Copyright Joyent, Inc. and other Node contributors.
	//
	// Permission is hereby granted, free of charge, to any person obtaining a
	// copy of this software and associated documentation files (the
	// "Software"), to deal in the Software without restriction, including
	// without limitation the rights to use, copy, modify, merge, publish,
	// distribute, sublicense, and/or sell copies of the Software, and to permit
	// persons to whom the Software is furnished to do so, subject to the
	// following conditions:
	//
	// The above copyright notice and this permission notice shall be included
	// in all copies or substantial portions of the Software.
	//
	// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
	// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
	// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
	// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
	// USE OR OTHER DEALINGS IN THE SOFTWARE.

	'use strict';

	// If obj.hasOwnProperty has been overridden, then calling
	// obj.hasOwnProperty(prop) will break.
	// See: https://github.com/joyent/node/issues/1707
	function hasOwnProperty(obj, prop) {
	  return Object.prototype.hasOwnProperty.call(obj, prop);
	}

	module.exports = function(qs, sep, eq, options) {
	  sep = sep || '&';
	  eq = eq || '=';
	  var obj = {};

	  if (typeof qs !== 'string' || qs.length === 0) {
	    return obj;
	  }

	  var regexp = /\+/g;
	  qs = qs.split(sep);

	  var maxKeys = 1000;
	  if (options && typeof options.maxKeys === 'number') {
	    maxKeys = options.maxKeys;
	  }

	  var len = qs.length;
	  // maxKeys <= 0 means that we should not limit keys count
	  if (maxKeys > 0 && len > maxKeys) {
	    len = maxKeys;
	  }

	  for (var i = 0; i < len; ++i) {
	    var x = qs[i].replace(regexp, '%20'),
	        idx = x.indexOf(eq),
	        kstr, vstr, k, v;

	    if (idx >= 0) {
	      kstr = x.substr(0, idx);
	      vstr = x.substr(idx + 1);
	    } else {
	      kstr = x;
	      vstr = '';
	    }

	    k = decodeURIComponent(kstr);
	    v = decodeURIComponent(vstr);

	    if (!hasOwnProperty(obj, k)) {
	      obj[k] = v;
	    } else if (isArray(obj[k])) {
	      obj[k].push(v);
	    } else {
	      obj[k] = [obj[k], v];
	    }
	  }

	  return obj;
	};

	var isArray = Array.isArray || function (xs) {
	  return Object.prototype.toString.call(xs) === '[object Array]';
	};


/***/ },

/***/ 2408:
/***/ function(module, exports) {

	// Copyright Joyent, Inc. and other Node contributors.
	//
	// Permission is hereby granted, free of charge, to any person obtaining a
	// copy of this software and associated documentation files (the
	// "Software"), to deal in the Software without restriction, including
	// without limitation the rights to use, copy, modify, merge, publish,
	// distribute, sublicense, and/or sell copies of the Software, and to permit
	// persons to whom the Software is furnished to do so, subject to the
	// following conditions:
	//
	// The above copyright notice and this permission notice shall be included
	// in all copies or substantial portions of the Software.
	//
	// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
	// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
	// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
	// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
	// USE OR OTHER DEALINGS IN THE SOFTWARE.

	'use strict';

	var stringifyPrimitive = function(v) {
	  switch (typeof v) {
	    case 'string':
	      return v;

	    case 'boolean':
	      return v ? 'true' : 'false';

	    case 'number':
	      return isFinite(v) ? v : '';

	    default:
	      return '';
	  }
	};

	module.exports = function(obj, sep, eq, name) {
	  sep = sep || '&';
	  eq = eq || '=';
	  if (obj === null) {
	    obj = undefined;
	  }

	  if (typeof obj === 'object') {
	    return map(objectKeys(obj), function(k) {
	      var ks = encodeURIComponent(stringifyPrimitive(k)) + eq;
	      if (isArray(obj[k])) {
	        return map(obj[k], function(v) {
	          return ks + encodeURIComponent(stringifyPrimitive(v));
	        }).join(sep);
	      } else {
	        return ks + encodeURIComponent(stringifyPrimitive(obj[k]));
	      }
	    }).join(sep);

	  }

	  if (!name) return '';
	  return encodeURIComponent(stringifyPrimitive(name)) + eq +
	         encodeURIComponent(stringifyPrimitive(obj));
	};

	var isArray = Array.isArray || function (xs) {
	  return Object.prototype.toString.call(xs) === '[object Array]';
	};

	function map (xs, f) {
	  if (xs.map) return xs.map(f);
	  var res = [];
	  for (var i = 0; i < xs.length; i++) {
	    res.push(f(xs[i], i));
	  }
	  return res;
	}

	var objectKeys = Object.keys || function (obj) {
	  var res = [];
	  for (var key in obj) {
	    if (Object.prototype.hasOwnProperty.call(obj, key)) res.push(key);
	  }
	  return res;
	};


/***/ }

});