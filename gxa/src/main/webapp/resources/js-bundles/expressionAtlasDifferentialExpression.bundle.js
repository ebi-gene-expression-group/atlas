var expressionAtlasDifferentialExpression =
webpackJsonp_name_([4],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */3875);


/***/ },

/***/ 3695:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/index.js ***!
  \****************************************************************/
[5396, 3696, 3697],

/***/ 3696:
/*!*****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/decode.js ***!
  \*****************************************************************/
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

/***/ 3697:
/*!*****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/encode.js ***!
  \*****************************************************************/
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


/***/ },

/***/ 3875:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 3876);

/***/ },

/***/ 3876:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 4047);
	
	//*------------------------------------------------------------------*
	
	module.exports = function (_ref) {
	    var _ref$atlasHostUrl = _ref.atlasHostUrl,
	        hostUrl = _ref$atlasHostUrl === undefined ? window.location.protocol + "//" + window.location.host : _ref$atlasHostUrl,
	        query = _ref.query,
	        geneQuery = _ref.geneQuery,
	        conditionQuery = _ref.conditionQuery,
	        species = _ref.species,
	        _ref$target = _ref.target,
	        target = _ref$target === undefined ? 'gxaDifferentialTab' : _ref$target;
	
	    ReactDOM.render(React.createElement(DifferentialRouter, { hostUrl: hostUrl, query: query, geneQuery: geneQuery, conditionQuery: conditionQuery, species: species }), document.getElementById(target));
	};

/***/ },

/***/ 3877:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[5266, 3878],

/***/ 3878:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[5267, 3879, 3880, 3892, 3895, 3896, 3901, 3884, 3906, 3907, 3908, 3886, 3902],

/***/ 3879:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/object-assign/index.js ***!
  \********************************************************************************/
5,

/***/ 3880:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[5268, 3881, 3884, 3887, 3889],

/***/ 3881:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[5269, 3882, 3883],

/***/ 3882:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/reactProdInvariant.js ***!
  \*********************************************************************************/
8,

/***/ 3883:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/invariant.js ***!
  \*******************************************************************************/
9,

/***/ 3884:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[5270, 3879, 3885, 3886, 3888],

/***/ 3885:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
11,

/***/ 3886:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/warning.js ***!
  \*****************************************************************************/
[5271, 3887],

/***/ 3887:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyFunction.js ***!
  \***********************************************************************************/
13,

/***/ 3888:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
14,

/***/ 3889:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[5272, 3882, 3885, 3884, 3890, 3883, 3891, 3886],

/***/ 3890:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
16,

/***/ 3891:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/KeyEscapeUtils.js ***!
  \*****************************************************************************/
17,

/***/ 3892:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[5273, 3882, 3893, 3888, 3894, 3883, 3886],

/***/ 3893:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[5274, 3886],

/***/ 3894:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyObject.js ***!
  \*********************************************************************************/
20,

/***/ 3895:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPureComponent.js ***!
  \*********************************************************************************/
[5275, 3879, 3892, 3893, 3894],

/***/ 3896:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[5276, 3882, 3879, 3892, 3884, 3897, 3899, 3893, 3894, 3883, 3898, 3900, 3886],

/***/ 3897:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[5277, 3898],

/***/ 3898:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyMirror.js ***!
  \*******************************************************************************/
[5278, 3883],

/***/ 3899:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
25,

/***/ 3900:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyOf.js ***!
  \***************************************************************************/
26,

/***/ 3901:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[5279, 3884, 3902],

/***/ 3902:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[5280, 3885, 3903, 3884, 3897, 3904, 3888, 3890, 3886],

/***/ 3903:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentTreeHook.js ***!
  \*************************************************************************************/
[5281, 3882, 3885, 3883, 3886],

/***/ 3904:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/checkReactTypeSpec.js ***!
  \*********************************************************************************/
[5282, 3882, 3899, 3905, 3883, 3886, 3903, 3903],

/***/ 3905:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypesSecret.js ***!
  \***********************************************************************************/
32,

/***/ 3906:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[5283, 3884, 3899, 3905, 3887, 3890, 3886],

/***/ 3907:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
34,

/***/ 3908:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[5284, 3882, 3884, 3883],

/***/ 3909:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[5287, 3910],

/***/ 3910:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[5288, 3911, 3914, 4037, 3934, 3931, 3907, 4042, 4043, 4044, 3886, 3924, 3937, 4045, 4046],

/***/ 3911:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentTree.js ***!
  \************************************************************************************/
[5289, 3882, 3912, 3913, 3883],

/***/ 3912:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[5290, 3882, 3883],

/***/ 3913:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentFlags.js ***!
  \*************************************************************************************/
42,

/***/ 3914:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[5291, 3915, 3930, 3948, 3949, 3954, 3955, 3969, 3911, 4008, 4009, 4010, 4011, 4012, 4015, 4016, 4024, 4025, 4026],

/***/ 3915:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[5292, 3916, 3917, 3924, 3925, 3927, 3929, 3900],

/***/ 3916:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[5293, 3898],

/***/ 3917:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[5294, 3916, 3918, 3920, 3922, 3923, 3886],

/***/ 3918:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[5295, 3882, 3919, 3920, 3921, 3922, 3923, 3883],

/***/ 3919:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[5296, 3882, 3883],

/***/ 3920:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[5297, 3882, 3916, 3921, 3883, 3886],

/***/ 3921:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
50,

/***/ 3922:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[5298, 3882, 3883],

/***/ 3923:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
52,

/***/ 3924:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \******************************************************************************************/
53,

/***/ 3925:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[5299, 3879, 3881, 3926],

/***/ 3926:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[5300, 3924],

/***/ 3927:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[5301, 3928],

/***/ 3928:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[5302, 3879, 3881, 3887, 3886],

/***/ 3929:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[5303, 3928],

/***/ 3930:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[5304, 3916, 3918, 3917, 3924, 3911, 3931, 3928, 3945, 3946, 3947, 3900],

/***/ 3931:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[5305, 3882, 3879, 3932, 3881, 3933, 3934, 3944, 3883],

/***/ 3932:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[5306, 3882, 3879, 3881, 3883],

/***/ 3933:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactFeatureFlags.js ***!
  \********************************************************************************/
62,

/***/ 3934:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[5307, 3935, 3937, 3886],

/***/ 3935:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[5308, 3936],

/***/ 3936:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[5309, 3882, 3883],

/***/ 3937:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstrumentation.js ***!
  \***********************************************************************************/
[5310, 3938],

/***/ 3938:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDebugTool.js ***!
  \*****************************************************************************/
[5311, 3939, 3940, 3903, 3941, 3924, 3942, 3886],

/***/ 3939:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \**********************************************************************************************/
[5312, 3886],

/***/ 3940:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \********************************************************************************************/
69,

/***/ 3941:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \***********************************************************************************************/
[5313, 3903, 3886],

/***/ 3942:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performanceNow.js ***!
  \************************************************************************************/
[5314, 3943],

/***/ 3943:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performance.js ***!
  \*********************************************************************************/
[5315, 3924],

/***/ 3944:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[5316, 3882, 3883],

/***/ 3945:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
74,

/***/ 3946:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[5317, 3924],

/***/ 3947:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
76,

/***/ 3948:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[5318, 3900],

/***/ 3949:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[5319, 3916, 3917, 3911, 3950, 3900],

/***/ 3950:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[5320, 3951, 3952, 3953],

/***/ 3951:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[5321, 3928, 3945],

/***/ 3952:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
81,

/***/ 3953:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
82,

/***/ 3954:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[5322, 3912],

/***/ 3955:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[5323, 3956, 3968],

/***/ 3956:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[5324, 3957, 3963, 3967, 3911, 3937, 3960, 3959, 3961],

/***/ 3957:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMLazyTree.js ***!
  \**************************************************************************/
[5325, 3958, 3959, 3960, 3961],

/***/ 3958:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMNamespaces.js ***!
  \****************************************************************************/
87,

/***/ 3959:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[5326, 3924, 3958, 3960],

/***/ 3960:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \*************************************************************************************************/
89,

/***/ 3961:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[5327, 3924, 3962, 3959],

/***/ 3962:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
91,

/***/ 3963:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[5328, 3882, 3957, 3924, 3964, 3887, 3883],

/***/ 3964:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*******************************************************************************************/
[5329, 3924, 3965, 3966, 3883],

/***/ 3965:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \******************************************************************************************/
[5330, 3883],

/***/ 3966:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \***********************************************************************************/
[5331, 3924, 3883],

/***/ 3967:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[5332, 3898],

/***/ 3968:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[5333, 3956, 3911],

/***/ 3969:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[5334, 3882, 3879, 3970, 3972, 3957, 3958, 3912, 3980, 3916, 3918, 3919, 3982, 3985, 3913, 3911, 3987, 3989, 3990, 3991, 3937, 3992, 4004, 3887, 3962, 3883, 3946, 3900, 3999, 4007, 3886],

/***/ 3970:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[5335, 3911, 3971],

/***/ 3971:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/focusNode.js ***!
  \*******************************************************************************/
100,

/***/ 3972:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[5336, 3973, 3924, 3937, 3974, 3976, 3977, 3979, 3886],

/***/ 3973:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
102,

/***/ 3974:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***************************************************************************************/
[5337, 3975],

/***/ 3975:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelize.js ***!
  \******************************************************************************/
104,

/***/ 3976:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[5338, 3973, 3886],

/***/ 3977:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \****************************************************************************************/
[5339, 3978],

/***/ 3978:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenate.js ***!
  \*******************************************************************************/
107,

/***/ 3979:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***************************************************************************************/
108,

/***/ 3980:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[5340, 3912, 3911, 3937, 3981, 3886],

/***/ 3981:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[5341, 3962],

/***/ 3982:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[5342, 3879, 3916, 3919, 3983, 3952, 3984, 3946],

/***/ 3983:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[5343, 3918],

/***/ 3984:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getVendorPrefixedEventName.js ***!
  \*****************************************************************************************/
[5344, 3924],

/***/ 3985:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
[5345, 3986],

/***/ 3986:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DisabledInputUtils.js ***!
  \*********************************************************************************/
115,

/***/ 3987:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[5346, 3882, 3879, 3986, 3980, 3988, 3911, 3931, 3883, 3886],

/***/ 3988:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[5347, 3882, 3906, 3897, 3905, 3883, 3886],

/***/ 3989:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[5348, 3879, 3880, 3911, 3990, 3886],

/***/ 3990:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[5349, 3879, 3986, 3988, 3911, 3931, 3886],

/***/ 3991:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[5350, 3882, 3879, 3986, 3988, 3911, 3931, 3883, 3886],

/***/ 3992:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[5351, 3882, 3993, 3994, 3937, 3967, 3885, 3934, 3995, 3887, 4003, 3883],

/***/ 3993:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[5352, 3882, 3883],

/***/ 3994:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
123,

/***/ 3995:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[5353, 3934, 3996, 3891, 4000, 3889, 3886, 3903, 3903],

/***/ 3996:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[5354, 3882, 3879, 3997, 4001, 4002, 3883, 3886],

/***/ 3997:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[5355, 3882, 3879, 3993, 3885, 3884, 3921, 3994, 3937, 3998, 3897, 3934, 3904, 3894, 3883, 3999, 4000, 3886],

/***/ 3998:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNodeTypes.js ***!
  \*****************************************************************************/
[5356, 3882, 3884, 3883],

/***/ 3999:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/shallowEqual.js ***!
  \**********************************************************************************/
128,

/***/ 4000:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
129,

/***/ 4001:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
130,

/***/ 4002:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostComponent.js ***!
  \*********************************************************************************/
[5357, 3882, 3879, 3883],

/***/ 4003:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[5358, 3891, 3889, 3886, 3903, 3903],

/***/ 4004:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[5359, 3879, 3881, 3944, 3937, 4005],

/***/ 4005:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerUpdateQueue.js ***!
  \*************************************************************************************/
[5360, 4006, 3944, 3886],

/***/ 4006:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[5361, 3882, 3885, 3994, 3937, 3931, 3883, 3886],

/***/ 4007:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[5362, 3879, 3887, 3886],

/***/ 4008:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMEmptyComponent.js ***!
  \*************************************************************************************/
[5363, 3879, 3957, 3911],

/***/ 4009:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTreeTraversal.js ***!
  \************************************************************************************/
[5364, 3882, 3883],

/***/ 4010:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[5365, 3882, 3879, 3956, 3957, 3911, 3962, 3883, 4007],

/***/ 4011:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[5366, 3879, 3931, 3944, 3887],

/***/ 4012:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[5367, 3879, 4013, 3924, 3881, 3911, 3931, 3945, 4014],

/***/ 4013:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/EventListener.js ***!
  \***********************************************************************************/
[5368, 3887],

/***/ 4014:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \************************************************************************************************/
143,

/***/ 4015:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[5369, 3912, 3918, 3920, 3993, 3896, 4001, 3982, 4002, 3931],

/***/ 4016:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[5370, 3879, 3932, 3881, 3982, 4017, 3937, 3944, 4006],

/***/ 4017:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[5371, 4018, 4020, 3971, 4023],

/***/ 4018:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[5372, 3924, 4019, 3926],

/***/ 4019:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
148,

/***/ 4020:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/containsNode.js ***!
  \**********************************************************************************/
[5373, 4021],

/***/ 4021:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isTextNode.js ***!
  \********************************************************************************/
[5374, 4022],

