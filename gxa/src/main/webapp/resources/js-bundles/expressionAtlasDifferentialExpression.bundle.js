var expressionAtlasDifferentialExpression =
webpackJsonp_name_([6],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */6009);


/***/ },

/***/ 2698:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/index.js ***!
  \****************************************************************/
[7765, 2699, 2700],

/***/ 2699:
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

/***/ 2700:
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

/***/ 6009:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 6010);

/***/ },

/***/ 6010:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 6181);
	
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

/***/ 6011:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[7635, 6012],

/***/ 6012:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[7636, 6013, 6014, 6026, 6029, 6030, 6035, 6018, 6040, 6041, 6042, 6020, 6036],

/***/ 6013:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/object-assign/index.js ***!
  \********************************************************************************/
5,

/***/ 6014:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[7637, 6015, 6018, 6021, 6023],

/***/ 6015:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[7638, 6016, 6017],

/***/ 6016:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/reactProdInvariant.js ***!
  \*********************************************************************************/
8,

/***/ 6017:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/invariant.js ***!
  \*******************************************************************************/
9,

/***/ 6018:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[7639, 6013, 6019, 6020, 6022],

/***/ 6019:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
11,

/***/ 6020:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/warning.js ***!
  \*****************************************************************************/
[7640, 6021],

/***/ 6021:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyFunction.js ***!
  \***********************************************************************************/
13,

/***/ 6022:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
14,

/***/ 6023:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[7641, 6016, 6019, 6018, 6024, 6017, 6025, 6020],

/***/ 6024:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
16,

/***/ 6025:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/KeyEscapeUtils.js ***!
  \*****************************************************************************/
17,

/***/ 6026:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[7642, 6016, 6027, 6022, 6028, 6017, 6020],

/***/ 6027:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[7643, 6020],

/***/ 6028:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyObject.js ***!
  \*********************************************************************************/
20,

/***/ 6029:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPureComponent.js ***!
  \*********************************************************************************/
[7644, 6013, 6026, 6027, 6028],

/***/ 6030:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[7645, 6016, 6013, 6026, 6018, 6031, 6033, 6027, 6028, 6017, 6032, 6034, 6020],

/***/ 6031:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[7646, 6032],

/***/ 6032:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyMirror.js ***!
  \*******************************************************************************/
[7647, 6017],

/***/ 6033:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
25,

/***/ 6034:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyOf.js ***!
  \***************************************************************************/
26,

/***/ 6035:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[7648, 6018, 6036],

/***/ 6036:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[7649, 6019, 6037, 6018, 6031, 6038, 6022, 6024, 6020],

/***/ 6037:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentTreeHook.js ***!
  \*************************************************************************************/
[7650, 6016, 6019, 6017, 6020],

/***/ 6038:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/checkReactTypeSpec.js ***!
  \*********************************************************************************/
[7651, 6016, 6033, 6039, 6017, 6020, 6037, 6037],

/***/ 6039:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypesSecret.js ***!
  \***********************************************************************************/
32,

/***/ 6040:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[7652, 6018, 6033, 6039, 6021, 6024, 6020],

/***/ 6041:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
34,

/***/ 6042:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[7653, 6016, 6018, 6017],

/***/ 6043:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[7656, 6044],

/***/ 6044:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[7657, 6045, 6048, 6171, 6068, 6065, 6041, 6176, 6177, 6178, 6020, 6058, 6071, 6179, 6180],

/***/ 6045:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentTree.js ***!
  \************************************************************************************/
[7658, 6016, 6046, 6047, 6017],

/***/ 6046:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[7659, 6016, 6017],

/***/ 6047:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentFlags.js ***!
  \*************************************************************************************/
42,

/***/ 6048:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[7660, 6049, 6064, 6082, 6083, 6088, 6089, 6103, 6045, 6142, 6143, 6144, 6145, 6146, 6149, 6150, 6158, 6159, 6160],

/***/ 6049:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[7661, 6050, 6051, 6058, 6059, 6061, 6063, 6034],

/***/ 6050:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[7662, 6032],

/***/ 6051:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[7663, 6050, 6052, 6054, 6056, 6057, 6020],

/***/ 6052:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[7664, 6016, 6053, 6054, 6055, 6056, 6057, 6017],

/***/ 6053:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[7665, 6016, 6017],

/***/ 6054:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[7666, 6016, 6050, 6055, 6017, 6020],

/***/ 6055:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
50,

/***/ 6056:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[7667, 6016, 6017],

/***/ 6057:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
52,

/***/ 6058:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \******************************************************************************************/
53,

/***/ 6059:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[7668, 6013, 6015, 6060],

/***/ 6060:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[7669, 6058],

/***/ 6061:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[7670, 6062],

/***/ 6062:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[7671, 6013, 6015, 6021, 6020],

/***/ 6063:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[7672, 6062],

/***/ 6064:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[7673, 6050, 6052, 6051, 6058, 6045, 6065, 6062, 6079, 6080, 6081, 6034],

/***/ 6065:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[7674, 6016, 6013, 6066, 6015, 6067, 6068, 6078, 6017],

/***/ 6066:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[7675, 6016, 6013, 6015, 6017],

/***/ 6067:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactFeatureFlags.js ***!
  \********************************************************************************/
62,

/***/ 6068:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[7676, 6069, 6071, 6020],

/***/ 6069:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[7677, 6070],

/***/ 6070:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[7678, 6016, 6017],

/***/ 6071:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstrumentation.js ***!
  \***********************************************************************************/
[7679, 6072],

/***/ 6072:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDebugTool.js ***!
  \*****************************************************************************/
[7680, 6073, 6074, 6037, 6075, 6058, 6076, 6020],

/***/ 6073:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \**********************************************************************************************/
[7681, 6020],

/***/ 6074:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \********************************************************************************************/
69,

/***/ 6075:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \***********************************************************************************************/
[7682, 6037, 6020],

/***/ 6076:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performanceNow.js ***!
  \************************************************************************************/
[7683, 6077],

/***/ 6077:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performance.js ***!
  \*********************************************************************************/
[7684, 6058],

/***/ 6078:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[7685, 6016, 6017],

/***/ 6079:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
74,

/***/ 6080:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[7686, 6058],

/***/ 6081:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
76,

/***/ 6082:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[7687, 6034],

/***/ 6083:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[7688, 6050, 6051, 6045, 6084, 6034],

/***/ 6084:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[7689, 6085, 6086, 6087],

/***/ 6085:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[7690, 6062, 6079],

/***/ 6086:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
81,

/***/ 6087:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
82,

/***/ 6088:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[7691, 6046],

/***/ 6089:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[7692, 6090, 6102],

/***/ 6090:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[7693, 6091, 6097, 6101, 6045, 6071, 6094, 6093, 6095],

/***/ 6091:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMLazyTree.js ***!
  \**************************************************************************/
[7694, 6092, 6093, 6094, 6095],

/***/ 6092:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMNamespaces.js ***!
  \****************************************************************************/
87,

/***/ 6093:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[7695, 6058, 6092, 6094],

/***/ 6094:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \*************************************************************************************************/
89,

/***/ 6095:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[7696, 6058, 6096, 6093],

/***/ 6096:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
91,

/***/ 6097:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[7697, 6016, 6091, 6058, 6098, 6021, 6017],

/***/ 6098:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*******************************************************************************************/
[7698, 6058, 6099, 6100, 6017],

/***/ 6099:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \******************************************************************************************/
[7699, 6017],

/***/ 6100:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \***********************************************************************************/
[7700, 6058, 6017],

/***/ 6101:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[7701, 6032],

/***/ 6102:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[7702, 6090, 6045],

/***/ 6103:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[7703, 6016, 6013, 6104, 6106, 6091, 6092, 6046, 6114, 6050, 6052, 6053, 6116, 6119, 6047, 6045, 6121, 6123, 6124, 6125, 6071, 6126, 6138, 6021, 6096, 6017, 6080, 6034, 6133, 6141, 6020],

/***/ 6104:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[7704, 6045, 6105],

/***/ 6105:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/focusNode.js ***!
  \*******************************************************************************/
