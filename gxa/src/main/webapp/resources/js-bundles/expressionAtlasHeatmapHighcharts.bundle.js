var expressionAtlasHeatmapHighcharts =
webpackJsonp_name_([6],{

/***/ 0:
/*!**********************************************!*\
  !*** multi expressionAtlasHeatmapHighcharts ***!
  \**********************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	__webpack_require__(/*! whatwg-fetch */1344);
	module.exports = __webpack_require__(/*! ./atlas_bundles/heatmap-highcharts */5236);


/***/ },

/***/ 5236:
/*!***************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/index.js ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;
	
	var _expressionAtlasHeatmapHighcharts = __webpack_require__(/*! expression-atlas-heatmap-highcharts */ 5237);
	
	exports.render = _expressionAtlasHeatmapHighcharts.render; //module.exports = require(`expression-atlas-heatmap-highcharts`);

/***/ },

/***/ 5237:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/Main.jsx ***!
  \*********************************************************************************************/
[6892, 5238, 5268, 5414, 5418],

/***/ 5238:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/react.js ***!
  \***********************************************************/
[6201, 5239],

/***/ 5239:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/React.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var ReactChildren = __webpack_require__(/*! ./ReactChildren */ 5241);
	var ReactComponent = __webpack_require__(/*! ./ReactComponent */ 5254);
	var ReactPureComponent = __webpack_require__(/*! ./ReactPureComponent */ 5257);
	var ReactClass = __webpack_require__(/*! ./ReactClass */ 5258);
	var ReactDOMFactories = __webpack_require__(/*! ./ReactDOMFactories */ 5260);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5245);
	var ReactPropTypes = __webpack_require__(/*! ./ReactPropTypes */ 5265);
	var ReactVersion = __webpack_require__(/*! ./ReactVersion */ 5266);
	
	var onlyChild = __webpack_require__(/*! ./onlyChild */ 5267);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var createElement = ReactElement.createElement;
	var createFactory = ReactElement.createFactory;
	var cloneElement = ReactElement.cloneElement;
	
	if (true) {
	  var ReactElementValidator = __webpack_require__(/*! ./ReactElementValidator */ 5261);
	  createElement = ReactElementValidator.createElement;
	  createFactory = ReactElementValidator.createFactory;
	  cloneElement = ReactElementValidator.cloneElement;
	}
	
	var __spread = _assign;
	
	if (true) {
	  var warned = false;
	  __spread = function () {
	     true ? warning(warned, 'React.__spread is deprecated and should not be used. Use ' + 'Object.assign directly or another helper function with similar ' + 'semantics. You may be seeing this warning due to your compiler. ' + 'See https://fb.me/react-spread-deprecation for more details.') : void 0;
	    warned = true;
	    return _assign.apply(null, arguments);
	  };
	}
	
	var React = {
	
	  // Modern
	
	  Children: {
	    map: ReactChildren.map,
	    forEach: ReactChildren.forEach,
	    count: ReactChildren.count,
	    toArray: ReactChildren.toArray,
	    only: onlyChild
	  },
	
	  Component: ReactComponent,
	  PureComponent: ReactPureComponent,
	
	  createElement: createElement,
	  cloneElement: cloneElement,
	  isValidElement: ReactElement.isValidElement,
	
	  // Classic
	
	  PropTypes: ReactPropTypes,
	  createClass: ReactClass.createClass,
	  createFactory: createFactory,
	  createMixin: function (mixin) {
	    // Currently a noop. Will be used to validate and trace mixins.
	    return mixin;
	  },
	
	  // This looks DOM specific but these are actually isomorphic helpers
	  // since they are just generating DOM strings.
	  DOM: ReactDOMFactories,
	
	  version: ReactVersion,
	
	  // Deprecated hook for JSX spread, don't use this for anything.
	  __spread: __spread
	};
	
	module.exports = React;

/***/ },

/***/ 5240:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/object-assign/index.js ***!
  \*******************************************************************/
5,

/***/ 5241:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactChildren.js ***!
  \***********************************************************************/
[6894, 5242, 5245, 5248, 5251],

/***/ 5242:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/PooledClass.js ***!
  \*********************************************************************/
[6895, 5243, 5244],

/***/ 5243:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/reactProdInvariant.js ***!
  \****************************************************************************/
1350,

/***/ 5244:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/invariant.js ***!
  \******************************************************************/
9,

/***/ 5245:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactElement.js ***!
  \**********************************************************************/
[6896, 5240, 5246, 5247, 5249, 5250],

/***/ 5246:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactCurrentOwner.js ***!
  \***************************************************************************/
1352,

/***/ 5247:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/warning.js ***!
  \****************************************************************/
[6206, 5248],

/***/ 5248:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/emptyFunction.js ***!
  \**********************************************************************/
13,

/***/ 5249:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/canDefineProperty.js ***!
  \***************************************************************************/
1353,

/***/ 5250:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactElementSymbol.js ***!
  \****************************************************************************/
1354,

/***/ 5251:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/traverseAllChildren.js ***!
  \*****************************************************************************/
[6897, 5243, 5246, 5250, 5252, 5244, 5253, 5247],

/***/ 5252:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/getIteratorFn.js ***!
  \***********************************************************************/
1356,

/***/ 5253:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/KeyEscapeUtils.js ***!
  \************************************************************************/
1357,

/***/ 5254:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactComponent.js ***!
  \************************************************************************/
[6898, 5243, 5255, 5249, 5256, 5244, 5247],

/***/ 5255:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactNoopUpdateQueue.js ***!
  \******************************************************************************/
[6899, 5247],

/***/ 5256:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/emptyObject.js ***!
  \********************************************************************/
20,

/***/ 5257:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPureComponent.js ***!
  \****************************************************************************/
[6900, 5240, 5254, 5255, 5256],

/***/ 5258:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactClass.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5243),
	    _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var ReactComponent = __webpack_require__(/*! ./ReactComponent */ 5254);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5245);
	var ReactPropTypeLocationNames = __webpack_require__(/*! ./ReactPropTypeLocationNames */ 5259);
	var ReactNoopUpdateQueue = __webpack_require__(/*! ./ReactNoopUpdateQueue */ 5255);
	
	var emptyObject = __webpack_require__(/*! fbjs/lib/emptyObject */ 5256);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var MIXINS_KEY = 'mixins';
	
	// Helper function to allow the creation of anonymous functions which do not
	// have .name set to the name of the variable being assigned to.
	function identity(fn) {
	  return fn;
	}
	
	/**
	 * Policies that describe methods in `ReactClassInterface`.
	 */
	
	
	var injectedMixins = [];
	
	/**
	 * Composite components are higher-level components that compose other composite
	 * or host components.
	 *
	 * To create a new type of `ReactClass`, pass a specification of
	 * your new class to `React.createClass`. The only requirement of your class
	 * specification is that you implement a `render` method.
	 *
	 *   var MyComponent = React.createClass({
	 *     render: function() {
	 *       return <div>Hello World</div>;
	 *     }
	 *   });
	 *
	 * The class specification supports a specific protocol of methods that have
	 * special meaning (e.g. `render`). See `ReactClassInterface` for
	 * more the comprehensive protocol. Any other properties and methods in the
	 * class specification will be available on the prototype.
	 *
	 * @interface ReactClassInterface
	 * @internal
	 */
	var ReactClassInterface = {
	
	  /**
	   * An array of Mixin objects to include when defining your component.
	   *
	   * @type {array}
	   * @optional
	   */
	  mixins: 'DEFINE_MANY',
	
	  /**
	   * An object containing properties and methods that should be defined on
	   * the component's constructor instead of its prototype (static methods).
	   *
	   * @type {object}
	   * @optional
	   */
	  statics: 'DEFINE_MANY',
	
	  /**
	   * Definition of prop types for this component.
	   *
	   * @type {object}
	   * @optional
	   */
	  propTypes: 'DEFINE_MANY',
	
	  /**
	   * Definition of context types for this component.
	   *
	   * @type {object}
	   * @optional
	   */
	  contextTypes: 'DEFINE_MANY',
	
	  /**
	   * Definition of context types this component sets for its children.
	   *
	   * @type {object}
	   * @optional
	   */
	  childContextTypes: 'DEFINE_MANY',
	
	  // ==== Definition methods ====
	
	  /**
	   * Invoked when the component is mounted. Values in the mapping will be set on
	   * `this.props` if that prop is not specified (i.e. using an `in` check).
	   *
	   * This method is invoked before `getInitialState` and therefore cannot rely
	   * on `this.state` or use `this.setState`.
	   *
	   * @return {object}
	   * @optional
	   */
	  getDefaultProps: 'DEFINE_MANY_MERGED',
	
	  /**
	   * Invoked once before the component is mounted. The return value will be used
	   * as the initial value of `this.state`.
	   *
	   *   getInitialState: function() {
	   *     return {
	   *       isOn: false,
	   *       fooBaz: new BazFoo()
	   *     }
	   *   }
	   *
	   * @return {object}
	   * @optional
	   */
	  getInitialState: 'DEFINE_MANY_MERGED',
	
	  /**
	   * @return {object}
	   * @optional
	   */
	  getChildContext: 'DEFINE_MANY_MERGED',
	
	  /**
	   * Uses props from `this.props` and state from `this.state` to render the
	   * structure of the component.
	   *
	   * No guarantees are made about when or how often this method is invoked, so
	   * it must not have side effects.
	   *
	   *   render: function() {
	   *     var name = this.props.name;
	   *     return <div>Hello, {name}!</div>;
	   *   }
	   *
	   * @return {ReactComponent}
	   * @nosideeffects
	   * @required
	   */
	  render: 'DEFINE_ONCE',
	
	  // ==== Delegate methods ====
	
	  /**
	   * Invoked when the component is initially created and about to be mounted.
	   * This may have side effects, but any external subscriptions or data created
	   * by this method must be cleaned up in `componentWillUnmount`.
	   *
	   * @optional
	   */
	  componentWillMount: 'DEFINE_MANY',
	
	  /**
	   * Invoked when the component has been mounted and has a DOM representation.
	   * However, there is no guarantee that the DOM node is in the document.
	   *
	   * Use this as an opportunity to operate on the DOM when the component has
	   * been mounted (initialized and rendered) for the first time.
	   *
	   * @param {DOMElement} rootNode DOM element representing the component.
	   * @optional
	   */
	  componentDidMount: 'DEFINE_MANY',
	
	  /**
	   * Invoked before the component receives new props.
	   *
	   * Use this as an opportunity to react to a prop transition by updating the
	   * state using `this.setState`. Current props are accessed via `this.props`.
	   *
	   *   componentWillReceiveProps: function(nextProps, nextContext) {
	   *     this.setState({
	   *       likesIncreasing: nextProps.likeCount > this.props.likeCount
	   *     });
	   *   }
	   *
	   * NOTE: There is no equivalent `componentWillReceiveState`. An incoming prop
	   * transition may cause a state change, but the opposite is not true. If you
	   * need it, you are probably looking for `componentWillUpdate`.
	   *
	   * @param {object} nextProps
	   * @optional
	   */
	  componentWillReceiveProps: 'DEFINE_MANY',
	
	  /**
	   * Invoked while deciding if the component should be updated as a result of
	   * receiving new props, state and/or context.
	   *
	   * Use this as an opportunity to `return false` when you're certain that the
	   * transition to the new props/state/context will not require a component
	   * update.
	   *
	   *   shouldComponentUpdate: function(nextProps, nextState, nextContext) {
	   *     return !equal(nextProps, this.props) ||
	   *       !equal(nextState, this.state) ||
	   *       !equal(nextContext, this.context);
	   *   }
	   *
	   * @param {object} nextProps
	   * @param {?object} nextState
	   * @param {?object} nextContext
	   * @return {boolean} True if the component should update.
	   * @optional
	   */
	  shouldComponentUpdate: 'DEFINE_ONCE',
	
	  /**
	   * Invoked when the component is about to update due to a transition from
	   * `this.props`, `this.state` and `this.context` to `nextProps`, `nextState`
	   * and `nextContext`.
	   *
	   * Use this as an opportunity to perform preparation before an update occurs.
	   *
	   * NOTE: You **cannot** use `this.setState()` in this method.
	   *
	   * @param {object} nextProps
	   * @param {?object} nextState
	   * @param {?object} nextContext
	   * @param {ReactReconcileTransaction} transaction
	   * @optional
	   */
	  componentWillUpdate: 'DEFINE_MANY',
	
	  /**
	   * Invoked when the component's DOM representation has been updated.
	   *
	   * Use this as an opportunity to operate on the DOM when the component has
	   * been updated.
	   *
	   * @param {object} prevProps
	   * @param {?object} prevState
	   * @param {?object} prevContext
	   * @param {DOMElement} rootNode DOM element representing the component.
	   * @optional
	   */
	  componentDidUpdate: 'DEFINE_MANY',
	
	  /**
	   * Invoked when the component is about to be removed from its parent and have
	   * its DOM representation destroyed.
	   *
	   * Use this as an opportunity to deallocate any external resources.
	   *
	   * NOTE: There is no `componentDidUnmount` since your component will have been
	   * destroyed by that point.
	   *
	   * @optional
	   */
	  componentWillUnmount: 'DEFINE_MANY',
	
	  // ==== Advanced methods ====
	
	  /**
	   * Updates the component's currently mounted DOM representation.
	   *
	   * By default, this implements React's rendering and reconciliation algorithm.
	   * Sophisticated clients may wish to override this.
	   *
	   * @param {ReactReconcileTransaction} transaction
	   * @internal
	   * @overridable
	   */
	  updateComponent: 'OVERRIDE_BASE'
	
	};
	
	/**
	 * Mapping from class specification keys to special processing functions.
	 *
	 * Although these are declared like instance properties in the specification
	 * when defining classes using `React.createClass`, they are actually static
	 * and are accessible on the constructor instead of the prototype. Despite
	 * being static, they must be defined outside of the "statics" key under
	 * which all other static methods are defined.
	 */
	var RESERVED_SPEC_KEYS = {
	  displayName: function (Constructor, displayName) {
	    Constructor.displayName = displayName;
	  },
	  mixins: function (Constructor, mixins) {
	    if (mixins) {
	      for (var i = 0; i < mixins.length; i++) {
	        mixSpecIntoComponent(Constructor, mixins[i]);
	      }
	    }
	  },
	  childContextTypes: function (Constructor, childContextTypes) {
	    if (true) {
	      validateTypeDef(Constructor, childContextTypes, 'childContext');
	    }
	    Constructor.childContextTypes = _assign({}, Constructor.childContextTypes, childContextTypes);
	  },
	  contextTypes: function (Constructor, contextTypes) {
	    if (true) {
	      validateTypeDef(Constructor, contextTypes, 'context');
	    }
	    Constructor.contextTypes = _assign({}, Constructor.contextTypes, contextTypes);
	  },
	  /**
	   * Special case getDefaultProps which should move into statics but requires
	   * automatic merging.
	   */
	  getDefaultProps: function (Constructor, getDefaultProps) {
	    if (Constructor.getDefaultProps) {
	      Constructor.getDefaultProps = createMergedResultFunction(Constructor.getDefaultProps, getDefaultProps);
	    } else {
	      Constructor.getDefaultProps = getDefaultProps;
	    }
	  },
	  propTypes: function (Constructor, propTypes) {
	    if (true) {
	      validateTypeDef(Constructor, propTypes, 'prop');
	    }
	    Constructor.propTypes = _assign({}, Constructor.propTypes, propTypes);
	  },
	  statics: function (Constructor, statics) {
	    mixStaticSpecIntoComponent(Constructor, statics);
	  },
	  autobind: function () {} };
	
	function validateTypeDef(Constructor, typeDef, location) {
	  for (var propName in typeDef) {
	    if (typeDef.hasOwnProperty(propName)) {
	      // use a warning instead of an invariant so components
	      // don't show up in prod but only in __DEV__
	       true ? warning(typeof typeDef[propName] === 'function', '%s: %s type `%s` is invalid; it must be a function, usually from ' + 'React.PropTypes.', Constructor.displayName || 'ReactClass', ReactPropTypeLocationNames[location], propName) : void 0;
	    }
	  }
	}
	
	function validateMethodOverride(isAlreadyDefined, name) {
	  var specPolicy = ReactClassInterface.hasOwnProperty(name) ? ReactClassInterface[name] : null;
	
	  // Disallow overriding of base class methods unless explicitly allowed.
	  if (ReactClassMixin.hasOwnProperty(name)) {
	    !(specPolicy === 'OVERRIDE_BASE') ?  true ? invariant(false, 'ReactClassInterface: You are attempting to override `%s` from your class specification. Ensure that your method names do not overlap with React methods.', name) : _prodInvariant('73', name) : void 0;
	  }
	
	  // Disallow defining methods more than once unless explicitly allowed.
	  if (isAlreadyDefined) {
	    !(specPolicy === 'DEFINE_MANY' || specPolicy === 'DEFINE_MANY_MERGED') ?  true ? invariant(false, 'ReactClassInterface: You are attempting to define `%s` on your component more than once. This conflict may be due to a mixin.', name) : _prodInvariant('74', name) : void 0;
	  }
	}
	
	/**
	 * Mixin helper which handles policy validation and reserved
	 * specification keys when building React classes.
	 */
	function mixSpecIntoComponent(Constructor, spec) {
	  if (!spec) {
	    if (true) {
	      var typeofSpec = typeof spec;
	      var isMixinValid = typeofSpec === 'object' && spec !== null;
	
	       true ? warning(isMixinValid, '%s: You\'re attempting to include a mixin that is either null ' + 'or not an object. Check the mixins included by the component, ' + 'as well as any mixins they include themselves. ' + 'Expected object but got %s.', Constructor.displayName || 'ReactClass', spec === null ? null : typeofSpec) : void 0;
	    }
	
	    return;
	  }
	
	  !(typeof spec !== 'function') ?  true ? invariant(false, 'ReactClass: You\'re attempting to use a component class or function as a mixin. Instead, just use a regular object.') : _prodInvariant('75') : void 0;
	  !!ReactElement.isValidElement(spec) ?  true ? invariant(false, 'ReactClass: You\'re attempting to use a component as a mixin. Instead, just use a regular object.') : _prodInvariant('76') : void 0;
	
	  var proto = Constructor.prototype;
	  var autoBindPairs = proto.__reactAutoBindPairs;
	
	  // By handling mixins before any other properties, we ensure the same
	  // chaining order is applied to methods with DEFINE_MANY policy, whether
	  // mixins are listed before or after these methods in the spec.
	  if (spec.hasOwnProperty(MIXINS_KEY)) {
	    RESERVED_SPEC_KEYS.mixins(Constructor, spec.mixins);
	  }
	
	  for (var name in spec) {
	    if (!spec.hasOwnProperty(name)) {
	      continue;
	    }
	
	    if (name === MIXINS_KEY) {
	      // We have already handled mixins in a special case above.
	      continue;
	    }
	
	    var property = spec[name];
	    var isAlreadyDefined = proto.hasOwnProperty(name);
	    validateMethodOverride(isAlreadyDefined, name);
	
	    if (RESERVED_SPEC_KEYS.hasOwnProperty(name)) {
	      RESERVED_SPEC_KEYS[name](Constructor, property);
	    } else {
	      // Setup methods on prototype:
	      // The following member methods should not be automatically bound:
	      // 1. Expected ReactClass methods (in the "interface").
	      // 2. Overridden methods (that were mixed in).
	      var isReactClassMethod = ReactClassInterface.hasOwnProperty(name);
	      var isFunction = typeof property === 'function';
	      var shouldAutoBind = isFunction && !isReactClassMethod && !isAlreadyDefined && spec.autobind !== false;
	
	      if (shouldAutoBind) {
	        autoBindPairs.push(name, property);
	        proto[name] = property;
	      } else {
	        if (isAlreadyDefined) {
	          var specPolicy = ReactClassInterface[name];
	
	          // These cases should already be caught by validateMethodOverride.
	          !(isReactClassMethod && (specPolicy === 'DEFINE_MANY_MERGED' || specPolicy === 'DEFINE_MANY')) ?  true ? invariant(false, 'ReactClass: Unexpected spec policy %s for key %s when mixing in component specs.', specPolicy, name) : _prodInvariant('77', specPolicy, name) : void 0;
	
	          // For methods which are defined more than once, call the existing
	          // methods before calling the new property, merging if appropriate.
	          if (specPolicy === 'DEFINE_MANY_MERGED') {
	            proto[name] = createMergedResultFunction(proto[name], property);
	          } else if (specPolicy === 'DEFINE_MANY') {
	            proto[name] = createChainedFunction(proto[name], property);
	          }
	        } else {
	          proto[name] = property;
	          if (true) {
	            // Add verbose displayName to the function, which helps when looking
	            // at profiling tools.
	            if (typeof property === 'function' && spec.displayName) {
	              proto[name].displayName = spec.displayName + '_' + name;
	            }
	          }
	        }
	      }
	    }
	  }
	}
	
	function mixStaticSpecIntoComponent(Constructor, statics) {
	  if (!statics) {
	    return;
	  }
	  for (var name in statics) {
	    var property = statics[name];
	    if (!statics.hasOwnProperty(name)) {
	      continue;
	    }
	
	    var isReserved = name in RESERVED_SPEC_KEYS;
	    !!isReserved ?  true ? invariant(false, 'ReactClass: You are attempting to define a reserved property, `%s`, that shouldn\'t be on the "statics" key. Define it as an instance property instead; it will still be accessible on the constructor.', name) : _prodInvariant('78', name) : void 0;
	
	    var isInherited = name in Constructor;
	    !!isInherited ?  true ? invariant(false, 'ReactClass: You are attempting to define `%s` on your component more than once. This conflict may be due to a mixin.', name) : _prodInvariant('79', name) : void 0;
	    Constructor[name] = property;
	  }
	}
	
	/**
	 * Merge two objects, but throw if both contain the same key.
	 *
	 * @param {object} one The first object, which is mutated.
	 * @param {object} two The second object
	 * @return {object} one after it has been mutated to contain everything in two.
	 */
	function mergeIntoWithNoDuplicateKeys(one, two) {
	  !(one && two && typeof one === 'object' && typeof two === 'object') ?  true ? invariant(false, 'mergeIntoWithNoDuplicateKeys(): Cannot merge non-objects.') : _prodInvariant('80') : void 0;
	
	  for (var key in two) {
	    if (two.hasOwnProperty(key)) {
	      !(one[key] === undefined) ?  true ? invariant(false, 'mergeIntoWithNoDuplicateKeys(): Tried to merge two objects with the same key: `%s`. This conflict may be due to a mixin; in particular, this may be caused by two getInitialState() or getDefaultProps() methods returning objects with clashing keys.', key) : _prodInvariant('81', key) : void 0;
	      one[key] = two[key];
	    }
	  }
	  return one;
	}
	
	/**
	 * Creates a function that invokes two functions and merges their return values.
	 *
	 * @param {function} one Function to invoke first.
	 * @param {function} two Function to invoke second.
	 * @return {function} Function that invokes the two argument functions.
	 * @private
	 */
	function createMergedResultFunction(one, two) {
	  return function mergedResult() {
	    var a = one.apply(this, arguments);
	    var b = two.apply(this, arguments);
	    if (a == null) {
	      return b;
	    } else if (b == null) {
	      return a;
	    }
	    var c = {};
	    mergeIntoWithNoDuplicateKeys(c, a);
	    mergeIntoWithNoDuplicateKeys(c, b);
	    return c;
	  };
	}
	
	/**
	 * Creates a function that invokes two functions and ignores their return vales.
	 *
	 * @param {function} one Function to invoke first.
	 * @param {function} two Function to invoke second.
	 * @return {function} Function that invokes the two argument functions.
	 * @private
	 */
	function createChainedFunction(one, two) {
	  return function chainedFunction() {
	    one.apply(this, arguments);
	    two.apply(this, arguments);
	  };
	}
	
	/**
	 * Binds a method to the component.
	 *
	 * @param {object} component Component whose method is going to be bound.
	 * @param {function} method Method to be bound.
	 * @return {function} The bound method.
	 */
	function bindAutoBindMethod(component, method) {
	  var boundMethod = method.bind(component);
	  if (true) {
	    boundMethod.__reactBoundContext = component;
	    boundMethod.__reactBoundMethod = method;
	    boundMethod.__reactBoundArguments = null;
	    var componentName = component.constructor.displayName;
	    var _bind = boundMethod.bind;
	    boundMethod.bind = function (newThis) {
	      for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
	        args[_key - 1] = arguments[_key];
	      }
	
	      // User is trying to bind() an autobound method; we effectively will
	      // ignore the value of "this" that the user is trying to use, so
	      // let's warn.
	      if (newThis !== component && newThis !== null) {
	         true ? warning(false, 'bind(): React component methods may only be bound to the ' + 'component instance. See %s', componentName) : void 0;
	      } else if (!args.length) {
	         true ? warning(false, 'bind(): You are binding a component method to the component. ' + 'React does this for you automatically in a high-performance ' + 'way, so you can safely remove this call. See %s', componentName) : void 0;
	        return boundMethod;
	      }
	      var reboundMethod = _bind.apply(boundMethod, arguments);
	      reboundMethod.__reactBoundContext = component;
	      reboundMethod.__reactBoundMethod = method;
	      reboundMethod.__reactBoundArguments = args;
	      return reboundMethod;
	    };
	  }
	  return boundMethod;
	}
	
	/**
	 * Binds all auto-bound methods in a component.
	 *
	 * @param {object} component Component whose method is going to be bound.
	 */
	function bindAutoBindMethods(component) {
	  var pairs = component.__reactAutoBindPairs;
	  for (var i = 0; i < pairs.length; i += 2) {
	    var autoBindKey = pairs[i];
	    var method = pairs[i + 1];
	    component[autoBindKey] = bindAutoBindMethod(component, method);
	  }
	}
	
	/**
	 * Add more to the ReactClass base class. These are all legacy features and
	 * therefore not already part of the modern ReactComponent.
	 */
	var ReactClassMixin = {
	
	  /**
	   * TODO: This will be deprecated because state should always keep a consistent
	   * type signature and the only use case for this, is to avoid that.
	   */
	  replaceState: function (newState, callback) {
	    this.updater.enqueueReplaceState(this, newState);
	    if (callback) {
	      this.updater.enqueueCallback(this, callback, 'replaceState');
	    }
	  },
	
	  /**
	   * Checks whether or not this composite component is mounted.
	   * @return {boolean} True if mounted, false otherwise.
	   * @protected
	   * @final
	   */
	  isMounted: function () {
	    return this.updater.isMounted(this);
	  }
	};
	
	var ReactClassComponent = function () {};
	_assign(ReactClassComponent.prototype, ReactComponent.prototype, ReactClassMixin);
	
	/**
	 * Module for creating composite components.
	 *
	 * @class ReactClass
	 */
	var ReactClass = {
	
	  /**
	   * Creates a composite component class given a class specification.
	   * See https://facebook.github.io/react/docs/top-level-api.html#react.createclass
	   *
	   * @param {object} spec Class specification (which must define `render`).
	   * @return {function} Component constructor function.
	   * @public
	   */
	  createClass: function (spec) {
	    // To keep our warnings more understandable, we'll use a little hack here to
	    // ensure that Constructor.name !== 'Constructor'. This makes sure we don't
	    // unnecessarily identify a class without displayName as 'Constructor'.
	    var Constructor = identity(function (props, context, updater) {
	      // This constructor gets overridden by mocks. The argument is used
	      // by mocks to assert on what gets mounted.
	
	      if (true) {
	         true ? warning(this instanceof Constructor, 'Something is calling a React component directly. Use a factory or ' + 'JSX instead. See: https://fb.me/react-legacyfactory') : void 0;
	      }
	
	      // Wire up auto-binding
	      if (this.__reactAutoBindPairs.length) {
	        bindAutoBindMethods(this);
	      }
	
	      this.props = props;
	      this.context = context;
	      this.refs = emptyObject;
	      this.updater = updater || ReactNoopUpdateQueue;
	
	      this.state = null;
	
	      // ReactClasses doesn't have constructors. Instead, they use the
	      // getInitialState and componentWillMount methods for initialization.
	
	      var initialState = this.getInitialState ? this.getInitialState() : null;
	      if (true) {
	        // We allow auto-mocks to proceed as if they're returning null.
	        if (initialState === undefined && this.getInitialState._isMockFunction) {
	          // This is probably bad practice. Consider warning here and
	          // deprecating this convenience.
	          initialState = null;
	        }
	      }
	      !(typeof initialState === 'object' && !Array.isArray(initialState)) ?  true ? invariant(false, '%s.getInitialState(): must return an object or null', Constructor.displayName || 'ReactCompositeComponent') : _prodInvariant('82', Constructor.displayName || 'ReactCompositeComponent') : void 0;
	
	      this.state = initialState;
	    });
	    Constructor.prototype = new ReactClassComponent();
	    Constructor.prototype.constructor = Constructor;
	    Constructor.prototype.__reactAutoBindPairs = [];
	
	    injectedMixins.forEach(mixSpecIntoComponent.bind(null, Constructor));
	
	    mixSpecIntoComponent(Constructor, spec);
	
	    // Initialize the defaultProps property after all mixins have been merged.
	    if (Constructor.getDefaultProps) {
	      Constructor.defaultProps = Constructor.getDefaultProps();
	    }
	
	    if (true) {
	      // This is a tag to indicate that the use of these method names is ok,
	      // since it's used with createClass. If it's not, then it's likely a
	      // mistake so we'll warn you to use the static property, property
	      // initializer or constructor respectively.
	      if (Constructor.getDefaultProps) {
	        Constructor.getDefaultProps.isReactClassApproved = {};
	      }
	      if (Constructor.prototype.getInitialState) {
	        Constructor.prototype.getInitialState.isReactClassApproved = {};
	      }
	    }
	
	    !Constructor.prototype.render ?  true ? invariant(false, 'createClass(...): Class specification must implement a `render` method.') : _prodInvariant('83') : void 0;
	
	    if (true) {
	       true ? warning(!Constructor.prototype.componentShouldUpdate, '%s has a method called ' + 'componentShouldUpdate(). Did you mean shouldComponentUpdate()? ' + 'The name is phrased as a question because the function is ' + 'expected to return a value.', spec.displayName || 'A component') : void 0;
	       true ? warning(!Constructor.prototype.componentWillRecieveProps, '%s has a method called ' + 'componentWillRecieveProps(). Did you mean componentWillReceiveProps()?', spec.displayName || 'A component') : void 0;
	    }
	
	    // Reduce time spent doing lookups by setting these on the prototype.
	    for (var methodName in ReactClassInterface) {
	      if (!Constructor.prototype[methodName]) {
	        Constructor.prototype[methodName] = null;
	      }
	    }
	
	    return Constructor;
	  },
	
	  injection: {
	    injectMixin: function (mixin) {
	      injectedMixins.push(mixin);
	    }
	  }
	
	};
	
	module.exports = ReactClass;

/***/ },

/***/ 5259:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPropTypeLocationNames.js ***!
  \************************************************************************************/
1362,

/***/ 5260:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactDOMFactories.js ***!
  \***************************************************************************/
[6902, 5245, 5261],

/***/ 5261:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactElementValidator.js ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2014-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	/**
	 * ReactElementValidator provides a wrapper around a element factory
	 * which validates the props passed to the element. This is intended to be
	 * used only in DEV and could be replaced by a static type checker for languages
	 * that support it.
	 */
	
	'use strict';
	
	var ReactCurrentOwner = __webpack_require__(/*! ./ReactCurrentOwner */ 5246);
	var ReactComponentTreeHook = __webpack_require__(/*! ./ReactComponentTreeHook */ 5262);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5245);
	
	var checkReactTypeSpec = __webpack_require__(/*! ./checkReactTypeSpec */ 5263);
	
	var canDefineProperty = __webpack_require__(/*! ./canDefineProperty */ 5249);
	var getIteratorFn = __webpack_require__(/*! ./getIteratorFn */ 5252);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	function getDeclarationErrorAddendum() {
	  if (ReactCurrentOwner.current) {
	    var name = ReactCurrentOwner.current.getName();
	    if (name) {
	      return ' Check the render method of `' + name + '`.';
	    }
	  }
	  return '';
	}
	
	/**
	 * Warn if there's no key explicitly set on dynamic arrays of children or
	 * object keys are not valid. This allows us to keep track of children between
	 * updates.
	 */
	var ownerHasKeyUseWarning = {};
	
	function getCurrentComponentErrorInfo(parentType) {
	  var info = getDeclarationErrorAddendum();
	
	  if (!info) {
	    var parentName = typeof parentType === 'string' ? parentType : parentType.displayName || parentType.name;
	    if (parentName) {
	      info = ' Check the top-level render call using <' + parentName + '>.';
	    }
	  }
	  return info;
	}
	
	/**
	 * Warn if the element doesn't have an explicit key assigned to it.
	 * This element is in an array. The array could grow and shrink or be
	 * reordered. All children that haven't already been validated are required to
	 * have a "key" property assigned to it. Error statuses are cached so a warning
	 * will only be shown once.
	 *
	 * @internal
	 * @param {ReactElement} element Element that requires a key.
	 * @param {*} parentType element's parent's type.
	 */
	function validateExplicitKey(element, parentType) {
	  if (!element._store || element._store.validated || element.key != null) {
	    return;
	  }
	  element._store.validated = true;
	
	  var memoizer = ownerHasKeyUseWarning.uniqueKey || (ownerHasKeyUseWarning.uniqueKey = {});
	
	  var currentComponentErrorInfo = getCurrentComponentErrorInfo(parentType);
	  if (memoizer[currentComponentErrorInfo]) {
	    return;
	  }
	  memoizer[currentComponentErrorInfo] = true;
	
	  // Usually the current owner is the offender, but if it accepts children as a
	  // property, it may be the creator of the child that's responsible for
	  // assigning it a key.
	  var childOwner = '';
	  if (element && element._owner && element._owner !== ReactCurrentOwner.current) {
	    // Give the component that originally created this child.
	    childOwner = ' It was passed a child from ' + element._owner.getName() + '.';
	  }
	
	   true ? warning(false, 'Each child in an array or iterator should have a unique "key" prop.' + '%s%s See https://fb.me/react-warning-keys for more information.%s', currentComponentErrorInfo, childOwner, ReactComponentTreeHook.getCurrentStackAddendum(element)) : void 0;
	}
	
	/**
	 * Ensure that every element either is passed in a static location, in an
	 * array with an explicit keys property defined, or in an object literal
	 * with valid key property.
	 *
	 * @internal
	 * @param {ReactNode} node Statically passed child of any type.
	 * @param {*} parentType node's parent's type.
	 */
	function validateChildKeys(node, parentType) {
	  if (typeof node !== 'object') {
	    return;
	  }
	  if (Array.isArray(node)) {
	    for (var i = 0; i < node.length; i++) {
	      var child = node[i];
	      if (ReactElement.isValidElement(child)) {
	        validateExplicitKey(child, parentType);
	      }
	    }
	  } else if (ReactElement.isValidElement(node)) {
	    // This element was passed in a valid location.
	    if (node._store) {
	      node._store.validated = true;
	    }
	  } else if (node) {
	    var iteratorFn = getIteratorFn(node);
	    // Entry iterators provide implicit keys.
	    if (iteratorFn) {
	      if (iteratorFn !== node.entries) {
	        var iterator = iteratorFn.call(node);
	        var step;
	        while (!(step = iterator.next()).done) {
	          if (ReactElement.isValidElement(step.value)) {
	            validateExplicitKey(step.value, parentType);
	          }
	        }
	      }
	    }
	  }
	}
	
	/**
	 * Given an element, validate that its props follow the propTypes definition,
	 * provided by the type.
	 *
	 * @param {ReactElement} element
	 */
	function validatePropTypes(element) {
	  var componentClass = element.type;
	  if (typeof componentClass !== 'function') {
	    return;
	  }
	  var name = componentClass.displayName || componentClass.name;
	  if (componentClass.propTypes) {
	    checkReactTypeSpec(componentClass.propTypes, element.props, 'prop', name, element, null);
	  }
	  if (typeof componentClass.getDefaultProps === 'function') {
	     true ? warning(componentClass.getDefaultProps.isReactClassApproved, 'getDefaultProps is only used on classic React.createClass ' + 'definitions. Use a static property named `defaultProps` instead.') : void 0;
	  }
	}
	
	var ReactElementValidator = {
	
	  createElement: function (type, props, children) {
	    var validType = typeof type === 'string' || typeof type === 'function';
	    // We warn in this case but don't throw. We expect the element creation to
	    // succeed and there will likely be errors in render.
	    if (!validType) {
	      if (typeof type !== 'function' && typeof type !== 'string') {
	        var info = '';
	        if (type === undefined || typeof type === 'object' && type !== null && Object.keys(type).length === 0) {
	          info += ' You likely forgot to export your component from the file ' + 'it\'s defined in.';
	        }
	        info += getDeclarationErrorAddendum();
	         true ? warning(false, 'React.createElement: type is invalid -- expected a string (for ' + 'built-in components) or a class/function (for composite ' + 'components) but got: %s.%s', type == null ? type : typeof type, info) : void 0;
	      }
	    }
	
	    var element = ReactElement.createElement.apply(this, arguments);
	
	    // The result can be nullish if a mock or a custom function is used.
	    // TODO: Drop this when these are no longer allowed as the type argument.
	    if (element == null) {
	      return element;
	    }
	
	    // Skip key warning if the type isn't valid since our key validation logic
	    // doesn't expect a non-string/function type and can throw confusing errors.
	    // We don't want exception behavior to differ between dev and prod.
	    // (Rendering will throw with a helpful message and as soon as the type is
	    // fixed, the key warnings will appear.)
	    if (validType) {
	      for (var i = 2; i < arguments.length; i++) {
	        validateChildKeys(arguments[i], type);
	      }
	    }
	
	    validatePropTypes(element);
	
	    return element;
	  },
	
	  createFactory: function (type) {
	    var validatedFactory = ReactElementValidator.createElement.bind(null, type);
	    // Legacy hook TODO: Warn if this is accessed
	    validatedFactory.type = type;
	
	    if (true) {
	      if (canDefineProperty) {
	        Object.defineProperty(validatedFactory, 'type', {
	          enumerable: false,
	          get: function () {
	             true ? warning(false, 'Factory.type is deprecated. Access the class directly ' + 'before passing it to createFactory.') : void 0;
	            Object.defineProperty(this, 'type', {
	              value: type
	            });
	            return type;
	          }
	        });
	      }
	    }
	
	    return validatedFactory;
	  },
	
	  cloneElement: function (element, props, children) {
	    var newElement = ReactElement.cloneElement.apply(this, arguments);
	    for (var i = 2; i < arguments.length; i++) {
	      validateChildKeys(arguments[i], newElement.type);
	    }
	    validatePropTypes(newElement);
	    return newElement;
	  }
	
	};
	
	module.exports = ReactElementValidator;

