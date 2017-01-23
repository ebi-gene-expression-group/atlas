var expressionAtlasDifferentialExpression =
webpackJsonp_name_([4],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */905);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */3323);


/***/ },

/***/ 905:
/*!***************************************!*\
  !*** ./~/babel-polyfill/lib/index.js ***!
  \***************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {"use strict";
	
	__webpack_require__(/*! core-js/shim */ 906);
	
	__webpack_require__(/*! regenerator-runtime/runtime */ 1197);
	
	__webpack_require__(/*! core-js/fn/regexp/escape */ 1198);
	
	if (global._babelPolyfill) {
	  throw new Error("only one instance of babel-polyfill is allowed");
	}
	global._babelPolyfill = true;
	
	var DEFINE_PROPERTY = "defineProperty";
	function define(O, key, value) {
	  O[key] || Object[DEFINE_PROPERTY](O, key, {
	    writable: true,
	    configurable: true,
	    value: value
	  });
	}
	
	define(String.prototype, "padLeft", "".padStart);
	define(String.prototype, "padRight", "".padEnd);
	
	"pop,reverse,shift,keys,values,entries,indexOf,every,some,forEach,map,filter,find,findIndex,includes,join,slice,concat,push,splice,unshift,sort,lastIndexOf,reduce,reduceRight,copyWithin,fill".split(",").forEach(function (key) {
	  [][key] && define(Array, key, Function.call.bind([][key]));
	});
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },

/***/ 906:
/*!********************************************!*\
  !*** ./~/babel-polyfill/~/core-js/shim.js ***!
  \********************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./modules/es6.symbol */ 907);
	__webpack_require__(/*! ./modules/es6.object.create */ 956);
	__webpack_require__(/*! ./modules/es6.object.define-property */ 957);
	__webpack_require__(/*! ./modules/es6.object.define-properties */ 958);
	__webpack_require__(/*! ./modules/es6.object.get-own-property-descriptor */ 959);
	__webpack_require__(/*! ./modules/es6.object.get-prototype-of */ 961);
	__webpack_require__(/*! ./modules/es6.object.keys */ 964);
	__webpack_require__(/*! ./modules/es6.object.get-own-property-names */ 965);
	__webpack_require__(/*! ./modules/es6.object.freeze */ 966);
	__webpack_require__(/*! ./modules/es6.object.seal */ 967);
	__webpack_require__(/*! ./modules/es6.object.prevent-extensions */ 968);
	__webpack_require__(/*! ./modules/es6.object.is-frozen */ 969);
	__webpack_require__(/*! ./modules/es6.object.is-sealed */ 970);
	__webpack_require__(/*! ./modules/es6.object.is-extensible */ 971);
	__webpack_require__(/*! ./modules/es6.object.assign */ 972);
	__webpack_require__(/*! ./modules/es6.object.is */ 974);
	__webpack_require__(/*! ./modules/es6.object.set-prototype-of */ 976);
	__webpack_require__(/*! ./modules/es6.object.to-string */ 978);
	__webpack_require__(/*! ./modules/es6.function.bind */ 980);
	__webpack_require__(/*! ./modules/es6.function.name */ 983);
	__webpack_require__(/*! ./modules/es6.function.has-instance */ 984);
	__webpack_require__(/*! ./modules/es6.parse-int */ 985);
	__webpack_require__(/*! ./modules/es6.parse-float */ 989);
	__webpack_require__(/*! ./modules/es6.number.constructor */ 991);
	__webpack_require__(/*! ./modules/es6.number.to-fixed */ 993);
	__webpack_require__(/*! ./modules/es6.number.to-precision */ 996);
	__webpack_require__(/*! ./modules/es6.number.epsilon */ 997);
	__webpack_require__(/*! ./modules/es6.number.is-finite */ 998);
	__webpack_require__(/*! ./modules/es6.number.is-integer */ 999);
	__webpack_require__(/*! ./modules/es6.number.is-nan */ 1001);
	__webpack_require__(/*! ./modules/es6.number.is-safe-integer */ 1002);
	__webpack_require__(/*! ./modules/es6.number.max-safe-integer */ 1003);
	__webpack_require__(/*! ./modules/es6.number.min-safe-integer */ 1004);
	__webpack_require__(/*! ./modules/es6.number.parse-float */ 1005);
	__webpack_require__(/*! ./modules/es6.number.parse-int */ 1006);
	__webpack_require__(/*! ./modules/es6.math.acosh */ 1007);
	__webpack_require__(/*! ./modules/es6.math.asinh */ 1009);
	__webpack_require__(/*! ./modules/es6.math.atanh */ 1010);
	__webpack_require__(/*! ./modules/es6.math.cbrt */ 1011);
	__webpack_require__(/*! ./modules/es6.math.clz32 */ 1013);
	__webpack_require__(/*! ./modules/es6.math.cosh */ 1014);
	__webpack_require__(/*! ./modules/es6.math.expm1 */ 1015);
	__webpack_require__(/*! ./modules/es6.math.fround */ 1017);
	__webpack_require__(/*! ./modules/es6.math.hypot */ 1018);
	__webpack_require__(/*! ./modules/es6.math.imul */ 1019);
	__webpack_require__(/*! ./modules/es6.math.log10 */ 1020);
	__webpack_require__(/*! ./modules/es6.math.log1p */ 1021);
	__webpack_require__(/*! ./modules/es6.math.log2 */ 1022);
	__webpack_require__(/*! ./modules/es6.math.sign */ 1023);
	__webpack_require__(/*! ./modules/es6.math.sinh */ 1024);
	__webpack_require__(/*! ./modules/es6.math.tanh */ 1025);
	__webpack_require__(/*! ./modules/es6.math.trunc */ 1026);
	__webpack_require__(/*! ./modules/es6.string.from-code-point */ 1027);
	__webpack_require__(/*! ./modules/es6.string.raw */ 1028);
	__webpack_require__(/*! ./modules/es6.string.trim */ 1029);
	__webpack_require__(/*! ./modules/es6.string.iterator */ 1030);
	__webpack_require__(/*! ./modules/es6.string.code-point-at */ 1035);
	__webpack_require__(/*! ./modules/es6.string.ends-with */ 1036);
	__webpack_require__(/*! ./modules/es6.string.includes */ 1040);
	__webpack_require__(/*! ./modules/es6.string.repeat */ 1041);
	__webpack_require__(/*! ./modules/es6.string.starts-with */ 1042);
	__webpack_require__(/*! ./modules/es6.string.anchor */ 1043);
	__webpack_require__(/*! ./modules/es6.string.big */ 1045);
	__webpack_require__(/*! ./modules/es6.string.blink */ 1046);
	__webpack_require__(/*! ./modules/es6.string.bold */ 1047);
	__webpack_require__(/*! ./modules/es6.string.fixed */ 1048);
	__webpack_require__(/*! ./modules/es6.string.fontcolor */ 1049);
	__webpack_require__(/*! ./modules/es6.string.fontsize */ 1050);
	__webpack_require__(/*! ./modules/es6.string.italics */ 1051);
	__webpack_require__(/*! ./modules/es6.string.link */ 1052);
	__webpack_require__(/*! ./modules/es6.string.small */ 1053);
	__webpack_require__(/*! ./modules/es6.string.strike */ 1054);
	__webpack_require__(/*! ./modules/es6.string.sub */ 1055);
	__webpack_require__(/*! ./modules/es6.string.sup */ 1056);
	__webpack_require__(/*! ./modules/es6.date.now */ 1057);
	__webpack_require__(/*! ./modules/es6.date.to-json */ 1058);
	__webpack_require__(/*! ./modules/es6.date.to-iso-string */ 1059);
	__webpack_require__(/*! ./modules/es6.date.to-string */ 1060);
	__webpack_require__(/*! ./modules/es6.date.to-primitive */ 1061);
	__webpack_require__(/*! ./modules/es6.array.is-array */ 1063);
	__webpack_require__(/*! ./modules/es6.array.from */ 1064);
	__webpack_require__(/*! ./modules/es6.array.of */ 1070);
	__webpack_require__(/*! ./modules/es6.array.join */ 1071);
	__webpack_require__(/*! ./modules/es6.array.slice */ 1073);
	__webpack_require__(/*! ./modules/es6.array.sort */ 1074);
	__webpack_require__(/*! ./modules/es6.array.for-each */ 1075);
	__webpack_require__(/*! ./modules/es6.array.map */ 1079);
	__webpack_require__(/*! ./modules/es6.array.filter */ 1080);
	__webpack_require__(/*! ./modules/es6.array.some */ 1081);
	__webpack_require__(/*! ./modules/es6.array.every */ 1082);
	__webpack_require__(/*! ./modules/es6.array.reduce */ 1083);
	__webpack_require__(/*! ./modules/es6.array.reduce-right */ 1085);
	__webpack_require__(/*! ./modules/es6.array.index-of */ 1086);
	__webpack_require__(/*! ./modules/es6.array.last-index-of */ 1087);
	__webpack_require__(/*! ./modules/es6.array.copy-within */ 1088);
	__webpack_require__(/*! ./modules/es6.array.fill */ 1091);
	__webpack_require__(/*! ./modules/es6.array.find */ 1093);
	__webpack_require__(/*! ./modules/es6.array.find-index */ 1094);
	__webpack_require__(/*! ./modules/es6.array.species */ 1095);
	__webpack_require__(/*! ./modules/es6.array.iterator */ 1097);
	__webpack_require__(/*! ./modules/es6.regexp.constructor */ 1099);
	__webpack_require__(/*! ./modules/es6.regexp.to-string */ 1101);
	__webpack_require__(/*! ./modules/es6.regexp.flags */ 1102);
	__webpack_require__(/*! ./modules/es6.regexp.match */ 1103);
	__webpack_require__(/*! ./modules/es6.regexp.replace */ 1105);
	__webpack_require__(/*! ./modules/es6.regexp.search */ 1106);
	__webpack_require__(/*! ./modules/es6.regexp.split */ 1107);
	__webpack_require__(/*! ./modules/es6.promise */ 1108);
	__webpack_require__(/*! ./modules/es6.map */ 1115);
	__webpack_require__(/*! ./modules/es6.set */ 1118);
	__webpack_require__(/*! ./modules/es6.weak-map */ 1119);
	__webpack_require__(/*! ./modules/es6.weak-set */ 1121);
	__webpack_require__(/*! ./modules/es6.typed.array-buffer */ 1122);
	__webpack_require__(/*! ./modules/es6.typed.data-view */ 1125);
	__webpack_require__(/*! ./modules/es6.typed.int8-array */ 1126);
	__webpack_require__(/*! ./modules/es6.typed.uint8-array */ 1128);
	__webpack_require__(/*! ./modules/es6.typed.uint8-clamped-array */ 1129);
	__webpack_require__(/*! ./modules/es6.typed.int16-array */ 1130);
	__webpack_require__(/*! ./modules/es6.typed.uint16-array */ 1131);
	__webpack_require__(/*! ./modules/es6.typed.int32-array */ 1132);
	__webpack_require__(/*! ./modules/es6.typed.uint32-array */ 1133);
	__webpack_require__(/*! ./modules/es6.typed.float32-array */ 1134);
	__webpack_require__(/*! ./modules/es6.typed.float64-array */ 1135);
	__webpack_require__(/*! ./modules/es6.reflect.apply */ 1136);
	__webpack_require__(/*! ./modules/es6.reflect.construct */ 1137);
	__webpack_require__(/*! ./modules/es6.reflect.define-property */ 1138);
	__webpack_require__(/*! ./modules/es6.reflect.delete-property */ 1139);
	__webpack_require__(/*! ./modules/es6.reflect.enumerate */ 1140);
	__webpack_require__(/*! ./modules/es6.reflect.get */ 1141);
	__webpack_require__(/*! ./modules/es6.reflect.get-own-property-descriptor */ 1142);
	__webpack_require__(/*! ./modules/es6.reflect.get-prototype-of */ 1143);
	__webpack_require__(/*! ./modules/es6.reflect.has */ 1144);
	__webpack_require__(/*! ./modules/es6.reflect.is-extensible */ 1145);
	__webpack_require__(/*! ./modules/es6.reflect.own-keys */ 1146);
	__webpack_require__(/*! ./modules/es6.reflect.prevent-extensions */ 1148);
	__webpack_require__(/*! ./modules/es6.reflect.set */ 1149);
	__webpack_require__(/*! ./modules/es6.reflect.set-prototype-of */ 1150);
	__webpack_require__(/*! ./modules/es7.array.includes */ 1151);
	__webpack_require__(/*! ./modules/es7.string.at */ 1152);
	__webpack_require__(/*! ./modules/es7.string.pad-start */ 1153);
	__webpack_require__(/*! ./modules/es7.string.pad-end */ 1155);
	__webpack_require__(/*! ./modules/es7.string.trim-left */ 1156);
	__webpack_require__(/*! ./modules/es7.string.trim-right */ 1157);
	__webpack_require__(/*! ./modules/es7.string.match-all */ 1158);
	__webpack_require__(/*! ./modules/es7.symbol.async-iterator */ 1159);
	__webpack_require__(/*! ./modules/es7.symbol.observable */ 1160);
	__webpack_require__(/*! ./modules/es7.object.get-own-property-descriptors */ 1161);
	__webpack_require__(/*! ./modules/es7.object.values */ 1162);
	__webpack_require__(/*! ./modules/es7.object.entries */ 1164);
	__webpack_require__(/*! ./modules/es7.object.define-getter */ 1165);
	__webpack_require__(/*! ./modules/es7.object.define-setter */ 1167);
	__webpack_require__(/*! ./modules/es7.object.lookup-getter */ 1168);
	__webpack_require__(/*! ./modules/es7.object.lookup-setter */ 1169);
	__webpack_require__(/*! ./modules/es7.map.to-json */ 1170);
	__webpack_require__(/*! ./modules/es7.set.to-json */ 1173);
	__webpack_require__(/*! ./modules/es7.system.global */ 1174);
	__webpack_require__(/*! ./modules/es7.error.is-error */ 1175);
	__webpack_require__(/*! ./modules/es7.math.iaddh */ 1176);
	__webpack_require__(/*! ./modules/es7.math.isubh */ 1177);
	__webpack_require__(/*! ./modules/es7.math.imulh */ 1178);
	__webpack_require__(/*! ./modules/es7.math.umulh */ 1179);
	__webpack_require__(/*! ./modules/es7.reflect.define-metadata */ 1180);
	__webpack_require__(/*! ./modules/es7.reflect.delete-metadata */ 1182);
	__webpack_require__(/*! ./modules/es7.reflect.get-metadata */ 1183);
	__webpack_require__(/*! ./modules/es7.reflect.get-metadata-keys */ 1184);
	__webpack_require__(/*! ./modules/es7.reflect.get-own-metadata */ 1185);
	__webpack_require__(/*! ./modules/es7.reflect.get-own-metadata-keys */ 1186);
	__webpack_require__(/*! ./modules/es7.reflect.has-metadata */ 1187);
	__webpack_require__(/*! ./modules/es7.reflect.has-own-metadata */ 1188);
	__webpack_require__(/*! ./modules/es7.reflect.metadata */ 1189);
	__webpack_require__(/*! ./modules/es7.asap */ 1190);
	__webpack_require__(/*! ./modules/es7.observable */ 1191);
	__webpack_require__(/*! ./modules/web.timers */ 1192);
	__webpack_require__(/*! ./modules/web.immediate */ 1195);
	__webpack_require__(/*! ./modules/web.dom.iterable */ 1196);
	module.exports = __webpack_require__(/*! ./modules/_core */ 913);

/***/ },

/***/ 907:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.symbol.js ***!
  \**********************************************************/
[4797, 908, 909, 910, 912, 922, 926, 911, 927, 928, 923, 929, 930, 931, 933, 946, 949, 916, 936, 920, 921, 950, 953, 955, 915, 934, 954, 948, 947, 932, 914],

/***/ 908:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_global.js ***!
  \*******************************************************/
393,

/***/ 909:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_has.js ***!
  \****************************************************/
680,

/***/ 910:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_descriptors.js ***!
  \************************************************************/
[4750, 911],

/***/ 911:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fails.js ***!
  \******************************************************/
403,

/***/ 912:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_export.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 908)
	  , core      = __webpack_require__(/*! ./_core */ 913)
	  , hide      = __webpack_require__(/*! ./_hide */ 914)
	  , redefine  = __webpack_require__(/*! ./_redefine */ 922)
	  , ctx       = __webpack_require__(/*! ./_ctx */ 924)
	  , PROTOTYPE = 'prototype';
	
	var $export = function(type, name, source){
	  var IS_FORCED = type & $export.F
	    , IS_GLOBAL = type & $export.G
	    , IS_STATIC = type & $export.S
	    , IS_PROTO  = type & $export.P
	    , IS_BIND   = type & $export.B
	    , target    = IS_GLOBAL ? global : IS_STATIC ? global[name] || (global[name] = {}) : (global[name] || {})[PROTOTYPE]
	    , exports   = IS_GLOBAL ? core : core[name] || (core[name] = {})
	    , expProto  = exports[PROTOTYPE] || (exports[PROTOTYPE] = {})
	    , key, own, out, exp;
	  if(IS_GLOBAL)source = name;
	  for(key in source){
	    // contains in native
	    own = !IS_FORCED && target && target[key] !== undefined;
	    // export native or passed
	    out = (own ? target : source)[key];
	    // bind timers to global for call from export context
	    exp = IS_BIND && own ? ctx(out, global) : IS_PROTO && typeof out == 'function' ? ctx(Function.call, out) : out;
	    // extend global
	    if(target)redefine(target, key, out, type & $export.U);
	    // export
	    if(exports[key] != out)hide(exports, key, exp);
	    if(IS_PROTO && expProto[key] != out)expProto[key] = out;
	  }
	};
	global.core = core;
	// type bitmap
	$export.F = 1;   // forced
	$export.G = 2;   // global
	$export.S = 4;   // static
	$export.P = 8;   // proto
	$export.B = 16;  // bind
	$export.W = 32;  // wrap
	$export.U = 64;  // safe
	$export.R = 128; // real proto method for `library` 
	module.exports = $export;

/***/ },

/***/ 913:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_core.js ***!
  \*****************************************************/
657,

/***/ 914:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_hide.js ***!
  \*****************************************************/
[4746, 915, 921, 910],

/***/ 915:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-dp.js ***!
  \**********************************************************/
[4747, 916, 918, 920, 910],

/***/ 916:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_an-object.js ***!
  \**********************************************************/
[4748, 917],

/***/ 917:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-object.js ***!
  \**********************************************************/
428,

/***/ 918:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_ie8-dom-define.js ***!
  \***************************************************************/
[4749, 910, 911, 919],

/***/ 919:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_dom-create.js ***!
  \***********************************************************/
[4751, 917, 908],

/***/ 920:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-primitive.js ***!
  \*************************************************************/
[4752, 917],

/***/ 921:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_property-desc.js ***!
  \**************************************************************/
669,

/***/ 922:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_redefine.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 908)
	  , hide      = __webpack_require__(/*! ./_hide */ 914)
	  , has       = __webpack_require__(/*! ./_has */ 909)
	  , SRC       = __webpack_require__(/*! ./_uid */ 923)('src')
	  , TO_STRING = 'toString'
	  , $toString = Function[TO_STRING]
	  , TPL       = ('' + $toString).split(TO_STRING);
	
	__webpack_require__(/*! ./_core */ 913).inspectSource = function(it){
	  return $toString.call(it);
	};
	
	(module.exports = function(O, key, val, safe){
	  var isFunction = typeof val == 'function';
	  if(isFunction)has(val, 'name') || hide(val, 'name', key);
	  if(O[key] === val)return;
	  if(isFunction)has(val, SRC) || hide(val, SRC, O[key] ? '' + O[key] : TPL.join(String(key)));
	  if(O === global){
	    O[key] = val;
	  } else {
	    if(!safe){
	      delete O[key];
	      hide(O, key, val);
	    } else {
	      if(O[key])O[key] = val;
	      else hide(O, key, val);
	    }
	  }
	// add fake Function#toString for correct work wrapped methods / constructors with methods like LoDash isNative
	})(Function.prototype, TO_STRING, function toString(){
	  return typeof this == 'function' && this[SRC] || $toString.call(this);
	});

/***/ },

/***/ 923:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_uid.js ***!
  \****************************************************/
695,

/***/ 924:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_ctx.js ***!
  \****************************************************/
[4745, 925],

/***/ 925:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_a-function.js ***!
  \***********************************************************/
396,

/***/ 926:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_meta.js ***!
  \*****************************************************/
[4798, 923, 917, 909, 915, 911],

/***/ 927:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_shared.js ***!
  \*******************************************************/
[4771, 908],

/***/ 928:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-to-string-tag.js ***!
  \******************************************************************/
[4773, 915, 909, 929],

/***/ 929:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks.js ***!
  \****************************************************/
[4774, 927, 923, 908],

/***/ 930:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks-ext.js ***!
  \********************************************************/
[4794, 929],

/***/ 931:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks-define.js ***!
  \***********************************************************/
[4799, 908, 913, 932, 930, 915],

/***/ 932:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_library.js ***!
  \********************************************************/
/***/ function(module, exports) {

	module.exports = false;

/***/ },

/***/ 933:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_keyof.js ***!
  \******************************************************/
[4800, 934, 936],

/***/ 934:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-keys.js ***!
  \************************************************************/
[4763, 935, 945],

/***/ 935:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-keys-internal.js ***!
  \*********************************************************************/
[4764, 909, 936, 940, 944],

/***/ 936:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-iobject.js ***!
  \***********************************************************/
[4765, 937, 939],

/***/ 937:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iobject.js ***!
  \********************************************************/
[4766, 938],

/***/ 938:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_cof.js ***!
  \****************************************************/
402,

/***/ 939:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_defined.js ***!
  \********************************************************/
400,

/***/ 940:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-includes.js ***!
  \***************************************************************/
[4767, 936, 941, 943],

/***/ 941:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-length.js ***!
  \**********************************************************/
[4768, 942],

/***/ 942:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-integer.js ***!
  \***********************************************************/
675,

/***/ 943:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-index.js ***!
  \*********************************************************/
[4769, 942],

/***/ 944:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_shared-key.js ***!
  \***********************************************************/
[4770, 927, 923],

/***/ 945:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_enum-bug-keys.js ***!
  \**************************************************************/
696,

/***/ 946:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_enum-keys.js ***!
  \**********************************************************/
[4801, 934, 947, 948],

/***/ 947:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gops.js ***!
  \************************************************************/
714,

/***/ 948:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-pie.js ***!
  \***********************************************************/
715,

/***/ 949:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-array.js ***!
  \*********************************************************/
[4802, 938],

/***/ 950:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-create.js ***!
  \**************************************************************/
[4761, 916, 951, 945, 944, 919, 952],

/***/ 951:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-dps.js ***!
  \***********************************************************/
[4762, 915, 916, 934, 910],

/***/ 952:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_html.js ***!
  \*****************************************************/
[4772, 908],

/***/ 953:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopn-ext.js ***!
  \****************************************************************/
[4803, 936, 954],

/***/ 954:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopn.js ***!
  \************************************************************/
[4804, 935, 945],

/***/ 955:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopd.js ***!
  \************************************************************/
[4805, 948, 921, 936, 920, 909, 918, 910],

/***/ 956:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.create.js ***!
  \*****************************************************************/
[4813, 912, 950],

/***/ 957:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.define-property.js ***!
  \**************************************************************************/
[4743, 912, 910, 915],

/***/ 958:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.define-properties.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912);
	// 19.1.2.3 / 15.2.3.7 Object.defineProperties(O, Properties)
	$export($export.S + $export.F * !__webpack_require__(/*! ./_descriptors */ 910), 'Object', {defineProperties: __webpack_require__(/*! ./_object-dps */ 951)});

/***/ },

/***/ 959:
/*!**************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-own-property-descriptor.js ***!
  \**************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.6 Object.getOwnPropertyDescriptor(O, P)
	var toIObject                 = __webpack_require__(/*! ./_to-iobject */ 936)
	  , $getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 955).f;
	
	__webpack_require__(/*! ./_object-sap */ 960)('getOwnPropertyDescriptor', function(){
	  return function getOwnPropertyDescriptor(it, key){
	    return $getOwnPropertyDescriptor(toIObject(it), key);
	  };
	});

/***/ },

/***/ 960:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-sap.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// most Object methods by ES6 should accept primitives
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , core    = __webpack_require__(/*! ./_core */ 913)
	  , fails   = __webpack_require__(/*! ./_fails */ 911);
	module.exports = function(KEY, exec){
	  var fn  = (core.Object || {})[KEY] || Object[KEY]
	    , exp = {};
	  exp[KEY] = exec(fn);
	  $export($export.S + $export.F * fails(function(){ fn(1); }), 'Object', exp);
	};

/***/ },

/***/ 961:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-prototype-of.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.9 Object.getPrototypeOf(O)
	var toObject        = __webpack_require__(/*! ./_to-object */ 962)
	  , $getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 963);
	
	__webpack_require__(/*! ./_object-sap */ 960)('getPrototypeOf', function(){
	  return function getPrototypeOf(it){
	    return $getPrototypeOf(toObject(it));
	  };
	});

/***/ },

/***/ 962:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-object.js ***!
  \**********************************************************/
[4776, 939],

/***/ 963:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gpo.js ***!
  \***********************************************************/
[4775, 909, 962, 944],

/***/ 964:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.keys.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 Object.keys(O)
	var toObject = __webpack_require__(/*! ./_to-object */ 962)
	  , $keys    = __webpack_require__(/*! ./_object-keys */ 934);
	
	__webpack_require__(/*! ./_object-sap */ 960)('keys', function(){
	  return function keys(it){
	    return $keys(toObject(it));
	  };
	});

/***/ },

/***/ 965:
/*!*********************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-own-property-names.js ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.7 Object.getOwnPropertyNames(O)
	__webpack_require__(/*! ./_object-sap */ 960)('getOwnPropertyNames', function(){
	  return __webpack_require__(/*! ./_object-gopn-ext */ 953).f;
	});

/***/ },

/***/ 966:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.freeze.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.5 Object.freeze(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , meta     = __webpack_require__(/*! ./_meta */ 926).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 960)('freeze', function($freeze){
	  return function freeze(it){
	    return $freeze && isObject(it) ? $freeze(meta(it)) : it;
	  };
	});

/***/ },

/***/ 967:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.seal.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.17 Object.seal(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , meta     = __webpack_require__(/*! ./_meta */ 926).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 960)('seal', function($seal){
	  return function seal(it){
	    return $seal && isObject(it) ? $seal(meta(it)) : it;
	  };
	});

/***/ },

/***/ 968:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.prevent-extensions.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.15 Object.preventExtensions(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , meta     = __webpack_require__(/*! ./_meta */ 926).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 960)('preventExtensions', function($preventExtensions){
	  return function preventExtensions(it){
	    return $preventExtensions && isObject(it) ? $preventExtensions(meta(it)) : it;
	  };
	});

/***/ },

/***/ 969:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-frozen.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.12 Object.isFrozen(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917);
	
	__webpack_require__(/*! ./_object-sap */ 960)('isFrozen', function($isFrozen){
	  return function isFrozen(it){
	    return isObject(it) ? $isFrozen ? $isFrozen(it) : false : true;
	  };
	});

/***/ },

/***/ 970:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-sealed.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.13 Object.isSealed(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917);
	
	__webpack_require__(/*! ./_object-sap */ 960)('isSealed', function($isSealed){
	  return function isSealed(it){
	    return isObject(it) ? $isSealed ? $isSealed(it) : false : true;
	  };
	});

/***/ },

/***/ 971:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-extensible.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.11 Object.isExtensible(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 917);
	
	__webpack_require__(/*! ./_object-sap */ 960)('isExtensible', function($isExtensible){
	  return function isExtensible(it){
	    return isObject(it) ? $isExtensible ? $isExtensible(it) : true : false;
	  };
	});

/***/ },

/***/ 972:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.assign.js ***!
  \*****************************************************************/
[4786, 912, 973],

/***/ 973:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-assign.js ***!
  \**************************************************************/
[4787, 934, 947, 948, 962, 937, 911],

/***/ 974:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.10 Object.is(value1, value2)
	var $export = __webpack_require__(/*! ./_export */ 912);
	$export($export.S, 'Object', {is: __webpack_require__(/*! ./_same-value */ 975)});

/***/ },

/***/ 975:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_same-value.js ***!
  \***********************************************************/
/***/ function(module, exports) {

	// 7.2.9 SameValue(x, y)
	module.exports = Object.is || function is(x, y){
	  return x === y ? x !== 0 || 1 / x === 1 / y : x != x && y != y;
	};

/***/ },

/***/ 976:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.set-prototype-of.js ***!
  \***************************************************************************/
[4810, 912, 977],

/***/ 977:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-proto.js ***!
  \**********************************************************/
[4811, 917, 916, 924, 955],

/***/ 978:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.to-string.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.3.6 Object.prototype.toString()
	var classof = __webpack_require__(/*! ./_classof */ 979)
	  , test    = {};
	test[__webpack_require__(/*! ./_wks */ 929)('toStringTag')] = 'z';
	if(test + '' != '[object z]'){
	  __webpack_require__(/*! ./_redefine */ 922)(Object.prototype, 'toString', function toString(){
	    return '[object ' + classof(this) + ']';
	  }, true);
	}

/***/ },

/***/ 979:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_classof.js ***!
  \********************************************************/
[4782, 938, 929],

/***/ 980:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.bind.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.2.3.2 / 15.3.4.5 Function.prototype.bind(thisArg, args...)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P, 'Function', {bind: __webpack_require__(/*! ./_bind */ 981)});

/***/ },

/***/ 981:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_bind.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var aFunction  = __webpack_require__(/*! ./_a-function */ 925)
	  , isObject   = __webpack_require__(/*! ./_is-object */ 917)
	  , invoke     = __webpack_require__(/*! ./_invoke */ 982)
	  , arraySlice = [].slice
	  , factories  = {};
	
	var construct = function(F, len, args){
	  if(!(len in factories)){
	    for(var n = [], i = 0; i < len; i++)n[i] = 'a[' + i + ']';
	    factories[len] = Function('F,a', 'return new F(' + n.join(',') + ')');
	  } return factories[len](F, args);
	};
	
	module.exports = Function.bind || function bind(that /*, args... */){
	  var fn       = aFunction(this)
	    , partArgs = arraySlice.call(arguments, 1);
	  var bound = function(/* args... */){
	    var args = partArgs.concat(arraySlice.call(arguments));
	    return this instanceof bound ? construct(fn, args.length, args) : invoke(fn, args, that);
	  };
	  if(isObject(fn.prototype))bound.prototype = fn.prototype;
	  return bound;
	};

/***/ },

/***/ 982:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_invoke.js ***!
  \*******************************************************/
/***/ function(module, exports) {

	// fast apply, http://jsperf.lnkit.com/fast-apply/5
	module.exports = function(fn, args, that){
	  var un = that === undefined;
	  switch(args.length){
	    case 0: return un ? fn()
	                      : fn.call(that);
	    case 1: return un ? fn(args[0])
	                      : fn.call(that, args[0]);
	    case 2: return un ? fn(args[0], args[1])
	                      : fn.call(that, args[0], args[1]);
	    case 3: return un ? fn(args[0], args[1], args[2])
	                      : fn.call(that, args[0], args[1], args[2]);
	    case 4: return un ? fn(args[0], args[1], args[2], args[3])
	                      : fn.call(that, args[0], args[1], args[2], args[3]);
	  } return              fn.apply(that, args);
	};

/***/ },

/***/ 983:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.name.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(/*! ./_object-dp */ 915).f
	  , createDesc = __webpack_require__(/*! ./_property-desc */ 921)
	  , has        = __webpack_require__(/*! ./_has */ 909)
	  , FProto     = Function.prototype
	  , nameRE     = /^\s*function ([^ (]*)/
	  , NAME       = 'name';
	
	var isExtensible = Object.isExtensible || function(){
	  return true;
	};
	
	// 19.2.4.2 name
	NAME in FProto || __webpack_require__(/*! ./_descriptors */ 910) && dP(FProto, NAME, {
	  configurable: true,
	  get: function(){
	    try {
	      var that = this
	        , name = ('' + that).match(nameRE)[1];
	      has(that, NAME) || !isExtensible(that) || dP(that, NAME, createDesc(5, name));
	      return name;
	    } catch(e){
	      return '';
	    }
	  }
	});

/***/ },

/***/ 984:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.has-instance.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var isObject       = __webpack_require__(/*! ./_is-object */ 917)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 963)
	  , HAS_INSTANCE   = __webpack_require__(/*! ./_wks */ 929)('hasInstance')
	  , FunctionProto  = Function.prototype;
	// 19.2.3.6 Function.prototype[@@hasInstance](V)
	if(!(HAS_INSTANCE in FunctionProto))__webpack_require__(/*! ./_object-dp */ 915).f(FunctionProto, HAS_INSTANCE, {value: function(O){
	  if(typeof this != 'function' || !isObject(O))return false;
	  if(!isObject(this.prototype))return O instanceof this;
	  // for environment w/o native `@@hasInstance` logic enough `instanceof`, but add this:
	  while(O = getPrototypeOf(O))if(this.prototype === O)return true;
	  return false;
	}});

/***/ },

/***/ 985:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.parse-int.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , $parseInt = __webpack_require__(/*! ./_parse-int */ 986);
	// 18.2.5 parseInt(string, radix)
	$export($export.G + $export.F * (parseInt != $parseInt), {parseInt: $parseInt});

/***/ },

/***/ 986:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_parse-int.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $parseInt = __webpack_require__(/*! ./_global */ 908).parseInt
	  , $trim     = __webpack_require__(/*! ./_string-trim */ 987).trim
	  , ws        = __webpack_require__(/*! ./_string-ws */ 988)
	  , hex       = /^[\-+]?0[xX]/;
	
	module.exports = $parseInt(ws + '08') !== 8 || $parseInt(ws + '0x16') !== 22 ? function parseInt(str, radix){
	  var string = $trim(String(str), 3);
	  return $parseInt(string, (radix >>> 0) || (hex.test(string) ? 16 : 10));
	} : $parseInt;

/***/ },

/***/ 987:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-trim.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912)
	  , defined = __webpack_require__(/*! ./_defined */ 939)
	  , fails   = __webpack_require__(/*! ./_fails */ 911)
	  , spaces  = __webpack_require__(/*! ./_string-ws */ 988)
	  , space   = '[' + spaces + ']'
	  , non     = '\u200b\u0085'
	  , ltrim   = RegExp('^' + space + space + '*')
	  , rtrim   = RegExp(space + space + '*$');
	
	var exporter = function(KEY, exec, ALIAS){
	  var exp   = {};
	  var FORCE = fails(function(){
	    return !!spaces[KEY]() || non[KEY]() != non;
	  });
	  var fn = exp[KEY] = FORCE ? exec(trim) : spaces[KEY];
	  if(ALIAS)exp[ALIAS] = fn;
	  $export($export.P + $export.F * FORCE, 'String', exp);
	};
	
	// 1 -> String#trimLeft
	// 2 -> String#trimRight
	// 3 -> String#trim
	var trim = exporter.trim = function(string, TYPE){
	  string = String(defined(string));
	  if(TYPE & 1)string = string.replace(ltrim, '');
	  if(TYPE & 2)string = string.replace(rtrim, '');
	  return string;
	};
	
	module.exports = exporter;

/***/ },

/***/ 988:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-ws.js ***!
  \**********************************************************/
/***/ function(module, exports) {

	module.exports = '\x09\x0A\x0B\x0C\x0D\x20\xA0\u1680\u180E\u2000\u2001\u2002\u2003' +
	  '\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000\u2028\u2029\uFEFF';

/***/ },

/***/ 989:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.parse-float.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , $parseFloat = __webpack_require__(/*! ./_parse-float */ 990);
	// 18.2.4 parseFloat(string)
	$export($export.G + $export.F * (parseFloat != $parseFloat), {parseFloat: $parseFloat});

/***/ },

/***/ 990:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_parse-float.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $parseFloat = __webpack_require__(/*! ./_global */ 908).parseFloat
	  , $trim       = __webpack_require__(/*! ./_string-trim */ 987).trim;
	
	module.exports = 1 / $parseFloat(__webpack_require__(/*! ./_string-ws */ 988) + '-0') !== -Infinity ? function parseFloat(str){
	  var string = $trim(String(str), 3)
	    , result = $parseFloat(string);
	  return result === 0 && string.charAt(0) == '-' ? -0 : result;
	} : $parseFloat;

