var expressionAtlasBrowseBySpecies =
webpackJsonp_name_([3],{

/***/ 1196:
/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _browseBySpeciesRenderer = __webpack_require__(/*! ./src/browseBySpeciesRenderer.jsx */ 1197);

var _browseBySpeciesRenderer2 = _interopRequireDefault(_browseBySpeciesRenderer);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.render = _browseBySpeciesRenderer2.default;

/***/ }),

/***/ 1197:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

exports.default = function (_ref) {
    var atlasUrl = _ref.atlasUrl,
        speciesInfoList = _ref.speciesInfoList,
        container = _ref.container;

    _reactDom2.default.render(_react2.default.createElement(_BrowseBySpecies2.default, { atlasUrl: atlasUrl, speciesInfoList: speciesInfoList }), typeof container === 'string' ? document.getElementById(container) : container);
};

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 11);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _BrowseBySpecies = __webpack_require__(/*! ./BrowseBySpecies.jsx */ 1198);

var _BrowseBySpecies2 = _interopRequireDefault(_BrowseBySpecies);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

;

/***/ }),

/***/ 1198:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _SpeciesItem = __webpack_require__(/*! ./SpeciesItem.jsx */ 1199);

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

/***/ 1199:
/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _urijs = __webpack_require__(/*! urijs */ 19);

var _urijs2 = _interopRequireDefault(_urijs);

var _reactEbiSpecies = __webpack_require__(/*! react-ebi-species */ 260);

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

/***/ }),

/***/ 260:
/*!*****************************************************!*\
  !*** ./node_modules/react-ebi-species/lib/index.js ***!
  \*****************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = exports.default = undefined;

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 11);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _EbiSpeciesIcon = __webpack_require__(/*! ./EbiSpeciesIcon.js */ 261);

var _EbiSpeciesIcon2 = _interopRequireDefault(_EbiSpeciesIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, target) {
  _reactDom2.default.render(_react2.default.createElement(_EbiSpeciesIcon2.default, options), document.getElementById(target));
};

exports.default = _EbiSpeciesIcon2.default;
exports.render = render;

/***/ }),

/***/ 261:
/*!**************************************************************!*\
  !*** ./node_modules/react-ebi-species/lib/EbiSpeciesIcon.js ***!
  \**************************************************************/
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

__webpack_require__(/*! ./ebi-visual-species.css */ 262);

var _mapping = __webpack_require__(/*! ./mapping.js */ 264);

var _mapping2 = _interopRequireDefault(_mapping);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var EbiSpeciesIcon = function EbiSpeciesIcon(props) {
  var groupAndIcon = (0, _mapping2.default)(props.species);
  return _react2.default.createElement('span', {
    className: 'react-ebi-species-icon',
    'data-icon': groupAndIcon[1],
    style: { color: props.colourOverride || props.groupColours[groupAndIcon[0]] },
    title: props.species });
};

EbiSpeciesIcon.propTypes = {
  species: _propTypes2.default.string.isRequired,
  colourOverride: _propTypes2.default.string,
  groupColours: _propTypes2.default.shape({
    mammals: _propTypes2.default.string.isRequired,
    plants: _propTypes2.default.string.isRequired,
    other: _propTypes2.default.string.isRequired
  }).isRequired
};

EbiSpeciesIcon.defaultProps = {
  species: 'oryctolagus cuniculus', //rabbit is objectively the best species
  groupColours: {
    mammals: 'red',
    plants: 'green',
    other: 'blue'
  }
};

exports.default = EbiSpeciesIcon;

/***/ }),

/***/ 262:
/*!*******************************************************************!*\
  !*** ./node_modules/react-ebi-species/lib/ebi-visual-species.css ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(/*! !../../css-loader!./ebi-visual-species.css */ 263);
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

/***/ 263:
/*!*********************************************************************************************!*\
  !*** ./node_modules/css-loader!./node_modules/react-ebi-species/lib/ebi-visual-species.css ***!
  \*********************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 16)(undefined);
// imports


// module
exports.push([module.i, "/* Taken from: https://github.com/ebiwd/EBI-Icon-fonts */\n/* https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css */\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal;\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    color: inherit;\n    content: attr(data-icon);\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);

// exports


/***/ }),

