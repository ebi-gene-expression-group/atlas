var expressionAtlasDifferentialExpression =
webpackJsonp_name_([6],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */6197);


/***/ },

/***/ 2871:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/index.js ***!
  \****************************************************************/
[7953, 2872, 2873],

/***/ 2872:
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

/***/ 2873:
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

/***/ 6197:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 6198);

/***/ },

/***/ 6198:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 6369);
	
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

/***/ 6199:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[7823, 6200],

/***/ 6200:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[7824, 6201, 6202, 6214, 6217, 6218, 6223, 6206, 6228, 6229, 6230, 6208, 6224],

/***/ 6201:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/object-assign/index.js ***!
  \********************************************************************************/
5,

/***/ 6202:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[7825, 6203, 6206, 6209, 6211],

/***/ 6203:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[7826, 6204, 6205],

/***/ 6204:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/reactProdInvariant.js ***!
  \*********************************************************************************/
8,

/***/ 6205:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/invariant.js ***!
  \*******************************************************************************/
9,

/***/ 6206:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[7827, 6201, 6207, 6208, 6210],

/***/ 6207:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
11,

/***/ 6208:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/warning.js ***!
  \*****************************************************************************/
[7828, 6209],

/***/ 6209:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyFunction.js ***!
  \***********************************************************************************/
13,

/***/ 6210:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
14,

/***/ 6211:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[7829, 6204, 6207, 6206, 6212, 6205, 6213, 6208],

/***/ 6212:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
16,

/***/ 6213:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/KeyEscapeUtils.js ***!
  \*****************************************************************************/
17,

/***/ 6214:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[7830, 6204, 6215, 6210, 6216, 6205, 6208],

/***/ 6215:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[7831, 6208],

/***/ 6216:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyObject.js ***!
  \*********************************************************************************/
20,

/***/ 6217:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPureComponent.js ***!
  \*********************************************************************************/
[7832, 6201, 6214, 6215, 6216],

/***/ 6218:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[7833, 6204, 6201, 6214, 6206, 6219, 6221, 6215, 6216, 6205, 6220, 6222, 6208],

/***/ 6219:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[7834, 6220],

/***/ 6220:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyMirror.js ***!
  \*******************************************************************************/
[7835, 6205],

/***/ 6221:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
25,

/***/ 6222:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyOf.js ***!
  \***************************************************************************/
26,

/***/ 6223:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[7836, 6206, 6224],

/***/ 6224:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[7837, 6207, 6225, 6206, 6219, 6226, 6210, 6212, 6208],

/***/ 6225:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentTreeHook.js ***!
  \*************************************************************************************/
[7838, 6204, 6207, 6205, 6208],

/***/ 6226:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/checkReactTypeSpec.js ***!
  \*********************************************************************************/
[7839, 6204, 6221, 6227, 6205, 6208, 6225, 6225],

/***/ 6227:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypesSecret.js ***!
  \***********************************************************************************/
32,

/***/ 6228:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[7840, 6206, 6221, 6227, 6209, 6212, 6208],

/***/ 6229:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
34,

/***/ 6230:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[7841, 6204, 6206, 6205],

/***/ 6231:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[7844, 6232],

/***/ 6232:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[7845, 6233, 6236, 6359, 6256, 6253, 6229, 6364, 6365, 6366, 6208, 6246, 6259, 6367, 6368],

/***/ 6233:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentTree.js ***!
  \************************************************************************************/
[7846, 6204, 6234, 6235, 6205],

/***/ 6234:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[7847, 6204, 6205],

/***/ 6235:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentFlags.js ***!
  \*************************************************************************************/
42,

/***/ 6236:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[7848, 6237, 6252, 6270, 6271, 6276, 6277, 6291, 6233, 6330, 6331, 6332, 6333, 6334, 6337, 6338, 6346, 6347, 6348],

/***/ 6237:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[7849, 6238, 6239, 6246, 6247, 6249, 6251, 6222],

/***/ 6238:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[7850, 6220],

/***/ 6239:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[7851, 6238, 6240, 6242, 6244, 6245, 6208],

/***/ 6240:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[7852, 6204, 6241, 6242, 6243, 6244, 6245, 6205],

/***/ 6241:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[7853, 6204, 6205],

/***/ 6242:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[7854, 6204, 6238, 6243, 6205, 6208],

/***/ 6243:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
50,

/***/ 6244:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[7855, 6204, 6205],

/***/ 6245:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
52,

/***/ 6246:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \******************************************************************************************/
53,

/***/ 6247:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[7856, 6201, 6203, 6248],

/***/ 6248:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[7857, 6246],

/***/ 6249:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[7858, 6250],

/***/ 6250:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[7859, 6201, 6203, 6209, 6208],

/***/ 6251:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[7860, 6250],

/***/ 6252:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[7861, 6238, 6240, 6239, 6246, 6233, 6253, 6250, 6267, 6268, 6269, 6222],

/***/ 6253:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[7862, 6204, 6201, 6254, 6203, 6255, 6256, 6266, 6205],

/***/ 6254:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[7863, 6204, 6201, 6203, 6205],

/***/ 6255:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactFeatureFlags.js ***!
  \********************************************************************************/
62,

/***/ 6256:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[7864, 6257, 6259, 6208],

/***/ 6257:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[7865, 6258],

/***/ 6258:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[7866, 6204, 6205],

/***/ 6259:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstrumentation.js ***!
  \***********************************************************************************/
[7867, 6260],

/***/ 6260:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDebugTool.js ***!
  \*****************************************************************************/
[7868, 6261, 6262, 6225, 6263, 6246, 6264, 6208],

/***/ 6261:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \**********************************************************************************************/
[7869, 6208],

/***/ 6262:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \********************************************************************************************/
69,

/***/ 6263:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \***********************************************************************************************/
[7870, 6225, 6208],

/***/ 6264:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performanceNow.js ***!
  \************************************************************************************/
[7871, 6265],

/***/ 6265:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performance.js ***!
  \*********************************************************************************/
[7872, 6246],

/***/ 6266:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[7873, 6204, 6205],

/***/ 6267:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
74,

/***/ 6268:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[7874, 6246],

/***/ 6269:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
76,

/***/ 6270:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[7875, 6222],

/***/ 6271:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[7876, 6238, 6239, 6233, 6272, 6222],

/***/ 6272:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[7877, 6273, 6274, 6275],

/***/ 6273:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[7878, 6250, 6267],

/***/ 6274:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
81,

/***/ 6275:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
82,

/***/ 6276:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[7879, 6234],

/***/ 6277:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[7880, 6278, 6290],

/***/ 6278:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[7881, 6279, 6285, 6289, 6233, 6259, 6282, 6281, 6283],

/***/ 6279:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMLazyTree.js ***!
  \**************************************************************************/
[7882, 6280, 6281, 6282, 6283],

/***/ 6280:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMNamespaces.js ***!
  \****************************************************************************/
87,

/***/ 6281:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[7883, 6246, 6280, 6282],

/***/ 6282:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \*************************************************************************************************/
89,

/***/ 6283:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[7884, 6246, 6284, 6281],

/***/ 6284:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
91,

/***/ 6285:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[7885, 6204, 6279, 6246, 6286, 6209, 6205],

/***/ 6286:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*******************************************************************************************/
[7886, 6246, 6287, 6288, 6205],

/***/ 6287:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \******************************************************************************************/
[7887, 6205],

/***/ 6288:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \***********************************************************************************/
[7888, 6246, 6205],

/***/ 6289:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[7889, 6220],

/***/ 6290:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[7890, 6278, 6233],

/***/ 6291:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[7891, 6204, 6201, 6292, 6294, 6279, 6280, 6234, 6302, 6238, 6240, 6241, 6304, 6307, 6235, 6233, 6309, 6311, 6312, 6313, 6259, 6314, 6326, 6209, 6284, 6205, 6268, 6222, 6321, 6329, 6208],

/***/ 6292:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[7892, 6233, 6293],

/***/ 6293:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/focusNode.js ***!
  \*******************************************************************************/
100,

/***/ 6294:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[7893, 6295, 6246, 6259, 6296, 6298, 6299, 6301, 6208],

/***/ 6295:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
102,

/***/ 6296:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***************************************************************************************/
[7894, 6297],

/***/ 6297:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelize.js ***!
  \******************************************************************************/
104,

/***/ 6298:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[7895, 6295, 6208],

/***/ 6299:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \****************************************************************************************/
[7896, 6300],

/***/ 6300:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenate.js ***!
  \*******************************************************************************/
107,

/***/ 6301:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***************************************************************************************/
108,

/***/ 6302:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[7897, 6234, 6233, 6259, 6303, 6208],

/***/ 6303:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[7898, 6284],

/***/ 6304:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[7899, 6201, 6238, 6241, 6305, 6274, 6306, 6268],

/***/ 6305:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[7900, 6240],

/***/ 6306:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getVendorPrefixedEventName.js ***!
  \*****************************************************************************************/
[7901, 6246],

/***/ 6307:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
[7902, 6308],

/***/ 6308:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DisabledInputUtils.js ***!
  \*********************************************************************************/
115,

/***/ 6309:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[7903, 6204, 6201, 6308, 6302, 6310, 6233, 6253, 6205, 6208],

/***/ 6310:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[7904, 6204, 6228, 6219, 6227, 6205, 6208],

/***/ 6311:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[7905, 6201, 6202, 6233, 6312, 6208],