/***/ },

/***/ 991:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.constructor.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global            = __webpack_require__(/*! ./_global */ 908)
	  , has               = __webpack_require__(/*! ./_has */ 909)
	  , cof               = __webpack_require__(/*! ./_cof */ 938)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 992)
	  , toPrimitive       = __webpack_require__(/*! ./_to-primitive */ 920)
	  , fails             = __webpack_require__(/*! ./_fails */ 911)
	  , gOPN              = __webpack_require__(/*! ./_object-gopn */ 954).f
	  , gOPD              = __webpack_require__(/*! ./_object-gopd */ 955).f
	  , dP                = __webpack_require__(/*! ./_object-dp */ 915).f
	  , $trim             = __webpack_require__(/*! ./_string-trim */ 987).trim
	  , NUMBER            = 'Number'
	  , $Number           = global[NUMBER]
	  , Base              = $Number
	  , proto             = $Number.prototype
	  // Opera ~12 has broken Object#toString
	  , BROKEN_COF        = cof(__webpack_require__(/*! ./_object-create */ 950)(proto)) == NUMBER
	  , TRIM              = 'trim' in String.prototype;
	
	// 7.1.3 ToNumber(argument)
	var toNumber = function(argument){
	  var it = toPrimitive(argument, false);
	  if(typeof it == 'string' && it.length > 2){
	    it = TRIM ? it.trim() : $trim(it, 3);
	    var first = it.charCodeAt(0)
	      , third, radix, maxCode;
	    if(first === 43 || first === 45){
	      third = it.charCodeAt(2);
	      if(third === 88 || third === 120)return NaN; // Number('+0x1') should be NaN, old V8 fix
	    } else if(first === 48){
	      switch(it.charCodeAt(1)){
	        case 66 : case 98  : radix = 2; maxCode = 49; break; // fast equal /^0b[01]+$/i
	        case 79 : case 111 : radix = 8; maxCode = 55; break; // fast equal /^0o[0-7]+$/i
	        default : return +it;
	      }
	      for(var digits = it.slice(2), i = 0, l = digits.length, code; i < l; i++){
	        code = digits.charCodeAt(i);
	        // parseInt parses a string to a first unavailable symbol
	        // but ToNumber should return NaN if a string contains unavailable symbols
	        if(code < 48 || code > maxCode)return NaN;
	      } return parseInt(digits, radix);
	    }
	  } return +it;
	};
	
	if(!$Number(' 0o1') || !$Number('0b1') || $Number('+0x1')){
	  $Number = function Number(value){
	    var it = arguments.length < 1 ? 0 : value
	      , that = this;
	    return that instanceof $Number
	      // check on 1..constructor(foo) case
	      && (BROKEN_COF ? fails(function(){ proto.valueOf.call(that); }) : cof(that) != NUMBER)
	        ? inheritIfRequired(new Base(toNumber(it)), that, $Number) : toNumber(it);
	  };
	  for(var keys = __webpack_require__(/*! ./_descriptors */ 910) ? gOPN(Base) : (
	    // ES3:
	    'MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,' +
	    // ES6 (in case, if modules with ES6 Number statics required before):
	    'EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,' +
	    'MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger'
	  ).split(','), j = 0, key; keys.length > j; j++){
	    if(has(Base, key = keys[j]) && !has($Number, key)){
	      dP($Number, key, gOPD(Base, key));
	    }
	  }
	  $Number.prototype = proto;
	  proto.constructor = $Number;
	  __webpack_require__(/*! ./_redefine */ 922)(global, NUMBER, $Number);
	}

/***/ },

/***/ 992:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_inherit-if-required.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var isObject       = __webpack_require__(/*! ./_is-object */ 917)
	  , setPrototypeOf = __webpack_require__(/*! ./_set-proto */ 977).set;
	module.exports = function(that, target, C){
	  var P, S = target.constructor;
	  if(S !== C && typeof S == 'function' && (P = S.prototype) !== C.prototype && isObject(P) && setPrototypeOf){
	    setPrototypeOf(that, P);
	  } return that;
	};

/***/ },

/***/ 993:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.to-fixed.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 912)
	  , toInteger    = __webpack_require__(/*! ./_to-integer */ 942)
	  , aNumberValue = __webpack_require__(/*! ./_a-number-value */ 994)
	  , repeat       = __webpack_require__(/*! ./_string-repeat */ 995)
	  , $toFixed     = 1..toFixed
	  , floor        = Math.floor
	  , data         = [0, 0, 0, 0, 0, 0]
	  , ERROR        = 'Number.toFixed: incorrect invocation!'
	  , ZERO         = '0';
	
	var multiply = function(n, c){
	  var i  = -1
	    , c2 = c;
	  while(++i < 6){
	    c2 += n * data[i];
	    data[i] = c2 % 1e7;
	    c2 = floor(c2 / 1e7);
	  }
	};
	var divide = function(n){
	  var i = 6
	    , c = 0;
	  while(--i >= 0){
	    c += data[i];
	    data[i] = floor(c / n);
	    c = (c % n) * 1e7;
	  }
	};
	var numToString = function(){
	  var i = 6
	    , s = '';
	  while(--i >= 0){
	    if(s !== '' || i === 0 || data[i] !== 0){
	      var t = String(data[i]);
	      s = s === '' ? t : s + repeat.call(ZERO, 7 - t.length) + t;
	    }
	  } return s;
	};
	var pow = function(x, n, acc){
	  return n === 0 ? acc : n % 2 === 1 ? pow(x, n - 1, acc * x) : pow(x * x, n / 2, acc);
	};
	var log = function(x){
	  var n  = 0
	    , x2 = x;
	  while(x2 >= 4096){
	    n += 12;
	    x2 /= 4096;
	  }
	  while(x2 >= 2){
	    n  += 1;
	    x2 /= 2;
	  } return n;
	};
	
	$export($export.P + $export.F * (!!$toFixed && (
	  0.00008.toFixed(3) !== '0.000' ||
	  0.9.toFixed(0) !== '1' ||
	  1.255.toFixed(2) !== '1.25' ||
	  1000000000000000128..toFixed(0) !== '1000000000000000128'
	) || !__webpack_require__(/*! ./_fails */ 911)(function(){
	  // V8 ~ Android 4.3-
	  $toFixed.call({});
	})), 'Number', {
	  toFixed: function toFixed(fractionDigits){
	    var x = aNumberValue(this, ERROR)
	      , f = toInteger(fractionDigits)
	      , s = ''
	      , m = ZERO
	      , e, z, j, k;
	    if(f < 0 || f > 20)throw RangeError(ERROR);
	    if(x != x)return 'NaN';
	    if(x <= -1e21 || x >= 1e21)return String(x);
	    if(x < 0){
	      s = '-';
	      x = -x;
	    }
	    if(x > 1e-21){
	      e = log(x * pow(2, 69, 1)) - 69;
	      z = e < 0 ? x * pow(2, -e, 1) : x / pow(2, e, 1);
	      z *= 0x10000000000000;
	      e = 52 - e;
	      if(e > 0){
	        multiply(0, z);
	        j = f;
	        while(j >= 7){
	          multiply(1e7, 0);
	          j -= 7;
	        }
	        multiply(pow(10, j, 1), 0);
	        j = e - 1;
	        while(j >= 23){
	          divide(1 << 23);
	          j -= 23;
	        }
	        divide(1 << j);
	        multiply(1, 1);
	        divide(2);
	        m = numToString();
	      } else {
	        multiply(0, z);
	        multiply(1 << -e, 0);
	        m = numToString() + repeat.call(ZERO, f);
	      }
	    }
	    if(f > 0){
	      k = m.length;
	      m = s + (k <= f ? '0.' + repeat.call(ZERO, f - k) + m : m.slice(0, k - f) + '.' + m.slice(k - f));
	    } else {
	      m = s + m;
	    } return m;
	  }
	});

/***/ },

/***/ 994:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_a-number-value.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var cof = __webpack_require__(/*! ./_cof */ 938);
	module.exports = function(it, msg){
	  if(typeof it != 'number' && cof(it) != 'Number')throw TypeError(msg);
	  return +it;
	};

/***/ },

/***/ 995:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-repeat.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var toInteger = __webpack_require__(/*! ./_to-integer */ 942)
	  , defined   = __webpack_require__(/*! ./_defined */ 939);
	
	module.exports = function repeat(count){
	  var str = String(defined(this))
	    , res = ''
	    , n   = toInteger(count);
	  if(n < 0 || n == Infinity)throw RangeError("Count can't be negative");
	  for(;n > 0; (n >>>= 1) && (str += str))if(n & 1)res += str;
	  return res;
	};

/***/ },

/***/ 996:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.to-precision.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 912)
	  , $fails       = __webpack_require__(/*! ./_fails */ 911)
	  , aNumberValue = __webpack_require__(/*! ./_a-number-value */ 994)
	  , $toPrecision = 1..toPrecision;
	
	$export($export.P + $export.F * ($fails(function(){
	  // IE7-
	  return $toPrecision.call(1, undefined) !== '1';
	}) || !$fails(function(){
	  // V8 ~ Android 4.3-
	  $toPrecision.call({});
	})), 'Number', {
	  toPrecision: function toPrecision(precision){
	    var that = aNumberValue(this, 'Number#toPrecision: incorrect invocation!');
	    return precision === undefined ? $toPrecision.call(that) : $toPrecision.call(that, precision); 
	  }
	});

/***/ },

/***/ 997:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.epsilon.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.1 Number.EPSILON
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Number', {EPSILON: Math.pow(2, -52)});

/***/ },

/***/ 998:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-finite.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.2 Number.isFinite(number)
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , _isFinite = __webpack_require__(/*! ./_global */ 908).isFinite;
	
	$export($export.S, 'Number', {
	  isFinite: function isFinite(it){
	    return typeof it == 'number' && _isFinite(it);
	  }
	});

/***/ },

/***/ 999:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-integer.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.3 Number.isInteger(number)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Number', {isInteger: __webpack_require__(/*! ./_is-integer */ 1000)});

/***/ },

/***/ 1000:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-integer.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.3 Number.isInteger(number)
	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , floor    = Math.floor;
	module.exports = function isInteger(it){
	  return !isObject(it) && isFinite(it) && floor(it) === it;
	};

/***/ },

/***/ 1001:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-nan.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.4 Number.isNaN(number)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Number', {
	  isNaN: function isNaN(number){
	    return number != number;
	  }
	});

/***/ },

/***/ 1002:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-safe-integer.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.5 Number.isSafeInteger(number)
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , isInteger = __webpack_require__(/*! ./_is-integer */ 1000)
	  , abs       = Math.abs;
	
	$export($export.S, 'Number', {
	  isSafeInteger: function isSafeInteger(number){
	    return isInteger(number) && abs(number) <= 0x1fffffffffffff;
	  }
	});

/***/ },

/***/ 1003:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.max-safe-integer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.6 Number.MAX_SAFE_INTEGER
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Number', {MAX_SAFE_INTEGER: 0x1fffffffffffff});

/***/ },

/***/ 1004:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.min-safe-integer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.10 Number.MIN_SAFE_INTEGER
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Number', {MIN_SAFE_INTEGER: -0x1fffffffffffff});

/***/ },

/***/ 1005:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.parse-float.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , $parseFloat = __webpack_require__(/*! ./_parse-float */ 990);
	// 20.1.2.12 Number.parseFloat(string)
	$export($export.S + $export.F * (Number.parseFloat != $parseFloat), 'Number', {parseFloat: $parseFloat});

/***/ },

/***/ 1006:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.parse-int.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , $parseInt = __webpack_require__(/*! ./_parse-int */ 986);
	// 20.1.2.13 Number.parseInt(string, radix)
	$export($export.S + $export.F * (Number.parseInt != $parseInt), 'Number', {parseInt: $parseInt});

/***/ },

/***/ 1007:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.acosh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.3 Math.acosh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , log1p   = __webpack_require__(/*! ./_math-log1p */ 1008)
	  , sqrt    = Math.sqrt
	  , $acosh  = Math.acosh;
	
	$export($export.S + $export.F * !($acosh
	  // V8 bug: https://code.google.com/p/v8/issues/detail?id=3509
	  && Math.floor($acosh(Number.MAX_VALUE)) == 710
	  // Tor Browser bug: Math.acosh(Infinity) -> NaN 
	  && $acosh(Infinity) == Infinity
	), 'Math', {
	  acosh: function acosh(x){
	    return (x = +x) < 1 ? NaN : x > 94906265.62425156
	      ? Math.log(x) + Math.LN2
	      : log1p(x - 1 + sqrt(x - 1) * sqrt(x + 1));
	  }
	});

/***/ },

/***/ 1008:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_math-log1p.js ***!
  \***********************************************************/
/***/ function(module, exports) {

	// 20.2.2.20 Math.log1p(x)
	module.exports = Math.log1p || function log1p(x){
	  return (x = +x) > -1e-8 && x < 1e-8 ? x - x * x / 2 : Math.log(1 + x);
	};

/***/ },

/***/ 1009:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.asinh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.5 Math.asinh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $asinh  = Math.asinh;
	
	function asinh(x){
	  return !isFinite(x = +x) || x == 0 ? x : x < 0 ? -asinh(-x) : Math.log(x + Math.sqrt(x * x + 1));
	}
	
	// Tor Browser bug: Math.asinh(0) -> -0 
	$export($export.S + $export.F * !($asinh && 1 / $asinh(0) > 0), 'Math', {asinh: asinh});

/***/ },

/***/ 1010:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.atanh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.7 Math.atanh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $atanh  = Math.atanh;
	
	// Tor Browser bug: Math.atanh(-0) -> 0 
	$export($export.S + $export.F * !($atanh && 1 / $atanh(-0) < 0), 'Math', {
	  atanh: function atanh(x){
	    return (x = +x) == 0 ? x : Math.log((1 + x) / (1 - x)) / 2;
	  }
	});

/***/ },

/***/ 1011:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.cbrt.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.9 Math.cbrt(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , sign    = __webpack_require__(/*! ./_math-sign */ 1012);
	
	$export($export.S, 'Math', {
	  cbrt: function cbrt(x){
	    return sign(x = +x) * Math.pow(Math.abs(x), 1 / 3);
	  }
	});

/***/ },

/***/ 1012:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_math-sign.js ***!
  \**********************************************************/
/***/ function(module, exports) {

	// 20.2.2.28 Math.sign(x)
	module.exports = Math.sign || function sign(x){
	  return (x = +x) == 0 || x != x ? x : x < 0 ? -1 : 1;
	};

/***/ },

/***/ 1013:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.clz32.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.11 Math.clz32(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  clz32: function clz32(x){
	    return (x >>>= 0) ? 31 - Math.floor(Math.log(x + 0.5) * Math.LOG2E) : 32;
	  }
	});

/***/ },

/***/ 1014:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.cosh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.12 Math.cosh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , exp     = Math.exp;
	
	$export($export.S, 'Math', {
	  cosh: function cosh(x){
	    return (exp(x = +x) + exp(-x)) / 2;
	  }
	});

/***/ },

/***/ 1015:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.expm1.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.14 Math.expm1(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $expm1  = __webpack_require__(/*! ./_math-expm1 */ 1016);
	
	$export($export.S + $export.F * ($expm1 != Math.expm1), 'Math', {expm1: $expm1});

/***/ },

/***/ 1016:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_math-expm1.js ***!
  \***********************************************************/
/***/ function(module, exports) {

	// 20.2.2.14 Math.expm1(x)
	var $expm1 = Math.expm1;
	module.exports = (!$expm1
	  // Old FF bug
	  || $expm1(10) > 22025.465794806719 || $expm1(10) < 22025.4657948067165168
	  // Tor Browser bug
	  || $expm1(-2e-17) != -2e-17
	) ? function expm1(x){
	  return (x = +x) == 0 ? x : x > -1e-6 && x < 1e-6 ? x + x * x / 2 : Math.exp(x) - 1;
	} : $expm1;

/***/ },

/***/ 1017:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.fround.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.16 Math.fround(x)
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , sign      = __webpack_require__(/*! ./_math-sign */ 1012)
	  , pow       = Math.pow
	  , EPSILON   = pow(2, -52)
	  , EPSILON32 = pow(2, -23)
	  , MAX32     = pow(2, 127) * (2 - EPSILON32)
	  , MIN32     = pow(2, -126);
	
	var roundTiesToEven = function(n){
	  return n + 1 / EPSILON - 1 / EPSILON;
	};
	
	
	$export($export.S, 'Math', {
	  fround: function fround(x){
	    var $abs  = Math.abs(x)
	      , $sign = sign(x)
	      , a, result;
	    if($abs < MIN32)return $sign * roundTiesToEven($abs / MIN32 / EPSILON32) * MIN32 * EPSILON32;
	    a = (1 + EPSILON32 / EPSILON) * $abs;
	    result = a - (a - $abs);
	    if(result > MAX32 || result != result)return $sign * Infinity;
	    return $sign * result;
	  }
	});

/***/ },

/***/ 1018:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.hypot.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.17 Math.hypot([value1[, value2[, … ]]])
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , abs     = Math.abs;
	
	$export($export.S, 'Math', {
	  hypot: function hypot(value1, value2){ // eslint-disable-line no-unused-vars
	    var sum  = 0
	      , i    = 0
	      , aLen = arguments.length
	      , larg = 0
	      , arg, div;
	    while(i < aLen){
	      arg = abs(arguments[i++]);
	      if(larg < arg){
	        div  = larg / arg;
	        sum  = sum * div * div + 1;
	        larg = arg;
	      } else if(arg > 0){
	        div  = arg / larg;
	        sum += div * div;
	      } else sum += arg;
	    }
	    return larg === Infinity ? Infinity : larg * Math.sqrt(sum);
	  }
	});

/***/ },

/***/ 1019:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.imul.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.18 Math.imul(x, y)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $imul   = Math.imul;
	
	// some WebKit versions fails with big numbers, some has wrong arity
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  return $imul(0xffffffff, 5) != -5 || $imul.length != 2;
	}), 'Math', {
	  imul: function imul(x, y){
	    var UINT16 = 0xffff
	      , xn = +x
	      , yn = +y
	      , xl = UINT16 & xn
	      , yl = UINT16 & yn;
	    return 0 | xl * yl + ((UINT16 & xn >>> 16) * yl + xl * (UINT16 & yn >>> 16) << 16 >>> 0);
	  }
	});

/***/ },

/***/ 1020:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log10.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.21 Math.log10(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  log10: function log10(x){
	    return Math.log(x) / Math.LN10;
	  }
	});

/***/ },

/***/ 1021:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log1p.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.20 Math.log1p(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {log1p: __webpack_require__(/*! ./_math-log1p */ 1008)});

/***/ },

/***/ 1022:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log2.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.22 Math.log2(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  log2: function log2(x){
	    return Math.log(x) / Math.LN2;
	  }
	});

/***/ },

/***/ 1023:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.sign.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.28 Math.sign(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {sign: __webpack_require__(/*! ./_math-sign */ 1012)});

/***/ },

/***/ 1024:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.sinh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.30 Math.sinh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , expm1   = __webpack_require__(/*! ./_math-expm1 */ 1016)
	  , exp     = Math.exp;
	
	// V8 near Chromium 38 has a problem with very small numbers
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  return !Math.sinh(-2e-17) != -2e-17;
	}), 'Math', {
	  sinh: function sinh(x){
	    return Math.abs(x = +x) < 1
	      ? (expm1(x) - expm1(-x)) / 2
	      : (exp(x - 1) - exp(-x - 1)) * (Math.E / 2);
	  }
	});

/***/ },

/***/ 1025:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.tanh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.33 Math.tanh(x)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , expm1   = __webpack_require__(/*! ./_math-expm1 */ 1016)
	  , exp     = Math.exp;
	
	$export($export.S, 'Math', {
	  tanh: function tanh(x){
	    var a = expm1(x = +x)
	      , b = expm1(-x);
	    return a == Infinity ? 1 : b == Infinity ? -1 : (a - b) / (exp(x) + exp(-x));
	  }
	});

/***/ },

/***/ 1026:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.trunc.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.34 Math.trunc(x)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  trunc: function trunc(it){
	    return (it > 0 ? Math.floor : Math.ceil)(it);
	  }
	});

/***/ },

/***/ 1027:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.from-code-point.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export        = __webpack_require__(/*! ./_export */ 912)
	  , toIndex        = __webpack_require__(/*! ./_to-index */ 943)
	  , fromCharCode   = String.fromCharCode
	  , $fromCodePoint = String.fromCodePoint;
	
	// length should be 1, old FF problem
	$export($export.S + $export.F * (!!$fromCodePoint && $fromCodePoint.length != 1), 'String', {
	  // 21.1.2.2 String.fromCodePoint(...codePoints)
	  fromCodePoint: function fromCodePoint(x){ // eslint-disable-line no-unused-vars
	    var res  = []
	      , aLen = arguments.length
	      , i    = 0
	      , code;
	    while(aLen > i){
	      code = +arguments[i++];
	      if(toIndex(code, 0x10ffff) !== code)throw RangeError(code + ' is not a valid code point');
	      res.push(code < 0x10000
	        ? fromCharCode(code)
	        : fromCharCode(((code -= 0x10000) >> 10) + 0xd800, code % 0x400 + 0xdc00)
	      );
	    } return res.join('');
	  }
	});

/***/ },

/***/ 1028:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.raw.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , toIObject = __webpack_require__(/*! ./_to-iobject */ 936)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 941);
	
	$export($export.S, 'String', {
	  // 21.1.2.4 String.raw(callSite, ...substitutions)
	  raw: function raw(callSite){
	    var tpl  = toIObject(callSite.raw)
	      , len  = toLength(tpl.length)
	      , aLen = arguments.length
	      , res  = []
	      , i    = 0;
	    while(len > i){
	      res.push(String(tpl[i++]));
	      if(i < aLen)res.push(String(arguments[i]));
	    } return res.join('');
	  }
	});

/***/ },

/***/ 1029:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.trim.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 21.1.3.25 String.prototype.trim()
	__webpack_require__(/*! ./_string-trim */ 987)('trim', function($trim){
	  return function trim(){
	    return $trim(this, 3);
	  };
	});

/***/ },

/***/ 1030:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.iterator.js ***!
  \*******************************************************************/
[4756, 1031, 1032],

/***/ 1031:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-at.js ***!
  \**********************************************************/
[4757, 942, 939],

/***/ 1032:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-define.js ***!
  \************************************************************/
[4758, 932, 912, 922, 914, 909, 1033, 1034, 928, 963, 929],

/***/ 1033:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iterators.js ***!
  \**********************************************************/
681,

/***/ 1034:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-create.js ***!
  \************************************************************/
[4760, 950, 921, 928, 914, 929],

/***/ 1035:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.code-point-at.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $at     = __webpack_require__(/*! ./_string-at */ 1031)(false);
	$export($export.P, 'String', {
	  // 21.1.3.3 String.prototype.codePointAt(pos)
	  codePointAt: function codePointAt(pos){
	    return $at(this, pos);
	  }
	});

/***/ },

/***/ 1036:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.ends-with.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.6 String.prototype.endsWith(searchString [, endPosition])
	'use strict';
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 941)
	  , context   = __webpack_require__(/*! ./_string-context */ 1037)
	  , ENDS_WITH = 'endsWith'
	  , $endsWith = ''[ENDS_WITH];
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1039)(ENDS_WITH), 'String', {
	  endsWith: function endsWith(searchString /*, endPosition = @length */){
	    var that = context(this, searchString, ENDS_WITH)
	      , endPosition = arguments.length > 1 ? arguments[1] : undefined
	      , len    = toLength(that.length)
	      , end    = endPosition === undefined ? len : Math.min(toLength(endPosition), len)
	      , search = String(searchString);
	    return $endsWith
	      ? $endsWith.call(that, search, end)
	      : that.slice(end - search.length, end) === search;
	  }
	});

/***/ },

/***/ 1037:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-context.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// helper for String#{startsWith, endsWith, includes}
	var isRegExp = __webpack_require__(/*! ./_is-regexp */ 1038)
	  , defined  = __webpack_require__(/*! ./_defined */ 939);
	
	module.exports = function(that, searchString, NAME){
	  if(isRegExp(searchString))throw TypeError('String#' + NAME + " doesn't accept regex!");
	  return String(defined(that));
	};

/***/ },

/***/ 1038:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-regexp.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 7.2.8 IsRegExp(argument)
	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , cof      = __webpack_require__(/*! ./_cof */ 938)
	  , MATCH    = __webpack_require__(/*! ./_wks */ 929)('match');
	module.exports = function(it){
	  var isRegExp;
	  return isObject(it) && ((isRegExp = it[MATCH]) !== undefined ? !!isRegExp : cof(it) == 'RegExp');
	};

/***/ },

/***/ 1039:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fails-is-regexp.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var MATCH = __webpack_require__(/*! ./_wks */ 929)('match');
	module.exports = function(KEY){
	  var re = /./;
	  try {
	    '/./'[KEY](re);
	  } catch(e){
	    try {
	      re[MATCH] = false;
	      return !'/./'[KEY](re);
	    } catch(f){ /* empty */ }
	  } return true;
	};

/***/ },

/***/ 1040:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.includes.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.7 String.prototype.includes(searchString, position = 0)
	'use strict';
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , context  = __webpack_require__(/*! ./_string-context */ 1037)
	  , INCLUDES = 'includes';
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1039)(INCLUDES), 'String', {
	  includes: function includes(searchString /*, position = 0 */){
	    return !!~context(this, searchString, INCLUDES)
	      .indexOf(searchString, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});

/***/ },

/***/ 1041:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.repeat.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P, 'String', {
	  // 21.1.3.13 String.prototype.repeat(count)
	  repeat: __webpack_require__(/*! ./_string-repeat */ 995)
	});

/***/ },

/***/ 1042:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.starts-with.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.18 String.prototype.startsWith(searchString [, position ])
	'use strict';
	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 941)
	  , context     = __webpack_require__(/*! ./_string-context */ 1037)
	  , STARTS_WITH = 'startsWith'
	  , $startsWith = ''[STARTS_WITH];
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1039)(STARTS_WITH), 'String', {
	  startsWith: function startsWith(searchString /*, position = 0 */){
	    var that   = context(this, searchString, STARTS_WITH)
	      , index  = toLength(Math.min(arguments.length > 1 ? arguments[1] : undefined, that.length))
	      , search = String(searchString);
	    return $startsWith
	      ? $startsWith.call(that, search, index)
	      : that.slice(index, index + search.length) === search;
	  }
	});

/***/ },

/***/ 1043:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.anchor.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.2 String.prototype.anchor(name)
	__webpack_require__(/*! ./_string-html */ 1044)('anchor', function(createHTML){
	  return function anchor(name){
	    return createHTML(this, 'a', 'name', name);
	  }
	});

/***/ },

/***/ 1044:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-html.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912)
	  , fails   = __webpack_require__(/*! ./_fails */ 911)
	  , defined = __webpack_require__(/*! ./_defined */ 939)
	  , quot    = /"/g;
	// B.2.3.2.1 CreateHTML(string, tag, attribute, value)
	var createHTML = function(string, tag, attribute, value) {
	  var S  = String(defined(string))
	    , p1 = '<' + tag;
	  if(attribute !== '')p1 += ' ' + attribute + '="' + String(value).replace(quot, '&quot;') + '"';
	  return p1 + '>' + S + '</' + tag + '>';
	};
	module.exports = function(NAME, exec){
	  var O = {};
	  O[NAME] = exec(createHTML);
	  $export($export.P + $export.F * fails(function(){
	    var test = ''[NAME]('"');
	    return test !== test.toLowerCase() || test.split('"').length > 3;
	  }), 'String', O);
	};

/***/ },

/***/ 1045:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.big.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.3 String.prototype.big()
	__webpack_require__(/*! ./_string-html */ 1044)('big', function(createHTML){
	  return function big(){
	    return createHTML(this, 'big', '', '');
	  }
	});

/***/ },

/***/ 1046:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.blink.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.4 String.prototype.blink()
	__webpack_require__(/*! ./_string-html */ 1044)('blink', function(createHTML){
	  return function blink(){
	    return createHTML(this, 'blink', '', '');
	  }
	});

/***/ },

/***/ 1047:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.bold.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.5 String.prototype.bold()
	__webpack_require__(/*! ./_string-html */ 1044)('bold', function(createHTML){
	  return function bold(){
	    return createHTML(this, 'b', '', '');
	  }
	});

/***/ },

/***/ 1048:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fixed.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.6 String.prototype.fixed()
	__webpack_require__(/*! ./_string-html */ 1044)('fixed', function(createHTML){
	  return function fixed(){
	    return createHTML(this, 'tt', '', '');
	  }
	});

/***/ },

/***/ 1049:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fontcolor.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.7 String.prototype.fontcolor(color)
	__webpack_require__(/*! ./_string-html */ 1044)('fontcolor', function(createHTML){
	  return function fontcolor(color){
	    return createHTML(this, 'font', 'color', color);
	  }
	});

/***/ },

/***/ 1050:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fontsize.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.8 String.prototype.fontsize(size)
	__webpack_require__(/*! ./_string-html */ 1044)('fontsize', function(createHTML){
	  return function fontsize(size){
	    return createHTML(this, 'font', 'size', size);
	  }
	});

/***/ },

/***/ 1051:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.italics.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.9 String.prototype.italics()
	__webpack_require__(/*! ./_string-html */ 1044)('italics', function(createHTML){
	  return function italics(){
	    return createHTML(this, 'i', '', '');
	  }
	});

/***/ },

/***/ 1052:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.link.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.10 String.prototype.link(url)
	__webpack_require__(/*! ./_string-html */ 1044)('link', function(createHTML){
	  return function link(url){
	    return createHTML(this, 'a', 'href', url);
	  }
	});

/***/ },

/***/ 1053:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.small.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.11 String.prototype.small()
	__webpack_require__(/*! ./_string-html */ 1044)('small', function(createHTML){
	  return function small(){
	    return createHTML(this, 'small', '', '');
	  }
	});

/***/ },

/***/ 1054:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.strike.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.12 String.prototype.strike()
	__webpack_require__(/*! ./_string-html */ 1044)('strike', function(createHTML){
	  return function strike(){
	    return createHTML(this, 'strike', '', '');
	  }
	});

/***/ },

/***/ 1055:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.sub.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.13 String.prototype.sub()
	__webpack_require__(/*! ./_string-html */ 1044)('sub', function(createHTML){
	  return function sub(){
	    return createHTML(this, 'sub', '', '');
	  }
	});

/***/ },

/***/ 1056:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.sup.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.14 String.prototype.sup()
	__webpack_require__(/*! ./_string-html */ 1044)('sup', function(createHTML){
	  return function sup(){
	    return createHTML(this, 'sup', '', '');
	  }
	});

/***/ },

/***/ 1057:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.now.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.3.3.1 / 15.9.4.4 Date.now()
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Date', {now: function(){ return new Date().getTime(); }});

/***/ },

/***/ 1058:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-json.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , toObject    = __webpack_require__(/*! ./_to-object */ 962)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 920);
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  return new Date(NaN).toJSON() !== null || Date.prototype.toJSON.call({toISOString: function(){ return 1; }}) !== 1;
	}), 'Date', {
	  toJSON: function toJSON(key){
	    var O  = toObject(this)
	      , pv = toPrimitive(O);
	    return typeof pv == 'number' && !isFinite(pv) ? null : O.toISOString();
	  }
	});

/***/ },

/***/ 1059:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-iso-string.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 20.3.4.36 / 15.9.5.43 Date.prototype.toISOString()
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , fails   = __webpack_require__(/*! ./_fails */ 911)
	  , getTime = Date.prototype.getTime;
	
	var lz = function(num){
	  return num > 9 ? num : '0' + num;
	};
	
	// PhantomJS / old WebKit has a broken implementations
	$export($export.P + $export.F * (fails(function(){
	  return new Date(-5e13 - 1).toISOString() != '0385-07-25T07:06:39.999Z';
	}) || !fails(function(){
	  new Date(NaN).toISOString();
	})), 'Date', {
	  toISOString: function toISOString(){
	    if(!isFinite(getTime.call(this)))throw RangeError('Invalid time value');
	    var d = this
	      , y = d.getUTCFullYear()
	      , m = d.getUTCMilliseconds()
	      , s = y < 0 ? '-' : y > 9999 ? '+' : '';
	    return s + ('00000' + Math.abs(y)).slice(s ? -6 : -4) +
	      '-' + lz(d.getUTCMonth() + 1) + '-' + lz(d.getUTCDate()) +
	      'T' + lz(d.getUTCHours()) + ':' + lz(d.getUTCMinutes()) +
	      ':' + lz(d.getUTCSeconds()) + '.' + (m > 99 ? m : '0' + lz(m)) + 'Z';
	  }
	});

/***/ },

/***/ 1060:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-string.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var DateProto    = Date.prototype
	  , INVALID_DATE = 'Invalid Date'
	  , TO_STRING    = 'toString'
	  , $toString    = DateProto[TO_STRING]
	  , getTime      = DateProto.getTime;
	if(new Date(NaN) + '' != INVALID_DATE){
	  __webpack_require__(/*! ./_redefine */ 922)(DateProto, TO_STRING, function toString(){
	    var value = getTime.call(this);
	    return value === value ? $toString.call(this) : INVALID_DATE;
	  });
	}

/***/ },

/***/ 1061:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-primitive.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var TO_PRIMITIVE = __webpack_require__(/*! ./_wks */ 929)('toPrimitive')
	  , proto        = Date.prototype;
	
	if(!(TO_PRIMITIVE in proto))__webpack_require__(/*! ./_hide */ 914)(proto, TO_PRIMITIVE, __webpack_require__(/*! ./_date-to-primitive */ 1062));

/***/ },

/***/ 1062:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_date-to-primitive.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var anObject    = __webpack_require__(/*! ./_an-object */ 916)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 920)
	  , NUMBER      = 'number';
	
	module.exports = function(hint){
	  if(hint !== 'string' && hint !== NUMBER && hint !== 'default')throw TypeError('Incorrect hint');
	  return toPrimitive(anObject(this), hint != NUMBER);
	};

/***/ },

/***/ 1063:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.is-array.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.2.2 / 15.4.3.2 Array.isArray(arg)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Array', {isArray: __webpack_require__(/*! ./_is-array */ 949)});

/***/ },

/***/ 1064:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.from.js ***!
  \**************************************************************/
[4777, 924, 912, 962, 1065, 1066, 941, 1067, 1068, 1069],

/***/ 1065:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-call.js ***!
  \**********************************************************/
[4778, 916],

/***/ 1066:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-array-iter.js ***!
  \**************************************************************/
[4779, 1033, 929],

/***/ 1067:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_create-property.js ***!
  \****************************************************************/
[4780, 915, 921],

/***/ 1068:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/core.get-iterator-method.js ***!
  \************************************************************************/
[4781, 979, 929, 1033, 913],

/***/ 1069:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-detect.js ***!
  \************************************************************/
[4783, 929],

/***/ 1070:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.of.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export        = __webpack_require__(/*! ./_export */ 912)
	  , createProperty = __webpack_require__(/*! ./_create-property */ 1067);
	
	// WebKit Array.of isn't generic
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  function F(){}
	  return !(Array.of.call(F) instanceof F);
	}), 'Array', {
	  // 22.1.2.3 Array.of( ...items)
	  of: function of(/* ...args */){
	    var index  = 0
	      , aLen   = arguments.length
	      , result = new (typeof this == 'function' ? this : Array)(aLen);
	    while(aLen > index)createProperty(result, index, arguments[index++]);
	    result.length = aLen;
	    return result;
	  }
	});

/***/ },

/***/ 1071:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.join.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.13 Array.prototype.join(separator)
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , toIObject = __webpack_require__(/*! ./_to-iobject */ 936)
	  , arrayJoin = [].join;
	
	// fallback for not array-like strings
	$export($export.P + $export.F * (__webpack_require__(/*! ./_iobject */ 937) != Object || !__webpack_require__(/*! ./_strict-method */ 1072)(arrayJoin)), 'Array', {
	  join: function join(separator){
	    return arrayJoin.call(toIObject(this), separator === undefined ? ',' : separator);
	  }
	});

/***/ },