/***/ 4022:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isNode.js ***!
  \****************************************************************************/
151,

/***/ 4023:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**************************************************************************************/
152,

/***/ 4024:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
153,

/***/ 4025:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[5375, 3916, 3917, 3924, 3911, 4017, 3928, 4023, 3947, 3900, 3999],

/***/ 4026:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[5376, 3882, 3916, 4013, 3917, 3911, 4027, 4028, 3928, 4029, 4030, 3950, 4033, 4034, 4035, 3951, 4036, 3887, 4031, 3883, 3900],

/***/ 4027:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticAnimationEvent.js ***!
  \**************************************************************************************/
[5377, 3928],

/***/ 4028:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[5378, 3928],

/***/ 4029:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[5379, 3951],

/***/ 4030:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[5380, 3951, 4031, 4032, 3953],

/***/ 4031:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
160,

/***/ 4032:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[5381, 4031],

/***/ 4033:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[5382, 3950],

/***/ 4034:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[5383, 3951, 3953],

/***/ 4035:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTransitionEvent.js ***!
  \***************************************************************************************/
[5384, 3928],

/***/ 4036:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[5385, 3950],

/***/ 4037:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[5386, 3882, 3957, 3912, 3982, 3885, 3911, 4038, 4039, 3884, 3933, 3994, 3937, 4040, 3934, 4006, 3931, 3894, 3996, 3883, 3959, 4000, 3886],

/***/ 4038:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMContainerInfo.js ***!
  \************************************************************************************/
[5387, 4007],

/***/ 4039:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
168,

/***/ 4040:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[5388, 4041],

/***/ 4041:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
170,

/***/ 4042:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[5389, 3882, 3885, 3911, 3994, 4043, 3883, 3886],

/***/ 4043:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getHostComponentFromComposite.js ***!
  \********************************************************************************************/
[5390, 3998],

/***/ 4044:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[5391, 4037],

/***/ 4045:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \******************************************************************************************/
[5392, 3912, 3919, 3903, 3886],

/***/ 4046:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \*********************************************************************************************/
[5393, 3903, 3886],

/***/ 4047:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 3877);
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 179);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 4049);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 4541);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 4544);
	
	//*------------------------------------------------------------------*
	
	/*
	 TODO if Solr queries get fast enough that we can:
	 - split the two requests, so that the facets load first, initial results load second
	 - a request to the server is done for every interaction with the facets tree
	 - add counts to each facet and disable check boxes if count is 0
	*/
	
	var RequiredString = React.PropTypes.string.isRequired;
	
	var DifferentialRouter = React.createClass({
	    displayName: 'DifferentialRouter',
	
	    propTypes: {
	        hostUrl: RequiredString,
	        query: RequiredString,
	        geneQuery: RequiredString,
	        conditionQuery: RequiredString,
	        species: RequiredString
	    },
	
	    getInitialState: function getInitialState() {
	        return {
	            facetsTreeData: [],
	            results: [],
	            legend: {
	                maxDownLevel: 0,
	                minDownLevel: 0,
	                minUpLevel: 0,
	                maxUpLevel: 0
	            },
	            querySelect: {}
	        };
	    },
	    componentDidMount: function componentDidMount() {
	        var _this = this;
	
	        this._loadInitialData();
	        // TODO Consider using https://github.com/reactjs/react-router
	        window.addEventListener('popstate', function () {
	            _this.setState({ querySelect: UrlManager.parseDifferentialUrlParameter() });
	        }, false);
	    },
	    _addElementToObjectOfArrays: function _addElementToObjectOfArrays(obj, arrayName, element) {
	        if (!obj[arrayName]) {
	            obj[arrayName] = [];
	        }
	        obj[arrayName].push(element);
	    },
	    _removeElementFromObjectOfArrays: function _removeElementFromObjectOfArrays(obj, arrayName, element) {
	        delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1);
	        if (obj[arrayName].length === 0) {
	            delete obj[arrayName];
	        }
	    },
	    _setChecked: function _setChecked(facetName, facetItemName, checked) {
	        // Update URL
	        var newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect));
	        if (checked) {
	            this._addElementToObjectOfArrays(newQuerySelect, facetName, facetItemName);
	        } else {
	            this._removeElementFromObjectOfArrays(newQuerySelect, facetName, facetItemName);
	        }
	
	        // TODO Consider using https://github.com/reactjs/react-router
	        UrlManager.differentialPush(newQuerySelect, false);
	        this.setState({
	            querySelect: newQuerySelect
	        });
	    },
	    _filteredResults: function _filteredResults() {
	        var _this2 = this;
	
	        var query = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : this.state.querySelect;
	
	        return this.state.results.filter(function (result) {
	            return _this2._resultMatchesQuery(result, query);
	        });
	    },
	    _resultMatchesQuery: function _resultMatchesQuery(result, query) {
	        var _this3 = this;
	
	        if (Object.keys(query).length === 0) {
	            return false;
	        } else {
	            return Object.keys(query).every(function (facetName) {
	                return query[facetName].some(function (facetItem) {
	                    return _this3._equalsToOrIncludes(result[facetName], facetItem);
	                });
	            });
	        }
	    },
	    _equalsToOrIncludes: function _equalsToOrIncludes(obj, value) {
	        if (!!obj) {
	            if (obj.constructor === Array) {
	                return obj.includes(value);
	            } else {
	                return obj === value;
	            }
	        } else {
	            return false;
	        }
	    },
	
	
	    // Syncs tree data with URL (querySelect) and does some other smart things such as check/uncheck or disable facets based on
	    // the user results (e.g. check & disable a facet if it’s shared by all results as a side effect of other choice)
	    _prepareFacetTreeData: function _prepareFacetTreeData(filteredResults) {
	        var _this4 = this;
	
	        return this.state.facetsTreeData.map(function (facet) {
	            return {
	                facetName: facet.facetName,
	                facetItems: facet.facetItems.map(function (facetItem) {
	                    var querySelectAfterSwitchingThisFacetItem = JSON.parse(JSON.stringify(_this4.state.querySelect));
	                    if (_this4._equalsToOrIncludes(querySelectAfterSwitchingThisFacetItem[facet.facetName], facetItem.name)) {
	                        _this4._removeElementFromObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
	                    } else {
	                        _this4._addElementToObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
	                    }
	                    var resultIdsAfterSwitchingThisFacetItem = _this4._filteredResults(querySelectAfterSwitchingThisFacetItem).map(function (result) {
	                        return result.id;
	                    }).sort();
	                    var currentResultIds = filteredResults.map(function (result) {
	                        return result.id;
	                    }).sort();
	
	                    var sameResultsAfterSwitchingThisItem = JSON.stringify(resultIdsAfterSwitchingThisFacetItem) === JSON.stringify(currentResultIds);
	                    var noResultsAfterSwitchingThisItem = resultIdsAfterSwitchingThisFacetItem.length === 0;
	                    return {
	                        name: facetItem.name,
	                        value: facetItem.value,
	                        checked: _this4._equalsToOrIncludes(_this4.state.querySelect[facet.facetName], facetItem.name) || sameResultsAfterSwitchingThisItem,
	                        disabled: noResultsAfterSwitchingThisItem || sameResultsAfterSwitchingThisItem
	                    };
	                })
	            };
	        });
	    },
	    render: function render() {
	        var filteredResults = this._filteredResults();
	
	        return React.createElement(
	            'div',
	            null,
	            React.createElement(
	                'div',
	                { className: 'grid_6 alpha', id: 'gxaDifferentialFacetsContainerDiv' },
	                Object.keys(this.state.facetsTreeData).length ? React.createElement(Facets, {
	                    facets: this._prepareFacetTreeData(filteredResults),
	                    setChecked: this._setChecked
	                }) : React.createElement('div', null)
	            ),
	            React.createElement(
	                'div',
	                { className: 'grid_18 omega', id: 'gxaDifferentialResultsContainerDiv' },
	                this.state.results && this.state.results.length ? React.createElement(Results, _extends({
	                    results: filteredResults,
	                    hostUrl: this.props.hostUrl
	                }, this.state.legend)) : React.createElement(
	                    'div',
	                    { ref: 'loadingImagePlaceholder' },
	                    React.createElement('img', { src: this.props.hostUrl + "/gxa/resources/images/loading.gif" })
	                )
	            )
	        );
	    },
	    _loadInitialData: function _loadInitialData() {
	        var _this5 = this;
	
	        var differentialFacetsUrlObject = Url.parse(this.props.hostUrl),
	            differentialResultsUrlObject = Url.parse(this.props.hostUrl);
	
	        differentialFacetsUrlObject.pathname = this.props.query ? 'gxa/json/search/differentialFacets' : 'gxa/json/query/differentialFacets';
	        differentialResultsUrlObject.pathname = this.props.query ? 'gxa/json/search/differentialResults' : 'gxa/json/query/differentialResults';
	
	        var queryParams = { geneQuery: this.props.geneQuery, conditionQuery: this.props.conditionQuery, organism: this.props.species };
	        differentialFacetsUrlObject.query = queryParams;
	        differentialResultsUrlObject.query = queryParams;
	
	        var onAjaxFailure = function onAjaxFailure(jqXHR, textStatus, errorThrown) {
	            console.log("ERROR");
	            console.log("Status: " + textStatus);
	            console.log("Error thrown: " + errorThrown);
	        };
	
	        $.ajax({
	            url: Url.format(differentialFacetsUrlObject),
	            dataType: "json",
	            success: function success(facetsResponse) {
	                $.ajax({
	                    url: Url.format(differentialResultsUrlObject),
	                    dataType: "json",
	                    success: function success(resultsResponse) {
	
	                        // TODO Consider using https://github.com/reactjs/react-router
	                        var querySelect = UrlManager.parseDifferentialUrlParameter();
	                        if (!querySelect.kingdom) {
	                            querySelect.kingdom = facetsResponse.kingdom.map(function (facetItem) {
	                                return facetItem.name;
	                            });
	                        }
	                        UrlManager.differentialPush(querySelect, true);
	
	                        var facetsTreeData = _this5._transformFacetsResponseToArray(facetsResponse);
	
	                        _this5.setState({
	                            facetsTreeData: _this5._pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, resultsResponse.results),
	                            querySelect: querySelect,
	                            results: resultsResponse.results,
	                            legend: {
	                                minDownLevel: resultsResponse.minDownLevel,
	                                minUpLevel: resultsResponse.minUpLevel,
	                                maxDownLevel: resultsResponse.maxDownLevel,
	                                maxUpLevel: resultsResponse.maxUpLevel
	                            }
	                        });
	                    },
	                    error: onAjaxFailure
	                });
	            },
	            error: onAjaxFailure
	        });
	    },
	    _transformFacetsResponseToArray: function _transformFacetsResponseToArray(facetsResponse) {
	        return Object.keys(facetsResponse).map(function (facetName) {
	            return {
	                facetName: facetName,
	                facetItems: facetsResponse[facetName].map(function (facetItem) {
	                    return {
	                        name: facetItem.name,
	                        value: facetItem.value,
	                        disabled: false,
	                        checked: false
	                    };
	                })
	            };
	        });
	    },
	    _pruneFacetsTreeBasedOnResultsThatCameIn: function _pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, results) {
	        return facetsTreeData.map(function (facet) {
	            return {
	                facetName: facet.facetName,
	                facetItems: facet.facetItems.filter(function (facetItem) {
	                    return results.some(function (result) {
	                        if (result[facet.facetName].constructor === Array) {
	                            return result[facet.facetName].indexOf(facetItem.name) > -1;
	                        } else {
	                            return result[facet.facetName] === facetItem.name;
	                        }
	                    });
	                })
	            };
	        }).filter(function (facet) {
	            return facet.facetItems.length > 0;
	        });
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DifferentialRouter;

/***/ },

/***/ 4048:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
836,