100,

/***/ 6106:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[7705, 6107, 6058, 6071, 6108, 6110, 6111, 6113, 6020],

/***/ 6107:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
102,

/***/ 6108:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***************************************************************************************/
[7706, 6109],

/***/ 6109:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelize.js ***!
  \******************************************************************************/
104,

/***/ 6110:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[7707, 6107, 6020],

/***/ 6111:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \****************************************************************************************/
[7708, 6112],

/***/ 6112:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenate.js ***!
  \*******************************************************************************/
107,

/***/ 6113:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***************************************************************************************/
108,

/***/ 6114:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[7709, 6046, 6045, 6071, 6115, 6020],

/***/ 6115:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[7710, 6096],

/***/ 6116:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[7711, 6013, 6050, 6053, 6117, 6086, 6118, 6080],

/***/ 6117:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[7712, 6052],

/***/ 6118:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getVendorPrefixedEventName.js ***!
  \*****************************************************************************************/
[7713, 6058],

/***/ 6119:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
[7714, 6120],

/***/ 6120:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DisabledInputUtils.js ***!
  \*********************************************************************************/
115,

/***/ 6121:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[7715, 6016, 6013, 6120, 6114, 6122, 6045, 6065, 6017, 6020],

/***/ 6122:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[7716, 6016, 6040, 6031, 6039, 6017, 6020],

/***/ 6123:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[7717, 6013, 6014, 6045, 6124, 6020],

/***/ 6124:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[7718, 6013, 6120, 6122, 6045, 6065, 6020],

/***/ 6125:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[7719, 6016, 6013, 6120, 6122, 6045, 6065, 6017, 6020],

/***/ 6126:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[7720, 6016, 6127, 6128, 6071, 6101, 6019, 6068, 6129, 6021, 6137, 6017],

/***/ 6127:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[7721, 6016, 6017],

/***/ 6128:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
123,

/***/ 6129:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[7722, 6068, 6130, 6025, 6134, 6023, 6020, 6037, 6037],

/***/ 6130:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[7723, 6016, 6013, 6131, 6135, 6136, 6017, 6020],

/***/ 6131:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[7724, 6016, 6013, 6127, 6019, 6018, 6055, 6128, 6071, 6132, 6031, 6068, 6038, 6028, 6017, 6133, 6134, 6020],

/***/ 6132:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNodeTypes.js ***!
  \*****************************************************************************/
[7725, 6016, 6018, 6017],

/***/ 6133:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/shallowEqual.js ***!
  \**********************************************************************************/
128,

/***/ 6134:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
129,

/***/ 6135:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
130,

/***/ 6136:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostComponent.js ***!
  \*********************************************************************************/
[7726, 6016, 6013, 6017],

/***/ 6137:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[7727, 6025, 6023, 6020, 6037, 6037],

/***/ 6138:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[7728, 6013, 6015, 6078, 6071, 6139],

/***/ 6139:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerUpdateQueue.js ***!
  \*************************************************************************************/
[7729, 6140, 6078, 6020],

/***/ 6140:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[7730, 6016, 6019, 6128, 6071, 6065, 6017, 6020],

/***/ 6141:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[7731, 6013, 6021, 6020],

/***/ 6142:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMEmptyComponent.js ***!
  \*************************************************************************************/
[7732, 6013, 6091, 6045],

/***/ 6143:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTreeTraversal.js ***!
  \************************************************************************************/
[7733, 6016, 6017],

/***/ 6144:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[7734, 6016, 6013, 6090, 6091, 6045, 6096, 6017, 6141],

/***/ 6145:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[7735, 6013, 6065, 6078, 6021],

/***/ 6146:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[7736, 6013, 6147, 6058, 6015, 6045, 6065, 6079, 6148],

/***/ 6147:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/EventListener.js ***!
  \***********************************************************************************/
[7737, 6021],

/***/ 6148:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \************************************************************************************************/
143,

/***/ 6149:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[7738, 6046, 6052, 6054, 6127, 6030, 6135, 6116, 6136, 6065],

/***/ 6150:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[7739, 6013, 6066, 6015, 6116, 6151, 6071, 6078, 6140],

/***/ 6151:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[7740, 6152, 6154, 6105, 6157],

/***/ 6152:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[7741, 6058, 6153, 6060],

/***/ 6153:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
148,

/***/ 6154:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/containsNode.js ***!
  \**********************************************************************************/
[7742, 6155],

/***/ 6155:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isTextNode.js ***!
  \********************************************************************************/
[7743, 6156],

/***/ 6156:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isNode.js ***!
  \****************************************************************************/
151,

/***/ 6157:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**************************************************************************************/
152,

/***/ 6158:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
153,

/***/ 6159:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[7744, 6050, 6051, 6058, 6045, 6151, 6062, 6157, 6081, 6034, 6133],

/***/ 6160:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[7745, 6016, 6050, 6147, 6051, 6045, 6161, 6162, 6062, 6163, 6164, 6084, 6167, 6168, 6169, 6085, 6170, 6021, 6165, 6017, 6034],

/***/ 6161:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticAnimationEvent.js ***!
  \**************************************************************************************/
[7746, 6062],

/***/ 6162:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[7747, 6062],

/***/ 6163:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[7748, 6085],

/***/ 6164:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[7749, 6085, 6165, 6166, 6087],

/***/ 6165:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
160,

/***/ 6166:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[7750, 6165],

/***/ 6167:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[7751, 6084],

/***/ 6168:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[7752, 6085, 6087],

/***/ 6169:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTransitionEvent.js ***!
  \***************************************************************************************/
[7753, 6062],

/***/ 6170:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[7754, 6084],

/***/ 6171:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[7755, 6016, 6091, 6046, 6116, 6019, 6045, 6172, 6173, 6018, 6067, 6128, 6071, 6174, 6068, 6140, 6065, 6028, 6130, 6017, 6093, 6134, 6020],

/***/ 6172:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMContainerInfo.js ***!
  \************************************************************************************/
[7756, 6141],

/***/ 6173:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
168,

/***/ 6174:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[7757, 6175],

/***/ 6175:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
170,

/***/ 6176:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[7758, 6016, 6019, 6045, 6128, 6177, 6017, 6020],

/***/ 6177:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getHostComponentFromComposite.js ***!
  \********************************************************************************************/
[7759, 6132],

/***/ 6178:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[7760, 6171],

/***/ 6179:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \******************************************************************************************/
[7761, 6046, 6053, 6037, 6020],

/***/ 6180:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \*********************************************************************************************/
[7762, 6037, 6020],

/***/ 6181:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 6011);
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 179);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 6183);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 6675);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 6678);
	
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

/***/ 6182:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
836,

/***/ 6183:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery.browser */ 6184);
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 6185);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 6188).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 6204);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 6216);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 6219);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 6224);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 6510).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 6673);
	
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

/***/ 6184:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[8160, 6182],

/***/ 6185:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 6186);

/***/ },

/***/ 6186:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery-ui-bundle */ 6187);
	
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

/***/ 6187:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[8159, 6182],

/***/ 6188:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 6189);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 6201);

/***/ },

/***/ 6189:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6190);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6195);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6199);
	
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

/***/ 6190:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	
	__webpack_require__(/*! ./gxaGradient.css */ 6191);
	
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

/***/ 6191:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 6192);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6192:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 6193:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
234,

/***/ 6194:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
235,

/***/ 6195:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/index.js ***!
  \*******************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 6196);

/***/ },

/***/ 6196:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery-ui-bundle */ 6187);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 6197);
	
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

/***/ 6197:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../css-loader!./gxaHelpTooltip.css */ 6198);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6198:
/*!**************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \**************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6199:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 6200);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6200:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6201:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6190);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6202).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6195);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6199);
	
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

/***/ 6202:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[8332, 6203],

/***/ 6203:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
[8333, 6011],

/***/ 6204:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 6205);

/***/ },

/***/ 6205:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6206);
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery-ui-bundle */ 6187);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6210).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 6212);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 6214);
	
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

/***/ 6206:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
[8163, 6207],