/***/ 6312:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[7906, 6201, 6308, 6310, 6233, 6253, 6208],

/***/ 6313:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[7907, 6204, 6201, 6308, 6310, 6233, 6253, 6205, 6208],

/***/ 6314:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[7908, 6204, 6315, 6316, 6259, 6289, 6207, 6256, 6317, 6209, 6325, 6205],

/***/ 6315:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[7909, 6204, 6205],

/***/ 6316:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
123,

/***/ 6317:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[7910, 6256, 6318, 6213, 6322, 6211, 6208, 6225, 6225],

/***/ 6318:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[7911, 6204, 6201, 6319, 6323, 6324, 6205, 6208],

/***/ 6319:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[7912, 6204, 6201, 6315, 6207, 6206, 6243, 6316, 6259, 6320, 6219, 6256, 6226, 6216, 6205, 6321, 6322, 6208],

/***/ 6320:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNodeTypes.js ***!
  \*****************************************************************************/
[7913, 6204, 6206, 6205],

/***/ 6321:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/shallowEqual.js ***!
  \**********************************************************************************/
128,

/***/ 6322:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
129,

/***/ 6323:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
130,

/***/ 6324:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostComponent.js ***!
  \*********************************************************************************/
[7914, 6204, 6201, 6205],

/***/ 6325:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[7915, 6213, 6211, 6208, 6225, 6225],

/***/ 6326:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[7916, 6201, 6203, 6266, 6259, 6327],

/***/ 6327:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerUpdateQueue.js ***!
  \*************************************************************************************/
[7917, 6328, 6266, 6208],

/***/ 6328:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[7918, 6204, 6207, 6316, 6259, 6253, 6205, 6208],

/***/ 6329:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[7919, 6201, 6209, 6208],

/***/ 6330:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMEmptyComponent.js ***!
  \*************************************************************************************/
[7920, 6201, 6279, 6233],

/***/ 6331:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTreeTraversal.js ***!
  \************************************************************************************/
[7921, 6204, 6205],

/***/ 6332:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[7922, 6204, 6201, 6278, 6279, 6233, 6284, 6205, 6329],

/***/ 6333:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[7923, 6201, 6253, 6266, 6209],

/***/ 6334:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[7924, 6201, 6335, 6246, 6203, 6233, 6253, 6267, 6336],

/***/ 6335:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/EventListener.js ***!
  \***********************************************************************************/
[7925, 6209],

/***/ 6336:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \************************************************************************************************/
143,

/***/ 6337:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[7926, 6234, 6240, 6242, 6315, 6218, 6323, 6304, 6324, 6253],

/***/ 6338:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[7927, 6201, 6254, 6203, 6304, 6339, 6259, 6266, 6328],

/***/ 6339:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[7928, 6340, 6342, 6293, 6345],

/***/ 6340:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[7929, 6246, 6341, 6248],

/***/ 6341:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
148,

/***/ 6342:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/containsNode.js ***!
  \**********************************************************************************/
[7930, 6343],

/***/ 6343:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isTextNode.js ***!
  \********************************************************************************/
[7931, 6344],

/***/ 6344:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isNode.js ***!
  \****************************************************************************/
151,

/***/ 6345:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**************************************************************************************/
152,

/***/ 6346:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
153,

/***/ 6347:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[7932, 6238, 6239, 6246, 6233, 6339, 6250, 6345, 6269, 6222, 6321],

/***/ 6348:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[7933, 6204, 6238, 6335, 6239, 6233, 6349, 6350, 6250, 6351, 6352, 6272, 6355, 6356, 6357, 6273, 6358, 6209, 6353, 6205, 6222],

/***/ 6349:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticAnimationEvent.js ***!
  \**************************************************************************************/
[7934, 6250],

/***/ 6350:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[7935, 6250],

/***/ 6351:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[7936, 6273],

/***/ 6352:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[7937, 6273, 6353, 6354, 6275],

/***/ 6353:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
160,

/***/ 6354:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[7938, 6353],

/***/ 6355:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[7939, 6272],

/***/ 6356:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[7940, 6273, 6275],

/***/ 6357:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTransitionEvent.js ***!
  \***************************************************************************************/
[7941, 6250],

/***/ 6358:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[7942, 6272],

/***/ 6359:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[7943, 6204, 6279, 6234, 6304, 6207, 6233, 6360, 6361, 6206, 6255, 6316, 6259, 6362, 6256, 6328, 6253, 6216, 6318, 6205, 6281, 6322, 6208],

/***/ 6360:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMContainerInfo.js ***!
  \************************************************************************************/
[7944, 6329],

/***/ 6361:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
168,

/***/ 6362:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[7945, 6363],

/***/ 6363:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
170,

/***/ 6364:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[7946, 6204, 6207, 6233, 6316, 6365, 6205, 6208],

/***/ 6365:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getHostComponentFromComposite.js ***!
  \********************************************************************************************/
[7947, 6320],

/***/ 6366:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[7948, 6359],

/***/ 6367:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \******************************************************************************************/
[7949, 6234, 6241, 6225, 6208],

/***/ 6368:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \*********************************************************************************************/
[7950, 6225, 6208],

/***/ 6369:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 6199);
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 179);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 6371);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 6863);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 6866);
	
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

/***/ 6370:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
836,

/***/ 6371:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery.browser */ 6372);
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 6373);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 6376).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 6392);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 6404);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 6407);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 6412);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 6698).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 6861);
	
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

/***/ 6372:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[8358, 6370],

/***/ 6373:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 6374);

/***/ },

/***/ 6374:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery-ui-bundle */ 6375);
	
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

/***/ 6375:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[8357, 6370],

/***/ 6376:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 6377);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 6389);

/***/ },

/***/ 6377:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6378);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6383);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6387);
	
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

/***/ 6378:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	
	__webpack_require__(/*! ./gxaGradient.css */ 6379);
	
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

/***/ 6379:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 6380);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6380:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 6381:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
234,

/***/ 6382:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	var stylesInDom = {},
		memoize = function(fn) {
			var memo;
			return function () {
				if (typeof memo === "undefined") memo = fn.apply(this, arguments);
				return memo;
			};
		},
		isOldIE = memoize(function() {
			return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase());
		}),
		getHeadElement = memoize(function () {
			return document.head || document.getElementsByTagName("head")[0];
		}),
		singletonElement = null,
		singletonCounter = 0,
		styleElementsInsertedAtTop = [];
	
	module.exports = function(list, options) {
		if(true) {
			if(typeof document !== "object") throw new Error("The style-loader cannot be used in a non-browser environment");
		}
	
		options = options || {};
		// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
		// tags it will allow on a page
		if (typeof options.singleton === "undefined") options.singleton = isOldIE();
	
		// By default, add <style> tags to the bottom of <head>.
		if (typeof options.insertAt === "undefined") options.insertAt = "bottom";
	
		var styles = listToStyles(list);
		addStylesToDom(styles, options);
	
		return function update(newList) {
			var mayRemove = [];
			for(var i = 0; i < styles.length; i++) {
				var item = styles[i];
				var domStyle = stylesInDom[item.id];
				domStyle.refs--;
				mayRemove.push(domStyle);
			}
			if(newList) {
				var newStyles = listToStyles(newList);
				addStylesToDom(newStyles, options);
			}
			for(var i = 0; i < mayRemove.length; i++) {
				var domStyle = mayRemove[i];
				if(domStyle.refs === 0) {
					for(var j = 0; j < domStyle.parts.length; j++)
						domStyle.parts[j]();
					delete stylesInDom[domStyle.id];
				}
			}
		};
	}
	
	function addStylesToDom(styles, options) {
		for(var i = 0; i < styles.length; i++) {
			var item = styles[i];
			var domStyle = stylesInDom[item.id];
			if(domStyle) {
				domStyle.refs++;
				for(var j = 0; j < domStyle.parts.length; j++) {
					domStyle.parts[j](item.parts[j]);
				}
				for(; j < item.parts.length; j++) {
					domStyle.parts.push(addStyle(item.parts[j], options));
				}
			} else {
				var parts = [];
				for(var j = 0; j < item.parts.length; j++) {
					parts.push(addStyle(item.parts[j], options));
				}
				stylesInDom[item.id] = {id: item.id, refs: 1, parts: parts};
			}
		}
	}
	
	function listToStyles(list) {
		var styles = [];
		var newStyles = {};
		for(var i = 0; i < list.length; i++) {
			var item = list[i];
			var id = item[0];
			var css = item[1];
			var media = item[2];
			var sourceMap = item[3];
			var part = {css: css, media: media, sourceMap: sourceMap};
			if(!newStyles[id])
				styles.push(newStyles[id] = {id: id, parts: [part]});
			else
				newStyles[id].parts.push(part);
		}
		return styles;
	}
	
	function insertStyleElement(options, styleElement) {
		var head = getHeadElement();
		var lastStyleElementInsertedAtTop = styleElementsInsertedAtTop[styleElementsInsertedAtTop.length - 1];
		if (options.insertAt === "top") {
			if(!lastStyleElementInsertedAtTop) {
				head.insertBefore(styleElement, head.firstChild);
			} else if(lastStyleElementInsertedAtTop.nextSibling) {
				head.insertBefore(styleElement, lastStyleElementInsertedAtTop.nextSibling);
			} else {
				head.appendChild(styleElement);
			}
			styleElementsInsertedAtTop.push(styleElement);
		} else if (options.insertAt === "bottom") {
			head.appendChild(styleElement);
		} else {
			throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
		}
	}
	
	function removeStyleElement(styleElement) {
		styleElement.parentNode.removeChild(styleElement);
		var idx = styleElementsInsertedAtTop.indexOf(styleElement);
		if(idx >= 0) {
			styleElementsInsertedAtTop.splice(idx, 1);
		}
	}
	
	function createStyleElement(options) {
		var styleElement = document.createElement("style");
		styleElement.type = "text/css";
		insertStyleElement(options, styleElement);
		return styleElement;
	}
	
	function createLinkElement(options) {
		var linkElement = document.createElement("link");
		linkElement.rel = "stylesheet";
		insertStyleElement(options, linkElement);
		return linkElement;
	}
	
	function addStyle(obj, options) {
		var styleElement, update, remove;
	
		if (options.singleton) {
			var styleIndex = singletonCounter++;
			styleElement = singletonElement || (singletonElement = createStyleElement(options));
			update = applyToSingletonTag.bind(null, styleElement, styleIndex, false);
			remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true);
		} else if(obj.sourceMap &&
			typeof URL === "function" &&
			typeof URL.createObjectURL === "function" &&
			typeof URL.revokeObjectURL === "function" &&
			typeof Blob === "function" &&
			typeof btoa === "function") {
			styleElement = createLinkElement(options);
			update = updateLink.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
				if(styleElement.href)
					URL.revokeObjectURL(styleElement.href);
			};
		} else {
			styleElement = createStyleElement(options);
			update = applyToTag.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
			};
		}
	
		update(obj);
	
		return function updateStyle(newObj) {
			if(newObj) {
				if(newObj.css === obj.css && newObj.media === obj.media && newObj.sourceMap === obj.sourceMap)
					return;
				update(obj = newObj);
			} else {
				remove();
			}
		};
	}
	
	var replaceText = (function () {
		var textStore = [];
	
		return function (index, replacement) {
			textStore[index] = replacement;
			return textStore.filter(Boolean).join('\n');
		};
	})();
	
	function applyToSingletonTag(styleElement, index, remove, obj) {
		var css = remove ? "" : obj.css;
	
		if (styleElement.styleSheet) {
			styleElement.styleSheet.cssText = replaceText(index, css);
		} else {
			var cssNode = document.createTextNode(css);
			var childNodes = styleElement.childNodes;
			if (childNodes[index]) styleElement.removeChild(childNodes[index]);
			if (childNodes.length) {
				styleElement.insertBefore(cssNode, childNodes[index]);
			} else {
				styleElement.appendChild(cssNode);
			}
		}
	}
	
	function applyToTag(styleElement, obj) {
		var css = obj.css;
		var media = obj.media;
	
		if(media) {
			styleElement.setAttribute("media", media)
		}
	
		if(styleElement.styleSheet) {
			styleElement.styleSheet.cssText = css;
		} else {
			while(styleElement.firstChild) {
				styleElement.removeChild(styleElement.firstChild);
			}
			styleElement.appendChild(document.createTextNode(css));
		}
	}
	
	function updateLink(linkElement, obj) {
		var css = obj.css;
		var sourceMap = obj.sourceMap;
	
		if(sourceMap) {
			// http://stackoverflow.com/a/26603875
			css += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + " */";
		}
	
		var blob = new Blob([css], { type: "text/css" });
	
		var oldSrc = linkElement.href;
	
		linkElement.href = URL.createObjectURL(blob);
	
		if(oldSrc)
			URL.revokeObjectURL(oldSrc);
	}


