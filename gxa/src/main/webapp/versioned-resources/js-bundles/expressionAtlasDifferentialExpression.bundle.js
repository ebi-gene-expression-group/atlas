var expressionAtlasDifferentialExpression =
webpackJsonp_name_([6],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */6026);


/***/ },

/***/ 2713:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/index.js ***!
  \****************************************************************/
[7782, 2714, 2715],

/***/ 2714:
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

/***/ 2715:
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

/***/ 6026:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 6027);

/***/ },

/***/ 6027:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 6198);
	
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

/***/ 6028:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[7652, 6029],

/***/ 6029:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[7653, 6030, 6031, 6043, 6046, 6047, 6052, 6035, 6057, 6058, 6059, 6037, 6053],

/***/ 6030:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/object-assign/index.js ***!
  \********************************************************************************/
5,

/***/ 6031:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[7654, 6032, 6035, 6038, 6040],

/***/ 6032:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[7655, 6033, 6034],

/***/ 6033:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/reactProdInvariant.js ***!
  \*********************************************************************************/
8,

/***/ 6034:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/invariant.js ***!
  \*******************************************************************************/
9,

/***/ 6035:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[7656, 6030, 6036, 6037, 6039],

/***/ 6036:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
11,

/***/ 6037:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/warning.js ***!
  \*****************************************************************************/
[7657, 6038],

/***/ 6038:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyFunction.js ***!
  \***********************************************************************************/
13,

/***/ 6039:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
14,

/***/ 6040:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[7658, 6033, 6036, 6035, 6041, 6034, 6042, 6037],

/***/ 6041:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
16,

/***/ 6042:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/KeyEscapeUtils.js ***!
  \*****************************************************************************/
17,

/***/ 6043:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[7659, 6033, 6044, 6039, 6045, 6034, 6037],

/***/ 6044:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[7660, 6037],

/***/ 6045:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyObject.js ***!
  \*********************************************************************************/
20,

/***/ 6046:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPureComponent.js ***!
  \*********************************************************************************/
[7661, 6030, 6043, 6044, 6045],

/***/ 6047:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[7662, 6033, 6030, 6043, 6035, 6048, 6050, 6044, 6045, 6034, 6049, 6051, 6037],

/***/ 6048:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[7663, 6049],

/***/ 6049:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyMirror.js ***!
  \*******************************************************************************/
[7664, 6034],

/***/ 6050:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
25,

/***/ 6051:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyOf.js ***!
  \***************************************************************************/
26,

/***/ 6052:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[7665, 6035, 6053],

/***/ 6053:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[7666, 6036, 6054, 6035, 6048, 6055, 6039, 6041, 6037],

/***/ 6054:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentTreeHook.js ***!
  \*************************************************************************************/
[7667, 6033, 6036, 6034, 6037],

/***/ 6055:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/checkReactTypeSpec.js ***!
  \*********************************************************************************/
[7668, 6033, 6050, 6056, 6034, 6037, 6054, 6054],

/***/ 6056:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypesSecret.js ***!
  \***********************************************************************************/
32,

/***/ 6057:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[7669, 6035, 6050, 6056, 6038, 6041, 6037],

/***/ 6058:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
34,

/***/ 6059:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[7670, 6033, 6035, 6034],

/***/ 6060:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[7673, 6061],

/***/ 6061:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[7674, 6062, 6065, 6188, 6085, 6082, 6058, 6193, 6194, 6195, 6037, 6075, 6088, 6196, 6197],

/***/ 6062:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentTree.js ***!
  \************************************************************************************/
[7675, 6033, 6063, 6064, 6034],

/***/ 6063:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[7676, 6033, 6034],

/***/ 6064:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentFlags.js ***!
  \*************************************************************************************/
42,

/***/ 6065:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[7677, 6066, 6081, 6099, 6100, 6105, 6106, 6120, 6062, 6159, 6160, 6161, 6162, 6163, 6166, 6167, 6175, 6176, 6177],

/***/ 6066:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[7678, 6067, 6068, 6075, 6076, 6078, 6080, 6051],

/***/ 6067:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[7679, 6049],

/***/ 6068:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[7680, 6067, 6069, 6071, 6073, 6074, 6037],

/***/ 6069:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[7681, 6033, 6070, 6071, 6072, 6073, 6074, 6034],

/***/ 6070:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[7682, 6033, 6034],

/***/ 6071:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[7683, 6033, 6067, 6072, 6034, 6037],

/***/ 6072:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
50,

/***/ 6073:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[7684, 6033, 6034],

/***/ 6074:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
52,

/***/ 6075:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \******************************************************************************************/
53,

/***/ 6076:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[7685, 6030, 6032, 6077],

/***/ 6077:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[7686, 6075],

/***/ 6078:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[7687, 6079],

/***/ 6079:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[7688, 6030, 6032, 6038, 6037],

/***/ 6080:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[7689, 6079],

/***/ 6081:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[7690, 6067, 6069, 6068, 6075, 6062, 6082, 6079, 6096, 6097, 6098, 6051],

/***/ 6082:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[7691, 6033, 6030, 6083, 6032, 6084, 6085, 6095, 6034],

/***/ 6083:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[7692, 6033, 6030, 6032, 6034],

/***/ 6084:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactFeatureFlags.js ***!
  \********************************************************************************/
62,

/***/ 6085:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[7693, 6086, 6088, 6037],

/***/ 6086:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[7694, 6087],

/***/ 6087:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[7695, 6033, 6034],

/***/ 6088:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstrumentation.js ***!
  \***********************************************************************************/
[7696, 6089],

/***/ 6089:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDebugTool.js ***!
  \*****************************************************************************/
[7697, 6090, 6091, 6054, 6092, 6075, 6093, 6037],

/***/ 6090:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \**********************************************************************************************/
[7698, 6037],

/***/ 6091:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \********************************************************************************************/
69,

/***/ 6092:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \***********************************************************************************************/
[7699, 6054, 6037],

/***/ 6093:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performanceNow.js ***!
  \************************************************************************************/
[7700, 6094],

/***/ 6094:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performance.js ***!
  \*********************************************************************************/
[7701, 6075],

/***/ 6095:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[7702, 6033, 6034],

/***/ 6096:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
74,

/***/ 6097:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[7703, 6075],

/***/ 6098:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
76,

/***/ 6099:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[7704, 6051],

/***/ 6100:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[7705, 6067, 6068, 6062, 6101, 6051],

/***/ 6101:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[7706, 6102, 6103, 6104],

/***/ 6102:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[7707, 6079, 6096],

/***/ 6103:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
81,

/***/ 6104:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
82,

/***/ 6105:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[7708, 6063],

/***/ 6106:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[7709, 6107, 6119],

/***/ 6107:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[7710, 6108, 6114, 6118, 6062, 6088, 6111, 6110, 6112],

/***/ 6108:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMLazyTree.js ***!
  \**************************************************************************/
[7711, 6109, 6110, 6111, 6112],

/***/ 6109:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMNamespaces.js ***!
  \****************************************************************************/
87,

/***/ 6110:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[7712, 6075, 6109, 6111],

/***/ 6111:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \*************************************************************************************************/
89,

/***/ 6112:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[7713, 6075, 6113, 6110],

/***/ 6113:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
91,

/***/ 6114:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[7714, 6033, 6108, 6075, 6115, 6038, 6034],

/***/ 6115:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*******************************************************************************************/
[7715, 6075, 6116, 6117, 6034],

/***/ 6116:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \******************************************************************************************/
[7716, 6034],

/***/ 6117:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \***********************************************************************************/
[7717, 6075, 6034],

/***/ 6118:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[7718, 6049],

/***/ 6119:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[7719, 6107, 6062],

/***/ 6120:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[7720, 6033, 6030, 6121, 6123, 6108, 6109, 6063, 6131, 6067, 6069, 6070, 6133, 6136, 6064, 6062, 6138, 6140, 6141, 6142, 6088, 6143, 6155, 6038, 6113, 6034, 6097, 6051, 6150, 6158, 6037],

/***/ 6121:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[7721, 6062, 6122],

/***/ 6122:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/focusNode.js ***!
  \*******************************************************************************/
100,

/***/ 6123:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[7722, 6124, 6075, 6088, 6125, 6127, 6128, 6130, 6037],

/***/ 6124:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
102,

/***/ 6125:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***************************************************************************************/
[7723, 6126],

/***/ 6126:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelize.js ***!
  \******************************************************************************/