/***/ 1072:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_strict-method.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var fails = __webpack_require__(/*! ./_fails */ 911);
	
	module.exports = function(method, arg){
	  return !!method && fails(function(){
	    arg ? method.call(null, function(){}, 1) : method.call(null);
	  });
	};

/***/ },

/***/ 1073:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.slice.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export    = __webpack_require__(/*! ./_export */ 912)
	  , html       = __webpack_require__(/*! ./_html */ 952)
	  , cof        = __webpack_require__(/*! ./_cof */ 938)
	  , toIndex    = __webpack_require__(/*! ./_to-index */ 943)
	  , toLength   = __webpack_require__(/*! ./_to-length */ 941)
	  , arraySlice = [].slice;
	
	// fallback for not array-like ES3 strings and DOM objects
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  if(html)arraySlice.call(html);
	}), 'Array', {
	  slice: function slice(begin, end){
	    var len   = toLength(this.length)
	      , klass = cof(this);
	    end = end === undefined ? len : end;
	    if(klass == 'Array')return arraySlice.call(this, begin, end);
	    var start  = toIndex(begin, len)
	      , upTo   = toIndex(end, len)
	      , size   = toLength(upTo - start)
	      , cloned = Array(size)
	      , i      = 0;
	    for(; i < size; i++)cloned[i] = klass == 'String'
	      ? this.charAt(start + i)
	      : this[start + i];
	    return cloned;
	  }
	});

/***/ },

/***/ 1074:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.sort.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 925)
	  , toObject  = __webpack_require__(/*! ./_to-object */ 962)
	  , fails     = __webpack_require__(/*! ./_fails */ 911)
	  , $sort     = [].sort
	  , test      = [1, 2, 3];
	
	$export($export.P + $export.F * (fails(function(){
	  // IE8-
	  test.sort(undefined);
	}) || !fails(function(){
	  // V8 bug
	  test.sort(null);
	  // Old WebKit
	}) || !__webpack_require__(/*! ./_strict-method */ 1072)($sort)), 'Array', {
	  // 22.1.3.25 Array.prototype.sort(comparefn)
	  sort: function sort(comparefn){
	    return comparefn === undefined
	      ? $sort.call(toObject(this))
	      : $sort.call(toObject(this), aFunction(comparefn));
	  }
	});

/***/ },

/***/ 1075:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.for-each.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , $forEach = __webpack_require__(/*! ./_array-methods */ 1076)(0)
	  , STRICT   = __webpack_require__(/*! ./_strict-method */ 1072)([].forEach, true);
	
	$export($export.P + $export.F * !STRICT, 'Array', {
	  // 22.1.3.10 / 15.4.4.18 Array.prototype.forEach(callbackfn [, thisArg])
	  forEach: function forEach(callbackfn /* , thisArg */){
	    return $forEach(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1076:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-methods.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 0 -> Array#forEach
	// 1 -> Array#map
	// 2 -> Array#filter
	// 3 -> Array#some
	// 4 -> Array#every
	// 5 -> Array#find
	// 6 -> Array#findIndex
	var ctx      = __webpack_require__(/*! ./_ctx */ 924)
	  , IObject  = __webpack_require__(/*! ./_iobject */ 937)
	  , toObject = __webpack_require__(/*! ./_to-object */ 962)
	  , toLength = __webpack_require__(/*! ./_to-length */ 941)
	  , asc      = __webpack_require__(/*! ./_array-species-create */ 1077);
	module.exports = function(TYPE, $create){
	  var IS_MAP        = TYPE == 1
	    , IS_FILTER     = TYPE == 2
	    , IS_SOME       = TYPE == 3
	    , IS_EVERY      = TYPE == 4
	    , IS_FIND_INDEX = TYPE == 6
	    , NO_HOLES      = TYPE == 5 || IS_FIND_INDEX
	    , create        = $create || asc;
	  return function($this, callbackfn, that){
	    var O      = toObject($this)
	      , self   = IObject(O)
	      , f      = ctx(callbackfn, that, 3)
	      , length = toLength(self.length)
	      , index  = 0
	      , result = IS_MAP ? create($this, length) : IS_FILTER ? create($this, 0) : undefined
	      , val, res;
	    for(;length > index; index++)if(NO_HOLES || index in self){
	      val = self[index];
	      res = f(val, index, O);
	      if(TYPE){
	        if(IS_MAP)result[index] = res;            // map
	        else if(res)switch(TYPE){
	          case 3: return true;                    // some
	          case 5: return val;                     // find
	          case 6: return index;                   // findIndex
	          case 2: result.push(val);               // filter
	        } else if(IS_EVERY)return false;          // every
	      }
	    }
	    return IS_FIND_INDEX ? -1 : IS_SOME || IS_EVERY ? IS_EVERY : result;
	  };
	};

/***/ },

/***/ 1077:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-species-create.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 9.4.2.3 ArraySpeciesCreate(originalArray, length)
	var speciesConstructor = __webpack_require__(/*! ./_array-species-constructor */ 1078);
	
	module.exports = function(original, length){
	  return new (speciesConstructor(original))(length);
	};

/***/ },

/***/ 1078:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-species-constructor.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(/*! ./_is-object */ 917)
	  , isArray  = __webpack_require__(/*! ./_is-array */ 949)
	  , SPECIES  = __webpack_require__(/*! ./_wks */ 929)('species');
	
	module.exports = function(original){
	  var C;
	  if(isArray(original)){
	    C = original.constructor;
	    // cross-realm fallback
	    if(typeof C == 'function' && (C === Array || isArray(C.prototype)))C = undefined;
	    if(isObject(C)){
	      C = C[SPECIES];
	      if(C === null)C = undefined;
	    }
	  } return C === undefined ? Array : C;
	};

/***/ },

/***/ 1079:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.map.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $map    = __webpack_require__(/*! ./_array-methods */ 1076)(1);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].map, true), 'Array', {
	  // 22.1.3.15 / 15.4.4.19 Array.prototype.map(callbackfn [, thisArg])
	  map: function map(callbackfn /* , thisArg */){
	    return $map(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1080:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.filter.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $filter = __webpack_require__(/*! ./_array-methods */ 1076)(2);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].filter, true), 'Array', {
	  // 22.1.3.7 / 15.4.4.20 Array.prototype.filter(callbackfn [, thisArg])
	  filter: function filter(callbackfn /* , thisArg */){
	    return $filter(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1081:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.some.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $some   = __webpack_require__(/*! ./_array-methods */ 1076)(3);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].some, true), 'Array', {
	  // 22.1.3.23 / 15.4.4.17 Array.prototype.some(callbackfn [, thisArg])
	  some: function some(callbackfn /* , thisArg */){
	    return $some(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1082:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.every.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $every  = __webpack_require__(/*! ./_array-methods */ 1076)(4);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].every, true), 'Array', {
	  // 22.1.3.5 / 15.4.4.16 Array.prototype.every(callbackfn [, thisArg])
	  every: function every(callbackfn /* , thisArg */){
	    return $every(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1083:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.reduce.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $reduce = __webpack_require__(/*! ./_array-reduce */ 1084);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].reduce, true), 'Array', {
	  // 22.1.3.18 / 15.4.4.21 Array.prototype.reduce(callbackfn [, initialValue])
	  reduce: function reduce(callbackfn /* , initialValue */){
	    return $reduce(this, callbackfn, arguments.length, arguments[1], false);
	  }
	});

/***/ },

/***/ 1084:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-reduce.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var aFunction = __webpack_require__(/*! ./_a-function */ 925)
	  , toObject  = __webpack_require__(/*! ./_to-object */ 962)
	  , IObject   = __webpack_require__(/*! ./_iobject */ 937)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 941);
	
	module.exports = function(that, callbackfn, aLen, memo, isRight){
	  aFunction(callbackfn);
	  var O      = toObject(that)
	    , self   = IObject(O)
	    , length = toLength(O.length)
	    , index  = isRight ? length - 1 : 0
	    , i      = isRight ? -1 : 1;
	  if(aLen < 2)for(;;){
	    if(index in self){
	      memo = self[index];
	      index += i;
	      break;
	    }
	    index += i;
	    if(isRight ? index < 0 : length <= index){
	      throw TypeError('Reduce of empty array with no initial value');
	    }
	  }
	  for(;isRight ? index >= 0 : length > index; index += i)if(index in self){
	    memo = callbackfn(memo, self[index], index, O);
	  }
	  return memo;
	};

/***/ },

/***/ 1085:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.reduce-right.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $reduce = __webpack_require__(/*! ./_array-reduce */ 1084);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1072)([].reduceRight, true), 'Array', {
	  // 22.1.3.19 / 15.4.4.22 Array.prototype.reduceRight(callbackfn [, initialValue])
	  reduceRight: function reduceRight(callbackfn /* , initialValue */){
	    return $reduce(this, callbackfn, arguments.length, arguments[1], true);
	  }
	});

/***/ },

/***/ 1086:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.index-of.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export       = __webpack_require__(/*! ./_export */ 912)
	  , $indexOf      = __webpack_require__(/*! ./_array-includes */ 940)(false)
	  , $native       = [].indexOf
	  , NEGATIVE_ZERO = !!$native && 1 / [1].indexOf(1, -0) < 0;
	
	$export($export.P + $export.F * (NEGATIVE_ZERO || !__webpack_require__(/*! ./_strict-method */ 1072)($native)), 'Array', {
	  // 22.1.3.11 / 15.4.4.14 Array.prototype.indexOf(searchElement [, fromIndex])
	  indexOf: function indexOf(searchElement /*, fromIndex = 0 */){
	    return NEGATIVE_ZERO
	      // convert -0 to +0
	      ? $native.apply(this, arguments) || 0
	      : $indexOf(this, searchElement, arguments[1]);
	  }
	});

/***/ },

/***/ 1087:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.last-index-of.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export       = __webpack_require__(/*! ./_export */ 912)
	  , toIObject     = __webpack_require__(/*! ./_to-iobject */ 936)
	  , toInteger     = __webpack_require__(/*! ./_to-integer */ 942)
	  , toLength      = __webpack_require__(/*! ./_to-length */ 941)
	  , $native       = [].lastIndexOf
	  , NEGATIVE_ZERO = !!$native && 1 / [1].lastIndexOf(1, -0) < 0;
	
	$export($export.P + $export.F * (NEGATIVE_ZERO || !__webpack_require__(/*! ./_strict-method */ 1072)($native)), 'Array', {
	  // 22.1.3.14 / 15.4.4.15 Array.prototype.lastIndexOf(searchElement [, fromIndex])
	  lastIndexOf: function lastIndexOf(searchElement /*, fromIndex = @[*-1] */){
	    // convert -0 to +0
	    if(NEGATIVE_ZERO)return $native.apply(this, arguments) || 0;
	    var O      = toIObject(this)
	      , length = toLength(O.length)
	      , index  = length - 1;
	    if(arguments.length > 1)index = Math.min(index, toInteger(arguments[1]));
	    if(index < 0)index = length + index;
	    for(;index >= 0; index--)if(index in O)if(O[index] === searchElement)return index || 0;
	    return -1;
	  }
	});

/***/ },

/***/ 1088:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.copy-within.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.3 Array.prototype.copyWithin(target, start, end = this.length)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P, 'Array', {copyWithin: __webpack_require__(/*! ./_array-copy-within */ 1089)});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1090)('copyWithin');

/***/ },

/***/ 1089:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-copy-within.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.3 Array.prototype.copyWithin(target, start, end = this.length)
	'use strict';
	var toObject = __webpack_require__(/*! ./_to-object */ 962)
	  , toIndex  = __webpack_require__(/*! ./_to-index */ 943)
	  , toLength = __webpack_require__(/*! ./_to-length */ 941);
	
	module.exports = [].copyWithin || function copyWithin(target/*= 0*/, start/*= 0, end = @length*/){
	  var O     = toObject(this)
	    , len   = toLength(O.length)
	    , to    = toIndex(target, len)
	    , from  = toIndex(start, len)
	    , end   = arguments.length > 2 ? arguments[2] : undefined
	    , count = Math.min((end === undefined ? len : toIndex(end, len)) - from, len - to)
	    , inc   = 1;
	  if(from < to && to < from + count){
	    inc  = -1;
	    from += count - 1;
	    to   += count - 1;
	  }
	  while(count-- > 0){
	    if(from in O)O[to] = O[from];
	    else delete O[to];
	    to   += inc;
	    from += inc;
	  } return O;
	};

/***/ },

/***/ 1090:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_add-to-unscopables.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.31 Array.prototype[@@unscopables]
	var UNSCOPABLES = __webpack_require__(/*! ./_wks */ 929)('unscopables')
	  , ArrayProto  = Array.prototype;
	if(ArrayProto[UNSCOPABLES] == undefined)__webpack_require__(/*! ./_hide */ 914)(ArrayProto, UNSCOPABLES, {});
	module.exports = function(key){
	  ArrayProto[UNSCOPABLES][key] = true;
	};

/***/ },

/***/ 1091:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.fill.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.6 Array.prototype.fill(value, start = 0, end = this.length)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P, 'Array', {fill: __webpack_require__(/*! ./_array-fill */ 1092)});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1090)('fill');

/***/ },

/***/ 1092:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-fill.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.6 Array.prototype.fill(value, start = 0, end = this.length)
	'use strict';
	var toObject = __webpack_require__(/*! ./_to-object */ 962)
	  , toIndex  = __webpack_require__(/*! ./_to-index */ 943)
	  , toLength = __webpack_require__(/*! ./_to-length */ 941);
	module.exports = function fill(value /*, start = 0, end = @length */){
	  var O      = toObject(this)
	    , length = toLength(O.length)
	    , aLen   = arguments.length
	    , index  = toIndex(aLen > 1 ? arguments[1] : undefined, length)
	    , end    = aLen > 2 ? arguments[2] : undefined
	    , endPos = end === undefined ? length : toIndex(end, length);
	  while(endPos > index)O[index++] = value;
	  return O;
	};

/***/ },

/***/ 1093:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.find.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.8 Array.prototype.find(predicate, thisArg = undefined)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $find   = __webpack_require__(/*! ./_array-methods */ 1076)(5)
	  , KEY     = 'find'
	  , forced  = true;
	// Shouldn't skip holes
	if(KEY in [])Array(1)[KEY](function(){ forced = false; });
	$export($export.P + $export.F * forced, 'Array', {
	  find: function find(callbackfn/*, that = undefined */){
	    return $find(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	__webpack_require__(/*! ./_add-to-unscopables */ 1090)(KEY);

/***/ },

/***/ 1094:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.find-index.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.9 Array.prototype.findIndex(predicate, thisArg = undefined)
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $find   = __webpack_require__(/*! ./_array-methods */ 1076)(6)
	  , KEY     = 'findIndex'
	  , forced  = true;
	// Shouldn't skip holes
	if(KEY in [])Array(1)[KEY](function(){ forced = false; });
	$export($export.P + $export.F * forced, 'Array', {
	  findIndex: function findIndex(callbackfn/*, that = undefined */){
	    return $find(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	__webpack_require__(/*! ./_add-to-unscopables */ 1090)(KEY);

/***/ },

/***/ 1095:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.species.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_set-species */ 1096)('Array');

/***/ },

/***/ 1096:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-species.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global      = __webpack_require__(/*! ./_global */ 908)
	  , dP          = __webpack_require__(/*! ./_object-dp */ 915)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 910)
	  , SPECIES     = __webpack_require__(/*! ./_wks */ 929)('species');
	
	module.exports = function(KEY){
	  var C = global[KEY];
	  if(DESCRIPTORS && C && !C[SPECIES])dP.f(C, SPECIES, {
	    configurable: true,
	    get: function(){ return this; }
	  });
	};

/***/ },

/***/ 1097:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.iterator.js ***!
  \******************************************************************/
[4793, 1090, 1098, 1033, 936, 1032],

/***/ 1098:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-step.js ***!
  \**********************************************************/
724,

/***/ 1099:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.constructor.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global            = __webpack_require__(/*! ./_global */ 908)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 992)
	  , dP                = __webpack_require__(/*! ./_object-dp */ 915).f
	  , gOPN              = __webpack_require__(/*! ./_object-gopn */ 954).f
	  , isRegExp          = __webpack_require__(/*! ./_is-regexp */ 1038)
	  , $flags            = __webpack_require__(/*! ./_flags */ 1100)
	  , $RegExp           = global.RegExp
	  , Base              = $RegExp
	  , proto             = $RegExp.prototype
	  , re1               = /a/g
	  , re2               = /a/g
	  // "new" creates a new object, old webkit buggy here
	  , CORRECT_NEW       = new $RegExp(re1) !== re1;
	
	if(__webpack_require__(/*! ./_descriptors */ 910) && (!CORRECT_NEW || __webpack_require__(/*! ./_fails */ 911)(function(){
	  re2[__webpack_require__(/*! ./_wks */ 929)('match')] = false;
	  // RegExp constructor can alter flags and IsRegExp works correct with @@match
	  return $RegExp(re1) != re1 || $RegExp(re2) == re2 || $RegExp(re1, 'i') != '/a/i';
	}))){
	  $RegExp = function RegExp(p, f){
	    var tiRE = this instanceof $RegExp
	      , piRE = isRegExp(p)
	      , fiU  = f === undefined;
	    return !tiRE && piRE && p.constructor === $RegExp && fiU ? p
	      : inheritIfRequired(CORRECT_NEW
	        ? new Base(piRE && !fiU ? p.source : p, f)
	        : Base((piRE = p instanceof $RegExp) ? p.source : p, piRE && fiU ? $flags.call(p) : f)
	      , tiRE ? this : proto, $RegExp);
	  };
	  var proxy = function(key){
	    key in $RegExp || dP($RegExp, key, {
	      configurable: true,
	      get: function(){ return Base[key]; },
	      set: function(it){ Base[key] = it; }
	    });
	  };
	  for(var keys = gOPN(Base), i = 0; keys.length > i; )proxy(keys[i++]);
	  proto.constructor = $RegExp;
	  $RegExp.prototype = proto;
	  __webpack_require__(/*! ./_redefine */ 922)(global, 'RegExp', $RegExp);
	}
	
	__webpack_require__(/*! ./_set-species */ 1096)('RegExp');

/***/ },

/***/ 1100:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_flags.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 21.2.5.3 get RegExp.prototype.flags
	var anObject = __webpack_require__(/*! ./_an-object */ 916);
	module.exports = function(){
	  var that   = anObject(this)
	    , result = '';
	  if(that.global)     result += 'g';
	  if(that.ignoreCase) result += 'i';
	  if(that.multiline)  result += 'm';
	  if(that.unicode)    result += 'u';
	  if(that.sticky)     result += 'y';
	  return result;
	};

/***/ },

/***/ 1101:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.to-string.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	__webpack_require__(/*! ./es6.regexp.flags */ 1102);
	var anObject    = __webpack_require__(/*! ./_an-object */ 916)
	  , $flags      = __webpack_require__(/*! ./_flags */ 1100)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 910)
	  , TO_STRING   = 'toString'
	  , $toString   = /./[TO_STRING];
	
	var define = function(fn){
	  __webpack_require__(/*! ./_redefine */ 922)(RegExp.prototype, TO_STRING, fn, true);
	};
	
	// 21.2.5.14 RegExp.prototype.toString()
	if(__webpack_require__(/*! ./_fails */ 911)(function(){ return $toString.call({source: 'a', flags: 'b'}) != '/a/b'; })){
	  define(function toString(){
	    var R = anObject(this);
	    return '/'.concat(R.source, '/',
	      'flags' in R ? R.flags : !DESCRIPTORS && R instanceof RegExp ? $flags.call(R) : undefined);
	  });
	// FF44- RegExp#toString has a wrong name
	} else if($toString.name != TO_STRING){
	  define(function toString(){
	    return $toString.call(this);
	  });
	}

/***/ },

/***/ 1102:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.flags.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.2.5.3 get RegExp.prototype.flags()
	if(__webpack_require__(/*! ./_descriptors */ 910) && /./g.flags != 'g')__webpack_require__(/*! ./_object-dp */ 915).f(RegExp.prototype, 'flags', {
	  configurable: true,
	  get: __webpack_require__(/*! ./_flags */ 1100)
	});

/***/ },

/***/ 1103:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.match.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@match logic
	__webpack_require__(/*! ./_fix-re-wks */ 1104)('match', 1, function(defined, MATCH, $match){
	  // 21.1.3.11 String.prototype.match(regexp)
	  return [function match(regexp){
	    'use strict';
	    var O  = defined(this)
	      , fn = regexp == undefined ? undefined : regexp[MATCH];
	    return fn !== undefined ? fn.call(regexp, O) : new RegExp(regexp)[MATCH](String(O));
	  }, $match];
	});

/***/ },

/***/ 1104:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fix-re-wks.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var hide     = __webpack_require__(/*! ./_hide */ 914)
	  , redefine = __webpack_require__(/*! ./_redefine */ 922)
	  , fails    = __webpack_require__(/*! ./_fails */ 911)
	  , defined  = __webpack_require__(/*! ./_defined */ 939)
	  , wks      = __webpack_require__(/*! ./_wks */ 929);
	
	module.exports = function(KEY, length, exec){
	  var SYMBOL   = wks(KEY)
	    , fns      = exec(defined, SYMBOL, ''[KEY])
	    , strfn    = fns[0]
	    , rxfn     = fns[1];
	  if(fails(function(){
	    var O = {};
	    O[SYMBOL] = function(){ return 7; };
	    return ''[KEY](O) != 7;
	  })){
	    redefine(String.prototype, KEY, strfn);
	    hide(RegExp.prototype, SYMBOL, length == 2
	      // 21.2.5.8 RegExp.prototype[@@replace](string, replaceValue)
	      // 21.2.5.11 RegExp.prototype[@@split](string, limit)
	      ? function(string, arg){ return rxfn.call(string, this, arg); }
	      // 21.2.5.6 RegExp.prototype[@@match](string)
	      // 21.2.5.9 RegExp.prototype[@@search](string)
	      : function(string){ return rxfn.call(string, this); }
	    );
	  }
	};

/***/ },

/***/ 1105:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.replace.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@replace logic
	__webpack_require__(/*! ./_fix-re-wks */ 1104)('replace', 2, function(defined, REPLACE, $replace){
	  // 21.1.3.14 String.prototype.replace(searchValue, replaceValue)
	  return [function replace(searchValue, replaceValue){
	    'use strict';
	    var O  = defined(this)
	      , fn = searchValue == undefined ? undefined : searchValue[REPLACE];
	    return fn !== undefined
	      ? fn.call(searchValue, O, replaceValue)
	      : $replace.call(String(O), searchValue, replaceValue);
	  }, $replace];
	});

/***/ },

/***/ 1106:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.search.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@search logic
	__webpack_require__(/*! ./_fix-re-wks */ 1104)('search', 1, function(defined, SEARCH, $search){
	  // 21.1.3.15 String.prototype.search(regexp)
	  return [function search(regexp){
	    'use strict';
	    var O  = defined(this)
	      , fn = regexp == undefined ? undefined : regexp[SEARCH];
	    return fn !== undefined ? fn.call(regexp, O) : new RegExp(regexp)[SEARCH](String(O));
	  }, $search];
	});

/***/ },

/***/ 1107:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.split.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@split logic
	__webpack_require__(/*! ./_fix-re-wks */ 1104)('split', 2, function(defined, SPLIT, $split){
	  'use strict';
	  var isRegExp   = __webpack_require__(/*! ./_is-regexp */ 1038)
	    , _split     = $split
	    , $push      = [].push
	    , $SPLIT     = 'split'
	    , LENGTH     = 'length'
	    , LAST_INDEX = 'lastIndex';
	  if(
	    'abbc'[$SPLIT](/(b)*/)[1] == 'c' ||
	    'test'[$SPLIT](/(?:)/, -1)[LENGTH] != 4 ||
	    'ab'[$SPLIT](/(?:ab)*/)[LENGTH] != 2 ||
	    '.'[$SPLIT](/(.?)(.?)/)[LENGTH] != 4 ||
	    '.'[$SPLIT](/()()/)[LENGTH] > 1 ||
	    ''[$SPLIT](/.?/)[LENGTH]
	  ){
	    var NPCG = /()??/.exec('')[1] === undefined; // nonparticipating capturing group
	    // based on es5-shim implementation, need to rework it
	    $split = function(separator, limit){
	      var string = String(this);
	      if(separator === undefined && limit === 0)return [];
	      // If `separator` is not a regex, use native split
	      if(!isRegExp(separator))return _split.call(string, separator, limit);
	      var output = [];
	      var flags = (separator.ignoreCase ? 'i' : '') +
	                  (separator.multiline ? 'm' : '') +
	                  (separator.unicode ? 'u' : '') +
	                  (separator.sticky ? 'y' : '');
	      var lastLastIndex = 0;
	      var splitLimit = limit === undefined ? 4294967295 : limit >>> 0;
	      // Make `global` and avoid `lastIndex` issues by working with a copy
	      var separatorCopy = new RegExp(separator.source, flags + 'g');
	      var separator2, match, lastIndex, lastLength, i;
	      // Doesn't need flags gy, but they don't hurt
	      if(!NPCG)separator2 = new RegExp('^' + separatorCopy.source + '$(?!\\s)', flags);
	      while(match = separatorCopy.exec(string)){
	        // `separatorCopy.lastIndex` is not reliable cross-browser
	        lastIndex = match.index + match[0][LENGTH];
	        if(lastIndex > lastLastIndex){
	          output.push(string.slice(lastLastIndex, match.index));
	          // Fix browsers whose `exec` methods don't consistently return `undefined` for NPCG
	          if(!NPCG && match[LENGTH] > 1)match[0].replace(separator2, function(){
	            for(i = 1; i < arguments[LENGTH] - 2; i++)if(arguments[i] === undefined)match[i] = undefined;
	          });
	          if(match[LENGTH] > 1 && match.index < string[LENGTH])$push.apply(output, match.slice(1));
	          lastLength = match[0][LENGTH];
	          lastLastIndex = lastIndex;
	          if(output[LENGTH] >= splitLimit)break;
	        }
	        if(separatorCopy[LAST_INDEX] === match.index)separatorCopy[LAST_INDEX]++; // Avoid an infinite loop
	      }
	      if(lastLastIndex === string[LENGTH]){
	        if(lastLength || !separatorCopy.test(''))output.push('');
	      } else output.push(string.slice(lastLastIndex));
	      return output[LENGTH] > splitLimit ? output.slice(0, splitLimit) : output;
	    };
	  // Chakra, V8
	  } else if('0'[$SPLIT](undefined, 0)[LENGTH]){
	    $split = function(separator, limit){
	      return separator === undefined && limit === 0 ? [] : _split.call(this, separator, limit);
	    };
	  }
	  // 21.1.3.17 String.prototype.split(separator, limit)
	  return [function split(separator, limit){
	    var O  = defined(this)
	      , fn = separator == undefined ? undefined : separator[SPLIT];
	    return fn !== undefined ? fn.call(separator, O, limit) : $split.call(String(O), separator, limit);
	  }, $split];
	});

/***/ },

/***/ 1108:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.promise.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var LIBRARY            = __webpack_require__(/*! ./_library */ 932)
	  , global             = __webpack_require__(/*! ./_global */ 908)
	  , ctx                = __webpack_require__(/*! ./_ctx */ 924)
	  , classof            = __webpack_require__(/*! ./_classof */ 979)
	  , $export            = __webpack_require__(/*! ./_export */ 912)
	  , isObject           = __webpack_require__(/*! ./_is-object */ 917)
	  , aFunction          = __webpack_require__(/*! ./_a-function */ 925)
	  , anInstance         = __webpack_require__(/*! ./_an-instance */ 1109)
	  , forOf              = __webpack_require__(/*! ./_for-of */ 1110)
	  , speciesConstructor = __webpack_require__(/*! ./_species-constructor */ 1111)
	  , task               = __webpack_require__(/*! ./_task */ 1112).set
	  , microtask          = __webpack_require__(/*! ./_microtask */ 1113)()
	  , PROMISE            = 'Promise'
	  , TypeError          = global.TypeError
	  , process            = global.process
	  , $Promise           = global[PROMISE]
	  , process            = global.process
	  , isNode             = classof(process) == 'process'
	  , empty              = function(){ /* empty */ }
	  , Internal, GenericPromiseCapability, Wrapper;
	
	var USE_NATIVE = !!function(){
	  try {
	    // correct subclassing with @@species support
	    var promise     = $Promise.resolve(1)
	      , FakePromise = (promise.constructor = {})[__webpack_require__(/*! ./_wks */ 929)('species')] = function(exec){ exec(empty, empty); };
	    // unhandled rejections tracking support, NodeJS Promise without it fails @@species test
	    return (isNode || typeof PromiseRejectionEvent == 'function') && promise.then(empty) instanceof FakePromise;
	  } catch(e){ /* empty */ }
	}();
	
	// helpers
	var sameConstructor = function(a, b){
	  // with library wrapper special case
	  return a === b || a === $Promise && b === Wrapper;
	};
	var isThenable = function(it){
	  var then;
	  return isObject(it) && typeof (then = it.then) == 'function' ? then : false;
	};
	var newPromiseCapability = function(C){
	  return sameConstructor($Promise, C)
	    ? new PromiseCapability(C)
	    : new GenericPromiseCapability(C);
	};
	var PromiseCapability = GenericPromiseCapability = function(C){
	  var resolve, reject;
	  this.promise = new C(function($$resolve, $$reject){
	    if(resolve !== undefined || reject !== undefined)throw TypeError('Bad Promise constructor');
	    resolve = $$resolve;
	    reject  = $$reject;
	  });
	  this.resolve = aFunction(resolve);
	  this.reject  = aFunction(reject);
	};
	var perform = function(exec){
	  try {
	    exec();
	  } catch(e){
	    return {error: e};
	  }
	};
	var notify = function(promise, isReject){
	  if(promise._n)return;
	  promise._n = true;
	  var chain = promise._c;
	  microtask(function(){
	    var value = promise._v
	      , ok    = promise._s == 1
	      , i     = 0;
	    var run = function(reaction){
	      var handler = ok ? reaction.ok : reaction.fail
	        , resolve = reaction.resolve
	        , reject  = reaction.reject
	        , domain  = reaction.domain
	        , result, then;
	      try {
	        if(handler){
	          if(!ok){
	            if(promise._h == 2)onHandleUnhandled(promise);
	            promise._h = 1;
	          }
	          if(handler === true)result = value;
	          else {
	            if(domain)domain.enter();
	            result = handler(value);
	            if(domain)domain.exit();
	          }
	          if(result === reaction.promise){
	            reject(TypeError('Promise-chain cycle'));
	          } else if(then = isThenable(result)){
	            then.call(result, resolve, reject);
	          } else resolve(result);
	        } else reject(value);
	      } catch(e){
	        reject(e);
	      }
	    };
	    while(chain.length > i)run(chain[i++]); // variable length - can't use forEach
	    promise._c = [];
	    promise._n = false;
	    if(isReject && !promise._h)onUnhandled(promise);
	  });
	};
	var onUnhandled = function(promise){
	  task.call(global, function(){
	    var value = promise._v
	      , abrupt, handler, console;
	    if(isUnhandled(promise)){
	      abrupt = perform(function(){
	        if(isNode){
	          process.emit('unhandledRejection', value, promise);
	        } else if(handler = global.onunhandledrejection){
	          handler({promise: promise, reason: value});
	        } else if((console = global.console) && console.error){
	          console.error('Unhandled promise rejection', value);
	        }
	      });
	      // Browsers should not trigger `rejectionHandled` event if it was handled here, NodeJS - should
	      promise._h = isNode || isUnhandled(promise) ? 2 : 1;
	    } promise._a = undefined;
	    if(abrupt)throw abrupt.error;
	  });
	};
	var isUnhandled = function(promise){
	  if(promise._h == 1)return false;
	  var chain = promise._a || promise._c
	    , i     = 0
	    , reaction;
	  while(chain.length > i){
	    reaction = chain[i++];
	    if(reaction.fail || !isUnhandled(reaction.promise))return false;
	  } return true;
	};
	var onHandleUnhandled = function(promise){
	  task.call(global, function(){
	    var handler;
	    if(isNode){
	      process.emit('rejectionHandled', promise);
	    } else if(handler = global.onrejectionhandled){
	      handler({promise: promise, reason: promise._v});
	    }
	  });
	};
	var $reject = function(value){
	  var promise = this;
	  if(promise._d)return;
	  promise._d = true;
	  promise = promise._w || promise; // unwrap
	  promise._v = value;
	  promise._s = 2;
	  if(!promise._a)promise._a = promise._c.slice();
	  notify(promise, true);
	};
	var $resolve = function(value){
	  var promise = this
	    , then;
	  if(promise._d)return;
	  promise._d = true;
	  promise = promise._w || promise; // unwrap
	  try {
	    if(promise === value)throw TypeError("Promise can't be resolved itself");
	    if(then = isThenable(value)){
	      microtask(function(){
	        var wrapper = {_w: promise, _d: false}; // wrap
	        try {
	          then.call(value, ctx($resolve, wrapper, 1), ctx($reject, wrapper, 1));
	        } catch(e){
	          $reject.call(wrapper, e);
	        }
	      });
	    } else {
	      promise._v = value;
	      promise._s = 1;
	      notify(promise, false);
	    }
	  } catch(e){
	    $reject.call({_w: promise, _d: false}, e); // wrap
	  }
	};
	
	// constructor polyfill
	if(!USE_NATIVE){
	  // 25.4.3.1 Promise(executor)
	  $Promise = function Promise(executor){
	    anInstance(this, $Promise, PROMISE, '_h');
	    aFunction(executor);
	    Internal.call(this);
	    try {
	      executor(ctx($resolve, this, 1), ctx($reject, this, 1));
	    } catch(err){
	      $reject.call(this, err);
	    }
	  };
	  Internal = function Promise(executor){
	    this._c = [];             // <- awaiting reactions
	    this._a = undefined;      // <- checked in isUnhandled reactions
	    this._s = 0;              // <- state
	    this._d = false;          // <- done
	    this._v = undefined;      // <- value
	    this._h = 0;              // <- rejection state, 0 - default, 1 - handled, 2 - unhandled
	    this._n = false;          // <- notify
	  };
	  Internal.prototype = __webpack_require__(/*! ./_redefine-all */ 1114)($Promise.prototype, {
	    // 25.4.5.3 Promise.prototype.then(onFulfilled, onRejected)
	    then: function then(onFulfilled, onRejected){
	      var reaction    = newPromiseCapability(speciesConstructor(this, $Promise));
	      reaction.ok     = typeof onFulfilled == 'function' ? onFulfilled : true;
	      reaction.fail   = typeof onRejected == 'function' && onRejected;
	      reaction.domain = isNode ? process.domain : undefined;
	      this._c.push(reaction);
	      if(this._a)this._a.push(reaction);
	      if(this._s)notify(this, false);
	      return reaction.promise;
	    },
	    // 25.4.5.1 Promise.prototype.catch(onRejected)
	    'catch': function(onRejected){
	      return this.then(undefined, onRejected);
	    }
	  });
	  PromiseCapability = function(){
	    var promise  = new Internal;
	    this.promise = promise;
	    this.resolve = ctx($resolve, promise, 1);
	    this.reject  = ctx($reject, promise, 1);
	  };
	}
	
	$export($export.G + $export.W + $export.F * !USE_NATIVE, {Promise: $Promise});
	__webpack_require__(/*! ./_set-to-string-tag */ 928)($Promise, PROMISE);
	__webpack_require__(/*! ./_set-species */ 1096)(PROMISE);
	Wrapper = __webpack_require__(/*! ./_core */ 913)[PROMISE];
	
	// statics
	$export($export.S + $export.F * !USE_NATIVE, PROMISE, {
	  // 25.4.4.5 Promise.reject(r)
	  reject: function reject(r){
	    var capability = newPromiseCapability(this)
	      , $$reject   = capability.reject;
	    $$reject(r);
	    return capability.promise;
	  }
	});
	$export($export.S + $export.F * (LIBRARY || !USE_NATIVE), PROMISE, {
	  // 25.4.4.6 Promise.resolve(x)
	  resolve: function resolve(x){
	    // instanceof instead of internal slot check because we should fix it without replacement native Promise core
	    if(x instanceof $Promise && sameConstructor(x.constructor, this))return x;
	    var capability = newPromiseCapability(this)
	      , $$resolve  = capability.resolve;
	    $$resolve(x);
	    return capability.promise;
	  }
	});
	$export($export.S + $export.F * !(USE_NATIVE && __webpack_require__(/*! ./_iter-detect */ 1069)(function(iter){
	  $Promise.all(iter)['catch'](empty);
	})), PROMISE, {
	  // 25.4.4.1 Promise.all(iterable)
	  all: function all(iterable){
	    var C          = this
	      , capability = newPromiseCapability(C)
	      , resolve    = capability.resolve
	      , reject     = capability.reject;
	    var abrupt = perform(function(){
	      var values    = []
	        , index     = 0
	        , remaining = 1;
	      forOf(iterable, false, function(promise){
	        var $index        = index++
	          , alreadyCalled = false;
	        values.push(undefined);
	        remaining++;
	        C.resolve(promise).then(function(value){
	          if(alreadyCalled)return;
	          alreadyCalled  = true;
	          values[$index] = value;
	          --remaining || resolve(values);
	        }, reject);
	      });
	      --remaining || resolve(values);
	    });
	    if(abrupt)reject(abrupt.error);
	    return capability.promise;
	  },
	  // 25.4.4.4 Promise.race(iterable)
	  race: function race(iterable){
	    var C          = this
	      , capability = newPromiseCapability(C)
	      , reject     = capability.reject;
	    var abrupt = perform(function(){
	      forOf(iterable, false, function(promise){
	        C.resolve(promise).then(capability.resolve, reject);
	      });
	    });
	    if(abrupt)reject(abrupt.error);
	    return capability.promise;
	  }
	});