/***/ },

/***/ 6383:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/index.js ***!
  \*******************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 6384);

/***/ },

/***/ 6384:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery-ui-bundle */ 6375);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 6385);
	
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

/***/ 6385:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../css-loader!./gxaHelpTooltip.css */ 6386);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6386:
/*!**************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \**************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6387:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 6388);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6388:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6389:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 6378);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6390).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 6383);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 6387);
	
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

/***/ 6390:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[8530, 6391],

/***/ 6391:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
[8531, 6199],

/***/ 6392:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 6393);

/***/ },

/***/ 6393:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6394);
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery-ui-bundle */ 6375);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 6398).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 6400);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 6402);
	
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

/***/ 6394:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
[8361, 6395],

/***/ 6395:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[8362, 6236, 6396, 6229],

/***/ 6396:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[8363, 6204, 6360, 6333, 6206, 6259, 6362, 6256, 6397, 6326, 6253, 6216, 6318, 6205],

/***/ 6397:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
1228,

/***/ 6398:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/index.js ***!
  \******************************************************************************************************************************/
[8530, 6399],

/***/ 6399:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \******************************************************************************************************************************************/
[8531, 6199],

/***/ 6400:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 6401);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6401:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6402:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 6403);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6403:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    width: 26%;\n    left: 300px;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n.gxaDifferentialCellTooltip thead {\n    font-size: 0.9em;\n}\n\n.gxaDifferentialCellTooltip tbody {\n    font-size: smaller;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6404:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery-ui-bundle */ 6375);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOM = __webpack_require__(/*! react-dom */ 6231);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 6405);
	
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

/***/ 6405:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 6406);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6406:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6407:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 6408);

/***/ },

/***/ 6408:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 6394);
	
	var $ = __webpack_require__(/*! jquery */ 6370);
	__webpack_require__(/*! jquery-ui-bundle */ 6375);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 6409);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 6410);
	
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

/***/ 6409:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 6199);
	
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

/***/ 6410:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 6411);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6411:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    position: relative;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n    font-size: 0.8em;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n\n#contrastExperimentDescription {\n    font-size: small;\n}\n\n#contrastExperimentDescription {\n    font-size:small;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6412:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[7954, 6413],

/***/ 6413:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[7955, 6199, 6414, 6416, 6417, 6424, 6523, 6525, 6530, 6531, 6696],

/***/ 6414:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/react-localstorage.js ***!
  \**********************************************************************************************************************/
[7956, 6199, 6415],

/***/ 6415:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/lib/warning.js ***!
  \***************************************************************************************************************/
241,

/***/ 6416:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-timer-mixin/TimerMixin.js ***!
  \*************************************************************************************************************/
242,

/***/ 6417:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-addons-css-transition-group/index.js ***!
  \************************************************************************************************************************/
[7957, 6418],

/***/ 6418:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[7958, 6201, 6200, 6419, 6421],

/***/ 6419:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[7959, 6201, 6200, 6316, 6420, 6209],

/***/ 6420:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[7960, 6325],

/***/ 6421:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[7961, 6200, 6232, 6422, 6423, 6230],

/***/ 6422:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/CSSCore.js ***!
  \*****************************************************************************/
[7962, 6205],

/***/ 6423:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[7963, 6246, 6306],

/***/ 6424:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Button.js ***!
  \***********************************************************************************************************/
[7964, 6425, 6460, 6461, 6468, 6469, 6505, 6513, 6199, 6514, 6516, 6521, 6522],

/***/ 6425:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \**************************************************************************************************************************************/
[7965, 6426],

/***/ 6426:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \***************************************************************************************************************************************************/
[7966, 6427, 6430],

/***/ 6427:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \************************************************************************************************************************************************************/
[7967, 6428, 6443],

/***/ 6428:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \**************************************************************************************************************************************************/
[7968, 6429, 6430, 6431, 6433],

/***/ 6429:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \**************************************************************************************************************************************************/
255,

/***/ 6430:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \************************************************************************************************************************************************/
256,

/***/ 6431:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \***********************************************************************************************************************************************/
[7969, 6432],

/***/ 6432:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \******************************************************************************************************************************************************/
258,

/***/ 6433:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \************************************************************************************************************************************************/
[7970, 6434, 6442, 6438],

/***/ 6434:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*****************************************************************************************************************************************************/
[7971, 6435, 6437, 6441, 6438],

/***/ 6435:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*****************************************************************************************************************************************************/
[7972, 6436],

/***/ 6436:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*****************************************************************************************************************************************************/
262,

/***/ 6437:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \**********************************************************************************************************************************************************/
[7973, 6438, 6439, 6440],

/***/ 6438:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*******************************************************************************************************************************************************/
[7974, 6439],

/***/ 6439:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \*************************************************************************************************************************************************/
265,

/***/ 6440:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \******************************************************************************************************************************************************/
[7975, 6436, 6429],

/***/ 6441:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \********************************************************************************************************************************************************/
[7976, 6436],

/***/ 6442:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \*********************************************************************************************************************************************************/
268,

/***/ 6443:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \***********************************************************************************************************************************************************/
[7977, 6444, 6447, 6459],

/***/ 6444:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*******************************************************************************************************************************************************/
[7978, 6445, 6458],

/***/ 6445:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \****************************************************************************************************************************************************************/
[7979, 6446, 6447, 6451, 6455],

/***/ 6446:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \***********************************************************************************************************************************************/
272,

/***/ 6447:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \******************************************************************************************************************************************************/
[7980, 6448, 6450],

/***/ 6448:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \***************************************************************************************************************************************************/
[7981, 6449],

/***/ 6449:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \***********************************************************************************************************************************************/
275,

/***/ 6450:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \***************************************************************************************************************************************************/
276,

/***/ 6451:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \**********************************************************************************************************************************************************/
[7982, 6447, 6452, 6454],

/***/ 6452:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*****************************************************************************************************************************************************/
[7983, 6453],

/***/ 6453:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \******************************************************************************************************************************************************/
279,

/***/ 6454:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \****************************************************************************************************************************************************/
[7984, 6453],