104,

/***/ 6127:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[7724, 6124, 6037],

/***/ 6128:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \****************************************************************************************/
[7725, 6129],

/***/ 6129:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenate.js ***!
  \*******************************************************************************/
107,

/***/ 6130:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***************************************************************************************/
108,

/***/ 6131:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[7726, 6063, 6062, 6088, 6132, 6037],

/***/ 6132:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[7727, 6113],

/***/ 6133:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[7728, 6030, 6067, 6070, 6134, 6103, 6135, 6097],

/***/ 6134:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[7729, 6069],

/***/ 6135:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getVendorPrefixedEventName.js ***!
  \*****************************************************************************************/
[7730, 6075],

/***/ 6136:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
[7731, 6137],

/***/ 6137:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DisabledInputUtils.js ***!
  \*********************************************************************************/
115,

/***/ 6138:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[7732, 6033, 6030, 6137, 6131, 6139, 6062, 6082, 6034, 6037],

/***/ 6139:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[7733, 6033, 6057, 6048, 6056, 6034, 6037],

/***/ 6140:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[7734, 6030, 6031, 6062, 6141, 6037],

/***/ 6141:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[7735, 6030, 6137, 6139, 6062, 6082, 6037],

/***/ 6142:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[7736, 6033, 6030, 6137, 6139, 6062, 6082, 6034, 6037],

/***/ 6143:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[7737, 6033, 6144, 6145, 6088, 6118, 6036, 6085, 6146, 6038, 6154, 6034],

/***/ 6144:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[7738, 6033, 6034],

/***/ 6145:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
123,

/***/ 6146:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[7739, 6085, 6147, 6042, 6151, 6040, 6037, 6054, 6054],

/***/ 6147:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[7740, 6033, 6030, 6148, 6152, 6153, 6034, 6037],

/***/ 6148:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[7741, 6033, 6030, 6144, 6036, 6035, 6072, 6145, 6088, 6149, 6048, 6085, 6055, 6045, 6034, 6150, 6151, 6037],

/***/ 6149:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNodeTypes.js ***!
  \*****************************************************************************/
[7742, 6033, 6035, 6034],

/***/ 6150:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/shallowEqual.js ***!
  \**********************************************************************************/
128,

/***/ 6151:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
129,

/***/ 6152:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
130,

/***/ 6153:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostComponent.js ***!
  \*********************************************************************************/
[7743, 6033, 6030, 6034],

/***/ 6154:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[7744, 6042, 6040, 6037, 6054, 6054],

/***/ 6155:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[7745, 6030, 6032, 6095, 6088, 6156],

/***/ 6156:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerUpdateQueue.js ***!
  \*************************************************************************************/
[7746, 6157, 6095, 6037],

/***/ 6157:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[7747, 6033, 6036, 6145, 6088, 6082, 6034, 6037],

/***/ 6158:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[7748, 6030, 6038, 6037],

/***/ 6159:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMEmptyComponent.js ***!
  \*************************************************************************************/
[7749, 6030, 6108, 6062],

/***/ 6160:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTreeTraversal.js ***!
  \************************************************************************************/
[7750, 6033, 6034],

/***/ 6161:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[7751, 6033, 6030, 6107, 6108, 6062, 6113, 6034, 6158],

/***/ 6162:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[7752, 6030, 6082, 6095, 6038],

/***/ 6163:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[7753, 6030, 6164, 6075, 6032, 6062, 6082, 6096, 6165],

/***/ 6164:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/EventListener.js ***!
  \***********************************************************************************/
[7754, 6038],

/***/ 6165:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \************************************************************************************************/
143,

/***/ 6166:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[7755, 6063, 6069, 6071, 6144, 6047, 6152, 6133, 6153, 6082],

/***/ 6167:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[7756, 6030, 6083, 6032, 6133, 6168, 6088, 6095, 6157],

/***/ 6168:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[7757, 6169, 6171, 6122, 6174],

/***/ 6169:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[7758, 6075, 6170, 6077],

/***/ 6170:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
148,

/***/ 6171:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/containsNode.js ***!
  \**********************************************************************************/
[7759, 6172],

/***/ 6172:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isTextNode.js ***!
  \********************************************************************************/
[7760, 6173],

/***/ 6173:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isNode.js ***!
  \****************************************************************************/
151,

/***/ 6174:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**************************************************************************************/
152,

/***/ 6175:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
153,

/***/ 6176:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[7761, 6067, 6068, 6075, 6062, 6168, 6079, 6174, 6098, 6051, 6150],

/***/ 6177:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[7762, 6033, 6067, 6164, 6068, 6062, 6178, 6179, 6079, 6180, 6181, 6101, 6184, 6185, 6186, 6102, 6187, 6038, 6182, 6034, 6051],

/***/ 6178:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticAnimationEvent.js ***!
  \**************************************************************************************/
[7763, 6079],

/***/ 6179:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[7764, 6079],

/***/ 6180:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[7765, 6102],

/***/ 6181:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[7766, 6102, 6182, 6183, 6104],

/***/ 6182:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
160,

/***/ 6183:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[7767, 6182],

/***/ 6184:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[7768, 6101],

/***/ 6185:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[7769, 6102, 6104],

/***/ 6186:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTransitionEvent.js ***!
  \***************************************************************************************/
[7770, 6079],

/***/ 6187:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[7771, 6101],

/***/ 6188:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[7772, 6033, 6108, 6063, 6133, 6036, 6062, 6189, 6190, 6035, 6084, 6145, 6088, 6191, 6085, 6157, 6082, 6045, 6147, 6034, 6110, 6151, 6037],

/***/ 6189:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMContainerInfo.js ***!
  \************************************************************************************/
[7773, 6158],

/***/ 6190:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
168,

/***/ 6191:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[7774, 6192],

/***/ 6192:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
170,

/***/ 6193:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[7775, 6033, 6036, 6062, 6145, 6194, 6034, 6037],

/***/ 6194:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getHostComponentFromComposite.js ***!
  \********************************************************************************************/
[7776, 6149],

/***/ 6195:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[7777, 6188],

/***/ 6196:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \******************************************************************************************/
[7778, 6063, 6070, 6054, 6037],

/***/ 6197:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \*********************************************************************************************/
[7779, 6054, 6037],

/***/ 6198:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 6028);
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 179);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 6200);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 6692);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 6695);
	
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

/***/ 6199:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
836,

/***/ 6200:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery.browser */ 6201);
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 6202);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 6205).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 6221);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 6233);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 6236);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 6241);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 6527).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 6690);
	
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

/***/ 6201:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[8189, 6199],

/***/ 6202:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 6203);

/***/ },

/***/ 6203:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery-ui-bundle */ 6204);
	
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

/***/ 6204:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[8188, 6199],

/***/ 6205:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 6206);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 6218);

/***/ },

/***/ 6206:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6207);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6212);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6216);
	
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

/***/ 6207:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	
	__webpack_require__(/*! ./gxaGradient.css */ 6208);
	
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

/***/ 6208:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 6209);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6209:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 6210:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
234,

/***/ 6211:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
235,

/***/ 6212:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/index.js ***!
  \*******************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 6213);

/***/ },

/***/ 6213:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery-ui-bundle */ 6204);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 6214);
	
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

/***/ 6214:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../css-loader!./gxaHelpTooltip.css */ 6215);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6215:
/*!**************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \**************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6216:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 6217);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6217:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6218:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6207);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6219).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6212);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6216);
	
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

/***/ 6219:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[8361, 6220],

/***/ 6220:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
[8362, 6028],

/***/ 6221:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 6222);

/***/ },

/***/ 6222:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6223);
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery-ui-bundle */ 6204);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6227).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 6229);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 6231);
	
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

/***/ 6223:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
[8192, 6224],

/***/ 6224:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[8193, 6065, 6225, 6058],

/***/ 6225:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[8194, 6033, 6189, 6162, 6035, 6088, 6191, 6085, 6226, 6155, 6082, 6045, 6147, 6034],

/***/ 6226:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
1228,

/***/ 6227:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/index.js ***!
  \******************************************************************************************************************************/
[8361, 6228],

/***/ 6228:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \******************************************************************************************************************************************/
[8362, 6028],

/***/ 6229:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 6230);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6230:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6231:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 6232);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6232:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6233:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery-ui-bundle */ 6204);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6060);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 6234);
	
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

/***/ 6234:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 6235);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6235:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6236:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 6237);