/***/ },

/***/ 1109:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_an-instance.js ***!
  \************************************************************/
/***/ function(module, exports) {

	module.exports = function(it, Constructor, name, forbiddenField){
	  if(!(it instanceof Constructor) || (forbiddenField !== undefined && forbiddenField in it)){
	    throw TypeError(name + ': incorrect invocation!');
	  } return it;
	};

/***/ },

/***/ 1110:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_for-of.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var ctx         = __webpack_require__(/*! ./_ctx */ 924)
	  , call        = __webpack_require__(/*! ./_iter-call */ 1065)
	  , isArrayIter = __webpack_require__(/*! ./_is-array-iter */ 1066)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 916)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 941)
	  , getIterFn   = __webpack_require__(/*! ./core.get-iterator-method */ 1068)
	  , BREAK       = {}
	  , RETURN      = {};
	var exports = module.exports = function(iterable, entries, fn, that, ITERATOR){
	  var iterFn = ITERATOR ? function(){ return iterable; } : getIterFn(iterable)
	    , f      = ctx(fn, that, entries ? 2 : 1)
	    , index  = 0
	    , length, step, iterator, result;
	  if(typeof iterFn != 'function')throw TypeError(iterable + ' is not iterable!');
	  // fast case for arrays with default iterator
	  if(isArrayIter(iterFn))for(length = toLength(iterable.length); length > index; index++){
	    result = entries ? f(anObject(step = iterable[index])[0], step[1]) : f(iterable[index]);
	    if(result === BREAK || result === RETURN)return result;
	  } else for(iterator = iterFn.call(iterable); !(step = iterator.next()).done; ){
	    result = call(iterator, f, step.value, entries);
	    if(result === BREAK || result === RETURN)return result;
	  }
	};
	exports.BREAK  = BREAK;
	exports.RETURN = RETURN;

/***/ },

/***/ 1111:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_species-constructor.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 7.3.20 SpeciesConstructor(O, defaultConstructor)
	var anObject  = __webpack_require__(/*! ./_an-object */ 916)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 925)
	  , SPECIES   = __webpack_require__(/*! ./_wks */ 929)('species');
	module.exports = function(O, D){
	  var C = anObject(O).constructor, S;
	  return C === undefined || (S = anObject(C)[SPECIES]) == undefined ? D : aFunction(S);
	};

/***/ },

/***/ 1112:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_task.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	var ctx                = __webpack_require__(/*! ./_ctx */ 924)
	  , invoke             = __webpack_require__(/*! ./_invoke */ 982)
	  , html               = __webpack_require__(/*! ./_html */ 952)
	  , cel                = __webpack_require__(/*! ./_dom-create */ 919)
	  , global             = __webpack_require__(/*! ./_global */ 908)
	  , process            = global.process
	  , setTask            = global.setImmediate
	  , clearTask          = global.clearImmediate
	  , MessageChannel     = global.MessageChannel
	  , counter            = 0
	  , queue              = {}
	  , ONREADYSTATECHANGE = 'onreadystatechange'
	  , defer, channel, port;
	var run = function(){
	  var id = +this;
	  if(queue.hasOwnProperty(id)){
	    var fn = queue[id];
	    delete queue[id];
	    fn();
	  }
	};
	var listener = function(event){
	  run.call(event.data);
	};
	// Node.js 0.9+ & IE10+ has setImmediate, otherwise:
	if(!setTask || !clearTask){
	  setTask = function setImmediate(fn){
	    var args = [], i = 1;
	    while(arguments.length > i)args.push(arguments[i++]);
	    queue[++counter] = function(){
	      invoke(typeof fn == 'function' ? fn : Function(fn), args);
	    };
	    defer(counter);
	    return counter;
	  };
	  clearTask = function clearImmediate(id){
	    delete queue[id];
	  };
	  // Node.js 0.8-
	  if(__webpack_require__(/*! ./_cof */ 938)(process) == 'process'){
	    defer = function(id){
	      process.nextTick(ctx(run, id, 1));
	    };
	  // Browsers with MessageChannel, includes WebWorkers
	  } else if(MessageChannel){
	    channel = new MessageChannel;
	    port    = channel.port2;
	    channel.port1.onmessage = listener;
	    defer = ctx(port.postMessage, port, 1);
	  // Browsers with postMessage, skip WebWorkers
	  // IE8 has postMessage, but it's sync & typeof its postMessage is 'object'
	  } else if(global.addEventListener && typeof postMessage == 'function' && !global.importScripts){
	    defer = function(id){
	      global.postMessage(id + '', '*');
	    };
	    global.addEventListener('message', listener, false);
	  // IE8-
	  } else if(ONREADYSTATECHANGE in cel('script')){
	    defer = function(id){
	      html.appendChild(cel('script'))[ONREADYSTATECHANGE] = function(){
	        html.removeChild(this);
	        run.call(id);
	      };
	    };
	  // Rest old browsers
	  } else {
	    defer = function(id){
	      setTimeout(ctx(run, id, 1), 0);
	    };
	  }
	}
	module.exports = {
	  set:   setTask,
	  clear: clearTask
	};

/***/ },

/***/ 1113:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_microtask.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 908)
	  , macrotask = __webpack_require__(/*! ./_task */ 1112).set
	  , Observer  = global.MutationObserver || global.WebKitMutationObserver
	  , process   = global.process
	  , Promise   = global.Promise
	  , isNode    = __webpack_require__(/*! ./_cof */ 938)(process) == 'process';
	
	module.exports = function(){
	  var head, last, notify;
	
	  var flush = function(){
	    var parent, fn;
	    if(isNode && (parent = process.domain))parent.exit();
	    while(head){
	      fn   = head.fn;
	      head = head.next;
	      try {
	        fn();
	      } catch(e){
	        if(head)notify();
	        else last = undefined;
	        throw e;
	      }
	    } last = undefined;
	    if(parent)parent.enter();
	  };
	
	  // Node.js
	  if(isNode){
	    notify = function(){
	      process.nextTick(flush);
	    };
	  // browsers with MutationObserver
	  } else if(Observer){
	    var toggle = true
	      , node   = document.createTextNode('');
	    new Observer(flush).observe(node, {characterData: true}); // eslint-disable-line no-new
	    notify = function(){
	      node.data = toggle = !toggle;
	    };
	  // environments with maybe non-completely correct, but existent Promise
	  } else if(Promise && Promise.resolve){
	    var promise = Promise.resolve();
	    notify = function(){
	      promise.then(flush);
	    };
	  // for other environments - macrotask based on:
	  // - setImmediate
	  // - MessageChannel
	  // - window.postMessag
	  // - onreadystatechange
	  // - setTimeout
	  } else {
	    notify = function(){
	      // strange IE + webpack dev server bug - use .call(global)
	      macrotask.call(global, flush);
	    };
	  }
	
	  return function(fn){
	    var task = {fn: fn, next: undefined};
	    if(last)last.next = task;
	    if(!head){
	      head = task;
	      notify();
	    } last = task;
	  };
	};

/***/ },

/***/ 1114:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_redefine-all.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var redefine = __webpack_require__(/*! ./_redefine */ 922);
	module.exports = function(target, src, safe){
	  for(var key in src)redefine(target, key, src[key], safe);
	  return target;
	};

/***/ },

/***/ 1115:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.map.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var strong = __webpack_require__(/*! ./_collection-strong */ 1116);
	
	// 23.1 Map Objects
	module.exports = __webpack_require__(/*! ./_collection */ 1117)('Map', function(get){
	  return function Map(){ return get(this, arguments.length > 0 ? arguments[0] : undefined); };
	}, {
	  // 23.1.3.6 Map.prototype.get(key)
	  get: function get(key){
	    var entry = strong.getEntry(this, key);
	    return entry && entry.v;
	  },
	  // 23.1.3.9 Map.prototype.set(key, value)
	  set: function set(key, value){
	    return strong.def(this, key === 0 ? 0 : key, value);
	  }
	}, strong, true);

/***/ },

/***/ 1116:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-strong.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var dP          = __webpack_require__(/*! ./_object-dp */ 915).f
	  , create      = __webpack_require__(/*! ./_object-create */ 950)
	  , redefineAll = __webpack_require__(/*! ./_redefine-all */ 1114)
	  , ctx         = __webpack_require__(/*! ./_ctx */ 924)
	  , anInstance  = __webpack_require__(/*! ./_an-instance */ 1109)
	  , defined     = __webpack_require__(/*! ./_defined */ 939)
	  , forOf       = __webpack_require__(/*! ./_for-of */ 1110)
	  , $iterDefine = __webpack_require__(/*! ./_iter-define */ 1032)
	  , step        = __webpack_require__(/*! ./_iter-step */ 1098)
	  , setSpecies  = __webpack_require__(/*! ./_set-species */ 1096)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 910)
	  , fastKey     = __webpack_require__(/*! ./_meta */ 926).fastKey
	  , SIZE        = DESCRIPTORS ? '_s' : 'size';
	
	var getEntry = function(that, key){
	  // fast case
	  var index = fastKey(key), entry;
	  if(index !== 'F')return that._i[index];
	  // frozen object case
	  for(entry = that._f; entry; entry = entry.n){
	    if(entry.k == key)return entry;
	  }
	};
	
	module.exports = {
	  getConstructor: function(wrapper, NAME, IS_MAP, ADDER){
	    var C = wrapper(function(that, iterable){
	      anInstance(that, C, NAME, '_i');
	      that._i = create(null); // index
	      that._f = undefined;    // first entry
	      that._l = undefined;    // last entry
	      that[SIZE] = 0;         // size
	      if(iterable != undefined)forOf(iterable, IS_MAP, that[ADDER], that);
	    });
	    redefineAll(C.prototype, {
	      // 23.1.3.1 Map.prototype.clear()
	      // 23.2.3.2 Set.prototype.clear()
	      clear: function clear(){
	        for(var that = this, data = that._i, entry = that._f; entry; entry = entry.n){
	          entry.r = true;
	          if(entry.p)entry.p = entry.p.n = undefined;
	          delete data[entry.i];
	        }
	        that._f = that._l = undefined;
	        that[SIZE] = 0;
	      },
	      // 23.1.3.3 Map.prototype.delete(key)
	      // 23.2.3.4 Set.prototype.delete(value)
	      'delete': function(key){
	        var that  = this
	          , entry = getEntry(that, key);
	        if(entry){
	          var next = entry.n
	            , prev = entry.p;
	          delete that._i[entry.i];
	          entry.r = true;
	          if(prev)prev.n = next;
	          if(next)next.p = prev;
	          if(that._f == entry)that._f = next;
	          if(that._l == entry)that._l = prev;
	          that[SIZE]--;
	        } return !!entry;
	      },
	      // 23.2.3.6 Set.prototype.forEach(callbackfn, thisArg = undefined)
	      // 23.1.3.5 Map.prototype.forEach(callbackfn, thisArg = undefined)
	      forEach: function forEach(callbackfn /*, that = undefined */){
	        anInstance(this, C, 'forEach');
	        var f = ctx(callbackfn, arguments.length > 1 ? arguments[1] : undefined, 3)
	          , entry;
	        while(entry = entry ? entry.n : this._f){
	          f(entry.v, entry.k, this);
	          // revert to the last existing entry
	          while(entry && entry.r)entry = entry.p;
	        }
	      },
	      // 23.1.3.7 Map.prototype.has(key)
	      // 23.2.3.7 Set.prototype.has(value)
	      has: function has(key){
	        return !!getEntry(this, key);
	      }
	    });
	    if(DESCRIPTORS)dP(C.prototype, 'size', {
	      get: function(){
	        return defined(this[SIZE]);
	      }
	    });
	    return C;
	  },
	  def: function(that, key, value){
	    var entry = getEntry(that, key)
	      , prev, index;
	    // change existing entry
	    if(entry){
	      entry.v = value;
	    // create new entry
	    } else {
	      that._l = entry = {
	        i: index = fastKey(key, true), // <- index
	        k: key,                        // <- key
	        v: value,                      // <- value
	        p: prev = that._l,             // <- previous entry
	        n: undefined,                  // <- next entry
	        r: false                       // <- removed
	      };
	      if(!that._f)that._f = entry;
	      if(prev)prev.n = entry;
	      that[SIZE]++;
	      // add to index
	      if(index !== 'F')that._i[index] = entry;
	    } return that;
	  },
	  getEntry: getEntry,
	  setStrong: function(C, NAME, IS_MAP){
	    // add .keys, .values, .entries, [@@iterator]
	    // 23.1.3.4, 23.1.3.8, 23.1.3.11, 23.1.3.12, 23.2.3.5, 23.2.3.8, 23.2.3.10, 23.2.3.11
	    $iterDefine(C, NAME, function(iterated, kind){
	      this._t = iterated;  // target
	      this._k = kind;      // kind
	      this._l = undefined; // previous
	    }, function(){
	      var that  = this
	        , kind  = that._k
	        , entry = that._l;
	      // revert to the last existing entry
	      while(entry && entry.r)entry = entry.p;
	      // get next entry
	      if(!that._t || !(that._l = entry = entry ? entry.n : that._t._f)){
	        // or finish the iteration
	        that._t = undefined;
	        return step(1);
	      }
	      // return step by kind
	      if(kind == 'keys'  )return step(0, entry.k);
	      if(kind == 'values')return step(0, entry.v);
	      return step(0, [entry.k, entry.v]);
	    }, IS_MAP ? 'entries' : 'values' , !IS_MAP, true);
	
	    // add [@@species], 23.1.2.2, 23.2.2.2
	    setSpecies(NAME);
	  }
	};

/***/ },

/***/ 1117:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global            = __webpack_require__(/*! ./_global */ 908)
	  , $export           = __webpack_require__(/*! ./_export */ 912)
	  , redefine          = __webpack_require__(/*! ./_redefine */ 922)
	  , redefineAll       = __webpack_require__(/*! ./_redefine-all */ 1114)
	  , meta              = __webpack_require__(/*! ./_meta */ 926)
	  , forOf             = __webpack_require__(/*! ./_for-of */ 1110)
	  , anInstance        = __webpack_require__(/*! ./_an-instance */ 1109)
	  , isObject          = __webpack_require__(/*! ./_is-object */ 917)
	  , fails             = __webpack_require__(/*! ./_fails */ 911)
	  , $iterDetect       = __webpack_require__(/*! ./_iter-detect */ 1069)
	  , setToStringTag    = __webpack_require__(/*! ./_set-to-string-tag */ 928)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 992);
	
	module.exports = function(NAME, wrapper, methods, common, IS_MAP, IS_WEAK){
	  var Base  = global[NAME]
	    , C     = Base
	    , ADDER = IS_MAP ? 'set' : 'add'
	    , proto = C && C.prototype
	    , O     = {};
	  var fixMethod = function(KEY){
	    var fn = proto[KEY];
	    redefine(proto, KEY,
	      KEY == 'delete' ? function(a){
	        return IS_WEAK && !isObject(a) ? false : fn.call(this, a === 0 ? 0 : a);
	      } : KEY == 'has' ? function has(a){
	        return IS_WEAK && !isObject(a) ? false : fn.call(this, a === 0 ? 0 : a);
	      } : KEY == 'get' ? function get(a){
	        return IS_WEAK && !isObject(a) ? undefined : fn.call(this, a === 0 ? 0 : a);
	      } : KEY == 'add' ? function add(a){ fn.call(this, a === 0 ? 0 : a); return this; }
	        : function set(a, b){ fn.call(this, a === 0 ? 0 : a, b); return this; }
	    );
	  };
	  if(typeof C != 'function' || !(IS_WEAK || proto.forEach && !fails(function(){
	    new C().entries().next();
	  }))){
	    // create collection constructor
	    C = common.getConstructor(wrapper, NAME, IS_MAP, ADDER);
	    redefineAll(C.prototype, methods);
	    meta.NEED = true;
	  } else {
	    var instance             = new C
	      // early implementations not supports chaining
	      , HASNT_CHAINING       = instance[ADDER](IS_WEAK ? {} : -0, 1) != instance
	      // V8 ~  Chromium 40- weak-collections throws on primitives, but should return false
	      , THROWS_ON_PRIMITIVES = fails(function(){ instance.has(1); })
	      // most early implementations doesn't supports iterables, most modern - not close it correctly
	      , ACCEPT_ITERABLES     = $iterDetect(function(iter){ new C(iter); }) // eslint-disable-line no-new
	      // for early implementations -0 and +0 not the same
	      , BUGGY_ZERO = !IS_WEAK && fails(function(){
	        // V8 ~ Chromium 42- fails only with 5+ elements
	        var $instance = new C()
	          , index     = 5;
	        while(index--)$instance[ADDER](index, index);
	        return !$instance.has(-0);
	      });
	    if(!ACCEPT_ITERABLES){ 
	      C = wrapper(function(target, iterable){
	        anInstance(target, C, NAME);
	        var that = inheritIfRequired(new Base, target, C);
	        if(iterable != undefined)forOf(iterable, IS_MAP, that[ADDER], that);
	        return that;
	      });
	      C.prototype = proto;
	      proto.constructor = C;
	    }
	    if(THROWS_ON_PRIMITIVES || BUGGY_ZERO){
	      fixMethod('delete');
	      fixMethod('has');
	      IS_MAP && fixMethod('get');
	    }
	    if(BUGGY_ZERO || HASNT_CHAINING)fixMethod(ADDER);
	    // weak collections should not contains .clear method
	    if(IS_WEAK && proto.clear)delete proto.clear;
	  }
	
	  setToStringTag(C, NAME);
	
	  O[NAME] = C;
	  $export($export.G + $export.W + $export.F * (C != Base), O);
	
	  if(!IS_WEAK)common.setStrong(C, NAME, IS_MAP);
	
	  return C;
	};

/***/ },

/***/ 1118:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.set.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var strong = __webpack_require__(/*! ./_collection-strong */ 1116);
	
	// 23.2 Set Objects
	module.exports = __webpack_require__(/*! ./_collection */ 1117)('Set', function(get){
	  return function Set(){ return get(this, arguments.length > 0 ? arguments[0] : undefined); };
	}, {
	  // 23.2.3.1 Set.prototype.add(value)
	  add: function add(value){
	    return strong.def(this, value = value === 0 ? 0 : value, value);
	  }
	}, strong);

/***/ },

/***/ 1119:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.weak-map.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var each         = __webpack_require__(/*! ./_array-methods */ 1076)(0)
	  , redefine     = __webpack_require__(/*! ./_redefine */ 922)
	  , meta         = __webpack_require__(/*! ./_meta */ 926)
	  , assign       = __webpack_require__(/*! ./_object-assign */ 973)
	  , weak         = __webpack_require__(/*! ./_collection-weak */ 1120)
	  , isObject     = __webpack_require__(/*! ./_is-object */ 917)
	  , getWeak      = meta.getWeak
	  , isExtensible = Object.isExtensible
	  , uncaughtFrozenStore = weak.ufstore
	  , tmp          = {}
	  , InternalMap;
	
	var wrapper = function(get){
	  return function WeakMap(){
	    return get(this, arguments.length > 0 ? arguments[0] : undefined);
	  };
	};
	
	var methods = {
	  // 23.3.3.3 WeakMap.prototype.get(key)
	  get: function get(key){
	    if(isObject(key)){
	      var data = getWeak(key);
	      if(data === true)return uncaughtFrozenStore(this).get(key);
	      return data ? data[this._i] : undefined;
	    }
	  },
	  // 23.3.3.5 WeakMap.prototype.set(key, value)
	  set: function set(key, value){
	    return weak.def(this, key, value);
	  }
	};
	
	// 23.3 WeakMap Objects
	var $WeakMap = module.exports = __webpack_require__(/*! ./_collection */ 1117)('WeakMap', wrapper, methods, weak, true, true);
	
	// IE11 WeakMap frozen keys fix
	if(new $WeakMap().set((Object.freeze || Object)(tmp), 7).get(tmp) != 7){
	  InternalMap = weak.getConstructor(wrapper);
	  assign(InternalMap.prototype, methods);
	  meta.NEED = true;
	  each(['delete', 'has', 'get', 'set'], function(key){
	    var proto  = $WeakMap.prototype
	      , method = proto[key];
	    redefine(proto, key, function(a, b){
	      // store frozen objects on internal weakmap shim
	      if(isObject(a) && !isExtensible(a)){
	        if(!this._f)this._f = new InternalMap;
	        var result = this._f[key](a, b);
	        return key == 'set' ? this : result;
	      // store all the rest on native weakmap
	      } return method.call(this, a, b);
	    });
	  });
	}

/***/ },

/***/ 1120:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-weak.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var redefineAll       = __webpack_require__(/*! ./_redefine-all */ 1114)
	  , getWeak           = __webpack_require__(/*! ./_meta */ 926).getWeak
	  , anObject          = __webpack_require__(/*! ./_an-object */ 916)
	  , isObject          = __webpack_require__(/*! ./_is-object */ 917)
	  , anInstance        = __webpack_require__(/*! ./_an-instance */ 1109)
	  , forOf             = __webpack_require__(/*! ./_for-of */ 1110)
	  , createArrayMethod = __webpack_require__(/*! ./_array-methods */ 1076)
	  , $has              = __webpack_require__(/*! ./_has */ 909)
	  , arrayFind         = createArrayMethod(5)
	  , arrayFindIndex    = createArrayMethod(6)
	  , id                = 0;
	
	// fallback for uncaught frozen keys
	var uncaughtFrozenStore = function(that){
	  return that._l || (that._l = new UncaughtFrozenStore);
	};
	var UncaughtFrozenStore = function(){
	  this.a = [];
	};
	var findUncaughtFrozen = function(store, key){
	  return arrayFind(store.a, function(it){
	    return it[0] === key;
	  });
	};
	UncaughtFrozenStore.prototype = {
	  get: function(key){
	    var entry = findUncaughtFrozen(this, key);
	    if(entry)return entry[1];
	  },
	  has: function(key){
	    return !!findUncaughtFrozen(this, key);
	  },
	  set: function(key, value){
	    var entry = findUncaughtFrozen(this, key);
	    if(entry)entry[1] = value;
	    else this.a.push([key, value]);
	  },
	  'delete': function(key){
	    var index = arrayFindIndex(this.a, function(it){
	      return it[0] === key;
	    });
	    if(~index)this.a.splice(index, 1);
	    return !!~index;
	  }
	};
	
	module.exports = {
	  getConstructor: function(wrapper, NAME, IS_MAP, ADDER){
	    var C = wrapper(function(that, iterable){
	      anInstance(that, C, NAME, '_i');
	      that._i = id++;      // collection id
	      that._l = undefined; // leak store for uncaught frozen objects
	      if(iterable != undefined)forOf(iterable, IS_MAP, that[ADDER], that);
	    });
	    redefineAll(C.prototype, {
	      // 23.3.3.2 WeakMap.prototype.delete(key)
	      // 23.4.3.3 WeakSet.prototype.delete(value)
	      'delete': function(key){
	        if(!isObject(key))return false;
	        var data = getWeak(key);
	        if(data === true)return uncaughtFrozenStore(this)['delete'](key);
	        return data && $has(data, this._i) && delete data[this._i];
	      },
	      // 23.3.3.4 WeakMap.prototype.has(key)
	      // 23.4.3.4 WeakSet.prototype.has(value)
	      has: function has(key){
	        if(!isObject(key))return false;
	        var data = getWeak(key);
	        if(data === true)return uncaughtFrozenStore(this).has(key);
	        return data && $has(data, this._i);
	      }
	    });
	    return C;
	  },
	  def: function(that, key, value){
	    var data = getWeak(anObject(key), true);
	    if(data === true)uncaughtFrozenStore(that).set(key, value);
	    else data[that._i] = value;
	    return that;
	  },
	  ufstore: uncaughtFrozenStore
	};

/***/ },

/***/ 1121:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.weak-set.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var weak = __webpack_require__(/*! ./_collection-weak */ 1120);
	
	// 23.4 WeakSet Objects
	__webpack_require__(/*! ./_collection */ 1117)('WeakSet', function(get){
	  return function WeakSet(){ return get(this, arguments.length > 0 ? arguments[0] : undefined); };
	}, {
	  // 23.4.3.1 WeakSet.prototype.add(value)
	  add: function add(value){
	    return weak.def(this, value, true);
	  }
	}, weak, false, true);

/***/ },

/***/ 1122:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.array-buffer.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 912)
	  , $typed       = __webpack_require__(/*! ./_typed */ 1123)
	  , buffer       = __webpack_require__(/*! ./_typed-buffer */ 1124)
	  , anObject     = __webpack_require__(/*! ./_an-object */ 916)
	  , toIndex      = __webpack_require__(/*! ./_to-index */ 943)
	  , toLength     = __webpack_require__(/*! ./_to-length */ 941)
	  , isObject     = __webpack_require__(/*! ./_is-object */ 917)
	  , ArrayBuffer  = __webpack_require__(/*! ./_global */ 908).ArrayBuffer
	  , speciesConstructor = __webpack_require__(/*! ./_species-constructor */ 1111)
	  , $ArrayBuffer = buffer.ArrayBuffer
	  , $DataView    = buffer.DataView
	  , $isView      = $typed.ABV && ArrayBuffer.isView
	  , $slice       = $ArrayBuffer.prototype.slice
	  , VIEW         = $typed.VIEW
	  , ARRAY_BUFFER = 'ArrayBuffer';
	
	$export($export.G + $export.W + $export.F * (ArrayBuffer !== $ArrayBuffer), {ArrayBuffer: $ArrayBuffer});
	
	$export($export.S + $export.F * !$typed.CONSTR, ARRAY_BUFFER, {
	  // 24.1.3.1 ArrayBuffer.isView(arg)
	  isView: function isView(it){
	    return $isView && $isView(it) || isObject(it) && VIEW in it;
	  }
	});
	
	$export($export.P + $export.U + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  return !new $ArrayBuffer(2).slice(1, undefined).byteLength;
	}), ARRAY_BUFFER, {
	  // 24.1.4.3 ArrayBuffer.prototype.slice(start, end)
	  slice: function slice(start, end){
	    if($slice !== undefined && end === undefined)return $slice.call(anObject(this), start); // FF fix
	    var len    = anObject(this).byteLength
	      , first  = toIndex(start, len)
	      , final  = toIndex(end === undefined ? len : end, len)
	      , result = new (speciesConstructor(this, $ArrayBuffer))(toLength(final - first))
	      , viewS  = new $DataView(this)
	      , viewT  = new $DataView(result)
	      , index  = 0;
	    while(first < final){
	      viewT.setUint8(index++, viewS.getUint8(first++));
	    } return result;
	  }
	});
	
	__webpack_require__(/*! ./_set-species */ 1096)(ARRAY_BUFFER);

/***/ },

/***/ 1123:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(/*! ./_global */ 908)
	  , hide   = __webpack_require__(/*! ./_hide */ 914)
	  , uid    = __webpack_require__(/*! ./_uid */ 923)
	  , TYPED  = uid('typed_array')
	  , VIEW   = uid('view')
	  , ABV    = !!(global.ArrayBuffer && global.DataView)
	  , CONSTR = ABV
	  , i = 0, l = 9, Typed;
	
	var TypedArrayConstructors = (
	  'Int8Array,Uint8Array,Uint8ClampedArray,Int16Array,Uint16Array,Int32Array,Uint32Array,Float32Array,Float64Array'
	).split(',');
	
	while(i < l){
	  if(Typed = global[TypedArrayConstructors[i++]]){
	    hide(Typed.prototype, TYPED, true);
	    hide(Typed.prototype, VIEW, true);
	  } else CONSTR = false;
	}
	
	module.exports = {
	  ABV:    ABV,
	  CONSTR: CONSTR,
	  TYPED:  TYPED,
	  VIEW:   VIEW
	};

/***/ },

/***/ 1124:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed-buffer.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global         = __webpack_require__(/*! ./_global */ 908)
	  , DESCRIPTORS    = __webpack_require__(/*! ./_descriptors */ 910)
	  , LIBRARY        = __webpack_require__(/*! ./_library */ 932)
	  , $typed         = __webpack_require__(/*! ./_typed */ 1123)
	  , hide           = __webpack_require__(/*! ./_hide */ 914)
	  , redefineAll    = __webpack_require__(/*! ./_redefine-all */ 1114)
	  , fails          = __webpack_require__(/*! ./_fails */ 911)
	  , anInstance     = __webpack_require__(/*! ./_an-instance */ 1109)
	  , toInteger      = __webpack_require__(/*! ./_to-integer */ 942)
	  , toLength       = __webpack_require__(/*! ./_to-length */ 941)
	  , gOPN           = __webpack_require__(/*! ./_object-gopn */ 954).f
	  , dP             = __webpack_require__(/*! ./_object-dp */ 915).f
	  , arrayFill      = __webpack_require__(/*! ./_array-fill */ 1092)
	  , setToStringTag = __webpack_require__(/*! ./_set-to-string-tag */ 928)
	  , ARRAY_BUFFER   = 'ArrayBuffer'
	  , DATA_VIEW      = 'DataView'
	  , PROTOTYPE      = 'prototype'
	  , WRONG_LENGTH   = 'Wrong length!'
	  , WRONG_INDEX    = 'Wrong index!'
	  , $ArrayBuffer   = global[ARRAY_BUFFER]
	  , $DataView      = global[DATA_VIEW]
	  , Math           = global.Math
	  , RangeError     = global.RangeError
	  , Infinity       = global.Infinity
	  , BaseBuffer     = $ArrayBuffer
	  , abs            = Math.abs
	  , pow            = Math.pow
	  , floor          = Math.floor
	  , log            = Math.log
	  , LN2            = Math.LN2
	  , BUFFER         = 'buffer'
	  , BYTE_LENGTH    = 'byteLength'
	  , BYTE_OFFSET    = 'byteOffset'
	  , $BUFFER        = DESCRIPTORS ? '_b' : BUFFER
	  , $LENGTH        = DESCRIPTORS ? '_l' : BYTE_LENGTH
	  , $OFFSET        = DESCRIPTORS ? '_o' : BYTE_OFFSET;
	
	// IEEE754 conversions based on https://github.com/feross/ieee754
	var packIEEE754 = function(value, mLen, nBytes){
	  var buffer = Array(nBytes)
	    , eLen   = nBytes * 8 - mLen - 1
	    , eMax   = (1 << eLen) - 1
	    , eBias  = eMax >> 1
	    , rt     = mLen === 23 ? pow(2, -24) - pow(2, -77) : 0
	    , i      = 0
	    , s      = value < 0 || value === 0 && 1 / value < 0 ? 1 : 0
	    , e, m, c;
	  value = abs(value)
	  if(value != value || value === Infinity){
	    m = value != value ? 1 : 0;
	    e = eMax;
	  } else {
	    e = floor(log(value) / LN2);
	    if(value * (c = pow(2, -e)) < 1){
	      e--;
	      c *= 2;
	    }
	    if(e + eBias >= 1){
	      value += rt / c;
	    } else {
	      value += rt * pow(2, 1 - eBias);
	    }
	    if(value * c >= 2){
	      e++;
	      c /= 2;
	    }
	    if(e + eBias >= eMax){
	      m = 0;
	      e = eMax;
	    } else if(e + eBias >= 1){
	      m = (value * c - 1) * pow(2, mLen);
	      e = e + eBias;
	    } else {
	      m = value * pow(2, eBias - 1) * pow(2, mLen);
	      e = 0;
	    }
	  }
	  for(; mLen >= 8; buffer[i++] = m & 255, m /= 256, mLen -= 8);
	  e = e << mLen | m;
	  eLen += mLen;
	  for(; eLen > 0; buffer[i++] = e & 255, e /= 256, eLen -= 8);
	  buffer[--i] |= s * 128;
	  return buffer;
	};
	var unpackIEEE754 = function(buffer, mLen, nBytes){
	  var eLen  = nBytes * 8 - mLen - 1
	    , eMax  = (1 << eLen) - 1
	    , eBias = eMax >> 1
	    , nBits = eLen - 7
	    , i     = nBytes - 1
	    , s     = buffer[i--]
	    , e     = s & 127
	    , m;
	  s >>= 7;
	  for(; nBits > 0; e = e * 256 + buffer[i], i--, nBits -= 8);
	  m = e & (1 << -nBits) - 1;
	  e >>= -nBits;
	  nBits += mLen;
	  for(; nBits > 0; m = m * 256 + buffer[i], i--, nBits -= 8);
	  if(e === 0){
	    e = 1 - eBias;
	  } else if(e === eMax){
	    return m ? NaN : s ? -Infinity : Infinity;
	  } else {
	    m = m + pow(2, mLen);
	    e = e - eBias;
	  } return (s ? -1 : 1) * m * pow(2, e - mLen);
	};
	
	var unpackI32 = function(bytes){
	  return bytes[3] << 24 | bytes[2] << 16 | bytes[1] << 8 | bytes[0];
	};
	var packI8 = function(it){
	  return [it & 0xff];
	};
	var packI16 = function(it){
	  return [it & 0xff, it >> 8 & 0xff];
	};
	var packI32 = function(it){
	  return [it & 0xff, it >> 8 & 0xff, it >> 16 & 0xff, it >> 24 & 0xff];
	};
	var packF64 = function(it){
	  return packIEEE754(it, 52, 8);
	};
	var packF32 = function(it){
	  return packIEEE754(it, 23, 4);
	};
	
	var addGetter = function(C, key, internal){
	  dP(C[PROTOTYPE], key, {get: function(){ return this[internal]; }});
	};
	
	var get = function(view, bytes, index, isLittleEndian){
	  var numIndex = +index
	    , intIndex = toInteger(numIndex);
	  if(numIndex != intIndex || intIndex < 0 || intIndex + bytes > view[$LENGTH])throw RangeError(WRONG_INDEX);
	  var store = view[$BUFFER]._b
	    , start = intIndex + view[$OFFSET]
	    , pack  = store.slice(start, start + bytes);
	  return isLittleEndian ? pack : pack.reverse();
	};
	var set = function(view, bytes, index, conversion, value, isLittleEndian){
	  var numIndex = +index
	    , intIndex = toInteger(numIndex);
	  if(numIndex != intIndex || intIndex < 0 || intIndex + bytes > view[$LENGTH])throw RangeError(WRONG_INDEX);
	  var store = view[$BUFFER]._b
	    , start = intIndex + view[$OFFSET]
	    , pack  = conversion(+value);
	  for(var i = 0; i < bytes; i++)store[start + i] = pack[isLittleEndian ? i : bytes - i - 1];
	};
	
	var validateArrayBufferArguments = function(that, length){
	  anInstance(that, $ArrayBuffer, ARRAY_BUFFER);
	  var numberLength = +length
	    , byteLength   = toLength(numberLength);
	  if(numberLength != byteLength)throw RangeError(WRONG_LENGTH);
	  return byteLength;
	};
	
	if(!$typed.ABV){
	  $ArrayBuffer = function ArrayBuffer(length){
	    var byteLength = validateArrayBufferArguments(this, length);
	    this._b       = arrayFill.call(Array(byteLength), 0);
	    this[$LENGTH] = byteLength;
	  };
	
	  $DataView = function DataView(buffer, byteOffset, byteLength){
	    anInstance(this, $DataView, DATA_VIEW);
	    anInstance(buffer, $ArrayBuffer, DATA_VIEW);
	    var bufferLength = buffer[$LENGTH]
	      , offset       = toInteger(byteOffset);
	    if(offset < 0 || offset > bufferLength)throw RangeError('Wrong offset!');
	    byteLength = byteLength === undefined ? bufferLength - offset : toLength(byteLength);
	    if(offset + byteLength > bufferLength)throw RangeError(WRONG_LENGTH);
	    this[$BUFFER] = buffer;
	    this[$OFFSET] = offset;
	    this[$LENGTH] = byteLength;
	  };
	
	  if(DESCRIPTORS){
	    addGetter($ArrayBuffer, BYTE_LENGTH, '_l');
	    addGetter($DataView, BUFFER, '_b');
	    addGetter($DataView, BYTE_LENGTH, '_l');
	    addGetter($DataView, BYTE_OFFSET, '_o');
	  }
	
	  redefineAll($DataView[PROTOTYPE], {
	    getInt8: function getInt8(byteOffset){
	      return get(this, 1, byteOffset)[0] << 24 >> 24;
	    },
	    getUint8: function getUint8(byteOffset){
	      return get(this, 1, byteOffset)[0];
	    },
	    getInt16: function getInt16(byteOffset /*, littleEndian */){
	      var bytes = get(this, 2, byteOffset, arguments[1]);
	      return (bytes[1] << 8 | bytes[0]) << 16 >> 16;
	    },
	    getUint16: function getUint16(byteOffset /*, littleEndian */){
	      var bytes = get(this, 2, byteOffset, arguments[1]);
	      return bytes[1] << 8 | bytes[0];
	    },
	    getInt32: function getInt32(byteOffset /*, littleEndian */){
	      return unpackI32(get(this, 4, byteOffset, arguments[1]));
	    },
	    getUint32: function getUint32(byteOffset /*, littleEndian */){
	      return unpackI32(get(this, 4, byteOffset, arguments[1])) >>> 0;
	    },
	    getFloat32: function getFloat32(byteOffset /*, littleEndian */){
	      return unpackIEEE754(get(this, 4, byteOffset, arguments[1]), 23, 4);
	    },
	    getFloat64: function getFloat64(byteOffset /*, littleEndian */){
	      return unpackIEEE754(get(this, 8, byteOffset, arguments[1]), 52, 8);
	    },
	    setInt8: function setInt8(byteOffset, value){
	      set(this, 1, byteOffset, packI8, value);
	    },
	    setUint8: function setUint8(byteOffset, value){
	      set(this, 1, byteOffset, packI8, value);
	    },
	    setInt16: function setInt16(byteOffset, value /*, littleEndian */){
	      set(this, 2, byteOffset, packI16, value, arguments[2]);
	    },
	    setUint16: function setUint16(byteOffset, value /*, littleEndian */){
	      set(this, 2, byteOffset, packI16, value, arguments[2]);
	    },
	    setInt32: function setInt32(byteOffset, value /*, littleEndian */){
	      set(this, 4, byteOffset, packI32, value, arguments[2]);
	    },
	    setUint32: function setUint32(byteOffset, value /*, littleEndian */){
	      set(this, 4, byteOffset, packI32, value, arguments[2]);
	    },
	    setFloat32: function setFloat32(byteOffset, value /*, littleEndian */){
	      set(this, 4, byteOffset, packF32, value, arguments[2]);
	    },
	    setFloat64: function setFloat64(byteOffset, value /*, littleEndian */){
	      set(this, 8, byteOffset, packF64, value, arguments[2]);
	    }
	  });
	} else {
	  if(!fails(function(){
	    new $ArrayBuffer;     // eslint-disable-line no-new
	  }) || !fails(function(){
	    new $ArrayBuffer(.5); // eslint-disable-line no-new
	  })){
	    $ArrayBuffer = function ArrayBuffer(length){
	      return new BaseBuffer(validateArrayBufferArguments(this, length));
	    };
	    var ArrayBufferProto = $ArrayBuffer[PROTOTYPE] = BaseBuffer[PROTOTYPE];
	    for(var keys = gOPN(BaseBuffer), j = 0, key; keys.length > j; ){
	      if(!((key = keys[j++]) in $ArrayBuffer))hide($ArrayBuffer, key, BaseBuffer[key]);
	    };
	    if(!LIBRARY)ArrayBufferProto.constructor = $ArrayBuffer;
	  }
	  // iOS Safari 7.x bug
	  var view = new $DataView(new $ArrayBuffer(2))
	    , $setInt8 = $DataView[PROTOTYPE].setInt8;
	  view.setInt8(0, 2147483648);
	  view.setInt8(1, 2147483649);
	  if(view.getInt8(0) || !view.getInt8(1))redefineAll($DataView[PROTOTYPE], {
	    setInt8: function setInt8(byteOffset, value){
	      $setInt8.call(this, byteOffset, value << 24 >> 24);
	    },
	    setUint8: function setUint8(byteOffset, value){
	      $setInt8.call(this, byteOffset, value << 24 >> 24);
	    }
	  }, true);
	}
	setToStringTag($ArrayBuffer, ARRAY_BUFFER);
	setToStringTag($DataView, DATA_VIEW);
	hide($DataView[PROTOTYPE], $typed.VIEW, true);
	exports[ARRAY_BUFFER] = $ArrayBuffer;
	exports[DATA_VIEW] = $DataView;