/***/ 6455:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \******************************************************************************************************************************************************/
[7985, 6456, 6457],

/***/ 6456:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \**************************************************************************************************************************************************/
[7986, 6429],

/***/ 6457:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \***********************************************************************************************************************************************/
283,

/***/ 6458:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \*********************************************************************************************************************************************************/
284,

/***/ 6459:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \******************************************************************************************************************************************************/
285,

/***/ 6460:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \************************************************************************************************************************************************/
286,

/***/ 6461:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \********************************************************************************************************************************/
[7987, 6462],

/***/ 6462:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \**************************************************************************************************************************************/
[7988, 6463],

/***/ 6463:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \***************************************************************************************************************************************************/
[7989, 6464, 6430],

/***/ 6464:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \************************************************************************************************************************************************************/
[7990, 6428, 6465],

/***/ 6465:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \*********************************************************************************************************************************************************/
[7991, 6444, 6466, 6459, 6467, 6448, 6439],

/***/ 6466:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*******************************************************************************************************************************************************/
292,

/***/ 6467:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*****************************************************************************************************************************************************/
[7992, 6450],

/***/ 6468:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \***************************************************************************************************************************************/
294,

/***/ 6469:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \**************************************************************************************************************************************************/
[7993, 6470],

/***/ 6470:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \*******************************************************************************************************************************/
[7994, 6471, 6491],

/***/ 6471:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \****************************************************************************************************************************************/
[7995, 6472],

/***/ 6472:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*****************************************************************************************************************************************************/
[7996, 6473, 6486, 6490],

/***/ 6473:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \**************************************************************************************************************************************************************/
[7997, 6474, 6475],

/***/ 6474:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*****************************************************************************************************************************************************/
[7998, 6453, 6450],

/***/ 6475:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*******************************************************************************************************************************************************/
[7999, 6476, 6428, 6477, 6433, 6446, 6478, 6479, 6483, 6485, 6484],

/***/ 6476:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \***************************************************************************************************************************************************/
302,

/***/ 6477:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \****************************************************************************************************************************************************/
[8000, 6433],

/***/ 6478:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*****************************************************************************************************************************************************/
304,

/***/ 6479:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*******************************************************************************************************************************************************/
[8001, 6480, 6442, 6483, 6433, 6484],

/***/ 6480:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \*********************************************************************************************************************************************************/
[8002, 6435, 6481, 6458, 6455, 6440, 6482],

/***/ 6481:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \******************************************************************************************************************************************************/
[8003, 6434, 6435, 6444, 6438],

/***/ 6482:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \************************************************************************************************************************************************/
[8004, 6429],

/***/ 6483:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \*************************************************************************************************************************************************************/
[8005, 6434, 6446, 6484],

/***/ 6484:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \***********************************************************************************************************************************************/
[8006, 6456, 6457, 6429],

/***/ 6485:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \******************************************************************************************************************************************************/
[8007, 6446, 6467, 6455],

/***/ 6486:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \***********************************************************************************************************************************************************/
[8008, 6487, 6429, 6433, 6478, 6484],

/***/ 6487:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \*************************************************************************************************************************************************************/
[8009, 6488, 6489, 6478, 6447, 6475],

/***/ 6488:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \**************************************************************************************************************************************************************/
314,

/***/ 6489:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*****************************************************************************************************************************************************/
315,

/***/ 6490:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \***************************************************************************************************************************************************/
[8010, 6484],

/***/ 6491:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \*******************************************************************************************************************************/
[8011, 6492],

/***/ 6492:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \**************************************************************************************************************************************************/
[8012, 6493, 6502, 6503, 6504, 6430],

/***/ 6493:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*****************************************************************************************************************************************************/
[8013, 6429, 6446, 6438, 6428, 6477, 6494, 6439, 6456, 6483, 6457, 6484, 6490, 6495, 6496, 6497, 6498, 6435, 6447, 6441, 6442, 6480, 6499, 6501, 6434, 6444, 6500, 6459, 6466, 6476, 6433],

/***/ 6494:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \************************************************************************************************************************************************/
[8014, 6457, 6436, 6446, 6434, 6439],

/***/ 6495:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \******************************************************************************************************************************************************/
[8015, 6429, 6430, 6476, 6490, 6434],

/***/ 6496:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \*************************************************************************************************************************************************/
[8016, 6444, 6447],

/***/ 6497:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*****************************************************************************************************************************************************/
[8017, 6444, 6466, 6459],

/***/ 6498:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \****************************************************************************************************************************************************/
[8018, 6449],

/***/ 6499:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \***********************************************************************************************************************************************************/
[8019, 6447, 6500],

/***/ 6500:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*******************************************************************************************************************************************************/
[8020, 6445, 6458],

/***/ 6501:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*******************************************************************************************************************************************************/
[8021, 6459, 6442, 6447, 6441, 6446, 6437, 6438],

/***/ 6502:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \***************************************************************************************************************************************************************/
328,

/***/ 6503:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \********************************************************************************************************************************************************************/
[8022, 6495],

/***/ 6504:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \****************************************************************************************************************************************************************/
[8023, 6495],

/***/ 6505:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \*********************************************************************************************************************************/
[8024, 6506, 6510, 6470],

/***/ 6506:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************/
[8025, 6507],

/***/ 6507:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*************************************************************************************************************************************************************/
[8026, 6508, 6430],

/***/ 6508:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \**********************************************************************************************************************************************************************/
[8027, 6428, 6509],

/***/ 6509:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*****************************************************************************************************************************************************/
[8028, 6436, 6435, 6431, 6501],

/***/ 6510:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \**************************************************************************************************************************************/
[8029, 6511],

/***/ 6511:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \***************************************************************************************************************************************************/
[8030, 6512, 6430],

/***/ 6512:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \************************************************************************************************************************************************************/
[8031, 6428, 6480],

/***/ 6513:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/classnames/index.js ***!
  \*******************************************************************************************************************/
339,

/***/ 6514:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \***********************************************************************************************************************************/
[8032, 6199, 6515],

/***/ 6515:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \********************************************************************************************************************************************************/
341,

/***/ 6516:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*************************************************************************************************************************/
[8033, 6517, 6461, 6520, 6199, 6521],

/***/ 6517:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \***************************************************************************************************************************************/
[8034, 6518],

/***/ 6518:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \****************************************************************************************************************************************************/
[8035, 6519, 6430],

/***/ 6519:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \*************************************************************************************************************************************************************/
[8036, 6428, 6443],

/***/ 6520:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/invariant/browser.js ***!
  \********************************************************************************************************************/
346,

/***/ 6521:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \**********************************************************************************************************************/
347,

/***/ 6522:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***************************************************************************************************************/
[8037, 6461, 6460, 6468, 6469, 6505, 6199, 6514],

/***/ 6523:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormGroup.js ***!
  \**************************************************************************************************************/
[8038, 6461, 6460, 6468, 6469, 6505, 6513, 6199, 6516, 6521, 6524],

/***/ 6524:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*********************************************************************************************************************************/
[8039, 6199],

/***/ 6525:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControl.js ***!
  \****************************************************************************************************************/
[8040, 6461, 6460, 6468, 6469, 6505, 6513, 6199, 6514, 6526, 6527, 6529, 6516],

/***/ 6526:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/warning/browser.js ***!
  \******************************************************************************************************************/
352,

/***/ 6527:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \************************************************************************************************************************/
[8041, 6460, 6461, 6468, 6469, 6505, 6513, 6199, 6528, 6516],

/***/ 6528:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Glyphicon.js ***!
  \**************************************************************************************************************/
[8042, 6461, 6460, 6468, 6469, 6505, 6513, 6199, 6516],

/***/ 6529:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlStatic.js ***!
  \**********************************************************************************************************************/
[8043, 6461, 6460, 6468, 6469, 6505, 6513, 6199, 6514, 6516],

/***/ 6530:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
356,

/***/ 6531:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/react-emojione.js ***!
  \******************************************************************************************************************/
[8044, 6532, 6533, 6693],

/***/ 6532:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*************************************************************************************************************************/
358,

/***/ 6533:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \******************************************************************************************************************************/
[8045, 6534, 6695],

/***/ 6534:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \****************************************************************************************************************************/
[8046, 6535, 6691, 6693],

/***/ 6535:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/react.js ***!
  \*************************************************************************************************************/
[7823, 6536],

/***/ 6536:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/React.js ***!
  \*****************************************************************************************************************/
[8047, 6537, 6681, 6685, 6572, 6690],

/***/ 6537:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOM.js ***!
  \********************************************************************************************************************/
[8048, 6538, 6539, 6604, 6578, 6561, 6551, 6583, 6587, 6679, 6624, 6680, 6558, 6542],

/***/ 6538:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************************************************/
364,

/***/ 6539:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************************************************/
[8049, 6540, 6555, 6559, 6561, 6572, 6554, 6553, 6603],

/***/ 6540:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************************************************/
[8050, 6541, 6549, 6551, 6552, 6553, 6546],

/***/ 6541:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Danger.js ***!
  \******************************************************************************************************************/
[8051, 6542, 6543, 6548, 6547, 6546],

/***/ 6542:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \***************************************************************************************************************************************/
368,

/***/ 6543:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \****************************************************************************************************************************************/
[8052, 6542, 6544, 6547, 6546],

/***/ 6544:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \***************************************************************************************************************************************/
[8053, 6545],

/***/ 6545:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/toArray.js ***!
  \**************************************************************************************************************************/