/***/ },

/***/ 5262:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactComponentTreeHook.js ***!
  \********************************************************************************/
[6904, 5243, 5246, 5244, 5247],

/***/ 5263:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/checkReactTypeSpec.js ***!
  \****************************************************************************/
[6905, 5243, 5259, 5264, 5244, 5247, 5262, 5262],

/***/ 5264:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPropTypesSecret.js ***!
  \******************************************************************************/
1367,

/***/ 5265:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPropTypes.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5245);
	var ReactPropTypeLocationNames = __webpack_require__(/*! ./ReactPropTypeLocationNames */ 5259);
	var ReactPropTypesSecret = __webpack_require__(/*! ./ReactPropTypesSecret */ 5264);
	
	var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 5248);
	var getIteratorFn = __webpack_require__(/*! ./getIteratorFn */ 5252);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	/**
	 * Collection of methods that allow declaration and validation of props that are
	 * supplied to React components. Example usage:
	 *
	 *   var Props = require('ReactPropTypes');
	 *   var MyArticle = React.createClass({
	 *     propTypes: {
	 *       // An optional string prop named "description".
	 *       description: Props.string,
	 *
	 *       // A required enum prop named "category".
	 *       category: Props.oneOf(['News','Photos']).isRequired,
	 *
	 *       // A prop named "dialog" that requires an instance of Dialog.
	 *       dialog: Props.instanceOf(Dialog).isRequired
	 *     },
	 *     render: function() { ... }
	 *   });
	 *
	 * A more formal specification of how these methods are used:
	 *
	 *   type := array|bool|func|object|number|string|oneOf([...])|instanceOf(...)
	 *   decl := ReactPropTypes.{type}(.isRequired)?
	 *
	 * Each and every declaration produces a function with the same signature. This
	 * allows the creation of custom validation functions. For example:
	 *
	 *  var MyLink = React.createClass({
	 *    propTypes: {
	 *      // An optional string or URI prop named "href".
	 *      href: function(props, propName, componentName) {
	 *        var propValue = props[propName];
	 *        if (propValue != null && typeof propValue !== 'string' &&
	 *            !(propValue instanceof URI)) {
	 *          return new Error(
	 *            'Expected a string or an URI for ' + propName + ' in ' +
	 *            componentName
	 *          );
	 *        }
	 *      }
	 *    },
	 *    render: function() {...}
	 *  });
	 *
	 * @internal
	 */
	
	var ANONYMOUS = '<<anonymous>>';
	
	var ReactPropTypes = {
	  array: createPrimitiveTypeChecker('array'),
	  bool: createPrimitiveTypeChecker('boolean'),
	  func: createPrimitiveTypeChecker('function'),
	  number: createPrimitiveTypeChecker('number'),
	  object: createPrimitiveTypeChecker('object'),
	  string: createPrimitiveTypeChecker('string'),
	  symbol: createPrimitiveTypeChecker('symbol'),
	
	  any: createAnyTypeChecker(),
	  arrayOf: createArrayOfTypeChecker,
	  element: createElementTypeChecker(),
	  instanceOf: createInstanceTypeChecker,
	  node: createNodeChecker(),
	  objectOf: createObjectOfTypeChecker,
	  oneOf: createEnumTypeChecker,
	  oneOfType: createUnionTypeChecker,
	  shape: createShapeTypeChecker
	};
	
	/**
	 * inlined Object.is polyfill to avoid requiring consumers ship their own
	 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/is
	 */
	/*eslint-disable no-self-compare*/
	function is(x, y) {
	  // SameValue algorithm
	  if (x === y) {
	    // Steps 1-5, 7-10
	    // Steps 6.b-6.e: +0 != -0
	    return x !== 0 || 1 / x === 1 / y;
	  } else {
	    // Step 6.a: NaN == NaN
	    return x !== x && y !== y;
	  }
	}
	/*eslint-enable no-self-compare*/
	
	/**
	 * We use an Error-like object for backward compatibility as people may call
	 * PropTypes directly and inspect their output. However we don't use real
	 * Errors anymore. We don't inspect their stack anyway, and creating them
	 * is prohibitively expensive if they are created too often, such as what
	 * happens in oneOfType() for any type before the one that matched.
	 */
	function PropTypeError(message) {
	  this.message = message;
	  this.stack = '';
	}
	// Make `instanceof Error` still work for returned errors.
	PropTypeError.prototype = Error.prototype;
	
	function createChainableTypeChecker(validate) {
	  if (true) {
	    var manualPropTypeCallCache = {};
	  }
	  function checkType(isRequired, props, propName, componentName, location, propFullName, secret) {
	    componentName = componentName || ANONYMOUS;
	    propFullName = propFullName || propName;
	    if (true) {
	      if (secret !== ReactPropTypesSecret && typeof console !== 'undefined') {
	        var cacheKey = componentName + ':' + propName;
	        if (!manualPropTypeCallCache[cacheKey]) {
	           true ? warning(false, 'You are manually calling a React.PropTypes validation ' + 'function for the `%s` prop on `%s`. This is deprecated ' + 'and will not work in production with the next major version. ' + 'You may be seeing this warning due to a third-party PropTypes ' + 'library. See https://fb.me/react-warning-dont-call-proptypes ' + 'for details.', propFullName, componentName) : void 0;
	          manualPropTypeCallCache[cacheKey] = true;
	        }
	      }
	    }
	    if (props[propName] == null) {
	      var locationName = ReactPropTypeLocationNames[location];
	      if (isRequired) {
	        if (props[propName] === null) {
	          return new PropTypeError('The ' + locationName + ' `' + propFullName + '` is marked as required ' + ('in `' + componentName + '`, but its value is `null`.'));
	        }
	        return new PropTypeError('The ' + locationName + ' `' + propFullName + '` is marked as required in ' + ('`' + componentName + '`, but its value is `undefined`.'));
	      }
	      return null;
	    } else {
	      return validate(props, propName, componentName, location, propFullName);
	    }
	  }
	
	  var chainedCheckType = checkType.bind(null, false);
	  chainedCheckType.isRequired = checkType.bind(null, true);
	
	  return chainedCheckType;
	}
	
	function createPrimitiveTypeChecker(expectedType) {
	  function validate(props, propName, componentName, location, propFullName, secret) {
	    var propValue = props[propName];
	    var propType = getPropType(propValue);
	    if (propType !== expectedType) {
	      var locationName = ReactPropTypeLocationNames[location];
	      // `propValue` being instance of, say, date/regexp, pass the 'object'
	      // check, but we can offer a more precise error message here rather than
	      // 'of type `object`'.
	      var preciseType = getPreciseType(propValue);
	
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type ' + ('`' + preciseType + '` supplied to `' + componentName + '`, expected ') + ('`' + expectedType + '`.'));
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createAnyTypeChecker() {
	  return createChainableTypeChecker(emptyFunction.thatReturns(null));
	}
	
	function createArrayOfTypeChecker(typeChecker) {
	  function validate(props, propName, componentName, location, propFullName) {
	    if (typeof typeChecker !== 'function') {
	      return new PropTypeError('Property `' + propFullName + '` of component `' + componentName + '` has invalid PropType notation inside arrayOf.');
	    }
	    var propValue = props[propName];
	    if (!Array.isArray(propValue)) {
	      var locationName = ReactPropTypeLocationNames[location];
	      var propType = getPropType(propValue);
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected an array.'));
	    }
	    for (var i = 0; i < propValue.length; i++) {
	      var error = typeChecker(propValue, i, componentName, location, propFullName + '[' + i + ']', ReactPropTypesSecret);
	      if (error instanceof Error) {
	        return error;
	      }
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createElementTypeChecker() {
	  function validate(props, propName, componentName, location, propFullName) {
	    var propValue = props[propName];
	    if (!ReactElement.isValidElement(propValue)) {
	      var locationName = ReactPropTypeLocationNames[location];
	      var propType = getPropType(propValue);
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected a single ReactElement.'));
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createInstanceTypeChecker(expectedClass) {
	  function validate(props, propName, componentName, location, propFullName) {
	    if (!(props[propName] instanceof expectedClass)) {
	      var locationName = ReactPropTypeLocationNames[location];
	      var expectedClassName = expectedClass.name || ANONYMOUS;
	      var actualClassName = getClassName(props[propName]);
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type ' + ('`' + actualClassName + '` supplied to `' + componentName + '`, expected ') + ('instance of `' + expectedClassName + '`.'));
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createEnumTypeChecker(expectedValues) {
	  if (!Array.isArray(expectedValues)) {
	     true ? warning(false, 'Invalid argument supplied to oneOf, expected an instance of array.') : void 0;
	    return emptyFunction.thatReturnsNull;
	  }
	
	  function validate(props, propName, componentName, location, propFullName) {
	    var propValue = props[propName];
	    for (var i = 0; i < expectedValues.length; i++) {
	      if (is(propValue, expectedValues[i])) {
	        return null;
	      }
	    }
	
	    var locationName = ReactPropTypeLocationNames[location];
	    var valuesString = JSON.stringify(expectedValues);
	    return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of value `' + propValue + '` ' + ('supplied to `' + componentName + '`, expected one of ' + valuesString + '.'));
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createObjectOfTypeChecker(typeChecker) {
	  function validate(props, propName, componentName, location, propFullName) {
	    if (typeof typeChecker !== 'function') {
	      return new PropTypeError('Property `' + propFullName + '` of component `' + componentName + '` has invalid PropType notation inside objectOf.');
	    }
	    var propValue = props[propName];
	    var propType = getPropType(propValue);
	    if (propType !== 'object') {
	      var locationName = ReactPropTypeLocationNames[location];
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected an object.'));
	    }
	    for (var key in propValue) {
	      if (propValue.hasOwnProperty(key)) {
	        var error = typeChecker(propValue, key, componentName, location, propFullName + '.' + key, ReactPropTypesSecret);
	        if (error instanceof Error) {
	          return error;
	        }
	      }
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createUnionTypeChecker(arrayOfTypeCheckers) {
	  if (!Array.isArray(arrayOfTypeCheckers)) {
	     true ? warning(false, 'Invalid argument supplied to oneOfType, expected an instance of array.') : void 0;
	    return emptyFunction.thatReturnsNull;
	  }
	
	  function validate(props, propName, componentName, location, propFullName) {
	    for (var i = 0; i < arrayOfTypeCheckers.length; i++) {
	      var checker = arrayOfTypeCheckers[i];
	      if (checker(props, propName, componentName, location, propFullName, ReactPropTypesSecret) == null) {
	        return null;
	      }
	    }
	
	    var locationName = ReactPropTypeLocationNames[location];
	    return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` supplied to ' + ('`' + componentName + '`.'));
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createNodeChecker() {
	  function validate(props, propName, componentName, location, propFullName) {
	    if (!isNode(props[propName])) {
	      var locationName = ReactPropTypeLocationNames[location];
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` supplied to ' + ('`' + componentName + '`, expected a ReactNode.'));
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function createShapeTypeChecker(shapeTypes) {
	  function validate(props, propName, componentName, location, propFullName) {
	    var propValue = props[propName];
	    var propType = getPropType(propValue);
	    if (propType !== 'object') {
	      var locationName = ReactPropTypeLocationNames[location];
	      return new PropTypeError('Invalid ' + locationName + ' `' + propFullName + '` of type `' + propType + '` ' + ('supplied to `' + componentName + '`, expected `object`.'));
	    }
	    for (var key in shapeTypes) {
	      var checker = shapeTypes[key];
	      if (!checker) {
	        continue;
	      }
	      var error = checker(propValue, key, componentName, location, propFullName + '.' + key, ReactPropTypesSecret);
	      if (error) {
	        return error;
	      }
	    }
	    return null;
	  }
	  return createChainableTypeChecker(validate);
	}
	
	function isNode(propValue) {
	  switch (typeof propValue) {
	    case 'number':
	    case 'string':
	    case 'undefined':
	      return true;
	    case 'boolean':
	      return !propValue;
	    case 'object':
	      if (Array.isArray(propValue)) {
	        return propValue.every(isNode);
	      }
	      if (propValue === null || ReactElement.isValidElement(propValue)) {
	        return true;
	      }
	
	      var iteratorFn = getIteratorFn(propValue);
	      if (iteratorFn) {
	        var iterator = iteratorFn.call(propValue);
	        var step;
	        if (iteratorFn !== propValue.entries) {
	          while (!(step = iterator.next()).done) {
	            if (!isNode(step.value)) {
	              return false;
	            }
	          }
	        } else {
	          // Iterator will provide entry [k,v] tuples rather than values.
	          while (!(step = iterator.next()).done) {
	            var entry = step.value;
	            if (entry) {
	              if (!isNode(entry[1])) {
	                return false;
	              }
	            }
	          }
	        }
	      } else {
	        return false;
	      }
	
	      return true;
	    default:
	      return false;
	  }
	}
	
	function isSymbol(propType, propValue) {
	  // Native Symbol.
	  if (propType === 'symbol') {
	    return true;
	  }
	
	  // 19.4.3.5 Symbol.prototype[@@toStringTag] === 'Symbol'
	  if (propValue['@@toStringTag'] === 'Symbol') {
	    return true;
	  }
	
	  // Fallback for non-spec compliant Symbols which are polyfilled.
	  if (typeof Symbol === 'function' && propValue instanceof Symbol) {
	    return true;
	  }
	
	  return false;
	}
	
	// Equivalent of `typeof` but with special handling for array and regexp.
	function getPropType(propValue) {
	  var propType = typeof propValue;
	  if (Array.isArray(propValue)) {
	    return 'array';
	  }
	  if (propValue instanceof RegExp) {
	    // Old webkits (at least until Android 4.0) return 'function' rather than
	    // 'object' for typeof a RegExp. We'll normalize this here so that /bla/
	    // passes PropTypes.object.
	    return 'object';
	  }
	  if (isSymbol(propType, propValue)) {
	    return 'symbol';
	  }
	  return propType;
	}
	
	// This handles more types than `getPropType`. Only used for error messages.
	// See `createPrimitiveTypeChecker`.
	function getPreciseType(propValue) {
	  var propType = getPropType(propValue);
	  if (propType === 'object') {
	    if (propValue instanceof Date) {
	      return 'date';
	    } else if (propValue instanceof RegExp) {
	      return 'regexp';
	    }
	  }
	  return propType;
	}
	
	// Returns class name of the object, if any.
	function getClassName(propValue) {
	  if (!propValue.constructor || !propValue.constructor.name) {
	    return ANONYMOUS;
	  }
	  return propValue.constructor.name;
	}
	
	module.exports = ReactPropTypes;

/***/ },

/***/ 5266:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactVersion.js ***!
  \**********************************************************************/
/***/ function(module, exports) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	module.exports = '15.4.2';

/***/ },

/***/ 5267:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/onlyChild.js ***!
  \*******************************************************************/
[6909, 5243, 5245, 5244],

/***/ 5268:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/index.js ***!
  \***************************************************************/
[6910, 5269],

/***/ 5269:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOM.js ***!
  \**********************************************************************/
[6911, 5270, 5274, 5402, 5295, 5292, 5407, 5408, 5409, 5410, 5247, 5284, 5298, 5411, 5412, 5413],

/***/ 5270:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponentTree.js ***!
  \***********************************************************************************/
[6912, 5271, 5272, 5273, 5244],

/***/ 5271:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/reactProdInvariant.js ***!
  \********************************************************************************/
1350,

/***/ 5272:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMProperty.js ***!
  \*************************************************************************/
[6913, 5271, 5244],

/***/ 5273:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponentFlags.js ***!
  \************************************************************************************/
1379,

/***/ 5274:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDefaultInjection.js ***!
  \***********************************************************************************/
[6914, 5275, 5276, 5291, 5308, 5309, 5314, 5315, 5328, 5270, 5373, 5374, 5375, 5376, 5377, 5380, 5381, 5389, 5390, 5391],

/***/ 5275:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ARIADOMPropertyConfig.js ***!
  \***********************************************************************************/
1381,

/***/ 5276:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/BeforeInputEventPlugin.js ***!
  \************************************************************************************/
[6915, 5277, 5284, 5285, 5288, 5290],

/***/ 5277:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPropagators.js ***!
  \******************************************************************************/
[6916, 5278, 5280, 5282, 5283, 5247],

/***/ 5278:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginHub.js ***!
  \****************************************************************************/
[6917, 5271, 5279, 5280, 5281, 5282, 5283, 5244],

/***/ 5279:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginRegistry.js ***!
  \*********************************************************************************/
[6918, 5271, 5244],

/***/ 5280:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginUtils.js ***!
  \******************************************************************************/
[6919, 5271, 5281, 5244, 5247],

/***/ 5281:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactErrorUtils.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 * 
	 */
	
	'use strict';
	
	var caughtError = null;
	
	/**
	 * Call a function while guarding against errors that happens within it.
	 *
	 * @param {String} name of the guard to use for logging or debugging
	 * @param {Function} func The function to invoke
	 * @param {*} a First argument
	 * @param {*} b Second argument
	 */
	function invokeGuardedCallback(name, func, a) {
	  try {
	    func(a);
	  } catch (x) {
	    if (caughtError === null) {
	      caughtError = x;
	    }
	  }
	}
	
	var ReactErrorUtils = {
	  invokeGuardedCallback: invokeGuardedCallback,
	
	  /**
	   * Invoked by ReactTestUtils.Simulate so that any errors thrown by the event
	   * handler are sure to be rethrown by rethrowCaughtError.
	   */
	  invokeGuardedCallbackWithCatch: invokeGuardedCallback,
	
	  /**
	   * During execution of guarded functions we will capture the first error which
	   * we will rethrow to be handled by the top level error handler.
	   */
	  rethrowCaughtError: function () {
	    if (caughtError) {
	      var error = caughtError;
	      caughtError = null;
	      throw error;
	    }
	  }
	};
	
	if (true) {
	  /**
	   * To help development we can get better devtools integration by simulating a
	   * real browser event.
	   */
	  if (typeof window !== 'undefined' && typeof window.dispatchEvent === 'function' && typeof document !== 'undefined' && typeof document.createEvent === 'function') {
	    var fakeNode = document.createElement('react');
	    ReactErrorUtils.invokeGuardedCallback = function (name, func, a) {
	      var boundFunc = func.bind(null, a);
	      var evtType = 'react-' + name;
	      fakeNode.addEventListener(evtType, boundFunc, false);
	      var evt = document.createEvent('Event');
	      // $FlowFixMe https://github.com/facebook/flow/issues/2336
	      evt.initEvent(evtType, false, false);
	      fakeNode.dispatchEvent(evt);
	      fakeNode.removeEventListener(evtType, boundFunc, false);
	    };
	  }
	}
	
	module.exports = ReactErrorUtils;

/***/ },

/***/ 5282:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/accumulateInto.js ***!
  \****************************************************************************/
[6920, 5271, 5244],

/***/ 5283:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/forEachAccumulated.js ***!
  \********************************************************************************/
1389,

/***/ 5284:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/ExecutionEnvironment.js ***!
  \*****************************************************************************/
53,

/***/ 5285:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/FallbackCompositionState.js ***!
  \**************************************************************************************/
[6921, 5240, 5286, 5287],

/***/ 5286:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/PooledClass.js ***!
  \*************************************************************************/
[6895, 5271, 5244],

/***/ 5287:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getTextContentAccessor.js ***!
  \************************************************************************************/
[6922, 5284],

/***/ 5288:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticCompositionEvent.js ***!
  \***************************************************************************************/
[6923, 5289],

/***/ 5289:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticEvent.js ***!
  \****************************************************************************/
[6924, 5240, 5286, 5248, 5247],

/***/ 5290:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticInputEvent.js ***!
  \*********************************************************************************/
[6925, 5289],

/***/ 5291:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ChangeEventPlugin.js ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var EventPluginHub = __webpack_require__(/*! ./EventPluginHub */ 5278);
	var EventPropagators = __webpack_require__(/*! ./EventPropagators */ 5277);
	var ExecutionEnvironment = __webpack_require__(/*! fbjs/lib/ExecutionEnvironment */ 5284);
	var ReactDOMComponentTree = __webpack_require__(/*! ./ReactDOMComponentTree */ 5270);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 5292);
	var SyntheticEvent = __webpack_require__(/*! ./SyntheticEvent */ 5289);
	
	var getEventTarget = __webpack_require__(/*! ./getEventTarget */ 5305);
	var isEventSupported = __webpack_require__(/*! ./isEventSupported */ 5306);
	var isTextInputElement = __webpack_require__(/*! ./isTextInputElement */ 5307);
	
	var eventTypes = {
	  change: {
	    phasedRegistrationNames: {
	      bubbled: 'onChange',
	      captured: 'onChangeCapture'
	    },
	    dependencies: ['topBlur', 'topChange', 'topClick', 'topFocus', 'topInput', 'topKeyDown', 'topKeyUp', 'topSelectionChange']
	  }
	};
	
	/**
	 * For IE shims
	 */
	var activeElement = null;
	var activeElementInst = null;
	var activeElementValue = null;
	var activeElementValueProp = null;
	
	/**
	 * SECTION: handle `change` event
	 */
	function shouldUseChangeEvent(elem) {
	  var nodeName = elem.nodeName && elem.nodeName.toLowerCase();
	  return nodeName === 'select' || nodeName === 'input' && elem.type === 'file';
	}
	
	var doesChangeEventBubble = false;
	if (ExecutionEnvironment.canUseDOM) {
	  // See `handleChange` comment below
	  doesChangeEventBubble = isEventSupported('change') && (!document.documentMode || document.documentMode > 8);
	}
	
	function manualDispatchChangeEvent(nativeEvent) {
	  var event = SyntheticEvent.getPooled(eventTypes.change, activeElementInst, nativeEvent, getEventTarget(nativeEvent));
	  EventPropagators.accumulateTwoPhaseDispatches(event);
	
	  // If change and propertychange bubbled, we'd just bind to it like all the
	  // other events and have it go through ReactBrowserEventEmitter. Since it
	  // doesn't, we manually listen for the events and so we have to enqueue and
	  // process the abstract event manually.
	  //
	  // Batching is necessary here in order to ensure that all event handlers run
	  // before the next rerender (including event handlers attached to ancestor
	  // elements instead of directly on the input). Without this, controlled
	  // components don't work properly in conjunction with event bubbling because
	  // the component is rerendered and the value reverted before all the event
	  // handlers can run. See https://github.com/facebook/react/issues/708.
	  ReactUpdates.batchedUpdates(runEventInBatch, event);
	}
	
	function runEventInBatch(event) {
	  EventPluginHub.enqueueEvents(event);
	  EventPluginHub.processEventQueue(false);
	}
	
	function startWatchingForChangeEventIE8(target, targetInst) {
	  activeElement = target;
	  activeElementInst = targetInst;
	  activeElement.attachEvent('onchange', manualDispatchChangeEvent);
	}
	
	function stopWatchingForChangeEventIE8() {
	  if (!activeElement) {
	    return;
	  }
	  activeElement.detachEvent('onchange', manualDispatchChangeEvent);
	  activeElement = null;
	  activeElementInst = null;
	}
	
	function getTargetInstForChangeEvent(topLevelType, targetInst) {
	  if (topLevelType === 'topChange') {
	    return targetInst;
	  }
	}
	function handleEventsForChangeEventIE8(topLevelType, target, targetInst) {
	  if (topLevelType === 'topFocus') {
	    // stopWatching() should be a noop here but we call it just in case we
	    // missed a blur event somehow.
	    stopWatchingForChangeEventIE8();
	    startWatchingForChangeEventIE8(target, targetInst);
	  } else if (topLevelType === 'topBlur') {
	    stopWatchingForChangeEventIE8();
	  }
	}
	
	/**
	 * SECTION: handle `input` event
	 */
	var isInputEventSupported = false;
	if (ExecutionEnvironment.canUseDOM) {
	  // IE9 claims to support the input event but fails to trigger it when
	  // deleting text, so we ignore its input events.
	  // IE10+ fire input events to often, such when a placeholder
	  // changes or when an input with a placeholder is focused.
	  isInputEventSupported = isEventSupported('input') && (!document.documentMode || document.documentMode > 11);
	}
	
	/**
	 * (For IE <=11) Replacement getter/setter for the `value` property that gets
	 * set on the active element.
	 */
	var newValueProp = {
	  get: function () {
	    return activeElementValueProp.get.call(this);
	  },
	  set: function (val) {
	    // Cast to a string so we can do equality checks.
	    activeElementValue = '' + val;
	    activeElementValueProp.set.call(this, val);
	  }
	};
	
	/**
	 * (For IE <=11) Starts tracking propertychange events on the passed-in element
	 * and override the value property so that we can distinguish user events from
	 * value changes in JS.
	 */
	function startWatchingForValueChange(target, targetInst) {
	  activeElement = target;
	  activeElementInst = targetInst;
	  activeElementValue = target.value;
	  activeElementValueProp = Object.getOwnPropertyDescriptor(target.constructor.prototype, 'value');
	
	  // Not guarded in a canDefineProperty check: IE8 supports defineProperty only
	  // on DOM elements
	  Object.defineProperty(activeElement, 'value', newValueProp);
	  if (activeElement.attachEvent) {
	    activeElement.attachEvent('onpropertychange', handlePropertyChange);
	  } else {
	    activeElement.addEventListener('propertychange', handlePropertyChange, false);
	  }
	}
	
	/**
	 * (For IE <=11) Removes the event listeners from the currently-tracked element,
	 * if any exists.
	 */
	function stopWatchingForValueChange() {
	  if (!activeElement) {
	    return;
	  }
	
	  // delete restores the original property definition
	  delete activeElement.value;
	
	  if (activeElement.detachEvent) {
	    activeElement.detachEvent('onpropertychange', handlePropertyChange);
	  } else {
	    activeElement.removeEventListener('propertychange', handlePropertyChange, false);
	  }
	
	  activeElement = null;
	  activeElementInst = null;
	  activeElementValue = null;
	  activeElementValueProp = null;
	}
	
	/**
	 * (For IE <=11) Handles a propertychange event, sending a `change` event if
	 * the value of the active element has changed.
	 */
	function handlePropertyChange(nativeEvent) {
	  if (nativeEvent.propertyName !== 'value') {
	    return;
	  }
	  var value = nativeEvent.srcElement.value;
	  if (value === activeElementValue) {
	    return;
	  }
	  activeElementValue = value;
	
	  manualDispatchChangeEvent(nativeEvent);
	}
	
	/**
	 * If a `change` event should be fired, returns the target's ID.
	 */
	function getTargetInstForInputEvent(topLevelType, targetInst) {
	  if (topLevelType === 'topInput') {
	    // In modern browsers (i.e., not IE8 or IE9), the input event is exactly
	    // what we want so fall through here and trigger an abstract event
	    return targetInst;
	  }
	}
	
	function handleEventsForInputEventIE(topLevelType, target, targetInst) {
	  if (topLevelType === 'topFocus') {
	    // In IE8, we can capture almost all .value changes by adding a
	    // propertychange handler and looking for events with propertyName
	    // equal to 'value'
	    // In IE9-11, propertychange fires for most input events but is buggy and
	    // doesn't fire when text is deleted, but conveniently, selectionchange
	    // appears to fire in all of the remaining cases so we catch those and
	    // forward the event if the value has changed
	    // In either case, we don't want to call the event handler if the value
	    // is changed from JS so we redefine a setter for `.value` that updates
	    // our activeElementValue variable, allowing us to ignore those changes
	    //
	    // stopWatching() should be a noop here but we call it just in case we
	    // missed a blur event somehow.
	    stopWatchingForValueChange();
	    startWatchingForValueChange(target, targetInst);
	  } else if (topLevelType === 'topBlur') {
	    stopWatchingForValueChange();
	  }
	}
	
	// For IE8 and IE9.
	function getTargetInstForInputEventIE(topLevelType, targetInst) {
	  if (topLevelType === 'topSelectionChange' || topLevelType === 'topKeyUp' || topLevelType === 'topKeyDown') {
	    // On the selectionchange event, the target is just document which isn't
	    // helpful for us so just check activeElement instead.
	    //
	    // 99% of the time, keydown and keyup aren't necessary. IE8 fails to fire
	    // propertychange on the first input event after setting `value` from a
	    // script and fires only keydown, keypress, keyup. Catching keyup usually
	    // gets it and catching keydown lets us fire an event for the first
	    // keystroke if user does a key repeat (it'll be a little delayed: right
	    // before the second keystroke). Other input methods (e.g., paste) seem to
	    // fire selectionchange normally.
	    if (activeElement && activeElement.value !== activeElementValue) {
	      activeElementValue = activeElement.value;
	      return activeElementInst;
	    }
	  }
	}
	
	/**
	 * SECTION: handle `click` event
	 */
	function shouldUseClickEvent(elem) {
	  // Use the `click` event to detect changes to checkbox and radio inputs.
	  // This approach works across all browsers, whereas `change` does not fire
	  // until `blur` in IE8.
	  return elem.nodeName && elem.nodeName.toLowerCase() === 'input' && (elem.type === 'checkbox' || elem.type === 'radio');
	}
	
	function getTargetInstForClickEvent(topLevelType, targetInst) {
	  if (topLevelType === 'topClick') {
	    return targetInst;
	  }
	}
	
	/**
	 * This plugin creates an `onChange` event that normalizes change events
	 * across form elements. This event fires at a time when it's possible to
	 * change the element's value without seeing a flicker.
	 *
	 * Supported elements are:
	 * - input (see `isTextInputElement`)
	 * - textarea
	 * - select
	 */
	var ChangeEventPlugin = {
	
	  eventTypes: eventTypes,
	
	  extractEvents: function (topLevelType, targetInst, nativeEvent, nativeEventTarget) {
	    var targetNode = targetInst ? ReactDOMComponentTree.getNodeFromInstance(targetInst) : window;
	
	    var getTargetInstFunc, handleEventFunc;
	    if (shouldUseChangeEvent(targetNode)) {
	      if (doesChangeEventBubble) {
	        getTargetInstFunc = getTargetInstForChangeEvent;
	      } else {
	        handleEventFunc = handleEventsForChangeEventIE8;
	      }
	    } else if (isTextInputElement(targetNode)) {
	      if (isInputEventSupported) {
	        getTargetInstFunc = getTargetInstForInputEvent;
	      } else {
	        getTargetInstFunc = getTargetInstForInputEventIE;
	        handleEventFunc = handleEventsForInputEventIE;
	      }
	    } else if (shouldUseClickEvent(targetNode)) {
	      getTargetInstFunc = getTargetInstForClickEvent;
	    }
	
	    if (getTargetInstFunc) {
	      var inst = getTargetInstFunc(topLevelType, targetInst);
	      if (inst) {
	        var event = SyntheticEvent.getPooled(eventTypes.change, inst, nativeEvent, nativeEventTarget);
	        event.type = 'change';
	        EventPropagators.accumulateTwoPhaseDispatches(event);
	        return event;
	      }
	    }
	
	    if (handleEventFunc) {
	      handleEventFunc(topLevelType, targetNode, targetInst);
	    }
	  }
	
	};
	
	module.exports = ChangeEventPlugin;

/***/ },

/***/ 5292:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactUpdates.js ***!
  \**************************************************************************/
[6927, 5271, 5240, 5293, 5286, 5294, 5295, 5304, 5244],

/***/ 5293:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CallbackQueue.js ***!
  \***************************************************************************/
[6928, 5271, 5286, 5244],

/***/ 5294:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactFeatureFlags.js ***!
  \*******************************************************************************/
1399,

/***/ 5295:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactReconciler.js ***!
  \*****************************************************************************/
[6929, 5296, 5298, 5247],

/***/ 5296:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactRef.js ***!
  \**********************************************************************/
[6930, 5297],

/***/ 5297:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactOwner.js ***!
  \************************************************************************/
[6931, 5271, 5244],

/***/ 5298:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInstrumentation.js ***!
  \**********************************************************************************/
[6932, 5299],

/***/ 5299:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDebugTool.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2016-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 * 
	 */
	
	'use strict';
	
	var ReactInvalidSetStateWarningHook = __webpack_require__(/*! ./ReactInvalidSetStateWarningHook */ 5300);
	var ReactHostOperationHistoryHook = __webpack_require__(/*! ./ReactHostOperationHistoryHook */ 5301);
	var ReactComponentTreeHook = __webpack_require__(/*! react/lib/ReactComponentTreeHook */ 5262);
	var ExecutionEnvironment = __webpack_require__(/*! fbjs/lib/ExecutionEnvironment */ 5284);
	
	var performanceNow = __webpack_require__(/*! fbjs/lib/performanceNow */ 5302);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var hooks = [];
	var didHookThrowForEvent = {};
	
	function callHook(event, fn, context, arg1, arg2, arg3, arg4, arg5) {
	  try {
	    fn.call(context, arg1, arg2, arg3, arg4, arg5);
	  } catch (e) {
	     true ? warning(didHookThrowForEvent[event], 'Exception thrown by hook while handling %s: %s', event, e + '\n' + e.stack) : void 0;
	    didHookThrowForEvent[event] = true;
	  }
	}
	
	function emitEvent(event, arg1, arg2, arg3, arg4, arg5) {
	  for (var i = 0; i < hooks.length; i++) {
	    var hook = hooks[i];
	    var fn = hook[event];
	    if (fn) {
	      callHook(event, fn, hook, arg1, arg2, arg3, arg4, arg5);
	    }
	  }
	}
	
	var isProfiling = false;
	var flushHistory = [];
	var lifeCycleTimerStack = [];
	var currentFlushNesting = 0;
	var currentFlushMeasurements = [];
	var currentFlushStartTime = 0;
	var currentTimerDebugID = null;
	var currentTimerStartTime = 0;
	var currentTimerNestedFlushDuration = 0;
	var currentTimerType = null;
	
	var lifeCycleTimerHasWarned = false;
	
	function clearHistory() {
	  ReactComponentTreeHook.purgeUnmountedComponents();
	  ReactHostOperationHistoryHook.clearHistory();
	}
	
	function getTreeSnapshot(registeredIDs) {
	  return registeredIDs.reduce(function (tree, id) {
	    var ownerID = ReactComponentTreeHook.getOwnerID(id);
	    var parentID = ReactComponentTreeHook.getParentID(id);
	    tree[id] = {
	      displayName: ReactComponentTreeHook.getDisplayName(id),
	      text: ReactComponentTreeHook.getText(id),
	      updateCount: ReactComponentTreeHook.getUpdateCount(id),
	      childIDs: ReactComponentTreeHook.getChildIDs(id),
	      // Text nodes don't have owners but this is close enough.
	      ownerID: ownerID || parentID && ReactComponentTreeHook.getOwnerID(parentID) || 0,
	      parentID: parentID
	    };
	    return tree;
	  }, {});
	}
	
	function resetMeasurements() {
	  var previousStartTime = currentFlushStartTime;
	  var previousMeasurements = currentFlushMeasurements;
	  var previousOperations = ReactHostOperationHistoryHook.getHistory();
	
	  if (currentFlushNesting === 0) {
	    currentFlushStartTime = 0;
	    currentFlushMeasurements = [];
	    clearHistory();
	    return;
	  }
	
	  if (previousMeasurements.length || previousOperations.length) {
	    var registeredIDs = ReactComponentTreeHook.getRegisteredIDs();
	    flushHistory.push({
	      duration: performanceNow() - previousStartTime,
	      measurements: previousMeasurements || [],
	      operations: previousOperations || [],
	      treeSnapshot: getTreeSnapshot(registeredIDs)
	    });
	  }
	
	  clearHistory();
	  currentFlushStartTime = performanceNow();
	  currentFlushMeasurements = [];
	}
	
	function checkDebugID(debugID) {
	  var allowRoot = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
	
	  if (allowRoot && debugID === 0) {
	    return;
	  }
	  if (!debugID) {
	     true ? warning(false, 'ReactDebugTool: debugID may not be empty.') : void 0;
	  }
	}
	
	function beginLifeCycleTimer(debugID, timerType) {
	  if (currentFlushNesting === 0) {
	    return;
	  }
	  if (currentTimerType && !lifeCycleTimerHasWarned) {
	     true ? warning(false, 'There is an internal error in the React performance measurement code. ' + 'Did not expect %s timer to start while %s timer is still in ' + 'progress for %s instance.', timerType, currentTimerType || 'no', debugID === currentTimerDebugID ? 'the same' : 'another') : void 0;
	    lifeCycleTimerHasWarned = true;
	  }
	  currentTimerStartTime = performanceNow();
	  currentTimerNestedFlushDuration = 0;
	  currentTimerDebugID = debugID;
	  currentTimerType = timerType;
	}
	
	function endLifeCycleTimer(debugID, timerType) {
	  if (currentFlushNesting === 0) {
	    return;
	  }
	  if (currentTimerType !== timerType && !lifeCycleTimerHasWarned) {
	     true ? warning(false, 'There is an internal error in the React performance measurement code. ' + 'We did not expect %s timer to stop while %s timer is still in ' + 'progress for %s instance. Please report this as a bug in React.', timerType, currentTimerType || 'no', debugID === currentTimerDebugID ? 'the same' : 'another') : void 0;
	    lifeCycleTimerHasWarned = true;
	  }
	  if (isProfiling) {
	    currentFlushMeasurements.push({
	      timerType: timerType,
	      instanceID: debugID,
	      duration: performanceNow() - currentTimerStartTime - currentTimerNestedFlushDuration
	    });
	  }
	  currentTimerStartTime = 0;
	  currentTimerNestedFlushDuration = 0;
	  currentTimerDebugID = null;
	  currentTimerType = null;
	}
	
	function pauseCurrentLifeCycleTimer() {
	  var currentTimer = {
	    startTime: currentTimerStartTime,
	    nestedFlushStartTime: performanceNow(),
	    debugID: currentTimerDebugID,
	    timerType: currentTimerType
	  };
	  lifeCycleTimerStack.push(currentTimer);
	  currentTimerStartTime = 0;
	  currentTimerNestedFlushDuration = 0;
	  currentTimerDebugID = null;
	  currentTimerType = null;
	}
	
	function resumeCurrentLifeCycleTimer() {
	  var _lifeCycleTimerStack$ = lifeCycleTimerStack.pop(),
	      startTime = _lifeCycleTimerStack$.startTime,
	      nestedFlushStartTime = _lifeCycleTimerStack$.nestedFlushStartTime,
	      debugID = _lifeCycleTimerStack$.debugID,
	      timerType = _lifeCycleTimerStack$.timerType;
	
	  var nestedFlushDuration = performanceNow() - nestedFlushStartTime;
	  currentTimerStartTime = startTime;
	  currentTimerNestedFlushDuration += nestedFlushDuration;
	  currentTimerDebugID = debugID;
	  currentTimerType = timerType;
	}
	
	var lastMarkTimeStamp = 0;
	var canUsePerformanceMeasure =
	// $FlowFixMe https://github.com/facebook/flow/issues/2345
	typeof performance !== 'undefined' && typeof performance.mark === 'function' && typeof performance.clearMarks === 'function' && typeof performance.measure === 'function' && typeof performance.clearMeasures === 'function';
	
	function shouldMark(debugID) {
	  if (!isProfiling || !canUsePerformanceMeasure) {
	    return false;
	  }
	  var element = ReactComponentTreeHook.getElement(debugID);
	  if (element == null || typeof element !== 'object') {
	    return false;
	  }
	  var isHostElement = typeof element.type === 'string';
	  if (isHostElement) {
	    return false;
	  }
	  return true;
	}
	
	function markBegin(debugID, markType) {
	  if (!shouldMark(debugID)) {
	    return;
	  }
	
	  var markName = debugID + '::' + markType;
	  lastMarkTimeStamp = performanceNow();
	  performance.mark(markName);
	}
	
	function markEnd(debugID, markType) {
	  if (!shouldMark(debugID)) {
	    return;
	  }
	
	  var markName = debugID + '::' + markType;
	  var displayName = ReactComponentTreeHook.getDisplayName(debugID) || 'Unknown';
	
	  // Chrome has an issue of dropping markers recorded too fast:
	  // https://bugs.chromium.org/p/chromium/issues/detail?id=640652
	  // To work around this, we will not report very small measurements.
	  // I determined the magic number by tweaking it back and forth.
	  // 0.05ms was enough to prevent the issue, but I set it to 0.1ms to be safe.
	  // When the bug is fixed, we can `measure()` unconditionally if we want to.
	  var timeStamp = performanceNow();
	  if (timeStamp - lastMarkTimeStamp > 0.1) {
	    var measurementName = displayName + ' [' + markType + ']';
	    performance.measure(measurementName, markName);
	  }
	
	  performance.clearMarks(markName);
	  performance.clearMeasures(measurementName);
	}
	
	var ReactDebugTool = {
	  addHook: function (hook) {
	    hooks.push(hook);
	  },
	  removeHook: function (hook) {
	    for (var i = 0; i < hooks.length; i++) {
	      if (hooks[i] === hook) {
	        hooks.splice(i, 1);
	        i--;
	      }
	    }
	  },
	  isProfiling: function () {
	    return isProfiling;
	  },
	  beginProfiling: function () {
	    if (isProfiling) {
	      return;
	    }
	
	    isProfiling = true;
	    flushHistory.length = 0;
	    resetMeasurements();
	    ReactDebugTool.addHook(ReactHostOperationHistoryHook);
	  },
	  endProfiling: function () {
	    if (!isProfiling) {
	      return;
	    }
	
	    isProfiling = false;
	    resetMeasurements();
	    ReactDebugTool.removeHook(ReactHostOperationHistoryHook);
	  },
	  getFlushHistory: function () {
	    return flushHistory;
	  },
	  onBeginFlush: function () {
	    currentFlushNesting++;
	    resetMeasurements();
	    pauseCurrentLifeCycleTimer();
	    emitEvent('onBeginFlush');
	  },
	  onEndFlush: function () {
	    resetMeasurements();
	    currentFlushNesting--;
	    resumeCurrentLifeCycleTimer();
	    emitEvent('onEndFlush');
	  },
	  onBeginLifeCycleTimer: function (debugID, timerType) {
	    checkDebugID(debugID);
	    emitEvent('onBeginLifeCycleTimer', debugID, timerType);
	    markBegin(debugID, timerType);
	    beginLifeCycleTimer(debugID, timerType);
	  },
	  onEndLifeCycleTimer: function (debugID, timerType) {
	    checkDebugID(debugID);
	    endLifeCycleTimer(debugID, timerType);
	    markEnd(debugID, timerType);
	    emitEvent('onEndLifeCycleTimer', debugID, timerType);
	  },
	  onBeginProcessingChildContext: function () {
	    emitEvent('onBeginProcessingChildContext');
	  },
	  onEndProcessingChildContext: function () {
	    emitEvent('onEndProcessingChildContext');
	  },
	  onHostOperation: function (operation) {
	    checkDebugID(operation.instanceID);
	    emitEvent('onHostOperation', operation);
	  },
	  onSetState: function () {
	    emitEvent('onSetState');
	  },
	  onSetChildren: function (debugID, childDebugIDs) {
	    checkDebugID(debugID);
	    childDebugIDs.forEach(checkDebugID);
	    emitEvent('onSetChildren', debugID, childDebugIDs);
	  },
	  onBeforeMountComponent: function (debugID, element, parentDebugID) {
	    checkDebugID(debugID);
	    checkDebugID(parentDebugID, true);
	    emitEvent('onBeforeMountComponent', debugID, element, parentDebugID);
	    markBegin(debugID, 'mount');
	  },
	  onMountComponent: function (debugID) {
	    checkDebugID(debugID);
	    markEnd(debugID, 'mount');
	    emitEvent('onMountComponent', debugID);
	  },
	  onBeforeUpdateComponent: function (debugID, element) {
	    checkDebugID(debugID);
	    emitEvent('onBeforeUpdateComponent', debugID, element);
	    markBegin(debugID, 'update');
	  },
	  onUpdateComponent: function (debugID) {
	    checkDebugID(debugID);
	    markEnd(debugID, 'update');
	    emitEvent('onUpdateComponent', debugID);
	  },
	  onBeforeUnmountComponent: function (debugID) {
	    checkDebugID(debugID);
	    emitEvent('onBeforeUnmountComponent', debugID);
	    markBegin(debugID, 'unmount');
	  },
	  onUnmountComponent: function (debugID) {
	    checkDebugID(debugID);
	    markEnd(debugID, 'unmount');
	    emitEvent('onUnmountComponent', debugID);
	  },
	  onTestEvent: function () {
	    emitEvent('onTestEvent');
	  }
	};
	
	// TODO remove these when RN/www gets updated
	ReactDebugTool.addDevtool = ReactDebugTool.addHook;
	ReactDebugTool.removeDevtool = ReactDebugTool.removeHook;
	
	ReactDebugTool.addHook(ReactInvalidSetStateWarningHook);
	ReactDebugTool.addHook(ReactComponentTreeHook);
	var url = ExecutionEnvironment.canUseDOM && window.location.href || '';
	if (/[?&]react_perf\b/.test(url)) {
	  ReactDebugTool.beginProfiling();
	}
	
	module.exports = ReactDebugTool;

/***/ },

/***/ 5300:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInvalidSetStateWarningHook.js ***!
  \*********************************************************************************************/
[6934, 5247],

/***/ 5301:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactHostOperationHistoryHook.js ***!
  \*******************************************************************************************/
1406,

/***/ 5302:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/performanceNow.js ***!
  \***********************************************************************/
[6249, 5303],

/***/ 5303:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/performance.js ***!
  \********************************************************************/
[6250, 5284],

/***/ 5304:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/Transaction.js ***!
  \*************************************************************************/
[6935, 5271, 5244],

/***/ 5305:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventTarget.js ***!
  \****************************************************************************/
1408,

/***/ 5306:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/isEventSupported.js ***!
  \******************************************************************************/
[6936, 5284],

/***/ 5307:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/isTextInputElement.js ***!
  \********************************************************************************/
1410,

/***/ 5308:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DefaultEventPluginOrder.js ***!
  \*************************************************************************************/
1411,

/***/ 5309:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EnterLeaveEventPlugin.js ***!
  \***********************************************************************************/
[6937, 5277, 5270, 5310],

/***/ 5310:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticMouseEvent.js ***!
  \*********************************************************************************/
[6938, 5311, 5312, 5313],

/***/ 5311:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticUIEvent.js ***!
  \******************************************************************************/
[6939, 5289, 5305],

/***/ 5312:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ViewportMetrics.js ***!
  \*****************************************************************************/
1415,

/***/ 5313:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventModifierState.js ***!
  \***********************************************************************************/
1416,

/***/ 5314:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/HTMLDOMPropertyConfig.js ***!
  \***********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var DOMProperty = __webpack_require__(/*! ./DOMProperty */ 5272);
	
	var MUST_USE_PROPERTY = DOMProperty.injection.MUST_USE_PROPERTY;
	var HAS_BOOLEAN_VALUE = DOMProperty.injection.HAS_BOOLEAN_VALUE;
	var HAS_NUMERIC_VALUE = DOMProperty.injection.HAS_NUMERIC_VALUE;
	var HAS_POSITIVE_NUMERIC_VALUE = DOMProperty.injection.HAS_POSITIVE_NUMERIC_VALUE;
	var HAS_OVERLOADED_BOOLEAN_VALUE = DOMProperty.injection.HAS_OVERLOADED_BOOLEAN_VALUE;
	
	var HTMLDOMPropertyConfig = {
	  isCustomAttribute: RegExp.prototype.test.bind(new RegExp('^(data|aria)-[' + DOMProperty.ATTRIBUTE_NAME_CHAR + ']*$')),
	  Properties: {
	    /**
	     * Standard Properties
	     */
	    accept: 0,
	    acceptCharset: 0,
	    accessKey: 0,
	    action: 0,
	    allowFullScreen: HAS_BOOLEAN_VALUE,
	    allowTransparency: 0,
	    alt: 0,
	    // specifies target context for links with `preload` type
	    as: 0,
	    async: HAS_BOOLEAN_VALUE,
	    autoComplete: 0,
	    // autoFocus is polyfilled/normalized by AutoFocusUtils
	    // autoFocus: HAS_BOOLEAN_VALUE,
	    autoPlay: HAS_BOOLEAN_VALUE,
	    capture: HAS_BOOLEAN_VALUE,
	    cellPadding: 0,
	    cellSpacing: 0,
	    charSet: 0,
	    challenge: 0,
	    checked: MUST_USE_PROPERTY | HAS_BOOLEAN_VALUE,
	    cite: 0,
	    classID: 0,
	    className: 0,
	    cols: HAS_POSITIVE_NUMERIC_VALUE,
	    colSpan: 0,
	    content: 0,
	    contentEditable: 0,
	    contextMenu: 0,
	    controls: HAS_BOOLEAN_VALUE,
	    coords: 0,
	    crossOrigin: 0,
	    data: 0, // For `<object />` acts as `src`.
	    dateTime: 0,
	    'default': HAS_BOOLEAN_VALUE,
	    defer: HAS_BOOLEAN_VALUE,
	    dir: 0,
	    disabled: HAS_BOOLEAN_VALUE,
	    download: HAS_OVERLOADED_BOOLEAN_VALUE,
	    draggable: 0,
	    encType: 0,
	    form: 0,
	    formAction: 0,
	    formEncType: 0,
	    formMethod: 0,
	    formNoValidate: HAS_BOOLEAN_VALUE,
	    formTarget: 0,
	    frameBorder: 0,
	    headers: 0,
	    height: 0,
	    hidden: HAS_BOOLEAN_VALUE,
	    high: 0,
	    href: 0,
	    hrefLang: 0,
	    htmlFor: 0,
	    httpEquiv: 0,
	    icon: 0,
	    id: 0,
	    inputMode: 0,
	    integrity: 0,
	    is: 0,
	    keyParams: 0,
	    keyType: 0,
	    kind: 0,
	    label: 0,
	    lang: 0,
	    list: 0,
	    loop: HAS_BOOLEAN_VALUE,
	    low: 0,
	    manifest: 0,
	    marginHeight: 0,
	    marginWidth: 0,
	    max: 0,
	    maxLength: 0,
	    media: 0,
	    mediaGroup: 0,
	    method: 0,
	    min: 0,
	    minLength: 0,
	    // Caution; `option.selected` is not updated if `select.multiple` is
	    // disabled with `removeAttribute`.
	    multiple: MUST_USE_PROPERTY | HAS_BOOLEAN_VALUE,
	    muted: MUST_USE_PROPERTY | HAS_BOOLEAN_VALUE,
	    name: 0,
	    nonce: 0,
	    noValidate: HAS_BOOLEAN_VALUE,
	    open: HAS_BOOLEAN_VALUE,
	    optimum: 0,
	    pattern: 0,
	    placeholder: 0,
	    playsInline: HAS_BOOLEAN_VALUE,
	    poster: 0,
	    preload: 0,
	    profile: 0,
	    radioGroup: 0,
	    readOnly: HAS_BOOLEAN_VALUE,
	    referrerPolicy: 0,
	    rel: 0,
	    required: HAS_BOOLEAN_VALUE,
	    reversed: HAS_BOOLEAN_VALUE,
	    role: 0,
	    rows: HAS_POSITIVE_NUMERIC_VALUE,
	    rowSpan: HAS_NUMERIC_VALUE,
	    sandbox: 0,
	    scope: 0,
	    scoped: HAS_BOOLEAN_VALUE,
	    scrolling: 0,
	    seamless: HAS_BOOLEAN_VALUE,
	    selected: MUST_USE_PROPERTY | HAS_BOOLEAN_VALUE,
	    shape: 0,
	    size: HAS_POSITIVE_NUMERIC_VALUE,
	    sizes: 0,
	    span: HAS_POSITIVE_NUMERIC_VALUE,
	    spellCheck: 0,
	    src: 0,
	    srcDoc: 0,
	    srcLang: 0,
	    srcSet: 0,
	    start: HAS_NUMERIC_VALUE,
	    step: 0,
	    style: 0,
	    summary: 0,
	    tabIndex: 0,
	    target: 0,
	    title: 0,
	    // Setting .type throws on non-<input> tags
	    type: 0,
	    useMap: 0,
	    value: 0,
	    width: 0,
	    wmode: 0,
	    wrap: 0,
	
	    /**
	     * RDFa Properties
	     */
	    about: 0,
	    datatype: 0,
	    inlist: 0,
	    prefix: 0,
	    // property is also supported for OpenGraph in meta tags.
	    property: 0,
	    resource: 0,
	    'typeof': 0,
	    vocab: 0,
	
	    /**
	     * Non-standard Properties
	     */
	    // autoCapitalize and autoCorrect are supported in Mobile Safari for
	    // keyboard hints.
	    autoCapitalize: 0,
	    autoCorrect: 0,
	    // autoSave allows WebKit/Blink to persist values of input fields on page reloads
	    autoSave: 0,
	    // color is for Safari mask-icon link
	    color: 0,
	    // itemProp, itemScope, itemType are for
	    // Microdata support. See http://schema.org/docs/gs.html
	    itemProp: 0,
	    itemScope: HAS_BOOLEAN_VALUE,
	    itemType: 0,
	    // itemID and itemRef are for Microdata support as well but
	    // only specified in the WHATWG spec document. See
	    // https://html.spec.whatwg.org/multipage/microdata.html#microdata-dom-api
	    itemID: 0,
	    itemRef: 0,
	    // results show looking glass icon and recent searches on input
	    // search fields in WebKit/Blink
	    results: 0,
	    // IE-only attribute that specifies security restrictions on an iframe
	    // as an alternative to the sandbox attribute on IE<10
	    security: 0,
	    // IE-only attribute that controls focus behavior
	    unselectable: 0
	  },
	  DOMAttributeNames: {
	    acceptCharset: 'accept-charset',
	    className: 'class',
	    htmlFor: 'for',
	    httpEquiv: 'http-equiv'
	  },
	  DOMPropertyNames: {}
	};
	
	module.exports = HTMLDOMPropertyConfig;

/***/ },

/***/ 5315:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactComponentBrowserEnvironment.js ***!
  \**********************************************************************************************/
[6941, 5316, 5327],

/***/ 5316:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMChildrenOperations.js ***!
  \***********************************************************************************/
[6942, 5317, 5323, 5270, 5298, 5320, 5319, 5321],

/***/ 5317:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMLazyTree.js ***!
  \*************************************************************************/
[6943, 5318, 5319, 5320, 5321],

/***/ 5318:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMNamespaces.js ***!
  \***************************************************************************/
1421,

/***/ 5319:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/setInnerHTML.js ***!
  \**************************************************************************/
[6944, 5284, 5318, 5320],

/***/ 5320:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \************************************************************************************************/
1423,

/***/ 5321:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/setTextContent.js ***!
  \****************************************************************************/
[6945, 5284, 5322, 5319],

/***/ 5322:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/escapeTextContentForBrowser.js ***!
  \*****************************************************************************************/
1425,

/***/ 5323:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/Danger.js ***!
  \********************************************************************/
[6946, 5271, 5317, 5284, 5324, 5248, 5244],

/***/ 5324:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/createNodesFromMarkup.js ***!
  \******************************************************************************/
[6264, 5284, 5325, 5326, 5244],

/***/ 5325:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/createArrayFromMixed.js ***!
  \*****************************************************************************/
[6265, 5244],

/***/ 5326:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/getMarkupWrap.js ***!
  \**********************************************************************/
[6266, 5284, 5244],

/***/ 5327:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMIDOperations.js ***!
  \**********************************************************************************/
[6947, 5316, 5270],

/***/ 5328:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponent.js ***!
  \*******************************************************************************/
[6948, 5271, 5240, 5329, 5331, 5317, 5318, 5272, 5339, 5278, 5279, 5341, 5273, 5270, 5344, 5347, 5348, 5349, 5298, 5350, 5369, 5248, 5322, 5244, 5306, 5359, 5372, 5247],

/***/ 5329:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/AutoFocusUtils.js ***!
  \****************************************************************************/
[6949, 5270, 5330],

/***/ 5330:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/focusNode.js ***!
  \******************************************************************/
100,

/***/ 5331:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CSSPropertyOperations.js ***!
  \***********************************************************************************/
[6950, 5332, 5284, 5298, 5333, 5335, 5336, 5338, 5247],

/***/ 5332:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CSSProperty.js ***!
  \*************************************************************************/
1431,

/***/ 5333:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/camelizeStyleName.js ***!
  \**************************************************************************/
[6272, 5334],

/***/ 5334:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/camelize.js ***!
  \*****************************************************************/
104,

/***/ 5335:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/dangerousStyleValue.js ***!
  \*********************************************************************************/
[6951, 5332, 5247],

/***/ 5336:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/hyphenateStyleName.js ***!
  \***************************************************************************/
[6274, 5337],

/***/ 5337:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/hyphenate.js ***!
  \******************************************************************/
107,

/***/ 5338:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/memoizeStringOnly.js ***!
  \**************************************************************************/
108,

/***/ 5339:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMPropertyOperations.js ***!
  \***********************************************************************************/
[6952, 5272, 5270, 5298, 5340, 5247],

/***/ 5340:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/quoteAttributeValueForBrowser.js ***!
  \*******************************************************************************************/
[6953, 5322],

/***/ 5341:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactBrowserEventEmitter.js ***!
  \**************************************************************************************/
[6954, 5240, 5279, 5342, 5312, 5343, 5306],

/***/ 5342:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEventEmitterMixin.js ***!
  \************************************************************************************/
[6955, 5278],

/***/ 5343:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getVendorPrefixedEventName.js ***!
  \****************************************************************************************/
[6956, 5284],

/***/ 5344:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMInput.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5271),
	    _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var DOMPropertyOperations = __webpack_require__(/*! ./DOMPropertyOperations */ 5339);
	var LinkedValueUtils = __webpack_require__(/*! ./LinkedValueUtils */ 5345);
	var ReactDOMComponentTree = __webpack_require__(/*! ./ReactDOMComponentTree */ 5270);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 5292);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var didWarnValueLink = false;
	var didWarnCheckedLink = false;
	var didWarnValueDefaultValue = false;
	var didWarnCheckedDefaultChecked = false;
	var didWarnControlledToUncontrolled = false;
	var didWarnUncontrolledToControlled = false;
	
	function forceUpdateIfMounted() {
	  if (this._rootNodeID) {
	    // DOM component is still mounted; update
	    ReactDOMInput.updateWrapper(this);
	  }
	}
	
	function isControlled(props) {
	  var usesChecked = props.type === 'checkbox' || props.type === 'radio';
	  return usesChecked ? props.checked != null : props.value != null;
	}
	
	/**
	 * Implements an <input> host component that allows setting these optional
	 * props: `checked`, `value`, `defaultChecked`, and `defaultValue`.
	 *
	 * If `checked` or `value` are not supplied (or null/undefined), user actions
	 * that affect the checked state or value will trigger updates to the element.
	 *
	 * If they are supplied (and not null/undefined), the rendered element will not
	 * trigger updates to the element. Instead, the props must change in order for
	 * the rendered element to be updated.
	 *
	 * The rendered element will be initialized as unchecked (or `defaultChecked`)
	 * with an empty value (or `defaultValue`).
	 *
	 * @see http://www.w3.org/TR/2012/WD-html5-20121025/the-input-element.html
	 */
	var ReactDOMInput = {
	  getHostProps: function (inst, props) {
	    var value = LinkedValueUtils.getValue(props);
	    var checked = LinkedValueUtils.getChecked(props);
	
	    var hostProps = _assign({
	      // Make sure we set .type before any other properties (setting .value
	      // before .type means .value is lost in IE11 and below)
	      type: undefined,
	      // Make sure we set .step before .value (setting .value before .step
	      // means .value is rounded on mount, based upon step precision)
	      step: undefined,
	      // Make sure we set .min & .max before .value (to ensure proper order
	      // in corner cases such as min or max deriving from value, e.g. Issue #7170)
	      min: undefined,
	      max: undefined
	    }, props, {
	      defaultChecked: undefined,
	      defaultValue: undefined,
	      value: value != null ? value : inst._wrapperState.initialValue,
	      checked: checked != null ? checked : inst._wrapperState.initialChecked,
	      onChange: inst._wrapperState.onChange
	    });
	
	    return hostProps;
	  },
	
	  mountWrapper: function (inst, props) {
	    if (true) {
	      LinkedValueUtils.checkPropTypes('input', props, inst._currentElement._owner);
	
	      var owner = inst._currentElement._owner;
	
	      if (props.valueLink !== undefined && !didWarnValueLink) {
	         true ? warning(false, '`valueLink` prop on `input` is deprecated; set `value` and `onChange` instead.') : void 0;
	        didWarnValueLink = true;
	      }
	      if (props.checkedLink !== undefined && !didWarnCheckedLink) {
	         true ? warning(false, '`checkedLink` prop on `input` is deprecated; set `value` and `onChange` instead.') : void 0;
	        didWarnCheckedLink = true;
	      }
	      if (props.checked !== undefined && props.defaultChecked !== undefined && !didWarnCheckedDefaultChecked) {
	         true ? warning(false, '%s contains an input of type %s with both checked and defaultChecked props. ' + 'Input elements must be either controlled or uncontrolled ' + '(specify either the checked prop, or the defaultChecked prop, but not ' + 'both). Decide between using a controlled or uncontrolled input ' + 'element and remove one of these props. More info: ' + 'https://fb.me/react-controlled-components', owner && owner.getName() || 'A component', props.type) : void 0;
	        didWarnCheckedDefaultChecked = true;
	      }
	      if (props.value !== undefined && props.defaultValue !== undefined && !didWarnValueDefaultValue) {
	         true ? warning(false, '%s contains an input of type %s with both value and defaultValue props. ' + 'Input elements must be either controlled or uncontrolled ' + '(specify either the value prop, or the defaultValue prop, but not ' + 'both). Decide between using a controlled or uncontrolled input ' + 'element and remove one of these props. More info: ' + 'https://fb.me/react-controlled-components', owner && owner.getName() || 'A component', props.type) : void 0;
	        didWarnValueDefaultValue = true;
	      }
	    }
	
	    var defaultValue = props.defaultValue;
	    inst._wrapperState = {
	      initialChecked: props.checked != null ? props.checked : props.defaultChecked,
	      initialValue: props.value != null ? props.value : defaultValue,
	      listeners: null,
	      onChange: _handleChange.bind(inst)
	    };
	
	    if (true) {
	      inst._wrapperState.controlled = isControlled(props);
	    }
	  },
	
	  updateWrapper: function (inst) {
	    var props = inst._currentElement.props;
	
	    if (true) {
	      var controlled = isControlled(props);
	      var owner = inst._currentElement._owner;
	
	      if (!inst._wrapperState.controlled && controlled && !didWarnUncontrolledToControlled) {
	         true ? warning(false, '%s is changing an uncontrolled input of type %s to be controlled. ' + 'Input elements should not switch from uncontrolled to controlled (or vice versa). ' + 'Decide between using a controlled or uncontrolled input ' + 'element for the lifetime of the component. More info: https://fb.me/react-controlled-components', owner && owner.getName() || 'A component', props.type) : void 0;
	        didWarnUncontrolledToControlled = true;
	      }
	      if (inst._wrapperState.controlled && !controlled && !didWarnControlledToUncontrolled) {
	         true ? warning(false, '%s is changing a controlled input of type %s to be uncontrolled. ' + 'Input elements should not switch from controlled to uncontrolled (or vice versa). ' + 'Decide between using a controlled or uncontrolled input ' + 'element for the lifetime of the component. More info: https://fb.me/react-controlled-components', owner && owner.getName() || 'A component', props.type) : void 0;
	        didWarnControlledToUncontrolled = true;
	      }
	    }
	
	    // TODO: Shouldn't this be getChecked(props)?
	    var checked = props.checked;
	    if (checked != null) {
	      DOMPropertyOperations.setValueForProperty(ReactDOMComponentTree.getNodeFromInstance(inst), 'checked', checked || false);
	    }
	
	    var node = ReactDOMComponentTree.getNodeFromInstance(inst);
	    var value = LinkedValueUtils.getValue(props);
	    if (value != null) {
	
	      // Cast `value` to a string to ensure the value is set correctly. While
	      // browsers typically do this as necessary, jsdom doesn't.
	      var newValue = '' + value;
	
	      // To avoid side effects (such as losing text selection), only set value if changed
	      if (newValue !== node.value) {
	        node.value = newValue;
	      }
	    } else {
	      if (props.value == null && props.defaultValue != null) {
	        // In Chrome, assigning defaultValue to certain input types triggers input validation.
	        // For number inputs, the display value loses trailing decimal points. For email inputs,
	        // Chrome raises "The specified value <x> is not a valid email address".
	        //
	        // Here we check to see if the defaultValue has actually changed, avoiding these problems
	        // when the user is inputting text
	        //
	        // https://github.com/facebook/react/issues/7253
	        if (node.defaultValue !== '' + props.defaultValue) {
	          node.defaultValue = '' + props.defaultValue;
	        }
	      }
	      if (props.checked == null && props.defaultChecked != null) {
	        node.defaultChecked = !!props.defaultChecked;
	      }
	    }
	  },
	
	  postMountWrapper: function (inst) {
	    var props = inst._currentElement.props;
	
	    // This is in postMount because we need access to the DOM node, which is not
	    // available until after the component has mounted.
	    var node = ReactDOMComponentTree.getNodeFromInstance(inst);
	
	    // Detach value from defaultValue. We won't do anything if we're working on
	    // submit or reset inputs as those values & defaultValues are linked. They
	    // are not resetable nodes so this operation doesn't matter and actually
	    // removes browser-default values (eg "Submit Query") when no value is
	    // provided.
	
	    switch (props.type) {
	      case 'submit':
	      case 'reset':
	        break;
	      case 'color':
	      case 'date':
	      case 'datetime':
	      case 'datetime-local':
	      case 'month':
	      case 'time':
	      case 'week':
	        // This fixes the no-show issue on iOS Safari and Android Chrome:
	        // https://github.com/facebook/react/issues/7233
	        node.value = '';
	        node.value = node.defaultValue;
	        break;
	      default:
	        node.value = node.value;
	        break;
	    }
	
	    // Normally, we'd just do `node.checked = node.checked` upon initial mount, less this bug
	    // this is needed to work around a chrome bug where setting defaultChecked
	    // will sometimes influence the value of checked (even after detachment).
	    // Reference: https://bugs.chromium.org/p/chromium/issues/detail?id=608416
	    // We need to temporarily unset name to avoid disrupting radio button groups.
	    var name = node.name;
	    if (name !== '') {
	      node.name = '';
	    }
	    node.defaultChecked = !node.defaultChecked;
	    node.defaultChecked = !node.defaultChecked;
	    if (name !== '') {
	      node.name = name;
	    }
	  }
	};
	
	function _handleChange(event) {
	  var props = this._currentElement.props;
	
	  var returnValue = LinkedValueUtils.executeOnChange(props, event);
	
	  // Here we use asap to wait until all updates have propagated, which
	  // is important when using controlled components within layers:
	  // https://github.com/facebook/react/issues/1698
	  ReactUpdates.asap(forceUpdateIfMounted, this);
	
	  var name = props.name;
	  if (props.type === 'radio' && name != null) {
	    var rootNode = ReactDOMComponentTree.getNodeFromInstance(this);
	    var queryRoot = rootNode;
	
	    while (queryRoot.parentNode) {
	      queryRoot = queryRoot.parentNode;
	    }
	
	    // If `rootNode.form` was non-null, then we could try `form.elements`,
	    // but that sometimes behaves strangely in IE8. We could also try using
	    // `form.getElementsByName`, but that will only return direct children
	    // and won't include inputs that use the HTML5 `form=` attribute. Since
	    // the input might not even be in a form, let's just use the global
	    // `querySelectorAll` to ensure we don't miss anything.
	    var group = queryRoot.querySelectorAll('input[name=' + JSON.stringify('' + name) + '][type="radio"]');
	
	    for (var i = 0; i < group.length; i++) {
	      var otherNode = group[i];
	      if (otherNode === rootNode || otherNode.form !== rootNode.form) {
	        continue;
	      }
	      // This will throw if radio buttons rendered by different copies of React
	      // and the same name are rendered into the same form (same as #1939).
	      // That's probably okay; we don't support it just as we don't support
	      // mixing React radio buttons with non-React ones.
	      var otherInstance = ReactDOMComponentTree.getInstanceFromNode(otherNode);
	      !otherInstance ?  true ? invariant(false, 'ReactDOMInput: Mixing React and non-React radio inputs with the same `name` is not supported.') : _prodInvariant('90') : void 0;
	      // If this is a controlled radio button group, forcing the input that
	      // was previously checked to update will cause it to be come re-checked
	      // as appropriate.
	      ReactUpdates.asap(forceUpdateIfMounted, otherInstance);
	    }
	  }
	
	  return returnValue;
	}
	
	module.exports = ReactDOMInput;

/***/ },

/***/ 5345:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/LinkedValueUtils.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5271);
	
	var React = __webpack_require__(/*! react/lib/React */ 5239);
	var ReactPropTypesSecret = __webpack_require__(/*! ./ReactPropTypesSecret */ 5346);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var hasReadOnlyValue = {
	  'button': true,
	  'checkbox': true,
	  'image': true,
	  'hidden': true,
	  'radio': true,
	  'reset': true,
	  'submit': true
	};
	
	function _assertSingleLink(inputProps) {
	  !(inputProps.checkedLink == null || inputProps.valueLink == null) ?  true ? invariant(false, 'Cannot provide a checkedLink and a valueLink. If you want to use checkedLink, you probably don\'t want to use valueLink and vice versa.') : _prodInvariant('87') : void 0;
	}
	function _assertValueLink(inputProps) {
	  _assertSingleLink(inputProps);
	  !(inputProps.value == null && inputProps.onChange == null) ?  true ? invariant(false, 'Cannot provide a valueLink and a value or onChange event. If you want to use value or onChange, you probably don\'t want to use valueLink.') : _prodInvariant('88') : void 0;
	}
	
	function _assertCheckedLink(inputProps) {
	  _assertSingleLink(inputProps);
	  !(inputProps.checked == null && inputProps.onChange == null) ?  true ? invariant(false, 'Cannot provide a checkedLink and a checked property or onChange event. If you want to use checked or onChange, you probably don\'t want to use checkedLink') : _prodInvariant('89') : void 0;
	}
	
	var propTypes = {
	  value: function (props, propName, componentName) {
	    if (!props[propName] || hasReadOnlyValue[props.type] || props.onChange || props.readOnly || props.disabled) {
	      return null;
	    }
	    return new Error('You provided a `value` prop to a form field without an ' + '`onChange` handler. This will render a read-only field. If ' + 'the field should be mutable use `defaultValue`. Otherwise, ' + 'set either `onChange` or `readOnly`.');
	  },
	  checked: function (props, propName, componentName) {
	    if (!props[propName] || props.onChange || props.readOnly || props.disabled) {
	      return null;
	    }
	    return new Error('You provided a `checked` prop to a form field without an ' + '`onChange` handler. This will render a read-only field. If ' + 'the field should be mutable use `defaultChecked`. Otherwise, ' + 'set either `onChange` or `readOnly`.');
	  },
	  onChange: React.PropTypes.func
	};
	
	var loggedTypeFailures = {};
	function getDeclarationErrorAddendum(owner) {
	  if (owner) {
	    var name = owner.getName();
	    if (name) {
	      return ' Check the render method of `' + name + '`.';
	    }
	  }
	  return '';
	}
	
	/**
	 * Provide a linked `value` attribute for controlled forms. You should not use
	 * this outside of the ReactDOM controlled form components.
	 */
	var LinkedValueUtils = {
	  checkPropTypes: function (tagName, props, owner) {
	    for (var propName in propTypes) {
	      if (propTypes.hasOwnProperty(propName)) {
	        var error = propTypes[propName](props, propName, tagName, 'prop', null, ReactPropTypesSecret);
	      }
	      if (error instanceof Error && !(error.message in loggedTypeFailures)) {
	        // Only monitor this failure once because there tends to be a lot of the
	        // same error.
	        loggedTypeFailures[error.message] = true;
	
	        var addendum = getDeclarationErrorAddendum(owner);
	         true ? warning(false, 'Failed form propType: %s%s', error.message, addendum) : void 0;
	      }
	    }
	  },
	
	  /**
	   * @param {object} inputProps Props for form component
	   * @return {*} current value of the input either from value prop or link.
	   */
	  getValue: function (inputProps) {
	    if (inputProps.valueLink) {
	      _assertValueLink(inputProps);
	      return inputProps.valueLink.value;
	    }
	    return inputProps.value;
	  },
	
	  /**
	   * @param {object} inputProps Props for form component
	   * @return {*} current checked status of the input either from checked prop
	   *             or link.
	   */
	  getChecked: function (inputProps) {
	    if (inputProps.checkedLink) {
	      _assertCheckedLink(inputProps);
	      return inputProps.checkedLink.value;
	    }
	    return inputProps.checked;
	  },
	
	  /**
	   * @param {object} inputProps Props for form component
	   * @param {SyntheticEvent} event change event to handle
	   */
	  executeOnChange: function (inputProps, event) {
	    if (inputProps.valueLink) {
	      _assertValueLink(inputProps);
	      return inputProps.valueLink.requestChange(event.target.value);
	    } else if (inputProps.checkedLink) {
	      _assertCheckedLink(inputProps);
	      return inputProps.checkedLink.requestChange(event.target.checked);
	    } else if (inputProps.onChange) {
	      return inputProps.onChange.call(undefined, event);
	    }
	  }
	};
	
	module.exports = LinkedValueUtils;

/***/ },

/***/ 5346:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactPropTypesSecret.js ***!
  \**********************************************************************************/
1367,

/***/ 5347:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMOption.js ***!
  \****************************************************************************/
[6959, 5240, 5239, 5270, 5348, 5247],

/***/ 5348:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMSelect.js ***!
  \****************************************************************************/
[6960, 5240, 5345, 5270, 5292, 5247],

/***/ 5349:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTextarea.js ***!
  \******************************************************************************/
[6961, 5271, 5240, 5345, 5270, 5292, 5244, 5247],

/***/ 5350:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMultiChild.js ***!
  \*****************************************************************************/
[6962, 5271, 5351, 5352, 5298, 5246, 5295, 5353, 5248, 5368, 5244],

/***/ 5351:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactComponentEnvironment.js ***!
  \***************************************************************************************/
[6963, 5271, 5244],

/***/ 5352:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInstanceMap.js ***!
  \******************************************************************************/
1446,

/***/ 5353:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactChildReconciler.js ***!
  \**********************************************************************************/
[6964, 5295, 5354, 5364, 5360, 5365, 5247, 5262, 5262],

/***/ 5354:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/instantiateReactComponent.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5271),
	    _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var ReactCompositeComponent = __webpack_require__(/*! ./ReactCompositeComponent */ 5355);
	var ReactEmptyComponent = __webpack_require__(/*! ./ReactEmptyComponent */ 5361);
	var ReactHostComponent = __webpack_require__(/*! ./ReactHostComponent */ 5362);
	
	var getNextDebugID = __webpack_require__(/*! ./getNextDebugID */ 5363);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	// To avoid a cyclic dependency, we create the final class in this module
	var ReactCompositeComponentWrapper = function (element) {
	  this.construct(element);
	};
	_assign(ReactCompositeComponentWrapper.prototype, ReactCompositeComponent, {
	  _instantiateReactComponent: instantiateReactComponent
	});
	
	function getDeclarationErrorAddendum(owner) {
	  if (owner) {
	    var name = owner.getName();
	    if (name) {
	      return ' Check the render method of `' + name + '`.';
	    }
	  }
	  return '';
	}
	
	/**
	 * Check if the type reference is a known internal type. I.e. not a user
	 * provided composite type.
	 *
	 * @param {function} type
	 * @return {boolean} Returns true if this is a valid internal type.
	 */
	function isInternalComponentType(type) {
	  return typeof type === 'function' && typeof type.prototype !== 'undefined' && typeof type.prototype.mountComponent === 'function' && typeof type.prototype.receiveComponent === 'function';
	}
	
	/**
	 * Given a ReactNode, create an instance that will actually be mounted.
	 *
	 * @param {ReactNode} node
	 * @param {boolean} shouldHaveDebugID
	 * @return {object} A new instance of the element's constructor.
	 * @protected
	 */
	function instantiateReactComponent(node, shouldHaveDebugID) {
	  var instance;
	
	  if (node === null || node === false) {
	    instance = ReactEmptyComponent.create(instantiateReactComponent);
	  } else if (typeof node === 'object') {
	    var element = node;
	    var type = element.type;
	    if (typeof type !== 'function' && typeof type !== 'string') {
	      var info = '';
	      if (true) {
	        if (type === undefined || typeof type === 'object' && type !== null && Object.keys(type).length === 0) {
	          info += ' You likely forgot to export your component from the file ' + 'it\'s defined in.';
	        }
	      }
	      info += getDeclarationErrorAddendum(element._owner);
	       true ?  true ? invariant(false, 'Element type is invalid: expected a string (for built-in components) or a class/function (for composite components) but got: %s.%s', type == null ? type : typeof type, info) : _prodInvariant('130', type == null ? type : typeof type, info) : void 0;
	    }
	
	    // Special case string values
	    if (typeof element.type === 'string') {
	      instance = ReactHostComponent.createInternalComponent(element);
	    } else if (isInternalComponentType(element.type)) {
	      // This is temporarily available for custom components that are not string
	      // representations. I.e. ART. Once those are updated to use the string
	      // representation, we can drop this code path.
	      instance = new element.type(element);
	
	      // We renamed this. Allow the old name for compat. :(
	      if (!instance.getHostNode) {
	        instance.getHostNode = instance.getNativeNode;
	      }
	    } else {
	      instance = new ReactCompositeComponentWrapper(element);
	    }
	  } else if (typeof node === 'string' || typeof node === 'number') {
	    instance = ReactHostComponent.createInstanceForText(node);
	  } else {
	     true ?  true ? invariant(false, 'Encountered invalid React node of type %s', typeof node) : _prodInvariant('131', typeof node) : void 0;
	  }
	
	  if (true) {
	     true ? warning(typeof instance.mountComponent === 'function' && typeof instance.receiveComponent === 'function' && typeof instance.getHostNode === 'function' && typeof instance.unmountComponent === 'function', 'Only React Components can be mounted.') : void 0;
	  }
	
	  // These two fields are used by the DOM and ART diffing algorithms
	  // respectively. Instead of using expandos on components, we should be
	  // storing the state needed by the diffing algorithms elsewhere.
	  instance._mountIndex = 0;
	  instance._mountImage = null;
	
	  if (true) {
	    instance._debugID = shouldHaveDebugID ? getNextDebugID() : 0;
	  }
	
	  // Internal instances should fully constructed at this point, so they should
	  // not get any new fields added to them at this point.
	  if (true) {
	    if (Object.preventExtensions) {
	      Object.preventExtensions(instance);
	    }
	  }
	
	  return instance;
	}
	
	module.exports = instantiateReactComponent;

/***/ },

/***/ 5355:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactCompositeComponent.js ***!
  \*************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2013-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5271),
	    _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var React = __webpack_require__(/*! react/lib/React */ 5239);
	var ReactComponentEnvironment = __webpack_require__(/*! ./ReactComponentEnvironment */ 5351);
	var ReactCurrentOwner = __webpack_require__(/*! react/lib/ReactCurrentOwner */ 5246);
	var ReactErrorUtils = __webpack_require__(/*! ./ReactErrorUtils */ 5281);
	var ReactInstanceMap = __webpack_require__(/*! ./ReactInstanceMap */ 5352);
	var ReactInstrumentation = __webpack_require__(/*! ./ReactInstrumentation */ 5298);
	var ReactNodeTypes = __webpack_require__(/*! ./ReactNodeTypes */ 5356);
	var ReactReconciler = __webpack_require__(/*! ./ReactReconciler */ 5295);
	
	if (true) {
	  var checkReactTypeSpec = __webpack_require__(/*! ./checkReactTypeSpec */ 5357);
	}
	
	var emptyObject = __webpack_require__(/*! fbjs/lib/emptyObject */ 5256);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var shallowEqual = __webpack_require__(/*! fbjs/lib/shallowEqual */ 5359);
	var shouldUpdateReactComponent = __webpack_require__(/*! ./shouldUpdateReactComponent */ 5360);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var CompositeTypes = {
	  ImpureClass: 0,
	  PureClass: 1,
	  StatelessFunctional: 2
	};
	
	function StatelessComponent(Component) {}
	StatelessComponent.prototype.render = function () {
	  var Component = ReactInstanceMap.get(this)._currentElement.type;
	  var element = Component(this.props, this.context, this.updater);
	  warnIfInvalidElement(Component, element);
	  return element;
	};
	
	function warnIfInvalidElement(Component, element) {
	  if (true) {
	     true ? warning(element === null || element === false || React.isValidElement(element), '%s(...): A valid React element (or null) must be returned. You may have ' + 'returned undefined, an array or some other invalid object.', Component.displayName || Component.name || 'Component') : void 0;
	     true ? warning(!Component.childContextTypes, '%s(...): childContextTypes cannot be defined on a functional component.', Component.displayName || Component.name || 'Component') : void 0;
	  }
	}
	
	function shouldConstruct(Component) {
	  return !!(Component.prototype && Component.prototype.isReactComponent);
	}
	
	function isPureComponent(Component) {
	  return !!(Component.prototype && Component.prototype.isPureReactComponent);
	}
	
	// Separated into a function to contain deoptimizations caused by try/finally.
	function measureLifeCyclePerf(fn, debugID, timerType) {
	  if (debugID === 0) {
	    // Top-level wrappers (see ReactMount) and empty components (see
	    // ReactDOMEmptyComponent) are invisible to hooks and devtools.
	    // Both are implementation details that should go away in the future.
	    return fn();
	  }
	
	  ReactInstrumentation.debugTool.onBeginLifeCycleTimer(debugID, timerType);
	  try {
	    return fn();
	  } finally {
	    ReactInstrumentation.debugTool.onEndLifeCycleTimer(debugID, timerType);
	  }
	}
	
	/**
	 * ------------------ The Life-Cycle of a Composite Component ------------------
	 *
	 * - constructor: Initialization of state. The instance is now retained.
	 *   - componentWillMount
	 *   - render
	 *   - [children's constructors]
	 *     - [children's componentWillMount and render]
	 *     - [children's componentDidMount]
	 *     - componentDidMount
	 *
	 *       Update Phases:
	 *       - componentWillReceiveProps (only called if parent updated)
	 *       - shouldComponentUpdate
	 *         - componentWillUpdate
	 *           - render
	 *           - [children's constructors or receive props phases]
	 *         - componentDidUpdate
	 *
	 *     - componentWillUnmount
	 *     - [children's componentWillUnmount]
	 *   - [children destroyed]
	 * - (destroyed): The instance is now blank, released by React and ready for GC.
	 *
	 * -----------------------------------------------------------------------------
	 */
	
	/**
	 * An incrementing ID assigned to each component when it is mounted. This is
	 * used to enforce the order in which `ReactUpdates` updates dirty components.
	 *
	 * @private
	 */
	var nextMountID = 1;
	
	/**
	 * @lends {ReactCompositeComponent.prototype}
	 */
	var ReactCompositeComponent = {
	
	  /**
	   * Base constructor for all composite component.
	   *
	   * @param {ReactElement} element
	   * @final
	   * @internal
	   */
	  construct: function (element) {
	    this._currentElement = element;
	    this._rootNodeID = 0;
	    this._compositeType = null;
	    this._instance = null;
	    this._hostParent = null;
	    this._hostContainerInfo = null;
	
	    // See ReactUpdateQueue
	    this._updateBatchNumber = null;
	    this._pendingElement = null;
	    this._pendingStateQueue = null;
	    this._pendingReplaceState = false;
	    this._pendingForceUpdate = false;
	
	    this._renderedNodeType = null;
	    this._renderedComponent = null;
	    this._context = null;
	    this._mountOrder = 0;
	    this._topLevelWrapper = null;
	
	    // See ReactUpdates and ReactUpdateQueue.
	    this._pendingCallbacks = null;
	
	    // ComponentWillUnmount shall only be called once
	    this._calledComponentWillUnmount = false;
	
	    if (true) {
	      this._warnedAboutRefsInRender = false;
	    }
	  },
	
	  /**
	   * Initializes the component, renders markup, and registers event listeners.
	   *
	   * @param {ReactReconcileTransaction|ReactServerRenderingTransaction} transaction
	   * @param {?object} hostParent
	   * @param {?object} hostContainerInfo
	   * @param {?object} context
	   * @return {?string} Rendered markup to be inserted into the DOM.
	   * @final
	   * @internal
	   */
	  mountComponent: function (transaction, hostParent, hostContainerInfo, context) {
	    var _this = this;
	
	    this._context = context;
	    this._mountOrder = nextMountID++;
	    this._hostParent = hostParent;
	    this._hostContainerInfo = hostContainerInfo;
	
	    var publicProps = this._currentElement.props;
	    var publicContext = this._processContext(context);
	
	    var Component = this._currentElement.type;
	
	    var updateQueue = transaction.getUpdateQueue();
	
	    // Initialize the public class
	    var doConstruct = shouldConstruct(Component);
	    var inst = this._constructComponent(doConstruct, publicProps, publicContext, updateQueue);
	    var renderedElement;
	
	    // Support functional components
	    if (!doConstruct && (inst == null || inst.render == null)) {
	      renderedElement = inst;
	      warnIfInvalidElement(Component, renderedElement);
	      !(inst === null || inst === false || React.isValidElement(inst)) ?  true ? invariant(false, '%s(...): A valid React element (or null) must be returned. You may have returned undefined, an array or some other invalid object.', Component.displayName || Component.name || 'Component') : _prodInvariant('105', Component.displayName || Component.name || 'Component') : void 0;
	      inst = new StatelessComponent(Component);
	      this._compositeType = CompositeTypes.StatelessFunctional;
	    } else {
	      if (isPureComponent(Component)) {
	        this._compositeType = CompositeTypes.PureClass;
	      } else {
	        this._compositeType = CompositeTypes.ImpureClass;
	      }
	    }
	
	    if (true) {
	      // This will throw later in _renderValidatedComponent, but add an early
	      // warning now to help debugging
	      if (inst.render == null) {
	         true ? warning(false, '%s(...): No `render` method found on the returned component ' + 'instance: you may have forgotten to define `render`.', Component.displayName || Component.name || 'Component') : void 0;
	      }
	
	      var propsMutated = inst.props !== publicProps;
	      var componentName = Component.displayName || Component.name || 'Component';
	
	       true ? warning(inst.props === undefined || !propsMutated, '%s(...): When calling super() in `%s`, make sure to pass ' + 'up the same props that your component\'s constructor was passed.', componentName, componentName) : void 0;
	    }
	
	    // These should be set up in the constructor, but as a convenience for
	    // simpler class abstractions, we set them up after the fact.
	    inst.props = publicProps;
	    inst.context = publicContext;
	    inst.refs = emptyObject;
	    inst.updater = updateQueue;
	
	    this._instance = inst;
	
	    // Store a reference from the instance back to the internal representation
	    ReactInstanceMap.set(inst, this);
	
	    if (true) {
	      // Since plain JS classes are defined without any special initialization
	      // logic, we can not catch common errors early. Therefore, we have to
	      // catch them here, at initialization time, instead.
	       true ? warning(!inst.getInitialState || inst.getInitialState.isReactClassApproved || inst.state, 'getInitialState was defined on %s, a plain JavaScript class. ' + 'This is only supported for classes created using React.createClass. ' + 'Did you mean to define a state property instead?', this.getName() || 'a component') : void 0;
	       true ? warning(!inst.getDefaultProps || inst.getDefaultProps.isReactClassApproved, 'getDefaultProps was defined on %s, a plain JavaScript class. ' + 'This is only supported for classes created using React.createClass. ' + 'Use a static property to define defaultProps instead.', this.getName() || 'a component') : void 0;
	       true ? warning(!inst.propTypes, 'propTypes was defined as an instance property on %s. Use a static ' + 'property to define propTypes instead.', this.getName() || 'a component') : void 0;
	       true ? warning(!inst.contextTypes, 'contextTypes was defined as an instance property on %s. Use a ' + 'static property to define contextTypes instead.', this.getName() || 'a component') : void 0;
	       true ? warning(typeof inst.componentShouldUpdate !== 'function', '%s has a method called ' + 'componentShouldUpdate(). Did you mean shouldComponentUpdate()? ' + 'The name is phrased as a question because the function is ' + 'expected to return a value.', this.getName() || 'A component') : void 0;
	       true ? warning(typeof inst.componentDidUnmount !== 'function', '%s has a method called ' + 'componentDidUnmount(). But there is no such lifecycle method. ' + 'Did you mean componentWillUnmount()?', this.getName() || 'A component') : void 0;
	       true ? warning(typeof inst.componentWillRecieveProps !== 'function', '%s has a method called ' + 'componentWillRecieveProps(). Did you mean componentWillReceiveProps()?', this.getName() || 'A component') : void 0;
	    }
	
	    var initialState = inst.state;
	    if (initialState === undefined) {
	      inst.state = initialState = null;
	    }
	    !(typeof initialState === 'object' && !Array.isArray(initialState)) ?  true ? invariant(false, '%s.state: must be set to an object or null', this.getName() || 'ReactCompositeComponent') : _prodInvariant('106', this.getName() || 'ReactCompositeComponent') : void 0;
	
	    this._pendingStateQueue = null;
	    this._pendingReplaceState = false;
	    this._pendingForceUpdate = false;
	
	    var markup;
	    if (inst.unstable_handleError) {
	      markup = this.performInitialMountWithErrorHandling(renderedElement, hostParent, hostContainerInfo, transaction, context);
	    } else {
	      markup = this.performInitialMount(renderedElement, hostParent, hostContainerInfo, transaction, context);
	    }
	
	    if (inst.componentDidMount) {
	      if (true) {
	        transaction.getReactMountReady().enqueue(function () {
	          measureLifeCyclePerf(function () {
	            return inst.componentDidMount();
	          }, _this._debugID, 'componentDidMount');
	        });
	      } else {
	        transaction.getReactMountReady().enqueue(inst.componentDidMount, inst);
	      }
	    }
	
	    return markup;
	  },
	
	  _constructComponent: function (doConstruct, publicProps, publicContext, updateQueue) {
	    if (true) {
	      ReactCurrentOwner.current = this;
	      try {
	        return this._constructComponentWithoutOwner(doConstruct, publicProps, publicContext, updateQueue);
	      } finally {
	        ReactCurrentOwner.current = null;
	      }
	    } else {
	      return this._constructComponentWithoutOwner(doConstruct, publicProps, publicContext, updateQueue);
	    }
	  },
	
	  _constructComponentWithoutOwner: function (doConstruct, publicProps, publicContext, updateQueue) {
	    var Component = this._currentElement.type;
	
	    if (doConstruct) {
	      if (true) {
	        return measureLifeCyclePerf(function () {
	          return new Component(publicProps, publicContext, updateQueue);
	        }, this._debugID, 'ctor');
	      } else {
	        return new Component(publicProps, publicContext, updateQueue);
	      }
	    }
	
	    // This can still be an instance in case of factory components
	    // but we'll count this as time spent rendering as the more common case.
	    if (true) {
	      return measureLifeCyclePerf(function () {
	        return Component(publicProps, publicContext, updateQueue);
	      }, this._debugID, 'render');
	    } else {
	      return Component(publicProps, publicContext, updateQueue);
	    }
	  },
	
	  performInitialMountWithErrorHandling: function (renderedElement, hostParent, hostContainerInfo, transaction, context) {
	    var markup;
	    var checkpoint = transaction.checkpoint();
	    try {
	      markup = this.performInitialMount(renderedElement, hostParent, hostContainerInfo, transaction, context);
	    } catch (e) {
	      // Roll back to checkpoint, handle error (which may add items to the transaction), and take a new checkpoint
	      transaction.rollback(checkpoint);
	      this._instance.unstable_handleError(e);
	      if (this._pendingStateQueue) {
	        this._instance.state = this._processPendingState(this._instance.props, this._instance.context);
	      }
	      checkpoint = transaction.checkpoint();
	
	      this._renderedComponent.unmountComponent(true);
	      transaction.rollback(checkpoint);
	
	      // Try again - we've informed the component about the error, so they can render an error message this time.
	      // If this throws again, the error will bubble up (and can be caught by a higher error boundary).
	      markup = this.performInitialMount(renderedElement, hostParent, hostContainerInfo, transaction, context);
	    }
	    return markup;
	  },
	
	  performInitialMount: function (renderedElement, hostParent, hostContainerInfo, transaction, context) {
	    var inst = this._instance;
	
	    var debugID = 0;
	    if (true) {
	      debugID = this._debugID;
	    }
	
	    if (inst.componentWillMount) {
	      if (true) {
	        measureLifeCyclePerf(function () {
	          return inst.componentWillMount();
	        }, debugID, 'componentWillMount');
	      } else {
	        inst.componentWillMount();
	      }
	      // When mounting, calls to `setState` by `componentWillMount` will set
	      // `this._pendingStateQueue` without triggering a re-render.
	      if (this._pendingStateQueue) {
	        inst.state = this._processPendingState(inst.props, inst.context);
	      }
	    }
	
	    // If not a stateless component, we now render
	    if (renderedElement === undefined) {
	      renderedElement = this._renderValidatedComponent();
	    }
	
	    var nodeType = ReactNodeTypes.getType(renderedElement);
	    this._renderedNodeType = nodeType;
	    var child = this._instantiateReactComponent(renderedElement, nodeType !== ReactNodeTypes.EMPTY /* shouldHaveDebugID */
	    );
	    this._renderedComponent = child;
	
	    var markup = ReactReconciler.mountComponent(child, transaction, hostParent, hostContainerInfo, this._processChildContext(context), debugID);
	
	    if (true) {
	      if (debugID !== 0) {
	        var childDebugIDs = child._debugID !== 0 ? [child._debugID] : [];
	        ReactInstrumentation.debugTool.onSetChildren(debugID, childDebugIDs);
	      }
	    }
	
	    return markup;
	  },
	
	  getHostNode: function () {
	    return ReactReconciler.getHostNode(this._renderedComponent);
	  },
	
	  /**
	   * Releases any resources allocated by `mountComponent`.
	   *
	   * @final
	   * @internal
	   */
	  unmountComponent: function (safely) {
	    if (!this._renderedComponent) {
	      return;
	    }
	
	    var inst = this._instance;
	
	    if (inst.componentWillUnmount && !inst._calledComponentWillUnmount) {
	      inst._calledComponentWillUnmount = true;
	
	      if (safely) {
	        var name = this.getName() + '.componentWillUnmount()';
	        ReactErrorUtils.invokeGuardedCallback(name, inst.componentWillUnmount.bind(inst));
	      } else {
	        if (true) {
	          measureLifeCyclePerf(function () {
	            return inst.componentWillUnmount();
	          }, this._debugID, 'componentWillUnmount');
	        } else {
	          inst.componentWillUnmount();
	        }
	      }
	    }
	
	    if (this._renderedComponent) {
	      ReactReconciler.unmountComponent(this._renderedComponent, safely);
	      this._renderedNodeType = null;
	      this._renderedComponent = null;
	      this._instance = null;
	    }
	
	    // Reset pending fields
	    // Even if this component is scheduled for another update in ReactUpdates,
	    // it would still be ignored because these fields are reset.
	    this._pendingStateQueue = null;
	    this._pendingReplaceState = false;
	    this._pendingForceUpdate = false;
	    this._pendingCallbacks = null;
	    this._pendingElement = null;
	
	    // These fields do not really need to be reset since this object is no
	    // longer accessible.
	    this._context = null;
	    this._rootNodeID = 0;
	    this._topLevelWrapper = null;
	
	    // Delete the reference from the instance to this internal representation
	    // which allow the internals to be properly cleaned up even if the user
	    // leaks a reference to the public instance.
	    ReactInstanceMap.remove(inst);
	
	    // Some existing components rely on inst.props even after they've been
	    // destroyed (in event handlers).
	    // TODO: inst.props = null;
	    // TODO: inst.state = null;
	    // TODO: inst.context = null;
	  },
	
	  /**
	   * Filters the context object to only contain keys specified in
	   * `contextTypes`
	   *
	   * @param {object} context
	   * @return {?object}
	   * @private
	   */
	  _maskContext: function (context) {
	    var Component = this._currentElement.type;
	    var contextTypes = Component.contextTypes;
	    if (!contextTypes) {
	      return emptyObject;
	    }
	    var maskedContext = {};
	    for (var contextName in contextTypes) {
	      maskedContext[contextName] = context[contextName];
	    }
	    return maskedContext;
	  },
	
	  /**
	   * Filters the context object to only contain keys specified in
	   * `contextTypes`, and asserts that they are valid.
	   *
	   * @param {object} context
	   * @return {?object}
	   * @private
	   */
	  _processContext: function (context) {
	    var maskedContext = this._maskContext(context);
	    if (true) {
	      var Component = this._currentElement.type;
	      if (Component.contextTypes) {
	        this._checkContextTypes(Component.contextTypes, maskedContext, 'context');
	      }
	    }
	    return maskedContext;
	  },
	
	  /**
	   * @param {object} currentContext
	   * @return {object}
	   * @private
	   */
	  _processChildContext: function (currentContext) {
	    var Component = this._currentElement.type;
	    var inst = this._instance;
	    var childContext;
	
	    if (inst.getChildContext) {
	      if (true) {
	        ReactInstrumentation.debugTool.onBeginProcessingChildContext();
	        try {
	          childContext = inst.getChildContext();
	        } finally {
	          ReactInstrumentation.debugTool.onEndProcessingChildContext();
	        }
	      } else {
	        childContext = inst.getChildContext();
	      }
	    }
	
	    if (childContext) {
	      !(typeof Component.childContextTypes === 'object') ?  true ? invariant(false, '%s.getChildContext(): childContextTypes must be defined in order to use getChildContext().', this.getName() || 'ReactCompositeComponent') : _prodInvariant('107', this.getName() || 'ReactCompositeComponent') : void 0;
	      if (true) {
	        this._checkContextTypes(Component.childContextTypes, childContext, 'childContext');
	      }
	      for (var name in childContext) {
	        !(name in Component.childContextTypes) ?  true ? invariant(false, '%s.getChildContext(): key "%s" is not defined in childContextTypes.', this.getName() || 'ReactCompositeComponent', name) : _prodInvariant('108', this.getName() || 'ReactCompositeComponent', name) : void 0;
	      }
	      return _assign({}, currentContext, childContext);
	    }
	    return currentContext;
	  },
	
	  /**
	   * Assert that the context types are valid
	   *
	   * @param {object} typeSpecs Map of context field to a ReactPropType
	   * @param {object} values Runtime values that need to be type-checked
	   * @param {string} location e.g. "prop", "context", "child context"
	   * @private
	   */
	  _checkContextTypes: function (typeSpecs, values, location) {
	    if (true) {
	      checkReactTypeSpec(typeSpecs, values, location, this.getName(), null, this._debugID);
	    }
	  },
	
	  receiveComponent: function (nextElement, transaction, nextContext) {
	    var prevElement = this._currentElement;
	    var prevContext = this._context;
	
	    this._pendingElement = null;
	
	    this.updateComponent(transaction, prevElement, nextElement, prevContext, nextContext);
	  },
	
	  /**
	   * If any of `_pendingElement`, `_pendingStateQueue`, or `_pendingForceUpdate`
	   * is set, update the component.
	   *
	   * @param {ReactReconcileTransaction} transaction
	   * @internal
	   */
	  performUpdateIfNecessary: function (transaction) {
	    if (this._pendingElement != null) {
	      ReactReconciler.receiveComponent(this, this._pendingElement, transaction, this._context);
	    } else if (this._pendingStateQueue !== null || this._pendingForceUpdate) {
	      this.updateComponent(transaction, this._currentElement, this._currentElement, this._context, this._context);
	    } else {
	      this._updateBatchNumber = null;
	    }
	  },
	
	  /**
	   * Perform an update to a mounted component. The componentWillReceiveProps and
	   * shouldComponentUpdate methods are called, then (assuming the update isn't
	   * skipped) the remaining update lifecycle methods are called and the DOM
	   * representation is updated.
	   *
	   * By default, this implements React's rendering and reconciliation algorithm.
	   * Sophisticated clients may wish to override this.
	   *
	   * @param {ReactReconcileTransaction} transaction
	   * @param {ReactElement} prevParentElement
	   * @param {ReactElement} nextParentElement
	   * @internal
	   * @overridable
	   */
	  updateComponent: function (transaction, prevParentElement, nextParentElement, prevUnmaskedContext, nextUnmaskedContext) {
	    var inst = this._instance;
	    !(inst != null) ?  true ? invariant(false, 'Attempted to update component `%s` that has already been unmounted (or failed to mount).', this.getName() || 'ReactCompositeComponent') : _prodInvariant('136', this.getName() || 'ReactCompositeComponent') : void 0;
	
	    var willReceive = false;
	    var nextContext;
	
	    // Determine if the context has changed or not
	    if (this._context === nextUnmaskedContext) {
	      nextContext = inst.context;
	    } else {
	      nextContext = this._processContext(nextUnmaskedContext);
	      willReceive = true;
	    }
	
	    var prevProps = prevParentElement.props;
	    var nextProps = nextParentElement.props;
	
	    // Not a simple state update but a props update
	    if (prevParentElement !== nextParentElement) {
	      willReceive = true;
	    }
	
	    // An update here will schedule an update but immediately set
	    // _pendingStateQueue which will ensure that any state updates gets
	    // immediately reconciled instead of waiting for the next batch.
	    if (willReceive && inst.componentWillReceiveProps) {
	      if (true) {
	        measureLifeCyclePerf(function () {
	          return inst.componentWillReceiveProps(nextProps, nextContext);
	        }, this._debugID, 'componentWillReceiveProps');
	      } else {
	        inst.componentWillReceiveProps(nextProps, nextContext);
	      }
	    }
	
	    var nextState = this._processPendingState(nextProps, nextContext);
	    var shouldUpdate = true;
	
	    if (!this._pendingForceUpdate) {
	      if (inst.shouldComponentUpdate) {
	        if (true) {
	          shouldUpdate = measureLifeCyclePerf(function () {
	            return inst.shouldComponentUpdate(nextProps, nextState, nextContext);
	          }, this._debugID, 'shouldComponentUpdate');
	        } else {
	          shouldUpdate = inst.shouldComponentUpdate(nextProps, nextState, nextContext);
	        }
	      } else {
	        if (this._compositeType === CompositeTypes.PureClass) {
	          shouldUpdate = !shallowEqual(prevProps, nextProps) || !shallowEqual(inst.state, nextState);
	        }
	      }
	    }
	
	    if (true) {
	       true ? warning(shouldUpdate !== undefined, '%s.shouldComponentUpdate(): Returned undefined instead of a ' + 'boolean value. Make sure to return true or false.', this.getName() || 'ReactCompositeComponent') : void 0;
	    }
	
	    this._updateBatchNumber = null;
	    if (shouldUpdate) {
	      this._pendingForceUpdate = false;
	      // Will set `this.props`, `this.state` and `this.context`.
	      this._performComponentUpdate(nextParentElement, nextProps, nextState, nextContext, transaction, nextUnmaskedContext);
	    } else {
	      // If it's determined that a component should not update, we still want
	      // to set props and state but we shortcut the rest of the update.
	      this._currentElement = nextParentElement;
	      this._context = nextUnmaskedContext;
	      inst.props = nextProps;
	      inst.state = nextState;
	      inst.context = nextContext;
	    }
	  },
	
	  _processPendingState: function (props, context) {
	    var inst = this._instance;
	    var queue = this._pendingStateQueue;
	    var replace = this._pendingReplaceState;
	    this._pendingReplaceState = false;
	    this._pendingStateQueue = null;
	
	    if (!queue) {
	      return inst.state;
	    }
	
	    if (replace && queue.length === 1) {
	      return queue[0];
	    }
	
	    var nextState = _assign({}, replace ? queue[0] : inst.state);
	    for (var i = replace ? 1 : 0; i < queue.length; i++) {
	      var partial = queue[i];
	      _assign(nextState, typeof partial === 'function' ? partial.call(inst, nextState, props, context) : partial);
	    }
	
	    return nextState;
	  },
	
	  /**
	   * Merges new props and state, notifies delegate methods of update and
	   * performs update.
	   *
	   * @param {ReactElement} nextElement Next element
	   * @param {object} nextProps Next public object to set as properties.
	   * @param {?object} nextState Next object to set as state.
	   * @param {?object} nextContext Next public object to set as context.
	   * @param {ReactReconcileTransaction} transaction
	   * @param {?object} unmaskedContext
	   * @private
	   */
	  _performComponentUpdate: function (nextElement, nextProps, nextState, nextContext, transaction, unmaskedContext) {
	    var _this2 = this;
	
	    var inst = this._instance;
	
	    var hasComponentDidUpdate = Boolean(inst.componentDidUpdate);
	    var prevProps;
	    var prevState;
	    var prevContext;
	    if (hasComponentDidUpdate) {
	      prevProps = inst.props;
	      prevState = inst.state;
	      prevContext = inst.context;
	    }
	
	    if (inst.componentWillUpdate) {
	      if (true) {
	        measureLifeCyclePerf(function () {
	          return inst.componentWillUpdate(nextProps, nextState, nextContext);
	        }, this._debugID, 'componentWillUpdate');
	      } else {
	        inst.componentWillUpdate(nextProps, nextState, nextContext);
	      }
	    }
	
	    this._currentElement = nextElement;
	    this._context = unmaskedContext;
	    inst.props = nextProps;
	    inst.state = nextState;
	    inst.context = nextContext;
	
	    this._updateRenderedComponent(transaction, unmaskedContext);
	
	    if (hasComponentDidUpdate) {
	      if (true) {
	        transaction.getReactMountReady().enqueue(function () {
	          measureLifeCyclePerf(inst.componentDidUpdate.bind(inst, prevProps, prevState, prevContext), _this2._debugID, 'componentDidUpdate');
	        });
	      } else {
	        transaction.getReactMountReady().enqueue(inst.componentDidUpdate.bind(inst, prevProps, prevState, prevContext), inst);
	      }
	    }
	  },
	
	  /**
	   * Call the component's `render` method and update the DOM accordingly.
	   *
	   * @param {ReactReconcileTransaction} transaction
	   * @internal
	   */
	  _updateRenderedComponent: function (transaction, context) {
	    var prevComponentInstance = this._renderedComponent;
	    var prevRenderedElement = prevComponentInstance._currentElement;
	    var nextRenderedElement = this._renderValidatedComponent();
	
	    var debugID = 0;
	    if (true) {
	      debugID = this._debugID;
	    }
	
	    if (shouldUpdateReactComponent(prevRenderedElement, nextRenderedElement)) {
	      ReactReconciler.receiveComponent(prevComponentInstance, nextRenderedElement, transaction, this._processChildContext(context));
	    } else {
	      var oldHostNode = ReactReconciler.getHostNode(prevComponentInstance);
	      ReactReconciler.unmountComponent(prevComponentInstance, false);
	
	      var nodeType = ReactNodeTypes.getType(nextRenderedElement);
	      this._renderedNodeType = nodeType;
	      var child = this._instantiateReactComponent(nextRenderedElement, nodeType !== ReactNodeTypes.EMPTY /* shouldHaveDebugID */
	      );
	      this._renderedComponent = child;
	
	      var nextMarkup = ReactReconciler.mountComponent(child, transaction, this._hostParent, this._hostContainerInfo, this._processChildContext(context), debugID);
	
	      if (true) {
	        if (debugID !== 0) {
	          var childDebugIDs = child._debugID !== 0 ? [child._debugID] : [];
	          ReactInstrumentation.debugTool.onSetChildren(debugID, childDebugIDs);
	        }
	      }
	
	      this._replaceNodeWithMarkup(oldHostNode, nextMarkup, prevComponentInstance);
	    }
	  },
	
	  /**
	   * Overridden in shallow rendering.
	   *
	   * @protected
	   */
	  _replaceNodeWithMarkup: function (oldHostNode, nextMarkup, prevInstance) {
	    ReactComponentEnvironment.replaceNodeWithMarkup(oldHostNode, nextMarkup, prevInstance);
	  },
	
	  /**
	   * @protected
	   */
	  _renderValidatedComponentWithoutOwnerOrContext: function () {
	    var inst = this._instance;
	    var renderedElement;
	
	    if (true) {
	      renderedElement = measureLifeCyclePerf(function () {
	        return inst.render();
	      }, this._debugID, 'render');
	    } else {
	      renderedElement = inst.render();
	    }
	
	    if (true) {
	      // We allow auto-mocks to proceed as if they're returning null.
	      if (renderedElement === undefined && inst.render._isMockFunction) {
	        // This is probably bad practice. Consider warning here and
	        // deprecating this convenience.
	        renderedElement = null;
	      }
	    }
	
	    return renderedElement;
	  },
	
	  /**
	   * @private
	   */
	  _renderValidatedComponent: function () {
	    var renderedElement;
	    if (true) {
	      ReactCurrentOwner.current = this;
	      try {
	        renderedElement = this._renderValidatedComponentWithoutOwnerOrContext();
	      } finally {
	        ReactCurrentOwner.current = null;
	      }
	    } else {
	      renderedElement = this._renderValidatedComponentWithoutOwnerOrContext();
	    }
	    !(
	    // TODO: An `isValidNode` function would probably be more appropriate
	    renderedElement === null || renderedElement === false || React.isValidElement(renderedElement)) ?  true ? invariant(false, '%s.render(): A valid React element (or null) must be returned. You may have returned undefined, an array or some other invalid object.', this.getName() || 'ReactCompositeComponent') : _prodInvariant('109', this.getName() || 'ReactCompositeComponent') : void 0;
	
	    return renderedElement;
	  },
	
	  /**
	   * Lazily allocates the refs object and stores `component` as `ref`.
	   *
	   * @param {string} ref Reference name.
	   * @param {component} component Component to store as `ref`.
	   * @final
	   * @private
	   */
	  attachRef: function (ref, component) {
	    var inst = this.getPublicInstance();
	    !(inst != null) ?  true ? invariant(false, 'Stateless function components cannot have refs.') : _prodInvariant('110') : void 0;
	    var publicComponentInstance = component.getPublicInstance();
	    if (true) {
	      var componentName = component && component.getName ? component.getName() : 'a component';
	       true ? warning(publicComponentInstance != null || component._compositeType !== CompositeTypes.StatelessFunctional, 'Stateless function components cannot be given refs ' + '(See ref "%s" in %s created by %s). ' + 'Attempts to access this ref will fail.', ref, componentName, this.getName()) : void 0;
	    }
	    var refs = inst.refs === emptyObject ? inst.refs = {} : inst.refs;
	    refs[ref] = publicComponentInstance;
	  },
	
	  /**
	   * Detaches a reference name.
	   *
	   * @param {string} ref Name to dereference.
	   * @final
	   * @private
	   */
	  detachRef: function (ref) {
	    var refs = this.getPublicInstance().refs;
	    delete refs[ref];
	  },
	
	  /**
	   * Get a text description of the component that can be used to identify it
	   * in error messages.
	   * @return {string} The name or null.
	   * @internal
	   */
	  getName: function () {
	    var type = this._currentElement.type;
	    var constructor = this._instance && this._instance.constructor;
	    return type.displayName || constructor && constructor.displayName || type.name || constructor && constructor.name || null;
	  },
	
	  /**
	   * Get the publicly accessible representation of this component - i.e. what
	   * is exposed by refs and returned by render. Can be null for stateless
	   * components.
	   *
	   * @return {ReactComponent} the public component instance.
	   * @internal
	   */
	  getPublicInstance: function () {
	    var inst = this._instance;
	    if (this._compositeType === CompositeTypes.StatelessFunctional) {
	      return null;
	    }
	    return inst;
	  },
	
	  // Stub
	  _instantiateReactComponent: null
	
	};
	
	module.exports = ReactCompositeComponent;

/***/ },

/***/ 5356:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactNodeTypes.js ***!
  \****************************************************************************/
[6967, 5271, 5239, 5244],

/***/ 5357:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/checkReactTypeSpec.js ***!
  \********************************************************************************/
[6968, 5271, 5358, 5346, 5244, 5247, 5262, 5262],

/***/ 5358:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactPropTypeLocationNames.js ***!
  \****************************************************************************************/
1362,

/***/ 5359:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/shallowEqual.js ***!
  \*********************************************************************/
128,

/***/ 5360:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/shouldUpdateReactComponent.js ***!
  \****************************************************************************************/
1453,

/***/ 5361:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEmptyComponent.js ***!
  \*********************************************************************************/
1454,

/***/ 5362:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactHostComponent.js ***!
  \********************************************************************************/
[6969, 5271, 5244],

/***/ 5363:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getNextDebugID.js ***!
  \****************************************************************************/
1456,

/***/ 5364:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/KeyEscapeUtils.js ***!
  \****************************************************************************/
1357,

/***/ 5365:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/traverseAllChildren.js ***!
  \*********************************************************************************/
[6970, 5271, 5246, 5366, 5367, 5244, 5364, 5247],

/***/ 5366:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactElementSymbol.js ***!
  \********************************************************************************/
1354,

/***/ 5367:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getIteratorFn.js ***!
  \***************************************************************************/
1356,

/***/ 5368:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/flattenChildren.js ***!
  \*****************************************************************************/
[6971, 5364, 5365, 5247, 5262, 5262],

/***/ 5369:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerRenderingTransaction.js ***!
  \*********************************************************************************************/
[6972, 5240, 5286, 5304, 5298, 5370],

/***/ 5370:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerUpdateQueue.js ***!
  \************************************************************************************/
[6973, 5371, 5247],

/***/ 5371:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactUpdateQueue.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2015-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5271);
	
	var ReactCurrentOwner = __webpack_require__(/*! react/lib/ReactCurrentOwner */ 5246);
	var ReactInstanceMap = __webpack_require__(/*! ./ReactInstanceMap */ 5352);
	var ReactInstrumentation = __webpack_require__(/*! ./ReactInstrumentation */ 5298);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 5292);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5244);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	function enqueueUpdate(internalInstance) {
	  ReactUpdates.enqueueUpdate(internalInstance);
	}
	
	function formatUnexpectedArgument(arg) {
	  var type = typeof arg;
	  if (type !== 'object') {
	    return type;
	  }
	  var displayName = arg.constructor && arg.constructor.name || type;
	  var keys = Object.keys(arg);
	  if (keys.length > 0 && keys.length < 20) {
	    return displayName + ' (keys: ' + keys.join(', ') + ')';
	  }
	  return displayName;
	}
	
	function getInternalInstanceReadyForUpdate(publicInstance, callerName) {
	  var internalInstance = ReactInstanceMap.get(publicInstance);
	  if (!internalInstance) {
	    if (true) {
	      var ctor = publicInstance.constructor;
	      // Only warn when we have a callerName. Otherwise we should be silent.
	      // We're probably calling from enqueueCallback. We don't want to warn
	      // there because we already warned for the corresponding lifecycle method.
	       true ? warning(!callerName, '%s(...): Can only update a mounted or mounting component. ' + 'This usually means you called %s() on an unmounted component. ' + 'This is a no-op. Please check the code for the %s component.', callerName, callerName, ctor && (ctor.displayName || ctor.name) || 'ReactClass') : void 0;
	    }
	    return null;
	  }
	
	  if (true) {
	     true ? warning(ReactCurrentOwner.current == null, '%s(...): Cannot update during an existing state transition (such as ' + 'within `render` or another component\'s constructor). Render methods ' + 'should be a pure function of props and state; constructor ' + 'side-effects are an anti-pattern, but can be moved to ' + '`componentWillMount`.', callerName) : void 0;
	  }
	
	  return internalInstance;
	}
	
	/**
	 * ReactUpdateQueue allows for state updates to be scheduled into a later
	 * reconciliation step.
	 */
	var ReactUpdateQueue = {
	
	  /**
	   * Checks whether or not this composite component is mounted.
	   * @param {ReactClass} publicInstance The instance we want to test.
	   * @return {boolean} True if mounted, false otherwise.
	   * @protected
	   * @final
	   */
	  isMounted: function (publicInstance) {
	    if (true) {
	      var owner = ReactCurrentOwner.current;
	      if (owner !== null) {
	         true ? warning(owner._warnedAboutRefsInRender, '%s is accessing isMounted inside its render() function. ' + 'render() should be a pure function of props and state. It should ' + 'never access something that requires stale data from the previous ' + 'render, such as refs. Move this logic to componentDidMount and ' + 'componentDidUpdate instead.', owner.getName() || 'A component') : void 0;
	        owner._warnedAboutRefsInRender = true;
	      }
	    }
	    var internalInstance = ReactInstanceMap.get(publicInstance);
	    if (internalInstance) {
	      // During componentWillMount and render this will still be null but after
	      // that will always render to something. At least for now. So we can use
	      // this hack.
	      return !!internalInstance._renderedComponent;
	    } else {
	      return false;
	    }
	  },
	
	  /**
	   * Enqueue a callback that will be executed after all the pending updates
	   * have processed.
	   *
	   * @param {ReactClass} publicInstance The instance to use as `this` context.
	   * @param {?function} callback Called after state is updated.
	   * @param {string} callerName Name of the calling function in the public API.
	   * @internal
	   */
	  enqueueCallback: function (publicInstance, callback, callerName) {
	    ReactUpdateQueue.validateCallback(callback, callerName);
	    var internalInstance = getInternalInstanceReadyForUpdate(publicInstance);
	
	    // Previously we would throw an error if we didn't have an internal
	    // instance. Since we want to make it a no-op instead, we mirror the same
	    // behavior we have in other enqueue* methods.
	    // We also need to ignore callbacks in componentWillMount. See
	    // enqueueUpdates.
	    if (!internalInstance) {
	      return null;
	    }
	
	    if (internalInstance._pendingCallbacks) {
	      internalInstance._pendingCallbacks.push(callback);
	    } else {
	      internalInstance._pendingCallbacks = [callback];
	    }
	    // TODO: The callback here is ignored when setState is called from
	    // componentWillMount. Either fix it or disallow doing so completely in
	    // favor of getInitialState. Alternatively, we can disallow
	    // componentWillMount during server-side rendering.
	    enqueueUpdate(internalInstance);
	  },
	
	  enqueueCallbackInternal: function (internalInstance, callback) {
	    if (internalInstance._pendingCallbacks) {
	      internalInstance._pendingCallbacks.push(callback);
	    } else {
	      internalInstance._pendingCallbacks = [callback];
	    }
	    enqueueUpdate(internalInstance);
	  },
	
	  /**
	   * Forces an update. This should only be invoked when it is known with
	   * certainty that we are **not** in a DOM transaction.
	   *
	   * You may want to call this when you know that some deeper aspect of the
	   * component's state has changed but `setState` was not called.
	   *
	   * This will not invoke `shouldComponentUpdate`, but it will invoke
	   * `componentWillUpdate` and `componentDidUpdate`.
	   *
	   * @param {ReactClass} publicInstance The instance that should rerender.
	   * @internal
	   */
	  enqueueForceUpdate: function (publicInstance) {
	    var internalInstance = getInternalInstanceReadyForUpdate(publicInstance, 'forceUpdate');
	
	    if (!internalInstance) {
	      return;
	    }
	
	    internalInstance._pendingForceUpdate = true;
	
	    enqueueUpdate(internalInstance);
	  },
	
	  /**
	   * Replaces all of the state. Always use this or `setState` to mutate state.
	   * You should treat `this.state` as immutable.
	   *
	   * There is no guarantee that `this.state` will be immediately updated, so
	   * accessing `this.state` after calling this method may return the old value.
	   *
	   * @param {ReactClass} publicInstance The instance that should rerender.
	   * @param {object} completeState Next state.
	   * @internal
	   */
	  enqueueReplaceState: function (publicInstance, completeState) {
	    var internalInstance = getInternalInstanceReadyForUpdate(publicInstance, 'replaceState');
	
	    if (!internalInstance) {
	      return;
	    }
	
	    internalInstance._pendingStateQueue = [completeState];
	    internalInstance._pendingReplaceState = true;
	
	    enqueueUpdate(internalInstance);
	  },
	
	  /**
	   * Sets a subset of the state. This only exists because _pendingState is
	   * internal. This provides a merging strategy that is not available to deep
	   * properties which is confusing. TODO: Expose pendingState or don't use it
	   * during the merge.
	   *
	   * @param {ReactClass} publicInstance The instance that should rerender.
	   * @param {object} partialState Next partial state to be merged with state.
	   * @internal
	   */
	  enqueueSetState: function (publicInstance, partialState) {
	    if (true) {
	      ReactInstrumentation.debugTool.onSetState();
	       true ? warning(partialState != null, 'setState(...): You passed an undefined or null state object; ' + 'instead, use forceUpdate().') : void 0;
	    }
	
	    var internalInstance = getInternalInstanceReadyForUpdate(publicInstance, 'setState');
	
	    if (!internalInstance) {
	      return;
	    }
	
	    var queue = internalInstance._pendingStateQueue || (internalInstance._pendingStateQueue = []);
	    queue.push(partialState);
	
	    enqueueUpdate(internalInstance);
	  },
	
	  enqueueElementInternal: function (internalInstance, nextElement, nextContext) {
	    internalInstance._pendingElement = nextElement;
	    // TODO: introduce _pendingContext instead of setting it directly.
	    internalInstance._context = nextContext;
	    enqueueUpdate(internalInstance);
	  },
	
	  validateCallback: function (callback, callerName) {
	    !(!callback || typeof callback === 'function') ?  true ? invariant(false, '%s(...): Expected the last optional `callback` argument to be a function. Instead received: %s.', callerName, formatUnexpectedArgument(callback)) : _prodInvariant('122', callerName, formatUnexpectedArgument(callback)) : void 0;
	  }
	
	};
	
	module.exports = ReactUpdateQueue;