/***/ },

/***/ 1125:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.data-view.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912);
	$export($export.G + $export.W + $export.F * !__webpack_require__(/*! ./_typed */ 1123).ABV, {
	  DataView: __webpack_require__(/*! ./_typed-buffer */ 1124).DataView
	});

/***/ },

/***/ 1126:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int8-array.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Int8', 1, function(init){
	  return function Int8Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1127:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed-array.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	if(__webpack_require__(/*! ./_descriptors */ 910)){
	  var LIBRARY             = __webpack_require__(/*! ./_library */ 932)
	    , global              = __webpack_require__(/*! ./_global */ 908)
	    , fails               = __webpack_require__(/*! ./_fails */ 911)
	    , $export             = __webpack_require__(/*! ./_export */ 912)
	    , $typed              = __webpack_require__(/*! ./_typed */ 1123)
	    , $buffer             = __webpack_require__(/*! ./_typed-buffer */ 1124)
	    , ctx                 = __webpack_require__(/*! ./_ctx */ 924)
	    , anInstance          = __webpack_require__(/*! ./_an-instance */ 1109)
	    , propertyDesc        = __webpack_require__(/*! ./_property-desc */ 921)
	    , hide                = __webpack_require__(/*! ./_hide */ 914)
	    , redefineAll         = __webpack_require__(/*! ./_redefine-all */ 1114)
	    , toInteger           = __webpack_require__(/*! ./_to-integer */ 942)
	    , toLength            = __webpack_require__(/*! ./_to-length */ 941)
	    , toIndex             = __webpack_require__(/*! ./_to-index */ 943)
	    , toPrimitive         = __webpack_require__(/*! ./_to-primitive */ 920)
	    , has                 = __webpack_require__(/*! ./_has */ 909)
	    , same                = __webpack_require__(/*! ./_same-value */ 975)
	    , classof             = __webpack_require__(/*! ./_classof */ 979)
	    , isObject            = __webpack_require__(/*! ./_is-object */ 917)
	    , toObject            = __webpack_require__(/*! ./_to-object */ 962)
	    , isArrayIter         = __webpack_require__(/*! ./_is-array-iter */ 1066)
	    , create              = __webpack_require__(/*! ./_object-create */ 950)
	    , getPrototypeOf      = __webpack_require__(/*! ./_object-gpo */ 963)
	    , gOPN                = __webpack_require__(/*! ./_object-gopn */ 954).f
	    , getIterFn           = __webpack_require__(/*! ./core.get-iterator-method */ 1068)
	    , uid                 = __webpack_require__(/*! ./_uid */ 923)
	    , wks                 = __webpack_require__(/*! ./_wks */ 929)
	    , createArrayMethod   = __webpack_require__(/*! ./_array-methods */ 1076)
	    , createArrayIncludes = __webpack_require__(/*! ./_array-includes */ 940)
	    , speciesConstructor  = __webpack_require__(/*! ./_species-constructor */ 1111)
	    , ArrayIterators      = __webpack_require__(/*! ./es6.array.iterator */ 1097)
	    , Iterators           = __webpack_require__(/*! ./_iterators */ 1033)
	    , $iterDetect         = __webpack_require__(/*! ./_iter-detect */ 1069)
	    , setSpecies          = __webpack_require__(/*! ./_set-species */ 1096)
	    , arrayFill           = __webpack_require__(/*! ./_array-fill */ 1092)
	    , arrayCopyWithin     = __webpack_require__(/*! ./_array-copy-within */ 1089)
	    , $DP                 = __webpack_require__(/*! ./_object-dp */ 915)
	    , $GOPD               = __webpack_require__(/*! ./_object-gopd */ 955)
	    , dP                  = $DP.f
	    , gOPD                = $GOPD.f
	    , RangeError          = global.RangeError
	    , TypeError           = global.TypeError
	    , Uint8Array          = global.Uint8Array
	    , ARRAY_BUFFER        = 'ArrayBuffer'
	    , SHARED_BUFFER       = 'Shared' + ARRAY_BUFFER
	    , BYTES_PER_ELEMENT   = 'BYTES_PER_ELEMENT'
	    , PROTOTYPE           = 'prototype'
	    , ArrayProto          = Array[PROTOTYPE]
	    , $ArrayBuffer        = $buffer.ArrayBuffer
	    , $DataView           = $buffer.DataView
	    , arrayForEach        = createArrayMethod(0)
	    , arrayFilter         = createArrayMethod(2)
	    , arraySome           = createArrayMethod(3)
	    , arrayEvery          = createArrayMethod(4)
	    , arrayFind           = createArrayMethod(5)
	    , arrayFindIndex      = createArrayMethod(6)
	    , arrayIncludes       = createArrayIncludes(true)
	    , arrayIndexOf        = createArrayIncludes(false)
	    , arrayValues         = ArrayIterators.values
	    , arrayKeys           = ArrayIterators.keys
	    , arrayEntries        = ArrayIterators.entries
	    , arrayLastIndexOf    = ArrayProto.lastIndexOf
	    , arrayReduce         = ArrayProto.reduce
	    , arrayReduceRight    = ArrayProto.reduceRight
	    , arrayJoin           = ArrayProto.join
	    , arraySort           = ArrayProto.sort
	    , arraySlice          = ArrayProto.slice
	    , arrayToString       = ArrayProto.toString
	    , arrayToLocaleString = ArrayProto.toLocaleString
	    , ITERATOR            = wks('iterator')
	    , TAG                 = wks('toStringTag')
	    , TYPED_CONSTRUCTOR   = uid('typed_constructor')
	    , DEF_CONSTRUCTOR     = uid('def_constructor')
	    , ALL_CONSTRUCTORS    = $typed.CONSTR
	    , TYPED_ARRAY         = $typed.TYPED
	    , VIEW                = $typed.VIEW
	    , WRONG_LENGTH        = 'Wrong length!';
	
	  var $map = createArrayMethod(1, function(O, length){
	    return allocate(speciesConstructor(O, O[DEF_CONSTRUCTOR]), length);
	  });
	
	  var LITTLE_ENDIAN = fails(function(){
	    return new Uint8Array(new Uint16Array([1]).buffer)[0] === 1;
	  });
	
	  var FORCED_SET = !!Uint8Array && !!Uint8Array[PROTOTYPE].set && fails(function(){
	    new Uint8Array(1).set({});
	  });
	
	  var strictToLength = function(it, SAME){
	    if(it === undefined)throw TypeError(WRONG_LENGTH);
	    var number = +it
	      , length = toLength(it);
	    if(SAME && !same(number, length))throw RangeError(WRONG_LENGTH);
	    return length;
	  };
	
	  var toOffset = function(it, BYTES){
	    var offset = toInteger(it);
	    if(offset < 0 || offset % BYTES)throw RangeError('Wrong offset!');
	    return offset;
	  };
	
	  var validate = function(it){
	    if(isObject(it) && TYPED_ARRAY in it)return it;
	    throw TypeError(it + ' is not a typed array!');
	  };
	
	  var allocate = function(C, length){
	    if(!(isObject(C) && TYPED_CONSTRUCTOR in C)){
	      throw TypeError('It is not a typed array constructor!');
	    } return new C(length);
	  };
	
	  var speciesFromList = function(O, list){
	    return fromList(speciesConstructor(O, O[DEF_CONSTRUCTOR]), list);
	  };
	
	  var fromList = function(C, list){
	    var index  = 0
	      , length = list.length
	      , result = allocate(C, length);
	    while(length > index)result[index] = list[index++];
	    return result;
	  };
	
	  var addGetter = function(it, key, internal){
	    dP(it, key, {get: function(){ return this._d[internal]; }});
	  };
	
	  var $from = function from(source /*, mapfn, thisArg */){
	    var O       = toObject(source)
	      , aLen    = arguments.length
	      , mapfn   = aLen > 1 ? arguments[1] : undefined
	      , mapping = mapfn !== undefined
	      , iterFn  = getIterFn(O)
	      , i, length, values, result, step, iterator;
	    if(iterFn != undefined && !isArrayIter(iterFn)){
	      for(iterator = iterFn.call(O), values = [], i = 0; !(step = iterator.next()).done; i++){
	        values.push(step.value);
	      } O = values;
	    }
	    if(mapping && aLen > 2)mapfn = ctx(mapfn, arguments[2], 2);
	    for(i = 0, length = toLength(O.length), result = allocate(this, length); length > i; i++){
	      result[i] = mapping ? mapfn(O[i], i) : O[i];
	    }
	    return result;
	  };
	
	  var $of = function of(/*...items*/){
	    var index  = 0
	      , length = arguments.length
	      , result = allocate(this, length);
	    while(length > index)result[index] = arguments[index++];
	    return result;
	  };
	
	  // iOS Safari 6.x fails here
	  var TO_LOCALE_BUG = !!Uint8Array && fails(function(){ arrayToLocaleString.call(new Uint8Array(1)); });
	
	  var $toLocaleString = function toLocaleString(){
	    return arrayToLocaleString.apply(TO_LOCALE_BUG ? arraySlice.call(validate(this)) : validate(this), arguments);
	  };
	
	  var proto = {
	    copyWithin: function copyWithin(target, start /*, end */){
	      return arrayCopyWithin.call(validate(this), target, start, arguments.length > 2 ? arguments[2] : undefined);
	    },
	    every: function every(callbackfn /*, thisArg */){
	      return arrayEvery(validate(this), callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    fill: function fill(value /*, start, end */){ // eslint-disable-line no-unused-vars
	      return arrayFill.apply(validate(this), arguments);
	    },
	    filter: function filter(callbackfn /*, thisArg */){
	      return speciesFromList(this, arrayFilter(validate(this), callbackfn,
	        arguments.length > 1 ? arguments[1] : undefined));
	    },
	    find: function find(predicate /*, thisArg */){
	      return arrayFind(validate(this), predicate, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    findIndex: function findIndex(predicate /*, thisArg */){
	      return arrayFindIndex(validate(this), predicate, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    forEach: function forEach(callbackfn /*, thisArg */){
	      arrayForEach(validate(this), callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    indexOf: function indexOf(searchElement /*, fromIndex */){
	      return arrayIndexOf(validate(this), searchElement, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    includes: function includes(searchElement /*, fromIndex */){
	      return arrayIncludes(validate(this), searchElement, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    join: function join(separator){ // eslint-disable-line no-unused-vars
	      return arrayJoin.apply(validate(this), arguments);
	    },
	    lastIndexOf: function lastIndexOf(searchElement /*, fromIndex */){ // eslint-disable-line no-unused-vars
	      return arrayLastIndexOf.apply(validate(this), arguments);
	    },
	    map: function map(mapfn /*, thisArg */){
	      return $map(validate(this), mapfn, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    reduce: function reduce(callbackfn /*, initialValue */){ // eslint-disable-line no-unused-vars
	      return arrayReduce.apply(validate(this), arguments);
	    },
	    reduceRight: function reduceRight(callbackfn /*, initialValue */){ // eslint-disable-line no-unused-vars
	      return arrayReduceRight.apply(validate(this), arguments);
	    },
	    reverse: function reverse(){
	      var that   = this
	        , length = validate(that).length
	        , middle = Math.floor(length / 2)
	        , index  = 0
	        , value;
	      while(index < middle){
	        value         = that[index];
	        that[index++] = that[--length];
	        that[length]  = value;
	      } return that;
	    },
	    some: function some(callbackfn /*, thisArg */){
	      return arraySome(validate(this), callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	    },
	    sort: function sort(comparefn){
	      return arraySort.call(validate(this), comparefn);
	    },
	    subarray: function subarray(begin, end){
	      var O      = validate(this)
	        , length = O.length
	        , $begin = toIndex(begin, length);
	      return new (speciesConstructor(O, O[DEF_CONSTRUCTOR]))(
	        O.buffer,
	        O.byteOffset + $begin * O.BYTES_PER_ELEMENT,
	        toLength((end === undefined ? length : toIndex(end, length)) - $begin)
	      );
	    }
	  };
	
	  var $slice = function slice(start, end){
	    return speciesFromList(this, arraySlice.call(validate(this), start, end));
	  };
	
	  var $set = function set(arrayLike /*, offset */){
	    validate(this);
	    var offset = toOffset(arguments[1], 1)
	      , length = this.length
	      , src    = toObject(arrayLike)
	      , len    = toLength(src.length)
	      , index  = 0;
	    if(len + offset > length)throw RangeError(WRONG_LENGTH);
	    while(index < len)this[offset + index] = src[index++];
	  };
	
	  var $iterators = {
	    entries: function entries(){
	      return arrayEntries.call(validate(this));
	    },
	    keys: function keys(){
	      return arrayKeys.call(validate(this));
	    },
	    values: function values(){
	      return arrayValues.call(validate(this));
	    }
	  };
	
	  var isTAIndex = function(target, key){
	    return isObject(target)
	      && target[TYPED_ARRAY]
	      && typeof key != 'symbol'
	      && key in target
	      && String(+key) == String(key);
	  };
	  var $getDesc = function getOwnPropertyDescriptor(target, key){
	    return isTAIndex(target, key = toPrimitive(key, true))
	      ? propertyDesc(2, target[key])
	      : gOPD(target, key);
	  };
	  var $setDesc = function defineProperty(target, key, desc){
	    if(isTAIndex(target, key = toPrimitive(key, true))
	      && isObject(desc)
	      && has(desc, 'value')
	      && !has(desc, 'get')
	      && !has(desc, 'set')
	      // TODO: add validation descriptor w/o calling accessors
	      && !desc.configurable
	      && (!has(desc, 'writable') || desc.writable)
	      && (!has(desc, 'enumerable') || desc.enumerable)
	    ){
	      target[key] = desc.value;
	      return target;
	    } else return dP(target, key, desc);
	  };
	
	  if(!ALL_CONSTRUCTORS){
	    $GOPD.f = $getDesc;
	    $DP.f   = $setDesc;
	  }
	
	  $export($export.S + $export.F * !ALL_CONSTRUCTORS, 'Object', {
	    getOwnPropertyDescriptor: $getDesc,
	    defineProperty:           $setDesc
	  });
	
	  if(fails(function(){ arrayToString.call({}); })){
	    arrayToString = arrayToLocaleString = function toString(){
	      return arrayJoin.call(this);
	    }
	  }
	
	  var $TypedArrayPrototype$ = redefineAll({}, proto);
	  redefineAll($TypedArrayPrototype$, $iterators);
	  hide($TypedArrayPrototype$, ITERATOR, $iterators.values);
	  redefineAll($TypedArrayPrototype$, {
	    slice:          $slice,
	    set:            $set,
	    constructor:    function(){ /* noop */ },
	    toString:       arrayToString,
	    toLocaleString: $toLocaleString
	  });
	  addGetter($TypedArrayPrototype$, 'buffer', 'b');
	  addGetter($TypedArrayPrototype$, 'byteOffset', 'o');
	  addGetter($TypedArrayPrototype$, 'byteLength', 'l');
	  addGetter($TypedArrayPrototype$, 'length', 'e');
	  dP($TypedArrayPrototype$, TAG, {
	    get: function(){ return this[TYPED_ARRAY]; }
	  });
	
	  module.exports = function(KEY, BYTES, wrapper, CLAMPED){
	    CLAMPED = !!CLAMPED;
	    var NAME       = KEY + (CLAMPED ? 'Clamped' : '') + 'Array'
	      , ISNT_UINT8 = NAME != 'Uint8Array'
	      , GETTER     = 'get' + KEY
	      , SETTER     = 'set' + KEY
	      , TypedArray = global[NAME]
	      , Base       = TypedArray || {}
	      , TAC        = TypedArray && getPrototypeOf(TypedArray)
	      , FORCED     = !TypedArray || !$typed.ABV
	      , O          = {}
	      , TypedArrayPrototype = TypedArray && TypedArray[PROTOTYPE];
	    var getter = function(that, index){
	      var data = that._d;
	      return data.v[GETTER](index * BYTES + data.o, LITTLE_ENDIAN);
	    };
	    var setter = function(that, index, value){
	      var data = that._d;
	      if(CLAMPED)value = (value = Math.round(value)) < 0 ? 0 : value > 0xff ? 0xff : value & 0xff;
	      data.v[SETTER](index * BYTES + data.o, value, LITTLE_ENDIAN);
	    };
	    var addElement = function(that, index){
	      dP(that, index, {
	        get: function(){
	          return getter(this, index);
	        },
	        set: function(value){
	          return setter(this, index, value);
	        },
	        enumerable: true
	      });
	    };
	    if(FORCED){
	      TypedArray = wrapper(function(that, data, $offset, $length){
	        anInstance(that, TypedArray, NAME, '_d');
	        var index  = 0
	          , offset = 0
	          , buffer, byteLength, length, klass;
	        if(!isObject(data)){
	          length     = strictToLength(data, true)
	          byteLength = length * BYTES;
	          buffer     = new $ArrayBuffer(byteLength);
	        } else if(data instanceof $ArrayBuffer || (klass = classof(data)) == ARRAY_BUFFER || klass == SHARED_BUFFER){
	          buffer = data;
	          offset = toOffset($offset, BYTES);
	          var $len = data.byteLength;
	          if($length === undefined){
	            if($len % BYTES)throw RangeError(WRONG_LENGTH);
	            byteLength = $len - offset;
	            if(byteLength < 0)throw RangeError(WRONG_LENGTH);
	          } else {
	            byteLength = toLength($length) * BYTES;
	            if(byteLength + offset > $len)throw RangeError(WRONG_LENGTH);
	          }
	          length = byteLength / BYTES;
	        } else if(TYPED_ARRAY in data){
	          return fromList(TypedArray, data);
	        } else {
	          return $from.call(TypedArray, data);
	        }
	        hide(that, '_d', {
	          b: buffer,
	          o: offset,
	          l: byteLength,
	          e: length,
	          v: new $DataView(buffer)
	        });
	        while(index < length)addElement(that, index++);
	      });
	      TypedArrayPrototype = TypedArray[PROTOTYPE] = create($TypedArrayPrototype$);
	      hide(TypedArrayPrototype, 'constructor', TypedArray);
	    } else if(!$iterDetect(function(iter){
	      // V8 works with iterators, but fails in many other cases
	      // https://code.google.com/p/v8/issues/detail?id=4552
	      new TypedArray(null); // eslint-disable-line no-new
	      new TypedArray(iter); // eslint-disable-line no-new
	    }, true)){
	      TypedArray = wrapper(function(that, data, $offset, $length){
	        anInstance(that, TypedArray, NAME);
	        var klass;
	        // `ws` module bug, temporarily remove validation length for Uint8Array
	        // https://github.com/websockets/ws/pull/645
	        if(!isObject(data))return new Base(strictToLength(data, ISNT_UINT8));
	        if(data instanceof $ArrayBuffer || (klass = classof(data)) == ARRAY_BUFFER || klass == SHARED_BUFFER){
	          return $length !== undefined
	            ? new Base(data, toOffset($offset, BYTES), $length)
	            : $offset !== undefined
	              ? new Base(data, toOffset($offset, BYTES))
	              : new Base(data);
	        }
	        if(TYPED_ARRAY in data)return fromList(TypedArray, data);
	        return $from.call(TypedArray, data);
	      });
	      arrayForEach(TAC !== Function.prototype ? gOPN(Base).concat(gOPN(TAC)) : gOPN(Base), function(key){
	        if(!(key in TypedArray))hide(TypedArray, key, Base[key]);
	      });
	      TypedArray[PROTOTYPE] = TypedArrayPrototype;
	      if(!LIBRARY)TypedArrayPrototype.constructor = TypedArray;
	    }
	    var $nativeIterator   = TypedArrayPrototype[ITERATOR]
	      , CORRECT_ITER_NAME = !!$nativeIterator && ($nativeIterator.name == 'values' || $nativeIterator.name == undefined)
	      , $iterator         = $iterators.values;
	    hide(TypedArray, TYPED_CONSTRUCTOR, true);
	    hide(TypedArrayPrototype, TYPED_ARRAY, NAME);
	    hide(TypedArrayPrototype, VIEW, true);
	    hide(TypedArrayPrototype, DEF_CONSTRUCTOR, TypedArray);
	
	    if(CLAMPED ? new TypedArray(1)[TAG] != NAME : !(TAG in TypedArrayPrototype)){
	      dP(TypedArrayPrototype, TAG, {
	        get: function(){ return NAME; }
	      });
	    }
	
	    O[NAME] = TypedArray;
	
	    $export($export.G + $export.W + $export.F * (TypedArray != Base), O);
	
	    $export($export.S, NAME, {
	      BYTES_PER_ELEMENT: BYTES,
	      from: $from,
	      of: $of
	    });
	
	    if(!(BYTES_PER_ELEMENT in TypedArrayPrototype))hide(TypedArrayPrototype, BYTES_PER_ELEMENT, BYTES);
	
	    $export($export.P, NAME, proto);
	
	    setSpecies(NAME);
	
	    $export($export.P + $export.F * FORCED_SET, NAME, {set: $set});
	
	    $export($export.P + $export.F * !CORRECT_ITER_NAME, NAME, $iterators);
	
	    $export($export.P + $export.F * (TypedArrayPrototype.toString != arrayToString), NAME, {toString: arrayToString});
	
	    $export($export.P + $export.F * fails(function(){
	      new TypedArray(1).slice();
	    }), NAME, {slice: $slice});
	
	    $export($export.P + $export.F * (fails(function(){
	      return [1, 2].toLocaleString() != new TypedArray([1, 2]).toLocaleString()
	    }) || !fails(function(){
	      TypedArrayPrototype.toLocaleString.call([1, 2]);
	    })), NAME, {toLocaleString: $toLocaleString});
	
	    Iterators[NAME] = CORRECT_ITER_NAME ? $nativeIterator : $iterator;
	    if(!LIBRARY && !CORRECT_ITER_NAME)hide(TypedArrayPrototype, ITERATOR, $iterator);
	  };
	} else module.exports = function(){ /* empty */ };

/***/ },

/***/ 1128:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint8-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Uint8', 1, function(init){
	  return function Uint8Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1129:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint8-clamped-array.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Uint8', 1, function(init){
	  return function Uint8ClampedArray(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	}, true);

/***/ },

/***/ 1130:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int16-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Int16', 2, function(init){
	  return function Int16Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1131:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint16-array.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Uint16', 2, function(init){
	  return function Uint16Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1132:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int32-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Int32', 4, function(init){
	  return function Int32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1133:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint32-array.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Uint32', 4, function(init){
	  return function Uint32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1134:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.float32-array.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Float32', 4, function(init){
	  return function Float32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1135:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.float64-array.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1127)('Float64', 8, function(init){
	  return function Float64Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1136:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.apply.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.1 Reflect.apply(target, thisArgument, argumentsList)
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 925)
	  , anObject  = __webpack_require__(/*! ./_an-object */ 916)
	  , rApply    = (__webpack_require__(/*! ./_global */ 908).Reflect || {}).apply
	  , fApply    = Function.apply;
	// MS Edge argumentsList argument is optional
	$export($export.S + $export.F * !__webpack_require__(/*! ./_fails */ 911)(function(){
	  rApply(function(){});
	}), 'Reflect', {
	  apply: function apply(target, thisArgument, argumentsList){
	    var T = aFunction(target)
	      , L = anObject(argumentsList);
	    return rApply ? rApply(T, thisArgument, L) : fApply.call(T, thisArgument, L);
	  }
	});

/***/ },

/***/ 1137:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.construct.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.2 Reflect.construct(target, argumentsList [, newTarget])
	var $export    = __webpack_require__(/*! ./_export */ 912)
	  , create     = __webpack_require__(/*! ./_object-create */ 950)
	  , aFunction  = __webpack_require__(/*! ./_a-function */ 925)
	  , anObject   = __webpack_require__(/*! ./_an-object */ 916)
	  , isObject   = __webpack_require__(/*! ./_is-object */ 917)
	  , fails      = __webpack_require__(/*! ./_fails */ 911)
	  , bind       = __webpack_require__(/*! ./_bind */ 981)
	  , rConstruct = (__webpack_require__(/*! ./_global */ 908).Reflect || {}).construct;
	
	// MS Edge supports only 2 arguments and argumentsList argument is optional
	// FF Nightly sets third argument as `new.target`, but does not create `this` from it
	var NEW_TARGET_BUG = fails(function(){
	  function F(){}
	  return !(rConstruct(function(){}, [], F) instanceof F);
	});
	var ARGS_BUG = !fails(function(){
	  rConstruct(function(){});
	});
	
	$export($export.S + $export.F * (NEW_TARGET_BUG || ARGS_BUG), 'Reflect', {
	  construct: function construct(Target, args /*, newTarget*/){
	    aFunction(Target);
	    anObject(args);
	    var newTarget = arguments.length < 3 ? Target : aFunction(arguments[2]);
	    if(ARGS_BUG && !NEW_TARGET_BUG)return rConstruct(Target, args, newTarget);
	    if(Target == newTarget){
	      // w/o altered newTarget, optimization for 0-4 arguments
	      switch(args.length){
	        case 0: return new Target;
	        case 1: return new Target(args[0]);
	        case 2: return new Target(args[0], args[1]);
	        case 3: return new Target(args[0], args[1], args[2]);
	        case 4: return new Target(args[0], args[1], args[2], args[3]);
	      }
	      // w/o altered newTarget, lot of arguments case
	      var $args = [null];
	      $args.push.apply($args, args);
	      return new (bind.apply(Target, $args));
	    }
	    // with altered newTarget, not support built-in constructors
	    var proto    = newTarget.prototype
	      , instance = create(isObject(proto) ? proto : Object.prototype)
	      , result   = Function.apply.call(Target, instance, args);
	    return isObject(result) ? result : instance;
	  }
	});

/***/ },

/***/ 1138:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.define-property.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.3 Reflect.defineProperty(target, propertyKey, attributes)
	var dP          = __webpack_require__(/*! ./_object-dp */ 915)
	  , $export     = __webpack_require__(/*! ./_export */ 912)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 916)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 920);
	
	// MS Edge has broken Reflect.defineProperty - throwing instead of returning false
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 911)(function(){
	  Reflect.defineProperty(dP.f({}, 1, {value: 1}), 1, {value: 2});
	}), 'Reflect', {
	  defineProperty: function defineProperty(target, propertyKey, attributes){
	    anObject(target);
	    propertyKey = toPrimitive(propertyKey, true);
	    anObject(attributes);
	    try {
	      dP.f(target, propertyKey, attributes);
	      return true;
	    } catch(e){
	      return false;
	    }
	  }
	});

/***/ },

/***/ 1139:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.delete-property.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.4 Reflect.deleteProperty(target, propertyKey)
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , gOPD     = __webpack_require__(/*! ./_object-gopd */ 955).f
	  , anObject = __webpack_require__(/*! ./_an-object */ 916);
	
	$export($export.S, 'Reflect', {
	  deleteProperty: function deleteProperty(target, propertyKey){
	    var desc = gOPD(anObject(target), propertyKey);
	    return desc && !desc.configurable ? false : delete target[propertyKey];
	  }
	});

/***/ },

/***/ 1140:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.enumerate.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 26.1.5 Reflect.enumerate(target)
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , anObject = __webpack_require__(/*! ./_an-object */ 916);
	var Enumerate = function(iterated){
	  this._t = anObject(iterated); // target
	  this._i = 0;                  // next index
	  var keys = this._k = []       // keys
	    , key;
	  for(key in iterated)keys.push(key);
	};
	__webpack_require__(/*! ./_iter-create */ 1034)(Enumerate, 'Object', function(){
	  var that = this
	    , keys = that._k
	    , key;
	  do {
	    if(that._i >= keys.length)return {value: undefined, done: true};
	  } while(!((key = keys[that._i++]) in that._t));
	  return {value: key, done: false};
	});
	
	$export($export.S, 'Reflect', {
	  enumerate: function enumerate(target){
	    return new Enumerate(target);
	  }
	});

/***/ },

/***/ 1141:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.6 Reflect.get(target, propertyKey [, receiver])
	var gOPD           = __webpack_require__(/*! ./_object-gopd */ 955)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 963)
	  , has            = __webpack_require__(/*! ./_has */ 909)
	  , $export        = __webpack_require__(/*! ./_export */ 912)
	  , isObject       = __webpack_require__(/*! ./_is-object */ 917)
	  , anObject       = __webpack_require__(/*! ./_an-object */ 916);
	
	function get(target, propertyKey/*, receiver*/){
	  var receiver = arguments.length < 3 ? target : arguments[2]
	    , desc, proto;
	  if(anObject(target) === receiver)return target[propertyKey];
	  if(desc = gOPD.f(target, propertyKey))return has(desc, 'value')
	    ? desc.value
	    : desc.get !== undefined
	      ? desc.get.call(receiver)
	      : undefined;
	  if(isObject(proto = getPrototypeOf(target)))return get(proto, propertyKey, receiver);
	}
	
	$export($export.S, 'Reflect', {get: get});

/***/ },

/***/ 1142:
/*!***************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get-own-property-descriptor.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.7 Reflect.getOwnPropertyDescriptor(target, propertyKey)
	var gOPD     = __webpack_require__(/*! ./_object-gopd */ 955)
	  , $export  = __webpack_require__(/*! ./_export */ 912)
	  , anObject = __webpack_require__(/*! ./_an-object */ 916);
	
	$export($export.S, 'Reflect', {
	  getOwnPropertyDescriptor: function getOwnPropertyDescriptor(target, propertyKey){
	    return gOPD.f(anObject(target), propertyKey);
	  }
	});

/***/ },

/***/ 1143:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get-prototype-of.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.8 Reflect.getPrototypeOf(target)
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , getProto = __webpack_require__(/*! ./_object-gpo */ 963)
	  , anObject = __webpack_require__(/*! ./_an-object */ 916);
	
	$export($export.S, 'Reflect', {
	  getPrototypeOf: function getPrototypeOf(target){
	    return getProto(anObject(target));
	  }
	});

/***/ },

/***/ 1144:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.has.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.9 Reflect.has(target, propertyKey)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Reflect', {
	  has: function has(target, propertyKey){
	    return propertyKey in target;
	  }
	});

/***/ },

/***/ 1145:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.is-extensible.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.10 Reflect.isExtensible(target)
	var $export       = __webpack_require__(/*! ./_export */ 912)
	  , anObject      = __webpack_require__(/*! ./_an-object */ 916)
	  , $isExtensible = Object.isExtensible;
	
	$export($export.S, 'Reflect', {
	  isExtensible: function isExtensible(target){
	    anObject(target);
	    return $isExtensible ? $isExtensible(target) : true;
	  }
	});

/***/ },

/***/ 1146:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.own-keys.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.11 Reflect.ownKeys(target)
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Reflect', {ownKeys: __webpack_require__(/*! ./_own-keys */ 1147)});

/***/ },

/***/ 1147:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_own-keys.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// all object keys, includes non-enumerable and symbols
	var gOPN     = __webpack_require__(/*! ./_object-gopn */ 954)
	  , gOPS     = __webpack_require__(/*! ./_object-gops */ 947)
	  , anObject = __webpack_require__(/*! ./_an-object */ 916)
	  , Reflect  = __webpack_require__(/*! ./_global */ 908).Reflect;
	module.exports = Reflect && Reflect.ownKeys || function ownKeys(it){
	  var keys       = gOPN.f(anObject(it))
	    , getSymbols = gOPS.f;
	  return getSymbols ? keys.concat(getSymbols(it)) : keys;
	};

