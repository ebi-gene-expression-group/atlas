var expressionAtlasDifferentialExpression =
webpackJsonp_name_([5],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */5669);


/***/ },

/***/ 2695:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/querystring-es3/index.js ***!
  \****************************************************************/
[7425, 2696, 2697],

/***/ 2696:
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

/***/ 2697:
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

/***/ 5669:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 5670);

/***/ },

/***/ 5670:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 5841);
	
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

/***/ 5671:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[7295, 5672],

/***/ 5672:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[7296, 5673, 5674, 5686, 5689, 5690, 5695, 5678, 5700, 5701, 5702, 5680, 5696],

/***/ 5673:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/object-assign/index.js ***!
  \********************************************************************************/
5,

/***/ 5674:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[7297, 5675, 5678, 5681, 5683],

/***/ 5675:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[7298, 5676, 5677],

/***/ 5676:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/reactProdInvariant.js ***!
  \*********************************************************************************/
8,

/***/ 5677:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/invariant.js ***!
  \*******************************************************************************/
9,

/***/ 5678:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[7299, 5673, 5679, 5680, 5682],

/***/ 5679:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
11,

/***/ 5680:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/warning.js ***!
  \*****************************************************************************/
[7300, 5681],

/***/ 5681:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyFunction.js ***!
  \***********************************************************************************/
13,

/***/ 5682:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
14,

/***/ 5683:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[7301, 5676, 5679, 5678, 5684, 5677, 5685, 5680],

/***/ 5684:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
16,

/***/ 5685:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/KeyEscapeUtils.js ***!
  \*****************************************************************************/
17,

/***/ 5686:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[7302, 5676, 5687, 5682, 5688, 5677, 5680],

/***/ 5687:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[7303, 5680],

/***/ 5688:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/emptyObject.js ***!
  \*********************************************************************************/
20,

/***/ 5689:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPureComponent.js ***!
  \*********************************************************************************/
[7304, 5673, 5686, 5687, 5688],

/***/ 5690:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[7305, 5676, 5673, 5686, 5678, 5691, 5693, 5687, 5688, 5677, 5692, 5694, 5680],

/***/ 5691:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[7306, 5692],

/***/ 5692:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyMirror.js ***!
  \*******************************************************************************/
[7307, 5677],

/***/ 5693:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
25,

/***/ 5694:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/keyOf.js ***!
  \***************************************************************************/
26,

/***/ 5695:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[7308, 5678, 5696],

/***/ 5696:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[7309, 5679, 5697, 5678, 5691, 5698, 5682, 5684, 5680],

/***/ 5697:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentTreeHook.js ***!
  \*************************************************************************************/
[7310, 5676, 5679, 5677, 5680],

/***/ 5698:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/checkReactTypeSpec.js ***!
  \*********************************************************************************/
[7311, 5676, 5693, 5699, 5677, 5680, 5697, 5697],

/***/ 5699:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypesSecret.js ***!
  \***********************************************************************************/
32,

/***/ 5700:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[7312, 5678, 5693, 5699, 5681, 5684, 5680],

/***/ 5701:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
34,

/***/ 5702:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[7313, 5676, 5678, 5677],

/***/ 5703:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[7316, 5704],

/***/ 5704:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[7317, 5705, 5708, 5831, 5728, 5725, 5701, 5836, 5837, 5838, 5680, 5718, 5731, 5839, 5840],

/***/ 5705:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentTree.js ***!
  \************************************************************************************/
[7318, 5676, 5706, 5707, 5677],

/***/ 5706:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[7319, 5676, 5677],

/***/ 5707:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponentFlags.js ***!
  \*************************************************************************************/
42,

/***/ 5708:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[7320, 5709, 5724, 5742, 5743, 5748, 5749, 5763, 5705, 5802, 5803, 5804, 5805, 5806, 5809, 5810, 5818, 5819, 5820],

/***/ 5709:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[7321, 5710, 5711, 5718, 5719, 5721, 5723, 5694],

/***/ 5710:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[7322, 5692],

/***/ 5711:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[7323, 5710, 5712, 5714, 5716, 5717, 5680],

/***/ 5712:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[7324, 5676, 5713, 5714, 5715, 5716, 5717, 5677],

/***/ 5713:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[7325, 5676, 5677],

/***/ 5714:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[7326, 5676, 5710, 5715, 5677, 5680],

/***/ 5715:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
50,

/***/ 5716:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[7327, 5676, 5677],

/***/ 5717:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
52,

/***/ 5718:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \******************************************************************************************/
53,

/***/ 5719:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[7328, 5673, 5675, 5720],

/***/ 5720:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[7329, 5718],

/***/ 5721:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[7330, 5722],

/***/ 5722:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[7331, 5673, 5675, 5681, 5680],

/***/ 5723:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[7332, 5722],

/***/ 5724:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[7333, 5710, 5712, 5711, 5718, 5705, 5725, 5722, 5739, 5740, 5741, 5694],

/***/ 5725:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[7334, 5676, 5673, 5726, 5675, 5727, 5728, 5738, 5677],

/***/ 5726:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[7335, 5676, 5673, 5675, 5677],

/***/ 5727:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactFeatureFlags.js ***!
  \********************************************************************************/
62,

/***/ 5728:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[7336, 5729, 5731, 5680],

/***/ 5729:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[7337, 5730],

/***/ 5730:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[7338, 5676, 5677],

/***/ 5731:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstrumentation.js ***!
  \***********************************************************************************/
[7339, 5732],

/***/ 5732:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDebugTool.js ***!
  \*****************************************************************************/
[7340, 5733, 5734, 5697, 5735, 5718, 5736, 5680],

/***/ 5733:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \**********************************************************************************************/
[7341, 5680],

/***/ 5734:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \********************************************************************************************/
69,

/***/ 5735:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \***********************************************************************************************/
[7342, 5697, 5680],

/***/ 5736:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performanceNow.js ***!
  \************************************************************************************/
[7343, 5737],

/***/ 5737:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/performance.js ***!
  \*********************************************************************************/
[7344, 5718],

/***/ 5738:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[7345, 5676, 5677],

/***/ 5739:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
74,

/***/ 5740:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[7346, 5718],

/***/ 5741:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
76,

/***/ 5742:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[7347, 5694],

/***/ 5743:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[7348, 5710, 5711, 5705, 5744, 5694],

/***/ 5744:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[7349, 5745, 5746, 5747],

/***/ 5745:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[7350, 5722, 5739],

/***/ 5746:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
81,

/***/ 5747:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
82,

/***/ 5748:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[7351, 5706],

/***/ 5749:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[7352, 5750, 5762],

/***/ 5750:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[7353, 5751, 5757, 5761, 5705, 5731, 5754, 5753, 5755],

/***/ 5751:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMLazyTree.js ***!
  \**************************************************************************/
[7354, 5752, 5753, 5754, 5755],

/***/ 5752:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMNamespaces.js ***!
  \****************************************************************************/
87,

/***/ 5753:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[7355, 5718, 5752, 5754],

/***/ 5754:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \*************************************************************************************************/
89,

/***/ 5755:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[7356, 5718, 5756, 5753],

/***/ 5756:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
91,

/***/ 5757:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[7357, 5676, 5751, 5718, 5758, 5681, 5677],

/***/ 5758:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*******************************************************************************************/
[7358, 5718, 5759, 5760, 5677],

/***/ 5759:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \******************************************************************************************/
[7359, 5677],

/***/ 5760:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \***********************************************************************************/
[7360, 5718, 5677],

/***/ 5761:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[7361, 5692],

/***/ 5762:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[7362, 5750, 5705],

/***/ 5763:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[7363, 5676, 5673, 5764, 5766, 5751, 5752, 5706, 5774, 5710, 5712, 5713, 5776, 5779, 5707, 5705, 5781, 5783, 5784, 5785, 5731, 5786, 5798, 5681, 5756, 5677, 5740, 5694, 5793, 5801, 5680],

/***/ 5764:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[7364, 5705, 5765],

/***/ 5765:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/focusNode.js ***!
  \*******************************************************************************/
100,