/***/ },

/***/ 6237:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6223);
	
	var $ = __webpack_require__(/*! jquery */ 6199);
	__webpack_require__(/*! jquery-ui-bundle */ 6204);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 6238);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 6239);
	
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

/***/ 6238:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 6028);
	
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

/***/ 6239:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 6240);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6240:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    position: relative;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6241:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[7783, 6242],

/***/ 6242:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[7784, 6028, 6243, 6245, 6246, 6253, 6352, 6354, 6359, 6360, 6525],

/***/ 6243:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/react-localstorage.js ***!
  \**********************************************************************************************************************/
[7785, 6028, 6244],

/***/ 6244:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/lib/warning.js ***!
  \***************************************************************************************************************/
241,

/***/ 6245:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-timer-mixin/TimerMixin.js ***!
  \*************************************************************************************************************/
242,

/***/ 6246:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-addons-css-transition-group/index.js ***!
  \************************************************************************************************************************/
[7786, 6247],

/***/ 6247:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[7787, 6030, 6029, 6248, 6250],

/***/ 6248:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[7788, 6030, 6029, 6145, 6249, 6038],

/***/ 6249:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[7789, 6154],

/***/ 6250:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[7790, 6029, 6061, 6251, 6252, 6059],

/***/ 6251:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/CSSCore.js ***!
  \*****************************************************************************/
[7791, 6034],

/***/ 6252:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[7792, 6075, 6135],

/***/ 6253:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Button.js ***!
  \***********************************************************************************************************/
[7793, 6254, 6289, 6290, 6297, 6298, 6334, 6342, 6028, 6343, 6345, 6350, 6351],

/***/ 6254:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \**************************************************************************************************************************************/
[7794, 6255],

/***/ 6255:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \***************************************************************************************************************************************************/
[7795, 6256, 6259],

/***/ 6256:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \************************************************************************************************************************************************************/
[7796, 6257, 6272],

/***/ 6257:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \**************************************************************************************************************************************************/
[7797, 6258, 6259, 6260, 6262],

/***/ 6258:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \**************************************************************************************************************************************************/
255,

/***/ 6259:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \************************************************************************************************************************************************/
256,

/***/ 6260:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \***********************************************************************************************************************************************/
[7798, 6261],

/***/ 6261:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \******************************************************************************************************************************************************/
258,

/***/ 6262:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \************************************************************************************************************************************************/
[7799, 6263, 6271, 6267],

/***/ 6263:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*****************************************************************************************************************************************************/
[7800, 6264, 6266, 6270, 6267],

/***/ 6264:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*****************************************************************************************************************************************************/
[7801, 6265],

/***/ 6265:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*****************************************************************************************************************************************************/
262,

/***/ 6266:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \**********************************************************************************************************************************************************/
[7802, 6267, 6268, 6269],

/***/ 6267:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*******************************************************************************************************************************************************/
[7803, 6268],

/***/ 6268:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \*************************************************************************************************************************************************/
265,

/***/ 6269:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \******************************************************************************************************************************************************/
[7804, 6265, 6258],

/***/ 6270:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \********************************************************************************************************************************************************/
[7805, 6265],

/***/ 6271:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \*********************************************************************************************************************************************************/
268,

/***/ 6272:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \***********************************************************************************************************************************************************/
[7806, 6273, 6276, 6288],

/***/ 6273:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*******************************************************************************************************************************************************/
[7807, 6274, 6287],

/***/ 6274:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \****************************************************************************************************************************************************************/
[7808, 6275, 6276, 6280, 6284],

/***/ 6275:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \***********************************************************************************************************************************************/
272,

/***/ 6276:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \******************************************************************************************************************************************************/
[7809, 6277, 6279],

/***/ 6277:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \***************************************************************************************************************************************************/
[7810, 6278],

/***/ 6278:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \***********************************************************************************************************************************************/
275,

/***/ 6279:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \***************************************************************************************************************************************************/
276,

/***/ 6280:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \**********************************************************************************************************************************************************/
[7811, 6276, 6281, 6283],

/***/ 6281:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*****************************************************************************************************************************************************/
[7812, 6282],

/***/ 6282:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \******************************************************************************************************************************************************/
279,

/***/ 6283:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \****************************************************************************************************************************************************/
[7813, 6282],

/***/ 6284:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \******************************************************************************************************************************************************/
[7814, 6285, 6286],

/***/ 6285:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \**************************************************************************************************************************************************/
[7815, 6258],

/***/ 6286:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \***********************************************************************************************************************************************/
283,

/***/ 6287:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \*********************************************************************************************************************************************************/
284,

/***/ 6288:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \******************************************************************************************************************************************************/
285,

/***/ 6289:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \************************************************************************************************************************************************/
286,

/***/ 6290:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \********************************************************************************************************************************/
[7816, 6291],

/***/ 6291:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \**************************************************************************************************************************************/
[7817, 6292],

/***/ 6292:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \***************************************************************************************************************************************************/
[7818, 6293, 6259],

/***/ 6293:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \************************************************************************************************************************************************************/
[7819, 6257, 6294],

/***/ 6294:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \*********************************************************************************************************************************************************/
[7820, 6273, 6295, 6288, 6296, 6277, 6268],

/***/ 6295:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*******************************************************************************************************************************************************/
292,

/***/ 6296:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*****************************************************************************************************************************************************/
[7821, 6279],

/***/ 6297:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \***************************************************************************************************************************************/
294,

/***/ 6298:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \**************************************************************************************************************************************************/
[7822, 6299],

/***/ 6299:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \*******************************************************************************************************************************/
[7823, 6300, 6320],

/***/ 6300:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \****************************************************************************************************************************************/
[7824, 6301],

/***/ 6301:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*****************************************************************************************************************************************************/
[7825, 6302, 6315, 6319],

/***/ 6302:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \**************************************************************************************************************************************************************/
[7826, 6303, 6304],

/***/ 6303:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*****************************************************************************************************************************************************/
[7827, 6282, 6279],

/***/ 6304:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*******************************************************************************************************************************************************/
[7828, 6305, 6257, 6306, 6262, 6275, 6307, 6308, 6312, 6314, 6313],

/***/ 6305:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \***************************************************************************************************************************************************/
302,

/***/ 6306:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \****************************************************************************************************************************************************/
[7829, 6262],

/***/ 6307:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*****************************************************************************************************************************************************/
304,

/***/ 6308:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*******************************************************************************************************************************************************/
[7830, 6309, 6271, 6312, 6262, 6313],

/***/ 6309:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \*********************************************************************************************************************************************************/
[7831, 6264, 6310, 6287, 6284, 6269, 6311],

/***/ 6310:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \******************************************************************************************************************************************************/
[7832, 6263, 6264, 6273, 6267],

/***/ 6311:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \************************************************************************************************************************************************/
[7833, 6258],

/***/ 6312:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \*************************************************************************************************************************************************************/
[7834, 6263, 6275, 6313],

/***/ 6313:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \***********************************************************************************************************************************************/
[7835, 6285, 6286, 6258],

/***/ 6314:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \******************************************************************************************************************************************************/
[7836, 6275, 6296, 6284],

/***/ 6315:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \***********************************************************************************************************************************************************/
[7837, 6316, 6258, 6262, 6307, 6313],

/***/ 6316:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \*************************************************************************************************************************************************************/
[7838, 6317, 6318, 6307, 6276, 6304],

/***/ 6317:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \**************************************************************************************************************************************************************/
314,

/***/ 6318:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*****************************************************************************************************************************************************/
315,

/***/ 6319:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \***************************************************************************************************************************************************/
[7839, 6313],

/***/ 6320:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \*******************************************************************************************************************************/
[7840, 6321],

/***/ 6321:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \**************************************************************************************************************************************************/
[7841, 6322, 6331, 6332, 6333, 6259],

/***/ 6322:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*****************************************************************************************************************************************************/
[7842, 6258, 6275, 6267, 6257, 6306, 6323, 6268, 6285, 6312, 6286, 6313, 6319, 6324, 6325, 6326, 6327, 6264, 6276, 6270, 6271, 6309, 6328, 6330, 6263, 6273, 6329, 6288, 6295, 6305, 6262],

/***/ 6323:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \************************************************************************************************************************************************/
[7843, 6286, 6265, 6275, 6263, 6268],

/***/ 6324:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \******************************************************************************************************************************************************/
[7844, 6258, 6259, 6305, 6319, 6263],