/***/ },

/***/ 1148:
/*!******************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.prevent-extensions.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.12 Reflect.preventExtensions(target)
	var $export            = __webpack_require__(/*! ./_export */ 912)
	  , anObject           = __webpack_require__(/*! ./_an-object */ 916)
	  , $preventExtensions = Object.preventExtensions;
	
	$export($export.S, 'Reflect', {
	  preventExtensions: function preventExtensions(target){
	    anObject(target);
	    try {
	      if($preventExtensions)$preventExtensions(target);
	      return true;
	    } catch(e){
	      return false;
	    }
	  }
	});

/***/ },

/***/ 1149:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.set.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.13 Reflect.set(target, propertyKey, V [, receiver])
	var dP             = __webpack_require__(/*! ./_object-dp */ 915)
	  , gOPD           = __webpack_require__(/*! ./_object-gopd */ 955)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 963)
	  , has            = __webpack_require__(/*! ./_has */ 909)
	  , $export        = __webpack_require__(/*! ./_export */ 912)
	  , createDesc     = __webpack_require__(/*! ./_property-desc */ 921)
	  , anObject       = __webpack_require__(/*! ./_an-object */ 916)
	  , isObject       = __webpack_require__(/*! ./_is-object */ 917);
	
	function set(target, propertyKey, V/*, receiver*/){
	  var receiver = arguments.length < 4 ? target : arguments[3]
	    , ownDesc  = gOPD.f(anObject(target), propertyKey)
	    , existingDescriptor, proto;
	  if(!ownDesc){
	    if(isObject(proto = getPrototypeOf(target))){
	      return set(proto, propertyKey, V, receiver);
	    }
	    ownDesc = createDesc(0);
	  }
	  if(has(ownDesc, 'value')){
	    if(ownDesc.writable === false || !isObject(receiver))return false;
	    existingDescriptor = gOPD.f(receiver, propertyKey) || createDesc(0);
	    existingDescriptor.value = V;
	    dP.f(receiver, propertyKey, existingDescriptor);
	    return true;
	  }
	  return ownDesc.set === undefined ? false : (ownDesc.set.call(receiver, V), true);
	}
	
	$export($export.S, 'Reflect', {set: set});

/***/ },

/***/ 1150:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.set-prototype-of.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.14 Reflect.setPrototypeOf(target, proto)
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , setProto = __webpack_require__(/*! ./_set-proto */ 977);
	
	if(setProto)$export($export.S, 'Reflect', {
	  setPrototypeOf: function setPrototypeOf(target, proto){
	    setProto.check(target, proto);
	    try {
	      setProto.set(target, proto);
	      return true;
	    } catch(e){
	      return false;
	    }
	  }
	});

/***/ },

/***/ 1151:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.array.includes.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/Array.prototype.includes
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , $includes = __webpack_require__(/*! ./_array-includes */ 940)(true);
	
	$export($export.P, 'Array', {
	  includes: function includes(el /*, fromIndex = 0 */){
	    return $includes(this, el, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1090)('includes');

/***/ },

/***/ 1152:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.at.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/mathiasbynens/String.prototype.at
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $at     = __webpack_require__(/*! ./_string-at */ 1031)(true);
	
	$export($export.P, 'String', {
	  at: function at(pos){
	    return $at(this, pos);
	  }
	});

/***/ },

/***/ 1153:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.pad-start.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/proposal-string-pad-start-end
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $pad    = __webpack_require__(/*! ./_string-pad */ 1154);
	
	$export($export.P, 'String', {
	  padStart: function padStart(maxLength /*, fillString = ' ' */){
	    return $pad(this, maxLength, arguments.length > 1 ? arguments[1] : undefined, true);
	  }
	});

/***/ },

/***/ 1154:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-pad.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-string-pad-start-end
	var toLength = __webpack_require__(/*! ./_to-length */ 941)
	  , repeat   = __webpack_require__(/*! ./_string-repeat */ 995)
	  , defined  = __webpack_require__(/*! ./_defined */ 939);
	
	module.exports = function(that, maxLength, fillString, left){
	  var S            = String(defined(that))
	    , stringLength = S.length
	    , fillStr      = fillString === undefined ? ' ' : String(fillString)
	    , intMaxLength = toLength(maxLength);
	  if(intMaxLength <= stringLength || fillStr == '')return S;
	  var fillLen = intMaxLength - stringLength
	    , stringFiller = repeat.call(fillStr, Math.ceil(fillLen / fillStr.length));
	  if(stringFiller.length > fillLen)stringFiller = stringFiller.slice(0, fillLen);
	  return left ? stringFiller + S : S + stringFiller;
	};


/***/ },

/***/ 1155:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.pad-end.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/proposal-string-pad-start-end
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $pad    = __webpack_require__(/*! ./_string-pad */ 1154);
	
	$export($export.P, 'String', {
	  padEnd: function padEnd(maxLength /*, fillString = ' ' */){
	    return $pad(this, maxLength, arguments.length > 1 ? arguments[1] : undefined, false);
	  }
	});

/***/ },

/***/ 1156:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.trim-left.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/sebmarkbage/ecmascript-string-left-right-trim
	__webpack_require__(/*! ./_string-trim */ 987)('trimLeft', function($trim){
	  return function trimLeft(){
	    return $trim(this, 1);
	  };
	}, 'trimStart');

/***/ },

/***/ 1157:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.trim-right.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/sebmarkbage/ecmascript-string-left-right-trim
	__webpack_require__(/*! ./_string-trim */ 987)('trimRight', function($trim){
	  return function trimRight(){
	    return $trim(this, 2);
	  };
	}, 'trimEnd');

/***/ },

/***/ 1158:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.match-all.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://tc39.github.io/String.prototype.matchAll/
	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , defined     = __webpack_require__(/*! ./_defined */ 939)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 941)
	  , isRegExp    = __webpack_require__(/*! ./_is-regexp */ 1038)
	  , getFlags    = __webpack_require__(/*! ./_flags */ 1100)
	  , RegExpProto = RegExp.prototype;
	
	var $RegExpStringIterator = function(regexp, string){
	  this._r = regexp;
	  this._s = string;
	};
	
	__webpack_require__(/*! ./_iter-create */ 1034)($RegExpStringIterator, 'RegExp String', function next(){
	  var match = this._r.exec(this._s);
	  return {value: match, done: match === null};
	});
	
	$export($export.P, 'String', {
	  matchAll: function matchAll(regexp){
	    defined(this);
	    if(!isRegExp(regexp))throw TypeError(regexp + ' is not a regexp!');
	    var S     = String(this)
	      , flags = 'flags' in RegExpProto ? String(regexp.flags) : getFlags.call(regexp)
	      , rx    = new RegExp(regexp.source, ~flags.indexOf('g') ? flags : 'g' + flags);
	    rx.lastIndex = toLength(regexp.lastIndex);
	    return new $RegExpStringIterator(rx, S);
	  }
	});

/***/ },

/***/ 1159:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.symbol.async-iterator.js ***!
  \*************************************************************************/
[4806, 931],

/***/ 1160:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.symbol.observable.js ***!
  \*********************************************************************/
[4807, 931],

/***/ 1161:
/*!***************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.get-own-property-descriptors.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-object-getownpropertydescriptors
	var $export        = __webpack_require__(/*! ./_export */ 912)
	  , ownKeys        = __webpack_require__(/*! ./_own-keys */ 1147)
	  , toIObject      = __webpack_require__(/*! ./_to-iobject */ 936)
	  , gOPD           = __webpack_require__(/*! ./_object-gopd */ 955)
	  , createProperty = __webpack_require__(/*! ./_create-property */ 1067);
	
	$export($export.S, 'Object', {
	  getOwnPropertyDescriptors: function getOwnPropertyDescriptors(object){
	    var O       = toIObject(object)
	      , getDesc = gOPD.f
	      , keys    = ownKeys(O)
	      , result  = {}
	      , i       = 0
	      , key;
	    while(keys.length > i)createProperty(result, key = keys[i++], getDesc(O, key));
	    return result;
	  }
	});

/***/ },

/***/ 1162:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.values.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-object-values-entries
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $values = __webpack_require__(/*! ./_object-to-array */ 1163)(false);
	
	$export($export.S, 'Object', {
	  values: function values(it){
	    return $values(it);
	  }
	});

/***/ },

/***/ 1163:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-to-array.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var getKeys   = __webpack_require__(/*! ./_object-keys */ 934)
	  , toIObject = __webpack_require__(/*! ./_to-iobject */ 936)
	  , isEnum    = __webpack_require__(/*! ./_object-pie */ 948).f;
	module.exports = function(isEntries){
	  return function(it){
	    var O      = toIObject(it)
	      , keys   = getKeys(O)
	      , length = keys.length
	      , i      = 0
	      , result = []
	      , key;
	    while(length > i)if(isEnum.call(O, key = keys[i++])){
	      result.push(isEntries ? [key, O[key]] : O[key]);
	    } return result;
	  };
	};

/***/ },

/***/ 1164:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.entries.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-object-values-entries
	var $export  = __webpack_require__(/*! ./_export */ 912)
	  , $entries = __webpack_require__(/*! ./_object-to-array */ 1163)(true);
	
	$export($export.S, 'Object', {
	  entries: function entries(it){
	    return $entries(it);
	  }
	});

/***/ },

/***/ 1165:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.define-getter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export         = __webpack_require__(/*! ./_export */ 912)
	  , toObject        = __webpack_require__(/*! ./_to-object */ 962)
	  , aFunction       = __webpack_require__(/*! ./_a-function */ 925)
	  , $defineProperty = __webpack_require__(/*! ./_object-dp */ 915);
	
	// B.2.2.2 Object.prototype.__defineGetter__(P, getter)
	__webpack_require__(/*! ./_descriptors */ 910) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1166), 'Object', {
	  __defineGetter__: function __defineGetter__(P, getter){
	    $defineProperty.f(toObject(this), P, {get: aFunction(getter), enumerable: true, configurable: true});
	  }
	});

/***/ },

/***/ 1166:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-forced-pam.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// Forced replacement prototype accessors methods
	module.exports = __webpack_require__(/*! ./_library */ 932)|| !__webpack_require__(/*! ./_fails */ 911)(function(){
	  var K = Math.random();
	  // In FF throws only define methods
	  __defineSetter__.call(null, K, function(){ /* empty */});
	  delete __webpack_require__(/*! ./_global */ 908)[K];
	});

/***/ },

/***/ 1167:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.define-setter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export         = __webpack_require__(/*! ./_export */ 912)
	  , toObject        = __webpack_require__(/*! ./_to-object */ 962)
	  , aFunction       = __webpack_require__(/*! ./_a-function */ 925)
	  , $defineProperty = __webpack_require__(/*! ./_object-dp */ 915);
	
	// B.2.2.3 Object.prototype.__defineSetter__(P, setter)
	__webpack_require__(/*! ./_descriptors */ 910) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1166), 'Object', {
	  __defineSetter__: function __defineSetter__(P, setter){
	    $defineProperty.f(toObject(this), P, {set: aFunction(setter), enumerable: true, configurable: true});
	  }
	});

/***/ },

/***/ 1168:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.lookup-getter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export                  = __webpack_require__(/*! ./_export */ 912)
	  , toObject                 = __webpack_require__(/*! ./_to-object */ 962)
	  , toPrimitive              = __webpack_require__(/*! ./_to-primitive */ 920)
	  , getPrototypeOf           = __webpack_require__(/*! ./_object-gpo */ 963)
	  , getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 955).f;
	
	// B.2.2.4 Object.prototype.__lookupGetter__(P)
	__webpack_require__(/*! ./_descriptors */ 910) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1166), 'Object', {
	  __lookupGetter__: function __lookupGetter__(P){
	    var O = toObject(this)
	      , K = toPrimitive(P, true)
	      , D;
	    do {
	      if(D = getOwnPropertyDescriptor(O, K))return D.get;
	    } while(O = getPrototypeOf(O));
	  }
	});

/***/ },

/***/ 1169:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.lookup-setter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export                  = __webpack_require__(/*! ./_export */ 912)
	  , toObject                 = __webpack_require__(/*! ./_to-object */ 962)
	  , toPrimitive              = __webpack_require__(/*! ./_to-primitive */ 920)
	  , getPrototypeOf           = __webpack_require__(/*! ./_object-gpo */ 963)
	  , getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 955).f;
	
	// B.2.2.5 Object.prototype.__lookupSetter__(P)
	__webpack_require__(/*! ./_descriptors */ 910) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1166), 'Object', {
	  __lookupSetter__: function __lookupSetter__(P){
	    var O = toObject(this)
	      , K = toPrimitive(P, true)
	      , D;
	    do {
	      if(D = getOwnPropertyDescriptor(O, K))return D.set;
	    } while(O = getPrototypeOf(O));
	  }
	});

/***/ },

/***/ 1170:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.map.to-json.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var $export  = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P + $export.R, 'Map', {toJSON: __webpack_require__(/*! ./_collection-to-json */ 1171)('Map')});

/***/ },

/***/ 1171:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-to-json.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var classof = __webpack_require__(/*! ./_classof */ 979)
	  , from    = __webpack_require__(/*! ./_array-from-iterable */ 1172);
	module.exports = function(NAME){
	  return function toJSON(){
	    if(classof(this) != NAME)throw TypeError(NAME + "#toJSON isn't generic");
	    return from(this);
	  };
	};

/***/ },

/***/ 1172:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-from-iterable.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var forOf = __webpack_require__(/*! ./_for-of */ 1110);
	
	module.exports = function(iter, ITERATOR){
	  var result = [];
	  forOf(iter, false, result.push, result, ITERATOR);
	  return result;
	};


/***/ },

/***/ 1173:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.set.to-json.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var $export  = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.P + $export.R, 'Set', {toJSON: __webpack_require__(/*! ./_collection-to-json */ 1171)('Set')});

/***/ },

/***/ 1174:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.system.global.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/ljharb/proposal-global
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'System', {global: __webpack_require__(/*! ./_global */ 908)});

/***/ },

/***/ 1175:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.error.is-error.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/ljharb/proposal-is-error
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , cof     = __webpack_require__(/*! ./_cof */ 938);
	
	$export($export.S, 'Error', {
	  isError: function isError(it){
	    return cof(it) === 'Error';
	  }
	});

/***/ },

/***/ 1176:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.iaddh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  iaddh: function iaddh(x0, x1, y0, y1){
	    var $x0 = x0 >>> 0
	      , $x1 = x1 >>> 0
	      , $y0 = y0 >>> 0;
	    return $x1 + (y1 >>> 0) + (($x0 & $y0 | ($x0 | $y0) & ~($x0 + $y0 >>> 0)) >>> 31) | 0;
	  }
	});

/***/ },

/***/ 1177:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.isubh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  isubh: function isubh(x0, x1, y0, y1){
	    var $x0 = x0 >>> 0
	      , $x1 = x1 >>> 0
	      , $y0 = y0 >>> 0;
	    return $x1 - (y1 >>> 0) - ((~$x0 & $y0 | ~($x0 ^ $y0) & $x0 - $y0 >>> 0) >>> 31) | 0;
	  }
	});

/***/ },

/***/ 1178:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.imulh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  imulh: function imulh(u, v){
	    var UINT16 = 0xffff
	      , $u = +u
	      , $v = +v
	      , u0 = $u & UINT16
	      , v0 = $v & UINT16
	      , u1 = $u >> 16
	      , v1 = $v >> 16
	      , t  = (u1 * v0 >>> 0) + (u0 * v0 >>> 16);
	    return u1 * v1 + (t >> 16) + ((u0 * v1 >>> 0) + (t & UINT16) >> 16);
	  }
	});

/***/ },

/***/ 1179:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.umulh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 912);
	
	$export($export.S, 'Math', {
	  umulh: function umulh(u, v){
	    var UINT16 = 0xffff
	      , $u = +u
	      , $v = +v
	      , u0 = $u & UINT16
	      , v0 = $v & UINT16
	      , u1 = $u >>> 16
	      , v1 = $v >>> 16
	      , t  = (u1 * v0 >>> 0) + (u0 * v0 >>> 16);
	    return u1 * v1 + (t >>> 16) + ((u0 * v1 >>> 0) + (t & UINT16) >>> 16);
	  }
	});

/***/ },

/***/ 1180:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.define-metadata.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                  = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject                  = __webpack_require__(/*! ./_an-object */ 916)
	  , toMetaKey                 = metadata.key
	  , ordinaryDefineOwnMetadata = metadata.set;
	
	metadata.exp({defineMetadata: function defineMetadata(metadataKey, metadataValue, target, targetKey){
	  ordinaryDefineOwnMetadata(metadataKey, metadataValue, anObject(target), toMetaKey(targetKey));
	}});

/***/ },

/***/ 1181:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_metadata.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var Map     = __webpack_require__(/*! ./es6.map */ 1115)
	  , $export = __webpack_require__(/*! ./_export */ 912)
	  , shared  = __webpack_require__(/*! ./_shared */ 927)('metadata')
	  , store   = shared.store || (shared.store = new (__webpack_require__(/*! ./es6.weak-map */ 1119)));
	
	var getOrCreateMetadataMap = function(target, targetKey, create){
	  var targetMetadata = store.get(target);
	  if(!targetMetadata){
	    if(!create)return undefined;
	    store.set(target, targetMetadata = new Map);
	  }
	  var keyMetadata = targetMetadata.get(targetKey);
	  if(!keyMetadata){
	    if(!create)return undefined;
	    targetMetadata.set(targetKey, keyMetadata = new Map);
	  } return keyMetadata;
	};
	var ordinaryHasOwnMetadata = function(MetadataKey, O, P){
	  var metadataMap = getOrCreateMetadataMap(O, P, false);
	  return metadataMap === undefined ? false : metadataMap.has(MetadataKey);
	};
	var ordinaryGetOwnMetadata = function(MetadataKey, O, P){
	  var metadataMap = getOrCreateMetadataMap(O, P, false);
	  return metadataMap === undefined ? undefined : metadataMap.get(MetadataKey);
	};
	var ordinaryDefineOwnMetadata = function(MetadataKey, MetadataValue, O, P){
	  getOrCreateMetadataMap(O, P, true).set(MetadataKey, MetadataValue);
	};
	var ordinaryOwnMetadataKeys = function(target, targetKey){
	  var metadataMap = getOrCreateMetadataMap(target, targetKey, false)
	    , keys        = [];
	  if(metadataMap)metadataMap.forEach(function(_, key){ keys.push(key); });
	  return keys;
	};
	var toMetaKey = function(it){
	  return it === undefined || typeof it == 'symbol' ? it : String(it);
	};
	var exp = function(O){
	  $export($export.S, 'Reflect', O);
	};
	
	module.exports = {
	  store: store,
	  map: getOrCreateMetadataMap,
	  has: ordinaryHasOwnMetadata,
	  get: ordinaryGetOwnMetadata,
	  set: ordinaryDefineOwnMetadata,
	  keys: ordinaryOwnMetadataKeys,
	  key: toMetaKey,
	  exp: exp
	};

/***/ },

/***/ 1182:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.delete-metadata.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 916)
	  , toMetaKey              = metadata.key
	  , getOrCreateMetadataMap = metadata.map
	  , store                  = metadata.store;
	
	metadata.exp({deleteMetadata: function deleteMetadata(metadataKey, target /*, targetKey */){
	  var targetKey   = arguments.length < 3 ? undefined : toMetaKey(arguments[2])
	    , metadataMap = getOrCreateMetadataMap(anObject(target), targetKey, false);
	  if(metadataMap === undefined || !metadataMap['delete'](metadataKey))return false;
	  if(metadataMap.size)return true;
	  var targetMetadata = store.get(target);
	  targetMetadata['delete'](targetKey);
	  return !!targetMetadata.size || store['delete'](target);
	}});

/***/ },

/***/ 1183:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-metadata.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 916)
	  , getPrototypeOf         = __webpack_require__(/*! ./_object-gpo */ 963)
	  , ordinaryHasOwnMetadata = metadata.has
	  , ordinaryGetOwnMetadata = metadata.get
	  , toMetaKey              = metadata.key;
	
	var ordinaryGetMetadata = function(MetadataKey, O, P){
	  var hasOwn = ordinaryHasOwnMetadata(MetadataKey, O, P);
	  if(hasOwn)return ordinaryGetOwnMetadata(MetadataKey, O, P);
	  var parent = getPrototypeOf(O);
	  return parent !== null ? ordinaryGetMetadata(MetadataKey, parent, P) : undefined;
	};
	
	metadata.exp({getMetadata: function getMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryGetMetadata(metadataKey, anObject(target), arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1184:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-metadata-keys.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var Set                     = __webpack_require__(/*! ./es6.set */ 1118)
	  , from                    = __webpack_require__(/*! ./_array-from-iterable */ 1172)
	  , metadata                = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject                = __webpack_require__(/*! ./_an-object */ 916)
	  , getPrototypeOf          = __webpack_require__(/*! ./_object-gpo */ 963)
	  , ordinaryOwnMetadataKeys = metadata.keys
	  , toMetaKey               = metadata.key;
	
	var ordinaryMetadataKeys = function(O, P){
	  var oKeys  = ordinaryOwnMetadataKeys(O, P)
	    , parent = getPrototypeOf(O);
	  if(parent === null)return oKeys;
	  var pKeys  = ordinaryMetadataKeys(parent, P);
	  return pKeys.length ? oKeys.length ? from(new Set(oKeys.concat(pKeys))) : pKeys : oKeys;
	};
	
	metadata.exp({getMetadataKeys: function getMetadataKeys(target /*, targetKey */){
	  return ordinaryMetadataKeys(anObject(target), arguments.length < 2 ? undefined : toMetaKey(arguments[1]));
	}});

/***/ },

/***/ 1185:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-own-metadata.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 916)
	  , ordinaryGetOwnMetadata = metadata.get
	  , toMetaKey              = metadata.key;
	
	metadata.exp({getOwnMetadata: function getOwnMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryGetOwnMetadata(metadataKey, anObject(target)
	    , arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1186:
/*!*********************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-own-metadata-keys.js ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject                = __webpack_require__(/*! ./_an-object */ 916)
	  , ordinaryOwnMetadataKeys = metadata.keys
	  , toMetaKey               = metadata.key;
	
	metadata.exp({getOwnMetadataKeys: function getOwnMetadataKeys(target /*, targetKey */){
	  return ordinaryOwnMetadataKeys(anObject(target), arguments.length < 2 ? undefined : toMetaKey(arguments[1]));
	}});

/***/ },

/***/ 1187:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.has-metadata.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 916)
	  , getPrototypeOf         = __webpack_require__(/*! ./_object-gpo */ 963)
	  , ordinaryHasOwnMetadata = metadata.has
	  , toMetaKey              = metadata.key;
	
	var ordinaryHasMetadata = function(MetadataKey, O, P){
	  var hasOwn = ordinaryHasOwnMetadata(MetadataKey, O, P);
	  if(hasOwn)return true;
	  var parent = getPrototypeOf(O);
	  return parent !== null ? ordinaryHasMetadata(MetadataKey, parent, P) : false;
	};
	
	metadata.exp({hasMetadata: function hasMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryHasMetadata(metadataKey, anObject(target), arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1188:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.has-own-metadata.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 916)
	  , ordinaryHasOwnMetadata = metadata.has
	  , toMetaKey              = metadata.key;
	
	metadata.exp({hasOwnMetadata: function hasOwnMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryHasOwnMetadata(metadataKey, anObject(target)
	    , arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1189:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.metadata.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                  = __webpack_require__(/*! ./_metadata */ 1181)
	  , anObject                  = __webpack_require__(/*! ./_an-object */ 916)
	  , aFunction                 = __webpack_require__(/*! ./_a-function */ 925)
	  , toMetaKey                 = metadata.key
	  , ordinaryDefineOwnMetadata = metadata.set;
	
	metadata.exp({metadata: function metadata(metadataKey, metadataValue){
	  return function decorator(target, targetKey){
	    ordinaryDefineOwnMetadata(
	      metadataKey, metadataValue,
	      (targetKey !== undefined ? anObject : aFunction)(target),
	      toMetaKey(targetKey)
	    );
	  };
	}});

/***/ },

/***/ 1190:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.asap.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/rwaldron/tc39-notes/blob/master/es6/2014-09/sept-25.md#510-globalasap-for-enqueuing-a-microtask
	var $export   = __webpack_require__(/*! ./_export */ 912)
	  , microtask = __webpack_require__(/*! ./_microtask */ 1113)()
	  , process   = __webpack_require__(/*! ./_global */ 908).process
	  , isNode    = __webpack_require__(/*! ./_cof */ 938)(process) == 'process';
	
	$export($export.G, {
	  asap: function asap(fn){
	    var domain = isNode && process.domain;
	    microtask(domain ? domain.bind(fn) : fn);
	  }
	});

/***/ },

/***/ 1191:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.observable.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/zenparsing/es-observable
	var $export     = __webpack_require__(/*! ./_export */ 912)
	  , global      = __webpack_require__(/*! ./_global */ 908)
	  , core        = __webpack_require__(/*! ./_core */ 913)
	  , microtask   = __webpack_require__(/*! ./_microtask */ 1113)()
	  , OBSERVABLE  = __webpack_require__(/*! ./_wks */ 929)('observable')
	  , aFunction   = __webpack_require__(/*! ./_a-function */ 925)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 916)
	  , anInstance  = __webpack_require__(/*! ./_an-instance */ 1109)
	  , redefineAll = __webpack_require__(/*! ./_redefine-all */ 1114)
	  , hide        = __webpack_require__(/*! ./_hide */ 914)
	  , forOf       = __webpack_require__(/*! ./_for-of */ 1110)
	  , RETURN      = forOf.RETURN;
	
	var getMethod = function(fn){
	  return fn == null ? undefined : aFunction(fn);
	};
	
	var cleanupSubscription = function(subscription){
	  var cleanup = subscription._c;
	  if(cleanup){
	    subscription._c = undefined;
	    cleanup();
	  }
	};
	
	var subscriptionClosed = function(subscription){
	  return subscription._o === undefined;
	};
	
	var closeSubscription = function(subscription){
	  if(!subscriptionClosed(subscription)){
	    subscription._o = undefined;
	    cleanupSubscription(subscription);
	  }
	};
	
	var Subscription = function(observer, subscriber){
	  anObject(observer);
	  this._c = undefined;
	  this._o = observer;
	  observer = new SubscriptionObserver(this);
	  try {
	    var cleanup      = subscriber(observer)
	      , subscription = cleanup;
	    if(cleanup != null){
	      if(typeof cleanup.unsubscribe === 'function')cleanup = function(){ subscription.unsubscribe(); };
	      else aFunction(cleanup);
	      this._c = cleanup;
	    }
	  } catch(e){
	    observer.error(e);
	    return;
	  } if(subscriptionClosed(this))cleanupSubscription(this);
	};
	
	Subscription.prototype = redefineAll({}, {
	  unsubscribe: function unsubscribe(){ closeSubscription(this); }
	});
	
	var SubscriptionObserver = function(subscription){
	  this._s = subscription;
	};
	
	SubscriptionObserver.prototype = redefineAll({}, {
	  next: function next(value){
	    var subscription = this._s;
	    if(!subscriptionClosed(subscription)){
	      var observer = subscription._o;
	      try {
	        var m = getMethod(observer.next);
	        if(m)return m.call(observer, value);
	      } catch(e){
	        try {
	          closeSubscription(subscription);
	        } finally {
	          throw e;
	        }
	      }
	    }
	  },
	  error: function error(value){
	    var subscription = this._s;
	    if(subscriptionClosed(subscription))throw value;
	    var observer = subscription._o;
	    subscription._o = undefined;
	    try {
	      var m = getMethod(observer.error);
	      if(!m)throw value;
	      value = m.call(observer, value);
	    } catch(e){
	      try {
	        cleanupSubscription(subscription);
	      } finally {
	        throw e;
	      }
	    } cleanupSubscription(subscription);
	    return value;
	  },
	  complete: function complete(value){
	    var subscription = this._s;
	    if(!subscriptionClosed(subscription)){
	      var observer = subscription._o;
	      subscription._o = undefined;
	      try {
	        var m = getMethod(observer.complete);
	        value = m ? m.call(observer, value) : undefined;
	      } catch(e){
	        try {
	          cleanupSubscription(subscription);
	        } finally {
	          throw e;
	        }
	      } cleanupSubscription(subscription);
	      return value;
	    }
	  }
	});
	
	var $Observable = function Observable(subscriber){
	  anInstance(this, $Observable, 'Observable', '_f')._f = aFunction(subscriber);
	};
	
	redefineAll($Observable.prototype, {
	  subscribe: function subscribe(observer){
	    return new Subscription(observer, this._f);
	  },
	  forEach: function forEach(fn){
	    var that = this;
	    return new (core.Promise || global.Promise)(function(resolve, reject){
	      aFunction(fn);
	      var subscription = that.subscribe({
	        next : function(value){
	          try {
	            return fn(value);
	          } catch(e){
	            reject(e);
	            subscription.unsubscribe();
	          }
	        },
	        error: reject,
	        complete: resolve
	      });
	    });
	  }
	});
	
	redefineAll($Observable, {
	  from: function from(x){
	    var C = typeof this === 'function' ? this : $Observable;
	    var method = getMethod(anObject(x)[OBSERVABLE]);
	    if(method){
	      var observable = anObject(method.call(x));
	      return observable.constructor === C ? observable : new C(function(observer){
	        return observable.subscribe(observer);
	      });
	    }
	    return new C(function(observer){
	      var done = false;
	      microtask(function(){
	        if(!done){
	          try {
	            if(forOf(x, false, function(it){
	              observer.next(it);
	              if(done)return RETURN;
	            }) === RETURN)return;
	          } catch(e){
	            if(done)throw e;
	            observer.error(e);
	            return;
	          } observer.complete();
	        }
	      });
	      return function(){ done = true; };
	    });
	  },
	  of: function of(){
	    for(var i = 0, l = arguments.length, items = Array(l); i < l;)items[i] = arguments[i++];
	    return new (typeof this === 'function' ? this : $Observable)(function(observer){
	      var done = false;
	      microtask(function(){
	        if(!done){
	          for(var i = 0; i < items.length; ++i){
	            observer.next(items[i]);
	            if(done)return;
	          } observer.complete();
	        }
	      });
	      return function(){ done = true; };
	    });
	  }
	});
	
	hide($Observable.prototype, OBSERVABLE, function(){ return this; });
	
	$export($export.G, {Observable: $Observable});
	
	__webpack_require__(/*! ./_set-species */ 1096)('Observable');

/***/ },

/***/ 1192:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.timers.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// ie9- setTimeout & setInterval additional parameters fix
	var global     = __webpack_require__(/*! ./_global */ 908)
	  , $export    = __webpack_require__(/*! ./_export */ 912)
	  , invoke     = __webpack_require__(/*! ./_invoke */ 982)
	  , partial    = __webpack_require__(/*! ./_partial */ 1193)
	  , navigator  = global.navigator
	  , MSIE       = !!navigator && /MSIE .\./.test(navigator.userAgent); // <- dirty ie9- check
	var wrap = function(set){
	  return MSIE ? function(fn, time /*, ...args */){
	    return set(invoke(
	      partial,
	      [].slice.call(arguments, 2),
	      typeof fn == 'function' ? fn : Function(fn)
	    ), time);
	  } : set;
	};
	$export($export.G + $export.B + $export.F * MSIE, {
	  setTimeout:  wrap(global.setTimeout),
	  setInterval: wrap(global.setInterval)
	});

/***/ },

/***/ 1193:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_partial.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var path      = __webpack_require__(/*! ./_path */ 1194)
	  , invoke    = __webpack_require__(/*! ./_invoke */ 982)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 925);
	module.exports = function(/* ...pargs */){
	  var fn     = aFunction(this)
	    , length = arguments.length
	    , pargs  = Array(length)
	    , i      = 0
	    , _      = path._
	    , holder = false;
	  while(length > i)if((pargs[i] = arguments[i++]) === _)holder = true;
	  return function(/* ...args */){
	    var that = this
	      , aLen = arguments.length
	      , j = 0, k = 0, args;
	    if(!holder && !aLen)return invoke(fn, pargs, that);
	    args = pargs.slice();
	    if(holder)for(;length > j; j++)if(args[j] === _)args[j] = arguments[k++];
	    while(aLen > k)args.push(arguments[k++]);
	    return invoke(fn, args, that);
	  };
	};

/***/ },

/***/ 1194:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_path.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(/*! ./_global */ 908);

/***/ },

/***/ 1195:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.immediate.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $task   = __webpack_require__(/*! ./_task */ 1112);
	$export($export.G + $export.B, {
	  setImmediate:   $task.set,
	  clearImmediate: $task.clear
	});

/***/ },

/***/ 1196:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.dom.iterable.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $iterators    = __webpack_require__(/*! ./es6.array.iterator */ 1097)
	  , redefine      = __webpack_require__(/*! ./_redefine */ 922)
	  , global        = __webpack_require__(/*! ./_global */ 908)
	  , hide          = __webpack_require__(/*! ./_hide */ 914)
	  , Iterators     = __webpack_require__(/*! ./_iterators */ 1033)
	  , wks           = __webpack_require__(/*! ./_wks */ 929)
	  , ITERATOR      = wks('iterator')
	  , TO_STRING_TAG = wks('toStringTag')
	  , ArrayValues   = Iterators.Array;
	
	for(var collections = ['NodeList', 'DOMTokenList', 'MediaList', 'StyleSheetList', 'CSSRuleList'], i = 0; i < 5; i++){
	  var NAME       = collections[i]
	    , Collection = global[NAME]
	    , proto      = Collection && Collection.prototype
	    , key;
	  if(proto){
	    if(!proto[ITERATOR])hide(proto, ITERATOR, ArrayValues);
	    if(!proto[TO_STRING_TAG])hide(proto, TO_STRING_TAG, NAME);
	    Iterators[NAME] = ArrayValues;
	    for(key in $iterators)if(!proto[key])redefine(proto, key, $iterators[key], true);
	  }
	}

/***/ },