/***/ 5766:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[7365, 5767, 5718, 5731, 5768, 5770, 5771, 5773, 5680],

/***/ 5767:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
102,

/***/ 5768:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***************************************************************************************/
[7366, 5769],

/***/ 5769:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/camelize.js ***!
  \******************************************************************************/
104,

/***/ 5770:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[7367, 5767, 5680],

/***/ 5771:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \****************************************************************************************/
[7368, 5772],

/***/ 5772:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/hyphenate.js ***!
  \*******************************************************************************/
107,

/***/ 5773:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***************************************************************************************/
108,

/***/ 5774:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[7369, 5706, 5705, 5731, 5775, 5680],

/***/ 5775:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[7370, 5756],

/***/ 5776:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[7371, 5673, 5710, 5713, 5777, 5746, 5778, 5740],

/***/ 5777:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[7372, 5712],

/***/ 5778:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getVendorPrefixedEventName.js ***!
  \*****************************************************************************************/
[7373, 5718],

/***/ 5779:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
[7374, 5780],

/***/ 5780:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DisabledInputUtils.js ***!
  \*********************************************************************************/
115,

/***/ 5781:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[7375, 5676, 5673, 5780, 5774, 5782, 5705, 5725, 5677, 5680],

/***/ 5782:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[7376, 5676, 5700, 5691, 5699, 5677, 5680],

/***/ 5783:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[7377, 5673, 5674, 5705, 5784, 5680],

/***/ 5784:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[7378, 5673, 5780, 5782, 5705, 5725, 5680],

/***/ 5785:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[7379, 5676, 5673, 5780, 5782, 5705, 5725, 5677, 5680],

/***/ 5786:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[7380, 5676, 5787, 5788, 5731, 5761, 5679, 5728, 5789, 5681, 5797, 5677],

/***/ 5787:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[7381, 5676, 5677],

/***/ 5788:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
123,

/***/ 5789:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[7382, 5728, 5790, 5685, 5794, 5683, 5680, 5697, 5697],

/***/ 5790:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[7383, 5676, 5673, 5791, 5795, 5796, 5677, 5680],

/***/ 5791:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[7384, 5676, 5673, 5787, 5679, 5678, 5715, 5788, 5731, 5792, 5691, 5728, 5698, 5688, 5677, 5793, 5794, 5680],

/***/ 5792:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNodeTypes.js ***!
  \*****************************************************************************/
[7385, 5676, 5678, 5677],

/***/ 5793:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/shallowEqual.js ***!
  \**********************************************************************************/
128,

/***/ 5794:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
129,

/***/ 5795:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
130,

/***/ 5796:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactHostComponent.js ***!
  \*********************************************************************************/
[7386, 5676, 5673, 5677],

/***/ 5797:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[7387, 5685, 5683, 5680, 5697, 5697],

/***/ 5798:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[7388, 5673, 5675, 5738, 5731, 5799],

/***/ 5799:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerUpdateQueue.js ***!
  \*************************************************************************************/
[7389, 5800, 5738, 5680],

/***/ 5800:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[7390, 5676, 5679, 5788, 5731, 5725, 5677, 5680],

/***/ 5801:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[7391, 5673, 5681, 5680],

/***/ 5802:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMEmptyComponent.js ***!
  \*************************************************************************************/
[7392, 5673, 5751, 5705],

/***/ 5803:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTreeTraversal.js ***!
  \************************************************************************************/
[7393, 5676, 5677],

/***/ 5804:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[7394, 5676, 5673, 5750, 5751, 5705, 5756, 5677, 5801],

/***/ 5805:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[7395, 5673, 5725, 5738, 5681],

/***/ 5806:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[7396, 5673, 5807, 5718, 5675, 5705, 5725, 5739, 5808],

/***/ 5807:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/EventListener.js ***!
  \***********************************************************************************/
[7397, 5681],

/***/ 5808:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \************************************************************************************************/
143,

/***/ 5809:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[7398, 5706, 5712, 5714, 5787, 5690, 5795, 5776, 5796, 5725],

/***/ 5810:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[7399, 5673, 5726, 5675, 5776, 5811, 5731, 5738, 5800],

/***/ 5811:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[7400, 5812, 5814, 5765, 5817],

/***/ 5812:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[7401, 5718, 5813, 5720],

/***/ 5813:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
148,

/***/ 5814:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/containsNode.js ***!
  \**********************************************************************************/
[7402, 5815],

/***/ 5815:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isTextNode.js ***!
  \********************************************************************************/
[7403, 5816],

/***/ 5816:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/isNode.js ***!
  \****************************************************************************/
151,

/***/ 5817:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**************************************************************************************/
152,

/***/ 5818:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
153,

/***/ 5819:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[7404, 5710, 5711, 5718, 5705, 5811, 5722, 5817, 5741, 5694, 5793],

/***/ 5820:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[7405, 5676, 5710, 5807, 5711, 5705, 5821, 5822, 5722, 5823, 5824, 5744, 5827, 5828, 5829, 5745, 5830, 5681, 5825, 5677, 5694],

/***/ 5821:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticAnimationEvent.js ***!
  \**************************************************************************************/
[7406, 5722],

/***/ 5822:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[7407, 5722],

/***/ 5823:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[7408, 5745],

/***/ 5824:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[7409, 5745, 5825, 5826, 5747],

/***/ 5825:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
160,

/***/ 5826:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[7410, 5825],

/***/ 5827:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[7411, 5744],

/***/ 5828:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[7412, 5745, 5747],

/***/ 5829:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTransitionEvent.js ***!
  \***************************************************************************************/
[7413, 5722],

/***/ 5830:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[7414, 5744],

/***/ 5831:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[7415, 5676, 5751, 5706, 5776, 5679, 5705, 5832, 5833, 5678, 5727, 5788, 5731, 5834, 5728, 5800, 5725, 5688, 5790, 5677, 5753, 5794, 5680],

/***/ 5832:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMContainerInfo.js ***!
  \************************************************************************************/
[7416, 5801],

/***/ 5833:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
168,

/***/ 5834:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[7417, 5835],

/***/ 5835:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
170,

/***/ 5836:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[7418, 5676, 5679, 5705, 5788, 5837, 5677, 5680],

/***/ 5837:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getHostComponentFromComposite.js ***!
  \********************************************************************************************/
[7419, 5792],

/***/ 5838:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[7420, 5831],

/***/ 5839:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \******************************************************************************************/
[7421, 5706, 5713, 5697, 5680],

/***/ 5840:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \*********************************************************************************************/
[7422, 5697, 5680],

/***/ 5841:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 5671);
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 179);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 5843);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 6335);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 6338);
	
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

/***/ 5842:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
836,

/***/ 5843:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery.browser */ 5844);
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 5845);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 5848).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 5864);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 5876);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 5879);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 5884);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 6170).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 6333);
	
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

/***/ 5844:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[7820, 5842],

/***/ 5845:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 5846);

/***/ },

/***/ 5846:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery-ui-bundle */ 5847);
	
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

/***/ 5847:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[7819, 5842],

/***/ 5848:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 5849);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 5861);

/***/ },

/***/ 5849:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 5850);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 5855);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 5859);
	
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

/***/ 5850:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	
	__webpack_require__(/*! ./gxaGradient.css */ 5851);
	
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

/***/ 5851:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 5852);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5852:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 5853:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
234,

/***/ 5854:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
235,

/***/ 5855:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/index.js ***!
  \*******************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 5856);

/***/ },

/***/ 5856:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery-ui-bundle */ 5847);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 5857);
	
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

/***/ 5857:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../css-loader!./gxaHelpTooltip.css */ 5858);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5858:
/*!**************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \**************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5859:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 5860);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5860:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5861:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 5850);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 5862).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 5855);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 5859);
	
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

/***/ 5862:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[7992, 5863],

/***/ 5863:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
[7993, 5671],

/***/ 5864:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 5865);

/***/ },

/***/ 5865:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 5866);
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery-ui-bundle */ 5847);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 5870).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 5872);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 5874);
	
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

/***/ 5866:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
[7823, 5867],

/***/ 5867:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[7824, 5708, 5868, 5701],