/***/ 6325:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \*************************************************************************************************************************************************/
[7845, 6273, 6276],

/***/ 6326:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*****************************************************************************************************************************************************/
[7846, 6273, 6295, 6288],

/***/ 6327:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \****************************************************************************************************************************************************/
[7847, 6278],

/***/ 6328:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \***********************************************************************************************************************************************************/
[7848, 6276, 6329],

/***/ 6329:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*******************************************************************************************************************************************************/
[7849, 6274, 6287],

/***/ 6330:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*******************************************************************************************************************************************************/
[7850, 6288, 6271, 6276, 6270, 6275, 6266, 6267],

/***/ 6331:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \***************************************************************************************************************************************************************/
328,

/***/ 6332:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \********************************************************************************************************************************************************************/
[7851, 6324],

/***/ 6333:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \****************************************************************************************************************************************************************/
[7852, 6324],

/***/ 6334:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \*********************************************************************************************************************************/
[7853, 6335, 6339, 6299],

/***/ 6335:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************/
[7854, 6336],

/***/ 6336:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*************************************************************************************************************************************************************/
[7855, 6337, 6259],

/***/ 6337:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \**********************************************************************************************************************************************************************/
[7856, 6257, 6338],

/***/ 6338:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*****************************************************************************************************************************************************/
[7857, 6265, 6264, 6260, 6330],

/***/ 6339:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \**************************************************************************************************************************************/
[7858, 6340],

/***/ 6340:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \***************************************************************************************************************************************************/
[7859, 6341, 6259],

/***/ 6341:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \************************************************************************************************************************************************************/
[7860, 6257, 6309],

/***/ 6342:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/classnames/index.js ***!
  \*******************************************************************************************************************/
339,

/***/ 6343:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \***********************************************************************************************************************************/
[7861, 6028, 6344],

/***/ 6344:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \********************************************************************************************************************************************************/
341,

/***/ 6345:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*************************************************************************************************************************/
[7862, 6346, 6290, 6349, 6028, 6350],

/***/ 6346:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \***************************************************************************************************************************************/
[7863, 6347],

/***/ 6347:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \****************************************************************************************************************************************************/
[7864, 6348, 6259],

/***/ 6348:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \*************************************************************************************************************************************************************/
[7865, 6257, 6272],

/***/ 6349:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/invariant/browser.js ***!
  \********************************************************************************************************************/
346,

/***/ 6350:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \**********************************************************************************************************************/
347,

/***/ 6351:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***************************************************************************************************************/
[7866, 6290, 6289, 6297, 6298, 6334, 6028, 6343],

/***/ 6352:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormGroup.js ***!
  \**************************************************************************************************************/
[7867, 6290, 6289, 6297, 6298, 6334, 6342, 6028, 6345, 6350, 6353],

/***/ 6353:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*********************************************************************************************************************************/
[7868, 6028],

/***/ 6354:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControl.js ***!
  \****************************************************************************************************************/
[7869, 6290, 6289, 6297, 6298, 6334, 6342, 6028, 6343, 6355, 6356, 6358, 6345],

/***/ 6355:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/warning/browser.js ***!
  \******************************************************************************************************************/
352,

/***/ 6356:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \************************************************************************************************************************/
[7870, 6289, 6290, 6297, 6298, 6334, 6342, 6028, 6357, 6345],

/***/ 6357:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Glyphicon.js ***!
  \**************************************************************************************************************/
[7871, 6290, 6289, 6297, 6298, 6334, 6342, 6028, 6345],

/***/ 6358:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlStatic.js ***!
  \**********************************************************************************************************************/
[7872, 6290, 6289, 6297, 6298, 6334, 6342, 6028, 6343, 6345],

/***/ 6359:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
356,

/***/ 6360:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/react-emojione.js ***!
  \******************************************************************************************************************/
[7873, 6361, 6362, 6522],

/***/ 6361:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*************************************************************************************************************************/
358,

/***/ 6362:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \******************************************************************************************************************************/
[7874, 6363, 6524],

/***/ 6363:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \****************************************************************************************************************************/
[7875, 6364, 6520, 6522],

/***/ 6364:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/react.js ***!
  \*************************************************************************************************************/
[7652, 6365],

/***/ 6365:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/React.js ***!
  \*****************************************************************************************************************/
[7876, 6366, 6510, 6514, 6401, 6519],

/***/ 6366:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOM.js ***!
  \********************************************************************************************************************/
[7877, 6367, 6368, 6433, 6407, 6390, 6380, 6412, 6416, 6508, 6453, 6509, 6387, 6371],

/***/ 6367:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************************************************/
364,

/***/ 6368:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************************************************/
[7878, 6369, 6384, 6388, 6390, 6401, 6383, 6382, 6432],

/***/ 6369:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************************************************/
[7879, 6370, 6378, 6380, 6381, 6382, 6375],

/***/ 6370:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Danger.js ***!
  \******************************************************************************************************************/
[7880, 6371, 6372, 6377, 6376, 6375],

/***/ 6371:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \***************************************************************************************************************************************/
368,

/***/ 6372:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \****************************************************************************************************************************************/
[7881, 6371, 6373, 6376, 6375],

/***/ 6373:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \***************************************************************************************************************************************/
[7882, 6374],

/***/ 6374:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/toArray.js ***!
  \**************************************************************************************************************************/
[7883, 6375],

/***/ 6375:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/invariant.js ***!
  \****************************************************************************************************************************/
372,

/***/ 6376:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \********************************************************************************************************************************/
[7884, 6371, 6375],

/***/ 6377:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyFunction.js ***!
  \********************************************************************************************************************************/
374,

/***/ 6378:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************************************************/
[7885, 6379],

/***/ 6379:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyMirror.js ***!
  \****************************************************************************************************************************/
[7886, 6375],

/***/ 6380:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPerf.js ***!
  \*********************************************************************************************************************/
377,

/***/ 6381:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************************************************/
[7887, 6371],

/***/ 6382:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setTextContent.js ***!
  \**************************************************************************************************************************/
[7888, 6371, 6383, 6381],

/***/ 6383:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************************************************/
380,

/***/ 6384:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7889, 6385, 6380, 6386, 6387],

/***/ 6385:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************************************************/
[7890, 6375],

/***/ 6386:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************************************************/
[7891, 6383],

/***/ 6387:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/warning.js ***!
  \**************************************************************************************************************************/
[7892, 6377],

/***/ 6388:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************************************************/
[7893, 6389, 6390],

/***/ 6389:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************************************************/
[7894, 6369, 6384, 6390, 6380, 6375],

/***/ 6390:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMount.js ***!
  \**********************************************************************************************************************/
[7895, 6385, 6391, 6367, 6403, 6404, 6406, 6407, 6409, 6410, 6380, 6412, 6415, 6416, 6401, 6420, 6421, 6424, 6375, 6381, 6429, 6432, 6387],

/***/ 6391:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************************************************/
[7896, 6392, 6393, 6394, 6399, 6380, 6400, 6401, 6402],

/***/ 6392:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventConstants.js ***!
  \**************************************************************************************************************************/
[7897, 6379],

/***/ 6393:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************************************************/
[7898, 6394, 6395, 6396, 6397, 6398, 6375, 6387],

/***/ 6394:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************************************************/
[7899, 6375],

/***/ 6395:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************************************************/
[7900, 6392, 6396, 6375, 6387],

/***/ 6396:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************************************************/
393,

/***/ 6397:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************************************************/
[7901, 6375],

/***/ 6398:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************************************************/
395,

/***/ 6399:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************************************************/
[7902, 6393],

/***/ 6400:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************************************************/
397,

/***/ 6401:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Object.assign.js ***!
  \*************************************************************************************************************************/
398,

/***/ 6402:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************************************************/
[7903, 6371],

/***/ 6403:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************************************************/
400,

/***/ 6404:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElement.js ***!
  \************************************************************************************************************************/
[7904, 6367, 6401, 6405],

/***/ 6405:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************************************************/
402,

/***/ 6406:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \***************************************************************************************************************************************/
403,

/***/ 6407:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceHandles.js ***!
  \********************************************************************************************************************************/
[7905, 6408, 6375],

/***/ 6408:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRootIndex.js ***!
  \**************************************************************************************************************************/
405,

/***/ 6409:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************************************************/
406,

/***/ 6410:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************************************************/
[7906, 6411],

/***/ 6411:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/adler32.js ***!
  \*******************************************************************************************************************/