[8054, 6546],

/***/ 6546:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/invariant.js ***!
  \****************************************************************************************************************************/
372,

/***/ 6547:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \********************************************************************************************************************************/
[8055, 6542, 6546],

/***/ 6548:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyFunction.js ***!
  \********************************************************************************************************************************/
374,

/***/ 6549:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************************************************/
[8056, 6550],

/***/ 6550:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyMirror.js ***!
  \****************************************************************************************************************************/
[8057, 6546],

/***/ 6551:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPerf.js ***!
  \*********************************************************************************************************************/
377,

/***/ 6552:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************************************************/
[8058, 6542],

/***/ 6553:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setTextContent.js ***!
  \**************************************************************************************************************************/
[8059, 6542, 6554, 6552],

/***/ 6554:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************************************************/
380,

/***/ 6555:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[8060, 6556, 6551, 6557, 6558],

/***/ 6556:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************************************************/
[8061, 6546],

/***/ 6557:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************************************************/
[8062, 6554],

/***/ 6558:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/warning.js ***!
  \**************************************************************************************************************************/
[8063, 6548],

/***/ 6559:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************************************************/
[8064, 6560, 6561],

/***/ 6560:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************************************************/
[8065, 6540, 6555, 6561, 6551, 6546],

/***/ 6561:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMount.js ***!
  \**********************************************************************************************************************/
[8066, 6556, 6562, 6538, 6574, 6575, 6577, 6578, 6580, 6581, 6551, 6583, 6586, 6587, 6572, 6591, 6592, 6595, 6546, 6552, 6600, 6603, 6558],

/***/ 6562:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************************************************/
[8067, 6563, 6564, 6565, 6570, 6551, 6571, 6572, 6573],

/***/ 6563:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventConstants.js ***!
  \**************************************************************************************************************************/
[8068, 6550],

/***/ 6564:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************************************************/
[8069, 6565, 6566, 6567, 6568, 6569, 6546, 6558],

/***/ 6565:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************************************************/
[8070, 6546],

/***/ 6566:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************************************************/
[8071, 6563, 6567, 6546, 6558],

/***/ 6567:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************************************************/
393,

/***/ 6568:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************************************************/
[8072, 6546],

/***/ 6569:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************************************************/
395,

/***/ 6570:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************************************************/
[8073, 6564],

/***/ 6571:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************************************************/
397,

/***/ 6572:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Object.assign.js ***!
  \*************************************************************************************************************************/
398,

/***/ 6573:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************************************************/
[8074, 6542],

/***/ 6574:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************************************************/
400,

/***/ 6575:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElement.js ***!
  \************************************************************************************************************************/
[8075, 6538, 6572, 6576],

/***/ 6576:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************************************************/
402,

/***/ 6577:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \***************************************************************************************************************************************/
403,

/***/ 6578:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceHandles.js ***!
  \********************************************************************************************************************************/
[8076, 6579, 6546],

/***/ 6579:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRootIndex.js ***!
  \**************************************************************************************************************************/
405,

/***/ 6580:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************************************************/
406,

/***/ 6581:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************************************************/
[8077, 6582],

/***/ 6582:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/adler32.js ***!
  \*******************************************************************************************************************/
408,

/***/ 6583:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************************************************/
[8078, 6584],

/***/ 6584:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRef.js ***!
  \********************************************************************************************************************/
[8079, 6585],

/***/ 6585:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************************************************/
[8080, 6546],

/***/ 6586:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************************************************/
[8081, 6538, 6575, 6580, 6587, 6572, 6546, 6558],

/***/ 6587:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************************************************/
[8082, 6588, 6589, 6551, 6583, 6590, 6572, 6546],

/***/ 6588:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************************************************/
[8083, 6589, 6572, 6546],

/***/ 6589:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/PooledClass.js ***!
  \***********************************************************************************************************************/
[8084, 6546],

/***/ 6590:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Transaction.js ***!
  \***********************************************************************************************************************/
[8085, 6546],

/***/ 6591:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyObject.js ***!
  \******************************************************************************************************************************/
417,

/***/ 6592:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/containsNode.js ***!
  \*******************************************************************************************************************************/
[8086, 6593],

/***/ 6593:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isTextNode.js ***!
  \*****************************************************************************************************************************/
[8087, 6594],

/***/ 6594:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isNode.js ***!
  \*************************************************************************************************************************/
420,

/***/ 6595:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************************************************/
[8088, 6596, 6601, 6602, 6572, 6546, 6558],

/***/ 6596:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************************************************/
[8089, 6597, 6538, 6575, 6580, 6551, 6598, 6599, 6583, 6586, 6572, 6591, 6546, 6600, 6558],

/***/ 6597:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************************************************/
[8090, 6546],

/***/ 6598:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************************************************/
[8091, 6550],

/***/ 6599:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************************************************/
425,

/***/ 6600:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************************************************/
426,

/***/ 6601:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************************************************/
[8092, 6575, 6577, 6583, 6572],

/***/ 6602:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNativeComponent.js ***!
  \********************************************************************************************************************************/
[8093, 6572, 6546],

/***/ 6603:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************************************************/
[8094, 6572, 6548, 6558],

/***/ 6604:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************************************************/
[8095, 6605, 6613, 6616, 6617, 6618, 6542, 6622, 6623, 6559, 6625, 6626, 6539, 6651, 6654, 6578, 6561, 6658, 6663, 6664, 6665, 6674, 6675],

/***/ 6605:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************************************************/
[8096, 6563, 6606, 6542, 6607, 6609, 6611, 6612],

/***/ 6606:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************************************************/
[8097, 6563, 6564, 6558, 6568, 6569],

/***/ 6607:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************************************************/
[8098, 6589, 6572, 6608],

/***/ 6608:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************************************************/
[8099, 6542],

/***/ 6609:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************************************************/
[8100, 6610],

/***/ 6610:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************************************************/
[8101, 6589, 6572, 6548, 6558],

/***/ 6611:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************************************************/
[8102, 6610],

/***/ 6612:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyOf.js ***!
  \************************************************************************************************************************/
438,

/***/ 6613:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************************************************/
[8103, 6563, 6564, 6606, 6542, 6587, 6610, 6614, 6573, 6615, 6612],

/***/ 6614:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************************************************/
440,

/***/ 6615:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************************************************/
441,

/***/ 6616:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ClientReactRootIndex.js ***!
  \********************************************************************************************************************************/
442,

/***/ 6617:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************************************************/
[8104, 6612],

/***/ 6618:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************************************************/
[8105, 6563, 6606, 6619, 6561, 6612],

/***/ 6619:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************************************************/
[8106, 6620, 6571, 6621],

/***/ 6620:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************************************************/
[8107, 6610, 6614],

/***/ 6621:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************************************************/
447,

/***/ 6622:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************************************************/
[8108, 6556, 6542],

/***/ 6623:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserComponentMixin.js ***!
  \**************************************************************************************************************************************/
[8109, 6580, 6624, 6558],

/***/ 6624:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************************************************/
[8110, 6538, 6580, 6561, 6546, 6558],

/***/ 6625:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************************************************/
[8111, 6587, 6590, 6572, 6548],

/***/ 6626:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************************************************/
[8112, 6627, 6629, 6556, 6555, 6563, 6562, 6559, 6637, 6638, 6642, 6645, 6646, 6561, 6647, 6551, 6586, 6572, 6576, 6554, 6546, 6573, 6612, 6552, 6553, 6650, 6603, 6558],

/***/ 6627:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************************************************/
[8113, 6561, 6624, 6628],

/***/ 6628:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/focusNode.js ***!
  \****************************************************************************************************************************/
454,

/***/ 6629:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[8114, 6630, 6542, 6551, 6631, 6633, 6634, 6636, 6558],

/***/ 6630:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************************************************/
456,

/***/ 6631:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \************************************************************************************************************************************/
[8115, 6632],

/***/ 6632:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelize.js ***!
  \***************************************************************************************************************************/
458,

/***/ 6633:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************************************************/
[8116, 6630],

/***/ 6634:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \*************************************************************************************************************************************/
[8117, 6635],

/***/ 6635:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenate.js ***!
  \****************************************************************************************************************************/
461,

/***/ 6636:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \************************************************************************************************************************************/
462,

/***/ 6637:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************************************************/
463,

/***/ 6638:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************************************************/
[8118, 6560, 6639, 6561, 6587, 6572, 6546],

/***/ 6639:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************************************************/
[8119, 6640, 6598, 6546, 6558],

/***/ 6640:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************************************************/
[8120, 6575, 6599, 6548, 6641],

/***/ 6641:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************************************************/
467,

/***/ 6642:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************************************************/
[8121, 6643, 6645, 6572, 6558],

/***/ 6643:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************************************************/
[8122, 6589, 6575, 6548, 6644],

/***/ 6644:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************************************************/
[8123, 6538, 6575, 6578, 6641, 6546, 6558],

/***/ 6645:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************************************************/
[8124, 6639, 6561, 6587, 6572, 6558],

/***/ 6646:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************************************************/
[8125, 6639, 6560, 6587, 6572, 6546, 6558],

/***/ 6647:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************************************************/
[8126, 6597, 6549, 6538, 6583, 6648, 6649],

/***/ 6648:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************************************************/
[8127, 6583, 6595, 6600, 6644, 6558],

/***/ 6649:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************************************************/
[8128, 6644, 6558],