/***/ 6207:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[8164, 6048, 6208, 6041],

/***/ 6208:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[8165, 6016, 6172, 6145, 6018, 6071, 6174, 6068, 6209, 6138, 6065, 6028, 6130, 6017],

/***/ 6209:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
1213,

/***/ 6210:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/index.js ***!
  \******************************************************************************************************************************/
[8332, 6211],

/***/ 6211:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \******************************************************************************************************************************************/
[8333, 6011],

/***/ 6212:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 6213);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6213:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6214:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 6215);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6215:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6216:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery-ui-bundle */ 6187);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6043);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 6217);
	
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

/***/ 6217:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 6218);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6218:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6219:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 6220);

/***/ },

/***/ 6220:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6206);
	
	var $ = __webpack_require__(/*! jquery */ 6182);
	__webpack_require__(/*! jquery-ui-bundle */ 6187);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 6221);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 6222);
	
	//*------------------------------------------------------------------*
	
	function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {
	
	    $(element).attr("title", "").tooltip({
	
	        hide: false,
	
	        show: false,
	
	        tooltipClass: "gxaContrastTooltip",
	
	        position: { collision: "none" },
	
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

/***/ 6221:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 6011);
	
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

/***/ 6222:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 6223);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6223:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    position: relative;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6224:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[7770, 6225],

/***/ 6225:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[7771, 6011, 6226, 6228, 6229, 6236, 6335, 6337, 6342, 6343, 6508],

/***/ 6226:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/react-localstorage.js ***!
  \**********************************************************************************************************************/
[7772, 6011, 6227],

/***/ 6227:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/lib/warning.js ***!
  \***************************************************************************************************************/
241,

/***/ 6228:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-timer-mixin/TimerMixin.js ***!
  \*************************************************************************************************************/
242,

/***/ 6229:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-addons-css-transition-group/index.js ***!
  \************************************************************************************************************************/
[7773, 6230],

/***/ 6230:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[7774, 6013, 6012, 6231, 6233],

/***/ 6231:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[7775, 6013, 6012, 6128, 6232, 6021],

/***/ 6232:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[7776, 6137],

/***/ 6233:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[7777, 6012, 6044, 6234, 6235, 6042],

/***/ 6234:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/CSSCore.js ***!
  \*****************************************************************************/
[7778, 6017],

/***/ 6235:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[7779, 6058, 6118],

/***/ 6236:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Button.js ***!
  \***********************************************************************************************************/
[7780, 6237, 6272, 6273, 6280, 6281, 6317, 6325, 6011, 6326, 6328, 6333, 6334],

/***/ 6237:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \**************************************************************************************************************************************/
[7781, 6238],

/***/ 6238:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \***************************************************************************************************************************************************/
[7782, 6239, 6242],

/***/ 6239:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \************************************************************************************************************************************************************/
[7783, 6240, 6255],

/***/ 6240:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \**************************************************************************************************************************************************/
[7784, 6241, 6242, 6243, 6245],

/***/ 6241:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \**************************************************************************************************************************************************/
255,

/***/ 6242:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \************************************************************************************************************************************************/
256,

/***/ 6243:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \***********************************************************************************************************************************************/
[7785, 6244],

/***/ 6244:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \******************************************************************************************************************************************************/
258,

/***/ 6245:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \************************************************************************************************************************************************/
[7786, 6246, 6254, 6250],

/***/ 6246:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*****************************************************************************************************************************************************/
[7787, 6247, 6249, 6253, 6250],

/***/ 6247:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*****************************************************************************************************************************************************/
[7788, 6248],

/***/ 6248:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*****************************************************************************************************************************************************/
262,

/***/ 6249:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \**********************************************************************************************************************************************************/
[7789, 6250, 6251, 6252],

/***/ 6250:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*******************************************************************************************************************************************************/
[7790, 6251],

/***/ 6251:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \*************************************************************************************************************************************************/
265,

/***/ 6252:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \******************************************************************************************************************************************************/
[7791, 6248, 6241],

/***/ 6253:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \********************************************************************************************************************************************************/
[7792, 6248],

/***/ 6254:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \*********************************************************************************************************************************************************/
268,

/***/ 6255:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \***********************************************************************************************************************************************************/
[7793, 6256, 6259, 6271],

/***/ 6256:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*******************************************************************************************************************************************************/
[7794, 6257, 6270],

/***/ 6257:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \****************************************************************************************************************************************************************/
[7795, 6258, 6259, 6263, 6267],

/***/ 6258:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \***********************************************************************************************************************************************/
272,

/***/ 6259:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \******************************************************************************************************************************************************/
[7796, 6260, 6262],

/***/ 6260:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \***************************************************************************************************************************************************/
[7797, 6261],

/***/ 6261:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \***********************************************************************************************************************************************/
275,

/***/ 6262:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \***************************************************************************************************************************************************/
276,

/***/ 6263:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \**********************************************************************************************************************************************************/
[7798, 6259, 6264, 6266],

/***/ 6264:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*****************************************************************************************************************************************************/
[7799, 6265],

/***/ 6265:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \******************************************************************************************************************************************************/
279,

/***/ 6266:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \****************************************************************************************************************************************************/
[7800, 6265],

/***/ 6267:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \******************************************************************************************************************************************************/
[7801, 6268, 6269],

/***/ 6268:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \**************************************************************************************************************************************************/
[7802, 6241],

/***/ 6269:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \***********************************************************************************************************************************************/
283,

/***/ 6270:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \*********************************************************************************************************************************************************/
284,

/***/ 6271:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \******************************************************************************************************************************************************/
285,

/***/ 6272:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \************************************************************************************************************************************************/
286,

/***/ 6273:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \********************************************************************************************************************************/
[7803, 6274],

/***/ 6274:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \**************************************************************************************************************************************/
[7804, 6275],

/***/ 6275:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \***************************************************************************************************************************************************/
[7805, 6276, 6242],

/***/ 6276:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \************************************************************************************************************************************************************/
[7806, 6240, 6277],

/***/ 6277:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \*********************************************************************************************************************************************************/
[7807, 6256, 6278, 6271, 6279, 6260, 6251],

/***/ 6278:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*******************************************************************************************************************************************************/
292,

/***/ 6279:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*****************************************************************************************************************************************************/
[7808, 6262],

/***/ 6280:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \***************************************************************************************************************************************/
294,

/***/ 6281:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \**************************************************************************************************************************************************/
[7809, 6282],

/***/ 6282:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \*******************************************************************************************************************************/
[7810, 6283, 6303],

/***/ 6283:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \****************************************************************************************************************************************/
[7811, 6284],

/***/ 6284:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*****************************************************************************************************************************************************/
[7812, 6285, 6298, 6302],

/***/ 6285:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \**************************************************************************************************************************************************************/
[7813, 6286, 6287],

/***/ 6286:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*****************************************************************************************************************************************************/
[7814, 6265, 6262],

/***/ 6287:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*******************************************************************************************************************************************************/
[7815, 6288, 6240, 6289, 6245, 6258, 6290, 6291, 6295, 6297, 6296],

/***/ 6288:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \***************************************************************************************************************************************************/
302,

/***/ 6289:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \****************************************************************************************************************************************************/
[7816, 6245],

/***/ 6290:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*****************************************************************************************************************************************************/
304,

/***/ 6291:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*******************************************************************************************************************************************************/
[7817, 6292, 6254, 6295, 6245, 6296],

/***/ 6292:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \*********************************************************************************************************************************************************/
[7818, 6247, 6293, 6270, 6267, 6252, 6294],

/***/ 6293:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \******************************************************************************************************************************************************/
[7819, 6246, 6247, 6256, 6250],

/***/ 6294:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \************************************************************************************************************************************************/
[7820, 6241],

/***/ 6295:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \*************************************************************************************************************************************************************/
[7821, 6246, 6258, 6296],

/***/ 6296:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \***********************************************************************************************************************************************/
[7822, 6268, 6269, 6241],

/***/ 6297:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \******************************************************************************************************************************************************/
[7823, 6258, 6279, 6267],

/***/ 6298:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \***********************************************************************************************************************************************************/
[7824, 6299, 6241, 6245, 6290, 6296],

/***/ 6299:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \*************************************************************************************************************************************************************/
[7825, 6300, 6301, 6290, 6259, 6287],

/***/ 6300:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \**************************************************************************************************************************************************************/
314,

/***/ 6301:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*****************************************************************************************************************************************************/
315,

/***/ 6302:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \***************************************************************************************************************************************************/
[7826, 6296],

/***/ 6303:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \*******************************************************************************************************************************/
[7827, 6304],

/***/ 6304:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \**************************************************************************************************************************************************/
[7828, 6305, 6314, 6315, 6316, 6242],

/***/ 6305:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*****************************************************************************************************************************************************/
[7829, 6241, 6258, 6250, 6240, 6289, 6306, 6251, 6268, 6295, 6269, 6296, 6302, 6307, 6308, 6309, 6310, 6247, 6259, 6253, 6254, 6292, 6311, 6313, 6246, 6256, 6312, 6271, 6278, 6288, 6245],

/***/ 6306:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \************************************************************************************************************************************************/
[7830, 6269, 6248, 6258, 6246, 6251],

/***/ 6307:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \******************************************************************************************************************************************************/
[7831, 6241, 6242, 6288, 6302, 6246],

/***/ 6308:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \*************************************************************************************************************************************************/
[7832, 6256, 6259],

/***/ 6309:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*****************************************************************************************************************************************************/
[7833, 6256, 6278, 6271],

/***/ 6310:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \****************************************************************************************************************************************************/
[7834, 6261],

/***/ 6311:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \***********************************************************************************************************************************************************/
[7835, 6259, 6312],

/***/ 6312:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*******************************************************************************************************************************************************/
[7836, 6257, 6270],

/***/ 6313:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*******************************************************************************************************************************************************/
[7837, 6271, 6254, 6259, 6253, 6258, 6249, 6250],

/***/ 6314:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \***************************************************************************************************************************************************************/
328,

/***/ 6315:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \********************************************************************************************************************************************************************/
[7838, 6307],

/***/ 6316:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \****************************************************************************************************************************************************************/
[7839, 6307],

/***/ 6317:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \*********************************************************************************************************************************/
[7840, 6318, 6322, 6282],

/***/ 6318:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************/
[7841, 6319],

/***/ 6319:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*************************************************************************************************************************************************************/
[7842, 6320, 6242],

/***/ 6320:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \**********************************************************************************************************************************************************************/
[7843, 6240, 6321],

/***/ 6321:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*****************************************************************************************************************************************************/
[7844, 6248, 6247, 6243, 6313],

/***/ 6322:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \**************************************************************************************************************************************/
[7845, 6323],

/***/ 6323:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \***************************************************************************************************************************************************/
[7846, 6324, 6242],

/***/ 6324:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \************************************************************************************************************************************************************/
[7847, 6240, 6292],

/***/ 6325:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/classnames/index.js ***!
  \*******************************************************************************************************************/
339,

/***/ 6326:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \***********************************************************************************************************************************/
[7848, 6011, 6327],

/***/ 6327:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \********************************************************************************************************************************************************/
341,

/***/ 6328:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*************************************************************************************************************************/
[7849, 6329, 6273, 6332, 6011, 6333],

/***/ 6329:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \***************************************************************************************************************************************/
[7850, 6330],

/***/ 6330:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \****************************************************************************************************************************************************/
[7851, 6331, 6242],

/***/ 6331:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \*************************************************************************************************************************************************************/
[7852, 6240, 6255],

/***/ 6332:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/invariant/browser.js ***!
  \********************************************************************************************************************/
346,

/***/ 6333:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \**********************************************************************************************************************/
347,

/***/ 6334:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***************************************************************************************************************/
[7853, 6273, 6272, 6280, 6281, 6317, 6011, 6326],

/***/ 6335:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormGroup.js ***!
  \**************************************************************************************************************/
[7854, 6273, 6272, 6280, 6281, 6317, 6325, 6011, 6328, 6333, 6336],

/***/ 6336:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*********************************************************************************************************************************/
[7855, 6011],

/***/ 6337:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControl.js ***!
  \****************************************************************************************************************/
[7856, 6273, 6272, 6280, 6281, 6317, 6325, 6011, 6326, 6338, 6339, 6341, 6328],

/***/ 6338:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/warning/browser.js ***!
  \******************************************************************************************************************/
352,

/***/ 6339:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \************************************************************************************************************************/
[7857, 6272, 6273, 6280, 6281, 6317, 6325, 6011, 6340, 6328],

/***/ 6340:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Glyphicon.js ***!
  \**************************************************************************************************************/
[7858, 6273, 6272, 6280, 6281, 6317, 6325, 6011, 6328],

/***/ 6341:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlStatic.js ***!
  \**********************************************************************************************************************/
[7859, 6273, 6272, 6280, 6281, 6317, 6325, 6011, 6326, 6328],

/***/ 6342:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
356,

/***/ 6343:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/react-emojione.js ***!
  \******************************************************************************************************************/
[7860, 6344, 6345, 6505],

/***/ 6344:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*************************************************************************************************************************/
358,

/***/ 6345:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \******************************************************************************************************************************/
[7861, 6346, 6507],

/***/ 6346:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \****************************************************************************************************************************/
[7862, 6347, 6503, 6505],

/***/ 6347:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/react.js ***!
  \*************************************************************************************************************/
[7635, 6348],

/***/ 6348:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/React.js ***!
  \*****************************************************************************************************************/
[7863, 6349, 6493, 6497, 6384, 6502],

/***/ 6349:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOM.js ***!
  \********************************************************************************************************************/
[7864, 6350, 6351, 6416, 6390, 6373, 6363, 6395, 6399, 6491, 6436, 6492, 6370, 6354],

/***/ 6350:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************************************************/
364,

/***/ 6351:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************************************************/
[7865, 6352, 6367, 6371, 6373, 6384, 6366, 6365, 6415],

/***/ 6352:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************************************************/
[7866, 6353, 6361, 6363, 6364, 6365, 6358],

/***/ 6353:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Danger.js ***!
  \******************************************************************************************************************/
[7867, 6354, 6355, 6360, 6359, 6358],

/***/ 6354:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \***************************************************************************************************************************************/
368,

/***/ 6355:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \****************************************************************************************************************************************/
[7868, 6354, 6356, 6359, 6358],

/***/ 6356:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \***************************************************************************************************************************************/
[7869, 6357],

/***/ 6357:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/toArray.js ***!
  \**************************************************************************************************************************/
[7870, 6358],

/***/ 6358:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/invariant.js ***!
  \****************************************************************************************************************************/
372,

/***/ 6359:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \********************************************************************************************************************************/
[7871, 6354, 6358],

/***/ 6360:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyFunction.js ***!
  \********************************************************************************************************************************/
374,

/***/ 6361:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************************************************/
[7872, 6362],

/***/ 6362:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyMirror.js ***!
  \****************************************************************************************************************************/
[7873, 6358],

/***/ 6363:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPerf.js ***!
  \*********************************************************************************************************************/
377,

/***/ 6364:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************************************************/
[7874, 6354],

/***/ 6365:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setTextContent.js ***!
  \**************************************************************************************************************************/
[7875, 6354, 6366, 6364],

/***/ 6366:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************************************************/
380,

/***/ 6367:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7876, 6368, 6363, 6369, 6370],