/***/ 4049:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery.browser */ 4050);
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 4051);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 4054).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 4070);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 4082);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 4085);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 4090);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 4376).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 4539);
	
	//*------------------------------------------------------------------*
	
	var RequiredString = React.PropTypes.string.isRequired;
	var OptionalString = React.PropTypes.string;
	var DoubleWithDefault = React.PropTypes.number;
	var RequiredBool = React.PropTypes.bool.isRequired;
	
	var ResultType = {
	    species: RequiredString,
	    kingdom: RequiredString,
	    experimentType: RequiredString,
	    numReplicates: RequiredString, // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
	    regulation: RequiredString,
	    factors: React.PropTypes.arrayOf(OptionalString).isRequired,
	    bioentityIdentifier: RequiredString,
	    bioentityName: RequiredString,
	    experimentAccession: RequiredString,
	    experimentName: RequiredString,
	    contrastId: RequiredString,
	    comparison: RequiredString,
	    foldChange: React.PropTypes.number.isRequired,
	    colour: RequiredString,
	    id: RequiredString
	};
	
	var DifferentialResults = React.createClass({
	    displayName: 'DifferentialResults',
	
	    /*
	    results: [
	     {
	       "bioentityIdentifier":"ENSMUSG00000072476",
	       "species":"mus musculus",
	       "kingdom":"animals",
	       "experimentAccession":"E-MTAB-698",
	       "experimentType":"rnaseq_mrna_differential",
	       "contrastId":"g1_g2",
	       "numReplicates":"3",
	       "foldChange":"2.4",
	       "regulation":"DOWN"
	       "colour": some_hex_value
	     },
	     {
	       "bioentityIdentifier":"ENSMUSG00000071341",
	       "species":"mus musculus",
	       "kingdom":"animals",
	       "experimentAccession":"E-MTAB-698",
	       "experimentType":"rnaseq_mrna_differential",
	       "contrastId":"g1_g2",
	       "numReplicates":"3",
	       "foldChange":"-∞",
	       "regulation":"DOWN",
	       "colour": some_hex_value
	      }
	    ],
	    maxDownLevel: "-∞" ,
	    minDownLevel: "0",
	    minUpLevel: "0",
	    maxUpLevel: "2.4"
	    */
	    propTypes: {
	        results: React.PropTypes.arrayOf(React.PropTypes.shape(ResultType)).isRequired,
	        maxDownLevel: DoubleWithDefault,
	        minDownLevel: DoubleWithDefault,
	        minUpLevel: DoubleWithDefault,
	        maxUpLevel: DoubleWithDefault,
	        hostUrl: RequiredString
	    },
	
	    getDefaultProps: function getDefaultProps() {
	        return {
	            maxDownLevel: Number.NEGATIVE_INFINITY,
	            minDownLevel: 0,
	            minUpLevel: 0,
	            maxUpLevel: Number.POSITIVE_INFINITY
	        };
	    },
	    getInitialState: function getInitialState() {
	        return {
	            displayLevels: false,
	            googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function () {}
	        };
	    },
	    _toggleDisplayLevels: function _toggleDisplayLevels() {
	        var newDisplayLevels = !this.state.displayLevels;
	        this.setState({ displayLevels: newDisplayLevels });
	    },
	    render: function render() {
	        var _this = this;
	
	        var differentialResultRows = this.props.results.map(function (diffResult) {
	            return React.createElement(DifferentialResultRow, _extends({
	                key: diffResult.id,
	                displayLevels: _this.state.displayLevels,
	                atlasBaseUrl: _this.props.hostUrl + '/gxa'
	            }, diffResult));
	        });
	
	        var feedbackSmileys = $.browser.msie ? null : React.createElement(
	            'div',
	            { style: { marginTop: '50px' } },
	            React.createElement(AtlasFeedback, {
	                collectionCallback: function collectionCallback(score, comment) {
	                    _this.state.googleAnalyticsCallback('send', 'event', 'DifferentialHeatmaps', 'feedback', comment, score);
	                } })
	        );
	
	        return React.createElement(
	            'div',
	            null,
	            React.createElement(
	                'div',
	                { style: { display: 'inline-block', verticalAlign: 'middle' } },
	                React.createElement(DisplayLevelsButton, { hideText: 'Hide log<sub>2</sub>-fold change', showText: 'Display log<sub>2</sub>-fold change', onClickCallback: this._toggleDisplayLevels, displayLevels: this.state.displayLevels, fontSize: '14px', width: '200px' })
	            ),
	            React.createElement(
	                'div',
	                { style: { display: 'inline-block', verticalAlign: 'middle' } },
	                React.createElement(Legend, {
	                    atlasBaseURL: this.props.hostUrl + '/gxa', minDownLevel: this.props.minDownLevel, maxDownLevel: this.props.maxDownLevel, minUpLevel: this.props.minUpLevel, maxUpLevel: this.props.maxUpLevel
	                })
	            ),
	            React.createElement(
	                'div',
	                { style: { display: 'inline-block', paddingLeft: '10px', verticalAlign: 'top' } },
	                React.createElement(DifferentialDownloadButton, { ref: 'downloadProfilesButton',
	                    hostUrl: this.props.hostUrl,
	                    results: this.props.results
	                })
	            ),
	            React.createElement(
	                'table',
	                { className: 'table-striped gxaDifferentialFacetedSearchResults' },
	                React.createElement(
	                    'thead',
	                    null,
	                    React.createElement(
	                        'tr',
	                        null,
	                        React.createElement(
	                            'th',
	                            { style: { width: '10%' } },
	                            'Log',
	                            React.createElement(
	                                'sub',
	                                null,
	                                '2'
	                            ),
	                            '-fold change'
	                        ),
	                        React.createElement(
	                            'th',
	                            { style: { width: '5%' } },
	                            'Species'
	                        ),
	                        React.createElement(
	                            'th',
	                            { style: { width: '5%' } },
	                            'Gene name'
	                        ),
	                        React.createElement(
	                            'th',
	                            { style: { width: '30%' } },
	                            'Comparison'
	                        ),
	                        React.createElement(
	                            'th',
	                            { style: { width: '15%' } },
	                            'Experimental variables'
	                        ),
	                        React.createElement(
	                            'th',
	                            { style: { width: '35%' } },
	                            'Experiment name'
	                        )
	                    )
	                ),
	                React.createElement(
	                    'tbody',
	                    null,
	                    differentialResultRows
	                )
	            ),
	            feedbackSmileys
	        );
	    }
	});
	
	var DifferentialResultRow = React.createClass({
	    displayName: 'DifferentialResultRow',
	
	    propTypes: ResultType,
	
	    _linkToComparisonPage: function _linkToComparisonPage() {
	        return 'experiments/' + this.props.experimentAccession + '?geneQuery=' + this.props.bioentityIdentifier + '&queryFactorValues=' + this.props.contrastId + '&specific=false';
	    },
	    render: function render() {
	        var factors = this.props.factors ? this.props.factors.toString().replace(/,/g, ', ') : '';
	
	        return React.createElement(
	            'tr',
	            null,
	            React.createElement(CellDifferential, {
	                colour: this.props.colour,
	                infinity: this.props.infinity,
	                foldChange: this.props.foldChange,
	                pValue: this.props.pValue,
	                tStat: this.props.tStatistics,
	                displayLevels: this.props.displayLevels }),
	            React.createElement(
	                'td',
	                { className: 'col_species' },
	                React.createElement(EbiSpeciesIcon, { species: this.props.species })
	            ),
	            React.createElement(
	                'td',
	                null,
	                React.createElement(
	                    'a',
	                    { href: '/gxa/genes/' + this.props.bioentityIdentifier },
	                    this.props.bioentityName || this.props.bioentityIdentifier
	                )
	            ),
	            React.createElement(
	                'td',
	                { ref: 'comparison' },
	                React.createElement(
	                    'a',
	                    { href: this._linkToComparisonPage() },
	                    this.props.comparison
	                )
	            ),
	            React.createElement(
	                'td',
	                { className: 'gxaExperimentalVariable' },
	                factors
	            ),
	            React.createElement(
	                'td',
	                null,
	                React.createElement(
	                    'a',
	                    { href: 'experiments/' + this.props.experimentAccession },
	                    this.props.experimentName
	                )
	            )
	        );
	    },
	    componentDidMount: function componentDidMount() {
	        var _this2 = this;
	
	        ContrastTooltips(this.props.atlasBaseUrl, '', ReactDOM.findDOMNode(this.refs.comparison), this.props.experimentAccession, this.props.contrastId);
	        $(document).ready(function () {
	            _this2.setState({ googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : function () {} });
	        });
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DifferentialResults;

/***/ },

/***/ 4050:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[5715, 4048],

/***/ 4051:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 4052);

/***/ },

/***/ 4052:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery-ui-bundle */ 4053);
	
	var DisplayLevelsButton = React.createClass({
	    displayName: 'DisplayLevelsButton',
	
	
	    propTypes: {
	        hideText: React.PropTypes.string.isRequired,
	        showText: React.PropTypes.string.isRequired,
	        onClickCallback: React.PropTypes.func.isRequired,
	        displayLevels: React.PropTypes.bool.isRequired,
	        width: React.PropTypes.string,
	        fontSize: React.PropTypes.string
	    },
	
	    _buttonText: function _buttonText() {
	        return this.props.displayLevels ? this.props.hideText : this.props.showText;
	    },
	
	    _updateButtonText: function _updateButtonText() {
	        $(ReactDOM.findDOMNode(this)).button({ label: this._buttonText() });
	    },
	
	    render: function render() {
	        var style = {};
	        if (this.props.width) {
	            style.width = this.props.width;
	        }
	        if (this.props.fontSize) {
	            style.fontSize = this.props.fontSize;
	        }
	
	        return React.createElement('button', { style: style, onClick: this.props.onClickCallback });
	    },
	
	    componentDidMount: function componentDidMount() {
	        this._updateButtonText();
	    },
	
	    componentDidUpdate: function componentDidUpdate() {
	        this._updateButtonText();
	    }
	
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DisplayLevelsButton;

/***/ },

/***/ 4053:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[5714, 4048],

/***/ 4054:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 4055);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 4067);

/***/ },

/***/ 4055:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 4056);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 4061);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 4065);
	
	//*------------------------------------------------------------------*
	
	var LegendDifferential = React.createClass({
	    displayName: 'LegendDifferential',
	
	
	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        minDownLevel: React.PropTypes.number.isRequired,
	        maxDownLevel: React.PropTypes.number.isRequired,
	        minUpLevel: React.PropTypes.number.isRequired,
	        maxUpLevel: React.PropTypes.number.isRequired
	    },
	
	    render: function render() {
	        return React.createElement(
	            'div',
	            { className: 'gxaLegend' },
	            React.createElement(
	                'div',
	                { style: { display: "inline-table" } },
	                isNaN(this.props.minDownLevel) && isNaN(this.props.maxDownLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: React.createElement(
	                        'span',
	                        null,
	                        this.props.minDownLevel
	                    ),
	                    highExpressionLevel: React.createElement(
	                        'span',
	                        null,
	                        this.props.maxDownLevel
	                    ),
	                    lowValueColour: '#C0C0C0',
	                    highValueColour: '#0000FF' }),
	                isNaN(this.props.minUpLevel) && isNaN(this.props.maxUpLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: React.createElement(
	                        'span',
	                        null,
	                        this.props.minUpLevel
	                    ),
	                    highExpressionLevel: React.createElement(
	                        'span',
	                        null,
	                        this.props.maxUpLevel
	                    ),
	                    lowValueColour: '#FFAFAF',
	                    highValueColour: '#FF0000' })
	            ),
	            React.createElement('div', { ref: 'legendHelp', 'data-help-loc': '#gradient-differential', className: 'gxaLegendHelp' })
	        );
	    },
	
	    componentDidMount: function componentDidMount() {
	        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = LegendDifferential;

/***/ },

/***/ 4056:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	
	__webpack_require__(/*! ./gxaGradient.css */ 4057);
	
	var LegendRow = React.createClass({
	    displayName: 'LegendRow',
	
	
	    propTypes: {
	        lowValueColour: React.PropTypes.string.isRequired,
	        highValueColour: React.PropTypes.string.isRequired,
	        lowExpressionLevel: React.PropTypes.element.isRequired,
	        highExpressionLevel: React.PropTypes.element.isRequired
	    },
	
	    render: function render() {
	        var spanStyle = {
	            backgroundImage: 'linear-gradient(to right, ' + this.props.lowValueColour + ', ' + this.props.highValueColour + ')'
	        };
	
	        return this.props.lowExpressionLevel || this.props.highExpressionLevel ? React.createElement(
	            'div',
	            { style: { display: "table-row" } },
	            React.createElement(
	                'div',
	                { className: 'gxaGradientLevel gxaGradientLevelMin' },
	                this.props.lowExpressionLevel
	            ),
	            React.createElement(
	                'div',
	                { style: { display: "table-cell" } },
	                React.createElement('span', { className: 'gxaGradientColour', style: spanStyle })
	            ),
	            React.createElement(
	                'div',
	                { className: 'gxaGradientLevel gxaGradientLevelMax' },
	                this.props.highExpressionLevel
	            )
	        ) : null;
	    }
	});
	
	module.exports = LegendRow;

/***/ },

/***/ 4057:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 4058);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaGradient.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaGradient.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4058:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 4059:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
234,

/***/ 4060:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
235,

/***/ 4061:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/index.js ***!
  \*******************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 4062);

/***/ },

/***/ 4062:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery-ui-bundle */ 4053);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 4063);
	
	//*------------------------------------------------------------------*
	
	function buildHelpAnchor() {
	    return $("<a/>", {
	        class: "help-icon",
	        href: "#",
	        title: "",
	        text: "?"
	    });
	}
	
	function getHelpFileName(pageName) {
	    return "help-tooltips." + pageName + "-page.html";
	}
	
	function initTooltips(atlasBaseURL, pageName, parentElementId) {
	
	    var anchor = buildHelpAnchor();
	
	    var helpSelector = (typeof parentElementId === 'undefined' ? 'undefined' : _typeof(parentElementId)) === "object" ? parentElementId : parentElementId == "" ? "[data-help-loc]" : "#" + parentElementId + " [data-help-loc]";
	
	    $(helpSelector).append(anchor).click(function (e) {
	        e.preventDefault();
	    }).tooltip({
	        tooltipClass: "gxaHelpTooltip",
	        content: function content(callback) {
	            var tooltipHelpHtmlId = $(this).parent().attr("data-help-loc");
	
	            $.get(atlasBaseURL + "/resources/html/" + getHelpFileName(pageName), function (response, status, xhr) {
	                var tooltipContent;
	
	                if (status === "error") {
	                    tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
	                    callback(tooltipContent);
	                    return;
	                }
	
	                tooltipContent = $(response).filter(tooltipHelpHtmlId).text();
	                if (!tooltipContent) {
	                    tooltipContent = "Missing help section for id = " + tooltipHelpHtmlId + " in html file " + getHelpFileName(pageName);
	                }
	
	                callback(tooltipContent);
	            });
	        }
	    });
	}
	
	//*------------------------------------------------------------------*
	
	module.exports = function (atlasBaseURL, pageName, parentElementId) {
	    initTooltips(atlasBaseURL, pageName, parentElementId);
	};

