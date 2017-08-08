var expressionAtlasBioentityInformation =
webpackJsonp_name_([4],{

/***/ 1141:
/*!******************************************************!*\
  !*** ./atlas_bundles/bioentity-information/index.js ***!
  \******************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = __webpack_require__(/*! ./src/renderer.js */ 1142);

/***/ }),

/***/ 1142:
/*!*************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/renderer.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var React = __webpack_require__(/*! react */ 0);
var ReactDOM = __webpack_require__(/*! react-dom */ 11);

var BioentityInformation = __webpack_require__(/*! ./BioentityInformation.jsx */ 1143);

exports.render = function (options) {
    ReactDOM.render(React.createElement(BioentityInformation, { bioentityProperties: options.payload }), typeof options.target === "string" ? document.getElementById(options.target) : options.target);
};

/***/ }),

/***/ 1143:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/BioentityInformation.jsx ***!
  \**************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var React = __webpack_require__(/*! react */ 0);
__webpack_require__(/*! ./BioentityInformation.css */ 1144);

var PropertyLinkShape = {
  text: React.PropTypes.string.isRequired,
  url: React.PropTypes.string.isRequired,
  relevance: React.PropTypes.number.isRequired
};

var BioentityPropertyShape = {
  type: React.PropTypes.string.isRequired,
  name: React.PropTypes.string.isRequired,
  values: React.PropTypes.arrayOf(React.PropTypes.shape(PropertyLinkShape)).isRequired
};

var BioentityPropertiesShape = {
  bioentityProperties: React.PropTypes.arrayOf(React.PropTypes.shape(BioentityPropertyShape))
};

var BioentityProperty = React.createClass({
  displayName: "BioentityProperty",

  propTypes: BioentityPropertyShape,

  getInitialState: function getInitialState() {
    return {
      showAll: false
    };
  },


  // take three most relevant links and then all of the same relevance
  _pickMostRelevant: function _pickMostRelevant(properties) {
    var relevanceThreshold = properties.map(function (p) {
      return p.relevance;
    }).sort(function (l, r) {
      return r - l;
    }).concat([0, 0, 0])[properties.size < 3 ? properties.size - 1 : 2];
    return properties.filter(function (p) {
      return p.relevance >= relevanceThreshold;
    });
  },
  _renderProperty: function _renderProperty(property, ix) {
    return property.url ? React.createElement(
      "a",
      { key: property.url + " " + ix, className: "bioEntityCardLink", href: property.url, target: "_blank" },
      property.text
    ) : React.createElement(
      "span",
      { key: property.text + " " + ix },
      property.text
    );
  },
  _zipWithCommaSpans: function _zipWithCommaSpans(elts) {
    return [].concat.apply([], elts.map(function (e, ix) {
      return [e, React.createElement(
        "span",
        { key: "comma " + ix },
        ", "
      )];
    })).slice(0, -1);
  },
  render: function render() {
    var numUnshownLinks = this.props.values.length - this._pickMostRelevant(this.props.values).length;
    var hasOptionalLinks = ["go", "po"].indexOf(this.props.type) > -1 && numUnshownLinks > 0;

    return React.createElement(
      "tr",
      null,
      React.createElement(
        "td",
        { className: "gxaBioentityInformationCardPropertyType" },
        this.props.name
      ),
      React.createElement(
        "td",
        null,
        React.createElement(
          "div",
          null,
          hasOptionalLinks ? React.createElement(
            "span",
            null,
            this._zipWithCommaSpans((this.state.showAll ? this.props.values : this._pickMostRelevant(this.props.values)).sort(function (l, r) {
              return r.relevance === l.relevance ? r.text.toLowerCase() < l.text.toLowerCase() ? 1 : -1 : r.relevance - l.relevance;
            }).map(this._renderProperty)),
            React.createElement(
              "a",
              { role: "button", style: { cursor: "pointer" },
                onClick: function () {
                  this.setState(function (previousState) {
                    return { showAll: !previousState.showAll };
                  });
                }.bind(this) },
              this.state.showAll ? " (show less)" : " … and " + numUnshownLinks + " more"
            )
          ) : this._zipWithCommaSpans(this.props.values.map(this._renderProperty))
        )
      )
    );
  }
});

var BioentityInformation = React.createClass({
  displayName: "BioentityInformation",

  propTypes: BioentityPropertiesShape,

  render: function render() {
    return React.createElement(
      "div",
      { className: "gxaBioentityInformationCard" },
      React.createElement(
        "table",
        null,
        React.createElement(
          "tbody",
          null,
          this.props.bioentityProperties.map(function (bioentityProperty) {
            return React.createElement(BioentityProperty, _extends({
              key: bioentityProperty.type
            }, bioentityProperty));
          })
        )
      )
    );
  }
});

module.exports = BioentityInformation;

/***/ }),

/***/ 1144:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/BioentityInformation.css ***!
  \**************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(/*! !../../../node_modules/css-loader!./BioentityInformation.css */ 1145);
if(typeof content === 'string') content = [[module.i, content, '']];
// Prepare cssTransformation
var transform;

var options = {}
options.transform = transform
// add the styles to the DOM
var update = __webpack_require__(/*! ../../../node_modules/style-loader/lib/addStyles.js */ 17)(content, options);
if(content.locals) module.exports = content.locals;
// Hot Module Replacement
if(false) {
	// When the styles change, update the <style> tags
	if(!content.locals) {
		module.hot.accept("!!../../../node_modules/css-loader/index.js!./BioentityInformation.css", function() {
			var newContent = require("!!../../../node_modules/css-loader/index.js!./BioentityInformation.css");
			if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
			update(newContent);
		});
	}
	// When the module is disposed, remove the <style> tags
	module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 1145:
/*!****************************************************************************************************!*\
  !*** ./node_modules/css-loader!./atlas_bundles/bioentity-information/src/BioentityInformation.css ***!
  \****************************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(/*! ../../../node_modules/css-loader/lib/css-base.js */ 16)(undefined);
// imports


// module
exports.push([module.i, ".gxaBioentityInformationCard {\n    margin-top: 20px;\n}\n\n.gxaBioentityInformationCard table {\n    margin: 0;\n    width: auto;\n    border: none;\n}\n\n.gxaBioentityInformationCard td {\n    border: none;\n}\n\n\ntd.gxaBioentityInformationCardPropertyType {\n    font-size: 14px;\n    font-weight: bold;\n    white-space: nowrap;\n    padding-right: 2em;\n    border:none;\n}\n\n.gxaBioentityInformationCardPropertyValue {\n    border: none;\n}\n", ""]);

// exports


/***/ })

},[1141]);
//# sourceMappingURL=expressionAtlasBioentityInformation.bundle.js.map