/***/ 6368:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************************************************/
[7877, 6358],

/***/ 6369:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************************************************/
[7878, 6366],

/***/ 6370:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/warning.js ***!
  \**************************************************************************************************************************/
[7879, 6360],

/***/ 6371:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************************************************/
[7880, 6372, 6373],

/***/ 6372:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************************************************/
[7881, 6352, 6367, 6373, 6363, 6358],

/***/ 6373:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMount.js ***!
  \**********************************************************************************************************************/
[7882, 6368, 6374, 6350, 6386, 6387, 6389, 6390, 6392, 6393, 6363, 6395, 6398, 6399, 6384, 6403, 6404, 6407, 6358, 6364, 6412, 6415, 6370],

/***/ 6374:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************************************************/
[7883, 6375, 6376, 6377, 6382, 6363, 6383, 6384, 6385],

/***/ 6375:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventConstants.js ***!
  \**************************************************************************************************************************/
[7884, 6362],

/***/ 6376:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************************************************/
[7885, 6377, 6378, 6379, 6380, 6381, 6358, 6370],

/***/ 6377:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************************************************/
[7886, 6358],

/***/ 6378:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************************************************/
[7887, 6375, 6379, 6358, 6370],

/***/ 6379:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************************************************/
393,

/***/ 6380:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************************************************/
[7888, 6358],