/***/ },

/***/ 5372:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/validateDOMNesting.js ***!
  \********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Copyright 2015-present, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * LICENSE file in the root directory of this source tree. An additional grant
	 * of patent rights can be found in the PATENTS file in the same directory.
	 *
	 */
	
	'use strict';
	
	var _assign = __webpack_require__(/*! object-assign */ 5240);
	
	var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 5248);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5247);
	
	var validateDOMNesting = emptyFunction;
	
	if (true) {
	  // This validation code was written based on the HTML5 parsing spec:
	  // https://html.spec.whatwg.org/multipage/syntax.html#has-an-element-in-scope
	  //
	  // Note: this does not catch all invalid nesting, nor does it try to (as it's
	  // not clear what practical benefit doing so provides); instead, we warn only
	  // for cases where the parser will give a parse tree differing from what React
	  // intended. For example, <b><div></div></b> is invalid but we don't warn
	  // because it still parses correctly; we do warn for other cases like nested
	  // <p> tags where the beginning of the second element implicitly closes the
	  // first, causing a confusing mess.
	
	  // https://html.spec.whatwg.org/multipage/syntax.html#special
	  var specialTags = ['address', 'applet', 'area', 'article', 'aside', 'base', 'basefont', 'bgsound', 'blockquote', 'body', 'br', 'button', 'caption', 'center', 'col', 'colgroup', 'dd', 'details', 'dir', 'div', 'dl', 'dt', 'embed', 'fieldset', 'figcaption', 'figure', 'footer', 'form', 'frame', 'frameset', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'head', 'header', 'hgroup', 'hr', 'html', 'iframe', 'img', 'input', 'isindex', 'li', 'link', 'listing', 'main', 'marquee', 'menu', 'menuitem', 'meta', 'nav', 'noembed', 'noframes', 'noscript', 'object', 'ol', 'p', 'param', 'plaintext', 'pre', 'script', 'section', 'select', 'source', 'style', 'summary', 'table', 'tbody', 'td', 'template', 'textarea', 'tfoot', 'th', 'thead', 'title', 'tr', 'track', 'ul', 'wbr', 'xmp'];
	
	  // https://html.spec.whatwg.org/multipage/syntax.html#has-an-element-in-scope
	  var inScopeTags = ['applet', 'caption', 'html', 'table', 'td', 'th', 'marquee', 'object', 'template',
	
	  // https://html.spec.whatwg.org/multipage/syntax.html#html-integration-point
	  // TODO: Distinguish by namespace here -- for <title>, including it here
	  // errs on the side of fewer warnings
	  'foreignObject', 'desc', 'title'];
	
	  // https://html.spec.whatwg.org/multipage/syntax.html#has-an-element-in-button-scope
	  var buttonScopeTags = inScopeTags.concat(['button']);
	
	  // https://html.spec.whatwg.org/multipage/syntax.html#generate-implied-end-tags
	  var impliedEndTags = ['dd', 'dt', 'li', 'option', 'optgroup', 'p', 'rp', 'rt'];
	
	  var emptyAncestorInfo = {
	    current: null,
	
	    formTag: null,
	    aTagInScope: null,
	    buttonTagInScope: null,
	    nobrTagInScope: null,
	    pTagInButtonScope: null,
	
	    listItemTagAutoclosing: null,
	    dlItemTagAutoclosing: null
	  };
	
	  var updatedAncestorInfo = function (oldInfo, tag, instance) {
	    var ancestorInfo = _assign({}, oldInfo || emptyAncestorInfo);
	    var info = { tag: tag, instance: instance };
	
	    if (inScopeTags.indexOf(tag) !== -1) {
	      ancestorInfo.aTagInScope = null;
	      ancestorInfo.buttonTagInScope = null;
	      ancestorInfo.nobrTagInScope = null;
	    }
	    if (buttonScopeTags.indexOf(tag) !== -1) {
	      ancestorInfo.pTagInButtonScope = null;
	    }
	
	    // See rules for 'li', 'dd', 'dt' start tags in
	    // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-inbody
	    if (specialTags.indexOf(tag) !== -1 && tag !== 'address' && tag !== 'div' && tag !== 'p') {
	      ancestorInfo.listItemTagAutoclosing = null;
	      ancestorInfo.dlItemTagAutoclosing = null;
	    }
	
	    ancestorInfo.current = info;
	
	    if (tag === 'form') {
	      ancestorInfo.formTag = info;
	    }
	    if (tag === 'a') {
	      ancestorInfo.aTagInScope = info;
	    }
	    if (tag === 'button') {
	      ancestorInfo.buttonTagInScope = info;
	    }
	    if (tag === 'nobr') {
	      ancestorInfo.nobrTagInScope = info;
	    }
	    if (tag === 'p') {
	      ancestorInfo.pTagInButtonScope = info;
	    }
	    if (tag === 'li') {
	      ancestorInfo.listItemTagAutoclosing = info;
	    }
	    if (tag === 'dd' || tag === 'dt') {
	      ancestorInfo.dlItemTagAutoclosing = info;
	    }
	
	    return ancestorInfo;
	  };
	
	  /**
	   * Returns whether
	   */
	  var isTagValidWithParent = function (tag, parentTag) {
	    // First, let's check if we're in an unusual parsing mode...
	    switch (parentTag) {
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-inselect
	      case 'select':
	        return tag === 'option' || tag === 'optgroup' || tag === '#text';
	      case 'optgroup':
	        return tag === 'option' || tag === '#text';
	      // Strictly speaking, seeing an <option> doesn't mean we're in a <select>
	      // but
	      case 'option':
	        return tag === '#text';
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-intd
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-incaption
	      // No special behavior since these rules fall back to "in body" mode for
	      // all except special table nodes which cause bad parsing behavior anyway.
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-intr
	      case 'tr':
	        return tag === 'th' || tag === 'td' || tag === 'style' || tag === 'script' || tag === 'template';
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-intbody
	      case 'tbody':
	      case 'thead':
	      case 'tfoot':
	        return tag === 'tr' || tag === 'style' || tag === 'script' || tag === 'template';
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-incolgroup
	      case 'colgroup':
	        return tag === 'col' || tag === 'template';
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-intable
	      case 'table':
	        return tag === 'caption' || tag === 'colgroup' || tag === 'tbody' || tag === 'tfoot' || tag === 'thead' || tag === 'style' || tag === 'script' || tag === 'template';
	
	      // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-inhead
	      case 'head':
	        return tag === 'base' || tag === 'basefont' || tag === 'bgsound' || tag === 'link' || tag === 'meta' || tag === 'title' || tag === 'noscript' || tag === 'noframes' || tag === 'style' || tag === 'script' || tag === 'template';
	
	      // https://html.spec.whatwg.org/multipage/semantics.html#the-html-element
	      case 'html':
	        return tag === 'head' || tag === 'body';
	      case '#document':
	        return tag === 'html';
	    }
	
	    // Probably in the "in body" parsing mode, so we outlaw only tag combos
	    // where the parsing rules cause implicit opens or closes to be added.
	    // https://html.spec.whatwg.org/multipage/syntax.html#parsing-main-inbody
	    switch (tag) {
	      case 'h1':
	      case 'h2':
	      case 'h3':
	      case 'h4':
	      case 'h5':
	      case 'h6':
	        return parentTag !== 'h1' && parentTag !== 'h2' && parentTag !== 'h3' && parentTag !== 'h4' && parentTag !== 'h5' && parentTag !== 'h6';
	
	      case 'rp':
	      case 'rt':
	        return impliedEndTags.indexOf(parentTag) === -1;
	
	      case 'body':
	      case 'caption':
	      case 'col':
	      case 'colgroup':
	      case 'frame':
	      case 'head':
	      case 'html':
	      case 'tbody':
	      case 'td':
	      case 'tfoot':
	      case 'th':
	      case 'thead':
	      case 'tr':
	        // These tags are only valid with a few parents that have special child
	        // parsing rules -- if we're down here, then none of those matched and
	        // so we allow it only if we don't know what the parent is, as all other
	        // cases are invalid.
	        return parentTag == null;
	    }
	
	    return true;
	  };
	
	  /**
	   * Returns whether
	   */
	  var findInvalidAncestorForTag = function (tag, ancestorInfo) {
	    switch (tag) {
	      case 'address':
	      case 'article':
	      case 'aside':
	      case 'blockquote':
	      case 'center':
	      case 'details':
	      case 'dialog':
	      case 'dir':
	      case 'div':
	      case 'dl':
	      case 'fieldset':
	      case 'figcaption':
	      case 'figure':
	      case 'footer':
	      case 'header':
	      case 'hgroup':
	      case 'main':
	      case 'menu':
	      case 'nav':
	      case 'ol':
	      case 'p':
	      case 'section':
	      case 'summary':
	      case 'ul':
	
	      case 'pre':
	      case 'listing':
	
	      case 'table':
	
	      case 'hr':
	
	      case 'xmp':
	
	      case 'h1':
	      case 'h2':
	      case 'h3':
	      case 'h4':
	      case 'h5':
	      case 'h6':
	        return ancestorInfo.pTagInButtonScope;
	
	      case 'form':
	        return ancestorInfo.formTag || ancestorInfo.pTagInButtonScope;
	
	      case 'li':
	        return ancestorInfo.listItemTagAutoclosing;
	
	      case 'dd':
	      case 'dt':
	        return ancestorInfo.dlItemTagAutoclosing;
	
	      case 'button':
	        return ancestorInfo.buttonTagInScope;
	
	      case 'a':
	        // Spec says something about storing a list of markers, but it sounds
	        // equivalent to this check.
	        return ancestorInfo.aTagInScope;
	
	      case 'nobr':
	        return ancestorInfo.nobrTagInScope;
	    }
	
	    return null;
	  };
	
	  /**
	   * Given a ReactCompositeComponent instance, return a list of its recursive
	   * owners, starting at the root and ending with the instance itself.
	   */
	  var findOwnerStack = function (instance) {
	    if (!instance) {
	      return [];
	    }
	
	    var stack = [];
	    do {
	      stack.push(instance);
	    } while (instance = instance._currentElement._owner);
	    stack.reverse();
	    return stack;
	  };
	
	  var didWarn = {};
	
	  validateDOMNesting = function (childTag, childText, childInstance, ancestorInfo) {
	    ancestorInfo = ancestorInfo || emptyAncestorInfo;
	    var parentInfo = ancestorInfo.current;
	    var parentTag = parentInfo && parentInfo.tag;
	
	    if (childText != null) {
	       true ? warning(childTag == null, 'validateDOMNesting: when childText is passed, childTag should be null') : void 0;
	      childTag = '#text';
	    }
	
	    var invalidParent = isTagValidWithParent(childTag, parentTag) ? null : parentInfo;
	    var invalidAncestor = invalidParent ? null : findInvalidAncestorForTag(childTag, ancestorInfo);
	    var problematic = invalidParent || invalidAncestor;
	
	    if (problematic) {
	      var ancestorTag = problematic.tag;
	      var ancestorInstance = problematic.instance;
	
	      var childOwner = childInstance && childInstance._currentElement._owner;
	      var ancestorOwner = ancestorInstance && ancestorInstance._currentElement._owner;
	
	      var childOwners = findOwnerStack(childOwner);
	      var ancestorOwners = findOwnerStack(ancestorOwner);
	
	      var minStackLen = Math.min(childOwners.length, ancestorOwners.length);
	      var i;
	
	      var deepestCommon = -1;
	      for (i = 0; i < minStackLen; i++) {
	        if (childOwners[i] === ancestorOwners[i]) {
	          deepestCommon = i;
	        } else {
	          break;
	        }
	      }
	
	      var UNKNOWN = '(unknown)';
	      var childOwnerNames = childOwners.slice(deepestCommon + 1).map(function (inst) {
	        return inst.getName() || UNKNOWN;
	      });
	      var ancestorOwnerNames = ancestorOwners.slice(deepestCommon + 1).map(function (inst) {
	        return inst.getName() || UNKNOWN;
	      });
	      var ownerInfo = [].concat(
	      // If the parent and child instances have a common owner ancestor, start
	      // with that -- otherwise we just start with the parent's owners.
	      deepestCommon !== -1 ? childOwners[deepestCommon].getName() || UNKNOWN : [], ancestorOwnerNames, ancestorTag,
	      // If we're warning about an invalid (non-parent) ancestry, add '...'
	      invalidAncestor ? ['...'] : [], childOwnerNames, childTag).join(' > ');
	
	      var warnKey = !!invalidParent + '|' + childTag + '|' + ancestorTag + '|' + ownerInfo;
	      if (didWarn[warnKey]) {
	        return;
	      }
	      didWarn[warnKey] = true;
	
	      var tagDisplayName = childTag;
	      var whitespaceInfo = '';
	      if (childTag === '#text') {
	        if (/\S/.test(childText)) {
	          tagDisplayName = 'Text nodes';
	        } else {
	          tagDisplayName = 'Whitespace text nodes';
	          whitespaceInfo = ' Make sure you don\'t have any extra whitespace between tags on ' + 'each line of your source code.';
	        }
	      } else {
	        tagDisplayName = '<' + childTag + '>';
	      }
	
	      if (invalidParent) {
	        var info = '';
	        if (ancestorTag === 'table' && childTag === 'tr') {
	          info += ' Add a <tbody> to your code to match the DOM tree generated by ' + 'the browser.';
	        }
	         true ? warning(false, 'validateDOMNesting(...): %s cannot appear as a child of <%s>.%s ' + 'See %s.%s', tagDisplayName, ancestorTag, whitespaceInfo, ownerInfo, info) : void 0;
	      } else {
	         true ? warning(false, 'validateDOMNesting(...): %s cannot appear as a descendant of ' + '<%s>. See %s.', tagDisplayName, ancestorTag, ownerInfo) : void 0;
	      }
	    }
	  };
	
	  validateDOMNesting.updatedAncestorInfo = updatedAncestorInfo;
	
	  // For testing
	  validateDOMNesting.isTagValidInContext = function (tag, ancestorInfo) {
	    ancestorInfo = ancestorInfo || emptyAncestorInfo;
	    var parentInfo = ancestorInfo.current;
	    var parentTag = parentInfo && parentInfo.tag;
	    return isTagValidWithParent(tag, parentTag) && !findInvalidAncestorForTag(tag, ancestorInfo);
	  };
	}
	
	module.exports = validateDOMNesting;