408,

/***/ 6412:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************************************************/
[7907, 6413],

/***/ 6413:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRef.js ***!
  \********************************************************************************************************************/
[7908, 6414],

/***/ 6414:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************************************************/
[7909, 6375],

/***/ 6415:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************************************************/
[7910, 6367, 6404, 6409, 6416, 6401, 6375, 6387],

/***/ 6416:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************************************************/
[7911, 6417, 6418, 6380, 6412, 6419, 6401, 6375],

/***/ 6417:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************************************************/
[7912, 6418, 6401, 6375],

/***/ 6418:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/PooledClass.js ***!
  \***********************************************************************************************************************/
[7913, 6375],

/***/ 6419:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Transaction.js ***!
  \***********************************************************************************************************************/
[7914, 6375],

/***/ 6420:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyObject.js ***!
  \******************************************************************************************************************************/
417,

/***/ 6421:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/containsNode.js ***!
  \*******************************************************************************************************************************/
[7915, 6422],

/***/ 6422:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isTextNode.js ***!
  \*****************************************************************************************************************************/
[7916, 6423],

/***/ 6423:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isNode.js ***!
  \*************************************************************************************************************************/
420,

/***/ 6424:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************************************************/
[7917, 6425, 6430, 6431, 6401, 6375, 6387],

/***/ 6425:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************************************************/
[7918, 6426, 6367, 6404, 6409, 6380, 6427, 6428, 6412, 6415, 6401, 6420, 6375, 6429, 6387],

/***/ 6426:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************************************************/
[7919, 6375],

/***/ 6427:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************************************************/
[7920, 6379],

/***/ 6428:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************************************************/
425,

/***/ 6429:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************************************************/
426,

/***/ 6430:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************************************************/
[7921, 6404, 6406, 6412, 6401],

/***/ 6431:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNativeComponent.js ***!
  \********************************************************************************************************************************/
[7922, 6401, 6375],

/***/ 6432:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************************************************/
[7923, 6401, 6377, 6387],

/***/ 6433:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************************************************/
[7924, 6434, 6442, 6445, 6446, 6447, 6371, 6451, 6452, 6388, 6454, 6455, 6368, 6480, 6483, 6407, 6390, 6487, 6492, 6493, 6494, 6503, 6504],

/***/ 6434:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************************************************/
[7925, 6392, 6435, 6371, 6436, 6438, 6440, 6441],

/***/ 6435:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************************************************/
[7926, 6392, 6393, 6387, 6397, 6398],

/***/ 6436:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************************************************/
[7927, 6418, 6401, 6437],

/***/ 6437:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************************************************/
[7928, 6371],

/***/ 6438:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************************************************/
[7929, 6439],

/***/ 6439:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************************************************/
[7930, 6418, 6401, 6377, 6387],

/***/ 6440:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************************************************/
[7931, 6439],

/***/ 6441:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyOf.js ***!
  \************************************************************************************************************************/
438,

/***/ 6442:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7932, 6392, 6393, 6435, 6371, 6416, 6439, 6443, 6402, 6444, 6441],

/***/ 6443:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************************************************/
440,

/***/ 6444:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************************************************/
441,

/***/ 6445:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ClientReactRootIndex.js ***!
  \********************************************************************************************************************************/
442,

/***/ 6446:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************************************************/
[7933, 6441],

/***/ 6447:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************************************************/
[7934, 6392, 6435, 6448, 6390, 6441],

/***/ 6448:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************************************************/
[7935, 6449, 6400, 6450],

/***/ 6449:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************************************************/
[7936, 6439, 6443],

/***/ 6450:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************************************************/
447,

/***/ 6451:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************************************************/
[7937, 6385, 6371],

/***/ 6452:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserComponentMixin.js ***!
  \**************************************************************************************************************************************/
[7938, 6409, 6453, 6387],

/***/ 6453:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************************************************/
[7939, 6367, 6409, 6390, 6375, 6387],

/***/ 6454:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************************************************/
[7940, 6416, 6419, 6401, 6377],

/***/ 6455:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************************************************/
[7941, 6456, 6458, 6385, 6384, 6392, 6391, 6388, 6466, 6467, 6471, 6474, 6475, 6390, 6476, 6380, 6415, 6401, 6405, 6383, 6375, 6402, 6441, 6381, 6382, 6479, 6432, 6387],

/***/ 6456:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************************************************/
[7942, 6390, 6453, 6457],

/***/ 6457:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/focusNode.js ***!
  \****************************************************************************************************************************/
454,

/***/ 6458:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7943, 6459, 6371, 6380, 6460, 6462, 6463, 6465, 6387],

/***/ 6459:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************************************************/
456,

/***/ 6460:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \************************************************************************************************************************************/
[7944, 6461],

/***/ 6461:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelize.js ***!
  \***************************************************************************************************************************/
458,

/***/ 6462:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************************************************/
[7945, 6459],

/***/ 6463:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \*************************************************************************************************************************************/
[7946, 6464],

/***/ 6464:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenate.js ***!
  \****************************************************************************************************************************/
461,

/***/ 6465:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \************************************************************************************************************************************/
462,

/***/ 6466:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************************************************/
463,

/***/ 6467:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************************************************/
[7947, 6389, 6468, 6390, 6416, 6401, 6375],

/***/ 6468:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************************************************/
[7948, 6469, 6427, 6375, 6387],

/***/ 6469:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************************************************/
[7949, 6404, 6428, 6377, 6470],

/***/ 6470:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************************************************/
467,

/***/ 6471:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************************************************/
[7950, 6472, 6474, 6401, 6387],

/***/ 6472:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************************************************/
[7951, 6418, 6404, 6377, 6473],

/***/ 6473:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************************************************/
[7952, 6367, 6404, 6407, 6470, 6375, 6387],

/***/ 6474:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************************************************/
[7953, 6468, 6390, 6416, 6401, 6387],

/***/ 6475:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************************************************/
[7954, 6468, 6389, 6416, 6401, 6375, 6387],

/***/ 6476:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************************************************/
[7955, 6426, 6378, 6367, 6412, 6477, 6478],

/***/ 6477:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************************************************/
[7956, 6412, 6424, 6429, 6473, 6387],

/***/ 6478:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************************************************/
[7957, 6473, 6387],

/***/ 6479:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*******************************************************************************************************************************/
476,

/***/ 6480:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************************************************/
[7958, 6481, 6371, 6418, 6407, 6390, 6416, 6401, 6443, 6482],

/***/ 6481:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/EventListener.js ***!
  \********************************************************************************************************************************/
[7959, 6377],

/***/ 6482:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \*********************************************************************************************************************************************/
479,

/***/ 6483:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************************************************/
[7960, 6385, 6393, 6426, 6484, 6430, 6391, 6431, 6380, 6408, 6416],

/***/ 6484:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactClass.js ***!
  \**********************************************************************************************************************/
[7961, 6485, 6404, 6427, 6428, 6486, 6401, 6420, 6375, 6379, 6441, 6387],

/***/ 6485:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************************************************/
[7962, 6486, 6405, 6420, 6375, 6387],

/***/ 6486:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************************************************/
[7963, 6387],

/***/ 6487:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************************************************/
[7964, 6417, 6418, 6391, 6403, 6488, 6419, 6401],

/***/ 6488:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************************************************/
[7965, 6489, 6421, 6457, 6491],

/***/ 6489:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************************************************/
[7966, 6371, 6490, 6437],

/***/ 6490:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************************************************/
487,

/***/ 6491:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getActiveElement.js ***!
  \***********************************************************************************************************************************/
488,

/***/ 6492:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7967, 6392, 6435, 6371, 6488, 6439, 6491, 6444, 6441, 6479],

/***/ 6493:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ServerReactRootIndex.js ***!
  \********************************************************************************************************************************/
490,

/***/ 6494:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7968, 6392, 6481, 6435, 6390, 6495, 6439, 6496, 6497, 6448, 6500, 6501, 6449, 6502, 6377, 6498, 6375, 6441],

/***/ 6495:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************************************************/
[7969, 6439],

/***/ 6496:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************************************************/
[7970, 6449],

/***/ 6497:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************************************************/
[7971, 6449, 6498, 6499, 6450],

/***/ 6498:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************************************************/
495,

/***/ 6499:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventKey.js ***!
  \***********************************************************************************************************************/
[7972, 6498],

/***/ 6500:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************************************************/
[7973, 6448],