/***/ 6381:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************************************************/
395,

/***/ 6382:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************************************************/
[7889, 6376],

/***/ 6383:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************************************************/
397,

/***/ 6384:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Object.assign.js ***!
  \*************************************************************************************************************************/
398,

/***/ 6385:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************************************************/
[7890, 6354],

/***/ 6386:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************************************************/
400,

/***/ 6387:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElement.js ***!
  \************************************************************************************************************************/
[7891, 6350, 6384, 6388],

/***/ 6388:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************************************************/
402,

/***/ 6389:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \***************************************************************************************************************************************/
403,

/***/ 6390:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceHandles.js ***!
  \********************************************************************************************************************************/
[7892, 6391, 6358],

/***/ 6391:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRootIndex.js ***!
  \**************************************************************************************************************************/
405,

/***/ 6392:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************************************************/
406,

/***/ 6393:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************************************************/
[7893, 6394],

/***/ 6394:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/adler32.js ***!
  \*******************************************************************************************************************/
408,

/***/ 6395:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************************************************/
[7894, 6396],

/***/ 6396:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRef.js ***!
  \********************************************************************************************************************/
[7895, 6397],

/***/ 6397:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************************************************/
[7896, 6358],

/***/ 6398:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************************************************/
[7897, 6350, 6387, 6392, 6399, 6384, 6358, 6370],

/***/ 6399:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************************************************/
[7898, 6400, 6401, 6363, 6395, 6402, 6384, 6358],

/***/ 6400:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************************************************/
[7899, 6401, 6384, 6358],

/***/ 6401:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/PooledClass.js ***!
  \***********************************************************************************************************************/
[7900, 6358],

/***/ 6402:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Transaction.js ***!
  \***********************************************************************************************************************/
[7901, 6358],

/***/ 6403:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyObject.js ***!
  \******************************************************************************************************************************/
417,

/***/ 6404:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/containsNode.js ***!
  \*******************************************************************************************************************************/
[7902, 6405],

/***/ 6405:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isTextNode.js ***!
  \*****************************************************************************************************************************/
[7903, 6406],

/***/ 6406:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isNode.js ***!
  \*************************************************************************************************************************/
420,

/***/ 6407:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************************************************/
[7904, 6408, 6413, 6414, 6384, 6358, 6370],

/***/ 6408:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************************************************/
[7905, 6409, 6350, 6387, 6392, 6363, 6410, 6411, 6395, 6398, 6384, 6403, 6358, 6412, 6370],

/***/ 6409:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************************************************/
[7906, 6358],

/***/ 6410:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************************************************/
[7907, 6362],

/***/ 6411:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************************************************/
425,

/***/ 6412:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************************************************/
426,

/***/ 6413:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************************************************/
[7908, 6387, 6389, 6395, 6384],

/***/ 6414:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNativeComponent.js ***!
  \********************************************************************************************************************************/
[7909, 6384, 6358],

/***/ 6415:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************************************************/
[7910, 6384, 6360, 6370],

/***/ 6416:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************************************************/
[7911, 6417, 6425, 6428, 6429, 6430, 6354, 6434, 6435, 6371, 6437, 6438, 6351, 6463, 6466, 6390, 6373, 6470, 6475, 6476, 6477, 6486, 6487],

/***/ 6417:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************************************************/
[7912, 6375, 6418, 6354, 6419, 6421, 6423, 6424],

/***/ 6418:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************************************************/
[7913, 6375, 6376, 6370, 6380, 6381],

/***/ 6419:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************************************************/
[7914, 6401, 6384, 6420],

/***/ 6420:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************************************************/
[7915, 6354],

/***/ 6421:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************************************************/
[7916, 6422],

/***/ 6422:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************************************************/
[7917, 6401, 6384, 6360, 6370],

/***/ 6423:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************************************************/
[7918, 6422],

/***/ 6424:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyOf.js ***!
  \************************************************************************************************************************/
438,

/***/ 6425:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7919, 6375, 6376, 6418, 6354, 6399, 6422, 6426, 6385, 6427, 6424],

/***/ 6426:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************************************************/
440,

/***/ 6427:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************************************************/
441,

/***/ 6428:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ClientReactRootIndex.js ***!
  \********************************************************************************************************************************/
442,

/***/ 6429:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************************************************/
[7920, 6424],

/***/ 6430:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************************************************/
[7921, 6375, 6418, 6431, 6373, 6424],

/***/ 6431:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************************************************/
[7922, 6432, 6383, 6433],

/***/ 6432:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************************************************/
[7923, 6422, 6426],

/***/ 6433:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************************************************/
447,

/***/ 6434:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************************************************/
[7924, 6368, 6354],

/***/ 6435:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserComponentMixin.js ***!
  \**************************************************************************************************************************************/
[7925, 6392, 6436, 6370],

/***/ 6436:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************************************************/
[7926, 6350, 6392, 6373, 6358, 6370],

/***/ 6437:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************************************************/
[7927, 6399, 6402, 6384, 6360],

/***/ 6438:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************************************************/
[7928, 6439, 6441, 6368, 6367, 6375, 6374, 6371, 6449, 6450, 6454, 6457, 6458, 6373, 6459, 6363, 6398, 6384, 6388, 6366, 6358, 6385, 6424, 6364, 6365, 6462, 6415, 6370],

/***/ 6439:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************************************************/
[7929, 6373, 6436, 6440],

/***/ 6440:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/focusNode.js ***!
  \****************************************************************************************************************************/
454,

/***/ 6441:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7930, 6442, 6354, 6363, 6443, 6445, 6446, 6448, 6370],

/***/ 6442:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************************************************/
456,

/***/ 6443:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \************************************************************************************************************************************/
[7931, 6444],

/***/ 6444:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelize.js ***!
  \***************************************************************************************************************************/
458,

/***/ 6445:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************************************************/
[7932, 6442],

/***/ 6446:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \*************************************************************************************************************************************/
[7933, 6447],

/***/ 6447:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenate.js ***!
  \****************************************************************************************************************************/
461,

/***/ 6448:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \************************************************************************************************************************************/
462,

/***/ 6449:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************************************************/
463,

/***/ 6450:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************************************************/
[7934, 6372, 6451, 6373, 6399, 6384, 6358],

/***/ 6451:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************************************************/
[7935, 6452, 6410, 6358, 6370],

/***/ 6452:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************************************************/
[7936, 6387, 6411, 6360, 6453],

/***/ 6453:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************************************************/
467,

/***/ 6454:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************************************************/
[7937, 6455, 6457, 6384, 6370],

/***/ 6455:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************************************************/
[7938, 6401, 6387, 6360, 6456],

/***/ 6456:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************************************************/
[7939, 6350, 6387, 6390, 6453, 6358, 6370],

/***/ 6457:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************************************************/
[7940, 6451, 6373, 6399, 6384, 6370],

/***/ 6458:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************************************************/
[7941, 6451, 6372, 6399, 6384, 6358, 6370],

/***/ 6459:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************************************************/
[7942, 6409, 6361, 6350, 6395, 6460, 6461],

/***/ 6460:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************************************************/
[7943, 6395, 6407, 6412, 6456, 6370],

/***/ 6461:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************************************************/
[7944, 6456, 6370],

/***/ 6462:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*******************************************************************************************************************************/
476,

/***/ 6463:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************************************************/
[7945, 6464, 6354, 6401, 6390, 6373, 6399, 6384, 6426, 6465],

