var expressionAtlasBrowseBySpecies =
webpackJsonp_name_([4],{

/***/ 0:
/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;
	
	var _browseBySpeciesRenderer = __webpack_require__(/*! ./src/browseBySpeciesRenderer.jsx */ 3087);
	
	var _browseBySpeciesRenderer2 = _interopRequireDefault(_browseBySpeciesRenderer);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.render = _browseBySpeciesRenderer2.default;

/***/ },

/***/ 3087:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	exports.default = function (_ref) {
	    var atlasUrl = _ref.atlasUrl,
	        speciesInfoList = _ref.speciesInfoList,
	        container = _ref.container;
	
	    _reactDom2.default.render(_react2.default.createElement(_BrowseBySpecies2.default, { atlasUrl: atlasUrl, speciesInfoList: speciesInfoList }), typeof container === 'string' ? document.getElementById(container) : container);
	};
	
	var _react = __webpack_require__(/*! react */ 2);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 35);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	var _BrowseBySpecies = __webpack_require__(/*! ./BrowseBySpecies.jsx */ 3088);
	
	var _BrowseBySpecies2 = _interopRequireDefault(_BrowseBySpecies);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	;

/***/ },

/***/ 3088:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var _react = __webpack_require__(/*! react */ 2);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _SpeciesItem = __webpack_require__(/*! ./SpeciesItem.jsx */ 3089);
	
	var _SpeciesItem2 = _interopRequireDefault(_SpeciesItem);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var BrowseBySpecies = function BrowseBySpecies(props) {
	    var speciesItems = props.speciesInfoList.map(function (speciesInfo) {
	        return _react2.default.createElement(_SpeciesItem2.default, _extends({ key: speciesInfo.species,
	            atlasUrl: props.atlasUrl
	        }, speciesInfo));
	    });
	
	    return _react2.default.createElement(
	        'div',
	        { className: 'row small-up-2 medium-up-3' },
	        speciesItems
	    );
	};
	
	BrowseBySpecies.propTypes = {
	    atlasUrl: _react2.default.PropTypes.string.isRequired,
	    speciesInfoList: _react2.default.PropTypes.arrayOf(_react2.default.PropTypes.shape({
	        species: _react2.default.PropTypes.string.isRequired,
	        totalExperiments: _react2.default.PropTypes.number.isRequired,
	        baselineExperiments: _react2.default.PropTypes.number.isRequired,
	        differentialExperiments: _react2.default.PropTypes.number.isRequired
	    })).isRequired
	};
	
	exports.default = BrowseBySpecies;

/***/ },

/***/ 3089:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	var _react = __webpack_require__(/*! react */ 2);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _urijs = __webpack_require__(/*! urijs */ 3090);
	
	var _urijs2 = _interopRequireDefault(_urijs);
	
	var _reactEbiSpecies = __webpack_require__(/*! react-ebi-species */ 3094);
	
	var _reactEbiSpecies2 = _interopRequireDefault(_reactEbiSpecies);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var SpeciesItem = function SpeciesItem(props) {
	    var allExperimentsUrl = (0, _urijs2.default)(props.atlasUrl).segment('experiments').addSearch({ organism: props.species });
	    var differentialExperimentsUrl = (0, _urijs2.default)(allExperimentsUrl).addSearch({ experimentType: 'differential' });
	    var baselineExperimentsUrl = (0, _urijs2.default)(allExperimentsUrl).addSearch({ experimentType: 'baseline' });
	
	    var speciesFirstCapitalLetter = props.species[0].toUpperCase() + props.species.substr(1);
	
	    return _react2.default.createElement(
	        'div',
	        { className: 'column column-block text-center combo' },
	        _react2.default.createElement(
	            'a',
	            { href: allExperimentsUrl },
	            _react2.default.createElement(
	                'span',
	                { className: 'large-species-icon' },
	                _react2.default.createElement(_reactEbiSpecies2.default, { species: props.species })
	            ),
	            _react2.default.createElement(
	                'h5',
	                { className: 'species' },
	                speciesFirstCapitalLetter
	            )
	        ),
	        _react2.default.createElement(
	            'p',
	            { className: 'experiments' },
	            props.totalExperiments,
	            ' experiments',
	            _react2.default.createElement('br', null),
	            _react2.default.createElement(
	                'a',
	                { href: baselineExperimentsUrl, className: 'baseline' },
	                _react2.default.createElement(
	                    'span',
	                    { 'data-tooltip': true, style: { cursor: 'unset', fontWeight: 'bold' },
	                        className: 'baseline tiny button-rd', title: 'Baseline experiments' },
	                    'B'
	                ),
	                props.baselineExperiments
	            ),
	            _react2.default.createElement(
	                'a',
	                { href: differentialExperimentsUrl, className: 'differential padding-left-medium' },
	                _react2.default.createElement(
	                    'span',
	                    { 'data-tooltip': true, style: { cursor: 'unset', fontWeight: 'bold' },
	                        className: 'differential tiny button-rd', title: 'Differential experiments' },
	                    'D'
	                ),
	                props.differentialExperiments
	            )
	        )
	    );
	};
	
	SpeciesItem.propTypes = {
	    atlasUrl: _react2.default.PropTypes.string.isRequired,
	    species: _react2.default.PropTypes.string.isRequired,
	    totalExperiments: _react2.default.PropTypes.number.isRequired,
	    baselineExperiments: _react2.default.PropTypes.number.isRequired,
	    differentialExperiments: _react2.default.PropTypes.number.isRequired
	};
	
	exports.default = SpeciesItem;