/***/ },

/***/ 5373:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMEmptyComponent.js ***!
  \************************************************************************************/
[6976, 5240, 5317, 5270],

/***/ 5374:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTreeTraversal.js ***!
  \***********************************************************************************/
[6977, 5271, 5244],

/***/ 5375:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTextComponent.js ***!
  \***********************************************************************************/
[6978, 5271, 5240, 5316, 5317, 5270, 5322, 5244, 5372],

/***/ 5376:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDefaultBatchingStrategy.js ***!
  \******************************************************************************************/
[6979, 5240, 5292, 5304, 5248],

/***/ 5377:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEventListener.js ***!
  \********************************************************************************/
[6980, 5240, 5378, 5284, 5286, 5270, 5292, 5305, 5379],

/***/ 5378:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/EventListener.js ***!
  \**********************************************************************/
[6303, 5248],

/***/ 5379:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \***********************************************************************************/
143,

/***/ 5380:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInjection.js ***!
  \****************************************************************************/
[6981, 5272, 5278, 5280, 5351, 5361, 5341, 5362, 5292],

/***/ 5381:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactReconcileTransaction.js ***!
  \***************************************************************************************/
[6982, 5240, 5293, 5286, 5341, 5382, 5298, 5304, 5371],

/***/ 5382:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInputSelection.js ***!
  \*********************************************************************************/