/***/ 6464:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/EventListener.js ***!
  \********************************************************************************************************************************/
[7946, 6360],

/***/ 6465:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \*********************************************************************************************************************************************/
479,

/***/ 6466:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************************************************/
[7947, 6368, 6376, 6409, 6467, 6413, 6374, 6414, 6363, 6391, 6399],

/***/ 6467:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactClass.js ***!
  \**********************************************************************************************************************/
[7948, 6468, 6387, 6410, 6411, 6469, 6384, 6403, 6358, 6362, 6424, 6370],

/***/ 6468:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************************************************/
[7949, 6469, 6388, 6403, 6358, 6370],

/***/ 6469:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************************************************/
[7950, 6370],

/***/ 6470:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************************************************/
[7951, 6400, 6401, 6374, 6386, 6471, 6402, 6384],

/***/ 6471:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************************************************/
[7952, 6472, 6404, 6440, 6474],

/***/ 6472:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************************************************/
[7953, 6354, 6473, 6420],

/***/ 6473:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************************************************/
487,

/***/ 6474:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getActiveElement.js ***!
  \***********************************************************************************************************************************/
488,

/***/ 6475:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7954, 6375, 6418, 6354, 6471, 6422, 6474, 6427, 6424, 6462],

/***/ 6476:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ServerReactRootIndex.js ***!
  \********************************************************************************************************************************/
490,

/***/ 6477:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7955, 6375, 6464, 6418, 6373, 6478, 6422, 6479, 6480, 6431, 6483, 6484, 6432, 6485, 6360, 6481, 6358, 6424],

/***/ 6478:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************************************************/
[7956, 6422],

/***/ 6479:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************************************************/
[7957, 6432],

/***/ 6480:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************************************************/
[7958, 6432, 6481, 6482, 6433],

/***/ 6481:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************************************************/
495,

/***/ 6482:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventKey.js ***!
  \***********************************************************************************************************************/
[7959, 6481],

/***/ 6483:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************************************************/
[7960, 6431],

/***/ 6484:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************************************************/
[7961, 6432, 6433],

/***/ 6485:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************************************************/
[7962, 6431],

/***/ 6486:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************************************************/
[7963, 6368],

/***/ 6487:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerf.js ***!
  \****************************************************************************************************************************/
[7964, 6368, 6488, 6373, 6363, 6489],

/***/ 6488:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \************************************************************************************************************************************/
[7965, 6384],

/***/ 6489:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performanceNow.js ***!
  \*********************************************************************************************************************************/
[7966, 6490],

/***/ 6490:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performance.js ***!
  \******************************************************************************************************************************/
[7967, 6354],

/***/ 6491:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactVersion.js ***!
  \************************************************************************************************************************/
505,

/***/ 6492:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************************************************/
[7968, 6373],

/***/ 6493:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMServer.js ***!
  \**************************************************************************************************************************/
[7969, 6416, 6494, 6491],

/***/ 6494:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRendering.js ***!
  \********************************************************************************************************************************/
[7970, 6437, 6387, 6390, 6393, 6495, 6496, 6399, 6403, 6407, 6358],

/***/ 6495:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerBatchingStrategy.js ***!
  \***************************************************************************************************************************************/
509,

/***/ 6496:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************************************************/
[7971, 6401, 6400, 6402, 6384, 6360],

/***/ 6497:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactIsomorphic.js ***!
  \***************************************************************************************************************************/
[7972, 6455, 6468, 6467, 6498, 6387, 6499, 6452, 6491, 6384, 6501],

/***/ 6498:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************************************************/
[7973, 6387, 6499, 6500],

/***/ 6499:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************************************************/
[7974, 6387, 6410, 6411, 6350, 6388, 6453, 6358, 6370],

/***/ 6500:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/mapObject.js ***!
  \****************************************************************************************************************************/
514,

/***/ 6501:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/onlyChild.js ***!
  \*********************************************************************************************************************/
[7975, 6387, 6358],

/***/ 6502:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/deprecated.js ***!
  \**********************************************************************************************************************/
[7976, 6384, 6370],

/***/ 6503:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**************************************************************************************************************************/
[7977, 6504],

/***/ 6504:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \************************************************************************************************************************************/
518,

/***/ 6505:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*********************************************************************************************************************************/
[7978, 6506],

/***/ 6506:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/emoji-data.js ***!
  \*******************************************************************************************************************/
520,

/***/ 6507:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \******************************************************************************************************************************/
[7979, 6505],

/***/ 6508:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
[7980, 6509, 6194],

/***/ 6509:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
[7981, 6193],

/***/ 6510:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[8369, 6511, 6671],

/***/ 6511:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[8370, 6512, 6668, 6670],

/***/ 6512:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/react.js ***!
  \************************************************************************************/
[7635, 6513],

/***/ 6513:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/React.js ***!
  \****************************************************************************************/
[7863, 6514, 6658, 6662, 6549, 6667],

/***/ 6514:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOM.js ***!
  \*******************************************************************************************/
[7864, 6515, 6516, 6581, 6555, 6538, 6528, 6560, 6564, 6656, 6601, 6657, 6535, 6519],

/***/ 6515:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCurrentOwner.js ***!
  \****************************************************************************************************/
364,

/***/ 6516:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextComponent.js ***!
  \********************************************************************************************************/
[7865, 6517, 6532, 6536, 6538, 6549, 6531, 6530, 6580],

/***/ 6517:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMChildrenOperations.js ***!
  \********************************************************************************************************/
[7866, 6518, 6526, 6528, 6529, 6530, 6523],

/***/ 6518:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Danger.js ***!
  \*****************************************************************************************/
[7867, 6519, 6520, 6525, 6524, 6523],

/***/ 6519:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**************************************************************************************************************/
368,

/***/ 6520:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***************************************************************************************************************/
[7868, 6519, 6521, 6524, 6523],

/***/ 6521:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \**************************************************************************************************************/
[7869, 6522],

/***/ 6522:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/toArray.js ***!
  \*************************************************************************************************/
[7870, 6523],

/***/ 6523:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/invariant.js ***!
  \***************************************************************************************************/
372,

/***/ 6524:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*******************************************************************************************************/
[7871, 6519, 6523],

/***/ 6525:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*******************************************************************************************************/
374,

/***/ 6526:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*************************************************************************************************************/
[7872, 6527],

/***/ 6527:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyMirror.js ***!
  \***************************************************************************************************/
[7873, 6523],

/***/ 6528:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPerf.js ***!
  \********************************************************************************************/
377,

/***/ 6529:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setInnerHTML.js ***!
  \***********************************************************************************************/
[7874, 6519],

/***/ 6530:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setTextContent.js ***!
  \*************************************************************************************************/
[7875, 6519, 6531, 6529],

/***/ 6531:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/escapeTextContentForBrowser.js ***!
  \**************************************************************************************************************/
380,

/***/ 6532:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMPropertyOperations.js ***!
  \********************************************************************************************************/
[7876, 6533, 6528, 6534, 6535],

/***/ 6533:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMProperty.js ***!
  \**********************************************************************************************/
[7877, 6523],

/***/ 6534:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \****************************************************************************************************************/
[7878, 6531],

/***/ 6535:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/warning.js ***!
  \*************************************************************************************************/
[7879, 6525],

/***/ 6536:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*******************************************************************************************************************/
[7880, 6537, 6538],

/***/ 6537:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMIDOperations.js ***!
  \*******************************************************************************************************/
[7881, 6517, 6532, 6538, 6528, 6523],

/***/ 6538:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMount.js ***!
  \*********************************************************************************************/
[7882, 6533, 6539, 6515, 6551, 6552, 6554, 6555, 6557, 6558, 6528, 6560, 6563, 6564, 6549, 6568, 6569, 6572, 6523, 6529, 6577, 6580, 6535],

/***/ 6539:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***********************************************************************************************************/
[7883, 6540, 6541, 6542, 6547, 6528, 6548, 6549, 6550],

/***/ 6540:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventConstants.js ***!
  \*************************************************************************************************/
[7884, 6527],

/***/ 6541:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginHub.js ***!
  \*************************************************************************************************/