/***/ },

/***/ 4063:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../css-loader!./gxaHelpTooltip.css */ 4064);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../css-loader/index.js!./gxaHelpTooltip.css", function() {
				var newContent = require("!!./../../../../css-loader/index.js!./gxaHelpTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4064:
/*!**************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \**************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4065:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 4066);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaLegend.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaLegend.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4066:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4067:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 4056);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 4068).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 4061);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 4065);
	
	//*------------------------------------------------------------------*
	
	var LegendBaseline = React.createClass({
	    displayName: 'LegendBaseline',
	
	
	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        minExpressionLevel: React.PropTypes.number.isRequired,
	        maxExpressionLevel: React.PropTypes.number.isRequired,
	        isMultiExperiment: React.PropTypes.bool.isRequired
	    },
	
	    render: function render() {
	        var _this = this;
	
	        var dataHelpLoc = this.props.isMultiExperiment ? "#gradient-base-crossexp" : "#gradient-base";
	
	        // The class gxaHeatmapLegendGradient is used for Selenium tests but isn’t styled
	        return React.createElement(
	            'div',
	            { className: 'gxaHeatmapLegendGradient' },
	            React.createElement(
	                'div',
	                { style: { display: "inline-table" } },
	                React.createElement(LegendRow, { lowExpressionLevel: NumberFormat.baselineExpression(this.props.minExpressionLevel),
	                    highExpressionLevel: NumberFormat.baselineExpression(this.props.maxExpressionLevel),
	                    lowValueColour: '#C0C0C0',
	                    highValueColour: '#0000FF' })
	            ),
	            React.createElement('div', { ref: function ref(legendHelpDiv) {
	                    _this.legendHelpDiv = legendHelpDiv;
	                }, 'data-help-loc': dataHelpLoc, className: 'gxaLegendHelp' })
	        );
	    },
	
	    componentDidMount: function componentDidMount() {
	        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.legendHelpDiv));
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = LegendBaseline;

/***/ },

/***/ 4068:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[5887, 4069],

/***/ 4069:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
[5888, 3877],

/***/ 4070:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 4071);

/***/ },

/***/ 4071:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 4072);
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery-ui-bundle */ 4053);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 4076).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 4078);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 4080);
	
	var CellDifferential = React.createClass({
	    displayName: 'CellDifferential',
	
	
	    propTypes: {
	        fontSize: React.PropTypes.number,
	        colour: React.PropTypes.string,
	        foldChange: React.PropTypes.number,
	        pValue: React.PropTypes.number,
	        tStat: React.PropTypes.number,
	        displayLevels: React.PropTypes.bool.isRequired
	    },
	
	    _hasValue: function _hasValue() {
	        return !!this.props.foldChange;
	    },
	    _getStyle: function _getStyle() {
	        return this.props.fontSize ? { fontSize: this.props.fontSize + 'px' } : {};
	    },
	    render: function render() {
	        if (!this._hasValue()) {
	            return React.createElement('td', null);
	        }
	
	        return React.createElement(
	            'td',
	            { style: { backgroundColor: this.props.colour, verticalAlign: 'middle' } },
	            React.createElement(
	                'div',
	                { style: this._getStyle(), className: this.props.displayLevels ? 'gxaShowCell' : 'gxaHideCell' },
	                this.props.foldChange
	            )
	        );
	    },
	    componentDidMount: function componentDidMount() {
	        if (this._hasValue()) {
	            this._initTooltip(ReactDOM.findDOMNode(this));
	        }
	    },
	    _initTooltip: function _initTooltip(element) {
	        var _this = this;
	
	        function buildHeatmapCellTooltip(pValue, tStatistic, foldChange) {
	            return React.createElement(
	                'table',
	                null,
	                React.createElement(
	                    'thead',
	                    null,
	                    React.createElement(
	                        'tr',
	                        null,
	                        pValue ? React.createElement(
	                            'th',
	                            null,
	                            'Adjusted ',
	                            React.createElement(
	                                'em',
	                                null,
	                                'p'
	                            ),
	                            '-value'
	                        ) : null,
	                        tStatistic ? React.createElement(
	                            'th',
	                            null,
	                            React.createElement(
	                                'em',
	                                null,
	                                't'
	                            ),
	                            '-statistic'
	                        ) : null,
	                        React.createElement(
	                            'th',
	                            null,
	                            'Log',
	                            React.createElement(
	                                'sub',
	                                null,
	                                '2'
	                            ),
	                            '-fold change'
	                        )
	                    )
	                ),
	                React.createElement(
	                    'tbody',
	                    null,
	                    React.createElement(
	                        'tr',
	                        null,
	                        pValue ? React.createElement(
	                            'td',
	                            null,
	                            NumberFormat.scientificNotation(pValue)
	                        ) : null,
	                        tStatistic ? React.createElement(
	                            'td',
	                            null,
	                            Math.floor(tStatistic * 1e4) / 1e4
	                        ) : null,
	                        React.createElement(
	                            'td',
	                            null,
	                            foldChange
	                        )
	                    )
	                )
	            );
	        }
	
	        $(element).attr('title', '').tooltip({
	            open: function open(event, ui) {
	                ui.tooltip.css('background', _this.props.colour);
	            },
	
	            tooltipClass: 'gxaDifferentialCellTooltip',
	
	            content: function content() {
	                return ReactDOMServer.renderToStaticMarkup(buildHeatmapCellTooltip(_this.props.pValue, _this.props.tStat, _this.props.foldChange));
	            }
	        });
	    }
	});
	
	module.exports = CellDifferential;

/***/ },

/***/ 4072:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
[5718, 4073],

/***/ 4073:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[5719, 3914, 4074, 3907],

/***/ 4074:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[5720, 3882, 4038, 4011, 3884, 3937, 4040, 3934, 4075, 4004, 3931, 3894, 3996, 3883],

/***/ 4075:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
1213,

/***/ 4076:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/index.js ***!
  \******************************************************************************************************************************/
[5887, 4077],

/***/ 4077:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \******************************************************************************************************************************************/
[5888, 3877],

/***/ 4078:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 4079);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaShowHideCell.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaShowHideCell.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4079:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4080:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 4081);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaDifferentialCellTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaDifferentialCellTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4081:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4082:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery-ui-bundle */ 4053);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3909);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 4083);
	
	//*------------------------------------------------------------------*
	
	
	var RequiredString = React.PropTypes.string.isRequired;
	var OptionalString = React.PropTypes.string;
	var RequiredNumber = React.PropTypes.number.isRequired;
	var OptionalNumber = React.PropTypes.number;
	
	var DownloadDifferentialButton = React.createClass({
	    displayName: 'DownloadDifferentialButton',
	
	
	    propTypes: {
	        hostUrl: RequiredString,
	        results: React.PropTypes.arrayOf(React.PropTypes.shape({
	            species: RequiredString,
	            kingdom: RequiredString,
	            experimentType: RequiredString,
	            numReplicates: RequiredString, // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
	            regulation: RequiredString,
	            factors: React.PropTypes.arrayOf(OptionalString).isRequired,
	            bioentityIdentifier: RequiredString,
	            experimentAccession: RequiredString,
	            experimentName: RequiredString,
	            contrastId: RequiredString,
	            comparison: RequiredString,
	            foldChange: RequiredNumber,
	            pValue: RequiredNumber,
	            tStatistics: OptionalNumber,
	            colour: RequiredString,
	            id: RequiredString
	        })).isRequired
	    },
	
	    _convertJsonToTsv: function _convertJsonToTsv(results) {
	        var arrayResults = (typeof results === 'undefined' ? 'undefined' : _typeof(results)) !== 'object' ? JSON.parse(results) : results;
	        return [['Gene', 'Organism', 'Experiment Accession', 'Comparison', 'log2foldchange', 'pValue'].concat(arrayResults.some(function (diffResults) {
	            return diffResults.tStatistics != null;
	        }) ? ['tStatistics'] : []).join('\t')].concat(arrayResults.map(function (diffResults) {
	            return [diffResults.bioentityIdentifier, diffResults.species, diffResults.experimentAccession, diffResults.comparison, diffResults.foldChange, diffResults.pValue, diffResults.tStatistics].filter(function (el) {
	                return el !== null;
	            }) // tStatistics might be missing
	            .join('\t');
	        })).join('\n');
	    },
	    _downloadDifferentialProfiles: function _downloadDifferentialProfiles() {
	        $(ReactDOM.findDOMNode(this._downloadProfilesLinkRef)).click();
	    },
	    render: function render() {
	        var _this = this;
	
	        var downloadImgSrcURL = this.props.hostUrl + '/gxa/resources/images/download_blue_small.png';
	
	        var tsvString = this._convertJsonToTsv(this.props.results);
	        var uri = 'data:text/tsv;charset=utf-8,' + encodeURI(tsvString);
	        var fileName = 'differentialResults.tsv';
	
	        return React.createElement(
	            'div',
	            { style: { display: 'inline-block', verticalAlign: 'top', paddingLeft: '10px' } },
	            React.createElement(
	                'a',
	                { ref: function ref(c) {
	                        _this._downloadProfilesLinkRef = c;
	                    }, className: 'gxaNoTextButton',
	                    href: uri, download: fileName, target: '_blank',
	                    onClick: this._downloadDifferentialProfiles },
	                React.createElement('img', { id: 'download-profiles', alt: 'Download query results', style: { width: '20px' },
	                    src: downloadImgSrcURL })
	            )
	        );
	    },
	    componentDidMount: function componentDidMount() {
	        var $downloadProfilesLink = $(ReactDOM.findDOMNode(this._downloadProfilesLinkRef));
	        $downloadProfilesLink.tooltip();
	        $downloadProfilesLink.button();
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DownloadDifferentialButton;

/***/ },

/***/ 4083:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 4084);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./DifferentialDownloadButton.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./DifferentialDownloadButton.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4084:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4085:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 4086);

/***/ },

/***/ 4086:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 4072);
	
	var $ = __webpack_require__(/*! jquery */ 4048);
	__webpack_require__(/*! jquery-ui-bundle */ 4053);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 4087);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 4088);
	
	//*------------------------------------------------------------------*
	
	function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {
	
	    $(element).attr("title", "").tooltip({
	
	        hide: false,
	
	        show: false,
	
	        tooltipClass: "gxaContrastTooltip",
	
	        position: { my: "right bottom", at: "bottom" },
	
	        close: function close() {
	            $(".gxaContrastTooltip").remove();
	        },
	
	        content: function content(callback) {
	            $.ajax({
	                url: contextRoot + "/rest/contrast-summary",
	                data: {
	                    experimentAccession: experimentAccession,
	                    contrastId: contrastId,
	                    accessKey: accessKey
	                },
	                type: "GET",
	                success: function success(data) {
	                    var html = ReactDOMServer.renderToString(React.createElement(ContrastTooltip, {
	                        experimentDescription: data.experimentDescription,
	                        contrastDescription: data.contrastDescription,
	                        testReplicates: data.testReplicates,
	                        referenceReplicates: data.referenceReplicates,
	                        properties: data.properties
	                    }));
	                    callback(html);
	                }
	            }).fail(function (data) {
	                console.log("ERROR:  " + data);
	                callback("ERROR: " + data);
	            });
	        }
	
	    });
	}
	
	//*------------------------------------------------------------------*
	
	module.exports = function (contextRoot, accessKey, element, experimentAccession, contrastId) {
	    initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId);
	};

/***/ },

/***/ 4087:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 3877);
	
	var ContrastTooltip = React.createClass({
	    displayName: "ContrastTooltip",
	
	    propTypes: {
	        experimentDescription: React.PropTypes.string.isRequired,
	        contrastDescription: React.PropTypes.string.isRequired,
	        testReplicates: React.PropTypes.number.isRequired,
	        referenceReplicates: React.PropTypes.number.isRequired,
	        properties: React.PropTypes.arrayOf(React.PropTypes.shape({
	            contrastPropertyType: React.PropTypes.string,
	            propertyName: React.PropTypes.string.isRequired,
	            referenceValue: React.PropTypes.string.isRequired,
	            testValue: React.PropTypes.string.isRequired
	        }))
	    },
	
	    propertyRow: function propertyRow(property) {
	        if (!property.testValue && !property.referenceValue) {
	            return null;
	        }
	
	        function isFactor(property) {
	            return property.contrastPropertyType === "FACTOR";
	        }
	
	        var style = { whiteSpace: "normal" };
	
	        if (isFactor(property)) {
	            style.fontWeight = "bold";
	        } else {
	            style.color = "gray";
	        }
	
	        return React.createElement(
	            "tr",
	            { key: property.contrastPropertyType + "-" + property.propertyName },
	            React.createElement(
	                "td",
	                { style: style },
	                property.propertyName
	            ),
	            React.createElement(
	                "td",
	                { style: style },
	                property.testValue
	            ),
	            React.createElement(
	                "td",
	                { style: style },
	                property.referenceValue
	            )
	        );
	    },
	
	    render: function render() {
	        return React.createElement(
	            "div",
	            null,
	            React.createElement(
	                "div",
	                { id: "contrastExperimentDescription", style: { fontWeight: "bold", color: "blue", textAlign: "center" } },
	                this.props.experimentDescription
	            ),
	            React.createElement(
	                "div",
	                { id: "contrastDescription", style: { textAlign: "center" } },
	                this.props.contrastDescription
	            ),
	            React.createElement(
	                "table",
	                { style: { padding: "0px", margin: "0px", width: "100%" } },
	                React.createElement(
	                    "thead",
	                    null,
	                    React.createElement(
	                        "tr",
	                        null,
	                        React.createElement(
	                            "th",
	                            null,
	                            "Property"
	                        ),
	                        React.createElement(
	                            "th",
	                            null,
	                            "Test value (N=",
	                            this.props.testReplicates,
	                            ")"
	                        ),
	                        React.createElement(
	                            "th",
	                            null,
	                            "Reference value (N=",
	                            this.props.referenceReplicates,
	                            ")"
	                        )
	                    )
	                ),
	                React.createElement(
	                    "tbody",
	                    null,
	                    this.props.properties.map(this.propertyRow)
	                )
	            )
	        );
	    }
	
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = ContrastTooltip;

/***/ },

/***/ 4088:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 4089);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaContrastTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaContrastTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4089:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4090:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[5401, 4091],