/***/ 6650:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*******************************************************************************************************************************/
476,

/***/ 6651:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************************************************/
[8129, 6652, 6542, 6589, 6578, 6561, 6587, 6572, 6614, 6653],

/***/ 6652:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/EventListener.js ***!
  \********************************************************************************************************************************/
[8130, 6548],

/***/ 6653:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \*********************************************************************************************************************************************/
479,

/***/ 6654:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************************************************/
[8131, 6556, 6564, 6597, 6655, 6601, 6562, 6602, 6551, 6579, 6587],

/***/ 6655:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactClass.js ***!
  \**********************************************************************************************************************/
[8132, 6656, 6575, 6598, 6599, 6657, 6572, 6591, 6546, 6550, 6612, 6558],

/***/ 6656:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************************************************/
[8133, 6657, 6576, 6591, 6546, 6558],

/***/ 6657:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************************************************/
[8134, 6558],

/***/ 6658:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************************************************/
[8135, 6588, 6589, 6562, 6574, 6659, 6590, 6572],

/***/ 6659:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************************************************/
[8136, 6660, 6592, 6628, 6662],

/***/ 6660:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************************************************/
[8137, 6542, 6661, 6608],

/***/ 6661:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************************************************/
487,

/***/ 6662:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getActiveElement.js ***!
  \***********************************************************************************************************************************/
488,

/***/ 6663:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************************************************/
[8138, 6563, 6606, 6542, 6659, 6610, 6662, 6615, 6612, 6650],

/***/ 6664:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ServerReactRootIndex.js ***!
  \********************************************************************************************************************************/
490,

/***/ 6665:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************************************************/
[8139, 6563, 6652, 6606, 6561, 6666, 6610, 6667, 6668, 6619, 6671, 6672, 6620, 6673, 6548, 6669, 6546, 6612],

/***/ 6666:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************************************************/
[8140, 6610],

/***/ 6667:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************************************************/
[8141, 6620],

/***/ 6668:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************************************************/
[8142, 6620, 6669, 6670, 6621],

/***/ 6669:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************************************************/
495,

/***/ 6670:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventKey.js ***!
  \***********************************************************************************************************************/
[8143, 6669],

/***/ 6671:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************************************************/
[8144, 6619],

/***/ 6672:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************************************************/
[8145, 6620, 6621],

/***/ 6673:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************************************************/
[8146, 6619],

/***/ 6674:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************************************************/
[8147, 6556],

/***/ 6675:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerf.js ***!
  \****************************************************************************************************************************/
[8148, 6556, 6676, 6561, 6551, 6677],

/***/ 6676:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \************************************************************************************************************************************/
[8149, 6572],

/***/ 6677:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performanceNow.js ***!
  \*********************************************************************************************************************************/
[8150, 6678],

/***/ 6678:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performance.js ***!
  \******************************************************************************************************************************/
[8151, 6542],

/***/ 6679:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactVersion.js ***!
  \************************************************************************************************************************/
505,

/***/ 6680:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************************************************/
[8152, 6561],

/***/ 6681:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMServer.js ***!
  \**************************************************************************************************************************/
[8153, 6604, 6682, 6679],

/***/ 6682:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRendering.js ***!
  \********************************************************************************************************************************/
[8154, 6625, 6575, 6578, 6581, 6683, 6684, 6587, 6591, 6595, 6546],

/***/ 6683:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerBatchingStrategy.js ***!
  \***************************************************************************************************************************************/
509,

/***/ 6684:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************************************************/
[8155, 6589, 6588, 6590, 6572, 6548],

/***/ 6685:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactIsomorphic.js ***!
  \***************************************************************************************************************************/
[8156, 6643, 6656, 6655, 6686, 6575, 6687, 6640, 6679, 6572, 6689],

/***/ 6686:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************************************************/
[8157, 6575, 6687, 6688],

/***/ 6687:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************************************************/
[8158, 6575, 6598, 6599, 6538, 6576, 6641, 6546, 6558],

/***/ 6688:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/mapObject.js ***!
  \****************************************************************************************************************************/
514,

/***/ 6689:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/onlyChild.js ***!
  \*********************************************************************************************************************/
[8159, 6575, 6546],

/***/ 6690:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/deprecated.js ***!
  \**********************************************************************************************************************/
[8160, 6572, 6558],

/***/ 6691:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**************************************************************************************************************************/
[8161, 6692],

/***/ 6692:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \************************************************************************************************************************************/
518,

/***/ 6693:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*********************************************************************************************************************************/
[8162, 6694],

/***/ 6694:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/emoji-data.js ***!
  \*******************************************************************************************************************/
520,

/***/ 6695:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \******************************************************************************************************************************/
[8163, 6693],

/***/ 6696:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaFeedback.css */ 6697);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6697:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, "div.gxaFeedbackQuestionBox {\n  margin: 30px;\n  width: 300px;\n  background-color: #b3e0ff;\n  border: 3px solid #008ae6;\n  opacity: 0.6;\n  filter: alpha(opacity=60); /* For IE8 and earlier */\n}\n\n#feedbackBoxCross {\n  margin: 3px;\n  margin-top: 5px;\n  float: right;\n  cursor:pointer;\n}\n\n#feedbackBoxCross:before {\n  color: #BF2222;\n}\n\ndiv.gxaFeedbackQuestionBox p {\n  margin: 2%;\n font-weight: bold;\n text-align: center;\n}\n\ndiv.gxaFeedbackQuestionBox a {\n  float: right;\n  margin-top: 6px;\n  cursor:pointer;\n}\n\ndiv.gxaFeedbackQuestionBoxAnswer {\n  position:relative;\ntext-align: center;\n  margin: 0 auto;\n  margin-bottom: 10px;\n  width: 90%;\n}\n\ndiv.gxaFeedbackQuestionBox button {\n width: auto;\n}\n\n.feedbackBoxTransitionWrapper-leave {\n  opacity: 1;\n}\n\n.feedbackBoxTransitionWrapper-leave.feedbackBoxTransitionWrapper-leave-active {\n  opacity: 0.01;\n  transition: opacity 300ms ease-in;\n}\n\n.gxaSmiley {\n  opacity: 0.6;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmiley:hover {\n  opacity: 0.9;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmileyClicked {\n  opacity: 1;\n}\n\n.gxaSmileyFeedbackBox {\n  text-align: center;\n  clear: both;\n  width: 300px;\n  opacity: 0.8;\n  filter: alpha(opacity=80); /* For IE8 and earlier */\n}\n\n.gxaSmileyRow {\n  text-align: center!important;\n}\n\n.gxaSmileyFeedbackBox p {\n  padding-left: 18px;\n  padding-top: 5px;\n  font-weight: bold;\n  font-size: 14px;\n}\n\n.gxaSmileyFeedbackBox form {\n  padding: 6px;\n  width: 87%;\n}\n\n.gxaSmileyFeedbackBox button {\n  width: 100px;\n  margin-left: 91px;\n}\n\n.form-control {\n  display: block;\n  width: 100%;\n  height: 34px;\n  padding: 6px 12px;\n  font-size: 14px;\n  line-height: 1.42857143;\n  color: #555;\n  background-color: #fff;\n  background-image: none;\n  border: 1px solid #ccc;\n  border-radius: 4px;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;\n       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n}\n.form-control:focus {\n  border-color: #66afe9;\n  outline: 0;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n}\n.form-control::-moz-placeholder {\n  color: #999;\n  opacity: 1;\n}\n.form-control:-ms-input-placeholder {\n  color: #999;\n}\n.form-control::-webkit-input-placeholder {\n  color: #999;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6698:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[8565, 6699, 6859],

/***/ 6699:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[8566, 6700, 6856, 6858],

/***/ 6700:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/react.js ***!
  \************************************************************************************/
[7823, 6701],

/***/ 6701:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/React.js ***!
  \****************************************************************************************/
[8047, 6702, 6846, 6850, 6737, 6855],

/***/ 6702:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOM.js ***!
  \*******************************************************************************************/
[8048, 6703, 6704, 6769, 6743, 6726, 6716, 6748, 6752, 6844, 6789, 6845, 6723, 6707],

/***/ 6703:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCurrentOwner.js ***!
  \****************************************************************************************************/
364,

/***/ 6704:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextComponent.js ***!
  \********************************************************************************************************/
[8049, 6705, 6720, 6724, 6726, 6737, 6719, 6718, 6768],

/***/ 6705:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMChildrenOperations.js ***!
  \********************************************************************************************************/
[8050, 6706, 6714, 6716, 6717, 6718, 6711],

/***/ 6706:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Danger.js ***!
  \*****************************************************************************************/
[8051, 6707, 6708, 6713, 6712, 6711],

/***/ 6707:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**************************************************************************************************************/
368,

/***/ 6708:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***************************************************************************************************************/
[8052, 6707, 6709, 6712, 6711],

/***/ 6709:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \**************************************************************************************************************/
[8053, 6710],

/***/ 6710:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/toArray.js ***!
  \*************************************************************************************************/
[8054, 6711],

/***/ 6711:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/invariant.js ***!
  \***************************************************************************************************/
372,

/***/ 6712:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*******************************************************************************************************/
[8055, 6707, 6711],

/***/ 6713:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*******************************************************************************************************/
374,

/***/ 6714:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*************************************************************************************************************/
[8056, 6715],

/***/ 6715:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyMirror.js ***!
  \***************************************************************************************************/
[8057, 6711],

/***/ 6716:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPerf.js ***!
  \********************************************************************************************/
