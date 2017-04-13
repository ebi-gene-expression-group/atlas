var expressionAtlasHeatmapHighcharts =
webpackJsonp_name_([6],{

/***/ 0:
/*!**********************************************!*\
  !*** multi expressionAtlasHeatmapHighcharts ***!
  \**********************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */524);
	__webpack_require__(/*! whatwg-fetch */1434);
	module.exports = __webpack_require__(/*! ./atlas_bundles/heatmap-highcharts */5961);


/***/ },

/***/ 5961:
/*!***************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/index.js ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.render = undefined;
	
	var _expressionAtlasHeatmapHighcharts = __webpack_require__(/*! expression-atlas-heatmap-highcharts */ 5962);
	
	exports.render = _expressionAtlasHeatmapHighcharts.render; //module.exports = require(`expression-atlas-heatmap-highcharts`);

/***/ },

/***/ 5962:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/Main.jsx ***!
  \*********************************************************************************************/
[7726, 5963, 5993, 6144, 6148],

/***/ 5963:
/*!***********************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/react.js ***!
  \***********************************************************/
[7066, 5964],

/***/ 5964:
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
	
	var _assign = __webpack_require__(/*! object-assign */ 5965);
	
	var ReactChildren = __webpack_require__(/*! ./ReactChildren */ 5966);
	var ReactComponent = __webpack_require__(/*! ./ReactComponent */ 5979);
	var ReactPureComponent = __webpack_require__(/*! ./ReactPureComponent */ 5982);
	var ReactClass = __webpack_require__(/*! ./ReactClass */ 5983);
	var ReactDOMFactories = __webpack_require__(/*! ./ReactDOMFactories */ 5985);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5970);
	var ReactPropTypes = __webpack_require__(/*! ./ReactPropTypes */ 5990);
	var ReactVersion = __webpack_require__(/*! ./ReactVersion */ 5991);
	
	var onlyChild = __webpack_require__(/*! ./onlyChild */ 5992);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5972);
	
	var createElement = ReactElement.createElement;
	var createFactory = ReactElement.createFactory;
	var cloneElement = ReactElement.cloneElement;
	
	if (true) {
	  var ReactElementValidator = __webpack_require__(/*! ./ReactElementValidator */ 5986);
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

/***/ 5965:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/~/object-assign/index.js ***!
  \***************************************************************************/
5,

/***/ 5966:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactChildren.js ***!
  \***********************************************************************/
[7728, 5967, 5970, 5973, 5976],

/***/ 5967:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/PooledClass.js ***!
  \*********************************************************************/
[7729, 5968, 5969],

/***/ 5968:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/reactProdInvariant.js ***!
  \****************************************************************************/
1441,

/***/ 5969:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/~/fbjs/lib/invariant.js ***!
  \**************************************************************************/
9,

/***/ 5970:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactElement.js ***!
  \**********************************************************************/
[7730, 5965, 5971, 5972, 5974, 5975],

/***/ 5971:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactCurrentOwner.js ***!
  \***************************************************************************/
1444,

/***/ 5972:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/~/fbjs/lib/warning.js ***!
  \************************************************************************/
[7071, 5973],

/***/ 5973:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/~/fbjs/lib/emptyFunction.js ***!
  \******************************************************************************/
13,

/***/ 5974:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/canDefineProperty.js ***!
  \***************************************************************************/
1447,

/***/ 5975:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactElementSymbol.js ***!
  \****************************************************************************/
1448,

/***/ 5976:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/traverseAllChildren.js ***!
  \*****************************************************************************/
[7731, 5968, 5971, 5975, 5977, 5969, 5978, 5972],

/***/ 5977:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/getIteratorFn.js ***!
  \***********************************************************************/
1450,

/***/ 5978:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/KeyEscapeUtils.js ***!
  \************************************************************************/
1451,

/***/ 5979:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactComponent.js ***!
  \************************************************************************/
[7732, 5968, 5980, 5974, 5981, 5969, 5972],

/***/ 5980:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactNoopUpdateQueue.js ***!
  \******************************************************************************/
[7733, 5972],

/***/ 5981:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/~/fbjs/lib/emptyObject.js ***!
  \****************************************************************************/
20,

/***/ 5982:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPureComponent.js ***!
  \****************************************************************************/
[7734, 5965, 5979, 5980, 5981],

/***/ 5983:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5968),
	    _assign = __webpack_require__(/*! object-assign */ 5965);
	
	var ReactComponent = __webpack_require__(/*! ./ReactComponent */ 5979);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5970);
	var ReactPropTypeLocationNames = __webpack_require__(/*! ./ReactPropTypeLocationNames */ 5984);
	var ReactNoopUpdateQueue = __webpack_require__(/*! ./ReactNoopUpdateQueue */ 5980);
	
	var emptyObject = __webpack_require__(/*! fbjs/lib/emptyObject */ 5981);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5969);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5972);
	
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

/***/ 5984:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPropTypeLocationNames.js ***!
  \************************************************************************************/
1457,

/***/ 5985:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactDOMFactories.js ***!
  \***************************************************************************/
[7736, 5970, 5986],

/***/ 5986:
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
	
	var ReactCurrentOwner = __webpack_require__(/*! ./ReactCurrentOwner */ 5971);
	var ReactComponentTreeHook = __webpack_require__(/*! ./ReactComponentTreeHook */ 5987);
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5970);
	
	var checkReactTypeSpec = __webpack_require__(/*! ./checkReactTypeSpec */ 5988);
	
	var canDefineProperty = __webpack_require__(/*! ./canDefineProperty */ 5974);
	var getIteratorFn = __webpack_require__(/*! ./getIteratorFn */ 5977);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5972);
	
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

/***/ 5987:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactComponentTreeHook.js ***!
  \********************************************************************************/
[7738, 5968, 5971, 5969, 5972],

/***/ 5988:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/checkReactTypeSpec.js ***!
  \****************************************************************************/
[7739, 5968, 5984, 5989, 5969, 5972, 5987, 5987],

/***/ 5989:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/ReactPropTypesSecret.js ***!
  \******************************************************************************/
1462,

/***/ 5990:
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
	
	var ReactElement = __webpack_require__(/*! ./ReactElement */ 5970);
	var ReactPropTypeLocationNames = __webpack_require__(/*! ./ReactPropTypeLocationNames */ 5984);
	var ReactPropTypesSecret = __webpack_require__(/*! ./ReactPropTypesSecret */ 5989);
	
	var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 5973);
	var getIteratorFn = __webpack_require__(/*! ./getIteratorFn */ 5977);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 5972);
	
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

/***/ 5991:
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

/***/ 5992:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react/lib/onlyChild.js ***!
  \*******************************************************************/
[7744, 5968, 5970, 5969],

/***/ 5993:
/*!***************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/index.js ***!
  \***************************************************************/
[7745, 5994],

/***/ 5994:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOM.js ***!
  \**********************************************************************/
[7746, 5995, 6000, 6132, 6024, 6021, 6137, 6138, 6139, 6140, 6008, 6012, 6027, 6141, 6142, 6143],

/***/ 5995:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponentTree.js ***!
  \***********************************************************************************/
[7747, 5996, 5997, 5999, 5998],

/***/ 5996:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/reactProdInvariant.js ***!
  \********************************************************************************/
1441,

/***/ 5997:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMProperty.js ***!
  \*************************************************************************/
[7748, 5996, 5998],

/***/ 5998:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/invariant.js ***!
  \******************************************************************************/
9,

/***/ 5999:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponentFlags.js ***!
  \************************************************************************************/
1476,

/***/ 6000:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDefaultInjection.js ***!
  \***********************************************************************************/
[7749, 6001, 6002, 6020, 6037, 6038, 6043, 6044, 6057, 5995, 6103, 6104, 6105, 6106, 6107, 6110, 6111, 6119, 6120, 6121],

/***/ 6001:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ARIADOMPropertyConfig.js ***!
  \***********************************************************************************/
1478,

/***/ 6002:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/BeforeInputEventPlugin.js ***!
  \************************************************************************************/
[7750, 6003, 6012, 6013, 6017, 6019],

/***/ 6003:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPropagators.js ***!
  \******************************************************************************/
[7751, 6004, 6006, 6010, 6011, 6008],

/***/ 6004:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginHub.js ***!
  \****************************************************************************/
[7752, 5996, 6005, 6006, 6007, 6010, 6011, 5998],

/***/ 6005:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginRegistry.js ***!
  \*********************************************************************************/
[7753, 5996, 5998],

/***/ 6006:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EventPluginUtils.js ***!
  \******************************************************************************/
[7754, 5996, 6007, 5998, 6008],

/***/ 6007:
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

/***/ 6008:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/warning.js ***!
  \****************************************************************************/
[7071, 6009],

/***/ 6009:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/emptyFunction.js ***!
  \**********************************************************************************/
13,

/***/ 6010:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/accumulateInto.js ***!
  \****************************************************************************/
[7755, 5996, 5998],

/***/ 6011:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/forEachAccumulated.js ***!
  \********************************************************************************/
1488,

/***/ 6012:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/ExecutionEnvironment.js ***!
  \*****************************************************************************************/
53,

/***/ 6013:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/FallbackCompositionState.js ***!
  \**************************************************************************************/
[7756, 6014, 6015, 6016],

/***/ 6014:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/object-assign/index.js ***!
  \*******************************************************************************/
5,

/***/ 6015:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/PooledClass.js ***!
  \*************************************************************************/
[7729, 5996, 5998],

/***/ 6016:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getTextContentAccessor.js ***!
  \************************************************************************************/
[7757, 6012],

/***/ 6017:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticCompositionEvent.js ***!
  \***************************************************************************************/
[7758, 6018],

/***/ 6018:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticEvent.js ***!
  \****************************************************************************/
[7759, 6014, 6015, 6009, 6008],

/***/ 6019:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticInputEvent.js ***!
  \*********************************************************************************/
[7760, 6018],

/***/ 6020:
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
	
	var EventPluginHub = __webpack_require__(/*! ./EventPluginHub */ 6004);
	var EventPropagators = __webpack_require__(/*! ./EventPropagators */ 6003);
	var ExecutionEnvironment = __webpack_require__(/*! fbjs/lib/ExecutionEnvironment */ 6012);
	var ReactDOMComponentTree = __webpack_require__(/*! ./ReactDOMComponentTree */ 5995);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 6021);
	var SyntheticEvent = __webpack_require__(/*! ./SyntheticEvent */ 6018);
	
	var getEventTarget = __webpack_require__(/*! ./getEventTarget */ 6034);
	var isEventSupported = __webpack_require__(/*! ./isEventSupported */ 6035);
	var isTextInputElement = __webpack_require__(/*! ./isTextInputElement */ 6036);
	
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

/***/ 6021:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactUpdates.js ***!
  \**************************************************************************/
[7762, 5996, 6014, 6022, 6015, 6023, 6024, 6033, 5998],

/***/ 6022:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CallbackQueue.js ***!
  \***************************************************************************/
[7763, 5996, 6015, 5998],

/***/ 6023:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactFeatureFlags.js ***!
  \*******************************************************************************/
1500,

/***/ 6024:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactReconciler.js ***!
  \*****************************************************************************/
[7764, 6025, 6027, 6008],

/***/ 6025:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactRef.js ***!
  \**********************************************************************/
[7765, 6026],

/***/ 6026:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactOwner.js ***!
  \************************************************************************/
[7766, 5996, 5998],

/***/ 6027:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInstrumentation.js ***!
  \**********************************************************************************/
[7767, 6028],

/***/ 6028:
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
	
	var ReactInvalidSetStateWarningHook = __webpack_require__(/*! ./ReactInvalidSetStateWarningHook */ 6029);
	var ReactHostOperationHistoryHook = __webpack_require__(/*! ./ReactHostOperationHistoryHook */ 6030);
	var ReactComponentTreeHook = __webpack_require__(/*! react/lib/ReactComponentTreeHook */ 5987);
	var ExecutionEnvironment = __webpack_require__(/*! fbjs/lib/ExecutionEnvironment */ 6012);
	
	var performanceNow = __webpack_require__(/*! fbjs/lib/performanceNow */ 6031);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6029:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInvalidSetStateWarningHook.js ***!
  \*********************************************************************************************/
[7769, 6008],

/***/ 6030:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactHostOperationHistoryHook.js ***!
  \*******************************************************************************************/
1507,

/***/ 6031:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/performanceNow.js ***!
  \***********************************************************************************/
[7114, 6032],

/***/ 6032:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/performance.js ***!
  \********************************************************************************/
[7115, 6012],

/***/ 6033:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/Transaction.js ***!
  \*************************************************************************/
[7770, 5996, 5998],

/***/ 6034:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventTarget.js ***!
  \****************************************************************************/
1511,

/***/ 6035:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/isEventSupported.js ***!
  \******************************************************************************/
[7771, 6012],

/***/ 6036:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/isTextInputElement.js ***!
  \********************************************************************************/
1513,

/***/ 6037:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DefaultEventPluginOrder.js ***!
  \*************************************************************************************/
1514,

/***/ 6038:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/EnterLeaveEventPlugin.js ***!
  \***********************************************************************************/
[7772, 6003, 5995, 6039],

/***/ 6039:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticMouseEvent.js ***!
  \*********************************************************************************/
[7773, 6040, 6041, 6042],

/***/ 6040:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticUIEvent.js ***!
  \******************************************************************************/
[7774, 6018, 6034],

/***/ 6041:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ViewportMetrics.js ***!
  \*****************************************************************************/
1518,

/***/ 6042:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventModifierState.js ***!
  \***********************************************************************************/
1519,

/***/ 6043:
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
	
	var DOMProperty = __webpack_require__(/*! ./DOMProperty */ 5997);
	
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

/***/ 6044:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactComponentBrowserEnvironment.js ***!
  \**********************************************************************************************/
[7776, 6045, 6056],

/***/ 6045:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMChildrenOperations.js ***!
  \***********************************************************************************/
[7777, 6046, 6052, 5995, 6027, 6049, 6048, 6050],

/***/ 6046:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMLazyTree.js ***!
  \*************************************************************************/
[7778, 6047, 6048, 6049, 6050],

/***/ 6047:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMNamespaces.js ***!
  \***************************************************************************/
1524,

/***/ 6048:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/setInnerHTML.js ***!
  \**************************************************************************/
[7779, 6012, 6047, 6049],

/***/ 6049:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \************************************************************************************************/
1526,

/***/ 6050:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/setTextContent.js ***!
  \****************************************************************************/
[7780, 6012, 6051, 6048],

/***/ 6051:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/escapeTextContentForBrowser.js ***!
  \*****************************************************************************************/
1528,

/***/ 6052:
/*!********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/Danger.js ***!
  \********************************************************************/
[7781, 5996, 6046, 6012, 6053, 6009, 5998],

/***/ 6053:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/createNodesFromMarkup.js ***!
  \******************************************************************************************/
[7129, 6012, 6054, 6055, 5998],

/***/ 6054:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/createArrayFromMixed.js ***!
  \*****************************************************************************************/
[7130, 5998],

/***/ 6055:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/getMarkupWrap.js ***!
  \**********************************************************************************/
[7131, 6012, 5998],

/***/ 6056:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMIDOperations.js ***!
  \**********************************************************************************/
[7782, 6045, 5995],

/***/ 6057:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMComponent.js ***!
  \*******************************************************************************/
[7783, 5996, 6014, 6058, 6060, 6046, 6047, 5997, 6068, 6004, 6005, 6070, 5999, 5995, 6073, 6076, 6077, 6078, 6027, 6079, 6099, 6009, 6051, 5998, 6035, 6089, 6102, 6008],

/***/ 6058:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/AutoFocusUtils.js ***!
  \****************************************************************************/
[7784, 5995, 6059],

/***/ 6059:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/focusNode.js ***!
  \******************************************************************************/
100,

/***/ 6060:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CSSPropertyOperations.js ***!
  \***********************************************************************************/
[7785, 6061, 6012, 6027, 6062, 6064, 6065, 6067, 6008],

/***/ 6061:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/CSSProperty.js ***!
  \*************************************************************************/
1538,

/***/ 6062:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/camelizeStyleName.js ***!
  \**************************************************************************************/
[7137, 6063],

/***/ 6063:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/camelize.js ***!
  \*****************************************************************************/
104,

/***/ 6064:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/dangerousStyleValue.js ***!
  \*********************************************************************************/
[7786, 6061, 6008],

/***/ 6065:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/hyphenateStyleName.js ***!
  \***************************************************************************************/
[7139, 6066],

/***/ 6066:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/hyphenate.js ***!
  \******************************************************************************/
107,

/***/ 6067:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/memoizeStringOnly.js ***!
  \**************************************************************************************/
108,

/***/ 6068:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/DOMPropertyOperations.js ***!
  \***********************************************************************************/
[7787, 5997, 5995, 6027, 6069, 6008],

/***/ 6069:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/quoteAttributeValueForBrowser.js ***!
  \*******************************************************************************************/
[7788, 6051],

/***/ 6070:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactBrowserEventEmitter.js ***!
  \**************************************************************************************/
[7789, 6014, 6005, 6071, 6041, 6072, 6035],

/***/ 6071:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEventEmitterMixin.js ***!
  \************************************************************************************/
[7790, 6004],

/***/ 6072:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getVendorPrefixedEventName.js ***!
  \****************************************************************************************/
[7791, 6012],

/***/ 6073:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5996),
	    _assign = __webpack_require__(/*! object-assign */ 6014);
	
	var DOMPropertyOperations = __webpack_require__(/*! ./DOMPropertyOperations */ 6068);
	var LinkedValueUtils = __webpack_require__(/*! ./LinkedValueUtils */ 6074);
	var ReactDOMComponentTree = __webpack_require__(/*! ./ReactDOMComponentTree */ 5995);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 6021);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5998);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6074:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5996);
	
	var React = __webpack_require__(/*! react/lib/React */ 5964);
	var ReactPropTypesSecret = __webpack_require__(/*! ./ReactPropTypesSecret */ 6075);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5998);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6075:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactPropTypesSecret.js ***!
  \**********************************************************************************/
1462,

/***/ 6076:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMOption.js ***!
  \****************************************************************************/
[7794, 6014, 5964, 5995, 6077, 6008],

/***/ 6077:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMSelect.js ***!
  \****************************************************************************/
[7795, 6014, 6074, 5995, 6021, 6008],

/***/ 6078:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTextarea.js ***!
  \******************************************************************************/
[7796, 5996, 6014, 6074, 5995, 6021, 5998, 6008],

/***/ 6079:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMultiChild.js ***!
  \*****************************************************************************/
[7797, 5996, 6080, 6081, 6027, 5971, 6024, 6082, 6009, 6098, 5998],

/***/ 6080:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactComponentEnvironment.js ***!
  \***************************************************************************************/
[7798, 5996, 5998],

/***/ 6081:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInstanceMap.js ***!
  \******************************************************************************/
1562,

/***/ 6082:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactChildReconciler.js ***!
  \**********************************************************************************/
[7799, 6024, 6083, 6094, 6090, 6095, 6008, 5987, 5987],

/***/ 6083:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5996),
	    _assign = __webpack_require__(/*! object-assign */ 6014);
	
	var ReactCompositeComponent = __webpack_require__(/*! ./ReactCompositeComponent */ 6084);
	var ReactEmptyComponent = __webpack_require__(/*! ./ReactEmptyComponent */ 6091);
	var ReactHostComponent = __webpack_require__(/*! ./ReactHostComponent */ 6092);
	
	var getNextDebugID = __webpack_require__(/*! ./getNextDebugID */ 6093);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5998);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6084:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5996),
	    _assign = __webpack_require__(/*! object-assign */ 6014);
	
	var React = __webpack_require__(/*! react/lib/React */ 5964);
	var ReactComponentEnvironment = __webpack_require__(/*! ./ReactComponentEnvironment */ 6080);
	var ReactCurrentOwner = __webpack_require__(/*! react/lib/ReactCurrentOwner */ 5971);
	var ReactErrorUtils = __webpack_require__(/*! ./ReactErrorUtils */ 6007);
	var ReactInstanceMap = __webpack_require__(/*! ./ReactInstanceMap */ 6081);
	var ReactInstrumentation = __webpack_require__(/*! ./ReactInstrumentation */ 6027);
	var ReactNodeTypes = __webpack_require__(/*! ./ReactNodeTypes */ 6085);
	var ReactReconciler = __webpack_require__(/*! ./ReactReconciler */ 6024);
	
	if (true) {
	  var checkReactTypeSpec = __webpack_require__(/*! ./checkReactTypeSpec */ 6086);
	}
	
	var emptyObject = __webpack_require__(/*! fbjs/lib/emptyObject */ 6088);
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5998);
	var shallowEqual = __webpack_require__(/*! fbjs/lib/shallowEqual */ 6089);
	var shouldUpdateReactComponent = __webpack_require__(/*! ./shouldUpdateReactComponent */ 6090);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6085:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactNodeTypes.js ***!
  \****************************************************************************/
[7802, 5996, 5964, 5998],

/***/ 6086:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/checkReactTypeSpec.js ***!
  \********************************************************************************/
[7803, 5996, 6087, 6075, 5998, 6008, 5987, 5987],

/***/ 6087:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactPropTypeLocationNames.js ***!
  \****************************************************************************************/
1457,

/***/ 6088:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/emptyObject.js ***!
  \********************************************************************************/
20,

/***/ 6089:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/shallowEqual.js ***!
  \*********************************************************************************/
128,

/***/ 6090:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/shouldUpdateReactComponent.js ***!
  \****************************************************************************************/
1571,

/***/ 6091:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEmptyComponent.js ***!
  \*********************************************************************************/
1572,

/***/ 6092:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactHostComponent.js ***!
  \********************************************************************************/
[7804, 5996, 5998],

/***/ 6093:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getNextDebugID.js ***!
  \****************************************************************************/
1574,

/***/ 6094:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/KeyEscapeUtils.js ***!
  \****************************************************************************/
1451,

/***/ 6095:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/traverseAllChildren.js ***!
  \*********************************************************************************/
[7805, 5996, 5971, 6096, 6097, 5998, 6094, 6008],

/***/ 6096:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactElementSymbol.js ***!
  \********************************************************************************/
1448,

/***/ 6097:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getIteratorFn.js ***!
  \***************************************************************************/
1450,

/***/ 6098:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/flattenChildren.js ***!
  \*****************************************************************************/
[7806, 6094, 6095, 6008, 5987, 5987],

/***/ 6099:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerRenderingTransaction.js ***!
  \*********************************************************************************************/
[7807, 6014, 6015, 6033, 6027, 6100],

/***/ 6100:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerUpdateQueue.js ***!
  \************************************************************************************/
[7808, 6101, 6008],

/***/ 6101:
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
	
	var _prodInvariant = __webpack_require__(/*! ./reactProdInvariant */ 5996);
	
	var ReactCurrentOwner = __webpack_require__(/*! react/lib/ReactCurrentOwner */ 5971);
	var ReactInstanceMap = __webpack_require__(/*! ./ReactInstanceMap */ 6081);
	var ReactInstrumentation = __webpack_require__(/*! ./ReactInstrumentation */ 6027);
	var ReactUpdates = __webpack_require__(/*! ./ReactUpdates */ 6021);
	
	var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 5998);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6102:
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
	
	var _assign = __webpack_require__(/*! object-assign */ 6014);
	
	var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 6009);
	var warning = __webpack_require__(/*! fbjs/lib/warning */ 6008);
	
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

/***/ 6103:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMEmptyComponent.js ***!
  \************************************************************************************/
[7811, 6014, 6046, 5995],

/***/ 6104:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTreeTraversal.js ***!
  \***********************************************************************************/
[7812, 5996, 5998],

/***/ 6105:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMTextComponent.js ***!
  \***********************************************************************************/
[7813, 5996, 6014, 6045, 6046, 5995, 6051, 5998, 6102],

/***/ 6106:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDefaultBatchingStrategy.js ***!
  \******************************************************************************************/
[7814, 6014, 6021, 6033, 6009],

/***/ 6107:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactEventListener.js ***!
  \********************************************************************************/
[7815, 6014, 6108, 6012, 6015, 5995, 6021, 6034, 6109],

/***/ 6108:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/EventListener.js ***!
  \**********************************************************************************/
[7168, 6009],

/***/ 6109:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \***********************************************************************************************/
143,

/***/ 6110:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInjection.js ***!
  \****************************************************************************/
[7816, 5997, 6004, 6006, 6080, 6091, 6070, 6092, 6021],

/***/ 6111:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactReconcileTransaction.js ***!
  \***************************************************************************************/
[7817, 6014, 6022, 6015, 6070, 6112, 6027, 6033, 6101],

/***/ 6112:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactInputSelection.js ***!
  \*********************************************************************************/
[7818, 6113, 6115, 6059, 6118],

/***/ 6113:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMSelection.js ***!
  \*******************************************************************************/
[7819, 6012, 6114, 6016],

/***/ 6114:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getNodeForCharacterOffset.js ***!
  \***************************************************************************************/
1595,

/***/ 6115:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/containsNode.js ***!
  \*********************************************************************************/
[7173, 6116],

/***/ 6116:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/isTextNode.js ***!
  \*******************************************************************************/
[7174, 6117],

/***/ 6117:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/isNode.js ***!
  \***************************************************************************/
151,

/***/ 6118:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/~/fbjs/lib/getActiveElement.js ***!
  \*************************************************************************************/
152,

/***/ 6119:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SVGDOMPropertyConfig.js ***!
  \**********************************************************************************/
1600,

/***/ 6120:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SelectEventPlugin.js ***!
  \*******************************************************************************/
[7820, 6003, 6012, 5995, 6112, 6018, 6118, 6036, 6089],

/***/ 6121:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SimpleEventPlugin.js ***!
  \*******************************************************************************/
[7821, 5996, 6108, 6003, 5995, 6122, 6123, 6018, 6124, 6125, 6039, 6128, 6129, 6130, 6040, 6131, 6009, 6126, 5998],

/***/ 6122:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticAnimationEvent.js ***!
  \*************************************************************************************/
[7822, 6018],

/***/ 6123:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticClipboardEvent.js ***!
  \*************************************************************************************/
[7823, 6018],

/***/ 6124:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticFocusEvent.js ***!
  \*********************************************************************************/
[7824, 6040],

/***/ 6125:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticKeyboardEvent.js ***!
  \************************************************************************************/
[7825, 6040, 6126, 6127, 6042],

/***/ 6126:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventCharCode.js ***!
  \******************************************************************************/
1607,

/***/ 6127:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getEventKey.js ***!
  \*************************************************************************/
[7826, 6126],

/***/ 6128:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticDragEvent.js ***!
  \********************************************************************************/
[7827, 6039],

/***/ 6129:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticTouchEvent.js ***!
  \*********************************************************************************/
[7828, 6040, 6042],

/***/ 6130:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticTransitionEvent.js ***!
  \**************************************************************************************/
[7829, 6018],

/***/ 6131:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/SyntheticWheelEvent.js ***!
  \*********************************************************************************/
[7830, 6039],

/***/ 6132:
/*!************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMount.js ***!
  \************************************************************************/
[7831, 5996, 6046, 5997, 5964, 6070, 5971, 5995, 6133, 6134, 6023, 6081, 6027, 6135, 6024, 6101, 6021, 6088, 6083, 5998, 6048, 6090, 6008],

/***/ 6133:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMContainerInfo.js ***!
  \***********************************************************************************/
[7832, 6102],

/***/ 6134:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMFeatureFlags.js ***!
  \**********************************************************************************/
1615,

/***/ 6135:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactMarkupChecksum.js ***!
  \*********************************************************************************/
[7833, 6136],

/***/ 6136:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/adler32.js ***!
  \*********************************************************************/
1617,

/***/ 6137:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactVersion.js ***!
  \**************************************************************************/
5991,

/***/ 6138:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/findDOMNode.js ***!
  \*************************************************************************/
[7834, 5996, 5971, 5995, 6081, 6139, 5998, 6008],

/***/ 6139:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/getHostComponentFromComposite.js ***!
  \*******************************************************************************************/
[7835, 6085],

/***/ 6140:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/renderSubtreeIntoContainer.js ***!
  \****************************************************************************************/
[7836, 6132],

/***/ 6141:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMUnknownPropertyHook.js ***!
  \*****************************************************************************************/
[7837, 5997, 6005, 5987, 6008],

/***/ 6142:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMNullInputValuePropHook.js ***!
  \********************************************************************************************/
[7838, 5987, 6008],

/***/ 6143:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMInvalidARIAHook.js ***!
  \*************************************************************************************/
[7839, 5997, 5987, 6008],

/***/ 6144:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/urijs/src/URI.js ***!
  \***************************************************************************************************/
[7570, 6145, 6146, 6147, 6145, 6146, 6147],

/***/ 6145:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/urijs/src/punycode.js ***!
  \********************************************************************************************************/
1205,

/***/ 6146:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/urijs/src/IPv6.js ***!
  \****************************************************************************************************/
1206,

/***/ 6147:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/urijs/src/SecondLevelDomains.js ***!
  \******************************************************************************************************************/
1207,

/***/ 6148:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/ContainerLoader.jsx ***!
  \***************************************************************************************************************/
[7840, 5963, 6149, 6144, 6375],

/***/ 6149:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/index.js ***!
  \*************************************************************************************************************/
[7571, 6150, 6158],

/***/ 6150:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/components/connect.js ***!
  \**************************************************************************************************************************/
[7572, 5963, 6151, 6152, 6153, 6155, 6156, 6158, 6159, 6157, 6160, 6161],

/***/ 6151:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/isPlainObject.js ***!
  \***************************************************************************************************************************/
1210,

/***/ 6152:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/shallowEqual.js ***!
  \**************************************************************************************************************************/
1211,

/***/ 6153:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/handleResponse.js ***!
  \****************************************************************************************************************************/
[7573, 6154],

/***/ 6154:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/errors.js ***!
  \********************************************************************************************************************/
1213,

/***/ 6155:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/buildRequest.js ***!
  \**************************************************************************************************************************/
1214,

/***/ 6156:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/utils/checkTypes.js ***!
  \************************************************************************************************************************/
[7574, 6157, 6151],

/***/ 6157:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/~/invariant/browser.js ***!
  \***********************************************************************************************************************/
346,

/***/ 6158:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/lib/PromiseState.js ***!
  \********************************************************************************************************************/
1217,

/***/ 6159:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/~/hoist-non-react-statics/index.js ***!
  \***********************************************************************************************************************************/
1218,

/***/ 6160:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-refetch/~/warning/browser.js ***!
  \*********************************************************************************************************************/
352,

/***/ 6161:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/omit.js ***!
  \****************************************************************************************************/
[7575, 6162, 6368, 6165],

/***/ 6162:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/convert.js ***!
  \*******************************************************************************************************/
[7576, 6163, 6166],

/***/ 6163:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/_baseConvert.js ***!
  \************************************************************************************************************/
[7577, 6164, 6165],

/***/ 6164:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/_mapping.js ***!
  \********************************************************************************************************/
1223,

/***/ 6165:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/placeholder.js ***!
  \***********************************************************************************************************/
1224,

/***/ 6166:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/fp/_util.js ***!
  \*****************************************************************************************************/
[7578, 6167, 6236, 6258, 6325, 6220, 6206, 6175, 6326, 6253, 6361, 6232, 6367],

/***/ 6167:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/ary.js ***!
  \************************************************************************************************/
[7579, 6168],

/***/ 6168:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createWrap.js ***!
  \********************************************************************************************************/
[7580, 6169, 6187, 6190, 6192, 6230, 6200, 6231, 6210, 6212, 6232],

/***/ 6169:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseSetData.js ***!
  \*********************************************************************************************************/
[7581, 6170, 6171],

/***/ 6170:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/identity.js ***!
  \*****************************************************************************************************/
1229,

/***/ 6171:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_metaMap.js ***!
  \*****************************************************************************************************/
[7582, 6172],

/***/ 6172:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_WeakMap.js ***!
  \*****************************************************************************************************/
[7583, 6173, 6178],

/***/ 6173:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getNative.js ***!
  \*******************************************************************************************************/
[7584, 6174, 6186],

/***/ 6174:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsNative.js ***!
  \**********************************************************************************************************/
[7585, 6175, 6183, 6182, 6185],

/***/ 6175:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isFunction.js ***!
  \*******************************************************************************************************/
[7586, 6176, 6182],

/***/ 6176:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseGetTag.js ***!
  \********************************************************************************************************/
[7587, 6177, 6180, 6181],

/***/ 6177:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Symbol.js ***!
  \****************************************************************************************************/
[7588, 6178],

/***/ 6178:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_root.js ***!
  \**************************************************************************************************/
[7589, 6179],

/***/ 6179:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_freeGlobal.js ***!
  \********************************************************************************************************/
1238,

/***/ 6180:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getRawTag.js ***!
  \*******************************************************************************************************/
[7590, 6177],

/***/ 6181:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_objectToString.js ***!
  \************************************************************************************************************/
1240,

/***/ 6182:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isObject.js ***!
  \*****************************************************************************************************/
1241,

/***/ 6183:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isMasked.js ***!
  \******************************************************************************************************/
[7591, 6184],

/***/ 6184:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_coreJsData.js ***!
  \********************************************************************************************************/
[7592, 6178],

/***/ 6185:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_toSource.js ***!
  \******************************************************************************************************/
1244,

/***/ 6186:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getValue.js ***!
  \******************************************************************************************************/
1245,

/***/ 6187:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createBind.js ***!
  \********************************************************************************************************/
[7593, 6188, 6178],

/***/ 6188:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createCtor.js ***!
  \********************************************************************************************************/
[7594, 6189, 6182],

/***/ 6189:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseCreate.js ***!
  \********************************************************************************************************/
[7595, 6182],

/***/ 6190:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createCurry.js ***!
  \*********************************************************************************************************/
[7596, 6191, 6188, 6192, 6196, 6226, 6229, 6178],

/***/ 6191:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_apply.js ***!
  \***************************************************************************************************/
1250,

/***/ 6192:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createHybrid.js ***!
  \**********************************************************************************************************/
[7597, 6193, 6194, 6195, 6188, 6196, 6226, 6227, 6229, 6178],

/***/ 6193:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_composeArgs.js ***!
  \*********************************************************************************************************/
1252,

/***/ 6194:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_composeArgsRight.js ***!
  \**************************************************************************************************************/
1253,

/***/ 6195:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_countHolders.js ***!
  \**********************************************************************************************************/
1254,

/***/ 6196:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createRecurry.js ***!
  \***********************************************************************************************************/
[7598, 6197, 6210, 6212],

/***/ 6197:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isLaziable.js ***!
  \********************************************************************************************************/
[7599, 6198, 6200, 6202, 6204],

/***/ 6198:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_LazyWrapper.js ***!
  \*********************************************************************************************************/
[7600, 6189, 6199],

/***/ 6199:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseLodash.js ***!
  \********************************************************************************************************/
1258,

/***/ 6200:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getData.js ***!
  \*****************************************************************************************************/
[7601, 6171, 6201],

/***/ 6201:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/noop.js ***!
  \*************************************************************************************************/
1260,

/***/ 6202:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getFuncName.js ***!
  \*********************************************************************************************************/
[7602, 6203],

/***/ 6203:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_realNames.js ***!
  \*******************************************************************************************************/
1262,

/***/ 6204:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/wrapperLodash.js ***!
  \**********************************************************************************************************/
[7603, 6198, 6205, 6199, 6206, 6207, 6208],

/***/ 6205:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_LodashWrapper.js ***!
  \***********************************************************************************************************/
[7604, 6189, 6199],

/***/ 6206:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isArray.js ***!
  \****************************************************************************************************/
1265,

/***/ 6207:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isObjectLike.js ***!
  \*********************************************************************************************************/
1266,

/***/ 6208:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_wrapperClone.js ***!
  \**********************************************************************************************************/
[7605, 6198, 6205, 6209],

/***/ 6209:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_copyArray.js ***!
  \*******************************************************************************************************/
1268,

/***/ 6210:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setData.js ***!
  \*****************************************************************************************************/
[7606, 6169, 6211],

/***/ 6211:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_shortOut.js ***!
  \******************************************************************************************************/
1270,

/***/ 6212:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setWrapToString.js ***!
  \*************************************************************************************************************/
[7607, 6213, 6214, 6215, 6219],

/***/ 6213:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getWrapDetails.js ***!
  \************************************************************************************************************/
1272,

/***/ 6214:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_insertWrapDetails.js ***!
  \***************************************************************************************************************/
1273,

/***/ 6215:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setToString.js ***!
  \*********************************************************************************************************/
[7608, 6216, 6211],

/***/ 6216:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseSetToString.js ***!
  \*************************************************************************************************************/
[7609, 6217, 6218, 6170],

/***/ 6217:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/constant.js ***!
  \*****************************************************************************************************/
1276,

/***/ 6218:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_defineProperty.js ***!
  \************************************************************************************************************/
[7610, 6173],

/***/ 6219:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_updateWrapDetails.js ***!
  \***************************************************************************************************************/
[7611, 6220, 6221],

/***/ 6220:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayEach.js ***!
  \*******************************************************************************************************/
1279,

/***/ 6221:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayIncludes.js ***!
  \***********************************************************************************************************/
[7612, 6222],

/***/ 6222:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIndexOf.js ***!
  \*********************************************************************************************************/
[7613, 6223, 6224, 6225],

/***/ 6223:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseFindIndex.js ***!
  \***********************************************************************************************************/
1282,

/***/ 6224:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsNaN.js ***!
  \*******************************************************************************************************/
1283,

/***/ 6225:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_strictIndexOf.js ***!
  \***********************************************************************************************************/
1284,

/***/ 6226:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getHolder.js ***!
  \*******************************************************************************************************/
1285,

/***/ 6227:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_reorder.js ***!
  \*****************************************************************************************************/
[7614, 6209, 6228],

/***/ 6228:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isIndex.js ***!
  \*****************************************************************************************************/
1287,

/***/ 6229:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_replaceHolders.js ***!
  \************************************************************************************************************/
1288,

/***/ 6230:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createPartial.js ***!
  \***********************************************************************************************************/
[7615, 6191, 6188, 6178],

/***/ 6231:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mergeData.js ***!
  \*******************************************************************************************************/
[7616, 6193, 6194, 6229],

/***/ 6232:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/toInteger.js ***!
  \******************************************************************************************************/
[7617, 6233],

/***/ 6233:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/toFinite.js ***!
  \*****************************************************************************************************/
[7618, 6234],

/***/ 6234:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/toNumber.js ***!
  \*****************************************************************************************************/
[7619, 6182, 6235],

/***/ 6235:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isSymbol.js ***!
  \*****************************************************************************************************/
[7620, 6176, 6207],

/***/ 6236:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseAssign.js ***!
  \********************************************************************************************************/
[7621, 6237, 6241],

/***/ 6237:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_copyObject.js ***!
  \********************************************************************************************************/
[7622, 6238, 6239],

/***/ 6238:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_assignValue.js ***!
  \*********************************************************************************************************/
[7623, 6239, 6240],

/***/ 6239:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseAssignValue.js ***!
  \*************************************************************************************************************/
[7624, 6218],

/***/ 6240:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/eq.js ***!
  \***********************************************************************************************/
1299,

/***/ 6241:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/keys.js ***!
  \*************************************************************************************************/
[7625, 6242, 6253, 6257],

/***/ 6242:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayLikeKeys.js ***!
  \***********************************************************************************************************/
[7626, 6243, 6244, 6206, 6246, 6228, 6248],

/***/ 6243:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseTimes.js ***!
  \*******************************************************************************************************/
1302,

/***/ 6244:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isArguments.js ***!
  \********************************************************************************************************/
[7627, 6245, 6207],

/***/ 6245:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsArguments.js ***!
  \*************************************************************************************************************/
[7628, 6176, 6207],

/***/ 6246:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isBuffer.js ***!
  \*****************************************************************************************************/
[7629, 6178, 6247],

/***/ 6247:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/stubFalse.js ***!
  \******************************************************************************************************/
1306,

/***/ 6248:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isTypedArray.js ***!
  \*********************************************************************************************************/
[7630, 6249, 6251, 6252],

/***/ 6249:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsTypedArray.js ***!
  \**************************************************************************************************************/
[7631, 6176, 6250, 6207],

/***/ 6250:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isLength.js ***!
  \*****************************************************************************************************/
1309,

/***/ 6251:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseUnary.js ***!
  \*******************************************************************************************************/
1310,

/***/ 6252:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_nodeUtil.js ***!
  \******************************************************************************************************/
[7632, 6179],

/***/ 6253:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseKeys.js ***!
  \******************************************************************************************************/
[7633, 6254, 6255],

/***/ 6254:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isPrototype.js ***!
  \*********************************************************************************************************/
1313,

/***/ 6255:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_nativeKeys.js ***!
  \********************************************************************************************************/
[7634, 6256],

/***/ 6256:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_overArg.js ***!
  \*****************************************************************************************************/
1315,

/***/ 6257:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isArrayLike.js ***!
  \********************************************************************************************************/
[7635, 6175, 6250],

/***/ 6258:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/clone.js ***!
  \**************************************************************************************************/
[7636, 6259],

/***/ 6259:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseClone.js ***!
  \*******************************************************************************************************/
[7637, 6260, 6220, 6238, 6236, 6289, 6293, 6209, 6294, 6298, 6302, 6304, 6305, 6309, 6310, 6324, 6206, 6246, 6182, 6241],

/***/ 6260:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Stack.js ***!
  \***************************************************************************************************/
[7638, 6261, 6268, 6269, 6270, 6271, 6272],

/***/ 6261:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_ListCache.js ***!
  \*******************************************************************************************************/
[7639, 6262, 6263, 6265, 6266, 6267],

/***/ 6262:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_listCacheClear.js ***!
  \************************************************************************************************************/
1321,

/***/ 6263:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_listCacheDelete.js ***!
  \*************************************************************************************************************/
[7640, 6264],

/***/ 6264:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_assocIndexOf.js ***!
  \**********************************************************************************************************/
[7641, 6240],

/***/ 6265:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_listCacheGet.js ***!
  \**********************************************************************************************************/
[7642, 6264],

/***/ 6266:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_listCacheHas.js ***!
  \**********************************************************************************************************/
[7643, 6264],

/***/ 6267:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_listCacheSet.js ***!
  \**********************************************************************************************************/
[7644, 6264],

/***/ 6268:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stackClear.js ***!
  \********************************************************************************************************/
[7645, 6261],

/***/ 6269:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stackDelete.js ***!
  \*********************************************************************************************************/
1328,

/***/ 6270:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stackGet.js ***!
  \******************************************************************************************************/
1329,

/***/ 6271:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stackHas.js ***!
  \******************************************************************************************************/
1330,

/***/ 6272:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stackSet.js ***!
  \******************************************************************************************************/
[7646, 6261, 6273, 6274],

/***/ 6273:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Map.js ***!
  \*************************************************************************************************/
[7647, 6173, 6178],

/***/ 6274:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_MapCache.js ***!
  \******************************************************************************************************/
[7648, 6275, 6283, 6286, 6287, 6288],

/***/ 6275:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapCacheClear.js ***!
  \***********************************************************************************************************/
[7649, 6276, 6261, 6273],

/***/ 6276:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Hash.js ***!
  \**************************************************************************************************/
[7650, 6277, 6279, 6280, 6281, 6282],

/***/ 6277:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hashClear.js ***!
  \*******************************************************************************************************/
[7651, 6278],

/***/ 6278:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_nativeCreate.js ***!
  \**********************************************************************************************************/
[7652, 6173],

/***/ 6279:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hashDelete.js ***!
  \********************************************************************************************************/
1338,

/***/ 6280:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hashGet.js ***!
  \*****************************************************************************************************/
[7653, 6278],

/***/ 6281:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hashHas.js ***!
  \*****************************************************************************************************/
[7654, 6278],

/***/ 6282:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hashSet.js ***!
  \*****************************************************************************************************/
[7655, 6278],

/***/ 6283:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapCacheDelete.js ***!
  \************************************************************************************************************/
[7656, 6284],

/***/ 6284:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getMapData.js ***!
  \********************************************************************************************************/
[7657, 6285],

/***/ 6285:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isKeyable.js ***!
  \*******************************************************************************************************/
1344,

/***/ 6286:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapCacheGet.js ***!
  \*********************************************************************************************************/
[7658, 6284],

/***/ 6287:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapCacheHas.js ***!
  \*********************************************************************************************************/
[7659, 6284],

/***/ 6288:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapCacheSet.js ***!
  \*********************************************************************************************************/
[7660, 6284],

/***/ 6289:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseAssignIn.js ***!
  \**********************************************************************************************************/
[7661, 6237, 6290],

/***/ 6290:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/keysIn.js ***!
  \***************************************************************************************************/
[7662, 6242, 6291, 6257],

/***/ 6291:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseKeysIn.js ***!
  \********************************************************************************************************/
[7663, 6182, 6254, 6292],

/***/ 6292:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_nativeKeysIn.js ***!
  \**********************************************************************************************************/
1351,

/***/ 6293:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneBuffer.js ***!
  \*********************************************************************************************************/
[7664, 6178],

/***/ 6294:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_copySymbols.js ***!
  \*********************************************************************************************************/
[7665, 6237, 6295],

/***/ 6295:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getSymbols.js ***!
  \********************************************************************************************************/
[7666, 6296, 6297],

/***/ 6296:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayFilter.js ***!
  \*********************************************************************************************************/
1355,

/***/ 6297:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/stubArray.js ***!
  \******************************************************************************************************/
1356,

/***/ 6298:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_copySymbolsIn.js ***!
  \***********************************************************************************************************/
[7667, 6237, 6299],

/***/ 6299:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getSymbolsIn.js ***!
  \**********************************************************************************************************/
[7668, 6300, 6301, 6295, 6297],

/***/ 6300:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayPush.js ***!
  \*******************************************************************************************************/
1359,

/***/ 6301:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getPrototype.js ***!
  \**********************************************************************************************************/
[7669, 6256],

/***/ 6302:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getAllKeys.js ***!
  \********************************************************************************************************/
[7670, 6303, 6295, 6241],

/***/ 6303:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseGetAllKeys.js ***!
  \************************************************************************************************************/
[7671, 6300, 6206],

/***/ 6304:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getAllKeysIn.js ***!
  \**********************************************************************************************************/
[7672, 6303, 6299, 6290],

/***/ 6305:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getTag.js ***!
  \****************************************************************************************************/
[7673, 6306, 6273, 6307, 6308, 6172, 6176, 6185],

/***/ 6306:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_DataView.js ***!
  \******************************************************************************************************/
[7674, 6173, 6178],

/***/ 6307:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Promise.js ***!
  \*****************************************************************************************************/
[7675, 6173, 6178],

/***/ 6308:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Set.js ***!
  \*************************************************************************************************/
[7676, 6173, 6178],

/***/ 6309:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_initCloneArray.js ***!
  \************************************************************************************************************/
1368,

/***/ 6310:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_initCloneByTag.js ***!
  \************************************************************************************************************/
[7677, 6311, 6313, 6314, 6318, 6319, 6322, 6323],

/***/ 6311:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneArrayBuffer.js ***!
  \**************************************************************************************************************/
[7678, 6312],

/***/ 6312:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_Uint8Array.js ***!
  \********************************************************************************************************/
[7679, 6178],

/***/ 6313:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneDataView.js ***!
  \***********************************************************************************************************/
[7680, 6311],

/***/ 6314:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneMap.js ***!
  \******************************************************************************************************/
[7681, 6315, 6316, 6317],

/***/ 6315:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_addMapEntry.js ***!
  \*********************************************************************************************************/
1374,

/***/ 6316:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayReduce.js ***!
  \*********************************************************************************************************/
1375,

/***/ 6317:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_mapToArray.js ***!
  \********************************************************************************************************/
1376,

/***/ 6318:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneRegExp.js ***!
  \*********************************************************************************************************/
1377,

/***/ 6319:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneSet.js ***!
  \******************************************************************************************************/
[7682, 6320, 6316, 6321],

/***/ 6320:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_addSetEntry.js ***!
  \*********************************************************************************************************/
1379,

/***/ 6321:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setToArray.js ***!
  \********************************************************************************************************/
1380,

/***/ 6322:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneSymbol.js ***!
  \*********************************************************************************************************/
[7683, 6177],

/***/ 6323:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cloneTypedArray.js ***!
  \*************************************************************************************************************/
[7684, 6311],

/***/ 6324:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_initCloneObject.js ***!
  \*************************************************************************************************************/
[7685, 6189, 6301, 6254],

/***/ 6325:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/curry.js ***!
  \**************************************************************************************************/
[7686, 6168],

/***/ 6326:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/iteratee.js ***!
  \*****************************************************************************************************/
[7687, 6259, 6327],

/***/ 6327:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIteratee.js ***!
  \**********************************************************************************************************/
[7688, 6328, 6343, 6170, 6206, 6358],

/***/ 6328:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseMatches.js ***!
  \*********************************************************************************************************/
[7689, 6329, 6340, 6342],

/***/ 6329:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsMatch.js ***!
  \*********************************************************************************************************/
[7690, 6260, 6330],

/***/ 6330:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsEqual.js ***!
  \*********************************************************************************************************/
[7691, 6331, 6207],

/***/ 6331:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseIsEqualDeep.js ***!
  \*************************************************************************************************************/
[7692, 6260, 6332, 6338, 6339, 6305, 6206, 6246, 6248],

/***/ 6332:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_equalArrays.js ***!
  \*********************************************************************************************************/
[7693, 6333, 6336, 6337],

/***/ 6333:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_SetCache.js ***!
  \******************************************************************************************************/
[7694, 6274, 6334, 6335],

/***/ 6334:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setCacheAdd.js ***!
  \*********************************************************************************************************/
1393,

/***/ 6335:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_setCacheHas.js ***!
  \*********************************************************************************************************/
1394,

/***/ 6336:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arraySome.js ***!
  \*******************************************************************************************************/
1395,

/***/ 6337:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_cacheHas.js ***!
  \******************************************************************************************************/
1396,

/***/ 6338:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_equalByTag.js ***!
  \********************************************************************************************************/
[7695, 6177, 6312, 6240, 6332, 6317, 6321],

/***/ 6339:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_equalObjects.js ***!
  \**********************************************************************************************************/
[7696, 6302],

/***/ 6340:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_getMatchData.js ***!
  \**********************************************************************************************************/
[7697, 6341, 6241],

/***/ 6341:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isStrictComparable.js ***!
  \****************************************************************************************************************/
[7698, 6182],

/***/ 6342:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_matchesStrictComparable.js ***!
  \*********************************************************************************************************************/
1401,

/***/ 6343:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseMatchesProperty.js ***!
  \*****************************************************************************************************************/
[7699, 6330, 6344, 6355, 6347, 6341, 6342, 6354],

/***/ 6344:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/get.js ***!
  \************************************************************************************************/
[7700, 6345],

/***/ 6345:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseGet.js ***!
  \*****************************************************************************************************/
[7701, 6346, 6354],

/***/ 6346:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_castPath.js ***!
  \******************************************************************************************************/
[7702, 6206, 6347, 6348, 6351],

/***/ 6347:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isKey.js ***!
  \***************************************************************************************************/
[7703, 6206, 6235],

/***/ 6348:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_stringToPath.js ***!
  \**********************************************************************************************************/
[7704, 6349],

/***/ 6349:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_memoizeCapped.js ***!
  \***********************************************************************************************************/
[7705, 6350],

/***/ 6350:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/memoize.js ***!
  \****************************************************************************************************/
[7706, 6274],

/***/ 6351:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/toString.js ***!
  \*****************************************************************************************************/
[7707, 6352],

/***/ 6352:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseToString.js ***!
  \**********************************************************************************************************/
[7708, 6177, 6353, 6206, 6235],

/***/ 6353:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayMap.js ***!
  \******************************************************************************************************/
1412,

/***/ 6354:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_toKey.js ***!
  \***************************************************************************************************/
[7709, 6235],

/***/ 6355:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/hasIn.js ***!
  \**************************************************************************************************/
[7710, 6356, 6357],

/***/ 6356:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseHasIn.js ***!
  \*******************************************************************************************************/
1415,

/***/ 6357:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_hasPath.js ***!
  \*****************************************************************************************************/
[7711, 6346, 6244, 6206, 6228, 6250, 6354],

/***/ 6358:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/property.js ***!
  \*****************************************************************************************************/
[7712, 6359, 6360, 6347, 6354],

/***/ 6359:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseProperty.js ***!
  \**********************************************************************************************************/
1418,

/***/ 6360:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_basePropertyDeep.js ***!
  \**************************************************************************************************************/
[7713, 6345],

/***/ 6361:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/rearg.js ***!
  \**************************************************************************************************/
[7714, 6168, 6362],

/***/ 6362:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_flatRest.js ***!
  \******************************************************************************************************/
[7715, 6363, 6366, 6215],

/***/ 6363:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/flatten.js ***!
  \****************************************************************************************************/
[7716, 6364],

/***/ 6364:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseFlatten.js ***!
  \*********************************************************************************************************/
[7717, 6300, 6365],

/***/ 6365:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isFlattenable.js ***!
  \***********************************************************************************************************/
[7718, 6177, 6244, 6206],

/***/ 6366:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_overRest.js ***!
  \******************************************************************************************************/
[7719, 6191],

/***/ 6367:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/toPath.js ***!
  \***************************************************************************************************/
[7720, 6353, 6209, 6206, 6235, 6348, 6354, 6351],

/***/ 6368:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/omit.js ***!
  \*************************************************************************************************/
[7721, 6353, 6259, 6369, 6346, 6237, 6373, 6362, 6304],

/***/ 6369:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseUnset.js ***!
  \*******************************************************************************************************/
[7722, 6346, 6370, 6371, 6354],

/***/ 6370:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/last.js ***!
  \*************************************************************************************************/
1429,

/***/ 6371:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_parent.js ***!
  \****************************************************************************************************/
[7723, 6345, 6372],

/***/ 6372:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseSlice.js ***!
  \*******************************************************************************************************/
1431,

/***/ 6373:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_customOmitClone.js ***!
  \*************************************************************************************************************/
[7724, 6374],

/***/ 6374:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isPlainObject.js ***!
  \**********************************************************************************************************/
[7725, 6176, 6301, 6207],

/***/ 6375:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/Container.jsx ***!
  \*********************************************************************************************************/
[7841, 5963, 6144, 6376, 6602, 6603, 6604, 7041, 7042],

/***/ 6376:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/index.js ***!
  \*******************************************************************************************************/
[7064, 6377],

/***/ 6377:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/AnatomogramFactory.jsx ***!
  \*************************************************************************************************************************/
[7065, 6378, 6410, 6552, 6600],

/***/ 6378:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/react.js ***!
  \***************************************************************************************************************/
[7066, 6379],

/***/ 6379:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/React.js ***!
  \*******************************************************************************************************************/
[7067, 6380, 6381, 6393, 6396, 6397, 6402, 6385, 6407, 6408, 6409, 6387, 6403],

/***/ 6380:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/object-assign/index.js ***!
  \*******************************************************************************************************************************/
5,

/***/ 6381:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildren.js ***!
  \***************************************************************************************************************************/
[7068, 6382, 6385, 6388, 6390],

/***/ 6382:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/PooledClass.js ***!
  \*************************************************************************************************************************/
[7069, 6383, 6384],

/***/ 6383:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/reactProdInvariant.js ***!
  \********************************************************************************************************************************/
8,

/***/ 6384:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/invariant.js ***!
  \******************************************************************************************************************************/
9,

/***/ 6385:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactElement.js ***!
  \**************************************************************************************************************************/
[7070, 6380, 6386, 6387, 6389],

/***/ 6386:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactCurrentOwner.js ***!
  \*******************************************************************************************************************************/
11,

/***/ 6387:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/warning.js ***!
  \****************************************************************************************************************************/
[7071, 6388],

/***/ 6388:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/emptyFunction.js ***!
  \**********************************************************************************************************************************/
13,

/***/ 6389:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/canDefineProperty.js ***!
  \*******************************************************************************************************************************/
14,

/***/ 6390:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/traverseAllChildren.js ***!
  \*********************************************************************************************************************************/
[7072, 6383, 6386, 6385, 6391, 6384, 6392, 6387],

/***/ 6391:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getIteratorFn.js ***!
  \***************************************************************************************************************************/
16,

/***/ 6392:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/KeyEscapeUtils.js ***!
  \****************************************************************************************************************************/
17,

/***/ 6393:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponent.js ***!
  \****************************************************************************************************************************/
[7073, 6383, 6394, 6389, 6395, 6384, 6387],

/***/ 6394:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactNoopUpdateQueue.js ***!
  \**********************************************************************************************************************************/
[7074, 6387],

/***/ 6395:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/emptyObject.js ***!
  \********************************************************************************************************************************/
20,

/***/ 6396:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactPureComponent.js ***!
  \********************************************************************************************************************************/
[7075, 6380, 6393, 6394, 6395],

/***/ 6397:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactClass.js ***!
  \************************************************************************************************************************/
[7076, 6383, 6380, 6393, 6385, 6398, 6400, 6394, 6395, 6384, 6399, 6401, 6387],

/***/ 6398:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypeLocations.js ***!
  \************************************************************************************************************************************/
[7077, 6399],

/***/ 6399:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/keyMirror.js ***!
  \******************************************************************************************************************************/
[7078, 6384],

/***/ 6400:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypeLocationNames.js ***!
  \****************************************************************************************************************************************/
25,

/***/ 6401:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/keyOf.js ***!
  \**************************************************************************************************************************/
26,

/***/ 6402:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMFactories.js ***!
  \*******************************************************************************************************************************/
[7079, 6385, 6403],

/***/ 6403:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactElementValidator.js ***!
  \***********************************************************************************************************************************/
[7080, 6386, 6404, 6385, 6398, 6405, 6389, 6391, 6387],

/***/ 6404:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentTreeHook.js ***!
  \************************************************************************************************************************************/
[7081, 6383, 6386, 6384, 6387],

/***/ 6405:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/checkReactTypeSpec.js ***!
  \********************************************************************************************************************************/
[7082, 6383, 6400, 6406, 6384, 6387, 6404, 6404],

/***/ 6406:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypesSecret.js ***!
  \**********************************************************************************************************************************/
32,

/***/ 6407:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactPropTypes.js ***!
  \****************************************************************************************************************************/
[7083, 6385, 6400, 6406, 6388, 6391, 6387],

/***/ 6408:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactVersion.js ***!
  \**************************************************************************************************************************/
34,

/***/ 6409:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/onlyChild.js ***!
  \***********************************************************************************************************************/
[7084, 6383, 6385, 6384],

/***/ 6410:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/Anatomogram.jsx ***!
  \******************************************************************************************************************/
[7085, 6378, 6411, 6551],

/***/ 6411:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/AnatomogramImage.jsx ***!
  \***********************************************************************************************************************/
[7086, 6378, 6412, 6550],

/***/ 6412:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react-dom/index.js ***!
  \*******************************************************************************************************************/
[7087, 6413],

/***/ 6413:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOM.js ***!
  \**********************************************************************************************************************/
[7088, 6414, 6417, 6540, 6437, 6434, 6408, 6545, 6546, 6547, 6387, 6427, 6440, 6548, 6549],

/***/ 6414:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponentTree.js ***!
  \***********************************************************************************************************************************/
[7089, 6383, 6415, 6416, 6384],

/***/ 6415:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DOMProperty.js ***!
  \*************************************************************************************************************************/
[7090, 6383, 6384],

/***/ 6416:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponentFlags.js ***!
  \************************************************************************************************************************************/
42,

/***/ 6417:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDefaultInjection.js ***!
  \***********************************************************************************************************************************/
[7091, 6418, 6433, 6451, 6452, 6457, 6458, 6472, 6414, 6511, 6512, 6513, 6514, 6515, 6518, 6519, 6527, 6528, 6529],

/***/ 6418:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/BeforeInputEventPlugin.js ***!
  \************************************************************************************************************************************/
[7092, 6419, 6420, 6427, 6428, 6430, 6432, 6401],

/***/ 6419:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EventConstants.js ***!
  \****************************************************************************************************************************/
[7093, 6399],

/***/ 6420:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EventPropagators.js ***!
  \******************************************************************************************************************************/
[7094, 6419, 6421, 6423, 6425, 6426, 6387],

/***/ 6421:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginHub.js ***!
  \****************************************************************************************************************************/
[7095, 6383, 6422, 6423, 6424, 6425, 6426, 6384],

/***/ 6422:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginRegistry.js ***!
  \*********************************************************************************************************************************/
[7096, 6383, 6384],

/***/ 6423:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EventPluginUtils.js ***!
  \******************************************************************************************************************************/
[7097, 6383, 6419, 6424, 6384, 6387],

/***/ 6424:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactErrorUtils.js ***!
  \*****************************************************************************************************************************/
50,

/***/ 6425:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/accumulateInto.js ***!
  \****************************************************************************************************************************/
[7098, 6383, 6384],

/***/ 6426:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/forEachAccumulated.js ***!
  \********************************************************************************************************************************/
52,

/***/ 6427:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \*****************************************************************************************************************************************/
53,

/***/ 6428:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/FallbackCompositionState.js ***!
  \**************************************************************************************************************************************/
[7099, 6380, 6382, 6429],

/***/ 6429:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getTextContentAccessor.js ***!
  \************************************************************************************************************************************/
[7100, 6427],

/***/ 6430:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticCompositionEvent.js ***!
  \***************************************************************************************************************************************/
[7101, 6431],

/***/ 6431:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticEvent.js ***!
  \****************************************************************************************************************************/
[7102, 6380, 6382, 6388, 6387],

/***/ 6432:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticInputEvent.js ***!
  \*********************************************************************************************************************************/
[7103, 6431],

/***/ 6433:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ChangeEventPlugin.js ***!
  \*******************************************************************************************************************************/
[7104, 6419, 6421, 6420, 6427, 6414, 6434, 6431, 6448, 6449, 6450, 6401],

/***/ 6434:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactUpdates.js ***!
  \**************************************************************************************************************************/
[7105, 6383, 6380, 6435, 6382, 6436, 6437, 6447, 6384],

/***/ 6435:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/CallbackQueue.js ***!
  \***************************************************************************************************************************/
[7106, 6383, 6380, 6382, 6384],

/***/ 6436:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactFeatureFlags.js ***!
  \*******************************************************************************************************************************/
62,

/***/ 6437:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactReconciler.js ***!
  \*****************************************************************************************************************************/
[7107, 6438, 6440, 6387],

/***/ 6438:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactRef.js ***!
  \**********************************************************************************************************************/
[7108, 6439],

/***/ 6439:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactOwner.js ***!
  \************************************************************************************************************************/
[7109, 6383, 6384],

/***/ 6440:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactInstrumentation.js ***!
  \**********************************************************************************************************************************/
[7110, 6441],

/***/ 6441:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDebugTool.js ***!
  \****************************************************************************************************************************/
[7111, 6442, 6443, 6404, 6444, 6427, 6445, 6387],

/***/ 6442:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactInvalidSetStateWarningHook.js ***!
  \*********************************************************************************************************************************************/
[7112, 6387],

/***/ 6443:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactHostOperationHistoryHook.js ***!
  \*******************************************************************************************************************************************/
69,

/***/ 6444:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildrenMutationWarningHook.js ***!
  \**********************************************************************************************************************************************/
[7113, 6404, 6387],

/***/ 6445:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/performanceNow.js ***!
  \***********************************************************************************************************************************/
[7114, 6446],

/***/ 6446:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/performance.js ***!
  \********************************************************************************************************************************/
[7115, 6427],

/***/ 6447:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/Transaction.js ***!
  \*************************************************************************************************************************/
[7116, 6383, 6384],

/***/ 6448:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getEventTarget.js ***!
  \****************************************************************************************************************************/
74,

/***/ 6449:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/isEventSupported.js ***!
  \******************************************************************************************************************************/
[7117, 6427],

/***/ 6450:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/isTextInputElement.js ***!
  \********************************************************************************************************************************/
76,

/***/ 6451:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DefaultEventPluginOrder.js ***!
  \*************************************************************************************************************************************/
[7118, 6401],

/***/ 6452:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/EnterLeaveEventPlugin.js ***!
  \***********************************************************************************************************************************/
[7119, 6419, 6420, 6414, 6453, 6401],

/***/ 6453:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticMouseEvent.js ***!
  \*********************************************************************************************************************************/
[7120, 6454, 6455, 6456],

/***/ 6454:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticUIEvent.js ***!
  \******************************************************************************************************************************/
[7121, 6431, 6448],

/***/ 6455:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ViewportMetrics.js ***!
  \*****************************************************************************************************************************/
81,

/***/ 6456:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getEventModifierState.js ***!
  \***********************************************************************************************************************************/
82,

/***/ 6457:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \***********************************************************************************************************************************/
[7122, 6415],

/***/ 6458:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \**********************************************************************************************************************************************/
[7123, 6459, 6471],

/***/ 6459:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DOMChildrenOperations.js ***!
  \***********************************************************************************************************************************/
[7124, 6460, 6466, 6470, 6414, 6440, 6463, 6462, 6464],

/***/ 6460:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DOMLazyTree.js ***!
  \*************************************************************************************************************************/
[7125, 6461, 6462, 6463, 6464],

/***/ 6461:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DOMNamespaces.js ***!
  \***************************************************************************************************************************/
87,

/***/ 6462:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/setInnerHTML.js ***!
  \**************************************************************************************************************************/
[7126, 6427, 6461, 6463],

/***/ 6463:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/createMicrosoftUnsafeLocalFunction.js ***!
  \************************************************************************************************************************************************/
89,

/***/ 6464:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/setTextContent.js ***!
  \****************************************************************************************************************************/
[7127, 6427, 6465, 6462],

/***/ 6465:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/escapeTextContentForBrowser.js ***!
  \*****************************************************************************************************************************************/
91,

/***/ 6466:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/Danger.js ***!
  \********************************************************************************************************************/
[7128, 6383, 6460, 6427, 6467, 6388, 6384],

/***/ 6467:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \******************************************************************************************************************************************/
[7129, 6427, 6468, 6469, 6384],

/***/ 6468:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \*****************************************************************************************************************************************/
[7130, 6384],

/***/ 6469:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \**********************************************************************************************************************************/
[7131, 6427, 6384],

/***/ 6470:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \****************************************************************************************************************************************/
[7132, 6399],

/***/ 6471:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMIDOperations.js ***!
  \**********************************************************************************************************************************/
[7133, 6459, 6414],

/***/ 6472:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMComponent.js ***!
  \*******************************************************************************************************************************/
[7134, 6383, 6380, 6473, 6475, 6460, 6461, 6415, 6483, 6419, 6421, 6422, 6485, 6488, 6416, 6414, 6490, 6492, 6493, 6494, 6440, 6495, 6507, 6388, 6465, 6384, 6449, 6401, 6502, 6510, 6387],

/***/ 6473:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/AutoFocusUtils.js ***!
  \****************************************************************************************************************************/
[7135, 6414, 6474],

/***/ 6474:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/focusNode.js ***!
  \******************************************************************************************************************************/
100,

/***/ 6475:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/CSSPropertyOperations.js ***!
  \***********************************************************************************************************************************/
[7136, 6476, 6427, 6440, 6477, 6479, 6480, 6482, 6387],

/***/ 6476:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/CSSProperty.js ***!
  \*************************************************************************************************************************/
102,

/***/ 6477:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \**************************************************************************************************************************************/
[7137, 6478],

/***/ 6478:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/camelize.js ***!
  \*****************************************************************************************************************************/
104,

/***/ 6479:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/dangerousStyleValue.js ***!
  \*********************************************************************************************************************************/
[7138, 6476, 6387],

/***/ 6480:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \***************************************************************************************************************************************/
[7139, 6481],

/***/ 6481:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/hyphenate.js ***!
  \******************************************************************************************************************************/
107,

/***/ 6482:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \**************************************************************************************************************************************/
108,

/***/ 6483:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DOMPropertyOperations.js ***!
  \***********************************************************************************************************************************/
[7140, 6415, 6414, 6440, 6484, 6387],

/***/ 6484:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \*******************************************************************************************************************************************/
[7141, 6465],

/***/ 6485:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactBrowserEventEmitter.js ***!
  \**************************************************************************************************************************************/
[7142, 6380, 6419, 6422, 6486, 6455, 6487, 6449],

/***/ 6486:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactEventEmitterMixin.js ***!
  \************************************************************************************************************************************/
[7143, 6421],

/***/ 6487:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getVendorPrefixedEventName.js ***!
  \****************************************************************************************************************************************/
[7144, 6427],

/***/ 6488:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMButton.js ***!
  \****************************************************************************************************************************/
[7145, 6489],

/***/ 6489:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/DisabledInputUtils.js ***!
  \********************************************************************************************************************************/
115,

/***/ 6490:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMInput.js ***!
  \***************************************************************************************************************************/
[7146, 6383, 6380, 6489, 6483, 6491, 6414, 6434, 6384, 6387],

/***/ 6491:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/LinkedValueUtils.js ***!
  \******************************************************************************************************************************/
[7147, 6383, 6407, 6398, 6406, 6384, 6387],

/***/ 6492:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMOption.js ***!
  \****************************************************************************************************************************/
[7148, 6380, 6381, 6414, 6493, 6387],

/***/ 6493:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMSelect.js ***!
  \****************************************************************************************************************************/
[7149, 6380, 6489, 6491, 6414, 6434, 6387],

/***/ 6494:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTextarea.js ***!
  \******************************************************************************************************************************/
[7150, 6383, 6380, 6489, 6491, 6414, 6434, 6384, 6387],

/***/ 6495:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactMultiChild.js ***!
  \*****************************************************************************************************************************/
[7151, 6383, 6496, 6497, 6440, 6470, 6386, 6437, 6498, 6388, 6506, 6384],

/***/ 6496:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactComponentEnvironment.js ***!
  \***************************************************************************************************************************************/
[7152, 6383, 6384],

/***/ 6497:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactInstanceMap.js ***!
  \******************************************************************************************************************************/
123,

/***/ 6498:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactChildReconciler.js ***!
  \**********************************************************************************************************************************/
[7153, 6437, 6499, 6392, 6503, 6390, 6387, 6404, 6404],

/***/ 6499:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/instantiateReactComponent.js ***!
  \***************************************************************************************************************************************/
[7154, 6383, 6380, 6500, 6504, 6505, 6384, 6387],

/***/ 6500:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactCompositeComponent.js ***!
  \*************************************************************************************************************************************/
[7155, 6383, 6380, 6496, 6386, 6385, 6424, 6497, 6440, 6501, 6398, 6437, 6405, 6395, 6384, 6502, 6503, 6387],

/***/ 6501:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactNodeTypes.js ***!
  \****************************************************************************************************************************/
[7156, 6383, 6385, 6384],

/***/ 6502:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/shallowEqual.js ***!
  \*********************************************************************************************************************************/
128,

/***/ 6503:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/shouldUpdateReactComponent.js ***!
  \****************************************************************************************************************************************/
129,

/***/ 6504:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactEmptyComponent.js ***!
  \*********************************************************************************************************************************/
130,

/***/ 6505:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactHostComponent.js ***!
  \********************************************************************************************************************************/
[7157, 6383, 6380, 6384],

/***/ 6506:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/flattenChildren.js ***!
  \*****************************************************************************************************************************/
[7158, 6392, 6390, 6387, 6404, 6404],

/***/ 6507:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactServerRenderingTransaction.js ***!
  \*********************************************************************************************************************************************/
[7159, 6380, 6382, 6447, 6440, 6508],

/***/ 6508:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactServerUpdateQueue.js ***!
  \************************************************************************************************************************************/
[7160, 6509, 6447, 6387],

/***/ 6509:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactUpdateQueue.js ***!
  \******************************************************************************************************************************/
[7161, 6383, 6386, 6497, 6440, 6434, 6384, 6387],

/***/ 6510:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/validateDOMNesting.js ***!
  \********************************************************************************************************************************/
[7162, 6380, 6388, 6387],

/***/ 6511:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMEmptyComponent.js ***!
  \************************************************************************************************************************************/
[7163, 6380, 6460, 6414],

/***/ 6512:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTreeTraversal.js ***!
  \***********************************************************************************************************************************/
[7164, 6383, 6384],

/***/ 6513:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMTextComponent.js ***!
  \***********************************************************************************************************************************/
[7165, 6383, 6380, 6459, 6460, 6414, 6465, 6384, 6510],

/***/ 6514:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \******************************************************************************************************************************************/
[7166, 6380, 6434, 6447, 6388],

/***/ 6515:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactEventListener.js ***!
  \********************************************************************************************************************************/
[7167, 6380, 6516, 6427, 6382, 6414, 6434, 6448, 6517],

/***/ 6516:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/EventListener.js ***!
  \**********************************************************************************************************************************/
[7168, 6388],

/***/ 6517:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \***********************************************************************************************************************************************/
1590,

/***/ 6518:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactInjection.js ***!
  \****************************************************************************************************************************/
[7169, 6415, 6421, 6423, 6496, 6397, 6504, 6485, 6505, 6434],

/***/ 6519:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactReconcileTransaction.js ***!
  \***************************************************************************************************************************************/
[7170, 6380, 6435, 6382, 6485, 6520, 6440, 6447, 6509],

/***/ 6520:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactInputSelection.js ***!
  \*********************************************************************************************************************************/
[7171, 6521, 6523, 6474, 6526],

/***/ 6521:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMSelection.js ***!
  \*******************************************************************************************************************************/
[7172, 6427, 6522, 6429],

/***/ 6522:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getNodeForCharacterOffset.js ***!
  \***************************************************************************************************************************************/
148,

/***/ 6523:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/containsNode.js ***!
  \*********************************************************************************************************************************/
[7173, 6524],

/***/ 6524:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/isTextNode.js ***!
  \*******************************************************************************************************************************/
[7174, 6525],

/***/ 6525:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/isNode.js ***!
  \***************************************************************************************************************************/
1598,

/***/ 6526:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/~/fbjs/lib/getActiveElement.js ***!
  \*************************************************************************************************************************************/
1599,

/***/ 6527:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SVGDOMPropertyConfig.js ***!
  \**********************************************************************************************************************************/
153,

/***/ 6528:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SelectEventPlugin.js ***!
  \*******************************************************************************************************************************/
[7175, 6419, 6420, 6427, 6414, 6520, 6431, 6526, 6450, 6401, 6502],

/***/ 6529:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SimpleEventPlugin.js ***!
  \*******************************************************************************************************************************/
[7176, 6383, 6419, 6516, 6420, 6414, 6530, 6531, 6431, 6532, 6533, 6453, 6536, 6537, 6538, 6454, 6539, 6388, 6534, 6384, 6401],

/***/ 6530:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticAnimationEvent.js ***!
  \*************************************************************************************************************************************/
[7177, 6431],

/***/ 6531:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticClipboardEvent.js ***!
  \*************************************************************************************************************************************/
[7178, 6431],

/***/ 6532:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticFocusEvent.js ***!
  \*********************************************************************************************************************************/
[7179, 6454],

/***/ 6533:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticKeyboardEvent.js ***!
  \************************************************************************************************************************************/
[7180, 6454, 6534, 6535, 6456],

/***/ 6534:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getEventCharCode.js ***!
  \******************************************************************************************************************************/
160,

/***/ 6535:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getEventKey.js ***!
  \*************************************************************************************************************************/
[7181, 6534],

/***/ 6536:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticDragEvent.js ***!
  \********************************************************************************************************************************/
[7182, 6453],

/***/ 6537:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticTouchEvent.js ***!
  \*********************************************************************************************************************************/
[7183, 6454, 6456],

/***/ 6538:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticTransitionEvent.js ***!
  \**************************************************************************************************************************************/
[7184, 6431],

/***/ 6539:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/SyntheticWheelEvent.js ***!
  \*********************************************************************************************************************************/
[7185, 6453],

/***/ 6540:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactMount.js ***!
  \************************************************************************************************************************/
[7186, 6383, 6460, 6415, 6485, 6386, 6414, 6541, 6542, 6385, 6436, 6497, 6440, 6543, 6437, 6509, 6434, 6395, 6499, 6384, 6462, 6503, 6387],

/***/ 6541:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMContainerInfo.js ***!
  \***********************************************************************************************************************************/
[7187, 6510],

/***/ 6542:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMFeatureFlags.js ***!
  \**********************************************************************************************************************************/
168,

/***/ 6543:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactMarkupChecksum.js ***!
  \*********************************************************************************************************************************/
[7188, 6544],

/***/ 6544:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/adler32.js ***!
  \*********************************************************************************************************************/
170,

/***/ 6545:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/findDOMNode.js ***!
  \*************************************************************************************************************************/
[7189, 6383, 6386, 6414, 6497, 6546, 6384, 6387],

/***/ 6546:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/getHostComponentFromComposite.js ***!
  \*******************************************************************************************************************************************/
[7190, 6501],

/***/ 6547:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/renderSubtreeIntoContainer.js ***!
  \****************************************************************************************************************************************/
[7191, 6540],

/***/ 6548:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMUnknownPropertyHook.js ***!
  \*****************************************************************************************************************************************/
[7192, 6415, 6422, 6404, 6387],

/***/ 6549:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/react/lib/ReactDOMNullInputValuePropHook.js ***!
  \********************************************************************************************************************************************/
[7193, 6404, 6387],

/***/ 6550:
/*!*******************************************************************************************************************************************************************************!*\
  !*** ./~/imports-loader?this=>window,fix=>module.exports=0!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/~/snapsvg/dist/snap.svg.js ***!
  \*******************************************************************************************************************************************************************************/
176,

/***/ 6551:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.jsx ***!
  \********************************************************************************************************************/
[7194, 6378, 6552, 6598],

/***/ 6552:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/imagesAvailable.js ***!
  \*********************************************************************************************************************/
[7195, 179, 186, 6553, 6554, 6555, 6566],

/***/ 6553:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/json/svgsForSpecies.json ***!
  \*********************************************************************************************************************************/
187,

/***/ 6554:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/json/idsForSvgs.json ***!
  \*****************************************************************************************************************************/
188,

/***/ 6555:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons ^\.\/.*selected\.png$ ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./brain_selected.png": 6556,
		"./brain_unselected.png": 6557,
		"./female_selected.png": 6558,
		"./female_unselected.png": 6559,
		"./flower_parts_selected.png": 6560,
		"./flower_parts_unselected.png": 6561,
		"./male_selected.png": 6562,
		"./male_unselected.png": 6563,
		"./whole_plant_selected.png": 6564,
		"./whole_plant_unselected.png": 6565
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
	webpackContext.id = 6555;


/***/ },

/***/ 6556:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/brain_selected.png ***!
  \*********************************************************************************************************************************/
190,

/***/ 6557:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/brain_unselected.png ***!
  \***********************************************************************************************************************************/
191,

/***/ 6558:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/female_selected.png ***!
  \**********************************************************************************************************************************/
192,

/***/ 6559:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/female_unselected.png ***!
  \************************************************************************************************************************************/
193,

/***/ 6560:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/flower_parts_selected.png ***!
  \****************************************************************************************************************************************/
194,

/***/ 6561:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/flower_parts_unselected.png ***!
  \******************************************************************************************************************************************/
195,

/***/ 6562:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/male_selected.png ***!
  \********************************************************************************************************************************/
196,

/***/ 6563:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/male_unselected.png ***!
  \**********************************************************************************************************************************/
197,

/***/ 6564:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/whole_plant_selected.png ***!
  \***************************************************************************************************************************************/
198,

/***/ 6565:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/icons/whole_plant_unselected.png ***!
  \*****************************************************************************************************************************************/
199,

/***/ 6566:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg ^\.\/.*$ ***!
  \*********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./anolis_carolinensis.svg": 6567,
		"./arabidopsis_thaliana_whole_plant.svg": 6568,
		"./brachypodium_distachyon_flower_parts.svg": 6569,
		"./brachypodium_distachyon_whole_plant.svg": 6570,
		"./chicken.svg": 6571,
		"./cow.svg": 6572,
		"./hordeum_vulgare_flower_parts.svg": 6573,
		"./hordeum_vulgare_whole_plant.svg": 6574,
		"./human_brain.svg": 6575,
		"./human_female.svg": 6576,
		"./human_male.svg": 6577,
		"./macaca_mulatta.svg": 6578,
		"./monodelphis_domestica.svg": 6579,
		"./mouse_brain.svg": 6580,
		"./mouse_female.svg": 6581,
		"./mouse_male.svg": 6582,
		"./oryza_sativa_flower_parts.svg": 6583,
		"./oryza_sativa_whole_plant.svg": 6584,
		"./papio_anubis.svg": 6585,
		"./rat.svg": 6586,
		"./solanum_lycopersicum_flower_parts.svg": 6587,
		"./solanum_lycopersicum_whole_plant.svg": 6588,
		"./solanum_tuberosum_whole_plant.svg": 6589,
		"./sorghum_bicolor_flower_parts.svg": 6590,
		"./sorghum_bicolor_whole_plant.svg": 6591,
		"./tetraodon_nigroviridis.svg": 6592,
		"./triticum_aestivum_flower_parts.svg": 6593,
		"./triticum_aestivum_whole_plant.svg": 6594,
		"./xenopus_tropicalis.svg": 6595,
		"./zea_mays_flower_parts.svg": 6596,
		"./zea_mays_whole_plant.svg": 6597
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
	webpackContext.id = 6566;


/***/ },

/***/ 6567:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/anolis_carolinensis.svg ***!
  \************************************************************************************************************************************/
201,

/***/ 6568:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/arabidopsis_thaliana_whole_plant.svg ***!
  \*************************************************************************************************************************************************/
202,

/***/ 6569:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/brachypodium_distachyon_flower_parts.svg ***!
  \*****************************************************************************************************************************************************/
203,

/***/ 6570:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/brachypodium_distachyon_whole_plant.svg ***!
  \****************************************************************************************************************************************************/
204,

/***/ 6571:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/chicken.svg ***!
  \************************************************************************************************************************/
205,

/***/ 6572:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/cow.svg ***!
  \********************************************************************************************************************/
206,

/***/ 6573:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/hordeum_vulgare_flower_parts.svg ***!
  \*********************************************************************************************************************************************/
207,

/***/ 6574:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/hordeum_vulgare_whole_plant.svg ***!
  \********************************************************************************************************************************************/
208,

/***/ 6575:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/human_brain.svg ***!
  \****************************************************************************************************************************/
209,

/***/ 6576:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/human_female.svg ***!
  \*****************************************************************************************************************************/
210,

/***/ 6577:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/human_male.svg ***!
  \***************************************************************************************************************************/
211,

/***/ 6578:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/macaca_mulatta.svg ***!
  \*******************************************************************************************************************************/
212,

/***/ 6579:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/monodelphis_domestica.svg ***!
  \**************************************************************************************************************************************/
213,

/***/ 6580:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/mouse_brain.svg ***!
  \****************************************************************************************************************************/
214,

/***/ 6581:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/mouse_female.svg ***!
  \*****************************************************************************************************************************/
215,

/***/ 6582:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/mouse_male.svg ***!
  \***************************************************************************************************************************/
216,

/***/ 6583:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/oryza_sativa_flower_parts.svg ***!
  \******************************************************************************************************************************************/
217,

/***/ 6584:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/oryza_sativa_whole_plant.svg ***!
  \*****************************************************************************************************************************************/
218,

/***/ 6585:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/papio_anubis.svg ***!
  \*****************************************************************************************************************************/
219,

/***/ 6586:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/rat.svg ***!
  \********************************************************************************************************************/
220,

/***/ 6587:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/solanum_lycopersicum_flower_parts.svg ***!
  \**************************************************************************************************************************************************/
221,

/***/ 6588:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/solanum_lycopersicum_whole_plant.svg ***!
  \*************************************************************************************************************************************************/
222,

/***/ 6589:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/solanum_tuberosum_whole_plant.svg ***!
  \**********************************************************************************************************************************************/
223,

/***/ 6590:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/sorghum_bicolor_flower_parts.svg ***!
  \*********************************************************************************************************************************************/
224,

/***/ 6591:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/sorghum_bicolor_whole_plant.svg ***!
  \********************************************************************************************************************************************/
225,

/***/ 6592:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/tetraodon_nigroviridis.svg ***!
  \***************************************************************************************************************************************/
226,

/***/ 6593:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/triticum_aestivum_flower_parts.svg ***!
  \***********************************************************************************************************************************************/
227,

/***/ 6594:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/triticum_aestivum_whole_plant.svg ***!
  \**********************************************************************************************************************************************/
228,

/***/ 6595:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/xenopus_tropicalis.svg ***!
  \***********************************************************************************************************************************/
229,

/***/ 6596:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/zea_mays_flower_parts.svg ***!
  \**************************************************************************************************************************************/
230,

/***/ 6597:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/resources/svg/zea_mays_whole_plant.svg ***!
  \*************************************************************************************************************************************/
231,

/***/ 6598:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \*********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./SelectionIcon.less */ 6599);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./SelectionIcon.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./SelectionIcon.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6599:
/*!****************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".selection-icon {\n  display: block;\n  position: relative;\n  padding: 0;\n  line-height: normal;\n  margin-right: .1em;\n  cursor: pointer;\n  vertical-align: middle;\n  text-align: center;\n  overflow: visible;\n  border: 1px solid #ccc;\n  border-top-left-radius: 4px;\n  border-top-right-radius: 4px;\n  border-bottom-left-radius: 4px;\n  border-bottom-right-radius: 4px;\n  width: 24px;\n  height: 24px;\n  padding: 2px;\n}\n.selection-icon:hover {\n  border: 1px solid #fbcb09;\n  background: #fdf5ce 50% 50% repeat-x;\n  font-weight: bold;\n  color: #c77405;\n}\n.jquery-ui-like-button {\n  display: block;\n  position: relative;\n  padding: 0;\n  line-height: normal;\n  margin-right: .1em;\n  cursor: pointer;\n  vertical-align: middle;\n  text-align: center;\n  overflow: visible;\n}\n.rounded-corners {\n  border: 1px solid #ccc;\n  border-top-left-radius: 4px;\n  border-top-right-radius: 4px;\n  border-bottom-left-radius: 4px;\n  border-bottom-right-radius: 4px;\n}\n.right-dimensions {\n  width: 24px;\n  height: 24px;\n  padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6600:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./ContainerLayout.less */ 6601);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./ContainerLayout.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./ContainerLayout.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6601:
/*!******************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \******************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, "#gxaAnatomogramWrapper {\n  display: block;\n  zoom: 1;\n  position: relative;\n  overflow: hidden;\n  marginLeft: 270px;\n}\n#gxaAnatomogramWrapper:after {\n  content: \" \";\n  display: block;\n  font-size: 0;\n  height: 0;\n  clear: both;\n  visibility: hidden;\n}\n#gxaAnatomogramAside {\n  float: left;\n  max-width: 270px;\n}\n.clearfix {\n  display: block;\n  zoom: 1;\n}\n.clearfix:after {\n  content: \" \";\n  display: block;\n  font-size: 0;\n  height: 0;\n  clear: both;\n  visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6602:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/ExperimentDescription.jsx ***!
  \*********************************************************************************************************************/
[7842, 5963],

/***/ 6603:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/Footer.jsx ***!
  \******************************************************************************************************/
[7843, 5963],

/***/ 6604:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/ChartContainer.jsx ***!
  \******************************************************************************************************************/
[7844, 5963, 6605, 6609, 7039, 6797],

/***/ 6605:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/uncontrollable/index.js ***!
  \**********************************************************************************************************/
[7487, 6606],

/***/ 6606:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/uncontrollable/createUncontrollable.js ***!
  \*************************************************************************************************************************/
[7488, 5963, 6607, 6608],

/***/ 6607:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/uncontrollable/~/invariant/browser.js ***!
  \************************************************************************************************************************/
346,

/***/ 6608:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/uncontrollable/utils.js ***!
  \**********************************************************************************************************/
[7489, 5963, 6607],

/***/ 6609:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/HeatmapWithControls.jsx ***!
  \***********************************************************************************************************************/
[7845, 5963, 6610, 6745, 6799, 6807, 6821, 6827, 6833, 6874, 6875, 6883, 7036, 7038, 6797],

/***/ 6610:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/OrderingsDropdown.jsx ***!
  \******************************************************************************************************************************/
[7846, 5963, 6611, 6743, 6744],

/***/ 6611:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Dropdown.js ***!
  \******************************************************************************************************************/
[7484, 6612, 6613, 6651, 6652, 6688, 6696, 6697, 6700, 6702, 5963, 5993, 6703, 6705, 6706, 6605, 6707, 6708, 6721, 6741, 6714, 6739, 6742, 6740],

/***/ 6612:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \*****************************************************************************************************************************************************/
286,

/***/ 6613:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \*************************************************************************************************************************************/
[7231, 6614],

/***/ 6614:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \*******************************************************************************************************************************************/
[7232, 6615],

/***/ 6615:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \********************************************************************************************************************************************************/
[7233, 6616, 6619],

/***/ 6616:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \*****************************************************************************************************************************************************************/
[7234, 6617, 6632],

/***/ 6617:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \*******************************************************************************************************************************************************/
[7212, 6618, 6619, 6620, 6622],

/***/ 6618:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \*******************************************************************************************************************************************************/
255,

/***/ 6619:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \*****************************************************************************************************************************************************/
256,

/***/ 6620:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \****************************************************************************************************************************************************/
[7213, 6621],

/***/ 6621:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \***********************************************************************************************************************************************************/
258,

/***/ 6622:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \*****************************************************************************************************************************************************/
[7214, 6623, 6631, 6627],

/***/ 6623:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \**********************************************************************************************************************************************************/
[7215, 6624, 6626, 6630, 6627],

/***/ 6624:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \**********************************************************************************************************************************************************/
[7216, 6625],

/***/ 6625:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \**********************************************************************************************************************************************************/
262,

/***/ 6626:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \***************************************************************************************************************************************************************/
[7217, 6627, 6628, 6629],

/***/ 6627:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \************************************************************************************************************************************************************/
[7218, 6628],

/***/ 6628:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \******************************************************************************************************************************************************/
265,

/***/ 6629:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \***********************************************************************************************************************************************************/
[7219, 6625, 6618],

/***/ 6630:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \*************************************************************************************************************************************************************/
[7220, 6625],

/***/ 6631:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \**************************************************************************************************************************************************************/
268,

/***/ 6632:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \**************************************************************************************************************************************************************/
[7235, 6633, 6648, 6649, 6650, 6637, 6628],

/***/ 6633:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \************************************************************************************************************************************************************/
[7222, 6634, 6647],

/***/ 6634:
/*!*********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \*********************************************************************************************************************************************************************/
[7223, 6635, 6636, 6640, 6644],

/***/ 6635:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \****************************************************************************************************************************************************/
272,

/***/ 6636:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \***********************************************************************************************************************************************************/
[7224, 6637, 6639],

/***/ 6637:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \********************************************************************************************************************************************************/
[7225, 6638],

/***/ 6638:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \****************************************************************************************************************************************************/
275,

/***/ 6639:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \********************************************************************************************************************************************************/
276,

/***/ 6640:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \***************************************************************************************************************************************************************/
[7226, 6636, 6641, 6643],

/***/ 6641:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \**********************************************************************************************************************************************************/
[7227, 6642],

/***/ 6642:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \***********************************************************************************************************************************************************/
279,

/***/ 6643:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \*********************************************************************************************************************************************************/
[7228, 6642],

/***/ 6644:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \***********************************************************************************************************************************************************/
[7229, 6645, 6646],

/***/ 6645:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \*******************************************************************************************************************************************************/
[7230, 6618],

/***/ 6646:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \****************************************************************************************************************************************************/
283,

/***/ 6647:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \**************************************************************************************************************************************************************/
284,

/***/ 6648:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \************************************************************************************************************************************************************/
292,

/***/ 6649:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \***********************************************************************************************************************************************************/
285,

/***/ 6650:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \**********************************************************************************************************************************************************/
[7236, 6639],

/***/ 6651:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/classCallCheck.js ***!
  \********************************************************************************************************************************************/
294,

/***/ 6652:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \*******************************************************************************************************************************************************/
[7237, 6653],

/***/ 6653:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/typeof.js ***!
  \************************************************************************************************************************************/
[7238, 6654, 6674],

/***/ 6654:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/symbol/iterator.js ***!
  \*********************************************************************************************************************************************/
[7239, 6655],

/***/ 6655:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \**********************************************************************************************************************************************************/
[7240, 6656, 6669, 6673],

/***/ 6656:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \*******************************************************************************************************************************************************************/
[7241, 6657, 6658],

/***/ 6657:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \**********************************************************************************************************************************************************/
[7242, 6642, 6639],

/***/ 6658:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \************************************************************************************************************************************************************/
[7243, 6659, 6617, 6660, 6622, 6635, 6661, 6662, 6666, 6668, 6667],

/***/ 6659:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \********************************************************************************************************************************************************/
302,

/***/ 6660:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \*********************************************************************************************************************************************************/
[7244, 6622],

/***/ 6661:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \**********************************************************************************************************************************************************/
304,

/***/ 6662:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \************************************************************************************************************************************************************/
[7245, 6663, 6631, 6666, 6622, 6667],

/***/ 6663:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \**************************************************************************************************************************************************************/
[7246, 6624, 6664, 6647, 6644, 6629, 6665],

/***/ 6664:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \***********************************************************************************************************************************************************/
[7247, 6623, 6624, 6633, 6627],

/***/ 6665:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \*****************************************************************************************************************************************************/
[7248, 6618],

/***/ 6666:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \******************************************************************************************************************************************************************/
[7249, 6623, 6635, 6667],

/***/ 6667:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \****************************************************************************************************************************************************/
[7250, 6645, 6646, 6618],

/***/ 6668:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \***********************************************************************************************************************************************************/
[7251, 6635, 6650, 6644],

/***/ 6669:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \****************************************************************************************************************************************************************/
[7252, 6670, 6618, 6622, 6661, 6667],

/***/ 6670:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \******************************************************************************************************************************************************************/
[7253, 6671, 6672, 6661, 6636, 6658],

/***/ 6671:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \*******************************************************************************************************************************************************************/
314,

/***/ 6672:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \**********************************************************************************************************************************************************/
315,

/***/ 6673:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \********************************************************************************************************************************************************/
[7254, 6667],

/***/ 6674:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/symbol.js ***!
  \************************************************************************************************************************************/
[7255, 6675],

/***/ 6675:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \*******************************************************************************************************************************************************/
[7256, 6676, 6685, 6686, 6687, 6619],

/***/ 6676:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \**********************************************************************************************************************************************************/
[7257, 6618, 6635, 6627, 6617, 6660, 6677, 6628, 6645, 6666, 6646, 6667, 6673, 6678, 6679, 6680, 6681, 6624, 6636, 6630, 6631, 6663, 6682, 6684, 6623, 6633, 6683, 6649, 6648, 6659, 6622],

/***/ 6677:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \*****************************************************************************************************************************************************/
[7258, 6646, 6625, 6635, 6623, 6628],

/***/ 6678:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \***********************************************************************************************************************************************************/
[7259, 6618, 6619, 6659, 6673, 6623],

/***/ 6679:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \******************************************************************************************************************************************************/
[7260, 6633, 6636],

/***/ 6680:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \**********************************************************************************************************************************************************/
[7261, 6633, 6648, 6649],

/***/ 6681:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \*********************************************************************************************************************************************************/
[7262, 6638],

/***/ 6682:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \****************************************************************************************************************************************************************/
[7263, 6636, 6683],

/***/ 6683:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \************************************************************************************************************************************************************/
[7264, 6634, 6647],

/***/ 6684:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \************************************************************************************************************************************************************/
[7265, 6649, 6631, 6636, 6630, 6635, 6626, 6627],

/***/ 6685:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \********************************************************************************************************************************************************************/
328,

/***/ 6686:
/*!*************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \*************************************************************************************************************************************************************************/
[7266, 6678],

/***/ 6687:
/*!*********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \*********************************************************************************************************************************************************************/
[7267, 6678],

/***/ 6688:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \**************************************************************************************************************************************/
[7268, 6689, 6693, 6653],

/***/ 6689:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \*****************************************************************************************************************************************************/
[7269, 6690],

/***/ 6690:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \******************************************************************************************************************************************************************/
[7270, 6691, 6619],

/***/ 6691:
/*!***************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \***************************************************************************************************************************************************************************/
[7271, 6617, 6692],

/***/ 6692:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \**********************************************************************************************************************************************************/
[7272, 6625, 6624, 6620, 6684],

/***/ 6693:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \*******************************************************************************************************************************************/
[7273, 6694],

/***/ 6694:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \********************************************************************************************************************************************************/
[7274, 6695, 6619],

/***/ 6695:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \*****************************************************************************************************************************************************************/
[7275, 6617, 6663],

/***/ 6696:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/classnames/index.js ***!
  \************************************************************************************************************************/
339,

/***/ 6697:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/activeElement.js ***!
  \*********************************************************************************************************************************/
[7485, 6698, 6699],

/***/ 6698:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/babelHelpers.js ***!
  \*************************************************************************************************************************************/
1073,

/***/ 6699:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/ownerDocument.js ***!
  \*********************************************************************************************************************************/
1081,

/***/ 6700:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/contains.js ***!
  \**********************************************************************************************************************************/
[7486, 6701],

/***/ 6701:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/inDOM.js ***!
  \******************************************************************************************************************************/
1077,

/***/ 6702:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/keycode/index.js ***!
  \*********************************************************************************************************************/
1083,

/***/ 6703:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/all.js ***!
  \********************************************************************************************************************************/
[7469, 6704],

/***/ 6704:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \*************************************************************************************************************************************************************/
341,

/***/ 6705:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \****************************************************************************************************************************************/
[7276, 5963, 6704],

/***/ 6706:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/isRequiredForA11y.js ***!
  \**********************************************************************************************************************************************/
1084,

/***/ 6707:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/warning/browser.js ***!
  \***********************************************************************************************************************/
352,

/***/ 6708:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ButtonGroup.js ***!
  \*********************************************************************************************************************/
[7468, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6703, 6709, 6714],

/***/ 6709:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Button.js ***!
  \****************************************************************************************************************/
[7208, 6710, 6612, 6613, 6651, 6652, 6688, 6696, 5963, 6705, 6714, 6719, 6720],

/***/ 6710:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/values.js ***!
  \*******************************************************************************************************************************************/
[7209, 6711],

/***/ 6711:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \********************************************************************************************************************************************************/
[7210, 6712, 6619],

/***/ 6712:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \*****************************************************************************************************************************************************************/
[7211, 6617, 6713],

/***/ 6713:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \****************************************************************************************************************************************************************/
[7221, 6633, 6636, 6649],

/***/ 6714:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \******************************************************************************************************************************/
[7277, 6715, 6613, 6718, 5963, 6719],

/***/ 6715:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/entries.js ***!
  \********************************************************************************************************************************************/
[7278, 6716],

/***/ 6716:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \*********************************************************************************************************************************************************/
[7279, 6717, 6619],

/***/ 6717:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \******************************************************************************************************************************************************************/
[7280, 6617, 6713],

/***/ 6718:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/invariant/browser.js ***!
  \*************************************************************************************************************************/
346,

/***/ 6719:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \***************************************************************************************************************************/
347,

/***/ 6720:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/SafeAnchor.js ***!
  \********************************************************************************************************************/
[7281, 6613, 6612, 6651, 6652, 6688, 5963, 6705],

/***/ 6721:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/DropdownMenu.js ***!
  \**********************************************************************************************************************/
[7490, 6613, 6612, 6722, 6651, 6652, 6688, 6696, 6702, 5963, 5993, 6731, 6714, 6739, 6740],

/***/ 6722:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/array/from.js ***!
  \****************************************************************************************************************************************/
[7432, 6723],

/***/ 6723:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/array/from.js ***!
  \*****************************************************************************************************************************************************/
[7433, 6656, 6724, 6619],

/***/ 6724:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.array.from.js ***!
  \**************************************************************************************************************************************************************/
[7412, 6620, 6617, 6650, 6725, 6726, 6641, 6727, 6728, 6730],

/***/ 6725:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-call.js ***!
  \**********************************************************************************************************************************************************/
[7413, 6624],

/***/ 6726:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_is-array-iter.js ***!
  \**************************************************************************************************************************************************************/
[7414, 6661, 6667],

/***/ 6727:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_create-property.js ***!
  \****************************************************************************************************************************************************************/
[7415, 6623, 6631],

/***/ 6728:
/*!************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/core.get-iterator-method.js ***!
  \************************************************************************************************************************************************************************/
[7416, 6729, 6667, 6661, 6619],

/***/ 6729:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_classof.js ***!
  \********************************************************************************************************************************************************/
[7411, 6638, 6667],

/***/ 6730:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/_iter-detect.js ***!
  \************************************************************************************************************************************************************/
[7417, 6667],

/***/ 6731:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/RootCloseWrapper.js ***!
  \*******************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	var _contains = __webpack_require__(/*! dom-helpers/query/contains */ 6732);
	
	var _contains2 = _interopRequireDefault(_contains);
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 5993);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	var _addEventListener = __webpack_require__(/*! ./utils/addEventListener */ 6734);
	
	var _addEventListener2 = _interopRequireDefault(_addEventListener);
	
	var _ownerDocument = __webpack_require__(/*! ./utils/ownerDocument */ 6737);
	
	var _ownerDocument2 = _interopRequireDefault(_ownerDocument);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var escapeKeyCode = 27;
	
	function isLeftClickEvent(event) {
	  return event.button === 0;
	}
	
	function isModifiedEvent(event) {
	  return !!(event.metaKey || event.altKey || event.ctrlKey || event.shiftKey);
	}
	
	/**
	 * The `<RootCloseWrapper/>` component registers your callback on the document
	 * when rendered. Powers the `<Overlay/>` component. This is used achieve modal
	 * style behavior where your callback is triggered when the user tries to
	 * interact with the rest of the document or hits the `esc` key.
	 */
	
	var RootCloseWrapper = function (_React$Component) {
	  _inherits(RootCloseWrapper, _React$Component);
	
	  function RootCloseWrapper(props, context) {
	    _classCallCheck(this, RootCloseWrapper);
	
	    var _this = _possibleConstructorReturn(this, (RootCloseWrapper.__proto__ || Object.getPrototypeOf(RootCloseWrapper)).call(this, props, context));
	
	    _this.handleMouseCapture = function (e) {
	      _this.preventMouseRootClose = isModifiedEvent(e) || !isLeftClickEvent(e) || (0, _contains2.default)(_reactDom2.default.findDOMNode(_this), e.target);
	    };
	
	    _this.handleMouse = function (e) {
	      if (!_this.preventMouseRootClose && _this.props.onRootClose) {
	        _this.props.onRootClose(e);
	      }
	    };
	
	    _this.handleKeyUp = function (e) {
	      if (e.keyCode === escapeKeyCode && _this.props.onRootClose) {
	        _this.props.onRootClose(e);
	      }
	    };
	
	    _this.preventMouseRootClose = false;
	    return _this;
	  }
	
	  _createClass(RootCloseWrapper, [{
	    key: 'componentDidMount',
	    value: function componentDidMount() {
	      if (!this.props.disabled) {
	        this.addEventListeners();
	      }
	    }
	  }, {
	    key: 'componentDidUpdate',
	    value: function componentDidUpdate(prevProps) {
	      if (!this.props.disabled && prevProps.disabled) {
	        this.addEventListeners();
	      } else if (this.props.disabled && !prevProps.disabled) {
	        this.removeEventListeners();
	      }
	    }
	  }, {
	    key: 'componentWillUnmount',
	    value: function componentWillUnmount() {
	      if (!this.props.disabled) {
	        this.removeEventListeners();
	      }
	    }
	  }, {
	    key: 'addEventListeners',
	    value: function addEventListeners() {
	      var event = this.props.event;
	
	      var doc = (0, _ownerDocument2.default)(this);
	
	      // Use capture for this listener so it fires before React's listener, to
	      // avoid false positives in the contains() check below if the target DOM
	      // element is removed in the React mouse callback.
	      this.documentMouseCaptureListener = (0, _addEventListener2.default)(doc, event, this.handleMouseCapture, true);
	
	      this.documentMouseListener = (0, _addEventListener2.default)(doc, event, this.handleMouse);
	
	      this.documentKeyupListener = (0, _addEventListener2.default)(doc, 'keyup', this.handleKeyUp);
	    }
	  }, {
	    key: 'removeEventListeners',
	    value: function removeEventListeners() {
	      if (this.documentMouseCaptureListener) {
	        this.documentMouseCaptureListener.remove();
	      }
	
	      if (this.documentMouseListener) {
	        this.documentMouseListener.remove();
	      }
	
	      if (this.documentKeyupListener) {
	        this.documentKeyupListener.remove();
	      }
	    }
	  }, {
	    key: 'render',
	    value: function render() {
	      return this.props.children;
	    }
	  }]);
	
	  return RootCloseWrapper;
	}(_react2.default.Component);
	
	RootCloseWrapper.displayName = 'RootCloseWrapper';
	
	RootCloseWrapper.propTypes = {
	  /**
	   * Callback fired after click or mousedown. Also triggers when user hits `esc`.
	   */
	  onRootClose: _react2.default.PropTypes.func,
	  /**
	   * Children to render.
	   */
	  children: _react2.default.PropTypes.element,
	  /**
	   * Disable the the RootCloseWrapper, preventing it from triggering `onRootClose`.
	   */
	  disabled: _react2.default.PropTypes.bool,
	  /**
	   * Choose which document mouse event to bind to.
	   */
	  event: _react2.default.PropTypes.oneOf(['click', 'mousedown'])
	};
	
	RootCloseWrapper.defaultProps = {
	  event: 'click'
	};
	
	exports.default = RootCloseWrapper;
	module.exports = exports['default'];

/***/ },

/***/ 6732:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/query/contains.js ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _inDOM = __webpack_require__(/*! ../util/inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function () {
	  // HTML DOM and SVG DOM may have different support levels,
	  // so we need to check on context instead of a document root element.
	  return _inDOM2.default ? function (context, node) {
	    if (context.contains) {
	      return context.contains(node);
	    } else if (context.compareDocumentPosition) {
	      return context === node || !!(context.compareDocumentPosition(node) & 16);
	    } else {
	      return fallback(context, node);
	    }
	  } : fallback;
	}();
	
	function fallback(context, node) {
	  if (node) do {
	    if (node === context) return true;
	  } while (node = node.parentNode);
	
	  return false;
	}
	module.exports = exports['default'];

/***/ },

/***/ 6733:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/inDOM.js ***!
  \***********************************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = !!(typeof window !== 'undefined' && window.document && window.document.createElement);
	module.exports = exports['default'];

/***/ },

/***/ 6734:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/addEventListener.js ***!
  \*************************************************************************************************************************************************/
[7491, 6735, 6736],

/***/ 6735:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/events/on.js ***!
  \**********************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _inDOM = __webpack_require__(/*! ../util/inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var on = function on() {};
	if (_inDOM2.default) {
	  on = function () {
	
	    if (document.addEventListener) return function (node, eventName, handler, capture) {
	      return node.addEventListener(eventName, handler, capture || false);
	    };else if (document.attachEvent) return function (node, eventName, handler) {
	      return node.attachEvent('on' + eventName, function (e) {
	        e = e || window.event;
	        e.target = e.target || e.srcElement;
	        e.currentTarget = node;
	        handler.call(node, e);
	      });
	    };
	  }();
	}
	
	exports.default = on;
	module.exports = exports['default'];

/***/ },

/***/ 6736:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/events/off.js ***!
  \***********************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _inDOM = __webpack_require__(/*! ../util/inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var off = function off() {};
	if (_inDOM2.default) {
	  off = function () {
	    if (document.addEventListener) return function (node, eventName, handler, capture) {
	      return node.removeEventListener(eventName, handler, capture || false);
	    };else if (document.attachEvent) return function (node, eventName, handler) {
	      return node.detachEvent('on' + eventName, handler);
	    };
	  }();
	}
	
	exports.default = off;
	module.exports = exports['default'];

/***/ },

/***/ 6737:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/ownerDocument.js ***!
  \**********************************************************************************************************************************************/
[7493, 5993, 6738],

/***/ 6738:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/ownerDocument.js ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = ownerDocument;
	function ownerDocument(node) {
	  return node && node.ownerDocument || document;
	}
	module.exports = exports["default"];

/***/ },

/***/ 6739:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/createChainedFunction.js ***!
  \*************************************************************************************************************************************/
1049,

/***/ 6740:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \**************************************************************************************************************************************/
[7283, 5963],

/***/ 6741:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/DropdownToggle.js ***!
  \************************************************************************************************************************/
[7494, 6613, 6612, 6651, 6652, 6688, 5963, 6696, 6709, 6720, 6714],

/***/ 6742:
/*!*************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/PropTypes.js ***!
  \*************************************************************************************************************************/
[7495, 6704, 6740],

/***/ 6743:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/MenuItem.js ***!
  \******************************************************************************************************************/
[7517, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6703, 6720, 6714, 6739],

/***/ 6744:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Glyphicon.js ***!
  \*******************************************************************************************************************/
[7286, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6714],

/***/ 6745:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/FiltersModal.jsx ***!
  \********************************************************************************************************************************/
[7847, 5963, 6746, 6709, 6744, 6785, 6796, 6797],

/***/ 6746:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Modal.js ***!
  \***************************************************************************************************************/
[7518, 6612, 6651, 6652, 6688, 6613, 6696, 6747, 6699, 6701, 6752, 5963, 5993, 6753, 6772, 6705, 6777, 6779, 6780, 6781, 6782, 6783, 6714, 6739, 6784, 6719],

/***/ 6747:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/index.js ***!
  \********************************************************************************************************************************/
[7519, 6748, 6749, 6750],

/***/ 6748:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/on.js ***!
  \*****************************************************************************************************************************/
[7483, 6701],

/***/ 6749:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/off.js ***!
  \******************************************************************************************************************************/
[7492, 6701],

/***/ 6750:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/filter.js ***!
  \*********************************************************************************************************************************/
[7520, 6700, 6751],

/***/ 6751:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/querySelectorAll.js ***!
  \******************************************************************************************************************************************/
1129,

/***/ 6752:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/scrollbarSize.js ***!
  \**************************************************************************************************************************************/
[7521, 6701],

/***/ 6753:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Modal.js ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; /*eslint-disable react/prop-types */
	
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _warning = __webpack_require__(/*! warning */ 6707);
	
	var _warning2 = _interopRequireDefault(_warning);
	
	var _componentOrElement = __webpack_require__(/*! react-prop-types/lib/componentOrElement */ 6754);
	
	var _componentOrElement2 = _interopRequireDefault(_componentOrElement);
	
	var _elementType = __webpack_require__(/*! react-prop-types/lib/elementType */ 6705);
	
	var _elementType2 = _interopRequireDefault(_elementType);
	
	var _Portal = __webpack_require__(/*! ./Portal */ 6755);
	
	var _Portal2 = _interopRequireDefault(_Portal);
	
	var _ModalManager = __webpack_require__(/*! ./ModalManager */ 6757);
	
	var _ModalManager2 = _interopRequireDefault(_ModalManager);
	
	var _ownerDocument = __webpack_require__(/*! ./utils/ownerDocument */ 6737);
	
	var _ownerDocument2 = _interopRequireDefault(_ownerDocument);
	
	var _addEventListener = __webpack_require__(/*! ./utils/addEventListener */ 6734);
	
	var _addEventListener2 = _interopRequireDefault(_addEventListener);
	
	var _addFocusListener = __webpack_require__(/*! ./utils/addFocusListener */ 6775);
	
	var _addFocusListener2 = _interopRequireDefault(_addFocusListener);
	
	var _inDOM = __webpack_require__(/*! dom-helpers/util/inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	var _activeElement = __webpack_require__(/*! dom-helpers/activeElement */ 6776);
	
	var _activeElement2 = _interopRequireDefault(_activeElement);
	
	var _contains = __webpack_require__(/*! dom-helpers/query/contains */ 6732);
	
	var _contains2 = _interopRequireDefault(_contains);
	
	var _getContainer = __webpack_require__(/*! ./utils/getContainer */ 6756);
	
	var _getContainer2 = _interopRequireDefault(_getContainer);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var modalManager = new _ModalManager2.default();
	
	/**
	 * Love them or hate them, `<Modal/>` provides a solid foundation for creating dialogs, lightboxes, or whatever else.
	 * The Modal component renders its `children` node in front of a backdrop component.
	 *
	 * The Modal offers a few helpful features over using just a `<Portal/>` component and some styles:
	 *
	 * - Manages dialog stacking when one-at-a-time just isn't enough.
	 * - Creates a backdrop, for disabling interaction below the modal.
	 * - It properly manages focus; moving to the modal content, and keeping it there until the modal is closed.
	 * - It disables scrolling of the page content while open.
	 * - Adds the appropriate ARIA roles are automatically.
	 * - Easily pluggable animations via a `<Transition/>` component.
	 *
	 * Note that, in the same way the backdrop element prevents users from clicking or interacting
	 * with the page content underneath the Modal, Screen readers also need to be signaled to not to
	 * interact with page content while the Modal is open. To do this, we use a common technique of applying
	 * the `aria-hidden='true'` attribute to the non-Modal elements in the Modal `container`. This means that for
	 * a Modal to be truly modal, it should have a `container` that is _outside_ your app's
	 * React hierarchy (such as the default: document.body).
	 */
	var Modal = _react2.default.createClass({
	  displayName: 'Modal',
	
	
	  propTypes: _extends({}, _Portal2.default.propTypes, {
	
	    /**
	     * Set the visibility of the Modal
	     */
	    show: _react2.default.PropTypes.bool,
	
	    /**
	     * A Node, Component instance, or function that returns either. The Modal is appended to it's container element.
	     *
	     * For the sake of assistive technologies, the container should usually be the document body, so that the rest of the
	     * page content can be placed behind a virtual backdrop as well as a visual one.
	     */
	    container: _react2.default.PropTypes.oneOfType([_componentOrElement2.default, _react2.default.PropTypes.func]),
	
	    /**
	     * A callback fired when the Modal is opening.
	     */
	    onShow: _react2.default.PropTypes.func,
	
	    /**
	     * A callback fired when either the backdrop is clicked, or the escape key is pressed.
	     *
	     * The `onHide` callback only signals intent from the Modal,
	     * you must actually set the `show` prop to `false` for the Modal to close.
	     */
	    onHide: _react2.default.PropTypes.func,
	
	    /**
	     * Include a backdrop component.
	     */
	    backdrop: _react2.default.PropTypes.oneOfType([_react2.default.PropTypes.bool, _react2.default.PropTypes.oneOf(['static'])]),
	
	    /**
	     * A function that returns a backdrop component. Useful for custom
	     * backdrop rendering.
	     *
	     * ```js
	     *  renderBackdrop={props => <MyBackdrop {...props} />}
	     * ```
	     */
	    renderBackdrop: _react2.default.PropTypes.func,
	
	    /**
	     * A callback fired when the escape key, if specified in `keyboard`, is pressed.
	     */
	    onEscapeKeyUp: _react2.default.PropTypes.func,
	
	    /**
	     * A callback fired when the backdrop, if specified, is clicked.
	     */
	    onBackdropClick: _react2.default.PropTypes.func,
	
	    /**
	     * A style object for the backdrop component.
	     */
	    backdropStyle: _react2.default.PropTypes.object,
	
	    /**
	     * A css class or classes for the backdrop component.
	     */
	    backdropClassName: _react2.default.PropTypes.string,
	
	    /**
	     * A css class or set of classes applied to the modal container when the modal is open,
	     * and removed when it is closed.
	     */
	    containerClassName: _react2.default.PropTypes.string,
	
	    /**
	     * Close the modal when escape key is pressed
	     */
	    keyboard: _react2.default.PropTypes.bool,
	
	    /**
	     * A `<Transition/>` component to use for the dialog and backdrop components.
	     */
	    transition: _elementType2.default,
	
	    /**
	     * The `timeout` of the dialog transition if specified. This number is used to ensure that
	     * transition callbacks are always fired, even if browser transition events are canceled.
	     *
	     * See the Transition `timeout` prop for more infomation.
	     */
	    dialogTransitionTimeout: _react2.default.PropTypes.number,
	
	    /**
	     * The `timeout` of the backdrop transition if specified. This number is used to
	     * ensure that transition callbacks are always fired, even if browser transition events are canceled.
	     *
	     * See the Transition `timeout` prop for more infomation.
	     */
	    backdropTransitionTimeout: _react2.default.PropTypes.number,
	
	    /**
	     * When `true` The modal will automatically shift focus to itself when it opens, and
	     * replace it to the last focused element when it closes. This also
	     * works correctly with any Modal children that have the `autoFocus` prop.
	     *
	     * Generally this should never be set to `false` as it makes the Modal less
	     * accessible to assistive technologies, like screen readers.
	     */
	    autoFocus: _react2.default.PropTypes.bool,
	
	    /**
	     * When `true` The modal will prevent focus from leaving the Modal while open.
	     *
	     * Generally this should never be set to `false` as it makes the Modal less
	     * accessible to assistive technologies, like screen readers.
	     */
	    enforceFocus: _react2.default.PropTypes.bool,
	
	    /**
	     * When `true` The modal will restore focus to previously focused element once
	     * modal is hidden
	     */
	    restoreFocus: _react2.default.PropTypes.bool,
	
	    /**
	     * Callback fired before the Modal transitions in
	     */
	    onEnter: _react2.default.PropTypes.func,
	
	    /**
	     * Callback fired as the Modal begins to transition in
	     */
	    onEntering: _react2.default.PropTypes.func,
	
	    /**
	     * Callback fired after the Modal finishes transitioning in
	     */
	    onEntered: _react2.default.PropTypes.func,
	
	    /**
	     * Callback fired right before the Modal transitions out
	     */
	    onExit: _react2.default.PropTypes.func,
	
	    /**
	     * Callback fired as the Modal begins to transition out
	     */
	    onExiting: _react2.default.PropTypes.func,
	
	    /**
	     * Callback fired after the Modal finishes transitioning out
	     */
	    onExited: _react2.default.PropTypes.func,
	
	    /**
	     * A ModalManager instance used to track and manage the state of open
	     * Modals. Useful when customizing how modals interact within a container
	     */
	    manager: _react2.default.PropTypes.object.isRequired
	  }),
	
	  getDefaultProps: function getDefaultProps() {
	    var noop = function noop() {};
	
	    return {
	      show: false,
	      backdrop: true,
	      keyboard: true,
	      autoFocus: true,
	      enforceFocus: true,
	      restoreFocus: true,
	      onHide: noop,
	      manager: modalManager,
	      renderBackdrop: function renderBackdrop(props) {
	        return _react2.default.createElement('div', props);
	      }
	    };
	  },
	  omitProps: function omitProps(props, propTypes) {
	
	    var keys = Object.keys(props);
	    var newProps = {};
	    keys.map(function (prop) {
	      if (!Object.prototype.hasOwnProperty.call(propTypes, prop)) {
	        newProps[prop] = props[prop];
	      }
	    });
	
	    return newProps;
	  },
	  getInitialState: function getInitialState() {
	    return { exited: !this.props.show };
	  },
	  render: function render() {
	    var _props = this.props,
	        show = _props.show,
	        container = _props.container,
	        children = _props.children,
	        Transition = _props.transition,
	        backdrop = _props.backdrop,
	        dialogTransitionTimeout = _props.dialogTransitionTimeout,
	        className = _props.className,
	        style = _props.style,
	        onExit = _props.onExit,
	        onExiting = _props.onExiting,
	        onEnter = _props.onEnter,
	        onEntering = _props.onEntering,
	        onEntered = _props.onEntered;
	
	
	    var dialog = _react2.default.Children.only(children);
	    var filteredProps = this.omitProps(this.props, Modal.propTypes);
	
	    var mountModal = show || Transition && !this.state.exited;
	    if (!mountModal) {
	      return null;
	    }
	
	    var _dialog$props = dialog.props,
	        role = _dialog$props.role,
	        tabIndex = _dialog$props.tabIndex;
	
	
	    if (role === undefined || tabIndex === undefined) {
	      dialog = (0, _react.cloneElement)(dialog, {
	        role: role === undefined ? 'document' : role,
	        tabIndex: tabIndex == null ? '-1' : tabIndex
	      });
	    }
	
	    if (Transition) {
	      dialog = _react2.default.createElement(
	        Transition,
	        {
	          transitionAppear: true,
	          unmountOnExit: true,
	          'in': show,
	          timeout: dialogTransitionTimeout,
	          onExit: onExit,
	          onExiting: onExiting,
	          onExited: this.handleHidden,
	          onEnter: onEnter,
	          onEntering: onEntering,
	          onEntered: onEntered
	        },
	        dialog
	      );
	    }
	
	    return _react2.default.createElement(
	      _Portal2.default,
	      {
	        ref: this.setMountNode,
	        container: container
	      },
	      _react2.default.createElement(
	        'div',
	        _extends({
	          ref: 'modal',
	          role: role || 'dialog'
	        }, filteredProps, {
	          style: style,
	          className: className
	        }),
	        backdrop && this.renderBackdrop(),
	        dialog
	      )
	    );
	  },
	  renderBackdrop: function renderBackdrop() {
	    var _this = this;
	
	    var _props2 = this.props,
	        backdropStyle = _props2.backdropStyle,
	        backdropClassName = _props2.backdropClassName,
	        renderBackdrop = _props2.renderBackdrop,
	        Transition = _props2.transition,
	        backdropTransitionTimeout = _props2.backdropTransitionTimeout;
	
	
	    var backdropRef = function backdropRef(ref) {
	      return _this.backdrop = ref;
	    };
	
	    var backdrop = _react2.default.createElement('div', {
	      ref: backdropRef,
	      style: this.props.backdropStyle,
	      className: this.props.backdropClassName,
	      onClick: this.handleBackdropClick
	    });
	
	    if (Transition) {
	      backdrop = _react2.default.createElement(
	        Transition,
	        { transitionAppear: true,
	          'in': this.props.show,
	          timeout: backdropTransitionTimeout
	        },
	        renderBackdrop({
	          ref: backdropRef,
	          style: backdropStyle,
	          className: backdropClassName,
	          onClick: this.handleBackdropClick
	        })
	      );
	    }
	
	    return backdrop;
	  },
	  componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
	    if (nextProps.show) {
	      this.setState({ exited: false });
	    } else if (!nextProps.transition) {
	      // Otherwise let handleHidden take care of marking exited.
	      this.setState({ exited: true });
	    }
	  },
	  componentWillUpdate: function componentWillUpdate(nextProps) {
	    if (!this.props.show && nextProps.show) {
	      this.checkForFocus();
	    }
	  },
	  componentDidMount: function componentDidMount() {
	    if (this.props.show) {
	      this.onShow();
	    }
	  },
	  componentDidUpdate: function componentDidUpdate(prevProps) {
	    var transition = this.props.transition;
	
	
	    if (prevProps.show && !this.props.show && !transition) {
	      // Otherwise handleHidden will call this.
	      this.onHide();
	    } else if (!prevProps.show && this.props.show) {
	      this.onShow();
	    }
	  },
	  componentWillUnmount: function componentWillUnmount() {
	    var _props3 = this.props,
	        show = _props3.show,
	        transition = _props3.transition;
	
	
	    if (show || transition && !this.state.exited) {
	      this.onHide();
	    }
	  },
	  onShow: function onShow() {
	    var doc = (0, _ownerDocument2.default)(this);
	    var container = (0, _getContainer2.default)(this.props.container, doc.body);
	
	    this.props.manager.add(this, container, this.props.containerClassName);
	
	    this._onDocumentKeyupListener = (0, _addEventListener2.default)(doc, 'keyup', this.handleDocumentKeyUp);
	
	    this._onFocusinListener = (0, _addFocusListener2.default)(this.enforceFocus);
	
	    this.focus();
	
	    if (this.props.onShow) {
	      this.props.onShow();
	    }
	  },
	  onHide: function onHide() {
	    this.props.manager.remove(this);
	
	    this._onDocumentKeyupListener.remove();
	
	    this._onFocusinListener.remove();
	
	    if (this.props.restoreFocus) {
	      this.restoreLastFocus();
	    }
	  },
	  setMountNode: function setMountNode(ref) {
	    this.mountNode = ref ? ref.getMountNode() : ref;
	  },
	  handleHidden: function handleHidden() {
	    this.setState({ exited: true });
	    this.onHide();
	
	    if (this.props.onExited) {
	      var _props4;
	
	      (_props4 = this.props).onExited.apply(_props4, arguments);
	    }
	  },
	  handleBackdropClick: function handleBackdropClick(e) {
	    if (e.target !== e.currentTarget) {
	      return;
	    }
	
	    if (this.props.onBackdropClick) {
	      this.props.onBackdropClick(e);
	    }
	
	    if (this.props.backdrop === true) {
	      this.props.onHide();
	    }
	  },
	  handleDocumentKeyUp: function handleDocumentKeyUp(e) {
	    if (this.props.keyboard && e.keyCode === 27 && this.isTopModal()) {
	      if (this.props.onEscapeKeyUp) {
	        this.props.onEscapeKeyUp(e);
	      }
	      this.props.onHide();
	    }
	  },
	  checkForFocus: function checkForFocus() {
	    if (_inDOM2.default) {
	      this.lastFocus = (0, _activeElement2.default)();
	    }
	  },
	  focus: function focus() {
	    var autoFocus = this.props.autoFocus;
	    var modalContent = this.getDialogElement();
	    var current = (0, _activeElement2.default)((0, _ownerDocument2.default)(this));
	    var focusInModal = current && (0, _contains2.default)(modalContent, current);
	
	    if (modalContent && autoFocus && !focusInModal) {
	      this.lastFocus = current;
	
	      if (!modalContent.hasAttribute('tabIndex')) {
	        modalContent.setAttribute('tabIndex', -1);
	        (0, _warning2.default)(false, 'The modal content node does not accept focus. ' + 'For the benefit of assistive technologies, the tabIndex of the node is being set to "-1".');
	      }
	
	      modalContent.focus();
	    }
	  },
	  restoreLastFocus: function restoreLastFocus() {
	    // Support: <=IE11 doesn't support `focus()` on svg elements (RB: #917)
	    if (this.lastFocus && this.lastFocus.focus) {
	      this.lastFocus.focus();
	      this.lastFocus = null;
	    }
	  },
	  enforceFocus: function enforceFocus() {
	    var enforceFocus = this.props.enforceFocus;
	
	
	    if (!enforceFocus || !this.isMounted() || !this.isTopModal()) {
	      return;
	    }
	
	    var active = (0, _activeElement2.default)((0, _ownerDocument2.default)(this));
	    var modal = this.getDialogElement();
	
	    if (modal && modal !== active && !(0, _contains2.default)(modal, active)) {
	      modal.focus();
	    }
	  },
	
	
	  //instead of a ref, which might conflict with one the parent applied.
	  getDialogElement: function getDialogElement() {
	    var node = this.refs.modal;
	    return node && node.lastChild;
	  },
	  isTopModal: function isTopModal() {
	    return this.props.manager.isTopModal(this);
	  }
	});
	
	Modal.Manager = _ModalManager2.default;
	
	exports.default = Modal;
	module.exports = exports['default'];

/***/ },

/***/ 6754:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/componentOrElement.js ***!
  \***********************************************************************************************************************************************/
[7522, 5963, 6704],

/***/ 6755:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Portal.js ***!
  \*********************************************************************************************************************************/
[7523, 5963, 5993, 6754, 6737, 6756],

/***/ 6756:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/getContainer.js ***!
  \*********************************************************************************************************************************************/
[7524, 5993],

/***/ 6757:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/ModalManager.js ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	var _style = __webpack_require__(/*! dom-helpers/style */ 6758);
	
	var _style2 = _interopRequireDefault(_style);
	
	var _class = __webpack_require__(/*! dom-helpers/class */ 6767);
	
	var _class2 = _interopRequireDefault(_class);
	
	var _scrollbarSize = __webpack_require__(/*! dom-helpers/util/scrollbarSize */ 6771);
	
	var _scrollbarSize2 = _interopRequireDefault(_scrollbarSize);
	
	var _isOverflowing = __webpack_require__(/*! ./utils/isOverflowing */ 6772);
	
	var _isOverflowing2 = _interopRequireDefault(_isOverflowing);
	
	var _manageAriaHidden = __webpack_require__(/*! ./utils/manageAriaHidden */ 6774);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function findIndexOf(arr, cb) {
	  var idx = -1;
	  arr.some(function (d, i) {
	    if (cb(d, i)) {
	      idx = i;
	      return true;
	    }
	  });
	  return idx;
	}
	
	function findContainer(data, modal) {
	  return findIndexOf(data, function (d) {
	    return d.modals.indexOf(modal) !== -1;
	  });
	}
	
	function setContainerStyle(state, container) {
	  var style = { overflow: 'hidden' };
	
	  // we are only interested in the actual `style` here
	  // becasue we will override it
	  state.style = {
	    overflow: container.style.overflow,
	    paddingRight: container.style.paddingRight
	  };
	
	  if (state.overflowing) {
	    // use computed style, here to get the real padding
	    // to add our scrollbar width
	    style.paddingRight = parseInt((0, _style2.default)(container, 'paddingRight') || 0, 10) + (0, _scrollbarSize2.default)() + 'px';
	  }
	
	  (0, _style2.default)(container, style);
	}
	
	function removeContainerStyle(_ref, container) {
	  var style = _ref.style;
	
	
	  Object.keys(style).forEach(function (key) {
	    return container.style[key] = style[key];
	  });
	}
	/**
	 * Proper state managment for containers and the modals in those containers.
	 *
	 * @internal Used by the Modal to ensure proper styling of containers.
	 */
	
	var ModalManager = function () {
	  function ModalManager() {
	    var _ref2 = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {},
	        _ref2$hideSiblingNode = _ref2.hideSiblingNodes,
	        hideSiblingNodes = _ref2$hideSiblingNode === undefined ? true : _ref2$hideSiblingNode,
	        _ref2$handleContainer = _ref2.handleContainerOverflow,
	        handleContainerOverflow = _ref2$handleContainer === undefined ? true : _ref2$handleContainer;
	
	    _classCallCheck(this, ModalManager);
	
	    this.hideSiblingNodes = hideSiblingNodes;
	    this.handleContainerOverflow = handleContainerOverflow;
	    this.modals = [];
	    this.containers = [];
	    this.data = [];
	  }
	
	  _createClass(ModalManager, [{
	    key: 'add',
	    value: function add(modal, container, className) {
	      var modalIdx = this.modals.indexOf(modal);
	      var containerIdx = this.containers.indexOf(container);
	
	      if (modalIdx !== -1) {
	        return modalIdx;
	      }
	
	      modalIdx = this.modals.length;
	      this.modals.push(modal);
	
	      if (this.hideSiblingNodes) {
	        (0, _manageAriaHidden.hideSiblings)(container, modal.mountNode);
	      }
	
	      if (containerIdx !== -1) {
	        this.data[containerIdx].modals.push(modal);
	        return modalIdx;
	      }
	
	      var data = {
	        modals: [modal],
	        //right now only the first modal of a container will have its classes applied
	        classes: className ? className.split(/\s+/) : [],
	
	        overflowing: (0, _isOverflowing2.default)(container)
	      };
	
	      if (this.handleContainerOverflow) {
	        setContainerStyle(data, container);
	      }
	
	      data.classes.forEach(_class2.default.addClass.bind(null, container));
	
	      this.containers.push(container);
	      this.data.push(data);
	
	      return modalIdx;
	    }
	  }, {
	    key: 'remove',
	    value: function remove(modal) {
	      var modalIdx = this.modals.indexOf(modal);
	
	      if (modalIdx === -1) {
	        return;
	      }
	
	      var containerIdx = findContainer(this.data, modal);
	      var data = this.data[containerIdx];
	      var container = this.containers[containerIdx];
	
	      data.modals.splice(data.modals.indexOf(modal), 1);
	
	      this.modals.splice(modalIdx, 1);
	
	      // if that was the last modal in a container,
	      // clean up the container
	      if (data.modals.length === 0) {
	        data.classes.forEach(_class2.default.removeClass.bind(null, container));
	
	        if (this.handleContainerOverflow) {
	          removeContainerStyle(data, container);
	        }
	
	        if (this.hideSiblingNodes) {
	          (0, _manageAriaHidden.showSiblings)(container, modal.mountNode);
	        }
	        this.containers.splice(containerIdx, 1);
	        this.data.splice(containerIdx, 1);
	      } else if (this.hideSiblingNodes) {
	        //otherwise make sure the next top modal is visible to a SR
	        (0, _manageAriaHidden.ariaHidden)(false, data.modals[data.modals.length - 1].mountNode);
	      }
	    }
	  }, {
	    key: 'isTopModal',
	    value: function isTopModal(modal) {
	      return !!this.modals.length && this.modals[this.modals.length - 1] === modal;
	    }
	  }]);
	
	  return ModalManager;
	}();
	
	exports.default = ModalManager;
	module.exports = exports['default'];

/***/ },

/***/ 6758:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/style/index.js ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = style;
	
	var _camelizeStyle = __webpack_require__(/*! ../util/camelizeStyle */ 6759);
	
	var _camelizeStyle2 = _interopRequireDefault(_camelizeStyle);
	
	var _hyphenateStyle = __webpack_require__(/*! ../util/hyphenateStyle */ 6761);
	
	var _hyphenateStyle2 = _interopRequireDefault(_hyphenateStyle);
	
	var _getComputedStyle2 = __webpack_require__(/*! ./getComputedStyle */ 6763);
	
	var _getComputedStyle3 = _interopRequireDefault(_getComputedStyle2);
	
	var _removeStyle = __webpack_require__(/*! ./removeStyle */ 6764);
	
	var _removeStyle2 = _interopRequireDefault(_removeStyle);
	
	var _properties = __webpack_require__(/*! ../transition/properties */ 6765);
	
	var _isTransform = __webpack_require__(/*! ../transition/isTransform */ 6766);
	
	var _isTransform2 = _interopRequireDefault(_isTransform);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function style(node, property, value) {
	  var css = '';
	  var transforms = '';
	  var props = property;
	
	  if (typeof property === 'string') {
	    if (value === undefined) {
	      return node.style[(0, _camelizeStyle2.default)(property)] || (0, _getComputedStyle3.default)(node).getPropertyValue((0, _hyphenateStyle2.default)(property));
	    } else {
	      (props = {})[property] = value;
	    }
	  }
	
	  Object.keys(props).forEach(function (key) {
	    var value = props[key];
	    if (!value && value !== 0) {
	      (0, _removeStyle2.default)(node, (0, _hyphenateStyle2.default)(key));
	    } else if ((0, _isTransform2.default)(key)) {
	      transforms += key + '(' + value + ') ';
	    } else {
	      css += (0, _hyphenateStyle2.default)(key) + ': ' + value + ';';
	    }
	  });
	
	  if (transforms) {
	    css += _properties.transform + ': ' + transforms + ';';
	  }
	
	  node.style.cssText += ';' + css;
	}
	module.exports = exports['default'];

/***/ },

/***/ 6759:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/camelizeStyle.js ***!
  \*******************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = camelizeStyleName;
	
	var _camelize = __webpack_require__(/*! ./camelize */ 6760);
	
	var _camelize2 = _interopRequireDefault(_camelize);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var msPattern = /^-ms-/; /**
	                          * Copyright 2014-2015, Facebook, Inc.
	                          * All rights reserved.
	                          * https://github.com/facebook/react/blob/2aeb8a2a6beb00617a4217f7f8284924fa2ad819/src/vendor/core/camelizeStyleName.js
	                          */
	function camelizeStyleName(string) {
	  return (0, _camelize2.default)(string.replace(msPattern, 'ms-'));
	}
	module.exports = exports['default'];

/***/ },

/***/ 6760:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/camelize.js ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = camelize;
	var rHyphen = /-(.)/g;
	
	function camelize(string) {
	  return string.replace(rHyphen, function (_, chr) {
	    return chr.toUpperCase();
	  });
	}
	module.exports = exports["default"];

/***/ },

/***/ 6761:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/hyphenateStyle.js ***!
  \********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = hyphenateStyleName;
	
	var _hyphenate = __webpack_require__(/*! ./hyphenate */ 6762);
	
	var _hyphenate2 = _interopRequireDefault(_hyphenate);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var msPattern = /^ms-/; /**
	                         * Copyright 2013-2014, Facebook, Inc.
	                         * All rights reserved.
	                         * https://github.com/facebook/react/blob/2aeb8a2a6beb00617a4217f7f8284924fa2ad819/src/vendor/core/hyphenateStyleName.js
	                         */
	
	function hyphenateStyleName(string) {
	  return (0, _hyphenate2.default)(string).replace(msPattern, '-ms-');
	}
	module.exports = exports['default'];

/***/ },

/***/ 6762:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/hyphenate.js ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = hyphenate;
	
	var rUpper = /([A-Z])/g;
	
	function hyphenate(string) {
	  return string.replace(rUpper, '-$1').toLowerCase();
	}
	module.exports = exports['default'];

/***/ },

/***/ 6763:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/style/getComputedStyle.js ***!
  \***********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = _getComputedStyle;
	
	var _camelizeStyle = __webpack_require__(/*! ../util/camelizeStyle */ 6759);
	
	var _camelizeStyle2 = _interopRequireDefault(_camelizeStyle);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var rposition = /^(top|right|bottom|left)$/;
	var rnumnonpx = /^([+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|))(?!px)[a-z%]+$/i;
	
	function _getComputedStyle(node) {
	  if (!node) throw new TypeError('No Element passed to `getComputedStyle()`');
	  var doc = node.ownerDocument;
	
	  return 'defaultView' in doc ? doc.defaultView.opener ? node.ownerDocument.defaultView.getComputedStyle(node, null) : window.getComputedStyle(node, null) : {
	    //ie 8 "magic" from: https://github.com/jquery/jquery/blob/1.11-stable/src/css/curCSS.js#L72
	    getPropertyValue: function getPropertyValue(prop) {
	      var style = node.style;
	
	      prop = (0, _camelizeStyle2.default)(prop);
	
	      if (prop == 'float') prop = 'styleFloat';
	
	      var current = node.currentStyle[prop] || null;
	
	      if (current == null && style && style[prop]) current = style[prop];
	
	      if (rnumnonpx.test(current) && !rposition.test(prop)) {
	        // Remember the original values
	        var left = style.left;
	        var runStyle = node.runtimeStyle;
	        var rsLeft = runStyle && runStyle.left;
	
	        // Put in the new values to get a computed value out
	        if (rsLeft) runStyle.left = node.currentStyle.left;
	
	        style.left = prop === 'fontSize' ? '1em' : current;
	        current = style.pixelLeft + 'px';
	
	        // Revert the changed values
	        style.left = left;
	        if (rsLeft) runStyle.left = rsLeft;
	      }
	
	      return current;
	    }
	  };
	}
	module.exports = exports['default'];

/***/ },

/***/ 6764:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/style/removeStyle.js ***!
  \******************************************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = removeStyle;
	function removeStyle(node, key) {
	  return 'removeProperty' in node.style ? node.style.removeProperty(key) : node.style.removeAttribute(key);
	}
	module.exports = exports['default'];

/***/ },

/***/ 6765:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/transition/properties.js ***!
  \**********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.animationEnd = exports.animationDelay = exports.animationTiming = exports.animationDuration = exports.animationName = exports.transitionEnd = exports.transitionDuration = exports.transitionDelay = exports.transitionTiming = exports.transitionProperty = exports.transform = undefined;
	
	var _inDOM = __webpack_require__(/*! ../util/inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var transform = 'transform';
	var prefix = void 0,
	    transitionEnd = void 0,
	    animationEnd = void 0;
	var transitionProperty = void 0,
	    transitionDuration = void 0,
	    transitionTiming = void 0,
	    transitionDelay = void 0;
	var animationName = void 0,
	    animationDuration = void 0,
	    animationTiming = void 0,
	    animationDelay = void 0;
	
	if (_inDOM2.default) {
	  var _getTransitionPropert = getTransitionProperties();
	
	  prefix = _getTransitionPropert.prefix;
	  exports.transitionEnd = transitionEnd = _getTransitionPropert.transitionEnd;
	  exports.animationEnd = animationEnd = _getTransitionPropert.animationEnd;
	
	
	  exports.transform = transform = prefix + '-' + transform;
	  exports.transitionProperty = transitionProperty = prefix + '-transition-property';
	  exports.transitionDuration = transitionDuration = prefix + '-transition-duration';
	  exports.transitionDelay = transitionDelay = prefix + '-transition-delay';
	  exports.transitionTiming = transitionTiming = prefix + '-transition-timing-function';
	
	  exports.animationName = animationName = prefix + '-animation-name';
	  exports.animationDuration = animationDuration = prefix + '-animation-duration';
	  exports.animationTiming = animationTiming = prefix + '-animation-delay';
	  exports.animationDelay = animationDelay = prefix + '-animation-timing-function';
	}
	
	exports.transform = transform;
	exports.transitionProperty = transitionProperty;
	exports.transitionTiming = transitionTiming;
	exports.transitionDelay = transitionDelay;
	exports.transitionDuration = transitionDuration;
	exports.transitionEnd = transitionEnd;
	exports.animationName = animationName;
	exports.animationDuration = animationDuration;
	exports.animationTiming = animationTiming;
	exports.animationDelay = animationDelay;
	exports.animationEnd = animationEnd;
	exports.default = {
	  transform: transform,
	  end: transitionEnd,
	  property: transitionProperty,
	  timing: transitionTiming,
	  delay: transitionDelay,
	  duration: transitionDuration
	};
	
	
	function getTransitionProperties() {
	  var style = document.createElement('div').style;
	
	  var vendorMap = {
	    O: function O(e) {
	      return 'o' + e.toLowerCase();
	    },
	    Moz: function Moz(e) {
	      return e.toLowerCase();
	    },
	    Webkit: function Webkit(e) {
	      return 'webkit' + e;
	    },
	    ms: function ms(e) {
	      return 'MS' + e;
	    }
	  };
	
	  var vendors = Object.keys(vendorMap);
	
	  var transitionEnd = void 0,
	      animationEnd = void 0;
	  var prefix = '';
	
	  for (var i = 0; i < vendors.length; i++) {
	    var vendor = vendors[i];
	
	    if (vendor + 'TransitionProperty' in style) {
	      prefix = '-' + vendor.toLowerCase();
	      transitionEnd = vendorMap[vendor]('TransitionEnd');
	      animationEnd = vendorMap[vendor]('AnimationEnd');
	      break;
	    }
	  }
	
	  if (!transitionEnd && 'transitionProperty' in style) transitionEnd = 'transitionend';
	
	  if (!animationEnd && 'animationName' in style) animationEnd = 'animationend';
	
	  style = null;
	
	  return { animationEnd: animationEnd, transitionEnd: transitionEnd, prefix: prefix };
	}

/***/ },

/***/ 6766:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/transition/isTransform.js ***!
  \***********************************************************************************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = isTransform;
	var supportedTransforms = /^((translate|rotate|scale)(X|Y|Z|3d)?|matrix(3d)?|perspective|skew(X|Y)?)$/i;
	
	function isTransform(property) {
	  return !!(property && supportedTransforms.test(property));
	}
	module.exports = exports["default"];

/***/ },

/***/ 6767:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/class/index.js ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.hasClass = exports.removeClass = exports.addClass = undefined;
	
	var _addClass = __webpack_require__(/*! ./addClass */ 6768);
	
	var _addClass2 = _interopRequireDefault(_addClass);
	
	var _removeClass = __webpack_require__(/*! ./removeClass */ 6770);
	
	var _removeClass2 = _interopRequireDefault(_removeClass);
	
	var _hasClass = __webpack_require__(/*! ./hasClass */ 6769);
	
	var _hasClass2 = _interopRequireDefault(_hasClass);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.addClass = _addClass2.default;
	exports.removeClass = _removeClass2.default;
	exports.hasClass = _hasClass2.default;
	exports.default = { addClass: _addClass2.default, removeClass: _removeClass2.default, hasClass: _hasClass2.default };

/***/ },

/***/ 6768:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/class/addClass.js ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = addClass;
	
	var _hasClass = __webpack_require__(/*! ./hasClass */ 6769);
	
	var _hasClass2 = _interopRequireDefault(_hasClass);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function addClass(element, className) {
	  if (element.classList) element.classList.add(className);else if (!(0, _hasClass2.default)(element)) element.className = element.className + ' ' + className;
	}
	module.exports = exports['default'];

/***/ },

/***/ 6769:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/class/hasClass.js ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = hasClass;
	function hasClass(element, className) {
	  if (element.classList) return !!className && element.classList.contains(className);else return (" " + element.className + " ").indexOf(" " + className + " ") !== -1;
	}
	module.exports = exports["default"];

/***/ },

/***/ 6770:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/class/removeClass.js ***!
  \******************************************************************************************************************************************************/
1139,

/***/ 6771:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/util/scrollbarSize.js ***!
  \*******************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	exports.default = function (recalc) {
	  if (!size || recalc) {
	    if (_inDOM2.default) {
	      var scrollDiv = document.createElement('div');
	
	      scrollDiv.style.position = 'absolute';
	      scrollDiv.style.top = '-9999px';
	      scrollDiv.style.width = '50px';
	      scrollDiv.style.height = '50px';
	      scrollDiv.style.overflow = 'scroll';
	
	      document.body.appendChild(scrollDiv);
	      size = scrollDiv.offsetWidth - scrollDiv.clientWidth;
	      document.body.removeChild(scrollDiv);
	    }
	  }
	
	  return size;
	};
	
	var _inDOM = __webpack_require__(/*! ./inDOM */ 6733);
	
	var _inDOM2 = _interopRequireDefault(_inDOM);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var size = void 0;
	
	module.exports = exports['default'];

/***/ },

/***/ 6772:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/isOverflowing.js ***!
  \**********************************************************************************************************************************************/
[7525, 6773, 6738],

/***/ 6773:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/query/isWindow.js ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = getWindow;
	function getWindow(node) {
	  return node === node.window ? node : node.nodeType === 9 ? node.defaultView || node.parentWindow : false;
	}
	module.exports = exports["default"];

/***/ },

/***/ 6774:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/manageAriaHidden.js ***!
  \*************************************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.ariaHidden = ariaHidden;
	exports.hideSiblings = hideSiblings;
	exports.showSiblings = showSiblings;
	
	var BLACKLIST = ['template', 'script', 'style'];
	
	var isHidable = function isHidable(_ref) {
	  var nodeType = _ref.nodeType,
	      tagName = _ref.tagName;
	  return nodeType === 1 && BLACKLIST.indexOf(tagName.toLowerCase()) === -1;
	};
	
	var siblings = function siblings(container, mount, cb) {
	  mount = [].concat(mount);
	
	  [].forEach.call(container.children, function (node) {
	    if (mount.indexOf(node) === -1 && isHidable(node)) {
	      cb(node);
	    }
	  });
	};
	
	function ariaHidden(show, node) {
	  if (!node) {
	    return;
	  }
	  if (show) {
	    node.setAttribute('aria-hidden', 'true');
	  } else {
	    node.removeAttribute('aria-hidden');
	  }
	}
	
	function hideSiblings(container, mountNode) {
	  siblings(container, mountNode, function (node) {
	    return ariaHidden(true, node);
	  });
	}
	
	function showSiblings(container, mountNode) {
	  siblings(container, mountNode, function (node) {
	    return ariaHidden(false, node);
	  });
	}

/***/ },

/***/ 6775:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/addFocusListener.js ***!
  \*************************************************************************************************************************************************/
1143,

/***/ 6776:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/dom-helpers/activeElement.js ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = activeElement;
	
	var _ownerDocument = __webpack_require__(/*! ./ownerDocument */ 6738);
	
	var _ownerDocument2 = _interopRequireDefault(_ownerDocument);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function activeElement() {
	  var doc = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : (0, _ownerDocument2.default)();
	
	  try {
	    return doc.activeElement;
	  } catch (e) {/* ie throws if no active element */}
	}
	module.exports = exports['default'];

/***/ },

/***/ 6777:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Fade.js ***!
  \**************************************************************************************************************/
[7498, 6613, 6651, 6652, 6688, 6696, 5963, 6778],

/***/ 6778:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Transition.js ***!
  \*************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.EXITING = exports.ENTERED = exports.ENTERING = exports.EXITED = exports.UNMOUNTED = undefined;
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	var _classnames = __webpack_require__(/*! classnames */ 6696);
	
	var _classnames2 = _interopRequireDefault(_classnames);
	
	var _on = __webpack_require__(/*! dom-helpers/events/on */ 6735);
	
	var _on2 = _interopRequireDefault(_on);
	
	var _properties = __webpack_require__(/*! dom-helpers/transition/properties */ 6765);
	
	var _properties2 = _interopRequireDefault(_properties);
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 5993);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var transitionEndEvent = _properties2.default.end;
	
	var UNMOUNTED = exports.UNMOUNTED = 0;
	var EXITED = exports.EXITED = 1;
	var ENTERING = exports.ENTERING = 2;
	var ENTERED = exports.ENTERED = 3;
	var EXITING = exports.EXITING = 4;
	
	/**
	 * The Transition component lets you define and run css transitions with a simple declarative api.
	 * It works similar to React's own [CSSTransitionGroup](http://facebook.github.io/react/docs/animation.html#high-level-api-reactcsstransitiongroup)
	 * but is specifically optimized for transitioning a single child "in" or "out".
	 *
	 * You don't even need to use class based css transitions if you don't want to (but it is easiest).
	 * The extensive set of lifecycle callbacks means you have control over
	 * the transitioning now at each step of the way.
	 */
	
	var Transition = function (_React$Component) {
	  _inherits(Transition, _React$Component);
	
	  function Transition(props, context) {
	    _classCallCheck(this, Transition);
	
	    var _this = _possibleConstructorReturn(this, (Transition.__proto__ || Object.getPrototypeOf(Transition)).call(this, props, context));
	
	    var initialStatus = void 0;
	    _this.nextStatus = null;
	
	    if (props.in) {
	      if (props.transitionAppear) {
	        initialStatus = EXITED;
	        _this.nextStatus = ENTERING;
	      } else {
	        initialStatus = ENTERED;
	      }
	    } else {
	      if (props.unmountOnExit || props.mountOnEnter) {
	        initialStatus = UNMOUNTED;
	      } else {
	        initialStatus = EXITED;
	      }
	    }
	
	    _this.state = { status: initialStatus };
	
	    _this.nextCallback = null;
	    return _this;
	  }
	
	  _createClass(Transition, [{
	    key: 'componentDidMount',
	    value: function componentDidMount() {
	      this.updateStatus();
	    }
	  }, {
	    key: 'componentWillReceiveProps',
	    value: function componentWillReceiveProps(nextProps) {
	      var status = this.state.status;
	
	
	      if (nextProps.in) {
	        if (status === UNMOUNTED) {
	          this.setState({ status: EXITED });
	        }
	        if (status !== ENTERING && status !== ENTERED) {
	          this.nextStatus = ENTERING;
	        }
	      } else {
	        if (status === ENTERING || status === ENTERED) {
	          this.nextStatus = EXITING;
	        }
	      }
	    }
	  }, {
	    key: 'componentDidUpdate',
	    value: function componentDidUpdate() {
	      this.updateStatus();
	    }
	  }, {
	    key: 'componentWillUnmount',
	    value: function componentWillUnmount() {
	      this.cancelNextCallback();
	    }
	  }, {
	    key: 'updateStatus',
	    value: function updateStatus() {
	      var _this2 = this;
	
	      if (this.nextStatus !== null) {
	        // nextStatus will always be ENTERING or EXITING.
	        this.cancelNextCallback();
	        var node = _reactDom2.default.findDOMNode(this);
	
	        if (this.nextStatus === ENTERING) {
	          this.props.onEnter(node);
	
	          this.safeSetState({ status: ENTERING }, function () {
	            _this2.props.onEntering(node);
	
	            _this2.onTransitionEnd(node, function () {
	              _this2.safeSetState({ status: ENTERED }, function () {
	                _this2.props.onEntered(node);
	              });
	            });
	          });
	        } else {
	          this.props.onExit(node);
	
	          this.safeSetState({ status: EXITING }, function () {
	            _this2.props.onExiting(node);
	
	            _this2.onTransitionEnd(node, function () {
	              _this2.safeSetState({ status: EXITED }, function () {
	                _this2.props.onExited(node);
	              });
	            });
	          });
	        }
	
	        this.nextStatus = null;
	      } else if (this.props.unmountOnExit && this.state.status === EXITED) {
	        this.setState({ status: UNMOUNTED });
	      }
	    }
	  }, {
	    key: 'cancelNextCallback',
	    value: function cancelNextCallback() {
	      if (this.nextCallback !== null) {
	        this.nextCallback.cancel();
	        this.nextCallback = null;
	      }
	    }
	  }, {
	    key: 'safeSetState',
	    value: function safeSetState(nextState, callback) {
	      // This shouldn't be necessary, but there are weird race conditions with
	      // setState callbacks and unmounting in testing, so always make sure that
	      // we can cancel any pending setState callbacks after we unmount.
	      this.setState(nextState, this.setNextCallback(callback));
	    }
	  }, {
	    key: 'setNextCallback',
	    value: function setNextCallback(callback) {
	      var _this3 = this;
	
	      var active = true;
	
	      this.nextCallback = function (event) {
	        if (active) {
	          active = false;
	          _this3.nextCallback = null;
	
	          callback(event);
	        }
	      };
	
	      this.nextCallback.cancel = function () {
	        active = false;
	      };
	
	      return this.nextCallback;
	    }
	  }, {
	    key: 'onTransitionEnd',
	    value: function onTransitionEnd(node, handler) {
	      this.setNextCallback(handler);
	
	      if (node) {
	        (0, _on2.default)(node, transitionEndEvent, this.nextCallback);
	        setTimeout(this.nextCallback, this.props.timeout);
	      } else {
	        setTimeout(this.nextCallback, 0);
	      }
	    }
	  }, {
	    key: 'render',
	    value: function render() {
	      var status = this.state.status;
	      if (status === UNMOUNTED) {
	        return null;
	      }
	
	      var _props = this.props,
	          children = _props.children,
	          className = _props.className,
	          childProps = _objectWithoutProperties(_props, ['children', 'className']);
	
	      Object.keys(Transition.propTypes).forEach(function (key) {
	        return delete childProps[key];
	      });
	
	      var transitionClassName = void 0;
	      if (status === EXITED) {
	        transitionClassName = this.props.exitedClassName;
	      } else if (status === ENTERING) {
	        transitionClassName = this.props.enteringClassName;
	      } else if (status === ENTERED) {
	        transitionClassName = this.props.enteredClassName;
	      } else if (status === EXITING) {
	        transitionClassName = this.props.exitingClassName;
	      }
	
	      var child = _react2.default.Children.only(children);
	      return _react2.default.cloneElement(child, _extends({}, childProps, {
	        className: (0, _classnames2.default)(child.props.className, className, transitionClassName)
	      }));
	    }
	  }]);
	
	  return Transition;
	}(_react2.default.Component);
	
	Transition.propTypes = {
	  /**
	   * Show the component; triggers the enter or exit animation
	   */
	  in: _react2.default.PropTypes.bool,
	
	  /**
	   * Wait until the first "enter" transition to mount the component (add it to the DOM)
	   */
	  mountOnEnter: _react2.default.PropTypes.bool,
	
	  /**
	   * Unmount the component (remove it from the DOM) when it is not shown
	   */
	  unmountOnExit: _react2.default.PropTypes.bool,
	
	  /**
	   * Run the enter animation when the component mounts, if it is initially
	   * shown
	   */
	  transitionAppear: _react2.default.PropTypes.bool,
	
	  /**
	   * A Timeout for the animation, in milliseconds, to ensure that a node doesn't
	   * transition indefinately if the browser transitionEnd events are
	   * canceled or interrupted.
	   *
	   * By default this is set to a high number (5 seconds) as a failsafe. You should consider
	   * setting this to the duration of your animation (or a bit above it).
	   */
	  timeout: _react2.default.PropTypes.number,
	
	  /**
	   * CSS class or classes applied when the component is exited
	   */
	  exitedClassName: _react2.default.PropTypes.string,
	  /**
	   * CSS class or classes applied while the component is exiting
	   */
	  exitingClassName: _react2.default.PropTypes.string,
	  /**
	   * CSS class or classes applied when the component is entered
	   */
	  enteredClassName: _react2.default.PropTypes.string,
	  /**
	   * CSS class or classes applied while the component is entering
	   */
	  enteringClassName: _react2.default.PropTypes.string,
	
	  /**
	   * Callback fired before the "entering" classes are applied
	   */
	  onEnter: _react2.default.PropTypes.func,
	  /**
	   * Callback fired after the "entering" classes are applied
	   */
	  onEntering: _react2.default.PropTypes.func,
	  /**
	   * Callback fired after the "enter" classes are applied
	   */
	  onEntered: _react2.default.PropTypes.func,
	  /**
	   * Callback fired before the "exiting" classes are applied
	   */
	  onExit: _react2.default.PropTypes.func,
	  /**
	   * Callback fired after the "exiting" classes are applied
	   */
	  onExiting: _react2.default.PropTypes.func,
	  /**
	   * Callback fired after the "exited" classes are applied
	   */
	  onExited: _react2.default.PropTypes.func
	};
	
	// Name the function so it is clearer in the documentation
	function noop() {}
	
	Transition.displayName = 'Transition';
	
	Transition.defaultProps = {
	  in: false,
	  unmountOnExit: false,
	  transitionAppear: false,
	
	  timeout: 5000,
	
	  onEnter: noop,
	  onEntering: noop,
	  onEntered: noop,
	
	  onExit: noop,
	  onExiting: noop,
	  onExited: noop
	};
	
	exports.default = Transition;

/***/ },

/***/ 6779:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalBody.js ***!
  \*******************************************************************************************************************/
[7526, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6705, 6714],

/***/ 6780:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalDialog.js ***!
  \*********************************************************************************************************************/
[7527, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6714, 6719],

/***/ 6781:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalFooter.js ***!
  \*********************************************************************************************************************/
[7528, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6705, 6714],

/***/ 6782:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalHeader.js ***!
  \*********************************************************************************************************************/
[7529, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6714, 6739],

/***/ 6783:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalTitle.js ***!
  \********************************************************************************************************************/
[7530, 6613, 6612, 6651, 6652, 6688, 6696, 5963, 6705, 6714],

/***/ 6784:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/splitComponentProps.js ***!
  \***********************************************************************************************************************************/
[7497, 6715],

/***/ 6785:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/FlatFilter.jsx ***!
  \******************************************************************************************************************************/
[7848, 5963, 6786, 6794],

/***/ 6786:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/xor.js ***!
  \************************************************************************************************/
[7849, 6296, 6787, 6788, 6793],

/***/ 6787:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseRest.js ***!
  \******************************************************************************************************/
[7850, 6170, 6366, 6215],

/***/ 6788:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseXor.js ***!
  \*****************************************************************************************************/
[7851, 6789, 6364, 6791],

/***/ 6789:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseDifference.js ***!
  \************************************************************************************************************/
[7852, 6333, 6221, 6790, 6353, 6251, 6337],

/***/ 6790:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_arrayIncludesWith.js ***!
  \***************************************************************************************************************/
1642,

/***/ 6791:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseUniq.js ***!
  \******************************************************************************************************/
[7853, 6333, 6221, 6790, 6337, 6792, 6321],

/***/ 6792:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createSet.js ***!
  \*******************************************************************************************************/
[7854, 6308, 6201, 6321],

/***/ 6793:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/isArrayLikeObject.js ***!
  \**************************************************************************************************************/
[7855, 6257, 6207],

/***/ 6794:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/Filter.less ***!
  \***************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../../~/css-loader!./../../../../../../../../~/less-loader!./Filter.less */ 6795);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../../node_modules/css-loader/index.js!./../../../../../../../../node_modules/less-loader/index.js!./Filter.less", function() {
				var newContent = require("!!./../../../../../../../../node_modules/css-loader/index.js!./../../../../../../../../node_modules/less-loader/index.js!./Filter.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6795:
/*!**********************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/Filter.less ***!
  \**********************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaFilter {\n  padding-bottom: 1.25rem;\n}\n.gxaFilter .filterBody input {\n  margin: 0.2rem;\n}\n.gxaFilter .filterBody .groupName {\n  display: inline-block;\n  cursor: pointer;\n}\n.gxaFilter .filterBody .groupName:first-letter {\n  text-transform: capitalize;\n}\n.gxaFilter .filterBody .groupName:hover {\n  text-decoration: underline;\n}\n.gxaFilter .filterBody .groupName:indeterminate {\n  opacity: 0.75;\n}\n.gxaFilter .filterBody .options {\n  padding-left: 15px;\n  font-size: 85%;\n  -webkit-column-width: 180px;\n  -moz-column-width: 180px;\n  column-width: 180px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6796:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/filter/GroupingFilter.jsx ***!
  \**********************************************************************************************************************************/
[7857, 5963, 6708, 6709, 6744, 6786, 6794],

/***/ 6797:
/*!*********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/chartDataPropTypes.js ***!
  \*********************************************************************************************************************/
[7858, 5963, 6798],

/***/ 6798:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/experimentTypeUtils.js ***!
  \****************************************************************************************************************/
[7859, 5963],

/***/ 6799:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/DownloadButton.jsx ***!
  \*******************************************************************************************************************************************/
[7860, 5963, 6746, 6709, 6744, 6800, 6801, 6797],

/***/ 6800:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/Disclaimers.jsx ***!
  \****************************************************************************************************************************************/
[7861, 5963],

/***/ 6801:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/controls/download-button/Download.js ***!
  \************************************************************************************************************************************/
[7862, 6802, 6806],

/***/ 6802:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/range.js ***!
  \**************************************************************************************************/
[7863, 6803],

/***/ 6803:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_createRange.js ***!
  \*********************************************************************************************************/
[7864, 6804, 6805, 6233],

/***/ 6804:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_baseRange.js ***!
  \*******************************************************************************************************/
1656,

/***/ 6805:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/_isIterateeCall.js ***!
  \************************************************************************************************************/
[7865, 6240, 6257, 6228, 6182],

/***/ 6806:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/downloadjs/download.js ***!
  \*********************************************************************************************************/
832,

/***/ 6807:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.jsx ***!
  \********************************************************************************************************************************/
[7866, 5963, 6808, 6819],

/***/ 6808:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/index.js ***!
  \**************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	var _class, _class2, _temp;
	
	/* Decoraters */
	
	
	/* Utils */
	
	
	/* CSS */
	
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 5993);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	var _classnames = __webpack_require__(/*! classnames */ 6809);
	
	var _classnames2 = _interopRequireDefault(_classnames);
	
	var _staticMethods = __webpack_require__(/*! ./decorators/staticMethods */ 6810);
	
	var _staticMethods2 = _interopRequireDefault(_staticMethods);
	
	var _windowListener = __webpack_require__(/*! ./decorators/windowListener */ 6812);
	
	var _windowListener2 = _interopRequireDefault(_windowListener);
	
	var _customEvent = __webpack_require__(/*! ./decorators/customEvent */ 6813);
	
	var _customEvent2 = _interopRequireDefault(_customEvent);
	
	var _isCapture = __webpack_require__(/*! ./decorators/isCapture */ 6814);
	
	var _isCapture2 = _interopRequireDefault(_isCapture);
	
	var _getPosition = __webpack_require__(/*! ./utils/getPosition */ 6815);
	
	var _getPosition2 = _interopRequireDefault(_getPosition);
	
	var _getTipContent = __webpack_require__(/*! ./utils/getTipContent */ 6816);
	
	var _getTipContent2 = _interopRequireDefault(_getTipContent);
	
	var _aria = __webpack_require__(/*! ./utils/aria */ 6817);
	
	var _style = __webpack_require__(/*! ./style */ 6818);
	
	var _style2 = _interopRequireDefault(_style);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var ReactTooltip = (0, _staticMethods2.default)(_class = (0, _windowListener2.default)(_class = (0, _customEvent2.default)(_class = (0, _isCapture2.default)(_class = (_temp = _class2 = function (_Component) {
	  _inherits(ReactTooltip, _Component);
	
	  function ReactTooltip(props) {
	    _classCallCheck(this, ReactTooltip);
	
	    var _this = _possibleConstructorReturn(this, (ReactTooltip.__proto__ || Object.getPrototypeOf(ReactTooltip)).call(this, props));
	
	    _this.onClickOutsideFrozenTooltip = function (e) {
	      var _this$props = _this.props,
	          frozen = _this$props.frozen,
	          onClickOutside = _this$props.onClickOutside;
	      var currentTarget = _this.state.currentTarget;
	
	      if (frozen && onClickOutside && !_reactDom2.default.findDOMNode(_this).contains(e.target) && e.target !== currentTarget) {
	        onClickOutside();
	      }
	    };
	
	    _this.state = {
	      place: 'top', // Direction of tooltip
	      type: 'dark', // Color theme of tooltip
	      effect: 'float', // float or fixed
	      show: false,
	      border: false,
	      placeholder: '',
	      offset: {},
	      extraClass: '',
	      html: false,
	      delayHide: 0,
	      delayShow: 0,
	      event: props.event || null,
	      eventOff: props.eventOff || null,
	      currentEvent: null, // Current mouse event
	      currentTarget: null, // Current target of mouse event
	      ariaProps: (0, _aria.parseAria)(props), // aria- and role attributes
	      afterUnfreeze: function afterUnfreeze() {},
	      isEmptyTip: false,
	      disable: false
	    };
	
	    _this.bind(['showTooltip', 'updateTooltip', 'hideTooltip', 'globalRebuild', 'globalShow', 'globalHide', 'onWindowResize']);
	
	    _this.mount = true;
	    _this.delayShowLoop = null;
	    _this.delayHideLoop = null;
	    _this.intervalUpdateContent = null;
	    return _this;
	  }
	
	  /**
	   * For unify the bind and unbind listener
	   */
	
	
	  _createClass(ReactTooltip, [{
	    key: 'bind',
	    value: function bind(methodArray) {
	      var _this2 = this;
	
	      methodArray.forEach(function (method) {
	        _this2[method] = _this2[method].bind(_this2);
	      });
	    }
	  }, {
	    key: 'componentDidMount',
	    value: function componentDidMount() {
	      this.setStyleHeader(); // Set the style to the <link>
	      this.bindListener(); // Bind listener for tooltip
	      this.bindWindowEvents(this.props.resizeHide); // Bind global event for static method
	    }
	  }, {
	    key: 'componentWillReceiveProps',
	    value: function componentWillReceiveProps(props) {
	      if (!props.frozen && this.props.frozen) {
	        this.state.afterUnfreeze();
	      }
	      var ariaProps = this.state.ariaProps;
	
	      var newAriaProps = (0, _aria.parseAria)(props);
	
	      var isChanged = Object.keys(newAriaProps).some(function (props) {
	        return newAriaProps[props] !== ariaProps[props];
	      });
	      if (isChanged) {
	        this.setState({ ariaProps: newAriaProps });
	      }
	    }
	  }, {
	    key: 'componentWillUnmount',
	    value: function componentWillUnmount() {
	      this.mount = false;
	
	      this.clearTimer();
	
	      this.unbindListener();
	      this.removeScrollListener();
	      this.unbindWindowEvents();
	    }
	
	    /**
	     * Pick out corresponded target elements
	     */
	
	  }, {
	    key: 'getTargetArray',
	    value: function getTargetArray(id) {
	      var targetArray = void 0;
	
	      if (!id) {
	        targetArray = document.querySelectorAll('[data-tip]:not([data-for])');
	      } else {
	        targetArray = document.querySelectorAll('[data-tip][data-for="' + id + '"]');
	      }
	
	      // targetArray is a NodeList, convert it to a real array
	      // I hope I can use Object.values...
	      return Object.keys(targetArray).filter(function (key) {
	        return key !== 'length';
	      }).map(function (key) {
	        return targetArray[key];
	      });
	    }
	
	    /**
	     * Bind listener to the target elements
	     * These listeners used to trigger showing or hiding the tooltip
	     */
	
	  }, {
	    key: 'bindListener',
	    value: function bindListener() {
	      var _this3 = this;
	
	      var _props = this.props,
	          id = _props.id,
	          globalEventOff = _props.globalEventOff;
	
	      var targetArray = this.getTargetArray(id);
	
	      targetArray.forEach(function (target) {
	        var isCaptureMode = _this3.isCapture(target);
	        if (target.getAttribute('currentItem') === null) {
	          target.setAttribute('currentItem', 'false');
	        }
	        _this3.unbindBasicListener(target);
	
	        if (_this3.isCustomEvent(target)) {
	          _this3.customBindListener(target);
	          return;
	        }
	
	        target.addEventListener('mouseenter', _this3.showTooltip, isCaptureMode);
	        if (_this3.state.effect === 'float') {
	          target.addEventListener('mousemove', _this3.updateTooltip, isCaptureMode);
	        }
	        target.addEventListener('mouseleave', _this3.hideTooltip, isCaptureMode);
	      });
	
	      // Global event to hide tooltip
	      if (globalEventOff) {
	        window.removeEventListener(globalEventOff, this.hideTooltip);
	        window.addEventListener(globalEventOff, this.hideTooltip, false);
	      }
	
	      if (this.props.onClickOutside) {
	        window.addEventListener('click', this.onClickOutsideFrozenTooltip);
	      }
	    }
	
	    /**
	     * Unbind listeners on target elements
	     */
	
	  }, {
	    key: 'unbindListener',
	    value: function unbindListener() {
	      var _this4 = this;
	
	      var _props2 = this.props,
	          id = _props2.id,
	          globalEventOff = _props2.globalEventOff;
	
	      var targetArray = this.getTargetArray(id);
	      targetArray.forEach(function (target) {
	        _this4.unbindBasicListener(target);
	        if (_this4.isCustomEvent(target)) _this4.customUnbindListener(target);
	      });
	
	      if (globalEventOff) window.removeEventListener(globalEventOff, this.hideTooltip);
	      window.removeEventListener('click', this.onClickOutsideFrozenTooltip);
	    }
	
	    /**
	     * Invoke this before bind listener and ummount the compont
	     * it is necessary to invloke this even when binding custom event
	     * so that the tooltip can switch between custom and default listener
	     */
	
	  }, {
	    key: 'unbindBasicListener',
	    value: function unbindBasicListener(target) {
	      target.removeEventListener('mouseenter', this.showTooltip);
	      target.removeEventListener('mousemove', this.updateTooltip);
	      target.removeEventListener('mouseleave', this.hideTooltip);
	    }
	
	    /**
	     * When mouse enter, show the tooltip
	     */
	
	  }, {
	    key: 'showTooltip',
	    value: function showTooltip(e, isGlobalCall) {
	      var _this5 = this;
	
	      if (isGlobalCall) {
	        // Don't trigger other elements belongs to other ReactTooltip
	        var targetArray = this.getTargetArray(this.props.id);
	        var isMyElement = targetArray.some(function (ele) {
	          return ele === e.currentTarget;
	        });
	        if (!isMyElement || this.state.show) return;
	      }
	      // Get the tooltip content
	      // calculate in this phrase so that tip width height can be detected
	      var _props3 = this.props,
	          children = _props3.children,
	          multiline = _props3.multiline,
	          getContent = _props3.getContent,
	          frozen = _props3.frozen;
	
	      if (frozen) return;
	      var originTooltip = e.currentTarget.getAttribute('data-tip');
	      var isMultiline = e.currentTarget.getAttribute('data-multiline') || multiline || false;
	
	      // Generate tootlip content
	      var content = void 0;
	      if (getContent) {
	        if (Array.isArray(getContent)) {
	          content = getContent[0] && getContent[0]();
	        } else {
	          content = getContent();
	        }
	      }
	      var placeholder = (0, _getTipContent2.default)(originTooltip, children, content, isMultiline);
	      var isEmptyTip = typeof placeholder === 'string' && placeholder === '' || placeholder === null;
	
	      // If it is focus event or called by ReactTooltip.show, switch to `solid` effect
	      var switchToSolid = e instanceof window.FocusEvent || isGlobalCall;
	
	      // if it need to skip adding hide listener to scroll
	      var scrollHide = true;
	      if (e.currentTarget.getAttribute('data-scroll-hide')) {
	        scrollHide = e.currentTarget.getAttribute('data-scroll-hide') === 'true';
	      } else if (this.props.scrollHide != null) {
	        scrollHide = this.props.scrollHide;
	      }
	
	      this.setState({
	        placeholder: placeholder,
	        isEmptyTip: isEmptyTip,
	        place: e.currentTarget.getAttribute('data-place') || this.props.place || 'top',
	        type: e.currentTarget.getAttribute('data-type') || this.props.type || 'dark',
	        effect: switchToSolid && 'solid' || e.currentTarget.getAttribute('data-effect') || this.props.effect || 'float',
	        offset: e.currentTarget.getAttribute('data-offset') || this.props.offset || {},
	        html: e.currentTarget.getAttribute('data-html') ? e.currentTarget.getAttribute('data-html') === 'true' : this.props.html || false,
	        delayShow: e.currentTarget.getAttribute('data-delay-show') || this.props.delayShow || 0,
	        delayHide: e.currentTarget.getAttribute('data-delay-hide') || this.props.delayHide || 0,
	        border: e.currentTarget.getAttribute('data-border') ? e.currentTarget.getAttribute('data-border') === 'true' : this.props.border || false,
	        extraClass: e.currentTarget.getAttribute('data-class') || this.props.class || '',
	        disable: e.currentTarget.getAttribute('data-tip-disable') ? e.currentTarget.getAttribute('data-tip-disable') === 'true' : this.props.disable || false
	      }, function () {
	        if (scrollHide) _this5.addScrollListener(e);
	        _this5.updateTooltip(e);
	
	        if (getContent && Array.isArray(getContent)) {
	          _this5.intervalUpdateContent = setInterval(function () {
	            if (_this5.mount) {
	              var _getContent = _this5.props.getContent;
	
	              var _placeholder = (0, _getTipContent2.default)(originTooltip, _getContent[0](), isMultiline);
	              var _isEmptyTip = typeof _placeholder === 'string' && _placeholder === '';
	              _this5.setState({
	                placeholder: _placeholder,
	                isEmptyTip: _isEmptyTip
	              });
	            }
	          }, getContent[1]);
	        }
	      });
	    }
	
	    /**
	     * When mouse hover, updatetooltip
	     */
	
	  }, {
	    key: 'updateTooltip',
	    value: function updateTooltip(e) {
	      var _this6 = this;
	
	      var _state = this.state,
	          delayShow = _state.delayShow,
	          show = _state.show,
	          isEmptyTip = _state.isEmptyTip,
	          disable = _state.disable;
	      var _props4 = this.props,
	          afterShow = _props4.afterShow,
	          frozen = _props4.frozen;
	      var placeholder = this.state.placeholder;
	
	      var delayTime = show ? 0 : parseInt(delayShow, 10);
	      var eventTarget = e.currentTarget;
	
	      if (isEmptyTip || disable) return; // if the tooltip is empty, disable the tooltip
	      var updateState = function updateState() {
	        if (Array.isArray(placeholder) && placeholder.length > 0 || placeholder) {
	          var isInvisible = !_this6.state.show;
	          _this6.setState({
	            currentEvent: e,
	            currentTarget: eventTarget,
	            show: true
	          }, function () {
	            _this6.updatePosition();
	            if (isInvisible && afterShow) afterShow();
	          });
	        }
	      };
	
	      clearTimeout(this.delayShowLoop);
	      if (frozen) {
	        this.setState({ afterUnfreeze: updateState });
	      } else if (delayShow) {
	        this.delayShowLoop = setTimeout(updateState, delayTime);
	      } else {
	        updateState();
	      }
	    }
	  }, {
	    key: 'hideTooltip',
	
	
	    /**
	     * When mouse leave, hide tooltip
	     */
	    value: function hideTooltip(e, hasTarget) {
	      var _this7 = this;
	
	      var _state2 = this.state,
	          delayHide = _state2.delayHide,
	          isEmptyTip = _state2.isEmptyTip,
	          disable = _state2.disable;
	      var _props5 = this.props,
	          afterHide = _props5.afterHide,
	          frozen = _props5.frozen;
	
	      if (!this.mount) return;
	      if (isEmptyTip || disable) return; // if the tooltip is empty, disable the tooltip
	      if (hasTarget) {
	        // Don't trigger other elements belongs to other ReactTooltip
	        var targetArray = this.getTargetArray(this.props.id);
	        var isMyElement = targetArray.some(function (ele) {
	          return ele === e.currentTarget;
	        });
	        if (!isMyElement || !this.state.show) return;
	      }
	      var resetState = function resetState() {
	        var isVisible = _this7.state.show;
	        _this7.setState({
	          show: false
	        }, function () {
	          _this7.removeScrollListener();
	          if (isVisible && afterHide) afterHide();
	        });
	      };
	
	      this.clearTimer();
	      if (frozen) {
	        this.setState({ afterUnfreeze: resetState });
	      } else if (delayHide) {
	        this.delayHideLoop = setTimeout(resetState, parseInt(delayHide, 10));
	      } else {
	        resetState();
	      }
	    }
	
	    /**
	     * Add scroll eventlistener when tooltip show
	     * automatically hide the tooltip when scrolling
	     */
	
	  }, {
	    key: 'addScrollListener',
	    value: function addScrollListener(e) {
	      var isCaptureMode = this.isCapture(e.currentTarget);
	      window.addEventListener('scroll', this.hideTooltip, isCaptureMode);
	    }
	  }, {
	    key: 'removeScrollListener',
	    value: function removeScrollListener() {
	      window.removeEventListener('scroll', this.hideTooltip);
	    }
	
	    // Calculate the position
	
	  }, {
	    key: 'updatePosition',
	    value: function updatePosition() {
	      var _this8 = this;
	
	      var _state3 = this.state,
	          currentEvent = _state3.currentEvent,
	          currentTarget = _state3.currentTarget,
	          place = _state3.place,
	          effect = _state3.effect,
	          offset = _state3.offset;
	
	      var node = _reactDom2.default.findDOMNode(this);
	      var result = (0, _getPosition2.default)(currentEvent, currentTarget, node, place, effect, offset);
	
	      if (result.isNewState) {
	        // Switch to reverse placement
	        return this.setState(result.newState, function () {
	          _this8.updatePosition();
	        });
	      }
	      // Set tooltip position
	      node.style.left = result.position.left + 'px';
	      node.style.top = result.position.top + 'px';
	    }
	
	    /**
	     * Set style tag in header
	     * in this way we can insert default css
	     */
	
	  }, {
	    key: 'setStyleHeader',
	    value: function setStyleHeader() {
	      if (!document.getElementsByTagName('head')[0].querySelector('style[id="react-tooltip"]')) {
	        var tag = document.createElement('style');
	        tag.id = 'react-tooltip';
	        tag.innerHTML = _style2.default;
	        document.getElementsByTagName('head')[0].appendChild(tag);
	      }
	    }
	
	    /**
	     * CLear all kinds of timeout of interval
	     */
	
	  }, {
	    key: 'clearTimer',
	    value: function clearTimer() {
	      clearTimeout(this.delayShowLoop);
	      clearTimeout(this.delayHideLoop);
	      clearInterval(this.intervalUpdateContent);
	    }
	  }, {
	    key: 'render',
	    value: function render() {
	      var _state4 = this.state,
	          placeholder = _state4.placeholder,
	          extraClass = _state4.extraClass,
	          html = _state4.html,
	          ariaProps = _state4.ariaProps,
	          disable = _state4.disable,
	          isEmptyTip = _state4.isEmptyTip;
	
	      var tooltipClass = (0, _classnames2.default)('__react_component_tooltip', { 'show': this.state.show && !disable && !isEmptyTip }, { 'frozen': this.props.frozen }, { 'border': this.state.border }, { 'place-top': this.state.place === 'top' }, { 'place-bottom': this.state.place === 'bottom' }, { 'place-left': this.state.place === 'left' }, { 'place-right': this.state.place === 'right' }, { 'type-dark': this.state.type === 'dark' }, { 'type-success': this.state.type === 'success' }, { 'type-warning': this.state.type === 'warning' }, { 'type-error': this.state.type === 'error' }, { 'type-info': this.state.type === 'info' }, { 'type-light': this.state.type === 'light' });
	      if (html) {
	        return _react2.default.createElement('div', _extends({ className: tooltipClass + ' ' + extraClass
	        }, ariaProps, {
	          'data-id': 'tooltip',
	          dangerouslySetInnerHTML: { __html: placeholder } }));
	      } else {
	        return _react2.default.createElement(
	          'div',
	          _extends({ className: tooltipClass + ' ' + extraClass
	          }, ariaProps, {
	            'data-id': 'tooltip' }),
	          placeholder
	        );
	      }
	    }
	  }]);
	
	  return ReactTooltip;
	}(_react.Component), _class2.propTypes = {
	  children: _react.PropTypes.any,
	  place: _react.PropTypes.string,
	  type: _react.PropTypes.string,
	  effect: _react.PropTypes.string,
	  offset: _react.PropTypes.object,
	  multiline: _react.PropTypes.bool,
	  border: _react.PropTypes.bool,
	  class: _react.PropTypes.string,
	  id: _react.PropTypes.string,
	  html: _react.PropTypes.bool,
	  delayHide: _react.PropTypes.number,
	  delayShow: _react.PropTypes.number,
	  event: _react.PropTypes.string,
	  eventOff: _react.PropTypes.string,
	  watchWindow: _react.PropTypes.bool,
	  isCapture: _react.PropTypes.bool,
	  globalEventOff: _react.PropTypes.string,
	  getContent: _react.PropTypes.any,
	  afterShow: _react.PropTypes.func,
	  afterHide: _react.PropTypes.func,
	  disable: _react.PropTypes.bool,
	  frozen: _react.PropTypes.bool,
	  onClickOutside: _react.PropTypes.func,
	  scrollHide: _react.PropTypes.bool,
	  resizeHide: _react.PropTypes.bool
	}, _class2.defaultProps = {
	  resizeHide: true
	}, _temp)) || _class) || _class) || _class) || _class;
	
	/* export default not fit for standalone, it will exports {default:...} */
	
	
	module.exports = ReactTooltip;

/***/ },

/***/ 6809:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/~/classnames/index.js ***!
  \**********************************************************************************************************************/
339,

/***/ 6810:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/decorators/staticMethods.js ***!
  \*********************************************************************************************************************************/
[7567, 6811],

/***/ 6811:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/constant.js ***!
  \*****************************************************************************************************************/
1196,

/***/ 6812:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/decorators/windowListener.js ***!
  \**********************************************************************************************************************************/
[7568, 6811],

/***/ 6813:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/decorators/customEvent.js ***!
  \*******************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	exports.default = function (target) {
	  target.prototype.isCustomEvent = function (ele) {
	    var event = this.state.event;
	
	    return event || !!ele.getAttribute('data-event');
	  };
	
	  /* Bind listener for custom event */
	  target.prototype.customBindListener = function (ele) {
	    var _this = this;
	
	    var _state = this.state,
	        event = _state.event,
	        eventOff = _state.eventOff;
	
	    var dataEvent = ele.getAttribute('data-event') || event;
	    var dataEventOff = ele.getAttribute('data-event-off') || eventOff;
	
	    dataEvent.split(' ').forEach(function (event) {
	      ele.removeEventListener(event, customListener);
	      customListener = checkStatus.bind(_this, dataEventOff);
	      ele.addEventListener(event, customListener, false);
	    });
	    if (dataEventOff) {
	      dataEventOff.split(' ').forEach(function (event) {
	        ele.removeEventListener(event, _this.hideTooltip);
	        ele.addEventListener(event, _this.hideTooltip, false);
	      });
	    }
	  };
	
	  /* Unbind listener for custom event */
	  target.prototype.customUnbindListener = function (ele) {
	    var _state2 = this.state,
	        event = _state2.event,
	        eventOff = _state2.eventOff;
	
	    var dataEvent = event || ele.getAttribute('data-event');
	    var dataEventOff = eventOff || ele.getAttribute('data-event-off');
	
	    ele.removeEventListener(dataEvent, customListener);
	    if (dataEventOff) ele.removeEventListener(dataEventOff, this.hideTooltip);
	  };
	};
	
	/**
	 * Custom events to control showing and hiding of tooltip
	 *
	 * @attributes
	 * - `event` {String}
	 * - `eventOff` {String}
	 */
	
	var checkStatus = function checkStatus(dataEventOff, e) {
	  var show = this.state.show;
	  var id = this.props.id;
	
	  var dataIsCapture = e.currentTarget.getAttribute('data-iscapture');
	  var isCapture = dataIsCapture && dataIsCapture === 'true' || this.props.isCapture;
	  var currentItem = e.currentTarget.getAttribute('currentItem');
	
	  if (!isCapture) e.stopPropagation();
	  if (show && currentItem === 'true') {
	    if (!dataEventOff) this.hideTooltip(e);
	  } else {
	    e.currentTarget.setAttribute('currentItem', 'true');
	    setUntargetItems(e.currentTarget, this.getTargetArray(id));
	    this.showTooltip(e);
	  }
	};
	
	var setUntargetItems = function setUntargetItems(currentTarget, targetArray) {
	  for (var i = 0; i < targetArray.length; i++) {
	    if (currentTarget !== targetArray[i]) {
	      targetArray[i].setAttribute('currentItem', 'false');
	    } else {
	      targetArray[i].setAttribute('currentItem', 'true');
	    }
	  }
	};
	
	var customListener = void 0;

/***/ },

/***/ 6814:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/decorators/isCapture.js ***!
  \*****************************************************************************************************************************/
1199,

/***/ 6815:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/utils/getPosition.js ***!
  \**************************************************************************************************************************/
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	exports.default = function (e, target, node, place, effect, offset) {
	  var tipWidth = node.clientWidth;
	  var tipHeight = node.clientHeight;
	
	  var _getCurrentOffset = getCurrentOffset(e, target, effect),
	      mouseX = _getCurrentOffset.mouseX,
	      mouseY = _getCurrentOffset.mouseY;
	
	  var defaultOffset = getDefaultPosition(effect, target.clientWidth, target.clientHeight, tipWidth, tipHeight);
	
	  var _calculateOffset = calculateOffset(offset),
	      extraOffset_X = _calculateOffset.extraOffset_X,
	      extraOffset_Y = _calculateOffset.extraOffset_Y;
	
	  var windowWidth = window.innerWidth;
	  var windowHeight = window.innerHeight;
	
	  var _getParent = getParent(node),
	      parentTop = _getParent.parentTop,
	      parentLeft = _getParent.parentLeft;
	
	  // Get the edge offset of the tooltip
	
	
	  var getTipOffsetLeft = function getTipOffsetLeft(place) {
	    var offset_X = defaultOffset[place].l;
	    return mouseX + offset_X + extraOffset_X;
	  };
	  var getTipOffsetRight = function getTipOffsetRight(place) {
	    var offset_X = defaultOffset[place].r;
	    return mouseX + offset_X + extraOffset_X;
	  };
	  var getTipOffsetTop = function getTipOffsetTop(place) {
	    var offset_Y = defaultOffset[place].t;
	    return mouseY + offset_Y + extraOffset_Y;
	  };
	  var getTipOffsetBottom = function getTipOffsetBottom(place) {
	    var offset_Y = defaultOffset[place].b;
	    return mouseY + offset_Y + extraOffset_Y;
	  };
	
	  // Judge if the tooltip has over the window(screen)
	  var outsideVertical = function outsideVertical() {
	    var result = false;
	    var newPlace = void 0;
	    if (getTipOffsetTop('left') < 0 && getTipOffsetBottom('left') <= windowHeight && getTipOffsetBottom('bottom') <= windowHeight) {
	      result = true;
	      newPlace = 'bottom';
	    } else if (getTipOffsetBottom('left') > windowHeight && getTipOffsetTop('left') >= 0 && getTipOffsetTop('top') >= 0) {
	      result = true;
	      newPlace = 'top';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	  var outsideLeft = function outsideLeft() {
	    var _outsideVertical = outsideVertical(),
	        result = _outsideVertical.result,
	        newPlace = _outsideVertical.newPlace; // Deal with vertical as first priority
	
	
	    if (result && outsideHorizontal().result) {
	      return { result: false }; // No need to change, if change to vertical will out of space
	    }
	    if (!result && getTipOffsetLeft('left') < 0 && getTipOffsetRight('right') <= windowWidth) {
	      result = true; // If vertical ok, but let out of side and right won't out of side
	      newPlace = 'right';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	  var outsideRight = function outsideRight() {
	    var _outsideVertical2 = outsideVertical(),
	        result = _outsideVertical2.result,
	        newPlace = _outsideVertical2.newPlace;
	
	    if (result && outsideHorizontal().result) {
	      return { result: false }; // No need to change, if change to vertical will out of space
	    }
	    if (!result && getTipOffsetRight('right') > windowWidth && getTipOffsetLeft('left') >= 0) {
	      result = true;
	      newPlace = 'left';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	
	  var outsideHorizontal = function outsideHorizontal() {
	    var result = false;
	    var newPlace = void 0;
	    if (getTipOffsetLeft('top') < 0 && getTipOffsetRight('top') <= windowWidth && getTipOffsetRight('right') <= windowWidth) {
	      result = true;
	      newPlace = 'right';
	    } else if (getTipOffsetRight('top') > windowWidth && getTipOffsetLeft('top') >= 0 && getTipOffsetLeft('left') >= 0) {
	      result = true;
	      newPlace = 'left';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	  var outsideTop = function outsideTop() {
	    var _outsideHorizontal = outsideHorizontal(),
	        result = _outsideHorizontal.result,
	        newPlace = _outsideHorizontal.newPlace;
	
	    if (result && outsideVertical().result) {
	      return { result: false };
	    }
	    if (!result && getTipOffsetTop('top') < 0 && getTipOffsetBottom('bottom') <= windowHeight) {
	      result = true;
	      newPlace = 'bottom';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	  var outsideBottom = function outsideBottom() {
	    var _outsideHorizontal2 = outsideHorizontal(),
	        result = _outsideHorizontal2.result,
	        newPlace = _outsideHorizontal2.newPlace;
	
	    if (result && outsideVertical().result) {
	      return { result: false };
	    }
	    if (!result && getTipOffsetBottom('bottom') > windowHeight && getTipOffsetTop('top') >= 0) {
	      result = true;
	      newPlace = 'top';
	    }
	    return { result: result, newPlace: newPlace };
	  };
	
	  // Return new state to change the placement to the reverse if possible
	  var outsideLeftResult = outsideLeft();
	  var outsideRightResult = outsideRight();
	  var outsideTopResult = outsideTop();
	  var outsideBottomResult = outsideBottom();
	
	  if (place === 'left' && outsideLeftResult.result) {
	    return {
	      isNewState: true,
	      newState: { place: outsideLeftResult.newPlace }
	    };
	  } else if (place === 'right' && outsideRightResult.result) {
	    return {
	      isNewState: true,
	      newState: { place: outsideRightResult.newPlace }
	    };
	  } else if (place === 'top' && outsideTopResult.result) {
	    return {
	      isNewState: true,
	      newState: { place: outsideTopResult.newPlace }
	    };
	  } else if (place === 'bottom' && outsideBottomResult.result) {
	    return {
	      isNewState: true,
	      newState: { place: outsideBottomResult.newPlace }
	    };
	  }
	
	  // Return tooltip offset position
	  return {
	    isNewState: false,
	    position: {
	      left: parseInt(getTipOffsetLeft(place) - parentLeft, 10),
	      top: parseInt(getTipOffsetTop(place) - parentTop, 10)
	    }
	  };
	};
	
	// Get current mouse offset
	var getCurrentOffset = function getCurrentOffset(e, currentTarget, effect) {
	  var boundingClientRect = currentTarget.getBoundingClientRect();
	  var targetTop = boundingClientRect.top;
	  var targetLeft = boundingClientRect.left;
	  var targetWidth = currentTarget.clientWidth;
	  var targetHeight = currentTarget.clientHeight;
	
	  if (effect === 'float') {
	    return {
	      mouseX: e.clientX,
	      mouseY: e.clientY
	    };
	  }
	  return {
	    mouseX: targetLeft + targetWidth / 2,
	    mouseY: targetTop + targetHeight / 2
	  };
	};
	
	// List all possibility of tooltip final offset
	// This is useful in judging if it is necessary for tooltip to switch position when out of window
	/**
	 * Calculate the position of tooltip
	 *
	 * @params
	 * - `e` {Event} the event of current mouse
	 * - `target` {Element} the currentTarget of the event
	 * - `node` {DOM} the react-tooltip object
	 * - `place` {String} top / right / bottom / left
	 * - `effect` {String} float / solid
	 * - `offset` {Object} the offset to default position
	 *
	 * @return {Object
	 * - `isNewState` {Bool} required
	 * - `newState` {Object}
	 * - `position` {OBject} {left: {Number}, top: {Number}}
	 */
	var getDefaultPosition = function getDefaultPosition(effect, targetWidth, targetHeight, tipWidth, tipHeight) {
	  var top = void 0;
	  var right = void 0;
	  var bottom = void 0;
	  var left = void 0;
	  var disToMouse = 3;
	  var triangleHeight = 2;
	  var cursorHeight = 12; // Optimize for float bottom only, cause the cursor will hide the tooltip
	
	  if (effect === 'float') {
	    top = {
	      l: -(tipWidth / 2),
	      r: tipWidth / 2,
	      t: -(tipHeight + disToMouse + triangleHeight),
	      b: -disToMouse
	    };
	    bottom = {
	      l: -(tipWidth / 2),
	      r: tipWidth / 2,
	      t: disToMouse + cursorHeight,
	      b: tipHeight + disToMouse + triangleHeight + cursorHeight
	    };
	    left = {
	      l: -(tipWidth + disToMouse + triangleHeight),
	      r: -disToMouse,
	      t: -(tipHeight / 2),
	      b: tipHeight / 2
	    };
	    right = {
	      l: disToMouse,
	      r: tipWidth + disToMouse + triangleHeight,
	      t: -(tipHeight / 2),
	      b: tipHeight / 2
	    };
	  } else if (effect === 'solid') {
	    top = {
	      l: -(tipWidth / 2),
	      r: tipWidth / 2,
	      t: -(targetHeight / 2 + tipHeight + triangleHeight),
	      b: -(targetHeight / 2)
	    };
	    bottom = {
	      l: -(tipWidth / 2),
	      r: tipWidth / 2,
	      t: targetHeight / 2,
	      b: targetHeight / 2 + tipHeight + triangleHeight
	    };
	    left = {
	      l: -(tipWidth + targetWidth / 2 + triangleHeight),
	      r: -(targetWidth / 2),
	      t: -(tipHeight / 2),
	      b: tipHeight / 2
	    };
	    right = {
	      l: targetWidth / 2,
	      r: tipWidth + targetWidth / 2 + triangleHeight,
	      t: -(tipHeight / 2),
	      b: tipHeight / 2
	    };
	  }
	
	  return { top: top, bottom: bottom, left: left, right: right };
	};
	
	// Consider additional offset into position calculation
	var calculateOffset = function calculateOffset(offset) {
	  var extraOffset_X = 0;
	  var extraOffset_Y = 0;
	
	  if (Object.prototype.toString.apply(offset) === '[object String]') {
	    offset = JSON.parse(offset.toString().replace(/\'/g, '\"'));
	  }
	  for (var key in offset) {
	    if (key === 'top') {
	      extraOffset_Y -= parseInt(offset[key], 10);
	    } else if (key === 'bottom') {
	      extraOffset_Y += parseInt(offset[key], 10);
	    } else if (key === 'left') {
	      extraOffset_X -= parseInt(offset[key], 10);
	    } else if (key === 'right') {
	      extraOffset_X += parseInt(offset[key], 10);
	    }
	  }
	
	  return { extraOffset_X: extraOffset_X, extraOffset_Y: extraOffset_Y };
	};
	
	// Get the offset of the parent elements
	var getParent = function getParent(currentTarget) {
	  var currentParent = currentTarget;
	  while (currentParent) {
	    if (window.getComputedStyle(currentParent).getPropertyValue('transform') !== 'none') break;
	    currentParent = currentParent.parentElement;
	  }
	
	  var parentTop = currentParent && currentParent.getBoundingClientRect().top || 0;
	  var parentLeft = currentParent && currentParent.getBoundingClientRect().left || 0;
	
	  return { parentTop: parentTop, parentLeft: parentLeft };
	};

/***/ },

/***/ 6816:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/utils/getTipContent.js ***!
  \****************************************************************************************************************************/
[7569, 5963],

/***/ 6817:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/utils/aria.js ***!
  \*******************************************************************************************************************/
1202,

/***/ 6818:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-tooltip/dist/style.js ***!
  \**************************************************************************************************************/
1203,

/***/ 6819:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.less ***!
  \*********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./TooltipStateManager.less */ 6820);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./TooltipStateManager.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./TooltipStateManager.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6820:
/*!****************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/TooltipStateManager.less ***!
  \****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaFadeBackgroundForOpenTooltip {\n  opacity: 0.5;\n  pointer-events: none;\n}\n.highcharts-container {\n  overflow: visible !important;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6821:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/show/HeatmapCanvas.jsx ***!
  \***********************************************************************************************************/
[7868, 5963, 6822, 6824, 6825, 6826, 6797],

/***/ 6822:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/react-highcharts/dist/ReactHighcharts.js ***!
  \***************************************************************************************************************************/
[7566, 5963, 6823],

/***/ 6823:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/highcharts/highcharts.js ***!
  \***********************************************************************************************************/
/***/ function(module, exports) {

	/*
	 Highcharts JS v5.0.10 (2017-03-31)
	
	 (c) 2009-2016 Torstein Honsi
	
	 License: www.highcharts.com/license
	*/
	(function(L,a){"object"===typeof module&&module.exports?module.exports=L.document?a(L):a:L.Highcharts=a(L)})("undefined"!==typeof window?window:this,function(L){L=function(){var a=window,B=a.document,A=a.navigator&&a.navigator.userAgent||"",H=B&&B.createElementNS&&!!B.createElementNS("http://www.w3.org/2000/svg","svg").createSVGRect,G=/(edge|msie|trident)/i.test(A)&&!window.opera,r=!H,f=/Firefox/.test(A),l=f&&4>parseInt(A.split("Firefox/")[1],10);return a.Highcharts?a.Highcharts.error(16,!0):{product:"Highcharts",
	version:"5.0.10",deg2rad:2*Math.PI/360,doc:B,hasBidiBug:l,hasTouch:B&&void 0!==B.documentElement.ontouchstart,isMS:G,isWebKit:/AppleWebKit/.test(A),isFirefox:f,isTouchDevice:/(Mobile|Android|Windows Phone)/.test(A),SVG_NS:"http://www.w3.org/2000/svg",chartCount:0,seriesTypes:{},symbolSizes:{},svg:H,vml:r,win:a,charts:[],marginNames:["plotTop","marginRight","marginBottom","plotLeft"],noop:function(){}}}();(function(a){var B=[],A=a.charts,H=a.doc,G=a.win;a.error=function(r,f){r=a.isNumber(r)?"Highcharts error #"+
	r+": www.highcharts.com/errors/"+r:r;if(f)throw Error(r);G.console&&console.log(r)};a.Fx=function(a,f,l){this.options=f;this.elem=a;this.prop=l};a.Fx.prototype={dSetter:function(){var a=this.paths[0],f=this.paths[1],l=[],q=this.now,k=a.length,u;if(1===q)l=this.toD;else if(k===f.length&&1>q)for(;k--;)u=parseFloat(a[k]),l[k]=isNaN(u)?a[k]:q*parseFloat(f[k]-u)+u;else l=f;this.elem.attr("d",l,null,!0)},update:function(){var a=this.elem,f=this.prop,l=this.now,q=this.options.step;if(this[f+"Setter"])this[f+
	"Setter"]();else a.attr?a.element&&a.attr(f,l,null,!0):a.style[f]=l+this.unit;q&&q.call(a,l,this)},run:function(a,f,l){var r=this,k=function(a){return k.stopped?!1:r.step(a)},u;this.startTime=+new Date;this.start=a;this.end=f;this.unit=l;this.now=this.start;this.pos=0;k.elem=this.elem;k.prop=this.prop;k()&&1===B.push(k)&&(k.timerId=setInterval(function(){for(u=0;u<B.length;u++)B[u]()||B.splice(u--,1);B.length||clearInterval(k.timerId)},13))},step:function(a){var f=+new Date,r,q=this.options;r=this.elem;
	var k=q.complete,u=q.duration,d=q.curAnim,c;if(r.attr&&!r.element)r=!1;else if(a||f>=u+this.startTime){this.now=this.end;this.pos=1;this.update();a=d[this.prop]=!0;for(c in d)!0!==d[c]&&(a=!1);a&&k&&k.call(r);r=!1}else this.pos=q.easing((f-this.startTime)/u),this.now=this.start+(this.end-this.start)*this.pos,this.update(),r=!0;return r},initPath:function(r,f,l){function q(a){var b,e;for(t=a.length;t--;)b="M"===a[t]||"L"===a[t],e=/[a-zA-Z]/.test(a[t+3]),b&&e&&a.splice(t+1,0,a[t+1],a[t+2],a[t+1],a[t+
	2])}function k(a,e){for(;a.length<p;){a[0]=e[p-a.length];var h=a.slice(0,b);[].splice.apply(a,[0,0].concat(h));D&&(h=a.slice(a.length-b),[].splice.apply(a,[a.length,0].concat(h)),t--)}a[0]="M"}function u(a,e){for(var c=(p-a.length)/b;0<c&&c--;)h=a.slice().splice(a.length/w-b,b*w),h[0]=e[p-b-c*b],z&&(h[b-6]=h[b-2],h[b-5]=h[b-1]),[].splice.apply(a,[a.length/w,0].concat(h)),D&&c--}f=f||"";var d,c=r.startX,n=r.endX,z=-1<f.indexOf("C"),b=z?7:3,p,h,t;f=f.split(" ");l=l.slice();var D=r.isArea,w=D?2:1,e;
	z&&(q(f),q(l));if(c&&n){for(t=0;t<c.length;t++)if(c[t]===n[0]){d=t;break}else if(c[0]===n[n.length-c.length+t]){d=t;e=!0;break}void 0===d&&(f=[])}f.length&&a.isNumber(d)&&(p=l.length+d*w*b,e?(k(f,l),u(l,f)):(k(l,f),u(f,l)));return[f,l]}};a.extend=function(a,f){var r;a||(a={});for(r in f)a[r]=f[r];return a};a.merge=function(){var r,f=arguments,l,q={},k=function(u,d){var c,n;"object"!==typeof u&&(u={});for(n in d)d.hasOwnProperty(n)&&(c=d[n],a.isObject(c,!0)&&"renderTo"!==n&&"number"!==typeof c.nodeType?
	u[n]=k(u[n]||{},c):u[n]=d[n]);return u};!0===f[0]&&(q=f[1],f=Array.prototype.slice.call(f,2));l=f.length;for(r=0;r<l;r++)q=k(q,f[r]);return q};a.pInt=function(a,f){return parseInt(a,f||10)};a.isString=function(a){return"string"===typeof a};a.isArray=function(a){a=Object.prototype.toString.call(a);return"[object Array]"===a||"[object Array Iterator]"===a};a.isObject=function(r,f){return r&&"object"===typeof r&&(!f||!a.isArray(r))};a.isNumber=function(a){return"number"===typeof a&&!isNaN(a)};a.erase=
	function(a,f){for(var r=a.length;r--;)if(a[r]===f){a.splice(r,1);break}};a.defined=function(a){return void 0!==a&&null!==a};a.attr=function(r,f,l){var q,k;if(a.isString(f))a.defined(l)?r.setAttribute(f,l):r&&r.getAttribute&&(k=r.getAttribute(f));else if(a.defined(f)&&a.isObject(f))for(q in f)r.setAttribute(q,f[q]);return k};a.splat=function(r){return a.isArray(r)?r:[r]};a.syncTimeout=function(a,f,l){if(f)return setTimeout(a,f,l);a.call(0,l)};a.pick=function(){var a=arguments,f,l,q=a.length;for(f=
	0;f<q;f++)if(l=a[f],void 0!==l&&null!==l)return l};a.css=function(r,f){a.isMS&&!a.svg&&f&&void 0!==f.opacity&&(f.filter="alpha(opacity\x3d"+100*f.opacity+")");a.extend(r.style,f)};a.createElement=function(r,f,l,q,k){r=H.createElement(r);var u=a.css;f&&a.extend(r,f);k&&u(r,{padding:0,border:"none",margin:0});l&&u(r,l);q&&q.appendChild(r);return r};a.extendClass=function(r,f){var l=function(){};l.prototype=new r;a.extend(l.prototype,f);return l};a.pad=function(a,f,l){return Array((f||2)+1-String(a).length).join(l||
	0)+a};a.relativeLength=function(a,f){return/%$/.test(a)?f*parseFloat(a)/100:parseFloat(a)};a.wrap=function(a,f,l){var q=a[f];a[f]=function(){var a=Array.prototype.slice.call(arguments),u=arguments,d=this;d.proceed=function(){q.apply(d,arguments.length?arguments:u)};a.unshift(q);a=l.apply(this,a);d.proceed=null;return a}};a.getTZOffset=function(r){var f=a.Date;return 6E4*(f.hcGetTimezoneOffset&&f.hcGetTimezoneOffset(r)||f.hcTimezoneOffset||0)};a.dateFormat=function(r,f,l){if(!a.defined(f)||isNaN(f))return a.defaultOptions.lang.invalidDate||
	"";r=a.pick(r,"%Y-%m-%d %H:%M:%S");var q=a.Date,k=new q(f-a.getTZOffset(f)),u,d=k[q.hcGetHours](),c=k[q.hcGetDay](),n=k[q.hcGetDate](),z=k[q.hcGetMonth](),b=k[q.hcGetFullYear](),p=a.defaultOptions.lang,h=p.weekdays,t=p.shortWeekdays,D=a.pad,q=a.extend({a:t?t[c]:h[c].substr(0,3),A:h[c],d:D(n),e:D(n,2," "),w:c,b:p.shortMonths[z],B:p.months[z],m:D(z+1),y:b.toString().substr(2,2),Y:b,H:D(d),k:d,I:D(d%12||12),l:d%12||12,M:D(k[q.hcGetMinutes]()),p:12>d?"AM":"PM",P:12>d?"am":"pm",S:D(k.getSeconds()),L:D(Math.round(f%
	1E3),3)},a.dateFormats);for(u in q)for(;-1!==r.indexOf("%"+u);)r=r.replace("%"+u,"function"===typeof q[u]?q[u](f):q[u]);return l?r.substr(0,1).toUpperCase()+r.substr(1):r};a.formatSingle=function(r,f){var l=/\.([0-9])/,q=a.defaultOptions.lang;/f$/.test(r)?(l=(l=r.match(l))?l[1]:-1,null!==f&&(f=a.numberFormat(f,l,q.decimalPoint,-1<r.indexOf(",")?q.thousandsSep:""))):f=a.dateFormat(r,f);return f};a.format=function(r,f){for(var l="{",q=!1,k,u,d,c,n=[],z;r;){l=r.indexOf(l);if(-1===l)break;k=r.slice(0,
	l);if(q){k=k.split(":");u=k.shift().split(".");c=u.length;z=f;for(d=0;d<c;d++)z=z[u[d]];k.length&&(z=a.formatSingle(k.join(":"),z));n.push(z)}else n.push(k);r=r.slice(l+1);l=(q=!q)?"}":"{"}n.push(r);return n.join("")};a.getMagnitude=function(a){return Math.pow(10,Math.floor(Math.log(a)/Math.LN10))};a.normalizeTickInterval=function(r,f,l,q,k){var u,d=r;l=a.pick(l,1);u=r/l;f||(f=k?[1,1.2,1.5,2,2.5,3,4,5,6,8,10]:[1,2,2.5,5,10],!1===q&&(1===l?f=a.grep(f,function(a){return 0===a%1}):.1>=l&&(f=[1/l])));
	for(q=0;q<f.length&&!(d=f[q],k&&d*l>=r||!k&&u<=(f[q]+(f[q+1]||f[q]))/2);q++);return d=a.correctFloat(d*l,-Math.round(Math.log(.001)/Math.LN10))};a.stableSort=function(a,f){var l=a.length,q,k;for(k=0;k<l;k++)a[k].safeI=k;a.sort(function(a,d){q=f(a,d);return 0===q?a.safeI-d.safeI:q});for(k=0;k<l;k++)delete a[k].safeI};a.arrayMin=function(a){for(var f=a.length,l=a[0];f--;)a[f]<l&&(l=a[f]);return l};a.arrayMax=function(a){for(var f=a.length,l=a[0];f--;)a[f]>l&&(l=a[f]);return l};a.destroyObjectProperties=
	function(a,f){for(var l in a)a[l]&&a[l]!==f&&a[l].destroy&&a[l].destroy(),delete a[l]};a.discardElement=function(r){var f=a.garbageBin;f||(f=a.createElement("div"));r&&f.appendChild(r);f.innerHTML=""};a.correctFloat=function(a,f){return parseFloat(a.toPrecision(f||14))};a.setAnimation=function(r,f){f.renderer.globalAnimation=a.pick(r,f.options.chart.animation,!0)};a.animObject=function(r){return a.isObject(r)?a.merge(r):{duration:r?500:0}};a.timeUnits={millisecond:1,second:1E3,minute:6E4,hour:36E5,
	day:864E5,week:6048E5,month:24192E5,year:314496E5};a.numberFormat=function(r,f,l,q){r=+r||0;f=+f;var k=a.defaultOptions.lang,u=(r.toString().split(".")[1]||"").length,d,c;-1===f?f=Math.min(u,20):a.isNumber(f)||(f=2);c=(Math.abs(r)+Math.pow(10,-Math.max(f,u)-1)).toFixed(f);u=String(a.pInt(c));d=3<u.length?u.length%3:0;l=a.pick(l,k.decimalPoint);q=a.pick(q,k.thousandsSep);r=(0>r?"-":"")+(d?u.substr(0,d)+q:"");r+=u.substr(d).replace(/(\d{3})(?=\d)/g,"$1"+q);f&&(r+=l+c.slice(-f));return r};Math.easeInOutSine=
	function(a){return-.5*(Math.cos(Math.PI*a)-1)};a.getStyle=function(r,f){return"width"===f?Math.min(r.offsetWidth,r.scrollWidth)-a.getStyle(r,"padding-left")-a.getStyle(r,"padding-right"):"height"===f?Math.min(r.offsetHeight,r.scrollHeight)-a.getStyle(r,"padding-top")-a.getStyle(r,"padding-bottom"):(r=G.getComputedStyle(r,void 0))&&a.pInt(r.getPropertyValue(f))};a.inArray=function(a,f){return f.indexOf?f.indexOf(a):[].indexOf.call(f,a)};a.grep=function(a,f){return[].filter.call(a,f)};a.find=function(a,
	f){return[].find.call(a,f)};a.map=function(a,f){for(var l=[],q=0,k=a.length;q<k;q++)l[q]=f.call(a[q],a[q],q,a);return l};a.offset=function(a){var f=H.documentElement;a=a.getBoundingClientRect();return{top:a.top+(G.pageYOffset||f.scrollTop)-(f.clientTop||0),left:a.left+(G.pageXOffset||f.scrollLeft)-(f.clientLeft||0)}};a.stop=function(a,f){for(var l=B.length;l--;)B[l].elem!==a||f&&f!==B[l].prop||(B[l].stopped=!0)};a.each=function(a,f,l){return Array.prototype.forEach.call(a,f,l)};a.addEvent=function(r,
	f,l){function q(a){a.target=a.srcElement||G;l.call(r,a)}var k=r.hcEvents=r.hcEvents||{};r.addEventListener?r.addEventListener(f,l,!1):r.attachEvent&&(r.hcEventsIE||(r.hcEventsIE={}),r.hcEventsIE[l.toString()]=q,r.attachEvent("on"+f,q));k[f]||(k[f]=[]);k[f].push(l);return function(){a.removeEvent(r,f,l)}};a.removeEvent=function(r,f,l){function q(a,c){r.removeEventListener?r.removeEventListener(a,c,!1):r.attachEvent&&(c=r.hcEventsIE[c.toString()],r.detachEvent("on"+a,c))}function k(){var a,c;if(r.nodeName)for(c in f?
	(a={},a[f]=!0):a=d,a)if(d[c])for(a=d[c].length;a--;)q(c,d[c][a])}var u,d=r.hcEvents,c;d&&(f?(u=d[f]||[],l?(c=a.inArray(l,u),-1<c&&(u.splice(c,1),d[f]=u),q(f,l)):(k(),d[f]=[])):(k(),r.hcEvents={}))};a.fireEvent=function(r,f,l,q){var k;k=r.hcEvents;var u,d;l=l||{};if(H.createEvent&&(r.dispatchEvent||r.fireEvent))k=H.createEvent("Events"),k.initEvent(f,!0,!0),a.extend(k,l),r.dispatchEvent?r.dispatchEvent(k):r.fireEvent(f,k);else if(k)for(k=k[f]||[],u=k.length,l.target||a.extend(l,{preventDefault:function(){l.defaultPrevented=
	!0},target:r,type:f}),f=0;f<u;f++)(d=k[f])&&!1===d.call(r,l)&&l.preventDefault();q&&!l.defaultPrevented&&q(l)};a.animate=function(r,f,l){var q,k="",u,d,c;a.isObject(l)||(q=arguments,l={duration:q[2],easing:q[3],complete:q[4]});a.isNumber(l.duration)||(l.duration=400);l.easing="function"===typeof l.easing?l.easing:Math[l.easing]||Math.easeInOutSine;l.curAnim=a.merge(f);for(c in f)a.stop(r,c),d=new a.Fx(r,l,c),u=null,"d"===c?(d.paths=d.initPath(r,r.d,f.d),d.toD=f.d,q=0,u=1):r.attr?q=r.attr(c):(q=parseFloat(a.getStyle(r,
	c))||0,"opacity"!==c&&(k="px")),u||(u=f[c]),u&&u.match&&u.match("px")&&(u=u.replace(/px/g,"")),d.run(q,u,k)};a.seriesType=function(r,f,l,q,k){var u=a.getOptions(),d=a.seriesTypes;u.plotOptions[r]=a.merge(u.plotOptions[f],l);d[r]=a.extendClass(d[f]||function(){},q);d[r].prototype.type=r;k&&(d[r].prototype.pointClass=a.extendClass(a.Point,k));return d[r]};a.uniqueKey=function(){var a=Math.random().toString(36).substring(2,9),f=0;return function(){return"highcharts-"+a+"-"+f++}}();G.jQuery&&(G.jQuery.fn.highcharts=
	function(){var r=[].slice.call(arguments);if(this[0])return r[0]?(new (a[a.isString(r[0])?r.shift():"Chart"])(this[0],r[0],r[1]),this):A[a.attr(this[0],"data-highcharts-chart")]});H&&!H.defaultView&&(a.getStyle=function(r,f){var l={width:"clientWidth",height:"clientHeight"}[f];if(r.style[f])return a.pInt(r.style[f]);"opacity"===f&&(f="filter");if(l)return r.style.zoom=1,Math.max(r[l]-2*a.getStyle(r,"padding"),0);r=r.currentStyle[f.replace(/\-(\w)/g,function(a,k){return k.toUpperCase()})];"filter"===
	f&&(r=r.replace(/alpha\(opacity=([0-9]+)\)/,function(a,k){return k/100}));return""===r?1:a.pInt(r)});Array.prototype.forEach||(a.each=function(a,f,l){for(var q=0,k=a.length;q<k;q++)if(!1===f.call(l,a[q],q,a))return q});Array.prototype.indexOf||(a.inArray=function(a,f){var l,q=0;if(f)for(l=f.length;q<l;q++)if(f[q]===a)return q;return-1});Array.prototype.filter||(a.grep=function(a,f){for(var l=[],q=0,k=a.length;q<k;q++)f(a[q],q)&&l.push(a[q]);return l});Array.prototype.find||(a.find=function(a,f){var l,
	q=a.length;for(l=0;l<q;l++)if(f(a[l],l))return a[l]})})(L);(function(a){var B=a.each,A=a.isNumber,H=a.map,G=a.merge,r=a.pInt;a.Color=function(f){if(!(this instanceof a.Color))return new a.Color(f);this.init(f)};a.Color.prototype={parsers:[{regex:/rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/,parse:function(a){return[r(a[1]),r(a[2]),r(a[3]),parseFloat(a[4],10)]}},{regex:/rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/,parse:function(a){return[r(a[1]),
	r(a[2]),r(a[3]),1]}}],names:{white:"#ffffff",black:"#000000"},init:function(f){var l,q,k,u;if((this.input=f=this.names[f&&f.toLowerCase?f.toLowerCase():""]||f)&&f.stops)this.stops=H(f.stops,function(d){return new a.Color(d[1])});else if(f&&"#"===f[0]&&(l=f.length,f=parseInt(f.substr(1),16),7===l?q=[(f&16711680)>>16,(f&65280)>>8,f&255,1]:4===l&&(q=[(f&3840)>>4|(f&3840)>>8,(f&240)>>4|f&240,(f&15)<<4|f&15,1])),!q)for(k=this.parsers.length;k--&&!q;)u=this.parsers[k],(l=u.regex.exec(f))&&(q=u.parse(l));
	this.rgba=q||[]},get:function(a){var f=this.input,q=this.rgba,k;this.stops?(k=G(f),k.stops=[].concat(k.stops),B(this.stops,function(u,d){k.stops[d]=[k.stops[d][0],u.get(a)]})):k=q&&A(q[0])?"rgb"===a||!a&&1===q[3]?"rgb("+q[0]+","+q[1]+","+q[2]+")":"a"===a?q[3]:"rgba("+q.join(",")+")":f;return k},brighten:function(a){var f,q=this.rgba;if(this.stops)B(this.stops,function(k){k.brighten(a)});else if(A(a)&&0!==a)for(f=0;3>f;f++)q[f]+=r(255*a),0>q[f]&&(q[f]=0),255<q[f]&&(q[f]=255);return this},setOpacity:function(a){this.rgba[3]=
	a;return this}};a.color=function(f){return new a.Color(f)}})(L);(function(a){var B,A,H=a.addEvent,G=a.animate,r=a.attr,f=a.charts,l=a.color,q=a.css,k=a.createElement,u=a.defined,d=a.deg2rad,c=a.destroyObjectProperties,n=a.doc,z=a.each,b=a.extend,p=a.erase,h=a.grep,t=a.hasTouch,D=a.inArray,w=a.isArray,e=a.isFirefox,x=a.isMS,C=a.isObject,E=a.isString,m=a.isWebKit,y=a.merge,I=a.noop,K=a.pick,J=a.pInt,g=a.removeEvent,F=a.stop,Q=a.svg,N=a.SVG_NS,P=a.symbolSizes,O=a.win;B=a.SVGElement=function(){return this};
	B.prototype={opacity:1,SVG_NS:N,textProps:"direction fontSize fontWeight fontFamily fontStyle color lineHeight width textAlign textDecoration textOverflow textOutline".split(" "),init:function(a,g){this.element="span"===g?k(g):n.createElementNS(this.SVG_NS,g);this.renderer=a},animate:function(v,g,b){g=a.animObject(K(g,this.renderer.globalAnimation,!0));0!==g.duration?(b&&(g.complete=b),G(this,v,g)):(this.attr(v,null,b),g.step&&g.step.call(this));return this},colorGradient:function(v,g,b){var e=this.renderer,
	h,c,m,M,F,x,d,C,t,p,n,k=[],R;v.radialGradient?c="radialGradient":v.linearGradient&&(c="linearGradient");if(c){m=v[c];F=e.gradients;d=v.stops;p=b.radialReference;w(m)&&(v[c]=m={x1:m[0],y1:m[1],x2:m[2],y2:m[3],gradientUnits:"userSpaceOnUse"});"radialGradient"===c&&p&&!u(m.gradientUnits)&&(M=m,m=y(m,e.getRadialAttr(p,M),{gradientUnits:"userSpaceOnUse"}));for(n in m)"id"!==n&&k.push(n,m[n]);for(n in d)k.push(d[n]);k=k.join(",");F[k]?p=F[k].attr("id"):(m.id=p=a.uniqueKey(),F[k]=x=e.createElement(c).attr(m).add(e.defs),
	x.radAttr=M,x.stops=[],z(d,function(v){0===v[1].indexOf("rgba")?(h=a.color(v[1]),C=h.get("rgb"),t=h.get("a")):(C=v[1],t=1);v=e.createElement("stop").attr({offset:v[0],"stop-color":C,"stop-opacity":t}).add(x);x.stops.push(v)}));R="url("+e.url+"#"+p+")";b.setAttribute(g,R);b.gradient=k;v.toString=function(){return R}}},applyTextOutline:function(v){var g=this.element,b,e,c,h,m;-1!==v.indexOf("contrast")&&(v=v.replace(/contrast/g,this.renderer.getContrast(g.style.fill)));v=v.split(" ");e=v[v.length-1];
	if((c=v[0])&&"none"!==c&&a.svg){this.fakeTS=!0;v=[].slice.call(g.getElementsByTagName("tspan"));this.ySetter=this.xSetter;c=c.replace(/(^[\d\.]+)(.*?)$/g,function(a,v,g){return 2*v+g});for(m=v.length;m--;)b=v[m],"highcharts-text-outline"===b.getAttribute("class")&&p(v,g.removeChild(b));h=g.firstChild;z(v,function(a,v){0===v&&(a.setAttribute("x",g.getAttribute("x")),v=g.getAttribute("y"),a.setAttribute("y",v||0),null===v&&g.setAttribute("y",0));a=a.cloneNode(1);r(a,{"class":"highcharts-text-outline",
	fill:e,stroke:e,"stroke-width":c,"stroke-linejoin":"round"});g.insertBefore(a,h)})}},attr:function(a,g,b,e){var v,c=this.element,h,m=this,M;"string"===typeof a&&void 0!==g&&(v=a,a={},a[v]=g);if("string"===typeof a)m=(this[a+"Getter"]||this._defaultGetter).call(this,a,c);else{for(v in a)g=a[v],M=!1,e||F(this,v),this.symbolName&&/^(x|y|width|height|r|start|end|innerR|anchorX|anchorY)$/.test(v)&&(h||(this.symbolAttr(a),h=!0),M=!0),!this.rotation||"x"!==v&&"y"!==v||(this.doTransform=!0),M||(M=this[v+
	"Setter"]||this._defaultSetter,M.call(this,g,v,c),this.shadows&&/^(width|height|visibility|x|y|d|transform|cx|cy|r)$/.test(v)&&this.updateShadows(v,g,M));this.doTransform&&(this.updateTransform(),this.doTransform=!1)}b&&b();return m},updateShadows:function(a,g,b){for(var v=this.shadows,e=v.length;e--;)b.call(v[e],"height"===a?Math.max(g-(v[e].cutHeight||0),0):"d"===a?this.d:g,a,v[e])},addClass:function(a,g){var v=this.attr("class")||"";-1===v.indexOf(a)&&(g||(a=(v+(v?" ":"")+a).replace("  "," ")),
	this.attr("class",a));return this},hasClass:function(a){return-1!==r(this.element,"class").indexOf(a)},removeClass:function(a){r(this.element,"class",(r(this.element,"class")||"").replace(a,""));return this},symbolAttr:function(a){var v=this;z("x y r start end width height innerR anchorX anchorY".split(" "),function(g){v[g]=K(a[g],v[g])});v.attr({d:v.renderer.symbols[v.symbolName](v.x,v.y,v.width,v.height,v)})},clip:function(a){return this.attr("clip-path",a?"url("+this.renderer.url+"#"+a.id+")":
	"none")},crisp:function(a,g){var v,b={},e;g=g||a.strokeWidth||0;e=Math.round(g)%2/2;a.x=Math.floor(a.x||this.x||0)+e;a.y=Math.floor(a.y||this.y||0)+e;a.width=Math.floor((a.width||this.width||0)-2*e);a.height=Math.floor((a.height||this.height||0)-2*e);u(a.strokeWidth)&&(a.strokeWidth=g);for(v in a)this[v]!==a[v]&&(this[v]=b[v]=a[v]);return b},css:function(a){var v=this.styles,g={},e=this.element,c,h="",m=!v,F=["textOutline","textOverflow","width"];a&&a.color&&(a.fill=a.color);if(v)for(c in a)a[c]!==
	v[c]&&(g[c]=a[c],m=!0);if(m){v&&(a=b(v,g));v=this.textWidth=a&&a.width&&"auto"!==a.width&&"text"===e.nodeName.toLowerCase()&&J(a.width);this.styles=a;v&&!Q&&this.renderer.forExport&&delete a.width;if(x&&!Q)q(this.element,a);else{v=function(a,v){return"-"+v.toLowerCase()};for(c in a)-1===D(c,F)&&(h+=c.replace(/([A-Z])/g,v)+":"+a[c]+";");h&&r(e,"style",h)}this.added&&("text"===this.element.nodeName&&this.renderer.buildText(this),a&&a.textOutline&&this.applyTextOutline(a.textOutline))}return this},strokeWidth:function(){return this["stroke-width"]||
	0},on:function(a,g){var v=this,e=v.element;t&&"click"===a?(e.ontouchstart=function(a){v.touchEventFired=Date.now();a.preventDefault();g.call(e,a)},e.onclick=function(a){(-1===O.navigator.userAgent.indexOf("Android")||1100<Date.now()-(v.touchEventFired||0))&&g.call(e,a)}):e["on"+a]=g;return this},setRadialReference:function(a){var v=this.renderer.gradients[this.element.gradient];this.element.radialReference=a;v&&v.radAttr&&v.animate(this.renderer.getRadialAttr(a,v.radAttr));return this},translate:function(a,
	g){return this.attr({translateX:a,translateY:g})},invert:function(a){this.inverted=a;this.updateTransform();return this},updateTransform:function(){var a=this.translateX||0,g=this.translateY||0,e=this.scaleX,b=this.scaleY,c=this.inverted,h=this.rotation,m=this.element;c&&(a+=this.width,g+=this.height);a=["translate("+a+","+g+")"];c?a.push("rotate(90) scale(-1,1)"):h&&a.push("rotate("+h+" "+(m.getAttribute("x")||0)+" "+(m.getAttribute("y")||0)+")");(u(e)||u(b))&&a.push("scale("+K(e,1)+" "+K(b,1)+")");
	a.length&&m.setAttribute("transform",a.join(" "))},toFront:function(){var a=this.element;a.parentNode.appendChild(a);return this},align:function(a,g,e){var v,b,c,h,m={};b=this.renderer;c=b.alignedObjects;var F,x;if(a){if(this.alignOptions=a,this.alignByTranslate=g,!e||E(e))this.alignTo=v=e||"renderer",p(c,this),c.push(this),e=null}else a=this.alignOptions,g=this.alignByTranslate,v=this.alignTo;e=K(e,b[v],b);v=a.align;b=a.verticalAlign;c=(e.x||0)+(a.x||0);h=(e.y||0)+(a.y||0);"right"===v?F=1:"center"===
	v&&(F=2);F&&(c+=(e.width-(a.width||0))/F);m[g?"translateX":"x"]=Math.round(c);"bottom"===b?x=1:"middle"===b&&(x=2);x&&(h+=(e.height-(a.height||0))/x);m[g?"translateY":"y"]=Math.round(h);this[this.placed?"animate":"attr"](m);this.placed=!0;this.alignAttr=m;return this},getBBox:function(a,g){var v,e=this.renderer,c,h=this.element,m=this.styles,F,x=this.textStr,y,M=e.cache,C=e.cacheKeys,t;g=K(g,this.rotation);c=g*d;F=m&&m.fontSize;void 0!==x&&(t=x.toString(),-1===t.indexOf("\x3c")&&(t=t.replace(/[0-9]/g,
	"0")),t+=["",g||0,F,m&&m.width,m&&m.textOverflow].join());t&&!a&&(v=M[t]);if(!v){if(h.namespaceURI===this.SVG_NS||e.forExport){try{(y=this.fakeTS&&function(a){z(h.querySelectorAll(".highcharts-text-outline"),function(v){v.style.display=a})})&&y("none"),v=h.getBBox?b({},h.getBBox()):{width:h.offsetWidth,height:h.offsetHeight},y&&y("")}catch(X){}if(!v||0>v.width)v={width:0,height:0}}else v=this.htmlGetBBox();e.isSVG&&(a=v.width,e=v.height,m&&"11px"===m.fontSize&&17===Math.round(e)&&(v.height=e=14),
	g&&(v.width=Math.abs(e*Math.sin(c))+Math.abs(a*Math.cos(c)),v.height=Math.abs(e*Math.cos(c))+Math.abs(a*Math.sin(c))));if(t&&0<v.height){for(;250<C.length;)delete M[C.shift()];M[t]||C.push(t);M[t]=v}}return v},show:function(a){return this.attr({visibility:a?"inherit":"visible"})},hide:function(){return this.attr({visibility:"hidden"})},fadeOut:function(a){var v=this;v.animate({opacity:0},{duration:a||150,complete:function(){v.attr({y:-9999})}})},add:function(a){var v=this.renderer,g=this.element,
	e;a&&(this.parentGroup=a);this.parentInverted=a&&a.inverted;void 0!==this.textStr&&v.buildText(this);this.added=!0;if(!a||a.handleZ||this.zIndex)e=this.zIndexSetter();e||(a?a.element:v.box).appendChild(g);if(this.onAdd)this.onAdd();return this},safeRemoveChild:function(a){var v=a.parentNode;v&&v.removeChild(a)},destroy:function(){var a=this,g=a.element||{},e=a.renderer.isSVG&&"SPAN"===g.nodeName&&a.parentGroup,b,c;g.onclick=g.onmouseout=g.onmouseover=g.onmousemove=g.point=null;F(a);a.clipPath&&(z(a.element.ownerSVGElement.querySelectorAll("[clip-path]"),
	function(v){-1<v.getAttribute("clip-path").indexOf(a.clipPath.element.id)&&v.removeAttribute("clip-path")}),a.clipPath=a.clipPath.destroy());if(a.stops){for(c=0;c<a.stops.length;c++)a.stops[c]=a.stops[c].destroy();a.stops=null}a.safeRemoveChild(g);for(a.destroyShadows();e&&e.div&&0===e.div.childNodes.length;)g=e.parentGroup,a.safeRemoveChild(e.div),delete e.div,e=g;a.alignTo&&p(a.renderer.alignedObjects,a);for(b in a)delete a[b];return null},shadow:function(a,g,e){var v=[],b,c,h=this.element,m,F,
	x,y;if(!a)this.destroyShadows();else if(!this.shadows){F=K(a.width,3);x=(a.opacity||.15)/F;y=this.parentInverted?"(-1,-1)":"("+K(a.offsetX,1)+", "+K(a.offsetY,1)+")";for(b=1;b<=F;b++)c=h.cloneNode(0),m=2*F+1-2*b,r(c,{isShadow:"true",stroke:a.color||"#000000","stroke-opacity":x*b,"stroke-width":m,transform:"translate"+y,fill:"none"}),e&&(r(c,"height",Math.max(r(c,"height")-m,0)),c.cutHeight=m),g?g.element.appendChild(c):h.parentNode.insertBefore(c,h),v.push(c);this.shadows=v}return this},destroyShadows:function(){z(this.shadows||
	[],function(a){this.safeRemoveChild(a)},this);this.shadows=void 0},xGetter:function(a){"circle"===this.element.nodeName&&("x"===a?a="cx":"y"===a&&(a="cy"));return this._defaultGetter(a)},_defaultGetter:function(a){a=K(this[a],this.element?this.element.getAttribute(a):null,0);/^[\-0-9\.]+$/.test(a)&&(a=parseFloat(a));return a},dSetter:function(a,g,e){a&&a.join&&(a=a.join(" "));/(NaN| {2}|^$)/.test(a)&&(a="M 0 0");e.setAttribute(g,a);this[g]=a},dashstyleSetter:function(a){var v,g=this["stroke-width"];
	"inherit"===g&&(g=1);if(a=a&&a.toLowerCase()){a=a.replace("shortdashdotdot","3,1,1,1,1,1,").replace("shortdashdot","3,1,1,1").replace("shortdot","1,1,").replace("shortdash","3,1,").replace("longdash","8,3,").replace(/dot/g,"1,3,").replace("dash","4,3,").replace(/,$/,"").split(",");for(v=a.length;v--;)a[v]=J(a[v])*g;a=a.join(",").replace(/NaN/g,"none");this.element.setAttribute("stroke-dasharray",a)}},alignSetter:function(a){this.element.setAttribute("text-anchor",{left:"start",center:"middle",right:"end"}[a])},
	opacitySetter:function(a,g,e){this[g]=a;e.setAttribute(g,a)},titleSetter:function(a){var v=this.element.getElementsByTagName("title")[0];v||(v=n.createElementNS(this.SVG_NS,"title"),this.element.appendChild(v));v.firstChild&&v.removeChild(v.firstChild);v.appendChild(n.createTextNode(String(K(a),"").replace(/<[^>]*>/g,"")))},textSetter:function(a){a!==this.textStr&&(delete this.bBox,this.textStr=a,this.added&&this.renderer.buildText(this))},fillSetter:function(a,g,e){"string"===typeof a?e.setAttribute(g,
	a):a&&this.colorGradient(a,g,e)},visibilitySetter:function(a,g,e){"inherit"===a?e.removeAttribute(g):e.setAttribute(g,a)},zIndexSetter:function(a,g){var v=this.renderer,e=this.parentGroup,b=(e||v).element||v.box,c,h=this.element,m;c=this.added;var F;u(a)&&(h.zIndex=a,a=+a,this[g]===a&&(c=!1),this[g]=a);if(c){(a=this.zIndex)&&e&&(e.handleZ=!0);g=b.childNodes;for(F=0;F<g.length&&!m;F++)e=g[F],c=e.zIndex,e!==h&&(J(c)>a||!u(a)&&u(c)||0>a&&!u(c)&&b!==v.box)&&(b.insertBefore(h,e),m=!0);m||b.appendChild(h)}return m},
	_defaultSetter:function(a,g,e){e.setAttribute(g,a)}};B.prototype.yGetter=B.prototype.xGetter;B.prototype.translateXSetter=B.prototype.translateYSetter=B.prototype.rotationSetter=B.prototype.verticalAlignSetter=B.prototype.scaleXSetter=B.prototype.scaleYSetter=function(a,g){this[g]=a;this.doTransform=!0};B.prototype["stroke-widthSetter"]=B.prototype.strokeSetter=function(a,g,e){this[g]=a;this.stroke&&this["stroke-width"]?(B.prototype.fillSetter.call(this,this.stroke,"stroke",e),e.setAttribute("stroke-width",
	this["stroke-width"]),this.hasStroke=!0):"stroke-width"===g&&0===a&&this.hasStroke&&(e.removeAttribute("stroke"),this.hasStroke=!1)};A=a.SVGRenderer=function(){this.init.apply(this,arguments)};A.prototype={Element:B,SVG_NS:N,init:function(a,g,b,c,h,F){var v;c=this.createElement("svg").attr({version:"1.1","class":"highcharts-root"}).css(this.getStyle(c));v=c.element;a.appendChild(v);-1===a.innerHTML.indexOf("xmlns")&&r(v,"xmlns",this.SVG_NS);this.isSVG=!0;this.box=v;this.boxWrapper=c;this.alignedObjects=
	[];this.url=(e||m)&&n.getElementsByTagName("base").length?O.location.href.replace(/#.*?$/,"").replace(/<[^>]*>/g,"").replace(/([\('\)])/g,"\\$1").replace(/ /g,"%20"):"";this.createElement("desc").add().element.appendChild(n.createTextNode("Created with Highcharts 5.0.10"));this.defs=this.createElement("defs").add();this.allowHTML=F;this.forExport=h;this.gradients={};this.cache={};this.cacheKeys=[];this.imgCount=0;this.setSize(g,b,!1);var x;e&&a.getBoundingClientRect&&(g=function(){q(a,{left:0,top:0});
	x=a.getBoundingClientRect();q(a,{left:Math.ceil(x.left)-x.left+"px",top:Math.ceil(x.top)-x.top+"px"})},g(),this.unSubPixelFix=H(O,"resize",g))},getStyle:function(a){return this.style=b({fontFamily:'"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',fontSize:"12px"},a)},setStyle:function(a){this.boxWrapper.css(this.getStyle(a))},isHidden:function(){return!this.boxWrapper.getBBox().width},destroy:function(){var a=this.defs;this.box=null;this.boxWrapper=this.boxWrapper.destroy();c(this.gradients||
	{});this.gradients=null;a&&(this.defs=a.destroy());this.unSubPixelFix&&this.unSubPixelFix();return this.alignedObjects=null},createElement:function(a){var g=new this.Element;g.init(this,a);return g},draw:I,getRadialAttr:function(a,g){return{cx:a[0]-a[2]/2+g.cx*a[2],cy:a[1]-a[2]/2+g.cy*a[2],r:g.r*a[2]}},getSpanWidth:function(a,g){var v=a.getBBox(!0).width;!Q&&this.forExport&&(v=this.measureSpanWidth(g.firstChild.data,a.styles));return v},applyEllipsis:function(a,g,e,b){var v=this.getSpanWidth(a,g),
	c=v>b,v=e,h,m=0,F=e.length,x=function(a){g.removeChild(g.firstChild);a&&g.appendChild(n.createTextNode(a))};if(c){for(;m<=F;)h=Math.ceil((m+F)/2),v=e.substring(0,h)+"\u2026",x(v),v=this.getSpanWidth(a,g),m===F?m=F+1:v>b?F=h-1:m=h;0===F&&x("")}return c},buildText:function(a){var g=a.element,e=this,v=e.forExport,b=K(a.textStr,"").toString(),c=-1!==b.indexOf("\x3c"),m=g.childNodes,F,x,y,t,d=r(g,"x"),C=a.styles,p=a.textWidth,k=C&&C.lineHeight,w=C&&C.textOutline,u=C&&"ellipsis"===C.textOverflow,f=C&&"nowrap"===
	C.whiteSpace,E=C&&C.fontSize,D,I,l=m.length,C=p&&!a.added&&this.box,P=function(a){var v;v=/(px|em)$/.test(a&&a.style.fontSize)?a.style.fontSize:E||e.style.fontSize||12;return k?J(k):e.fontMetrics(v,a.getAttribute("style")?a:g).h};D=[b,u,f,k,w,E,p].join();if(D!==a.textCache){for(a.textCache=D;l--;)g.removeChild(m[l]);c||w||u||p||-1!==b.indexOf(" ")?(F=/<.*class="([^"]+)".*>/,x=/<.*style="([^"]+)".*>/,y=/<.*href="(http[^"]+)".*>/,C&&C.appendChild(g),b=c?b.replace(/<(b|strong)>/g,'\x3cspan style\x3d"font-weight:bold"\x3e').replace(/<(i|em)>/g,
	'\x3cspan style\x3d"font-style:italic"\x3e').replace(/<a/g,"\x3cspan").replace(/<\/(b|strong|i|em|a)>/g,"\x3c/span\x3e").split(/<br.*?>/g):[b],b=h(b,function(a){return""!==a}),z(b,function(b,c){var m,h=0;b=b.replace(/^\s+|\s+$/g,"").replace(/<span/g,"|||\x3cspan").replace(/<\/span>/g,"\x3c/span\x3e|||");m=b.split("|||");z(m,function(b){if(""!==b||1===m.length){var C={},k=n.createElementNS(e.SVG_NS,"tspan"),w,E;F.test(b)&&(w=b.match(F)[1],r(k,"class",w));x.test(b)&&(E=b.match(x)[1].replace(/(;| |^)color([ :])/,
	"$1fill$2"),r(k,"style",E));y.test(b)&&!v&&(r(k,"onclick",'location.href\x3d"'+b.match(y)[1]+'"'),q(k,{cursor:"pointer"}));b=(b.replace(/<(.|\n)*?>/g,"")||" ").replace(/&lt;/g,"\x3c").replace(/&gt;/g,"\x3e");if(" "!==b){k.appendChild(n.createTextNode(b));h?C.dx=0:c&&null!==d&&(C.x=d);r(k,C);g.appendChild(k);!h&&I&&(!Q&&v&&q(k,{display:"block"}),r(k,"dy",P(k)));if(p){C=b.replace(/([^\^])-/g,"$1- ").split(" ");w=1<m.length||c||1<C.length&&!f;var D=[],M,z=P(k),l=a.rotation;for(u&&(t=e.applyEllipsis(a,
	k,b,p));!u&&w&&(C.length||D.length);)a.rotation=0,M=e.getSpanWidth(a,k),b=M>p,void 0===t&&(t=b),b&&1!==C.length?(k.removeChild(k.firstChild),D.unshift(C.pop())):(C=D,D=[],C.length&&!f&&(k=n.createElementNS(N,"tspan"),r(k,{dy:z,x:d}),E&&r(k,"style",E),g.appendChild(k)),M>p&&(p=M)),C.length&&k.appendChild(n.createTextNode(C.join(" ").replace(/- /g,"-")));a.rotation=l}h++}}});I=I||g.childNodes.length}),t&&a.attr("title",a.textStr),C&&C.removeChild(g),w&&a.applyTextOutline&&a.applyTextOutline(w)):g.appendChild(n.createTextNode(b.replace(/&lt;/g,
	"\x3c").replace(/&gt;/g,"\x3e")))}},getContrast:function(a){a=l(a).rgba;return 510<a[0]+a[1]+a[2]?"#000000":"#FFFFFF"},button:function(a,g,e,c,m,h,F,C,t){var v=this.label(a,g,e,t,null,null,null,null,"button"),d=0;v.attr(y({padding:8,r:2},m));var p,n,k,w;m=y({fill:"#f7f7f7",stroke:"#cccccc","stroke-width":1,style:{color:"#333333",cursor:"pointer",fontWeight:"normal"}},m);p=m.style;delete m.style;h=y(m,{fill:"#e6e6e6"},h);n=h.style;delete h.style;F=y(m,{fill:"#e6ebf5",style:{color:"#000000",fontWeight:"bold"}},
	F);k=F.style;delete F.style;C=y(m,{style:{color:"#cccccc"}},C);w=C.style;delete C.style;H(v.element,x?"mouseover":"mouseenter",function(){3!==d&&v.setState(1)});H(v.element,x?"mouseout":"mouseleave",function(){3!==d&&v.setState(d)});v.setState=function(a){1!==a&&(v.state=d=a);v.removeClass(/highcharts-button-(normal|hover|pressed|disabled)/).addClass("highcharts-button-"+["normal","hover","pressed","disabled"][a||0]);v.attr([m,h,F,C][a||0]).css([p,n,k,w][a||0])};v.attr(m).css(b({cursor:"default"},
	p));return v.on("click",function(a){3!==d&&c.call(v,a)})},crispLine:function(a,g){a[1]===a[4]&&(a[1]=a[4]=Math.round(a[1])-g%2/2);a[2]===a[5]&&(a[2]=a[5]=Math.round(a[2])+g%2/2);return a},path:function(a){var g={fill:"none"};w(a)?g.d=a:C(a)&&b(g,a);return this.createElement("path").attr(g)},circle:function(a,g,e){a=C(a)?a:{x:a,y:g,r:e};g=this.createElement("circle");g.xSetter=g.ySetter=function(a,g,e){e.setAttribute("c"+g,a)};return g.attr(a)},arc:function(a,g,e,b,c,m){C(a)?(b=a,g=b.y,e=b.r,a=b.x):
	b={innerR:b,start:c,end:m};a=this.symbol("arc",a,g,e,e,b);a.r=e;return a},rect:function(a,g,e,b,c,m){c=C(a)?a.r:c;var v=this.createElement("rect");a=C(a)?a:void 0===a?{}:{x:a,y:g,width:Math.max(e,0),height:Math.max(b,0)};void 0!==m&&(a.strokeWidth=m,a=v.crisp(a));a.fill="none";c&&(a.r=c);v.rSetter=function(a,g,e){r(e,{rx:a,ry:a})};return v.attr(a)},setSize:function(a,g,e){var b=this.alignedObjects,v=b.length;this.width=a;this.height=g;for(this.boxWrapper.animate({width:a,height:g},{step:function(){this.attr({viewBox:"0 0 "+
	this.attr("width")+" "+this.attr("height")})},duration:K(e,!0)?void 0:0});v--;)b[v].align()},g:function(a){var g=this.createElement("g");return a?g.attr({"class":"highcharts-"+a}):g},image:function(a,g,e,c,m){var v={preserveAspectRatio:"none"};1<arguments.length&&b(v,{x:g,y:e,width:c,height:m});v=this.createElement("image").attr(v);v.element.setAttributeNS?v.element.setAttributeNS("http://www.w3.org/1999/xlink","href",a):v.element.setAttribute("hc-svg-href",a);return v},symbol:function(a,g,e,c,m,
	h){var v=this,F,x=this.symbols[a],y=u(g)&&x&&this.symbols[a](Math.round(g),Math.round(e),c,m,h),C=/^url\((.*?)\)$/,d,t;x?(F=this.path(y),F.attr("fill","none"),b(F,{symbolName:a,x:g,y:e,width:c,height:m}),h&&b(F,h)):C.test(a)&&(d=a.match(C)[1],F=this.image(d),F.imgwidth=K(P[d]&&P[d].width,h&&h.width),F.imgheight=K(P[d]&&P[d].height,h&&h.height),t=function(){F.attr({width:F.width,height:F.height})},z(["width","height"],function(a){F[a+"Setter"]=function(a,g){var e={},b=this["img"+g],v="width"===g?"translateX":
	"translateY";this[g]=a;u(b)&&(this.element&&this.element.setAttribute(g,b),this.alignByTranslate||(e[v]=((this[g]||0)-b)/2,this.attr(e)))}}),u(g)&&F.attr({x:g,y:e}),F.isImg=!0,u(F.imgwidth)&&u(F.imgheight)?t():(F.attr({width:0,height:0}),k("img",{onload:function(){var a=f[v.chartIndex];0===this.width&&(q(this,{position:"absolute",top:"-999em"}),n.body.appendChild(this));P[d]={width:this.width,height:this.height};F.imgwidth=this.width;F.imgheight=this.height;F.element&&t();this.parentNode&&this.parentNode.removeChild(this);
	v.imgCount--;if(!v.imgCount&&a&&a.onload)a.onload()},src:d}),this.imgCount++));return F},symbols:{circle:function(a,g,e,b){return this.arc(a+e/2,g+b/2,e/2,b/2,{start:0,end:2*Math.PI,open:!1})},square:function(a,g,e,b){return["M",a,g,"L",a+e,g,a+e,g+b,a,g+b,"Z"]},triangle:function(a,g,e,b){return["M",a+e/2,g,"L",a+e,g+b,a,g+b,"Z"]},"triangle-down":function(a,g,e,b){return["M",a,g,"L",a+e,g,a+e/2,g+b,"Z"]},diamond:function(a,g,e,b){return["M",a+e/2,g,"L",a+e,g+b/2,a+e/2,g+b,a,g+b/2,"Z"]},arc:function(a,
	g,e,b,c){var v=c.start,m=c.r||e,h=c.r||b||e,F=c.end-.001;e=c.innerR;b=c.open;var x=Math.cos(v),y=Math.sin(v),C=Math.cos(F),F=Math.sin(F);c=c.end-v<Math.PI?0:1;m=["M",a+m*x,g+h*y,"A",m,h,0,c,1,a+m*C,g+h*F];u(e)&&m.push(b?"M":"L",a+e*C,g+e*F,"A",e,e,0,c,0,a+e*x,g+e*y);m.push(b?"":"Z");return m},callout:function(a,g,e,b,c){var m=Math.min(c&&c.r||0,e,b),h=m+6,v=c&&c.anchorX;c=c&&c.anchorY;var F;F=["M",a+m,g,"L",a+e-m,g,"C",a+e,g,a+e,g,a+e,g+m,"L",a+e,g+b-m,"C",a+e,g+b,a+e,g+b,a+e-m,g+b,"L",a+m,g+b,"C",
	a,g+b,a,g+b,a,g+b-m,"L",a,g+m,"C",a,g,a,g,a+m,g];v&&v>e?c>g+h&&c<g+b-h?F.splice(13,3,"L",a+e,c-6,a+e+6,c,a+e,c+6,a+e,g+b-m):F.splice(13,3,"L",a+e,b/2,v,c,a+e,b/2,a+e,g+b-m):v&&0>v?c>g+h&&c<g+b-h?F.splice(33,3,"L",a,c+6,a-6,c,a,c-6,a,g+m):F.splice(33,3,"L",a,b/2,v,c,a,b/2,a,g+m):c&&c>b&&v>a+h&&v<a+e-h?F.splice(23,3,"L",v+6,g+b,v,g+b+6,v-6,g+b,a+m,g+b):c&&0>c&&v>a+h&&v<a+e-h&&F.splice(3,3,"L",v-6,g,v,g-6,v+6,g,e-m,g);return F}},clipRect:function(g,e,b,c){var m=a.uniqueKey(),h=this.createElement("clipPath").attr({id:m}).add(this.defs);
	g=this.rect(g,e,b,c,0).add(h);g.id=m;g.clipPath=h;g.count=0;return g},text:function(a,g,e,b){var c=!Q&&this.forExport,m={};if(b&&(this.allowHTML||!this.forExport))return this.html(a,g,e);m.x=Math.round(g||0);e&&(m.y=Math.round(e));if(a||0===a)m.text=a;a=this.createElement("text").attr(m);c&&a.css({position:"absolute"});b||(a.xSetter=function(a,g,e){var b=e.getElementsByTagName("tspan"),c,m=e.getAttribute(g),h;for(h=0;h<b.length;h++)c=b[h],c.getAttribute(g)===m&&c.setAttribute(g,a);e.setAttribute(g,
	a)});return a},fontMetrics:function(a,g){a=a||g&&g.style&&g.style.fontSize||this.style&&this.style.fontSize;a=/px/.test(a)?J(a):/em/.test(a)?parseFloat(a)*(g?this.fontMetrics(null,g.parentNode).f:16):12;g=24>a?a+3:Math.round(1.2*a);return{h:g,b:Math.round(.8*g),f:a}},rotCorr:function(a,g,e){var b=a;g&&e&&(b=Math.max(b*Math.cos(g*d),4));return{x:-a/3*Math.sin(g*d),y:b}},label:function(e,c,m,h,F,x,C,d,t){var v=this,p=v.g("button"!==t&&"label"),n=p.text=v.text("",0,0,C).attr({zIndex:1}),k,w,E=0,f=3,
	D=0,I,q,l,Q,N,K={},J,r,M=/^url\((.*?)\)$/.test(h),P=M,R,S,O,U;t&&p.addClass("highcharts-"+t);P=M;R=function(){return(J||0)%2/2};S=function(){var a=n.element.style,g={};w=(void 0===I||void 0===q||N)&&u(n.textStr)&&n.getBBox();p.width=(I||w.width||0)+2*f+D;p.height=(q||w.height||0)+2*f;r=f+v.fontMetrics(a&&a.fontSize,n).b;P&&(k||(p.box=k=v.symbols[h]||M?v.symbol(h):v.rect(),k.addClass(("button"===t?"":"highcharts-label-box")+(t?" highcharts-"+t+"-box":"")),k.add(p),a=R(),g.x=a,g.y=(d?-r:0)+a),g.width=
	Math.round(p.width),g.height=Math.round(p.height),k.attr(b(g,K)),K={})};O=function(){var a=D+f,g;g=d?0:r;u(I)&&w&&("center"===N||"right"===N)&&(a+={center:.5,right:1}[N]*(I-w.width));if(a!==n.x||g!==n.y)n.attr("x",a),void 0!==g&&n.attr("y",g);n.x=a;n.y=g};U=function(a,g){k?k.attr(a,g):K[a]=g};p.onAdd=function(){n.add(p);p.attr({text:e||0===e?e:"",x:c,y:m});k&&u(F)&&p.attr({anchorX:F,anchorY:x})};p.widthSetter=function(g){I=a.isNumber(g)?g:null};p.heightSetter=function(a){q=a};p["text-alignSetter"]=
	function(a){N=a};p.paddingSetter=function(a){u(a)&&a!==f&&(f=p.padding=a,O())};p.paddingLeftSetter=function(a){u(a)&&a!==D&&(D=a,O())};p.alignSetter=function(a){a={left:0,center:.5,right:1}[a];a!==E&&(E=a,w&&p.attr({x:l}))};p.textSetter=function(a){void 0!==a&&n.textSetter(a);S();O()};p["stroke-widthSetter"]=function(a,g){a&&(P=!0);J=this["stroke-width"]=a;U(g,a)};p.strokeSetter=p.fillSetter=p.rSetter=function(a,g){"fill"===g&&a&&(P=!0);U(g,a)};p.anchorXSetter=function(a,g){F=a;U(g,Math.round(a)-
	R()-l)};p.anchorYSetter=function(a,g){x=a;U(g,a-Q)};p.xSetter=function(a){p.x=a;E&&(a-=E*((I||w.width)+2*f));l=Math.round(a);p.attr("translateX",l)};p.ySetter=function(a){Q=p.y=Math.round(a);p.attr("translateY",Q)};var W=p.css;return b(p,{css:function(a){if(a){var g={};a=y(a);z(p.textProps,function(e){void 0!==a[e]&&(g[e]=a[e],delete a[e])});n.css(g)}return W.call(p,a)},getBBox:function(){return{width:w.width+2*f,height:w.height+2*f,x:w.x-f,y:w.y-f}},shadow:function(a){a&&(S(),k&&k.shadow(a));return p},
	destroy:function(){g(p.element,"mouseenter");g(p.element,"mouseleave");n&&(n=n.destroy());k&&(k=k.destroy());B.prototype.destroy.call(p);p=v=S=O=U=null}})}};a.Renderer=A})(L);(function(a){var B=a.attr,A=a.createElement,H=a.css,G=a.defined,r=a.each,f=a.extend,l=a.isFirefox,q=a.isMS,k=a.isWebKit,u=a.pInt,d=a.SVGRenderer,c=a.win,n=a.wrap;f(a.SVGElement.prototype,{htmlCss:function(a){var b=this.element;if(b=a&&"SPAN"===b.tagName&&a.width)delete a.width,this.textWidth=b,this.updateTransform();a&&"ellipsis"===
	a.textOverflow&&(a.whiteSpace="nowrap",a.overflow="hidden");this.styles=f(this.styles,a);H(this.element,a);return this},htmlGetBBox:function(){var a=this.element;"text"===a.nodeName&&(a.style.position="absolute");return{x:a.offsetLeft,y:a.offsetTop,width:a.offsetWidth,height:a.offsetHeight}},htmlUpdateTransform:function(){if(this.added){var a=this.renderer,b=this.element,c=this.translateX||0,h=this.translateY||0,d=this.x||0,n=this.y||0,w=this.textAlign||"left",e={left:0,center:.5,right:1}[w],x=this.styles;
	H(b,{marginLeft:c,marginTop:h});this.shadows&&r(this.shadows,function(a){H(a,{marginLeft:c+1,marginTop:h+1})});this.inverted&&r(b.childNodes,function(e){a.invertChild(e,b)});if("SPAN"===b.tagName){var C=this.rotation,f=u(this.textWidth),m=x&&x.whiteSpace,y=[C,w,b.innerHTML,this.textWidth,this.textAlign].join();y!==this.cTT&&(x=a.fontMetrics(b.style.fontSize).b,G(C)&&this.setSpanRotation(C,e,x),H(b,{width:"",whiteSpace:m||"nowrap"}),b.offsetWidth>f&&/[ \-]/.test(b.textContent||b.innerText)&&H(b,{width:f+
	"px",display:"block",whiteSpace:m||"normal"}),this.getSpanCorrection(b.offsetWidth,x,e,C,w));H(b,{left:d+(this.xCorr||0)+"px",top:n+(this.yCorr||0)+"px"});k&&(x=b.offsetHeight);this.cTT=y}}else this.alignOnAdd=!0},setSpanRotation:function(a,b,p){var h={},d=q?"-ms-transform":k?"-webkit-transform":l?"MozTransform":c.opera?"-o-transform":"";h[d]=h.transform="rotate("+a+"deg)";h[d+(l?"Origin":"-origin")]=h.transformOrigin=100*b+"% "+p+"px";H(this.element,h)},getSpanCorrection:function(a,b,c){this.xCorr=
	-a*c;this.yCorr=-b}});f(d.prototype,{html:function(a,b,c){var h=this.createElement("span"),d=h.element,p=h.renderer,k=p.isSVG,e=function(a,e){r(["opacity","visibility"],function(b){n(a,b+"Setter",function(a,b,c,h){a.call(this,b,c,h);e[c]=b})})};h.textSetter=function(a){a!==d.innerHTML&&delete this.bBox;d.innerHTML=this.textStr=a;h.htmlUpdateTransform()};k&&e(h,h.element.style);h.xSetter=h.ySetter=h.alignSetter=h.rotationSetter=function(a,e){"align"===e&&(e="textAlign");h[e]=a;h.htmlUpdateTransform()};
	h.attr({text:a,x:Math.round(b),y:Math.round(c)}).css({fontFamily:this.style.fontFamily,fontSize:this.style.fontSize,position:"absolute"});d.style.whiteSpace="nowrap";h.css=h.htmlCss;k&&(h.add=function(a){var b,c=p.box.parentNode,m=[];if(this.parentGroup=a){if(b=a.div,!b){for(;a;)m.push(a),a=a.parentGroup;r(m.reverse(),function(a){var x,d=B(a.element,"class");d&&(d={className:d});b=a.div=a.div||A("div",d,{position:"absolute",left:(a.translateX||0)+"px",top:(a.translateY||0)+"px",display:a.display,
	opacity:a.opacity,pointerEvents:a.styles&&a.styles.pointerEvents},b||c);x=b.style;f(a,{on:function(){h.on.apply({element:m[0].div},arguments);return a},translateXSetter:function(e,g){x.left=e+"px";a[g]=e;a.doTransform=!0},translateYSetter:function(e,g){x.top=e+"px";a[g]=e;a.doTransform=!0}});e(a,x)})}}else b=c;b.appendChild(d);h.added=!0;h.alignOnAdd&&h.htmlUpdateTransform();return h});return h}})})(L);(function(a){var B,A,H=a.createElement,G=a.css,r=a.defined,f=a.deg2rad,l=a.discardElement,q=a.doc,
	k=a.each,u=a.erase,d=a.extend;B=a.extendClass;var c=a.isArray,n=a.isNumber,z=a.isObject,b=a.merge;A=a.noop;var p=a.pick,h=a.pInt,t=a.SVGElement,D=a.SVGRenderer,w=a.win;a.svg||(A={docMode8:q&&8===q.documentMode,init:function(a,b){var e=["\x3c",b,' filled\x3d"f" stroked\x3d"f"'],c=["position: ","absolute",";"],m="div"===b;("shape"===b||m)&&c.push("left:0;top:0;width:1px;height:1px;");c.push("visibility: ",m?"hidden":"visible");e.push(' style\x3d"',c.join(""),'"/\x3e');b&&(e=m||"span"===b||"img"===b?
	e.join(""):a.prepVML(e),this.element=H(e));this.renderer=a},add:function(a){var e=this.renderer,b=this.element,c=e.box,m=a&&a.inverted,c=a?a.element||a:c;a&&(this.parentGroup=a);m&&e.invertChild(b,c);c.appendChild(b);this.added=!0;this.alignOnAdd&&!this.deferUpdateTransform&&this.updateTransform();if(this.onAdd)this.onAdd();this.className&&this.attr("class",this.className);return this},updateTransform:t.prototype.htmlUpdateTransform,setSpanRotation:function(){var a=this.rotation,b=Math.cos(a*f),c=
	Math.sin(a*f);G(this.element,{filter:a?["progid:DXImageTransform.Microsoft.Matrix(M11\x3d",b,", M12\x3d",-c,", M21\x3d",c,", M22\x3d",b,", sizingMethod\x3d'auto expand')"].join(""):"none"})},getSpanCorrection:function(a,b,c,h,m){var e=h?Math.cos(h*f):1,x=h?Math.sin(h*f):0,d=p(this.elemHeight,this.element.offsetHeight),t;this.xCorr=0>e&&-a;this.yCorr=0>x&&-d;t=0>e*x;this.xCorr+=x*b*(t?1-c:c);this.yCorr-=e*b*(h?t?c:1-c:1);m&&"left"!==m&&(this.xCorr-=a*c*(0>e?-1:1),h&&(this.yCorr-=d*c*(0>x?-1:1)),G(this.element,
	{textAlign:m}))},pathToVML:function(a){for(var e=a.length,b=[];e--;)n(a[e])?b[e]=Math.round(10*a[e])-5:"Z"===a[e]?b[e]="x":(b[e]=a[e],!a.isArc||"wa"!==a[e]&&"at"!==a[e]||(b[e+5]===b[e+7]&&(b[e+7]+=a[e+7]>a[e+5]?1:-1),b[e+6]===b[e+8]&&(b[e+8]+=a[e+8]>a[e+6]?1:-1)));return b.join(" ")||"x"},clip:function(a){var e=this,b;a?(b=a.members,u(b,e),b.push(e),e.destroyClip=function(){u(b,e)},a=a.getCSS(e)):(e.destroyClip&&e.destroyClip(),a={clip:e.docMode8?"inherit":"rect(auto)"});return e.css(a)},css:t.prototype.htmlCss,
	safeRemoveChild:function(a){a.parentNode&&l(a)},destroy:function(){this.destroyClip&&this.destroyClip();return t.prototype.destroy.apply(this)},on:function(a,b){this.element["on"+a]=function(){var a=w.event;a.target=a.srcElement;b(a)};return this},cutOffPath:function(a,b){var e;a=a.split(/[ ,]/);e=a.length;if(9===e||11===e)a[e-4]=a[e-2]=h(a[e-2])-10*b;return a.join(" ")},shadow:function(a,b,c){var e=[],m,d=this.element,t=this.renderer,x,n=d.style,g,F=d.path,k,C,w,f;F&&"string"!==typeof F.value&&(F=
	"x");C=F;if(a){w=p(a.width,3);f=(a.opacity||.15)/w;for(m=1;3>=m;m++)k=2*w+1-2*m,c&&(C=this.cutOffPath(F.value,k+.5)),g=['\x3cshape isShadow\x3d"true" strokeweight\x3d"',k,'" filled\x3d"false" path\x3d"',C,'" coordsize\x3d"10 10" style\x3d"',d.style.cssText,'" /\x3e'],x=H(t.prepVML(g),null,{left:h(n.left)+p(a.offsetX,1),top:h(n.top)+p(a.offsetY,1)}),c&&(x.cutOff=k+1),g=['\x3cstroke color\x3d"',a.color||"#000000",'" opacity\x3d"',f*m,'"/\x3e'],H(t.prepVML(g),null,null,x),b?b.element.appendChild(x):
	d.parentNode.insertBefore(x,d),e.push(x);this.shadows=e}return this},updateShadows:A,setAttr:function(a,b){this.docMode8?this.element[a]=b:this.element.setAttribute(a,b)},classSetter:function(a){(this.added?this.element:this).className=a},dashstyleSetter:function(a,b,c){(c.getElementsByTagName("stroke")[0]||H(this.renderer.prepVML(["\x3cstroke/\x3e"]),null,null,c))[b]=a||"solid";this[b]=a},dSetter:function(a,b,c){var e=this.shadows;a=a||[];this.d=a.join&&a.join(" ");c.path=a=this.pathToVML(a);if(e)for(c=
	e.length;c--;)e[c].path=e[c].cutOff?this.cutOffPath(a,e[c].cutOff):a;this.setAttr(b,a)},fillSetter:function(a,b,c){var e=c.nodeName;"SPAN"===e?c.style.color=a:"IMG"!==e&&(c.filled="none"!==a,this.setAttr("fillcolor",this.renderer.color(a,c,b,this)))},"fill-opacitySetter":function(a,b,c){H(this.renderer.prepVML(["\x3c",b.split("-")[0],' opacity\x3d"',a,'"/\x3e']),null,null,c)},opacitySetter:A,rotationSetter:function(a,b,c){c=c.style;this[b]=c[b]=a;c.left=-Math.round(Math.sin(a*f)+1)+"px";c.top=Math.round(Math.cos(a*
	f))+"px"},strokeSetter:function(a,b,c){this.setAttr("strokecolor",this.renderer.color(a,c,b,this))},"stroke-widthSetter":function(a,b,c){c.stroked=!!a;this[b]=a;n(a)&&(a+="px");this.setAttr("strokeweight",a)},titleSetter:function(a,b){this.setAttr(b,a)},visibilitySetter:function(a,b,c){"inherit"===a&&(a="visible");this.shadows&&k(this.shadows,function(e){e.style[b]=a});"DIV"===c.nodeName&&(a="hidden"===a?"-999em":0,this.docMode8||(c.style[b]=a?"visible":"hidden"),b="top");c.style[b]=a},xSetter:function(a,
	b,c){this[b]=a;"x"===b?b="left":"y"===b&&(b="top");this.updateClipping?(this[b]=a,this.updateClipping()):c.style[b]=a},zIndexSetter:function(a,b,c){c.style[b]=a}},A["stroke-opacitySetter"]=A["fill-opacitySetter"],a.VMLElement=A=B(t,A),A.prototype.ySetter=A.prototype.widthSetter=A.prototype.heightSetter=A.prototype.xSetter,A={Element:A,isIE8:-1<w.navigator.userAgent.indexOf("MSIE 8.0"),init:function(a,b,c){var e,m;this.alignedObjects=[];e=this.createElement("div").css({position:"relative"});m=e.element;
	a.appendChild(e.element);this.isVML=!0;this.box=m;this.boxWrapper=e;this.gradients={};this.cache={};this.cacheKeys=[];this.imgCount=0;this.setSize(b,c,!1);if(!q.namespaces.hcv){q.namespaces.add("hcv","urn:schemas-microsoft-com:vml");try{q.createStyleSheet().cssText="hcv\\:fill, hcv\\:path, hcv\\:shape, hcv\\:stroke{ behavior:url(#default#VML); display: inline-block; } "}catch(y){q.styleSheets[0].cssText+="hcv\\:fill, hcv\\:path, hcv\\:shape, hcv\\:stroke{ behavior:url(#default#VML); display: inline-block; } "}}},
	isHidden:function(){return!this.box.offsetWidth},clipRect:function(a,b,c,h){var e=this.createElement(),p=z(a);return d(e,{members:[],count:0,left:(p?a.x:a)+1,top:(p?a.y:b)+1,width:(p?a.width:c)-1,height:(p?a.height:h)-1,getCSS:function(a){var b=a.element,c=b.nodeName,g=a.inverted,e=this.top-("shape"===c?b.offsetTop:0),m=this.left,b=m+this.width,h=e+this.height,e={clip:"rect("+Math.round(g?m:e)+"px,"+Math.round(g?h:b)+"px,"+Math.round(g?b:h)+"px,"+Math.round(g?e:m)+"px)"};!g&&a.docMode8&&"DIV"===c&&
	d(e,{width:b+"px",height:h+"px"});return e},updateClipping:function(){k(e.members,function(a){a.element&&a.css(e.getCSS(a))})}})},color:function(b,c,h,d){var e=this,p,t=/^rgba/,n,x,g="none";b&&b.linearGradient?x="gradient":b&&b.radialGradient&&(x="pattern");if(x){var F,w,f=b.linearGradient||b.radialGradient,C,u,v,D,q,l="";b=b.stops;var z,E=[],r=function(){n=['\x3cfill colors\x3d"'+E.join(",")+'" opacity\x3d"',v,'" o:opacity2\x3d"',u,'" type\x3d"',x,'" ',l,'focus\x3d"100%" method\x3d"any" /\x3e'];
	H(e.prepVML(n),null,null,c)};C=b[0];z=b[b.length-1];0<C[0]&&b.unshift([0,C[1]]);1>z[0]&&b.push([1,z[1]]);k(b,function(g,b){t.test(g[1])?(p=a.color(g[1]),F=p.get("rgb"),w=p.get("a")):(F=g[1],w=1);E.push(100*g[0]+"% "+F);b?(v=w,D=F):(u=w,q=F)});if("fill"===h)if("gradient"===x)h=f.x1||f[0]||0,b=f.y1||f[1]||0,C=f.x2||f[2]||0,f=f.y2||f[3]||0,l='angle\x3d"'+(90-180*Math.atan((f-b)/(C-h))/Math.PI)+'"',r();else{var g=f.r,A=2*g,B=2*g,G=f.cx,V=f.cy,L=c.radialReference,T,g=function(){L&&(T=d.getBBox(),G+=(L[0]-
	T.x)/T.width-.5,V+=(L[1]-T.y)/T.height-.5,A*=L[2]/T.width,B*=L[2]/T.height);l='src\x3d"'+a.getOptions().global.VMLRadialGradientURL+'" size\x3d"'+A+","+B+'" origin\x3d"0.5,0.5" position\x3d"'+G+","+V+'" color2\x3d"'+q+'" ';r()};d.added?g():d.onAdd=g;g=D}else g=F}else t.test(b)&&"IMG"!==c.tagName?(p=a.color(b),d[h+"-opacitySetter"](p.get("a"),h,c),g=p.get("rgb")):(g=c.getElementsByTagName(h),g.length&&(g[0].opacity=1,g[0].type="solid"),g=b);return g},prepVML:function(a){var b=this.isIE8;a=a.join("");
	b?(a=a.replace("/\x3e",' xmlns\x3d"urn:schemas-microsoft-com:vml" /\x3e'),a=-1===a.indexOf('style\x3d"')?a.replace("/\x3e",' style\x3d"display:inline-block;behavior:url(#default#VML);" /\x3e'):a.replace('style\x3d"','style\x3d"display:inline-block;behavior:url(#default#VML);')):a=a.replace("\x3c","\x3chcv:");return a},text:D.prototype.html,path:function(a){var b={coordsize:"10 10"};c(a)?b.d=a:z(a)&&d(b,a);return this.createElement("shape").attr(b)},circle:function(a,b,c){var e=this.symbol("circle");
	z(a)&&(c=a.r,b=a.y,a=a.x);e.isCircle=!0;e.r=c;return e.attr({x:a,y:b})},g:function(a){var b;a&&(b={className:"highcharts-"+a,"class":"highcharts-"+a});return this.createElement("div").attr(b)},image:function(a,b,c,h,m){var e=this.createElement("img").attr({src:a});1<arguments.length&&e.attr({x:b,y:c,width:h,height:m});return e},createElement:function(a){return"rect"===a?this.symbol(a):D.prototype.createElement.call(this,a)},invertChild:function(a,b){var c=this;b=b.style;var e="IMG"===a.tagName&&a.style;
	G(a,{flip:"x",left:h(b.width)-(e?h(e.top):1),top:h(b.height)-(e?h(e.left):1),rotation:-90});k(a.childNodes,function(b){c.invertChild(b,a)})},symbols:{arc:function(a,b,c,h,m){var e=m.start,d=m.end,p=m.r||c||h;c=m.innerR;h=Math.cos(e);var t=Math.sin(e),g=Math.cos(d),F=Math.sin(d);if(0===d-e)return["x"];e=["wa",a-p,b-p,a+p,b+p,a+p*h,b+p*t,a+p*g,b+p*F];m.open&&!c&&e.push("e","M",a,b);e.push("at",a-c,b-c,a+c,b+c,a+c*g,b+c*F,a+c*h,b+c*t,"x","e");e.isArc=!0;return e},circle:function(a,b,c,h,m){m&&r(m.r)&&
	(c=h=2*m.r);m&&m.isCircle&&(a-=c/2,b-=h/2);return["wa",a,b,a+c,b+h,a+c,b+h/2,a+c,b+h/2,"e"]},rect:function(a,b,c,h,m){return D.prototype.symbols[r(m)&&m.r?"callout":"square"].call(0,a,b,c,h,m)}}},a.VMLRenderer=B=function(){this.init.apply(this,arguments)},B.prototype=b(D.prototype,A),a.Renderer=B);D.prototype.measureSpanWidth=function(a,b){var c=q.createElement("span");a=q.createTextNode(a);c.appendChild(a);G(c,b);this.box.appendChild(c);b=c.offsetWidth;l(c);return b}})(L);(function(a){function B(){var k=
	a.defaultOptions.global,f=q.moment;if(k.timezone){if(f)return function(a){return-f.tz(a,k.timezone).utcOffset()};a.error(25)}return k.useUTC&&k.getTimezoneOffset}function A(){var k=a.defaultOptions.global,f,d=k.useUTC,c=d?"getUTC":"get",n=d?"setUTC":"set";a.Date=f=k.Date||q.Date;f.hcTimezoneOffset=d&&k.timezoneOffset;f.hcGetTimezoneOffset=B();f.hcMakeTime=function(a,b,c,h,t,n){var p;d?(p=f.UTC.apply(0,arguments),p+=r(p)):p=(new f(a,b,l(c,1),l(h,0),l(t,0),l(n,0))).getTime();return p};G("Minutes Hours Day Date Month FullYear".split(" "),
	function(a){f["hcGet"+a]=c+a});G("Milliseconds Seconds Minutes Hours Date Month FullYear".split(" "),function(a){f["hcSet"+a]=n+a})}var H=a.color,G=a.each,r=a.getTZOffset,f=a.merge,l=a.pick,q=a.win;a.defaultOptions={colors:"#7cb5ec #434348 #90ed7d #f7a35c #8085e9 #f15c80 #e4d354 #2b908f #f45b5b #91e8e1".split(" "),symbols:["circle","diamond","square","triangle","triangle-down"],lang:{loading:"Loading...",months:"January February March April May June July August September October November December".split(" "),
	shortMonths:"Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" "),weekdays:"Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),decimalPoint:".",numericSymbols:"kMGTPE".split(""),resetZoom:"Reset zoom",resetZoomTitle:"Reset zoom level 1:1",thousandsSep:" "},global:{useUTC:!0,VMLRadialGradientURL:"http://code.highcharts.com/5.0.10/gfx/vml-radial-gradient.png"},chart:{borderRadius:0,defaultSeriesType:"line",ignoreHiddenSeries:!0,spacing:[10,10,15,10],resetZoomButton:{theme:{zIndex:20},
	position:{align:"right",x:-10,y:10}},width:null,height:null,borderColor:"#335cad",backgroundColor:"#ffffff",plotBorderColor:"#cccccc"},title:{text:"Chart title",align:"center",margin:15,widthAdjust:-44},subtitle:{text:"",align:"center",widthAdjust:-44},plotOptions:{},labels:{style:{position:"absolute",color:"#333333"}},legend:{enabled:!0,align:"center",layout:"horizontal",labelFormatter:function(){return this.name},borderColor:"#999999",borderRadius:0,navigation:{activeColor:"#003399",inactiveColor:"#cccccc"},
	itemStyle:{color:"#333333",fontSize:"12px",fontWeight:"bold"},itemHoverStyle:{color:"#000000"},itemHiddenStyle:{color:"#cccccc"},shadow:!1,itemCheckboxStyle:{position:"absolute",width:"13px",height:"13px"},squareSymbol:!0,symbolPadding:5,verticalAlign:"bottom",x:0,y:0,title:{style:{fontWeight:"bold"}}},loading:{labelStyle:{fontWeight:"bold",position:"relative",top:"45%"},style:{position:"absolute",backgroundColor:"#ffffff",opacity:.5,textAlign:"center"}},tooltip:{enabled:!0,animation:a.svg,borderRadius:3,
	dateTimeLabelFormats:{millisecond:"%A, %b %e, %H:%M:%S.%L",second:"%A, %b %e, %H:%M:%S",minute:"%A, %b %e, %H:%M",hour:"%A, %b %e, %H:%M",day:"%A, %b %e, %Y",week:"Week from %A, %b %e, %Y",month:"%B %Y",year:"%Y"},footerFormat:"",padding:8,snap:a.isTouchDevice?25:10,backgroundColor:H("#f7f7f7").setOpacity(.85).get(),borderWidth:1,headerFormat:'\x3cspan style\x3d"font-size: 10px"\x3e{point.key}\x3c/span\x3e\x3cbr/\x3e',pointFormat:'\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e {series.name}: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e',
	shadow:!0,style:{color:"#333333",cursor:"default",fontSize:"12px",pointerEvents:"none",whiteSpace:"nowrap"}},credits:{enabled:!0,href:"http://www.highcharts.com",position:{align:"right",x:-10,verticalAlign:"bottom",y:-5},style:{cursor:"pointer",color:"#999999",fontSize:"9px"},text:"Highcharts.com"}};a.setOptions=function(k){a.defaultOptions=f(!0,a.defaultOptions,k);A();return a.defaultOptions};a.getOptions=function(){return a.defaultOptions};a.defaultPlotOptions=a.defaultOptions.plotOptions;A()})(L);
	(function(a){var B=a.arrayMax,A=a.arrayMin,H=a.defined,G=a.destroyObjectProperties,r=a.each,f=a.erase,l=a.merge,q=a.pick;a.PlotLineOrBand=function(a,f){this.axis=a;f&&(this.options=f,this.id=f.id)};a.PlotLineOrBand.prototype={render:function(){var a=this,f=a.axis,d=f.horiz,c=a.options,n=c.label,z=a.label,b=c.to,p=c.from,h=c.value,t=H(p)&&H(b),D=H(h),w=a.svgElem,e=!w,x=[],C,E=c.color,m=q(c.zIndex,0),y=c.events,x={"class":"highcharts-plot-"+(t?"band ":"line ")+(c.className||"")},I={},r=f.chart.renderer,
	J=t?"bands":"lines",g=f.log2lin;f.isLog&&(p=g(p),b=g(b),h=g(h));D?(x={stroke:E,"stroke-width":c.width},c.dashStyle&&(x.dashstyle=c.dashStyle)):t&&(E&&(x.fill=E),c.borderWidth&&(x.stroke=c.borderColor,x["stroke-width"]=c.borderWidth));I.zIndex=m;J+="-"+m;(E=f.plotLinesAndBandsGroups[J])||(f.plotLinesAndBandsGroups[J]=E=r.g("plot-"+J).attr(I).add());e&&(a.svgElem=w=r.path().attr(x).add(E));if(D)x=f.getPlotLinePath(h,w.strokeWidth());else if(t)x=f.getPlotBandPath(p,b,c);else return;if(e&&x&&x.length){if(w.attr({d:x}),
	y)for(C in c=function(g){w.on(g,function(b){y[g].apply(a,[b])})},y)c(C)}else w&&(x?(w.show(),w.animate({d:x})):(w.hide(),z&&(a.label=z=z.destroy())));n&&H(n.text)&&x&&x.length&&0<f.width&&0<f.height&&!x.flat?(n=l({align:d&&t&&"center",x:d?!t&&4:10,verticalAlign:!d&&t&&"middle",y:d?t?16:10:t?6:-4,rotation:d&&!t&&90},n),this.renderLabel(n,x,t,m)):z&&z.hide();return a},renderLabel:function(a,f,d,c){var n=this.label,k=this.axis.chart.renderer;n||(n={align:a.textAlign||a.align,rotation:a.rotation,"class":"highcharts-plot-"+
	(d?"band":"line")+"-label "+(a.className||"")},n.zIndex=c,this.label=n=k.text(a.text,0,0,a.useHTML).attr(n).add(),n.css(a.style));c=[f[1],f[4],d?f[6]:f[1]];f=[f[2],f[5],d?f[7]:f[2]];d=A(c);k=A(f);n.align(a,!1,{x:d,y:k,width:B(c)-d,height:B(f)-k});n.show()},destroy:function(){f(this.axis.plotLinesAndBands,this);delete this.axis;G(this)}};a.AxisPlotLineOrBandExtension={getPlotBandPath:function(a,f){var d=this.getPlotLinePath(f,null,null,!0),c=this.getPlotLinePath(a,null,null,!0),n=this.horiz,k=1;a=
	a<this.min&&f<this.min||a>this.max&&f>this.max;c&&d?(a&&(c.flat=c.toString()===d.toString(),k=0),c.push(n&&d[4]===c[4]?d[4]+k:d[4],n||d[5]!==c[5]?d[5]:d[5]+k,n&&d[1]===c[1]?d[1]+k:d[1],n||d[2]!==c[2]?d[2]:d[2]+k)):c=null;return c},addPlotBand:function(a){return this.addPlotBandOrLine(a,"plotBands")},addPlotLine:function(a){return this.addPlotBandOrLine(a,"plotLines")},addPlotBandOrLine:function(f,q){var d=(new a.PlotLineOrBand(this,f)).render(),c=this.userOptions;d&&(q&&(c[q]=c[q]||[],c[q].push(f)),
	this.plotLinesAndBands.push(d));return d},removePlotBandOrLine:function(a){for(var k=this.plotLinesAndBands,d=this.options,c=this.userOptions,n=k.length;n--;)k[n].id===a&&k[n].destroy();r([d.plotLines||[],c.plotLines||[],d.plotBands||[],c.plotBands||[]],function(c){for(n=c.length;n--;)c[n].id===a&&f(c,c[n])})}}})(L);(function(a){var B=a.correctFloat,A=a.defined,H=a.destroyObjectProperties,G=a.isNumber,r=a.merge,f=a.pick,l=a.deg2rad;a.Tick=function(a,f,l,d){this.axis=a;this.pos=f;this.type=l||"";this.isNew=
	!0;l||d||this.addLabel()};a.Tick.prototype={addLabel:function(){var a=this.axis,k=a.options,l=a.chart,d=a.categories,c=a.names,n=this.pos,z=k.labels,b=a.tickPositions,p=n===b[0],h=n===b[b.length-1],c=d?f(d[n],c[n],n):n,d=this.label,b=b.info,t;a.isDatetimeAxis&&b&&(t=k.dateTimeLabelFormats[b.higherRanks[n]||b.unitName]);this.isFirst=p;this.isLast=h;k=a.labelFormatter.call({axis:a,chart:l,isFirst:p,isLast:h,dateTimeLabelFormat:t,value:a.isLog?B(a.lin2log(c)):c});A(d)?d&&d.attr({text:k}):(this.labelLength=
	(this.label=d=A(k)&&z.enabled?l.renderer.text(k,0,0,z.useHTML).css(r(z.style)).add(a.labelGroup):null)&&d.getBBox().width,this.rotation=0)},getLabelSize:function(){return this.label?this.label.getBBox()[this.axis.horiz?"height":"width"]:0},handleOverflow:function(a){var k=this.axis,q=a.x,d=k.chart.chartWidth,c=k.chart.spacing,n=f(k.labelLeft,Math.min(k.pos,c[3])),c=f(k.labelRight,Math.max(k.pos+k.len,d-c[1])),z=this.label,b=this.rotation,p={left:0,center:.5,right:1}[k.labelAlign],h=z.getBBox().width,
	t=k.getSlotWidth(),D=t,w=1,e,x={};if(b)0>b&&q-p*h<n?e=Math.round(q/Math.cos(b*l)-n):0<b&&q+p*h>c&&(e=Math.round((d-q)/Math.cos(b*l)));else if(d=q+(1-p)*h,q-p*h<n?D=a.x+D*(1-p)-n:d>c&&(D=c-a.x+D*p,w=-1),D=Math.min(t,D),D<t&&"center"===k.labelAlign&&(a.x+=w*(t-D-p*(t-Math.min(h,D)))),h>D||k.autoRotation&&(z.styles||{}).width)e=D;e&&(x.width=e,(k.options.labels.style||{}).textOverflow||(x.textOverflow="ellipsis"),z.css(x))},getPosition:function(a,f,l,d){var c=this.axis,n=c.chart,k=d&&n.oldChartHeight||
	n.chartHeight;return{x:a?c.translate(f+l,null,null,d)+c.transB:c.left+c.offset+(c.opposite?(d&&n.oldChartWidth||n.chartWidth)-c.right-c.left:0),y:a?k-c.bottom+c.offset-(c.opposite?c.height:0):k-c.translate(f+l,null,null,d)-c.transB}},getLabelPosition:function(a,f,u,d,c,n,z,b){var p=this.axis,h=p.transA,t=p.reversed,k=p.staggerLines,w=p.tickRotCorr||{x:0,y:0},e=c.y;A(e)||(e=0===p.side?u.rotation?-8:-u.getBBox().height:2===p.side?w.y+8:Math.cos(u.rotation*l)*(w.y-u.getBBox(!1,0).height/2));a=a+c.x+
	w.x-(n&&d?n*h*(t?-1:1):0);f=f+e-(n&&!d?n*h*(t?1:-1):0);k&&(u=z/(b||1)%k,p.opposite&&(u=k-u-1),f+=p.labelOffset/k*u);return{x:a,y:Math.round(f)}},getMarkPath:function(a,f,l,d,c,n){return n.crispLine(["M",a,f,"L",a+(c?0:-l),f+(c?l:0)],d)},renderGridLine:function(a,f,l){var d=this.axis,c=d.options,n=this.gridLine,k={},b=this.pos,p=this.type,h=d.tickmarkOffset,t=d.chart.renderer,D=p?p+"Grid":"grid",w=c[D+"LineWidth"],e=c[D+"LineColor"],c=c[D+"LineDashStyle"];n||(k.stroke=e,k["stroke-width"]=w,c&&(k.dashstyle=
	c),p||(k.zIndex=1),a&&(k.opacity=0),this.gridLine=n=t.path().attr(k).addClass("highcharts-"+(p?p+"-":"")+"grid-line").add(d.gridGroup));if(!a&&n&&(a=d.getPlotLinePath(b+h,n.strokeWidth()*l,a,!0)))n[this.isNew?"attr":"animate"]({d:a,opacity:f})},renderMark:function(a,k,l){var d=this.axis,c=d.options,n=d.chart.renderer,q=this.type,b=q?q+"Tick":"tick",p=d.tickSize(b),h=this.mark,t=!h,D=a.x;a=a.y;var w=f(c[b+"Width"],!q&&d.isXAxis?1:0),c=c[b+"Color"];p&&(d.opposite&&(p[0]=-p[0]),t&&(this.mark=h=n.path().addClass("highcharts-"+
	(q?q+"-":"")+"tick").add(d.axisGroup),h.attr({stroke:c,"stroke-width":w})),h[t?"attr":"animate"]({d:this.getMarkPath(D,a,p[0],h.strokeWidth()*l,d.horiz,n),opacity:k}))},renderLabel:function(a,k,l,d){var c=this.axis,n=c.horiz,q=c.options,b=this.label,p=q.labels,h=p.step,t=c.tickmarkOffset,D=!0,w=a.x;a=a.y;b&&G(w)&&(b.xy=a=this.getLabelPosition(w,a,b,n,p,t,d,h),this.isFirst&&!this.isLast&&!f(q.showFirstLabel,1)||this.isLast&&!this.isFirst&&!f(q.showLastLabel,1)?D=!1:!n||c.isRadial||p.step||p.rotation||
	k||0===l||this.handleOverflow(a),h&&d%h&&(D=!1),D&&G(a.y)?(a.opacity=l,b[this.isNew?"attr":"animate"](a)):b.attr("y",-9999),this.isNew=!1)},render:function(a,k,l){var d=this.axis,c=d.horiz,n=this.getPosition(c,this.pos,d.tickmarkOffset,k),q=n.x,b=n.y,d=c&&q===d.pos+d.len||!c&&b===d.pos?-1:1;l=f(l,1);this.isActive=!0;this.renderGridLine(k,l,d);this.renderMark(n,l,d);this.renderLabel(n,k,l,a)},destroy:function(){H(this,this.axis)}}})(L);(function(a){var B=a.addEvent,A=a.animObject,H=a.arrayMax,G=a.arrayMin,
	r=a.AxisPlotLineOrBandExtension,f=a.color,l=a.correctFloat,q=a.defaultOptions,k=a.defined,u=a.deg2rad,d=a.destroyObjectProperties,c=a.each,n=a.extend,z=a.fireEvent,b=a.format,p=a.getMagnitude,h=a.grep,t=a.inArray,D=a.isArray,w=a.isNumber,e=a.isString,x=a.merge,C=a.normalizeTickInterval,E=a.pick,m=a.PlotLineOrBand,y=a.removeEvent,I=a.splat,K=a.syncTimeout,J=a.Tick;a.Axis=function(){this.init.apply(this,arguments)};a.Axis.prototype={defaultOptions:{dateTimeLabelFormats:{millisecond:"%H:%M:%S.%L",second:"%H:%M:%S",
	minute:"%H:%M",hour:"%H:%M",day:"%e. %b",week:"%e. %b",month:"%b '%y",year:"%Y"},endOnTick:!1,labels:{enabled:!0,style:{color:"#666666",cursor:"default",fontSize:"11px"},x:0},minPadding:.01,maxPadding:.01,minorTickLength:2,minorTickPosition:"outside",startOfWeek:1,startOnTick:!1,tickLength:10,tickmarkPlacement:"between",tickPixelInterval:100,tickPosition:"outside",title:{align:"middle",style:{color:"#666666"}},type:"linear",minorGridLineColor:"#f2f2f2",minorGridLineWidth:1,minorTickColor:"#999999",
	lineColor:"#ccd6eb",lineWidth:1,gridLineColor:"#e6e6e6",tickColor:"#ccd6eb"},defaultYAxisOptions:{endOnTick:!0,tickPixelInterval:72,showLastLabel:!0,labels:{x:-8},maxPadding:.05,minPadding:.05,startOnTick:!0,title:{rotation:270,text:"Values"},stackLabels:{enabled:!1,formatter:function(){return a.numberFormat(this.total,-1)},style:{fontSize:"11px",fontWeight:"bold",color:"#000000",textOutline:"1px contrast"}},gridLineWidth:1,lineWidth:0},defaultLeftAxisOptions:{labels:{x:-15},title:{rotation:270}},
	defaultRightAxisOptions:{labels:{x:15},title:{rotation:90}},defaultBottomAxisOptions:{labels:{autoRotation:[-45],x:0},title:{rotation:0}},defaultTopAxisOptions:{labels:{autoRotation:[-45],x:0},title:{rotation:0}},init:function(a,b){var g=b.isX;this.chart=a;this.horiz=a.inverted?!g:g;this.isXAxis=g;this.coll=this.coll||(g?"xAxis":"yAxis");this.opposite=b.opposite;this.side=b.side||(this.horiz?this.opposite?0:2:this.opposite?1:3);this.setOptions(b);var c=this.options,e=c.type;this.labelFormatter=c.labels.formatter||
	this.defaultLabelFormatter;this.userOptions=b;this.minPixelPadding=0;this.reversed=c.reversed;this.visible=!1!==c.visible;this.zoomEnabled=!1!==c.zoomEnabled;this.hasNames="category"===e||!0===c.categories;this.categories=c.categories||this.hasNames;this.names=this.names||[];this.plotLinesAndBandsGroups={};this.isLog="logarithmic"===e;this.isDatetimeAxis="datetime"===e;this.positiveValuesOnly=this.isLog&&!this.allowNegativeLog;this.isLinked=k(c.linkedTo);this.ticks={};this.labelEdge=[];this.minorTicks=
	{};this.plotLinesAndBands=[];this.alternateBands={};this.len=0;this.minRange=this.userMinRange=c.minRange||c.maxZoom;this.range=c.range;this.offset=c.offset||0;this.stacks={};this.oldStacks={};this.stacksTouched=0;this.min=this.max=null;this.crosshair=E(c.crosshair,I(a.options.tooltip.crosshairs)[g?0:1],!1);var h;b=this.options.events;-1===t(this,a.axes)&&(g?a.axes.splice(a.xAxis.length,0,this):a.axes.push(this),a[this.coll].push(this));this.series=this.series||[];a.inverted&&g&&void 0===this.reversed&&
	(this.reversed=!0);this.removePlotLine=this.removePlotBand=this.removePlotBandOrLine;for(h in b)B(this,h,b[h]);this.lin2log=c.linearToLogConverter||this.lin2log;this.isLog&&(this.val2lin=this.log2lin,this.lin2val=this.lin2log)},setOptions:function(a){this.options=x(this.defaultOptions,"yAxis"===this.coll&&this.defaultYAxisOptions,[this.defaultTopAxisOptions,this.defaultRightAxisOptions,this.defaultBottomAxisOptions,this.defaultLeftAxisOptions][this.side],x(q[this.coll],a))},defaultLabelFormatter:function(){var g=
	this.axis,c=this.value,e=g.categories,h=this.dateTimeLabelFormat,m=q.lang,d=m.numericSymbols,m=m.numericSymbolMagnitude||1E3,v=d&&d.length,p,t=g.options.labels.format,g=g.isLog?Math.abs(c):g.tickInterval;if(t)p=b(t,this);else if(e)p=c;else if(h)p=a.dateFormat(h,c);else if(v&&1E3<=g)for(;v--&&void 0===p;)e=Math.pow(m,v+1),g>=e&&0===10*c%e&&null!==d[v]&&0!==c&&(p=a.numberFormat(c/e,-1)+d[v]);void 0===p&&(p=1E4<=Math.abs(c)?a.numberFormat(c,-1):a.numberFormat(c,-1,void 0,""));return p},getSeriesExtremes:function(){var a=
	this,b=a.chart;a.hasVisibleSeries=!1;a.dataMin=a.dataMax=a.threshold=null;a.softThreshold=!a.isXAxis;a.buildStacks&&a.buildStacks();c(a.series,function(g){if(g.visible||!b.options.chart.ignoreHiddenSeries){var c=g.options,e=c.threshold,m;a.hasVisibleSeries=!0;a.positiveValuesOnly&&0>=e&&(e=null);if(a.isXAxis)c=g.xData,c.length&&(g=G(c),w(g)||g instanceof Date||(c=h(c,function(a){return w(a)}),g=G(c)),a.dataMin=Math.min(E(a.dataMin,c[0]),g),a.dataMax=Math.max(E(a.dataMax,c[0]),H(c)));else if(g.getExtremes(),
	m=g.dataMax,g=g.dataMin,k(g)&&k(m)&&(a.dataMin=Math.min(E(a.dataMin,g),g),a.dataMax=Math.max(E(a.dataMax,m),m)),k(e)&&(a.threshold=e),!c.softThreshold||a.positiveValuesOnly)a.softThreshold=!1}})},translate:function(a,b,c,e,h,m){var g=this.linkedParent||this,F=1,p=0,d=e?g.oldTransA:g.transA;e=e?g.oldMin:g.min;var t=g.minPixelPadding;h=(g.isOrdinal||g.isBroken||g.isLog&&h)&&g.lin2val;d||(d=g.transA);c&&(F*=-1,p=g.len);g.reversed&&(F*=-1,p-=F*(g.sector||g.len));b?(a=(a*F+p-t)/d+e,h&&(a=g.lin2val(a))):
	(h&&(a=g.val2lin(a)),a=F*(a-e)*d+p+F*t+(w(m)?d*m:0));return a},toPixels:function(a,b){return this.translate(a,!1,!this.horiz,null,!0)+(b?0:this.pos)},toValue:function(a,b){return this.translate(a-(b?0:this.pos),!0,!this.horiz,null,!0)},getPlotLinePath:function(a,b,c,e,h){var g=this.chart,m=this.left,F=this.top,p,d,t=c&&g.oldChartHeight||g.chartHeight,f=c&&g.oldChartWidth||g.chartWidth,n;p=this.transB;var y=function(a,b,g){if(a<b||a>g)e?a=Math.min(Math.max(b,a),g):n=!0;return a};h=E(h,this.translate(a,
	null,null,c));a=c=Math.round(h+p);p=d=Math.round(t-h-p);w(h)?this.horiz?(p=F,d=t-this.bottom,a=c=y(a,m,m+this.width)):(a=m,c=f-this.right,p=d=y(p,F,F+this.height)):n=!0;return n&&!e?null:g.renderer.crispLine(["M",a,p,"L",c,d],b||1)},getLinearTickPositions:function(a,b,c){var g,e=l(Math.floor(b/a)*a);c=l(Math.ceil(c/a)*a);var h=[];if(this.single)return[b];for(b=e;b<=c;){h.push(b);b=l(b+a);if(b===g)break;g=b}return h},getMinorTickPositions:function(){var a=this,b=a.options,e=a.tickPositions,h=a.minorTickInterval,
	m=[],p=a.pointRangePadding||0,v=a.min-p,p=a.max+p,d=p-v;if(d&&d/h<a.len/3)if(a.isLog)c(this.paddedTicks,function(b,g,c){g&&m.push.apply(m,a.getLogTickPositions(h,c[g-1],c[g],!0))});else if(a.isDatetimeAxis&&"auto"===b.minorTickInterval)m=m.concat(a.getTimeTicks(a.normalizeTimeTickInterval(h),v,p,b.startOfWeek));else for(b=v+(e[0]-v)%h;b<=p&&b!==m[0];b+=h)m.push(b);0!==m.length&&a.trimTicks(m);return m},adjustForMinRange:function(){var a=this.options,b=this.min,e=this.max,h,m=this.dataMax-this.dataMin>=
	this.minRange,p,v,d,t,f,n;this.isXAxis&&void 0===this.minRange&&!this.isLog&&(k(a.min)||k(a.max)?this.minRange=null:(c(this.series,function(a){t=a.xData;for(v=f=a.xIncrement?1:t.length-1;0<v;v--)if(d=t[v]-t[v-1],void 0===p||d<p)p=d}),this.minRange=Math.min(5*p,this.dataMax-this.dataMin)));e-b<this.minRange&&(n=this.minRange,h=(n-e+b)/2,h=[b-h,E(a.min,b-h)],m&&(h[2]=this.isLog?this.log2lin(this.dataMin):this.dataMin),b=H(h),e=[b+n,E(a.max,b+n)],m&&(e[2]=this.isLog?this.log2lin(this.dataMax):this.dataMax),
	e=G(e),e-b<n&&(h[0]=e-n,h[1]=E(a.min,e-n),b=H(h)));this.min=b;this.max=e},getClosest:function(){var a;this.categories?a=1:c(this.series,function(b){var g=b.closestPointRange,c=b.visible||!b.chart.options.chart.ignoreHiddenSeries;!b.noSharedTooltip&&k(g)&&c&&(a=k(a)?Math.min(a,g):g)});return a},nameToX:function(a){var b=D(this.categories),g=b?this.categories:this.names,c=a.options.x,e;a.series.requireSorting=!1;k(c)||(c=!1===this.options.uniqueNames?a.series.autoIncrement():t(a.name,g));-1===c?b||
	(e=g.length):e=c;void 0!==e&&(this.names[e]=a.name);return e},updateNames:function(){var a=this;0<this.names.length&&(this.names.length=0,this.minRange=void 0,c(this.series||[],function(b){b.xIncrement=null;if(!b.points||b.isDirtyData)b.processData(),b.generatePoints();c(b.points,function(g,c){var e;g.options&&(e=a.nameToX(g),void 0!==e&&e!==g.x&&(g.x=e,b.xData[c]=e))})}))},setAxisTranslation:function(a){var b=this,g=b.max-b.min,h=b.axisPointRange||0,m,p=0,v=0,d=b.linkedParent,t=!!b.categories,f=
	b.transA,n=b.isXAxis;if(n||t||h)m=b.getClosest(),d?(p=d.minPointOffset,v=d.pointRangePadding):c(b.series,function(a){var g=t?1:n?E(a.options.pointRange,m,0):b.axisPointRange||0;a=a.options.pointPlacement;h=Math.max(h,g);b.single||(p=Math.max(p,e(a)?0:g/2),v=Math.max(v,"on"===a?0:g))}),d=b.ordinalSlope&&m?b.ordinalSlope/m:1,b.minPointOffset=p*=d,b.pointRangePadding=v*=d,b.pointRange=Math.min(h,g),n&&(b.closestPointRange=m);a&&(b.oldTransA=f);b.translationSlope=b.transA=f=b.options.staticScale||b.len/
	(g+v||1);b.transB=b.horiz?b.left:b.bottom;b.minPixelPadding=f*p},minFromRange:function(){return this.max-this.range},setTickInterval:function(b){var g=this,e=g.chart,h=g.options,m=g.isLog,d=g.log2lin,v=g.isDatetimeAxis,t=g.isXAxis,f=g.isLinked,n=h.maxPadding,y=h.minPadding,x=h.tickInterval,D=h.tickPixelInterval,I=g.categories,q=g.threshold,u=g.softThreshold,r,K,J,A;v||I||f||this.getTickAmount();J=E(g.userMin,h.min);A=E(g.userMax,h.max);f?(g.linkedParent=e[g.coll][h.linkedTo],e=g.linkedParent.getExtremes(),
	g.min=E(e.min,e.dataMin),g.max=E(e.max,e.dataMax),h.type!==g.linkedParent.options.type&&a.error(11,1)):(!u&&k(q)&&(g.dataMin>=q?(r=q,y=0):g.dataMax<=q&&(K=q,n=0)),g.min=E(J,r,g.dataMin),g.max=E(A,K,g.dataMax));m&&(g.positiveValuesOnly&&!b&&0>=Math.min(g.min,E(g.dataMin,g.min))&&a.error(10,1),g.min=l(d(g.min),15),g.max=l(d(g.max),15));g.range&&k(g.max)&&(g.userMin=g.min=J=Math.max(g.min,g.minFromRange()),g.userMax=A=g.max,g.range=null);z(g,"foundExtremes");g.beforePadding&&g.beforePadding();g.adjustForMinRange();
	!(I||g.axisPointRange||g.usePercentage||f)&&k(g.min)&&k(g.max)&&(d=g.max-g.min)&&(!k(J)&&y&&(g.min-=d*y),!k(A)&&n&&(g.max+=d*n));w(h.softMin)&&(g.min=Math.min(g.min,h.softMin));w(h.softMax)&&(g.max=Math.max(g.max,h.softMax));w(h.floor)&&(g.min=Math.max(g.min,h.floor));w(h.ceiling)&&(g.max=Math.min(g.max,h.ceiling));u&&k(g.dataMin)&&(q=q||0,!k(J)&&g.min<q&&g.dataMin>=q?g.min=q:!k(A)&&g.max>q&&g.dataMax<=q&&(g.max=q));g.tickInterval=g.min===g.max||void 0===g.min||void 0===g.max?1:f&&!x&&D===g.linkedParent.options.tickPixelInterval?
	x=g.linkedParent.tickInterval:E(x,this.tickAmount?(g.max-g.min)/Math.max(this.tickAmount-1,1):void 0,I?1:(g.max-g.min)*D/Math.max(g.len,D));t&&!b&&c(g.series,function(a){a.processData(g.min!==g.oldMin||g.max!==g.oldMax)});g.setAxisTranslation(!0);g.beforeSetTickPositions&&g.beforeSetTickPositions();g.postProcessTickInterval&&(g.tickInterval=g.postProcessTickInterval(g.tickInterval));g.pointRange&&!x&&(g.tickInterval=Math.max(g.pointRange,g.tickInterval));b=E(h.minTickInterval,g.isDatetimeAxis&&g.closestPointRange);
	!x&&g.tickInterval<b&&(g.tickInterval=b);v||m||x||(g.tickInterval=C(g.tickInterval,null,p(g.tickInterval),E(h.allowDecimals,!(.5<g.tickInterval&&5>g.tickInterval&&1E3<g.max&&9999>g.max)),!!this.tickAmount));this.tickAmount||(g.tickInterval=g.unsquish());this.setTickPositions()},setTickPositions:function(){var a=this.options,b,c=a.tickPositions,e=a.tickPositioner,h=a.startOnTick,m=a.endOnTick;this.tickmarkOffset=this.categories&&"between"===a.tickmarkPlacement&&1===this.tickInterval?.5:0;this.minorTickInterval=
	"auto"===a.minorTickInterval&&this.tickInterval?this.tickInterval/5:a.minorTickInterval;this.single=this.min===this.max&&k(this.min)&&!this.tickAmount&&!1!==a.allowDecimals;this.tickPositions=b=c&&c.slice();!b&&(b=this.isDatetimeAxis?this.getTimeTicks(this.normalizeTimeTickInterval(this.tickInterval,a.units),this.min,this.max,a.startOfWeek,this.ordinalPositions,this.closestPointRange,!0):this.isLog?this.getLogTickPositions(this.tickInterval,this.min,this.max):this.getLinearTickPositions(this.tickInterval,
	this.min,this.max),b.length>this.len&&(b=[b[0],b.pop()]),this.tickPositions=b,e&&(e=e.apply(this,[this.min,this.max])))&&(this.tickPositions=b=e);this.paddedTicks=b.slice(0);this.trimTicks(b,h,m);this.isLinked||(this.single&&(this.min-=.5,this.max+=.5),c||e||this.adjustTickAmount())},trimTicks:function(a,b,c){var g=a[0],e=a[a.length-1],h=this.minPointOffset||0;if(!this.isLinked){if(b&&-Infinity!==g)this.min=g;else for(;this.min-h>a[0];)a.shift();if(c)this.max=e;else for(;this.max+h<a[a.length-1];)a.pop();
	0===a.length&&k(g)&&a.push((e+g)/2)}},alignToOthers:function(){var a={},b,e=this.options;!1===this.chart.options.chart.alignTicks||!1===e.alignTicks||this.isLog||c(this.chart[this.coll],function(g){var c=g.options,c=[g.horiz?c.left:c.top,c.width,c.height,c.pane].join();g.series.length&&(a[c]?b=!0:a[c]=1)});return b},getTickAmount:function(){var a=this.options,b=a.tickAmount,c=a.tickPixelInterval;!k(a.tickInterval)&&this.len<c&&!this.isRadial&&!this.isLog&&a.startOnTick&&a.endOnTick&&(b=2);!b&&this.alignToOthers()&&
	(b=Math.ceil(this.len/c)+1);4>b&&(this.finalTickAmt=b,b=5);this.tickAmount=b},adjustTickAmount:function(){var a=this.tickInterval,b=this.tickPositions,c=this.tickAmount,e=this.finalTickAmt,h=b&&b.length;if(h<c){for(;b.length<c;)b.push(l(b[b.length-1]+a));this.transA*=(h-1)/(c-1);this.max=b[b.length-1]}else h>c&&(this.tickInterval*=2,this.setTickPositions());if(k(e)){for(a=c=b.length;a--;)(3===e&&1===a%2||2>=e&&0<a&&a<c-1)&&b.splice(a,1);this.finalTickAmt=void 0}},setScale:function(){var a,b;this.oldMin=
	this.min;this.oldMax=this.max;this.oldAxisLength=this.len;this.setAxisSize();b=this.len!==this.oldAxisLength;c(this.series,function(b){if(b.isDirtyData||b.isDirty||b.xAxis.isDirty)a=!0});b||a||this.isLinked||this.forceRedraw||this.userMin!==this.oldUserMin||this.userMax!==this.oldUserMax||this.alignToOthers()?(this.resetStacks&&this.resetStacks(),this.forceRedraw=!1,this.getSeriesExtremes(),this.setTickInterval(),this.oldUserMin=this.userMin,this.oldUserMax=this.userMax,this.isDirty||(this.isDirty=
	b||this.min!==this.oldMin||this.max!==this.oldMax)):this.cleanStacks&&this.cleanStacks()},setExtremes:function(a,b,e,h,m){var g=this,p=g.chart;e=E(e,!0);c(g.series,function(a){delete a.kdTree});m=n(m,{min:a,max:b});z(g,"setExtremes",m,function(){g.userMin=a;g.userMax=b;g.eventArgs=m;e&&p.redraw(h)})},zoom:function(a,b){var g=this.dataMin,c=this.dataMax,e=this.options,h=Math.min(g,E(e.min,g)),e=Math.max(c,E(e.max,c));if(a!==this.min||b!==this.max)this.allowZoomOutside||(k(g)&&(a<h&&(a=h),a>e&&(a=e)),
	k(c)&&(b<h&&(b=h),b>e&&(b=e))),this.displayBtn=void 0!==a||void 0!==b,this.setExtremes(a,b,!1,void 0,{trigger:"zoom"});return!0},setAxisSize:function(){var a=this.chart,b=this.options,c=b.offsets||[0,0,0,0],e=this.horiz,h=E(b.width,a.plotWidth-c[3]+c[1]),m=E(b.height,a.plotHeight-c[0]+c[2]),p=E(b.top,a.plotTop+c[0]),b=E(b.left,a.plotLeft+c[3]),c=/%$/;c.test(m)&&(m=Math.round(parseFloat(m)/100*a.plotHeight));c.test(p)&&(p=Math.round(parseFloat(p)/100*a.plotHeight+a.plotTop));this.left=b;this.top=p;
	this.width=h;this.height=m;this.bottom=a.chartHeight-m-p;this.right=a.chartWidth-h-b;this.len=Math.max(e?h:m,0);this.pos=e?b:p},getExtremes:function(){var a=this.isLog,b=this.lin2log;return{min:a?l(b(this.min)):this.min,max:a?l(b(this.max)):this.max,dataMin:this.dataMin,dataMax:this.dataMax,userMin:this.userMin,userMax:this.userMax}},getThreshold:function(a){var b=this.isLog,g=this.lin2log,c=b?g(this.min):this.min,b=b?g(this.max):this.max;null===a?a=c:c>a?a=c:b<a&&(a=b);return this.translate(a,0,
	1,0,1)},autoLabelAlign:function(a){a=(E(a,0)-90*this.side+720)%360;return 15<a&&165>a?"right":195<a&&345>a?"left":"center"},tickSize:function(a){var b=this.options,g=b[a+"Length"],c=E(b[a+"Width"],"tick"===a&&this.isXAxis?1:0);if(c&&g)return"inside"===b[a+"Position"]&&(g=-g),[g,c]},labelMetrics:function(){return this.chart.renderer.fontMetrics(this.options.labels.style&&this.options.labels.style.fontSize,this.ticks[0]&&this.ticks[0].label)},unsquish:function(){var a=this.options.labels,b=this.horiz,
	e=this.tickInterval,h=e,m=this.len/(((this.categories?1:0)+this.max-this.min)/e),p,d=a.rotation,t=this.labelMetrics(),f,n=Number.MAX_VALUE,y,w=function(a){a/=m||1;a=1<a?Math.ceil(a):1;return a*e};b?(y=!a.staggerLines&&!a.step&&(k(d)?[d]:m<E(a.autoRotationLimit,80)&&a.autoRotation))&&c(y,function(a){var b;if(a===d||a&&-90<=a&&90>=a)f=w(Math.abs(t.h/Math.sin(u*a))),b=f+Math.abs(a/360),b<n&&(n=b,p=a,h=f)}):a.step||(h=w(t.h));this.autoRotation=y;this.labelRotation=E(p,d);return h},getSlotWidth:function(){var a=
	this.chart,b=this.horiz,c=this.options.labels,e=Math.max(this.tickPositions.length-(this.categories?0:1),1),h=a.margin[3];return b&&2>(c.step||0)&&!c.rotation&&(this.staggerLines||1)*this.len/e||!b&&(h&&h-a.spacing[3]||.33*a.chartWidth)},renderUnsquish:function(){var a=this.chart,b=a.renderer,h=this.tickPositions,m=this.ticks,p=this.options.labels,d=this.horiz,v=this.getSlotWidth(),t=Math.max(1,Math.round(v-2*(p.padding||5))),f={},n=this.labelMetrics(),y=p.style&&p.style.textOverflow,k,w=0,D,l;e(p.rotation)||
	(f.rotation=p.rotation||0);c(h,function(a){(a=m[a])&&a.labelLength>w&&(w=a.labelLength)});this.maxLabelLength=w;if(this.autoRotation)w>t&&w>n.h?f.rotation=this.labelRotation:this.labelRotation=0;else if(v&&(k={width:t+"px"},!y))for(k.textOverflow="clip",D=h.length;!d&&D--;)if(l=h[D],t=m[l].label)t.styles&&"ellipsis"===t.styles.textOverflow?t.css({textOverflow:"clip"}):m[l].labelLength>v&&t.css({width:v+"px"}),t.getBBox().height>this.len/h.length-(n.h-n.f)&&(t.specCss={textOverflow:"ellipsis"});f.rotation&&
	(k={width:(w>.5*a.chartHeight?.33*a.chartHeight:a.chartHeight)+"px"},y||(k.textOverflow="ellipsis"));if(this.labelAlign=p.align||this.autoLabelAlign(this.labelRotation))f.align=this.labelAlign;c(h,function(a){var b=(a=m[a])&&a.label;b&&(b.attr(f),k&&b.css(x(k,b.specCss)),delete b.specCss,a.rotation=f.rotation)});this.tickRotCorr=b.rotCorr(n.b,this.labelRotation||0,0!==this.side)},hasData:function(){return this.hasVisibleSeries||k(this.min)&&k(this.max)&&!!this.tickPositions},addTitle:function(a){var b=
	this.chart.renderer,c=this.horiz,g=this.opposite,e=this.options.title,h;this.axisTitle||((h=e.textAlign)||(h=(c?{low:"left",middle:"center",high:"right"}:{low:g?"right":"left",middle:"center",high:g?"left":"right"})[e.align]),this.axisTitle=b.text(e.text,0,0,e.useHTML).attr({zIndex:7,rotation:e.rotation||0,align:h}).addClass("highcharts-axis-title").css(e.style).add(this.axisGroup),this.axisTitle.isNew=!0);this.axisTitle[a?"show":"hide"](!0)},generateTick:function(a){var b=this.ticks;b[a]?b[a].addLabel():
	b[a]=new J(this,a)},getOffset:function(){var a=this,b=a.chart,e=b.renderer,h=a.options,m=a.tickPositions,p=a.ticks,d=a.horiz,t=a.side,f=b.inverted?[1,0,3,2][t]:t,n,y,w=0,x,D=0,l=h.title,C=h.labels,q=0,I=b.axisOffset,b=b.clipOffset,u=[-1,1,1,-1][t],z,r=h.className,K=a.axisParent,J=this.tickSize("tick");n=a.hasData();a.showAxis=y=n||E(h.showEmpty,!0);a.staggerLines=a.horiz&&C.staggerLines;a.axisGroup||(a.gridGroup=e.g("grid").attr({zIndex:h.gridZIndex||1}).addClass("highcharts-"+this.coll.toLowerCase()+
	"-grid "+(r||"")).add(K),a.axisGroup=e.g("axis").attr({zIndex:h.zIndex||2}).addClass("highcharts-"+this.coll.toLowerCase()+" "+(r||"")).add(K),a.labelGroup=e.g("axis-labels").attr({zIndex:C.zIndex||7}).addClass("highcharts-"+a.coll.toLowerCase()+"-labels "+(r||"")).add(K));if(n||a.isLinked)c(m,function(b,c){a.generateTick(b,c)}),a.renderUnsquish(),!1===C.reserveSpace||0!==t&&2!==t&&{1:"left",3:"right"}[t]!==a.labelAlign&&"center"!==a.labelAlign||c(m,function(a){q=Math.max(p[a].getLabelSize(),q)}),
	a.staggerLines&&(q*=a.staggerLines,a.labelOffset=q*(a.opposite?-1:1));else for(z in p)p[z].destroy(),delete p[z];l&&l.text&&!1!==l.enabled&&(a.addTitle(y),y&&(w=a.axisTitle.getBBox()[d?"height":"width"],x=l.offset,D=k(x)?0:E(l.margin,d?5:10)));a.renderLine();a.offset=u*E(h.offset,I[t]);a.tickRotCorr=a.tickRotCorr||{x:0,y:0};e=0===t?-a.labelMetrics().h:2===t?a.tickRotCorr.y:0;D=Math.abs(q)+D;q&&(D=D-e+u*(d?E(C.y,a.tickRotCorr.y+8*u):C.x));a.axisTitleMargin=E(x,D);I[t]=Math.max(I[t],a.axisTitleMargin+
	w+u*a.offset,D,n&&m.length&&J?J[0]+u*a.offset:0);h=h.offset?0:2*Math.floor(a.axisLine.strokeWidth()/2);b[f]=Math.max(b[f],h)},getLinePath:function(a){var b=this.chart,c=this.opposite,g=this.offset,e=this.horiz,h=this.left+(c?this.width:0)+g,g=b.chartHeight-this.bottom-(c?this.height:0)+g;c&&(a*=-1);return b.renderer.crispLine(["M",e?this.left:h,e?g:this.top,"L",e?b.chartWidth-this.right:h,e?g:b.chartHeight-this.bottom],a)},renderLine:function(){this.axisLine||(this.axisLine=this.chart.renderer.path().addClass("highcharts-axis-line").add(this.axisGroup),
	this.axisLine.attr({stroke:this.options.lineColor,"stroke-width":this.options.lineWidth,zIndex:7}))},getTitlePosition:function(){var a=this.horiz,b=this.left,c=this.top,e=this.len,h=this.options.title,m=a?b:c,p=this.opposite,d=this.offset,t=h.x||0,f=h.y||0,n=this.chart.renderer.fontMetrics(h.style&&h.style.fontSize,this.axisTitle).f,e={low:m+(a?0:e),middle:m+e/2,high:m+(a?e:0)}[h.align],b=(a?c+this.height:b)+(a?1:-1)*(p?-1:1)*this.axisTitleMargin+(2===this.side?n:0);return{x:a?e+t:b+(p?this.width:
	0)+d+t,y:a?b+f-(p?this.height:0)+d:e+f}},renderMinorTick:function(a){var b=this.chart.hasRendered&&w(this.oldMin),c=this.minorTicks;c[a]||(c[a]=new J(this,a,"minor"));b&&c[a].isNew&&c[a].render(null,!0);c[a].render(null,!1,1)},renderTick:function(a,b){var c=this.isLinked,g=this.ticks,e=this.chart.hasRendered&&w(this.oldMin);if(!c||a>=this.min&&a<=this.max)g[a]||(g[a]=new J(this,a)),e&&g[a].isNew&&g[a].render(b,!0,.1),g[a].render(b)},render:function(){var a=this,b=a.chart,e=a.options,h=a.isLog,p=a.lin2log,
	d=a.isLinked,t=a.tickPositions,f=a.axisTitle,n=a.ticks,y=a.minorTicks,k=a.alternateBands,w=e.stackLabels,x=e.alternateGridColor,D=a.tickmarkOffset,l=a.axisLine,C=a.showAxis,q=A(b.renderer.globalAnimation),I,E;a.labelEdge.length=0;a.overlap=!1;c([n,y,k],function(a){for(var b in a)a[b].isActive=!1});if(a.hasData()||d)a.minorTickInterval&&!a.categories&&c(a.getMinorTickPositions(),function(b){a.renderMinorTick(b)}),t.length&&(c(t,function(b,c){a.renderTick(b,c)}),D&&(0===a.min||a.single)&&(n[-1]||(n[-1]=
	new J(a,-1,null,!0)),n[-1].render(-1))),x&&c(t,function(c,e){E=void 0!==t[e+1]?t[e+1]+D:a.max-D;0===e%2&&c<a.max&&E<=a.max+(b.polar?-D:D)&&(k[c]||(k[c]=new m(a)),I=c+D,k[c].options={from:h?p(I):I,to:h?p(E):E,color:x},k[c].render(),k[c].isActive=!0)}),a._addedPlotLB||(c((e.plotLines||[]).concat(e.plotBands||[]),function(b){a.addPlotBandOrLine(b)}),a._addedPlotLB=!0);c([n,y,k],function(a){var c,e,g=[],h=q.duration;for(c in a)a[c].isActive||(a[c].render(c,!1,0),a[c].isActive=!1,g.push(c));K(function(){for(e=
	g.length;e--;)a[g[e]]&&!a[g[e]].isActive&&(a[g[e]].destroy(),delete a[g[e]])},a!==k&&b.hasRendered&&h?h:0)});l&&(l[l.isPlaced?"animate":"attr"]({d:this.getLinePath(l.strokeWidth())}),l.isPlaced=!0,l[C?"show":"hide"](!0));f&&C&&(f[f.isNew?"attr":"animate"](a.getTitlePosition()),f.isNew=!1);w&&w.enabled&&a.renderStackTotals();a.isDirty=!1},redraw:function(){this.visible&&(this.render(),c(this.plotLinesAndBands,function(a){a.render()}));c(this.series,function(a){a.isDirty=!0})},keepProps:"extKey hcEvents names series userMax userMin".split(" "),
	destroy:function(a){var b=this,e=b.stacks,g,h=b.plotLinesAndBands,m,p;a||y(b);for(g in e)d(e[g]),e[g]=null;c([b.ticks,b.minorTicks,b.alternateBands],function(a){d(a)});if(h)for(a=h.length;a--;)h[a].destroy();c("stackTotalGroup axisLine axisTitle axisGroup gridGroup labelGroup cross".split(" "),function(a){b[a]&&(b[a]=b[a].destroy())});for(m in b.plotLinesAndBandsGroups)b.plotLinesAndBandsGroups[m]=b.plotLinesAndBandsGroups[m].destroy();for(p in b)b.hasOwnProperty(p)&&-1===t(p,b.keepProps)&&delete b[p]},
	drawCrosshair:function(a,b){var c,e=this.crosshair,g=E(e.snap,!0),h,m=this.cross;a||(a=this.cross&&this.cross.e);this.crosshair&&!1!==(k(b)||!g)?(g?k(b)&&(h=this.isXAxis?b.plotX:this.len-b.plotY):h=a&&(this.horiz?a.chartX-this.pos:this.len-a.chartY+this.pos),k(h)&&(c=this.getPlotLinePath(b&&(this.isXAxis?b.x:E(b.stackY,b.y)),null,null,null,h)||null),k(c)?(b=this.categories&&!this.isRadial,m||(this.cross=m=this.chart.renderer.path().addClass("highcharts-crosshair highcharts-crosshair-"+(b?"category ":
	"thin ")+e.className).attr({zIndex:E(e.zIndex,2)}).add(),m.attr({stroke:e.color||(b?f("#ccd6eb").setOpacity(.25).get():"#cccccc"),"stroke-width":E(e.width,1)}),e.dashStyle&&m.attr({dashstyle:e.dashStyle})),m.show().attr({d:c}),b&&!e.width&&m.attr({"stroke-width":this.transA}),this.cross.e=a):this.hideCrosshair()):this.hideCrosshair()},hideCrosshair:function(){this.cross&&this.cross.hide()}};n(a.Axis.prototype,r)})(L);(function(a){var B=a.Axis,A=a.Date,H=a.dateFormat,G=a.defaultOptions,r=a.defined,
	f=a.each,l=a.extend,q=a.getMagnitude,k=a.getTZOffset,u=a.normalizeTickInterval,d=a.pick,c=a.timeUnits;B.prototype.getTimeTicks=function(a,q,b,p){var h=[],t={},n=G.global.useUTC,w,e=new A(q-Math.abs(k(q))),x=A.hcMakeTime,C=a.unitRange,E=a.count,m;if(r(q)){e[A.hcSetMilliseconds](C>=c.second?0:E*Math.floor(e.getMilliseconds()/E));if(C>=c.second)e[A.hcSetSeconds](C>=c.minute?0:E*Math.floor(e.getSeconds()/E));if(C>=c.minute)e[A.hcSetMinutes](C>=c.hour?0:E*Math.floor(e[A.hcGetMinutes]()/E));if(C>=c.hour)e[A.hcSetHours](C>=
	c.day?0:E*Math.floor(e[A.hcGetHours]()/E));if(C>=c.day)e[A.hcSetDate](C>=c.month?1:E*Math.floor(e[A.hcGetDate]()/E));C>=c.month&&(e[A.hcSetMonth](C>=c.year?0:E*Math.floor(e[A.hcGetMonth]()/E)),w=e[A.hcGetFullYear]());if(C>=c.year)e[A.hcSetFullYear](w-w%E);if(C===c.week)e[A.hcSetDate](e[A.hcGetDate]()-e[A.hcGetDay]()+d(p,1));w=e[A.hcGetFullYear]();p=e[A.hcGetMonth]();var y=e[A.hcGetDate](),I=e[A.hcGetHours]();if(A.hcTimezoneOffset||A.hcGetTimezoneOffset)m=(!n||!!A.hcGetTimezoneOffset)&&(b-q>4*c.month||
	k(q)!==k(b)),e=e.getTime(),e=new A(e+k(e));n=e.getTime();for(q=1;n<b;)h.push(n),n=C===c.year?x(w+q*E,0):C===c.month?x(w,p+q*E):!m||C!==c.day&&C!==c.week?m&&C===c.hour?x(w,p,y,I+q*E):n+C*E:x(w,p,y+q*E*(C===c.day?1:7)),q++;h.push(n);C<=c.hour&&1E4>h.length&&f(h,function(a){0===a%18E5&&"000000000"===H("%H%M%S%L",a)&&(t[a]="day")})}h.info=l(a,{higherRanks:t,totalRange:C*E});return h};B.prototype.normalizeTimeTickInterval=function(a,d){var b=d||[["millisecond",[1,2,5,10,20,25,50,100,200,500]],["second",
	[1,2,5,10,15,30]],["minute",[1,2,5,10,15,30]],["hour",[1,2,3,4,6,8,12]],["day",[1,2]],["week",[1,2]],["month",[1,2,3,4,6]],["year",null]];d=b[b.length-1];var p=c[d[0]],h=d[1],t;for(t=0;t<b.length&&!(d=b[t],p=c[d[0]],h=d[1],b[t+1]&&a<=(p*h[h.length-1]+c[b[t+1][0]])/2);t++);p===c.year&&a<5*p&&(h=[1,2,5]);a=u(a/p,h,"year"===d[0]?Math.max(q(a/p),1):1);return{unitRange:p,count:a,unitName:d[0]}}})(L);(function(a){var B=a.Axis,A=a.getMagnitude,H=a.map,G=a.normalizeTickInterval,r=a.pick;B.prototype.getLogTickPositions=
	function(a,l,q,k){var f=this.options,d=this.len,c=this.lin2log,n=this.log2lin,z=[];k||(this._minorAutoInterval=null);if(.5<=a)a=Math.round(a),z=this.getLinearTickPositions(a,l,q);else if(.08<=a)for(var d=Math.floor(l),b,p,h,t,D,f=.3<a?[1,2,4]:.15<a?[1,2,4,6,8]:[1,2,3,4,5,6,7,8,9];d<q+1&&!D;d++)for(p=f.length,b=0;b<p&&!D;b++)h=n(c(d)*f[b]),h>l&&(!k||t<=q)&&void 0!==t&&z.push(t),t>q&&(D=!0),t=h;else l=c(l),q=c(q),a=f[k?"minorTickInterval":"tickInterval"],a=r("auto"===a?null:a,this._minorAutoInterval,
	f.tickPixelInterval/(k?5:1)*(q-l)/((k?d/this.tickPositions.length:d)||1)),a=G(a,null,A(a)),z=H(this.getLinearTickPositions(a,l,q),n),k||(this._minorAutoInterval=a/5);k||(this.tickInterval=a);return z};B.prototype.log2lin=function(a){return Math.log(a)/Math.LN10};B.prototype.lin2log=function(a){return Math.pow(10,a)}})(L);(function(a){var B=a.dateFormat,A=a.each,H=a.extend,G=a.format,r=a.isNumber,f=a.map,l=a.merge,q=a.pick,k=a.splat,u=a.syncTimeout,d=a.timeUnits;a.Tooltip=function(){this.init.apply(this,
	arguments)};a.Tooltip.prototype={init:function(a,d){this.chart=a;this.options=d;this.crosshairs=[];this.now={x:0,y:0};this.isHidden=!0;this.split=d.split&&!a.inverted;this.shared=d.shared||this.split},cleanSplit:function(a){A(this.chart.series,function(c){var d=c&&c.tt;d&&(!d.isActive||a?c.tt=d.destroy():d.isActive=!1)})},getLabel:function(){var a=this.chart.renderer,d=this.options;this.label||(this.split?this.label=a.g("tooltip"):(this.label=a.label("",0,0,d.shape||"callout",null,null,d.useHTML,
	null,"tooltip").attr({padding:d.padding,r:d.borderRadius}),this.label.attr({fill:d.backgroundColor,"stroke-width":d.borderWidth}).css(d.style).shadow(d.shadow)),this.label.attr({zIndex:8}).add());return this.label},update:function(a){this.destroy();this.init(this.chart,l(!0,this.options,a))},destroy:function(){this.label&&(this.label=this.label.destroy());this.split&&this.tt&&(this.cleanSplit(this.chart,!0),this.tt=this.tt.destroy());clearTimeout(this.hideTimer);clearTimeout(this.tooltipTimeout)},
	move:function(a,d,f,b){var c=this,h=c.now,t=!1!==c.options.animation&&!c.isHidden&&(1<Math.abs(a-h.x)||1<Math.abs(d-h.y)),n=c.followPointer||1<c.len;H(h,{x:t?(2*h.x+a)/3:a,y:t?(h.y+d)/2:d,anchorX:n?void 0:t?(2*h.anchorX+f)/3:f,anchorY:n?void 0:t?(h.anchorY+b)/2:b});c.getLabel().attr(h);t&&(clearTimeout(this.tooltipTimeout),this.tooltipTimeout=setTimeout(function(){c&&c.move(a,d,f,b)},32))},hide:function(a){var c=this;clearTimeout(this.hideTimer);a=q(a,this.options.hideDelay,500);this.isHidden||(this.hideTimer=
	u(function(){c.getLabel()[a?"fadeOut":"hide"]();c.isHidden=!0},a))},getAnchor:function(a,d){var c,b=this.chart,p=b.inverted,h=b.plotTop,t=b.plotLeft,n=0,w=0,e,x;a=k(a);c=a[0].tooltipPos;this.followPointer&&d&&(void 0===d.chartX&&(d=b.pointer.normalize(d)),c=[d.chartX-b.plotLeft,d.chartY-h]);c||(A(a,function(a){e=a.series.yAxis;x=a.series.xAxis;n+=a.plotX+(!p&&x?x.left-t:0);w+=(a.plotLow?(a.plotLow+a.plotHigh)/2:a.plotY)+(!p&&e?e.top-h:0)}),n/=a.length,w/=a.length,c=[p?b.plotWidth-w:n,this.shared&&
	!p&&1<a.length&&d?d.chartY-h:p?b.plotHeight-n:w]);return f(c,Math.round)},getPosition:function(a,d,f){var b=this.chart,c=this.distance,h={},t=f.h||0,n,k=["y",b.chartHeight,d,f.plotY+b.plotTop,b.plotTop,b.plotTop+b.plotHeight],e=["x",b.chartWidth,a,f.plotX+b.plotLeft,b.plotLeft,b.plotLeft+b.plotWidth],x=!this.followPointer&&q(f.ttBelow,!b.inverted===!!f.negative),l=function(a,b,e,g,m,d){var p=e<g-c,f=g+c+e<b,n=g-c-e;g+=c;if(x&&f)h[a]=g;else if(!x&&p)h[a]=n;else if(p)h[a]=Math.min(d-e,0>n-t?n:n-t);
	else if(f)h[a]=Math.max(m,g+t+e>b?g:g+t);else return!1},E=function(a,b,e,g){var m;g<c||g>b-c?m=!1:h[a]=g<e/2?1:g>b-e/2?b-e-2:g-e/2;return m},m=function(a){var b=k;k=e;e=b;n=a},y=function(){!1!==l.apply(0,k)?!1!==E.apply(0,e)||n||(m(!0),y()):n?h.x=h.y=0:(m(!0),y())};(b.inverted||1<this.len)&&m();y();return h},defaultFormatter:function(a){var c=this.points||k(this),d;d=[a.tooltipFooterHeaderFormatter(c[0])];d=d.concat(a.bodyFormatter(c));d.push(a.tooltipFooterHeaderFormatter(c[0],!0));return d},refresh:function(a,
	d){var c,b=this.options,p,h=a,t,f={},n=[];c=b.formatter||this.defaultFormatter;var f=this.shared,e;clearTimeout(this.hideTimer);this.followPointer=k(h)[0].series.tooltipOptions.followPointer;t=this.getAnchor(h,d);d=t[0];p=t[1];!f||h.series&&h.series.noSharedTooltip?f=h.getLabelConfig():(A(h,function(a){a.setState("hover");n.push(a.getLabelConfig())}),f={x:h[0].category,y:h[0].y},f.points=n,h=h[0]);this.len=n.length;f=c.call(f,this);e=h.series;this.distance=q(e.tooltipOptions.distance,16);!1===f?this.hide():
	(c=this.getLabel(),this.isHidden&&c.attr({opacity:1}).show(),this.split?this.renderSplit(f,a):(c.attr({text:f&&f.join?f.join(""):f}),c.removeClass(/highcharts-color-[\d]+/g).addClass("highcharts-color-"+q(h.colorIndex,e.colorIndex)),c.attr({stroke:b.borderColor||h.color||e.color||"#666666"}),this.updatePosition({plotX:d,plotY:p,negative:h.negative,ttBelow:h.ttBelow,h:t[2]||0})),this.isHidden=!1)},renderSplit:function(c,d){var f=this,b=[],p=this.chart,h=p.renderer,t=!0,n=this.options,k,e=this.getLabel();
	A(c.slice(0,d.length+1),function(a,c){c=d[c-1]||{isHeader:!0,plotX:d[0].plotX};var w=c.series||f,m=w.tt,y=c.series||{},x="highcharts-color-"+q(c.colorIndex,y.colorIndex,"none");m||(w.tt=m=h.label(null,null,null,"callout").addClass("highcharts-tooltip-box "+x).attr({padding:n.padding,r:n.borderRadius,fill:n.backgroundColor,stroke:c.color||y.color||"#333333","stroke-width":n.borderWidth}).add(e));m.isActive=!0;m.attr({text:a});m.css(n.style);a=m.getBBox();y=a.width+m.strokeWidth();c.isHeader?(k=a.height,
	y=Math.max(0,Math.min(c.plotX+p.plotLeft-y/2,p.chartWidth-y))):y=c.plotX+p.plotLeft-q(n.distance,16)-y;0>y&&(t=!1);a=(c.series&&c.series.yAxis&&c.series.yAxis.pos)+(c.plotY||0);a-=p.plotTop;b.push({target:c.isHeader?p.plotHeight+k:a,rank:c.isHeader?1:0,size:w.tt.getBBox().height+1,point:c,x:y,tt:m})});this.cleanSplit();a.distribute(b,p.plotHeight+k);A(b,function(a){var b=a.point,c=b.series;a.tt.attr({visibility:void 0===a.pos?"hidden":"inherit",x:t||b.isHeader?a.x:b.plotX+p.plotLeft+q(n.distance,
	16),y:a.pos+p.plotTop,anchorX:b.isHeader?b.plotX+p.plotLeft:b.plotX+c.xAxis.pos,anchorY:b.isHeader?a.pos+p.plotTop-15:b.plotY+c.yAxis.pos})})},updatePosition:function(a){var c=this.chart,d=this.getLabel(),d=(this.options.positioner||this.getPosition).call(this,d.width,d.height,a);this.move(Math.round(d.x),Math.round(d.y||0),a.plotX+c.plotLeft,a.plotY+c.plotTop)},getDateFormat:function(a,f,k,b){var c=B("%m-%d %H:%M:%S.%L",f),h,t,n={millisecond:15,second:12,minute:9,hour:6,day:3},w="millisecond";for(t in d){if(a===
	d.week&&+B("%w",f)===k&&"00:00:00.000"===c.substr(6)){t="week";break}if(d[t]>a){t=w;break}if(n[t]&&c.substr(n[t])!=="01-01 00:00:00.000".substr(n[t]))break;"week"!==t&&(w=t)}t&&(h=b[t]);return h},getXDateFormat:function(a,d,f){d=d.dateTimeLabelFormats;var b=f&&f.closestPointRange;return(b?this.getDateFormat(b,a.x,f.options.startOfWeek,d):d.day)||d.year},tooltipFooterHeaderFormatter:function(a,d){var c=d?"footer":"header";d=a.series;var b=d.tooltipOptions,p=b.xDateFormat,h=d.xAxis,t=h&&"datetime"===
	h.options.type&&r(a.key),c=b[c+"Format"];t&&!p&&(p=this.getXDateFormat(a,b,h));t&&p&&(c=c.replace("{point.key}","{point.key:"+p+"}"));return G(c,{point:a,series:d})},bodyFormatter:function(a){return f(a,function(a){var c=a.series.tooltipOptions;return(c.pointFormatter||a.point.tooltipFormatter).call(a.point,c.pointFormat)})}}})(L);(function(a){var B=a.addEvent,A=a.attr,H=a.charts,G=a.color,r=a.css,f=a.defined,l=a.doc,q=a.each,k=a.extend,u=a.fireEvent,d=a.offset,c=a.pick,n=a.removeEvent,z=a.splat,
	b=a.Tooltip,p=a.win;a.Pointer=function(a,b){this.init(a,b)};a.Pointer.prototype={init:function(a,d){this.options=d;this.chart=a;this.runChartClick=d.chart.events&&!!d.chart.events.click;this.pinchDown=[];this.lastValidTouch={};b&&d.tooltip.enabled&&(a.tooltip=new b(a,d.tooltip),this.followTouchMove=c(d.tooltip.followTouchMove,!0));this.setDOMEvents()},zoomOption:function(a){var b=this.chart,h=b.options.chart,d=h.zoomType||"",b=b.inverted;/touch/.test(a.type)&&(d=c(h.pinchType,d));this.zoomX=a=/x/.test(d);
	this.zoomY=d=/y/.test(d);this.zoomHor=a&&!b||d&&b;this.zoomVert=d&&!b||a&&b;this.hasZoom=a||d},normalize:function(a,b){var c,h;a=a||p.event;a.target||(a.target=a.srcElement);h=a.touches?a.touches.length?a.touches.item(0):a.changedTouches[0]:a;b||(this.chartPosition=b=d(this.chart.container));void 0===h.pageX?(c=Math.max(a.x,a.clientX-b.left),b=a.y):(c=h.pageX-b.left,b=h.pageY-b.top);return k(a,{chartX:Math.round(c),chartY:Math.round(b)})},getCoordinates:function(a){var b={xAxis:[],yAxis:[]};q(this.chart.axes,
	function(c){b[c.isXAxis?"xAxis":"yAxis"].push({axis:c,value:c.toValue(a[c.horiz?"chartX":"chartY"])})});return b},getKDPoints:function(a,b,d){var h=[],e,p,f;q(a,function(a){e=a.noSharedTooltip&&b;p=!b&&a.directTouch;a.visible&&!p&&c(a.options.enableMouseTracking,!0)&&(f=a.searchPoint(d,!e&&0>a.options.findNearestPointBy.indexOf("y")))&&f.series&&h.push(f)});h.sort(function(a,c){var e=a.distX-c.distX,h=a.dist-c.dist,m=(c.series.group&&c.series.group.zIndex)-(a.series.group&&a.series.group.zIndex);
	return 0!==e&&b?e:0!==h?h:0!==m?m:a.series.index>c.series.index?-1:1});if(b&&h[0]&&!h[0].series.noSharedTooltip)for(a=h.length;a--;)(h[a].x!==h[0].x||h[a].series.noSharedTooltip)&&h.splice(a,1);return h},getPointFromEvent:function(a){a=a.target;for(var b;a&&!b;)b=a.point,a=a.parentNode;return b},getHoverData:function(b,d,p,f,e,n){var h=b,t=d,m;f?e?(m=[],q(p,function(a){var b=a.noSharedTooltip&&e,d=!e&&a.directTouch;a.visible&&!b&&!d&&c(a.options.enableMouseTracking,!0)&&(a=a.searchKDTree({clientX:h.clientX,
	plotY:h.plotY},!b&&1===a.kdDimensions))&&a.series&&m.push(a)}),0===m.length&&(m=[h])):m=[h]:t&&!t.stickyTracking?(e||(p=[t]),m=this.getKDPoints(p,e,n),h=a.find(m,function(a){return a.series===t})):(b=a.grep(p,function(a){return a.stickyTracking}),m=this.getKDPoints(b,e,n),t=(h=m[0])&&h.series,e&&(m=this.getKDPoints(p,e,n)));m.sort(function(a,b){return a.series.index-b.series.index});return{hoverPoint:h,hoverSeries:t,hoverPoints:m}},runPointActions:function(b,d){var h=this.chart,p=h.tooltip,e=p?p.shared:
	!1,f=d||h.hoverPoint,t=f&&f.series||h.hoverSeries;d=this.getHoverData(f,t,h.series,!!d||!e&&t&&t.directTouch,e,b);var n,m,f=d.hoverPoint;n=(t=d.hoverSeries)&&t.tooltipOptions.followPointer;m=(e=e&&f&&!f.series.noSharedTooltip)?d.hoverPoints:f?[f]:[];if(f&&(f!==h.hoverPoint||p&&p.isHidden)){q(h.hoverPoints||[],function(b){-1===a.inArray(b,m)&&b.setState()});q(m||[],function(a){a.setState("hover")});if(h.hoverSeries!==t)t.onMouseOver();t&&!t.directTouch&&(h.hoverPoint&&h.hoverPoint.firePointEvent("mouseOut"),
	f.firePointEvent("mouseOver"));h.hoverPoints=m;h.hoverPoint=f;p&&p.refresh(e?m:f,b)}else n&&p&&!p.isHidden&&(f=p.getAnchor([{}],b),p.updatePosition({plotX:f[0],plotY:f[1]}));this.unDocMouseMove||(this.unDocMouseMove=B(l,"mousemove",function(b){var c=H[a.hoverChartIndex];if(c)c.pointer.onDocumentMouseMove(b)}));q(h.axes,function(a){c(a.crosshair.snap,!0)?q(m,function(c){c.series[a.coll]===a&&a.drawCrosshair(b,c)}):a.drawCrosshair(b)})},reset:function(a,b){var c=this.chart,h=c.hoverSeries,e=c.hoverPoint,
	d=c.hoverPoints,p=c.tooltip,f=p&&p.shared?d:e;a&&f&&q(z(f),function(b){b.series.isCartesian&&void 0===b.plotX&&(a=!1)});if(a)p&&f&&(p.refresh(f),e&&(e.setState(e.state,!0),q(c.axes,function(a){a.crosshair&&a.drawCrosshair(null,e)})));else{if(e)e.onMouseOut();d&&q(d,function(a){a.setState()});if(h)h.onMouseOut();p&&p.hide(b);this.unDocMouseMove&&(this.unDocMouseMove=this.unDocMouseMove());q(c.axes,function(a){a.hideCrosshair()});this.hoverX=c.hoverPoints=c.hoverPoint=null}},scaleGroups:function(a,
	b){var c=this.chart,h;q(c.series,function(e){h=a||e.getPlotBox();e.xAxis&&e.xAxis.zoomEnabled&&e.group&&(e.group.attr(h),e.markerGroup&&(e.markerGroup.attr(h),e.markerGroup.clip(b?c.clipRect:null)),e.dataLabelsGroup&&e.dataLabelsGroup.attr(h))});c.clipRect.attr(b||c.clipBox)},dragStart:function(a){var b=this.chart;b.mouseIsDown=a.type;b.cancelClick=!1;b.mouseDownX=this.mouseDownX=a.chartX;b.mouseDownY=this.mouseDownY=a.chartY},drag:function(a){var b=this.chart,c=b.options.chart,h=a.chartX,e=a.chartY,
	d=this.zoomHor,p=this.zoomVert,f=b.plotLeft,m=b.plotTop,n=b.plotWidth,k=b.plotHeight,l,q=this.selectionMarker,g=this.mouseDownX,u=this.mouseDownY,r=c.panKey&&a[c.panKey+"Key"];q&&q.touch||(h<f?h=f:h>f+n&&(h=f+n),e<m?e=m:e>m+k&&(e=m+k),this.hasDragged=Math.sqrt(Math.pow(g-h,2)+Math.pow(u-e,2)),10<this.hasDragged&&(l=b.isInsidePlot(g-f,u-m),b.hasCartesianSeries&&(this.zoomX||this.zoomY)&&l&&!r&&!q&&(this.selectionMarker=q=b.renderer.rect(f,m,d?1:n,p?1:k,0).attr({fill:c.selectionMarkerFill||G("#335cad").setOpacity(.25).get(),
	"class":"highcharts-selection-marker",zIndex:7}).add()),q&&d&&(h-=g,q.attr({width:Math.abs(h),x:(0<h?0:h)+g})),q&&p&&(h=e-u,q.attr({height:Math.abs(h),y:(0<h?0:h)+u})),l&&!q&&c.panning&&b.pan(a,c.panning)))},drop:function(a){var b=this,c=this.chart,h=this.hasPinched;if(this.selectionMarker){var e={originalEvent:a,xAxis:[],yAxis:[]},d=this.selectionMarker,p=d.attr?d.attr("x"):d.x,n=d.attr?d.attr("y"):d.y,m=d.attr?d.attr("width"):d.width,y=d.attr?d.attr("height"):d.height,l;if(this.hasDragged||h)q(c.axes,
	function(c){if(c.zoomEnabled&&f(c.min)&&(h||b[{xAxis:"zoomX",yAxis:"zoomY"}[c.coll]])){var d=c.horiz,g="touchend"===a.type?c.minPixelPadding:0,t=c.toValue((d?p:n)+g),d=c.toValue((d?p+m:n+y)-g);e[c.coll].push({axis:c,min:Math.min(t,d),max:Math.max(t,d)});l=!0}}),l&&u(c,"selection",e,function(a){c.zoom(k(a,h?{animation:!1}:null))});this.selectionMarker=this.selectionMarker.destroy();h&&this.scaleGroups()}c&&(r(c.container,{cursor:c._cursor}),c.cancelClick=10<this.hasDragged,c.mouseIsDown=this.hasDragged=
	this.hasPinched=!1,this.pinchDown=[])},onContainerMouseDown:function(a){a=this.normalize(a);this.zoomOption(a);a.preventDefault&&a.preventDefault();this.dragStart(a)},onDocumentMouseUp:function(b){H[a.hoverChartIndex]&&H[a.hoverChartIndex].pointer.drop(b)},onDocumentMouseMove:function(a){var b=this.chart,c=this.chartPosition;a=this.normalize(a,c);!c||this.inClass(a.target,"highcharts-tracker")||b.isInsidePlot(a.chartX-b.plotLeft,a.chartY-b.plotTop)||this.reset()},onContainerMouseLeave:function(b){var c=
	H[a.hoverChartIndex];c&&(b.relatedTarget||b.toElement)&&(c.pointer.reset(),c.pointer.chartPosition=null)},onContainerMouseMove:function(b){var c=this.chart;f(a.hoverChartIndex)&&H[a.hoverChartIndex]&&H[a.hoverChartIndex].mouseIsDown||(a.hoverChartIndex=c.index);b=this.normalize(b);b.returnValue=!1;"mousedown"===c.mouseIsDown&&this.drag(b);!this.inClass(b.target,"highcharts-tracker")&&!c.isInsidePlot(b.chartX-c.plotLeft,b.chartY-c.plotTop)||c.openMenu||this.runPointActions(b)},inClass:function(a,b){for(var c;a;){if(c=
	A(a,"class")){if(-1!==c.indexOf(b))return!0;if(-1!==c.indexOf("highcharts-container"))return!1}a=a.parentNode}},onTrackerMouseOut:function(a){var b=this.chart.hoverSeries;a=a.relatedTarget||a.toElement;if(!(!b||!a||b.stickyTracking||this.inClass(a,"highcharts-tooltip")||this.inClass(a,"highcharts-series-"+b.index)&&this.inClass(a,"highcharts-tracker")))b.onMouseOut()},onContainerClick:function(a){var b=this.chart,c=b.hoverPoint,h=b.plotLeft,e=b.plotTop;a=this.normalize(a);b.cancelClick||(c&&this.inClass(a.target,
	"highcharts-tracker")?(u(c.series,"click",k(a,{point:c})),b.hoverPoint&&c.firePointEvent("click",a)):(k(a,this.getCoordinates(a)),b.isInsidePlot(a.chartX-h,a.chartY-e)&&u(b,"click",a)))},setDOMEvents:function(){var b=this,c=b.chart.container;c.onmousedown=function(a){b.onContainerMouseDown(a)};c.onmousemove=function(a){b.onContainerMouseMove(a)};c.onclick=function(a){b.onContainerClick(a)};B(c,"mouseleave",b.onContainerMouseLeave);1===a.chartCount&&B(l,"mouseup",b.onDocumentMouseUp);a.hasTouch&&(c.ontouchstart=
	function(a){b.onContainerTouchStart(a)},c.ontouchmove=function(a){b.onContainerTouchMove(a)},1===a.chartCount&&B(l,"touchend",b.onDocumentTouchEnd))},destroy:function(){var b;this.unDocMouseMove&&this.unDocMouseMove();n(this.chart.container,"mouseleave",this.onContainerMouseLeave);a.chartCount||(n(l,"mouseup",this.onDocumentMouseUp),n(l,"touchend",this.onDocumentTouchEnd));clearInterval(this.tooltipTimeout);for(b in this)this[b]=null}}})(L);(function(a){var B=a.charts,A=a.each,H=a.extend,G=a.map,
	r=a.noop,f=a.pick;H(a.Pointer.prototype,{pinchTranslate:function(a,f,k,u,d,c){this.zoomHor&&this.pinchTranslateDirection(!0,a,f,k,u,d,c);this.zoomVert&&this.pinchTranslateDirection(!1,a,f,k,u,d,c)},pinchTranslateDirection:function(a,f,k,u,d,c,n,r){var b=this.chart,p=a?"x":"y",h=a?"X":"Y",t="chart"+h,l=a?"width":"height",w=b["plot"+(a?"Left":"Top")],e,q,C=r||1,E=b.inverted,m=b.bounds[a?"h":"v"],y=1===f.length,I=f[0][t],K=k[0][t],J=!y&&f[1][t],g=!y&&k[1][t],z;k=function(){!y&&20<Math.abs(I-J)&&(C=r||
	Math.abs(K-g)/Math.abs(I-J));q=(w-K)/C+I;e=b["plot"+(a?"Width":"Height")]/C};k();f=q;f<m.min?(f=m.min,z=!0):f+e>m.max&&(f=m.max-e,z=!0);z?(K-=.8*(K-n[p][0]),y||(g-=.8*(g-n[p][1])),k()):n[p]=[K,g];E||(c[p]=q-w,c[l]=e);c=E?1/C:C;d[l]=e;d[p]=f;u[E?a?"scaleY":"scaleX":"scale"+h]=C;u["translate"+h]=c*w+(K-c*I)},pinch:function(a){var l=this,k=l.chart,u=l.pinchDown,d=a.touches,c=d.length,n=l.lastValidTouch,z=l.hasZoom,b=l.selectionMarker,p={},h=1===c&&(l.inClass(a.target,"highcharts-tracker")&&k.runTrackerClick||
	l.runChartClick),t={};1<c&&(l.initiated=!0);z&&l.initiated&&!h&&a.preventDefault();G(d,function(a){return l.normalize(a)});"touchstart"===a.type?(A(d,function(a,b){u[b]={chartX:a.chartX,chartY:a.chartY}}),n.x=[u[0].chartX,u[1]&&u[1].chartX],n.y=[u[0].chartY,u[1]&&u[1].chartY],A(k.axes,function(a){if(a.zoomEnabled){var b=k.bounds[a.horiz?"h":"v"],c=a.minPixelPadding,h=a.toPixels(f(a.options.min,a.dataMin)),d=a.toPixels(f(a.options.max,a.dataMax)),p=Math.max(h,d);b.min=Math.min(a.pos,Math.min(h,d)-
	c);b.max=Math.max(a.pos+a.len,p+c)}}),l.res=!0):l.followTouchMove&&1===c?this.runPointActions(l.normalize(a)):u.length&&(b||(l.selectionMarker=b=H({destroy:r,touch:!0},k.plotBox)),l.pinchTranslate(u,d,p,b,t,n),l.hasPinched=z,l.scaleGroups(p,t),l.res&&(l.res=!1,this.reset(!1,0)))},touch:function(l,q){var k=this.chart,u,d;if(k.index!==a.hoverChartIndex)this.onContainerMouseLeave({relatedTarget:!0});a.hoverChartIndex=k.index;1===l.touches.length?(l=this.normalize(l),(d=k.isInsidePlot(l.chartX-k.plotLeft,
	l.chartY-k.plotTop))&&!k.openMenu?(q&&this.runPointActions(l),"touchmove"===l.type&&(q=this.pinchDown,u=q[0]?4<=Math.sqrt(Math.pow(q[0].chartX-l.chartX,2)+Math.pow(q[0].chartY-l.chartY,2)):!1),f(u,!0)&&this.pinch(l)):q&&this.reset()):2===l.touches.length&&this.pinch(l)},onContainerTouchStart:function(a){this.zoomOption(a);this.touch(a,!0)},onContainerTouchMove:function(a){this.touch(a)},onDocumentTouchEnd:function(f){B[a.hoverChartIndex]&&B[a.hoverChartIndex].pointer.drop(f)}})})(L);(function(a){var B=
	a.addEvent,A=a.charts,H=a.css,G=a.doc,r=a.extend,f=a.noop,l=a.Pointer,q=a.removeEvent,k=a.win,u=a.wrap;if(k.PointerEvent||k.MSPointerEvent){var d={},c=!!k.PointerEvent,n=function(){var a,c=[];c.item=function(a){return this[a]};for(a in d)d.hasOwnProperty(a)&&c.push({pageX:d[a].pageX,pageY:d[a].pageY,target:d[a].target});return c},z=function(b,c,h,d){"touch"!==b.pointerType&&b.pointerType!==b.MSPOINTER_TYPE_TOUCH||!A[a.hoverChartIndex]||(d(b),d=A[a.hoverChartIndex].pointer,d[c]({type:h,target:b.currentTarget,
	preventDefault:f,touches:n()}))};r(l.prototype,{onContainerPointerDown:function(a){z(a,"onContainerTouchStart","touchstart",function(a){d[a.pointerId]={pageX:a.pageX,pageY:a.pageY,target:a.currentTarget}})},onContainerPointerMove:function(a){z(a,"onContainerTouchMove","touchmove",function(a){d[a.pointerId]={pageX:a.pageX,pageY:a.pageY};d[a.pointerId].target||(d[a.pointerId].target=a.currentTarget)})},onDocumentPointerUp:function(a){z(a,"onDocumentTouchEnd","touchend",function(a){delete d[a.pointerId]})},
	batchMSEvents:function(a){a(this.chart.container,c?"pointerdown":"MSPointerDown",this.onContainerPointerDown);a(this.chart.container,c?"pointermove":"MSPointerMove",this.onContainerPointerMove);a(G,c?"pointerup":"MSPointerUp",this.onDocumentPointerUp)}});u(l.prototype,"init",function(a,c,h){a.call(this,c,h);this.hasZoom&&H(c.container,{"-ms-touch-action":"none","touch-action":"none"})});u(l.prototype,"setDOMEvents",function(a){a.apply(this);(this.hasZoom||this.followTouchMove)&&this.batchMSEvents(B)});
	u(l.prototype,"destroy",function(a){this.batchMSEvents(q);a.call(this)})}})(L);(function(a){var B,A=a.addEvent,H=a.css,G=a.discardElement,r=a.defined,f=a.each,l=a.isFirefox,q=a.marginNames,k=a.merge,u=a.pick,d=a.setAnimation,c=a.stableSort,n=a.win,z=a.wrap;B=a.Legend=function(a,c){this.init(a,c)};B.prototype={init:function(a,c){this.chart=a;this.setOptions(c);c.enabled&&(this.render(),A(this.chart,"endResize",function(){this.legend.positionCheckboxes()}))},setOptions:function(a){var b=u(a.padding,
	8);this.options=a;this.itemStyle=a.itemStyle;this.itemHiddenStyle=k(this.itemStyle,a.itemHiddenStyle);this.itemMarginTop=a.itemMarginTop||0;this.padding=b;this.initialItemY=b-5;this.itemHeight=this.maxItemWidth=0;this.symbolWidth=u(a.symbolWidth,16);this.pages=[]},update:function(a,c){var b=this.chart;this.setOptions(k(!0,this.options,a));this.destroy();b.isDirtyLegend=b.isDirtyBox=!0;u(c,!0)&&b.redraw()},colorizeItem:function(a,c){a.legendGroup[c?"removeClass":"addClass"]("highcharts-legend-item-hidden");
	var b=this.options,d=a.legendItem,f=a.legendLine,p=a.legendSymbol,e=this.itemHiddenStyle.color,b=c?b.itemStyle.color:e,n=c?a.color||e:e,k=a.options&&a.options.marker,l={fill:n},m;d&&d.css({fill:b,color:b});f&&f.attr({stroke:n});if(p){if(k&&p.isMarker&&(l=a.pointAttribs(),!c))for(m in l)l[m]=e;p.attr(l)}},positionItem:function(a){var b=this.options,c=b.symbolPadding,b=!b.rtl,d=a._legendItemPos,f=d[0],d=d[1],n=a.checkbox;(a=a.legendGroup)&&a.element&&a.translate(b?f:this.legendWidth-f-2*c-4,d);n&&(n.x=
	f,n.y=d)},destroyItem:function(a){var b=a.checkbox;f(["legendItem","legendLine","legendSymbol","legendGroup"],function(b){a[b]&&(a[b]=a[b].destroy())});b&&G(a.checkbox)},destroy:function(){function a(a){this[a]&&(this[a]=this[a].destroy())}f(this.getAllItems(),function(b){f(["legendItem","legendGroup"],a,b)});f("clipRect up down pager nav box title group".split(" "),a,this);this.display=null},positionCheckboxes:function(a){var b=this.group&&this.group.alignAttr,c,d=this.clipHeight||this.legendHeight,
	n=this.titleHeight;b&&(c=b.translateY,f(this.allItems,function(h){var e=h.checkbox,f;e&&(f=c+n+e.y+(a||0)+3,H(e,{left:b.translateX+h.checkboxOffset+e.x-20+"px",top:f+"px",display:f>c-6&&f<c+d-6?"":"none"}))}))},renderTitle:function(){var a=this.padding,c=this.options.title,h=0;c.text&&(this.title||(this.title=this.chart.renderer.label(c.text,a-3,a-4,null,null,null,null,null,"legend-title").attr({zIndex:1}).css(c.style).add(this.group)),a=this.title.getBBox(),h=a.height,this.offsetWidth=a.width,this.contentGroup.attr({translateY:h}));
	this.titleHeight=h},setText:function(b){var c=this.options;b.legendItem.attr({text:c.labelFormat?a.format(c.labelFormat,b):c.labelFormatter.call(b)})},renderItem:function(a){var b=this.chart,c=b.renderer,d=this.options,f="horizontal"===d.layout,n=this.symbolWidth,e=d.symbolPadding,l=this.itemStyle,q=this.itemHiddenStyle,r=this.padding,m=f?u(d.itemDistance,20):0,y=!d.rtl,I=d.width,K=d.itemMarginBottom||0,J=this.itemMarginTop,g=a.legendItem,z=!a.series,Q=!z&&a.series.drawLegendSymbol?a.series:a,A=Q.options,
	A=this.createCheckboxForItem&&A&&A.showCheckbox,B=d.useHTML,H=a.options.className;g||(a.legendGroup=c.g("legend-item").addClass("highcharts-"+Q.type+"-series highcharts-color-"+a.colorIndex+(H?" "+H:"")+(z?" highcharts-series-"+a.index:"")).attr({zIndex:1}).add(this.scrollGroup),a.legendItem=g=c.text("",y?n+e:-e,this.baseline||0,B).css(k(a.visible?l:q)).attr({align:y?"left":"right",zIndex:2}).add(a.legendGroup),this.baseline||(l=l.fontSize,this.fontMetrics=c.fontMetrics(l,g),this.baseline=this.fontMetrics.f+
	3+J,g.attr("y",this.baseline)),this.symbolHeight=d.symbolHeight||this.fontMetrics.f,Q.drawLegendSymbol(this,a),this.setItemEvents&&this.setItemEvents(a,g,B),A&&this.createCheckboxForItem(a));this.colorizeItem(a,a.visible);this.setText(a);c=g.getBBox();n=a.checkboxOffset=d.itemWidth||a.legendItemWidth||n+e+c.width+m+(A?20:0);this.itemHeight=e=Math.round(a.legendItemHeight||c.height||this.symbolHeight);f&&this.itemX-r+n>(I||b.spacingBox.width-2*r-d.x)&&(this.itemX=r,this.itemY+=J+this.lastLineHeight+
	K,this.lastLineHeight=0);this.maxItemWidth=Math.max(this.maxItemWidth,n);this.lastItemY=J+this.itemY+K;this.lastLineHeight=Math.max(e,this.lastLineHeight);a._legendItemPos=[this.itemX,this.itemY];f?this.itemX+=n:(this.itemY+=J+e+K,this.lastLineHeight=e);this.offsetWidth=I||Math.max((f?this.itemX-r-m:n)+r,this.offsetWidth)},getAllItems:function(){var a=[];f(this.chart.series,function(b){var c=b&&b.options;b&&u(c.showInLegend,r(c.linkedTo)?!1:void 0,!0)&&(a=a.concat(b.legendItems||("point"===c.legendType?
	b.data:b)))});return a},adjustMargins:function(a,c){var b=this.chart,d=this.options,p=d.align.charAt(0)+d.verticalAlign.charAt(0)+d.layout.charAt(0);d.floating||f([/(lth|ct|rth)/,/(rtv|rm|rbv)/,/(rbh|cb|lbh)/,/(lbv|lm|ltv)/],function(h,e){h.test(p)&&!r(a[e])&&(b[q[e]]=Math.max(b[q[e]],b.legend[(e+1)%2?"legendHeight":"legendWidth"]+[1,-1,-1,1][e]*d[e%2?"x":"y"]+u(d.margin,12)+c[e]))})},render:function(){var a=this,d=a.chart,h=d.renderer,n=a.group,l,q,e,x,u=a.box,r=a.options,m=a.padding;a.itemX=m;a.itemY=
	a.initialItemY;a.offsetWidth=0;a.lastItemY=0;n||(a.group=n=h.g("legend").attr({zIndex:7}).add(),a.contentGroup=h.g().attr({zIndex:1}).add(n),a.scrollGroup=h.g().add(a.contentGroup));a.renderTitle();l=a.getAllItems();c(l,function(a,b){return(a.options&&a.options.legendIndex||0)-(b.options&&b.options.legendIndex||0)});r.reversed&&l.reverse();a.allItems=l;a.display=q=!!l.length;a.lastLineHeight=0;f(l,function(b){a.renderItem(b)});e=(r.width||a.offsetWidth)+m;x=a.lastItemY+a.lastLineHeight+a.titleHeight;
	x=a.handleOverflow(x);x+=m;u||(a.box=u=h.rect().addClass("highcharts-legend-box").attr({r:r.borderRadius}).add(n),u.isNew=!0);u.attr({stroke:r.borderColor,"stroke-width":r.borderWidth||0,fill:r.backgroundColor||"none"}).shadow(r.shadow);0<e&&0<x&&(u[u.isNew?"attr":"animate"](u.crisp({x:0,y:0,width:e,height:x},u.strokeWidth())),u.isNew=!1);u[q?"show":"hide"]();a.legendWidth=e;a.legendHeight=x;f(l,function(b){a.positionItem(b)});q&&n.align(k(r,{width:e,height:x}),!0,"spacingBox");d.isResizing||this.positionCheckboxes()},
	handleOverflow:function(a){var b=this,c=this.chart,d=c.renderer,n=this.options,k=n.y,e=this.padding,c=c.spacingBox.height+("top"===n.verticalAlign?-k:k)-e,k=n.maxHeight,l,q=this.clipRect,r=n.navigation,m=u(r.animation,!0),y=r.arrowSize||12,I=this.nav,K=this.pages,J,g=this.allItems,z=function(a){a?q.attr({height:a}):q&&(b.clipRect=q.destroy(),b.contentGroup.clip());b.contentGroup.div&&(b.contentGroup.div.style.clip=a?"rect("+e+"px,9999px,"+(e+a)+"px,0)":"auto")};"horizontal"!==n.layout||"middle"===
	n.verticalAlign||n.floating||(c/=2);k&&(c=Math.min(c,k));K.length=0;a>c&&!1!==r.enabled?(this.clipHeight=l=Math.max(c-20-this.titleHeight-e,0),this.currentPage=u(this.currentPage,1),this.fullHeight=a,f(g,function(a,b){var c=a._legendItemPos[1];a=Math.round(a.legendItem.getBBox().height);var e=K.length;if(!e||c-K[e-1]>l&&(J||c)!==K[e-1])K.push(J||c),e++;b===g.length-1&&c+a-K[e-1]>l&&K.push(c);c!==J&&(J=c)}),q||(q=b.clipRect=d.clipRect(0,e,9999,0),b.contentGroup.clip(q)),z(l),I||(this.nav=I=d.g().attr({zIndex:1}).add(this.group),
	this.up=d.symbol("triangle",0,0,y,y).on("click",function(){b.scroll(-1,m)}).add(I),this.pager=d.text("",15,10).addClass("highcharts-legend-navigation").css(r.style).add(I),this.down=d.symbol("triangle-down",0,0,y,y).on("click",function(){b.scroll(1,m)}).add(I)),b.scroll(0),a=c):I&&(z(),this.nav=I.destroy(),this.scrollGroup.attr({translateY:1}),this.clipHeight=0);return a},scroll:function(a,c){var b=this.pages,f=b.length;a=this.currentPage+a;var p=this.clipHeight,n=this.options.navigation,e=this.pager,
	k=this.padding;a>f&&(a=f);0<a&&(void 0!==c&&d(c,this.chart),this.nav.attr({translateX:k,translateY:p+this.padding+7+this.titleHeight,visibility:"visible"}),this.up.attr({"class":1===a?"highcharts-legend-nav-inactive":"highcharts-legend-nav-active"}),e.attr({text:a+"/"+f}),this.down.attr({x:18+this.pager.getBBox().width,"class":a===f?"highcharts-legend-nav-inactive":"highcharts-legend-nav-active"}),this.up.attr({fill:1===a?n.inactiveColor:n.activeColor}).css({cursor:1===a?"default":"pointer"}),this.down.attr({fill:a===
	f?n.inactiveColor:n.activeColor}).css({cursor:a===f?"default":"pointer"}),c=-b[a-1]+this.initialItemY,this.scrollGroup.animate({translateY:c}),this.currentPage=a,this.positionCheckboxes(c))}};a.LegendSymbolMixin={drawRectangle:function(a,c){var b=a.symbolHeight,d=a.options.squareSymbol;c.legendSymbol=this.chart.renderer.rect(d?(a.symbolWidth-b)/2:0,a.baseline-b+1,d?b:a.symbolWidth,b,u(a.options.symbolRadius,b/2)).addClass("highcharts-point").attr({zIndex:3}).add(c.legendGroup)},drawLineMarker:function(a){var b=
	this.options,c=b.marker,d=a.symbolWidth,f=a.symbolHeight,n=f/2,e=this.chart.renderer,l=this.legendGroup;a=a.baseline-Math.round(.3*a.fontMetrics.b);var q;q={"stroke-width":b.lineWidth||0};b.dashStyle&&(q.dashstyle=b.dashStyle);this.legendLine=e.path(["M",0,a,"L",d,a]).addClass("highcharts-graph").attr(q).add(l);c&&!1!==c.enabled&&(b=Math.min(u(c.radius,n),n),0===this.symbol.indexOf("url")&&(c=k(c,{width:f,height:f}),b=0),this.legendSymbol=c=e.symbol(this.symbol,d/2-b,a-b,2*b,2*b,c).addClass("highcharts-point").add(l),
	c.isMarker=!0)}};(/Trident\/7\.0/.test(n.navigator.userAgent)||l)&&z(B.prototype,"positionItem",function(a,c){var b=this,d=function(){c._legendItemPos&&a.call(b,c)};d();setTimeout(d)})})(L);(function(a){var B=a.addEvent,A=a.animate,H=a.animObject,G=a.attr,r=a.doc,f=a.Axis,l=a.createElement,q=a.defaultOptions,k=a.discardElement,u=a.charts,d=a.css,c=a.defined,n=a.each,z=a.extend,b=a.find,p=a.fireEvent,h=a.getStyle,t=a.grep,D=a.isNumber,w=a.isObject,e=a.isString,x=a.Legend,C=a.marginNames,E=a.merge,
	m=a.Pointer,y=a.pick,I=a.pInt,K=a.removeEvent,J=a.seriesTypes,g=a.splat,F=a.svg,Q=a.syncTimeout,N=a.win,P=a.Renderer,O=a.Chart=function(){this.getArgs.apply(this,arguments)};a.chart=function(a,b,c){return new O(a,b,c)};O.prototype={callbacks:[],getArgs:function(){var a=[].slice.call(arguments);if(e(a[0])||a[0].nodeName)this.renderTo=a.shift();this.init(a[0],a[1])},init:function(b,c){var e,g=b.series;b.series=null;e=E(q,b);e.series=b.series=g;this.userOptions=b;b=e.chart;g=b.events;this.margin=[];
	this.spacing=[];this.bounds={h:{},v:{}};this.callback=c;this.isResizing=0;this.options=e;this.axes=[];this.series=[];this.hasCartesianSeries=b.showAxes;var d;this.index=u.length;u.push(this);a.chartCount++;if(g)for(d in g)B(this,d,g[d]);this.xAxis=[];this.yAxis=[];this.pointCount=this.colorCounter=this.symbolCounter=0;this.firstRender()},initSeries:function(b){var c=this.options.chart;(c=J[b.type||c.type||c.defaultSeriesType])||a.error(17,!0);c=new c;c.init(this,b);return c},orderSeries:function(a){var b=
	this.series;for(a=a||0;a<b.length;a++)b[a]&&(b[a].index=a,b[a].name=b[a].name||"Series "+(b[a].index+1))},isInsidePlot:function(a,b,c){var e=c?b:a;a=c?a:b;return 0<=e&&e<=this.plotWidth&&0<=a&&a<=this.plotHeight},redraw:function(b){var c=this.axes,e=this.series,g=this.pointer,d=this.legend,m=this.isDirtyLegend,h,f,v=this.hasCartesianSeries,k=this.isDirtyBox,y,l=this.renderer,t=l.isHidden(),q=[];this.setResponsive&&this.setResponsive(!1);a.setAnimation(b,this);t&&this.cloneRenderTo();this.layOutTitles();
	for(b=e.length;b--;)if(y=e[b],y.options.stacking&&(h=!0,y.isDirty)){f=!0;break}if(f)for(b=e.length;b--;)y=e[b],y.options.stacking&&(y.isDirty=!0);n(e,function(a){a.isDirty&&"point"===a.options.legendType&&(a.updateTotals&&a.updateTotals(),m=!0);a.isDirtyData&&p(a,"updatedData")});m&&d.options.enabled&&(d.render(),this.isDirtyLegend=!1);h&&this.getStacks();v&&n(c,function(a){a.updateNames();a.setScale()});this.getMargins();v&&(n(c,function(a){a.isDirty&&(k=!0)}),n(c,function(a){var b=a.min+","+a.max;
	a.extKey!==b&&(a.extKey=b,q.push(function(){p(a,"afterSetExtremes",z(a.eventArgs,a.getExtremes()));delete a.eventArgs}));(k||h)&&a.redraw()}));k&&this.drawChartBox();p(this,"predraw");n(e,function(a){(k||a.isDirty)&&a.visible&&a.redraw();a.isDirtyData=!1});g&&g.reset(!0);l.draw();p(this,"redraw");p(this,"render");t&&this.cloneRenderTo(!0);n(q,function(a){a.call()})},get:function(a){function c(b){return b.id===a||b.options&&b.options.id===a}var e,g=this.series,d;e=b(this.axes,c)||b(this.series,c);
	for(d=0;!e&&d<g.length;d++)e=b(g[d].points||[],c);return e},getAxes:function(){var a=this,b=this.options,c=b.xAxis=g(b.xAxis||{}),b=b.yAxis=g(b.yAxis||{});n(c,function(a,b){a.index=b;a.isX=!0});n(b,function(a,b){a.index=b});c=c.concat(b);n(c,function(b){new f(a,b)})},getSelectedPoints:function(){var a=[];n(this.series,function(b){a=a.concat(t(b.points||[],function(a){return a.selected}))});return a},getSelectedSeries:function(){return t(this.series,function(a){return a.selected})},setTitle:function(a,
	b,c){var e=this,g=e.options,d;d=g.title=E({style:{color:"#333333",fontSize:g.isStock?"16px":"18px"}},g.title,a);g=g.subtitle=E({style:{color:"#666666"}},g.subtitle,b);n([["title",a,d],["subtitle",b,g]],function(a,b){var c=a[0],g=e[c],d=a[1];a=a[2];g&&d&&(e[c]=g=g.destroy());a&&a.text&&!g&&(e[c]=e.renderer.text(a.text,0,0,a.useHTML).attr({align:a.align,"class":"highcharts-"+c,zIndex:a.zIndex||4}).add(),e[c].update=function(a){e.setTitle(!b&&a,b&&a)},e[c].css(a.style))});e.layOutTitles(c)},layOutTitles:function(a){var b=
	0,c,e=this.renderer,g=this.spacingBox;n(["title","subtitle"],function(a){var c=this[a],d=this.options[a],m;c&&(m=d.style.fontSize,m=e.fontMetrics(m,c).b,c.css({width:(d.width||g.width+d.widthAdjust)+"px"}).align(z({y:b+m+("title"===a?-3:2)},d),!1,"spacingBox"),d.floating||d.verticalAlign||(b=Math.ceil(b+c.getBBox(d.useHTML).height)))},this);c=this.titleOffset!==b;this.titleOffset=b;!this.isDirtyBox&&c&&(this.isDirtyBox=c,this.hasRendered&&y(a,!0)&&this.isDirtyBox&&this.redraw())},getChartSize:function(){var b=
	this.options.chart,e=b.width,b=b.height,g=this.renderToClone||this.renderTo;c(e)||(this.containerWidth=h(g,"width"));c(b)||(this.containerHeight=h(g,"height"));this.chartWidth=Math.max(0,e||this.containerWidth||600);this.chartHeight=Math.max(0,a.relativeLength(b,this.chartWidth)||this.containerHeight||400)},cloneRenderTo:function(a){var b=this.renderToClone,c=this.container;if(a){if(b){for(;b.childNodes.length;)this.renderTo.appendChild(b.firstChild);k(b);delete this.renderToClone}}else c&&c.parentNode===
	this.renderTo&&this.renderTo.removeChild(c),this.renderToClone=b=this.renderTo.cloneNode(0),d(b,{position:"absolute",top:"-9999px",display:"block"}),b.style.setProperty&&b.style.setProperty("display","block","important"),r.body.appendChild(b),c&&b.appendChild(c)},setClassName:function(a){this.container.className="highcharts-container "+(a||"")},getContainer:function(){var b,c=this.options,g=c.chart,d,m;b=this.renderTo;var h=a.uniqueKey(),f;b||(this.renderTo=b=g.renderTo);e(b)&&(this.renderTo=b=r.getElementById(b));
	b||a.error(13,!0);d=I(G(b,"data-highcharts-chart"));D(d)&&u[d]&&u[d].hasRendered&&u[d].destroy();G(b,"data-highcharts-chart",this.index);b.innerHTML="";g.skipClone||b.offsetWidth||this.cloneRenderTo();this.getChartSize();d=this.chartWidth;m=this.chartHeight;f=z({position:"relative",overflow:"hidden",width:d+"px",height:m+"px",textAlign:"left",lineHeight:"normal",zIndex:0,"-webkit-tap-highlight-color":"rgba(0,0,0,0)"},g.style);this.container=b=l("div",{id:h},f,this.renderToClone||b);this._cursor=b.style.cursor;
	this.renderer=new (a[g.renderer]||P)(b,d,m,null,g.forExport,c.exporting&&c.exporting.allowHTML);this.setClassName(g.className);this.renderer.setStyle(g.style);this.renderer.chartIndex=this.index},getMargins:function(a){var b=this.spacing,e=this.margin,g=this.titleOffset;this.resetMargins();g&&!c(e[0])&&(this.plotTop=Math.max(this.plotTop,g+this.options.title.margin+b[0]));this.legend.display&&this.legend.adjustMargins(e,b);this.extraMargin&&(this[this.extraMargin.type]=(this[this.extraMargin.type]||
	0)+this.extraMargin.value);this.extraTopMargin&&(this.plotTop+=this.extraTopMargin);a||this.getAxisMargins()},getAxisMargins:function(){var a=this,b=a.axisOffset=[0,0,0,0],e=a.margin;a.hasCartesianSeries&&n(a.axes,function(a){a.visible&&a.getOffset()});n(C,function(g,d){c(e[d])||(a[g]+=b[d])});a.setChartSize()},reflow:function(a){var b=this,e=b.options.chart,g=b.renderTo,d=c(e.width),m=e.width||h(g,"width"),e=e.height||h(g,"height"),g=a?a.target:N;if(!d&&!b.isPrinting&&m&&e&&(g===N||g===r)){if(m!==
	b.containerWidth||e!==b.containerHeight)clearTimeout(b.reflowTimeout),b.reflowTimeout=Q(function(){b.container&&b.setSize(void 0,void 0,!1)},a?100:0);b.containerWidth=m;b.containerHeight=e}},initReflow:function(){var a=this,b;b=B(N,"resize",function(b){a.reflow(b)});B(a,"destroy",b)},setSize:function(b,c,e){var g=this,m=g.renderer;g.isResizing+=1;a.setAnimation(e,g);g.oldChartHeight=g.chartHeight;g.oldChartWidth=g.chartWidth;void 0!==b&&(g.options.chart.width=b);void 0!==c&&(g.options.chart.height=
	c);g.getChartSize();b=m.globalAnimation;(b?A:d)(g.container,{width:g.chartWidth+"px",height:g.chartHeight+"px"},b);g.setChartSize(!0);m.setSize(g.chartWidth,g.chartHeight,e);n(g.axes,function(a){a.isDirty=!0;a.setScale()});g.isDirtyLegend=!0;g.isDirtyBox=!0;g.layOutTitles();g.getMargins();g.redraw(e);g.oldChartHeight=null;p(g,"resize");Q(function(){g&&p(g,"endResize",null,function(){--g.isResizing})},H(b).duration)},setChartSize:function(a){var b=this.inverted,c=this.renderer,e=this.chartWidth,g=
	this.chartHeight,d=this.options.chart,m=this.spacing,h=this.clipOffset,f,p,k,y;this.plotLeft=f=Math.round(this.plotLeft);this.plotTop=p=Math.round(this.plotTop);this.plotWidth=k=Math.max(0,Math.round(e-f-this.marginRight));this.plotHeight=y=Math.max(0,Math.round(g-p-this.marginBottom));this.plotSizeX=b?y:k;this.plotSizeY=b?k:y;this.plotBorderWidth=d.plotBorderWidth||0;this.spacingBox=c.spacingBox={x:m[3],y:m[0],width:e-m[3]-m[1],height:g-m[0]-m[2]};this.plotBox=c.plotBox={x:f,y:p,width:k,height:y};
	e=2*Math.floor(this.plotBorderWidth/2);b=Math.ceil(Math.max(e,h[3])/2);c=Math.ceil(Math.max(e,h[0])/2);this.clipBox={x:b,y:c,width:Math.floor(this.plotSizeX-Math.max(e,h[1])/2-b),height:Math.max(0,Math.floor(this.plotSizeY-Math.max(e,h[2])/2-c))};a||n(this.axes,function(a){a.setAxisSize();a.setAxisTranslation()})},resetMargins:function(){var a=this,b=a.options.chart;n(["margin","spacing"],function(c){var e=b[c],g=w(e)?e:[e,e,e,e];n(["Top","Right","Bottom","Left"],function(e,d){a[c][d]=y(b[c+e],g[d])})});
	n(C,function(b,c){a[b]=y(a.margin[c],a.spacing[c])});a.axisOffset=[0,0,0,0];a.clipOffset=[0,0,0,0]},drawChartBox:function(){var a=this.options.chart,b=this.renderer,c=this.chartWidth,e=this.chartHeight,g=this.chartBackground,d=this.plotBackground,m=this.plotBorder,h,f=this.plotBGImage,n=a.backgroundColor,p=a.plotBackgroundColor,k=a.plotBackgroundImage,y,l=this.plotLeft,t=this.plotTop,q=this.plotWidth,u=this.plotHeight,x=this.plotBox,r=this.clipRect,w=this.clipBox,I="animate";g||(this.chartBackground=
	g=b.rect().addClass("highcharts-background").add(),I="attr");h=a.borderWidth||0;y=h+(a.shadow?8:0);n={fill:n||"none"};if(h||g["stroke-width"])n.stroke=a.borderColor,n["stroke-width"]=h;g.attr(n).shadow(a.shadow);g[I]({x:y/2,y:y/2,width:c-y-h%2,height:e-y-h%2,r:a.borderRadius});I="animate";d||(I="attr",this.plotBackground=d=b.rect().addClass("highcharts-plot-background").add());d[I](x);d.attr({fill:p||"none"}).shadow(a.plotShadow);k&&(f?f.animate(x):this.plotBGImage=b.image(k,l,t,q,u).add());r?r.animate({width:w.width,
	height:w.height}):this.clipRect=b.clipRect(w);I="animate";m||(I="attr",this.plotBorder=m=b.rect().addClass("highcharts-plot-border").attr({zIndex:1}).add());m.attr({stroke:a.plotBorderColor,"stroke-width":a.plotBorderWidth||0,fill:"none"});m[I](m.crisp({x:l,y:t,width:q,height:u},-m.strokeWidth()));this.isDirtyBox=!1},propFromSeries:function(){var a=this,b=a.options.chart,c,e=a.options.series,g,d;n(["inverted","angular","polar"],function(m){c=J[b.type||b.defaultSeriesType];d=b[m]||c&&c.prototype[m];
	for(g=e&&e.length;!d&&g--;)(c=J[e[g].type])&&c.prototype[m]&&(d=!0);a[m]=d})},linkSeries:function(){var a=this,b=a.series;n(b,function(a){a.linkedSeries.length=0});n(b,function(b){var c=b.options.linkedTo;e(c)&&(c=":previous"===c?a.series[b.index-1]:a.get(c))&&c.linkedParent!==b&&(c.linkedSeries.push(b),b.linkedParent=c,b.visible=y(b.options.visible,c.options.visible,b.visible))})},renderSeries:function(){n(this.series,function(a){a.translate();a.render()})},renderLabels:function(){var a=this,b=a.options.labels;
	b.items&&n(b.items,function(c){var e=z(b.style,c.style),g=I(e.left)+a.plotLeft,d=I(e.top)+a.plotTop+12;delete e.left;delete e.top;a.renderer.text(c.html,g,d).attr({zIndex:2}).css(e).add()})},render:function(){var a=this.axes,b=this.renderer,c=this.options,e,g,d;this.setTitle();this.legend=new x(this,c.legend);this.getStacks&&this.getStacks();this.getMargins(!0);this.setChartSize();c=this.plotWidth;e=this.plotHeight-=21;n(a,function(a){a.setScale()});this.getAxisMargins();g=1.1<c/this.plotWidth;d=
	1.05<e/this.plotHeight;if(g||d)n(a,function(a){(a.horiz&&g||!a.horiz&&d)&&a.setTickInterval(!0)}),this.getMargins();this.drawChartBox();this.hasCartesianSeries&&n(a,function(a){a.visible&&a.render()});this.seriesGroup||(this.seriesGroup=b.g("series-group").attr({zIndex:3}).add());this.renderSeries();this.renderLabels();this.addCredits();this.setResponsive&&this.setResponsive();this.hasRendered=!0},addCredits:function(a){var b=this;a=E(!0,this.options.credits,a);a.enabled&&!this.credits&&(this.credits=
	this.renderer.text(a.text+(this.mapCredits||""),0,0).addClass("highcharts-credits").on("click",function(){a.href&&(N.location.href=a.href)}).attr({align:a.position.align,zIndex:8}).css(a.style).add().align(a.position),this.credits.update=function(a){b.credits=b.credits.destroy();b.addCredits(a)})},destroy:function(){var b=this,c=b.axes,e=b.series,g=b.container,d,m=g&&g.parentNode;p(b,"destroy");u[b.index]=void 0;a.chartCount--;b.renderTo.removeAttribute("data-highcharts-chart");K(b);for(d=c.length;d--;)c[d]=
	c[d].destroy();this.scroller&&this.scroller.destroy&&this.scroller.destroy();for(d=e.length;d--;)e[d]=e[d].destroy();n("title subtitle chartBackground plotBackground plotBGImage plotBorder seriesGroup clipRect credits pointer rangeSelector legend resetZoomButton tooltip renderer".split(" "),function(a){var c=b[a];c&&c.destroy&&(b[a]=c.destroy())});g&&(g.innerHTML="",K(g),m&&k(g));for(d in b)delete b[d]},isReadyToRender:function(){var a=this;return F||N!=N.top||"complete"===r.readyState?!0:(r.attachEvent("onreadystatechange",
	function(){r.detachEvent("onreadystatechange",a.firstRender);"complete"===r.readyState&&a.firstRender()}),!1)},firstRender:function(){var a=this,b=a.options;if(a.isReadyToRender()){a.getContainer();p(a,"init");a.resetMargins();a.setChartSize();a.propFromSeries();a.getAxes();n(b.series||[],function(b){a.initSeries(b)});a.linkSeries();p(a,"beforeRender");m&&(a.pointer=new m(a,b));a.render();if(!a.renderer.imgCount&&a.onload)a.onload();a.cloneRenderTo(!0)}},onload:function(){n([this.callback].concat(this.callbacks),
	function(a){a&&void 0!==this.index&&a.apply(this,[this])},this);p(this,"load");p(this,"render");c(this.index)&&!1!==this.options.chart.reflow&&this.initReflow();this.onload=null}}})(L);(function(a){var B,A=a.each,H=a.extend,G=a.erase,r=a.fireEvent,f=a.format,l=a.isArray,q=a.isNumber,k=a.pick,u=a.removeEvent;B=a.Point=function(){};B.prototype={init:function(a,c,f){this.series=a;this.color=a.color;this.applyOptions(c,f);a.options.colorByPoint?(c=a.options.colors||a.chart.options.colors,this.color=this.color||
	c[a.colorCounter],c=c.length,f=a.colorCounter,a.colorCounter++,a.colorCounter===c&&(a.colorCounter=0)):f=a.colorIndex;this.colorIndex=k(this.colorIndex,f);a.chart.pointCount++;return this},applyOptions:function(a,c){var d=this.series,f=d.options.pointValKey||d.pointValKey;a=B.prototype.optionsToObject.call(this,a);H(this,a);this.options=this.options?H(this.options,a):a;a.group&&delete this.group;f&&(this.y=this[f]);this.isNull=k(this.isValid&&!this.isValid(),null===this.x||!q(this.y,!0));this.selected&&
	(this.state="select");"name"in this&&void 0===c&&d.xAxis&&d.xAxis.hasNames&&(this.x=d.xAxis.nameToX(this));void 0===this.x&&d&&(this.x=void 0===c?d.autoIncrement(this):c);return this},optionsToObject:function(a){var c={},d=this.series,f=d.options.keys,b=f||d.pointArrayMap||["y"],p=b.length,h=0,k=0;if(q(a)||null===a)c[b[0]]=a;else if(l(a))for(!f&&a.length>p&&(d=typeof a[0],"string"===d?c.name=a[0]:"number"===d&&(c.x=a[0]),h++);k<p;)f&&void 0===a[h]||(c[b[k]]=a[h]),h++,k++;else"object"===typeof a&&
	(c=a,a.dataLabels&&(d._hasPointLabels=!0),a.marker&&(d._hasPointMarkers=!0));return c},getClassName:function(){return"highcharts-point"+(this.selected?" highcharts-point-select":"")+(this.negative?" highcharts-negative":"")+(this.isNull?" highcharts-null-point":"")+(void 0!==this.colorIndex?" highcharts-color-"+this.colorIndex:"")+(this.options.className?" "+this.options.className:"")+(this.zone&&this.zone.className?" "+this.zone.className.replace("highcharts-negative",""):"")},getZone:function(){var a=
	this.series,c=a.zones,a=a.zoneAxis||"y",f=0,k;for(k=c[f];this[a]>=k.value;)k=c[++f];k&&k.color&&!this.options.color&&(this.color=k.color);return k},destroy:function(){var a=this.series.chart,c=a.hoverPoints,f;a.pointCount--;c&&(this.setState(),G(c,this),c.length||(a.hoverPoints=null));if(this===a.hoverPoint)this.onMouseOut();if(this.graphic||this.dataLabel)u(this),this.destroyElements();this.legendItem&&a.legend.destroyItem(this);for(f in this)this[f]=null},destroyElements:function(){for(var a=["graphic",
	"dataLabel","dataLabelUpper","connector","shadowGroup"],c,f=6;f--;)c=a[f],this[c]&&(this[c]=this[c].destroy())},getLabelConfig:function(){return{x:this.category,y:this.y,color:this.color,colorIndex:this.colorIndex,key:this.name||this.category,series:this.series,point:this,percentage:this.percentage,total:this.total||this.stackTotal}},tooltipFormatter:function(a){var c=this.series,d=c.tooltipOptions,l=k(d.valueDecimals,""),b=d.valuePrefix||"",p=d.valueSuffix||"";A(c.pointArrayMap||["y"],function(c){c=
	"{point."+c;if(b||p)a=a.replace(c+"}",b+c+"}"+p);a=a.replace(c+"}",c+":,."+l+"f}")});return f(a,{point:this,series:this.series})},firePointEvent:function(a,c,f){var d=this,b=this.series.options;(b.point.events[a]||d.options&&d.options.events&&d.options.events[a])&&this.importEvents();"click"===a&&b.allowPointSelect&&(f=function(a){d.select&&d.select(null,a.ctrlKey||a.metaKey||a.shiftKey)});r(this,a,c,f)},visible:!0}})(L);(function(a){var B=a.addEvent,A=a.animObject,H=a.arrayMax,G=a.arrayMin,r=a.correctFloat,
	f=a.Date,l=a.defaultOptions,q=a.defaultPlotOptions,k=a.defined,u=a.each,d=a.erase,c=a.extend,n=a.fireEvent,z=a.grep,b=a.isArray,p=a.isNumber,h=a.isString,t=a.merge,D=a.pick,w=a.removeEvent,e=a.splat,x=a.SVGElement,C=a.syncTimeout,E=a.win;a.Series=a.seriesType("line",null,{lineWidth:2,allowPointSelect:!1,showCheckbox:!1,animation:{duration:1E3},events:{},marker:{lineWidth:0,lineColor:"#ffffff",radius:4,states:{hover:{animation:{duration:50},enabled:!0,radiusPlus:2,lineWidthPlus:1},select:{fillColor:"#cccccc",
	lineColor:"#000000",lineWidth:2}}},point:{events:{}},dataLabels:{align:"center",formatter:function(){return null===this.y?"":a.numberFormat(this.y,-1)},style:{fontSize:"11px",fontWeight:"bold",color:"contrast",textOutline:"1px contrast"},verticalAlign:"bottom",x:0,y:0,padding:5},cropThreshold:300,pointRange:0,softThreshold:!0,states:{hover:{animation:{duration:50},lineWidthPlus:1,marker:{},halo:{size:10,opacity:.25}},select:{marker:{}}},stickyTracking:!0,turboThreshold:1E3,findNearestPointBy:"x"},
	{isCartesian:!0,pointClass:a.Point,sorted:!0,requireSorting:!0,directTouch:!1,axisTypes:["xAxis","yAxis"],colorCounter:0,parallelArrays:["x","y"],coll:"series",init:function(a,b){var e=this,d,m,g=a.series,h;e.chart=a;e.options=b=e.setOptions(b);e.linkedSeries=[];e.bindAxes();c(e,{name:b.name,state:"",visible:!1!==b.visible,selected:!0===b.selected});m=b.events;for(d in m)B(e,d,m[d]);if(m&&m.click||b.point&&b.point.events&&b.point.events.click||b.allowPointSelect)a.runTrackerClick=!0;e.getColor();
	e.getSymbol();u(e.parallelArrays,function(a){e[a+"Data"]=[]});e.setData(b.data,!1);e.isCartesian&&(a.hasCartesianSeries=!0);g.length&&(h=g[g.length-1]);e._i=D(h&&h._i,-1)+1;a.orderSeries(this.insert(g))},insert:function(a){var b=this.options.index,c;if(p(b)){for(c=a.length;c--;)if(b>=D(a[c].options.index,a[c]._i)){a.splice(c+1,0,this);break}-1===c&&a.unshift(this);c+=1}else a.push(this);return D(c,a.length-1)},bindAxes:function(){var b=this,c=b.options,e=b.chart,d;u(b.axisTypes||[],function(m){u(e[m],
	function(a){d=a.options;if(c[m]===d.index||void 0!==c[m]&&c[m]===d.id||void 0===c[m]&&0===d.index)b.insert(a.series),b[m]=a,a.isDirty=!0});b[m]||b.optionalAxis===m||a.error(18,!0)})},updateParallelArrays:function(a,b){var c=a.series,e=arguments,d=p(b)?function(e){var g="y"===e&&c.toYData?c.toYData(a):a[e];c[e+"Data"][b]=g}:function(a){Array.prototype[b].apply(c[a+"Data"],Array.prototype.slice.call(e,2))};u(c.parallelArrays,d)},autoIncrement:function(){var a=this.options,b=this.xIncrement,c,e=a.pointIntervalUnit,
	b=D(b,a.pointStart,0);this.pointInterval=c=D(this.pointInterval,a.pointInterval,1);e&&(a=new f(b),"day"===e?a=+a[f.hcSetDate](a[f.hcGetDate]()+c):"month"===e?a=+a[f.hcSetMonth](a[f.hcGetMonth]()+c):"year"===e&&(a=+a[f.hcSetFullYear](a[f.hcGetFullYear]()+c)),c=a-b);this.xIncrement=b+c;return b},setOptions:function(a){var b=this.chart,c=b.options.plotOptions,b=b.userOptions||{},e=b.plotOptions||{},d=c[this.type];this.userOptions=a;c=t(d,c.series,a);this.tooltipOptions=t(l.tooltip,l.plotOptions[this.type].tooltip,
	b.tooltip,e.series&&e.series.tooltip,e[this.type]&&e[this.type].tooltip,a.tooltip);this.stickyTracking=D(a.stickyTracking,e[this.type]&&e[this.type].stickyTracking,e.series&&e.series.stickyTracking,this.tooltipOptions.shared&&!this.noSharedTooltip?!0:c.stickyTracking);null===d.marker&&delete c.marker;this.zoneAxis=c.zoneAxis;a=this.zones=(c.zones||[]).slice();!c.negativeColor&&!c.negativeFillColor||c.zones||a.push({value:c[this.zoneAxis+"Threshold"]||c.threshold||0,className:"highcharts-negative",
	color:c.negativeColor,fillColor:c.negativeFillColor});a.length&&k(a[a.length-1].value)&&a.push({color:this.color,fillColor:this.fillColor});return c},getCyclic:function(a,b,c){var e,d=this.chart,g=this.userOptions,m=a+"Index",h=a+"Counter",f=c?c.length:D(d.options.chart[a+"Count"],d[a+"Count"]);b||(e=D(g[m],g["_"+m]),k(e)||(d.series.length||(d[h]=0),g["_"+m]=e=d[h]%f,d[h]+=1),c&&(b=c[e]));void 0!==e&&(this[m]=e);this[a]=b},getColor:function(){this.options.colorByPoint?this.options.color=null:this.getCyclic("color",
	this.options.color||q[this.type].color,this.chart.options.colors)},getSymbol:function(){this.getCyclic("symbol",this.options.marker.symbol,this.chart.options.symbols)},drawLegendSymbol:a.LegendSymbolMixin.drawLineMarker,setData:function(c,e,d,f){var m=this,g=m.points,k=g&&g.length||0,n,l=m.options,y=m.chart,t=null,q=m.xAxis,x=l.turboThreshold,r=this.xData,w=this.yData,C=(n=m.pointArrayMap)&&n.length;c=c||[];n=c.length;e=D(e,!0);if(!1!==f&&n&&k===n&&!m.cropped&&!m.hasGroupedData&&m.visible)u(c,function(a,
	b){g[b].update&&a!==l.data[b]&&g[b].update(a,!1,null,!1)});else{m.xIncrement=null;m.colorCounter=0;u(this.parallelArrays,function(a){m[a+"Data"].length=0});if(x&&n>x){for(d=0;null===t&&d<n;)t=c[d],d++;if(p(t))for(d=0;d<n;d++)r[d]=this.autoIncrement(),w[d]=c[d];else if(b(t))if(C)for(d=0;d<n;d++)t=c[d],r[d]=t[0],w[d]=t.slice(1,C+1);else for(d=0;d<n;d++)t=c[d],r[d]=t[0],w[d]=t[1];else a.error(12)}else for(d=0;d<n;d++)void 0!==c[d]&&(t={series:m},m.pointClass.prototype.applyOptions.apply(t,[c[d]]),m.updateParallelArrays(t,
	d));h(w[0])&&a.error(14,!0);m.data=[];m.options.data=m.userOptions.data=c;for(d=k;d--;)g[d]&&g[d].destroy&&g[d].destroy();q&&(q.minRange=q.userMinRange);m.isDirty=y.isDirtyBox=!0;m.isDirtyData=!!g;d=!1}"point"===l.legendType&&(this.processData(),this.generatePoints());e&&y.redraw(d)},processData:function(b){var c=this.xData,e=this.yData,d=c.length,m;m=0;var g,h,f=this.xAxis,p,k=this.options;p=k.cropThreshold;var n=this.getExtremesFromAll||k.getExtremesFromAll,l=this.isCartesian,k=f&&f.val2lin,t=f&&
	f.isLog,q,x;if(l&&!this.isDirty&&!f.isDirty&&!this.yAxis.isDirty&&!b)return!1;f&&(b=f.getExtremes(),q=b.min,x=b.max);if(l&&this.sorted&&!n&&(!p||d>p||this.forceCrop))if(c[d-1]<q||c[0]>x)c=[],e=[];else if(c[0]<q||c[d-1]>x)m=this.cropData(this.xData,this.yData,q,x),c=m.xData,e=m.yData,m=m.start,g=!0;for(p=c.length||1;--p;)d=t?k(c[p])-k(c[p-1]):c[p]-c[p-1],0<d&&(void 0===h||d<h)?h=d:0>d&&this.requireSorting&&a.error(15);this.cropped=g;this.cropStart=m;this.processedXData=c;this.processedYData=e;this.closestPointRange=
	h},cropData:function(a,b,c,e){var d=a.length,g=0,m=d,h=D(this.cropShoulder,1),f;for(f=0;f<d;f++)if(a[f]>=c){g=Math.max(0,f-h);break}for(c=f;c<d;c++)if(a[c]>e){m=c+h;break}return{xData:a.slice(g,m),yData:b.slice(g,m),start:g,end:m}},generatePoints:function(){var a=this.options.data,b=this.data,c,d=this.processedXData,h=this.processedYData,g=this.pointClass,f=d.length,p=this.cropStart||0,k,n=this.hasGroupedData,l,t=[],q;b||n||(b=[],b.length=a.length,b=this.data=b);for(q=0;q<f;q++)k=p+q,n?(l=(new g).init(this,
	[d[q]].concat(e(h[q]))),l.dataGroup=this.groupMap[q]):(l=b[k])||void 0===a[k]||(b[k]=l=(new g).init(this,a[k],d[q])),l&&(l.index=k,t[q]=l);if(b&&(f!==(c=b.length)||n))for(q=0;q<c;q++)q!==p||n||(q+=f),b[q]&&(b[q].destroyElements(),b[q].plotX=void 0);this.data=b;this.points=t},getExtremes:function(a){var c=this.yAxis,e=this.processedXData,d,m=[],g=0;d=this.xAxis.getExtremes();var h=d.min,f=d.max,k,n,l,t;a=a||this.stackedYData||this.processedYData||[];d=a.length;for(t=0;t<d;t++)if(n=e[t],l=a[t],k=(p(l,
	!0)||b(l))&&(!c.positiveValuesOnly||l.length||0<l),n=this.getExtremesFromAll||this.options.getExtremesFromAll||this.cropped||(e[t]||n)>=h&&(e[t]||n)<=f,k&&n)if(k=l.length)for(;k--;)null!==l[k]&&(m[g++]=l[k]);else m[g++]=l;this.dataMin=G(m);this.dataMax=H(m)},translate:function(){this.processedXData||this.processData();this.generatePoints();var a=this.options,b=a.stacking,c=this.xAxis,e=c.categories,d=this.yAxis,g=this.points,h=g.length,f=!!this.modifyValue,n=a.pointPlacement,l="between"===n||p(n),
	t=a.threshold,q=a.startFromThreshold?t:0,x,u,w,C,E=Number.MAX_VALUE;"between"===n&&(n=.5);p(n)&&(n*=D(a.pointRange||c.pointRange));for(a=0;a<h;a++){var z=g[a],A=z.x,B=z.y;u=z.low;var H=b&&d.stacks[(this.negStacks&&B<(q?0:t)?"-":"")+this.stackKey],G;d.positiveValuesOnly&&null!==B&&0>=B&&(z.isNull=!0);z.plotX=x=r(Math.min(Math.max(-1E5,c.translate(A,0,0,0,1,n,"flags"===this.type)),1E5));b&&this.visible&&!z.isNull&&H&&H[A]&&(C=this.getStackIndicator(C,A,this.index),G=H[A],B=G.points[C.key],u=B[0],B=
	B[1],u===q&&C.key===H[A].base&&(u=D(t,d.min)),d.positiveValuesOnly&&0>=u&&(u=null),z.total=z.stackTotal=G.total,z.percentage=G.total&&z.y/G.total*100,z.stackY=B,G.setOffset(this.pointXOffset||0,this.barW||0));z.yBottom=k(u)?d.translate(u,0,1,0,1):null;f&&(B=this.modifyValue(B,z));z.plotY=u="number"===typeof B&&Infinity!==B?Math.min(Math.max(-1E5,d.translate(B,0,1,0,1)),1E5):void 0;z.isInside=void 0!==u&&0<=u&&u<=d.len&&0<=x&&x<=c.len;z.clientX=l?r(c.translate(A,0,0,0,1,n)):x;z.negative=z.y<(t||0);
	z.category=e&&void 0!==e[z.x]?e[z.x]:z.x;z.isNull||(void 0!==w&&(E=Math.min(E,Math.abs(x-w))),w=x);z.zone=this.zones.length&&z.getZone()}this.closestPointRangePx=E},getValidPoints:function(a,b){var c=this.chart;return z(a||this.points||[],function(a){return b&&!c.isInsidePlot(a.plotX,a.plotY,c.inverted)?!1:!a.isNull})},setClip:function(a){var b=this.chart,c=this.options,e=b.renderer,d=b.inverted,g=this.clipBox,h=g||b.clipBox,m=this.sharedClipKey||["_sharedClip",a&&a.duration,a&&a.easing,h.height,
	c.xAxis,c.yAxis].join(),f=b[m],p=b[m+"m"];f||(a&&(h.width=0,b[m+"m"]=p=e.clipRect(-99,d?-b.plotLeft:-b.plotTop,99,d?b.chartWidth:b.chartHeight)),b[m]=f=e.clipRect(h),f.count={length:0});a&&!f.count[this.index]&&(f.count[this.index]=!0,f.count.length+=1);!1!==c.clip&&(this.group.clip(a||g?f:b.clipRect),this.markerGroup.clip(p),this.sharedClipKey=m);a||(f.count[this.index]&&(delete f.count[this.index],--f.count.length),0===f.count.length&&m&&b[m]&&(g||(b[m]=b[m].destroy()),b[m+"m"]&&(b[m+"m"]=b[m+"m"].destroy())))},
	animate:function(a){var b=this.chart,c=A(this.options.animation),e;a?this.setClip(c):(e=this.sharedClipKey,(a=b[e])&&a.animate({width:b.plotSizeX},c),b[e+"m"]&&b[e+"m"].animate({width:b.plotSizeX+99},c),this.animate=null)},afterAnimate:function(){this.setClip();n(this,"afterAnimate")},drawPoints:function(){var a=this.points,b=this.chart,c,e,d,g,h=this.options.marker,f,n,k,l,t=this.markerGroup,q=D(h.enabled,this.xAxis.isRadial?!0:null,this.closestPointRangePx>=2*h.radius);if(!1!==h.enabled||this._hasPointMarkers)for(e=
	0;e<a.length;e++)d=a[e],c=d.plotY,g=d.graphic,f=d.marker||{},n=!!d.marker,k=q&&void 0===f.enabled||f.enabled,l=d.isInside,k&&p(c)&&null!==d.y?(c=D(f.symbol,this.symbol),d.hasImage=0===c.indexOf("url"),k=this.markerAttribs(d,d.selected&&"select"),g?g[l?"show":"hide"](!0).animate(k):l&&(0<k.width||d.hasImage)&&(d.graphic=g=b.renderer.symbol(c,k.x,k.y,k.width,k.height,n?f:h).add(t)),g&&g.attr(this.pointAttribs(d,d.selected&&"select")),g&&g.addClass(d.getClassName(),!0)):g&&(d.graphic=g.destroy())},markerAttribs:function(a,
	b){var c=this.options.marker,e=a.marker||{},d=D(e.radius,c.radius);b&&(c=c.states[b],b=e.states&&e.states[b],d=D(b&&b.radius,c&&c.radius,d+(c&&c.radiusPlus||0)));a.hasImage&&(d=0);a={x:Math.floor(a.plotX)-d,y:a.plotY-d};d&&(a.width=a.height=2*d);return a},pointAttribs:function(a,b){var c=this.options.marker,e=a&&a.options,d=e&&e.marker||{},g=this.color,h=e&&e.color,f=a&&a.color,e=D(d.lineWidth,c.lineWidth);a=a&&a.zone&&a.zone.color;g=h||a||f||g;a=d.fillColor||c.fillColor||g;g=d.lineColor||c.lineColor||
	g;b&&(c=c.states[b],b=d.states&&d.states[b]||{},e=D(b.lineWidth,c.lineWidth,e+D(b.lineWidthPlus,c.lineWidthPlus,0)),a=b.fillColor||c.fillColor||a,g=b.lineColor||c.lineColor||g);return{stroke:g,"stroke-width":e,fill:a}},destroy:function(){var a=this,b=a.chart,c=/AppleWebKit\/533/.test(E.navigator.userAgent),e,h=a.data||[],g,f,k;n(a,"destroy");w(a);u(a.axisTypes||[],function(b){(k=a[b])&&k.series&&(d(k.series,a),k.isDirty=k.forceRedraw=!0)});a.legendItem&&a.chart.legend.destroyItem(a);for(e=h.length;e--;)(g=
	h[e])&&g.destroy&&g.destroy();a.points=null;clearTimeout(a.animationTimeout);for(f in a)a[f]instanceof x&&!a[f].survive&&(e=c&&"group"===f?"hide":"destroy",a[f][e]());b.hoverSeries===a&&(b.hoverSeries=null);d(b.series,a);b.orderSeries();for(f in a)delete a[f]},getGraphPath:function(a,b,c){var e=this,d=e.options,g=d.step,h,f=[],m=[],p;a=a||e.points;(h=a.reversed)&&a.reverse();(g={right:1,center:2}[g]||g&&3)&&h&&(g=4-g);!d.connectNulls||b||c||(a=this.getValidPoints(a));u(a,function(h,n){var l=h.plotX,
	t=h.plotY,q=a[n-1];(h.leftCliff||q&&q.rightCliff)&&!c&&(p=!0);h.isNull&&!k(b)&&0<n?p=!d.connectNulls:h.isNull&&!b?p=!0:(0===n||p?n=["M",h.plotX,h.plotY]:e.getPointSpline?n=e.getPointSpline(a,h,n):g?(n=1===g?["L",q.plotX,t]:2===g?["L",(q.plotX+l)/2,q.plotY,"L",(q.plotX+l)/2,t]:["L",l,q.plotY],n.push("L",l,t)):n=["L",l,t],m.push(h.x),g&&m.push(h.x),f.push.apply(f,n),p=!1)});f.xMap=m;return e.graphPath=f},drawGraph:function(){var a=this,b=this.options,c=(this.gappedPath||this.getGraphPath).call(this),
	e=[["graph","highcharts-graph",b.lineColor||this.color,b.dashStyle]];u(this.zones,function(c,d){e.push(["zone-graph-"+d,"highcharts-graph highcharts-zone-graph-"+d+" "+(c.className||""),c.color||a.color,c.dashStyle||b.dashStyle])});u(e,function(e,d){var g=e[0],h=a[g];h?(h.endX=c.xMap,h.animate({d:c})):c.length&&(a[g]=a.chart.renderer.path(c).addClass(e[1]).attr({zIndex:1}).add(a.group),h={stroke:e[2],"stroke-width":b.lineWidth,fill:a.fillGraph&&a.color||"none"},e[3]?h.dashstyle=e[3]:"square"!==b.linecap&&
	(h["stroke-linecap"]=h["stroke-linejoin"]="round"),h=a[g].attr(h).shadow(2>d&&b.shadow));h&&(h.startX=c.xMap,h.isArea=c.isArea)})},applyZones:function(){var a=this,b=this.chart,c=b.renderer,e=this.zones,d,g,h=this.clips||[],f,k=this.graph,p=this.area,n=Math.max(b.chartWidth,b.chartHeight),l=this[(this.zoneAxis||"y")+"Axis"],t,q,x=b.inverted,w,r,C,E,z=!1;e.length&&(k||p)&&l&&void 0!==l.min&&(q=l.reversed,w=l.horiz,k&&k.hide(),p&&p.hide(),t=l.getExtremes(),u(e,function(e,m){d=q?w?b.plotWidth:0:w?0:
	l.toPixels(t.min);d=Math.min(Math.max(D(g,d),0),n);g=Math.min(Math.max(Math.round(l.toPixels(D(e.value,t.max),!0)),0),n);z&&(d=g=l.toPixels(t.max));r=Math.abs(d-g);C=Math.min(d,g);E=Math.max(d,g);l.isXAxis?(f={x:x?E:C,y:0,width:r,height:n},w||(f.x=b.plotHeight-f.x)):(f={x:0,y:x?E:C,width:n,height:r},w&&(f.y=b.plotWidth-f.y));x&&c.isVML&&(f=l.isXAxis?{x:0,y:q?C:E,height:f.width,width:b.chartWidth}:{x:f.y-b.plotLeft-b.spacingBox.x,y:0,width:f.height,height:b.chartHeight});h[m]?h[m].animate(f):(h[m]=
	c.clipRect(f),k&&a["zone-graph-"+m].clip(h[m]),p&&a["zone-area-"+m].clip(h[m]));z=e.value>t.max}),this.clips=h)},invertGroups:function(a){function b(){u(["group","markerGroup"],function(b){c[b]&&(e.renderer.isVML&&c[b].attr({width:c.yAxis.len,height:c.xAxis.len}),c[b].width=c.yAxis.len,c[b].height=c.xAxis.len,c[b].invert(a))})}var c=this,e=c.chart,d;c.xAxis&&(d=B(e,"resize",b),B(c,"destroy",d),b(a),c.invertGroups=b)},plotGroup:function(a,b,c,e,d){var g=this[a],h=!g;h&&(this[a]=g=this.chart.renderer.g(b).attr({zIndex:e||
	.1}).add(d),g.addClass("highcharts-series-"+this.index+" highcharts-"+this.type+"-series highcharts-color-"+this.colorIndex+" "+(this.options.className||"")));g.attr({visibility:c})[h?"attr":"animate"](this.getPlotBox());return g},getPlotBox:function(){var a=this.chart,b=this.xAxis,c=this.yAxis;a.inverted&&(b=c,c=this.xAxis);return{translateX:b?b.left:a.plotLeft,translateY:c?c.top:a.plotTop,scaleX:1,scaleY:1}},render:function(){var a=this,b=a.chart,c,e=a.options,d=!!a.animate&&b.renderer.isSVG&&A(e.animation).duration,
	g=a.visible?"inherit":"hidden",h=e.zIndex,f=a.hasRendered,k=b.seriesGroup,p=b.inverted;c=a.plotGroup("group","series",g,h,k);a.markerGroup=a.plotGroup("markerGroup","markers",g,h,k);d&&a.animate(!0);c.inverted=a.isCartesian?p:!1;a.drawGraph&&(a.drawGraph(),a.applyZones());a.drawDataLabels&&a.drawDataLabels();a.visible&&a.drawPoints();a.drawTracker&&!1!==a.options.enableMouseTracking&&a.drawTracker();a.invertGroups(p);!1===e.clip||a.sharedClipKey||f||c.clip(b.clipRect);d&&a.animate();f||(a.animationTimeout=
	C(function(){a.afterAnimate()},d));a.isDirty=!1;a.hasRendered=!0},redraw:function(){var a=this.chart,b=this.isDirty||this.isDirtyData,c=this.group,e=this.xAxis,d=this.yAxis;c&&(a.inverted&&c.attr({width:a.plotWidth,height:a.plotHeight}),c.animate({translateX:D(e&&e.left,a.plotLeft),translateY:D(d&&d.top,a.plotTop)}));this.translate();this.render();b&&delete this.kdTree},kdAxisArray:["clientX","plotY"],searchPoint:function(a,b){var c=this.xAxis,e=this.yAxis,d=this.chart.inverted;return this.searchKDTree({clientX:d?
	c.len-a.chartY+c.pos:a.chartX-c.pos,plotY:d?e.len-a.chartX+e.pos:a.chartY-e.pos},b)},buildKDTree:function(){function a(c,e,d){var g,h;if(h=c&&c.length)return g=b.kdAxisArray[e%d],c.sort(function(a,b){return a[g]-b[g]}),h=Math.floor(h/2),{point:c[h],left:a(c.slice(0,h),e+1,d),right:a(c.slice(h+1),e+1,d)}}this.buildingKdTree=!0;var b=this,c=-1<b.options.findNearestPointBy.indexOf("y")?2:1;delete b.kdTree;C(function(){b.kdTree=a(b.getValidPoints(null,!b.directTouch),c,c);b.buildingKdTree=!1},b.options.kdNow?
	0:1)},searchKDTree:function(a,b){function c(a,b,f,m){var p=b.point,n=e.kdAxisArray[f%m],l,t,q=p;t=k(a[d])&&k(p[d])?Math.pow(a[d]-p[d],2):null;l=k(a[g])&&k(p[g])?Math.pow(a[g]-p[g],2):null;l=(t||0)+(l||0);p.dist=k(l)?Math.sqrt(l):Number.MAX_VALUE;p.distX=k(t)?Math.sqrt(t):Number.MAX_VALUE;n=a[n]-p[n];l=0>n?"left":"right";t=0>n?"right":"left";b[l]&&(l=c(a,b[l],f+1,m),q=l[h]<q[h]?l:p);b[t]&&Math.sqrt(n*n)<q[h]&&(a=c(a,b[t],f+1,m),q=a[h]<q[h]?a:q);return q}var e=this,d=this.kdAxisArray[0],g=this.kdAxisArray[1],
	h=b?"distX":"dist";b=-1<e.options.findNearestPointBy.indexOf("y")?2:1;this.kdTree||this.buildingKdTree||this.buildKDTree();if(this.kdTree)return c(a,this.kdTree,b,b)}})})(L);(function(a){function B(a,d,c,f,l){var b=a.chart.inverted;this.axis=a;this.isNegative=c;this.options=d;this.x=f;this.total=null;this.points={};this.stack=l;this.rightCliff=this.leftCliff=0;this.alignOptions={align:d.align||(b?c?"left":"right":"center"),verticalAlign:d.verticalAlign||(b?"middle":c?"bottom":"top"),y:k(d.y,b?4:c?
	14:-6),x:k(d.x,b?c?-6:6:0)};this.textAlign=d.textAlign||(b?c?"right":"left":"center")}var A=a.Axis,H=a.Chart,G=a.correctFloat,r=a.defined,f=a.destroyObjectProperties,l=a.each,q=a.format,k=a.pick;a=a.Series;B.prototype={destroy:function(){f(this,this.axis)},render:function(a){var d=this.options,c=d.format,c=c?q(c,this):d.formatter.call(this);this.label?this.label.attr({text:c,visibility:"hidden"}):this.label=this.axis.chart.renderer.text(c,null,null,d.useHTML).css(d.style).attr({align:this.textAlign,
	rotation:d.rotation,visibility:"hidden"}).add(a)},setOffset:function(a,d){var c=this.axis,f=c.chart,k=f.inverted,b=c.reversed,b=this.isNegative&&!b||!this.isNegative&&b,p=c.translate(c.usePercentage?100:this.total,0,0,0,1),c=c.translate(0),c=Math.abs(p-c);a=f.xAxis[0].translate(this.x)+a;var h=f.plotHeight,k={x:k?b?p:p-c:a,y:k?h-a-d:b?h-p-c:h-p,width:k?c:d,height:k?d:c};if(d=this.label)d.align(this.alignOptions,null,k),k=d.alignAttr,d[!1===this.options.crop||f.isInsidePlot(k.x,k.y)?"show":"hide"](!0)}};
	H.prototype.getStacks=function(){var a=this;l(a.yAxis,function(a){a.stacks&&a.hasVisibleSeries&&(a.oldStacks=a.stacks)});l(a.series,function(d){!d.options.stacking||!0!==d.visible&&!1!==a.options.chart.ignoreHiddenSeries||(d.stackKey=d.type+k(d.options.stack,""))})};A.prototype.buildStacks=function(){var a=this.series,d,c=k(this.options.reversedStacks,!0),f=a.length,l;if(!this.isXAxis){this.usePercentage=!1;for(l=f;l--;)a[c?l:f-l-1].setStackedPoints();for(l=f;l--;)d=a[c?l:f-l-1],d.setStackCliffs&&
	d.setStackCliffs();if(this.usePercentage)for(l=0;l<f;l++)a[l].setPercentStacks()}};A.prototype.renderStackTotals=function(){var a=this.chart,d=a.renderer,c=this.stacks,f,k,b=this.stackTotalGroup;b||(this.stackTotalGroup=b=d.g("stack-labels").attr({visibility:"visible",zIndex:6}).add());b.translate(a.plotLeft,a.plotTop);for(f in c)for(k in a=c[f],a)a[k].render(b)};A.prototype.resetStacks=function(){var a=this.stacks,d,c;if(!this.isXAxis)for(d in a)for(c in a[d])a[d][c].touched<this.stacksTouched?(a[d][c].destroy(),
	delete a[d][c]):(a[d][c].total=null,a[d][c].cum=null)};A.prototype.cleanStacks=function(){var a,d,c;if(!this.isXAxis)for(d in this.oldStacks&&(a=this.stacks=this.oldStacks),a)for(c in a[d])a[d][c].cum=a[d][c].total};a.prototype.setStackedPoints=function(){if(this.options.stacking&&(!0===this.visible||!1===this.chart.options.chart.ignoreHiddenSeries)){var a=this.processedXData,d=this.processedYData,c=[],f=d.length,l=this.options,b=l.threshold,p=l.startFromThreshold?b:0,h=l.stack,l=l.stacking,t=this.stackKey,
	q="-"+t,w=this.negStacks,e=this.yAxis,x=e.stacks,C=e.oldStacks,E,m,y,A,K,J,g;e.stacksTouched+=1;for(K=0;K<f;K++)J=a[K],g=d[K],E=this.getStackIndicator(E,J,this.index),A=E.key,y=(m=w&&g<(p?0:b))?q:t,x[y]||(x[y]={}),x[y][J]||(C[y]&&C[y][J]?(x[y][J]=C[y][J],x[y][J].total=null):x[y][J]=new B(e,e.options.stackLabels,m,J,h)),y=x[y][J],null!==g&&(y.points[A]=y.points[this.index]=[k(y.cum,p)],r(y.cum)||(y.base=A),y.touched=e.stacksTouched,0<E.index&&!1===this.singleStacks&&(y.points[A][0]=y.points[this.index+
	","+J+",0"][0])),"percent"===l?(m=m?t:q,w&&x[m]&&x[m][J]?(m=x[m][J],y.total=m.total=Math.max(m.total,y.total)+Math.abs(g)||0):y.total=G(y.total+(Math.abs(g)||0))):y.total=G(y.total+(g||0)),y.cum=k(y.cum,p)+(g||0),null!==g&&(y.points[A].push(y.cum),c[K]=y.cum);"percent"===l&&(e.usePercentage=!0);this.stackedYData=c;e.oldStacks={}}};a.prototype.setPercentStacks=function(){var a=this,d=a.stackKey,c=a.yAxis.stacks,f=a.processedXData,k;l([d,"-"+d],function(b){for(var d=f.length,h,n;d--;)if(h=f[d],k=a.getStackIndicator(k,
	h,a.index,b),h=(n=c[b]&&c[b][h])&&n.points[k.key])n=n.total?100/n.total:0,h[0]=G(h[0]*n),h[1]=G(h[1]*n),a.stackedYData[d]=h[1]})};a.prototype.getStackIndicator=function(a,d,c,f){!r(a)||a.x!==d||f&&a.key!==f?a={x:d,index:0,key:f}:a.index++;a.key=[c,d,a.index].join();return a}})(L);(function(a){var B=a.addEvent,A=a.animate,H=a.Axis,G=a.createElement,r=a.css,f=a.defined,l=a.each,q=a.erase,k=a.extend,u=a.fireEvent,d=a.inArray,c=a.isNumber,n=a.isObject,z=a.merge,b=a.pick,p=a.Point,h=a.Series,t=a.seriesTypes,
	D=a.setAnimation,w=a.splat;k(a.Chart.prototype,{addSeries:function(a,c,d){var e,h=this;a&&(c=b(c,!0),u(h,"addSeries",{options:a},function(){e=h.initSeries(a);h.isDirtyLegend=!0;h.linkSeries();c&&h.redraw(d)}));return e},addAxis:function(a,c,d,h){var e=c?"xAxis":"yAxis",f=this.options;a=z(a,{index:this[e].length,isX:c});new H(this,a);f[e]=w(f[e]||{});f[e].push(a);b(d,!0)&&this.redraw(h)},showLoading:function(a){var b=this,c=b.options,e=b.loadingDiv,d=c.loading,h=function(){e&&r(e,{left:b.plotLeft+
	"px",top:b.plotTop+"px",width:b.plotWidth+"px",height:b.plotHeight+"px"})};e||(b.loadingDiv=e=G("div",{className:"highcharts-loading highcharts-loading-hidden"},null,b.container),b.loadingSpan=G("span",{className:"highcharts-loading-inner"},null,e),B(b,"redraw",h));e.className="highcharts-loading";b.loadingSpan.innerHTML=a||c.lang.loading;r(e,k(d.style,{zIndex:10}));r(b.loadingSpan,d.labelStyle);b.loadingShown||(r(e,{opacity:0,display:""}),A(e,{opacity:d.style.opacity||.5},{duration:d.showDuration||
	0}));b.loadingShown=!0;h()},hideLoading:function(){var a=this.options,b=this.loadingDiv;b&&(b.className="highcharts-loading highcharts-loading-hidden",A(b,{opacity:0},{duration:a.loading.hideDuration||100,complete:function(){r(b,{display:"none"})}}));this.loadingShown=!1},propsRequireDirtyBox:"backgroundColor borderColor borderWidth margin marginTop marginRight marginBottom marginLeft spacing spacingTop spacingRight spacingBottom spacingLeft borderRadius plotBackgroundColor plotBackgroundImage plotBorderColor plotBorderWidth plotShadow shadow".split(" "),
	propsRequireUpdateSeries:"chart.inverted chart.polar chart.ignoreHiddenSeries chart.type colors plotOptions".split(" "),update:function(a,h){var e,k={credits:"addCredits",title:"setTitle",subtitle:"setSubtitle"},m=a.chart,p,n;if(m){z(!0,this.options.chart,m);"className"in m&&this.setClassName(m.className);if("inverted"in m||"polar"in m)this.propFromSeries(),p=!0;"alignTicks"in m&&(p=!0);for(e in m)m.hasOwnProperty(e)&&(-1!==d("chart."+e,this.propsRequireUpdateSeries)&&(n=!0),-1!==d(e,this.propsRequireDirtyBox)&&
	(this.isDirtyBox=!0));"style"in m&&this.renderer.setStyle(m.style)}for(e in a){if(this[e]&&"function"===typeof this[e].update)this[e].update(a[e],!1);else if("function"===typeof this[k[e]])this[k[e]](a[e]);"chart"!==e&&-1!==d(e,this.propsRequireUpdateSeries)&&(n=!0)}a.colors&&(this.options.colors=a.colors);a.plotOptions&&z(!0,this.options.plotOptions,a.plotOptions);l(["xAxis","yAxis","series","colorAxis","pane"],function(b){a[b]&&l(w(a[b]),function(a,c){(c=f(a.id)&&this.get(a.id)||this[b][c])&&c.coll===
	b&&c.update(a,!1)},this)},this);p&&l(this.axes,function(a){a.update({},!1)});n&&l(this.series,function(a){a.update({},!1)});a.loading&&z(!0,this.options.loading,a.loading);e=m&&m.width;m=m&&m.height;c(e)&&e!==this.chartWidth||c(m)&&m!==this.chartHeight?this.setSize(e,m):b(h,!0)&&this.redraw()},setSubtitle:function(a){this.setTitle(void 0,a)}});k(p.prototype,{update:function(a,c,d,h){function e(){f.applyOptions(a);null===f.y&&p&&(f.graphic=p.destroy());n(a,!0)&&(p&&p.element&&a&&a.marker&&a.marker.symbol&&
	(f.graphic=p.destroy()),a&&a.dataLabels&&f.dataLabel&&(f.dataLabel=f.dataLabel.destroy()));l=f.index;k.updateParallelArrays(f,l);t.data[l]=n(t.data[l],!0)||n(a,!0)?f.options:a;k.isDirty=k.isDirtyData=!0;!k.fixedBox&&k.hasCartesianSeries&&(g.isDirtyBox=!0);"point"===t.legendType&&(g.isDirtyLegend=!0);c&&g.redraw(d)}var f=this,k=f.series,p=f.graphic,l,g=k.chart,t=k.options;c=b(c,!0);!1===h?e():f.firePointEvent("update",{options:a},e)},remove:function(a,b){this.series.removePoint(d(this,this.series.data),
	a,b)}});k(h.prototype,{addPoint:function(a,c,d,h){var e=this.options,f=this.data,k=this.chart,p=this.xAxis,p=p&&p.hasNames&&p.names,n=e.data,g,l,t=this.xData,q,w;c=b(c,!0);g={series:this};this.pointClass.prototype.applyOptions.apply(g,[a]);w=g.x;q=t.length;if(this.requireSorting&&w<t[q-1])for(l=!0;q&&t[q-1]>w;)q--;this.updateParallelArrays(g,"splice",q,0,0);this.updateParallelArrays(g,q);p&&g.name&&(p[w]=g.name);n.splice(q,0,a);l&&(this.data.splice(q,0,null),this.processData());"point"===e.legendType&&
	this.generatePoints();d&&(f[0]&&f[0].remove?f[0].remove(!1):(f.shift(),this.updateParallelArrays(g,"shift"),n.shift()));this.isDirtyData=this.isDirty=!0;c&&k.redraw(h)},removePoint:function(a,c,d){var e=this,h=e.data,f=h[a],k=e.points,p=e.chart,n=function(){k&&k.length===h.length&&k.splice(a,1);h.splice(a,1);e.options.data.splice(a,1);e.updateParallelArrays(f||{series:e},"splice",a,1);f&&f.destroy();e.isDirty=!0;e.isDirtyData=!0;c&&p.redraw()};D(d,p);c=b(c,!0);f?f.firePointEvent("remove",null,n):
	n()},remove:function(a,c,d){function e(){h.destroy();f.isDirtyLegend=f.isDirtyBox=!0;f.linkSeries();b(a,!0)&&f.redraw(c)}var h=this,f=h.chart;!1!==d?u(h,"remove",null,e):e()},update:function(a,c){var e=this,d=this.chart,h=this.userOptions,f=this.oldType||this.type,p=a.type||h.type||d.options.chart.type,n=t[f].prototype,q=["group","markerGroup","dataLabelsGroup"],g;if(p&&p!==f||void 0!==a.zIndex)q.length=0;l(q,function(a){q[a]=e[a];delete e[a]});a=z(h,{animation:!1,index:this.index,pointStart:this.xData[0]},
	{data:this.options.data},a);this.remove(!1,null,!1);for(g in n)this[g]=void 0;k(this,t[p||f].prototype);l(q,function(a){e[a]=q[a]});this.init(d,a);this.oldType=f;d.linkSeries();b(c,!0)&&d.redraw(!1)}});k(H.prototype,{update:function(a,c){var e=this.chart;a=e.options[this.coll][this.options.index]=z(this.userOptions,a);this.destroy(!0);this.init(e,k(a,{events:void 0}));e.isDirtyBox=!0;b(c,!0)&&e.redraw()},remove:function(a){for(var c=this.chart,e=this.coll,d=this.series,h=d.length;h--;)d[h]&&d[h].remove(!1);
	q(c.axes,this);q(c[e],this);c.options[e].splice(this.options.index,1);l(c[e],function(a,b){a.options.index=b});this.destroy();c.isDirtyBox=!0;b(a,!0)&&c.redraw()},setTitle:function(a,b){this.update({title:a},b)},setCategories:function(a,b){this.update({categories:a},b)}})})(L);(function(a){var B=a.color,A=a.each,H=a.map,G=a.pick,r=a.Series,f=a.seriesType;f("area","line",{softThreshold:!1,threshold:0},{singleStacks:!1,getStackPoints:function(){var a=[],f=[],k=this.xAxis,r=this.yAxis,d=r.stacks[this.stackKey],
	c={},n=this.points,z=this.index,b=r.series,p=b.length,h,t=G(r.options.reversedStacks,!0)?1:-1,D,w;if(this.options.stacking){for(D=0;D<n.length;D++)c[n[D].x]=n[D];for(w in d)null!==d[w].total&&f.push(w);f.sort(function(a,b){return a-b});h=H(b,function(){return this.visible});A(f,function(b,n){var e=0,l,m;if(c[b]&&!c[b].isNull)a.push(c[b]),A([-1,1],function(a){var e=1===a?"rightNull":"leftNull",k=0,q=d[f[n+a]];if(q)for(D=z;0<=D&&D<p;)l=q.points[D],l||(D===z?c[b][e]=!0:h[D]&&(m=d[b].points[D])&&(k-=
	m[1]-m[0])),D+=t;c[b][1===a?"rightCliff":"leftCliff"]=k});else{for(D=z;0<=D&&D<p;){if(l=d[b].points[D]){e=l[1];break}D+=t}e=r.translate(e,0,1,0,1);a.push({isNull:!0,plotX:k.translate(b,0,0,0,1),x:b,plotY:e,yBottom:e})}})}return a},getGraphPath:function(a){var f=r.prototype.getGraphPath,k=this.options,l=k.stacking,d=this.yAxis,c,n,z=[],b=[],p=this.index,h,t=d.stacks[this.stackKey],D=k.threshold,w=d.getThreshold(k.threshold),e,k=k.connectNulls||"percent"===l,x=function(c,e,f){var k=a[c];c=l&&t[k.x].points[p];
	var m=k[f+"Null"]||0;f=k[f+"Cliff"]||0;var n,q,k=!0;f||m?(n=(m?c[0]:c[1])+f,q=c[0]+f,k=!!m):!l&&a[e]&&a[e].isNull&&(n=q=D);void 0!==n&&(b.push({plotX:h,plotY:null===n?w:d.getThreshold(n),isNull:k,isCliff:!0}),z.push({plotX:h,plotY:null===q?w:d.getThreshold(q),doCurve:!1}))};a=a||this.points;l&&(a=this.getStackPoints());for(c=0;c<a.length;c++)if(n=a[c].isNull,h=G(a[c].rectPlotX,a[c].plotX),e=G(a[c].yBottom,w),!n||k)k||x(c,c-1,"left"),n&&!l&&k||(b.push(a[c]),z.push({x:c,plotX:h,plotY:e})),k||x(c,c+
	1,"right");c=f.call(this,b,!0,!0);z.reversed=!0;n=f.call(this,z,!0,!0);n.length&&(n[0]="L");n=c.concat(n);f=f.call(this,b,!1,k);n.xMap=c.xMap;this.areaPath=n;return f},drawGraph:function(){this.areaPath=[];r.prototype.drawGraph.apply(this);var a=this,f=this.areaPath,k=this.options,u=[["area","highcharts-area",this.color,k.fillColor]];A(this.zones,function(d,c){u.push(["zone-area-"+c,"highcharts-area highcharts-zone-area-"+c+" "+d.className,d.color||a.color,d.fillColor||k.fillColor])});A(u,function(d){var c=
	d[0],n=a[c];n?(n.endX=f.xMap,n.animate({d:f})):(n=a[c]=a.chart.renderer.path(f).addClass(d[1]).attr({fill:G(d[3],B(d[2]).setOpacity(G(k.fillOpacity,.75)).get()),zIndex:0}).add(a.group),n.isArea=!0);n.startX=f.xMap;n.shiftUnit=k.step?2:1})},drawLegendSymbol:a.LegendSymbolMixin.drawRectangle})})(L);(function(a){var B=a.pick;a=a.seriesType;a("spline","line",{},{getPointSpline:function(a,H,G){var r=H.plotX,f=H.plotY,l=a[G-1];G=a[G+1];var q,k,u,d;if(l&&!l.isNull&&!1!==l.doCurve&&!H.isCliff&&G&&!G.isNull&&
	!1!==G.doCurve&&!H.isCliff){a=l.plotY;u=G.plotX;G=G.plotY;var c=0;q=(1.5*r+l.plotX)/2.5;k=(1.5*f+a)/2.5;u=(1.5*r+u)/2.5;d=(1.5*f+G)/2.5;u!==q&&(c=(d-k)*(u-r)/(u-q)+f-d);k+=c;d+=c;k>a&&k>f?(k=Math.max(a,f),d=2*f-k):k<a&&k<f&&(k=Math.min(a,f),d=2*f-k);d>G&&d>f?(d=Math.max(G,f),k=2*f-d):d<G&&d<f&&(d=Math.min(G,f),k=2*f-d);H.rightContX=u;H.rightContY=d}H=["C",B(l.rightContX,l.plotX),B(l.rightContY,l.plotY),B(q,r),B(k,f),r,f];l.rightContX=l.rightContY=null;return H}})})(L);(function(a){var B=a.seriesTypes.area.prototype,
	A=a.seriesType;A("areaspline","spline",a.defaultPlotOptions.area,{getStackPoints:B.getStackPoints,getGraphPath:B.getGraphPath,setStackCliffs:B.setStackCliffs,drawGraph:B.drawGraph,drawLegendSymbol:a.LegendSymbolMixin.drawRectangle})})(L);(function(a){var B=a.animObject,A=a.color,H=a.each,G=a.extend,r=a.isNumber,f=a.merge,l=a.pick,q=a.Series,k=a.seriesType,u=a.svg;k("column","line",{borderRadius:0,crisp:!0,groupPadding:.2,marker:null,pointPadding:.1,minPointLength:0,cropThreshold:50,pointRange:null,
	states:{hover:{halo:!1,brightness:.1,shadow:!1},select:{color:"#cccccc",borderColor:"#000000",shadow:!1}},dataLabels:{align:null,verticalAlign:null,y:null},softThreshold:!1,startFromThreshold:!0,stickyTracking:!1,tooltip:{distance:6},threshold:0,borderColor:"#ffffff"},{cropShoulder:0,directTouch:!0,trackerGroups:["group","dataLabelsGroup"],negStacks:!0,init:function(){q.prototype.init.apply(this,arguments);var a=this,c=a.chart;c.hasRendered&&H(c.series,function(c){c.type===a.type&&(c.isDirty=!0)})},
	getColumnMetrics:function(){var a=this,c=a.options,f=a.xAxis,k=a.yAxis,b=f.reversed,p,h={},t=0;!1===c.grouping?t=1:H(a.chart.series,function(b){var c=b.options,e=b.yAxis,d;b.type===a.type&&b.visible&&k.len===e.len&&k.pos===e.pos&&(c.stacking?(p=b.stackKey,void 0===h[p]&&(h[p]=t++),d=h[p]):!1!==c.grouping&&(d=t++),b.columnIndex=d)});var q=Math.min(Math.abs(f.transA)*(f.ordinalSlope||c.pointRange||f.closestPointRange||f.tickInterval||1),f.len),w=q*c.groupPadding,e=(q-2*w)/(t||1),c=Math.min(c.maxPointWidth||
	f.len,l(c.pointWidth,e*(1-2*c.pointPadding)));a.columnMetrics={width:c,offset:(e-c)/2+(w+((a.columnIndex||0)+(b?1:0))*e-q/2)*(b?-1:1)};return a.columnMetrics},crispCol:function(a,c,f,k){var b=this.chart,d=this.borderWidth,h=-(d%2?.5:0),d=d%2?.5:1;b.inverted&&b.renderer.isVML&&(d+=1);this.options.crisp&&(f=Math.round(a+f)+h,a=Math.round(a)+h,f-=a);k=Math.round(c+k)+d;h=.5>=Math.abs(c)&&.5<k;c=Math.round(c)+d;k-=c;h&&k&&(--c,k+=1);return{x:a,y:c,width:f,height:k}},translate:function(){var a=this,c=
	a.chart,f=a.options,k=a.dense=2>a.closestPointRange*a.xAxis.transA,k=a.borderWidth=l(f.borderWidth,k?0:1),b=a.yAxis,p=a.translatedThreshold=b.getThreshold(f.threshold),h=l(f.minPointLength,5),t=a.getColumnMetrics(),r=t.width,w=a.barW=Math.max(r,1+2*k),e=a.pointXOffset=t.offset;c.inverted&&(p-=.5);f.pointPadding&&(w=Math.ceil(w));q.prototype.translate.apply(a);H(a.points,function(d){var f=l(d.yBottom,p),k=999+Math.abs(f),k=Math.min(Math.max(-k,d.plotY),b.len+k),m=d.plotX+e,n=w,t=Math.min(k,f),q,u=
	Math.max(k,f)-t;Math.abs(u)<h&&h&&(u=h,q=!b.reversed&&!d.negative||b.reversed&&d.negative,t=Math.abs(t-p)>h?f-h:p-(q?h:0));d.barX=m;d.pointWidth=r;d.tooltipPos=c.inverted?[b.len+b.pos-c.plotLeft-k,a.xAxis.len-m-n/2,u]:[m+n/2,k+b.pos-c.plotTop,u];d.shapeType="rect";d.shapeArgs=a.crispCol.apply(a,d.isNull?[d.plotX,b.len/2,0,0]:[m,t,n,u])})},getSymbol:a.noop,drawLegendSymbol:a.LegendSymbolMixin.drawRectangle,drawGraph:function(){this.group[this.dense?"addClass":"removeClass"]("highcharts-dense-data")},
	pointAttribs:function(a,c){var d=this.options,k,b=this.pointAttrToOptions||{};k=b.stroke||"borderColor";var p=b["stroke-width"]||"borderWidth",h=a&&a.color||this.color,l=a[k]||d[k]||this.color||h,q=a[p]||d[p]||this[p]||0,b=d.dashStyle;a&&this.zones.length&&(h=(h=a.getZone())&&h.color||a.options.color||this.color);c&&(a=f(d.states[c],a.options.states&&a.options.states[c]||{}),c=a.brightness,h=a.color||void 0!==c&&A(h).brighten(a.brightness).get()||h,l=a[k]||l,q=a[p]||q,b=a.dashStyle||b);k={fill:h,
	stroke:l,"stroke-width":q};d.borderRadius&&(k.r=d.borderRadius);b&&(k.dashstyle=b);return k},drawPoints:function(){var a=this,c=this.chart,k=a.options,l=c.renderer,b=k.animationLimit||250,p;H(a.points,function(d){var h=d.graphic;if(r(d.plotY)&&null!==d.y){p=d.shapeArgs;if(h)h[c.pointCount<b?"animate":"attr"](f(p));else d.graphic=h=l[d.shapeType](p).add(d.group||a.group);h.attr(a.pointAttribs(d,d.selected&&"select")).shadow(k.shadow,null,k.stacking&&!k.borderRadius);h.addClass(d.getClassName(),!0)}else h&&
	(d.graphic=h.destroy())})},animate:function(a){var c=this,d=this.yAxis,f=c.options,b=this.chart.inverted,k={};u&&(a?(k.scaleY=.001,a=Math.min(d.pos+d.len,Math.max(d.pos,d.toPixels(f.threshold))),b?k.translateX=a-d.len:k.translateY=a,c.group.attr(k)):(k[b?"translateX":"translateY"]=d.pos,c.group.animate(k,G(B(c.options.animation),{step:function(a,b){c.group.attr({scaleY:Math.max(.001,b.pos)})}})),c.animate=null))},remove:function(){var a=this,c=a.chart;c.hasRendered&&H(c.series,function(c){c.type===
	a.type&&(c.isDirty=!0)});q.prototype.remove.apply(a,arguments)}})})(L);(function(a){a=a.seriesType;a("bar","column",null,{inverted:!0})})(L);(function(a){var B=a.Series;a=a.seriesType;a("scatter","line",{lineWidth:0,findNearestPointBy:"xy",marker:{enabled:!0},tooltip:{headerFormat:'\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e \x3cspan style\x3d"font-size: 0.85em"\x3e {series.name}\x3c/span\x3e\x3cbr/\x3e',pointFormat:"x: \x3cb\x3e{point.x}\x3c/b\x3e\x3cbr/\x3ey: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e"}},
	{sorted:!1,requireSorting:!1,noSharedTooltip:!0,trackerGroups:["group","markerGroup","dataLabelsGroup"],takeOrdinalPosition:!1,drawGraph:function(){this.options.lineWidth&&B.prototype.drawGraph.call(this)}})})(L);(function(a){var B=a.pick,A=a.relativeLength;a.CenteredSeriesMixin={getCenter:function(){var a=this.options,G=this.chart,r=2*(a.slicedOffset||0),f=G.plotWidth-2*r,G=G.plotHeight-2*r,l=a.center,l=[B(l[0],"50%"),B(l[1],"50%"),a.size||"100%",a.innerSize||0],q=Math.min(f,G),k,u;for(k=0;4>k;++k)u=
	l[k],a=2>k||2===k&&/%$/.test(u),l[k]=A(u,[f,G,q,l[2]][k])+(a?r:0);l[3]>l[2]&&(l[3]=l[2]);return l}}})(L);(function(a){var B=a.addEvent,A=a.defined,H=a.each,G=a.extend,r=a.inArray,f=a.noop,l=a.pick,q=a.Point,k=a.Series,u=a.seriesType,d=a.setAnimation;u("pie","line",{center:[null,null],clip:!1,colorByPoint:!0,dataLabels:{distance:30,enabled:!0,formatter:function(){return null===this.y?void 0:this.point.name},x:0},ignoreHiddenPoint:!0,legendType:"point",marker:null,size:null,showInLegend:!1,slicedOffset:10,
	stickyTracking:!1,tooltip:{followPointer:!0},borderColor:"#ffffff",borderWidth:1,states:{hover:{brightness:.1,shadow:!1}}},{isCartesian:!1,requireSorting:!1,directTouch:!0,noSharedTooltip:!0,trackerGroups:["group","dataLabelsGroup"],axisTypes:[],pointAttribs:a.seriesTypes.column.prototype.pointAttribs,animate:function(a){var c=this,d=c.points,b=c.startAngleRad;a||(H(d,function(a){var d=a.graphic,f=a.shapeArgs;d&&(d.attr({r:a.startR||c.center[3]/2,start:b,end:b}),d.animate({r:f.r,start:f.start,end:f.end},
	c.options.animation))}),c.animate=null)},updateTotals:function(){var a,d=0,f=this.points,b=f.length,k,h=this.options.ignoreHiddenPoint;for(a=0;a<b;a++)k=f[a],0>k.y&&(k.y=null),d+=h&&!k.visible?0:k.y;this.total=d;for(a=0;a<b;a++)k=f[a],k.percentage=0<d&&(k.visible||!h)?k.y/d*100:0,k.total=d},generatePoints:function(){k.prototype.generatePoints.call(this);this.updateTotals()},translate:function(a){this.generatePoints();var c=0,d=this.options,b=d.slicedOffset,f=b+(d.borderWidth||0),h,k,q,w=d.startAngle||
	0,e=this.startAngleRad=Math.PI/180*(w-90),w=(this.endAngleRad=Math.PI/180*(l(d.endAngle,w+360)-90))-e,r=this.points,u=d.dataLabels.distance,d=d.ignoreHiddenPoint,E,m=r.length,y;a||(this.center=a=this.getCenter());this.getX=function(b,c){q=Math.asin(Math.min((b-a[1])/(a[2]/2+u),1));return a[0]+(c?-1:1)*Math.cos(q)*(a[2]/2+u)};for(E=0;E<m;E++){y=r[E];h=e+c*w;if(!d||y.visible)c+=y.percentage/100;k=e+c*w;y.shapeType="arc";y.shapeArgs={x:a[0],y:a[1],r:a[2]/2,innerR:a[3]/2,start:Math.round(1E3*h)/1E3,end:Math.round(1E3*
	k)/1E3};q=(k+h)/2;q>1.5*Math.PI?q-=2*Math.PI:q<-Math.PI/2&&(q+=2*Math.PI);y.slicedTranslation={translateX:Math.round(Math.cos(q)*b),translateY:Math.round(Math.sin(q)*b)};h=Math.cos(q)*a[2]/2;k=Math.sin(q)*a[2]/2;y.tooltipPos=[a[0]+.7*h,a[1]+.7*k];y.half=q<-Math.PI/2||q>Math.PI/2?1:0;y.angle=q;f=Math.min(f,u/5);y.labelPos=[a[0]+h+Math.cos(q)*u,a[1]+k+Math.sin(q)*u,a[0]+h+Math.cos(q)*f,a[1]+k+Math.sin(q)*f,a[0]+h,a[1]+k,0>u?"center":y.half?"right":"left",q]}},drawGraph:null,drawPoints:function(){var a=
	this,d=a.chart.renderer,f,b,k,h,l=a.options.shadow;l&&!a.shadowGroup&&(a.shadowGroup=d.g("shadow").add(a.group));H(a.points,function(c){if(null!==c.y){b=c.graphic;h=c.shapeArgs;f=c.getTranslate();var p=c.shadowGroup;l&&!p&&(p=c.shadowGroup=d.g("shadow").add(a.shadowGroup));p&&p.attr(f);k=a.pointAttribs(c,c.selected&&"select");b?b.setRadialReference(a.center).attr(k).animate(G(h,f)):(c.graphic=b=d[c.shapeType](h).setRadialReference(a.center).attr(f).add(a.group),c.visible||b.attr({visibility:"hidden"}),
	b.attr(k).attr({"stroke-linejoin":"round"}).shadow(l,p));b.addClass(c.getClassName())}})},searchPoint:f,sortByAngle:function(a,d){a.sort(function(a,b){return void 0!==a.angle&&(b.angle-a.angle)*d})},drawLegendSymbol:a.LegendSymbolMixin.drawRectangle,getCenter:a.CenteredSeriesMixin.getCenter,getSymbol:f},{init:function(){q.prototype.init.apply(this,arguments);var a=this,d;a.name=l(a.name,"Slice");d=function(c){a.slice("select"===c.type)};B(a,"select",d);B(a,"unselect",d);return a},setVisible:function(a,
	d){var c=this,b=c.series,f=b.chart,h=b.options.ignoreHiddenPoint;d=l(d,h);a!==c.visible&&(c.visible=c.options.visible=a=void 0===a?!c.visible:a,b.options.data[r(c,b.data)]=c.options,H(["graphic","dataLabel","connector","shadowGroup"],function(b){if(c[b])c[b][a?"show":"hide"](!0)}),c.legendItem&&f.legend.colorizeItem(c,a),a||"hover"!==c.state||c.setState(""),h&&(b.isDirty=!0),d&&f.redraw())},slice:function(a,f,k){var b=this.series;d(k,b.chart);l(f,!0);this.sliced=this.options.sliced=A(a)?a:!this.sliced;
	b.options.data[r(this,b.data)]=this.options;this.graphic.animate(this.getTranslate());this.shadowGroup&&this.shadowGroup.animate(this.getTranslate())},getTranslate:function(){return this.sliced?this.slicedTranslation:{translateX:0,translateY:0}},haloPath:function(a){var c=this.shapeArgs;return this.sliced||!this.visible?[]:this.series.chart.renderer.symbols.arc(c.x,c.y,c.r+a,c.r+a,{innerR:this.shapeArgs.r,start:c.start,end:c.end})}})})(L);(function(a){var B=a.addEvent,A=a.arrayMax,H=a.defined,G=a.each,
	r=a.extend,f=a.format,l=a.map,q=a.merge,k=a.noop,u=a.pick,d=a.relativeLength,c=a.Series,n=a.seriesTypes,z=a.stableSort;a.distribute=function(a,c){function b(a,b){return a.target-b.target}var d,f=!0,k=a,e=[],p;p=0;for(d=a.length;d--;)p+=a[d].size;if(p>c){z(a,function(a,b){return(b.rank||0)-(a.rank||0)});for(p=d=0;p<=c;)p+=a[d].size,d++;e=a.splice(d-1,a.length)}z(a,b);for(a=l(a,function(a){return{size:a.size,targets:[a.target]}});f;){for(d=a.length;d--;)f=a[d],p=(Math.min.apply(0,f.targets)+Math.max.apply(0,
	f.targets))/2,f.pos=Math.min(Math.max(0,p-f.size/2),c-f.size);d=a.length;for(f=!1;d--;)0<d&&a[d-1].pos+a[d-1].size>a[d].pos&&(a[d-1].size+=a[d].size,a[d-1].targets=a[d-1].targets.concat(a[d].targets),a[d-1].pos+a[d-1].size>c&&(a[d-1].pos=c-a[d-1].size),a.splice(d,1),f=!0)}d=0;G(a,function(a){var b=0;G(a.targets,function(){k[d].pos=a.pos+b;b+=k[d].size;d++})});k.push.apply(k,e);z(k,b)};c.prototype.drawDataLabels=function(){var a=this,c=a.options,d=c.dataLabels,k=a.points,l,n,e=a.hasRendered||0,r,C,
	E=u(d.defer,!0),m=a.chart.renderer;if(d.enabled||a._hasPointLabels)a.dlProcessOptions&&a.dlProcessOptions(d),C=a.plotGroup("dataLabelsGroup","data-labels",E&&!e?"hidden":"visible",d.zIndex||6),E&&(C.attr({opacity:+e}),e||B(a,"afterAnimate",function(){a.visible&&C.show(!0);C[c.animation?"animate":"attr"]({opacity:1},{duration:200})})),n=d,G(k,function(b){var e,h=b.dataLabel,k,g,p,t=b.connector,w=!h,x;l=b.dlOptions||b.options&&b.options.dataLabels;if(e=u(l&&l.enabled,n.enabled)&&null!==b.y)for(g in d=
	q(n,l),k=b.getLabelConfig(),r=d.format?f(d.format,k):d.formatter.call(k,d),x=d.style,p=d.rotation,x.color=u(d.color,x.color,a.color,"#000000"),"contrast"===x.color&&(b.contrastColor=m.getContrast(b.color||a.color),x.color=d.inside||0>d.distance||c.stacking?b.contrastColor:"#000000"),c.cursor&&(x.cursor=c.cursor),k={fill:d.backgroundColor,stroke:d.borderColor,"stroke-width":d.borderWidth,r:d.borderRadius||0,rotation:p,padding:d.padding,zIndex:1},k)void 0===k[g]&&delete k[g];!h||e&&H(r)?e&&H(r)&&(h?
	k.text=r:(h=b.dataLabel=m[p?"text":"label"](r,0,-9999,d.shape,null,null,d.useHTML,null,"data-label"),h.addClass("highcharts-data-label-color-"+b.colorIndex+" "+(d.className||"")+(d.useHTML?"highcharts-tracker":""))),h.attr(k),h.css(x).shadow(d.shadow),h.added||h.add(C),a.alignDataLabel(b,h,d,null,w)):(b.dataLabel=h.destroy(),t&&(b.connector=t.destroy()))})};c.prototype.alignDataLabel=function(a,c,d,f,k){var b=this.chart,e=b.inverted,h=u(a.plotX,-9999),l=u(a.plotY,-9999),p=c.getBBox(),m,n=d.rotation,
	q=d.align,t=this.visible&&(a.series.forceDL||b.isInsidePlot(h,Math.round(l),e)||f&&b.isInsidePlot(h,e?f.x+1:f.y+f.height-1,e)),D="justify"===u(d.overflow,"justify");t&&(m=d.style.fontSize,m=b.renderer.fontMetrics(m,c).b,f=r({x:e?b.plotWidth-l:h,y:Math.round(e?b.plotHeight-h:l),width:0,height:0},f),r(d,{width:p.width,height:p.height}),n?(D=!1,e=b.renderer.rotCorr(m,n),e={x:f.x+d.x+f.width/2+e.x,y:f.y+d.y+{top:0,middle:.5,bottom:1}[d.verticalAlign]*f.height},c[k?"attr":"animate"](e).attr({align:q}),
	h=(n+720)%360,h=180<h&&360>h,"left"===q?e.y-=h?p.height:0:"center"===q?(e.x-=p.width/2,e.y-=p.height/2):"right"===q&&(e.x-=p.width,e.y-=h?0:p.height)):(c.align(d,null,f),e=c.alignAttr),D?a.isLabelJustified=this.justifyDataLabel(c,d,e,p,f,k):u(d.crop,!0)&&(t=b.isInsidePlot(e.x,e.y)&&b.isInsidePlot(e.x+p.width,e.y+p.height)),d.shape&&!n&&c.attr({anchorX:a.plotX,anchorY:a.plotY}));t||(c.attr({y:-9999}),c.placed=!1)};c.prototype.justifyDataLabel=function(a,c,d,f,k,l){var b=this.chart,h=c.align,p=c.verticalAlign,
	n,m,q=a.box?0:a.padding||0;n=d.x+q;0>n&&("right"===h?c.align="left":c.x=-n,m=!0);n=d.x+f.width-q;n>b.plotWidth&&("left"===h?c.align="right":c.x=b.plotWidth-n,m=!0);n=d.y+q;0>n&&("bottom"===p?c.verticalAlign="top":c.y=-n,m=!0);n=d.y+f.height-q;n>b.plotHeight&&("top"===p?c.verticalAlign="bottom":c.y=b.plotHeight-n,m=!0);m&&(a.placed=!l,a.align(c,null,k));return m};n.pie&&(n.pie.prototype.drawDataLabels=function(){var b=this,d=b.data,f,k=b.chart,n=b.options.dataLabels,q=u(n.connectorPadding,10),e=u(n.connectorWidth,
	1),r=k.plotWidth,C=k.plotHeight,E,m=n.distance,y=b.center,z=y[2]/2,B=y[1],H=0<m,g,F,L,N,P=[[],[]],O,v,M,R,S=[0,0,0,0];b.visible&&(n.enabled||b._hasPointLabels)&&(G(d,function(a){a.dataLabel&&a.visible&&a.dataLabel.shortened&&(a.dataLabel.attr({width:"auto"}).css({width:"auto",textOverflow:"clip"}),a.dataLabel.shortened=!1)}),c.prototype.drawDataLabels.apply(b),G(d,function(a){a.dataLabel&&a.visible&&(P[a.half].push(a),a.dataLabel._pos=null)}),G(P,function(c,e){var d,h,p=c.length,t,w,u;if(p)for(b.sortByAngle(c,
	e-.5),0<m&&(d=Math.max(0,B-z-m),h=Math.min(B+z+m,k.plotHeight),t=l(c,function(a){if(a.dataLabel)return u=a.dataLabel.getBBox().height||21,{target:a.labelPos[1]-d+u/2,size:u,rank:a.y}}),a.distribute(t,h+u-d)),R=0;R<p;R++)f=c[R],L=f.labelPos,g=f.dataLabel,M=!1===f.visible?"hidden":"inherit",w=L[1],t?void 0===t[R].pos?M="hidden":(N=t[R].size,v=d+t[R].pos):v=w,O=n.justify?y[0]+(e?-1:1)*(z+m):b.getX(v<d+2||v>h-2?w:v,e),g._attr={visibility:M,align:L[6]},g._pos={x:O+n.x+({left:q,right:-q}[L[6]]||0),y:v+
	n.y-10},L.x=O,L.y=v,null===b.options.size&&(F=g.getBBox().width,w=null,O-F<q?(w=Math.round(F-O+q),S[3]=Math.max(w,S[3])):O+F>r-q&&(w=Math.round(O+F-r+q),S[1]=Math.max(w,S[1])),0>v-N/2?S[0]=Math.max(Math.round(-v+N/2),S[0]):v+N/2>C&&(S[2]=Math.max(Math.round(v+N/2-C),S[2])),g.sideOverflow=w)}),0===A(S)||this.verifyDataLabelOverflow(S))&&(this.placeDataLabels(),H&&e&&G(this.points,function(a){var c;E=a.connector;if((g=a.dataLabel)&&g._pos&&a.visible){M=g._attr.visibility;if(c=!E)a.connector=E=k.renderer.path().addClass("highcharts-data-label-connector highcharts-color-"+
	a.colorIndex).add(b.dataLabelsGroup),E.attr({"stroke-width":e,stroke:n.connectorColor||a.color||"#666666"});E[c?"attr":"animate"]({d:b.connectorPath(a.labelPos)});E.attr("visibility",M)}else E&&(a.connector=E.destroy())}))},n.pie.prototype.connectorPath=function(a){var b=a.x,c=a.y;return u(this.options.dataLabels.softConnector,!0)?["M",b+("left"===a[6]?5:-5),c,"C",b,c,2*a[2]-a[4],2*a[3]-a[5],a[2],a[3],"L",a[4],a[5]]:["M",b+("left"===a[6]?5:-5),c,"L",a[2],a[3],"L",a[4],a[5]]},n.pie.prototype.placeDataLabels=
	function(){G(this.points,function(a){var b=a.dataLabel;b&&a.visible&&((a=b._pos)?(b.sideOverflow&&(b._attr.width=b.getBBox().width-b.sideOverflow,b.css({width:b._attr.width+"px",textOverflow:"ellipsis"}),b.shortened=!0),b.attr(b._attr),b[b.moved?"animate":"attr"](a),b.moved=!0):b&&b.attr({y:-9999}))},this)},n.pie.prototype.alignDataLabel=k,n.pie.prototype.verifyDataLabelOverflow=function(a){var b=this.center,c=this.options,f=c.center,k=c.minSize||80,l,e;null!==f[0]?l=Math.max(b[2]-Math.max(a[1],a[3]),
	k):(l=Math.max(b[2]-a[1]-a[3],k),b[0]+=(a[3]-a[1])/2);null!==f[1]?l=Math.max(Math.min(l,b[2]-Math.max(a[0],a[2])),k):(l=Math.max(Math.min(l,b[2]-a[0]-a[2]),k),b[1]+=(a[0]-a[2])/2);l<b[2]?(b[2]=l,b[3]=Math.min(d(c.innerSize||0,l),l),this.translate(b),this.drawDataLabels&&this.drawDataLabels()):e=!0;return e});n.column&&(n.column.prototype.alignDataLabel=function(a,d,f,k,l){var b=this.chart.inverted,e=a.series,h=a.dlBox||a.shapeArgs,n=u(a.below,a.plotY>u(this.translatedThreshold,e.yAxis.len)),p=u(f.inside,
	!!this.options.stacking);h&&(k=q(h),0>k.y&&(k.height+=k.y,k.y=0),h=k.y+k.height-e.yAxis.len,0<h&&(k.height-=h),b&&(k={x:e.yAxis.len-k.y-k.height,y:e.xAxis.len-k.x-k.width,width:k.height,height:k.width}),p||(b?(k.x+=n?0:k.width,k.width=0):(k.y+=n?k.height:0,k.height=0)));f.align=u(f.align,!b||p?"center":n?"right":"left");f.verticalAlign=u(f.verticalAlign,b||p?"middle":n?"top":"bottom");c.prototype.alignDataLabel.call(this,a,d,f,k,l);a.isLabelJustified&&a.contrastColor&&a.dataLabel.css({color:a.contrastColor})})})(L);
	(function(a){var B=a.Chart,A=a.each,H=a.pick,G=a.addEvent;B.prototype.callbacks.push(function(a){function f(){var f=[];A(a.series||[],function(a){var k=a.options.dataLabels,l=a.dataLabelCollections||["dataLabel"];(k.enabled||a._hasPointLabels)&&!k.allowOverlap&&a.visible&&A(l,function(d){A(a.points,function(a){a[d]&&(a[d].labelrank=H(a.labelrank,a.shapeArgs&&a.shapeArgs.height),f.push(a[d]))})})});a.hideOverlappingLabels(f)}f();G(a,"redraw",f)});B.prototype.hideOverlappingLabels=function(a){var f=
	a.length,l,q,k,r,d,c,n,z,b,p=function(a,b,c,d,e,f,k,l){return!(e>a+c||e+k<a||f>b+d||f+l<b)};for(q=0;q<f;q++)if(l=a[q])l.oldOpacity=l.opacity,l.newOpacity=1;a.sort(function(a,b){return(b.labelrank||0)-(a.labelrank||0)});for(q=0;q<f;q++)for(k=a[q],l=q+1;l<f;++l)if(r=a[l],k&&r&&k!==r&&k.placed&&r.placed&&0!==k.newOpacity&&0!==r.newOpacity&&(d=k.alignAttr,c=r.alignAttr,n=k.parentGroup,z=r.parentGroup,b=2*(k.box?0:k.padding),d=p(d.x+n.translateX,d.y+n.translateY,k.width-b,k.height-b,c.x+z.translateX,c.y+
	z.translateY,r.width-b,r.height-b)))(k.labelrank<r.labelrank?k:r).newOpacity=0;A(a,function(a){var b,c;a&&(c=a.newOpacity,a.oldOpacity!==c&&a.placed&&(c?a.show(!0):b=function(){a.hide()},a.alignAttr.opacity=c,a[a.isOld?"animate":"attr"](a.alignAttr,null,b)),a.isOld=!0)})}})(L);(function(a){var B=a.addEvent,A=a.Chart,H=a.createElement,G=a.css,r=a.defaultOptions,f=a.defaultPlotOptions,l=a.each,q=a.extend,k=a.fireEvent,u=a.hasTouch,d=a.inArray,c=a.isObject,n=a.Legend,z=a.merge,b=a.pick,p=a.Point,h=a.Series,
	t=a.seriesTypes,D=a.svg;a=a.TrackerMixin={drawTrackerPoint:function(){var a=this,b=a.chart.pointer,c=function(a){var c=b.getPointFromEvent(a);if(void 0!==c)c.onMouseOver(a)};l(a.points,function(a){a.graphic&&(a.graphic.element.point=a);a.dataLabel&&(a.dataLabel.div?a.dataLabel.div.point=a:a.dataLabel.element.point=a)});a._hasTracking||(l(a.trackerGroups,function(e){if(a[e]){a[e].addClass("highcharts-tracker").on("mouseover",c).on("mouseout",function(a){b.onTrackerMouseOut(a)});if(u)a[e].on("touchstart",
	c);a.options.cursor&&a[e].css(G).css({cursor:a.options.cursor})}}),a._hasTracking=!0)},drawTrackerGraph:function(){var a=this,b=a.options,c=b.trackByArea,d=[].concat(c?a.areaPath:a.graphPath),f=d.length,h=a.chart,k=h.pointer,n=h.renderer,p=h.options.tooltip.snap,q=a.tracker,g,r=function(){if(h.hoverSeries!==a)a.onMouseOver()},t="rgba(192,192,192,"+(D?.0001:.002)+")";if(f&&!c)for(g=f+1;g--;)"M"===d[g]&&d.splice(g+1,0,d[g+1]-p,d[g+2],"L"),(g&&"M"===d[g]||g===f)&&d.splice(g,0,"L",d[g-2]+p,d[g-1]);q?
	q.attr({d:d}):a.graph&&(a.tracker=n.path(d).attr({"stroke-linejoin":"round",visibility:a.visible?"visible":"hidden",stroke:t,fill:c?t:"none","stroke-width":a.graph.strokeWidth()+(c?0:2*p),zIndex:2}).add(a.group),l([a.tracker,a.markerGroup],function(a){a.addClass("highcharts-tracker").on("mouseover",r).on("mouseout",function(a){k.onTrackerMouseOut(a)});b.cursor&&a.css({cursor:b.cursor});if(u)a.on("touchstart",r)}))}};t.column&&(t.column.prototype.drawTracker=a.drawTrackerPoint);t.pie&&(t.pie.prototype.drawTracker=
	a.drawTrackerPoint);t.scatter&&(t.scatter.prototype.drawTracker=a.drawTrackerPoint);q(n.prototype,{setItemEvents:function(a,b,c){var d=this,e=d.chart.renderer.boxWrapper,f="highcharts-legend-"+(a.series?"point":"series")+"-active";(c?b:a.legendGroup).on("mouseover",function(){a.setState("hover");e.addClass(f);b.css(d.options.itemHoverStyle)}).on("mouseout",function(){b.css(a.visible?d.itemStyle:d.itemHiddenStyle);e.removeClass(f);a.setState()}).on("click",function(b){var c=function(){a.setVisible&&
	a.setVisible()};b={browserEvent:b};a.firePointEvent?a.firePointEvent("legendItemClick",b,c):k(a,"legendItemClick",b,c)})},createCheckboxForItem:function(a){a.checkbox=H("input",{type:"checkbox",checked:a.selected,defaultChecked:a.selected},this.options.itemCheckboxStyle,this.chart.container);B(a.checkbox,"click",function(b){k(a.series||a,"checkboxClick",{checked:b.target.checked,item:a},function(){a.select()})})}});r.legend.itemStyle.cursor="pointer";q(A.prototype,{showResetZoom:function(){var a=
	this,b=r.lang,c=a.options.chart.resetZoomButton,d=c.theme,f=d.states,h="chart"===c.relativeTo?null:"plotBox";this.resetZoomButton=a.renderer.button(b.resetZoom,null,null,function(){a.zoomOut()},d,f&&f.hover).attr({align:c.position.align,title:b.resetZoomTitle}).addClass("highcharts-reset-zoom").add().align(c.position,!1,h)},zoomOut:function(){var a=this;k(a,"selection",{resetSelection:!0},function(){a.zoom()})},zoom:function(a){var d,f=this.pointer,h=!1,k;!a||a.resetSelection?l(this.axes,function(a){d=
	a.zoom()}):l(a.xAxis.concat(a.yAxis),function(a){var b=a.axis;f[b.isXAxis?"zoomX":"zoomY"]&&(d=b.zoom(a.min,a.max),b.displayBtn&&(h=!0))});k=this.resetZoomButton;h&&!k?this.showResetZoom():!h&&c(k)&&(this.resetZoomButton=k.destroy());d&&this.redraw(b(this.options.chart.animation,a&&a.animation,100>this.pointCount))},pan:function(a,b){var c=this,d=c.hoverPoints,e;d&&l(d,function(a){a.setState()});l("xy"===b?[1,0]:[1],function(b){b=c[b?"xAxis":"yAxis"][0];var d=b.horiz,f=a[d?"chartX":"chartY"],d=d?
	"mouseDownX":"mouseDownY",h=c[d],k=(b.pointRange||0)/2,g=b.getExtremes(),l=b.toValue(h-f,!0)+k,k=b.toValue(h+b.len-f,!0)-k,m=k<l,h=m?k:l,l=m?l:k,m=b.toValue(b.toPixels(g.min)-b.minPixelPadding),k=b.toValue(b.toPixels(g.max)+b.minPixelPadding),m=Math.min(g.dataMin,m)-h,g=l-Math.max(g.dataMax,k);b.series.length&&0>m&&0>g&&(b.setExtremes(h,l,!1,!1,{trigger:"pan"}),e=!0);c[d]=f});e&&c.redraw(!1);G(c.container,{cursor:"move"})}});q(p.prototype,{select:function(a,c){var e=this,f=e.series,h=f.chart;a=b(a,
	!e.selected);e.firePointEvent(a?"select":"unselect",{accumulate:c},function(){e.selected=e.options.selected=a;f.options.data[d(e,f.data)]=e.options;e.setState(a&&"select");c||l(h.getSelectedPoints(),function(a){a.selected&&a!==e&&(a.selected=a.options.selected=!1,f.options.data[d(a,f.data)]=a.options,a.setState(""),a.firePointEvent("unselect"))})})},onMouseOver:function(a){var b=this.series.chart.pointer;this.firePointEvent("mouseOver");b.runPointActions(a,this)},onMouseOut:function(){var a=this.series.chart;
	this.firePointEvent("mouseOut");l(a.hoverPoints||[],function(a){a.setState()});a.hoverPoints=a.hoverPoint=null},importEvents:function(){if(!this.hasImportedEvents){var a=z(this.series.options.point,this.options).events,b;this.events=a;for(b in a)B(this,b,a[b]);this.hasImportedEvents=!0}},setState:function(a,c){var d=Math.floor(this.plotX),e=this.plotY,h=this.series,k=h.options.states[a]||{},l=f[h.type].marker&&h.options.marker,n=l&&!1===l.enabled,p=l&&l.states&&l.states[a]||{},r=!1===p.enabled,g=
	h.stateMarkerGraphic,t=this.marker||{},u=h.chart,w=h.halo,z,A=l&&h.markerAttribs;a=a||"";if(!(a===this.state&&!c||this.selected&&"select"!==a||!1===k.enabled||a&&(r||n&&!1===p.enabled)||a&&t.states&&t.states[a]&&!1===t.states[a].enabled)){A&&(z=h.markerAttribs(this,a));if(this.graphic)this.state&&this.graphic.removeClass("highcharts-point-"+this.state),a&&this.graphic.addClass("highcharts-point-"+a),this.graphic.attr(h.pointAttribs(this,a)),z&&this.graphic.animate(z,b(u.options.chart.animation,p.animation,
	l.animation)),g&&g.hide();else{if(a&&p){l=t.symbol||h.symbol;g&&g.currentSymbol!==l&&(g=g.destroy());if(g)g[c?"animate":"attr"]({x:z.x,y:z.y});else l&&(h.stateMarkerGraphic=g=u.renderer.symbol(l,z.x,z.y,z.width,z.height).add(h.markerGroup),g.currentSymbol=l);g&&g.attr(h.pointAttribs(this,a))}g&&(g[a&&u.isInsidePlot(d,e,u.inverted)?"show":"hide"](),g.element.point=this)}(d=k.halo)&&d.size?(w||(h.halo=w=u.renderer.path().add(A?h.markerGroup:h.group)),w[c?"animate":"attr"]({d:this.haloPath(d.size)}),
	w.attr({"class":"highcharts-halo highcharts-color-"+b(this.colorIndex,h.colorIndex)}),w.point=this,w.attr(q({fill:this.color||h.color,"fill-opacity":d.opacity,zIndex:-1},d.attributes))):w&&w.point&&w.point.haloPath&&w.animate({d:w.point.haloPath(0)});this.state=a}},haloPath:function(a){return this.series.chart.renderer.symbols.circle(Math.floor(this.plotX)-a,this.plotY-a,2*a,2*a)}});q(h.prototype,{onMouseOver:function(){var a=this.chart,b=a.hoverSeries;if(b&&b!==this)b.onMouseOut();this.options.events.mouseOver&&
	k(this,"mouseOver");this.setState("hover");a.hoverSeries=this},onMouseOut:function(){var a=this.options,b=this.chart,c=b.tooltip,d=b.hoverPoint;b.hoverSeries=null;if(d)d.onMouseOut();this&&a.events.mouseOut&&k(this,"mouseOut");!c||this.stickyTracking||c.shared&&!this.noSharedTooltip||c.hide();this.setState()},setState:function(a){var c=this,d=c.options,f=c.graph,h=d.states,k=d.lineWidth,d=0;a=a||"";if(c.state!==a&&(l([c.group,c.markerGroup,c.dataLabelsGroup],function(b){b&&(c.state&&b.removeClass("highcharts-series-"+
	c.state),a&&b.addClass("highcharts-series-"+a))}),c.state=a,!h[a]||!1!==h[a].enabled)&&(a&&(k=h[a].lineWidth||k+(h[a].lineWidthPlus||0)),f&&!f.dashstyle))for(k={"stroke-width":k},f.animate(k,b(c.chart.options.chart.animation,h[a]&&h[a].animation));c["zone-graph-"+d];)c["zone-graph-"+d].attr(k),d+=1},setVisible:function(a,b){var c=this,d=c.chart,e=c.legendItem,f,h=d.options.chart.ignoreHiddenSeries,n=c.visible;f=(c.visible=a=c.options.visible=c.userOptions.visible=void 0===a?!n:a)?"show":"hide";l(["group",
	"dataLabelsGroup","markerGroup","tracker","tt"],function(a){if(c[a])c[a][f]()});if(d.hoverSeries===c||(d.hoverPoint&&d.hoverPoint.series)===c)c.onMouseOut();e&&d.legend.colorizeItem(c,a);c.isDirty=!0;c.options.stacking&&l(d.series,function(a){a.options.stacking&&a.visible&&(a.isDirty=!0)});l(c.linkedSeries,function(b){b.setVisible(a,!1)});h&&(d.isDirtyBox=!0);!1!==b&&d.redraw();k(c,f)},show:function(){this.setVisible(!0)},hide:function(){this.setVisible(!1)},select:function(a){this.selected=a=void 0===
	a?!this.selected:a;this.checkbox&&(this.checkbox.checked=a);k(this,a?"select":"unselect")},drawTracker:a.drawTrackerGraph})})(L);(function(a){var B=a.Chart,A=a.each,H=a.inArray,G=a.isArray,r=a.isObject,f=a.pick,l=a.splat;B.prototype.setResponsive=function(f){var k=this.options.responsive,l=[],d=this.currentResponsive;k&&k.rules&&A(k.rules,function(c){void 0===c._id&&(c._id=a.uniqueKey());this.matchResponsiveRule(c,l,f)},this);var c=a.merge.apply(0,a.map(l,function(c){return a.find(k.rules,function(a){return a._id===
	c}).chartOptions})),l=l.toString()||void 0;l!==(d&&d.ruleIds)&&(d&&this.update(d.undoOptions,f),l?(this.currentResponsive={ruleIds:l,mergedOptions:c,undoOptions:this.currentOptions(c)},this.update(c,f)):this.currentResponsive=void 0)};B.prototype.matchResponsiveRule=function(a,k){var l=a.condition;(l.callback||function(){return this.chartWidth<=f(l.maxWidth,Number.MAX_VALUE)&&this.chartHeight<=f(l.maxHeight,Number.MAX_VALUE)&&this.chartWidth>=f(l.minWidth,0)&&this.chartHeight>=f(l.minHeight,0)}).call(this)&&
	k.push(a._id)};B.prototype.currentOptions=function(a){function f(a,c,k,q){var b,d;for(b in a)if(!q&&-1<H(b,["series","xAxis","yAxis"]))for(a[b]=l(a[b]),k[b]=[],d=0;d<a[b].length;d++)c[b][d]&&(k[b][d]={},f(a[b][d],c[b][d],k[b][d],q+1));else r(a[b])?(k[b]=G(a[b])?[]:{},f(a[b],c[b]||{},k[b],q+1)):k[b]=c[b]||null}var q={};f(a,this.options,q,0);return q}})(L);return L});


/***/ },

/***/ 6824:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/highcharts/modules/heatmap.js ***!
  \****************************************************************************************************************/
1662,

/***/ 6825:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/highcharts-custom-events/js/customEvents.js ***!
  \******************************************************************************************************************************/
837,

/***/ 6826:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/object-hash/index.js ***!
  \*******************************************************************************************************/
840,

/***/ 6827:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/main.jsx ***!
  \*****************************************************************************************************************/
[7869, 5963, 6828, 6829, 6830, 6831],

/***/ 6828:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/FactorTooltip.jsx ***!
  \**************************************************************************************************************************/
[7870, 5963],

/***/ 6829:
/*!****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/ContrastTooltip.jsx ***!
  \****************************************************************************************************************************/
[7871, 5963],

/***/ 6830:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/GeneTooltip.jsx ***!
  \************************************************************************************************************************/
[7872, 5963],

/***/ 6831:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/Tooltips.less ***!
  \**********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./Tooltips.less */ 6832);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./Tooltips.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./Tooltips.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6832:
/*!*****************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/tooltips/Tooltips.less ***!
  \*****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n}\n.gxaTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaTooltip td,\n.gxaTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaGeneTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 250px;\n  text-align: justify;\n}\n.gxaGeneTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaGeneTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaGeneTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaGeneTooltip td,\n.gxaGeneTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaGeneTooltip .propertyName {\n  color: brown;\n  font-weight: bold;\n  display: inline-block;\n  text-align: left;\n  margin-right: 5px;\n}\n.gxaGeneTooltip .propertyValue {\n  display: inline-block;\n  text-align: center;\n  background-color: #dfd5d5;\n}\n.gxaGeneTooltip .propertyValue:first-child {\n  margin-left: 5px;\n}\n.gxaGeneTooltip .propertyValue + .propertyValue {\n  margin-left: 5px;\n}\n.gxaContrastTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 500px;\n  display: inline-block;\n}\n.gxaContrastTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaContrastTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaContrastTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaContrastTooltip td,\n.gxaContrastTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n.gxaContrastTooltip .contrastPlots .info {\n  font-style: italic;\n  font-size: xsmall;\n  align-content: right;\n  text-align: right;\n}\n.gxaFactorTooltip {\n  border: solid transparent;\n  color: darkslategray;\n  padding: 2px;\n  font: 10px Verdana, Helvetica, Arial, sans-serif;\n  background-color: floralwhite;\n  max-width: 600px;\n}\n.gxaFactorTooltip table {\n  margin: 0;\n  /* overrides ebi-visual.css:134 */\n  background-color: white;\n  border: 1px solid lightgrey;\n  border-collapse: collapse;\n}\n.gxaFactorTooltip th {\n  border-bottom: 1px solid lightgrey;\n  background-color: floralwhite;\n}\n.gxaFactorTooltip td {\n  border: 1px solid lightgrey;\n}\n.gxaFactorTooltip td,\n.gxaFactorTooltip .gxaTooltip th {\n  vertical-align: middle;\n  padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6833:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/heatmapCellTooltipFormatter.jsx ***!
  \******************************************************************************************************************************************/
[7874, 5963, 6834, 6838, 6873],

/***/ 6834:
/*!****************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/server.js ***!
  \****************************************************************/
[7875, 6835],

/***/ 6835:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactDOMServer.js ***!
  \****************************************************************************/
[7876, 6000, 6836, 6137],

/***/ 6836:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerRendering.js ***!
  \**********************************************************************************/
[7877, 5996, 5964, 6133, 6106, 6027, 6135, 6024, 6837, 6099, 6021, 6088, 6083, 5998],

/***/ 6837:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/react-dom/lib/ReactServerBatchingStrategy.js ***!
  \*****************************************************************************************/
1673,

/***/ 6838:
/*!*********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/HeatmapCellTooltip.jsx ***!
  \*********************************************************************************************************************************/
[7878, 5963, 6839],

/***/ 6839:
/*!**************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/index.js ***!
  \**************************************************************************************************************************/
[7423, 6840],

/***/ 6840:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \**************************************************************************************************************************************/
[7424, 6841],

/***/ 6841:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/react.js ***!
  \**********************************************************************************************************************************/
[7066, 6842],

/***/ 6842:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/React.js ***!
  \**************************************************************************************************************************************/
[7067, 6843, 6844, 6856, 6859, 6860, 6865, 6848, 6870, 6871, 6872, 6850, 6866],

/***/ 6843:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/object-assign/index.js ***!
  \**************************************************************************************************************************************************/
5,

/***/ 6844:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactChildren.js ***!
  \**********************************************************************************************************************************************/
[7068, 6845, 6848, 6851, 6853],

/***/ 6845:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/PooledClass.js ***!
  \********************************************************************************************************************************************/
[7069, 6846, 6847],

/***/ 6846:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/reactProdInvariant.js ***!
  \***************************************************************************************************************************************************/
8,

/***/ 6847:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/invariant.js ***!
  \*************************************************************************************************************************************************/
9,

/***/ 6848:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactElement.js ***!
  \*********************************************************************************************************************************************/
[7070, 6843, 6849, 6850, 6852],

/***/ 6849:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactCurrentOwner.js ***!
  \**************************************************************************************************************************************************/
11,

/***/ 6850:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/warning.js ***!
  \***********************************************************************************************************************************************/
[7071, 6851],

/***/ 6851:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*****************************************************************************************************************************************************/
13,

/***/ 6852:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/canDefineProperty.js ***!
  \**************************************************************************************************************************************************/
14,

/***/ 6853:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/traverseAllChildren.js ***!
  \****************************************************************************************************************************************************/
[7072, 6846, 6849, 6848, 6854, 6847, 6855, 6850],

/***/ 6854:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/getIteratorFn.js ***!
  \**********************************************************************************************************************************************/
16,

/***/ 6855:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/KeyEscapeUtils.js ***!
  \***********************************************************************************************************************************************/
17,

/***/ 6856:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactComponent.js ***!
  \***********************************************************************************************************************************************/
[7073, 6846, 6857, 6852, 6858, 6847, 6850],

/***/ 6857:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*****************************************************************************************************************************************************/
[7074, 6850],

/***/ 6858:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/emptyObject.js ***!
  \***************************************************************************************************************************************************/
20,

/***/ 6859:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPureComponent.js ***!
  \***************************************************************************************************************************************************/
[7075, 6843, 6856, 6857, 6858],

/***/ 6860:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactClass.js ***!
  \*******************************************************************************************************************************************/
[7076, 6846, 6843, 6856, 6848, 6861, 6863, 6857, 6858, 6847, 6862, 6864, 6850],

/***/ 6861:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypeLocations.js ***!
  \*******************************************************************************************************************************************************/
[7077, 6862],

/***/ 6862:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/keyMirror.js ***!
  \*************************************************************************************************************************************************/
[7078, 6847],

/***/ 6863:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypeLocationNames.js ***!
  \***********************************************************************************************************************************************************/
25,

/***/ 6864:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/~/fbjs/lib/keyOf.js ***!
  \*********************************************************************************************************************************************/
26,

/***/ 6865:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactDOMFactories.js ***!
  \**************************************************************************************************************************************************/
[7079, 6848, 6866],

/***/ 6866:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactElementValidator.js ***!
  \******************************************************************************************************************************************************/
[7080, 6849, 6867, 6848, 6861, 6868, 6852, 6854, 6850],

/***/ 6867:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactComponentTreeHook.js ***!
  \*******************************************************************************************************************************************************/
[7081, 6846, 6849, 6847, 6850],

/***/ 6868:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/checkReactTypeSpec.js ***!
  \***************************************************************************************************************************************************/
[7082, 6846, 6863, 6869, 6847, 6850, 6867, 6867],

/***/ 6869:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypesSecret.js ***!
  \*****************************************************************************************************************************************************/
32,

/***/ 6870:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactPropTypes.js ***!
  \***********************************************************************************************************************************************/
[7083, 6848, 6863, 6869, 6851, 6854, 6850],

/***/ 6871:
/*!*********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/ReactVersion.js ***!
  \*********************************************************************************************************************************************/
34,

/***/ 6872:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/expression-atlas-number-format/~/react/lib/onlyChild.js ***!
  \******************************************************************************************************************************************/
[7084, 6846, 6848, 6847],

/***/ 6873:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/he/he.js ***!
  \*******************************************************************************************/
835,

/***/ 6874:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/formatters/axesFormatters.jsx ***!
  \*****************************************************************************************************************************/
[7879, 5963, 6834, 6873],

/***/ 6875:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/HeatmapLegend.jsx ***!
  \********************************************************************************************************************************/
[7880, 5963, 6876, 6879, 6797],

/***/ 6876:
/*!******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.jsx ***!
  \******************************************************************************************************************************************/
[7881, 5963, 6877],

/***/ 6877:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.less ***!
  \*******************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./DataSeriesHeatmapLegend.less */ 6878);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./DataSeriesHeatmapLegend.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./DataSeriesHeatmapLegend.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6878:
/*!**************************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/DataSeriesHeatmapLegend.less ***!
  \**************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHeatmapLegend {\n  color: #606060;\n  margin-left: 180px;\n  border: 0 solid olive;\n}\n.gxaHeatmapLegend .legend-item {\n  display: inline-block;\n  margin-left: 8px;\n  padding: 4px;\n  vertical-align: middle;\n  cursor: default;\n}\n.gxaHeatmapLegend .legend-item.legend-item-off {\n  color: #ccc;\n}\n.gxaHeatmapLegend .legend-item.legend-item-off div {\n  background-color: #f7f7f7;\n}\n.gxaHeatmapLegend .legend-item .legend-rectangle {\n  width: 12px;\n  height: 12px;\n  border: 1px rgba(0, 0, 0, 0.2) solid;\n  display: inline-block;\n  margin-right: 4px;\n  vertical-align: middle;\n}\n.gxaHeatmapLegend .legend-item .icon-generic:before {\n  font-size: 180%;\n  color: #7e7e7e;\n}\n.gxaHeatmapLegend .legend-item:hover .icon-generic:before {\n  color: #353535;\n}\n@font-face {\n  font-family: 'EBI-Generic';\n  src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');\n  src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');\n  font-weight: normal;\n  font-style: normal;\n}\n.icon-generic:before {\n  font-family: 'EBI-Generic';\n  font-size: 100%;\n  color: #BBB;\n  content: attr(data-icon);\n  margin: 0 0 0 0;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6879:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.jsx ***!
  \****************************************************************************************************************************************/
[7883, 5963, 6798, 6880, 6881],

/***/ 6880:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/utils.js ***!
  \*********************************************************************************************/
1681,

/***/ 6881:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.less ***!
  \*****************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./GradientHeatmapLegend.less */ 6882);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./GradientHeatmapLegend.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./GradientHeatmapLegend.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 6882:
/*!************************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/heatmap-legend/GradientHeatmapLegend.less ***!
  \************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientLegend {\n  font-size: 12px;\n  padding-top: 10px;\n}\n.gxaGradientColour {\n  overflow: auto;\n  vertical-align: middle;\n  width: 200px;\n  height: 15px;\n  margin: 2px 6px 2px 6px;\n  display: inline-block;\n}\n.gxaGradientLevel {\n  white-space: nowrap;\n  font-size: 10px;\n  vertical-align: middle;\n  display: table-cell;\n}\n.gxaGradientLevelMin {\n  text-align: right;\n}\n.gxaGradientLevelMax {\n  text-align: left;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 6883:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.jsx ***!
  \***********************************************************************************************************************************/
[7885, 5963, 6709, 6744, 6884, 7032, 7034],

/***/ 6884:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/index.js ***!
  \*********************************************************************************************************/
[7426, 6885],

/***/ 6885:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/Slider.js ***!
  \**********************************************************************************************************/
[7427, 6886, 6905, 6944, 6951, 6952, 6975, 5963, 6983, 6988, 6989, 6990, 7029, 7031],

/***/ 6886:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/defineProperty.js ***!
  \**************************************************************************************************************************************/
[7428, 6887],

/***/ 6887:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/object/define-property.js ***!
  \**********************************************************************************************************************************************/
[7429, 6888],

/***/ 6888:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/object/define-property.js ***!
  \***********************************************************************************************************************************************************/
[7430, 6889, 6892],

/***/ 6889:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.object.define-property.js ***!
  \********************************************************************************************************************************************************************/
[7410, 6890, 6900, 6896],

/***/ 6890:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \*************************************************************************************************************************************************/
[7212, 6891, 6892, 6893, 6895],

/***/ 6891:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \*************************************************************************************************************************************************/
255,

/***/ 6892:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \***********************************************************************************************************************************************/
256,

/***/ 6893:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \**********************************************************************************************************************************************/
[7213, 6894],

/***/ 6894:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \*****************************************************************************************************************************************************/
258,

/***/ 6895:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \***********************************************************************************************************************************************/
[7214, 6896, 6904, 6900],

/***/ 6896:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \****************************************************************************************************************************************************/
[7215, 6897, 6899, 6903, 6900],

/***/ 6897:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \****************************************************************************************************************************************************/
[7216, 6898],

/***/ 6898:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \****************************************************************************************************************************************************/
262,

/***/ 6899:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \*********************************************************************************************************************************************************/
[7217, 6900, 6901, 6902],

/***/ 6900:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \******************************************************************************************************************************************************/
[7218, 6901],

/***/ 6901:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \************************************************************************************************************************************************/
265,

/***/ 6902:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \*****************************************************************************************************************************************************/
[7219, 6898, 6891],

/***/ 6903:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \*******************************************************************************************************************************************************/
[7220, 6898],

/***/ 6904:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \********************************************************************************************************************************************************/
268,

/***/ 6905:
/*!*****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/toConsumableArray.js ***!
  \*****************************************************************************************************************************************/
[7431, 6906],

/***/ 6906:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/array/from.js ***!
  \**********************************************************************************************************************************/
[7432, 6907],

/***/ 6907:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/array/from.js ***!
  \***********************************************************************************************************************************************/
[7433, 6908, 6937, 6892],

/***/ 6908:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \*************************************************************************************************************************************************************/
[7241, 6909, 6912],

/***/ 6909:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \****************************************************************************************************************************************************/
[7242, 6910, 6911],

/***/ 6910:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \*****************************************************************************************************************************************************/
279,

/***/ 6911:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \**************************************************************************************************************************************************/
276,

/***/ 6912:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \******************************************************************************************************************************************************/
[7243, 6913, 6890, 6914, 6895, 6915, 6916, 6917, 6933, 6935, 6934],

/***/ 6913:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \**************************************************************************************************************************************************/
302,

/***/ 6914:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \***************************************************************************************************************************************************/
[7244, 6895],

/***/ 6915:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \**********************************************************************************************************************************************/
272,

/***/ 6916:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \****************************************************************************************************************************************************/
304,

/***/ 6917:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \******************************************************************************************************************************************************/
[7245, 6918, 6904, 6933, 6895, 6934],

/***/ 6918:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \********************************************************************************************************************************************************/
[7246, 6897, 6919, 6931, 6928, 6902, 6932],

/***/ 6919:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \*****************************************************************************************************************************************************/
[7247, 6896, 6897, 6920, 6900],

/***/ 6920:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \******************************************************************************************************************************************************/
[7222, 6921, 6931],

/***/ 6921:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \***************************************************************************************************************************************************************/
[7223, 6915, 6922, 6925, 6928],

/***/ 6922:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \*****************************************************************************************************************************************************/
[7224, 6923, 6911],

/***/ 6923:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \**************************************************************************************************************************************************/
[7225, 6924],

/***/ 6924:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \**********************************************************************************************************************************************/
275,

/***/ 6925:
/*!*********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \*********************************************************************************************************************************************************/
[7226, 6922, 6926, 6927],

/***/ 6926:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \****************************************************************************************************************************************************/
[7227, 6910],

/***/ 6927:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \***************************************************************************************************************************************************/
[7228, 6910],

/***/ 6928:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \*****************************************************************************************************************************************************/
[7229, 6929, 6930],

/***/ 6929:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \*************************************************************************************************************************************************/
[7230, 6891],

/***/ 6930:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \**********************************************************************************************************************************************/
283,

/***/ 6931:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \********************************************************************************************************************************************************/
284,

/***/ 6932:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \***********************************************************************************************************************************************/
[7248, 6891],

/***/ 6933:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \************************************************************************************************************************************************************/
[7249, 6896, 6915, 6934],

/***/ 6934:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \**********************************************************************************************************************************************/
[7250, 6929, 6930, 6891],

/***/ 6935:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \*****************************************************************************************************************************************************/
[7251, 6915, 6936, 6928],

/***/ 6936:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \****************************************************************************************************************************************************/
[7236, 6911],

/***/ 6937:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.array.from.js ***!
  \********************************************************************************************************************************************************/
[7412, 6893, 6890, 6936, 6938, 6939, 6926, 6940, 6941, 6943],

/***/ 6938:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iter-call.js ***!
  \****************************************************************************************************************************************************/
[7413, 6897],

/***/ 6939:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_is-array-iter.js ***!
  \********************************************************************************************************************************************************/
[7414, 6916, 6934],

/***/ 6940:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_create-property.js ***!
  \**********************************************************************************************************************************************************/
[7415, 6896, 6904],

/***/ 6941:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/core.get-iterator-method.js ***!
  \******************************************************************************************************************************************************************/
[7416, 6942, 6934, 6916, 6892],

/***/ 6942:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_classof.js ***!
  \**************************************************************************************************************************************************/
[7411, 6924, 6934],

/***/ 6943:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iter-detect.js ***!
  \******************************************************************************************************************************************************/
[7417, 6934],

/***/ 6944:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/extends.js ***!
  \*******************************************************************************************************************************/
[7231, 6945],

/***/ 6945:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/object/assign.js ***!
  \*************************************************************************************************************************************/
[7232, 6946],

/***/ 6946:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \**************************************************************************************************************************************************/
[7233, 6947, 6892],

/***/ 6947:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \***********************************************************************************************************************************************************/
[7234, 6890, 6948],

/***/ 6948:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \********************************************************************************************************************************************************/
[7235, 6920, 6949, 6950, 6936, 6923, 6901],

/***/ 6949:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \******************************************************************************************************************************************************/
292,

/***/ 6950:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \*****************************************************************************************************************************************************/
285,

/***/ 6951:
/*!**************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/classCallCheck.js ***!
  \**************************************************************************************************************************************/
294,

/***/ 6952:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \*************************************************************************************************************************************************/
[7237, 6953],

/***/ 6953:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/typeof.js ***!
  \******************************************************************************************************************************/
[7238, 6954, 6961],

/***/ 6954:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/symbol/iterator.js ***!
  \***************************************************************************************************************************************/
[7239, 6955],

/***/ 6955:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \****************************************************************************************************************************************************/
[7240, 6908, 6956, 6960],

/***/ 6956:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \**********************************************************************************************************************************************************/
[7252, 6957, 6891, 6895, 6916, 6934],

/***/ 6957:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \************************************************************************************************************************************************************/
[7253, 6958, 6959, 6916, 6922, 6912],

/***/ 6958:
/*!*************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \*************************************************************************************************************************************************************/
314,

/***/ 6959:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \****************************************************************************************************************************************************/
315,

/***/ 6960:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \**************************************************************************************************************************************************/
[7254, 6934],

/***/ 6961:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/symbol.js ***!
  \******************************************************************************************************************************/
[7255, 6962],

/***/ 6962:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \*************************************************************************************************************************************************/
[7256, 6963, 6972, 6973, 6974, 6892],

/***/ 6963:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \****************************************************************************************************************************************************/
[7257, 6891, 6915, 6900, 6890, 6914, 6964, 6901, 6929, 6933, 6930, 6934, 6960, 6965, 6966, 6967, 6968, 6897, 6922, 6903, 6904, 6918, 6969, 6971, 6896, 6920, 6970, 6950, 6949, 6913, 6895],

/***/ 6964:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \***********************************************************************************************************************************************/
[7258, 6930, 6898, 6915, 6896, 6901],

/***/ 6965:
/*!*****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \*****************************************************************************************************************************************************/
[7259, 6891, 6892, 6913, 6960, 6896],

/***/ 6966:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \************************************************************************************************************************************************/
[7260, 6920, 6922],

/***/ 6967:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \****************************************************************************************************************************************************/
[7261, 6920, 6949, 6950],

/***/ 6968:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \***************************************************************************************************************************************************/
[7262, 6924],

/***/ 6969:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \**********************************************************************************************************************************************************/
[7263, 6922, 6970],

/***/ 6970:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \******************************************************************************************************************************************************/
[7264, 6921, 6931],

/***/ 6971:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \******************************************************************************************************************************************************/
[7265, 6950, 6904, 6922, 6903, 6915, 6899, 6900],

/***/ 6972:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \**************************************************************************************************************************************************************/
328,

/***/ 6973:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \*******************************************************************************************************************************************************************/
[7266, 6965],

/***/ 6974:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \***************************************************************************************************************************************************************/
[7267, 6965],

/***/ 6975:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/inherits.js ***!
  \********************************************************************************************************************************/
[7268, 6976, 6980, 6953],

/***/ 6976:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \***********************************************************************************************************************************************/
[7269, 6977],

/***/ 6977:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \************************************************************************************************************************************************************/
[7270, 6978, 6892],

/***/ 6978:
/*!*********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \*********************************************************************************************************************************************************************/
[7271, 6890, 6979],

/***/ 6979:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \****************************************************************************************************************************************************/
[7272, 6898, 6897, 6893, 6971],

/***/ 6980:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/core-js/object/create.js ***!
  \*************************************************************************************************************************************/
[7273, 6981],

/***/ 6981:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \**************************************************************************************************************************************************/
[7274, 6982, 6892],

/***/ 6982:
/*!***********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \***********************************************************************************************************************************************************/
[7275, 6890, 6918],

/***/ 6983:
/*!**********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-util/lib/Dom/addEventListener.js ***!
  \**********************************************************************************************************************************/
[7434, 6984, 5993],

/***/ 6984:
/*!********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-util/~/add-dom-event-listener/lib/index.js ***!
  \********************************************************************************************************************************************/
[7435, 6985],

/***/ 6985:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-util/~/add-dom-event-listener/lib/EventObject.js ***!
  \**************************************************************************************************************************************************/
[7436, 6986, 6987],

/***/ 6986:
/*!******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-util/~/add-dom-event-listener/lib/EventBaseObject.js ***!
  \******************************************************************************************************************************************************/
1000,

/***/ 6987:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-util/~/add-dom-event-listener/~/object-assign/index.js ***!
  \********************************************************************************************************************************************************/
5,

/***/ 6988:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/classnames/index.js ***!
  \******************************************************************************************************************/
339,

/***/ 6989:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/Track.js ***!
  \*********************************************************************************************************/
[7437, 5963],

/***/ 6990:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/Handle.js ***!
  \**********************************************************************************************************/
[7438, 6951, 6952, 6975, 5963, 6991],

/***/ 6991:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/lib/index.js ***!
  \**********************************************************************************************************************/
[7439, 6992],

/***/ 6992:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/lib/Tooltip.js ***!
  \************************************************************************************************************************/
[7440, 5963, 6993, 6994],

/***/ 6993:
/*!***************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/lib/placements.js ***!
  \***************************************************************************************************************************/
1007,

/***/ 6994:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/index.js ***!
  \***********************************************************************************************************************************/
[7441, 6995],

/***/ 6995:
/*!*************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/Trigger.js ***!
  \*************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _extends2 = __webpack_require__(/*! babel-runtime/helpers/extends */ 6944);
	
	var _extends3 = _interopRequireDefault(_extends2);
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 5993);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	var _contains = __webpack_require__(/*! rc-util/lib/Dom/contains */ 6996);
	
	var _contains2 = _interopRequireDefault(_contains);
	
	var _addEventListener = __webpack_require__(/*! rc-util/lib/Dom/addEventListener */ 6997);
	
	var _addEventListener2 = _interopRequireDefault(_addEventListener);
	
	var _Popup = __webpack_require__(/*! ./Popup */ 7002);
	
	var _Popup2 = _interopRequireDefault(_Popup);
	
	var _utils = __webpack_require__(/*! ./utils */ 7027);
	
	var _getContainerRenderMixin = __webpack_require__(/*! rc-util/lib/getContainerRenderMixin */ 7028);
	
	var _getContainerRenderMixin2 = _interopRequireDefault(_getContainerRenderMixin);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }
	
	function noop() {}
	
	function returnEmptyString() {
	  return '';
	}
	
	function returnDocument() {
	  return window.document;
	}
	
	// use fastclick for mobile touch
	var ALL_HANDLERS = ['onClick', 'onMouseDown', 'onMouseEnter', 'onMouseLeave', 'onFocus', 'onBlur'];
	
	var Trigger = _react2["default"].createClass({
	  displayName: 'Trigger',
	
	  propTypes: {
	    children: _react.PropTypes.any,
	    action: _react.PropTypes.oneOfType([_react.PropTypes.string, _react.PropTypes.arrayOf(_react.PropTypes.string)]),
	    showAction: _react.PropTypes.any,
	    hideAction: _react.PropTypes.any,
	    getPopupClassNameFromAlign: _react.PropTypes.any,
	    onPopupVisibleChange: _react.PropTypes.func,
	    afterPopupVisibleChange: _react.PropTypes.func,
	    popup: _react.PropTypes.oneOfType([_react.PropTypes.node, _react.PropTypes.func]).isRequired,
	    popupStyle: _react.PropTypes.object,
	    prefixCls: _react.PropTypes.string,
	    popupClassName: _react.PropTypes.string,
	    popupPlacement: _react.PropTypes.string,
	    builtinPlacements: _react.PropTypes.object,
	    popupTransitionName: _react.PropTypes.oneOfType([_react.PropTypes.string, _react.PropTypes.object]),
	    popupAnimation: _react.PropTypes.any,
	    mouseEnterDelay: _react.PropTypes.number,
	    mouseLeaveDelay: _react.PropTypes.number,
	    zIndex: _react.PropTypes.number,
	    focusDelay: _react.PropTypes.number,
	    blurDelay: _react.PropTypes.number,
	    getPopupContainer: _react.PropTypes.func,
	    getDocument: _react.PropTypes.func,
	    destroyPopupOnHide: _react.PropTypes.bool,
	    mask: _react.PropTypes.bool,
	    maskClosable: _react.PropTypes.bool,
	    onPopupAlign: _react.PropTypes.func,
	    popupAlign: _react.PropTypes.object,
	    popupVisible: _react.PropTypes.bool,
	    maskTransitionName: _react.PropTypes.oneOfType([_react.PropTypes.string, _react.PropTypes.object]),
	    maskAnimation: _react.PropTypes.string
	  },
	
	  mixins: [(0, _getContainerRenderMixin2["default"])({
	    autoMount: false,
	
	    isVisible: function isVisible(instance) {
	      return instance.state.popupVisible;
	    },
	    getContainer: function getContainer(instance) {
	      var props = instance.props;
	
	      var popupContainer = document.createElement('div');
	      // Make sure default popup container will never cause scrollbar appearing
	      // https://github.com/react-component/trigger/issues/41
	      popupContainer.style.position = 'absolute';
	      popupContainer.style.top = '0';
	      popupContainer.style.left = '0';
	      popupContainer.style.width = '100%';
	      var mountNode = props.getPopupContainer ? props.getPopupContainer((0, _reactDom.findDOMNode)(instance)) : props.getDocument().body;
	      mountNode.appendChild(popupContainer);
	      return popupContainer;
	    }
	  })],
	
	  getDefaultProps: function getDefaultProps() {
	    return {
	      prefixCls: 'rc-trigger-popup',
	      getPopupClassNameFromAlign: returnEmptyString,
	      getDocument: returnDocument,
	      onPopupVisibleChange: noop,
	      afterPopupVisibleChange: noop,
	      onPopupAlign: noop,
	      popupClassName: '',
	      mouseEnterDelay: 0,
	      mouseLeaveDelay: 0.1,
	      focusDelay: 0,
	      blurDelay: 0.15,
	      popupStyle: {},
	      destroyPopupOnHide: false,
	      popupAlign: {},
	      defaultPopupVisible: false,
	      mask: false,
	      maskClosable: true,
	      action: [],
	      showAction: [],
	      hideAction: []
	    };
	  },
	  getInitialState: function getInitialState() {
	    var props = this.props;
	    var popupVisible = void 0;
	    if ('popupVisible' in props) {
	      popupVisible = !!props.popupVisible;
	    } else {
	      popupVisible = !!props.defaultPopupVisible;
	    }
	    return {
	      popupVisible: popupVisible
	    };
	  },
	  componentWillMount: function componentWillMount() {
	    var _this = this;
	
	    ALL_HANDLERS.forEach(function (h) {
	      _this['fire' + h] = function (e) {
	        _this.fireEvents(h, e);
	      };
	    });
	  },
	  componentDidMount: function componentDidMount() {
	    this.componentDidUpdate({}, {
	      popupVisible: this.state.popupVisible
	    });
	  },
	  componentWillReceiveProps: function componentWillReceiveProps(_ref) {
	    var popupVisible = _ref.popupVisible;
	
	    if (popupVisible !== undefined) {
	      this.setState({
	        popupVisible: popupVisible
	      });
	    }
	  },
	  componentDidUpdate: function componentDidUpdate(_, prevState) {
	    var props = this.props;
	    var state = this.state;
	    this.renderComponent(null, function () {
	      if (prevState.popupVisible !== state.popupVisible) {
	        props.afterPopupVisibleChange(state.popupVisible);
	      }
	    });
	
	    if (state.popupVisible) {
	      var currentDocument = void 0;
	      if (!this.clickOutsideHandler && this.isClickToHide()) {
	        currentDocument = props.getDocument();
	        this.clickOutsideHandler = (0, _addEventListener2["default"])(currentDocument, 'click', this.onDocumentClick);
	      }
	      return;
	    }
	
	    this.clearOutsideHandler();
	  },
	  componentWillUnmount: function componentWillUnmount() {
	    this.clearDelayTimer();
	    this.clearOutsideHandler();
	  },
	  onMouseEnter: function onMouseEnter(e) {
	    this.fireEvents('onMouseEnter', e);
	    this.delaySetPopupVisible(true, this.props.mouseEnterDelay);
	  },
	  onMouseLeave: function onMouseLeave(e) {
	    this.fireEvents('onMouseLeave', e);
	    this.delaySetPopupVisible(false, this.props.mouseLeaveDelay);
	  },
	  onPopupMouseEnter: function onPopupMouseEnter() {
	    this.clearDelayTimer();
	  },
	  onPopupMouseLeave: function onPopupMouseLeave(e) {
	    // https://github.com/react-component/trigger/pull/13
	    // react bug?
	    if (e.relatedTarget && !e.relatedTarget.setTimeout && this._component && (0, _contains2["default"])(this._component.getPopupDomNode(), e.relatedTarget)) {
	      return;
	    }
	    this.delaySetPopupVisible(false, this.props.mouseLeaveDelay);
	  },
	  onFocus: function onFocus(e) {
	    this.fireEvents('onFocus', e);
	    // incase focusin and focusout
	    this.clearDelayTimer();
	    if (this.isFocusToShow()) {
	      this.focusTime = Date.now();
	      this.delaySetPopupVisible(true, this.props.focusDelay);
	    }
	  },
	  onMouseDown: function onMouseDown(e) {
	    this.fireEvents('onMouseDown', e);
	    this.preClickTime = Date.now();
	  },
	  onBlur: function onBlur(e) {
	    this.fireEvents('onBlur', e);
	    this.clearDelayTimer();
	    if (this.isBlurToHide()) {
	      this.delaySetPopupVisible(false, this.props.blurDelay);
	    }
	  },
	  onClick: function onClick(event) {
	    this.fireEvents('onClick', event);
	    // focus will trigger click
	    if (this.focusTime) {
	      var preTime = void 0;
	      if (this.preClickTime) {
	        preTime = this.preClickTime;
	      }
	      if (Math.abs(preTime - this.focusTime) < 20) {
	        return;
	      }
	      this.focusTime = 0;
	    }
	    this.preClickTime = 0;
	    event.preventDefault();
	    var nextVisible = !this.state.popupVisible;
	    if (this.isClickToHide() && !nextVisible || nextVisible && this.isClickToShow()) {
	      this.setPopupVisible(!this.state.popupVisible);
	    }
	  },
	  onDocumentClick: function onDocumentClick(event) {
	    if (this.props.mask && !this.props.maskClosable) {
	      return;
	    }
	    var target = event.target;
	    var root = (0, _reactDom.findDOMNode)(this);
	    var popupNode = this.getPopupDomNode();
	    if (!(0, _contains2["default"])(root, target) && !(0, _contains2["default"])(popupNode, target)) {
	      this.close();
	    }
	  },
	  getPopupDomNode: function getPopupDomNode() {
	    // for test
	    if (this._component) {
	      return this._component.isMounted() ? this._component.getPopupDomNode() : null;
	    }
	    return null;
	  },
	  getRootDomNode: function getRootDomNode() {
	    return _reactDom2["default"].findDOMNode(this);
	  },
	  getPopupClassNameFromAlign: function getPopupClassNameFromAlign(align) {
	    var className = [];
	    var props = this.props;
	    var popupPlacement = props.popupPlacement,
	        builtinPlacements = props.builtinPlacements,
	        prefixCls = props.prefixCls;
	
	    if (popupPlacement && builtinPlacements) {
	      className.push((0, _utils.getPopupClassNameFromAlign)(builtinPlacements, prefixCls, align));
	    }
	    if (props.getPopupClassNameFromAlign) {
	      className.push(props.getPopupClassNameFromAlign(align));
	    }
	    return className.join(' ');
	  },
	  getPopupAlign: function getPopupAlign() {
	    var props = this.props;
	    var popupPlacement = props.popupPlacement,
	        popupAlign = props.popupAlign,
	        builtinPlacements = props.builtinPlacements;
	
	    if (popupPlacement && builtinPlacements) {
	      return (0, _utils.getAlignFromPlacement)(builtinPlacements, popupPlacement, popupAlign);
	    }
	    return popupAlign;
	  },
	  getComponent: function getComponent() {
	    var props = this.props,
	        state = this.state;
	
	    var mouseProps = {};
	    if (this.isMouseEnterToShow()) {
	      mouseProps.onMouseEnter = this.onPopupMouseEnter;
	    }
	    if (this.isMouseLeaveToHide()) {
	      mouseProps.onMouseLeave = this.onPopupMouseLeave;
	    }
	    return _react2["default"].createElement(
	      _Popup2["default"],
	      (0, _extends3["default"])({
	        prefixCls: props.prefixCls,
	        destroyPopupOnHide: props.destroyPopupOnHide,
	        visible: state.popupVisible,
	        className: props.popupClassName,
	        action: props.action,
	        align: this.getPopupAlign(),
	        onAlign: props.onPopupAlign,
	        animation: props.popupAnimation,
	        getClassNameFromAlign: this.getPopupClassNameFromAlign
	      }, mouseProps, {
	        getRootDomNode: this.getRootDomNode,
	        style: props.popupStyle,
	        mask: props.mask,
	        zIndex: props.zIndex,
	        transitionName: props.popupTransitionName,
	        maskAnimation: props.maskAnimation,
	        maskTransitionName: props.maskTransitionName
	      }),
	      typeof props.popup === 'function' ? props.popup() : props.popup
	    );
	  },
	  setPopupVisible: function setPopupVisible(popupVisible) {
	    this.clearDelayTimer();
	    if (this.state.popupVisible !== popupVisible) {
	      if (!('popupVisible' in this.props)) {
	        this.setState({
	          popupVisible: popupVisible
	        });
	      }
	      this.props.onPopupVisibleChange(popupVisible);
	    }
	  },
	  delaySetPopupVisible: function delaySetPopupVisible(visible, delayS) {
	    var _this2 = this;
	
	    var delay = delayS * 1000;
	    this.clearDelayTimer();
	    if (delay) {
	      this.delayTimer = setTimeout(function () {
	        _this2.setPopupVisible(visible);
	        _this2.clearDelayTimer();
	      }, delay);
	    } else {
	      this.setPopupVisible(visible);
	    }
	  },
	  clearDelayTimer: function clearDelayTimer() {
	    if (this.delayTimer) {
	      clearTimeout(this.delayTimer);
	      this.delayTimer = null;
	    }
	  },
	  clearOutsideHandler: function clearOutsideHandler() {
	    if (this.clickOutsideHandler) {
	      this.clickOutsideHandler.remove();
	      this.clickOutsideHandler = null;
	    }
	  },
	  createTwoChains: function createTwoChains(event) {
	    var childPros = this.props.children.props;
	    var props = this.props;
	    if (childPros[event] && props[event]) {
	      return this['fire' + event];
	    }
	    return childPros[event] || props[event];
	  },
	  isClickToShow: function isClickToShow() {
	    var _props = this.props,
	        action = _props.action,
	        showAction = _props.showAction;
	
	    return action.indexOf('click') !== -1 || showAction.indexOf('click') !== -1;
	  },
	  isClickToHide: function isClickToHide() {
	    var _props2 = this.props,
	        action = _props2.action,
	        hideAction = _props2.hideAction;
	
	    return action.indexOf('click') !== -1 || hideAction.indexOf('click') !== -1;
	  },
	  isMouseEnterToShow: function isMouseEnterToShow() {
	    var _props3 = this.props,
	        action = _props3.action,
	        showAction = _props3.showAction;
	
	    return action.indexOf('hover') !== -1 || showAction.indexOf('mouseEnter') !== -1;
	  },
	  isMouseLeaveToHide: function isMouseLeaveToHide() {
	    var _props4 = this.props,
	        action = _props4.action,
	        hideAction = _props4.hideAction;
	
	    return action.indexOf('hover') !== -1 || hideAction.indexOf('mouseLeave') !== -1;
	  },
	  isFocusToShow: function isFocusToShow() {
	    var _props5 = this.props,
	        action = _props5.action,
	        showAction = _props5.showAction;
	
	    return action.indexOf('focus') !== -1 || showAction.indexOf('focus') !== -1;
	  },
	  isBlurToHide: function isBlurToHide() {
	    var _props6 = this.props,
	        action = _props6.action,
	        hideAction = _props6.hideAction;
	
	    return action.indexOf('focus') !== -1 || hideAction.indexOf('blur') !== -1;
	  },
	  forcePopupAlign: function forcePopupAlign() {
	    if (this.state.popupVisible && this.popupInstance && this.popupInstance.alignInstance) {
	      this.popupInstance.alignInstance.forceAlign();
	    }
	  },
	  fireEvents: function fireEvents(type, e) {
	    var childCallback = this.props.children.props[type];
	    if (childCallback) {
	      childCallback(e);
	    }
	    var callback = this.props[type];
	    if (callback) {
	      callback(e);
	    }
	  },
	  close: function close() {
	    this.setPopupVisible(false);
	  },
	  render: function render() {
	    var props = this.props;
	    var children = props.children;
	    var child = _react2["default"].Children.only(children);
	    var newChildProps = {};
	    if (this.isClickToHide() || this.isClickToShow()) {
	      newChildProps.onClick = this.onClick;
	      newChildProps.onMouseDown = this.onMouseDown;
	    } else {
	      newChildProps.onClick = this.createTwoChains('onClick');
	      newChildProps.onMouseDown = this.createTwoChains('onMouseDown');
	    }
	    if (this.isMouseEnterToShow()) {
	      newChildProps.onMouseEnter = this.onMouseEnter;
	    } else {
	      newChildProps.onMouseEnter = this.createTwoChains('onMouseEnter');
	    }
	    if (this.isMouseLeaveToHide()) {
	      newChildProps.onMouseLeave = this.onMouseLeave;
	    } else {
	      newChildProps.onMouseLeave = this.createTwoChains('onMouseLeave');
	    }
	    if (this.isFocusToShow() || this.isBlurToHide()) {
	      newChildProps.onFocus = this.onFocus;
	      newChildProps.onBlur = this.onBlur;
	    } else {
	      newChildProps.onFocus = this.createTwoChains('onFocus');
	      newChildProps.onBlur = this.createTwoChains('onBlur');
	    }
	
	    return _react2["default"].cloneElement(child, newChildProps);
	  }
	});
	
	exports["default"] = Trigger;
	module.exports = exports['default'];

/***/ },

/***/ 6996:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/lib/Dom/contains.js ***!
  \****************************************************************************************************************************************************/
1010,

/***/ 6997:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/lib/Dom/addEventListener.js ***!
  \************************************************************************************************************************************************************/
[7434, 6998, 5993],

/***/ 6998:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/~/add-dom-event-listener/lib/index.js ***!
  \**********************************************************************************************************************************************************************/
[7435, 6999],

/***/ 6999:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/~/add-dom-event-listener/lib/EventObject.js ***!
  \****************************************************************************************************************************************************************************/
[7436, 7000, 7001],

/***/ 7000:
/*!********************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/~/add-dom-event-listener/lib/EventBaseObject.js ***!
  \********************************************************************************************************************************************************************************/
1000,

/***/ 7001:
/*!**********************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/~/add-dom-event-listener/~/object-assign/index.js ***!
  \**********************************************************************************************************************************************************************************/
5,

/***/ 7002:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/Popup.js ***!
  \***********************************************************************************************************************************/
[7442, 6944, 5963, 5993, 7003, 7015, 7024, 7025],

/***/ 7003:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/lib/index.js ***!
  \**********************************************************************************************************************************************/
[7443, 7004],

/***/ 7004:
/*!**********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/lib/Align.js ***!
  \**********************************************************************************************************************************************/
[7444, 5963, 5993, 7005, 6997, 7014],

/***/ 7005:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/index.js ***!
  \**********************************************************************************************************************************************************/
[7445, 7006, 7008, 7009, 7010, 7011, 7012],

/***/ 7006:
/*!**********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/utils.js ***!
  \**********************************************************************************************************************************************************/
[7446, 7007],

/***/ 7007:
/*!******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/propertyUtils.js ***!
  \******************************************************************************************************************************************************************/
1021,

/***/ 7008:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/getOffsetParent.js ***!
  \********************************************************************************************************************************************************************/
[7447, 7006],

/***/ 7009:
/*!*****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/getVisibleRectForElement.js ***!
  \*****************************************************************************************************************************************************************************/
[7448, 7006, 7008],

/***/ 7010:
/*!**********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/adjustForViewport.js ***!
  \**********************************************************************************************************************************************************************/
[7449, 7006],

/***/ 7011:
/*!**************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/getRegion.js ***!
  \**************************************************************************************************************************************************************/
[7450, 7006],

/***/ 7012:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/getElFuturePos.js ***!
  \*******************************************************************************************************************************************************************/
[7451, 7013],

/***/ 7013:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/~/dom-align/lib/getAlignOffset.js ***!
  \*******************************************************************************************************************************************************************/
1027,

/***/ 7014:
/*!*************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-align/lib/isWindow.js ***!
  \*************************************************************************************************************************************************/
1028,

/***/ 7015:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/lib/index.js ***!
  \************************************************************************************************************************************************/
[7452, 7016],

/***/ 7016:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/lib/Animate.js ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _ChildrenUtils = __webpack_require__(/*! ./ChildrenUtils */ 7017);
	
	var _AnimateChild = __webpack_require__(/*! ./AnimateChild */ 7018);
	
	var _AnimateChild2 = _interopRequireDefault(_AnimateChild);
	
	var _util = __webpack_require__(/*! ./util */ 7023);
	
	var _util2 = _interopRequireDefault(_util);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }
	
	function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }
	
	var defaultKey = 'rc_animate_' + Date.now();
	
	
	function getChildrenFromProps(props) {
	  var children = props.children;
	  if (_react2["default"].isValidElement(children)) {
	    if (!children.key) {
	      return _react2["default"].cloneElement(children, {
	        key: defaultKey
	      });
	    }
	  }
	  return children;
	}
	
	function noop() {}
	
	var Animate = _react2["default"].createClass({
	  displayName: 'Animate',
	
	  propTypes: {
	    component: _react2["default"].PropTypes.any,
	    componentProps: _react2["default"].PropTypes.object,
	    animation: _react2["default"].PropTypes.object,
	    transitionName: _react2["default"].PropTypes.oneOfType([_react2["default"].PropTypes.string, _react2["default"].PropTypes.object]),
	    transitionEnter: _react2["default"].PropTypes.bool,
	    transitionAppear: _react2["default"].PropTypes.bool,
	    exclusive: _react2["default"].PropTypes.bool,
	    transitionLeave: _react2["default"].PropTypes.bool,
	    onEnd: _react2["default"].PropTypes.func,
	    onEnter: _react2["default"].PropTypes.func,
	    onLeave: _react2["default"].PropTypes.func,
	    onAppear: _react2["default"].PropTypes.func,
	    showProp: _react2["default"].PropTypes.string
	  },
	
	  getDefaultProps: function getDefaultProps() {
	    return {
	      animation: {},
	      component: 'span',
	      componentProps: {},
	      transitionEnter: true,
	      transitionLeave: true,
	      transitionAppear: false,
	      onEnd: noop,
	      onEnter: noop,
	      onLeave: noop,
	      onAppear: noop
	    };
	  },
	  getInitialState: function getInitialState() {
	    this.currentlyAnimatingKeys = {};
	    this.keysToEnter = [];
	    this.keysToLeave = [];
	    return {
	      children: (0, _ChildrenUtils.toArrayChildren)(getChildrenFromProps(this.props))
	    };
	  },
	  componentDidMount: function componentDidMount() {
	    var _this = this;
	
	    var showProp = this.props.showProp;
	    var children = this.state.children;
	    if (showProp) {
	      children = children.filter(function (child) {
	        return !!child.props[showProp];
	      });
	    }
	    children.forEach(function (child) {
	      if (child) {
	        _this.performAppear(child.key);
	      }
	    });
	  },
	  componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
	    var _this2 = this;
	
	    this.nextProps = nextProps;
	    var nextChildren = (0, _ChildrenUtils.toArrayChildren)(getChildrenFromProps(nextProps));
	    var props = this.props;
	    // exclusive needs immediate response
	    if (props.exclusive) {
	      Object.keys(this.currentlyAnimatingKeys).forEach(function (key) {
	        _this2.stop(key);
	      });
	    }
	    var showProp = props.showProp;
	    var currentlyAnimatingKeys = this.currentlyAnimatingKeys;
	    // last props children if exclusive
	    var currentChildren = props.exclusive ? (0, _ChildrenUtils.toArrayChildren)(getChildrenFromProps(props)) : this.state.children;
	    // in case destroy in showProp mode
	    var newChildren = [];
	    if (showProp) {
	      currentChildren.forEach(function (currentChild) {
	        var nextChild = currentChild && (0, _ChildrenUtils.findChildInChildrenByKey)(nextChildren, currentChild.key);
	        var newChild = void 0;
	        if ((!nextChild || !nextChild.props[showProp]) && currentChild.props[showProp]) {
	          newChild = _react2["default"].cloneElement(nextChild || currentChild, _defineProperty({}, showProp, true));
	        } else {
	          newChild = nextChild;
	        }
	        if (newChild) {
	          newChildren.push(newChild);
	        }
	      });
	      nextChildren.forEach(function (nextChild) {
	        if (!nextChild || !(0, _ChildrenUtils.findChildInChildrenByKey)(currentChildren, nextChild.key)) {
	          newChildren.push(nextChild);
	        }
	      });
	    } else {
	      newChildren = (0, _ChildrenUtils.mergeChildren)(currentChildren, nextChildren);
	    }
	
	    // need render to avoid update
	    this.setState({
	      children: newChildren
	    });
	
	    nextChildren.forEach(function (child) {
	      var key = child && child.key;
	      if (child && currentlyAnimatingKeys[key]) {
	        return;
	      }
	      var hasPrev = child && (0, _ChildrenUtils.findChildInChildrenByKey)(currentChildren, key);
	      if (showProp) {
	        var showInNext = child.props[showProp];
	        if (hasPrev) {
	          var showInNow = (0, _ChildrenUtils.findShownChildInChildrenByKey)(currentChildren, key, showProp);
	          if (!showInNow && showInNext) {
	            _this2.keysToEnter.push(key);
	          }
	        } else if (showInNext) {
	          _this2.keysToEnter.push(key);
	        }
	      } else if (!hasPrev) {
	        _this2.keysToEnter.push(key);
	      }
	    });
	
	    currentChildren.forEach(function (child) {
	      var key = child && child.key;
	      if (child && currentlyAnimatingKeys[key]) {
	        return;
	      }
	      var hasNext = child && (0, _ChildrenUtils.findChildInChildrenByKey)(nextChildren, key);
	      if (showProp) {
	        var showInNow = child.props[showProp];
	        if (hasNext) {
	          var showInNext = (0, _ChildrenUtils.findShownChildInChildrenByKey)(nextChildren, key, showProp);
	          if (!showInNext && showInNow) {
	            _this2.keysToLeave.push(key);
	          }
	        } else if (showInNow) {
	          _this2.keysToLeave.push(key);
	        }
	      } else if (!hasNext) {
	        _this2.keysToLeave.push(key);
	      }
	    });
	  },
	  componentDidUpdate: function componentDidUpdate() {
	    var keysToEnter = this.keysToEnter;
	    this.keysToEnter = [];
	    keysToEnter.forEach(this.performEnter);
	    var keysToLeave = this.keysToLeave;
	    this.keysToLeave = [];
	    keysToLeave.forEach(this.performLeave);
	  },
	  performEnter: function performEnter(key) {
	    // may already remove by exclusive
	    if (this.refs[key]) {
	      this.currentlyAnimatingKeys[key] = true;
	      this.refs[key].componentWillEnter(this.handleDoneAdding.bind(this, key, 'enter'));
	    }
	  },
	  performAppear: function performAppear(key) {
	    if (this.refs[key]) {
	      this.currentlyAnimatingKeys[key] = true;
	      this.refs[key].componentWillAppear(this.handleDoneAdding.bind(this, key, 'appear'));
	    }
	  },
	  handleDoneAdding: function handleDoneAdding(key, type) {
	    var props = this.props;
	    delete this.currentlyAnimatingKeys[key];
	    // if update on exclusive mode, skip check
	    if (props.exclusive && props !== this.nextProps) {
	      return;
	    }
	    var currentChildren = (0, _ChildrenUtils.toArrayChildren)(getChildrenFromProps(props));
	    if (!this.isValidChildByKey(currentChildren, key)) {
	      // exclusive will not need this
	      this.performLeave(key);
	    } else {
	      if (type === 'appear') {
	        if (_util2["default"].allowAppearCallback(props)) {
	          props.onAppear(key);
	          props.onEnd(key, true);
	        }
	      } else {
	        if (_util2["default"].allowEnterCallback(props)) {
	          props.onEnter(key);
	          props.onEnd(key, true);
	        }
	      }
	    }
	  },
	  performLeave: function performLeave(key) {
	    // may already remove by exclusive
	    if (this.refs[key]) {
	      this.currentlyAnimatingKeys[key] = true;
	      this.refs[key].componentWillLeave(this.handleDoneLeaving.bind(this, key));
	    }
	  },
	  handleDoneLeaving: function handleDoneLeaving(key) {
	    var props = this.props;
	    delete this.currentlyAnimatingKeys[key];
	    // if update on exclusive mode, skip check
	    if (props.exclusive && props !== this.nextProps) {
	      return;
	    }
	    var currentChildren = (0, _ChildrenUtils.toArrayChildren)(getChildrenFromProps(props));
	    // in case state change is too fast
	    if (this.isValidChildByKey(currentChildren, key)) {
	      this.performEnter(key);
	    } else {
	      var end = function end() {
	        if (_util2["default"].allowLeaveCallback(props)) {
	          props.onLeave(key);
	          props.onEnd(key, false);
	        }
	      };
	      /* eslint react/no-is-mounted:0 */
	      if (this.isMounted() && !(0, _ChildrenUtils.isSameChildren)(this.state.children, currentChildren, props.showProp)) {
	        this.setState({
	          children: currentChildren
	        }, end);
	      } else {
	        end();
	      }
	    }
	  },
	  isValidChildByKey: function isValidChildByKey(currentChildren, key) {
	    var showProp = this.props.showProp;
	    if (showProp) {
	      return (0, _ChildrenUtils.findShownChildInChildrenByKey)(currentChildren, key, showProp);
	    }
	    return (0, _ChildrenUtils.findChildInChildrenByKey)(currentChildren, key);
	  },
	  stop: function stop(key) {
	    delete this.currentlyAnimatingKeys[key];
	    var component = this.refs[key];
	    if (component) {
	      component.stop();
	    }
	  },
	  render: function render() {
	    var props = this.props;
	    this.nextProps = props;
	    var stateChildren = this.state.children;
	    var children = null;
	    if (stateChildren) {
	      children = stateChildren.map(function (child) {
	        if (child === null || child === undefined) {
	          return child;
	        }
	        if (!child.key) {
	          throw new Error('must set key for <rc-animate> children');
	        }
	        return _react2["default"].createElement(
	          _AnimateChild2["default"],
	          {
	            key: child.key,
	            ref: child.key,
	            animation: props.animation,
	            transitionName: props.transitionName,
	            transitionEnter: props.transitionEnter,
	            transitionAppear: props.transitionAppear,
	            transitionLeave: props.transitionLeave
	          },
	          child
	        );
	      });
	    }
	    var Component = props.component;
	    if (Component) {
	      var passedProps = props;
	      if (typeof Component === 'string') {
	        passedProps = _extends({
	          className: props.className,
	          style: props.style
	        }, props.componentProps);
	      }
	      return _react2["default"].createElement(
	        Component,
	        passedProps,
	        children
	      );
	    }
	    return children[0] || null;
	  }
	});
	
	exports["default"] = Animate;
	module.exports = exports['default'];

/***/ },

/***/ 7017:
/*!********************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/lib/ChildrenUtils.js ***!
  \********************************************************************************************************************************************************/
[7453, 5963],

/***/ 7018:
/*!*******************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/lib/AnimateChild.js ***!
  \*******************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var _react = __webpack_require__(/*! react */ 5963);
	
	var _react2 = _interopRequireDefault(_react);
	
	var _reactDom = __webpack_require__(/*! react-dom */ 5993);
	
	var _reactDom2 = _interopRequireDefault(_reactDom);
	
	var _cssAnimation = __webpack_require__(/*! css-animation */ 7019);
	
	var _cssAnimation2 = _interopRequireDefault(_cssAnimation);
	
	var _util = __webpack_require__(/*! ./util */ 7023);
	
	var _util2 = _interopRequireDefault(_util);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }
	
	var transitionMap = {
	  enter: 'transitionEnter',
	  appear: 'transitionAppear',
	  leave: 'transitionLeave'
	};
	
	var AnimateChild = _react2["default"].createClass({
	  displayName: 'AnimateChild',
	
	  propTypes: {
	    children: _react2["default"].PropTypes.any
	  },
	
	  componentWillUnmount: function componentWillUnmount() {
	    this.stop();
	  },
	  componentWillEnter: function componentWillEnter(done) {
	    if (_util2["default"].isEnterSupported(this.props)) {
	      this.transition('enter', done);
	    } else {
	      done();
	    }
	  },
	  componentWillAppear: function componentWillAppear(done) {
	    if (_util2["default"].isAppearSupported(this.props)) {
	      this.transition('appear', done);
	    } else {
	      done();
	    }
	  },
	  componentWillLeave: function componentWillLeave(done) {
	    if (_util2["default"].isLeaveSupported(this.props)) {
	      this.transition('leave', done);
	    } else {
	      // always sync, do not interupt with react component life cycle
	      // update hidden -> animate hidden ->
	      // didUpdate -> animate leave -> unmount (if animate is none)
	      done();
	    }
	  },
	  transition: function transition(animationType, finishCallback) {
	    var _this = this;
	
	    var node = _reactDom2["default"].findDOMNode(this);
	    var props = this.props;
	    var transitionName = props.transitionName;
	    var nameIsObj = (typeof transitionName === 'undefined' ? 'undefined' : _typeof(transitionName)) === 'object';
	    this.stop();
	    var end = function end() {
	      _this.stopper = null;
	      finishCallback();
	    };
	    if ((_cssAnimation.isCssAnimationSupported || !props.animation[animationType]) && transitionName && props[transitionMap[animationType]]) {
	      var name = nameIsObj ? transitionName[animationType] : transitionName + '-' + animationType;
	      var activeName = name + '-active';
	      if (nameIsObj && transitionName[animationType + 'Active']) {
	        activeName = transitionName[animationType + 'Active'];
	      }
	      this.stopper = (0, _cssAnimation2["default"])(node, {
	        name: name,
	        active: activeName
	      }, end);
	    } else {
	      this.stopper = props.animation[animationType](node, end);
	    }
	  },
	  stop: function stop() {
	    var stopper = this.stopper;
	    if (stopper) {
	      this.stopper = null;
	      stopper.stop();
	    }
	  },
	  render: function render() {
	    return this.props.children;
	  }
	});
	
	exports["default"] = AnimateChild;
	module.exports = exports['default'];

/***/ },

/***/ 7019:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/~/css-animation/lib/index.js ***!
  \****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var _Event = __webpack_require__(/*! ./Event */ 7020);
	
	var _Event2 = _interopRequireDefault(_Event);
	
	var _componentClasses = __webpack_require__(/*! component-classes */ 7021);
	
	var _componentClasses2 = _interopRequireDefault(_componentClasses);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }
	
	var isCssAnimationSupported = _Event2["default"].endEvents.length !== 0;
	
	
	var capitalPrefixes = ['Webkit', 'Moz', 'O',
	// ms is special .... !
	'ms'];
	var prefixes = ['-webkit-', '-moz-', '-o-', 'ms-', ''];
	
	function getStyleProperty(node, name) {
	  // old ff need null, https://developer.mozilla.org/en-US/docs/Web/API/Window/getComputedStyle
	  var style = window.getComputedStyle(node, null);
	  var ret = '';
	  for (var i = 0; i < prefixes.length; i++) {
	    ret = style.getPropertyValue(prefixes[i] + name);
	    if (ret) {
	      break;
	    }
	  }
	  return ret;
	}
	
	function fixBrowserByTimeout(node) {
	  if (isCssAnimationSupported) {
	    var transitionDelay = parseFloat(getStyleProperty(node, 'transition-delay')) || 0;
	    var transitionDuration = parseFloat(getStyleProperty(node, 'transition-duration')) || 0;
	    var animationDelay = parseFloat(getStyleProperty(node, 'animation-delay')) || 0;
	    var animationDuration = parseFloat(getStyleProperty(node, 'animation-duration')) || 0;
	    var time = Math.max(transitionDuration + transitionDelay, animationDuration + animationDelay);
	    // sometimes, browser bug
	    node.rcEndAnimTimeout = setTimeout(function () {
	      node.rcEndAnimTimeout = null;
	      if (node.rcEndListener) {
	        node.rcEndListener();
	      }
	    }, time * 1000 + 200);
	  }
	}
	
	function clearBrowserBugTimeout(node) {
	  if (node.rcEndAnimTimeout) {
	    clearTimeout(node.rcEndAnimTimeout);
	    node.rcEndAnimTimeout = null;
	  }
	}
	
	var cssAnimation = function cssAnimation(node, transitionName, endCallback) {
	  var nameIsObj = (typeof transitionName === 'undefined' ? 'undefined' : _typeof(transitionName)) === 'object';
	  var className = nameIsObj ? transitionName.name : transitionName;
	  var activeClassName = nameIsObj ? transitionName.active : transitionName + '-active';
	  var end = endCallback;
	  var start = void 0;
	  var active = void 0;
	  var nodeClasses = (0, _componentClasses2["default"])(node);
	
	  if (endCallback && Object.prototype.toString.call(endCallback) === '[object Object]') {
	    end = endCallback.end;
	    start = endCallback.start;
	    active = endCallback.active;
	  }
	
	  if (node.rcEndListener) {
	    node.rcEndListener();
	  }
	
	  node.rcEndListener = function (e) {
	    if (e && e.target !== node) {
	      return;
	    }
	
	    if (node.rcAnimTimeout) {
	      clearTimeout(node.rcAnimTimeout);
	      node.rcAnimTimeout = null;
	    }
	
	    clearBrowserBugTimeout(node);
	
	    nodeClasses.remove(className);
	    nodeClasses.remove(activeClassName);
	
	    _Event2["default"].removeEndEventListener(node, node.rcEndListener);
	    node.rcEndListener = null;
	
	    // Usually this optional end is used for informing an owner of
	    // a leave animation and telling it to remove the child.
	    if (end) {
	      end();
	    }
	  };
	
	  _Event2["default"].addEndEventListener(node, node.rcEndListener);
	
	  if (start) {
	    start();
	  }
	  nodeClasses.add(className);
	
	  node.rcAnimTimeout = setTimeout(function () {
	    node.rcAnimTimeout = null;
	    nodeClasses.add(activeClassName);
	    if (active) {
	      setTimeout(active, 0);
	    }
	    fixBrowserByTimeout(node);
	    // 30ms for firefox
	  }, 30);
	
	  return {
	    stop: function stop() {
	      if (node.rcEndListener) {
	        node.rcEndListener();
	      }
	    }
	  };
	};
	
	cssAnimation.style = function (node, style, callback) {
	  if (node.rcEndListener) {
	    node.rcEndListener();
	  }
	
	  node.rcEndListener = function (e) {
	    if (e && e.target !== node) {
	      return;
	    }
	
	    if (node.rcAnimTimeout) {
	      clearTimeout(node.rcAnimTimeout);
	      node.rcAnimTimeout = null;
	    }
	
	    clearBrowserBugTimeout(node);
	
	    _Event2["default"].removeEndEventListener(node, node.rcEndListener);
	    node.rcEndListener = null;
	
	    // Usually this optional callback is used for informing an owner of
	    // a leave animation and telling it to remove the child.
	    if (callback) {
	      callback();
	    }
	  };
	
	  _Event2["default"].addEndEventListener(node, node.rcEndListener);
	
	  node.rcAnimTimeout = setTimeout(function () {
	    for (var s in style) {
	      if (style.hasOwnProperty(s)) {
	        node.style[s] = style[s];
	      }
	    }
	    node.rcAnimTimeout = null;
	    fixBrowserByTimeout(node);
	  }, 0);
	};
	
	cssAnimation.setTransition = function (node, p, value) {
	  var property = p;
	  var v = value;
	  if (value === undefined) {
	    v = property;
	    property = '';
	  }
	  property = property || '';
	  capitalPrefixes.forEach(function (prefix) {
	    node.style[prefix + 'Transition' + property] = v;
	  });
	};
	
	cssAnimation.isCssAnimationSupported = isCssAnimationSupported;
	
	exports["default"] = cssAnimation;
	module.exports = exports['default'];

/***/ },

/***/ 7020:
/*!****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/~/css-animation/lib/Event.js ***!
  \****************************************************************************************************************************************************************/
1034,

/***/ 7021:
/*!********************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/~/css-animation/~/component-classes/index.js ***!
  \********************************************************************************************************************************************************************************/
[7454, 7022, 7022],

/***/ 7022:
/*!****************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/~/css-animation/~/component-classes/~/component-indexof/index.js ***!
  \****************************************************************************************************************************************************************************************************/
1036,

/***/ 7023:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-animate/lib/util.js ***!
  \***********************************************************************************************************************************************/
1037,

/***/ 7024:
/*!****************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/PopupInner.js ***!
  \****************************************************************************************************************************************/
[7455, 5963, 7025],

/***/ 7025:
/*!*******************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/LazyRenderBox.js ***!
  \*******************************************************************************************************************************************/
[7456, 7026, 5963],

/***/ 7026:
/*!***********************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \***********************************************************************************************************************************************/
286,

/***/ 7027:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/lib/utils.js ***!
  \***********************************************************************************************************************************/
[7457, 6944],

/***/ 7028:
/*!***************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/rc-tooltip/~/rc-trigger/~/rc-util/lib/getContainerRenderMixin.js ***!
  \***************************************************************************************************************************************************************/
[7458, 5993],

/***/ 7029:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/Steps.js ***!
  \*********************************************************************************************************/
[7459, 6886, 5963, 6988, 7030],

/***/ 7030:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/~/warning/browser.js ***!
  \*****************************************************************************************************************/
352,

/***/ 7031:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/lib/Marks.js ***!
  \*********************************************************************************************************/
[7460, 6944, 6953, 6886, 5963, 6988],

/***/ 7032:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/assets/index.css ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./index.css */ 7033);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./index.css", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./index.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 7033:
/*!****************************************************************************************************************************!*\
  !*** ./~/css-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/rc-slider/assets/index.css ***!
  \****************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".rc-slider {\n  position: relative;\n  height: 4px;\n  width: 100%;\n  border-radius: 6px;\n  background-color: #e9e9e9;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-slider-track {\n  position: absolute;\n  left: 0;\n  height: 4px;\n  border-radius: 6px;\n  background-color: #abe2fb;\n}\n.rc-slider-handle {\n  position: absolute;\n  margin-left: -7px;\n  margin-top: -5px;\n  width: 14px;\n  height: 14px;\n  cursor: pointer;\n  border-radius: 50%;\n  border: solid 2px #96dbfa;\n  background-color: #fff;\n}\n.rc-slider-handle:hover {\n  border-color: #57c5f7;\n}\n.rc-slider-handle-active:active {\n  border-color: #57c5f7;\n  box-shadow: 0 0 5px #57c5f7;\n}\n.rc-slider-mark {\n  position: absolute;\n  top: 10px;\n  left: 0;\n  width: 100%;\n  font-size: 12px;\n}\n.rc-slider-mark-text {\n  position: absolute;\n  display: inline-block;\n  vertical-align: middle;\n  text-align: center;\n  cursor: pointer;\n  color: #999;\n}\n.rc-slider-mark-text-active {\n  color: #666;\n}\n.rc-slider-step {\n  position: absolute;\n  width: 100%;\n  height: 4px;\n  background: transparent;\n}\n.rc-slider-dot {\n  position: absolute;\n  bottom: -2px;\n  margin-left: -4px;\n  width: 8px;\n  height: 8px;\n  border: 2px solid #e9e9e9;\n  background-color: #fff;\n  cursor: pointer;\n  border-radius: 50%;\n  vertical-align: middle;\n}\n.rc-slider-dot:first-child {\n  margin-left: -4px;\n}\n.rc-slider-dot:last-child {\n  margin-left: -4px;\n}\n.rc-slider-dot-active {\n  border-color: #96dbfa;\n}\n.rc-slider-disabled {\n  background-color: #e9e9e9;\n}\n.rc-slider-disabled .rc-slider-track {\n  background-color: #ccc;\n}\n.rc-slider-disabled .rc-slider-handle,\n.rc-slider-disabled .rc-slider-dot {\n  border-color: #ccc;\n  background-color: #fff;\n  cursor: not-allowed;\n}\n.rc-slider-disabled .rc-slider-mark-text,\n.rc-slider-disabled .rc-slider-dot {\n  cursor: not-allowed !important;\n}\n.rc-slider-vertical {\n  width: 4px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-track {\n  bottom: 0;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-handle {\n  position: absolute;\n  margin-left: -5px;\n  margin-bottom: -7px;\n}\n.rc-slider-vertical .rc-slider-mark {\n  top: 0;\n  left: 10px;\n  height: 100%;\n}\n.rc-slider-vertical .rc-slider-step {\n  height: 100%;\n  width: 4px;\n}\n.rc-slider-vertical .rc-slider-dot {\n  left: 2px;\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:first-child {\n  margin-bottom: -4px;\n}\n.rc-slider-vertical .rc-slider-dot:last-child {\n  margin-bottom: -4px;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-duration: .3s;\n          animation-duration: .3s;\n  -webkit-animation-fill-mode: both;\n          animation-fill-mode: both;\n  display: block !important;\n  -webkit-animation-play-state: paused;\n          animation-play-state: paused;\n}\n.rc-slider-tooltip-zoom-down-enter.rc-slider-tooltip-zoom-down-enter-active,\n.rc-slider-tooltip-zoom-down-appear.rc-slider-tooltip-zoom-down-appear-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownIn;\n          animation-name: rcSliderTooltipZoomDownIn;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-leave.rc-slider-tooltip-zoom-down-leave-active {\n  -webkit-animation-name: rcSliderTooltipZoomDownOut;\n          animation-name: rcSliderTooltipZoomDownOut;\n  -webkit-animation-play-state: running;\n          animation-play-state: running;\n}\n.rc-slider-tooltip-zoom-down-enter,\n.rc-slider-tooltip-zoom-down-appear {\n  -webkit-transform: scale(0, 0);\n          transform: scale(0, 0);\n  -webkit-animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n          animation-timing-function: cubic-bezier(0.23, 1, 0.32, 1);\n}\n.rc-slider-tooltip-zoom-down-leave {\n  -webkit-animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n          animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);\n}\n@-webkit-keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@keyframes rcSliderTooltipZoomDownIn {\n  0% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n  100% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n}\n@-webkit-keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n@keyframes rcSliderTooltipZoomDownOut {\n  0% {\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(1, 1);\n            transform: scale(1, 1);\n  }\n  100% {\n    opacity: 0;\n    -webkit-transform-origin: 50% 100%;\n            transform-origin: 50% 100%;\n    -webkit-transform: scale(0, 0);\n            transform: scale(0, 0);\n  }\n}\n.rc-tooltip {\n  position: absolute;\n  left: -9999px;\n  top: -9999px;\n  visibility: visible;\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip * {\n  box-sizing: border-box;\n  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);\n}\n.rc-tooltip-hidden {\n  display: none;\n}\n.rc-tooltip-placement-top {\n  padding: 4px 0 8px 0;\n}\n.rc-tooltip-inner {\n  padding: 6px 2px;\n  min-width: 24px;\n  height: 24px;\n  font-size: 12px;\n  line-height: 1;\n  color: #fff;\n  text-align: center;\n  text-decoration: none;\n  background-color: #6c6c6c;\n  border-radius: 6px;\n  box-shadow: 0 0 4px #d9d9d9;\n}\n.rc-tooltip-arrow {\n  position: absolute;\n  width: 0;\n  height: 0;\n  border-color: transparent;\n  border-style: solid;\n}\n.rc-tooltip-placement-top .rc-tooltip-arrow {\n  bottom: 4px;\n  left: 50%;\n  margin-left: -4px;\n  border-width: 4px 4px 0;\n  border-top-color: #6c6c6c;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 7034:
/*!************************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.less ***!
  \************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../../../../../../~/css-loader!./../../../../../../../~/less-loader!./CoexpressionOption.less */ 7035);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../../../../../../~/style-loader/addStyles.js */ 235)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./CoexpressionOption.less", function() {
				var newContent = require("!!./../../../../../../../node_modules/css-loader/index.js!./../../../../../../../node_modules/less-loader/index.js!./CoexpressionOption.less");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 7035:
/*!*******************************************************************************************************************************************************************!*\
  !*** ./~/css-loader!./~/less-loader!./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/coexpression/CoexpressionOption.less ***!
  \*******************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../../../../../../~/css-loader/lib/css-base.js */ 234)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDisplayCoexpressionOffer {\n  margin-top: 30px;\n}\n.gxaDisplayCoexpressionOffer .gxaSlider {\n  width: 250px;\n  margin: 15px;\n  padding-bottom: 20px;\n}\n.gxaDisplayCoexpressionOffer p {\n  font-size: 93%;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 7036:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/Events.js ***!
  \*********************************************************************************************************/
[7887, 7037],

/***/ 7037:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/lodash/lodash.js ***!
  \***************************************************************************************************/
839,

/***/ 7038:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/manipulate/Manipulators.js ***!
  \***************************************************************************************************************/
1690,

/***/ 7039:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/show/BoxplotCanvas.jsx ***!
  \***********************************************************************************************************/
[7888, 5963, 6822, 7040],

/***/ 7040:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/highcharts/highcharts-more.js ***!
  \****************************************************************************************************************/
1692,

/***/ 7041:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/layout/jsonPayloadPropTypes.js ***!
  \*******************************************************************************************************************/
[7889, 5963, 6798],

/***/ 7042:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/main.js ***!
  \*************************************************************************************************/
[7890, 7043, 7044, 7052, 7053, 7054, 7063],

/***/ 7043:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/chartConfiguration.js ***!
  \***************************************************************************************************************/
[7891, 6798, 6880],

/***/ 7044:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapData.js ***!
  \********************************************************************************************************/
[7892, 7045, 7046],

/***/ 7045:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapDataSeries.js ***!
  \**************************************************************************************************************/
[7893, 7037, 6798],

/***/ 7046:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapAxisCategories.js ***!
  \******************************************************************************************************************/
[7894, 179, 186, 6798, 7047],

/***/ 7047:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets ^\.\/.*\-icon\.png$ ***!
  \***********************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./gsea_go-icon.png": 7048,
		"./gsea_interpro-icon.png": 7049,
		"./gsea_reactome-icon.png": 7050,
		"./ma-plot-icon.png": 7051
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
	webpackContext.id = 7047;


/***/ },

/***/ 7048:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_go-icon.png ***!
  \********************************************************************************************************/
1700,

/***/ 7049:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_interpro-icon.png ***!
  \**************************************************************************************************************/
1701,

/***/ 7050:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/gsea_reactome-icon.png ***!
  \**************************************************************************************************************/
1702,

/***/ 7051:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/assets/ma-plot-icon.png ***!
  \********************************************************************************************************/
1703,

/***/ 7052:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/boxplotData.js ***!
  \********************************************************************************************************/
[7895, 6798],

/***/ 7053:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapOrderings.js ***!
  \*************************************************************************************************************/
[7896, 7037, 6798],

/***/ 7054:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapColourAxis.js ***!
  \**************************************************************************************************************/
[7897, 7055, 6798],

/***/ 7055:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/index.js ***!
  \*************************************************************************************************/
[7418, 7056, 7057, 7061],

/***/ 7056:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/clone/clone.js ***!
  \*********************************************************************************************************/
821,

/***/ 7057:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-convert/index.js ***!
  \*****************************************************************************************************************/
[7419, 7058, 7060],

/***/ 7058:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-convert/conversions.js ***!
  \***********************************************************************************************************************/
[7420, 7059],

/***/ 7059:
/*!******************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-convert/~/color-name/index.js ***!
  \******************************************************************************************************************************/
828,

/***/ 7060:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-convert/route.js ***!
  \*****************************************************************************************************************/
[7421, 7058],

/***/ 7061:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-string/color-string.js ***!
  \***********************************************************************************************************************/
[7422, 7062],

/***/ 7062:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/~/color/~/color-string/~/color-name/index.js ***!
  \*****************************************************************************************************************************/
828,

/***/ 7063:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/heatmap-highcharts/~/expression-atlas-heatmap-highcharts/src/load/heatmapFilters.js ***!
  \***********************************************************************************************************/
[7898, 7037]

});
//# sourceMappingURL=expressionAtlasHeatmapHighcharts.bundle.js.map