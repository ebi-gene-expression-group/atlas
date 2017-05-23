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
	
	var _browseBySpeciesRenderer = __webpack_require__(/*! ./src/browseBySpeciesRenderer.jsx */ 2909);
	
	var _browseBySpeciesRenderer2 = _interopRequireDefault(_browseBySpeciesRenderer);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.render = _browseBySpeciesRenderer2.default;

/***/ },

/***/ 2909:
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
	
	var _BrowseBySpecies = __webpack_require__(/*! ./BrowseBySpecies.jsx */ 2910);
	
	var _BrowseBySpecies2 = _interopRequireDefault(_BrowseBySpecies);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	;

/***/ },

/***/ 2910:
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
	
	var _SpeciesItem = __webpack_require__(/*! ./SpeciesItem.jsx */ 2911);
	
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

/***/ 2911:
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
	
	var _urijs = __webpack_require__(/*! urijs */ 2912);
	
	var _urijs2 = _interopRequireDefault(_urijs);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 2916).Icon;
	
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
	                _react2.default.createElement(EbiSpeciesIcon, { species: props.species })
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

/***/ 2912:
/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3832, 2913, 2914, 2915, 2913, 2914, 2915],

/***/ 2913:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
197,

/***/ 2914:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
199,

/***/ 2915:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
200,

/***/ 2916:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/index.js ***!
  \**********************************************************************/
[4208, 2917, 2922],

/***/ 2917:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \*********************************************************************************/
[4209, 2918, 2921],

/***/ 2918:
/*!********************************************************************************************************************************************************!*\
  !*** ./~/style-loader!./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./ebi-visual-species.css */ 2919);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../~/style-loader/addStyles.js */ 491)(content, {});
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

/***/ 2919:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2920)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Taken from: https://github.com/ebiwd/EBI-Icon-fonts */\n\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    color: inherit;\n    content: attr(data-icon);\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2920:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
490,

/***/ 2921:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
992,

/***/ 2922:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/renderer.js ***!
  \*****************************************************************************/
[4212, 2917]

});
//# sourceMappingURL=expressionAtlasBrowseBySpecies.bundle.js.map