[7885, 6542, 6543, 6544, 6545, 6546, 6523, 6535],

/***/ 6542:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginRegistry.js ***!
  \******************************************************************************************************/
[7886, 6523],

/***/ 6543:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginUtils.js ***!
  \***************************************************************************************************/
[7887, 6540, 6544, 6523, 6535],

/***/ 6544:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactErrorUtils.js ***!
  \**************************************************************************************************/
393,

/***/ 6545:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/accumulateInto.js ***!
  \*************************************************************************************************/
[7888, 6523],

/***/ 6546:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/forEachAccumulated.js ***!
  \*****************************************************************************************************/
395,

/***/ 6547:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventEmitterMixin.js ***!
  \*********************************************************************************************************/
[7889, 6541],

/***/ 6548:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ViewportMetrics.js ***!
  \**************************************************************************************************/
397,

/***/ 6549:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Object.assign.js ***!
  \************************************************************************************************/
398,

/***/ 6550:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isEventSupported.js ***!
  \***************************************************************************************************/
[7890, 6519],

/***/ 6551:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*******************************************************************************************************/
400,

/***/ 6552:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElement.js ***!
  \***********************************************************************************************/
[7891, 6515, 6549, 6553],

/***/ 6553:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/canDefineProperty.js ***!
  \****************************************************************************************************/
402,

/***/ 6554:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \**************************************************************************************************************/
403,

/***/ 6555:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceHandles.js ***!
  \*******************************************************************************************************/
[7892, 6556, 6523],

/***/ 6556:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRootIndex.js ***!
  \*************************************************************************************************/
405,

/***/ 6557:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceMap.js ***!
  \***************************************************************************************************/
406,

/***/ 6558:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMarkupChecksum.js ***!
  \******************************************************************************************************/
[7893, 6559],

/***/ 6559:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/adler32.js ***!
  \******************************************************************************************/
408,

/***/ 6560:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconciler.js ***!
  \**************************************************************************************************/
[7894, 6561],

/***/ 6561:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRef.js ***!
  \*******************************************************************************************/
[7895, 6562],

/***/ 6562:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactOwner.js ***!
  \*********************************************************************************************/
[7896, 6523],

/***/ 6563:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdateQueue.js ***!
  \***************************************************************************************************/
[7897, 6515, 6552, 6557, 6564, 6549, 6523, 6535],

/***/ 6564:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdates.js ***!
  \***********************************************************************************************/
[7898, 6565, 6566, 6528, 6560, 6567, 6549, 6523],

/***/ 6565:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CallbackQueue.js ***!
  \************************************************************************************************/
[7899, 6566, 6549, 6523],

/***/ 6566:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/PooledClass.js ***!
  \**********************************************************************************************/
[7900, 6523],

/***/ 6567:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Transaction.js ***!
  \**********************************************************************************************/
[7901, 6523],

/***/ 6568:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyObject.js ***!
  \*****************************************************************************************************/
417,

/***/ 6569:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/containsNode.js ***!
  \******************************************************************************************************/
[7902, 6570],

/***/ 6570:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isTextNode.js ***!
  \****************************************************************************************************/
[7903, 6571],

/***/ 6571:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isNode.js ***!
  \************************************************************************************************/
420,

/***/ 6572:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/instantiateReactComponent.js ***!
  \************************************************************************************************************/
[7904, 6573, 6578, 6579, 6549, 6523, 6535],

/***/ 6573:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCompositeComponent.js ***!
  \**********************************************************************************************************/
[7905, 6574, 6515, 6552, 6557, 6528, 6575, 6576, 6560, 6563, 6549, 6568, 6523, 6577, 6535],

/***/ 6574:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentEnvironment.js ***!
  \************************************************************************************************************/
[7906, 6523],

/***/ 6575:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocations.js ***!
  \*********************************************************************************************************/
[7907, 6527],

/***/ 6576:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*************************************************************************************************************/
425,

/***/ 6577:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/shouldUpdateReactComponent.js ***!
  \*************************************************************************************************************/
426,

/***/ 6578:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponent.js ***!
  \******************************************************************************************************/
[7908, 6552, 6554, 6560, 6549],

/***/ 6579:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNativeComponent.js ***!
  \*******************************************************************************************************/
[7909, 6549, 6523],

/***/ 6580:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/validateDOMNesting.js ***!
  \*****************************************************************************************************/
[7910, 6549, 6525, 6535],

/***/ 6581:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultInjection.js ***!
  \********************************************************************************************************/
[7911, 6582, 6590, 6593, 6594, 6595, 6519, 6599, 6600, 6536, 6602, 6603, 6516, 6628, 6631, 6555, 6538, 6635, 6640, 6641, 6642, 6651, 6652],

/***/ 6582:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/BeforeInputEventPlugin.js ***!
  \*********************************************************************************************************/
[7912, 6540, 6583, 6519, 6584, 6586, 6588, 6589],

/***/ 6583:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPropagators.js ***!
  \***************************************************************************************************/
[7913, 6540, 6541, 6535, 6545, 6546],

/***/ 6584:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/FallbackCompositionState.js ***!
  \***********************************************************************************************************/
[7914, 6566, 6549, 6585],

/***/ 6585:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getTextContentAccessor.js ***!
  \*********************************************************************************************************/
[7915, 6519],

/***/ 6586:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticCompositionEvent.js ***!
  \************************************************************************************************************/
[7916, 6587],

/***/ 6587:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticEvent.js ***!
  \*************************************************************************************************/
[7917, 6566, 6549, 6525, 6535],

/***/ 6588:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticInputEvent.js ***!
  \******************************************************************************************************/
[7918, 6587],

/***/ 6589:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyOf.js ***!
  \***********************************************************************************************/
438,

/***/ 6590:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ChangeEventPlugin.js ***!
  \****************************************************************************************************/
[7919, 6540, 6541, 6583, 6519, 6564, 6587, 6591, 6550, 6592, 6589],

/***/ 6591:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventTarget.js ***!
  \*************************************************************************************************/
440,

/***/ 6592:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isTextInputElement.js ***!
  \*****************************************************************************************************/
441,

/***/ 6593:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ClientReactRootIndex.js ***!
  \*******************************************************************************************************/
442,

/***/ 6594:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DefaultEventPluginOrder.js ***!
  \**********************************************************************************************************/
[7920, 6589],

/***/ 6595:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EnterLeaveEventPlugin.js ***!
  \********************************************************************************************************/
[7921, 6540, 6583, 6596, 6538, 6589],

/***/ 6596:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticMouseEvent.js ***!
  \******************************************************************************************************/
[7922, 6597, 6548, 6598],

/***/ 6597:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticUIEvent.js ***!
  \***************************************************************************************************/
[7923, 6587, 6591],

/***/ 6598:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventModifierState.js ***!
  \********************************************************************************************************/
447,

/***/ 6599:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \********************************************************************************************************/
[7924, 6533, 6519],

/***/ 6600:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*************************************************************************************************************/
[7925, 6557, 6601, 6535],

/***/ 6601:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/findDOMNode.js ***!
  \**********************************************************************************************/
[7926, 6515, 6557, 6538, 6523, 6535],

/***/ 6602:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \***************************************************************************************************************/
[7927, 6564, 6567, 6549, 6525],

/***/ 6603:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMComponent.js ***!
  \****************************************************************************************************/
[7928, 6604, 6606, 6533, 6532, 6540, 6539, 6536, 6614, 6615, 6619, 6622, 6623, 6538, 6624, 6528, 6563, 6549, 6553, 6531, 6523, 6550, 6589, 6529, 6530, 6627, 6580, 6535],

/***/ 6604:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/AutoFocusUtils.js ***!
  \*************************************************************************************************/
[7929, 6538, 6601, 6605],

/***/ 6605:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/focusNode.js ***!
  \***************************************************************************************************/
454,

/***/ 6606:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSPropertyOperations.js ***!
  \********************************************************************************************************/
[7930, 6607, 6519, 6528, 6608, 6610, 6611, 6613, 6535],