/***/ 4091:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[5402, 3877, 4092, 4094, 4095, 4102, 4201, 4203, 4208, 4209, 4374],

/***/ 4092:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/react-localstorage.js ***!
  \**********************************************************************************************************************/
[5403, 3877, 4093],

/***/ 4093:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/lib/warning.js ***!
  \***************************************************************************************************************/
241,

/***/ 4094:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-timer-mixin/TimerMixin.js ***!
  \*************************************************************************************************************/
242,

/***/ 4095:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-addons-css-transition-group/index.js ***!
  \************************************************************************************************************************/
[5404, 4096],

/***/ 4096:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[5405, 3879, 3878, 4097, 4099],

/***/ 4097:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[5406, 3879, 3878, 3994, 4098, 3887],

/***/ 4098:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[5407, 4003],

/***/ 4099:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[5408, 3878, 3910, 4100, 4101, 3908],

/***/ 4100:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/CSSCore.js ***!
  \*****************************************************************************/
[5409, 3883],

/***/ 4101:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[5410, 3924, 3984],

/***/ 4102:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Button.js ***!
  \***********************************************************************************************************/
[5411, 4103, 4138, 4139, 4146, 4147, 4183, 4191, 3877, 4192, 4194, 4199, 4200],

/***/ 4103:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \**************************************************************************************************************************************/
[5412, 4104],

/***/ 4104:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \***************************************************************************************************************************************************/
[5413, 4105, 4108],

/***/ 4105:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \************************************************************************************************************************************************************/
[5414, 4106, 4121],

/***/ 4106:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \**************************************************************************************************************************************************/
[5415, 4107, 4108, 4109, 4111],

/***/ 4107:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \**************************************************************************************************************************************************/
255,

/***/ 4108:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \************************************************************************************************************************************************/
256,

/***/ 4109:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \***********************************************************************************************************************************************/
[5416, 4110],

/***/ 4110:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \******************************************************************************************************************************************************/
258,

/***/ 4111:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \************************************************************************************************************************************************/
[5417, 4112, 4120, 4116],

/***/ 4112:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*****************************************************************************************************************************************************/
[5418, 4113, 4115, 4119, 4116],

/***/ 4113:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*****************************************************************************************************************************************************/
[5419, 4114],

/***/ 4114:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*****************************************************************************************************************************************************/
262,

/***/ 4115:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \**********************************************************************************************************************************************************/
[5420, 4116, 4117, 4118],

/***/ 4116:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*******************************************************************************************************************************************************/
[5421, 4117],

/***/ 4117:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \*************************************************************************************************************************************************/
265,

/***/ 4118:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \******************************************************************************************************************************************************/
[5422, 4114, 4107],

/***/ 4119:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \********************************************************************************************************************************************************/
[5423, 4114],

/***/ 4120:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \*********************************************************************************************************************************************************/
268,

/***/ 4121:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \***********************************************************************************************************************************************************/
[5424, 4122, 4125, 4137],

/***/ 4122:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*******************************************************************************************************************************************************/
[5425, 4123, 4136],

/***/ 4123:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \****************************************************************************************************************************************************************/
[5426, 4124, 4125, 4129, 4133],

/***/ 4124:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \***********************************************************************************************************************************************/
272,

/***/ 4125:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \******************************************************************************************************************************************************/
[5427, 4126, 4128],

/***/ 4126:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \***************************************************************************************************************************************************/
[5428, 4127],

/***/ 4127:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \***********************************************************************************************************************************************/
275,

/***/ 4128:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \***************************************************************************************************************************************************/
276,

/***/ 4129:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \**********************************************************************************************************************************************************/
[5429, 4125, 4130, 4132],

/***/ 4130:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*****************************************************************************************************************************************************/
[5430, 4131],

/***/ 4131:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \******************************************************************************************************************************************************/
279,

/***/ 4132:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \****************************************************************************************************************************************************/
[5431, 4131],

/***/ 4133:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \******************************************************************************************************************************************************/
[5432, 4134, 4135],

/***/ 4134:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \**************************************************************************************************************************************************/
[5433, 4107],

/***/ 4135:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \***********************************************************************************************************************************************/
283,

/***/ 4136:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \*********************************************************************************************************************************************************/
284,

/***/ 4137:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \******************************************************************************************************************************************************/
285,

/***/ 4138:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \************************************************************************************************************************************************/
286,

/***/ 4139:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \********************************************************************************************************************************/
[5434, 4140],

/***/ 4140:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \**************************************************************************************************************************************/
[5435, 4141],

/***/ 4141:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \***************************************************************************************************************************************************/
[5436, 4142, 4108],

/***/ 4142:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \************************************************************************************************************************************************************/
[5437, 4106, 4143],

/***/ 4143:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \*********************************************************************************************************************************************************/
[5438, 4122, 4144, 4137, 4145, 4126, 4117],

/***/ 4144:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*******************************************************************************************************************************************************/
292,

/***/ 4145:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*****************************************************************************************************************************************************/
[5439, 4128],

/***/ 4146:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \***************************************************************************************************************************************/
294,

/***/ 4147:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \**************************************************************************************************************************************************/
[5440, 4148],

/***/ 4148:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \*******************************************************************************************************************************/
[5441, 4149, 4169],

/***/ 4149:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \****************************************************************************************************************************************/
[5442, 4150],

/***/ 4150:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*****************************************************************************************************************************************************/
[5443, 4151, 4164, 4168],

/***/ 4151:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \**************************************************************************************************************************************************************/
[5444, 4152, 4153],

/***/ 4152:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*****************************************************************************************************************************************************/
[5445, 4131, 4128],

/***/ 4153:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*******************************************************************************************************************************************************/
[5446, 4154, 4106, 4155, 4111, 4124, 4156, 4157, 4161, 4163, 4162],

/***/ 4154:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \***************************************************************************************************************************************************/
302,

/***/ 4155:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \****************************************************************************************************************************************************/
[5447, 4111],

/***/ 4156:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*****************************************************************************************************************************************************/
304,

/***/ 4157:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*******************************************************************************************************************************************************/
[5448, 4158, 4120, 4161, 4111, 4162],

/***/ 4158:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \*********************************************************************************************************************************************************/
[5449, 4113, 4159, 4136, 4133, 4118, 4160],

/***/ 4159:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \******************************************************************************************************************************************************/
[5450, 4112, 4113, 4122, 4116],

/***/ 4160:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \************************************************************************************************************************************************/
[5451, 4107],

/***/ 4161:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \*************************************************************************************************************************************************************/
[5452, 4112, 4124, 4162],

/***/ 4162:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \***********************************************************************************************************************************************/
[5453, 4134, 4135, 4107],

/***/ 4163:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \******************************************************************************************************************************************************/
[5454, 4124, 4145, 4133],

/***/ 4164:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \***********************************************************************************************************************************************************/
[5455, 4165, 4107, 4111, 4156, 4162],

/***/ 4165:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \*************************************************************************************************************************************************************/
[5456, 4166, 4167, 4156, 4125, 4153],

/***/ 4166:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \**************************************************************************************************************************************************************/
314,

/***/ 4167:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*****************************************************************************************************************************************************/
315,

/***/ 4168:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \***************************************************************************************************************************************************/
[5457, 4162],

/***/ 4169:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \*******************************************************************************************************************************/
[5458, 4170],

/***/ 4170:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \**************************************************************************************************************************************************/
[5459, 4171, 4180, 4181, 4182, 4108],

/***/ 4171:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*****************************************************************************************************************************************************/
[5460, 4107, 4124, 4116, 4106, 4155, 4172, 4117, 4134, 4161, 4135, 4162, 4168, 4173, 4174, 4175, 4176, 4113, 4125, 4119, 4120, 4158, 4177, 4179, 4112, 4122, 4178, 4137, 4144, 4154, 4111],

/***/ 4172:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \************************************************************************************************************************************************/
[5461, 4135, 4114, 4124, 4112, 4117],

/***/ 4173:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \******************************************************************************************************************************************************/
[5462, 4107, 4108, 4154, 4168, 4112],

/***/ 4174:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \*************************************************************************************************************************************************/
[5463, 4122, 4125],

/***/ 4175:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*****************************************************************************************************************************************************/
[5464, 4122, 4144, 4137],

/***/ 4176:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \****************************************************************************************************************************************************/
[5465, 4127],

/***/ 4177:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \***********************************************************************************************************************************************************/
[5466, 4125, 4178],

/***/ 4178:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*******************************************************************************************************************************************************/
[5467, 4123, 4136],

/***/ 4179:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*******************************************************************************************************************************************************/
[5468, 4137, 4120, 4125, 4119, 4124, 4115, 4116],

/***/ 4180:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \***************************************************************************************************************************************************************/
328,

/***/ 4181:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \********************************************************************************************************************************************************************/
[5469, 4173],

/***/ 4182:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \****************************************************************************************************************************************************************/
[5470, 4173],

/***/ 4183:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \*********************************************************************************************************************************/
[5471, 4184, 4188, 4148],

/***/ 4184:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************/
[5472, 4185],

/***/ 4185:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*************************************************************************************************************************************************************/
[5473, 4186, 4108],

/***/ 4186:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \**********************************************************************************************************************************************************************/
[5474, 4106, 4187],

/***/ 4187:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*****************************************************************************************************************************************************/
[5475, 4114, 4113, 4109, 4179],

/***/ 4188:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \**************************************************************************************************************************************/
[5476, 4189],

/***/ 4189:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \***************************************************************************************************************************************************/
[5477, 4190, 4108],

/***/ 4190:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \************************************************************************************************************************************************************/
[5478, 4106, 4158],

/***/ 4191:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/classnames/index.js ***!
  \*******************************************************************************************************************/
339,

/***/ 4192:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \***********************************************************************************************************************************/
[5479, 3877, 4193],

/***/ 4193:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \********************************************************************************************************************************************************/
341,

/***/ 4194:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*************************************************************************************************************************/
[5480, 4195, 4139, 4198, 3877, 4199],

/***/ 4195:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \***************************************************************************************************************************************/
[5481, 4196],

/***/ 4196:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \****************************************************************************************************************************************************/
[5482, 4197, 4108],

/***/ 4197:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \*************************************************************************************************************************************************************/
[5483, 4106, 4121],

/***/ 4198:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/invariant/browser.js ***!
  \********************************************************************************************************************/
346,

/***/ 4199:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \**********************************************************************************************************************/
347,

/***/ 4200:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***************************************************************************************************************/
[5484, 4139, 4138, 4146, 4147, 4183, 3877, 4192],

/***/ 4201:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormGroup.js ***!
  \**************************************************************************************************************/
[5485, 4139, 4138, 4146, 4147, 4183, 4191, 3877, 4194, 4199, 4202],

/***/ 4202:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*********************************************************************************************************************************/
[5486, 3877],

/***/ 4203:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControl.js ***!
  \****************************************************************************************************************/
[5487, 4139, 4138, 4146, 4147, 4183, 4191, 3877, 4192, 4204, 4205, 4207, 4194],

/***/ 4204:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/warning/browser.js ***!
  \******************************************************************************************************************/
352,

/***/ 4205:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \************************************************************************************************************************/
[5488, 4138, 4139, 4146, 4147, 4183, 4191, 3877, 4206, 4194],

/***/ 4206:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Glyphicon.js ***!
  \**************************************************************************************************************/
[5489, 4139, 4138, 4146, 4147, 4183, 4191, 3877, 4194],

/***/ 4207:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlStatic.js ***!
  \**********************************************************************************************************************/
[5490, 4139, 4138, 4146, 4147, 4183, 4191, 3877, 4192, 4194],

/***/ 4208:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
356,

/***/ 4209:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/react-emojione.js ***!
  \******************************************************************************************************************/
[5491, 4210, 4211, 4371],

/***/ 4210:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*************************************************************************************************************************/
358,

/***/ 4211:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \******************************************************************************************************************************/
[5492, 4212, 4373],

/***/ 4212:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \****************************************************************************************************************************/
[5493, 4213, 4369, 4371],

/***/ 4213:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/react.js ***!
  \*************************************************************************************************************/
[5266, 4214],

/***/ 4214:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/React.js ***!
  \*****************************************************************************************************************/
[5494, 4215, 4359, 4363, 4250, 4368],