/***/ 1197:
/*!******************************************!*\
  !*** ./~/regenerator-runtime/runtime.js ***!
  \******************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global, process) {/**
	 * Copyright (c) 2014, Facebook, Inc.
	 * All rights reserved.
	 *
	 * This source code is licensed under the BSD-style license found in the
	 * https://raw.github.com/facebook/regenerator/master/LICENSE file. An
	 * additional grant of patent rights can be found in the PATENTS file in
	 * the same directory.
	 */
	
	!(function(global) {
	  "use strict";
	
	  var hasOwn = Object.prototype.hasOwnProperty;
	  var undefined; // More compressible than void 0.
	  var $Symbol = typeof Symbol === "function" ? Symbol : {};
	  var iteratorSymbol = $Symbol.iterator || "@@iterator";
	  var toStringTagSymbol = $Symbol.toStringTag || "@@toStringTag";
	
	  var inModule = typeof module === "object";
	  var runtime = global.regeneratorRuntime;
	  if (runtime) {
	    if (inModule) {
	      // If regeneratorRuntime is defined globally and we're in a module,
	      // make the exports object identical to regeneratorRuntime.
	      module.exports = runtime;
	    }
	    // Don't bother evaluating the rest of this file if the runtime was
	    // already defined globally.
	    return;
	  }
	
	  // Define the runtime globally (as expected by generated code) as either
	  // module.exports (if we're in a module) or a new, empty object.
	  runtime = global.regeneratorRuntime = inModule ? module.exports : {};
	
	  function wrap(innerFn, outerFn, self, tryLocsList) {
	    // If outerFn provided and outerFn.prototype is a Generator, then outerFn.prototype instanceof Generator.
	    var protoGenerator = outerFn && outerFn.prototype instanceof Generator ? outerFn : Generator;
	    var generator = Object.create(protoGenerator.prototype);
	    var context = new Context(tryLocsList || []);
	
	    // The ._invoke method unifies the implementations of the .next,
	    // .throw, and .return methods.
	    generator._invoke = makeInvokeMethod(innerFn, self, context);
	
	    return generator;
	  }
	  runtime.wrap = wrap;
	
	  // Try/catch helper to minimize deoptimizations. Returns a completion
	  // record like context.tryEntries[i].completion. This interface could
	  // have been (and was previously) designed to take a closure to be
	  // invoked without arguments, but in all the cases we care about we
	  // already have an existing method we want to call, so there's no need
	  // to create a new function object. We can even get away with assuming
	  // the method takes exactly one argument, since that happens to be true
	  // in every case, so we don't have to touch the arguments object. The
	  // only additional allocation required is the completion record, which
	  // has a stable shape and so hopefully should be cheap to allocate.
	  function tryCatch(fn, obj, arg) {
	    try {
	      return { type: "normal", arg: fn.call(obj, arg) };
	    } catch (err) {
	      return { type: "throw", arg: err };
	    }
	  }
	
	  var GenStateSuspendedStart = "suspendedStart";
	  var GenStateSuspendedYield = "suspendedYield";
	  var GenStateExecuting = "executing";
	  var GenStateCompleted = "completed";
	
	  // Returning this object from the innerFn has the same effect as
	  // breaking out of the dispatch switch statement.
	  var ContinueSentinel = {};
	
	  // Dummy constructor functions that we use as the .constructor and
	  // .constructor.prototype properties for functions that return Generator
	  // objects. For full spec compliance, you may wish to configure your
	  // minifier not to mangle the names of these two functions.
	  function Generator() {}
	  function GeneratorFunction() {}
	  function GeneratorFunctionPrototype() {}
	
	  var Gp = GeneratorFunctionPrototype.prototype = Generator.prototype;
	  GeneratorFunction.prototype = Gp.constructor = GeneratorFunctionPrototype;
	  GeneratorFunctionPrototype.constructor = GeneratorFunction;
	  GeneratorFunctionPrototype[toStringTagSymbol] = GeneratorFunction.displayName = "GeneratorFunction";
	
	  // Helper for defining the .next, .throw, and .return methods of the
	  // Iterator interface in terms of a single ._invoke method.
	  function defineIteratorMethods(prototype) {
	    ["next", "throw", "return"].forEach(function(method) {
	      prototype[method] = function(arg) {
	        return this._invoke(method, arg);
	      };
	    });
	  }
	
	  runtime.isGeneratorFunction = function(genFun) {
	    var ctor = typeof genFun === "function" && genFun.constructor;
	    return ctor
	      ? ctor === GeneratorFunction ||
	        // For the native GeneratorFunction constructor, the best we can
	        // do is to check its .name property.
	        (ctor.displayName || ctor.name) === "GeneratorFunction"
	      : false;
	  };
	
	  runtime.mark = function(genFun) {
	    if (Object.setPrototypeOf) {
	      Object.setPrototypeOf(genFun, GeneratorFunctionPrototype);
	    } else {
	      genFun.__proto__ = GeneratorFunctionPrototype;
	      if (!(toStringTagSymbol in genFun)) {
	        genFun[toStringTagSymbol] = "GeneratorFunction";
	      }
	    }
	    genFun.prototype = Object.create(Gp);
	    return genFun;
	  };
	
	  // Within the body of any async function, `await x` is transformed to
	  // `yield regeneratorRuntime.awrap(x)`, so that the runtime can test
	  // `value instanceof AwaitArgument` to determine if the yielded value is
	  // meant to be awaited. Some may consider the name of this method too
	  // cutesy, but they are curmudgeons.
	  runtime.awrap = function(arg) {
	    return new AwaitArgument(arg);
	  };
	
	  function AwaitArgument(arg) {
	    this.arg = arg;
	  }
	
	  function AsyncIterator(generator) {
	    function invoke(method, arg, resolve, reject) {
	      var record = tryCatch(generator[method], generator, arg);
	      if (record.type === "throw") {
	        reject(record.arg);
	      } else {
	        var result = record.arg;
	        var value = result.value;
	        if (value instanceof AwaitArgument) {
	          return Promise.resolve(value.arg).then(function(value) {
	            invoke("next", value, resolve, reject);
	          }, function(err) {
	            invoke("throw", err, resolve, reject);
	          });
	        }
	
	        return Promise.resolve(value).then(function(unwrapped) {
	          // When a yielded Promise is resolved, its final value becomes
	          // the .value of the Promise<{value,done}> result for the
	          // current iteration. If the Promise is rejected, however, the
	          // result for this iteration will be rejected with the same
	          // reason. Note that rejections of yielded Promises are not
	          // thrown back into the generator function, as is the case
	          // when an awaited Promise is rejected. This difference in
	          // behavior between yield and await is important, because it
	          // allows the consumer to decide what to do with the yielded
	          // rejection (swallow it and continue, manually .throw it back
	          // into the generator, abandon iteration, whatever). With
	          // await, by contrast, there is no opportunity to examine the
	          // rejection reason outside the generator function, so the
	          // only option is to throw it from the await expression, and
	          // let the generator function handle the exception.
	          result.value = unwrapped;
	          resolve(result);
	        }, reject);
	      }
	    }
	
	    if (typeof process === "object" && process.domain) {
	      invoke = process.domain.bind(invoke);
	    }
	
	    var previousPromise;
	
	    function enqueue(method, arg) {
	      function callInvokeWithMethodAndArg() {
	        return new Promise(function(resolve, reject) {
	          invoke(method, arg, resolve, reject);
	        });
	      }
	
	      return previousPromise =
	        // If enqueue has been called before, then we want to wait until
	        // all previous Promises have been resolved before calling invoke,
	        // so that results are always delivered in the correct order. If
	        // enqueue has not been called before, then it is important to
	        // call invoke immediately, without waiting on a callback to fire,
	        // so that the async generator function has the opportunity to do
	        // any necessary setup in a predictable way. This predictability
	        // is why the Promise constructor synchronously invokes its
	        // executor callback, and why async functions synchronously
	        // execute code before the first await. Since we implement simple
	        // async functions in terms of async generators, it is especially
	        // important to get this right, even though it requires care.
	        previousPromise ? previousPromise.then(
	          callInvokeWithMethodAndArg,
	          // Avoid propagating failures to Promises returned by later
	          // invocations of the iterator.
	          callInvokeWithMethodAndArg
	        ) : callInvokeWithMethodAndArg();
	    }
	
	    // Define the unified helper method that is used to implement .next,
	    // .throw, and .return (see defineIteratorMethods).
	    this._invoke = enqueue;
	  }
	
	  defineIteratorMethods(AsyncIterator.prototype);
	
	  // Note that simple async functions are implemented on top of
	  // AsyncIterator objects; they just return a Promise for the value of
	  // the final result produced by the iterator.
	  runtime.async = function(innerFn, outerFn, self, tryLocsList) {
	    var iter = new AsyncIterator(
	      wrap(innerFn, outerFn, self, tryLocsList)
	    );
	
	    return runtime.isGeneratorFunction(outerFn)
	      ? iter // If outerFn is a generator, return the full iterator.
	      : iter.next().then(function(result) {
	          return result.done ? result.value : iter.next();
	        });
	  };
	
	  function makeInvokeMethod(innerFn, self, context) {
	    var state = GenStateSuspendedStart;
	
	    return function invoke(method, arg) {
	      if (state === GenStateExecuting) {
	        throw new Error("Generator is already running");
	      }
	
	      if (state === GenStateCompleted) {
	        if (method === "throw") {
	          throw arg;
	        }
	
	        // Be forgiving, per 25.3.3.3.3 of the spec:
	        // https://people.mozilla.org/~jorendorff/es6-draft.html#sec-generatorresume
	        return doneResult();
	      }
	
	      while (true) {
	        var delegate = context.delegate;
	        if (delegate) {
	          if (method === "return" ||
	              (method === "throw" && delegate.iterator[method] === undefined)) {
	            // A return or throw (when the delegate iterator has no throw
	            // method) always terminates the yield* loop.
	            context.delegate = null;
	
	            // If the delegate iterator has a return method, give it a
	            // chance to clean up.
	            var returnMethod = delegate.iterator["return"];
	            if (returnMethod) {
	              var record = tryCatch(returnMethod, delegate.iterator, arg);
	              if (record.type === "throw") {
	                // If the return method threw an exception, let that
	                // exception prevail over the original return or throw.
	                method = "throw";
	                arg = record.arg;
	                continue;
	              }
	            }
	
	            if (method === "return") {
	              // Continue with the outer return, now that the delegate
	              // iterator has been terminated.
	              continue;
	            }
	          }
	
	          var record = tryCatch(
	            delegate.iterator[method],
	            delegate.iterator,
	            arg
	          );
	
	          if (record.type === "throw") {
	            context.delegate = null;
	
	            // Like returning generator.throw(uncaught), but without the
	            // overhead of an extra function call.
	            method = "throw";
	            arg = record.arg;
	            continue;
	          }
	
	          // Delegate generator ran and handled its own exceptions so
	          // regardless of what the method was, we continue as if it is
	          // "next" with an undefined arg.
	          method = "next";
	          arg = undefined;
	
	          var info = record.arg;
	          if (info.done) {
	            context[delegate.resultName] = info.value;
	            context.next = delegate.nextLoc;
	          } else {
	            state = GenStateSuspendedYield;
	            return info;
	          }
	
	          context.delegate = null;
	        }
	
	        if (method === "next") {
	          // Setting context._sent for legacy support of Babel's
	          // function.sent implementation.
	          context.sent = context._sent = arg;
	
	        } else if (method === "throw") {
	          if (state === GenStateSuspendedStart) {
	            state = GenStateCompleted;
	            throw arg;
	          }
	
	          if (context.dispatchException(arg)) {
	            // If the dispatched exception was caught by a catch block,
	            // then let that catch block handle the exception normally.
	            method = "next";
	            arg = undefined;
	          }
	
	        } else if (method === "return") {
	          context.abrupt("return", arg);
	        }
	
	        state = GenStateExecuting;
	
	        var record = tryCatch(innerFn, self, context);
	        if (record.type === "normal") {
	          // If an exception is thrown from innerFn, we leave state ===
	          // GenStateExecuting and loop back for another invocation.
	          state = context.done
	            ? GenStateCompleted
	            : GenStateSuspendedYield;
	
	          var info = {
	            value: record.arg,
	            done: context.done
	          };
	
	          if (record.arg === ContinueSentinel) {
	            if (context.delegate && method === "next") {
	              // Deliberately forget the last sent value so that we don't
	              // accidentally pass it on to the delegate.
	              arg = undefined;
	            }
	          } else {
	            return info;
	          }
	
	        } else if (record.type === "throw") {
	          state = GenStateCompleted;
	          // Dispatch the exception by looping back around to the
	          // context.dispatchException(arg) call above.
	          method = "throw";
	          arg = record.arg;
	        }
	      }
	    };
	  }
	
	  // Define Generator.prototype.{next,throw,return} in terms of the
	  // unified ._invoke helper method.
	  defineIteratorMethods(Gp);
	
	  Gp[iteratorSymbol] = function() {
	    return this;
	  };
	
	  Gp[toStringTagSymbol] = "Generator";
	
	  Gp.toString = function() {
	    return "[object Generator]";
	  };
	
	  function pushTryEntry(locs) {
	    var entry = { tryLoc: locs[0] };
	
	    if (1 in locs) {
	      entry.catchLoc = locs[1];
	    }
	
	    if (2 in locs) {
	      entry.finallyLoc = locs[2];
	      entry.afterLoc = locs[3];
	    }
	
	    this.tryEntries.push(entry);
	  }
	
	  function resetTryEntry(entry) {
	    var record = entry.completion || {};
	    record.type = "normal";
	    delete record.arg;
	    entry.completion = record;
	  }
	
	  function Context(tryLocsList) {
	    // The root entry object (effectively a try statement without a catch
	    // or a finally block) gives us a place to store values thrown from
	    // locations where there is no enclosing try statement.
	    this.tryEntries = [{ tryLoc: "root" }];
	    tryLocsList.forEach(pushTryEntry, this);
	    this.reset(true);
	  }
	
	  runtime.keys = function(object) {
	    var keys = [];
	    for (var key in object) {
	      keys.push(key);
	    }
	    keys.reverse();
	
	    // Rather than returning an object with a next method, we keep
	    // things simple and return the next function itself.
	    return function next() {
	      while (keys.length) {
	        var key = keys.pop();
	        if (key in object) {
	          next.value = key;
	          next.done = false;
	          return next;
	        }
	      }
	
	      // To avoid creating an additional object, we just hang the .value
	      // and .done properties off the next function object itself. This
	      // also ensures that the minifier will not anonymize the function.
	      next.done = true;
	      return next;
	    };
	  };
	
	  function values(iterable) {
	    if (iterable) {
	      var iteratorMethod = iterable[iteratorSymbol];
	      if (iteratorMethod) {
	        return iteratorMethod.call(iterable);
	      }
	
	      if (typeof iterable.next === "function") {
	        return iterable;
	      }
	
	      if (!isNaN(iterable.length)) {
	        var i = -1, next = function next() {
	          while (++i < iterable.length) {
	            if (hasOwn.call(iterable, i)) {
	              next.value = iterable[i];
	              next.done = false;
	              return next;
	            }
	          }
	
	          next.value = undefined;
	          next.done = true;
	
	          return next;
	        };
	
	        return next.next = next;
	      }
	    }
	
	    // Return an iterator with no values.
	    return { next: doneResult };
	  }
	  runtime.values = values;
	
	  function doneResult() {
	    return { value: undefined, done: true };
	  }
	
	  Context.prototype = {
	    constructor: Context,
	
	    reset: function(skipTempReset) {
	      this.prev = 0;
	      this.next = 0;
	      // Resetting context._sent for legacy support of Babel's
	      // function.sent implementation.
	      this.sent = this._sent = undefined;
	      this.done = false;
	      this.delegate = null;
	
	      this.tryEntries.forEach(resetTryEntry);
	
	      if (!skipTempReset) {
	        for (var name in this) {
	          // Not sure about the optimal order of these conditions:
	          if (name.charAt(0) === "t" &&
	              hasOwn.call(this, name) &&
	              !isNaN(+name.slice(1))) {
	            this[name] = undefined;
	          }
	        }
	      }
	    },
	
	    stop: function() {
	      this.done = true;
	
	      var rootEntry = this.tryEntries[0];
	      var rootRecord = rootEntry.completion;
	      if (rootRecord.type === "throw") {
	        throw rootRecord.arg;
	      }
	
	      return this.rval;
	    },
	
	    dispatchException: function(exception) {
	      if (this.done) {
	        throw exception;
	      }
	
	      var context = this;
	      function handle(loc, caught) {
	        record.type = "throw";
	        record.arg = exception;
	        context.next = loc;
	        return !!caught;
	      }
	
	      for (var i = this.tryEntries.length - 1; i >= 0; --i) {
	        var entry = this.tryEntries[i];
	        var record = entry.completion;
	
	        if (entry.tryLoc === "root") {
	          // Exception thrown outside of any try block that could handle
	          // it, so set the completion value of the entire function to
	          // throw the exception.
	          return handle("end");
	        }
	
	        if (entry.tryLoc <= this.prev) {
	          var hasCatch = hasOwn.call(entry, "catchLoc");
	          var hasFinally = hasOwn.call(entry, "finallyLoc");
	
	          if (hasCatch && hasFinally) {
	            if (this.prev < entry.catchLoc) {
	              return handle(entry.catchLoc, true);
	            } else if (this.prev < entry.finallyLoc) {
	              return handle(entry.finallyLoc);
	            }
	
	          } else if (hasCatch) {
	            if (this.prev < entry.catchLoc) {
	              return handle(entry.catchLoc, true);
	            }
	
	          } else if (hasFinally) {
	            if (this.prev < entry.finallyLoc) {
	              return handle(entry.finallyLoc);
	            }
	
	          } else {
	            throw new Error("try statement without catch or finally");
	          }
	        }
	      }
	    },
	
	    abrupt: function(type, arg) {
	      for (var i = this.tryEntries.length - 1; i >= 0; --i) {
	        var entry = this.tryEntries[i];
	        if (entry.tryLoc <= this.prev &&
	            hasOwn.call(entry, "finallyLoc") &&
	            this.prev < entry.finallyLoc) {
	          var finallyEntry = entry;
	          break;
	        }
	      }
	
	      if (finallyEntry &&
	          (type === "break" ||
	           type === "continue") &&
	          finallyEntry.tryLoc <= arg &&
	          arg <= finallyEntry.finallyLoc) {
	        // Ignore the finally entry if control is not jumping to a
	        // location outside the try/catch block.
	        finallyEntry = null;
	      }
	
	      var record = finallyEntry ? finallyEntry.completion : {};
	      record.type = type;
	      record.arg = arg;
	
	      if (finallyEntry) {
	        this.next = finallyEntry.finallyLoc;
	      } else {
	        this.complete(record);
	      }
	
	      return ContinueSentinel;
	    },
	
	    complete: function(record, afterLoc) {
	      if (record.type === "throw") {
	        throw record.arg;
	      }
	
	      if (record.type === "break" ||
	          record.type === "continue") {
	        this.next = record.arg;
	      } else if (record.type === "return") {
	        this.rval = record.arg;
	        this.next = "end";
	      } else if (record.type === "normal" && afterLoc) {
	        this.next = afterLoc;
	      }
	    },
	
	    finish: function(finallyLoc) {
	      for (var i = this.tryEntries.length - 1; i >= 0; --i) {
	        var entry = this.tryEntries[i];
	        if (entry.finallyLoc === finallyLoc) {
	          this.complete(entry.completion, entry.afterLoc);
	          resetTryEntry(entry);
	          return ContinueSentinel;
	        }
	      }
	    },
	
	    "catch": function(tryLoc) {
	      for (var i = this.tryEntries.length - 1; i >= 0; --i) {
	        var entry = this.tryEntries[i];
	        if (entry.tryLoc === tryLoc) {
	          var record = entry.completion;
	          if (record.type === "throw") {
	            var thrown = record.arg;
	            resetTryEntry(entry);
	          }
	          return thrown;
	        }
	      }
	
	      // The context.catch method must only be called with a location
	      // argument that corresponds to a known catch block.
	      throw new Error("illegal catch attempt");
	    },
	
	    delegateYield: function(iterable, resultName, nextLoc) {
	      this.delegate = {
	        iterator: values(iterable),
	        resultName: resultName,
	        nextLoc: nextLoc
	      };
	
	      return ContinueSentinel;
	    }
	  };
	})(
	  // Among the various tricks for obtaining a reference to the global
	  // object, this seems to be the most reliable technique that does not
	  // use indirect eval (which violates Content Security Policy).
	  typeof global === "object" ? global :
	  typeof window === "object" ? window :
	  typeof self === "object" ? self : this
	);
	
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }()), __webpack_require__(/*! ./~/process/browser.js */ 838)))

/***/ },

/***/ 1198:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/fn/regexp/escape.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ../../modules/core.regexp.escape */ 1199);
	module.exports = __webpack_require__(/*! ../../modules/_core */ 913).RegExp.escape;

/***/ },

/***/ 1199:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/core.regexp.escape.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/benjamingr/RexExp.escape
	var $export = __webpack_require__(/*! ./_export */ 912)
	  , $re     = __webpack_require__(/*! ./_replacer */ 1200)(/[\\^$*+?.()|[\]{}]/g, '\\$&');
	
	$export($export.S, 'RegExp', {escape: function escape(it){ return $re(it); }});


/***/ },

/***/ 1200:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_replacer.js ***!
  \*********************************************************/
/***/ function(module, exports) {

	module.exports = function(regExp, replace){
	  var replacer = replace === Object(replace) ? function(part){
	    return replace[part];
	  } : replace;
	  return function(it){
	    return String(it).replace(regExp, replacer);
	  };
	};

/***/ },

/***/ 3323:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 3324);

/***/ },

/***/ 3324:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 3482);
	
	//*------------------------------------------------------------------*
	
	module.exports = function (_ref) {
	    var _ref$atlasHostUrl = _ref.atlasHostUrl;
	    var hostUrl = _ref$atlasHostUrl === undefined ? window.location.protocol + "//" + window.location.host : _ref$atlasHostUrl;
	    var query = _ref.query;
	    var geneQuery = _ref.geneQuery;
	    var conditionQuery = _ref.conditionQuery;
	    var species = _ref.species;
	    var _ref$target = _ref.target;
	    var target = _ref$target === undefined ? 'gxaDifferentialTab' : _ref$target;
	
	    ReactDOM.render(React.createElement(DifferentialRouter, { hostUrl: hostUrl, query: query, geneQuery: geneQuery, conditionQuery: conditionQuery, species: species }), document.getElementById(target));
	};

/***/ },

/***/ 3325:
/*!****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/react.js ***!
  \****************************************************************/
[4347, 3326],

/***/ 3326:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/React.js ***!
  \********************************************************************/
[4348, 3327, 3471, 3475, 3362, 3480],

/***/ 3327:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOM.js ***!
  \***********************************************************************/
[4349, 3328, 3329, 3394, 3368, 3351, 3341, 3373, 3377, 3469, 3414, 3470, 3348, 3332],

/***/ 3328:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCurrentOwner.js ***!
  \********************************************************************************/
4,

/***/ 3329:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextComponent.js ***!
  \************************************************************************************/
[4350, 3330, 3345, 3349, 3351, 3362, 3344, 3343, 3393],

/***/ 3330:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMChildrenOperations.js ***!
  \************************************************************************************/
[4351, 3331, 3339, 3341, 3342, 3343, 3336],

/***/ 3331:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Danger.js ***!
  \*********************************************************************/
[4352, 3332, 3333, 3338, 3337, 3336],

/***/ 3332:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/ExecutionEnvironment.js ***!
  \**********************************************************************************/
8,

/***/ 3333:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/createNodesFromMarkup.js ***!
  \***********************************************************************************/
[4353, 3332, 3334, 3337, 3336],

/***/ 3334:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/createArrayFromMixed.js ***!
  \**********************************************************************************/
[4354, 3335],

/***/ 3335:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/toArray.js ***!
  \*********************************************************************/
[4355, 3336],

/***/ 3336:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/invariant.js ***!
  \***********************************************************************/
12,

/***/ 3337:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/getMarkupWrap.js ***!
  \***************************************************************************/
[4356, 3332, 3336],

/***/ 3338:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/emptyFunction.js ***!
  \***************************************************************************/
14,

/***/ 3339:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \*****************************************************************************************/
[4357, 3340],

/***/ 3340:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/keyMirror.js ***!
  \***********************************************************************/
[4358, 3336],

/***/ 3341:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPerf.js ***!
  \************************************************************************/
17,

/***/ 3342:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setInnerHTML.js ***!
  \***************************************************************************/
[4359, 3332],

/***/ 3343:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/setTextContent.js ***!
  \*****************************************************************************/
[4360, 3332, 3344, 3342],

/***/ 3344:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/escapeTextContentForBrowser.js ***!
  \******************************************************************************************/
20,

/***/ 3345:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMPropertyOperations.js ***!
  \************************************************************************************/
[4361, 3346, 3341, 3347, 3348],

/***/ 3346:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DOMProperty.js ***!
  \**************************************************************************/
[4362, 3336],

/***/ 3347:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \********************************************************************************************/
[4363, 3344],

/***/ 3348:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/warning.js ***!
  \*********************************************************************/
[4364, 3338],

/***/ 3349:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \***********************************************************************************************/
[4365, 3350, 3351],

/***/ 3350:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMIDOperations.js ***!
  \***********************************************************************************/
[4366, 3330, 3345, 3351, 3341, 3336],

/***/ 3351:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMount.js ***!
  \*************************************************************************/
[4367, 3346, 3352, 3328, 3364, 3365, 3367, 3368, 3370, 3371, 3341, 3373, 3376, 3377, 3362, 3381, 3382, 3385, 3336, 3342, 3390, 3393, 3348],

/***/ 3352:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserEventEmitter.js ***!
  \***************************************************************************************/
[4368, 3353, 3354, 3355, 3360, 3341, 3361, 3362, 3363],

/***/ 3353:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventConstants.js ***!
  \*****************************************************************************/
[4369, 3340],

/***/ 3354:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginHub.js ***!
  \*****************************************************************************/
[4370, 3355, 3356, 3357, 3358, 3359, 3336, 3348],

/***/ 3355:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginRegistry.js ***!
  \**********************************************************************************/
[4371, 3336],

/***/ 3356:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPluginUtils.js ***!
  \*******************************************************************************/
[4372, 3353, 3357, 3336, 3348],

/***/ 3357:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactErrorUtils.js ***!
  \******************************************************************************/
33,

/***/ 3358:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/accumulateInto.js ***!
  \*****************************************************************************/
[4373, 3336],

/***/ 3359:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/forEachAccumulated.js ***!
  \*********************************************************************************/
35,

/***/ 3360:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventEmitterMixin.js ***!
  \*************************************************************************************/
[4374, 3354],

/***/ 3361:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ViewportMetrics.js ***!
  \******************************************************************************/
37,

/***/ 3362:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Object.assign.js ***!
  \****************************************************************************/
38,

/***/ 3363:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isEventSupported.js ***!
  \*******************************************************************************/
[4375, 3332],

/***/ 3364:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFeatureFlags.js ***!
  \***********************************************************************************/
40,

/***/ 3365:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElement.js ***!
  \***************************************************************************/
[4376, 3328, 3362, 3366],

/***/ 3366:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/canDefineProperty.js ***!
  \********************************************************************************/
42,

/***/ 3367:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \******************************************************************************************/
43,

/***/ 3368:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceHandles.js ***!
  \***********************************************************************************/
[4377, 3369, 3336],

/***/ 3369:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRootIndex.js ***!
  \*****************************************************************************/
45,

/***/ 3370:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInstanceMap.js ***!
  \*******************************************************************************/
46,

/***/ 3371:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMarkupChecksum.js ***!
  \**********************************************************************************/
[4378, 3372],

/***/ 3372:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/adler32.js ***!
  \**********************************************************************/
48,

/***/ 3373:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconciler.js ***!
  \******************************************************************************/
[4379, 3374],

/***/ 3374:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactRef.js ***!
  \***********************************************************************/
[4380, 3375],

/***/ 3375:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactOwner.js ***!
  \*************************************************************************/
[4381, 3336],

/***/ 3376:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdateQueue.js ***!
  \*******************************************************************************/
[4382, 3328, 3365, 3370, 3377, 3362, 3336, 3348],

/***/ 3377:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactUpdates.js ***!
  \***************************************************************************/
[4383, 3378, 3379, 3341, 3373, 3380, 3362, 3336],

/***/ 3378:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CallbackQueue.js ***!
  \****************************************************************************/
[4384, 3379, 3362, 3336],

/***/ 3379:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/PooledClass.js ***!
  \**************************************************************************/
[4385, 3336],

/***/ 3380:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/Transaction.js ***!
  \**************************************************************************/
[4386, 3336],

/***/ 3381:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/emptyObject.js ***!
  \*************************************************************************/
57,

/***/ 3382:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/containsNode.js ***!
  \**************************************************************************/
[4387, 3383],

/***/ 3383:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/isTextNode.js ***!
  \************************************************************************/
[4388, 3384],

/***/ 3384:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/isNode.js ***!
  \********************************************************************/
60,

/***/ 3385:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/instantiateReactComponent.js ***!
  \****************************************************************************************/
[4389, 3386, 3391, 3392, 3362, 3336, 3348],

/***/ 3386:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCompositeComponent.js ***!
  \**************************************************************************************/
[4390, 3387, 3328, 3365, 3370, 3341, 3388, 3389, 3373, 3376, 3362, 3381, 3336, 3390, 3348],

/***/ 3387:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponentEnvironment.js ***!
  \****************************************************************************************/
[4391, 3336],

/***/ 3388:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocations.js ***!
  \*************************************************************************************/
[4392, 3340],

/***/ 3389:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypeLocationNames.js ***!
  \*****************************************************************************************/
65,

/***/ 3390:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/shouldUpdateReactComponent.js ***!
  \*****************************************************************************************/
66,

/***/ 3391:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEmptyComponent.js ***!
  \**********************************************************************************/
[4393, 3365, 3367, 3373, 3362],

/***/ 3392:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNativeComponent.js ***!
  \***********************************************************************************/
[4394, 3362, 3336],

/***/ 3393:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/validateDOMNesting.js ***!
  \*********************************************************************************/
[4395, 3362, 3338, 3348],

/***/ 3394:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultInjection.js ***!
  \************************************************************************************/
[4396, 3395, 3403, 3406, 3407, 3408, 3332, 3412, 3413, 3349, 3415, 3416, 3329, 3441, 3444, 3368, 3351, 3448, 3453, 3454, 3455, 3464, 3465],

/***/ 3395:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/BeforeInputEventPlugin.js ***!
  \*************************************************************************************/
[4397, 3353, 3396, 3332, 3397, 3399, 3401, 3402],

/***/ 3396:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EventPropagators.js ***!
  \*******************************************************************************/
[4398, 3353, 3354, 3348, 3358, 3359],

/***/ 3397:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/FallbackCompositionState.js ***!
  \***************************************************************************************/
[4399, 3379, 3362, 3398],

/***/ 3398:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getTextContentAccessor.js ***!
  \*************************************************************************************/
[4400, 3332],

/***/ 3399:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticCompositionEvent.js ***!
  \****************************************************************************************/
[4401, 3400],

/***/ 3400:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticEvent.js ***!
  \*****************************************************************************/
[4402, 3379, 3362, 3338, 3348],

/***/ 3401:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticInputEvent.js ***!
  \**********************************************************************************/
[4403, 3400],

/***/ 3402:
/*!*******************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/keyOf.js ***!
  \*******************************************************************/
78,

/***/ 3403:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ChangeEventPlugin.js ***!
  \********************************************************************************/
[4404, 3353, 3354, 3396, 3332, 3377, 3400, 3404, 3363, 3405, 3402],

/***/ 3404:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventTarget.js ***!
  \*****************************************************************************/
80,

/***/ 3405:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/isTextInputElement.js ***!
  \*********************************************************************************/
81,

/***/ 3406:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ClientReactRootIndex.js ***!
  \***********************************************************************************/
82,

/***/ 3407:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/DefaultEventPluginOrder.js ***!
  \**************************************************************************************/
[4405, 3402],

/***/ 3408:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/EnterLeaveEventPlugin.js ***!
  \************************************************************************************/
[4406, 3353, 3396, 3409, 3351, 3402],

/***/ 3409:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticMouseEvent.js ***!
  \**********************************************************************************/
[4407, 3410, 3361, 3411],

/***/ 3410:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticUIEvent.js ***!
  \*******************************************************************************/
[4408, 3400, 3404],

/***/ 3411:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventModifierState.js ***!
  \************************************************************************************/
87,

/***/ 3412:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \************************************************************************************/
[4409, 3346, 3332],

/***/ 3413:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactBrowserComponentMixin.js ***!
  \*****************************************************************************************/
[4410, 3370, 3414, 3348],

/***/ 3414:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/findDOMNode.js ***!
  \**************************************************************************/
[4411, 3328, 3370, 3351, 3336, 3348],

/***/ 3415:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*******************************************************************************************/
[4412, 3377, 3380, 3362, 3338],

/***/ 3416:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMComponent.js ***!
  \********************************************************************************/
[4413, 3417, 3419, 3346, 3345, 3353, 3352, 3349, 3427, 3428, 3432, 3435, 3436, 3351, 3437, 3341, 3376, 3362, 3366, 3344, 3336, 3363, 3402, 3342, 3343, 3440, 3393, 3348],

/***/ 3417:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/AutoFocusUtils.js ***!
  \*****************************************************************************/
[4414, 3351, 3414, 3418],

/***/ 3418:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/focusNode.js ***!
  \***********************************************************************/
94,

/***/ 3419:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSPropertyOperations.js ***!
  \************************************************************************************/
[4415, 3420, 3332, 3341, 3421, 3423, 3424, 3426, 3348],

/***/ 3420:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/CSSProperty.js ***!
  \**************************************************************************/
96,

/***/ 3421:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/camelizeStyleName.js ***!
  \*******************************************************************************/
[4416, 3422],

/***/ 3422:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/camelize.js ***!
  \**********************************************************************/
98,

/***/ 3423:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/dangerousStyleValue.js ***!
  \**********************************************************************************/
[4417, 3420],

/***/ 3424:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/hyphenateStyleName.js ***!
  \********************************************************************************/
[4418, 3425],

/***/ 3425:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/hyphenate.js ***!
  \***********************************************************************/
101,

/***/ 3426:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/memoizeStringOnly.js ***!
  \*******************************************************************************/
102,

/***/ 3427:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMButton.js ***!
  \*****************************************************************************/
103,

/***/ 3428:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMInput.js ***!
  \****************************************************************************/
[4419, 3350, 3429, 3351, 3377, 3362, 3336],

/***/ 3429:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/LinkedValueUtils.js ***!
  \*******************************************************************************/
[4420, 3430, 3388, 3336, 3348],

/***/ 3430:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactPropTypes.js ***!
  \*****************************************************************************/
[4421, 3365, 3389, 3338, 3431],

/***/ 3431:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getIteratorFn.js ***!
  \****************************************************************************/
107,

/***/ 3432:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMOption.js ***!
  \*****************************************************************************/
[4422, 3433, 3435, 3362, 3348],

/***/ 3433:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildren.js ***!
  \****************************************************************************/
[4423, 3379, 3365, 3338, 3434],

/***/ 3434:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/traverseAllChildren.js ***!
  \**********************************************************************************/
[4424, 3328, 3365, 3368, 3431, 3336, 3348],

/***/ 3435:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelect.js ***!
  \*****************************************************************************/
[4425, 3429, 3351, 3377, 3362, 3348],

/***/ 3436:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMTextarea.js ***!
  \*******************************************************************************/
[4426, 3429, 3350, 3377, 3362, 3336, 3348],

/***/ 3437:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactMultiChild.js ***!
  \******************************************************************************/
[4427, 3387, 3339, 3328, 3373, 3438, 3439],

/***/ 3438:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactChildReconciler.js ***!
  \***********************************************************************************/
[4428, 3373, 3385, 3390, 3434, 3348],

/***/ 3439:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/flattenChildren.js ***!
  \******************************************************************************/
[4429, 3434, 3348],

/***/ 3440:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/shallowEqual.js ***!
  \**************************************************************************/
116,

/***/ 3441:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactEventListener.js ***!
  \*********************************************************************************/
[4430, 3442, 3332, 3379, 3368, 3351, 3377, 3362, 3404, 3443],

/***/ 3442:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/EventListener.js ***!
  \***************************************************************************/
[4431, 3338],

/***/ 3443:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \****************************************************************************************/
119,

/***/ 3444:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInjection.js ***!
  \*****************************************************************************/
[4432, 3346, 3354, 3387, 3445, 3391, 3352, 3392, 3341, 3369, 3377],

/***/ 3445:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactClass.js ***!
  \*************************************************************************/
[4433, 3446, 3365, 3388, 3389, 3447, 3362, 3381, 3336, 3340, 3402, 3348],

/***/ 3446:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactComponent.js ***!
  \*****************************************************************************/
[4434, 3447, 3366, 3381, 3336, 3348],

/***/ 3447:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactNoopUpdateQueue.js ***!
  \***********************************************************************************/
[4435, 3348],

/***/ 3448:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactReconcileTransaction.js ***!
  \****************************************************************************************/
[4436, 3378, 3379, 3352, 3364, 3449, 3380, 3362],

/***/ 3449:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactInputSelection.js ***!
  \**********************************************************************************/
[4437, 3450, 3382, 3418, 3452],

/***/ 3450:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMSelection.js ***!
  \********************************************************************************/
[4438, 3332, 3451, 3398],

/***/ 3451:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getNodeForCharacterOffset.js ***!
  \****************************************************************************************/
127,

/***/ 3452:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/getActiveElement.js ***!
  \******************************************************************************/
128,

/***/ 3453:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SelectEventPlugin.js ***!
  \********************************************************************************/
[4439, 3353, 3396, 3332, 3449, 3400, 3452, 3405, 3402, 3440],

/***/ 3454:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ServerReactRootIndex.js ***!
  \***********************************************************************************/
130,

/***/ 3455:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SimpleEventPlugin.js ***!
  \********************************************************************************/
[4440, 3353, 3442, 3396, 3351, 3456, 3400, 3457, 3458, 3409, 3461, 3462, 3410, 3463, 3338, 3459, 3336, 3402],

/***/ 3456:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticClipboardEvent.js ***!
  \**************************************************************************************/
[4441, 3400],

/***/ 3457:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticFocusEvent.js ***!
  \**********************************************************************************/