/***/ 5868:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[7825, 5676, 5832, 5805, 5678, 5731, 5834, 5728, 5869, 5798, 5725, 5688, 5790, 5677],

/***/ 5869:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
1213,

/***/ 5870:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/index.js ***!
  \******************************************************************************************************************************/
[7992, 5871],

/***/ 5871:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \******************************************************************************************************************************************/
[7993, 5671],

/***/ 5872:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 5873);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5873:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5874:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 5875);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5875:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5876:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery-ui-bundle */ 5847);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOM = __webpack_require__(/*! react-dom */ 5703);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 5877);
	
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

/***/ 5877:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 5878);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5878:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5879:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 5880);

/***/ },

/***/ 5880:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 5866);
	
	var $ = __webpack_require__(/*! jquery */ 5842);
	__webpack_require__(/*! jquery-ui-bundle */ 5847);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 5881);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 5882);
	
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

/***/ 5881:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 5671);
	
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

/***/ 5882:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 5883);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 5883:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    position: relative;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5884:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[7430, 5885],

/***/ 5885:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[7431, 5671, 5886, 5888, 5889, 5896, 5995, 5997, 6002, 6003, 6168],

/***/ 5886:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/react-localstorage.js ***!
  \**********************************************************************************************************************/
[7432, 5671, 5887],

/***/ 5887:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-localstorage/lib/warning.js ***!
  \***************************************************************************************************************/
241,

/***/ 5888:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-timer-mixin/TimerMixin.js ***!
  \*************************************************************************************************************/
242,

/***/ 5889:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-addons-css-transition-group/index.js ***!
  \************************************************************************************************************************/
[7433, 5890],

/***/ 5890:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[7434, 5673, 5672, 5891, 5893],

/***/ 5891:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[7435, 5673, 5672, 5788, 5892, 5681],

/***/ 5892:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[7436, 5797],

/***/ 5893:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[7437, 5672, 5704, 5894, 5895, 5702],

/***/ 5894:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/~/fbjs/lib/CSSCore.js ***!
  \*****************************************************************************/
[7438, 5677],

/***/ 5895:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[7439, 5718, 5778],

/***/ 5896:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Button.js ***!
  \***********************************************************************************************************/
[7440, 5897, 5932, 5933, 5940, 5941, 5977, 5985, 5671, 5986, 5988, 5993, 5994],

/***/ 5897:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \**************************************************************************************************************************************/
[7441, 5898],

/***/ 5898:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \***************************************************************************************************************************************************/
[7442, 5899, 5902],

/***/ 5899:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \************************************************************************************************************************************************************/
[7443, 5900, 5915],

/***/ 5900:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \**************************************************************************************************************************************************/
[7444, 5901, 5902, 5903, 5905],

/***/ 5901:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \**************************************************************************************************************************************************/
255,

/***/ 5902:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \************************************************************************************************************************************************/
256,

/***/ 5903:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \***********************************************************************************************************************************************/
[7445, 5904],

/***/ 5904:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \******************************************************************************************************************************************************/
258,

/***/ 5905:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \************************************************************************************************************************************************/
[7446, 5906, 5914, 5910],

/***/ 5906:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*****************************************************************************************************************************************************/
[7447, 5907, 5909, 5913, 5910],

/***/ 5907:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*****************************************************************************************************************************************************/
[7448, 5908],

/***/ 5908:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*****************************************************************************************************************************************************/
262,

/***/ 5909:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \**********************************************************************************************************************************************************/
[7449, 5910, 5911, 5912],

/***/ 5910:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*******************************************************************************************************************************************************/
[7450, 5911],

/***/ 5911:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \*************************************************************************************************************************************************/
265,

/***/ 5912:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \******************************************************************************************************************************************************/
[7451, 5908, 5901],

/***/ 5913:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \********************************************************************************************************************************************************/
[7452, 5908],

/***/ 5914:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \*********************************************************************************************************************************************************/
268,

/***/ 5915:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \***********************************************************************************************************************************************************/
[7453, 5916, 5919, 5931],

/***/ 5916:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*******************************************************************************************************************************************************/
[7454, 5917, 5930],

/***/ 5917:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \****************************************************************************************************************************************************************/
[7455, 5918, 5919, 5923, 5927],

/***/ 5918:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \***********************************************************************************************************************************************/
272,

/***/ 5919:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \******************************************************************************************************************************************************/
[7456, 5920, 5922],

/***/ 5920:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \***************************************************************************************************************************************************/
[7457, 5921],

/***/ 5921:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \***********************************************************************************************************************************************/
275,

/***/ 5922:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \***************************************************************************************************************************************************/
276,

/***/ 5923:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \**********************************************************************************************************************************************************/
[7458, 5919, 5924, 5926],

/***/ 5924:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*****************************************************************************************************************************************************/
[7459, 5925],

/***/ 5925:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \******************************************************************************************************************************************************/
279,

/***/ 5926:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \****************************************************************************************************************************************************/
[7460, 5925],

/***/ 5927:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \******************************************************************************************************************************************************/
[7461, 5928, 5929],

/***/ 5928:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \**************************************************************************************************************************************************/
[7462, 5901],

/***/ 5929:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \***********************************************************************************************************************************************/
283,

/***/ 5930:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \*********************************************************************************************************************************************************/
284,

/***/ 5931:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \******************************************************************************************************************************************************/
285,

/***/ 5932:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \************************************************************************************************************************************************/
286,

/***/ 5933:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \********************************************************************************************************************************/
[7463, 5934],

/***/ 5934:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \**************************************************************************************************************************************/
[7464, 5935],

/***/ 5935:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \***************************************************************************************************************************************************/
[7465, 5936, 5902],

/***/ 5936:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \************************************************************************************************************************************************************/
[7466, 5900, 5937],

/***/ 5937:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \*********************************************************************************************************************************************************/
[7467, 5916, 5938, 5931, 5939, 5920, 5911],

/***/ 5938:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*******************************************************************************************************************************************************/
292,

/***/ 5939:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*****************************************************************************************************************************************************/
[7468, 5922],

/***/ 5940:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \***************************************************************************************************************************************/
294,

/***/ 5941:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \**************************************************************************************************************************************************/
[7469, 5942],

/***/ 5942:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \*******************************************************************************************************************************/
[7470, 5943, 5963],

/***/ 5943:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \****************************************************************************************************************************************/
[7471, 5944],

/***/ 5944:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*****************************************************************************************************************************************************/
[7472, 5945, 5958, 5962],

/***/ 5945:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \**************************************************************************************************************************************************************/
[7473, 5946, 5947],

/***/ 5946:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*****************************************************************************************************************************************************/
[7474, 5925, 5922],

/***/ 5947:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*******************************************************************************************************************************************************/
[7475, 5948, 5900, 5949, 5905, 5918, 5950, 5951, 5955, 5957, 5956],

/***/ 5948:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \***************************************************************************************************************************************************/
302,

/***/ 5949:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \****************************************************************************************************************************************************/
[7476, 5905],

/***/ 5950:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*****************************************************************************************************************************************************/
304,

/***/ 5951:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*******************************************************************************************************************************************************/
[7477, 5952, 5914, 5955, 5905, 5956],

/***/ 5952:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \*********************************************************************************************************************************************************/
[7478, 5907, 5953, 5930, 5927, 5912, 5954],

/***/ 5953:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \******************************************************************************************************************************************************/
[7479, 5906, 5907, 5916, 5910],

/***/ 5954:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \************************************************************************************************************************************************/
[7480, 5901],

/***/ 5955:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \*************************************************************************************************************************************************************/
[7481, 5906, 5918, 5956],

/***/ 5956:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \***********************************************************************************************************************************************/
[7482, 5928, 5929, 5901],

/***/ 5957:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \******************************************************************************************************************************************************/
[7483, 5918, 5939, 5927],

/***/ 5958:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \***********************************************************************************************************************************************************/
[7484, 5959, 5901, 5905, 5950, 5956],

/***/ 5959:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \*************************************************************************************************************************************************************/
[7485, 5960, 5961, 5950, 5919, 5947],

/***/ 5960:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \**************************************************************************************************************************************************************/
314,