377,

/***/ 6717:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setInnerHTML.js ***!
  \***********************************************************************************************/
[8058, 6707],

/***/ 6718:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setTextContent.js ***!
  \*************************************************************************************************/
[8059, 6707, 6719, 6717],

/***/ 6719:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/escapeTextContentForBrowser.js ***!
  \**************************************************************************************************************/
380,

/***/ 6720:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMPropertyOperations.js ***!
  \********************************************************************************************************/
[8060, 6721, 6716, 6722, 6723],

/***/ 6721:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMProperty.js ***!
  \**********************************************************************************************/
[8061, 6711],

/***/ 6722:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \****************************************************************************************************************/
[8062, 6719],

/***/ 6723:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/warning.js ***!
  \*************************************************************************************************/
[8063, 6713],

/***/ 6724:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*******************************************************************************************************************/
[8064, 6725, 6726],

/***/ 6725:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMIDOperations.js ***!
  \*******************************************************************************************************/
[8065, 6705, 6720, 6726, 6716, 6711],

/***/ 6726:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMount.js ***!
  \*********************************************************************************************/
[8066, 6721, 6727, 6703, 6739, 6740, 6742, 6743, 6745, 6746, 6716, 6748, 6751, 6752, 6737, 6756, 6757, 6760, 6711, 6717, 6765, 6768, 6723],

/***/ 6727:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***********************************************************************************************************/
[8067, 6728, 6729, 6730, 6735, 6716, 6736, 6737, 6738],

/***/ 6728:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventConstants.js ***!
  \*************************************************************************************************/
[8068, 6715],

/***/ 6729:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginHub.js ***!
  \*************************************************************************************************/
[8069, 6730, 6731, 6732, 6733, 6734, 6711, 6723],

/***/ 6730:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginRegistry.js ***!
  \******************************************************************************************************/
[8070, 6711],

/***/ 6731:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginUtils.js ***!
  \***************************************************************************************************/
[8071, 6728, 6732, 6711, 6723],

/***/ 6732:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactErrorUtils.js ***!
  \**************************************************************************************************/
393,

/***/ 6733:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/accumulateInto.js ***!
  \*************************************************************************************************/
[8072, 6711],

/***/ 6734:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/forEachAccumulated.js ***!
  \*****************************************************************************************************/
395,

/***/ 6735:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventEmitterMixin.js ***!
  \*********************************************************************************************************/
[8073, 6729],

/***/ 6736:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ViewportMetrics.js ***!
  \**************************************************************************************************/
397,

/***/ 6737:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Object.assign.js ***!
  \************************************************************************************************/
398,

/***/ 6738:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isEventSupported.js ***!
  \***************************************************************************************************/
[8074, 6707],

/***/ 6739:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*******************************************************************************************************/
400,

/***/ 6740:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElement.js ***!
  \***********************************************************************************************/
[8075, 6703, 6737, 6741],

/***/ 6741:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/canDefineProperty.js ***!
  \****************************************************************************************************/
402,

/***/ 6742:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \**************************************************************************************************************/
403,

/***/ 6743:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceHandles.js ***!
  \*******************************************************************************************************/
[8076, 6744, 6711],

/***/ 6744:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRootIndex.js ***!
  \*************************************************************************************************/
405,

/***/ 6745:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceMap.js ***!
  \***************************************************************************************************/
406,

/***/ 6746:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMarkupChecksum.js ***!
  \******************************************************************************************************/
[8077, 6747],

/***/ 6747:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/adler32.js ***!
  \******************************************************************************************/
408,

/***/ 6748:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconciler.js ***!
  \**************************************************************************************************/
[8078, 6749],

/***/ 6749:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRef.js ***!
  \*******************************************************************************************/
[8079, 6750],

/***/ 6750:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactOwner.js ***!
  \*********************************************************************************************/
[8080, 6711],

/***/ 6751:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdateQueue.js ***!
  \***************************************************************************************************/
[8081, 6703, 6740, 6745, 6752, 6737, 6711, 6723],

/***/ 6752:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdates.js ***!
  \***********************************************************************************************/
[8082, 6753, 6754, 6716, 6748, 6755, 6737, 6711],

/***/ 6753:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CallbackQueue.js ***!
  \************************************************************************************************/
[8083, 6754, 6737, 6711],

/***/ 6754:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/PooledClass.js ***!
  \**********************************************************************************************/
[8084, 6711],

/***/ 6755:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Transaction.js ***!
  \**********************************************************************************************/
[8085, 6711],

/***/ 6756:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyObject.js ***!
  \*****************************************************************************************************/
417,

/***/ 6757:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/containsNode.js ***!
  \******************************************************************************************************/
[8086, 6758],

/***/ 6758:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isTextNode.js ***!
  \****************************************************************************************************/
[8087, 6759],

/***/ 6759:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isNode.js ***!
  \************************************************************************************************/
420,

/***/ 6760:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/instantiateReactComponent.js ***!
  \************************************************************************************************************/
[8088, 6761, 6766, 6767, 6737, 6711, 6723],

/***/ 6761:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCompositeComponent.js ***!
  \**********************************************************************************************************/
[8089, 6762, 6703, 6740, 6745, 6716, 6763, 6764, 6748, 6751, 6737, 6756, 6711, 6765, 6723],

/***/ 6762:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentEnvironment.js ***!
  \************************************************************************************************************/
[8090, 6711],

/***/ 6763:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocations.js ***!
  \*********************************************************************************************************/
[8091, 6715],

/***/ 6764:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*************************************************************************************************************/
425,

/***/ 6765:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/shouldUpdateReactComponent.js ***!
  \*************************************************************************************************************/
426,

/***/ 6766:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponent.js ***!
  \******************************************************************************************************/
[8092, 6740, 6742, 6748, 6737],

/***/ 6767:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNativeComponent.js ***!
  \*******************************************************************************************************/
[8093, 6737, 6711],

/***/ 6768:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/validateDOMNesting.js ***!
  \*****************************************************************************************************/
[8094, 6737, 6713, 6723],

/***/ 6769:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultInjection.js ***!
  \********************************************************************************************************/
[8095, 6770, 6778, 6781, 6782, 6783, 6707, 6787, 6788, 6724, 6790, 6791, 6704, 6816, 6819, 6743, 6726, 6823, 6828, 6829, 6830, 6839, 6840],

/***/ 6770:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/BeforeInputEventPlugin.js ***!
  \*********************************************************************************************************/
[8096, 6728, 6771, 6707, 6772, 6774, 6776, 6777],

/***/ 6771:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPropagators.js ***!
  \***************************************************************************************************/
[8097, 6728, 6729, 6723, 6733, 6734],

/***/ 6772:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/FallbackCompositionState.js ***!
  \***********************************************************************************************************/
[8098, 6754, 6737, 6773],

/***/ 6773:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getTextContentAccessor.js ***!
  \*********************************************************************************************************/
[8099, 6707],

/***/ 6774:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticCompositionEvent.js ***!
  \************************************************************************************************************/
[8100, 6775],

/***/ 6775:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticEvent.js ***!
  \*************************************************************************************************/
[8101, 6754, 6737, 6713, 6723],

/***/ 6776:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticInputEvent.js ***!
  \******************************************************************************************************/
[8102, 6775],

/***/ 6777:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyOf.js ***!
  \***********************************************************************************************/
438,

/***/ 6778:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ChangeEventPlugin.js ***!
  \****************************************************************************************************/
[8103, 6728, 6729, 6771, 6707, 6752, 6775, 6779, 6738, 6780, 6777],

/***/ 6779:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventTarget.js ***!
  \*************************************************************************************************/
440,

/***/ 6780:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isTextInputElement.js ***!
  \*****************************************************************************************************/
441,

/***/ 6781:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ClientReactRootIndex.js ***!
  \*******************************************************************************************************/
442,

/***/ 6782:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DefaultEventPluginOrder.js ***!
  \**********************************************************************************************************/
[8104, 6777],

/***/ 6783:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EnterLeaveEventPlugin.js ***!
  \********************************************************************************************************/
[8105, 6728, 6771, 6784, 6726, 6777],

/***/ 6784:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticMouseEvent.js ***!
  \******************************************************************************************************/
[8106, 6785, 6736, 6786],

/***/ 6785:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticUIEvent.js ***!
  \***************************************************************************************************/
[8107, 6775, 6779],

/***/ 6786:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventModifierState.js ***!
  \********************************************************************************************************/
447,

/***/ 6787:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \********************************************************************************************************/
[8108, 6721, 6707],

/***/ 6788:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*************************************************************************************************************/
[8109, 6745, 6789, 6723],

/***/ 6789:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/findDOMNode.js ***!
  \**********************************************************************************************/
[8110, 6703, 6745, 6726, 6711, 6723],

/***/ 6790:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \***************************************************************************************************************/
[8111, 6752, 6755, 6737, 6713],

/***/ 6791:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMComponent.js ***!
  \****************************************************************************************************/
[8112, 6792, 6794, 6721, 6720, 6728, 6727, 6724, 6802, 6803, 6807, 6810, 6811, 6726, 6812, 6716, 6751, 6737, 6741, 6719, 6711, 6738, 6777, 6717, 6718, 6815, 6768, 6723],

/***/ 6792:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/AutoFocusUtils.js ***!
  \*************************************************************************************************/
[8113, 6726, 6789, 6793],

/***/ 6793:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/focusNode.js ***!
  \***************************************************************************************************/