[4442, 3410],

/***/ 3458:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticKeyboardEvent.js ***!
  \*************************************************************************************/
[4443, 3410, 3459, 3460, 3411],

/***/ 3459:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventCharCode.js ***!
  \*******************************************************************************/
135,

/***/ 3460:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/getEventKey.js ***!
  \**************************************************************************/
[4444, 3459],

/***/ 3461:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticDragEvent.js ***!
  \*********************************************************************************/
[4445, 3409],

/***/ 3462:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticTouchEvent.js ***!
  \**********************************************************************************/
[4446, 3410, 3411],

/***/ 3463:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SyntheticWheelEvent.js ***!
  \**********************************************************************************/
[4447, 3409],

/***/ 3464:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/SVGDOMPropertyConfig.js ***!
  \***********************************************************************************/
[4448, 3346],

/***/ 3465:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultPerf.js ***!
  \*******************************************************************************/
[4449, 3346, 3466, 3351, 3341, 3467],

/***/ 3466:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \***************************************************************************************/
[4450, 3362],

/***/ 3467:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/performanceNow.js ***!
  \****************************************************************************/
[4451, 3468],

/***/ 3468:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/performance.js ***!
  \*************************************************************************/
[4452, 3332],

/***/ 3469:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactVersion.js ***!
  \***************************************************************************/
145,

/***/ 3470:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/renderSubtreeIntoContainer.js ***!
  \*****************************************************************************************/
[4453, 3351],

/***/ 3471:
/*!*****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMServer.js ***!
  \*****************************************************************************/
[4454, 3394, 3472, 3469],

/***/ 3472:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRendering.js ***!
  \***********************************************************************************/
[4455, 3415, 3365, 3368, 3371, 3473, 3474, 3377, 3381, 3385, 3336],

/***/ 3473:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerBatchingStrategy.js ***!
  \******************************************************************************************/
149,

/***/ 3474:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactServerRenderingTransaction.js ***!
  \**********************************************************************************************/
[4456, 3379, 3378, 3380, 3362, 3338],

/***/ 3475:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactIsomorphic.js ***!
  \******************************************************************************/
[4457, 3433, 3446, 3445, 3476, 3365, 3477, 3430, 3469, 3362, 3479],

/***/ 3476:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactDOMFactories.js ***!
  \********************************************************************************/
[4458, 3365, 3477, 3478],

/***/ 3477:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactElementValidator.js ***!
  \************************************************************************************/
[4459, 3365, 3388, 3389, 3328, 3366, 3431, 3336, 3348],

/***/ 3478:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/mapObject.js ***!
  \***********************************************************************/
154,

/***/ 3479:
/*!************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/onlyChild.js ***!
  \************************************************************************/
[4460, 3365, 3336],

/***/ 3480:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/deprecated.js ***!
  \*************************************************************************/
[4461, 3362, 3348],

/***/ 3481:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/index.js ***!
  \********************************************************************/
[4462, 3327],

/***/ 3482:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 3325);
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 3484);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 3489);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 3605);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 3608);
	
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

/***/ 3483:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
794,

/***/ 3484:
/*!************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/url/url.js ***!
  \************************************************************/
[4876, 3485, 3486],

/***/ 3485:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/url/~/punycode/punycode.js ***!
  \****************************************************************************/
850,

/***/ 3486:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/index.js ***!
  \**********************************************************************/
[4877, 3487, 3488],

/***/ 3487:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/decode.js ***!
  \***********************************************************************/
852,

/***/ 3488:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/encode.js ***!
  \***********************************************************************/
853,

/***/ 3489:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery.browser */ 3490);
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 3491);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 3494).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 3510);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 3517);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 3520);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 3525);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 3597).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 3603);
	
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

/***/ 3490:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
[4848, 3483],

/***/ 3491:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 3492);

/***/ },

/***/ 3492:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery-ui-bundle */ 3493);
	
	//*------------------------------------------------------------------*
	
	
	//*------------------------------------------------------------------*
	
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

/***/ 3493:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
[4847, 3483],

/***/ 3494:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 3495);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 3507);

/***/ },

/***/ 3495:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 3496);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 3501);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 3505);
	
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
	                isNaN(this.props.minDownLevel) && isNaN(this.props.maxDownLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: this.props.minDownLevel,
	                    highExpressionLevel: this.props.maxDownLevel,
	                    lowValueColour: '#C0C0C0',
	                    highValueColour: '#0000FF' }),
	                isNaN(this.props.minUpLevel) && isNaN(this.props.maxUpLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: this.props.minUpLevel,
	                    highExpressionLevel: this.props.maxUpLevel,
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

/***/ 3496:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaGradient.css */ 3497);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = React.createClass({
	    displayName: 'LegendRow',
	
	
	    propTypes: {
	        lowValueColour: React.PropTypes.string.isRequired,
	        highValueColour: React.PropTypes.string.isRequired,
	        lowExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.number, React.PropTypes.element]).isRequired, // Baseline legend rows can be a React <span> element returned by NumberFormat
	        highExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.number, React.PropTypes.element]).isRequired
	    },
	
	    render: function render() {
	        var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -ms-linear-gradient(left, ${lowValueColour}, ${highValueColour}); background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
	        var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/g, this.props.lowValueColour).replace(/\${highValueColour}/g, this.props.highValueColour);
	
	        // for IE9
	        var LT_IE10_FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";
	        var lt_ie10_filter = LT_IE10_FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);
	
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
	                React.createElement('span', { className: 'gxaGradientColour', style: { backgroundImage: backgroundImage, filter: lt_ie10_filter } })
	            ),
	            React.createElement(
	                'div',
	                { className: 'gxaGradientLevel gxaGradientLevelMax' },
	                this.props.highExpressionLevel
	            )
	        ) : null;
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = LegendRow;

/***/ },

/***/ 3497:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaGradient.css */ 3498);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3498:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 3499:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
792,

/***/ 3500:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
793,

/***/ 3501:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/index.js ***!
  \*****************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 3502);

/***/ },

/***/ 3502:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \**********************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery-ui-bundle */ 3493);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 3503);
	
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

/***/ 3503:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*******************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaHelpTooltip.css */ 3504);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaHelpTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaHelpTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 3504:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3505:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaLegend.css */ 3506);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3506:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3507:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 3496);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 3508);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 3501);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 3505);
	
	//*------------------------------------------------------------------*
	
	var LegendBaseline = React.createClass({
	    displayName: 'LegendBaseline',
	
	
	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        minExpressionLevel: React.PropTypes.string.isRequired,
	        maxExpressionLevel: React.PropTypes.string.isRequired,
	        isMultiExperiment: React.PropTypes.bool.isRequired
	    },
	
	    render: function render() {
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
	            React.createElement('div', { ref: 'legendHelp', 'data-help-loc': dataHelpLoc, className: 'gxaLegendHelp' })
	        );
	    },
	
	    componentDidMount: function componentDidMount() {
	        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = LegendBaseline;

/***/ },

/***/ 3508:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-number-format/index.js ***!
  \*****************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/NumberFormat.jsx */ 3509);

/***/ },

/***/ 3509:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 3325); // React is called in the transpiled JS files in the return statements
	
	//*------------------------------------------------------------------*
	
	function formatBaselineExpression(expressionLevel) {
	    var numberExpressionLevel = +expressionLevel;
	    return numberExpressionLevel >= 100000 || numberExpressionLevel < 0.1 ? formatScientificNotation(expressionLevel, 1) : '' + numberExpressionLevel;
	}
	
	// expects number in the format #E# and displays exponent in superscript
	function formatScientificNotation(value, accuracy) {
	    var scientificNotationString = (+value).toExponential(accuracy || 4);
	
	    var formatParts = scientificNotationString.split(/[Ee]/);
	
	    if (formatParts.length == 1) {
	        return React.createElement(
	            'span',
	            null,
	            value
	        );
	    }
	
	    var mantissa = formatParts[0].replace(/([^\.])0+$/, "$1");
	    var exponent = formatParts[1].replace("+", "");
	
	    if (+exponent == 0) {
	        return React.createElement(
	            'span',
	            null,
	            mantissa
	        );
	    }
	
	    return React.createElement(
	        'span',
	        null,
	        mantissa !== "1" ? mantissa + ' \xD7 ' : '',
	        '10',
	        React.createElement(
	            'span',
	            { style: { 'verticalAlign': 'super' } },
	            exponent
	        )
	    );
	}
	
	//*------------------------------------------------------------------*
	
	exports.baselineExpression = formatBaselineExpression;
	exports.scientificNotation = formatScientificNotation;

/***/ },

/***/ 3510:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 3511);

/***/ },

/***/ 3511:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 3512);
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery-ui-bundle */ 3493);
	
	//*------------------------------------------------------------------*
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 3508);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 3513);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 3515);
	
	//*------------------------------------------------------------------*
	
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
	        return this.props.foldChange !== undefined;
	    },
	
	    _getStyle: function _getStyle() {
	        var style = {};
	        if (this.props.fontSize) {
	            style.fontSize = this.props.fontSize + "px";
	        }
	
	        return style;
	    },
	
	    render: function render() {
	        if (!this._hasValue()) {
	            return React.createElement('td', null);
	        }
	
	        return React.createElement(
	            'td',
	            { style: { backgroundColor: this.props.colour, verticalAlign: "middle" } },
	            React.createElement(
	                'div',
	                { style: this._getStyle(), className: this.props.displayLevels ? "gxaShowCell" : "gxaHideCell" },
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
	
	        //TODO - build this from a React component, like we do for FactorTooltip
	        function buildHeatmapCellTooltip(pValue, tStatistic, foldChange) {
	
	            return "<table>" + "<thead>" + (pValue ? "<th>Adjusted <em>p</em>-value</th>" : "") + (tStatistic ? "<th><em>t</em>-statistic</th>" : "") + "<th class='gxaHeaderCell'>Log<sub>2</sub>-fold change</th>" + "</thead>" + "<tbody>" + "<tr>" + (pValue ? "<td>" + ReactDOMServer.renderToStaticMarkup(NumberFormat.scientificNotation(pValue)) + "</td>" : "") + (tStatistic ? "<td>" + Math.floor(tStatistic * 1e4) / 1e4 + "</td>" : "") + "<td>" + foldChange + "</td>" + "</tr>" + "</tbody>" + "</table>";
	        }
	
	        // Don’t use bind, tooltip uses this internally
	        var thisProps = this.props;
	
	        $(element).attr("title", "").tooltip({
	            open: function open(event, ui) {
	                ui.tooltip.css("background", thisProps.colour);
	            },
	
	            tooltipClass: "gxaDifferentialCellTooltip",
	
	            content: function content() {
	                return buildHeatmapCellTooltip(thisProps.pValue, thisProps.tStat, thisProps.foldChange);
	            }
	        });
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = CellDifferential;

/***/ },

/***/ 3512:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-dom/server.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! react/lib/ReactDOMServer */ 3471);


/***/ },

/***/ 3513:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaShowHideCell.css */ 3514);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3514:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3515:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaDifferentialCellTooltip.css */ 3516);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3516:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3517:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery-ui-bundle */ 3493);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 3518);
	
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

/***/ 3518:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialDownloadButton.css */ 3519);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3519:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3520:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 3521);

/***/ },

/***/ 3521:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 3512);
	
	var $ = __webpack_require__(/*! jquery */ 3483);
	__webpack_require__(/*! jquery-ui-bundle */ 3493);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 3522);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 3523);
	
	//*------------------------------------------------------------------*
	
	function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {
	
	    $(element).attr("title", "").tooltip({
	
	        hide: false,
	
	        show: false,
	
	        tooltipClass: "gxaContrastTooltip",
	
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

/***/ 3522:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	
	//*------------------------------------------------------------------*
	
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

/***/ 3523:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaContrastTooltip.css */ 3524);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3524:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3525:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/Feedback.jsx */ 3526);

/***/ },

/***/ 3526:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var LocalStorageMixin = __webpack_require__(/*! react-localstorage */ 3527);
	var TimerMixin = __webpack_require__(/*! react-timer-mixin */ 3529);
	var ReactCSSTransitionGroup = __webpack_require__(/*! react-addons-css-transition-group */ 3530);
	
	var BootstrapButton = __webpack_require__(/*! react-bootstrap/lib/Button */ 3537);
	var BootstrapFormGroup = __webpack_require__(/*! react-bootstrap/lib/FormGroup */ 3577);
	var BootstrapFormControl = __webpack_require__(/*! react-bootstrap/lib/FormControl */ 3581);
	
	var EmojiSpritesFile = __webpack_require__(/*! ../assets/emojione.sprites.png */ 3585);
	var Emoji = __webpack_require__(/*! react-emojione */ 3586);
	
	__webpack_require__(/*! ./gxaFeedback.css */ 3595);
	
	//*------------------------------------------------------------------*
	
	var FeedbackPersistence = function createFeedbackComponent(FeedbackUIComponent) {
	  return React.createClass({
	    displayName: 'ExpressionAtlasFeedbackForm',
	    mixins: [LocalStorageMixin],
	
	    propTypes: {
	      collectionCallback: React.PropTypes.func.isRequired
	    },
	
	    getInitialState: function getInitialState() {
	      return {
	        created: new Date().toISOString(),
	        shownTimes: 0,
	        show: true
	      };
	    },
	
	    _shouldShow: function _shouldShow() {
	      var timeDiff = Math.abs(new Date().getTime() - new Date(this.state.created).getTime());
	      var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
	
	      return this.state.show && diffDays > 0 && this.state.shownTimes < 50;
	    },
	
	    _hide: function _hide() {
	      this.setState({ show: false });
	    },
	
	    _complete: function _complete(userResponse, optionalUserComment) {
	      this.setState({ show: false });
	      this.props.collectionCallback(userResponse, new Date().toISOString() + (optionalUserComment || ""));
	    },
	
	    render: function render() {
	      var it = this._shouldShow() ? React.createElement(FeedbackUIComponent, { key: "box", onComplete: this._complete, onRequestHide: this._hide }) : React.createElement('div', { key: 'nullKey' });
	      return React.createElement(
	        ReactCSSTransitionGroup,
	        { transitionName: 'feedbackBoxTransitionWrapper', transitionEnterTimeout: 500, transitionLeaveTimeout: 1000 },
	        it
	      );
	    },
	
	    componentDidMount: function componentDidMount() {
	      if (this._shouldShow()) {
	        this.setState(function (previousState) {
	          return { shownTimes: previousState.shownTimes + 1 };
	        });
	      }
	    }
	  });
	};
	
	var FeedbackBox = React.createClass({
	  displayName: 'FeedbackBox',
	
	  propTypes: {
	    onComplete: React.PropTypes.func.isRequired,
	    onRequestHide: React.PropTypes.func.isRequired
	  },
	
	  mixins: [TimerMixin],
	
	  getInitialState: function getInitialState() {
	    return {
	      askingWhyTheResultsWereNotUseful: false,
	      feedbackMessage: ""
	    };
	  },
	
	  componentDidUpdate: function componentDidUpdate() {
	    if (this.state.askingWhyTheResultsWereNotUseful && this.state.feedbackMessage.length === 0) {
	      this.setTimeout(function () {
	        if (this.state.feedbackMessage.length === 0) {
	          this._submitNegativeAnswer();
	        }
	      }.bind(this), 5000);
	    }
	  },
	
	  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
	    this.setState({ feedbackMessage: e.target.value });
	  },
	
	  _submitNegativeAnswer: function _submitNegativeAnswer() {
	    this._submitAnswer(0, this.state.feedbackMessage);
	  },
	
	  _submitPositiveAnswer: function _submitPositiveAnswer() {
	    this._submitAnswer(10);
	  },
	
	  _submitAnswer: function _submitAnswer(score, optionalMessage) {
	    this.props.onComplete.apply(this, arguments);
	  },
	
	  render: function render() {
	    return React.createElement(
	      'div',
	      { className: 'gxaFeedbackQuestionBox' },
	      React.createElement('div', { id: 'feedbackBoxCross', className: 'icon icon-functional', 'data-icon': 'x', onClick: this.props.onRequestHide }),
	      React.createElement(
	        'p',
	        null,
	        'Did you find these results useful?'
	      ),
	      React.createElement(
	        'div',
	        { className: 'gxaFeedbackQuestionBoxAnswer' },
	        this.state.askingWhyTheResultsWereNotUseful ? React.createElement(
	          'form',
	          null,
	          React.createElement(
	            BootstrapFormGroup,
	            {
	              controlId: 'optionalFeedback'
	            },
	            React.createElement(BootstrapFormControl, {
	              componentClass: 'textarea',
	              type: 'text',
	              value: this.state.feedbackMessage,
	              placeholder: 'Why not? (optional)',
	              onChange: this._updateStateWithFormAnswer
	            }),
	            React.createElement(BootstrapFormControl.Feedback, null),
	            React.createElement(
	              BootstrapButton,
	              { style: { float: "right" }, onClick: this._submitNegativeAnswer },
	              'Submit'
	            )
	          )
	        ) : React.createElement(
	          'div',
	          null,
	          React.createElement(
	            BootstrapButton,
	            { bsStyle: 'default', onClick: this._submitPositiveAnswer },
	            'Yes'
	          ),
	          React.createElement(
	            BootstrapButton,
	            { onClick: function () {
	                this.setState({ askingWhyTheResultsWereNotUseful: true });
	              }.bind(this), bsStyle: 'default' },
	            'No'
	          ),
	          React.createElement(
	            'a',
	            { onClick: this.props.onRequestHide },
	            'Do not show this again'
	          )
	        )
	      )
	    );
	  }
	});
	
	var Smiley = React.createClass({
	  displayName: 'Smiley',
	
	  propTypes: {
	    emoji: React.PropTypes.string.isRequired,
	    value: React.PropTypes.number.isRequired,
	    onClickCallback: React.PropTypes.func.isRequired,
	    selected: React.PropTypes.bool.isRequired
	  },
	
	  _onClick: function _onClick() {
	    this.props.onClickCallback(this.props.value);
	  },
	
	  _emojifyOptions: {
	    convertShortnames: true,
	    convertUnicode: false,
	    convertAscii: true,
	    styles: {
	      backgroundImage: 'url(' + EmojiSpritesFile + ')',
	      width: '32px',
	      height: '32px',
	      margin: '4px'
	    }
	  },
	
	  render: function render() {
	    return React.createElement(
	      'span',
	      { style: { padding: "6px" } },
	      React.createElement(
	        'span',
	        { className: this.props.selected ? "gxaSmiley gxaSmileyClicked" : "gxaSmiley", onClick: this._onClick },
	        Emoji.emojify(this.props.emoji, this._emojifyOptions)
	      )
	    );
	  }
	});
	
	var FeedbackSmileys = React.createClass({
	  displayName: 'FeedbackSmileys',
	
	  propTypes: {
	    onComplete: React.PropTypes.func.isRequired,
	    onRequestHide: React.PropTypes.func.isRequired
	  },
	
	  mixins: [TimerMixin],
	
	  getInitialState: function getInitialState() {
	    return {
	      score: -1,
	      feedbackMessage: ""
	    };
	  },
	
	  _interactionHappened: function _interactionHappened() {
	    return this.state.score !== this.getInitialState().score;
	  },
	
	  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
	    this.setState({ feedbackMessage: e.target.value });
	  },
	
	  _smileyClicked: function _smileyClicked(newScore) {
	    this.setState({ score: newScore });
	  },
	
	  _submit: function _submit() {
	    this.props.onComplete(this.state.score, this.state.feedbackMessage);
	  },
	
	  componentDidUpdate: function componentDidUpdate() {
	    if (this._interactionHappened() && this.state.feedbackMessage.length === 0) {
	      this.setTimeout(function () {
	        if (this.state.feedbackMessage.length === 0) {
	          this._submit();
	        }
	      }.bind(this), 5000);
	    }
	  },
	
	  render: function render() {
	    /* identifiers from http://emoji.codes/ */
	    return React.createElement(
	      'div',
	      { className: 'gxaSmileyFeedbackBox' },
	      React.createElement(
	        'p',
	        null,
	        ' Did you find these results useful?'
	      ),
	      React.createElement(
	        'div',
	        { className: 'gxaSmileyRow' },
	        [[":frowning:", 0], [":slight_frown:", 2], [":neutral_face:", 5], [":slight_smile:", 8], [":smiley:", 10]].map(function (ar) {
	          return React.createElement(Smiley, {
	            key: ar[0] + (this.state.score === ar[1]),
	            emoji: ar[0],
	            value: ar[1],
	            onClickCallback: this._smileyClicked,
	            selected: this.state.score === ar[1]
	          });
	        }.bind(this))
	      ),
	      React.createElement(
	        'form',
	        { style: { display: this._interactionHappened() ? "block" : "none" } },
	        React.createElement(
	          BootstrapFormGroup,
	          {
	            controlId: 'optionalFeedback'
	          },
	          React.createElement(BootstrapFormControl, {
	            componentClass: 'textarea',
	            type: 'text',
	            value: this.state.feedbackMessage,
	            placeholder: 'Feedback (optional)',
	            onChange: this._updateStateWithFormAnswer
	          }),
	          React.createElement(BootstrapFormControl.Feedback, null),
	          React.createElement(
	            'div',
	            null,
	            React.createElement(
	              BootstrapButton,
	              { onClick: this._submit },
	              'Submit'
	            )
	          )
	        )
	      )
	    );
	  }
	
	});
	
	module.exports = FeedbackPersistence(FeedbackSmileys);

/***/ },

/***/ 3527:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-localstorage/react-localstorage.js ***!
  \******************************************************************************************/
[4869, 3325, 3528],

/***/ 3528:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-localstorage/lib/warning.js ***!
  \***********************************************************************************/
839,

/***/ 3529:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-timer-mixin/TimerMixin.js ***!
  \*********************************************************************************/
840,

/***/ 3530:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-addons-css-transition-group/index.js ***!
  \********************************************************************************************/
[4856, 3531],

/***/ 3531:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroup.js ***!
  \**************************************************************************************/
[4857, 3326, 3362, 3532, 3534],

/***/ 3532:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionGroup.js ***!
  \***********************************************************************************/
[4858, 3326, 3533, 3362, 3338],

/***/ 3533:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionChildMapping.js ***!
  \******************************************************************************************/
[4859, 3439],

/***/ 3534:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactCSSTransitionGroupChild.js ***!
  \*******************************************************************************************/
[4860, 3326, 3327, 3535, 3536, 3479],

/***/ 3535:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/fbjs/lib/CSSCore.js ***!
  \*********************************************************************/
[4861, 3336],

/***/ 3536:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react/lib/ReactTransitionEvents.js ***!
  \************************************************************************************/
[4862, 3332],

/***/ 3537:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/Button.js ***!
  \*******************************************************************************/
[4639, 3538, 3553, 3554, 3564, 3565, 3325, 3566, 3568, 3573, 3575],

/***/ 3538:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/inherits.js ***!
  \***********************************************************************************/
[4632, 3539, 3542],

/***/ 3539:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/create.js ***!
  \****************************************************************************************/
[4623, 3540],

/***/ 3540:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/fn/object/create.js ***!
  \*************************************************************************************/
[4624, 3541],

/***/ 3541:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.js ***!
  \******************************************************************************/
398,

/***/ 3542:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \**************************************************************************************************/
[4633, 3543],

/***/ 3543:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/fn/object/set-prototype-of.js ***!
  \***********************************************************************************************/
[4634, 3544, 3547],

/***/ 3544:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \********************************************************************************************************/
[4635, 3545, 3550],

/***/ 3545:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.export.js ***!
  \*************************************************************************************/
[4616, 3546, 3547, 3548],

/***/ 3546:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.global.js ***!
  \*************************************************************************************/
393,

/***/ 3547:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.core.js ***!
  \***********************************************************************************/
394,

/***/ 3548:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.ctx.js ***!
  \**********************************************************************************/
[4617, 3549],

/***/ 3549:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.a-function.js ***!
  \*****************************************************************************************/
396,

/***/ 3550:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.set-proto.js ***!
  \****************************************************************************************/
[4636, 3541, 3551, 3552, 3548],

/***/ 3551:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.is-object.js ***!
  \****************************************************************************************/
428,

/***/ 3552:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.an-object.js ***!
  \****************************************************************************************/
[4637, 3551],

/***/ 3553:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/class-call-check.js ***!
  \*******************************************************************************************/
430,

/***/ 3554:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/extends.js ***!
  \**********************************************************************************/
[4612, 3555],

/***/ 3555:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/assign.js ***!
  \****************************************************************************************/
[4613, 3556],

/***/ 3556:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/fn/object/assign.js ***!
  \*************************************************************************************/
[4614, 3557, 3547],

/***/ 3557:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/es6.object.assign.js ***!
  \**********************************************************************************************/
[4615, 3545, 3558],

/***/ 3558:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.object-assign.js ***!
  \********************************************************************************************/
[4618, 3541, 3559, 3561, 3563],

/***/ 3559:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.to-object.js ***!
  \****************************************************************************************/
[4619, 3560],

/***/ 3560:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.defined.js ***!
  \**************************************************************************************/
400,

/***/ 3561:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.iobject.js ***!
  \**************************************************************************************/
[4620, 3562],

/***/ 3562:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.cof.js ***!
  \**********************************************************************************/
402,

/***/ 3563:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.fails.js ***!
  \************************************************************************************/
403,

/***/ 3564:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/interop-require-default.js ***!
  \**************************************************************************************************/
385,

/***/ 3565:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/classnames/index.js ***!
  \*********************************************************************/
406,

/***/ 3566:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-prop-types/lib/elementType.js ***!
  \*************************************************************************************/
[4638, 3325, 3567],

/***/ 3567:
/*!********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-prop-types/lib/common.js ***!
  \********************************************************************************/
432,

/***/ 3568:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/styleMaps.js ***!
  \**********************************************************************************/
[4622, 3555, 3539, 3569],

/***/ 3569:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/keys.js ***!
  \**************************************************************************************/
[4625, 3570],

/***/ 3570:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/fn/object/keys.js ***!
  \***********************************************************************************/
[4626, 3571, 3547],

/***/ 3571:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/es6.object.keys.js ***!
  \********************************************************************************************/
[4627, 3559, 3572],

/***/ 3572:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/core-js/library/modules/$.object-sap.js ***!
  \*****************************************************************************************/
[4628, 3545, 3547, 3563],

/***/ 3573:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*********************************************************************************************/
[4621, 3554, 3564, 3325, 3568, 3574],

/***/ 3574:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/invariant/browser.js ***!
  \**********************************************************************/
166,

/***/ 3575:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***********************************************************************************/
[4631, 3538, 3553, 3554, 3576, 3564, 3325, 3566],

/***/ 3576:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/object-without-properties.js ***!
  \****************************************************************************************************/
405,

/***/ 3577:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormGroup.js ***!
  \**********************************************************************************/
[4642, 3538, 3553, 3554, 3576, 3564, 3565, 3325, 3578, 3568, 3573, 3580],

/***/ 3578:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-prop-types/lib/deprecated.js ***!
  \************************************************************************************/
[4630, 3579],

/***/ 3579:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/warning/browser.js ***!
  \********************************************************************/
169,

/***/ 3580:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*****************************************************************************************************/
[4629, 3564, 3325],

/***/ 3581:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControl.js ***!
  \************************************************************************************/
[4716, 3538, 3553, 3576, 3554, 3564, 3565, 3325, 3566, 3579, 3573, 3582, 3584],

/***/ 3582:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \********************************************************************************************/
[4717, 3538, 3553, 3554, 3576, 3564, 3565, 3325, 3573, 3583],

/***/ 3583:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/Glyphicon.js ***!
  \**********************************************************************************/
[4643, 3554, 3564, 3565, 3325, 3578],

/***/ 3584:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControlStatic.js ***!
  \******************************************************************************************/
[4718, 3538, 3553, 3576, 3554, 3564, 3565, 3325, 3566, 3573],

/***/ 3585:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "72e306f1246f69de2c83c8d3c3141177.png";

/***/ },

/***/ 3586:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/react-emojione.js ***!
  \**************************************************************************************/
[4863, 3587, 3588, 3592],

/***/ 3587:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*********************************************************************************************/
829,

/***/ 3588:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \**************************************************************************************************/
[4864, 3589, 3594],

/***/ 3589:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \************************************************************************************************/
[4865, 3325, 3590, 3592],

/***/ 3590:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**********************************************************************************************/
[4866, 3591],

/***/ 3591:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \********************************************************************************************************/
833,

/***/ 3592:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*****************************************************************************************************/
[4867, 3593],

/***/ 3593:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/data/emoji-data.js ***!
  \***************************************************************************************/
835,

/***/ 3594:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \**************************************************************************************************/
[4868, 3592],

/***/ 3595:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./gxaFeedback.css */ 3596);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3596:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, "div.gxaFeedbackQuestionBox {\n  margin: 30px;\n  width: 300px;\n  background-color: #b3e0ff;\n  border: 3px solid #008ae6;\n  opacity: 0.6;\n  filter: alpha(opacity=60); /* For IE8 and earlier */\n}\n\n#feedbackBoxCross {\n  margin: 3px;\n  margin-top: 5px;\n  float: right;\n  cursor:pointer;\n}\n\n#feedbackBoxCross:before {\n  color: #BF2222;\n}\n\ndiv.gxaFeedbackQuestionBox p {\n  margin: 2%;\n font-weight: bold;\n text-align: center;\n}\n\ndiv.gxaFeedbackQuestionBox a {\n  float: right;\n  margin-top: 6px;\n  cursor:pointer;\n}\n\ndiv.gxaFeedbackQuestionBoxAnswer {\n  position:relative;\ntext-align: center;\n  margin: 0 auto;\n  margin-bottom: 10px;\n  width: 90%;\n}\n\ndiv.gxaFeedbackQuestionBox button {\n width: auto;\n}\n\n.feedbackBoxTransitionWrapper-leave {\n  opacity: 1;\n}\n\n.feedbackBoxTransitionWrapper-leave.feedbackBoxTransitionWrapper-leave-active {\n  opacity: 0.01;\n  transition: opacity 300ms ease-in;\n}\n\n.gxaSmiley {\n  opacity: 0.6;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmiley:hover {\n  opacity: 0.9;\n  text-decoration:none;\n  cursor:pointer;\n}\n\n.gxaSmileyClicked {\n  opacity: 1;\n}\n\n.gxaSmileyFeedbackBox {\n  text-align: center;\n  clear: both;\n  width: 300px;\n  opacity: 0.8;\n  filter: alpha(opacity=80); /* For IE8 and earlier */\n}\n\n.gxaSmileyRow {\n  text-align: center!important;\n}\n\n.gxaSmileyFeedbackBox p {\n  padding-left: 18px;\n  padding-top: 5px;\n  font-weight: bold;\n  font-size: 14px;\n}\n\n.gxaSmileyFeedbackBox form {\n  padding: 6px;\n  width: 87%;\n}\n\n.gxaSmileyFeedbackBox button {\n  width: 100px;\n  margin-left: 91px;\n}\n\n.form-control {\n  display: block;\n  width: 100%;\n  height: 34px;\n  padding: 6px 12px;\n  font-size: 14px;\n  line-height: 1.42857143;\n  color: #555;\n  background-color: #fff;\n  background-image: none;\n  border: 1px solid #ccc;\n  border-radius: 4px;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);\n  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;\n       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;\n}\n.form-control:focus {\n  border-color: #66afe9;\n  outline: 0;\n  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);\n}\n.form-control::-moz-placeholder {\n  color: #999;\n  opacity: 1;\n}\n.form-control:-ms-input-placeholder {\n  color: #999;\n}\n.form-control::-webkit-input-placeholder {\n  color: #999;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3597:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	exports.Icon = __webpack_require__(/*! ./src/SpeciesIcon.jsx */ 3598);
	exports.render = __webpack_require__(/*! ./src/renderer.js */ 3602);

/***/ },

/***/ 3598:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	__webpack_require__(/*! style!css!./ebi-visual-species.css */ 3599);
	var mapping = __webpack_require__(/*! ./mapping.js */ 3601);
	
	//*------------------------------------------------------------------*
	
	var Icon = React.createClass({
	  displayName: "Icon",
	
	
	  propTypes: {
	    species: React.PropTypes.string.isRequired,
	    colourOverride: React.PropTypes.string,
	    colourPerGroup: React.PropTypes.objectOf(React.PropTypes.string).isRequired
	  },
	
	  getDefaultProps: function getDefaultProps() {
	    return {
	      species: "oryctolagus cuniculus", //rabbit is objectively the best species
	      colourPerGroup: {
	        mammals: "red",
	        plants: "green",
	        other: "blue"
	      }
	    };
	  },
	
	  _lookUpIcon: function _lookUpIcon() {
	    for (var group in mapping) {
	      if (mapping.hasOwnProperty(group)) {
	        for (var iconSymbol in mapping[group]) {
	          if (mapping[group].hasOwnProperty(iconSymbol)) {
	            if (mapping[group][iconSymbol].indexOf(this.props.species.toLowerCase()) > -1) {
	              return [group, iconSymbol];
	            }
	          }
	        }
	      }
	    }
	    return ["", ""];
	  },
	
	  render: function render() {
	    var groupAndIcon = this._lookUpIcon();
	    return React.createElement("span", {
	      className: "react-ebi-species-icon",
	      "data-icon": groupAndIcon[1],
	      style: { "color": this.props.colourOverride || this.props.colourPerGroup[groupAndIcon[0]] },
	      title: this.props.species });
	  }
	
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = Icon;

/***/ },

/***/ 3599:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../css-loader!./ebi-visual-species.css */ 3600);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3600:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Taken from: https://www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css */\n\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'), local('\\263A'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'), url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n.react-ebi-species-icon:before {\n    font-family: 'EBI-Species';\n    font-size: 100%;\n    color: inherit;\n    content: attr(data-icon);\n    margin: 0 0.3em 0 0\n}\n\n.react-ebi-species-icon {\n    text-decoration: none;\n    font-style: normal\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3601:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	module.exports = {
		"mammals": {
			"C": "bos taurus",
			"d": ["canis lupus", "canis lupus familiaris"],
			"h": "equus caballus",
			"H": "homo sapiens",
			"k": "gallus gallus",
			"G": "gorilla gorilla",
			"r": "macaca mulatta",
			"9": "monodelphis domestica",
			"M": "mus musculus",
			"i": ["pan paniscus", "pan troglodytes"],
			"R": "rattus norvegicus",
			"t": "oryctolagus cuniculus",
			"x": "ovis aries",
			"8": "papio anubis"
		},
		"plants": {
			"B": "arabidopsis thaliana",
			"5": ["hordeum vulgare", "hordeum vulgare subsp. vulgare"],
			"6": ["oryza sativa", "oryza sativa japonica group"],
			"c": "zea mays",
			"P": ["brachypodium distachyon", "glycine max", "physcomitrella patens", "solanum lycopersicum", "solanum tuberosum", "sorghum bicolor", "vitis vinifera", "triticum aestivum"]
		},
		"other": {
			"7": "anolis carolinensis",
			"Z": "danio rerio",
			"F": "drosophila melanogaster",
			"W": ["caenorhabditis elegans", "schistosoma mansoni"],
			"": "saccharomyces cerevisiae",
			"E": "tetraodon nigroviridis",
			"f": ["xenopus (silurana) tropicalis", "xenopus tropicalis"]
		}
	};

/***/ },

/***/ 3602:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 3325);
	var ReactDOM = __webpack_require__(/*! react-dom */ 3481);
	
	//*------------------------------------------------------------------*
	
	var Icon = __webpack_require__(/*! ./SpeciesIcon.jsx */ 3598);
	
	//*------------------------------------------------------------------*
	
	module.exports = function (mountNode, species, colourOverride, colourPerKingdom) {
	    ReactDOM.render(React.createElement(Icon, {
	        species: species,
	        colourOverride: colourOverride,
	        colourPerKingdom: colourPerKingdom
	    }), mountNode);
	};

/***/ },

/***/ 3603:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialResults.css */ 3604);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3604:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3605:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 3325);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 3606);
	
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

/***/ 3606:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./DifferentialFacetsTree.css */ 3607);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 3500)(content, {});
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

/***/ 3607:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 3499)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 2px 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 8px;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer .gxaFacetItem h4 {\n    font-weight: bold;\n    font-size: 133%;\n    padding: 0;\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3608:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 3484);
	var QueryString = __webpack_require__(/*! querystring */ 3486);
	
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