/***/ 5961:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*****************************************************************************************************************************************************/
315,

/***/ 5962:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \***************************************************************************************************************************************************/
[7486, 5956],

/***/ 5963:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \*******************************************************************************************************************************/
[7487, 5964],

/***/ 5964:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \**************************************************************************************************************************************************/
[7488, 5965, 5974, 5975, 5976, 5902],

/***/ 5965:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*****************************************************************************************************************************************************/
[7489, 5901, 5918, 5910, 5900, 5949, 5966, 5911, 5928, 5955, 5929, 5956, 5962, 5967, 5968, 5969, 5970, 5907, 5919, 5913, 5914, 5952, 5971, 5973, 5906, 5916, 5972, 5931, 5938, 5948, 5905],

/***/ 5966:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \************************************************************************************************************************************************/
[7490, 5929, 5908, 5918, 5906, 5911],

/***/ 5967:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \******************************************************************************************************************************************************/
[7491, 5901, 5902, 5948, 5962, 5906],

/***/ 5968:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \*************************************************************************************************************************************************/
[7492, 5916, 5919],

/***/ 5969:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*****************************************************************************************************************************************************/
[7493, 5916, 5938, 5931],

/***/ 5970:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \****************************************************************************************************************************************************/
[7494, 5921],

/***/ 5971:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \***********************************************************************************************************************************************************/
[7495, 5919, 5972],

/***/ 5972:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*******************************************************************************************************************************************************/
[7496, 5917, 5930],

/***/ 5973:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*******************************************************************************************************************************************************/
[7497, 5931, 5914, 5919, 5913, 5918, 5909, 5910],

/***/ 5974:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \***************************************************************************************************************************************************************/
328,

/***/ 5975:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \********************************************************************************************************************************************************************/
[7498, 5967],

/***/ 5976:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \****************************************************************************************************************************************************************/
[7499, 5967],

/***/ 5977:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \*********************************************************************************************************************************/
[7500, 5978, 5982, 5942],

/***/ 5978:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************/
[7501, 5979],

/***/ 5979:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*************************************************************************************************************************************************************/
[7502, 5980, 5902],

/***/ 5980:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \**********************************************************************************************************************************************************************/
[7503, 5900, 5981],

/***/ 5981:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*****************************************************************************************************************************************************/
[7504, 5908, 5907, 5903, 5973],

/***/ 5982:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \**************************************************************************************************************************************/
[7505, 5983],

/***/ 5983:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \***************************************************************************************************************************************************/
[7506, 5984, 5902],

/***/ 5984:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \************************************************************************************************************************************************************/
[7507, 5900, 5952],

/***/ 5985:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/classnames/index.js ***!
  \*******************************************************************************************************************/
339,

/***/ 5986:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \***********************************************************************************************************************************/
[7508, 5671, 5987],

/***/ 5987:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \********************************************************************************************************************************************************/
341,

/***/ 5988:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*************************************************************************************************************************/
[7509, 5989, 5933, 5992, 5671, 5993],

/***/ 5989:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \***************************************************************************************************************************************/
[7510, 5990],

/***/ 5990:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \****************************************************************************************************************************************************/
[7511, 5991, 5902],

/***/ 5991:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \*************************************************************************************************************************************************************/
[7512, 5900, 5915],

/***/ 5992:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/invariant/browser.js ***!
  \********************************************************************************************************************/
346,

/***/ 5993:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \**********************************************************************************************************************/
347,

/***/ 5994:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***************************************************************************************************************/
[7513, 5933, 5932, 5940, 5941, 5977, 5671, 5986],

/***/ 5995:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormGroup.js ***!
  \**************************************************************************************************************/
[7514, 5933, 5932, 5940, 5941, 5977, 5985, 5671, 5988, 5993, 5996],

/***/ 5996:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*********************************************************************************************************************************/
[7515, 5671],

/***/ 5997:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControl.js ***!
  \****************************************************************************************************************/
[7516, 5933, 5932, 5940, 5941, 5977, 5985, 5671, 5986, 5998, 5999, 6001, 5988],

/***/ 5998:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/~/warning/browser.js ***!
  \******************************************************************************************************************/
352,

/***/ 5999:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \************************************************************************************************************************/
[7517, 5932, 5933, 5940, 5941, 5977, 5985, 5671, 6000, 5988],

/***/ 6000:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/Glyphicon.js ***!
  \**************************************************************************************************************/
[7518, 5933, 5932, 5940, 5941, 5977, 5985, 5671, 5988],

/***/ 6001:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-bootstrap/lib/FormControlStatic.js ***!
  \**********************************************************************************************************************/
[7519, 5933, 5932, 5940, 5941, 5977, 5985, 5671, 5986, 5988],

/***/ 6002:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
356,

/***/ 6003:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/react-emojione.js ***!
  \******************************************************************************************************************/
[7520, 6004, 6005, 6165],

/***/ 6004:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*************************************************************************************************************************/
358,

/***/ 6005:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \******************************************************************************************************************************/
[7521, 6006, 6167],

/***/ 6006:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \****************************************************************************************************************************/
[7522, 6007, 6163, 6165],

/***/ 6007:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/react.js ***!
  \*************************************************************************************************************/
[7295, 6008],

/***/ 6008:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/React.js ***!
  \*****************************************************************************************************************/
[7523, 6009, 6153, 6157, 6044, 6162],

/***/ 6009:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOM.js ***!
  \********************************************************************************************************************/
[7524, 6010, 6011, 6076, 6050, 6033, 6023, 6055, 6059, 6151, 6096, 6152, 6030, 6014],

/***/ 6010:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************************************************/
364,

/***/ 6011:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************************************************/
[7525, 6012, 6027, 6031, 6033, 6044, 6026, 6025, 6075],

/***/ 6012:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************************************************/
[7526, 6013, 6021, 6023, 6024, 6025, 6018],

/***/ 6013:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Danger.js ***!
  \******************************************************************************************************************/
[7527, 6014, 6015, 6020, 6019, 6018],

/***/ 6014:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \***************************************************************************************************************************************/
368,

/***/ 6015:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \****************************************************************************************************************************************/
[7528, 6014, 6016, 6019, 6018],

/***/ 6016:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \***************************************************************************************************************************************/
[7529, 6017],

/***/ 6017:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/toArray.js ***!
  \**************************************************************************************************************************/
[7530, 6018],

/***/ 6018:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/invariant.js ***!
  \****************************************************************************************************************************/
372,

/***/ 6019:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \********************************************************************************************************************************/
[7531, 6014, 6018],

/***/ 6020:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyFunction.js ***!
  \********************************************************************************************************************************/
374,

/***/ 6021:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************************************************/
[7532, 6022],

/***/ 6022:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyMirror.js ***!
  \****************************************************************************************************************************/
[7533, 6018],

/***/ 6023:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPerf.js ***!
  \*********************************************************************************************************************/
377,

/***/ 6024:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************************************************/
[7534, 6014],

/***/ 6025:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/setTextContent.js ***!
  \**************************************************************************************************************************/
[7535, 6014, 6026, 6024],

/***/ 6026:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************************************************/
380,

/***/ 6027:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7536, 6028, 6023, 6029, 6030],

/***/ 6028:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************************************************/
[7537, 6018],

/***/ 6029:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************************************************/
[7538, 6026],

/***/ 6030:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/warning.js ***!
  \**************************************************************************************************************************/
[7539, 6020],

/***/ 6031:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************************************************/
[7540, 6032, 6033],

/***/ 6032:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************************************************/
[7541, 6012, 6027, 6033, 6023, 6018],

/***/ 6033:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMount.js ***!
  \**********************************************************************************************************************/
[7542, 6028, 6034, 6010, 6046, 6047, 6049, 6050, 6052, 6053, 6023, 6055, 6058, 6059, 6044, 6063, 6064, 6067, 6018, 6024, 6072, 6075, 6030],

/***/ 6034:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************************************************/
[7543, 6035, 6036, 6037, 6042, 6023, 6043, 6044, 6045],

/***/ 6035:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventConstants.js ***!
  \**************************************************************************************************************************/
[7544, 6022],