[6983, 5383, 5385, 5330, 5388],

/***/ 5383:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMSelection.js ***!
  \*******************************************************************************/
[6984, 5284, 5384, 5287],

/***/ 5384:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getNodeForCharacterOffset.js ***!
  \***************************************************************************************/
1475,

/***/ 5385:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/containsNode.js ***!
  \*********************************************************************/
[6308, 5386],

/***/ 5386:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/isTextNode.js ***!
  \*******************************************************************/
[6309, 5387],

/***/ 5387:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/isNode.js ***!
  \***************************************************************/
151,

/***/ 5388:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/getActiveElement.js ***!
  \*************************************************************************/
152,

/***/ 5389:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SVGDOMPropertyConfig.js ***!
  \**********************************************************************************/
1476,

/***/ 5390:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SelectEventPlugin.js ***!
  \*******************************************************************************/
[6985, 5277, 5284, 5270, 5382, 5289, 5388, 5307, 5359],

/***/ 5391:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SimpleEventPlugin.js ***!
  \*******************************************************************************/
[6986, 5271, 5378, 5277, 5270, 5392, 5393, 5289, 5394, 5395, 5310, 5398, 5399, 5400, 5311, 5401, 5248, 5396, 5244],

/***/ 5392:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticAnimationEvent.js ***!
  \*************************************************************************************/