/***/ 6607:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSProperty.js ***!
  \**********************************************************************************************/
456,

/***/ 6608:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***********************************************************************************************************/
[7931, 6609],

/***/ 6609:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelize.js ***!
  \**************************************************************************************************/
458,

/***/ 6610:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/dangerousStyleValue.js ***!
  \******************************************************************************************************/
[7932, 6607],

/***/ 6611:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \************************************************************************************************************/
[7933, 6612],

/***/ 6612:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenate.js ***!
  \***************************************************************************************************/
461,

/***/ 6613:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***********************************************************************************************************/
462,

/***/ 6614:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMButton.js ***!
  \*************************************************************************************************/
463,

/***/ 6615:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMInput.js ***!
  \************************************************************************************************/
[7934, 6537, 6616, 6538, 6564, 6549, 6523],

/***/ 6616:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/LinkedValueUtils.js ***!
  \***************************************************************************************************/
[7935, 6617, 6575, 6523, 6535],

/***/ 6617:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypes.js ***!
  \*************************************************************************************************/
[7936, 6552, 6576, 6525, 6618],

/***/ 6618:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getIteratorFn.js ***!
  \************************************************************************************************/
467,

/***/ 6619:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMOption.js ***!
  \*************************************************************************************************/
[7937, 6620, 6622, 6549, 6535],

/***/ 6620:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildren.js ***!
  \************************************************************************************************/
[7938, 6566, 6552, 6525, 6621],

/***/ 6621:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/traverseAllChildren.js ***!
  \******************************************************************************************************/
[7939, 6515, 6552, 6555, 6618, 6523, 6535],

/***/ 6622:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelect.js ***!
  \*************************************************************************************************/
[7940, 6616, 6538, 6564, 6549, 6535],

/***/ 6623:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextarea.js ***!
  \***************************************************************************************************/
[7941, 6616, 6537, 6564, 6549, 6523, 6535],

/***/ 6624:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChild.js ***!
  \**************************************************************************************************/
[7942, 6574, 6526, 6515, 6560, 6625, 6626],

/***/ 6625:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildReconciler.js ***!
  \*******************************************************************************************************/
[7943, 6560, 6572, 6577, 6621, 6535],

/***/ 6626:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/flattenChildren.js ***!
  \**************************************************************************************************/
[7944, 6621, 6535],

/***/ 6627:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/shallowEqual.js ***!
  \******************************************************************************************************/
476,

/***/ 6628:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventListener.js ***!
  \*****************************************************************************************************/
[7945, 6629, 6519, 6566, 6555, 6538, 6564, 6549, 6591, 6630],

/***/ 6629:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/EventListener.js ***!
  \*******************************************************************************************************/
[7946, 6525],

/***/ 6630:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \********************************************************************************************************************/
479,

/***/ 6631:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInjection.js ***!
  \*************************************************************************************************/
[7947, 6533, 6541, 6574, 6632, 6578, 6539, 6579, 6528, 6556, 6564],

/***/ 6632:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactClass.js ***!
  \*********************************************************************************************/
[7948, 6633, 6552, 6575, 6576, 6634, 6549, 6568, 6523, 6527, 6589, 6535],

/***/ 6633:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponent.js ***!
  \*************************************************************************************************/
[7949, 6634, 6553, 6568, 6523, 6535],

/***/ 6634:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*******************************************************************************************************/
[7950, 6535],

/***/ 6635:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconcileTransaction.js ***!
  \************************************************************************************************************/
[7951, 6565, 6566, 6539, 6551, 6636, 6567, 6549],

/***/ 6636:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInputSelection.js ***!
  \******************************************************************************************************/
[7952, 6637, 6569, 6605, 6639],

/***/ 6637:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelection.js ***!
  \****************************************************************************************************/
[7953, 6519, 6638, 6585],

/***/ 6638:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getNodeForCharacterOffset.js ***!
  \************************************************************************************************************/
487,

/***/ 6639:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**********************************************************************************************************/
488,

/***/ 6640:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SelectEventPlugin.js ***!
  \****************************************************************************************************/
[7954, 6540, 6583, 6519, 6636, 6587, 6639, 6592, 6589, 6627],

/***/ 6641:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ServerReactRootIndex.js ***!
  \*******************************************************************************************************/
490,

/***/ 6642:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SimpleEventPlugin.js ***!
  \****************************************************************************************************/
[7955, 6540, 6629, 6583, 6538, 6643, 6587, 6644, 6645, 6596, 6648, 6649, 6597, 6650, 6525, 6646, 6523, 6589],

/***/ 6643:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticClipboardEvent.js ***!
  \**********************************************************************************************************/
[7956, 6587],

/***/ 6644:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticFocusEvent.js ***!
  \******************************************************************************************************/
[7957, 6597],

/***/ 6645:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*********************************************************************************************************/
[7958, 6597, 6646, 6647, 6598],

/***/ 6646:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventCharCode.js ***!
  \***************************************************************************************************/
495,

/***/ 6647:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventKey.js ***!
  \**********************************************************************************************/
[7959, 6646],

/***/ 6648:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticDragEvent.js ***!
  \*****************************************************************************************************/
[7960, 6596],

/***/ 6649:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticTouchEvent.js ***!
  \******************************************************************************************************/
[7961, 6597, 6598],

/***/ 6650:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticWheelEvent.js ***!
  \******************************************************************************************************/
[7962, 6596],

/***/ 6651:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*******************************************************************************************************/
[7963, 6533],

/***/ 6652:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerf.js ***!
  \***************************************************************************************************/
[7964, 6533, 6653, 6538, 6528, 6654],

/***/ 6653:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***********************************************************************************************************/
[7965, 6549],

/***/ 6654:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performanceNow.js ***!
  \********************************************************************************************************/
[7966, 6655],

/***/ 6655:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performance.js ***!
  \*****************************************************************************************************/
[7967, 6519],

/***/ 6656:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactVersion.js ***!
  \***********************************************************************************************/
505,

/***/ 6657:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*************************************************************************************************************/
[7968, 6538],

/***/ 6658:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMServer.js ***!
  \*************************************************************************************************/
[7969, 6581, 6659, 6656],

/***/ 6659:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRendering.js ***!
  \*******************************************************************************************************/
[7970, 6602, 6552, 6555, 6558, 6660, 6661, 6564, 6568, 6572, 6523],

/***/ 6660:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerBatchingStrategy.js ***!
  \**************************************************************************************************************/
509,

/***/ 6661:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRenderingTransaction.js ***!
  \******************************************************************************************************************/
[7971, 6566, 6565, 6567, 6549, 6525],

/***/ 6662:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactIsomorphic.js ***!
  \**************************************************************************************************/
[7972, 6620, 6633, 6632, 6663, 6552, 6664, 6617, 6656, 6549, 6666],

/***/ 6663:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFactories.js ***!
  \****************************************************************************************************/
[7973, 6552, 6664, 6665],

/***/ 6664:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElementValidator.js ***!
  \********************************************************************************************************/
[7974, 6552, 6575, 6576, 6515, 6553, 6618, 6523, 6535],

/***/ 6665:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/mapObject.js ***!
  \***************************************************************************************************/
514,

/***/ 6666:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/onlyChild.js ***!
  \********************************************************************************************/
[7975, 6552, 6523],

/***/ 6667:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/deprecated.js ***!
  \*********************************************************************************************/
[7976, 6549, 6535],

/***/ 6668:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
[8371, 6669, 6194],

/***/ 6669:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
[8372, 6193],

/***/ 6670:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
1669,

/***/ 6671:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[8373, 6512, 6672, 6511],

/***/ 6672:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react-dom/index.js ***!
  \****************************************************************************************/
[7656, 6514],

/***/ 6673:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 6674);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6674:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6675:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6011);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 6676);
	
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

/***/ 6676:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 6677);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6194)(content, {});
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

/***/ 6677:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6193)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 2px 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 8px;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4 {\n    font-weight: bold;\n    font-size: 133%;\n    padding: 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6678:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 179);
	var QueryString = __webpack_require__(/*! querystring */ 2698);
	
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