/***/ 6036:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************************************************/
[7545, 6037, 6038, 6039, 6040, 6041, 6018, 6030],

/***/ 6037:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************************************************/
[7546, 6018],

/***/ 6038:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************************************************/
[7547, 6035, 6039, 6018, 6030],

/***/ 6039:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************************************************/
393,

/***/ 6040:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************************************************/
[7548, 6018],

/***/ 6041:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************************************************/
395,

/***/ 6042:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************************************************/
[7549, 6036],

/***/ 6043:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************************************************/
397,

/***/ 6044:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Object.assign.js ***!
  \*************************************************************************************************************************/
398,

/***/ 6045:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************************************************/
[7550, 6014],

/***/ 6046:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************************************************/
400,

/***/ 6047:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElement.js ***!
  \************************************************************************************************************************/
[7551, 6010, 6044, 6048],

/***/ 6048:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************************************************/
402,

/***/ 6049:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \***************************************************************************************************************************************/
403,

/***/ 6050:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceHandles.js ***!
  \********************************************************************************************************************************/
[7552, 6051, 6018],

/***/ 6051:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRootIndex.js ***!
  \**************************************************************************************************************************/
405,

/***/ 6052:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************************************************/
406,

/***/ 6053:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************************************************/
[7553, 6054],

/***/ 6054:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/adler32.js ***!
  \*******************************************************************************************************************/
408,

/***/ 6055:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************************************************/
[7554, 6056],

/***/ 6056:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactRef.js ***!
  \********************************************************************************************************************/
[7555, 6057],

/***/ 6057:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************************************************/
[7556, 6018],

/***/ 6058:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************************************************/
[7557, 6010, 6047, 6052, 6059, 6044, 6018, 6030],

/***/ 6059:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************************************************/
[7558, 6060, 6061, 6023, 6055, 6062, 6044, 6018],

/***/ 6060:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************************************************/
[7559, 6061, 6044, 6018],

/***/ 6061:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/PooledClass.js ***!
  \***********************************************************************************************************************/
[7560, 6018],

/***/ 6062:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/Transaction.js ***!
  \***********************************************************************************************************************/
[7561, 6018],

/***/ 6063:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/emptyObject.js ***!
  \******************************************************************************************************************************/
417,

/***/ 6064:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/containsNode.js ***!
  \*******************************************************************************************************************************/
[7562, 6065],

/***/ 6065:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isTextNode.js ***!
  \*****************************************************************************************************************************/
[7563, 6066],

/***/ 6066:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/isNode.js ***!
  \*************************************************************************************************************************/
420,

/***/ 6067:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************************************************/
[7564, 6068, 6073, 6074, 6044, 6018, 6030],

/***/ 6068:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************************************************/
[7565, 6069, 6010, 6047, 6052, 6023, 6070, 6071, 6055, 6058, 6044, 6063, 6018, 6072, 6030],

/***/ 6069:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************************************************/
[7566, 6018],

/***/ 6070:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************************************************/
[7567, 6022],

/***/ 6071:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************************************************/
425,

/***/ 6072:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************************************************/
426,

/***/ 6073:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************************************************/
[7568, 6047, 6049, 6055, 6044],

/***/ 6074:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNativeComponent.js ***!
  \********************************************************************************************************************************/
[7569, 6044, 6018],

/***/ 6075:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************************************************/
[7570, 6044, 6020, 6030],

/***/ 6076:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************************************************/
[7571, 6077, 6085, 6088, 6089, 6090, 6014, 6094, 6095, 6031, 6097, 6098, 6011, 6123, 6126, 6050, 6033, 6130, 6135, 6136, 6137, 6146, 6147],

/***/ 6077:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************************************************/
[7572, 6035, 6078, 6014, 6079, 6081, 6083, 6084],

/***/ 6078:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************************************************/
[7573, 6035, 6036, 6030, 6040, 6041],

/***/ 6079:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************************************************/
[7574, 6061, 6044, 6080],

/***/ 6080:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************************************************/
[7575, 6014],

/***/ 6081:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************************************************/
[7576, 6082],

/***/ 6082:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************************************************/
[7577, 6061, 6044, 6020, 6030],

/***/ 6083:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************************************************/
[7578, 6082],

/***/ 6084:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/keyOf.js ***!
  \************************************************************************************************************************/
438,

/***/ 6085:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7579, 6035, 6036, 6078, 6014, 6059, 6082, 6086, 6045, 6087, 6084],

/***/ 6086:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************************************************/
440,

/***/ 6087:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************************************************/
441,

/***/ 6088:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ClientReactRootIndex.js ***!
  \********************************************************************************************************************************/
442,

/***/ 6089:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************************************************/
[7580, 6084],

/***/ 6090:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************************************************/
[7581, 6035, 6078, 6091, 6033, 6084],

/***/ 6091:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************************************************/
[7582, 6092, 6043, 6093],

/***/ 6092:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************************************************/
[7583, 6082, 6086],

/***/ 6093:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************************************************/
447,

/***/ 6094:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************************************************/
[7584, 6028, 6014],

/***/ 6095:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactBrowserComponentMixin.js ***!
  \**************************************************************************************************************************************/
[7585, 6052, 6096, 6030],

/***/ 6096:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************************************************/
[7586, 6010, 6052, 6033, 6018, 6030],

/***/ 6097:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************************************************/
[7587, 6059, 6062, 6044, 6020],

/***/ 6098:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************************************************/
[7588, 6099, 6101, 6028, 6027, 6035, 6034, 6031, 6109, 6110, 6114, 6117, 6118, 6033, 6119, 6023, 6058, 6044, 6048, 6026, 6018, 6045, 6084, 6024, 6025, 6122, 6075, 6030],

/***/ 6099:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************************************************/
[7589, 6033, 6096, 6100],

/***/ 6100:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/focusNode.js ***!
  \****************************************************************************************************************************/
454,

/***/ 6101:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************************************************/
[7590, 6102, 6014, 6023, 6103, 6105, 6106, 6108, 6030],

/***/ 6102:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************************************************/
456,

/***/ 6103:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \************************************************************************************************************************************/
[7591, 6104],

/***/ 6104:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/camelize.js ***!
  \***************************************************************************************************************************/
458,

/***/ 6105:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************************************************/
[7592, 6102],

/***/ 6106:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \*************************************************************************************************************************************/
[7593, 6107],

/***/ 6107:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/hyphenate.js ***!
  \****************************************************************************************************************************/
461,

/***/ 6108:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \************************************************************************************************************************************/
462,

/***/ 6109:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************************************************/
463,

/***/ 6110:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************************************************/
[7594, 6032, 6111, 6033, 6059, 6044, 6018],

/***/ 6111:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************************************************/
[7595, 6112, 6070, 6018, 6030],

/***/ 6112:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************************************************/
[7596, 6047, 6071, 6020, 6113],

/***/ 6113:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************************************************/
467,

/***/ 6114:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************************************************/
[7597, 6115, 6117, 6044, 6030],

/***/ 6115:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************************************************/
[7598, 6061, 6047, 6020, 6116],

/***/ 6116:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************************************************/
[7599, 6010, 6047, 6050, 6113, 6018, 6030],

/***/ 6117:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************************************************/
[7600, 6111, 6033, 6059, 6044, 6030],

/***/ 6118:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************************************************/
[7601, 6111, 6032, 6059, 6044, 6018, 6030],

/***/ 6119:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************************************************/
[7602, 6069, 6021, 6010, 6055, 6120, 6121],

/***/ 6120:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************************************************/
[7603, 6055, 6067, 6072, 6116, 6030],

/***/ 6121:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************************************************/
[7604, 6116, 6030],

/***/ 6122:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*******************************************************************************************************************************/
476,

/***/ 6123:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************************************************/
[7605, 6124, 6014, 6061, 6050, 6033, 6059, 6044, 6086, 6125],

/***/ 6124:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/EventListener.js ***!
  \********************************************************************************************************************************/
[7606, 6020],

/***/ 6125:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \*********************************************************************************************************************************************/
479,

/***/ 6126:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************************************************/
[7607, 6028, 6036, 6069, 6127, 6073, 6034, 6074, 6023, 6051, 6059],