/***/ },

/***/ 3090:
/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[4044, 3091, 3092, 3093, 3091, 3092, 3093],

/***/ 3091:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
194,

/***/ 3092:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
196,

/***/ 3093:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
197,

/***/ 3094:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/lib/index.js ***!
  \**************************************************************************/
[4450, 3095],

/***/ 3095:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/lib/EbiSpeciesIcon.js ***!
  \***********************************************************************************/
[4451, 3096, 3103, 3106],

/***/ 3096:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/index.js ***!
  \***********************************************************************************/
[4041, 3097],

/***/ 3097:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/factoryWithTypeCheckers.js ***!
  \*****************************************************************************************************/
[4042, 3098, 3099, 3100, 3101, 3102],

/***/ 3098:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/~/fbjs/lib/emptyFunction.js ***!
  \******************************************************************************************************/
12,

/***/ 3099:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/~/fbjs/lib/invariant.js ***!
  \**************************************************************************************************/
8,

/***/ 3100:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/~/fbjs/lib/warning.js ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2014-2015, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var emptyFunction = __webpack_require__(/*! ./emptyFunction */ 3098);
	
	/**
	 * Similar to invariant but only logs a warning if the condition is not met.
	 * This can be used to log issues in development environments in critical
	 * paths. Removing the logging code for production environments will keep the
	 * same logic and follow the same code paths.
	 */
	
	var warning = emptyFunction;
	
	if (true) {
	  var printWarning = function printWarning(format) {
	    for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
	      args[_key - 1] = arguments[_key];
	    }
	
	    var argIndex = 0;
	    var message = 'Warning: ' + format.replace(/%s/g, function () {
	      return args[argIndex++];
	    });
	    if (typeof console !== 'undefined') {
	      console.error(message);
	    }
	    try {
	      // --- Welcome to debugging React ---
	      // This error was thrown as a convenience so that you can use this stack
	      // to find the callsite that caused this warning to fire.
	      throw new Error(message);
	    } catch (x) {}
	  };
	
	  warning = function warning(condition, format) {
	    if (format === undefined) {
	      throw new Error('`warning(condition, format, ...args)` requires a warning ' + 'message argument');
	    }
	
	    if (format.indexOf('Failed Composite propType: ') === 0) {
	      return; // Ignore CompositeComponent proptype check.
	    }
	
	    if (!condition) {
	      for (var _len2 = arguments.length, args = Array(_len2 > 2 ? _len2 - 2 : 0), _key2 = 2; _key2 < _len2; _key2++) {
	        args[_key2 - 2] = arguments[_key2];
	      }
	
	      printWarning.apply(undefined, [format].concat(args));
	    }
	  };
	}
	
	module.exports = warning;

/***/ },

/***/ 3101:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/lib/ReactPropTypesSecret.js ***!
  \******************************************************************************************************/
191,

/***/ 3102:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/checkPropTypes.js ***!
  \********************************************************************************************/
[4043, 3099, 3100, 3101],

/***/ 3103:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/lib/ebi-visual-species.css ***!
  \****************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./ebi-visual-species.css */ 3104);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../~/style-loader/addStyles.js */ 436)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./ebi-visual-species.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./ebi-visual-species.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 3104:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/lib/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 3105)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Taken from: https://github.com/ebiwd/EBI-Icon-fonts */\n/* https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css */\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal;\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    color: inherit;\n    content: attr(data-icon);\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3105:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
435,

/***/ 3106:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/lib/mapping.js ***!
  \****************************************************************************/
1123

});
//# sourceMappingURL=expressionAtlasBrowseBySpecies.bundle.js.map