/***/ 6501:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************************************************/
[7974, 6449, 6450],

/***/ 6502:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************************************************/
[7975, 6448],

/***/ 6503:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************************************************/
[7976, 6385],

/***/ 6504:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerf.js ***!
  \****************************************************************************************************************************/
[7977, 6385, 6505, 6390, 6380, 6506],

/***/ 6505:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \************************************************************************************************************************************/
[7978, 6401],

/***/ 6506:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performanceNow.js ***!
  \*********************************************************************************************************************************/
[7979, 6507],

/***/ 6507:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performance.js ***!
  \******************************************************************************************************************************/
[7980, 6371],

/***/ 6508:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactVersion.js ***!
  \************************************************************************************************************************/
505,

/***/ 6509:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************************************************/
[7981, 6390],

/***/ 6510:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMServer.js ***!
  \**************************************************************************************************************************/
[7982, 6433, 6511, 6508],

/***/ 6511:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRendering.js ***!
  \********************************************************************************************************************************/
[7983, 6454, 6404, 6407, 6410, 6512, 6513, 6416, 6420, 6424, 6375],

/***/ 6512:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerBatchingStrategy.js ***!
  \***************************************************************************************************************************************/
509,

/***/ 6513:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************************************************/
[7984, 6418, 6417, 6419, 6401, 6377],

/***/ 6514:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactIsomorphic.js ***!
  \***************************************************************************************************************************/
[7985, 6472, 6485, 6484, 6515, 6404, 6516, 6469, 6508, 6401, 6518],

/***/ 6515:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************************************************/
[7986, 6404, 6516, 6517],

/***/ 6516:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************************************************/
[7987, 6404, 6427, 6428, 6367, 6405, 6470, 6375, 6387],

/***/ 6517:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/mapObject.js ***!
  \****************************************************************************************************************************/
514,

/***/ 6518:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/onlyChild.js ***!
  \*********************************************************************************************************************/
[7988, 6404, 6375],

/***/ 6519:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/deprecated.js ***!
  \**********************************************************************************************************************/
[7989, 6401, 6387],

/***/ 6520:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**************************************************************************************************************************/
[7990, 6521],

/***/ 6521:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \************************************************************************************************************************************/
518,

/***/ 6522:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*********************************************************************************************************************************/
[7991, 6523],

/***/ 6523:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/emoji-data.js ***!
  \*******************************************************************************************************************/
520,

/***/ 6524:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \******************************************************************************************************************************/
[7992, 6522],

/***/ 6525:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaFeedback.css */ 6526);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaFeedback.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaFeedback.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6526:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, "div.gxaFeedbackQuestionBox {\n  margin: 30px;\n  width: 300px;\n  background-color: #b3e0ff;\n  border: 3px solid #008ae6;\n  opacity: 0.6;\n  filter: alpha(opacity=60); /* For IE8 and earlier */\n}\n\n#feedbackBoxCross {\n  margin: 3px;\n  margin-top: 5px;\n  float: right;\n  cursor:pointer;\n}\n\n#feedbackBoxCross:before {\n  color: #BF2222;\n}\n\ndiv.gxaFeedbackQuestionBox p {\n  margin: 2%;\n font-weight: bold;\n text-align: center;\n}\n\ndiv.gxaFeedbackQuestionBox a {\n  float: right;\n  margin-top: 6px;\n  cursor:pointer;\n}\n\ndiv.gxaFeedbackQuestionBoxAnswer {\n  position:relative;\ntext-align: center;\n  margin: 0 auto;\n  margin-bottom: 10px;\n  width: 90%;\n}\n\ndiv.gxaFeedbackQuestionBox button {\n width: auto;\n}\n\n.feedbackBoxTransitionWrapper-leave {\n  opacity: 1;\n}\n\n.feedbackBoxTransitionWrapper-leave.feedbackBoxTransitionWrapper-leave-active {\n  opacity: 0.01;\n  transition: opacity 300ms ease-in;\n}\n\n.gxaSmiley {\n  opacity: 0.6;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmiley:hover {\n  opacity: 0.9;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmileyClicked {\n  opacity: 1;\n}\n\n.gxaSmileyFeedbackBox {\n  text-align: center;\n  clear: both;\n  width: 300px;\n  opacity: 0.8;\n  filter: alpha(opacity=80); /* For IE8 and earlier */\n}\n\n.gxaSmileyRow {\n  text-align: center!important;\n}\n\n.gxaSmileyFeedbackBox p {\n  padding-left: 18px;\n  padding-top: 5px;\n  font-weight: bold;\n  font-size: 14px;\n}\n\n.gxaSmileyFeedbackBox form {\n  padding: 6px;\n  width: 87%;\n}\n\n.gxaSmileyFeedbackBox button {\n  width: 100px;\n  margin-left: 91px;\n}\n\n.form-control {\n  display: block;\n  width: 100%;\n  height: 34px;\n  padding: 6px 12px;\n  font-size: 14px;\n  line-height: 1.42857143;\n  color: #555;\n  background-color: #fff;\n  background-image: none;\n  border: 1px solid #ccc;\n  border-radius: 4px;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;\n       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n}\n.form-control:focus {\n  border-color: #66afe9;\n  outline: 0;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n}\n.form-control::-moz-placeholder {\n  color: #999;\n  opacity: 1;\n}\n.form-control:-ms-input-placeholder {\n  color: #999;\n}\n.form-control::-webkit-input-placeholder {\n  color: #999;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6527:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[8389, 6528, 6688],

/***/ 6528:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[8390, 6529, 6685, 6687],

/***/ 6529:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/react.js ***!
  \************************************************************************************/
[7652, 6530],

/***/ 6530:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/React.js ***!
  \****************************************************************************************/
[7876, 6531, 6675, 6679, 6566, 6684],

/***/ 6531:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOM.js ***!
  \*******************************************************************************************/
[7877, 6532, 6533, 6598, 6572, 6555, 6545, 6577, 6581, 6673, 6618, 6674, 6552, 6536],

/***/ 6532:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCurrentOwner.js ***!
  \****************************************************************************************************/
364,

/***/ 6533:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextComponent.js ***!
  \********************************************************************************************************/
[7878, 6534, 6549, 6553, 6555, 6566, 6548, 6547, 6597],

/***/ 6534:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMChildrenOperations.js ***!
  \********************************************************************************************************/
[7879, 6535, 6543, 6545, 6546, 6547, 6540],

/***/ 6535:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Danger.js ***!
  \*****************************************************************************************/
[7880, 6536, 6537, 6542, 6541, 6540],

/***/ 6536:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**************************************************************************************************************/
368,

/***/ 6537:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***************************************************************************************************************/
[7881, 6536, 6538, 6541, 6540],

/***/ 6538:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \**************************************************************************************************************/
[7882, 6539],

/***/ 6539:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/toArray.js ***!
  \*************************************************************************************************/
[7883, 6540],

/***/ 6540:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/invariant.js ***!
  \***************************************************************************************************/
372,

/***/ 6541:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*******************************************************************************************************/
[7884, 6536, 6540],

/***/ 6542:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*******************************************************************************************************/
374,

/***/ 6543:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*************************************************************************************************************/
[7885, 6544],

/***/ 6544:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyMirror.js ***!
  \***************************************************************************************************/
[7886, 6540],

/***/ 6545:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPerf.js ***!
  \********************************************************************************************/
377,

/***/ 6546:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setInnerHTML.js ***!
  \***********************************************************************************************/
[7887, 6536],

/***/ 6547:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setTextContent.js ***!
  \*************************************************************************************************/
[7888, 6536, 6548, 6546],

/***/ 6548:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/escapeTextContentForBrowser.js ***!
  \**************************************************************************************************************/
380,

/***/ 6549:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMPropertyOperations.js ***!
  \********************************************************************************************************/
[7889, 6550, 6545, 6551, 6552],

/***/ 6550:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMProperty.js ***!
  \**********************************************************************************************/
[7890, 6540],

/***/ 6551:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \****************************************************************************************************************/
[7891, 6548],

/***/ 6552:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/warning.js ***!
  \*************************************************************************************************/
[7892, 6542],

/***/ 6553:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*******************************************************************************************************************/
[7893, 6554, 6555],

/***/ 6554:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMIDOperations.js ***!
  \*******************************************************************************************************/
[7894, 6534, 6549, 6555, 6545, 6540],

/***/ 6555:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMount.js ***!
  \*********************************************************************************************/