/***/ 4215:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOM.js ***!
  \********************************************************************************************************************/
[5495, 4216, 4217, 4282, 4256, 4239, 4229, 4261, 4265, 4357, 4302, 4358, 4236, 4220],

/***/ 4216:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************************************************/
364,

/***/ 4217:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************************************************/
[5496, 4218, 4233, 4237, 4239, 4250, 4232, 4231, 4281],

/***/ 4218:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************************************************/
[5497, 4219, 4227, 4229, 4230, 4231, 4224],

/***/ 4219:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Danger.js ***!
  \******************************************************************************************************************/
[5498, 4220, 4221, 4226, 4225, 4224],

/***/ 4220:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \***************************************************************************************************************************************/
368,

/***/ 4221:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \****************************************************************************************************************************************/
[5499, 4220, 4222, 4225, 4224],

/***/ 4222:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \***************************************************************************************************************************************/
[5500, 4223],

/***/ 4223:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/toArray.js ***!
  \**************************************************************************************************************************/
[5501, 4224],

/***/ 4224:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/invariant.js ***!
  \****************************************************************************************************************************/
372,

/***/ 4225:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \********************************************************************************************************************************/
[5502, 4220, 4224],

/***/ 4226:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyFunction.js ***!
  \********************************************************************************************************************************/
374,

/***/ 4227:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************************************************/
[5503, 4228],

/***/ 4228:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyMirror.js ***!
  \****************************************************************************************************************************/
[5504, 4224],

/***/ 4229:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPerf.js ***!
  \*********************************************************************************************************************/
377,

/***/ 4230:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************************************************/
[5505, 4220],

/***/ 4231:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setTextContent.js ***!
  \**************************************************************************************************************************/
[5506, 4220, 4232, 4230],

/***/ 4232:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************************************************/
380,

/***/ 4233:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[5507, 4234, 4229, 4235, 4236],

/***/ 4234:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************************************************/
[5508, 4224],

/***/ 4235:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************************************************/
[5509, 4232],

/***/ 4236:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/warning.js ***!
  \**************************************************************************************************************************/
[5510, 4226],

/***/ 4237:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************************************************/
[5511, 4238, 4239],

/***/ 4238:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************************************************/
[5512, 4218, 4233, 4239, 4229, 4224],

/***/ 4239:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMount.js ***!
  \**********************************************************************************************************************/
[5513, 4234, 4240, 4216, 4252, 4253, 4255, 4256, 4258, 4259, 4229, 4261, 4264, 4265, 4250, 4269, 4270, 4273, 4224, 4230, 4278, 4281, 4236],

/***/ 4240:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************************************************/
[5514, 4241, 4242, 4243, 4248, 4229, 4249, 4250, 4251],

/***/ 4241:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventConstants.js ***!
  \**************************************************************************************************************************/
[5515, 4228],

/***/ 4242:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************************************************/
[5516, 4243, 4244, 4245, 4246, 4247, 4224, 4236],

/***/ 4243:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************************************************/
[5517, 4224],

/***/ 4244:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************************************************/
[5518, 4241, 4245, 4224, 4236],

/***/ 4245:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************************************************/
393,

/***/ 4246:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************************************************/
[5519, 4224],

/***/ 4247:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************************************************/
395,

/***/ 4248:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************************************************/
[5520, 4242],

/***/ 4249:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************************************************/
397,

/***/ 4250:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Object.assign.js ***!
  \*************************************************************************************************************************/
398,

/***/ 4251:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************************************************/
[5521, 4220],

/***/ 4252:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************************************************/
400,

/***/ 4253:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElement.js ***!
  \************************************************************************************************************************/
[5522, 4216, 4250, 4254],

/***/ 4254:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************************************************/
402,

/***/ 4255:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \***************************************************************************************************************************************/
403,

/***/ 4256:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceHandles.js ***!
  \********************************************************************************************************************************/
[5523, 4257, 4224],

/***/ 4257:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRootIndex.js ***!
  \**************************************************************************************************************************/
405,

/***/ 4258:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************************************************/
406,

/***/ 4259:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************************************************/
[5524, 4260],

/***/ 4260:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/adler32.js ***!
  \*******************************************************************************************************************/
408,

/***/ 4261:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************************************************/
[5525, 4262],

/***/ 4262:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRef.js ***!
  \********************************************************************************************************************/
[5526, 4263],

/***/ 4263:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************************************************/
[5527, 4224],

/***/ 4264:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************************************************/
[5528, 4216, 4253, 4258, 4265, 4250, 4224, 4236],

/***/ 4265:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************************************************/
[5529, 4266, 4267, 4229, 4261, 4268, 4250, 4224],

/***/ 4266:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************************************************/
[5530, 4267, 4250, 4224],

/***/ 4267:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/PooledClass.js ***!
  \***********************************************************************************************************************/
[5531, 4224],

/***/ 4268:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Transaction.js ***!
  \***********************************************************************************************************************/
[5532, 4224],

/***/ 4269:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyObject.js ***!
  \******************************************************************************************************************************/
417,

/***/ 4270:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/containsNode.js ***!
  \*******************************************************************************************************************************/
[5533, 4271],

/***/ 4271:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isTextNode.js ***!
  \*****************************************************************************************************************************/
[5534, 4272],

/***/ 4272:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isNode.js ***!
  \*************************************************************************************************************************/
420,

/***/ 4273:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************************************************/
[5535, 4274, 4279, 4280, 4250, 4224, 4236],

/***/ 4274:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************************************************/
[5536, 4275, 4216, 4253, 4258, 4229, 4276, 4277, 4261, 4264, 4250, 4269, 4224, 4278, 4236],

/***/ 4275:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************************************************/
[5537, 4224],

/***/ 4276:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************************************************/
[5538, 4228],

/***/ 4277:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************************************************/
425,

/***/ 4278:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************************************************/
426,

/***/ 4279:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************************************************/
[5539, 4253, 4255, 4261, 4250],

/***/ 4280:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNativeComponent.js ***!
  \********************************************************************************************************************************/
[5540, 4250, 4224],

/***/ 4281:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************************************************/
[5541, 4250, 4226, 4236],

/***/ 4282:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************************************************/
[5542, 4283, 4291, 4294, 4295, 4296, 4220, 4300, 4301, 4237, 4303, 4304, 4217, 4329, 4332, 4256, 4239, 4336, 4341, 4342, 4343, 4352, 4353],

/***/ 4283:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************************************************/
[5543, 4241, 4284, 4220, 4285, 4287, 4289, 4290],

/***/ 4284:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************************************************/
[5544, 4241, 4242, 4236, 4246, 4247],

/***/ 4285:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************************************************/
[5545, 4267, 4250, 4286],

/***/ 4286:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************************************************/
[5546, 4220],

/***/ 4287:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************************************************/
[5547, 4288],

/***/ 4288:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************************************************/
[5548, 4267, 4250, 4226, 4236],

/***/ 4289:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************************************************/
[5549, 4288],

/***/ 4290:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyOf.js ***!
  \************************************************************************************************************************/
438,

/***/ 4291:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************************************************/
[5550, 4241, 4242, 4284, 4220, 4265, 4288, 4292, 4251, 4293, 4290],

/***/ 4292:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************************************************/
440,

/***/ 4293:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************************************************/
441,

/***/ 4294:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ClientReactRootIndex.js ***!
  \********************************************************************************************************************************/
442,

/***/ 4295:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************************************************/
[5551, 4290],

/***/ 4296:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************************************************/
[5552, 4241, 4284, 4297, 4239, 4290],

/***/ 4297:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************************************************/
[5553, 4298, 4249, 4299],

/***/ 4298:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************************************************/
[5554, 4288, 4292],

/***/ 4299:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************************************************/
447,

/***/ 4300:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************************************************/
[5555, 4234, 4220],

/***/ 4301:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserComponentMixin.js ***!
  \**************************************************************************************************************************************/
[5556, 4258, 4302, 4236],

/***/ 4302:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************************************************/
[5557, 4216, 4258, 4239, 4224, 4236],

/***/ 4303:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************************************************/
[5558, 4265, 4268, 4250, 4226],

/***/ 4304:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************************************************/
[5559, 4305, 4307, 4234, 4233, 4241, 4240, 4237, 4315, 4316, 4320, 4323, 4324, 4239, 4325, 4229, 4264, 4250, 4254, 4232, 4224, 4251, 4290, 4230, 4231, 4328, 4281, 4236],

/***/ 4305:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************************************************/
[5560, 4239, 4302, 4306],

/***/ 4306:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/focusNode.js ***!
  \****************************************************************************************************************************/
454,

/***/ 4307:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[5561, 4308, 4220, 4229, 4309, 4311, 4312, 4314, 4236],

/***/ 4308:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************************************************/
456,

/***/ 4309:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \************************************************************************************************************************************/
[5562, 4310],

/***/ 4310:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelize.js ***!
  \***************************************************************************************************************************/
458,

/***/ 4311:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************************************************/
[5563, 4308],

/***/ 4312:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \*************************************************************************************************************************************/
[5564, 4313],

/***/ 4313:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenate.js ***!
  \****************************************************************************************************************************/
461,

/***/ 4314:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \************************************************************************************************************************************/
462,

/***/ 4315:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************************************************/
463,

/***/ 4316:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************************************************/
[5565, 4238, 4317, 4239, 4265, 4250, 4224],

/***/ 4317:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************************************************/
[5566, 4318, 4276, 4224, 4236],

/***/ 4318:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************************************************/
[5567, 4253, 4277, 4226, 4319],

/***/ 4319:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************************************************/
467,

/***/ 4320:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************************************************/
[5568, 4321, 4323, 4250, 4236],

/***/ 4321:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************************************************/
[5569, 4267, 4253, 4226, 4322],

/***/ 4322:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************************************************/
[5570, 4216, 4253, 4256, 4319, 4224, 4236],

/***/ 4323:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************************************************/
[5571, 4317, 4239, 4265, 4250, 4236],

/***/ 4324:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************************************************/
[5572, 4317, 4238, 4265, 4250, 4224, 4236],

/***/ 4325:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************************************************/
[5573, 4275, 4227, 4216, 4261, 4326, 4327],

/***/ 4326:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************************************************/
[5574, 4261, 4273, 4278, 4322, 4236],

/***/ 4327:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************************************************/
[5575, 4322, 4236],

/***/ 4328:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*******************************************************************************************************************************/
476,

/***/ 4329:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************************************************/
[5576, 4330, 4220, 4267, 4256, 4239, 4265, 4250, 4292, 4331],

/***/ 4330:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/EventListener.js ***!
  \********************************************************************************************************************************/
[5577, 4226],

/***/ 4331:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \*********************************************************************************************************************************************/
479,

/***/ 4332:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************************************************/
[5578, 4234, 4242, 4275, 4333, 4279, 4240, 4280, 4229, 4257, 4265],

/***/ 4333:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactClass.js ***!
  \**********************************************************************************************************************/
[5579, 4334, 4253, 4276, 4277, 4335, 4250, 4269, 4224, 4228, 4290, 4236],

/***/ 4334:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************************************************/
[5580, 4335, 4254, 4269, 4224, 4236],

/***/ 4335:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************************************************/
[5581, 4236],

/***/ 4336:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************************************************/
[5582, 4266, 4267, 4240, 4252, 4337, 4268, 4250],

/***/ 4337:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************************************************/
[5583, 4338, 4270, 4306, 4340],

/***/ 4338:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************************************************/
[5584, 4220, 4339, 4286],

/***/ 4339:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************************************************/
487,

/***/ 4340:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getActiveElement.js ***!
  \***********************************************************************************************************************************/
488,

/***/ 4341:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************************************************/
[5585, 4241, 4284, 4220, 4337, 4288, 4340, 4293, 4290, 4328],

/***/ 4342:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ServerReactRootIndex.js ***!
  \********************************************************************************************************************************/
490,

/***/ 4343:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************************************************/
[5586, 4241, 4330, 4284, 4239, 4344, 4288, 4345, 4346, 4297, 4349, 4350, 4298, 4351, 4226, 4347, 4224, 4290],

/***/ 4344:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************************************************/
[5587, 4288],

/***/ 4345:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************************************************/
[5588, 4298],

/***/ 4346:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************************************************/
[5589, 4298, 4347, 4348, 4299],

/***/ 4347:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************************************************/
495,

/***/ 4348:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventKey.js ***!
  \***********************************************************************************************************************/
[5590, 4347],

/***/ 4349:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************************************************/
[5591, 4297],

/***/ 4350:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************************************************/
[5592, 4298, 4299],

/***/ 4351:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************************************************/
[5593, 4297],

/***/ 4352:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************************************************/
[5594, 4234],

/***/ 4353:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerf.js ***!
  \****************************************************************************************************************************/
[5595, 4234, 4354, 4239, 4229, 4355],

/***/ 4354:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \************************************************************************************************************************************/
[5596, 4250],

/***/ 4355:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performanceNow.js ***!
  \*********************************************************************************************************************************/
[5597, 4356],

/***/ 4356:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performance.js ***!
  \******************************************************************************************************************************/
[5598, 4220],

/***/ 4357:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactVersion.js ***!
  \************************************************************************************************************************/
505,

/***/ 4358:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************************************************/
[5599, 4239],

/***/ 4359:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMServer.js ***!
  \**************************************************************************************************************************/
[5600, 4282, 4360, 4357],

/***/ 4360:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRendering.js ***!
  \********************************************************************************************************************************/
