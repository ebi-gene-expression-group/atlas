webpackJsonp([2,9],{

/***/ 0:
/*!***********************************************************!*\
  !*** ./src/webpack_example/with_plain_JS/moduleC-page.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["$page"] = __webpack_require__(/*! -!./src/webpack_example/with_plain_JS/moduleC-page.js */ 7);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },

/***/ 7:
/*!***********************************************************!*\
  !*** ./src/webpack_example/with_plain_JS/moduleC-page.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
	 */
	
	var moduleB = __webpack_require__(/*! ./moduleB.js */ 15);
	
	// This is the evil way:
	//module.exports = function (modB) {
	//
	//    function init() {
	//        return 'Hola mundo desde C; el module B dice "' + modB.init() + '"';
	//    }
	//
	//    return {
	//        init: init
	//    }
	//
	//}(moduleB);
	
	// This is the Webpack good way:
	exports.init = function() {
	    return 'Hello world from C; module B says "' + moduleB.init() + '"';
	};


/***/ },

/***/ 15:
/*!******************************************************!*\
  !*** ./src/webpack_example/with_plain_JS/moduleB.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
	 */
	
	// This is the evil way:
	//module.exports = function () {
	//
	//    function init() {
	//        return "Hola mundo desde B";
	//    }
	//
	//    return {
	//        init: init
	//    };
	//
	//}();
	
	// This is the Webpack good way:
	exports.init = function() {
	    return "Hello world from B";
	}


/***/ }

});
//# sourceMappingURL=webpack_example_with_JS_2_modules-page-bundle.js.map