[7895, 6550, 6556, 6532, 6568, 6569, 6571, 6572, 6574, 6575, 6545, 6577, 6580, 6581, 6566, 6585, 6586, 6589, 6540, 6546, 6594, 6597, 6552],

/***/ 6556:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***********************************************************************************************************/
[7896, 6557, 6558, 6559, 6564, 6545, 6565, 6566, 6567],

/***/ 6557:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventConstants.js ***!
  \*************************************************************************************************/
[7897, 6544],

/***/ 6558:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginHub.js ***!
  \*************************************************************************************************/
[7898, 6559, 6560, 6561, 6562, 6563, 6540, 6552],

/***/ 6559:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginRegistry.js ***!
  \******************************************************************************************************/
[7899, 6540],

/***/ 6560:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginUtils.js ***!
  \***************************************************************************************************/
[7900, 6557, 6561, 6540, 6552],

/***/ 6561:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactErrorUtils.js ***!
  \**************************************************************************************************/
393,

/***/ 6562:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/accumulateInto.js ***!
  \*************************************************************************************************/
[7901, 6540],

/***/ 6563:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/forEachAccumulated.js ***!
  \*****************************************************************************************************/
395,

/***/ 6564:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventEmitterMixin.js ***!
  \*********************************************************************************************************/
[7902, 6558],

/***/ 6565:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ViewportMetrics.js ***!
  \**************************************************************************************************/
397,

/***/ 6566:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Object.assign.js ***!
  \************************************************************************************************/
398,

/***/ 6567:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isEventSupported.js ***!
  \***************************************************************************************************/
[7903, 6536],

/***/ 6568:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*******************************************************************************************************/
400,

/***/ 6569:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElement.js ***!
  \***********************************************************************************************/
[7904, 6532, 6566, 6570],

/***/ 6570:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/canDefineProperty.js ***!
  \****************************************************************************************************/
402,

/***/ 6571:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \**************************************************************************************************************/
403,

/***/ 6572:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceHandles.js ***!
  \*******************************************************************************************************/
[7905, 6573, 6540],

/***/ 6573:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRootIndex.js ***!
  \*************************************************************************************************/
405,

/***/ 6574:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceMap.js ***!
  \***************************************************************************************************/
406,

/***/ 6575:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMarkupChecksum.js ***!
  \******************************************************************************************************/
[7906, 6576],

/***/ 6576:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/adler32.js ***!
  \******************************************************************************************/
408,

/***/ 6577:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconciler.js ***!
  \**************************************************************************************************/
[7907, 6578],

/***/ 6578:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRef.js ***!
  \*******************************************************************************************/
[7908, 6579],

/***/ 6579:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactOwner.js ***!
  \*********************************************************************************************/
[7909, 6540],

/***/ 6580:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdateQueue.js ***!
  \***************************************************************************************************/
[7910, 6532, 6569, 6574, 6581, 6566, 6540, 6552],

/***/ 6581:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdates.js ***!
  \***********************************************************************************************/
[7911, 6582, 6583, 6545, 6577, 6584, 6566, 6540],

/***/ 6582:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CallbackQueue.js ***!
  \************************************************************************************************/
[7912, 6583, 6566, 6540],

/***/ 6583:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/PooledClass.js ***!
  \**********************************************************************************************/
[7913, 6540],

/***/ 6584:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Transaction.js ***!
  \**********************************************************************************************/
[7914, 6540],

/***/ 6585:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyObject.js ***!
  \*****************************************************************************************************/
417,

/***/ 6586:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/containsNode.js ***!
  \******************************************************************************************************/
[7915, 6587],

/***/ 6587:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isTextNode.js ***!
  \****************************************************************************************************/
[7916, 6588],

/***/ 6588:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isNode.js ***!
  \************************************************************************************************/
420,

/***/ 6589:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/instantiateReactComponent.js ***!
  \************************************************************************************************************/
[7917, 6590, 6595, 6596, 6566, 6540, 6552],

/***/ 6590:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCompositeComponent.js ***!
  \**********************************************************************************************************/
[7918, 6591, 6532, 6569, 6574, 6545, 6592, 6593, 6577, 6580, 6566, 6585, 6540, 6594, 6552],

/***/ 6591:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentEnvironment.js ***!
  \************************************************************************************************************/
[7919, 6540],

/***/ 6592:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocations.js ***!
  \*********************************************************************************************************/
[7920, 6544],

/***/ 6593:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*************************************************************************************************************/
425,

/***/ 6594:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/shouldUpdateReactComponent.js ***!
  \*************************************************************************************************************/
426,

/***/ 6595:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponent.js ***!
  \******************************************************************************************************/
[7921, 6569, 6571, 6577, 6566],

/***/ 6596:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNativeComponent.js ***!
  \*******************************************************************************************************/
[7922, 6566, 6540],

/***/ 6597:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/validateDOMNesting.js ***!
  \*****************************************************************************************************/
[7923, 6566, 6542, 6552],

/***/ 6598:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultInjection.js ***!
  \********************************************************************************************************/
[7924, 6599, 6607, 6610, 6611, 6612, 6536, 6616, 6617, 6553, 6619, 6620, 6533, 6645, 6648, 6572, 6555, 6652, 6657, 6658, 6659, 6668, 6669],

/***/ 6599:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/BeforeInputEventPlugin.js ***!
  \*********************************************************************************************************/
[7925, 6557, 6600, 6536, 6601, 6603, 6605, 6606],

/***/ 6600:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPropagators.js ***!
  \***************************************************************************************************/
[7926, 6557, 6558, 6552, 6562, 6563],

/***/ 6601:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/FallbackCompositionState.js ***!
  \***********************************************************************************************************/
[7927, 6583, 6566, 6602],

/***/ 6602:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getTextContentAccessor.js ***!
  \*********************************************************************************************************/
[7928, 6536],

/***/ 6603:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticCompositionEvent.js ***!
  \************************************************************************************************************/
[7929, 6604],

/***/ 6604:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticEvent.js ***!
  \*************************************************************************************************/
[7930, 6583, 6566, 6542, 6552],

/***/ 6605:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticInputEvent.js ***!
  \******************************************************************************************************/
[7931, 6604],

/***/ 6606:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyOf.js ***!
  \***********************************************************************************************/
438,

/***/ 6607:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ChangeEventPlugin.js ***!
  \****************************************************************************************************/
[7932, 6557, 6558, 6600, 6536, 6581, 6604, 6608, 6567, 6609, 6606],

/***/ 6608:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventTarget.js ***!
  \*************************************************************************************************/
440,

/***/ 6609:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isTextInputElement.js ***!
  \*****************************************************************************************************/
441,

/***/ 6610:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ClientReactRootIndex.js ***!
  \*******************************************************************************************************/
442,

/***/ 6611:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DefaultEventPluginOrder.js ***!
  \**********************************************************************************************************/
[7933, 6606],

/***/ 6612:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EnterLeaveEventPlugin.js ***!
  \********************************************************************************************************/
[7934, 6557, 6600, 6613, 6555, 6606],

/***/ 6613:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticMouseEvent.js ***!
  \******************************************************************************************************/
[7935, 6614, 6565, 6615],

/***/ 6614:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticUIEvent.js ***!
  \***************************************************************************************************/
[7936, 6604, 6608],

/***/ 6615:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventModifierState.js ***!
  \********************************************************************************************************/
447,

/***/ 6616:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \********************************************************************************************************/
[7937, 6550, 6536],

/***/ 6617:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*************************************************************************************************************/
[7938, 6574, 6618, 6552],

/***/ 6618:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/findDOMNode.js ***!
  \**********************************************************************************************/
[7939, 6532, 6574, 6555, 6540, 6552],

/***/ 6619:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \***************************************************************************************************************/
[7940, 6581, 6584, 6566, 6542],

/***/ 6620:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMComponent.js ***!
  \****************************************************************************************************/
[7941, 6621, 6623, 6550, 6549, 6557, 6556, 6553, 6631, 6632, 6636, 6639, 6640, 6555, 6641, 6545, 6580, 6566, 6570, 6548, 6540, 6567, 6606, 6546, 6547, 6644, 6597, 6552],

/***/ 6621:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/AutoFocusUtils.js ***!
  \*************************************************************************************************/
[7942, 6555, 6618, 6622],

/***/ 6622:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/focusNode.js ***!
  \***************************************************************************************************/