[5601, 4303, 4253, 4256, 4259, 4361, 4362, 4265, 4269, 4273, 4224],

/***/ 4361:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerBatchingStrategy.js ***!
  \***************************************************************************************************************************************/
509,

/***/ 4362:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************************************************/
[5602, 4267, 4266, 4268, 4250, 4226],

/***/ 4363:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactIsomorphic.js ***!
  \***************************************************************************************************************************/
[5603, 4321, 4334, 4333, 4364, 4253, 4365, 4318, 4357, 4250, 4367],

/***/ 4364:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************************************************/
[5604, 4253, 4365, 4366],

/***/ 4365:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************************************************/
[5605, 4253, 4276, 4277, 4216, 4254, 4319, 4224, 4236],

/***/ 4366:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/mapObject.js ***!
  \****************************************************************************************************************************/
514,

/***/ 4367:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/onlyChild.js ***!
  \*********************************************************************************************************************/
[5606, 4253, 4224],

/***/ 4368:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/deprecated.js ***!
  \**********************************************************************************************************************/
[5607, 4250, 4236],

/***/ 4369:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**************************************************************************************************************************/
[5608, 4370],

/***/ 4370:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \************************************************************************************************************************************/
518,

/***/ 4371:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*********************************************************************************************************************************/
[5609, 4372],

/***/ 4372:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/emoji-data.js ***!
  \*******************************************************************************************************************/
520,

/***/ 4373:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \******************************************************************************************************************************/
[5610, 4371],

/***/ 4374:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
[5611, 4375, 4060],

/***/ 4375:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
[5612, 4059],

/***/ 4376:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[5924, 4377, 4537],

/***/ 4377:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[5925, 4378, 4534, 4536],

/***/ 4378:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/react.js ***!
  \************************************************************************************/
[5266, 4379],

/***/ 4379:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/React.js ***!
  \****************************************************************************************/
[5494, 4380, 4524, 4528, 4415, 4533],

/***/ 4380:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOM.js ***!
  \*******************************************************************************************/
[5495, 4381, 4382, 4447, 4421, 4404, 4394, 4426, 4430, 4522, 4467, 4523, 4401, 4385],

/***/ 4381:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCurrentOwner.js ***!
  \****************************************************************************************************/
364,

/***/ 4382:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextComponent.js ***!
  \********************************************************************************************************/
[5496, 4383, 4398, 4402, 4404, 4415, 4397, 4396, 4446],

/***/ 4383:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMChildrenOperations.js ***!
  \********************************************************************************************************/
[5497, 4384, 4392, 4394, 4395, 4396, 4389],

/***/ 4384:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Danger.js ***!
  \*****************************************************************************************/
[5498, 4385, 4386, 4391, 4390, 4389],

/***/ 4385:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**************************************************************************************************************/
368,

/***/ 4386:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***************************************************************************************************************/
[5499, 4385, 4387, 4390, 4389],

/***/ 4387:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \**************************************************************************************************************/
[5500, 4388],

/***/ 4388:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/toArray.js ***!
  \*************************************************************************************************/
[5501, 4389],

/***/ 4389:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/invariant.js ***!
  \***************************************************************************************************/
372,

/***/ 4390:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*******************************************************************************************************/
[5502, 4385, 4389],

/***/ 4391:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*******************************************************************************************************/
374,

/***/ 4392:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*************************************************************************************************************/
[5503, 4393],

/***/ 4393:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyMirror.js ***!
  \***************************************************************************************************/
[5504, 4389],

/***/ 4394:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPerf.js ***!
  \********************************************************************************************/
377,

/***/ 4395:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setInnerHTML.js ***!
  \***********************************************************************************************/
[5505, 4385],

/***/ 4396:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setTextContent.js ***!
  \*************************************************************************************************/
[5506, 4385, 4397, 4395],

/***/ 4397:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/escapeTextContentForBrowser.js ***!
  \**************************************************************************************************************/
380,

/***/ 4398:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMPropertyOperations.js ***!
  \********************************************************************************************************/
[5507, 4399, 4394, 4400, 4401],

/***/ 4399:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMProperty.js ***!
  \**********************************************************************************************/
[5508, 4389],

/***/ 4400:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \****************************************************************************************************************/
[5509, 4397],

/***/ 4401:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/warning.js ***!
  \*************************************************************************************************/
[5510, 4391],

/***/ 4402:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*******************************************************************************************************************/
[5511, 4403, 4404],

/***/ 4403:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMIDOperations.js ***!
  \*******************************************************************************************************/
[5512, 4383, 4398, 4404, 4394, 4389],

/***/ 4404:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMount.js ***!
  \*********************************************************************************************/
[5513, 4399, 4405, 4381, 4417, 4418, 4420, 4421, 4423, 4424, 4394, 4426, 4429, 4430, 4415, 4434, 4435, 4438, 4389, 4395, 4443, 4446, 4401],

/***/ 4405:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***********************************************************************************************************/
[5514, 4406, 4407, 4408, 4413, 4394, 4414, 4415, 4416],

/***/ 4406:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventConstants.js ***!
  \*************************************************************************************************/
[5515, 4393],

/***/ 4407:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginHub.js ***!
  \*************************************************************************************************/
[5516, 4408, 4409, 4410, 4411, 4412, 4389, 4401],

/***/ 4408:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginRegistry.js ***!
  \******************************************************************************************************/
[5517, 4389],

/***/ 4409:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginUtils.js ***!
  \***************************************************************************************************/
[5518, 4406, 4410, 4389, 4401],

/***/ 4410:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactErrorUtils.js ***!
  \**************************************************************************************************/
393,

/***/ 4411:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/accumulateInto.js ***!
  \*************************************************************************************************/
[5519, 4389],

/***/ 4412:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/forEachAccumulated.js ***!
  \*****************************************************************************************************/
395,

/***/ 4413:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventEmitterMixin.js ***!
  \*********************************************************************************************************/
[5520, 4407],

/***/ 4414:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ViewportMetrics.js ***!
  \**************************************************************************************************/
397,

/***/ 4415:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Object.assign.js ***!
  \************************************************************************************************/
398,

/***/ 4416:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isEventSupported.js ***!
  \***************************************************************************************************/
[5521, 4385],

/***/ 4417:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*******************************************************************************************************/
400,

/***/ 4418:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElement.js ***!
  \***********************************************************************************************/
[5522, 4381, 4415, 4419],

/***/ 4419:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/canDefineProperty.js ***!
  \****************************************************************************************************/
402,

/***/ 4420:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \**************************************************************************************************************/
403,

/***/ 4421:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceHandles.js ***!
  \*******************************************************************************************************/
[5523, 4422, 4389],

/***/ 4422:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRootIndex.js ***!
  \*************************************************************************************************/
405,

/***/ 4423:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceMap.js ***!
  \***************************************************************************************************/
406,

/***/ 4424:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMarkupChecksum.js ***!
  \******************************************************************************************************/
[5524, 4425],

/***/ 4425:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/adler32.js ***!
  \******************************************************************************************/
408,

/***/ 4426:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconciler.js ***!
  \**************************************************************************************************/
[5525, 4427],

/***/ 4427:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRef.js ***!
  \*******************************************************************************************/
[5526, 4428],

/***/ 4428:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactOwner.js ***!
  \*********************************************************************************************/
[5527, 4389],

/***/ 4429:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdateQueue.js ***!
  \***************************************************************************************************/
[5528, 4381, 4418, 4423, 4430, 4415, 4389, 4401],

/***/ 4430:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdates.js ***!
  \***********************************************************************************************/
[5529, 4431, 4432, 4394, 4426, 4433, 4415, 4389],

/***/ 4431:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CallbackQueue.js ***!
  \************************************************************************************************/
[5530, 4432, 4415, 4389],

/***/ 4432:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/PooledClass.js ***!
  \**********************************************************************************************/
[5531, 4389],

/***/ 4433:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Transaction.js ***!
  \**********************************************************************************************/
[5532, 4389],

/***/ 4434:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyObject.js ***!
  \*****************************************************************************************************/
417,

/***/ 4435:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/containsNode.js ***!
  \******************************************************************************************************/
[5533, 4436],

/***/ 4436:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isTextNode.js ***!
  \****************************************************************************************************/
[5534, 4437],

/***/ 4437:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isNode.js ***!
  \************************************************************************************************/
420,

/***/ 4438:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/instantiateReactComponent.js ***!
  \************************************************************************************************************/
[5535, 4439, 4444, 4445, 4415, 4389, 4401],

/***/ 4439:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCompositeComponent.js ***!
  \**********************************************************************************************************/
[5536, 4440, 4381, 4418, 4423, 4394, 4441, 4442, 4426, 4429, 4415, 4434, 4389, 4443, 4401],

/***/ 4440:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentEnvironment.js ***!
  \************************************************************************************************************/
[5537, 4389],

/***/ 4441:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocations.js ***!
  \*********************************************************************************************************/
[5538, 4393],

/***/ 4442:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*************************************************************************************************************/
425,

/***/ 4443:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/shouldUpdateReactComponent.js ***!
  \*************************************************************************************************************/
426,

/***/ 4444:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponent.js ***!
  \******************************************************************************************************/
[5539, 4418, 4420, 4426, 4415],

/***/ 4445:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNativeComponent.js ***!
  \*******************************************************************************************************/
[5540, 4415, 4389],

/***/ 4446:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/validateDOMNesting.js ***!
  \*****************************************************************************************************/
[5541, 4415, 4391, 4401],

/***/ 4447:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultInjection.js ***!
  \********************************************************************************************************/
[5542, 4448, 4456, 4459, 4460, 4461, 4385, 4465, 4466, 4402, 4468, 4469, 4382, 4494, 4497, 4421, 4404, 4501, 4506, 4507, 4508, 4517, 4518],

/***/ 4448:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/BeforeInputEventPlugin.js ***!
  \*********************************************************************************************************/
[5543, 4406, 4449, 4385, 4450, 4452, 4454, 4455],

/***/ 4449:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPropagators.js ***!
  \***************************************************************************************************/
[5544, 4406, 4407, 4401, 4411, 4412],

/***/ 4450:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/FallbackCompositionState.js ***!
  \***********************************************************************************************************/
[5545, 4432, 4415, 4451],

/***/ 4451:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getTextContentAccessor.js ***!
  \*********************************************************************************************************/
[5546, 4385],

/***/ 4452:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticCompositionEvent.js ***!
  \************************************************************************************************************/
[5547, 4453],

/***/ 4453:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticEvent.js ***!
  \*************************************************************************************************/
[5548, 4432, 4415, 4391, 4401],

/***/ 4454:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticInputEvent.js ***!
  \******************************************************************************************************/
[5549, 4453],

/***/ 4455:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyOf.js ***!
  \***********************************************************************************************/
438,

/***/ 4456:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ChangeEventPlugin.js ***!
  \****************************************************************************************************/
[5550, 4406, 4407, 4449, 4385, 4430, 4453, 4457, 4416, 4458, 4455],

/***/ 4457:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventTarget.js ***!
  \*************************************************************************************************/
440,

/***/ 4458:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isTextInputElement.js ***!
  \*****************************************************************************************************/
441,

/***/ 4459:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ClientReactRootIndex.js ***!
  \*******************************************************************************************************/
442,

/***/ 4460:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DefaultEventPluginOrder.js ***!
  \**********************************************************************************************************/
[5551, 4455],

/***/ 4461:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EnterLeaveEventPlugin.js ***!
  \********************************************************************************************************/
[5552, 4406, 4449, 4462, 4404, 4455],

/***/ 4462:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticMouseEvent.js ***!
  \******************************************************************************************************/
[5553, 4463, 4414, 4464],

/***/ 4463:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticUIEvent.js ***!
  \***************************************************************************************************/
[5554, 4453, 4457],

/***/ 4464:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventModifierState.js ***!
  \********************************************************************************************************/
447,

/***/ 4465:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \********************************************************************************************************/
[5555, 4399, 4385],

/***/ 4466:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*************************************************************************************************************/
[5556, 4423, 4467, 4401],

/***/ 4467:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/findDOMNode.js ***!
  \**********************************************************************************************/
[5557, 4381, 4423, 4404, 4389, 4401],

/***/ 4468:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \***************************************************************************************************************/
[5558, 4430, 4433, 4415, 4391],

/***/ 4469:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMComponent.js ***!
  \****************************************************************************************************/
[5559, 4470, 4472, 4399, 4398, 4406, 4405, 4402, 4480, 4481, 4485, 4488, 4489, 4404, 4490, 4394, 4429, 4415, 4419, 4397, 4389, 4416, 4455, 4395, 4396, 4493, 4446, 4401],

/***/ 4470:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/AutoFocusUtils.js ***!
  \*************************************************************************************************/
[5560, 4404, 4467, 4471],

/***/ 4471:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/focusNode.js ***!
  \***************************************************************************************************/
454,

/***/ 4472:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSPropertyOperations.js ***!
  \********************************************************************************************************/
[5561, 4473, 4385, 4394, 4474, 4476, 4477, 4479, 4401],

/***/ 4473:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSProperty.js ***!
  \**********************************************************************************************/
456,

/***/ 4474:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***********************************************************************************************************/
[5562, 4475],

/***/ 4475:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelize.js ***!
  \**************************************************************************************************/
458,

/***/ 4476:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/dangerousStyleValue.js ***!
  \******************************************************************************************************/