[6987, 5289],

/***/ 5393:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticClipboardEvent.js ***!
  \*************************************************************************************/
[6988, 5289],

/***/ 5394:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticFocusEvent.js ***!
  \*********************************************************************************/
[6989, 5311],

/***/ 5395:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticKeyboardEvent.js ***!
  \************************************************************************************/
[6990, 5311, 5396, 5397, 5313],

/***/ 5396:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventCharCode.js ***!
  \******************************************************************************/
1483,

/***/ 5397:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventKey.js ***!
  \*************************************************************************/
[6991, 5396],

/***/ 5398:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticDragEvent.js ***!
  \********************************************************************************/
[6992, 5310],

/***/ 5399:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticTouchEvent.js ***!
  \*********************************************************************************/
[6993, 5311, 5313],

/***/ 5400:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticTransitionEvent.js ***!
  \**************************************************************************************/
[6994, 5289],

/***/ 5401:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticWheelEvent.js ***!
  \*********************************************************************************/
[6995, 5310],

/***/ 5402:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMount.js ***!
  \************************************************************************/
[6996, 5271, 5317, 5272, 5239, 5341, 5246, 5270, 5403, 5404, 5294, 5352, 5298, 5405, 5295, 5371, 5292, 5256, 5354, 5244, 5319, 5360, 5247],

/***/ 5403:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMContainerInfo.js ***!
  \***********************************************************************************/
[6997, 5372],

/***/ 5404:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMFeatureFlags.js ***!
  \**********************************************************************************/
1491,

/***/ 5405:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMarkupChecksum.js ***!
  \*********************************************************************************/
[6998, 5406],

/***/ 5406:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/adler32.js ***!
  \*********************************************************************/
1493,

/***/ 5407:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactVersion.js ***!
  \**************************************************************************/
5266,

/***/ 5408:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/findDOMNode.js ***!
  \*************************************************************************/
[6999, 5271, 5246, 5270, 5352, 5409, 5244, 5247],

/***/ 5409:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getHostComponentFromComposite.js ***!
  \*******************************************************************************************/
[7000, 5356],

/***/ 5410:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/renderSubtreeIntoContainer.js ***!
  \****************************************************************************************/
[7001, 5402],

/***/ 5411:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMUnknownPropertyHook.js ***!
  \*****************************************************************************************/
[7002, 5272, 5279, 5262, 5247],

/***/ 5412:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMNullInputValuePropHook.js ***!
  \********************************************************************************************/
[7003, 5262, 5247],

/***/ 5413:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMInvalidARIAHook.js ***!
  \*************************************************************************************/
[7004, 5272, 5262, 5247],

/***/ 5414:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/urijs/src/URI.js ***!
  \*************************************************************/
[6736, 5415, 5416, 5417, 5415, 5416, 5417],

/***/ 5415:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/urijs/src/punycode.js ***!
  \******************************************************************/
1116,

/***/ 5416:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/urijs/src/IPv6.js ***!
  \**************************************************************/
1117,

/***/ 5417:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/urijs/src/SecondLevelDomains.js ***!
  \****************************************************************************/
1118,

/***/ 5418:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/ContainerLoader.jsx ***!
  \***************************************************************************************************************/
[7005, 5238, 5419, 5414, 5645],

/***/ 5419:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/index.js ***!
  \***********************************************************************/
[6737, 5420, 5428],

/***/ 5420:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/components/connect.js ***!
  \************************************************************************************/
[6738, 5238, 5421, 5422, 5423, 5425, 5426, 5428, 5429, 5427, 5430, 5431],

/***/ 5421:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/isPlainObject.js ***!
  \*************************************************************************************/
1121,

/***/ 5422:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/shallowEqual.js ***!
  \************************************************************************************/
1122,

/***/ 5423:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/handleResponse.js ***!
  \**************************************************************************************/
[6739, 5424],

/***/ 5424:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/errors.js ***!
  \******************************************************************************/
1124,

/***/ 5425:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/buildRequest.js ***!
  \************************************************************************************/
1125,

/***/ 5426:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/utils/checkTypes.js ***!
  \**********************************************************************************/
[6740, 5427, 5421],

/***/ 5427:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/invariant/browser.js ***!
  \*****************************************************************/
346,

/***/ 5428:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/lib/PromiseState.js ***!
  \******************************************************************************/
1127,

/***/ 5429:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/hoist-non-react-statics/index.js ***!
  \*****************************************************************************/
1128,

/***/ 5430:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-refetch/~/warning/browser.js ***!
  \*******************************************************************************/
352,

/***/ 5431:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/omit.js ***!
  \**************************************************************/
[6741, 5432, 5638, 5435],

/***/ 5432:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/convert.js ***!
  \*****************************************************************/
[6742, 5433, 5436],

/***/ 5433:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/_baseConvert.js ***!
  \**********************************************************************/
[6743, 5434, 5435],

/***/ 5434:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/_mapping.js ***!
  \******************************************************************/
1133,

/***/ 5435:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/placeholder.js ***!
  \*********************************************************************/
1134,

/***/ 5436:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/fp/_util.js ***!
  \***************************************************************/
[6744, 5437, 5506, 5528, 5595, 5490, 5476, 5445, 5596, 5523, 5631, 5502, 5637],

/***/ 5437:
/*!**********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/ary.js ***!
  \**********************************************************/
[6745, 5438],

/***/ 5438:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createWrap.js ***!
  \******************************************************************/
[6746, 5439, 5457, 5460, 5462, 5500, 5470, 5501, 5480, 5482, 5502],

/***/ 5439:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseSetData.js ***!
  \*******************************************************************/
[6747, 5440, 5441],

/***/ 5440:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/identity.js ***!
  \***************************************************************/
1139,

/***/ 5441:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_metaMap.js ***!
  \***************************************************************/
[6748, 5442],

/***/ 5442:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_WeakMap.js ***!
  \***************************************************************/
[6749, 5443, 5448],

/***/ 5443:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getNative.js ***!
  \*****************************************************************/
[6750, 5444, 5456],

/***/ 5444:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsNative.js ***!
  \********************************************************************/
[6751, 5445, 5453, 5452, 5455],

/***/ 5445:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isFunction.js ***!
  \*****************************************************************/
[6752, 5446, 5452],

/***/ 5446:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseGetTag.js ***!
  \******************************************************************/
[6753, 5447, 5450, 5451],

/***/ 5447:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Symbol.js ***!
  \**************************************************************/
[6754, 5448],

/***/ 5448:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_root.js ***!
  \************************************************************/
[6755, 5449],

/***/ 5449:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_freeGlobal.js ***!
  \******************************************************************/
1148,

/***/ 5450:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getRawTag.js ***!
  \*****************************************************************/
[6756, 5447],

/***/ 5451:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_objectToString.js ***!
  \**********************************************************************/
1150,

/***/ 5452:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isObject.js ***!
  \***************************************************************/
1151,

/***/ 5453:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isMasked.js ***!
  \****************************************************************/
[6757, 5454],

/***/ 5454:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_coreJsData.js ***!
  \******************************************************************/
[6758, 5448],

/***/ 5455:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_toSource.js ***!
  \****************************************************************/
1154,

/***/ 5456:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getValue.js ***!
  \****************************************************************/
1155,

/***/ 5457:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createBind.js ***!
  \******************************************************************/
[6759, 5458, 5448],

/***/ 5458:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createCtor.js ***!
  \******************************************************************/
[6760, 5459, 5452],

/***/ 5459:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseCreate.js ***!
  \******************************************************************/
[6761, 5452],

/***/ 5460:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createCurry.js ***!
  \*******************************************************************/
[6762, 5461, 5458, 5462, 5466, 5496, 5499, 5448],

/***/ 5461:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_apply.js ***!
  \*************************************************************/
1160,

/***/ 5462:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createHybrid.js ***!
  \********************************************************************/
[6763, 5463, 5464, 5465, 5458, 5466, 5496, 5497, 5499, 5448],

/***/ 5463:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_composeArgs.js ***!
  \*******************************************************************/
1162,

/***/ 5464:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_composeArgsRight.js ***!
  \************************************************************************/
1163,

/***/ 5465:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_countHolders.js ***!
  \********************************************************************/
1164,

/***/ 5466:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createRecurry.js ***!
  \*********************************************************************/
[6764, 5467, 5480, 5482],

/***/ 5467:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isLaziable.js ***!
  \******************************************************************/
[6765, 5468, 5470, 5472, 5474],

/***/ 5468:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_LazyWrapper.js ***!
  \*******************************************************************/
[6766, 5459, 5469],

/***/ 5469:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseLodash.js ***!
  \******************************************************************/
1168,

/***/ 5470:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getData.js ***!
  \***************************************************************/
[6767, 5441, 5471],

/***/ 5471:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/noop.js ***!
  \***********************************************************/
1170,

/***/ 5472:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getFuncName.js ***!
  \*******************************************************************/
[6768, 5473],

/***/ 5473:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_realNames.js ***!
  \*****************************************************************/
1172,

/***/ 5474:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/wrapperLodash.js ***!
  \********************************************************************/
[6769, 5468, 5475, 5469, 5476, 5477, 5478],

/***/ 5475:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_LodashWrapper.js ***!
  \*********************************************************************/
[6770, 5459, 5469],

/***/ 5476:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isArray.js ***!
  \**************************************************************/
1175,

/***/ 5477:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isObjectLike.js ***!
  \*******************************************************************/
1176,

/***/ 5478:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_wrapperClone.js ***!
  \********************************************************************/
[6771, 5468, 5475, 5479],

/***/ 5479:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_copyArray.js ***!
  \*****************************************************************/
1178,

/***/ 5480:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setData.js ***!
  \***************************************************************/
[6772, 5439, 5481],

/***/ 5481:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_shortOut.js ***!
  \****************************************************************/
1180,

/***/ 5482:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setWrapToString.js ***!
  \***********************************************************************/
[6773, 5483, 5484, 5485, 5489],

/***/ 5483:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getWrapDetails.js ***!
  \**********************************************************************/
1182,

/***/ 5484:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_insertWrapDetails.js ***!
  \*************************************************************************/
1183,

/***/ 5485:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setToString.js ***!
  \*******************************************************************/
[6774, 5486, 5481],

/***/ 5486:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseSetToString.js ***!
  \***********************************************************************/
[6775, 5487, 5488, 5440],

/***/ 5487:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/constant.js ***!
  \***************************************************************/
1186,

/***/ 5488:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_defineProperty.js ***!
  \**********************************************************************/
[6776, 5443],

/***/ 5489:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_updateWrapDetails.js ***!
  \*************************************************************************/
[6777, 5490, 5491],

/***/ 5490:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayEach.js ***!
  \*****************************************************************/
1189,

/***/ 5491:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayIncludes.js ***!
  \*********************************************************************/
[6778, 5492],

/***/ 5492:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIndexOf.js ***!
  \*******************************************************************/
[6779, 5493, 5494, 5495],

/***/ 5493:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseFindIndex.js ***!
  \*********************************************************************/
1192,

/***/ 5494:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsNaN.js ***!
  \*****************************************************************/
1193,

/***/ 5495:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_strictIndexOf.js ***!
  \*********************************************************************/
1194,

/***/ 5496:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getHolder.js ***!
  \*****************************************************************/
1195,

/***/ 5497:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_reorder.js ***!
  \***************************************************************/
[6780, 5479, 5498],

/***/ 5498:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isIndex.js ***!
  \***************************************************************/
1197,

/***/ 5499:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_replaceHolders.js ***!
  \**********************************************************************/
1198,

/***/ 5500:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createPartial.js ***!
  \*********************************************************************/
[6781, 5461, 5458, 5448],

/***/ 5501:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mergeData.js ***!
  \*****************************************************************/
[6782, 5463, 5464, 5499],

/***/ 5502:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/toInteger.js ***!
  \****************************************************************/
[6783, 5503],

/***/ 5503:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/toFinite.js ***!
  \***************************************************************/
[6784, 5504],

/***/ 5504:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/toNumber.js ***!
  \***************************************************************/
[6785, 5452, 5505],

/***/ 5505:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isSymbol.js ***!
  \***************************************************************/
[6786, 5446, 5477],

/***/ 5506:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseAssign.js ***!
  \******************************************************************/
[6787, 5507, 5511],

/***/ 5507:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_copyObject.js ***!
  \******************************************************************/
[6788, 5508, 5509],

/***/ 5508:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_assignValue.js ***!
  \*******************************************************************/
[6789, 5509, 5510],

/***/ 5509:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseAssignValue.js ***!
  \***********************************************************************/
[6790, 5488],

/***/ 5510:
/*!*********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/eq.js ***!
  \*********************************************************/
1209,

/***/ 5511:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/keys.js ***!
  \***********************************************************/
[6791, 5512, 5523, 5527],

/***/ 5512:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayLikeKeys.js ***!
  \*********************************************************************/
[6792, 5513, 5514, 5476, 5516, 5498, 5518],

/***/ 5513:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseTimes.js ***!
  \*****************************************************************/
1212,

/***/ 5514:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isArguments.js ***!
  \******************************************************************/
[6793, 5515, 5477],

/***/ 5515:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsArguments.js ***!
  \***********************************************************************/
[6794, 5446, 5477],

/***/ 5516:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isBuffer.js ***!
  \***************************************************************/
[6795, 5448, 5517],

/***/ 5517:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/stubFalse.js ***!
  \****************************************************************/
1216,

/***/ 5518:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isTypedArray.js ***!
  \*******************************************************************/
[6796, 5519, 5521, 5522],

/***/ 5519:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsTypedArray.js ***!
  \************************************************************************/
[6797, 5446, 5520, 5477],

/***/ 5520:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isLength.js ***!
  \***************************************************************/
1219,

/***/ 5521:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseUnary.js ***!
  \*****************************************************************/
1220,

/***/ 5522:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_nodeUtil.js ***!
  \****************************************************************/
[6798, 5449],

/***/ 5523:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseKeys.js ***!
  \****************************************************************/
[6799, 5524, 5525],

/***/ 5524:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isPrototype.js ***!
  \*******************************************************************/
1223,

/***/ 5525:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_nativeKeys.js ***!
  \******************************************************************/
[6800, 5526],

/***/ 5526:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_overArg.js ***!
  \***************************************************************/
1225,

/***/ 5527:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isArrayLike.js ***!
  \******************************************************************/
[6801, 5445, 5520],

/***/ 5528:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/clone.js ***!
  \************************************************************/
[6802, 5529],

/***/ 5529:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseClone.js ***!
  \*****************************************************************/
[6803, 5530, 5490, 5508, 5506, 5559, 5563, 5479, 5564, 5568, 5572, 5574, 5575, 5579, 5580, 5594, 5476, 5516, 5452, 5511],

/***/ 5530:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Stack.js ***!
  \*************************************************************/
[6804, 5531, 5538, 5539, 5540, 5541, 5542],

/***/ 5531:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_ListCache.js ***!
  \*****************************************************************/
[6805, 5532, 5533, 5535, 5536, 5537],

/***/ 5532:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_listCacheClear.js ***!
  \**********************************************************************/
1231,

/***/ 5533:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_listCacheDelete.js ***!
  \***********************************************************************/
[6806, 5534],

/***/ 5534:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_assocIndexOf.js ***!
  \********************************************************************/
[6807, 5510],

/***/ 5535:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_listCacheGet.js ***!
  \********************************************************************/
[6808, 5534],

/***/ 5536:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_listCacheHas.js ***!
  \********************************************************************/
[6809, 5534],

/***/ 5537:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_listCacheSet.js ***!
  \********************************************************************/
[6810, 5534],

/***/ 5538:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stackClear.js ***!
  \******************************************************************/
[6811, 5531],

/***/ 5539:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stackDelete.js ***!
  \*******************************************************************/
1238,

/***/ 5540:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stackGet.js ***!
  \****************************************************************/
1239,

/***/ 5541:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stackHas.js ***!
  \****************************************************************/
1240,

/***/ 5542:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stackSet.js ***!
  \****************************************************************/
[6812, 5531, 5543, 5544],

/***/ 5543:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Map.js ***!
  \***********************************************************/
[6813, 5443, 5448],

/***/ 5544:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_MapCache.js ***!
  \****************************************************************/
[6814, 5545, 5553, 5556, 5557, 5558],

/***/ 5545:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapCacheClear.js ***!
  \*********************************************************************/
[6815, 5546, 5531, 5543],

/***/ 5546:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Hash.js ***!
  \************************************************************/
[6816, 5547, 5549, 5550, 5551, 5552],

/***/ 5547:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hashClear.js ***!
  \*****************************************************************/
[6817, 5548],

/***/ 5548:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_nativeCreate.js ***!
  \********************************************************************/
[6818, 5443],

/***/ 5549:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hashDelete.js ***!
  \******************************************************************/
1248,

/***/ 5550:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hashGet.js ***!
  \***************************************************************/
[6819, 5548],

/***/ 5551:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hashHas.js ***!
  \***************************************************************/
[6820, 5548],

/***/ 5552:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hashSet.js ***!
  \***************************************************************/
[6821, 5548],

/***/ 5553:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapCacheDelete.js ***!
  \**********************************************************************/
[6822, 5554],

/***/ 5554:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getMapData.js ***!
  \******************************************************************/
[6823, 5555],

/***/ 5555:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isKeyable.js ***!
  \*****************************************************************/
1254,

/***/ 5556:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapCacheGet.js ***!
  \*******************************************************************/
[6824, 5554],

/***/ 5557:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapCacheHas.js ***!
  \*******************************************************************/
[6825, 5554],

/***/ 5558:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapCacheSet.js ***!
  \*******************************************************************/
[6826, 5554],

/***/ 5559:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseAssignIn.js ***!
  \********************************************************************/
[6827, 5507, 5560],

/***/ 5560:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/keysIn.js ***!
  \*************************************************************/
[6828, 5512, 5561, 5527],

/***/ 5561:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseKeysIn.js ***!
  \******************************************************************/
[6829, 5452, 5524, 5562],

/***/ 5562:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_nativeKeysIn.js ***!
  \********************************************************************/
1261,

/***/ 5563:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneBuffer.js ***!
  \*******************************************************************/
[6830, 5448],

/***/ 5564:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_copySymbols.js ***!
  \*******************************************************************/
[6831, 5507, 5565],

/***/ 5565:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getSymbols.js ***!
  \******************************************************************/
[6832, 5566, 5567],

/***/ 5566:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayFilter.js ***!
  \*******************************************************************/
1265,

/***/ 5567:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/stubArray.js ***!
  \****************************************************************/
1266,

/***/ 5568:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_copySymbolsIn.js ***!
  \*********************************************************************/
[6833, 5507, 5569],

/***/ 5569:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getSymbolsIn.js ***!
  \********************************************************************/
[6834, 5570, 5571, 5565, 5567],

/***/ 5570:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayPush.js ***!
  \*****************************************************************/
1269,

/***/ 5571:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getPrototype.js ***!
  \********************************************************************/
[6835, 5526],

/***/ 5572:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getAllKeys.js ***!
  \******************************************************************/
[6836, 5573, 5565, 5511],

/***/ 5573:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseGetAllKeys.js ***!
  \**********************************************************************/
[6837, 5570, 5476],

/***/ 5574:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getAllKeysIn.js ***!
  \********************************************************************/
[6838, 5573, 5569, 5560],

/***/ 5575:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getTag.js ***!
  \**************************************************************/
[6839, 5576, 5543, 5577, 5578, 5442, 5446, 5455],

/***/ 5576:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_DataView.js ***!
  \****************************************************************/
[6840, 5443, 5448],

/***/ 5577:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Promise.js ***!
  \***************************************************************/
[6841, 5443, 5448],

/***/ 5578:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Set.js ***!
  \***********************************************************/
[6842, 5443, 5448],

/***/ 5579:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_initCloneArray.js ***!
  \**********************************************************************/
1278,

/***/ 5580:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_initCloneByTag.js ***!
  \**********************************************************************/
[6843, 5581, 5583, 5584, 5588, 5589, 5592, 5593],

/***/ 5581:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneArrayBuffer.js ***!
  \************************************************************************/
[6844, 5582],

/***/ 5582:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_Uint8Array.js ***!
  \******************************************************************/
[6845, 5448],

/***/ 5583:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneDataView.js ***!
  \*********************************************************************/
[6846, 5581],

/***/ 5584:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneMap.js ***!
  \****************************************************************/
[6847, 5585, 5586, 5587],

/***/ 5585:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_addMapEntry.js ***!
  \*******************************************************************/
1284,

/***/ 5586:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayReduce.js ***!
  \*******************************************************************/
1285,

/***/ 5587:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_mapToArray.js ***!
  \******************************************************************/
1286,

/***/ 5588:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneRegExp.js ***!
  \*******************************************************************/
1287,

/***/ 5589:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneSet.js ***!
  \****************************************************************/
[6848, 5590, 5586, 5591],

/***/ 5590:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_addSetEntry.js ***!
  \*******************************************************************/
1289,

/***/ 5591:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setToArray.js ***!
  \******************************************************************/
1290,

/***/ 5592:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneSymbol.js ***!
  \*******************************************************************/
[6849, 5447],

/***/ 5593:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cloneTypedArray.js ***!
  \***********************************************************************/
[6850, 5581],

/***/ 5594:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_initCloneObject.js ***!
  \***********************************************************************/
[6851, 5459, 5571, 5524],

/***/ 5595:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/curry.js ***!
  \************************************************************/
[6852, 5438],

/***/ 5596:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/iteratee.js ***!
  \***************************************************************/
[6853, 5529, 5597],

/***/ 5597:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIteratee.js ***!
  \********************************************************************/
[6854, 5598, 5613, 5440, 5476, 5628],

/***/ 5598:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseMatches.js ***!
  \*******************************************************************/
[6855, 5599, 5610, 5612],

/***/ 5599:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsMatch.js ***!
  \*******************************************************************/
[6856, 5530, 5600],

/***/ 5600:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsEqual.js ***!
  \*******************************************************************/
[6857, 5601, 5477],

/***/ 5601:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseIsEqualDeep.js ***!
  \***********************************************************************/
[6858, 5530, 5602, 5608, 5609, 5575, 5476, 5516, 5518],

/***/ 5602:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_equalArrays.js ***!
  \*******************************************************************/
[6859, 5603, 5606, 5607],

/***/ 5603:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_SetCache.js ***!
  \****************************************************************/
[6860, 5544, 5604, 5605],

/***/ 5604:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setCacheAdd.js ***!
  \*******************************************************************/
1303,

/***/ 5605:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_setCacheHas.js ***!
  \*******************************************************************/
1304,

/***/ 5606:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arraySome.js ***!
  \*****************************************************************/
1305,

/***/ 5607:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_cacheHas.js ***!
  \****************************************************************/
1306,

/***/ 5608:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_equalByTag.js ***!
  \******************************************************************/
[6861, 5447, 5582, 5510, 5602, 5587, 5591],

/***/ 5609:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_equalObjects.js ***!
  \********************************************************************/
[6862, 5572],

/***/ 5610:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_getMatchData.js ***!
  \********************************************************************/
[6863, 5611, 5511],

/***/ 5611:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isStrictComparable.js ***!
  \**************************************************************************/
[6864, 5452],

/***/ 5612:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_matchesStrictComparable.js ***!
  \*******************************************************************************/
1311,

/***/ 5613:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseMatchesProperty.js ***!
  \***************************************************************************/
[6865, 5600, 5614, 5625, 5617, 5611, 5612, 5624],

/***/ 5614:
/*!**********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/get.js ***!
  \**********************************************************/
[6866, 5615],

/***/ 5615:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseGet.js ***!
  \***************************************************************/
[6867, 5616, 5624],

/***/ 5616:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_castPath.js ***!
  \****************************************************************/
[6868, 5476, 5617, 5618, 5621],

/***/ 5617:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isKey.js ***!
  \*************************************************************/
[6869, 5476, 5505],

/***/ 5618:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_stringToPath.js ***!
  \********************************************************************/
[6870, 5619],

/***/ 5619:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_memoizeCapped.js ***!
  \*********************************************************************/
[6871, 5620],

/***/ 5620:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/memoize.js ***!
  \**************************************************************/
[6872, 5544],

/***/ 5621:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/toString.js ***!
  \***************************************************************/
[6873, 5622],

/***/ 5622:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseToString.js ***!
  \********************************************************************/
[6874, 5447, 5623, 5476, 5505],

/***/ 5623:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayMap.js ***!
  \****************************************************************/
1322,

/***/ 5624:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_toKey.js ***!
  \*************************************************************/
[6875, 5505],

/***/ 5625:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/hasIn.js ***!
  \************************************************************/
[6876, 5626, 5627],

/***/ 5626:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseHasIn.js ***!
  \*****************************************************************/
1325,

/***/ 5627:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_hasPath.js ***!
  \***************************************************************/
[6877, 5616, 5514, 5476, 5498, 5520, 5624],

/***/ 5628:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/property.js ***!
  \***************************************************************/
[6878, 5629, 5630, 5617, 5624],

/***/ 5629:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseProperty.js ***!
  \********************************************************************/
1328,

/***/ 5630:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_basePropertyDeep.js ***!
  \************************************************************************/
[6879, 5615],

/***/ 5631:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/rearg.js ***!
  \************************************************************/
[6880, 5438, 5632],

/***/ 5632:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_flatRest.js ***!
  \****************************************************************/
[6881, 5633, 5636, 5485],

/***/ 5633:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/flatten.js ***!
  \**************************************************************/
[6882, 5634],

/***/ 5634:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseFlatten.js ***!
  \*******************************************************************/
[6883, 5570, 5635],

/***/ 5635:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isFlattenable.js ***!
  \*********************************************************************/
[6884, 5447, 5514, 5476],

/***/ 5636:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_overRest.js ***!
  \****************************************************************/
[6885, 5461],

/***/ 5637:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/toPath.js ***!
  \*************************************************************/
[6886, 5623, 5479, 5476, 5505, 5618, 5624, 5621],

/***/ 5638:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/omit.js ***!
  \***********************************************************/
[6887, 5623, 5529, 5639, 5616, 5507, 5643, 5632, 5574],

/***/ 5639:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseUnset.js ***!
  \*****************************************************************/
[6888, 5616, 5640, 5641, 5624],

/***/ 5640:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/last.js ***!
  \***********************************************************/
1339,

/***/ 5641:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_parent.js ***!
  \**************************************************************/
[6889, 5615, 5642],

/***/ 5642:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseSlice.js ***!
  \*****************************************************************/
1341,

/***/ 5643:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_customOmitClone.js ***!
  \***********************************************************************/
[6890, 5644],

/***/ 5644:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isPlainObject.js ***!
  \********************************************************************/
[6891, 5446, 5571, 5477],

/***/ 5645:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/Container.jsx ***!
  \*********************************************************************************************************/
[7006, 5238, 5414, 5646, 5848, 5849, 5850, 6176, 6177],

/***/ 5646:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/index.js ***!
  \*****************************************************************/
[6199, 5647],

/***/ 5647:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/AnatomogramFactory.jsx ***!
  \***********************************************************************************/
[6200, 5648, 5675, 5798, 5846],

/***/ 5648:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/react.js ***!
  \*************************************************************************/
[6201, 5649],

/***/ 5649:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/React.js ***!
  \*****************************************************************************/
[6202, 5240, 5650, 5659, 5661, 5662, 5667, 5653, 5672, 5673, 5674, 5247, 5668],

/***/ 5650:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildren.js ***!
  \*************************************************************************************/
[6203, 5651, 5653, 5248, 5656],

/***/ 5651:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/PooledClass.js ***!
  \***********************************************************************************/
[6204, 5652, 5244],

/***/ 5652:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/reactProdInvariant.js ***!
  \******************************************************************************************/
8,

/***/ 5653:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactElement.js ***!
  \************************************************************************************/
[6205, 5240, 5654, 5247, 5655],

/***/ 5654:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactCurrentOwner.js ***!
  \*****************************************************************************************/
11,

/***/ 5655:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/canDefineProperty.js ***!
  \*****************************************************************************************/
14,

/***/ 5656:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/traverseAllChildren.js ***!
  \*******************************************************************************************/
[6207, 5652, 5654, 5653, 5657, 5244, 5658, 5247],

/***/ 5657:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getIteratorFn.js ***!
  \*************************************************************************************/
16,

/***/ 5658:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/KeyEscapeUtils.js ***!
  \**************************************************************************************/
17,

/***/ 5659:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponent.js ***!
  \**************************************************************************************/
[6208, 5652, 5660, 5655, 5256, 5244, 5247],

/***/ 5660:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactNoopUpdateQueue.js ***!
  \********************************************************************************************/
[6209, 5247],

/***/ 5661:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactPureComponent.js ***!
  \******************************************************************************************/
[6210, 5240, 5659, 5660, 5256],

/***/ 5662:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactClass.js ***!
  \**********************************************************************************/
[6211, 5652, 5240, 5659, 5653, 5663, 5665, 5660, 5256, 5244, 5664, 5666, 5247],

/***/ 5663:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypeLocations.js ***!
  \**********************************************************************************************/
[6212, 5664],

/***/ 5664:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/keyMirror.js ***!
  \******************************************************************/
[6213, 5244],

/***/ 5665:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypeLocationNames.js ***!
  \**************************************************************************************************/
25,

/***/ 5666:
/*!**************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/fbjs/lib/keyOf.js ***!
  \**************************************************************/
26,

/***/ 5667:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMFactories.js ***!
  \*****************************************************************************************/
[6214, 5653, 5668],

/***/ 5668:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactElementValidator.js ***!
  \*********************************************************************************************/
[6215, 5654, 5669, 5653, 5663, 5670, 5655, 5657, 5247],

/***/ 5669:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentTreeHook.js ***!
  \**********************************************************************************************/
[6216, 5652, 5654, 5244, 5247],

/***/ 5670:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/checkReactTypeSpec.js ***!
  \******************************************************************************************/
[6217, 5652, 5665, 5671, 5244, 5247, 5669, 5669],

/***/ 5671:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypesSecret.js ***!
  \********************************************************************************************/
32,

/***/ 5672:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypes.js ***!
  \**************************************************************************************/
[6218, 5653, 5665, 5671, 5248, 5657, 5247],

/***/ 5673:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactVersion.js ***!
  \************************************************************************************/
34,

/***/ 5674:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/onlyChild.js ***!
  \*********************************************************************************/
[6219, 5652, 5653, 5244],

/***/ 5675:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/Anatomogram.jsx ***!
  \****************************************************************************/
[6220, 5648, 5676, 5797],

/***/ 5676:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/AnatomogramImage.jsx ***!
  \*********************************************************************************/
[6221, 5648, 5677, 5796],

/***/ 5677:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react-dom/index.js ***!
  \*****************************************************************************/
[6222, 5678],

/***/ 5678:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOM.js ***!
  \********************************************************************************/
[6223, 5679, 5682, 5786, 5701, 5698, 5673, 5791, 5792, 5793, 5247, 5284, 5704, 5794, 5795],

/***/ 5679:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponentTree.js ***!
  \*********************************************************************************************/
[6224, 5652, 5680, 5681, 5244],

/***/ 5680:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DOMProperty.js ***!
  \***********************************************************************************/
[6225, 5652, 5244],

/***/ 5681:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponentFlags.js ***!
  \**********************************************************************************************/
42,

/***/ 5682:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDefaultInjection.js ***!
  \*********************************************************************************************/
[6226, 5683, 5697, 5713, 5714, 5719, 5720, 5731, 5679, 5763, 5764, 5765, 5766, 5767, 5768, 5769, 5773, 5774, 5775],

/***/ 5683:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/BeforeInputEventPlugin.js ***!
  \**********************************************************************************************/
[6227, 5684, 5685, 5284, 5692, 5694, 5696, 5666],

/***/ 5684:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EventConstants.js ***!
  \**************************************************************************************/
[6228, 5664],

/***/ 5685:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EventPropagators.js ***!
  \****************************************************************************************/
[6229, 5684, 5686, 5688, 5690, 5691, 5247],

/***/ 5686:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginHub.js ***!
  \**************************************************************************************/
[6230, 5652, 5687, 5688, 5689, 5690, 5691, 5244],

/***/ 5687:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginRegistry.js ***!
  \*******************************************************************************************/
[6231, 5652, 5244],

/***/ 5688:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginUtils.js ***!
  \****************************************************************************************/
[6232, 5652, 5684, 5689, 5244, 5247],

/***/ 5689:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactErrorUtils.js ***!
  \***************************************************************************************/
50,

/***/ 5690:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/accumulateInto.js ***!
  \**************************************************************************************/
[6233, 5652, 5244],

/***/ 5691:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/forEachAccumulated.js ***!
  \******************************************************************************************/
52,

/***/ 5692:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/FallbackCompositionState.js ***!
  \************************************************************************************************/
[6234, 5240, 5651, 5693],

/***/ 5693:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getTextContentAccessor.js ***!
  \**********************************************************************************************/
[6235, 5284],

/***/ 5694:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticCompositionEvent.js ***!
  \*************************************************************************************************/
[6236, 5695],

/***/ 5695:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticEvent.js ***!
  \**************************************************************************************/
[6237, 5240, 5651, 5248, 5247],

/***/ 5696:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticInputEvent.js ***!
  \*******************************************************************************************/
[6238, 5695],

/***/ 5697:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ChangeEventPlugin.js ***!
  \*****************************************************************************************/
[6239, 5684, 5686, 5685, 5284, 5679, 5698, 5695, 5710, 5711, 5712, 5666],

/***/ 5698:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactUpdates.js ***!
  \************************************************************************************/
[6240, 5652, 5240, 5699, 5651, 5700, 5701, 5709, 5244],

/***/ 5699:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/CallbackQueue.js ***!
  \*************************************************************************************/