/***/ 264:
/*!*******************************************************!*\
  !*** ./node_modules/react-ebi-species/lib/mapping.js ***!
  \*******************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var mapping = {
  mammals: {
    "a": ["alpaca", "vicugna pacos"],
    "A": ["cat", "felis catus"],
    "C": ["cow", "bos taurus"],
    "d": ["dog", "canis lupus", "canis lupus familiaris"],
    "D": ["dolphin"],
    "e": ["elephant", "loxodonta africana", "loxodonta cyclotis", "elephas maximus"],
    "g": ["guinea pig", "cavia porcellus"],
    "G": ["gorilla", "gorilla gorilla"],
    "h": ["horse", "equus caballus"],
    "H": ["human", "homo sapiens"],
    "i": ["chimpanzee", "pan paniscus", "pan troglodytes"],
    "I": ["squirrel"],
    "l": ["armadillo"],
    "m": ["goat"],
    "M": ["mouse", "mus musculus"],
    "N": ["mouse lemur"],
    "o": ["hedgehog", "erinaceus europaeus"],
    "p": ["pig", "sus scrofa"],
    "Q": ["shrew"],
    "r": ["monkey", "macaca mulatta"],
    "R": ["rat", "rattus norvegicus"],
    "t": ["rabbit", "oryctolagus cuniculus"],
    "U": ["platypus", "ornithorhynchus anatinus"],
    "w": ["wallaby"],
    "x": ["sheep", "ovis aries"],
    "3": ["kangaroo rat"],
    "8": ["papio anubis"],
    "9": ["monodelphis domestica"],
    "!": ["ferret", "mustela putorius furo"],
    "(": ["bat"],
    "*": ["orangutan", "pongo abelii", "pongo pygmaeus"]
  },
  plants: {
    "B": ["arabidopsis thaliana", "arabidopsis lyrata", "brassica oleracea", "brassica rapa"],
    "c": ["corn", "zea mays"],
    "O": ["grapes", "vitis vinifera"],
    "P": ["plant", "physcomitrella patens", "sorghum bicolor", "triticum aestivum"],
    "5": ["barley", "hordeum vulgare", "hordeum vulgare subsp. vulgare"],
    "6": ["oryza sativa", "oryza sativa japonica group"],
    "%": ["brachypodium distachyon"],
    ")": ["tomatoes", "solanum lycopersicum", "solanum tuberosum"],
    "^": ["glycine max"]
  },
  other: {
    "b": ["bug"],
    "E": ["pufferfish", "tetraodon nigroviridis"],
    "f": ["frog", "xenopus (silurana) tropicalis", "xenopus tropicalis"],
    "F": ["fly", "drosophila melanogaster"],
    "k": ["chicken", "gallus gallus"],
    "L": ["escherichia coli"],
    "n": ["finch", "pyrrhula pyrrhula"],
    "s": ["scorpion"],
    "S": ["spider"],
    "u": ["fungus"],
    "v": ["virus"],
    "W": ["caenorhabditis elegans", "schistosoma mansoni"],
    "Y": ["yeast", "saccharomyces cerevisiae", "schizosaccharomyces pombe"],
    "Z": ["zebrafish", "danio rerio"],
    "0": ["amoeba"],
    "1": ["mosquito"],
    "2": ["diatom"],
    "4": ["louse"],
    "7": ["anolis carolinensis"],
    "£": ["aspergillus fumigatus"],
    "+": ["ray"],
    "'": ["snail"],
    "$": ["bee"],
    "&": ["tick"],
    "@": ["plasmodium"]
  }
};

var lookupInGroup = function lookupInGroup(group, species) {
  return Object.keys(mapping[group]).find(function (iconChar) {
    return mapping[group][iconChar].includes(species.toLowerCase());
  });
};

var lookupIcon = function lookupIcon(species) {
  for (var group in mapping) {
    var iconChar = lookupInGroup(group, species);
    if (iconChar) {
      return [group, iconChar];
    }
  }

  return ["", ""];
};

var getAllSpecies = function getAllSpecies() {
  var allSpecies = [];
  Object.keys(mapping).forEach(function (group) {
    Object.keys(mapping[group]).forEach(function (iconChar) {
      mapping[group][iconChar].forEach(function (species) {
        return allSpecies.push(species);
      });
    });
  });

  return allSpecies;
};

exports.default = lookupIcon;
exports.getAllSpecies = getAllSpecies;

/***/ })

},[1196]);
//# sourceMappingURL=expressionAtlasBrowseBySpecies.bundle.js.map