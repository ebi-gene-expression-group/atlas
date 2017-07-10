var expressionAtlasBrowseBySpecies =
webpackJsonp_name_([4],{

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;

	var _browseBySpeciesRenderer = __webpack_require__(2415);

	var _browseBySpeciesRenderer2 = _interopRequireDefault(_browseBySpeciesRenderer);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.render = _browseBySpeciesRenderer2.default;

/***/ },

/***/ 2415:
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

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _reactDom = __webpack_require__(35);

	var _reactDom2 = _interopRequireDefault(_reactDom);

	var _BrowseBySpecies = __webpack_require__(2416);

	var _BrowseBySpecies2 = _interopRequireDefault(_BrowseBySpecies);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	;

/***/ },

/***/ 2416:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _SpeciesItem = __webpack_require__(2417);

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

/***/ 2417:
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _react = __webpack_require__(2);

	var _react2 = _interopRequireDefault(_react);

	var _urijs = __webpack_require__(193);

	var _urijs2 = _interopRequireDefault(_urijs);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var EbiSpeciesIcon = __webpack_require__(1089).Icon;

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

/***/ }

});