[6241, 5652, 5240, 5651, 5244],

/***/ 5700:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactFeatureFlags.js ***!
  \*****************************************************************************************/
62,

/***/ 5701:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactReconciler.js ***!
  \***************************************************************************************/
[6242, 5702, 5704, 5247],

/***/ 5702:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactRef.js ***!
  \********************************************************************************/
[6243, 5703],

/***/ 5703:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactOwner.js ***!
  \**********************************************************************************/
[6244, 5652, 5244],

/***/ 5704:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactInstrumentation.js ***!
  \********************************************************************************************/
[6245, 5705],

/***/ 5705:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDebugTool.js ***!
  \**************************************************************************************/
[6246, 5706, 5707, 5669, 5708, 5284, 5302, 5247],

/***/ 5706:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \*******************************************************************************************************/
[6247, 5247],

/***/ 5707:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \*****************************************************************************************************/
69,

/***/ 5708:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \********************************************************************************************************/
[6248, 5669, 5247],

/***/ 5709:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/Transaction.js ***!
  \***********************************************************************************/
[6251, 5652, 5244],

/***/ 5710:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getEventTarget.js ***!
  \**************************************************************************************/
74,

/***/ 5711:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/isEventSupported.js ***!
  \****************************************************************************************/
[6252, 5284],

/***/ 5712:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/isTextInputElement.js ***!
  \******************************************************************************************/
76,

/***/ 5713:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DefaultEventPluginOrder.js ***!
  \***********************************************************************************************/
[6253, 5666],

/***/ 5714:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*********************************************************************************************/
[6254, 5684, 5685, 5679, 5715, 5666],

/***/ 5715:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticMouseEvent.js ***!
  \*******************************************************************************************/
[6255, 5716, 5717, 5718],

/***/ 5716:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticUIEvent.js ***!
  \****************************************************************************************/
[6256, 5695, 5710],

/***/ 5717:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ViewportMetrics.js ***!
  \***************************************************************************************/
81,

/***/ 5718:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getEventModifierState.js ***!
  \*********************************************************************************************/
82,

/***/ 5719:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*********************************************************************************************/
[6257, 5680],

/***/ 5720:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \********************************************************************************************************/
[6258, 5721, 5730],

/***/ 5721:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DOMChildrenOperations.js ***!
  \*********************************************************************************************/
[6259, 5722, 5728, 5729, 5679, 5704, 5725, 5724, 5726],

/***/ 5722:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DOMLazyTree.js ***!
  \***********************************************************************************/
[6260, 5723, 5724, 5725, 5726],

/***/ 5723:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DOMNamespaces.js ***!
  \*************************************************************************************/
87,

/***/ 5724:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/setInnerHTML.js ***!
  \************************************************************************************/
[6261, 5284, 5723, 5725],

/***/ 5725:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \**********************************************************************************************************/
89,

/***/ 5726:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/setTextContent.js ***!
  \**************************************************************************************/
[6262, 5284, 5727, 5724],

/***/ 5727:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/escapeTextContentForBrowser.js ***!
  \***************************************************************************************************/
91,

/***/ 5728:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/Danger.js ***!
  \******************************************************************************/
[6263, 5652, 5722, 5284, 5324, 5248, 5244],

/***/ 5729:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \**************************************************************************************************/
[6267, 5664],

/***/ 5730:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMIDOperations.js ***!
  \********************************************************************************************/
[6268, 5721, 5679],

/***/ 5731:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponent.js ***!
  \*****************************************************************************************/
[6269, 5652, 5240, 5732, 5733, 5722, 5723, 5680, 5736, 5684, 5686, 5687, 5738, 5741, 5681, 5679, 5743, 5745, 5746, 5747, 5704, 5748, 5759, 5248, 5727, 5244, 5711, 5666, 5359, 5762, 5247],

/***/ 5732:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/AutoFocusUtils.js ***!
  \**************************************************************************************/
[6270, 5679, 5330],

/***/ 5733:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/CSSPropertyOperations.js ***!
  \*********************************************************************************************/
[6271, 5734, 5284, 5704, 5333, 5735, 5336, 5338, 5247],

/***/ 5734:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/CSSProperty.js ***!
  \***********************************************************************************/
102,

/***/ 5735:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/dangerousStyleValue.js ***!
  \*******************************************************************************************/
[6273, 5734, 5247],

/***/ 5736:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DOMPropertyOperations.js ***!
  \*********************************************************************************************/
[6275, 5680, 5679, 5704, 5737, 5247],

/***/ 5737:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*****************************************************************************************************/
[6276, 5727],

/***/ 5738:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactBrowserEventEmitter.js ***!
  \************************************************************************************************/
[6277, 5240, 5684, 5687, 5739, 5717, 5740, 5711],

/***/ 5739:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactEventEmitterMixin.js ***!
  \**********************************************************************************************/
[6278, 5686],

/***/ 5740:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getVendorPrefixedEventName.js ***!
  \**************************************************************************************************/
[6279, 5284],

/***/ 5741:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMButton.js ***!
  \**************************************************************************************/
[6280, 5742],

/***/ 5742:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/DisabledInputUtils.js ***!
  \******************************************************************************************/
115,

/***/ 5743:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMInput.js ***!
  \*************************************************************************************/
[6281, 5652, 5240, 5742, 5736, 5744, 5679, 5698, 5244, 5247],

/***/ 5744:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/LinkedValueUtils.js ***!
  \****************************************************************************************/
[6282, 5652, 5672, 5663, 5671, 5244, 5247],

/***/ 5745:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMOption.js ***!
  \**************************************************************************************/
[6283, 5240, 5650, 5679, 5746, 5247],

/***/ 5746:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMSelect.js ***!
  \**************************************************************************************/
[6284, 5240, 5742, 5744, 5679, 5698, 5247],

/***/ 5747:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTextarea.js ***!
  \****************************************************************************************/
[6285, 5652, 5240, 5742, 5744, 5679, 5698, 5244, 5247],

/***/ 5748:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactMultiChild.js ***!
  \***************************************************************************************/
[6286, 5652, 5749, 5750, 5704, 5729, 5654, 5701, 5751, 5248, 5758, 5244],

/***/ 5749:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentEnvironment.js ***!
  \*************************************************************************************************/
[6287, 5652, 5244],

/***/ 5750:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactInstanceMap.js ***!
  \****************************************************************************************/
123,

/***/ 5751:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildReconciler.js ***!
  \********************************************************************************************/
[6288, 5701, 5752, 5658, 5755, 5656, 5247, 5669, 5669],

/***/ 5752:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/instantiateReactComponent.js ***!
  \*************************************************************************************************/
[6289, 5652, 5240, 5753, 5756, 5757, 5244, 5247],

/***/ 5753:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactCompositeComponent.js ***!
  \***********************************************************************************************/
[6290, 5652, 5240, 5749, 5654, 5653, 5689, 5750, 5704, 5754, 5663, 5701, 5670, 5256, 5244, 5359, 5755, 5247],

/***/ 5754:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactNodeTypes.js ***!
  \**************************************************************************************/
[6291, 5652, 5653, 5244],

/***/ 5755:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/shouldUpdateReactComponent.js ***!
  \**************************************************************************************************/
129,

/***/ 5756:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactEmptyComponent.js ***!
  \*******************************************************************************************/
130,

/***/ 5757:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactHostComponent.js ***!
  \******************************************************************************************/
[6292, 5652, 5240, 5244],

/***/ 5758:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/flattenChildren.js ***!
  \***************************************************************************************/
[6293, 5658, 5656, 5247, 5669, 5669],

/***/ 5759:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*******************************************************************************************************/
[6294, 5240, 5651, 5709, 5704, 5760],

/***/ 5760:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactServerUpdateQueue.js ***!
  \**********************************************************************************************/
[6295, 5761, 5709, 5247],

/***/ 5761:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactUpdateQueue.js ***!
  \****************************************************************************************/
[6296, 5652, 5654, 5750, 5704, 5698, 5244, 5247],

/***/ 5762:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/validateDOMNesting.js ***!
  \******************************************************************************************/
[6297, 5240, 5248, 5247],

/***/ 5763:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMEmptyComponent.js ***!
  \**********************************************************************************************/
[6298, 5240, 5722, 5679],

/***/ 5764:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTreeTraversal.js ***!
  \*********************************************************************************************/
[6299, 5652, 5244],

/***/ 5765:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTextComponent.js ***!
  \*********************************************************************************************/
[6300, 5652, 5240, 5721, 5722, 5679, 5727, 5244, 5762],

/***/ 5766:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \****************************************************************************************************/
[6301, 5240, 5698, 5709, 5248],

/***/ 5767:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactEventListener.js ***!
  \******************************************************************************************/
[6302, 5240, 5378, 5284, 5651, 5679, 5698, 5710, 5379],

/***/ 5768:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactInjection.js ***!
  \**************************************************************************************/
[6304, 5680, 5686, 5688, 5749, 5662, 5756, 5738, 5757, 5698],

/***/ 5769:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactReconcileTransaction.js ***!
  \*************************************************************************************************/
[6305, 5240, 5699, 5651, 5738, 5770, 5704, 5709, 5761],

/***/ 5770:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactInputSelection.js ***!
  \*******************************************************************************************/
[6306, 5771, 5385, 5330, 5388],

/***/ 5771:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMSelection.js ***!
  \*****************************************************************************************/
[6307, 5284, 5772, 5693],

/***/ 5772:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getNodeForCharacterOffset.js ***!
  \*************************************************************************************************/
148,

/***/ 5773:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SVGDOMPropertyConfig.js ***!
  \********************************************************************************************/
153,

/***/ 5774:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SelectEventPlugin.js ***!
  \*****************************************************************************************/
[6310, 5684, 5685, 5284, 5679, 5770, 5695, 5388, 5712, 5666, 5359],

/***/ 5775:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SimpleEventPlugin.js ***!
  \*****************************************************************************************/
[6311, 5652, 5684, 5378, 5685, 5679, 5776, 5777, 5695, 5778, 5779, 5715, 5782, 5783, 5784, 5716, 5785, 5248, 5780, 5244, 5666],

/***/ 5776:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticAnimationEvent.js ***!
  \***********************************************************************************************/
[6312, 5695],

/***/ 5777:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticClipboardEvent.js ***!
  \***********************************************************************************************/
[6313, 5695],

/***/ 5778:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticFocusEvent.js ***!
  \*******************************************************************************************/
[6314, 5716],

/***/ 5779:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**********************************************************************************************/
[6315, 5716, 5780, 5781, 5718],

/***/ 5780:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getEventCharCode.js ***!
  \****************************************************************************************/
160,

/***/ 5781:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getEventKey.js ***!
  \***********************************************************************************/
[6316, 5780],

/***/ 5782:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticDragEvent.js ***!
  \******************************************************************************************/
[6317, 5715],

/***/ 5783:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticTouchEvent.js ***!
  \*******************************************************************************************/
[6318, 5716, 5718],

/***/ 5784:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticTransitionEvent.js ***!
  \************************************************************************************************/
[6319, 5695],

/***/ 5785:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticWheelEvent.js ***!
  \*******************************************************************************************/
[6320, 5715],

/***/ 5786:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactMount.js ***!
  \**********************************************************************************/
[6321, 5652, 5722, 5680, 5738, 5654, 5679, 5787, 5788, 5653, 5700, 5750, 5704, 5789, 5701, 5761, 5698, 5256, 5752, 5244, 5724, 5755, 5247],

/***/ 5787:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMContainerInfo.js ***!
  \*********************************************************************************************/
[6322, 5762],

/***/ 5788:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMFeatureFlags.js ***!
  \********************************************************************************************/
168,

/***/ 5789:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactMarkupChecksum.js ***!
  \*******************************************************************************************/
[6323, 5790],

/***/ 5790:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/adler32.js ***!
  \*******************************************************************************/
170,

/***/ 5791:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/findDOMNode.js ***!
  \***********************************************************************************/
[6324, 5652, 5654, 5679, 5750, 5792, 5244, 5247],

/***/ 5792:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/getHostComponentFromComposite.js ***!
  \*****************************************************************************************************/
[6325, 5754],

/***/ 5793:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/renderSubtreeIntoContainer.js ***!
  \**************************************************************************************************/
[6326, 5786],

/***/ 5794:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \***************************************************************************************************/
[6327, 5680, 5687, 5669, 5247],

/***/ 5795:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \******************************************************************************************************/
[6328, 5669, 5247],

/***/ 5796:
/*!***************************************************************************************************************************!*\
  !*** ./~/imports-loader?this=>window,fix=>module.exports=0!./atlas_bundles/heatmap-highcharts/~/snapsvg/dist/snap.svg.js ***!
  \***************************************************************************************************************************/
176,

/***/ 5797:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/SelectionIcon.jsx ***!
  \******************************************************************************/
[6329, 5648, 5798, 5844],

/***/ 5798:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/imagesAvailable.js ***!
  \*******************************************************************************/
[6330, 179, 186, 5799, 5800, 5801, 5812],

/***/ 5799:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/json/svgsForSpecies.json ***!
  \*******************************************************************************************/
187,

/***/ 5800:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/json/idsForSvgs.json ***!
  \***************************************************************************************/
188,

/***/ 5801:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons ^\.\/.*selected\.png$ ***!
  \**********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./brain_selected.png": 5802,
		"./brain_unselected.png": 5803,
		"./female_selected.png": 5804,
		"./female_unselected.png": 5805,
		"./flower_parts_selected.png": 5806,
		"./flower_parts_unselected.png": 5807,
		"./male_selected.png": 5808,
		"./male_unselected.png": 5809,
		"./whole_plant_selected.png": 5810,
		"./whole_plant_unselected.png": 5811
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 5801;


/***/ },

/***/ 5802:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/brain_selected.png ***!
  \*******************************************************************************************/
190,

/***/ 5803:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/brain_unselected.png ***!
  \*********************************************************************************************/
191,

/***/ 5804:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/female_selected.png ***!
  \********************************************************************************************/
192,

/***/ 5805:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/female_unselected.png ***!
  \**********************************************************************************************/
193,

/***/ 5806:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/flower_parts_selected.png ***!
  \**************************************************************************************************/
194,

/***/ 5807:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/flower_parts_unselected.png ***!
  \****************************************************************************************************/
195,

/***/ 5808:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/male_selected.png ***!
  \******************************************************************************************/
196,

/***/ 5809:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/male_unselected.png ***!
  \********************************************************************************************/
197,

/***/ 5810:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/whole_plant_selected.png ***!
  \*************************************************************************************************/
198,

/***/ 5811:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/icons/whole_plant_unselected.png ***!
  \***************************************************************************************************/
199,

/***/ 5812:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg ^\.\/.*$ ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./anolis_carolinensis.svg": 5813,
		"./arabidopsis_thaliana_whole_plant.svg": 5814,
		"./brachypodium_distachyon_flower_parts.svg": 5815,
		"./brachypodium_distachyon_whole_plant.svg": 5816,
		"./chicken.svg": 5817,
		"./cow.svg": 5818,
		"./hordeum_vulgare_flower_parts.svg": 5819,
		"./hordeum_vulgare_whole_plant.svg": 5820,
		"./human_brain.svg": 5821,
		"./human_female.svg": 5822,
		"./human_male.svg": 5823,
		"./macaca_mulatta.svg": 5824,
		"./monodelphis_domestica.svg": 5825,
		"./mouse_brain.svg": 5826,
		"./mouse_female.svg": 5827,
		"./mouse_male.svg": 5828,
		"./oryza_sativa_flower_parts.svg": 5829,
		"./oryza_sativa_whole_plant.svg": 5830,
		"./papio_anubis.svg": 5831,
		"./rat.svg": 5832,
		"./solanum_lycopersicum_flower_parts.svg": 5833,
		"./solanum_lycopersicum_whole_plant.svg": 5834,
		"./solanum_tuberosum_whole_plant.svg": 5835,
		"./sorghum_bicolor_flower_parts.svg": 5836,
		"./sorghum_bicolor_whole_plant.svg": 5837,
		"./tetraodon_nigroviridis.svg": 5838,
		"./triticum_aestivum_flower_parts.svg": 5839,
		"./triticum_aestivum_whole_plant.svg": 5840,
		"./xenopus_tropicalis.svg": 5841,
		"./zea_mays_flower_parts.svg": 5842,
		"./zea_mays_whole_plant.svg": 5843
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 5812;


/***/ },

/***/ 5813:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/anolis_carolinensis.svg ***!
  \**********************************************************************************************/
201,

/***/ 5814:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/arabidopsis_thaliana_whole_plant.svg ***!
  \***********************************************************************************************************/
202,

/***/ 5815:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/brachypodium_distachyon_flower_parts.svg ***!
  \***************************************************************************************************************/
203,

/***/ 5816:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/brachypodium_distachyon_whole_plant.svg ***!
  \**************************************************************************************************************/
204,

/***/ 5817:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/chicken.svg ***!
  \**********************************************************************************/
205,

/***/ 5818:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/cow.svg ***!
  \******************************************************************************/
206,

/***/ 5819:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/hordeum_vulgare_flower_parts.svg ***!
  \*******************************************************************************************************/
207,

/***/ 5820:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/hordeum_vulgare_whole_plant.svg ***!
  \******************************************************************************************************/
208,

/***/ 5821:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/human_brain.svg ***!
  \**************************************************************************************/
209,

/***/ 5822:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/human_female.svg ***!
  \***************************************************************************************/
210,

/***/ 5823:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/human_male.svg ***!
  \*************************************************************************************/
211,

/***/ 5824:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/macaca_mulatta.svg ***!
  \*****************************************************************************************/
212,

/***/ 5825:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/monodelphis_domestica.svg ***!
  \************************************************************************************************/
213,

/***/ 5826:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/mouse_brain.svg ***!
  \**************************************************************************************/
214,

/***/ 5827:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/mouse_female.svg ***!
  \***************************************************************************************/
215,

/***/ 5828:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/mouse_male.svg ***!
  \*************************************************************************************/
216,

/***/ 5829:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/oryza_sativa_flower_parts.svg ***!
  \****************************************************************************************************/
217,

/***/ 5830:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/oryza_sativa_whole_plant.svg ***!
  \***************************************************************************************************/
218,

/***/ 5831:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/papio_anubis.svg ***!
  \***************************************************************************************/
219,

/***/ 5832:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/rat.svg ***!
  \******************************************************************************/
220,

/***/ 5833:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/solanum_lycopersicum_flower_parts.svg ***!
  \************************************************************************************************************/
221,

/***/ 5834:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/solanum_lycopersicum_whole_plant.svg ***!
  \***********************************************************************************************************/
222,

/***/ 5835:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/solanum_tuberosum_whole_plant.svg ***!
  \********************************************************************************************************/
223,

/***/ 5836:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/sorghum_bicolor_flower_parts.svg ***!
  \*******************************************************************************************************/
224,

/***/ 5837:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/sorghum_bicolor_whole_plant.svg ***!
  \******************************************************************************************************/
225,

/***/ 5838:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/tetraodon_nigroviridis.svg ***!
  \*************************************************************************************************/
226,

/***/ 5839:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/triticum_aestivum_flower_parts.svg ***!
  \*********************************************************************************************************/
227,

/***/ 5840:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/triticum_aestivum_whole_plant.svg ***!
  \********************************************************************************************************/
228,

/***/ 5841:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/xenopus_tropicalis.svg ***!
  \*********************************************************************************************/
229,

/***/ 5842:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/zea_mays_flower_parts.svg ***!
  \************************************************************************************************/
230,

/***/ 5843:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/resources/svg/zea_mays_whole_plant.svg ***!
  \***********************************************************************************************/
231,

/***/ 5844:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../~/css-loader!../../../../../~/less-loader!./SelectionIcon.less */ 5845);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/less-loader/index.js!./SelectionIcon.less", function() {
				var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/less-loader/index.js!./SelectionIcon.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 5845:
/*!**************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \**************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".selection-icon {\n  display: block;\n  position: relative;\n  padding: 0;\n  line-height: normal;\n  margin-right: .1em;\n  cursor: pointer;\n  vertical-align: middle;\n  text-align: center;\n  overflow: visible;\n  border: 1px solid #ccc;\n  border-top-left-radius: 4px;\n  border-top-right-radius: 4px;\n  border-bottom-left-radius: 4px;\n  border-bottom-right-radius: 4px;\n  width: 24px;\n  height: 24px;\n  padding: 2px;\n}\n.selection-icon:hover {\n  border: 1px solid #fbcb09;\n  background: #fdf5ce 50% 50% repeat-x;\n  font-weight: bold;\n  color: #c77405;\n}\n.jquery-ui-like-button {\n  display: block;\n  position: relative;\n  padding: 0;\n  line-height: normal;\n  margin-right: .1em;\n  cursor: pointer;\n  vertical-align: middle;\n  text-align: center;\n  overflow: visible;\n}\n.rounded-corners {\n  border: 1px solid #ccc;\n  border-top-left-radius: 4px;\n  border-top-right-radius: 4px;\n  border-bottom-left-radius: 4px;\n  border-bottom-right-radius: 4px;\n}\n.right-dimensions {\n  width: 24px;\n  height: 24px;\n  padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5846:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../~/css-loader!../../../../../~/less-loader!./ContainerLayout.less */ 5847);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/less-loader/index.js!./ContainerLayout.less", function() {
				var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/less-loader/index.js!./ContainerLayout.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 5847:
/*!****************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, "#gxaAnatomogramWrapper {\n  display: block;\n  zoom: 1;\n  position: relative;\n  overflow: hidden;\n  marginLeft: 270px;\n}\n#gxaAnatomogramWrapper:after {\n  content: \" \";\n  display: block;\n  font-size: 0;\n  height: 0;\n  clear: both;\n  visibility: hidden;\n}\n#gxaAnatomogramAside {\n  float: left;\n  max-width: 270px;\n}\n.clearfix {\n  display: block;\n  zoom: 1;\n}\n.clearfix:after {\n  content: \" \";\n  display: block;\n  font-size: 0;\n  height: 0;\n  clear: both;\n  visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 5848:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/ExperimentDescription.jsx ***!
  \*********************************************************************************************************************/
[7007, 5238],

/***/ 5849:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/Footer.jsx ***!
  \******************************************************************************************************/
[7008, 5238],

/***/ 5850:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/ChartContainer.jsx ***!
  \******************************************************************************************************************/
[7009, 5238, 5851, 6174, 6041],

/***/ 5851:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/HeatmapWithControls.jsx ***!
  \***********************************************************************************************************************/
[7010, 5238, 5852, 5989, 6043, 6051, 6064, 6070, 6076, 6110, 6111, 6119, 6173, 6041],

/***/ 5852:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/OrderingsDropdown.jsx ***!
  \******************************************************************************************************************************/
[7011, 5238, 5853, 5987, 5988],

/***/ 5853:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/Dropdown.js ***!
  \****************************************************************************/
[6627, 5854, 5855, 5893, 5894, 5930, 5938, 5939, 5942, 5944, 5238, 5268, 5945, 5947, 5948, 5949, 5952, 5953, 5965, 5985, 5959, 5983, 5986, 5984],

/***/ 5854:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \*********************************************************************************************/
286,

/***/ 5855:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/extends.js ***!
  \*****************************************************************************/
[6368, 5856],

/***/ 5856:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/assign.js ***!
  \***********************************************************************************/
[6369, 5857],

/***/ 5857:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \************************************************************************************************/
[6370, 5858, 5861],

/***/ 5858:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \*********************************************************************************************************/
[6371, 5859, 5874],

/***/ 5859:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \***********************************************************************************************/
[6349, 5860, 5861, 5862, 5864],

/***/ 5860:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \***********************************************************************************************/
255,

/***/ 5861:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \*********************************************************************************************/
256,

/***/ 5862:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \********************************************************************************************/
[6350, 5863],

/***/ 5863:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \***************************************************************************************************/
258,

/***/ 5864:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \*********************************************************************************************/
[6351, 5865, 5873, 5869],

/***/ 5865:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \**************************************************************************************************/
[6352, 5866, 5868, 5872, 5869],

/***/ 5866:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \**************************************************************************************************/
[6353, 5867],

/***/ 5867:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \**************************************************************************************************/
262,

/***/ 5868:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \*******************************************************************************************************/
[6354, 5869, 5870, 5871],

/***/ 5869:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \****************************************************************************************************/
[6355, 5870],

/***/ 5870:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \**********************************************************************************************/
265,

/***/ 5871:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \***************************************************************************************************/
[6356, 5867, 5860],

/***/ 5872:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \*****************************************************************************************************/
[6357, 5867],

/***/ 5873:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \******************************************************************************************************/
268,

/***/ 5874:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \******************************************************************************************************/
[6372, 5875, 5890, 5891, 5892, 5879, 5870],

/***/ 5875:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \****************************************************************************************************/
[6359, 5876, 5889],

/***/ 5876:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \*************************************************************************************************************/
[6360, 5877, 5878, 5882, 5886],

/***/ 5877:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \********************************************************************************************/
272,

/***/ 5878:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \***************************************************************************************************/
[6361, 5879, 5881],

/***/ 5879:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \************************************************************************************************/
[6362, 5880],

/***/ 5880:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \********************************************************************************************/
275,

/***/ 5881:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \************************************************************************************************/
276,

/***/ 5882:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \*******************************************************************************************************/
[6363, 5878, 5883, 5885],

/***/ 5883:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \**************************************************************************************************/
[6364, 5884],

/***/ 5884:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \***************************************************************************************************/
279,

/***/ 5885:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \*************************************************************************************************/
[6365, 5884],

/***/ 5886:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \***************************************************************************************************/
[6366, 5887, 5888],

/***/ 5887:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \***********************************************************************************************/
[6367, 5860],

/***/ 5888:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \********************************************************************************************/
283,

/***/ 5889:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \******************************************************************************************************/
284,

/***/ 5890:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \****************************************************************************************************/
292,

/***/ 5891:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \***************************************************************************************************/
285,

/***/ 5892:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \**************************************************************************************************/
[6373, 5881],

/***/ 5893:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/classCallCheck.js ***!
  \************************************************************************************/
294,

/***/ 5894:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \***********************************************************************************************/
[6374, 5895],

/***/ 5895:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/typeof.js ***!
  \****************************************************************************/
[6375, 5896, 5916],

/***/ 5896:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/symbol/iterator.js ***!
  \*************************************************************************************/
[6376, 5897],

/***/ 5897:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \**************************************************************************************************/
[6377, 5898, 5911, 5915],

/***/ 5898:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \***********************************************************************************************************/
[6378, 5899, 5900],

/***/ 5899:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \**************************************************************************************************/
[6379, 5884, 5881],

/***/ 5900:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \****************************************************************************************************/
[6380, 5901, 5859, 5902, 5864, 5877, 5903, 5904, 5908, 5910, 5909],

/***/ 5901:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \************************************************************************************************/
302,

/***/ 5902:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \*************************************************************************************************/
[6381, 5864],

/***/ 5903:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \**************************************************************************************************/
304,

/***/ 5904:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \****************************************************************************************************/
[6382, 5905, 5873, 5908, 5864, 5909],

/***/ 5905:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \******************************************************************************************************/
[6383, 5866, 5906, 5889, 5886, 5871, 5907],

/***/ 5906:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \***************************************************************************************************/
[6384, 5865, 5866, 5875, 5869],

/***/ 5907:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \*********************************************************************************************/
[6385, 5860],

/***/ 5908:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \**********************************************************************************************************/
[6386, 5865, 5877, 5909],

/***/ 5909:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \********************************************************************************************/
[6387, 5887, 5888, 5860],

/***/ 5910:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \***************************************************************************************************/
[6388, 5877, 5892, 5886],

/***/ 5911:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \********************************************************************************************************/
[6389, 5912, 5860, 5864, 5903, 5909],

/***/ 5912:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \**********************************************************************************************************/
[6390, 5913, 5914, 5903, 5878, 5900],

/***/ 5913:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \***********************************************************************************************************/
314,

/***/ 5914:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \**************************************************************************************************/
315,

/***/ 5915:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \************************************************************************************************/
[6391, 5909],

/***/ 5916:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/symbol.js ***!
  \****************************************************************************/
[6392, 5917],

/***/ 5917:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \***********************************************************************************************/
[6393, 5918, 5927, 5928, 5929, 5861],

/***/ 5918:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \**************************************************************************************************/
[6394, 5860, 5877, 5869, 5859, 5902, 5919, 5870, 5887, 5908, 5888, 5909, 5915, 5920, 5921, 5922, 5923, 5866, 5878, 5872, 5873, 5905, 5924, 5926, 5865, 5875, 5925, 5891, 5890, 5901, 5864],

/***/ 5919:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \*********************************************************************************************/
[6395, 5888, 5867, 5877, 5865, 5870],

/***/ 5920:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \***************************************************************************************************/
[6396, 5860, 5861, 5901, 5915, 5865],

/***/ 5921:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \**********************************************************************************************/
[6397, 5875, 5878],

/***/ 5922:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \**************************************************************************************************/
[6398, 5875, 5890, 5891],

/***/ 5923:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \*************************************************************************************************/
[6399, 5880],

/***/ 5924:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \********************************************************************************************************/
[6400, 5878, 5925],

/***/ 5925:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \****************************************************************************************************/
[6401, 5876, 5889],

/***/ 5926:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \****************************************************************************************************/
[6402, 5891, 5873, 5878, 5872, 5877, 5868, 5869],

/***/ 5927:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \************************************************************************************************************/
328,

/***/ 5928:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \*****************************************************************************************************************/
[6403, 5920],

/***/ 5929:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \*************************************************************************************************************/
[6404, 5920],

/***/ 5930:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/inherits.js ***!
  \******************************************************************************/
[6405, 5931, 5935, 5895],

/***/ 5931:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \*********************************************************************************************/
[6406, 5932],

/***/ 5932:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \**********************************************************************************************************/
[6407, 5933, 5861],

/***/ 5933:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \*******************************************************************************************************************/
[6408, 5859, 5934],

/***/ 5934:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \**************************************************************************************************/
[6409, 5867, 5866, 5862, 5926],

/***/ 5935:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/create.js ***!
  \***********************************************************************************/
[6410, 5936],

/***/ 5936:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \************************************************************************************************/
[6411, 5937, 5861],

/***/ 5937:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \*********************************************************************************************************/
[6412, 5859, 5905],

/***/ 5938:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/classnames/index.js ***!
  \****************************************************************/
339,

/***/ 5939:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/activeElement.js ***!
  \*************************************************************************/
[6628, 5940, 5941],

/***/ 5940:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/util/babelHelpers.js ***!
  \*****************************************************************************/
979,

/***/ 5941:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/ownerDocument.js ***!
  \*************************************************************************/
987,

/***/ 5942:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/query/contains.js ***!
  \**************************************************************************/
[6629, 5943],

/***/ 5943:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/util/inDOM.js ***!
  \**********************************************************************/
989,

/***/ 5944:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/keycode/index.js ***!
  \*************************************************************/
990,

/***/ 5945:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-prop-types/lib/all.js ***!
  \************************************************************************/
[6610, 5946],

/***/ 5946:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \*****************************************************************************************************/
341,

/***/ 5947:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-prop-types/lib/elementType.js ***!
  \********************************************************************************/
[6413, 5238, 5946],

/***/ 5948:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-prop-types/lib/isRequiredForA11y.js ***!
  \**************************************************************************************/
991,

/***/ 5949:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/uncontrollable/index.js ***!
  \********************************************************************/
[6630, 5950],

/***/ 5950:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/uncontrollable/createUncontrollable.js ***!
  \***********************************************************************************/
[6631, 5238, 5427, 5951],

/***/ 5951:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/uncontrollable/utils.js ***!
  \********************************************************************/
[6632, 5238, 5427],

/***/ 5952:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/warning/browser.js ***!
  \***************************************************************/
352,

/***/ 5953:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ButtonGroup.js ***!
  \*******************************************************************************/
[6609, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5945, 5954, 5959],

/***/ 5954:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/Button.js ***!
  \**************************************************************************/
[6345, 5955, 5854, 5855, 5893, 5894, 5930, 5938, 5238, 5947, 5959, 5963, 5964],

/***/ 5955:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/values.js ***!
  \***********************************************************************************/
[6346, 5956],

/***/ 5956:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \************************************************************************************************/
[6347, 5957, 5861],

/***/ 5957:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \*********************************************************************************************************/
[6348, 5859, 5958],

/***/ 5958:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \********************************************************************************************************/
[6358, 5875, 5878, 5891],

/***/ 5959:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \****************************************************************************************/
[6414, 5960, 5855, 5427, 5238, 5963],

/***/ 5960:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/entries.js ***!
  \************************************************************************************/
[6415, 5961],

/***/ 5961:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \*************************************************************************************************/
[6416, 5962, 5861],

/***/ 5962:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \**********************************************************************************************************/
[6417, 5859, 5958],

/***/ 5963:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \*************************************************************************************/
347,

/***/ 5964:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/SafeAnchor.js ***!
  \******************************************************************************/
[6418, 5855, 5854, 5893, 5894, 5930, 5238, 5947],

/***/ 5965:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/DropdownMenu.js ***!
  \********************************************************************************/
[6633, 5855, 5854, 5966, 5893, 5894, 5930, 5938, 5944, 5238, 5268, 5975, 5959, 5983, 5984],

/***/ 5966:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/array/from.js ***!
  \********************************************************************************/
[6569, 5967],

/***/ 5967:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/array/from.js ***!
  \*********************************************************************************************/
[6570, 5898, 5968, 5861],

/***/ 5968:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.array.from.js ***!
  \******************************************************************************************************/
[6549, 5862, 5859, 5892, 5969, 5970, 5883, 5971, 5972, 5974],

/***/ 5969:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iter-call.js ***!
  \**************************************************************************************************/
[6550, 5866],

/***/ 5970:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_is-array-iter.js ***!
  \******************************************************************************************************/
[6551, 5903, 5909],

/***/ 5971:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_create-property.js ***!
  \********************************************************************************************************/
[6552, 5865, 5873],

/***/ 5972:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/core.get-iterator-method.js ***!
  \****************************************************************************************************************/
[6553, 5973, 5909, 5903, 5861],

/***/ 5973:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_classof.js ***!
  \************************************************************************************************/
[6548, 5880, 5909],

/***/ 5974:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/_iter-detect.js ***!
  \****************************************************************************************************/
[6554, 5909],

/***/ 5975:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/RootCloseWrapper.js ***!
  \***********************************************************************************/
[6634, 5976, 5238, 5268, 5978, 5981],

/***/ 5976:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/query/contains.js ***!
  \*******************************************************************************************/
[6635, 5977],

/***/ 5977:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/inDOM.js ***!
  \***************************************************************************************/
983,

/***/ 5978:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/addEventListener.js ***!
  \*****************************************************************************************/
[6636, 5979, 5980],

/***/ 5979:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/events/on.js ***!
  \**************************************************************************************/
[6625, 5977],

/***/ 5980:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/events/off.js ***!
  \***************************************************************************************/
[6637, 5977],

/***/ 5981:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/ownerDocument.js ***!
  \**************************************************************************************/
[6638, 5268, 5982],

/***/ 5982:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/ownerDocument.js ***!
  \******************************************************************************************/
1001,

/***/ 5983:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/createChainedFunction.js ***!
  \***********************************************************************************************/
