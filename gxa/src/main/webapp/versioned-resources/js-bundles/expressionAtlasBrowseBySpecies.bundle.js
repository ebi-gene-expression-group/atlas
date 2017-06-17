var expressionAtlasBrowseBySpecies =
webpackJsonp_name_([4],{

/***/ 0:
/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;
	
	var _browseBySpeciesRenderer = __webpack_require__(/*! ./src/browseBySpeciesRenderer.jsx */ 2495);
	
	var _browseBySpeciesRenderer2 = _interopRequireDefault(_browseBySpeciesRenderer);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.render = _browseBySpeciesRenderer2.default;

/***/ }),

/***/ 2495:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
/***/ (function(module, exports, __webpack_require__) {

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
	
	var _BrowseBySpecies = __webpack_require__(/*! ./BrowseBySpecies.jsx */ 2496);
	
	var _BrowseBySpecies2 = _interopRequireDefault(_BrowseBySpecies);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	;

/***/ }),

/***/ 2496:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var _react = __webpack_require__(/*! react */ 2);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _SpeciesItem = __webpack_require__(/*! ./SpeciesItem.jsx */ 2497);
	
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

/***/ }),

/***/ 2497:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	var _react = __webpack_require__(/*! react */ 2);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _urijs = __webpack_require__(/*! urijs */ 2498);
	
	var _urijs2 = _interopRequireDefault(_urijs);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 2502).Icon;
	
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

/***/ }),

/***/ 2498:
/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3307, 2499, 2500, 2501, 2499, 2500, 2501],

/***/ 2499:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
189,

/***/ 2500:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
191,

/***/ 2501:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
192,

/***/ 2502:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/index.js ***!
  \**********************************************************************/
[3708, 2503, 2508],

/***/ 2503:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \*********************************************************************************/
[3709, 2504, 2507],

/***/ 2504:
/*!********************************************************************************************************************************************************!*\
  !*** ./~/style-loader!./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \********************************************************************************************************************************************************/
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./ebi-visual-species.css */ 2505);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../~/style-loader/addStyles.js */ 483)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./ebi-visual-species.css", function() {
				var newContent = require("!!../../css-loader/index.js!./ebi-visual-species.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 2505:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
[3711, 2506],

/***/ 2506:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
482,

/***/ 2507:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
886,

/***/ 2508:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/renderer.js ***!
  \*****************************************************************************/
[3712, 2503]

});
//# sourceMappingURL=expressionAtlasBrowseBySpecies.bundle.js.map