/***/ 6127:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactClass.js ***!
  \**********************************************************************************************************************/
[7608, 6128, 6047, 6070, 6071, 6129, 6044, 6063, 6018, 6022, 6084, 6030],

/***/ 6128:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************************************************/
[7609, 6129, 6048, 6063, 6018, 6030],

/***/ 6129:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************************************************/
[7610, 6030],

/***/ 6130:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************************************************/
[7611, 6060, 6061, 6034, 6046, 6131, 6062, 6044],

/***/ 6131:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************************************************/
[7612, 6132, 6064, 6100, 6134],

/***/ 6132:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************************************************/
[7613, 6014, 6133, 6080],

/***/ 6133:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************************************************/
487,

/***/ 6134:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/getActiveElement.js ***!
  \***********************************************************************************************************************************/
488,

/***/ 6135:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7614, 6035, 6078, 6014, 6131, 6082, 6134, 6087, 6084, 6122],

/***/ 6136:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ServerReactRootIndex.js ***!
  \********************************************************************************************************************************/
490,

/***/ 6137:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************************************************/
[7615, 6035, 6124, 6078, 6033, 6138, 6082, 6139, 6140, 6091, 6143, 6144, 6092, 6145, 6020, 6141, 6018, 6084],

/***/ 6138:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************************************************/
[7616, 6082],

/***/ 6139:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************************************************/
[7617, 6092],

/***/ 6140:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************************************************/
[7618, 6092, 6141, 6142, 6093],

/***/ 6141:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************************************************/
495,

/***/ 6142:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/getEventKey.js ***!
  \***********************************************************************************************************************/
[7619, 6141],

/***/ 6143:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************************************************/
[7620, 6091],

/***/ 6144:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************************************************/
[7621, 6092, 6093],

/***/ 6145:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************************************************/
[7622, 6091],

/***/ 6146:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************************************************/
[7623, 6028],

/***/ 6147:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerf.js ***!
  \****************************************************************************************************************************/
[7624, 6028, 6148, 6033, 6023, 6149],

/***/ 6148:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \************************************************************************************************************************************/
[7625, 6044],

/***/ 6149:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performanceNow.js ***!
  \*********************************************************************************************************************************/
[7626, 6150],

/***/ 6150:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/performance.js ***!
  \******************************************************************************************************************************/
[7627, 6014],

/***/ 6151:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactVersion.js ***!
  \************************************************************************************************************************/
505,

/***/ 6152:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************************************************/
[7628, 6033],

/***/ 6153:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMServer.js ***!
  \**************************************************************************************************************************/
[7629, 6076, 6154, 6151],

/***/ 6154:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRendering.js ***!
  \********************************************************************************************************************************/
[7630, 6097, 6047, 6050, 6053, 6155, 6156, 6059, 6063, 6067, 6018],

/***/ 6155:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerBatchingStrategy.js ***!
  \***************************************************************************************************************************************/
509,

/***/ 6156:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************************************************/
[7631, 6061, 6060, 6062, 6044, 6020],

/***/ 6157:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactIsomorphic.js ***!
  \***************************************************************************************************************************/
[7632, 6115, 6128, 6127, 6158, 6047, 6159, 6112, 6151, 6044, 6161],

/***/ 6158:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************************************************/
[7633, 6047, 6159, 6160],

/***/ 6159:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************************************************/
[7634, 6047, 6070, 6071, 6010, 6048, 6113, 6018, 6030],

/***/ 6160:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/~/fbjs/lib/mapObject.js ***!
  \****************************************************************************************************************************/
514,

/***/ 6161:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/onlyChild.js ***!
  \*********************************************************************************************************************/
[7635, 6047, 6018],

/***/ 6162:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/~/react/lib/deprecated.js ***!
  \**********************************************************************************************************************/
[7636, 6044, 6030],

/***/ 6163:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**************************************************************************************************************************/
[7637, 6164],

/***/ 6164:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \************************************************************************************************************************************/
518,

/***/ 6165:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*********************************************************************************************************************************/
[7638, 6166],

/***/ 6166:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/data/emoji-data.js ***!
  \*******************************************************************************************************************/
520,

/***/ 6167:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \******************************************************************************************************************************/
[7639, 6165],

/***/ 6168:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
[7640, 6169, 5854],

/***/ 6169:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
[7641, 5853],

/***/ 6170:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[8029, 6171, 6331],

/***/ 6171:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[8030, 6172, 6328, 6330],

/***/ 6172:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/react.js ***!
  \************************************************************************************/
[7295, 6173],

/***/ 6173:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/React.js ***!
  \****************************************************************************************/
[7523, 6174, 6318, 6322, 6209, 6327],

/***/ 6174:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOM.js ***!
  \*******************************************************************************************/
[7524, 6175, 6176, 6241, 6215, 6198, 6188, 6220, 6224, 6316, 6261, 6317, 6195, 6179],

/***/ 6175:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCurrentOwner.js ***!
  \****************************************************************************************************/
364,

/***/ 6176:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextComponent.js ***!
  \********************************************************************************************************/
[7525, 6177, 6192, 6196, 6198, 6209, 6191, 6190, 6240],

/***/ 6177:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMChildrenOperations.js ***!
  \********************************************************************************************************/
[7526, 6178, 6186, 6188, 6189, 6190, 6183],

/***/ 6178:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Danger.js ***!
  \*****************************************************************************************/
[7527, 6179, 6180, 6185, 6184, 6183],

/***/ 6179:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**************************************************************************************************************/
368,

/***/ 6180:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***************************************************************************************************************/
[7528, 6179, 6181, 6184, 6183],

/***/ 6181:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \**************************************************************************************************************/
[7529, 6182],

/***/ 6182:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/toArray.js ***!
  \*************************************************************************************************/
[7530, 6183],

/***/ 6183:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/invariant.js ***!
  \***************************************************************************************************/
372,

/***/ 6184:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*******************************************************************************************************/
[7531, 6179, 6183],

/***/ 6185:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*******************************************************************************************************/
374,

/***/ 6186:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*************************************************************************************************************/
[7532, 6187],

/***/ 6187:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyMirror.js ***!
  \***************************************************************************************************/
[7533, 6183],

/***/ 6188:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPerf.js ***!
  \********************************************************************************************/
377,

/***/ 6189:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setInnerHTML.js ***!
  \***********************************************************************************************/
[7534, 6179],

/***/ 6190:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/setTextContent.js ***!
  \*************************************************************************************************/
[7535, 6179, 6191, 6189],

/***/ 6191:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/escapeTextContentForBrowser.js ***!
  \**************************************************************************************************************/
380,

/***/ 6192:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMPropertyOperations.js ***!
  \********************************************************************************************************/
[7536, 6193, 6188, 6194, 6195],

/***/ 6193:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DOMProperty.js ***!
  \**********************************************************************************************/
[7537, 6183],

/***/ 6194:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \****************************************************************************************************************/
[7538, 6191],

/***/ 6195:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/warning.js ***!
  \*************************************************************************************************/
[7539, 6185],

/***/ 6196:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*******************************************************************************************************************/
[7540, 6197, 6198],

/***/ 6197:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMIDOperations.js ***!
  \*******************************************************************************************************/
[7541, 6177, 6192, 6198, 6188, 6183],

/***/ 6198:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMount.js ***!
  \*********************************************************************************************/
[7542, 6193, 6199, 6175, 6211, 6212, 6214, 6215, 6217, 6218, 6188, 6220, 6223, 6224, 6209, 6228, 6229, 6232, 6183, 6189, 6237, 6240, 6195],

/***/ 6199:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***********************************************************************************************************/
[7543, 6200, 6201, 6202, 6207, 6188, 6208, 6209, 6210],

/***/ 6200:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventConstants.js ***!
  \*************************************************************************************************/
[7544, 6187],

/***/ 6201:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginHub.js ***!
  \*************************************************************************************************/
[7545, 6202, 6203, 6204, 6205, 6206, 6183, 6195],

/***/ 6202:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginRegistry.js ***!
  \******************************************************************************************************/