[5563, 4473],

/***/ 4477:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \************************************************************************************************************/
[5564, 4478],

/***/ 4478:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenate.js ***!
  \***************************************************************************************************/
461,

/***/ 4479:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***********************************************************************************************************/
462,

/***/ 4480:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMButton.js ***!
  \*************************************************************************************************/
463,

/***/ 4481:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMInput.js ***!
  \************************************************************************************************/
[5565, 4403, 4482, 4404, 4430, 4415, 4389],

/***/ 4482:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/LinkedValueUtils.js ***!
  \***************************************************************************************************/
[5566, 4483, 4441, 4389, 4401],

/***/ 4483:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypes.js ***!
  \*************************************************************************************************/
[5567, 4418, 4442, 4391, 4484],

/***/ 4484:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getIteratorFn.js ***!
  \************************************************************************************************/
467,

/***/ 4485:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMOption.js ***!
  \*************************************************************************************************/
[5568, 4486, 4488, 4415, 4401],

/***/ 4486:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildren.js ***!
  \************************************************************************************************/
[5569, 4432, 4418, 4391, 4487],

/***/ 4487:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/traverseAllChildren.js ***!
  \******************************************************************************************************/
[5570, 4381, 4418, 4421, 4484, 4389, 4401],

/***/ 4488:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelect.js ***!
  \*************************************************************************************************/
[5571, 4482, 4404, 4430, 4415, 4401],

/***/ 4489:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextarea.js ***!
  \***************************************************************************************************/
[5572, 4482, 4403, 4430, 4415, 4389, 4401],

/***/ 4490:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChild.js ***!
  \**************************************************************************************************/
[5573, 4440, 4392, 4381, 4426, 4491, 4492],

/***/ 4491:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildReconciler.js ***!
  \*******************************************************************************************************/
[5574, 4426, 4438, 4443, 4487, 4401],

/***/ 4492:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/flattenChildren.js ***!
  \**************************************************************************************************/
[5575, 4487, 4401],

/***/ 4493:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/shallowEqual.js ***!
  \******************************************************************************************************/
476,

/***/ 4494:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventListener.js ***!
  \*****************************************************************************************************/
[5576, 4495, 4385, 4432, 4421, 4404, 4430, 4415, 4457, 4496],

/***/ 4495:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/EventListener.js ***!
  \*******************************************************************************************************/
[5577, 4391],

/***/ 4496:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \********************************************************************************************************************/
479,

/***/ 4497:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInjection.js ***!
  \*************************************************************************************************/
[5578, 4399, 4407, 4440, 4498, 4444, 4405, 4445, 4394, 4422, 4430],

/***/ 4498:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactClass.js ***!
  \*********************************************************************************************/
[5579, 4499, 4418, 4441, 4442, 4500, 4415, 4434, 4389, 4393, 4455, 4401],

/***/ 4499:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponent.js ***!
  \*************************************************************************************************/
[5580, 4500, 4419, 4434, 4389, 4401],

/***/ 4500:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*******************************************************************************************************/
[5581, 4401],

/***/ 4501:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconcileTransaction.js ***!
  \************************************************************************************************************/
[5582, 4431, 4432, 4405, 4417, 4502, 4433, 4415],

/***/ 4502:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInputSelection.js ***!
  \******************************************************************************************************/
[5583, 4503, 4435, 4471, 4505],

/***/ 4503:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelection.js ***!
  \****************************************************************************************************/
[5584, 4385, 4504, 4451],

/***/ 4504:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getNodeForCharacterOffset.js ***!
  \************************************************************************************************************/
487,

/***/ 4505:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**********************************************************************************************************/
488,

/***/ 4506:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SelectEventPlugin.js ***!
  \****************************************************************************************************/
[5585, 4406, 4449, 4385, 4502, 4453, 4505, 4458, 4455, 4493],

/***/ 4507:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ServerReactRootIndex.js ***!
  \*******************************************************************************************************/
490,

/***/ 4508:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SimpleEventPlugin.js ***!
  \****************************************************************************************************/
[5586, 4406, 4495, 4449, 4404, 4509, 4453, 4510, 4511, 4462, 4514, 4515, 4463, 4516, 4391, 4512, 4389, 4455],

/***/ 4509:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticClipboardEvent.js ***!
  \**********************************************************************************************************/
[5587, 4453],

/***/ 4510:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticFocusEvent.js ***!
  \******************************************************************************************************/
[5588, 4463],

/***/ 4511:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*********************************************************************************************************/
[5589, 4463, 4512, 4513, 4464],

/***/ 4512:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventCharCode.js ***!
  \***************************************************************************************************/
495,

/***/ 4513:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventKey.js ***!
  \**********************************************************************************************/
[5590, 4512],

/***/ 4514:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticDragEvent.js ***!
  \*****************************************************************************************************/
[5591, 4462],

/***/ 4515:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticTouchEvent.js ***!
  \******************************************************************************************************/
[5592, 4463, 4464],

/***/ 4516:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticWheelEvent.js ***!
  \******************************************************************************************************/
[5593, 4462],

/***/ 4517:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*******************************************************************************************************/
[5594, 4399],

/***/ 4518:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerf.js ***!
  \***************************************************************************************************/
[5595, 4399, 4519, 4404, 4394, 4520],

/***/ 4519:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***********************************************************************************************************/
[5596, 4415],

/***/ 4520:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performanceNow.js ***!
  \********************************************************************************************************/
[5597, 4521],

/***/ 4521:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performance.js ***!
  \*****************************************************************************************************/
[5598, 4385],

/***/ 4522:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactVersion.js ***!
  \***********************************************************************************************/
505,

/***/ 4523:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*************************************************************************************************************/
[5599, 4404],

/***/ 4524:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMServer.js ***!
  \*************************************************************************************************/
[5600, 4447, 4525, 4522],

/***/ 4525:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRendering.js ***!
  \*******************************************************************************************************/
[5601, 4468, 4418, 4421, 4424, 4526, 4527, 4430, 4434, 4438, 4389],

/***/ 4526:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerBatchingStrategy.js ***!
  \**************************************************************************************************************/
509,

/***/ 4527:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRenderingTransaction.js ***!
  \******************************************************************************************************************/
[5602, 4432, 4431, 4433, 4415, 4391],

/***/ 4528:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactIsomorphic.js ***!
  \**************************************************************************************************/
[5603, 4486, 4499, 4498, 4529, 4418, 4530, 4483, 4522, 4415, 4532],

/***/ 4529:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFactories.js ***!
  \****************************************************************************************************/
[5604, 4418, 4530, 4531],

/***/ 4530:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElementValidator.js ***!
  \********************************************************************************************************/
[5605, 4418, 4441, 4442, 4381, 4419, 4484, 4389, 4401],

/***/ 4531:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/mapObject.js ***!
  \***************************************************************************************************/
514,

/***/ 4532:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/onlyChild.js ***!
  \********************************************************************************************/
[5606, 4418, 4389],

/***/ 4533:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/deprecated.js ***!
  \*********************************************************************************************/
[5607, 4415, 4401],

/***/ 4534:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
[5926, 4535, 4060],

/***/ 4535:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
[5927, 4059],

/***/ 4536:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
1667,

/***/ 4537:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[5928, 4378, 4538, 4377],

/***/ 4538:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react-dom/index.js ***!
  \****************************************************************************************/
[5287, 4380],

/***/ 4539:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 4540);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./DifferentialResults.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./DifferentialResults.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4540:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4541:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3877);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 4542);
	
	//*------------------------------------------------------------------*
	
	var RequiredString = React.PropTypes.string.isRequired;
	var RequiredBool = React.PropTypes.bool.isRequired;
	
	var DifferentialFacetsTree = React.createClass({
	    displayName: 'DifferentialFacetsTree',
	
	    propTypes: {
	        /*
	        [
	            { "facetName" : "species",
	              "facetItems" : [ {"name": "homo sapiens", "value": "Homo sapiens", checked: false, disabled: false},
	                               {"name": "arabidopsis thaliana", "value": "arabidopsis thaliana", checked: true, disabled: false} ]
	            },
	            { "facetName" : "experimentType",
	              "facetItems" : [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA", checked: false, disabled: true},
	                                {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA", checked: false, disabled: false} ]
	            },
	            { "facetName" : "factors",
	              "facetItems" : [ {"name": "genotype", "value": "genotype", checked: true, disabled: true} ]
	            },
	            { "facetName" : "numReplicates",
	              "facetItems" : [ {"name": "3", "value": "3", checked: true, disabled: true} ]
	            },
	            { "facetName" : "regulation".
	              "facetItems" : [ {"name": "UP", "value": "Up", checked: true, disabled: false},
	                            {"name": "DOWN", "value": "Down", checked: false, disabled: false} ]
	            }
	        ]
	        */
	        facets: React.PropTypes.arrayOf(React.PropTypes.shape({
	            facetName: RequiredString,
	            facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
	                name: RequiredString,
	                value: RequiredString,
	                checked: RequiredBool,
	                disabled: RequiredBool
	            }).isRequired).isRequired
	        }).isRequired).isRequired,
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function _setChecked(facetName, facetItemName, checked) {
	        this.props.setChecked(facetName, facetItemName, checked);
	    },
	    render: function render() {
	        var _this = this;
	
	        var facets = this.props.facets.map(function (facet) {
	            return React.createElement(Facet, {
	                key: facet.facetName,
	                facetName: facet.facetName,
	                facetItems: facet.facetItems,
	                setChecked: _this._setChecked
	            });
	        });
	
	        return React.createElement(
	            'div',
	            { className: 'hidden-xs gxaFacetsContainer' },
	            React.createElement(
	                'h3',
	                null,
	                'Filter your results'
	            ),
	            facets
	        );
	    }
	});
	
	var Facet = React.createClass({
	    displayName: 'Facet',
	
	    propTypes: {
	        facetName: React.PropTypes.string.isRequired,
	        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
	            name: RequiredString,
	            value: RequiredString,
	            checked: RequiredBool,
	            disabled: RequiredBool
	        }).isRequired).isRequired,
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function _setChecked(facetItemName, checked) {
	        this.props.setChecked(this.props.facetName, facetItemName, checked);
	    },
	    _prettifyFacetName: function _prettifyFacetName(facetName) {
	        switch (facetName) {
	            case 'kingdom':
	                return 'Kingdom';
	            case 'species':
	                return 'Species';
	            case 'experimentType':
	                return 'Experiment type';
	            case 'factors':
	                return 'Experimental variables';
	            case 'numReplicates':
	                return 'Number of replicates';
	            case 'regulation':
	                return 'Regulation';
	            default:
	                return facetName;
	        }
	    },
	    render: function render() {
	        var _this2 = this;
	
	        var facetItems = this.props.facetItems.map(function (facetItem) {
	            return React.createElement(FacetItem, {
	                key: facetItem.name,
	                name: facetItem.name,
	                value: facetItem.value,
	                checked: facetItem.checked,
	                disabled: facetItem.disabled,
	                setChecked: _this2._setChecked
	            });
	        });
	
	        var className = this.props.facetName === 'species' ? 'gxaSpeciesFacet' : '';
	
	        return React.createElement(
	            'div',
	            { className: 'gxaFacetItem' },
	            React.createElement(
	                'h4',
	                null,
	                this._prettifyFacetName(this.props.facetName)
	            ),
	            React.createElement(
	                'ul',
	                { className: className },
	                facetItems
	            )
	        );
	    }
	});
	
	var FacetItem = React.createClass({
	    displayName: 'FacetItem',
	
	    propTypes: {
	        name: RequiredString,
	        value: RequiredString,
	        checked: RequiredBool,
	        disabled: RequiredBool,
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function _setChecked() {
	        this.props.setChecked(this.props.name, !this.props.checked);
	    },
	    render: function render() {
	        var className = this.props.disabled ? 'gxaDisabledFacet' : '';
	        return React.createElement(
	            'li',
	            { className: className },
	            React.createElement('input', { type: 'checkbox', checked: this.props.checked, onChange: this._setChecked, disabled: this.props.disabled }),
	            this.props.value
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DifferentialFacetsTree;

/***/ },

/***/ 4542:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 4543);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 4060)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./DifferentialFacetsTree.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./DifferentialFacetsTree.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 4543:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 4059)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 2px 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 8px;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4 {\n    font-weight: bold;\n    font-size: 133%;\n    padding: 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 4544:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 179);
	var QueryString = __webpack_require__(/*! querystring */ 3695);
	
	/**
	 * Stringify the `query` object, assign it to the `ds` search field in the URL and store it in the History
	 * @param {object} querySelect
	 * @param {boolean} replace - use `replaceState` instead of `pushState`
	 */
	exports.differentialPush = function pushQueryIntoBrowserHistory(querySelect, replace) {
	    var currentUrlObject = Url.parse(window.location.toString());
	
	    var newUrlQueryParams = QueryString.parse(currentUrlObject.query);
	    newUrlQueryParams.ds = JSON.stringify(querySelect);
	
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
	
	exports.parseDifferentialUrlParameter = function getQuerySelectFromLocation() {
	    var location = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : window.location;
	
	    var currentURL = Url.parse(location.toString());
	    var differentialSelectParam = QueryString.parse(currentURL.query).ds;
	    return differentialSelectParam ? JSON.parse(differentialSelectParam) : {};
	};

/***/ }

});
//# sourceMappingURL=expressionAtlasDifferentialExpression.bundle.js.map