955,

/***/ 5984:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \************************************************************************************************/
[6420, 5238],

/***/ 5985:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/DropdownToggle.js ***!
  \**********************************************************************************/
[6639, 5855, 5854, 5893, 5894, 5930, 5238, 5938, 5954, 5964, 5959],

/***/ 5986:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/PropTypes.js ***!
  \***********************************************************************************/
[6640, 5946, 5984],

/***/ 5987:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/MenuItem.js ***!
  \****************************************************************************/
[6662, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5945, 5964, 5959, 5983],

/***/ 5988:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/Glyphicon.js ***!
  \*****************************************************************************/
[6423, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5959],

/***/ 5989:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/FiltersModal.jsx ***!
  \********************************************************************************************************************************/
[7012, 5238, 5990, 5954, 5988, 6029, 6040, 6041],

/***/ 5990:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/Modal.js ***!
  \*************************************************************************/
[6663, 5854, 5893, 5894, 5930, 5855, 5938, 5991, 5941, 5943, 5996, 5238, 5268, 5997, 6016, 5947, 6021, 6023, 6024, 6025, 6026, 6027, 5959, 5983, 6028, 5963],

/***/ 5991:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/events/index.js ***!
  \************************************************************************/
[6664, 5992, 5993, 5994],

/***/ 5992:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/events/on.js ***!
  \*********************************************************************/
[6665, 5943],

/***/ 5993:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/events/off.js ***!
  \**********************************************************************/
[6666, 5943],

/***/ 5994:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/events/filter.js ***!
  \*************************************************************************/
[6667, 5942, 5995],

/***/ 5995:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/query/querySelectorAll.js ***!
  \**********************************************************************************/
1031,

/***/ 5996:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-helpers/util/scrollbarSize.js ***!
  \******************************************************************************/
[6668, 5943],

/***/ 5997:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/Modal.js ***!
  \************************************************************************/
[6669, 5238, 5952, 5998, 5947, 5999, 6001, 5981, 5978, 6019, 5977, 6020, 5976, 6000],

/***/ 5998:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-prop-types/lib/componentOrElement.js ***!
  \***************************************************************************************/
[6670, 5238, 5946],

/***/ 5999:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/Portal.js ***!
  \*************************************************************************/
[6671, 5238, 5268, 5998, 5981, 6000],

/***/ 6000:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/getContainer.js ***!
  \*************************************************************************************/
[6672, 5268],

/***/ 6001:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/ModalManager.js ***!
  \*******************************************************************************/
[6673, 6002, 6011, 6015, 6016, 6018],

/***/ 6002:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/style/index.js ***!
  \****************************************************************************************/
[6674, 6003, 6005, 6007, 6008, 6009, 6010],

/***/ 6003:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/camelizeStyle.js ***!
  \***********************************************************************************************/
[6675, 6004],

/***/ 6004:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/camelize.js ***!
  \******************************************************************************************/
1040,

/***/ 6005:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/hyphenateStyle.js ***!
  \************************************************************************************************/
[6676, 6006],

/***/ 6006:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/hyphenate.js ***!
  \*******************************************************************************************/
1042,

/***/ 6007:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/style/getComputedStyle.js ***!
  \***************************************************************************************************/
[6677, 6003],

/***/ 6008:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/style/removeStyle.js ***!
  \**********************************************************************************************/
1044,

/***/ 6009:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/transition/properties.js ***!
  \**************************************************************************************************/
[6626, 5977],

/***/ 6010:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/transition/isTransform.js ***!
  \***************************************************************************************************/
1045,

/***/ 6011:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/class/index.js ***!
  \****************************************************************************************/
[6678, 6012, 6014, 6013],

/***/ 6012:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/class/addClass.js ***!
  \*******************************************************************************************/
[6679, 6013],

/***/ 6013:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/class/hasClass.js ***!
  \*******************************************************************************************/
1048,

/***/ 6014:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/class/removeClass.js ***!
  \**********************************************************************************************/
1049,

/***/ 6015:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/util/scrollbarSize.js ***!
  \***********************************************************************************************/
[6680, 5977],

/***/ 6016:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/isOverflowing.js ***!
  \**************************************************************************************/
[6681, 6017, 5982],

/***/ 6017:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/query/isWindow.js ***!
  \*******************************************************************************************/
1052,

/***/ 6018:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/manageAriaHidden.js ***!
  \*****************************************************************************************/
1053,

/***/ 6019:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/utils/addFocusListener.js ***!
  \*****************************************************************************************/
1054,

/***/ 6020:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/~/dom-helpers/activeElement.js ***!
  \******************************************************************************************/
[6682, 5982],

/***/ 6021:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/Fade.js ***!
  \************************************************************************/
[6643, 5855, 5893, 5894, 5930, 5938, 5238, 6022],

/***/ 6022:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-overlays/lib/Transition.js ***!
  \*****************************************************************************/
[6624, 5938, 5979, 6009, 5238, 5268],

/***/ 6023:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ModalBody.js ***!
  \*****************************************************************************/
[6683, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5947, 5959],

/***/ 6024:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ModalDialog.js ***!
  \*******************************************************************************/
[6684, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5959, 5963],

/***/ 6025:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ModalFooter.js ***!
  \*******************************************************************************/
[6685, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5947, 5959],

/***/ 6026:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ModalHeader.js ***!
  \*******************************************************************************/
[6686, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5959, 5983],

/***/ 6027:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/ModalTitle.js ***!
  \******************************************************************************/
[6687, 5855, 5854, 5893, 5894, 5930, 5938, 5238, 5947, 5959],

/***/ 6028:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-bootstrap/lib/utils/splitComponentProps.js ***!
  \*********************************************************************************************/
[6642, 5960],

/***/ 6029:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/FlatFilter.jsx ***!
  \******************************************************************************************************************************/
[7013, 5238, 6030, 6038],

/***/ 6030:
/*!**********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/xor.js ***!
  \**********************************************************/
[7014, 5566, 6031, 6032, 6037],

/***/ 6031:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseRest.js ***!
  \****************************************************************/
[7015, 5440, 5636, 5485],

/***/ 6032:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseXor.js ***!
  \***************************************************************/
[7016, 6033, 5634, 6035],

/***/ 6033:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseDifference.js ***!
  \**********************************************************************/
[7017, 5603, 5491, 6034, 5623, 5521, 5607],

/***/ 6034:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_arrayIncludesWith.js ***!
  \*************************************************************************/
1514,

/***/ 6035:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseUniq.js ***!
  \****************************************************************/
[7018, 5603, 5491, 6034, 5607, 6036, 5591],

/***/ 6036:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createSet.js ***!
  \*****************************************************************/
[7019, 5578, 5471, 5591],

/***/ 6037:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/isArrayLikeObject.js ***!
  \************************************************************************/
[7020, 5527, 5477],

/***/ 6038:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/Filter.less ***!
  \***************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../../~/css-loader!../../../../../../../../~/less-loader!./Filter.less */ 6039);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../../node_modules/css-loader/index.js!../../../../../../../../node_modules/less-loader/index.js!./Filter.less", function() {
				var newContent = require("!!../../../../../../../../node_modules/css-loader/index.js!../../../../../../../../node_modules/less-loader/index.js!./Filter.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6039:
/*!**********************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/Filter.less ***!
  \**********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaFilter {\n  padding-bottom: 1.25rem;\n}\n.gxaFilter .filterBody input {\n  margin: 0.2rem;\n}\n.gxaFilter .filterBody .groupName {\n  display: inline-block;\n  cursor: pointer;\n}\n.gxaFilter .filterBody .groupName:first-letter {\n  text-transform: capitalize;\n}\n.gxaFilter .filterBody .groupName:hover {\n  text-decoration: underline;\n}\n.gxaFilter .filterBody .groupName:indeterminate {\n  opacity: 0.75;\n}\n.gxaFilter .filterBody .options {\n  padding-left: 15px;\n  font-size: 85%;\n  -webkit-column-width: 180px;\n  -moz-column-width: 180px;\n  column-width: 180px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6040:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/GroupingFilter.jsx ***!
  \**********************************************************************************************************************************/
[7022, 5238, 5953, 5954, 5988, 6030, 6038],

/***/ 6041:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/chartDataPropTypes.js ***!
  \*********************************************************************************************************************/
[7023, 5238, 6042],

/***/ 6042:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/experimentTypeUtils.js ***!
  \****************************************************************************************************************/
[7024, 5238],

/***/ 6043:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/DownloadButton.jsx ***!
  \*******************************************************************************************************************************************/
[7025, 5238, 5990, 5954, 5988, 6044, 6045, 6041],

/***/ 6044:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/Disclaimers.jsx ***!
  \****************************************************************************************************************************************/
[7026, 5238],

/***/ 6045:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/Download.js ***!
  \************************************************************************************************************************************/
[7027, 6046, 6050],

/***/ 6046:
/*!************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/range.js ***!
  \************************************************************/
[7028, 6047],

/***/ 6047:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_createRange.js ***!
  \*******************************************************************/
[7029, 6048, 6049, 5503],

/***/ 6048:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_baseRange.js ***!
  \*****************************************************************/
1528,

/***/ 6049:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/_isIterateeCall.js ***!
  \**********************************************************************/
[7030, 5510, 5527, 5498, 5452],

/***/ 6050:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/downloadjs/download.js ***!
  \*******************************************************************/
831,

/***/ 6051:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.jsx ***!
  \********************************************************************************************************************************/
[7031, 5238, 6052, 6062],

/***/ 6052:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/index.js ***!
  \************************************************************************/
[6732, 5238, 5268, 5938, 6053, 6055, 6056, 6057, 6058, 6059, 6060, 6061],

/***/ 6053:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/decorators/staticMethods.js ***!
  \*******************************************************************************************/
[6733, 6054],

/***/ 6054:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/constant.js ***!
  \***************************************************************************/
1107,

/***/ 6055:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/decorators/windowListener.js ***!
  \********************************************************************************************/
[6734, 6054],

/***/ 6056:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/decorators/customEvent.js ***!
  \*****************************************************************************************/
1109,

/***/ 6057:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/decorators/isCapture.js ***!
  \***************************************************************************************/
1110,

/***/ 6058:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/utils/getPosition.js ***!
  \************************************************************************************/
1111,

/***/ 6059:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/utils/getTipContent.js ***!
  \**************************************************************************************/
[6735, 5238],

/***/ 6060:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/utils/aria.js ***!
  \*****************************************************************************/
1113,

/***/ 6061:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-tooltip/dist/style.js ***!
  \************************************************************************/
1114,

/***/ 6062:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.less ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../~/css-loader!../../../../../../../~/less-loader!./TooltipStateManager.less */ 6063);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./TooltipStateManager.less", function() {
				var newContent = require("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./TooltipStateManager.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6063:
/*!****************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.less ***!
  \****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaFadeBackgroundForOpenTooltip {\n  opacity: 0.5;\n  pointer-events: none;\n}\n.highcharts-container {\n  overflow: visible !important;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6064:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/show/HeatmapCanvas.jsx ***!
  \***********************************************************************************************************/
[7033, 5238, 6065, 6067, 6068, 6069, 6041],

/***/ 6065:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-highcharts/dist/ReactHighcharts.js ***!
  \*************************************************************************************/
[6731, 5238, 6066],

/***/ 6066:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/highcharts/highcharts.js ***!
  \*********************************************************************/
835,

/***/ 6067:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/highcharts/modules/heatmap.js ***!
  \**************************************************************************/
1534,

/***/ 6068:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/highcharts-custom-events/js/customEvents.js ***!
  \****************************************************************************************/
836,

/***/ 6069:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/object-hash/index.js ***!
  \*****************************************************************/
839,

/***/ 6070:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/main.jsx ***!
  \*****************************************************************************************************************/
[7034, 5238, 6071, 6072, 6073, 6074],

/***/ 6071:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/FactorTooltip.jsx ***!
  \**************************************************************************************************************************/
[7035, 5238],

/***/ 6072:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/ContrastTooltip.jsx ***!
  \****************************************************************************************************************************/
[7036, 5238],

/***/ 6073:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/GeneTooltip.jsx ***!
  \************************************************************************************************************************/
[7037, 5238],

/***/ 6074:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/Tooltips.less ***!
  \**********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../~/css-loader!../../../../../../../~/less-loader!./Tooltips.less */ 6075);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./Tooltips.less", function() {
				var newContent = require("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./Tooltips.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6075:
/*!*****************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/Tooltips.less ***!
  \*****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n}\n.gxaTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaTooltip td,\n.gxaTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaGeneTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 250px;\n  text-align: justify;\n}\n.gxaGeneTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaGeneTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaGeneTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaGeneTooltip td,\n.gxaGeneTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaGeneTooltip .propertyName {\n  color: brown;\n  font-weight: bold;\n  display: inline-block;\n  text-align: left;\n  margin-right: 5px;\n}\n.gxaGeneTooltip .propertyValue {\n  display: inline-block;\n  text-align: center;\n  background-color: #dfd5d5;\n}\n.gxaGeneTooltip .propertyValue:first-child {\n  margin-left: 5px;\n}\n.gxaGeneTooltip .propertyValue + .propertyValue {\n  margin-left: 5px;\n}\n.gxaContrastTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 500px;\n  display: inline-block;\n}\n.gxaContrastTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaContrastTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaContrastTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaContrastTooltip td,\n.gxaContrastTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaContrastTooltip .contrastPlots .info {\n  font-style: italic;\n  font-size: xsmall;\n  align-content: right;\n  text-align: right;\n}\n.gxaFactorTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 600px;\n}\n.gxaFactorTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaFactorTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaFactorTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaFactorTooltip td,\n.gxaFactorTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6076:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/heatmapCellTooltipFormatter.jsx ***!
  \******************************************************************************************************************************************/
[7039, 5238, 6077, 6081, 6109],

/***/ 6077:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/server.js ***!
  \****************************************************************/
[7040, 6078],

/***/ 6078:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMServer.js ***!
  \****************************************************************************/
[7041, 5274, 6079, 5407],

/***/ 6079:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerRendering.js ***!
  \**********************************************************************************/
[7042, 5271, 5239, 5403, 5376, 5298, 5405, 5295, 6080, 5369, 5292, 5256, 5354, 5244],

/***/ 6080:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerBatchingStrategy.js ***!
  \*****************************************************************************************/
1545,

/***/ 6081:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/HeatmapCellTooltip.jsx ***!
  \*********************************************************************************************************************************/
[7043, 5238, 6082],

/***/ 6082:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/index.js ***!
  \************************************************************************************/
[6560, 6083],

/***/ 6083:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \************************************************************************************************/
[6561, 6084],

/***/ 6084:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/react.js ***!
  \********************************************************************************************/
[6201, 6085],

/***/ 6085:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/React.js ***!
  \************************************************************************************************/
[6202, 5240, 6086, 6095, 6097, 6098, 6101, 6089, 6106, 6107, 6108, 5247, 6102],

/***/ 6086:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactChildren.js ***!
  \********************************************************************************************************/
[6203, 6087, 6089, 5248, 6092],

/***/ 6087:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/PooledClass.js ***!
  \******************************************************************************************************/
[6204, 6088, 5244],

/***/ 6088:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/reactProdInvariant.js ***!
  \*************************************************************************************************************/
8,

/***/ 6089:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactElement.js ***!
  \*******************************************************************************************************/
[6205, 5240, 6090, 5247, 6091],

/***/ 6090:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactCurrentOwner.js ***!
  \************************************************************************************************************/
11,

/***/ 6091:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/canDefineProperty.js ***!
  \************************************************************************************************************/
14,

/***/ 6092:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/traverseAllChildren.js ***!
  \**************************************************************************************************************/
[6207, 6088, 6090, 6089, 6093, 5244, 6094, 5247],

/***/ 6093:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/getIteratorFn.js ***!
  \********************************************************************************************************/
16,

/***/ 6094:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/KeyEscapeUtils.js ***!
  \*********************************************************************************************************/
17,

/***/ 6095:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactComponent.js ***!
  \*********************************************************************************************************/
[6208, 6088, 6096, 6091, 5256, 5244, 5247],

/***/ 6096:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***************************************************************************************************************/
[6209, 5247],

/***/ 6097:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPureComponent.js ***!
  \*************************************************************************************************************/
[6210, 5240, 6095, 6096, 5256],

/***/ 6098:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactClass.js ***!
  \*****************************************************************************************************/
[6211, 6088, 5240, 6095, 6089, 6099, 6100, 6096, 5256, 5244, 5664, 5666, 5247],

/***/ 6099:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypeLocations.js ***!
  \*****************************************************************************************************************/
[6212, 5664],

/***/ 6100:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*********************************************************************************************************************/
25,

/***/ 6101:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactDOMFactories.js ***!
  \************************************************************************************************************/
[6214, 6089, 6102],

/***/ 6102:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactElementValidator.js ***!
  \****************************************************************************************************************/
[6215, 6090, 6103, 6089, 6099, 6104, 6091, 6093, 5247],

/***/ 6103:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactComponentTreeHook.js ***!
  \*****************************************************************************************************************/
[6216, 6088, 6090, 5244, 5247],

/***/ 6104:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/checkReactTypeSpec.js ***!
  \*************************************************************************************************************/
[6217, 6088, 6100, 6105, 5244, 5247, 6103, 6103],

/***/ 6105:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypesSecret.js ***!
  \***************************************************************************************************************/
32,

/***/ 6106:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypes.js ***!
  \*********************************************************************************************************/
[6218, 6089, 6100, 6105, 5248, 6093, 5247],

/***/ 6107:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactVersion.js ***!
  \*******************************************************************************************************/
34,

/***/ 6108:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/onlyChild.js ***!
  \****************************************************************************************************/
[6219, 6088, 6089, 5244],

/***/ 6109:
/*!*****************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/he/he.js ***!
  \*****************************************************/
834,

/***/ 6110:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/axesFormatters.jsx ***!
  \*****************************************************************************************************************************/
[7044, 5238, 6077, 6109],

/***/ 6111:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/HeatmapLegend.jsx ***!
  \********************************************************************************************************************************/
[7045, 5238, 6112, 6115, 6041],

/***/ 6112:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.jsx ***!
  \******************************************************************************************************************************************/
[7046, 5238, 6113],

/***/ 6113:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.less ***!
  \*******************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../~/css-loader!../../../../../../../~/less-loader!./DataSeriesHeatmapLegend.less */ 6114);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./DataSeriesHeatmapLegend.less", function() {
				var newContent = require("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./DataSeriesHeatmapLegend.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6114:
/*!**************************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.less ***!
  \**************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHeatmapLegend {\n  color: #606060;\n  margin-left: 180px;\n  border: 0 solid olive;\n}\n.gxaHeatmapLegend .legend-item {\n  display: inline-block;\n  margin-left: 8px;\n  padding: 4px;\n  vertical-align: middle;\n  cursor: default;\n}\n.gxaHeatmapLegend .legend-item.legend-item-off {\n  color: #ccc;\n}\n.gxaHeatmapLegend .legend-item.legend-item-off div {\n  background-color: #f7f7f7;\n}\n.gxaHeatmapLegend .legend-item .legend-rectangle {\n  width: 12px;\n  height: 12px;\n  border: 1px rgba(0, 0, 0, 0.2) solid;\n  display: inline-block;\n  margin-right: 4px;\n  vertical-align: middle;\n}\n.gxaHeatmapLegend .legend-item .icon-generic:before {\n  font-size: 180%;\n  color: #7e7e7e;\n}\n.gxaHeatmapLegend .legend-item:hover .icon-generic:before {\n  color: #353535;\n}\n@font-face {\n  font-family: 'EBI-Generic';\n  src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');\n  src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');\n  font-weight: normal;\n  font-style: normal;\n}\n.icon-generic:before {\n  font-family: 'EBI-Generic';\n  font-size: 100%;\n  color: #BBB;\n  content: attr(data-icon);\n  margin: 0 0 0 0;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6115:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.jsx ***!
  \****************************************************************************************************************************************/
[7048, 5238, 6116, 6117],

/***/ 6116:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/utils.js ***!
  \*********************************************************************************************/
1553,

/***/ 6117:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.less ***!
  \*****************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../~/css-loader!../../../../../../../~/less-loader!./GradientHeatmapLegend.less */ 6118);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./GradientHeatmapLegend.less", function() {
				var newContent = require("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./GradientHeatmapLegend.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6118:
/*!************************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.less ***!
  \************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n  overflow: auto;\n  vertical-align: middle;\n  width: 200px;\n  height: 15px;\n  margin: 2px 6px 2px 6px;\n  display: inline-block;\n}\n.gxaGradientLevel {\n  white-space: nowrap;\n  font-size: 10px;\n  vertical-align: middle;\n  display: table-cell;\n}\n.gxaGradientLevelMin {\n  text-align: right;\n}\n.gxaGradientLevelMax {\n  text-align: left;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6119:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.jsx ***!
  \***********************************************************************************************************************************/
[7050, 5238, 5954, 5988, 6120, 6169, 6171],

/***/ 6120:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/index.js ***!
  \*******************************************************************/
[6563, 6121],

/***/ 6121:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/Slider.js ***!
  \********************************************************************/
[6564, 6122, 6126, 5855, 5893, 5894, 5930, 5238, 6127, 5938, 6131, 6132, 6167, 6168],

/***/ 6122:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/defineProperty.js ***!
  \************************************************************************************/
[6565, 6123],

/***/ 6123:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/core-js/object/define-property.js ***!
  \********************************************************************************************/
[6566, 6124],

/***/ 6124:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/fn/object/define-property.js ***!
  \*********************************************************************************************************/
[6567, 6125, 5861],

/***/ 6125:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/~/core-js/library/modules/es6.object.define-property.js ***!
  \******************************************************************************************************************/
[6547, 5859, 5869, 5865],

/***/ 6126:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/babel-runtime/helpers/toConsumableArray.js ***!
  \***************************************************************************************/
[6568, 5966],

/***/ 6127:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-util/lib/Dom/addEventListener.js ***!
  \********************************************************************************/
[6571, 6128, 5268],

/***/ 6128:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/add-dom-event-listener/lib/index.js ***!
  \********************************************************************************/
[6572, 6129],

/***/ 6129:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/add-dom-event-listener/lib/EventObject.js ***!
  \**************************************************************************************/
[6573, 6130, 5240],

/***/ 6130:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/add-dom-event-listener/lib/EventBaseObject.js ***!
  \******************************************************************************************/
913,

/***/ 6131:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/Track.js ***!
  \*******************************************************************/
[6574, 5238],

/***/ 6132:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/Handle.js ***!
  \********************************************************************/
[6575, 5893, 5894, 5930, 5238, 6133],

/***/ 6133:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-tooltip/lib/index.js ***!
  \********************************************************************/
[6576, 6134],

/***/ 6134:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-tooltip/lib/Tooltip.js ***!
  \**********************************************************************/
[6577, 5238, 6135, 6136],

/***/ 6135:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-tooltip/lib/placements.js ***!
  \*************************************************************************/
918,

/***/ 6136:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/index.js ***!
  \********************************************************************/
[6578, 6137],

/***/ 6137:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/Trigger.js ***!
  \**********************************************************************/
[6579, 5855, 5238, 5268, 6138, 6139, 6140, 6165, 6166],

/***/ 6138:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/~/rc-util/lib/Dom/contains.js ***!
  \*************************************************************************************/
921,

/***/ 6139:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/~/rc-util/lib/Dom/addEventListener.js ***!
  \*********************************************************************************************/
[6571, 6128, 5268],

/***/ 6140:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/Popup.js ***!
  \********************************************************************/
[6580, 5855, 5238, 5268, 6141, 6154, 6163, 6164],

/***/ 6141:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-align/lib/index.js ***!
  \******************************************************************/
[6581, 6142],

/***/ 6142:
/*!******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-align/lib/Align.js ***!
  \******************************************************************/
[6582, 5238, 5268, 6143, 6152, 6153],

/***/ 6143:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/index.js ***!
  \*******************************************************************/
[6583, 6144, 6146, 6147, 6148, 6149, 6150],

/***/ 6144:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/utils.js ***!
  \*******************************************************************/
[6584, 6145],

/***/ 6145:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/propertyUtils.js ***!
  \***************************************************************************/
928,

/***/ 6146:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/getOffsetParent.js ***!
  \*****************************************************************************/
[6585, 6144],

/***/ 6147:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/getVisibleRectForElement.js ***!
  \**************************************************************************************/
[6586, 6144, 6146],

/***/ 6148:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/adjustForViewport.js ***!
  \*******************************************************************************/
[6587, 6144],

/***/ 6149:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/getRegion.js ***!
  \***********************************************************************/
[6588, 6144],

/***/ 6150:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/getElFuturePos.js ***!
  \****************************************************************************/
[6589, 6151],

/***/ 6151:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/dom-align/lib/getAlignOffset.js ***!
  \****************************************************************************/
934,

/***/ 6152:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-align/~/rc-util/lib/Dom/addEventListener.js ***!
  \*******************************************************************************************/
[6571, 6128, 5268],

/***/ 6153:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-align/lib/isWindow.js ***!
  \*********************************************************************/
936,

/***/ 6154:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-animate/lib/index.js ***!
  \********************************************************************/
[6590, 6155],

/***/ 6155:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-animate/lib/Animate.js ***!
  \**********************************************************************/
[6591, 5238, 6156, 6157, 6162],

/***/ 6156:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-animate/lib/ChildrenUtils.js ***!
  \****************************************************************************/
[6592, 5238],

/***/ 6157:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-animate/lib/AnimateChild.js ***!
  \***************************************************************************/
[6593, 5238, 5268, 6158, 6162],

/***/ 6158:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/css-animation/lib/index.js ***!
  \***********************************************************************/
[6594, 6159, 6160],

/***/ 6159:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/css-animation/lib/Event.js ***!
  \***********************************************************************/
942,

/***/ 6160:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/component-classes/index.js ***!
  \***********************************************************************/
[6595, 6161, 6161],

/***/ 6161:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/component-indexof/index.js ***!
  \***********************************************************************/
944,

/***/ 6162:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-animate/lib/util.js ***!
  \*******************************************************************/
945,

/***/ 6163:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/PopupInner.js ***!
  \*************************************************************************/
[6596, 5238, 6164],

/***/ 6164:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/LazyRenderBox.js ***!
  \****************************************************************************/
[6597, 5854, 5238],

/***/ 6165:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/lib/utils.js ***!
  \********************************************************************/
[6598, 5855],

/***/ 6166:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-trigger/~/rc-util/lib/getContainerRenderMixin.js ***!
  \************************************************************************************************/
[6599, 5268],

/***/ 6167:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/Steps.js ***!
  \*******************************************************************/
[6600, 6122, 5238, 5938, 5952],

/***/ 6168:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/lib/Marks.js ***!
  \*******************************************************************/
[6601, 5855, 5895, 6122, 5238, 5938],

/***/ 6169:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/rc-slider/assets/index.css ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../~/css-loader!./index.css */ 6170);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../node_modules/css-loader/index.js!./index.css", function() {
				var newContent = require("!!../../../../../node_modules/css-loader/index.js!./index.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6170:
/*!**************************************************************************************!*\
  !*** ./~/css-loader!./atlas_bundles/heatmap-highcharts/~/rc-slider/assets/index.css ***!
  \**************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".rc-slider {\n  position: relative;\n  height: 4px;\n  width: 100%;\n  border-radius: 6px;\n  background-color: #e9e9e9;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider-track {\n  position: absolute;\n  left: 0;\n  height: 4px;\n  border-radius: 6px;\n  background-color: #abe2fb;\n}\n.rc-slider-handle {\n  position: absolute;\n  margin-left: -7px;\n  margin-top: -5px;\n  width: 14px;\n  height: 14px;\n  cursor: pointer;\n  border-radius: 50%;\n  border: solid 2px #96dbfa;\n  background-color: #fff;\n}\n.rc-slider-handle:hover {\n  border-color: #57c5f7;\n}\n.rc-slider-handle-active:active {\n  border-color: #57c5f7;\n  box-shadow: 0 0 5px #57c5f7;\n}\n.rc-slider-mark {\n  position: absolute;\n  top: 10px;\n  left: 0;\n  width: 100%;\n  font-size: 12px;\n}\n.rc-slider-mark-text {\n  position: absolute;\n  display: inline-block;\n  vertical-align: middle;\n  text-align: center;\n  cursor: pointer;\n  color: #999;\n}\n.rc-slider-mark-text-active {\n  color: #666;\n}\n.rc-slider-step {\n  position: absolute;\n  width: 100%;\n  height: 4px;\n  background: transparent;\n}\n.rc-slider-dot {\n  position: absolute;\n  bottom: -2px;\n  margin-left: -4px;\n  width: 8px;\n  height: 8px;\n  border: 2px solid #e9e9e9;\n  background-color: #fff;\n  cursor: pointer;\n  border-radius: 50%;\n  vertical-align: middle;\n}\n.rc-slider-dot:first-child {\n  margin-left: -4px;\n}\n.rc-slider-dot:last-child {\n  margin-left: -4px;\n}\n.rc-slider-dot-active {\n  border-color: #96dbfa;\n}\n.rc-slider-disabled {\n  background-color: #e9e9e9;\n}\n.rc-slider-disabled .rc-slider-track {\n  background-color: #ccc;\n}\n.rc-slider-disabled .rc-slider-handle,\n.rc-slider-disabled .rc-slider-dot {\n  border-color: #ccc;\n  background-color: #fff;\n  cursor: not-allowed;\n}\n.rc-slider-disabled .rc-slider-mark-text,\n.rc-slider-disabled .rc-slider-dot {\n  cursor: not-allowed !important;\n}\n.rc-slider-vertical {\n  width: 4px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-track {\n  bottom: 0;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-handle {\n  position: absolute;\n  margin-left: -5px;\n  margin-bottom: -7px;\n}\n.rc-slider-vertical .rc-slider-mark {\n  top: 0;\n  left: 10px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-step {\n  height: 100%;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-dot {\n  left: 2px;\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:first-child {\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:last-child {\n  margin-bottom: -4px;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-enter.rc-slider-tooltip-zoom-down-enter-active,\n.rc-slider-tooltip-zoom-down-appear.rc-slider-tooltip-zoom-down-appear-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownIn;\n          animation-name: rcSliderTooltipZoomDownIn;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-leave.rc-slider-tooltip-zoom-down-leave-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownOut;\n          animation-name: rcSliderTooltipZoomDownOut;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-transform: scale(0, 0);\n          transform: scale(0, 0);\n  -webkit-animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n          animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n          animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n}\n@-webkit-keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@-webkit-keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n@keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n.rc-tooltip {\n  position: absolute;\n  left: -9999px;\n  top: -9999px;\n  visibility: visible;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip-hidden {\n  display: none;\n}\n.rc-tooltip-placement-top {\n  padding: 4px 0 8px 0;\n}\n.rc-tooltip-inner {\n  padding: 6px 2px;\n  min-width: 24px;\n  height: 24px;\n  font-size: 12px;\n  line-height: 1;\n  color: #fff;\n  text-align: center;\n  text-decoration: none;\n  background-color: #6c6c6c;\n  border-radius: 6px;\n  box-shadow: 0 0 4px #d9d9d9;\n}\n.rc-tooltip-arrow {\n  position: absolute;\n  width: 0;\n  height: 0;\n  border-color: transparent;\n  border-style: solid;\n}\n.rc-tooltip-placement-top .rc-tooltip-arrow {\n  bottom: 4px;\n  left: 50%;\n  margin-left: -4px;\n  border-width: 4px 4px 0;\n  border-top-color: #6c6c6c;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6171:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.less ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../../../../../../~/css-loader!../../../../../../../~/less-loader!./CoexpressionOption.less */ 6172);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./CoexpressionOption.less", function() {
				var newContent = require("!!../../../../../../../node_modules/css-loader/index.js!../../../../../../../node_modules/less-loader/index.js!./CoexpressionOption.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6172:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.less ***!
  \*******************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDisplayCoexpressionOffer {\n  margin-top: 30px;\n}\n.gxaDisplayCoexpressionOffer .gxaSlider {\n  width: 250px;\n  margin: 15px;\n  padding-bottom: 20px;\n}\n.gxaDisplayCoexpressionOffer p {\n  font-size: 93%;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6173:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/Manipulators.js ***!
  \***************************************************************************************************************/
1561,

/***/ 6174:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/show/BoxplotCanvas.jsx ***!
  \***********************************************************************************************************/
[7054, 5238, 6065, 6175],

/***/ 6175:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/highcharts/highcharts-more.js ***!
  \**************************************************************************/
1563,

/***/ 6176:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/jsonPayloadPropTypes.js ***!
  \*******************************************************************************************************************/
[7055, 5238, 6042],

/***/ 6177:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/main.js ***!
  \*************************************************************************************************/
[7056, 6178, 6179, 6188, 6189, 6190, 6198],

/***/ 6178:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/chartConfiguration.js ***!
  \***************************************************************************************************************/
[7057, 6042, 6116],

/***/ 6179:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapData.js ***!
  \********************************************************************************************************/
[7058, 6180, 6182],

/***/ 6180:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapDataSeries.js ***!
  \**************************************************************************************************************/
[7059, 6181, 6042],

/***/ 6181:
/*!*************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/lodash/lodash.js ***!
  \*************************************************************/
838,

/***/ 6182:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapAxisCategories.js ***!
  \******************************************************************************************************************/
[7060, 179, 186, 6042, 6183],

/***/ 6183:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets ^\.\/.*\-icon\.png$ ***!
  \***********************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./gsea_go-icon.png": 6184,
		"./gsea_interpro-icon.png": 6185,
		"./gsea_reactome-icon.png": 6186,
		"./ma-plot-icon.png": 6187
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 6183;


/***/ },

/***/ 6184:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_go-icon.png ***!
  \********************************************************************************************************/
1571,

/***/ 6185:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_interpro-icon.png ***!
  \**************************************************************************************************************/
1572,

/***/ 6186:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_reactome-icon.png ***!
  \**************************************************************************************************************/
1573,

/***/ 6187:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/ma-plot-icon.png ***!
  \********************************************************************************************************/
1574,

/***/ 6188:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/boxplotData.js ***!
  \********************************************************************************************************/
[7061, 6042],

/***/ 6189:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapOrderings.js ***!
  \*************************************************************************************************************/
[7062, 6181, 6042],

/***/ 6190:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapColourAxis.js ***!
  \**************************************************************************************************************/
[7063, 6191, 6042],

/***/ 6191:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color/index.js ***!
  \***********************************************************/
[6555, 6192, 6193, 6197],

/***/ 6192:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/clone/clone.js ***!
  \***********************************************************/
821,

/***/ 6193:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color-convert/index.js ***!
  \*******************************************************************/
[6556, 6194, 6196],

/***/ 6194:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color-convert/conversions.js ***!
  \*************************************************************************/
[6557, 6195],

/***/ 6195:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color-name/index.js ***!
  \****************************************************************/
828,

/***/ 6196:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color-convert/route.js ***!
  \*******************************************************************/
[6558, 6194],

/***/ 6197:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/color-string/color-string.js ***!
  \*************************************************************************/
[6559, 6195],

/***/ 6198:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapFilters.js ***!
  \***********************************************************************************************************/
[7064, 6181]

});
//# sourceMappingURL=expressionAtlasHeatmapHighcharts.bundle.js.map