454,

/***/ 6623:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSPropertyOperations.js ***!
  \********************************************************************************************************/
[7943, 6624, 6536, 6545, 6625, 6627, 6628, 6630, 6552],

/***/ 6624:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSProperty.js ***!
  \**********************************************************************************************/
456,

/***/ 6625:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***********************************************************************************************************/
[7944, 6626],

/***/ 6626:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelize.js ***!
  \**************************************************************************************************/
458,

/***/ 6627:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/dangerousStyleValue.js ***!
  \******************************************************************************************************/
[7945, 6624],

/***/ 6628:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \************************************************************************************************************/
[7946, 6629],

/***/ 6629:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenate.js ***!
  \***************************************************************************************************/
461,

/***/ 6630:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***********************************************************************************************************/
462,

/***/ 6631:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMButton.js ***!
  \*************************************************************************************************/
463,

/***/ 6632:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMInput.js ***!
  \************************************************************************************************/
[7947, 6554, 6633, 6555, 6581, 6566, 6540],

/***/ 6633:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/LinkedValueUtils.js ***!
  \***************************************************************************************************/
[7948, 6634, 6592, 6540, 6552],

/***/ 6634:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypes.js ***!
  \*************************************************************************************************/
[7949, 6569, 6593, 6542, 6635],

/***/ 6635:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getIteratorFn.js ***!
  \************************************************************************************************/
467,

/***/ 6636:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMOption.js ***!
  \*************************************************************************************************/
[7950, 6637, 6639, 6566, 6552],

/***/ 6637:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildren.js ***!
  \************************************************************************************************/
[7951, 6583, 6569, 6542, 6638],

/***/ 6638:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/traverseAllChildren.js ***!
  \******************************************************************************************************/
[7952, 6532, 6569, 6572, 6635, 6540, 6552],

/***/ 6639:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelect.js ***!
  \*************************************************************************************************/
[7953, 6633, 6555, 6581, 6566, 6552],

/***/ 6640:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextarea.js ***!
  \***************************************************************************************************/
[7954, 6633, 6554, 6581, 6566, 6540, 6552],

/***/ 6641:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChild.js ***!
  \**************************************************************************************************/
[7955, 6591, 6543, 6532, 6577, 6642, 6643],

/***/ 6642:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildReconciler.js ***!
  \*******************************************************************************************************/
[7956, 6577, 6589, 6594, 6638, 6552],

/***/ 6643:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/flattenChildren.js ***!
  \**************************************************************************************************/
[7957, 6638, 6552],

/***/ 6644:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/shallowEqual.js ***!
  \******************************************************************************************************/
476,

/***/ 6645:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventListener.js ***!
  \*****************************************************************************************************/
[7958, 6646, 6536, 6583, 6572, 6555, 6581, 6566, 6608, 6647],

/***/ 6646:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/EventListener.js ***!
  \*******************************************************************************************************/
[7959, 6542],

/***/ 6647:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \********************************************************************************************************************/
479,

/***/ 6648:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInjection.js ***!
  \*************************************************************************************************/
[7960, 6550, 6558, 6591, 6649, 6595, 6556, 6596, 6545, 6573, 6581],

/***/ 6649:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactClass.js ***!
  \*********************************************************************************************/
[7961, 6650, 6569, 6592, 6593, 6651, 6566, 6585, 6540, 6544, 6606, 6552],

/***/ 6650:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponent.js ***!
  \*************************************************************************************************/
[7962, 6651, 6570, 6585, 6540, 6552],

/***/ 6651:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*******************************************************************************************************/
[7963, 6552],

/***/ 6652:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconcileTransaction.js ***!
  \************************************************************************************************************/
[7964, 6582, 6583, 6556, 6568, 6653, 6584, 6566],

/***/ 6653:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInputSelection.js ***!
  \******************************************************************************************************/
[7965, 6654, 6586, 6622, 6656],

/***/ 6654:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelection.js ***!
  \****************************************************************************************************/
[7966, 6536, 6655, 6602],

/***/ 6655:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getNodeForCharacterOffset.js ***!
  \************************************************************************************************************/
487,

/***/ 6656:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**********************************************************************************************************/
488,

/***/ 6657:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SelectEventPlugin.js ***!
  \****************************************************************************************************/
[7967, 6557, 6600, 6536, 6653, 6604, 6656, 6609, 6606, 6644],

/***/ 6658:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ServerReactRootIndex.js ***!
  \*******************************************************************************************************/
490,

/***/ 6659:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SimpleEventPlugin.js ***!
  \****************************************************************************************************/
[7968, 6557, 6646, 6600, 6555, 6660, 6604, 6661, 6662, 6613, 6665, 6666, 6614, 6667, 6542, 6663, 6540, 6606],

/***/ 6660:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticClipboardEvent.js ***!
  \**********************************************************************************************************/
[7969, 6604],

/***/ 6661:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticFocusEvent.js ***!
  \******************************************************************************************************/
[7970, 6614],

/***/ 6662:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*********************************************************************************************************/
[7971, 6614, 6663, 6664, 6615],

/***/ 6663:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventCharCode.js ***!
  \***************************************************************************************************/
495,

/***/ 6664:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventKey.js ***!
  \**********************************************************************************************/
[7972, 6663],

/***/ 6665:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticDragEvent.js ***!
  \*****************************************************************************************************/
[7973, 6613],

/***/ 6666:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticTouchEvent.js ***!
  \******************************************************************************************************/
[7974, 6614, 6615],

/***/ 6667:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticWheelEvent.js ***!
  \******************************************************************************************************/
[7975, 6613],

/***/ 6668:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*******************************************************************************************************/
[7976, 6550],

/***/ 6669:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerf.js ***!
  \***************************************************************************************************/
[7977, 6550, 6670, 6555, 6545, 6671],

/***/ 6670:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***********************************************************************************************************/
[7978, 6566],

/***/ 6671:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performanceNow.js ***!
  \********************************************************************************************************/
[7979, 6672],

/***/ 6672:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performance.js ***!
  \*****************************************************************************************************/
[7980, 6536],

/***/ 6673:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactVersion.js ***!
  \***********************************************************************************************/
505,

/***/ 6674:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*************************************************************************************************************/
[7981, 6555],

/***/ 6675:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMServer.js ***!
  \*************************************************************************************************/
[7982, 6598, 6676, 6673],

/***/ 6676:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRendering.js ***!
  \*******************************************************************************************************/
[7983, 6619, 6569, 6572, 6575, 6677, 6678, 6581, 6585, 6589, 6540],

/***/ 6677:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerBatchingStrategy.js ***!
  \**************************************************************************************************************/
509,

/***/ 6678:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRenderingTransaction.js ***!
  \******************************************************************************************************************/
[7984, 6583, 6582, 6584, 6566, 6542],

/***/ 6679:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactIsomorphic.js ***!
  \**************************************************************************************************/
[7985, 6637, 6650, 6649, 6680, 6569, 6681, 6634, 6673, 6566, 6683],

/***/ 6680:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFactories.js ***!
  \****************************************************************************************************/
[7986, 6569, 6681, 6682],

/***/ 6681:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElementValidator.js ***!
  \********************************************************************************************************/
[7987, 6569, 6592, 6593, 6532, 6570, 6635, 6540, 6552],

/***/ 6682:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/mapObject.js ***!
  \***************************************************************************************************/
514,

/***/ 6683:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/onlyChild.js ***!
  \********************************************************************************************/
[7988, 6569, 6540],

/***/ 6684:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/deprecated.js ***!
  \*********************************************************************************************/
[7989, 6566, 6552],

/***/ 6685:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./ebi-visual-species.css */ 6686);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6686:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Taken from: https://www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css */\n\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    font-size: 100%;\n    color: inherit;\n    content: attr(data-icon);\n    margin: 0 0.3em 0 0\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6687:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
1684,

/***/ 6688:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[8391, 6529, 6689, 6528],

/***/ 6689:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react-dom/index.js ***!
  \****************************************************************************************/
[7673, 6531],

/***/ 6690:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 6691);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6691:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6692:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6028);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 6693);
	
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

/***/ 6693:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 6694);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6211)(content, {});
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

/***/ 6694:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6210)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 2px 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 8px;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4 {\n    font-weight: bold;\n    font-size: 133%;\n    padding: 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6695:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 179);
	var QueryString = __webpack_require__(/*! querystring */ 2713);
	
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