454,

/***/ 6794:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSPropertyOperations.js ***!
  \********************************************************************************************************/
[8114, 6795, 6707, 6716, 6796, 6798, 6799, 6801, 6723],

/***/ 6795:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSProperty.js ***!
  \**********************************************************************************************/
456,

/***/ 6796:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***********************************************************************************************************/
[8115, 6797],

/***/ 6797:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelize.js ***!
  \**************************************************************************************************/
458,

/***/ 6798:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/dangerousStyleValue.js ***!
  \******************************************************************************************************/
[8116, 6795],

/***/ 6799:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \************************************************************************************************************/
[8117, 6800],

/***/ 6800:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenate.js ***!
  \***************************************************************************************************/
461,

/***/ 6801:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***********************************************************************************************************/
462,

/***/ 6802:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMButton.js ***!
  \*************************************************************************************************/
463,

/***/ 6803:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMInput.js ***!
  \************************************************************************************************/
[8118, 6725, 6804, 6726, 6752, 6737, 6711],

/***/ 6804:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/LinkedValueUtils.js ***!
  \***************************************************************************************************/
[8119, 6805, 6763, 6711, 6723],

/***/ 6805:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypes.js ***!
  \*************************************************************************************************/
[8120, 6740, 6764, 6713, 6806],

/***/ 6806:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getIteratorFn.js ***!
  \************************************************************************************************/
467,

/***/ 6807:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMOption.js ***!
  \*************************************************************************************************/
[8121, 6808, 6810, 6737, 6723],

/***/ 6808:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildren.js ***!
  \************************************************************************************************/
[8122, 6754, 6740, 6713, 6809],

/***/ 6809:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/traverseAllChildren.js ***!
  \******************************************************************************************************/
[8123, 6703, 6740, 6743, 6806, 6711, 6723],

/***/ 6810:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelect.js ***!
  \*************************************************************************************************/
[8124, 6804, 6726, 6752, 6737, 6723],

/***/ 6811:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextarea.js ***!
  \***************************************************************************************************/
[8125, 6804, 6725, 6752, 6737, 6711, 6723],

/***/ 6812:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChild.js ***!
  \**************************************************************************************************/
[8126, 6762, 6714, 6703, 6748, 6813, 6814],

/***/ 6813:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildReconciler.js ***!
  \*******************************************************************************************************/
[8127, 6748, 6760, 6765, 6809, 6723],

/***/ 6814:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/flattenChildren.js ***!
  \**************************************************************************************************/
[8128, 6809, 6723],

/***/ 6815:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/shallowEqual.js ***!
  \******************************************************************************************************/
476,

/***/ 6816:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventListener.js ***!
  \*****************************************************************************************************/
[8129, 6817, 6707, 6754, 6743, 6726, 6752, 6737, 6779, 6818],

/***/ 6817:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/EventListener.js ***!
  \*******************************************************************************************************/
[8130, 6713],

/***/ 6818:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \********************************************************************************************************************/
479,

/***/ 6819:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInjection.js ***!
  \*************************************************************************************************/
[8131, 6721, 6729, 6762, 6820, 6766, 6727, 6767, 6716, 6744, 6752],

/***/ 6820:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactClass.js ***!
  \*********************************************************************************************/
[8132, 6821, 6740, 6763, 6764, 6822, 6737, 6756, 6711, 6715, 6777, 6723],

/***/ 6821:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponent.js ***!
  \*************************************************************************************************/
[8133, 6822, 6741, 6756, 6711, 6723],

/***/ 6822:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*******************************************************************************************************/
[8134, 6723],

/***/ 6823:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconcileTransaction.js ***!
  \************************************************************************************************************/
[8135, 6753, 6754, 6727, 6739, 6824, 6755, 6737],

/***/ 6824:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInputSelection.js ***!
  \******************************************************************************************************/
[8136, 6825, 6757, 6793, 6827],

/***/ 6825:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelection.js ***!
  \****************************************************************************************************/
[8137, 6707, 6826, 6773],

/***/ 6826:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getNodeForCharacterOffset.js ***!
  \************************************************************************************************************/
487,

/***/ 6827:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**********************************************************************************************************/
488,

/***/ 6828:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SelectEventPlugin.js ***!
  \****************************************************************************************************/
[8138, 6728, 6771, 6707, 6824, 6775, 6827, 6780, 6777, 6815],

/***/ 6829:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ServerReactRootIndex.js ***!
  \*******************************************************************************************************/
490,

/***/ 6830:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SimpleEventPlugin.js ***!
  \****************************************************************************************************/
[8139, 6728, 6817, 6771, 6726, 6831, 6775, 6832, 6833, 6784, 6836, 6837, 6785, 6838, 6713, 6834, 6711, 6777],

/***/ 6831:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticClipboardEvent.js ***!
  \**********************************************************************************************************/
[8140, 6775],

/***/ 6832:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticFocusEvent.js ***!
  \******************************************************************************************************/
[8141, 6785],

/***/ 6833:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*********************************************************************************************************/
[8142, 6785, 6834, 6835, 6786],

/***/ 6834:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventCharCode.js ***!
  \***************************************************************************************************/
495,

/***/ 6835:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventKey.js ***!
  \**********************************************************************************************/
[8143, 6834],

/***/ 6836:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticDragEvent.js ***!
  \*****************************************************************************************************/
[8144, 6784],

/***/ 6837:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticTouchEvent.js ***!
  \******************************************************************************************************/
[8145, 6785, 6786],

/***/ 6838:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticWheelEvent.js ***!
  \******************************************************************************************************/
[8146, 6784],

/***/ 6839:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*******************************************************************************************************/
[8147, 6721],

/***/ 6840:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerf.js ***!
  \***************************************************************************************************/
[8148, 6721, 6841, 6726, 6716, 6842],

/***/ 6841:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***********************************************************************************************************/
[8149, 6737],

/***/ 6842:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performanceNow.js ***!
  \********************************************************************************************************/
[8150, 6843],

/***/ 6843:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performance.js ***!
  \*****************************************************************************************************/
[8151, 6707],

/***/ 6844:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactVersion.js ***!
  \***********************************************************************************************/
505,

/***/ 6845:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*************************************************************************************************************/
[8152, 6726],

/***/ 6846:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMServer.js ***!
  \*************************************************************************************************/
[8153, 6769, 6847, 6844],

/***/ 6847:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRendering.js ***!
  \*******************************************************************************************************/
[8154, 6790, 6740, 6743, 6746, 6848, 6849, 6752, 6756, 6760, 6711],

/***/ 6848:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerBatchingStrategy.js ***!
  \**************************************************************************************************************/
509,

/***/ 6849:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRenderingTransaction.js ***!
  \******************************************************************************************************************/
[8155, 6754, 6753, 6755, 6737, 6713],

/***/ 6850:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactIsomorphic.js ***!
  \**************************************************************************************************/
[8156, 6808, 6821, 6820, 6851, 6740, 6852, 6805, 6844, 6737, 6854],

/***/ 6851:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFactories.js ***!
  \****************************************************************************************************/
[8157, 6740, 6852, 6853],

/***/ 6852:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElementValidator.js ***!
  \********************************************************************************************************/
[8158, 6740, 6763, 6764, 6703, 6741, 6806, 6711, 6723],

/***/ 6853:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/mapObject.js ***!
  \***************************************************************************************************/
514,

/***/ 6854:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/onlyChild.js ***!
  \********************************************************************************************/
[8159, 6740, 6711],

/***/ 6855:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/deprecated.js ***!
  \*********************************************************************************************/
[8160, 6737, 6723],

/***/ 6856:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./ebi-visual-species.css */ 6857);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6857:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Taken from: https://www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css */\n\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    font-size: 100%;\n    color: inherit;\n    content: attr(data-icon);\n    margin: 0 0.3em 0 0\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6858:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
1684,

/***/ 6859:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[8567, 6700, 6860, 6699],

/***/ 6860:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react-dom/index.js ***!
  \****************************************************************************************/
[7844, 6702],

/***/ 6861:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 6862);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6862:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults, table.gxaDifferentialFacetedSearchResults td {\n    border: none;\n    width: inherit;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    background: transparent;\n    text-align: center;\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd;\n    text-align: center;\n    font-size: 90%;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6863:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 6199);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 6864);
	
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

/***/ 6864:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 6865);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 6382)(content, {});
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

/***/ 6865:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 6381)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 0px;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 0px;\n}\n\n.gxaFacetsContainer h3 {\n    padding-left: 0;\n    margin: 0 0 20px 0;\n    font-size: 1.6rem;\n}\n\n.filterTitle {\n    color: #195454\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n/* Responsive */\n@media print, screen and (min-width: 40em) {\n    .gxaFacetsContainer .gxaFacetItem h4 {\n        /*font-weight: bold;*/\n        color: #333;\n        font-size: 100%;\n        padding: 0;\n    }\n}\n\n.gxaFacetsContainer h5 {\n    font-size: 1.2rem;\n    color: rgba(34,34,34,0.84);\n}\n\n.gxaFacetsContainer input {\n    margin: 0 0.5em 0.5em 0;\n    /*vertical-align: middle;*/\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n\n.gxaFacetItem li {\n    font-size: 80%;\n}\n\n#gxaDifferentialFacetsContainerDiv {\n    float:left;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6866:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 179);
	var QueryString = __webpack_require__(/*! querystring */ 2871);
	
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