[7546, 6183],

/***/ 6203:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPluginUtils.js ***!
  \***************************************************************************************************/
[7547, 6200, 6204, 6183, 6195],

/***/ 6204:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactErrorUtils.js ***!
  \**************************************************************************************************/
393,

/***/ 6205:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/accumulateInto.js ***!
  \*************************************************************************************************/
[7548, 6183],

/***/ 6206:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/forEachAccumulated.js ***!
  \*****************************************************************************************************/
395,

/***/ 6207:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventEmitterMixin.js ***!
  \*********************************************************************************************************/
[7549, 6201],

/***/ 6208:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ViewportMetrics.js ***!
  \**************************************************************************************************/
397,

/***/ 6209:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Object.assign.js ***!
  \************************************************************************************************/
398,

/***/ 6210:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isEventSupported.js ***!
  \***************************************************************************************************/
[7550, 6179],

/***/ 6211:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*******************************************************************************************************/
400,

/***/ 6212:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElement.js ***!
  \***********************************************************************************************/
[7551, 6175, 6209, 6213],

/***/ 6213:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/canDefineProperty.js ***!
  \****************************************************************************************************/
402,

/***/ 6214:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \**************************************************************************************************************/
403,

/***/ 6215:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceHandles.js ***!
  \*******************************************************************************************************/
[7552, 6216, 6183],

/***/ 6216:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRootIndex.js ***!
  \*************************************************************************************************/
405,

/***/ 6217:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInstanceMap.js ***!
  \***************************************************************************************************/
406,

/***/ 6218:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMarkupChecksum.js ***!
  \******************************************************************************************************/
[7553, 6219],

/***/ 6219:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/adler32.js ***!
  \******************************************************************************************/
408,

/***/ 6220:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconciler.js ***!
  \**************************************************************************************************/
[7554, 6221],

/***/ 6221:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactRef.js ***!
  \*******************************************************************************************/
[7555, 6222],

/***/ 6222:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactOwner.js ***!
  \*********************************************************************************************/
[7556, 6183],

/***/ 6223:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdateQueue.js ***!
  \***************************************************************************************************/
[7557, 6175, 6212, 6217, 6224, 6209, 6183, 6195],

/***/ 6224:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactUpdates.js ***!
  \***********************************************************************************************/
[7558, 6225, 6226, 6188, 6220, 6227, 6209, 6183],

/***/ 6225:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CallbackQueue.js ***!
  \************************************************************************************************/
[7559, 6226, 6209, 6183],

/***/ 6226:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/PooledClass.js ***!
  \**********************************************************************************************/
[7560, 6183],

/***/ 6227:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/Transaction.js ***!
  \**********************************************************************************************/
[7561, 6183],

/***/ 6228:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/emptyObject.js ***!
  \*****************************************************************************************************/
417,

/***/ 6229:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/containsNode.js ***!
  \******************************************************************************************************/
[7562, 6230],

/***/ 6230:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isTextNode.js ***!
  \****************************************************************************************************/
[7563, 6231],

/***/ 6231:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/isNode.js ***!
  \************************************************************************************************/
420,

/***/ 6232:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/instantiateReactComponent.js ***!
  \************************************************************************************************************/
[7564, 6233, 6238, 6239, 6209, 6183, 6195],

/***/ 6233:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactCompositeComponent.js ***!
  \**********************************************************************************************************/
[7565, 6234, 6175, 6212, 6217, 6188, 6235, 6236, 6220, 6223, 6209, 6228, 6183, 6237, 6195],

/***/ 6234:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponentEnvironment.js ***!
  \************************************************************************************************************/
[7566, 6183],

/***/ 6235:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocations.js ***!
  \*********************************************************************************************************/
[7567, 6187],

/***/ 6236:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*************************************************************************************************************/
425,

/***/ 6237:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/shouldUpdateReactComponent.js ***!
  \*************************************************************************************************************/
426,

/***/ 6238:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEmptyComponent.js ***!
  \******************************************************************************************************/
[7568, 6212, 6214, 6220, 6209],

/***/ 6239:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNativeComponent.js ***!
  \*******************************************************************************************************/
[7569, 6209, 6183],

/***/ 6240:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/validateDOMNesting.js ***!
  \*****************************************************************************************************/
[7570, 6209, 6185, 6195],

/***/ 6241:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultInjection.js ***!
  \********************************************************************************************************/
[7571, 6242, 6250, 6253, 6254, 6255, 6179, 6259, 6260, 6196, 6262, 6263, 6176, 6288, 6291, 6215, 6198, 6295, 6300, 6301, 6302, 6311, 6312],

/***/ 6242:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/BeforeInputEventPlugin.js ***!
  \*********************************************************************************************************/
[7572, 6200, 6243, 6179, 6244, 6246, 6248, 6249],

/***/ 6243:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EventPropagators.js ***!
  \***************************************************************************************************/
[7573, 6200, 6201, 6195, 6205, 6206],

/***/ 6244:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/FallbackCompositionState.js ***!
  \***********************************************************************************************************/
[7574, 6226, 6209, 6245],

/***/ 6245:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getTextContentAccessor.js ***!
  \*********************************************************************************************************/
[7575, 6179],

/***/ 6246:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticCompositionEvent.js ***!
  \************************************************************************************************************/
[7576, 6247],

/***/ 6247:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticEvent.js ***!
  \*************************************************************************************************/
[7577, 6226, 6209, 6185, 6195],

/***/ 6248:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticInputEvent.js ***!
  \******************************************************************************************************/
[7578, 6247],

/***/ 6249:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/keyOf.js ***!
  \***********************************************************************************************/
438,

/***/ 6250:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ChangeEventPlugin.js ***!
  \****************************************************************************************************/
[7579, 6200, 6201, 6243, 6179, 6224, 6247, 6251, 6210, 6252, 6249],

/***/ 6251:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventTarget.js ***!
  \*************************************************************************************************/
440,

/***/ 6252:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/isTextInputElement.js ***!
  \*****************************************************************************************************/
441,

/***/ 6253:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ClientReactRootIndex.js ***!
  \*******************************************************************************************************/
442,

/***/ 6254:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/DefaultEventPluginOrder.js ***!
  \**********************************************************************************************************/
[7580, 6249],

/***/ 6255:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/EnterLeaveEventPlugin.js ***!
  \********************************************************************************************************/
[7581, 6200, 6243, 6256, 6198, 6249],

/***/ 6256:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticMouseEvent.js ***!
  \******************************************************************************************************/
[7582, 6257, 6208, 6258],

/***/ 6257:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticUIEvent.js ***!
  \***************************************************************************************************/
[7583, 6247, 6251],

/***/ 6258:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventModifierState.js ***!
  \********************************************************************************************************/
447,

/***/ 6259:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \********************************************************************************************************/
[7584, 6193, 6179],

/***/ 6260:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*************************************************************************************************************/
[7585, 6217, 6261, 6195],

/***/ 6261:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/findDOMNode.js ***!
  \**********************************************************************************************/
[7586, 6175, 6217, 6198, 6183, 6195],

/***/ 6262:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \***************************************************************************************************************/
[7587, 6224, 6227, 6209, 6185],

/***/ 6263:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMComponent.js ***!
  \****************************************************************************************************/
[7588, 6264, 6266, 6193, 6192, 6200, 6199, 6196, 6274, 6275, 6279, 6282, 6283, 6198, 6284, 6188, 6223, 6209, 6213, 6191, 6183, 6210, 6249, 6189, 6190, 6287, 6240, 6195],

/***/ 6264:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/AutoFocusUtils.js ***!
  \*************************************************************************************************/
[7589, 6198, 6261, 6265],

/***/ 6265:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/focusNode.js ***!
  \***************************************************************************************************/
454,

/***/ 6266:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSPropertyOperations.js ***!
  \********************************************************************************************************/
[7590, 6267, 6179, 6188, 6268, 6270, 6271, 6273, 6195],

/***/ 6267:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/CSSProperty.js ***!
  \**********************************************************************************************/
456,

/***/ 6268:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \***********************************************************************************************************/
[7591, 6269],

