var markerGenesSearchResults =
webpackJsonp_name_([3],Array(22).concat([
/* 22 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_root.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var freeGlobal = __webpack_require__(/*! ./_freeGlobal */ 434);

/** Detect free variable `self`. */
var freeSelf = (typeof self === 'undefined' ? 'undefined' : _typeof(self)) == 'object' && self && self.Object === Object && self;

/** Used as a reference to the global object. */
var root = freeGlobal || freeSelf || Function('return this')();

module.exports = root;

/***/ }),
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isArray.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Checks if `value` is classified as an `Array` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an array, else `false`.
 * @example
 *
 * _.isArray([1, 2, 3]);
 * // => true
 *
 * _.isArray(document.body.children);
 * // => false
 *
 * _.isArray('abc');
 * // => false
 *
 * _.isArray(_.noop);
 * // => false
 */
var isArray = Array.isArray;

module.exports = isArray;

/***/ }),
/* 29 */,
/* 30 */,
/* 31 */,
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */,
/* 45 */,
/* 46 */,
/* 47 */,
/* 48 */,
/* 49 */,
/* 50 */,
/* 51 */,
/* 52 */,
/* 53 */,
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isObject.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * Checks if `value` is the
 * [language type](http://www.ecma-international.org/ecma-262/7.0/#sec-ecmascript-language-types)
 * of `Object`. (e.g. arrays, functions, objects, regexes, `new Number(0)`, and `new String('')`)
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an object, else `false`.
 * @example
 *
 * _.isObject({});
 * // => true
 *
 * _.isObject([1, 2, 3]);
 * // => true
 *
 * _.isObject(_.noop);
 * // => true
 *
 * _.isObject(null);
 * // => false
 */
function isObject(value) {
  var type = typeof value === 'undefined' ? 'undefined' : _typeof(value);
  return value != null && (type == 'object' || type == 'function');
}

module.exports = isObject;

/***/ }),
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */,
/* 68 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getNative.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsNative = __webpack_require__(/*! ./_baseIsNative */ 891),
    getValue = __webpack_require__(/*! ./_getValue */ 896);

/**
 * Gets the native function at `key` of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {string} key The key of the method to get.
 * @returns {*} Returns the function if it's native, else `undefined`.
 */
function getNative(object, key) {
  var value = getValue(object, key);
  return baseIsNative(value) ? value : undefined;
}

module.exports = getNative;

/***/ }),
/* 69 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isObjectLike.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * Checks if `value` is object-like. A value is object-like if it's not `null`
 * and has a `typeof` result of "object".
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is object-like, else `false`.
 * @example
 *
 * _.isObjectLike({});
 * // => true
 *
 * _.isObjectLike([1, 2, 3]);
 * // => true
 *
 * _.isObjectLike(_.noop);
 * // => false
 *
 * _.isObjectLike(null);
 * // => false
 */
function isObjectLike(value) {
  return value != null && (typeof value === 'undefined' ? 'undefined' : _typeof(value)) == 'object';
}

module.exports = isObjectLike;

/***/ }),
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseGetTag.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93),
    getRawTag = __webpack_require__(/*! ./_getRawTag */ 892),
    objectToString = __webpack_require__(/*! ./_objectToString */ 893);

/** `Object#toString` result references. */
var nullTag = '[object Null]',
    undefinedTag = '[object Undefined]';

/** Built-in value references. */
var symToStringTag = _Symbol ? _Symbol.toStringTag : undefined;

/**
 * The base implementation of `getTag` without fallbacks for buggy environments.
 *
 * @private
 * @param {*} value The value to query.
 * @returns {string} Returns the `toStringTag`.
 */
function baseGetTag(value) {
    if (value == null) {
        return value === undefined ? undefinedTag : nullTag;
    }
    return symToStringTag && symToStringTag in Object(value) ? getRawTag(value) : objectToString(value);
}

module.exports = baseGetTag;

/***/ }),
/* 93 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Symbol.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var root = __webpack_require__(/*! ./_root */ 22);

/** Built-in value references. */
var _Symbol = root.Symbol;

module.exports = _Symbol;

/***/ }),
/* 94 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_toKey.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isSymbol = __webpack_require__(/*! ./isSymbol */ 112);

/** Used as references for various `Number` constants. */
var INFINITY = 1 / 0;

/**
 * Converts `value` to a string key if it's not a string or symbol.
 *
 * @private
 * @param {*} value The value to inspect.
 * @returns {string|symbol} Returns the key.
 */
function toKey(value) {
  if (typeof value == 'string' || isSymbol(value)) {
    return value;
  }
  var result = value + '';
  return result == '0' && 1 / value == -INFINITY ? '-0' : result;
}

module.exports = toKey;

/***/ }),
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */,
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */,
/* 103 */,
/* 104 */,
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */,
/* 110 */,
/* 111 */,
/* 112 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isSymbol.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/** `Object#toString` result references. */
var symbolTag = '[object Symbol]';

/**
 * Checks if `value` is classified as a `Symbol` primitive or object.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a symbol, else `false`.
 * @example
 *
 * _.isSymbol(Symbol.iterator);
 * // => true
 *
 * _.isSymbol('abc');
 * // => false
 */
function isSymbol(value) {
    return (typeof value === 'undefined' ? 'undefined' : _typeof(value)) == 'symbol' || isObjectLike(value) && baseGetTag(value) == symbolTag;
}

module.exports = isSymbol;

/***/ }),
/* 113 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_copyObject.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var assignValue = __webpack_require__(/*! ./_assignValue */ 450),
    baseAssignValue = __webpack_require__(/*! ./_baseAssignValue */ 451);

/**
 * Copies properties of `source` to `object`.
 *
 * @private
 * @param {Object} source The object to copy properties from.
 * @param {Array} props The property identifiers to copy.
 * @param {Object} [object={}] The object to copy properties to.
 * @param {Function} [customizer] The function to customize copied values.
 * @returns {Object} Returns `object`.
 */
function copyObject(source, props, object, customizer) {
  var isNew = !object;
  object || (object = {});

  var index = -1,
      length = props.length;

  while (++index < length) {
    var key = props[index];

    var newValue = customizer ? customizer(object[key], source[key], key, object, source) : undefined;

    if (newValue === undefined) {
      newValue = source[key];
    }
    if (isNew) {
      baseAssignValue(object, key, newValue);
    } else {
      assignValue(object, key, newValue);
    }
  }
  return object;
}

module.exports = copyObject;

/***/ }),
/* 114 */,
/* 115 */,
/* 116 */,
/* 117 */,
/* 118 */,
/* 119 */,
/* 120 */,
/* 121 */,
/* 122 */,
/* 123 */,
/* 124 */,
/* 125 */,
/* 126 */,
/* 127 */,
/* 128 */,
/* 129 */,
/* 130 */,
/* 131 */,
/* 132 */,
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createCtor.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseCreate = __webpack_require__(/*! ./_baseCreate */ 142),
    isObject = __webpack_require__(/*! ./isObject */ 58);

/**
 * Creates a function that produces an instance of `Ctor` regardless of
 * whether it was invoked as part of a `new` expression or by `call` or `apply`.
 *
 * @private
 * @param {Function} Ctor The constructor to wrap.
 * @returns {Function} Returns the new wrapped function.
 */
function createCtor(Ctor) {
  return function () {
    // Use a `switch` statement to work with class constructors. See
    // http://ecma-international.org/ecma-262/7.0/#sec-ecmascript-function-objects-call-thisargument-argumentslist
    // for more details.
    var args = arguments;
    switch (args.length) {
      case 0:
        return new Ctor();
      case 1:
        return new Ctor(args[0]);
      case 2:
        return new Ctor(args[0], args[1]);
      case 3:
        return new Ctor(args[0], args[1], args[2]);
      case 4:
        return new Ctor(args[0], args[1], args[2], args[3]);
      case 5:
        return new Ctor(args[0], args[1], args[2], args[3], args[4]);
      case 6:
        return new Ctor(args[0], args[1], args[2], args[3], args[4], args[5]);
      case 7:
        return new Ctor(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
    }
    var thisBinding = baseCreate(Ctor.prototype),
        result = Ctor.apply(thisBinding, args);

    // Mimic the constructor's `return` behavior.
    // See https://es5.github.io/#x13.2.2 for more details.
    return isObject(result) ? result : thisBinding;
  };
}

module.exports = createCtor;

/***/ }),
/* 142 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseCreate.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(/*! ./isObject */ 58);

/** Built-in value references. */
var objectCreate = Object.create;

/**
 * The base implementation of `_.create` without support for assigning
 * properties to the created object.
 *
 * @private
 * @param {Object} proto The object to inherit from.
 * @returns {Object} Returns the new object.
 */
var baseCreate = function () {
  function object() {}
  return function (proto) {
    if (!isObject(proto)) {
      return {};
    }
    if (objectCreate) {
      return objectCreate(proto);
    }
    object.prototype = proto;
    var result = new object();
    object.prototype = undefined;
    return result;
  };
}();

module.exports = baseCreate;

/***/ }),
/* 143 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_copyArray.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Copies the values of `source` to `array`.
 *
 * @private
 * @param {Array} source The array to copy values from.
 * @param {Array} [array=[]] The array to copy values to.
 * @returns {Array} Returns `array`.
 */
function copyArray(source, array) {
  var index = -1,
      length = source.length;

  array || (array = Array(length));
  while (++index < length) {
    array[index] = source[index];
  }
  return array;
}

module.exports = copyArray;

/***/ }),
/* 144 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/keys.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayLikeKeys = __webpack_require__(/*! ./_arrayLikeKeys */ 452),
    baseKeys = __webpack_require__(/*! ./_baseKeys */ 454),
    isArrayLike = __webpack_require__(/*! ./isArrayLike */ 456);

/**
 * Creates an array of the own enumerable property names of `object`.
 *
 * **Note:** Non-object values are coerced to objects. See the
 * [ES spec](http://ecma-international.org/ecma-262/7.0/#sec-object.keys)
 * for more details.
 *
 * @static
 * @since 0.1.0
 * @memberOf _
 * @category Object
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 * @example
 *
 * function Foo() {
 *   this.a = 1;
 *   this.b = 2;
 * }
 *
 * Foo.prototype.c = 3;
 *
 * _.keys(new Foo);
 * // => ['a', 'b'] (iteration order is not guaranteed)
 *
 * _.keys('hi');
 * // => ['0', '1']
 */
function keys(object) {
  return isArrayLike(object) ? arrayLikeKeys(object) : baseKeys(object);
}

module.exports = keys;

/***/ }),
/* 145 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_ListCache.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var listCacheClear = __webpack_require__(/*! ./_listCacheClear */ 929),
    listCacheDelete = __webpack_require__(/*! ./_listCacheDelete */ 930),
    listCacheGet = __webpack_require__(/*! ./_listCacheGet */ 931),
    listCacheHas = __webpack_require__(/*! ./_listCacheHas */ 932),
    listCacheSet = __webpack_require__(/*! ./_listCacheSet */ 933);

/**
 * Creates an list cache object.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function ListCache(entries) {
    var index = -1,
        length = entries == null ? 0 : entries.length;

    this.clear();
    while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
    }
}

// Add methods to `ListCache`.
ListCache.prototype.clear = listCacheClear;
ListCache.prototype['delete'] = listCacheDelete;
ListCache.prototype.get = listCacheGet;
ListCache.prototype.has = listCacheHas;
ListCache.prototype.set = listCacheSet;

module.exports = ListCache;

/***/ }),
/* 146 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_assocIndexOf.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var eq = __webpack_require__(/*! ./eq */ 234);

/**
 * Gets the index at which the `key` is found in `array` of key-value pairs.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {*} key The key to search for.
 * @returns {number} Returns the index of the matched value, else `-1`.
 */
function assocIndexOf(array, key) {
  var length = array.length;
  while (length--) {
    if (eq(array[length][0], key)) {
      return length;
    }
  }
  return -1;
}

module.exports = assocIndexOf;

/***/ }),
/* 147 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_nativeCreate.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68);

/* Built-in method references that are verified to be native. */
var nativeCreate = getNative(Object, 'create');

module.exports = nativeCreate;

/***/ }),
/* 148 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getMapData.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isKeyable = __webpack_require__(/*! ./_isKeyable */ 947);

/**
 * Gets the data for `map`.
 *
 * @private
 * @param {Object} map The map to query.
 * @param {string} key The reference key.
 * @returns {*} Returns the map data.
 */
function getMapData(map, key) {
  var data = map.__data__;
  return isKeyable(key) ? data[typeof key == 'string' ? 'string' : 'hash'] : data.map;
}

module.exports = getMapData;

/***/ }),
/* 149 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_castPath.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isArray = __webpack_require__(/*! ./isArray */ 28),
    isKey = __webpack_require__(/*! ./_isKey */ 248),
    stringToPath = __webpack_require__(/*! ./_stringToPath */ 472),
    toString = __webpack_require__(/*! ./toString */ 473);

/**
 * Casts `value` to a path array if it's not one.
 *
 * @private
 * @param {*} value The value to inspect.
 * @param {Object} [object] The object to query keys on.
 * @returns {Array} Returns the cast property path array.
 */
function castPath(value, object) {
  if (isArray(value)) {
    return value;
  }
  return isKey(value, object) ? [value] : stringToPath(toString(value));
}

module.exports = castPath;

/***/ }),
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */,
/* 223 */,
/* 224 */,
/* 225 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createWrap.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseSetData = __webpack_require__(/*! ./_baseSetData */ 431),
    createBind = __webpack_require__(/*! ./_createBind */ 897),
    createCurry = __webpack_require__(/*! ./_createCurry */ 898),
    createHybrid = __webpack_require__(/*! ./_createHybrid */ 436),
    createPartial = __webpack_require__(/*! ./_createPartial */ 917),
    getData = __webpack_require__(/*! ./_getData */ 440),
    mergeData = __webpack_require__(/*! ./_mergeData */ 918),
    setData = __webpack_require__(/*! ./_setData */ 442),
    setWrapToString = __webpack_require__(/*! ./_setWrapToString */ 444),
    toInteger = __webpack_require__(/*! ./toInteger */ 448);

/** Error message constants. */
var FUNC_ERROR_TEXT = 'Expected a function';

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1,
    WRAP_BIND_KEY_FLAG = 2,
    WRAP_CURRY_FLAG = 8,
    WRAP_CURRY_RIGHT_FLAG = 16,
    WRAP_PARTIAL_FLAG = 32,
    WRAP_PARTIAL_RIGHT_FLAG = 64;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMax = Math.max;

/**
 * Creates a function that either curries or invokes `func` with optional
 * `this` binding and partially applied arguments.
 *
 * @private
 * @param {Function|string} func The function or method name to wrap.
 * @param {number} bitmask The bitmask flags.
 *    1 - `_.bind`
 *    2 - `_.bindKey`
 *    4 - `_.curry` or `_.curryRight` of a bound function
 *    8 - `_.curry`
 *   16 - `_.curryRight`
 *   32 - `_.partial`
 *   64 - `_.partialRight`
 *  128 - `_.rearg`
 *  256 - `_.ary`
 *  512 - `_.flip`
 * @param {*} [thisArg] The `this` binding of `func`.
 * @param {Array} [partials] The arguments to be partially applied.
 * @param {Array} [holders] The `partials` placeholder indexes.
 * @param {Array} [argPos] The argument positions of the new function.
 * @param {number} [ary] The arity cap of `func`.
 * @param {number} [arity] The arity of `func`.
 * @returns {Function} Returns the new wrapped function.
 */
function createWrap(func, bitmask, thisArg, partials, holders, argPos, ary, arity) {
  var isBindKey = bitmask & WRAP_BIND_KEY_FLAG;
  if (!isBindKey && typeof func != 'function') {
    throw new TypeError(FUNC_ERROR_TEXT);
  }
  var length = partials ? partials.length : 0;
  if (!length) {
    bitmask &= ~(WRAP_PARTIAL_FLAG | WRAP_PARTIAL_RIGHT_FLAG);
    partials = holders = undefined;
  }
  ary = ary === undefined ? ary : nativeMax(toInteger(ary), 0);
  arity = arity === undefined ? arity : toInteger(arity);
  length -= holders ? holders.length : 0;

  if (bitmask & WRAP_PARTIAL_RIGHT_FLAG) {
    var partialsRight = partials,
        holdersRight = holders;

    partials = holders = undefined;
  }
  var data = isBindKey ? undefined : getData(func);

  var newData = [func, bitmask, thisArg, partials, holders, partialsRight, holdersRight, argPos, ary, arity];

  if (data) {
    mergeData(newData, data);
  }
  func = newData[0];
  bitmask = newData[1];
  thisArg = newData[2];
  partials = newData[3];
  holders = newData[4];
  arity = newData[9] = newData[9] === undefined ? isBindKey ? 0 : func.length : nativeMax(newData[9] - length, 0);

  if (!arity && bitmask & (WRAP_CURRY_FLAG | WRAP_CURRY_RIGHT_FLAG)) {
    bitmask &= ~(WRAP_CURRY_FLAG | WRAP_CURRY_RIGHT_FLAG);
  }
  if (!bitmask || bitmask == WRAP_BIND_FLAG) {
    var result = createBind(func, bitmask, thisArg);
  } else if (bitmask == WRAP_CURRY_FLAG || bitmask == WRAP_CURRY_RIGHT_FLAG) {
    result = createCurry(func, bitmask, arity);
  } else if ((bitmask == WRAP_PARTIAL_FLAG || bitmask == (WRAP_BIND_FLAG | WRAP_PARTIAL_FLAG)) && !holders.length) {
    result = createPartial(func, bitmask, thisArg, partials);
  } else {
    result = createHybrid.apply(undefined, newData);
  }
  var setter = data ? baseSetData : setData;
  return setWrapToString(setter(result, newData), func, bitmask);
}

module.exports = createWrap;

/***/ }),
/* 226 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/identity.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * This method returns the first argument it receives.
 *
 * @static
 * @since 0.1.0
 * @memberOf _
 * @category Util
 * @param {*} value Any value.
 * @returns {*} Returns `value`.
 * @example
 *
 * var object = { 'a': 1 };
 *
 * console.log(_.identity(object) === object);
 * // => true
 */
function identity(value) {
  return value;
}

module.exports = identity;

/***/ }),
/* 227 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isFunction.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    isObject = __webpack_require__(/*! ./isObject */ 58);

/** `Object#toString` result references. */
var asyncTag = '[object AsyncFunction]',
    funcTag = '[object Function]',
    genTag = '[object GeneratorFunction]',
    proxyTag = '[object Proxy]';

/**
 * Checks if `value` is classified as a `Function` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a function, else `false`.
 * @example
 *
 * _.isFunction(_);
 * // => true
 *
 * _.isFunction(/abc/);
 * // => false
 */
function isFunction(value) {
    if (!isObject(value)) {
        return false;
    }
    // The use of `Object#toString` avoids issues with the `typeof` operator
    // in Safari 9 which returns 'object' for typed arrays and other constructors.
    var tag = baseGetTag(value);
    return tag == funcTag || tag == genTag || tag == asyncTag || tag == proxyTag;
}

module.exports = isFunction;

/***/ }),
/* 228 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_apply.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A faster alternative to `Function#apply`, this function invokes `func`
 * with the `this` binding of `thisArg` and the arguments of `args`.
 *
 * @private
 * @param {Function} func The function to invoke.
 * @param {*} thisArg The `this` binding of `func`.
 * @param {Array} args The arguments to invoke `func` with.
 * @returns {*} Returns the result of `func`.
 */
function apply(func, thisArg, args) {
  switch (args.length) {
    case 0:
      return func.call(thisArg);
    case 1:
      return func.call(thisArg, args[0]);
    case 2:
      return func.call(thisArg, args[0], args[1]);
    case 3:
      return func.call(thisArg, args[0], args[1], args[2]);
  }
  return func.apply(thisArg, args);
}

module.exports = apply;

/***/ }),
/* 229 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_LazyWrapper.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseCreate = __webpack_require__(/*! ./_baseCreate */ 142),
    baseLodash = __webpack_require__(/*! ./_baseLodash */ 230);

/** Used as references for the maximum length and index of an array. */
var MAX_ARRAY_LENGTH = 4294967295;

/**
 * Creates a lazy wrapper object which wraps `value` to enable lazy evaluation.
 *
 * @private
 * @constructor
 * @param {*} value The value to wrap.
 */
function LazyWrapper(value) {
  this.__wrapped__ = value;
  this.__actions__ = [];
  this.__dir__ = 1;
  this.__filtered__ = false;
  this.__iteratees__ = [];
  this.__takeCount__ = MAX_ARRAY_LENGTH;
  this.__views__ = [];
}

// Ensure `LazyWrapper` is an instance of `baseLodash`.
LazyWrapper.prototype = baseCreate(baseLodash.prototype);
LazyWrapper.prototype.constructor = LazyWrapper;

module.exports = LazyWrapper;

/***/ }),
/* 230 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseLodash.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The function whose prototype chain sequence wrappers inherit from.
 *
 * @private
 */
function baseLodash() {
  // No operation performed.
}

module.exports = baseLodash;

/***/ }),
/* 231 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayEach.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.forEach` for arrays without support for
 * iteratee shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} iteratee The function invoked per iteration.
 * @returns {Array} Returns `array`.
 */
function arrayEach(array, iteratee) {
  var index = -1,
      length = array == null ? 0 : array.length;

  while (++index < length) {
    if (iteratee(array[index], index, array) === false) {
      break;
    }
  }
  return array;
}

module.exports = arrayEach;

/***/ }),
/* 232 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isIndex.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used as references for various `Number` constants. */
var MAX_SAFE_INTEGER = 9007199254740991;

/** Used to detect unsigned integer values. */
var reIsUint = /^(?:0|[1-9]\d*)$/;

/**
 * Checks if `value` is a valid array-like index.
 *
 * @private
 * @param {*} value The value to check.
 * @param {number} [length=MAX_SAFE_INTEGER] The upper bounds of a valid index.
 * @returns {boolean} Returns `true` if `value` is a valid index, else `false`.
 */
function isIndex(value, length) {
  length = length == null ? MAX_SAFE_INTEGER : length;
  return !!length && (typeof value == 'number' || reIsUint.test(value)) && value > -1 && value % 1 == 0 && value < length;
}

module.exports = isIndex;

/***/ }),
/* 233 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_replaceHolders.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used as the internal argument placeholder. */
var PLACEHOLDER = '__lodash_placeholder__';

/**
 * Replaces all `placeholder` elements in `array` with an internal placeholder
 * and returns an array of their indexes.
 *
 * @private
 * @param {Array} array The array to modify.
 * @param {*} placeholder The placeholder to replace.
 * @returns {Array} Returns the new array of placeholder indexes.
 */
function replaceHolders(array, placeholder) {
  var index = -1,
      length = array.length,
      resIndex = 0,
      result = [];

  while (++index < length) {
    var value = array[index];
    if (value === placeholder || value === PLACEHOLDER) {
      array[index] = PLACEHOLDER;
      result[resIndex++] = index;
    }
  }
  return result;
}

module.exports = replaceHolders;

/***/ }),
/* 234 */
/*!********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/eq.js ***!
  \********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Performs a
 * [`SameValueZero`](http://ecma-international.org/ecma-262/7.0/#sec-samevaluezero)
 * comparison between two values to determine if they are equivalent.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to compare.
 * @param {*} other The other value to compare.
 * @returns {boolean} Returns `true` if the values are equivalent, else `false`.
 * @example
 *
 * var object = { 'a': 1 };
 * var other = { 'a': 1 };
 *
 * _.eq(object, object);
 * // => true
 *
 * _.eq(object, other);
 * // => false
 *
 * _.eq('a', 'a');
 * // => true
 *
 * _.eq('a', Object('a'));
 * // => false
 *
 * _.eq(NaN, NaN);
 * // => true
 */
function eq(value, other) {
  return value === other || value !== value && other !== other;
}

module.exports = eq;

/***/ }),
/* 235 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isArguments.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsArguments = __webpack_require__(/*! ./_baseIsArguments */ 922),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Built-in value references. */
var propertyIsEnumerable = objectProto.propertyIsEnumerable;

/**
 * Checks if `value` is likely an `arguments` object.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an `arguments` object,
 *  else `false`.
 * @example
 *
 * _.isArguments(function() { return arguments; }());
 * // => true
 *
 * _.isArguments([1, 2, 3]);
 * // => false
 */
var isArguments = baseIsArguments(function () {
    return arguments;
}()) ? baseIsArguments : function (value) {
    return isObjectLike(value) && hasOwnProperty.call(value, 'callee') && !propertyIsEnumerable.call(value, 'callee');
};

module.exports = isArguments;

/***/ }),
/* 236 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isBuffer.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var root = __webpack_require__(/*! ./_root */ 22),
    stubFalse = __webpack_require__(/*! ./stubFalse */ 923);

/** Detect free variable `exports`. */
var freeExports = ( false ? 'undefined' : _typeof(exports)) == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && ( false ? 'undefined' : _typeof(module)) == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Built-in value references. */
var Buffer = moduleExports ? root.Buffer : undefined;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeIsBuffer = Buffer ? Buffer.isBuffer : undefined;

/**
 * Checks if `value` is a buffer.
 *
 * @static
 * @memberOf _
 * @since 4.3.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a buffer, else `false`.
 * @example
 *
 * _.isBuffer(new Buffer(2));
 * // => true
 *
 * _.isBuffer(new Uint8Array(2));
 * // => false
 */
var isBuffer = nativeIsBuffer || stubFalse;

module.exports = isBuffer;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 237 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isLength.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used as references for various `Number` constants. */
var MAX_SAFE_INTEGER = 9007199254740991;

/**
 * Checks if `value` is a valid array-like length.
 *
 * **Note:** This method is loosely based on
 * [`ToLength`](http://ecma-international.org/ecma-262/7.0/#sec-tolength).
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a valid length, else `false`.
 * @example
 *
 * _.isLength(3);
 * // => true
 *
 * _.isLength(Number.MIN_VALUE);
 * // => false
 *
 * _.isLength(Infinity);
 * // => false
 *
 * _.isLength('3');
 * // => false
 */
function isLength(value) {
  return typeof value == 'number' && value > -1 && value % 1 == 0 && value <= MAX_SAFE_INTEGER;
}

module.exports = isLength;

/***/ }),
/* 238 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isPrototype.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used for built-in method references. */
var objectProto = Object.prototype;

/**
 * Checks if `value` is likely a prototype object.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a prototype, else `false`.
 */
function isPrototype(value) {
  var Ctor = value && value.constructor,
      proto = typeof Ctor == 'function' && Ctor.prototype || objectProto;

  return value === proto;
}

module.exports = isPrototype;

/***/ }),
/* 239 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseClone.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Stack = __webpack_require__(/*! ./_Stack */ 240),
    arrayEach = __webpack_require__(/*! ./_arrayEach */ 231),
    assignValue = __webpack_require__(/*! ./_assignValue */ 450),
    baseAssign = __webpack_require__(/*! ./_baseAssign */ 449),
    baseAssignIn = __webpack_require__(/*! ./_baseAssignIn */ 951),
    cloneBuffer = __webpack_require__(/*! ./_cloneBuffer */ 954),
    copyArray = __webpack_require__(/*! ./_copyArray */ 143),
    copySymbols = __webpack_require__(/*! ./_copySymbols */ 955),
    copySymbolsIn = __webpack_require__(/*! ./_copySymbolsIn */ 957),
    getAllKeys = __webpack_require__(/*! ./_getAllKeys */ 460),
    getAllKeysIn = __webpack_require__(/*! ./_getAllKeysIn */ 462),
    getTag = __webpack_require__(/*! ./_getTag */ 463),
    initCloneArray = __webpack_require__(/*! ./_initCloneArray */ 961),
    initCloneByTag = __webpack_require__(/*! ./_initCloneByTag */ 962),
    initCloneObject = __webpack_require__(/*! ./_initCloneObject */ 971),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isBuffer = __webpack_require__(/*! ./isBuffer */ 236),
    isObject = __webpack_require__(/*! ./isObject */ 58),
    keys = __webpack_require__(/*! ./keys */ 144);

/** Used to compose bitmasks for cloning. */
var CLONE_DEEP_FLAG = 1,
    CLONE_FLAT_FLAG = 2,
    CLONE_SYMBOLS_FLAG = 4;

/** `Object#toString` result references. */
var argsTag = '[object Arguments]',
    arrayTag = '[object Array]',
    boolTag = '[object Boolean]',
    dateTag = '[object Date]',
    errorTag = '[object Error]',
    funcTag = '[object Function]',
    genTag = '[object GeneratorFunction]',
    mapTag = '[object Map]',
    numberTag = '[object Number]',
    objectTag = '[object Object]',
    regexpTag = '[object RegExp]',
    setTag = '[object Set]',
    stringTag = '[object String]',
    symbolTag = '[object Symbol]',
    weakMapTag = '[object WeakMap]';

var arrayBufferTag = '[object ArrayBuffer]',
    dataViewTag = '[object DataView]',
    float32Tag = '[object Float32Array]',
    float64Tag = '[object Float64Array]',
    int8Tag = '[object Int8Array]',
    int16Tag = '[object Int16Array]',
    int32Tag = '[object Int32Array]',
    uint8Tag = '[object Uint8Array]',
    uint8ClampedTag = '[object Uint8ClampedArray]',
    uint16Tag = '[object Uint16Array]',
    uint32Tag = '[object Uint32Array]';

/** Used to identify `toStringTag` values supported by `_.clone`. */
var cloneableTags = {};
cloneableTags[argsTag] = cloneableTags[arrayTag] = cloneableTags[arrayBufferTag] = cloneableTags[dataViewTag] = cloneableTags[boolTag] = cloneableTags[dateTag] = cloneableTags[float32Tag] = cloneableTags[float64Tag] = cloneableTags[int8Tag] = cloneableTags[int16Tag] = cloneableTags[int32Tag] = cloneableTags[mapTag] = cloneableTags[numberTag] = cloneableTags[objectTag] = cloneableTags[regexpTag] = cloneableTags[setTag] = cloneableTags[stringTag] = cloneableTags[symbolTag] = cloneableTags[uint8Tag] = cloneableTags[uint8ClampedTag] = cloneableTags[uint16Tag] = cloneableTags[uint32Tag] = true;
cloneableTags[errorTag] = cloneableTags[funcTag] = cloneableTags[weakMapTag] = false;

/**
 * The base implementation of `_.clone` and `_.cloneDeep` which tracks
 * traversed objects.
 *
 * @private
 * @param {*} value The value to clone.
 * @param {boolean} bitmask The bitmask flags.
 *  1 - Deep clone
 *  2 - Flatten inherited properties
 *  4 - Clone symbols
 * @param {Function} [customizer] The function to customize cloning.
 * @param {string} [key] The key of `value`.
 * @param {Object} [object] The parent object of `value`.
 * @param {Object} [stack] Tracks traversed objects and their clone counterparts.
 * @returns {*} Returns the cloned value.
 */
function baseClone(value, bitmask, customizer, key, object, stack) {
  var result,
      isDeep = bitmask & CLONE_DEEP_FLAG,
      isFlat = bitmask & CLONE_FLAT_FLAG,
      isFull = bitmask & CLONE_SYMBOLS_FLAG;

  if (customizer) {
    result = object ? customizer(value, key, object, stack) : customizer(value);
  }
  if (result !== undefined) {
    return result;
  }
  if (!isObject(value)) {
    return value;
  }
  var isArr = isArray(value);
  if (isArr) {
    result = initCloneArray(value);
    if (!isDeep) {
      return copyArray(value, result);
    }
  } else {
    var tag = getTag(value),
        isFunc = tag == funcTag || tag == genTag;

    if (isBuffer(value)) {
      return cloneBuffer(value, isDeep);
    }
    if (tag == objectTag || tag == argsTag || isFunc && !object) {
      result = isFlat || isFunc ? {} : initCloneObject(value);
      if (!isDeep) {
        return isFlat ? copySymbolsIn(value, baseAssignIn(result, value)) : copySymbols(value, baseAssign(result, value));
      }
    } else {
      if (!cloneableTags[tag]) {
        return object ? value : {};
      }
      result = initCloneByTag(value, tag, baseClone, isDeep);
    }
  }
  // Check for circular references and return its corresponding clone.
  stack || (stack = new Stack());
  var stacked = stack.get(value);
  if (stacked) {
    return stacked;
  }
  stack.set(value, result);

  var keysFunc = isFull ? isFlat ? getAllKeysIn : getAllKeys : isFlat ? keysIn : keys;

  var props = isArr ? undefined : keysFunc(value);
  arrayEach(props || value, function (subValue, key) {
    if (props) {
      key = subValue;
      subValue = value[key];
    }
    // Recursively populate clone (susceptible to call stack limits).
    assignValue(result, key, baseClone(subValue, bitmask, customizer, key, value, stack));
  });
  return result;
}

module.exports = baseClone;

/***/ }),
/* 240 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Stack.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var ListCache = __webpack_require__(/*! ./_ListCache */ 145),
    stackClear = __webpack_require__(/*! ./_stackClear */ 934),
    stackDelete = __webpack_require__(/*! ./_stackDelete */ 935),
    stackGet = __webpack_require__(/*! ./_stackGet */ 936),
    stackHas = __webpack_require__(/*! ./_stackHas */ 937),
    stackSet = __webpack_require__(/*! ./_stackSet */ 938);

/**
 * Creates a stack cache object to store key-value pairs.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function Stack(entries) {
  var data = this.__data__ = new ListCache(entries);
  this.size = data.size;
}

// Add methods to `Stack`.
Stack.prototype.clear = stackClear;
Stack.prototype['delete'] = stackDelete;
Stack.prototype.get = stackGet;
Stack.prototype.has = stackHas;
Stack.prototype.set = stackSet;

module.exports = Stack;

/***/ }),
/* 241 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Map.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68),
    root = __webpack_require__(/*! ./_root */ 22);

/* Built-in method references that are verified to be native. */
var Map = getNative(root, 'Map');

module.exports = Map;

/***/ }),
/* 242 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_MapCache.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var mapCacheClear = __webpack_require__(/*! ./_mapCacheClear */ 939),
    mapCacheDelete = __webpack_require__(/*! ./_mapCacheDelete */ 946),
    mapCacheGet = __webpack_require__(/*! ./_mapCacheGet */ 948),
    mapCacheHas = __webpack_require__(/*! ./_mapCacheHas */ 949),
    mapCacheSet = __webpack_require__(/*! ./_mapCacheSet */ 950);

/**
 * Creates a map cache object to store key-value pairs.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function MapCache(entries) {
    var index = -1,
        length = entries == null ? 0 : entries.length;

    this.clear();
    while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
    }
}

// Add methods to `MapCache`.
MapCache.prototype.clear = mapCacheClear;
MapCache.prototype['delete'] = mapCacheDelete;
MapCache.prototype.get = mapCacheGet;
MapCache.prototype.has = mapCacheHas;
MapCache.prototype.set = mapCacheSet;

module.exports = MapCache;

/***/ }),
/* 243 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getSymbols.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayFilter = __webpack_require__(/*! ./_arrayFilter */ 956),
    stubArray = __webpack_require__(/*! ./stubArray */ 458);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Built-in value references. */
var propertyIsEnumerable = objectProto.propertyIsEnumerable;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeGetSymbols = Object.getOwnPropertySymbols;

/**
 * Creates an array of the own enumerable symbols of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of symbols.
 */
var getSymbols = !nativeGetSymbols ? stubArray : function (object) {
  if (object == null) {
    return [];
  }
  object = Object(object);
  return arrayFilter(nativeGetSymbols(object), function (symbol) {
    return propertyIsEnumerable.call(object, symbol);
  });
};

module.exports = getSymbols;

/***/ }),
/* 244 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayPush.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Appends the elements of `values` to `array`.
 *
 * @private
 * @param {Array} array The array to modify.
 * @param {Array} values The values to append.
 * @returns {Array} Returns `array`.
 */
function arrayPush(array, values) {
  var index = -1,
      length = values.length,
      offset = array.length;

  while (++index < length) {
    array[offset + index] = values[index];
  }
  return array;
}

module.exports = arrayPush;

/***/ }),
/* 245 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getPrototype.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var overArg = __webpack_require__(/*! ./_overArg */ 455);

/** Built-in value references. */
var getPrototype = overArg(Object.getPrototypeOf, Object);

module.exports = getPrototype;

/***/ }),
/* 246 */
/*!***********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneArrayBuffer.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Uint8Array = __webpack_require__(/*! ./_Uint8Array */ 464);

/**
 * Creates a clone of `arrayBuffer`.
 *
 * @private
 * @param {ArrayBuffer} arrayBuffer The array buffer to clone.
 * @returns {ArrayBuffer} Returns the cloned array buffer.
 */
function cloneArrayBuffer(arrayBuffer) {
  var result = new arrayBuffer.constructor(arrayBuffer.byteLength);
  new Uint8Array(result).set(new Uint8Array(arrayBuffer));
  return result;
}

module.exports = cloneArrayBuffer;

/***/ }),
/* 247 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseGet.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var castPath = __webpack_require__(/*! ./_castPath */ 149),
    toKey = __webpack_require__(/*! ./_toKey */ 94);

/**
 * The base implementation of `_.get` without support for default values.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Array|string} path The path of the property to get.
 * @returns {*} Returns the resolved value.
 */
function baseGet(object, path) {
  path = castPath(path, object);

  var index = 0,
      length = path.length;

  while (object != null && index < length) {
    object = object[toKey(path[index++])];
  }
  return index && index == length ? object : undefined;
}

module.exports = baseGet;

/***/ }),
/* 248 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isKey.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var isArray = __webpack_require__(/*! ./isArray */ 28),
    isSymbol = __webpack_require__(/*! ./isSymbol */ 112);

/** Used to match property names within property paths. */
var reIsDeepProp = /\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,
    reIsPlainProp = /^\w*$/;

/**
 * Checks if `value` is a property name and not a property path.
 *
 * @private
 * @param {*} value The value to check.
 * @param {Object} [object] The object to query keys on.
 * @returns {boolean} Returns `true` if `value` is a property name, else `false`.
 */
function isKey(value, object) {
  if (isArray(value)) {
    return false;
  }
  var type = typeof value === 'undefined' ? 'undefined' : _typeof(value);
  if (type == 'number' || type == 'symbol' || type == 'boolean' || value == null || isSymbol(value)) {
    return true;
  }
  return reIsPlainProp.test(value) || !reIsDeepProp.test(value) || object != null && value in Object(object);
}

module.exports = isKey;

/***/ }),
/* 249 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayMap.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.map` for arrays without support for iteratee
 * shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} iteratee The function invoked per iteration.
 * @returns {Array} Returns the new mapped array.
 */
function arrayMap(array, iteratee) {
  var index = -1,
      length = array == null ? 0 : array.length,
      result = Array(length);

  while (++index < length) {
    result[index] = iteratee(array[index], index, array);
  }
  return result;
}

module.exports = arrayMap;

/***/ }),
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */,
/* 261 */,
/* 262 */,
/* 263 */,
/* 264 */,
/* 265 */,
/* 266 */,
/* 267 */,
/* 268 */,
/* 269 */,
/* 270 */,
/* 271 */,
/* 272 */,
/* 273 */,
/* 274 */,
/* 275 */,
/* 276 */,
/* 277 */,
/* 278 */,
/* 279 */,
/* 280 */,
/* 281 */,
/* 282 */,
/* 283 */,
/* 284 */,
/* 285 */,
/* 286 */,
/* 287 */,
/* 288 */,
/* 289 */,
/* 290 */,
/* 291 */,
/* 292 */,
/* 293 */,
/* 294 */,
/* 295 */,
/* 296 */,
/* 297 */,
/* 298 */,
/* 299 */,
/* 300 */,
/* 301 */,
/* 302 */,
/* 303 */,
/* 304 */,
/* 305 */,
/* 306 */,
/* 307 */,
/* 308 */,
/* 309 */,
/* 310 */,
/* 311 */,
/* 312 */,
/* 313 */,
/* 314 */,
/* 315 */,
/* 316 */,
/* 317 */,
/* 318 */,
/* 319 */,
/* 320 */,
/* 321 */,
/* 322 */,
/* 323 */,
/* 324 */,
/* 325 */,
/* 326 */,
/* 327 */,
/* 328 */,
/* 329 */,
/* 330 */,
/* 331 */,
/* 332 */,
/* 333 */,
/* 334 */,
/* 335 */,
/* 336 */,
/* 337 */,
/* 338 */,
/* 339 */,
/* 340 */,
/* 341 */,
/* 342 */,
/* 343 */,
/* 344 */,
/* 345 */,
/* 346 */,
/* 347 */,
/* 348 */,
/* 349 */,
/* 350 */,
/* 351 */,
/* 352 */,
/* 353 */,
/* 354 */,
/* 355 */,
/* 356 */,
/* 357 */,
/* 358 */,
/* 359 */,
/* 360 */,
/* 361 */,
/* 362 */,
/* 363 */,
/* 364 */,
/* 365 */,
/* 366 */,
/* 367 */,
/* 368 */,
/* 369 */,
/* 370 */,
/* 371 */,
/* 372 */,
/* 373 */,
/* 374 */,
/* 375 */,
/* 376 */,
/* 377 */,
/* 378 */,
/* 379 */,
/* 380 */,
/* 381 */,
/* 382 */,
/* 383 */,
/* 384 */,
/* 385 */,
/* 386 */,
/* 387 */,
/* 388 */,
/* 389 */,
/* 390 */,
/* 391 */,
/* 392 */,
/* 393 */,
/* 394 */,
/* 395 */,
/* 396 */,
/* 397 */,
/* 398 */,
/* 399 */,
/* 400 */,
/* 401 */,
/* 402 */,
/* 403 */,
/* 404 */,
/* 405 */,
/* 406 */,
/* 407 */,
/* 408 */,
/* 409 */,
/* 410 */,
/* 411 */,
/* 412 */,
/* 413 */,
/* 414 */,
/* 415 */,
/* 416 */,
/* 417 */,
/* 418 */,
/* 419 */,
/* 420 */,
/* 421 */,
/* 422 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/prop-types/index.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * Copyright 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

if (true) {
  var REACT_ELEMENT_TYPE = typeof Symbol === 'function' && Symbol.for && Symbol.for('react.element') || 0xeac7;

  var isValidElement = function isValidElement(object) {
    return (typeof object === 'undefined' ? 'undefined' : _typeof(object)) === 'object' && object !== null && object.$$typeof === REACT_ELEMENT_TYPE;
  };

  // By explicitly using `prop-types` you are opting into new development behavior.
  // http://fb.me/prop-types-in-prod
  var throwOnDirectAccess = true;
  module.exports = __webpack_require__(/*! ./factoryWithTypeCheckers */ 874)(isValidElement, throwOnDirectAccess);
} else {
  // By explicitly using `prop-types` you are opting into new production behavior.
  // http://fb.me/prop-types-in-prod
  module.exports = require('./factoryWithThrowingShims')();
}

/***/ }),
/* 423 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/fbjs/lib/emptyFunction.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Copyright (c) 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * 
 */

function makeEmptyFunction(arg) {
  return function () {
    return arg;
  };
}

/**
 * This function accepts and discards inputs; it has no side effects. This is
 * primarily useful idiomatically for overridable function endpoints which
 * always need to be callable, since JS lacks a null-call idiom ala Cocoa.
 */
var emptyFunction = function emptyFunction() {};

emptyFunction.thatReturns = makeEmptyFunction;
emptyFunction.thatReturnsFalse = makeEmptyFunction(false);
emptyFunction.thatReturnsTrue = makeEmptyFunction(true);
emptyFunction.thatReturnsNull = makeEmptyFunction(null);
emptyFunction.thatReturnsThis = function () {
  return this;
};
emptyFunction.thatReturnsArgument = function (arg) {
  return arg;
};

module.exports = emptyFunction;

/***/ }),
/* 424 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/fbjs/lib/invariant.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 */



/**
 * Use invariant() to assert state which your program assumes to be true.
 *
 * Provide sprintf-style format (only %s is supported) and arguments
 * to provide information about what broke and what you were
 * expecting.
 *
 * The invariant message will be stripped in production, but the invariant
 * will remain to ensure logic does not differ in production.
 */

var validateFormat = function validateFormat(format) {};

if (true) {
  validateFormat = function validateFormat(format) {
    if (format === undefined) {
      throw new Error('invariant requires an error message argument');
    }
  };
}

function invariant(condition, format, a, b, c, d, e, f) {
  validateFormat(format);

  if (!condition) {
    var error;
    if (format === undefined) {
      error = new Error('Minified exception occurred; use the non-minified dev environment ' + 'for the full error message and additional helpful warnings.');
    } else {
      var args = [a, b, c, d, e, f];
      var argIndex = 0;
      error = new Error(format.replace(/%s/g, function () {
        return args[argIndex++];
      }));
      error.name = 'Invariant Violation';
    }

    error.framesToPop = 1; // we don't care about invariant's own frame
    throw error;
  }
}

module.exports = invariant;

/***/ }),
/* 425 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/fbjs/lib/warning.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2014-2015, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 */



var emptyFunction = __webpack_require__(/*! ./emptyFunction */ 423);

/**
 * Similar to invariant but only logs a warning if the condition is not met.
 * This can be used to log issues in development environments in critical
 * paths. Removing the logging code for production environments will keep the
 * same logic and follow the same code paths.
 */

var warning = emptyFunction;

if (true) {
  (function () {
    var printWarning = function printWarning(format) {
      for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
        args[_key - 1] = arguments[_key];
      }

      var argIndex = 0;
      var message = 'Warning: ' + format.replace(/%s/g, function () {
        return args[argIndex++];
      });
      if (typeof console !== 'undefined') {
        console.error(message);
      }
      try {
        // --- Welcome to debugging React ---
        // This error was thrown as a convenience so that you can use this stack
        // to find the callsite that caused this warning to fire.
        throw new Error(message);
      } catch (x) {}
    };

    warning = function warning(condition, format) {
      if (format === undefined) {
        throw new Error('`warning(condition, format, ...args)` requires a warning ' + 'message argument');
      }

      if (format.indexOf('Failed Composite propType: ') === 0) {
        return; // Ignore CompositeComponent proptype check.
      }

      if (!condition) {
        for (var _len2 = arguments.length, args = Array(_len2 > 2 ? _len2 - 2 : 0), _key2 = 2; _key2 < _len2; _key2++) {
          args[_key2 - 2] = arguments[_key2];
        }

        printWarning.apply(undefined, [format].concat(args));
      }
    };
  })();
}

module.exports = warning;

/***/ }),
/* 426 */
/*!**********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/prop-types/lib/ReactPropTypesSecret.js ***!
  \**********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



var ReactPropTypesSecret = 'SECRET_DO_NOT_PASS_THIS_OR_YOU_WILL_BE_FIRED';

module.exports = ReactPropTypesSecret;

/***/ }),
/* 427 */
/*!************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/isPlainObject.js ***!
  \************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

exports.__esModule = true;
exports.default = isPlainObject;
var fnToString = function fnToString(fn) {
  return Function.prototype.toString.call(fn);
};

/**
 * @param {any} obj The object to inspect.
 * @returns {boolean} True if the argument appears to be a plain object.
 */
function isPlainObject(obj) {
  if (!obj || (typeof obj === 'undefined' ? 'undefined' : _typeof(obj)) !== 'object') {
    return false;
  }

  var proto = typeof obj.constructor === 'function' ? Object.getPrototypeOf(obj) : Object.prototype;

  if (proto === null) {
    return true;
  }

  var constructor = proto.constructor;

  return typeof constructor === 'function' && constructor instanceof constructor && fnToString(constructor) === fnToString(Object);
}

/***/ }),
/* 428 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/invariant/browser.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2013-2015, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



/**
 * Use invariant() to assert state which your program assumes to be true.
 *
 * Provide sprintf-style format (only %s is supported) and arguments
 * to provide information about what broke and what you were
 * expecting.
 *
 * The invariant message will be stripped in production, but the invariant
 * will remain to ensure logic does not differ in production.
 */

var invariant = function invariant(condition, format, a, b, c, d, e, f) {
  if (true) {
    if (format === undefined) {
      throw new Error('invariant requires an error message argument');
    }
  }

  if (!condition) {
    var error;
    if (format === undefined) {
      error = new Error('Minified exception occurred; use the non-minified dev environment ' + 'for the full error message and additional helpful warnings.');
    } else {
      var args = [a, b, c, d, e, f];
      var argIndex = 0;
      error = new Error(format.replace(/%s/g, function () {
        return args[argIndex++];
      }));
      error.name = 'Invariant Violation';
    }

    error.framesToPop = 1; // we don't care about invariant's own frame
    throw error;
  }
};

module.exports = invariant;

/***/ }),
/* 429 */
/*!*****************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/PromiseState.js ***!
  \*****************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

var PromiseState = function () {

  // creates a new PromiseState that is pending

  PromiseState.create = function create(meta) {
    return new PromiseState({
      pending: true,
      meta: meta
    });
  };

  // creates as PromiseState that is refreshing
  // can be called without a previous PromiseState and will be both pending and refreshing

  PromiseState.refresh = function refresh(previous, meta) {
    var p = previous || PromiseState.create(meta);

    return new PromiseState({
      pending: p.pending,
      refreshing: true,
      fulfilled: p.fulfilled,
      rejected: p.rejected,
      value: p.value,
      reason: p.reason,
      meta: p.meta
    });
  };

  // creates a PromiseState that is resolved with the given value.
  // if the given value is already a PromiseState,
  // it will be returned as is and ignore the provided meta.

  PromiseState.resolve = function resolve(value, meta) {
    if (value instanceof PromiseState) {
      return value;
    }

    return new PromiseState({
      fulfilled: true,
      value: value,
      meta: meta
    });
  };

  // creates a PromiseState that is rejected with the given reason

  PromiseState.reject = function reject(reason, meta) {
    return new PromiseState({
      rejected: true,
      reason: reason,
      meta: meta
    });
  };

  // The PromiseState.all(iterable) method returns a PromiseState
  // that resolves when all of the PromiseStates in the iterable
  // argument have resolved, or rejects with the reason of the
  // first passed PromiseState that rejects.

  PromiseState.all = function all(iterable) {
    if (!Array.isArray(iterable)) {
      iterable = Array.from(iterable);
    }

    return new PromiseState({
      pending: iterable.some(function (ps) {
        return ps.pending;
      }),
      refreshing: iterable.some(function (ps) {
        return ps.refreshing;
      }),
      fulfilled: iterable.every(function (ps) {
        return ps.fulfilled;
      }),
      rejected: iterable.some(function (ps) {
        return ps.rejected;
      }),
      value: iterable.map(function (ps) {
        return ps.value;
      }),
      reason: (iterable.find(function (ps) {
        return ps.reason;
      }) || {}).reason,
      meta: iterable.map(function (ps) {
        return ps.meta;
      })
    });
  };

  // The PromiseState.race(iterable) method returns a PromiseState
  // that resolves or rejects as soon as one of the PromiseStates in
  // the iterable resolves or rejects, with the value or reason
  // from that PromiseState.

  PromiseState.race = function race(iterable) {
    if (!Array.isArray(iterable)) {
      iterable = Array.from(iterable);
    }

    var winner = iterable.find(function (ps) {
      return ps.settled;
    });

    return new PromiseState({
      pending: !winner && iterable.some(function (ps) {
        return ps.pending;
      }),
      refreshing: !winner && iterable.some(function (ps) {
        return ps.refreshing;
      }),
      fulfilled: winner && winner.fulfilled,
      rejected: winner && winner.rejected,
      value: winner && winner.value,
      reason: winner && winner.reason,
      meta: winner && winner.meta
    });
  };

  // Constructor for creating a raw PromiseState. DO NOT USE DIRECTLY. Instead, use PromiseState.create() or other static constructors

  function PromiseState(_ref) {
    var _ref$pending = _ref.pending;
    var pending = _ref$pending === undefined ? false : _ref$pending;
    var _ref$refreshing = _ref.refreshing;
    var refreshing = _ref$refreshing === undefined ? false : _ref$refreshing;
    var _ref$fulfilled = _ref.fulfilled;
    var fulfilled = _ref$fulfilled === undefined ? false : _ref$fulfilled;
    var _ref$rejected = _ref.rejected;
    var rejected = _ref$rejected === undefined ? false : _ref$rejected;
    var _ref$value = _ref.value;
    var value = _ref$value === undefined ? null : _ref$value;
    var _ref$reason = _ref.reason;
    var reason = _ref$reason === undefined ? null : _ref$reason;
    var _ref$meta = _ref.meta;
    var meta = _ref$meta === undefined ? {} : _ref$meta;

    _classCallCheck(this, PromiseState);

    this.pending = pending;
    this.refreshing = refreshing;
    this.fulfilled = fulfilled;
    this.rejected = rejected;
    this.settled = fulfilled || rejected;
    this.value = value;
    this.reason = reason;
    this.meta = meta;
  }

  // Appends and calls fulfillment and rejection handlers on the PromiseState,
  // and returns a new PromiseState resolving to the return value of the called handler,
  // or to its original settled value if the promise was not handled.
  // The handler functions take the value/reason and meta as parameters.
  // (i.e. if the relevant handler onFulfilled or onRejected is undefined).
  // Note, unlike Promise.then(), these handlers are called immediately.

  PromiseState.prototype.then = function then(onFulFilled, onRejected) {
    if (this.fulfilled && onFulFilled) {
      return PromiseState.resolve(onFulFilled(this.value, this.meta), this.meta);
    }

    if (this.rejected && onRejected) {
      return PromiseState.resolve(onRejected(this.reason, this.meta), this.meta);
    }

    return this;
  };

  // Appends and calls a rejection handler callback to the PromiseState,
  // and returns a new PromiseState resolving to the return value of the
  // callback if it is called, or to its original fulfillment value if
  // the PromiseState is instead fulfilled. The handler function take
  // the reason and meta as parameters. Note, unlike Promise.catch(),
  // this handlers is called immediately.

  PromiseState.prototype.catch = function _catch(onRejected) {
    return this.then(undefined, onRejected);
  };

  return PromiseState;
}();

exports.default = PromiseState;

/***/ }),
/* 430 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/placeholder.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The default argument placeholder value for methods.
 *
 * @type {Object}
 */
module.exports = {};

/***/ }),
/* 431 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseSetData.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var identity = __webpack_require__(/*! ./identity */ 226),
    metaMap = __webpack_require__(/*! ./_metaMap */ 432);

/**
 * The base implementation of `setData` without support for hot loop shorting.
 *
 * @private
 * @param {Function} func The function to associate metadata with.
 * @param {*} data The metadata.
 * @returns {Function} Returns `func`.
 */
var baseSetData = !metaMap ? identity : function (func, data) {
  metaMap.set(func, data);
  return func;
};

module.exports = baseSetData;

/***/ }),
/* 432 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_metaMap.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var WeakMap = __webpack_require__(/*! ./_WeakMap */ 433);

/** Used to store function metadata. */
var metaMap = WeakMap && new WeakMap();

module.exports = metaMap;

/***/ }),
/* 433 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_WeakMap.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68),
    root = __webpack_require__(/*! ./_root */ 22);

/* Built-in method references that are verified to be native. */
var WeakMap = getNative(root, 'WeakMap');

module.exports = WeakMap;

/***/ }),
/* 434 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_freeGlobal.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/** Detect free variable `global` from Node.js. */
var freeGlobal = (typeof global === 'undefined' ? 'undefined' : _typeof(global)) == 'object' && global && global.Object === Object && global;

module.exports = freeGlobal;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../node_modules/webpack/buildin/global.js */ 19)))

/***/ }),
/* 435 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_toSource.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used for built-in method references. */
var funcProto = Function.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/**
 * Converts `func` to its source code.
 *
 * @private
 * @param {Function} func The function to convert.
 * @returns {string} Returns the source code.
 */
function toSource(func) {
  if (func != null) {
    try {
      return funcToString.call(func);
    } catch (e) {}
    try {
      return func + '';
    } catch (e) {}
  }
  return '';
}

module.exports = toSource;

/***/ }),
/* 436 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createHybrid.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var composeArgs = __webpack_require__(/*! ./_composeArgs */ 437),
    composeArgsRight = __webpack_require__(/*! ./_composeArgsRight */ 438),
    countHolders = __webpack_require__(/*! ./_countHolders */ 899),
    createCtor = __webpack_require__(/*! ./_createCtor */ 141),
    createRecurry = __webpack_require__(/*! ./_createRecurry */ 439),
    getHolder = __webpack_require__(/*! ./_getHolder */ 447),
    reorder = __webpack_require__(/*! ./_reorder */ 916),
    replaceHolders = __webpack_require__(/*! ./_replaceHolders */ 233),
    root = __webpack_require__(/*! ./_root */ 22);

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1,
    WRAP_BIND_KEY_FLAG = 2,
    WRAP_CURRY_FLAG = 8,
    WRAP_CURRY_RIGHT_FLAG = 16,
    WRAP_ARY_FLAG = 128,
    WRAP_FLIP_FLAG = 512;

/**
 * Creates a function that wraps `func` to invoke it with optional `this`
 * binding of `thisArg`, partial application, and currying.
 *
 * @private
 * @param {Function|string} func The function or method name to wrap.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @param {*} [thisArg] The `this` binding of `func`.
 * @param {Array} [partials] The arguments to prepend to those provided to
 *  the new function.
 * @param {Array} [holders] The `partials` placeholder indexes.
 * @param {Array} [partialsRight] The arguments to append to those provided
 *  to the new function.
 * @param {Array} [holdersRight] The `partialsRight` placeholder indexes.
 * @param {Array} [argPos] The argument positions of the new function.
 * @param {number} [ary] The arity cap of `func`.
 * @param {number} [arity] The arity of `func`.
 * @returns {Function} Returns the new wrapped function.
 */
function createHybrid(func, bitmask, thisArg, partials, holders, partialsRight, holdersRight, argPos, ary, arity) {
  var isAry = bitmask & WRAP_ARY_FLAG,
      isBind = bitmask & WRAP_BIND_FLAG,
      isBindKey = bitmask & WRAP_BIND_KEY_FLAG,
      isCurried = bitmask & (WRAP_CURRY_FLAG | WRAP_CURRY_RIGHT_FLAG),
      isFlip = bitmask & WRAP_FLIP_FLAG,
      Ctor = isBindKey ? undefined : createCtor(func);

  function wrapper() {
    var length = arguments.length,
        args = Array(length),
        index = length;

    while (index--) {
      args[index] = arguments[index];
    }
    if (isCurried) {
      var placeholder = getHolder(wrapper),
          holdersCount = countHolders(args, placeholder);
    }
    if (partials) {
      args = composeArgs(args, partials, holders, isCurried);
    }
    if (partialsRight) {
      args = composeArgsRight(args, partialsRight, holdersRight, isCurried);
    }
    length -= holdersCount;
    if (isCurried && length < arity) {
      var newHolders = replaceHolders(args, placeholder);
      return createRecurry(func, bitmask, createHybrid, wrapper.placeholder, thisArg, args, newHolders, argPos, ary, arity - length);
    }
    var thisBinding = isBind ? thisArg : this,
        fn = isBindKey ? thisBinding[func] : func;

    length = args.length;
    if (argPos) {
      args = reorder(args, argPos);
    } else if (isFlip && length > 1) {
      args.reverse();
    }
    if (isAry && ary < length) {
      args.length = ary;
    }
    if (this && this !== root && this instanceof wrapper) {
      fn = Ctor || createCtor(fn);
    }
    return fn.apply(thisBinding, args);
  }
  return wrapper;
}

module.exports = createHybrid;

/***/ }),
/* 437 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_composeArgs.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMax = Math.max;

/**
 * Creates an array that is the composition of partially applied arguments,
 * placeholders, and provided arguments into a single array of arguments.
 *
 * @private
 * @param {Array} args The provided arguments.
 * @param {Array} partials The arguments to prepend to those provided.
 * @param {Array} holders The `partials` placeholder indexes.
 * @params {boolean} [isCurried] Specify composing for a curried function.
 * @returns {Array} Returns the new array of composed arguments.
 */
function composeArgs(args, partials, holders, isCurried) {
  var argsIndex = -1,
      argsLength = args.length,
      holdersLength = holders.length,
      leftIndex = -1,
      leftLength = partials.length,
      rangeLength = nativeMax(argsLength - holdersLength, 0),
      result = Array(leftLength + rangeLength),
      isUncurried = !isCurried;

  while (++leftIndex < leftLength) {
    result[leftIndex] = partials[leftIndex];
  }
  while (++argsIndex < holdersLength) {
    if (isUncurried || argsIndex < argsLength) {
      result[holders[argsIndex]] = args[argsIndex];
    }
  }
  while (rangeLength--) {
    result[leftIndex++] = args[argsIndex++];
  }
  return result;
}

module.exports = composeArgs;

/***/ }),
/* 438 */
/*!***********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_composeArgsRight.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMax = Math.max;

/**
 * This function is like `composeArgs` except that the arguments composition
 * is tailored for `_.partialRight`.
 *
 * @private
 * @param {Array} args The provided arguments.
 * @param {Array} partials The arguments to append to those provided.
 * @param {Array} holders The `partials` placeholder indexes.
 * @params {boolean} [isCurried] Specify composing for a curried function.
 * @returns {Array} Returns the new array of composed arguments.
 */
function composeArgsRight(args, partials, holders, isCurried) {
  var argsIndex = -1,
      argsLength = args.length,
      holdersIndex = -1,
      holdersLength = holders.length,
      rightIndex = -1,
      rightLength = partials.length,
      rangeLength = nativeMax(argsLength - holdersLength, 0),
      result = Array(rangeLength + rightLength),
      isUncurried = !isCurried;

  while (++argsIndex < rangeLength) {
    result[argsIndex] = args[argsIndex];
  }
  var offset = argsIndex;
  while (++rightIndex < rightLength) {
    result[offset + rightIndex] = partials[rightIndex];
  }
  while (++holdersIndex < holdersLength) {
    if (isUncurried || argsIndex < argsLength) {
      result[offset + holders[holdersIndex]] = args[argsIndex++];
    }
  }
  return result;
}

module.exports = composeArgsRight;

/***/ }),
/* 439 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createRecurry.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isLaziable = __webpack_require__(/*! ./_isLaziable */ 900),
    setData = __webpack_require__(/*! ./_setData */ 442),
    setWrapToString = __webpack_require__(/*! ./_setWrapToString */ 444);

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1,
    WRAP_BIND_KEY_FLAG = 2,
    WRAP_CURRY_BOUND_FLAG = 4,
    WRAP_CURRY_FLAG = 8,
    WRAP_PARTIAL_FLAG = 32,
    WRAP_PARTIAL_RIGHT_FLAG = 64;

/**
 * Creates a function that wraps `func` to continue currying.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @param {Function} wrapFunc The function to create the `func` wrapper.
 * @param {*} placeholder The placeholder value.
 * @param {*} [thisArg] The `this` binding of `func`.
 * @param {Array} [partials] The arguments to prepend to those provided to
 *  the new function.
 * @param {Array} [holders] The `partials` placeholder indexes.
 * @param {Array} [argPos] The argument positions of the new function.
 * @param {number} [ary] The arity cap of `func`.
 * @param {number} [arity] The arity of `func`.
 * @returns {Function} Returns the new wrapped function.
 */
function createRecurry(func, bitmask, wrapFunc, placeholder, thisArg, partials, holders, argPos, ary, arity) {
  var isCurry = bitmask & WRAP_CURRY_FLAG,
      newHolders = isCurry ? holders : undefined,
      newHoldersRight = isCurry ? undefined : holders,
      newPartials = isCurry ? partials : undefined,
      newPartialsRight = isCurry ? undefined : partials;

  bitmask |= isCurry ? WRAP_PARTIAL_FLAG : WRAP_PARTIAL_RIGHT_FLAG;
  bitmask &= ~(isCurry ? WRAP_PARTIAL_RIGHT_FLAG : WRAP_PARTIAL_FLAG);

  if (!(bitmask & WRAP_CURRY_BOUND_FLAG)) {
    bitmask &= ~(WRAP_BIND_FLAG | WRAP_BIND_KEY_FLAG);
  }
  var newData = [func, bitmask, thisArg, newPartials, newHolders, newPartialsRight, newHoldersRight, argPos, ary, arity];

  var result = wrapFunc.apply(undefined, newData);
  if (isLaziable(func)) {
    setData(result, newData);
  }
  result.placeholder = placeholder;
  return setWrapToString(result, func, bitmask);
}

module.exports = createRecurry;

/***/ }),
/* 440 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getData.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var metaMap = __webpack_require__(/*! ./_metaMap */ 432),
    noop = __webpack_require__(/*! ./noop */ 901);

/**
 * Gets metadata for `func`.
 *
 * @private
 * @param {Function} func The function to query.
 * @returns {*} Returns the metadata for `func`.
 */
var getData = !metaMap ? noop : function (func) {
  return metaMap.get(func);
};

module.exports = getData;

/***/ }),
/* 441 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_LodashWrapper.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseCreate = __webpack_require__(/*! ./_baseCreate */ 142),
    baseLodash = __webpack_require__(/*! ./_baseLodash */ 230);

/**
 * The base constructor for creating `lodash` wrapper objects.
 *
 * @private
 * @param {*} value The value to wrap.
 * @param {boolean} [chainAll] Enable explicit method chain sequences.
 */
function LodashWrapper(value, chainAll) {
  this.__wrapped__ = value;
  this.__actions__ = [];
  this.__chain__ = !!chainAll;
  this.__index__ = 0;
  this.__values__ = undefined;
}

LodashWrapper.prototype = baseCreate(baseLodash.prototype);
LodashWrapper.prototype.constructor = LodashWrapper;

module.exports = LodashWrapper;

/***/ }),
/* 442 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setData.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseSetData = __webpack_require__(/*! ./_baseSetData */ 431),
    shortOut = __webpack_require__(/*! ./_shortOut */ 443);

/**
 * Sets metadata for `func`.
 *
 * **Note:** If this function becomes hot, i.e. is invoked a lot in a short
 * period of time, it will trip its breaker and transition to an identity
 * function to avoid garbage collection pauses in V8. See
 * [V8 issue 2070](https://bugs.chromium.org/p/v8/issues/detail?id=2070)
 * for more details.
 *
 * @private
 * @param {Function} func The function to associate metadata with.
 * @param {*} data The metadata.
 * @returns {Function} Returns `func`.
 */
var setData = shortOut(baseSetData);

module.exports = setData;

/***/ }),
/* 443 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_shortOut.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to detect hot functions by number of calls within a span of milliseconds. */
var HOT_COUNT = 800,
    HOT_SPAN = 16;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeNow = Date.now;

/**
 * Creates a function that'll short out and invoke `identity` instead
 * of `func` when it's called `HOT_COUNT` or more times in `HOT_SPAN`
 * milliseconds.
 *
 * @private
 * @param {Function} func The function to restrict.
 * @returns {Function} Returns the new shortable function.
 */
function shortOut(func) {
  var count = 0,
      lastCalled = 0;

  return function () {
    var stamp = nativeNow(),
        remaining = HOT_SPAN - (stamp - lastCalled);

    lastCalled = stamp;
    if (remaining > 0) {
      if (++count >= HOT_COUNT) {
        return arguments[0];
      }
    } else {
      count = 0;
    }
    return func.apply(undefined, arguments);
  };
}

module.exports = shortOut;

/***/ }),
/* 444 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setWrapToString.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getWrapDetails = __webpack_require__(/*! ./_getWrapDetails */ 906),
    insertWrapDetails = __webpack_require__(/*! ./_insertWrapDetails */ 907),
    setToString = __webpack_require__(/*! ./_setToString */ 445),
    updateWrapDetails = __webpack_require__(/*! ./_updateWrapDetails */ 910);

/**
 * Sets the `toString` method of `wrapper` to mimic the source of `reference`
 * with wrapper details in a comment at the top of the source body.
 *
 * @private
 * @param {Function} wrapper The function to modify.
 * @param {Function} reference The reference function.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @returns {Function} Returns `wrapper`.
 */
function setWrapToString(wrapper, reference, bitmask) {
  var source = reference + '';
  return setToString(wrapper, insertWrapDetails(source, updateWrapDetails(getWrapDetails(source), bitmask)));
}

module.exports = setWrapToString;

/***/ }),
/* 445 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setToString.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseSetToString = __webpack_require__(/*! ./_baseSetToString */ 908),
    shortOut = __webpack_require__(/*! ./_shortOut */ 443);

/**
 * Sets the `toString` method of `func` to return `string`.
 *
 * @private
 * @param {Function} func The function to modify.
 * @param {Function} string The `toString` result.
 * @returns {Function} Returns `func`.
 */
var setToString = shortOut(baseSetToString);

module.exports = setToString;

/***/ }),
/* 446 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_defineProperty.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68);

var defineProperty = function () {
  try {
    var func = getNative(Object, 'defineProperty');
    func({}, '', {});
    return func;
  } catch (e) {}
}();

module.exports = defineProperty;

/***/ }),
/* 447 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getHolder.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Gets the argument placeholder value for `func`.
 *
 * @private
 * @param {Function} func The function to inspect.
 * @returns {*} Returns the placeholder value.
 */
function getHolder(func) {
  var object = func;
  return object.placeholder;
}

module.exports = getHolder;

/***/ }),
/* 448 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/toInteger.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var toFinite = __webpack_require__(/*! ./toFinite */ 919);

/**
 * Converts `value` to an integer.
 *
 * **Note:** This method is loosely based on
 * [`ToInteger`](http://www.ecma-international.org/ecma-262/7.0/#sec-tointeger).
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to convert.
 * @returns {number} Returns the converted integer.
 * @example
 *
 * _.toInteger(3.2);
 * // => 3
 *
 * _.toInteger(Number.MIN_VALUE);
 * // => 0
 *
 * _.toInteger(Infinity);
 * // => 1.7976931348623157e+308
 *
 * _.toInteger('3.2');
 * // => 3
 */
function toInteger(value) {
  var result = toFinite(value),
      remainder = result % 1;

  return result === result ? remainder ? result - remainder : result : 0;
}

module.exports = toInteger;

/***/ }),
/* 449 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseAssign.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var copyObject = __webpack_require__(/*! ./_copyObject */ 113),
    keys = __webpack_require__(/*! ./keys */ 144);

/**
 * The base implementation of `_.assign` without support for multiple sources
 * or `customizer` functions.
 *
 * @private
 * @param {Object} object The destination object.
 * @param {Object} source The source object.
 * @returns {Object} Returns `object`.
 */
function baseAssign(object, source) {
  return object && copyObject(source, keys(source), object);
}

module.exports = baseAssign;

/***/ }),
/* 450 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_assignValue.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseAssignValue = __webpack_require__(/*! ./_baseAssignValue */ 451),
    eq = __webpack_require__(/*! ./eq */ 234);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Assigns `value` to `key` of `object` if the existing value is not equivalent
 * using [`SameValueZero`](http://ecma-international.org/ecma-262/7.0/#sec-samevaluezero)
 * for equality comparisons.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {string} key The key of the property to assign.
 * @param {*} value The value to assign.
 */
function assignValue(object, key, value) {
  var objValue = object[key];
  if (!(hasOwnProperty.call(object, key) && eq(objValue, value)) || value === undefined && !(key in object)) {
    baseAssignValue(object, key, value);
  }
}

module.exports = assignValue;

/***/ }),
/* 451 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseAssignValue.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var defineProperty = __webpack_require__(/*! ./_defineProperty */ 446);

/**
 * The base implementation of `assignValue` and `assignMergeValue` without
 * value checks.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {string} key The key of the property to assign.
 * @param {*} value The value to assign.
 */
function baseAssignValue(object, key, value) {
  if (key == '__proto__' && defineProperty) {
    defineProperty(object, key, {
      'configurable': true,
      'enumerable': true,
      'value': value,
      'writable': true
    });
  } else {
    object[key] = value;
  }
}

module.exports = baseAssignValue;

/***/ }),
/* 452 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayLikeKeys.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseTimes = __webpack_require__(/*! ./_baseTimes */ 921),
    isArguments = __webpack_require__(/*! ./isArguments */ 235),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isBuffer = __webpack_require__(/*! ./isBuffer */ 236),
    isIndex = __webpack_require__(/*! ./_isIndex */ 232),
    isTypedArray = __webpack_require__(/*! ./isTypedArray */ 453);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Creates an array of the enumerable property names of the array-like `value`.
 *
 * @private
 * @param {*} value The value to query.
 * @param {boolean} inherited Specify returning inherited property names.
 * @returns {Array} Returns the array of property names.
 */
function arrayLikeKeys(value, inherited) {
  var isArr = isArray(value),
      isArg = !isArr && isArguments(value),
      isBuff = !isArr && !isArg && isBuffer(value),
      isType = !isArr && !isArg && !isBuff && isTypedArray(value),
      skipIndexes = isArr || isArg || isBuff || isType,
      result = skipIndexes ? baseTimes(value.length, String) : [],
      length = result.length;

  for (var key in value) {
    if ((inherited || hasOwnProperty.call(value, key)) && !(skipIndexes && (
    // Safari 9 has enumerable `arguments.length` in strict mode.
    key == 'length' ||
    // Node.js 0.10 has enumerable non-index properties on buffers.
    isBuff && (key == 'offset' || key == 'parent') ||
    // PhantomJS 2 has enumerable non-index properties on typed arrays.
    isType && (key == 'buffer' || key == 'byteLength' || key == 'byteOffset') ||
    // Skip index properties.
    isIndex(key, length)))) {
      result.push(key);
    }
  }
  return result;
}

module.exports = arrayLikeKeys;

/***/ }),
/* 453 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isTypedArray.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsTypedArray = __webpack_require__(/*! ./_baseIsTypedArray */ 924),
    baseUnary = __webpack_require__(/*! ./_baseUnary */ 925),
    nodeUtil = __webpack_require__(/*! ./_nodeUtil */ 926);

/* Node.js helper references. */
var nodeIsTypedArray = nodeUtil && nodeUtil.isTypedArray;

/**
 * Checks if `value` is classified as a typed array.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
 * @example
 *
 * _.isTypedArray(new Uint8Array);
 * // => true
 *
 * _.isTypedArray([]);
 * // => false
 */
var isTypedArray = nodeIsTypedArray ? baseUnary(nodeIsTypedArray) : baseIsTypedArray;

module.exports = isTypedArray;

/***/ }),
/* 454 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseKeys.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isPrototype = __webpack_require__(/*! ./_isPrototype */ 238),
    nativeKeys = __webpack_require__(/*! ./_nativeKeys */ 927);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * The base implementation of `_.keys` which doesn't treat sparse arrays as dense.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 */
function baseKeys(object) {
  if (!isPrototype(object)) {
    return nativeKeys(object);
  }
  var result = [];
  for (var key in Object(object)) {
    if (hasOwnProperty.call(object, key) && key != 'constructor') {
      result.push(key);
    }
  }
  return result;
}

module.exports = baseKeys;

/***/ }),
/* 455 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_overArg.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Creates a unary function that invokes `func` with its argument transformed.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {Function} transform The argument transform.
 * @returns {Function} Returns the new function.
 */
function overArg(func, transform) {
  return function (arg) {
    return func(transform(arg));
  };
}

module.exports = overArg;

/***/ }),
/* 456 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isArrayLike.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isFunction = __webpack_require__(/*! ./isFunction */ 227),
    isLength = __webpack_require__(/*! ./isLength */ 237);

/**
 * Checks if `value` is array-like. A value is considered array-like if it's
 * not a function and has a `value.length` that's an integer greater than or
 * equal to `0` and less than or equal to `Number.MAX_SAFE_INTEGER`.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is array-like, else `false`.
 * @example
 *
 * _.isArrayLike([1, 2, 3]);
 * // => true
 *
 * _.isArrayLike(document.body.children);
 * // => true
 *
 * _.isArrayLike('abc');
 * // => true
 *
 * _.isArrayLike(_.noop);
 * // => false
 */
function isArrayLike(value) {
  return value != null && isLength(value.length) && !isFunction(value);
}

module.exports = isArrayLike;

/***/ }),
/* 457 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/keysIn.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayLikeKeys = __webpack_require__(/*! ./_arrayLikeKeys */ 452),
    baseKeysIn = __webpack_require__(/*! ./_baseKeysIn */ 952),
    isArrayLike = __webpack_require__(/*! ./isArrayLike */ 456);

/**
 * Creates an array of the own and inherited enumerable property names of `object`.
 *
 * **Note:** Non-object values are coerced to objects.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Object
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 * @example
 *
 * function Foo() {
 *   this.a = 1;
 *   this.b = 2;
 * }
 *
 * Foo.prototype.c = 3;
 *
 * _.keysIn(new Foo);
 * // => ['a', 'b', 'c'] (iteration order is not guaranteed)
 */
function keysIn(object) {
  return isArrayLike(object) ? arrayLikeKeys(object, true) : baseKeysIn(object);
}

module.exports = keysIn;

/***/ }),
/* 458 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/stubArray.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * This method returns a new empty array.
 *
 * @static
 * @memberOf _
 * @since 4.13.0
 * @category Util
 * @returns {Array} Returns the new empty array.
 * @example
 *
 * var arrays = _.times(2, _.stubArray);
 *
 * console.log(arrays);
 * // => [[], []]
 *
 * console.log(arrays[0] === arrays[1]);
 * // => false
 */
function stubArray() {
  return [];
}

module.exports = stubArray;

/***/ }),
/* 459 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getSymbolsIn.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayPush = __webpack_require__(/*! ./_arrayPush */ 244),
    getPrototype = __webpack_require__(/*! ./_getPrototype */ 245),
    getSymbols = __webpack_require__(/*! ./_getSymbols */ 243),
    stubArray = __webpack_require__(/*! ./stubArray */ 458);

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeGetSymbols = Object.getOwnPropertySymbols;

/**
 * Creates an array of the own and inherited enumerable symbols of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of symbols.
 */
var getSymbolsIn = !nativeGetSymbols ? stubArray : function (object) {
  var result = [];
  while (object) {
    arrayPush(result, getSymbols(object));
    object = getPrototype(object);
  }
  return result;
};

module.exports = getSymbolsIn;

/***/ }),
/* 460 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getAllKeys.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetAllKeys = __webpack_require__(/*! ./_baseGetAllKeys */ 461),
    getSymbols = __webpack_require__(/*! ./_getSymbols */ 243),
    keys = __webpack_require__(/*! ./keys */ 144);

/**
 * Creates an array of own enumerable property names and symbols of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names and symbols.
 */
function getAllKeys(object) {
  return baseGetAllKeys(object, keys, getSymbols);
}

module.exports = getAllKeys;

/***/ }),
/* 461 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseGetAllKeys.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayPush = __webpack_require__(/*! ./_arrayPush */ 244),
    isArray = __webpack_require__(/*! ./isArray */ 28);

/**
 * The base implementation of `getAllKeys` and `getAllKeysIn` which uses
 * `keysFunc` and `symbolsFunc` to get the enumerable property names and
 * symbols of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Function} keysFunc The function to get the keys of `object`.
 * @param {Function} symbolsFunc The function to get the symbols of `object`.
 * @returns {Array} Returns the array of property names and symbols.
 */
function baseGetAllKeys(object, keysFunc, symbolsFunc) {
  var result = keysFunc(object);
  return isArray(object) ? result : arrayPush(result, symbolsFunc(object));
}

module.exports = baseGetAllKeys;

/***/ }),
/* 462 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getAllKeysIn.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetAllKeys = __webpack_require__(/*! ./_baseGetAllKeys */ 461),
    getSymbolsIn = __webpack_require__(/*! ./_getSymbolsIn */ 459),
    keysIn = __webpack_require__(/*! ./keysIn */ 457);

/**
 * Creates an array of own and inherited enumerable property names and
 * symbols of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names and symbols.
 */
function getAllKeysIn(object) {
  return baseGetAllKeys(object, keysIn, getSymbolsIn);
}

module.exports = getAllKeysIn;

/***/ }),
/* 463 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getTag.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var DataView = __webpack_require__(/*! ./_DataView */ 958),
    Map = __webpack_require__(/*! ./_Map */ 241),
    Promise = __webpack_require__(/*! ./_Promise */ 959),
    Set = __webpack_require__(/*! ./_Set */ 960),
    WeakMap = __webpack_require__(/*! ./_WeakMap */ 433),
    baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    toSource = __webpack_require__(/*! ./_toSource */ 435);

/** `Object#toString` result references. */
var mapTag = '[object Map]',
    objectTag = '[object Object]',
    promiseTag = '[object Promise]',
    setTag = '[object Set]',
    weakMapTag = '[object WeakMap]';

var dataViewTag = '[object DataView]';

/** Used to detect maps, sets, and weakmaps. */
var dataViewCtorString = toSource(DataView),
    mapCtorString = toSource(Map),
    promiseCtorString = toSource(Promise),
    setCtorString = toSource(Set),
    weakMapCtorString = toSource(WeakMap);

/**
 * Gets the `toStringTag` of `value`.
 *
 * @private
 * @param {*} value The value to query.
 * @returns {string} Returns the `toStringTag`.
 */
var getTag = baseGetTag;

// Fallback for data views, maps, sets, and weak maps in IE 11 and promises in Node.js < 6.
if (DataView && getTag(new DataView(new ArrayBuffer(1))) != dataViewTag || Map && getTag(new Map()) != mapTag || Promise && getTag(Promise.resolve()) != promiseTag || Set && getTag(new Set()) != setTag || WeakMap && getTag(new WeakMap()) != weakMapTag) {
    getTag = function getTag(value) {
        var result = baseGetTag(value),
            Ctor = result == objectTag ? value.constructor : undefined,
            ctorString = Ctor ? toSource(Ctor) : '';

        if (ctorString) {
            switch (ctorString) {
                case dataViewCtorString:
                    return dataViewTag;
                case mapCtorString:
                    return mapTag;
                case promiseCtorString:
                    return promiseTag;
                case setCtorString:
                    return setTag;
                case weakMapCtorString:
                    return weakMapTag;
            }
        }
        return result;
    };
}

module.exports = getTag;

/***/ }),
/* 464 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Uint8Array.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var root = __webpack_require__(/*! ./_root */ 22);

/** Built-in value references. */
var Uint8Array = root.Uint8Array;

module.exports = Uint8Array;

/***/ }),
/* 465 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayReduce.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.reduce` for arrays without support for
 * iteratee shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} iteratee The function invoked per iteration.
 * @param {*} [accumulator] The initial value.
 * @param {boolean} [initAccum] Specify using the first element of `array` as
 *  the initial value.
 * @returns {*} Returns the accumulated value.
 */
function arrayReduce(array, iteratee, accumulator, initAccum) {
  var index = -1,
      length = array == null ? 0 : array.length;

  if (initAccum && length) {
    accumulator = array[++index];
  }
  while (++index < length) {
    accumulator = iteratee(accumulator, array[index], index, array);
  }
  return accumulator;
}

module.exports = arrayReduce;

/***/ }),
/* 466 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapToArray.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Converts `map` to its key-value pairs.
 *
 * @private
 * @param {Object} map The map to convert.
 * @returns {Array} Returns the key-value pairs.
 */
function mapToArray(map) {
  var index = -1,
      result = Array(map.size);

  map.forEach(function (value, key) {
    result[++index] = [key, value];
  });
  return result;
}

module.exports = mapToArray;

/***/ }),
/* 467 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setToArray.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Converts `set` to an array of its values.
 *
 * @private
 * @param {Object} set The set to convert.
 * @returns {Array} Returns the values.
 */
function setToArray(set) {
  var index = -1,
      result = Array(set.size);

  set.forEach(function (value) {
    result[++index] = value;
  });
  return result;
}

module.exports = setToArray;

/***/ }),
/* 468 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsEqual.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsEqualDeep = __webpack_require__(/*! ./_baseIsEqualDeep */ 977),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/**
 * The base implementation of `_.isEqual` which supports partial comparisons
 * and tracks traversed objects.
 *
 * @private
 * @param {*} value The value to compare.
 * @param {*} other The other value to compare.
 * @param {boolean} bitmask The bitmask flags.
 *  1 - Unordered comparison
 *  2 - Partial comparison
 * @param {Function} [customizer] The function to customize comparisons.
 * @param {Object} [stack] Tracks traversed `value` and `other` objects.
 * @returns {boolean} Returns `true` if the values are equivalent, else `false`.
 */
function baseIsEqual(value, other, bitmask, customizer, stack) {
  if (value === other) {
    return true;
  }
  if (value == null || other == null || !isObjectLike(value) && !isObjectLike(other)) {
    return value !== value && other !== other;
  }
  return baseIsEqualDeep(value, other, bitmask, customizer, baseIsEqual, stack);
}

module.exports = baseIsEqual;

/***/ }),
/* 469 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_equalArrays.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var SetCache = __webpack_require__(/*! ./_SetCache */ 978),
    arraySome = __webpack_require__(/*! ./_arraySome */ 981),
    cacheHas = __webpack_require__(/*! ./_cacheHas */ 982);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1,
    COMPARE_UNORDERED_FLAG = 2;

/**
 * A specialized version of `baseIsEqualDeep` for arrays with support for
 * partial deep comparisons.
 *
 * @private
 * @param {Array} array The array to compare.
 * @param {Array} other The other array to compare.
 * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
 * @param {Function} customizer The function to customize comparisons.
 * @param {Function} equalFunc The function to determine equivalents of values.
 * @param {Object} stack Tracks traversed `array` and `other` objects.
 * @returns {boolean} Returns `true` if the arrays are equivalent, else `false`.
 */
function equalArrays(array, other, bitmask, customizer, equalFunc, stack) {
  var isPartial = bitmask & COMPARE_PARTIAL_FLAG,
      arrLength = array.length,
      othLength = other.length;

  if (arrLength != othLength && !(isPartial && othLength > arrLength)) {
    return false;
  }
  // Assume cyclic values are equal.
  var stacked = stack.get(array);
  if (stacked && stack.get(other)) {
    return stacked == other;
  }
  var index = -1,
      result = true,
      seen = bitmask & COMPARE_UNORDERED_FLAG ? new SetCache() : undefined;

  stack.set(array, other);
  stack.set(other, array);

  // Ignore non-index properties.
  while (++index < arrLength) {
    var arrValue = array[index],
        othValue = other[index];

    if (customizer) {
      var compared = isPartial ? customizer(othValue, arrValue, index, other, array, stack) : customizer(arrValue, othValue, index, array, other, stack);
    }
    if (compared !== undefined) {
      if (compared) {
        continue;
      }
      result = false;
      break;
    }
    // Recursively compare arrays (susceptible to call stack limits).
    if (seen) {
      if (!arraySome(other, function (othValue, othIndex) {
        if (!cacheHas(seen, othIndex) && (arrValue === othValue || equalFunc(arrValue, othValue, bitmask, customizer, stack))) {
          return seen.push(othIndex);
        }
      })) {
        result = false;
        break;
      }
    } else if (!(arrValue === othValue || equalFunc(arrValue, othValue, bitmask, customizer, stack))) {
      result = false;
      break;
    }
  }
  stack['delete'](array);
  stack['delete'](other);
  return result;
}

module.exports = equalArrays;

/***/ }),
/* 470 */
/*!*************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isStrictComparable.js ***!
  \*************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(/*! ./isObject */ 58);

/**
 * Checks if `value` is suitable for strict equality comparisons, i.e. `===`.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` if suitable for strict
 *  equality comparisons, else `false`.
 */
function isStrictComparable(value) {
  return value === value && !isObject(value);
}

module.exports = isStrictComparable;

/***/ }),
/* 471 */
/*!******************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_matchesStrictComparable.js ***!
  \******************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `matchesProperty` for source values suitable
 * for strict equality comparisons, i.e. `===`.
 *
 * @private
 * @param {string} key The key of the property to get.
 * @param {*} srcValue The value to match.
 * @returns {Function} Returns the new spec function.
 */
function matchesStrictComparable(key, srcValue) {
  return function (object) {
    if (object == null) {
      return false;
    }
    return object[key] === srcValue && (srcValue !== undefined || key in Object(object));
  };
}

module.exports = matchesStrictComparable;

/***/ }),
/* 472 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stringToPath.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var memoizeCapped = __webpack_require__(/*! ./_memoizeCapped */ 988);

/** Used to match property names within property paths. */
var reLeadingDot = /^\./,
    rePropName = /[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g;

/** Used to match backslashes in property paths. */
var reEscapeChar = /\\(\\)?/g;

/**
 * Converts `string` to a property path array.
 *
 * @private
 * @param {string} string The string to convert.
 * @returns {Array} Returns the property path array.
 */
var stringToPath = memoizeCapped(function (string) {
  var result = [];
  if (reLeadingDot.test(string)) {
    result.push('');
  }
  string.replace(rePropName, function (match, number, quote, string) {
    result.push(quote ? string.replace(reEscapeChar, '$1') : number || match);
  });
  return result;
});

module.exports = stringToPath;

/***/ }),
/* 473 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/toString.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseToString = __webpack_require__(/*! ./_baseToString */ 990);

/**
 * Converts `value` to a string. An empty string is returned for `null`
 * and `undefined` values. The sign of `-0` is preserved.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to convert.
 * @returns {string} Returns the converted string.
 * @example
 *
 * _.toString(null);
 * // => ''
 *
 * _.toString(-0);
 * // => '-0'
 *
 * _.toString([1, 2, 3]);
 * // => '1,2,3'
 */
function toString(value) {
  return value == null ? '' : baseToString(value);
}

module.exports = toString;

/***/ }),
/* 474 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_flatRest.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var flatten = __webpack_require__(/*! ./flatten */ 998),
    overRest = __webpack_require__(/*! ./_overRest */ 1001),
    setToString = __webpack_require__(/*! ./_setToString */ 445);

/**
 * A specialized version of `baseRest` which flattens the rest array.
 *
 * @private
 * @param {Function} func The function to apply a rest parameter to.
 * @returns {Function} Returns the new function.
 */
function flatRest(func) {
  return setToString(overRest(func, undefined, flatten), func + '');
}

module.exports = flatRest;

/***/ }),
/* 475 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/urijs/src/punycode.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module, global) {var __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*! https://mths.be/punycode v1.4.0 by @mathias */
;(function (root) {

	/** Detect free variables */
	var freeExports = ( false ? 'undefined' : _typeof(exports)) == 'object' && exports && !exports.nodeType && exports;
	var freeModule = ( false ? 'undefined' : _typeof(module)) == 'object' && module && !module.nodeType && module;
	var freeGlobal = (typeof global === 'undefined' ? 'undefined' : _typeof(global)) == 'object' && global;
	if (freeGlobal.global === freeGlobal || freeGlobal.window === freeGlobal || freeGlobal.self === freeGlobal) {
		root = freeGlobal;
	}

	/**
  * The `punycode` object.
  * @name punycode
  * @type Object
  */
	var punycode,


	/** Highest positive signed 32-bit float value */
	maxInt = 2147483647,
	    // aka. 0x7FFFFFFF or 2^31-1

	/** Bootstring parameters */
	base = 36,
	    tMin = 1,
	    tMax = 26,
	    skew = 38,
	    damp = 700,
	    initialBias = 72,
	    initialN = 128,
	    // 0x80
	delimiter = '-',
	    // '\x2D'

	/** Regular expressions */
	regexPunycode = /^xn--/,
	    regexNonASCII = /[^\x20-\x7E]/,
	    // unprintable ASCII chars + non-ASCII chars
	regexSeparators = /[\x2E\u3002\uFF0E\uFF61]/g,
	    // RFC 3490 separators

	/** Error messages */
	errors = {
		'overflow': 'Overflow: input needs wider integers to process',
		'not-basic': 'Illegal input >= 0x80 (not a basic code point)',
		'invalid-input': 'Invalid input'
	},


	/** Convenience shortcuts */
	baseMinusTMin = base - tMin,
	    floor = Math.floor,
	    stringFromCharCode = String.fromCharCode,


	/** Temporary variable */
	key;

	/*--------------------------------------------------------------------------*/

	/**
  * A generic error utility function.
  * @private
  * @param {String} type The error type.
  * @returns {Error} Throws a `RangeError` with the applicable error message.
  */
	function error(type) {
		throw new RangeError(errors[type]);
	}

	/**
  * A generic `Array#map` utility function.
  * @private
  * @param {Array} array The array to iterate over.
  * @param {Function} callback The function that gets called for every array
  * item.
  * @returns {Array} A new array of values returned by the callback function.
  */
	function map(array, fn) {
		var length = array.length;
		var result = [];
		while (length--) {
			result[length] = fn(array[length]);
		}
		return result;
	}

	/**
  * A simple `Array#map`-like wrapper to work with domain name strings or email
  * addresses.
  * @private
  * @param {String} domain The domain name or email address.
  * @param {Function} callback The function that gets called for every
  * character.
  * @returns {Array} A new string of characters returned by the callback
  * function.
  */
	function mapDomain(string, fn) {
		var parts = string.split('@');
		var result = '';
		if (parts.length > 1) {
			// In email addresses, only the domain name should be punycoded. Leave
			// the local part (i.e. everything up to `@`) intact.
			result = parts[0] + '@';
			string = parts[1];
		}
		// Avoid `split(regex)` for IE8 compatibility. See #17.
		string = string.replace(regexSeparators, '\x2E');
		var labels = string.split('.');
		var encoded = map(labels, fn).join('.');
		return result + encoded;
	}

	/**
  * Creates an array containing the numeric code points of each Unicode
  * character in the string. While JavaScript uses UCS-2 internally,
  * this function will convert a pair of surrogate halves (each of which
  * UCS-2 exposes as separate characters) into a single code point,
  * matching UTF-16.
  * @see `punycode.ucs2.encode`
  * @see <https://mathiasbynens.be/notes/javascript-encoding>
  * @memberOf punycode.ucs2
  * @name decode
  * @param {String} string The Unicode input string (UCS-2).
  * @returns {Array} The new array of code points.
  */
	function ucs2decode(string) {
		var output = [],
		    counter = 0,
		    length = string.length,
		    value,
		    extra;
		while (counter < length) {
			value = string.charCodeAt(counter++);
			if (value >= 0xD800 && value <= 0xDBFF && counter < length) {
				// high surrogate, and there is a next character
				extra = string.charCodeAt(counter++);
				if ((extra & 0xFC00) == 0xDC00) {
					// low surrogate
					output.push(((value & 0x3FF) << 10) + (extra & 0x3FF) + 0x10000);
				} else {
					// unmatched surrogate; only append this code unit, in case the next
					// code unit is the high surrogate of a surrogate pair
					output.push(value);
					counter--;
				}
			} else {
				output.push(value);
			}
		}
		return output;
	}

	/**
  * Creates a string based on an array of numeric code points.
  * @see `punycode.ucs2.decode`
  * @memberOf punycode.ucs2
  * @name encode
  * @param {Array} codePoints The array of numeric code points.
  * @returns {String} The new Unicode string (UCS-2).
  */
	function ucs2encode(array) {
		return map(array, function (value) {
			var output = '';
			if (value > 0xFFFF) {
				value -= 0x10000;
				output += stringFromCharCode(value >>> 10 & 0x3FF | 0xD800);
				value = 0xDC00 | value & 0x3FF;
			}
			output += stringFromCharCode(value);
			return output;
		}).join('');
	}

	/**
  * Converts a basic code point into a digit/integer.
  * @see `digitToBasic()`
  * @private
  * @param {Number} codePoint The basic numeric code point value.
  * @returns {Number} The numeric value of a basic code point (for use in
  * representing integers) in the range `0` to `base - 1`, or `base` if
  * the code point does not represent a value.
  */
	function basicToDigit(codePoint) {
		if (codePoint - 48 < 10) {
			return codePoint - 22;
		}
		if (codePoint - 65 < 26) {
			return codePoint - 65;
		}
		if (codePoint - 97 < 26) {
			return codePoint - 97;
		}
		return base;
	}

	/**
  * Converts a digit/integer into a basic code point.
  * @see `basicToDigit()`
  * @private
  * @param {Number} digit The numeric value of a basic code point.
  * @returns {Number} The basic code point whose value (when used for
  * representing integers) is `digit`, which needs to be in the range
  * `0` to `base - 1`. If `flag` is non-zero, the uppercase form is
  * used; else, the lowercase form is used. The behavior is undefined
  * if `flag` is non-zero and `digit` has no uppercase form.
  */
	function digitToBasic(digit, flag) {
		//  0..25 map to ASCII a..z or A..Z
		// 26..35 map to ASCII 0..9
		return digit + 22 + 75 * (digit < 26) - ((flag != 0) << 5);
	}

	/**
  * Bias adaptation function as per section 3.4 of RFC 3492.
  * https://tools.ietf.org/html/rfc3492#section-3.4
  * @private
  */
	function adapt(delta, numPoints, firstTime) {
		var k = 0;
		delta = firstTime ? floor(delta / damp) : delta >> 1;
		delta += floor(delta / numPoints);
		for (; /* no initialization */delta > baseMinusTMin * tMax >> 1; k += base) {
			delta = floor(delta / baseMinusTMin);
		}
		return floor(k + (baseMinusTMin + 1) * delta / (delta + skew));
	}

	/**
  * Converts a Punycode string of ASCII-only symbols to a string of Unicode
  * symbols.
  * @memberOf punycode
  * @param {String} input The Punycode string of ASCII-only symbols.
  * @returns {String} The resulting string of Unicode symbols.
  */
	function decode(input) {
		// Don't use UCS-2
		var output = [],
		    inputLength = input.length,
		    out,
		    i = 0,
		    n = initialN,
		    bias = initialBias,
		    basic,
		    j,
		    index,
		    oldi,
		    w,
		    k,
		    digit,
		    t,

		/** Cached calculation results */
		baseMinusT;

		// Handle the basic code points: let `basic` be the number of input code
		// points before the last delimiter, or `0` if there is none, then copy
		// the first basic code points to the output.

		basic = input.lastIndexOf(delimiter);
		if (basic < 0) {
			basic = 0;
		}

		for (j = 0; j < basic; ++j) {
			// if it's not a basic code point
			if (input.charCodeAt(j) >= 0x80) {
				error('not-basic');
			}
			output.push(input.charCodeAt(j));
		}

		// Main decoding loop: start just after the last delimiter if any basic code
		// points were copied; start at the beginning otherwise.

		for (index = basic > 0 ? basic + 1 : 0; index < inputLength;) /* no final expression */{

			// `index` is the index of the next character to be consumed.
			// Decode a generalized variable-length integer into `delta`,
			// which gets added to `i`. The overflow checking is easier
			// if we increase `i` as we go, then subtract off its starting
			// value at the end to obtain `delta`.
			for (oldi = i, w = 1, k = base;; /* no condition */k += base) {

				if (index >= inputLength) {
					error('invalid-input');
				}

				digit = basicToDigit(input.charCodeAt(index++));

				if (digit >= base || digit > floor((maxInt - i) / w)) {
					error('overflow');
				}

				i += digit * w;
				t = k <= bias ? tMin : k >= bias + tMax ? tMax : k - bias;

				if (digit < t) {
					break;
				}

				baseMinusT = base - t;
				if (w > floor(maxInt / baseMinusT)) {
					error('overflow');
				}

				w *= baseMinusT;
			}

			out = output.length + 1;
			bias = adapt(i - oldi, out, oldi == 0);

			// `i` was supposed to wrap around from `out` to `0`,
			// incrementing `n` each time, so we'll fix that now:
			if (floor(i / out) > maxInt - n) {
				error('overflow');
			}

			n += floor(i / out);
			i %= out;

			// Insert `n` at position `i` of the output
			output.splice(i++, 0, n);
		}

		return ucs2encode(output);
	}

	/**
  * Converts a string of Unicode symbols (e.g. a domain name label) to a
  * Punycode string of ASCII-only symbols.
  * @memberOf punycode
  * @param {String} input The string of Unicode symbols.
  * @returns {String} The resulting Punycode string of ASCII-only symbols.
  */
	function encode(input) {
		var n,
		    delta,
		    handledCPCount,
		    basicLength,
		    bias,
		    j,
		    m,
		    q,
		    k,
		    t,
		    currentValue,
		    output = [],

		/** `inputLength` will hold the number of code points in `input`. */
		inputLength,

		/** Cached calculation results */
		handledCPCountPlusOne,
		    baseMinusT,
		    qMinusT;

		// Convert the input in UCS-2 to Unicode
		input = ucs2decode(input);

		// Cache the length
		inputLength = input.length;

		// Initialize the state
		n = initialN;
		delta = 0;
		bias = initialBias;

		// Handle the basic code points
		for (j = 0; j < inputLength; ++j) {
			currentValue = input[j];
			if (currentValue < 0x80) {
				output.push(stringFromCharCode(currentValue));
			}
		}

		handledCPCount = basicLength = output.length;

		// `handledCPCount` is the number of code points that have been handled;
		// `basicLength` is the number of basic code points.

		// Finish the basic string - if it is not empty - with a delimiter
		if (basicLength) {
			output.push(delimiter);
		}

		// Main encoding loop:
		while (handledCPCount < inputLength) {

			// All non-basic code points < n have been handled already. Find the next
			// larger one:
			for (m = maxInt, j = 0; j < inputLength; ++j) {
				currentValue = input[j];
				if (currentValue >= n && currentValue < m) {
					m = currentValue;
				}
			}

			// Increase `delta` enough to advance the decoder's <n,i> state to <m,0>,
			// but guard against overflow
			handledCPCountPlusOne = handledCPCount + 1;
			if (m - n > floor((maxInt - delta) / handledCPCountPlusOne)) {
				error('overflow');
			}

			delta += (m - n) * handledCPCountPlusOne;
			n = m;

			for (j = 0; j < inputLength; ++j) {
				currentValue = input[j];

				if (currentValue < n && ++delta > maxInt) {
					error('overflow');
				}

				if (currentValue == n) {
					// Represent delta as a generalized variable-length integer
					for (q = delta, k = base;; /* no condition */k += base) {
						t = k <= bias ? tMin : k >= bias + tMax ? tMax : k - bias;
						if (q < t) {
							break;
						}
						qMinusT = q - t;
						baseMinusT = base - t;
						output.push(stringFromCharCode(digitToBasic(t + qMinusT % baseMinusT, 0)));
						q = floor(qMinusT / baseMinusT);
					}

					output.push(stringFromCharCode(digitToBasic(q, 0)));
					bias = adapt(delta, handledCPCountPlusOne, handledCPCount == basicLength);
					delta = 0;
					++handledCPCount;
				}
			}

			++delta;
			++n;
		}
		return output.join('');
	}

	/**
  * Converts a Punycode string representing a domain name or an email address
  * to Unicode. Only the Punycoded parts of the input will be converted, i.e.
  * it doesn't matter if you call it on a string that has already been
  * converted to Unicode.
  * @memberOf punycode
  * @param {String} input The Punycoded domain name or email address to
  * convert to Unicode.
  * @returns {String} The Unicode representation of the given Punycode
  * string.
  */
	function toUnicode(input) {
		return mapDomain(input, function (string) {
			return regexPunycode.test(string) ? decode(string.slice(4).toLowerCase()) : string;
		});
	}

	/**
  * Converts a Unicode string representing a domain name or an email address to
  * Punycode. Only the non-ASCII parts of the domain name will be converted,
  * i.e. it doesn't matter if you call it with a domain that's already in
  * ASCII.
  * @memberOf punycode
  * @param {String} input The domain name or email address to convert, as a
  * Unicode string.
  * @returns {String} The Punycode representation of the given domain name or
  * email address.
  */
	function toASCII(input) {
		return mapDomain(input, function (string) {
			return regexNonASCII.test(string) ? 'xn--' + encode(string) : string;
		});
	}

	/*--------------------------------------------------------------------------*/

	/** Define the public API */
	punycode = {
		/**
   * A string representing the current Punycode.js version number.
   * @memberOf punycode
   * @type String
   */
		'version': '1.3.2',
		/**
   * An object of methods to convert from JavaScript's internal character
   * representation (UCS-2) to Unicode code points, and back.
   * @see <https://mathiasbynens.be/notes/javascript-encoding>
   * @memberOf punycode
   * @type Object
   */
		'ucs2': {
			'decode': ucs2decode,
			'encode': ucs2encode
		},
		'decode': decode,
		'encode': encode,
		'toASCII': toASCII,
		'toUnicode': toUnicode
	};

	/** Expose `punycode` */
	// Some AMD build optimizers, like r.js, check for specific condition patterns
	// like the following:
	if ("function" == 'function' && _typeof(__webpack_require__(/*! !webpack amd options */ 56)) == 'object' && __webpack_require__(/*! !webpack amd options */ 56)) {
		!(__WEBPACK_AMD_DEFINE_RESULT__ = function () {
			return punycode;
		}.call(exports, __webpack_require__, exports, module),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	} else if (freeExports && freeModule) {
		if (module.exports == freeExports) {
			// in Node.js, io.js, or RingoJS v0.8.0+
			freeModule.exports = punycode;
		} else {
			// in Narwhal or RingoJS v0.7.0-
			for (key in punycode) {
				punycode.hasOwnProperty(key) && (freeExports[key] = punycode[key]);
			}
		}
	} else {
		// in Rhino or a web browser
		root.punycode = punycode;
	}
})(undefined);
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 6)(module), __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/global.js */ 19)))

/***/ }),
/* 476 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/urijs/src/IPv6.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 * IPv6 Support
 *
 * Version: 1.18.10
 *
 * Author: Rodney Rehm
 * Web: http://medialize.github.io/URI.js/
 *
 * Licensed under
 *   MIT License http://www.opensource.org/licenses/mit-license
 *
 */

(function (root, factory) {
  'use strict';
  // https://github.com/umdjs/umd/blob/master/returnExports.js

  if (( false ? 'undefined' : _typeof(module)) === 'object' && module.exports) {
    // Node
    module.exports = factory();
  } else if (true) {
    // AMD. Register as an anonymous module.
    !(__WEBPACK_AMD_DEFINE_FACTORY__ = (factory),
				__WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ?
				(__WEBPACK_AMD_DEFINE_FACTORY__.call(exports, __webpack_require__, exports, module)) :
				__WEBPACK_AMD_DEFINE_FACTORY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
  } else {
    // Browser globals (root is window)
    root.IPv6 = factory(root);
  }
})(undefined, function (root) {
  'use strict';

  /*
  var _in = "fe80:0000:0000:0000:0204:61ff:fe9d:f156";
  var _out = IPv6.best(_in);
  var _expected = "fe80::204:61ff:fe9d:f156";
   console.log(_in, _out, _expected, _out === _expected);
  */

  // save current IPv6 variable, if any

  var _IPv6 = root && root.IPv6;

  function bestPresentation(address) {
    // based on:
    // Javascript to test an IPv6 address for proper format, and to
    // present the "best text representation" according to IETF Draft RFC at
    // http://tools.ietf.org/html/draft-ietf-6man-text-addr-representation-04
    // 8 Feb 2010 Rich Brown, Dartware, LLC
    // Please feel free to use this code as long as you provide a link to
    // http://www.intermapper.com
    // http://intermapper.com/support/tools/IPV6-Validator.aspx
    // http://download.dartware.com/thirdparty/ipv6validator.js

    var _address = address.toLowerCase();
    var segments = _address.split(':');
    var length = segments.length;
    var total = 8;

    // trim colons (:: or ::a:b:c… or …a:b:c::)
    if (segments[0] === '' && segments[1] === '' && segments[2] === '') {
      // must have been ::
      // remove first two items
      segments.shift();
      segments.shift();
    } else if (segments[0] === '' && segments[1] === '') {
      // must have been ::xxxx
      // remove the first item
      segments.shift();
    } else if (segments[length - 1] === '' && segments[length - 2] === '') {
      // must have been xxxx::
      segments.pop();
    }

    length = segments.length;

    // adjust total segments for IPv4 trailer
    if (segments[length - 1].indexOf('.') !== -1) {
      // found a "." which means IPv4
      total = 7;
    }

    // fill empty segments them with "0000"
    var pos;
    for (pos = 0; pos < length; pos++) {
      if (segments[pos] === '') {
        break;
      }
    }

    if (pos < total) {
      segments.splice(pos, 1, '0000');
      while (segments.length < total) {
        segments.splice(pos, 0, '0000');
      }
    }

    // strip leading zeros
    var _segments;
    for (var i = 0; i < total; i++) {
      _segments = segments[i].split('');
      for (var j = 0; j < 3; j++) {
        if (_segments[0] === '0' && _segments.length > 1) {
          _segments.splice(0, 1);
        } else {
          break;
        }
      }

      segments[i] = _segments.join('');
    }

    // find longest sequence of zeroes and coalesce them into one segment
    var best = -1;
    var _best = 0;
    var _current = 0;
    var current = -1;
    var inzeroes = false;
    // i; already declared

    for (i = 0; i < total; i++) {
      if (inzeroes) {
        if (segments[i] === '0') {
          _current += 1;
        } else {
          inzeroes = false;
          if (_current > _best) {
            best = current;
            _best = _current;
          }
        }
      } else {
        if (segments[i] === '0') {
          inzeroes = true;
          current = i;
          _current = 1;
        }
      }
    }

    if (_current > _best) {
      best = current;
      _best = _current;
    }

    if (_best > 1) {
      segments.splice(best, _best, '');
    }

    length = segments.length;

    // assemble remaining segments
    var result = '';
    if (segments[0] === '') {
      result = ':';
    }

    for (i = 0; i < length; i++) {
      result += segments[i];
      if (i === length - 1) {
        break;
      }

      result += ':';
    }

    if (segments[length - 1] === '') {
      result += ':';
    }

    return result;
  }

  function noConflict() {
    /*jshint validthis: true */
    if (root.IPv6 === this) {
      root.IPv6 = _IPv6;
    }

    return this;
  }

  return {
    best: bestPresentation,
    noConflict: noConflict
  };
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 477 */
/*!***************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 * Second Level Domain (SLD) Support
 *
 * Version: 1.18.10
 *
 * Author: Rodney Rehm
 * Web: http://medialize.github.io/URI.js/
 *
 * Licensed under
 *   MIT License http://www.opensource.org/licenses/mit-license
 *
 */

(function (root, factory) {
  'use strict';
  // https://github.com/umdjs/umd/blob/master/returnExports.js

  if (( false ? 'undefined' : _typeof(module)) === 'object' && module.exports) {
    // Node
    module.exports = factory();
  } else if (true) {
    // AMD. Register as an anonymous module.
    !(__WEBPACK_AMD_DEFINE_FACTORY__ = (factory),
				__WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ?
				(__WEBPACK_AMD_DEFINE_FACTORY__.call(exports, __webpack_require__, exports, module)) :
				__WEBPACK_AMD_DEFINE_FACTORY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
  } else {
    // Browser globals (root is window)
    root.SecondLevelDomains = factory(root);
  }
})(undefined, function (root) {
  'use strict';

  // save current SecondLevelDomains variable, if any

  var _SecondLevelDomains = root && root.SecondLevelDomains;

  var SLD = {
    // list of known Second Level Domains
    // converted list of SLDs from https://github.com/gavingmiller/second-level-domains
    // ----
    // publicsuffix.org is more current and actually used by a couple of browsers internally.
    // downside is it also contains domains like "dyndns.org" - which is fine for the security
    // issues browser have to deal with (SOP for cookies, etc) - but is way overboard for URI.js
    // ----
    list: {
      'ac': ' com gov mil net org ',
      'ae': ' ac co gov mil name net org pro sch ',
      'af': ' com edu gov net org ',
      'al': ' com edu gov mil net org ',
      'ao': ' co ed gv it og pb ',
      'ar': ' com edu gob gov int mil net org tur ',
      'at': ' ac co gv or ',
      'au': ' asn com csiro edu gov id net org ',
      'ba': ' co com edu gov mil net org rs unbi unmo unsa untz unze ',
      'bb': ' biz co com edu gov info net org store tv ',
      'bh': ' biz cc com edu gov info net org ',
      'bn': ' com edu gov net org ',
      'bo': ' com edu gob gov int mil net org tv ',
      'br': ' adm adv agr am arq art ato b bio blog bmd cim cng cnt com coop ecn edu eng esp etc eti far flog fm fnd fot fst g12 ggf gov imb ind inf jor jus lel mat med mil mus net nom not ntr odo org ppg pro psc psi qsl rec slg srv tmp trd tur tv vet vlog wiki zlg ',
      'bs': ' com edu gov net org ',
      'bz': ' du et om ov rg ',
      'ca': ' ab bc mb nb nf nl ns nt nu on pe qc sk yk ',
      'ck': ' biz co edu gen gov info net org ',
      'cn': ' ac ah bj com cq edu fj gd gov gs gx gz ha hb he hi hl hn jl js jx ln mil net nm nx org qh sc sd sh sn sx tj tw xj xz yn zj ',
      'co': ' com edu gov mil net nom org ',
      'cr': ' ac c co ed fi go or sa ',
      'cy': ' ac biz com ekloges gov ltd name net org parliament press pro tm ',
      'do': ' art com edu gob gov mil net org sld web ',
      'dz': ' art asso com edu gov net org pol ',
      'ec': ' com edu fin gov info med mil net org pro ',
      'eg': ' com edu eun gov mil name net org sci ',
      'er': ' com edu gov ind mil net org rochest w ',
      'es': ' com edu gob nom org ',
      'et': ' biz com edu gov info name net org ',
      'fj': ' ac biz com info mil name net org pro ',
      'fk': ' ac co gov net nom org ',
      'fr': ' asso com f gouv nom prd presse tm ',
      'gg': ' co net org ',
      'gh': ' com edu gov mil org ',
      'gn': ' ac com gov net org ',
      'gr': ' com edu gov mil net org ',
      'gt': ' com edu gob ind mil net org ',
      'gu': ' com edu gov net org ',
      'hk': ' com edu gov idv net org ',
      'hu': ' 2000 agrar bolt casino city co erotica erotika film forum games hotel info ingatlan jogasz konyvelo lakas media news org priv reklam sex shop sport suli szex tm tozsde utazas video ',
      'id': ' ac co go mil net or sch web ',
      'il': ' ac co gov idf k12 muni net org ',
      'in': ' ac co edu ernet firm gen gov i ind mil net nic org res ',
      'iq': ' com edu gov i mil net org ',
      'ir': ' ac co dnssec gov i id net org sch ',
      'it': ' edu gov ',
      'je': ' co net org ',
      'jo': ' com edu gov mil name net org sch ',
      'jp': ' ac ad co ed go gr lg ne or ',
      'ke': ' ac co go info me mobi ne or sc ',
      'kh': ' com edu gov mil net org per ',
      'ki': ' biz com de edu gov info mob net org tel ',
      'km': ' asso com coop edu gouv k medecin mil nom notaires pharmaciens presse tm veterinaire ',
      'kn': ' edu gov net org ',
      'kr': ' ac busan chungbuk chungnam co daegu daejeon es gangwon go gwangju gyeongbuk gyeonggi gyeongnam hs incheon jeju jeonbuk jeonnam k kg mil ms ne or pe re sc seoul ulsan ',
      'kw': ' com edu gov net org ',
      'ky': ' com edu gov net org ',
      'kz': ' com edu gov mil net org ',
      'lb': ' com edu gov net org ',
      'lk': ' assn com edu gov grp hotel int ltd net ngo org sch soc web ',
      'lr': ' com edu gov net org ',
      'lv': ' asn com conf edu gov id mil net org ',
      'ly': ' com edu gov id med net org plc sch ',
      'ma': ' ac co gov m net org press ',
      'mc': ' asso tm ',
      'me': ' ac co edu gov its net org priv ',
      'mg': ' com edu gov mil nom org prd tm ',
      'mk': ' com edu gov inf name net org pro ',
      'ml': ' com edu gov net org presse ',
      'mn': ' edu gov org ',
      'mo': ' com edu gov net org ',
      'mt': ' com edu gov net org ',
      'mv': ' aero biz com coop edu gov info int mil museum name net org pro ',
      'mw': ' ac co com coop edu gov int museum net org ',
      'mx': ' com edu gob net org ',
      'my': ' com edu gov mil name net org sch ',
      'nf': ' arts com firm info net other per rec store web ',
      'ng': ' biz com edu gov mil mobi name net org sch ',
      'ni': ' ac co com edu gob mil net nom org ',
      'np': ' com edu gov mil net org ',
      'nr': ' biz com edu gov info net org ',
      'om': ' ac biz co com edu gov med mil museum net org pro sch ',
      'pe': ' com edu gob mil net nom org sld ',
      'ph': ' com edu gov i mil net ngo org ',
      'pk': ' biz com edu fam gob gok gon gop gos gov net org web ',
      'pl': ' art bialystok biz com edu gda gdansk gorzow gov info katowice krakow lodz lublin mil net ngo olsztyn org poznan pwr radom slupsk szczecin torun warszawa waw wroc wroclaw zgora ',
      'pr': ' ac biz com edu est gov info isla name net org pro prof ',
      'ps': ' com edu gov net org plo sec ',
      'pw': ' belau co ed go ne or ',
      'ro': ' arts com firm info nom nt org rec store tm www ',
      'rs': ' ac co edu gov in org ',
      'sb': ' com edu gov net org ',
      'sc': ' com edu gov net org ',
      'sh': ' co com edu gov net nom org ',
      'sl': ' com edu gov net org ',
      'st': ' co com consulado edu embaixada gov mil net org principe saotome store ',
      'sv': ' com edu gob org red ',
      'sz': ' ac co org ',
      'tr': ' av bbs bel biz com dr edu gen gov info k12 name net org pol tel tsk tv web ',
      'tt': ' aero biz cat co com coop edu gov info int jobs mil mobi museum name net org pro tel travel ',
      'tw': ' club com ebiz edu game gov idv mil net org ',
      'mu': ' ac co com gov net or org ',
      'mz': ' ac co edu gov org ',
      'na': ' co com ',
      'nz': ' ac co cri geek gen govt health iwi maori mil net org parliament school ',
      'pa': ' abo ac com edu gob ing med net nom org sld ',
      'pt': ' com edu gov int net nome org publ ',
      'py': ' com edu gov mil net org ',
      'qa': ' com edu gov mil net org ',
      're': ' asso com nom ',
      'ru': ' ac adygeya altai amur arkhangelsk astrakhan bashkiria belgorod bir bryansk buryatia cbg chel chelyabinsk chita chukotka chuvashia com dagestan e-burg edu gov grozny int irkutsk ivanovo izhevsk jar joshkar-ola kalmykia kaluga kamchatka karelia kazan kchr kemerovo khabarovsk khakassia khv kirov koenig komi kostroma kranoyarsk kuban kurgan kursk lipetsk magadan mari mari-el marine mil mordovia mosreg msk murmansk nalchik net nnov nov novosibirsk nsk omsk orenburg org oryol penza perm pp pskov ptz rnd ryazan sakhalin samara saratov simbirsk smolensk spb stavropol stv surgut tambov tatarstan tom tomsk tsaritsyn tsk tula tuva tver tyumen udm udmurtia ulan-ude vladikavkaz vladimir vladivostok volgograd vologda voronezh vrn vyatka yakutia yamal yekaterinburg yuzhno-sakhalinsk ',
      'rw': ' ac co com edu gouv gov int mil net ',
      'sa': ' com edu gov med net org pub sch ',
      'sd': ' com edu gov info med net org tv ',
      'se': ' a ac b bd c d e f g h i k l m n o org p parti pp press r s t tm u w x y z ',
      'sg': ' com edu gov idn net org per ',
      'sn': ' art com edu gouv org perso univ ',
      'sy': ' com edu gov mil net news org ',
      'th': ' ac co go in mi net or ',
      'tj': ' ac biz co com edu go gov info int mil name net nic org test web ',
      'tn': ' agrinet com defense edunet ens fin gov ind info intl mincom nat net org perso rnrt rns rnu tourism ',
      'tz': ' ac co go ne or ',
      'ua': ' biz cherkassy chernigov chernovtsy ck cn co com crimea cv dn dnepropetrovsk donetsk dp edu gov if in ivano-frankivsk kh kharkov kherson khmelnitskiy kiev kirovograd km kr ks kv lg lugansk lutsk lviv me mk net nikolaev od odessa org pl poltava pp rovno rv sebastopol sumy te ternopil uzhgorod vinnica vn zaporizhzhe zhitomir zp zt ',
      'ug': ' ac co go ne or org sc ',
      'uk': ' ac bl british-library co cym gov govt icnet jet lea ltd me mil mod national-library-scotland nel net nhs nic nls org orgn parliament plc police sch scot soc ',
      'us': ' dni fed isa kids nsn ',
      'uy': ' com edu gub mil net org ',
      've': ' co com edu gob info mil net org web ',
      'vi': ' co com k12 net org ',
      'vn': ' ac biz com edu gov health info int name net org pro ',
      'ye': ' co com gov ltd me net org plc ',
      'yu': ' ac co edu gov org ',
      'za': ' ac agric alt bourse city co cybernet db edu gov grondar iaccess imt inca landesign law mil net ngo nis nom olivetti org pix school tm web ',
      'zm': ' ac co com edu gov net org sch ',
      // https://en.wikipedia.org/wiki/CentralNic#Second-level_domains
      'com': 'ar br cn de eu gb gr hu jpn kr no qc ru sa se uk us uy za ',
      'net': 'gb jp se uk ',
      'org': 'ae',
      'de': 'com '
    },
    // gorhill 2013-10-25: Using indexOf() instead Regexp(). Significant boost
    // in both performance and memory footprint. No initialization required.
    // http://jsperf.com/uri-js-sld-regex-vs-binary-search/4
    // Following methods use lastIndexOf() rather than array.split() in order
    // to avoid any memory allocations.
    has: function has(domain) {
      var tldOffset = domain.lastIndexOf('.');
      if (tldOffset <= 0 || tldOffset >= domain.length - 1) {
        return false;
      }
      var sldOffset = domain.lastIndexOf('.', tldOffset - 1);
      if (sldOffset <= 0 || sldOffset >= tldOffset - 1) {
        return false;
      }
      var sldList = SLD.list[domain.slice(tldOffset + 1)];
      if (!sldList) {
        return false;
      }
      return sldList.indexOf(' ' + domain.slice(sldOffset + 1, tldOffset) + ' ') >= 0;
    },
    is: function is(domain) {
      var tldOffset = domain.lastIndexOf('.');
      if (tldOffset <= 0 || tldOffset >= domain.length - 1) {
        return false;
      }
      var sldOffset = domain.lastIndexOf('.', tldOffset - 1);
      if (sldOffset >= 0) {
        return false;
      }
      var sldList = SLD.list[domain.slice(tldOffset + 1)];
      if (!sldList) {
        return false;
      }
      return sldList.indexOf(' ' + domain.slice(0, tldOffset) + ' ') >= 0;
    },
    get: function get(domain) {
      var tldOffset = domain.lastIndexOf('.');
      if (tldOffset <= 0 || tldOffset >= domain.length - 1) {
        return null;
      }
      var sldOffset = domain.lastIndexOf('.', tldOffset - 1);
      if (sldOffset <= 0 || sldOffset >= tldOffset - 1) {
        return null;
      }
      var sldList = SLD.list[domain.slice(tldOffset + 1)];
      if (!sldList) {
        return null;
      }
      if (sldList.indexOf(' ' + domain.slice(sldOffset + 1, tldOffset) + ' ') < 0) {
        return null;
      }
      return domain.slice(sldOffset + 1);
    },
    noConflict: function noConflict() {
      if (root.SecondLevelDomains === this) {
        root.SecondLevelDomains = _SecondLevelDomains;
      }
      return this;
    }
  };

  return SLD;
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 478 */,
/* 479 */,
/* 480 */,
/* 481 */,
/* 482 */,
/* 483 */,
/* 484 */,
/* 485 */,
/* 486 */,
/* 487 */,
/* 488 */,
/* 489 */,
/* 490 */,
/* 491 */,
/* 492 */,
/* 493 */,
/* 494 */,
/* 495 */,
/* 496 */,
/* 497 */,
/* 498 */,
/* 499 */,
/* 500 */,
/* 501 */,
/* 502 */,
/* 503 */,
/* 504 */,
/* 505 */,
/* 506 */,
/* 507 */,
/* 508 */,
/* 509 */,
/* 510 */,
/* 511 */,
/* 512 */,
/* 513 */,
/* 514 */,
/* 515 */,
/* 516 */,
/* 517 */,
/* 518 */,
/* 519 */,
/* 520 */,
/* 521 */,
/* 522 */,
/* 523 */,
/* 524 */,
/* 525 */,
/* 526 */,
/* 527 */,
/* 528 */,
/* 529 */,
/* 530 */,
/* 531 */,
/* 532 */,
/* 533 */,
/* 534 */,
/* 535 */,
/* 536 */,
/* 537 */,
/* 538 */,
/* 539 */,
/* 540 */,
/* 541 */,
/* 542 */,
/* 543 */,
/* 544 */,
/* 545 */,
/* 546 */,
/* 547 */,
/* 548 */,
/* 549 */,
/* 550 */,
/* 551 */,
/* 552 */,
/* 553 */,
/* 554 */,
/* 555 */,
/* 556 */,
/* 557 */,
/* 558 */,
/* 559 */,
/* 560 */,
/* 561 */,
/* 562 */,
/* 563 */,
/* 564 */,
/* 565 */,
/* 566 */,
/* 567 */,
/* 568 */,
/* 569 */,
/* 570 */,
/* 571 */,
/* 572 */,
/* 573 */,
/* 574 */,
/* 575 */,
/* 576 */,
/* 577 */,
/* 578 */,
/* 579 */,
/* 580 */,
/* 581 */,
/* 582 */,
/* 583 */,
/* 584 */,
/* 585 */,
/* 586 */,
/* 587 */,
/* 588 */,
/* 589 */,
/* 590 */,
/* 591 */,
/* 592 */,
/* 593 */,
/* 594 */,
/* 595 */,
/* 596 */,
/* 597 */,
/* 598 */,
/* 599 */,
/* 600 */,
/* 601 */,
/* 602 */,
/* 603 */,
/* 604 */,
/* 605 */,
/* 606 */,
/* 607 */,
/* 608 */,
/* 609 */,
/* 610 */,
/* 611 */,
/* 612 */,
/* 613 */,
/* 614 */,
/* 615 */,
/* 616 */,
/* 617 */,
/* 618 */,
/* 619 */,
/* 620 */,
/* 621 */,
/* 622 */,
/* 623 */,
/* 624 */,
/* 625 */,
/* 626 */,
/* 627 */,
/* 628 */,
/* 629 */,
/* 630 */,
/* 631 */,
/* 632 */,
/* 633 */,
/* 634 */,
/* 635 */,
/* 636 */,
/* 637 */,
/* 638 */,
/* 639 */,
/* 640 */,
/* 641 */,
/* 642 */,
/* 643 */,
/* 644 */,
/* 645 */,
/* 646 */,
/* 647 */,
/* 648 */,
/* 649 */,
/* 650 */,
/* 651 */,
/* 652 */,
/* 653 */,
/* 654 */,
/* 655 */,
/* 656 */,
/* 657 */,
/* 658 */,
/* 659 */,
/* 660 */,
/* 661 */,
/* 662 */,
/* 663 */,
/* 664 */,
/* 665 */,
/* 666 */,
/* 667 */,
/* 668 */,
/* 669 */,
/* 670 */,
/* 671 */,
/* 672 */,
/* 673 */,
/* 674 */,
/* 675 */,
/* 676 */,
/* 677 */,
/* 678 */,
/* 679 */,
/* 680 */,
/* 681 */,
/* 682 */,
/* 683 */,
/* 684 */,
/* 685 */,
/* 686 */,
/* 687 */,
/* 688 */,
/* 689 */,
/* 690 */,
/* 691 */,
/* 692 */,
/* 693 */,
/* 694 */,
/* 695 */,
/* 696 */,
/* 697 */,
/* 698 */,
/* 699 */,
/* 700 */,
/* 701 */,
/* 702 */,
/* 703 */,
/* 704 */,
/* 705 */,
/* 706 */,
/* 707 */,
/* 708 */,
/* 709 */,
/* 710 */,
/* 711 */,
/* 712 */,
/* 713 */,
/* 714 */,
/* 715 */,
/* 716 */,
/* 717 */,
/* 718 */,
/* 719 */,
/* 720 */,
/* 721 */,
/* 722 */,
/* 723 */,
/* 724 */,
/* 725 */,
/* 726 */,
/* 727 */,
/* 728 */,
/* 729 */,
/* 730 */,
/* 731 */,
/* 732 */,
/* 733 */,
/* 734 */,
/* 735 */,
/* 736 */,
/* 737 */,
/* 738 */,
/* 739 */,
/* 740 */,
/* 741 */,
/* 742 */,
/* 743 */,
/* 744 */,
/* 745 */,
/* 746 */,
/* 747 */,
/* 748 */,
/* 749 */,
/* 750 */,
/* 751 */,
/* 752 */,
/* 753 */,
/* 754 */,
/* 755 */,
/* 756 */,
/* 757 */,
/* 758 */,
/* 759 */,
/* 760 */,
/* 761 */,
/* 762 */,
/* 763 */,
/* 764 */,
/* 765 */,
/* 766 */,
/* 767 */,
/* 768 */,
/* 769 */,
/* 770 */,
/* 771 */,
/* 772 */,
/* 773 */,
/* 774 */,
/* 775 */,
/* 776 */,
/* 777 */,
/* 778 */,
/* 779 */,
/* 780 */,
/* 781 */,
/* 782 */,
/* 783 */,
/* 784 */,
/* 785 */,
/* 786 */,
/* 787 */,
/* 788 */,
/* 789 */,
/* 790 */,
/* 791 */,
/* 792 */,
/* 793 */,
/* 794 */,
/* 795 */,
/* 796 */,
/* 797 */,
/* 798 */,
/* 799 */,
/* 800 */,
/* 801 */,
/* 802 */,
/* 803 */,
/* 804 */,
/* 805 */,
/* 806 */,
/* 807 */,
/* 808 */,
/* 809 */,
/* 810 */,
/* 811 */,
/* 812 */,
/* 813 */,
/* 814 */,
/* 815 */,
/* 816 */,
/* 817 */,
/* 818 */,
/* 819 */,
/* 820 */,
/* 821 */,
/* 822 */,
/* 823 */,
/* 824 */,
/* 825 */,
/* 826 */,
/* 827 */,
/* 828 */,
/* 829 */,
/* 830 */,
/* 831 */,
/* 832 */,
/* 833 */,
/* 834 */,
/* 835 */,
/* 836 */,
/* 837 */,
/* 838 */,
/* 839 */,
/* 840 */,
/* 841 */,
/* 842 */,
/* 843 */,
/* 844 */,
/* 845 */,
/* 846 */,
/* 847 */,
/* 848 */,
/* 849 */,
/* 850 */,
/* 851 */,
/* 852 */,
/* 853 */,
/* 854 */,
/* 855 */,
/* 856 */,
/* 857 */,
/* 858 */,
/* 859 */,
/* 860 */,
/* 861 */,
/* 862 */,
/* 863 */,
/* 864 */,
/* 865 */,
/* 866 */,
/* 867 */,
/* 868 */,
/* 869 */,
/* 870 */
/*!************************************!*\
  !*** multi ./bundles/marker-genes ***!
  \************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ./bundles/marker-genes */871);


/***/ }),
/* 871 */
/*!***************************************!*\
  !*** ./bundles/marker-genes/index.js ***!
  \***************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = undefined;

var _react = __webpack_require__(/*! react */ 2);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 51);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _atlasMarkerGenesSearchResults = __webpack_require__(/*! atlas-marker-genes-search-results */ 872);

var _atlasMarkerGenesSearchResults2 = _interopRequireDefault(_atlasMarkerGenesSearchResults);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, target) {
  _reactDom2.default.render(_react2.default.createElement(_atlasMarkerGenesSearchResults2.default, options), document.getElementById(target));
};

exports.render = render;

/***/ }),
/* 872 */
/*!******************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/atlas-marker-genes-search-results/lib/index.js ***!
  \******************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _MarkerGeneSearchResults = __webpack_require__(/*! ./MarkerGeneSearchResults.js */ 873);

var _MarkerGeneSearchResults2 = _interopRequireDefault(_MarkerGeneSearchResults);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

exports.default = _MarkerGeneSearchResults2.default;

/***/ }),
/* 873 */
/*!************************************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/atlas-marker-genes-search-results/lib/MarkerGeneSearchResults.js ***!
  \************************************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _react = __webpack_require__(/*! react */ 2);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 422);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactRefetch = __webpack_require__(/*! react-refetch */ 876);

var _urijs = __webpack_require__(/*! urijs */ 1010);

var _urijs2 = _interopRequireDefault(_urijs);

var _FlaskLoaderIcon = __webpack_require__(/*! ./FlaskLoaderIcon.js */ 1011);

var _FlaskLoaderIcon2 = _interopRequireDefault(_FlaskLoaderIcon);

var _MarkerGeneProfile = __webpack_require__(/*! ./MarkerGeneProfile.js */ 1012);

var _MarkerGeneProfile2 = _interopRequireDefault(_MarkerGeneProfile);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var MarkerGeneSearchResults = function MarkerGeneSearchResults(props) {
  var markerGeneFetch = props.markerGeneFetch;

  if (markerGeneFetch.fulfilled) {
    return _react2.default.createElement('div', { className: 'row margin-bottom-xlarge' }, _react2.default.createElement('div', { className: 'small-12 columns' }, markerGeneFetch.value.length > 0 ? markerGeneFetch.value.map(function (e) {
      return _react2.default.createElement('div', { className: 'column row ' }, _react2.default.createElement(_MarkerGeneProfile2.default, _extends({ key: e.url }, e)));
    }) : _react2.default.createElement('p', null, 'Sorry, no marker gene profiles could be found for ', props.geneId, '.')));
  } else if (markerGeneFetch.pending) {
    return _react2.default.createElement('div', { className: 'row' }, _react2.default.createElement('div', { className: 'small-12 columns text-center' }, _react2.default.createElement(_FlaskLoaderIcon2.default, { width: '50', height: '50' })));
  } else {
    return _react2.default.createElement('div', { className: 'row' }, _react2.default.createElement('div', { style: { textAlign: 'center' }, className: 'small-12 columns text-center' }, _react2.default.createElement('p', null, 'Error loading search results')));
  }
};

MarkerGeneSearchResults.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  geneId: _propTypes2.default.string.isRequired
};

exports.default = (0, _reactRefetch.connect)(function (props) {
  return {
    markerGeneFetch: (0, _urijs2.default)('json/markerGenes/' + props.geneId, props.atlasUrl).toString()
  };
})(MarkerGeneSearchResults);

/***/ }),
/* 874 */
/*!*********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/prop-types/factoryWithTypeCheckers.js ***!
  \*********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 423);
var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 424);
var warning = __webpack_require__(/*! fbjs/lib/warning */ 425);

var ReactPropTypesSecret = __webpack_require__(/*! ./lib/ReactPropTypesSecret */ 426);
var checkPropTypes = __webpack_require__(/*! ./checkPropTypes */ 875);

module.exports = function (isValidElement, throwOnDirectAccess) {
  /* global Symbol */
  var ITERATOR_SYMBOL = typeof Symbol === 'function' && Symbol.iterator;
  var FAUX_ITERATOR_SYMBOL = '@@iterator'; // Before Symbol spec.

  /**
   * Returns the iterator method function contained on the iterable object.
   *
   * Be sure to invoke the function with the iterable as context:
   *
   *     var iteratorFn = getIteratorFn(myIterable);
   *     if (iteratorFn) {
   *       var iterator = iteratorFn.call(myIterable);
   *       ...
   *     }
   *
   * @param {?object} maybeIterable
   * @return {?function}
   */
  function getIteratorFn(maybeIterable) {
    var iteratorFn = maybeIterable && (ITERATOR_SYMBOL && maybeIterable[ITERATOR_SYMBOL] || maybeIterable[FAUX_ITERATOR_SYMBOL]);
    if (typeof iteratorFn === 'function') {
      return iteratorFn;
    }
  }

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

  // Important!
  // Keep this list in sync with production version in `./factoryWithThrowingShims.js`.
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
   * PropTypes directly and inspect their output. However, we don't use real
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
      var manualPropTypeWarningCount = 0;
    }
    function checkType(isRequired, props, propName, componentName, location, propFullName, secret) {
      componentName = componentName || ANONYMOUS;
      propFullName = propFullName || propName;

      if (secret !== ReactPropTypesSecret) {
        if (throwOnDirectAccess) {
          // New behavior only for users of `prop-types` package
          invariant(false, 'Calling PropTypes validators directly is not supported by the `prop-types` package. ' + 'Use `PropTypes.checkPropTypes()` to call them. ' + 'Read more at http://fb.me/use-check-prop-types');
        } else if ("development" !== 'production' && typeof console !== 'undefined') {
          // Old behavior for people using React.PropTypes
          var cacheKey = componentName + ':' + propName;
          if (!manualPropTypeCallCache[cacheKey] &&
          // Avoid spamming the console because they are often not actionable except for lib authors
          manualPropTypeWarningCount < 3) {
            warning(false, 'You are manually calling a React.PropTypes validation ' + 'function for the `%s` prop on `%s`. This is deprecated ' + 'and will throw in the standalone `prop-types` package. ' + 'You may be seeing this warning due to a third-party PropTypes ' + 'library. See https://fb.me/react-warning-dont-call-proptypes ' + 'for details.', propFullName, componentName);
            manualPropTypeCallCache[cacheKey] = true;
            manualPropTypeWarningCount++;
          }
        }
      }
      if (props[propName] == null) {
        if (isRequired) {
          if (props[propName] === null) {
            return new PropTypeError('The ' + location + ' `' + propFullName + '` is marked as required ' + ('in `' + componentName + '`, but its value is `null`.'));
          }
          return new PropTypeError('The ' + location + ' `' + propFullName + '` is marked as required in ' + ('`' + componentName + '`, but its value is `undefined`.'));
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
        // `propValue` being instance of, say, date/regexp, pass the 'object'
        // check, but we can offer a more precise error message here rather than
        // 'of type `object`'.
        var preciseType = getPreciseType(propValue);

        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type ' + ('`' + preciseType + '` supplied to `' + componentName + '`, expected ') + ('`' + expectedType + '`.'));
      }
      return null;
    }
    return createChainableTypeChecker(validate);
  }

  function createAnyTypeChecker() {
    return createChainableTypeChecker(emptyFunction.thatReturnsNull);
  }

  function createArrayOfTypeChecker(typeChecker) {
    function validate(props, propName, componentName, location, propFullName) {
      if (typeof typeChecker !== 'function') {
        return new PropTypeError('Property `' + propFullName + '` of component `' + componentName + '` has invalid PropType notation inside arrayOf.');
      }
      var propValue = props[propName];
      if (!Array.isArray(propValue)) {
        var propType = getPropType(propValue);
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected an array.'));
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
      if (!isValidElement(propValue)) {
        var propType = getPropType(propValue);
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected a single ReactElement.'));
      }
      return null;
    }
    return createChainableTypeChecker(validate);
  }

  function createInstanceTypeChecker(expectedClass) {
    function validate(props, propName, componentName, location, propFullName) {
      if (!(props[propName] instanceof expectedClass)) {
        var expectedClassName = expectedClass.name || ANONYMOUS;
        var actualClassName = getClassName(props[propName]);
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type ' + ('`' + actualClassName + '` supplied to `' + componentName + '`, expected ') + ('instance of `' + expectedClassName + '`.'));
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

      var valuesString = JSON.stringify(expectedValues);
      return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of value `' + propValue + '` ' + ('supplied to `' + componentName + '`, expected one of ' + valuesString + '.'));
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
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type ' + ('`' + propType + '` supplied to `' + componentName + '`, expected an object.'));
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

    for (var i = 0; i < arrayOfTypeCheckers.length; i++) {
      var checker = arrayOfTypeCheckers[i];
      if (typeof checker !== 'function') {
        warning(false, 'Invalid argument supplid to oneOfType. Expected an array of check functions, but ' + 'received %s at index %s.', getPostfixForTypeWarning(checker), i);
        return emptyFunction.thatReturnsNull;
      }
    }

    function validate(props, propName, componentName, location, propFullName) {
      for (var i = 0; i < arrayOfTypeCheckers.length; i++) {
        var checker = arrayOfTypeCheckers[i];
        if (checker(props, propName, componentName, location, propFullName, ReactPropTypesSecret) == null) {
          return null;
        }
      }

      return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` supplied to ' + ('`' + componentName + '`.'));
    }
    return createChainableTypeChecker(validate);
  }

  function createNodeChecker() {
    function validate(props, propName, componentName, location, propFullName) {
      if (!isNode(props[propName])) {
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` supplied to ' + ('`' + componentName + '`, expected a ReactNode.'));
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
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type `' + propType + '` ' + ('supplied to `' + componentName + '`, expected `object`.'));
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
    switch (typeof propValue === 'undefined' ? 'undefined' : _typeof(propValue)) {
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
        if (propValue === null || isValidElement(propValue)) {
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
    var propType = typeof propValue === 'undefined' ? 'undefined' : _typeof(propValue);
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
    if (typeof propValue === 'undefined' || propValue === null) {
      return '' + propValue;
    }
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

  // Returns a string that is postfixed to a warning about an invalid type.
  // For example, "undefined" or "of type array"
  function getPostfixForTypeWarning(value) {
    var type = getPreciseType(value);
    switch (type) {
      case 'array':
      case 'object':
        return 'an ' + type;
      case 'boolean':
      case 'date':
      case 'regexp':
        return 'a ' + type;
      default:
        return type;
    }
  }

  // Returns class name of the object, if any.
  function getClassName(propValue) {
    if (!propValue.constructor || !propValue.constructor.name) {
      return ANONYMOUS;
    }
    return propValue.constructor.name;
  }

  ReactPropTypes.checkPropTypes = checkPropTypes;
  ReactPropTypes.PropTypes = ReactPropTypes;

  return ReactPropTypes;
};

/***/ }),
/* 875 */
/*!************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/prop-types/checkPropTypes.js ***!
  \************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

if (true) {
  var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 424);
  var warning = __webpack_require__(/*! fbjs/lib/warning */ 425);
  var ReactPropTypesSecret = __webpack_require__(/*! ./lib/ReactPropTypesSecret */ 426);
  var loggedTypeFailures = {};
}

/**
 * Assert that the values match with the type specs.
 * Error messages are memorized and will only be shown once.
 *
 * @param {object} typeSpecs Map of name to a ReactPropType
 * @param {object} values Runtime values that need to be type-checked
 * @param {string} location e.g. "prop", "context", "child context"
 * @param {string} componentName Name of the component for error messages.
 * @param {?Function} getStack Returns the component stack.
 * @private
 */
function checkPropTypes(typeSpecs, values, location, componentName, getStack) {
  if (true) {
    for (var typeSpecName in typeSpecs) {
      if (typeSpecs.hasOwnProperty(typeSpecName)) {
        var error;
        // Prop type validation may throw. In case they do, we don't want to
        // fail the render phase where it didn't fail before. So we log it.
        // After these have been cleaned up, we'll let them throw.
        try {
          // This is intentionally an invariant that gets caught. It's the same
          // behavior as without this statement except with a better message.
          invariant(typeof typeSpecs[typeSpecName] === 'function', '%s: %s type `%s` is invalid; it must be a function, usually from ' + 'React.PropTypes.', componentName || 'React class', location, typeSpecName);
          error = typeSpecs[typeSpecName](values, typeSpecName, componentName, location, null, ReactPropTypesSecret);
        } catch (ex) {
          error = ex;
        }
        warning(!error || error instanceof Error, '%s: type specification of %s `%s` is invalid; the type checker ' + 'function must return `null` or an `Error` but returned a %s. ' + 'You may have forgotten to pass an argument to the type checker ' + 'creator (arrayOf, instanceOf, objectOf, oneOf, oneOfType, and ' + 'shape all require an argument).', componentName || 'React class', location, typeSpecName, typeof error === 'undefined' ? 'undefined' : _typeof(error));
        if (error instanceof Error && !(error.message in loggedTypeFailures)) {
          // Only monitor this failure once because there tends to be a lot of the
          // same error.
          loggedTypeFailures[error.message] = true;

          var stack = getStack ? getStack() : '';

          warning(false, 'Failed %s type: %s%s', location, error.message, stack != null ? stack : '');
        }
      }
    }
  }
}

module.exports = checkPropTypes;

/***/ }),
/* 876 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/index.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.PromiseState = exports.connect = undefined;

var _connect = __webpack_require__(/*! ./components/connect */ 877);

var _connect2 = _interopRequireDefault(_connect);

var _PromiseState = __webpack_require__(/*! ./PromiseState */ 429);

var _PromiseState2 = _interopRequireDefault(_PromiseState);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

exports.connect = _connect2.default;
exports.PromiseState = _PromiseState2.default;

/***/ }),
/* 877 */
/*!***********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/components/connect.js ***!
  \***********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

exports.__esModule = true;

var _react = __webpack_require__(/*! react */ 2);

var _react2 = _interopRequireDefault(_react);

var _isPlainObject = __webpack_require__(/*! ../utils/isPlainObject */ 427);

var _isPlainObject2 = _interopRequireDefault(_isPlainObject);

var _shallowEqual = __webpack_require__(/*! ../utils/shallowEqual */ 878);

var _shallowEqual2 = _interopRequireDefault(_shallowEqual);

var _handleResponse = __webpack_require__(/*! ../utils/handleResponse */ 879);

var _handleResponse2 = _interopRequireDefault(_handleResponse);

var _buildRequest = __webpack_require__(/*! ../utils/buildRequest */ 881);

var _buildRequest2 = _interopRequireDefault(_buildRequest);

var _checkTypes = __webpack_require__(/*! ../utils/checkTypes */ 882);

var _checkTypes2 = _interopRequireDefault(_checkTypes);

var _PromiseState = __webpack_require__(/*! ../PromiseState */ 429);

var _PromiseState2 = _interopRequireDefault(_PromiseState);

var _hoistNonReactStatics = __webpack_require__(/*! hoist-non-react-statics */ 883);

var _hoistNonReactStatics2 = _interopRequireDefault(_hoistNonReactStatics);

var _invariant = __webpack_require__(/*! invariant */ 428);

var _invariant2 = _interopRequireDefault(_invariant);

var _warning = __webpack_require__(/*! warning */ 884);

var _warning2 = _interopRequireDefault(_warning);

var _omit = __webpack_require__(/*! lodash/fp/omit */ 885);

var _omit2 = _interopRequireDefault(_omit);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === 'undefined' ? 'undefined' : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var defaultMapPropsToRequestsToProps = function defaultMapPropsToRequestsToProps() {
  return {};
};

function getDisplayName(WrappedComponent) {
  return WrappedComponent.displayName || WrappedComponent.name || 'Component';
}

// Helps track hot reloading.
var nextVersion = 0;

function connectFactory() {
  var defaults = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];
  var options = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

  function connectImpl(map) {
    var deprecatedOptionsArgument = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    var finalOptions = options;
    if ('withRef' in deprecatedOptionsArgument) {
      (0, _warning2.default)(false, 'The options argument is deprecated in favor of `connect.options()`. In a future release, support will be removed.');
      finalOptions = Object.assign({}, options, { withRef: deprecatedOptionsArgument.withRef });
    }

    (0, _warning2.default)(!(Function.prototype.isPrototypeOf(defaults.buildRequest) && Function.prototype.isPrototypeOf(defaults.Request)), 'Both buildRequest and Request were provided in `connect.defaults()`. ' + 'However, this custom Request would only be used in the default buildRequest.');

    return connect(map, defaults, finalOptions);
  }

  connectImpl.defaults = function () {
    var overrides = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    (0, _checkTypes2.default)(overrides);
    return connectFactory(Object.assign({}, defaults, overrides, { headers: Object.assign({}, defaults.headers, overrides.headers) }), options);
  };

  connectImpl.options = function () {
    var overrides = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    return connectFactory(defaults, Object.assign({}, options, overrides));
  };

  return connectImpl;
}

exports.default = connectFactory({
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  }
});

var omitChildren = (0, _omit2.default)('children');

function connect(mapPropsToRequestsToProps, defaults, options) {
  var finalMapPropsToRequestsToProps = mapPropsToRequestsToProps || defaultMapPropsToRequestsToProps;
  var dependsOnProps = finalMapPropsToRequestsToProps.length >= 1;
  var dependsOnContext = finalMapPropsToRequestsToProps.length == 2;

  var topFetch = undefined;
  var topRequest = undefined;
  if (typeof window !== 'undefined') {
    if (window.fetch) {
      topFetch = window.fetch.bind(window);
    }
    if (window.Request) {
      topRequest = window.Request.bind(window);
    }
  } else if (typeof global !== 'undefined') {
    if (global.fetch) {
      topFetch = global.fetch.bind(global);
    }
    if (global.Request) {
      topRequest = global.Request.bind(global);
    }
  } else if (typeof self !== 'undefined') {
    if (self.fetch) {
      topFetch = self.fetch.bind(self);
    }
    if (self.Request) {
      topRequest = self.Request.bind(self);
    }
  }

  defaults = Object.assign({
    buildRequest: _buildRequest2.default,
    credentials: 'same-origin',
    fetch: topFetch,
    force: false,
    handleResponse: _handleResponse2.default,
    method: 'GET',
    redirect: 'follow',
    refreshing: false,
    refreshInterval: 0,
    Request: topRequest
  }, defaults);

  (0, _checkTypes2.default)(defaults);

  options = Object.assign({
    withRef: false,
    pure: true
  }, options);

  // Helps track hot reloading.
  var version = nextVersion++;

  function coerceMappings(rawMappings) {
    (0, _invariant2.default)((0, _isPlainObject2.default)(rawMappings), '`mapPropsToRequestsToProps` must return an object. Instead received %s.', rawMappings);

    var mappings = {};
    Object.keys(rawMappings).forEach(function (prop) {
      mappings[prop] = coerceMapping(prop, rawMappings[prop]);
    });
    return mappings;
  }

  function coerceMapping(prop, mapping, parent) {
    if (Function.prototype.isPrototypeOf(mapping)) {
      return mapping;
    }

    if (typeof mapping === 'string') {
      mapping = { url: mapping };
    }

    (0, _invariant2.default)((0, _isPlainObject2.default)(mapping), 'Request for `%s` must be either a string or a plain object. Instead received %s', prop, mapping);
    (0, _invariant2.default)(mapping.hasOwnProperty('url') || mapping.hasOwnProperty('value'), 'Request object for `%s` must have `url` (or `value`) attribute.', prop);
    (0, _invariant2.default)(!(mapping.hasOwnProperty('url') && mapping.hasOwnProperty('value')), 'Request object for `%s` must not have both `url` and `value` attributes.', prop);

    (0, _checkTypes2.default)(mapping);

    if (parent) {
      mapping.parent = parent.parent || parent;
    }

    mapping = assignDefaults(mapping, parent);

    (0, _invariant2.default)((0, _isPlainObject2.default)(mapping.meta), 'meta for `%s` must be a plain object. Instead received %s', prop, mapping.meta);

    mapping.equals = function (that) {
      var _this = this;

      that = that.parent || that;

      if (this.comparison !== undefined) {
        return this.comparison === that.comparison;
      }

      return ['value', 'url', 'method', 'headers', 'body'].every(function (c) {
        return (0, _shallowEqual2.default)(_this[c], that[c]);
      });
    }.bind(mapping);

    return mapping;
  }

  function assignDefaults(mapping, parent) {
    var rawHeaders = Object.assign({}, defaults.headers, mapping.headers);
    var headers = {};
    for (var key in rawHeaders) {
      // Discard headers with falsy values
      if (rawHeaders.hasOwnProperty(key) && rawHeaders[key]) {
        headers[key] = rawHeaders[key];
      }
    }

    return Object.assign({
      meta: {}
    }, defaults, parent ? {
      fetch: parent.fetch,
      buildRequest: parent.buildRequest,
      handleResponse: parent.handleResponse,
      Request: parent.Request,
      comparison: parent.comparison,
      then: undefined,
      andThen: undefined
    } : {}, mapping, { headers: headers });
  }

  return function wrapWithConnect(WrappedComponent) {
    var RefetchConnect = function (_Component) {
      _inherits(RefetchConnect, _Component);

      function RefetchConnect(props, context) {
        _classCallCheck(this, RefetchConnect);

        var _this2 = _possibleConstructorReturn(this, _Component.call(this, props, context));

        _this2.version = version;
        _this2.state = { mappings: {}, startedAts: {}, data: {}, refreshTimeouts: {} };
        return _this2;
      }

      RefetchConnect.prototype.componentWillMount = function componentWillMount() {
        this.refetchDataFromProps();
      };

      RefetchConnect.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps, nextContext) {
        if (!options.pure || dependsOnProps && !(0, _shallowEqual2.default)(omitChildren(this.props), omitChildren(nextProps)) || dependsOnContext && !(0, _shallowEqual2.default)(this.context, nextContext)) {
          this.refetchDataFromProps(nextProps, nextContext);
        }
      };

      RefetchConnect.prototype.shouldComponentUpdate = function shouldComponentUpdate(nextProps, nextState) {
        return !options.pure || this.state.data != nextState.data || !(0, _shallowEqual2.default)(this.props, nextProps);
      };

      RefetchConnect.prototype.componentWillUnmount = function componentWillUnmount() {
        this.clearAllRefreshTimeouts();
        this._unmounted = true;
      };

      RefetchConnect.prototype.render = function render() {
        var ref = options.withRef ? 'wrappedInstance' : null;
        return _react2.default.createElement(WrappedComponent, _extends({}, this.state.data, this.props, { ref: ref }));
      };

      RefetchConnect.prototype.getWrappedInstance = function getWrappedInstance() {
        (0, _invariant2.default)(options.withRef, 'To access the wrapped instance, you need to specify { withRef: true } in .options().');

        return this.refs.wrappedInstance;
      };

      RefetchConnect.prototype.refetchDataFromProps = function refetchDataFromProps() {
        var props = arguments.length <= 0 || arguments[0] === undefined ? this.props : arguments[0];
        var context = arguments.length <= 1 || arguments[1] === undefined ? this.context : arguments[1];

        this.refetchDataFromMappings(finalMapPropsToRequestsToProps(omitChildren(props), context) || {});
      };

      RefetchConnect.prototype.refetchDataFromMappings = function refetchDataFromMappings(mappings) {
        var _this3 = this;

        mappings = coerceMappings(mappings);
        Object.keys(mappings).forEach(function (prop) {
          var mapping = mappings[prop];

          if (Function.prototype.isPrototypeOf(mapping)) {
            _this3.setAtomicState(prop, new Date(), mapping, function () {
              _this3.refetchDataFromMappings(mapping.apply(undefined, arguments));
            });
            return;
          }

          if (mapping.force || !mapping.equals(_this3.state.mappings[prop] || {})) {
            _this3.refetchDatum(prop, mapping);
          }
        });
      };

      RefetchConnect.prototype.refetchDatum = function refetchDatum(prop, mapping) {
        var startedAt = new Date();

        if (this.state.refreshTimeouts[prop]) {
          window.clearTimeout(this.state.refreshTimeouts[prop]);
        }

        return this.createPromise(prop, mapping, startedAt);
      };

      RefetchConnect.prototype.createPromise = function createPromise(prop, mapping, startedAt) {
        var _this4 = this;

        var meta = mapping.meta;
        var initPS = this.createInitialPromiseState(prop, mapping);
        var onFulfillment = this.createPromiseStateOnFulfillment(prop, mapping, startedAt);
        var onRejection = this.createPromiseStateOnRejection(prop, mapping, startedAt);

        if (mapping.hasOwnProperty('value')) {
          if (mapping.value instanceof Promise) {
            this.setAtomicState(prop, startedAt, mapping, initPS(meta));
            return mapping.value.then(onFulfillment(meta), onRejection(meta));
          } else {
            return onFulfillment(meta)(mapping.value);
          }
        } else {
          var request = mapping.buildRequest(mapping);
          meta.request = request;
          this.setAtomicState(prop, startedAt, mapping, initPS(meta));

          var fetched = mapping.fetch(request);
          return fetched.then(function (response) {
            meta.response = response;
            meta.component = _this4.refs.wrappedInstance;

            return response;
          }).then(mapping.handleResponse).then(onFulfillment(meta), onRejection(meta));
        }
      };

      RefetchConnect.prototype.createInitialPromiseState = function createInitialPromiseState(prop, mapping) {
        var _this5 = this;

        return function (meta) {
          if (typeof mapping.refreshing == 'function') {
            var current = _this5.state.data[prop];
            if (current) {
              current.value = mapping.refreshing(current.value);
            }
            return _PromiseState2.default.refresh(current, meta);
          } else if (mapping.refreshing) {
            return _PromiseState2.default.refresh(_this5.state.data[prop], meta);
          } else {
            return _PromiseState2.default.create(meta);
          }
        };
      };

      RefetchConnect.prototype.createPromiseStateOnFulfillment = function createPromiseStateOnFulfillment(prop, mapping, startedAt) {
        var _this6 = this;

        return function (meta) {
          return function (value) {
            var refreshTimeout = null;
            if (mapping.refreshInterval > 0) {
              refreshTimeout = window.setTimeout(function () {
                _this6.refetchDatum(prop, Object.assign({}, mapping, { refreshing: true, force: true }));
              }, mapping.refreshInterval);
            }

            if (mapping.then) {
              var thenMapping = mapping.then(value, meta);
              if (typeof thenMapping !== 'undefined') {
                _this6.refetchDatum(prop, coerceMapping(null, thenMapping, mapping));
                return;
              }
            }

            _this6.setAtomicState(prop, startedAt, mapping, _PromiseState2.default.resolve(value, meta), refreshTimeout, function () {
              if (mapping.andThen) {
                _this6.refetchDataFromMappings(mapping.andThen(value, meta));
              }
            });
          };
        };
      };

      RefetchConnect.prototype.createPromiseStateOnRejection = function createPromiseStateOnRejection(prop, mapping, startedAt) {
        var _this7 = this;

        return function (meta) {
          return function (reason) {
            if (mapping.catch) {
              var catchMapping = mapping.catch(reason, meta);
              if (typeof catchMapping !== 'undefined') {
                _this7.refetchDatum(prop, coerceMapping(null, catchMapping, mapping));
                return;
              }
            }

            _this7.setAtomicState(prop, startedAt, mapping, _PromiseState2.default.reject(reason, meta), null, function () {
              if (mapping.andCatch) {
                _this7.refetchDataFromMappings(mapping.andCatch(reason, meta));
              }
            });
          };
        };
      };

      RefetchConnect.prototype.setAtomicState = function setAtomicState(prop, startedAt, mapping, datum, refreshTimeout, callback) {
        if (this._unmounted) {
          return;
        }

        this.setState(function (prevState) {
          var _Object$assign, _Object$assign2, _Object$assign3, _Object$assign4;

          if (startedAt < prevState.startedAts[prop]) {
            return {};
          }

          return {
            startedAts: Object.assign({}, prevState.startedAts, (_Object$assign = {}, _Object$assign[prop] = startedAt, _Object$assign)),
            mappings: Object.assign({}, prevState.mappings, (_Object$assign2 = {}, _Object$assign2[prop] = mapping, _Object$assign2)),
            data: Object.assign({}, prevState.data, (_Object$assign3 = {}, _Object$assign3[prop] = datum, _Object$assign3)),
            refreshTimeouts: Object.assign({}, prevState.refreshTimeouts, (_Object$assign4 = {}, _Object$assign4[prop] = refreshTimeout, _Object$assign4))
          };
        }, callback);
      };

      RefetchConnect.prototype.clearAllRefreshTimeouts = function clearAllRefreshTimeouts() {
        var _this8 = this;

        Object.keys(this.state.refreshTimeouts).forEach(function (prop) {
          clearTimeout(_this8.state.refreshTimeouts[prop]);
        });
      };

      return RefetchConnect;
    }(_react.Component);

    RefetchConnect.displayName = 'Refetch.connect(' + getDisplayName(WrappedComponent) + ')';
    RefetchConnect.WrappedComponent = WrappedComponent;

    if (dependsOnContext && WrappedComponent.contextTypes) {
      RefetchConnect.contextTypes = WrappedComponent.contextTypes;
    }

    if (true) {
      RefetchConnect.prototype.componentWillUpdate = function componentWillUpdate() {
        if (this.version === version) {
          return;
        }

        // We are hot reloading!
        this.version = version;
        this.clearAllRefreshTimeouts();
        this.refetchDataFromProps();
      };
    }

    return (0, _hoistNonReactStatics2.default)(RefetchConnect, WrappedComponent);
  };
}
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../../node_modules/webpack/buildin/global.js */ 19)))

/***/ }),
/* 878 */
/*!***********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/shallowEqual.js ***!
  \***********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

exports.__esModule = true;
exports.default = shallowEqual;
function shallowEqual(objA, objB) {
  if (objA === objB) {
    return true;
  }

  if ((typeof objA === 'undefined' ? 'undefined' : _typeof(objA)) !== 'object' || (typeof objB === 'undefined' ? 'undefined' : _typeof(objB)) !== 'object' || objA === null || objB === null) {
    return false;
  }

  var keysA = Object.keys(objA);
  var keysB = Object.keys(objB);

  if (keysA.length !== keysB.length) {
    return false;
  }

  // Test for A's keys different from B.
  var hasOwn = Object.prototype.hasOwnProperty;
  for (var i = 0; i < keysA.length; i++) {
    if (!hasOwn.call(objB, keysA[i]) || objA[keysA[i]] !== objB[keysA[i]]) {
      return false;
    }
  }

  return true;
}

/***/ }),
/* 879 */
/*!*************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/handleResponse.js ***!
  \*************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.default = handleResponse;

var _errors = __webpack_require__(/*! ./errors */ 880);

var _errors2 = _interopRequireDefault(_errors);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

function handleResponse(response) {
  if (response.headers.get('content-length') === '0' || response.status === 204) {
    return;
  }

  var json = response.json(); // TODO: support other response types

  if (response.status >= 200 && response.status < 300) {
    // TODO: support custom acceptable statuses
    return json;
  } else {
    return json.then(function (cause) {
      return Promise.reject((0, _errors2.default)(cause));
    });
  }
}

/***/ }),
/* 880 */
/*!*****************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/errors.js ***!
  \*****************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.default = newError;
function newError(cause) {
  var e = new Error(parse(cause));
  e.cause = cause;
  return e;
}

function parse(cause) {
  var error = cause.error;
  var message = cause.message;

  if (error) {
    return error;
  } else if (message) {
    return message;
  } else {
    return '';
  }
}

/***/ }),
/* 881 */
/*!***********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/buildRequest.js ***!
  \***********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.default = buildRequest;
function buildRequest(mapping) {
  return new mapping.Request(mapping.url, {
    method: mapping.method,
    headers: mapping.headers,
    credentials: mapping.credentials,
    redirect: mapping.redirect,
    body: mapping.body
  });
}

/***/ }),
/* 882 */
/*!*********************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/react-refetch/lib/utils/checkTypes.js ***!
  \*********************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

exports.__esModule = true;
exports.default = checkTypes;

var _invariant = __webpack_require__(/*! invariant */ 428);

var _invariant2 = _interopRequireDefault(_invariant);

var _isPlainObject = __webpack_require__(/*! ./isPlainObject */ 427);

var _isPlainObject2 = _interopRequireDefault(_isPlainObject);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

function typecheck(types, name, obj) {
  (0, _invariant2.default)(Array.isArray(types) ? types.some(function (t) {
    return (typeof obj === 'undefined' ? 'undefined' : _typeof(obj)) === t;
  }) : (typeof obj === 'undefined' ? 'undefined' : _typeof(obj)) === types, name + ' must be ' + (Array.isArray(types) ? 'one of' : 'a') + ' ' + types + '. Instead received a %s.', typeof obj === 'undefined' ? 'undefined' : _typeof(obj));
}

var checks = {
  buildRequest: function buildRequest(fn) {
    typecheck('function', 'buildRequest', fn);
  },
  credentials: function credentials(str) {
    var allowed = ['omit', 'same-origin', 'include'];
    (0, _invariant2.default)(allowed.indexOf(str) !== -1, 'credentials must be one of ' + allowed.join(', ') + '. Instead got %s.', str ? str.toString() : str);
  },
  fetch: function fetch(fn) {
    typecheck('function', 'fetch', fn);
  },
  handleResponse: function handleResponse(fn) {
    typecheck('function', 'handleResponse', fn);
  },
  headers: function headers(obj) {
    (0, _invariant2.default)((0, _isPlainObject2.default)(obj), 'headers must be a plain object with string values. Instead received a %s.', typeof obj === 'undefined' ? 'undefined' : _typeof(obj));
  },
  method: function method(str) {
    typecheck('string', 'method', str);
  },
  redirect: function redirect(str) {
    var allowed = ['follow', 'error', 'manual'];
    (0, _invariant2.default)(allowed.indexOf(str) !== -1, 'redirect must be one of ' + allowed.join(', ') + '. Instead got %s.', str ? str.toString() : str);
  },
  refreshInterval: function refreshInterval(num) {
    typecheck('number', 'refreshInterval', num);
    (0, _invariant2.default)(num >= 0, 'refreshInterval must be positive or 0.');
    (0, _invariant2.default)(num !== Infinity, 'refreshInterval must not be Infinity.');
  },
  Request: function Request(fn) {
    typecheck('function', 'Request', fn);
  },
  then: function then(fn) {
    typecheck(['function', 'undefined'], 'then', fn);
  },
  andThen: function andThen(fn) {
    typecheck(['function', 'undefined'], 'andThen', fn);
  },
  catch: function _catch(fn) {
    typecheck(['function', 'undefined'], 'catch', fn);
  },
  andCatch: function andCatch(fn) {
    typecheck(['function', 'undefined'], 'andCatch', fn);
  }
};

function checkTypes(mapping) {
  Object.keys(mapping).forEach(function (key) {
    if (checks[key]) {
      checks[key](mapping[key]);
    }
  });
}

/***/ }),
/* 883 */
/*!****************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/hoist-non-react-statics/index.js ***!
  \****************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2015, Yahoo! Inc.
 * Copyrights licensed under the New BSD License. See the accompanying LICENSE file for terms.
 */


var REACT_STATICS = {
    childContextTypes: true,
    contextTypes: true,
    defaultProps: true,
    displayName: true,
    getDefaultProps: true,
    mixins: true,
    propTypes: true,
    type: true
};

var KNOWN_STATICS = {
    name: true,
    length: true,
    prototype: true,
    caller: true,
    arguments: true,
    arity: true
};

var isGetOwnPropertySymbolsAvailable = typeof Object.getOwnPropertySymbols === 'function';

module.exports = function hoistNonReactStatics(targetComponent, sourceComponent, customStatics) {
    if (typeof sourceComponent !== 'string') {
        // don't hoist over string (html) components
        var keys = Object.getOwnPropertyNames(sourceComponent);

        /* istanbul ignore else */
        if (isGetOwnPropertySymbolsAvailable) {
            keys = keys.concat(Object.getOwnPropertySymbols(sourceComponent));
        }

        for (var i = 0; i < keys.length; ++i) {
            if (!REACT_STATICS[keys[i]] && !KNOWN_STATICS[keys[i]] && (!customStatics || !customStatics[keys[i]])) {
                try {
                    targetComponent[keys[i]] = sourceComponent[keys[i]];
                } catch (error) {}
            }
        }
    }

    return targetComponent;
};

/***/ }),
/* 884 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/warning/browser.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright 2014-2015, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */



/**
 * Similar to invariant but only logs a warning if the condition is not met.
 * This can be used to log issues in development environments in critical
 * paths. Removing the logging code for production environments will keep the
 * same logic and follow the same code paths.
 */

var warning = function warning() {};

if (true) {
  warning = function warning(condition, format, args) {
    var len = arguments.length;
    args = new Array(len > 2 ? len - 2 : 0);
    for (var key = 2; key < len; key++) {
      args[key - 2] = arguments[key];
    }
    if (format === undefined) {
      throw new Error('`warning(condition, format, ...args)` requires a warning ' + 'message argument');
    }

    if (format.length < 10 || /^[s\W]*$/.test(format)) {
      throw new Error('The warning format should be able to uniquely identify this ' + 'warning. Please, use a more descriptive format than: ' + format);
    }

    if (!condition) {
      var argIndex = 0;
      var message = 'Warning: ' + format.replace(/%s/g, function () {
        return args[argIndex++];
      });
      if (typeof console !== 'undefined') {
        console.error(message);
      }
      try {
        // This error was thrown as a convenience so that you can use this stack
        // to find the callsite that caused this warning to fire.
        throw new Error(message);
      } catch (x) {}
    }
  };
}

module.exports = warning;

/***/ }),
/* 885 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/omit.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var convert = __webpack_require__(/*! ./convert */ 886),
    func = convert('omit', __webpack_require__(/*! ../omit */ 1003));

func.placeholder = __webpack_require__(/*! ./placeholder */ 430);
module.exports = func;

/***/ }),
/* 886 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/convert.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseConvert = __webpack_require__(/*! ./_baseConvert */ 887),
    util = __webpack_require__(/*! ./_util */ 889);

/**
 * Converts `func` of `name` to an immutable auto-curried iteratee-first data-last
 * version with conversion `options` applied. If `name` is an object its methods
 * will be converted.
 *
 * @param {string} name The name of the function to wrap.
 * @param {Function} [func] The function to wrap.
 * @param {Object} [options] The options object. See `baseConvert` for more details.
 * @returns {Function|Object} Returns the converted function or object.
 */
function convert(name, func, options) {
  return baseConvert(util, name, func, options);
}

module.exports = convert;

/***/ }),
/* 887 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/_baseConvert.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var mapping = __webpack_require__(/*! ./_mapping */ 888),
    fallbackHolder = __webpack_require__(/*! ./placeholder */ 430);

/** Built-in value reference. */
var push = Array.prototype.push;

/**
 * Creates a function, with an arity of `n`, that invokes `func` with the
 * arguments it receives.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {number} n The arity of the new function.
 * @returns {Function} Returns the new function.
 */
function baseArity(func, n) {
  return n == 2 ? function (a, b) {
    return func.apply(undefined, arguments);
  } : function (a) {
    return func.apply(undefined, arguments);
  };
}

/**
 * Creates a function that invokes `func`, with up to `n` arguments, ignoring
 * any additional arguments.
 *
 * @private
 * @param {Function} func The function to cap arguments for.
 * @param {number} n The arity cap.
 * @returns {Function} Returns the new function.
 */
function baseAry(func, n) {
  return n == 2 ? function (a, b) {
    return func(a, b);
  } : function (a) {
    return func(a);
  };
}

/**
 * Creates a clone of `array`.
 *
 * @private
 * @param {Array} array The array to clone.
 * @returns {Array} Returns the cloned array.
 */
function cloneArray(array) {
  var length = array ? array.length : 0,
      result = Array(length);

  while (length--) {
    result[length] = array[length];
  }
  return result;
}

/**
 * Creates a function that clones a given object using the assignment `func`.
 *
 * @private
 * @param {Function} func The assignment function.
 * @returns {Function} Returns the new cloner function.
 */
function createCloner(func) {
  return function (object) {
    return func({}, object);
  };
}

/**
 * A specialized version of `_.spread` which flattens the spread array into
 * the arguments of the invoked `func`.
 *
 * @private
 * @param {Function} func The function to spread arguments over.
 * @param {number} start The start position of the spread.
 * @returns {Function} Returns the new function.
 */
function flatSpread(func, start) {
  return function () {
    var length = arguments.length,
        lastIndex = length - 1,
        args = Array(length);

    while (length--) {
      args[length] = arguments[length];
    }
    var array = args[start],
        otherArgs = args.slice(0, start);

    if (array) {
      push.apply(otherArgs, array);
    }
    if (start != lastIndex) {
      push.apply(otherArgs, args.slice(start + 1));
    }
    return func.apply(this, otherArgs);
  };
}

/**
 * Creates a function that wraps `func` and uses `cloner` to clone the first
 * argument it receives.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {Function} cloner The function to clone arguments.
 * @returns {Function} Returns the new immutable function.
 */
function wrapImmutable(func, cloner) {
  return function () {
    var length = arguments.length;
    if (!length) {
      return;
    }
    var args = Array(length);
    while (length--) {
      args[length] = arguments[length];
    }
    var result = args[0] = cloner.apply(undefined, args);
    func.apply(undefined, args);
    return result;
  };
}

/**
 * The base implementation of `convert` which accepts a `util` object of methods
 * required to perform conversions.
 *
 * @param {Object} util The util object.
 * @param {string} name The name of the function to convert.
 * @param {Function} func The function to convert.
 * @param {Object} [options] The options object.
 * @param {boolean} [options.cap=true] Specify capping iteratee arguments.
 * @param {boolean} [options.curry=true] Specify currying.
 * @param {boolean} [options.fixed=true] Specify fixed arity.
 * @param {boolean} [options.immutable=true] Specify immutable operations.
 * @param {boolean} [options.rearg=true] Specify rearranging arguments.
 * @returns {Function|Object} Returns the converted function or object.
 */
function baseConvert(util, name, func, options) {
  var setPlaceholder,
      isLib = typeof name == 'function',
      isObj = name === Object(name);

  if (isObj) {
    options = func;
    func = name;
    name = undefined;
  }
  if (func == null) {
    throw new TypeError();
  }
  options || (options = {});

  var config = {
    'cap': 'cap' in options ? options.cap : true,
    'curry': 'curry' in options ? options.curry : true,
    'fixed': 'fixed' in options ? options.fixed : true,
    'immutable': 'immutable' in options ? options.immutable : true,
    'rearg': 'rearg' in options ? options.rearg : true
  };

  var forceCurry = 'curry' in options && options.curry,
      forceFixed = 'fixed' in options && options.fixed,
      forceRearg = 'rearg' in options && options.rearg,
      placeholder = isLib ? func : fallbackHolder,
      pristine = isLib ? func.runInContext() : undefined;

  var helpers = isLib ? func : {
    'ary': util.ary,
    'assign': util.assign,
    'clone': util.clone,
    'curry': util.curry,
    'forEach': util.forEach,
    'isArray': util.isArray,
    'isFunction': util.isFunction,
    'iteratee': util.iteratee,
    'keys': util.keys,
    'rearg': util.rearg,
    'toInteger': util.toInteger,
    'toPath': util.toPath
  };

  var ary = helpers.ary,
      assign = helpers.assign,
      clone = helpers.clone,
      curry = helpers.curry,
      each = helpers.forEach,
      isArray = helpers.isArray,
      isFunction = helpers.isFunction,
      keys = helpers.keys,
      rearg = helpers.rearg,
      toInteger = helpers.toInteger,
      toPath = helpers.toPath;

  var aryMethodKeys = keys(mapping.aryMethod);

  var wrappers = {
    'castArray': function castArray(_castArray) {
      return function () {
        var value = arguments[0];
        return isArray(value) ? _castArray(cloneArray(value)) : _castArray.apply(undefined, arguments);
      };
    },
    'iteratee': function iteratee(_iteratee) {
      return function () {
        var func = arguments[0],
            arity = arguments[1],
            result = _iteratee(func, arity),
            length = result.length;

        if (config.cap && typeof arity == 'number') {
          arity = arity > 2 ? arity - 2 : 1;
          return length && length <= arity ? result : baseAry(result, arity);
        }
        return result;
      };
    },
    'mixin': function mixin(_mixin) {
      return function (source) {
        var func = this;
        if (!isFunction(func)) {
          return _mixin(func, Object(source));
        }
        var pairs = [];
        each(keys(source), function (key) {
          if (isFunction(source[key])) {
            pairs.push([key, func.prototype[key]]);
          }
        });

        _mixin(func, Object(source));

        each(pairs, function (pair) {
          var value = pair[1];
          if (isFunction(value)) {
            func.prototype[pair[0]] = value;
          } else {
            delete func.prototype[pair[0]];
          }
        });
        return func;
      };
    },
    'nthArg': function nthArg(_nthArg) {
      return function (n) {
        var arity = n < 0 ? 1 : toInteger(n) + 1;
        return curry(_nthArg(n), arity);
      };
    },
    'rearg': function rearg(_rearg) {
      return function (func, indexes) {
        var arity = indexes ? indexes.length : 0;
        return curry(_rearg(func, indexes), arity);
      };
    },
    'runInContext': function runInContext(_runInContext) {
      return function (context) {
        return baseConvert(util, _runInContext(context), options);
      };
    }
  };

  /*--------------------------------------------------------------------------*/

  /**
   * Casts `func` to a function with an arity capped iteratee if needed.
   *
   * @private
   * @param {string} name The name of the function to inspect.
   * @param {Function} func The function to inspect.
   * @returns {Function} Returns the cast function.
   */
  function castCap(name, func) {
    if (config.cap) {
      var indexes = mapping.iterateeRearg[name];
      if (indexes) {
        return iterateeRearg(func, indexes);
      }
      var n = !isLib && mapping.iterateeAry[name];
      if (n) {
        return iterateeAry(func, n);
      }
    }
    return func;
  }

  /**
   * Casts `func` to a curried function if needed.
   *
   * @private
   * @param {string} name The name of the function to inspect.
   * @param {Function} func The function to inspect.
   * @param {number} n The arity of `func`.
   * @returns {Function} Returns the cast function.
   */
  function castCurry(name, func, n) {
    return forceCurry || config.curry && n > 1 ? curry(func, n) : func;
  }

  /**
   * Casts `func` to a fixed arity function if needed.
   *
   * @private
   * @param {string} name The name of the function to inspect.
   * @param {Function} func The function to inspect.
   * @param {number} n The arity cap.
   * @returns {Function} Returns the cast function.
   */
  function castFixed(name, func, n) {
    if (config.fixed && (forceFixed || !mapping.skipFixed[name])) {
      var data = mapping.methodSpread[name],
          start = data && data.start;

      return start === undefined ? ary(func, n) : flatSpread(func, start);
    }
    return func;
  }

  /**
   * Casts `func` to an rearged function if needed.
   *
   * @private
   * @param {string} name The name of the function to inspect.
   * @param {Function} func The function to inspect.
   * @param {number} n The arity of `func`.
   * @returns {Function} Returns the cast function.
   */
  function castRearg(name, func, n) {
    return config.rearg && n > 1 && (forceRearg || !mapping.skipRearg[name]) ? rearg(func, mapping.methodRearg[name] || mapping.aryRearg[n]) : func;
  }

  /**
   * Creates a clone of `object` by `path`.
   *
   * @private
   * @param {Object} object The object to clone.
   * @param {Array|string} path The path to clone by.
   * @returns {Object} Returns the cloned object.
   */
  function cloneByPath(object, path) {
    path = toPath(path);

    var index = -1,
        length = path.length,
        lastIndex = length - 1,
        result = clone(Object(object)),
        nested = result;

    while (nested != null && ++index < length) {
      var key = path[index],
          value = nested[key];

      if (value != null) {
        nested[path[index]] = clone(index == lastIndex ? value : Object(value));
      }
      nested = nested[key];
    }
    return result;
  }

  /**
   * Converts `lodash` to an immutable auto-curried iteratee-first data-last
   * version with conversion `options` applied.
   *
   * @param {Object} [options] The options object. See `baseConvert` for more details.
   * @returns {Function} Returns the converted `lodash`.
   */
  function convertLib(options) {
    return _.runInContext.convert(options)(undefined);
  }

  /**
   * Create a converter function for `func` of `name`.
   *
   * @param {string} name The name of the function to convert.
   * @param {Function} func The function to convert.
   * @returns {Function} Returns the new converter function.
   */
  function createConverter(name, func) {
    var realName = mapping.aliasToReal[name] || name,
        methodName = mapping.remap[realName] || realName,
        oldOptions = options;

    return function (options) {
      var newUtil = isLib ? pristine : helpers,
          newFunc = isLib ? pristine[methodName] : func,
          newOptions = assign(assign({}, oldOptions), options);

      return baseConvert(newUtil, realName, newFunc, newOptions);
    };
  }

  /**
   * Creates a function that wraps `func` to invoke its iteratee, with up to `n`
   * arguments, ignoring any additional arguments.
   *
   * @private
   * @param {Function} func The function to cap iteratee arguments for.
   * @param {number} n The arity cap.
   * @returns {Function} Returns the new function.
   */
  function iterateeAry(func, n) {
    return overArg(func, function (func) {
      return typeof func == 'function' ? baseAry(func, n) : func;
    });
  }

  /**
   * Creates a function that wraps `func` to invoke its iteratee with arguments
   * arranged according to the specified `indexes` where the argument value at
   * the first index is provided as the first argument, the argument value at
   * the second index is provided as the second argument, and so on.
   *
   * @private
   * @param {Function} func The function to rearrange iteratee arguments for.
   * @param {number[]} indexes The arranged argument indexes.
   * @returns {Function} Returns the new function.
   */
  function iterateeRearg(func, indexes) {
    return overArg(func, function (func) {
      var n = indexes.length;
      return baseArity(rearg(baseAry(func, n), indexes), n);
    });
  }

  /**
   * Creates a function that invokes `func` with its first argument transformed.
   *
   * @private
   * @param {Function} func The function to wrap.
   * @param {Function} transform The argument transform.
   * @returns {Function} Returns the new function.
   */
  function overArg(func, transform) {
    return function () {
      var length = arguments.length;
      if (!length) {
        return func();
      }
      var args = Array(length);
      while (length--) {
        args[length] = arguments[length];
      }
      var index = config.rearg ? 0 : length - 1;
      args[index] = transform(args[index]);
      return func.apply(undefined, args);
    };
  }

  /**
   * Creates a function that wraps `func` and applys the conversions
   * rules by `name`.
   *
   * @private
   * @param {string} name The name of the function to wrap.
   * @param {Function} func The function to wrap.
   * @returns {Function} Returns the converted function.
   */
  function wrap(name, func) {
    var result,
        realName = mapping.aliasToReal[name] || name,
        wrapped = func,
        wrapper = wrappers[realName];

    if (wrapper) {
      wrapped = wrapper(func);
    } else if (config.immutable) {
      if (mapping.mutate.array[realName]) {
        wrapped = wrapImmutable(func, cloneArray);
      } else if (mapping.mutate.object[realName]) {
        wrapped = wrapImmutable(func, createCloner(func));
      } else if (mapping.mutate.set[realName]) {
        wrapped = wrapImmutable(func, cloneByPath);
      }
    }
    each(aryMethodKeys, function (aryKey) {
      each(mapping.aryMethod[aryKey], function (otherName) {
        if (realName == otherName) {
          var data = mapping.methodSpread[realName],
              afterRearg = data && data.afterRearg;

          result = afterRearg ? castFixed(realName, castRearg(realName, wrapped, aryKey), aryKey) : castRearg(realName, castFixed(realName, wrapped, aryKey), aryKey);

          result = castCap(realName, result);
          result = castCurry(realName, result, aryKey);
          return false;
        }
      });
      return !result;
    });

    result || (result = wrapped);
    if (result == func) {
      result = forceCurry ? curry(result, 1) : function () {
        return func.apply(this, arguments);
      };
    }
    result.convert = createConverter(realName, func);
    if (mapping.placeholder[realName]) {
      setPlaceholder = true;
      result.placeholder = func.placeholder = placeholder;
    }
    return result;
  }

  /*--------------------------------------------------------------------------*/

  if (!isObj) {
    return wrap(name, func);
  }
  var _ = func;

  // Convert methods by ary cap.
  var pairs = [];
  each(aryMethodKeys, function (aryKey) {
    each(mapping.aryMethod[aryKey], function (key) {
      var func = _[mapping.remap[key] || key];
      if (func) {
        pairs.push([key, wrap(key, func)]);
      }
    });
  });

  // Convert remaining methods.
  each(keys(_), function (key) {
    var func = _[key];
    if (typeof func == 'function') {
      var length = pairs.length;
      while (length--) {
        if (pairs[length][0] == key) {
          return;
        }
      }
      func.convert = createConverter(key, func);
      pairs.push([key, func]);
    }
  });

  // Assign to `_` leaving `_.prototype` unchanged to allow chaining.
  each(pairs, function (pair) {
    _[pair[0]] = pair[1];
  });

  _.convert = convertLib;
  if (setPlaceholder) {
    _.placeholder = placeholder;
  }
  // Assign aliases.
  each(keys(_), function (key) {
    each(mapping.realToAlias[key] || [], function (alias) {
      _[alias] = _[key];
    });
  });

  return _;
}

module.exports = baseConvert;

/***/ }),
/* 888 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/_mapping.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to map aliases to their real names. */
exports.aliasToReal = {

  // Lodash aliases.
  'each': 'forEach',
  'eachRight': 'forEachRight',
  'entries': 'toPairs',
  'entriesIn': 'toPairsIn',
  'extend': 'assignIn',
  'extendAll': 'assignInAll',
  'extendAllWith': 'assignInAllWith',
  'extendWith': 'assignInWith',
  'first': 'head',

  // Methods that are curried variants of others.
  'conforms': 'conformsTo',
  'matches': 'isMatch',
  'property': 'get',

  // Ramda aliases.
  '__': 'placeholder',
  'F': 'stubFalse',
  'T': 'stubTrue',
  'all': 'every',
  'allPass': 'overEvery',
  'always': 'constant',
  'any': 'some',
  'anyPass': 'overSome',
  'apply': 'spread',
  'assoc': 'set',
  'assocPath': 'set',
  'complement': 'negate',
  'compose': 'flowRight',
  'contains': 'includes',
  'dissoc': 'unset',
  'dissocPath': 'unset',
  'dropLast': 'dropRight',
  'dropLastWhile': 'dropRightWhile',
  'equals': 'isEqual',
  'identical': 'eq',
  'indexBy': 'keyBy',
  'init': 'initial',
  'invertObj': 'invert',
  'juxt': 'over',
  'omitAll': 'omit',
  'nAry': 'ary',
  'path': 'get',
  'pathEq': 'matchesProperty',
  'pathOr': 'getOr',
  'paths': 'at',
  'pickAll': 'pick',
  'pipe': 'flow',
  'pluck': 'map',
  'prop': 'get',
  'propEq': 'matchesProperty',
  'propOr': 'getOr',
  'props': 'at',
  'symmetricDifference': 'xor',
  'symmetricDifferenceBy': 'xorBy',
  'symmetricDifferenceWith': 'xorWith',
  'takeLast': 'takeRight',
  'takeLastWhile': 'takeRightWhile',
  'unapply': 'rest',
  'unnest': 'flatten',
  'useWith': 'overArgs',
  'where': 'conformsTo',
  'whereEq': 'isMatch',
  'zipObj': 'zipObject'
};

/** Used to map ary to method names. */
exports.aryMethod = {
  '1': ['assignAll', 'assignInAll', 'attempt', 'castArray', 'ceil', 'create', 'curry', 'curryRight', 'defaultsAll', 'defaultsDeepAll', 'floor', 'flow', 'flowRight', 'fromPairs', 'invert', 'iteratee', 'memoize', 'method', 'mergeAll', 'methodOf', 'mixin', 'nthArg', 'over', 'overEvery', 'overSome', 'rest', 'reverse', 'round', 'runInContext', 'spread', 'template', 'trim', 'trimEnd', 'trimStart', 'uniqueId', 'words', 'zipAll'],
  '2': ['add', 'after', 'ary', 'assign', 'assignAllWith', 'assignIn', 'assignInAllWith', 'at', 'before', 'bind', 'bindAll', 'bindKey', 'chunk', 'cloneDeepWith', 'cloneWith', 'concat', 'conformsTo', 'countBy', 'curryN', 'curryRightN', 'debounce', 'defaults', 'defaultsDeep', 'defaultTo', 'delay', 'difference', 'divide', 'drop', 'dropRight', 'dropRightWhile', 'dropWhile', 'endsWith', 'eq', 'every', 'filter', 'find', 'findIndex', 'findKey', 'findLast', 'findLastIndex', 'findLastKey', 'flatMap', 'flatMapDeep', 'flattenDepth', 'forEach', 'forEachRight', 'forIn', 'forInRight', 'forOwn', 'forOwnRight', 'get', 'groupBy', 'gt', 'gte', 'has', 'hasIn', 'includes', 'indexOf', 'intersection', 'invertBy', 'invoke', 'invokeMap', 'isEqual', 'isMatch', 'join', 'keyBy', 'lastIndexOf', 'lt', 'lte', 'map', 'mapKeys', 'mapValues', 'matchesProperty', 'maxBy', 'meanBy', 'merge', 'mergeAllWith', 'minBy', 'multiply', 'nth', 'omit', 'omitBy', 'overArgs', 'pad', 'padEnd', 'padStart', 'parseInt', 'partial', 'partialRight', 'partition', 'pick', 'pickBy', 'propertyOf', 'pull', 'pullAll', 'pullAt', 'random', 'range', 'rangeRight', 'rearg', 'reject', 'remove', 'repeat', 'restFrom', 'result', 'sampleSize', 'some', 'sortBy', 'sortedIndex', 'sortedIndexOf', 'sortedLastIndex', 'sortedLastIndexOf', 'sortedUniqBy', 'split', 'spreadFrom', 'startsWith', 'subtract', 'sumBy', 'take', 'takeRight', 'takeRightWhile', 'takeWhile', 'tap', 'throttle', 'thru', 'times', 'trimChars', 'trimCharsEnd', 'trimCharsStart', 'truncate', 'union', 'uniqBy', 'uniqWith', 'unset', 'unzipWith', 'without', 'wrap', 'xor', 'zip', 'zipObject', 'zipObjectDeep'],
  '3': ['assignInWith', 'assignWith', 'clamp', 'differenceBy', 'differenceWith', 'findFrom', 'findIndexFrom', 'findLastFrom', 'findLastIndexFrom', 'getOr', 'includesFrom', 'indexOfFrom', 'inRange', 'intersectionBy', 'intersectionWith', 'invokeArgs', 'invokeArgsMap', 'isEqualWith', 'isMatchWith', 'flatMapDepth', 'lastIndexOfFrom', 'mergeWith', 'orderBy', 'padChars', 'padCharsEnd', 'padCharsStart', 'pullAllBy', 'pullAllWith', 'rangeStep', 'rangeStepRight', 'reduce', 'reduceRight', 'replace', 'set', 'slice', 'sortedIndexBy', 'sortedLastIndexBy', 'transform', 'unionBy', 'unionWith', 'update', 'xorBy', 'xorWith', 'zipWith'],
  '4': ['fill', 'setWith', 'updateWith']
};

/** Used to map ary to rearg configs. */
exports.aryRearg = {
  '2': [1, 0],
  '3': [2, 0, 1],
  '4': [3, 2, 0, 1]
};

/** Used to map method names to their iteratee ary. */
exports.iterateeAry = {
  'dropRightWhile': 1,
  'dropWhile': 1,
  'every': 1,
  'filter': 1,
  'find': 1,
  'findFrom': 1,
  'findIndex': 1,
  'findIndexFrom': 1,
  'findKey': 1,
  'findLast': 1,
  'findLastFrom': 1,
  'findLastIndex': 1,
  'findLastIndexFrom': 1,
  'findLastKey': 1,
  'flatMap': 1,
  'flatMapDeep': 1,
  'flatMapDepth': 1,
  'forEach': 1,
  'forEachRight': 1,
  'forIn': 1,
  'forInRight': 1,
  'forOwn': 1,
  'forOwnRight': 1,
  'map': 1,
  'mapKeys': 1,
  'mapValues': 1,
  'partition': 1,
  'reduce': 2,
  'reduceRight': 2,
  'reject': 1,
  'remove': 1,
  'some': 1,
  'takeRightWhile': 1,
  'takeWhile': 1,
  'times': 1,
  'transform': 2
};

/** Used to map method names to iteratee rearg configs. */
exports.iterateeRearg = {
  'mapKeys': [1],
  'reduceRight': [1, 0]
};

/** Used to map method names to rearg configs. */
exports.methodRearg = {
  'assignInAllWith': [1, 0],
  'assignInWith': [1, 2, 0],
  'assignAllWith': [1, 0],
  'assignWith': [1, 2, 0],
  'differenceBy': [1, 2, 0],
  'differenceWith': [1, 2, 0],
  'getOr': [2, 1, 0],
  'intersectionBy': [1, 2, 0],
  'intersectionWith': [1, 2, 0],
  'isEqualWith': [1, 2, 0],
  'isMatchWith': [2, 1, 0],
  'mergeAllWith': [1, 0],
  'mergeWith': [1, 2, 0],
  'padChars': [2, 1, 0],
  'padCharsEnd': [2, 1, 0],
  'padCharsStart': [2, 1, 0],
  'pullAllBy': [2, 1, 0],
  'pullAllWith': [2, 1, 0],
  'rangeStep': [1, 2, 0],
  'rangeStepRight': [1, 2, 0],
  'setWith': [3, 1, 2, 0],
  'sortedIndexBy': [2, 1, 0],
  'sortedLastIndexBy': [2, 1, 0],
  'unionBy': [1, 2, 0],
  'unionWith': [1, 2, 0],
  'updateWith': [3, 1, 2, 0],
  'xorBy': [1, 2, 0],
  'xorWith': [1, 2, 0],
  'zipWith': [1, 2, 0]
};

/** Used to map method names to spread configs. */
exports.methodSpread = {
  'assignAll': { 'start': 0 },
  'assignAllWith': { 'start': 0 },
  'assignInAll': { 'start': 0 },
  'assignInAllWith': { 'start': 0 },
  'defaultsAll': { 'start': 0 },
  'defaultsDeepAll': { 'start': 0 },
  'invokeArgs': { 'start': 2 },
  'invokeArgsMap': { 'start': 2 },
  'mergeAll': { 'start': 0 },
  'mergeAllWith': { 'start': 0 },
  'partial': { 'start': 1 },
  'partialRight': { 'start': 1 },
  'without': { 'start': 1 },
  'zipAll': { 'start': 0 }
};

/** Used to identify methods which mutate arrays or objects. */
exports.mutate = {
  'array': {
    'fill': true,
    'pull': true,
    'pullAll': true,
    'pullAllBy': true,
    'pullAllWith': true,
    'pullAt': true,
    'remove': true,
    'reverse': true
  },
  'object': {
    'assign': true,
    'assignAll': true,
    'assignAllWith': true,
    'assignIn': true,
    'assignInAll': true,
    'assignInAllWith': true,
    'assignInWith': true,
    'assignWith': true,
    'defaults': true,
    'defaultsAll': true,
    'defaultsDeep': true,
    'defaultsDeepAll': true,
    'merge': true,
    'mergeAll': true,
    'mergeAllWith': true,
    'mergeWith': true
  },
  'set': {
    'set': true,
    'setWith': true,
    'unset': true,
    'update': true,
    'updateWith': true
  }
};

/** Used to track methods with placeholder support */
exports.placeholder = {
  'bind': true,
  'bindKey': true,
  'curry': true,
  'curryRight': true,
  'partial': true,
  'partialRight': true
};

/** Used to map real names to their aliases. */
exports.realToAlias = function () {
  var hasOwnProperty = Object.prototype.hasOwnProperty,
      object = exports.aliasToReal,
      result = {};

  for (var key in object) {
    var value = object[key];
    if (hasOwnProperty.call(result, value)) {
      result[value].push(key);
    } else {
      result[value] = [key];
    }
  }
  return result;
}();

/** Used to map method names to other names. */
exports.remap = {
  'assignAll': 'assign',
  'assignAllWith': 'assignWith',
  'assignInAll': 'assignIn',
  'assignInAllWith': 'assignInWith',
  'curryN': 'curry',
  'curryRightN': 'curryRight',
  'defaultsAll': 'defaults',
  'defaultsDeepAll': 'defaultsDeep',
  'findFrom': 'find',
  'findIndexFrom': 'findIndex',
  'findLastFrom': 'findLast',
  'findLastIndexFrom': 'findLastIndex',
  'getOr': 'get',
  'includesFrom': 'includes',
  'indexOfFrom': 'indexOf',
  'invokeArgs': 'invoke',
  'invokeArgsMap': 'invokeMap',
  'lastIndexOfFrom': 'lastIndexOf',
  'mergeAll': 'merge',
  'mergeAllWith': 'mergeWith',
  'padChars': 'pad',
  'padCharsEnd': 'padEnd',
  'padCharsStart': 'padStart',
  'propertyOf': 'get',
  'rangeStep': 'range',
  'rangeStepRight': 'rangeRight',
  'restFrom': 'rest',
  'spreadFrom': 'spread',
  'trimChars': 'trim',
  'trimCharsEnd': 'trimEnd',
  'trimCharsStart': 'trimStart',
  'zipAll': 'zip'
};

/** Used to track methods that skip fixing their arity. */
exports.skipFixed = {
  'castArray': true,
  'flow': true,
  'flowRight': true,
  'iteratee': true,
  'mixin': true,
  'rearg': true,
  'runInContext': true
};

/** Used to track methods that skip rearranging arguments. */
exports.skipRearg = {
  'add': true,
  'assign': true,
  'assignIn': true,
  'bind': true,
  'bindKey': true,
  'concat': true,
  'difference': true,
  'divide': true,
  'eq': true,
  'gt': true,
  'gte': true,
  'isEqual': true,
  'lt': true,
  'lte': true,
  'matchesProperty': true,
  'merge': true,
  'multiply': true,
  'overArgs': true,
  'partial': true,
  'partialRight': true,
  'propertyOf': true,
  'random': true,
  'range': true,
  'rangeRight': true,
  'subtract': true,
  'zip': true,
  'zipObject': true,
  'zipObjectDeep': true
};

/***/ }),
/* 889 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/fp/_util.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = {
  'ary': __webpack_require__(/*! ../ary */ 890),
  'assign': __webpack_require__(/*! ../_baseAssign */ 449),
  'clone': __webpack_require__(/*! ../clone */ 928),
  'curry': __webpack_require__(/*! ../curry */ 972),
  'forEach': __webpack_require__(/*! ../_arrayEach */ 231),
  'isArray': __webpack_require__(/*! ../isArray */ 28),
  'isFunction': __webpack_require__(/*! ../isFunction */ 227),
  'iteratee': __webpack_require__(/*! ../iteratee */ 973),
  'keys': __webpack_require__(/*! ../_baseKeys */ 454),
  'rearg': __webpack_require__(/*! ../rearg */ 997),
  'toInteger': __webpack_require__(/*! ../toInteger */ 448),
  'toPath': __webpack_require__(/*! ../toPath */ 1002)
};

/***/ }),
/* 890 */
/*!*********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/ary.js ***!
  \*********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var createWrap = __webpack_require__(/*! ./_createWrap */ 225);

/** Used to compose bitmasks for function metadata. */
var WRAP_ARY_FLAG = 128;

/**
 * Creates a function that invokes `func`, with up to `n` arguments,
 * ignoring any additional arguments.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Function
 * @param {Function} func The function to cap arguments for.
 * @param {number} [n=func.length] The arity cap.
 * @param- {Object} [guard] Enables use as an iteratee for methods like `_.map`.
 * @returns {Function} Returns the new capped function.
 * @example
 *
 * _.map(['6', '8', '10'], _.ary(parseInt, 1));
 * // => [6, 8, 10]
 */
function ary(func, n, guard) {
  n = guard ? undefined : n;
  n = func && n == null ? func.length : n;
  return createWrap(func, WRAP_ARY_FLAG, undefined, undefined, undefined, undefined, n);
}

module.exports = ary;

/***/ }),
/* 891 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsNative.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isFunction = __webpack_require__(/*! ./isFunction */ 227),
    isMasked = __webpack_require__(/*! ./_isMasked */ 894),
    isObject = __webpack_require__(/*! ./isObject */ 58),
    toSource = __webpack_require__(/*! ./_toSource */ 435);

/**
 * Used to match `RegExp`
 * [syntax characters](http://ecma-international.org/ecma-262/7.0/#sec-patterns).
 */
var reRegExpChar = /[\\^$.*+?()[\]{}|]/g;

/** Used to detect host constructors (Safari). */
var reIsHostCtor = /^\[object .+?Constructor\]$/;

/** Used for built-in method references. */
var funcProto = Function.prototype,
    objectProto = Object.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Used to detect if a method is native. */
var reIsNative = RegExp('^' + funcToString.call(hasOwnProperty).replace(reRegExpChar, '\\$&').replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, '$1.*?') + '$');

/**
 * The base implementation of `_.isNative` without bad shim checks.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a native function,
 *  else `false`.
 */
function baseIsNative(value) {
  if (!isObject(value) || isMasked(value)) {
    return false;
  }
  var pattern = isFunction(value) ? reIsNative : reIsHostCtor;
  return pattern.test(toSource(value));
}

module.exports = baseIsNative;

/***/ }),
/* 892 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getRawTag.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Used to resolve the
 * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
 * of values.
 */
var nativeObjectToString = objectProto.toString;

/** Built-in value references. */
var symToStringTag = _Symbol ? _Symbol.toStringTag : undefined;

/**
 * A specialized version of `baseGetTag` which ignores `Symbol.toStringTag` values.
 *
 * @private
 * @param {*} value The value to query.
 * @returns {string} Returns the raw `toStringTag`.
 */
function getRawTag(value) {
  var isOwn = hasOwnProperty.call(value, symToStringTag),
      tag = value[symToStringTag];

  try {
    value[symToStringTag] = undefined;
    var unmasked = true;
  } catch (e) {}

  var result = nativeObjectToString.call(value);
  if (unmasked) {
    if (isOwn) {
      value[symToStringTag] = tag;
    } else {
      delete value[symToStringTag];
    }
  }
  return result;
}

module.exports = getRawTag;

/***/ }),
/* 893 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_objectToString.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used for built-in method references. */
var objectProto = Object.prototype;

/**
 * Used to resolve the
 * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
 * of values.
 */
var nativeObjectToString = objectProto.toString;

/**
 * Converts `value` to a string using `Object.prototype.toString`.
 *
 * @private
 * @param {*} value The value to convert.
 * @returns {string} Returns the converted string.
 */
function objectToString(value) {
  return nativeObjectToString.call(value);
}

module.exports = objectToString;

/***/ }),
/* 894 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isMasked.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var coreJsData = __webpack_require__(/*! ./_coreJsData */ 895);

/** Used to detect methods masquerading as native. */
var maskSrcKey = function () {
  var uid = /[^.]+$/.exec(coreJsData && coreJsData.keys && coreJsData.keys.IE_PROTO || '');
  return uid ? 'Symbol(src)_1.' + uid : '';
}();

/**
 * Checks if `func` has its source masked.
 *
 * @private
 * @param {Function} func The function to check.
 * @returns {boolean} Returns `true` if `func` is masked, else `false`.
 */
function isMasked(func) {
  return !!maskSrcKey && maskSrcKey in func;
}

module.exports = isMasked;

/***/ }),
/* 895 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_coreJsData.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var root = __webpack_require__(/*! ./_root */ 22);

/** Used to detect overreaching core-js shims. */
var coreJsData = root['__core-js_shared__'];

module.exports = coreJsData;

/***/ }),
/* 896 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getValue.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Gets the value at `key` of `object`.
 *
 * @private
 * @param {Object} [object] The object to query.
 * @param {string} key The key of the property to get.
 * @returns {*} Returns the property value.
 */
function getValue(object, key) {
  return object == null ? undefined : object[key];
}

module.exports = getValue;

/***/ }),
/* 897 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createBind.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var createCtor = __webpack_require__(/*! ./_createCtor */ 141),
    root = __webpack_require__(/*! ./_root */ 22);

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1;

/**
 * Creates a function that wraps `func` to invoke it with the optional `this`
 * binding of `thisArg`.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @param {*} [thisArg] The `this` binding of `func`.
 * @returns {Function} Returns the new wrapped function.
 */
function createBind(func, bitmask, thisArg) {
  var isBind = bitmask & WRAP_BIND_FLAG,
      Ctor = createCtor(func);

  function wrapper() {
    var fn = this && this !== root && this instanceof wrapper ? Ctor : func;
    return fn.apply(isBind ? thisArg : this, arguments);
  }
  return wrapper;
}

module.exports = createBind;

/***/ }),
/* 898 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createCurry.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var apply = __webpack_require__(/*! ./_apply */ 228),
    createCtor = __webpack_require__(/*! ./_createCtor */ 141),
    createHybrid = __webpack_require__(/*! ./_createHybrid */ 436),
    createRecurry = __webpack_require__(/*! ./_createRecurry */ 439),
    getHolder = __webpack_require__(/*! ./_getHolder */ 447),
    replaceHolders = __webpack_require__(/*! ./_replaceHolders */ 233),
    root = __webpack_require__(/*! ./_root */ 22);

/**
 * Creates a function that wraps `func` to enable currying.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @param {number} arity The arity of `func`.
 * @returns {Function} Returns the new wrapped function.
 */
function createCurry(func, bitmask, arity) {
  var Ctor = createCtor(func);

  function wrapper() {
    var length = arguments.length,
        args = Array(length),
        index = length,
        placeholder = getHolder(wrapper);

    while (index--) {
      args[index] = arguments[index];
    }
    var holders = length < 3 && args[0] !== placeholder && args[length - 1] !== placeholder ? [] : replaceHolders(args, placeholder);

    length -= holders.length;
    if (length < arity) {
      return createRecurry(func, bitmask, createHybrid, wrapper.placeholder, undefined, args, holders, undefined, undefined, arity - length);
    }
    var fn = this && this !== root && this instanceof wrapper ? Ctor : func;
    return apply(fn, this, args);
  }
  return wrapper;
}

module.exports = createCurry;

/***/ }),
/* 899 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_countHolders.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Gets the number of `placeholder` occurrences in `array`.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {*} placeholder The placeholder to search for.
 * @returns {number} Returns the placeholder count.
 */
function countHolders(array, placeholder) {
  var length = array.length,
      result = 0;

  while (length--) {
    if (array[length] === placeholder) {
      ++result;
    }
  }
  return result;
}

module.exports = countHolders;

/***/ }),
/* 900 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isLaziable.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var LazyWrapper = __webpack_require__(/*! ./_LazyWrapper */ 229),
    getData = __webpack_require__(/*! ./_getData */ 440),
    getFuncName = __webpack_require__(/*! ./_getFuncName */ 902),
    lodash = __webpack_require__(/*! ./wrapperLodash */ 904);

/**
 * Checks if `func` has a lazy counterpart.
 *
 * @private
 * @param {Function} func The function to check.
 * @returns {boolean} Returns `true` if `func` has a lazy counterpart,
 *  else `false`.
 */
function isLaziable(func) {
  var funcName = getFuncName(func),
      other = lodash[funcName];

  if (typeof other != 'function' || !(funcName in LazyWrapper.prototype)) {
    return false;
  }
  if (func === other) {
    return true;
  }
  var data = getData(other);
  return !!data && func === data[0];
}

module.exports = isLaziable;

/***/ }),
/* 901 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/noop.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * This method returns `undefined`.
 *
 * @static
 * @memberOf _
 * @since 2.3.0
 * @category Util
 * @example
 *
 * _.times(2, _.noop);
 * // => [undefined, undefined]
 */
function noop() {
  // No operation performed.
}

module.exports = noop;

/***/ }),
/* 902 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getFuncName.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var realNames = __webpack_require__(/*! ./_realNames */ 903);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Gets the name of `func`.
 *
 * @private
 * @param {Function} func The function to query.
 * @returns {string} Returns the function name.
 */
function getFuncName(func) {
  var result = func.name + '',
      array = realNames[result],
      length = hasOwnProperty.call(realNames, result) ? array.length : 0;

  while (length--) {
    var data = array[length],
        otherFunc = data.func;
    if (otherFunc == null || otherFunc == func) {
      return data.name;
    }
  }
  return result;
}

module.exports = getFuncName;

/***/ }),
/* 903 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_realNames.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to lookup unminified function names. */
var realNames = {};

module.exports = realNames;

/***/ }),
/* 904 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/wrapperLodash.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var LazyWrapper = __webpack_require__(/*! ./_LazyWrapper */ 229),
    LodashWrapper = __webpack_require__(/*! ./_LodashWrapper */ 441),
    baseLodash = __webpack_require__(/*! ./_baseLodash */ 230),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69),
    wrapperClone = __webpack_require__(/*! ./_wrapperClone */ 905);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Creates a `lodash` object which wraps `value` to enable implicit method
 * chain sequences. Methods that operate on and return arrays, collections,
 * and functions can be chained together. Methods that retrieve a single value
 * or may return a primitive value will automatically end the chain sequence
 * and return the unwrapped value. Otherwise, the value must be unwrapped
 * with `_#value`.
 *
 * Explicit chain sequences, which must be unwrapped with `_#value`, may be
 * enabled using `_.chain`.
 *
 * The execution of chained methods is lazy, that is, it's deferred until
 * `_#value` is implicitly or explicitly called.
 *
 * Lazy evaluation allows several methods to support shortcut fusion.
 * Shortcut fusion is an optimization to merge iteratee calls; this avoids
 * the creation of intermediate arrays and can greatly reduce the number of
 * iteratee executions. Sections of a chain sequence qualify for shortcut
 * fusion if the section is applied to an array and iteratees accept only
 * one argument. The heuristic for whether a section qualifies for shortcut
 * fusion is subject to change.
 *
 * Chaining is supported in custom builds as long as the `_#value` method is
 * directly or indirectly included in the build.
 *
 * In addition to lodash methods, wrappers have `Array` and `String` methods.
 *
 * The wrapper `Array` methods are:
 * `concat`, `join`, `pop`, `push`, `shift`, `sort`, `splice`, and `unshift`
 *
 * The wrapper `String` methods are:
 * `replace` and `split`
 *
 * The wrapper methods that support shortcut fusion are:
 * `at`, `compact`, `drop`, `dropRight`, `dropWhile`, `filter`, `find`,
 * `findLast`, `head`, `initial`, `last`, `map`, `reject`, `reverse`, `slice`,
 * `tail`, `take`, `takeRight`, `takeRightWhile`, `takeWhile`, and `toArray`
 *
 * The chainable wrapper methods are:
 * `after`, `ary`, `assign`, `assignIn`, `assignInWith`, `assignWith`, `at`,
 * `before`, `bind`, `bindAll`, `bindKey`, `castArray`, `chain`, `chunk`,
 * `commit`, `compact`, `concat`, `conforms`, `constant`, `countBy`, `create`,
 * `curry`, `debounce`, `defaults`, `defaultsDeep`, `defer`, `delay`,
 * `difference`, `differenceBy`, `differenceWith`, `drop`, `dropRight`,
 * `dropRightWhile`, `dropWhile`, `extend`, `extendWith`, `fill`, `filter`,
 * `flatMap`, `flatMapDeep`, `flatMapDepth`, `flatten`, `flattenDeep`,
 * `flattenDepth`, `flip`, `flow`, `flowRight`, `fromPairs`, `functions`,
 * `functionsIn`, `groupBy`, `initial`, `intersection`, `intersectionBy`,
 * `intersectionWith`, `invert`, `invertBy`, `invokeMap`, `iteratee`, `keyBy`,
 * `keys`, `keysIn`, `map`, `mapKeys`, `mapValues`, `matches`, `matchesProperty`,
 * `memoize`, `merge`, `mergeWith`, `method`, `methodOf`, `mixin`, `negate`,
 * `nthArg`, `omit`, `omitBy`, `once`, `orderBy`, `over`, `overArgs`,
 * `overEvery`, `overSome`, `partial`, `partialRight`, `partition`, `pick`,
 * `pickBy`, `plant`, `property`, `propertyOf`, `pull`, `pullAll`, `pullAllBy`,
 * `pullAllWith`, `pullAt`, `push`, `range`, `rangeRight`, `rearg`, `reject`,
 * `remove`, `rest`, `reverse`, `sampleSize`, `set`, `setWith`, `shuffle`,
 * `slice`, `sort`, `sortBy`, `splice`, `spread`, `tail`, `take`, `takeRight`,
 * `takeRightWhile`, `takeWhile`, `tap`, `throttle`, `thru`, `toArray`,
 * `toPairs`, `toPairsIn`, `toPath`, `toPlainObject`, `transform`, `unary`,
 * `union`, `unionBy`, `unionWith`, `uniq`, `uniqBy`, `uniqWith`, `unset`,
 * `unshift`, `unzip`, `unzipWith`, `update`, `updateWith`, `values`,
 * `valuesIn`, `without`, `wrap`, `xor`, `xorBy`, `xorWith`, `zip`,
 * `zipObject`, `zipObjectDeep`, and `zipWith`
 *
 * The wrapper methods that are **not** chainable by default are:
 * `add`, `attempt`, `camelCase`, `capitalize`, `ceil`, `clamp`, `clone`,
 * `cloneDeep`, `cloneDeepWith`, `cloneWith`, `conformsTo`, `deburr`,
 * `defaultTo`, `divide`, `each`, `eachRight`, `endsWith`, `eq`, `escape`,
 * `escapeRegExp`, `every`, `find`, `findIndex`, `findKey`, `findLast`,
 * `findLastIndex`, `findLastKey`, `first`, `floor`, `forEach`, `forEachRight`,
 * `forIn`, `forInRight`, `forOwn`, `forOwnRight`, `get`, `gt`, `gte`, `has`,
 * `hasIn`, `head`, `identity`, `includes`, `indexOf`, `inRange`, `invoke`,
 * `isArguments`, `isArray`, `isArrayBuffer`, `isArrayLike`, `isArrayLikeObject`,
 * `isBoolean`, `isBuffer`, `isDate`, `isElement`, `isEmpty`, `isEqual`,
 * `isEqualWith`, `isError`, `isFinite`, `isFunction`, `isInteger`, `isLength`,
 * `isMap`, `isMatch`, `isMatchWith`, `isNaN`, `isNative`, `isNil`, `isNull`,
 * `isNumber`, `isObject`, `isObjectLike`, `isPlainObject`, `isRegExp`,
 * `isSafeInteger`, `isSet`, `isString`, `isUndefined`, `isTypedArray`,
 * `isWeakMap`, `isWeakSet`, `join`, `kebabCase`, `last`, `lastIndexOf`,
 * `lowerCase`, `lowerFirst`, `lt`, `lte`, `max`, `maxBy`, `mean`, `meanBy`,
 * `min`, `minBy`, `multiply`, `noConflict`, `noop`, `now`, `nth`, `pad`,
 * `padEnd`, `padStart`, `parseInt`, `pop`, `random`, `reduce`, `reduceRight`,
 * `repeat`, `result`, `round`, `runInContext`, `sample`, `shift`, `size`,
 * `snakeCase`, `some`, `sortedIndex`, `sortedIndexBy`, `sortedLastIndex`,
 * `sortedLastIndexBy`, `startCase`, `startsWith`, `stubArray`, `stubFalse`,
 * `stubObject`, `stubString`, `stubTrue`, `subtract`, `sum`, `sumBy`,
 * `template`, `times`, `toFinite`, `toInteger`, `toJSON`, `toLength`,
 * `toLower`, `toNumber`, `toSafeInteger`, `toString`, `toUpper`, `trim`,
 * `trimEnd`, `trimStart`, `truncate`, `unescape`, `uniqueId`, `upperCase`,
 * `upperFirst`, `value`, and `words`
 *
 * @name _
 * @constructor
 * @category Seq
 * @param {*} value The value to wrap in a `lodash` instance.
 * @returns {Object} Returns the new `lodash` wrapper instance.
 * @example
 *
 * function square(n) {
 *   return n * n;
 * }
 *
 * var wrapped = _([1, 2, 3]);
 *
 * // Returns an unwrapped value.
 * wrapped.reduce(_.add);
 * // => 6
 *
 * // Returns a wrapped value.
 * var squares = wrapped.map(square);
 *
 * _.isArray(squares);
 * // => false
 *
 * _.isArray(squares.value());
 * // => true
 */
function lodash(value) {
  if (isObjectLike(value) && !isArray(value) && !(value instanceof LazyWrapper)) {
    if (value instanceof LodashWrapper) {
      return value;
    }
    if (hasOwnProperty.call(value, '__wrapped__')) {
      return wrapperClone(value);
    }
  }
  return new LodashWrapper(value);
}

// Ensure wrappers are instances of `baseLodash`.
lodash.prototype = baseLodash.prototype;
lodash.prototype.constructor = lodash;

module.exports = lodash;

/***/ }),
/* 905 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_wrapperClone.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var LazyWrapper = __webpack_require__(/*! ./_LazyWrapper */ 229),
    LodashWrapper = __webpack_require__(/*! ./_LodashWrapper */ 441),
    copyArray = __webpack_require__(/*! ./_copyArray */ 143);

/**
 * Creates a clone of `wrapper`.
 *
 * @private
 * @param {Object} wrapper The wrapper to clone.
 * @returns {Object} Returns the cloned wrapper.
 */
function wrapperClone(wrapper) {
  if (wrapper instanceof LazyWrapper) {
    return wrapper.clone();
  }
  var result = new LodashWrapper(wrapper.__wrapped__, wrapper.__chain__);
  result.__actions__ = copyArray(wrapper.__actions__);
  result.__index__ = wrapper.__index__;
  result.__values__ = wrapper.__values__;
  return result;
}

module.exports = wrapperClone;

/***/ }),
/* 906 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getWrapDetails.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to match wrap detail comments. */
var reWrapDetails = /\{\n\/\* \[wrapped with (.+)\] \*/,
    reSplitDetails = /,? & /;

/**
 * Extracts wrapper details from the `source` body comment.
 *
 * @private
 * @param {string} source The source to inspect.
 * @returns {Array} Returns the wrapper details.
 */
function getWrapDetails(source) {
  var match = source.match(reWrapDetails);
  return match ? match[1].split(reSplitDetails) : [];
}

module.exports = getWrapDetails;

/***/ }),
/* 907 */
/*!************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_insertWrapDetails.js ***!
  \************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to match wrap detail comments. */
var reWrapComment = /\{(?:\n\/\* \[wrapped with .+\] \*\/)?\n?/;

/**
 * Inserts wrapper `details` in a comment at the top of the `source` body.
 *
 * @private
 * @param {string} source The source to modify.
 * @returns {Array} details The details to insert.
 * @returns {string} Returns the modified source.
 */
function insertWrapDetails(source, details) {
  var length = details.length;
  if (!length) {
    return source;
  }
  var lastIndex = length - 1;
  details[lastIndex] = (length > 1 ? '& ' : '') + details[lastIndex];
  details = details.join(length > 2 ? ', ' : ' ');
  return source.replace(reWrapComment, '{\n/* [wrapped with ' + details + '] */\n');
}

module.exports = insertWrapDetails;

/***/ }),
/* 908 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseSetToString.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var constant = __webpack_require__(/*! ./constant */ 909),
    defineProperty = __webpack_require__(/*! ./_defineProperty */ 446),
    identity = __webpack_require__(/*! ./identity */ 226);

/**
 * The base implementation of `setToString` without support for hot loop shorting.
 *
 * @private
 * @param {Function} func The function to modify.
 * @param {Function} string The `toString` result.
 * @returns {Function} Returns `func`.
 */
var baseSetToString = !defineProperty ? identity : function (func, string) {
  return defineProperty(func, 'toString', {
    'configurable': true,
    'enumerable': false,
    'value': constant(string),
    'writable': true
  });
};

module.exports = baseSetToString;

/***/ }),
/* 909 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/constant.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Creates a function that returns `value`.
 *
 * @static
 * @memberOf _
 * @since 2.4.0
 * @category Util
 * @param {*} value The value to return from the new function.
 * @returns {Function} Returns the new constant function.
 * @example
 *
 * var objects = _.times(2, _.constant({ 'a': 1 }));
 *
 * console.log(objects);
 * // => [{ 'a': 1 }, { 'a': 1 }]
 *
 * console.log(objects[0] === objects[1]);
 * // => true
 */
function constant(value) {
  return function () {
    return value;
  };
}

module.exports = constant;

/***/ }),
/* 910 */
/*!************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_updateWrapDetails.js ***!
  \************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayEach = __webpack_require__(/*! ./_arrayEach */ 231),
    arrayIncludes = __webpack_require__(/*! ./_arrayIncludes */ 911);

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1,
    WRAP_BIND_KEY_FLAG = 2,
    WRAP_CURRY_FLAG = 8,
    WRAP_CURRY_RIGHT_FLAG = 16,
    WRAP_PARTIAL_FLAG = 32,
    WRAP_PARTIAL_RIGHT_FLAG = 64,
    WRAP_ARY_FLAG = 128,
    WRAP_REARG_FLAG = 256,
    WRAP_FLIP_FLAG = 512;

/** Used to associate wrap methods with their bit flags. */
var wrapFlags = [['ary', WRAP_ARY_FLAG], ['bind', WRAP_BIND_FLAG], ['bindKey', WRAP_BIND_KEY_FLAG], ['curry', WRAP_CURRY_FLAG], ['curryRight', WRAP_CURRY_RIGHT_FLAG], ['flip', WRAP_FLIP_FLAG], ['partial', WRAP_PARTIAL_FLAG], ['partialRight', WRAP_PARTIAL_RIGHT_FLAG], ['rearg', WRAP_REARG_FLAG]];

/**
 * Updates wrapper `details` based on `bitmask` flags.
 *
 * @private
 * @returns {Array} details The details to modify.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @returns {Array} Returns `details`.
 */
function updateWrapDetails(details, bitmask) {
  arrayEach(wrapFlags, function (pair) {
    var value = '_.' + pair[0];
    if (bitmask & pair[1] && !arrayIncludes(details, value)) {
      details.push(value);
    }
  });
  return details.sort();
}

module.exports = updateWrapDetails;

/***/ }),
/* 911 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayIncludes.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIndexOf = __webpack_require__(/*! ./_baseIndexOf */ 912);

/**
 * A specialized version of `_.includes` for arrays without support for
 * specifying an index to search from.
 *
 * @private
 * @param {Array} [array] The array to inspect.
 * @param {*} target The value to search for.
 * @returns {boolean} Returns `true` if `target` is found, else `false`.
 */
function arrayIncludes(array, value) {
  var length = array == null ? 0 : array.length;
  return !!length && baseIndexOf(array, value, 0) > -1;
}

module.exports = arrayIncludes;

/***/ }),
/* 912 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIndexOf.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseFindIndex = __webpack_require__(/*! ./_baseFindIndex */ 913),
    baseIsNaN = __webpack_require__(/*! ./_baseIsNaN */ 914),
    strictIndexOf = __webpack_require__(/*! ./_strictIndexOf */ 915);

/**
 * The base implementation of `_.indexOf` without `fromIndex` bounds checks.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {*} value The value to search for.
 * @param {number} fromIndex The index to search from.
 * @returns {number} Returns the index of the matched value, else `-1`.
 */
function baseIndexOf(array, value, fromIndex) {
    return value === value ? strictIndexOf(array, value, fromIndex) : baseFindIndex(array, baseIsNaN, fromIndex);
}

module.exports = baseIndexOf;

/***/ }),
/* 913 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseFindIndex.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.findIndex` and `_.findLastIndex` without
 * support for iteratee shorthands.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {Function} predicate The function invoked per iteration.
 * @param {number} fromIndex The index to search from.
 * @param {boolean} [fromRight] Specify iterating from right to left.
 * @returns {number} Returns the index of the matched value, else `-1`.
 */
function baseFindIndex(array, predicate, fromIndex, fromRight) {
  var length = array.length,
      index = fromIndex + (fromRight ? 1 : -1);

  while (fromRight ? index-- : ++index < length) {
    if (predicate(array[index], index, array)) {
      return index;
    }
  }
  return -1;
}

module.exports = baseFindIndex;

/***/ }),
/* 914 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsNaN.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.isNaN` without support for number objects.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is `NaN`, else `false`.
 */
function baseIsNaN(value) {
  return value !== value;
}

module.exports = baseIsNaN;

/***/ }),
/* 915 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_strictIndexOf.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.indexOf` which performs strict equality
 * comparisons of values, i.e. `===`.
 *
 * @private
 * @param {Array} array The array to inspect.
 * @param {*} value The value to search for.
 * @param {number} fromIndex The index to search from.
 * @returns {number} Returns the index of the matched value, else `-1`.
 */
function strictIndexOf(array, value, fromIndex) {
  var index = fromIndex - 1,
      length = array.length;

  while (++index < length) {
    if (array[index] === value) {
      return index;
    }
  }
  return -1;
}

module.exports = strictIndexOf;

/***/ }),
/* 916 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_reorder.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var copyArray = __webpack_require__(/*! ./_copyArray */ 143),
    isIndex = __webpack_require__(/*! ./_isIndex */ 232);

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMin = Math.min;

/**
 * Reorder `array` according to the specified indexes where the element at
 * the first index is assigned as the first element, the element at
 * the second index is assigned as the second element, and so on.
 *
 * @private
 * @param {Array} array The array to reorder.
 * @param {Array} indexes The arranged array indexes.
 * @returns {Array} Returns `array`.
 */
function reorder(array, indexes) {
  var arrLength = array.length,
      length = nativeMin(indexes.length, arrLength),
      oldArray = copyArray(array);

  while (length--) {
    var index = indexes[length];
    array[length] = isIndex(index, arrLength) ? oldArray[index] : undefined;
  }
  return array;
}

module.exports = reorder;

/***/ }),
/* 917 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_createPartial.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var apply = __webpack_require__(/*! ./_apply */ 228),
    createCtor = __webpack_require__(/*! ./_createCtor */ 141),
    root = __webpack_require__(/*! ./_root */ 22);

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1;

/**
 * Creates a function that wraps `func` to invoke it with the `this` binding
 * of `thisArg` and `partials` prepended to the arguments it receives.
 *
 * @private
 * @param {Function} func The function to wrap.
 * @param {number} bitmask The bitmask flags. See `createWrap` for more details.
 * @param {*} thisArg The `this` binding of `func`.
 * @param {Array} partials The arguments to prepend to those provided to
 *  the new function.
 * @returns {Function} Returns the new wrapped function.
 */
function createPartial(func, bitmask, thisArg, partials) {
  var isBind = bitmask & WRAP_BIND_FLAG,
      Ctor = createCtor(func);

  function wrapper() {
    var argsIndex = -1,
        argsLength = arguments.length,
        leftIndex = -1,
        leftLength = partials.length,
        args = Array(leftLength + argsLength),
        fn = this && this !== root && this instanceof wrapper ? Ctor : func;

    while (++leftIndex < leftLength) {
      args[leftIndex] = partials[leftIndex];
    }
    while (argsLength--) {
      args[leftIndex++] = arguments[++argsIndex];
    }
    return apply(fn, isBind ? thisArg : this, args);
  }
  return wrapper;
}

module.exports = createPartial;

/***/ }),
/* 918 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mergeData.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var composeArgs = __webpack_require__(/*! ./_composeArgs */ 437),
    composeArgsRight = __webpack_require__(/*! ./_composeArgsRight */ 438),
    replaceHolders = __webpack_require__(/*! ./_replaceHolders */ 233);

/** Used as the internal argument placeholder. */
var PLACEHOLDER = '__lodash_placeholder__';

/** Used to compose bitmasks for function metadata. */
var WRAP_BIND_FLAG = 1,
    WRAP_BIND_KEY_FLAG = 2,
    WRAP_CURRY_BOUND_FLAG = 4,
    WRAP_CURRY_FLAG = 8,
    WRAP_ARY_FLAG = 128,
    WRAP_REARG_FLAG = 256;

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMin = Math.min;

/**
 * Merges the function metadata of `source` into `data`.
 *
 * Merging metadata reduces the number of wrappers used to invoke a function.
 * This is possible because methods like `_.bind`, `_.curry`, and `_.partial`
 * may be applied regardless of execution order. Methods like `_.ary` and
 * `_.rearg` modify function arguments, making the order in which they are
 * executed important, preventing the merging of metadata. However, we make
 * an exception for a safe combined case where curried functions have `_.ary`
 * and or `_.rearg` applied.
 *
 * @private
 * @param {Array} data The destination metadata.
 * @param {Array} source The source metadata.
 * @returns {Array} Returns `data`.
 */
function mergeData(data, source) {
  var bitmask = data[1],
      srcBitmask = source[1],
      newBitmask = bitmask | srcBitmask,
      isCommon = newBitmask < (WRAP_BIND_FLAG | WRAP_BIND_KEY_FLAG | WRAP_ARY_FLAG);

  var isCombo = srcBitmask == WRAP_ARY_FLAG && bitmask == WRAP_CURRY_FLAG || srcBitmask == WRAP_ARY_FLAG && bitmask == WRAP_REARG_FLAG && data[7].length <= source[8] || srcBitmask == (WRAP_ARY_FLAG | WRAP_REARG_FLAG) && source[7].length <= source[8] && bitmask == WRAP_CURRY_FLAG;

  // Exit early if metadata can't be merged.
  if (!(isCommon || isCombo)) {
    return data;
  }
  // Use source `thisArg` if available.
  if (srcBitmask & WRAP_BIND_FLAG) {
    data[2] = source[2];
    // Set when currying a bound function.
    newBitmask |= bitmask & WRAP_BIND_FLAG ? 0 : WRAP_CURRY_BOUND_FLAG;
  }
  // Compose partial arguments.
  var value = source[3];
  if (value) {
    var partials = data[3];
    data[3] = partials ? composeArgs(partials, value, source[4]) : value;
    data[4] = partials ? replaceHolders(data[3], PLACEHOLDER) : source[4];
  }
  // Compose partial right arguments.
  value = source[5];
  if (value) {
    partials = data[5];
    data[5] = partials ? composeArgsRight(partials, value, source[6]) : value;
    data[6] = partials ? replaceHolders(data[5], PLACEHOLDER) : source[6];
  }
  // Use source `argPos` if available.
  value = source[7];
  if (value) {
    data[7] = value;
  }
  // Use source `ary` if it's smaller.
  if (srcBitmask & WRAP_ARY_FLAG) {
    data[8] = data[8] == null ? source[8] : nativeMin(data[8], source[8]);
  }
  // Use source `arity` if one is not provided.
  if (data[9] == null) {
    data[9] = source[9];
  }
  // Use source `func` and merge bitmasks.
  data[0] = source[0];
  data[1] = newBitmask;

  return data;
}

module.exports = mergeData;

/***/ }),
/* 919 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/toFinite.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var toNumber = __webpack_require__(/*! ./toNumber */ 920);

/** Used as references for various `Number` constants. */
var INFINITY = 1 / 0,
    MAX_INTEGER = 1.7976931348623157e+308;

/**
 * Converts `value` to a finite number.
 *
 * @static
 * @memberOf _
 * @since 4.12.0
 * @category Lang
 * @param {*} value The value to convert.
 * @returns {number} Returns the converted number.
 * @example
 *
 * _.toFinite(3.2);
 * // => 3.2
 *
 * _.toFinite(Number.MIN_VALUE);
 * // => 5e-324
 *
 * _.toFinite(Infinity);
 * // => 1.7976931348623157e+308
 *
 * _.toFinite('3.2');
 * // => 3.2
 */
function toFinite(value) {
  if (!value) {
    return value === 0 ? value : 0;
  }
  value = toNumber(value);
  if (value === INFINITY || value === -INFINITY) {
    var sign = value < 0 ? -1 : 1;
    return sign * MAX_INTEGER;
  }
  return value === value ? value : 0;
}

module.exports = toFinite;

/***/ }),
/* 920 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/toNumber.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(/*! ./isObject */ 58),
    isSymbol = __webpack_require__(/*! ./isSymbol */ 112);

/** Used as references for various `Number` constants. */
var NAN = 0 / 0;

/** Used to match leading and trailing whitespace. */
var reTrim = /^\s+|\s+$/g;

/** Used to detect bad signed hexadecimal string values. */
var reIsBadHex = /^[-+]0x[0-9a-f]+$/i;

/** Used to detect binary string values. */
var reIsBinary = /^0b[01]+$/i;

/** Used to detect octal string values. */
var reIsOctal = /^0o[0-7]+$/i;

/** Built-in method references without a dependency on `root`. */
var freeParseInt = parseInt;

/**
 * Converts `value` to a number.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Lang
 * @param {*} value The value to process.
 * @returns {number} Returns the number.
 * @example
 *
 * _.toNumber(3.2);
 * // => 3.2
 *
 * _.toNumber(Number.MIN_VALUE);
 * // => 5e-324
 *
 * _.toNumber(Infinity);
 * // => Infinity
 *
 * _.toNumber('3.2');
 * // => 3.2
 */
function toNumber(value) {
  if (typeof value == 'number') {
    return value;
  }
  if (isSymbol(value)) {
    return NAN;
  }
  if (isObject(value)) {
    var other = typeof value.valueOf == 'function' ? value.valueOf() : value;
    value = isObject(other) ? other + '' : other;
  }
  if (typeof value != 'string') {
    return value === 0 ? value : +value;
  }
  value = value.replace(reTrim, '');
  var isBinary = reIsBinary.test(value);
  return isBinary || reIsOctal.test(value) ? freeParseInt(value.slice(2), isBinary ? 2 : 8) : reIsBadHex.test(value) ? NAN : +value;
}

module.exports = toNumber;

/***/ }),
/* 921 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseTimes.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.times` without support for iteratee shorthands
 * or max array length checks.
 *
 * @private
 * @param {number} n The number of times to invoke `iteratee`.
 * @param {Function} iteratee The function invoked per iteration.
 * @returns {Array} Returns the array of results.
 */
function baseTimes(n, iteratee) {
  var index = -1,
      result = Array(n);

  while (++index < n) {
    result[index] = iteratee(index);
  }
  return result;
}

module.exports = baseTimes;

/***/ }),
/* 922 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsArguments.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/** `Object#toString` result references. */
var argsTag = '[object Arguments]';

/**
 * The base implementation of `_.isArguments`.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is an `arguments` object,
 */
function baseIsArguments(value) {
  return isObjectLike(value) && baseGetTag(value) == argsTag;
}

module.exports = baseIsArguments;

/***/ }),
/* 923 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/stubFalse.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * This method returns `false`.
 *
 * @static
 * @memberOf _
 * @since 4.13.0
 * @category Util
 * @returns {boolean} Returns `false`.
 * @example
 *
 * _.times(2, _.stubFalse);
 * // => [false, false]
 */
function stubFalse() {
  return false;
}

module.exports = stubFalse;

/***/ }),
/* 924 */
/*!***********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsTypedArray.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    isLength = __webpack_require__(/*! ./isLength */ 237),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/** `Object#toString` result references. */
var argsTag = '[object Arguments]',
    arrayTag = '[object Array]',
    boolTag = '[object Boolean]',
    dateTag = '[object Date]',
    errorTag = '[object Error]',
    funcTag = '[object Function]',
    mapTag = '[object Map]',
    numberTag = '[object Number]',
    objectTag = '[object Object]',
    regexpTag = '[object RegExp]',
    setTag = '[object Set]',
    stringTag = '[object String]',
    weakMapTag = '[object WeakMap]';

var arrayBufferTag = '[object ArrayBuffer]',
    dataViewTag = '[object DataView]',
    float32Tag = '[object Float32Array]',
    float64Tag = '[object Float64Array]',
    int8Tag = '[object Int8Array]',
    int16Tag = '[object Int16Array]',
    int32Tag = '[object Int32Array]',
    uint8Tag = '[object Uint8Array]',
    uint8ClampedTag = '[object Uint8ClampedArray]',
    uint16Tag = '[object Uint16Array]',
    uint32Tag = '[object Uint32Array]';

/** Used to identify `toStringTag` values of typed arrays. */
var typedArrayTags = {};
typedArrayTags[float32Tag] = typedArrayTags[float64Tag] = typedArrayTags[int8Tag] = typedArrayTags[int16Tag] = typedArrayTags[int32Tag] = typedArrayTags[uint8Tag] = typedArrayTags[uint8ClampedTag] = typedArrayTags[uint16Tag] = typedArrayTags[uint32Tag] = true;
typedArrayTags[argsTag] = typedArrayTags[arrayTag] = typedArrayTags[arrayBufferTag] = typedArrayTags[boolTag] = typedArrayTags[dataViewTag] = typedArrayTags[dateTag] = typedArrayTags[errorTag] = typedArrayTags[funcTag] = typedArrayTags[mapTag] = typedArrayTags[numberTag] = typedArrayTags[objectTag] = typedArrayTags[regexpTag] = typedArrayTags[setTag] = typedArrayTags[stringTag] = typedArrayTags[weakMapTag] = false;

/**
 * The base implementation of `_.isTypedArray` without Node.js optimizations.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
 */
function baseIsTypedArray(value) {
    return isObjectLike(value) && isLength(value.length) && !!typedArrayTags[baseGetTag(value)];
}

module.exports = baseIsTypedArray;

/***/ }),
/* 925 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseUnary.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.unary` without support for storing metadata.
 *
 * @private
 * @param {Function} func The function to cap arguments for.
 * @returns {Function} Returns the new capped function.
 */
function baseUnary(func) {
  return function (value) {
    return func(value);
  };
}

module.exports = baseUnary;

/***/ }),
/* 926 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_nodeUtil.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var freeGlobal = __webpack_require__(/*! ./_freeGlobal */ 434);

/** Detect free variable `exports`. */
var freeExports = ( false ? 'undefined' : _typeof(exports)) == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && ( false ? 'undefined' : _typeof(module)) == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Detect free variable `process` from Node.js. */
var freeProcess = moduleExports && freeGlobal.process;

/** Used to access faster Node.js helpers. */
var nodeUtil = function () {
  try {
    return freeProcess && freeProcess.binding && freeProcess.binding('util');
  } catch (e) {}
}();

module.exports = nodeUtil;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 927 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_nativeKeys.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var overArg = __webpack_require__(/*! ./_overArg */ 455);

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeKeys = overArg(Object.keys, Object);

module.exports = nativeKeys;

/***/ }),
/* 928 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/clone.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseClone = __webpack_require__(/*! ./_baseClone */ 239);

/** Used to compose bitmasks for cloning. */
var CLONE_SYMBOLS_FLAG = 4;

/**
 * Creates a shallow clone of `value`.
 *
 * **Note:** This method is loosely based on the
 * [structured clone algorithm](https://mdn.io/Structured_clone_algorithm)
 * and supports cloning arrays, array buffers, booleans, date objects, maps,
 * numbers, `Object` objects, regexes, sets, strings, symbols, and typed
 * arrays. The own enumerable properties of `arguments` objects are cloned
 * as plain objects. An empty object is returned for uncloneable values such
 * as error objects, functions, DOM nodes, and WeakMaps.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Lang
 * @param {*} value The value to clone.
 * @returns {*} Returns the cloned value.
 * @see _.cloneDeep
 * @example
 *
 * var objects = [{ 'a': 1 }, { 'b': 2 }];
 *
 * var shallow = _.clone(objects);
 * console.log(shallow[0] === objects[0]);
 * // => true
 */
function clone(value) {
  return baseClone(value, CLONE_SYMBOLS_FLAG);
}

module.exports = clone;

/***/ }),
/* 929 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_listCacheClear.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Removes all key-value entries from the list cache.
 *
 * @private
 * @name clear
 * @memberOf ListCache
 */
function listCacheClear() {
  this.__data__ = [];
  this.size = 0;
}

module.exports = listCacheClear;

/***/ }),
/* 930 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_listCacheDelete.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ 146);

/** Used for built-in method references. */
var arrayProto = Array.prototype;

/** Built-in value references. */
var splice = arrayProto.splice;

/**
 * Removes `key` and its value from the list cache.
 *
 * @private
 * @name delete
 * @memberOf ListCache
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function listCacheDelete(key) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  if (index < 0) {
    return false;
  }
  var lastIndex = data.length - 1;
  if (index == lastIndex) {
    data.pop();
  } else {
    splice.call(data, index, 1);
  }
  --this.size;
  return true;
}

module.exports = listCacheDelete;

/***/ }),
/* 931 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_listCacheGet.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ 146);

/**
 * Gets the list cache value for `key`.
 *
 * @private
 * @name get
 * @memberOf ListCache
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function listCacheGet(key) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  return index < 0 ? undefined : data[index][1];
}

module.exports = listCacheGet;

/***/ }),
/* 932 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_listCacheHas.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ 146);

/**
 * Checks if a list cache value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf ListCache
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function listCacheHas(key) {
  return assocIndexOf(this.__data__, key) > -1;
}

module.exports = listCacheHas;

/***/ }),
/* 933 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_listCacheSet.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ 146);

/**
 * Sets the list cache `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf ListCache
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the list cache instance.
 */
function listCacheSet(key, value) {
  var data = this.__data__,
      index = assocIndexOf(data, key);

  if (index < 0) {
    ++this.size;
    data.push([key, value]);
  } else {
    data[index][1] = value;
  }
  return this;
}

module.exports = listCacheSet;

/***/ }),
/* 934 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stackClear.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var ListCache = __webpack_require__(/*! ./_ListCache */ 145);

/**
 * Removes all key-value entries from the stack.
 *
 * @private
 * @name clear
 * @memberOf Stack
 */
function stackClear() {
  this.__data__ = new ListCache();
  this.size = 0;
}

module.exports = stackClear;

/***/ }),
/* 935 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stackDelete.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Removes `key` and its value from the stack.
 *
 * @private
 * @name delete
 * @memberOf Stack
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function stackDelete(key) {
  var data = this.__data__,
      result = data['delete'](key);

  this.size = data.size;
  return result;
}

module.exports = stackDelete;

/***/ }),
/* 936 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stackGet.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Gets the stack value for `key`.
 *
 * @private
 * @name get
 * @memberOf Stack
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function stackGet(key) {
  return this.__data__.get(key);
}

module.exports = stackGet;

/***/ }),
/* 937 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stackHas.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Checks if a stack value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf Stack
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function stackHas(key) {
  return this.__data__.has(key);
}

module.exports = stackHas;

/***/ }),
/* 938 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_stackSet.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var ListCache = __webpack_require__(/*! ./_ListCache */ 145),
    Map = __webpack_require__(/*! ./_Map */ 241),
    MapCache = __webpack_require__(/*! ./_MapCache */ 242);

/** Used as the size to enable large array optimizations. */
var LARGE_ARRAY_SIZE = 200;

/**
 * Sets the stack `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf Stack
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the stack cache instance.
 */
function stackSet(key, value) {
  var data = this.__data__;
  if (data instanceof ListCache) {
    var pairs = data.__data__;
    if (!Map || pairs.length < LARGE_ARRAY_SIZE - 1) {
      pairs.push([key, value]);
      this.size = ++data.size;
      return this;
    }
    data = this.__data__ = new MapCache(pairs);
  }
  data.set(key, value);
  this.size = data.size;
  return this;
}

module.exports = stackSet;

/***/ }),
/* 939 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapCacheClear.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Hash = __webpack_require__(/*! ./_Hash */ 940),
    ListCache = __webpack_require__(/*! ./_ListCache */ 145),
    Map = __webpack_require__(/*! ./_Map */ 241);

/**
 * Removes all key-value entries from the map.
 *
 * @private
 * @name clear
 * @memberOf MapCache
 */
function mapCacheClear() {
  this.size = 0;
  this.__data__ = {
    'hash': new Hash(),
    'map': new (Map || ListCache)(),
    'string': new Hash()
  };
}

module.exports = mapCacheClear;

/***/ }),
/* 940 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Hash.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var hashClear = __webpack_require__(/*! ./_hashClear */ 941),
    hashDelete = __webpack_require__(/*! ./_hashDelete */ 942),
    hashGet = __webpack_require__(/*! ./_hashGet */ 943),
    hashHas = __webpack_require__(/*! ./_hashHas */ 944),
    hashSet = __webpack_require__(/*! ./_hashSet */ 945);

/**
 * Creates a hash object.
 *
 * @private
 * @constructor
 * @param {Array} [entries] The key-value pairs to cache.
 */
function Hash(entries) {
    var index = -1,
        length = entries == null ? 0 : entries.length;

    this.clear();
    while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
    }
}

// Add methods to `Hash`.
Hash.prototype.clear = hashClear;
Hash.prototype['delete'] = hashDelete;
Hash.prototype.get = hashGet;
Hash.prototype.has = hashHas;
Hash.prototype.set = hashSet;

module.exports = Hash;

/***/ }),
/* 941 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hashClear.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ 147);

/**
 * Removes all key-value entries from the hash.
 *
 * @private
 * @name clear
 * @memberOf Hash
 */
function hashClear() {
  this.__data__ = nativeCreate ? nativeCreate(null) : {};
  this.size = 0;
}

module.exports = hashClear;

/***/ }),
/* 942 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hashDelete.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Removes `key` and its value from the hash.
 *
 * @private
 * @name delete
 * @memberOf Hash
 * @param {Object} hash The hash to modify.
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function hashDelete(key) {
  var result = this.has(key) && delete this.__data__[key];
  this.size -= result ? 1 : 0;
  return result;
}

module.exports = hashDelete;

/***/ }),
/* 943 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hashGet.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ 147);

/** Used to stand-in for `undefined` hash values. */
var HASH_UNDEFINED = '__lodash_hash_undefined__';

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Gets the hash value for `key`.
 *
 * @private
 * @name get
 * @memberOf Hash
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function hashGet(key) {
  var data = this.__data__;
  if (nativeCreate) {
    var result = data[key];
    return result === HASH_UNDEFINED ? undefined : result;
  }
  return hasOwnProperty.call(data, key) ? data[key] : undefined;
}

module.exports = hashGet;

/***/ }),
/* 944 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hashHas.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ 147);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Checks if a hash value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf Hash
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function hashHas(key) {
  var data = this.__data__;
  return nativeCreate ? data[key] !== undefined : hasOwnProperty.call(data, key);
}

module.exports = hashHas;

/***/ }),
/* 945 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hashSet.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ 147);

/** Used to stand-in for `undefined` hash values. */
var HASH_UNDEFINED = '__lodash_hash_undefined__';

/**
 * Sets the hash `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf Hash
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the hash instance.
 */
function hashSet(key, value) {
  var data = this.__data__;
  this.size += this.has(key) ? 0 : 1;
  data[key] = nativeCreate && value === undefined ? HASH_UNDEFINED : value;
  return this;
}

module.exports = hashSet;

/***/ }),
/* 946 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapCacheDelete.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getMapData = __webpack_require__(/*! ./_getMapData */ 148);

/**
 * Removes `key` and its value from the map.
 *
 * @private
 * @name delete
 * @memberOf MapCache
 * @param {string} key The key of the value to remove.
 * @returns {boolean} Returns `true` if the entry was removed, else `false`.
 */
function mapCacheDelete(key) {
  var result = getMapData(this, key)['delete'](key);
  this.size -= result ? 1 : 0;
  return result;
}

module.exports = mapCacheDelete;

/***/ }),
/* 947 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isKeyable.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * Checks if `value` is suitable for use as unique object key.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is suitable, else `false`.
 */
function isKeyable(value) {
  var type = typeof value === 'undefined' ? 'undefined' : _typeof(value);
  return type == 'string' || type == 'number' || type == 'symbol' || type == 'boolean' ? value !== '__proto__' : value === null;
}

module.exports = isKeyable;

/***/ }),
/* 948 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapCacheGet.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getMapData = __webpack_require__(/*! ./_getMapData */ 148);

/**
 * Gets the map value for `key`.
 *
 * @private
 * @name get
 * @memberOf MapCache
 * @param {string} key The key of the value to get.
 * @returns {*} Returns the entry value.
 */
function mapCacheGet(key) {
  return getMapData(this, key).get(key);
}

module.exports = mapCacheGet;

/***/ }),
/* 949 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapCacheHas.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getMapData = __webpack_require__(/*! ./_getMapData */ 148);

/**
 * Checks if a map value for `key` exists.
 *
 * @private
 * @name has
 * @memberOf MapCache
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function mapCacheHas(key) {
  return getMapData(this, key).has(key);
}

module.exports = mapCacheHas;

/***/ }),
/* 950 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_mapCacheSet.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getMapData = __webpack_require__(/*! ./_getMapData */ 148);

/**
 * Sets the map `key` to `value`.
 *
 * @private
 * @name set
 * @memberOf MapCache
 * @param {string} key The key of the value to set.
 * @param {*} value The value to set.
 * @returns {Object} Returns the map cache instance.
 */
function mapCacheSet(key, value) {
  var data = getMapData(this, key),
      size = data.size;

  data.set(key, value);
  this.size += data.size == size ? 0 : 1;
  return this;
}

module.exports = mapCacheSet;

/***/ }),
/* 951 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseAssignIn.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var copyObject = __webpack_require__(/*! ./_copyObject */ 113),
    keysIn = __webpack_require__(/*! ./keysIn */ 457);

/**
 * The base implementation of `_.assignIn` without support for multiple sources
 * or `customizer` functions.
 *
 * @private
 * @param {Object} object The destination object.
 * @param {Object} source The source object.
 * @returns {Object} Returns `object`.
 */
function baseAssignIn(object, source) {
  return object && copyObject(source, keysIn(source), object);
}

module.exports = baseAssignIn;

/***/ }),
/* 952 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseKeysIn.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(/*! ./isObject */ 58),
    isPrototype = __webpack_require__(/*! ./_isPrototype */ 238),
    nativeKeysIn = __webpack_require__(/*! ./_nativeKeysIn */ 953);

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * The base implementation of `_.keysIn` which doesn't treat sparse arrays as dense.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 */
function baseKeysIn(object) {
  if (!isObject(object)) {
    return nativeKeysIn(object);
  }
  var isProto = isPrototype(object),
      result = [];

  for (var key in object) {
    if (!(key == 'constructor' && (isProto || !hasOwnProperty.call(object, key)))) {
      result.push(key);
    }
  }
  return result;
}

module.exports = baseKeysIn;

/***/ }),
/* 953 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_nativeKeysIn.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * This function is like
 * [`Object.keys`](http://ecma-international.org/ecma-262/7.0/#sec-object.keys)
 * except that it includes inherited enumerable properties.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the array of property names.
 */
function nativeKeysIn(object) {
  var result = [];
  if (object != null) {
    for (var key in Object(object)) {
      result.push(key);
    }
  }
  return result;
}

module.exports = nativeKeysIn;

/***/ }),
/* 954 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneBuffer.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var root = __webpack_require__(/*! ./_root */ 22);

/** Detect free variable `exports`. */
var freeExports = ( false ? 'undefined' : _typeof(exports)) == 'object' && exports && !exports.nodeType && exports;

/** Detect free variable `module`. */
var freeModule = freeExports && ( false ? 'undefined' : _typeof(module)) == 'object' && module && !module.nodeType && module;

/** Detect the popular CommonJS extension `module.exports`. */
var moduleExports = freeModule && freeModule.exports === freeExports;

/** Built-in value references. */
var Buffer = moduleExports ? root.Buffer : undefined,
    allocUnsafe = Buffer ? Buffer.allocUnsafe : undefined;

/**
 * Creates a clone of  `buffer`.
 *
 * @private
 * @param {Buffer} buffer The buffer to clone.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Buffer} Returns the cloned buffer.
 */
function cloneBuffer(buffer, isDeep) {
  if (isDeep) {
    return buffer.slice();
  }
  var length = buffer.length,
      result = allocUnsafe ? allocUnsafe(length) : new buffer.constructor(length);

  buffer.copy(result);
  return result;
}

module.exports = cloneBuffer;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 955 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_copySymbols.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var copyObject = __webpack_require__(/*! ./_copyObject */ 113),
    getSymbols = __webpack_require__(/*! ./_getSymbols */ 243);

/**
 * Copies own symbols of `source` to `object`.
 *
 * @private
 * @param {Object} source The object to copy symbols from.
 * @param {Object} [object={}] The object to copy symbols to.
 * @returns {Object} Returns `object`.
 */
function copySymbols(source, object) {
  return copyObject(source, getSymbols(source), object);
}

module.exports = copySymbols;

/***/ }),
/* 956 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arrayFilter.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.filter` for arrays without support for
 * iteratee shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} predicate The function invoked per iteration.
 * @returns {Array} Returns the new filtered array.
 */
function arrayFilter(array, predicate) {
  var index = -1,
      length = array == null ? 0 : array.length,
      resIndex = 0,
      result = [];

  while (++index < length) {
    var value = array[index];
    if (predicate(value, index, array)) {
      result[resIndex++] = value;
    }
  }
  return result;
}

module.exports = arrayFilter;

/***/ }),
/* 957 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_copySymbolsIn.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var copyObject = __webpack_require__(/*! ./_copyObject */ 113),
    getSymbolsIn = __webpack_require__(/*! ./_getSymbolsIn */ 459);

/**
 * Copies own and inherited symbols of `source` to `object`.
 *
 * @private
 * @param {Object} source The object to copy symbols from.
 * @param {Object} [object={}] The object to copy symbols to.
 * @returns {Object} Returns `object`.
 */
function copySymbolsIn(source, object) {
  return copyObject(source, getSymbolsIn(source), object);
}

module.exports = copySymbolsIn;

/***/ }),
/* 958 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_DataView.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68),
    root = __webpack_require__(/*! ./_root */ 22);

/* Built-in method references that are verified to be native. */
var DataView = getNative(root, 'DataView');

module.exports = DataView;

/***/ }),
/* 959 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Promise.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68),
    root = __webpack_require__(/*! ./_root */ 22);

/* Built-in method references that are verified to be native. */
var Promise = getNative(root, 'Promise');

module.exports = Promise;

/***/ }),
/* 960 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_Set.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getNative = __webpack_require__(/*! ./_getNative */ 68),
    root = __webpack_require__(/*! ./_root */ 22);

/* Built-in method references that are verified to be native. */
var Set = getNative(root, 'Set');

module.exports = Set;

/***/ }),
/* 961 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_initCloneArray.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * Initializes an array clone.
 *
 * @private
 * @param {Array} array The array to clone.
 * @returns {Array} Returns the initialized clone.
 */
function initCloneArray(array) {
  var length = array.length,
      result = array.constructor(length);

  // Add properties assigned by `RegExp#exec`.
  if (length && typeof array[0] == 'string' && hasOwnProperty.call(array, 'index')) {
    result.index = array.index;
    result.input = array.input;
  }
  return result;
}

module.exports = initCloneArray;

/***/ }),
/* 962 */
/*!*********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_initCloneByTag.js ***!
  \*********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var cloneArrayBuffer = __webpack_require__(/*! ./_cloneArrayBuffer */ 246),
    cloneDataView = __webpack_require__(/*! ./_cloneDataView */ 963),
    cloneMap = __webpack_require__(/*! ./_cloneMap */ 964),
    cloneRegExp = __webpack_require__(/*! ./_cloneRegExp */ 966),
    cloneSet = __webpack_require__(/*! ./_cloneSet */ 967),
    cloneSymbol = __webpack_require__(/*! ./_cloneSymbol */ 969),
    cloneTypedArray = __webpack_require__(/*! ./_cloneTypedArray */ 970);

/** `Object#toString` result references. */
var boolTag = '[object Boolean]',
    dateTag = '[object Date]',
    mapTag = '[object Map]',
    numberTag = '[object Number]',
    regexpTag = '[object RegExp]',
    setTag = '[object Set]',
    stringTag = '[object String]',
    symbolTag = '[object Symbol]';

var arrayBufferTag = '[object ArrayBuffer]',
    dataViewTag = '[object DataView]',
    float32Tag = '[object Float32Array]',
    float64Tag = '[object Float64Array]',
    int8Tag = '[object Int8Array]',
    int16Tag = '[object Int16Array]',
    int32Tag = '[object Int32Array]',
    uint8Tag = '[object Uint8Array]',
    uint8ClampedTag = '[object Uint8ClampedArray]',
    uint16Tag = '[object Uint16Array]',
    uint32Tag = '[object Uint32Array]';

/**
 * Initializes an object clone based on its `toStringTag`.
 *
 * **Note:** This function only supports cloning values with tags of
 * `Boolean`, `Date`, `Error`, `Number`, `RegExp`, or `String`.
 *
 * @private
 * @param {Object} object The object to clone.
 * @param {string} tag The `toStringTag` of the object to clone.
 * @param {Function} cloneFunc The function to clone values.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the initialized clone.
 */
function initCloneByTag(object, tag, cloneFunc, isDeep) {
  var Ctor = object.constructor;
  switch (tag) {
    case arrayBufferTag:
      return cloneArrayBuffer(object);

    case boolTag:
    case dateTag:
      return new Ctor(+object);

    case dataViewTag:
      return cloneDataView(object, isDeep);

    case float32Tag:case float64Tag:
    case int8Tag:case int16Tag:case int32Tag:
    case uint8Tag:case uint8ClampedTag:case uint16Tag:case uint32Tag:
      return cloneTypedArray(object, isDeep);

    case mapTag:
      return cloneMap(object, isDeep, cloneFunc);

    case numberTag:
    case stringTag:
      return new Ctor(object);

    case regexpTag:
      return cloneRegExp(object);

    case setTag:
      return cloneSet(object, isDeep, cloneFunc);

    case symbolTag:
      return cloneSymbol(object);
  }
}

module.exports = initCloneByTag;

/***/ }),
/* 963 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneDataView.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var cloneArrayBuffer = __webpack_require__(/*! ./_cloneArrayBuffer */ 246);

/**
 * Creates a clone of `dataView`.
 *
 * @private
 * @param {Object} dataView The data view to clone.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the cloned data view.
 */
function cloneDataView(dataView, isDeep) {
  var buffer = isDeep ? cloneArrayBuffer(dataView.buffer) : dataView.buffer;
  return new dataView.constructor(buffer, dataView.byteOffset, dataView.byteLength);
}

module.exports = cloneDataView;

/***/ }),
/* 964 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneMap.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var addMapEntry = __webpack_require__(/*! ./_addMapEntry */ 965),
    arrayReduce = __webpack_require__(/*! ./_arrayReduce */ 465),
    mapToArray = __webpack_require__(/*! ./_mapToArray */ 466);

/** Used to compose bitmasks for cloning. */
var CLONE_DEEP_FLAG = 1;

/**
 * Creates a clone of `map`.
 *
 * @private
 * @param {Object} map The map to clone.
 * @param {Function} cloneFunc The function to clone values.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the cloned map.
 */
function cloneMap(map, isDeep, cloneFunc) {
  var array = isDeep ? cloneFunc(mapToArray(map), CLONE_DEEP_FLAG) : mapToArray(map);
  return arrayReduce(array, addMapEntry, new map.constructor());
}

module.exports = cloneMap;

/***/ }),
/* 965 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_addMapEntry.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Adds the key-value `pair` to `map`.
 *
 * @private
 * @param {Object} map The map to modify.
 * @param {Array} pair The key-value pair to add.
 * @returns {Object} Returns `map`.
 */
function addMapEntry(map, pair) {
  // Don't return `map.set` because it's not chainable in IE 11.
  map.set(pair[0], pair[1]);
  return map;
}

module.exports = addMapEntry;

/***/ }),
/* 966 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneRegExp.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to match `RegExp` flags from their coerced string values. */
var reFlags = /\w*$/;

/**
 * Creates a clone of `regexp`.
 *
 * @private
 * @param {Object} regexp The regexp to clone.
 * @returns {Object} Returns the cloned regexp.
 */
function cloneRegExp(regexp) {
  var result = new regexp.constructor(regexp.source, reFlags.exec(regexp));
  result.lastIndex = regexp.lastIndex;
  return result;
}

module.exports = cloneRegExp;

/***/ }),
/* 967 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneSet.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var addSetEntry = __webpack_require__(/*! ./_addSetEntry */ 968),
    arrayReduce = __webpack_require__(/*! ./_arrayReduce */ 465),
    setToArray = __webpack_require__(/*! ./_setToArray */ 467);

/** Used to compose bitmasks for cloning. */
var CLONE_DEEP_FLAG = 1;

/**
 * Creates a clone of `set`.
 *
 * @private
 * @param {Object} set The set to clone.
 * @param {Function} cloneFunc The function to clone values.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the cloned set.
 */
function cloneSet(set, isDeep, cloneFunc) {
  var array = isDeep ? cloneFunc(setToArray(set), CLONE_DEEP_FLAG) : setToArray(set);
  return arrayReduce(array, addSetEntry, new set.constructor());
}

module.exports = cloneSet;

/***/ }),
/* 968 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_addSetEntry.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Adds `value` to `set`.
 *
 * @private
 * @param {Object} set The set to modify.
 * @param {*} value The value to add.
 * @returns {Object} Returns `set`.
 */
function addSetEntry(set, value) {
  // Don't return `set.add` because it's not chainable in IE 11.
  set.add(value);
  return set;
}

module.exports = addSetEntry;

/***/ }),
/* 969 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneSymbol.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93);

/** Used to convert symbols to primitives and strings. */
var symbolProto = _Symbol ? _Symbol.prototype : undefined,
    symbolValueOf = symbolProto ? symbolProto.valueOf : undefined;

/**
 * Creates a clone of the `symbol` object.
 *
 * @private
 * @param {Object} symbol The symbol object to clone.
 * @returns {Object} Returns the cloned symbol object.
 */
function cloneSymbol(symbol) {
  return symbolValueOf ? Object(symbolValueOf.call(symbol)) : {};
}

module.exports = cloneSymbol;

/***/ }),
/* 970 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cloneTypedArray.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var cloneArrayBuffer = __webpack_require__(/*! ./_cloneArrayBuffer */ 246);

/**
 * Creates a clone of `typedArray`.
 *
 * @private
 * @param {Object} typedArray The typed array to clone.
 * @param {boolean} [isDeep] Specify a deep clone.
 * @returns {Object} Returns the cloned typed array.
 */
function cloneTypedArray(typedArray, isDeep) {
  var buffer = isDeep ? cloneArrayBuffer(typedArray.buffer) : typedArray.buffer;
  return new typedArray.constructor(buffer, typedArray.byteOffset, typedArray.length);
}

module.exports = cloneTypedArray;

/***/ }),
/* 971 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_initCloneObject.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseCreate = __webpack_require__(/*! ./_baseCreate */ 142),
    getPrototype = __webpack_require__(/*! ./_getPrototype */ 245),
    isPrototype = __webpack_require__(/*! ./_isPrototype */ 238);

/**
 * Initializes an object clone.
 *
 * @private
 * @param {Object} object The object to clone.
 * @returns {Object} Returns the initialized clone.
 */
function initCloneObject(object) {
    return typeof object.constructor == 'function' && !isPrototype(object) ? baseCreate(getPrototype(object)) : {};
}

module.exports = initCloneObject;

/***/ }),
/* 972 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/curry.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var createWrap = __webpack_require__(/*! ./_createWrap */ 225);

/** Used to compose bitmasks for function metadata. */
var WRAP_CURRY_FLAG = 8;

/**
 * Creates a function that accepts arguments of `func` and either invokes
 * `func` returning its result, if at least `arity` number of arguments have
 * been provided, or returns a function that accepts the remaining `func`
 * arguments, and so on. The arity of `func` may be specified if `func.length`
 * is not sufficient.
 *
 * The `_.curry.placeholder` value, which defaults to `_` in monolithic builds,
 * may be used as a placeholder for provided arguments.
 *
 * **Note:** This method doesn't set the "length" property of curried functions.
 *
 * @static
 * @memberOf _
 * @since 2.0.0
 * @category Function
 * @param {Function} func The function to curry.
 * @param {number} [arity=func.length] The arity of `func`.
 * @param- {Object} [guard] Enables use as an iteratee for methods like `_.map`.
 * @returns {Function} Returns the new curried function.
 * @example
 *
 * var abc = function(a, b, c) {
 *   return [a, b, c];
 * };
 *
 * var curried = _.curry(abc);
 *
 * curried(1)(2)(3);
 * // => [1, 2, 3]
 *
 * curried(1, 2)(3);
 * // => [1, 2, 3]
 *
 * curried(1, 2, 3);
 * // => [1, 2, 3]
 *
 * // Curried with placeholders.
 * curried(1)(_, 3)(2);
 * // => [1, 2, 3]
 */
function curry(func, arity, guard) {
  arity = guard ? undefined : arity;
  var result = createWrap(func, WRAP_CURRY_FLAG, undefined, undefined, undefined, undefined, undefined, arity);
  result.placeholder = curry.placeholder;
  return result;
}

// Assign default placeholders.
curry.placeholder = {};

module.exports = curry;

/***/ }),
/* 973 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/iteratee.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseClone = __webpack_require__(/*! ./_baseClone */ 239),
    baseIteratee = __webpack_require__(/*! ./_baseIteratee */ 974);

/** Used to compose bitmasks for cloning. */
var CLONE_DEEP_FLAG = 1;

/**
 * Creates a function that invokes `func` with the arguments of the created
 * function. If `func` is a property name, the created function returns the
 * property value for a given element. If `func` is an array or object, the
 * created function returns `true` for elements that contain the equivalent
 * source properties, otherwise it returns `false`.
 *
 * @static
 * @since 4.0.0
 * @memberOf _
 * @category Util
 * @param {*} [func=_.identity] The value to convert to a callback.
 * @returns {Function} Returns the callback.
 * @example
 *
 * var users = [
 *   { 'user': 'barney', 'age': 36, 'active': true },
 *   { 'user': 'fred',   'age': 40, 'active': false }
 * ];
 *
 * // The `_.matches` iteratee shorthand.
 * _.filter(users, _.iteratee({ 'user': 'barney', 'active': true }));
 * // => [{ 'user': 'barney', 'age': 36, 'active': true }]
 *
 * // The `_.matchesProperty` iteratee shorthand.
 * _.filter(users, _.iteratee(['user', 'fred']));
 * // => [{ 'user': 'fred', 'age': 40 }]
 *
 * // The `_.property` iteratee shorthand.
 * _.map(users, _.iteratee('user'));
 * // => ['barney', 'fred']
 *
 * // Create custom iteratee shorthands.
 * _.iteratee = _.wrap(_.iteratee, function(iteratee, func) {
 *   return !_.isRegExp(func) ? iteratee(func) : function(string) {
 *     return func.test(string);
 *   };
 * });
 *
 * _.filter(['abc', 'def'], /ef/);
 * // => ['def']
 */
function iteratee(func) {
  return baseIteratee(typeof func == 'function' ? func : baseClone(func, CLONE_DEEP_FLAG));
}

module.exports = iteratee;

/***/ }),
/* 974 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIteratee.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var baseMatches = __webpack_require__(/*! ./_baseMatches */ 975),
    baseMatchesProperty = __webpack_require__(/*! ./_baseMatchesProperty */ 986),
    identity = __webpack_require__(/*! ./identity */ 226),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    property = __webpack_require__(/*! ./property */ 994);

/**
 * The base implementation of `_.iteratee`.
 *
 * @private
 * @param {*} [value=_.identity] The value to convert to an iteratee.
 * @returns {Function} Returns the iteratee.
 */
function baseIteratee(value) {
  // Don't store the `typeof` result in a variable to avoid a JIT bug in Safari 9.
  // See https://bugs.webkit.org/show_bug.cgi?id=156034 for more details.
  if (typeof value == 'function') {
    return value;
  }
  if (value == null) {
    return identity;
  }
  if ((typeof value === 'undefined' ? 'undefined' : _typeof(value)) == 'object') {
    return isArray(value) ? baseMatchesProperty(value[0], value[1]) : baseMatches(value);
  }
  return property(value);
}

module.exports = baseIteratee;

/***/ }),
/* 975 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseMatches.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsMatch = __webpack_require__(/*! ./_baseIsMatch */ 976),
    getMatchData = __webpack_require__(/*! ./_getMatchData */ 985),
    matchesStrictComparable = __webpack_require__(/*! ./_matchesStrictComparable */ 471);

/**
 * The base implementation of `_.matches` which doesn't clone `source`.
 *
 * @private
 * @param {Object} source The object of property values to match.
 * @returns {Function} Returns the new spec function.
 */
function baseMatches(source) {
  var matchData = getMatchData(source);
  if (matchData.length == 1 && matchData[0][2]) {
    return matchesStrictComparable(matchData[0][0], matchData[0][1]);
  }
  return function (object) {
    return object === source || baseIsMatch(object, source, matchData);
  };
}

module.exports = baseMatches;

/***/ }),
/* 976 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsMatch.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Stack = __webpack_require__(/*! ./_Stack */ 240),
    baseIsEqual = __webpack_require__(/*! ./_baseIsEqual */ 468);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1,
    COMPARE_UNORDERED_FLAG = 2;

/**
 * The base implementation of `_.isMatch` without support for iteratee shorthands.
 *
 * @private
 * @param {Object} object The object to inspect.
 * @param {Object} source The object of property values to match.
 * @param {Array} matchData The property names, values, and compare flags to match.
 * @param {Function} [customizer] The function to customize comparisons.
 * @returns {boolean} Returns `true` if `object` is a match, else `false`.
 */
function baseIsMatch(object, source, matchData, customizer) {
  var index = matchData.length,
      length = index,
      noCustomizer = !customizer;

  if (object == null) {
    return !length;
  }
  object = Object(object);
  while (index--) {
    var data = matchData[index];
    if (noCustomizer && data[2] ? data[1] !== object[data[0]] : !(data[0] in object)) {
      return false;
    }
  }
  while (++index < length) {
    data = matchData[index];
    var key = data[0],
        objValue = object[key],
        srcValue = data[1];

    if (noCustomizer && data[2]) {
      if (objValue === undefined && !(key in object)) {
        return false;
      }
    } else {
      var stack = new Stack();
      if (customizer) {
        var result = customizer(objValue, srcValue, key, object, source, stack);
      }
      if (!(result === undefined ? baseIsEqual(srcValue, objValue, COMPARE_PARTIAL_FLAG | COMPARE_UNORDERED_FLAG, customizer, stack) : result)) {
        return false;
      }
    }
  }
  return true;
}

module.exports = baseIsMatch;

/***/ }),
/* 977 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseIsEqualDeep.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var Stack = __webpack_require__(/*! ./_Stack */ 240),
    equalArrays = __webpack_require__(/*! ./_equalArrays */ 469),
    equalByTag = __webpack_require__(/*! ./_equalByTag */ 983),
    equalObjects = __webpack_require__(/*! ./_equalObjects */ 984),
    getTag = __webpack_require__(/*! ./_getTag */ 463),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isBuffer = __webpack_require__(/*! ./isBuffer */ 236),
    isTypedArray = __webpack_require__(/*! ./isTypedArray */ 453);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1;

/** `Object#toString` result references. */
var argsTag = '[object Arguments]',
    arrayTag = '[object Array]',
    objectTag = '[object Object]';

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * A specialized version of `baseIsEqual` for arrays and objects which performs
 * deep comparisons and tracks traversed objects enabling objects with circular
 * references to be compared.
 *
 * @private
 * @param {Object} object The object to compare.
 * @param {Object} other The other object to compare.
 * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
 * @param {Function} customizer The function to customize comparisons.
 * @param {Function} equalFunc The function to determine equivalents of values.
 * @param {Object} [stack] Tracks traversed `object` and `other` objects.
 * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
 */
function baseIsEqualDeep(object, other, bitmask, customizer, equalFunc, stack) {
  var objIsArr = isArray(object),
      othIsArr = isArray(other),
      objTag = objIsArr ? arrayTag : getTag(object),
      othTag = othIsArr ? arrayTag : getTag(other);

  objTag = objTag == argsTag ? objectTag : objTag;
  othTag = othTag == argsTag ? objectTag : othTag;

  var objIsObj = objTag == objectTag,
      othIsObj = othTag == objectTag,
      isSameTag = objTag == othTag;

  if (isSameTag && isBuffer(object)) {
    if (!isBuffer(other)) {
      return false;
    }
    objIsArr = true;
    objIsObj = false;
  }
  if (isSameTag && !objIsObj) {
    stack || (stack = new Stack());
    return objIsArr || isTypedArray(object) ? equalArrays(object, other, bitmask, customizer, equalFunc, stack) : equalByTag(object, other, objTag, bitmask, customizer, equalFunc, stack);
  }
  if (!(bitmask & COMPARE_PARTIAL_FLAG)) {
    var objIsWrapped = objIsObj && hasOwnProperty.call(object, '__wrapped__'),
        othIsWrapped = othIsObj && hasOwnProperty.call(other, '__wrapped__');

    if (objIsWrapped || othIsWrapped) {
      var objUnwrapped = objIsWrapped ? object.value() : object,
          othUnwrapped = othIsWrapped ? other.value() : other;

      stack || (stack = new Stack());
      return equalFunc(objUnwrapped, othUnwrapped, bitmask, customizer, stack);
    }
  }
  if (!isSameTag) {
    return false;
  }
  stack || (stack = new Stack());
  return equalObjects(object, other, bitmask, customizer, equalFunc, stack);
}

module.exports = baseIsEqualDeep;

/***/ }),
/* 978 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_SetCache.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var MapCache = __webpack_require__(/*! ./_MapCache */ 242),
    setCacheAdd = __webpack_require__(/*! ./_setCacheAdd */ 979),
    setCacheHas = __webpack_require__(/*! ./_setCacheHas */ 980);

/**
 *
 * Creates an array cache object to store unique values.
 *
 * @private
 * @constructor
 * @param {Array} [values] The values to cache.
 */
function SetCache(values) {
    var index = -1,
        length = values == null ? 0 : values.length;

    this.__data__ = new MapCache();
    while (++index < length) {
        this.add(values[index]);
    }
}

// Add methods to `SetCache`.
SetCache.prototype.add = SetCache.prototype.push = setCacheAdd;
SetCache.prototype.has = setCacheHas;

module.exports = SetCache;

/***/ }),
/* 979 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setCacheAdd.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/** Used to stand-in for `undefined` hash values. */
var HASH_UNDEFINED = '__lodash_hash_undefined__';

/**
 * Adds `value` to the array cache.
 *
 * @private
 * @name add
 * @memberOf SetCache
 * @alias push
 * @param {*} value The value to cache.
 * @returns {Object} Returns the cache instance.
 */
function setCacheAdd(value) {
  this.__data__.set(value, HASH_UNDEFINED);
  return this;
}

module.exports = setCacheAdd;

/***/ }),
/* 980 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_setCacheHas.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Checks if `value` is in the array cache.
 *
 * @private
 * @name has
 * @memberOf SetCache
 * @param {*} value The value to search for.
 * @returns {number} Returns `true` if `value` is found, else `false`.
 */
function setCacheHas(value) {
  return this.__data__.has(value);
}

module.exports = setCacheHas;

/***/ }),
/* 981 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_arraySome.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * A specialized version of `_.some` for arrays without support for iteratee
 * shorthands.
 *
 * @private
 * @param {Array} [array] The array to iterate over.
 * @param {Function} predicate The function invoked per iteration.
 * @returns {boolean} Returns `true` if any element passes the predicate check,
 *  else `false`.
 */
function arraySome(array, predicate) {
  var index = -1,
      length = array == null ? 0 : array.length;

  while (++index < length) {
    if (predicate(array[index], index, array)) {
      return true;
    }
  }
  return false;
}

module.exports = arraySome;

/***/ }),
/* 982 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_cacheHas.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Checks if a `cache` value for `key` exists.
 *
 * @private
 * @param {Object} cache The cache to query.
 * @param {string} key The key of the entry to check.
 * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
 */
function cacheHas(cache, key) {
  return cache.has(key);
}

module.exports = cacheHas;

/***/ }),
/* 983 */
/*!*****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_equalByTag.js ***!
  \*****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93),
    Uint8Array = __webpack_require__(/*! ./_Uint8Array */ 464),
    eq = __webpack_require__(/*! ./eq */ 234),
    equalArrays = __webpack_require__(/*! ./_equalArrays */ 469),
    mapToArray = __webpack_require__(/*! ./_mapToArray */ 466),
    setToArray = __webpack_require__(/*! ./_setToArray */ 467);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1,
    COMPARE_UNORDERED_FLAG = 2;

/** `Object#toString` result references. */
var boolTag = '[object Boolean]',
    dateTag = '[object Date]',
    errorTag = '[object Error]',
    mapTag = '[object Map]',
    numberTag = '[object Number]',
    regexpTag = '[object RegExp]',
    setTag = '[object Set]',
    stringTag = '[object String]',
    symbolTag = '[object Symbol]';

var arrayBufferTag = '[object ArrayBuffer]',
    dataViewTag = '[object DataView]';

/** Used to convert symbols to primitives and strings. */
var symbolProto = _Symbol ? _Symbol.prototype : undefined,
    symbolValueOf = symbolProto ? symbolProto.valueOf : undefined;

/**
 * A specialized version of `baseIsEqualDeep` for comparing objects of
 * the same `toStringTag`.
 *
 * **Note:** This function only supports comparing values with tags of
 * `Boolean`, `Date`, `Error`, `Number`, `RegExp`, or `String`.
 *
 * @private
 * @param {Object} object The object to compare.
 * @param {Object} other The other object to compare.
 * @param {string} tag The `toStringTag` of the objects to compare.
 * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
 * @param {Function} customizer The function to customize comparisons.
 * @param {Function} equalFunc The function to determine equivalents of values.
 * @param {Object} stack Tracks traversed `object` and `other` objects.
 * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
 */
function equalByTag(object, other, tag, bitmask, customizer, equalFunc, stack) {
  switch (tag) {
    case dataViewTag:
      if (object.byteLength != other.byteLength || object.byteOffset != other.byteOffset) {
        return false;
      }
      object = object.buffer;
      other = other.buffer;

    case arrayBufferTag:
      if (object.byteLength != other.byteLength || !equalFunc(new Uint8Array(object), new Uint8Array(other))) {
        return false;
      }
      return true;

    case boolTag:
    case dateTag:
    case numberTag:
      // Coerce booleans to `1` or `0` and dates to milliseconds.
      // Invalid dates are coerced to `NaN`.
      return eq(+object, +other);

    case errorTag:
      return object.name == other.name && object.message == other.message;

    case regexpTag:
    case stringTag:
      // Coerce regexes to strings and treat strings, primitives and objects,
      // as equal. See http://www.ecma-international.org/ecma-262/7.0/#sec-regexp.prototype.tostring
      // for more details.
      return object == other + '';

    case mapTag:
      var convert = mapToArray;

    case setTag:
      var isPartial = bitmask & COMPARE_PARTIAL_FLAG;
      convert || (convert = setToArray);

      if (object.size != other.size && !isPartial) {
        return false;
      }
      // Assume cyclic values are equal.
      var stacked = stack.get(object);
      if (stacked) {
        return stacked == other;
      }
      bitmask |= COMPARE_UNORDERED_FLAG;

      // Recursively compare objects (susceptible to call stack limits).
      stack.set(object, other);
      var result = equalArrays(convert(object), convert(other), bitmask, customizer, equalFunc, stack);
      stack['delete'](object);
      return result;

    case symbolTag:
      if (symbolValueOf) {
        return symbolValueOf.call(object) == symbolValueOf.call(other);
      }
  }
  return false;
}

module.exports = equalByTag;

/***/ }),
/* 984 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_equalObjects.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var getAllKeys = __webpack_require__(/*! ./_getAllKeys */ 460);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1;

/** Used for built-in method references. */
var objectProto = Object.prototype;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/**
 * A specialized version of `baseIsEqualDeep` for objects with support for
 * partial deep comparisons.
 *
 * @private
 * @param {Object} object The object to compare.
 * @param {Object} other The other object to compare.
 * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
 * @param {Function} customizer The function to customize comparisons.
 * @param {Function} equalFunc The function to determine equivalents of values.
 * @param {Object} stack Tracks traversed `object` and `other` objects.
 * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
 */
function equalObjects(object, other, bitmask, customizer, equalFunc, stack) {
  var isPartial = bitmask & COMPARE_PARTIAL_FLAG,
      objProps = getAllKeys(object),
      objLength = objProps.length,
      othProps = getAllKeys(other),
      othLength = othProps.length;

  if (objLength != othLength && !isPartial) {
    return false;
  }
  var index = objLength;
  while (index--) {
    var key = objProps[index];
    if (!(isPartial ? key in other : hasOwnProperty.call(other, key))) {
      return false;
    }
  }
  // Assume cyclic values are equal.
  var stacked = stack.get(object);
  if (stacked && stack.get(other)) {
    return stacked == other;
  }
  var result = true;
  stack.set(object, other);
  stack.set(other, object);

  var skipCtor = isPartial;
  while (++index < objLength) {
    key = objProps[index];
    var objValue = object[key],
        othValue = other[key];

    if (customizer) {
      var compared = isPartial ? customizer(othValue, objValue, key, other, object, stack) : customizer(objValue, othValue, key, object, other, stack);
    }
    // Recursively compare objects (susceptible to call stack limits).
    if (!(compared === undefined ? objValue === othValue || equalFunc(objValue, othValue, bitmask, customizer, stack) : compared)) {
      result = false;
      break;
    }
    skipCtor || (skipCtor = key == 'constructor');
  }
  if (result && !skipCtor) {
    var objCtor = object.constructor,
        othCtor = other.constructor;

    // Non `Object` object instances with different constructors are not equal.
    if (objCtor != othCtor && 'constructor' in object && 'constructor' in other && !(typeof objCtor == 'function' && objCtor instanceof objCtor && typeof othCtor == 'function' && othCtor instanceof othCtor)) {
      result = false;
    }
  }
  stack['delete'](object);
  stack['delete'](other);
  return result;
}

module.exports = equalObjects;

/***/ }),
/* 985 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_getMatchData.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isStrictComparable = __webpack_require__(/*! ./_isStrictComparable */ 470),
    keys = __webpack_require__(/*! ./keys */ 144);

/**
 * Gets the property names, values, and compare flags of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @returns {Array} Returns the match data of `object`.
 */
function getMatchData(object) {
    var result = keys(object),
        length = result.length;

    while (length--) {
        var key = result[length],
            value = object[key];

        result[length] = [key, value, isStrictComparable(value)];
    }
    return result;
}

module.exports = getMatchData;

/***/ }),
/* 986 */
/*!**************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseMatchesProperty.js ***!
  \**************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseIsEqual = __webpack_require__(/*! ./_baseIsEqual */ 468),
    get = __webpack_require__(/*! ./get */ 987),
    hasIn = __webpack_require__(/*! ./hasIn */ 991),
    isKey = __webpack_require__(/*! ./_isKey */ 248),
    isStrictComparable = __webpack_require__(/*! ./_isStrictComparable */ 470),
    matchesStrictComparable = __webpack_require__(/*! ./_matchesStrictComparable */ 471),
    toKey = __webpack_require__(/*! ./_toKey */ 94);

/** Used to compose bitmasks for value comparisons. */
var COMPARE_PARTIAL_FLAG = 1,
    COMPARE_UNORDERED_FLAG = 2;

/**
 * The base implementation of `_.matchesProperty` which doesn't clone `srcValue`.
 *
 * @private
 * @param {string} path The path of the property to get.
 * @param {*} srcValue The value to match.
 * @returns {Function} Returns the new spec function.
 */
function baseMatchesProperty(path, srcValue) {
  if (isKey(path) && isStrictComparable(srcValue)) {
    return matchesStrictComparable(toKey(path), srcValue);
  }
  return function (object) {
    var objValue = get(object, path);
    return objValue === undefined && objValue === srcValue ? hasIn(object, path) : baseIsEqual(srcValue, objValue, COMPARE_PARTIAL_FLAG | COMPARE_UNORDERED_FLAG);
  };
}

module.exports = baseMatchesProperty;

/***/ }),
/* 987 */
/*!*********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/get.js ***!
  \*********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGet = __webpack_require__(/*! ./_baseGet */ 247);

/**
 * Gets the value at `path` of `object`. If the resolved value is
 * `undefined`, the `defaultValue` is returned in its place.
 *
 * @static
 * @memberOf _
 * @since 3.7.0
 * @category Object
 * @param {Object} object The object to query.
 * @param {Array|string} path The path of the property to get.
 * @param {*} [defaultValue] The value returned for `undefined` resolved values.
 * @returns {*} Returns the resolved value.
 * @example
 *
 * var object = { 'a': [{ 'b': { 'c': 3 } }] };
 *
 * _.get(object, 'a[0].b.c');
 * // => 3
 *
 * _.get(object, ['a', '0', 'b', 'c']);
 * // => 3
 *
 * _.get(object, 'a.b.c', 'default');
 * // => 'default'
 */
function get(object, path, defaultValue) {
  var result = object == null ? undefined : baseGet(object, path);
  return result === undefined ? defaultValue : result;
}

module.exports = get;

/***/ }),
/* 988 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_memoizeCapped.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var memoize = __webpack_require__(/*! ./memoize */ 989);

/** Used as the maximum memoize cache size. */
var MAX_MEMOIZE_SIZE = 500;

/**
 * A specialized version of `_.memoize` which clears the memoized function's
 * cache when it exceeds `MAX_MEMOIZE_SIZE`.
 *
 * @private
 * @param {Function} func The function to have its output memoized.
 * @returns {Function} Returns the new memoized function.
 */
function memoizeCapped(func) {
  var result = memoize(func, function (key) {
    if (cache.size === MAX_MEMOIZE_SIZE) {
      cache.clear();
    }
    return key;
  });

  var cache = result.cache;
  return result;
}

module.exports = memoizeCapped;

/***/ }),
/* 989 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/memoize.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var MapCache = __webpack_require__(/*! ./_MapCache */ 242);

/** Error message constants. */
var FUNC_ERROR_TEXT = 'Expected a function';

/**
 * Creates a function that memoizes the result of `func`. If `resolver` is
 * provided, it determines the cache key for storing the result based on the
 * arguments provided to the memoized function. By default, the first argument
 * provided to the memoized function is used as the map cache key. The `func`
 * is invoked with the `this` binding of the memoized function.
 *
 * **Note:** The cache is exposed as the `cache` property on the memoized
 * function. Its creation may be customized by replacing the `_.memoize.Cache`
 * constructor with one whose instances implement the
 * [`Map`](http://ecma-international.org/ecma-262/7.0/#sec-properties-of-the-map-prototype-object)
 * method interface of `clear`, `delete`, `get`, `has`, and `set`.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Function
 * @param {Function} func The function to have its output memoized.
 * @param {Function} [resolver] The function to resolve the cache key.
 * @returns {Function} Returns the new memoized function.
 * @example
 *
 * var object = { 'a': 1, 'b': 2 };
 * var other = { 'c': 3, 'd': 4 };
 *
 * var values = _.memoize(_.values);
 * values(object);
 * // => [1, 2]
 *
 * values(other);
 * // => [3, 4]
 *
 * object.a = 2;
 * values(object);
 * // => [1, 2]
 *
 * // Modify the result cache.
 * values.cache.set(object, ['a', 'b']);
 * values(object);
 * // => ['a', 'b']
 *
 * // Replace `_.memoize.Cache`.
 * _.memoize.Cache = WeakMap;
 */
function memoize(func, resolver) {
  if (typeof func != 'function' || resolver != null && typeof resolver != 'function') {
    throw new TypeError(FUNC_ERROR_TEXT);
  }
  var memoized = function memoized() {
    var args = arguments,
        key = resolver ? resolver.apply(this, args) : args[0],
        cache = memoized.cache;

    if (cache.has(key)) {
      return cache.get(key);
    }
    var result = func.apply(this, args);
    memoized.cache = cache.set(key, result) || cache;
    return result;
  };
  memoized.cache = new (memoize.Cache || MapCache)();
  return memoized;
}

// Expose `MapCache`.
memoize.Cache = MapCache;

module.exports = memoize;

/***/ }),
/* 990 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseToString.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93),
    arrayMap = __webpack_require__(/*! ./_arrayMap */ 249),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isSymbol = __webpack_require__(/*! ./isSymbol */ 112);

/** Used as references for various `Number` constants. */
var INFINITY = 1 / 0;

/** Used to convert symbols to primitives and strings. */
var symbolProto = _Symbol ? _Symbol.prototype : undefined,
    symbolToString = symbolProto ? symbolProto.toString : undefined;

/**
 * The base implementation of `_.toString` which doesn't convert nullish
 * values to empty strings.
 *
 * @private
 * @param {*} value The value to process.
 * @returns {string} Returns the string.
 */
function baseToString(value) {
  // Exit early for strings to avoid a performance hit in some environments.
  if (typeof value == 'string') {
    return value;
  }
  if (isArray(value)) {
    // Recursively convert values (susceptible to call stack limits).
    return arrayMap(value, baseToString) + '';
  }
  if (isSymbol(value)) {
    return symbolToString ? symbolToString.call(value) : '';
  }
  var result = value + '';
  return result == '0' && 1 / value == -INFINITY ? '-0' : result;
}

module.exports = baseToString;

/***/ }),
/* 991 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/hasIn.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseHasIn = __webpack_require__(/*! ./_baseHasIn */ 992),
    hasPath = __webpack_require__(/*! ./_hasPath */ 993);

/**
 * Checks if `path` is a direct or inherited property of `object`.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Object
 * @param {Object} object The object to query.
 * @param {Array|string} path The path to check.
 * @returns {boolean} Returns `true` if `path` exists, else `false`.
 * @example
 *
 * var object = _.create({ 'a': _.create({ 'b': 2 }) });
 *
 * _.hasIn(object, 'a');
 * // => true
 *
 * _.hasIn(object, 'a.b');
 * // => true
 *
 * _.hasIn(object, ['a', 'b']);
 * // => true
 *
 * _.hasIn(object, 'b');
 * // => false
 */
function hasIn(object, path) {
  return object != null && hasPath(object, path, baseHasIn);
}

module.exports = hasIn;

/***/ }),
/* 992 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseHasIn.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.hasIn` without support for deep paths.
 *
 * @private
 * @param {Object} [object] The object to query.
 * @param {Array|string} key The key to check.
 * @returns {boolean} Returns `true` if `key` exists, else `false`.
 */
function baseHasIn(object, key) {
  return object != null && key in Object(object);
}

module.exports = baseHasIn;

/***/ }),
/* 993 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_hasPath.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var castPath = __webpack_require__(/*! ./_castPath */ 149),
    isArguments = __webpack_require__(/*! ./isArguments */ 235),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isIndex = __webpack_require__(/*! ./_isIndex */ 232),
    isLength = __webpack_require__(/*! ./isLength */ 237),
    toKey = __webpack_require__(/*! ./_toKey */ 94);

/**
 * Checks if `path` exists on `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Array|string} path The path to check.
 * @param {Function} hasFunc The function to check properties.
 * @returns {boolean} Returns `true` if `path` exists, else `false`.
 */
function hasPath(object, path, hasFunc) {
  path = castPath(path, object);

  var index = -1,
      length = path.length,
      result = false;

  while (++index < length) {
    var key = toKey(path[index]);
    if (!(result = object != null && hasFunc(object, key))) {
      break;
    }
    object = object[key];
  }
  if (result || ++index != length) {
    return result;
  }
  length = object == null ? 0 : object.length;
  return !!length && isLength(length) && isIndex(key, length) && (isArray(object) || isArguments(object));
}

module.exports = hasPath;

/***/ }),
/* 994 */
/*!**************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/property.js ***!
  \**************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseProperty = __webpack_require__(/*! ./_baseProperty */ 995),
    basePropertyDeep = __webpack_require__(/*! ./_basePropertyDeep */ 996),
    isKey = __webpack_require__(/*! ./_isKey */ 248),
    toKey = __webpack_require__(/*! ./_toKey */ 94);

/**
 * Creates a function that returns the value at `path` of a given object.
 *
 * @static
 * @memberOf _
 * @since 2.4.0
 * @category Util
 * @param {Array|string} path The path of the property to get.
 * @returns {Function} Returns the new accessor function.
 * @example
 *
 * var objects = [
 *   { 'a': { 'b': 2 } },
 *   { 'a': { 'b': 1 } }
 * ];
 *
 * _.map(objects, _.property('a.b'));
 * // => [2, 1]
 *
 * _.map(_.sortBy(objects, _.property(['a', 'b'])), 'a.b');
 * // => [1, 2]
 */
function property(path) {
  return isKey(path) ? baseProperty(toKey(path)) : basePropertyDeep(path);
}

module.exports = property;

/***/ }),
/* 995 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseProperty.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.property` without support for deep paths.
 *
 * @private
 * @param {string} key The key of the property to get.
 * @returns {Function} Returns the new accessor function.
 */
function baseProperty(key) {
  return function (object) {
    return object == null ? undefined : object[key];
  };
}

module.exports = baseProperty;

/***/ }),
/* 996 */
/*!***********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_basePropertyDeep.js ***!
  \***********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGet = __webpack_require__(/*! ./_baseGet */ 247);

/**
 * A specialized version of `baseProperty` which supports deep paths.
 *
 * @private
 * @param {Array|string} path The path of the property to get.
 * @returns {Function} Returns the new accessor function.
 */
function basePropertyDeep(path) {
  return function (object) {
    return baseGet(object, path);
  };
}

module.exports = basePropertyDeep;

/***/ }),
/* 997 */
/*!***********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/rearg.js ***!
  \***********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var createWrap = __webpack_require__(/*! ./_createWrap */ 225),
    flatRest = __webpack_require__(/*! ./_flatRest */ 474);

/** Used to compose bitmasks for function metadata. */
var WRAP_REARG_FLAG = 256;

/**
 * Creates a function that invokes `func` with arguments arranged according
 * to the specified `indexes` where the argument value at the first index is
 * provided as the first argument, the argument value at the second index is
 * provided as the second argument, and so on.
 *
 * @static
 * @memberOf _
 * @since 3.0.0
 * @category Function
 * @param {Function} func The function to rearrange arguments for.
 * @param {...(number|number[])} indexes The arranged argument indexes.
 * @returns {Function} Returns the new function.
 * @example
 *
 * var rearged = _.rearg(function(a, b, c) {
 *   return [a, b, c];
 * }, [2, 0, 1]);
 *
 * rearged('b', 'c', 'a')
 * // => ['a', 'b', 'c']
 */
var rearg = flatRest(function (func, indexes) {
  return createWrap(func, WRAP_REARG_FLAG, undefined, undefined, undefined, indexes);
});

module.exports = rearg;

/***/ }),
/* 998 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/flatten.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseFlatten = __webpack_require__(/*! ./_baseFlatten */ 999);

/**
 * Flattens `array` a single level deep.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Array
 * @param {Array} array The array to flatten.
 * @returns {Array} Returns the new flattened array.
 * @example
 *
 * _.flatten([1, [2, [3, [4]], 5]]);
 * // => [1, 2, [3, [4]], 5]
 */
function flatten(array) {
  var length = array == null ? 0 : array.length;
  return length ? baseFlatten(array, 1) : [];
}

module.exports = flatten;

/***/ }),
/* 999 */
/*!******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseFlatten.js ***!
  \******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayPush = __webpack_require__(/*! ./_arrayPush */ 244),
    isFlattenable = __webpack_require__(/*! ./_isFlattenable */ 1000);

/**
 * The base implementation of `_.flatten` with support for restricting flattening.
 *
 * @private
 * @param {Array} array The array to flatten.
 * @param {number} depth The maximum recursion depth.
 * @param {boolean} [predicate=isFlattenable] The function invoked per iteration.
 * @param {boolean} [isStrict] Restrict to values that pass `predicate` checks.
 * @param {Array} [result=[]] The initial result value.
 * @returns {Array} Returns the new flattened array.
 */
function baseFlatten(array, depth, predicate, isStrict, result) {
  var index = -1,
      length = array.length;

  predicate || (predicate = isFlattenable);
  result || (result = []);

  while (++index < length) {
    var value = array[index];
    if (depth > 0 && predicate(value)) {
      if (depth > 1) {
        // Recursively flatten arrays (susceptible to call stack limits).
        baseFlatten(value, depth - 1, predicate, isStrict, result);
      } else {
        arrayPush(result, value);
      }
    } else if (!isStrict) {
      result[result.length] = value;
    }
  }
  return result;
}

module.exports = baseFlatten;

/***/ }),
/* 1000 */
/*!********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_isFlattenable.js ***!
  \********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _Symbol = __webpack_require__(/*! ./_Symbol */ 93),
    isArguments = __webpack_require__(/*! ./isArguments */ 235),
    isArray = __webpack_require__(/*! ./isArray */ 28);

/** Built-in value references. */
var spreadableSymbol = _Symbol ? _Symbol.isConcatSpreadable : undefined;

/**
 * Checks if `value` is a flattenable `arguments` object or array.
 *
 * @private
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is flattenable, else `false`.
 */
function isFlattenable(value) {
    return isArray(value) || isArguments(value) || !!(spreadableSymbol && value && value[spreadableSymbol]);
}

module.exports = isFlattenable;

/***/ }),
/* 1001 */
/*!***************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_overRest.js ***!
  \***************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var apply = __webpack_require__(/*! ./_apply */ 228);

/* Built-in method references for those with the same name as other `lodash` methods. */
var nativeMax = Math.max;

/**
 * A specialized version of `baseRest` which transforms the rest array.
 *
 * @private
 * @param {Function} func The function to apply a rest parameter to.
 * @param {number} [start=func.length-1] The start position of the rest parameter.
 * @param {Function} transform The rest array transform.
 * @returns {Function} Returns the new function.
 */
function overRest(func, start, transform) {
  start = nativeMax(start === undefined ? func.length - 1 : start, 0);
  return function () {
    var args = arguments,
        index = -1,
        length = nativeMax(args.length - start, 0),
        array = Array(length);

    while (++index < length) {
      array[index] = args[start + index];
    }
    index = -1;
    var otherArgs = Array(start + 1);
    while (++index < start) {
      otherArgs[index] = args[index];
    }
    otherArgs[start] = transform(array);
    return apply(func, this, otherArgs);
  };
}

module.exports = overRest;

/***/ }),
/* 1002 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/toPath.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayMap = __webpack_require__(/*! ./_arrayMap */ 249),
    copyArray = __webpack_require__(/*! ./_copyArray */ 143),
    isArray = __webpack_require__(/*! ./isArray */ 28),
    isSymbol = __webpack_require__(/*! ./isSymbol */ 112),
    stringToPath = __webpack_require__(/*! ./_stringToPath */ 472),
    toKey = __webpack_require__(/*! ./_toKey */ 94),
    toString = __webpack_require__(/*! ./toString */ 473);

/**
 * Converts `value` to a property path array.
 *
 * @static
 * @memberOf _
 * @since 4.0.0
 * @category Util
 * @param {*} value The value to convert.
 * @returns {Array} Returns the new property path array.
 * @example
 *
 * _.toPath('a.b.c');
 * // => ['a', 'b', 'c']
 *
 * _.toPath('a[0].b.c');
 * // => ['a', '0', 'b', 'c']
 */
function toPath(value) {
  if (isArray(value)) {
    return arrayMap(value, toKey);
  }
  return isSymbol(value) ? [value] : copyArray(stringToPath(toString(value)));
}

module.exports = toPath;

/***/ }),
/* 1003 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/omit.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var arrayMap = __webpack_require__(/*! ./_arrayMap */ 249),
    baseClone = __webpack_require__(/*! ./_baseClone */ 239),
    baseUnset = __webpack_require__(/*! ./_baseUnset */ 1004),
    castPath = __webpack_require__(/*! ./_castPath */ 149),
    copyObject = __webpack_require__(/*! ./_copyObject */ 113),
    customOmitClone = __webpack_require__(/*! ./_customOmitClone */ 1008),
    flatRest = __webpack_require__(/*! ./_flatRest */ 474),
    getAllKeysIn = __webpack_require__(/*! ./_getAllKeysIn */ 462);

/** Used to compose bitmasks for cloning. */
var CLONE_DEEP_FLAG = 1,
    CLONE_FLAT_FLAG = 2,
    CLONE_SYMBOLS_FLAG = 4;

/**
 * The opposite of `_.pick`; this method creates an object composed of the
 * own and inherited enumerable property paths of `object` that are not omitted.
 *
 * **Note:** This method is considerably slower than `_.pick`.
 *
 * @static
 * @since 0.1.0
 * @memberOf _
 * @category Object
 * @param {Object} object The source object.
 * @param {...(string|string[])} [paths] The property paths to omit.
 * @returns {Object} Returns the new object.
 * @example
 *
 * var object = { 'a': 1, 'b': '2', 'c': 3 };
 *
 * _.omit(object, ['a', 'c']);
 * // => { 'b': '2' }
 */
var omit = flatRest(function (object, paths) {
  var result = {};
  if (object == null) {
    return result;
  }
  var isDeep = false;
  paths = arrayMap(paths, function (path) {
    path = castPath(path, object);
    isDeep || (isDeep = path.length > 1);
    return path;
  });
  copyObject(object, getAllKeysIn(object), result);
  if (isDeep) {
    result = baseClone(result, CLONE_DEEP_FLAG | CLONE_FLAT_FLAG | CLONE_SYMBOLS_FLAG, customOmitClone);
  }
  var length = paths.length;
  while (length--) {
    baseUnset(result, paths[length]);
  }
  return result;
});

module.exports = omit;

/***/ }),
/* 1004 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseUnset.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var castPath = __webpack_require__(/*! ./_castPath */ 149),
    last = __webpack_require__(/*! ./last */ 1005),
    parent = __webpack_require__(/*! ./_parent */ 1006),
    toKey = __webpack_require__(/*! ./_toKey */ 94);

/**
 * The base implementation of `_.unset`.
 *
 * @private
 * @param {Object} object The object to modify.
 * @param {Array|string} path The property path to unset.
 * @returns {boolean} Returns `true` if the property is deleted, else `false`.
 */
function baseUnset(object, path) {
  path = castPath(path, object);
  object = parent(object, path);
  return object == null || delete object[toKey(last(path))];
}

module.exports = baseUnset;

/***/ }),
/* 1005 */
/*!**********************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/last.js ***!
  \**********************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Gets the last element of `array`.
 *
 * @static
 * @memberOf _
 * @since 0.1.0
 * @category Array
 * @param {Array} array The array to query.
 * @returns {*} Returns the last element of `array`.
 * @example
 *
 * _.last([1, 2, 3]);
 * // => 3
 */
function last(array) {
  var length = array == null ? 0 : array.length;
  return length ? array[length - 1] : undefined;
}

module.exports = last;

/***/ }),
/* 1006 */
/*!*************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_parent.js ***!
  \*************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGet = __webpack_require__(/*! ./_baseGet */ 247),
    baseSlice = __webpack_require__(/*! ./_baseSlice */ 1007);

/**
 * Gets the parent value at `path` of `object`.
 *
 * @private
 * @param {Object} object The object to query.
 * @param {Array} path The path to get the parent value of.
 * @returns {*} Returns the parent value.
 */
function parent(object, path) {
  return path.length < 2 ? object : baseGet(object, baseSlice(path, 0, -1));
}

module.exports = parent;

/***/ }),
/* 1007 */
/*!****************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_baseSlice.js ***!
  \****************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * The base implementation of `_.slice` without an iteratee call guard.
 *
 * @private
 * @param {Array} array The array to slice.
 * @param {number} [start=0] The start position.
 * @param {number} [end=array.length] The end position.
 * @returns {Array} Returns the slice of `array`.
 */
function baseSlice(array, start, end) {
  var index = -1,
      length = array.length;

  if (start < 0) {
    start = -start > length ? 0 : length + start;
  }
  end = end > length ? length : end;
  if (end < 0) {
    end += length;
  }
  length = start > end ? 0 : end - start >>> 0;
  start >>>= 0;

  var result = Array(length);
  while (++index < length) {
    result[index] = array[index + start];
  }
  return result;
}

module.exports = baseSlice;

/***/ }),
/* 1008 */
/*!**********************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/_customOmitClone.js ***!
  \**********************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isPlainObject = __webpack_require__(/*! ./isPlainObject */ 1009);

/**
 * Used by `_.omit` to customize its `_.cloneDeep` use to only clone plain
 * objects.
 *
 * @private
 * @param {*} value The value to inspect.
 * @param {string} key The key of the property to inspect.
 * @returns {*} Returns the uncloned value or `undefined` to defer cloning to `_.cloneDeep`.
 */
function customOmitClone(value) {
  return isPlainObject(value) ? undefined : value;
}

module.exports = customOmitClone;

/***/ }),
/* 1009 */
/*!*******************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/lodash/isPlainObject.js ***!
  \*******************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ 92),
    getPrototype = __webpack_require__(/*! ./_getPrototype */ 245),
    isObjectLike = __webpack_require__(/*! ./isObjectLike */ 69);

/** `Object#toString` result references. */
var objectTag = '[object Object]';

/** Used for built-in method references. */
var funcProto = Function.prototype,
    objectProto = Object.prototype;

/** Used to resolve the decompiled source of functions. */
var funcToString = funcProto.toString;

/** Used to check objects for own properties. */
var hasOwnProperty = objectProto.hasOwnProperty;

/** Used to infer the `Object` constructor. */
var objectCtorString = funcToString.call(Object);

/**
 * Checks if `value` is a plain object, that is, an object created by the
 * `Object` constructor or one with a `[[Prototype]]` of `null`.
 *
 * @static
 * @memberOf _
 * @since 0.8.0
 * @category Lang
 * @param {*} value The value to check.
 * @returns {boolean} Returns `true` if `value` is a plain object, else `false`.
 * @example
 *
 * function Foo() {
 *   this.a = 1;
 * }
 *
 * _.isPlainObject(new Foo);
 * // => false
 *
 * _.isPlainObject([1, 2, 3]);
 * // => false
 *
 * _.isPlainObject({ 'x': 0, 'y': 0 });
 * // => true
 *
 * _.isPlainObject(Object.create(null));
 * // => true
 */
function isPlainObject(value) {
  if (!isObjectLike(value) || baseGetTag(value) != objectTag) {
    return false;
  }
  var proto = getPrototype(value);
  if (proto === null) {
    return true;
  }
  var Ctor = hasOwnProperty.call(proto, 'constructor') && proto.constructor;
  return typeof Ctor == 'function' && Ctor instanceof Ctor && funcToString.call(Ctor) == objectCtorString;
}

module.exports = isPlainObject;

/***/ }),
/* 1010 */
/*!************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/urijs/src/URI.js ***!
  \************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 *
 * Version: 1.18.10
 *
 * Author: Rodney Rehm
 * Web: http://medialize.github.io/URI.js/
 *
 * Licensed under
 *   MIT License http://www.opensource.org/licenses/mit-license
 *
 */
(function (root, factory) {
  'use strict';
  // https://github.com/umdjs/umd/blob/master/returnExports.js

  if (( false ? 'undefined' : _typeof(module)) === 'object' && module.exports) {
    // Node
    module.exports = factory(__webpack_require__(/*! ./punycode */ 475), __webpack_require__(/*! ./IPv6 */ 476), __webpack_require__(/*! ./SecondLevelDomains */ 477));
  } else if (true) {
    // AMD. Register as an anonymous module.
    !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(/*! ./punycode */ 475), __webpack_require__(/*! ./IPv6 */ 476), __webpack_require__(/*! ./SecondLevelDomains */ 477)], __WEBPACK_AMD_DEFINE_FACTORY__ = (factory),
				__WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ?
				(__WEBPACK_AMD_DEFINE_FACTORY__.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__)) : __WEBPACK_AMD_DEFINE_FACTORY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
  } else {
    // Browser globals (root is window)
    root.URI = factory(root.punycode, root.IPv6, root.SecondLevelDomains, root);
  }
})(undefined, function (punycode, IPv6, SLD, root) {
  'use strict';
  /*global location, escape, unescape */
  // FIXME: v2.0.0 renamce non-camelCase properties to uppercase
  /*jshint camelcase: false */

  // save current URI variable, if any

  var _URI = root && root.URI;

  function URI(url, base) {
    var _urlSupplied = arguments.length >= 1;
    var _baseSupplied = arguments.length >= 2;

    // Allow instantiation without the 'new' keyword
    if (!(this instanceof URI)) {
      if (_urlSupplied) {
        if (_baseSupplied) {
          return new URI(url, base);
        }

        return new URI(url);
      }

      return new URI();
    }

    if (url === undefined) {
      if (_urlSupplied) {
        throw new TypeError('undefined is not a valid argument for URI');
      }

      if (typeof location !== 'undefined') {
        url = location.href + '';
      } else {
        url = '';
      }
    }

    if (url === null) {
      if (_urlSupplied) {
        throw new TypeError('null is not a valid argument for URI');
      }
    }

    this.href(url);

    // resolve to base according to http://dvcs.w3.org/hg/url/raw-file/tip/Overview.html#constructor
    if (base !== undefined) {
      return this.absoluteTo(base);
    }

    return this;
  }

  URI.version = '1.18.10';

  var p = URI.prototype;
  var hasOwn = Object.prototype.hasOwnProperty;

  function escapeRegEx(string) {
    // https://github.com/medialize/URI.js/commit/85ac21783c11f8ccab06106dba9735a31a86924d#commitcomment-821963
    return string.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
  }

  function getType(value) {
    // IE8 doesn't return [Object Undefined] but [Object Object] for undefined value
    if (value === undefined) {
      return 'Undefined';
    }

    return String(Object.prototype.toString.call(value)).slice(8, -1);
  }

  function isArray(obj) {
    return getType(obj) === 'Array';
  }

  function filterArrayValues(data, value) {
    var lookup = {};
    var i, length;

    if (getType(value) === 'RegExp') {
      lookup = null;
    } else if (isArray(value)) {
      for (i = 0, length = value.length; i < length; i++) {
        lookup[value[i]] = true;
      }
    } else {
      lookup[value] = true;
    }

    for (i = 0, length = data.length; i < length; i++) {
      /*jshint laxbreak: true */
      var _match = lookup && lookup[data[i]] !== undefined || !lookup && value.test(data[i]);
      /*jshint laxbreak: false */
      if (_match) {
        data.splice(i, 1);
        length--;
        i--;
      }
    }

    return data;
  }

  function arrayContains(list, value) {
    var i, length;

    // value may be string, number, array, regexp
    if (isArray(value)) {
      // Note: this can be optimized to O(n) (instead of current O(m * n))
      for (i = 0, length = value.length; i < length; i++) {
        if (!arrayContains(list, value[i])) {
          return false;
        }
      }

      return true;
    }

    var _type = getType(value);
    for (i = 0, length = list.length; i < length; i++) {
      if (_type === 'RegExp') {
        if (typeof list[i] === 'string' && list[i].match(value)) {
          return true;
        }
      } else if (list[i] === value) {
        return true;
      }
    }

    return false;
  }

  function arraysEqual(one, two) {
    if (!isArray(one) || !isArray(two)) {
      return false;
    }

    // arrays can't be equal if they have different amount of content
    if (one.length !== two.length) {
      return false;
    }

    one.sort();
    two.sort();

    for (var i = 0, l = one.length; i < l; i++) {
      if (one[i] !== two[i]) {
        return false;
      }
    }

    return true;
  }

  function trimSlashes(text) {
    var trim_expression = /^\/+|\/+$/g;
    return text.replace(trim_expression, '');
  }

  URI._parts = function () {
    return {
      protocol: null,
      username: null,
      password: null,
      hostname: null,
      urn: null,
      port: null,
      path: null,
      query: null,
      fragment: null,
      // state
      duplicateQueryParameters: URI.duplicateQueryParameters,
      escapeQuerySpace: URI.escapeQuerySpace
    };
  };
  // state: allow duplicate query parameters (a=1&a=1)
  URI.duplicateQueryParameters = false;
  // state: replaces + with %20 (space in query strings)
  URI.escapeQuerySpace = true;
  // static properties
  URI.protocol_expression = /^[a-z][a-z0-9.+-]*$/i;
  URI.idn_expression = /[^a-z0-9\.-]/i;
  URI.punycode_expression = /(xn--)/i;
  // well, 333.444.555.666 matches, but it sure ain't no IPv4 - do we care?
  URI.ip4_expression = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
  // credits to Rich Brown
  // source: http://forums.intermapper.com/viewtopic.php?p=1096#1096
  // specification: http://www.ietf.org/rfc/rfc4291.txt
  URI.ip6_expression = /^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/;
  // expression used is "gruber revised" (@gruber v2) determined to be the
  // best solution in a regex-golf we did a couple of ages ago at
  // * http://mathiasbynens.be/demo/url-regex
  // * http://rodneyrehm.de/t/url-regex.html
  URI.find_uri_expression = /\b((?:[a-z][\w-]+:(?:\/{1,3}|[a-z0-9%])|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'".,<>?«»“”‘’]))/ig;
  URI.findUri = {
    // valid "scheme://" or "www."
    start: /\b(?:([a-z][a-z0-9.+-]*:\/\/)|www\.)/gi,
    // everything up to the next whitespace
    end: /[\s\r\n]|$/,
    // trim trailing punctuation captured by end RegExp
    trim: /[`!()\[\]{};:'".,<>?«»“”„‘’]+$/,
    // balanced parens inclusion (), [], {}, <>
    parens: /(\([^\)]*\)|\[[^\]]*\]|\{[^}]*\}|<[^>]*>)/g
  };
  // http://www.iana.org/assignments/uri-schemes.html
  // http://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers#Well-known_ports
  URI.defaultPorts = {
    http: '80',
    https: '443',
    ftp: '21',
    gopher: '70',
    ws: '80',
    wss: '443'
  };
  // allowed hostname characters according to RFC 3986
  // ALPHA DIGIT "-" "." "_" "~" "!" "$" "&" "'" "(" ")" "*" "+" "," ";" "=" %encoded
  // I've never seen a (non-IDN) hostname other than: ALPHA DIGIT . -
  URI.invalid_hostname_characters = /[^a-zA-Z0-9\.-]/;
  // map DOM Elements to their URI attribute
  URI.domAttributes = {
    'a': 'href',
    'blockquote': 'cite',
    'link': 'href',
    'base': 'href',
    'script': 'src',
    'form': 'action',
    'img': 'src',
    'area': 'href',
    'iframe': 'src',
    'embed': 'src',
    'source': 'src',
    'track': 'src',
    'input': 'src', // but only if type="image"
    'audio': 'src',
    'video': 'src'
  };
  URI.getDomAttribute = function (node) {
    if (!node || !node.nodeName) {
      return undefined;
    }

    var nodeName = node.nodeName.toLowerCase();
    // <input> should only expose src for type="image"
    if (nodeName === 'input' && node.type !== 'image') {
      return undefined;
    }

    return URI.domAttributes[nodeName];
  };

  function escapeForDumbFirefox36(value) {
    // https://github.com/medialize/URI.js/issues/91
    return escape(value);
  }

  // encoding / decoding according to RFC3986
  function strictEncodeURIComponent(string) {
    // see https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/encodeURIComponent
    return encodeURIComponent(string).replace(/[!'()*]/g, escapeForDumbFirefox36).replace(/\*/g, '%2A');
  }
  URI.encode = strictEncodeURIComponent;
  URI.decode = decodeURIComponent;
  URI.iso8859 = function () {
    URI.encode = escape;
    URI.decode = unescape;
  };
  URI.unicode = function () {
    URI.encode = strictEncodeURIComponent;
    URI.decode = decodeURIComponent;
  };
  URI.characters = {
    pathname: {
      encode: {
        // RFC3986 2.1: For consistency, URI producers and normalizers should
        // use uppercase hexadecimal digits for all percent-encodings.
        expression: /%(24|26|2B|2C|3B|3D|3A|40)/ig,
        map: {
          // -._~!'()*
          '%24': '$',
          '%26': '&',
          '%2B': '+',
          '%2C': ',',
          '%3B': ';',
          '%3D': '=',
          '%3A': ':',
          '%40': '@'
        }
      },
      decode: {
        expression: /[\/\?#]/g,
        map: {
          '/': '%2F',
          '?': '%3F',
          '#': '%23'
        }
      }
    },
    reserved: {
      encode: {
        // RFC3986 2.1: For consistency, URI producers and normalizers should
        // use uppercase hexadecimal digits for all percent-encodings.
        expression: /%(21|23|24|26|27|28|29|2A|2B|2C|2F|3A|3B|3D|3F|40|5B|5D)/ig,
        map: {
          // gen-delims
          '%3A': ':',
          '%2F': '/',
          '%3F': '?',
          '%23': '#',
          '%5B': '[',
          '%5D': ']',
          '%40': '@',
          // sub-delims
          '%21': '!',
          '%24': '$',
          '%26': '&',
          '%27': '\'',
          '%28': '(',
          '%29': ')',
          '%2A': '*',
          '%2B': '+',
          '%2C': ',',
          '%3B': ';',
          '%3D': '='
        }
      }
    },
    urnpath: {
      // The characters under `encode` are the characters called out by RFC 2141 as being acceptable
      // for usage in a URN. RFC2141 also calls out "-", ".", and "_" as acceptable characters, but
      // these aren't encoded by encodeURIComponent, so we don't have to call them out here. Also
      // note that the colon character is not featured in the encoding map; this is because URI.js
      // gives the colons in URNs semantic meaning as the delimiters of path segements, and so it
      // should not appear unencoded in a segment itself.
      // See also the note above about RFC3986 and capitalalized hex digits.
      encode: {
        expression: /%(21|24|27|28|29|2A|2B|2C|3B|3D|40)/ig,
        map: {
          '%21': '!',
          '%24': '$',
          '%27': '\'',
          '%28': '(',
          '%29': ')',
          '%2A': '*',
          '%2B': '+',
          '%2C': ',',
          '%3B': ';',
          '%3D': '=',
          '%40': '@'
        }
      },
      // These characters are the characters called out by RFC2141 as "reserved" characters that
      // should never appear in a URN, plus the colon character (see note above).
      decode: {
        expression: /[\/\?#:]/g,
        map: {
          '/': '%2F',
          '?': '%3F',
          '#': '%23',
          ':': '%3A'
        }
      }
    }
  };
  URI.encodeQuery = function (string, escapeQuerySpace) {
    var escaped = URI.encode(string + '');
    if (escapeQuerySpace === undefined) {
      escapeQuerySpace = URI.escapeQuerySpace;
    }

    return escapeQuerySpace ? escaped.replace(/%20/g, '+') : escaped;
  };
  URI.decodeQuery = function (string, escapeQuerySpace) {
    string += '';
    if (escapeQuerySpace === undefined) {
      escapeQuerySpace = URI.escapeQuerySpace;
    }

    try {
      return URI.decode(escapeQuerySpace ? string.replace(/\+/g, '%20') : string);
    } catch (e) {
      // we're not going to mess with weird encodings,
      // give up and return the undecoded original string
      // see https://github.com/medialize/URI.js/issues/87
      // see https://github.com/medialize/URI.js/issues/92
      return string;
    }
  };
  // generate encode/decode path functions
  var _parts = { 'encode': 'encode', 'decode': 'decode' };
  var _part;
  var generateAccessor = function generateAccessor(_group, _part) {
    return function (string) {
      try {
        return URI[_part](string + '').replace(URI.characters[_group][_part].expression, function (c) {
          return URI.characters[_group][_part].map[c];
        });
      } catch (e) {
        // we're not going to mess with weird encodings,
        // give up and return the undecoded original string
        // see https://github.com/medialize/URI.js/issues/87
        // see https://github.com/medialize/URI.js/issues/92
        return string;
      }
    };
  };

  for (_part in _parts) {
    URI[_part + 'PathSegment'] = generateAccessor('pathname', _parts[_part]);
    URI[_part + 'UrnPathSegment'] = generateAccessor('urnpath', _parts[_part]);
  }

  var generateSegmentedPathFunction = function generateSegmentedPathFunction(_sep, _codingFuncName, _innerCodingFuncName) {
    return function (string) {
      // Why pass in names of functions, rather than the function objects themselves? The
      // definitions of some functions (but in particular, URI.decode) will occasionally change due
      // to URI.js having ISO8859 and Unicode modes. Passing in the name and getting it will ensure
      // that the functions we use here are "fresh".
      var actualCodingFunc;
      if (!_innerCodingFuncName) {
        actualCodingFunc = URI[_codingFuncName];
      } else {
        actualCodingFunc = function actualCodingFunc(string) {
          return URI[_codingFuncName](URI[_innerCodingFuncName](string));
        };
      }

      var segments = (string + '').split(_sep);

      for (var i = 0, length = segments.length; i < length; i++) {
        segments[i] = actualCodingFunc(segments[i]);
      }

      return segments.join(_sep);
    };
  };

  // This takes place outside the above loop because we don't want, e.g., encodeUrnPath functions.
  URI.decodePath = generateSegmentedPathFunction('/', 'decodePathSegment');
  URI.decodeUrnPath = generateSegmentedPathFunction(':', 'decodeUrnPathSegment');
  URI.recodePath = generateSegmentedPathFunction('/', 'encodePathSegment', 'decode');
  URI.recodeUrnPath = generateSegmentedPathFunction(':', 'encodeUrnPathSegment', 'decode');

  URI.encodeReserved = generateAccessor('reserved', 'encode');

  URI.parse = function (string, parts) {
    var pos;
    if (!parts) {
      parts = {};
    }
    // [protocol"://"[username[":"password]"@"]hostname[":"port]"/"?][path]["?"querystring]["#"fragment]

    // extract fragment
    pos = string.indexOf('#');
    if (pos > -1) {
      // escaping?
      parts.fragment = string.substring(pos + 1) || null;
      string = string.substring(0, pos);
    }

    // extract query
    pos = string.indexOf('?');
    if (pos > -1) {
      // escaping?
      parts.query = string.substring(pos + 1) || null;
      string = string.substring(0, pos);
    }

    // extract protocol
    if (string.substring(0, 2) === '//') {
      // relative-scheme
      parts.protocol = null;
      string = string.substring(2);
      // extract "user:pass@host:port"
      string = URI.parseAuthority(string, parts);
    } else {
      pos = string.indexOf(':');
      if (pos > -1) {
        parts.protocol = string.substring(0, pos) || null;
        if (parts.protocol && !parts.protocol.match(URI.protocol_expression)) {
          // : may be within the path
          parts.protocol = undefined;
        } else if (string.substring(pos + 1, pos + 3) === '//') {
          string = string.substring(pos + 3);

          // extract "user:pass@host:port"
          string = URI.parseAuthority(string, parts);
        } else {
          string = string.substring(pos + 1);
          parts.urn = true;
        }
      }
    }

    // what's left must be the path
    parts.path = string;

    // and we're done
    return parts;
  };
  URI.parseHost = function (string, parts) {
    // Copy chrome, IE, opera backslash-handling behavior.
    // Back slashes before the query string get converted to forward slashes
    // See: https://github.com/joyent/node/blob/386fd24f49b0e9d1a8a076592a404168faeecc34/lib/url.js#L115-L124
    // See: https://code.google.com/p/chromium/issues/detail?id=25916
    // https://github.com/medialize/URI.js/pull/233
    string = string.replace(/\\/g, '/');

    // extract host:port
    var pos = string.indexOf('/');
    var bracketPos;
    var t;

    if (pos === -1) {
      pos = string.length;
    }

    if (string.charAt(0) === '[') {
      // IPv6 host - http://tools.ietf.org/html/draft-ietf-6man-text-addr-representation-04#section-6
      // I claim most client software breaks on IPv6 anyways. To simplify things, URI only accepts
      // IPv6+port in the format [2001:db8::1]:80 (for the time being)
      bracketPos = string.indexOf(']');
      parts.hostname = string.substring(1, bracketPos) || null;
      parts.port = string.substring(bracketPos + 2, pos) || null;
      if (parts.port === '/') {
        parts.port = null;
      }
    } else {
      var firstColon = string.indexOf(':');
      var firstSlash = string.indexOf('/');
      var nextColon = string.indexOf(':', firstColon + 1);
      if (nextColon !== -1 && (firstSlash === -1 || nextColon < firstSlash)) {
        // IPv6 host contains multiple colons - but no port
        // this notation is actually not allowed by RFC 3986, but we're a liberal parser
        parts.hostname = string.substring(0, pos) || null;
        parts.port = null;
      } else {
        t = string.substring(0, pos).split(':');
        parts.hostname = t[0] || null;
        parts.port = t[1] || null;
      }
    }

    if (parts.hostname && string.substring(pos).charAt(0) !== '/') {
      pos++;
      string = '/' + string;
    }

    return string.substring(pos) || '/';
  };
  URI.parseAuthority = function (string, parts) {
    string = URI.parseUserinfo(string, parts);
    return URI.parseHost(string, parts);
  };
  URI.parseUserinfo = function (string, parts) {
    // extract username:password
    var firstSlash = string.indexOf('/');
    var pos = string.lastIndexOf('@', firstSlash > -1 ? firstSlash : string.length - 1);
    var t;

    // authority@ must come before /path
    if (pos > -1 && (firstSlash === -1 || pos < firstSlash)) {
      t = string.substring(0, pos).split(':');
      parts.username = t[0] ? URI.decode(t[0]) : null;
      t.shift();
      parts.password = t[0] ? URI.decode(t.join(':')) : null;
      string = string.substring(pos + 1);
    } else {
      parts.username = null;
      parts.password = null;
    }

    return string;
  };
  URI.parseQuery = function (string, escapeQuerySpace) {
    if (!string) {
      return {};
    }

    // throw out the funky business - "?"[name"="value"&"]+
    string = string.replace(/&+/g, '&').replace(/^\?*&*|&+$/g, '');

    if (!string) {
      return {};
    }

    var items = {};
    var splits = string.split('&');
    var length = splits.length;
    var v, name, value;

    for (var i = 0; i < length; i++) {
      v = splits[i].split('=');
      name = URI.decodeQuery(v.shift(), escapeQuerySpace);
      // no "=" is null according to http://dvcs.w3.org/hg/url/raw-file/tip/Overview.html#collect-url-parameters
      value = v.length ? URI.decodeQuery(v.join('='), escapeQuerySpace) : null;

      if (hasOwn.call(items, name)) {
        if (typeof items[name] === 'string' || items[name] === null) {
          items[name] = [items[name]];
        }

        items[name].push(value);
      } else {
        items[name] = value;
      }
    }

    return items;
  };

  URI.build = function (parts) {
    var t = '';

    if (parts.protocol) {
      t += parts.protocol + ':';
    }

    if (!parts.urn && (t || parts.hostname)) {
      t += '//';
    }

    t += URI.buildAuthority(parts) || '';

    if (typeof parts.path === 'string') {
      if (parts.path.charAt(0) !== '/' && typeof parts.hostname === 'string') {
        t += '/';
      }

      t += parts.path;
    }

    if (typeof parts.query === 'string' && parts.query) {
      t += '?' + parts.query;
    }

    if (typeof parts.fragment === 'string' && parts.fragment) {
      t += '#' + parts.fragment;
    }
    return t;
  };
  URI.buildHost = function (parts) {
    var t = '';

    if (!parts.hostname) {
      return '';
    } else if (URI.ip6_expression.test(parts.hostname)) {
      t += '[' + parts.hostname + ']';
    } else {
      t += parts.hostname;
    }

    if (parts.port) {
      t += ':' + parts.port;
    }

    return t;
  };
  URI.buildAuthority = function (parts) {
    return URI.buildUserinfo(parts) + URI.buildHost(parts);
  };
  URI.buildUserinfo = function (parts) {
    var t = '';

    if (parts.username) {
      t += URI.encode(parts.username);
    }

    if (parts.password) {
      t += ':' + URI.encode(parts.password);
    }

    if (t) {
      t += '@';
    }

    return t;
  };
  URI.buildQuery = function (data, duplicateQueryParameters, escapeQuerySpace) {
    // according to http://tools.ietf.org/html/rfc3986 or http://labs.apache.org/webarch/uri/rfc/rfc3986.html
    // being »-._~!$&'()*+,;=:@/?« %HEX and alnum are allowed
    // the RFC explicitly states ?/foo being a valid use case, no mention of parameter syntax!
    // URI.js treats the query string as being application/x-www-form-urlencoded
    // see http://www.w3.org/TR/REC-html40/interact/forms.html#form-content-type

    var t = '';
    var unique, key, i, length;
    for (key in data) {
      if (hasOwn.call(data, key) && key) {
        if (isArray(data[key])) {
          unique = {};
          for (i = 0, length = data[key].length; i < length; i++) {
            if (data[key][i] !== undefined && unique[data[key][i] + ''] === undefined) {
              t += '&' + URI.buildQueryParameter(key, data[key][i], escapeQuerySpace);
              if (duplicateQueryParameters !== true) {
                unique[data[key][i] + ''] = true;
              }
            }
          }
        } else if (data[key] !== undefined) {
          t += '&' + URI.buildQueryParameter(key, data[key], escapeQuerySpace);
        }
      }
    }

    return t.substring(1);
  };
  URI.buildQueryParameter = function (name, value, escapeQuerySpace) {
    // http://www.w3.org/TR/REC-html40/interact/forms.html#form-content-type -- application/x-www-form-urlencoded
    // don't append "=" for null values, according to http://dvcs.w3.org/hg/url/raw-file/tip/Overview.html#url-parameter-serialization
    return URI.encodeQuery(name, escapeQuerySpace) + (value !== null ? '=' + URI.encodeQuery(value, escapeQuerySpace) : '');
  };

  URI.addQuery = function (data, name, value) {
    if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
      for (var key in name) {
        if (hasOwn.call(name, key)) {
          URI.addQuery(data, key, name[key]);
        }
      }
    } else if (typeof name === 'string') {
      if (data[name] === undefined) {
        data[name] = value;
        return;
      } else if (typeof data[name] === 'string') {
        data[name] = [data[name]];
      }

      if (!isArray(value)) {
        value = [value];
      }

      data[name] = (data[name] || []).concat(value);
    } else {
      throw new TypeError('URI.addQuery() accepts an object, string as the name parameter');
    }
  };
  URI.removeQuery = function (data, name, value) {
    var i, length, key;

    if (isArray(name)) {
      for (i = 0, length = name.length; i < length; i++) {
        data[name[i]] = undefined;
      }
    } else if (getType(name) === 'RegExp') {
      for (key in data) {
        if (name.test(key)) {
          data[key] = undefined;
        }
      }
    } else if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
      for (key in name) {
        if (hasOwn.call(name, key)) {
          URI.removeQuery(data, key, name[key]);
        }
      }
    } else if (typeof name === 'string') {
      if (value !== undefined) {
        if (getType(value) === 'RegExp') {
          if (!isArray(data[name]) && value.test(data[name])) {
            data[name] = undefined;
          } else {
            data[name] = filterArrayValues(data[name], value);
          }
        } else if (data[name] === String(value) && (!isArray(value) || value.length === 1)) {
          data[name] = undefined;
        } else if (isArray(data[name])) {
          data[name] = filterArrayValues(data[name], value);
        }
      } else {
        data[name] = undefined;
      }
    } else {
      throw new TypeError('URI.removeQuery() accepts an object, string, RegExp as the first parameter');
    }
  };
  URI.hasQuery = function (data, name, value, withinArray) {
    switch (getType(name)) {
      case 'String':
        // Nothing to do here
        break;

      case 'RegExp':
        for (var key in data) {
          if (hasOwn.call(data, key)) {
            if (name.test(key) && (value === undefined || URI.hasQuery(data, key, value))) {
              return true;
            }
          }
        }

        return false;

      case 'Object':
        for (var _key in name) {
          if (hasOwn.call(name, _key)) {
            if (!URI.hasQuery(data, _key, name[_key])) {
              return false;
            }
          }
        }

        return true;

      default:
        throw new TypeError('URI.hasQuery() accepts a string, regular expression or object as the name parameter');
    }

    switch (getType(value)) {
      case 'Undefined':
        // true if exists (but may be empty)
        return name in data; // data[name] !== undefined;

      case 'Boolean':
        // true if exists and non-empty
        var _booly = Boolean(isArray(data[name]) ? data[name].length : data[name]);
        return value === _booly;

      case 'Function':
        // allow complex comparison
        return !!value(data[name], name, data);

      case 'Array':
        if (!isArray(data[name])) {
          return false;
        }

        var op = withinArray ? arrayContains : arraysEqual;
        return op(data[name], value);

      case 'RegExp':
        if (!isArray(data[name])) {
          return Boolean(data[name] && data[name].match(value));
        }

        if (!withinArray) {
          return false;
        }

        return arrayContains(data[name], value);

      case 'Number':
        value = String(value);
      /* falls through */
      case 'String':
        if (!isArray(data[name])) {
          return data[name] === value;
        }

        if (!withinArray) {
          return false;
        }

        return arrayContains(data[name], value);

      default:
        throw new TypeError('URI.hasQuery() accepts undefined, boolean, string, number, RegExp, Function as the value parameter');
    }
  };

  URI.joinPaths = function () {
    var input = [];
    var segments = [];
    var nonEmptySegments = 0;

    for (var i = 0; i < arguments.length; i++) {
      var url = new URI(arguments[i]);
      input.push(url);
      var _segments = url.segment();
      for (var s = 0; s < _segments.length; s++) {
        if (typeof _segments[s] === 'string') {
          segments.push(_segments[s]);
        }

        if (_segments[s]) {
          nonEmptySegments++;
        }
      }
    }

    if (!segments.length || !nonEmptySegments) {
      return new URI('');
    }

    var uri = new URI('').segment(segments);

    if (input[0].path() === '' || input[0].path().slice(0, 1) === '/') {
      uri.path('/' + uri.path());
    }

    return uri.normalize();
  };

  URI.commonPath = function (one, two) {
    var length = Math.min(one.length, two.length);
    var pos;

    // find first non-matching character
    for (pos = 0; pos < length; pos++) {
      if (one.charAt(pos) !== two.charAt(pos)) {
        pos--;
        break;
      }
    }

    if (pos < 1) {
      return one.charAt(0) === two.charAt(0) && one.charAt(0) === '/' ? '/' : '';
    }

    // revert to last /
    if (one.charAt(pos) !== '/' || two.charAt(pos) !== '/') {
      pos = one.substring(0, pos).lastIndexOf('/');
    }

    return one.substring(0, pos + 1);
  };

  URI.withinString = function (string, callback, options) {
    options || (options = {});
    var _start = options.start || URI.findUri.start;
    var _end = options.end || URI.findUri.end;
    var _trim = options.trim || URI.findUri.trim;
    var _parens = options.parens || URI.findUri.parens;
    var _attributeOpen = /[a-z0-9-]=["']?$/i;

    _start.lastIndex = 0;
    while (true) {
      var match = _start.exec(string);
      if (!match) {
        break;
      }

      var start = match.index;
      if (options.ignoreHtml) {
        // attribut(e=["']?$)
        var attributeOpen = string.slice(Math.max(start - 3, 0), start);
        if (attributeOpen && _attributeOpen.test(attributeOpen)) {
          continue;
        }
      }

      var end = start + string.slice(start).search(_end);
      var slice = string.slice(start, end);
      // make sure we include well balanced parens
      var parensEnd = -1;
      while (true) {
        var parensMatch = _parens.exec(slice);
        if (!parensMatch) {
          break;
        }

        var parensMatchEnd = parensMatch.index + parensMatch[0].length;
        parensEnd = Math.max(parensEnd, parensMatchEnd);
      }

      if (parensEnd > -1) {
        slice = slice.slice(0, parensEnd) + slice.slice(parensEnd).replace(_trim, '');
      } else {
        slice = slice.replace(_trim, '');
      }

      if (slice.length <= match[0].length) {
        // the extract only contains the starting marker of a URI,
        // e.g. "www" or "http://"
        continue;
      }

      if (options.ignore && options.ignore.test(slice)) {
        continue;
      }

      end = start + slice.length;
      var result = callback(slice, start, end, string);
      if (result === undefined) {
        _start.lastIndex = end;
        continue;
      }

      result = String(result);
      string = string.slice(0, start) + result + string.slice(end);
      _start.lastIndex = start + result.length;
    }

    _start.lastIndex = 0;
    return string;
  };

  URI.ensureValidHostname = function (v) {
    // Theoretically URIs allow percent-encoding in Hostnames (according to RFC 3986)
    // they are not part of DNS and therefore ignored by URI.js

    if (v.match(URI.invalid_hostname_characters)) {
      // test punycode
      if (!punycode) {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-] and Punycode.js is not available');
      }

      if (punycode.toASCII(v).match(URI.invalid_hostname_characters)) {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-]');
      }
    }
  };

  // noConflict
  URI.noConflict = function (removeAll) {
    if (removeAll) {
      var unconflicted = {
        URI: this.noConflict()
      };

      if (root.URITemplate && typeof root.URITemplate.noConflict === 'function') {
        unconflicted.URITemplate = root.URITemplate.noConflict();
      }

      if (root.IPv6 && typeof root.IPv6.noConflict === 'function') {
        unconflicted.IPv6 = root.IPv6.noConflict();
      }

      if (root.SecondLevelDomains && typeof root.SecondLevelDomains.noConflict === 'function') {
        unconflicted.SecondLevelDomains = root.SecondLevelDomains.noConflict();
      }

      return unconflicted;
    } else if (root.URI === this) {
      root.URI = _URI;
    }

    return this;
  };

  p.build = function (deferBuild) {
    if (deferBuild === true) {
      this._deferred_build = true;
    } else if (deferBuild === undefined || this._deferred_build) {
      this._string = URI.build(this._parts);
      this._deferred_build = false;
    }

    return this;
  };

  p.clone = function () {
    return new URI(this);
  };

  p.valueOf = p.toString = function () {
    return this.build(false)._string;
  };

  function generateSimpleAccessor(_part) {
    return function (v, build) {
      if (v === undefined) {
        return this._parts[_part] || '';
      } else {
        this._parts[_part] = v || null;
        this.build(!build);
        return this;
      }
    };
  }

  function generatePrefixAccessor(_part, _key) {
    return function (v, build) {
      if (v === undefined) {
        return this._parts[_part] || '';
      } else {
        if (v !== null) {
          v = v + '';
          if (v.charAt(0) === _key) {
            v = v.substring(1);
          }
        }

        this._parts[_part] = v;
        this.build(!build);
        return this;
      }
    };
  }

  p.protocol = generateSimpleAccessor('protocol');
  p.username = generateSimpleAccessor('username');
  p.password = generateSimpleAccessor('password');
  p.hostname = generateSimpleAccessor('hostname');
  p.port = generateSimpleAccessor('port');
  p.query = generatePrefixAccessor('query', '?');
  p.fragment = generatePrefixAccessor('fragment', '#');

  p.search = function (v, build) {
    var t = this.query(v, build);
    return typeof t === 'string' && t.length ? '?' + t : t;
  };
  p.hash = function (v, build) {
    var t = this.fragment(v, build);
    return typeof t === 'string' && t.length ? '#' + t : t;
  };

  p.pathname = function (v, build) {
    if (v === undefined || v === true) {
      var res = this._parts.path || (this._parts.hostname ? '/' : '');
      return v ? (this._parts.urn ? URI.decodeUrnPath : URI.decodePath)(res) : res;
    } else {
      if (this._parts.urn) {
        this._parts.path = v ? URI.recodeUrnPath(v) : '';
      } else {
        this._parts.path = v ? URI.recodePath(v) : '/';
      }
      this.build(!build);
      return this;
    }
  };
  p.path = p.pathname;
  p.href = function (href, build) {
    var key;

    if (href === undefined) {
      return this.toString();
    }

    this._string = '';
    this._parts = URI._parts();

    var _URI = href instanceof URI;
    var _object = (typeof href === 'undefined' ? 'undefined' : _typeof(href)) === 'object' && (href.hostname || href.path || href.pathname);
    if (href.nodeName) {
      var attribute = URI.getDomAttribute(href);
      href = href[attribute] || '';
      _object = false;
    }

    // window.location is reported to be an object, but it's not the sort
    // of object we're looking for:
    // * location.protocol ends with a colon
    // * location.query != object.search
    // * location.hash != object.fragment
    // simply serializing the unknown object should do the trick
    // (for location, not for everything...)
    if (!_URI && _object && href.pathname !== undefined) {
      href = href.toString();
    }

    if (typeof href === 'string' || href instanceof String) {
      this._parts = URI.parse(String(href), this._parts);
    } else if (_URI || _object) {
      var src = _URI ? href._parts : href;
      for (key in src) {
        if (hasOwn.call(this._parts, key)) {
          this._parts[key] = src[key];
        }
      }
    } else {
      throw new TypeError('invalid input');
    }

    this.build(!build);
    return this;
  };

  // identification accessors
  p.is = function (what) {
    var ip = false;
    var ip4 = false;
    var ip6 = false;
    var name = false;
    var sld = false;
    var idn = false;
    var punycode = false;
    var relative = !this._parts.urn;

    if (this._parts.hostname) {
      relative = false;
      ip4 = URI.ip4_expression.test(this._parts.hostname);
      ip6 = URI.ip6_expression.test(this._parts.hostname);
      ip = ip4 || ip6;
      name = !ip;
      sld = name && SLD && SLD.has(this._parts.hostname);
      idn = name && URI.idn_expression.test(this._parts.hostname);
      punycode = name && URI.punycode_expression.test(this._parts.hostname);
    }

    switch (what.toLowerCase()) {
      case 'relative':
        return relative;

      case 'absolute':
        return !relative;

      // hostname identification
      case 'domain':
      case 'name':
        return name;

      case 'sld':
        return sld;

      case 'ip':
        return ip;

      case 'ip4':
      case 'ipv4':
      case 'inet4':
        return ip4;

      case 'ip6':
      case 'ipv6':
      case 'inet6':
        return ip6;

      case 'idn':
        return idn;

      case 'url':
        return !this._parts.urn;

      case 'urn':
        return !!this._parts.urn;

      case 'punycode':
        return punycode;
    }

    return null;
  };

  // component specific input validation
  var _protocol = p.protocol;
  var _port = p.port;
  var _hostname = p.hostname;

  p.protocol = function (v, build) {
    if (v !== undefined) {
      if (v) {
        // accept trailing ://
        v = v.replace(/:(\/\/)?$/, '');

        if (!v.match(URI.protocol_expression)) {
          throw new TypeError('Protocol "' + v + '" contains characters other than [A-Z0-9.+-] or doesn\'t start with [A-Z]');
        }
      }
    }
    return _protocol.call(this, v, build);
  };
  p.scheme = p.protocol;
  p.port = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v !== undefined) {
      if (v === 0) {
        v = null;
      }

      if (v) {
        v += '';
        if (v.charAt(0) === ':') {
          v = v.substring(1);
        }

        if (v.match(/[^0-9]/)) {
          throw new TypeError('Port "' + v + '" contains characters other than [0-9]');
        }
      }
    }
    return _port.call(this, v, build);
  };
  p.hostname = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v !== undefined) {
      var x = {};
      var res = URI.parseHost(v, x);
      if (res !== '/') {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-]');
      }

      v = x.hostname;
    }
    return _hostname.call(this, v, build);
  };

  // compound accessors
  p.origin = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined) {
      var protocol = this.protocol();
      var authority = this.authority();
      if (!authority) {
        return '';
      }

      return (protocol ? protocol + '://' : '') + this.authority();
    } else {
      var origin = URI(v);
      this.protocol(origin.protocol()).authority(origin.authority()).build(!build);
      return this;
    }
  };
  p.host = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined) {
      return this._parts.hostname ? URI.buildHost(this._parts) : '';
    } else {
      var res = URI.parseHost(v, this._parts);
      if (res !== '/') {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-]');
      }

      this.build(!build);
      return this;
    }
  };
  p.authority = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined) {
      return this._parts.hostname ? URI.buildAuthority(this._parts) : '';
    } else {
      var res = URI.parseAuthority(v, this._parts);
      if (res !== '/') {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-]');
      }

      this.build(!build);
      return this;
    }
  };
  p.userinfo = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined) {
      var t = URI.buildUserinfo(this._parts);
      return t ? t.substring(0, t.length - 1) : t;
    } else {
      if (v[v.length - 1] !== '@') {
        v += '@';
      }

      URI.parseUserinfo(v, this._parts);
      this.build(!build);
      return this;
    }
  };
  p.resource = function (v, build) {
    var parts;

    if (v === undefined) {
      return this.path() + this.search() + this.hash();
    }

    parts = URI.parse(v);
    this._parts.path = parts.path;
    this._parts.query = parts.query;
    this._parts.fragment = parts.fragment;
    this.build(!build);
    return this;
  };

  // fraction accessors
  p.subdomain = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    // convenience, return "www" from "www.example.org"
    if (v === undefined) {
      if (!this._parts.hostname || this.is('IP')) {
        return '';
      }

      // grab domain and add another segment
      var end = this._parts.hostname.length - this.domain().length - 1;
      return this._parts.hostname.substring(0, end) || '';
    } else {
      var e = this._parts.hostname.length - this.domain().length;
      var sub = this._parts.hostname.substring(0, e);
      var replace = new RegExp('^' + escapeRegEx(sub));

      if (v && v.charAt(v.length - 1) !== '.') {
        v += '.';
      }

      if (v) {
        URI.ensureValidHostname(v);
      }

      this._parts.hostname = this._parts.hostname.replace(replace, v);
      this.build(!build);
      return this;
    }
  };
  p.domain = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (typeof v === 'boolean') {
      build = v;
      v = undefined;
    }

    // convenience, return "example.org" from "www.example.org"
    if (v === undefined) {
      if (!this._parts.hostname || this.is('IP')) {
        return '';
      }

      // if hostname consists of 1 or 2 segments, it must be the domain
      var t = this._parts.hostname.match(/\./g);
      if (t && t.length < 2) {
        return this._parts.hostname;
      }

      // grab tld and add another segment
      var end = this._parts.hostname.length - this.tld(build).length - 1;
      end = this._parts.hostname.lastIndexOf('.', end - 1) + 1;
      return this._parts.hostname.substring(end) || '';
    } else {
      if (!v) {
        throw new TypeError('cannot set domain empty');
      }

      URI.ensureValidHostname(v);

      if (!this._parts.hostname || this.is('IP')) {
        this._parts.hostname = v;
      } else {
        var replace = new RegExp(escapeRegEx(this.domain()) + '$');
        this._parts.hostname = this._parts.hostname.replace(replace, v);
      }

      this.build(!build);
      return this;
    }
  };
  p.tld = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (typeof v === 'boolean') {
      build = v;
      v = undefined;
    }

    // return "org" from "www.example.org"
    if (v === undefined) {
      if (!this._parts.hostname || this.is('IP')) {
        return '';
      }

      var pos = this._parts.hostname.lastIndexOf('.');
      var tld = this._parts.hostname.substring(pos + 1);

      if (build !== true && SLD && SLD.list[tld.toLowerCase()]) {
        return SLD.get(this._parts.hostname) || tld;
      }

      return tld;
    } else {
      var replace;

      if (!v) {
        throw new TypeError('cannot set TLD empty');
      } else if (v.match(/[^a-zA-Z0-9-]/)) {
        if (SLD && SLD.is(v)) {
          replace = new RegExp(escapeRegEx(this.tld()) + '$');
          this._parts.hostname = this._parts.hostname.replace(replace, v);
        } else {
          throw new TypeError('TLD "' + v + '" contains characters other than [A-Z0-9]');
        }
      } else if (!this._parts.hostname || this.is('IP')) {
        throw new ReferenceError('cannot set TLD on non-domain host');
      } else {
        replace = new RegExp(escapeRegEx(this.tld()) + '$');
        this._parts.hostname = this._parts.hostname.replace(replace, v);
      }

      this.build(!build);
      return this;
    }
  };
  p.directory = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined || v === true) {
      if (!this._parts.path && !this._parts.hostname) {
        return '';
      }

      if (this._parts.path === '/') {
        return '/';
      }

      var end = this._parts.path.length - this.filename().length - 1;
      var res = this._parts.path.substring(0, end) || (this._parts.hostname ? '/' : '');

      return v ? URI.decodePath(res) : res;
    } else {
      var e = this._parts.path.length - this.filename().length;
      var directory = this._parts.path.substring(0, e);
      var replace = new RegExp('^' + escapeRegEx(directory));

      // fully qualifier directories begin with a slash
      if (!this.is('relative')) {
        if (!v) {
          v = '/';
        }

        if (v.charAt(0) !== '/') {
          v = '/' + v;
        }
      }

      // directories always end with a slash
      if (v && v.charAt(v.length - 1) !== '/') {
        v += '/';
      }

      v = URI.recodePath(v);
      this._parts.path = this._parts.path.replace(replace, v);
      this.build(!build);
      return this;
    }
  };
  p.filename = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (typeof v !== 'string') {
      if (!this._parts.path || this._parts.path === '/') {
        return '';
      }

      var pos = this._parts.path.lastIndexOf('/');
      var res = this._parts.path.substring(pos + 1);

      return v ? URI.decodePathSegment(res) : res;
    } else {
      var mutatedDirectory = false;

      if (v.charAt(0) === '/') {
        v = v.substring(1);
      }

      if (v.match(/\.?\//)) {
        mutatedDirectory = true;
      }

      var replace = new RegExp(escapeRegEx(this.filename()) + '$');
      v = URI.recodePath(v);
      this._parts.path = this._parts.path.replace(replace, v);

      if (mutatedDirectory) {
        this.normalizePath(build);
      } else {
        this.build(!build);
      }

      return this;
    }
  };
  p.suffix = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v === undefined || v === true) {
      if (!this._parts.path || this._parts.path === '/') {
        return '';
      }

      var filename = this.filename();
      var pos = filename.lastIndexOf('.');
      var s, res;

      if (pos === -1) {
        return '';
      }

      // suffix may only contain alnum characters (yup, I made this up.)
      s = filename.substring(pos + 1);
      res = /^[a-z0-9%]+$/i.test(s) ? s : '';
      return v ? URI.decodePathSegment(res) : res;
    } else {
      if (v.charAt(0) === '.') {
        v = v.substring(1);
      }

      var suffix = this.suffix();
      var replace;

      if (!suffix) {
        if (!v) {
          return this;
        }

        this._parts.path += '.' + URI.recodePath(v);
      } else if (!v) {
        replace = new RegExp(escapeRegEx('.' + suffix) + '$');
      } else {
        replace = new RegExp(escapeRegEx(suffix) + '$');
      }

      if (replace) {
        v = URI.recodePath(v);
        this._parts.path = this._parts.path.replace(replace, v);
      }

      this.build(!build);
      return this;
    }
  };
  p.segment = function (segment, v, build) {
    var separator = this._parts.urn ? ':' : '/';
    var path = this.path();
    var absolute = path.substring(0, 1) === '/';
    var segments = path.split(separator);

    if (segment !== undefined && typeof segment !== 'number') {
      build = v;
      v = segment;
      segment = undefined;
    }

    if (segment !== undefined && typeof segment !== 'number') {
      throw new Error('Bad segment "' + segment + '", must be 0-based integer');
    }

    if (absolute) {
      segments.shift();
    }

    if (segment < 0) {
      // allow negative indexes to address from the end
      segment = Math.max(segments.length + segment, 0);
    }

    if (v === undefined) {
      /*jshint laxbreak: true */
      return segment === undefined ? segments : segments[segment];
      /*jshint laxbreak: false */
    } else if (segment === null || segments[segment] === undefined) {
      if (isArray(v)) {
        segments = [];
        // collapse empty elements within array
        for (var i = 0, l = v.length; i < l; i++) {
          if (!v[i].length && (!segments.length || !segments[segments.length - 1].length)) {
            continue;
          }

          if (segments.length && !segments[segments.length - 1].length) {
            segments.pop();
          }

          segments.push(trimSlashes(v[i]));
        }
      } else if (v || typeof v === 'string') {
        v = trimSlashes(v);
        if (segments[segments.length - 1] === '') {
          // empty trailing elements have to be overwritten
          // to prevent results such as /foo//bar
          segments[segments.length - 1] = v;
        } else {
          segments.push(v);
        }
      }
    } else {
      if (v) {
        segments[segment] = trimSlashes(v);
      } else {
        segments.splice(segment, 1);
      }
    }

    if (absolute) {
      segments.unshift('');
    }

    return this.path(segments.join(separator), build);
  };
  p.segmentCoded = function (segment, v, build) {
    var segments, i, l;

    if (typeof segment !== 'number') {
      build = v;
      v = segment;
      segment = undefined;
    }

    if (v === undefined) {
      segments = this.segment(segment, v, build);
      if (!isArray(segments)) {
        segments = segments !== undefined ? URI.decode(segments) : undefined;
      } else {
        for (i = 0, l = segments.length; i < l; i++) {
          segments[i] = URI.decode(segments[i]);
        }
      }

      return segments;
    }

    if (!isArray(v)) {
      v = typeof v === 'string' || v instanceof String ? URI.encode(v) : v;
    } else {
      for (i = 0, l = v.length; i < l; i++) {
        v[i] = URI.encode(v[i]);
      }
    }

    return this.segment(segment, v, build);
  };

  // mutating query string
  var q = p.query;
  p.query = function (v, build) {
    if (v === true) {
      return URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);
    } else if (typeof v === 'function') {
      var data = URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);
      var result = v.call(this, data);
      this._parts.query = URI.buildQuery(result || data, this._parts.duplicateQueryParameters, this._parts.escapeQuerySpace);
      this.build(!build);
      return this;
    } else if (v !== undefined && typeof v !== 'string') {
      this._parts.query = URI.buildQuery(v, this._parts.duplicateQueryParameters, this._parts.escapeQuerySpace);
      this.build(!build);
      return this;
    } else {
      return q.call(this, v, build);
    }
  };
  p.setQuery = function (name, value, build) {
    var data = URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);

    if (typeof name === 'string' || name instanceof String) {
      data[name] = value !== undefined ? value : null;
    } else if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
      for (var key in name) {
        if (hasOwn.call(name, key)) {
          data[key] = name[key];
        }
      }
    } else {
      throw new TypeError('URI.addQuery() accepts an object, string as the name parameter');
    }

    this._parts.query = URI.buildQuery(data, this._parts.duplicateQueryParameters, this._parts.escapeQuerySpace);
    if (typeof name !== 'string') {
      build = value;
    }

    this.build(!build);
    return this;
  };
  p.addQuery = function (name, value, build) {
    var data = URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);
    URI.addQuery(data, name, value === undefined ? null : value);
    this._parts.query = URI.buildQuery(data, this._parts.duplicateQueryParameters, this._parts.escapeQuerySpace);
    if (typeof name !== 'string') {
      build = value;
    }

    this.build(!build);
    return this;
  };
  p.removeQuery = function (name, value, build) {
    var data = URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);
    URI.removeQuery(data, name, value);
    this._parts.query = URI.buildQuery(data, this._parts.duplicateQueryParameters, this._parts.escapeQuerySpace);
    if (typeof name !== 'string') {
      build = value;
    }

    this.build(!build);
    return this;
  };
  p.hasQuery = function (name, value, withinArray) {
    var data = URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace);
    return URI.hasQuery(data, name, value, withinArray);
  };
  p.setSearch = p.setQuery;
  p.addSearch = p.addQuery;
  p.removeSearch = p.removeQuery;
  p.hasSearch = p.hasQuery;

  // sanitizing URLs
  p.normalize = function () {
    if (this._parts.urn) {
      return this.normalizeProtocol(false).normalizePath(false).normalizeQuery(false).normalizeFragment(false).build();
    }

    return this.normalizeProtocol(false).normalizeHostname(false).normalizePort(false).normalizePath(false).normalizeQuery(false).normalizeFragment(false).build();
  };
  p.normalizeProtocol = function (build) {
    if (typeof this._parts.protocol === 'string') {
      this._parts.protocol = this._parts.protocol.toLowerCase();
      this.build(!build);
    }

    return this;
  };
  p.normalizeHostname = function (build) {
    if (this._parts.hostname) {
      if (this.is('IDN') && punycode) {
        this._parts.hostname = punycode.toASCII(this._parts.hostname);
      } else if (this.is('IPv6') && IPv6) {
        this._parts.hostname = IPv6.best(this._parts.hostname);
      }

      this._parts.hostname = this._parts.hostname.toLowerCase();
      this.build(!build);
    }

    return this;
  };
  p.normalizePort = function (build) {
    // remove port of it's the protocol's default
    if (typeof this._parts.protocol === 'string' && this._parts.port === URI.defaultPorts[this._parts.protocol]) {
      this._parts.port = null;
      this.build(!build);
    }

    return this;
  };
  p.normalizePath = function (build) {
    var _path = this._parts.path;
    if (!_path) {
      return this;
    }

    if (this._parts.urn) {
      this._parts.path = URI.recodeUrnPath(this._parts.path);
      this.build(!build);
      return this;
    }

    if (this._parts.path === '/') {
      return this;
    }

    _path = URI.recodePath(_path);

    var _was_relative;
    var _leadingParents = '';
    var _parent, _pos;

    // handle relative paths
    if (_path.charAt(0) !== '/') {
      _was_relative = true;
      _path = '/' + _path;
    }

    // handle relative files (as opposed to directories)
    if (_path.slice(-3) === '/..' || _path.slice(-2) === '/.') {
      _path += '/';
    }

    // resolve simples
    _path = _path.replace(/(\/(\.\/)+)|(\/\.$)/g, '/').replace(/\/{2,}/g, '/');

    // remember leading parents
    if (_was_relative) {
      _leadingParents = _path.substring(1).match(/^(\.\.\/)+/) || '';
      if (_leadingParents) {
        _leadingParents = _leadingParents[0];
      }
    }

    // resolve parents
    while (true) {
      _parent = _path.search(/\/\.\.(\/|$)/);
      if (_parent === -1) {
        // no more ../ to resolve
        break;
      } else if (_parent === 0) {
        // top level cannot be relative, skip it
        _path = _path.substring(3);
        continue;
      }

      _pos = _path.substring(0, _parent).lastIndexOf('/');
      if (_pos === -1) {
        _pos = _parent;
      }
      _path = _path.substring(0, _pos) + _path.substring(_parent + 3);
    }

    // revert to relative
    if (_was_relative && this.is('relative')) {
      _path = _leadingParents + _path.substring(1);
    }

    this._parts.path = _path;
    this.build(!build);
    return this;
  };
  p.normalizePathname = p.normalizePath;
  p.normalizeQuery = function (build) {
    if (typeof this._parts.query === 'string') {
      if (!this._parts.query.length) {
        this._parts.query = null;
      } else {
        this.query(URI.parseQuery(this._parts.query, this._parts.escapeQuerySpace));
      }

      this.build(!build);
    }

    return this;
  };
  p.normalizeFragment = function (build) {
    if (!this._parts.fragment) {
      this._parts.fragment = null;
      this.build(!build);
    }

    return this;
  };
  p.normalizeSearch = p.normalizeQuery;
  p.normalizeHash = p.normalizeFragment;

  p.iso8859 = function () {
    // expect unicode input, iso8859 output
    var e = URI.encode;
    var d = URI.decode;

    URI.encode = escape;
    URI.decode = decodeURIComponent;
    try {
      this.normalize();
    } finally {
      URI.encode = e;
      URI.decode = d;
    }
    return this;
  };

  p.unicode = function () {
    // expect iso8859 input, unicode output
    var e = URI.encode;
    var d = URI.decode;

    URI.encode = strictEncodeURIComponent;
    URI.decode = unescape;
    try {
      this.normalize();
    } finally {
      URI.encode = e;
      URI.decode = d;
    }
    return this;
  };

  p.readable = function () {
    var uri = this.clone();
    // removing username, password, because they shouldn't be displayed according to RFC 3986
    uri.username('').password('').normalize();
    var t = '';
    if (uri._parts.protocol) {
      t += uri._parts.protocol + '://';
    }

    if (uri._parts.hostname) {
      if (uri.is('punycode') && punycode) {
        t += punycode.toUnicode(uri._parts.hostname);
        if (uri._parts.port) {
          t += ':' + uri._parts.port;
        }
      } else {
        t += uri.host();
      }
    }

    if (uri._parts.hostname && uri._parts.path && uri._parts.path.charAt(0) !== '/') {
      t += '/';
    }

    t += uri.path(true);
    if (uri._parts.query) {
      var q = '';
      for (var i = 0, qp = uri._parts.query.split('&'), l = qp.length; i < l; i++) {
        var kv = (qp[i] || '').split('=');
        q += '&' + URI.decodeQuery(kv[0], this._parts.escapeQuerySpace).replace(/&/g, '%26');

        if (kv[1] !== undefined) {
          q += '=' + URI.decodeQuery(kv[1], this._parts.escapeQuerySpace).replace(/&/g, '%26');
        }
      }
      t += '?' + q.substring(1);
    }

    t += URI.decodeQuery(uri.hash(), true);
    return t;
  };

  // resolving relative and absolute URLs
  p.absoluteTo = function (base) {
    var resolved = this.clone();
    var properties = ['protocol', 'username', 'password', 'hostname', 'port'];
    var basedir, i, p;

    if (this._parts.urn) {
      throw new Error('URNs do not have any generally defined hierarchical components');
    }

    if (!(base instanceof URI)) {
      base = new URI(base);
    }

    if (resolved._parts.protocol) {
      // Directly returns even if this._parts.hostname is empty.
      return resolved;
    } else {
      resolved._parts.protocol = base._parts.protocol;
    }

    if (this._parts.hostname) {
      return resolved;
    }

    for (i = 0; p = properties[i]; i++) {
      resolved._parts[p] = base._parts[p];
    }

    if (!resolved._parts.path) {
      resolved._parts.path = base._parts.path;
      if (!resolved._parts.query) {
        resolved._parts.query = base._parts.query;
      }
    } else {
      if (resolved._parts.path.substring(-2) === '..') {
        resolved._parts.path += '/';
      }

      if (resolved.path().charAt(0) !== '/') {
        basedir = base.directory();
        basedir = basedir ? basedir : base.path().indexOf('/') === 0 ? '/' : '';
        resolved._parts.path = (basedir ? basedir + '/' : '') + resolved._parts.path;
        resolved.normalizePath();
      }
    }

    resolved.build();
    return resolved;
  };
  p.relativeTo = function (base) {
    var relative = this.clone().normalize();
    var relativeParts, baseParts, common, relativePath, basePath;

    if (relative._parts.urn) {
      throw new Error('URNs do not have any generally defined hierarchical components');
    }

    base = new URI(base).normalize();
    relativeParts = relative._parts;
    baseParts = base._parts;
    relativePath = relative.path();
    basePath = base.path();

    if (relativePath.charAt(0) !== '/') {
      throw new Error('URI is already relative');
    }

    if (basePath.charAt(0) !== '/') {
      throw new Error('Cannot calculate a URI relative to another relative URI');
    }

    if (relativeParts.protocol === baseParts.protocol) {
      relativeParts.protocol = null;
    }

    if (relativeParts.username !== baseParts.username || relativeParts.password !== baseParts.password) {
      return relative.build();
    }

    if (relativeParts.protocol !== null || relativeParts.username !== null || relativeParts.password !== null) {
      return relative.build();
    }

    if (relativeParts.hostname === baseParts.hostname && relativeParts.port === baseParts.port) {
      relativeParts.hostname = null;
      relativeParts.port = null;
    } else {
      return relative.build();
    }

    if (relativePath === basePath) {
      relativeParts.path = '';
      return relative.build();
    }

    // determine common sub path
    common = URI.commonPath(relativePath, basePath);

    // If the paths have nothing in common, return a relative URL with the absolute path.
    if (!common) {
      return relative.build();
    }

    var parents = baseParts.path.substring(common.length).replace(/[^\/]*$/, '').replace(/.*?\//g, '../');

    relativeParts.path = parents + relativeParts.path.substring(common.length) || './';

    return relative.build();
  };

  // comparing URIs
  p.equals = function (uri) {
    var one = this.clone();
    var two = new URI(uri);
    var one_map = {};
    var two_map = {};
    var checked = {};
    var one_query, two_query, key;

    one.normalize();
    two.normalize();

    // exact match
    if (one.toString() === two.toString()) {
      return true;
    }

    // extract query string
    one_query = one.query();
    two_query = two.query();
    one.query('');
    two.query('');

    // definitely not equal if not even non-query parts match
    if (one.toString() !== two.toString()) {
      return false;
    }

    // query parameters have the same length, even if they're permuted
    if (one_query.length !== two_query.length) {
      return false;
    }

    one_map = URI.parseQuery(one_query, this._parts.escapeQuerySpace);
    two_map = URI.parseQuery(two_query, this._parts.escapeQuerySpace);

    for (key in one_map) {
      if (hasOwn.call(one_map, key)) {
        if (!isArray(one_map[key])) {
          if (one_map[key] !== two_map[key]) {
            return false;
          }
        } else if (!arraysEqual(one_map[key], two_map[key])) {
          return false;
        }

        checked[key] = true;
      }
    }

    for (key in two_map) {
      if (hasOwn.call(two_map, key)) {
        if (!checked[key]) {
          // two contains a parameter not present in one
          return false;
        }
      }
    }

    return true;
  };

  // state
  p.duplicateQueryParameters = function (v) {
    this._parts.duplicateQueryParameters = !!v;
    return this;
  };

  p.escapeQuerySpace = function (v) {
    this._parts.escapeQuerySpace = !!v;
    return this;
  };

  return URI;
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 6)(module)))

/***/ }),
/* 1011 */
/*!****************************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/atlas-marker-genes-search-results/lib/FlaskLoaderIcon.js ***!
  \****************************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 2);

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var FlaskLoaderIcon = function FlaskLoaderIcon(props) {
  return _react2.default.createElement("svg", { className: "lds-flask", width: props.width, height: props.height, xmlns: "http://www.w3.org/2000/svg", viewBox: "0 0 100 100", preserveAspectRatio: "xMidYMid" }, _react2.default.createElement("defs", null, _react2.default.createElement("clipPath", { id: "lds-flask-cpid-f698bd12cdca4", clipPathUnits: "userSpaceOnUse" }, _react2.default.createElement("rect", { x: "0", y: "50", width: "100", height: "50" })), _react2.default.createElement("pattern", { id: "lds-flask-patid-c8acea1e2ed45", patternUnits: "userSpaceOnUse", x: "0", y: "0", width: "100", height: "100" }, _react2.default.createElement("rect", { x: "0", y: "0", width: "100", height: "100", fill: "#5bc0de" }), _react2.default.createElement("circle", { cx: "34", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 128;0 -28", keyTimes: "0;1", dur: "3s", begin: "-2.85s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "63", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 148;0 -48", keyTimes: "0;1", dur: "3s", begin: "-0.3s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "14", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 144;0 -44", keyTimes: "0;1", dur: "3s", begin: "-0.81s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "23", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 115;0 -15", keyTimes: "0;1", dur: "3s", begin: "-1.47s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "6", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 106;0 -6", keyTimes: "0;1", dur: "3s", begin: "-0.6s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "20", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 146;0 -46", keyTimes: "0;1", dur: "3s", begin: "-1.74s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "48", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 142;0 -42", keyTimes: "0;1", dur: "3s", begin: "-1.53s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "65", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 130;0 -30", keyTimes: "0;1", dur: "3s", begin: "-1.89s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "17", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 108;0 -8", keyTimes: "0;1", dur: "3s", begin: "-0.72s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "76", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 120;0 -20", keyTimes: "0;1", dur: "3s", begin: "-1.17s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "49", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 109;0 -9", keyTimes: "0;1", dur: "3s", begin: "-0.33s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "65", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 151;0 -51", keyTimes: "0;1", dur: "3s", begin: "-0.96s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "64", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 118;0 -18", keyTimes: "0;1", dur: "3s", begin: "-0.48s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "55", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 146;0 -46", keyTimes: "0;1", dur: "3s", begin: "-1.02s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "24", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 125;0 -25", keyTimes: "0;1", dur: "3s", begin: "-1.53s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "39", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 103;0 -3", keyTimes: "0;1", dur: "3s", begin: "-1.35s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "7", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 113;0 -13", keyTimes: "0;1", dur: "3s", begin: "-1.86s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "88", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 138;0 -38", keyTimes: "0;1", dur: "3s", begin: "-1.23s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "40", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 136;0 -36", keyTimes: "0;1", dur: "3s", begin: "-0.69s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "47", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 128;0 -28", keyTimes: "0;1", dur: "3s", begin: "-0.45s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "87", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 117;0 -17", keyTimes: "0;1", dur: "3s", begin: "-0.93s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "9", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 111;0 -11", keyTimes: "0;1", dur: "3s", begin: "-1.89s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "87", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 121;0 -21", keyTimes: "0;1", dur: "3s", begin: "-2.1s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "57", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 130;0 -30", keyTimes: "0;1", dur: "3s", begin: "-2.73s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "81", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 151;0 -51", keyTimes: "0;1", dur: "3s", begin: "-1.98s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "32", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 125;0 -25", keyTimes: "0;1", dur: "3s", begin: "-0.69s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "35", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 144;0 -44", keyTimes: "0;1", dur: "3s", begin: "-0.6s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "84", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 145;0 -45", keyTimes: "0;1", dur: "3s", begin: "-1.98s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "89", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 132;0 -32", keyTimes: "0;1", dur: "3s", begin: "-0.36s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "21", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 129;0 -29", keyTimes: "0;1", dur: "3s", begin: "-1.53s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "31", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 130;0 -30", keyTimes: "0;1", dur: "3s", begin: "-1.86s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "85", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 148;0 -48", keyTimes: "0;1", dur: "3s", begin: "-2.88s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "41", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 107;0 -7", keyTimes: "0;1", dur: "3s", begin: "-0.51s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "14", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 132;0 -32", keyTimes: "0;1", dur: "3s", begin: "-0.48s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "13", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 147;0 -47", keyTimes: "0;1", dur: "3s", begin: "-2.25s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "45", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 142;0 -42", keyTimes: "0;1", dur: "3s", begin: "-0.48s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "90", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 118;0 -18", keyTimes: "0;1", dur: "3s", begin: "-0.45s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "74", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 112;0 -12", keyTimes: "0;1", dur: "3s", begin: "-1.08s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "80", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 116;0 -16", keyTimes: "0;1", dur: "3s", begin: "-2.43s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "56", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 147;0 -47", keyTimes: "0;1", dur: "3s", begin: "-0.12s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "68", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 135;0 -35", keyTimes: "0;1", dur: "3s", begin: "-2.64s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "2", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 113;0 -13", keyTimes: "0;1", dur: "3s", begin: "-1.41s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "50", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 104;0 -4", keyTimes: "0;1", dur: "3s", begin: "-1.05s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "45", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 144;0 -44", keyTimes: "0;1", dur: "3s", begin: "-1.14s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "86", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 123;0 -23", keyTimes: "0;1", dur: "3s", begin: "-2.4s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "3", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 148;0 -48", keyTimes: "0;1", dur: "3s", begin: "-2.4s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "33", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 115;0 -15", keyTimes: "0;1", dur: "3s", begin: "-2.85s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "94", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 114;0 -14", keyTimes: "0;1", dur: "3s", begin: "-2.52s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "55", cy: "0", r: "3", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 132;0 -32", keyTimes: "0;1", dur: "3s", begin: "-1.71s", repeatCount: "indefinite" })), _react2.default.createElement("circle", { cx: "68", cy: "0", r: "2", fill: "#337ab7" }, _react2.default.createElement("animateTransform", { attributeName: "transform", type: "translate", values: "0 104;0 -4", keyTimes: "0;1", dur: "3s", begin: "-0.39s", repeatCount: "indefinite" })), "      ")), _react2.default.createElement("path", { fill: "url(#lds-flask-patid-c8acea1e2ed45)", clipPath: "url(#lds-flask-cpid-f698bd12cdca4)", d: "M59,37.3V18.9c3-0.8,5.1-3.6,5.1-6.8C64.1,8.2,61,5,57.1,5H42.9c-3.9,0-7.1,3.2-7.1,7.1c0,3.2,2.2,6,5.1,6.8v18.4c-11.9,3.8-20.6,15-20.6,28.2C20.4,81.8,33.7,95,50,95s29.6-13.2,29.6-29.6C79.6,52.2,70.9,41.1,59,37.3z" }), _react2.default.createElement("path", { fill: "none", stroke: "#5cb85c", strokeWidth: "5", d: "M59,37.3V18.9c3-0.8,5.1-3.6,5.1-6.8C64.1,8.2,61,5,57.1,5H42.9c-3.9,0-7.1,3.2-7.1,7.1c0,3.2,2.2,6,5.1,6.8v18.4c-11.9,3.8-20.6,15-20.6,28.2C20.4,81.8,33.7,95,50,95s29.6-13.2,29.6-29.6C79.6,52.2,70.9,41.1,59,37.3z" }));
};

FlaskLoaderIcon.defaultProps = {
  width: "100%",
  height: "100%"
};

exports.default = FlaskLoaderIcon;

/***/ }),
/* 1012 */
/*!******************************************************************************************************!*\
  !*** ./bundles/marker-genes/node_modules/atlas-marker-genes-search-results/lib/MarkerGeneProfile.js ***!
  \******************************************************************************************************/
/*! no static exports found */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 2);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 422);

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var MarkerGeneProfile = function MarkerGeneProfile(props) {
  return _react2.default.createElement('div', null, _react2.default.createElement('a', { href: props.url }, props.experimentAccession, ' \u2013 ', props.perplexity));
};

MarkerGeneProfile.propTypes = {
  experimentAccession: _propTypes2.default.string.isRequired,
  perplexity: _propTypes2.default.number.isRequired,
  clusters: _propTypes2.default.arrayOf(_propTypes2.default.shape({
    clusterId: _propTypes2.default.number.isRequired,
    p: _propTypes2.default.number.isRequired
  })).isRequired,
  url: _propTypes2.default.string.isRequired
};

exports.default = MarkerGeneProfile;

/***/ })
]),[870]);
//# sourceMappingURL=markerGenesSearchResults.bundle.js.map