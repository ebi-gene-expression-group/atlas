var expressionAtlasBaselineExpression =
webpackJsonp_name_([3],{

/***/ 972:
/*!****************************************************!*\
  !*** ./atlas_bundles/baseline-expression/index.js ***!
  \****************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _baselineRenderer = __webpack_require__(/*! ./src/baselineRenderer.jsx */ 973);

var _baselineRenderer2 = _interopRequireDefault(_baselineRenderer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.render = _baselineRenderer2.default;

/***/ }),

/***/ 973:
/*!********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/baselineRenderer.jsx ***!
  \********************************************************************/
/*! dynamic exports provided */
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

var _reactDom = __webpack_require__(/*! react-dom */ 9);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _BaselineRouter = __webpack_require__(/*! ./BaselineRouter.jsx */ 974);

var _BaselineRouter2 = _interopRequireDefault(_BaselineRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/***/ }),

/***/ 974:
/*!******************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineRouter.jsx ***!
  \******************************************************************/
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

var _BaselineFacetsTree = __webpack_require__(/*! ./facets-tree/BaselineFacetsTree.jsx */ 975);

var _BaselineFacetsTree2 = _interopRequireDefault(_BaselineFacetsTree);

var _BaselineHeatmaps = __webpack_require__(/*! ./BaselineHeatmaps.jsx */ 978);

var _BaselineHeatmaps2 = _interopRequireDefault(_BaselineHeatmaps);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var UrlManager = __webpack_require__(/*! ./urlManager.js */ 980);

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

/***/ 975:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/BaselineFacetsTree.jsx ***!
  \**********************************************************************************/
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

var _Facet = __webpack_require__(/*! ./Facet.jsx */ 976);

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
    facets: _propTypes2.default.arrayOf(_propTypes2.default.shape({
        facetName: _propTypes2.default.string.isRequired,
        facetItems: _propTypes2.default.arrayOf(_propTypes2.default.shape({
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

/***/ 976:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/Facet.jsx ***!
  \*********************************************************************/
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

var _FacetItem = __webpack_require__(/*! ./FacetItem.jsx */ 977);

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
    facetItems: _propTypes2.default.arrayOf(_propTypes2.default.shape({
        name: _propTypes2.default.string.isRequired,
        value: _propTypes2.default.string.isRequired,
        checked: _propTypes2.default.bool.isRequired
    })).isRequired,
    setChecked: _propTypes2.default.func.isRequired
};

exports.default = Facet;

/***/ }),

/***/ 977:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/facets-tree/FacetItem.jsx ***!
  \*************************************************************************/
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

/***/ 978:
/*!********************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineHeatmaps.jsx ***!
  \********************************************************************/
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

var _BaselineHeatmapWidget = __webpack_require__(/*! ./BaselineHeatmapWidget.jsx */ 979);

var _BaselineHeatmapWidget2 = _interopRequireDefault(_BaselineHeatmapWidget);

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
    heatmaps: _propTypes2.default.arrayOf(_propTypes2.default.shape({
        species: _propTypes2.default.string.isRequired,
        factor: _propTypes2.default.shape({
            name: _propTypes2.default.string.isRequired,
            value: _propTypes2.default.string.isRequired
        })
    })).isRequired
};

exports.default = BaselineHeatmaps;

/***/ }),

/***/ 979:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/BaselineHeatmapWidget.jsx ***!
  \*************************************************************************/
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

var _expressionAtlasHeatmapHighcharts = __webpack_require__(/*! expression-atlas-heatmap-highcharts */ 121);

var _expressionAtlasHeatmapHighcharts2 = _interopRequireDefault(_expressionAtlasHeatmapHighcharts);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var capitalizeFirstLetter = function capitalizeFirstLetter(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
};

var BaselineHeatmapWidget = function BaselineHeatmapWidget(props) {
    return _react2.default.createElement(
        'div',
        { className: 'row column expanded margin-top-large margin-bottom-xlarge' },
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

/***/ 980:
/*!*************************************************************!*\
  !*** ./atlas_bundles/baseline-expression/src/urlManager.js ***!
  \*************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Url = __webpack_require__(/*! url */ 119);
var QueryString = __webpack_require__(/*! querystring */ 189);

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

/***/ })

},[972]);
//# sourceMappingURL=expressionAtlasBaselineExpression.bundle.js.map