/***/ 6269:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/camelize.js ***!
  \**************************************************************************************************/
458,

/***/ 6270:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/dangerousStyleValue.js ***!
  \******************************************************************************************************/
[7592, 6267],

/***/ 6271:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \************************************************************************************************************/
[7593, 6272],

/***/ 6272:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/hyphenate.js ***!
  \***************************************************************************************************/
461,

/***/ 6273:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \***********************************************************************************************************/
462,

/***/ 6274:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMButton.js ***!
  \*************************************************************************************************/
463,

/***/ 6275:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMInput.js ***!
  \************************************************************************************************/
[7594, 6197, 6276, 6198, 6224, 6209, 6183],

/***/ 6276:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/LinkedValueUtils.js ***!
  \***************************************************************************************************/
[7595, 6277, 6235, 6183, 6195],

/***/ 6277:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactPropTypes.js ***!
  \*************************************************************************************************/
[7596, 6212, 6236, 6185, 6278],

/***/ 6278:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getIteratorFn.js ***!
  \************************************************************************************************/
467,

/***/ 6279:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMOption.js ***!
  \*************************************************************************************************/
[7597, 6280, 6282, 6209, 6195],

/***/ 6280:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildren.js ***!
  \************************************************************************************************/
[7598, 6226, 6212, 6185, 6281],

/***/ 6281:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/traverseAllChildren.js ***!
  \******************************************************************************************************/
[7599, 6175, 6212, 6215, 6278, 6183, 6195],

/***/ 6282:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelect.js ***!
  \*************************************************************************************************/
[7600, 6276, 6198, 6224, 6209, 6195],

/***/ 6283:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMTextarea.js ***!
  \***************************************************************************************************/
[7601, 6276, 6197, 6224, 6209, 6183, 6195],

/***/ 6284:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactMultiChild.js ***!
  \**************************************************************************************************/
[7602, 6234, 6186, 6175, 6220, 6285, 6286],

/***/ 6285:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactChildReconciler.js ***!
  \*******************************************************************************************************/
[7603, 6220, 6232, 6237, 6281, 6195],

/***/ 6286:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/flattenChildren.js ***!
  \**************************************************************************************************/
[7604, 6281, 6195],

/***/ 6287:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/shallowEqual.js ***!
  \******************************************************************************************************/
476,

/***/ 6288:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactEventListener.js ***!
  \*****************************************************************************************************/
[7605, 6289, 6179, 6226, 6215, 6198, 6224, 6209, 6251, 6290],

/***/ 6289:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/EventListener.js ***!
  \*******************************************************************************************************/
[7606, 6185],

/***/ 6290:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \********************************************************************************************************************/
479,

/***/ 6291:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInjection.js ***!
  \*************************************************************************************************/
[7607, 6193, 6201, 6234, 6292, 6238, 6199, 6239, 6188, 6216, 6224],

/***/ 6292:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactClass.js ***!
  \*********************************************************************************************/
[7608, 6293, 6212, 6235, 6236, 6294, 6209, 6228, 6183, 6187, 6249, 6195],

/***/ 6293:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactComponent.js ***!
  \*************************************************************************************************/
[7609, 6294, 6213, 6228, 6183, 6195],

/***/ 6294:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*******************************************************************************************************/
[7610, 6195],

/***/ 6295:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactReconcileTransaction.js ***!
  \************************************************************************************************************/
[7611, 6225, 6226, 6199, 6211, 6296, 6227, 6209],

/***/ 6296:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactInputSelection.js ***!
  \******************************************************************************************************/
[7612, 6297, 6229, 6265, 6299],

/***/ 6297:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMSelection.js ***!
  \****************************************************************************************************/
[7613, 6179, 6298, 6245],

/***/ 6298:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getNodeForCharacterOffset.js ***!
  \************************************************************************************************************/
487,

/***/ 6299:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/getActiveElement.js ***!
  \**********************************************************************************************************/
488,

/***/ 6300:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SelectEventPlugin.js ***!
  \****************************************************************************************************/
[7614, 6200, 6243, 6179, 6296, 6247, 6299, 6252, 6249, 6287],

/***/ 6301:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ServerReactRootIndex.js ***!
  \*******************************************************************************************************/
490,

/***/ 6302:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SimpleEventPlugin.js ***!
  \****************************************************************************************************/
[7615, 6200, 6289, 6243, 6198, 6303, 6247, 6304, 6305, 6256, 6308, 6309, 6257, 6310, 6185, 6306, 6183, 6249],

/***/ 6303:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticClipboardEvent.js ***!
  \**********************************************************************************************************/
[7616, 6247],

/***/ 6304:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticFocusEvent.js ***!
  \******************************************************************************************************/
[7617, 6257],

/***/ 6305:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*********************************************************************************************************/
[7618, 6257, 6306, 6307, 6258],

/***/ 6306:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventCharCode.js ***!
  \***************************************************************************************************/
495,

/***/ 6307:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/getEventKey.js ***!
  \**********************************************************************************************/
[7619, 6306],

/***/ 6308:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticDragEvent.js ***!
  \*****************************************************************************************************/
[7620, 6256],

/***/ 6309:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticTouchEvent.js ***!
  \******************************************************************************************************/
[7621, 6257, 6258],

/***/ 6310:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SyntheticWheelEvent.js ***!
  \******************************************************************************************************/
[7622, 6256],

/***/ 6311:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*******************************************************************************************************/
[7623, 6193],

/***/ 6312:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerf.js ***!
  \***************************************************************************************************/
[7624, 6193, 6313, 6198, 6188, 6314],

/***/ 6313:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***********************************************************************************************************/
[7625, 6209],

/***/ 6314:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performanceNow.js ***!
  \********************************************************************************************************/
[7626, 6315],

/***/ 6315:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/performance.js ***!
  \*****************************************************************************************************/
[7627, 6179],

/***/ 6316:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactVersion.js ***!
  \***********************************************************************************************/
505,

/***/ 6317:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*************************************************************************************************************/
[7628, 6198],

/***/ 6318:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMServer.js ***!
  \*************************************************************************************************/
[7629, 6241, 6319, 6316],

/***/ 6319:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRendering.js ***!
  \*******************************************************************************************************/
[7630, 6262, 6212, 6215, 6218, 6320, 6321, 6224, 6228, 6232, 6183],

/***/ 6320:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerBatchingStrategy.js ***!
  \**************************************************************************************************************/
509,

/***/ 6321:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactServerRenderingTransaction.js ***!
  \******************************************************************************************************************/
[7631, 6226, 6225, 6227, 6209, 6185],

/***/ 6322:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactIsomorphic.js ***!
  \**************************************************************************************************/
[7632, 6280, 6293, 6292, 6323, 6212, 6324, 6277, 6316, 6209, 6326],

/***/ 6323:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactDOMFactories.js ***!
  \****************************************************************************************************/
[7633, 6212, 6324, 6325],

/***/ 6324:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/ReactElementValidator.js ***!
  \********************************************************************************************************/
[7634, 6212, 6235, 6236, 6175, 6213, 6278, 6183, 6195],

/***/ 6325:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/~/fbjs/lib/mapObject.js ***!
  \***************************************************************************************************/
514,

/***/ 6326:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/onlyChild.js ***!
  \********************************************************************************************/
[7635, 6212, 6183],

/***/ 6327:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react/lib/deprecated.js ***!
  \*********************************************************************************************/
[7636, 6209, 6195],

/***/ 6328:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
[8031, 6329, 5854],

/***/ 6329:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
[8032, 5853],

/***/ 6330:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
1669,

/***/ 6331:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[8033, 6172, 6332, 6171],

/***/ 6332:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/~/react-dom/index.js ***!
  \****************************************************************************************/
[7316, 6174],

/***/ 6333:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 6334);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 6334:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6335:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 5671);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 6336);
	
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

/***/ 6336:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 6337);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 5854)(content, {});
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

/***/ 6337:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 5853)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 2px 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 8px;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4 {\n    font-weight: bold;\n    font-size: 133%;\n    padding: 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6338:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 179);
	var QueryString = __webpack_require__(/*! querystring */ 2695);
	
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